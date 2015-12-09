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
 * ��ܺ�Լ�༭����
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

	// �ɱ����ɷ�¼�������
	private static final String COST_ID = "id";// ID
	private static final String PROJECT = "project";// ������Ŀ(F7)
	private static final String COSTACCOUNT_NUMBER = "number";// �ɱ���Ŀ����
	private static final String COSTACCOUNT = "name";// �ɱ���Ŀ����(F7)
	private static final String GOALCOST = "goalCost";// Ŀ��ɱ�
	private static final String ASSIGNED = "assigned";// �ѷ���
	private static final String ASSIGNING = "assigning";// ������
	private static final String CONTRACTASSIGN = "contractAssign";// ����Լ����
	private static final String COST_DES = "description";// ��ע
	
	// modified by zhaoqin on 2013/10/23
	private static final String AIMCOST_ID = "aimCostId";// Ŀ��ɱ�id

	// ���������¼�������
	private static final String ECONOMY_ID = "id";// ID
	private static final String PAYMENTTYPE = "paymentType";// ��������
	private static final String SCALE = "scale";// �������
	private static final String AMOUNT = "amount";// ������
	private static final String PAYMENTDATE = "paymentDate";// ��������
	private static final String CONDITION = "condition";// ��������
	private static final String ECONOMY_DES = "description";// ��ע

	private static final String DEFAULT_WORK_CONTENT = "��¼�롰�������ݡ������б�ָ����������ִͬ�г������⡱�ȱ�ע��Ϣ";

	protected PayPlanNewUI payPlanNewUI;
	
	// ��ȡ��Ȩ�޵���֯
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
		//����Ĭ��������
		kdtEconomy.getColumn("scheduletask").getStyleAttributes().setHided(true);
		kdtEconomy.getColumn("completedate").getStyleAttributes().setHided(true);
		kdtEconomy.getColumn("delaydays").getStyleAttributes().setHided(true);
		
		txtAmount.addDataChangeListener(new DataChangeListener() {
			/*
			 * �滮���ı����Ҫ��������������¼������ 1. �жϹ滮���¼�����ֵ�Ƿ�С��0 2. �����������������ԭ���������̬�ı�
			 * 3.
			 */
			public void dataChanged(DataChangeEvent e) {
				if (e.getOldValue() != e.getNewValue()) {
					/**
					 * ���������¼��̬�仯���
					 */
					int economyRowCount = kdtEconomy.getRowCount();
					BigDecimal planAmount = null;// �滮���
					if (!FDCHelper.isEmpty(e.getNewValue())) {
						planAmount = new BigDecimal(e.getNewValue().toString());
						/* 1.�жϹ滮���¼�����ֵ�Ƿ�С��0 */
						// if (planAmount.compareTo(new BigDecimal(0)) <= 0) {
						// FDCMsgBox.showInfo("\"�滮���\"����С��0");
						// txtAmount.setValue(e.getOldValue());
						// SysUtil.abort();
						// }
						/* 2.�����������������ԭ���������̬�ı� */
						// 2.1 �滮���䲻Ϊ��ֵ������������� = ԭ�������*�¹滮���
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
						// 2.2 �滮����Ϊ��ֵ�������������ȫ����null
						for (int i = 0; i < economyRowCount; i++) {
							kdtEconomy.getCell(i, AMOUNT).setValue(null);
						}
					}

					/**
					 * �ɱ����ɷ�¼��̬�仯���
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
	 * ������
	 * @throws ParseException 
	 * @throws BOSException 
	 * @Author��keyan_zhao
	 * @CreateTime��2013-8-22
	 */
	private void storeEditData() throws ParseException, BOSException {
		/* ���浥��ͷ��Ϣ */
		editData.setLongNumber(txtNumber.getText());// ������
		if (FDCHelper.isEmpty(txtParentLongName.getText())) {
			editData.setDisplayName(txtParentLongName.getText());// �ϼ���Լ������
		} else {
			editData.setDisplayName(txtParentLongName.getText() + "." + txtName.getText());
		}
		editData.setNumber(txtNumber.getText());
		editData.setName(setNameIndent(editData.getLevel()) + txtName.getText());// ����
		editData.setInviteWay((InviteFormEnum) kdcInviteWay.getSelectedItem());// �б귽ʽ
		editData.setInviteOrg((CostCenterOrgUnitInfo) prmtInviteOrg.getData());
		
		/* modified by zhaoqin for R131227-0115 on 2013/01/24 start */
		BigDecimal amount = txtAmount.getBigDecimalValue();
		amount = null == amount ? amount : FDCHelper.toBigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
		//editData.setAmount((BigDecimal) txtAmount.getValue());// �滮���
		editData.setAmount(amount);// �滮���
		this.txtAmount.commitEdit();
		/* modified by zhaoqin for R131227-0115 on 2013/01/24 end */
		
		editData.setControlAmount((BigDecimal) txtControlAmount.getValue());// �ɹ����ƽ��
		editData.setReservedChangeRate(FDCHelper.toBigDecimal(txtReservedChangeRate.getValue()));// �ɹ����ƽ��
		editData.setWorkContent(txtWorkContent.getText());// ��������
		editData.setContractContUI(txtContractContUI.getText());
		editData.setAttachWork(txtAttachWork.getText());
		editData.setAttContract(txtAttContract.getText());
		if (editData.getWorkContent() != null && editData.getWorkContent().equals(DEFAULT_WORK_CONTENT)) {
			editData.setWorkContent("");
		}
		editData.setSupMaterial(txtSupMaterial.getText());// �׹�����ָ����
		editData.setDescription(txtDescription.getText());// ��ע
		//		if (FDCHelper.isEmpty(txtAttachment.getText())) {
		//			pcInfo.setAttachment(null);// ����
		//		} else {
		//			pcInfo.setAttachment(txtAttachment.getText());// ����
		//		}

		// ���¹滮���������
		updateBalance(editData);

		/* ���浥�ݷ�¼��Ϣ */
		/* �ɱ����� */
		editData.getCostEntries().clear();
		int cost_rowCount = kdtCost.getRowCount();
		for (int i = 0; i < cost_rowCount; i++) {
			ProgrammingContracCostInfo pccInfo = new ProgrammingContracCostInfo();
			Object project = kdtCost.getCell(i, PROJECT).getValue();// ������Ŀ
			Object costAccount = kdtCost.getCell(i, COSTACCOUNT).getValue();// �ɱ���Ŀ
			Object goalCost = kdtCost.getCell(i, GOALCOST).getValue();// Ŀ��ɱ�
			Object assigned = kdtCost.getCell(i, ASSIGNED).getValue();// �ѷ���
			Object assigning = kdtCost.getCell(i, ASSIGNING).getValue();// ������
			Object contractScale = kdtCost.getCell(i, kdtCost.getColumnIndex(CONSTRACTSCALE)).getValue();
			Object contractAssign = kdtCost.getCell(i, CONTRACTASSIGN).getValue();// ����Լ����
			String description = (String) kdtCost.getCell(i, COST_DES).getValue();// ��ע
			if(project!=null){
				CurProjectInfo projectInfo = (CurProjectInfo) project;
				if(costAccount!=null){
					CostAccountInfo costAccountInfo = (CostAccountInfo) costAccount;
					costAccountInfo.setCurProject(projectInfo);
					pccInfo.setCostAccount(costAccountInfo);
				}else{
					FDCMsgBox.showWarning("��"+(i+1)+"�гɱ���Ŀ����Ϊ��");
					abort();
				}
			}else{
				FDCMsgBox.showWarning("��"+(i+1)+"�й�����Ŀ����Ϊ��");
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
			// �����Լ�滮����ʱĿ��ɱ�����汾����
			String aimCostId = (String) kdtCost.getCell(i, AIMCOST_ID).getValue();
			pccInfo.setAimCostId(aimCostId);
			
			editData.getCostEntries().add(pccInfo);
		}

		// modified by zhaoqin on 2013/11/07
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("project");
		// ��̬���³ɱ�����"������"��ֵ
		int pteSize = pcCollection.size();
		for (int i = 0; i < pteSize; i++) {
			/* modified by zhaoqin for R140425-0059 on 2014/05/09 start */
			ProgrammingContractInfo pcInfo = pcCollection.get(i);
			if(null != pcInfo.getId() && pcInfo.getId().toString().equals(this.editData.getId().toString()))
				continue;
			ProgrammingContracCostCollection pccCollection = pcInfo.getCostEntries();// �ɱ����ɼ���
			/* modified by zhaoqin for R140425-0059 on 2014/05/09 end */
			for (int j = 0; j < pccCollection.size(); j++) {
				ProgrammingContracCostInfo pccInfo = pccCollection.get(j);// �ɱ�����
				BigDecimal oldContractAssign = pccInfo.getContractAssign();// ����Լ����
				CostAccountInfo costAccountInfo = pccInfo.getCostAccount();// �ɱ���Ŀ
				BigDecimal goalCost = pccInfo.getGoalCost();// Ŀ��ɱ�
				if (costAccountInfo != null) {
					// ---------- modified by zhaoqin on 2013/11/07 start ----------
					BigDecimal allAssignScale = getAllContractAssignScale(project, pcCollection.get(i),costAccountInfo);
					//BigDecimal allCostAccountAssign = getAllContractAssign(costAccountInfo, true, null);// "����Լ����"���֮��
					if (oldContractAssign != null) {
						// ����"������":"Ŀ��ɱ�"-"����Լ����"���֮��+����"����Լ����"
						// BigDecimal newAssigning = goalCost.subtract(allCostAccountAssign).add(oldContractAssign);
						// ����"�ѷ���"��"����Լ����"���֮��-����"����Լ����"
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

		/* �������� 
		editData.getEconomyEntries().clear();
		int enonomy_rowCount = kdtEconomy.getRowCount();
		for (int i = 0; i < enonomy_rowCount; i++) {
			ProgrammingContractEconomyInfo pciInfo = new ProgrammingContractEconomyInfo();
			PaymentTypeInfo currentInfo = (PaymentTypeInfo) kdtEconomy.getCell(i, PAYMENTTYPE).getValue();// ��������
			Object scaleObj = kdtEconomy.getCell(i, SCALE).getValue();// �������
			Object amountObj = kdtEconomy.getCell(i, AMOUNT).getValue();// ������
			Object conditionObj = kdtEconomy.getCell(i, CONDITION).getValue();// ��������
			Object paymentDateObj = kdtEconomy.getCell(i, PAYMENTDATE).getValue();// ��������
			Object descriptionObj = kdtEconomy.getCell(i, ECONOMY_DES).getValue();// ��ע
			Object completeDateObj = kdtEconomy.getCell(i, "completedate").getValue();// �ƻ��������
			Object delaydaysObj = kdtEconomy.getCell(i, "delaydays").getValue();//�ӳ�����

			Object scheduleObj = kdtEconomy.getCell(i, "scheduletask").getValue();

			pciInfo.setPaymentType(currentInfo);// �洢��������
			if (!FDCHelper.isEmpty(scaleObj)) {
				pciInfo.setScale(new BigDecimal(scaleObj.toString()));// �洢�������
			}
			if (!FDCHelper.isEmpty(amountObj)) {
				pciInfo.setAmount(new BigDecimal(amountObj.toString()));// �洢������
			}
			pciInfo.setCondition((String) conditionObj);// �洢��������
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (!FDCHelper.isEmpty(paymentDateObj)) {
				Date paymentDate = null;
				if (paymentDateObj instanceof Date) {

					paymentDate = (Date) paymentDateObj;
				} else {
					paymentDate = sdf.parse((String) paymentDateObj);
				}
				pciInfo.setPaymentDate(new Timestamp(paymentDate.getTime()));// �洢��������
			}
			// added by shangjing ����ƻ�������� ��������� �ӳ����� ��������
			if (!FDCHelper.isEmpty(completeDateObj)) {
				Date completeDate = (Date) completeDateObj;
				pciInfo.setCompletedate(completeDate);// �洢�ƻ��������
			}
			if (!FDCHelper.isEmpty(scheduleObj)) {
				FDCScheduleTaskInfo schedule = (FDCScheduleTaskInfo) scheduleObj;
				pciInfo.setScheduletask(schedule);//�洢���������
			}

			if (!FDCHelper.isEmpty(delaydaysObj)) {
				int delaydays = 0;
				delaydays = (new Integer(delaydaysObj.toString())).intValue();
				//				if ((String) delaydaysObj != null
				//						&& !"".equals((String) delaydaysObj)) {
				//					delaydays = Integer.parseInt((String) delaydaysObj);// �洢�ӳ�����
				//				}
				pciInfo.setDelaydays(delaydays);
			}
			//
			pciInfo.setDescription((String) descriptionObj);// �洢��������
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
		this.txtParentLongName.setEditable(false);// �ϼ���Լ��ܳ����Ʋ��ɱ༭
		this.txtAttachment.setEditable(false);// �����ı�����ṩ������ɸ��ݲ���״̬�����ڸ������������Ǳ༭״̬���ǲ鿴
		Map uiContext = this.getUIContext();
		Object object = uiContext.get("inviteProject");
		Object contractBillProg = uiContext.get("programmingContractTemp");
		if (object != null || contractBillProg != null) {
			this.btnSave.setVisible(false);// �б����������鿴���룬�ѱ��水ťȥ��
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
		//modify by yxl 20150812  ���Ӹ����ҳǩ
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
				row1.getCell(columnIndex).setValue("�ؼ���������ƻ����ʱ��");
				row2.getCell(columnIndex).setValue("��������");
				icol = kdtfxbd.addColumn();
				icol.setEditor(ywDate_CellEditor);
				icol.setKey(key+"date");
				columnIndex = icol.getColumnIndex();
				icol.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
				row0.getCell(columnIndex).setValue(name);
				row1.getCell(columnIndex).setValue("�ؼ���������ƻ����ʱ��");
				row2.getCell(columnIndex).setValue("���ʱ��");
				//�����Ƿ�Ԥ��
				icol = kdtfxbd.addColumn();
				icol.setEditor(boxeditor);
				icol.setRenderer(boxrender);
				icol.setKey(key+"yj");
				columnIndex = icol.getColumnIndex();
				row0.getCell(columnIndex).setValue(name);
				row1.getCell(columnIndex).setValue("�ؼ���������ƻ����ʱ��");
				row2.getCell(columnIndex).setValue("�Ƿ�Ԥ��");
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
			//ǿ��
			if(pinfo.isStrongControl() && ckdate.compareTo(editdate)<0){
				FDCMsgBox.showInfo("ֻ�ܰ�ʱ����ǰ�ģ�");
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
		addFxbdLine = new KDWorkButton("������");
		removeFxbdLine = new KDWorkButton("ɾ����");
//		KDWorkButton insetLine = new KDWorkButton("������");
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
    	        	 MsgBox.showInfo("�������ݲ����û����������ܽ���ɾ��������");
    	        	 return;
    	        }
    	        if(FDCMsgBox.OK == FDCMsgBox.showConfirm2("�Ƿ�ȷ��ɾ�����ݣ�"))
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
	 * ��ʼ��һЩ��ֵ���������ж���
	 */
	private void initOldValue() {
		oldPcInfo = editData;
		oldAmount = FDCNumberHelper.toBigDecimal(editData.getAmount());
		oldControlAmount = FDCNumberHelper.toBigDecimal(editData.getControlAmount());
		oldbalance = FDCNumberHelper.toBigDecimal(editData.getBalance());
		oldcontrolBalance = FDCNumberHelper.toBigDecimal(editData.getControlBalance());
	}

	/**
	 * ��ʼ��"�滮���"��"�������"�ؼ������༭����Ϊ����ϸ��ܺ�Լʱ�����ܱ༭��
	 * ��Ϊ��������Ǵ��¼���ܺ�Լ����������.
	 * 
	 * ע�⣺��δ������ĺ�Լ�滮��û�����¼���ϵ�ģ�������������·���ϸҲ���Ա༭��δ������������
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @Author��owen_wen
	 * @CreateTime��2012-4-4 
	 */
	private void initAmountControlEnable() throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent", editData.getId().toString()));
		boolean isExistChildren = ProgrammingContractFactory.getRemoteInstance().exists(filter); //������ϸ
		this.txtAmount.setEnabled(!isExistChildren);
		this.txtControlAmount.setEnabled(!isExistChildren);
	}

	/**
	 * �ڳɱ����ɺ;�������ҳǩ�����������ɾ����ť
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

		setButtonStyle(kdContainerCost, btnAddnewLine_cost, "������", "imgTbtn_addline");
		setButtonStyle(kdContainerCost, btnRemoveLines_cost, "ɾ����", "imgTbtn_deleteline");
		setButtonStyle(kDContainerEconomy, btnAddnewLine_economy, "������", "imgTbtn_addline");
		setButtonStyle(kDContainerEconomy, btnRemoveLines_economy, "ɾ����", "imgTbtn_deleteline");

		if (OprtState.VIEW.equals(getOprtState())) {
			setButtionEnable(false);
		} else {
			setButtionEnable(true);
		}
	}

	/**
	 * ���ð�ť��ʽ
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
	 * ���ư�ť�Ƿ����
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
			FDCMsgBox.showInfo("����ѡ���¼��");
			SysUtil.abort();
		}
		if (FDCMsgBox.OK == FDCMsgBox.showConfirm2("�Ƿ�ȷ��ɾ�����ݣ�")) {
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
			FDCMsgBox.showInfo("����ѡ���¼��");
			SysUtil.abort();
		}
		if (FDCMsgBox.OK == FDCMsgBox.showConfirm2("�Ƿ�ȷ��ɾ�����ݣ�")) {
			int rowIndex = this.kdtEconomy.getSelectManager().getActiveRowIndex();
			removeLine(kdtEconomy, rowIndex);
		}
	}

	/**
	 * ��ʾ������
	 */
	protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
		dataBinder.loadLineFields(table, row, obj);
	}

	/**
	 * �½������У�����һ���µķ�¼�е�Ĭ��ֵ
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
	 * ��ָ�������ɾ��ָ������
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
	 * ��ʽ��KDFormattedTextField�ı���
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
		/* �ɱ����� */
		this.kdtCost.checkParsed();
		// Ŀ��ɱ�
		FDCHelper.formatTableNumber(kdtCost, GOALCOST);
		// �ѷ���
		FDCHelper.formatTableNumber(kdtCost, ASSIGNED);
		// ������
		FDCHelper.formatTableNumber(kdtCost, ASSIGNING);
		// ����Լ����
		FDCHelper.formatTableNumber(kdtCost, CONTRACTASSIGN);
		
		// modified by zhaoqin for R131118-0273 on 2013/11/20
		kdtCost.getColumn(CONSTRACTSCALE).getStyleAttributes().setNumberFormat(FDCHelper.strPropFormat2);
		
		KDTDefaultCellEditor cellEditor0 = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf = (KDFormattedTextField) cellEditor0.getComponent();
		kdftf.setDataType(1);
		kdftf.setSupportedEmpty(true);
		kdftf.setMinimumValue(FDCHelper.ZERO);
		kdtCost.getColumn(CONTRACTASSIGN).setEditor(cellEditor0);
		kdtCost.getHeadRow(0).getCell(CONSTRACTSCALE).setValue("����Լռ�ɱ���Ŀ�ı���");
		kdtCost.getColumn(CONSTRACTSCALE).setWidth(160);
		//����Լ����
		KDTDefaultCellEditor cellEditor2 = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf2 = (KDFormattedTextField) cellEditor2.getComponent();
		kdftf2.setDataType(1);
		//kdftf2.setPrecision(2);	// modified by zhaoqin for R131118-0273 on 2013/11/20
		kdftf2.setPrecision(12);
		kdftf2.setSupportedEmpty(true);
		kdftf2.setMaximumValue(FDCHelper.ONE_HUNDRED);
		kdftf2.setMinimumValue(new BigDecimal("0"));
		this.kdtCost.getColumn(CONSTRACTSCALE).setEditor(cellEditor2);
		// ��ע
		KDTDefaultCellEditor cellEditorDes = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtfDes = (KDTextField) cellEditorDes.getComponent();
		kdtfDes.setMaxLength(80);
		this.kdtCost.getColumn(COST_DES).setEditor(cellEditorDes);
		// modify by yxl 20151109
//		projectF7();
		costAccountF7((CurProjectInfo)this.getUIContext().get("project"));
		kdtCost.getColumn(PROJECT).getStyleAttributes().setHided(true);
		/* �������� */
		//added by shangjing ��ʼ�����������¼������
//		kDContainerEconomy.setTitle("���������б�");
//		kdtEconomy.getColumn("description").setWidth(0);
//		KDFormattedTextField formattedTextField1 = new KDFormattedTextField(KDFormattedTextField.INTEGER_TYPE);
//		ICellEditor integerEditor = new KDTDefaultCellEditor(formattedTextField1);
//		kdtEconomy.getColumn("delaydays").setEditor(integerEditor);
//		kdtEconomy.getColumn("completedate").getStyleAttributes().setLocked(true);
//		kdtEconomy.getColumn("delaydays").getStyleAttributes().setLocked(true);
//		scheduleTaskF7();
//		this.kdtEconomy.checkParsed();
//		// ��������
//		this.kdtEconomy.getColumn(PAYMENTDATE).getStyleAttributes().setNumberFormat("yyyy-MM-dd");
//		// �������� -----���Ȳ��ܳ���80���ַ�
//		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDTextField());
//		KDTextField kdtf = (KDTextField) cellEditor.getComponent();
//		kdtf.setMaxLength(80);
//		this.kdtEconomy.getColumn(CONDITION).setEditor(cellEditor);
	}

	/**
	 * ��ʼ��KDFormattedTextField����ػ�������
	 * */
	private static void setTextFormat(KDFormattedTextField textField) {
		textField.setRemoveingZeroInDispaly(false);
		textField.setRemoveingZeroInEdit(false);
		textField.setPrecision(2);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setSupportedEmpty(true);
	}

	/**
	 * ��ť����
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
	 * �༭����
	 * 
	 * @param b
	 */
	private void isEditable(boolean isEditable) {
		// �ļ��༭��
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

		// ��¼
		kdtCost.setEditable(isEditable);
		kdtEconomy.setEditable(isEditable);
		
		//modify by yxl 20150813
		kdtfxbd.setEditable(isEditable);
	}

	/**
	 * �رմ���ǰ�¼�
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
				int i = FDCMsgBox.showConfirm3("�������޸ģ��Ƿ񱣴沢�˳�?");
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
	 * �жϵ����Ƿ������޸�
	 * @return	   true��ʾ���޸Ĺ���false��ʾδ���޸�
	 */
	private boolean verifyIsModify() {
		if (oprtState.equals(OprtState.VIEW)) {
			return false;
		}
		// ����״̬����EDIT����Ҳ����ADDNEWʱ������false
		if (!oprtState.equals(OprtState.EDIT) && !oprtState.equals(OprtState.ADDNEW)) {
			return false;
		}
		if (isBillHeadModified())
			return true;

		if (isCostModified())
			return true;
		//��������ע�͵�   ���ϸ����    modified by yxl 20150814
//		if (isEconomyModified())
//			return true;
		if(isFxbdModified())
			return true;
		if(payPlanNewUI.verifyTabIsModify())
			return true;
		return false;
	}

	/**
	 * �ع���ȡ�����ķ���������ͷ�Ƿ����޸� 
	 * @Author��owen_wen
	 * @CreateTime��2012-4-7
	 */
	private boolean isBillHeadModified() {
		// ������
		if (FDCHelper.isEmpty(txtNumber.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getLongNumber())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtNumber.getText()) & !FDCHelper.isEmpty(oldPcInfo.getLongNumber())) {
			if (!txtNumber.getText().equals(oldPcInfo.getLongNumber())) {
				return true;
			}
		}
		// ����
		if (FDCHelper.isEmpty(txtName.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getName())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtName.getText()) & !FDCHelper.isEmpty(oldPcInfo.getName())) {
			if (!txtName.getText().equals(oldPcInfo.getName().trim())) {
				return true;
			}
		}
		// �ɹ����ƽ��
		if (FDCHelper.isEmpty(txtControlAmount.getNumberValue()) ^ FDCHelper.isEmpty(oldPcInfo.getControlAmount())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtControlAmount.getNumberValue()) & !FDCHelper.isEmpty(oldPcInfo.getControlAmount())) {
			if (new BigDecimal(txtControlAmount.getNumberValue().toString()).compareTo(oldPcInfo.getControlAmount()) != 0) {
				return true;
			}
		}

		// �滮���
		if (FDCHelper.isEmpty(txtAmount.getNumberValue()) ^ FDCHelper.isEmpty(oldPcInfo.getAmount())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtAmount.getNumberValue()) & !FDCHelper.isEmpty(oldPcInfo.getAmount())) {
			if (new BigDecimal(txtAmount.getNumberValue().toString()).compareTo(oldPcInfo.getAmount()) != 0) {
				return true;
			}
		}

		// �б귽ʽ
//		if (FDCHelper.isEmpty(kdcInviteWay.getSelectedItem()) ^ FDCHelper.isEmpty(oldPcInfo.getInviteWay())) {
//			return true;
//		}
//		if (!FDCHelper.isEmpty(kdcInviteWay.getSelectedItem()) & !FDCHelper.isEmpty(oldPcInfo.getInviteWay())) {
//			if (!kdcInviteWay.getSelectedItem().equals(oldPcInfo.getInviteWay())) {
//				return true;
//			}
//		}

		// �б���֯
//		if (FDCHelper.isEmpty(prmtInviteOrg.getData()) ^ FDCHelper.isEmpty(oldPcInfo.getInviteOrg())) {
//			return true;
//		}
//		if (!FDCHelper.isEmpty(prmtInviteOrg.getData()) & !FDCHelper.isEmpty(oldPcInfo.getInviteOrg())) {
//			if (!prmtInviteOrg.getData().equals(oldPcInfo.getInviteOrg())) {
//				return true;
//			}
//		}

		// �������ݣ�ע��Ϊ��Ĭ�Ϲ������ݡ�ʱ�൱��û���������
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

		// �׹�����ָ����
		if (FDCHelper.isEmpty(txtSupMaterial.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getSupMaterial())) {
			return true;
		}
		if (!FDCHelper.isEmpty(txtSupMaterial.getText()) & !FDCHelper.isEmpty(oldPcInfo.getSupMaterial())) {
			if (!txtSupMaterial.getText().equals(oldPcInfo.getSupMaterial())) {
				return true;
			}
		}

		// ��ע
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
	 * �ع���ȡ�����ķ��������������Ƿ����޸�
	 * @Author��owen_wen
	 * @CreateTime��2012-4-7
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

					// ��������
					if (FDCHelper.isEmpty(infoPT) ^ FDCHelper.isEmpty(tablePT)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoPT) & !FDCHelper.isEmpty(tablePT)) {
						if (!infoPT.getName().equals(tablePT.getName())) {
							return true;
						}
					}

					// �������
					if (FDCHelper.isEmpty(infoScale) ^ FDCHelper.isEmpty(scale)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoScale) & !FDCHelper.isEmpty(scale)) {
						BigDecimal temp = new BigDecimal(scale.toString());
						if (infoScale.compareTo(temp) != 0) {
							return true;
						}
					}
					// ������
					if (FDCHelper.isEmpty(infoAmount) ^ FDCHelper.isEmpty(amount)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoAmount) & !FDCHelper.isEmpty(amount)) {
						BigDecimal temp = new BigDecimal(amount.toString());
						if (infoAmount.compareTo(temp) != 0) {
							return true;
						}
					}
					// ��������
					if (FDCHelper.isEmpty(infoCondition) ^ FDCHelper.isEmpty(condition)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoCondition) & !FDCHelper.isEmpty(condition)) {
						if (!infoCondition.equals(condition.toString())) {
							return true;
						}
					}
					// ��ע
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
			//�ж�ʱ���Ƿ��޸�
			realDate = (Date)kdtfxbd.getCell(rowIndex,number+"date").getValue();
			planDate = feinfo.getPlanDate();
			if(realDate==null ^ planDate==null)
				return true;
			if(realDate!=null & planDate!=null){
				if(new Timestamp(realDate.getTime()).compareTo(new Timestamp(planDate.getTime())) != 0)
					return true;
			}
			//�ж�Ԥ���Ƿ��޸�
			if(rowIndex==5)
				feinfo.getItemName();
			if(feinfo.isIsYj()^(kdtfxbd.getCell(rowIndex,number+"yj").getValue()==null?false:(Boolean)kdtfxbd.getCell(rowIndex,number+"yj").getValue()))
				return true;
			//������û������ļ�¼����Ҫ�ж����������Ƿ�ı�
