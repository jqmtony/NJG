/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.CellCheckBoxRenderer;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.LimitedLengthDocument;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.permission.OrgRangeIncludeSubOrgCollection;
import com.kingdee.eas.base.permission.OrgRangeIncludeSubOrgFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.JobTypeFactory;
import com.kingdee.eas.fdc.basedata.JobTypeInfo;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.CKDate;
import com.kingdee.eas.fdc.contract.programming.PcTypeEntryCollection;
import com.kingdee.eas.fdc.contract.programming.PcTypeEntryFactory;
import com.kingdee.eas.fdc.contract.programming.PcTypeEntryInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFxbdEntryCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFxbdEntryInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.client.PayPlanNewUI;
import com.kingdee.eas.fdc.invite.InviteFormEnum;
import com.kingdee.eas.fdc.invite.InviteModeFactory;
import com.kingdee.eas.fdc.invite.InviteModeInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 框架合约编辑界面
 */
public class ProgrammingContractEditUI extends AbstractProgrammingContractEditUI {
	private static final String CONSTRACTSCALE = "contractScale";

	private static final Logger logger = CoreUIObject.getLogger(ProgrammingContractEditUI.class);

	protected KDWorkButton btnAddnewLine_cost = null;
	protected KDWorkButton btnRemoveLines_cost = null;
	protected KDWorkButton btnAddnewLine_economy = null;
	protected KDWorkButton btnRemoveLines_economy = null;
	protected KDWorkButton addFxbdLine = null;
	protected KDWorkButton removeFxbdLine = null;
	
	private Map<String,PcTypeEntryInfo> ptentrys = new HashMap<String,PcTypeEntryInfo>();
	private Map<String,Date> ckDates = new HashMap<String,Date>();
	private ProgrammingContractFxbdEntryCollection fxcoll=null;

	private ProgrammingContractInfo oldPcInfo;

	private ProgrammingContractCollection pcCollection;
	private BigDecimal oldAmount;
	private BigDecimal oldControlAmount;
	private BigDecimal oldbalance;
	private BigDecimal oldcontrolBalance;

	// 成本构成分录表格列名
	private static final String COST_ID = "id";// ID
	private static final String PROJECT = "project";// 工程项目(F7)
	private static final String COSTACCOUNT_NUMBER = "number";// 成本科目编码
	private static final String COSTACCOUNT = "name";// 成本科目名称(F7)
	private static final String GOALCOST = "goalCost";// 目标成本
	private static final String ASSIGNED = "assigned";// 已分配
	private static final String ASSIGNING = "assigning";// 待分配
	private static final String CONTRACTASSIGN = "contractAssign";// 本合约分配
	private static final String COST_DES = "description";// 备注
	
	// modified by zhaoqin on 2013/10/23
	private static final String AIMCOST_ID = "aimCostId";// 目标成本id

	// 经济条款分录表格列名
	private static final String ECONOMY_ID = "id";// ID
	private static final String PAYMENTTYPE = "paymentType";// 付款类型
	private static final String SCALE = "scale";// 付款比例
	private static final String AMOUNT = "amount";// 付款金额
	private static final String PAYMENTDATE = "paymentDate";// 付款日期
	private static final String CONDITION = "condition";// 付款条件
	private static final String ECONOMY_DES = "description";// 备注

	private static final String DEFAULT_WORK_CONTENT = "可录入“工作内容”、“招标指导”、“合同执行常见问题”等备注信息";

	protected PayPlanNewUI payPlanNewUI;
	
	// 获取有权限的组织
	protected Set authorizedOrgs = null;

