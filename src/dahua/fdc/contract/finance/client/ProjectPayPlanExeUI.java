/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.client.AimCostClientHelper;
import com.kingdee.eas.fdc.aimcost.client.FDCAimcostClientHelper;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.AccountStageHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 项目付款计划执行情况表
 */
public class ProjectPayPlanExeUI extends AbstractProjectPayPlanExeUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ProjectPayPlanExeUI.class);
	 // [begin] 子类重载，如果希望怱略CU过滤。
    protected boolean isIgnoreCUFilter()
    {
        return true;
    }
	/**
	 * 当前财务组织及下级实体财务组织
	 */
	protected Map companyOrgUnitMap = null;

	/**
	 * 工程项目
	 */
	protected CurProjectInfo curProject = null;
	/**
	 * 初始化数据
	 */
	protected Map initData = null;
	
	/**
	 * 目标成本
	 */
	protected Map costAccout = null;

	/**
	 * 目标成本对应付款拆分累计金额
	 */
	protected Map paySplitEntAmt = null;
	/**
	 * 动态列数
	 */
	private int dymicCols = 0;
	
	private Map acctStageMap = new HashMap();
	
	/**
	 * output class constructor
	 */
	public ProjectPayPlanExeUI() throws Exception {
		super();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
//		super.tblMain_tableClicked(e);
	}
	
	/**
	 * 点击“查询”按钮
	 */
	public void actionOK_actionPerformed(ActionEvent e) throws Exception {
		if(tblMain.getRowCount()!=0){
			return;
		}
		if(prmtProject.getValue()==null){
			FDCMsgBox.showWarning("工程项目不能为空!");
			this.abort();
		}
		String startYear = spiStartYear.getValue().toString();
		String startMon= spiStartMon.getValue().toString();
		String endYear = spiEndYear.getValue().toString();
		String endMon = spiEndMon.getValue().toString();
		if(Integer.parseInt(startYear)>Integer.parseInt(endYear)){
			FDCMsgBox.showWarning("计划起始年 不能大于 计划截止年!");
			this.abort();
		}
		if(Integer.parseInt(startYear)==Integer.parseInt(endYear)&&Integer.parseInt(startMon)>Integer.parseInt(endMon)){
			FDCMsgBox.showWarning("计划起始月 不能大于 计划截止月!");
			this.abort();
		}
		fillTable(tblMain);
		// 默认显示可拆分科目
		filterSplitData();
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionEdit, actionRemove, actionView, actionQuery, 
				 actionLocate, actionRefresh }, false);
		this.menuEdit.setVisible(false);
		this.menuEdit.setEnabled(false);
		this.menuView.setVisible(false);
		this.menuView.setEnabled(false);

	}

	public void onLoad() throws Exception {
		super.onLoad();
		if ((!SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit())
				&& (SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit())) {
			MsgBox.showWarning(this, PaymentBillClientUtils.getRes("CostUnit"));
			SysUtil.abort();
		}
		initControl();
		initTable();
		
		// 根据当前财务组织，取下级的所有实体财务组织,通过长编码来匹配
		companyOrgUnitMap = FDCClientUtils.getChildrenCompanyOrgUnitInfos(SysContext.getSysContext().getCurrentOrgUnit().getId());
		//为什么放在willShow中不行??
		EntityViewInfo view = prmtProject.getEntityViewInfo();
		if(view==null){
			view = new EntityViewInfo();
		}
		FilterInfo filter = view.getFilter();
		if(filter==null){
			filter = new FilterInfo();
		}
		view.setFilter(filter);
		// 过滤当前财务组织及下级组织对应的工程项目
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", new HashSet(companyOrgUnitMap.keySet()), CompareType.INCLUDE));
		prmtProject.setEntityViewInfo(view);
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(
				formattedTextField);
		tblMain.getColumn("targetCost").setEditor(numberEditor);
		FDCHelper.formatTableNumber(tblMain, "targetCost");
		FDCHelper.formatTableNumber(tblMain, "totalPayAmt");
		this.tblMain.addKDTEditListener(new KDTEditListener() {

			public void editCanceled(KDTEditEvent e) {

			}

			public void editStarted(KDTEditEvent e) {

			}

			public void editStarting(KDTEditEvent e) {

			}

			public void editStopped(KDTEditEvent e) {

			}

			public void editStopping(KDTEditEvent e) {
				KDTable tb = (KDTable) e.getSource();
				BigDecimal oldValue = FDCHelper.ZERO;
				BigDecimal newValue = FDCHelper.ZERO;
				if (e.getOldValue() != null) {
					oldValue = FDCHelper.toBigDecimal(e.getOldValue(), 2);
				}
				if (e.getValue() != null) {
					newValue = FDCHelper.toBigDecimal(e.getValue(), 2);
				}
				newValue = FDCHelper.subtract(newValue, oldValue);
				int colIndex = tb.getSelectManager().getActiveColumnIndex();
				int rowIndex = tb.getSelectManager().getActiveRowIndex();
				//指定列不执行操作！
				if (colIndex%3==0||colIndex<=3) return;
				
				int colDeduct =((colIndex/3)+1)*3;
				int gap = colDeduct-colIndex;
				
				BigDecimal value1 = new BigDecimal("0.00");
				BigDecimal value2 = new BigDecimal("0.00");
				BigDecimal result = null;
				if(gap==1)
				{	
					Object cellValue1= tb.getCell(rowIndex, colIndex).getValue();
					if (cellValue1!=null
							&&cellValue1 instanceof BigDecimal)
					{
						value1=(BigDecimal)cellValue1;
					}
					Object cellValue2= tb.getCell(rowIndex, colIndex).getValue();
					if (cellValue2!=null
							&&cellValue2 instanceof BigDecimal)
					{
						value2=(BigDecimal)cellValue2;
					}
				}else if(gap==2)
				{
					Object cellValue1= tb.getCell(rowIndex, colIndex+1).getValue();
					if (cellValue1!=null
							&&cellValue1 instanceof BigDecimal)
					{
						value1=(BigDecimal)cellValue1;
					}
					Object cellValue2= tb.getCell(rowIndex, colIndex).getValue();
					if (cellValue2!=null
							&&cellValue2 instanceof BigDecimal)
					{
						value2=(BigDecimal)cellValue2;
					}					
				}
				
				result=value1.subtract(value2);
				tb.getCell(rowIndex, colDeduct).setValue(result);
			}

			public void editValueChanged(KDTEditEvent e) {
				// TODO Auto-generated method stub

			}
		});	
		this.btnSplit.setText(FDCAimcostClientHelper.getRes("showAll"));
	}

	/**
	 *  初始化界面上方的起始年日控件
	 *  @Added By Owen_wen 2010-11-10
	 */
	private void initControl() {
		Calendar curCal = Calendar.getInstance();
		int year = curCal.get(Calendar.YEAR);
		int month = curCal.get(Calendar.MONTH);

		SpinnerNumberModel yearMo = new SpinnerNumberModel(year, 1990, 2099, 1);
		this.spiStartYear.setModel(yearMo);
		SpinnerNumberModel monthMo = new SpinnerNumberModel(month + 1, 1, 12, 1);
		this.spiStartMon.setModel(monthMo);
		
		yearMo = new SpinnerNumberModel(year, 1990, 2099, 1);
		this.spiEndYear.setModel(yearMo);
		monthMo = new SpinnerNumberModel(month + 1, 1, 12, 1);
		this.spiEndMon.setModel(monthMo);
	}

	/**
	 * 初始化表格的若干属性：取数模式、是否锁定、融合
	 * @Added By Owen_wen 2010-11-10
	 */
	private void initTable() {
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getHeadMergeManager().mergeBlock(0,	0, 1, tblMain.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
	}
	
	
	private Map getCostMap(Map param) throws BOSException {
		return FDCCostRptFacadeFactory.getRemoteInstance().getCostMap(param);
	}
	
	
	/**
	 * 工程项目明细科目长编码
	 */
	private Set costAccNumberSet;

	private Set getleafCostAccountNumber(String prjID) throws BOSException {
		costAccNumberSet = new HashSet();
		Set set = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("longnumber");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getFilter().getFilterItems().add(new FilterItemInfo("curProject.id", prjID));
		view.getFilter().getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		CostAccountCollection costAccountCollection = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		for (int i = 0; i < costAccountCollection.size(); i++) {
			set.add(costAccountCollection.get(i).getLongNumber());
		}
		return set;
	}

	/**
	 * 描述：如果项目分期中科目在非明细项目中不存在，则把该科目的值加到上级科目中
	 * @param aimCostMap 
	 * @param bigDecimal 
	 * @Author：keyan_zhao
	 * @CreateTime：2012-11-28
	 */
	private void setMapUpCostAccount(String longNumber, Map map, BigDecimal bigDecimal) {
		if (longNumber == null) {
			return;
		}
		for (Iterator ite = costAccNumberSet.iterator(); ite.hasNext();) {
			String lNumber = (String) ite.next();
			if (longNumber.startsWith(lNumber)) {
				if (map.containsKey(lNumber)) {
					Object object = map.get(lNumber);
					map.put(lNumber, FDCHelper.add(object, bigDecimal));
				} else {
					map.put(lNumber, bigDecimal);
				}
				break;
			}
		}
	}

	private String SetConvertToString(Set idSet) {

		if (idSet == null || idSet.isEmpty()) {
			return "";
		}
		StringBuffer filter = new StringBuffer();
		filter.append("( ");
		Iterator iter = idSet.iterator();
		int i = 0;
		int size = idSet.size();
		while (iter.hasNext()) {
			String id = (String) iter.next();
			filter.append("'").append(id).append("'");
			if (i < size - 1) {
				filter.append(",");
			}
			i++;
		}
		filter.append(" ) ");
		return filter.toString();
	}

	private Map getAcctAimCostData(Set prjIDs) throws BOSException, SQLException, EASBizException {
		Map aimCostMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if (FDCUtils.getDefaultFDCParamByKey(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString(),
				FDCConstants.FDC_PARAM_AIMCOSTAUDIT)) {
			builder.appendSql("select acc.flongnumber as acctid,sum(entry.fcostamount) as amount from T_AIM_AimCost head "
					+ "	inner join T_AIM_CostEntry entry on entry.fheadid=head.fid "
					+ " inner join t_fdc_costaccount acc on acc.fid = entry.fcostaccountid" + "	where head.fisLastVersion=1  ");
			builder.appendSql("and head.fstate=? ");
			builder.appendSql("and head.ForgOrProId in " + SetConvertToString(prjIDs));
			builder.appendSql("	group by acc.flongnumber ");
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		} else {
			builder.appendSql("select acc.flongnumber as acctid,sum(entry.fcostamount) as amount from T_AIM_AimCost head "
					+ "	inner join T_AIM_CostEntry entry on entry.fheadid=head.fid "
					+ " inner join t_fdc_costaccount acc on acc.fid = entry.fcostaccountid" + "	where head.fisLastVersion=1  ");
			builder.appendSql("and head.ForgOrProId in " + SetConvertToString(prjIDs));
			builder.appendSql("	group by acc.flongnumber ");
		}

		final IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			String longNumber = rowSet.getString("acctid");
			if (costAccNumberSet.contains(longNumber)) {
				if (aimCostMap.containsKey(longNumber)) {
					Object object = aimCostMap.get(longNumber);
					aimCostMap.put(longNumber, FDCHelper.add(object, rowSet.getBigDecimal("amount")));
				} else {
					aimCostMap.put(longNumber, rowSet.getBigDecimal("amount"));
				}
			} else {
				setMapUpCostAccount(longNumber, aimCostMap, rowSet.getBigDecimal("amount"));
			}
		}

		return aimCostMap;
	}

	/**
	 * 描述：获取项目及分期id
	 * @throws BOSException 
	 * @Author：keyan_zhao
	 * @CreateTime：2012-11-28
	 */
	private Set getCurProjectIds(String prjID) throws BOSException {
		Set set = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getFilter().getFilterItems().add(new FilterItemInfo("parent.id", prjID));
		view.getFilter().getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.getFilter().getFilterItems().add(new FilterItemInfo("projectStatus.id", ProjectStatusInfo.flowID, CompareType.NOTEQUALS));
		view.getFilter().getFilterItems().add(new FilterItemInfo("projectStatus.id", ProjectStatusInfo.closeID, CompareType.NOTEQUALS));
		CurProjectCollection curProjectCollection = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);
		for (int i = 0; i < curProjectCollection.size(); i++) {
			set.add(curProjectCollection.get(i).getId().toString());
		}
		set.add(prjID);
		return set;
	}

	/**
	 * 根据项目获得科目付款拆分金额累计
	 * 
	 * @author tim_gao
	 * @throws SQLException 
	 */
	private Map getPaySplitEntData(Set set) throws BOSException, SQLException {
		Map paySplitEntDat = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select acc.flongnumber as costAccId,sum(entry.FPayedAmt) as paySplitEntAmt from  T_FNC_PaymentSplit ps ");
		
		/* modified by zhaoqin for R140404-0117 on 2014/05/06 start */
		//builder.appendSql(" left join T_FNC_PaymentSplitEntry entry on entry.FParentID = ps.fid  ");
		builder.appendSql(" join T_FNC_PaymentSplitEntry entry on entry.FParentID = ps.fid  ");
		//builder.appendSql(" left join t_fdc_costaccount acc on acc.fid=entry.fcostAccountid ");
		builder.appendSql(" join t_fdc_costaccount acc on acc.fid=entry.fcostAccountid ");
		builder.appendSql(" where  entry.fcostAccountid is not null ");
		builder.appendSql(" and entry.FProductID is null ");
		/* modified by zhaoqin for R140404-0117 on 2014/05/06 end */
		
		builder.appendSql(" and  ps.fcurProjectid in " + SetConvertToString(set));
		builder.appendSql(" and  acc.fcurProject in " + SetConvertToString(set));
		builder.appendSql(" group by  acc.flongnumber");
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {

			String longNumber = rowSet.getString("costAccId");
			if (costAccNumberSet.contains(longNumber)) {
				if (paySplitEntDat.containsKey(longNumber)) {
					Object object = paySplitEntDat.get(longNumber);
					paySplitEntDat.put(longNumber, FDCHelper.add(object, rowSet.getBigDecimal("paySplitEntAmt")));
				} else {
					paySplitEntDat.put(longNumber, rowSet.getBigDecimal("paySplitEntAmt"));
				}
			} else {
				setMapUpCostAccount(longNumber, paySplitEntDat, rowSet.getBigDecimal("paySplitEntAmt"));
			}
		}

		return paySplitEntDat;

	}

	/**
	 * 用数据填充表格
	 * @Added By Owen_wen 2010-11-10
	 * @param table 待填充的表格
	 * @throws Exception
	 */
	private void fillTable(KDTable table) throws Exception {
		table.removeRows();
		removeColumns(table);
		
		
		costAccNumberSet = getleafCostAccountNumber(curProject.getId().toString());
		Map param = new HashMap();
		Set prjIDs = new HashSet();
		if (curProject.isIsLeaf()) {
			prjIDs.add(curProject.getId().toString());
		} else {
			prjIDs = getCurProjectIds(curProject.getId().toString());
		}
		costAccout = getAcctAimCostData(prjIDs);
		// by tim_gao
		paySplitEntAmt = getPaySplitEntData(prjIDs);
		
		acctStageMap=AccountStageHelper.initAccoutStageMap(null,curProject.getId().toString(), null);
		Integer startYear = (Integer) spiStartYear.getValue();
		if (startYear.intValue() == 0) {
			return;
		}
		Integer endYear = (Integer) spiEndYear.getValue();
		if (endYear.intValue() == 0) {
			return;
		}
		Integer startMonth = (Integer) spiStartMon.getValue();
		Integer endMonth = (Integer) spiEndMon.getValue();

		//		Set prjIds = new HashSet();
		//		prjIds.add(curProject.getId().toString());
		param = new HashMap();
		param.put("startYear", startYear);
		param.put("endYear", endYear);
		param.put("startMonth", startMonth);
		param.put("endMonth", endMonth);
		param.put("proID", curProject.getId().toString());
		initData = FDCDepConPayPlanFacadeFactory.getRemoteInstance().getProjectPayPlanExeData(prjIDs, param);
		
		
		// 计算月数,删除列,增加列
		int months = calMonths(startYear.intValue(), startMonth.intValue(),
				endYear.intValue(), endMonth.intValue());
		addColumn(months);
		
		FilterInfo acctFilter = new FilterInfo();
		acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));
		acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		TreeModel costAcctTree = null;
		try {
			// 根据当前指定项目条件构造科目树
			costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory
					.getRemoteInstance(), acctFilter);
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
					.getRoot();
			Enumeration childrens = root.depthFirstEnumeration();
			int maxLevel = 0;
			while (childrens.hasMoreElements()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
						.nextElement();
				if (node.getUserObject() != null && node.getLevel() > maxLevel) {
					maxLevel = node.getLevel();
				}
			}
			table.getTreeColumn().setDepth(maxLevel);
			for (int i = 0; i < root.getChildCount(); i++) {
				fillNode((DefaultMutableTreeNode) root.getChildAt(i), table);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		tblMain.getHeadMergeManager().mergeBlock(0,	0, 1, tblMain.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
		setUnionData(table);
	}

	private void fillNode(DefaultMutableTreeNode node, KDTable table)
			throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}

		String acctId = costAcct.getId().toString();
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("id").setValue(acctId);
		String longNumber = costAcct.getLongNumber();
		row.getCell("acctNumber").setValue(
				costAcct.getLongNumber().replace('!', '.'));
		row.getCell("acctNumber").setUserObject(costAcct);
		row.getCell("acctName").setValue(costAcct.getName());
		// by tim_gao 累计金额字段
		row.getCell("totalPayAmt").setValue(
				null != paySplitEntAmt.get(longNumber) ? FDCHelper.toBigDecimal(paySplitEntAmt.get(longNumber)) : null);
		row.getCell("targetCost").setValue(costAccout.get(longNumber));
		
		if (node.isLeaf()) {
			row.setUserObject(costAcct);
			updateRowData(table, longNumber, row);
			setRowColor(row, new Color(0xF0EDD9));
		 } else {
			 updateRowData(table, acctId, row);
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode((DefaultMutableTreeNode) node.getChildAt(i), table);
			}
		}
	}

	private void updateRowData(KDTable table, String longNumber, IRow row) {
			int colInex = tblMain.getColumnIndex("acctName");
			for(int i=colInex+1;i<table.getColumnCount();i++){
				String colKey = table.getColumnKey(i);
				if("targetCost".equals(colKey)){
					continue;
				}
				if("totalPayAmt".equals(colKey)){
					continue;
				}
				if(colKey.startsWith("deduct")){
					BigDecimal pay = (BigDecimal) row.getCell(i - 2).getValue();
					BigDecimal plan = (BigDecimal) row.getCell(i - 1)
							.getValue();
					if(pay==null&&plan==null){
						continue;
					}
					pay = pay == null ? FDCHelper.ZERO : pay;
					plan = plan == null ? FDCHelper.ZERO : plan;
					row.getCell(i).setValue(pay.subtract(plan));
				} else {
					String key = longNumber + colKey;
					row.getCell(colKey).setValue(initData.get(key));
				}
			}
			setRowColor(row, new Color(0xF0EDD9));			
	}
	
	private void setUnionData(KDTable table){
		List amountColumns = new ArrayList();
		int colInex = tblMain.getColumnIndex("acctName");
		for (int j = colInex+1; j < table.getColumnCount(); j++) {
			amountColumns.add(tblMain.getColumn(j).getKey());
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() == null) {// 非叶结点
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}else if (rowAfter.getCell("acctNumber").getUserObject() != null) {
						//成本科目多级改造
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					// 获取原有科目金额
					BigDecimal amounts = (BigDecimal) row.getCell(colName).getValue();
					BigDecimal amount = FDCHelper.ZERO;
					boolean hasData = false;
					boolean isStageLeaf = true;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						//不存在的阶段科目，不统计
						Object obj = row.getCell("acctNumber").getUserObject();
						if(obj!=null){
							CostAccountInfo info = (CostAccountInfo)obj;
							if(!acctStageMap.containsKey(info.getId().toString())){
								isStageLeaf = false;
							}
						}
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if (hasData) {
						// 将统计金额加上自己原有的金额
						amount = FDCHelper.add(amount, amounts); 
						row.getCell(colName).setValue(amount);
					} else {
						if(!isStageLeaf){
							amount = FDCHelper.add(row.getCell(colName).getValue(), amount);
							if(FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO)!=0){
								// 将统计金额加上自己原有的金额
								amount = FDCHelper.add(amount, amounts); 
								row.getCell(colName).setValue(amount);
							}
						}
					}
				}
			}
		}
		if(amountColumns.size()>0){
			String[] columns=new String[amountColumns.size()];
			amountColumns.toArray(columns);
			try{
				AimCostClientHelper.setTotalCostRow(table, columns);
			}catch(Exception e){
				handUIException(e);
			}
		}
		
	}
	
	private void setRowColor(IRow row, Color color) {
		row.getStyleAttributes().setBackground(color);
	}
	
	private void clear() {
		if (initData != null) {
			initData.clear();
		}
		if(companyOrgUnitMap!=null){
			companyOrgUnitMap.clear();
		}
		initData = null;
		companyOrgUnitMap=null;
	}

	public boolean destroyWindow() {
		boolean value = super.destroyWindow();
		clear();
		return value;
	}

	protected void prmtProject_dataChanged(DataChangeEvent e) throws Exception {
		if (e.getNewValue() == null) {
			return;
		}
		CurProjectInfo info = (CurProjectInfo) e.getNewValue();
		if (curProject != null && curProject.getId().toString().equals(info.getId().toString())) {
			return;
		}
		curProject = info;
		tblMain.removeRows();
		removeColumns(tblMain);
	}

	protected void prmtProject_willShow(SelectorEvent e) throws Exception {
		super.prmtProject_willShow(e);
	}
	
	protected void spiStartYear_stateChanged(ChangeEvent e) throws Exception {
		super.spiStartYear_stateChanged(e);
		tblMain.removeRows();
		removeColumns(tblMain);
	}
	
	protected void spiStartMon_stateChanged(ChangeEvent e) throws Exception {
		super.spiStartMon_stateChanged(e);
		tblMain.removeRows();
		removeColumns(tblMain);
	}
	
	protected void spiEndYear_stateChanged(ChangeEvent e) throws Exception {
		super.spiEndYear_stateChanged(e);
		tblMain.removeRows();
		removeColumns(tblMain);
	}
	
	protected void spiEndMon_stateChanged(ChangeEvent e) throws Exception {
		super.spiEndMon_stateChanged(e);
		tblMain.removeRows();
		removeColumns(tblMain);
	}

	private int getValue(KDSpinner spn) {
		return ((Integer) spn.getValue()).intValue();
	}

	private void removeListiner(KDSpinner spn, EventListener[] listeners) {
		for (int i = 0; i < listeners.length; i++) {
			spn.removeChangeListener((ChangeListener) listeners[i]);
		}
	}

	private void addListiner(KDSpinner spn, EventListener[] listeners) {
		for (int i = 0; i < listeners.length; i++) {
			spn.addChangeListener((ChangeListener) listeners[i]);
		}
	}
	
	/**
	 * 计算动态列数
	 * @param startYear
	 * @param startMonth
	 * @param endYear
	 * @param endMonth
	 * @return
	 */
	private int calMonths(int startYear, int startMonth, int endYear,
			int endMonth) {
		int months = 1;
		if (startYear == endYear) {
			months = endMonth - startMonth + 1;
		} else {
			months = 12 - startMonth + 1;
			for (int i = startYear + 1; i < endYear; i++) {
				months += 12;
			}
			months += endMonth;
		}

		return months;
	}

	
	/**
	 * 添加动态列
	 * @param months
	 */
	private void addColumn(int months) {

		if (months == 0)
			return;
		dymicCols = months;
		Integer startYear = (Integer) this.spiStartYear.getValue();
		Integer startMonth = (Integer) this.spiStartMon.getValue();
		// 增加列
		int colInex = tblMain.getColumnIndex("totalPayAmt");
		String yearText = "年";
		String monText = "月";
		String plan = "计划支付金额";
		String pay = "实际支付金额";
		String deduct  = "偏差(实际-计划)";
		for (int i = 1; i < dymicCols * 3 + 1; i = i + 3) {
			String colName = String.valueOf(startYear) + yearText
			+ (startMonth.intValue() + ((i-1)/3)) + monText;
			String colKey = String.valueOf(startYear) +(startMonth.intValue() + ((i-1)/3));
			
			for (int j = 1; j < 6; j++) {
				if (startMonth.intValue() + ((i-1)/3)  > 12 * j) {
					int newStartYear = startYear.intValue() + 1 * j;
					colName = String.valueOf(newStartYear) + yearText
							+ (startMonth.intValue() + ((i-1)/3) - 12 * j) + monText;
					colKey = String.valueOf(newStartYear)
							+ (startMonth.intValue() + ((i-1)/3) - 12 * j);
				}
			}

			IColumn col = tblMain.addColumn(colInex + i);
			String plankey = "plan" + colKey;
			col.setKey(plankey);
			String splitKey = "split" + colKey;
			col = tblMain.addColumn(colInex + i + 1);
			col.setKey(splitKey);
			String deductKey = "deduct" + colKey;
			col = tblMain.addColumn(colInex + i + 2);
			col.setKey(deductKey);
			
			tblMain.getHeadRow(0).getCell("plan"+colKey).setValue(colName);
			tblMain.getHeadRow(0).getCell("split"+colKey).setValue(colName);
			tblMain.getHeadRow(0).getCell("deduct"+colKey).setValue(colName);
			
			tblMain.getHeadRow(1).getCell("plan"+colKey).setValue(plan);
			tblMain.getHeadRow(1).getCell("split"+colKey).setValue(pay);
			tblMain.getHeadRow(1).getCell("deduct"+colKey).setValue(deduct);

			KDFormattedTextField formattedTextField = new KDFormattedTextField(
					KDFormattedTextField.DECIMAL);
			formattedTextField.setPrecision(2);
			formattedTextField.setSupportedEmpty(true);
			KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(
					formattedTextField);
			tblMain.getColumn(plankey).setEditor(numberEditor);
			tblMain.getColumn(splitKey).setEditor(numberEditor);
			tblMain.getColumn(deductKey).setEditor(numberEditor);
			FDCHelper.formatTableNumber(tblMain, plankey);
			FDCHelper.formatTableNumber(tblMain, splitKey);
			FDCHelper.formatTableNumber(tblMain, deductKey);
		}
	}
	
	/**
	 * 删除表格列，从第4列开始删除，即列索引号从3开始
	 * @Added By Owen_wen 2010-11-10
	 * @param table
	 */
	private void removeColumns(KDTable table){
		// for(int i=table.getColumnCount()-1; i>=4; i--){
		// String key = table.getColumnKey(i);
		//if(key!=null&&(key.equals("id")||key.equals("acctNumber")||key.equals(
		// "acctName"))){
		// continue;
		// }
		// table.removeColumn(table.getColumnIndex(key));
		// }
		// by tim_ago
		for (int i = table.getColumnCount() - 1; i >= 5; i--) {
			String key = table.getColumnKey(i);
			if (key != null
					&& (key.equals("id") || key.equals("totalPayAmt")
							|| key.equals("acctNumber") || key
							.equals("acctName"))) {
				continue;
			}
			table.removeColumn(table.getColumnIndex(key));
		}
	}
	/**
	 * 新增显示条件按钮
	 * 
	 * @author 向晓帆
	 */
	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		if (curProject != null) {
				// 当前按钮显示内容
			String txt = this.btnSplit.getText();
			if (txt.equals(FDCAimcostClientHelper.getRes("showSplit"))) {
				this.btnSplit.setText(FDCAimcostClientHelper.getRes("showAll"));
				filterSplitData();// 过滤可拆分科目
			} else {
				this.btnSplit.setText(FDCAimcostClientHelper.getRes("showSplit"));
				this.fillTable(tblMain);
			}
		} else {
			MsgBox.showWarning("工程项目不能为空，请选择工程项目。");
		}
	}

	/**
	 * 过滤可拆分科目
	 * 
	 * @description
	 * @author 何鹏
	 * @createDate 2011-9-19
	 * @version EAS7.0
	 * @see
	 */
	
	private void filterSplitData() {
		int rowCount = tblMain.getRowCount();
		CostAccountInfo info = null;
		String tmpNumber = null;
		String curNumber = null;
		IRow row = null;
		// 循环遍历删出不可拆分的科目
		for (int i = rowCount; i > 0; i--) {
			row = tblMain.getRow(i);
			if (row != null) {
				// 得到当前行成本科目对象
				info = (CostAccountInfo) row.getCell("acctNumber").getUserObject();
				curNumber = row.getCell("acctNumber").getValue().toString();
				tmpNumber = curNumber.replace('.', '!');
				if (info.getLongNumber().equals(tmpNumber) && !info.isIsSplit()) {
					tblMain.removeRow(i);
				}
			}
		}
	}
}