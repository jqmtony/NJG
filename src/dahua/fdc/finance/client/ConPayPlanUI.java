/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.render.RenderObject;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.render.TextIconRender;
import com.kingdee.bos.ctrl.kdf.util.style.Style;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.PaymentTypeCollection;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractFullInfoUI;
import com.kingdee.eas.fdc.contract.programming.client.CostAccountPromptBox;
import com.kingdee.eas.fdc.finance.CalStandardEnum;
import com.kingdee.eas.fdc.finance.CalTypeEnum;
import com.kingdee.eas.fdc.finance.ConPayPlanByMonthInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanByScheduleInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanByScheduleTaskInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanDataACollection;
import com.kingdee.eas.fdc.finance.ConPayPlanDataAInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanDataCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanDataInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanDatapCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanDatapInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanFactory;
import com.kingdee.eas.fdc.finance.ConPayPlanInfo;
import com.kingdee.eas.fdc.finance.PayPlanModeEnum;
import com.kingdee.eas.fdc.finance.PrepayWriteOffEnum;
import com.kingdee.eas.fdc.finance.client.util.PayPlanClientUtil;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.gr.rptclient.frame.WinStyle;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * 描述：合同付款计划
 * 
 */
public class ConPayPlanUI extends AbstractConPayPlanUI {

	private static final String BIZ_TYPE_CASHFLOW = "+BONIPDySL67xM7mEm9tXmLF6cA=";

	private static final String COL_PLAN_PAY_AMOUNT = "planPayAmount";

	private static final String COL_TASK_MEASURE_AMOUNT = "taskMeasureAmount";

	private static final String COL_TASK_SETTLE_AMOUNT = "taskSettleAmount";

	private static final String COL_CAL_TYPE = "calType";

	private static final String COL_WRITE_OFF_TYPE = "writeOffType";

	private static final String COL_CAL_STANDARD = "calStandard";

	private static final String COL_PAY_SCALE = "payScale";

	private static final String COL_DELAY_DAY = "delayDay";

	private static final String COL_TASK = "task";

	private static final String COL_COST_ACCOUNT = "costAccount";

	private static final String COL_PAYMENT_TYPE = "paymentType";

	private static final String COL_PLAN_PAY_DATE = "planPayDate";

	private static final String COL_END_DATE = "endDate";

	private static final String COL_BEGIN_DATE = "beginDate";

	private static final Logger logger = CoreUIObject.getLogger(ConPayPlanUI.class);

	protected KDWorkButton btnAddnewLine;
	protected KDWorkButton btnInsertLine;
	protected KDWorkButton btnRemoveLines;
	protected KDWorkButton btnCopyLines;
	
	//0 一次性冲销且不可修改#
	//1 一次性冲销且可修改#
	//2 分批冲销且不可修改#
	//3 分批冲销且可修改
	protected int prePayParam = 0;

	private KDBizPromptBox prmtCostAccountBySchedule;

	private KDBizPromptBox prmtTask;

	private ContractBillInfo contractBillInfo;

	private KDBizPromptBox prmtCostAccountByMonth;

	public ConPayPlanUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {

		queryData();

		initTable();

		super.onLoad();

		actionImportPayPlan.setVisible(false);
		chkMenuItemSubmitAndAddNew.setSelected(false);
	}
	protected void queryData() throws EASBizException, BOSException {
		HashMap key = new HashMap();
		key.put(FDCConstants.FDC551_PREPEYMENTAPPROVE, null);
        IParamControl pc = ParamControlFactory.getRemoteInstance();
        HashMap param = pc.getParamHashMap(key);
		prePayParam =  new Integer(param.get(FDCConstants.FDC551_PREPEYMENTAPPROVE).toString());
		
		String contractBillId = (String) getUIContext().get("contractBillId");
		IObjectPK pk = new ObjectUuidPK(contractBillId);
		contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(pk, getContractBillSelector());
	}

