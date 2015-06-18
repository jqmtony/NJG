/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.json.JSONObject;
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
import com.kingdee.eas.basedata.master.cssp.CustomerCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.custom.richbase.CustomerSyncLogFactory;
import com.kingdee.eas.custom.richbase.CustomerSyncLogInfo;
import com.kingdee.eas.custom.richbase.ICustomerSyncLog;
import com.kingdee.eas.custom.richtimer.client.ReserveServiceLocator;
import com.kingdee.eas.custom.richtimer.client.ReserveServicePortType;
import com.kingdee.eas.fi.ar.client.OtherBillEditUI;
import com.kingdee.eas.fm.common.FMIsqlFacadeFactory;
import com.kingdee.eas.fm.common.IFMIsqlFacade;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
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
    
    private final String sq_sql = "select FDestObjectID from T_BOT_Relation where FSrcObjectID=?";
    
    private final String fp_sql = "select FDestObjectID from T_BOT_Relation where FSrcObjectID in (select FDestObjectID from T_BOT_Relation where FSrcObjectID=?)";
    
    private final String nb_sql = "select fparentid from CT_RIC_CompayWriteDj where CFDjNoID=?";
    
    private final String kh_sql = "select fparentid from CT_RIC_RichCWODE where CFDjNoID=?";
    
    private final Color blue = new Color(175,169,250);
    
    private final Color yellow = new Color(222,218,137);
    
    private final Color green = new Color(140,251,149);
    
    private final Color red = new Color(241,115,88);
    
    public String getEntriesName() {
    	return super.getEntriesName();
    }
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
    	actionSyncustomer.setEnabled(true);
    	actionViewLog.setEnabled(true);
    	this.actionAddNew.setVisible(false);
    	this.actionEdit.setVisible(false);
    	this.actionRemove.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	
    	this.actionAddNewInviton.setEnabled(true);
    	this.actionInvoiteCreatToBill.setEnabled(true);
    	this.actionCompnyOff.setEnabled(true);
    	this.actionCustomerOff.setEnabled(true);
    	actionInvoiteCreatToBill.setVisible(false);
    	actionAddNewInviton.setVisible(false);
    	setUITitle("\u6211\u7684\u5de5\u4f5c\u53f0");
    }
    
    private void initDefault(){
    	tblMain.getColumn("yhxAmount").getStyleAttributes().setNumberFormat("#,##0.00");
    	kDLabel1.setOpaque(true);
    	kDLabel2.setOpaque(true);
    	kDLabel3.setOpaque(true);
    	kDLabel4.setOpaque(true);
    	kDLabel1.setBackground(green);
    	kDLabel2.setBackground(blue);
    	kDLabel3.setBackground(yellow);
    	kDLabel4.setBackground(red);
    }
    
    private void tblMain_afterDataFill(KDTDataRequestEvent e){
    	IFMIsqlFacade iff = null;
    	try {
    		iff = FMIsqlFacadeFactory.getRemoteInstance();
		} catch (BOSException e1) {
			handUIException(e1);
		}
		IRowSet rs = null;
		BigDecimal yhx = null;
		BigDecimal amount = null;
		Object[] ps = new Object[1];
		StyleAttributes sab = null;
		for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
			ps[0] = (String)tblMain.getCell(i,"id").getValue();
			//判断是否已进行开票申请
			try {
				rs = iff.executeQuery(sq_sql,ps);
				sab = tblMain.getRow(i).getStyleAttributes();
				if(rs.size() > 0) {
					sab.setBackground(green);
					rs = iff.executeQuery(fp_sql,ps);
					if(rs.size() > 0) {
						sab.setBackground(blue);
					}
				}
				rs = iff.executeQuery(nb_sql,ps);
				if(rs.size() > 0) {
					yhx = (BigDecimal)tblMain.getCell(i,"yhxAmount").getValue();
					amount = (BigDecimal)tblMain.getCell(i,"amount").getValue();
					if(amount!=null && yhx != null && amount.compareTo(yhx) == 0) {
						sab.setBackground(red);
					}else {
						sab.setBackground(yellow);
					}
				}else {
					rs = iff.executeQuery(kh_sql,ps);
					if(rs.size() > 0) {
						yhx = (BigDecimal)tblMain.getCell(i,"yhxAmount").getValue();
						amount = (BigDecimal)tblMain.getCell(i,"amount").getValue();
						if(amount!=null && yhx != null && amount.compareTo(yhx) == 0) {
							sab.setBackground(red);
						}else {
							sab.setBackground(yellow);
						}
					}
				}
			} catch (EASBizException e1) {
				handUIException(e1);
			} catch (BOSException e1) {
				handUIException(e1);
			} 
		 }
	}
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
    	EntityViewInfo newviewInfo = (EntityViewInfo) viewInfo.clone();
    	PersonInfo pinfo = SysContext.getSysContext().getCurrentUserInfo().getPerson();
    	FilterInfo filter = new FilterInfo();
    	if(pinfo != null) {
    		filter.getFilterItems().add(new FilterItemInfo("sales.id",pinfo.getId().toString()));
    	}
    	if(newviewInfo.getFilter()!=null)
    	{
    		try {
				newviewInfo.getFilter().mergeFilter(filter,"and");
			} catch (BOSException e) {
				handUIException(e);
			}
    	}else{
    		newviewInfo.setFilter(filter);
    	}
    	
    	return super.getQueryExecutor(queryPK, newviewInfo);
    }
    
    public void actionCreateTo_actionPerformed(ActionEvent e)throws Exception {
    	KDTSelectBlock selectBlock = null;
    	KDTSelectManager selectManger = tblMain.getSelectManager();
    	for (int i = 0; i < selectManger.size(); i++) {
    		selectBlock = selectManger.get(i);
    		for (int j = selectBlock.getBeginRow(); j <=selectBlock.getEndRow(); j++) {
    			BigDecimal yhx = (BigDecimal)tblMain.getCell(j,"yhxAmount").getValue();
    			if(yhx != null){
    				BigDecimal total = (BigDecimal)tblMain.getCell(j,"amount").getValue();
    				if(yhx.compareTo(total) == 0){
    					MsgBox.showInfo("第"+(j+1)+"行到检单已全部核销，不能进行开票申请！请重新选择！");
    		    		SysUtil.abort();
    				}
    			}
			}
		}
    	
    	super.actionCreateTo_actionPerformed(e);
    }
    
    public void actionInvoiteCreatToBill_actionPerformed(ActionEvent e)
    		throws Exception {
//    	super.actionInvoiteCreatToBill_actionPerformed(e);
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
			handUIException(e);
		}
        uiWindow.show();
    }
    
    
    private int showQueryDate(KDTable tblMain,String queryName,EntityViewInfo evi) throws Exception
    {
    	tblMain.setEnabled(false);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	IMetaDataPK queryPK = MetaDataPK.create(queryName);
    	
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
       
        //发票申请单
        EntityViewInfo viewInfo = new EntityViewInfo();
        FilterInfo filInfo = new FilterInfo();
        filInfo.getFilterItems().add(new FilterItemInfo("id","select FDestObjectID from T_BOT_Relation where FSrcObjectID='"+billId+"'",CompareType.INNER));
        viewInfo.setFilter(filInfo);
        showQueryDate(tblInvoiceRequest, InvoiceRequestQuery, viewInfo);
        //应收单
        filInfo.getFilterItems().clear();
        filInfo.getFilterItems().add(new FilterItemInfo("id","select FDestObjectID from T_BOT_Relation where FSrcObjectID in (select FDestObjectID from T_BOT_Relation where FSrcObjectID='"+billId+"')",CompareType.INNER));
        showQueryDate(tblOtherBill, OtherBillQuery, viewInfo);
        //内部核销单
        filInfo.getFilterItems().clear();
        filInfo.getFilterItems().add(new FilterItemInfo("id","select fparentid from CT_RIC_CompayWriteDj where CFDjNoID='"+billId+"'",CompareType.INNER));
        showQueryDate(tblCompanyoff, CompanyoffQuery, viewInfo);
        //客户核销单
        filInfo.getFilterItems().clear();
        filInfo.getFilterItems().add(new FilterItemInfo("id","select fparentid from CT_RIC_RichCWODE where CFDjNoID='"+billId+"'",CompareType.INNER));
        showQueryDate(tblCustomerOff, CustomerOffQuery, viewInfo);
    }

    public void actionSyncustomer_actionPerformed(ActionEvent e) throws Exception {
    	MsgBox.showInfo(CustomerSyncLogFactory.getRemoteInstance().syncCustomer());
    }
    
    public void actionViewLog_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	UIContext context = new UIContext(this);//UIFactoryName.MODEL
        try {
			UIFactory.createUIFactory(UIFactoryName.NEWWIN).create("com.kingdee.eas.custom.richbase.client.CustomerSyncLogListUI",context).show();
		} catch (UIException e1) {
			handUIException(e1);
		}
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