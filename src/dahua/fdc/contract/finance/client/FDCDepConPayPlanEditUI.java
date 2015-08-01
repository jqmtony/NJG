package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUI;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.PayPlanCycleFactory;
import com.kingdee.eas.fdc.basedata.PayPlanCycleInfo;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaUtil;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayItemCollection;
import com.kingdee.eas.fdc.contract.ContractPayItemFactory;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractF7UI;
import com.kingdee.eas.fdc.finance.ConPayPlanCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanDataCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanDataInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanDatapCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettleEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettleEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo;
import com.kingdee.eas.fdc.finance.IFDCDepConPayPlanBill;
import com.kingdee.eas.fdc.finance.PayPlanDataBaseInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewDataCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ����:��ͬ�¶ȹ�������ƻ� �༭����
 * @author owen_wen  date:2011-12-19
 * @version EAS6.1
 */
public class FDCDepConPayPlanEditUI extends AbstractFDCDepConPayPlanEditUI {

	private static final long serialVersionUID = 1859998184673045988L;

	private static final Logger logger = CoreUIObject
			.getLogger(FDCDepConPayPlanEditUI.class);

	// ��ǰ��¼���
	private KDTable table = this.tblContract;

	// ���µ������Чֵ�����ڵ��ı��·ݵ����ڼƻ�ʱ������Ϊ��ֵ
	Integer year_old = null;
	Integer month_old = null;

	// ��¼�������·ݳ�ʼĬ�Ͽ�ʼ�к�
	private static final int START_TOL = 1;
	private static final int START_CON = 12;
	private static final int START_UNC = 7;
	private static final int START_NOC = 6;

	// ��ʼ�汾
	private String VERSION = "1.0";
	// �ƻ�״̬(�޶����޸�)
	String flag_state = "";
	// ����ʱ��
	Date auditTime = null;
	// ������ȷ�������븶�����̷������
	boolean separteFromPayment = true;
	
	private int[] COLS_TOL;
	Map total;

