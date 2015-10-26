/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.render.RenderObject;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.render.TextIconRender;
import com.kingdee.bos.ctrl.kdf.util.style.Style;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.CommitListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.PaymentTypeCollection;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.programming.PTECostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;
import com.kingdee.eas.fdc.contract.programming.client.CostAccountPromptBox;
import com.kingdee.eas.fdc.contract.programming.client.F7RESchTemplateTaskPromptBox;
import com.kingdee.eas.fdc.finance.CalStandardEnum;
import com.kingdee.eas.fdc.finance.CalTypeEnum;
import com.kingdee.eas.fdc.finance.PayPlanModeEnum;
import com.kingdee.eas.fdc.finance.PayPlanTemplateByMonthInfo;
import com.kingdee.eas.fdc.finance.PayPlanTemplateByScheduleInfo;
import com.kingdee.eas.fdc.finance.PayPlanTemplateCollection;
import com.kingdee.eas.fdc.finance.PayPlanTemplateFactory;
import com.kingdee.eas.fdc.finance.PayPlanTemplateInfo;
import com.kingdee.eas.fdc.finance.PrepayWriteOffEnum;
import com.kingdee.eas.fdc.finance.client.util.PayPlanClientUtil;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述：付款规划模板
 * 
 */
public class PayPlanTemplateUI extends AbstractPayPlanTemplateUI {

	private static final String BIZ_TYPE_CASHFLOW = "+BONIPDySL67xM7mEm9tXmLF6cA=";

	private static final String COL_TEMPLATE_TASK = "templateTask";

	private static final String COL_COST_ACCOUNT = "costAccount";

	private static final String COL_DELAY_DAY = "delayDay";

	private static final String COL_PAY_SCALE = "payScale";

	private static final String COL_PAYMENT_TYPE = "paymentType";

	private static final String COL_WRITE_OFF_TYPE = "writeOffType";

	private static final String COL_CAL_STANDARD = "calStandard";

	private static final String COL_CAL_TYPE = "calType";

	private static final Logger logger = CoreUIObject.getLogger(PayPlanTemplateUI.class);

	protected KDWorkButton btnAddnewLine;
	protected KDWorkButton btnInsertLine;
	protected KDWorkButton btnRemoveLines;
	protected KDWorkButton btnCopyLines;

	private ProgrammingTemplateEntireInfo pInfo;

	private KDBizPromptBox prmtCostAccountByMonth;

	private KDBizPromptBox prmtCostAccountBySchedule;

	public PayPlanTemplateUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {

		initTable();

		queryData();

		super.onLoad();
	}

	protected void queryData() throws BOSException {
		pInfo = (ProgrammingTemplateEntireInfo) getUIContext().get("programming");
		if (pInfo.containsKey("PayPlan")) {
			PayPlanTemplateInfo info = (PayPlanTemplateInfo) pInfo.get("PayPlan");
			getUIContext().put(UIContext.INIT_DATAOBJECT, info);
			setOprtState(OprtState.ADDNEW);
			return;
		}

		String id = (String) getUIContext().get("programmingId");
		if (id != null) {
			PayPlanTemplateCollection coll = PayPlanTemplateFactory.getRemoteInstance().getPayPlanTemplateCollection(
					" where programmingTemplate.id = '" + id + "'");

			if (coll != null && coll.get(0) != null) {
				PayPlanTemplateInfo info = coll.get(0);
				getUIContext().put(UIContext.ID, info.getId().toString());
			} else {
				setOprtState(OprtState.ADDNEW);
			}
		} else {
			setOprtState(OprtState.ADDNEW);
		}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(tblBySchedule.getRowCount()>0){
			BigDecimal payScale =FDCHelper.ZERO;
			for(int i=0;i<tblBySchedule.getRowCount();i++){
				
				Object value = tblBySchedule.getCell(i, COL_PAYMENT_TYPE).getValue();
				if (value instanceof PaymentTypeInfo) {
					PaymentTypeInfo pInfo = (PaymentTypeInfo) value;
					if(!pInfo.isPreType()){
						payScale = FDCHelper.add(payScale, tblBySchedule.getCell(i, COL_PAY_SCALE).getValue());
					}
				}
			}
			if(FDCHelper.compareTo(payScale, FDCHelper.ONE_HUNDRED)!=0){
				FDCMsgBox.showWarning("支付比例不等于100%，不能保存！");
				abort();
			}
		}
	}

	protected void initTable() throws BOSException {
		initTableBySchedule();

		//		initTableByMonth();
	}

