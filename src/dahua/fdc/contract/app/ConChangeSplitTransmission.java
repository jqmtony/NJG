package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

public class ConChangeSplitTransmission extends AbstractDataTransmission {
	
	private static String resource = "com.kingdee.eas.fdc.contract.ContractTransResource";

	//�����ֶ���
	ConChangeSplitInfo conChangeSplitInfo = null;
	//�����ַ�¼����
	ConChangeSplitEntryInfo conChangeSplitEntryInfo = null;
	//��Ŀ���̶���
	CurProjectInfo curProjectInfo = null;
	//��֯����
	FullOrgUnitInfo fullOrgUnitInfo = null;
	//���ǩ֤ȷ��
	ContractChangeBillInfo contractChangeBillInfo = null;
	//����˶���
	UserInfo createUserInfo = null;
	//�����˶���
	UserInfo auditorUserInfo = null;
	//��������ɱ���Ŀ����
	CostAccountInfo costAccountInfo = null;
	//��Ʒ���Ͷ���
	ProductTypeInfo productTypeInfo = null;
	
	// �������֮��
	private BigDecimal sum = FDCHelper.ZERO;
	
	// �����ֽ��
	private BigDecimal changeSplitAmount = null;
	
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return ConChangeSplitFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}
	/**
	 * ����:����hashtable���������ת��Ϊһ��EAS�е�CoreBaseInfo����
	 */	
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		// TODO Auto-generated method stub
		try {
//			for (int i = 0; i < hsData.size(); i++) {
//				Hashtable lineData = (Hashtable) hsData.get(new Integer(i));
//				// ���α�Ϊ0����ʾ��һ����¼����ʱҪƴ��һ����ͷ��
//				if (i == 0) {
					conChangeSplitInfo = transmitHead(hsData, ctx);
//				}
				if (conChangeSplitInfo == null) {
					return null;
				}
				ConChangeSplitEntryInfo entryInfo = transmitEntry(hsData, ctx);
	            int seq = conChangeSplitInfo.getEntrys().size() + 1;
	            entryInfo.setSeq(seq);
	            entryInfo.setParent(conChangeSplitInfo);
	            conChangeSplitInfo.getEntrys().add(entryInfo);
	            // ���ò��״̬
	            StringBuffer sqlStr = new StringBuffer("update T_CON_ContractChangeBill set FSplitState = '");
		        if(FDCHelper.ZERO.compareTo(sum) == 0) {
		        	conChangeSplitInfo.setSplitState(CostSplitStateEnum.NOSPLIT);
		        	sqlStr.append(CostSplitStateEnum.NOSPLIT_VALUE).append("'");
		        } else if(changeSplitAmount.compareTo(sum) == 1) {
		        	conChangeSplitInfo.setSplitState(CostSplitStateEnum.PARTSPLIT);
		        	sqlStr.append(CostSplitStateEnum.PARTSPLIT_VALUE).append("'");
		        } else {
		        	conChangeSplitInfo.setSplitState(CostSplitStateEnum.ALLSPLIT);
		        	sqlStr.append(CostSplitStateEnum.ALLSPLIT_VALUE).append("'");
		        }
		        sqlStr.append(" where fid='").append(contractChangeBillInfo.getId().toString()).append("'");
		        FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(sqlStr.toString());
		        sqlBuilder.executeUpdate(ctx);
		       
		        // �ֶ����棬��save���ܸ�����Ӧ��ȫ��Ŀ��̬�ɱ���ʹ������ɱ�Ԥ���
		        getController(ctx).save(conChangeSplitInfo);
//			}
		} catch (TaskExternalException e) {
			conChangeSplitInfo = null;
			throw e;
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		} catch (EASBizException e) {
			throw new TaskExternalException(e.getMessage());
		}		
		
		return conChangeSplitInfo;
	}
	
	private ConChangeSplitInfo transmitHead(Hashtable lineData, Context ctx) throws TaskExternalException{
		
		//��֯����
		String FOrgUnitLongNumber = (String) ((DataToken) lineData.get("FOrgUnit_longNumber")).data;
		//������Ŀ����
		String fCurProjectLongNumber = (String) ((DataToken) lineData.get("FCurProject_longNumber")).data;
		//���ǩ֤ȷ�ϱ���
		String fContractChangeNumber = (String) ((DataToken) lineData.get("FContractChange_number")).data;
		//���ǩ֤ȷ������
		String fContractChangeName = (String) ((DataToken) lineData.get("FContractChange_name")).data;
		//������
		String fContractChangeAmount = (String) ((DataToken) lineData.get("FContractChange_amount")).data;
		//�Ѳ�ֽ��
		String fAmount = (String) ((DataToken) lineData.get("FAmount")).data;
		//δ��ֽ��
		String fUnSplitAmount = (String) ((DataToken) lineData.get("FUnSplitAmount")).data;
		//����˱���
		String fCreatorNumber = (String) ((DataToken) lineData.get("FCreator_number")).data;
		//���ʱ��
		String fCreateTime = (String) ((DataToken) lineData.get("FCreateTime")).data;
		//�����˱���
		String fAuditorNumber = (String) ((DataToken) lineData.get("FAuditor_number")).data;
		//����ʱ��
		String fAuditTime = (String) ((DataToken) lineData.get("FAuditTime")).data;
		//״̬
		String fState = (String) ((DataToken) lineData.get("FState")).data;
		
		/*
		 * �ж��Ƿ�Ϊ��
		 */
		if (StringUtils.isEmpty(fCurProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCurProjectCodingNumberNotNull"));
		}
		if (StringUtils.isEmpty(fContractChangeNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fContractChangeNumberNotNull"));
		}
		if (StringUtils.isEmpty(fCreatorNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCreatorNumberNotNull"));
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCreateTimeNotNull"));
		}
		
		try {
			// ������Ŀ����
			FilterInfo curFilter = new FilterInfo();
			curFilter.getFilterItems().add(new FilterItemInfo("LongNumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo curView = new EntityViewInfo();
			curView.setFilter(curFilter);
			CurProjectCollection curcoll = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curView);
			if(curcoll.size()>0){
				curProjectInfo = curcoll.get(0);
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_fCurProjectCodingNumber1")+getResource(ctx, "Import_NOTNULL"));
			}
			// ��֯����
			CostCenterOrgUnitInfo costCenterOrgUnit = curProjectInfo.getCostCenter();
			if (costCenterOrgUnit == null) {
				throw new TaskExternalException(getResource(ctx,"Import_costcenter"));
			} else {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", costCenterOrgUnit.getId().toString()));
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				CostCenterOrgUnitCollection collection = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(view);
				if(collection.size() > 0) {
					costCenterOrgUnit = collection.get(0);
				} 
			}

			// ���ǩ֤ȷ��
			FilterInfo contractChangeFilter = new FilterInfo();
			contractChangeFilter.getFilterItems().add(new FilterItemInfo("number", fContractChangeNumber));
			contractChangeFilter.getFilterItems().add(new FilterItemInfo("curproject.id", curProjectInfo.getId().toString()));
			contractChangeFilter.setMaskString("#0 and #1");
			EntityViewInfo contractChangeView = new EntityViewInfo();
			contractChangeView.setFilter(contractChangeFilter);
			ContractChangeBillCollection contractChangeBillColl = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(contractChangeView);
			if (contractChangeBillColl.size() > 0) {
				contractChangeBillInfo = contractChangeBillColl.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx,"Import_fContractChangeNumberNotE"));
			}
			// ��ѯ��ͷ�����Ƿ��Ѿ�����
			conChangeSplitInfo = this.getConChangeSplitInfoFromNumber(curProjectInfo.getId().toString(), contractChangeBillInfo.getId().toString(), ctx);
			if (conChangeSplitInfo != null) {
				if(conChangeSplitInfo.getSplitState() == CostSplitStateEnum.ALLSPLIT) {
					throw new TaskExternalException(getResource(ctx, "AllSplit"));
				}
				return conChangeSplitInfo;
			} else {
				conChangeSplitInfo = new ConChangeSplitInfo();
			}
			
			conChangeSplitInfo.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
			
			// �õ�����˶���
			FilterInfo creatfilter = new FilterInfo();
			creatfilter.getFilterItems().add(new FilterItemInfo("number",fCreatorNumber));
			EntityViewInfo creatview = new EntityViewInfo();
			creatview.setFilter(creatfilter);
			UserCollection creatcoll = UserFactory.getLocalInstance(ctx).getUserCollection(creatview);
			if(creatcoll.size()>0){
				createUserInfo = creatcoll.get(0);	
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_fCreatorNumber")+ getResource(ctx,"Import_NOTNULL"));
			}
			// �����˱��룬���ʱ��
			if(fState.trim().equals(getResource(ctx,"yishenpi"))){
				if (!StringUtils.isEmpty(fAuditorNumber)){ 
					FilterInfo audiofilter = new FilterInfo();
					audiofilter.getFilterItems().add(new FilterItemInfo("number",fAuditorNumber));
					EntityViewInfo audioview = new EntityViewInfo();
					audioview.setFilter(audiofilter);
					UserCollection audiocoll = UserFactory.getLocalInstance(ctx).getUserCollection(audioview);
					if(audiocoll.size()>0){
						auditorUserInfo = audiocoll.get(0);	
						conChangeSplitInfo.setAuditor(auditorUserInfo);
					} else {
						throw new TaskExternalException(getResource(ctx,"Import_fAuditorNameL21")+ getResource(ctx, "Import_NOTNULL"));
					}
				} else {
					throw new TaskExternalException(getResource(ctx,"SettlementCostSplit_Import_fAuditorNumberNotNull"));
				}
			}	
			if(fState.trim().equals(getResource(ctx,"yishenpi"))){
				if (StringUtils.isEmpty(fAuditTime)) {
					throw new TaskExternalException(getResource(ctx,"SettlementCostSplit_Import_fAuditTime"));
				}
				conChangeSplitInfo.setAuditTime(checkDate(fAuditTime,ctx));
			}
			//״̬
			if(fState.trim().equals(getResource(ctx,"yishenpi"))){
				conChangeSplitInfo.setState(FDCBillStateEnum.AUDITTED);
			}else if(fState.trim().equals(getResource(ctx,"yitijiao"))){
				conChangeSplitInfo.setState(FDCBillStateEnum.SUBMITTED);
			}else{
				conChangeSplitInfo.setState(FDCBillStateEnum.SAVED);
			}
			conChangeSplitInfo.setCurProject(curProjectInfo);
			conChangeSplitInfo.setContractChange(contractChangeBillInfo);
			conChangeSplitInfo.setCreator(createUserInfo);
			conChangeSplitInfo.setCreateTime(new Timestamp(checkDate(fCreateTime, ctx).getTime()));
			conChangeSplitInfo.setPeriod(contractChangeBillInfo.getPeriod());
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		
		return conChangeSplitInfo;
	}
	
	private ConChangeSplitEntryInfo transmitEntry(Hashtable lineData, Context ctx) throws TaskExternalException{
		
		conChangeSplitEntryInfo = new ConChangeSplitEntryInfo();
		
		//�����������������
		String fCurProjectCodingNumber =  (String) ((DataToken) lineData.get("FCurProject_codingNumber")).data;
		//��������������
		String FCurProjectName =  (String) ((DataToken) lineData.get("FCurProject_name_l2")).data;
		//��������ɱ���Ŀ����
		String fEntryscostAccountNumber =  (String) ((DataToken) lineData.get("FEntrys$costAccount_number")).data;
		//��������ɱ���Ŀ
		String fEntryscostAccountName  =  (String) ((DataToken) lineData.get("FEntrys$costAccount_name_l2")).data;
		//��ֱ���(%)
		String fEntrysParentSplitScale =  (String) ((DataToken) lineData.get("FEntrys$parent_splitScale")).data;
		//�������
		String fEntrysAmount =  (String) ((DataToken) lineData.get("FEntrys_amount")).data;
		BigDecimal bdEntrysAmount = null;
		//������׼
		String fEntrysSplitType =  (String) ((DataToken) lineData.get("FEntrys_splitType")).data;
		//ֱ�ӹ���
		String fEntrysProductNumber =  (String) ((DataToken) lineData.get("FEntrys$product_number")).data;
		/*
		 * �ж��Ƿ�Ϊ��
		 */
		if (StringUtils.isEmpty(fCurProjectCodingNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCurProjectCodingNumber2NotNull"));
		}
		if (StringUtils.isEmpty(fEntryscostAccountNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fEntryscostAccountNumberNotNull"));
		}
//		if (StringUtils.isEmpty(fEntrysParentSplitScale)) {
//			throw new TaskExternalException(getResource(ctx,"SettlementCostSplit_Import_fEntrysSplitScaleNotNull"));
//		}
//		if (StringUtils.isEmpty(fEntrysAmount)) {
//			throw new TaskExternalException(getResource(ctx,"SettlementCostSplit_Import_fEntrysAmountNotNull"));
//		}
		if (StringUtils.isEmpty(fEntrysParentSplitScale) && StringUtils.isEmpty(fEntrysAmount)) {
			throw new TaskExternalException(getResource(ctx,"EntrysParentSplitScaleOrEntrysAmountNotNull"));
		}
		
		try {
			//��������ɱ���Ŀ����
			FilterInfo costAccountFilter = new FilterInfo();
			fEntryscostAccountNumber = fEntryscostAccountNumber.replace('!', '.');
			costAccountFilter.getFilterItems().add(new FilterItemInfo("codingNumber", fEntryscostAccountNumber));
			costAccountFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectInfo.getId().toString()));
			costAccountFilter.setMaskString("#0 and #1");
			EntityViewInfo costAccountView = new EntityViewInfo();
			costAccountView.setFilter(costAccountFilter);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(costAccountView);
			if (costAccountColl.size() > 0) {
				costAccountInfo = costAccountColl.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx,"Import_fEntryscostAccountNumber")+getResource(ctx,"Import_NOTNULL"));
			}

			// ֱ�ӹ���
			if(!StringUtils.isEmpty(fEntrysProductNumber)) {				
				FilterInfo productTypeFilter = new FilterInfo();
				productTypeFilter.getFilterItems().add(new FilterItemInfo("number", fEntrysProductNumber.trim()));
				EntityViewInfo productTypeView = new EntityViewInfo();
				productTypeView.setFilter(productTypeFilter);
				ProductTypeCollection productTypeCollection = ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(productTypeView);
				if(productTypeCollection.size() > 0) {
					productTypeInfo = productTypeCollection.get(0);
				} else {
					throw new TaskExternalException(getResource(ctx, "ProductTypeNotFound"));
				}
			}

			//������׼
		    CostSplitTypeEnum costSplitTypeEnum = null;
		    if(!StringUtils.isEmpty(fEntrysSplitType)){
			    costSplitTypeEnum = CostSplitTypeEnum.getEnum(fEntrysSplitType);
			    if(costSplitTypeEnum == null) {
				    throw new TaskExternalException(getResource(ctx, "CostSplitTypeEnum"));
			    }
			    conChangeSplitEntryInfo.setSplitType(costSplitTypeEnum);
		    }
		    
			// �жϷ�¼�Ƿ����
			ConChangeSplitEntryCollection coll = conChangeSplitInfo.getEntrys();
			if (coll!=null && coll.size()>0) {
				ConChangeSplitEntryInfo tempInfo = null;
				for (int i = 0; i < coll.size(); i++) {
					tempInfo = coll.get(i);
					if (tempInfo.getCostAccount().getId().toString().equals(costAccountInfo.getId().toString())) {
						throw new TaskExternalException(getResource(ctx, "EntryIsRepeat"));
					}
				}
			}
			
			BigDecimal entrysAmount = null;
			// ����ֵı�����
			if (changeSplitAmount == null) {
				changeSplitAmount = contractChangeBillInfo.getBalanceAmount();	// ������
				if (changeSplitAmount == null) {
					changeSplitAmount = contractChangeBillInfo.getAmount();	// ��λ�ҽ��
					if (changeSplitAmount == null) {
						changeSplitAmount = FDCHelper.ZERO;
					}
				}
			}
			//��ֱ���(%)
		    if (!StringUtils.isEmpty(fEntrysParentSplitScale)) {
		    	try {
				    if (Float.parseFloat(fEntrysParentSplitScale) > 100 || Float.parseFloat(fEntrysParentSplitScale) < 0) {
					    throw new TaskExternalException(getResource(ctx,"Import_fEntrysParentSplitScale"));
				    }
				    entrysAmount = FDCHelper.divide(FDCHelper.multiply(changeSplitAmount, new BigDecimal(fEntrysParentSplitScale)), new BigDecimal(100));
		    	} catch (Exception e) {
		    		throw new TaskExternalException(getResource(ctx, "EntrysParentSplitScaleIsNotNumber"));
		    	}
		    } else {
		    	try {
		    		entrysAmount = new BigDecimal(fEntrysAmount);
		    	} catch (Exception e) {
		    		throw new TaskExternalException(getResource(ctx, "EntrysAmountIsNotNumber"));
		    	}
		    }

		    sum = FDCHelper.ZERO;
			for (int i=0; i<coll.size(); i++) {
				BigDecimal money = coll.get(i).getAmount();
				if (money == null) {
					money = FDCHelper.ZERO;
				}
				sum = sum.add(money);
			}
			sum = sum.add(entrysAmount);
			
			if (sum.compareTo(changeSplitAmount) > 0) {
				throw new TaskExternalException(getResource(ctx, "Amount"));
			}			
			
			//��ֱ���(%)
		    if (!StringUtils.isEmpty(fEntrysParentSplitScale)) {
		    	conChangeSplitEntryInfo.setSplitScale(new BigDecimal(fEntrysParentSplitScale));
		    } else {
		    	conChangeSplitEntryInfo.setSplitScale(FDCHelper.multiply(FDCHelper.divide(entrysAmount, changeSplitAmount), new BigDecimal(100)));
		    }
			conChangeSplitEntryInfo.setIsLeaf(true);
			conChangeSplitEntryInfo.setCostAccount(costAccountInfo);
			conChangeSplitEntryInfo.setAmount(entrysAmount);
			conChangeSplitEntryInfo.setProduct(productTypeInfo);		
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		
		return conChangeSplitEntryInfo;
	}
	//�����ж�
	private Date checkDate(String dateStr,Context ctx) throws TaskExternalException{
		try {
		    if(StringUtils.isEmpty(dateStr)) return null;
                DateFormat df = null;
		    if(dateStr.trim().length() <= "yyyy-MM-dd".length()){ // ���� "yyyy-MM-d"
			    df = new SimpleDateFormat("yyyy-MM-dd");
		    }else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm".length()){ //���� yyyy-MM-d HH:mm���
			    df = new SimpleDateFormat("yyyy-MM-dd HH:mm");	
		    }else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm:ss".length()){
			    df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    }else{
			    throw new TaskExternalException(getResource(ctx,"Import_DateForm"));
		    }
		    df.setLenient(false);
		    return df.parse(dateStr);
		} catch (ParseException e) {
//			e.printStackTrace();
			throw new TaskExternalException(getResource(ctx,"Import_DateForm"));
		}
    }