	public FDCDepConPayPlanEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		detachListeners();
		super.loadFields();
		clearTable();
		initTableColumn();
		initMonthWhenLoadFields();
		loadEntryData();
		attachListeners();
		addSumRow();
		fillSummaryTable();
	}

	/**
	 * KDSpinner�ؼ�ȥ��Change�����󣬱���setValueҲ���ᴥ����ʾֵ�ı䣬<br>
	 * �����ʾֵ��ԶΪ0���˴����������Change������<br>
	 * ����һ����ͬ��ֵ������������´���Change�¼�<br>
	 */
	private void initMonthWhenLoadFields() {
		spYear.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);//added by ken...����ͨ��Ȩ�ޣ��������õ�ֵ��Ч��
		spYear.setValue(new Integer(0), false);
		spYear.setValue(new Integer(editData.getYear()), false);
		year_old = new Integer(editData.getYear());
		spMonth.setValue(new Integer(0), false);
		spMonth.setValue(new Integer(editData.getMonth()), false);
		month_old = new Integer(editData.getMonth());
	}

	/**
	 * ���3�������������
	 */
	protected void clearTable() {
		total = new TreeMap();
		tblTotal.removeRows();
		tblContract.removeRows();
		tblUnsettledCon.removeRows();
		tblNoContract.removeRows();
		for (int i = tblTotal.getColumnCount() - 1; i >= START_TOL; i--) {
			tblTotal.removeColumn(i);
		}
		for (int i = tblContract.getColumnCount() - 1; i >= START_CON; i--) {
			tblContract.removeColumn(i);
		}
		for (int i = tblUnsettledCon.getColumnCount() - 1; i >= START_UNC; i--) {
			tblUnsettledCon.removeColumn(i);
		}
		for (int i = tblNoContract.getColumnCount() - 1; i >= START_NOC; i--) {
			tblNoContract.removeColumn(i);
		}
	}

	/**
	 * ���ݼƻ����ڳ��ȣ���ʼ��3�����Ķ�̬��
	 */
	protected void initTableColumn() {
		tblTotal.checkParsed();
		tblContract.checkParsed();
		tblUnsettledCon.checkParsed();
		tblNoContract.checkParsed();
		tblContract.getColumn("lastMonthPayable").setEditor(
				getCellEditor("amount"));
		tblUnsettledCon.getColumn("unsettledConNumber").setEditor(
				getCellEditor("text"));

		tblUnsettledCon.getColumn("unsettledConName").setEditor(
				getCellEditor("text"));
		tblNoContract.getColumn("payMatters").setEditor(getCellEditor("text"));
		tblNoContract.getColumn("payMattersName").setEditor(
				getCellEditor("text"));

		if (editData.getPayPlanCycle() != null) {
			int cycle = editData.getPayPlanCycle().getCycle().getValue();
			int year = spYear.getIntegerVlaue().intValue();
			int month = spMonth.getIntegerVlaue().intValue();
			// ��ͷ
			IRow tolHead0 = tblTotal.getHeadRow(0);
			IRow tolHead1 = tblTotal.getHeadRow(1);
			IRow conHead0 = tblContract.getHeadRow(0);
			IRow conHead1 = tblContract.getHeadRow(1);
			IRow unCHead0 = tblUnsettledCon.getHeadRow(0);
			IRow unCHead1 = tblUnsettledCon.getHeadRow(1);
			IRow noCHead0 = tblNoContract.getHeadRow(0);
			IRow noCHead1 = tblNoContract.getHeadRow(1);
			// �ںϹ���
			KDTMergeManager tolMager = tblTotal.getHeadMergeManager();
			KDTMergeManager conMarge = tblContract.getHeadMergeManager();
			KDTMergeManager unCMarge = tblUnsettledCon.getHeadMergeManager();
			KDTMergeManager noCMarge = tblNoContract.getHeadMergeManager();
			// �б�
			int index = 0;
			for (int i = 0; i < cycle; i++) {
				if (month > 12) {
					year += 1;
					month = 1;
				}
				String monthStr;
				if (month < 10) {
					monthStr = year + "��0" + month + "��";
				} else {
					monthStr = year + "��" + month + "��";
				}
				String KeyHead = "MONTH" + year + "" + month;

				// ͳ�Ʊ����
				IColumn col = tblTotal.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				col.setWidth(140);
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				tolHead0.getCell(index).setValue(monthStr);
				tolHead1.getCell(index).setValue("�ƻ�֧��������");

				col = tblTotal.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "official");
				col.setWidth(140);
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				tolHead1.getCell(index).setValue("��������������");

				tolMager.mergeBlock(0, i * 2 + START_TOL, 0, START_TOL + i * 2 + 1);
				
				// ��ͬ����ƻ�����
				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				conHead0.getCell(index).setValue(monthStr);
				conHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);
				col.setRequired(true);

				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				conHead1.getCell(index).setValue("�ƻ�֧�����");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "define");
				conHead1.getCell(index).setValue("��������");
				col.setEditor(getCellEditor("define"));
				col.setRequired(true);

				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "explain");
				conHead1.getCell(index).setValue("�ÿ�˵��");
				col.setEditor(getCellEditor("explain"));
				col.setWidth(200);
				col.setRequired(true);

				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "official");
				conHead1.getCell(index).setValue("�����������");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setLocked(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				conMarge.mergeBlock(0, START_CON + (i * 5), 0, START_CON
						+ (i * 5) + 4);

				// ��ǩ����ͬ����ƻ�����
				col = tblUnsettledCon.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				unCHead0.getCell(index).setValue(monthStr);
				unCHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);
				col.setRequired(true);

				col = tblUnsettledCon.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				unCHead1.getCell(index).setValue("�ƻ�֧�����");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				col = tblUnsettledCon.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "define");
				unCHead1.getCell(index).setValue("��������");
				col.setEditor(getCellEditor("define"));
				col.setRequired(true);

				col = tblUnsettledCon.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "explain");
				unCHead1.getCell(index).setValue("�ÿ�˵��");
				col.setEditor(getCellEditor("explain"));
				col.setWidth(200);
				col.setRequired(true);

				col = tblUnsettledCon.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "official");
				unCHead1.getCell(index).setValue("�����������");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setLocked(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				unCMarge.mergeBlock(0, START_UNC + (i * 5), 0, START_UNC
						+ (i * 5) + 4);

				// �޺�ͬ����ƻ�����
				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				noCHead0.getCell(index).setValue(monthStr);
				noCHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);
				col.setRequired(true);

				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				noCHead1.getCell(index).setValue("�ƻ�֧�����");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "define");
				noCHead1.getCell(index).setValue("��������");
				col.setEditor(getCellEditor("define"));
				col.setRequired(true);

				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "explain");
				noCHead1.getCell(index).setValue("�ÿ�˵��");
				col.setEditor(getCellEditor("explain"));
				col.setWidth(200);
				col.setRequired(true);

				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "official");
				noCHead1.getCell(index).setValue("�����������");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setLocked(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				noCMarge.mergeBlock(0, START_NOC + (i * 5), 0, START_NOC
						+ (i * 5) + 4);

				month++;
			}
		}
	}

	protected void loadEntryData() {
		// ���غ�ͬ�ƻ�
		if (editData.getHasContract() != null) {
			for (int i = 0; i < editData.getHasContract().size(); i++) {
				IRow row = tblContract.addRow();
				FDCDepConPayPlanContractInfo hasCon = editData.getHasContract()
						.get(i);
				
				String depName = hasCon.getProject().getName();
				Map depMap;
				if (total.get(depName) == null) {
					depMap = new HashMap();
				} else {
					depMap = (Map) total.get(depName);
				}
				row.getCell("id").setValue(hasCon.getId());
				row.getCell("isBack").setValue(new Boolean(hasCon.isIsBack()));
				if (hasCon.isIsBack()) {
					row.getStyleAttributes().setBackground(Color.RED);
				}
				row.getCell("isEdit").setValue(new Boolean(hasCon.isIsEdit()));
				if (hasCon.isIsEdit()) {
					row.getStyleAttributes().setBackground(Color.PINK);
				}
				row.getCell("project").setValue(hasCon.getProject());
				row.getCell("conNumber").setValue(hasCon.getContract());
				row.getCell("conName").setValue(hasCon.getContractName());
				if (!FDCHelper.isEmpty(hasCon.getContract())) {
					row.getCell("contractId").setValue(
							hasCon.getContract().getId().toString());
				}
				
				try {
					/*
					 * R120720-0057��ͬ�¶ȹ�������ƻ�����ǩ����ͬ����ƻ����޸Ĳ����㣬�޸�ĳһ��ͬʱ������¼һ��
					 * ÿ�ζ�Ҫ�������µ����ݣ���Ϊÿ�ݺ�ͬ�ġ���ͬ������ۡ��ۼ��깤���ۼ��Ѹ����̿
					 * �ۼ�δ�����̿�ۼ���;�� �����ܻᷢ���仯������Ҫʵʱȡ����ʾ
					 * Added by Owen_wen 2012-08-28
					 */
					setColumunValues(calConInfo(hasCon.getContract().getId().toString()), row);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					handUIExceptionAndAbort(e);
				}
				for (int j = 0; j < hasCon.getEntrys().size(); j++) {
					FDCDepConPayPlanContractEntryInfo conEntry = hasCon
							.getEntrys().get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(conEntry.getMonth());
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					String keyHead = "MONTH" + year + "" + month;
					row.getCell(keyHead + "id").setValue(conEntry.getId());
					row.getCell(keyHead + "plan").setValue(
							conEntry.getPlanPay());
					row.getCell(keyHead + "define").setValue(
							conEntry.getMoneyDefine());
					row.getCell(keyHead + "explain").setValue(
							conEntry.getExplain());
					row.getCell(keyHead + "official").setValue(
							conEntry.getOfficialPay());
					
					BigDecimal curPlan = conEntry.getPlanPay() == null ? FDCHelper.ZERO : conEntry.getPlanPay();
					BigDecimal curOfficial = conEntry.getOfficialPay() == null ? FDCHelper.ZERO : conEntry.getOfficialPay();
					if (depMap.get(keyHead + "plan") != null) {
						BigDecimal plan = (BigDecimal) depMap.get(keyHead + "plan");
						// plan = plan == null ? FDCHelper.ZERO : plan;
						depMap.put(keyHead + "plan", plan.add(curPlan));
					} else {
						depMap.put(keyHead + "plan", curPlan);
					}
					if (depMap.get(keyHead + "official") != null) {
						BigDecimal official = (BigDecimal) depMap.get(keyHead + "official");
						// plan = plan == null ? FDCHelper.ZERO : plan;
						depMap.put(keyHead + "official", official.add(curOfficial));
					} else {
						depMap.put(keyHead + "official", curOfficial);
					}
					total.put(depName, depMap);
				}
			}
		}
		// ���ش�ǩ����ͬ�ƻ�
		if (editData.getUnsettledCon() != null) {
			for (int i = 0; i < editData.getUnsettledCon().size(); i++) {
				IRow row = tblUnsettledCon.addRow();
				FDCDepConPayPlanUnsettledConInfo unCon = editData
						.getUnsettledCon().get(i);
				
				String depName = unCon.getProject().getName();
				Map depMap;
				if (total.get(depName) == null) {
					depMap = new HashMap();
				} else {
					depMap = (Map) total.get(depName);
				}
				row.getCell("id").setValue(unCon.getId());
				row.getCell("isBack").setValue(new Boolean(unCon.isIsBack()));
				if (unCon.isIsBack()) {
					row.getStyleAttributes().setBackground(Color.RED);
				}
				row.getCell("isEdit").setValue(new Boolean(unCon.isIsEdit()));
				if (unCon.isIsEdit()) {
					row.getStyleAttributes().setBackground(Color.PINK);
				}
				row.getCell("project").setValue(unCon.getProject());
				row.getCell("unsettledConNumber").setValue(
						unCon.getUnConNumber());
				row.getCell("unsettledConName").setValue(unCon.getUnConName());
				row.getCell("unsettledConestPrice").setValue(
						unCon.getPlanAmount());
				for (int j = 0; j < unCon.getEntrys1().size(); j++) {
					FDCDepConPayPlanUnsettleEntryInfo unEntry = unCon
							.getEntrys1().get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(unEntry.getMonth());
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					String keyHead = "MONTH" + year + "" + month;
					row.getCell(keyHead + "id").setValue(unEntry.getId());
					row.getCell(keyHead + "plan")
							.setValue(unEntry.getPlanPay());
					row.getCell(keyHead + "define").setValue(
							unEntry.getMoneyDefine());
					row.getCell(keyHead + "explain").setValue(
							unEntry.getExplain());
					row.getCell(keyHead + "official").setValue(
							unEntry.getOfficialPay());
					
					BigDecimal curPlan = unEntry.getPlanPay() == null ? FDCHelper.ZERO : unEntry.getPlanPay();
					BigDecimal curOfficial = unEntry.getOfficialPay() == null ? FDCHelper.ZERO : unEntry.getOfficialPay();
					if (depMap.get(keyHead + "plan") != null) {
						BigDecimal plan = (BigDecimal) depMap.get(keyHead + "plan");
						// plan = plan == null ? FDCHelper.ZERO : plan;
						depMap.put(keyHead + "plan", plan.add(curPlan));
					} else {
						depMap.put(keyHead + "plan", curPlan);
					}
					if (depMap.get(keyHead + "official") != null) {
						BigDecimal official = (BigDecimal) depMap.get(keyHead + "official");
						// plan = plan == null ? FDCHelper.ZERO : plan;
						depMap.put(keyHead + "official", official.add(curOfficial));
					} else {
						depMap.put(keyHead + "official", curOfficial);
					}
					total.put(depName, depMap);
				}
			}
		}
		// �����޺�ͬ�ƻ�
		if (editData.getNoContract() != null) {
			for (int i = 0; i < editData.getNoContract().size(); i++) {
				IRow row = tblNoContract.addRow();
				FDCDepConPayPlanNoContractInfo noCon = editData.getNoContract()
						.get(i);
				
				
				String depName = noCon.getProject().getName();
				Map depMap;
				if (total.get(depName) == null) {
					depMap = new HashMap();
				} else {
					depMap = (Map) total.get(depName);
				}
				row.getCell("id").setValue(noCon.getId());
				row.getCell("isBack").setValue(new Boolean(noCon.isIsBack()));
				if (noCon.isIsBack()) {
					row.getStyleAttributes().setBackground(Color.RED);
				}
				row.getCell("isEdit").setValue(new Boolean(noCon.isIsEdit()));
				if (noCon.isIsEdit()) {
					row.getStyleAttributes().setBackground(Color.PINK);
				}
				row.getCell("project").setValue(noCon.getProject());
				row.getCell("payMatters").setValue(noCon.getPayMatters());
				row.getCell("payMattersName").setValue(
						noCon.getPayMattersName());
				for (int j = 0; j < noCon.getEntrys1().size(); j++) {
					FDCDepConPayPlanNoContractEntryInfo noEntry = noCon
							.getEntrys1().get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(noEntry.getMonth());
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					String keyHead = "MONTH" + year + "" + month;
					row.getCell(keyHead + "id").setValue(noEntry.getId());
					row.getCell(keyHead + "plan")
							.setValue(noEntry.getPlanPay());
					row.getCell(keyHead + "define").setValue(
							noEntry.getMoneyDefine());
					row.getCell(keyHead + "explain").setValue(
							noEntry.getExplain());
					row.getCell(keyHead + "official").setValue(
							noEntry.getOfficialPay());
					
					BigDecimal curPlan = noEntry.getPlanPay() == null ? FDCHelper.ZERO : noEntry.getPlanPay();
					BigDecimal curOfficial = noEntry.getOfficialPay() == null ? FDCHelper.ZERO : noEntry.getOfficialPay();
					if (depMap.get(keyHead + "plan") != null) {
						BigDecimal plan = (BigDecimal) depMap.get(keyHead + "plan");
						// plan = plan == null ? FDCHelper.ZERO : plan;
						depMap.put(keyHead + "plan", plan.add(curPlan));
					} else {
						depMap.put(keyHead + "plan", curPlan);
					}
					if (depMap.get(keyHead + "official") != null) {
						BigDecimal official = (BigDecimal) depMap.get(keyHead + "official");
						// plan = plan == null ? FDCHelper.ZERO : plan;
						depMap.put(keyHead + "official", official.add(curOfficial));
					} else {
						depMap.put(keyHead + "official", curOfficial);
					}
					total.put(depName, depMap);
				}
			}
		}
	}

	/**
	 * ���ñ���е�Ԫ��ı༭��
	 */
	protected ICellEditor getCellEditor(String type) {
		if (type == null) {
			return null;
		} else if ("amount".equals(type)) {
			return FDCClientHelper.getNumberCellEditor();
		} else if ("explain".equals(type)) {
			KDDetailedArea explain = new KDDetailedArea(250, 200);
			explain.setMaxLength(1000);
			return new KDTDefaultCellEditor(explain);
		} else if ("define".equals(type)) {
			KDBizPromptBox prmtDefine = new KDBizPromptBox();
			prmtDefine
					.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
			prmtDefine.setDisplayFormat("$name$");
			prmtDefine.setEditFormat("$number$");
			prmtDefine.setCommitFormat("$number$");
			prmtDefine.setRequired(true);
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE));
			view.setFilter(filter);
			prmtDefine.setEntityViewInfo(view);
			return new KDTDefaultCellEditor(prmtDefine);
		} else if ("contract".equals(type)) {
			KDBizPromptBox prmtcontract = new KDContractBizPromptBox();
			prmtcontract.setDisplayFormat("$name$");
			prmtcontract.setEditFormat("$number$");
			prmtcontract.setCommitFormat("$number$");
			prmtcontract.setRequired(true);
			
			prmtcontract.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBillQueryForPayPlanQuery");
			// ��Ϊ��ѡ
			 prmtcontract.getQueryAgent().setEnabledMultiSelection(true);
			 prmtcontract.addDataChangeListener(new DataChangeListener() {
				 // by tim_gao�պ�������ظ�У��Ļ�Ӧ�ð�row ����map��
				 public void dataChanged(DataChangeEvent eventObj) {
				 // TODO Auto-generated method stub
					
				 }
			
			 });
			prmtcontract.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					KDBizPromptBox f7 = (KDContractBizPromptBox) e.getSource();
					f7.getQueryAgent().resetRuntimeEntityView();
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					// ��Ҫ���ݹ�����Ŀ�����β��Ź��˳���ͬ
					Object dpt = prmtDeptment.getValue();
					AdminOrgUnitInfo admin = null;
					if (dpt == null) {
						admin = SysContext.getSysContext().getCurrentAdminUnit();
					} else {
						admin = (AdminOrgUnitInfo) dpt;
					}
					filter.getFilterItems().add(new FilterItemInfo("respDept.id", admin.getId().toString(), CompareType.EQUALS));// ���β���
					filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));// ������
					filter.getFilterItems().add(new FilterItemInfo("isCoseSplit", Boolean.TRUE));
					
					//�Ƿ񵥶����㣨0���ǵ������㣬1�����ǵ������㣩
					filter.getFilterItems().add(new FilterItemInfo("isAmtWithoutCost", Boolean.FALSE, CompareType.EQUALS));
					view.setFilter(filter);
					f7.setEntityViewInfo(view);

					// modified by zhaoqin for R130603-0215 on 2013/7/24 start
					KDContractPromptDialog.getParams().put(KDContractPromptDialog.ADMIN_OR_ORGUNIT_ENTITYVIEW, view);
					// modified by zhaoqin for R130603-0215 on 2013/7/24 end
				}
			});
			// modified by zhaoqin for R130603-0215 on 2013/7/24 start
			prmtcontract.setSelector(new KDContractPromptDialog());
			prmtcontract.setDefaultF7UIName("com.kingdee.eas.fdc.finance.client.KDContractPromptDialog");
			// modified by zhaoqin for R130603-0215 on 2013/7/24 end
			return new KDTDefaultCellEditor(prmtcontract);
		} else if ("project".equals(type)) {
			//Ĭ����
			//modified by ken_liu... �޸�Ϊ�¼���֯һ����ѯ��
			String longNumber=SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getLongNumber();
			KDCommonPromptEnumFilterDialog curProjectF7 = 
				new KDCommonPromptEnumFilterDialog(true,"��ǰ��֯","fullOrgUnit.longNumber",longNumber.replaceAll("!", ".")+"%");
			curProjectF7.getEnumCompareTypeMap().put("��ǰ��֯", CompareType.LIKE);

			//�����
			String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
			//moidfied by Owen_wen ��ȫ����ʾ��Ҫ��ʾ��ǰ��¼��֯����CU�����й�����Ŀ 2012-9-25
			curProjectF7.addEumFilterItem("ȫ����ʾ", "CU.id", cuId);
			curProjectF7.getEnumCompareTypeMap().put("ȫ����ʾ", CompareType.EQUALS);
			//���ӹ���
			EntityViewInfo filterViewInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
			filterViewInfo.setFilter(filter);
			//ö�ٹ���
			curProjectF7.setEnumFilterInfo(filterViewInfo);
			//��ѯ����
			curProjectF7.setEntityViewInfo(filterViewInfo);
			curProjectF7.setQueryInfo(MetaDataLoaderFactory.
						getRemoteMetaDataLoader().getQuery(new MetaDataPK("com.kingdee.eas.fdc.basedata.app.CurProjectQuery")));
			prmtCurProject.setSelector(curProjectF7);
			return new KDTDefaultCellEditor(prmtCurProject);
		} else if ("text".equals(type)) {
			KDTextField txt = new KDTextField();
			txt.setMaxLength(80);
			return new KDTDefaultCellEditor(txt);
		}
		return null;
	}

	/**
	 * ��Ӻϼ���
	 */
	protected void addSumRow() {
		IRow row = tblContract.getRow(tblContract.getRowCount() - 1);
		if (row != null && "(С��)".equals(row.getCell("project").getValue())) {
			tblContract.removeRow(tblContract.getRowCount() - 1);
		}
		row = tblUnsettledCon.getRow(tblUnsettledCon.getRowCount() - 1);
		if (row != null && "(С��)".equals(row.getCell("project").getValue())) {
			tblUnsettledCon.removeRow(tblUnsettledCon.getRowCount() - 1);
		}
		row = tblNoContract.getRow(tblNoContract.getRowCount() - 1);
		if (row != null && "(С��)".equals(row.getCell("project").getValue())) {
			tblNoContract.removeRow(tblNoContract.getRowCount() - 1);
		}

		int cycle = editData.getPayPlanCycle().getCycle().getValue();
		int[] COLS_Con = new int[cycle * 2 + 5];
		int[] COLS_UnC = new int[cycle * 2 + 1];
		int[] COLS_NoC = new int[cycle * 2];

		// �ϼƺ�ͬ��¼
		COLS_Con[0] = 6;
		COLS_Con[1] = 8;
		COLS_Con[2] = 9;
		COLS_Con[3] = 10;
		COLS_Con[4] = 11;
		for (int i = 0; i < cycle; i++) {
			COLS_Con[5 + 2 * i] = START_CON + (i * 5) + 1;
			COLS_Con[6 + 2 * i] = START_CON + (i * 5) + 4;
		}
		row = FDCTableHelper.calcSumForTable(0, tblContract.getRowCount() - 1,
				COLS_Con, tblContract);
		if (row != null) {
			row.getCell("project").setValue("(С��)");
			lockCell4SumRow(row, START_CON, tblContract.getColumnCount());
		}
		// �ϼƴ�ǩ����ͬ��¼
		COLS_UnC[0] = 6;
		for (int i = 0; i < cycle; i++) {
			COLS_UnC[1 + 2 * i] = START_UNC + (i * 5) + 1;
			COLS_UnC[2 + 2 * i] = START_UNC + (i * 5) + 4;
		}
		row = FDCTableHelper.calcSumForTable(0,
				tblUnsettledCon.getRowCount() - 1, COLS_UnC, tblUnsettledCon);
		if (row != null) {
			row.getCell("project").setValue("(С��)");
			lockCell4SumRow(row, START_UNC, tblUnsettledCon.getColumnCount());
		}
		// �ϼ��޺�ͬ��¼
		for (int i = 0; i < cycle; i++) {
			COLS_NoC[2 * i] = START_NOC + (i * 5) + 1;
			COLS_NoC[2 * i + 1] = START_NOC + (i * 5) + 4;
		}
		row = FDCTableHelper.calcSumForTable(0,
				tblNoContract.getRowCount() - 1, COLS_NoC, tblNoContract);
		if (row != null) {
			row.getCell("project").setValue("(С��)");
			lockCell4SumRow(row, START_NOC, tblNoContract.getColumnCount());
		}
	}

	public void storeFields() {
		super.storeFields();
		storeEntryData();
	}

	protected void storeEntryData() {
		editData.getHasContract().clear();
		editData.getUnsettledCon().clear();
		editData.getNoContract().clear();
		// �����ͬ�ƻ���¼
		for (int i = 0; i < tblContract.getRowCount(); i++) {
			IRow row = tblContract.getRow(i);
			FDCDepConPayPlanContractInfo conPlan = new FDCDepConPayPlanContractInfo();
			BOSUuid id = (BOSUuid) row.getCell("id").getValue();
			Boolean isBack = (Boolean) row.getCell("isBack").getValue();
			if (isBack == null) {
				isBack = Boolean.FALSE;
			}
			Boolean isEdit = (Boolean) row.getCell("isEdit").getValue();
			if (isEdit == null) {
				isEdit = Boolean.FALSE;
			}
			Object obj = row.getCell("project").getValue();
			if ("(С��)".equals(obj)) {
				continue;
			}
			CurProjectInfo project = (CurProjectInfo) obj;
			ContractBillInfo contract = (ContractBillInfo) row.getCell(
					"conNumber").getValue();
			String conName = (String) row.getCell("conName").getValue();
			BigDecimal conPrice = (BigDecimal) row.getCell("conPrice")
					.getValue();
			BigDecimal lastMonthPayable = (BigDecimal) row.getCell(
					"lastMonthPayable").getValue();
			BigDecimal lastMonthPay = (BigDecimal) row.getCell("lastMonthPay")
					.getValue();
			BigDecimal lastMonthNopay = (BigDecimal) row.getCell(
					"lastMonthNopay").getValue();
			BigDecimal lastMonthEnRoute = (BigDecimal) row.getCell(
					"lastMonthEnRoute").getValue();

			conPlan.setId(id);
			conPlan.setIsBack(isBack.booleanValue());
			conPlan.setIsEdit(isEdit.booleanValue());
			conPlan.setProject(project);
			conPlan.setContract(contract);
			conPlan.setContractName(conName);
			conPlan.setContractPrice(conPrice);
			conPlan.setLastMonthPayable(lastMonthPayable);
			conPlan.setLastMonthPay(lastMonthPay);
			conPlan.setLastMonthNopay(lastMonthNopay);
			conPlan.setLastMonthEnRoute(lastMonthEnRoute);

			for (int j = START_CON; j < tblContract.getColumnCount(); j += 5) {
				FDCDepConPayPlanContractEntryInfo planEntry = new FDCDepConPayPlanContractEntryInfo();
				if (row.getCell(j).getValue() instanceof BOSUuid) {
					BOSUuid planID = (BOSUuid) row.getCell(j).getValue();
					planEntry.setId(planID);
				}
				BigDecimal planPay = (BigDecimal) row.getCell(j + 1).getValue();
				PaymentTypeInfo moneyDefine = (PaymentTypeInfo) row.getCell(
						j + 2).getValue();
				String explain = (String) row.getCell(j + 3).getValue();
				BigDecimal officialPay = (BigDecimal) row.getCell(j + 4)
						.getValue();
				planEntry.setPlanPay(planPay);
				planEntry.setMoneyDefine(moneyDefine);
				planEntry.setExplain(explain);
				planEntry.setOfficialPay(officialPay);

				String key = tblContract.getColumn(j).getKey();
				String year = key.substring(5, 9);
				String month = key.substring(9, key.length() - 2);
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, new Integer(year).intValue());
				cal.set(Calendar.MONTH, new Integer(month).intValue() - 1);
				cal.set(Calendar.DAY_OF_MONTH, 15);
				planEntry.setMonth(cal.getTime());

				conPlan.getEntrys().add(planEntry);
			}
			editData.getHasContract().add(conPlan);
		}
		// �����ǩ����ͬ�ƻ���¼
		for (int i = 0; i < tblUnsettledCon.getRowCount(); i++) {
			IRow row = tblUnsettledCon.getRow(i);
			FDCDepConPayPlanUnsettledConInfo unCPlan = new FDCDepConPayPlanUnsettledConInfo();
			BOSUuid id = (BOSUuid) row.getCell("id").getValue();
			Boolean isBack = (Boolean) row.getCell("isBack").getValue();
			if (isBack == null) {
				isBack = Boolean.FALSE;
			}
			Boolean isEdit = (Boolean) row.getCell("isEdit").getValue();
			if (isEdit == null) {
				isEdit = Boolean.FALSE;
			}
			Object obj = row.getCell("project").getValue();
			if ("(С��)".equals(obj)) {
				continue;
			}
			CurProjectInfo project = null;
			
			if(obj instanceof CurProjectInfo){
				project = (CurProjectInfo) obj;
			}else if (obj instanceof Object[]){
				project = (CurProjectInfo) ((Object[])obj)[0];
			}
			String unConNumber;
			Object ucObj = row.getCell("unsettledConNumber").getValue();
			if (ucObj == null) {
				unConNumber = null;
			} else if (ucObj instanceof ProgrammingContractInfo) {
				unConNumber = ((ProgrammingContractInfo) ucObj).getLongNumber();
			} else {
				unConNumber = ucObj.toString();
			}
			String unConName = (String) row.getCell("unsettledConName")
					.getValue();
			BigDecimal unConPrice = (BigDecimal) row.getCell(
					"unsettledConestPrice").getValue();
			unCPlan.setId(id);
			unCPlan.setIsBack(isBack.booleanValue());
			unCPlan.setIsEdit(isEdit.booleanValue());
			unCPlan.setProject(project);
			unCPlan.setUnConNumber(unConNumber);
			unCPlan.setUnConName(unConName);
			unCPlan.setPlanAmount(unConPrice);

			for (int j = START_UNC; j < tblUnsettledCon.getColumnCount(); j += 5) {
				FDCDepConPayPlanUnsettleEntryInfo planEntry = new FDCDepConPayPlanUnsettleEntryInfo();
				BOSUuid planID = (BOSUuid) row.getCell(j).getValue();
				BigDecimal planPay = (BigDecimal) row.getCell(j + 1).getValue();
				PaymentTypeInfo moneyDefine = (PaymentTypeInfo) row.getCell(
						j + 2).getValue();
				String explain = (String) row.getCell(j + 3).getValue();
				BigDecimal officialPay = (BigDecimal) row.getCell(j + 4)
						.getValue();
				planEntry.setId(planID);
				planEntry.setPlanPay(planPay);
				planEntry.setMoneyDefine(moneyDefine);
				planEntry.setExplain(explain);
				planEntry.setOfficialPay(officialPay);

				String key = tblUnsettledCon.getColumn(j).getKey();
				String year = key.substring(5, 9);
				String month = key.substring(9, key.length() - 2);
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, new Integer(year).intValue());
				cal.set(Calendar.MONTH, new Integer(month).intValue() - 1);
				cal.set(Calendar.DAY_OF_MONTH, 15);
				planEntry.setMonth(cal.getTime());

				unCPlan.getEntrys1().add(planEntry);
			}
			editData.getUnsettledCon().add(unCPlan);
		}
		// �����޺�ͬ�ƻ���¼
		for (int i = 0; i < tblNoContract.getRowCount(); i++) {
			IRow row = tblNoContract.getRow(i);
			FDCDepConPayPlanNoContractInfo noCPlan = new FDCDepConPayPlanNoContractInfo();
			BOSUuid id = (BOSUuid) row.getCell("id").getValue();
			Boolean isBack = (Boolean) row.getCell("isBack").getValue();
			if (isBack == null) {
				isBack = Boolean.FALSE;
			}
			Boolean isEdit = (Boolean) row.getCell("isEdit").getValue();
			if (isEdit == null) {
				isEdit = Boolean.FALSE;
			}
			Object obj = row.getCell("project").getValue();
			if ("(С��)".equals(obj)) {
				continue;
			}
			CurProjectInfo project = null;
			if( obj instanceof CurProjectInfo){
				project = (CurProjectInfo) obj;
			}else if (obj instanceof Object[]){
			
				project = (CurProjectInfo) ((Object[])obj)[0];
			}
			
		
			
			String payMatters = (String) row.getCell("payMatters").getValue();
			String payMattersName = (String) row.getCell("payMattersName")
					.getValue();
			noCPlan.setId(id);
			noCPlan.setIsBack(isBack.booleanValue());
			noCPlan.setIsEdit(isEdit.booleanValue());
			noCPlan.setProject(project);
			noCPlan.setPayMatters(payMatters);
			noCPlan.setPayMattersName(payMattersName);

			for (int j = START_NOC; j < tblNoContract.getColumnCount(); j += 5) {
				FDCDepConPayPlanNoContractEntryInfo planEntry = new FDCDepConPayPlanNoContractEntryInfo();
				BOSUuid planID = (BOSUuid) row.getCell(j).getValue();
				BigDecimal planPay = (BigDecimal) row.getCell(j + 1).getValue();
				PaymentTypeInfo moneyDefine = (PaymentTypeInfo) row.getCell(
						j + 2).getValue();
				String explain = (String) row.getCell(j + 3).getValue();
				BigDecimal officialPay = (BigDecimal) row.getCell(j + 4)
						.getValue();
				planEntry.setId(planID);
				planEntry.setPlanPay(planPay);
				planEntry.setMoneyDefine(moneyDefine);
				planEntry.setExplain(explain);
				planEntry.setOfficialPay(officialPay);

				String key = tblNoContract.getColumn(j).getKey();
				String year = key.substring(5, 9);
				String month = key.substring(9, key.length() - 2);
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, new Integer(year).intValue());
				cal.set(Calendar.MONTH, new Integer(month).intValue() - 1);
				cal.set(Calendar.DAY_OF_MONTH, 15);
				planEntry.setMonth(cal.getTime());

				noCPlan.getEntrys1().add(planEntry);
			}
			editData.getNoContract().add(noCPlan);
		}
	}

	boolean isInWorkflow = false;
	public void onLoad() throws Exception {
		super.onLoad();
		
		//added by ken_liu..
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if (isFromWorkflow != null && "true".equals(isFromWorkflow.toString())) {
			isInWorkflow = true;
		}
		
		initUI();
		setSortAuto(tblContract);
		setSortAuto(tblNoContract);
		setSortAuto(tblUnsettledCon);
		setUITitle("�����¶ȸ���ƻ�");
		
		//������ǲ鿴״̬�������Ҫ�ѱ��水ť����
		if (!OprtState.VIEW.equals(this.getOprtState())) {
			this.actionSave.setEnabled(true);
		}
		//������״̬���ǡ����桱�͡���ء�״̬��ʱ��Ͱѱ��水ť���� add by Jian_cao  BT721353
		if (null != this.editData.getState()) {
			if (!FDCBillStateEnum.SAVED.equals(this.editData.getState()) && !FDCBillStateEnum.BACK.equals(this.editData.getState())) {
				this.actionSave.setEnabled(false);
			}
		}
	}
	private void setSortAuto(KDTable tables) {
		tables.setColumnMoveable(true);
		
		
		// ���ñ���Զ�������
		// �����Զ�������в����Զ�����
		tables.getColumn("project").setSortable(true);
		KDTSortManager sm = new KDTSortManager(tables);
		sm.setSortAuto(true);
	}
	

	protected void initUI() {
		btnPublish.setIcon(EASResource.getIcon("imgTbtn_timingrefer"));
		menuItemPublish.setIcon(EASResource.getIcon("imgTbtn_timingrefer"));
		btnRevise.setIcon(EASResource.getIcon("imgTbtn_emend"));
		menuItemRevise.setIcon(EASResource.getIcon("imgTbtn_emend"));
		btnBack.setIcon(EASResource.getIcon("imgTbtn_untreadreport"));
		btnIntroPre.setIcon(EASResource.getIcon("imgTbtn_editiondifference"));
		btnViewContract.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));

		txtName.setMaxLength(100);
		description.setAutoscrolls(true);
		description.setRows(3);
		SpinnerNumberModel model = new SpinnerNumberModel();
		model.setMinimum(new Integer(1));
		model.setMaximum(new Integer(12));
		model.setStepSize(new Integer(1));
		spMonth.setModel(model);
		spMonth.setValue(new Integer(editData.getMonth()), false);
