/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.ISortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.QueryFieldInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.material.PartAMaterialFactory;
import com.kingdee.eas.framework.AbstractTreeBaseInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MaterialMeasureFullListUI extends AbstractMaterialMeasureFullListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialMeasureFullListUI.class);
    private String selectKeyValue = null;
    protected int colIndex = -1;
	protected int sortType = ISortManager.SORT_ASCEND;
	private BigDecimal sLeftAmount = FDCHelper.ZERO; 	//剩余采购金额合计
	private BigDecimal sLatestAmount = FDCHelper.ZERO;	//最新造价合计
//	private Map proLongNameMap=new HashMap();
	
    /**
     * output class constructor
     */
    public MaterialMeasureFullListUI() throws Exception
    {
        super();
    }

    //需要定位的列
    protected String[] getLocateNames() {
    	String[] strs = new String[1];
    	strs[0] = "mainContractBill.number";
    	return strs;
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
    	//如果是点击条件中的三列，那么就不进行排序
    	if (isOrderForClickTableHead() && e.getType() == KDTStyleConstants.HEAD_ROW
                && e.getButton() == MouseEvent.BUTTON1 
                && (tblMain.getColumn(e.getColIndex()).getKey().equals("MaterialconfirmBillEntry.price")
                		|| tblMain.getColumn(e.getColIndex()).getKey().equals("leftAmount")
                		|| tblMain.getColumn(e.getColIndex()).getKey().equals("latestAmount"))){
			return;
    	}
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
        
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    protected boolean initDefaultFilter() {
    	// TODO 自动生成方法存根
    	return true;
    }
    
    public void onLoad() throws Exception {
    	// TODO 自动生成方法存根
//    	setIsIgnoreOrder(false);
    	tblMain.addKDTDataFillListener(new KDTDataFillListener() {
			public void afterDataFill(KDTDataRequestEvent e) {
				tblMain_tableAfterDataFill(e);
			}
		});
    	
//    	this.proLongNameMap=FDCHelper.createTreeDataMap(CurProjectFactory.getRemoteInstance(),"name","\\");
    	super.onLoad();
    	this.btnAddNew.setVisible(false);
    	this.btnAddNew.setEnabled(false);
    	this.btnEdit.setVisible(false);
    	this.btnEdit.setEnabled(false);
    	this.btnWorkFlowG.setVisible(false);
    	this.btnWorkFlowG.setEnabled(false);
    	this.btnWorkFlowList.setVisible(false);
    	this.btnWorkFlowList.setEnabled(false);
    	this.btnCreateTo.setVisible(false);
    	this.btnCreateTo.setEnabled(false);
    	this.btnTraceUp.setVisible(false);
    	this.btnTraceUp.setEnabled(false);
    	this.btnTraceDown.setVisible(false);
    	this.btnTraceDown.setEnabled(false);
    	this.btnAuditResult.setVisible(false);
    	this.btnAuditResult.setEnabled(false);
    	this.btnAttachment.setVisible(false);
    	this.btnAttachment.setEnabled(false);
    	this.btnRemove.setVisible(false);
    	this.btnRemove.setEnabled(false);
    	this.menuItemAddNew.setVisible(false);
    	this.menuItemAddNew.setEnabled(false);
    	this.menuItemTraceUp.setVisible(false);
    	this.menuItemTraceUp.setEnabled(false);
    	this.menuItemTraceDown.setVisible(false);
    	this.menuItemTraceDown.setEnabled(false);
    	this.MenuItemAttachment.setVisible(false);
    	this.MenuItemAttachment.setEnabled(false);
    	this.menuItemCreateTo.setVisible(false);
    	this.menuItemCreateTo.setEnabled(false);
    	this.menuEdit.setVisible(false);
    	this.menuEdit.setEnabled(false);
    	this.menuWorkFlow.setVisible(false);
    	this.menuWorkFlow.setEnabled(false);
    	this.menuBiz.setVisible(false);
    	this.menuBiz.setEnabled(false);
    	
//  	给tHelper赋值之后，就可以用helper类中的保存方法
    	tHelper = new TablePreferencesHelper(this);
    }
    
    //重载这个方法，可以使得我们增加的filteritemInfo能加到过滤之后
    protected FilterInfo getDefaultFilterForQuery() {
    	FilterInfo filter = super.getDefaultFilterForQuery();
    	AbstractTreeBaseInfo cu = SysContext.getSysContext().getCurrentCtrlUnit();
		if(cu!=null&&cu.getLongNumber()!=null){	
			//做一个匹配，与当前长编码左边相同的所有长编码都显示出来
			try {
				FilterInfo curFilter = new FilterInfo();
				curFilter.getFilterItems().add((new FilterItemInfo("CU.longNumber",cu.getLongNumber()+"%",CompareType.LIKE)));
				filter.mergeFilter(curFilter, "or");
//				filter.setMaskString("#0 or #1 or #2 or #3");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
    	return filter;
    }
    
    /**
     * 获取Query执行接口。
     *
     * @param queryPK
     * @param viewInfo
     * @return
     */
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo)
    {
    	EntityViewInfo view=(EntityViewInfo)viewInfo.clone();
    	
//    	去掉id排序后，在调用基类的时候，就会是isIgnoreOrder为false
    	if(view.getSorter()!= null && mainQuery.getSorter().size()==1){
    		mainQuery.getSorter().clear();
    	}
    	IQueryExecutor exec = super.getQueryExecutor(queryPK,viewInfo);
//  	是否忽略Query中定义的Order By信息，默认为不忽略
//    	exec.option().isIgnoreOrder = false;	
    	exec.option().isAutoIgnoreZero = false;  //不忽略0值，即要显示出来。 Added by Owen_wen 2010-09-08
    	return exec;
    	
    }
    
    
    protected void tblMain_tableAfterDataFill(KDTDataRequestEvent e) {
		int start = e.getFirstRow();
		int end = e.getLastRow();
		setRowColor(start, end);

	}
    
    protected void setRowColor(int start, int end) {
		//用红色字体显示剩余数量小于零
    	int quantity = 0;
    	BigDecimal amount;
	    Color backColor;
		for (int i = start; i <= end; i++) {
			IRow row = tblMain.getRow(i);
			if(row.getCell("leftQuantity") != null && 
					row.getCell("leftQuantity").getValue() != null){
				
				quantity = row.getCell("leftQuantity").getValue().toString().compareTo("0");
			}
			
			if(quantity < 0){
				amount = (BigDecimal)row.getCell("MaterialconfirmBillEntry.amount").getValue();
				row.getCell("latestAmount").setValue(amount);
				row.getCell("leftAmount").setValue(FDCHelper.ZERO);
//				backColor = row.getStyleAttributes().getBackground();
//				row.getStyleAttributes().setBackground(Color.ORANGE);
				
				row.getCell("leftQuantity").getStyleAttributes().setFontColor(Color.RED);
				
				//前几列在融合后会自动成为融合行中第一行，所以手动赋颜色
//				row.getCell("curProject.name").getFormattedStyleAttributes().setBackground(backColor);
//				row.getCell("contractType.name").getFormattedStyleAttributes().setBackground(backColor);
//				row.getCell("mainContractBill.number").getFormattedStyleAttributes().setBackground(backColor);
//				row.getCell("partB.name").getFormattedStyleAttributes().setBackground(backColor);
//				row.getCell("mainContractBill.name").getFormattedStyleAttributes().setBackground(backColor);
			}
			
//			row.getCell("curProject.name").getFormattedStyleAttributes().setBackground(FDCHelper.)
		}
    }
			
 
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return PartAMaterialFactory.getRemoteInstance();
    }
    
    protected String getEditUIName() {
    	//增加一个变量，使得在明细界面可以知道是此界面在调用
    	SysContext.getSysContext().setProperty("measure",new String("measure"));
    	return PartAMaterialEditUI.class.getName();
    }
    
//    protected String getKeyFieldName() {
//    	// TODO 自动生成方法存根
//    	return "entrys.id";
//    }
    
    /**
	 * 获取当前选择行的主键
	 * 
	 * @return 返回当前选择行的主键，若当前选择行为空，或者当前选中行的主键列为空，则返回null
	 */
	protected String getSelectedKeyValue(KDTable table) {
		//String value = super.getSelectedKeyValue();
		
	    KDTSelectBlock selectBlock = table.getSelectManager().get();
	    selectKeyValue = null;
	
	    if (selectBlock != null) {
	        int rowIndex = selectBlock.getTop();
	        IRow row = table.getRow(rowIndex);
	        if (row == null) 
	        {
	            return null;
	        }
	        
	        ICell cell = row.getCell(getKeyFieldName());
	
	        if (cell == null) {
	            MsgBox.showError(EASResource
	                    .getString(FrameWorkClientUtils.strResource
	                            + "Error_KeyField_Fail"));
	            SysUtil.abort();
	        }
	
	        Object keyValue = cell.getValue();
	        
	        if (keyValue != null) {
	        	selectKeyValue = keyValue.toString();
	        }
	    }   
	    	
	    return selectKeyValue;
	}
	
	 protected String getSelectedValue(String key) {
	    	if(FDCHelper.isEmpty(key)){
	    		return null;
	    	}
	    	
	    	IRow row=KDTableUtil.getSelectedRow(tblMain);
	    	if(row!=null&&row.getCell(key)!=null){
	    		return (String)row.getCell(key).getValue();
	    	}
	    	return null;
		}
	 
	  protected String getSelectedKeyValue() {
			return getSelectedKeyValue(getBillListTable());
		}
    /**
	 * 
	 * 描述：为当前单据的新增、编辑、查看准备Context
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.CoreBillListUI#prepareUIContext(com.kingdee.eas.common.client.UIContext, java.awt.event.ActionEvent)
	 */
	  protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		  super.prepareUIContext(uiContext, e);
		  ItemAction act = getActionFromActionEvent(e);
		  
		  if(act.equals(actionEdit) || act.equals(actionView)) {
			  uiContext.put(UIContext.ID, getSelectedKeyValue());
		  }
		  
		  uiContext.put("contractBillId", this.getSelectedValue("mainContractBill.id"));
		  uiContext.put("contractBillNumber", this.getSelectedValue("mainContractBill.number"));
		  uiContext.put("projectId", this.getSelectedValue("project.id"));
	  }
	  
    protected void getRowSetBeforeFillTable(IRowSet rowSet) {
    	// TODO 自动生成方法存根
    	try {
    		rowSet.beforeFirst();
    		
    		Map rMap = new HashMap();
        	Map topMap = new HashMap();
			while(rowSet.next()){
	    		String contractbillId = rowSet.getString("mainContractBill.id");
	    		String materialId = rowSet.getString("material.id");
	    		String createTime = rowSet.getString("MaterialConfirmBill.createTime");
	    		String key = contractbillId + materialId;
	    		Map map=new HashMap();
	    		map.put("contractbillId", contractbillId);
	    		map.put("materialId", materialId);
	    		map.put("createTime", createTime);
	    		topMap.put(key,map);
	    	}
			
			rMap = PartAMaterialFactory.getRemoteInstance().fetchBackData(topMap);
			
			rowSet.beforeFirst();
			Set set = rMap.keySet();
			Iterator it = set.iterator();
			
			while(it.hasNext()){

				Map map = (Map)rMap.get(it.next().toString());
				
				//取得服务器端传回的值
				BigDecimal price = (BigDecimal)map.get("price");
				String cbillId = map.get("contractbillId").toString();
				String mId = map.get("materialId").toString();
				while(rowSet.next()){
					//得到界面上的合同以及物料id
					String contractbillId = rowSet.getString("mainContractBill.id");
					String materialId = rowSet.getString("material.id");
					
					BigDecimal leftQuantity = rowSet.getBigDecimal("leftQuantity");
					BigDecimal rAmount = rowSet.getBigDecimal("MaterialcBillEntry.amount");
					BigDecimal leftAmount = price.multiply(FDCHelper.toBigDecimal(rowSet.getBigDecimal("leftQuantity")));
//					BigDecimal leftAmount = price.multiply((BigDecimal)rowSet.getBigDecimal("leftQuantity"));
//					BigDecimal latestAmount = price.add(leftAmount);
					BigDecimal latestAmount = FDCHelper.add(rAmount,leftAmount);
					if(cbillId.equals(contractbillId) && mId.equals(materialId)){
						rowSet.updateBigDecimal("MaterialconfirmBillEntry.price",price);
						rowSet.updateBigDecimal("leftAmount",leftAmount);
						rowSet.updateBigDecimal("latestAmount",latestAmount);
						
						//合计最新金额与剩余采购金额
						if(leftQuantity != null && leftQuantity.toString().compareTo("0") < 0){
							sLatestAmount = FDCHelper.add(sLatestAmount,rAmount);
//							sLatestAmount = sLatestAmount.add(rAmount);
						}else{
							sLatestAmount = FDCHelper.add(sLatestAmount,latestAmount);
							sLeftAmount = FDCHelper.add(sLeftAmount,leftAmount);
//							sLatestAmount = sLatestAmount.add(latestAmount);
//							sLeftAmount = sLeftAmount.add(leftAmount);
						}
					}
				}
				
				rowSet.beforeFirst();
			}
			rowSet.beforeFirst();
    	} catch (SQLException e) {
    		// TODO 自动生成 catch 块
    		e.printStackTrace();
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

    }
    
    //数据融合
    public String[] getMergeColumnKeys() {
		  
		  return new String[]{"curProject.displayName", "contractType.name", "mainContractBill.number",
				  "mainContractBill.name", "partB.name"};
	  }
    
    protected void refresh(ActionEvent e) throws Exception {
    	// TODO 自动生成方法存根
    	super.refresh(e);
    }
    
    public void refreshList() throws Exception {
    	// TODO 自动生成方法存根
    	super.refreshList();
    }
    public void refreshListForOrder() throws Exception {
    	// TODO 自动生成方法存根
    	super.refreshListForOrder();
    }
    
    protected boolean isFootVisible() {
    	
    	return true;
    }
   
    /**
     * 添加合计行
     * 2005-03-09 haiti_yang
     */
    //
    protected IRow appendFootRow()
    {
        if (!this.isFootVisible()) return null;
        try{
            List fieldSumList=this.getFieldSumList();

            if (fieldSumList.size()>0)
            {
                //进行列计算
                QueryFieldInfo fieldInfo[]=new QueryFieldInfo[fieldSumList.size()];
                System.arraycopy(fieldSumList.toArray(),0,fieldInfo,0,fieldSumList.size());
                IQueryExecutor iexec = getQueryExecutor(this.mainQueryPK, this.mainQuery);

                IRowSet singleRowSet=iexec.sum(fieldInfo);

                if (singleRowSet==null)
                    return null;
                singleRowSet.next();
                //生成计算列
                IRow footRow = FDCTableHelper.generateFootRow(tblMain);
                String colFormat="###,##0.00";
                int columnCount=this.tblMain.getColumnCount();
                for (int c=0;c<columnCount;c++){
                    String fieldName=this.tblMain.getColumn(c).getFieldName();
                    for (int i=0;i<fieldSumList.size();i++)
                    {
                        QueryFieldInfo info=(QueryFieldInfo)fieldSumList.get(i);
                        String name=info.getName();
                        if (name.equalsIgnoreCase(fieldName))
                        {
                        	ICell cell=footRow.getCell(c);
                        	cell.getStyleAttributes().setNumberFormat(colFormat);
                            cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
                            cell.getStyleAttributes().setFontColor(Color.BLACK);

                            cell.setValue(singleRowSet.getString(name));
                            
                            //如何这两列为剩余采购金额和最新造价，就用计算得到的值赋值
                            if(name.equalsIgnoreCase("leftAmount")){
                            	cell.setValue(sLeftAmount);
                            	sLeftAmount = FDCHelper.ZERO;
                            }
                            else if(name.equalsIgnoreCase("latestAmount")){
                            	cell.setValue(sLatestAmount);
                            	sLatestAmount = FDCHelper.ZERO;
                            }
                        }
                    }
                }
                footRow.getStyleAttributes().setBackground(new Color(0xf6, 0xf6, 0xbf));
                return footRow;
            }
        }catch(Exception E){
            E.printStackTrace();
            logger.error(E);
        }
        return null;
    }
    
    public void onGetRowSet(IRowSet rowSet) {
    	try {
    		rowSet.beforeFirst();
    		while (rowSet.next()) {
    			String displayName=rowSet.getString("curProject.displayName");
//  			String id=rowSet.getString("curProject.id");
    			String orgName=rowSet.getString("orgUnit.name");
    			if(orgName!=null){
    				displayName= orgName + "\\"+displayName;
    			}
//  			String proName = (String) this.proLongNameMap.get(id);
    			
    			rowSet.updateString("curProject.displayName",displayName);
    		}
    		rowSet.beforeFirst();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	super.onGetRowSet(rowSet);
    }

}