//			if(feinfo.isIsNew() && !feinfo.getItemName().equals(kdtfxbd.getCell(rowIndex,number+"name").getValue()))
//				return true;
		}
		return false;
	}
	
	/**
	 * �ع���ȡ�����ķ������ɱ������Ƿ����޸�
	 * @Author��owen_wen
	 * @CreateTime��2012-4-7
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
					// ������Ŀ
					if (FDCHelper.isEmpty(project) ^ FDCHelper.isEmpty(tablePro)) {
						return true;
					}
					if (!FDCHelper.isEmpty(project) & !FDCHelper.isEmpty(tablePro)) {
						if (!project.getName().equals(tablePro.getName())) {
							return true;
						}
					}
					// �ɱ���ĿF7
					if (FDCHelper.isEmpty(infoCA) ^ FDCHelper.isEmpty(tableCA)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoCA) & !FDCHelper.isEmpty(tableCA)) {
						if (!infoCA.getName().equals(tableCA.getName())) {
							return true;
						}
					}
					// ����Լ����
					if (FDCHelper.isEmpty(infoContractAssign) ^ FDCHelper.isEmpty(contractAssign)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoContractAssign) & !FDCHelper.isEmpty(contractAssign)) {
						BigDecimal temp = new BigDecimal(contractAssign.toString());
						if (infoContractAssign.compareTo(temp) != 0) {
							return true;
						}
					}
					// ��ע
					if (FDCHelper.isEmpty(infoDescription) ^ FDCHelper.isEmpty(description)) {
						return true;
					}
					if (!FDCHelper.isEmpty(infoDescription) & !FDCHelper.isEmpty(description)) {
						if (!infoDescription.equals(description.toString())) {
							return true;
						}
					}
					// ����Լ����
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

	//����ʱ�ж��Ƿ����ظ��ĳɱ���Ŀ
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
					FDCMsgBox.showInfo("����ܺ�Լ�ɱ������ڴ����ظ��ĳɱ���Ŀ!");
					//					return ;
					SysUtil.abort();
				}
			}
		}
	}

	/**
	 * ����
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		
		if (getUIContext().get("editPayPlan") != null) {
			payPlanNewUI.actionSave_actionPerformed(e);
		} else {
			verifyIsEmpty();
			verifyAllData();
			//	�ж��Ƿ����ظ��ĳɱ���Ŀ
			fromByisrepeat();

			// modified by zhaoqin on 2013/11/22
			storeEditData();
			
			payPlanNewUI.actionSave_actionPerformed(e);
			if (directExit) {
				// ֱ�ӱ����˳���������ʾ
				// verifyIsEmpty();
				// verifyAllData();
			} else {
				FDCMsgBox.showInfo("�����ܺ�Լ�ɹ���");
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
		// start�����Ž�������Լ�滮����ϸ���ݼ��б�������4���ֶΡ�������ʽ��������Ͷ�귽ʽ������Ԥ�Ʒ�����ʼʱ�䡱����Ԥ�Ʒ�������ʱ�䡱���ĸ��ֶ�
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
	 * ���¹滮���������
	 */
	private void updateBalance(ProgrammingContractInfo pcInfo) {
		BigDecimal newAmount = pcInfo.getAmount();// �滮���
		if (newAmount == null) {
			newAmount = FDCHelper.ZERO;
		}
		BigDecimal newControlAmount = pcInfo.getControlAmount();// �ɹ����ƽ��
		if (newControlAmount == null) {
			newControlAmount = FDCHelper.ZERO;
		}
		// �����µĹ滮���
		pcInfo.setBalance(oldbalance.add(newAmount.subtract(oldAmount)));
		// �����µĿ������
		pcInfo.setControlBalance(oldcontrolBalance.add(newControlAmount.subtract(oldControlAmount)));
	}

	/**
	 * ������ǰ��ӿո���ʾ����Ч��
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
	 * ��¼���п���֤
	 */
	private void verifyIsEmpty() {
		ProgrammingContractInfo head = editData.getParent();
		if (head != null) {
			String longNumber = head.getLongNumber();
			if (txtNumber.getText().equals(longNumber + ".")) {
				FDCMsgBox.showInfo("��ܺ�Լ���벻��Ϊ�գ�");
				txtNumber.requestFocus();
				SysUtil.abort();
			}
			for (int i = 0; i < pcCollection.size(); i++) {
				ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
				if (programmingContractInfo.getLongNumber().equals(txtNumber.getText())
						&& !editData.getId().toString().equals(programmingContractInfo.getId().toString())) {
					FDCMsgBox.showInfo("��ܺ�Լ�����Ѵ��ڣ����������룡");
					txtNumber.requestFocus();
					SysUtil.abort();
				}
			}
		} else {
			if (FDCHelper.isEmpty(txtNumber.getText())) {
				FDCMsgBox.showInfo("��ܺ�Լ���벻��Ϊ�գ�");
				txtNumber.requestFocus();
				SysUtil.abort();
			}
		}
		if (FDCHelper.isEmpty(txtName.getText())) {
			FDCMsgBox.showInfo("��ܺ�Լ���Ʋ���Ϊ�գ�");
			txtName.requestFocus();
			SysUtil.abort();
		} else {
			for (int i = 0; i < pcCollection.size(); i++) {
				ProgrammingContractInfo rowObject = pcCollection.get(i);
				String name = rowObject.getName();
				if (!FDCHelper.isEmpty(name)) {
					if (txtName.getText().equals(name.trim()) && !editData.getId().toString().equals(rowObject.getId().toString())) {
						FDCMsgBox.showInfo("��ܺ�Լ�����Ѵ��ڣ����������룡");
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
		// �ɱ����ɷ�¼��¼�����
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
				FDCMsgBox.showInfo("������Ŀ����Ϊ�գ�");
				SysUtil.abort();
			}
			//if (FDCHelper.isEmpty(costAccountCell_number.getValue())) {
			//		FDCMsgBox.showInfo("�ɱ���Ŀ���벻��Ϊ�գ�");
			//		SysUtil.abort();
			//}
			if (FDCHelper.isEmpty(costAccountCell_name.getValue())) {
				FDCMsgBox.showInfo("�ɱ���Ŀ���Ʋ���Ϊ�գ�");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(goalCost.getValue())) {
				FDCMsgBox.showInfo("Ŀ��ɱ�����Ϊ�գ�");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(assigned.getValue())) {
				FDCMsgBox.showInfo("�ѷ��䲻��Ϊ�գ�");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(assigning.getValue())) {
				FDCMsgBox.showInfo("�����䲻��Ϊ�գ�");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(contractAssign.getValue())) {
				FDCMsgBox.showInfo("����Լ���䲻��Ϊ�գ�");
				SysUtil.abort();
			}
		}
		// ���������¼��¼�����
//		for (int i = 0; i < kdtEconomy.getRowCount(); i++) {
//			IRow row = kdtEconomy.getRow(i);
//			ICell costAccountCell = row.getCell(PAYMENTTYPE);
//			ICell scale = row.getCell(SCALE);
//			if (FDCHelper.isEmpty(costAccountCell.getValue())) {
//				FDCMsgBox.showInfo("�������Ͳ���Ϊ�գ�");
//				SysUtil.abort();
//			}
//			if (FDCHelper.isEmpty(scale.getValue())) {
//				FDCMsgBox.showInfo("�����������Ϊ�գ�");
//				SysUtil.abort();
//			}
//		}
		
	}

	private void verifyAllData() {

		BigDecimal amount = (BigDecimal) txtAmount.getValue();// �滮���
		BigDecimal controlAmount = (BigDecimal) txtControlAmount.getValue();// �ɹ����ƽ��
		
		/* modified by zhaoqin for R131227-0115 on 2013/01/24 start */
		amount = null == amount ? amount : amount.setScale(2, BigDecimal.ROUND_HALF_UP);
		controlAmount = null == controlAmount ? controlAmount : controlAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		/* modified by zhaoqin for R131227-0115 on 2013/01/24 end */
		
		if (amount != null) {
			// �滮����С��0
			if (amount.compareTo(FDCHelper.ZERO) < 0) {
				FDCMsgBox.showInfo("�滮����С��0");
				SysUtil.abort();
			}
		}
		if (controlAmount != null) {
			// �ɹ����ƽ���С��0
			if (controlAmount.compareTo(FDCHelper.ZERO) < 0) {
				FDCMsgBox.showInfo("�ɹ����ƽ���С��0");
				SysUtil.abort();
			}
		}
		if (amount != null && controlAmount != null) {
			// �ɹ����ƽ��ܴ��ڹ滮���
			if (controlAmount.compareTo(amount) > 0) {
				FDCMsgBox.showInfo("�ɹ����ƽ��ô��ڹ滮���");
				SysUtil.abort();
			}
			
			/* modified by zhaoqin for R140107-0185 on 2014/01/23 start */
			// �滮����С�����õĺ�Լ���ѷ������
			//if (amount.compareTo(getHappenedAmount()) < 0) {
			/*BigDecimal happenedAmount = FDCHelper.subtract(editData.getAmount(), editData.getBalance());
			if (amount.compareTo(happenedAmount) < 0) {
				FDCMsgBox.showInfo("�滮����С���ѷ������");
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
					FDCMsgBox.showInfo("����Լ������ô��ڴ�������");
					SysUtil.abort();
				}
			}
		}

	}

	/**
	 * 
	 * ��������Լ��������к�ͬ���ѷ������ȡֵ��ʽ(�����ṩ)���£�<br/>
	 * 		��ͬ���ս���ǰ: �ѷ������ = ��ͬ���+�����������Ƿ���㣬δ���㣺ȡԤ����ѽ��㣺ȡ�������<br/>
	 *		��ͬ���ս����: �ѷ������ = ��ͬ������<br/>
	 * @return
	 * @Author��jian_cao
	 * @CreateTime��2012-8-28
	 */
	private BigDecimal getHappenedAmount() {

		//����滮��ԼԴIDΪ�վ�֤�� �������Ĺ滮��Լ�������Ĺ滮��Լ����û�б���ͬ�����ľͲ���Ҫ�����ѷ������
		if (this.oldPcInfo.getSrcId() == null)
			return FDCHelper.ZERO;

		BigDecimal happenedAmount = FDCHelper.ZERO;
		EntityViewInfo viewInfo = new EntityViewInfo();
		viewInfo.setFilter(new FilterInfo());
		try {

			viewInfo.getFilter().appendFilterItem("programmingContract", this.oldPcInfo.getSrcId());
			//��ѯ����Լ����µ����к�ͬ
			ContractBillCollection contractBillCollection = (ContractBillCollection) ContractBillFactory.getRemoteInstance()
					.getContractBillCollection(viewInfo);
			//�����Լ�滮��û�к�ͬ�ͷ���
			if (null == contractBillCollection || contractBillCollection.size() == 0) {
				return FDCHelper.ZERO;
			}
			//�洢�����ͬID��set
			Set settlementContractBillSet = new HashSet();
			//�洢�����ͬID���ַ���
			StringBuffer changeContractBillBuff = new StringBuffer();
			//ѭ������µ����к�ͬ
			for (int i = 0, size = contractBillCollection.size(); i < size; i++) {
				ContractBillInfo conotractBillInfo = contractBillCollection.get(i);
				String contractId = conotractBillInfo.getId().toString();
				//�ѽ����ͬ
				if (conotractBillInfo.isHasSettled()) {
					settlementContractBillSet.add(contractId);
				} else {
					//�ۼӺ�ͬ���
					happenedAmount = happenedAmount.add(conotractBillInfo.getAmount());
					changeContractBillBuff.append("'").append(contractId).append("'").append(",");
				}
			}

			//�ۼ��Ѿ�����ĺ�ͬ�Ľ�����
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

			//�ۼ�δ����ĺ�ͬ�ı�����
			if (changeContractBillBuff.length() > 0) {
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("select fhasSettled,famount,fbalanceAmount from T_CON_CONTRACTCHANGEBILL where fcontractbillid in");
				builder.appendSql("(").appendSql(changeContractBillBuff.substring(0, changeContractBillBuff.length() - 1)).appendSql(") ");
				builder.appendSql(" and (fstate='" + FDCBillStateEnum.AUDITTED_VALUE + "' or");
				builder.appendSql(" fstate='" + FDCBillStateEnum.ANNOUNCE_VALUE + "' or");
				builder.appendSql(" fstate='" + FDCBillStateEnum.VISA_VALUE + "') ");
				IRowSet rowSet = builder.executeQuery();
				while (rowSet.next()) {
					//�Ѽ���ȡ������
					if (rowSet.getBoolean("fhasSettled")) {
						happenedAmount = FDCNumberHelper.add(happenedAmount, rowSet.getBigDecimal("fbalanceAmount"));
					} else {
						//δ����ȡԤ����
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
	 * �ɱ����ɷ�¼�����¼�
	 */
	protected void kdtCost_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		// ѡ������ɾ���С���ť����
		if (e.getRowIndex() > -1 && !oprtState.equals(OprtState.VIEW)
				&& e.getType() != KDTStyleConstants.HEAD_ROW) {
			btnRemoveLines_cost.setEnabled(true);
		}
		if (e.getColIndex() == kdtCost.getColumnIndex(COSTACCOUNT)
				&& e.getType() != KDTStyleConstants.HEAD_ROW) {
			CurProjectInfo project = (CurProjectInfo) kdtCost.getCell(rowIndex, PROJECT).getValue();
			if (project == null) {
				FDCMsgBox.showInfo("����¼�빤����Ŀ");
			} else {
				kdtCost.getColumn(COSTACCOUNT).getStyleAttributes().setLocked(false);
			}
		}
	}

	/**
	 * �ɱ����ɷ�¼���ݱ༭����
	 */
	protected void kdtCost_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		Object oldValue = e.getOldValue();
		// �༭"������Ŀ"
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
				costAccountCellF7(newProject, rowIndex, kdtCost.getColumnIndex(COSTACCOUNT));// ͨ��������ĿΪ�������¼��سɱ���ĿF7
				// ������Ŀ���䣬��������
				if (oldValue != null) {
					CurProjectInfo oldProject = (CurProjectInfo) oldValue;
					if (newProject.getNumber().equals(oldProject.getNumber())) {
						return;
					}
				}

				// ȡĿ��ɱ���Ϊ����ѵ�ǰ�г�������Ŀ������ֵ�ÿ�
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
				
				// ȡ��ǰ������Ŀ��Ӧ��Ŀ��ɱ� - modified by zhaoqin on 2013/10/22
				CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("project");
				if(!project.getId().toString().equals(newProject.getId().toString()))
					aimCostInfo = getLastAimCostByCurProject(newProject);
				// �����Լ�滮����ʱĿ��ɱ�����汾����
				if(null != aimCostInfo)
					kdtCost.getCell(rowIndex, AIMCOST_ID).setValue(aimCostInfo.getId().toString());
				
				/*
				 * ������Ŀ�ı�֮��
				 * 
				 * 1.
				 * 
				 * 2.�жϵ�ǰ���Ƿ���ѡ�˳ɱ���Ŀ
				 * 
				 * 2.1:���ޣ���ճ�������Ŀ�����е�ǰ�е�Ԫ��
				 * 
				 * 2.2:���У��ж�����Ĺ�����Ŀ�Ƿ�Ҳ�д˳ɱ���Ŀ
				 * 
				 * 2.2.1:���ޣ���ճ�������Ŀ�����е�ǰ�е�Ԫ��
				 * 
				 * 2.2.2:���У�
				 * 
				 * 2.2.2.1:����ǰ�гɱ���Ŀ��Ԫ�����¹����µĳɱ���Ŀ
				 * 
				 * 2.2.2.2����ȡ"Ŀ��ɱ�"
				 * 
				 * 2.2.2.2.1���ж�Ŀ��ɱ��Ƿ�Ϊ0
				 * 
				 * 2.2.2.2.1.1:Ϊ0����"Ŀ��ɱ�","�ѷ���","������","����Լ����"��ֵ0,��ע���
				 * 
				 * 2.2.2.2.1.2:��Ϊ0�����"�ѷ���","������","����Լ����"��ֵ
				 * 
				 * ����ǣ�浽��ֵ���滮������������"�������"��"������"��ֵ ����һ��
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
						// 2.2.2.1����ǰ�гɱ���Ŀ��Ԫ�����¹����µĳɱ���Ŀ
						CostAccountInfo newCostAccountInfo = ProgrammingContractUtil
								.getCostAccountByNewID(newCostAccountID);
						kdtCost.getCell(rowIndex, COSTACCOUNT).setValue(newCostAccountInfo);
						kdtCost.getCell(rowIndex, COSTACCOUNT_NUMBER).setValue(
								newCostAccountInfo.getLongNumber().replace('!','.'));
						// 2.2.2.2����ȡ"Ŀ��ɱ�"
						
						// modified by zhaoqin on 2013/10/22 start
						// newCostAccountInfoΪ����ϸ��Ŀʱ������Ҫ����ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost
						// BigDecimal goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(newCostAccountInfo, aimCostInfo);
						BigDecimal goalCost = null;
						// ȡ�ɲ�ַ���ϸ��Ŀ�Ļ���Ŀ��ɱ� add by ����
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
							// 2.2.2.2.1.2 ���"�ѷ���","������","����Լ����"��ֵ
							BigDecimal allAssigned = FDCHelper.ZERO;// �ѷ���
							// ���"������" == "Ŀ��ɱ�" - "�ѷ���"
							BigDecimal assigning = goalCost.subtract(allAssigned);
							// ����"����Լ����"="������"
							BigDecimal contractAssign = assigning;
							// ��ʾ�ڵ�Ԫ����
							kdtCost.getCell(rowIndex, GOALCOST).setValue(goalCost);// Ŀ��ɱ�
							kdtCost.getCell(rowIndex, ASSIGNED).setValue(allAssigned);// �ѷ���
							kdtCost.getCell(rowIndex, ASSIGNING).setValue(assigning);// ������
							kdtCost.getCell(rowIndex, CONTRACTASSIGN).setValue(contractAssign);// ����Լ����
							kdtCost.getCell(rowIndex, CONSTRACTSCALE).setValue(calculateContractSacle(contractAssign, goalCost));
							// ����Լ������������Զ����"�滮���"
							afterContractAssignChange();
							// �Զ����"�滮���"�󣬶�̬�ı侭��������"�������"��"������"
							// Ĭ����"�������"Ϊ��ֵ���ı�"������"
							afterPlanAmountChange();
						}
					}
				}
			} else {
				// ������Ŀ��Ϊ�������������������
				ProgrammingContractUtil.clearCell(kdtCost, rowIndex, PROJECT,
						COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST, ASSIGNED,
						ASSIGNING, CONTRACTASSIGN, COST_DES);
				kdtCost.getCell(rowIndex, CONSTRACTSCALE).setValue(null);
				kdtCost.getColumn(COSTACCOUNT).getStyleAttributes().setLocked(true);
			}

		}
		// �༭"����Լ����"
		if (colIndex == kdtCost.getColumnIndex(CONTRACTASSIGN)) {
			//���㱾��Լ����  ������Լ������=������Լ���䡱/��Ŀ��ɱ���
			Object goalCostObj = kdtCost.getCell(rowIndex, GOALCOST).getValue();
			if (!FDCHelper.isEmpty(goalCostObj)) {
				Object contractAssignObj = kdtCost.getCell(rowIndex, CONTRACTASSIGN).getValue();
				BigDecimal contractAssign = new BigDecimal(contractAssignObj == null ? "0" : contractAssignObj.toString());
				BigDecimal goalCost = new BigDecimal(goalCostObj.toString());
				if (contractAssign.compareTo(goalCost) > 0) {
					MsgBox.showWarning("����Լ���䣬���ܴ���Ŀ��ɱ�");
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

		// ѡ��"�ɱ���ĿF7"
		if (colIndex == kdtCost.getColumnIndex(COSTACCOUNT)) {
			/*
			 * 1.ȡ���ɱ���Ŀ��ֵ
			 * 
			 * 1.1�жϳɱ���Ŀ�Ƿ�Ϊ��
			 * 
			 * 1.1.1Ϊ�գ��ÿյ�ǰ�г�������Ŀ������е�Ԫ��
			 * 
			 * 1.1.2��Ϊ�գ�ȡֵ
			 * 
			 * 1.2�ж���ѡ�ĳɱ���Ŀ�Ƿ��ظ����ظ�ֱ�ӷ���,���ظ�����
			 * 
			 * 2.ȡ��Ŀ��ɱ���Ϣ
			 * 
			 * 2.1��Ϊ�գ���"Ŀ��ɱ�","�ѷ���","������","����Լ����","��ע"�ÿգ�
			 * 
			 * 2.2����Ϊ�գ� �ж�Ŀ��ɱ��Ƿ���������֮��״̬
			 * 
			 * 2.2.1����Ϊ����֮��״̬���Ѹ���Ԫ����ֵ��0����ע���ÿ� ��
			 * 
			 * 2.2.2��Ϊ����֮��״̬��ȡ����Ӧ�ɱ���Ŀ��Ŀ��ɱ�ֵ����Ҫ�õ��ɱ���Ŀ��Ŀ��ɱ���Ϊ������
			 * 
			 * 3.���"�ѷ���","������","����Լ����"��ֵ
			 * 
			 * ����ǣ�浽��ֵ���滮������������"�������"��"������"��ֵ ����һ��
			 */
			BigDecimal allAssigned = FDCHelper.ZERO;// "�ѷ���"
			CurProjectInfo project = (CurProjectInfo) kdtCost.getCell(rowIndex,PROJECT).getValue();// ������Ŀ
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
				//by tim_gao ���������Ӷ�ѡ
				if(newValue instanceof CostAccountInfo){//��ѡ by tim_gao
					CostAccountInfo newCostAccountInfo = (CostAccountInfo) newValue;// �ɱ���Ŀ
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
					AimCostInfo aimCostInfo = (AimCostInfo) this.getUIContext().get("aimCostInfo");// Ŀ��ɱ�
					
					// ȡ��ǰ������Ŀ��Ӧ��Ŀ��ɱ� - modified by zhaoqin on 2013/10/22
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
						// 2.2�Ƿ���������
						if (!isAimCostAudit(aimCostInfo)) {
							// 2.2.1
							ProgrammingContractUtil.setZero(kdtCost, rowIndex,
									GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN,COST_DES);
							
							// modified by zhaoqin on 2013/10/28
							kdtCost.getCell(rowIndex, AIMCOST_ID).setValue(null);
						} else {
							// �����Լ�滮����ʱĿ��ɱ�����汾����
							kdtCost.getCell(rowIndex, AIMCOST_ID).setValue(aimCostInfo.getId().toString());
							// 2.2.2 ȡ��Ŀ��ɱ���ֵ
							// CurProjectInfo project = (CurProjectInfo)
							// kdtCost.getCell(rowIndex, PROJECT).getValue();
							if (project != null) {
								BigDecimal goalCost = ProgrammingContractUtil
										.getGoalCostBy_costAcc_aimCost(newCostAccountInfo, aimCostInfo);
								// ȡ�ɲ�ַ���ϸ��Ŀ�Ļ���Ŀ��ɱ� add by ����
								//���ϼ�Ϊ0ʱ��ȡ�¼����� by hpw
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
									// �ѷ������
									BigDecimal allAssignedScale = getAllContractAssignScale(oldProject, this.editData, 
											newCostAccountInfo);
									// ���������
									BigDecimal assignedScale = FDCHelper.ONE_HUNDRED.subtract(allAssignedScale);
									// allAssigned = getAllContractAssign(newCostAccountInfo, false, aimCostInfo);// �ѷ���
									allAssigned = allAssignedScale.multiply(goalCost).divide(
											FDCHelper.ONE_HUNDRED, 12, BigDecimal.ROUND_HALF_UP);
									
									// ���"������" == "Ŀ��ɱ�" - "�ѷ���"
									BigDecimal assigning = goalCost.subtract(allAssigned);// ������
									// ����"����Լ����"="������"
									BigDecimal contractAssign = assigning;// ����Լ����
									// ��ʾ�ڵ�Ԫ����
									kdtCost.getCell(rowIndex, GOALCOST).setValue(goalCost);// Ŀ��ɱ�
									kdtCost.getCell(rowIndex, ASSIGNED).setValue(allAssigned);// �ѷ���
									kdtCost.getCell(rowIndex, ASSIGNING).setValue(assigning);// ������
									kdtCost.getCell(rowIndex, CONTRACTASSIGN).setValue(contractAssign);// ����Լ����
									//kdtCost.getCell(rowIndex, CONSTRACTSCALE).setValue(calculateContractSacle(contractAssign, goalCost));//
									kdtCost.getCell(rowIndex, CONSTRACTSCALE).setValue(assignedScale);
									// ---------- modified by zhaoqin on 2013/11/07 end ----------
									
									// ����Լ������������Զ����"�滮���"
									// afterContractAssignChange();
									// �Զ����"�滮���"�󣬶�̬�ı侭��������"�������"��"������"
									// Ĭ����"�������"Ϊ��ֵ���ı�"������"
									// afterPlanAmountChange();
								}
							}
						}
					}
				}else if (newValue instanceof Object[]){//��ѡ by tim_gao
					// ȡ��ǰ������Ŀ��Ӧ��Ŀ��ɱ� - modified by zhaoqin on 2013/10/22
					AimCostInfo aimCostInfo = (AimCostInfo) this.getUIContext().get("aimCostInfo");// Ŀ��ɱ�
					CurProjectInfo oldProject = (CurProjectInfo) this.getUIContext().get("project");
					if(!oldProject.getId().toString().equals(project.getId().toString()))
						aimCostInfo = getLastAimCostByCurProject(project);
					
					Object[] objr = (Object[]) newValue;
					for (int i = 0; i < objr.length; i++) {
						int tmpRowIndex = rowIndex;
						CostAccountInfo newCostAccountInfo = (CostAccountInfo) objr[i];// �ɱ���Ŀ
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
						// ȡ��ǰ������Ŀ��Ӧ��Ŀ��ɱ� - modified by zhaoqin on 2013/10/22
						//AimCostInfo aimCostInfo = (AimCostInfo) this.getUIContext().get("aimCostInfo");// Ŀ��ɱ�
						
						if (aimCostInfo == null) {
							// 2.1
							ProgrammingContractUtil.clearCell(kdtCost, tmpRowIndex,
									GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN,COST_DES);
							
							// modified by zhaoqin on 2013/10/28
							kdtCost.getCell(tmpRowIndex, AIMCOST_ID).setValue(null);
						} else {
							// 2.2�Ƿ���������
							if (!isAimCostAudit(aimCostInfo)) {
								// 2.2.1
								ProgrammingContractUtil.setZero(kdtCost, tmpRowIndex,
										GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN,COST_DES);
								
								// modified by zhaoqin on 2013/10/28
								kdtCost.getCell(tmpRowIndex, AIMCOST_ID).setValue(null);
							} else {
								// modified by zhaoqin on 2013/10/23
								// �����Լ�滮����ʱĿ��ɱ�����汾����
								kdtCost.getCell(tmpRowIndex, AIMCOST_ID).setValue(aimCostInfo.getId().toString());
								
								// 2.2.2 ȡ��Ŀ��ɱ���ֵ
								// CurProjectInfo project = (CurProjectInfo)
								// kdtCost.getCell(rowIndex, PROJECT).getValue();
								if (project != null) {
									BigDecimal goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(
											newCostAccountInfo, aimCostInfo);
									// ȡ�ɲ�ַ���ϸ��Ŀ�Ļ���Ŀ��ɱ� add by ����
									//���ϼ�Ϊ0ʱ��ȡ�¼����� by hpw
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
										// �ѷ������
										BigDecimal allAssignedScale = getAllContractAssignScale(oldProject, this.editData,
												newCostAccountInfo);
										// ���������
										BigDecimal assignedScale = FDCHelper.ONE_HUNDRED.subtract(allAssignedScale);
										//allAssigned = getAllContractAssign(newCostAccountInfo, false, aimCostInfo);// �ѷ���
										
										/* modified by zhaoqin for R140425-0059 on 2014/05/09 start */
										//allAssigned = allAssignedScale.multiply(goalCost).divide(
												//FDCHelper.ONE_HUNDRED, 12, BigDecimal.ROUND_HALF_UP);
										//���ֻ����λС��
										allAssigned = allAssignedScale.multiply(goalCost).divide(
												FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
										/* modified by zhaoqin for R140425-0059 on 2014/05/09 end */
										
										// ���"������" == "Ŀ��ɱ�" - "�ѷ���"
										BigDecimal assigning = goalCost.subtract(allAssigned);// ������
										// ����"����Լ����"="������"
										BigDecimal contractAssign = assigning;// ����Լ����
										// ��ʾ�ڵ�Ԫ����
										kdtCost.getCell(tmpRowIndex, GOALCOST).setValue(goalCost);// Ŀ��ɱ�
										kdtCost.getCell(tmpRowIndex, ASSIGNED).setValue(allAssigned);// �ѷ���
										kdtCost.getCell(tmpRowIndex, ASSIGNING).setValue(assigning);// ������
										kdtCost.getCell(tmpRowIndex, CONTRACTASSIGN).setValue(contractAssign);// ����Լ����
										//kdtCost.getCell(tmpRowIndex, CONSTRACTSCALE).setValue(
										//calculateContractSacle(contractAssign, goalCost));	//����Լ����
										kdtCost.getCell(tmpRowIndex, CONSTRACTSCALE).setValue(assignedScale);
										// ---------- modified by zhaoqin on 2013/11/07 end ----------
									}

									// ����Լ������������Զ����"�滮���"
									afterContractAssignChange();
									// �Զ����"�滮���"�󣬶�̬�ı侭��������"�������"��"������"
									// Ĭ����"�������"Ϊ��ֵ���ı�"������"
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
				MsgBox.showWarning("������0-100֮�������");
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
					FDCMsgBox.showInfo("����Լ�������ܳ���������/Ŀ��ɱ�");
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
					FDCMsgBox.showInfo("����Լ�������ܳ���������/Ŀ��ɱ�");
					
					kdtCost.getCell(rowIndex, colIndex).setValue(e.getOldValue());
					SysUtil.abort();
				}
			}
			
			if (!FDCHelper.isEmpty(contractScaleObj)) {
				String contractScaleStr = contractScaleObj.toString();
				BigDecimal contractScaleBig = new BigDecimal(contractScaleStr);
				if (contractScaleBig.compareTo(FDCHelper.ONE_HUNDRED) <= 0 && contractScaleBig.compareTo(FDCHelper.ZERO) > 0) {
					// ���Ƹ��������ʾ����
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
					MsgBox.showWarning("������1-100֮�������");
					return;
				}
			}
			//���� ������Լ���䡱=��Ŀ��ɱ���*������Լ������
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
	 * "����Լ����"ֵ�ı�󣬻��ܹ滮���
	 */
	private void afterContractAssignChange() {
		BigDecimal allContractAssign = FDCHelper.ZERO;
		for (int i = 0; i < kdtCost.getRowCount(); i++) {
			Object contractAssignObj = kdtCost.getCell(i, CONTRACTASSIGN).getValue();// ����Լ����
			if (contractAssignObj == null) {
				allContractAssign = allContractAssign.add(FDCHelper.ZERO);
			} else {
				allContractAssign = allContractAssign.add(new BigDecimal(contractAssignObj.toString()));
			}
		}
		txtAmount.setValue(allContractAssign);
	}

	/**
	 * "�滮���"ֵ�ı�󣬶�̬�ı侭�������и������ֵ
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
	 * �ж���һ����Լ֮����ͬ������Ŀ�³ɱ���Ŀ�Ƿ��ظ�
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
							FDCMsgBox.showInfo("����ܺ�Լ�ɱ��������Ѿ���\""
									+ currentInfo.getName()
									+ "\"�ɱ���Ŀ�������ټ�����Ӵ˳ɱ���Ŀ�ˣ�");
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
	 * ����������ͬ�ɱ���Ŀ��"����Լ����"���֮��
	 * 
	 * @param caInfo
	 * 
	 * @param flag
	 * 
	 *            true��ʾ������гɱ�������"����Լ����"֮��
	 * 
	 *            false ��ʾ���������Լ֮�����гɱ�������"����Լ����"֮��
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
		 * ��ѡ�񹤳���Ŀ���ǵ�ǰ��Ŀʱ��Ҫ�жϴ˳ɱ���Ŀ�ڱ����Ŀ�Ƿ��ѷ��� add by zkyan 
		 * 
		 * �����ʱ��ͬ��Ŀ�ɱ���Ŀ���������ͬ���ʽ��������жϸ�Ϊid�ж�
		 */

		for (int i = 0; i < pcCollection.size(); i++) {
			ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
			if (flag) {
				ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
				for (int j = 0; j < costEntries.size(); j++) {
					ProgrammingContracCostInfo pccInfo = costEntries.get(j);
					CostAccountInfo costAccountInfo = pccInfo.getCostAccount();
					if (costAccountInfo != null) {

						//add by zkayn LongNumber ��Ϊ id
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

						//add by zkayn LongNumber ��Ϊ id
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
	 * ���������¼�����¼�
	 */
	protected void kdtEconomy_tableClicked(KDTMouseEvent e) throws Exception {
		// ѡ������ɾ���С���ť����
		if (e.getRowIndex() > -1 && !oprtState.equals(OprtState.VIEW)
				&& e.getType() != KDTStyleConstants.HEAD_ROW) {
			btnRemoveLines_economy.setEnabled(true);
		}
	}

	/**
	 * ���������¼���ݱ༭����
	 */
	protected void kdtEconomy_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		Object oldValue = e.getOldValue();
		/* ѡ�񸶿����� */
		// if (colIndex == kdtEconomy.getColumnIndex(PAYMENTTYPE)) {
		// isEnonomDup();
		// }

		/* �༭������� */
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
			// ���Ƹ��������ʾ ����
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

			// ���Ƹ�������ʾ ����
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
					// ��д����������Զ������������
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
					FDCMsgBox.showInfo("��¼��1~100�����֣�");
				}

				// �ж�
				if (getPayScaleAll().compareTo(new BigDecimal(100)) > 0) {
					FDCMsgBox.showInfo("��������ۼƲ��ó���100%");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				} else if (getPayAmountAll().compareTo(
						txtAmount.getValue() == null ? new BigDecimal(0)
								: new BigDecimal(txtAmount.getValue().toString())) > 0) {
					FDCMsgBox.showInfo("�������ܺͲ��ܳ����滮���");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				}
			} else {
				amountCell.setValue(null);
			}
		}

		/* �༭������ */
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
			// ���Ƹ��������ʾ ����
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

			// ���Ƹ����� ��ʾ����
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
					// ��д��������Զ�������������
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
					FDCMsgBox.showInfo("��¼�����֣�");
				}

				// �ж�
				if (getPayScaleAll().compareTo(FDCHelper.ONE_HUNDRED) > 0) {
					FDCMsgBox.showInfo("������д�ĸ�������󣬸�������ѳ���100%");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				} else if (getPayAmountAll().compareTo(
						txtAmount.getValue() == null ? new BigDecimal(0)
								: new BigDecimal(txtAmount.getValue().toString())) > 0) {
					FDCMsgBox.showInfo("�������ܺͲ��ܳ����滮���");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				}
			} else {
				scaleCell.setValue(null);
			}

		}

		//added by shangjing ���������
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
				// MsgBox.showInfo("����������!");
				// ((KDTextField)kdtEconomy.getColumn("delaydays").getEditor()).requestFocus();
				// }
				// }

				//��������  �������+�ӳ�����=��������
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

		//added by shangjing �ӳ�����
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
				// MsgBox.showInfo("����������!");
				// }
				// }

				//��������  �������+�ӳ�����=��������
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
	 * �ж�Ŀ��ɱ��Ƿ�������
	 * 
	 * @return true:������֮��״̬; false:δ����
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
	 * �жϵ�Ԫ���ֵ�ڱ༭ǰ���Ƿ�ı�
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
	 * ��������ܺ�
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
	 * �������ܺ�
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
	 * ������򸶿������Ԫ��ֵɾ���������
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
	 * �жϸ��������Ƿ��ظ�
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
							FDCMsgBox.showInfo("����ܺ�Լ�����������Ѿ���\""
									+ currentInfo.getName()
									+ "\"�������ͣ������ټ�����Ӵ˸��������ˣ�");
							economyCell.setValue(null);
							SysUtil.abort();
						}
					}
				}
			}
		}
	}

	private void preparePCData() {
		// ����ͷ��Ϣ
		//yuxue
		if (editData.getParent() != null) {
			String longName = editData.getDisplayName();
			if (longName.lastIndexOf('.') > -1) {
				//��Ϊ'.'������ʱ���᷵��-1������subString()���׳�IndexOutOfBoundsException�����Լ����ж�
				String headName = longName.substring(0, longName.lastIndexOf('.'));
				this.txtParentLongName.setText(headName);// �ϼ���ܺ�Լ������
			}
		}
		txtNumber.setText(editData.getLongNumber());// ��ܺ�Լ������
		txtName.setText(editData.getName() == null ? null : editData.getName().trim());// ����
		txtAmount.setValue(editData.getAmount(), false);// �滮���
		txtControlAmount.setValue(editData.getControlAmount(), false);// �ɹ����ƽ��   
		txtReservedChangeRate.setValue(editData.getReservedChangeRate(), false);// �ɹ����ƽ��   
//		kdcInviteWay.setSelectedItem(editData.getInviteWay());// �б귽ʽ
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
//			prmtInviteOrg.setData(editData.getInviteOrg());// �б���֯
//		}

		if (editData.getWorkContent() == null || editData.getWorkContent().trim().length() == 0) {
			this.txtWorkContent.setText(DEFAULT_WORK_CONTENT);// ��������
			txtWorkContent.setCustomForegroundColor(Color.GRAY);
		} else {
			this.txtWorkContent.setText(editData.getWorkContent().trim());// ��������
			txtWorkContent.setCustomForegroundColor(Color.BLACK);
		}
		txtContractContUI.setText(editData.getContractContUI());
		txtAttachWork.setText(editData.getAttachWork());
		txtAttContract.setText(editData.getAttContract());
//		txtSupMaterial.setText(editData.getSupMaterial());// �׹�����ָ����
//		txtDescription.setText(editData.getDescription());// ��ע
//		txtDescription.setToolTipText(editData.getDescription());
		txtAttachment.setText(getAllAttachmentName(editData.getId().toString()).toString());// ����
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

		// ��¼��Ϣ
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
		// start�����Ž�������Լ�滮����ϸ���ݼ��б�������4���ֶΡ�������ʽ��������Ͷ�귽ʽ������Ԥ�Ʒ�����ʼʱ�䡱����Ԥ�Ʒ�������ʱ�䡱���ĸ��ֶ�
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
	 * ��ʼ����ܺ�Լ�ɱ����ɷ�¼����
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
				costAccountCellF7(project, i, kdtCost.getColumnIndex(COSTACCOUNT));// ���ݵ�ǰ�й�����Ŀ����F7�ɱ���Ŀ
				if(pccInfo.getCostAccount()!=null){
					row.getCell(COSTACCOUNT_NUMBER).setValue(pccInfo.getCostAccount().getLongNumber().replace('!', '.'));
				}
				row.getCell(COSTACCOUNT).setValue(pccInfo.getCostAccount());
				row.getCell(GOALCOST).setValue(pccInfo.getGoalCost());
				row.getCell(ASSIGNED).setValue(pccInfo.getAssigned());
				row.getCell(ASSIGNING).setValue(pccInfo.getAssigning());
				//by tim_gao �����ж�
				ICell cell = row.getCell(ASSIGNING);
				Object temp = cell.getValue();// ����Լ����
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
		// �汾��Լ������ʾ����
		kdtCost.getColumn(CONSTRACTSCALE).setRenderer(render);
	}

	/**
	 * added by shangjing
	 * ��鲢��ʱ���¾���������Ӧ��Ϣ
	 * @param infos ��������
	 * @return ���º�ľ�������
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
			//�õ������������������ �����¼��㾭�����������
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
	 * ��ʼ����ܺ�Լ���������¼����
	 * 
	 * @param table
	 */
	private void addEconomyLine(KDTable table) {
		ProgrammingContractEconomyCollection pcEnonomyCollection = editData.getEconomyEntries();
		//added by shangjing  ����侭�������Ƿ����������������ʱ���Ƿ�����ˣ������¾�����������ʱ��͸���ʱ�� 
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
	 * ͨ�����ID��ȡ�������ľ�������
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
	 * ��ܺ�Լ�����Ƿ��Ѵ���
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
				FDCMsgBox.showInfo("��ܺ�Լ�����Ѵ��ڣ�����������");
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
	 * ��ܺ�Լ�����Ƿ��Ѵ���
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
				FDCMsgBox.showInfo("��ܺ�Լ�����Ѵ��ڣ�����������");
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

	// ������״̬�£�Ҫ���и������������ʱ�����ʱ������id
	private String attachMentTempID = null;

	/**
	 * ��������
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)	throws Exception {
		boolean isEdit = false;// Ĭ��Ϊ�鿴״̬
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
	 * ��ȡ��Լ������и��������ַ������������ֳ���";"���
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
	 * ������ĿF7
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
		/**** modify bu  lihaiou,���ӹ���********/
		
		// modified by zhaoqin for R130827-0309 on 2013/9/29 start
		// ������Ŀ��ʾ����ǰ�û���Ȩ�޵ĳɱ�������֯����
		//String id = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		//if (authorizedOrgs != null && authorizedOrgs.size() > 0) {
			//filterInfo.getFilterItems().add(new FilterItemInfo("id", authorizedOrgs, CompareType.INCLUDE));
		//}
		try {
			// ��ǰ�û�����Ȩ�޵����гɱ���֯
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
	 * �ɱ���ĿF7 ��駹�����Ŀ����
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
		//��ѡ by tim_gao
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
	 * �ɱ���ĿF7������Ŀ����
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
		// by tim_gao ��ѡ
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
	 * ��������F7
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
	 * ���������F7 ��ʼ������
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
		//���F7
		prmtScheduleTask.setSelector(new F7ScheduleTaskPromptBox(filter));
		ICellEditor scheduletaskeditor = new KDTDefaultCellEditor(prmtScheduleTask);
		this.kdtEconomy.getColumn("scheduletask").setEditor(scheduletaskeditor);
	}

	/**
	 * ��������
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
	 * ��ȡ��Լ������и��������ַ������������ֳ���";"���
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
	 * ����������ɹ����Ƽ�
	 * �滮���/��1+Ԥ�����ǩ֤�ʣ�����λС��
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
	 * ����Ҫ����Ŀ��ɱ�ʱ���滮���ֻ�� - modified by zhaoqin for R130828-0384 on 2013/9/29
	 */
	private void changeTxtAmountState() {
		if(kdtCost.getRowCount() > 0) {
			this.txtAmount.setEnabled(false);
		} else {
			this.txtAmount.setEnabled(true);
		}
	}
	
	/**
	 * ��ȡ��ǰ������Ŀ���°汾��Ŀ��ɱ�
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
	 * ȡ�óɱ���Ŀ��"����Լ����"����֮��
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
		builder.appendSql(" where pro.fprojectid<>'").appendSql(projectId).appendSql("' ");	// ��������ǰ������Ŀ
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