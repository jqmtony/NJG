/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

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
public abstract class AbstractFIProjectListUI extends com.kingdee.eas.fdc.basedata.client.ProjectListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFIProjectListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnGetted;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFlow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClose;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAntiGetted;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAntiFlow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAntiClose;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemGetted;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemFlow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClose;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAntiGetted;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAntiFlow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAntiClose;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTransfered;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemTransfer;
    protected ActionGetted actionGetted = null;
    protected ActionFlow actionFlow = null;
    protected ActionClose actionClose = null;
    protected ActionAntiGetted actionAntiGetted = null;
    protected ActionAntiFlow actionAntiFlow = null;
    protected ActionAntiClose actionAntiClose = null;
    protected ActionTransfer actionTransfer = null;
    /**
     * output class constructor
     */
    public AbstractFIProjectListUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractFIProjectListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.basedata.app", "CurProjectQuery");
        //actionGetted
        this.actionGetted = new ActionGetted(this);
        getActionManager().registerAction("actionGetted", actionGetted);
         this.actionGetted.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFlow
        this.actionFlow = new ActionFlow(this);
        getActionManager().registerAction("actionFlow", actionFlow);
         this.actionFlow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClose
        this.actionClose = new ActionClose(this);
        getActionManager().registerAction("actionClose", actionClose);
         this.actionClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAntiGetted
        this.actionAntiGetted = new ActionAntiGetted(this);
        getActionManager().registerAction("actionAntiGetted", actionAntiGetted);
         this.actionAntiGetted.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAntiFlow
        this.actionAntiFlow = new ActionAntiFlow(this);
        getActionManager().registerAction("actionAntiFlow", actionAntiFlow);
         this.actionAntiFlow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAntiClose
        this.actionAntiClose = new ActionAntiClose(this);
        getActionManager().registerAction("actionAntiClose", actionAntiClose);
         this.actionAntiClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTransfer
        this.actionTransfer = new ActionTransfer(this);
        getActionManager().registerAction("actionTransfer", actionTransfer);
         this.actionTransfer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnGetted = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFlow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAntiGetted = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAntiFlow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAntiClose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemGetted = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemFlow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemClose = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAntiGetted = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAntiFlow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAntiClose = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnTransfered = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemTransfer = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnGetted.setName("btnGetted");
        this.btnFlow.setName("btnFlow");
        this.btnClose.setName("btnClose");
        this.btnAntiGetted.setName("btnAntiGetted");
        this.btnAntiFlow.setName("btnAntiFlow");
        this.btnAntiClose.setName("btnAntiClose");
        this.menuItemGetted.setName("menuItemGetted");
        this.menuItemFlow.setName("menuItemFlow");
        this.menuItemClose.setName("menuItemClose");
        this.menuItemAntiGetted.setName("menuItemAntiGetted");
        this.menuItemAntiFlow.setName("menuItemAntiFlow");
        this.menuItemAntiClose.setName("menuItemAntiClose");
        this.btnTransfered.setName("btnTransfered");
        this.menuItemTransfer.setName("menuItemTransfer");
        // CoreUI		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"longNumber","name","landDeveloper.name","startDate","sortNo","isEnabled","description","id","parent.id","CU.id","projectStatus.name","projectPeriod","projectType.name"});


        this.treeMain.addMouseListener(new java.awt.event.MouseAdapter() {
        });
        // btnGetted
        this.btnGetted.setAction((IItemAction)ActionProxyFactory.getProxy(actionGetted, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnGetted.setText(resHelper.getString("btnGetted.text"));		
        this.btnGetted.setToolTipText(resHelper.getString("btnGetted.toolTipText"));
        // btnFlow
        this.btnFlow.setAction((IItemAction)ActionProxyFactory.getProxy(actionFlow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFlow.setText(resHelper.getString("btnFlow.text"));		
        this.btnFlow.setToolTipText(resHelper.getString("btnFlow.toolTipText"));
        // btnClose
        this.btnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClose.setText(resHelper.getString("btnClose.text"));		
        this.btnClose.setToolTipText(resHelper.getString("btnClose.toolTipText"));
        // btnAntiGetted
        this.btnAntiGetted.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiGetted, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAntiGetted.setText(resHelper.getString("btnAntiGetted.text"));		
        this.btnAntiGetted.setToolTipText(resHelper.getString("btnAntiGetted.toolTipText"));
        // btnAntiFlow
        this.btnAntiFlow.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiFlow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAntiFlow.setText(resHelper.getString("btnAntiFlow.text"));		
        this.btnAntiFlow.setToolTipText(resHelper.getString("btnAntiFlow.toolTipText"));
        // btnAntiClose
        this.btnAntiClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAntiClose.setText(resHelper.getString("btnAntiClose.text"));		
        this.btnAntiClose.setToolTipText(resHelper.getString("btnAntiClose.toolTipText"));
        // menuItemGetted
        this.menuItemGetted.setAction((IItemAction)ActionProxyFactory.getProxy(actionGetted, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemGetted.setText(resHelper.getString("menuItemGetted.text"));		
        this.menuItemGetted.setToolTipText(resHelper.getString("menuItemGetted.toolTipText"));		
        this.menuItemGetted.setMnemonic(71);
        // menuItemFlow
        this.menuItemFlow.setAction((IItemAction)ActionProxyFactory.getProxy(actionFlow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemFlow.setText(resHelper.getString("menuItemFlow.text"));		
        this.menuItemFlow.setToolTipText(resHelper.getString("menuItemFlow.toolTipText"));		
        this.menuItemFlow.setMnemonic(68);
        // menuItemClose
        this.menuItemClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClose.setText(resHelper.getString("menuItemClose.text"));		
        this.menuItemClose.setToolTipText(resHelper.getString("menuItemClose.toolTipText"));		
        this.menuItemClose.setMnemonic(84);
        // menuItemAntiGetted
        this.menuItemAntiGetted.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiGetted, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAntiGetted.setText(resHelper.getString("menuItemAntiGetted.text"));		
        this.menuItemAntiGetted.setToolTipText(resHelper.getString("menuItemAntiGetted.toolTipText"));		
        this.menuItemAntiGetted.setMnemonic(70);
        // menuItemAntiFlow
        this.menuItemAntiFlow.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiFlow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAntiFlow.setText(resHelper.getString("menuItemAntiFlow.text"));		
        this.menuItemAntiFlow.setToolTipText(resHelper.getString("menuItemAntiFlow.toolTipText"));		
        this.menuItemAntiFlow.setMnemonic(85);
        // menuItemAntiClose
        this.menuItemAntiClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAntiClose.setText(resHelper.getString("menuItemAntiClose.text"));		
        this.menuItemAntiClose.setToolTipText(resHelper.getString("menuItemAntiClose.toolTipText"));		
        this.menuItemAntiClose.setMnemonic(65);
        // btnTransfered
        this.btnTransfered.setAction((IItemAction)ActionProxyFactory.getProxy(actionTransfer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTransfered.setText(resHelper.getString("btnTransfered.text"));		
        this.btnTransfered.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_closeledger"));
        // menuItemTransfer
        this.menuItemTransfer.setAction((IItemAction)ActionProxyFactory.getProxy(actionTransfer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemTransfer.setText(resHelper.getString("menuItemTransfer.text"));		
        this.menuItemTransfer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_closeledger"));		
        this.menuItemTransfer.setToolTipText(resHelper.getString("menuItemTransfer.toolTipText"));		
        this.menuItemTransfer.setMnemonic(82);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        pnlMain.setBounds(new Rectangle(10, 10, 996, 580));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 996, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(tblMain, "right");
        pnlMain.add(treeView, "left");
        //treeView
        treeView.setTree(treeMain);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuView);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(kDSeparator1);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(MenuItemAttachment);
        menuEdit.add(menuItemMoveTree);
        menuEdit.add(separatorEdit1);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(menuItemQuery);
        menuView.add(separatorView1);
        menuView.add(menuItemRefresh);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemIdxRefresh);
        menuBiz.add(menuItemGetted);
        menuBiz.add(kDMenuItem1);
        menuBiz.add(menuItemFlow);
        menuBiz.add(kDMenuItem2);
        menuBiz.add(menuItemClose);
        menuBiz.add(kDMenuItem3);
        menuBiz.add(menuItemAntiGetted);
        menuBiz.add(menuItemProjectType);
        menuBiz.add(menuItemAntiFlow);
        menuBiz.add(menuItemAntiClose);
        menuBiz.add(menuItemTransfer);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemAbout);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnEnabled);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnDisEnabled);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnVersionRedact);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnGetted);
        this.toolBar.add(btnFlow);
        this.toolBar.add(btnClose);
        this.toolBar.add(btnIdxRefresh);
        this.toolBar.add(btnAntiGetted);
        this.toolBar.add(btnAntiFlow);
        this.toolBar.add(btnAntiClose);
        this.toolBar.add(btnTransfered);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.FIProjectListUIHandler";
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
     * output treeMain_mouseClicked method
     */
    protected void treeMain_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("longNumber"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("startDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("sortNo"));
        sic.add(new SelectorItemInfo("landDeveloper.name"));
        sic.add(new SelectorItemInfo("CU.id"));
        sic.add(new SelectorItemInfo("parent.id"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("projectStatus.name"));
        sic.add(new SelectorItemInfo("projectType.name"));
        sic.add(new SelectorItemInfo("projectPeriod"));
        return sic;
    }        
    	

    /**
     * output actionGetted_actionPerformed method
     */
    public void actionGetted_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFlow_actionPerformed method
     */
    public void actionFlow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClose_actionPerformed method
     */
    public void actionClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAntiGetted_actionPerformed method
     */
    public void actionAntiGetted_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAntiFlow_actionPerformed method
     */
    public void actionAntiFlow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAntiClose_actionPerformed method
     */
    public void actionAntiClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTransfer_actionPerformed method
     */
    public void actionTransfer_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionGetted(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionGetted() {
    	return false;
    }
	public RequestContext prepareActionFlow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFlow() {
    	return false;
    }
	public RequestContext prepareActionClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClose() {
    	return false;
    }
	public RequestContext prepareActionAntiGetted(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAntiGetted() {
    	return false;
    }
	public RequestContext prepareActionAntiFlow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAntiFlow() {
    	return false;
    }
	public RequestContext prepareActionAntiClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAntiClose() {
    	return false;
    }
	public RequestContext prepareActionTransfer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTransfer() {
    	return false;
    }

    /**
     * output ActionGetted class
     */
    protected class ActionGetted extends ItemAction
    {
        public ActionGetted()
        {
            this(null);
        }

        public ActionGetted(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_execute"));
            _tempStr = resHelper.getString("ActionGetted.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGetted.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGetted.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFIProjectListUI.this, "ActionGetted", "actionGetted_actionPerformed", e);
        }
    }

    /**
     * output ActionFlow class
     */
    protected class ActionFlow extends ItemAction
    {
        public ActionFlow()
        {
            this(null);
        }

        public ActionFlow(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_blankout"));
            _tempStr = resHelper.getString("ActionFlow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFlow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFlow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFIProjectListUI.this, "ActionFlow", "actionFlow_actionPerformed", e);
        }
    }

    /**
     * output ActionClose class
     */
    protected class ActionClose extends ItemAction
    {
        public ActionClose()
        {
            this(null);
        }

        public ActionClose(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_close"));
            _tempStr = resHelper.getString("ActionClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFIProjectListUI.this, "ActionClose", "actionClose_actionPerformed", e);
        }
    }

    /**
     * output ActionAntiGetted class
     */
    protected class ActionAntiGetted extends ItemAction
    {
        public ActionAntiGetted()
        {
            this(null);
        }

        public ActionAntiGetted(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_restore"));
            _tempStr = resHelper.getString("ActionAntiGetted.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiGetted.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiGetted.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFIProjectListUI.this, "ActionAntiGetted", "actionAntiGetted_actionPerformed", e);
        }
    }

    /**
     * output ActionAntiFlow class
     */
    protected class ActionAntiFlow extends ItemAction
    {
        public ActionAntiFlow()
        {
            this(null);
        }

        public ActionAntiFlow(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_fblankout"));
            _tempStr = resHelper.getString("ActionAntiFlow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiFlow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiFlow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFIProjectListUI.this, "ActionAntiFlow", "actionAntiFlow_actionPerformed", e);
        }
    }

    /**
     * output ActionAntiClose class
     */
    protected class ActionAntiClose extends ItemAction
    {
        public ActionAntiClose()
        {
            this(null);
        }

        public ActionAntiClose(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_fclose"));
            _tempStr = resHelper.getString("ActionAntiClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFIProjectListUI.this, "ActionAntiClose", "actionAntiClose_actionPerformed", e);
        }
    }

    /**
     * output ActionTransfer class
     */
    protected class ActionTransfer extends ItemAction
    {
        public ActionTransfer()
        {
            this(null);
        }

        public ActionTransfer(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionTransfer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTransfer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTransfer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFIProjectListUI.this, "ActionTransfer", "actionTransfer_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "FIProjectListUI");
    }




}