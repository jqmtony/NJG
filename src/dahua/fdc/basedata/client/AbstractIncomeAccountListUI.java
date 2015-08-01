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
public abstract class AbstractIncomeAccountListUI extends com.kingdee.eas.framework.client.TreeDetailListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractIncomeAccountListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAssignToOrg;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisAssign;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnIncomeAccountImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTemplateImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAssign;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAssign;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator4;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAssignToOrg;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDisAssign;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemTemplateImport;
    protected ActionAssign actionAssign = null;
    protected ActionAssignToOrg actionAssignToOrg = null;
    protected ActionDisAssign actionDisAssign = null;
    protected ActionImport actionImport = null;
    protected ActionTemplateImport actionTemplateImport = null;
    protected ActionEnterDB actionEnterDB = null;
    protected ActionCancelEnterDB actionCancelEnterDB = null;
    /**
     * output class constructor
     */
    public AbstractIncomeAccountListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractIncomeAccountListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.basedata.app", "IncomeAccountQuery");
        //actionAssign
        this.actionAssign = new ActionAssign(this);
        getActionManager().registerAction("actionAssign", actionAssign);
         this.actionAssign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAssignToOrg
        this.actionAssignToOrg = new ActionAssignToOrg(this);
        getActionManager().registerAction("actionAssignToOrg", actionAssignToOrg);
         this.actionAssignToOrg.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisAssign
        this.actionDisAssign = new ActionDisAssign(this);
        getActionManager().registerAction("actionDisAssign", actionDisAssign);
         this.actionDisAssign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImport
        this.actionImport = new ActionImport(this);
        getActionManager().registerAction("actionImport", actionImport);
         this.actionImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTemplateImport
        this.actionTemplateImport = new ActionTemplateImport(this);
        getActionManager().registerAction("actionTemplateImport", actionTemplateImport);
         this.actionTemplateImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEnterDB
        this.actionEnterDB = new ActionEnterDB(this);
        getActionManager().registerAction("actionEnterDB", actionEnterDB);
         this.actionEnterDB.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelEnterDB
        this.actionCancelEnterDB = new ActionCancelEnterDB(this);
        getActionManager().registerAction("actionCancelEnterDB", actionCancelEnterDB);
         this.actionCancelEnterDB.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnAssignToOrg = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDisAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnIncomeAccountImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTemplateImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAssign = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator4 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemAssignToOrg = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDisAssign = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemImport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemTemplateImport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnAssignToOrg.setName("btnAssignToOrg");
        this.btnDisAssign.setName("btnDisAssign");
        this.btnIncomeAccountImport.setName("btnIncomeAccountImport");
        this.btnTemplateImport.setName("btnTemplateImport");
        this.btnAssign.setName("btnAssign");
        this.menuItemAssign.setName("menuItemAssign");
        this.kDSeparator4.setName("kDSeparator4");
        this.menuItemAssignToOrg.setName("menuItemAssignToOrg");
        this.menuItemDisAssign.setName("menuItemDisAssign");
        this.kDSeparator5.setName("kDSeparator5");
        this.menuItemImport.setName("menuItemImport");
        this.menuItemTemplateImport.setName("menuItemTemplateImport");
        // CoreUI		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"transLongNumber","name","id","isEnabled","assigned","description","isLeaf","level","curProject.id","curProject.longNumber","fullOrgUnit.id","fullOrgUnit.longNumber","parent.id","longNumber","isSource","srcIncomeAccountId"});

		
        this.kDSeparator2.setVisible(false);		
        this.menuBiz.setEnabled(true);		
        this.menuBiz.setVisible(true);		
        this.btnCancel.setVisible(true);		
        this.btnCancelCancel.setVisible(true);		
        this.pnlMain.setDividerLocation(240);		
        this.treeView.setShowControlPanel(false);		
        this.chkIncludeChild.setText(resHelper.getString("chkIncludeChild.text"));		
        this.btnMoveTree.setVisible(false);		
        this.menuItemGroupEdit.setVisible(false);		
        this.menuItemGroupRemove.setVisible(false);		
        this.separatorEdit1.setVisible(false);		
        this.separatorEdit2.setVisible(false);		
        this.menuItemMoveTree.setVisible(false);		
        this.menuItemGroupMoveTree.setVisible(false);
        // btnAssignToOrg
        this.btnAssignToOrg.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssignToOrg, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAssignToOrg.setText(resHelper.getString("btnAssignToOrg.text"));		
        this.btnAssignToOrg.setToolTipText(resHelper.getString("btnAssignToOrg.toolTipText"));
        // btnDisAssign
        this.btnDisAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisAssign.setText(resHelper.getString("btnDisAssign.text"));		
        this.btnDisAssign.setToolTipText(resHelper.getString("btnDisAssign.toolTipText"));
        // btnIncomeAccountImport
        this.btnIncomeAccountImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnIncomeAccountImport.setText(resHelper.getString("btnIncomeAccountImport.text"));		
        this.btnIncomeAccountImport.setToolTipText(resHelper.getString("btnIncomeAccountImport.toolTipText"));
        // btnTemplateImport
        this.btnTemplateImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionTemplateImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTemplateImport.setText(resHelper.getString("btnTemplateImport.text"));		
        this.btnTemplateImport.setToolTipText(resHelper.getString("btnTemplateImport.toolTipText"));
        // btnAssign
        this.btnAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAssign.setText(resHelper.getString("btnAssign.text"));		
        this.btnAssign.setToolTipText(resHelper.getString("btnAssign.toolTipText"));
        // menuItemAssign
        this.menuItemAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAssign.setText(resHelper.getString("menuItemAssign.text"));
        // kDSeparator4
        // menuItemAssignToOrg
        this.menuItemAssignToOrg.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssignToOrg, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAssignToOrg.setText(resHelper.getString("menuItemAssignToOrg.text"));
        // menuItemDisAssign
        this.menuItemDisAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDisAssign.setText(resHelper.getString("menuItemDisAssign.text"));
        // kDSeparator5
        // menuItemImport
        this.menuItemImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImport.setText(resHelper.getString("menuItemImport.text"));
        // menuItemTemplateImport
        this.menuItemTemplateImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionTemplateImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemTemplateImport.setText(resHelper.getString("menuItemTemplateImport.text"));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        pnlMain.setBounds(new Rectangle(5, 12, 996, 608));
        this.add(pnlMain, new KDLayout.Constraints(5, 12, 996, 608, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(pnlTable, "right");
        //treeView
        treeView.setTree(treeMain);
        //pnlTable
pnlTable.setLayout(new BorderLayout(0, 0));        pnlTable.add(tblMain, BorderLayout.CENTER);
        pnlTable.add(chkIncludeChild, BorderLayout.NORTH);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(SeparatorFile2);
        menuFile.add(menuItemGroupAddNew);
        menuFile.add(separator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator2);
        menuEdit.add(menuItemMoveTree);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemGroupEdit);
        menuEdit.add(menuItemGroupRemove);
        menuEdit.add(separatorEdit2);
        menuEdit.add(menuItemGroupMoveTree);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(separatorView2);
        menuView.add(menuItemGroupView);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(kDSeparator4);
        menuBiz.add(menuItemAssign);
        menuBiz.add(menuItemAssignToOrg);
        menuBiz.add(menuItemDisAssign);
        menuBiz.add(kDSeparator5);
        menuBiz.add(menuItemImport);
        menuBiz.add(menuItemTemplateImport);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnGroupAddNew);
        this.toolBar.add(btnGroupView);
        this.toolBar.add(btnGroupEdit);
        this.toolBar.add(btnGroupRemove);
        this.toolBar.add(btnGroupMoveTree);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnAssign);
        this.toolBar.add(btnAssignToOrg);
        this.toolBar.add(btnDisAssign);
        this.toolBar.add(btnIncomeAccountImport);
        this.toolBar.add(btnTemplateImport);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.IncomeAccountListUIHandler";
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("longNumber"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("assigned"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.longNumber"));
        sic.add(new SelectorItemInfo("parent.id"));
        sic.add(new SelectorItemInfo("isLeaf"));
        sic.add(new SelectorItemInfo("level"));
        sic.add(new SelectorItemInfo("fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
        sic.add(new SelectorItemInfo("isSource"));
        sic.add(new SelectorItemInfo("srcIncomeAccountId"));
        sic.add(new SelectorItemInfo("transLongNumber"));
        return sic;
    }        
    	

    /**
     * output actionAssign_actionPerformed method
     */
    public void actionAssign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAssignToOrg_actionPerformed method
     */
    public void actionAssignToOrg_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisAssign_actionPerformed method
     */
    public void actionDisAssign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImport_actionPerformed method
     */
    public void actionImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTemplateImport_actionPerformed method
     */
    public void actionTemplateImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEnterDB_actionPerformed method
     */
    public void actionEnterDB_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancelEnterDB_actionPerformed method
     */
    public void actionCancelEnterDB_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionAssign class
     */     
    protected class ActionAssign extends ItemAction {     
    
        public ActionAssign()
        {
            this(null);
        }

        public ActionAssign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAssign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountListUI.this, "ActionAssign", "actionAssign_actionPerformed", e);
        }
    }

    /**
     * output ActionAssignToOrg class
     */     
    protected class ActionAssignToOrg extends ItemAction {     
    
        public ActionAssignToOrg()
        {
            this(null);
        }

        public ActionAssignToOrg(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAssignToOrg.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssignToOrg.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssignToOrg.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountListUI.this, "ActionAssignToOrg", "actionAssignToOrg_actionPerformed", e);
        }
    }

    /**
     * output ActionDisAssign class
     */     
    protected class ActionDisAssign extends ItemAction {     
    
        public ActionDisAssign()
        {
            this(null);
        }

        public ActionDisAssign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisAssign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisAssign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisAssign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountListUI.this, "ActionDisAssign", "actionDisAssign_actionPerformed", e);
        }
    }

    /**
     * output ActionImport class
     */     
    protected class ActionImport extends ItemAction {     
    
        public ActionImport()
        {
            this(null);
        }

        public ActionImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountListUI.this, "ActionImport", "actionImport_actionPerformed", e);
        }
    }

    /**
     * output ActionTemplateImport class
     */     
    protected class ActionTemplateImport extends ItemAction {     
    
        public ActionTemplateImport()
        {
            this(null);
        }

        public ActionTemplateImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionTemplateImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTemplateImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTemplateImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountListUI.this, "ActionTemplateImport", "actionTemplateImport_actionPerformed", e);
        }
    }

    /**
     * output ActionEnterDB class
     */     
    protected class ActionEnterDB extends ItemAction {     
    
        public ActionEnterDB()
        {
            this(null);
        }

        public ActionEnterDB(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEnterDB.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEnterDB.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEnterDB.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountListUI.this, "ActionEnterDB", "actionEnterDB_actionPerformed", e);
        }
    }

    /**
     * output ActionCancelEnterDB class
     */     
    protected class ActionCancelEnterDB extends ItemAction {     
    
        public ActionCancelEnterDB()
        {
            this(null);
        }

        public ActionCancelEnterDB(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCancelEnterDB.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelEnterDB.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelEnterDB.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountListUI.this, "ActionCancelEnterDB", "actionCancelEnterDB_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "IncomeAccountListUI");
    }




}