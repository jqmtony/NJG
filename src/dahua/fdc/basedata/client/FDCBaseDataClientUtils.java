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
 * 描述:房地产基础资料客户端工具类
 * 
 * @author jackwang date:2006-7-8
 * @version EAS5.1
 */
public class FDCBaseDataClientUtils {

	public static final String FDCBASEDATA_RESOURCE = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";

	public static final String UI_TITLE_SEPRATOR = " - "; // UI标题分隔符

	public static final String ADDNEW = "addnew"; // 新增

	public static final String EDIT = "edit"; // 修改

	public static final String VIEW = "view"; // 查看

	public static final String DISABLE_SUCCESSED = "disableSuccessed"; // 禁用成功

	public static final String DISABLE_FAILED = "disableFailed"; // 禁用失败

	public static final String ENABLE_SUCCESSED = "enableSuccessed"; // 启用成功

	public static final String ENABLE_FAILED = "enableFailed"; // 启用失败

	public static final String CONFIRM_DISABLE = "confirmDisable"; // 请确认禁用

	public static final String ADJUSTREASON = "AdjustReason";
	
	public static final String CONTRACTSOURCE = "ContractSource";

	public static final String ADJUSTTYPE = "AdjustType";

	public static final String CHANGEREASON = "ChangeReason";

	public static final String CHANGETYPE = "ChangeType";
	
	//变更类型 
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
	//项目管理添加
	public static final String CTRLITEM = "CtrlItem";
	
	public static final String PLANADJUSTREASON = "PlanAdjustReason";
	
	public static final String TASKTYPE = "TaskType";
	
	public static final String WORKTASK = "WorkTask";
	//测算阶段
	public static final String MEASURESTAGE = "MeasureStage";
	//成本测算指标
	public static final String MEASUREINDEX = "MeasureIndex";
	
	//收入科目
	public static final String INCOMEACCOUNT = "IncomeAccount";
	/**
	 * 根据界面状态设置窗口的标题
	 * 
	 * @param ui
	 *            CoreUIObject
	 * @param bindedEntityName
	 *            String CoreUIObject绑定的业务对象的名称
	 * @return
	 */
	public static void setupUITitle(CoreUIObject ui, String bindedEntityName) {
		if (ui != null) {
			if (ui instanceof EditUI) {
				if (ui.getOprtState().equalsIgnoreCase(OprtState.ADDNEW)) {
					// 对象 - 新增
					ui.setUITitle(bindedEntityName + AssistantClientUtils.UI_TITLE_SEPRATOR + EASResource.getString(AssistantClientUtils.ASSISTANT_RESOURCE, AssistantClientUtils.ADDNEW));
				} else if (ui.getOprtState().equalsIgnoreCase(OprtState.EDIT)) {
					// 对象 - 修改
					ui.setUITitle(bindedEntityName + AssistantClientUtils.UI_TITLE_SEPRATOR + EASResource.getString(AssistantClientUtils.ASSISTANT_RESOURCE, AssistantClientUtils.EDIT));
				} else if (ui.getOprtState().equalsIgnoreCase(OprtState.VIEW)) {
					// 对象 - 查看
					ui.setUITitle(bindedEntityName + AssistantClientUtils.UI_TITLE_SEPRATOR + EASResource.getString(AssistantClientUtils.ASSISTANT_RESOURCE, AssistantClientUtils.VIEW));
				}
			} else {
				ui.setUITitle(bindedEntityName);
			}

		}
	}

	/**
	 * 子类重载此方法，负责清空一些不能重复的域的值
	 */
	public static void setFieldsNull(IObjectValue newData) {
		newData.setString("number", null);
		newData.setString("name", null);
	}

	/**
	 * 判断一个多语言KDBizMultiLangBox控件中用户输入是否为空
	 * 
	 * @param mltBox
	 *            KDBizMultiLangBox
	 * @param editData
	 *            IObjectValue 绑定EditUI的editData
	 * @param propertyName
	 *            String 绑定mltBox的值对象的属性名称
	 * @return boolean 当输入为空或""时返回TRUE，否则返回FALSE
	 */
	public static boolean isMultiLangBoxInputNameEmpty(KDBizMultiLangBox mltBox, IObjectValue editData, String propertyName) {
		// 检查参数是否合法
		if (mltBox == null || editData == null || propertyName == null || propertyName.equals("")) {
			throw new IllegalArgumentException();
		}

		// 判断控件中用户输入是否为空
		boolean isEmpty = true;
		com.kingdee.bos.ui.util.UIHelper.storeMultiLangFields(mltBox, editData, propertyName);

		String inputText = (String) editData.get(propertyName);
		if (inputText == null || inputText.trim().equals("")) {
			return true;
		}

		return false;
	}

	/**
	 * 判断一个多语言KDBizMultiLangArea控件中用户输入是否为空
	 * 
	 * @param mltArea
	 *            KDBizMultiLangArea
	 * @param editData
	 *            IObjectValue 绑定EditUI的editData
	 * @param propertyName
	 *            String 绑定mltArea的值对象的属性名称
	 * @return boolean 当输入为空或""时返回TRUE，否则返回FALSE
	 */
	public static boolean isMultiLangAreaInputDescriptionEmpty(KDBizMultiLangArea mltArea, IObjectValue editData, String propertyName) {
		// 检查参数是否合法
		if (mltArea == null || editData == null || propertyName == null || propertyName.equals("")) {
			throw new IllegalArgumentException();
		}

		// 判断控件中用户输入是否为空
		boolean isEmpty = true;
		com.kingdee.bos.ui.util.UIHelper.storeMultiLangFields(mltArea, editData, propertyName);

		String inputText = (String) editData.get(propertyName);
		if (inputText == null || inputText.equals("")) {
			return true;
		}

		return false;
	}

	/**
	 * 得到当前用户的财务组织ID串
	 * 
	 * @return String 当前用户的财务组织ID串
	 */
	public static String getUserCompanyOrgIDString() {
		String comOrgString = (String) SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		if (comOrgString == null) {
			throw new RuntimeException();
		}

		return comOrgString;
	}

	/**
	 * “请先选中行！”提示对话框
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
	 * 去除字条串空格
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
 * 那里曾有灯火的辉煌 那里没有漫漫无尽黑夜 那里永远不会被遗忘 那里没有孤单寂寞黑夜 我像一条彩虹,你像阳光 温暖我的世界 合上双手,愿意做尘埃
 * 记得那时你曾望着我 你的笑容让我感觉甜美 记得那时天空很辽阔 记得那时岁月像一首歌 因为爱的伤口,爱的等候 流下悲喜泪水 合上双手,愿意做尘埃
 * 
 * 那里曾有灯火的辉煌 那里没有漫漫无尽黑夜 那里永远不会被遗忘 那里没有孤单寂寞黑夜 现在晚风吹起,音乐响起 生命如此欢喜 祝福着你,祝福这一切
 * 我总是在这里想起那里 不知不觉我会独自哭泣 我也曾在这里忘了那里 这里总是让我扑朔迷离 你让我看见这世间 闪烁千万灯火 超越梦里所有想象 还更美
 * 直到我看见这世间 闪烁千万灯火 超越我们所有想象 还更美
 */