	public ProgrammingContractEditUI() throws Exception {
		super();
		
		  this.kdtpMain.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				try {
					kdtpMain_stateChanged(e);
				} catch (Exception exc) {
					handUIException(exc);
				} finally {
				}
			}
		});
		txtAmount.setEnabled(false);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initFormat(); 
		setSmallButton();
		if (this.oprtState.equals(OprtState.VIEW)) {
			isEditable(false);
			ctrButtonEnable(false);
		} else if (this.oprtState.equals(OprtState.EDIT) || this.oprtState.equals(OprtState.ADDNEW)) {
			isEditable(true);
			ctrButtonEnable(true);
		}
		authorizedOrgs = (Set) this.getUIContext().get("projectIDs");
		kdtEconomy.checkParsed();
		//设置默认隐藏列
		kdtEconomy.getColumn("scheduletask").getStyleAttributes().setHided(true);
		kdtEconomy.getColumn("completedate").getStyleAttributes().setHided(true);
		kdtEconomy.getColumn("delaydays").getStyleAttributes().setHided(true);
		
		txtAmount.addDataChangeListener(new DataChangeListener() {
			/*
			 * 规划金额改变后需要处理的事情有以下几种情况 1. 判断规划金额录入的数值是否小于0 2. 经济条款—付款金额依据原付款比例动态改变
			 * 3.
			 */
			public void dataChanged(DataChangeEvent e) {
				if (e.getOldValue() != e.getNewValue()) {
					/**
					 * 经济条款分录动态变化情况
					 */
					int economyRowCount = kdtEconomy.getRowCount();
					BigDecimal planAmount = null;// 规划金额
					if (!FDCHelper.isEmpty(e.getNewValue())) {
						planAmount = new BigDecimal(e.getNewValue().toString());
						/* 1.判断规划金额录入的数值是否小于0 */
						// if (planAmount.compareTo(new BigDecimal(0)) <= 0) {
						// FDCMsgBox.showInfo("\"规划金额\"不能小于0");
						// txtAmount.setValue(e.getOldValue());
						// SysUtil.abort();
						// }
						/* 2.经济条款—付款金额依据原付款比例动态改变 */
						// 2.1 规划金额变不为空值情况——付款金额 = 原付款比例*新规划金额
						for (int i = 0; i < economyRowCount; i++) {
							// BigDecimal amount = null;
							Object obj = kdtEconomy.getCell(i, SCALE).getValue();
							if (obj != null) {
								BigDecimal scale = new BigDecimal(obj.toString());
								scale = FDCHelper.divide(scale, new BigDecimal(100), 8, BigDecimal.ROUND_HALF_UP);
								kdtEconomy.getCell(i, AMOUNT).setValue(scale.multiply(planAmount));
							}
						}
					} else {
						// 2.2 规划金额变为空值情况——付款金额全部置null
						for (int i = 0; i < economyRowCount; i++) {
							kdtEconomy.getCell(i, AMOUNT).setValue(null);
						}
					}

					/**
					 * 成本构成分录动态变化情况
					 */
				}
			}
		});

		initPayPlan();
	}
	
	protected void kdtpMain_stateChanged(ChangeEvent e) throws Exception {

		Object obj = kdtpMain.getClientProperty("oldIndex");
		if (obj instanceof Integer) {
			if (((Integer) obj).intValue() == 0) {
				if (isFirstOnload()) {
					return;
				}
				if (OprtState.ADDNEW.equals(oprtState) || OprtState.EDIT.equals(oprtState)) {
//					storeEditData();  modify by yxl
				}
				if(txtAmount.getBigDecimalValue()!=null && txtAmount.getBigDecimalValue().compareTo(payPlanNewUI.planAmount)!=0){
					payPlanNewUI.updateAmount(txtAmount.getBigDecimalValue());
				}
			}
		}
		kdtpMain.putClientProperty("oldIndex", new Integer(kdtpMain.getSelectedIndex()));
	}

	/**
	 * 描述：
	 * @throws ParseException 
	 * @throws BOSException 
	 * @Author：keyan_zhao
	 * @CreateTime：2013-8-22
	 */
	private void storeEditData() throws ParseException, BOSException {
		/* 保存单据头信息 */
		editData.setLongNumber(txtNumber.getText());// 长编码
		if (FDCHelper.isEmpty(txtParentLongName.getText())) {
			editData.setDisplayName(txtParentLongName.getText());// 上级合约长名称
		} else {
			editData.setDisplayName(txtParentLongName.getText() + "." + txtName.getText());
		}
		editData.setNumber(txtNumber.getText());
		editData.setName(setNameIndent(editData.getLevel()) + txtName.getText());// 名称
		editData.setInviteWay((InviteFormEnum) kdcInviteWay.getSelectedItem());// 招标方式
		editData.setInviteOrg((CostCenterOrgUnitInfo) prmtInviteOrg.getData());
		
		/* modified by zhaoqin for R131227-0115 on 2013/01/24 start */
		BigDecimal amount = txtAmount.getBigDecimalValue();
		amount = null == amount ? amount : FDCHelper.toBigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
		//editData.setAmount((BigDecimal) txtAmount.getValue());// 规划金额
		editData.setAmount(amount);// 规划金额
		this.txtAmount.commitEdit();
		/* modified by zhaoqin for R131227-0115 on 2013/01/24 end */
		
		editData.setControlAmount((BigDecimal) txtControlAmount.getValue());// 采购控制金额
		editData.setReservedChangeRate(FDCHelper.toBigDecimal(txtReservedChangeRate.getValue()));// 采购控制金额
		editData.setWorkContent(txtWorkContent.getText());// 工作内容
		editData.setContractContUI(txtContractContUI.getText());
		editData.setAttachWork(txtAttachWork.getText());
		editData.setAttContract(txtAttContract.getText());
		if (editData.getWorkContent() != null && editData.getWorkContent().equals(DEFAULT_WORK_CONTENT)) {
			editData.setWorkContent("");
		}
		editData.setSupMaterial(txtSupMaterial.getText());// 甲供及甲指材设
		editData.setDescription(txtDescription.getText());// 备注
		//		if (FDCHelper.isEmpty(txtAttachment.getText())) {
		//			pcInfo.setAttachment(null);// 附件
		//		} else {
		//			pcInfo.setAttachment(txtAttachment.getText());// 附件
		//		}

		// 更新规划余额，控制余额
		updateBalance(editData);

		/* 保存单据分录信息 */
		/* 成本构成 */
		editData.getCostEntries().clear();
		int cost_rowCount = kdtCost.getRowCount();
		for (int i = 0; i < cost_rowCount; i++) {
			ProgrammingContracCostInfo pccInfo = new ProgrammingContracCostInfo();
			Object project = kdtCost.getCell(i, PROJECT).getValue();// 工程项目
			Object costAccount = kdtCost.getCell(i, COSTACCOUNT).getValue();// 成本科目
			Object goalCost = kdtCost.getCell(i, GOALCOST).getValue();// 目标成本
			Object assigned = kdtCost.getCell(i, ASSIGNED).getValue();// 已分配
			Object assigning = kdtCost.getCell(i, ASSIGNING).getValue();// 待分配
			Object contractScale = kdtCost.getCell(i, kdtCost.getColumnIndex(CONSTRACTSCALE)).getValue();
			Object contractAssign = kdtCost.getCell(i, CONTRACTASSIGN).getValue();// 本合约分配
			String description = (String) kdtCost.getCell(i, COST_DES).getValue();// 备注
			if(project!=null){
				CurProjectInfo projectInfo = (CurProjectInfo) project;
				if(costAccount!=null){
					CostAccountInfo costAccountInfo = (CostAccountInfo) costAccount;
					costAccountInfo.setCurProject(projectInfo);
					pccInfo.setCostAccount(costAccountInfo);
				}else{
					FDCMsgBox.showWarning("第"+(i+1)+"行成本科目不能为空");
					abort();
				}
			}else{
				FDCMsgBox.showWarning("第"+(i+1)+"行工程项目不能为空");
				abort();
			}
			if (!FDCHelper.isEmpty(goalCost)) {
				pccInfo.setGoalCost(new BigDecimal(goalCost.toString()));
			}
			if (!FDCHelper.isEmpty(assigned)) {
				pccInfo.setAssigned(new BigDecimal(assigned.toString()));
			}
			if (!FDCHelper.isEmpty(assigning)) {
				pccInfo.setAssigning(new BigDecimal(assigning.toString()));
			}
			if (!FDCHelper.isEmpty(contractAssign)) {
				pccInfo.setContractAssign(new BigDecimal(contractAssign.toString()));
			}
			if (!FDCHelper.isEmpty(contractScale)) {
				pccInfo.setContractScale(new BigDecimal(contractScale.toString()));
    		}
			pccInfo.setDescription(description);
			
			// modified by zhaoqin on 2013/10/23
			// 解决合约规划跨期时目标成本问题版本问题
			String aimCostId = (String) kdtCost.getCell(i, AIMCOST_ID).getValue();
			pccInfo.setAimCostId(aimCostId);
			
			editData.getCostEntries().add(pccInfo);
		}

		// modified by zhaoqin on 2013/11/07
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("project");
		// 动态更新成本构成"待分配"的值
		int pteSize = pcCollection.size();
		for (int i = 0; i < pteSize; i++) {
			/* modified by zhaoqin for R140425-0059 on 2014/05/09 start */
			ProgrammingContractInfo pcInfo = pcCollection.get(i);
			if(null != pcInfo.getId() && pcInfo.getId().toString().equals(this.editData.getId().toString()))
				continue;
			ProgrammingContracCostCollection pccCollection = pcInfo.getCostEntries();// 成本构成集合
			/* modified by zhaoqin for R140425-0059 on 2014/05/09 end */
			for (int j = 0; j < pccCollection.size(); j++) {
				ProgrammingContracCostInfo pccInfo = pccCollection.get(j);// 成本构成
				BigDecimal oldContractAssign = pccInfo.getContractAssign();// 本合约分配
				CostAccountInfo costAccountInfo = pccInfo.getCostAccount();// 成本科目
				BigDecimal goalCost = pccInfo.getGoalCost();// 目标成本
				if (costAccountInfo != null) {
					// ---------- modified by zhaoqin on 2013/11/07 start ----------
					BigDecimal allAssignScale = getAllContractAssignScale(project, pcCollection.get(i),costAccountInfo);
					//BigDecimal allCostAccountAssign = getAllContractAssign(costAccountInfo, true, null);// "本合约分配"金额之和
					if (oldContractAssign != null) {
						// 更新"待分配":"目标成本"-"本合约分配"金额之和+自身"本合约分配"
						// BigDecimal newAssigning = goalCost.subtract(allCostAccountAssign).add(oldContractAssign);
						// 更新"已分配"："本合约分配"金额之和-自身"本合约分配"
						// BigDecimal newAssigned = allCostAccountAssign.subtract(oldContractAssign);
						BigDecimal newAssigned = FDCHelper.divide(
								FDCHelper.multiply(goalCost, allAssignScale), 
								/* modified by zhaoqin for R140425-0059 on 2014/05/09 start */
								//FDCHelper.ONE_HUNDRED, 12, BigDecimal.ROUND_HALF_UP);
								FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
								/* modified by zhaoqin for R140425-0059 on 2014/05/09 end */
						
						pccInfo.setAssigning(goalCost.subtract(newAssigned));
						pccInfo.setAssigned(newAssigned);
					}
					// ---------- modified by zhaoqin on 2013/11/07 end ----------
				}
			}
		}

		/* 经济条款 
		editData.getEconomyEntries().clear();
		int enonomy_rowCount = kdtEconomy.getRowCount();
		for (int i = 0; i < enonomy_rowCount; i++) {
			ProgrammingContractEconomyInfo pciInfo = new ProgrammingContractEconomyInfo();
			PaymentTypeInfo currentInfo = (PaymentTypeInfo) kdtEconomy.getCell(i, PAYMENTTYPE).getValue();// 付款类型
			Object scaleObj = kdtEconomy.getCell(i, SCALE).getValue();// 付款比例
			Object amountObj = kdtEconomy.getCell(i, AMOUNT).getValue();// 付款金额
			Object conditionObj = kdtEconomy.getCell(i, CONDITION).getValue();// 付款条件
			Object paymentDateObj = kdtEconomy.getCell(i, PAYMENTDATE).getValue();// 付款日期
			Object descriptionObj = kdtEconomy.getCell(i, ECONOMY_DES).getValue();// 备注
			Object completeDateObj = kdtEconomy.getCell(i, "completedate").getValue();// 计划完成日期
			Object delaydaysObj = kdtEconomy.getCell(i, "delaydays").getValue();//延迟天数

			Object scheduleObj = kdtEconomy.getCell(i, "scheduletask").getValue();

			pciInfo.setPaymentType(currentInfo);// 存储付款类型
			if (!FDCHelper.isEmpty(scaleObj)) {
				pciInfo.setScale(new BigDecimal(scaleObj.toString()));// 存储付款比例
			}
			if (!FDCHelper.isEmpty(amountObj)) {
				pciInfo.setAmount(new BigDecimal(amountObj.toString()));// 存储付款金额
			}
			pciInfo.setCondition((String) conditionObj);// 存储付款条件
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (!FDCHelper.isEmpty(paymentDateObj)) {
				Date paymentDate = null;
				if (paymentDateObj instanceof Date) {

					paymentDate = (Date) paymentDateObj;
				} else {
					paymentDate = sdf.parse((String) paymentDateObj);
				}
				pciInfo.setPaymentDate(new Timestamp(paymentDate.getTime()));// 存储付款日期
			}
			// added by shangjing 保存计划完成日期 相关任务项 延迟天数 付款日期
			if (!FDCHelper.isEmpty(completeDateObj)) {
				Date completeDate = (Date) completeDateObj;
				pciInfo.setCompletedate(completeDate);// 存储计划完成日期
			}
			if (!FDCHelper.isEmpty(scheduleObj)) {
				FDCScheduleTaskInfo schedule = (FDCScheduleTaskInfo) scheduleObj;
				pciInfo.setScheduletask(schedule);//存储相关任务项
			}

			if (!FDCHelper.isEmpty(delaydaysObj)) {
				int delaydays = 0;
				delaydays = (new Integer(delaydaysObj.toString())).intValue();
				//				if ((String) delaydaysObj != null
				//						&& !"".equals((String) delaydaysObj)) {
				//					delaydays = Integer.parseInt((String) delaydaysObj);// 存储延迟天数
				//				}
				pciInfo.setDelaydays(delaydays);
			}
			//
			pciInfo.setDescription((String) descriptionObj);// 存储付款条件
			editData.getEconomyEntries().add(pciInfo);
		}*/
		//modify by yxl 20150814 store fxbd
		
		editData.getFxbdEntry().clear();
		ProgrammingContractFxbdEntryInfo fxbdinfo = null;
		String number = null;
		for (int k = 1; k < kdtfxbd.getColumnCount(); k=k+3) {
			number = kdtfxbd.getColumnKey(k).substring(0,3);
			for (int j = kdtfxbd.getRowCount3()-1; j >=0; j--) {
				if(FDCHelper.isEmpty(kdtfxbd.getCell(j,k).getValue()))
					continue;
				fxbdinfo = new ProgrammingContractFxbdEntryInfo();
				fxbdinfo.setItemName((String)kdtfxbd.getCell(j,k).getValue());
				fxbdinfo.setPlanDate((Date)kdtfxbd.getCell(j,number+"date").getValue());
				if(kdtfxbd.getCell(j,number+"yj").getValue() == null)
					fxbdinfo.setIsYj(false);
				else
					fxbdinfo.setIsYj((Boolean)kdtfxbd.getCell(j,number+"yj").getValue());
				fxbdinfo.setRecordSeq(number+j);
				if(kdtfxbd.getCell(j,0).getValue() == null)
					fxbdinfo.setIsNew(false);
				else
					fxbdinfo.setIsNew((Boolean)kdtfxbd.getCell(j,0).getValue());
				if(ptentrys.get(number+j) != null)
					fxbdinfo.setDepType(ptentrys.get(number+j).getDepType());
				editData.getFxbdEntry().add(fxbdinfo);
			}
		}
//		fxcoll = editData.getFxbdEntry();
	}

	protected void initPayPlan() throws UIException {
		//yuxue
		UIContext uiContext = new UIContext(this);
		ProgrammingContractInfo editData = (ProgrammingContractInfo) getUIContext().get("programmingContract");
		uiContext.put("programming", editData);
		uiContext.put("project", getUIContext().get("project"));
		if (editData.getId() != null) {
			uiContext.put("programmingId", editData.getId().toString());
		}
		if (editData.getSrcId() != null) {
			uiContext.put("programmingSrcId", editData.getSrcId().toString());
		}
		
		CoreUIObject uiObj = null;
		if (getUIContext().get("editPayPlan") != null) {
			uiContext.put("editPayPlan", true);
			uiObj = (CoreUIObject) UIFactoryHelper.initUIObject(PayPlanNewUI.class.getName(), uiContext, null, OprtState.EDIT);
			actionSubmit.setEnabled(true);
		} else {
			uiObj = (CoreUIObject) UIFactoryHelper.initUIObject(PayPlanNewUI.class.getName(), uiContext, null, getOprtState());
		}

		uiObj.setBounds(0, 0, kdtpMain.getWidth(), kdtpMain.getHeight() - 20);
		uiObj.putClientProperty("KDLayoutConstraints", new KDLayout.Constraints(0, 0, kdtpMain.getWidth(), kdtpMain.getHeight() - 20,
				KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
		payPlanNewUI = (PayPlanNewUI) uiObj;
		kdplPayPlan.add(uiObj);


	}

	public void onShow() throws Exception {
		super.onShow();
		initTabalFormat();
		this.btnAttachment.setIcon(EASResource.getIcon("imgTbtn_affixmanage"));
		this.txtParentLongName.setEditable(false);// 上级合约框架长名称不可编辑
		this.txtAttachment.setEditable(false);// 附件文本框仅提供浏览，可根据操作状态决定在附件管理器里是编辑状态还是查看
		Map uiContext = this.getUIContext();
		Object object = uiContext.get("inviteProject");
		Object contractBillProg = uiContext.get("programmingContractTemp");
		if (object != null || contractBillProg != null) {
			this.btnSave.setVisible(false);// 招标立项关联里查看进入，把保存按钮去掉
		}
		editData = (ProgrammingContractInfo) uiContext.get("programmingContract");
		if (editData.getId() == null) {
			editData.setId(BOSUuid.create(editData.getBOSType()));
		}
		initOldValue();
		pcCollection = (ProgrammingContractCollection) uiContext.get("pcCollection");
		preparePCData();
		// modify by yxl 20151109
//		initAmountControlEnable();
//		if(kdtCost.getRowCount()>0){
//			txtAmount.setEnabled(false);
//		}else{
//			txtAmount.setEnabled(true);
//			txtAmount.setRequired(true);
//		}
		txtName.setDocument(new LimitedLengthDocument() {
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str.matches("^\\.+$")) {
					return;
				}
				super.insertString(offs, str, a);
			}
		});
		txtName.setText(editData.getName() == null ? null : editData.getName().trim());
		
		changeTxtAmountState(); // modified by zhaoqin for R130828-0384 on 2013/9/29
		
		kDTabbedPane1.remove(kDContainerEconomy);
		//modify by yxl 20150812
		initFxbd();
		txtAmount.setEnabled(false);
	}

	private void initFxbd() throws BOSException{
		//modify by yxl 20150812  增加副项表单页签
		if(editData.getHyType() != null){
			String oql="select depType.id,depType.number,depType.name,fieldName,ckDate,tqDays,strongControl,recordSeq where parent.id='"+editData.getHyType().getId().toString()+"' order by recordSeq";
			PcTypeEntryCollection pcoll=PcTypeEntryFactory.getRemoteInstance().getPcTypeEntryCollection(oql);
			PcTypeEntryInfo einfo = null;
			String depId = null;
			Map<String,Integer> depIds = new TreeMap<String,Integer>();
			for (int i = pcoll.size()-1; i >=0; i--) {
				einfo = pcoll.get(i);
				depId = einfo.getDepType().getNumber();
				if(depIds.containsKey(depId)){
					depIds.put(depId,depIds.get(depId)+1);
				}else{
					depIds.put(depId,1);
				}
				ptentrys.put(einfo.getRecordSeq(),einfo);
			}
			IColumn icol = null;
//			icol = kdtfxbd.addColumn();
//			icol.setKey("tempColumn");
			IRow row0 = kdtfxbd.addHeadRow(0);
			IRow row1 = kdtfxbd.addHeadRow(1);
			IRow row2 = kdtfxbd.addHeadRow(2);
//			kdtfxbd.getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
			KDTMergeManager kdmm = kdtfxbd.getHeadMergeManager();
			String key = null;
			int columnIndex = -1;
			int maxSize = 0;
		    KDTDefaultCellEditor ywDate_CellEditor = new KDTDefaultCellEditor(new KDDatePicker());
		    KDTDefaultCellEditor boxeditor = new KDTDefaultCellEditor(new KDCheckBox());
		    CellCheckBoxRenderer boxrender = new CellCheckBoxRenderer();
		    String name = null;
		    icol = kdtfxbd.addColumn();
		    icol.setKey("isNew");
		    icol.setEditor(boxeditor);
			icol.setRenderer(boxrender);
			icol.getStyleAttributes().setHided(true);
			for(Iterator<String> it=depIds.keySet().iterator(); it.hasNext();){
				key = it.next();
				if(maxSize < depIds.get(key)){
					maxSize = depIds.get(key);
				}
				name = ptentrys.get(key+"0").getDepType().getName();
				icol = kdtfxbd.addColumn();
				icol.setKey(key+"name");
				columnIndex = icol.getColumnIndex();
//				icol.getStyleAttributes().setLocked(true);
				row0.getCell(columnIndex).setValue(name);
				row1.getCell(columnIndex).setValue("关键工作及其计划完成时间");
				row2.getCell(columnIndex).setValue("事项名称");
				icol = kdtfxbd.addColumn();
				icol.setEditor(ywDate_CellEditor);
				icol.setKey(key+"date");
				columnIndex = icol.getColumnIndex();
				icol.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
				row0.getCell(columnIndex).setValue(name);
				row1.getCell(columnIndex).setValue("关键工作及其计划完成时间");
				row2.getCell(columnIndex).setValue("完成时间");
				//增加是否预警
				icol = kdtfxbd.addColumn();
				icol.setEditor(boxeditor);
				icol.setRenderer(boxrender);
				icol.setKey(key+"yj");
				columnIndex = icol.getColumnIndex();
				row0.getCell(columnIndex).setValue(name);
				row1.getCell(columnIndex).setValue("关键工作及其计划完成时间");
				row2.getCell(columnIndex).setValue("是否预警");
				kdmm.mergeBlock(0,columnIndex-2,0,columnIndex);
				kdmm.mergeBlock(1,columnIndex-2,1,columnIndex);
			}
			ckDates.put("SGT",editData.getSgtDate());
			ckDates.put("CSD",editData.getContSignDate());
			ckDates.put("SWD",editData.getStartDate());
			ckDates.put("EWD",editData.getEndDate());
			ckDates.put("CSED",editData.getCsendDate());
//			fxcoll=ProgrammingContractFxbdEntryFactory.getRemoteInstance().getProgrammingContractFxbdEntryCollection("where parent1.id='"+editData.getId().toString()+"'");
			fxcoll = editData.getFxbdEntry();
			if(fxcoll.size() > 0){
				ProgrammingContractFxbdEntryInfo feinfo = null;
				Map<String,ProgrammingContractFxbdEntryInfo> fxentrys = new HashMap<String,ProgrammingContractFxbdEntryInfo>();
				maxSize = 0;
				for (int i = 0; i < fxcoll.size(); i++) {
					feinfo = fxcoll.get(i);
					fxentrys.put(feinfo.getRecordSeq(),feinfo);
					if(maxSize < Integer.parseInt(feinfo.getRecordSeq().substring(3)))
						maxSize = Integer.parseInt(feinfo.getRecordSeq().substring(3));
				}
				for(int i=0; i<=maxSize; i++){
					row0 = kdtfxbd.addRow();
					for(Iterator<String> it=depIds.keySet().iterator(); it.hasNext();){
						key = it.next();
						feinfo = fxentrys.get(key+row0.getRowIndex());
//						if(i == maxSize)
//							row0.getCell(key+"name").getStyleAttributes().setBackground(chCSplitClientHelper.COLOR_NOSPLIT);
						if(feinfo != null){
							row0.getCell("isNew").setValue(feinfo.isIsNew());
							if(feinfo.getItemName() != null){
								row0.getCell(key+"name").setValue(feinfo.getItemName());
								row0.getCell(key+"date").setValue(feinfo.getPlanDate());
								row0.getCell(key+"yj").setValue(feinfo.isIsYj());
							}else if(!feinfo.isIsNew()){
								row0.getCell(key+"date").getStyleAttributes().setLocked(true);
								row0.getCell(key+"yj").getStyleAttributes().setLocked(true);
							}
							if(!feinfo.isIsNew())
								row0.getCell(key+"name").getStyleAttributes().setLocked(true);
						}
					}
				}
				setFxbdBg();
			}else{
				Calendar c1 = Calendar.getInstance();
				for(int i=0; i<maxSize; i++){
					row0 = kdtfxbd.addRow();
					row0.getCell("isNew").setValue(Boolean.FALSE);
					for(Iterator<String> it=depIds.keySet().iterator(); it.hasNext();){
						key = it.next();
						einfo = ptentrys.get(key+row0.getRowIndex());
						row0.getCell(key+"name").getStyleAttributes().setLocked(true);
						if(einfo != null){
							row0.getCell(key+"name").setValue(einfo.getFieldName());
							row0.getCell(key+"yj").setValue(Boolean.FALSE);
							if(einfo.getCkDate()!=null && ckDates.get(einfo.getCkDate().getName())!=null){
								c1.setTime(ckDates.get(einfo.getCkDate().getName()));
								c1.add(Calendar.DATE,-einfo.getTqDays());
								row0.getCell(key+"date").setValue(c1.getTime());
							}
						}else{
							row0.getCell(key+"date").getStyleAttributes().setLocked(true);
							row0.getCell(key+"yj").getStyleAttributes().setLocked(true);
						}
					}
				}
			}
			kdtfxbd.addKDTEditListener(new KDTEditAdapter(){
				@Override
				public void editStopped(KDTEditEvent e) {
					kdtfxbd_editStopped(e);
				}
			});
			initFxbdButton();
			kdtfxbd.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		}
	}
	
	private String isDateChange(String key,Date editDate,Calendar c1){
		PcTypeEntryInfo einfo = ptentrys.get(key);
		if(einfo != null){
			if(editDate==null)
				return null;
			CKDate ckItem = einfo.getCkDate();
			if(ckItem==null)
				return null;
			Date ckdate = ckDates.get(ckItem.getName());
			if(ckdate==null)
				return null;
			c1.setTime(ckdate);
			c1.add(Calendar.DATE,-einfo.getTqDays());
			Timestamp ck = new Timestamp(c1.getTime().getTime());
			Timestamp sj = new Timestamp(editDate.getTime());
			if(sj.compareTo(ck) > 0)
				return "red";
			else if(sj.compareTo(ck) < 0)
				return "green";
			else
				return null;
//			if(realDate!=null & planDate!=null){
//				if(new Timestamp(realDate.getTime()).compareTo(new Timestamp(planDate.getTime())) != 0)
//					return true;
//			}
		}
		return null;
	}
	
	private void setFxbdBg(){
		Calendar c1 = Calendar.getInstance();
		String number = null;
		String result = null;
		for (int k = 1; k < kdtfxbd.getColumnCount(); k=k+3) {
			number = kdtfxbd.getColumnKey(k).substring(0,3);
			for (int j = kdtfxbd.getRowCount3()-1; j >=0; j--) {
				result = isDateChange(number+j,(Date)kdtfxbd.getCell(j,number+"date").getValue(),c1);
				if("red".equals(result)){
					kdtfxbd.getCell(j,number+"date").getStyleAttributes().setBackground(Color.red);
				}else if("green".equals(result)){
					kdtfxbd.getCell(j,number+"date").getStyleAttributes().setBackground(Color.GREEN);
				}
			}
		}
	}
	
	private void kdtfxbd_editStopped(KDTEditEvent e){
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if(kdtfxbd.getColumnKey(colIndex).endsWith("yj") || kdtfxbd.getColumnKey(colIndex).endsWith("name"))
			return;
		if(kdtfxbd.getCell(rowIndex,colIndex).getValue() == null){
			kdtfxbd.getCell(rowIndex,colIndex).getStyleAttributes().setBackground(Color.white);
			return;
		}
		String ckey = kdtfxbd.getColumnKey(colIndex).substring(0,3);
		PcTypeEntryInfo pinfo = ptentrys.get(ckey+rowIndex);
		if(pinfo!=null && pinfo.getCkDate()!=null && ckDates.get(pinfo.getCkDate().getName())!=null){
			Calendar c1 = Calendar.getInstance();
			c1.setTime(ckDates.get(pinfo.getCkDate().getName()));
			c1.add(Calendar.DATE,-pinfo.getTqDays());
			Timestamp ckdate = new Timestamp(c1.getTime().getTime());
			Timestamp editdate = new Timestamp(((Date)kdtfxbd.getCell(rowIndex,colIndex).getValue()).getTime());
			//强控
			if(pinfo.isStrongControl() && ckdate.compareTo(editdate)<0){
				FDCMsgBox.showInfo("只能把时间往前改！");
				kdtfxbd.getCell(rowIndex,colIndex).setValue(c1.getTime());
				return;
			}
			if(ckdate.compareTo(editdate)==0){
				kdtfxbd.getCell(rowIndex,colIndex).getStyleAttributes().setBackground(Color.WHITE);
				return;
			}
			if(ckdate.compareTo(editdate)<0)
				kdtfxbd.getCell(rowIndex,colIndex).getStyleAttributes().setBackground(Color.RED);
			else
				kdtfxbd.getCell(rowIndex,colIndex).getStyleAttributes().setBackground(Color.GREEN);
		}
	}
	
	private void initFxbdButton(){
		addFxbdLine = new KDWorkButton("新增行");
		removeFxbdLine = new KDWorkButton("删除行");
//		KDWorkButton insetLine = new KDWorkButton("插入行");
		addFxbdLine.setName("addLine");
//		insetLine.setName("insetLine");
		removeFxbdLine.setName("removeLine");
		addFxbdLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
//		insetLine.setIcon(EASResource.getIcon("imgTbtn_insert"));
		removeFxbdLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		kDContainer1fx.addButton(addFxbdLine);
		kDContainer1fx.addButton(removeFxbdLine);
//		kDContainer1fx.addButton(insetLine);
		MyActionListener myal = new MyActionListener(kdtfxbd);
		addFxbdLine.addActionListener(myal);
//		insetLine.addActionListener(myal);
		removeFxbdLine.addActionListener(myal);
	}
	
	class MyActionListener implements ActionListener{
    	private KDTable table;
    	
    	public MyActionListener() {
		}
    	public MyActionListener(KDTable tab) {
    		table = tab;
		}
    	public void actionPerformed(ActionEvent e) {
    		String type = ((KDWorkButton)e.getSource()).getName();
    		IRow row = null;
    		if("addLine".equals(type)){
    			row = table.addRow();
//    			row.getStyleAttributes().setBackground(Color.GRAY);
    			for(int i = table.getColumnCount()-1; i >= 0; i--) {
					if(table.getColumnKey(i).endsWith("yj"))
						row.getCell(i).setValue(Boolean.FALSE);
					else if("isNew".equals(table.getColumnKey(i)))
						row.getCell(i).setValue(Boolean.TRUE);
				}
    		}else if("insetLine".equals(type)){
    			if(table.getSelectManager().size() > 0) {
    	            int top = table.getSelectManager().get().getTop();
    	            if(isTableColumnSelected(table))
    	            	row = table.addRow();
    	            else
    	            	row = table.addRow(top);
    	        } else {
    	        	row = table.addRow();
    	        }
    		}else{
    			if(table.getSelectManager().size() == 0){
    	            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
    	            return;
    	        }
    	        int top = table.getSelectManager().get().getTop();
    	        if(table.getRow(top) == null){
    	            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
    	            return;
    	        }
    	        if(!(Boolean)table.getRow(top).getCell("isNew").getValue()){
    	        	 MsgBox.showInfo("该行数据不是用户新增，不能进行删除操作！");
    	        	 return;
    	        }
    	        if(FDCMsgBox.OK == FDCMsgBox.showConfirm2("是否确认删除数据？"))
    	        	table.removeRow(top);
    		}
    		if(row != null){
//    			row.getCell("strongControl").setValue(Boolean.FALSE);
//    			IRow entryRow = kdtEntrys.addRow();
//    			entryRow.getCell("depType").setValue(depTypeMap.get(table.getName()));
//    			entryRow.getCell("recordSeq").setValue(table.getName()+row.getRowIndex());
    		}
    	}
		private boolean isTableColumnSelected(KDTable table2) {
			if(table2.getSelectManager().size() > 0) {
	            KDTSelectBlock block = table2.getSelectManager().get();
	            if(block.getMode() == 4 || block.getMode() == 8)
	                return true;
	        }
	        return false;
		}
    }
	
	/**
	 * 初始化一些旧值，后续作判断用
	 */
	private void initOldValue() {
		oldPcInfo = editData;
		oldAmount = FDCNumberHelper.toBigDecimal(editData.getAmount());
		oldControlAmount = FDCNumberHelper.toBigDecimal(editData.getControlAmount());
		oldbalance = FDCNumberHelper.toBigDecimal(editData.getBalance());
		oldcontrolBalance = FDCNumberHelper.toBigDecimal(editData.getControlBalance());
	}

	/**
	 * 初始化"规划金额"和"控制余额"控件，当编辑对象为非明细框架合约时，不能编辑，
	 * 因为这两金额是从下级框架合约汇总上来的.
	 * 
	 * 注意：还未保存过的合约规划是没有上下级关系的，所以那种情况下非明细也可以编辑，未处理此种情况。
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @Author：owen_wen
	 * @CreateTime：2012-4-4 
	 */
	private void initAmountControlEnable() throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent", editData.getId().toString()));
		boolean isExistChildren = ProgrammingContractFactory.getRemoteInstance().exists(filter); //存在明细
		this.txtAmount.setEnabled(!isExistChildren);
		this.txtControlAmount.setEnabled(!isExistChildren);
	}

	/**
	 * 在成本构成和经济条款页签中添加新增、删除按钮
	 */
	private void setSmallButton() {
		btnAddnewLine_cost = new KDWorkButton();
		btnRemoveLines_cost = new KDWorkButton();
		btnAddnewLine_economy = new KDWorkButton();
		btnRemoveLines_economy = new KDWorkButton();

		btnAddnewLine_cost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddnewLine_cost_actionPerformed(e);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}

		});
		btnRemoveLines_cost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLine_cost_actionPerformed(e);
					if (kdtCost.getRowCount() == 0) {
						btnRemoveLines_cost.setEnabled(false);
					}
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});
		btnAddnewLine_economy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddnewLine_enocomy_actionPerformed(e);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});
		btnRemoveLines_economy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLine_enocomy_actionPerformed(e);
					//if (kdtEconomy.getRowCount() == 0) {
					//		btnRemoveLines_economy.setEnabled(false);
					//} 
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});

		setButtonStyle(kdContainerCost, btnAddnewLine_cost, "新增行", "imgTbtn_addline");
		setButtonStyle(kdContainerCost, btnRemoveLines_cost, "删除行", "imgTbtn_deleteline");
		setButtonStyle(kDContainerEconomy, btnAddnewLine_economy, "新增行", "imgTbtn_addline");
		setButtonStyle(kDContainerEconomy, btnRemoveLines_economy, "删除行", "imgTbtn_deleteline");

		if (OprtState.VIEW.equals(getOprtState())) {
			setButtionEnable(false);
		} else {
			setButtionEnable(true);
		}
	}

	/**
	 * 设置按钮样式
	 * 
	 * @param kdContainer
	 * @param button
	 * @param text
	 * @param icon
	 */
	private void setButtonStyle(KDContainer kdContainer, KDWorkButton button, String text, String icon) {
		button.setText(text);
		button.setToolTipText(text);
		button.setVisible(true);
		button.setIcon(EASResource.getIcon(icon));
		kdContainer.addButton(button);
	}

	/**
	 * 控制按钮是否可用
	 * 
	 * @param isEnable
	 */
	private void setButtionEnable(boolean isEnable) {
		btnAddnewLine_cost.setEnabled(isEnable);
		btnRemoveLines_cost.setEnabled(isEnable);
		if(addFxbdLine != null)
			addFxbdLine.setEnabled(isEnable);
		if(removeFxbdLine != null)
			removeFxbdLine.setEnabled(isEnable);
//		btnAddnewLine_economy.setEnabled(isEnable);
//		btnRemoveLines_economy.setEnabled(isEnable);
	}

	protected void actionAddnewLine_cost_actionPerformed(ActionEvent e) {
		IRow row = kdtCost.addRow();
		int rowIndex = row.getRowIndex();

		CurProjectInfo project = (CurProjectInfo)this.getUIContext().get("project");
		row.getCell(COST_ID).setValue(BOSUuid.create("9E6FDD26"));
		row.getCell(PROJECT).setValue(project);
//		projectF7();
		costAccountCellF7(project, rowIndex, kdtCost.getColumnIndex(COSTACCOUNT));
		
		// modified by zhaoqin for R130828-0384 on 2013/9/29 modify by yxl
//		changeTxtAmountState();
//		if(kdtCost.getRowCount()>0){
//			txtAmount.setEnabled(false);
//		}else{
//			txtAmount.setEnabled(true);
//			txtAmount.setRequired(true);
//		}
	}

	protected void actionRemoveLine_cost_actionPerformed(ActionEvent e) throws Exception {
		if (kdtCost.getSelectManager().getActiveRowIndex() < 0) {
			FDCMsgBox.showInfo("请先选择记录行");
			SysUtil.abort();
		}
		if (FDCMsgBox.OK == FDCMsgBox.showConfirm2("是否确认删除数据？")) {
			int rowIndex = this.kdtCost.getSelectManager().getActiveRowIndex();
			Object contractAssignObj = kdtCost.getCell(rowIndex, CONTRACTASSIGN).getValue();
			if (contractAssignObj != null) {
				BigDecimal contractAssign = new BigDecimal(contractAssignObj.toString());
				Object amountObj = this.txtAmount.getValue();
				if (amountObj != null) {
					BigDecimal amount = new BigDecimal(amountObj.toString());
					txtAmount.setValue(amount.subtract(contractAssign));
					afterPlanAmountChange();
				}
			}
			removeLine(kdtCost, rowIndex);
		}
		
		// modified by zhaoqin for R130828-0384 on 2013/9/29
//		changeTxtAmountState();
//		if(kdtCost.getRowCount()>0){
//			txtAmount.setEnabled(false);
//		}else{
//			txtAmount.setEnabled(true);
//			txtAmount.setRequired(true);
//		}
	}

	protected void actionAddnewLine_enocomy_actionPerformed(ActionEvent e) {
		IRow row = kdtEconomy.addRow();
		row.getCell("delaydays").getStyleAttributes().setBackground(Color.lightGray);
		row.getCell("delaydays").getStyleAttributes().setLocked(true);
		row.getCell(ECONOMY_ID).setValue(BOSUuid.create("144467E3"));
	}

	protected void actionRemoveLine_enocomy_actionPerformed(ActionEvent e) throws Exception {
		if (kdtEconomy.getSelectManager().getActiveRowIndex() < 0) {
			FDCMsgBox.showInfo("请先选择记录行");
			SysUtil.abort();
		}
		if (FDCMsgBox.OK == FDCMsgBox.showConfirm2("是否确认删除数据？")) {
			int rowIndex = this.kdtEconomy.getSelectManager().getActiveRowIndex();
			removeLine(kdtEconomy, rowIndex);
		}
	}

	/**
	 * 显示单据行
	 */
	protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
		dataBinder.loadLineFields(table, row, obj);
	}

	/**
	 * 新建单据行，返回一个新的分录行的默认值
	 */
	protected IObjectValue createNewDetailData(KDTable table, Object obj) {
		if (table == null) {
			return null;
		}
		if (obj instanceof ProgrammingContracCostInfo) {
			ProgrammingContracCostInfo newDetailInfo = (ProgrammingContracCostInfo) obj;
			newDetailInfo.setId(BOSUuid.create("9E6FDD26"));
			return (IObjectValue) newDetailInfo;
		}
		if (obj instanceof ProgrammingContractEconomyInfo) {
			ProgrammingContractEconomyInfo newDetailInfo = new ProgrammingContractEconomyInfo();
			newDetailInfo.setId(BOSUuid.create("144467E3"));
			return (IObjectValue) newDetailInfo;
		}
		return null;
	}

	/**
	 * 在指定表格中删除指定的行
	 * 
	 * @param table
	 * @param rowIndex
	 * @throws Exception
	 */
	protected void removeLine(KDTable table, int rowIndex) throws Exception {
		IObjectValue detailData = (IObjectValue) table.getRow(rowIndex).getUserObject();
		table.removeRow(rowIndex);
		IObjectCollection collection = (IObjectCollection) table.getUserObject();
		if (collection != null) {
			if (detailData != null) {
				collection.removeObject(rowIndex);
			}
		}
	}

	/**
	 * 格式化KDFormattedTextField文本框
	 */
	private void initFormat() {
		
		//yuxue
		setTextFormat(txtAmount);
		setTextFormat(txtControlAmount);
		setTextFormat(txtReservedChangeRate);
		
		txtReservedChangeRate.setPercentDisplay(true);
		txtReservedChangeRate.setMaximumValue(FDCHelper.ONE_HUNDRED);
		txtReservedChangeRate.setMinimumValue(FDCHelper.ZERO);
//		txtReservedChangeRate.setRemoveingZeroInDispaly(false);
//		txtReservedChangeRate.setRemoveingZeroInEdit(false);
//		txtReservedChangeRate.setPrecision(4);
//		txtReservedChangeRate.setHorizontalAlignment(JTextField.RIGHT);
//		txtReservedChangeRate.setSupportedEmpty(true);
//		filterORG();
	}

	private void filterORG() {
		EntityViewInfo view = prmtInviteOrg.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		if (view.getFilter() == null) {
			view.setFilter(new FilterInfo());
		}
		Set idSet = null;
		try {
			idSet = FDCUtils.getAuthorizedOrgs(null);
		} catch (Exception e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("COSTCENTERORGUNIT.id", idSet, CompareType.INCLUDE));
		prmtInviteOrg.setEntityViewInfo(view);
	}

	private void initF7() {
	}

	private void initTabalFormat() {
		// kdtCost.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		// kdtCost.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		// kdtEconomy.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		// kdtEconomy.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		/* 成本构成 */
		this.kdtCost.checkParsed();
		// 目标成本
		FDCHelper.formatTableNumber(kdtCost, GOALCOST);
		// 已分配
		FDCHelper.formatTableNumber(kdtCost, ASSIGNED);
		// 待分配
		FDCHelper.formatTableNumber(kdtCost, ASSIGNING);
		// 本合约分配
		FDCHelper.formatTableNumber(kdtCost, CONTRACTASSIGN);
		
		// modified by zhaoqin for R131118-0273 on 2013/11/20
		kdtCost.getColumn(CONSTRACTSCALE).getStyleAttributes().setNumberFormat(FDCHelper.strPropFormat2);
		
		KDTDefaultCellEditor cellEditor0 = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf = (KDFormattedTextField) cellEditor0.getComponent();
		kdftf.setDataType(1);
		kdftf.setSupportedEmpty(true);
		kdftf.setMinimumValue(FDCHelper.ZERO);
		kdtCost.getColumn(CONTRACTASSIGN).setEditor(cellEditor0);
		kdtCost.getHeadRow(0).getCell(CONSTRACTSCALE).setValue("本合约占成本科目的比例");
		kdtCost.getColumn(CONSTRACTSCALE).setWidth(160);
		//本合约比例
		KDTDefaultCellEditor cellEditor2 = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf2 = (KDFormattedTextField) cellEditor2.getComponent();
		kdftf2.setDataType(1);
		//kdftf2.setPrecision(2);	// modified by zhaoqin for R131118-0273 on 2013/11/20
		kdftf2.setPrecision(12);
		kdftf2.setSupportedEmpty(true);
		kdftf2.setMaximumValue(FDCHelper.ONE_HUNDRED);
		kdftf2.setMinimumValue(new BigDecimal("0"));
		this.kdtCost.getColumn(CONSTRACTSCALE).setEditor(cellEditor2);
		// 备注
		KDTDefaultCellEditor cellEditorDes = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtfDes = (KDTextField) cellEditorDes.getComponent();
		kdtfDes.setMaxLength(80);
		this.kdtCost.getColumn(COST_DES).setEditor(cellEditorDes);
		// modify by yxl 20151109
//		projectF7();
		costAccountF7((CurProjectInfo)this.getUIContext().get("project"));
		kdtCost.getColumn(PROJECT).getStyleAttributes().setHided(true);
		/* 经济条款 */
		//added by shangjing 初始化经济条款分录的设置
//		kDContainerEconomy.setTitle("经济条款列表");
//		kdtEconomy.getColumn("description").setWidth(0);
//		KDFormattedTextField formattedTextField1 = new KDFormattedTextField(KDFormattedTextField.INTEGER_TYPE);
//		ICellEditor integerEditor = new KDTDefaultCellEditor(formattedTextField1);
//		kdtEconomy.getColumn("delaydays").setEditor(integerEditor);
//		kdtEconomy.getColumn("completedate").getStyleAttributes().setLocked(true);
//		kdtEconomy.getColumn("delaydays").getStyleAttributes().setLocked(true);
//		scheduleTaskF7();
//		this.kdtEconomy.checkParsed();
//		// 付款日期
//		this.kdtEconomy.getColumn(PAYMENTDATE).getStyleAttributes().setNumberFormat("yyyy-MM-dd");
//		// 付款条件 -----长度不能超过80个字符
//		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDTextField());
//		KDTextField kdtf = (KDTextField) cellEditor.getComponent();
//		kdtf.setMaxLength(80);
//		this.kdtEconomy.getColumn(CONDITION).setEditor(cellEditor);
	}

	/**
	 * 初始化KDFormattedTextField的相关基础属性
	 * */
	private static void setTextFormat(KDFormattedTextField textField) {
		textField.setRemoveingZeroInDispaly(false);
		textField.setRemoveingZeroInEdit(false);
		textField.setPrecision(2);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setSupportedEmpty(true);
	}

	/**
	 * 按钮控制
	 * 
	 * @param b
	 */
	private void ctrButtonEnable(boolean isEnable) {
		btnAttachment.setEnabled(true);
		btnSave.setEnabled(isEnable);
		btnAddnewLine_cost.setEnabled(isEnable);
		btnRemoveLines_cost.setEnabled(isEnable);
		btnAddnewLine_economy.setEnabled(isEnable);
		btnRemoveLines_economy.setEnabled(isEnable);
	}

	/**
	 * 编辑控制
	 * 
	 * @param b
	 */
	private void isEditable(boolean isEditable) {
		// 文件编辑框
		txtParentLongName.setEditable(isEditable);
		txtNumber.setEditable(isEditable);
		txtName.setEditable(isEditable);
		txtAmount.setEditable(isEditable);
		txtControlAmount.setEditable(isEditable);
		txtReservedChangeRate.setEditable(isEditable);

		txtWorkContent.setEditable(isEditable);
		txtContractContUI.setEditable(isEditable);
		txtAttachWork.setEditable(isEditable);
		txtAttContract.setEditable(isEditable);
		txtSupMaterial.setEditable(isEditable);
		txtDescription.setEditable(isEditable);
		kdcInviteWay.setEditable(isEditable);
		kdcInviteWay.setEnabled(isEditable);
		prmtInviteOrg.setEditable(isEditable);
		prmtInviteOrg.setEnabled(isEditable);

		// 分录
		kdtCost.setEditable(isEditable);
		kdtEconomy.setEditable(isEditable);
		
		//modify by yxl 20150813
		kdtfxbd.setEditable(isEditable);
	}

	/**
	 * 关闭窗口前事件
	 */
	private boolean directExit = false;

	protected boolean checkBeforeWindowClosing() {
		this.kdtCost.getEditManager().editingStopped();
		this.kdtEconomy.getEditManager().editingStopped();
		
		/* modified by zhaoqin for R131227-0115 on 2013/12/27 start */
		/*try {
			this.txtControlAmount.commitEdit();
			this.txtAmount.commitEdit();
		} catch (ParseException e1) {
			handUIExceptionAndAbort(e1);
		}*/
		/* modified by zhaoqin for R131227-0115 on 2013/12/27 end */
		
		if (this.oprtState.equals(OprtState.ADDNEW) || this.oprtState.equals(OprtState.EDIT)) {
			if (verifyIsModify()) {
				int i = FDCMsgBox.showConfirm3("数据已修改，是否保存并退出?");
				if (i == FDCMsgBox.OK) {
					try {
						directExit = true;
						actionSubmit_actionPerformed(null);
					} catch (Exception e) {
						handUIExceptionAndAbort(e);
					}
				} else if (i == FDCMsgBox.NO) {
					return super.checkBeforeWindowClosing();
				} else if (i == FDCMsgBox.CANCEL) {
					return false;
				}
			}
		}
		return super.checkBeforeWindowClosing();
	}

	/**
	 * 判断单据是否已做修改
	 * @return	   true表示已修改过，false表示未做修改
	 */
	private boolean verifyIsModify() {
		if (oprtState.equals(OprtState.VIEW)) {
			return false;
		}
		// 界面状态不是EDIT并且也不是ADDNEW时，返回false
		if (!oprtState.equals(OprtState.EDIT) && !oprtState.equals(OprtState.ADDNEW)) {
			return false;
		}
		if (isBillHeadModified())
			return true;

		if (isCostModified())
			return true;
		//经济条款注释掉   加上副项表单    modified by yxl 20150814
//		if (isEconomyModified())
//			return true;
		if(isFxbdModified())
			return true;
		if(payPlanNewUI.verifyTabIsModify())
			return true;
		return false;
	}

	/**
	 * 重构抽取出来的方法：单据头是否有修改 
	 * @Author：owen_wen
	 * @CreateTime：2012-4-7
	 */
	private boolean isBillHeadModified() {
		// 长编码
		if (FDCHelper.isEmpty(txtNumber.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getLongNumber())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtNumber.getText()) & !FDCHelper.isEmpty(oldPcInfo.getLongNumber())) {
			if (!txtNumber.getText().equals(oldPcInfo.getLongNumber())) {
				return true;
			}
		}
		// 名称
		if (FDCHelper.isEmpty(txtName.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getName())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtName.getText()) & !FDCHelper.isEmpty(oldPcInfo.getName())) {
			if (!txtName.getText().equals(oldPcInfo.getName().trim())) {
				return true;
			}
		}
		// 采购控制金额
		if (FDCHelper.isEmpty(txtControlAmount.getNumberValue()) ^ FDCHelper.isEmpty(oldPcInfo.getControlAmount())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtControlAmount.getNumberValue()) & !FDCHelper.isEmpty(oldPcInfo.getControlAmount())) {
			if (new BigDecimal(txtControlAmount.getNumberValue().toString()).compareTo(oldPcInfo.getControlAmount()) != 0) {
				return true;
			}
		}

		// 规划金额
		if (FDCHelper.isEmpty(txtAmount.getNumberValue()) ^ FDCHelper.isEmpty(oldPcInfo.getAmount())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtAmount.getNumberValue()) & !FDCHelper.isEmpty(oldPcInfo.getAmount())) {
			if (new BigDecimal(txtAmount.getNumberValue().toString()).compareTo(oldPcInfo.getAmount()) != 0) {
				return true;
			}
		}

		// 招标方式
