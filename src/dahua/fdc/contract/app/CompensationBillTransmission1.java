/**
 * 
 */
package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeFactory;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductModeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;

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
public class CompensationBillTransmission1 extends AbstractDataTransmission {

	// ���빤����
	private FDCTransmissionHelper help = new FDCTransmissionHelper();
	
	/** ������Ŀ */
	private CurProjectInfo curProject;
	/** ��ͬ */
	private ContractBillInfo contractBill;
	/** ������ */
	private UserInfo createUser;
	/** ����� */
	private UserInfo auditUser;
	/** ΥԼ���� */
	private CounterclaimTypeInfo counterclaimType;
	
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
		String fCostCenterNumber = (String) ((DataToken) hashtable.get("FCostCenter_number")).data;
		// 2.������Ŀ����
		String fCurProjectCodingNumber = (String) ((DataToken) hashtable.get("FCurProject_codingNumber")).data;
		// 3.��ͬ����
		String fContractCodingNumber = (String) ((DataToken) hashtable.get("FContract_codingNumber")).data;
		// 4.���ݱ���
		String fNumber = (String) ((DataToken) hashtable.get("FNumber")).data;
		// 5.��������
		String fName = (String) ((DataToken) hashtable.get("FName")).data;
		// 6.״̬
		String fState = (String) ((DataToken) hashtable.get("FState")).data;
		// 7.�ұ����
		String fCurrencyNumber = (String) ((DataToken) hashtable.get("FCurrency_number")).data;
		// 8.ΥԼ����
		String fCompensationTypeNameL2 = (String) ((DataToken) hashtable.get("FCompensationType_name_l2")).data;
		// 9.�ۿʽ
		String fDeductMode = (String) ((DataToken) hashtable.get("FDeductMode")).data;
		// 10.����˵��
		String fMoneyDes = (String) ((DataToken) hashtable.get("FMoneyDes")).data;
		// 11.ΥԼ����
		String fBreachFaichDes = (String) ((DataToken) hashtable.get("FBreachFaichDes")).data;
		// 12.ΥԼ����
		String fCompensationAccording = (String) ((DataToken) hashtable.get("FCompensationAccording")).data;
		// 13.����˵��
		String fOtherDes = (String) ((DataToken) hashtable.get("FOtherDes")).data;
		// 14.ԭ�ҽ��
		String fOriginalAmount = (String) ((DataToken) hashtable.get("FOriginalAmount")).data;
		// 15.����
		String fExRate = (String) ((DataToken) hashtable.get("FExRate")).data;
		// 16.��λ�ҽ��
		String fAmount = (String) ((DataToken) hashtable.get("FAmount")).data;
		// 17.�Ƿ�������ƾ֤
		String fFiVouchered = (String) ((DataToken) hashtable.get("fFiVouchered")).data;
		// 18.�Ƶ�������
		String fCreatorNameL2 = (String) ((DataToken) hashtable.get("FCreator_name_l2")).data;
		// 19.�Ƶ�ʱ��
		String fCreateTime = (String) ((DataToken) hashtable.get("FCreateTime")).data;
		// 20.���������
		String fAuditorNameL2 = (String) ((DataToken) hashtable.get("FAuditor_name_l2")).data;
		// 21.���ʱ��
		String fAuditTime = (String) ((DataToken) hashtable.get("FAuditTime")).data;
		
		
		/**
		 * �жϲ����ֶ��Ƿ�Ϊ�գ��Լ�ÿ���ֶεĳ���(���ֶβ�Ϊ��ʱ���ж��䳤���Ƿ���Ч)
		 */
		if (!StringUtils.isEmpty(fCostCenterNumber) && fCostCenterNumber.length() > 40) {
			throw new TaskExternalException("��֯�����ֶγ��Ȳ��ܳ���40��");
		}
		if (!StringUtils.isEmpty(fCurProjectCodingNumber)) {
			if (fCurProjectCodingNumber.length() > 40) {
				throw new TaskExternalException("������Ŀ�����ֶγ��Ȳ��ܳ���40��");
			}
		} else {
			throw new TaskExternalException("������Ŀ���벻��Ϊ�գ�");
		}
		if (!StringUtils.isEmpty(fContractCodingNumber)) {
			if (fContractCodingNumber.length() > 80) {
				throw new TaskExternalException("��ͬ�����ֶγ��Ȳ��ܳ���80��");
			}
		} else {
			throw new TaskExternalException("��ͬ���벻��Ϊ�գ�");
		}
		if (!StringUtils.isEmpty(fNumber)) {
			if (fNumber.length() > 80) {
				throw new TaskExternalException("���ݱ����ֶγ��Ȳ��ܳ���80��");
			}
		} else {
			throw new TaskExternalException("���ݱ��벻��Ϊ�գ�");
		}
		if (!StringUtils.isEmpty(fName)) {
			if (fName.length() > 80) {
				throw new TaskExternalException("���������ֶγ��Ȳ��ܳ���80��");
			}
		} else {
			throw new TaskExternalException("�������Ʋ���Ϊ�գ�");
		}
		if (!StringUtils.isEmpty(fCurrencyNumber) && fCurrencyNumber.length() > 40) {
			throw new TaskExternalException("�ұ�����ֶγ��Ȳ��ܳ���40��");
		}
		if (!StringUtils.isEmpty(fCompensationTypeNameL2) && fCompensationTypeNameL2.length() > 40) {
			throw new TaskExternalException("ΥԼ�����ֶγ��Ȳ��ܳ���40��");
		}
		if (!StringUtils.isEmpty(fDeductMode) && fDeductMode.length() > 40) {
			throw new TaskExternalException("�ۿʽ�ֶγ��Ȳ��ܳ���40��");
		}
		if (!StringUtils.isEmpty(fMoneyDes) && fMoneyDes.length() > 40) {
			throw new TaskExternalException("����˵���ֶγ��Ȳ��ܳ���40��");
		}
		if (!StringUtils.isEmpty(fBreachFaichDes) && fBreachFaichDes.length() > 40) {
			throw new TaskExternalException("ΥԼ�����ֶγ��Ȳ��ܳ���40��");
		}
		if (!StringUtils.isEmpty(fCompensationAccording) && fCompensationAccording.length() > 40) {
			throw new TaskExternalException("ΥԼ�����ֶγ��Ȳ��ܳ���40��");
		}
		if (!StringUtils.isEmpty(fOtherDes) && fOtherDes.length() > 40) {
			throw new TaskExternalException("����˵���ֶγ��Ȳ��ܳ���40��");
		}
		if (StringUtils.isEmpty(fOriginalAmount)) {
			
			String str = fOriginalAmount.trim();
			// ����ָ����ʾ����ֵ�� 1.00002213E15
			if (str.toLowerCase().indexOf("e") > -1) {
				try {
					new BigDecimal(str);
					help.valueFormat("ԭ�ҽ��", fOriginalAmount, "Double", false, -1);
				} catch (NumberFormatException e) {
					throw new TaskExternalException("ԭ�ҽ��Ӧ��¼�������ͣ�");
				}
			}
		} else {
			throw new TaskExternalException("ԭ�ҽ���Ϊ�գ�");
		}
		if (!StringUtils.isEmpty(fCreatorNameL2)) {
			if (fCreatorNameL2.length() > 40) {
				throw new TaskExternalException("�Ƶ��˱����ֶγ��Ȳ��ܳ���40��");
			}
		} else {
			throw new TaskExternalException("�Ƶ��˱��벻��Ϊ�գ�");
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException("�Ƶ�ʱ�䲻��Ϊ�գ�");
		} 
		if (!StringUtils.isEmpty(fAuditorNameL2) && fAuditorNameL2.length() > 40) {
			throw new TaskExternalException("�����˱����ֶγ��Ȳ��ܳ���40��");
		}

