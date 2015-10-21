package com.kingdee.eas.fdc.basedata.app;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IBoAttchAsso;
import com.kingdee.eas.base.codingrule.CodingRuleEntryCollection;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.util.FdcCodingRuleUtil;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcEntityViewUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectValueUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

/**
 * 
 * ����:���ز����ݻ���
 * 
 * @author liupd date:2006-10-13
 *         <p>
 * @version EAS5.1.3
 */
public class FDCBillControllerBean extends AbstractFDCBillControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean");

	//Ĭ�ϲ��ñ��롣
    protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        FDCBillInfo info = (FDCBillInfo)super._getValue(ctx,pk);
        String retValue = "";
        if(info.getNumber()!= null)
        {
        	retValue = info.getNumber();
            if(info.getName()!=null){
            	retValue = retValue + " " + info.getName();
            }
        }
        return retValue;
    }

    protected IObjectPK _addnew(Context ctx , IObjectValue model) throws BOSException , EASBizException
	{
    	FDCBillInfo fDCBillInfo = ((FDCBillInfo) model);
    	
		//ȡ�����Ƶ�ǰ��ո�
		trimName(fDCBillInfo);
		
		//���õ��ݵ�һЩ����
		setProps(ctx, fDCBillInfo);
		
		//�����ڼ�
		dealPeriod(ctx, fDCBillInfo);	
		
		//����ԭ��
		dealAmount(ctx, fDCBillInfo);
		
    	return super._addnew(ctx,model);
	}
    
    //����
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		FDCBillInfo fDCBillInfo = ((FDCBillInfo) model);

		if (fDCBillInfo.getState() == null)
			fDCBillInfo.setState(FDCBillStateEnum.SAVED);

		//		handleIntermitNumber(ctx,fDCBillInfo);//����Ϻ�
		//���õ��ݵ�һЩ����
		setPropsForBill(ctx, fDCBillInfo);

		//�����ֶβ������������ֵ�����ı䣬����Ҫ���»�ȡ����
		boolean flag = reHandleIntermitNumberByProperty(ctx, model);

		// ����Ϻ�
		if (!flag) {
			if (fDCBillInfo.getId() == null || !this._exists(ctx, new ObjectUuidPK(fDCBillInfo.getId()))) {
				handleIntermitNumber(ctx, fDCBillInfo);
			}
		}

		//��鵥��
		checkBill(ctx, model);

		//ȡ�����Ƶ�ǰ��ո�
		trimName(fDCBillInfo);

		//�����ڼ�
		dealPeriod(ctx, fDCBillInfo);

		super._save(ctx, pk, fDCBillInfo);
	}
	
	//����
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		FDCBillInfo fDCBillInfo = ((FDCBillInfo) model);
		
		if(fDCBillInfo.getState() == null){
			fDCBillInfo.setState(FDCBillStateEnum.SAVED);
		}
//		handleIntermitNumber(ctx,fDCBillInfo);//����Ϻ�
		
		//���õ��ݵ�һЩ����
		setPropsForBill(ctx, fDCBillInfo);
	
		//�����ֶβ������������ֵ�����ı䣬����Ҫ���»�ȡ����
		boolean flag = reHandleIntermitNumberByProperty(ctx, model);

		// ����Ϻ�
		if (!flag) {
			if (fDCBillInfo.getId() == null || !this._exists(ctx, new ObjectUuidPK(fDCBillInfo.getId())) || fDCBillInfo.getNumber() == null
					|| fDCBillInfo.getNumber().length() == 0) {
				handleIntermitNumber(ctx, fDCBillInfo);
			}
		}

		//��鵥��
		checkBill(ctx, model);

		//ȡ�����Ƶ�ǰ��ո�
		trimName(fDCBillInfo);
		 
		//�����ڼ�
		dealPeriod(ctx, fDCBillInfo); 
		
		return super._save(ctx, fDCBillInfo);
	}

	//�ύ
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);
		FDCBillInfo.setState(FDCBillStateEnum.SUBMITTED);
//		handleIntermitNumber(ctx,FDCBillInfo);//����Ϻ�
		
		//���õ��ݵ�һЩ����
		setPropsForBill(ctx, FDCBillInfo);
		
		//�����ֶβ������������ֵ�����ı䣬����Ҫ���»�ȡ����
		boolean flag = reHandleIntermitNumberByProperty(ctx, model);

		// ����Ϻ�
		if (!flag) {
			if (FDCBillInfo.getId() == null || !this._exists(ctx, new ObjectUuidPK(FDCBillInfo.getId())) || FDCBillInfo.getNumber() == null
					|| "".equals(FDCBillInfo.getNumber().trim())) {
				handleIntermitNumber(ctx, FDCBillInfo);
			}
		}
		 
		//��鵥��
		checkBill(ctx, model);
		
		//ȡ�����Ƶ�ǰ��ո�
		trimName(FDCBillInfo);
				 
		//�����ڼ�
		dealPeriod(ctx, FDCBillInfo); 
			
		super._submit(ctx, pk, FDCBillInfo);
	}

	//�ύ
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);
		FDCBillInfo.setState(FDCBillStateEnum.SUBMITTED);
