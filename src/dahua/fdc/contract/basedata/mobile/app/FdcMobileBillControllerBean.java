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
	 * ������
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
	 * ������
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
	 * ������
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
	 * ������
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
	 * ������
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
	 * ������
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
	 * ������
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
	 * ������
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

		// ����
		checkSave(ctx, model);

		IObjectPK pk = super._save(ctx, model);

		// �ύ�������ҵ��
		afterSave(ctx, pk, model);

		return pk;
	}

	/**
	 * �ύ
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// FdcMobileBillInfo billInfo = (FdcMobileBillInfo) model;

		// ����
		checkSubmit(ctx, model);

		// �ύǰ������ҵ��:һ���ǻع��ϴ��ύ��ҵ��
		beforeSubmit(ctx, model);

		// �ύ
		IObjectPK pk = super._submit(ctx, model);

		// �ύ�������ҵ��
		afterSubmit(ctx, pk, model);

		return pk;
	}

	/**
	 * ɾ����
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
		// ����
		checkDelete(ctx, model);

		// ɾ��ǰ
		beforeDelete(ctx, model);

		super._delete(ctx, pk);

		// ɾ���������ҵ��
		afterDelete(ctx, pk, model);
	}

	@Override
	protected void _deleteBatchData(Context ctx, IObjectPK[] pkArray) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		super._deleteBatchData(ctx, pkArray);

		IObjectValue model = new FdcMobileBillInfo();
		for (int i = 0; i < pkArray.length; i++) {
			IObjectPK pk = pkArray[i];

			// ɾ���������ҵ��
			afterDelete(ctx, pk, model);
		}
	}

	// ����ǰ���
	protected boolean checkSave(Context ctx, IObjectValue model) throws BOSException, EASBizException {

		return false;
	}

	// �ύǰ���
	protected boolean checkSubmit(Context ctx, IObjectValue model) throws BOSException, EASBizException {

		return false;
	}

	// ɾ��ǰ���
	protected boolean checkDelete(Context ctx, IObjectValue model) throws BOSException, EASBizException {

		return false;
	}

	// ɾ��ǰ������ҵ��
	protected void beforeDelete(Context ctx, IObjectValue model) throws BOSException, EASBizException {
	}

	// �����������ҵ��
	protected void afterAddnew(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
	}

	// �ύǰ������ҵ��
	protected void beforeSubmit(Context ctx, IObjectValue model) throws BOSException, EASBizException {

	}

	// ����������ҵ��
	protected void afterSave(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		// �����¼ϸĿ
		saveEntrysItems(ctx, pk, model);
	}

	// �ύ�������ҵ��
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

		// �����¼ϸĿ
		saveEntrysItems(ctx, pk, model);
	}

	// �����¼ϸĿ
	protected void saveEntrysItems(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		
	}

	// ɾ���������ҵ��
	protected void afterDelete(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		
	}

	// ��ȡ��ѯ���ֶ�
	protected SelectorItemCollection getSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add("*");

		return sic;
	}
    
}