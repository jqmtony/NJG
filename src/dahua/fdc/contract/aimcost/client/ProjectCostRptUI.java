/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.ProjectCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCRenderHelper;
import com.kingdee.eas.fdc.basedata.util.AccountStageHelper;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ProjectCostRptUI extends AbstractProjectCostRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectCostRptUI.class);
    
    private Map acctStageMap = new HashMap();
    
	private String fullOrgUnitId = null;

	/** 是否级次 */
	private boolean isShowSplitAcct = true;

	/** 是否有子集 */
	private boolean children = false;

	/** 子集集合 */
	private final Map acctChidrenMap = new HashMap();
    
	 // 是否包含禁用科目
	protected boolean isAll = false;
	
    /**
     * output class constructor
     */
    public ProjectCostRptUI() throws Exception
    {
        super();
    }
   
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        
    }

  
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionView_actionPerformed(e);
    }

    public void actionAll_actionPerformed(ActionEvent e) throws Exception {
		isAll = true;
		this.fillTable();
	}
	protected String getEditUIName() {
		return null;
	}
	
	protected void fillTable() throws Exception {
		super.fillTable();
		KDTable table = getMainTable();
		table.removeRows(false);
		fetchData();
		if(retValue==null){
			table.getHeadMergeManager().mergeBlock(0, 0, 2, table.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
			return;
		}
		resetHead();
		CostAccountCollection accts=getAccts();
		if(accts==null){
			return;
		}
		table.getTreeColumn().setDepth(getMaxLevel());
		for(int i=0;i<accts.size();i++){
			CostAccountInfo info=accts.get(i);
			if (!isAll) {
				if (!info.isIsEnabled()) {
					continue;
				}
			}
			// 如果显示的是可拆分科目同时级次为选择
			if (this.isShowSplitAcct && !info.isIsSplit()) {
				continue;
			}
			IRow row=table.addRow();
			row.getCell("id").setValue(info.getId().toString());
			row.getCell("acctNumber").setValue(info.getLongNumber());
			//多级成本管控改造
			row.getCell("acctNumber").setUserObject(info);
			row.getCell("acctName").setValue(info.getName());
			row.setTreeLevel(info.getLevel()-1);
			if(info.isIsLeaf()){
				row.setUserObject(info);
				loadRow(row);
			}else{
				loadRow(row);
			}
		}
		// setUnionData();
		//		setUnionData2();
		//		setAimUnionData2();
	}
	
	
	
	public void onLoad() throws Exception {
		super.onLoad();
		//设置保存当前样式
		tHelper = new TablePreferencesHelper(this);
		this.btnSplit.setEnabled(true);
		
		this.isSplitBtn.setIcon(EASResource.getIcon("imgTbtn_execute"));
		this.isSplitBtn.setEnabled(true);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
	}
	protected void initTable() {
		KDTable table=getMainTable();
		table.checkParsed();
		super.initTable();
		table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		table.getColumn("id").getStyleAttributes().setHided(true);
		tblMain.getColumn("acctNumber").setRenderer(FDCRenderHelper.getLongNumberRender());
	}
	
	private void resetHead(){
		KDTable table=getMainTable();
		for(int i=table.getColumnCount()-1;i>0;i--){
			if(table.getColumnKey(i)!=null&&table.getColumnKey(i).equals("dif")){
				break;
			}
			table.removeColumn(i);
		}
		getHeadKeySet().clear();
		getHeadTotalKeyMap().clear();
		IRow headRow0=table.getHeadRow(0);
		IRow headRow1=table.getHeadRow(1);
		IRow headRow2=table.getHeadRow(2);
		for(Iterator iter=getProducts().iterator();iter.hasNext();){
			ProductTypeInfo product=(ProductTypeInfo)iter.next();
			CurProjectCollection prjs = (CurProjectCollection)getPrjs().clone();
			CurProjectInfo totalPrj=new CurProjectInfo();
			totalPrj.setName("合计");
			prjs.add(totalPrj);
			String totalKey="total_"+product.getId().toString();
			Set subSet=new HashSet();
			getHeadTotalKeyMap().put(totalKey, subSet);
			
			for(Iterator iter2=prjs.iterator();iter2.hasNext();){
				CurProjectInfo prj=(CurProjectInfo)iter2.next();
				String key=null;
				if(prj==totalPrj){
					//总计行
					key=totalKey;
				}else{
					key=prj.getId().toString()+product.getId().toString();
					getHeadKeySet().add(key);
					subSet.add(key);
				}
				IColumn col = table.addColumn();
				col.setKey(key+"aimCost");
				col = table.addColumn();
				col.setKey(key+"happenCost");
				col = table.addColumn();
				col.setKey(key+"waitCost");
				col = table.addColumn();
				col.setKey(key+"dynCost");
				col = table.addColumn();
				col.setKey(key+"dif");
				
				headRow0.getCell(key+"aimCost").setValue(product.getName());
				headRow0.getCell(key+"happenCost").setValue(product.getName());
				headRow0.getCell(key+"waitCost").setValue(product.getName());
				headRow0.getCell(key+"dynCost").setValue(product.getName());
				headRow0.getCell(key+"dif").setValue(product.getName());
				
				headRow1.getCell(key+"aimCost").setValue(prj.getName());
				headRow1.getCell(key+"happenCost").setValue(prj.getName());
				headRow1.getCell(key+"waitCost").setValue(prj.getName());
				headRow1.getCell(key+"dynCost").setValue(prj.getName());
				headRow1.getCell(key+"dif").setValue(prj.getName());
				
				headRow2.getCell(key+"aimCost").setValue("目标成本");
				headRow2.getCell(key+"happenCost").setValue("已发生成本");
				headRow2.getCell(key+"waitCost").setValue("待发生成本");
				headRow2.getCell(key+"dynCost").setValue("动态成本");
				headRow2.getCell(key+"dif").setValue("差异");
			}			
		}
		
		table.getHeadMergeManager().mergeBlock(0, 0, 2, table.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
		FDCHelper.formatTableNumber(table, table.getColumnIndex("acctName")+1, table.getColumnCount()-1);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int number_col_index = tblMain.getColumn("acctName").getColumnIndex();
				tblMain.getViewManager().setFreezeView(-1, number_col_index+1);
		}});
	}
	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setAimUnionData2() {
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		KDTable table=getMainTable();
//		amountColumns.add("aimCost");
//		amountColumns.add("dynCost");
		for(int i=tblMain.getColumnIndex("acctName")+1;i<table.getColumnCount();i++){
			if("happenCost".endsWith(table.getColumnKey(i))){
				continue;
			}
			amountColumns.add(table.getColumnKey(i));
		}
		
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
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
					} else if (rowAfter.getCell("aimCost").getUserObject() != null) {
						rowList.add(rowAfter);
						//成本科目多级改造
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FMConstants.ZERO;
					boolean isRed = false;
					boolean hasData = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						if (rowAdd.getCell(colName).getStyleAttributes()
								.getFontColor().equals(Color.RED)) {
							isRed = true;
						}
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(
										((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if(row.getCell("aimCost").getUserObject()!=null){
						if(colName.equals("dynCost")){
							amount = FDCHelper.add(row.getCell("aimCost").getValue(), amount);
						}else if(!colName.equals("dynCost")&&colName.endsWith("dynCost")){
							amount = FDCHelper.add(row.getCell(colName.substring(0,colName.length()-7)+"aimCost").getValue(), amount);
						}else if(!colName.equals("aimCost")&&colName.endsWith("aimCost")){
							amount = FDCHelper.add(row.getCell(colName.substring(0,colName.length()-7)+"aimCost").getValue(), amount);
						}
					}
					if (isRed) {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.RED);
					} else {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.BLACK);
					}
					if(FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO)!=0){
						row.getCell(colName).setValue(amount);
					}
				}
			}
			//每行都单独处理：差异=动态-目标
			for (int k = 0; k < amountColumns.size(); k++) {
				String colName = (String) amountColumns.get(k);
				if(colName.endsWith("waitCost")){
					setCellValue(row.getCell(colName), FDCNumberHelper.subtract(row.getCell(colName.substring(0,colName.length()-8)+"dynCost").getValue(), row.getCell(colName.substring(0,colName.length()-8)+"happenCost").getValue()));
				}
				if(colName.equals("dynCost")){
					setCellValue(row.getCell("dif"), FDCNumberHelper.subtract(row.getCell(colName).getValue(), row.getCell("aimCost").getValue()));
					setCellValue(row.getCell("waitCost"), FDCNumberHelper.subtract(row.getCell("dynCost").getValue(), row.getCell("happenCost").getValue()));
				}else if(!colName.equals("dynCost")&&colName.endsWith("dynCost")){
					setCellValue(row.getCell(colName.substring(0,colName.length()-7)+"dif"), FDCNumberHelper.subtract(row.getCell(colName).getValue(), row.getCell(colName.substring(0,colName.length()-7)+"aimCost").getValue()));
					setCellValue(row.getCell(colName.substring(0,colName.length()-7)+"waitCost"), FDCNumberHelper.subtract(row.getCell(colName).getValue(), row.getCell(colName.substring(0,colName.length()-7)+"happenCost").getValue()));
				}
				
				/*if(colName.equals("dynCost")){
					BigDecimal amount = FDCHelper.add(row.getCell(colName.substring(0,colName.length()-7)+"aimCost").getValue(), amount);
					setCellValue(row.getCell(colName.substring(0,colName.length()-7)+"waitCost"), FDCNumberHelper.subtract(row.getCell(colName).getValue(), row.getCell(colName.substring(0,colName.length()-7)+"happenCost").getValue()));
					
				}*/
				
			}
			
		}
	}
	
	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData2() {
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		KDTable table=getMainTable();
		for(int i=tblMain.getColumnIndex("acctName")+2;i<table.getColumnCount();i++){
			if(!"happenCost".endsWith(table.getColumnKey(i))){
				continue;
			}
			amountColumns.add(table.getColumnKey(i));
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
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
					boolean isRed = false;
					boolean hasData = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						if (rowAdd.getCell(colName).getStyleAttributes()
								.getFontColor().equals(Color.RED)) {
							isRed = true;
						}
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(
										((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if (isRed) {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.RED);
					} else {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.BLACK);
					}
					
					if(FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO)!=0){
						row.getCell(colName).setValue(amount);
					}
				}
			}
		}
	}
	protected void setUnionData(){
		super.setUnionData();
		KDTable table=getMainTable();
		List columns=new ArrayList();
		for(int i=tblMain.getColumnIndex("acctName")+1;i<table.getColumnCount();i++){
			columns.add(table.getColumnKey(i));
		}
		String cols[]=new String[columns.size()];
		columns.toArray(cols);
		
		for(int i=0;i<table.getRowCount();i++){
			IRow row = table.getRow(i);
			int level=row.getTreeLevel();
			if(level==0){
				_setUnionSubRow(row, columns);
			}
		}
		try {
			AimCostClientHelper.setTotalCostRow(table, cols);
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
	}
	
	/**
	 * 对一个科目树进行递归汇总
	 * @param row
	 */
	private void _setUnionSubRow(IRow row,List sumCols) {
		KDTable table=getMainTable();
		Object obj=row.getUserObject();
		int level=row.getTreeLevel();
		int plevel = 0;
		if(obj instanceof CostAccountInfo ){
			//明细行,直接返回加上
			return;
		}
		List subRows=new ArrayList();
		for(int i=row.getRowIndex()+1;i<table.getRowCount();i++){
			IRow tmpRow=table.getRow(i);
			if(plevel>0&&plevel<tmpRow.getTreeLevel()){
				continue;
			}
			if(tmpRow.getTreeLevel()==level+1){
				_setUnionSubRow(tmpRow, sumCols);
//				//多级成本管控改造
				//当上级有数据时，不加,否则覆盖
				Object o = row.getCell("aimCost").getUserObject();
				if(o!=null ){
					continue;
				}
				subRows.add(tmpRow);
			}
			if(tmpRow.getTreeLevel()<=level){
				break;
			}
		}
		sumRow(row, subRows, sumCols);	
	}
	
	/**
	 * 将subRows按cols列汇总到row
	 * @param row
	 * @param subRows
	 * @param cols
	 */
	private void sumRow(IRow row,List subRows,List cols){
		if(cols.size()==0){
			return;
		}
		if(subRows.size()==0){
			return;
		}
		for(Iterator iter=cols.iterator();iter.hasNext();){
		//	先清空再汇总
			row.getCell((String)iter.next()).setValue(null);
		}
		for(Iterator iter=subRows.iterator();iter.hasNext();){
			IRow tmpRow=(IRow)iter.next();
			for(Iterator iter2=cols.iterator();iter2.hasNext();){
				String key=(String)iter2.next();
				setCellValue(row.getCell(key), FDCNumberHelper.add(row.getCell(key).getValue(), tmpRow.getCell(key).getValue()));
			}
		}
	
	}
	private void setCellValue(com.kingdee.bos.ctrl.kdf.table.ICell cell,BigDecimal value){
		if(FDCHelper.toBigDecimal(value).signum()==0){
			value = FDCHelper.ZERO;
		}
		cell.setValue(value);
	}
	private RetValue retValue=null;
	protected void fetchData() throws Exception {
		super.fetchData();
		CurProjectInfo prj = getSelectProject();
		if(prj==null){
			retValue=null;
			return;
		}
		String prjId=prj.getId().toString();
		ParamValue param=new ParamValue();
		param.setString("prjId", prjId);
		retValue=ProjectCostRptFacadeFactory.getRemoteInstance().getData(param);
		this.acctStageMap=AccountStageHelper.initAccoutStageMap(null,prjId, null);
	}
	
	private CurProjectCollection getPrjs(){
		CurProjectCollection prjs=null;
		if(retValue!=null){
			prjs=(CurProjectCollection)retValue.get("CurProjectCollection");
		}
		return prjs; 
	}
	
	private ProductTypeCollection getProducts(){
		ProductTypeCollection products=null;
		if(retValue!=null){
			products=(ProductTypeCollection)retValue.get("ProductTypeCollection");
		}
		return products;
	}
	private CostAccountCollection getAccts(){
		CostAccountCollection accts=null;
		if(retValue!=null){
			accts=(CostAccountCollection)retValue.get("CostAccountCollection");
		}
		return accts;
	}
	
	private int getMaxLevel(){
		int maxLevel=0;
		if(retValue!=null){
			maxLevel=retValue.getInt("maxLevel");
		}
		return maxLevel;
	}
	
	private RetValue getRowRetValue(String acctNumber){
		RetValue rowRetValue=null;
		if(retValue!=null){
			rowRetValue=(RetValue)retValue.get(acctNumber);
		}
		return rowRetValue;
	}
	private Set headKeySet=new HashSet();
	private Set getHeadKeySet(){
		return headKeySet;
	}
	
	//用于存储合计列对应的要汇总的项目－产品列的信息
	private Map totalMap=new HashMap();
	private Map getHeadTotalKeyMap(){
		return totalMap;
	}

	/**
	 * @description 填充数据行
	 * @author 陈伟
	 * @createDate 2011-8-30
	 * @param row
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	private void loadRow(IRow row) throws BOSException {
		CostAccountInfo info=(CostAccountInfo)row.getUserObject();
		if(info==null){
			info = (CostAccountInfo)row.getCell("acctNumber").getUserObject();
		}
		String acctNumber=info.getLongNumber();
		RetValue rowRetValue=getRowRetValue(acctNumber);
		// 得到子集集合
		CostAccountCollection cion = FDCAimcostClientHelper.getCostAccountCollection(info, this
				.getSelectObjId(), acctChidrenMap);
		RetValue childrenRowRetValue = null;
		BigDecimal aimCost = FDCHelper.ZERO;
		BigDecimal happenCost = FDCHelper.ZERO;
		

		
		// 将子集值重新组装后给本级缓存；bey chenwei
		for (int i = 0, l = cion.size(); i < l; i++) {
			acctNumber = cion.get(i).getLongNumber();
			childrenRowRetValue = getRowRetValue(acctNumber);
			if (childrenRowRetValue != null) {
				// 非空处理
				if (rowRetValue == null) {
					rowRetValue = new RetValue();
				}
				aimCost = FDCHelper.add(aimCost, childrenRowRetValue.getBigDecimal("aimCost"));
				// 目标成本
				rowRetValue.setBigDecimal("aimCost", FDCHelper.add(rowRetValue
						.getBigDecimal("aimCost"), childrenRowRetValue.getBigDecimal("aimCost")));
				// 动态成本
				rowRetValue.setBigDecimal("dynCost", FDCHelper.add(rowRetValue
						.getBigDecimal("dynCost"), childrenRowRetValue.getBigDecimal("dynCost")));

				rowRetValue.setBigDecimal("waitCost", FDCHelper.add(rowRetValue
						.getBigDecimal("waitCost"), childrenRowRetValue.getBigDecimal("waitCost")));
				rowRetValue.setBigDecimal("dif", FDCHelper.add(rowRetValue.getBigDecimal("dif"),
						childrenRowRetValue.getBigDecimal("dif")));

				// 产品数据也做同样处理
				for (Iterator iter = getHeadKeySet().iterator(); iter.hasNext();) {
					String key = (String) iter.next();
					rowRetValue.setBigDecimal(key + "aimCost", FDCHelper.add(rowRetValue
							.getBigDecimal(key + "aimCost"), childrenRowRetValue.getBigDecimal(key
							+ "aimCost")));

					rowRetValue.setBigDecimal(key + "dynCost", FDCHelper.add(rowRetValue
							.getBigDecimal(key + "dynCost"), childrenRowRetValue.getBigDecimal(key
							+ "dynCost")));
					rowRetValue.setBigDecimal(key + "dif", FDCHelper.add(rowRetValue
							.getBigDecimal(key + "dif"), childrenRowRetValue.getBigDecimal(key
							+ "dif")));
				}

			}
		}
		if(rowRetValue==null){
			return;
		}
		
		if(!info.isIsLeaf() && !FDCHelper.isZero(rowRetValue.getBigDecimal("aimCost"))){
			row.getCell("aimCost").setUserObject("hasData");
		}
		//项目数据
		setCellValue(row.getCell("aimCost"), rowRetValue.getBigDecimal("aimCost"));
		setCellValue(row.getCell("dynCost"), rowRetValue.getBigDecimal("dynCost"));
		setCellValue(row.getCell("happenCost"), rowRetValue.getBigDecimal("happenCost"));
		setCellValue(row.getCell("waitCost"), FDCNumberHelper.subtract(rowRetValue.getBigDecimal("dynCost"), rowRetValue.getBigDecimal("happenCost")));
		setCellValue(row.getCell("dif"), FDCNumberHelper.subtract(rowRetValue.getBigDecimal("dynCost"), rowRetValue.getBigDecimal("aimCost")));
		
		//产品数据
		for(Iterator iter=getHeadKeySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			setCellValue(row.getCell(key+"aimCost"), rowRetValue.getBigDecimal(key+"aimCost"));
			setCellValue(row.getCell(key+"dynCost"), rowRetValue.getBigDecimal(key+"dynCost"));
			setCellValue(row.getCell(key+"happenCost"), rowRetValue.getBigDecimal(key+"happenCost"));
			setCellValue(row.getCell(key+"waitCost"), FDCNumberHelper.subtract(rowRetValue.getBigDecimal(key+"dynCost"), rowRetValue.getBigDecimal(key+"happenCost")));
			setCellValue(row.getCell(key+"dif"), FDCNumberHelper.subtract(rowRetValue.getBigDecimal(key+"dynCost"), rowRetValue.getBigDecimal(key+"aimCost")));
		}
		//合计行数据
		for(Iterator iter=getHeadTotalKeyMap().keySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			Set set=(Set)getHeadTotalKeyMap().get(key);
			if(set==null||set.size()==0){
				continue;
			}
			for(Iterator iter2=set.iterator();iter2.hasNext();){
				String subKey=(String)iter2.next();
				setCellValue(row.getCell(key+"aimCost"),FDCNumberHelper.add(row.getCell(key+"aimCost").getValue(), row.getCell(subKey+"aimCost").getValue()));
				setCellValue(row.getCell(key+"dynCost"),FDCNumberHelper.add(row.getCell(key+"dynCost").getValue(), row.getCell(subKey+"dynCost").getValue()));
				setCellValue(row.getCell(key+"happenCost"),FDCNumberHelper.add(row.getCell(key+"happenCost").getValue(), row.getCell(subKey+"happenCost").getValue()));
			}
			setCellValue(row.getCell(key+"waitCost"),FDCNumberHelper.subtract(row.getCell(key+"dynCost").getValue(), row.getCell(key+"happenCost").getValue()));
			setCellValue(row.getCell(key+"dif"),FDCNumberHelper.subtract(row.getCell(key+"dynCost").getValue(), row.getCell(key+"aimCost").getValue()));
		}
	}
	public boolean destroyWindow() {
		boolean destroyWindow = super.destroyWindow();
		if(destroyWindow){
			clear();
		}
		return destroyWindow;
	}
	protected void initWorkButton() {
		super.initWorkButton();
		actionUpdateData.putValue(javax.swing.Action.SMALL_ICON, EASResource.getIcon("imgTbtn_synchronization"));
		actionUpdateData.setEnabled(true);
		actionUpdateData.setVisible(true);
		FDCClientHelper.setActionEnable(new ItemAction[]{actionAddNew,actionEdit,actionLocate,actionView,actionRemove,actionQuery}, false);
		this.menuEdit.setEnabled(false);
		this.menuEdit.setVisible(false);
	}
	private void clear(){
		getHeadKeySet().clear();
		if(retValue!=null){
			retValue.clear();
		}
	}
	public void actionUpdateData_actionPerformed(ActionEvent e)
			throws Exception {
		CurProjectInfo prj = getSelectProject();
		if(prj==null){
			retValue=null;
			return;
		}
		String prjId=prj.getId().toString();
		ProjectCostRptFacadeFactory.getRemoteInstance().updateData(prjId);
	}
	
	public int getRowCountFromDB() {
//		return super.getRowCountFromDB();
		return -1;
	}
	
	/**
	 * @description 可拆分科目级次
	 * @author 陈伟
	 * @createDate 2011-8-30
	 * @param e
	 * @throws Exception
	 * @version EAS7.0
	 * @see com.kingdee.eas.fdc.aimcost.client.AbstractDyCostSumUI#actionSplit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		// 当前按钮显示内容
		String txt = this.btnSplit.getText();
		if (txt.equals(FDCAimcostClientHelper.getRes("showSplit"))) {
			this.btnSplit.setText(FDCAimcostClientHelper.getRes("showAll"));
			this.isSplitBtn.setText(FDCAimcostClientHelper.getRes("showAll"));
			isShowSplitAcct = true;
		} else {
			this.btnSplit.setText(FDCAimcostClientHelper.getRes("showSplit"));
			this.isSplitBtn.setText(FDCAimcostClientHelper.getRes("showSplit"));
			isShowSplitAcct = false;
		}
		actionRefresh_actionPerformed(null);
	}
}