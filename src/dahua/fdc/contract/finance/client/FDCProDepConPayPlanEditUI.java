package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUI;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PayPlanCycleFactory;
import com.kingdee.eas.fdc.basedata.PayPlanCycleInfo;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaUtil;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettleEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanFactory;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractEntryCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsetEntryCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsetEntryInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledInfo;
import com.kingdee.eas.fdc.finance.IFDCProDepConPayPlan;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;


/**
 * 描述:项目月度资金滚动需求计划
 * @author owen_wen  date:2011-12-21
 * @version EAS6.1
 */
public class FDCProDepConPayPlanEditUI extends
		AbstractFDCProDepConPayPlanEditUI {

	// 表格滚动列起始序号
	private static final int START_TOL = 2;
	private static final int START_CON = 11;
	private static final int START_UNC = 8;
	private static final int START_NOC = 7;

	private Integer year_old = null;
	private Integer month_old = null;

	private boolean isSaved = false;

	// 初始版本
	private String VERSION = "1.0";

	// 是否重新汇总过
	private boolean isReSum = false;

	// 计划状态(修订、修改)
	String flag_state = "";

	// 需要合计的列
	private int[] COLS_TOL;
	private int[] COLS_CON;
	private int[] COLS_UNC;
	private int[] COLS_NOC;

	// 统计表Map，用于在统计表显示3个分录统计值，在loadEntryData里面计算
	Map total;
	//增加以下map 以供工作流中修改最终批复金额用。 By ken_liu
	Map map_Total_Total = new HashMap();	//汇总页签：prjLongNumber-->合计行
	Map map_SubTotal_CON = new HashMap();	//已签订合同：prjLongNumber+depNumber-->小计行
	Map map_Total_CON = new HashMap();		//已签订合同：prjLongNumber-->合计行
	Map map_SubTotal_UNC = new HashMap();	//待签订合同：prjLongNumber+depNumber-->小计行
	Map map_Total_UNC = new HashMap();		//待签订合同：prjLongNumber-->合计行
	Map map_SubTotal_NOC = new HashMap();	//无合同：prjLongNumber+depNumber-->小计行
	Map map_Total_NOC = new HashMap();		//无合同：prjLongNumber-->合计行

	Map newValueMap_CON = new HashMap();	//记录已签订合同中做过修改的分录:
	Map newValueMap_UNC = new HashMap();	//记录待签订合同中做过修改的分录:
	Map newValueMap_NOC = new HashMap();	//记录无合同中做过修改的分录:
		
	// 工程量确认流程与付款流程分离参数
	boolean separteFromPayment = true;

	public FDCProDepConPayPlanEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		detachListeners();
		super.loadFields();
		clearTable();
		checkIfCanEditOfficialCell();	//不放在onload是因为要在初始化表格前就要确定canEditOfficialCell
		initTableColumn();
		loadEntryData();
		CalcTotal();
		fillSummaryTable();
		attachListeners();
	}

	/**
	 * 清空4个表格所有数据
	 */
	protected void clearTable() {
		total = new TreeMap();
		tblTotal.removeRows();
		tblContract.removeRows();
		tblUnSetteled.removeRows();
		tblNoContract.removeRows();
		for (int i = tblTotal.getColumnCount() - 1; i >= START_TOL; i--) {
			tblTotal.removeColumn(i);
		}
		for (int i = tblContract.getColumnCount() - 1; i >= START_CON; i--) {
			tblContract.removeColumn(i);
		}
		for (int i = tblUnSetteled.getColumnCount() - 1; i >= START_UNC; i--) {
			tblUnSetteled.removeColumn(i);
		}
		for (int i = tblNoContract.getColumnCount() - 1; i >= START_NOC; i--) {
			tblNoContract.removeColumn(i);
		}
	}
	
	private boolean isFromWF() {
		return (this.getUIContext() != null) && ((Boolean) this.getUIContext().get(MultiApproveUI.IS_MULTI_APPROVE) == Boolean.TRUE);
	}

	/**
	 * 根据计划周期长度，初始化3个表格的动态列
	 */
	protected void initTableColumn() {
		tblTotal.checkParsed();
		tblContract.checkParsed();
		tblUnSetteled.checkParsed();
		tblNoContract.checkParsed();
		tblContract.getStyleAttributes().setLocked(true);
		tblUnSetteled.getStyleAttributes().setLocked(true);
		tblNoContract.getStyleAttributes().setLocked(true);
		if (editData.getPayPlanCycle() != null) {
			int cycle = editData.getPayPlanCycle().getCycle().getValue();
			int year = spYear.getIntegerVlaue().intValue();
			int month = spMonth.getIntegerVlaue().intValue();

			// 表头
			IRow tolHead0 = tblTotal.getHeadRow(0);
			IRow tolHead1 = tblTotal.getHeadRow(1);
			IRow conHead0 = tblContract.getHeadRow(0);
			IRow conHead1 = tblContract.getHeadRow(1);
			IRow unCHead0 = tblUnSetteled.getHeadRow(0);
			IRow unCHead1 = tblUnSetteled.getHeadRow(1);
			IRow noCHead0 = tblNoContract.getHeadRow(0);
			IRow noCHead1 = tblNoContract.getHeadRow(1);
			// 融合管理
			KDTMergeManager tolMager = tblTotal.getHeadMergeManager();
			KDTMergeManager conMarge = tblContract.getHeadMergeManager();
			KDTMergeManager unCMarge = tblUnSetteled.getHeadMergeManager();
			KDTMergeManager noCMarge = tblNoContract.getHeadMergeManager();
			// 列标
			int index = 0;
			for (int i = 0; i < cycle; i++) {
				if (month > 12) {
					year += 1;
					month = 1;
				}
				String monthStr;
				if (month < 10) {
					monthStr = year + "年0" + month + "月";
				} else {
					monthStr = year + "年" + month + "月";
				}
				String KeyHead = "MONTH" + year + "" + month;

				// 统计表加列
				IColumn col = tblTotal.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				col.setWidth(140);
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);
				tolHead0.getCell(index).setValue(monthStr);
				tolHead1.getCell(index).setValue("计划支付金额汇总");

				col = tblTotal.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "official");
				col.setWidth(140);
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);
				tolHead1.getCell(index).setValue("最终批复金额汇总");

				tolMager.mergeBlock(0, i * 2 + START_TOL, 0, START_TOL + i * 2 + 1);

				// 合同付款计划加列
				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				conHead0.getCell(index).setValue(monthStr);
				conHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);

				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				conHead1.getCell(index).setValue("计划支付金额");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));

				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "define");
				conHead1.getCell(index).setValue("款项类型");
				col.setEditor(getCellEditor("define"));

				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "explain");
				conHead1.getCell(index).setValue("用款说明");
				col.setEditor(getCellEditor("explain"));
				col.setWidth(200);

				col = tblContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "official");
				conHead1.getCell(index).setValue("最终批复金额");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				if (canEditOfficialCell) {
					col.getStyleAttributes().setLocked(false);
				} else {
					col.getStyleAttributes().setLocked(true);
				}
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				conMarge.mergeBlock(0, START_CON + (i * 5), 0, START_CON
						+ (i * 5) + 4);

				// 待签订合同付款计划加列
				col = tblUnSetteled.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				unCHead0.getCell(index).setValue(monthStr);
				unCHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);

				col = tblUnSetteled.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				unCHead1.getCell(index).setValue("计划支付金额");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));

				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				col = tblUnSetteled.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "define");
				unCHead1.getCell(index).setValue("款项类型");
				col.setEditor(getCellEditor("define"));

				col = tblUnSetteled.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "explain");
				unCHead1.getCell(index).setValue("用款说明");
				col.setEditor(getCellEditor("explain"));
				col.setWidth(200);

				col = tblUnSetteled.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "official");
				unCHead1.getCell(index).setValue("最终批复金额");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				if (canEditOfficialCell) {
					col.getStyleAttributes().setLocked(false);
				} else {
					col.getStyleAttributes().setLocked(true);
				}
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				unCMarge.mergeBlock(0, START_UNC + (i * 5), 0, START_UNC
						+ (i * 5) + 4);

				// 无合同付款计划加列
				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				noCHead0.getCell(index).setValue(monthStr);
				noCHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);

				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				noCHead0.getCell(index).setValue(monthStr);
				noCHead1.getCell(index).setValue("计划支付金额");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));

				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);
				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "define");
				noCHead1.getCell(index).setValue("款项类型");
				col.setEditor(getCellEditor("define"));

				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "explain");
				noCHead1.getCell(index).setValue("用款说明");
				col.setEditor(getCellEditor("explain"));
				col.setWidth(200);

				col = tblNoContract.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "official");
				noCHead1.getCell(index).setValue("最终批复金额");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				if (canEditOfficialCell) {
					col.getStyleAttributes().setLocked(false);
				} else {
					col.getStyleAttributes().setLocked(true);
				}
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				noCMarge.mergeBlock(0, START_NOC + (i * 5), 0, START_NOC
						+ (i * 5) + 4);

				month++;
			}
		}
	}

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
			return new KDTDefaultCellEditor(prmtDefine);
		} else if ("contract".equals(type)) {
			KDBizPromptBox prmtcontract = new KDBizPromptBox();
			prmtcontract.setDisplayFormat("$name$");
			prmtcontract.setEditFormat("$number$");
			prmtcontract.setCommitFormat("$number$");
			prmtcontract.setRequired(true);
			prmtcontract
					.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBillQueryForPayPlanQuery");
			return new KDTDefaultCellEditor(prmtcontract);
		} else if ("project".equals(type)) {
			KDBizPromptBox prmtCurProject = new KDBizPromptBox();
			prmtCurProject.setDisplayFormat("$name$");
			prmtCurProject.setEditFormat("$number$");
			prmtCurProject.setCommitFormat("$number$");
			prmtCurProject.setRequired(true);
			prmtCurProject
					.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");
			return new KDTDefaultCellEditor(prmtCurProject);
		} else if ("admin".equals(type)) {
			KDBizPromptBox prmtAdmin = new KDBizPromptBox();
			prmtAdmin.setDisplayFormat("$name$");
			prmtAdmin.setEditFormat("$number$");
			prmtAdmin.setCommitFormat("$number$");
			prmtAdmin.setRequired(true);
			prmtAdmin.setQueryInfo("com.kingdee.eas.sem.mp.app.AdminOrgUnitF7");
			return new KDTDefaultCellEditor(prmtAdmin);
		}
		return null;
	}

	StringBuilder sb = new StringBuilder();
	/**
	 * 获取应设置到最终批复金额中的数据。用于更新合同月度付款计划最终批复金额。
	 * @param depPlanId	页签分录对应的合同月度付款计划页签分录
	 * @param year	计划年
	 * @param month	计划月
	 * @return
	 */
	private String getUserObject4OfficialCell(String depPlanId, int year, int month) {
		//合同月度付款计划中的页签分录中的fmoth字段记录的是2013-05-14的形式.故统一。tochar函数不支持yyyymm,yyyy-mm。。。w也不想。。
		sb.setLength(0);
		sb.append(depPlanId).append("$$").append(year).append("-");
		if(month<10) {
			sb.append("0");
		}
		sb.append(month).append("-").append("15");	
		
		return sb.toString();
	}
	protected void loadEntryData() {
		// 加载合同计划
		if (editData.getHasContract() != null) {
			for (int i = 0; i < editData.getHasContract().size(); i++) {
				FDCProDepConPayPlanContractInfo hasCon = editData
						.getHasContract().get(i);
				AdminOrgUnitInfo dept = hasCon.getDepartment();

				// 用于计算部门统计的Map，从total中获取
				String depName = dept.getName();
				String curName = hasCon.getProject().getName();
				depName = curName + "$$" + depName;
				Map depMap;
				if (total.get(depName) == null) {
					depMap = new HashMap();
				} else {
					depMap = (Map) total.get(depName);
				}

				IRow row = tblContract.addRow();
				row.setUserObject(hasCon);
				row.getCell("project").setValue(hasCon.getProject());
				row.getCell("department").setValue(dept);
				row.getCell("id").setValue(hasCon.getId());
				row.getCell("isBack").setValue(new Boolean(hasCon.isIsBack()));
				if (hasCon.isIsBack()) {
					row.getStyleAttributes().setBackground(Color.RED);
				} else {
					row.getStyleAttributes().setBackground(null);
				}
				row.getCell("depPlanID").setValue(hasCon.getDepPlan());
				row.getCell("conNumber").setValue(hasCon.getContract());
				row.getCell("conName").setValue(hasCon.getContractName());
				row.getCell("conPrice").setValue(hasCon.getContractPrice());
				row.getCell("lastMonthPayable").setValue(
						hasCon.getLastMonthPayable());
				row.getCell("lastMonthPay").setValue(hasCon.getLastMonthPay());
				row.getCell("lastMonthNopay").setValue(
						hasCon.getLastMonthNopay());
				for (int j = 0; j < hasCon.getEntrys().size(); j++) {
					FDCProDepConPayContractEntryInfo conEntry = hasCon
							.getEntrys().get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(conEntry.getMonth());
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					String keyHead = "MONTH" + year + "" + month;
					BigDecimal curPlan = conEntry.getPlanPay();
					curPlan = curPlan == null ? FDCHelper.ZERO : curPlan;
					BigDecimal curOfficial = conEntry.getOfficialPay();
					curOfficial = curOfficial == null ? FDCHelper.ZERO
							: curOfficial;
					ICell cell = row.getCell(keyHead + "id");
					if (cell == null) {
						// 说明付款计划周期被改小了
						continue;
					}
					row.getCell(keyHead + "id").setValue(conEntry.getId());
					row.getCell(keyHead + "plan").setValue(curPlan);
					row.getCell(keyHead + "define").setValue(
							conEntry.getMoneyDefine());
					row.getCell(keyHead + "explain").setValue(
							conEntry.getExplain());
					row.getCell(keyHead + "official").setValue(curOfficial);

					String uoValue = getUserObject4OfficialCell(hasCon.getDepPlan().getId().toString(), year, month);
					row.getCell(keyHead + "official").setUserObject(uoValue);
					// 将计划及批复金额累加到汇总Map
					if (depMap.get(keyHead + "plan") != null) {
						BigDecimal plan = (BigDecimal) depMap.get(keyHead
								+ "plan");
						// plan = plan == null ? FDCHelper.ZERO : plan;
						depMap.put(keyHead + "plan", plan.add(curPlan));
					} else {
						depMap.put(keyHead + "plan", curPlan);
					}
					if (depMap.get(keyHead + "official") != null) {
						BigDecimal official = (BigDecimal) depMap.get(keyHead
								+ "official");
						// plan = plan == null ? FDCHelper.ZERO : plan;
						depMap.put(keyHead + "official", official
								.add(curOfficial));
					} else {
						depMap.put(keyHead + "official", curOfficial);
					}
					total.put(depName, depMap);
				}
			}
		}
		// 加载待签订合同计划
		if (editData.getUnsettledCon() != null) {
			for (int i = 0; i < editData.getUnsettledCon().size(); i++) {
				FDCProDepConPayPlanUnsettledInfo unCon = editData
						.getUnsettledCon().get(i);
				AdminOrgUnitInfo dept = unCon.getDepartment();

				// 用于计算部门统计的Map，从total中获取
				String depName = dept.getName();
				String curName = unCon.getProject().getName();
				depName = curName + "$$" + depName;
				Map depMap;
				if (total.get(depName) == null) {
					depMap = new HashMap();
				} else {
					depMap = (Map) total.get(depName);
				}

				IRow row = tblUnSetteled.addRow();
				row.setUserObject(unCon);
				row.getCell("project").setValue(unCon.getProject());
				row.getCell("department").setValue(dept);
				row.getCell("id").setValue(unCon.getId());
				row.getCell("isBack").setValue(new Boolean(unCon.isIsBack()));
				if (unCon.isIsBack()) {
					row.getStyleAttributes().setBackground(Color.RED);
				}
				row.getCell("depPlanID").setValue(unCon.getDepPlan());
				row.getCell("unConNum").setValue(unCon.getUnConNumber());
				row.getCell("unConName").setValue(unCon.getUnConName());
				row.getCell("unConPrice").setValue(unCon.getPlanAmount());
				for (int j = 0; j < unCon.getEntrys().size(); j++) {
					FDCProDepConPayPlanUnsetEntryInfo unEntry = unCon
							.getEntrys().get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(unEntry.getMonth());
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					String keyHead = "MONTH" + year + "" + month;

					BigDecimal curPlan = unEntry.getPlanPay();
					curPlan = curPlan == null ? FDCHelper.ZERO : curPlan;
					BigDecimal curOfficial = unEntry.getOfficialPay();
					curOfficial = curOfficial == null ? FDCHelper.ZERO
							: curOfficial;

					ICell cell = row.getCell(keyHead + "id");
					if (cell == null) {
						// 说明付款计划周期被改小了
						continue;
					}
					row.getCell(keyHead + "id").setValue(unEntry.getId());
					row.getCell(keyHead + "plan").setValue(curPlan);
					row.getCell(keyHead + "define").setValue(
							unEntry.getMoneyDefine());
					row.getCell(keyHead + "explain").setValue(
							unEntry.getExplain());
					row.getCell(keyHead + "official").setValue(curOfficial);

					String uoValue = getUserObject4OfficialCell(unCon.getDepPlan().getId().toString(), year, month);
					row.getCell(keyHead + "official").setUserObject(uoValue);
					// 将计划及批复金额累加到汇总Map
					if (depMap.get(keyHead + "plan") != null) {
						BigDecimal plan = (BigDecimal) depMap.get(keyHead
								+ "plan");
						// plan = plan == null ? FDCHelper.ZERO : plan;
						depMap.put(keyHead + "plan", plan.add(curPlan));
					} else {
						depMap.put(keyHead + "plan", curPlan);
					}
					if (depMap.get(keyHead + "official") != null) {
						BigDecimal official = (BigDecimal) depMap.get(keyHead
								+ "official");
						// plan = plan == null ? FDCHelper.ZERO : plan;
						depMap.put(keyHead + "official", official
								.add(curOfficial));
					} else {
						depMap.put(keyHead + "official", curOfficial);
					}
					total.put(depName, depMap);
				}
			}
		}
		// 加载无合同计划
		if (editData.getNoContract() != null) {
			for (int i = 0; i < editData.getNoContract().size(); i++) {
				FDCProDepConPayPlanNoContractInfo noCon = editData
						.getNoContract().get(i);
				AdminOrgUnitInfo dept = noCon.getDepartment();

				// 用于计算部门统计的Map，从total中获取
				String depName = dept.getName();
				String curName = noCon.getProject().getName();
				depName = curName + "$$" + depName;
				Map depMap;
				if (total.get(depName) == null) {
					depMap = new HashMap();
				} else {
					depMap = (Map) total.get(depName);
				}

				IRow row = tblNoContract.addRow();
				row.setUserObject(noCon);
				row.getCell("project").setValue(noCon.getProject());
				row.getCell("department").setValue(dept);
				row.getCell("id").setValue(noCon.getId());
				row.getCell("isBack").setValue(new Boolean(noCon.isIsBack()));
				if (noCon.isIsBack()) {
					row.getStyleAttributes().setBackground(Color.RED);
				}
				row.getCell("depPlanID").setValue(noCon.getDepPlan());
				row.getCell("payMatters").setValue(noCon.getPayMatters());
				row.getCell("payMattersName").setValue(
						noCon.getPayMattersName());
				for (int j = 0; j < noCon.getEntrys().size(); j++) {
					FDCProDepConPayPlanNoContractEntryInfo noEntry = noCon
							.getEntrys().get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(noEntry.getMonth());
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					String keyHead = "MONTH" + year + "" + month;

					BigDecimal curPlan = noEntry.getPlanPay();
					curPlan = curPlan == null ? FDCHelper.ZERO : curPlan;
					BigDecimal curOfficial = noEntry.getOfficialPay();
					curOfficial = curOfficial == null ? FDCHelper.ZERO
							: curOfficial;

					ICell cell = row.getCell(keyHead + "id");
					if (cell == null) {
						// 说明付款计划周期被改小了
						continue;
					}
					row.getCell(keyHead + "id").setValue(noEntry.getId());
					row.getCell(keyHead + "plan").setValue(curPlan);
					row.getCell(keyHead + "define").setValue(
							noEntry.getMoneyDefine());
					row.getCell(keyHead + "explain").setValue(
							noEntry.getExplain());
					row.getCell(keyHead + "official").setValue(curOfficial);

					String uoValue = getUserObject4OfficialCell(noCon.getDepPlan().getId().toString(), year, month);
					row.getCell(keyHead + "official").setUserObject(uoValue);
					
					// 将计划及批复金额累加到汇总Map
					if (depMap.get(keyHead + "plan") != null) {
						BigDecimal plan = (BigDecimal) depMap.get(keyHead
								+ "plan");
						// plan = plan == null ? FDCHelper.ZERO : plan;
						depMap.put(keyHead + "plan", plan.add(curPlan));
					} else {
						depMap.put(keyHead + "plan", curPlan);
					}
					if (depMap.get(keyHead + "official") != null) {
						BigDecimal official = (BigDecimal) depMap.get(keyHead
								+ "official");
						// plan = plan == null ? FDCHelper.ZERO : plan;
						depMap.put(keyHead + "official", official
								.add(curOfficial));
					} else {
						depMap.put(keyHead + "official", curOfficial);
					}
					total.put(depName, depMap);
				}
			}
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
		// 保存合同计划分录
		for (int i = 0; i < tblContract.getRowCount(); i++) {
			IRow row = tblContract.getRow(i);
			CurProjectInfo curInfo = null;
			if ("总计".equals(row.getCell("project").getValue())) {
				continue;
			} else {
				curInfo = (CurProjectInfo) row.getCell("project").getValue();
			}
			FDCProDepConPayPlanContractInfo conPlan = new FDCProDepConPayPlanContractInfo();
			Object obj = row.getCell("department").getValue();
			AdminOrgUnitInfo dept = null;
			if ("合计".equals(obj)) {
				continue;
			} else {
				dept = (AdminOrgUnitInfo) obj;
			}
			BOSUuid id = (BOSUuid) row.getCell("id").getValue();
			Boolean isBack = (Boolean) row.getCell("isBack").getValue();
			FDCDepConPayPlanContractInfo depPlan = (FDCDepConPayPlanContractInfo) row
					.getCell("depPlanID").getValue();
			obj = row.getCell("conNumber").getValue();
			ContractBillInfo contract = null;
			if ("小计".equals(obj)) {
				continue;
			} else {
				contract = (ContractBillInfo) obj;
			}
			String conName = (String) row.getCell("conName").getValue();
			BigDecimal conPrice = (BigDecimal) row.getCell("conPrice")
					.getValue();
			BigDecimal lastMonthPayable = (BigDecimal) row.getCell(
					"lastMonthPayable").getValue();
			BigDecimal lastMonthPay = (BigDecimal) row.getCell("lastMonthPay")
					.getValue();
			BigDecimal lastMonthNopay = (BigDecimal) row.getCell(
					"lastMonthNopay").getValue();

			conPlan.setId(id);
			conPlan.setDepartment(dept);
			conPlan.setIsBack(isBack.booleanValue());
			conPlan.setDepPlan(depPlan);
			conPlan.setContract(contract);
			conPlan.setContractName(conName);
			conPlan.setContractPrice(conPrice);
			conPlan.setLastMonthPayable(lastMonthPayable);
			conPlan.setLastMonthPay(lastMonthPay);
			conPlan.setLastMonthNopay(lastMonthNopay);
			conPlan.setProject(curInfo);

			for (int j = START_CON; j < tblContract.getColumnCount(); j += 5) {
				FDCProDepConPayContractEntryInfo planEntry = new FDCProDepConPayContractEntryInfo();
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
		// 保存待签订合同计划分录
		for (int i = 0; i < tblUnSetteled.getRowCount(); i++) {
			IRow row = tblUnSetteled.getRow(i);
			CurProjectInfo curInfo = null;
			if ("总计".equals(row.getCell("project").getValue())) {
				continue;
			} else {
				curInfo = (CurProjectInfo) row.getCell("project").getValue();
			}
			FDCProDepConPayPlanUnsettledInfo unCPlan = new FDCProDepConPayPlanUnsettledInfo();
			Object obj = row.getCell("department").getValue();
			AdminOrgUnitInfo dept = null;
			if ("合计".equals(obj)) {
				continue;
			} else {
				dept = (AdminOrgUnitInfo) obj;
			}
			BOSUuid id = (BOSUuid) row.getCell("id").getValue();
			Boolean isBack = (Boolean) row.getCell("isBack").getValue();
			FDCDepConPayPlanUnsettledConInfo depPlan = (FDCDepConPayPlanUnsettledConInfo) row
					.getCell("depPlanID").getValue();
			obj = row.getCell("unConNum").getValue();
			String unConNumber = null;
			if ("小计".equals(obj)) {
				continue;
			} else {
				unConNumber = (String) obj;
			}
			String unConName = (String) row.getCell("unConName").getValue();
			BigDecimal unConPrice = (BigDecimal) row.getCell("unConPrice")
					.getValue();
			unCPlan.setId(id);
			unCPlan.setDepartment(dept);
			unCPlan.setIsBack(isBack.booleanValue());
			unCPlan.setDepPlan(depPlan);
			unCPlan.setUnConNumber(unConNumber);
			unCPlan.setUnConName(unConName);
			unCPlan.setPlanAmount(unConPrice);
			unCPlan.setProject(curInfo);

			for (int j = START_UNC; j < tblUnSetteled.getColumnCount(); j += 5) {
				FDCProDepConPayPlanUnsetEntryInfo planEntry = new FDCProDepConPayPlanUnsetEntryInfo();
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

				String key = tblUnSetteled.getColumn(j).getKey();
				String year = key.substring(5, 9);
				String month = key.substring(9, key.length() - 2);
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, new Integer(year).intValue());
				cal.set(Calendar.MONTH, new Integer(month).intValue() - 1);
				cal.set(Calendar.DAY_OF_MONTH, 15);
				planEntry.setMonth(cal.getTime());

				unCPlan.getEntrys().add(planEntry);
			}
			editData.getUnsettledCon().add(unCPlan);
		}
		// 保存无合同计划分录
		for (int i = 0; i < tblNoContract.getRowCount(); i++) {
			IRow row = tblNoContract.getRow(i);
			CurProjectInfo curInfo = null;
			if ("总计".equals(row.getCell("project").getValue())) {
				continue;
			} else {
				curInfo = (CurProjectInfo) row.getCell("project").getValue();
			}
			FDCProDepConPayPlanNoContractInfo noCPlan = new FDCProDepConPayPlanNoContractInfo();
			Object obj = row.getCell("department").getValue();
			AdminOrgUnitInfo dept = null;
			if ("合计".equals(obj)) {
				continue;
			} else {
				dept = (AdminOrgUnitInfo) obj;
			}
			BOSUuid id = (BOSUuid) row.getCell("id").getValue();
			Boolean isBack = (Boolean) row.getCell("isBack").getValue();
			FDCDepConPayPlanNoContractInfo depPlan = (FDCDepConPayPlanNoContractInfo) row
					.getCell("depPlanID").getValue();
			obj = row.getCell("payMatters").getValue();
			String payMatters = null;
			if ("小计".equals(obj)) {
				continue;
			} else {
				payMatters = (String) obj;
			}
			String payMattersName = (String) row.getCell("payMattersName")
					.getValue();
			noCPlan.setId(id);
			noCPlan.setDepartment(dept);
			noCPlan.setIsBack(isBack.booleanValue());
			noCPlan.setDepPlan(depPlan);
			noCPlan.setPayMatters(payMatters);
			noCPlan.setPayMattersName(payMattersName);
			noCPlan.setProject(curInfo);

			for (int j = START_NOC; j < tblNoContract.getColumnCount(); j += 5) {
				FDCProDepConPayPlanNoContractEntryInfo planEntry = new FDCProDepConPayPlanNoContractEntryInfo();
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

				noCPlan.getEntrys().add(planEntry);
			}
			editData.getNoContract().add(noCPlan);
		}
	}

	boolean canEditOfficialCell = false;	//是否可编辑最终批复金额。
	
	/**
	 * 检查是否可编辑最终批复金额。
	 * 1、在工作流中。2、非制单人。3、审批节点使用Edit方式
	 */
	private void checkIfCanEditOfficialCell() {
		boolean isCreator = this.isCreator();
		boolean isInWorkflow = false; //是否在工作流
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if (isFromWorkflow != null && "true".equals(isFromWorkflow.toString())) {
			isInWorkflow = true;
		}
		boolean isEdit = false;
		if(this.getOprtState()==OprtState.EDIT) {
			isEdit = true;
		}
		if(!isCreator && isInWorkflow && isEdit) {
			canEditOfficialCell = true;
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		//by ken_liu..start
		if(canEditOfficialCell) {
			lockUI4Workflow();
			HashMap changedValueMap = new HashMap();
			changedValueMap.put("CON", newValueMap_CON);
			changedValueMap.put("UNC", newValueMap_UNC);
			changedValueMap.put("NOC", newValueMap_NOC);
			this.editData.put("changedValueMap", changedValueMap);
		}
		//by ken_liu..end
		initUI();
		//如果不是查看状态的情况就要把保存按钮亮显
		if (!OprtState.VIEW.equals(this.getOprtState())) {
			this.actionSave.setEnabled(true);
		}
		//当单据状态不是“保存”和“打回”状态的时候就把保存按钮灰显 add by Jian_cao  BT721353
		if (null != this.editData.getState()) {
			if (!FDCBillStateEnum.SAVED.equals(this.editData.getState()) && !FDCBillStateEnum.BACK.equals(this.editData.getState())) {
				this.actionSave.setEnabled(false);
			}
		}
		//上报隐藏
		actionPublish.setVisible(false);
	}

	/**
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#disablePrintFunc()
	 */
	protected void disablePrintFunc() {

	}
	
	protected void initUI() {
		btnPublish.setIcon(EASResource.getIcon("imgTbtn_timingrefer"));
		menuItemPublish.setIcon(EASResource.getIcon("imgTbtn_timingrefer"));
		btnRevise.setIcon(EASResource.getIcon("imgTbtn_emend"));
		menuItemRevise.setIcon(EASResource.getIcon("imgTbtn_emend"));
		btnBack.setIcon(EASResource.getIcon("imgTbtn_untreadreport"));
		menuItemBack.setIcon(EASResource.getIcon("imgTbtn_untreadreport"));
		menuItemViewContract.setIcon(EASResource
				.getIcon("imgTbtn_seeperformance"));
		btnViewContract.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		btnAttachment.setVisible(false);
		actionAudit.setVisible(false);
		actionUnAudit.setVisible(false);

		if (getUIContext().get("flag_state") != null) {
			flag_state = (String) getUIContext().get("flag_state");
			actionSave.setEnabled(false);
			actionAddNew.setEnabled(false);
			actionRemove.setEnabled(false);
			txtName.setEnabled(false);
			txtNumber.setEnabled(false);
			spMonth.setEnabled(false);
			spYear.setEnabled(false);
			description.setEnabled(false);
			actionRevise.setEnabled(false);
		}

		// 参数
		try {
			separteFromPayment = FDCUtils.getBooleanValue4FDCParamByKey(null,
					SysContext.getSysContext().getCurrentOrgUnit().getId()
							.toString(), "FDC317_SEPARATEFROMPAYMENT");
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}

		SpinnerNumberModel model = new SpinnerNumberModel();
		model.setMinimum(new Integer(1));
		model.setMaximum(new Integer(12));
		model.setStepSize(new Integer(1));
		spMonth.setModel(model);
		spMonth.setValue(new Integer(editData.getMonth()), false);

		year_old = this.spYear.getIntegerVlaue();
		month_old = this.spMonth.getIntegerVlaue();

		tblTotal.getStyleAttributes().setLocked(true);
//		tblContract.getStyleAttributes().setLocked(true);
//		tblUnSetteled.getStyleAttributes().setLocked(true);
//		tblNoContract.getStyleAttributes().setLocked(true);

		ObjectValueRender ovrNum = new ObjectValueRender();
		ovrNum.setFormat(new BizDataFormat("$number$"));
		tblContract.getColumn("conNumber").setRenderer(ovrNum);
		tblContract.getColumn("conNumber").setEditor(getCellEditor("contract"));

		ObjectValueRender ovrName = new ObjectValueRender();
		ovrName.setFormat(new BizDataFormat("$name$"));
		tblContract.getColumn("department").setRenderer(ovrName);
		tblContract.getColumn("department").setEditor(getCellEditor("admin"));
		tblUnSetteled.getColumn("department").setRenderer(ovrName);
		tblUnSetteled.getColumn("department").setEditor(getCellEditor("admin"));
		tblNoContract.getColumn("department").setRenderer(ovrName);
		tblNoContract.getColumn("department").setEditor(getCellEditor("admin"));

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
	}

	/**
	 * 
	 * 描述：当前登录用户是否是制单人
	 * @return true 是，false 不是
	 * @Author：owen_wen
	 * @CreateTime：2011-12-21
	 */
	private boolean isCreator() {
//		return false;
		String creatorID = editData.getCreator() == null ? "" : editData.getCreator().getId().toString();
		return creatorID.equals(SysContext.getSysContext().getCurrentUserInfo().getId().toString());
	}
	
	
	/**
	 * 为工作流审批时，锁定界面某些控件。
	 * 直接使用lockUIForViewStatus()需要放开table..小计合计总计又要重新锁。。直接lock控件算了、、
	 */
	protected void lockUI4Workflow() {
		txtNumber.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		txtNumber.setRequired(false);
		txtNumber.setEnabled(false);
		txtName.setEnabled(false);
		prmtCurProject.setEnabled(false);
		prmtPayPlanCycle.setEnabled(false);
		spYear.setEnabled(false);
		spMonth.setEnabled(false);
		description.setEnabled(false);
		this.actionSummary.setEnabled(false);
	}
	
	/**
	 * 小计合计总计的最终批复金额锁定
	 * @param sumRow
	 * @param startIndexOfDynamicColumn
	 * @param columnCount
	 */
	private void lockCell4SumRow(IRow sumRow, int startIndexOfDynamicColumn, int columnCount) {
		if(!canEditOfficialCell) {
			return;
		}
		for (int i = startIndexOfDynamicColumn + 4; i < columnCount; i += 5) {
			sumRow.getCell(i).getStyleAttributes().setLocked(true);
		}
	}
	
	/**
	 * 由于onShow里面会调用此方法，给第一个表添加排序，而此单据不需要排序，故重载
	 */
	protected void initListener() {
		// super.initListener();
	}

	protected void spYear_stateChanged(ChangeEvent e) throws Exception {
		if (isFirstOnload()) {
			return;
		}
		CurProjectInfo prj = (CurProjectInfo) prmtCurProject.getValue();
		Integer year = spYear.getIntegerVlaue();
		Integer month = spMonth.getIntegerVlaue();
		if (checkMonthHasPlan(year, month)) {
			FDCMsgBox.showWarning(this, "\"" + prj.getName() + "\" 项目下编制月份为："
					+ year + "年" + month + " 月的项目月度资金需求计划已存在");
			spYear.setValue(year_old, false);
			spMonth.setValue(month_old, false);
			abort();
		}
		// 改变月份清空分录，新增时不提示
		int result = MsgBox.OK;
		if (!STATUS_ADDNEW.equals(getOprtState())
				&& (tblContract.getRowCount() > 0
						|| tblUnSetteled.getRowCount() > 0 || tblNoContract
						.getRowCount() > 0)) {
			result = MsgBox.showConfirm2("改变编制月份将清空分录数据！");
		}
		if (result == MsgBox.OK) {
			clearTable();
			initTableColumn();
			year_old = year;
		} else {
			spYear.setValue(year_old, false);
		}
	}

	protected void spMonth_stateChanged(ChangeEvent e) throws Exception {
		if (isFirstOnload()) {
			return;
		}
		CurProjectInfo prj = (CurProjectInfo) prmtCurProject.getValue();
		Integer year = spYear.getIntegerVlaue();
		Integer month = spMonth.getIntegerVlaue();
		if (checkMonthHasPlan(year, month)) {
			FDCMsgBox.showWarning(this, "\"" + prj.getName() + "\" 项目下编制月份为："
					+ year + "年" + month + " 月的项目月度资金需求计划已存在");
			spYear.setValue(year_old, false);
			spMonth.setValue(month_old, false);
			abort();
		}
		// 改变月份清空分录，新增时不提示
		int result = MsgBox.OK;
		if (!STATUS_ADDNEW.equals(getOprtState())
				&& (tblContract.getRowCount() > 0
						|| tblUnSetteled.getRowCount() > 0 || tblNoContract
						.getRowCount() > 0)) {
			result = MsgBox.showConfirm2("改变编制月份将清空分录数据！");
		}
		if (result == MsgBox.OK) {
			clearTable();
			initTableColumn();
			month_old = month;
		} else {
			spMonth.setValue(month_old, false);
		}
	}

	/**
	 * 检查当前编制月份是否存在计划
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected boolean checkMonthHasPlan(Integer year, Integer month) {
		CurProjectInfo project = (CurProjectInfo) prmtCurProject.getValue();
		if (project == null
				|| (!getOprtState().equals(STATUS_ADDNEW) && !getOprtState()
						.equals(STATUS_EDIT))) {
			return false;
		}
		FilterInfo filter = new FilterInfo();
		if (!FDCHelper.isEmpty(editData.getId())) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", editData.getId().toString(),
							CompareType.NOTEQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("year", year));
		filter.getFilterItems().add(new FilterItemInfo("month", month));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("curProject.id", project.getId()
								.toString()));
		boolean isDep = false;
		try {
			isDep = getBizInterface().exists(filter);
		} catch (Exception e) {
			handUIException(e);
		}
		return isDep;
	}

	protected void pnlEntry_stateChanged(ChangeEvent e) throws Exception {
		int selectedIndex = pnlEntry.getSelectedIndex();
		if (selectedIndex == 0) {
			actionBack.setEnabled(false);
			actionViewContract.setEnabled(false);
		} else {
			FDCBillStateEnum state = editData.getState();
			if (FDCBillStateEnum.SAVED.equals(state)
					|| FDCBillStateEnum.SUBMITTED.equals(state)
					|| FDCBillStateEnum.BACK.equals(state)) {
				actionBack.setEnabled(true);
			}
			if (selectedIndex == 1) {
				actionViewContract.setEnabled(true);
			} else {
				actionViewContract.setEnabled(false);
			}
		}
	}

	/**
	 * 计算3个页签的小计以及合计值
	 */
	protected void CalcTotal() {
		int cycle = editData.getPayPlanCycle().getCycle().getValue();
		// 需要合计的列
		COLS_CON = new int[2 * cycle + 4];
		COLS_UNC = new int[cycle * 2 + 1];
		COLS_NOC = new int[cycle * 2];

		// 合计合同分录
		//		COLS_CON[0] = 5;
		//		COLS_CON[1] = 6;
		//		COLS_CON[2] = 7;
		//		COLS_CON[3] = 8;
		COLS_CON[0] = 7;
		COLS_CON[1] = 8;
		COLS_CON[2] = 9;
		COLS_CON[3] = 10;
		for (int i = 0; i < cycle; i++) {
			COLS_CON[4 + 2 * i] = START_CON + (i * 5) + 1;
			COLS_CON[5 + 2 * i] = START_CON + (i * 5) + 4;
		}
		// 先添加合计、再添加小计，避免合计重复计算
		IRow row = FDCTableHelper.calcSumForTable(0,
				tblContract.getRowCount() - 1, COLS_CON, tblContract);
		if (row != null) {
			row.getCell("project").setValue("总计");
			lockCell4SumRow(row, START_CON, tblContract.getColumnCount());
		}
		addSubTotal(tblContract, COLS_CON);

		// 合计待签订合同分录
		COLS_UNC[0] = 7;
		for (int i = 0; i < cycle; i++) {
			COLS_UNC[1 + 2 * i] = START_UNC + (i * 5) + 1;
			COLS_UNC[2 + 2 * i] = START_UNC + (i * 5) + 4;
		}
		// 先添加合计、再添加小计，避免合计重复计算
		row = FDCTableHelper.calcSumForTable(0,
				tblUnSetteled.getRowCount() - 1, COLS_UNC, tblUnSetteled);
		if (row != null) {
			row.getCell("project").setValue("总计");
			lockCell4SumRow(row, START_UNC, tblUnSetteled.getColumnCount());
		}
		addSubTotal(tblUnSetteled, COLS_UNC);

		// 合计无合同分录
		for (int i = 0; i < cycle; i++) {
			COLS_NOC[2 * i] = START_NOC + (i * 5) + 1;
			COLS_NOC[2 * i + 1] = START_NOC + (i * 5) + 4;
		}
		// 先添加合计、再添加小计，避免合计重复计算
		row = FDCTableHelper.calcSumForTable(0,
				tblNoContract.getRowCount() - 1, COLS_NOC, tblNoContract);
		if (row != null) {
			row.getCell("project").setValue("总计");
			lockCell4SumRow(row, START_NOC, tblNoContract.getColumnCount());
		}
		addSubTotal(tblNoContract, COLS_NOC);
	}

	/**
	 * 循环行，将编制部门相同的做小计<br>
	 * 顺便对相同部门列进行融合
	 * 
	 * @param table
	 * @param cols
	 */
	protected void addSubTotal(KDTable table, int[] cols) {
		if (table.getRowCount() > 0) {
			KDTMergeManager mg = table.getMergeManager();
			int begin = 0;
			int end = 0;
			String oldDpt = "";
			String oldCur = "";
			for (int i = table.getRowCount() - 2; i >= 0; i--) {
				IRow row = table.getRow(i);
				AdminOrgUnitInfo admin = (AdminOrgUnitInfo) row.getCell(
						"department").getValue();
				
				CurProjectInfo curInfo = (CurProjectInfo) row.getCell("project").getValue();
				String newCur = curInfo.getId().toString();
				String curDpt = admin.getId().toString();
				
				if (i == table.getRowCount() - 2) {
					oldCur = newCur;
					begin = i;
					end = i;
					continue;
				} else {
					if (newCur.equals(oldCur)) {
						begin = i;
						continue;
					} else {
						row = FDCTableHelper.calcSumForTable(begin, end, cols, table);
						recordSumRowMap(table, row, begin, false);
						
						mg.mergeBlock(begin, 0, end + 1, 0);
						String name = table.getName();
						if ("tblContract".equals(name)) {
							row.getCell("department").setValue("合计");
							lockCell4SumRow(row, START_CON, tblContract.getColumnCount());
						} else if ("tblUnSetteled".equals(name)) {
							row.getCell("department").setValue("合计");
							lockCell4SumRow(row, START_UNC, tblUnSetteled.getColumnCount());
						} else if ("tblNoContract".equals(name)) {
							row.getCell("department").setValue("合计");
							lockCell4SumRow(row, START_NOC, tblNoContract.getColumnCount());
						}
						addSubRow(table, cols, begin, end);
						oldCur = newCur;
						begin = i;
						end = i;
					}
				}
				
			}
			IRow row = FDCTableHelper.calcSumForTable(begin, end, cols, table);
			recordSumRowMap(table, row, begin, false);
			mg.mergeBlock(begin, 0, end + 1, 0);
			String name = table.getName();
			if ("tblContract".equals(name)) {
				row.getCell("department").setValue("合计");
				lockCell4SumRow(row, START_CON, tblContract.getColumnCount());
			} else if ("tblUnSetteled".equals(name)) {
				row.getCell("department").setValue("合计");
				lockCell4SumRow(row, START_UNC, tblUnSetteled.getColumnCount());
			} else if ("tblNoContract".equals(name)) {
				row.getCell("department").setValue("合计");
				lockCell4SumRow(row, START_NOC, tblNoContract.getColumnCount());
			}
			addSubRow(table, cols, begin, end);
		}
	}
	
	private void addSubRow(KDTable table, int[] cols, int beginRow, int endRow) {
		KDTMergeManager mg = table.getMergeManager();
		int begin = beginRow;
		int end = endRow;
		String oldDpt = "";
		for (int i = endRow; i >= beginRow; i--) {
			IRow row = table.getRow(i);
			AdminOrgUnitInfo admin = (AdminOrgUnitInfo) row.getCell("department").getValue();
			String curDpt = admin.getId().toString();
			if (i == endRow) {
				oldDpt = curDpt;
				begin = i;
				end = i;
				continue;
			} else {
				if (curDpt.equals(oldDpt)) {
					begin = i;
					continue;
				} else {
					row = FDCTableHelper.calcSumForTable(begin, end, cols, table);
										mg.mergeBlock(begin, 1, end + 1, 1);
					recordSumRowMap(table, row, begin, true);					
					String name = table.getName();
					if ("tblContract".equals(name)) {
						row.getCell("conNumber").setValue("小计");
						lockCell4SumRow(row, START_CON, tblContract.getColumnCount());
					} else if ("tblUnSetteled".equals(name)) {
						row.getCell("unConNum").setValue("小计");
						lockCell4SumRow(row, START_UNC, tblUnSetteled.getColumnCount());
					} else if ("tblNoContract".equals(name)) {
						row.getCell("payMatters").setValue("小计");
						lockCell4SumRow(row, START_NOC, tblNoContract.getColumnCount());
					}
					oldDpt = curDpt;
					begin = i;
					end = i;
				}
			}
		}
		IRow row = FDCTableHelper.calcSumForTable(begin, end, cols, table);
				mg.mergeBlock(begin, 1, end + 1, 1);
		recordSumRowMap(table, row, begin, true);
		String name = table.getName();
		if ("tblContract".equals(name)) {
			row.getCell("conNumber").setValue("小计");
			lockCell4SumRow(row, START_CON, tblContract.getColumnCount());
		} else if ("tblUnSetteled".equals(name)) {
			row.getCell("unConNum").setValue("小计");
			lockCell4SumRow(row, START_UNC, tblUnSetteled.getColumnCount());
		} else if ("tblNoContract".equals(name)) {
			row.getCell("payMatters").setValue("小计");
			lockCell4SumRow(row, START_NOC, tblNoContract.getColumnCount());
		}
	}
	
	/**
	 * 根据total<HashMap>的值，填写汇总表<br>
	 * total已在loadEntryData()里计算
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
				if (depName.indexOf("$$") > -1) {
					String curName = depName.substring(0, depName.indexOf("$$"));
					row.getCell("project").setValue(curName);
					depName = depName.substring(depName.indexOf("$$") + 2);
				}
				row.getCell("department").setValue(depName);
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
		IRow row = FDCTableHelper.calcSumForTable(0,
				tblTotal.getRowCount() - 1, COLS_TOL, tblTotal);
		if (row != null) {
			row.getCell("department").setValue("合计");
		}
		addSubTotal2(tblTotal, COLS_TOL);
	}

	/**
	 * 循环行，将编制部门相同的做小计<br>
	 * 顺便对相同部门列进行融合
	 * 
	 * @param table
	 * @param cols
	 */
	protected void addSubTotal2(KDTable table, int[] cols) {
		if (table.getRowCount() > 0) {
			KDTMergeManager mg = table.getMergeManager();
			int begin = 0;
			int end = 0;
			String oldCur = "";
			for (int i = table.getRowCount() - 2; i >= 0; i--) {
				IRow row = table.getRow(i);

				String newCur = (String) row.getCell("project").getValue();

				if (i == table.getRowCount() - 2) {
					oldCur = newCur;
					begin = i;
					end = i;
					continue;
				} else {
					if (newCur.equals(oldCur)) {
						begin = i;
						continue;
					} else {
						row = FDCTableHelper.calcSumForTable(begin, end, cols, table);
						recordSumRowMap(table, row, begin, false);
						mg.mergeBlock(begin, 0, end + 1, 0);
						row.getCell("department").setValue("合计");
						oldCur = newCur;
						begin = i;
						end = i;
					}
				}

			}
			IRow row = FDCTableHelper.calcSumForTable(begin, end, cols, table);
			recordSumRowMap(table, row, begin, false);
			mg.mergeBlock(begin, 0, end + 1, 0);
			String name = table.getName();
			row.getCell("department").setValue("合计");
		}
	}

	/**
	 * 记录表格的小计或合计行。总计行是最后一行，不必记录。
	 * @param table		tblTotal/tblContract/tblUnSetteled/tblNoContract
	 * @param sumRow	小计、合计行
	 * @param startRowIndexOfGroup	部门一小计，工程一合计。对应部门、工程开始行号
	 * @param subTotal	是否小计行。false表示合计行。
	 */
	private void recordSumRowMap(KDTable table, IRow sumRow, int startRowIndexOfGroup, boolean subTotal) {
		if(!canEditOfficialCell) {//若不能修改最终批复金额，则无需记录了。
			return;
		}
		
		Map updatedMap = null;
		if(table==tblTotal) {
			updatedMap = map_Total_Total;
		}else if(table==tblContract) {
			updatedMap = subTotal?map_SubTotal_CON:map_Total_CON;
		}else if(table==tblUnSetteled) {
			updatedMap = subTotal?map_SubTotal_UNC:map_Total_UNC;
		}else if(table==tblNoContract) {
			updatedMap = subTotal?map_SubTotal_NOC:map_Total_NOC;
		}
		
		IRow row= table.getRow(startRowIndexOfGroup);
		//汇总页签，project单元格记录的是名字。。。
		if(table==tblTotal) {//汇总页签没有小计
			String prjName = (String) row.getCell("project").getValue();
			updatedMap.put(prjName, sumRow);
		}else {
			CurProjectInfo prj = (CurProjectInfo)row.getCell("project").getValue();
			if(!subTotal) {
				updatedMap.put(prj.getLongNumber(), sumRow);
			}else {
				AdminOrgUnitInfo dep = (AdminOrgUnitInfo) row.getCell("department").getValue();
				String key = prj.getLongNumber()+ "$$" + dep.getNumber();
				updatedMap.put(key, sumRow);
			}
		}
		
	}
	
	/**
	 * 最终批复金额变化后，重新计算小计、合计、总计。并记录需同步到合同滚动计划中的最终批复金额数据。
	 * @param table
	 * @param e
	 * @param subTotalRowMap
	 * @param totalRowMap
	 * @param newValueMap
	 */
	private void reCalculateSumRow(KDTable table, KDTEditEvent e, Map subTotalRowMap, Map totalRowMap, Map newValueMap) {
		if(e.getValue()==null && e.getOldValue()==null) {
			return;
		}
		
		BigDecimal diff = FDCNumberHelper.subtract(e.getValue(), e.getOldValue());
		if(diff.compareTo(FDCNumberHelper.ZERO) == 0) {
			return;
		}
		
		int edittedColIndex = e.getColIndex();
		IRow edittedRow = table.getRow(e.getRowIndex());
		
		//记录需同步到合同滚动计划中的数据到对应map
		//map中格式：页签分录对应的合同滚动计划页签分录id + $$ + 分录记录的年月-->最终批复金额数值。 like: W2AAAAAD2fJP5Z5a$$2013-07-15 --> 20000.00
		String uoValue = (String) edittedRow.getCell(edittedColIndex).getUserObject();
		newValueMap.put(uoValue, e.getValue());
		
		
		CurProjectInfo prj = (CurProjectInfo)edittedRow.getCell("project").getValue();
		AdminOrgUnitInfo dep = (AdminOrgUnitInfo) edittedRow.getCell("department").getValue();
		
		IRow subTotalRow = (IRow) subTotalRowMap.get(prj.getLongNumber() + "$$" + dep.getNumber() );
		IRow totalRow = (IRow) totalRowMap.get(prj.getLongNumber());
		
				//更新编辑页签
		//小计更新。。
		BigDecimal newValue = FDCNumberHelper.add(diff, subTotalRow.getCell(edittedColIndex).getValue());
		subTotalRow.getCell(edittedColIndex).setValue(newValue);
		
		//合计更新。。
		newValue = FDCNumberHelper.add(diff, totalRow.getCell(edittedColIndex).getValue());
		totalRow.getCell(edittedColIndex).setValue(newValue);
		
		//总计更新。。
		IRow lastRow = table.getRow(table.getRowCount()-1);
		newValue = FDCNumberHelper.add(diff, lastRow.getCell(edittedColIndex).getValue());
		lastRow.getCell(edittedColIndex).setValue(newValue);
		
		
				//更新汇总表页签：
		//更新合计
		totalRow = (IRow) map_Total_Total.get(prj.getName());
		String colKey = table.getColumnKey(edittedColIndex);
		newValue = FDCNumberHelper.add(diff, totalRow.getCell(colKey).getValue());
		totalRow.getCell(colKey).setValue(newValue);
		
		//更新部门汇总数据
		IRow prjRow = tblTotal.getRow(totalRow.getRowIndex()-1);
		newValue = FDCNumberHelper.add(diff, prjRow.getCell(colKey).getValue());
		prjRow.getCell(colKey).setValue(newValue);
		
		//更新总计
		lastRow = tblTotal.getRow(tblTotal.getRowCount()-1);
		newValue = FDCNumberHelper.add(diff, lastRow.getCell(colKey).getValue());
		lastRow.getCell(colKey).setValue(newValue);
	}
	
	
	protected void tblContract_tableClicked(KDTMouseEvent e) throws Exception {
		int colIndex = e.getColIndex();
		if (colIndex > START_CON && (colIndex - START_CON) % 5 == 3) {
			KDDetailedAreaUtil.showDialog(this, tblContract);
		}
	}

	protected void tblContract_editStopped(KDTEditEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.tblContract_editStopped(e);
		reCalculateSumRow(tblContract, e, map_SubTotal_CON, map_Total_CON, newValueMap_CON);
	}

	protected void tblUnSetteled_tableClicked(KDTMouseEvent e) throws Exception {
		int colIndex = e.getColIndex();
		if (colIndex > START_UNC && (colIndex - START_UNC) % 5 == 3) {
			KDDetailedAreaUtil.showDialog(this, tblUnSetteled);
		}
	}

	protected void tblUnSetteled_editStopped(KDTEditEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.tblUnSetteled_editStopped(e);
		reCalculateSumRow(tblUnSetteled, e, map_SubTotal_UNC, map_Total_UNC, newValueMap_UNC);
	}

	protected void tblNoContract_tableClicked(KDTMouseEvent e) throws Exception {
		int colIndex = e.getColIndex();
		if (colIndex > START_NOC && (colIndex - START_NOC) % 5 == 3) {
			KDDetailedAreaUtil.showDialog(this, tblNoContract);
		}
	}

	protected void tblNoContract_editStopped(KDTEditEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.tblNoContract_editStopped(e);
		reCalculateSumRow(tblNoContract, e, map_SubTotal_NOC, map_Total_NOC, newValueMap_NOC);
	}

	protected IObjectValue createNewData() {
		FDCProDepConPayPlanInfo objectValue = new FDCProDepConPayPlanInfo();

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
				MsgBox.showWarning(this, "必须启用一套付款计划周期!");
				abort();
			}
		} catch (BOSException e) {
			handUIException(e);
		}

		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setCreateTime(new Timestamp(serverDate.getTime()));

		Object obj = getUIContext().get("selectedProj");
		if (obj == null) {
			obj = curProject;
		}
		curProject = (CurProjectInfo) obj;
		objectValue.setCurProject(curProject);

		objectValue.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit()
				.castToFullOrgUnitInfo());

		Calendar cal = Calendar.getInstance();
		cal.setTime(serverDate);
		while (checkMonthHasPlan(curProject,
				new Integer(cal.get(Calendar.YEAR)), new Integer(cal
						.get(Calendar.MONTH) + 1))) {
			cal.add(Calendar.MONTH, 1);
		}
		objectValue.setYear(cal.get(Calendar.YEAR));
		objectValue.setMonth(cal.get(Calendar.MONTH) + 1);

		objectValue.setVersion(VERSION);

		return objectValue;
	}

	protected boolean checkMonthHasPlan(CurProjectInfo curProject,
			Integer year, Integer month) {
		if (curProject == null
				|| (!getOprtState().equals(STATUS_ADDNEW) && !getOprtState()
						.equals(STATUS_EDIT))) {
			return false;
		}
		FilterInfo filter = new FilterInfo();
		if (!STATUS_ADDNEW.equals(getOprtState())) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", editData.getId().toString(),
							CompareType.NOTEQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("year", year));
		filter.getFilterItems().add(new FilterItemInfo("month", month));
		filter.getFilterItems().add(
				new FilterItemInfo("curProject.id", curProject.getId()
						.toString()));

		boolean isDep = false;
		try {
			isDep = FDCProDepConPayPlanFactory.getRemoteInstance().exists(
					filter);
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
		return isDep;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if (txtNumber.isEnabled() && FDCHelper.isEmpty(txtNumber.getText())) {
			MsgBox.showWarning("编码不能为空！ ");
			abort();
		}
		if (FDCHelper.isEmpty(txtName.getText())) {
			MsgBox.showWarning("单据名称不能为空！ ");
			abort();
		}
		super.verifyInput(e);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if ((STATUS_ADDNEW.equals(getOprtState()) || STATUS_EDIT
				.equals(getOprtState()))
				&& !isSaved) {
			// 修改状态但是没有提交，先提示提交
			String message = EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Confirm_Save_Check");
			int result = MsgBox.showConfirm3(this, message);
			// if (MsgBox.isYes(MsgBox.showConfirm2(this, EASResource
			// .getString(FrameWorkClientUtils.strResource
			// + "Confirm_Save_Check"))))
			if (result == KDOptionPane.YES_OPTION) {
				if (!isModifySave()) {
					// 2007-1-25 判断使用保存或提交处理。
					ActionEvent event = new ActionEvent(btnSubmit,
							ActionEvent.ACTION_PERFORMED, btnSubmit
									.getActionCommand());
					actionSubmit.actionPerformed(event);
					if (actionSubmit.isInvokeFailed()) {
						SysUtil.abort();
					}
				} else {
					actionSave.setDaemonRun(false);
					ActionEvent event = new ActionEvent(btnSave,
							ActionEvent.ACTION_PERFORMED, btnSave
									.getActionCommand());
					actionSave.actionPerformed(event);
					if (actionSave.isInvokeFailed()) {
						SysUtil.abort();
					}
				}

				// actionSubmit_actionPerformed(null);
			} else if (result == KDOptionPane.CANCEL_OPTION) {
				SysUtil.abort();
			}
		}
		getUIContext().put("selectedProj", editData.getCurProject());
		super.actionAddNew_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		isSaved = false;
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		// 重置汇总状态
		isReSum = false;
		isSaved = true;
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeSubmit();
		
		if ("revist".equals(flag_state)) {
			this.editData.setState(FDCBillStateEnum.REVISING);
			this.editData.setAuditor(SysContext.getSysContext()
					.getCurrentUserInfo());
			FDCProDepConPayPlanFactory.getRemoteInstance().save(this.editData);

			storeFields();
			this.editData.setState(FDCBillStateEnum.SUBMITTED);
			java.text.DecimalFormat myformat = new java.text.DecimalFormat(
					"#0.0");
			double version = Double.parseDouble(this.editData.getVersion()) + 0.1;
			this.editData.setVersion(myformat.format(version));

			clearID();
			loadFields();
			promAuditor.setValue(null);
			picAuditorTime.setValue(null);
			flag_state = null;
		}
		super.actionSubmit_actionPerformed(e);
		// 重置汇总状态
		isReSum = false;
		isSaved = true;
	}

	protected void clearID() {
		editData.setId(null);
		FDCProDepConPayPlanContractCollection hasCon = editData
				.getHasContract();
		for (int i = 0; i < hasCon.size(); i++) {
			hasCon.get(i).setId(null);
			hasCon.get(i).setIsBack(false);
			FDCProDepConPayContractEntryCollection entrys = hasCon.get(i)
					.getEntrys();
			for (int j = 0; j < entrys.size(); j++) {
				entrys.get(j).setId(null);
			}
		}
		FDCProDepConPayPlanUnsettledCollection unCon = editData
				.getUnsettledCon();
		for (int i = 0; i < unCon.size(); i++) {
			unCon.get(i).setId(null);
			unCon.get(i).setIsBack(false);
			FDCProDepConPayPlanUnsetEntryCollection entrys = unCon.get(i)
					.getEntrys();
			for (int j = 0; j < entrys.size(); j++) {
				entrys.get(j).setId(null);
			}
		}
		FDCProDepConPayPlanNoContractCollection noCon = editData
				.getNoContract();
		for (int i = 0; i < noCon.size(); i++) {
			noCon.get(i).setId(null);
			noCon.get(i).setIsBack(false);
			FDCProDepConPayPlanNoContractEntryCollection entrys = noCon.get(i)
					.getEntrys();
			for (int j = 0; j < entrys.size(); j++) {
				entrys.get(j).setId(null);
			}
		}
	}

	/**
	 * 点击汇总<br>
	 * 将汇总结果保存到editData，然后loadFields
	 */
	public void actionSummary_actionPerformed(ActionEvent e) throws Exception {
		Integer year = spYear.getIntegerVlaue();
		Integer month = spMonth.getIntegerVlaue();
		CurProjectInfo prj = (CurProjectInfo) prmtCurProject.getValue();
		if (prj == null) {
			MsgBox.showWarning(this, "工程项目不能为空!");
			abort();
		}
		editData.setYear(year.intValue());
		editData.setMonth(month.intValue());

		String prjID = prj.getId().toString();
		String longNumber = prj.getLongNumber();
		// 汇总已签订合同分录
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("head.id");
		sic.add("head.deptment.id");
		sic.add("head.deptment.name");
		sic.add("contract.id");
		sic.add("contract.number");
		sic.add("contract.name");
		sic.add("project.name");
		sic.add("project.id");
		sic.add("project.number");
		sic.add("contractPrice");
		sic.add("lastMonthPayable");
		sic.add("lastMonthPay");
		sic.add("lastMonthNopay");
		sic.add("entrys.id");
		sic.add("entrys.planPay");
		sic.add("entrys.moneyDefine.id");
		sic.add("entrys.moneyDefine.name");
		sic.add("entrys.explain");
		sic.add("entrys.month");
		sic.add("entrys.officialPay");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.year", year));
		filter.getFilterItems().add(new FilterItemInfo("head.month", month));
		filter.getFilterItems() 
				.add(
						new FilterItemInfo("head.state",
								FDCBillStateEnum.PUBLISH_VALUE));
		//		filter.getFilterItems().add(new FilterItemInfo("project.id", prjID));
		filter.getFilterItems().add(
				new FilterItemInfo("project.longNumber like '" + longNumber + "!%'"));
		filter.getFilterItems().add(new FilterItemInfo(" project.longNumber ='" + longNumber + "'"));
		filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		view.setFilter(filter);  
		
		SorterItemCollection sort = new SorterItemCollection();
		sort.add(new SorterItemInfo("project.longNumber"));
		sort.add(new SorterItemInfo("head.deptment.name"));
		sort.add(new SorterItemInfo("entrys.seq"));
		view.setSorter(sort);
		// 取得所有符合条件已签订合同分录后，构建项目资金需求计划的已签订合同分录
		FDCDepConPayPlanContractCollection depHas = FDCDepConPayPlanContractFactory
				.getRemoteInstance()
				.getFDCDepConPayPlanContractCollection(view);
		editData.getHasContract().clear();
		if (depHas != null && depHas.size() > 0) {
			FDCProDepConPayPlanContractInfo proInfo;
			FDCProDepConPayContractEntryInfo proEntry;
			for (int i = 0; i < depHas.size(); i++) {
				FDCDepConPayPlanContractInfo conPlan = depHas.get(i);
				proInfo = new FDCProDepConPayPlanContractInfo();
				proInfo.setDepPlan(conPlan);
				proInfo.setIsBack(false);
				proInfo.setDepartment(conPlan.getHead().getDeptment());
				proInfo.setContract(conPlan.getContract());
				proInfo.setContractName(conPlan.getContract().getName());
				// Map values = calConInfo(conPlan.getContract().getId()
				// .toString());
				proInfo.setContractPrice(conPlan.getContractPrice());
				proInfo.setLastMonthPayable(conPlan.getLastMonthPayable());
				proInfo.setLastMonthPay(conPlan.getLastMonthPay());
				proInfo.setLastMonthNopay(conPlan.getLastMonthNopay());
				proInfo.setProject(conPlan.getProject());
				for (int j = 0; j < conPlan.getEntrys().size(); j++) {
					FDCDepConPayPlanContractEntryInfo entry = conPlan
							.getEntrys().get(j);
					proEntry = new FDCProDepConPayContractEntryInfo();
					proEntry.setParent(proInfo);
					proEntry.setPlanPay(entry.getPlanPay());
					proEntry.setMoneyDefine(entry.getMoneyDefine());
					proEntry.setExplain(entry.getExplain());
					proEntry.setOfficialPay(entry.getOfficialPay());
					proEntry.setMonth(entry.getMonth());
					proInfo.getEntrys().add(proEntry);
				}
				editData.getHasContract().add(proInfo);
			}
		}

		// 汇总待签订合同分录
		view = new EntityViewInfo();
		sic = new SelectorItemCollection();
		sic.add("parent.id");
		sic.add("parent.deptment.id");
		sic.add("parent.deptment.name");
		sic.add("unConNumber");
		sic.add("unConName");
		sic.add("planAmount");
		sic.add("entrys1.id");
		sic.add("entrys1.planPay");
		sic.add("entrys1.moneyDefine.id");
		sic.add("entrys1.moneyDefine.name");
		sic.add("entrys1.explain");
		sic.add("entrys1.month");
		sic.add("entrys1.officialPay");
		sic.add("project.name");
		sic.add("project.id");
		sic.add("project.number");
		view.setSelector(sic);
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.year", year));
		filter.getFilterItems().add(new FilterItemInfo("parent.month", month));
		filter.getFilterItems().add(
				new FilterItemInfo("parent.state",
						FDCBillStateEnum.PUBLISH_VALUE));
		//		filter.getFilterItems().add(new FilterItemInfo("project.id", prjID));
		//		filter.getFilterItems().add(new FilterItemInfo("project.longNumber like '" + longNumber + "%'"));
		filter.getFilterItems().add(new FilterItemInfo("project.longNumber like '" + longNumber + "!%'"));
		filter.getFilterItems().add(new FilterItemInfo(" project.longNumber ='" + longNumber + "'"));
		filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		view.setFilter(filter);
		sort = new SorterItemCollection();
		sort.add(new SorterItemInfo("project.longNumber"));
		sort.add(new SorterItemInfo("parent.deptment.id"));
		sort.add(new SorterItemInfo("entrys1.seq"));
		view.setSorter(sort);
		// 取得所有符合条件待签订合同分录后，构建项目资金需求计划的待签订合同分录
		FDCDepConPayPlanUnsettledConCollection depUnC = FDCDepConPayPlanUnsettledConFactory
				.getRemoteInstance().getFDCDepConPayPlanUnsettledConCollection(
						view);
		editData.getUnsettledCon().clear();
		if (depUnC != null && depUnC.size() > 0) {
			FDCProDepConPayPlanUnsettledInfo proInfo;
			FDCProDepConPayPlanUnsetEntryInfo proEntry;
			for (int i = 0; i < depUnC.size(); i++) {
				FDCDepConPayPlanUnsettledConInfo UnCPlan = depUnC.get(i);
				proInfo = new FDCProDepConPayPlanUnsettledInfo();
				proInfo.setDepPlan(UnCPlan);
				proInfo.setIsBack(false);
				proInfo.setDepartment(UnCPlan.getParent().getDeptment());
				proInfo.setUnConNumber(UnCPlan.getUnConNumber());
				proInfo.setUnConName(UnCPlan.getUnConName());
				proInfo.setPlanAmount(UnCPlan.getPlanAmount());
				proInfo.setProject(UnCPlan.getProject());
				for (int j = 0; j < UnCPlan.getEntrys1().size(); j++) {
					FDCDepConPayPlanUnsettleEntryInfo entry = UnCPlan
							.getEntrys1().get(j);
					proEntry = new FDCProDepConPayPlanUnsetEntryInfo();
					proEntry.setParent(proInfo);
					proEntry.setPlanPay(entry.getPlanPay());
					proEntry.setMoneyDefine(entry.getMoneyDefine());
					proEntry.setExplain(entry.getExplain());
					proEntry.setOfficialPay(entry.getOfficialPay());
					proEntry.setMonth(entry.getMonth());
					proInfo.getEntrys().add(proEntry);
				}
				editData.getUnsettledCon().add(proInfo);
			}
		}

		// 汇总无合同分录
		view = new EntityViewInfo();
		sic = new SelectorItemCollection();
		sic.add("head.id");
		sic.add("head.deptment.id");
		sic.add("head.deptment.name");
		sic.add("payMatters");
		sic.add("payMattersName");
		sic.add("entrys1.id");
		sic.add("entrys1.planPay");
		sic.add("entrys1.moneyDefine.id");
		sic.add("entrys1.moneyDefine.name");
		sic.add("entrys1.explain");
		sic.add("entrys1.month");
		sic.add("entrys1.officialPay");
		sic.add("project.name");
		sic.add("project.id");
		sic.add("project.number");
		view.setSelector(sic);
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.year", year));
		filter.getFilterItems().add(new FilterItemInfo("head.month", month));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("head.state",
								FDCBillStateEnum.PUBLISH_VALUE));
		//		filter.getFilterItems().add(new FilterItemInfo("project.id", prjID));
		filter.getFilterItems().add(new FilterItemInfo("project.longNumber like '" + longNumber + "!%'"));
		filter.getFilterItems().add(new FilterItemInfo(" project.longNumber ='" + longNumber + "'"));
		filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		view.setFilter(filter);
		sort = new SorterItemCollection();
		sort.add(new SorterItemInfo("project.longNumber"));
		sort.add(new SorterItemInfo("head.deptment.id"));
		sort.add(new SorterItemInfo("entrys1.seq"));
		view.setSorter(sort);
		// 取得所有符合条件待签订合同分录后，构建项目资金需求计划的待签订合同分录
		FDCDepConPayPlanNoContractCollection depNoC = FDCDepConPayPlanNoContractFactory
				.getRemoteInstance().getFDCDepConPayPlanNoContractCollection(
						view);
		editData.getNoContract().clear();
		if (depNoC != null && depNoC.size() > 0) {
			FDCProDepConPayPlanNoContractInfo proInfo;
			FDCProDepConPayPlanNoContractEntryInfo proEntry;
			for (int i = 0; i < depNoC.size(); i++) {
				FDCDepConPayPlanNoContractInfo noCPlan = depNoC.get(i);
				proInfo = new FDCProDepConPayPlanNoContractInfo();
				proInfo.setDepPlan(noCPlan);
				proInfo.setIsBack(false);
				proInfo.setDepartment(noCPlan.getHead().getDeptment());
				proInfo.setPayMatters(noCPlan.getPayMatters());
				proInfo.setPayMattersName(noCPlan.getPayMattersName());
				proInfo.setProject(noCPlan.getProject());
				for (int j = 0; j < noCPlan.getEntrys1().size(); j++) {
					FDCDepConPayPlanNoContractEntryInfo entry = noCPlan
							.getEntrys1().get(j);
					proEntry = new FDCProDepConPayPlanNoContractEntryInfo();
					proEntry.setParent(proInfo);
					proEntry.setPlanPay(entry.getPlanPay());
					proEntry.setMoneyDefine(entry.getMoneyDefine());
					proEntry.setExplain(entry.getExplain());
					proEntry.setOfficialPay(entry.getOfficialPay());
					proEntry.setMonth(entry.getMonth());
					proInfo.getEntrys().add(proEntry);
				}
				editData.getNoContract().add(proInfo);
			}
		}
		editData.setNumber(txtNumber.getText());
		editData.setName(txtName.getText());
		if (!STATUS_ADDNEW.equals(getOprtState())) {
			editData.setIsReSum(true);
		}
		loadFields();
		isReSum = true;
	}

	public void actionBack_actionPerformed(ActionEvent e) throws Exception {
		if (editData == null) {
			MsgBox.showWarning(this, "请先保存单据！");
			abort();
		} else if (isReSum) {
			MsgBox.showWarning(this, "重新汇总过后，请先保存或提交！");
			abort();
		} else if (!FDCBillStateEnum.SAVED.equals(editData.getState())
				&& !FDCBillStateEnum.SUBMITTED.equals(editData.getState())
				&& !FDCBillStateEnum.BACK.equals(editData.getState())) {
			MsgBox.showWarning(this, "只有保存、提交、打回状态的项目资金需求计划可以做打回操作！");
			abort();
		}
		int[] selectedRows = KDTableUtil.getSelectedRows(getDetailTable());
		if (selectedRows.length > 0) {
			for (int i = 0; i < selectedRows.length; i++) {
				IRow row = getDetailTable().getRow(selectedRows[i]);

				String colKey = null;
				if (pnlEntry.getSelectedIndex() == 1) {
					colKey = "conNumber";
				} else if (pnlEntry.getSelectedIndex() == 2) {
					colKey = "unConNum";
				} else if (pnlEntry.getSelectedIndex() == 3) {
					colKey = "payMatters";
				}
				if ("小计".equals(row.getCell(colKey).getValue()) || "合计".equals(row.getCell("department").getValue())
						|| "总计".equals(row.getCell("project").getValue())) {
					MsgBox.showWarning(this, "请选中非统计行打回！");
					this.abort();
				} else if (((Boolean) row.getCell("isBack").getValue()).booleanValue()) {
					MsgBox.showWarning(this, "不能重复打回！");
					this.abort();
				}
			}
			if (MsgBox.showConfirm2(this, "是否打回选中的计划？") == MsgBox.OK) {
				for (int i = 0; i < selectedRows.length; i++) {
					IRow row = getDetailTable().getRow(selectedRows[i]);

					BOSUuid id = (BOSUuid) row.getCell("id").getValue();
					checkHasNewVersion(id);
					((IFDCProDepConPayPlan) getBizInterface()).setConPlanBack(id.toString());
					editData = ((IFDCProDepConPayPlan) getBizInterface()).getFDCProDepConPayPlanInfo(new ObjectUuidPK(editData.getId()),
							getSelectors());
					loadFields();
				}
			}
		} else {
			MsgBox.showWarning(this, "请先选中行！");
		}
	}

	/**
	 * 检查某个分录是否有新版本计划<br>
	 * add by emanon
	 * 
	 * @param pk
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected void checkHasNewVersion(BOSUuid pk) throws BOSException,
			SQLException {
		if (pk == null) {
			return;
		}
		FDCSQLBuilder sql = new FDCSQLBuilder();
		if (new FDCProDepConPayPlanContractInfo().getBOSType().equals(
				pk.getType())) {
			sql
					.appendSql(" select h.FID as billID from T_FNC_FDCProDepConPayPlanC as c ");
			sql.appendSql(" left join T_FNC_FDCDepConPayPlanContract as d ");
			sql.appendSql(" on d.FID = c.FDepPlanID ");
			sql.appendSql(" left join T_FNC_FDCDepConPayPlanBill as h ");
			sql.appendSql(" on h.FID = d.FHeadID ");
		} else if (new FDCProDepConPayPlanNoContractInfo().getBOSType().equals(
				pk.getType())) {
			sql
					.appendSql(" select h.FID as billID from T_FNC_FDCProDepConPayPlanNoC as c ");
			sql.appendSql(" left join T_FNC_FDCDepConPayPlanNoCon as d ");
			sql.appendSql(" on d.FID = c.FDepPlanID ");
			sql.appendSql(" left join T_FNC_FDCDepConPayPlanBill as h ");
			sql.appendSql(" on h.FID = d.FHeadID ");
		} else if (new FDCProDepConPayPlanUnsettledInfo().getBOSType().equals(
				pk.getType())) {
			sql
					.appendSql(" select h.FID as billID from T_FNC_FDCProDepConPayPlanU as c ");
			sql.appendSql(" left join T_FNC_FDCDepConPayPlanUC as d ");
			sql.appendSql(" on d.FID = c.FDepPlanID ");
			sql.appendSql(" left join T_FNC_FDCDepConPayPlanBill as h ");
			sql.appendSql(" on h.FID = d.FParentID ");
		}
		sql.appendSql(" where c.FID = ? ");
		sql.addParam(pk.toString());
		IRowSet rs = sql.executeQuery();
		while (rs.next()) {
			String billID = rs.getString("billID");
			if (!FDCHelper.isEmpty(billID)) {
				sql.clear();
				sql
						.appendSql(" select FID as newID from T_FNC_FDCDepConPayPlanBill ");
				sql
						.appendSql(" where FNumber = (select FNumber from T_FNC_FDCDepConPayPlanBill where FID = ?) ");
				sql
						.appendSql(" and FDeptmentID = (select FDeptmentID from T_FNC_FDCDepConPayPlanBill where FID = ?) ");
				sql.appendSql(" order by FVersion desc ");
				sql.addParam(billID);
				sql.addParam(billID);
				IRowSet rs2 = sql.executeQuery();
				while (rs2.next()) {
					String newID = rs2.getString("newID");
					if (!billID.equals(newID)) {
						FDCMsgBox.showWarning(this, "该合同月度滚动计划已有新版本，请先汇总。");
						SysUtil.abort();
					}
					break;
				}
			}
			break;
		}
	}

	public void actionPublish_actionPerformed(ActionEvent e) throws Exception {
		if (MsgBox.showConfirm2("是否确定上报？") == MsgBox.CANCEL) {
			return;
		}
		if (this.editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
			this.editData.setState(FDCBillStateEnum.PUBLISH);
			String id = getSelectBOID();
			if (id != null) {
				FDCProDepConPayPlanFactory.getRemoteInstance().setPublish(
						BOSUuid.read(id));
			}
			actionPublish.setEnabled(false);
		}
	}

	public void actionRevise_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getState().equals(FDCBillStateEnum.BACK)) {
			flag_state = "revist";
			super.actionEdit_actionPerformed(e);
			actionSave.setEnabled(false);
			actionAddNew.setEnabled(false);
			actionRemove.setEnabled(false);
			txtName.setEnabled(false);
			txtNumber.setEnabled(false);
			spMonth.setEnabled(false);
			spYear.setEnabled(false);
			description.setEnabled(false);
			actionRevise.setEnabled(false);
			promAuditor.setValue(null);
			picAuditorTime.setValue(null);
		} else {
			MsgBox.showWarning(this, "被打回的计划才能修订!");
		}
	}

	public void actionViewContract_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.pnlEntry.getSelectedIndex() == 1) {
			IRow row = KDTableUtil.getSelectedRow(tblContract);
			if (row == null) {
				MsgBox.showWarning(this, "请先选中行!");
				abort();
			}
			ContractBillInfo con = (ContractBillInfo) row.getCell("conNumber")
					.getValue();
			String id = null;
			if (con != null) {
				id = con.getId().toString();
			}
			if (id == null) {
				MsgBox.showWarning(this, "选中分录合同不存在!");
				abort();
			}

			Map ctx = new UIContext(this);
			ctx.put(UIContext.ID, id);
			ctx.put(UIContext.OWNER, this);
			// UIFactoryName.MODEL 为弹出模式
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
					ContractBillEditUI.class.getName(), ctx, null, OprtState.VIEW);
			// 开始展现UI
			uiWindow.show();
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("curProject.id");
		sic.add("curProject.number");
		sic.add("curProject.name");
		sic.add("curProject.longnumber");
		sic.add("payPlanCycle.id");
		sic.add("payPlanCycle.cycle");
		sic.add("creator.id");
		sic.add("creator.number");
		sic.add("creator.name");
		sic.add("auditor.id");
		sic.add("auditor.number");
		sic.add("auditor.name");

		sic.add("hasContract.*");
		sic.add("hasContract.project.*");
		sic.add("hasContract.depPlan.id");
		sic.add("hasContract.department.id");
		sic.add("hasContract.department.number");
		sic.add("hasContract.department.name");
		sic.add("hasContract.contract.id");
		sic.add("hasContract.contract.number");
		sic.add("hasContract.contract.name");
		sic.add("hasContract.entrys.*");
		sic.add("hasContract.entrys.moneyDefine.id");
		sic.add("hasContract.entrys.moneyDefine.number");
		sic.add("hasContract.entrys.moneyDefine.name");
		

		sic.add("unsettledCon.*");
		sic.add("unsettledCon.project.*");
		sic.add("unsettledCon.depPlan.id");
		sic.add("unsettledCon.department.id");
		sic.add("unsettledCon.department.number");
		sic.add("unsettledCon.department.name");
		sic.add("unsettledCon.entrys.*");
		sic.add("unsettledCon.entrys.moneyDefine.id");
		sic.add("unsettledCon.entrys.moneyDefine.number");
		sic.add("unsettledCon.entrys.moneyDefine.name");

		sic.add("noContract.*");
		sic.add("noContract.project.*");
		sic.add("noContract.depPlan.id");
		sic.add("noContract.department.id");
		sic.add("noContract.department.number");
		sic.add("noContract.department.name");
		sic.add("noContract.entrys.*");
		sic.add("noContract.entrys.moneyDefine.id");
		sic.add("noContract.entrys.moneyDefine.number");
		sic.add("noContract.entrys.moneyDefine.name");

		return sic;
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub

	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCProDepConPayPlanFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		if (pnlEntry.getSelectedIndex() == 0) {
			return tblTotal;
		} else if (pnlEntry.getSelectedIndex() == 1) {
			return tblContract;
		} else if (pnlEntry.getSelectedIndex() == 2) {
			return tblUnSetteled;
		} else if (pnlEntry.getSelectedIndex() == 3) {
			return tblNoContract;
		}
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public boolean isModify() {
		return false;
	}

	protected void initDataStatus() {
		super.initDataStatus();
		actionAudit.setVisible(false);
		actionUnAudit.setVisible(false);
		actionPublish.setEnabled(false);
		actionRevise.setEnabled(false);
		actionBack.setEnabled(false);

		if (STATUS_ADDNEW.equals(getOprtState())) {
			actionSummary.setEnabled(true);
		} else if (STATUS_EDIT.equals(getOprtState())) {
			actionSummary.setEnabled(true);
		} else {
			actionSummary.setEnabled(false);
		}

		if (editData != null) {
			if (FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
				actionPublish.setEnabled(true);
			} else if (FDCBillStateEnum.BACK.equals(editData.getState())) {
				actionRevise.setEnabled(true);
				actionEdit.setEnabled(false);
				if (pnlEntry.getSelectedIndex() != 0) {
					actionBack.setEnabled(true);
				}
			} else if (FDCBillStateEnum.SAVED.equals(editData.getState())) {
				if (pnlEntry.getSelectedIndex() != 0) {
					actionBack.setEnabled(true);
				}
			} else if (FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
				if (pnlEntry.getSelectedIndex() != 0) {
					actionBack.setEnabled(true);
				}
			}
		}
	}

	/**
	 * 根据合同ID计算最新造价、累计完工金额、累计已付工程款、累计未付工程款<br>
	 * 由于要跟合同滚动计划一致，此处不再计算，改为带出
	 * 
	 * @deprecated
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected Map calConInfo(String conID) throws EASBizException, BOSException {
		Map values = new HashMap();
		// 带出数据
		Map param = new HashMap();
		Integer year = (Integer) spYear.getValue();
		String date = "";
		if (month_old.intValue() == 12) {
			date = "{ts'" + (year.intValue() + 1) + "-1-1 00:00:00'}";
		} else {
			date = "{ts'" + year.intValue() + "-" + (month_old.intValue() + 1)
					+ "-1 00:00:00'}";
		}
		String flag = "false";
		if (separteFromPayment) {
			flag = "true";
		}
		param.put("date", date);
		param.put("conID", conID);
		param.put("flag", flag);
		values = FDCDepConPayPlanBillFactory.getRemoteInstance().getPlanPay(
				param);
		// row.getCell("lastMonthPayable").setValue(
		// valuse.get("payable"));
		// row.getCell("lastMonthPay").setValue(valuse.get("pay"));
		// row.getCell("lastMonthNopay").setValue(valuse.get("noPay"));
		// 得到合同最新造价
		Map retVal = FDCUtils.getLastAmt_Batch(null, new String[] { conID });
		BigDecimal conLastestPrice = (BigDecimal) retVal.get(conID);
		values.put("conPrice", conLastestPrice);
		// row.getCell("conPrice").setValue(conLastestPrice);
		return values;
	}
	
	
	/**
	 * 打印
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		FDCProDepConPayPlanPrintDataProvider data = new FDCProDepConPayPlanPrintDataProvider(editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	/**
	 * 打印预览
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		
		ArrayList idList = new ArrayList();
		if (editData != null && !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		FDCProDepConPayPlanPrintDataProvider data = new FDCProDepConPayPlanPrintDataProvider(editData.getString("id"), getTDQueryPK(), this
				.getDetailTable());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	// 获得无文本合同套打对应的目录
	protected String getTDFileName() {
		return "/bim/fdc/finance/FDCProDepConPayPlan";
	}

	// 对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.finance.app.FDCProDepConPayPlanPrintQuery");
	}

	/**
	 * 描述：提交前检查是否有新版本的付款计划 - R130801-0225
	 * @throws BOSException
	 * @throws SQLException
	 * @Author：zhaoqin
	 * @CreateTime：2013-9-18
	 */
	private void checkBeforeSubmit() throws BOSException, SQLException {
		// 合同计划分录,待签订合同计划分录,无合同计划分录
		KDTable[] kdtbls = { tblContract, tblUnSetteled, tblNoContract };
		for (int i = 0; i < kdtbls.length; i++) {
			for (int j = 0; j < kdtbls[i].getRowCount(); j++) {
				IRow row = kdtbls[i].getRow(j);
				if ("总计".equals(row.getCell("project").getValue()) || "合计".equals(row.getCell("department").getValue())) {
					continue;
				}
				BOSUuid id = (BOSUuid) row.getCell("id").getValue();
				checkHasNewVersion(id);
			}
		}
	}
}
