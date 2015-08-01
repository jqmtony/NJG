/**
 * 
 */
package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
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
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		
 *		
 * @author		dingwen_yong
 * @version		EAS7.0		
 * @createDate	2011-6-13	 
 * @see						
 */
public class SettlementCostSplitTransmission extends AbstractDataTransmission {
	
	// ��Դ�ļ�
	private static String resource = "com.kingdee.eas.fdc.contract.CompensationBillResource";
	
	/** ������Ŀ */
	private CurProjectInfo curProject = null;
	/** ��ͬ���㵥 */
	private ContractSettlementBillInfo contractSettlementBill = null;
	/** ����� */
	private UserInfo createUser = null;
	/** ������ */
	private UserInfo auditUser = null;
	/** ��Ʒ����<ֱ�ӹ���> */
	private ProductTypeInfo productType = null;
	/** ������ */
	private SettlementCostSplitInfo info = null;
	/** �����ַ�¼ */
	private SettlementCostSplitEntryInfo entryInfo = null;
	/** �ɱ���Ŀ */
	private CostAccountInfo costAccount = null;
	//��ͬ���㵥
	private ContractSettlementBillInfo setinfo = new ContractSettlementBillInfo();
	
	private BigDecimal sum = FDCHelper.ZERO;
	
	private Map arrMap = new HashMap();
	
	private Map infoMap = new HashedMap();
	
	private Set entrySet = new HashSet();
	
//	static Map stateMap = new HashedMap();
//	
//	static {
//		stateMap.put("��Ʒ���", "PRODSPLIT");
//	}
	
