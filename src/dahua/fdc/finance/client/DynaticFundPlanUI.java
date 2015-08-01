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
 * ��������̬�ʽ�ƻ�
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2013-9-27
 * @see
 * @since JDK1.4
 */
public class DynaticFundPlanUI extends AbstractDynaticFundPlanUI {

	private static final Logger logger = CoreUIObject.getLogger(DynaticFundPlanUI.class);

	// ��ǰ��
	public String curName;
	// �ƻ���������
	private Date startDate;
	// �ƻ��깤����
	private Date endDate;

	// �Ƿ��Ѿ���ʼ��
	protected boolean isInit;
	// ����
	protected RptParams params;

	// ͼ��ֵ����
	private BigDecimal lowerBound = FDCConstants._ONE;
	// ͼ��ֵ����
	private BigDecimal upperBound = FDCConstants.ONE;

	/**
	 * ���������캯��
	 * 
	 * @throws Exception
	 * @author��skyiter_wang
	 * @date��2013-10-5
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
	 * ���������Զ��ʵ��
	 * 
	 * @return
	 * @throws BOSException
	 * @author��skyiter_wang
	 * @date��2013-10-8
	 */
	 

	private IFdcRptBaseFacade getRemoteInstance() throws BOSException {
		return DynaticFundPlanRptFacadeFactory.getRemoteInstance();
	}

	/**
	 * ������ȡ��ѡ��Ĺ�����Ŀ���������
	 * 
	 * @param isCheck
	 * @return
	 * @author��skyiter_wang
	 * @date��2013-9-27
	 */
	protected CurProjectInfo getSelectedProject(boolean isCheck) {
		CurProjectInfo currentProject = null;

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
			currentProject = (CurProjectInfo) node.getUserObject();
		}

		if (isCheck && null == currentProject) {
			MsgBox.showWarning(this, "��ѡ�񹤳���Ŀ");
			SysUtil.abort();
		}

