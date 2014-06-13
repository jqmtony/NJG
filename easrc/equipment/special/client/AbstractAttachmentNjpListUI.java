/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.client;

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
public abstract class AbstractAttachmentNjpListUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAttachmentNjpListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddAtttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewAtttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAttRemove;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnOpen;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDow;
    protected EntityViewInfo queryAttactQuery = null;
    protected IMetaDataPK queryAttactQueryPK;
    protected actionAddAtttachment actionAddAtttachment = null;
    protected actionViewAtttachment actionViewAtttachment = null;
    protected actionAttRemove actionAttRemove = null;
    protected actionDow actionDow = null;
    protected actionOpen actionOpen = null;
    /**
     * output class constructor
     */
    public AbstractAttachmentNjpListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAttachmentNjpListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.port.equipment.rpt", "AttactQuery");
        queryAttactQueryPK = new MetaDataPK("com.kingdee.eas.port.equipment.rpt", "AttactQuery");
        //actionAddAtttachment
        this.actionAddAtttachment = new actionAddAtttachment(this);
        getActionManager().registerAction("actionAddAtttachment", actionAddAtttachment);
         this.actionAddAtttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAtttachment
        this.actionViewAtttachment = new actionViewAtttachment(this);
        getActionManager().registerAction("actionViewAtttachment", actionViewAtttachment);
         this.actionViewAtttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAttRemove
        this.actionAttRemove = new actionAttRemove(this);
        getActionManager().registerAction("actionAttRemove", actionAttRemove);
         this.actionAttRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDow
        this.actionDow = new actionDow(this);
        getActionManager().registerAction("actionDow", actionDow);
         this.actionDow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOpen
        this.actionOpen = new actionOpen(this);
        getActionManager().registerAction("actionOpen", actionOpen);
         this.actionOpen.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnAddAtttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewAtttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAttRemove = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnOpen = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDContainer1.setName("kDContainer1");
        this.btnAddAtttachment.setName("btnAddAtttachment");
        this.btnViewAtttachment.setName("btnViewAtttachment");
        this.btnAttRemove.setName("btnAttRemove");
        this.btnOpen.setName("btnOpen");
        this.btnDow.setName("btnDow");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"boID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"attachment.name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"attachment.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"attachment.type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"attachment.size\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"attachment.description\" t:width=\"222\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"CU.name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"attachment.createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"RegulationsEntry.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"attachment.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"attachment.storageType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"attachment.permission\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{boID}</t:Cell><t:Cell>$Resource{attachment.name}</t:Cell><t:Cell>$Resource{attachment.number}</t:Cell><t:Cell>$Resource{attachment.type}</t:Cell><t:Cell>$Resource{attachment.size}</t:Cell><t:Cell>$Resource{attachment.description}</t:Cell><t:Cell>$Resource{CU.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{attachment.createTime}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{RegulationsEntry.id}</t:Cell><t:Cell>$Resource{attachment.id}</t:Cell><t:Cell>$Resource{attachment.storageType}</t:Cell><t:Cell>$Resource{attachment.permission}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","boID","attachment.name","attachment.number","attachment.type","attachment.size","attachment.description","CU.name","creator.name","attachment.createTime","lastUpdateUser.name","RegulationsEntry.id","attachment.id","attachment.storageType","attachment.permission"});


        // kDContainer1		
        this.kDContainer1.setEnableActive(false);		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // btnAddAtttachment
        this.btnAddAtttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddAtttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddAtttachment.setText(resHelper.getString("btnAddAtttachment.text"));		
        this.btnAddAtttachment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcollection"));
        // btnViewAtttachment
        this.btnViewAtttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAtttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAtttachment.setText(resHelper.getString("btnViewAtttachment.text"));		
        this.btnViewAtttachment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_userview"));
        // btnAttRemove
        this.btnAttRemove.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttRemove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttRemove.setText(resHelper.getString("btnAttRemove.text"));		
        this.btnAttRemove.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_workremove"));
        // btnOpen
        this.btnOpen.setAction((IItemAction)ActionProxyFactory.getProxy(actionOpen, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOpen.setText(resHelper.getString("btnOpen.text"));		
        this.btnOpen.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_open"));
        // btnDow
        this.btnDow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDow.setText(resHelper.getString("btnDow.text"));		
        this.btnDow.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_downbill"));
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
        kDContainer1.setBounds(new Rectangle(5, 5, 1002, 619));
        this.add(kDContainer1, new KDLayout.Constraints(5, 5, 1002, 619, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnAddAtttachment.setBounds(new Rectangle(157, 591, 22, 19));
        this.add(btnAddAtttachment, new KDLayout.Constraints(157, 591, 22, 19, 0));
        btnViewAtttachment.setBounds(new Rectangle(246, 591, 22, 19));
        this.add(btnViewAtttachment, new KDLayout.Constraints(246, 591, 22, 19, 0));
        btnAttRemove.setBounds(new Rectangle(313, 592, 22, 19));
        this.add(btnAttRemove, new KDLayout.Constraints(313, 592, 22, 19, 0));
        btnOpen.setBounds(new Rectangle(10, 10, 30, 30));
        this.add(btnOpen, new KDLayout.Constraints(10, 10, 30, 30, 0));
        btnDow.setBounds(new Rectangle(10, 10, 30, 30));
        this.add(btnDow, new KDLayout.Constraints(10, 10, 30, 30, 0));
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblMain, BorderLayout.CENTER);

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
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnView);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.special.app.AttachmentNjpListUIHandler";
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
     * output setDataObject method
     */
    public void setDataObject(String key, IObjectValue dataObject)
    {
        super.setDataObject(key, dataObject);
        if (key.equalsIgnoreCase("queryAttactQuery")) {
            this.queryAttactQuery = (EntityViewInfo)dataObject;
		}
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
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("boID"));
        sic.add(new SelectorItemInfo("attachment.name"));
        sic.add(new SelectorItemInfo("attachment.number"));
        sic.add(new SelectorItemInfo("attachment.type"));
        sic.add(new SelectorItemInfo("attachment.size"));
        sic.add(new SelectorItemInfo("CU.name"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("attachment.createTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.name"));
        sic.add(new SelectorItemInfo("RegulationsEntry.id"));
        sic.add(new SelectorItemInfo("attachment.id"));
        sic.add(new SelectorItemInfo("attachment.storageType"));
        sic.add(new SelectorItemInfo("attachment.permission"));
        sic.add(new SelectorItemInfo("attachment.description"));
        return sic;
    }        
    	

    /**
     * output actionAddAtttachment_actionPerformed method
     */
    public void actionAddAtttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewAtttachment_actionPerformed method
     */
    public void actionViewAtttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAttRemove_actionPerformed method
     */
    public void actionAttRemove_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDow_actionPerformed method
     */
    public void actionDow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOpen_actionPerformed method
     */
    public void actionOpen_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareactionAddAtttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAddAtttachment() {
    	return false;
    }
	public RequestContext prepareactionViewAtttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionViewAtttachment() {
    	return false;
    }
	public RequestContext prepareactionAttRemove(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAttRemove() {
    	return false;
    }
	public RequestContext prepareactionDow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionDow() {
    	return false;
    }
	public RequestContext prepareactionOpen(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionOpen() {
    	return false;
    }

    /**
     * output actionAddAtttachment class
     */     
    protected class actionAddAtttachment extends ItemAction {     
    
        public actionAddAtttachment()
        {
            this(null);
        }

        public actionAddAtttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionAddAtttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddAtttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddAtttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAttachmentNjpListUI.this, "actionAddAtttachment", "actionAddAtttachment_actionPerformed", e);
        }
    }

    /**
     * output actionViewAtttachment class
     */     
    protected class actionViewAtttachment extends ItemAction {     
    
        public actionViewAtttachment()
        {
            this(null);
        }

        public actionViewAtttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionViewAtttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionViewAtttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionViewAtttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAttachmentNjpListUI.this, "actionViewAtttachment", "actionViewAtttachment_actionPerformed", e);
        }
    }

    /**
     * output actionAttRemove class
     */     
    protected class actionAttRemove extends ItemAction {     
    
        public actionAttRemove()
        {
            this(null);
        }

        public actionAttRemove(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionAttRemove.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAttRemove.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAttRemove.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAttachmentNjpListUI.this, "actionAttRemove", "actionAttRemove_actionPerformed", e);
        }
    }

    /**
     * output actionDow class
     */     
    protected class actionDow extends ItemAction {     
    
        public actionDow()
        {
            this(null);
        }

        public actionDow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionDow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionDow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionDow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAttachmentNjpListUI.this, "actionDow", "actionDow_actionPerformed", e);
        }
    }

    /**
     * output actionOpen class
     */     
    protected class actionOpen extends ItemAction {     
    
        public actionOpen()
        {
            this(null);
        }

        public actionOpen(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionOpen.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionOpen.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionOpen.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAttachmentNjpListUI.this, "actionOpen", "actionOpen_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.special.client", "AttachmentNjpListUI");
    }




}