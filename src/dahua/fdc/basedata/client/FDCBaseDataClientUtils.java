package com.kingdee.eas.fdc.basedata.client;

import java.awt.Component;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.assistant.client.AssistantClientUtils;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.client.EditUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ����:���ز��������Ͽͻ��˹�����
 * 
 * @author jackwang date:2006-7-8
 * @version EAS5.1
 */
public class FDCBaseDataClientUtils {

	public static final String FDCBASEDATA_RESOURCE = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";

	public static final String UI_TITLE_SEPRATOR = " - "; // UI����ָ���

	public static final String ADDNEW = "addnew"; // ����

	public static final String EDIT = "edit"; // �޸�

	public static final String VIEW = "view"; // �鿴

	public static final String DISABLE_SUCCESSED = "disableSuccessed"; // ���óɹ�

	public static final String DISABLE_FAILED = "disableFailed"; // ����ʧ��

	public static final String ENABLE_SUCCESSED = "enableSuccessed"; // ���óɹ�

	public static final String ENABLE_FAILED = "enableFailed"; // ����ʧ��

	public static final String CONFIRM_DISABLE = "confirmDisable"; // ��ȷ�Ͻ���

	public static final String ADJUSTREASON = "AdjustReason";
	
	public static final String CONTRACTSOURCE = "ContractSource";

	public static final String ADJUSTTYPE = "AdjustType";

	public static final String CHANGEREASON = "ChangeReason";

	public static final String CHANGETYPE = "ChangeType";
	
	//������� 
	public static final String VISATYPE = "VisaType";


	public static final String APPORTIONTYPE = "ApportionType";

	public static final String INVITETYPE = "InviteType";

	public static final String LANDDEVELOPER = "LandDeveloper";

	public static final String PAYMENTTYPE = "PaymentType";

	public static final String PRODUCTTYPE = "ProductType";

	public static final String CONTRACTTYPE = "ContractType";

	public static final String CONTRACTDETAILDEF = "ContractDetailDef";

	public static final String PROJECT = "Project";

	public static final String DEDUCTTYPE = "DeductType";

	public static final String COSTACCOUNT = "CostAccount";

	public static final String TARGETTYPE = "TargetType";

	public static final String PROJECTTYPE = "ProjectType";

	public static final String COSTTARGET = "CostTarget";

	public static final String PROJECTSTATUS = "ProjectStatus";

	public static final String JOBTYPE = "JobType";

	public static final String SPECIALTYTYPE = "SpecialtyType";

	public static final String COUNTERCLAIMTYPE = "CounterclaimType";

	public static final String CONTRACTCODINGTYPE = "ContractCodingType";
	
	public static final String UNITDATAMANAGER = "UnitDataManager";
	
	public static final String INVALIDCOSTREASON = "InvalidCostReason";
	//��Ŀ�������
	public static final String CTRLITEM = "CtrlItem";
	
	public static final String PLANADJUSTREASON = "PlanAdjustReason";
	
	public static final String TASKTYPE = "TaskType";
	
	public static final String WORKTASK = "WorkTask";
	//����׶�
	public static final String MEASURESTAGE = "MeasureStage";
	//�ɱ�����ָ��
	public static final String MEASUREINDEX = "MeasureIndex";
	
	//�����Ŀ
	public static final String INCOMEACCOUNT = "IncomeAccount";
	/**
	 * ���ݽ���״̬���ô��ڵı���
	 * 
	 * @param ui
	 *            CoreUIObject
	 * @param bindedEntityName
	 *            String CoreUIObject�󶨵�ҵ����������
	 * @return
	 */
	public static void setupUITitle(CoreUIObject ui, String bindedEntityName) {
		if (ui != null) {
			if (ui instanceof EditUI) {
				if (ui.getOprtState().equalsIgnoreCase(OprtState.ADDNEW)) {
					// ���� - ����
					ui.setUITitle(bindedEntityName + AssistantClientUtils.UI_TITLE_SEPRATOR + EASResource.getString(AssistantClientUtils.ASSISTANT_RESOURCE, AssistantClientUtils.ADDNEW));
				} else if (ui.getOprtState().equalsIgnoreCase(OprtState.EDIT)) {
					// ���� - �޸�
					ui.setUITitle(bindedEntityName + AssistantClientUtils.UI_TITLE_SEPRATOR + EASResource.getString(AssistantClientUtils.ASSISTANT_RESOURCE, AssistantClientUtils.EDIT));
				} else if (ui.getOprtState().equalsIgnoreCase(OprtState.VIEW)) {
					// ���� - �鿴
					ui.setUITitle(bindedEntityName + AssistantClientUtils.UI_TITLE_SEPRATOR + EASResource.getString(AssistantClientUtils.ASSISTANT_RESOURCE, AssistantClientUtils.VIEW));
				}
			} else {
				ui.setUITitle(bindedEntityName);
			}

		}
	}