		/**
		 * ��ֵ���������
		 */
		try {
			curProject = CurProjectFactory.getLocalInstance(context).getCurProjectInfo("where fLongNumber = '" + fCurProjectCodingNumber + "'");
			if (curProject != null) {
				info.setCurProject(curProject);
				// ��֯����
				CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); // ������Ŀ��Ӧ�ɱ�������֯
//				curProject.getCostCenter().getLongNumber().toString();  // ������Ŀ��Ӧ�ɱ����ĳ�����
				if (!StringUtils.isEmpty(fCostCenterNumber) || !fCostCenterNumber.trim().equals(costCenterOrgUnit.getLongNumber().toString())) {
					throw new TaskExternalException("��֯�������ڹ�����Ŀ����Ӧ�ĳɱ����Ĳ�����!");
				}
				info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());				
			} else {
				throw new TaskExternalException("������Ŀ����Ϊ��,��ù�����Ŀ���� " + fCurProjectCodingNumber + " ��ϵͳ�в����ڣ�");
			}
			// ��ͬ����
			contractBill = ContractBillFactory.getLocalInstance(context).getContractBillInfo("where fNumber = '" + fContractCodingNumber + "'");
			if (contractBill != null) {
				info.setContract(contractBill);
			} else {
				throw new TaskExternalException("��ͬ����Ϊ��,��ú�ͬ���� " + fContractCodingNumber + " ��ϵͳ�в����ڣ�");
			}
			// ���ݱ���
			info.setNumber(fNumber);
			// ��������
			info.setName(fName);
			// ״̬ <���״̬Ϊ��ʱ,������ֵΪ"������">
			if (!StringUtils.isEmpty(fState)) {
				if (fState.length() > 40) {
					throw new TaskExternalException("״̬�ֶγ��Ȳ��ܳ���40��");
				}
				if (fState.trim().equals("������") || fState.trim().equals("") || fState.trim() == null) {
					info.setState(FDCBillStateEnum.AUDITTED);
					// ������״̬Ϊ"������"ʱ,����Ҫ���������˺�����ʱ���ֵ��
					if (!StringUtils.isEmpty(fAuditorNameL2)) {
						// ���������
						auditUser = UserFactory.getLocalInstance(context).getUserInfo("where fNumber = '" + fAuditorNameL2 + "'");
						if (auditUser != null) {
							info.setCreator(auditUser);
						} else {
							throw new TaskExternalException("����˱���Ϊ��,���������˱��� " + fAuditorNameL2 + " ��ϵͳ�в����ڣ�");
						}
					} else {
						throw new TaskExternalException("��������״̬�µ��������ֶβ���Ϊ�գ�");
					}
					if (!StringUtils.isEmpty(fAuditTime)) {
						// ���ʱ��
//						info.setCreateTime(new Timestamp(help.checkDateFormat(fAuditTime).getTime()));
					} else {
						throw new TaskExternalException("��������״̬�µ����������ֶβ���Ϊ�գ�");
					}
				} else if (fState.trim().equals("����")) {
					info.setState(FDCBillStateEnum.SAVED);
				} else if (fState.trim().equals("���ύ")) {
					info.setState(FDCBillStateEnum.SUBMITTED);
				} else {
					throw new TaskExternalException("״̬¼����Ч��");
				}
				if (fState.trim().equals("������")) {
					
				}
			}
			// �ұ����
			CurrencyCollection currencyCollection = null;
			if (fCurrencyNumber.trim().equals("") || fCurrencyNumber.trim() == null) {
				try {
					currencyCollection = CurrencyFactory.getLocalInstance(context).getCurrencyCollection("where FNumber = 'glc'");
				} catch (BOSException e) {
					e.printStackTrace();
					throw new TaskExternalException(e.getMessage());
				}
			} else {
				try {
					currencyCollection = CurrencyFactory.getLocalInstance(context).getCurrencyCollection("where FNumber = '"+fCurrencyNumber+"'");
				} catch (BOSException e) {
					e.printStackTrace();
					throw new TaskExternalException(e.getMessage());
				}
			}
			if (currencyCollection!=null) {
				info.setCurrency(currencyCollection.get(0));
			}
			// ΥԼ����
			counterclaimType = CounterclaimTypeFactory.getLocalInstance(context).getCounterclaimTypeInfo("where FName = '"+fCompensationTypeNameL2+"'");
			if (counterclaimType != null) {
				info.setCompensationType(counterclaimType);
			} else {
				
			}
			// �ۿʽ
			if (fDeductMode.trim().equals("����ۿ�")) {
				info.setDeductMode(DeductModeEnum.JSKK);
			} else {
				throw new TaskExternalException("�ۿʽ " + fDeductMode + " ¼����Ч��");
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
			if(StringUtils.isEmpty(fExRate)){
				info.setExRate(FDCHelper.ONE);
			} else {
				info.setExRate(new BigDecimal(fExRate));
			}
			// ��λ�ҽ��
			if (Integer.parseInt(fAmount) == 0) {
				// ������ҽ��Ϊ��ʱ,������ֵΪ (���*����)
				info.setAmount(new BigDecimal(Integer.parseInt(fOriginalAmount) * Integer.parseInt(fExRate)));
			} else {
				info.setAmount(new BigDecimal(fAmount));
			}
			// �Ƿ�������ƾ֤
			if (fFiVouchered.trim().equals("��") || fFiVouchered.trim().equals("true") || fFiVouchered.trim().equals("1") || fFiVouchered.trim().equals("")) {
				info.setFiVouchered(true);
			} else {
				info.setFiVouchered(false);
			}
			// �Ƶ�������
			createUser = UserFactory.getLocalInstance(context).getUserInfo("where fNumber = '" + fCreatorNameL2 + "'");
			if (createUser != null) {
				info.setCreator(createUser);
			} else {
				throw new TaskExternalException("�Ƶ��˱���Ϊ��,�����Ƶ��˱��� " + fCreatorNameL2 + " ��ϵͳ�в����ڣ�");
			}
			// �Ƶ�ʱ��
//			info.setCreateTime(new Timestamp(help.checkDateFormat(fCreateTime).getTime()));
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		}
		return info;
	}


}