		return currentProject;
	}

	public void onLoad() throws Exception {
		super.onLoad();

		// ������һ�εļ���
		// initPreTime();

		isInit = true;
	}

	protected void initPreTime() {
		// ��ǰ�û�
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		// ��ǰ����֯
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

		// ���ز˵��� ���ߺͷ���
		menuTool.setVisible(false);
		MenuService.setVisible(false);
		menuItemPrint.setVisible(true);
		menuItemPrintPre.setVisible(true);
	}

	public void initParamToUI(RptParams params) {
		// ȡ��ѡ��Ĺ�����Ŀ���������
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
			MsgBox.showWarning(this, "��ʼ�ڼ䲻�ܴ��ڽ����ڼ�");
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
	 * ��ѯtblMain
	 */
	private void query_tblMain() {
		params = (RptParams) FdcObjectUtil.defaultIfNull(params, new RptParams());

		// ��ձ��
		tblMain.removeRows();

		// ��ѯ�������õ�����
		initParamToUI(params);

		// ���ò�ѯ���
		params.setObject(FdcRptConstant.KDTABLE, "tblMain");

		// ��ѯ����
		try {
			RptParams rps = getRemoteInstance().query(params);
			// ���ؽ��
			Map result = (Map) rps.getObject(FdcRptConstant.RPT_QUERYRESULT);
			// ���������뵽���
			fillTable_tblMain(result);
		} catch (Exception e) {
			logger.error(e);
			this.handUIExceptionAndAbort(e);
		} finally {
			// ���»���ͼ��
			initChart();
		}
	}

	// �������뵽���_tblMain
	protected int fillTable_tblMain(Map result) {
		// ���������
		Map parseMap = parseResult_tblMain(result);

		// ��ʼ����̬���
		initDynaTable_tblMain(parseMap);

		// /////////////////////////////////////////////////////////////////////////////////////

		if (FdcMapUtil.isNotEmpty(parseMap)) {
			Set keySet = (Set) parseMap.get("keySet");
			int columnIndex = 1;

			Map inComeMap = (Map) parseMap.get("inComeMap");
			Map payOutMap = (Map) parseMap.get("payOutMap");

			// �ֽ�����
			IRow inComeRow = this.tblMain.addRow();
			// �ֽ�����
			IRow payOutRow = this.tblMain.addRow();
			// ���ֽ�����
			IRow netRow = this.tblMain.addRow();
			// �ۼƾ��ֽ�����
			IRow totalNetRow = this.tblMain.addRow();

			inComeRow.getCell(0).setValue("�ֽ�����");
			payOutRow.getCell(0).setValue("�ֽ�����");
			netRow.getCell(0).setValue("���ֽ�����");
			totalNetRow.getCell(0).setValue("�ۼƾ��ֽ�����");

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
				// �ֽ����� �Ը�����ʽչʾ
				amountList.add(payOutAmount.negate());
				amountList.add(netAmount);
				amountList.add(totalNetAmount);

				columnIndex++;
			}

			// /////////////////////////////////////////////////////////////////////////////////////

			if (FdcCollectionUtil.isNotEmpty(amountList)) {
				// ����
				Collections.sort(amountList);
				BigDecimal minAmount = (BigDecimal) amountList.get(0);
				BigDecimal maxAmount = (BigDecimal) amountList.get(amountList.size() - 1);
				minAmount = FDCNumberHelper.isNullZero(minAmount) ? FDCConstants._ONE : minAmount;
				maxAmount = FDCNumberHelper.isNullZero(maxAmount) ? FDCConstants.ONE : maxAmount;
				
				// ���������ܳ���5��
				BigDecimal tempMinAmount = FDCNumberHelper.divide(minAmount.abs(), new BigDecimal("5"));
				BigDecimal tempMaxAmount = FDCNumberHelper.divide(maxAmount.abs(), new BigDecimal("5"));
				if (FDCNumberHelper.compareTo(tempMinAmount, maxAmount.abs()) > 0) {
					maxAmount = tempMinAmount;
					minAmount = minAmount.abs().negate();
				} else if (FDCNumberHelper.compareTo(tempMaxAmount, minAmount.abs()) > 0) {
					minAmount = tempMaxAmount.negate();
					maxAmount = maxAmount.abs();
				}

				// �����޲��ܽ����߽磬Ӧ��Ԥ��һ���϶
				minAmount = FDCNumberHelper.multiply(minAmount, new BigDecimal("1.1"));
				maxAmount = FDCNumberHelper.multiply(maxAmount, new BigDecimal("1.1"));

				// // ͼ��ֵ����
				// lowerBound = FDCNumberHelper.compareTo(lowerBound, minAmount) < 0 ? upperBound :
				// minAmount;
				// // ͼ��ֵ����
				// upperBound = FDCNumberHelper.compareTo(upperBound, maxAmount) > 0 ? upperBound :
				// maxAmount;
				// ͼ��ֵ����
				lowerBound = minAmount;
				// ͼ��ֵ����
				upperBound = maxAmount;
			} else {
				// ͼ��ֵ����
				lowerBound = FDCConstants._ONE;
				// ͼ��ֵ����
				upperBound = FDCConstants.ONE;
			}
		}

		// /////////////////////////////////////////////////////////////////////////////////////

		// �����֮��
		afterFillTable_tblMain(parseMap);

		return tblMain.getRowCount();
	}

	/**
	 * ���������������
	 * 
	 * @param result
	 * @return
	 * @author��skyiter_wang
	 * @date��2013-9-28
	 */
	protected Map parseResult_tblMain(Map result) {
		// ʹ��TreeMap����
		Map parseMap = new TreeMap();

		RptRowSet inComeResult = (RptRowSet) result.get("result.inCome");
		RptRowSet payOutResult = (RptRowSet) result.get("result.payOut");

		// //////////////////////////////////////////////////////////////////////

		inComeResult.reset();
		payOutResult.reset();

		Map inComeMap = new TreeMap();
		while (inComeResult.next()) {
			// Ŀ�굥�ݣ����ز���¥T_SHE_SellPlan����ݶ�Ӧ���ֶ���VARCHAR
			Object planYear = inComeResult.getObject("planYear");
			Object year = planYear;

			// ʮ������
			for (int i = 1; i <= 12; i++) {
				// int year = planYear.intValue();
				int month = i;
				String key = "amount_" + year + "_" + (month < 10 ? "0" + month : month);
				Object value = inComeResult.getBigDecimal("month" + i + "Amount");
				// ��λ����Ԫ(�ֽ������Ӧ�ı��д�������Ѿ�����Ԫ���˴�����Ҫ�ٳ�)
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
			// ��λ����Ԫ
			value = FDCNumberHelper.divide(value, FDCConstants.TEN_THOUSAND);

			payOutMap.put(key, value);
		}
		parseMap.put("payOutMap", payOutMap);

		// //////////////////////////////////////////////////////////////////////

		Set inComeKeySet = inComeMap.keySet();
		Set payOutKeySet = payOutMap.keySet();

		// �ֽ������Ӧ�ı�ṹ����в�����û�д��·ݣ����²�ѯSQL����д�������SQL����ֻ���ꣻ�·ݹ����ڿͻ��˴���
		List inComeKeyList = new ArrayList(inComeKeySet);
		List subInComeKeyList = new ArrayList();
		if (FdcCollectionUtil.isNotEmpty(inComeKeyList)) {
			String startYear = params.getString("startYear");
			String startMonth = params.getString("startMonth");
			int startMonthInt = Integer.parseInt(startMonth);
			String startKey = "amount_" + startYear + "_" + (startMonthInt < 10 ? "0" + startMonthInt : startMonthInt);
			int startIndex = inComeKeyList.indexOf(startKey);
			// ��������
			startIndex = (startIndex < 0) ? 0 : startIndex;

			String endYear = params.getString("endYear");
			String endMonth = params.getString("endMonth");
			int endMonthInt = Integer.parseInt(endMonth);
			String endKey = "amount_" + endYear + "_" + (endMonthInt < 10 ? "0" + endMonthInt : endMonthInt);
			int endIndex = inComeKeyList.indexOf(endKey);
			// ��������
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
	 * ��������ʼ����̬���
	 * <p>
	 * 1��key��ʽΪamount_2012_8<br>
	 * 2�������ʽΪ2012.8<br>
	 * 
	 * @author��skyiter_wang
	 * @date��2013-9-27
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
			// key��ʽΪamount_2012_8
			column.setKey(columnName);
			// �����ʽΪ2012.8
			headRow.getCell(columnIndex).setValue(titleName);

			columnIndex++;
		}
	}

	/**
	 * �����֮��_tblMain
	 * 
	 * @param rowRsMap
	 */
	private void afterFillTable_tblMain(Map rowRsMap) {
	}

	/**
	 * ��ʼ��ͼ�����
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
	 * ��ʼ������� add by libing
	 */
	protected void initTree() throws Exception {
		super.initTree();

		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
		// Ĭ��չ������ĩ��
		treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	/**
	 * ��ʼ����ͷ
	 */
	protected void initHead() throws Exception {
		// TODO Auto-generated method stub
		super.initHead();

		if (isInit) {
			return;
		}

		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		// ǰ��20��
		for (int i = currentYear - 20, maxYear = currentYear + 20; i <= maxYear; i++) {
			cbYear.addItem(new Integer(i));
			cbEndYear.addItem(new Integer(i));
		}
		// Ĭ��ѡ��ǰ��
		for (int i = 0; i < cbYear.getItemCount(); i++) {
			int tempYear = Integer.parseInt(cbYear.getItemAt(i).toString());
			if (tempYear == currentYear) {
				cbYear.setSelectedIndex(i);
				cbEndYear.setSelectedIndex(i);
			}
		}

		// 1�µ�12��
		for (int i = 1, maxMonth = 12; i <= maxMonth; i++) {
			cbStartMonth.addItem(new Integer(i));
			cbEndMonth.addItem(new Integer(i));
		}
		// Ĭ��ѡ���1�£���12��
		cbStartMonth.setSelectedIndex(0);
		cbEndMonth.setSelectedIndex(11);
	}

	/**
	 * ��ʼ�����
	 */
	protected void initTable() {
		super.initTable();

		// ��ͷ��һ�б���Ϊ��
		tblMain.getHead().getRow(0).getCell(0).setValue("");
		// �������
		tblMain.getStyleAttributes().setLocked(true);
	}

	/**
	 * ����һ����״ͼ���ݼ�<br>
	 * ʵ��Ӧ��ʱ���ڴ˷�����ȡ�����������ݼ�����
	 * <p>
	 * ��һ������Ϊ��ɰٷֱ�<br>
	 * �ڶ�������Ϊ���飬����������Ϊ���������飬������Ϊ���ڷ���<br>
	 * ����������Ϊ�¶�
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
		// defaultcategorydataset.addValue��������1���ᣬ2�Ӻ��ᣬ3����

		defaultcategorydataset.addValue(10, "�ֽ�����", "2013.01");
		defaultcategorydataset.addValue(-8, "�ֽ�����", "2013.01");
		defaultcategorydataset.addValue(2, "���ֽ�����", "2013.01");
		defaultcategorydataset.addValue(2, "�ۼƾ��ֽ�����", "2013.01");

		defaultcategorydataset.addValue(20, "�ֽ�����", "2013.02");
		defaultcategorydataset.addValue(-18, "�ֽ�����", "2013.02");
		defaultcategorydataset.addValue(2, "���ֽ�����", "2013.02");
		defaultcategorydataset.addValue(4, "�ۼƾ��ֽ�����", "2013.02");

		defaultcategorydataset.addValue(30, "�ֽ�����", "2013.03");
		defaultcategorydataset.addValue(-28, "�ֽ�����", "2013.03");
		defaultcategorydataset.addValue(2, "���ֽ�����", "2013.03");
		defaultcategorydataset.addValue(6, "�ۼƾ��ֽ�����", "2013.03");

		defaultcategorydataset.addValue(10, "�ֽ�����", "2013.04");
		defaultcategorydataset.addValue(-8, "�ֽ�����", "2013.04");
		defaultcategorydataset.addValue(2, "���ֽ�����", "2013.04");
		defaultcategorydataset.addValue(2, "�ۼƾ��ֽ�����", "2013.04");

		defaultcategorydataset.addValue(20, "�ֽ�����", "2013.05");
		defaultcategorydataset.addValue(-18, "�ֽ�����", "2013.05");
		defaultcategorydataset.addValue(2, "���ֽ�����", "2013.05");
		defaultcategorydataset.addValue(4, "�ۼƾ��ֽ�����", "2013.05");

		// ��ֵ
		defaultcategorydataset.addValue(30, "�ֽ�����", "2013.06");
		defaultcategorydataset.addValue(-38, "�ֽ�����", "2013.06");
		defaultcategorydataset.addValue(-8, "���ֽ�����", "2013.06");
		defaultcategorydataset.addValue(-4, "�ۼƾ��ֽ�����", "2013.06");

		return defaultcategorydataset;

	}

	/**
	 * ����һ����״ͼ���ݼ�<br>
	 * ʵ��Ӧ��ʱ���ڴ˷�����ȡ�����������ݼ�����
	 * <p>
	 * ��һ������Ϊ���<br>
	 * �ڶ�������Ϊ���飬����������Ϊ���������飬������Ϊ���ڷ���<br>
	 * ����������Ϊ�¶�
	 */
	protected Dataset createDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		if (FDCTableHelper.isEmpty(tblMain)) {
			return result;
		}

		int columnCount = tblMain.getColumnCount();
		IRow headRow = tblMain.getHeadRow(0);
		// �ֽ����룬�ֽ�����
		for (int i = 0; i < 2; i++) {
			String category = tblMain.getCell(i, 0).getValue() + "";

			for (int j = 1; j < columnCount; j++) {
				ICell yearMonthCell = headRow.getCell(j);
				String yearMonthValue = yearMonthCell.getValue() + "";

				ICell cell = tblMain.getCell(i, j);
				BigDecimal value = FDCNumberHelper.toBigDecimal(cell.getValue());
				// �ֽ����� �Ը�����ʽչʾ
				if ("�ֽ�����".equals(category)) {
					value = value.negate();
				}

				result.addValue(value, category, yearMonthValue);
			}
		}

		return result;
	}

	/**
	 * ����һ������ͼ���ݼ�
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
		// ���ֽ��������ۼƾ��ֽ�����
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
	 * ����ͼ��
	 */
	protected JFreeChart createChart(Dataset dataset) {
		JFreeChart chart = null;

		String title = "�ʱ����ֽ�����ͼ";// ����
		String domainAxisLabel = "�·�";// ����
		String rangeAxisLabel = "�ʱ����ֽ�����(��Ԫ)"; // ����;

		chart = ChartFactory.createStackedBarChart(title, // ����
				domainAxisLabel, // ����
				rangeAxisLabel, // ����
				(CategoryDataset) dataset, // ����Դ
				PlotOrientation.VERTICAL, // ͼ����
				true, // ˵�����
				true, // ���ͣ����ʾ
				false // urls
				);

		// ��������
		chart.getTitle().setFont(new Font("����", Font.PLAIN, 20));
		// ����ɫ
		chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
		// ͼʾ������
		chart.getLegend().setItemFont(new Font("����", Font.PLAIN, 12));

		// ������
		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		// ���ᣬС������
		NumberAxis rangeAxis = new NumberAxis("�ʱ����ֽ�����(��Ԫ)");
		// ����������ݱ�ǩ������ֻ��ʾ������ǩ����Ҫ��AutoTickUnitSelection��false��
		rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		// ��Сֵ
		rangeAxis.setLowerBound(lowerBound.doubleValue());
		// ���ֵ
		rangeAxis.setUpperBound(upperBound.doubleValue());
		// ��������������
		plot.setRangeAxis(rangeAxis);

		// �����˵����
		plot.setFixedLegendItems(createLegendItems());

		// ///////////////////////////////////////////////////////////////////

		// ��״��Ⱦ��
		CategoryItemRenderer renderer = getGroupedStackedBarRenderer(chart);
		plot.setRenderer(0, renderer);

		// ������Ⱦ��
		CategoryItemRenderer lineAndShapeRenderer = getLineAndShapeRenderer(chart);
		plot.setRenderer(1, lineAndShapeRenderer);

		// ///////////////////////////////////////////////////////////////////

		return chart;
	}

	/**
	 * ��������״��Ⱦ��
	 * 
	 * @param chart
	 * @return
	 * @author��skyiter_wang
	 * @date��2013-10-5
	 */
	 

	private CategoryItemRenderer getGroupedStackedBarRenderer(JFreeChart chart) {
		// ���÷�����������¿�ͷ�Ķ�����һ���飬�ۼƿ�ͷ��������һ����
		// ���������������ʾ���ĸ�����
		GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
		KeyToGroupMap map = new KeyToGroupMap("G1");
		// G1��G2��G3��G4�ֱ����4������
		map.mapKeyToGroup("�ֽ�����", "G1");
		map.mapKeyToGroup("�ֽ�����", "G2");
		map.mapKeyToGroup("���ֽ�����", "G3");
		map.mapKeyToGroup("�ۼƾ��ֽ�����", "G4");
		// ���������ɵ�ӳ������ɸ���
		renderer.setSeriesToGroupMap(map);
		// ���ڼ��
		renderer.setItemMargin(0.02);
		// �������
		renderer.setDrawBarOutline(false);

		// �������ɫ�����ڽ���
		Paint p1 = new GradientPaint(0.0f, 0.0f, new Color(149, 255, 149), 0.0f, 0.0f, new Color(89, 183, 89));
		// ָ������ͼ�ε���ɫ
		renderer.setSeriesPaint(0, p1);
		Paint p2 = new GradientPaint(0.0f, 0.0f, new Color(255, 200, 200), 20.0f, 0.0f, new Color(255, 145, 145));
		// ָ������ͼ�ε���ɫ
		renderer.setSeriesPaint(1, p2);

		// ��ɫ���䷽�򣬴˴�Ϊ���Ľ���
		renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(
				GradientPaintTransformType.CENTER_HORIZONTAL));

		// ���ͣ������״ͼ����ʾ��ʾ��Ϣ��ʽ
		renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator("({0}, {1}) = {2}��Ԫ", NumberFormat
				.getInstance()));
		// ��ʾ��ֵ
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);

		return renderer;

	}

	/**
	 * ������������Ⱦ��
	 * 
	 * @param chart
	 * @return
	 * @author��skyiter_wang
	 * @date��2013-10-5
	 */
	private CategoryItemRenderer getLineAndShapeRenderer(JFreeChart chart) {
		// ������
		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		// ��������ͼ
		CategoryDataset categorydataset = (CategoryDataset) createDataset2();
		// 0��ʾ����״ͼ��1��ʾ����ͼ
		plot.setDataset(1, categorydataset);
		// ��ʾ����ͼ��0��0�����ᶼ�����
		plot.mapDatasetToRangeAxis(0, 0);

		// ���ú����labelΪ45��
		CategoryAxis categoryaxis = plot.getDomainAxis();
		categoryaxis.setMaximumCategoryLabelWidthRatio(4f);
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		// ���ұ���ʾ����ͼ�����������
		// NumberAxis numberaxis = new NumberAxis("���ֽ�����");
		// numberaxis.setLabelFont(new Font("����", Font.PLAIN, 12));
		// plot.setRangeAxis(1, numberaxis);

		// ������ͼ
		LineAndShapeRenderer lineAndShapeRenderer = new LineAndShapeRenderer();
		// ���ͣ������״ͼ����ʾ��ʾ��Ϣ��ʽ
		lineAndShapeRenderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
		// ��ʾ��ֵ
		lineAndShapeRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineAndShapeRenderer.setBaseItemLabelsVisible(true);
		// ��������ͼ��ʾ������ͼ����
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		// ����ʵ��(���1.5��)
		BasicStroke realLine = new BasicStroke(1.5f);
		lineAndShapeRenderer.setSeriesStroke(0, realLine);
		// ��ʾ���ߵĵ�
		lineAndShapeRenderer.setShapesVisible(true);

		// Paint p3 = new GradientPaint(0.0f, 0.0f, new Color(109, 142, 255), 0.0f, 0.0f, new
		// Color(49, 82, 123));
		Paint p3 = new GradientPaint(0.0f, 0.0f, new Color(49, 82, 123), 0.0f, 0.0f, new Color(49, 82, 123));
		// ָ������ͼ�ε���ɫ
		lineAndShapeRenderer.setSeriesPaint(0, p3);
		// Paint p4 = new GradientPaint(0.0f, 0.0f, new Color(255, 170, 170), 20.0f, 0.0f, new
		// Color(255, 45, 45));
		Paint p4 = new GradientPaint(0.0f, 0.0f, new Color(255, 45, 45), 20.0f, 0.0f, new Color(255, 45, 45));
		// ָ������ͼ�ε���ɫ
		lineAndShapeRenderer.setSeriesPaint(1, p4);

		return lineAndShapeRenderer;
	}

	private static LegendItemCollection createLegendItems() {
		LegendItemCollection result = new LegendItemCollection();

		LegendItem item1 = new LegendItem("�ֽ�����", "-", "�ֽ�����", null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(89, 183,
				89));
		LegendItem item2 = new LegendItem("�ֽ�����", "-", "�ֽ�����", null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255, 105,
				105));
		LegendItem item3 = new LegendItem("���ֽ�����", "-", "���ֽ�����", null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(49, 82,
				123));
		LegendItem item4 = new LegendItem("�ۼƾ��ֽ�����", "-", "�ۼƾ��ֽ�����", null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255,
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

	// ��ӡ
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getTDName(), getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}

	// ��ӡԤ��
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionPrintPreview_actionPerformed(e);
	}

	protected String getTDPath() {
		// TODO Auto-generated method stub
		return super.getTDPath();
	}

	/**
	 * ����һ��ͼƬ(��д����)
	 * <p>
	 * ����ͼƬ˼·��<br>
	 * 1����ͼ����嵼��Ϊimg1<br>
	 * 2����������õ����ʵĸ߿����ѡ��<br>
	 * 3������񵼳�Ϊimg2<br>
	 * 4���½�img����img1�ȱ����ųɿ����img2һ����������img���Ϸ�<br>
	 * 5����img2����img���·�<br>
	 * 6�������߿����ԭ������ѡ��֮ǰ��ѡ�����<br>
	 * 7������img
	 */
	protected BufferedImage getUIIMG() {
		// 1����ͼ����嵼��Ϊimg1
		BufferedImage img1 = getChartIMG();
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		// BufferedImage img1 = new BufferedImage(w1, h1,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = img1.getGraphics();
		// pnlChart.paintAll(g);
		// g.dispose();

		// 2����������õ����ʵĸ߿����ѡ��
		// �������ڸ߿�ѡ��
		// int curDivLeft = sptLeft.getDividerLocation();
		// int curDivTop = sptTop.getDividerLocation();

		KDTSelectBlock curSelect = tblMain.getSelectManager().get();

		// ����������ʿ��(2�����صı߽�+����п�+�����п��)
		int fitWidth = 2 + tblMain.getIndexColumn().getWidth() + tblMain.getColumns().getWidth();
		// sptLeft
		// .setDividerLocation(curDivLeft
		// + (tblMain.getWidth() - fitWidth));
		// ����������ʸ߶�(2�����صı߽�+��ͷ��+�����иߺ�)
		int fitHeight = 2 + tblMain.getHead().getHeight() + tblMain.getBody().getHeight();
		// sptTop
		// .setDividerLocation(curDivTop
		// + (tblMain.getHeight() - fitHeight));
		// ���ѡ��
		tblMain.getSelectManager().remove();
		// �˾䲻��ȥ��������������ͼƬǰ���ñ����ʴ�С
		tblMain.setSize(fitWidth, fitHeight);

		// 3������񵼳�Ϊimg2
		int w2 = tblMain.getWidth();
		int h2 = tblMain.getHeight();
		BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		tblMain.paintAll(g);
		g.dispose();

		// 4���½�img����img1�ȱ����ųɿ����img2һ����������img���Ϸ�
		float scale = (float) w2 / w1;
		w1 = w2;
		h1 = (int) (h1 * scale);
		BufferedImage img = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
		Image scaleImage = img1.getScaledInstance(w1, h1, Image.SCALE_AREA_AVERAGING);
		g = img.createGraphics();
		g.drawImage(scaleImage, 0, 0, w1, h1, null);

		// 5����img2����img���·�
		g.drawImage(img2, 0, h1, w2, h2, null);
		g.dispose();

		// 6�������߿����ԭ������ѡ��֮ǰ��ѡ�����
		// sptLeft.setDividerLocation(curDivLeft);
		// sptTop.setDividerLocation(curDivTop);
		if (curSelect != null) {
			tblMain.getSelectManager().select(curSelect);
		}

		// 7������img
		return img;
	}

	protected BufferedImage getChartIMG() {
		return super.getChartIMG();
	}

	public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception {
		tblMain.checkParsed();
		if (tblMain.getRowCount() <= 0) {
			FDCMsgBox.showInfo("û����Ӧ��ͼ�����赼��");
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
			// ��ǰ�û�
			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
			// ��ǰ����֯
			OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			CurProjectInfo curProject = null;
			if (node != null && node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
				curProject = (CurProjectInfo) node.getUserObject();
			}
			// ������Ŀ
			Object[] objs = new Object[] { user, orgUnit, curProject };
			if (exits(objs)) {
				String userID = user.getId().toString();
				String orgUnitID = orgUnit.getId().toString();
				String curProjectID = curProject.getId().toString();
				if (isMainSchedule()) {// mainscheudlerateachivement
					// ���������Ĺ�����Ŀ
					autoRemember.save(userID, orgUnitID, "DynaticFundPlanUI", curProjectID);
				} else {
					// �����ר�� ��ô���ȼ���ר�������Ĺ�����ĿID�� �ڶ�������ר���ID
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
	 * �����������е�ֵ�Ƿ����
	 * 
	 * @param objs
	 * @return
	 * @author��skyiter_wang
	 * @date��2013-10-8
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