/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ICurProjProductEntries;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class AimCostVersionCompareUI extends AbstractAimCostVersionCompareUI
{
    private static final Logger logger = CoreUIObject.getLogger(AimCostVersionCompareUI.class);
    
    /**
     * output class constructor
     */
    public AimCostVersionCompareUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
//        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
//        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
//        super.treeMain_valueChanged(e);
    	isFirstAdd = true;
        clear();
    }
	protected String getEditUIName() {
		return null;
	}
	/**
	 * @see com.kingdee.eas.fdc.aimcost.client.AbstractAimCostVersionCompareUI#prmtCompareVer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent)
	 */
	protected void prmtCompareVer_dataChanged(DataChangeEvent e) throws Exception {
		Object value = prmtCompareVer.getValue();
		super.prmtCompareVer_dataChanged(e);
	}
	public void onLoad() throws Exception {
		super.onLoad();
		SelectorItemCollection selectors=new SelectorItemCollection();
		selectors.add("*");
		prmtBaseVer.setCommitFormat("$versionNumber$");
		prmtBaseVer.setEditFormat("$versionNumber$");
		prmtBaseVer.setDisplayFormat("$versionNumber$_$versionName$");
		prmtBaseVer.setRequired(true);
		prmtBaseVer.setSelectorCollection(selectors);

		prmtCompareVer.setSelector(new AimCostPromptSelector(this));
		
		prmtCompareVer.setEnabledMultiSelection(false);
		prmtCompareVer.setCommitFormat("$versionNumber$");
		prmtCompareVer.setEditFormat("$versionNumber$");
		prmtCompareVer.setDisplayFormat("$versionNumber$_$versionName$");
		prmtCompareVer.setRequired(true);
		prmtCompareVer.setSelectorCollection(selectors);
		clear();
		
	}
	private void clear(){
		prmtBaseVer.setValue(null);
		prmtCompareVer.setValue(null);
		chkAimCost.setSelected(true);
		chkBuildArea.setSelected(false);
		chkSellArea.setSelected(false);
		//		resetTableHead();

		resetTableHead(tblMain);
		resetTableHead(productTable);
        
		String prjId=getSelectObjId();
		EntityViewInfo view=new  EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("orgOrProId", prjId);
		this.prmtBaseVer.getQueryAgent().resetRuntimeEntityView();
		this.prmtBaseVer.setEntityViewInfo(view);
		this.prmtCompareVer.getQueryAgent().resetRuntimeEntityView();
		this.prmtCompareVer.setEntityViewInfo(view);


	}
	protected void fillTable(){
		resetTableHead(tblMain);
		isFirstAdd = true;
		if (product != null && product.size() > 0) {
			resetTableHead(productTable);
		} else {
			productTable.removeRows();
			for (int i = productTable.getColumnCount() - 1; i > 2; i--) {
				productTable.removeColumn(i);
			}
		}
		
		CostAccountCollection accts=(CostAccountCollection)dataMap.get("accts");
		
		CostAccountCollection accts2 = (CostAccountCollection) dataMap.get("accts2");
		if(accts==null||accts.size()==1){
			return;
		}
		if (accts2 == null || accts2.size() == 1) {
			return;
		}
		int maxLevel=0;
		
		int maxLevel1 = 0;
		int maxLevel2 = 0;
		for (int i = 0; i < accts.size(); i++) {
			if (accts.get(i).getLevel() > maxLevel1) {
				maxLevel1 = accts.get(i).getLevel();
			}
		}
		for (int i = 0; i < accts2.size(); i++) {
			if (accts2.get(i).getLevel() > maxLevel2) {
				maxLevel2 = accts2.get(i).getLevel();
			}
		}
		if (maxLevel1 >= maxLevel2) {
			maxLevel = maxLevel1;
		} else {
			maxLevel = maxLevel2;
		}
		
//		Map costEntrys=(Map)dataMap.get("costEntrys");
//		if(costEntrys==null){
//			costEntrys=new HashMap();
//		}
		tblMain.getTreeColumn().setDepth(maxLevel);
		if (product != null && product.size() > 0) {
			productTable.getTreeColumn().setDepth(maxLevel);
		}
		
		Map cAccMap = (Map) dataMap.get("maps");
		
		Map cAccMap2 = (Map) dataMap.get("maps2");
		
		
		Map costAccount = (Map) dataMap.get("costAccount");

		Map costAccount2 = (Map) dataMap.get("costAccount2");
		
		for (Iterator iter = cAccMap.keySet().iterator(); iter.hasNext();) {
			CostAccountInfo info = new CostAccountInfo();
			 Object next = iter.next();
			info.setLongNumber((String) next);
			info.setName((String) cAccMap.get(next));
			
			info.setLevel(((Integer) cAccMap2.get(next)).intValue());
			fillNode(info, costAccount, costAccount2);
		}
		
//		for(Iterator iter=accts.iterator();iter.hasNext();){
		//			CostAccountInfo info=(CostAccountInfo)iter.next();
		//			fillNode(info);
		//		}

		setUnionData();
		if (product != null && product.size() > 0) {
			setProUnionData();
		}
		
		//由于表格初始化序号列时会出现列宽不够
		tblMain.invalidate();
		productTable.invalidate();
	}

	/**
	 * 描述：填充项目整体对比数据(暂时不用,后续删除)
	 * @Author：keyan_zhao
	 * @CreateTime：2012-3-30
	 */
	private void setColumnValue(CostAccountInfo info, int g, IRow row, String abuildArea, String bsellArea) {
		/* TODO 自动生成方法存根 */
		if(info.isIsLeaf()){
			row.setUserObject(info);
			for (int i = g; i < tblMain.getColumnCount(); i++) {
				String colKey = tblMain.getColumnKey(i);
				String aimId = getId(colKey, tblMain);
				
				
				i = i + 1;
				if(aimId.equals(diffID)||aimId.equals(diffRate)){
					continue;
				}
				String key = null;
				if (aimId.equals(((AimCostInfo) prmtBaseVer.getValue()).getId().toString())) {
					//					key = aimId + ((CostAccountInfo) costAccount.get(info.getLongNumber())).getId().toString();
				}
					
					
				BigDecimal amt=(BigDecimal)dataMap.get(key);
				if(colKey.equals(aimId+"_build")){
					BigDecimal buildArea = (BigDecimal) dataMap.get(abuildArea);
					amt=FDCNumberHelper.divide(amt, buildArea);
				}
				if(colKey.equals(aimId+"_sell")){
					BigDecimal sellArea = (BigDecimal) dataMap.get(bsellArea);
					amt=FDCNumberHelper.divide(amt, sellArea);
				}
				if(amt!=null&&amt.signum()==0){
					amt=null;
				}
				row.getCell(colKey).setValue(amt);
				
			}
		}else{
			
			for (int i = g; i < tblMain.getColumnCount(); i++) {
				String colKey = tblMain.getColumnKey(i);
				String aimId = getId(colKey, tblMain);
				i = i + 1;
				if(aimId.equals(diffID)||aimId.equals(diffRate)){
					continue;
				}
				String key=aimId+info.getId().toString();
				BigDecimal amt=(BigDecimal)dataMap.get(key);
				if(colKey.equals(aimId+"_build")){
					BigDecimal buildArea = (BigDecimal) dataMap.get(abuildArea);
					amt=FDCNumberHelper.divide(amt, buildArea);
				}
				if(colKey.equals(aimId+"_sell")){
					BigDecimal sellArea = (BigDecimal) dataMap.get(bsellArea);
					amt=FDCNumberHelper.divide(amt, sellArea);
				}
				if(amt!=null&&amt.signum()==0){
					amt=null;
				}else{
					row.getCell(colKey).setUserObject("hasData");
				}
				row.getCell(colKey).setValue(amt);
				
			}
			
		}
	}
	
	/**
	 * 描述：填充项目整体对比数据
	 * @Author：keyan_zhao
	 * @CreateTime：2012-4-5
	 */
	private void fillTblMain(IRow row, CostAccountInfo info, Map costAccount, Map costAccount2) {
		for (int i = 3; i < tblMain.getColumnCount(); i++) {
			String colKey = tblMain.getColumnKey(i);
			String aimId = getId(colKey, tblMain);
			if (aimId.equals(diffID) || aimId.equals(diffRate)) {
				continue;
			}

			String key = null;
			if (aimId.equals(((AimCostInfo) prmtBaseVer.getValue()).getId().toString())) {
				
				if (costAccount.get(info.getLongNumber()) == null) {
					continue;
				}
				
				CostAccountInfo costInfo = (CostAccountInfo) costAccount.get(info.getLongNumber());
				if (costInfo.isIsLeaf()) {
					row.getCell(colKey).setUserObject(costInfo);
				}
				key = aimId + costInfo.getId().toString();
				
				BigDecimal amt = (BigDecimal) dataMap.get(key);
				if (colKey.equals(aimId + "_build")) {
					BigDecimal buildArea = (BigDecimal) dataMap.get("buildArea");
					amt = FDCNumberHelper.divide(amt, buildArea);
				}
				if (colKey.equals(aimId + "_sell")) {
					BigDecimal sellArea = (BigDecimal) dataMap.get("sellArea");
					amt = FDCNumberHelper.divide(amt, sellArea);
				}
				if (amt != null && amt.signum() == 0) {
					amt = null;
				}
				row.getCell(colKey).setValue(amt);
			}

			if (aimId.equals(((AimCostInfo) prmtCompareVer.getValue()).getId().toString())) {
				if (costAccount2.get(info.getLongNumber()) == null) {
					continue;
				}
				CostAccountInfo costInfo = (CostAccountInfo) costAccount2.get(info.getLongNumber());
				if (costInfo.isIsLeaf()) {
					row.getCell(colKey).setUserObject(costInfo);
				}
				
				key = aimId + ((CostAccountInfo) costAccount2.get(info.getLongNumber())).getId().toString();
				
				BigDecimal amt = (BigDecimal) dataMap.get(key);
				if (colKey.equals(aimId + "_build")) {
					BigDecimal buildArea = (BigDecimal) dataMap.get("buildArea2");
					amt = FDCNumberHelper.divide(amt, buildArea);
				}
				if (colKey.equals(aimId + "_sell")) {
					BigDecimal sellArea = (BigDecimal) dataMap.get("sellArea2");
					amt = FDCNumberHelper.divide(amt, sellArea);
				}
				if (amt != null && amt.signum() == 0) {
					amt = null;
				}
				row.getCell(colKey).setValue(amt);
			}
		}
	}

	/**
	 * 描述：填充项目产品对比数据
	 * @Author：keyan_zhao
	 * @CreateTime：2012-4-5
	 */
	private void fillProductTable(IRow row, CostAccountInfo info, Map costAccount, Map costAccount2) {
		for (int i = 3; i < productTable.getColumnCount(); i++) {

			String colKey = productTable.getColumnKey(i);
			String aimId = getId(colKey, productTable);
			String proID = getProId(colKey, productTable);

			Map entryMap = (Map) aimCostMap.get(aimId);
			if (entryMap == null) {
				continue;
			}
			
			if (aimId.equals(diffID) || aimId.equals(diffRate)) {
				continue;
			}

			String key = null;
			if (aimId.equals(((AimCostInfo) prmtBaseVer.getValue()).getId().toString())) {

				if (costAccount.get(info.getLongNumber()) == null) {
					continue;
				}

				CostAccountInfo costInfo = (CostAccountInfo) costAccount.get(info.getLongNumber());
				if (costInfo.isIsLeaf()) {
					row.getCell(colKey).setUserObject(costInfo);
				}
				key = costInfo.getId().toString() + proID;

				BigDecimal amt = FDCHelper.ZERO;
				if (entryMap.get(key) != null) {
					amt = (BigDecimal) entryMap.get(key);
				}
				if (colKey.equals(aimId + proID + "_build")) {
					BigDecimal buildArea = (BigDecimal) dataMap.get("buildArea");
					amt = FDCNumberHelper.divide(amt, buildArea);
				}
				if (colKey.equals(aimId + proID + "_sell")) {
					BigDecimal sellArea = (BigDecimal) dataMap.get("sellArea");
					amt = FDCNumberHelper.divide(amt, sellArea);
				}
				if (amt != null && amt.signum() == 0) {
					amt = null;
				}
				row.getCell(colKey).setValue(amt);
			}

			if (aimId.equals(((AimCostInfo) prmtCompareVer.getValue()).getId().toString())) {
				if (costAccount2.get(info.getLongNumber()) == null) {
					continue;
				}
				CostAccountInfo costInfo = (CostAccountInfo) costAccount2.get(info.getLongNumber());
				if (costInfo.isIsLeaf()) {
					row.getCell(colKey).setUserObject(costInfo);
				}

				key = ((CostAccountInfo) costAccount2.get(info.getLongNumber())).getId().toString() + proID;

				BigDecimal amt = FDCHelper.ZERO;
				if (entryMap.get(key) != null) {
					amt = (BigDecimal) entryMap.get(key);
				}
				if (colKey.equals(aimId + proID + "_build")) {
					BigDecimal buildArea = (BigDecimal) dataMap.get("buildArea2");
					amt = FDCNumberHelper.divide(amt, buildArea);
				}
				if (colKey.equals(aimId + proID + "_sell")) {
					BigDecimal sellArea = (BigDecimal) dataMap.get("sellArea2");
					amt = FDCNumberHelper.divide(amt, sellArea);
				}
				if (amt != null && amt.signum() == 0) {
					amt = null;
				}
				if (amt == null) {
					amt = FDCHelper.ZERO;
				}
				row.getCell(colKey).setValue(amt);
			}
		}

	}
	
	Map product = new HashMap();
	
	/**
	 * 描述：判断是否含有相同的产品类型
	 * @throws BOSException 
	 * @Author：keyan_zhao
	 * @CreateTime：2012-4-5
	 */
	private Map getSameProduct() throws BOSException {
		product.clear();
		Map baseProduct = initProductType(getSelectObjId());
		Map compareProduct = initProductType(getCompareObjId());
		
		if (baseProduct == null || baseProduct.size() == 0) {
			return null;
		}
		for(Iterator ite =baseProduct.keySet().iterator();ite.hasNext();){
			Object next = ite.next();

			if (compareProduct != null && compareProduct.containsKey(next)) {
				ProductTypeInfo info = (ProductTypeInfo) compareProduct.get(next);
				
				product.put(next.toString(), info);
			}
		}
		return product;
		
	}
	
	protected  Map initProductType(String selectObjId) throws BOSException {
		Map productTypesMap = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("curProject.id", selectObjId));
		filter.getFilterItems().add(
				new FilterItemInfo("productType.isEnabled", new Integer(1)));
		filter.getFilterItems().add(
				new FilterItemInfo("isAccObj", new Integer(1)));
		// 产品类型
		view.getSelector().add("productType.*");
		SorterItemInfo sorter = new SorterItemInfo("productType.number");
		sorter.setSortType(SortType.ASCEND);
		view.getSorter().add(sorter);
		// 分摊数据
		// view.getSelector().add("curProjProEntrApporData.apportionType.*");
		// view.getSelector().add("curProjProEntrApporData.value");

		ICurProjProductEntries inst = null;
		inst = CurProjProductEntriesFactory.getRemoteInstance();
		CurProjProductEntriesCollection collObj = inst.getCurProjProductEntriesCollection(view);
		for (int i = 0; i < collObj.size(); i++) {
			String productId = collObj.get(i).getProductType().getId().toString();
			productTypesMap.put(productId, collObj.get(i).getProductType());
		}
		return productTypesMap;
	}	
	
	private void fillNode(CostAccountInfo info, Map costAccount, Map costAccount2) {
		
		IRow row = tblMain.addRow();
		row.getCell("acctNumber").setValue(info.getLongNumber().replace('!', '.'));
		row.getCell("acctNumber").setUserObject(info);
		row.getCell("acctName").setValue(info.getName());
		row.setTreeLevel(info.getLevel() - 1);
		fillTblMain(row, info, costAccount, costAccount2);
		
		if (product != null && product.size() > 0) {
			row = productTable.addRow();
			row.getCell("acctNumber").setValue(info.getLongNumber().replace('!', '.'));
			row.getCell("acctNumber").setUserObject(info);
			row.getCell("acctName").setValue(info.getName());
			row.setTreeLevel(info.getLevel() - 1);
			fillProductTable(row, info, costAccount, costAccount2);
		}

		//		if (costAccount.get(info.getLongNumber()) != null) {
		//			setColumnValue((CostAccountInfo) costAccount.get(info.getLongNumber()), 3, row, "buildArea", "sellArea");
		//		}
		//
		//		if (costAccount2.get(info.getLongNumber()) != null) {
		//			setColumnValue((CostAccountInfo) costAccount2.get(info.getLongNumber()), 4, row, "buildArea2", "sellArea2");
		//		}

		//				if(info.isIsLeaf()){
		//					row.setUserObject(info);
		

			//						String key=aimId+info.getId().toString();
		
		//				}else{
		//					
		//					for(int i=3;i<tblMain.getColumnCount();i++){
		//						String colKey = tblMain.getColumnKey(i);
		//						String aimId=getId(colKey);
		//						if(aimId.equals(diffID)||aimId.equals(diffRate)){
		//							continue;
		//						}
		//						String key=aimId+info.getId().toString();
		//						BigDecimal amt=(BigDecimal)dataMap.get(key);
		//						if(colKey.equals(aimId+"_build")){
		//							BigDecimal buildArea=(BigDecimal)dataMap.get("buildArea");
		//							amt=FDCNumberHelper.divide(amt, buildArea);
		//						}
		//						if(colKey.equals(aimId+"_sell")){
		//							BigDecimal sellArea=(BigDecimal)dataMap.get("sellArea");
		//							amt=FDCNumberHelper.divide(amt, sellArea);
		//						}
		//						if(amt!=null&&amt.signum()==0){
		//							amt=null;
		//						}else{
		//							row.getCell(colKey).setUserObject("hasData");
		//						}
		//						row.getCell(colKey).setValue(amt);
		//					}
		//					
		//				}
	}
	
	/**
	 * 描述：
	 * @Author：keyan_zhao
	 * @CreateTime：2012-3-31
	 */
	private void setColumnUnion(IRow row, List cols, KDTable table, int i, int g) {
		/* TODO 自动生成方法存根 */

		if (row.getCell(g).getUserObject() == null) {
			// 设置汇总行
			int level = row.getTreeLevel();
			List aimRowList = new ArrayList();
			for (int j = i + 1; j < table.getRowCount(); j++) {
				IRow rowAfter = table.getRow(j);
				if (rowAfter.getTreeLevel() <= level) {
					break;
				}
				if (row.getCell(g).getUserObject() instanceof CostAccountInfo && row.getCell("acctNumber").getUserObject() == null) {
					aimRowList.add(rowAfter);
				} else if (row.getCell("acctNumber").getUserObject() != null) {
					aimRowList.add(rowAfter);
				}

			}

			/**
			 * 所有报表的所有单方不存在上下级汇总关系，而应该是各个级次的都等于各自的对应成本除以对应的面积，而非下级的单方汇总
			 * @author pengwei_hou Date: 2009-01-18 14:40:23
			 */
			for (int j = 0; j < cols.size(); j++) {
				BigDecimal sum = null;
				String key = cols.get(j).toString();
				String aimId = getId(key, table);
				if (g == 3) {
					if (!aimId.equals(((AimCostInfo) prmtBaseVer.getValue()).getId().toString())) {
						continue;
					}
				}

				if (g == 4) {
					if (!aimId.equals(((AimCostInfo) prmtCompareVer.getValue()).getId().toString())) {
						continue;
					}
				}

				if (key.endsWith("_aim")) {
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell((String) cols.get(j)).getValue();
						if (value != null) {
							if (sum == null) {
								sum = FMConstants.ZERO;
							}
							if (value instanceof BigDecimal) {
								sum = sum.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								sum = sum.add(new BigDecimal(((Integer) value).toString()));
							}
						}
					}
				}
				if (key.endsWith("_build")) {
					BigDecimal rowBuildAmt = FDCHelper.toBigDecimal(row.getCell(getId(key, table) + "_aim").getValue());
					if (g == 3) {
						sum = FDCNumberHelper.divide(rowBuildAmt, this.buildArea, 2, BigDecimal.ROUND_HALF_UP);
					}
					if (g == 4) {
						sum = FDCNumberHelper.divide(rowBuildAmt, this.buildArea2, 2, BigDecimal.ROUND_HALF_UP);
					}
				}
				if (key.endsWith("_sell")) {
					BigDecimal rowSellAmt = FDCHelper.toBigDecimal(row.getCell(getId(key, table) + "_aim").getValue());

					if (g == 3) {
						sum = FDCNumberHelper.divide(rowSellAmt, this.sellArea, 2, BigDecimal.ROUND_HALF_UP);
					}
					if (g == 4) {
						sum = FDCNumberHelper.divide(rowSellAmt, this.sellArea2, 2, BigDecimal.ROUND_HALF_UP);
					}
				}
				//				if(sum != null && sum.compareTo(FDCHelper.ZERO)==0){
				//					sum = null;
				//				}
				//				row.getCell(key).setValue(sum);

				if (FDCHelper.toBigDecimal(sum).compareTo(FDCHelper.ZERO) != 0) {
					row.getCell(key).setValue(sum);
				}

			}

		}

	}

	/**
	 * 描述：
	 * @Author：keyan_zhao
	 * @CreateTime：2012-3-31
	 */
	private void setProColumnUnion(IRow row, List cols, KDTable table, int i, int g) {
		/* TODO 自动生成方法存根 */

		if (row.getCell(g).getUserObject() == null) {
			// 设置汇总行
			int level = row.getTreeLevel();
			List aimRowList = new ArrayList();
			for (int j = i + 1; j < table.getRowCount(); j++) {
				IRow rowAfter = table.getRow(j);
				if (rowAfter.getTreeLevel() <= level) {
					break;
				}
				if (row.getCell(g).getUserObject() instanceof CostAccountInfo && row.getCell("acctNumber").getUserObject() == null) {
					aimRowList.add(rowAfter);
				} else if (row.getCell("acctNumber").getUserObject() != null) {
					aimRowList.add(rowAfter);
				}

			}

			/**
			 * 所有报表的所有单方不存在上下级汇总关系，而应该是各个级次的都等于各自的对应成本除以对应的面积，而非下级的单方汇总
			 * @author pengwei_hou Date: 2009-01-18 14:40:23
			 */
			for (int j = 0; j < cols.size(); j++) {
				BigDecimal sum = null;
				String key = cols.get(j).toString();
				String aimId = getId(key, table);
				String proID = getProId(key, table);
				if (g == 3) {
					if (!aimId.equals(((AimCostInfo) prmtBaseVer.getValue()).getId().toString())) {
						continue;
					}
				}
				
				if (g == 4) {
					if (!aimId.equals(((AimCostInfo) prmtCompareVer.getValue()).getId().toString())) {
						continue;
					}
				}
				

				if (key.endsWith("_aim")) {
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell((String) cols.get(j)).getValue();
						if (value != null) {
							if (sum == null) {
								sum = FMConstants.ZERO;
							}
							if (value instanceof BigDecimal) {
								sum = sum.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								sum = sum.add(new BigDecimal(((Integer) value).toString()));
							}
						}
					}
				}
				if (key.endsWith("_build")) {
					BigDecimal rowBuildAmt = FDCHelper.toBigDecimal(row.getCell(getId(key, table)+proID + "_aim").getValue());
					if (g == 3) {
						sum = FDCNumberHelper.divide(rowBuildAmt, this.buildArea, 2, BigDecimal.ROUND_HALF_UP);
					}
					if (g == 4) {
						sum = FDCNumberHelper.divide(rowBuildAmt, this.buildArea2, 2, BigDecimal.ROUND_HALF_UP);
					}
				}
				if (key.endsWith("_sell")) {
					BigDecimal rowSellAmt = FDCHelper.toBigDecimal(row.getCell(getId(key, table) + proID + "_aim").getValue());
					
					if (g == 3) {
						sum = FDCNumberHelper.divide(rowSellAmt, this.sellArea, 2, BigDecimal.ROUND_HALF_UP);
					}
					if (g == 4) {
						sum = FDCNumberHelper.divide(rowSellAmt, this.sellArea2, 2, BigDecimal.ROUND_HALF_UP);
					}
				}
				//				if(sum != null && sum.compareTo(FDCHelper.ZERO)==0){
				//					sum = null;
				//				}
				//				row.getCell(key).setValue(sum);

				if (FDCHelper.toBigDecimal(sum).compareTo(FDCHelper.ZERO) != 0) {
					row.getCell(key).setValue(sum);
				}

			}

		}

	}

	/**
	 * 描述：汇总项目产品对比
	 * @Author：keyan_zhao
	 * @CreateTime：2012-4-6
	 */
	protected void setProUnionData() {
		//汇总
		List cols=new  ArrayList();
		for(int i=3;i<productTable.getColumnCount();i++){
			String key = productTable.getColumnKey(i);
			if(key!=null&&(key.startsWith(diffID)||key.startsWith(diffRate))){
				continue;
			}
			cols.add(key);
		}
		KDTable table=productTable;
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			
			setProColumnUnion(row, cols, table, i, productTable.getColumnIndex("acctName") + 1);
			setProColumnUnion(row, cols, table, i, productTable.getColumnIndex("acctName") + 2);
		
		}
		//计算差值
		AimCostInfo baseInfo = (AimCostInfo) prmtBaseVer.getData();
		Object[] otherData = FDCHelper.getF7Data(prmtCompareVer);
		if (otherData.length == 1) {
			AimCostInfo verInfo = (AimCostInfo) otherData[0];
			String baseId = baseInfo.getId().toString();
			String verId = verInfo.getId().toString();
			for (int i = 0; i < productTable.getRowCount(); i++) {
				IRow row = productTable.getRow(i);

				for (Iterator ite = product.keySet().iterator(); ite.hasNext();) {
					//					ProductTypeInfo proInfo = (ProductTypeInfo) ite.next();
					String proID = (String) ite.next();
					if (isAimCostSelect()) {
						row.getCell(diffID + proID + "_aim").setValue(
								FDCNumberHelper.subtract(row.getCell(verId + proID + "_aim").getValue(), row.getCell(
										baseId + proID + "_aim").getValue()));
						row.getCell(diffRate + proID + "_aim").setValue(
								FDCNumberHelper.divide(row.getCell(diffID + proID + "_aim").getValue(), row
										.getCell(baseId + proID + "_aim").getValue(), 4, BigDecimal.ROUND_HALF_UP));
					}
					if (isBuildSelect()) {
						row.getCell(diffID + proID + "_build").setValue(
								FDCNumberHelper.subtract(row.getCell(verId + proID + "_build").getValue(), row.getCell(
										baseId + proID + "_build").getValue()));
						row.getCell(diffRate + proID + "_build").setValue(
								FDCNumberHelper.divide(row.getCell(diffID + proID + "_build").getValue(), row.getCell(
										baseId + proID + "_build").getValue(), 4, BigDecimal.ROUND_HALF_UP));
					}
					if (isSellSelect()) {
						row.getCell(diffID + proID + "_sell").setValue(
								FDCNumberHelper.subtract(row.getCell(verId + proID + "_sell").getValue(), row.getCell(
										baseId + proID + "_sell").getValue()));
						row.getCell(diffRate + proID + "_sell").setValue(
								FDCNumberHelper.divide(row.getCell(diffID + proID + "_sell").getValue(), row.getCell(
										baseId + proID + "_sell").getValue(), 4, BigDecimal.ROUND_HALF_UP));
					}
				}
			}

		}
	}
	
	protected void setUnionData(){
		//汇总
		List cols=new  ArrayList();
		for(int i=3;i<tblMain.getColumnCount();i++){
			String key = tblMain.getColumnKey(i);
			if(key!=null&&(key.startsWith(diffID)||key.startsWith(diffRate))){
				continue;
			}
			cols.add(key);
		}
		KDTable table=tblMain;
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			
			setColumnUnion(row, cols, table, i, 3);
			setColumnUnion(row, cols, table, i, 4);
		
		}
		//计算差值
		AimCostInfo baseInfo=(AimCostInfo)prmtBaseVer.getData();
		Object[] otherData = FDCHelper.getF7Data(prmtCompareVer);
		if(otherData.length==1){
			AimCostInfo verInfo=(AimCostInfo)otherData[0];
			String baseId=baseInfo.getId().toString();
			String verId=verInfo.getId().toString();
			for(int i=0;i<tblMain.getRowCount();i++){
				IRow row=tblMain.getRow(i);
				if(isAimCostSelect()){
					row.getCell(diffID+"_aim").setValue(FDCNumberHelper.subtract(row.getCell(verId+"_aim").getValue(), row.getCell(baseId+"_aim").getValue()));
					row.getCell(diffRate+"_aim").setValue(FDCNumberHelper.divide(row.getCell(diffID+"_aim").getValue(),row.getCell(baseId+"_aim").getValue(),
							                              4, BigDecimal.ROUND_HALF_UP));
				}
				if(isBuildSelect()){
					row.getCell(diffID+"_build").setValue(FDCNumberHelper.subtract(row.getCell(verId+"_build").getValue(), row.getCell(baseId+"_build").getValue()));
					row.getCell(diffRate+"_build").setValue(FDCNumberHelper.divide(row.getCell(diffID+"_build").getValue(),row.getCell(baseId+"_build").getValue(),
							                              4, BigDecimal.ROUND_HALF_UP));
				}
				if(isSellSelect()){
					row.getCell(diffID+"_sell").setValue(FDCNumberHelper.subtract(row.getCell(verId+"_sell").getValue(), row.getCell(baseId+"_sell").getValue()));
					row.getCell(diffRate+"_sell").setValue(FDCNumberHelper.divide(row.getCell(diffID+"_sell").getValue(),row.getCell(baseId+"_sell").getValue(),
							                              4, BigDecimal.ROUND_HALF_UP));
				}
			}
			
		}
		try {
			String keys[]=new String[cols.size()];
			cols.toArray(keys);
			AimCostClientHelper.setTotalCostRow(table, keys);
			
			IRow footRow = FDCTableHelper.generateFootRow(table);
			if (otherData.length == 1) {				
				AimCostInfo verInfo=(AimCostInfo)otherData[0];
				String baseId=baseInfo.getId().toString();
				String verId=verInfo.getId().toString();
				if (isAimCostSelect()) {
					footRow.getCell(diffID + "_aim").setValue(
							FDCNumberHelper.subtract(footRow.getCell(verId + "_aim").getValue(), footRow.getCell(baseId + "_aim")
									.getValue()));
					footRow.getCell(diffRate + "_aim").setValue(
							FDCNumberHelper.divide(footRow.getCell(diffID + "_aim").getValue(),
									footRow.getCell(baseId + "_aim").getValue(), 4, BigDecimal.ROUND_HALF_UP));
				}
				if (isBuildSelect()) {
					footRow.getCell(diffID + "_build").setValue(
							FDCNumberHelper.subtract(footRow.getCell(verId + "_build").getValue(), footRow.getCell(baseId + "_build")
									.getValue()));
					footRow.getCell(diffRate + "_build").setValue(
							FDCNumberHelper.divide(footRow.getCell(diffID + "_build").getValue(), footRow.getCell(baseId + "_build")
									.getValue(), 4, BigDecimal.ROUND_HALF_UP));
				}
				if (isSellSelect()) {
					footRow.getCell(diffID + "_sell").setValue(
							FDCNumberHelper.subtract(footRow.getCell(verId + "_sell").getValue(), footRow.getCell(baseId + "_sell")
									.getValue()));
					footRow.getCell(diffRate + "_sell").setValue(
							FDCNumberHelper.divide(footRow.getCell(diffID + "_sell").getValue(), footRow.getCell(baseId + "_sell")
									.getValue(), 4, BigDecimal.ROUND_HALF_UP));
				}
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}
	protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
		super.btnOK_actionPerformed(e);
		verify();
		fetchData();
		fillTable();
	}
	
	private void verify(){
		FDCClientVerifyHelper.verifyEmpty(this, prmtBaseVer);
		FDCClientVerifyHelper.verifyEmpty(this, prmtCompareVer);
		String id = FDCHelper.getF7Id(prmtBaseVer);
		Set set=FDCHelper.getF7IdSet(prmtCompareVer);
		if(set.contains(id)){
			FDCMsgBox.showWarning(this,"比较版本里面含有基准版本");
			SysUtil.abort();
		}
		if(!isAimCostSelect()&&!isBuildSelect()&&!isSellSelect()){
			FDCMsgBox.showWarning(this, "目标成本，建筑单方，可售单方必须选择一个");
			SysUtil.abort();
		}
	}
	private Map dataMap=null;
	private Map aimCostMap = new HashMap();
	private BigDecimal buildArea = FDCHelper.ZERO;
	private BigDecimal sellArea = FDCHelper.ZERO;
	
	
	private BigDecimal buildArea2 = FDCHelper.ZERO;
	private BigDecimal sellArea2 = FDCHelper.ZERO;

	/**
	 * 描述：获取对比版本中项目id
	 * @Author：keyan_zhao
	 * @CreateTime：2012-3-30
	 */
	private String getCompareObjId() {
		/* TODO 自动生成方法存根 */
		AimCostInfo value = (AimCostInfo) prmtCompareVer.getValue();
		return value.getOrgOrProId();
	}
	
	protected void fetchData() throws Exception{
		Map param=new HashMap();
		String prjId=getSelectObjId();
		
		String prjId2 = getCompareObjId();
		
		if(prjId==null){
			FDCMsgBox.showWarning(this, "当前选择的树结点不是明细工程项目");
			SysUtil.abort();
		}
		param.put("prjId", prjId);
		
		param.put("prjId2", prjId2);
		
		param.put("baseAimID", FDCHelper.getF7Id(prmtBaseVer));
		
		Set set=FDCHelper.getF7IdSet(prmtCompareVer);
		set.add(FDCHelper.getF7Id(prmtBaseVer));
		param.put("idSet", set);
		dataMap=AimCostFactory.getRemoteInstance().getAimCostVers(param);
		if(dataMap==null){
			dataMap=new  HashMap();
		}else{
			buildArea = FDCHelper.toBigDecimal(dataMap.get("buildArea"));
			sellArea = FDCHelper.toBigDecimal(dataMap.get("sellArea"));
			
			
			buildArea2 = FDCHelper.toBigDecimal(dataMap.get("buildArea2"));
			sellArea2 = FDCHelper.toBigDecimal(dataMap.get("sellArea2"));
		}
		aimCostMap.clear();
		product.clear();
			product = getSameProduct();
			
			if (product == null) {
				return;
			}
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
						sic.add("costAccount.longnumber");
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.id", set, CompareType.INCLUDE));
			Set proSet = new HashSet();
			for (Iterator ite = product.keySet().iterator(); ite.hasNext();) {
				proSet.add(ite.next());
			}
			
			
			filter.getFilterItems().add(new FilterItemInfo("product.id", proSet, CompareType.INCLUDE));
			view.setFilter(filter);
			view.setSelector(sic);
			CostEntryCollection entryCol = CostEntryFactory.getRemoteInstance().getCostEntryCollection(view);
			if (entryCol != null) {
				Map baseProAmount = new HashMap();
				Map comProAmount = new HashMap();
					if (entryCol != null && entryCol.size() > 0) {
						for (int j = 0; j < entryCol.size(); j++) {
							
							CostEntryInfo entryInfo = entryCol.get(j);
							String aimID = entryInfo.getHead().getId().toString();
							
							String costAccID = entryInfo.getCostAccount().getId().toString();
							String proID = entryInfo.getProduct().getId().toString();
							
						if (FDCHelper.getF7Id(prmtBaseVer).equals(aimID)) {
							if (baseProAmount.containsKey(costAccID + proID)) {

								baseProAmount.put(costAccID + proID, FDCHelper.add(baseProAmount.get(costAccID + proID), entryInfo
										.getCostAmount()));
							} else {
								baseProAmount.put(costAccID + proID, entryInfo.getCostAmount());
							}
						} else {
							if (comProAmount.containsKey(costAccID + proID)) {

								comProAmount.put(costAccID + proID, FDCHelper.add(comProAmount.get(costAccID + proID), entryInfo
										.getCostAmount()));
							} else {
								comProAmount.put(costAccID + proID, entryInfo.getCostAmount());
							}
							
							
							
						}
						
					}

					aimCostMap.put(FDCHelper.getF7Id(prmtBaseVer), baseProAmount);
					aimCostMap.put(FDCHelper.getF7Id(prmtCompareVer), comProAmount);
				}
			}
			
	}
	protected String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if(projectInfo.isIsLeaf()){
				return projectInfo.getId().toString();
			}
		} 
		return null;
	}
	protected void initTable(){
		IRow headRow=(IRow)tblMain.getHeadRow(0).clone();
		tblMain.addHeadRow(1, headRow);
		tblMain.getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		tblMain.getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		//		tblMain.getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_MERGE);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		headRow = (IRow) productTable.getHeadRow(0).clone();
		productTable.addHeadRow(1, headRow);
		//				productTable.getHeadMergeManager().mergeBlock(0, 0, 1, productTable.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);
		//		productTable.getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_MERGE);
		productTable.getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		productTable.getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		productTable.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		productTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
	
	/**
	 * 描述：
	 * @Author：keyan_zhao
	 * @CreateTime：2012-4-5
	 */
	private void resetTableHead(KDTable table, ProductTypeInfo proInfo) {
		table.removeRows(false);
		for (int i = table.getColumnCount() - 1; i > 2; i--) {
			if (proInfo != null) {
				if (isFirstAdd) {
					table.removeColumn(i);
				}
			} else {
				table.removeColumn(i);
			}
		}
		if(prmtBaseVer.getData()==null){
			return;
		}
		AimCostInfo baseInfo=(AimCostInfo)prmtBaseVer.getData();
		Object[] otherData = FDCHelper.getF7Data(prmtCompareVer);
		AimCostCollection aimCol=new AimCostCollection();
		for(int i=0;i<otherData.length;i++){
			aimCol.add((AimCostInfo)otherData[i]);
		}
		FDCHelper.sortObjectCollection(aimCol, "$versionNumber$");
		addColumn(baseInfo, table, proInfo);
		
		for(Iterator iter=aimCol.iterator();iter.hasNext();){
			AimCostInfo aimCostInfo=(AimCostInfo)iter.next();
			addColumn(aimCostInfo, table, proInfo);
		}
		if (aimCol.size() == 1) {
			AimCostInfo diffInfo = new AimCostInfo();
			diffInfo.setId(BOSUuid.read(diffID));
			diffInfo.setVersionNumber("差异");
			addColumn(diffInfo, table, proInfo);

			AimCostInfo diffRateInfo = new AimCostInfo();
			diffRateInfo.setId(BOSUuid.read(diffRate));
			diffRateInfo.setVersionNumber("差异率");
			addColumn(diffRateInfo, table, proInfo);
		}
		//				table.getHeadMergeManager().mergeBlock(1, 3, 1, table.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);

		int number_col_index = table.getColumn("acctName").getColumnIndex();
		table.getViewManager().setFreezeView(-1, number_col_index + 1);
	}
	boolean isFirstAdd;
	private void resetTableHead(KDTable table) {
		if (table == productTable) {
			if (product != null) {
				for (Iterator ite = product.keySet().iterator(); ite.hasNext();) {
					String itenext = ite.next().toString();
					ProductTypeInfo proInfo = (ProductTypeInfo) product.get(itenext);

					resetTableHead(table, proInfo);
					isFirstAdd = false;
				}
			}
		} else {
			resetTableHead(table, null);
		}
	}
	
	/**
	 * 描述：获得目标成本的工程项目
	 * @Author：keyan_zhao
	 * @CreateTime：2012-3-31
	 */
	private CurProjectInfo getAimCostCurPro(AimCostInfo info) {
		String id = info.getOrgOrProId();
		try {
			if (id != null && id.length() > 0) {
				CurProjectInfo curInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(id));
				return curInfo;
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		return null;
	}
	
	private void addColumn(AimCostInfo info, KDTable table, ProductTypeInfo proInfo) {
		
		CurProjectInfo curInfo = getAimCostCurPro(info);
		if(true){
			IColumn col = table.addColumn();
		
			
			if (proInfo != null) {
				col.setKey(info.getId().toString() + proInfo.getId().toString() + "_aim");
				table.getHeadRow(1).getCell(col.getKey()).setValue(proInfo.getName() + "_目标成本");
				if (curInfo != null) {
					table.getHeadRow(0).getCell(col.getKey()).setValue(curInfo.getName() + "_" + info.getVersionNumber());
				} else {
					table.getHeadRow(0).getCell(col.getKey()).setValue(info.getVersionNumber());
				}
				setId(col.getKey(), info, table);

				setProId(col.getKey(), proInfo, table);

				if (info.getId().toString().equals(diffRate)) {
					FDCHelper.formatTableNumber(table, col.getKey(), "0.00%");
				} else {
					FDCHelper.formatTableNumber(table, col.getKey());
				}
				if (!isAimCostSelect()) {
					col.getStyleAttributes().setHided(true);
				}
				
			}else{
				col.setKey(info.getId().toString() + "_aim");
				if (curInfo != null) {
					table.getHeadRow(0).getCell(col.getKey()).setValue(curInfo.getName() + "_" + info.getVersionNumber());
				} else {
					table.getHeadRow(0).getCell(col.getKey()).setValue(info.getVersionNumber());
				}
				
				table.getHeadRow(1).getCell(col.getKey()).setValue("目标成本");
				setId(col.getKey(), info, table);
				if (info.getId().toString().equals(diffRate)) {
					FDCHelper.formatTableNumber(table, col.getKey(), "0.00%");
				} else {
					FDCHelper.formatTableNumber(table, col.getKey());
				}
				if (!isAimCostSelect()) {
					col.getStyleAttributes().setHided(true);
				}
			}
		}
		
		if(isBuildSelect()){
			if (proInfo != null) {

				IColumn col = table.addColumn();
				col.setKey(info.getId().toString() + proInfo.getId().toString() + "_build");
				//			table.getHeadRow(0).getCell(col.getKey()).setValue(info.getVersionNumber());

				table.getHeadRow(1).getCell(col.getKey()).setValue(proInfo.getName() + "_建筑单方");

				
				if (curInfo != null) {
					table.getHeadRow(0).getCell(col.getKey()).setValue(curInfo.getName() + "_" + info.getVersionNumber());
				} else {
					table.getHeadRow(0).getCell(col.getKey()).setValue(info.getVersionNumber());
				}
				setId(col.getKey(), info, table);

				setProId(col.getKey(), proInfo, table);
				if (info.getId().toString().equals(diffRate)) {
					FDCHelper.formatTableNumber(table, col.getKey(), "0.00%");
				} else {
					FDCHelper.formatTableNumber(table, col.getKey());
				}

			} else {
				IColumn col = table.addColumn();
				col.setKey(info.getId().toString() + "_build");
				//			table.getHeadRow(0).getCell(col.getKey()).setValue(info.getVersionNumber());

				if (curInfo != null) {
					table.getHeadRow(0).getCell(col.getKey()).setValue(curInfo.getName() + "_" + info.getVersionNumber());
				} else {
					table.getHeadRow(0).getCell(col.getKey()).setValue(info.getVersionNumber());
				}

				table.getHeadRow(1).getCell(col.getKey()).setValue("建筑单方");
				setId(col.getKey(), info, table);
				if (info.getId().toString().equals(diffRate)) {
					FDCHelper.formatTableNumber(table, col.getKey(), "0.00%");
				} else {
					FDCHelper.formatTableNumber(table, col.getKey());
				}
			}
			
			
			
		
		}
		
		if(isSellSelect()){
			
			if (proInfo != null) {
				IColumn col = table.addColumn();
				col.setKey(info.getId().toString() + proInfo.getId().toString() + "_sell");
				//			table.getHeadRow(0).getCell(col.getKey()).setValue(info.getVersionNumber());

				table.getHeadRow(1).getCell(col.getKey()).setValue(proInfo.getName() + "_可售单方");
				
				if (curInfo != null) {
					table.getHeadRow(0).getCell(col.getKey()).setValue(curInfo.getName() + "_" + info.getVersionNumber());
				} else {
					table.getHeadRow(0).getCell(col.getKey()).setValue(info.getVersionNumber());
				}
				setId(col.getKey(), info, table);

				setProId(col.getKey(), proInfo, table);
				if (info.getId().toString().equals(diffRate)) {
					FDCHelper.formatTableNumber(table, col.getKey(), "0.00%");
				} else {
					FDCHelper.formatTableNumber(table, col.getKey());
				}
			}else{
				IColumn col = table.addColumn();
				col.setKey(info.getId().toString() + "_sell");
				//			table.getHeadRow(0).getCell(col.getKey()).setValue(info.getVersionNumber());

				if (curInfo != null) {
					table.getHeadRow(0).getCell(col.getKey()).setValue(curInfo.getName() + "_" + info.getVersionNumber());
				} else {
					table.getHeadRow(0).getCell(col.getKey()).setValue(info.getVersionNumber());
				}
				table.getHeadRow(1).getCell(col.getKey()).setValue("可售单方");
				setId(col.getKey(), info, table);
				if (info.getId().toString().equals(diffRate)) {
					FDCHelper.formatTableNumber(table, col.getKey(), "0.00%");
				} else {
					FDCHelper.formatTableNumber(table, col.getKey());
				}
			}
			
			
		}
	}

	private void setProId(String colKey, ProductTypeInfo info, KDTable table) {
		table.getHeadRow(0).getCell(colKey).setUserObject(info.getId().toString());
	}

	private String getProId(String colKey, KDTable table) {
		if (table.getHeadRow(0).getCell(colKey) != null) {
			return (String) table.getHeadRow(0).getCell(colKey).getUserObject();
		}
		return null;
	}
	
	
	
	private void setId(String colKey, AimCostInfo info, KDTable table) {
		table.getHeadRow(1).getCell(colKey).setUserObject(info.getId().toString());
	}
	private String getId(String colKey, KDTable table) {
		if (table.getHeadRow(1).getCell(colKey) != null) {
			return (String) table.getHeadRow(1).getCell(colKey).getUserObject();
		}
		return null;
	}
	private boolean isAimCostSelect(){
		return chkAimCost.isSelected();
	}
	private boolean isBuildSelect(){
		return chkBuildArea.isSelected();
	}
	private boolean isSellSelect(){
		return chkSellArea.isSelected();
	}
	protected IObjectPK getOrgPK(ItemAction action) {
		return new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
	}
	protected void initWorkButton() {
		super.initWorkButton();
		actionRemove.setEnabled(false);
		actionRemove.setVisible(false);
		actionLocate.setEnabled(false);
		actionLocate.setVisible(false);
		actionAddNew.setEnabled(false);
		actionAddNew.setVisible(false);
		actionEdit.setEnabled(false);
		actionEdit.setVisible(false);
		actionView.setEnabled(false);
		actionView.setVisible(false);
		actionQuery.setEnabled(false);
		actionQuery.setVisible(false);
		actionRefresh.setVisible(false);
		actionRefresh.setEnabled(false);
		menuView.setVisible(false);
		menuEdit.setVisible(false);
	}
	
	protected static final String diffID = BOSUuid.create("diff").toString();
	protected static final String diffRate = BOSUuid.create("diffRate").toString();
	protected void initCtrlListener(){
		SelectorListener selectorListener=new SelectorListener(){
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7=(KDBizPromptBox)e.getSource();
				String prjId=getSelectObjId();
				if(prjId!=null){
					EntityViewInfo view=new  EntityViewInfo();
					view.setFilter(new FilterInfo());
					view.getFilter().appendFilterItem("orgOrProId", prjId);
					f7.getQueryAgent().resetRuntimeEntityView();
					f7.setEntityViewInfo(view);
				}else{
					FDCMsgBox.showWarning(FDCClientHelper.getCurrentActiveWindow(),"请选择明细工程项目节点");
					e.setCanceled(true);
				}
			}
		};
		prmtBaseVer.addSelectorListener(selectorListener);
		prmtCompareVer.addSelectorListener(selectorListener);
	}
	
    /**
	 * @see com.kingdee.eas.framework.client.ListUI#isIgnoreCUFilter()
	 */
	protected boolean isIgnoreCUFilter() {
		return true;
	}
}