/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

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
public abstract class AbstractProjectAttachmentByBoListUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectAttachmentByBoListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDownload;
    protected javax.swing.JToolBar.Separator separator1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContent;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddAttch;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDownload;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewContent;
    protected ActionDownload actioinDownload = null;
    protected ActionViewContent actionViewContent = null;
    protected ActionAddNewAttch actionAddNewAttch = null;
    protected ActionDisplayFunction actionDisplayFunction = null;
    protected ActionAddAlreadyFile actionAddAlreadyFile = null;
    protected ActionSetScan actionSetScan = null;
    protected ActionSetDriver actionSetDriver = null;
    protected ActionSelectScan actionSelectScan = null;
    protected ActionAddScan actionAddScan = null;
    /**
     * output class constructor
     */
    public AbstractProjectAttachmentByBoListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectAttachmentByBoListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.attachment.app", "AttachmentQuery");
        //actionAddNew
        String _tempStr = null;
        actionAddNew.setEnabled(false);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
        actionAddNew.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
        actionAddNew.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.NAME");
        actionAddNew.putValue(ItemAction.NAME, _tempStr);
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionEdit
        actionEdit.setEnabled(false);
        actionEdit.setDaemonRun(false);

        actionEdit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        _tempStr = resHelper.getString("ActionEdit.SHORT_DESCRIPTION");
        actionEdit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.LONG_DESCRIPTION");
        actionEdit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.NAME");
        actionEdit.putValue(ItemAction.NAME, _tempStr);
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actioinDownload
        this.actioinDownload = new ActionDownload(this);
        getActionManager().registerAction("actioinDownload", actioinDownload);
         this.actioinDownload.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContent
        this.actionViewContent = new ActionViewContent(this);
        getActionManager().registerAction("actionViewContent", actionViewContent);
         this.actionViewContent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddNewAttch
        this.actionAddNewAttch = new ActionAddNewAttch(this);
        getActionManager().registerAction("actionAddNewAttch", actionAddNewAttch);
         this.actionAddNewAttch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisplayFunction
        this.actionDisplayFunction = new ActionDisplayFunction(this);
        getActionManager().registerAction("actionDisplayFunction", actionDisplayFunction);
         this.actionDisplayFunction.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddAlreadyFile
        this.actionAddAlreadyFile = new ActionAddAlreadyFile(this);
        getActionManager().registerAction("actionAddAlreadyFile", actionAddAlreadyFile);
         this.actionAddAlreadyFile.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAddAlreadyFile.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAddAlreadyFile.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSetScan
        this.actionSetScan = new ActionSetScan(this);
        getActionManager().registerAction("actionSetScan", actionSetScan);
         this.actionSetScan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSetDriver
        this.actionSetDriver = new ActionSetDriver(this);
        getActionManager().registerAction("actionSetDriver", actionSetDriver);
         this.actionSetDriver.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSelectScan
        this.actionSelectScan = new ActionSelectScan(this);
        getActionManager().registerAction("actionSelectScan", actionSelectScan);
         this.actionSelectScan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddScan
        this.actionAddScan = new ActionAddScan(this);
        getActionManager().registerAction("actionAddScan", actionAddScan);
         this.actionAddScan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnDownload = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator1 = new javax.swing.JToolBar.Separator();
        this.btnViewContent = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddAttch = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemDownload = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewContent = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnDownload.setName("btnDownload");
        this.separator1.setName("separator1");
        this.btnViewContent.setName("btnViewContent");
        this.btnAddAttch.setName("btnAddAttch");
        this.menuItemDownload.setName("menuItemDownload");
        this.menuItemViewContent.setName("menuItemViewContent");
        // CoreUI		
        this.setBorder(null);		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","attachID","boAttchAsso.assoType","name","type","size","description","sharedDesc","boAttchAsso.boID","","createTime","boAttchAsso.id"});

		
        this.btnAddNew.setToolTipText(resHelper.getString("btnAddNew.toolTipText"));		
        this.btnAddNew.setHorizontalAlignment(4);		
        this.btnAddNew.setEnabled(false);		
        this.btnAddNew.setVisible(false);
        this.btnAddNew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                try {
                    btnAddNew_mouseEntered(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });		
        this.btnEdit.setEnabled(false);		
        this.btnRemove.setEnabled(false);
        // btnDownload
        this.btnDownload.setAction((IItemAction)ActionProxyFactory.getProxy(actioinDownload, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDownload.setText(resHelper.getString("btnDownload.text"));		
        this.btnDownload.setToolTipText(resHelper.getString("btnDownload.toolTipText"));		
        this.btnDownload.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_dcdwj"));
        // separator1		
        this.separator1.setOrientation(1);		
        this.separator1.setVisible(false);
        // btnViewContent
        this.btnViewContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContent.setText(resHelper.getString("btnViewContent.text"));		
        this.btnViewContent.setToolTipText(resHelper.getString("btnViewContent.toolTipText"));		
        this.btnViewContent.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_open"));
        // btnAddAttch
        this.btnAddAttch.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddAttch.setText(resHelper.getString("btnAddAttch.text"));		
        this.btnAddAttch.setToolTipText(resHelper.getString("btnAddAttch.toolTipText"));
        // menuItemDownload
        this.menuItemDownload.setAction((IItemAction)ActionProxyFactory.getProxy(actioinDownload, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDownload.setText(resHelper.getString("menuItemDownload.text"));		
        this.menuItemDownload.setMnemonic(68);		
        this.menuItemDownload.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_dcdwj"));
        // menuItemViewContent
        this.menuItemViewContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewContent.setText(resHelper.getString("menuItemViewContent.text"));		
        this.menuItemViewContent.setMnemonic(83);		
        this.menuItemViewContent.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_open"));
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
        tblMain.setBounds(new Rectangle(10, 10, 996, 580));
        this.add(tblMain, new KDLayout.Constraints(10, 10, 996, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

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
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(menuItemDownload);
        menuFile.add(menuItemViewContent);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
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
        menuHelp.add(menuItemAbout);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddAttch);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separator1);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnDownload);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnViewContent);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.costdb.app.ProjectAttachmentByBoListUIHandler";
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
     * output btnAddNew_mouseEntered method
     */
    protected void btnAddNew_mouseEntered(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("boAttchAsso.assoType"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("type"));
        sic.add(new SelectorItemInfo("size"));
        sic.add(new SelectorItemInfo("sharedDesc"));
        sic.add(new SelectorItemInfo("boAttchAsso.boID"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("attachID"));
        sic.add(new SelectorItemInfo("boAttchAsso.id"));
        return sic;
    }        
    	

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    	

    /**
     * output actionEdit_actionPerformed method
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }
    	

    /**
     * output actionDownload_actionPerformed method
     */
    public void actionDownload_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContent_actionPerformed method
     */
    public void actionViewContent_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddNewAttch_actionPerformed method
     */
    public void actionAddNewAttch_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisplayFunction_actionPerformed method
     */
    public void actionDisplayFunction_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddAlreadyFile_actionPerformed method
     */
    public void actionAddAlreadyFile_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSetScan_actionPerformed method
     */
    public void actionSetScan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSetDriver_actionPerformed method
     */
    public void actionSetDriver_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSelectScan_actionPerformed method
     */
    public void actionSelectScan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddScan_actionPerformed method
     */
    public void actionAddScan_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionDownload class
     */
    protected class ActionDownload extends ItemAction
    {
        public ActionDownload()
        {
            this(null);
        }

        public ActionDownload(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDownload.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownload.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownload.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectAttachmentByBoListUI.this, "ActionDownload", "actionDownload_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContent class
     */
    protected class ActionViewContent extends ItemAction
    {
        public ActionViewContent()
        {
            this(null);
        }

        public ActionViewContent(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewContent.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContent.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContent.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectAttachmentByBoListUI.this, "ActionViewContent", "actionViewContent_actionPerformed", e);
        }
    }

    /**
     * output ActionAddNewAttch class
     */
    protected class ActionAddNewAttch extends ItemAction
    {
        public ActionAddNewAttch()
        {
            this(null);
        }

        public ActionAddNewAttch(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl H"));
            _tempStr = resHelper.getString("ActionAddNewAttch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddNewAttch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddNewAttch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectAttachmentByBoListUI.this, "ActionAddNewAttch", "actionAddNewAttch_actionPerformed", e);
        }
    }

    /**
     * output ActionDisplayFunction class
     */
    protected class ActionDisplayFunction extends ItemAction
    {
        public ActionDisplayFunction()
        {
            this(null);
        }

        public ActionDisplayFunction(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisplayFunction.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayFunction.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayFunction.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectAttachmentByBoListUI.this, "ActionDisplayFunction", "actionDisplayFunction_actionPerformed", e);
        }
    }

    /**
     * output ActionAddAlreadyFile class
     */
    protected class ActionAddAlreadyFile extends ItemAction
    {
        public ActionAddAlreadyFile()
        {
            this(null);
        }

        public ActionAddAlreadyFile(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl K"));
            _tempStr = resHelper.getString("ActionAddAlreadyFile.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddAlreadyFile.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddAlreadyFile.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectAttachmentByBoListUI.this, "ActionAddAlreadyFile", "actionAddAlreadyFile_actionPerformed", e);
        }
    }

    /**
     * output ActionSetScan class
     */
    protected class ActionSetScan extends ItemAction
    {
        public ActionSetScan()
        {
            this(null);
        }

        public ActionSetScan(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSetScan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetScan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetScan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectAttachmentByBoListUI.this, "ActionSetScan", "actionSetScan_actionPerformed", e);
        }
    }

    /**
     * output ActionSetDriver class
     */
    protected class ActionSetDriver extends ItemAction
    {
        public ActionSetDriver()
        {
            this(null);
        }

        public ActionSetDriver(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSetDriver.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetDriver.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetDriver.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectAttachmentByBoListUI.this, "ActionSetDriver", "actionSetDriver_actionPerformed", e);
        }
    }

    /**
     * output ActionSelectScan class
     */
    protected class ActionSelectScan extends ItemAction
    {
        public ActionSelectScan()
        {
            this(null);
        }

        public ActionSelectScan(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSelectScan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectScan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectScan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectAttachmentByBoListUI.this, "ActionSelectScan", "actionSelectScan_actionPerformed", e);
        }
    }

    /**
     * output ActionAddScan class
     */
    protected class ActionAddScan extends ItemAction
    {
        public ActionAddScan()
        {
            this(null);
        }

        public ActionAddScan(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl B"));
            _tempStr = resHelper.getString("ActionAddScan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddScan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddScan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectAttachmentByBoListUI.this, "ActionAddScan", "actionAddScan_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.costdb.client", "ProjectAttachmentByBoListUI");
    }




}