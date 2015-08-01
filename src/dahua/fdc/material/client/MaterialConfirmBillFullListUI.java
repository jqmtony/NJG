/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MaterialConfirmBillFullListUI extends AbstractMaterialConfirmBillFullListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialConfirmBillFullListUI.class);
    private final static String commonQueryRes = "com.kingdee.eas.base.commonquery.client.CommonQueryPanel";
    private String selectKeyValue = null;
    private final String dialogName = "材料价格走势";
//    private Map proLongNameMap=new HashMap();
    
    /**
     * output class constructor
     */
    public MaterialConfirmBillFullListUI() throws Exception
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
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    protected String getEditUIName() {
    	return MaterialConfirmBillEditUI.class.getName();
    }
    
    protected ICoreBase getBizInterface() throws Exception {
    	return MaterialConfirmBillFactory.getRemoteInstance();
    }
    
	
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
	  
	  public void onLoad() throws Exception {
//		  setIsIgnoreOrder(false);
		  setDialogTitle(dialogName);
//		  this.proLongNameMap=FDCHelper.createTreeDataMap(CurProjectFactory.getRemoteInstance(),"name","\\");
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
		  
		  //给tHelper赋值之后，就可以用helper类中的保存方法
		  tHelper = new TablePreferencesHelper(this);
	  }
	  
	  private void setDialogTitle(String dialogName) {
			if (dialogName == null || dialogName.trim().equalsIgnoreCase("")) {
				dialogName = EASResource.getString(commonQueryRes, "dialogName");
			}
			this.setUITitle(dialogName);
		}
	  
//	  重载这个方法，可以使得我们增加的filteritemInfo能加到过滤之后
	  protected FilterInfo getDefaultFilterForQuery() {
		  // TODO 自动生成方法存根
		  FilterInfo filter = super.getDefaultFilterForQuery();
		  AbstractTreeBaseInfo cu = SysContext.getSysContext().getCurrentCtrlUnit();
		  if(cu!=null&&cu.getLongNumber()!=null){	
			  //做一个匹配，与当前长编码左边相同的所有长编码都显示出来
			  try {
				  FilterInfo curFilter = new FilterInfo();
				  curFilter.getFilterItems().add((new FilterItemInfo("CU.longNumber",cu.getLongNumber()+"%",CompareType.LIKE)));
				  filter.mergeFilter(curFilter, "or");
//				  filter.setMaskString("#0 or #1 or #2 or #3");
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
	    	EntityViewInfo view = (EntityViewInfo)viewInfo.clone();
	    	
	    	//去掉id排序后，在调用基类的时候，就会是isIgnoreOrder为false
	    	if(view.getSorter()!=null && mainQuery.getSorter().size()==1){
	    		mainQuery.getSorter().clear();
	    	}
	    	
	    	IQueryExecutor exec = super.getQueryExecutor(queryPK,viewInfo);
	    	
//	    	是否忽略Query中定义的Order By信息，默认为不忽略
//	    	exec.option().isIgnoreOrder = false;
	    	return exec;
	    }
	  
	  /**
	   * 
	   * 初始化默认过滤条件
	   * 
	   * @return 如果重载了（即做了初始化动作），请返回true;默认返回false;
	   */
	  protected boolean initDefaultFilter() {
		  return true;
	  }
	  
	  public String[] getMergeColumnKeys() {
		  
		  return new String[]{"material.number","material.name","material.model","baseUnit.name"};
	  }
	  
	  public void onGetRowSet(IRowSet rowSet) {
		  try {
			  rowSet.beforeFirst();
			  while (rowSet.next()) {
				  String displayName=rowSet.getString("curProject.displayName");
//				  String id=rowSet.getString("curProject.id");
				  String orgName=rowSet.getString("orgUnit.name");
				  if(orgName!=null){
					  displayName= orgName + "\\"+displayName;
				  }
//				  String proName = (String) this.proLongNameMap.get(id);
				  
				  rowSet.updateString("curProject.displayName",displayName);
			  }
			  rowSet.beforeFirst();
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
		  super.onGetRowSet(rowSet);
	  }
	
}