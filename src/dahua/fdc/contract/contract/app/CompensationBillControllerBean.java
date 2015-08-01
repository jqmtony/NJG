package com.kingdee.eas.fdc.contract.app;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.dap.DAPTransformResult;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class CompensationBillControllerBean extends
		AbstractCompensationBillControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.contract.app.CompensationBillControllerBean");

	/**
	 * 
	 * 描述：是否使用名称字段
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-10-17
	 *               <p>
	 */
	protected boolean isUseName() {
		return false;
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		CompensationBillInfo billInfo = CompensationBillFactory.getLocalInstance(ctx)
				.getCompensationBillInfo(new ObjectUuidPK(billId));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", billInfo.getContract().getId()
						.toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("hasSettled", Boolean.TRUE));
		if (ContractBillFactory.getLocalInstance(ctx).exists(filter)) {
			throw new EASBizException(new NumericExceptionSubItem("111",
					"审批不通过：违约金单据对应的合同已结算！"));
		}
		super._audit(ctx, billId);
	}
	protected void _reverseSave(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
//		super._reverseSave(ctx, pk, model);
	}
	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum bOTBillOperStateEnum,
			IObjectValue bOTRelationInfo) throws BOSException, EASBizException {
		// 反写导致扣款分录数据删除，暂屏蔽
		// super._reverseSave(ctx, srcBillPK, srcBillVO,
		// bOTBillOperStateEnum,bOTRelationInfo);

		BOTRelationInfo botRelation = (BOTRelationInfo) bOTRelationInfo;
		if (new VoucherInfo().getBOSType().toString().equals(
				botRelation.getDestEntityID())) {
			CompensationBillInfo info = new CompensationBillInfo();
			info.setId(BOSUuid.read(srcBillPK.toString()));
			if (BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum)) {
				info.setFiVouchered(true);
				VoucherInfo voucherInfo = new VoucherInfo();
				voucherInfo.setId(BOSUuid.read(botRelation.getDestObjectID()));
				info.setVoucher(voucherInfo);

				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("fiVouchered");
				selector.add("voucher.id");
				CompensationBillFactory.getLocalInstance(ctx).updatePartial(info,
						selector);

			} else if (BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum)) {
				info.setFiVouchered(false);
				info.setVoucher(null);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("fiVouchered");
				selector.add("voucher.id");
				CompensationBillFactory.getLocalInstance(ctx).updatePartial(info,
						selector);
			}
		}
	}
	protected SelectorItemCollection getBOTPSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("curProject.*");
		sic.add("contract.partB.*");
		sic.add("accountView.id");
		sic.add("accountView.longNumber");
		sic.add("accountView.longName");
		return sic;
	}
	protected DAPTransformResult _generateVoucher(Context ctx,
			IObjectCollection sourceBillCollection, IObjectPK botMappingPK)
			throws BOSException, EASBizException {
		Set ids=new HashSet();
		for(Iterator iter=sourceBillCollection.iterator();iter.hasNext();){
			FDCBillInfo info=(FDCBillInfo)iter.next();
			ids.add(info.getId().toString());
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("id", ids, CompareType.INCLUDE));
		boolean  isAudited = CompensationBillFactory.getLocalInstance(ctx).exists(filter);
		if(isAudited){
			throw new EASBizException(new NumericExceptionSubItem("100","存在不符合生成凭证条件的记录，请重新选择，保证所选的记录都是审批状态的!"));
		}
		DAPTransformResult voucher = super._generateVoucher(ctx,sourceBillCollection,botMappingPK);
		return voucher;
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		checkBeforeUnAudit(ctx,billId);
		super._unAudit(ctx, billId);
	}
	private void checkBeforeUnAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", billId.toString()));
		filter.getFilterItems().add(new FilterItemInfo("fiVouchered",Boolean.valueOf(true)));
		boolean isVouchered = CompensationBillFactory.getLocalInstance(ctx).exists(filter);
		CompensationBillInfo info = getCompensationBillInfo(ctx, new ObjectUuidPK(
				billId));
		if(isVouchered){
			String msg = "单据: "+info.getNumber()+" 已生成凭证，不能反审批!";
			throw new EASBizException(new NumericExceptionSubItem("100",msg));
		}
	}
}