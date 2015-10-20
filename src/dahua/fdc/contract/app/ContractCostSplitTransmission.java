/**
 * 
 */
package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
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
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		��ͬ���������������ת����	
 *		
 * @author		����
 * @version		EAS7.0		
 * @createDate	2011-6-9	 
 * @see						
 */
public class ContractCostSplitTransmission extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.contract.ContractTransmissionResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";
	
	ContractCostSplitInfo info = null;;
	
	ContractCostSplitEntryInfo infoEntry = null;

	ContractBillInfo con = null;
	
//	static Map stateMap = new HashedMap();
	
	private BigDecimal sum = FDCHelper.ZERO;
	
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = ContractCostSplitFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		try {
			info = transmitHead(hsData, ctx);
			if (info == null) {
				return null;
			}
			ContractCostSplitEntryInfo entry = transmitEntry(hsData, ctx);
	        int seq = info.getEntrys().size() + 1;
	        entry.setSeq(seq);
	        entry.setParent(info);
	        info.getEntrys().add(entry);
	        // ���ò��״̬
	        StringBuffer sqlStr = new StringBuffer("update T_CON_ContractBill set FSplitState = '");
	        if(FDCHelper.ZERO.compareTo(sum) == 0) {
	        	info.setSplitState(CostSplitStateEnum.NOSPLIT);
	        	sqlStr.append(CostSplitStateEnum.NOSPLIT_VALUE).append("'");
	        } else if(con.getAmount().compareTo(sum) == 1) {
	        	info.setSplitState(CostSplitStateEnum.PARTSPLIT);
	        	sqlStr.append(CostSplitStateEnum.PARTSPLIT_VALUE).append("'");
	        } else {
	        	info.setSplitState(CostSplitStateEnum.ALLSPLIT);
	        	sqlStr.append(CostSplitStateEnum.ALLSPLIT_VALUE).append("'");
	        }
	        sqlStr.append(" where fid='").append(con.getId().toString()).append("'");
	        FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(sqlStr.toString());
	        sqlBuilder.executeUpdate(ctx);
	       
	        // �ֶ����棬��save���ܸ�����Ӧ��ȫ��Ŀ��̬�ɱ���ʹ������ɱ�Ԥ���
	        getController(ctx).save(info);
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		} catch (EASBizException e) {
			throw new TaskExternalException(e.getMessage());
		}
		
		return null;
	}
	
	private ContractCostSplitInfo getContractCostSplit(String curProjectID, String contractBillId, Context ctx) throws TaskExternalException{
		try {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractBillId));
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectID));
			filter.setMaskString("#0 and #1");
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			ContractCostSplitCollection info = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(view);
			if (info != null) {
				return info.get(0);
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	private ContractCostSplitInfo transmitHead(Hashtable hsdata, Context ctx) throws TaskExternalException {
		//��֯����
		String fcostOrg_longNumber  = (String) ((DataToken)hsdata.get("FCurProject$costOrg_longNumber")).data;
		//������Ŀ����
		String fCurProjectLongNumber = (String) ((DataToken)hsdata.get("FCurProject_longNumber")).data;			
	    //��ͬ����
		String fContractBillNumber = (String) ((DataToken)hsdata.get("FContractBill_number")).data;
		//��ͬ����
		String fContractBillName = (String) ((DataToken)hsdata.get("FContractBill_name")).data;
		//��ͬ���
		String fContractBillAmount = (String) ((DataToken)hsdata.get("FContractBill_amount")).data;
		//�Ѳ�ֺ�ͬ���
		String fAmount = (String) ((DataToken)hsdata.get("FAmount")).data;
		//δ��ֺ�ͬ���
		String fDCSplitBillAmount  = (String) ((DataToken)hsdata.get("UnSplitAmount")).data;
		//�Ƿ���ȷ��
		String fIsConfirm = (String) ((DataToken)hsdata.get("FIsConfirm")).data;
		// ״̬
		String fState=(String) ((DataToken) hsdata.get("FState")).data;
		//�����
		String fCreatorNumber = (String) ((DataToken)hsdata.get("FCreator_number")).data;
		//���ʱ��
		String fCreateTime = (String) ((DataToken)hsdata.get("FCreateTime")).data;
		//������
		String fAuditorNumber = (String) ((DataToken)hsdata.get("FAuditor_number")).data;
		//����ʱ��
		String fAuditTime = (String) ((DataToken)hsdata.get("FAuditTime")).data;
		
		String stateStr = this.getStateStr(fState, ctx);
		/**
		 * ��¼��У��
		 */
		if(StringUtils.isEmpty(fCurProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "CurProjectLongNumberNotNull",resource));
		}
		if(StringUtils.isEmpty(fContractBillNumber)) {
			throw new TaskExternalException(getResource(ctx, "ContractCodingNumberNotNull",resource));
		}
		if(StringUtils.isEmpty(fCreatorNumber)) {
			throw new TaskExternalException(getResource(ctx, "CreatorNumberIsNull",resource));
		}
		if(StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException(getResource(ctx, "CreateTime",resource));
		}
		if(StringUtils.isEmpty(fIsConfirm)) {
			fIsConfirm = getResource(ctx, "no",resourceCommon);
		}
		if(!fIsConfirm.trim().equals(getResource(ctx, "yes",resourceCommon)) && !fIsConfirm.trim().equals(getResource(ctx, "no",resourceCommon))) {
			throw new TaskExternalException(getResource(ctx, "IsConfirm",resource));
		}
		
		if(stateStr != null && stateStr.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "StateIsOver40",resource));
		}
		
		/**
		 * ���ڸ�ʽ��֤
		 */
		FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(ctx, "CreateTimeError",resource));
		
		/**
		 * ��ѯ������֤
		 */
		UserInfo creatot = null;
		UserInfo auditor = null;
		Date auditDate = null;
		CostCenterOrgUnitInfo costCenter = null;
		CurProjectInfo curProjectInfo = null;
		ContractBillInfo contractBillInfo = null;
		try {
			// ������Ŀ����
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "CurProjectNumberNotFound",resource));
			}
			
			// ��֯����
			costCenter = curProjectInfo.getCostCenter();
			String costCenterLongNumber = "";
			if(costCenter == null) {
				throw new TaskExternalException(getResource(ctx, "CostCenterNotFound",resource));
			}
			BOSUuid id = costCenter.getId();
			FilterInfo costCenterFilter = new FilterInfo();
			costCenterFilter.getFilterItems().add(new FilterItemInfo("id", id.toString()));
			EntityViewInfo costCenterView = new EntityViewInfo();
			costCenterView.setFilter(costCenterFilter);
			CostCenterOrgUnitCollection costCenterOrgUnitCollection = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(costCenterView);
			if(costCenterOrgUnitCollection.size() > 0) {
				costCenter = costCenterOrgUnitCollection.get(0);
				costCenterLongNumber = costCenter.getLongNumber();
				if(!StringUtils.isEmpty(fcostOrg_longNumber) && !costCenterLongNumber.equals(fcostOrg_longNumber.replace('.', '!'))) {
					throw new TaskExternalException(getResource(ctx, "CostOrgLongNumberNotFound",resource));
				}
			}
			
			// ��ͬ����
			FilterInfo contractBillFile = new FilterInfo();
			contractBillFile.getFilterItems().add(new FilterItemInfo("number", fContractBillNumber));
			contractBillFile.getFilterItems().add(new FilterItemInfo("curproject.id", curProjectInfo.getId().toString()));
			contractBillFile.setMaskString("#0 and #1");
			EntityViewInfo contractBillView = new EntityViewInfo();
			contractBillView.setFilter(contractBillFile);
			ContractBillCollection contractBillCollection = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(contractBillView);
			if(contractBillCollection.size() > 0) {
				contractBillInfo = contractBillCollection.get(0);
				con = contractBillInfo;
			} else {
				throw new TaskExternalException(getResource(ctx, "ContractBillNumberNotFound",resource));
			}
			
			// �жϵ�ͷ�Ƿ����
			info = getContractCostSplit(curProjectInfo.getId().toString(), contractBillInfo.getId().toString(), ctx);
			if(info != null) {
				if(info.getSplitState() == CostSplitStateEnum.ALLSPLIT) {
					throw new TaskExternalException(getResource(ctx, "AllSplit",resource));
				}
				return info;
			} else {
				info = new ContractCostSplitInfo();
			}
			
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		
		// �����
		creatot = isUserNumber(fCreatorNumber, ctx);
		if(creatot == null) {
			throw new TaskExternalException(getResource(ctx, "CreatorNotFound",resource));
		}
		
		if(stateStr.trim().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
			if(StringUtils.isEmpty(fAuditorNumber)) {
				throw new TaskExternalException(getResource(ctx, "AuditorNumberNotNull",resource));
			}
			if(StringUtils.isEmpty(fAuditTime)){
				throw new TaskExternalException(getResource(ctx, "AuditTimeNotNull",resource));
			}
		}
		if(!StringUtils.isEmpty(fAuditTime)) {
			auditDate = FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(ctx, "AuditTimeError",resource));
		}
		if(!StringUtils.isEmpty(fAuditorNumber)) {
			auditor = isUserNumber(fAuditorNumber, ctx);
			if(auditor == null){
				throw new TaskExternalException(getResource(ctx, "AuditorNumberNotFound",resource));
			}
		}
		
		/**
		 * ���ֵ
		 */
		info.setOrgUnit(costCenter.castToFullOrgUnitInfo());
		info.setCurProject(curProjectInfo);
		info.setContractBill(contractBillInfo);
		info.setState(FDCBillStateEnum.getEnum(stateStr));
		// �²�ֵ��Ѳ�ֺ�ͬ���Ϊ��
		info.setAmount(FDCHelper.ZERO);
		if(fIsConfirm.trim().equals(getResource(ctx, "yes",resourceCommon))) {
			info.setIsConfirm(true);
		} else {
			info.setIsConfirm(false);
		}
		info.setCreator(creatot);
		info.setCreateTime(new Timestamp(FDCDateHelper.stringToTimeStamp(fCreateTime).getTime()));
		info.setAuditor(auditor);
		info.setAuditTime(auditDate);
		return info;
	}
	
	private ContractCostSplitEntryInfo transmitEntry(Hashtable hsdata, Context ctx) throws TaskExternalException{
		//�����������������
		String curProjectLongNumber = (String) ((DataToken)hsdata.get("FEntrys$costAccount$curProject_longNumber")).data;
		//curProjectLongNumber = curProjectLongNumber.replace('!', '.');
		//��������������
		String curProjectName = (String) ((DataToken)hsdata.get("FEntrys$costAccount$curProject_name_l2")).data;
		//�����ɱ���Ŀ����
		String fCostAccountNumber = (String) ((DataToken)hsdata.get("FEntrys$costAccount_number")).data;
	    //�����ɱ���Ŀ
		String fCostAccountNamel2 = (String) ((DataToken)hsdata.get("FEntrys$costAccount_name_l2")).data;
		//��ֱ���(%)
		String fEntrysSplitScale = (String) ((DataToken)hsdata.get("FEntrys_splitScale")).data;
		//�������
		String fEntrysAmount = (String) ((DataToken)hsdata.get("FEntrys_amount")).data;
		// ������׼
		String fEntrysSplitType = (String) ((DataToken)hsdata.get("FEntrys_splitType")).data;
		//ֱ�ӹ���
		String productNumber = (String) ((DataToken)hsdata.get("FEntrys$product_number")).data;
		
		/**
		 * ��¼��У��
		 */
		if(StringUtils.isEmpty(curProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "CurProjectCodingNumberNotNull",resource));
		}
		if(StringUtils.isEmpty(fCostAccountNumber)) {
			throw new TaskExternalException(getResource(ctx, "CostAccountNumberNotNull",resource));
		}
		// Added by Owen_wen ��ֱ���������¼У���ˣ��Թ�����������Ϊ���������ֱ���
		// if(StringUtils.isEmpty(fEntrysSplitScale)) {
		// throw new TaskExternalException(getResource(ctx, "SplitScaleNotNull",resource));
		// }
		if(StringUtils.isEmpty(fEntrysAmount)) {
			throw new TaskExternalException(getResource(ctx, "AmountNotNull",resource));
		}
		
		if(!StringUtils.isEmpty(fEntrysSplitType)) {
			if(!fEntrysSplitType.equals(getResource(ctx, "Import_StateProdsplit",resource))) {
				//��������ȷ�Ĺ�����׼
				throw new TaskExternalException(getResource(ctx, "CostSplitTypeEnum",resource));
			}else
			{
				fEntrysSplitType = CostSplitTypeEnum.PRODSPLIT_VALUE;
			}
		}
		
		BigDecimal splitScale = FDCTransmissionHelper.strToBigDecimal(fEntrysSplitScale);
		if (!StringUtils.isEmpty(fEntrysSplitScale) ){ // ���벻Ϊ��ʱУ�飬Ϊ�ղ���ҪУ�飨��Ϊ�Ǳ�¼�
			if(!FDCTransmissionHelper.isNumber(fEntrysSplitScale)) {
				throw new TaskExternalException(getResource(ctx, "EntrysSplitScaleNotNumber",resource));
			} else if(splitScale.compareTo(FDCHelper.ZERO) == -1 || splitScale.compareTo(FDCHelper.ONE_HUNDRED) == 1) {
				throw new TaskExternalException(getResource(ctx, "splitScaleError",resource));
			}
		}
		
		if(!FDCTransmissionHelper.isNumber(fEntrysAmount)) {
			throw new TaskExternalException(getResource(ctx, "EntrysAmountNotNumber",resource));
		}
		
		/**
		 * ��ѯ����У��
		 */
		CostAccountInfo costAccountInfo = null;
		ProductTypeInfo productTypeInfo = null;
		CurProjectInfo curProjectInfo = null;
		ContractBillInfo contractBillInfo = null;
		BigDecimal entrysAmount = null;
		BigDecimal amount = null;
		try {
			//�����������������
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("codingNumber", curProjectLongNumber.trim().replace('!', '.')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "curProjectInfo",resource));
			}
			
			// �����ɱ���Ŀ����
			FilterInfo costAccountFilter = new FilterInfo();
			costAccountFilter.getFilterItems().add(new FilterItemInfo("codingNumber", fCostAccountNumber.trim().replace('!', '.')));
			costAccountFilter.getFilterItems().add(new FilterItemInfo("curProject", curProjectInfo.getId().toString()));
			costAccountFilter.setMaskString("#0 and #1");
			EntityViewInfo costAccountView = new EntityViewInfo();
			costAccountView.setFilter(costAccountFilter);
			CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(costAccountView);
			if(costAccountCollection.size() > 0) {
				costAccountInfo = costAccountCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "CostAccountNotFound",resource));
			}
			
			// ֱ�ӹ���
			if(!StringUtils.isEmpty(productNumber)) {				
				FilterInfo productTypeFilter = new FilterInfo();
				productTypeFilter.getFilterItems().add(new FilterItemInfo("number", productNumber.trim()));
				EntityViewInfo productTypeView = new EntityViewInfo();
				productTypeView.setFilter(productTypeFilter);
				ProductTypeCollection productTypeCollection = ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(productTypeView);
				if(productTypeCollection.size() > 0) {
					productTypeInfo = productTypeCollection.get(0);
				} else {
					throw new TaskExternalException(getResource(ctx, "ProductTypeNotFound",resource));
				}
			}
			
			// �жϷ�¼�Ƿ����
			ContractCostSplitEntryCollection coll = info.getEntrys();
			if(coll!=null&&coll.size()>0)
			{
				ContractCostSplitEntryInfo tempInfo = null;
				for(int i=0;i<coll.size();i++)
				{
					tempInfo = coll.get(i);
					if(tempInfo.getCostAccount().getId().toString().equals(costAccountInfo.getId().toString())) {
						throw new TaskExternalException(getResource(ctx, "EntryIsRepeat",resource));
					}
				}
			}
			
			// ���������ĺ�ͬ
			String id = info.getContractBill().getId().toString();
			FilterInfo contractBillFile = new FilterInfo();
			contractBillFile.getFilterItems().add(new FilterItemInfo("id", id));
			EntityViewInfo contractBillView = new EntityViewInfo();
			contractBillView.setFilter(contractBillFile);
			ContractBillCollection contractBillCollection = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(contractBillView);
			if(contractBillCollection.size() > 0) {
				contractBillInfo = contractBillCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "ContractBillNumberNotFound",resource));
			}
			entrysAmount = FDCTransmissionHelper.strToBigDecimal(fEntrysAmount);
			amount = contractBillInfo.getAmount();
			if(entrysAmount.compareTo(amount.subtract(info.getAmount())) > 0) {
				throw new TaskExternalException(getResource(ctx, "Amount",resource));
			}
			
			for(int i=0;i<coll.size();i++) {
				BigDecimal money = coll.get(i).getAmount();
				if(money == null) {
					money = FDCHelper.ZERO;
				}
				sum = sum.add(money);
			}
			sum = sum.add(entrysAmount);
			
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		infoEntry = new ContractCostSplitEntryInfo();
		costAccountInfo.setCurProject(curProjectInfo);
		infoEntry.setCostAccount(costAccountInfo);
		// ��ֱ���
		infoEntry.setSplitScale(entrysAmount.divide(amount, 6, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
		infoEntry.setAmount(entrysAmount);
		infoEntry.setSplitType(CostSplitTypeEnum.getEnum(fEntrysSplitType));
		infoEntry.setProduct(productTypeInfo);
		// �Ƿ��ӽڵ�
		if(fCostAccountNumber.indexOf(".") == -1) {
			infoEntry.setIsLeaf(false);
		} else {
			infoEntry.setIsLeaf(true);
		}
		return infoEntry;
	}
	
	/**
	 * 	�ж��û��Ƿ����
	 * @param number �û�����
	 * @param ctx
	 * @return	true/false  ����/������
	 */
	private UserInfo isUserNumber(String number, Context ctx) throws TaskExternalException {
		UserInfo user = null;
		try {
			FilterInfo userFilter = new FilterInfo();
			userFilter.getFilterItems().add(new FilterItemInfo("number",number));
			EntityViewInfo userView = new EntityViewInfo();
			userView.setFilter(userFilter);
			UserCollection userCollection;
			userCollection = UserFactory.getLocalInstance(ctx).getUserCollection(userView);
			if(userCollection.size() > 0) {
				user = userCollection.get(0);
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return user;
	}
	
	
	private String  getStateStr(String fState, Context ctx) {
		String stateStr = FDCBillStateEnum.SAVED_VALUE;
		if(fState.equals(getResource(ctx, "FDCBillState_AUDITTED",resource)))
		{
			stateStr = FDCBillStateEnum.AUDITTED_VALUE;
		}else if(fState.equals(getResource(ctx, "FDCBillState_SUBMITTED",resource)))
		{
			stateStr = FDCBillStateEnum.SUBMITTED_VALUE;
		}
		return stateStr;
	}
	
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}
}