	private SelectorItemCollection getContractBillSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.id");
		sic.add("programmingContract.number");
		sic.add("curProject.number");
		sic.add("curProject.name");
		return sic;
	}

	public void storeFields() {
		super.storeFields();
		if (btnBySchedule.isSelected()) {
			editData.setMode(PayPlanModeEnum.BYSCHEDULE);

			//保存节点
			for (int i = 0; i < tblBySchedule.getRowCount(); i++) {
				IRow row = tblBySchedule.getRow(i);
				ConPayPlanByScheduleInfo info = (ConPayPlanByScheduleInfo) row.getUserObject();
				info.getTask().clear();
				Object obj = tblBySchedule.getCell(i, COL_TASK).getValue();
				if (obj instanceof FDCScheduleTaskInfo) {
					ConPayPlanByScheduleTaskInfo bInfo = new ConPayPlanByScheduleTaskInfo();
					bInfo.setTask((FDCScheduleTaskInfo) obj);
					info.getTask().add(bInfo);
				} else if (obj instanceof Object[]) {
					Object[] object = (Object[]) obj;
					for (int j = 0; j < object.length; j++) {
						ConPayPlanByScheduleTaskInfo bInfo = new ConPayPlanByScheduleTaskInfo();
						bInfo.setTask((FDCScheduleTaskInfo) object[j]);
						info.getTask().add(bInfo);
					}
				}
			}

		} else if (btnByMonth.isSelected()) {
			editData.setMode(PayPlanModeEnum.BYMONTH);
		}

	}

	protected void initTable() throws BOSException {
		initTableBySchedule();

		//		initTableByMonth();
	}

	protected void initTableBySchedule() throws BOSException {
		tblBySchedule.checkParsed();
		
		tblBySchedule.getColumn(COL_COST_ACCOUNT).getStyleAttributes().setHided(true);
		tblBySchedule.getColumn(COL_CAL_STANDARD).getStyleAttributes().setHided(true);
		tblBySchedule.getColumn(COL_TASK_MEASURE_AMOUNT).getStyleAttributes().setHided(true);
		tblBySchedule.getColumn(COL_TASK_SETTLE_AMOUNT).getStyleAttributes().setHided(true);
		

		KDComboBox comboCalType = PayPlanClientUtil.initComboBoxCell(tblBySchedule, COL_CAL_TYPE);
		comboCalType.addItems(CalTypeEnum.getEnumList().toArray());

		KDComboBox comboCalStandard = PayPlanClientUtil.initComboBoxCell(tblBySchedule, COL_CAL_STANDARD);
		comboCalStandard.addItems(CalStandardEnum.getEnumList().toArray());

		KDComboBox comboWriteOffType = PayPlanClientUtil.initComboBoxCell(tblBySchedule, COL_WRITE_OFF_TYPE);
		comboWriteOffType.addItems(PrepayWriteOffEnum.getEnumList().toArray());

		KDComboBox comboPaymentType = PayPlanClientUtil.initComboBoxCell(tblBySchedule, COL_PAYMENT_TYPE);
		PaymentTypeCollection coll = PaymentTypeFactory.getRemoteInstance().getPaymentTypeCollection("order by number");
		comboPaymentType.addItems(coll.toArray());

		KDFormattedTextField txt = PayPlanClientUtil.initFormattedTextCell(tblBySchedule, COL_PAY_SCALE, 2);
		txt.setMinimumValue(new BigDecimal(1));
		txt.setMaximumValue(new BigDecimal(100));
		txt.setNegatived(false);
		txt.setDataType(BigDecimal.class);

		txt = PayPlanClientUtil.initFormattedTextCell(tblBySchedule, COL_DELAY_DAY, 0);
		txt.setMinimumValue(new Integer(0));
		txt.setDataType(Integer.class);
//		tblBySchedule.getColumn(COL_DELAY_DAY).setRenderer(new DelayDateCellRender());

		prmtCostAccountBySchedule = PayPlanClientUtil.initF7Cell(tblBySchedule, COL_COST_ACCOUNT);
		CostAccountPromptBox selector = new CostAccountPromptBox(this);

		prmtCostAccountBySchedule.setSelector(selector);
		CostAccountCellRender render = new CostAccountCellRender();
		render.setFormat(new BizDataFormat("$name$"));
		tblBySchedule.getColumn(COL_COST_ACCOUNT).setRenderer(render);

		prmtTask = PayPlanClientUtil.initF7Cell(tblBySchedule, COL_TASK);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("schedule.project.id", contractBillInfo.getCurProject().getId().toString());
		//		filter.appendFilterItem("businessType.id", BIZ_TYPE_CASHFLOW);
		prmtTask.setDisplayFormat("$name$");
		prmtTask.setEditFormat("$name$");
		prmtTask.setCommitFormat("$id$");
		prmtTask.setEnabledMultiSelection(false);
		prmtTask.setSelector(new F7ScheduleTaskPromptBox(contractBillInfo.getCurProject().getId().toString(), false));
		tblBySchedule.getColumn(COL_TASK).setRenderer(new FDCScheduleTaskCellRender());

		tblBySchedule.getColumn(COL_PAYMENT_TYPE).setMergeable(true);
		tblBySchedule.getColumn(COL_PAYMENT_TYPE).setGroup(true);
		tblBySchedule.getMergeManager().setMergeMode(KDTMergeManager.SPECIFY_MERGE);
		tblBySchedule.getMergeManager().setDataMode(KDTMergeManager.DATA_UNIFY);

		tblBySchedule.addKDTEditListener(new KDTEditAdapter() {

			public void editStopped(KDTEditEvent e) {
				final KDTEditEvent evt = e;
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						PayPlanClientUtil.doAutoMerge(tblBySchedule, COL_PAYMENT_TYPE, evt);
					}
				});

				tblBySchedule_editStopped(e);
			}

			public void editStarting(KDTEditEvent e) {
				//				tblBySchedule_editStarting(e);
			}

		});

		PayPlanClientUtil.initDateCell(tblBySchedule, COL_BEGIN_DATE);
		PayPlanClientUtil.initDateCell(tblBySchedule, COL_END_DATE);
		tblBySchedule.getColumn(COL_BEGIN_DATE).setRenderer(new BeginDateCellRender());
		tblBySchedule.getColumn(COL_END_DATE).setRenderer(new EndDateCellRender());

		PayPlanClientUtil.initDateCell(tblBySchedule, COL_PLAN_PAY_DATE);
		tblBySchedule.getColumn(COL_PLAN_PAY_DATE).setRenderer(new PlanPayDateCellRender());
		PayPlanClientUtil.initFormattedTextCell(tblBySchedule, COL_TASK_MEASURE_AMOUNT, 2);
		PayPlanClientUtil.initFormattedTextCell(tblBySchedule, COL_TASK_SETTLE_AMOUNT, 2);
		PayPlanClientUtil.initFormattedTextCell(tblBySchedule, COL_PLAN_PAY_AMOUNT, 2);

		tblBySchedule.getColumn(COL_CAL_TYPE).setRequired(true);
		tblBySchedule.getColumn(COL_WRITE_OFF_TYPE).setRequired(false);
		tblBySchedule.getColumn(COL_PAYMENT_TYPE).setRequired(true);
		//		tblBySchedule.getColumn(COL_TASK).setRequired(true);
		tblBySchedule.getColumn(COL_PAY_SCALE).setRequired(true);
		
		if(prePayParam == 0){
			initLockColumn(tblBySchedule.getColumn(COL_WRITE_OFF_TYPE));
		}else if(prePayParam == 1){
		}else if(prePayParam == 2){
			initLockColumn(tblBySchedule.getColumn(COL_WRITE_OFF_TYPE));
		}else if(prePayParam == 3){
		}

		initLockColumn(tblBySchedule.getColumn(COL_BEGIN_DATE));
		initLockColumn(tblBySchedule.getColumn(COL_END_DATE));
		//		initLockColumn(tblBySchedule.getColumn(COL_PLAN_PAY_DATE));
		initLockColumn(tblBySchedule.getColumn(COL_PLAN_PAY_AMOUNT));
		initLockColumn(tblBySchedule.getColumn(COL_CAL_TYPE));
	}

	protected void initLockColumn(IColumn column) {
		if (column != null) {
			column.getStyleAttributes().setLocked(true);
			column.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		}
	}

	/*

		protected void initTableByMonth() {
			tblByMonth.checkParsed();

			prmtCostAccountByMonth = PayPlanClientUtil.initF7Cell(tblByMonth, COL_COST_ACCOUNT);
			CostAccountPromptBox selectorByMonth = new CostAccountPromptBox(this);
			prmtCostAccountByMonth.setSelector(selectorByMonth);

			CostAccountCellRender render = new CostAccountCellRender();
			render.setFormat(new BizDataFormat("$name$"));
			tblByMonth.getColumn(COL_COST_ACCOUNT).setRenderer(render);
			
			
			PayPlanClientUtil.initTextColumn(tblByMonth, "paymentItem", 100);
			PayPlanClientUtil.initTextColumn(tblByMonth, "usage", 500);
			PayPlanClientUtil.initFormattedTextCell(tblByMonth, "payAmount", 2);
			PayPlanClientUtil.initDateCell(tblByMonth, COL_BEGIN_DATE);
			PayPlanClientUtil.initDateCell(tblByMonth, COL_END_DATE);
			PayPlanClientUtil.initRequiedColumn(tblByMonth,new String[]{COL_BEGIN_DATE,COL_END_DATE,"payAmount",COL_COST_ACCOUNT,"paymentItem"},true);
		}
	 */
	protected void tblBySchedule_editStarting(KDTEditEvent e) {
		IRow row = tblBySchedule.getRow(e.getRowIndex());

		ConPayPlanByScheduleInfo info = (ConPayPlanByScheduleInfo) row.getUserObject();

		if (info.getSrcID() != null) {
			e.setCancel(true);
			return;
		}

		if (e.getColIndex() == tblBySchedule.getColumnIndex(COL_WRITE_OFF_TYPE)) {
			Object value = row.getCell(COL_PAYMENT_TYPE).getValue();
			if (value instanceof PaymentTypeInfo) {
				PaymentTypeInfo pInfo = (PaymentTypeInfo) value;
				if (!pInfo.isPreType()) {
					e.setCancel(true);
					return;
				}
			} else {
				e.setCancel(true);
				return;
			}
		}
	}

	protected void tblBySchedule_editStopped(KDTEditEvent e) {
		IRow row = tblBySchedule.getRow(e.getRowIndex());

		if (e.getColIndex() == tblBySchedule.getColumnIndex(COL_PAYMENT_TYPE)) {
			Object value = row.getCell(COL_PAYMENT_TYPE).getValue();
			if (value instanceof PaymentTypeInfo) {
				PaymentTypeInfo pInfo = (PaymentTypeInfo) value;
				if (!pInfo.isPreType()) {
					row.getCell(COL_WRITE_OFF_TYPE).setValue(null);
					row.getCell(COL_WRITE_OFF_TYPE).getStyleAttributes().setLocked(true);
					return;
				}else{
					if (prePayParam == 0) {
						row.getCell(COL_WRITE_OFF_TYPE).setValue(PrepayWriteOffEnum.ONCE);
					} else if (prePayParam == 1) {
						row.getCell(COL_WRITE_OFF_TYPE).setValue(PrepayWriteOffEnum.ONCE);
					} else if (prePayParam == 2) {
						row.getCell(COL_WRITE_OFF_TYPE).setValue(PrepayWriteOffEnum.BATCH);
					} else if (prePayParam == 3) {
						row.getCell(COL_WRITE_OFF_TYPE).setValue(PrepayWriteOffEnum.BATCH);
					}
					return;
				}
			}
		} else if (e.getColIndex() == tblBySchedule.getColumnIndex(COL_TASK)) {
			Object value = row.getCell(COL_TASK).getValue();
			if (value != null) {
								row.getCell(COL_CAL_TYPE).getStyleAttributes().setLocked(false);
				row.getCell(COL_CAL_TYPE).getStyleAttributes().setBackground(Color.WHITE);

								row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setLocked(true);
				row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				
				
				row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setLocked(true);
				row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			} else {
				row.getCell(COL_CAL_TYPE).setValue(CalTypeEnum.TIME);
								row.getCell(COL_CAL_TYPE).getStyleAttributes().setLocked(true);
				row.getCell(COL_CAL_TYPE).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

								row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setLocked(false);
				row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setBackground(Color.WHITE);
				
				row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setLocked(false);
				row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);

			}
		}

		BigDecimal planPayAmount = FDCHelper.divide(
				FDCHelper.multiply(contractBillInfo.getAmount(), row.getCell(
				COL_PAY_SCALE).getValue()), FDCHelper.ONE_HUNDRED);

		if (!FDCHelper.isZero(planPayAmount)) {
			row.getCell(COL_PLAN_PAY_AMOUNT).setValue(planPayAmount);
		}
		
		updateAmount(contractBillInfo.getAmount());
	}
	
	
	public void updateAmount(BigDecimal amount){
		BigDecimal sumAmount = FDCHelper.ZERO;
		BigDecimal sumpayScal = FDCHelper.ZERO;
			for (int i = 0; i < tblBySchedule.getRowCount(); i++) {
				IRow row = tblBySchedule.getRow(i);
				Object paymentType = row.getCell(COL_PAYMENT_TYPE).getValue();
				if(paymentType!=null && !((PaymentTypeInfo)paymentType).isPreType()){
					sumpayScal = FDCHelper.add(sumpayScal, row.getCell(COL_PAY_SCALE).getValue());
				}

				
					BigDecimal planPayAmount = FDCHelper.divide(
							FDCHelper.multiply(amount, row.getCell(COL_PAY_SCALE).getValue()),
							FDCHelper.ONE_HUNDRED);
					if (i == tblBySchedule.getRowCount() - 1 && FDCHelper.compareTo(sumpayScal, FDCHelper.ONE_HUNDRED)==0) {
						row.getCell(COL_PLAN_PAY_AMOUNT).setValue(FDCHelper.subtract(amount, sumAmount));
					}
					
					if(paymentType!=null && !((PaymentTypeInfo)paymentType).isPreType()){
						sumAmount = FDCHelper.add(sumAmount, planPayAmount);
					}

				}
	}


	public void loadFields() {
		super.loadFields();

		if (PayPlanModeEnum.BYSCHEDULE.equals(editData.getMode())) {
			btnBySchedule.setSelected(true);

			for (int i = 0; i < tblBySchedule.getRowCount(); i++) {
				IRow row = tblBySchedule.getRow(i);
				ConPayPlanByScheduleInfo info = (ConPayPlanByScheduleInfo) row.getUserObject();

				List lst = new ArrayList();
				if (info.getTask() != null && info.getTask().size() > 0) {
					for (int j = 0; j < info.getTask().size(); j++) {
						lst.add(info.getTask().get(j).getTask());
					}
				}
				row.getCell(COL_TASK).setValue(lst.toArray());
				
				if (lst.size() == 0) {
					row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
					row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setLocked(false);
				} else {
					row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
					row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setLocked(true);
				}

					row.getCell(COL_COST_ACCOUNT).getStyleAttributes().setBackground(Color.WHITE);
					row.getCell(COL_TASK_MEASURE_AMOUNT).getStyleAttributes().setBackground(Color.WHITE);

				Object pObj = row.getCell(COL_PAYMENT_TYPE).getValue();
				if (pObj instanceof PaymentTypeInfo) {
					PaymentTypeInfo pInfo = (PaymentTypeInfo) pObj;
					if (!pInfo.isPreType()) {
						row.getCell(COL_WRITE_OFF_TYPE).setValue(null);
						row.getCell(COL_WRITE_OFF_TYPE).getStyleAttributes().setLocked(true);
					}
				}
				
				

			}
		}

		if (tblBySchedule.getRowCount() > 0) {
		PayPlanClientUtil.doAutoMerge(tblBySchedule, "paymentType", new KDTEditEvent(tblBySchedule, null, tblBySchedule.getRow(0)
					.getCell("paymentType").getValue(), 0, tblBySchedule.getColumnIndex("paymentType"), false, KDTStyleConstants.BODY_ROW));
		}
		
		

		updateTable();

		loadDataTable();
	}

	protected void loadDataTable() {

		tblPayPlanLst.removeColumns();
		tblPayPlanLst.checkParsed();
		ConPayPlanDataCollection datas = editData.getData();

		if (datas != null && datas.size() > 0) {
			String[] columnKeys = new String[datas.size() + 1];
			Object[] head = new Object[datas.size() + 1];
			Object[][] body = new Object[1][datas.size() + 1];

			for (int i = 1; i <= datas.size(); i++) {
				ConPayPlanDataInfo info = datas.get(i - 1);
				columnKeys[i] = "" + info.getPayMonth();
				head[i] = "" + info.getPayMonth() / 100 + "年" + info.getPayMonth() % 100 + "月";
				body[0][i] = info.getPayAmount();
			}

			columnKeys[0] = "payMonth";
			head[0] = "付款时间";
			body[0][0] = "付款金额";

			KDTableHelper.initTable(tblPayPlanLst, columnKeys, head, body);

			for (int i = 1; i <= datas.size(); i++) {
				PayPlanClientUtil.initFormattedTextCell(tblPayPlanLst, columnKeys[i], 2);
			}

//						ConPayPlanDatapCollection datap = editData.getDatap();
//							IRow row = tblPayPlanLst.addRow();
//							row.getCell(0).setValue("模型计算金额");
//							for (int i = 0; i < datap.size(); i++) {
//								ConPayPlanDatapInfo datapInfo = datap.get(i);
//								String key = "" + datapInfo.getPayMonth();
//								if (row.getCell(key) != null) {
//									row.getCell(key).setValue(datapInfo.getPayAmount());
//								}
//							}
//				
//							ConPayPlanDataACollection dataa = editData.getDataA();
//				row = tblPayPlanLst.addRow();
//				row.getCell(0).setValue("实际发生金额");
//				for (int i = 0; i < dataa.size(); i++) {
//					ConPayPlanDataAInfo datapInfo = dataa.get(i);
//					String key = "" + datapInfo.getPayMonth();
//					if (row.getCell(key) != null) {
//						row.getCell(key).setValue(datapInfo.getPayAmount());
//					}
//				}

				int settleMonth = getPayMonthKeyByDate(editData.getSettleMonth() == null ? new Date() : editData.getSettleMonth());
				for (int i = 1; i <= datas.size(); i++) {
					ConPayPlanDataInfo info = datas.get(i - 1);
					if (settleMonth >= info.getPayMonth()) {
						tblPayPlanLst.getColumn("" + info.getPayMonth()).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
					}
							}
			tblPayPlanLst.getStyleAttributes().setLocked(true);

		}
		tblPayPlanLst.reLayoutAndPaint();

	}

	protected int getPayMonthKeyByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR) * 100 + calendar.get(Calendar.MONTH) + 1;
	}

	public void checkTableColumnEmpty(KDTable table, String colName) throws EASBizException {
		for (int i = 0; i < table.getRowCount(); i++) {
			Object obj = table.getCell(i, colName).getValue();
			if (obj == null) {
				throw new EASBizException(new NumericExceptionSubItem("021", "第" + (i + 1) + "行的"
						+ table.getHeadRow(0).getCell(colName).getValue() + "不能为空。"));
			}
			if (obj instanceof Object[]) {
				Object[] objs = (Object[]) obj;
				if (objs.length < 1) {
					throw new EASBizException(new NumericExceptionSubItem("021", "第" + (i + 1) + "行的"
							+ table.getHeadRow(0).getCell(colName).getValue() + "不能为空。"));
				}
			}
		}
	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);

		if (btnByMonth.isSelected()) {
			checkTableColumnEmpty(tblByMonth, "paymentItem");
			checkTableColumnEmpty(tblByMonth, COL_BEGIN_DATE);
			checkTableColumnEmpty(tblByMonth, COL_END_DATE);
			checkTableColumnEmpty(tblByMonth, "payAmount");
			checkTableColumnEmpty(tblByMonth, COL_COST_ACCOUNT);

			BigDecimal sum = FDCHelper.ZERO;
			for (int i = 0; i < tblByMonth.getRowCount(); i++) {
				IRow row = tblByMonth.getRow(i);
				sum = FDCHelper.add(sum, row.getCell("payAmount").getValue());
			}

			if (contractBillInfo.getAmount() != null && sum.compareTo(contractBillInfo.getAmount()) > 0) {
				MsgBox.showInfo("合同付款规划金额之和不能大于合同金额" + contractBillInfo.getAmount() + "，当前合同付款规划金额之和为:" + sum.toString());
				SysUtil.abort();
			}
		} else if (btnBySchedule.isSelected()) {
			checkTableColumnEmpty(tblBySchedule, COL_PAYMENT_TYPE);
			//			checkTableColumnEmpty(tblBySchedule, COL_TASK);
			//			checkTableColumnEmpty(tblBySchedule, COL_COST_ACCOUNT);
			checkTableColumnEmpty(tblBySchedule, COL_CAL_TYPE);
			//			checkTableColumnEmpty(tblBySchedule, COL_BEGIN_DATE);
			//			checkTableColumnEmpty(tblBySchedule, COL_END_DATE);
			checkTableColumnEmpty(tblBySchedule, COL_CAL_STANDARD);
			checkTableColumnEmpty(tblBySchedule, COL_PLAN_PAY_AMOUNT);
			checkTableColumnEmpty(tblBySchedule, COL_PAY_SCALE);

			BigDecimal sum = FDCHelper.ZERO;
			for (int i = 0; i < tblBySchedule.getRowCount(); i++) {
				IRow row = tblBySchedule.getRow(i);
				Object value = row.getCell(COL_PAYMENT_TYPE).getValue();
				if (value instanceof PaymentTypeInfo) {
					PaymentTypeInfo pInfo = (PaymentTypeInfo) value;
					if (pInfo.isPreType()) {
						continue;
					}
				}
				sum = FDCHelper.add(sum, row.getCell(COL_PLAN_PAY_AMOUNT).getValue());
			}


			for (int i = 0; i < tblBySchedule.getRowCount(); i++) {
				IRow row = tblBySchedule.getRow(i);
				PaymentTypeInfo info = (PaymentTypeInfo) row.getCell(COL_PAYMENT_TYPE).getValue();
				if (info.isPreType()) {
					checkTableCellEmpty(tblBySchedule, i, COL_WRITE_OFF_TYPE, null);
				}
			}
		}
	}

	public void checkTableCellEmpty(KDTable table, int rowIndex, String colName, String msg) throws EASBizException {
		Object obj = table.getCell(rowIndex, colName).getValue();
		if (obj == null) {
			throw new EASBizException(new NumericExceptionSubItem("021", (msg == null ? "" : msg) + "第" + (rowIndex + 1) + "行的"
					+ table.getHeadRow(0).getCell(colName).getValue() + "不能为空。"));
		}
		if (obj instanceof Object[]) {
			Object[] objs = (Object[]) obj;
			if (objs.length < 1) {
				throw new EASBizException(new NumericExceptionSubItem("021", (msg == null ? "" : msg) + "第" + (rowIndex + 1) + "行的"
						+ table.getHeadRow(0).getCell(colName).getValue() + "不能为空。"));
			}
		}
	}

	protected boolean isContinueAddNew() {
		return false;
	}

	protected IObjectValue createNewData() {
		ConPayPlanInfo info = new ConPayPlanInfo();
		info.setMode(PayPlanModeEnum.BYSCHEDULE);

		info.setContractBill(contractBillInfo);

		return info;
	}

	protected IObjectValue createNewEntryData() {
		if (btnByMonth.isSelected()) {
			ConPayPlanByMonthInfo info = new ConPayPlanByMonthInfo();
			return info;

		} else if (btnBySchedule.isSelected()) {
			ConPayPlanByScheduleInfo info = new ConPayPlanByScheduleInfo();
			info.setCalType(CalTypeEnum.TIME);
			info.setCalStandard(CalStandardEnum.CONTRACTAMOUNT);
			if (contractBillInfo.getProgrammingContract() != null) {
				info.setProgrammingID(contractBillInfo.getProgrammingContract().getId().toString());
			}
			return info;
		}

		return null;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("*");
		sic.add("Data.*");
		sic.add("Datap.*");
		sic.add("DataA.*");
		sic.add("BySchedule.Task.task.name");
		sic.add("BySchedule.Task.task.start");
		sic.add("BySchedule.Task.task.end");
		sic.add("BySchedule.Dataz.*");
		sic.add("BySchedule.*");
		sic.add("ByMonth.*");
		sic.add("ByBuilding.curBuilding.*");
		sic.add("ByBuilding.*");
		sic.add("ByBuilding.Datas.*");
		return sic;
	}

	protected void initWorkButton() {
		super.initWorkButton();

		chkMenuItemSubmitAndAddNew.setVisible(false);

		btnAddnewLine = new KDWorkButton();
		btnInsertLine = new KDWorkButton();
		btnRemoveLines = new KDWorkButton();
		btnCopyLines = new KDWorkButton();

		btnAddnewLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddnewLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnInsertLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					actionInsertLine_actionPerformed(arg0);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});

		btnRemoveLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnCopyLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionCopyLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		setButtonStyle(btnAddnewLine, "新增行", "imgTbtn_addline");
		setButtonStyle(btnRemoveLines, "删除行", "imgTbtn_deleteline");
		setButtonStyle(btnInsertLine, "插入行", "imgTbtn_insert");
		//		setButtonStyle(btnCopyLines, "复制行", "imgTbtn_copy");

		actionByBuilding.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_analyze"));

		actionByMonth.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_autoarrange"));

		actionImportPayPlan.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_replace"));

		actionMonthSettle.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_allfinitialize"));
		actionContractFullInfo.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_execute"));
		actionContractView.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sequencecheck"));

		actionAddNew.setVisible(false);
		actionSave.setVisible(false);
		actionRemove.setVisible(false);
		actionPre.setVisible(false);
		actionFirst.setVisible(false);
		actionNext.setVisible(false);
		actionLast.setVisible(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		actionCopy.setVisible(false);
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		actionAttachment.setVisible(true);
		
		actionMonthSettle.setVisible(true);

	}

	protected void initDataStatus() {
		super.initDataStatus();

		if (OprtState.VIEW.equals(getOprtState())) {
			btnAddnewLine.setEnabled(false);
			btnRemoveLines.setEnabled(false);
			btnInsertLine.setEnabled(false);
			btnCopyLines.setEnabled(false);
			btnBySchedule.setEnabled(false);
			btnByMonth.setEnabled(false);
		} else {
			btnAddnewLine.setEnabled(true);
			btnRemoveLines.setEnabled(true);
			btnInsertLine.setEnabled(true);
			btnCopyLines.setEnabled(true);
			btnBySchedule.setEnabled(true);
			btnByMonth.setEnabled(true);
		}
	}

	


	private void setButtonStyle(KDWorkButton button, String text, String icon) {
		button.setText(text);
		button.setToolTipText(text);
		button.setVisible(true);
		button.setIcon(EASResource.getIcon(icon));
		contProgramming.addButton(button);
	}

	protected void actionCopyLine_actionPerformed(ActionEvent e) {
		KDTable tblMain = getTable();

		IRow row = null;
		if (tblMain.getSelectManager().size() > 0) {
			int top = tblMain.getSelectManager().get().getTop();
			IRow oldRow = tblMain.getRow(top);
			IObjectValue obj = (IObjectValue) oldRow.getUserObject();
			dataBinder.storeLineFields(tblMain, oldRow, obj);
			row = tblMain.addRow(top + 1);
			dataBinder.loadLineFields(tblMain, row, obj);
		} else {
			row = tblMain.addRow();
			IObjectValue obj = createNewEntryData();
			dataBinder.loadLineFields(tblMain, row, obj);
		}

	}

	protected void actionRemoveLine_actionPerformed(ActionEvent e) {
		KDTable tblMain = getTable();
		if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0) {
			MsgBox.showInfo("请选中行！");
			SysUtil.abort();
		}

		int index = tblMain.getSelectManager().getActiveRowIndex();
		if (FDCMsgBox.isOk(FDCMsgBox.showConfirm2("确认删除？"))) {
			tblMain.removeRow(index);
		}
	}

	protected void actionInsertLine_actionPerformed(ActionEvent arg0) {
		KDTable tblMain = getTable();
		IRow row = null;
		if (tblMain.getSelectManager().size() > 0) {
			int top = tblMain.getSelectManager().get().getTop();
			row = tblMain.addRow(top);
		} else {
			row = tblMain.addRow();
		}
		row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setLocked(false);
		row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		IObjectValue obj = createNewEntryData();
		dataBinder.loadLineFields(tblMain, row, obj);
	}

	protected void actionAddnewLine_actionPerformed(ActionEvent e) {
		KDTable tblMain = getTable();
		IRow row = tblMain.addRow();
		IObjectValue obj = createNewEntryData();
		row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setLocked(false);
		row.getCell(COL_PLAN_PAY_DATE).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		dataBinder.loadLineFields(tblMain, row, obj);
		
	}




	public void actionContractFullInfo_actionPerformed(ActionEvent e) throws Exception {
		super.actionContractFullInfo_actionPerformed(e);

		UIContext uiContext = new UIContext(this);

		uiContext.put(UIContext.ID, this.contractBillInfo.getId().toString());

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractFullInfoUI.class.getName(), uiContext, null,
				OprtState.VIEW);

		uiWindow.show();
	}

	public void actionContractView_actionPerformed(ActionEvent e) throws Exception {
		super.actionContractView_actionPerformed(e);

		UIContext uiContext = new UIContext(this);

		uiContext.put(UIContext.ID, this.contractBillInfo.getId().toString());

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillEditUI.class.getName(), uiContext, null,
				OprtState.VIEW, WinStyle.SHOW_KINGDEELOGO);
		ContractBillEditUI ui = (ContractBillEditUI) uiWindow.getUIObject();
		//		ui.showOnlyEconInfo();
		uiWindow.show();
	}

	private KDTable getTable() {
		if (btnByMonth.isSelected()) {
			return tblByMonth;

		} else if (btnBySchedule.isSelected()) {
			return tblBySchedule;
		}

		return null;
	}

	/**
	 * 描述：
	 * @Author：keyan_zhao
	 * @CreateTime：2013-8-27
	 */
	private void reSetEntryData() {
		if (editData.getBySchedule() != null && editData.getBySchedule().size() > 0) {
			tblBySchedule.removeRows();
			for (int i = 0; i < editData.getBySchedule().size(); i++) {
				dataBinder.loadLineFields(tblBySchedule, tblBySchedule.addRow(), (IObjectValue) editData.getBySchedule().getObject(i));
			} 
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ConPayPlanFactory.getRemoteInstance();
	}

	protected void btnByMonth_actionPerformed(ActionEvent e) throws Exception {
		super.btnByMonth_actionPerformed(e);

		if (MsgBox.isYes(MsgBox.showConfirm2New(this, "切换通用零星合同将清空表格的数据，是否继续？"))) {
			tblBySchedule.removeRows();
			updateTable();
		} else {
			btnBySchedule.setSelected(true);
		}

	}

	protected void btnBySchedule_actionPerformed(ActionEvent e) throws Exception {
		super.btnBySchedule_actionPerformed(e);

		if (MsgBox.isYes(MsgBox.showConfirm2New(this, "切换通用零星合同将清空表格的数据，是否继续？"))) {
			tblByMonth.removeRows();
			updateTable();
		} else {
			btnByMonth.setSelected(true);
		}
	}

	protected void updateTable() {
		if (btnBySchedule.isSelected()) {
			contProgramming.getContentPane().remove(tblByMonth);
			contProgramming.getContentPane().remove(tblBySchedule);
			contProgramming.getContentPane().setLayout(new BorderLayout());
			contProgramming.getContentPane().add(this.tblBySchedule, BorderLayout.CENTER);
			contProgramming.getContentPane().updateUI();
			contProgramming.validate();
			tblBySchedule.reLayoutAndPaint();
		}
	}

	/**
	 * 描述：导入合同关联合约规划的付款规划数据
	 */
	public void actionImportPayPlan_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportPayPlan_actionPerformed(e);
		IObjectPK pk = ConPayPlanFactory.getRemoteInstance().importPayPlan(contractBillInfo.getId().toString(),false);

		getUIContext().put(UIContext.ID, pk.toString());
		setOprtState(OprtState.EDIT);
		setDataObject(getValue(pk));
		loadFields();

	}

	class CostAccountCellRender extends ObjectValueRender {
		public String getText(Object obj) {
			if (obj == null)
				return null;
			if (obj instanceof CostAccountInfo) {
				if (((CostAccountInfo) obj).getName() == null) {
					return null;
				}
				return ((CostAccountInfo) obj).getName();
			}
			return defaultObjectName;
		}
	}
	

	class FDCScheduleTaskCellRender extends ObjectValueRender {
		BizDataFormat formater = new BizDataFormat("$name$");

		public String getText(Object obj) {
			if (obj instanceof Object[]) {
				Object[] objs = (Object[]) obj;
				StringBuffer txt = new StringBuffer();
				for (int i = 0; i < objs.length; i++) {
					txt.append(formater.format(objs[i]));
					txt.append(";");
				}
				return txt.toString();
			} else {
				return super.getText(obj);
			}
		}
	}

	class PlanPayDateCellRender extends TextIconRender {
		public void draw(Graphics graphics, Shape clip, Object object, Style cellStyle, Object extObject) {
			if (extObject instanceof RenderObject) {
				RenderObject rObj = (RenderObject) extObject;

				if (CalTypeEnum.TIMERANGE.equals(tblBySchedule.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_CAL_TYPE))
						.getValue())) {
					tblBySchedule.getCell(rObj.getRowIndex(), rObj.getColIndex()).setValue(null);
					object = "按月支付";
				} else if (tblBySchedule.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_TASK)).getValue() == null) {
					tblBySchedule.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_END_DATE)).setValue(null);
					tblBySchedule.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_BEGIN_DATE)).setValue(null);
				} else if (CalTypeEnum.TIME.equals(tblBySchedule.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_CAL_TYPE))
						.getValue())) {
					if (tblBySchedule.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_END_DATE)).getValue() != null
							&& tblBySchedule.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_DELAY_DAY)).getValue() != null) {

						Date endDate = (Date) tblBySchedule.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_END_DATE))
								.getValue();
						Integer delayDate = (Integer) tblBySchedule
								.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_DELAY_DAY)).getValue();
						Date payDate = DateTimeUtils.addDay(endDate, delayDate.longValue());
						tblBySchedule.getCell(rObj.getRowIndex(), rObj.getColIndex()).setValue(payDate);
					}

				} else if (tblBySchedule.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_TASK)).getValue() != null) {
					Date endDate = null;
					Object obj = tblBySchedule.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_TASK)).getValue();
					if (obj instanceof Object[]) {
						Object[] objs = (Object[]) obj;
						for (int j = 0; j < objs.length; j++) {
							FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) objs[j];
							if (endDate == null || info.getEnd().after(endDate)) {
								endDate = info.getEnd();
							}
						}
					}
					if (endDate != null) {
						Integer delayDate = (Integer) tblBySchedule
								.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_DELAY_DAY)).getValue();
						Date payDate = DateTimeUtils.addDay(endDate, delayDate.longValue());
						tblBySchedule.getCell(rObj.getRowIndex(), rObj.getColIndex()).setValue(payDate);
					}
				}
				super.draw(graphics, clip, object, cellStyle, extObject);
			} else {
				super.draw(graphics, clip, object, cellStyle, extObject);
			}
		}
	}

	class BeginDateCellRender extends TextIconRender {
		public void draw(Graphics graphics, Shape clip, Object object, Style cellStyle, Object extObject) {
			if (extObject instanceof RenderObject) {
				RenderObject rObj = (RenderObject) extObject;

				Object obj = tblBySchedule.getCell(rObj.getRowIndex(), COL_TASK).getValue();
				if (obj instanceof FDCScheduleTaskInfo) {
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) obj;
					tblBySchedule.getCell(rObj.getRowIndex(), rObj.getColIndex()).setValue(task.getStart());
				} else if (obj instanceof Object[]) {
					Object[] objs = (Object[]) obj;
					if (objs.length == 1 && objs[0] instanceof FDCScheduleTaskInfo) {
						FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) objs[0];
						tblBySchedule.getCell(rObj.getRowIndex(), rObj.getColIndex()).setValue(task.getStart());
					} else if (objs.length > 1) {
						tblBySchedule.getCell(rObj.getRowIndex(), rObj.getColIndex()).setValue(null);
						object = null;
					}
				}
				super.draw(graphics, clip, object, cellStyle, extObject);
			} else {
				super.draw(graphics, clip, object, cellStyle, extObject);
			}
		}
	}

	class EndDateCellRender extends TextIconRender {
		public void draw(Graphics graphics, Shape clip, Object object, Style cellStyle, Object extObject) {
			if (extObject instanceof RenderObject) {
				RenderObject rObj = (RenderObject) extObject;

				Object obj = tblBySchedule.getCell(rObj.getRowIndex(), COL_TASK).getValue();
				if (obj instanceof FDCScheduleTaskInfo) {
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) obj;
					tblBySchedule.getCell(rObj.getRowIndex(), rObj.getColIndex()).setValue(task.getEnd());
				} else if (obj instanceof Object[]) {
					Object[] objs = (Object[]) obj;
					if (objs.length == 1 && objs[0] instanceof FDCScheduleTaskInfo) {
						FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) objs[0];
						tblBySchedule.getCell(rObj.getRowIndex(), rObj.getColIndex()).setValue(task.getEnd());
					} else if (objs.length > 1) {
						tblBySchedule.getCell(rObj.getRowIndex(), rObj.getColIndex()).setValue(null);
						object = null;
					}
				}
				super.draw(graphics, clip, object, cellStyle, extObject);
			} else {
				super.draw(graphics, clip, object, cellStyle, extObject);
			}
		}
	}

