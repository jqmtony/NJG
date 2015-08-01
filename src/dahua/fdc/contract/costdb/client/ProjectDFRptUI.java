/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DanFangTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;

public class ProjectDFRptUI extends AbstractProjectDFRptUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ProjectDFRptUI.class);

	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	FDCCustomerParams para = new FDCCustomerParams();
	
	private Map acctMap = new HashMap();
	
	Map projectIndexMap;
	
	String apportionTypeID;

	Map fullProjAmtMap;
	Map prodMap;
	/**
	 * output class constructor
	 */
	public ProjectDFRptUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
//		super.tblMain_tableClicked(e);
		
		if(e.getClickCount() == 2) {
			int rowIndex = e.getRowIndex();
			int colIndex = e.getColIndex();
			
			ICell cell = getMainTable().getCell(rowIndex, colIndex);
			if(cell==null||cell.getValue() == null) return;
			
			Object value = getMainTable().getCell(rowIndex, "id").getValue();
			String id = (String)value;
			CostAccountInfo acctInfo = (CostAccountInfo)acctMap.get(id);
			
			boolean b = acctInfo.isIsLeaf();
			if(!b) return;
			
			String key = getMainTable().getColumn(colIndex).getKey();
			
			if(key.indexOf("_") == -1) return;
			
			
			String longNumber = acctInfo.getLongNumber();
			
			String[] keys = key.split("_");
			
			UIContext uiContext = new UIContext(this);
			uiContext.put("projId", keys[0]);
			uiContext.put("productTypeId", keys[1]);
			uiContext.put("acctLNum", longNumber);
			IUIWindow window2 = UIFactory.createUIFactory().create(ProjectDFRptAimCostInfoUI.class.getName(), uiContext, null, OprtState.VIEW);
			window2.show();
			
			
			
		}
	}


	// 覆盖此方法,屏蔽回车键产生异常中断 add by jianxing_zhou 2007-8-24
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		
	}
	
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowFilter(false);
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setUiObject(null);
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ProjectDFFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	public void onLoad() throws Exception {
		FDCClientUtils.checkCurrentCostCenterOrg(this);
		tblMain.checkParsed();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		FDCTableHelper.disableExportByPerimission(this, this.getTableForCommon(), "ActionPrint");
//		actionQuery_actionPerformed(null);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionAntiAudit.setVisible(false);
		actionView.setVisible(false);
		actionView.setEnabled(false);
		fillAcct(getMainTable());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int acctNameIndex=tblMain.getColumn("name").getColumnIndex()+1;
				tblMain.getViewManager().freeze(0, acctNameIndex);
		}});
		
		//添加导致中断的快捷键
		//已在元数据屏蔽了
	}

	/*
	 * 科目树构造
	 */
	private void initAcct(FilterInfo acctFilter) throws BOSException {
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sel.add(new SelectorItemInfo("curProject.longNumber"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit.longNumber"));
		sel.add(new SelectorItemInfo("isLeaf"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
		CostAccountCollection accts = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		for (int i = 0; i < accts.size(); i++) {
			CostAccountInfo info = accts.get(i);
			this.acctMap.put(info.getId().toString(), info);
		}
	}
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}
	private void setTableHeader() {
		getMainTable().checkParsed(true);
		FDCHelper.formatTableNumber(getMainTable(), "avg");
		getMainTable().addHeadRow();
		
		getMainTable().getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		getMainTable().getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		getMainTable().getHeadMergeManager().mergeBlock(0, 3, 1, 3);
		
		String[] projectIds = getParas().getStringArray(
				ProjectDFFilterUI.PROJECT_IDS);

		String[] productTypeIds = getParas().getStringArray(ProjectDFFilterUI.PRODUCT_TYPE_IDS);
		boolean chkProject=getParas().getBoolean(ProjectDFFilterUI.CHKWHOLEPROJECT);
		Set productTypeSet = null;
		if(productTypeIds != null && productTypeIds.length > 0) {
			productTypeSet = FDCHelper.getSetByArray(productTypeIds);
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.getSetByArray(projectIds), CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("curProjProductEntries.productType.id");
		view.getSelector().add("curProjProductEntries.productType.name");
		view.getSelector().add("curProjProductEntries.productType.number");
		view.getSorter().add(new SorterItemInfo("longNumber"));
//		view.getSorter().add(new SorterItemInfo("curProjProductEntries.productType.number"));
		CurProjectCollection projectCollection = null;
		try {
			projectCollection = CurProjectFactory
					.getRemoteInstance().getCurProjectCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if (projectCollection != null && projectCollection.size() > 0) {
			for (Iterator projectColl = projectCollection.iterator(); projectColl.hasNext();) {
				CurProjectInfo info = (CurProjectInfo) projectColl.next();
				IColumn column = getMainTable().addColumn();
				
				
				String projId = info.getId().toString();
				column.setKey(projId);
				FDCHelper.formatTableNumber(getMainTable(), projId);
				int colIdx = column.getColumnIndex();
				getMainTable().getHeadRow(0).getCell(colIdx).setValue(info.getName());
				getMainTable().getHeadRow(1).getCell(colIdx).setValue("全项目");
				
				//增加表头“编制时间”
				column = getMainTable().addColumn();
				colIdx = column.getColumnIndex();
				getMainTable().getHeadRow(0).getCell(colIdx).setValue(info.getName());
				getMainTable().getHeadRow(1).getCell(colIdx).setValue("编制时间");
				column.setKey(projId+"createTime");
				
				CurProjProductEntriesCollection curProjProductEntries = info.getCurProjProductEntries();
//				curProjProductEntries.reOrder(newIndexes);
				int size = 1;
				FDCHelper.sortObjectCollection(curProjProductEntries, getComparator());
				for (Iterator iter = curProjProductEntries.iterator(); iter.hasNext();) {
					CurProjProductEntriesInfo entryInfo = (CurProjProductEntriesInfo) iter.next();
					String productTypeId = entryInfo.getProductType().getId().toString();
					String productTypeName = entryInfo.getProductType().getName();
					String key = projId + "_" + productTypeId;
					if(chkProject){
						continue;
					}
					if(productTypeSet != null) {
						if(productTypeSet.contains(productTypeId)) {
							column = getMainTable().addColumn();
							column.setKey(key);
//							System.out.print("number:"+entryInfo.getProductType().getNumber());
//							System.out.println("\tname:"+entryInfo.getProductType().getName());
							getMainTable().getHeadRow(1).getCell(column.getColumnIndex()).setValue(productTypeName);
							++size;
						}
						else {
							continue;
						}
						FDCHelper.formatTableNumber(getMainTable(), key);
					}else{
						//包含所有产品
						column = getMainTable().addColumn();
						column.setKey(key);
						getMainTable().getHeadRow(1).getCell(column.getColumnIndex()).setValue(productTypeName);
						++size;
					}
/*	
 * 			不选产品时默认显示项目数据，而不是产品 by sxhong 2007-11-26
					else {
						column = getMainTable().addColumn();
						column.setKey(key);
						getMainTable().getHeadRow(1).getCell(column.getColumnIndex()).setValue(productTypeName);
						++size;
					}
*/
				}
				
				getMainTable().getHeadMergeManager().mergeBlock(0, colIdx-1, 0, colIdx + size-1);
			}
		}

	}
	
	protected void execQuery() {
//		super.execQuery();
		CustomerParams customerParams = getFilterUI().getCustomerParams();
		para = new FDCCustomerParams(customerParams);
		
		Date startDate = (Date)para.getDate(ProjectDFFilterUI.START_DATE);
		Date endDate   = (Date)para.getDate(ProjectDFFilterUI.END_DATE);
		
		if(getParas().getStringArray(ProjectDFFilterUI.PROJECT_IDS)==null&&mainQuery!=null&&mainQuery.getFilter()!=null){
			//使用默认的分摊方案
			para=new FDCCustomerParams();
			boolean start = true;
			
			FilterInfo filter=mainQuery.getFilter();
			for(Iterator iter=filter.getFilterItems().iterator();iter.hasNext();){
				FilterItemInfo item=(FilterItemInfo)iter.next();
				
				/**
				 * 6.1查询方案做了优化，对于条件为 = 与 like 的参数增加了转化为大写函数,
				 * 通过查询方案进入时，如原getPropertyName()取值为name则现在为UPPER(name)函数未取掉，经与BOS人员沟通，暂特殊处理
				 */
				String property = item.getPropertyName();
				if(property.startsWith("UPPER")){
					property = property.substring(6, property.length()-1);
					item.setPropertyName(property);
				}
				
				if(property.equals(ProjectDFFilterUI.PROJECT_IDS)){
					if(item.getCompareValue() instanceof Set){
						Set set = (Set)item.getCompareValue();
						String s[]=new String[set.size()];
						set.toArray(s);
						para.add(ProjectDFFilterUI.PROJECT_IDS, s);
					}
				}
				if(property.equals(ProjectDFFilterUI.PRODUCT_TYPE_IDS)){
					if(item.getCompareValue() instanceof Set){
						Set set = (Set)item.getCompareValue();
						String s[]=new String[set.size()];
						set.toArray(s);
						para.add(ProjectDFFilterUI.PRODUCT_TYPE_IDS, s);
					}
				}
				
				if(property.equals(ProjectDFFilterUI.DF_TYPE_VALUE)){
					if(item.getCompareValue() instanceof String){
						para.add(ProjectDFFilterUI.DF_TYPE_VALUE, (String)item.getCompareValue());
					}
				}
				
				if(property.equals(ProjectDFFilterUI.DF_TYPE_NAME)){
					if(item.getCompareValue() instanceof String){
						para.add(ProjectDFFilterUI.DF_TYPE_NAME, (String)item.getCompareValue());
					}
				}
				
				if(item.getPropertyName().equals(ProjectDFFilterUI.PROJECT_TYPE_NAMES)){
					if(item.getCompareValue() instanceof Set){
						Set set = (Set)item.getCompareValue();
						String s[]=new String[set.size()];
						set.toArray(s);
						para.add(ProjectDFFilterUI.PROJECT_TYPE_NAMES, s);
					}
				}
				if(item.getPropertyName().equals(ProjectDFFilterUI.CHKWHOLEPROJECT)){
					if(item.getCompareValue() instanceof String){
						para.add(ProjectDFFilterUI.CHKWHOLEPROJECT, (Boolean.valueOf((String)item.getCompareValue())).booleanValue());
					}
				}
			
				/**
				 * 描述：获取默认方案日期，一般情况第一次取值为开始日期，为防万一做了比较处理。
				 * @author pengwei_hou add by Date: 2008-11-23
				 */
				if(item.getPropertyName().equals("createTime")){
					if (item.getCompareValue() instanceof Date) {
						if (start) {
							startDate = (Date)item.getCompareValue();
//							para.add(ProjectDFFilterUI.START_DATE, (Date) item.getCompareValue());
							start = false;
						}else{
							endDate = (Date)item.getCompareValue();
//							para.add(ProjectDFFilterUI.END_DATE, (Date) item.getCompareValue());
						}
						if(startDate.after(endDate)){
							Date temp;
							temp = startDate;
							startDate = endDate;
							endDate = temp;
						}
					}
					
				}
			}
		}
		setTitle();
		setTableHeader();
		String[] projIds = getParas().getStringArray(ProjectDFFilterUI.PROJECT_IDS);
		if(projIds==null) return;
		fullProjAmtMap = ProjectDFRptHelper.createFullProjMap(projIds, startDate, endDate);
		String dfTypeValue = getParas().getString(ProjectDFFilterUI.DF_TYPE_VALUE);
		if(dfTypeValue!=null && dfTypeValue.equals(DanFangTypeEnum.BUILD_VALUE)) {
			apportionTypeID = FDCConstants.BUILD_AREA_ID;
		}
		else {
			apportionTypeID = FDCConstants.SALE_AREA_ID;
		}
//		String[] apportionTypeIds=new String[]{apportionTypeID};
		boolean chkProject=getParas().getBoolean(ProjectDFFilterUI.CHKWHOLEPROJECT);
		if(chkProject){
			prodMap=new  HashMap();
		}else{
			prodMap=ProjectDFRptHelper.createProductMap(projIds, startDate, endDate);
		}
		try {
			projectIndexMap = ProjectHelper.getIndexValueByProjProd(null, projIds, ProjectStageEnum.AIMCOST);
		} catch (BOSException e) {
			handUIException(e);
		}

		fillAcct(getMainTable());
		
	}
	
	private FDCCustomerParams getParas() {
		return para; 
	}
	
	private void setTitle() {
		FDCCustomerParams para = getParas();
		String[] projectTypeNames=para.getStringArray(ProjectDFFilterUI.PROJECT_TYPE_NAMES);
		String projectTypeName="";
		if(!FDCHelper.isEmpty(projectTypeNames)){
			for(int i=0;i<projectTypeNames.length;i++){
				projectTypeName+="|"+projectTypeNames[i];
			}
			if(!FDCHelper.isEmpty(projectTypeName)){
				projectTypeName=projectTypeName.substring(1);
			}
		}
		String dfTypeName = para.get(ProjectDFFilterUI.DF_TYPE_NAME);
		String title = projectTypeName+ "   " + dfTypeName;
		
		lblTitle.setText(title);
	}
	
	private void fillAcct(KDTable table) {
		table.removeRows(false);
		FilterInfo acctFilter = new FilterInfo();
		acctFilter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit.id", OrgConstants.DEF_CU_ID));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnterDB", new Integer(1)));
		TreeModel costAcctTree = null;
		try {
			// 根据当前指定项目条件构造科目树
			costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory
					.getRemoteInstance(), acctFilter);
			 this.initAcct(acctFilter);

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
		
		setUnionData(table);
		
	}

	private void fillNode(DefaultMutableTreeNode node, KDTable table) throws BOSException,
			SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		
		String acctId = costAcct.getId().toString();
		String longNumber = costAcct.getLongNumber();
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("id").setValue(acctId);
		row.getCell("number").setValue(
				costAcct.getLongNumber().replace('!', '.'));
		row.getCell("name").setValue(costAcct.getName());
		int columnCount = table.getColumnCount();
		if(node.isLeaf()){
	//		各产品类型金额
//			Map prodMap = ProjectDFRptHelper.createProductMap(getParas().getStringArray(ProjectDFFilterUI.PROJECT_IDS), longNumber);
			
			row.setUserObject(costAcct);
			//填充金额
			BigDecimal sumAmount=FDCHelper.ZERO;
			BigDecimal avgAmount=FDCHelper.ZERO;
			for (int i = 4; i < columnCount; i++) {
				String colKey = table.getColumn(i).getKey();
				String idxKey = colKey + "_" + apportionTypeID;
				if(colKey.indexOf("_") == -1) { //全项目
					//在叶子结点 填充“编制时间”
					if(colKey.endsWith("createTime")){
						Date createDate = (Date)fullProjAmtMap.get(colKey);
						row.getCell(colKey).setValue(createDate);
						continue;
					}
					
					String key = colKey + "_" + longNumber;
					BigDecimal amount = (BigDecimal)fullProjAmtMap.get(key);
					
					//				计算单方
					BigDecimal indexValue = (BigDecimal)projectIndexMap.get(idxKey);
					if(amount == null||indexValue==null) continue;
					sumAmount=sumAmount.add(FDCHelper.toBigDecimal(amount));
					avgAmount=avgAmount.add(FDCHelper.toBigDecimal(indexValue));
					if(indexValue != null && indexValue.signum() > 0) {
						amount = amount.divide(indexValue,2,BigDecimal.ROUND_HALF_UP);
					}/*else{
						amount=FDCHelper.ZERO;
					}*/
					row.getCell(colKey).setValue(amount);
				}
				else {	//各产品类型
					BigDecimal amount = (BigDecimal)prodMap.get(colKey+"_"+longNumber);
					//计算单方
					BigDecimal indexValue = (BigDecimal)projectIndexMap.get(idxKey);
					
					if(amount == null||indexValue==null) continue;
					
					if(indexValue.signum() > 0) {
						amount = amount.divide(indexValue,2,BigDecimal.ROUND_HALF_UP);
					}/*else{
						amount=FDCHelper.ZERO;
					}*/
					row.getCell(colKey).setValue(amount);
					
				}
			}
			
			//设置平均列
			BigDecimal avg=FDCHelper.ZERO;
			if(avgAmount.compareTo(FDCHelper.ZERO)>0){
				avg=sumAmount.divide(avgAmount,2,BigDecimal.ROUND_HALF_UP);
			}
			if(avg.signum()==0){
				avg=null;
			}
			row.getCell("avg").setValue(avg);
		}else{
			//非叶子结点填充“编制时间”
			for (int i = 4; i < columnCount; i++) {
				String colKey = table.getColumn(i).getKey();
				if(colKey!=null&&colKey.endsWith("createTime")){
					Date createDate = (Date)fullProjAmtMap.get(colKey);
					row.getCell(colKey).setValue(createDate);
				}
			}
			
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode((DefaultMutableTreeNode) node.getChildAt(i), table);
			}
		}
	}
	
	public void setUnionData(KDTable table) {		
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		for (int j = 3; j < table.getColumnCount(); j++) {
			//过滤掉“编制时间”
			if(tblMain.getColumn(j).getKey().endsWith("createTime")){
				continue;
			}
			amountColumns.add(tblMain.getColumn(j).getKey());
		}
		if(amountColumns.size()<=0) return;
		
		for (int i = 0; i < table.getRowCount(); i++) {
	
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
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
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FMConstants.ZERO;
					boolean hasData = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
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
//						if(FDCHelper.toBigDecimal(amount).signum()==0){
//							amount=null;
//						}
						row.getCell(colName).setValue(amount);
					}
				}
			} else {
				// System.out.println(row.getCell(0).getValue());
				// System.out.println(((CostAccountInfo)row.getUserObject()).getName());
	
			}
	
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected boolean initDefaultFilter() {
		// TODO Auto-generated method stub
		return true;
	}
	private Comparator c=null;
	public Comparator getComparator(){
		 if(c==null){
			 return new Comparator(){
				 public int compare(Object o1, Object o2) {
					if(o1==null&&o2==null){
						return 0;
					}
					if(o1!=null&&o2==null){
						return 1;
					}
					if(o1==null&&o2!=null){
						return -1;
					}
					CurProjProductEntriesInfo info1 = (CurProjProductEntriesInfo) o1;
					CurProjProductEntriesInfo info2 = (CurProjProductEntriesInfo) o2;
					o1=info1.getProductType().getNumber();
					o2=info2.getProductType().getNumber();
					if(o1==null&&o2==null){
						return 0;
					}
					if(o1!=null&&o2==null){
						return 1;
					}
					if(o1==null&&o2!=null){
						return -1;
					}
					
					return ((String)o1).compareTo((String)o2);
				}
			 };
		 }
		 return c;
	}
}