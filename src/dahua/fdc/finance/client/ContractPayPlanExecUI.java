/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettleEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettleEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @描述 合同付款计划执行表
 * @author duhongming
 * 
 */
public class ContractPayPlanExecUI extends AbstractContractPayPlanExecUI {
	private static final Logger logger = CoreUIObject.getLogger(ContractPayPlanExecUI.class);

	/** 用于加入行计数 **/
	private int ROW_COUNT = 0;

	/** 时期格式定义 **/
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

	/** 用于放小计行 **/
	Map tiny_Total = new HashMap();
	/** 用于放部门合计行 **/
	Map dep_Total = new HashMap();
	/** 用于放合计行 **/
	Map total_Total = new HashMap();

	/**
	 * output class constructor
	 */
	public ContractPayPlanExecUI() throws Exception {
		super();
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return null;
	}

	public void onLoad() throws Exception {
		//		checkFunctionPermission();
		buildProjectTree();
		init(); 
	}

	/**
	 * @description 权限检查
	 * @author duhongming
	 * @createDate 2012-1-13
	 * @throws BOSException
	 * @throws EASBizException
	 * @version EAS7.0
	 * @see
	 */
	private void checkFunctionPermission() throws BOSException, EASBizException {
		IObjectPK orgPK = getOrgPK(this.actionView);
		PermissionFactory.getRemoteInstance().checkFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()), orgPK,
				"fin_contractPlanExeTab_view");
	}

	protected void initWorkButton() {
		super.initWorkButton();
		btnPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		btnPrintPreview.setIcon(EASResource.getIcon("imgTbtn_preview"));
	}

	/**
	 * 
	 * @description 构建行政组织树
	 * @author duhongming
	 * @createDate 2011-12-13
	 * @throws Exception
	 * @version EAS7.0
	 * @see com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI#buildProjectTree()
	 */
	public void buildProjectTree() throws Exception {
		AdminOrgUnitInfo currentAdminUnit = SysContext.getSysContext().getCurrentAdminUnit();
		logger.warn("CurrentAdminUnit:" + currentAdminUnit.getLongNumber());
		FullOrgUnitInfo adminOUInfo = null;
		try {
			adminOUInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(currentAdminUnit.getId()));
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		if (adminOUInfo == null) {
			adminOUInfo = currentAdminUnit.castToFullOrgUnitInfo();
		}
		logger.warn("CastFullOrgUnit:" + adminOUInfo.getLongNumber());
		if (adminOUInfo != null) {
			try {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("longNumber", adminOUInfo.getLongNumber() + "%", CompareType.LIKE));
				filter.getFilterItems().add(new FilterItemInfo("isFreeze", new Integer(0)));

				CtrlUnitInfo obj = SysContext.getSysContext().getCurrentCtrlUnit();
				logger.warn("CtrlUnit:" + obj.getLongNumber());
				String cuId = obj.getId().toString();
				if (cuId != null) {
					filter.getFilterItems().add(new FilterItemInfo("CU.id", cuId));
				}
				// 用户组织范围内的组织才能选择
				try {
					Set authorizedOrgs = new HashSet();
					Map orgs = (Map) ActionCache.get("FDCBillEditUIHandler.authorizedOrgs");
					if (orgs == null) {
						orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.Admin,
								null, null, null);
					}
					if (orgs != null) {
						Set orgSet = orgs.keySet();
						Iterator it = orgSet.iterator();
						while (it.hasNext()) {
							Object item = it.next();
								authorizedOrgs.add(item);
						}
					}
					FilterInfo filterID = new FilterInfo();
					filterID.getFilterItems().add(new FilterItemInfo("id", authorizedOrgs, CompareType.INCLUDE));
					filter.mergeFilter(filterID, "and");
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.warn("Filter:" + filter);
				ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(new DefaultLNTreeNodeCtrl(FullOrgUnitFactory.getRemoteInstance()), 10, 10, filter);
				treeBuilder.buildTree(treeProject);
				treeProject.setSelectionNode((DefaultKingdeeTreeNode) treeProject.getModel().getRoot());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @description 查询事件
	 * @author duhongming
	 * @createDate 2011-12-13
	 * @param e
	 * @throws Exception
	 * @version EAS7.0
	 * @see com.kingdee.eas.fdc.finance.client.AbstractContractPayPlanExecUI#actionSearch_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSearch_actionPerformed(ActionEvent e) throws Exception {
		if (((DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent()).getUserObject() == null) {
			clearTable();
			return;
		}
		AdminOrgUnitInfo lastSelectAdminOrgUnit = getLastSelectAdminOrgUnit();
		if (lastSelectAdminOrgUnit != null && "00000000-0000-0000-0000-000000000000CCE7AED4".equals(lastSelectAdminOrgUnit.getId().toString())) {
			clearTable();
			return;
		}
		if (getPeriodList().size() == 0) {
			clearTable();
			return;
		}
		tiny_Total.clear();
		dep_Total.clear();
		total_Total.clear();
		loadTable();
	}

	/**
	 * 
	 * @description 动态加载表
	 * @author duhongming
	 * @createDate 2011-12-15
	 * @throws BOSException
	 * @throws SQLException
	 * @version EAS7.0
	 * @throws EASBizException
	 * @throws ParseException
	 * @see
	 */
	public void loadTable() throws BOSException, SQLException, EASBizException, ParseException {
		clearTable();
		List list = getPeriodList();
		Map map = getFDCDepConPayPlanBill(list);
		if (map == null || map.size() == 0) {
			return;
		}
		loadTableHeadRow();
		loadTableData(map);
		if (tblMain.getRowCount() == 0) {
			clearTable();
		}
	}

	/**
	 * @description 加载表内数据
	 * @author duhongming
	 * @createDate 2011-12-16
	 * @param map
	 *            合同月度滚动付款计划集合
	 * @throws BOSException
	 * @throws SQLException
	 * @throws EASBizException
	 * @version EAS7.0
	 * @throws ParseException
	 * @see
	 */
	private void loadTableData(Map map) throws BOSException, SQLException, EASBizException, ParseException {
		Set keySet = map.keySet();
		int count = 0;
		for (Iterator item = keySet.iterator(); item.hasNext();) {
			Object key = item.next();
			Set set = (Set) map.get(key);
			if (getCountByContractPayExec(set) > 0) {
				/** 循环加载部门数据 **/
				load3CategoryContractDataTable(set);
				addRow(1, "部门合计");
				loadEditDeptData(key);
				count++;
			}
		}
		addTotalRow(count);
		calTotal(tiny_Total);
		calDepTotal();
		calTotalTotal();
		mergeTable();
	}

	/**
	 * @description 重置表格
	 * @author duhongming
	 * @createDate 2011-12-16
	 * @param count
	 *            部门计数
	 * @version EAS7.0
	 * @see
	 */
	private void mergeTable() {
		int temp = 0;
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			String value = (String) tblMain.getRow(i).getCell(1).getValue();
			if ("部门合计".equals(value)) {
				tblMain.getMergeManager().mergeBlock(temp, 0, i, 0);
				temp = i + 1;
			}
		}
	}

	/**
	 * @description 加载编制部门数据行数据
	 * @author duhongming
	 * @createDate 2011-12-16
	 * @param key
	 *            行政组织ID
	 * @throws BOSException
	 * @throws EASBizException
	 * @version EAS7.0
	 * @see
	 */
	private void loadEditDeptData(Object key) throws BOSException, EASBizException {
		AdminOrgUnitInfo adminOrgUnitInfo = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(key.toString()));
		tblMain.getRow(tblMain.getRowCount() - 1).getCell(0).setValue(adminOrgUnitInfo.getName());
	}

	/**
	 * @description 加载三类合同数据表
	 * @author duhongming
	 * @createDate 2011-12-16
	 * @param set
	 *            合同付款滚动计划ID集合
	 * @throws BOSException
	 * @throws EASBizException
	 * @version EAS7.0
	 * @throws ParseException
	 * @see
	 */
	private void load3CategoryContractDataTable(Set set) throws BOSException, EASBizException, ParseException {
		loadFDCDepConPayPlanContract(set);
		loadFDCDepConPayPlanUnsettledContract(set);
		loadFDCDepConPayPlanNoContract(set);
	}

	/**
	 * @description 添加合计行
	 * @author duhongming
	 * @createDate 2011-12-16
	 * @param count
	 *            计数（主要判断是否有添加数据）
	 * @version EAS7.0
	 * @see
	 */
	private void addTotalRow(int count) {
		if (count > 0) {
			addRow(1, "合计");
		}
	}

	/**
	 * 
	 * @description 根据合同付款滚动计划ID集合,查看3类合同的计数
	 * @author duhongming
	 * @createDate 2011-12-16
	 * @param set
	 *            合同付款滚动计划ID集合
	 * @return 3类合同的计数
	 * @throws BOSException
	 * @throws SQLException
	 * @version EAS7.0
	 * @see
	 */
	private int getCountByContractPayExec(Set set) throws BOSException, SQLException {
		if (set.size() > 0) {
			Object[] array = set.toArray();
			String[] arrayStr = new String[array.length];
			for (int i = 0; i < set.toArray().length; i++) {
				arrayStr[i] = (String) array[i];
			}
			FDCSQLBuilder builder1 = new FDCSQLBuilder();
			builder1.appendSql("select count(fid) as count from T_FNC_FDCDepConPayPlanContract where " + FDCSQLBuilder.getInSql("fheadid", arrayStr));
			IRowSet rs1 = builder1.executeQuery();
			int count = 0;
			while (rs1.next()) {
				count += rs1.getInt("count");
			}
			FDCSQLBuilder builder2 = new FDCSQLBuilder();
			builder2.appendSql("select count(fid)  as count from T_FNC_FDCDepConPayPlanUC where " + FDCSQLBuilder.getInSql("fparentid", arrayStr));
			IRowSet rs2 = builder2.executeQuery();
			while (rs2.next()) {
				count += rs2.getInt("count");
			}
			FDCSQLBuilder builder3 = new FDCSQLBuilder();
			builder3.appendSql("select count(fid)  as count from T_FNC_FDCDepConPayPlanNoCon where " + FDCSQLBuilder.getInSql("fheadid", arrayStr));
			IRowSet rs3 = builder3.executeQuery();
			while (rs3.next()) {
				count += rs3.getInt("count");
			}
			return count;
		}
		return -1;
	}

	/**
	 * @description 删除表体
	 * @author duhongming
	 * @createDate 2011-12-15
	 * @version EAS7.0
	 * @see
	 */
	private void clearTable() {
		tblMain.checkParsed();
		tblMain.removeRows();
		tblMain.removeRows();
		tblMain.removeHeadRows();
		tblMain.removeColumns();
	}

	/**
	 * 
	 * @description 加载表头
	 * @author duhongming
	 * @createDate 2011-12-15
	 * @version EAS7.0
	 * @see
	 */
	private void loadTableHeadRow() {
		IColumn column0 = tblMain.addColumn(0);
		column0.setKey("editDept");
		column0.setWidth(200);
		IColumn column1 = tblMain.addColumn(1);
		column1.setKey("planType");
		column1.setWidth(150);
		IColumn column2 = tblMain.addColumn(2);
		column2.setKey("payNumber");
		column2.setWidth(200);
		IColumn column3 = tblMain.addColumn(3);
		column3.setKey("payName");
		column3.setWidth(200);

		IRow head0 = tblMain.addHeadRow(0);
		IRow head1 = tblMain.addHeadRow(1);
		head0.getCell(0).setValue("编制部门");
		head0.getCell(1).setValue("计划类型");
		head0.getCell(2).setValue("合同／待签订合同／付款事项编码");
		head0.getCell(3).setValue("合同／待签订合同／付款事项名称");
		List periodList = getPeriodList();
		int indexColumn = 4;
		int k = 1;
		for (int i = 0; i < periodList.size(); i++) {
			Date date = (Date) periodList.get(i);
			String[] dateStr = sdf.format(date).split("-");
			String formatDate = dateStr[0] + "年" + dateStr[1] + "月";
			String KeyHead = "MONTH" + dateStr[0] + "" + dateStr[1];
			int columnCount = indexColumn + 5 * (k - 1);
			IColumn column = addColumn(tblMain, columnCount);
			column.setKey(KeyHead + "conPrice");
			head0.getCell(columnCount).setValue(formatDate);
			head1.getCell(KeyHead + "conPrice").setValue("合同最新造价／预计签约金额");
			for (int j = 1; j <= 4; j++) {
				IColumn column_ = addColumn(tblMain, columnCount + j);
				if (j == 1) {
					column_.setKey(KeyHead + "acc");
					head1.getCell(KeyHead + "acc").setValue("累计已付工程款");
				}
				if (j == 2) {
					column_.setKey(KeyHead + "plan");
					head1.getCell(KeyHead + "plan").setValue("计划支付金额");
				}
				if (j == 3) {
					column_.setKey(KeyHead + "real");
					head1.getCell(KeyHead + "real").setValue("实际支付金额");
				}
				if (j == 4) {
					column_.setKey(KeyHead + "dev");
					head1.getCell(KeyHead + "dev").setValue("偏差（实际—计划)");
				}
			}
			k++;
		}
		mergrTableHead(periodList);
	}

	/**
	 * 
	 * @description
	 * @author duhongming
	 * @createDate 2012-1-11
	 * @param table
	 *            表名
	 * @param index
	 *            所属列
	 * @return 格式为"#,##0.00"的一列
	 * @version EAS7.0
	 * @see
	 */
	private IColumn addColumn(KDTable table, int index) {
		IColumn column = table.addColumn(index);
		column.getStyleAttributes().setNumberFormat("#,##0.00");
		column.setEditor(FDCClientHelper.getNumberCellEditor());
		column.setWidth(180);
		return column;
	}

	/**
	 * @description 重置表头
	 * @author duhongming
	 * @createDate 2011-12-16
	 * @param periodList
	 *            日期段
	 * @version EAS7.0
	 * @see
	 */
	private void mergrTableHead(List periodList) {
		for (int i = 0; i < 4; i++) {
			tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
		for (int i = 0; i < periodList.size(); i++) {
			tblMain.getHeadMergeManager().mergeBlock(0, 4 + i * 5, 0, 8 + i * 5);
		}
		tblMain.getStyleAttributes().setLocked(true);
	}

	/**
	 * @description 根据开始截至日期,获得开始与截至之间的日期集合包含开始、截至
	 * @author duhongming
	 * @createDate 2011-12-14
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private List getPeriodList() {
		Integer startYear = spStartYear.getIntegerVlaue();
		Integer startMonth = spStartMonth.getIntegerVlaue();
		Integer endYear = spEndYear.getIntegerVlaue();
		Integer endMonth = spEndMonth.getIntegerVlaue();
		if (startYear.intValue() < 1500 || startYear.intValue() > 10000 || startMonth.intValue() <= 0 || startMonth.intValue() > 12 || endYear.intValue() < 1500
				|| endYear.intValue() > 10000 || endMonth.intValue() <= 0 || endMonth.intValue() > 12) {
			return new LinkedList();
		}
		String start = startYear.toString() + "-" + startMonth.toString();
		String end = endYear.toString() + "-" + endMonth.toString();
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = sdf.parse(start);
			endDate = sdf.parse(end);
		} catch (Exception e1) {
			abort();
		}
		List list = new LinkedList();
		int diff = getMonths(startDate, endDate);
		for (int i = 0; i <= diff; i++) {
			list.add(DateUtils.addMonth(startDate, i));
		}
		return list;
	}

	/**
	 * 
	 * @description 获取2个日期的月份差值
	 * @author duhongming
	 * @createDate 2011-12-15
	 * @param date1
	 *            开始
	 * @param date2
	 *            结束
	 * @return 开始-结束=月份差值
	 * @version EAS7.0
	 * @see
	 */
	public int getMonths(Date date1, Date date2) {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(date1);
			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(date2);
			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH))
				flag = 1;
			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12 + objCalendarDate2.get(Calendar.MONTH) - flag)
						- objCalendarDate1.get(Calendar.MONTH);
			else
				iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH) - flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}

	/**
	 * @description 合同月度滚动付款计划
	 * @author duhongming
	 * @createDate 2011-12-14
	 * @return
	 * @throws BOSException
	 * @version EAS7.0
	 * @throws SQLException
	 * @throws EASBizException
	 * @throws ParseException
	 * @see
	 */
	private Map getFDCDepConPayPlanBill(List list) throws BOSException, SQLException, EASBizException, ParseException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("payPlanCycle.cycle");
		view.getSelector().add("year");
		view.getSelector().add("month");
		view.getSelector().add("id");
		view.getSelector().add("deptment.id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("deptment.longNumber", getLastSelectAdminOrgUnit().getLongNumber() + "%", CompareType.LIKE));
		view.setFilter(filter);
		view.setSorter(getSorter("deptment.id"));
		FDCDepConPayPlanBillCollection payPlans = FDCDepConPayPlanBillFactory.getRemoteInstance().getFDCDepConPayPlanBillCollection(view);
		Map map = getIncudeDateData(list, payPlans);
		return map;
	}

	/**
	 * @description 获取符合时期段的数据
	 * @author duhongming
	 * @createDate 2012-1-11
	 * @param list
	 * @param payPlans
	 * @return
	 * @throws ParseException
	 * @version EAS7.0
	 * @see
	 */
	private Map getIncudeDateData(List list, FDCDepConPayPlanBillCollection payPlans) throws ParseException {
		Map map = new LinkedHashMap();
		List dateList = new ArrayList();
		for (int i = 0; i < payPlans.size(); i++) {
			FDCDepConPayPlanBillInfo payPlan = payPlans.get(i);
			dateList.clear();
			dateList.add(sdf.parse(payPlan.getYear() + "-" + payPlan.getMonth()));
			addDateByCycle(dateList, payPlan);
			putFDCDepConPayPlanData(dateList, payPlan, list, map);
		}
		return map;
	}

	/**
	 * @description 根据周期,逐一添加日期
	 * @author duhongming
	 * @createDate 2012-1-11
	 * @param dateList
	 * @param payPlan
	 * @param year
	 * @param month
	 * @throws ParseException
	 * @version EAS7.0
	 * @see
	 */
	private void addDateByCycle(List dateList, FDCDepConPayPlanBillInfo payPlan) throws ParseException {
		int year = payPlan.getYear();
		int month = payPlan.getMonth();
		for (int j = 0; j < payPlan.getPayPlanCycle().getCycle().getValue() - 1; j++) {
			if (month == 12) {
				year = year + 1;
				month = 1;
			} else {
				month = month + 1;
			}
			dateList.add(sdf.parse(year + "-" + month));
		}
	}
	
	/**
	 * 
	 * @description 添加符合条件的ID
	 * @author duhongming
	 * @createDate 2012-1-11
	 * @param dateList
	 * @param payPlan
	 * @param list
	 * @param map
	 * @version EAS7.0
	 * @see
	 */
	private void putFDCDepConPayPlanData(List dateList, FDCDepConPayPlanBillInfo payPlan, List list, Map map) {
		Set set = new HashSet();
		for (int i = 0; i < dateList.size(); i++) {
			Object object = dateList.get(i);
			if (list.contains(object)) {
				String orgId = payPlan.getDeptment().getId().toString();
				String fid = payPlan.getId().toString();
				if (!map.containsKey(orgId)) {
					set = new HashSet();
					set.add(fid);
					map.put(orgId, set);
				} else {
					((HashSet) map.get(orgId)).add(fid);
				}
			}
		}
	}
	/**
	 * 
	 * @description 合计计算
	 * @author duhongming
	 * @createDate 2011-12-20
	 * @param map
	 * @version EAS7.0
	 * @see
	 */
	private void calTotal(Map map) {
		Set keySet = map.keySet();
		int size = getPeriodList().size();
		int beginColumn = 4;
		int endColumn = size * 5 + 3;
		for (Iterator item = keySet.iterator(); item.hasNext();) {
			String key = (String) item.next();
			IRow totalRow = (IRow) map.get(key);
			String[] split = key.split("-");
			int beginRow = Integer.parseInt(split[0]);
			int endRow = Integer.parseInt(split[1]);
			// 开始列-结束列
			for (int i = beginColumn; i <= endColumn; i++) {
				// 开始行-结束行
				BigDecimal amount = FDCNumberHelper.ZERO;
				for (int j = beginRow; j <= endRow; j++) {
					IRow row = tblMain.getRow(j);
					amount = FDCNumberHelper.add(amount, row.getCell(i).getValue());
				}
				if (amount != null && !FDCNumberHelper.ZERO.equals(amount)) {
					totalRow.getCell(i).setValue(amount);
				}
			}
		}
	}
	
	/**
	 * 
	 * @description 部门合计
	 * @author duhongming
	 * @createDate 2012-1-11
	 * @version EAS7.0
	 * @see
	 */
	private void calDepTotal() {
		Map map = putDepTotal();
		setValueOfDepTotalRow(map);
	}

	/**
	 * @description 给部门合计行赋值
	 * @author duhongming
	 * @createDate 2012-1-11
	 * @param map
	 * @version EAS7.0
	 * @see
	 */
	private void setValueOfDepTotalRow(Map map) {
		int deptCount = 0;
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			Object value = row.getCell(1).getValue();
			if ("部门合计".equals(value)) {
				for (Iterator item = map.keySet().iterator(); item.hasNext();) {
					String key = (String) item.next();
					int first_key = Integer.parseInt(key.split("-")[0]);
					int second_key = Integer.parseInt(key.split("-")[1]);
					if (deptCount == first_key) {
						row.getCell(second_key).setValue(map.get(key));
					}
				}
				deptCount++;
			}
		}
	}

	/**
	 * @description 获取部门合计值,并放入到MAP中
	 * @author duhongming
	 * @createDate 2012-1-11
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private Map putDepTotal() {
		int deptTimes = 0;
		Map map = new LinkedHashMap();
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			Object value = row.getCell(1).getValue();
			if ("部门合计".equals(value)) {
				deptTimes++;
			}
			if ("小计".equals(value)) {
				for (int j = 4; j < tblMain.getColumnCount(); j++) {
					Object amount = row.getCell(j).getValue();
					if (amount instanceof BigDecimal) {
						String key = deptTimes + "-" + j;
						if (map.containsKey(key)) {
							map.put(key, ((BigDecimal) (map.get(key))).add((BigDecimal) amount));
						} else {
							map.put(key, amount);
						}
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 
	 * @description 给总合计行赋值
	 * @author duhongming
	 * @createDate 2012-1-11
	 * @version EAS7.0
	 * @see
	 */
	private void calTotalTotal() {
		Map map = new LinkedHashMap();
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			Object value = row.getCell(1).getValue();
			if ("部门合计".equals(value)) {
				for (int j = 4; j < tblMain.getColumnCount(); j++) {
					Object amount = row.getCell(j).getValue();
					if (amount instanceof BigDecimal) {
						String key = j + "";
						if (map.containsKey(key)) {
							map.put(key, ((BigDecimal) (map.get(key))).add((BigDecimal) amount));
						} else {
							map.put(key, amount);
						}
					}
				}
			}
		}
		IRow totalRow = tblMain.getRow(tblMain.getRowCount() - 1);
		for (Iterator item = map.keySet().iterator(); item.hasNext();) {
			String key = (String) item.next();
			totalRow.getCell(Integer.parseInt(key)).setValue(map.get(key));
		}
	}

	/**
	 * 
	 * @description 添加行,主要用于小计、部门合计、合计行数据行
	 * @author duhongming
	 * @createDate 2011-12-16
	 * @param indexCell
	 * @param value
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private IRow addRow(int indexCell, String value) {
		IRow row = tblMain.addRow();
		row.getCell(indexCell).setValue(value);
		return row;
	}

	/**
	 * 
	 * @description 加载已签定合同付款计划
	 * @author duhongming
	 * @createDate 2011-12-15
	 * @param ids
	 * @throws BOSException
	 * @version EAS7.0
	 * @throws EASBizException
	 * @throws ParseException
	 * @see
	 */
	private void loadFDCDepConPayPlanContract(Set ids) throws BOSException, EASBizException, ParseException {
		Map map = getFDCDepConPayPlanContractColl(ids);
		tblMain.checkParsed();
		IRow row = null;
		int begin = tblMain.getRowCount();
		int end = begin;
		Set keySet = map.keySet();
		int count = 0;
		List periodList = getPeriodList();
		for (Iterator item = keySet.iterator(); item.hasNext();) {
			String key = (String) item.next();
			row = tblMain.addRow();
			if (count == 0) {
				row.getCell(1).setValue("已签定合同付款计划");
			}
			FDCDepConPayPlanContractCollection value = (FDCDepConPayPlanContractCollection) map.get(key);
			if (value.size() > 0) {
				row.getCell(2).setValue(value.get(0).getContract().getNumber());
				row.getCell(3).setValue(value.get(0).getContract().getName());
			}
			BigDecimal conPayPrice = FDCNumberHelper.ZERO;
			for (int i = 0; i < periodList.size(); i++) {
				conPayPrice = getConPayPrice(key, DateUtils.startOfMonth((Date) periodList.get(i)), DateUtils.endOfMonth((Date) periodList.get(i)));
				setRowValueWithDate(row, (Date) periodList.get(i), conPayPrice, "real");// 实际支付
			}
			for (int j = 0; j < value.size(); j++) {
				FDCDepConPayPlanContractInfo hasCon = value.get(j);
				setRowValueWithDate(row, hasCon.getHead(), hasCon.getContractPrice(), "conPrice");
				for (int k = 0; k < hasCon.getEntrys().size(); k++) {
					FDCDepConPayPlanContractEntryInfo entry = hasCon.getEntrys().get(k);
					Date time = entry.getMonth();
					setRowValueWithDate(row, time, entry.getOfficialPay(), "plan");// 计划支付
					setRowValueWithDate(row, hasCon.getHead(), entry.getParentC().getLastMonthPay(), "acc");// 累计已付工程款
					setDevAmount(entry.getOfficialPay(), conPayPrice, row, time);// 偏差
				}
			}
			ROW_COUNT++;
			end++;
			count++;
		}
		if (count > 0) {
			IRow totalRow = addRow(1, "小计");
			tiny_Total.put(begin + "-" + end, totalRow);
			ROW_COUNT++;
		}
	}

	/**
	 * 
	 * @description 设置偏差金额（实际金额-计划金额）
	 * @author duhongming
	 * @createDate 2012-1-12
	 * @param pAmount
	 *            计划金额
	 * @param aAmount
	 *            实际金额
	 * @param row
	 * @param time
	 * @version EAS7.0
	 * @see
	 */
	private void setDevAmount(BigDecimal pAmount, BigDecimal aAmount, IRow row, Date time) {
		if (pAmount == null) {
			pAmount = FDCNumberHelper.ZERO;
		}
		if (aAmount == null) {
			aAmount = FDCNumberHelper.ZERO;
		}
		BigDecimal devAmount = FDCNumberHelper.subtract(aAmount, pAmount);
		setRowValueWithDate(row, time, devAmount, "dev");
	}

	/**
	 * 
	 * @description 赋值合同造价
	 * @author duhongming
	 * @createDate 2012-1-12
	 * @param row
	 * @param billInfo
	 * @param amount
	 * @version EAS7.0
	 * @see
	 */
	private void setRowValueWithDate(IRow row, FDCDepConPayPlanBillInfo billInfo, BigDecimal amount, String args) {
		int edit_year = billInfo.getYear();
		int edit_month = billInfo.getMonth();
		String edit_year_month = "MONTH" + edit_year + "" + edit_month;
		if (edit_month < 10) {
			edit_year_month = "MONTH" + edit_year + "0" + edit_month;
		}
		if (row.getCell(edit_year_month + args) != null && !FDCNumberHelper.ZERO.equals(amount)) {
			row.getCell(edit_year_month + args).setValue(amount);
		}
	}
	
	private void setRowValueWithDate(IRow row, Date time, BigDecimal amount, String args) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String keyHead = "MONTH" + year + "" + month;
		if (month < 10) {
			keyHead = "MONTH" + year + "0" + month;
		}
		if (row.getCell(keyHead + args) != null && !FDCNumberHelper.ZERO.equals(amount)) {
			row.getCell(keyHead + args).setValue(amount);
		}
	}
	/**
	 * 
	 * @description 获得合同内工程款
	 * @author duhongming
	 * @createDate 2012-1-10
	 * @param conID
	 * @param from
	 * @param to
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	private BigDecimal getConPayPrice(String conID, Date from, Date to) throws BOSException {
		String begin = "{ts'" + new SimpleDateFormat("yyyy-MM-dd").format(from) + " 00:00:00'}";
		String end = "{ts'" + new SimpleDateFormat("yyyy-MM-dd").format(to) + " 00:00:00'}";
		BigDecimal pay = FDCNumberHelper.ZERO;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select sum(pb.FProjectPriceInContract) + sum(ppe.FLocAdvance)");
		builder.appendSql(" from T_CAS_PaymentBill as pb ");
		builder.appendSql(" left join T_FNC_PaymentPrjPayEntry as ppe ");
		builder.appendSql(" on pb.FPrjPayEntryID = ppe.FID ");
		builder.appendSql(" where pb.FContractbillId =? ");
		builder.appendSql(" and pb.fbillstatus = 15 ");
		builder.appendSql(" and pb.FpayDate between " + begin + " and " + end);
		builder.addParam(conID);
		IRowSet rs = builder.executeQuery();
		try {
			while (rs.next()) {
				pay = rs.getBigDecimal(1) != null ? rs.getBigDecimal(1) : pay;
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return pay;
	}
	/**
	 * 
	 * @description 加载待签定合同付款计划
	 * @author duhongming
	 * @createDate 2011-12-15
	 * @param ids
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	private void loadFDCDepConPayPlanUnsettledContract(Set ids) throws BOSException {
		Map map = getFDCDepConPayPlanUnsettledConColl(ids);
		tblMain.checkParsed();
		IRow row = null;
		int begin = tblMain.getRowCount();
		int end = begin;
		int count = 0;
		Set keySet = map.keySet();
		for (Iterator item = keySet.iterator(); item.hasNext();) {
			String key = (String) item.next();
			row = tblMain.addRow();
			if (count == 0) {
				row.getCell(1).setValue("待签定合同付款计划");
			}
			FDCDepConPayPlanUnsettledConCollection value = (FDCDepConPayPlanUnsettledConCollection) map.get(key);
			if (value.size() > 0) {
				row.getCell(2).setValue(value.get(0).getUnConNumber());
				row.getCell(3).setValue(value.get(0).getUnConName());
			}
			for (int j = 0; j < value.size(); j++) {
				FDCDepConPayPlanUnsettledConInfo unsettledCon = value.get(j);
				setRowValueWithDate(row, unsettledCon.getParent(), unsettledCon.getPlanAmount(), "conPrice");// 合同造价
				for (int k = 0; k < unsettledCon.getEntrys1().size(); k++) {
					FDCDepConPayPlanUnsettleEntryInfo entry = unsettledCon.getEntrys1().get(k);
					BigDecimal officialPay = entry.getOfficialPay();
					setRowValueWithDate(row, entry.getMonth(), officialPay, "plan");// 计划支付
					setDevAmount(officialPay, FDCNumberHelper.ZERO, row, entry.getMonth());// 偏差
				}
			}
			ROW_COUNT++; 
			end++;
			count++;
		}
		if (count > 0) {
			IRow totalRow = addRow(1, "小计");
			tiny_Total.put(begin + "-" + end, totalRow);
			ROW_COUNT++;
		}
	}

	/**
	 * 
	 * @description 加载无合同付款计划
	 * @author duhongming
	 * @createDate 2011-12-15
	 * @param ids
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	private void loadFDCDepConPayPlanNoContract(Set ids) throws BOSException {
		Map map = getFDCDepConPayPlanNoContractColl(ids);
		tblMain.checkParsed();
		IRow row = null;
		int begin = tblMain.getRowCount();
		int end = begin;
		int count = 0;
		Set keySet = map.keySet();
		for (Iterator item = keySet.iterator(); item.hasNext();) {
			String key = (String) item.next();
			row = tblMain.addRow();
			if (count == 0) {
				row.getCell(1).setValue("无合同付款计划");
			}
			FDCDepConPayPlanNoContractCollection value = (FDCDepConPayPlanNoContractCollection) map.get(key);
			if (value.size() > 0) {
				row.getCell(2).setValue(value.get(0).getPayMatters());
				row.getCell(3).setValue(value.get(0).getPayMattersName());
			}
			for (int j = 0; j < value.size(); j++) {
				FDCDepConPayPlanNoContractInfo noCon = value.get(j);
				for (int k = 0; k < noCon.getEntrys1().size(); k++) {
					FDCDepConPayPlanNoContractEntryInfo entry = noCon.getEntrys1().get(k);
					Calendar cal = Calendar.getInstance();
					cal.setTime(entry.getMonth());
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					String keyHead = "MONTH" + year + "" + month;
					if (month < 10) {
						keyHead = "MONTH" + year + "0" + month;
					}
					if (row.getCell(keyHead + "plan") != null) {
						row.getCell(keyHead + "plan").setValue(entry.getOfficialPay());
					}
					setRowValueWithDate(row, entry.getMonth(), entry.getOfficialPay(), "plan");
					setDevAmount(entry.getOfficialPay(), FDCNumberHelper.ZERO, row, entry.getMonth());
				}
			}
			ROW_COUNT++;
			end++;
			count++;
		}
		if (count > 0) {
			IRow totalRow = addRow(1, "小计");
			tiny_Total.put(begin + "-" + end, totalRow);
			ROW_COUNT++;
		}
	}

	/**
	 * 
	 * @description 已签定合同付款计划
	 * @author duhongming
	 * @createDate 2011-12-14
	 * @param heads
	 * @return
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	private Map getFDCDepConPayPlanContractColl(Set heads) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("contract.id");
		view.getSelector().add("contract.name");
		view.getSelector().add("contract.number");
		view.getSelector().add("contractPrice");
		view.getSelector().add("entrys.*");
		view.getSelector().add("entrys.parentC.lastMonthPay");
		view.getSelector().add("entrys.parentC.head.year");
		view.getSelector().add("entrys.parentC.head.month");
		view.getSelector().add("lastMonthPayable");
		view.getSelector().add("head.year");
		view.getSelector().add("head.month");
		view.setSorter(getSorter("contract.number"));
		filter.getFilterItems().add(new FilterItemInfo("head.id", heads, CompareType.INCLUDE));
		view.setFilter(filter);
		FDCDepConPayPlanContractCollection col = FDCDepConPayPlanContractFactory.getRemoteInstance().getFDCDepConPayPlanContractCollection(view);
		FDCDepConPayPlanContractCollection value = new FDCDepConPayPlanContractCollection();
		Map map = new LinkedHashMap();
		for (int i = 0; i < col.size(); i++) {
			FDCDepConPayPlanContractInfo item = col.get(i);
			String key = item.getContract().getId().toString();
			if (!map.containsKey(key)) {
				value = new FDCDepConPayPlanContractCollection();
				value.add(item);
				map.put(key, value);
			} else {
				resetPayPlanContractValue(map, key, item);
				((FDCDepConPayPlanContractCollection) map.get(key)).add(item);	
			}
		}
		return map;
	}

	/**
	 * 
	 * @description 如果有相同计划月份时，最终批复金额取编制月份大的数据
	 * @author duhongming
	 * @createDate 2012-1-16
	 * @param map
	 *            <合同ID,数据集合>
	 * @param key
	 *            当前合同ID
	 * @param currentItem
	 *            当前数据
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	private void resetPayPlanContractValue(Map map, String key,FDCDepConPayPlanContractInfo currentItem) throws BOSException {
		try {
			Date current_editDate = sdf.parse(currentItem.getHead().getYear() + "-" + currentItem.getHead().getMonth());// 获取当前的父节点的编制日期
			FDCDepConPayPlanContractEntryCollection entryCol = currentItem.getEntrys();// 获取当前的节点的分录
			for (int i = 0; i < entryCol.size(); i++) {//循环当前节点分录
				FDCDepConPayPlanContractEntryInfo currentEntryInfo = entryCol.get(i);//获取当前的每一个分录节点
				Date currentEntryPlanMonth = sdf.parse(sdf.format(currentEntryInfo.getMonth()));//获取当前的每一个分录节点的计划日期
				FDCDepConPayPlanContractCollection collection=((FDCDepConPayPlanContractCollection) map.get(key));//获取MAP中已经放入的数据集合
				for (int j = 0; j < collection.size(); j++) {
					FDCDepConPayPlanContractInfo contractInfo = collection.get(j);//获取数据集合中的每一个元素
					Date planMonth = sdf.parse(contractInfo.getHead().getYear() + "-" + contractInfo.getHead().getMonth());// 获取数据集合中的每一个元素的上级的编制日期
					FDCDepConPayPlanContractEntryCollection entrys = contractInfo.getEntrys();// 获取数据集合中的每一个元素的分录
					for (int k = 0; k < entrys.size(); k++) {// 循环获取数据集合中的每一个元素的分录的每一个元素
						Date formatPlanMonth = sdf.parse(sdf.format(entrys.get(k).getMonth()));// 获取数据集合中的每一个元素的分录的每一个元素的计划日期
						if (formatPlanMonth.compareTo(currentEntryPlanMonth) == 0) {// 判断获取数据集合中的每一个元素的分录的每一个元素的计划日期是否与当前的每一个分录节点的计划日期是否相等
							if (planMonth.after(current_editDate)) {// 判断当前的父节点的编制日期大于循环每个元素的父节点的编制日期
								currentEntryInfo.setOfficialPay(entrys.get(k).getOfficialPay());// 最终批复金额取编制月份大的数据
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
	}
	
	/**
	 * 
	 * @description 如果有相同计划月份时，最终批复金额取编制月份大的数据
	 * @author duhongming
	 * @createDate 2012-1-16
	 * @param map
	 *            <合同ID,数据集合>
	 * @param key
	 *            当前合同ID
	 * @param currentItem
	 *            当前数据
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	private void resetPayPlanUnsettleValue(Map map, String key, FDCDepConPayPlanUnsettledConInfo currentItem) throws BOSException {
		try {
			Date current_editDate = sdf.parse(currentItem.getParent().getYear() + "-" + currentItem.getParent().getMonth());// 获取当前的父节点的编制日期
			FDCDepConPayPlanUnsettleEntryCollection entryCol = currentItem.getEntrys1();// 获取当前的节点的分录
			for (int i = 0; i < entryCol.size(); i++) {// 循环当前节点分录
				FDCDepConPayPlanUnsettleEntryInfo currentEntryInfo = entryCol.get(i);// 获取当前的每一个分录节点
				Date currentEntryPlanMonth = sdf.parse(sdf.format(currentEntryInfo.getMonth()));// 获取当前的每一个分录节点的计划日期
				FDCDepConPayPlanUnsettledConCollection collection = ((FDCDepConPayPlanUnsettledConCollection) map.get(key));// 获取MAP中已经放入的数据集合
				for (int j = 0; j < collection.size(); j++) {
					FDCDepConPayPlanUnsettledConInfo contractInfo = collection.get(j);// 获取数据集合中的每一个元素
					Date planMonth = sdf.parse(contractInfo.getParent().getYear() + "-" + contractInfo.getParent().getMonth());// 获取数据集合中的每一个元素的上级的编制日期
					FDCDepConPayPlanUnsettleEntryCollection entrys = contractInfo.getEntrys1();// 获取数据集合中的每一个元素的分录
					for (int k = 0; k < entrys.size(); k++) {// 循环获取数据集合中的每一个元素的分录的每一个元素
						Date formatPlanMonth = sdf.parse(sdf.format(entrys.get(k).getMonth()));// 获取数据集合中的每一个元素的分录的每一个元素的计划日期
						if (formatPlanMonth.compareTo(currentEntryPlanMonth) == 0) {// 判断获取数据集合中的每一个元素的分录的每一个元素的计划日期是否与当前的每一个分录节点的计划日期是否相等
							if (planMonth.after(current_editDate)) {// 判断当前的父节点的编制日期大于循环每个元素的父节点的编制日期
								currentEntryInfo.setOfficialPay(entrys.get(k).getOfficialPay());// 最终批复金额取编制月份大的数据
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
	}

	/**
	 * 
	 * @description 如果有相同计划月份时，最终批复金额取编制月份大的数据
	 * @author duhongming
	 * @createDate 2012-1-16
	 * @param map
	 *            <合同ID,数据集合>
	 * @param key
	 *            当前合同ID
	 * @param currentItem
	 *            当前数据
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	private void resetPayPlanNoContractValue(Map map, String key, FDCDepConPayPlanNoContractInfo currentItem) throws BOSException {
		try {
			Date current_editDate = sdf.parse(currentItem.getHead().getYear() + "-" + currentItem.getHead().getMonth());// 获取当前的父节点的编制日期
			FDCDepConPayPlanNoContractEntryCollection entryCol = currentItem.getEntrys1();// 获取当前的节点的分录
			for (int i = 0; i < entryCol.size(); i++) {// 循环当前节点分录
				FDCDepConPayPlanNoContractEntryInfo currentEntryInfo = entryCol.get(i);// 获取当前的每一个分录节点
				Date currentEntryPlanMonth = sdf.parse(sdf.format(currentEntryInfo.getMonth()));// 获取当前的每一个分录节点的计划日期
				FDCDepConPayPlanNoContractCollection collection = ((FDCDepConPayPlanNoContractCollection) map.get(key));// 获取MAP中已经放入的数据集合
				for (int j = 0; j < collection.size(); j++) {
					FDCDepConPayPlanNoContractInfo contractInfo = collection.get(j);// 获取数据集合中的每一个元素
					Date planMonth = sdf.parse(contractInfo.getHead().getYear() + "-" + contractInfo.getHead().getMonth());// 获取数据集合中的每一个元素的上级的编制日期
					FDCDepConPayPlanNoContractEntryCollection entrys = contractInfo.getEntrys1();// 获取数据集合中的每一个元素的分录
					for (int k = 0; k < entrys.size(); k++) {// 循环获取数据集合中的每一个元素的分录的每一个元素
						Date formatPlanMonth = sdf.parse(sdf.format(entrys.get(k).getMonth()));// 获取数据集合中的每一个元素的分录的每一个元素的计划日期
						if (formatPlanMonth.compareTo(currentEntryPlanMonth) == 0) {// 判断获取数据集合中的每一个元素的分录的每一个元素的计划日期是否与当前的每一个分录节点的计划日期是否相等
							if (planMonth.after(current_editDate)) {// 判断当前的父节点的编制日期大于循环每个元素的父节点的编制日期
								currentEntryInfo.setOfficialPay(entrys.get(k).getOfficialPay());// 最终批复金额取编制月份大的数据
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BOSException(e);
		}
	}
	
	
	private SorterItemCollection getSorter(String sort) {
		SorterItemCollection sic = new SorterItemCollection();
		SorterItemInfo item = new SorterItemInfo(sort);
		item.setSortType(SortType.DESCEND);
		sic.add(item);
		return sic;
	}
	/**
	 * 
	 * @description 待签定合同付款计划
	 * @author duhongming
	 * @createDate 2011-12-14
	 * @param heads
	 * @return
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	private Map getFDCDepConPayPlanUnsettledConColl(Set heads) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("id");
		view.getSelector().add("unConName");
		view.getSelector().add("unConNumber");
		view.getSelector().add("planAmount");
		view.getSelector().add("parent.year");
		view.getSelector().add("parent.month");
		view.getSelector().add("entrys1.*");
		view.setSorter(getSorter("unConNumber"));
		filter.getFilterItems().add(new FilterItemInfo("parent.id", heads, CompareType.INCLUDE));
		view.setFilter(filter);
		FDCDepConPayPlanUnsettledConCollection col = FDCDepConPayPlanUnsettledConFactory.getRemoteInstance().getFDCDepConPayPlanUnsettledConCollection(view);
		FDCDepConPayPlanUnsettledConCollection value = new FDCDepConPayPlanUnsettledConCollection();
		Map map = new LinkedHashMap();
		for (int i = 0; i < col.size(); i++) {
			FDCDepConPayPlanUnsettledConInfo item = col.get(i);
			String key = item.getUnConNumber();
			if (!map.containsKey(key)) {
				value = new FDCDepConPayPlanUnsettledConCollection();
				value.add(item);
				map.put(key, value);
			} else {
				resetPayPlanUnsettleValue(map, key, item);
				((FDCDepConPayPlanUnsettledConCollection) map.get(key)).add(item);	
			}
		}
		return map;
	}

	/**
	 * 
	 * @description 无合同付款计划
	 * @author duhongming
	 * @createDate 2011-12-14
	 * @param heads
	 * @return
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	private Map getFDCDepConPayPlanNoContractColl(Set heads) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("id");
		view.getSelector().add("payMatters");
		view.getSelector().add("payMattersName");
		view.getSelector().add("entrys1.*");
		view.getSelector().add("head.year");
		view.getSelector().add("head.month");
		view.setSorter(getSorter("payMatters"));
		filter.getFilterItems().add(new FilterItemInfo("head.id", heads, CompareType.INCLUDE));
		view.setFilter(filter);
		FDCDepConPayPlanNoContractCollection col = FDCDepConPayPlanNoContractFactory.getRemoteInstance().getFDCDepConPayPlanNoContractCollection(view);
		FDCDepConPayPlanNoContractCollection value = new FDCDepConPayPlanNoContractCollection();
		Map map = new LinkedHashMap();
		for (int i = 0; i < col.size(); i++) {
			FDCDepConPayPlanNoContractInfo item = col.get(i);
			String key = item.getPayMatters();
			if (!map.containsKey(key)) {
				value = new FDCDepConPayPlanNoContractCollection();
				value.add(item);
				map.put(key, value);
			} else {
				resetPayPlanNoContractValue(map, key, item);
				((FDCDepConPayPlanNoContractCollection) map.get(key)).add(item);	
			}
		}
		return map;
	}

	private AdminOrgUnitInfo getLastSelectAdminOrgUnit() throws EASBizException, BOSException {
		AdminOrgUnitInfo adminInfo = new AdminOrgUnitInfo();
		Object userObject = ((DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent()).getUserObject();
		if (userObject instanceof FullOrgUnitInfo) {
			FullOrgUnitInfo fullinfo = (FullOrgUnitInfo) userObject;
			logger.warn("treenode:" + fullinfo.getLongNumber());
			adminInfo = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(fullinfo.getId()));
			logger.warn("AdminOrgUnit:" + adminInfo.getLongNumber());
		}
		return adminInfo;
	}

	/**
	 * 
	 * @description 开始日期、截至日期初始化
	 * @author duhongming
	 * @createDate 2011-12-13
	 * @version EAS7.0
	 * @see
	 */
	private void init() {
		KDTreeView treeView = new KDTreeView();
		treeView.setTree(treeProject);
		treeView.setShowButton(false);
		treeView.setTitle("行政组织");
		kDSplitPane1.add(treeView, "left");
		treeView.setShowControlPanel(true);
		treeProject.setShowsRootHandles(true);
		btnSearch.setEnabled(true);
		initDate();
	}

	/**
	 * @description 开始、截至年月初始化
	 * @author duhongming
	 * @createDate 2011-12-14
	 * @version EAS7.0
	 * @see
	 */
	private void initDate() {
		SpinnerNumberModel model = new SpinnerNumberModel();
		model.setMinimum(new Integer(1));
		model.setMaximum(new Integer(12));
		model.setStepSize(new Integer(1));
		spEndMonth.setModel(model);
		SpinnerNumberModel mode2 = new SpinnerNumberModel();
		mode2.setMinimum(new Integer(1));
		mode2.setMaximum(new Integer(12));
		mode2.setStepSize(new Integer(1));
		spEndMonth.setModel(mode2);
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		spStartYear.setValue(new Integer(year), false);
		spStartMonth.setValue(new Integer(month), false);
		spEndYear.setValue(new Integer(year), false);
		spEndMonth.setValue(new Integer(month), false);
	}

	protected void execQuery() {
	}

	protected void audit(List ids) throws Exception {
	}

	protected void unAudit(List ids) throws Exception {
	}
	/** 屏蔽表的敲击事件 **/
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	}
}