//	class DelayDateCellRender extends TextIconRender {
//		public void draw(Graphics graphics, Shape clip, Object object, Style cellStyle, Object extObject) {
//			if (extObject instanceof RenderObject) {
//				RenderObject rObj = (RenderObject) extObject;
//				
//				Object value = tblBySchedule.getCell(rObj.getRowIndex(), COL_TASK).getValue();
//				boolean isLock = false;
//				if (value instanceof Object[]) {
//					Object[] objs = (Object[]) value;
//					if (objs.length == 0) {
//						isLock = true;
//					}
//				} else if (value == null) {
//					isLock = true;
//				}
//				
//				if (CalTypeEnum.TIMERANGE.equals(tblBySchedule.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_CAL_TYPE))
//						.getValue())) {
//					tblBySchedule.getCell(rObj.getRowIndex(), COL_DELAY_DAY).setValue(0);
//										tblBySchedule.getCell(rObj.getRowIndex(), COL_DELAY_DAY).getStyleAttributes().setLocked(true);
//					object = "无";
//				} 
////				else if (isLock) {
////					object = "无";
////										tblBySchedule.getCell(rObj.getRowIndex(), COL_DELAY_DAY).getStyleAttributes().setLocked(true);
////				} 
//				else {
//										tblBySchedule.getCell(rObj.getRowIndex(), COL_DELAY_DAY).getStyleAttributes().setLocked(false);
//				}
//				super.draw(graphics, clip, object, cellStyle, extObject);
//			} else {
//				super.draw(graphics, clip, object, cellStyle, extObject);
//			}
//		}
//	}

	

	/**
	 * 描述：月结
	 */
	public void actionMonthSettle_actionPerformed(ActionEvent e) throws Exception {
		super.actionMonthSettle_actionPerformed(e);
		
		UIContext uiContext = new UIContext(this);
		
		uiContext.put("PayPlan", editData);
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				ConPayPlanSettleUI.class.getName(), uiContext, null,
				this.oprtState);
		
		uiWindow.show();
	}
	
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(tblBySchedule.getRowCount()>0){
			BigDecimal payScale =FDCHelper.ZERO;
			for(int i=0;i<tblBySchedule.getRowCount();i++){
				if(tblBySchedule.getCell(i, COL_TASK).getValue()==null && tblBySchedule.getCell(i, COL_PLAN_PAY_DATE).getValue()==null){
					FDCMsgBox.showWarning("第"+(i+1)+"行计划支付日期不能为空");
					abort();
				}
				
				Object value = tblBySchedule.getCell(i, COL_PAYMENT_TYPE).getValue();
				if (value instanceof PaymentTypeInfo) {
					PaymentTypeInfo pInfo = (PaymentTypeInfo) value;
					if(!pInfo.isPreType()){
						payScale = FDCHelper.add(payScale, tblBySchedule.getCell(i, COL_PAY_SCALE).getValue());
					}
				}
			}
			if(FDCHelper.compareTo(payScale, FDCHelper.ONE_HUNDRED)!=0){
				FDCMsgBox.showWarning("计划支付金额不等于合同金额，不能保存！");
				abort();
			}
		}
	}

}