//		spYear.setValue(new Integer(editData.getYear()), false);
		
		tblTotal.getStyleAttributes().setLocked(true);
		if (getUIContext().get("flag_state") != null) {
			flag_state = (String) getUIContext().get("flag_state");
			actionSave.setEnabled(false);
			actionAddNew.setEnabled(false);
			actionRemove.setEnabled(false);
			txtName.setEnabled(false);
			txtNumber.setEnabled(false);
			prmtDeptment.setEnabled(false);
			spMonth.setEnabled(false);
			spYear.setEnabled(false);
			description.setEnabled(false);
			actionRevise.setEnabled(false);
		}

		year_old = this.spYear.getIntegerVlaue();
		month_old = this.spMonth.getIntegerVlaue();

		CtrlUnitInfo obj = editData.getCU();
		if (obj == null) {
			obj = SysContext.getSysContext().getCurrentCtrlUnit();
		}
		String cuId = obj.getId().toString();
		FDCClientUtils.setRespDeptF7(prmtDeptment, this,
				canSelecOtherOrgPerson() ? null : cuId);

		ctnCon.removeAllButton();
		ctnCon.addButton(btnIntroPre);
		ctnCon.addButton(btnAddLine);
		ctnCon.addButton(btnRemoveLine);

		ObjectValueRender ovrNum = new ObjectValueRender();
		ovrNum.setFormat(new BizDataFormat("$number$"));
		tblContract.getColumn("conNumber").setRenderer(ovrNum);
		tblContract.getColumn("conNumber").setEditor(getCellEditor("contract"));

		ovrNum = new ObjectValueRender();
		ovrNum.setFormat(new BizDataFormat("$longNumber$"));
		tblUnsettledCon.getColumn("unsettledConNumber").setRenderer(ovrNum);
		tblUnsettledCon.getColumn("unsettledConestPrice").setEditor(
				getCellEditor("amount"));

		ObjectValueRender ovrName = new ObjectValueRender();
		ovrName.setFormat(new BizDataFormat("$name$"));
		tblUnsettledCon.getColumn("project").setRenderer(ovrName);
		tblUnsettledCon.getColumn("project")
				.setEditor(getCellEditor("project"));
		tblNoContract.getColumn("project").setRenderer(ovrName);
		tblNoContract.getColumn("project").setEditor(getCellEditor("project"));

		if (FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
			actionPublish.setEnabled(true);
		} else if (FDCBillStateEnum.BACK.equals(editData.getState())) {
			actionAudit.setEnabled(false);
			actionRevise.setEnabled(true);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		} else if (FDCBillStateEnum.PUBLISH.equals(editData.getState())) {
			actionAudit.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		}

		// ����δ���ã����ֹ�¼���ۼ��깤���
		try {
			separteFromPayment = FDCUtils.getBooleanValue4FDCParamByKey(null,
					SysContext.getSysContext().getCurrentOrgUnit().getId()
							.toString(), "FDC317_SEPARATEFROMPAYMENT");
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
		if (!separteFromPayment) {
			tblContract.getColumn("lastMonthPayable").getStyleAttributes()
					.setLocked(false);
		}

		/* ���������޸������������
		 * ����ڹ����������Ƶ��˱��ˣ�������ͨ������أ���Ӧ��Ҫ����ʱ��һ���ܱ༭������ֻ���޸��������
		 */
		if (editData.getId() != null && isInWorkflow && !isCreator()) {
			this.lockUIForViewStatus();
			actionSubmit.setEnabled(true);
			
			lockUI4Workflow();	//modified by ken_liu...��ȡ�������޸�
			/*tblContract.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			tblContract.setEditable(true);
			tblContract.getStyleAttributes().setLocked(true);
			for (int i = START_CON + 4; i < tblContract.getColumnCount(); i += 5) {

				tblContract.getColumn(i).getStyleAttributes().setLocked(false);
			}
			tblUnsettledCon
					.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			tblUnsettledCon.setEditable(true);
			tblUnsettledCon.getStyleAttributes().setLocked(true);
			for (int i = START_UNC + 4; i < tblUnsettledCon.getColumnCount(); i += 5) {
				tblUnsettledCon.getColumn(i).getStyleAttributes().setLocked(
						false);
			}
			tblNoContract
					.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			tblNoContract.setEditable(true);
			tblNoContract.getStyleAttributes().setLocked(true);
			for (int i = START_NOC + 4; i < tblNoContract.getColumnCount(); i += 5) {
				tblNoContract.getColumn(i).getStyleAttributes()
						.setLocked(false);
			}*/
			
		}
	}

	/**
	 * ��������ĳЩ�ؼ���
	 */
	protected void lockUI4Workflow() {
		this.lockUIForViewStatus();
		actionSubmit.setEnabled(true);
		btnIntroPre.setEnabled(false);
		btnAddLine.setEnabled(false);
		btnRemoveLine.setEnabled(false);
		
		lockTable4Workflow(tblContract, START_CON);
		lockTable4Workflow(tblUnsettledCon, START_UNC);
		lockTable4Workflow(tblNoContract, START_NOC);
	}
	
	/**
	 * ��С����ÿ���ڱ༭�¼���������������ɣ���ˣ�ÿ����Ҫ����������
	 * @param sumRow
	 * @param startIndexOfDynamicColumn
	 * @param columnCount
	 */
	private void lockCell4SumRow(IRow sumRow, int startIndexOfDynamicColumn, int columnCount) {
		for (int i = startIndexOfDynamicColumn + 4; i < columnCount; i += 5) {
			sumRow.getCell(i).getStyleAttributes().setLocked(true);
		}
	}
	
	/**
	 * ������񣬷ſ��������������ɱ༭
	 * @param table	�����ı��
	 * @param startIndexOfDynamicColumn ��̬�п�ʼ�б�
	 */
	private void lockTable4Workflow(KDTable table, int startIndexOfDynamicColumn) {
		if(table.getRowCount()<=0) {
			return;
		}
		
		table.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		table.setEditable(true);
		table.getStyleAttributes().setLocked(true);
		 
		IRow sumRow = table.getRow(table.getRowCount()-1); 
		for (int i = startIndexOfDynamicColumn + 4; i < table.getColumnCount(); i += 5) {
			table.getColumn(i).getStyleAttributes().setLocked(false);
			sumRow.getCell(i).getStyleAttributes().setLocked(true);
		}
	}
	
	protected boolean checkCanSubmit() throws Exception {
		// ���������޸������������
		if (editData.getId() != null && isInWorkflow) {
			return true;
		} else {
			return super.checkCanSubmit();
		}
	}

	/**
	 * ����onShow�������ô˷���������һ����������򣬶��˵��ݲ���Ҫ���򣬹�����
	 */
	protected void initListener() {
		// super.initListener();
	}

	protected void attachListeners() {
		addDataChangeListener(spYear);
		addDataChangeListener(spMonth);
		addDataChangeListener(prmtDeptment);
	}

	protected void detachListeners() {
		removeDataChangeListener(spYear);
		removeDataChangeListener(spMonth);
		removeDataChangeListener(prmtDeptment);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCDepConPayPlanBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		if (pnlBig.getSelectedIndex() == 0) {
			return tblTotal;
		} else if (pnlBig.getSelectedIndex() == 1) {
			return tblContract;
		} else if (pnlBig.getSelectedIndex() == 2) {
			return tblUnsettledCon;
		} else if (pnlBig.getSelectedIndex() == 3) {
			return tblNoContract;
		}
		return table;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");

		sic.add("curProject.id");
		sic.add("curProject.name");
		sic.add("curProject.number");
		sic.add("curProject.codingNumber");
		sic.add("curProject.displayName");
		sic.add("curProject.fullOrgUnit.name");

		sic.add("CU.id");
		sic.add("orgUnit.id");

		sic.add("payPlanCycle.id");
		sic.add("payPlanCycle.cycle");

		sic.add("deptment.id");
		sic.add("deptment.number");
		sic.add("deptment.name");
		sic.add("deptment.isAdminOrgUnit");

		sic.add("creator.id");
		sic.add("creator.number");
		sic.add("creator.name");

		sic.add("auditor.id");
		sic.add("auditor.number");
		sic.add("auditor.name");

		sic.add("hasContract.id");
		sic.add("hasContract.isBack");
		sic.add("hasContract.isEdit");
		sic.add("hasContract.project.id");
		sic.add("hasContract.project.number");
		sic.add("hasContract.project.name");
		sic.add("hasContract.contract.id");
		sic.add("hasContract.contract.number");
		sic.add("hasContract.contract.name");
		sic.add("hasContract.contract.curProject.id");
		sic.add("hasContract.contract.curProject.name");
		sic.add("hasContract.contract.curProject.number");
		
		
		sic.add("hasContract.contractName");
		sic.add("hasContract.contractPrice");
		sic.add("hasContract.lastMonthPayable");
		sic.add("hasContract.lastMonthPay");
		sic.add("hasContract.lastMonthNopay");
		sic.add("hasContract.lastMonthEnRoute");
		sic.add("hasContract.entrys.*");
		sic.add("hasContract.entrys.moneyDefine.id");
		sic.add("hasContract.entrys.moneyDefine.number");
		sic.add("hasContract.entrys.moneyDefine.name");

		sic.add("unsettledCon.id");
		sic.add("unsettledCon.isBack");
		sic.add("unsettledCon.isEdit");
		sic.add("unsettledCon.project.id");
		sic.add("unsettledCon.project.number");
		sic.add("unsettledCon.project.name");
		sic.add("unsettledCon.unConNumber");
		sic.add("unsettledCon.unConName");
		sic.add("unsettledCon.planAmount");
		sic.add("unsettledCon.entrys1.*");
		sic.add("unsettledCon.entrys1.moneyDefine.id");
		sic.add("unsettledCon.entrys1.moneyDefine.number");
		sic.add("unsettledCon.entrys1.moneyDefine.name");

		sic.add("noContract.id");
		sic.add("noContract.isBack");
		sic.add("noContract.isEdit");
		sic.add("noContract.project.id");
		sic.add("noContract.project.number");
		sic.add("noContract.project.name");
		sic.add("noContract.payMatters");
		sic.add("noContract.payMattersName");
		sic.add("noContract.entrys1.*");
		sic.add("noContract.entrys1.moneyDefine.id");
		sic.add("noContract.entrys1.moneyDefine.number");
		sic.add("noContract.entrys1.moneyDefine.name");

		return sic;
	}

	protected void spYear_stateChanged(ChangeEvent e) throws Exception {
		if (isFirstOnload()) {
			return;
		}
		AdminOrgUnitInfo adminInfo = (AdminOrgUnitInfo) prmtDeptment.getValue();
		Integer year = spYear.getIntegerVlaue();
		Integer month = spMonth.getIntegerVlaue();
		checkHasMonthDepHasPlan(adminInfo, year, month);
		// �ı��·���շ�¼
		int result = MsgBox.OK;
		if (tblContract.getRowCount() > 0 || tblUnsettledCon.getRowCount() > 0
				|| tblNoContract.getRowCount() > 0) {
			result = MsgBox.showConfirm2("�ı�����·ݽ���շ�¼���ݣ�");
		}
		if (result == MsgBox.OK) {
			clearTable();
			initTableColumn();
			year_old = year;
		} else {
			spYear.setValue(year_old, false);
		}
	}

	/**
	 * У�鲿�Ÿ��¶ȵļƻ��Ƿ����
	 * @param adminInfo
	 * @param year
	 * @param month
	 */
	private void checkHasMonthDepHasPlan(AdminOrgUnitInfo adminInfo, Integer year, Integer month) {
		if (hasMonthHasPlan(adminInfo, year, month)) {
			FDCMsgBox.showWarning(this, "����Ϊ: \"" + adminInfo.getName()
					+ "\" �����·�Ϊ��" + year + "��" + month + " �µĺ�ͬ�¶ȹ�������ƻ��Ѵ���");
			spYear.setValue(year_old, false);
			spMonth.setValue(month_old, false);
			abort();
		}
	}

	protected void spMonth_stateChanged(ChangeEvent e) throws Exception {
		if (isFirstOnload()) {
			return;
		}
		AdminOrgUnitInfo adminInfo = (AdminOrgUnitInfo) prmtDeptment.getValue();
		Integer year = spYear.getIntegerVlaue();
		Integer month = spMonth.getIntegerVlaue();
		checkHasMonthDepHasPlan(adminInfo, year, month);
		// �ı��·���շ�¼
		int result = MsgBox.OK;
		if (tblContract.getRowCount() > 0 || tblUnsettledCon.getRowCount() > 0
				|| tblNoContract.getRowCount() > 0) {
			result = MsgBox.showConfirm2("�ı�����·ݽ���շ�¼���ݣ�");
		}
		if (result == MsgBox.OK) {
			clearTable();
			initTableColumn();
			month_old = month;
		} else {
			spMonth.setValue(month_old, false);
		}

	}

	protected void addDataChangeListener(JComponent com) {

		EventListener[] listeners = (EventListener[]) listenersMap.get(com);

		if (listeners != null && listeners.length > 0) {
			if (com instanceof KDPromptBox) {
				for (int i = 0; i < listeners.length; i++) {
					((KDPromptBox) com)
							.addDataChangeListener((DataChangeListener) listeners[i]);
				}
			} else if (com instanceof KDFormattedTextField) {
				for (int i = 0; i < listeners.length; i++) {
					((KDFormattedTextField) com)
							.addDataChangeListener((DataChangeListener) listeners[i]);
				}
			} else if (com instanceof KDDatePicker) {
				for (int i = 0; i < listeners.length; i++) {
					((KDDatePicker) com)
							.addDataChangeListener((DataChangeListener) listeners[i]);
				}
			} else if (com instanceof KDComboBox) {
				for (int i = 0; i < listeners.length; i++) {
					((KDComboBox) com)
							.addItemListener((ItemListener) listeners[i]);
				}
			} else if (com instanceof KDSpinner) {
				for (int i = 0; i < listeners.length; i++) {
					((KDSpinner) com)
							.addChangeListener((ChangeListener) listeners[i]);
				}
			}
		}

	}

	protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;

		if (com instanceof KDPromptBox) {
			listeners = com.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDPromptBox) com)
						.removeDataChangeListener((DataChangeListener) listeners[i]);
			}
		} else if (com instanceof KDFormattedTextField) {
			listeners = com.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDFormattedTextField) com)
						.removeDataChangeListener((DataChangeListener) listeners[i]);
			}
		} else if (com instanceof KDDatePicker) {
			listeners = com.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDDatePicker) com)
						.removeDataChangeListener((DataChangeListener) listeners[i]);
			}
		} else if (com instanceof KDComboBox) {
			listeners = com.getListeners(ItemListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDComboBox) com)
						.removeItemListener((ItemListener) listeners[i]);
			}
		} else if (com instanceof KDSpinner) {
			listeners = com.getListeners(ChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDSpinner) com)
						.removeChangeListener((ChangeListener) listeners[i]);
			}
		}

		if (listeners != null && listeners.length > 0) {
			listenersMap.put(com, listeners);
		}
	}

	/**
	 * ��鵱ǰ�����·��Ƿ���ڼƻ�
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected boolean hasMonthHasPlan(AdminOrgUnitInfo adminInfo,
			Integer year,
			Integer month) {
		if (adminInfo == null
				|| (!getOprtState().equals(STATUS_ADDNEW) && !getOprtState()
						.equals(STATUS_EDIT))) {
			return false;
		}
		// Integer year = spYear.getIntegerVlaue();
		// Integer month = spMonth.getIntegerVlaue();
		FilterInfo filter = new FilterInfo();
		if (!STATUS_ADDNEW.equals(getOprtState()) && editData.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", editData.getId().toString(),
							CompareType.NOTEQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("year", year));
		filter.getFilterItems().add(new FilterItemInfo("month", month));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("deptment.id", adminInfo.getId()
								.toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.REVISE_VALUE,
						CompareType.NOTEQUALS));
		// if ("revist".equals(flag_state)) {
			filter.getFilterItems().add(
					new FilterItemInfo("state",
							FDCBillStateEnum.REVISING_VALUE,
							CompareType.NOTEQUALS));
		// }
		boolean isDep = false;
		try {
			isDep = FDCDepConPayPlanBillFactory.getRemoteInstance().exists(
					filter);
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
		return isDep;
	}

	protected void prmtDeptment_dataChanged(DataChangeEvent e) throws Exception {
		if (isFirstOnload()) {
			return;
		}
		Object oldValue = e.getOldValue();
		Object newValue = e.getNewValue();
		if (newValue == null) {
			tblContract.removeRows();
			tblUnsettledCon.removeRows();
			tblNoContract.removeRows();
		} else if (!((AdminOrgUnitInfo) newValue).isIsLeaf()) {
			prmtDeptment.setDataNoNotify(oldValue);
		} else {
			AdminOrgUnitInfo adminInfo = (AdminOrgUnitInfo) newValue;
			Integer year = spYear.getIntegerVlaue();
			Integer month = spMonth.getIntegerVlaue();
			if (hasMonthHasPlan(adminInfo, year, month)) {
				FDCMsgBox.showWarning(this, "����Ϊ: \"" + adminInfo.getName()
						+ "\" �����·�Ϊ��" + year + "��" + month + " �µĺ�ͬ�¶ȹ�������ƻ��Ѵ���");
				prmtDeptment.setDataNoNotify(oldValue);
				abort();
			}
			int result = MsgBox.showConfirm2("�ı����β��Ž���շ�¼���ݣ�");
			if (result == MsgBox.OK) {
				tblContract.removeRows();
				tblUnsettledCon.removeRows();
				tblNoContract.removeRows();
			} else {
				prmtDeptment.setDataNoNotify(oldValue);
			}
		}
	}

	protected void pnlBig_stateChanged(ChangeEvent e) throws Exception {
		if (pnlBig.getSelectedIndex() == 1) {
			ctnCon.removeAllButton();
			ctnCon.addButton(btnIntroPre);
			ctnCon.addButton(btnAddLine);
			ctnCon.addButton(btnRemoveLine);
			actionViewContract.setEnabled(true);
		} else if (pnlBig.getSelectedIndex() == 2) {
			ctnUnC.removeAllButton();
			ctnUnC.addButton(btnIntroPre);
			ctnUnC.addButton(btnAddLine);
			ctnUnC.addButton(btnRemoveLine);
			actionViewContract.setEnabled(false);
		} else if (pnlBig.getSelectedIndex() == 3) {
			ctnNoC.removeAllButton();
			ctnNoC.addButton(btnIntroPre);
			ctnNoC.addButton(btnAddLine);
			ctnNoC.addButton(btnRemoveLine);
			actionViewContract.setEnabled(false);
		}
	}

	protected void tblContract_tableClicked(KDTMouseEvent e) throws Exception {
		int colIndex = e.getColIndex();
		if (STATUS_VIEW.equals(getOprtState())) {
			if (colIndex > START_CON && (colIndex - START_CON) % 5 == 3) {
				KDDetailedAreaUtil.showDialog(this, tblContract);
			}
		}
	}
	
	
	/**
	 * ��ú�ͬ��������ĳ�µĸ���ƻ�
	 * @param conInfo ��ͬinfo
	 * @param month �·�
	 * @return �����ԭ�ң�֮��
	 */
	private String getContractPlanExplainByMonth(ContractBillInfo conInfo, int month) {
		String planPay = null;
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("contractbill.id", conInfo.getId().toString());
		viewInfo.setFilter(filter);
		ContractPayItemCollection items = null;
		try {
			items = ContractPayItemFactory.getRemoteInstance().getContractPayItemCollection(viewInfo);
		} catch (BOSException e) {
			handleException(e);
		}
		//�����Ӧ��ͬ��Ӧ�ľ�������
		ContractPayItemInfo payItem = null;
		for (int i = 0; i < items.size(); i++) {
			payItem = items.get(i);
			//���ĳ�¾���������ԭ�ң�֮��
			if (payItem.getPayItemDate() != null && payItem.getPayItemDate().getMonth() + 1 == month
					&& payItem.getPayItemDate().getYear() == spYear.getIntegerVlaue().intValue() - 1900) {
				planPay = payItem.getPayCondition();
				
			}
		}

		return planPay;
	}

	/**
	 * ��ú�ͬ��������ĳ�µ��ۻ��ļƻ�֧�����
	 * @param conInfo ��ͬinfo
	 * @param month �·�
	 * @return �����ԭ�ң�֮��
	 */
	private BigDecimal getContractPlanPayByMonth(ContractBillInfo conInfo,int month){
		BigDecimal planPay = new BigDecimal("0.00");
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("contractbill.id", conInfo.getId().toString());
		viewInfo.setFilter(filter);
		ContractPayItemCollection items = null;
			try {
				items = ContractPayItemFactory.getRemoteInstance().getContractPayItemCollection(viewInfo);
			} catch (BOSException e) { 
				handleException(e);
			}
			//�����Ӧ��ͬ��Ӧ�ľ�������
			ContractPayItemInfo payItem = null;
			for (int i = 0; i < items.size(); i++) {
				payItem = items.get(i);
				//���ĳ�¾���������ԭ�ң�֮��
				if(payItem.getPayItemDate()!=null && payItem.getPayItemDate().getMonth()+1==month && payItem.getPayItemDate().getYear() == spYear.getIntegerVlaue().intValue() - 1900){
					planPay = planPay.add(payItem.getAmount());
				}
			}
			
		return planPay;
	}
	
	/**
	 * ��ú�Լ�滮��������ĳ�µ��ۻ��ļƻ�֧������������ķ�����
	 * @param conInfo ��ͬinfo
	 * @param month �·�
	 * @return ��ͬ��Ӧ�·ݵľ��������и����ԭ�ң�֮��
	 */
	private BigDecimal getProContractPlanPayByMonth(ProgrammingContractInfo conInfo,int month){
		BigDecimal planPay = new BigDecimal("0.00");
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("contract.id", conInfo.getId().toString());
		viewInfo.setFilter(filter);
		ProgrammingContractEconomyCollection items = null;
			try {
				items = ProgrammingContractEconomyFactory.getRemoteInstance().getProgrammingContractEconomyCollection(viewInfo);
			} catch (BOSException e) {
				handleException(e);
			}
			ProgrammingContractEconomyInfo payItem = null;
			for (int i = 0; i < items.size(); i++) {
				try {
					payItem = items.get(i);
					if(payItem.getPaymentDate()!=null && payItem.getPaymentDate().getMonth()+1==month){						
						planPay = planPay.add(payItem.getAmount());
					}
				} catch (Exception e) {
					handleException(e);
				} 
			}
			
		return planPay;
	}
	
	
	
    /**
     * �õ���ͬ��������ĳ������ĸ�������
     * @param conInfo ��ͬinfo
     * @param month  �·�
     * @return ��������info
     */
	private PaymentTypeInfo getContractPayTypeByMonth(ContractBillInfo conInfo,int month){
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("contractbill.id", conInfo.getId().toString());
		viewInfo.setFilter(filter);
		//��ͬ�����еľ�������
		ContractPayItemCollection items = null;
		try {
			items = ContractPayItemFactory.getRemoteInstance().getContractPayItemCollection(viewInfo);
		} catch (BOSException e) {
			handleException(e);
		}
		//���µľ�������
		ContractPayItemInfo latestPayItem = null; 
		ContractPayItemInfo payItem = null;
		for (int i = 0; i < items.size(); i++) {
			payItem = items.get(i);
			//���������Ƿ�Ϊ����
			if (payItem.getPayItemDate() != null && payItem.getPayItemDate().getMonth() + 1 == month
					&& payItem.getPayItemDate().getYear() == spYear.getIntegerVlaue().intValue() - 1900) {
				if(latestPayItem == null ){
					latestPayItem = payItem;
				}
				//�Ƚϱ��µ����� ����Ϊ��������
				else if(latestPayItem.getPayItemDate().before(payItem.getPayItemDate())){
					latestPayItem = payItem;
				}
			}
		} 
		if(latestPayItem!=null){
			PaymentTypeInfo paymentType = null;
			try {
				paymentType = PaymentTypeFactory.getRemoteInstance().getPaymentTypeInfo(new ObjectUuidPK(latestPayItem.getPaymentType().getId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return paymentType;
		}
		return null;
	}
	
	 /**
     * �õ���Լ�滮��������ĳ������ĸ�������(��������ķ���)
     * @param conInfo ��ͬinfo
     * @param month �·�
     * @return ���µĸ�������
     */
	private PaymentTypeInfo getProContractPayTypeByMonth(ProgrammingContractInfo conInfo,int month){
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("contract.id", conInfo.getId().toString());
		viewInfo.setFilter(filter);
		//��Լ�滮�µľ�������
		ProgrammingContractEconomyCollection items = null;
		try {
			items = ProgrammingContractEconomyFactory.getRemoteInstance().getProgrammingContractEconomyCollection(viewInfo);
		} catch (BOSException e) {
			handleException(e);
		}
		//���µľ�������
		ProgrammingContractEconomyInfo latestPayItem = null;
		ProgrammingContractEconomyInfo payItem = null;
		for (int i = 0; i < items.size(); i++) {
			payItem = items.get(i);
			if(payItem.getPaymentDate()!=null && payItem.getPaymentDate().getMonth()+1==month){
				if(latestPayItem == null ){
					latestPayItem = payItem;
				}
				else if(latestPayItem.getPaymentDate().compareTo(payItem.getPaymentDate())<0){
					latestPayItem = payItem;
				}
			}
		}
		if(latestPayItem!=null){
			PaymentTypeInfo paymentType = null;
			try {
				paymentType = PaymentTypeFactory.getRemoteInstance().getPaymentTypeInfo(new ObjectUuidPK(latestPayItem.getPaymentType().getId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return paymentType;
		}
		return null;
	}

	/**
	 * ��ǩ����ͬ����ƻ� 
	 */
	protected void tblContract_editStopped(KDTEditEvent e) throws Exception {
		if (e.getValue() == e.getOldValue()) {
			return;
		}
		
		PayPlanCycleInfo ppcInfo = (PayPlanCycleInfo) prmtPayPlanCycle.getValue();
		int cycle = ppcInfo.getCycle().getValue();
		int colIndex = e.getColIndex();
		
		
		
		
		if ("conNumber".equals(tblContract.getColumnKey(colIndex))) {
			if (null != e.getValue()) {
				if (e.getValue() instanceof Object[]) {
					// ��ֵ�仯��ɾ��С����
					tblContract.removeRow(tblContract.getRowCount() - 1);
					Object[] obj = (Object[]) e.getValue();
					if (obj.length > 0) {
						// by tim_gao �ȴ����һ��,��ѭ������
						tblContract.getRow(tblContract.getSelectManager().getActiveRowIndex()).getCell("conNumber").setValue(
								(ContractBillInfo) obj[0]);
						for (int i = 1; i < obj.length; i++) {
							IRow row = null;
							if (getDetailTable() != null) {
								row = tblContract.addRow();
								// appendFootRow(getDetailTable());
								row.getCell("conNumber").setValue((ContractBillInfo) obj[i]);
							}
						}
					}
					addSumRow();
				} else if (e.getValue() instanceof ContractBillCollection) {
					// ��ֵ�仯��ɾ��С����
					tblContract.removeRow(tblContract.getRowCount() - 1);
					ContractBillCollection col = (ContractBillCollection) e.getValue();
					if (col.size() > 0) {
						// by tim_gao �ȴ����һ��,��ѭ������
						tblContract.getRow(tblContract.getSelectManager().getActiveRowIndex()).getCell("conNumber").setValue(
								(ContractBillInfo) col.get(0));

						for (int i = 1; i < col.size(); i++) {
							IRow row = null;
							if (getDetailTable() != null) {
								row = tblContract.addRow();
								// appendFootRow(getDetailTable());
								row.getCell("conNumber").setValue((ContractBillInfo) col.get(i));
							}
						}
					}
					addSumRow();
				}

				//				 	initTableColumn();
			}
		}

		// by tim_gao ��Ϊ���ڶ��ͬ����ѭ����ѯ
		for (int r = e.getRowIndex(); r < tblContract.getRowCount(); r++) {
			IRow row = tblContract.getRow(r);
			if (row == null) {
				return;
			}
			if (!("(С��)".equals(row.getCell("project").getValue()))) {

				String columnKey = tblContract.getColumnKey(colIndex);
				// �ۼ��깤����иı䣬��Ҫͬʱ����δ����
				if ("lastMonthPayable".equals(columnKey)) {
					BigDecimal lastMonthPayable = (BigDecimal) row.getCell("lastMonthPayable").getValue();
					BigDecimal lastMonthPay = (BigDecimal) row.getCell("lastMonthPay").getValue();
					lastMonthPayable = lastMonthPayable == null ? FDCHelper.ZERO : lastMonthPayable;
					lastMonthPay = lastMonthPay == null ? FDCHelper.ZERO : lastMonthPay;
					row.getCell("lastMonthNopay").setValue(lastMonthPayable.subtract(lastMonthPay));
				}
				// ��ͬ�����иı�
				if ("conNumber".equals(columnKey)) {
					
					Object old = e.getOldValue();
					Object cur = e.getValue();
					old = old == null ? "" : old;
					cur = cur == null ? "" : cur;
					Object contractrName =row.getCell("conName").getValue();
					
					if (old.equals(cur)) {
						//û��ֵ�Ĳ���У�� ��һ����� by tim_gao
						if ("".equals(contractrName) && null != contractrName) {
							return;
						}
					}
//					ContractBillInfo con = (ContractBillInfo) e.getValue();
					// by tim_gao ѭ����ͬ
				
					ContractBillInfo con = null;
					if( row.getCell("conNumber").getValue() instanceof ContractBillInfo){
						con =(ContractBillInfo) row.getCell("conNumber").getValue();
					}else if (row.getCell("conNumber").getValue() instanceof Object[]){
						con =(ContractBillInfo) ((Object[]) row.getCell("conNumber").getValue())[0];
					}
					if (con != null) {
						if (con.getCurProject() != null) {
							CurProjectInfo prj = CurProjectFactory.getRemoteInstance().getCurProjectInfo(
									new ObjectUuidPK(con.getCurProject().getId()), getSimpleSelector());
							row.getCell("project").setValue(prj);
						} else {
							row.getCell("project").setValue(null);
						}
						row.getCell("conName").setValue(con.getName());

						String conID = con.getId().toString();
						
						setColumunValues(calConInfo(conID), row);

						// �������¼ƻ���
						FDCDepConPayPlanBillInfo plan = getPreMonthConInfo();
						int year = spYear.getIntegerVlaue().intValue();
						int month = spMonth.getIntegerVlaue().intValue();
						
						
						// ��Ŀ�ʽ�ƻ� - modified by zhaoqin on 2013/09/07 start
						Map monthData = getContractPayPlanData("contract", con.getId().toString());
						// ��Ŀ�ʽ�ƻ� - modified by zhaoqin on 2013/09/07 end

						//added by shangjing 
						//�Զ�����ѡ�еĺ�ͬ��Ӧ�·ݵľ��������ܺ�		
						String headKey = "";				
						for(int i=0;i<cycle;i++){
							//����·ݳ���12 ����������1�·�
							if(month>12){
								year+=1;
								month = 1;
							}
							logger.info("*********"+i);
							headKey = "MONTH" + year + "" + (month);
							//��ô˺�ͬ��Ӧ�·ݵľ��������и����ԭ�ң�֮��
							BigDecimal planpay = getContractPlanPayByMonth(con,month);
							//����ƻ�֧�����Ϊ0  �ֶΡ��������͡����ÿ�˵�������Բ���
							if(planpay.intValue()==0){
									row.getCell(headKey + "define").getStyleAttributes().setBackground(Color.white);
									row.getCell(headKey + "explain").getStyleAttributes().setBackground(Color.white);
							}else{
								row.getCell(headKey + "define").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
								row.getCell(headKey + "explain").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
								
								row.getCell(headKey + "plan").setValue(planpay);//�ƻ�֧�����
							}
							
							//Ϊ�������͸�ֵ 
							row.getCell(headKey + "define").setValue(getContractPayTypeByMonth(con, month));
							row.getCell(headKey + "official").setValue(planpay);//�����������

							row.getCell(headKey + "explain").setValue(getContractPlanExplainByMonth(con, month));//�ÿ�˵��
							
							// ��Ŀ�ʽ�ƻ� - modified by zhaoqin on 2013/09/07 start
							// ����ʱ�Զ� ���뱾ѡ�к�ͬ����ƻ��ı��¸�����
							row.getCell(headKey + "plan").setValue(monthData.get(headKey));//�ƻ�֧�����
							// ��Ŀ�ʽ�ƻ� - modified by zhaoqin on 2013/09/07 end
							
							month++;
						}
				
				
						year = spYear.getIntegerVlaue().intValue();
						month = spMonth.getIntegerVlaue().intValue();
						month--;
						if (month < 1) {
							year--;
							month = 12;
						}
						Calendar cal = Calendar.getInstance();
						cal.set(year, month, 1, 0, 0, 0);
						if (plan != null) {
							FDCDepConPayPlanContractCollection hasCon = plan
									.getHasContract();
							if (hasCon != null && hasCon.size() > 0) {
								for (int i = 0; i < hasCon.size(); i++) {
									FDCDepConPayPlanContractInfo info = hasCon.get(i);
									String curConID = info.getContract().getId().toString();
									if (conID.equals(curConID)) {
										for (int j = 0; j < info.getEntrys().size(); j++) {
											FDCDepConPayPlanContractEntryInfo entry = info.getEntrys().get(j);
											if (entry.getMonth().compareTo(cal.getTime()) >= 0) {
												Calendar date = Calendar.getInstance();
												date.setTime(entry.getMonth());
												String keyHead = "MONTH" + date.get(Calendar.YEAR) + "" + (date.get(Calendar.MONTH) + 1);
												row.getCell(keyHead + "plan").setValue(entry.getPlanPay());
												row.getCell(keyHead + "define").setValue(entry.getMoneyDefine());
												row.getCell(keyHead + "explain").setValue(entry.getExplain());
												row.getCell(keyHead + "official").setValue(entry.getOfficialPay());
											}
										}
									}
								}
							}
						}
					} else {
						row.getCell("project").setValue(null);
						row.getCell("conName").setValue(null);
						row.getCell("conPrice").setValue(null);
						row.getCell("lastMonthPayable").setValue(null);
						row.getCell("lastMonthPay").setValue(null);
						row.getCell("lastMonthNopay").setValue(null);
						row.getCell("lastMonthEnRoute").setValue(null);
					}
				}

			// ����ƻ�֧������иı䣬ͬʱ�ı������������
			// ֻ�ı䱾�е������������������� r == e.getRowIndex() Added by Owen_wen 2012-02-28
				if (r == e.getRowIndex() && colIndex >= START_CON && (colIndex - START_CON) % 5 == 1) { 
					row.getCell(colIndex + 3).setValue(e.getValue());
					//add by shangjing
					BigDecimal val = (BigDecimal) e.getValue();
					if (val != null && val.compareTo(new BigDecimal(0.00)) == 0) {
						row.getCell(colIndex + 1).getStyleAttributes().setBackground(Color.white);
						row.getCell(colIndex + 2).getStyleAttributes().setBackground(Color.white);
					} else {
						row.getCell(colIndex + 1).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
						row.getCell(colIndex + 2).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
					}
				}

				// ����༭�����У���ɺ����õ�Ԫ�񲻻���
				if (colIndex > START_CON && (colIndex - START_CON) % 5 == 3) {
					row.getCell(colIndex).getStyleAttributes().setWrapText(
							false);
				}

				Boolean isBack = (Boolean) row.getCell("isBack").getValue();
				if (isBack != null && isBack.booleanValue()) {
					Object old = e.getOldValue();
					Object cur = e.getValue();
					old = old == null ? "" : old;
					cur = cur == null ? "" : cur;
					if (!old.equals(cur)) {
						row.getCell("isEdit").setValue(Boolean.TRUE);
						row.getStyleAttributes().setBackground(Color.PINK);
					}
				}
			}
		}
		// ����С��,�ŵ����治Ȼѭ��ִ��,��ѭ��
		addSumRow();
	}

	protected void tblUnsettledCon_tableClicked(KDTMouseEvent e)
			throws Exception {
		int colIndex = e.getColIndex();
		if (STATUS_VIEW.equals(getOprtState())) {
			if (colIndex > START_UNC && (colIndex - START_UNC) % 5 == 3) {
				KDDetailedAreaUtil.showDialog(this, tblUnsettledCon);
			}
		}
		// ѡ���Լ�滮�У�����editor
		if (colIndex == 2) {
			IRow row = tblUnsettledCon.getRow(e.getRowIndex());
			if (row != null) {
				initProgrammingEditor(row);
			}
		}
	}

	protected void initProgrammingEditor(IRow row) {
		KDBizPromptBox myPrmtBox = new KDBizPromptBox() {
			protected Object stringToValue(String t) {
				if (t != null && t.length() > 80) {
					t = t.substring(0, 80);
				}
				return t;
			}
		};
		myPrmtBox
				.setQueryInfo("com.kingdee.eas.fdc.contract.programming.app.ProgrammingContractF7Query");
		myPrmtBox.setDisplayFormat("$name$");
		CurProjectInfo prj = null;
		Object obj = row.getCell("project").getValue();
		if(obj instanceof CurProjectInfo){
			prj  = (CurProjectInfo) obj;
		}else if (obj instanceof Object[]){
			prj  = (CurProjectInfo) ((Object[])obj)[0];
		}
		if (prj != null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(
							new FilterItemInfo("project.id", prj.getId()
									.toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("programming.isLatest", Boolean.TRUE));
			view.setFilter(filter);
			myPrmtBox.setEntityViewInfo(view);
		}
		final boolean isNull = prj == null ? true : false;
		if (null != prj) {
			getUIContext().put("projectId", prj.getId().toString());
		}
		myPrmtBox.setSelector(new KDPromptSelector() {
			IUIWindow win = null;

			public void show() {
				try {
					UIContext context = new UIContext(FDCDepConPayPlanEditUI.this);
					Object object = getUIContext().get("projectId");
					if (object == null) {
						if (editData.getCurProject() != null) {
							object = editData.getCurProject().getId();
						}
					}
					context.put("projectId", object);
					//�½��������� uiwindow(��Լ���F7)����
					win = UIFactory.createUIFactory().create(ProgrammingContractF7UI.class.getName(), context);
					win.show();
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			}

			public boolean isCanceled() {
				return false;
			}

			//�õ����ص�ֵ
			public Object getData() {
				return getUIContext().get("selectedValue");
			}
		});
		
		myPrmtBox.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				if (isNull) {
					MsgBox.showWarning(getDetailTable(), "������ѡ�񹤳���Ŀ!");
					e.setCanceled(true);
				}
			}
		});

		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(myPrmtBox);
		row.getCell("unsettledConNumber").setEditor(f7Editor);
	}

	protected void tblUnsettledCon_editStopped(KDTEditEvent e) throws Exception {
		PayPlanCycleInfo ppcInfo = (PayPlanCycleInfo) prmtPayPlanCycle.getValue();
		int cycle = ppcInfo.getCycle().getValue();
		int colIndex = e.getColIndex();
		IRow row = tblUnsettledCon.getRow(e.getRowIndex());
		if (row == null) {
			return;
		}
		String key = tblUnsettledCon.getColumnKey(colIndex);
		
		// ����ƻ�֧������иı䣬ͬʱ�ı������������
		if (colIndex >= START_UNC && (colIndex - START_UNC) % 5 == 1) {
			row.getCell(colIndex + 3).setValue(e.getValue());			
			//add by shangjing
			BigDecimal val = (BigDecimal) e.getValue();
			if(val!=null && val.compareTo(new BigDecimal(0.00))==0){
				row.getCell(colIndex + 1).getStyleAttributes().setBackground(Color.white);
				row.getCell(colIndex + 2).getStyleAttributes().setBackground(Color.white);
			}else{
				row.getCell(colIndex + 1).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
				row.getCell(colIndex + 2).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
			}
		}

		// ����༭�����У���ɺ����õ�Ԫ�񲻻���
		if (colIndex > START_UNC && (colIndex - START_UNC) % 5 == 3) {
			row.getCell(colIndex).getStyleAttributes().setWrapText(false);
		}

		// �༭������Ŀ�У������������ú�Լ�滮editor
		if ("project".equals(key)) {
			initProgrammingEditor(row);
		}

		// �༭��ǩ����ͬ�����У���ȡֵ�����Ϊ��Լ�滮�����������в�����������ſ�������
		if ("unsettledConNumber".equals(key)) {
			Object obj = row.getCell("unsettledConNumber").getValue();
			if (obj == null || obj instanceof String) {
				row.getCell("unsettledConName").getStyleAttributes().setLocked(
						false);
			} else if (obj instanceof ProgrammingContractInfo) {
				ProgrammingContractInfo cp = (ProgrammingContractInfo) obj;
				row.getCell("unsettledConName").getStyleAttributes().setLocked(
						true);
				row.getCell("unsettledConName").setValue(cp.getName());
				row.getCell("unsettledConestPrice").setValue(
						cp.getBalance());
				//added by shangjing
				//�Զ�����ѡ�еĺ�Լ�滮��Ӧ�·ݵľ��������ܺ�
				int year = spYear.getIntegerVlaue().intValue();
				int month = spMonth.getIntegerVlaue().intValue();
				
				// ��Ŀ�ʽ�ƻ� - modified by zhaoqin on 2013/09/07 start
				Map monthData = getContractPayPlanData("programming", cp.getId().toString());
				// ��Ŀ�ʽ�ƻ� - modified by zhaoqin on 2013/09/07 end
				
				String headKey = "";
				for (int i = 0; i < cycle; i++) {

					if (month > 12) {
						year += 1;
						month = 1;
					}
					headKey = "MONTH" + year + "" + (month);

					// ��ô˺�Լ�滮��Ӧ�·ݵľ��������и����ԭ�ң�֮��
					BigDecimal planpay = getProContractPlanPayByMonth(cp, month);
					if (planpay.intValue() == 0) {
						row.getCell(headKey + "define").getStyleAttributes().setBackground(Color.white);
						row.getCell(headKey + "explain").getStyleAttributes().setBackground(Color.white);
					}else{
						row.getCell(headKey + "define").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
						row.getCell(headKey + "explain").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
					}

					row.getCell(headKey + "plan").setValue(planpay);//�ƻ�֧�����
					// Ϊ�������͸�ֵ
					row.getCell(headKey + "define").setValue(getProContractPayTypeByMonth(cp, month));
					row.getCell(headKey + "official").setValue(planpay);//�����������

					// ��Ŀ�ʽ�ƻ� - modified by zhaoqin on 2013/09/07 start
					// ����ʱ�Զ����뱾ѡ�к�Լ�滮�ĸ���滮�ı��¸�����
					row.getCell(headKey + "plan").setValue(monthData.get(headKey));//�ƻ�֧�����
					// ��Ŀ�ʽ�ƻ� - modified by zhaoqin on 2013/09/07 end
					
					month++;
				}
				//
			}
		}

		// ����С��
		addSumRow();

		Boolean isBack = (Boolean) row.getCell("isBack").getValue();
		if (isBack != null && isBack.booleanValue()) {
			Object old = e.getOldValue();
			Object cur = e.getValue();
			old = old == null ? "" : old;
			cur = cur == null ? "" : cur;
			if (!old.equals(cur)) {
				row.getCell("isEdit").setValue(Boolean.TRUE);
				row.getStyleAttributes().setBackground(Color.PINK);
			}
		}
	}

	protected void tblNoContract_tableClicked(KDTMouseEvent e) throws Exception {
		int colIndex = e.getColIndex();
		if (STATUS_VIEW.equals(getOprtState())) {
			if (colIndex > START_NOC && (colIndex - START_NOC) % 5 == 3) {
				KDDetailedAreaUtil.showDialog(this, tblNoContract);
			}
		}
	}

	protected void tblNoContract_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		IRow row = tblNoContract.getRow(e.getRowIndex());

		// ����ƻ�֧������иı䣬ͬʱ�ı������������
		if (colIndex >= START_NOC && (colIndex - START_NOC) % 5 == 1) {
			row.getCell(colIndex + 3).setValue(e.getValue());
			//add by shangjing
			BigDecimal val = (BigDecimal) e.getValue();
			if(val!=null && val.compareTo(new BigDecimal(0.00))==0){
				row.getCell(colIndex + 1).getStyleAttributes().setBackground(Color.white);
				row.getCell(colIndex + 2).getStyleAttributes().setBackground(Color.white);
			}else{
				row.getCell(colIndex + 1).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
				row.getCell(colIndex + 2).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
			}
		}

		// ����༭�����У���ɺ����õ�Ԫ�񲻻���
		if (colIndex > START_NOC && (colIndex - START_NOC) % 5 == 3) {
			row.getCell(colIndex).getStyleAttributes().setWrapText(false);
		}

		// ����С��
		addSumRow();

		Boolean isBack = (Boolean) row.getCell("isBack").getValue();
		if (isBack != null && isBack.booleanValue()) {
			Object old = e.getOldValue();
			Object cur = e.getValue();
			old = old == null ? "" : old;
			cur = cur == null ? "" : cur;
			if (!old.equals(cur)) {
				row.getCell("isEdit").setValue(Boolean.TRUE);
				row.getStyleAttributes().setBackground(Color.PINK);
			}
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (prmtDeptment.getValue() == null) {
			MsgBox.showWarning("�༭���Ų���Ϊ�գ� ");
			abort();
		}
		//R110510-1166����ͬ�¶ȹ�������ƻ����ñ������󣬱��벻������  by zhiyuan_tang 2010-05-12
//		if (FDCHelper.isEmpty(txtNumber.getText())) {
//			MsgBox.showWarning("���벻��Ϊ�գ� ");
//			abort();
//		}
		if (FDCHelper.isEmpty(txtName.getText())) {
			MsgBox.showWarning("�������Ʋ���Ϊ�գ� ");
			abort();
		}

		// У���ͬ�ƻ���¼
		Set num = new HashSet();
		Set name = new HashSet();
		for (int i = 0; i < tblContract.getRowCount(); i++) {
			IRow row = tblContract.getRow(i);
			if ("(С��)".equals(row.getCell("project").getValue())) {
				continue;
			}
			Object value = tblContract.getCell(i, "conNumber").getValue();
			if (value == null) {
				MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "�к�ͬ����Ϊ�գ�");
				abort();
			} else {
				ContractBillInfo con = (ContractBillInfo) value;
				if (num.contains(con.getId())) {
					MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "�к�ͬ�����ظ���");
					abort();
				} else {
					num.add(con.getId());
				}
			}

			for (int j = START_CON; j < tblContract.getColumnCount(); j += 5) {
				String title = (String) tblContract.getHeadRow(0).getCell(j)
						.getValue();
				//added by shagnjing ����ƻ�֧�����Ϊ0  �ֶΡ��������͡����ÿ�˵�������Բ��
				BigDecimal planpay = new BigDecimal("0.00");
				if(tblContract.getCell(i, j + 1).getValue() != null){
					planpay = (BigDecimal) tblContract.getCell(i, j + 1).getValue();
				}
				//
				if (FDCHelper.isEmpty(tblContract.getCell(i, j + 1).getValue())) {
					MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "��" + title
							+ "�ƻ�֧������Ϊ�գ�");
					abort();
				} else if (planpay.compareTo(new BigDecimal(0))>0 && FDCHelper.isEmpty(tblContract.getCell(i, j + 2)
						.getValue())) {
					MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "��" + title
							+ "�������Ͳ���Ϊ�գ�");
					abort();
				} else if (planpay.compareTo(new BigDecimal(0))>0 && FDCHelper.isEmpty(tblContract.getCell(i, j + 3)
						.getValue())) {
					MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "��" + title
							+ "�ÿ�˵������Ϊ�գ�");
					abort();
				}
			}
		}

		// У���ǩ����ͬ�ƻ���¼
		num = new HashSet();
		for (int i = 0; i < tblUnsettledCon.getRowCount(); i++) {
			IRow row = tblUnsettledCon.getRow(i);
			if ("(С��)".equals(row.getCell("project").getValue())) {
				continue;
			}
			if (FDCHelper.isEmpty(tblUnsettledCon.getCell(i, "project")
					.getValue())) {
				MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "�й�����Ŀ����Ϊ�գ�");
				abort();
			}
			if (FDCHelper.isEmpty(tblUnsettledCon.getCell(i,
					"unsettledConNumber").getValue())) {
				MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "�д�ǩ����ͬ�����벻��Ϊ�գ�");
				abort();
			} else if (num.contains(tblUnsettledCon.getCell(i,
					"unsettledConNumber").getValue())) {
				MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "�д�ǩ����ͬ�����ظ���");
				abort();
			} else {
				num.add(tblUnsettledCon.getCell(i, "unsettledConNumber")
						.getValue());
			}
			if (FDCHelper.isEmpty(tblUnsettledCon
					.getCell(i, "unsettledConName").getValue())) {
				MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "�д�ǩ����ͬ���Ʋ���Ϊ�գ�");
				abort();
			} else if (name.contains(tblUnsettledCon.getCell(i,
					"unsettledConName").getValue())) {
				MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "�д�ǩ����ͬ�����ظ���");
				abort();
			} else {
				name.add(tblUnsettledCon.getCell(i, "unsettledConName")
						.getValue());
			}
			if (FDCHelper.isEmpty(tblUnsettledCon.getCell(i,
					"unsettledConestPrice").getValue())) {
				MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "��Ԥ��ǩԼ����Ϊ�գ�");
				abort();
			}
			for (int j = START_UNC; j < tblUnsettledCon.getColumnCount(); j += 5) {
				String title = (String) tblUnsettledCon.getHeadRow(0)
						.getCell(j).getValue();
				 
				//added by shagnjing ����ƻ�֧�����Ϊ0  �ֶΡ��������͡����ÿ�˵�������Բ��
				BigDecimal planpay = new BigDecimal("0.00");
				if(tblUnsettledCon.getCell(i, j+1 ).getValue() != null){
					planpay = (BigDecimal) tblUnsettledCon.getCell(i, j + 1).getValue();
				}
				//
				
				if (FDCHelper.isEmpty(tblUnsettledCon.getCell(i, j + 1)
						.getValue())) {
					MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "��" + title
							+ "�ƻ�֧������Ϊ�գ�");
					abort();
				} else if (planpay.compareTo(new BigDecimal(0))>0 && FDCHelper.isEmpty(tblUnsettledCon.getCell(i, j + 2)
						.getValue())) {
					MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "��" + title
							+ "�������Ͳ���Ϊ�գ�");
					abort();
				} else if (planpay.compareTo(new BigDecimal(0))>0 && FDCHelper.isEmpty(tblUnsettledCon.getCell(i, j + 3)
						.getValue())) {
					MsgBox.showWarning("��ǩ����ͬ����ƻ���" + (i + 1) + "��" + title
							+ "�ÿ�˵������Ϊ�գ�");
					abort();
				}
			}
		}

		// У���޺�ͬ�ƻ���¼
		num = new HashSet();
		name = new HashSet();
		for (int i = 0; i < tblNoContract.getRowCount(); i++) {
			IRow row = tblNoContract.getRow(i);
			if ("(С��)".equals(row.getCell("project").getValue())) {
				continue;
			}
			if (FDCHelper.isEmpty(tblNoContract.getCell(i, "project")
					.getValue())) {
				MsgBox.showWarning("�޺�ͬ����ƻ���" + (i + 1) + "�й�����Ŀ����Ϊ�գ�");
				abort();
			}
			if (FDCHelper.isEmpty(tblNoContract.getCell(i, "payMatters")
					.getValue())) {
				MsgBox.showWarning("�޺�ͬ����ƻ���" + (i + 1) + "�и���������벻��Ϊ�գ�");
				abort();
			} else if (num.contains(tblNoContract.getCell(i, "payMatters")
					.getValue())) {
				MsgBox.showWarning("�޺�ͬ����ƻ���" + (i + 1) + "�и�����������ظ���");
				abort();
			} else {
				num.add(tblNoContract.getCell(i, "payMatters").getValue());
			}

			if (FDCHelper.isEmpty(tblNoContract.getCell(i, "payMattersName")
					.getValue())) {
				MsgBox.showWarning("�޺�ͬ����ƻ���" + (i + 1) + "�и����������Ʋ���Ϊ�գ�");
				abort();
			} else if (name.contains(tblNoContract.getCell(i, "payMattersName")
					.getValue())) {
				MsgBox.showWarning("�޺�ͬ��ͬ����ƻ���" + (i + 1) + "�и������������ظ���");
				abort();
			} else {
				name.add(tblNoContract.getCell(i, "payMattersName").getValue());
			}

			for (int j = START_NOC; j < tblNoContract.getColumnCount(); j += 5) {
				String title = (String) tblNoContract.getHeadRow(0).getCell(j)
						.getValue();
				
				//added by shagnjing
				BigDecimal planpay = new BigDecimal("0.00");
				if(tblNoContract.getCell(i, j+1 ).getValue() != null){
					planpay = (BigDecimal) tblNoContract.getCell(i, j + 1).getValue();
				}
				//
				
				if (FDCHelper.isEmpty(tblNoContract.getCell(i, j + 1)
						.getValue())) {
					MsgBox.showWarning("�޺�ͬ����ƻ���" + (i + 1) + "��" + title
							+ "�ƻ�֧������Ϊ�գ�");
					abort();
				} else if (planpay.compareTo(new BigDecimal(0))>0 && FDCHelper.isEmpty(tblNoContract.getCell(i, j + 2)
						.getValue())) {
					MsgBox.showWarning("�޺�ͬ����ƻ���" + (i + 1) + "��" + title
							+ "�������Ͳ���Ϊ�գ�");
					abort();
				} else if (planpay.compareTo(new BigDecimal(0))>0 && FDCHelper.isEmpty(tblNoContract.getCell(i, j + 3)
						.getValue())) {
					MsgBox.showWarning("�޺�ͬ����ƻ���" + (i + 1) + "��" + title
							+ "�ÿ�˵������Ϊ�գ�");
					abort();
				}
			}
		}
		
		//У��ò��Ÿ��·����Ƿ��Ѿ����ƹ������¶ȹ�������ƻ�
		AdminOrgUnitInfo admin = (AdminOrgUnitInfo) prmtDeptment.getValue();
		checkHasMonthDepHasPlan(admin, spYear.getIntegerVlaue(), spMonth.getIntegerVlaue());
	}

	/**
	 * 
	 * ��������ǰ��¼�û��Ƿ����Ƶ���
	 * @return true �ǣ�false ����
	 * @Author��owen_wen
	 * @CreateTime��2011-12-21
	 */
	private boolean isCreator() {
		String creatorID = editData.getCreator() == null ? "" : editData.getCreator().getId().toString();
		return creatorID.equals(SysContext.getSysContext().getCurrentUserInfo().getId().toString());
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// ���������޸�������������ʱ���ٴ���������
		if (editData.getId() != null && isInWorkflow && !isCreator()) {
			storeFields();
			getBizInterface().save(editData);
		} else {//�Ƶ����ڹ��������޸��Լ��ĵ��ݣ��ύ��Ҫ��������
			if ((flag_state != null) && (flag_state.equals("revist"))) {
				this.editData.setState(FDCBillStateEnum.REVISING);
				this.editData.setAuditor(SysContext.getSysContext().getCurrentUserInfo());
				this.editData.setAuditTime(auditTime);
				FDCDepConPayPlanBillFactory.getRemoteInstance().save(this.editData);

				storeFields();
				this.editData.setState(FDCBillStateEnum.SUBMITTED);
				java.text.DecimalFormat myformat = new java.text.DecimalFormat("#0.0");
				double version = Double.parseDouble(this.editData.getVersion()) + 0.1;
				this.editData.setVersion(myformat.format(version));

				clearID();
				loadFields();
				promAuditor.setValue(null);
				picAuditorTime.setValue(null);
				flag_state = null;
			}
			super.actionSubmit_actionPerformed(e);
		}
		
		//ken_liu..��ͨ�����鿴���ݡ��򿪣��޸Ľ��е��ύ�����ύ����µ��༶��������
		Object obj = this.getUIContext().get("Owner");
		if(obj instanceof MultiApproveUI) {
			MultiApproveUI owner = (MultiApproveUI) obj;
			CoreUIObject ui = owner.getBillUI();
			if(ui instanceof FDCDepConPayPlanEditUI) {
				ui.setDataObject(this.editData);
				ui.loadFields();
			}
			this.destroyWindow();
		}
	}

	/**
	 * ɾ��
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		getUIContext().put("department",
				editData.getDeptment().castToFullOrgUnitInfo());
		super.actionRemove_actionPerformed(e);
	}

	protected void clearID() {
		editData.setId(null);
		FDCDepConPayPlanContractCollection hasCon = editData.getHasContract();
		for (int i = 0; i < hasCon.size(); i++) {
			hasCon.get(i).setId(null);
			hasCon.get(i).setIsBack(false);
			FDCDepConPayPlanContractEntryCollection entrys = hasCon.get(i)
					.getEntrys();
			for (int j = 0; j < entrys.size(); j++) {
				entrys.get(j).setId(null);
			}
		}
		FDCDepConPayPlanUnsettledConCollection unCon = editData
				.getUnsettledCon();
		for (int i = 0; i < unCon.size(); i++) {
			unCon.get(i).setId(null);
			unCon.get(i).setIsBack(false);
			FDCDepConPayPlanUnsettleEntryCollection entrys = unCon.get(i)
					.getEntrys1();
			for (int j = 0; j < entrys.size(); j++) {
				entrys.get(j).setId(null);
			}
		}
		FDCDepConPayPlanNoContractCollection noCon = editData.getNoContract();
		for (int i = 0; i < noCon.size(); i++) {
			noCon.get(i).setId(null);
			noCon.get(i).setIsBack(false);
			FDCDepConPayPlanNoContractEntryCollection entrys = noCon.get(i)
					.getEntrys1();
			for (int j = 0; j < entrys.size(); j++) {
				entrys.get(j).setId(null);
			}
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		getUIContext().put("department",
				editData.getDeptment().castToFullOrgUnitInfo());
		super.actionAddNew_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		//�Ѿ��ύ�ĵ��ݱ��水ť��Ϊ����  add by Jian_cao BT721835
		if (FDCBillStateEnum.SUBMITTED.equals(this.editData.getState())) {
			this.actionSave.setEnabled(false);
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
	}

	public void actionViewContract_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.pnlBig.getSelectedIndex() == 1) {
			IRow row = KDTableUtil.getSelectedRow(tblContract);
			if (row == null) {
				MsgBox.showWarning(this, "����ѡ����!");
				abort();
			}
			ContractBillInfo con = (ContractBillInfo) row.getCell("conNumber")
					.getValue();
			String id = null;
			if (con != null) {
				id = con.getId().toString();
			}
			if (id == null) {
				MsgBox.showWarning(this, "ѡ�з�¼��ͬ������!");
				abort();
			}

			String uiName = ContractBillEditUI.class.getName();
			Map ctx = new UIContext(this);
			ctx.put(UIContext.ID, id);
			ctx.put(UIContext.OWNER, this);
			ctx.put("isFromCon", Boolean.TRUE);
			
			IUIWindow uiWindow = null;
			// UIFactoryName.MODEL Ϊ����ģʽ
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
					uiName, ctx, null, OprtState.VIEW);
			// ��ʼչ��UI
			uiWindow.show();

		}
	}

	public void actionPublish_actionPerformed(ActionEvent e) throws Exception {
		if (MsgBox.showConfirm2("�Ƿ�ȷ���ϱ���") == MsgBox.CANCEL) {
			return;
		}
		if (this.editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
			this.editData.setState(FDCBillStateEnum.PUBLISH);
			String id = getSelectBOID();
			if (id != null) {
				FDCDepConPayPlanBillFactory.getRemoteInstance().setPublish(
						BOSUuid.read(id));
			}
			actionPublish.setEnabled(false);
		}
	}
	
	/**
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#actionSave_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {

		if ((flag_state != null) && (flag_state.equals("revist"))) {
			this.editData.setState(FDCBillStateEnum.REVISING);
			this.editData.setAuditor(SysContext.getSysContext().getCurrentUserInfo());
			this.editData.setAuditTime(auditTime);
			FDCDepConPayPlanBillFactory.getRemoteInstance().save(this.editData);

			storeFields();
			this.editData.setState(FDCBillStateEnum.SAVED);
			java.text.DecimalFormat myformat = new java.text.DecimalFormat("#0.0");
			double version = Double.parseDouble(this.editData.getVersion()) + 0.1;
			this.editData.setVersion(myformat.format(version));

			clearID();
			loadFields();
			promAuditor.setValue(null);
			picAuditorTime.setValue(null);
			flag_state = null;
		}
		super.actionSave_actionPerformed(e);
	}
	
	public void actionRevise_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getState().equals(FDCBillStateEnum.BACK)) {
			flag_state = "revist";
			if (picAuditorTime.getValue() != null) {
				auditTime = (Date) picAuditorTime.getValue();
			}
			super.actionEdit_actionPerformed(e);
			actionSave.setEnabled(false);
			actionAddNew.setEnabled(false);
			actionRemove.setEnabled(false);
			txtName.setEnabled(false);
			txtNumber.setEnabled(false);
			prmtDeptment.setEnabled(false);
			spMonth.setEnabled(false);
			spYear.setEnabled(false);
			description.setEnabled(false);
			actionRevise.setEnabled(false);
			promAuditor.setValue(null);
			picAuditorTime.setValue(null);
		} else {
			MsgBox.showWarning(this, "����صļƻ������޶�!");
		}
	}

	public void actionIntroPre_actionPerformed(ActionEvent e) throws Exception {
		if (FDCHelper.isEmpty(prmtDeptment.getValue())) {
			MsgBox.showWarning(this, "���Ʋ��Ų���Ϊ��!");
			abort();
		}
		// ȡ�����¼ƻ�ֵ
		FDCDepConPayPlanBillInfo plan = getPreMonthConInfo();
		if (plan == null) {
			MsgBox.showWarning(this, "��������һ�µ����������ϱ��ĸ���ƻ�");
			abort();
		}
		// ��ֵ
		int year = spYear.getIntegerVlaue().intValue();
		int month = spMonth.getIntegerVlaue().intValue();
		month--;
		if (month < 1) {
			year--;
			month = 12;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1, 0, 0, 0);

		if (this.pnlBig.getSelectedIndex() == 1) {
			indroProCon(plan, cal);
		} else if (this.pnlBig.getSelectedIndex() == 2) {
			introPreUnCon(plan, cal);
		} else if (this.pnlBig.getSelectedIndex() == 3) {
			introProNoCon(plan, cal);
		}
		// ɾ���ϼ�����(����ֻɾ�����һ�У���Ϊǰ��ѭ������ʱ���ϼ��в��������)
		for (int i = getDetailTable().getRowCount() - 1; i >= 0; i--) {
			IRow totalRow = getDetailTable().getRow(i);
			if (totalRow != null
					&& "С��".equals(totalRow.getCell("project").getValue())) {
				getDetailTable().removeRow(totalRow.getRowIndex());
			}
		}
		addSumRow();
	}

	/**
	 * 
	 * �������������¸���ƻ������ı���ͬ��
	 * @Author��owen_wen
	 * @CreateTime��2012-3-1
	 */
	private void introProNoCon(FDCDepConPayPlanBillInfo plan, Calendar cal) {
		FDCDepConPayPlanNoContractCollection noCon = plan.getNoContract();
		if (noCon == null || noCon.size() < 1) {
			MsgBox.showWarning(this, "���µĸ���ƻ��������޺�ͬ����ƻ�");
		} else {
			for (int i = 0; i < noCon.size(); i++) {
				FDCDepConPayPlanNoContractInfo info = noCon.get(i);
				String payMatters = info.getPayMatters();
				if (!isHasPlanInTable("noContract", payMatters)) {
					IRow row = tblNoContract.addRow();
					row.getCell("project").setValue(info.getProject());
					row.getCell("payMatters").setValue(info.getPayMatters());
					row.getCell("payMattersName").setValue(info.getPayMattersName());
					for (int j = 0; j < info.getEntrys1().size(); j++) {
						FDCDepConPayPlanNoContractEntryInfo entry = info.getEntrys1().get(j);
						if (entry.getMonth().compareTo(cal.getTime()) >= 0) {
							Calendar date = Calendar.getInstance();
							date.setTime(entry.getMonth());
							String keyHead = "MONTH" + date.get(Calendar.YEAR) + "" + (date.get(Calendar.MONTH) + 1);
							row.getCell(keyHead + "plan").setValue(entry.getPlanPay());
							row.getCell(keyHead + "define").setValue(entry.getMoneyDefine());
							row.getCell(keyHead + "explain").setValue(entry.getExplain());
							row.getCell(keyHead + "official").setValue(entry.getOfficialPay());
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * �������������¸���ƻ�����ǩ����ͬ��
	 * @Author��owen_wen
	 * @CreateTime��2012-3-1
	 */
	private void introPreUnCon(FDCDepConPayPlanBillInfo plan, Calendar cal) {
		FDCDepConPayPlanUnsettledConCollection unCon = plan.getUnsettledCon();
		if (unCon == null || unCon.size() < 1) {
			MsgBox.showWarning(this, "���µĸ���ƻ���������ǩ����ͬ����ƻ�");
		} else {
			for (int i = 0; i < unCon.size(); i++) {
				FDCDepConPayPlanUnsettledConInfo info = unCon.get(i);
				String conNum = info.getUnConNumber();
				if (!isHasPlanInTable("unContract", conNum)) {
					IRow row = tblUnsettledCon.addRow();
					row.getCell("project").setValue(info.getProject());
					row.getCell("unsettledConNumber").setValue(info.getUnConNumber());
					row.getCell("unsettledConName").setValue(info.getUnConName());
					row.getCell("unsettledConestPrice").setValue(info.getPlanAmount());
					for (int j = 0; j < info.getEntrys1().size(); j++) {
						FDCDepConPayPlanUnsettleEntryInfo entry = info.getEntrys1().get(j);
						if (entry.getMonth().compareTo(cal.getTime()) >= 0) {
							Calendar date = Calendar.getInstance();
							date.setTime(entry.getMonth());
							String keyHead = "MONTH" + date.get(Calendar.YEAR) + "" + (date.get(Calendar.MONTH) + 1);
							row.getCell(keyHead + "plan").setValue(entry.getPlanPay());
							row.getCell(keyHead + "define").setValue(entry.getMoneyDefine());
							row.getCell(keyHead + "explain").setValue(entry.getExplain());
							row.getCell(keyHead + "official").setValue(entry.getOfficialPay());
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * �������������µĸ���ƻ�����ǩ����ͬ��
	 * @Author��owen_wen
	 * @CreateTime��2012-3-1
	 */
	private void indroProCon(FDCDepConPayPlanBillInfo plan, Calendar cal) throws EASBizException, BOSException {
		FDCDepConPayPlanContractCollection hasCon = plan.getHasContract();
		if (hasCon == null || hasCon.size() < 1) {
			MsgBox.showWarning(this, "���µĸ���ƻ���������ǩ����ͬ����ƻ�");
		} else {
			for (int i = 0; i < hasCon.size(); i++) {
				FDCDepConPayPlanContractInfo info = hasCon.get(i);
				String conID = info.getContract().getId().toString();
				if (!isHasPlanInTable("contract", conID)) {
					IRow row = tblContract.addRow();
					row.getCell("project").setValue(info.getProject());
					row.getCell("conNumber").setValue(info.getContract());
					row.getCell("conName").setValue(info.getContractName());
					setColumunValues(calConInfo(info.getContract().getId().toString()), row);
					for (int j = 0; j < info.getEntrys().size(); j++) {
						FDCDepConPayPlanContractEntryInfo entry = info.getEntrys().get(j);
						if (entry.getMonth().compareTo(cal.getTime()) >= 0) {
							Calendar date = Calendar.getInstance();
							date.setTime(entry.getMonth());
							String keyHead = "MONTH" + date.get(Calendar.YEAR) + "" + (date.get(Calendar.MONTH) + 1);
							row.getCell(keyHead + "plan").setValue(entry.getPlanPay());
							row.getCell(keyHead + "define").setValue(entry.getMoneyDefine());
							row.getCell(keyHead + "explain").setValue(entry.getExplain());
							row.getCell(keyHead + "official").setValue(entry.getOfficialPay());
						}
					}
				}
			}
		}
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
		addSumRow();
	}

	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(getDetailTable());
		if (row == null) {
			MsgBox.showWarning(this, "����ѡ���У�");
			abort();
		} else if ("(С��)".equals(row.getCell("project").getValue())) {
			MsgBox.showWarning(this, "����ɾ��С���У�");
			abort();
		} else {
			super.actionRemoveLine_actionPerformed(e);
			addSumRow();
		}
	}

	/**
	 * 
	 * ��������ȡ���¸���ƻ�
	 * @Author��owen_wen
	 * @CreateTime��2012-3-1
	 */
	protected FDCDepConPayPlanBillInfo getPreMonthConInfo() throws BOSException, Exception {
		FDCDepConPayPlanBillInfo plan = null;

		int year = spYear.getIntegerVlaue().intValue();
		int month = spMonth.getIntegerVlaue().intValue();
		month--;
		if (month < 1) {
			year--;
			month = 12;
		}
		AdminOrgUnitInfo admin = (AdminOrgUnitInfo) prmtDeptment.getValue();
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(getSelectors());
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("deptment.id", admin.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
		filter.getFilterItems().add(new FilterItemInfo("month", new Integer(month)));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.PUBLISH_VALUE));
		filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		view.setFilter(filter);
		FDCDepConPayPlanBillCollection col = ((IFDCDepConPayPlanBill) getBizInterface()).getFDCDepConPayPlanBillCollection(view);
		if (col != null && col.size() > 0) {
			plan = col.get(0);
		}

		return plan;
	}

	/**
	 * ���ݺ�ͬID����������ۡ��ۼ��깤���ۼ��Ѹ����̿�ۼ�δ�����̿�
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected Map calConInfo(String conID) throws EASBizException, BOSException {
		Map values = new HashMap();
		// ��������
		Map param = new HashMap();
		Integer year = (Integer) spYear.getValue();
		String date = "";

		if (month_old.intValue() == 12) {
			date = "{ts '" + (year.intValue() + 1) + "-1-1 00:00:00'}";
		} else {
			date = "{ts '" + year.intValue() + "-" + (month_old.intValue() + 1)
					+ "-1 00:00:00'}";
		}
		param.put("date", date);
		param.put("conID", conID);
		param.put("separteFromPayment", Boolean.valueOf(separteFromPayment));
		values = FDCDepConPayPlanBillFactory.getRemoteInstance().getPlanPay(param);
		// �õ���ͬ�������
		values.put("conPrice", FDCUtils.getLastAmt_Batch(null, new String[] { conID }).get(conID));
		return values;
	}

	/**
	 * ���á���ǩ����ͬ��ҳǩ�ϵ� ��ͬ������ۡ��ۼ��깤���ۼ��Ѹ����̿�ۼ�δ�����̿�ۼ���;���
	 * @Author��owen_wen
	 * @CreateTime��2012-8-28
	 */
	private void setColumunValues(Map values, IRow row) {
		row.getCell("conPrice").setValue(values.get("conPrice"));
		row.getCell("lastMonthPayable").setValue(values.get("payable"));
		row.getCell("lastMonthPay").setValue(values.get("pay"));
		row.getCell("lastMonthNopay").setValue(values.get("noPay"));
		row.getCell("lastMonthEnRoute").setValue(values.get("enRoute"));
	}

	protected boolean isHasPlanInTable(String table, String key) {
		boolean isHasPlan = false;

		if ("contract".equals(table)) {
			for (int i = 0; i < tblContract.getRowCount(); i++) {
				ContractBillInfo con = (ContractBillInfo) tblContract.getCell(
						i, "conNumber").getValue();
				if (!FDCHelper.isEmpty(con)) {
					String id = con.getId().toString();
					if (id.equals(key)) {
						return true;
					}
				}
			}
		} else if ("unContract".equals(table)) {
			for (int i = 0; i < tblUnsettledCon.getRowCount(); i++) {
				String con = (String) tblUnsettledCon.getCell(i,
						"unsettledConNumber").getValue();
				if (!FDCHelper.isEmpty(con) && con.equals(key)) {
					return true;
				}
			}
		} else if ("noContract".equals(table)) {
			for (int i = 0; i < tblNoContract.getRowCount(); i++) {
				String con = (String) tblNoContract.getCell(i, "payMatters")
						.getValue();
				if (!FDCHelper.isEmpty(con) && con.equals(key)) {
					return true;
				}
			}
		}

		return isHasPlan;
	}

	protected IObjectValue createNewData() {
		FDCDepConPayPlanBillInfo objectValue = new FDCDepConPayPlanBillInfo();

		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setCreateTime(new Timestamp(serverDate.getTime()));
		objectValue.setCurProject(this.curProject);
		FullOrgUnitInfo fullOrg = (FullOrgUnitInfo) getUIContext().get(
				"department");
		
		AdminOrgUnitInfo adminOrg = null;
		if (fullOrg != null ) {
			try {
				FullOrgUnitInfo unitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo("where id = '" +fullOrg.getId().toString()+ "'");
				
				if(unitInfo.isIsAdminOrgUnit()){
					adminOrg = AdminOrgUnitFactory.getRemoteInstance()
					.getAdminOrgUnitInfo(new ObjectUuidPK(fullOrg.getId()));
					objectValue.setDeptment(adminOrg);
				}
			} catch (EASBizException e) {
				handUIException(e);
			} catch (BOSException e) {
				handUIException(e);
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(serverDate);
		while (hasMonthHasPlan(adminOrg, new Integer(cal.get(Calendar.YEAR)),
				new Integer(cal.get(Calendar.MONTH) + 1))) {
			cal.add(Calendar.MONTH, 1);
		}

		objectValue.setYear(cal.get(Calendar.YEAR));
		objectValue.setMonth(cal.get(Calendar.MONTH) + 1);
		
		year_old = new Integer(objectValue.getYear());
		month_old = new Integer(objectValue.getMonth());

		objectValue.setVersion(VERSION);

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("cycle");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		try {
			FDCDataBaseCollection col = PayPlanCycleFactory.getRemoteInstance()
					.getFDCDataBaseCollection(view);
			if (col != null && col.size() > 0) {
				PayPlanCycleInfo info = (PayPlanCycleInfo) col.get(0);
				objectValue.setPayPlanCycle(info);
			} else {
				MsgBox.showWarning(this, "��������һ�׸���ƻ�����!");
				abort();
			}
		} catch (BOSException e) {
			handUIException(e);
		}

		return objectValue;
	}

	protected void addLine(KDTable table) {
		if (table == null) {
			return;
		}
		int count = table.getRowCount();
		if (count > 0
				&& "(С��)".equals(table.getCell(count - 1, "project").getValue())) {
			table.addRow(table.getRowCount() - 1);
		} else {
			table.addRow();
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
	}

	// �������Ƿ����ѡ���������ŵ���Ա
	private boolean canSelecOtherOrgPerson() {
		boolean canSelectOtherOrgPerson = false;
		try {
			canSelectOtherOrgPerson = FDCUtils.getDefaultFDCParamByKey(null,
					null, FDCConstants.FDC_PARAM_SELECTPERSON);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return canSelectOtherOrgPerson;
	}

	private SelectorItemCollection getSimpleSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		return sic;
	}

	protected void initDataStatus() {
		super.initDataStatus();
		actionPublish.setEnabled(false);
		actionRevise.setEnabled(false);
		actionIntroPre.setEnabled(false);
		actionAddLine.setEnabled(false);
		actionRemoveLine.setEnabled(false);

		if (!"revist".equals(flag_state)
				&& STATUS_ADDNEW.equals(getOprtState())
				|| STATUS_EDIT.equals(getOprtState())) {
			actionIntroPre.setEnabled(true);
			actionAddLine.setEnabled(true);
			actionRemoveLine.setEnabled(true);
		}

		if (editData != null) {
			if (FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
				actionPublish.setEnabled(true);
			} else if (FDCBillStateEnum.BACK.equals(editData.getState())) {
				actionRevise.setEnabled(true);
				actionEdit.setEnabled(false);
			}
		}
		actionSave.setVisible(true);
	}

	public boolean isModify() {
		return false;
	}
	
	/**
	 * ����total<HashMap>��ֵ����д���ܱ�<br>
	 * total����loadEntryData()�����
	 */
	protected void fillSummaryTable() {
		if (total != null && total.size() > 0) {
			Map.Entry details;
			Iterator it = total.entrySet().iterator();
			while (it.hasNext()) {
				details = (Map.Entry) it.next();
				String depName = (String) details.getKey();
				Map values = (Map) details.getValue();
				IRow row = tblTotal.addRow();
				row.getCell("project").setValue(depName);
				if (values != null && values.size() > 0) {
					Iterator month = values.entrySet().iterator();
					while (month.hasNext()) {
						details = (Entry) month.next();
						String key = (String) details.getKey();
						BigDecimal value = (BigDecimal) details.getValue();
						row.getCell(key).setValue(value);
					}
				}
			}
		}
		COLS_TOL = new int[tblTotal.getColumnCount() - 1];
		for (int i = 1; i < tblTotal.getColumnCount(); i++) {
			COLS_TOL[i - 1] = i;
		}
		IRow row = FDCTableHelper.calcSumForTable(0, tblTotal.getRowCount() - 1, COLS_TOL, tblTotal);
		if (row != null) {
			row.getCell("project").setValue("�ϼ�");
		}
	}
	/**
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#fetchInitParam()
	 */
	protected void fetchInitParam() throws Exception {
		super.fetchInitParam();
		// ����δ���ã����ֹ�¼���ۼ��깤���
		separteFromPayment = FDCUtils.getBooleanValue4FDCParamByKey(null,
				SysContext.getSysContext().getCurrentOrgUnit().getId().toString(), "FDC317_SEPARATEFROMPAYMENT");
	}
	
	/**
	 * ��ȡ ����滮/��ͬ����ƻ� ���¶ȸ�����
	 * @param conBill
	 * @return
	 * @throws Exception
	 */
	private Map getContractPayPlanData(String type, String id) throws Exception  {
		Map data = new HashMap();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("Data.payMonth");
		selector.add("Data.payAmount");
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(selector);
		FilterInfo filter = new FilterInfo();
		if("contract".equals(type)) {
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", id, CompareType.EQUALS));
			view.setFilter(filter);
			ConPayPlanCollection payPlans = ConPayPlanFactory.getRemoteInstance().getConPayPlanCollection(view);
			if(null != payPlans && payPlans.size() > 0) {
				ConPayPlanDataCollection payPlanDatas = payPlans.get(0).getData();
				for(int i = 0; i < payPlanDatas.size(); i++) {
					PayPlanDataBaseInfo ppData = payPlanDatas.get(i);
					// �·�С��10ʱ��ȥ��ǰ��� 0
					String key = "MONTH" + (ppData.getPayMonth()/100) + ppData.getPayMonth() % 100;
					data.put(key, ppData.getPayAmount());
				}
			}
		}
		if("programming".equals(type)) {
			filter.getFilterItems().add(new FilterItemInfo("programming.id", id, CompareType.EQUALS));
			view.setFilter(filter);
			PayPlanNewCollection payPlans = PayPlanNewFactory.getRemoteInstance().getPayPlanNewCollection(view);
			if(null != payPlans && payPlans.size() > 0) {
				PayPlanNewDataCollection payPlanDatas = payPlans.get(0).getData();
				for(int i = 0; i < payPlanDatas.size(); i++) {
					PayPlanDataBaseInfo ppData = payPlanDatas.get(i);
					// �·�С��10ʱ��ȥ��ǰ��� 0
					String key = "MONTH" + (ppData.getPayMonth()/100) + ppData.getPayMonth() % 100;
					data.put(key, ppData.getPayAmount());
				}
			}
		}
		return data;
	}
}
