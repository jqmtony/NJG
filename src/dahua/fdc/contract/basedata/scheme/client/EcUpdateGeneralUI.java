/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.scheme.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class EcUpdateGeneralUI extends AbstractEcUpdateGeneralUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8776308896413852532L;

	private static final Logger logger = CoreUIObject.getLogger(EcUpdateGeneralUI.class);
    
    /**
     * output class constructor
     */
    public EcUpdateGeneralUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
        // 调用基类方法装载数据
        super.onLoad();  
     
        actionUpdateBal.setEnabled(true);
        actionShowDiction.setEnabled(true);
        actionUpdateContract.setEnabled(true);
        actionBizProcess.setEnabled(true);
        actionShowExDiction.setEnabled(true);
        actionMainProcess.setEnabled(true);
        actionMigratePeriodProcess.setEnabled(true);
        actionPerfomance.setEnabled(true);
        btnHistoryUpdate.setEnabled(true);
        actionQualityUpdate.setEnabled(true);
    }

    /**
     * output actionUpdateBal_actionPerformed
     */
    public void actionUpdateBal_actionPerformed(ActionEvent e) throws Exception
    {
    	showText("UpdateBal");
    	String uiName = "com.kingdee.eas.fdc.basedata.scheme.client.BalanceModifyUI";       
    	UIUtils.openUI( uiName,this);
    }

    /**
     * output actionUpdateContract_actionPerformed
     */
    public void actionUpdateContract_actionPerformed(ActionEvent e) throws Exception
    {
    	showText("DelBiz");
    	
        String txt = " 请谨慎使用该功能，确定做好数据备份。" +
		" 本功能将删除 项目综合管理下的业务数据 (除质量、安全、工程项目、项目其他工程费)";
        
        if(MsgBox.showConfirm2(this,txt)==0){
        	
        	String uiName = "com.kingdee.eas.fdc.basedata.scheme.client.DelProjectUI";       
        	UIUtils.openUI( uiName,this);
        }
    }


    /**
     * output actionBizProcess_actionPerformed method
     */
    public void actionBizProcess_actionPerformed(ActionEvent e) throws Exception
    {
    	String uiName = "com.kingdee.eas.fdc.basedata.scheme.client.EcProcessListUI";       
    	UIUtils.openUI( uiName,this);
    }
    	

    /**
     * output actionShowDiction_actionPerformed method
     */
    public void actionShowDiction_actionPerformed(ActionEvent e) throws Exception
    {
    	String uiName = "com.kingdee.eas.fdc.basedata.scheme.client.FdcEntityObjectListUI";       
    	UIUtils.openUI( uiName,this);
    }
    
    /**
     * output actionShowExDiction_actionPerformed method
     */
    public void actionShowExDiction_actionPerformed(ActionEvent e) throws Exception
    {
    	String uiName = "com.kingdee.eas.fdc.basedata.scheme.client.FdcEntityObjectExtListUI";       
    	UIUtils.openUI( uiName,this);
    }
    	

    /**
     * output actionMainProcess_actionPerformed method
     */
    public void actionMainProcess_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	String uiName = "com.kingdee.eas.fdc.basedata.scheme.client.MainProcessUI";       
    	UIUtils.openUI( uiName,this);
    }

    public void showText(String resName) throws Exception
    {
    	String text = EASResource.getString("com.kingdee.eas.fdc.basedata.scheme.client.GeneralResource", resName);
    	txtDesc.setText(text);
    }
    
    public void actionMigratePeriodProcess_actionPerformed(ActionEvent e) throws Exception
    {
    	String uiName = "com.kingdee.eas.fdc.basedata.scheme.client.PeriodProcessUI";       
    	UIUtils.openUI( uiName,this);
    }
    
    /**
     * output actionPerfomance_actionPerformed method
     */
    public void actionPerfomance_actionPerformed(ActionEvent e) throws Exception
    {
     	String uiName = "com.kingdee.eas.fdc.basedata.scheme.client.CountShowUI";       
    	UIUtils.openUI( uiName,this);
    }
    
    public void actionQualityUpdate_actionPerformed(ActionEvent e) throws Exception
    {
     	String uiName = "com.kingdee.eas.fdc.basedata.scheme.client.HistoryUpdateUI";       
    	UIUtils.openUI( uiName,this);
    }
}