	private void setArrMap(Hashtable hsdata, Context context) throws TaskExternalException {
		for (int i = 0; i < hsdata.size(); i++) {
			Hashtable hashtable = (Hashtable) hsdata.get(new Integer(i));
			
			String fSettlementBillNumber = ((String) ((DataToken) hashtable.get("FSettlementBill_number")).data).trim();
			String fEntrysAmount = ((String) ((DataToken) hashtable.get("FEntrys_amount")).data).trim();
			String fCostAccountNumber = ((String) ((DataToken) hashtable.get("FCostAccount_number")).data).trim();
			
			if (StringUtils.isEmpty(fSettlementBillNumber)) {
				// "���㵥���벻��Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fSettlementBillNumber"));
			}
			if (!StringUtils.isEmpty(fEntrysAmount)) {
				// "�������"
				this.strTOnumber(fEntrysAmount, getResource(context, "SettlementCostSplit_Import_fEntrysAmount"), context);
			} else {
				// "��������Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fEntrysAmountNotNull"));
			}
			if (StringUtils.isEmpty(fCostAccountNumber)) {
				// "��������ɱ���Ŀ����Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fCostAccountNumber"));
			}

			// ��¼�Ƿ��ظ�
			if(!entrySet.add(fSettlementBillNumber.trim()+fCostAccountNumber.trim())) {
				throw new TaskExternalException(getResource(context, "EntryIsRepaet"));
			}
			
			if (arrMap.get(fSettlementBillNumber.trim()) == null) {
				arrMap.put(fSettlementBillNumber.trim(), FDCHelper.ZERO);
			}				
			BigDecimal amount = FDCTransmissionHelper.strToBigDecimal(arrMap.get(fSettlementBillNumber.trim()).toString());
			amount = amount.add(FDCTransmissionHelper.strToBigDecimal(fEntrysAmount));
			arrMap.put(fSettlementBillNumber.trim(), amount);
		}
	}
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return SettlementCostSplitFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
	}
	
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		setArrMap(hashtable, context);
		try {
			for(int i = 0; i < hashtable.size(); i++) {
				Hashtable lineTable = (Hashtable)hashtable.get(new Integer(i));
				info = transmitHead(lineTable, context);
				if (info == null) {
					return null;
				}
				SettlementCostSplitEntryInfo entry = transmitEntry(lineTable, context);
		        entry.setParent(info);
		        int seq = info.getEntrys().size() + 1;
		        entry.setSeq(seq);
		        info.getEntrys().add(entry);
			}
			
			// ����infoMAP
			Collection coll = infoMap.values();
			Iterator it = coll.iterator();
			while (it.hasNext()) {
				SettlementCostSplitInfo info = (SettlementCostSplitInfo) it.next();
				if (info.getId() == null || !getController(context).exists(new ObjectUuidPK(info.getId())))
					getController(context).save(info);
				else
					getController(context).update(new ObjectUuidPK(info.getId()), info);
			}
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		} catch (EASBizException e) {
			
			throw new TaskExternalException(e.getMessage(), e);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
		return null;
	}
	
	private SettlementCostSplitInfo transmitHead(Hashtable hashtable, Context context) throws TaskExternalException {
		/**
		 * ��ȡExcel�ı��ж�Ӧ��ֵ
		 */
		// 1.��֯���� 
		String fCostCenterNumber = ((String) ((DataToken) hashtable.get("FCurProject$costOrg_longNumber")).data).trim();
		// 2.������Ŀ����
		String fCurProjectLongNumber = ((String) ((DataToken) hashtable.get("FCurProject_longNumber")).data).trim();
		// 3.���㵥����
		String fSettlementBillNumber = ((String) ((DataToken) hashtable.get("FSettlementBill_number")).data).trim();
		// 4.���㵥����
		String fSettlementBillName = ((String) ((DataToken) hashtable.get("FSettlementBill_name")).data).trim();
		// 5.�������
		String fSettlementBillSettlePrice = ((String) ((DataToken) hashtable.get("FSettlementBill_settlePrice")).data).trim();
		// 6.�Ѳ�ֽ������
		String fAmount = ((String) ((DataToken) hashtable.get("FAmount")).data).trim();
		// 7.δ��ֽ������  <ͨ�������Զ�������Ľ����>
		String fUnSplitAmount = ((String) ((DataToken) hashtable.get("FUnSplitAmounts")).data).trim();
		// 8.���޽���
		String fSettlementBillQualityGuarante = ((String) ((DataToken) hashtable.get("FSettlementBill_qualityGuarante")).data).trim();
		// 8.5 ״̬<�޸����������ֶ�,Ĭ��Ϊ������>
		String fState = ((String) ((DataToken) hashtable.get("FState")).data).trim(); 
		// 9.����˱���
		String fCreatorNumber = ((String) ((DataToken) hashtable.get("FCreator_number")).data).trim();
		// 10.���ʱ��
		String fCreateTime = ((String) ((DataToken) hashtable.get("FCreateTime")).data).trim();
		// 11.�����˱���
		String fAuditorNumber = ((String) ((DataToken) hashtable.get("FAuditor_number")).data).trim();
		// 12.����ʱ��
		String fAuditTime = ((String) ((DataToken) hashtable.get("FAuditTime")).data).trim();
		
		
		String stateStr = this.getStateStr(fState, context);
		
		
		/**
		 * �жϲ����ֶ��Ƿ�Ϊ��
		 */
		if (StringUtils.isEmpty(fCurProjectLongNumber)) {
			// "������Ŀ���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fCurProjectLongNumber"));
		}
		if (StringUtils.isEmpty(fSettlementBillNumber)) {
			// "���㵥���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fSettlementBillNumber"));
		}
		if (!StringUtils.isEmpty(fSettlementBillSettlePrice)) { 
			// "�������"
			this.strTOnumber(fSettlementBillSettlePrice, getResource(context, "SettlementCostSplit_Import_fSettlementBillSettlePrice"), context);
		}
		if (!StringUtils.isEmpty(fAmount)) { 
			// "�Ѳ�ֽ������"
			this.strTOnumber(fAmount, getResource(context, "SettlementCostSplit_Import_fAmount"), context);
		}
		if (!StringUtils.isEmpty(fUnSplitAmount)) { 
			// "δ��ֽ������"
			this.strTOnumber(fUnSplitAmount, getResource(context, "SettlementCostSplit_Import_fUnSplitAmount"), context);
		}
		if (!StringUtils.isEmpty(fSettlementBillQualityGuarante)) { 
			// "���޽���"
			this.strTOnumber(fSettlementBillQualityGuarante, getResource(context, "fSettlementBillQualityGuarante"), context);
		}
		if (StringUtils.isEmpty(fCreatorNumber)) {
			// "����˱��벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fAuditorNumber"));
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			// "���ʱ�䲻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fCreateTime"));
		}
		
		// �жϵ�ͷ�Ƿ����
		if(infoMap.get(fSettlementBillNumber.trim()) == null) {
			info = new SettlementCostSplitInfo();
		} else {
			return (SettlementCostSplitInfo)infoMap.get(fSettlementBillNumber.trim());
		}
		
		/**
		 * ��ֵ���������
		 */
		CostCenterOrgUnitInfo costCenterOrgUnit = null;
		try {
			// ������Ŀ����
			FilterInfo filterCurProject = new FilterInfo();
			filterCurProject.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo viewCurProject = new EntityViewInfo();
			viewCurProject.setFilter(filterCurProject);
			CurProjectCollection curProjectBillColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(viewCurProject);
			if (curProjectBillColl.size() > 0){
				curProject = curProjectBillColl.get(0);
			} else {// 1 "������Ŀ����Ϊ��,��ù�����Ŀ���� "  // 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fCurProjectLongNumber1"));
			}

			// ��֯����
			costCenterOrgUnit = curProject.getCostCenter();
			String costCenterLongNumber = "";
			if(costCenterOrgUnit == null) {
				throw new TaskExternalException(getResource(context, "CostCenterNotFound"));
			}
			BOSUuid id = costCenterOrgUnit.getId();
			FilterInfo costCenterFilter = new FilterInfo();
			costCenterFilter.getFilterItems().add(new FilterItemInfo("id", id));
			EntityViewInfo costCenterView = new EntityViewInfo();
			costCenterView.setFilter(costCenterFilter);
			CostCenterOrgUnitCollection costCenterOrgUnitCollection = CostCenterOrgUnitFactory.getLocalInstance(context).getCostCenterOrgUnitCollection(costCenterView);
			if(costCenterOrgUnitCollection.size() > 0) {
				costCenterOrgUnit = costCenterOrgUnitCollection.get(0);
				costCenterLongNumber = costCenterOrgUnit.getLongNumber();
				if(!StringUtils.isEmpty(fCostCenterNumber) && !costCenterLongNumber.equals(fCostCenterNumber.replace('.', '!'))) {
					throw new TaskExternalException(getResource(context, "OrgUnitNumberNotFound"));
				}
			}
			
			// ���㵥����
			FilterInfo filtercontractSettlementBill = new FilterInfo();
			filtercontractSettlementBill.getFilterItems().add(new FilterItemInfo("number", fSettlementBillNumber));
			EntityViewInfo viewcontractSettlementBill = new EntityViewInfo();
			viewcontractSettlementBill.setFilter(filtercontractSettlementBill);
			ContractSettlementBillCollection contractSettlementBillColl = ContractSettlementBillFactory.getLocalInstance(context).getContractSettlementBillCollection(viewcontractSettlementBill);
			if (contractSettlementBillColl.size() > 0){
				contractSettlementBill = contractSettlementBillColl.get(0);
				if(contractSettlementBill.getSplitState() == CostSplitStateEnum.ALLSPLIT) {
					throw new TaskExternalException(getResource(context, "ALLSplitState"));
				}
				info.setSettlementBill(contractSettlementBill);
				info.setContractBill(contractSettlementBill.getContractBill());
			}else{
				// 1 "���㵥����Ϊ��,��ý��㵥���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fSettlementBillNumber1"));
			}
			
			if(!contractSettlementBill.getCurProject().getId().toString().equals(
					curProject.getId().toString())) {
				throw new TaskExternalException(getResource(context, "ContractSettlementBillProject"));
			}

			// �ж��Ƿ������ȫ���
			BigDecimal dec = FDCTransmissionHelper.strToBigDecimal(arrMap.get(fSettlementBillNumber.trim()).toString());
			if(contractSettlementBill.getSettlePrice().compareTo(dec) != 0) {
				throw new TaskExternalException(getResource(context, "SplitStateError"));
			} else {
				info.setSplitState(CostSplitStateEnum.ALLSPLIT);
				infoMap.put(fSettlementBillNumber, info);
			}
			
			setinfo.setNumber(fSettlementBillNumber);//���㵥����
			setinfo.setName(contractSettlementBill.getName());// ���㵥����  <�ɽ��㵥�����Զ�����>
			BigDecimal bdsprice = contractSettlementBill.getSettlePrice();//�õ�������� 
			setinfo.setSettlePrice(bdsprice);// �������  <�ɽ��㵥�����Զ�����>
			setinfo.setQualityGuarante(contractSettlementBill.getQualityGuarante());// ���޽���  <�ɽ��㵥�����Զ�����>
			BigDecimal bdaprice = FDCTransmissionHelper.strToBigDecimal(fAmount);//�Ѳ�ֽ������ 
			info.setAmount(bdaprice);// �Ѳ�ֽ������ 
			// δ��ֽ������  <ͨ������������Ľ����  (δ��ֽ������ = ������� - �Ѳ�ֽ������ )>
			if(bdsprice == null) {
				bdsprice = FDCHelper.ZERO;
			}
			BigDecimal bdunsamont = bdsprice.subtract(bdaprice);
			
			// ����˱���
			FilterInfo filtercreateUser = new FilterInfo();
			filtercreateUser.getFilterItems().add(new FilterItemInfo("number", fCreatorNumber));
			EntityViewInfo viewcreateUser = new EntityViewInfo();
			viewcreateUser.setFilter(filtercreateUser);
			UserCollection createUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewcreateUser);
			if (createUserColl.size() > 0){
				createUser = createUserColl.get(0);
				info.setCreator(createUser);
			}else{// 1 "����˱���Ϊ��,�����Ʋ���˱��� "// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fCreatorNumber"));
			}
			
//			info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "SettlementCostSplit_Import_cfsjcw")).getTime()));
			info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, "���ʱ���ʽ���󣬽����ʽYYYY-MM-DD��").getTime()));
			info.setCurProject(curProject);
			
			// ״̬
			
			info.setState(FDCBillStateEnum.getEnum(stateStr));
				
			if (stateStr.equals(FDCBillStateEnum.AUDITTED_VALUE) || fState.equals("") || fState == null) {
				info.setState(FDCBillStateEnum.AUDITTED);
				// ������ 
				if (StringUtils.isEmpty(fAuditorNumber)) {
					// "��������״̬�µ��������ֶβ���Ϊ�գ�"
					throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fAuditorNumberNotNull"));
				}
				
				//����ʱ��
				if (StringUtils.isEmpty(fAuditTime)) {
					// "��������״̬�µ����������ֶβ���Ϊ�գ�"
					throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fAuditTime"));
				}
			} else {// 1 "״̬Ϊ��,����״̬ "// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fState"));
			}
			
			if(!StringUtils.isEmpty(fAuditorNumber)) {
				FilterInfo filterauditUser = new FilterInfo();
				filterauditUser.getFilterItems().add(new FilterItemInfo("number", fAuditorNumber));
				EntityViewInfo viewauditUser = new EntityViewInfo();
				viewauditUser.setFilter(filterauditUser);
				UserCollection auditUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewauditUser);
				if (auditUserColl.size() > 0){
					UserInfo auditorUser = auditUserColl.get(0);
					info.setAuditor(auditorUser);
				} else {// 1 "�����˱��� "// 2 " ��ϵͳ�в����ڣ�"
					throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fAuditorNumber1"));
				}
			}
			
			if(!StringUtils.isEmpty(fAuditTime)){
				// �������ڸ�ʽ��֤
//				info.setAuditTime(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "SettlementCostSplit_Import_spsjcw")).getTime());
				info.setAuditTime(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "SettlementCostSplit_Import_spsjcw")));
			}

		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		return info;
	}

	private SettlementCostSplitEntryInfo transmitEntry(Hashtable hashtable, Context context) throws TaskExternalException{
		entryInfo = new SettlementCostSplitEntryInfo();
		/**
		 * �õ�Excel�з�¼��Ϣ
		 */
		String fSettlementBillNumber = ((String) ((DataToken) hashtable.get("FSettlementBill_number")).data).trim();
		// 13.��������������
		String fCurProjectLongNumber = ((String) ((DataToken) hashtable.get("FCurProject_longNumbers")).data).trim();
		// 14.��������ɱ���Ŀ
		String fCostAccountNumber = ((String) ((DataToken) hashtable.get("FCostAccount_number")).data).trim();
		// 15.��ͬǩԼ���
		String fEntrysContractAmt = ((String) ((DataToken) hashtable.get("FEntrys_contractAmt")).data).trim();
		// 16.��ͬ������
		String fEntrysChangeAmt = ((String) ((DataToken) hashtable.get("FEntrys_changeAmt")).data).trim();
		// 17.��ֱ���(%)
		String fEntrysSplitScale = ((String) ((DataToken) hashtable.get("FEntrys_splitScale")).data).trim();
		// 18.�������
		String fEntrysAmount = ((String) ((DataToken) hashtable.get("FEntrys_amount")).data).trim();
		// 19.�������޽���
		String fEntrysGrtSplitAmt = ((String) ((DataToken) hashtable.get("FEntrys_grtSplitAmt")).data).trim();
		// 20.������׼  
		String fEntrysSplitType = ((String) ((DataToken) hashtable.get("FEntrys_splitType")).data).trim();
		// 21.ֱ�ӹ���
		String fProductNumber = ((String) ((DataToken) hashtable.get("FEntrys$product_number")).data).trim();
		
		/**
		 * �жϲ����ֶβ���Ϊ��
		 */
		if (StringUtils.isEmpty(fCurProjectLongNumber)) {
			// "����������������Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fCurProjectCodingNumber"));
		}
		if (StringUtils.isEmpty(fCostAccountNumber)) {
			// "��������ɱ���Ŀ����Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fCostAccountNumber"));
		}
		if (!StringUtils.isEmpty(fEntrysContractAmt)) {
			// "��ͬǩԼ���"
			this.strTOnumber(fEntrysContractAmt, getResource(context, "SettlementCostSplit_Import_fEntrysContractPrice"), context);
		} else {
			// "��ͬǩԼ����Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fEntrysContractPriceNotNull"));
		}
		if (!StringUtils.isEmpty(fEntrysChangeAmt)) {
			// "��ͬ������"
			this.strTOnumber(fEntrysChangeAmt, getResource(context, "SettlementCostSplit_Import_fEntrysChangeAmt"), context);
		} else { 
			// "��ͬ�������Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fEntrysChangeAmtNotNull"));
		}
		if (!StringUtils.isEmpty(fEntrysSplitScale)) {
			if (FDCTransmissionHelper.isRangedInHundred(fEntrysSplitScale) == null){
				// "¼��Ĳ�ֱ���(%)����0~100֮�䣡"
				throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fEntrysSplitScale"));
			}
		} else {
			// "��ֱ���(%)����Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fEntrysSplitScaleNotNull"));
		}
		if (!StringUtils.isEmpty(fEntrysAmount)) {
			// "�������"
			this.strTOnumber(fEntrysAmount, getResource(context, "SettlementCostSplit_Import_fEntrysAmount"), context);
		} else {
			// "��������Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fEntrysAmountNotNull"));
		}
		if (!StringUtils.isEmpty(fEntrysGrtSplitAmt)) {
			// "�������޽���"
			this.strTOnumber(fEntrysGrtSplitAmt, getResource(context, "SettlementCostSplit_Import_fEntrysGrtSplitAmt"), context);
		} else {
			// "�������޽����Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fEntrysGrtSplitAmtNotNull"));
		}
//		
//		CostSplitTypeEnum costSplitTypeEnum = null;
//		if(!StringUtils.isEmpty(fEntrysSplitType)) {
//			if(stateMap.get(fEntrysSplitType) == null) {
//				throw new TaskExternalException(getResource(context, "CostSplitTypeEnum"));
//			}
//			costSplitTypeEnum = CostSplitTypeEnum.getEnum(stateMap.get(fEntrysSplitType).toString());
//			if(costSplitTypeEnum == null) {
//				throw new TaskExternalException(getResource(context, "CostSplitTypeEnum"));
//			} else {
//				entryInfo.setSplitType(costSplitTypeEnum);
//			}
//		}
//		
		if(!StringUtils.isEmpty(fEntrysSplitType)) {
			if(!fEntrysSplitType.equals(getResource(context, "Import_StateProdsplit"))) {
				//��������ȷ�Ĺ�����׼
				throw new TaskExternalException(getResource(context, "CostSplitTypeEnum"));
			}else
			{
				entryInfo.setSplitType(CostSplitTypeEnum.getEnum(fEntrysSplitType));
			}
		}
		
		/**
		 * ��¼��Ϣ��ֵ
		 */
		CurProjectInfo curProjectInfo = null;
		try {
			// ��������������  fCurProjectNumber
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectLongNumber.trim().replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(context, "CurProjectLongNumberNotFound"));
			}
			
			String infoCurprojectID = info.getCurProject().getId().toString();
			if(!curProjectInfo.getId().toString().equals(infoCurprojectID)) {
				throw new TaskExternalException(getResource(context, "InfoCurProject"));
			}
			
			// ��������ɱ���Ŀ   fCostAccountNumber
			FilterInfo filtercostAccount = new FilterInfo();
			filtercostAccount.getFilterItems().add(new FilterItemInfo("codingNumber", fCostAccountNumber.replace('!', '.')));
			filtercostAccount.getFilterItems().add(new FilterItemInfo("curProject", curProjectInfo.getId().toString()));
			EntityViewInfo viewcostAccount = new EntityViewInfo();
			viewcostAccount.setFilter(filtercostAccount);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewcostAccount);
			if (costAccountColl.size() > 0){
				costAccount = costAccountColl.get(0);
			}else{// 1 "�ɱ���Ŀ����Ϊ��,��óɱ���Ŀ����  "// 2 " ��ϵͳ�в�����"
				throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fCostAccountNumber1"));
			}
			
			if(!curProjectInfo.getId().toString().equals(
					costAccount.getCurProject().getId().toString())) {
				throw new TaskExternalException(getResource(context, "CurprojectByCostAccountNotFound"));
			}
			costAccount.setCurProject(curProjectInfo);
			entryInfo.setCostAccount(costAccount);
	
			FilterInfo filtercontractSettlementBill = new FilterInfo();
			filtercontractSettlementBill.getFilterItems().add(new FilterItemInfo("number", fSettlementBillNumber));
			EntityViewInfo viewcontractSettlementBill = new EntityViewInfo();
			viewcontractSettlementBill.setFilter(filtercontractSettlementBill);
			ContractSettlementBillCollection contractSettlementBillColl = ContractSettlementBillFactory.getLocalInstance(context).getContractSettlementBillCollection(viewcontractSettlementBill);
			if (contractSettlementBillColl.size() > 0){
				contractSettlementBill = contractSettlementBillColl.get(0);
			}else{
				throw new TaskExternalException(getResource(context, "SettlementCostSplit_Import_fSettlementBillNumber1"));
			}
			
			// ��ͬ����
			ContractBillInfo contractBillInfo = null;
			FilterInfo contractBillFile = new FilterInfo();
			contractBillFile.getFilterItems().add(new FilterItemInfo("id", contractSettlementBill.getContractBill().getId().toString()));
			EntityViewInfo contractBillView = new EntityViewInfo();
			contractBillView.setFilter(contractBillFile);
			ContractBillCollection contractBillCollection = ContractBillFactory.getLocalInstance(context).getContractBillCollection(contractBillView);
			if(contractBillCollection.size() > 0) {
				contractBillInfo = contractBillCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(context, "ContractNotFound"));
			}
			
			BigDecimal entryAmount = FDCTransmissionHelper.strToBigDecimal(fEntrysAmount);
			// ��ͬǩԼ���
			BigDecimal contractAmt = contractBillInfo.getOriginalAmount();
			entryInfo.setContractAmt(contractAmt);
			// ��ͬ������
			BigDecimal settlePrice = contractSettlementBill.getSettlePrice();
			if(settlePrice == null) {
				settlePrice = FDCHelper.ZERO;
			}
			entryInfo.setChangeAmt(settlePrice.subtract(contractAmt));
			// ��ֱ���(%)
			entryInfo.setSplitScale(entryAmount.divide(settlePrice, 6, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			// �������
			entryInfo.setAmount(new BigDecimal(fEntrysAmount));
			if(fCostAccountNumber.replace('!', '.').indexOf(".") == -1) {
				entryInfo.setIsLeaf(false);
			} else {
				entryInfo.setIsLeaf(true);
			}
			// �������޽���  
			entryInfo.setGrtSplitAmt(contractSettlementBill.getGuaranteAmt());
			// ֱ�ӹ���
			if(!StringUtils.isEmpty(fProductNumber)) {
				FilterInfo filterproductType = new FilterInfo();
				filterproductType.getFilterItems().add(new FilterItemInfo("number", fProductNumber.trim()));
				EntityViewInfo viewproductType = new EntityViewInfo();
				viewproductType.setFilter(filterproductType);
				ProductTypeCollection productTypeColl = ProductTypeFactory.getLocalInstance(context).getProductTypeCollection(viewproductType);
				if (productTypeColl.size() > 0){
					productType = productTypeColl.get(0);
					entryInfo.setProduct(productType);
				} else {
					throw new TaskExternalException(getResource(context, "ProductTypeNotFound"));
				}
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		return entryInfo;
	}
	
	private SettlementCostSplitInfo getSettlementCostSplitInfo(String number, Context ctx) throws TaskExternalException{
		try {
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("number", number));
			EntityViewInfo viewInfo = new EntityViewInfo();
			viewInfo.setFilter(filterInfo);
			ContractSettlementBillCollection coll = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillCollection(viewInfo);
			if(coll.size() == 0) {
				throw new TaskExternalException(getResource(ctx, "SettlementCostSplit_Import_fSettlementBillNumber1"));
			}
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("settlementBill.id", coll.get(0).getId().toString()));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			SettlementCostSplitCollection settlementCostSplitCollection = SettlementCostSplitFactory.getLocalInstance(ctx).getSettlementCostSplitCollection(view);
			if (settlementCostSplitCollection.size() > 0){
				SettlementCostSplitInfo info = settlementCostSplitCollection.get(0);
				if (info != null) {
					return info;
				}
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		return null;
	}

	/**
	 * 
	 * @description		�ж�������ַ������Ƿ�����������
	 * @author			dingwen_yong
	 * @createDate		2011-6-15
	 * @param 			str			��Ҫ�жϵ��ַ���
	 * @param 			fieldName	Ҫ�жϵ��ַ�������
	 * @version			EAS1.0
	 * @throws 			TaskExternalException 
	 * @see
	 */
	private void strTOnumber(String value, String fieldName, Context context) throws TaskExternalException {
		if (!value.matches("^[-+]?\\d+(\\.\\d+)?$")) {
			// "Ӧ��¼�������ͣ�"
			throw new TaskExternalException(fieldName + getResource(context, "SettlementCostSplit_Import_strTOnumber1"));
		}
	}
	
	/**
	 * �õ���Դ�ļ�
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
	
	// ����һ���Լ�������
	public int getSubmitType() {
		return SUBMITMULTIRECTYPE;
	}
	
	private String  getStateStr(String fState, Context ctx) {
		String stateStr = FDCBillStateEnum.SAVED_VALUE;
		if(fState.equals(getResource(ctx, "AUDITTED")))
		{
			stateStr = FDCBillStateEnum.AUDITTED_VALUE;
		}else if(fState.equals(getResource(ctx, "SUBMITTED")))
		{
			stateStr = FDCBillStateEnum.SUBMITTED_VALUE;
		}
		return stateStr;
	}
	
	// ��д���෽��
	public boolean isSameBlock(Hashtable firstData, Hashtable currentData){
		return firstData != null && currentData != null ;
	}
}
