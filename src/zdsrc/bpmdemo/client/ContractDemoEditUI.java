/**
 * output package name
 */
package com.kingdee.eas.bpmdemo.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.bpmdemo.ContractDemoEntryInfo;
import com.kingdee.eas.bpmdemo.JBrowserHelper.JFrameBrowser;
import com.kingdee.eas.bpmdemo.JBrowserHelper.MD5Helper;
import com.kingdee.eas.common.client.SysContext;

/**
 * output class name
 */
public class ContractDemoEditUI extends AbstractContractDemoEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractDemoEditUI.class);
    
    /**
     * output class constructor
     */
    public ContractDemoEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	InitButton();
    	
    	isEnabel();
    	
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
	   	this.chkMenuItemSubmitAndAddNew.setEnabled(false);
    }
    
    
    private void InitButton()
    {
    	this.kDContainer1.getContentPane().add(this.kdtEntrys,BorderLayout.CENTER);
    	
    	this.kDContainer1.addButton(this.kdtEntrys_detailPanel.getAddNewLineButton());
    	this.kDContainer1.addButton(this.kdtEntrys_detailPanel.getInsertLineButton());
    	this.kDContainer1.addButton(this.kdtEntrys_detailPanel.getRemoveLinesButton());
    	
    	this.actionCopy.setVisible(false);
    	this.actionAddNew.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	this.actionMultiapprove.setVisible(false);
    	this.actionNextPerson.setVisible(false);
    	
    	
    	this.btnSubmit.setText("�ύBMP");
    	this.btnSubmit.setToolTipText("�ύBMP");
    }
    
    private void isEnabel()
    {
    	this.contLastUpdateTime.setVisible(true);
    	this.contLastUpdateUser.setVisible(true);
    	
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSave_actionPerformed(e);
    }
    
    /**
     * �ύBMP
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSubmit_actionPerformed(e);
    	
//    	String xml = getInfoFacadeFactory.getRemoteInstance().GetbillInfo("",editData.getId().toString());
    	creatFrame();
    	
    }
    
    /**
     * ����ͼ
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
    	//TODO
    	
    	creatFrame();
    }
    
    /**
     * ��������鿴
     */
    public void actionAuditResult_actionPerformed(ActionEvent e)throws Exception {
    	//TODO
    	
    	creatFrame();
    }

    private void creatFrame()
    {
    	//��ȡMD5����
    	String md5 = MD5Helper.getMd5("blue", "20140813", "K2");
    	
    	JFrameBrowser jf = new JFrameBrowser();
    	jf.setJBrowserSize(720, 1200);
    	jf.setJBrwserOpenUrl(StringUtilBPM.getBPMServerURL()+"?bsid=ERP&boid=ERP001&btid=ERP001");
    	
    	jf.setTitle("�ύBMP");
    	
    	jf.OpenJBrowser(this);
    }
    
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.bpmdemo.ContractDemoFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.bpmdemo.ContractDemoInfo objectValue = new com.kingdee.eas.bpmdemo.ContractDemoInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setOrg(SysContext.getSysContext().getCurrentAdminUnit().getDisplayName());
        
        objectValue.setDep(SysContext.getSysContext().getCurrentAdminUnit().getName());
        objectValue.setPerosn(SysContext.getSysContext().getCurrentUserInfo().getName());
        objectValue.setBizDate(new Date());
        
        ContractDemoEntryInfo entryInfo = new ContractDemoEntryInfo();
        entryInfo.setDetial("��һ�������˵����ƻ���������ס��");
        entryInfo.setContent("1����ͬ�����˵�����������Ƿ��ˣ����跨�˵����ơ���Ҫʱ���ɲ鿴�����˵����֤��ִ��");
        
        objectValue.getEntrys().add(entryInfo);
        entryInfo = new ContractDemoEntryInfo();
        entryInfo.setDetial("���������");
        entryInfo.setContent("2����ͬ��ģ������������ͬ�Ļ�����Ҫ˵����������ơ�����ͺš����������ȣ�������ṩ������Ҫ˵����������͡�����ı�׼");
        
        objectValue.getEntrys().add(entryInfo);
        entryInfo = new ContractDemoEntryInfo();
        entryInfo.setDetial("����������");
        entryInfo.setContent("3���۸�Ҫע����ҵ�λ");
        
        objectValue.getEntrys().add(entryInfo);
        
        return objectValue;
    }

}