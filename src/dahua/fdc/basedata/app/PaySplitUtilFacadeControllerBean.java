package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.basedata.assistant.VoucherTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fi.gl.EntryDC;
import com.kingdee.eas.fi.gl.VoucherEntryInfo;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;

public class PaySplitUtilFacadeControllerBean extends
		AbstractPaySplitUtilFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.basedata.app.PaySplitUtilFacadeControllerBean");

	protected void _traceReverseVoucher(Context ctx, IObjectPK pk)
			throws BOSException, EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("company.id");
		selector.add("hasCashAccount");
		selector.add("entries.id");
		selector.add("entries.entryDC");
		selector.add("entries.account.id");
		selector.add("entries.account.isBank");
		selector.add("entries.account.isCash");
		selector.add("entries.account.isCashEquivalent");

		VoucherInfo info = (VoucherInfo) VoucherFactory.getLocalInstance(ctx)
				.getValue(pk, selector);
		
		FDCSQLBuilder builderUpdate = new FDCSQLBuilder(ctx);
		builderUpdate.clear();
		builderUpdate
				.appendSql("update t_gl_voucher set Fsourcetype=3,fsourcesys=37 where fid=?");
		builderUpdate.addParam(pk.toString());
		builderUpdate.executeUpdate();
		
		VoucherTypeInfo type = FDCUtils.getDefaultFDCParamVoucherType(
				ctx, info.getCompany().getId().toString());
		if(type!=null&&type.getId()!=null){
			builderUpdate = new FDCSQLBuilder(ctx);
			builderUpdate.clear();
			builderUpdate
					.appendSql("update t_gl_voucher set FVoucherTypeID=? where fid=?");
			builderUpdate.addParam(type.getId().toString());
			builderUpdate.addParam(pk.toString());
			builderUpdate.executeUpdate();
		}
//		if (info.isHasCashAccount()) {
			AccountViewInfo accountInfo = FDCUtils.getDefaultFDCParamAccount(
					ctx, info.getCompany().getId().toString());
			if (accountInfo.getId() != null) {
				for (int i = 0, size = info.getEntries().size(); i < size; i++) {
					VoucherEntryInfo entryInfo = info.getEntries().get(i);
					if (entryInfo.getEntryDC()
							.equals(EntryDC.getEnum("CREDIT"))) {
						if (entryInfo.getAccount().isIsBank()
								|| entryInfo.getAccount().isIsCash()
								|| entryInfo.getAccount().isIsCashEquivalent()) {
							builderUpdate = new FDCSQLBuilder(
									ctx);
							builderUpdate.clear();
							builderUpdate
									.appendSql("update T_GL_VoucherEntry set FAccountID=? where fid=?");
							builderUpdate.addParam(accountInfo.getId().toString());
							builderUpdate.addParam(entryInfo.getId().toString());
							builderUpdate.executeUpdate();
						}
					}
				}
			}
			else
				throw new FDCException(FDCException.NOACCOUNTVIEW);
//		}
	}
}