	protected void initTableBySchedule() throws BOSException {
		tblBySchedule.checkParsed();
		tblBySchedule.getColumn(COL_CAL_STANDARD).getStyleAttributes().setHided(true);
		tblBySchedule.getColumn(COL_COST_ACCOUNT).getStyleAttributes().setHided(true);
		tblBySchedule.getColumn(COL_CAL_TYPE).getStyleAttributes().setHided(true);
		tblBySchedule.getColumn(COL_WRITE_OFF_TYPE).getStyleAttributes().setHided(true);
		tblBySchedule.getColumn(COL_DELAY_DAY).getStyleAttributes().setHided(true);
		tblBySchedule.getColumn(COL_TEMPLATE_TASK).getStyleAttributes().setHided(true);
		tblBySchedule.getColumn("description").getStyleAttributes().setHided(true);
		tblBySchedule.getHeadRow(0).getCell(COL_PAY_SCALE).setValue("支付比例(%)");
		
//		KDComboBox combo = PayPlanClientUtil.initComboBoxCell(tblBySchedule, COL_CAL_TYPE);
//		combo.addItems(CalTypeEnum.getEnumList().toArray());
//
//		combo = PayPlanClientUtil.initComboBoxCell(tblBySchedule, COL_CAL_STANDARD);
//		combo.addItems(CalStandardEnum.getEnumList().toArray());
//
//		combo = PayPlanClientUtil.initComboBoxCell(tblBySchedule, COL_WRITE_OFF_TYPE);
//		combo.addItems(PrepayWriteOffEnum.getEnumList().toArray());

		KDComboBox combo = PayPlanClientUtil.initComboBoxCell(tblBySchedule, COL_PAYMENT_TYPE);
		PaymentTypeCollection coll = PaymentTypeFactory.getRemoteInstance().getPaymentTypeCollection("order by number");
		combo.addItems(coll.toArray());

		KDFormattedTextField txt = PayPlanClientUtil.initFormattedTextCell(tblBySchedule, COL_PAY_SCALE, 2);
		txt.setMinimumValue(new BigDecimal(1));
		txt.setMaximumValue(new BigDecimal(100));
		txt.setNegatived(false);
		txt.setDataType(BigDecimal.class);

//		txt = PayPlanClientUtil.initFormattedTextCell(tblBySchedule, COL_DELAY_DAY, 0);
//		txt.setMinimumValue(new Integer(0));
//		txt.setDataType(Integer.class);
//		tblBySchedule.getColumn(COL_DELAY_DAY).setRenderer(new DelayDateCellRender());

		prmtCostAccountBySchedule = PayPlanClientUtil.initF7Cell(tblBySchedule, COL_COST_ACCOUNT);
		CostAccountPromptBox selector = new CostAccountPromptBox(this);
		prmtCostAccountBySchedule.setSelector(selector);
		CostAccountCellRender render = new CostAccountCellRender();
		render.setFormat(new BizDataFormat("$name$"));
		tblBySchedule.getColumn(COL_COST_ACCOUNT).setRenderer(render);

		prmtCostAccountBySchedule.addSelectorListener(new SelectorListener() {

			public void willShow(SelectorEvent e) {
				initPrmtCostAccount(true);
			}
		});

		prmtCostAccountBySchedule.addCommitListener(new CommitListener() {

			public void willCommit(CommitEvent e) {
				initPrmtCostAccount(true);
			}
		});

//		tblBySchedule.getColumn(COL_PAYMENT_TYPE).setMergeable(true);
//		tblBySchedule.getColumn(COL_PAYMENT_TYPE).setGroup(true);
//		tblBySchedule.getMergeManager().setMergeMode(KDTMergeManager.SPECIFY_MERGE);
//		tblBySchedule.getMergeManager().setDataMode(KDTMergeManager.DATA_UNIFY);

//		KDBizPromptBox prmtTempalteTask = PayPlanClientUtil.initF7Cell(tblBySchedule, COL_TEMPLATE_TASK);
//		prmtTempalteTask.setDisplayFormat("$name$");
//		prmtTempalteTask.setEditFormat("$name$");
//		prmtTempalteTask.setCommitFormat("$id$");
//		prmtTempalteTask.setSelector(new F7RESchTemplateTaskPromptBox());

		tblBySchedule.addKDTEditListener(new KDTEditAdapter() {

			public void editStopped(KDTEditEvent e) {
//				final KDTEditEvent evt = e;
//				SwingUtilities.invokeLater(new Runnable() {
//					public void run() {
//						PayPlanClientUtil.doAutoMerge(tblBySchedule, COL_PAYMENT_TYPE, evt);
//					}
//				});
			}
		});
	}