//	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
//		if (coreBaseInfo == null || coreBaseInfo instanceof ContractBillInfo == false) {
//			return;
//		}
//		try {
//			ConChangeSplitInfo billBase = (ConChangeSplitInfo) coreBaseInfo;
//			String id = getIdFromNumber(billBase.getNumber(), ctx);
//			if (StringUtil.isEmptyString(id)) {
//				getController(ctx).addnew(coreBaseInfo);
//			} else {
//				coreBaseInfo.setId(BOSUuid.read(id));
//				getController(ctx).update(new ObjectUuidPK(id), coreBaseInfo);
//			}
//
//		} catch (Exception ex) {
//			throw new TaskExternalException(ex.getMessage(), ex.getCause());
//		}
//	}
	/**
	 * ����number��ȡ�������û���򷵻�null
	 * @param number
	 * @param ctx
	 * @return
	 * @throws TaskExternalException
	 * @author Robin
	 * @throws EASBizException 
	 */
	private ConChangeSplitInfo getConChangeSplitInfoFromNumber(String curProjectID, String contractChangeBillId, Context ctx) throws TaskExternalException{
		try {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectID));
			filter.getFilterItems().add(new FilterItemInfo("ContractChange.id", contractChangeBillId));			
			filter.setMaskString("#0 and #1");
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			ConChangeSplitCollection coll = ConChangeSplitFactory.getLocalInstance(ctx).getConChangeSplitCollection(view);
			if (coll != null&&coll.size()>0) {
				return coll.get(0);
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		} 
		return null;
	}

	/**
	 * �õ���Դ�ļ�
	 * @author ֣��Ԫ
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
