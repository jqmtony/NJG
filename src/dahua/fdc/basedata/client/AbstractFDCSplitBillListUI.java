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
public abstract class AbstractFDCSplitBillListUI extends com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCSplitBillListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProjectAttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContent;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddContent;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCostSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCostSplit;
    protected com.kingdee.bos.ctrl.swing.KDPanel mainPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel colorPanel;
    protected ActionProjectAttachment actionProjectAttachment = null;
    protected ActionViewContent actionViewContent = null;
    protected ActionAddContent actionAddContent = null;
    protected ActionCostSplit actionCostSplit = null;
    /**
     * output class constructor
     */
    public AbstractFDCSplitBillListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCSplitBillListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractCostSplitQuery");
        //actionRemove
        String _tempStr = null;
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
        this.actionRemove.setBindWorkFlow(true);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAudit
        actionAudit.setEnabled(true);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionAudit.setBindWorkFlow(true);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        actionUnAudit.setEnabled(false);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProjectAttachment
        this.actionProjectAttachment = new ActionProjectAttachment(this);
        getActionManager().registerAction("actionProjectAttachment", actionProjectAttachment);
         this.actionProjectAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContent
        this.actionViewContent = new ActionViewContent(this);
        getActionManager().registerAction("actionViewContent", actionViewContent);
         this.actionViewContent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddContent
        this.actionAddContent = new ActionAddContent(this);
        getActionManager().registerAction("actionAddContent", actionAddContent);
         this.actionAddContent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCostSplit
        this.actionCostSplit = new ActionCostSplit(this);
        getActionManager().registerAction("actionCostSplit", actionCostSplit);
         this.actionCostSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnProjectAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContent = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddContent = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCostSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemCostSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.mainPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.colorPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnProjectAttachment.setName("btnProjectAttachment");
        this.btnViewContent.setName("btnViewContent");
        this.btnAddContent.setName("btnAddContent");
        this.btnCostSplit.setName("btnCostSplit");
        this.menuItemCostSplit.setName("menuItemCostSplit");
        this.mainPanel.setName("mainPanel");
        this.colorPanel.setName("colorPanel");
        // CoreUI		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","costSplit.splitState","state","contractType.name","number","name","amount","partB.name","contractSource","signDate","landDeveloper.name","partC.name","costProperty","settleAmt","contractPropert","","costSplit.id","curProject.id"});

		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setText(resHelper.getString("btnEdit.text"));		
        this.btnEdit.setToolTipText(resHelper.getString("btnEdit.toolTipText"));		
        this.btnEdit.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemEdit.setText(resHelper.getString("menuItemEdit.text"));		
        this.menuItemEdit.setToolTipText(resHelper.getString("menuItemEdit.toolTipText"));		
        this.menuItemEdit.setEnabled(false);		
        this.menuItemEdit.setVisible(false);		
        this.separatorFile1.setVisible(false);		
        this.menuWorkFlow.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);
        // btnProjectAttachment
        this.btnProjectAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionProjectAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProjectAttachment.setText(resHelper.getString("btnProjectAttachment.text"));		
        this.btnProjectAttachment.setToolTipText(resHelper.getString("btnProjectAttachment.toolTipText"));		
        this.btnProjectAttachment.setVisible(false);
        // btnViewContent
        this.btnViewContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContent.setText(resHelper.getString("btnViewContent.text"));		
        this.btnViewContent.setToolTipText(resHelper.getString("btnViewContent.toolTipText"));		
        this.btnViewContent.setVisible(false);
        // btnAddContent
        this.btnAddContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddContent.setText(resHelper.getString("btnAddContent.text"));		
        this.btnAddContent.setToolTipText(resHelper.getString("btnAddContent.toolTipText"));		
        this.btnAddContent.setVisible(false);
        // btnCostSplit
        this.btnCostSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionCostSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCostSplit.setText(resHelper.getString("btnCostSplit.text"));		
        this.btnCostSplit.setToolTipText(resHelper.getString("btnCostSplit.toolTipText"));
        // menuItemCostSplit
        this.menuItemCostSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionCostSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCostSplit.setText(resHelper.getString("menuItemCostSplit.text"));		
        this.menuItemCostSplit.setToolTipText(resHelper.getString("menuItemCostSplit.toolTipText"));		
        this.menuItemCostSplit.setMnemonic(83);
        // mainPanel
        // colorPanel		
        this.colorPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("colorPanel.border.title")));		
        this.colorPanel.setPreferredSize(new Dimension(10,50));
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
        kDSplitPane1.setBounds(new Rectangle(10, 10, 993, 609));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(treeProject, "left");
        kDSplitPane1.add(mainPanel, "right");
        //mainPanel