	protected void initTableByMonth() {
		tblByMonth.checkParsed();

		prmtCostAccountByMonth = PayPlanClientUtil.initF7Cell(tblByMonth, COL_COST_ACCOUNT);
		CostAccountPromptBox selector = new CostAccountPromptBox(this);
		prmtCostAccountByMonth.setSelector(selector);

		CostAccountCellRender render = new CostAccountCellRender();
		render.setFormat(new BizDataFormat("$name$"));
		tblByMonth.getColumn(COL_COST_ACCOUNT).setRenderer(render);

		prmtCostAccountByMonth.addSelectorListener(new SelectorListener() {

			public void willShow(SelectorEvent e) {
				initPrmtCostAccount(false);
			}
		});

		prmtCostAccountByMonth.addCommitListener(new CommitListener() {

			public void willCommit(CommitEvent e) {
				initPrmtCostAccount(false);
			}
		});

		PayPlanClientUtil.initTextColumn(tblByMonth, "paymentItem", 100);
		PayPlanClientUtil.initTextColumn(tblByMonth, "usage", 500);
	}

	protected void tblBySchedule_editStarting(KDTEditEvent e) {
		IRow row = tblBySchedule.getRow(e.getRowIndex());
		

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

	protected void tblBySchedule_editStopped(KDTEditEvent e) throws Exception {
		super.tblBySchedule_editStopped(e);

		IRow row = tblBySchedule.getRow(e.getRowIndex());
		

		if (e.getColIndex() == tblBySchedule.getColumnIndex(COL_PAYMENT_TYPE)) {
			Object value = row.getCell(COL_PAYMENT_TYPE).getValue();
			if (value instanceof PaymentTypeInfo) {
				PaymentTypeInfo pInfo = (PaymentTypeInfo) value;
				if (!pInfo.isPreType()) {
					row.getCell(COL_WRITE_OFF_TYPE).setValue(null);
					return;
				}
			}
		}
	}

	protected Set getCostAccountIdSet() {
		Set idSet = new HashSet();
		int cost_rowCount = pInfo.getPteCost().size();
		for (int i = 0; i < cost_rowCount; i++) {
			PTECostInfo cInfo = pInfo.getPteCost().get(i);
			CostAccountInfo costAcct = cInfo.getCostAccount();
			if (costAcct != null) {
				idSet.add(costAcct.getId().toString());
			}
		}
		return idSet;
	}

	private void initPrmtCostAccount(boolean isBySchedule) {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", OrgConstants.DEF_CU_ID, CompareType.EQUALS));

		Set idSet = getCostAccountIdSet();
		if (idSet.size() > 0) {
			filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", "error"));
		}
		evi.setFilter(filter);
		if (isBySchedule) {
			prmtCostAccountBySchedule.setEntityViewInfo(evi);
			prmtCostAccountBySchedule.getQueryAgent().resetRuntimeEntityView();
		} else {
			prmtCostAccountByMonth.setEntityViewInfo(evi);
			prmtCostAccountByMonth.getQueryAgent().resetRuntimeEntityView();
		}
	}
	

	public void storeFields() {
		super.storeFields();

		if (btnBySchedule.isSelected()) {
			editData.setMode(PayPlanModeEnum.BYSCHEDULE);
		} else if (btnByMonth.isSelected()) {
			editData.setMode(PayPlanModeEnum.BYMONTH);
		}
	}

	public IObjectPK runSubmit() throws Exception {
		if (editData.getId() == null) {
			editData.setId(BOSUuid.create(editData.getBOSType()));
		}
		ProgrammingTemplateEntireInfo pInfo = (ProgrammingTemplateEntireInfo) getUIContext().get("programming");
		pInfo.put("PayPlan", editData);
		return new ObjectUuidPK(editData.getId());
	}

	protected void doAfterSubmit(IObjectPK pk) throws Exception {
		setSave(true);
		setSaved(true);
		initOldData(editData);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("*");
		return sic;
	}

	public void loadFields() {
		super.loadFields();

		if (PayPlanModeEnum.BYSCHEDULE.equals(editData.getMode())) {
			btnBySchedule.setSelected(true);
		} else if (PayPlanModeEnum.BYMONTH.equals(editData.getMode())) {
			btnByMonth.setSelected(true);
		}

//		if (tblBySchedule.getRowCount() > 0) {
//			PayPlanClientUtil.doAutoMerge(tblBySchedule, COL_PAYMENT_TYPE, new KDTEditEvent(tblBySchedule, null, tblBySchedule.getRow(0)
//					.getCell(COL_PAYMENT_TYPE).getValue(), 0, tblBySchedule.getColumnIndex(COL_PAYMENT_TYPE), false,
//					KDTStyleConstants.BODY_ROW));
			
//			for (int i = 0; i < tblBySchedule.getRowCount(); i++) {
//				IRow row = tblBySchedule.getRow(i);
//				Object pObj = row.getCell(COL_PAYMENT_TYPE).getValue();
//				if (pObj instanceof PaymentTypeInfo) {
//					PaymentTypeInfo pInfo = (PaymentTypeInfo) pObj;
//					if (!pInfo.isPreType()) {
//						row.getCell(COL_WRITE_OFF_TYPE).setValue(null);
//					}
//				}
//			}

//		}
		

		updateTable();
	}
	

	
	
	protected boolean isContinueAddNew() {
		return false;
	}

	protected IObjectValue createNewData() {
		PayPlanTemplateInfo info = new PayPlanTemplateInfo();
		info.setMode(PayPlanModeEnum.BYSCHEDULE);

		String id = (String) getUIContext().get("programmingId");
		if (id != null) {
			ProgrammingTemplateEntireInfo item = new ProgrammingTemplateEntireInfo();
			item.setId(BOSUuid.read(id));
			info.setProgrammingTemplate(item);
		}
		return info;
	}

	protected IObjectValue createNewEntryData() {
		if (btnByMonth.isSelected()) {
			PayPlanTemplateByMonthInfo info = new PayPlanTemplateByMonthInfo();
			return info;

		} else if (btnBySchedule.isSelected()) {
			PayPlanTemplateByScheduleInfo info = new PayPlanTemplateByScheduleInfo();
//			info.setCalStandard(CalStandardEnum.CONTRACTAMOUNT);
//			info.setCalType(CalTypeEnum.TIME);
			return info;
		}

		return null;
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
		setButtonStyle(btnCopyLines, "复制行", "imgTbtn_copy");
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
		IObjectValue obj = createNewEntryData();
		dataBinder.loadLineFields(tblMain, row, obj);
	}

	protected void actionAddnewLine_actionPerformed(ActionEvent e) {
		KDTable tblMain = getTable();
		IRow row = tblMain.addRow();
		IObjectValue obj = createNewEntryData();
		dataBinder.loadLineFields(tblMain, row, obj);
	}



	private KDTable getTable() {
		if (btnByMonth.isSelected()) {
			return tblByMonth;

		} else if (btnBySchedule.isSelected()) {
			return tblBySchedule;
		}

		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PayPlanTemplateFactory.getRemoteInstance();
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
		if (btnByMonth.isSelected()) {
			contProgramming.getContentPane().remove(tblByMonth);
			contProgramming.getContentPane().remove(tblBySchedule);
			contProgramming.getContentPane().setLayout(new BorderLayout());
			contProgramming.getContentPane().add(this.tblByMonth, BorderLayout.CENTER);
			contProgramming.getContentPane().updateUI();
			tblByMonth.reLayoutAndPaint();
		} else if (btnBySchedule.isSelected()) {
			contProgramming.getContentPane().remove(tblByMonth);
			contProgramming.getContentPane().remove(tblBySchedule);
			contProgramming.getContentPane().setLayout(new BorderLayout());
			contProgramming.getContentPane().add(this.tblBySchedule, BorderLayout.CENTER);
			contProgramming.getContentPane().updateUI();
			tblBySchedule.reLayoutAndPaint();
		}
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

	class DelayDateCellRender extends TextIconRender {
		public void draw(Graphics graphics, Shape clip, Object object, Style cellStyle, Object extObject) {
			if (extObject instanceof RenderObject) {
				RenderObject rObj = (RenderObject) extObject;

				if (CalTypeEnum.TIMERANGE.equals(tblBySchedule.getCell(rObj.getRowIndex(), tblBySchedule.getColumnIndex(COL_CAL_TYPE))
						.getValue())) {
					tblBySchedule.getCell(rObj.getRowIndex(), COL_DELAY_DAY).setValue(0);
					tblBySchedule.getCell(rObj.getRowIndex(), COL_DELAY_DAY).getStyleAttributes().setLocked(true);
					object = "无";
				} else {
					tblBySchedule.getCell(rObj.getRowIndex(), COL_DELAY_DAY).getStyleAttributes().setLocked(false);
				}
				super.draw(graphics, clip, object, cellStyle, extObject);
			} else {
				super.draw(graphics, clip, object, cellStyle, extObject);
			}
		}
	}

}