	/**
	 * �������ش˷������������һЩ�����ظ������ֵ
	 */
	public static void setFieldsNull(IObjectValue newData) {
		newData.setString("number", null);
		newData.setString("name", null);
	}

	/**
	 * �ж�һ��������KDBizMultiLangBox�ؼ����û������Ƿ�Ϊ��
	 * 
	 * @param mltBox
	 *            KDBizMultiLangBox
	 * @param editData
	 *            IObjectValue ��EditUI��editData
	 * @param propertyName
	 *            String ��mltBox��ֵ�������������
	 * @return boolean ������Ϊ�ջ�""ʱ����TRUE�����򷵻�FALSE
	 */
	public static boolean isMultiLangBoxInputNameEmpty(KDBizMultiLangBox mltBox, IObjectValue editData, String propertyName) {
		// �������Ƿ�Ϸ�
		if (mltBox == null || editData == null || propertyName == null || propertyName.equals("")) {
			throw new IllegalArgumentException();
		}

		// �жϿؼ����û������Ƿ�Ϊ��
		boolean isEmpty = true;
		com.kingdee.bos.ui.util.UIHelper.storeMultiLangFields(mltBox, editData, propertyName);

		String inputText = (String) editData.get(propertyName);
		if (inputText == null || inputText.trim().equals("")) {
			return true;
		}

		return false;
	}

	/**
	 * �ж�һ��������KDBizMultiLangArea�ؼ����û������Ƿ�Ϊ��
	 * 
	 * @param mltArea
	 *            KDBizMultiLangArea
	 * @param editData
	 *            IObjectValue ��EditUI��editData
	 * @param propertyName
	 *            String ��mltArea��ֵ�������������
	 * @return boolean ������Ϊ�ջ�""ʱ����TRUE�����򷵻�FALSE
	 */
	public static boolean isMultiLangAreaInputDescriptionEmpty(KDBizMultiLangArea mltArea, IObjectValue editData, String propertyName) {
		// �������Ƿ�Ϸ�
		if (mltArea == null || editData == null || propertyName == null || propertyName.equals("")) {
			throw new IllegalArgumentException();
		}

		// �жϿؼ����û������Ƿ�Ϊ��
		boolean isEmpty = true;
		com.kingdee.bos.ui.util.UIHelper.storeMultiLangFields(mltArea, editData, propertyName);

		String inputText = (String) editData.get(propertyName);
		if (inputText == null || inputText.equals("")) {
			return true;
		}

		return false;
	}

	/**
	 * �õ���ǰ�û��Ĳ�����֯ID��
	 * 
	 * @return String ��ǰ�û��Ĳ�����֯ID��
	 */
	public static String getUserCompanyOrgIDString() {
		String comOrgString = (String) SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		if (comOrgString == null) {
			throw new RuntimeException();
		}

		return comOrgString;
	}

	/**
	 * ������ѡ���У�����ʾ�Ի���
	 * 
	 * @param owner
	 *            Component
	 */
	public static void plsSelectRowFirst(Component owner) {
		MsgBox.showWarning(owner, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
	}

	// /**
	// * Login
	// *
	// * @throws Exception
	// */
	// public static void login() throws Exception {
	// LoginHelper.login("test", "test", "eas", "eas", new Locale("L2"),
	// LoginHelper.DB_SQLSERVER);
	// }
	
	
	/**
	 * 
	 * ȥ���������ո�
	 */
	public static String clearEmpty(String oldStr) {
		StringBuffer newStr = new StringBuffer();
		if (oldStr == null || oldStr.length() == 0) {
			return null;
		}

		for (int i = 0; i < oldStr.length(); i++) {
			if (oldStr.charAt(i)!=' ') {
				newStr.append(oldStr.charAt(i));
			}
		}
		return newStr.toString();
	}
	
	
	
}
/**
 * �������еƻ�ĻԻ� ����û�������޾���ҹ ������Զ���ᱻ���� ����û�йµ���į��ҹ ����һ���ʺ�,�������� ��ů�ҵ����� ����˫��,Ը��������
 * �ǵ���ʱ���������� ���Ц�����Ҹо����� �ǵ���ʱ��պ����� �ǵ���ʱ������һ�׸� ��Ϊ�����˿�,���ĵȺ� ���±�ϲ��ˮ ����˫��,Ը��������
 * 
 * �������еƻ�ĻԻ� ����û�������޾���ҹ ������Զ���ᱻ���� ����û�йµ���į��ҹ ������紵��,�������� ������˻�ϲ ף������,ף����һ��
 * �������������������� ��֪�����һ���Կ��� ��Ҳ���������������� ��������������˷���� �����ҿ��������� ��˸ǧ��ƻ� ��Խ������������ ������
 * ֱ���ҿ��������� ��˸ǧ��ƻ� ��Խ������������ ������
 */