//		handleIntermitNumber(ctx,FDCBillInfo);//����Ϻ�
		
		//���õ��ݵ�һЩ����
		setPropsForBill(ctx, FDCBillInfo);

		//�����ֶβ������������ֵ�����ı䣬����Ҫ���»�ȡ����
		boolean flag = reHandleIntermitNumberByProperty(ctx, model);

		// ����Ϻ�
		if (!flag) {
			if (FDCBillInfo.getId() == null || !this._exists(ctx, new ObjectUuidPK(FDCBillInfo.getId())) || FDCBillInfo.getNumber() == null
					|| "".equals(FDCBillInfo.getNumber().trim())) {
				handleIntermitNumber(ctx, FDCBillInfo);
			}
		}

		//��鵥��
		checkBill(ctx, model);
		//ȡ�����Ƶ�ǰ��ո�
		trimName(FDCBillInfo);

		//�����ڼ�
		dealPeriod(ctx, FDCBillInfo);
		
		return super._submit(ctx, FDCBillInfo);
	}


	//���õ��ݵ�һЩ���ԣ����ڵ��뵥�ݿ�����֯���ô������⣬����ȡ������Ŀ��Ӧ�ĳɱ�����
	protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo) throws EASBizException, BOSException {
	
		CurProjectInfo projectInfo = null;
		if(fDCBillInfo.getOrgUnit() == null) {			
			if(fDCBillInfo.get("curProject")!=null){
				projectInfo =(CurProjectInfo)fDCBillInfo.get("curProject");
				if( projectInfo.getCostCenter()==null || projectInfo.getCU()==null){
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("CU.id");
					sic.add("costCenter.id");				
					projectInfo = CurProjectFactory.getLocalInstance(ctx)
						.getCurProjectInfo(new ObjectUuidPK(projectInfo.getId().toString()),sic);
				}
				if(projectInfo.getCostCenter()!=null) {
					FullOrgUnitInfo orgUnit = projectInfo.getCostCenter().castToFullOrgUnitInfo();
					if (fDCBillInfo.get("botpFrom") == null){
						fDCBillInfo.setOrgUnit(orgUnit);
					}
				}
			}else{
				FullOrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo();
		
				if (fDCBillInfo.get("botpFrom") == null){
					fDCBillInfo.setOrgUnit(orgUnit);
				}
			}
		}
		if(fDCBillInfo.getCU() == null) {
			if(fDCBillInfo.get("curProject")!=null){
				if(projectInfo==null){
					projectInfo =(CurProjectInfo)fDCBillInfo.get("curProject");
				}
				if(projectInfo.getCU()==null){
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("CU.id");				
					projectInfo = CurProjectFactory.getLocalInstance(ctx)
						.getCurProjectInfo(new ObjectUuidPK(projectInfo.getId().toString()),sic);
				}				
				fDCBillInfo.setCU(projectInfo.getCU());
				
			}else{
				CtrlUnitInfo currentCtrlUnit = ContextUtil.getCurrentCtrlUnit(ctx);
				fDCBillInfo.setCU(currentCtrlUnit);
			}
		}		
	}
	
	//�����ڼ�
	protected void dealPeriod(Context ctx, FDCBillInfo fDCBillInfo) throws EASBizException, BOSException {
		
		if(fDCBillInfo.getBookedDate()==null ){
			if(fDCBillInfo.getCreateTime()==null){
				//fDCBillInfo.setCreateTime();
				fDCBillInfo.setCreateTime(new Timestamp(new Date().getTime()));
			}
			fDCBillInfo.setBookedDate(fDCBillInfo.getCreateTime());
		}
		
		//�ڼ�Ϊ�գ�����д���
		if(fDCBillInfo.getPeriod()==null && fDCBillInfo.get("curProject")!=null){
			CurProjectInfo projectInfo =(CurProjectInfo)fDCBillInfo.get("curProject");
			PeriodInfo period = FDCUtils.getCurrentPeriod(ctx,projectInfo.getId().toString(),isCost());			
			fDCBillInfo.setPeriod(period);
		}
	}

	//����ԭ��
	protected void dealAmount(Context ctx, FDCBillInfo fDCBillInfo) throws EASBizException, BOSException {
		if(fDCBillInfo.getOriginalAmount()==null ){
			fDCBillInfo.setOriginalAmount(fDCBillInfo.getAmount());
		}
	}
	
	/*protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {

		FDCBillInfo fDCBillInfo = ((FDCBillInfo) model);
		handleIntermitNumber(ctx,fDCBillInfo);//����Ϻ�
		setPropsForBill(ctx, fDCBillInfo);

		checkBill(ctx, model);

		trimName(fDCBillInfo);
		
		super._addnew(ctx, pk, fDCBillInfo);
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		FDCBillInfo fDCBillInfo = ((FDCBillInfo) model);
		handleIntermitNumber(ctx,fDCBillInfo);//����Ϻ�
		setPropsForBill(ctx, fDCBillInfo);

		checkBill(ctx, model);
		
		trimName(fDCBillInfo);
		
		return super._addnew(ctx, fDCBillInfo);
	}*/

	/**
	 * ȡ�����Ƶ�ǰ��ո�
	 * @param fDCBillInfo
	 */
	//update by david_yang PT043562 2011.04.02 (����name��255���ַ�)
	protected void trimName(FDCBillInfo fDCBillInfo) {
		if(fDCBillInfo.getName() != null) {
			fDCBillInfo.setName(fDCBillInfo.getName().trim());
			
			if(fDCBillInfo.getName().length()>255) {
				fDCBillInfo.setName(fDCBillInfo.getName().substring(0,255));
			}
		}

	}

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		//�˴�����,���������ύ,Ӧ�õ���submit,��submit��У��
//		checkBill(ctx, model);
//		
//		trimName((FDCBillInfo)model);
		super._update(ctx, pk, model);
	}

	/**
	 * 
	 * ����������ͨ��
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.app.AbstractFDCBillControllerBean#_audit(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	protected void _audit(Context ctx, List idList) throws BOSException,
			EASBizException {

		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();

			audit(ctx, BOSUuid.read(id));

		}

	}

	/**
	 * 
	 * ������������
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.app.AbstractFDCBillControllerBean#_unAudit(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	protected void _unAudit(Context ctx, List idList) throws BOSException,
			EASBizException {
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();

			unAudit(ctx, BOSUuid.read(id));

		}

	}

	/**
	 * 
	 * ��������������ظ�
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd ����ʱ�䣺2006-8-24
	 *               <p>
	 */
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
		if(!isUseName()) return;
		
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("name", billInfo.getName()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
		filter.getFilterItems()
				.add(new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
								.getId()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NAME_DUP);
		}
	}

	/**
	 * 
	 * �������������ظ�<p>
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author liupd ����ʱ�䣺2006-8-24
	 *               <p>
	 */
	protected void checkNumberDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
		if(!isUseNumber()) return;
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));
		if (billInfo.getOrgUnit() != null){
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit().getId()));
		}
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}
	}

	/**
	 * 
	 * ��������鵥��
	 * 
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd ����ʱ�䣺2006-10-13
	 *               <p>
	 */
	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);

		checkNumberDup(ctx, FDCBillInfo);

		checkNameDup(ctx, FDCBillInfo);

	}
	
	//���ʱ��鵥��
	private void checkBillForAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo)throws BOSException, EASBizException {
		
	}
	
	//�����ʱ��鵥��
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo)throws BOSException, EASBizException {
		
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();
		
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		billInfo.setAuditTime(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		
		//���ʱ��鵥��
		checkBillForAudit( ctx,billId,billInfo);

		_updatePartial(ctx, billInfo, selector);
	}

	protected void _setAudittingStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		//update by david_yang R110421-081 2011.04.28
		
		FDCBillInfo billInfo = new FDCBillInfo();
		
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTING);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		billInfo.setAuditTime(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
	
		_updatePartial(ctx, billInfo, selector);
	}

	//�����
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();

		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");

		//���ʱ��鵥��
		checkBillForUnAudit( ctx,billId,billInfo);
		
		_updatePartial(ctx, billInfo, selector);
	}

	
	/**
	 * 
	 * �������ɱ��½�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-10-17 <p>
	 */
	protected boolean isCost() {
		return true;
	}
	
	/**
	 * 
	 * �������Ƿ�ʹ�������ֶ�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-10-17 <p>
	 */
	protected boolean isUseName() {
		return true;
	}
	
	/**
	 * 
	 * �������Ƿ�ʹ�ñ����ֶ�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-10-17 <p>
	 */
	protected boolean isUseNumber() {
		return true;
	}
	
	
	/**
	 * ����
	 * @author sxhong  		Date 2006-11-25
	 */
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException
	{
//		String orgId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
//		String bindingProperty = "contractType.number";
//		// �޵�ǰ��֯�����ߵ�ǰ��֯û�����������ü��ŵ�
//		if (orgId == null || orgId.trim().length() == 0) {			
//			orgId = OrgConstants.DEF_CU_ID;
//		}
//		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
//		
//		// ��ID���������˱������
//		if (orgId != null && orgId.trim().length() > 0
//				&& iCodingRuleManager.isExist(info, orgId, bindingProperty)) {			
//			// ���ʹ����"������Ϻ�"
//			if (iCodingRuleManager.isUseIntermitNumber(info, orgId, bindingProperty)) {
//				String numberTemp = iCodingRuleManager.getNumber(info, orgId, bindingProperty, "");
//				info.setNumber(numberTemp);				
//			} else if (iCodingRuleManager.isAddView(info, orgId, bindingProperty)){				
//				// �ж��Ƿ��޸��˱���,�Ƿ�Ĵ���˳���
//				if (iCodingRuleManager.isModifiable(info, orgId, bindingProperty)) {					
//					iCodingRuleManager.checkModifiedNumber(info, orgId,info.getNumber(), bindingProperty);
//				}				
//			} else {
//				// ʲô��ûѡ,��������ʾ,����Ϻ�,ҵ�񴫿�numberֵ,�ڴ�����number
//				String numberTemp = iCodingRuleManager.getNumber(info,orgId, bindingProperty, "");
//				info.setNumber(numberTemp);
//			}
//		}		
				
		
		//����û��ڿͻ����ֹ�ѡ���˶Ϻ�,��˴�����������
		if(info.getNumber() != null && info.getNumber().length() > 0) return;
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		
		//�Գɱ����Ľ��д���
		String costUnitId= info.getOrgUnit().getId().toString();
			//ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		
       if(StringUtils.isEmpty(costUnitId)){
    	   return;
       }
       boolean isExist = true;
       if (!iCodingRuleManager.isExist(info, costUnitId)){
    	   costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
      		if (!iCodingRuleManager.isExist(info, costUnitId)){
      			isExist = false; 
           }
      }
       if(isExist){
    	   // ѡ���˶Ϻ�֧�ֻ���û��ѡ��������ʾ,��ȡ�����ñ��
           if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId) || !iCodingRuleManager.isAddView(info, costUnitId))
           // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
           {
               // �����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
               String number = iCodingRuleManager.getNumber(info,costUnitId);
               info.setNumber(number);
           }
       }
	}
	
	
	//��ͬ�����ı����������룬�Ѿ�����handleIntermitNumber
	protected void handleIntermitNumberForReset(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException
	{
		FilterInfo filter = null;
		int i=0;
		// ��ȡ���ѭ������(���ڱ������)
		int cycleMaxIndex = getCycleMaxIndex4HandleIntermitNumber();
		
		do {
			//��������ظ�����ȡ����
			handleIntermitNumber1(ctx, info, i);
			
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("number", info.getNumber()));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID.getValue(),CompareType.NOTEQUALS));		
			filter.getFilterItems()
					.add(new FilterItemInfo("orgUnit.id", info.getOrgUnit().getId()));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
			}
			i++;
		} while (_exists(ctx, filter) && i < cycleMaxIndex);
	}

	/**
	 * ��������ȡ���ѭ������(���ڱ������)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-6-13
	 */
	protected int getCycleMaxIndex4HandleIntermitNumber() {
		return 1000;
	}
	
	protected void handleIntermitNumber1(Context ctx, FDCBillInfo info, int count) throws BOSException, CodingRuleException,
			EASBizException
	{
		String orgId = info.getOrgUnit().getId().toString();
		// �޵�ǰ��֯�����ߵ�ǰ��֯û�����������ü��ŵ�
		if (orgId == null || orgId.trim().length() == 0) {			
			orgId = OrgConstants.DEF_CU_ID;
		}
	    if(StringUtils.isEmpty(orgId)){
	    	return;
	    }
	    ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	    boolean isExist = true;
	    if (!iCodingRuleManager.isExist(info, orgId)){
	    	orgId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
	      	if (!iCodingRuleManager.isExist(info, orgId)){
	      		isExist = false; 
	         }
	     }
		
		// ��ID���������˱������
		if (isExist) {			
			// ���ʹ����"������Ϻ�"
			if (iCodingRuleManager.isUseIntermitNumber(info, orgId)) {
				String numberTemp = iCodingRuleManager.getNumber(info, orgId);
				info.setNumber(numberTemp);				
			} else if (iCodingRuleManager.isAddView(info, orgId)){				
				// �ж��Ƿ��޸��˱���,�Ƿ�Ĵ���˳���
				if (iCodingRuleManager.isModifiable(info, orgId)) {					
					iCodingRuleManager.checkModifiedNumber(info, orgId,info.getNumber());
				}				
			} else {
				// ʲô��ûѡ,��������ʾ,����Ϻ�,ҵ�񴫿�numberֵ,�ڴ�����number
				String numberTemp = iCodingRuleManager.getNumber(info,orgId);
				info.setNumber(numberTemp);
			}
		}	
	}

	/**
	 * �����ύ״̬(������ͨ��ʱ���Զ��ڵ�)
	 */
	protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		setBillStatus(ctx, billId, FDCBillStateEnum.SUBMITTED);
		
	}

	private void setBillStatus(Context ctx, BOSUuid billId, FDCBillStateEnum state) throws BOSException, EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();

		billInfo.setId(billId);
		billInfo.setState(state);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");

		_updatePartial(ctx, billInfo, selector);
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
	
		recycleNumber(ctx, pk);
		
		super._delete(ctx, pk);
		
		this.delAttachment(ctx, new IObjectPK[] { pk });
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {

		for (int i = 0; i < arrayPK.length; i++) {
			recycleNumber(ctx, arrayPK[i]);
		}
		
		super._delete(ctx, arrayPK);
		this.delAttachment(ctx, arrayPK);
	}

	/**
	 * ������ɾ����������ҵ�����������б���ļ�¼�Լ� ��������
	 * @param ctx
	 * @param arrayPK
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author��jian_cao
	 * @CreateTime��2013-3-7
	 */
	private void delAttachment(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {

		for (int i = 0; i < arrayPK.length; i++) {

			EntityViewInfo view = new EntityViewInfo();
			
			SelectorItemCollection itemColl = new SelectorItemCollection();
			itemColl.add(new SelectorItemInfo("id"));
			itemColl.add(new SelectorItemInfo("attachment.id"));
			itemColl.add(new SelectorItemInfo("attachment.isShared"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", arrayPK[i]));
			
			view.getSelector().addObjectCollection(itemColl);
			view.setFilter(filter);
			
			//��ȡ��������ҵ�����������б��¼
			BoAttchAssoCollection boAttchColl = BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(view);
			if (!boAttchColl.isEmpty()) {
				ArrayList pkList = new ArrayList();
				IBoAttchAsso iBoAttchAsso = BoAttchAssoFactory.getLocalInstance(ctx);
				//ѭ���б��¼����¼����ID��pks���飬��ɾ����������ҵ�����������б�ļ�¼
				for (int j = 0; j < boAttchColl.size(); j++) {
					
					BoAttchAssoInfo boAttchAssoInfo = boAttchColl.get(j);
					AttachmentInfo attachment = (AttachmentInfo) boAttchAssoInfo.getAttachment();
					//������ǹ������ͼ�¼�¸���ID��������
					if (!attachment.isIsShared()) {
						pkList.add(new ObjectUuidPK(attachment.getId()));
					}
					//ɾ��������ҵ�����Ĺ�����ϵ
					iBoAttchAsso.delete(new ObjectUuidPK(boAttchAssoInfo.getId()));
				}
				
				IObjectPK[] pkArr = this.checkAttachmentIsDel(iBoAttchAsso, pkList);
				
				if (pkArr.length > 0) {
					//ɾ�������б�
					AttachmentFactory.getLocalInstance(ctx).delete(pkArr);
				}
			}
		}
	}

	/**
	 * ��������⸽���б���ĸ����Ƿ����ɾ���������ؿ���ɾ���ĸ���PK����
	 * @param pkList
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author��jian_cao
	 * @CreateTime��2013-3-8
	 */
	private IObjectPK[] checkAttachmentIsDel(IBoAttchAsso iBoAttchAsso, ArrayList pkList) throws BOSException, EASBizException {

		ArrayList delPKSet = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItemInfo = new FilterItemInfo("attachment.id", null);
		filter.getFilterItems().add(filterItemInfo);
		view.setFilter(filter);

		for (int i = 0, size = pkList.size(); i < size; i++) {
			
			IObjectPK pk = (IObjectPK) pkList.get(i);
			filterItemInfo.setCompareValue(pk);
			//����б���û�и�����صļ�¼���Ͱѿ���ɾ���ĸ�����PK�ŵ�������
			if (!iBoAttchAsso.exists(filter)) {
				delPKSet.add(pk);
			}
			
		}
		
		//����ɾ���õ�IObjectPK[]��ֵ
		IObjectPK[] pkArr = new IObjectPK[delPKSet.size()];
		for (int i = 0; i < pkArr.length; i++) {
			pkArr[i] = (IObjectPK) delPKSet.get(i);
		}
		
		return pkArr;
	}

	/**
	 * ����Number����������˱������֧�ֶϺŵĻ�
	 * @param ctx
	 * @param pk
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 */
	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		FDCBillInfo info = getValueForRecycleNumber(ctx, pk);
		
		//------ ������֯�»�ȡ�ɱ�����Ϊ�յĴ��� zhicheng_jin 090319
		OrgUnitInfo currentCostUnit = ContextUtil.getCurrentOrgUnit(ctx);
		if (currentCostUnit == null) {
			currentCostUnit = ContextUtil.getCurrentSaleUnit(ctx);
		}
		//-------- over --------
		
		logger.info("===============================================================================");
		logger.info("FDCBillControllerBean.recycleNumber(),start");
		logger.info("===============================================================================");

		logger.info("FDCBillControllerBean.recycleNumber().OrgUnit:" + ContextUtil.getCurrentOrgUnit(ctx));

		String curOrgId = currentCostUnit.getId().toString();

		logger.info("FDCBillControllerBean.recycleNumber().curOrgId:" + curOrgId);
		logger.info("FDCBillControllerBean.recycleNumber().info:" + info);
		logger.info("FDCBillControllerBean.recycleNumber().info.getNumber():" + info.getNumber());
		logger.info("������֯Ϊ��" + curOrgId + " �ı������");

		if (info.getNumber() != null && info.getNumber().length() > 0) {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);

			boolean isExistCodingRule = false;
			boolean isUseIntermitNumber = false;
			boolean flag = false;

			isExistCodingRule = iCodingRuleManager.isExist(info, curOrgId);
			if (isExistCodingRule) {
				isUseIntermitNumber = iCodingRuleManager.isUseIntermitNumber(info, curOrgId);
			}

			flag = isExistCodingRule && isUseIntermitNumber;
			logger.info("iCodingRuleManager.isExist(info, curOrgId):" + isExistCodingRule);
			logger.info("iCodingRuleManager.isUseIntermitNumber(info, curOrgId):" + isUseIntermitNumber);
			logger.info("iCodingRuleManager.isExist(info, curOrgId) && iCodingRuleManager.isUseIntermitNumber(info, curOrgId):" + flag);

			if (flag) {
				iCodingRuleManager.recycleNumber(info, curOrgId, info.getNumber());
			}
		} else {
			logger.info("if(info.getNumber()!=null&&info.getNumber().length()>0),IF�ж�δ����");
		}

		logger.info("===============================================================================");
		logger.info("FDCBillControllerBean.recycleNumber(),end");
		logger.info("===============================================================================");
	}

    //ȡ���룬�������������ð����� 
	protected String getBindingProperty() {
		return null;
	}

	/**
	 * ����
	 */
	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		setBillStatus(ctx, BOSUuid.read(pk.toString()), FDCBillStateEnum.INVALID);
	}
	
	/**
	 * ��Ч
	 */
	protected void _cancelCancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		setBillStatus(ctx, BOSUuid.read(pk.toString()), FDCBillStateEnum.AUDITTED);
		
	}
	
	/**
	 * 
	 * ��������������
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.app.AbstractFDCBillControllerBean#_audit(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	protected void _cancel(Context ctx, IObjectPK[] pkArray) throws BOSException,
			EASBizException {

		for (int i = 0; i < pkArray.length; i++) {
			
			_cancel(ctx, pkArray[i]);

		}

	}
	
	/**
	 * 
	 * ������������Ч
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.app.AbstractFDCBillControllerBean#_audit(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	protected void _cancelCancel(Context ctx, IObjectPK[] pkArray) throws BOSException,
			EASBizException {

		for (int i = 0; i < pkArray.length; i++) {

			_cancelCancel(ctx, pkArray[i]);

		}

	}

	//���ݱ༭�����ʼ���ݴ����ȷ���
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		Map initMap = new HashMap();
		
		OrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		
		//������Ŀ
		String projectId = (String) paramMap.get("projectId");
		
		CurProjectInfo curProjectInfo = null;
		initProject( ctx,  paramMap, initMap);
		if(initMap.get(FDCConstants.FDC_INIT_PROJECT)!=null){
			curProjectInfo = (CurProjectInfo)initMap.get(FDCConstants.FDC_INIT_PROJECT);
			projectId = curProjectInfo.getId().toString();
			paramMap.put("projectId",projectId);
			initMap.put("projectId",projectId);
		}
		
		//������Ŀ��Ӧ����֯
		String orgUnitId = null;
		if(curProjectInfo!=null &&  curProjectInfo.getCostCenter()!=null){
			orgUnitId = curProjectInfo.getCostCenter().getId().toString();
		}else{
			orgUnitId = orgUnit.getId().toString();
		}
		
		//��õ�ǰ��֯		
		FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getLocalInstance(ctx)
			.getFullOrgUnitInfo(new ObjectUuidPK(orgUnitId));
		
		initMap.put(FDCConstants.FDC_INIT_ORGUNIT,orgUnitInfo);
		
		String comId = null;
		if(curProjectInfo!=null){
			comId = curProjectInfo.getFullOrgUnit().getId().toString();
		}else{
			comId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		}
		
		//������֯
		CompanyOrgUnitInfo company =GlUtils.getCurrentCompany(ctx,comId,null,false);		
		initMap.put(FDCConstants.FDC_INIT_COMPANY,company);
		
		//��λ��
		initMap.put(FDCConstants.FDC_INIT_CURRENCY,company.getBaseCurrency());
		
		//��ǰ�ڼ�
		if(projectId!=null ){
			if( paramMap.get("isCost")==null || ((Boolean)paramMap.get("isCost")).booleanValue() == true ){
				initPeriod( ctx, projectId, curProjectInfo,comId, initMap,true );
			}else{
				initPeriod( ctx, projectId,curProjectInfo,comId,  initMap,false );
			}
		}
	
		//����
		Date serverDate = new Date();
		initMap.put("serverDate",serverDate);
		
		return initMap;
	}
	
	//��ʼ��������Ŀ
	protected void  initProject(Context ctx, Map paramMap,Map initMap) throws EASBizException, BOSException{
		String projectId = (String) paramMap.get("projectId");
		CurProjectInfo curProjectInfo = null;		
		if(paramMap.get("contractBillId")!=null) {
			//��ͬ����
			String contractBillId = (String)paramMap.get("contractBillId");
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("*");
			selector.add("contractType.isWorkLoadConfirm");
			selector.add("curProject.name");
			selector.add("curProject.number");
			selector.add("curProject.longNumber");
			selector.add("curProject.codingNumber");
			selector.add("curProject.displayName");
			selector.add("curProject.parent.id");
			selector.add("curProject.parent.number");
			selector.add("curProject.parent.name");
			selector.add("curProject.fullOrgUnit.name");
			selector.add("curProject.fullOrgUnit.code");
			selector.add("curProject.costCenter.id"); //modified by zhaoqin for R130711-0070 on 2013/12/3
			selector.add("curProject.CU.name");
			selector.add("curProject.CU.number");
			selector.add("curProject.CU.code");
			selector.add("currency.number");
			selector.add("currency.name");
			selector.add("respDept.number");
			selector.add("respDept.name");
			selector.add("partB.number");
			selector.add("partB.name");
			
			BOSObjectType  contractType=new ContractBillInfo().getBOSType();
			BOSObjectType  noTextContractType=new ContractWithoutTextInfo().getBOSType();
			if(BOSUuid.read(contractBillId).getType().equals(contractType)){
				ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
				getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
				initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
				
				//������Ŀ
				curProjectInfo=contractBill.getCurProject();
			}else if(BOSUuid.read(contractBillId).getType().equals(noTextContractType)){
				ContractWithoutTextInfo contractBill = ContractWithoutTextFactory.getLocalInstance(ctx).
				getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
				//initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
				
				//������Ŀ
				curProjectInfo=contractBill.getCurProject();
			}			
		}
		
		if(curProjectInfo==null&&projectId != null) {
			SelectorItemCollection selects = new SelectorItemCollection();
			selects.add("*");
			selects.add("isWholeAgeStage");
			selects.add("parent.id");
			selects.add("parent.number");
			selects.add("parent.name");
			selects.add("fullOrgUnit.name");
			selects.add("fullOrgUnit.code");
			selects.add("costCenter");
			selects.add("CU.name");
			selects.add("CU.number");
			selects.add("CU.code");
			curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId),selects);
		}
		initMap.put(FDCConstants.FDC_INIT_PROJECT,curProjectInfo);
	}
	
	//��ʼ���ڼ�
	protected void  initPeriod(Context ctx,String projectId, CurProjectInfo curProjectInfo,String comId,Map initMap,boolean isCost ) throws EASBizException, BOSException{
		
		//����
		Date bookedDate = new Date();
		
		//�Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		
		if(isInCore){
			//�ж��Ƿ񱻶���
			boolean isFreeze = FDCUtils.isFreeze(ctx,projectId,isCost);
			initMap.put(FDCConstants.FDC_INIT_ISFREEZE,Boolean.valueOf(isFreeze));
			
			//�ڼ�
			ProjectPeriodStatusUtil._save(ctx,new ObjectUuidPK(projectId),curProjectInfo);
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,projectId,isCost);
			
			if(bookedPeriod!=null){
				initMap.put(FDCConstants.FDC_INIT_BOOKEDPERIOD,bookedPeriod);		
				
				PeriodInfo curPeriod = null;
				if(isFreeze){
					curPeriod = PeriodUtils.getNextPeriodInfo(ctx,bookedPeriod);
				}else{
					curPeriod = bookedPeriod;
				}
				
				initMap.put(FDCConstants.FDC_INIT_PERIOD,curPeriod);
				
	
				if(bookedDate.before(curPeriod.getBeginDate())){
					bookedDate = curPeriod.getBeginDate();
				}
				if(bookedDate.after(curPeriod.getEndDate())){
					bookedDate = curPeriod.getEndDate();
				}		
	
			}
		}else{
			//�ڼ�
			PeriodInfo bookedPeriod = PeriodUtils.getPeriodInfo(ctx ,bookedDate ,new ObjectUuidPK(comId));
			initMap.put(FDCConstants.FDC_INIT_PERIOD,bookedPeriod);
			initMap.put(FDCConstants.FDC_INIT_BOOKEDPERIOD,bookedPeriod);		
		}
		
		initMap.put(FDCConstants.FDC_INIT_DATE,bookedDate);
	}
	
	//��ʱ����ȡ��ʼ�����ݷ���
	protected Map _fetchFilterInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
	
		return null;
	}

	//����ܷ��ύ
	protected boolean _checkCanSubmit(Context ctx, String id) throws BOSException, EASBizException {

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		if(_exists(ctx, new ObjectUuidPK(id))){
			FDCBillInfo billInfo = (FDCBillInfo)this.getValue(ctx,new ObjectUuidPK(id),selector);
			if(FDCBillStateEnum.AUDITTED.equals(billInfo.getState())
					|| FDCBillStateEnum.AUDITTING.equals(billInfo.getState())){
				return false;	
			}else{
				return true;
			}
		}
		
		return true;
	}

	//���õ��ݵ�һЩ����,�ɱ����ĺͣãն��ӹ�����Ŀ���
	protected void setProps (Context ctx, FDCBillInfo fDCBillInfo) throws EASBizException, BOSException {

		if(fDCBillInfo.get("curProject")!=null && (fDCBillInfo.getOrgUnit() == null ||fDCBillInfo.getCU() == null )) {
			CurProjectInfo projectInfo =(CurProjectInfo)fDCBillInfo.get("curProject");
			if( projectInfo.getCostCenter()==null || projectInfo.getCU()==null){
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("CU.id");
				sic.add("costCenter.id");				
				projectInfo = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(new ObjectUuidPK(projectInfo.getId().toString()),sic);
			}
			if(fDCBillInfo.getOrgUnit() == null && projectInfo.getCostCenter()!=null) {
				FullOrgUnitInfo orgUnit = projectInfo.getCostCenter().castToFullOrgUnitInfo();
				fDCBillInfo.setOrgUnit(orgUnit);
			}
			if(fDCBillInfo.getCU() == null) {
				CtrlUnitInfo currentCtrlUnit = projectInfo.getCU();
				fDCBillInfo.setCU(currentCtrlUnit);
			}
		}		
	}


	protected void _setRespite(Context ctx, BOSUuid billId, boolean value)
			throws BOSException, EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();
		billInfo.setId(billId);
		billInfo.setIsRespite(value);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("isRespite");
		_updatePartial(ctx, billInfo, selector);
		/**
		 * 
		 * EntityObjectInfo entity = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx).getEntity(BOSUuid.read(id).getType());
		DataTableInfo table = entity.getTable();
		String tableName=table.getName();
		 * 
		 * 
		 * 
		 */
	}


	protected void _setRespite(Context ctx, List ids, boolean value)
			throws BOSException, EASBizException {
		for(int i =0; i<ids.size();i++){
			_setRespite(ctx,BOSUuid.read(ids.get(i).toString()),value);
		}
	}

	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////

	/**
	 * ������ �����ֶβ������������ֵ�����ı䣬����Ҫ���»�ȡ����
	 * @param ctx
	 * @param model
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author��skyiter_wang
	 * @CreateTime��2013-10-10
	 */
	protected boolean reHandleIntermitNumberByProperty(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		if (!isNeedReHandleIntermitNumberByProperty()) {
			return false;
		}

		FDCBillInfo newInfo = (FDCBillInfo) model;
		if (newInfo.getId() == null || !this._exists(ctx, new ObjectUuidPK(newInfo.getId())) || FdcStringUtil.isBlank(newInfo.getNumber())) {
			handleIntermitNumber(ctx, newInfo);

			return true;
		}

		//////////////////////////////////////////////////////////////////////

		String bindingProperty = getBindingProperty();
		boolean isExistBindingProperty = FdcStringUtil.isNotBlank(bindingProperty);
		logger.info("bindingProperty:" + bindingProperty);
		
		//////////////////////////////////////////////////////////////////////

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		FDCBillInfo oldInfo = (FDCBillInfo) getValueForRecycleNumber(ctx, new ObjectUuidPK(newInfo.getId()));
		String currentOrgId = getOrgUnitId(ctx, oldInfo);

		// ////////////////////////////////////////////////////////////////////

		Object bindingPropertyOldValue = FdcObjectValueUtil.get(oldInfo, bindingProperty);
		Object bindingPropertyNewValue = FdcObjectValueUtil.get(model, bindingProperty);
		boolean isChangedBindingProperty = !FdcObjectUtil.isEquals(bindingPropertyOldValue, bindingPropertyNewValue);

		logger.info("bindingPropertyOldValue:" + bindingPropertyOldValue);
		logger.info("bindingPropertyNewValue:" + bindingPropertyNewValue);
		logger.info("isChangedBindingProperty:" + isChangedBindingProperty);

		// ////////////////////////////////////////////////////////////////////

		logger.info("oldInfo:" + oldInfo);
		logger.info("newInfo:" + model);

		// ////////////////////////////////////////////////////////////////////

		boolean isRecycleParentOrgNumber = isRecycleParentOrgNumber();
		logger.info("isRecycleParentOrgNumber:" + isRecycleParentOrgNumber);
		ArrayList orgIdList = new ArrayList();
		// ��ǰ��֯ID����ѭ���б���ǰ��
		orgIdList.add(currentOrgId);
		if (isRecycleParentOrgNumber) {
			ContractUtil.findParentOrgUnitIdToList(ctx, currentOrgId, orgIdList);
		}
		// ����������е��ظ�ֵ��Nullֵ
		FdcCollectionUtil.clearDuplicateAndNull(orgIdList);
		logger.info("orgIdList:" + orgIdList);

		//////////////////////////////////////////////////////////////////////

		boolean flag = false;
		for (int i = 0, size = orgIdList.size(); i < size; i++) {
			String orgId = orgIdList.get(i).toString();
			logger.info("������֯Ϊ��" + orgId + "�ı������");

			// �Ƿ���ձ���
			flag = FdcCodingRuleUtil.isRecycleNumber(iCodingRuleManager, oldInfo, orgId, bindingProperty);
			if (!flag) {
				continue;
			}

			// ////////////////////////////////////////////////////////////////////

			// �����Է����仯
			if (isChangedBindingProperty) {
				// ���ձ���
				if (FdcCodingRuleUtil.recycleNumber(iCodingRuleManager, oldInfo, orgId, bindingProperty, "", oldInfo.getNumber())) {
				}

				// �������ɱ���
				handleIntermitNumber(ctx, newInfo);

				// ����ѭ��
				break;
			}

			// ////////////////////////////////////////////////////////////////////

			// ȡ�ñ�������¼
			CodingRuleEntryCollection codingRuleEntryCollection = FdcCodingRuleUtil.getCodingRuleEntryCollection(ctx,
					oldInfo, orgId, bindingProperty);

			// ���������������
			Map valueAttributeMap = FdcObjectCollectionUtil.parsePropertyMap(codingRuleEntryCollection, "valueAttribute");
			Set valueAttributeSet = valueAttributeMap.keySet();

			if (FdcCollectionUtil.isNotEmpty(valueAttributeSet)) {
				String[] selectors = (String[]) valueAttributeSet.toArray(new String[0]);
				SelectorItemCollection sic = FdcEntityViewUtil.getSelector(selectors);
				sic.add(new SelectorItemInfo("*"));
				if (isExistBindingProperty) {
					sic.add(new SelectorItemInfo(bindingProperty));
				}
				oldInfo = (FDCBillInfo) _getValue(ctx, new ObjectUuidPK(newInfo.getId()), sic);

				Map newValueMap = new HashMap();
				Map oldValueMap = new HashMap();
				for (Iterator iterator = valueAttributeSet.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object newValue = FdcObjectValueUtil.get(model, key) + "";
					Object oldValue = FdcObjectValueUtil.get(oldInfo, key) + "";

					newValueMap.put(key, newValue);
					oldValueMap.put(key, oldValue);
				}

				//��������������Է����仯
				if (!ObjectUtils.equals(newValueMap, oldValueMap)) {
					//���ձ���
					if (FdcCodingRuleUtil.recycleNumber(iCodingRuleManager, oldInfo, orgId, bindingProperty, "", oldInfo.getNumber())) {
					}

					// �������ɱ���
					handleIntermitNumber(ctx, newInfo);

					// ����ѭ��
					break;
				}
			}
		}

		return true;
	}

	/**
	 * ����Number����������˱������֧�ֶϺŵĻ�
	 * @param ctx
	 * @param pk
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 */
	protected void recycleNumber2(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		logger.info("===============================================================================");
		logger.info("FDCBillControllerBean.recycleNumber(),start");
		logger.info("===============================================================================");

		//////////////////////////////////////////////////////////////////////

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		FDCBillInfo oldInfo = getValueForRecycleNumber(ctx, pk);
		String currentOrgId = getOrgUnitId(ctx, oldInfo);
		String number = oldInfo.getNumber();

		//////////////////////////////////////////////////////////////////////

		logger.info("FDCBillControllerBean.recycleNumber2().curOrgId:" + currentOrgId);
		logger.info("FDCBillControllerBean.recycleNumber2().info:" + oldInfo);
		logger.info("FDCBillControllerBean.recycleNumber2().info.getNumber():" + number);
		if (FdcStringUtil.isBlank(number)) {
			logger.info("����Ϊ�գ�������");
		}

		//////////////////////////////////////////////////////////////////////

		boolean isRecycleParentOrgNumber = isRecycleParentOrgNumber();
		logger.info("isRecycleParentOrgNumber:" + isRecycleParentOrgNumber);
		ArrayList orgIdList = new ArrayList();
		// ��ǰ��֯ID����ѭ���б���ǰ��
		orgIdList.add(currentOrgId);
		if (isRecycleParentOrgNumber) {
			ContractUtil.findParentOrgUnitIdToList(ctx, currentOrgId, orgIdList);
		}
		// ����������е��ظ�ֵ��Nullֵ
		FdcCollectionUtil.clearDuplicateAndNull(orgIdList);
		logger.info("orgIdList:" + orgIdList);
		
		String bindingProperty = getBindingProperty();
		boolean isExistBindingProperty = FdcStringUtil.isNotBlank(bindingProperty);
		logger.info("bindingProperty:" + bindingProperty);
		logger.info("isExistBindingProperty:" + isExistBindingProperty);

		boolean flag = false;
		for (int i = 0, size = orgIdList.size(); i < size; i++) {
			String orgId = orgIdList.get(i).toString();
			logger.info("������֯Ϊ��" + orgId + "�ı������");

			//�Ƿ���ձ���
			flag = FdcCodingRuleUtil.isRecycleNumber(iCodingRuleManager, oldInfo, orgId, bindingProperty);

			if (flag) {
				//������ճɹ��˾�����ѭ��
				if (FdcCodingRuleUtil.recycleNumber(iCodingRuleManager, oldInfo, orgId, bindingProperty, "", oldInfo.getNumber())) {
					break;
				}
			}
		}

		logger.info("===============================================================================");
		logger.info("FDCBillControllerBean.recycleNumber2(),end");
		logger.info("===============================================================================");
	}

	/**
	 * �����������ֶβ������������ֵ�����ı䣬����Ҫ���»�ȡ����
	 * @return
	 * @Author��skyiter_wang
	 * @CreateTime��2013-10-10
	 */
	protected String getOrgUnitId(Context ctx, IObjectValue model) {
		FDCBillInfo info = (FDCBillInfo) model;

		OrgUnitInfo orgUnitInfo = info.getOrgUnit();
		if (null == orgUnitInfo) {
			orgUnitInfo = ContextUtil.getCurrentOrgUnit(ctx);
		}
		String orgId = orgUnitInfo.getId().toString();

		return orgId;
	}

	/**
	 * �����������ֶβ������������ֵ�����ı䣬����Ҫ���»�ȡ����
	 * @return
	 * @Author��skyiter_wang
	 * @CreateTime��2013-10-10
	 */
	protected boolean isNeedReHandleIntermitNumberByProperty() {
		return false;
	}

	/**
	 * �������Ƿ�����ϼ���֯���ݵı������
	 * @return
	 * @Author��skyiter_wang
	 * @CreateTime��2013-10-10
	 */
	protected boolean isRecycleParentOrgNumber() {
		return false;
	}

	/**
	 * ������ȡ�û��ձ���Value
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @Author��skyiter_wang
	 * @CreateTime��2013-10-10
	 */
	protected FDCBillInfo getValueForRecycleNumber(Context ctx, IObjectPK pk) throws EASBizException, BOSException {
		FDCBillInfo info = null;

		SelectorItemCollection sic = getSeletorForRecycleNumber();
		if (FdcObjectCollectionUtil.isEmpty(sic)) {
			info = (FDCBillInfo) _getValue(ctx, pk);
		} else {
			info = (FDCBillInfo) _getValue(ctx, pk, sic);
		}

		return info;
	}

	/**
	 * ������ȡ�û��ձ���Seletor
	 * @return
	 * @author��skyiter_wang
	 * @createDate��2013-10-16
	 */
	protected SelectorItemCollection getSeletorForRecycleNumber() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("*"));

		String bindingProperty = getBindingProperty();
		// �Ƿ���ڰ�����
		boolean isExistBindingProperty = FdcStringUtil.isNotBlank(bindingProperty);
		if (isExistBindingProperty) {
			sic.add(new SelectorItemInfo(bindingProperty));
		}

		return sic;
	}

	/**
	 * ������ƥ�������򣬻�ȡ���룬���ñ���
	 * 
	 * @param ctx
	 * @param info
	 * @param orgId
	 * @param bindingProperty
	 * @param iCodingRuleManager
	 * @param count
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-20
	 */
	protected boolean setNumber(Context ctx, FDCBillInfo info, String orgId, String bindingProperty,
			ICodingRuleManager iCodingRuleManager, int count) throws BOSException, EASBizException {

		if (FdcCodingRuleUtil.isExist(iCodingRuleManager, info, orgId, bindingProperty)) {
			// ��ȡ����������
			CodingRuleInfo codingRuleInfo = FdcCodingRuleUtil.getCodingRule(iCodingRuleManager, info, orgId,
					bindingProperty);
			// ���֧���޸ľ�Ҫ�������Ƿ���ϵ�ǰ���õı������Ĺ���

			// ��ȡ��������Ƿ�֧���޸�
			boolean flag = FdcCodingRuleUtil.isAllowModifyNumber(iCodingRuleManager, info, orgId, bindingProperty);
			// �ж��Ƿ��޸��˱���,�Ƿ�Ĵ���˳���
			if (flag) {
				// CodingRuleFactory.getLocalInstance(ctx).checkModifiedNumber(info, codingRuleInfo,
				// info.getNumber());
			} else {
				// �����������ʾ
				if (codingRuleInfo.isIsAddView()) {
					// ����Ϊ�յ�ʱ��Ҫ��ȡ����
					/*
					 * modified by zhaojie for BT868215 on 2015-03-13 update 'isNotBlank' to
					 * 'isBlank'
					 */
					if (FdcStringUtil.isBlank(info.getNumber())) {
						String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId, bindingProperty);
						
						/* modified by zhaoqin for R140213-0169 on 2014/02/17 */
						//info.setNumber(number);
						info.setNumber(convertNumber(number));
					} else {
						// �����벻Ϊ�յ�ʱ��count=0��ʱ����Ҫ���»�ȡ���룬������>0��ʱ��֤�������ظ�Ҫ���»�ȡ�µı���
						if (count > 0) {
							String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId,
									bindingProperty);
							
							/* modified by zhaoqin for R140213-0169 on 2014/02/17 */
							//info.setNumber(number);
							info.setNumber(convertNumber(number));
						}
					}
				} else {
					// ������ǲ�����Ϻţ�ÿ�ζ�Ҫ���ֻ�ȡ
					String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId, bindingProperty);
					
					/* modified by zhaoqin for R140213-0169 on 2014/02/17 */
					//info.setNumber(number);
					info.setNumber(convertNumber(number));
				}
			}

			return true;
		}

		return false;
	}

	// ////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////

	/**
	 * ת������number����"!"��Ϊ"." - R140213-0169
	 * 
	 * @author zhaoqin
	 * @date 2014/02/14
	 */
	private String convertNumber(String orgNumber){
		return orgNumber.replaceAll("!", ".");
	}
}