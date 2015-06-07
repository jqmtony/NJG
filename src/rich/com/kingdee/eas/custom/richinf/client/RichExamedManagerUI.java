/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.client;

import java.awt.event.ActionEvent;
import java.sql.ResultSetMetaData;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fi.ar.client.OtherBillEditUI;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RichExamedManagerUI extends AbstractRichExamedManagerUI
{
    private static final Logger logger = CoreUIObject.getLogger(RichExamedManagerUI.class);
    
    private String InvoiceRequestQuery = "com.kingdee.eas.custom.richinf.query.F7RichInvoiceRequestQuery";
    
    private String OtherBillQuery = "com.kingdee.eas.custom.richinf.query.F7OtherBill";
    
    private String CompanyoffQuery = "com.kingdee.eas.custom.richinf.query.F7RichCompayWriteOffQuery";
    
    private String CustomerOffQuery = "com.kingdee.eas.custom.richinf.query.F7RichCustomWriteOffQuery";
    
    
    /**
     * output class constructor
     */
    public RichExamedManagerUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.VIRTUAL_MODE_PAGE);
		
		this.tblMain.addKDTDataFillListener(new KDTDataFillListener() {
            public void afterDataFill(KDTDataRequestEvent e)
            {
                try
                {
                    tblMain_afterDataFill(e);
                }
                catch(Exception exc)
                {
                    handUIException(exc);
                }
            }
        });
    	super.onLoad();
    	
    	initButton();
    	
    	initDefault();
    }
    
    
    private void initButton(){
    	this.actionAddNew.setVisible(false);
    	this.actionEdit.setVisible(false);
    	this.actionRemove.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	
    	this.actionAddNewInviton.setEnabled(true);
    	this.actionInvoiteCreatToBill.setEnabled(true);
    	this.actionCompnyOff.setEnabled(true);
    	this.actionCustomerOff.setEnabled(true);
    }
    
    private void initDefault(){
    	
    }
    
    private void tblMain_afterDataFill(KDTDataRequestEvent e){
		 for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
		 }
	}
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
    	return super.getQueryExecutor(queryPK, viewInfo);
    }
    
    public void actionAddNewInviton_actionPerformed(ActionEvent e)throws Exception {
    	super.actionAddNewInviton_actionPerformed(e);
        UIContext context = new UIContext(this);//UIFactoryName.MODEL
        IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RichInvoiceRequestEditUI.class.getName(), context, null, OprtState.ADDNEW);
        uiWindow.show();
    }
    
    public void actionInvoiteCreatToBill_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionInvoiteCreatToBill_actionPerformed(e);
    }
    
    public void actionCompnyOff_actionPerformed(ActionEvent e) throws Exception {
    	super.actionCompnyOff_actionPerformed(e);
    	UIContext context = new UIContext(this);//UIFactoryName.MODEL
        IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RichCompayWriteOffEditUI.class.getName(), context, null, OprtState.ADDNEW);
        uiWindow.show();
    }
    
    public void actionCustomerOff_actionPerformed(ActionEvent e)throws Exception {
    	super.actionCustomerOff_actionPerformed(e);
    	UIContext context = new UIContext(this);//UIFactoryName.MODEL
        IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RichCustomWriteOffEditUI.class.getName(), context, null, OprtState.ADDNEW);
        uiWindow.show();
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    
    protected void tblCompanyoff_tableClicked(KDTMouseEvent e) throws Exception {
    	super.tblCompanyoff_tableClicked(e);
    	openBillWindows(getSelecter(e), RichCompayWriteOffEditUI.class.getName(), UIFactoryName.MODEL, OprtState.VIEW);
    }
    
    protected void tblCustomerOff_tableClicked(KDTMouseEvent e)throws Exception {
    	super.tblCustomerOff_tableClicked(e);
    	openBillWindows(getSelecter(e), RichCustomWriteOffEditUI.class.getName(), UIFactoryName.MODEL, OprtState.VIEW);
    }
    
    protected void tblInvoiceRequest_tableClicked(KDTMouseEvent e)throws Exception {
    	super.tblInvoiceRequest_tableClicked(e);
    	openBillWindows(getSelecter(e), RichInvoiceRequestEditUI.class.getName(), UIFactoryName.MODEL, OprtState.VIEW);
    }
    
    protected void tblOtherBill_tableClicked(KDTMouseEvent e) throws Exception {
    	super.tblOtherBill_tableClicked(e);
    	openBillWindows(getSelecter(e), OtherBillEditUI.class.getName(), UIFactoryName.MODEL, OprtState.VIEW);
    }
    
    private String  getSelecter(KDTMouseEvent e){
    	if(e.getClickCount()!=2||e.getRowIndex()==-1)
    		return null;
    	
    	KDTable table = (KDTable)e.getSource();
    	return UIRuleUtil.getString(table.getCell(e.getRowIndex(), "id").getValue());
    }
    
    private void openBillWindows(String billId,String uiClass,String UIFactoryName,String optype){
    	if(billId==null)
    		return;
    	
    	IUIWindow uiWindow = null;
        UIContext context = new UIContext(this);//UIFactoryName.MODEL
        context.put("ID", billId);
        try {
			uiWindow = UIFactory.createUIFactory(UIFactoryName).create(uiClass, context, null, optype);
		} catch (UIException e) {
			e.printStackTrace();
		}
        uiWindow.show();
    }
    
    
    private int showQueryDate(KDTable tblMain,String queryName,EntityViewInfo evi) throws Exception
    {
    	tblMain.setEnabled(false);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	IMetaDataPK queryPK = new MetaDataPK(queryName);
    	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(queryPK );
    	exec.option().isAutoIgnoreZero = true;
    	exec.option().isAutoTranslateBoolean = true;
    	exec.option().isAutoTranslateEnum = true; 
        exec.setObjectView(evi);
    	IRowSet rowSet = exec.executeQuery();
    	IRow row = null;
    	tblMain.removeRows();
    	int maxLevel = 0;
        for(int i=0; rowSet.next(); i++)
        {
        	row = tblMain.addRow();
        	ResultSetMetaData o_dbMd = rowSet.getMetaData();
        	int cols = o_dbMd.getColumnCount(); //取得query的字段数
        	Object[] colname = new Object[cols]; //取得query的字段名称
            for (int j = 1; j < cols+1; j++) {
              colname[j-1] = o_dbMd.getColumnName(j);
              if(colname[j-1]!=null && row.getCell(colname[j-1].toString())!=null
            		  && rowSet.getObject(colname[j-1].toString())!=null)
            		  row.getCell(colname[j-1].toString()).setValue(rowSet.getObject(colname[j-1].toString()));
            }
        }
        return maxLevel+1;
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
//        super.tblMain_tableClicked(e);
    	openBillWindows(getSelecter(e), RichExamedEditUI.class.getName(), UIFactoryName.MODEL, OprtState.VIEW);
    }

    
    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
        String billId = this.getSelectedKeyValue()+"";
        if(billId==null)
        	billId = "";
        
        EntityViewInfo viewInfo = new EntityViewInfo();
        FilterInfo filInfo = new FilterInfo();
        filInfo.getFilterItems().add(new FilterItemInfo("id","select fparentid from CT_RIC_RichInvoiceRequestEntry where CFDjdID='"+billId+"'",CompareType.INNER));
        viewInfo.setFilter(filInfo);
        showQueryDate(tblInvoiceRequest, InvoiceRequestQuery, viewInfo);
        
        viewInfo = new EntityViewInfo();
        filInfo = new FilterInfo();
        filInfo.getFilterItems().add(new FilterItemInfo("sourceBillId",billId,CompareType.EQUALS));
        viewInfo.setFilter(filInfo);
        showQueryDate(tblOtherBill, OtherBillQuery, viewInfo);
        
        viewInfo = new EntityViewInfo();
        filInfo = new FilterInfo();
        filInfo.getFilterItems().add(new FilterItemInfo("id","select fparentid from CT_RIC_RichCWODE where CFDjNoID='"+billId+"'",CompareType.INNER));
        viewInfo.setFilter(filInfo);
        showQueryDate(tblCompanyoff, CompanyoffQuery, viewInfo);
        
        viewInfo = new EntityViewInfo();
        filInfo = new FilterInfo();
        filInfo.getFilterItems().add(new FilterItemInfo("id","select fparentid from CT_RIC_RichCWODE where CFDjNoID='"+billId+"'",CompareType.INNER));
        viewInfo.setFilter(filInfo);
        showQueryDate(tblCustomerOff, CustomerOffQuery, viewInfo);
    }


    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.richinf.RichExamedFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.custom.richinf.RichExamedInfo objectValue = new com.kingdee.eas.custom.richinf.RichExamedInfo();
		
        return objectValue;
    }

}