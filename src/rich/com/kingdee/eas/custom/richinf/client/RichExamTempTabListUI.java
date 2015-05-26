/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.client;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.custom.richfacade.EASRichFacadeFactory;
import com.kingdee.eas.custom.richfacade.serviceclient.EASLoginProxy;
import com.kingdee.eas.custom.richfacade.serviceclient.EASLoginProxyServiceLocator;
import com.kingdee.eas.custom.richfacade.serviceclient.WSContext;
import com.kingdee.eas.custom.richfacade.serviceclient.WSEASRichFacadeSrvProxy;
import com.kingdee.eas.custom.richfacade.serviceclient.WSEASRichFacadeSrvProxyServiceLocator;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RichExamTempTabListUI extends AbstractRichExamTempTabListUI
{
    private static final Logger logger = CoreUIObject.getLogger(RichExamTempTabListUI.class);
    
    /**
     * output class constructor
     */
    public RichExamTempTabListUI() throws Exception
    {
        super();
    }
    String getString (){
    	String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    			"<TempTables>" +
    				"<TempTable>" +
    					"<billHead>"
							+"<ywdjbh>0001</ywdjbh>"
							+"<bizdate>2015-05-13</bizdate>"
							+"<ldh>HT0001</ldh>"
							+"<qydw>0002500470</qydw>"
							+"<djdw>0002500470</djdw>"
							+"<kpdw>0002500470</kpdw>"
							+"<skdw>0002500470</skdw>"
							+"<zjjg>0002500470</zjjg>"
							+"<fph>111</fph>"
							+"<xsy>020034</xsy>"
							+"<tjlb>001|团检</tjlb>"
							+"<beizhu>1111</beizhu>"
							+"<djr>张三</djr>"
							+"<djtcbm>TC0001</djtcbm>"
							+"<djtcmc>A套餐</djtcmc>"
							+"<djxmbm>XM0001</djxmbm>"
							+"<djxmmc>A项目</djxmmc>"
							+"<xslb>001|销售员</xslb>"
							+"<sklb>001|现金</sklb>"
							+"<jxbs>0</jxbs>"
							+"<klj>11</klj>"
							+"<zkl>10</zkl>"
							+"<jsje>1000</jsje>"
							+"<se>0</se>"
							+"<jshj>1000</jshj>"
							+"<djjg>0101</djjg>"// 0101  NJP
							+"<kpjg>0101</kpjg>"
							+"<kh>9999999</kh>"
							+"<flag>0</flag >"
						+"</billHead>"
						+"<billEntries>"
						+"   <entry>"
						+"   </entry>"
						+"</billEntries>"
					+"</TempTable>"
					
					+"<TempTable>" +
					"<billHead>"
						+"<ywdjbh>0001</ywdjbh>"
						+"<bizdate>2015-05-13</bizdate>"
						+"<ldh>HT0002</ldh>"
						+"<qydw>0002500470</qydw>"// K000000
						+"<djdw>0002500470</djdw>"
						+"<kpdw>0002500470</kpdw>"
						+"<skdw>0002500470</skdw>"
						+"<zjjg>0002500470</zjjg>"
						+"<fph>1111</fph>"
						+"<xsy>020034</xsy>"//    N000003
						+"<tjlb>001|团检</tjlb>"
						+"<beizhu>111</beizhu>"
						+"<djr>李四</djr>"
						+"<djtcbm>TC0001</djtcbm>"
						+"<djtcmc>A套餐</djtcmc>"
						+"<djxmbm>XM0001</djxmbm>"
						+"<djxmmc>A项目</djxmmc>"
						+"<xslb>001|销售员</xslb>"
						+"<sklb>001|现金</sklb>"
						+"<jxbs>0</jxbs>"
						+"<klj>1</klj>"
						+"<zkl>10</zkl>"
						+"<jsje>2000</jsje>"
						+"<se>0</se>"
						+"<jshj>2000</jshj>"
						+"<djjg>0101</djjg>"
						+"<kpjg>0101</kpjg>"
						+"<kh>9999999</kh>"
						+"<flag>0</flag >"
					+"</billHead>"
					+"<billEntries>"
					+"   <entry>"
					+"   </entry>"
					+"</billEntries>"
				+"</TempTable>"
				
			+"</TempTables>";
    	return str;
    }
    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionPrintPreview_actionPerformed(e);
    	String[] str = new String[3];
		EASLoginProxy login;
		try {
//			login = new EASLoginProxyServiceLocator().getEASLogin( new URL("http://127.0.0.1:56898/ormrpc/services/EASLogin"));
//			WSContext  ws = login.login("user", "", "eas", "njp", "l2", 1);
			login = new EASLoginProxyServiceLocator().getEASLogin( new URL("http://172.3.1.81:6888/ormrpc/services/EASLogin"));
			WSContext  ws = login.login("user", "kingdee", "eas", "TEST201501020", "l2", 1);
		   	if(ws.getSessionId()!=null){
		   		WSEASRichFacadeSrvProxy pay = new WSEASRichFacadeSrvProxyServiceLocator().getWSEASRichFacade(new URL("http://172.3.1.81:6888/ormrpc/services/WSEASRichFacade"));
		  		str = pay.saveTempData(getString());
		  	}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		MsgBox.showInfo(str[0]+str[1]+str[2]);
    }
    
    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionPrint_actionPerformed(e);
    	EASRichFacadeFactory.getRemoteInstance().saveExamBill(new Date(), "");
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	
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
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
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

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.richinf.RichExamTempTabFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.custom.richinf.RichExamTempTabInfo objectValue = new com.kingdee.eas.custom.richinf.RichExamTempTabInfo();
		
        return objectValue;
    }

}