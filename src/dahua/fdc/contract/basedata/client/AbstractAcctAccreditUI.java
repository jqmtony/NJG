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
public abstract class AbstractAcctAccreditUI extends com.kingdee.eas.fdc.basedata.client.FDCRptBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAcctAccreditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane plLeft;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane plRight;
    protected com.kingdee.bos.ctrl.swing.KDTreeView schemeTreeView;
    protected com.kingdee.bos.ctrl.swing.KDTree treeScheme;
    protected com.kingdee.bos.ctrl.swing.KDContainer contAcct;
    protected com.kingdee.bos.ctrl.swing.KDContainer contUser;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblUser;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAssignAcct;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSave;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAssignAcct;
    protected ActionAddUser actionAddUser = null;
    protected ActionDelUser actionDelUser = null;
    protected ActionAddScheme actionAddScheme = null;
    protected ActionDelScheme actionDelScheme = null;
    protected ActionViewScheme actionViewScheme = null;
    protected ActionEditScheme actionEditScheme = null;
    protected ActionAssignAcct actionAssignAcct = null;
    protected ActionSave actionSave = null;
    /**
     * output class constructor
     */
    public AbstractAcctAccreditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAcctAccreditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionAddUser
        this.actionAddUser = new ActionAddUser(this);
        getActionManager().registerAction("actionAddUser", actionAddUser);
         this.actionAddUser.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelUser
        this.actionDelUser = new ActionDelUser(this);
        getActionManager().registerAction("actionDelUser", actionDelUser);
         this.actionDelUser.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddScheme
        this.actionAddScheme = new ActionAddScheme(this);
        getActionManager().registerAction("actionAddScheme", actionAddScheme);
         this.actionAddScheme.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelScheme
        this.actionDelScheme = new ActionDelScheme(this);
        getActionManager().registerAction("actionDelScheme", actionDelScheme);
         this.actionDelScheme.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewScheme
        this.actionViewScheme = new ActionViewScheme(this);
        getActionManager().registerAction("actionViewScheme", actionViewScheme);
         this.actionViewScheme.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditScheme
        this.actionEditScheme = new ActionEditScheme(this);
        getActionManager().registerAction("actionEditScheme", actionEditScheme);
         this.actionEditScheme.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAssignAcct
        this.actionAssignAcct = new ActionAssignAcct(this);
        getActionManager().registerAction("actionAssignAcct", actionAssignAcct);
         this.actionAssignAcct.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.plLeft = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.plRight = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.schemeTreeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeScheme = new com.kingdee.bos.ctrl.swing.KDTree();
        this.contAcct = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contUser = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblUser = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAssignAcct = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSave = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAssignAcct = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.plLeft.setName("plLeft");
        this.plRight.setName("plRight");
        this.schemeTreeView.setName("schemeTreeView");
        this.treeScheme.setName("treeScheme");
        this.contAcct.setName("contAcct");
        this.contUser.setName("contUser");
        this.tblUser.setName("tblUser");
        this.btnSave.setName("btnSave");
        this.btnAssignAcct.setName("btnAssignAcct");
        this.menuItemSave.setName("menuItemSave");
        this.menuItemAssignAcct.setName("menuItemAssignAcct");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"acctNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"acctName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{acctNumber}</t:Cell><t:Cell>$Resource{acctName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"","",""});


        // plLeft		
        this.plLeft.setOrientation(0);
        // plRight
        // schemeTreeView		
        this.schemeTreeView.setShowButton(false);
        // treeScheme
        this.treeScheme.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeScheme_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contAcct		
        this.contAcct.setTitle(resHelper.getString("contAcct.title"));		
        this.contAcct.setEnableActive(false);
        // contUser		
        this.contUser.setTitle(resHelper.getString("contUser.title"));		
        this.contUser.setEnableActive(false);
        // tblUser
		String tblUserStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"orgunit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"roleNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"roleName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">id</t:Cell><t:Cell t:configured=\"false\">用户账号</t:Cell><t:Cell t:configured=\"false\">用户实名</t:Cell><t:Cell t:configured=\"false\">职员所属组织</t:Cell><t:Cell t:configured=\"false\">编码</t:Cell><t:Cell t:configured=\"false\">名称</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblUser.setFormatXml(resHelper.translateString("tblUser",tblUserStrXML));
        this.tblUser.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblUser_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));
        // btnAssignAcct
        this.btnAssignAcct.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssignAcct, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAssignAcct.setText(resHelper.getString("btnAssignAcct.text"));		
        this.btnAssignAcct.setToolTipText(resHelper.getString("btnAssignAcct.toolTipText"));
        // menuItemSave
        this.menuItemSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSave.setText(resHelper.getString("menuItemSave.text"));		
        this.menuItemSave.setMnemonic(83);		
        this.menuItemSave.setToolTipText(resHelper.getString("menuItemSave.toolTipText"));
        // menuItemAssignAcct
        this.menuItemAssignAcct.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssignAcct, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAssignAcct.setText(resHelper.getString("menuItemAssignAcct.text"));		
        this.menuItemAssignAcct.setMnemonic(65);		
        this.menuItemAssignAcct.setToolTipText(resHelper.getString("menuItemAssignAcct.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        pnlMain.setBounds(new Rectangle(10, 16, 996, 574));
        this.add(pnlMain, new KDLayout.Constraints(10, 16, 996, 574, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(plLeft, "left");
        pnlMain.add(plRight, "right");
        //plLeft
        plLeft.add(treeView, "top");
        plLeft.add(schemeTreeView, "bottom");
        //treeView
        treeView.setTree(treeMain);
        //schemeTreeView
        schemeTreeView.setTree(treeScheme);
        //plRight
        plRight.add(contAcct, "left");
        plRight.add(contUser, "right");
        //contAcct
contAcct.getContentPane().setLayout(new BorderLayout(0, 0));        contAcct.getContentPane().add(tblMain, BorderLayout.CENTER);
        //contUser
contUser.getContentPane().setLayout(new BorderLayout(0, 0));        contUser.getContentPane().add(tblUser, BorderLayout.CENTER);

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
        menuFile.add(menuItemSave);
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
        menuEdit.add(menuItemRemove);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemMoveTree);
        menuEdit.add(menuItemAssignAcct);
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
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnSave);
        this.toolBar.add(btnAddNew);
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
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAssignAcct);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.AcctAccreditUIHandler";
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
	 * ????????У??
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
     * output treeScheme_valueChanged method
     */
    protected void treeScheme_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output tblUser_editStopped method
     */
    protected void tblUser_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionAddUser_actionPerformed method
     */
    public void actionAddUser_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelUser_actionPerformed method
     */
    public void actionDelUser_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddScheme_actionPerformed method
     */
    public void actionAddScheme_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelScheme_actionPerformed method
     */
    public void actionDelScheme_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewScheme_actionPerformed method
     */
    public void actionViewScheme_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditScheme_actionPerformed method
     */
    public void actionEditScheme_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAssignAcct_actionPerformed method
     */
    public void actionAssignAcct_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddUser(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddUser() {
    	return false;
    }
	public RequestContext prepareActionDelUser(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelUser() {
    	return false;
    }
	public RequestContext prepareActionAddScheme(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddScheme() {
    	return false;
    }
	public RequestContext prepareActionDelScheme(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelScheme() {
    	return false;
    }
	public RequestContext prepareActionViewScheme(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewScheme() {
    	return false;
    }
	public RequestContext prepareActionEditScheme(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditScheme() {
    	return false;
    }
	public RequestContext prepareActionAssignAcct(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAssignAcct() {
    	return false;
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
    }

    /**
     * output ActionAddUser class
     */     
    protected class ActionAddUser extends ItemAction {     
    
        public ActionAddUser()
        {
            this(null);
        }

        public ActionAddUser(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddUser.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddUser.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddUser.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAcctAccreditUI.this, "ActionAddUser", "actionAddUser_actionPerformed", e);
        }
    }

    /**
     * output ActionDelUser class
     */     
    protected class ActionDelUser extends ItemAction {     
    
        public ActionDelUser()
        {
            this(null);
        }

        public ActionDelUser(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelUser.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelUser.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelUser.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAcctAccreditUI.this, "ActionDelUser", "actionDelUser_actionPerformed", e);
        }
    }

    /**
     * output ActionAddScheme class
     */     
    protected class ActionAddScheme extends ItemAction {     
    
        public ActionAddScheme()
        {
            this(null);
        }

        public ActionAddScheme(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddScheme.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddScheme.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddScheme.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAcctAccreditUI.this, "ActionAddScheme", "actionAddScheme_actionPerformed", e);
        }
    }

    /**
     * output ActionDelScheme class
     */     
    protected class ActionDelScheme extends ItemAction {     
    
        public ActionDelScheme()
        {
            this(null);
        }

        public ActionDelScheme(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelScheme.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelScheme.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelScheme.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAcctAccreditUI.this, "ActionDelScheme", "actionDelScheme_actionPerformed", e);
        }
    }

    /**
     * output ActionViewScheme class
     */     
    protected class ActionViewScheme extends ItemAction {     
    
        public ActionViewScheme()
        {
            this(null);
        }

        public ActionViewScheme(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewScheme.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewScheme.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewScheme.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAcctAccreditUI.this, "ActionViewScheme", "actionViewScheme_actionPerformed", e);
        }
    }

    /**
     * output ActionEditScheme class
     */     
    protected class ActionEditScheme extends ItemAction {     
    
        public ActionEditScheme()
        {
            this(null);
        }

        public ActionEditScheme(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEditScheme.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditScheme.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditScheme.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAcctAccreditUI.this, "ActionEditScheme", "actionEditScheme_actionPerformed", e);
        }
    }

    /**
     * output ActionAssignAcct class
     */     
    protected class ActionAssignAcct extends ItemAction {     
    
        public ActionAssignAcct()
        {
            this(null);
        }

        public ActionAssignAcct(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAssignAcct.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssignAcct.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssignAcct.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAcctAccreditUI.this, "ActionAssignAcct", "actionAssignAcct_actionPerformed", e);
        }
    }

    /**
     * output ActionSave class
     */     
    protected class ActionSave extends ItemAction {     
    
        public ActionSave()
        {
            this(null);
        }

        public ActionSave(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAcctAccreditUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "AcctAccreditUI");
    }




}