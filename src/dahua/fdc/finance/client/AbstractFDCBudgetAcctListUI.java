/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractFDCBudgetAcctListUI extends com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCBudgetAcctListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel FDCBudgetAcctListUI;
    protected com.kingdee.bos.ctrl.swing.KDToolBar FDCBudgetAcctListUI_toolbar;
    protected com.kingdee.bos.ctrl.swing.KDMenuBar FDCBudgetAcctListUI_menubar;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYear;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMonth;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spMonth;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRecension;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRecension;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkAll;
    protected ActionRecension actionRecension = null;
    /**
     * output class constructor
     */
    public AbstractFDCBudgetAcctListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCBudgetAcctListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.finance.app", "FDCYearBudgetAcctQuery");
        //actionRecension
        this.actionRecension = new ActionRecension(this);
        getActionManager().registerAction("actionRecension", actionRecension);
         this.actionRecension.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.FDCBudgetAcctListUI = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.FDCBudgetAcctListUI_toolbar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.FDCBudgetAcctListUI_menubar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.contMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.menuItemRecension = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnRecension = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.chkAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.FDCBudgetAcctListUI.setName("FDCBudgetAcctListUI");
        this.FDCBudgetAcctListUI_toolbar.setName("FDCBudgetAcctListUI_toolbar");
        this.FDCBudgetAcctListUI_menubar.setName("FDCBudgetAcctListUI_menubar");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.contYear.setName("contYear");
        this.spYear.setName("spYear");
        this.contMonth.setName("contMonth");
        this.spMonth.setName("spMonth");
        this.menuItemRecension.setName("menuItemRecension");
        this.btnRecension.setName("btnRecension");
        this.chkAll.setName("chkAll");
        // CoreUI		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","state","number","verNumber","date","project.name","orgUnit.name","creator.name","createTime","auditor.name","auditTime","orgUnit.id","curProject.id"});


        // FDCBudgetAcctListUI
        // FDCBudgetAcctListUI_toolbar
        // FDCBudgetAcctListUI_menubar
        // kDPanel1
        // kDPanel2
        // contYear		
        this.contYear.setBoundLabelText(resHelper.getString("contYear.boundLabelText"));		
        this.contYear.setBoundLabelLength(20);		
        this.contYear.setBoundLabelAlignment(3);
        // spYear
        // contMonth		
        this.contMonth.setBoundLabelText(resHelper.getString("contMonth.boundLabelText"));		
        this.contMonth.setBoundLabelLength(20);		
        this.contMonth.setBoundLabelAlignment(3);
        // spMonth
        // menuItemRecension
        this.menuItemRecension.setAction((IItemAction)ActionProxyFactory.getProxy(actionRecension, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRecension.setText(resHelper.getString("menuItemRecension.text"));		
        this.menuItemRecension.setMnemonic(82);
        // btnRecension
        this.btnRecension.setAction((IItemAction)ActionProxyFactory.getProxy(actionRecension, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRecension.setText(resHelper.getString("btnRecension.text"));
        // chkAll		
        this.chkAll.setText(resHelper.getString("chkAll.text"));
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
        kDSplitPane1.setBounds(new Rectangle(10, 9, 993, 610));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 9, 993, 610, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(treeProject, "left");
        kDSplitPane1.add(kDPanel1, "right");
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(tblMain, BorderLayout.CENTER);
        kDPanel1.add(kDPanel2, BorderLayout.NORTH);
        //kDPanel2
        kDPanel2.setLayout(null);        contYear.setBounds(new Rectangle(9, 8, 119, 19));
        kDPanel2.add(contYear, null);
        contMonth.setBounds(new Rectangle(147, 8, 70, 19));
        kDPanel2.add(contMonth, null);
        chkAll.setBounds(new Rectangle(240, 10, 140, 19));
        kDPanel2.add(chkAll, null);
        //contYear
        contYear.setBoundEditor(spYear);
        //contMonth
        contMonth.setBoundEditor(spMonth);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuHelp);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuView);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuWorkFlow);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemAbout);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(menuItemRecension);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(separatorView1);
        menuView.add(kDSeparator5);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuTools
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        menuTools.add(menuMail);
        //menuMail
        menuMail.add(menuItemToExcel);
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuWorkFlow
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
        menuWorkFlow.add(menuItemWorkFlowList);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnRecension);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.FDCBudgetAcctListUIHandler";
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
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("verNumber"));
        sic.add(new SelectorItemInfo("date"));
        sic.add(new SelectorItemInfo("project.name"));
        sic.add(new SelectorItemInfo("orgUnit.name"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("orgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        return sic;
    }        
    	

    /**
     * output actionRecension_actionPerformed method
     */
    public void actionRecension_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionRecension class
     */
    protected class ActionRecension extends ItemAction
    {
        public ActionRecension()
        {
            this(null);
        }

        public ActionRecension(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRecension.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRecension.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRecension.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCBudgetAcctListUI.this, "ActionRecension", "actionRecension_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "FDCBudgetAcctListUI");
    }




}