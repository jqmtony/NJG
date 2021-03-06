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
public abstract class AbstractProjectDynaticFundPayPlanListUI extends com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectDynaticFundPayPlanListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSyn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMonthPayDistributePic;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPayProgressPic;
    protected ActionSyn actionSyn = null;
    protected ActionUnit actionUnit = null;
    protected ActionMonthPayDistributePic actionMonthPayDistributePic = null;
    protected ActionPayProgressPic actionPayProgressPic = null;
    /**
     * output class constructor
     */
    public AbstractProjectDynaticFundPayPlanListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectDynaticFundPayPlanListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.finance.app", "ProjectDynaticFundPayPlanQuery");
        //actionSyn
        this.actionSyn = new ActionSyn(this);
        getActionManager().registerAction("actionSyn", actionSyn);
         this.actionSyn.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnit
        this.actionUnit = new ActionUnit(this);
        getActionManager().registerAction("actionUnit", actionUnit);
         this.actionUnit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMonthPayDistributePic
        this.actionMonthPayDistributePic = new ActionMonthPayDistributePic(this);
        getActionManager().registerAction("actionMonthPayDistributePic", actionMonthPayDistributePic);
         this.actionMonthPayDistributePic.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPayProgressPic
        this.actionPayProgressPic = new ActionPayProgressPic(this);
        getActionManager().registerAction("actionPayProgressPic", actionPayProgressPic);
         this.actionPayProgressPic.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnSyn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnMonthPayDistributePic = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPayProgressPic = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSyn.setName("btnSyn");
        this.btnUnit.setName("btnUnit");
        this.btnMonthPayDistributePic.setName("btnMonthPayDistributePic");
        this.btnPayProgressPic.setName("btnPayProgressPic");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"curProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"curProject.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"curProject.name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"programming.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"programmingName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"programmingAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"signState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"totalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{curProject.id}</t:Cell><t:Cell>$Resource{curProject.number}</t:Cell><t:Cell>$Resource{curProject.name}</t:Cell><t:Cell>$Resource{programming.id}</t:Cell><t:Cell>$Resource{programmingName}</t:Cell><t:Cell>$Resource{programmingAmount}</t:Cell><t:Cell>$Resource{signState}</t:Cell><t:Cell>$Resource{totalAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","curProject.id","curProject.number","curProject.name","","","","",""});


        // btnSyn
        this.btnSyn.setAction((IItemAction)ActionProxyFactory.getProxy(actionSyn, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSyn.setText(resHelper.getString("btnSyn.text"));
        // btnUnit
        this.btnUnit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnit.setText(resHelper.getString("btnUnit.text"));
        // btnMonthPayDistributePic
        this.btnMonthPayDistributePic.setAction((IItemAction)ActionProxyFactory.getProxy(actionMonthPayDistributePic, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMonthPayDistributePic.setText(resHelper.getString("btnMonthPayDistributePic.text"));
        // btnPayProgressPic
        this.btnPayProgressPic.setAction((IItemAction)ActionProxyFactory.getProxy(actionPayProgressPic, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPayProgressPic.setText(resHelper.getString("btnPayProgressPic.text"));
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
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        kDSplitPane1.setBounds(new Rectangle(10, 10, 993, 609));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(tblMain, "right");
        kDSplitPane1.add(treeProject, "left");

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkFlow);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemCloudShare);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemSwitchView);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(menuItemQueryScheme);
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
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnSyn);
        this.toolBar.add(btnUnit);
        this.toolBar.add(btnMonthPayDistributePic);
        this.toolBar.add(btnPayProgressPic);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnView);
        
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.ProjectDynaticFundPayPlanListUIHandler";
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
	 * ????????��??
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
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.number"));
        sic.add(new SelectorItemInfo("curProject.name"));
        return sic;
    }        
    	

    /**
     * output actionSyn_actionPerformed method
     */
    public void actionSyn_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnit_actionPerformed method
     */
    public void actionUnit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMonthPayDistributePic_actionPerformed method
     */
    public void actionMonthPayDistributePic_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPayProgressPic_actionPerformed method
     */
    public void actionPayProgressPic_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSyn(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSyn() {
    	return false;
    }
	public RequestContext prepareActionUnit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnit() {
    	return false;
    }
	public RequestContext prepareActionMonthPayDistributePic(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMonthPayDistributePic() {
    	return false;
    }
	public RequestContext prepareActionPayProgressPic(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPayProgressPic() {
    	return false;
    }

    /**
     * output ActionSyn class
     */     
    protected class ActionSyn extends ItemAction {     
    
        public ActionSyn()
        {
            this(null);
        }

        public ActionSyn(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSyn.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSyn.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSyn.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectDynaticFundPayPlanListUI.this, "ActionSyn", "actionSyn_actionPerformed", e);
        }
    }

    /**
     * output ActionUnit class
     */     
    protected class ActionUnit extends ItemAction {     
    
        public ActionUnit()
        {
            this(null);
        }

        public ActionUnit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUnit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectDynaticFundPayPlanListUI.this, "ActionUnit", "actionUnit_actionPerformed", e);
        }
    }

    /**
     * output ActionMonthPayDistributePic class
     */     
    protected class ActionMonthPayDistributePic extends ItemAction {     
    
        public ActionMonthPayDistributePic()
        {
            this(null);
        }

        public ActionMonthPayDistributePic(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMonthPayDistributePic.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMonthPayDistributePic.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMonthPayDistributePic.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectDynaticFundPayPlanListUI.this, "ActionMonthPayDistributePic", "actionMonthPayDistributePic_actionPerformed", e);
        }
    }

    /**
     * output ActionPayProgressPic class
     */     
    protected class ActionPayProgressPic extends ItemAction {     
    
        public ActionPayProgressPic()
        {
            this(null);
        }

        public ActionPayProgressPic(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPayProgressPic.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPayProgressPic.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPayProgressPic.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectDynaticFundPayPlanListUI.this, "ActionPayProgressPic", "actionPayProgressPic_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "ProjectDynaticFundPayPlanListUI");
    }




}