mainPanel.setLayout(new BorderLayout(0, 0));        mainPanel.add(tblMain, BorderLayout.CENTER);
        mainPanel.add(colorPanel, BorderLayout.SOUTH);
        colorPanel.setLayout(null);
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
        this.menuBar.add(menuWorkFlow);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemCostSplit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
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
        this.toolBar.add(btnView);
        this.toolBar.add(btnCostSplit);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnProjectAttachment);
        this.toolBar.add(btnViewContent);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAddContent);
        this.toolBar.add(btnCancelCancel);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.FDCSplitBillListUIHandler";
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
        sic.add(new SelectorItemInfo("contractType.name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("costSplit.id"));
        sic.add(new SelectorItemInfo("costSplit.splitState"));
        sic.add(new SelectorItemInfo("partB.name"));
        sic.add(new SelectorItemInfo("contractSource"));
        sic.add(new SelectorItemInfo("signDate"));
        sic.add(new SelectorItemInfo("landDeveloper.name"));
        sic.add(new SelectorItemInfo("partC.name"));
        sic.add(new SelectorItemInfo("costProperty"));
        sic.add(new SelectorItemInfo("contractPropert"));
        sic.add(new SelectorItemInfo("settleAmt"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("curProject.id"));
        return sic;
    }        
    	

    /**
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
    	

    /**
     * output actionProjectAttachment_actionPerformed method
     */
    public void actionProjectAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContent_actionPerformed method
     */
    public void actionViewContent_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddContent_actionPerformed method
     */
    public void actionAddContent_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCostSplit_actionPerformed method
     */
    public void actionCostSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionRemove(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRemove(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemove() {
    	return false;
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionUnAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionProjectAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProjectAttachment() {
    	return false;
    }
	public RequestContext prepareActionViewContent(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContent() {
    	return false;
    }
	public RequestContext prepareActionAddContent(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddContent() {
    	return false;
    }
	public RequestContext prepareActionCostSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCostSplit() {
    	return false;
    }

    /**
     * output ActionProjectAttachment class
     */
    protected class ActionProjectAttachment extends ItemAction
    {
        public ActionProjectAttachment()
        {
            this(null);
        }

        public ActionProjectAttachment(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl W"));
            _tempStr = resHelper.getString("ActionProjectAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProjectAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProjectAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCSplitBillListUI.this, "ActionProjectAttachment", "actionProjectAttachment_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractFDCSplitBillListUI.this, "ActionViewContent", "actionViewContent_actionPerformed", e);
        }
    }

    /**
     * output ActionAddContent class
     */
    protected class ActionAddContent extends ItemAction
    {
        public ActionAddContent()
        {
            this(null);
        }

        public ActionAddContent(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddContent.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddContent.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddContent.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCSplitBillListUI.this, "ActionAddContent", "actionAddContent_actionPerformed", e);
        }
    }

    /**
     * output ActionCostSplit class
     */
    protected class ActionCostSplit extends ItemAction
    {
        public ActionCostSplit()
        {
            this(null);
        }

        public ActionCostSplit(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCostSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCostSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCostSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCSplitBillListUI.this, "ActionCostSplit", "actionCostSplit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "FDCSplitBillListUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}