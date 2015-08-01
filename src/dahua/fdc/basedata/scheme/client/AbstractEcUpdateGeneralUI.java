/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.scheme.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractEcUpdateGeneralUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEcUpdateGeneralUI.class);
    protected com.kingdee.bos.ctrl.swing.KDButton btnUpdateBal;
    protected com.kingdee.bos.ctrl.swing.KDButton btnUpdateContract;
    protected com.kingdee.bos.ctrl.swing.KDButton btnBizProcess;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDictionNary;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDButton btnExDictionNary;
    protected com.kingdee.bos.ctrl.swing.KDButton btnMainProcess;
    protected com.kingdee.bos.ctrl.swing.KDButton btnPeriodProcess;
    protected com.kingdee.bos.ctrl.swing.KDButton btnPerfomance;
    protected com.kingdee.bos.ctrl.swing.KDButton btnHistoryUpdate;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDesc;
    protected ActionUpdateBal actionUpdateBal = null;
    protected ActionUpdateContract actionUpdateContract = null;
    protected ActionBizProcess actionBizProcess = null;
    protected ActionShowDiction actionShowDiction = null;
    protected ActionShowExDiction actionShowExDiction = null;
    protected ActionMainProcess actionMainProcess = null;
    protected ActionMigratePeriodProcess actionMigratePeriodProcess = null;
    protected ActionPerfomance actionPerfomance = null;
    protected ActionQualityUpdate actionQualityUpdate = null;
    /**
     * output class constructor
     */
    public AbstractEcUpdateGeneralUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractEcUpdateGeneralUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionUpdateBal
        this.actionUpdateBal = new ActionUpdateBal(this);
        getActionManager().registerAction("actionUpdateBal", actionUpdateBal);
         this.actionUpdateBal.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpdateContract
        this.actionUpdateContract = new ActionUpdateContract(this);
        getActionManager().registerAction("actionUpdateContract", actionUpdateContract);
         this.actionUpdateContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBizProcess
        this.actionBizProcess = new ActionBizProcess(this);
        getActionManager().registerAction("actionBizProcess", actionBizProcess);
         this.actionBizProcess.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowDiction
        this.actionShowDiction = new ActionShowDiction(this);
        getActionManager().registerAction("actionShowDiction", actionShowDiction);
         this.actionShowDiction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowExDiction
        this.actionShowExDiction = new ActionShowExDiction(this);
        getActionManager().registerAction("actionShowExDiction", actionShowExDiction);
         this.actionShowExDiction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMainProcess
        this.actionMainProcess = new ActionMainProcess(this);
        getActionManager().registerAction("actionMainProcess", actionMainProcess);
         this.actionMainProcess.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMigratePeriodProcess
        this.actionMigratePeriodProcess = new ActionMigratePeriodProcess(this);
        getActionManager().registerAction("actionMigratePeriodProcess", actionMigratePeriodProcess);
         this.actionMigratePeriodProcess.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPerfomance
        this.actionPerfomance = new ActionPerfomance(this);
        getActionManager().registerAction("actionPerfomance", actionPerfomance);
         this.actionPerfomance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQualityUpdate
        this.actionQualityUpdate = new ActionQualityUpdate(this);
        getActionManager().registerAction("actionQualityUpdate", actionQualityUpdate);
         this.actionQualityUpdate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnUpdateBal = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnUpdateContract = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnBizProcess = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnDictionNary = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.btnExDictionNary = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnMainProcess = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnPeriodProcess = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnPerfomance = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnHistoryUpdate = new com.kingdee.bos.ctrl.swing.KDButton();
        this.txtDesc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.btnUpdateBal.setName("btnUpdateBal");
        this.btnUpdateContract.setName("btnUpdateContract");
        this.btnBizProcess.setName("btnBizProcess");
        this.btnDictionNary.setName("btnDictionNary");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.btnExDictionNary.setName("btnExDictionNary");
        this.btnMainProcess.setName("btnMainProcess");
        this.btnPeriodProcess.setName("btnPeriodProcess");
        this.btnPerfomance.setName("btnPerfomance");
        this.btnHistoryUpdate.setName("btnHistoryUpdate");
        this.txtDesc.setName("txtDesc");
        // CoreUI
        // btnUpdateBal
        this.btnUpdateBal.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpdateBal, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpdateBal.setText(resHelper.getString("btnUpdateBal.text"));
        // btnUpdateContract
        this.btnUpdateContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpdateContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpdateContract.setText(resHelper.getString("btnUpdateContract.text"));
        // btnBizProcess
        this.btnBizProcess.setAction((IItemAction)ActionProxyFactory.getProxy(actionBizProcess, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBizProcess.setText(resHelper.getString("btnBizProcess.text"));
        // btnDictionNary
        this.btnDictionNary.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowDiction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDictionNary.setText(resHelper.getString("btnDictionNary.text"));
        // kDScrollPane1
        // btnExDictionNary
        this.btnExDictionNary.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowExDiction, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExDictionNary.setText(resHelper.getString("btnExDictionNary.text"));
        // btnMainProcess
        this.btnMainProcess.setAction((IItemAction)ActionProxyFactory.getProxy(actionMainProcess, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMainProcess.setText(resHelper.getString("btnMainProcess.text"));
        // btnPeriodProcess
        this.btnPeriodProcess.setAction((IItemAction)ActionProxyFactory.getProxy(actionMigratePeriodProcess, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPeriodProcess.setText(resHelper.getString("btnPeriodProcess.text"));
        // btnPerfomance
        this.btnPerfomance.setAction((IItemAction)ActionProxyFactory.getProxy(actionPerfomance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPerfomance.setText(resHelper.getString("btnPerfomance.text"));
        // btnHistoryUpdate
        this.btnHistoryUpdate.setAction((IItemAction)ActionProxyFactory.getProxy(actionQualityUpdate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnHistoryUpdate.setText(resHelper.getString("btnHistoryUpdate.text"));
        // txtDesc		
        this.txtDesc.setEnabled(false);		
        this.txtDesc.setEditable(false);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(null);
        btnUpdateBal.setBounds(new Rectangle(20, 20, 130, 21));
        this.add(btnUpdateBal, null);
        btnUpdateContract.setBounds(new Rectangle(20, 50, 130, 21));
        this.add(btnUpdateContract, null);
        btnBizProcess.setBounds(new Rectangle(20, 80, 130, 21));
        this.add(btnBizProcess, null);
        btnDictionNary.setBounds(new Rectangle(20, 110, 130, 21));
        this.add(btnDictionNary, null);
        kDScrollPane1.setBounds(new Rectangle(21, 238, 968, 375));
        this.add(kDScrollPane1, null);
        btnExDictionNary.setBounds(new Rectangle(19, 140, 130, 21));
        this.add(btnExDictionNary, null);
        btnMainProcess.setBounds(new Rectangle(20, 170, 130, 21));
        this.add(btnMainProcess, null);
        btnPeriodProcess.setBounds(new Rectangle(20, 202, 130, 21));
        this.add(btnPeriodProcess, null);
        btnPerfomance.setBounds(new Rectangle(180, 20, 119, 21));
        this.add(btnPerfomance, null);
        btnHistoryUpdate.setBounds(new Rectangle(180, 50, 119, 21));
        this.add(btnHistoryUpdate, null);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDesc, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnCloud);
        
        this.toolBar.add(kDSeparatorCloud);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.scheme.app.EcUpdateGeneralUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    	

    /**
     * output actionUpdateBal_actionPerformed method
     */
    public void actionUpdateBal_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpdateContract_actionPerformed method
     */
    public void actionUpdateContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBizProcess_actionPerformed method
     */
    public void actionBizProcess_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowDiction_actionPerformed method
     */
    public void actionShowDiction_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowExDiction_actionPerformed method
     */
    public void actionShowExDiction_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMainProcess_actionPerformed method
     */
    public void actionMainProcess_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMigratePeriodProcess_actionPerformed method
     */
    public void actionMigratePeriodProcess_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPerfomance_actionPerformed method
     */
    public void actionPerfomance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQualityUpdate_actionPerformed method
     */
    public void actionQualityUpdate_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionUpdateBal(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpdateBal() {
    	return false;
    }
	public RequestContext prepareActionUpdateContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpdateContract() {
    	return false;
    }
	public RequestContext prepareActionBizProcess(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBizProcess() {
    	return false;
    }
	public RequestContext prepareActionShowDiction(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowDiction() {
    	return false;
    }
	public RequestContext prepareActionShowExDiction(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowExDiction() {
    	return false;
    }
	public RequestContext prepareActionMainProcess(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMainProcess() {
    	return false;
    }
	public RequestContext prepareActionMigratePeriodProcess(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMigratePeriodProcess() {
    	return false;
    }
	public RequestContext prepareActionPerfomance(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPerfomance() {
    	return false;
    }
	public RequestContext prepareActionQualityUpdate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQualityUpdate() {
    	return false;
    }

    /**
     * output ActionUpdateBal class
     */     
    protected class ActionUpdateBal extends ItemAction {     
    
        public ActionUpdateBal()
        {
            this(null);
        }

        public ActionUpdateBal(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUpdateBal.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateBal.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateBal.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEcUpdateGeneralUI.this, "ActionUpdateBal", "actionUpdateBal_actionPerformed", e);
        }
    }

    /**
     * output ActionUpdateContract class
     */     
    protected class ActionUpdateContract extends ItemAction {     
    
        public ActionUpdateContract()
        {
            this(null);
        }

        public ActionUpdateContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUpdateContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpdateContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEcUpdateGeneralUI.this, "ActionUpdateContract", "actionUpdateContract_actionPerformed", e);
        }
    }

    /**
     * output ActionBizProcess class
     */     
    protected class ActionBizProcess extends ItemAction {     
    
        public ActionBizProcess()
        {
            this(null);
        }

        public ActionBizProcess(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBizProcess.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBizProcess.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBizProcess.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEcUpdateGeneralUI.this, "ActionBizProcess", "actionBizProcess_actionPerformed", e);
        }
    }

    /**
     * output ActionShowDiction class
     */     
    protected class ActionShowDiction extends ItemAction {     
    
        public ActionShowDiction()
        {
            this(null);
        }

        public ActionShowDiction(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionShowDiction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowDiction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowDiction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEcUpdateGeneralUI.this, "ActionShowDiction", "actionShowDiction_actionPerformed", e);
        }
    }

    /**
     * output ActionShowExDiction class
     */     
    protected class ActionShowExDiction extends ItemAction {     
    
        public ActionShowExDiction()
        {
            this(null);
        }

        public ActionShowExDiction(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionShowExDiction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowExDiction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowExDiction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEcUpdateGeneralUI.this, "ActionShowExDiction", "actionShowExDiction_actionPerformed", e);
        }
    }

    /**
     * output ActionMainProcess class
     */     
    protected class ActionMainProcess extends ItemAction {     
    
        public ActionMainProcess()
        {
            this(null);
        }

        public ActionMainProcess(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMainProcess.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMainProcess.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMainProcess.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEcUpdateGeneralUI.this, "ActionMainProcess", "actionMainProcess_actionPerformed", e);
        }
    }

    /**
     * output ActionMigratePeriodProcess class
     */     
    protected class ActionMigratePeriodProcess extends ItemAction {     
    
        public ActionMigratePeriodProcess()
        {
            this(null);
        }

        public ActionMigratePeriodProcess(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMigratePeriodProcess.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMigratePeriodProcess.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMigratePeriodProcess.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEcUpdateGeneralUI.this, "ActionMigratePeriodProcess", "actionMigratePeriodProcess_actionPerformed", e);
        }
    }

    /**
     * output ActionPerfomance class
     */     
    protected class ActionPerfomance extends ItemAction {     
    
        public ActionPerfomance()
        {
            this(null);
        }

        public ActionPerfomance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPerfomance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPerfomance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPerfomance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEcUpdateGeneralUI.this, "ActionPerfomance", "actionPerfomance_actionPerformed", e);
        }
    }

    /**
     * output ActionQualityUpdate class
     */     
    protected class ActionQualityUpdate extends ItemAction {     
    
        public ActionQualityUpdate()
        {
            this(null);
        }

        public ActionQualityUpdate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionQualityUpdate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityUpdate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQualityUpdate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEcUpdateGeneralUI.this, "ActionQualityUpdate", "actionQualityUpdate_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.scheme.client", "EcUpdateGeneralUI");
    }




}