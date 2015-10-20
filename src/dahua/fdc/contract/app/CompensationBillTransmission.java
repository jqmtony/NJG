/**
 * 
 */
package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeAuxFactory;
import com.kingdee.eas.basedata.assistant.ExchangeAuxInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeCollection;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeFactory;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductModeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.CompensationBillCollection;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
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
public class CompensationBillTransmission extends AbstractDataTransmission {

	// ��Դ�ļ�
	private static String resource = "com.kingdee.eas.fdc.contract.CompensationBillResource";

	/** ������Ŀ */
	private CurProjectInfo curProject = null;
	/** ��ͬ */
	private ContractBillInfo contractBill = null;
	/** ������ */
	private UserInfo createUser = null;
	/** ����� */
	private UserInfo auditUser = null;
	/** ΥԼ���� */
	private CounterclaimTypeInfo counterclaimType = null;
	/** ���� */
	private CurrencyInfo currency = null;
	
	/**
	 * @description		
	 * @author			dingwen_yong		
	 * @createDate		2011-6-13
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	protected ICoreBase getController(Context context) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = CompensationBillFactory.getLocalInstance(context);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * @description		
	 * @author			dingwen_yong		
	 * @createDate		2011-6-13
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		
		CompensationBillInfo info = new CompensationBillInfo();
		/**
		 * ��ȡExcel�ı��ж�Ӧ��ֵ
		 */
		// 1.��֯���� 
		String fCostCenterNumber = ((String) ((DataToken) hashtable.get("FOrgUnit_number")).data).trim();
		// 2.������Ŀ����
		String fCurProjectCodingNumber = ((String) ((DataToken) hashtable.get("FCurProject_codingNumber")).data).trim();
		// 3.��ͬ����
		String fContractCodingNumber = ((String) ((DataToken) hashtable.get("FContract_codingNumber")).data).trim();
		// 4.���ݱ���
		String fNumber = ((String) ((DataToken) hashtable.get("FNumber")).data).trim();
		// 5.��������
		String fName = ((String) ((DataToken) hashtable.get("FName")).data).trim();
		// 6.״̬
		String fState = ((String) ((DataToken) hashtable.get("FState")).data).trim();
		// 7.�ұ����
		String fCurrencyNumber = ((String) ((DataToken) hashtable.get("FCurrency_number")).data).trim();
		// 8.ΥԼ����
		String fCompensationTypeNameL2 = ((String) ((DataToken) hashtable.get("FCompensationType_name_l2")).data).trim();
		// 9.�ۿʽ
		String fDeductMode = ((String) ((DataToken) hashtable.get("FDeductMode")).data).trim();
		// 10.����˵��
		String fMoneyDes = ((String) ((DataToken) hashtable.get("FMoneyDes")).data).trim();
		// 11.ΥԼ����
		String fBreachFaichDes = ((String) ((DataToken) hashtable.get("FBreachFaichDes")).data).trim();
		// 12.ΥԼ����
		String fCompensationAccording = ((String) ((DataToken) hashtable.get("FCompensationAccording")).data).trim();
		// 13.����˵��
		String fOtherDes = ((String) ((DataToken) hashtable.get("FOtherDes")).data).trim();
		// 14.ԭ�ҽ��
		String fOriginalAmount = ((String) ((DataToken) hashtable.get("FOriginalAmount")).data).trim();
		// 15.����
		String fExRate = ((String) ((DataToken) hashtable.get("FExRate")).data).trim();
		// 16.��λ�ҽ��
		String fAmount = ((String) ((DataToken) hashtable.get("FAmount")).data).trim();
		// 17.�Ƿ�������ƾ֤
		String fFiVouchered = ((String) ((DataToken) hashtable.get("FFiVouchered")).data).trim();
		// 18.�Ƶ�������
		String fCreatorNameL2 = ((String) ((DataToken) hashtable.get("FCreator_name_l2")).data).trim();
		// 19.�Ƶ�ʱ��
		String fCreateTime = ((String) ((DataToken) hashtable.get("FCreateTime")).data).trim();
		// 20.���������
		String fAuditorNameL2 = ((String) ((DataToken) hashtable.get("FAuditor_name_l2")).data).trim();
		// 21.���ʱ��
		String fAuditTime = ((String) ((DataToken) hashtable.get("FAuditTime")).data).trim();
		
		
		/**
		 * �жϲ����ֶ��Ƿ�Ϊ�գ��Լ�ÿ���ֶεĳ���(���ֶβ�Ϊ��ʱ���ж��䳤���Ƿ���Ч)
		 */
		if (!StringUtils.isEmpty(fCostCenterNumber) && fCostCenterNumber.length() > 40) {
			// "��֯�����ֶγ��Ȳ��ܳ���40��"
			throw new TaskExternalException(getResource(context, "Import_fCostCenterNumber"));
		}
		if (!StringUtils.isEmpty(fCurProjectCodingNumber)) {
			if (fCurProjectCodingNumber.length() > 40) {
				// "������Ŀ�����ֶγ��Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "Import_fCurProjectCodingNumber"));
			}
		} else {
			// "������Ŀ���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "Import_fCurProjectCodingNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fContractCodingNumber)) {
			if (fContractCodingNumber.length() > 80) {
				// "��ͬ�����ֶγ��Ȳ��ܳ���80��"
				throw new TaskExternalException(getResource(context, "Import_fContractCodingNumber"));
			}
		} else {
			// "��ͬ���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "Import_fContractCodingNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fNumber)) {
			if (fNumber.length() > 80) {
				// "���ݱ����ֶγ��Ȳ��ܳ���80��"
				throw new TaskExternalException(getResource(context, "Import_fNumber"));
			}
		} else {
			// "���ݱ��벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "Import_fNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fName)) {
			if (fName.length() > 80) {
				// "���������ֶγ��Ȳ��ܳ���80��"
				throw new TaskExternalException(getResource(context, "Import_fName"));
			}
		} else {
			// "�������Ʋ���Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "Import_fNameNotNull"));
		}
		if (!StringUtils.isEmpty(fCurrencyNumber) && fCurrencyNumber.length() > 40) {
			// "�ұ�����ֶγ��Ȳ��ܳ���40��"
			throw new TaskExternalException(getResource(context, "Import_fCurrencyNumber"));
		}
		if (!StringUtils.isEmpty(fCompensationTypeNameL2) && fCompensationTypeNameL2.length() > 40) {
			// "ΥԼ�����ֶγ��Ȳ��ܳ���40��"
			throw new TaskExternalException(getResource(context, "Import_fCompensationTypeNameL2"));
		}
		if (!StringUtils.isEmpty(fDeductMode) && fDeductMode.length() > 40) {
			// "�ۿʽ�ֶγ��Ȳ��ܳ���40��"
			throw new TaskExternalException(getResource(context, "Import_fDeductMode"));
		}
		if (!StringUtils.isEmpty(fMoneyDes) && fMoneyDes.length() > 40) {
			// "����˵���ֶγ��Ȳ��ܳ���40��"
			throw new TaskExternalException(getResource(context, "Import_fMoneyDes"));
		}
		if (!StringUtils.isEmpty(fBreachFaichDes) && fBreachFaichDes.length() > 40) {
			// "ΥԼ�����ֶγ��Ȳ��ܳ���40��"
			throw new TaskExternalException(getResource(context, "Import_fBreachFaichDes"));
		}
		if (!StringUtils.isEmpty(fCompensationAccording) && fCompensationAccording.length() > 40) {
			// "ΥԼ�����ֶγ��Ȳ��ܳ���40��"
			throw new TaskExternalException(getResource(context, "Import_fCompensationAccording"));
		}
		if (!StringUtils.isEmpty(fOtherDes) && fOtherDes.length() > 40) {
			// "����˵���ֶγ��Ȳ��ܳ���40��"
			throw new TaskExternalException(getResource(context, "Import_fOtherDes"));
		}
		if (!StringUtils.isEmpty(fOriginalAmount)) {
			if (!fOriginalAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,15})||0$")) {
				// "ԭ�ҽ��¼�벻�Ϸ���"
				throw new TaskExternalException(getResource(context, "Import_fOriginalAmount"));
			}
		} else {
			// "ԭ�ҽ���Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "Import_fOriginalAmountNotNull"));
		}
		if (!StringUtils.isEmpty(fAmount)) {
			if (!fAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,15})||0$")) {
				// ���ҽ��¼�벻�Ϸ�
				throw new TaskExternalException(getResource(context, "Import_fAmount5"));
			}
		}
		if (!StringUtils.isEmpty(fCreatorNameL2)) {
			if (fCreatorNameL2.length() > 40) {
				// "�Ƶ��˱����ֶγ��Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "Import_fCreatorNameL2"));
			}
		} else {
			// "�Ƶ��˱��벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "Import_fCreatorNameL2NotNull"));
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			// "�Ƶ�ʱ�䲻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "Import_fCreateTime"));
		} 
		if (!StringUtils.isEmpty(fAuditorNameL2) && fAuditorNameL2.length() > 40) {
			// "�����˱����ֶγ��Ȳ��ܳ���40��"
			throw new TaskExternalException(getResource(context, "Import_fAuditorNameL2"));
		}

		/**
		 * ��ֵ���������
		 */ 
		try {
			// �ֶ��ж�ΥԼ�������Ƿ��ظ�
			FilterInfo filterNumber = new FilterInfo();
			filterNumber.getFilterItems().add(new FilterItemInfo("number", fNumber));
			EntityViewInfo viewNumber = new EntityViewInfo();
			viewNumber.setFilter(filterNumber);
			CompensationBillCollection compensationBillColl = CompensationBillFactory.getLocalInstance(context).getCompensationBillCollection(viewNumber);
			if (compensationBillColl.size() > 0) {
				// ΥԼ�����벻���ظ�
				throw new TaskExternalException(getResource(context, "Import_fNumber_isExents"));
			}
			// �ֶ��ж�ΥԼ�������Ƿ��ظ�
			FilterInfo filterName = new FilterInfo();
			filterName.getFilterItems().add(new FilterItemInfo("name", fName));
			EntityViewInfo viewName = new EntityViewInfo();
			viewName.setFilter(filterName);
			CompensationBillCollection compensationBillColl1 = CompensationBillFactory.getLocalInstance(context).getCompensationBillCollection(viewName);
			if (compensationBillColl1.size() > 0) {
				// ΥԼ�����Ʋ����ظ�
				throw new TaskExternalException(getResource(context, "Import_fName_isExents"));
			}
			// ������Ŀ����
			FilterInfo filterCurProject = new FilterInfo();
			filterCurProject.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectCodingNumber.replace('.', '!')));
			EntityViewInfo viewCurProject = new EntityViewInfo();
			viewCurProject.setFilter(filterCurProject);
			CurProjectCollection curProjectBillColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(viewCurProject);
			if (curProjectBillColl.size() > 0){
				curProject = curProjectBillColl.get(0);
				info.setCurProject(curProject);
				// ��֯������
				CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); //  ������Ŀ��Ӧ�ɱ�������֯
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", costCenterOrgUnit.getId().toString()));
				EntityViewInfo view1 = new EntityViewInfo();
				view1.setFilter(filter1);
				CostCenterOrgUnitCollection ccouc = CostCenterOrgUnitFactory.getLocalInstance(context).getCostCenterOrgUnitCollection(view1);
				if (!StringUtils.isEmpty(fCostCenterNumber) && !fCostCenterNumber.replace('.', '!').equals(ccouc.get(0).getLongNumber())) {
					// "��֯�������ڹ�����Ŀ����Ӧ�ĳɱ����Ĳ�����!"
 					throw new TaskExternalException(getResource(context, "Import_fCostCenterNumber1"));
					// ԭת����֯���󷽷� <�ݲ��Ƽ�ʹ��> (FullOrgUnitInfo)FullOrgUnitInfo.class.cast(costCenterOrgUnit)
				}
				info.setOrgUnit(ccouc.get(0).castToFullOrgUnitInfo());
			} else {
				// 1 "������Ŀ����Ϊ��,��ù�����Ŀ���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "Import_fCurProjectCodingNumber1") + fCurProjectCodingNumber + getResource(context, "Import_NOTNULL"));
			}
			// ��ͬ����
			FilterInfo filtercontractBill = new FilterInfo();
			filtercontractBill.getFilterItems().add(new FilterItemInfo("number", fContractCodingNumber));
			EntityViewInfo viewcontractBill = new EntityViewInfo();
			viewcontractBill.setFilter(filtercontractBill);
			ContractBillCollection contractBillColl = ContractBillFactory.getLocalInstance(context).getContractBillCollection(viewcontractBill);
			if (contractBillColl.size() > 0){
				contractBill = contractBillColl.get(0);
				info.setContract(contractBill);
			}else{
				// 1 "��ͬ���벻����,���߸ú�ͬ���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "Import_fContractCodingNumber1") + fContractCodingNumber + getResource(context, "Import_NOTNULL"));
			}
			// ���ݱ���
			info.setNumber(fNumber);
			// ��������
			info.setName(fName);
			// ״̬ <���״̬Ϊ��ʱ,������ֵΪ"������">
			if (fState.equals(getResource(context, "AUDITTED")) || StringUtils.isEmpty(fState)) {
				info.setState(FDCBillStateEnum.AUDITTED);
				// ������״̬Ϊ"������"ʱ,����Ҫ���������˺�����ʱ���ֵ��
				if (!StringUtils.isEmpty(fAuditorNameL2)) {
					// ���������
					FilterInfo filterauditUser = new FilterInfo();
					filterauditUser.getFilterItems().add(new FilterItemInfo("number", fAuditorNameL2));
					EntityViewInfo viewauditUser = new EntityViewInfo();
					viewauditUser.setFilter(filterauditUser);
					UserCollection auditUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewauditUser);
					if (auditUserColl.size() > 0){
						auditUser = auditUserColl.get(0);
						info.setAuditor(auditUser);
					} else {
						// 1 "����˱���Ϊ��,���������˱��� "
						// 2 " ��ϵͳ�в����ڣ�"
						throw new TaskExternalException(getResource(context, "Import_fAuditorNameL21") + fAuditorNameL2 + getResource(context, "Import_NOTNULL"));
					}
				} else {
					// "��������״̬�µ��������ֶβ���Ϊ�գ�"
					throw new TaskExternalException(getResource(context, "Import_fAuditorNameL2NotNull"));
				}
				if (!StringUtils.isEmpty(fAuditTime)) {
					// ���ʱ�� ���ʱ��¼�벻��ȷ ��ʽΪ��YYYY-MM-DD
					info.setAuditTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "Import_shsjcw")).getTime()));
				} else {
					// "��������״̬�µ����������ֶβ���Ϊ�գ�"
					throw new TaskExternalException(getResource(context, "Import_fAuditTime"));
				}
			} else if (fState.equals(getResource(context, "SAVED"))) {
				info.setState(FDCBillStateEnum.SAVED);
			} else if (fState.equals(getResource(context, "SUBMITTED"))) {
				info.setState(FDCBillStateEnum.SUBMITTED);
			} else {
				// "״̬¼����Ч��"
				throw new TaskExternalException(getResource(context, "Import_fState"));
			}
			// �ұ����
			CurrencyCollection currencyCollection = null;
			FilterInfo currencyFilter = new FilterInfo();
			EntityViewInfo currencyView = new EntityViewInfo();
			if (StringUtils.isEmpty(fCurrencyNumber)) {
				currencyFilter.getFilterItems().add(new FilterItemInfo("number", "RMB"));
			} else {
				currencyFilter.getFilterItems().add(new FilterItemInfo("number",fCurrencyNumber));
			}
			currencyView.setFilter(currencyFilter);
			currencyCollection = CurrencyFactory.getLocalInstance(context).getCurrencyCollection(currencyView);
			if (currencyCollection.size() > 0) {
				currency = currencyCollection.get(0);
				info.setCurrency(currency);
			} else {
				// �ñ��ֱ�����ϵͳ�в�����
				throw new TaskExternalException(getResource(context, "Import_gbzbm") + fCurrencyNumber + getResource(context, "Import_NOTNULL"));
			}
			// ΥԼ����
			if (!StringUtils.isEmpty(fCompensationTypeNameL2)) {
				FilterInfo filtercounterclaimType = new FilterInfo();
				filtercounterclaimType.getFilterItems().add(new FilterItemInfo("name", fCompensationTypeNameL2));
				EntityViewInfo viewcounterclaimType = new EntityViewInfo();
				viewcounterclaimType.setFilter(filtercounterclaimType);
				CounterclaimTypeCollection counterclaimTypeColl = CounterclaimTypeFactory.getLocalInstance(context).getCounterclaimTypeCollection(viewcounterclaimType);
				if (counterclaimTypeColl.size() > 0){
					counterclaimType = counterclaimTypeColl.get(0);
					info.setCompensationType(counterclaimType);
				}
			}
			// �ۿʽ
			if (!StringUtils.isEmpty(fDeductMode)) {
				if (fDeductMode.equals(getResource(context, "JSKK"))) {
					info.setDeductMode(DeductModeEnum.JSKK);
				} else {
					// 1 "�ۿʽ "
					// 2 "��ϵͳ�в�����"
					throw new TaskExternalException(getResource(context, "Import_fDeductMode1") + fDeductMode + getResource(context, "Import_NOTNULL"));
				}
			}
			// ����˵��
			info.setMoneyDes(fMoneyDes);
			// ΥԼ����
			info.setBreachFaichDes(fBreachFaichDes);
			// ΥԼ����
			info.setCompensationAccording(fCompensationAccording);
			// ����˵��
			info.setOtherDes(fOtherDes);
			// ԭ�ҽ��
			info.setOriginalAmount(new BigDecimal(fOriginalAmount));
			// ����
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ExchangeRateInfo exchangeRateInfo = new ExchangeRateInfo();
			if (StringUtils.isEmpty(fExRate)) {  // �������Ϊ�գ���ȡ��ǰ�ұ����
				if (currency.getNumber().equals("RMB")) {
					fExRate = "1.00";
				} else {
					// ��ѯĿ��ұ� <���̶�ԭ��>
					CurrencyCollection rmbColl = null;
					FilterInfo rmbFilter = new FilterInfo();
					EntityViewInfo rmbView = new EntityViewInfo();
					rmbFilter.getFilterItems().add(new FilterItemInfo("number", "RMB"));
					rmbView.setFilter(rmbFilter);
					rmbColl = CurrencyFactory.getLocalInstance(context).getCurrencyCollection(rmbView);
					CurrencyInfo rmb = rmbColl.get(0);
					filter.getFilterItems().add(new FilterItemInfo("targetCurrency", rmb.getId(), CompareType.EQUALS));//Ŀ��ұ�
					filter.getFilterItems().add(new FilterItemInfo("SourceCurrency", currency.getId(), CompareType.EQUALS));//ԭ�ұ�
					filter.setMaskString("#0 and #1");
					view.setFilter(filter);
					ExchangeAuxInfo xinfo = ExchangeAuxFactory.getLocalInstance(context).getExchangeAuxCollection(view).get(0);
					if (xinfo != null) {
						exchangeRateInfo = ExchangeRateFactory.getLocalInstance(context).getExchangeRate(new ObjectUuidPK(xinfo.getExchangeTable().getId()), new ObjectUuidPK(xinfo.getSourceCurrency().getId()), new ObjectUuidPK(xinfo.getTargetCurrency().getId()), Calendar.getInstance().getTime());
						BigDecimal bigDecimal = exchangeRateInfo.getConvertRate();
						if (bigDecimal != null) {
							fExRate = bigDecimal.toString();
						}
					} else {
						fExRate = "1.00";
					}
				}
			} else {
				if (FDCTransmissionHelper.isRangedInHundred(fExRate) == null) {
					// ����¼�벻�Ϸ�
					throw new TaskExternalException(getResource(context, "Import_fExRate10"));
				}
			}
			info.setExRate(new BigDecimal(fExRate));
			// ��λ�ҽ��
			if (!StringUtils.isEmpty(fAmount)) {
				if (new BigDecimal(fAmount).compareTo(new BigDecimal("0")) != 0) {
//					info.setAmount(new BigDecimal(fAmount));
					// ���¼���˱�λ�ҽ� ���жϵ�ǰ��<��λ�ҽ���Ƿ����(ԭ�ҽ��*����)>
					BigDecimal amount = new BigDecimal(FDCTransmissionHelper.strToDouble(fOriginalAmount) * FDCTransmissionHelper.strToDouble(fExRate));
					if (new BigDecimal(fAmount).compareTo(amount) != 0) {
						// ��ǰ¼��ı�λ�ҽ�����ԭ�ҽ������ʵĳ˻�(����<���ҽ��=����*ԭ�ҽ��>)��
						throw new TaskExternalException(getResource(context, "Import_ludbwbjebdyjexyhl"));
					}
					info.setAmount(amount);
				} else {
					// ������ҽ��Ϊ��ʱ,������ֵΪ (���*����)
					info.setAmount(new BigDecimal(FDCTransmissionHelper.strToDouble(fOriginalAmount) * FDCTransmissionHelper.strToDouble(fExRate)));
				}
			} else {
				// ������ҽ��Ϊ��ʱ,������ֵΪ (���*����)
				info.setAmount(new BigDecimal(FDCTransmissionHelper.strToDouble(fOriginalAmount) * FDCTransmissionHelper.strToDouble(fExRate)));
			}
			// �Ƿ�������ƾ֤
			if (fFiVouchered.equals(getResource(context, "NO"))) {
				info.setFiVouchered(false);
			} else {
				info.setIsCompensated(true);
			}
			// �Ƶ�������
			FilterInfo filtercreateUser = new FilterInfo();
			filtercreateUser.getFilterItems().add(new FilterItemInfo("number", fCreatorNameL2));
			EntityViewInfo viewcreateUser = new EntityViewInfo();
			viewcreateUser.setFilter(filtercreateUser);
			UserCollection createUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewcreateUser);
			if (createUserColl.size() > 0){
				createUser = createUserColl.get(0);
				info.setCreator(createUser);
			}else{
				// 1 "�Ƶ��˱���Ϊ��,�����Ƶ��˱��� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "Import_fCreatorNameL21") + fCreatorNameL2 + getResource(context, "Import_NOTNULL"));
			}
			// �Ƶ�ʱ��
			info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "Import_zdsjcw")).getTime()));
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		} catch (EASBizException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		}
		return info;
	}
	
	/**
	 * �õ���Դ�ļ�
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}

}