//		if (FDCHelper.isEmpty(kdcInviteWay.getSelectedItem()) ^ FDCHelper.isEmpty(oldPcInfo.getInviteWay())) {
//			return true;
//		}
//		if (!FDCHelper.isEmpty(kdcInviteWay.getSelectedItem()) & !FDCHelper.isEmpty(oldPcInfo.getInviteWay())) {
//			if (!kdcInviteWay.getSelectedItem().equals(oldPcInfo.getInviteWay())) {
//				return true;
//			}
//		}

		// 招标组织
//		if (FDCHelper.isEmpty(prmtInviteOrg.getData()) ^ FDCHelper.isEmpty(oldPcInfo.getInviteOrg())) {
//			return true;
//		}
//		if (!FDCHelper.isEmpty(prmtInviteOrg.getData()) & !FDCHelper.isEmpty(oldPcInfo.getInviteOrg())) {
//			if (!prmtInviteOrg.getData().equals(oldPcInfo.getInviteOrg())) {
//				return true;
//			}
//		}

		// 工作内容，注意为“默认工作内容”时相当于没有填工作内容
		boolean isWorkContentEmpty = DEFAULT_WORK_CONTENT.equals(txtWorkContent.getText()) || FDCHelper.isEmpty(txtWorkContent.getText());
		if (isWorkContentEmpty ^ FDCHelper.isEmpty(oldPcInfo.getWorkContent())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtWorkContent.getText()) & !FDCHelper.isEmpty(oldPcInfo.getWorkContent())) {
			if (!txtWorkContent.getText().equals(oldPcInfo.getWorkContent())) {
				return true;
			}
		}
		if (FDCHelper.isEmpty(txtContractContUI.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getContractContUI())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtContractContUI.getText()) & !FDCHelper.isEmpty(oldPcInfo.getContractContUI())) {
			if (!txtContractContUI.getText().equals(oldPcInfo.getContractContUI())) {
				return true;
			}
		}
		// aat work
		if (FDCHelper.isEmpty(txtAttachWork.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getAttachWork())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtAttachWork.getText()) & !FDCHelper.isEmpty(oldPcInfo.getAttachWork())) {
			if (!txtAttachWork.getText().equals(oldPcInfo.getAttachWork())) {
				return true;
			}
		}
		//aa cont
		if (FDCHelper.isEmpty(txtAttContract.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getAttContract())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtAttContract.getText()) & !FDCHelper.isEmpty(oldPcInfo.getAttContract())) {
			if (!txtAttContract.getText().equals(oldPcInfo.getAttContract())) {
				return true;
			}
		}

		// 甲供及甲指材设
		if (FDCHelper.isEmpty(txtSupMaterial.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getSupMaterial())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtSupMaterial.getText()) & !FDCHelper.isEmpty(oldPcInfo.getSupMaterial())) {
			if (!txtSupMaterial.getText().equals(oldPcInfo.getSupMaterial())) {
				return true;
			}
		}

		// 备注
		if (FDCHelper.isEmpty(txtDescription.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getDescription())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtDescription.getText()) & !FDCHelper.isEmpty(oldPcInfo.getDescription())) {
			if (!txtDescription.getText().equals(oldPcInfo.getDescription())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 重构抽取出来的方法：经济条款是否有修改
	 * @Author：owen_wen
	 * @CreateTime：2012-4-7
	 */
	private boolean isEconomyModified() {
		if (kdtEconomy.getRowCount() != oldPcInfo.getEconomyEntries().size())
			return true;

		for (int i = 0; i < kdtEconomy.getRowCount(); i++) {
			for (int j = 0; j < oldPcInfo.getEconomyEntries().size(); j++) {
				if (oldPcInfo.getEconomyEntries().get(j).getId() == kdtEconomy.getCell(i, ECONOMY_ID).getValue()) {
					PaymentTypeInfo infoPT = oldPcInfo.getEconomyEntries().get(j).getPaymentType();
					BigDecimal infoScale = oldPcInfo.getEconomyEntries().get(j).getScale();
					BigDecimal infoAmount = oldPcInfo.getEconomyEntries().get(j).getAmount();
					String infoCondition = oldPcInfo.getEconomyEntries().get(j).getCondition();
					String infoDescription = oldPcInfo.getEconomyEntries().get(j).getDescription();

					PaymentTypeInfo tablePT = (PaymentTypeInfo) kdtEconomy.getCell(j, PAYMENTTYPE).getValue();
					Object scale = kdtEconomy.getCell(j, SCALE).getValue();
					Object amount = kdtEconomy.getCell(j, AMOUNT).getValue();
					Object condition = kdtEconomy.getCell(j, CONDITION).getValue();
					Object description = kdtEconomy.getCell(j, ECONOMY_DES).getValue();

					// 付款类型
					if (FDCHelper.isEmpty(infoPT) ^ FDCHelper.isEmpty(tablePT)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoPT) & !FDCHelper.isEmpty(tablePT)) {
						if (!infoPT.getName().equals(tablePT.getName())) {
							return true;
						}
					}

					// 付款比例
					if (FDCHelper.isEmpty(infoScale) ^ FDCHelper.isEmpty(scale)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoScale) & !FDCHelper.isEmpty(scale)) {
						BigDecimal temp = new BigDecimal(scale.toString());
						if (infoScale.compareTo(temp) != 0) {
							return true;
						}
					}
					// 付款金额
					if (FDCHelper.isEmpty(infoAmount) ^ FDCHelper.isEmpty(amount)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoAmount) & !FDCHelper.isEmpty(amount)) {
						BigDecimal temp = new BigDecimal(amount.toString());
						if (infoAmount.compareTo(temp) != 0) {
							return true;
						}
					}
					// 付款条件
					if (FDCHelper.isEmpty(infoCondition) ^ FDCHelper.isEmpty(condition)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoCondition) & !FDCHelper.isEmpty(condition)) {
						if (!infoCondition.equals(condition.toString())) {
							return true;
						}
					}
					// 备注
					if (FDCHelper.isEmpty(infoDescription) ^ FDCHelper.isEmpty(description)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoDescription) & !FDCHelper.isEmpty(description)) {
						if (!infoDescription.equals(description.toString())) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	//modify by yxl 20150814  check fxbd is modify
	private boolean isFxbdModified() {
		if(fxcoll == null)
			return false;
		if(kdtfxbd.getRowCount3() == 0)
			return false;
		if(fxcoll.size()==0 && kdtfxbd.getRowCount3()>0)
			return true;
		int maxSize = 0;
		for(int i = 0; i < fxcoll.size(); i++) {
			if(maxSize < Integer.parseInt(fxcoll.get(i).getRecordSeq().substring(3)))
				maxSize = Integer.parseInt(fxcoll.get(i).getRecordSeq().substring(3));
		}
		maxSize++;
		if(maxSize != kdtfxbd.getRowCount3())
			return true;
		ProgrammingContractFxbdEntryInfo feinfo = null;
		Date planDate = null;
		Date realDate = null;
		String recordSeq = null;
		int rowIndex = 0;
		String number = null;
		for (int i = 0; i < fxcoll.size(); i++) {
			feinfo = fxcoll.get(i);
			recordSeq = feinfo.getRecordSeq();
			rowIndex = Integer.parseInt(recordSeq.substring(3));
			number = recordSeq.substring(0,3);
			//判断时间是否修改
			realDate = (Date)kdtfxbd.getCell(rowIndex,number+"date").getValue();
			planDate = feinfo.getPlanDate();
			if(realDate==null ^ planDate==null)
				return true;
			if(realDate!=null & planDate!=null){
				if(new Timestamp(realDate.getTime()).compareTo(new Timestamp(planDate.getTime())) != 0)
					return true;
			}
			//判断预警是否修改
			if(rowIndex==5)
				feinfo.getItemName();
			if(feinfo.isIsYj()^(kdtfxbd.getCell(rowIndex,number+"yj").getValue()==null?false:(Boolean)kdtfxbd.getCell(rowIndex,number+"yj").getValue()))
				return true;
			//如果有用户新增的记录，则要判断事项名称是否改变
//			if(feinfo.isIsNew() && !feinfo.getItemName().equals(kdtfxbd.getCell(rowIndex,number+"name").getValue()))
//				return true;
		}
		return false;
	}
	
	/**
	 * 重构抽取出来的方法：成本构成是否有修改
	 * @Author：owen_wen
	 * @CreateTime：2012-4-7
	 */
	private boolean isCostModified() {
		if (kdtCost.getRowCount() != oldPcInfo.getCostEntries().size())
			return true;

		for (int i = 0; i < kdtCost.getRowCount(); i++) {
			for (int j = 0; j < oldPcInfo.getCostEntries().size(); j++) {
				if (oldPcInfo.getCostEntries().get(j).getId() == kdtCost.getCell(i, COST_ID).getValue()) {
					CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("project");
					CostAccountInfo infoCA = oldPcInfo.getCostEntries().get(j).getCostAccount();
					BigDecimal infoContractAssign = oldPcInfo.getCostEntries().get(j).getContractAssign();
					String infoDescription = oldPcInfo.getCostEntries().get(j).getDescription();

					CurProjectInfo tablePro = (CurProjectInfo) kdtCost.getCell(j, PROJECT).getValue();
					CostAccountInfo tableCA = (CostAccountInfo) kdtCost.getCell(j, COSTACCOUNT).getValue();
					Object contractAssign = kdtCost.getCell(j, CONTRACTASSIGN).getValue();
					Object description = kdtCost.getCell(j, COST_DES).getValue();
					BigDecimal infoContractScale = oldPcInfo.getCostEntries().get(j).getContractScale();
					Object contractScale = kdtCost.getCell(j, CONSTRACTSCALE).getValue();
					// 工程项目
					if (FDCHelper.isEmpty(project) ^ FDCHelper.isEmpty(tablePro)) {
						return true;
					}
					if (!FDCHelper.isEmpty(project) & !FDCHelper.isEmpty(tablePro)) {
						if (!project.getName().equals(tablePro.getName())) {
							return true;
						}
					}
					// 成本科目F7
					if (FDCHelper.isEmpty(infoCA) ^ FDCHelper.isEmpty(tableCA)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoCA) & !FDCHelper.isEmpty(tableCA)) {
						if (!infoCA.getName().equals(tableCA.getName())) {
							return true;
						}
					}
					// 本合约分配
					if (FDCHelper.isEmpty(infoContractAssign) ^ FDCHelper.isEmpty(contractAssign)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoContractAssign) & !FDCHelper.isEmpty(contractAssign)) {
						BigDecimal temp = new BigDecimal(contractAssign.toString());
						if (infoContractAssign.compareTo(temp) != 0) {
							return true;
						}
					}
					// 备注
					if (FDCHelper.isEmpty(infoDescription) ^ FDCHelper.isEmpty(description)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoDescription) & !FDCHelper.isEmpty(description)) {
						if (!infoDescription.equals(description.toString())) {
							return true;
						}
					}
					// 本合约比例
					if (FDCHelper.isEmpty(infoContractScale) ^ FDCHelper.isEmpty(contractScale)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoContractScale) & !FDCHelper.isEmpty(contractScale)) {
						BigDecimal temp = new BigDecimal(contractScale.toString());
						if (infoContractScale.compareTo(temp) != 0) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	//保存时判断是否有重复的成本科目
	public void fromByisrepeat() {
		int rowIndex = kdtCost.getRowCount();
		List list = new ArrayList();
		for (int i = rowIndex - 1; i >= 0; i--) {
			CurProjectInfo forProjectInfo = null;
			CostAccountInfo costaccount = null;
			if (kdtCost.getCell(i, PROJECT).getValue() instanceof CurProjectInfo) {
				forProjectInfo = (CurProjectInfo) kdtCost.getCell(i, PROJECT).getValue();
			}
			if (kdtCost.getCell(i, COSTACCOUNT).getValue() instanceof CostAccountInfo) {
				costaccount = (CostAccountInfo) kdtCost.getCell(i, COSTACCOUNT).getValue();
			}

			String ids = forProjectInfo.getId().toString() + costaccount.getId();
			list.add(ids);
		}
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).toString().equals(list.get(j).toString())) {
					FDCMsgBox.showInfo("本框架合约成本构成内存在重复的成本科目!");
					//					return ;
					SysUtil.abort();
				}
			}
		}
	}

	/**
	 * 保存
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		
		if (getUIContext().get("editPayPlan") != null) {
			payPlanNewUI.actionSave_actionPerformed(e);
		} else {
			verifyIsEmpty();
			verifyAllData();
			//	判断是否有重复的成本科目
			fromByisrepeat();

			// modified by zhaoqin on 2013/11/22
			storeEditData();
			
			payPlanNewUI.actionSave_actionPerformed(e);
			if (directExit) {
				// 直接保存退出，不作提示
				// verifyIsEmpty();
				// verifyAllData();
			} else {
				FDCMsgBox.showInfo("保存框架合约成功！");
			}
			oldPcInfo = editData;
			ProgrammingContractInfo parentsd = editData.getParent();
			if (parentsd != null && parentsd.getCostEntries().size() > 0) {
				parentsd.getCostEntries().clear();
		}
		}
		lockUIComponent();
		kdtfxbd.setEditable(false);
		kdtCost.setEditable(false);
		actionSubmit.setEnabled(false);
		btnSave.setEnabled(false);
		setButtionEnable(false);
		txtContractContUI.setEnabled(false);
		txtAttachWork.setEnabled(false);
		txtAttContract.setEnabled(false);
		txtWorkContent.setEnabled(false);
		// destroyWindow();

		// /////////////////////////////////////////////////////////////////////
		// start，厦门建发，合约规划的详细内容及列表界面加入4个字段“发包方式”、“招投标方式”、“预计发包开始时间”、“预计发包结束时间”等四个字段
		// by skyiter_wang 20131009
		// /////////////////////////////////////////////////////////////////////
//		editData.setEstimateAwardStartDate((Date) pkEstimateAwardStartDate.getValue());
//		editData.setEstimateAwardEndDate((Date) pkEstimateAwardEndDate.getValue());
//		editData.setInviteMode((InviteModeInfo) prmtInviteMode.getData());
//		editData.setJobType((JobTypeInfo) prmtJobType.getData());

		// /////////////////////////////////////////////////////////////////////
		// end
		// /////////////////////////////////////////////////////////////////////
		
		/* modified by zhaoqin for R131127-0266 on 2013/12/11 */
		getUIContext().put("isModified", Boolean.TRUE);
	}

	/**
	 * 更新规划余额，控制余额
	 */
	private void updateBalance(ProgrammingContractInfo pcInfo) {
		BigDecimal newAmount = pcInfo.getAmount();// 规划金额
		if (newAmount == null) {
			newAmount = FDCHelper.ZERO;
		}
		BigDecimal newControlAmount = pcInfo.getControlAmount();// 采购控制金额
		if (newControlAmount == null) {
			newControlAmount = FDCHelper.ZERO;
		}
		// 设置新的规划余额
		pcInfo.setBalance(oldbalance.add(newAmount.subtract(oldAmount)));
		// 设置新的控制余额
		pcInfo.setControlBalance(oldcontrolBalance.add(newControlAmount.subtract(oldControlAmount)));
	}

	/**
	 * 在名称前添加空格，显示缩进效果
	 * 
	 * @param level
	 * @return
	 */
	private String setNameIndent(int level) {
		StringBuffer blank = new StringBuffer("");
		for (int i = level; i > 1; i--) {
			blank.append("        ");
		}
		return blank.toString();
	}

	/**
	 * 必录项判空验证
	 */
	private void verifyIsEmpty() {
		ProgrammingContractInfo head = editData.getParent();
		if (head != null) {
			String longNumber = head.getLongNumber();
			if (txtNumber.getText().equals(longNumber + ".")) {
				FDCMsgBox.showInfo("框架合约编码不能为空！");
				txtNumber.requestFocus();
				SysUtil.abort();
			}
			for (int i = 0; i < pcCollection.size(); i++) {
				ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
				if (programmingContractInfo.getLongNumber().equals(txtNumber.getText())
						&& !editData.getId().toString().equals(programmingContractInfo.getId().toString())) {
					FDCMsgBox.showInfo("框架合约编码已存在，请重新输入！");
					txtNumber.requestFocus();
					SysUtil.abort();
				}
			}
		} else {
			if (FDCHelper.isEmpty(txtNumber.getText())) {
				FDCMsgBox.showInfo("框架合约编码不能为空！");
				txtNumber.requestFocus();
				SysUtil.abort();
			}
		}
		if (FDCHelper.isEmpty(txtName.getText())) {
			FDCMsgBox.showInfo("框架合约名称不能为空！");
			txtName.requestFocus();
			SysUtil.abort();
		} else {
			for (int i = 0; i < pcCollection.size(); i++) {
				ProgrammingContractInfo rowObject = pcCollection.get(i);
				String name = rowObject.getName();
				if (!FDCHelper.isEmpty(name)) {
					if (txtName.getText().equals(name.trim()) && !editData.getId().toString().equals(rowObject.getId().toString())) {
						FDCMsgBox.showInfo("框架合约名称已存在，请重新输入！");
						txtName.requestFocus();
						SysUtil.abort();
					}
				}
			}
		}
		// if (this.oprtState.equals(OprtState.ADDNEW)) {
		// isNameDup(txtName.getText(), pcInfo.getId().toString());
		// isNumberDup(txtNumber.getText(), null);
		// } else if (this.oprtState.equals(OprtState.EDIT)) {
		// isNameDup(txtName.getText(), pcInfo.getId().toString());
		// isNumberDup(txtNumber.getText(), pcInfo.getId().toString());
		// }
		// 成本构成分录必录项验空
		for (int i = 0; i < kdtCost.getRowCount(); i++) {
			IRow row = kdtCost.getRow(i);
			ICell projectCell = row.getCell(PROJECT);
			ICell costAccountCell_number = row.getCell(COSTACCOUNT_NUMBER);
			ICell costAccountCell_name = row.getCell(COSTACCOUNT);
			ICell goalCost = row.getCell(GOALCOST);
			ICell assigned = row.getCell(ASSIGNED);
			ICell assigning = row.getCell(ASSIGNING);
			ICell contractAssign = row.getCell(CONTRACTASSIGN);
			Object contractScale = row.getCell(CONSTRACTSCALE).getValue();
			if (FDCHelper.isEmpty(projectCell.getValue())) {
				FDCMsgBox.showInfo("工程项目不能为空！");
				SysUtil.abort();
			}
			//if (FDCHelper.isEmpty(costAccountCell_number.getValue())) {
			//		FDCMsgBox.showInfo("成本科目编码不能为空！");
			//		SysUtil.abort();
			//}
			if (FDCHelper.isEmpty(costAccountCell_name.getValue())) {
				FDCMsgBox.showInfo("成本科目名称不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(goalCost.getValue())) {
				FDCMsgBox.showInfo("目标成本不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(assigned.getValue())) {
				FDCMsgBox.showInfo("已分配不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(assigning.getValue())) {
				FDCMsgBox.showInfo("待分配不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(contractAssign.getValue())) {
				FDCMsgBox.showInfo("本合约分配不能为空！");
				SysUtil.abort();
			}
		}
		// 经济条款分录必录项验空
//		for (int i = 0; i < kdtEconomy.getRowCount(); i++) {
//			IRow row = kdtEconomy.getRow(i);
//			ICell costAccountCell = row.getCell(PAYMENTTYPE);
//			ICell scale = row.getCell(SCALE);
//			if (FDCHelper.isEmpty(costAccountCell.getValue())) {
//				FDCMsgBox.showInfo("付款类型不能为空！");
//				SysUtil.abort();
//			}
//			if (FDCHelper.isEmpty(scale.getValue())) {
//				FDCMsgBox.showInfo("付款比例不能为空！");
//				SysUtil.abort();
//			}
//		}
		
	}

	private void verifyAllData() {

		BigDecimal amount = (BigDecimal) txtAmount.getValue();// 规划金额
		BigDecimal controlAmount = (BigDecimal) txtControlAmount.getValue();// 采购控制金额
		
		/* modified by zhaoqin for R131227-0115 on 2013/01/24 start */
		amount = null == amount ? amount : amount.setScale(2, BigDecimal.ROUND_HALF_UP);
		controlAmount = null == controlAmount ? controlAmount : controlAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		/* modified by zhaoqin for R131227-0115 on 2013/01/24 end */
		
		if (amount != null) {
			// 规划金额不能小于0
			if (amount.compareTo(FDCHelper.ZERO) < 0) {
				FDCMsgBox.showInfo("规划金额不能小于0");
				SysUtil.abort();
			}
		}
		if (controlAmount != null) {
			// 采购控制金额不能小于0
			if (controlAmount.compareTo(FDCHelper.ZERO) < 0) {
				FDCMsgBox.showInfo("采购控制金额不能小于0");
				SysUtil.abort();
			}
		}
		if (amount != null && controlAmount != null) {
			// 采购控制金额不能大于规划金额
			if (controlAmount.compareTo(amount) > 0) {
				FDCMsgBox.showInfo("采购控制金额不得大于规划金额");
				SysUtil.abort();
			}
			
			/* modified by zhaoqin for R140107-0185 on 2014/01/23 start */
			// 规划金额不能小于引用的合约的已发生金额
			//if (amount.compareTo(getHappenedAmount()) < 0) {
			/*BigDecimal happenedAmount = FDCHelper.subtract(editData.getAmount(), editData.getBalance());
			if (amount.compareTo(happenedAmount) < 0) {
				FDCMsgBox.showInfo("规划金额不能小于已发生金额");
				SysUtil.abort();
			}*/
			/* modified by zhaoqin for R140107-0185 on 2014/01/23 end */
		}

		int costRowCount = kdtCost.getRowCount();
		for (int i = 0; i < costRowCount; i++) {
			Object assigningObj = kdtCost.getCell(i, ASSIGNING).getValue();
			Object contractAssignObj = kdtCost.getCell(i, CONTRACTASSIGN).getValue();
			if (assigningObj != null && contractAssignObj != null) {
				BigDecimal assigning = new BigDecimal(assigningObj.toString());
				BigDecimal contractAssign = new BigDecimal(contractAssignObj.toString());
				if (assigning.compareTo(contractAssign) < 0) {
					FDCMsgBox.showInfo("本合约分配金额不得大于待分配金额");
					SysUtil.abort();
				}
			}
		}

	}

	/**
	 * 
	 * 描述：合约框架下所有合同的已发生金额取值公式(周鹏提供)如下：<br/>
	 * 		合同最终结算前: 已发生金额 = 合同金额+变更金额（变更单是否结算，未结算：取预算金额，已结算：取结算金额，）<br/>
	 *		合同最终结算后: 已发生金额 = 合同结算金额<br/>
	 * @return
	 * @Author：jian_cao
	 * @CreateTime：2012-8-28
	 */
	private BigDecimal getHappenedAmount() {

		//如果规划合约源ID为空就证明 是新增的规划合约，新增的规划合约还是没有被合同关联的就不需要计算已发生金额
		if (this.oldPcInfo.getSrcId() == null)
			return FDCHelper.ZERO;

		BigDecimal happenedAmount = FDCHelper.ZERO;
		EntityViewInfo viewInfo = new EntityViewInfo();
		viewInfo.setFilter(new FilterInfo());
		try {

			viewInfo.getFilter().appendFilterItem("programmingContract", this.oldPcInfo.getSrcId());
			//查询出合约框架下的所有合同
			ContractBillCollection contractBillCollection = (ContractBillCollection) ContractBillFactory.getRemoteInstance()
					.getContractBillCollection(viewInfo);
			//如果合约规划下没有合同就返回
			if (null == contractBillCollection || contractBillCollection.size() == 0) {
				return FDCHelper.ZERO;
			}
			//存储结算合同ID的set
			Set settlementContractBillSet = new HashSet();
			//存储变更合同ID的字符串
			StringBuffer changeContractBillBuff = new StringBuffer();
			//循环框架下的所有合同
			for (int i = 0, size = contractBillCollection.size(); i < size; i++) {
				ContractBillInfo conotractBillInfo = contractBillCollection.get(i);
				String contractId = conotractBillInfo.getId().toString();
				//已结算合同
				if (conotractBillInfo.isHasSettled()) {
					settlementContractBillSet.add(contractId);
				} else {
					//累加合同金额
					happenedAmount = happenedAmount.add(conotractBillInfo.getAmount());
					changeContractBillBuff.append("'").append(contractId).append("'").append(",");
				}
			}

			//累加已经结算的合同的结算金额
			if (settlementContractBillSet.size() > 0) {
				viewInfo.getFilter().getFilterItems().clear();
				viewInfo.getFilter().getFilterItems().add(
						new FilterItemInfo("contractBill", settlementContractBillSet, CompareType.INCLUDE));
				ContractSettlementBillCollection settlementBills = ContractSettlementBillFactory.getRemoteInstance()
						.getContractSettlementBillCollection(viewInfo);
				for (int i = 0, size = settlementBills.size(); i < size; i++) {
					happenedAmount = happenedAmount.add(settlementBills.get(i).getSettlePrice());
				}
			}

			//累加未结算的合同的变更金额
			if (changeContractBillBuff.length() > 0) {
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("select fhasSettled,famount,fbalanceAmount from T_CON_CONTRACTCHANGEBILL where fcontractbillid in");
				builder.appendSql("(").appendSql(changeContractBillBuff.substring(0, changeContractBillBuff.length() - 1)).appendSql(") ");
				builder.appendSql(" and (fstate='" + FDCBillStateEnum.AUDITTED_VALUE + "' or");
				builder.appendSql(" fstate='" + FDCBillStateEnum.ANNOUNCE_VALUE + "' or");
				builder.appendSql(" fstate='" + FDCBillStateEnum.VISA_VALUE + "') ");
				IRowSet rowSet = builder.executeQuery();
				while (rowSet.next()) {
					//已计算取结算金额
					if (rowSet.getBoolean("fhasSettled")) {
						happenedAmount = FDCNumberHelper.add(happenedAmount, rowSet.getBigDecimal("fbalanceAmount"));
					} else {
						//未结算取预算金额
						happenedAmount = FDCNumberHelper.add(happenedAmount, rowSet.getBigDecimal("famount"));
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		}

		return happenedAmount;
	}

	/**
	 * 成本构成分录单击事件
	 */
	protected void kdtCost_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		// 选中行则“删除行”按钮可用
		if (e.getRowIndex() > -1 && !oprtState.equals(OprtState.VIEW)
				&& e.getType() != KDTStyleConstants.HEAD_ROW) {
			btnRemoveLines_cost.setEnabled(true);
		}
		if (e.getColIndex() == kdtCost.getColumnIndex(COSTACCOUNT)
				&& e.getType() != KDTStyleConstants.HEAD_ROW) {
			CurProjectInfo project = (CurProjectInfo) kdtCost.getCell(rowIndex, PROJECT).getValue();
			if (project == null) {
				FDCMsgBox.showInfo("请先录入工程项目");
			} else {
				kdtCost.getColumn(COSTACCOUNT).getStyleAttributes().setLocked(false);
			}
		}
	}

	/**
	 * 成本构成分录数据编辑处理
	 */
	protected void kdtCost_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		Object oldValue = e.getOldValue();
		// 编辑"工程项目"
		if (colIndex == kdtCost.getColumnIndex(PROJECT)) {

			Object projectObj = kdtCost.getCell(rowIndex, colIndex).getValue();

			if (oldValue != null && projectObj != null) {
				CurProjectInfo newProject = (CurProjectInfo) projectObj;
				CurProjectInfo oldProject = (CurProjectInfo) oldValue;

				if (!newProject.getId().toString().equals(oldProject.getId().toString())) {
					ProgrammingContractUtil.clearCell(kdtCost, rowIndex, "contractScale", COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST,
							ASSIGNED, ASSIGNING,CONTRACTASSIGN, COST_DES);
					
					// modified by zhaoqin on 2013/10/23
					kdtCost.getCell(rowIndex, AIMCOST_ID).setValue(null);
				}
			}

			if (projectObj != null) {
				CurProjectInfo newProject = (CurProjectInfo) projectObj;
				costAccountCellF7(newProject, rowIndex, kdtCost.getColumnIndex(COSTACCOUNT));// 通过工程项目为条件重新加载成本科目F7
				// 工程项目不变，则不做处理
				if (oldValue != null) {
					CurProjectInfo oldProject = (CurProjectInfo) oldValue;
					if (newProject.getNumber().equals(oldProject.getNumber())) {
						return;
					}
				}

				// 取目标成本，为空则把当前行除工程项目外所有值置空
				AimCostInfo aimCostInfo = null;
				Object aimCostObj = this.getUIContext().get("aimCostInfo");
				if (aimCostObj == null) {
					ProgrammingContractUtil.clearCell(kdtCost, rowIndex, 
							COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST,
							ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
					// modified by zhaoqin on 2013/10/23
					kdtCost.getCell(rowIndex, AIMCOST_ID).setValue(null);
				} else {
					aimCostInfo = (AimCostInfo) aimCostObj;
				}
				
				// 取当前工程项目对应的目标成本 - modified by zhaoqin on 2013/10/22
				CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("project");
				if(!project.getId().toString().equals(newProject.getId().toString()))
					aimCostInfo = getLastAimCostByCurProject(newProject);
				// 解决合约规划跨期时目标成本问题版本问题
				if(null != aimCostInfo)
					kdtCost.getCell(rowIndex, AIMCOST_ID).setValue(aimCostInfo.getId().toString());
				
				/*
				 * 工程项目改变之后
				 * 
				 * 1.
				 * 
				 * 2.判断当前行是否已选了成本科目
				 * 
				 * 2.1:若无：清空除工程项目外所有当前行单元格
				 * 
				 * 2.2:若有：判断所变的工程项目是否也有此成本科目
				 * 
				 * 2.2.1:若无：清空除工程项目外所有当前行单元格
				 * 
				 * 2.2.2:若有：
				 * 
				 * 2.2.2.1:给当前行成本科目单元格重新关联新的成本科目
				 * 
				 * 2.2.2.2：获取"目标成本"
				 * 
				 * 2.2.2.2.1：判断目标成本是否为0
				 * 
				 * 2.2.2.2.1.1:为0，给"目标成本","已分配","待分配","本合约分配"赋值0,备注清空
				 * 
				 * 2.2.2.2.1.2:不为0，算出"已分配","待分配","本合约分配"各值
				 * 
				 * 最后把牵涉到的值：规划金额，经济条款中"付款比例"、"付款金额"等值 更新一遍
				 */
				// 1
				Object costAccountObj = kdtCost.getCell(rowIndex, COSTACCOUNT).getValue();
				// 2
				if (costAccountObj == null) {
					// 2.1
					ProgrammingContractUtil.clearCell(kdtCost, rowIndex,
							COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST,
							ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
				} else {
					// 2.2
					CostAccountInfo costAccount = (CostAccountInfo) costAccountObj;
					String newCostAccountID = ProgrammingContractUtil.isExitCostAccount(newProject, costAccount);
					if (newCostAccountID == null) {
						// 2.2.1
						ProgrammingContractUtil.clearCell(kdtCost, rowIndex,
								COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST,
								ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
					} else {
						// 2.2.2
						// 2.2.2.1给当前行成本科目单元格重新关联新的成本科目
						CostAccountInfo newCostAccountInfo = ProgrammingContractUtil
								.getCostAccountByNewID(newCostAccountID);
						kdtCost.getCell(rowIndex, COSTACCOUNT).setValue(newCostAccountInfo);
						kdtCost.getCell(rowIndex, COSTACCOUNT_NUMBER).setValue(
								newCostAccountInfo.getLongNumber().replace('!','.'));
						// 2.2.2.2：获取"目标成本"
						
						// modified by zhaoqin on 2013/10/22 start
						// newCostAccountInfo为非明细科目时，不需要调用ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost
						// BigDecimal goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(newCostAccountInfo, aimCostInfo);
						BigDecimal goalCost = null;
						// 取可拆分非明细科目的汇总目标成本 add by 何鹏
						if (!newCostAccountInfo.isIsLeaf()) {
							goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_totalAimCost(newCostAccountInfo, aimCostInfo);
						} else {
							goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(newCostAccountInfo, aimCostInfo);
						}
						// modified by zhaoqin on 2013/10/22 end
						
						// 2.2.2.2.1
						if (goalCost.compareTo(FDCHelper.ZERO) == 0) {
							// 2.2.2.2.1.1
							ProgrammingContractUtil.setZero(kdtCost, rowIndex,
									GOALCOST, ASSIGNED, ASSIGNING,CONTRACTASSIGN, COST_DES);
							afterContractAssignChange();
							afterPlanAmountChange();
							kdtCost.getCell(rowIndex, CONSTRACTSCALE).setValue(FDCHelper.ZERO);
						} else {
							// 2.2.2.2.1.2 算出"已分配","待分配","本合约分配"各值
							BigDecimal allAssigned = FDCHelper.ZERO;// 已分配
							// 算出"待分配" == "目标成本" - "已分配"
							BigDecimal assigning = goalCost.subtract(allAssigned);
							// 带出"本合约分配"="待分配"
							BigDecimal contractAssign = assigning;
							// 显示在单元格中
							kdtCost.getCell(rowIndex, GOALCOST).setValue(goalCost);// 目标成本
							kdtCost.getCell(rowIndex, ASSIGNED).setValue(allAssigned);// 已分配
							kdtCost.getCell(rowIndex, ASSIGNING).setValue(assigning);// 待分配
							kdtCost.getCell(rowIndex, CONTRACTASSIGN).setValue(contractAssign);// 本合约分配
							kdtCost.getCell(rowIndex, CONSTRACTSCALE).setValue(calculateContractSacle(contractAssign, goalCost));
							// 本合约分配带出后又自动算出"规划金额"
							afterContractAssignChange();
							// 自动算出"规划金额"后，动态改变经济条款中"付款比例"和"付款金额"
							// 默认以"付款比例"为定值，改变"付款金额"
							afterPlanAmountChange();
						}
					}
				}
			} else {
				// 工程项目变为空则清空所在行所有行
				ProgrammingContractUtil.clearCell(kdtCost, rowIndex, PROJECT,
						COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST, ASSIGNED,
						ASSIGNING, CONTRACTASSIGN, COST_DES);
				kdtCost.getCell(rowIndex, CONSTRACTSCALE).setValue(null);
				kdtCost.getColumn(COSTACCOUNT).getStyleAttributes().setLocked(true);
			}

		}
		// 编辑"本合约分配"
		if (colIndex == kdtCost.getColumnIndex(CONTRACTASSIGN)) {
			//计算本合约比例  “本合约比例”=“本合约分配”/“目标成本”
			Object goalCostObj = kdtCost.getCell(rowIndex, GOALCOST).getValue();
			if (!FDCHelper.isEmpty(goalCostObj)) {
				Object contractAssignObj = kdtCost.getCell(rowIndex, CONTRACTASSIGN).getValue();
				BigDecimal contractAssign = new BigDecimal(contractAssignObj == null ? "0" : contractAssignObj.toString());
				BigDecimal goalCost = new BigDecimal(goalCostObj.toString());
				if (contractAssign.compareTo(goalCost) > 0) {
					MsgBox.showWarning("本合约分配，不能大于目标成本");
					kdtCost.getCell(rowIndex, CONTRACTASSIGN).setValue(null);
					return;
				}
				if (goalCost.compareTo(FDCHelper.ZERO) != 0) {
					/* modified by zhaoqin for R140425-0059 on 2014/05/09 start */
					//BigDecimal constractScale = contractAssign.divide(goalCost, 4, BigDecimal.ROUND_HALF_UP);
					BigDecimal constractScale = contractAssign.divide(goalCost, 12, BigDecimal.ROUND_HALF_UP);
					constractScale = constractScale.multiply(FDCHelper.ONE_HUNDRED);
					//constractScale = constractScale.divide(FDCHelper.ONE, 2, BigDecimal.ROUND_HALF_UP);
					/* modified by zhaoqin for R140425-0059 on 2014/05/09 end */
					kdtCost.getCell(rowIndex, CONSTRACTSCALE).setValue(constractScale);
				} else {
					kdtCost.getCell(rowIndex, CONSTRACTSCALE).setValue(FDCHelper.ZERO);
				}
			} else {
				kdtCost.getCell(rowIndex, CONSTRACTSCALE).setValue(FDCHelper.ONE_HUNDRED);
			}
			afterContractAssignChange();
		}

		// 选择"成本科目F7"
		if (colIndex == kdtCost.getColumnIndex(COSTACCOUNT)) {
			/*
			 * 1.取出成本科目的值
			 * 
			 * 1.1判断成本科目是否为空
			 * 
			 * 1.1.1为空：置空当前行除工程项目外的所有单元格
			 * 
			 * 1.1.2不为空：取值
			 * 
			 * 1.2判断所选的成本科目是否重复，重复直接返回,不重复继续
			 * 
			 * 2.取出目标成本信息
			 * 
			 * 2.1若为空，把"目标成本","已分配","待分配","本合约分配","备注"置空；
			 * 
			 * 2.2若不为空： 判断目标成本是否是已审批之后状态
			 * 
			 * 2.2.1若不为审批之后状态，把各单元格数值置0，备注项置空 ；
			 * 
			 * 2.2.2若为审批之后状态，取出相应成本科目的目标成本值（需要用到成本科目，目标成本作为条件）
			 * 
			 * 3.算出"已分配","待分配","本合约分配"各值
			 * 
			 * 最后把牵涉到的值：规划金额，经济条款中"付款比例"、"付款金额"等值 更新一遍
			 */
			BigDecimal allAssigned = FDCHelper.ZERO;// "已分配"
			CurProjectInfo project = (CurProjectInfo) kdtCost.getCell(rowIndex,PROJECT).getValue();// 工程项目
			// 1.
			Object newValue = kdtCost.getCell(rowIndex, COSTACCOUNT).getValue();
			// 1.1
			if (newValue == null) {
				// 1.1.1
				ProgrammingContractUtil.clearCell(kdtCost, rowIndex,
						COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST, ASSIGNED,
						ASSIGNING, CONTRACTASSIGN, COST_DES);
			} else {
				// 1.1.2
				//by tim_gao 在这里增加多选
				if(newValue instanceof CostAccountInfo){//单选 by tim_gao
					CostAccountInfo newCostAccountInfo = (CostAccountInfo) newValue;// 成本科目
					CostAccountInfo oldCostAccountInfo = (CostAccountInfo) oldValue;
					if (oldCostAccountInfo != null && newCostAccountInfo != null
							&& oldCostAccountInfo.getLongNumber().equals(newCostAccountInfo.getLongNumber()))
						return;
					kdtCost.getCell(rowIndex, COSTACCOUNT_NUMBER).setValue(
							newCostAccountInfo.getLongNumber().replace('!', '.'));
					// 1.2
					if (isCostAccountDup(newCostAccountInfo, project, rowIndex)) {
						return;
					}
					// 2.
					AimCostInfo aimCostInfo = (AimCostInfo) this.getUIContext().get("aimCostInfo");// 目标成本
					
					// 取当前工程项目对应的目标成本 - modified by zhaoqin on 2013/10/22
					CurProjectInfo oldProject = (CurProjectInfo) this.getUIContext().get("project");
					if(!oldProject.getId().toString().equals(project.getId().toString()))
						aimCostInfo = getLastAimCostByCurProject(project);
					
					if (aimCostInfo == null) {
						// 2.1
						ProgrammingContractUtil.clearCell(kdtCost, rowIndex,
								GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN,COST_DES);
						
						// modified by zhaoqin on 2013/10/28
						kdtCost.getCell(rowIndex, AIMCOST_ID).setValue(null);
					} else {
						// 2.2是否是已审批
						if (!isAimCostAudit(aimCostInfo)) {
							// 2.2.1
							ProgrammingContractUtil.setZero(kdtCost, rowIndex,
									GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN,COST_DES);
							
							// modified by zhaoqin on 2013/10/28
							kdtCost.getCell(rowIndex, AIMCOST_ID).setValue(null);
						} else {
							// 解决合约规划跨期时目标成本问题版本问题
							kdtCost.getCell(rowIndex, AIMCOST_ID).setValue(aimCostInfo.getId().toString());
							// 2.2.2 取出目标成本的值
							// CurProjectInfo project = (CurProjectInfo)
							// kdtCost.getCell(rowIndex, PROJECT).getValue();
							if (project != null) {
								BigDecimal goalCost = ProgrammingContractUtil
										.getGoalCostBy_costAcc_aimCost(newCostAccountInfo, aimCostInfo);
								// 取可拆分非明细科目的汇总目标成本 add by 何鹏
								//当上级为0时再取下级汇总 by hpw
								if (FDCHelper.toBigDecimal(goalCost).compareTo(FDCHelper.ZERO) == 0 && !newCostAccountInfo.isIsLeaf()) {
									goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_totalAimCost(newCostAccountInfo, aimCostInfo);
								}
								if (goalCost.compareTo(FDCHelper.ZERO) == 0) {
									ProgrammingContractUtil.setZero(kdtCost,rowIndex, GOALCOST, ASSIGNED,
											ASSIGNING, CONTRACTASSIGN, COST_DES);
									afterContractAssignChange();
									afterPlanAmountChange();
								} else {
									// ---------- modified by zhaoqin on 2013/11/07 start ----------
									// 已分配比例
									BigDecimal allAssignedScale = getAllContractAssignScale(oldProject, this.editData, 
											newCostAccountInfo);
									// 待分配比例
									BigDecimal assignedScale = FDCHelper.ONE_HUNDRED.subtract(allAssignedScale);
									// allAssigned = getAllContractAssign(newCostAccountInfo, false, aimCostInfo);// 已分配
									allAssigned = allAssignedScale.multiply(goalCost).divide(
											FDCHelper.ONE_HUNDRED, 12, BigDecimal.ROUND_HALF_UP);
									
									// 算出"待分配" == "目标成本" - "已分配"
									BigDecimal assigning = goalCost.subtract(allAssigned);// 待分配
									// 带出"本合约分配"="待分配"
									BigDecimal contractAssign = assigning;// 本合约分配
									// 显示在单元格中
									kdtCost.getCell(rowIndex, GOALCOST).setValue(goalCost);// 目标成本
									kdtCost.getCell(rowIndex, ASSIGNED).setValue(allAssigned);// 已分配
									kdtCost.getCell(rowIndex, ASSIGNING).setValue(assigning);// 待分配
									kdtCost.getCell(rowIndex, CONTRACTASSIGN).setValue(contractAssign);// 本合约分配
									//kdtCost.getCell(rowIndex, CONSTRACTSCALE).setValue(calculateContractSacle(contractAssign, goalCost));//
									kdtCost.getCell(rowIndex, CONSTRACTSCALE).setValue(assignedScale);
									// ---------- modified by zhaoqin on 2013/11/07 end ----------
									
									// 本合约分配带出后又自动算出"规划金额"
									// afterContractAssignChange();
									// 自动算出"规划金额"后，动态改变经济条款中"付款比例"和"付款金额"
									// 默认以"付款比例"为定值，改变"付款金额"
									// afterPlanAmountChange();
								}
							}
						}
					}
				}else if (newValue instanceof Object[]){//多选 by tim_gao
					// 取当前工程项目对应的目标成本 - modified by zhaoqin on 2013/10/22
					AimCostInfo aimCostInfo = (AimCostInfo) this.getUIContext().get("aimCostInfo");// 目标成本
					CurProjectInfo oldProject = (CurProjectInfo) this.getUIContext().get("project");
					if(!oldProject.getId().toString().equals(project.getId().toString()))
						aimCostInfo = getLastAimCostByCurProject(project);
					
					Object[] objr = (Object[]) newValue;
					for (int i = 0; i < objr.length; i++) {
						int tmpRowIndex = rowIndex;
						CostAccountInfo newCostAccountInfo = (CostAccountInfo) objr[i];// 成本科目
						CostAccountInfo oldCostAccountInfo = (CostAccountInfo) oldValue;
						if (i != 0) {
							IRow row = kdtCost.addRow();
							tmpRowIndex = row.getRowIndex();
							row.getCell(COST_ID).setValue(BOSUuid.create("9E6FDD26"));
							row.getCell(PROJECT).setValue(project);
//							projectF7();
							costAccountCellF7(project, tmpRowIndex, kdtCost.getColumnIndex(COSTACCOUNT));
						}
						kdtCost.getCell(tmpRowIndex, COSTACCOUNT).setValue(newCostAccountInfo);

						if (oldCostAccountInfo != null && newCostAccountInfo != null
								&& oldCostAccountInfo.getLongNumber().equals(newCostAccountInfo.getLongNumber()))
							continue;
						kdtCost.getCell(tmpRowIndex, COSTACCOUNT_NUMBER).setValue(
								newCostAccountInfo.getLongNumber().replace('!', '.'));
						// 1.2
						if (isCostAccountDup(newCostAccountInfo, project, tmpRowIndex)) {
							continue;
						}
						// 2.
						// 取当前工程项目对应的目标成本 - modified by zhaoqin on 2013/10/22
						//AimCostInfo aimCostInfo = (AimCostInfo) this.getUIContext().get("aimCostInfo");// 目标成本
						
						if (aimCostInfo == null) {
							// 2.1
							ProgrammingContractUtil.clearCell(kdtCost, tmpRowIndex,
									GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN,COST_DES);
							
							// modified by zhaoqin on 2013/10/28
							kdtCost.getCell(tmpRowIndex, AIMCOST_ID).setValue(null);
						} else {
							// 2.2是否是已审批
							if (!isAimCostAudit(aimCostInfo)) {
								// 2.2.1
								ProgrammingContractUtil.setZero(kdtCost, tmpRowIndex,
										GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN,COST_DES);
								
								// modified by zhaoqin on 2013/10/28
								kdtCost.getCell(tmpRowIndex, AIMCOST_ID).setValue(null);
							} else {
								// modified by zhaoqin on 2013/10/23
								// 解决合约规划跨期时目标成本问题版本问题
								kdtCost.getCell(tmpRowIndex, AIMCOST_ID).setValue(aimCostInfo.getId().toString());
								
								// 2.2.2 取出目标成本的值
								// CurProjectInfo project = (CurProjectInfo)
								// kdtCost.getCell(rowIndex, PROJECT).getValue();
								if (project != null) {
									BigDecimal goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(
											newCostAccountInfo, aimCostInfo);
									// 取可拆分非明细科目的汇总目标成本 add by 何鹏
									//当上级为0时再取下级汇总 by hpw
									if (FDCHelper.toBigDecimal(goalCost).compareTo(FDCHelper.ZERO) == 0 
											&& !newCostAccountInfo.isIsLeaf()) {
										goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_totalAimCost(
												newCostAccountInfo, aimCostInfo);
									}
									if (goalCost.compareTo(FDCHelper.ZERO) == 0) {
										ProgrammingContractUtil.setZero(kdtCost,tmpRowIndex, GOALCOST, ASSIGNED,
												ASSIGNING, CONTRACTASSIGN, COST_DES);
										afterContractAssignChange();
										afterPlanAmountChange();
									} else {
										// ---------- modified by zhaoqin on 2013/11/07 start ----------
										// 已分配比例
										BigDecimal allAssignedScale = getAllContractAssignScale(oldProject, this.editData,
												newCostAccountInfo);
										// 待分配比例
										BigDecimal assignedScale = FDCHelper.ONE_HUNDRED.subtract(allAssignedScale);
										//allAssigned = getAllContractAssign(newCostAccountInfo, false, aimCostInfo);// 已分配
										
										/* modified by zhaoqin for R140425-0059 on 2014/05/09 start */
										//allAssigned = allAssignedScale.multiply(goalCost).divide(
												//FDCHelper.ONE_HUNDRED, 12, BigDecimal.ROUND_HALF_UP);
										//金额只有两位小数
										allAssigned = allAssignedScale.multiply(goalCost).divide(
												FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
										/* modified by zhaoqin for R140425-0059 on 2014/05/09 end */
										
										// 算出"待分配" == "目标成本" - "已分配"
										BigDecimal assigning = goalCost.subtract(allAssigned);// 待分配
										// 带出"本合约分配"="待分配"
										BigDecimal contractAssign = assigning;// 本合约分配
										// 显示在单元格中
										kdtCost.getCell(tmpRowIndex, GOALCOST).setValue(goalCost);// 目标成本
										kdtCost.getCell(tmpRowIndex, ASSIGNED).setValue(allAssigned);// 已分配
										kdtCost.getCell(tmpRowIndex, ASSIGNING).setValue(assigning);// 待分配
										kdtCost.getCell(tmpRowIndex, CONTRACTASSIGN).setValue(contractAssign);// 本合约分配
										//kdtCost.getCell(tmpRowIndex, CONSTRACTSCALE).setValue(
										//calculateContractSacle(contractAssign, goalCost));	//本合约比例
										kdtCost.getCell(tmpRowIndex, CONSTRACTSCALE).setValue(assignedScale);
										// ---------- modified by zhaoqin on 2013/11/07 end ----------
									}

									// 本合约分配带出后又自动算出"规划金额"
									afterContractAssignChange();
									// 自动算出"规划金额"后，动态改变经济条款中"付款比例"和"付款金额"
									// 默认以"付款比例"为定值，改变"付款金额"
									afterPlanAmountChange();
								}
							}
						}
					}
				}

			}
		}
		if (colIndex == kdtCost.getColumnIndex(CONSTRACTSCALE)) {
			// modified by zhaoqin for R131118-0273 on 2013/11/20 start
			Object contractScaleObj = kdtCost.getCell(rowIndex, colIndex).getValue();
			BigDecimal contractScale = FDCHelper.toBigDecimal(contractScaleObj);
			if (contractScale.compareTo(FDCHelper.ONE_HUNDRED) > 0 || contractScale.compareTo(FDCHelper.ZERO) <= 0) {
				MsgBox.showWarning("请输入0-100之间的数字");
				kdtCost.getCell(rowIndex,CONSTRACTSCALE).setValue(null);
				kdtCost.getCell(rowIndex,CONTRACTASSIGN).setValue(null);
				return;
			}

			Object assigningObj = kdtCost.getCell(rowIndex, ASSIGNING).getValue();
			Object contractAssignObj = kdtCost.getCell(rowIndex, GOALCOST).getValue();
			if (assigningObj != null && contractAssignObj != null) {
				BigDecimal assigning = FDCHelper.toBigDecimal(assigningObj.toString());
				
				/* modified by zhaoqin for R140425-0059 on 2014/05/09 start */
				//BigDecimal contractAssign = FDCHelper.multiply(contractAssignObj, 
						//contractScale).divide(new BigDecimal("100"), 12, BigDecimal.ROUND_HALF_UP);
				BigDecimal contractAssign = FDCHelper.multiply(contractAssignObj, contractScale).divide(
						new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
				/* modified by zhaoqin for R140425-0059 on 2014/05/09 start */
				
				if (assigning.compareTo(contractAssign) < 0) {
					FDCMsgBox.showInfo("本合约比例不能超过待分配/目标成本");
					kdtCost.getCell(rowIndex, colIndex).setValue(e.getOldValue());
					SysUtil.abort();
				}
				kdtCost.getCell(rowIndex,CONTRACTASSIGN).setValue(contractAssign);
			}
			
			/*
			Object contractScaleObj = kdtCost.getCell(rowIndex, colIndex).getValue();
			Object assigningObj = kdtCost.getCell(rowIndex, ASSIGNING).getValue();
			Object contractAssignObj = kdtCost.getCell(rowIndex, GOALCOST).getValue();
			if (assigningObj != null && contractAssignObj != null) {
				BigDecimal assigning = FDCHelper.toBigDecimal(assigningObj.toString());
				BigDecimal decimal = FDCHelper.toBigDecimal(contractAssignObj).multiply(FDCHelper.toBigDecimal(contractScaleObj));
				BigDecimal contractAssign = decimal.divide(new BigDecimal("100"), 12, BigDecimal.ROUND_HALF_UP);
				if (assigning.compareTo(contractAssign) < 0) {
					FDCMsgBox.showInfo("本合约比例不能超过待分配/目标成本");
					
					kdtCost.getCell(rowIndex, colIndex).setValue(e.getOldValue());
					SysUtil.abort();
				}
			}
			
			if (!FDCHelper.isEmpty(contractScaleObj)) {
				String contractScaleStr = contractScaleObj.toString();
				BigDecimal contractScaleBig = new BigDecimal(contractScaleStr);
				if (contractScaleBig.compareTo(FDCHelper.ONE_HUNDRED) <= 0 && contractScaleBig.compareTo(FDCHelper.ZERO) > 0) {
					// 绘制付款比例显示郊果
					ObjectValueRender render_scale = new ObjectValueRender();
					render_scale.setFormat(new IDataFormat() {
						public String format(Object o) {
							String str = o.toString();
							if (!FDCHelper.isEmpty(str)) {
								return str + "%";
							}
							return str;
						}
					});
					kdtCost.getColumn(CONSTRACTSCALE).setRenderer(render_scale);

				} else {
					MsgBox.showWarning("请输入1-100之间的数字");
					return;
				}
			}
			//计算 “本合约分配”=“目标成本”*“本合约比例”
			if (!FDCHelper.isEmpty(contractScaleObj)) {
				 Object goalCost = kdtCost.getCell(rowIndex,GOALCOST).getValue();
				 BigDecimal bdContractScale=new BigDecimal(contractScaleObj.toString());
				 BigDecimal bdGoalCost=new BigDecimal(goalCost==null?"0":goalCost.toString());
				 kdtCost.getCell(rowIndex,CONTRACTASSIGN).setValue(bdGoalCost.multiply(bdContractScale).divide(new BigDecimal("100"), 12,BigDecimal.ROUND_HALF_UP));
			}
			*/
			// modified by zhaoqin for R131118-0273 on 2013/11/20 end
			afterContractAssignChange();
		}
		
		if(txtAmount.getBigDecimalValue()!=null && txtAmount.getBigDecimalValue().compareTo(payPlanNewUI.planAmount)!=0){
			payPlanNewUI.updateAmount(txtAmount.getBigDecimalValue());
		}
	}

	/**
	 * "本合约分配"值改变后，汇总规划金额
	 */
	private void afterContractAssignChange() {
		BigDecimal allContractAssign = FDCHelper.ZERO;
		for (int i = 0; i < kdtCost.getRowCount(); i++) {
			Object contractAssignObj = kdtCost.getCell(i, CONTRACTASSIGN).getValue();// 本合约分配
			if (contractAssignObj == null) {
				allContractAssign = allContractAssign.add(FDCHelper.ZERO);
			} else {
				allContractAssign = allContractAssign.add(new BigDecimal(contractAssignObj.toString()));
			}
		}
		txtAmount.setValue(allContractAssign);
	}

	/**
	 * "规划金额"值改变后，动态改变经济条款中付款金额的值
	 */
	private void afterPlanAmountChange() {
		BigDecimal planAmount = (BigDecimal) txtAmount.getValue();
		for (int i = 0; i < kdtEconomy.getRowCount(); i++) {
			Object scaleObj = kdtEconomy.getCell(i, SCALE).getValue();
			if (scaleObj != null) {
				BigDecimal scale = new BigDecimal(scaleObj.toString());
				BigDecimal hundren = FDCHelper.ONE_HUNDRED;
				scale = FDCHelper.divide(scale, hundren);
				BigDecimal amount = planAmount.multiply(scale);
				kdtEconomy.getCell(i, AMOUNT).setValue(amount);
			}
		}
	}

	/**
	 * 判断在一个合约之内相同工程项目下成本科目是否重复
	 * 
	 * @param rowIndex
	 */
	private boolean isCostAccountDup(CostAccountInfo currentInfo, CurProjectInfo project, int rowIndex) {
		ICell costAccountNameCell = kdtCost.getCell(rowIndex, COSTACCOUNT);
		ICell costAccountNumberCell = kdtCost.getCell(rowIndex, COSTACCOUNT_NUMBER);
		if (FDCHelper.isEmpty(currentInfo)) {
			return false;
		}
		int rowCount = kdtCost.getRowCount();
		if (!FDCHelper.isEmpty(currentInfo.getName())) {
			//int flag = 0;
			for (int i = 0; i < rowCount && rowIndex != i; i++) {
				CostAccountInfo forInfo = (CostAccountInfo) kdtCost.getCell(i, COSTACCOUNT).getValue();
				CurProjectInfo forProjectInfo = (CurProjectInfo) kdtCost.getCell(i, PROJECT).getValue();
				if (forInfo == null) {
					break;
				}
				if (!FDCHelper.isEmpty(forInfo.getName())) {
					/* modified by zhaoqin for R140512-0296 on 2014/05/14 start */
					if(project.getLongNumber().equals(forProjectInfo.getLongNumber()) &&
							currentInfo.getLongNumber().equals(forInfo.getLongNumber())) {
					//if (currentInfo.getName().equals(forInfo.getName())
						//	&& project.getName().equals(forProjectInfo.getName())) {
						//flag++;
						//if (flag >= 2) {
						/* modified by zhaoqin for R140512-0296 on 2014/05/14 end */
							FDCMsgBox.showInfo("本框架合约成本构成内已经有\""
									+ currentInfo.getName()
									+ "\"成本科目，不能再继续添加此成本科目了！");
							costAccountNameCell.setValue(null);
							costAccountNumberCell.setValue(null);
							return true;
						//}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 算出框架内相同成本科目的"本合约分配"金额之和
	 * 
	 * @param caInfo
	 * 
	 * @param flag
	 * 
	 *            true表示求出所有成本构成中"本合约分配"之和
	 * 
	 *            false 表示求出除本合约之外所有成本构成中"本合约分配"之和
	 * @return
	 * @throws BOSException 
	 */
	private BigDecimal getAllContractAssign(CostAccountInfo caInfo, boolean flag, AimCostInfo aimCostInfo) throws BOSException {
		BigDecimal allContractAssign = FDCHelper.ZERO;

		//		EntityViewInfo view = new EntityViewInfo();
		//		FilterInfo filter = new FilterInfo();
		//		filter.getFilterItems().add(new FilterItemInfo("costAccount.id", caInfo.getId().toString()));
		//		//		filter.getFilterItems().add(new FilterItemInfo("contract.id", editData.getId().toString(), CompareType.NOTEQUALS));
		//		filter.getFilterItems().add(new FilterItemInfo("contract.programming.isLatest", Boolean.TRUE));
		//		if (getUIContext().get("project") != null) {
		//			filter.getFilterItems().add(
		//					new FilterItemInfo("contract.programming.project.id", ((CurProjectInfo) getUIContext().get("project")).getId()
		//							.toString(),
		//							CompareType.NOTEQUALS));
		//		}
		//		SelectorItemCollection sic = new SelectorItemCollection();
		//		sic.add("id");
		//		sic.add("contractAssign");
		//		view.setFilter(filter);
		//		view.setSelector(sic);
		//		ProgrammingContracCostCollection costCollection = ProgrammingContracCostFactory.getRemoteInstance()
		//				.getProgrammingContracCostCollection(view);
		//		if (costCollection != null && costCollection.size() > 0) {
		//			for (int i = 0; i < costCollection.size(); i++) {
		//				allContractAssign = allContractAssign.add(FDCHelper.toBigDecimal(costCollection.get(i).getContractAssign()));
		//			}
		//		}

		FDCSQLBuilder builder = new FDCSQLBuilder();

		builder.appendSql("select cost.fcontractAssign as amount,cost.fid,cost.fcostAccountid,pro.fversion,pro.fprojectid ");
		builder.appendSql("from T_CON_ProgrammingContracCost as cost  ");
		builder.appendSql("left join T_CON_ProgrammingContract as con on con.fid= cost.FContractID ");
		builder.appendSql("left join T_CON_Programming as pro on pro.fid = con.FProgrammingID ");
		builder.appendSql("inner join ( ");
		builder.appendSql("select max(pro.fversion) as fver,pro.fprojectid as proid from T_CON_ProgrammingContracCost as cost  ");
		builder.appendSql("left join T_CON_ProgrammingContract as con on con.fid= cost.FContractID  ");
		builder.appendSql("left join T_CON_Programming as pro on pro.fid = con.FProgrammingID  ");
		builder.appendSql("where cost.fcostAccountid='" + caInfo.getId().toString() + "' ");
		if (getUIContext().get("project") != null) {
			builder.appendSql("and pro.fprojectid<> '" + ((CurProjectInfo) getUIContext().get("project")).getId().toString() + "' ");
		}
		builder.appendSql("group by pro.fprojectid ");

		builder.appendSql(") as b on b.fver=pro.fversion and b.proid = pro.fprojectid ");

		builder.appendSql("where cost.fcostAccountid='" + caInfo.getId().toString() + "' ");
		if (getUIContext().get("project") != null) {
			builder.appendSql("and pro.fprojectid<> '" + ((CurProjectInfo) getUIContext().get("project")).getId().toString() + "' ");
		}

		IRowSet rowSet = builder.executeQuery();
		try {
			while (rowSet.next()) {
				allContractAssign = allContractAssign.add(FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount")));
			}
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		

		/*
		 * 当选择工程项目不是当前项目时，要判断此成本科目在别的项目是否已分配 add by zkyan 
		 * 
		 * 另外此时不同项目成本科目编码可能相同，故将长编码判断改为id判断
		 */

		for (int i = 0; i < pcCollection.size(); i++) {
			ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
			if (flag) {
				ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
				for (int j = 0; j < costEntries.size(); j++) {
					ProgrammingContracCostInfo pccInfo = costEntries.get(j);
					CostAccountInfo costAccountInfo = pccInfo.getCostAccount();
					if (costAccountInfo != null) {

						//add by zkayn LongNumber 改为 id
						if (costAccountInfo.getId() != null) {
							if (costAccountInfo.getId().toString().equals(caInfo.getId().toString())) {
								BigDecimal contractAssign = pccInfo.getContractAssign();
								if (contractAssign == null) {
									contractAssign = FDCHelper.ZERO;
								}
								allContractAssign = allContractAssign.add(contractAssign);
							}
						}
					}
				}
			} else {
				if (!programmingContractInfo.getId().toString().equals(editData.getId().toString())) {
					ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
					for (int j = 0; j < costEntries.size(); j++) {
						ProgrammingContracCostInfo pccInfo = costEntries.get(j);
						CostAccountInfo costAccountInfo = pccInfo.getCostAccount();

						//add by zkayn LongNumber 改为 id
						if (costAccountInfo != null) {
							if (costAccountInfo.getId() != null) {
								if (costAccountInfo.getId().toString().equals(caInfo.getId().toString())) {
									BigDecimal contractAssign = pccInfo.getContractAssign();
									if (contractAssign == null) {
										contractAssign = FDCHelper.ZERO;
									}
									allContractAssign = allContractAssign.add(contractAssign);
								}
							}
						}
					}
				}
			}
		}

		return allContractAssign;
	}

	/**
	 * 经济条款分录单击事件
	 */
	protected void kdtEconomy_tableClicked(KDTMouseEvent e) throws Exception {
		// 选中行则“删除行”按钮可用
		if (e.getRowIndex() > -1 && !oprtState.equals(OprtState.VIEW)
				&& e.getType() != KDTStyleConstants.HEAD_ROW) {
			btnRemoveLines_economy.setEnabled(true);
		}
	}

	/**
	 * 经济条款分录数据编辑处理
	 */
	protected void kdtEconomy_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		Object oldValue = e.getOldValue();
		/* 选择付款类型 */
		// if (colIndex == kdtEconomy.getColumnIndex(PAYMENTTYPE)) {
		// isEnonomDup();
		// }

		/* 编辑付款比例 */
		if (colIndex == kdtEconomy.getColumnIndex(SCALE)) {
			ICell scaleCell = kdtEconomy.getCell(rowIndex, SCALE);
			ICell amountCell = kdtEconomy.getCell(rowIndex, AMOUNT);
			if (scaleCell.getValue() != null && oldValue != null) {
				BigDecimal newScale = FDCHelper.toBigDecimal(scaleCell.getValue().toString());
				BigDecimal oldScale = FDCHelper.toBigDecimal(oldValue.toString());
				if ((newScale).compareTo(oldScale) == 0) {
					return;
				}
			}
			// 绘制付款比例显示 郊果
			ObjectValueRender render_scale = new ObjectValueRender();
			render_scale.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str + "%";
					}
					return str;
				}
			});
			kdtEconomy.getColumn(SCALE).setRenderer(render_scale);

			// 绘制付款金额显示 郊果
			ObjectValueRender render_amount = new ObjectValueRender();
			render_amount.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str;
					}
					return str;
				}
			});
			kdtEconomy.getColumn(AMOUNT).setRenderer(render_amount);

			String scale = (String) scaleCell.getValue();
			if (!FDCHelper.isEmpty(scale)) {
				// Object newScaleValue = e.getValue();
				Object oldScaleValue = e.getOldValue();
				Object oldAmountValue = kdtEconomy.getCell(rowIndex, AMOUNT).getValue();
				// editStopedCheckIsChange(oldScaleValue, newScaleValue);
				if (scale.matches("^\\d*$") 
						&& new BigDecimal(scale).compareTo(new BigDecimal("0")) > 0
						&& new BigDecimal("101").compareTo(new BigDecimal(scale)) > 0) {
					// 填写付款比例后自动计算出付款金额
					BigDecimal planAmount = (BigDecimal) txtAmount.getValue();
					if (!FDCHelper.isEmpty(planAmount)) {
						BigDecimal scaleBig = new BigDecimal(scale);
						BigDecimal hundrenBig = FDCHelper.ONE_HUNDRED;
						scaleBig = FDCHelper.divide(scaleBig, hundrenBig);
						BigDecimal amount = planAmount.multiply(scaleBig);
						amountCell.setValue(amount.toString());
					}
				} else {
					scaleCell.setValue(null);
					// amountCell.setValue(null);
					FDCMsgBox.showInfo("请录入1~100的数字！");
				}

				// 判断
				if (getPayScaleAll().compareTo(new BigDecimal(100)) > 0) {
					FDCMsgBox.showInfo("付款比例累计不得超过100%");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				} else if (getPayAmountAll().compareTo(
						txtAmount.getValue() == null ? new BigDecimal(0)
								: new BigDecimal(txtAmount.getValue().toString())) > 0) {
					FDCMsgBox.showInfo("付款金额总和不能超出规划金额");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				}
			} else {
				amountCell.setValue(null);
			}
		}

		/* 编辑付款金额 */
		if (colIndex == kdtEconomy.getColumnIndex(AMOUNT)) {
			ICell scaleCell = kdtEconomy.getCell(rowIndex, SCALE);
			ICell amountCell = kdtEconomy.getCell(rowIndex, AMOUNT);
			if (amountCell.getValue() != null && oldValue != null) {
				BigDecimal newAmount = FDCHelper.toBigDecimal(amountCell.getValue().toString());
				BigDecimal oldAmount = FDCHelper.toBigDecimal(oldValue.toString());
				if ((newAmount).compareTo(oldAmount) == 0) {
					return;
				}
			}
			String amountStr = (String) amountCell.getValue();
			// 绘制付款比例显示 郊果
			ObjectValueRender render_scale = new ObjectValueRender();
			render_scale.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str + "%";
					}
					return str;
				}
			});
			kdtEconomy.getColumn(SCALE).setRenderer(render_scale);

			// 绘制付款金额 显示郊果
			ObjectValueRender render_amount = new ObjectValueRender();
			render_amount.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str;
					}
					return str;
				}
			});
			kdtEconomy.getColumn(AMOUNT).setRenderer(render_amount);

			if (!FDCHelper.isEmpty(amountStr)) {
				// Object newAmountValue = e.getValue();
				Object oldAmountValue = e.getOldValue();
				Object oldScaleValue = kdtEconomy.getCell(rowIndex, SCALE).getValue();
				// editStopedCheckIsChange(oldAmountValue, newAmountValue);

				if (amountStr.matches("^\\d*$")) {
					// 填写付款金额后自动计算出付款比例
					BigDecimal amount = new BigDecimal(amountStr);
					BigDecimal hundrenBig = FDCHelper.ONE_HUNDRED;
					amount = amount.multiply(hundrenBig);
					BigDecimal planAmount = (BigDecimal) txtAmount.getValue();
					if (!FDCHelper.isEmpty(planAmount)) {
						BigDecimal scale = FDCHelper.divide(amount, planAmount, 10, BigDecimal.ROUND_HALF_UP);
						scaleCell.setValue(scale);
					}
				} else {
					// scaleCell.setValue(null);
					amountCell.setValue(null);
					FDCMsgBox.showInfo("请录入数字！");
				}

				// 判断
				if (getPayScaleAll().compareTo(FDCHelper.ONE_HUNDRED) > 0) {
					FDCMsgBox.showInfo("您所填写的付款金额过大，付款比例已超出100%");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				} else if (getPayAmountAll().compareTo(
						txtAmount.getValue() == null ? new BigDecimal(0)
								: new BigDecimal(txtAmount.getValue().toString())) > 0) {
					FDCMsgBox.showInfo("付款金额总和不能超出规划金额");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				}
			} else {
				scaleCell.setValue(null);
			}

		}

		//added by shangjing 相关任务项
		if (colIndex == kdtEconomy.getColumnIndex("scheduletask")) {
			ICell taskCell = kdtEconomy.getCell(rowIndex, "scheduletask");
			FDCScheduleTaskInfo info = null;
			if (taskCell.getValue() != null) {
				info = (FDCScheduleTaskInfo) taskCell.getValue();
			}
			if (info != null && info.isIsLeaf()) {

				ICell endCell = kdtEconomy.getCell(rowIndex, "completedate");
				endCell.setValue(info.getEnd());

				ICell delaycell = kdtEconomy.getCell(rowIndex, "delaydays");
				int delaydays = 0;
				if (delaycell.getValue() != null && !"".equals(delaycell.getValue())) {
					delaydays = new Integer(delaycell.getValue().toString()).intValue();
				}

				// if (delayStr != null && !"".equals(delayStr)) {
				// if (delayStr.matches("^-?\\d*$")) {
				// delaydays = Integer.parseInt(delayStr);
				// } else {
				// MsgBox.showInfo("请输入整数!");
				// ((KDTextField)kdtEconomy.getColumn("delaydays").getEditor()).requestFocus();
				// }
				// }

				//计算日期  完成日期+延迟天数=付款日期
				Date endDate = (Date) endCell.getValue();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR)) + delaydays);
				Date completeDate = calendar.getTime();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				kdtEconomy.getCell(rowIndex, "paymentDate").setValue(sdf.format(completeDate));
				kdtEconomy.getCell(rowIndex, "delaydays").getStyleAttributes().setLocked(false);
				kdtEconomy.getCell(rowIndex, "delaydays").getStyleAttributes().setBackground(Color.WHITE);

			} else {
				kdtEconomy.getCell(rowIndex, "scheduletask").setValue(null);
				kdtEconomy.getCell(rowIndex, "paymentDate").setValue(null);
				kdtEconomy.getCell(rowIndex, "completedate").setValue(null);
				kdtEconomy.getCell(rowIndex, "delaydays").setValue(null);
				kdtEconomy.getCell(rowIndex, "delaydays").getStyleAttributes().setBackground(Color.lightGray);
				kdtEconomy.getCell(rowIndex, "delaydays").getStyleAttributes().setLocked(true);
			}
		}

		//added by shangjing 延迟日期
		if (colIndex == kdtEconomy.getColumnIndex("delaydays")) {
			ICell delayCell = kdtEconomy.getCell(rowIndex, "delaydays");
			if (delayCell.getValue() != null) {

				ICell endCell = kdtEconomy.getCell(rowIndex, "completedate");
				Date endDate = (Date) endCell.getValue();

				ICell delaycell = kdtEconomy.getCell(rowIndex, "delaydays");

				int delaydays = 0;
				if (delaycell.getValue() != null && !"".equals(delaycell.getValue())) {
					delaydays = new Integer(delaycell.getValue().toString()).intValue();
				}
				// int delaydays = 0;
				// if (delayStr != null && !"".equals(delayStr)) {
				// if (delayStr.matches("^-?\\d*$")) {
				// delaydays = Integer.parseInt(delayStr);
				// } else {
				// MsgBox.showInfo("请输入整数!");
				// }
				// }

				//计算日期  完成日期+延迟天数=付款日期
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR)) + delaydays);
				Date completeDate = calendar.getTime();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				kdtEconomy.getCell(rowIndex, "paymentDate").setValue(sdf.format(completeDate));
				kdtEconomy.getCell(rowIndex, "delaydays").getStyleAttributes().setLocked(false);
				kdtEconomy.getCell(rowIndex, "delaydays").getStyleAttributes().setBackground(Color.WHITE);

			}
		}

	}

	/**
	 * 判断目标成本是否已审批
	 * 
	 * @return true:审批中之后状态; false:未审批
	 */
	private boolean isAimCostAudit(AimCostInfo aimCostInfo) {
		if (aimCostInfo != null) {
			FDCBillStateEnum state = aimCostInfo.getState();
			if (state.equals(FDCBillStateEnum.SAVED)
					|| state.equals(FDCBillStateEnum.SUBMITTED)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断单元格的值在编辑前后是否改变
	 * 
	 * @param oldValue
	 * @param newValue
	 */
	private void editStopedCheckIsChange(Object oldValue, Object newValue) {
		if (newValue != null && oldValue != null) {
			if (new BigDecimal(newValue.toString()).compareTo(new BigDecimal(oldValue.toString())) == 0) {
				SysUtil.abort();
			}
		}
	}

	/**
	 * 付款比例总和
	 * 
	 * @return
	 */
	private BigDecimal getPayScaleAll() {
		int rowCount = kdtEconomy.getRowCount();
		BigDecimal scale = new BigDecimal(0);
		for (int i = 0; i < rowCount; i++) {
			Object scaleObj = kdtEconomy.getCell(i, SCALE).getValue();
			if (!FDCHelper.isEmpty(scaleObj)) {
				BigDecimal temp = new BigDecimal(scaleObj.toString());
				scale = scale.add(temp);
			}
		}
		return scale;
	}

	/**
	 * 付款金额总和
	 * 
	 * @return
	 */
	private BigDecimal getPayAmountAll() {
		int rowCount = kdtEconomy.getRowCount();
		BigDecimal amount = new BigDecimal(0);
		for (int i = 0; i < rowCount; i++) {
			Object amountObj = kdtEconomy.getCell(i, AMOUNT).getValue();
			if (!FDCHelper.isEmpty(amountObj)) {
				BigDecimal temp = new BigDecimal(amountObj.toString());
				amount = amount.add(temp);
			}
		}
		return amount;
	}

	/**
	 * 付款金额或付款比例单元格值删除后处理情况
	 */
	// protected void kdtEconomy_activeCellChanged(KDTActiveCellEvent e) throws
	// Exception {
	// if (e.getPrevColumnIndex() == kdtEconomy.getColumnIndex(SCALE)) {
	// if (kdtEconomy.getCell(e.getPrevRowIndex(),
	// e.getPrevColumnIndex()).getValue() == null) {
	// kdtEconomy.getCell(e.getPrevRowIndex(), AMOUNT).setValue(null);
	// }
	// }
	// if (e.getPrevColumnIndex() == kdtEconomy.getColumnIndex(AMOUNT)) {
	// if (kdtEconomy.getCell(e.getPrevRowIndex(),
	// e.getPrevColumnIndex()).getValue() == null) {
	// kdtEconomy.getCell(e.getPrevRowIndex(), SCALE).setValue(null);
	// }
	// }
	// }

	/**
	 * 判断付款类型是否重复
	 * 
	 * @param enonomy_rowCount
	 */
	private void isEnonomDup() {
		int rowIndex = kdtEconomy.getSelectManager().getActiveRowIndex();
		ICell economyCell = kdtEconomy.getCell(rowIndex, PAYMENTTYPE);
		PaymentTypeInfo currentInfo = (PaymentTypeInfo) economyCell.getValue();
		if (FDCHelper.isEmpty(currentInfo)) {
			return;
		}
		int columnCount = kdtEconomy.getRowCount();
		if (!FDCHelper.isEmpty(currentInfo.getName())) {
			int flag = 0;
			for (int i = 0; i < columnCount; i++) {
				PaymentTypeInfo forInfo = (PaymentTypeInfo) kdtEconomy.getCell(i, PAYMENTTYPE).getValue();
				if (forInfo == null) {
					break;
				}
				if (!FDCHelper.isEmpty(forInfo.getName())) {
					if (currentInfo.getName().equals(forInfo.getName())) {
						flag++;
						if (flag >= 2) {
							FDCMsgBox.showInfo("本框架合约经济条款内已经有\""
									+ currentInfo.getName()
									+ "\"付款类型，不能再继续添加此付款类型了！");
							economyCell.setValue(null);
							SysUtil.abort();
						}
					}
				}
			}
		}
	}

	private void preparePCData() {
		// 单据头信息
		//yuxue
		if (editData.getParent() != null) {
			String longName = editData.getDisplayName();
			if (longName.lastIndexOf('.') > -1) {
				//因为'.'不存在时，会返回-1，这样subString()会抛出IndexOutOfBoundsException，所以加上判断
				String headName = longName.substring(0, longName.lastIndexOf('.'));
				this.txtParentLongName.setText(headName);// 上级框架合约长名称
			}
		}
		txtNumber.setText(editData.getLongNumber());// 框架合约长编码
		txtName.setText(editData.getName() == null ? null : editData.getName().trim());// 名称
		txtAmount.setValue(editData.getAmount(), false);// 规划金额
		txtControlAmount.setValue(editData.getControlAmount(), false);// 采购控制金额   
		txtReservedChangeRate.setValue(editData.getReservedChangeRate(), false);// 采购控制金额   
//		kdcInviteWay.setSelectedItem(editData.getInviteWay());// 招标方式
//		if (this.oprtState.equals(OprtState.VIEW)) {
//			if (editData.getInviteOrg() != null) {
//				CostCenterOrgUnitInfo orgUnitInfo = null;
//				try {
//
//					orgUnitInfo = CostCenterOrgUnitFactory.getRemoteInstance().getCostCenterOrgUnitInfo(
//							new ObjectUuidPK(editData.getInviteOrg().getId()));
//				} catch (EASBizException e) {
//					handUIExceptionAndAbort(e);
//				} catch (BOSException e) {
//					handUIExceptionAndAbort(e);
//				}
//				prmtInviteOrg.setData(orgUnitInfo);
//			}
//		} else {
//			prmtInviteOrg.setData(editData.getInviteOrg());// 招标组织
//		}

		if (editData.getWorkContent() == null || editData.getWorkContent().trim().length() == 0) {
			this.txtWorkContent.setText(DEFAULT_WORK_CONTENT);// 工作内容
			txtWorkContent.setCustomForegroundColor(Color.GRAY);
		} else {
			this.txtWorkContent.setText(editData.getWorkContent().trim());// 工作内容
			txtWorkContent.setCustomForegroundColor(Color.BLACK);
		}
		txtContractContUI.setText(editData.getContractContUI());
		txtAttachWork.setText(editData.getAttachWork());
		txtAttContract.setText(editData.getAttContract());
//		txtSupMaterial.setText(editData.getSupMaterial());// 甲供及甲指材设
//		txtDescription.setText(editData.getDescription());// 备注
//		txtDescription.setToolTipText(editData.getDescription());
		txtAttachment.setText(getAllAttachmentName(editData.getId().toString()).toString());// 附件
		processAttachments(editData.getId().toString());
//		ObjectValueRender render_scale = new ObjectValueRender();
//		render_scale.setFormat(new IDataFormat() {
//			public String format(Object o) {
//				String str = o.toString();
//				if (!FDCHelper.isEmpty(str)) {
//					return str + "%";
//				}
//				return str;
//			}
//		});
//		kdtEconomy.getColumn(SCALE).setRenderer(render_scale);

		// 分录信息
		addCostLine(kdtCost);
//		addEconomyLine(kdtEconomy);

		int level = editData.getLevel();
		if (level > 1) {
			String longNumber = editData.getLongNumber();
			if (!FDCHelper.isEmpty(longNumber)) {
				LimitedTextDocument document = new LimitedTextDocument(longNumber);
				String text = txtNumber.getText();
				txtNumber.setDocument(document);
				document.setIsAutoUpdate(true);
				document.setIsOnload(true);
				txtNumber.setText(text);
				document.setIsAutoUpdate(false);
				document.setIsOnload(false);
			}
		}
		if (level == 1) {
			LimitedTextDocument document = new LimitedTextDocument("");
			String text = txtNumber.getText();
			txtNumber.setDocument(document);
			document.setIsAutoUpdate(true);
			document.setIsOnload(true);
			txtNumber.setText(text);
			document.setIsAutoUpdate(false);
			document.setIsOnload(false);
		}
		
		// /////////////////////////////////////////////////////////////////////
		// start，厦门建发，合约规划的详细内容及列表界面加入4个字段“发包方式”、“招投标方式”、“预计发包开始时间”、“预计发包结束时间”等四个字段
		// by skyiter_wang 20131009
		// /////////////////////////////////////////////////////////////////////
//
//		try {
//			if (null != editData.getInviteMode()) {
//				IObjectPK inviteModePk = new ObjectUuidPK(editData.getInviteMode().getId());
//				InviteModeInfo inviteModeInfo = InviteModeFactory.getRemoteInstance().getInviteModeInfo(inviteModePk);
//
//				if (null != inviteModeInfo) {
//					editData.setInviteMode(inviteModeInfo);
//				}
//			}
//			if (null != editData.getJobType()) {
//				IObjectPK jobTypePk = new ObjectUuidPK(editData.getJobType().getId());
//				JobTypeInfo jobTypeInfo = JobTypeFactory.getRemoteInstance().getJobTypeInfo(jobTypePk);
//
//				if (null != jobTypeInfo) {
//					editData.setJobType(jobTypeInfo);
//				}
//			}
//		} catch (Exception e) {
//			logger.equals(e);
//			this.handUIExceptionAndAbort(e);
//		}

//		pkEstimateAwardStartDate.setValue(editData.getEstimateAwardStartDate());
//		pkEstimateAwardEndDate.setValue(editData.getEstimateAwardEndDate());
//		prmtInviteMode.setData(editData.getInviteMode());
//		prmtJobType.setData(editData.getJobType());

		// /////////////////////////////////////////////////////////////////////
		// end
		// /////////////////////////////////////////////////////////////////////
	}

	/**
	 * 初始化框架合约成本构成分录数据
	 * 
	 * @param table
	 */
	private void addCostLine(KDTable table) {
//		projectF7();
		if (editData.getCostEntries().size() > 0) {
			IRow row;
			List cachList = new ArrayList();
			for (int i = 0; i < editData.getCostEntries().size(); i++) {
				ProgrammingContracCostInfo pccInfo = editData.getCostEntries().get(i);
				if (cachList.contains(pccInfo.getCostAccount())) {
					continue;
				} else {
					cachList.add(pccInfo.getCostAccount());
				}
				row = table.addRow();
				if (pccInfo.getId() == null) {
					pccInfo.setId(BOSUuid.create(pccInfo.getBOSType()));
				}
				row.getCell(COST_ID).setValue(pccInfo.getId());
				CurProjectInfo project = null;
				if (pccInfo.getCostAccount() != null) {
					project = pccInfo.getCostAccount().getCurProject();
				}
				row.getCell(PROJECT).setValue(project);
				costAccountCellF7(project, i, kdtCost.getColumnIndex(COSTACCOUNT));// 根据当前行工程项目加载F7成本科目
				if(pccInfo.getCostAccount()!=null){
					row.getCell(COSTACCOUNT_NUMBER).setValue(pccInfo.getCostAccount().getLongNumber().replace('!', '.'));
				}
				row.getCell(COSTACCOUNT).setValue(pccInfo.getCostAccount());
				row.getCell(GOALCOST).setValue(pccInfo.getGoalCost());
				row.getCell(ASSIGNED).setValue(pccInfo.getAssigned());
				row.getCell(ASSIGNING).setValue(pccInfo.getAssigning());
				//by tim_gao 负数判断
				ICell cell = row.getCell(ASSIGNING);
				Object temp = cell.getValue();// 本合约分配
				if (FDCHelper.compareTo(temp, FDCHelper.ZERO) < 0) {
					cell.getStyleAttributes().setFontColor(Color.GREEN);
				}
				row.getCell(CONTRACTASSIGN).setValue(pccInfo.getContractAssign());
				row.getCell(COST_DES).setValue(pccInfo.getDescription());
				row.getCell(CONSTRACTSCALE).setValue(pccInfo.getContractScale());
				
				// modified by zhaoqin on 2013/11/07
				row.getCell(AIMCOST_ID).setValue(pccInfo.getAimCostId());
			}
		}
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new IDataFormat() {
			public String format(Object o) {
				String str = o.toString();
				if (!FDCHelper.isEmpty(str)) {
					return str + "%";
				}
				return str;
			}
		});
		// 绘本合约比例显示郊果
		kdtCost.getColumn(CONSTRACTSCALE).setRenderer(render);
	}

	/**
	 * added by shangjing
	 * 检查并及时更新经济条款相应信息
	 * @param infos 经济条款
	 * @return 更新后的经济条款
	 */
	private ProgrammingContractEconomyCollection checkAndUpdateEconomy(ProgrammingContractEconomyCollection infos) {
		Iterator iterator = infos.iterator();
		ProgrammingContractEconomyInfo economyInfo = null;

		ProgrammingContractEconomyCollection newEconomyInfos = new ProgrammingContractEconomyCollection();
		Date end = null;

		Calendar calendar = Calendar.getInstance();

		while (iterator.hasNext()) {
			economyInfo = (ProgrammingContractEconomyInfo) iterator.next();
			FDCScheduleTaskInfo task = null;
			if (economyInfo.getScheduletask() != null) {
				try {
					task = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(
							new ObjectUuidPK(economyInfo.getScheduletask().getId()));
				} catch (EASBizException e) {
					handUIExceptionAndAbort(e);
				} catch (BOSException e) {
					handUIExceptionAndAbort(e);
				}
			}
			//得到相关任务项及其完成日期 并重新计算经济条款付款日期
			if (task != null && task.getEnd() != null
					&& economyInfo.getCompletedate() != null
					&& economyInfo.getCompletedate() != end) {
				end = task.getEnd();
				economyInfo.setCompletedate(end);
				calendar.setTime(economyInfo.getCompletedate());
				calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR)) + economyInfo.getDelaydays());
				economyInfo.setPaymentDate(new Timestamp(calendar.getTimeInMillis()));
				try {
					ProgrammingContractEconomyFactory.getRemoteInstance()
							.update(new ObjectUuidPK(economyInfo.getId()), economyInfo);
				} catch (EASBizException e) {
					handUIExceptionAndAbort(e);
				} catch (BOSException e) {
					handUIExceptionAndAbort(e);
				}
			}
			newEconomyInfos.add(economyInfo);
		}

		return newEconomyInfos;

	}

	/**
	 * 初始化框架合约经济条款分录数据
	 * 
	 * @param table
	 */
	private void addEconomyLine(KDTable table) {
		ProgrammingContractEconomyCollection pcEnonomyCollection = editData.getEconomyEntries();
		//added by shangjing  检查其经济条款是否中相关任务项的完成时间是否更新了，并更新经济条款的完成时间和付款时间 
		pcEnonomyCollection = checkAndUpdateEconomy(pcEnonomyCollection);
		//
		IRow row = null;
		ProgrammingContractEconomyInfo pcEnonomyInfo = null;
		for (int i = 0; i < pcEnonomyCollection.size(); i++) {
			pcEnonomyInfo = pcEnonomyCollection.get(i);
			row = table.addRow();
			if (pcEnonomyInfo.getId() == null) {
				pcEnonomyInfo.setId(BOSUuid.create(pcEnonomyInfo.getBOSType()));
			}
			row.getCell(ECONOMY_ID).setValue(pcEnonomyInfo.getId());
			row.getCell(PAYMENTTYPE).setValue(pcEnonomyInfo.getPaymentType());
			if (pcEnonomyInfo.getScale() != null) {
				row.getCell(SCALE).setValue(pcEnonomyInfo.getScale());
			}
			row.getCell(AMOUNT).setValue(pcEnonomyInfo.getAmount());
			row.getCell(CONDITION).setValue(pcEnonomyInfo.getCondition());
			row.getCell(PAYMENTDATE).setValue(pcEnonomyInfo.getPaymentDate());
			row.getCell(ECONOMY_DES).setValue(pcEnonomyInfo.getDescription());
			row.getCell("completedate").setValue(pcEnonomyInfo.getCompletedate());
			int delaydays = pcEnonomyInfo.getDelaydays();
			row.getCell("delaydays").setValue(delaydays == 0 ? "" : delaydays + "");

			FDCScheduleTaskInfo taskInfo = null;

			if (pcEnonomyInfo.getScheduletask() != null && pcEnonomyInfo.getScheduletask().getId() != null) {
				try {
					taskInfo = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(
							new ObjectUuidPK(pcEnonomyInfo.getScheduletask().getId()));
				} catch (EASBizException e) {
					handUIExceptionAndAbort(e);
				} catch (BOSException e) {
					handUIExceptionAndAbort(e);
				}
			}
			row.getCell("scheduletask").setValue(taskInfo);
		}
		paymentTypeF7();

		paymentDate();
	}

	/**
	 * 通过框架ID获取所关联的经济条款
	 * 
	 * @param pteID
	 * @return
	 */
	private ProgrammingContractEconomyCollection getPCEconomy(String pcID) {
		ProgrammingContractEconomyInfo pcEnonomyInfo = null;
		PaymentTypeInfo paymentInfo = null;
		ProgrammingContractEconomyCollection pceCollection = new ProgrammingContractEconomyCollection();
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select FID,FPAYMENTTYPEID  from T_CON_ProgContEconomy ");
		fdcBuilder.appendSql(" where FContractID = '" + pcID + "'");
		IRowSet rs = null;
		try {
			rs = fdcBuilder.executeQuery();
			for (int i = 0; i < rs.size(); i++) {
				if (rs.next()) {
					pcEnonomyInfo = ProgrammingContractEconomyFactory.getRemoteInstance().getProgrammingContractEconomyInfo(
							new ObjectUuidPK(rs.getString("FID")));
					paymentInfo = PaymentTypeFactory.getRemoteInstance().getPaymentTypeInfo(
							new ObjectUuidPK(rs.getString("FPAYMENTTYPEID")));
					pcEnonomyInfo.setPaymentType(paymentInfo);
					pceCollection.add(pcEnonomyInfo);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		}
		return pceCollection;
	}

	/**
	 * 框架合约名称是否已存在
	 * 
	 * @param name
	 */
	private void isNameDup(String name, String id) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select * from T_CON_ProgrammingContract where FName_l2 = '" + name + "' ");
		fdcBuilder.appendSql(" and FID <> '" + id + "' ");
		try {
			IRowSet iRowSet = fdcBuilder.executeQuery();
			if (iRowSet.next()) {
				FDCMsgBox.showInfo("框架合约名称已存在，请重新输入");
				txtName.requestFocus();
				SysUtil.abort();
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		}
	}

	/**
	 * 框架合约编码是否已存在
	 * 
	 * @param number
	 */
	private void isNumberDup(String longNumber, String id) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select * from T_CON_ProgrammingContract where FLongNumber = '" + longNumber + "' ");
		fdcBuilder.appendSql(" and FID <> '" + id + "' ");
		try {
			IRowSet iRowSet = fdcBuilder.executeQuery();
			if (iRowSet.next()) {
				FDCMsgBox.showInfo("框架合约编码已存在，请重新输入");
				txtNumber.requestFocus();
				SysUtil.abort();
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		}
	}

	// 在新增状态下，要进行附件的新增，故保留临时附件的id
	private String attachMentTempID = null;

	/**
	 * 附件管理
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)	throws Exception {
		boolean isEdit = false;// 默认为查看状态
		if (OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) {
			isEdit = true;
		}
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		AttachmentUIContextInfo info = getAttacheInfo();
		if (info == null) {
			info = new AttachmentUIContextInfo();
		}
		if (FDCHelper.isEmpty(info.getBoID())) {
			String boID = getSelectBOID();
			if (boID == null) {
				if (!isEdit) {
					if (attachMentTempID == null) {
						boID = acm.getAttID().toString();
						attachMentTempID = boID;
					} else {
						boID = attachMentTempID;
					}
				} else {
					return;
				}
			}
			info.setBoID(boID);
			acm.showAttachmentListUIByBoID(boID, this, isEdit);
		}
		info.setEdit(isEdit);
		if (getSelectBOID() != null) {
			//			StringBuffer allAttachmentName = getAllAttachmentName(getSelectBOID());
			//			if (!FDCHelper.isEmpty(allAttachmentName.toString())) {
			//				pcInfo.setAttachment(allAttachmentName.toString());
			//				this.txtAttachment.setText(allAttachmentName.toString());
			//				this.txtAttachment.setToolTipText(allAttachmentName.toString());
			//			} else {
			//				pcInfo.setAttachment(null);
			//				this.txtAttachment.setText(null);
			//			}
			processAttachments(getSelectBOID());
		}
	}

	protected AttachmentUIContextInfo getAttacheInfo() {
		return null;
	}

	protected final String getSelectBOID() {
		if (editData == null)
			return null;
		String boID = editData.getId() != null ? editData.getId().toString() : null;
		return boID;
	}

	/**
	 * 获取合约框架所有附件名称字符串，名称与乐称以";"相隔
	 * 
	 * @param boID
	 * @return
	 */
	private StringBuffer getAllAttachmentName(String boID) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" SELECT DISTINCT boAt.FBoID, at.FID, at.FSimpleName, at.FName_l2, at.FCreateTime FROM T_BAS_Attachment at");
		fdcBuilder.appendSql(" INNER JOIN T_BAS_BoAttchAsso boAt on at.FID=boAt.FAttachmentID");
		fdcBuilder.appendSql(" WHERE boAt.FBoID = ? ");
		fdcBuilder.appendSql(" ORDER BY boAt.FBoID, at.FCreateTime ");

		fdcBuilder.addParam(boID);

		logger.info("sql:" + fdcBuilder.getSql().toString());
		logger.info("paramaters:" + fdcBuilder.getParamaters());

		StringBuffer sb = new StringBuffer();
		IRowSet rs = null;
		try {
			rs = fdcBuilder.executeQuery();
			while (rs.next()) {
				if (rs.isLast()) {
					sb.append(rs.getString("FName_l2")).append(".").append(rs.getString("FSimpleName"));
				} else {
					sb.append(rs.getString("FName_l2")).append(".").append(rs.getString("FSimpleName")).append(",");
				}
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		}
		return sb;
	}

	/**
	 * 工程项目F7
	 * 
	 */
	private void projectF7() {
		KDBizPromptBox projectPromptBox = new KDBizPromptBox();
		projectPromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		projectPromptBox.setVisible(true);
		projectPromptBox.setEditable(true);
		projectPromptBox.setDisplayFormat("$number$");
		projectPromptBox.setEditFormat("$number$");
		projectPromptBox.setCommitFormat("$number$");
		/**** modify bu  lihaiou,增加过滤********/
		
		// modified by zhaoqin for R130827-0309 on 2013/9/29 start
		// 工程项目显示按当前用户有权限的成本中心组织过滤
		//String id = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		//if (authorizedOrgs != null && authorizedOrgs.size() > 0) {
			//filterInfo.getFilterItems().add(new FilterItemInfo("id", authorizedOrgs, CompareType.INCLUDE));
		//}
		try {
			// 当前用户具有权限的所有成本组织
			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
//			SelectorItemCollection selectors = new SelectorItemCollection();
//			selectors.add("id");
//			selectors.add("user.id");
//			selectors.add("org.id");
//			EntityViewInfo v = new EntityViewInfo();
//			FilterInfo f = new FilterInfo();
//			f.getFilterItems().add(new FilterItemInfo("user.id", user.getId().toString()));
//			f.getFilterItems().add(new FilterItemInfo("org.isCostOrgUnit", Boolean.TRUE));
//			v.setFilter(f);
//			v.setSelector(selectors);
//			OrgRangeIncludeSubOrgCollection orisos = OrgRangeIncludeSubOrgFactory.getRemoteInstance().getOrgRangeIncludeSubOrgCollection(v);
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select a.forgid from T_PM_OrgRangeIncludeSubOrg a left join T_ORG_BaseUnit c on c.fid=a.forgid where c.fisCostOrgUnit=1 and a.fuserid='"+user.getId().toString()+"'");
			IRowSet rs = builder.executeQuery();
			Set orgs = new HashSet();
			// 4252
			while(rs.next()){
				orgs.add(rs.getString(1));
			}
//			if(null != orisos) {
//				for(int i = 0; i < orisos.size(); i++)
//					orgs.add(orisos.get(i).getOrg().getId().toString());
//			}
			filterInfo.getFilterItems().add(new FilterItemInfo("costCenter.id", orgs, CompareType.INCLUDE));
			filterInfo.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// modified by zhaoqin for R130827-0309 on 2013/9/29 end
		
		viewInfo.setFilter(filterInfo);
		/*********** modify end***************/
		projectPromptBox.setEntityViewInfo(viewInfo);
		
		KDTDefaultCellEditor defaultCellEditor = new KDTDefaultCellEditor(projectPromptBox);
		this.kdtCost.getColumn(PROJECT).setEditor(defaultCellEditor);
		ObjectValueRender valueRender = new ObjectValueRender();
		valueRender.setFormat(new BizDataFormat("$name$"));
		this.kdtCost.getColumn(PROJECT).setRenderer(valueRender);
	}

	/**
	 * 成本科目F7 根椐工程项目过滤
	 */
	private void costAccountF7(CurProjectInfo project) {
		CostAccountPromptBox selector = new CostAccountPromptBox(this);
		KDBizPromptBox prmtCostAccount = new KDBizPromptBox() {
			protected String valueToString(Object o) {
				String str = null;
				if (o != null && o instanceof CostAccountInfo) {
					str = ((CostAccountInfo) o).getLongNumber().replace('!', '.');
				}
				return str;
			}
		};
		prmtCostAccount.setSelector(selector);
		//多选 by tim_gao
		prmtCostAccount.setEnabledMultiSelection(true);
		prmtCostAccount.setDisplayFormat("$longNumber$");
		prmtCostAccount.setEditFormat("$longNumber$");
		prmtCostAccount.setCommitFormat("$longNumber$");

		KDTDefaultCellEditor caEditor = new KDTDefaultCellEditor(prmtCostAccount);
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new
		// FilterItemInfo("fullOrgUnit.id",OrgConstants.DEF_CU_ID,CompareType.EQUALS));
		if(project == null)
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", "error"));
		else
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString()));
		entityView.setFilter(filter);
		prmtCostAccount.setEntityViewInfo(entityView);
		kdtCost.getColumn(COSTACCOUNT).setEditor(caEditor);
		ObjectValueRender kdtCostEntries_costAccount_OVR = new ObjectValueRender();
		kdtCostEntries_costAccount_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtCost.getColumn(COSTACCOUNT).setRenderer(kdtCostEntries_costAccount_OVR);
	}

	/**
	 * 成本科目F7工程项目过滤
	 */
	private void costAccountCellF7(CurProjectInfo project, int rowIndex, int colIndex) {
		this.getUIContext().put("projectF7", project);
		CostAccountPromptBox selector = new CostAccountPromptBox(this);
		this.getUIContext().remove("projectF7");
		KDBizPromptBox prmtCostAccount = new KDBizPromptBox() {
			protected String valueToString(Object o) {
				String str = null;
				if (o != null && o instanceof CostAccountInfo) {
					str = ((CostAccountInfo) o).getLongNumber().replace('!', '.');
				}
				return str;
			}
		};
		prmtCostAccount.setSelector(selector);
		// by tim_gao 多选
		prmtCostAccount.setEnabledMultiSelection(true);
		prmtCostAccount.setDisplayFormat("$longNumber$");
		prmtCostAccount.setEditFormat("$longNumber$");
		prmtCostAccount.setCommitFormat("$longNumber$");

		KDTDefaultCellEditor caEditor = new KDTDefaultCellEditor(prmtCostAccount);
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (project != null) {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString(), CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", "error", CompareType.EQUALS));
		}
		entityView.setFilter(filter);
		prmtCostAccount.setEntityViewInfo(entityView);
		caEditor.setValue(prmtCostAccount);
		if (kdtCost.getCell(rowIndex, colIndex) != null) {
			kdtCost.getCell(rowIndex, colIndex).setEditor(caEditor);
			ObjectValueRender kdtCostEntries_costAccount_OVR = new ObjectValueRender();
			kdtCostEntries_costAccount_OVR.setFormat(new BizDataFormat("$name$"));
			kdtCost.getCell(rowIndex, colIndex).setRenderer(kdtCostEntries_costAccount_OVR);
		}
	}

	/**
	 * 付款类型F7
	 */
	private void paymentTypeF7() {
		KDBizPromptBox kdtEconomyEntriese_costAccount_PromptBox = new KDBizPromptBox();
		kdtEconomyEntriese_costAccount_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
		kdtEconomyEntriese_costAccount_PromptBox.setVisible(true);
		kdtEconomyEntriese_costAccount_PromptBox.setEditable(true);
		kdtEconomyEntriese_costAccount_PromptBox.setDisplayFormat("$number$");
		kdtEconomyEntriese_costAccount_PromptBox.setEditFormat("$number$");
		kdtEconomyEntriese_costAccount_PromptBox.setCommitFormat("$number$");
		KDTDefaultCellEditor kdtEconomyEntriese_costAccount_CellEditor = new KDTDefaultCellEditor(kdtEconomyEntriese_costAccount_PromptBox);
		this.kdtEconomy.getColumn(PAYMENTTYPE).setEditor(kdtEconomyEntriese_costAccount_CellEditor);
		ObjectValueRender kdtCostEntries_paymentType_OVR = new ObjectValueRender();
		kdtCostEntries_paymentType_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtEconomy.getColumn(PAYMENTTYPE).setRenderer(kdtCostEntries_paymentType_OVR);
	}

	/**
	 * added by shangjing  
	 * 相关任务项F7 初始化设置
	 */
	private void scheduleTaskF7() {
		CurProjectInfo curProject = (CurProjectInfo) this.getUIContext().get("project");
		if (curProject == null)
			return;

		KDBizPromptBox prmtScheduleTask = new KDBizPromptBox();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("schedule.project.id", curProject.getId().toString());
		prmtScheduleTask.setDisplayFormat("$name$");
		prmtScheduleTask.setEditFormat("$name$");
		prmtScheduleTask.setCommitFormat("$id$");
		//添加F7
		prmtScheduleTask.setSelector(new F7ScheduleTaskPromptBox(filter));
		ICellEditor scheduletaskeditor = new KDTDefaultCellEditor(prmtScheduleTask);
		this.kdtEconomy.getColumn("scheduletask").setEditor(scheduletaskeditor);
	}

	/**
	 * 付款日期
	 */
	private void paymentDate() {
		KDDatePicker kdDataPicker = new KDDatePicker();
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(kdDataPicker);
		kdtEconomy.getColumn(PAYMENTDATE).setEditor(cellEditor);
		ObjectValueRender ovr = new ObjectValueRender();
		kdtEconomy.getColumn(PAYMENTDATE).setRenderer(ovr);
	}

	protected void initListener() {
		super.initListener();
		txtWorkContent.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				String text = txtWorkContent.getText();
				if (text == null || text.trim().length() == 0) {
					txtWorkContent.setText(DEFAULT_WORK_CONTENT);
					txtWorkContent.setCustomForegroundColor(Color.GRAY);
				}
			}

			public void focusGained(FocusEvent e) {
				String text = txtWorkContent.getText();
				if (text == null || text.trim().length() == 0)
					return;
				if (text.equals(DEFAULT_WORK_CONTENT)) {
					txtWorkContent.setText("");
					txtWorkContent.setCustomForegroundColor(Color.BLACK);
				}
			}
		});
	}

	/**
	 * 获取合约框架所有附件名称字符串，名称与乐称以";"相隔
	 * @param boID
	 * @return
	 */
	private void processAttachments(String boID) {
		pnlAttachment.removeAll();
		pnlAttachment.setAutoscrolls(true);
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" SELECT DISTINCT boAt.FBoID, at.FID, at.FSimpleName, at.FName_l2, at.FCreateTime FROM T_BAS_Attachment at");
		fdcBuilder.appendSql(" join T_BAS_BoAttchAsso boAt on at.FID=boAt.FAttachmentID");
		fdcBuilder.appendSql(" where boAt.FBoID = '").appendSql(boID).appendSql("'");
		logger.info("sql:" + fdcBuilder.getSql().toString());
		IRowSet rs = null;

		try {
			rs = fdcBuilder.executeQuery();
			int index = 0;
			int width = 108;
			int height= 19;
			int x=4;
			int y=4;
			while (rs.next()) {
				com.kingdee.bos.ctrl.swing.KDLabel att = new com.kingdee.bos.ctrl.swing.KDLabel();
				att.setName(rs.getString("FID"));
				att.setText(rs.getString("FName_l2") + "." + rs.getString("FSimpleName") + ";");
				att.setBounds(x, y, width, height);
				att.setForeground(new java.awt.Color(0, 0, 255));
				att.addMouseListener(new MouseListener() {

					public void mouseReleased(MouseEvent e) {
					}

					public void mousePressed(MouseEvent e) {
					}

					public void mouseExited(MouseEvent e) {
					}

					public void mouseEntered(MouseEvent e) {
					}

					public void mouseClicked(MouseEvent e) {
						try {
							com.kingdee.bos.ctrl.swing.KDLabel source = (com.kingdee.bos.ctrl.swing.KDLabel) e.getSource();
							/*String className = AttachmentEditUI.class.getName();
							Map map = new UIContext(this);
							map.put("ID", source.getName());
							IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(className, map, null, OprtState.VIEW);
							uiWindow.show();*/
							getFileGetter().viewAttachment(source.getName());
						} catch (Exception e1) {
							handUIExceptionAndAbort(e1);
						}
					}
				});
				if((index+1)%5==0){
					x=4;
					y=y+height+4;
				}else{
					x=x+width;
				}
				pnlAttachment.add(att, index++);
			}
			int changY = y<30 ? y : y - pnlAttachment.getHeight();
			kDTabbedPane1.setBounds(kDTabbedPane1.getX(), kDTabbedPane1.getY()+changY, kDTabbedPane1.getWidth(), kDTabbedPane1.getHeight()-changY);
			kDPanel1.setBounds(kDPanel1.getX(), kDPanel1.getY(), kDPanel1.getWidth(), kDPanel1.getHeight()+changY);
			pnlAttachment.setBounds(pnlAttachment.getX(), pnlAttachment.getY(), pnlAttachment.getWidth(), pnlAttachment.getHeight()+changY);
		} catch (BOSException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		}
	}
	private FileGetter fileGetter = new FileGetter((IAttachment)AttachmentFactory.getRemoteInstance(),AttachmentFtpFacadeFactory.getRemoteInstance());
	
	private FileGetter getFileGetter() throws Exception {
		if (fileGetter == null)
			fileGetter = new FileGetter((IAttachment) AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
		return fileGetter;
	}

	private BigDecimal calculateContractSacle(BigDecimal contractAssign, BigDecimal goalCost) {
		if (contractAssign == null || goalCost == null)
			return FDCHelper.ZERO;
		return contractAssign.divide(goalCost, 4, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED).divide(FDCHelper.ONE, 2,
				BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * @see com.kingdee.eas.fdc.contract.programming.client.AbstractProgrammingContractEditUI#txtReservedChangeRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent)
	 */
	protected void txtReservedChangeRate_dataChanged(DataChangeEvent e) throws Exception {
		txtReservedChangeRate.setValue(FDCHelper.divide(e.getNewValue(), FDCHelper.ONE_HUNDRED,4,BigDecimal.ROUND_HALF_UP), false);
		calPurControl();
	}

	/**
	 * @see com.kingdee.eas.fdc.contract.programming.client.AbstractProgrammingContractEditUI#txtAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent)
	 */
	protected void txtAmount_dataChanged(DataChangeEvent e) throws Exception {
		calPurControl();
		if(txtAmount.getBigDecimalValue()!=null && txtAmount.getBigDecimalValue().compareTo(payPlanNewUI.planAmount)!=0){
			payPlanNewUI.updateAmount(txtAmount.getBigDecimalValue());
		}
	}

	/**
	 * @see com.kingdee.eas.fdc.contract.programming.client.AbstractProgrammingContractEditUI#txtControlAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent)
	 */
	protected void txtControlAmount_dataChanged(DataChangeEvent e) throws Exception {
		if (!FDCHelper.isZero(txtAmount.getValue())) {
			txtReservedChangeRate.setValue(FDCHelper.subtract(FDCHelper.ONE, FDCHelper.divide(e.getNewValue(), txtAmount.getValue(), 2,
					BigDecimal.ROUND_HALF_UP)), false);
		}
	}

	/**
	 * 描述：计算采购控制价
	 * 规划金额/（1+预留变更签证率），两位小数
	 */
	private void calPurControl() {
		BigDecimal amount = txtAmount.getBigDecimalValue();
		BigDecimal reservedChangeRate = txtReservedChangeRate.getBigDecimalValue();

		if (FDCHelper.isNullZero(amount)) {
			txtControlAmount.setValue(amount, false);
		} else {
			txtControlAmount.setValue(FDCHelper.toBigDecimal(FDCHelper.multiply(amount, FDCHelper.subtract(FDCHelper.ONE,
					reservedChangeRate)), 2), false);
		}
	}
	
	/**
	 * 建发要求：有目标成本时，规划金额只读 - modified by zhaoqin for R130828-0384 on 2013/9/29
	 */
	private void changeTxtAmountState() {
		if(kdtCost.getRowCount() > 0) {
			this.txtAmount.setEnabled(false);
		} else {
			this.txtAmount.setEnabled(true);
		}
	}
	
	/**
	 * 获取当前工程项目最新版本的目标成本
	 * @param curProject
	 * @return
	 * @throws BOSException
	 * 
	 * @author zhaoqin
	 * @date 2013/10/22
	 */
	private AimCostInfo getLastAimCostByCurProject(CurProjectInfo curProject) throws BOSException {
		if(null == curProject || null == curProject.getId())
			return null;
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("versionNumber");
		selector.add("versionName");
		selector.add("orgOrProId");
		selector.add("state");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId", curProject.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion", Boolean.TRUE));
		view.setFilter(filter);
		view.setSelector(selector);
		AimCostCollection coll = AimCostFactory.getRemoteInstance().getAimCostCollection(view);
		if(coll.size() > 0)
			return coll.get(0);
		return null;
	}
	
	/**
	 * 取得成本科目的"本合约比例"比例之和
	 * @author zhaoqin
	 * @date 2013/10/25
	 */
	private BigDecimal getAllContractAssignScale(CurProjectInfo project, ProgrammingContractInfo pcInfo, 
			CostAccountInfo caInfo) throws BOSException {
		String projectId = project.getId().toString();
		String costAccountId = caInfo.getId().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		
		/* modified by zhaoqin for R131212-0339 on 2013/12/16 */
		//builder.appendSql("select sum(cost.fcontractAssign) as assigned,sum(cost.FContractScale) as assignedScale ");
		builder.appendSql("select cost.fcontractAssign as contractAssign,cost.FContractScale as ContractScale ,cost.FGoalCost as GoalCost ");
		
		builder.appendSql("from T_CON_ProgrammingContracCost as cost ");
		builder.appendSql("join T_CON_ProgrammingContract as con on con.fid= cost.FContractID ");
		builder.appendSql("join T_CON_Programming as pro on pro.fid = con.FProgrammingID ");
		builder.appendSql("join (select max(pro.fversion) as fver,pro.fprojectid as proid");
		//builder.appendSql(" from T_CON_ProgrammingContracCost as cost");
		//builder.appendSql(" join T_CON_ProgrammingContract as con on con.fid= cost.FContractID");
		//builder.appendSql(" join T_CON_Programming as pro on pro.fid = con.FProgrammingID");
		builder.appendSql(" from T_CON_Programming as pro ");
		builder.appendSql(" where pro.fprojectid<>'").appendSql(projectId).appendSql("' ");	// 不包含当前工程项目
		//builder.appendSql(" and cost.fcostAccountid='").appendSql(costAccountId).appendSql("' ");
		builder.appendSql(" group by pro.fprojectid");
		builder.appendSql(") t on t.fver=pro.fversion and t.proid = pro.fprojectid ");
		builder.appendSql("where cost.fcostAccountid='").appendSql(costAccountId).appendSql("'");

		BigDecimal allContractScale = FDCHelper.ZERO;
		IRowSet rowSet = builder.executeQuery();
		try {
			/* modified by zhaoqin for R131212-0339 on 2013/12/16 start */
			/*if (rowSet.next()) {
				allContractScale = FDCHelper.toBigDecimal(rowSet.getBigDecimal("assignedScale"));
			}*/
			while(rowSet.next()) {
				BigDecimal contractAssign = rowSet.getBigDecimal("contractAssign");
				BigDecimal goalCost = rowSet.getBigDecimal("GoalCost");
				
				if(null == goalCost || goalCost.signum() == 0) {
					if (null != rowSet.getBigDecimal("ContractScale")) {
						allContractScale = allContractScale.add(rowSet.getBigDecimal("ContractScale"));
					}
				} else {
					allContractScale = allContractScale.add(FDCHelper.multiply(FDCHelper.ONE_HUNDRED, 
							FDCHelper.divide(contractAssign, goalCost, 14, BigDecimal.ROUND_HALF_UP)));
				}
			}
			/* modified by zhaoqin for R131212-0339 on 2013/12/16 end */
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		if(null == allContractScale)
			allContractScale = FDCHelper.ZERO;
		
		for (int i = 0; i < pcCollection.size(); i++) {
			ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
			if (programmingContractInfo.getId().toString().equals(pcInfo.getId().toString())) 
				continue;
			ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
			for (int j = 0; j < costEntries.size(); j++) {
				ProgrammingContracCostInfo pccInfo = costEntries.get(j);
				CostAccountInfo costAccountInfo = pccInfo.getCostAccount();
				if (null != costAccountInfo && null != costAccountInfo.getId()) {
					if (costAccountInfo.getId().toString().equals(caInfo.getId().toString())) {
						/* modified by zhaoqin for R131212-0339 on 2013/12/16 start */
						BigDecimal contractScale = pccInfo.getContractScale();
						BigDecimal contractAssign = pccInfo.getContractAssign();
						BigDecimal goalCost = pccInfo.getGoalCost();
						if(null == goalCost || goalCost.signum() == 0) {
							if (null != contractScale) {
								allContractScale = allContractScale.add(contractScale);
							}
						} else {
							allContractScale = allContractScale.add(FDCHelper.multiply(FDCHelper.ONE_HUNDRED, 
									FDCHelper.divide(contractAssign, goalCost, 14, BigDecimal.ROUND_HALF_UP)));
						}
						/* modified by zhaoqin for R131212-0339 on 2013/12/16 end */
					}
				}
			}
		}
		
		return allContractScale;
	}
}