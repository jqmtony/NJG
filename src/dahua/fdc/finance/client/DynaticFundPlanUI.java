/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartFactory;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.LegendItem;
import com.kingdee.bos.ctrl.freechart.chart.LegendItemCollection;
import com.kingdee.bos.ctrl.freechart.chart.axis.CategoryAxis;
import com.kingdee.bos.ctrl.freechart.chart.axis.CategoryLabelPositions;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberAxis;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardCategoryItemLabelGenerator;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardCategoryToolTipGenerator;
import com.kingdee.bos.ctrl.freechart.chart.plot.CategoryPlot;
import com.kingdee.bos.ctrl.freechart.chart.plot.DatasetRenderingOrder;
import com.kingdee.bos.ctrl.freechart.chart.plot.Plot;
import com.kingdee.bos.ctrl.freechart.chart.plot.PlotOrientation;
import com.kingdee.bos.ctrl.freechart.chart.renderer.category.CategoryItemRenderer;
import com.kingdee.bos.ctrl.freechart.chart.renderer.category.GroupedStackedBarRenderer;
import com.kingdee.bos.ctrl.freechart.chart.renderer.category.LineAndShapeRenderer;
import com.kingdee.bos.ctrl.freechart.data.KeyToGroupMap;
import com.kingdee.bos.ctrl.freechart.data.category.CategoryDataset;
import com.kingdee.bos.ctrl.freechart.data.category.DefaultCategoryDataset;
import com.kingdee.bos.ctrl.freechart.data.general.Dataset;
import com.kingdee.bos.ctrl.freechart.ui.GradientPaintTransformType;
import com.kingdee.bos.ctrl.freechart.ui.StandardGradientPaintTransformer;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FdcRptConstant;
import com.kingdee.eas.fdc.basedata.IFdcRptBaseFacade;
import com.kingdee.eas.fdc.basedata.IREAutoRemember;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcMapUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectUtil;
import com.kingdee.eas.fdc.finance.DynaticFundPlanRptFacadeFactory;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleChartProvider;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述：动态资金计划
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-27
 * @see
 * @since JDK1.4
 */
public class DynaticFundPlanUI extends AbstractDynaticFundPlanUI {

	private static final Logger logger = CoreUIObject.getLogger(DynaticFundPlanUI.class);

	// 当前列
	public String curName;
	// 计划开工日期
	private Date startDate;
	// 计划完工日期
	private Date endDate;

	// 是否已经初始化
	protected boolean isInit;
	// 参数
	protected RptParams params;

	// 图标值下限
	private BigDecimal lowerBound = FDCConstants._ONE;
	// 图标值上限
	private BigDecimal upperBound = FDCConstants.ONE;

	/**
	 * 描述：构造函数
	 * 
	 * @throws Exception
	 * @author：skyiter_wang
	 * @date：2013-10-5
	 */
	 

	public DynaticFundPlanUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 描述：获得远程实例
	 * 
	 * @return
	 * @throws BOSException
	 * @author：skyiter_wang
	 * @date：2013-10-8
	 */
	 

	private IFdcRptBaseFacade getRemoteInstance() throws BOSException {
		return DynaticFundPlanRptFacadeFactory.getRemoteInstance();
	}

	/**
	 * 描述：取得选择的工程项目，并做检查
	 * 
	 * @param isCheck
	 * @return
	 * @author：skyiter_wang
	 * @date：2013-9-27
	 */
	protected CurProjectInfo getSelectedProject(boolean isCheck) {
		CurProjectInfo currentProject = null;

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
			currentProject = (CurProjectInfo) node.getUserObject();
		}

		if (isCheck && null == currentProject) {
			MsgBox.showWarning(this, "请选择工程项目");
			SysUtil.abort();
		}

