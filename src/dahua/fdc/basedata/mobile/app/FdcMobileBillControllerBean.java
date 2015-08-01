package com.kingdee.eas.fdc.basedata.mobile.app;

import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.mobile.FdcMobileBillInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;

public class FdcMobileBillControllerBean extends AbstractFdcMobileBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.mobile.app.FdcMobileBillControllerBean");
    
    /**
	 * 描述：
	 * 
	 * @param ctx
	 * @param paramMap
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_fetchInitData(com.kingdee.bos.Context,
	 *      java.util.Map)
	 */
	@Override
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return super._fetchInitData(ctx, paramMap);
	}

	/**
	 * 描述：
	 * 
	 * @param ctx
	 * @param model
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#reHandleIntermitNumberByProperty(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectValue)
	 */
	@Override
	protected boolean reHandleIntermitNumberByProperty(Context ctx, IObjectValue model) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 描述：
	 * 
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#handleIntermitNumber(com.kingdee.bos.Context,
	 *      com.kingdee.eas.fdc.basedata.FDCBillInfo)
	 */
	@Override
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException,
			EASBizException {
		// TODO Auto-generated method stub
		// super.handleIntermitNumber(ctx, info);
	}

	/**
	 * 描述：
	 * 
	 * @param ctx
	 * @param info
	 * @param count
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#handleIntermitNumber1(com.kingdee.bos.Context,
	 *      com.kingdee.eas.fdc.basedata.FDCBillInfo, int)
	 */
	@Override
	protected void handleIntermitNumber1(Context ctx, FDCBillInfo info, int count) throws BOSException,
			CodingRuleException, EASBizException {
		// TODO Auto-generated method stub
		// super.handleIntermitNumber1(ctx, info, count);
	}

	/**
	 * 描述：
	 * 
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#handleIntermitNumberForReset(com.kingdee.bos.Context,
	 *      com.kingdee.eas.fdc.basedata.FDCBillInfo)
	 */
	@Override
	protected void handleIntermitNumberForReset(Context ctx, FDCBillInfo info) throws BOSException,
			CodingRuleException, EASBizException {
		// TODO Auto-generated method stub
		// super.handleIntermitNumberForReset(ctx, info);
	}

	/**
	 * 描述：
	 * 
	 * @param ctx
	 * @param bizObject
	 * @param org
	 * @param bindingproperty
	 * @param customString
	 * @param number
	 * @throws BOSException
	 * @throws EASBizException
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 * @see com.kingdee.eas.framework.app.AbstractCoreBillBaseControllerBean#recycleNumber(com.kingdee.bos.Context,
	 *      com.kingdee.eas.framework.CoreBillBaseInfo, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void recycleNumber(Context ctx, CoreBillBaseInfo bizObject, String org, String bindingproperty,
			String customString, String number) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		// super.recycleNumber(ctx, bizObject, org, bindingproperty, customString, number);
	}

	/**
	 * 描述：
	 * 
	 * @param ctx
	 * @param pk
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#recycleNumber(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectPK)
	 */
	@Override
	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		// TODO Auto-generated method stub
	}

	/**
	 * 描述：
	 * 
	 * @param ctx
	 * @param pk
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#recycleNumber2(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectPK)
	 */
	@Override
	protected void recycleNumber2(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		// TODO Auto-generated method stub
		// super.recycleNumber2(ctx, pk);
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////
	
	
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// FdcMobileBillInfo billInfo = (FdcMobileBillInfo) model;

		// 检验
		checkSave(ctx, model);

		IObjectPK pk = super._save(ctx, model);

		// 提交后的其他业务
		afterSave(ctx, pk, model);

		return pk;
	}

	/**
	 * 提交
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// FdcMobileBillInfo billInfo = (FdcMobileBillInfo) model;

		// 检验
		checkSubmit(ctx, model);

		// 提交前的其他业务:一般是回滚上次提交的业务
		beforeSubmit(ctx, model);

		// 提交
		IObjectPK pk = super._submit(ctx, model);

		// 提交后的其他业务
		afterSubmit(ctx, pk, model);

		return pk;
	}

	/**
	 * 删除。
	 * 
	 * @param ctx
	 *            Context
	 * @param pk
	 *            IObjectPK
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		IObjectValue model = getValue(ctx, pk, getSelector());
		// 检验
		checkDelete(ctx, model);

		// 删除前
		beforeDelete(ctx, model);

		super._delete(ctx, pk);

		// 删除后的其他业务
		afterDelete(ctx, pk, model);
	}

	@Override
	protected void _deleteBatchData(Context ctx, IObjectPK[] pkArray) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		super._deleteBatchData(ctx, pkArray);

		IObjectValue model = new FdcMobileBillInfo();
		for (int i = 0; i < pkArray.length; i++) {
			IObjectPK pk = pkArray[i];

			// 删除后的其他业务
			afterDelete(ctx, pk, model);
		}
	}

	// 保存前检查
	protected boolean checkSave(Context ctx, IObjectValue model) throws BOSException, EASBizException {

		return false;
	}

	// 提交前检查
	protected boolean checkSubmit(Context ctx, IObjectValue model) throws BOSException, EASBizException {

		return false;
	}

	// 删除前检查
	protected boolean checkDelete(Context ctx, IObjectValue model) throws BOSException, EASBizException {

		return false;
	}

	// 删除前的其他业务
	protected void beforeDelete(Context ctx, IObjectValue model) throws BOSException, EASBizException {
	}

	// 新增后的其他业务
	protected void afterAddnew(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
	}

	// 提交前的其他业务
	protected void beforeSubmit(Context ctx, IObjectValue model) throws BOSException, EASBizException {

	}

	// 保存后的其他业务
	protected void afterSave(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		// 保存分录细目
		saveEntrysItems(ctx, pk, model);
	}

	// 提交后的其他业务
	protected void afterSubmit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		FdcMobileBillInfo billInfo = (FdcMobileBillInfo) model;
		if (null == billInfo.getId()) {
			BOSUuid id = BOSUuid.read(pk.toString());
			billInfo.setId(id);
		}

		afterSubmit(ctx, model);
	}

	protected void afterSubmit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		FdcMobileBillInfo billInfo = (FdcMobileBillInfo) model;
		IObjectPK pk = new ObjectUuidPK(billInfo.getId());

		// 保存分录细目
		saveEntrysItems(ctx, pk, model);
	}

	// 保存分录细目
	protected void saveEntrysItems(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		
	}

	// 删除后的其他业务
	protected void afterDelete(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		
	}

	// 获取查询的字段
	protected SelectorItemCollection getSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add("*");

		return sic;
	}
    
}