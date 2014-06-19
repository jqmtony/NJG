package com.kingdee.eas.fdc.basedata.app;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
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
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.port.pm.contract.ContractBillFactory;
import com.kingdee.eas.port.pm.contract.ContractBillInfo;
import com.kingdee.eas.port.pm.utils.FDCUtils;
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

		if(fDCBillInfo.getState() == null)
		fDCBillInfo.setState(FDCBillStateEnum.SAVED);

//		handleIntermitNumber(ctx,fDCBillInfo);//����Ϻ�
		//���õ��ݵ�һЩ����
		setPropsForBill(ctx, fDCBillInfo);

		//����Ϻ�
		if (fDCBillInfo.getId() == null ||
				!this._exists(ctx, new ObjectUuidPK(fDCBillInfo.getId()))) {
			handleIntermitNumber(ctx, fDCBillInfo);
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
	
		//����Ϻ�
		 if (fDCBillInfo.getId() == null ||
				!this._exists(ctx, new ObjectUuidPK(fDCBillInfo.getId()))||
				fDCBillInfo.getNumber() == null || fDCBillInfo.getNumber().length()==0) {
			handleIntermitNumber(ctx, fDCBillInfo);
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
		
		//����Ϻ�
		 if (FDCBillInfo.getId() == null ||
				!this._exists(ctx, new ObjectUuidPK(FDCBillInfo.getId()))) {
			handleIntermitNumber(ctx, FDCBillInfo);
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
		
		//����Ϻ�
		 if (FDCBillInfo.getId() == null ||
				!this._exists(ctx, new ObjectUuidPK(FDCBillInfo.getId()))) {
			handleIntermitNumber(ctx, FDCBillInfo);
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
					fDCBillInfo.setOrgUnit(orgUnit);
				}
			}else{
				FullOrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx)
				.castToFullOrgUnitInfo();
		
				fDCBillInfo.setOrgUnit(orgUnit);
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
			ProjectInfo projectInfo =(ProjectInfo)fDCBillInfo.get("curProject");
//			PeriodInfo period = FDCUtils.getCurrentPeriod(ctx,projectInfo.getId().toString(),isCost());			
//			fDCBillInfo.setPeriod(period);
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
//			throw new ContractException("");
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
	private void checkNumberDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
		if(!isUseNumber()) return;
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
		filter.getFilterItems()
				.add(new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit().getId()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
//			throw new ContractException(ContractException.NUMBER_DUP);
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
		do {
			//��������ظ�����ȡ����
			handleIntermitNumber1(ctx, info);
			
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
		}while (_exists(ctx, filter) && i<1000);
	}
	
	protected void handleIntermitNumber1(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException
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
		
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {

		for (int i = 0; i < arrayPK.length; i++) {
			recycleNumber(ctx, arrayPK[i]);
		}
		
		super._delete(ctx, arrayPK);

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
		FDCBillInfo info = (FDCBillInfo)_getValue(ctx, pk);
		//------ ������֯�»�ȡ�ɱ�����Ϊ�յĴ��� zhicheng_jin 090319
		OrgUnitInfo currentCostUnit = ContextUtil.getCurrentOrgUnit(ctx);		
		if(currentCostUnit == null){
			currentCostUnit = ContextUtil.getCurrentSaleUnit(ctx);
		}
		//-------- over --------
		String curOrgId = currentCostUnit.getId().toString();
		if(info.getNumber()!=null&&info.getNumber().length()>0){
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	        if (iCodingRuleManager.isExist(info, curOrgId) && iCodingRuleManager.isUseIntermitNumber(info, curOrgId)) {
	            iCodingRuleManager.recycleNumber(info, curOrgId, info.getNumber());
	        }
		}
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
		
		ProjectInfo curProjectInfo = null;
		initProject( ctx,  paramMap, initMap);
		if(initMap.get(FDCConstants.FDC_INIT_PROJECT)!=null){
			curProjectInfo = (ProjectInfo)initMap.get(FDCConstants.FDC_INIT_PROJECT);
			projectId = curProjectInfo.getId().toString();
			paramMap.put("projectId",projectId);
			initMap.put("projectId",projectId);
		}
		
		//������Ŀ��Ӧ����֯
		String orgUnitId = null;
		if(curProjectInfo!=null &&  curProjectInfo.getCompany()!=null){
			orgUnitId = curProjectInfo.getCompany().getId().toString();
		}else{
			orgUnitId = orgUnit.getId().toString();
		}
		
		//��õ�ǰ��֯		
		FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getLocalInstance(ctx)
			.getFullOrgUnitInfo(new ObjectUuidPK(orgUnitId));
		
		initMap.put(FDCConstants.FDC_INIT_ORGUNIT,orgUnitInfo);
		
		String comId = null;
		if(curProjectInfo!=null){
			comId = curProjectInfo.getCompany().getId().toString();
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
		ProjectInfo curProjectInfo = null;		
		if(paramMap.get("contractBillId")!=null) {
			//��ͬ����
			String contractBillId = (String)paramMap.get("contractBillId");
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("*");
			selector.add("curProject.name");
			selector.add("curProject.number");
			selector.add("curProject.longNumber");
			selector.add("curProject.codingNumber");
			selector.add("curProject.displayName");
			selector.add("curProject.parent.id");
			selector.add("curProject.fullOrgUnit.name");
			selector.add("curProject.fullOrgUnit.code");
			selector.add("curProject.costCenter");//bug
			selector.add("curProject.CU.name");
			selector.add("curProject.CU.number");
			selector.add("curProject.CU.code");
			selector.add("currency.number");
			selector.add("currency.name");
			selector.add("currency.isWholeAgeStage");
			selector.add("respDept.number");
			selector.add("respDept.name");
			selector.add("partB.number");
			selector.add("partB.name");
			
			BOSObjectType  contractType=new ContractBillInfo().getBOSType();
//			BOSObjectType  noTextContractType=new ContractWithoutTextInfo().getBOSType();
			if(BOSUuid.read(contractBillId).getType().equals(contractType)){
				ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
				getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
				initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
				
				//������Ŀ
				curProjectInfo=contractBill.getCurProject();
				if(contractBill.getCurProject()!=null) {
					SelectorItemCollection selects = new SelectorItemCollection();
					selects.add("*");
					selects.add("company.id");
					selects.add("company.name");
					selects.add("company.number");
					curProjectInfo = ProjectFactory.getLocalInstance(ctx).getProjectInfo(new ObjectUuidPK(contractBill.getCurProject().getId()),selects);
				}
			}
//			else if(BOSUuid.read(contractBillId).getType().equals(noTextContractType)){
//				ContractWithoutTextInfo contractBill = ContractWithoutTextFactory.getLocalInstance(ctx).
//				getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
//				//initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
//				
//				//������Ŀ
//				curProjectInfo=contractBill.getCurProject();
//			}			
		}
		
		if(curProjectInfo==null&&projectId != null) {
			SelectorItemCollection selects = new SelectorItemCollection();
			selects.add("*");
			selects.add("company.id");
			selects.add("company.name");
			selects.add("company.number");
			curProjectInfo = ProjectFactory.getLocalInstance(ctx).getProjectInfo(new ObjectUuidPK(projectId),selects);
		}
		initMap.put(FDCConstants.FDC_INIT_PROJECT,curProjectInfo);
	}
	
	//��ʼ���ڼ�
	protected void  initPeriod(Context ctx,String projectId, ProjectInfo curProjectInfo,String comId,Map initMap,boolean isCost ) throws EASBizException, BOSException{
		
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
	
}