		return currentProject;
	}

	public void onLoad() throws Exception {
		super.onLoad();

		// 加载上一次的记忆
		// initPreTime();

		isInit = true;
	}

	protected void initPreTime() {
		// 当前用户
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		// 当前组是织
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();

		String userID = user.getId().toString();
		String orgUnitID = orgUnit.getId().toString();
		String function = "DynaticFundPlanUI";
		String treeId = null;
		try {
			treeId = REAutoRememberFactory.getRemoteInstance().getValue(userID, orgUnitID, function);
			if (!FDCHelper.isEmpty(treeId)) {
				// DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode();
				// CurProjectInfo info = new CurProjectInfo();
				// info.setId(BOSUuid.read(treeId));
				// node.setUserObject(info);
				TreeModel model = treeMain.getModel();
				DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) model.getRoot();
				Enumeration e = root.depthFirstEnumeration();
				while (e.hasMoreElements()) {
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) e.nextElement();
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof CurProjectInfo) {
						CurProjectInfo info = (CurProjectInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							curName = info.getName();
							initChart();
						}
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void initWorkButton() {
		// TODO Auto-generated method stub
		super.initWorkButton();

		// 隐藏菜单栏 工具和服务
		menuTool.setVisible(false);
		MenuService.setVisible(false);
		menuItemPrint.setVisible(true);
		menuItemPrintPre.setVisible(true);
	}

	public void initParamToUI(RptParams params) {
		// 取得选择的工程项目，并做检查
		CurProjectInfo curProject = getSelectedProject(true);
		params.setObject("curProject", curProject);
		params.setObject("curProject.id", curProject.getId().toString());

		String startYearObj = cbYear.getSelectedItem().toString();
		String startMonthObj = cbStartMonth.getSelectedItem().toString();
		String endYearObj = cbEndYear.getSelectedItem().toString();
		String endMonthObj = cbEndMonth.getSelectedItem().toString();

		Integer startYear = new Integer(startYearObj);
		Integer startMonth = new Integer(startMonthObj);
		Integer startYearMonth = new Integer(startYear.intValue() * 100 + startMonth.intValue());
		Integer endYear = new Integer(endYearObj);
		Integer endMonth = new Integer(endMonthObj);
		Integer endYearMonth = new Integer(endYear.intValue() * 100 + endMonth.intValue());

		if (FDCNumberHelper.compareTo(startYearMonth, endYearMonth) > 0) {
			MsgBox.showWarning(this, "起始期间不能大于截至期间");
			SysUtil.abort();
		}

		params.setObject("startYear", startYear + "");
		params.setObject("startMonth", startMonth + "");
		params.setObject("startYearMonth", startYearMonth + "");
		params.setObject("endYear", endYear + "");
		params.setObject("endMonth", endMonth + "");
		params.setObject("endYearMonth", endYearMonth + "");
	}

	/**
	 * 查询tblMain
	 */
	private void query_tblMain() {
		params = (RptParams) FdcObjectUtil.defaultIfNull(params, new RptParams());

		// 清空表格
		tblMain.removeRows();

		// 查询条件设置到界面
		initParamToUI(params);

		// 设置查询表格
		params.setObject(FdcRptConstant.KDTABLE, "tblMain");

		// 查询数据
		try {
			RptParams rps = getRemoteInstance().query(params);
			// 返回结果
			Map result = (Map) rps.getObject(FdcRptConstant.RPT_QUERYRESULT);
			// 将数据填入到表格
			fillTable_tblMain(result);
		} catch (Exception e) {
			logger.error(e);
			this.handUIExceptionAndAbort(e);
		} finally {
			// 重新绘制图形
			initChart();
		}
	}

	// 数据填入到表格_tblMain
	protected int fillTable_tblMain(Map result) {
		// 解析结果集
		Map parseMap = parseResult_tblMain(result);

		// 初始化动态表格
		initDynaTable_tblMain(parseMap);

		// /////////////////////////////////////////////////////////////////////////////////////

		if (FdcMapUtil.isNotEmpty(parseMap)) {
			Set keySet = (Set) parseMap.get("keySet");
			int columnIndex = 1;

			Map inComeMap = (Map) parseMap.get("inComeMap");
			Map payOutMap = (Map) parseMap.get("payOutMap");

			// 现金流入
			IRow inComeRow = this.tblMain.addRow();
			// 现金流出
			IRow payOutRow = this.tblMain.addRow();
			// 净现金流量
			IRow netRow = this.tblMain.addRow();
			// 累计净现金流量
			IRow totalNetRow = this.tblMain.addRow();

			inComeRow.getCell(0).setValue("现金流入");
			payOutRow.getCell(0).setValue("现金流出");
			netRow.getCell(0).setValue("净现金流量");
			totalNetRow.getCell(0).setValue("累计净现金流量");

			String columnName = null;
			BigDecimal inComeAmount = null;
			BigDecimal payOutAmount = null;
			BigDecimal netAmount = null;
			BigDecimal totalNetAmount = null;
			List amountList = new ArrayList();

			for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
				columnName = (String) iterator.next();

				inComeAmount = FDCNumberHelper.toBigDecimal(inComeMap.get(columnName), 2);
				payOutAmount = FDCNumberHelper.toBigDecimal(payOutMap.get(columnName), 2);
				netAmount = FDCNumberHelper.subtract(inComeAmount, payOutAmount);
				totalNetAmount = FDCNumberHelper.add(totalNetAmount, netAmount);

				inComeRow.getCell(columnIndex).setValue(inComeAmount);
				payOutRow.getCell(columnIndex).setValue(payOutAmount);
				netRow.getCell(columnIndex).setValue(netAmount);
				totalNetRow.getCell(columnIndex).setValue(totalNetAmount);

				amountList.add(inComeAmount);
				// 现金流出 以负数形式展示
				amountList.add(payOutAmount.negate());
				amountList.add(netAmount);
				amountList.add(totalNetAmount);

				columnIndex++;
			}

			// /////////////////////////////////////////////////////////////////////////////////////

			if (FdcCollectionUtil.isNotEmpty(amountList)) {
				// 排序
				Collections.sort(amountList);
				BigDecimal minAmount = (BigDecimal) amountList.get(0);
				BigDecimal maxAmount = (BigDecimal) amountList.get(amountList.size() - 1);
				minAmount = FDCNumberHelper.isNullZero(minAmount) ? FDCConstants._ONE : minAmount;
				maxAmount = FDCNumberHelper.isNullZero(maxAmount) ? FDCConstants.ONE : maxAmount;
				
				// 上下限相差不能超过5倍
				BigDecimal tempMinAmount = FDCNumberHelper.divide(minAmount.abs(), new BigDecimal("5"));
				BigDecimal tempMaxAmount = FDCNumberHelper.divide(maxAmount.abs(), new BigDecimal("5"));
				if (FDCNumberHelper.compareTo(tempMinAmount, maxAmount.abs()) > 0) {
					maxAmount = tempMinAmount;
					minAmount = minAmount.abs().negate();
				} else if (FDCNumberHelper.compareTo(tempMaxAmount, minAmount.abs()) > 0) {
					minAmount = tempMaxAmount.negate();
					maxAmount = maxAmount.abs();
				}

				// 上下限不能紧贴边界，应该预留一点空隙
				minAmount = FDCNumberHelper.multiply(minAmount, new BigDecimal("1.1"));
				maxAmount = FDCNumberHelper.multiply(maxAmount, new BigDecimal("1.1"));

				// // 图标值下限
				// lowerBound = FDCNumberHelper.compareTo(lowerBound, minAmount) < 0 ? upperBound :
				// minAmount;
				// // 图标值上限
				// upperBound = FDCNumberHelper.compareTo(upperBound, maxAmount) > 0 ? upperBound :
				// maxAmount;
				// 图标值下限
				lowerBound = minAmount;
				// 图标值上限
				upperBound = maxAmount;
			} else {
				// 图标值下限
				lowerBound = FDCConstants._ONE;
				// 图标值上限
				upperBound = FDCConstants.ONE;
			}
		}

		// /////////////////////////////////////////////////////////////////////////////////////

		// 填充表格之后
		afterFillTable_tblMain(parseMap);

		return tblMain.getRowCount();
	}

	/**
	 * 描述：解析结果集
	 * 
	 * @param result
	 * @return
	 * @author：skyiter_wang
	 * @date：2013-9-28
	 */
	protected Map parseResult_tblMain(Map result) {
		// 使用TreeMap排序
		Map parseMap = new TreeMap();

		RptRowSet inComeResult = (RptRowSet) result.get("result.inCome");
		RptRowSet payOutResult = (RptRowSet) result.get("result.payOut");

		// //////////////////////////////////////////////////////////////////////

		inComeResult.reset();
		payOutResult.reset();

		Map inComeMap = new TreeMap();
		while (inComeResult.next()) {
			// 目标单据，房地产售楼T_SHE_SellPlan中年份对应的字段是VARCHAR
			Object planYear = inComeResult.getObject("planYear");
			Object year = planYear;

			// 十二个月
			for (int i = 1; i <= 12; i++) {
				// int year = planYear.intValue();
				int month = i;
				String key = "amount_" + year + "_" + (month < 10 ? "0" + month : month);
				Object value = inComeResult.getBigDecimal("month" + i + "Amount");
				// 单位：万元(现金流入对应的表中存的数据已经是万元，此处不需要再除)
				// value = FDCNumberHelper.divide(value, FDCConstants.TEN_THOUSAND);

				inComeMap.put(key, value);
			}
		}
		parseMap.put("inComeMap", inComeMap);

		Map payOutMap = new TreeMap();
		while (payOutResult.next()) {
			int payMonth = payOutResult.getInt("payMonth");
			int year = payMonth / 100;
			int month = payMonth % 100;
			String key = "amount_" + year + "_" + (month < 10 ? "0" + month : month);
			Object value = payOutResult.getBigDecimal("payAmount");
			// 单位：万元
			value = FDCNumberHelper.divide(value, FDCConstants.TEN_THOUSAND);

			payOutMap.put(key, value);
		}
		parseMap.put("payOutMap", payOutMap);

		// //////////////////////////////////////////////////////////////////////

		Set inComeKeySet = inComeMap.keySet();
		Set payOutKeySet = payOutMap.keySet();

		// 现金流入对应的表结构设计有不合理，没有存月份，导致查询SQL不好写。服务端SQL过滤只到年；月份过滤在客户端处理
		List inComeKeyList = new ArrayList(inComeKeySet);
		List subInComeKeyList = new ArrayList();
		if (FdcCollectionUtil.isNotEmpty(inComeKeyList)) {
			String startYear = params.getString("startYear");
			String startMonth = params.getString("startMonth");
			int startMonthInt = Integer.parseInt(startMonth);
			String startKey = "amount_" + startYear + "_" + (startMonthInt < 10 ? "0" + startMonthInt : startMonthInt);
			int startIndex = inComeKeyList.indexOf(startKey);
			// 超出下限
			startIndex = (startIndex < 0) ? 0 : startIndex;

			String endYear = params.getString("endYear");
			String endMonth = params.getString("endMonth");
			int endMonthInt = Integer.parseInt(endMonth);
			String endKey = "amount_" + endYear + "_" + (endMonthInt < 10 ? "0" + endMonthInt : endMonthInt);
			int endIndex = inComeKeyList.indexOf(endKey);
			// 超出上限
			endIndex = (endIndex < 0) ? inComeKeyList.size() - 1 : endIndex;

			subInComeKeyList = inComeKeyList.subList(startIndex, endIndex + 1);
		}

		// //////////////////////////////////////////////////////////////////////

		Set keySet = new TreeSet();
		keySet.addAll(subInComeKeyList);
		keySet.addAll(payOutKeySet);

		parseMap.put("keySet", keySet);

		// //////////////////////////////////////////////////////////////////////

		return parseMap;
	}

	/**
	 * 描述：初始化动态表格
	 * <p>
	 * 1、key格式为amount_2012_8<br>
	 * 2、标题格式为2012.8<br>
	 * 
	 * @author：skyiter_wang
	 * @date：2013-9-27
	 */
	protected void initDynaTable_tblMain(Map parseMap) {
		if (FdcMapUtil.isEmpty(parseMap)) {
			return;
		}

		Set keySet = (Set) parseMap.get("keySet");
		int columnIndex = 1;
		IRow headRow = this.tblMain.getHeadRow(0);

		int columnCount = this.tblMain.getColumnCount();
		for (int i = columnCount - 1; i >= 1; i--) {
			this.tblMain.removeColumn(i);
		}

		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String columnName = (String) iterator.next();
			String titleName = columnName.replaceFirst("amount_", "");
			titleName = titleName.replaceFirst("_", ".");

			IColumn column = this.tblMain.addColumn(columnIndex);
			// key格式为amount_2012_8
			column.setKey(columnName);
			// 标题格式为2012.8
			headRow.getCell(columnIndex).setValue(titleName);

			columnIndex++;
		}
	}

	/**
	 * 填充表格之后_tblMain
	 * 
	 * @param rowRsMap
	 */
	private void afterFillTable_tblMain(Map rowRsMap) {
	}

	/**
	 * 初始化图形面板
	 */
	protected void initChart() {
		super.initChart();

		fillChart();
	}

	/**
	 * add by libing at 2011-8-31
	 */
	protected void cbYear_itemStateChanged(java.awt.event.ItemEvent e) throws Exception {
	}

	protected void fillChart() {
		pnlChart.removeAll();
		pnlChart.add(createChartPanel(), BorderLayout.CENTER);
		updateUI();
	}

	protected JPanel createChartPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}

	/**
	 * 初始化左边树 add by libing
	 */
	protected void initTree() throws Exception {
		super.initTree();

		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
		// 默认展开到最末级
		treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	/**
	 * 初始化表头
	 */
	protected void initHead() throws Exception {
		// TODO Auto-generated method stub
		super.initHead();

		if (isInit) {
			return;
		}

		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		// 前后20年
		for (int i = currentYear - 20, maxYear = currentYear + 20; i <= maxYear; i++) {
			cbYear.addItem(new Integer(i));
			cbEndYear.addItem(new Integer(i));
		}
		// 默认选择当前年
		for (int i = 0; i < cbYear.getItemCount(); i++) {
			int tempYear = Integer.parseInt(cbYear.getItemAt(i).toString());
			if (tempYear == currentYear) {
				cbYear.setSelectedIndex(i);
				cbEndYear.setSelectedIndex(i);
			}
		}

		// 1月到12月
		for (int i = 1, maxMonth = 12; i <= maxMonth; i++) {
			cbStartMonth.addItem(new Integer(i));
			cbEndMonth.addItem(new Integer(i));
		}
		// 默认选择第1月，第12月
		cbStartMonth.setSelectedIndex(0);
		cbEndMonth.setSelectedIndex(11);
	}

	/**
	 * 初始化表格
	 */
	protected void initTable() {
		super.initTable();

		// 表头第一行标题为空
		tblMain.getHead().getRow(0).getCell(0).setValue("");
		// 锁定表格
		tblMain.getStyleAttributes().setLocked(true);
	}

	/**
	 * 返回一个柱状图数据集<br>
	 * 实际应用时，在此方法中取数并构建数据集返回
	 * <p>
	 * 第一个参数为完成百分比<br>
	 * 第二个参数为分组，其中括号外为属于柱分组，括号内为柱内分组<br>
	 * 第三个参数为月度
	 */
	protected Dataset createDataset_bak() {
		// DefaultCategoryDataset result = new DefaultCategoryDataset();
		// int rowCount = tblMain.getRowCount();
		// int columnCount = tblMain.getColumnCount();
		//
		// int k = 1;
		// for (int i = 0; i < rowCount; i++) {
		// for (int j = 1; j < columnCount; j++) {
		// ICell cell = tblMain.getCell(i, j);
		// BigDecimal value = FDCNumberHelper.toBigDecimal(cell.getValue());
		//		
		// String title = tblMain.getCell(i, 0).getValue() + "";
		// result.addValue(0, title, value.toString());
		//		
		// k++;
		// }
		// }
		//
		// return result;

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		// defaultcategorydataset.addValue，参数：1纵轴，2子横轴，3横轴

		defaultcategorydataset.addValue(10, "现金流入", "2013.01");
		defaultcategorydataset.addValue(-8, "现金流出", "2013.01");
		defaultcategorydataset.addValue(2, "净现金流量", "2013.01");
		defaultcategorydataset.addValue(2, "累计净现金流量", "2013.01");

		defaultcategorydataset.addValue(20, "现金流入", "2013.02");
		defaultcategorydataset.addValue(-18, "现金流出", "2013.02");
		defaultcategorydataset.addValue(2, "净现金流量", "2013.02");
		defaultcategorydataset.addValue(4, "累计净现金流量", "2013.02");

		defaultcategorydataset.addValue(30, "现金流入", "2013.03");
		defaultcategorydataset.addValue(-28, "现金流出", "2013.03");
		defaultcategorydataset.addValue(2, "净现金流量", "2013.03");
		defaultcategorydataset.addValue(6, "累计净现金流量", "2013.03");

		defaultcategorydataset.addValue(10, "现金流入", "2013.04");
		defaultcategorydataset.addValue(-8, "现金流出", "2013.04");
		defaultcategorydataset.addValue(2, "净现金流量", "2013.04");
		defaultcategorydataset.addValue(2, "累计净现金流量", "2013.04");

		defaultcategorydataset.addValue(20, "现金流入", "2013.05");
		defaultcategorydataset.addValue(-18, "现金流出", "2013.05");
		defaultcategorydataset.addValue(2, "净现金流量", "2013.05");
		defaultcategorydataset.addValue(4, "累计净现金流量", "2013.05");

		// 负值
		defaultcategorydataset.addValue(30, "现金流入", "2013.06");
		defaultcategorydataset.addValue(-38, "现金流出", "2013.06");
		defaultcategorydataset.addValue(-8, "净现金流量", "2013.06");
		defaultcategorydataset.addValue(-4, "累计净现金流量", "2013.06");

		return defaultcategorydataset;

	}

	/**
	 * 返回一个柱状图数据集<br>
	 * 实际应用时，在此方法中取数并构建数据集返回
	 * <p>
	 * 第一个参数为金额<br>
	 * 第二个参数为分组，其中括号外为属于柱分组，括号内为柱内分组<br>
	 * 第三个参数为月度
	 */
	protected Dataset createDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		if (FDCTableHelper.isEmpty(tblMain)) {
			return result;
		}

		int columnCount = tblMain.getColumnCount();
		IRow headRow = tblMain.getHeadRow(0);
		// 现金流入，现金流出
		for (int i = 0; i < 2; i++) {
			String category = tblMain.getCell(i, 0).getValue() + "";

			for (int j = 1; j < columnCount; j++) {
				ICell yearMonthCell = headRow.getCell(j);
				String yearMonthValue = yearMonthCell.getValue() + "";

				ICell cell = tblMain.getCell(i, j);
				BigDecimal value = FDCNumberHelper.toBigDecimal(cell.getValue());
				// 现金流出 以负数形式展示
				if ("现金流出".equals(category)) {
					value = value.negate();
				}

				result.addValue(value, category, yearMonthValue);
			}
		}

		return result;
	}

	/**
	 * 返回一个折线图数据集
	 * 
	 * @return
	 */
	protected Dataset createDataset2() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		if (FDCTableHelper.isEmpty(tblMain)) {
			return result;
		}

		int columnCount = tblMain.getColumnCount();
		IRow headRow = tblMain.getHeadRow(0);
		// 净现金流量，累计净现金流量
		for (int i = 2; i < 4; i++) {
			String category = tblMain.getCell(i, 0).getValue() + "";

			for (int j = 1; j < columnCount; j++) {
				ICell yearMonthCell = headRow.getCell(j);
				String yearMonthValue = yearMonthCell.getValue() + "";

				ICell cell = tblMain.getCell(i, j);
				BigDecimal value = FDCNumberHelper.toBigDecimal(cell.getValue());

				result.addValue(value, category, yearMonthValue);
			}
		}

		return result;
	}

	protected int getChartHeight() {
		return getHeight() - 200;
	}

	/**
	 * 返回图标
	 */
	protected JFreeChart createChart(Dataset dataset) {
		JFreeChart chart = null;

		String title = "资本金现金流量图";// 标题
		String domainAxisLabel = "月份";// 横轴
		String rangeAxisLabel = "资本金现金流量(万元)"; // 纵轴;

		chart = ChartFactory.createStackedBarChart(title, // 标题
				domainAxisLabel, // 横轴
				rangeAxisLabel, // 纵轴
				(CategoryDataset) dataset, // 数据源
				PlotOrientation.VERTICAL, // 图表方向
				true, // 说明面板
				true, // 鼠标停留提示
				false // urls
				);

		// 标题字体
		chart.getTitle().setFont(new Font("黑体", Font.PLAIN, 20));
		// 背景色
		chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
		// 图示项字体
		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));

		// 数据区
		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		// 纵轴，小数类型
		NumberAxis rangeAxis = new NumberAxis("资本金现金流量(万元)");
		// 数据轴的数据标签（可以只显示整数标签，需要将AutoTickUnitSelection设false）
		rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		// 最小值
		rangeAxis.setLowerBound(lowerBound.doubleValue());
		// 最大值
		rangeAxis.setUpperBound(upperBound.doubleValue());
		// 数据区的数据轴
		plot.setRangeAxis(rangeAxis);

		// 最底下说明框
		plot.setFixedLegendItems(createLegendItems());

		// ///////////////////////////////////////////////////////////////////

		// 柱状渲染器
		CategoryItemRenderer renderer = getGroupedStackedBarRenderer(chart);
		plot.setRenderer(0, renderer);

		// 折线渲染器
		CategoryItemRenderer lineAndShapeRenderer = getLineAndShapeRenderer(chart);
		plot.setRenderer(1, lineAndShapeRenderer);

		// ///////////////////////////////////////////////////////////////////

		return chart;
	}

	/**
	 * 描述：柱状渲染器
	 * 
	 * @param chart
	 * @return
	 * @author：skyiter_wang
	 * @date：2013-10-5
	 */
	 

	private CategoryItemRenderer getGroupedStackedBarRenderer(JFreeChart chart) {
		// 设置分组情况，当月开头的都属于一个组，累计开头的属于另一个组
		// 这里决定该数据显示于哪根柱子
		GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
		KeyToGroupMap map = new KeyToGroupMap("G1");
		// G1、G2、G3、G4分别代表4根柱子
		map.mapKeyToGroup("现金流入", "G1");
		map.mapKeyToGroup("现金流出", "G2");
		map.mapKeyToGroup("净现金流量", "G3");
		map.mapKeyToGroup("累计净现金流量", "G4");
		// 将分类自由的映射成若干个组
		renderer.setSeriesToGroupMap(map);
		// 柱内间隔
		renderer.setItemMargin(0.02);
		// 不画外框
		renderer.setDrawBarOutline(false);

		// 各组的颜色，属于渐变
		Paint p1 = new GradientPaint(0.0f, 0.0f, new Color(149, 255, 149), 0.0f, 0.0f, new Color(89, 183, 89));
		// 指定分类图形的颜色
		renderer.setSeriesPaint(0, p1);
		Paint p2 = new GradientPaint(0.0f, 0.0f, new Color(255, 200, 200), 20.0f, 0.0f, new Color(255, 145, 145));
		// 指定分类图形的颜色
		renderer.setSeriesPaint(1, p2);

		// 颜色渐变方向，此处为中心渐变
		renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(
				GradientPaintTransformType.CENTER_HORIZONTAL));

		// 鼠标停留在柱状图内显示提示信息格式
		renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator("({0}, {1}) = {2}万元", NumberFormat
				.getInstance()));
		// 显示数值
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);

		return renderer;

	}

	/**
	 * 描述：折线渲染器
	 * 
	 * @param chart
	 * @return
	 * @author：skyiter_wang
	 * @date：2013-10-5
	 */
	private CategoryItemRenderer getLineAndShapeRenderer(JFreeChart chart) {
		// 数据区
		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		// 创建折线图
		CategoryDataset categorydataset = (CategoryDataset) createDataset2();
		// 0显示是柱状图，1显示折线图
		plot.setDataset(1, categorydataset);
		// 显示折线图，0，0坐标轴都在左侧
		plot.mapDatasetToRangeAxis(0, 0);

		// 设置横轴的label为45度
		CategoryAxis categoryaxis = plot.getDomainAxis();
		categoryaxis.setMaximumCategoryLabelWidthRatio(4f);
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		// 在右边显示折线图的坐标和主题
		// NumberAxis numberaxis = new NumberAxis("净现金流量");
		// numberaxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
		// plot.setRangeAxis(1, numberaxis);

		// 画折线图
		LineAndShapeRenderer lineAndShapeRenderer = new LineAndShapeRenderer();
		// 鼠标停留在柱状图内显示提示信息格式
		lineAndShapeRenderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
		// 显示数值
		lineAndShapeRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineAndShapeRenderer.setBaseItemLabelsVisible(true);
		// 设置折线图显示在柱形图上面
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		// 设置实线(宽度1.5磅)
		BasicStroke realLine = new BasicStroke(1.5f);
		lineAndShapeRenderer.setSeriesStroke(0, realLine);
		// 显示折线的点
		lineAndShapeRenderer.setShapesVisible(true);

		// Paint p3 = new GradientPaint(0.0f, 0.0f, new Color(109, 142, 255), 0.0f, 0.0f, new
		// Color(49, 82, 123));
		Paint p3 = new GradientPaint(0.0f, 0.0f, new Color(49, 82, 123), 0.0f, 0.0f, new Color(49, 82, 123));
		// 指定分类图形的颜色
		lineAndShapeRenderer.setSeriesPaint(0, p3);
		// Paint p4 = new GradientPaint(0.0f, 0.0f, new Color(255, 170, 170), 20.0f, 0.0f, new
		// Color(255, 45, 45));
		Paint p4 = new GradientPaint(0.0f, 0.0f, new Color(255, 45, 45), 20.0f, 0.0f, new Color(255, 45, 45));
		// 指定分类图形的颜色
		lineAndShapeRenderer.setSeriesPaint(1, p4);

		return lineAndShapeRenderer;
	}

	private static LegendItemCollection createLegendItems() {
		LegendItemCollection result = new LegendItemCollection();

		LegendItem item1 = new LegendItem("现金流入", "-", "现金流入", null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(89, 183,
				89));
		LegendItem item2 = new LegendItem("现金流出", "-", "现金流出", null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255, 105,
				105));
		LegendItem item3 = new LegendItem("净现金流量", "-", "净现金流量", null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(49, 82,
				123));
		LegendItem item4 = new LegendItem("累计净现金流量", "-", "累计净现金流量", null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255,
				45, 45));
		result.add(item1);
		result.add(item2);
		result.add(item3);
		result.add(item4);

		return result;
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		if (!isInit) {
			return;
		}

		curName = "";
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo cpinfo = (CurProjectInfo) node.getUserObject();
			curName = cpinfo.getName();
		}

		query_tblMain();
	}

	// 打印
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getTDName(), getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}

	// 打印预览
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionPrintPreview_actionPerformed(e);
	}

	protected String getTDPath() {
		// TODO Auto-generated method stub
		return super.getTDPath();
	}

	/**
	 * 返回一张图片(重写父类)
	 * <p>
	 * 导出图片思路：<br>
	 * 1、将图表面板导出为img1<br>
	 * 2、将表格设置到合适的高宽，清除选择<br>
	 * 3、将表格导出为img2<br>
	 * 4、新建img，将img1等比缩放成宽度与img2一样，并画在img的上方<br>
	 * 5、将img2画在img的下方<br>
	 * 6、将表格高宽设回原样，并选择之前所选择的行<br>
	 * 7、返回img
	 */
	protected BufferedImage getUIIMG() {
		// 1、将图表面板导出为img1
		BufferedImage img1 = getChartIMG();
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		// BufferedImage img1 = new BufferedImage(w1, h1,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = img1.getGraphics();
		// pnlChart.paintAll(g);
		// g.dispose();

		// 2、将表格设置到合适的高宽，清除选择
		// 保存现在高宽选择
		// int curDivLeft = sptLeft.getDividerLocation();
		// int curDivTop = sptTop.getDividerLocation();

		KDTSelectBlock curSelect = tblMain.getSelectManager().get();

		// 计算表格最合适宽度(2个像素的边界+序号列宽+所有列宽和)
		int fitWidth = 2 + tblMain.getIndexColumn().getWidth() + tblMain.getColumns().getWidth();
		// sptLeft
		// .setDividerLocation(curDivLeft
		// + (tblMain.getWidth() - fitWidth));
		// 计算表格最合适高度(2个像素的边界+表头高+所有行高和)
		int fitHeight = 2 + tblMain.getHead().getHeight() + tblMain.getBody().getHeight();
		// sptTop
		// .setDividerLocation(curDivTop
		// + (tblMain.getHeight() - fitHeight));
		// 清除选择
		tblMain.getSelectManager().remove();
		// 此句不可去掉，用于在生成图片前设置表格合适大小
		tblMain.setSize(fitWidth, fitHeight);

		// 3、将表格导出为img2
		int w2 = tblMain.getWidth();
		int h2 = tblMain.getHeight();
		BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		tblMain.paintAll(g);
		g.dispose();

		// 4、新建img，将img1等比缩放成宽度与img2一样，并画在img的上方
		float scale = (float) w2 / w1;
		w1 = w2;
		h1 = (int) (h1 * scale);
		BufferedImage img = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
		Image scaleImage = img1.getScaledInstance(w1, h1, Image.SCALE_AREA_AVERAGING);
		g = img.createGraphics();
		g.drawImage(scaleImage, 0, 0, w1, h1, null);

		// 5、将img2画在img的下方
		g.drawImage(img2, 0, h1, w2, h2, null);
		g.dispose();

		// 6、将表格高宽设回原样，并选择之前所选择的行
		// sptLeft.setDividerLocation(curDivLeft);
		// sptTop.setDividerLocation(curDivTop);
		if (curSelect != null) {
			tblMain.getSelectManager().select(curSelect);
		}

		// 7、返回img
		return img;
	}

	protected BufferedImage getChartIMG() {
		return super.getChartIMG();
	}

	public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception {
		tblMain.checkParsed();
		if (tblMain.getRowCount() <= 0) {
			FDCMsgBox.showInfo("没有相应的图表，无需导出");
			abort();
		}
		super.actionExportIMG_actionPerformed(e);
	}

	protected boolean isMainSchedule() {
		return true;
	}

	public boolean destroyWindow() {
		try {
			IREAutoRemember autoRemember = REAutoRememberFactory.getRemoteInstance();
			// 当前用户
			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
			// 当前组是织
			OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			CurProjectInfo curProject = null;
			if (node != null && node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
				curProject = (CurProjectInfo) node.getUserObject();
			}
			// 工程项目
			Object[] objs = new Object[] { user, orgUnit, curProject };
			if (exits(objs)) {
				String userID = user.getId().toString();
				String orgUnitID = orgUnit.getId().toString();
				String curProjectID = curProject.getId().toString();
				if (isMainSchedule()) {// mainscheudlerateachivement
					// 主项所属的工程项目
					autoRemember.save(userID, orgUnitID, "DynaticFundPlanUI", curProjectID);
				} else {
					// 如果是专项 那么首先记忆专项所属的工程项目ID， 第二：记忆专项的ID
					// autoRemember.save(userID, orgUnitID,
					// "SpecialScheudleRateAchivementProject", curProjectID);
					// autoRemember.save(userID, orgUnitID,
					// "SpecialScheudleRateAchivementProjectSpecial",
					// editData.getProjectSpecial().getId()
					// .toString());
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

		return super.destroyWindow();
	}

	/**
	 * 描述：数组中的值是否存在
	 * 
	 * @param objs
	 * @return
	 * @author：skyiter_wang
	 * @date：2013-10-8
	 */
	 

	private boolean exits(Object[] objs) {
		for (int i = 0; i < objs.length; i++) {
			if (FDCHelper.isEmpty(objs[i])) {
				return false;
			}
		}
		return true;
	}

	public void actionQueryData_actionPerformed(ActionEvent e) throws Exception {
		super.actionQueryData_actionPerformed(e);

		query_tblMain();
	}
}