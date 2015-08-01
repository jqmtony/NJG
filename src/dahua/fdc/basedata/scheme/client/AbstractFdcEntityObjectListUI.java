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
public abstract class AbstractFdcEntityObjectListUI extends com.kingdee.eas.fdc.basedata.client.FdcTree2ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFdcEntityObjectListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSynchronizeMD;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSynchronizeEasMD;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSynchronizeBaseMD;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSynchronizeFdcMD;
    protected ActionSynchronizeMD actionSynchronizeMD = null;
    protected ActionSynchronizeEasMD actionSynchronizeEasMD = null;
    protected ActionSynchronizeBaseMD actionSynchronizeBaseMD = null;
    protected ActionSynchronizeFdcMD actionSynchronizeFdcMD = null;
    /**
     * output class constructor
     */
    public AbstractFdcEntityObjectListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFdcEntityObjectListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.basedata.scheme.app", "FdcEntityObjectQuery");
        //actionSynchronizeMD
        this.actionSynchronizeMD = new ActionSynchronizeMD(this);
        getActionManager().registerAction("actionSynchronizeMD", actionSynchronizeMD);
         this.actionSynchronizeMD.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSynchronizeEasMD
        this.actionSynchronizeEasMD = new ActionSynchronizeEasMD(this);
        getActionManager().registerAction("actionSynchronizeEasMD", actionSynchronizeEasMD);
         this.actionSynchronizeEasMD.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSynchronizeBaseMD
        this.actionSynchronizeBaseMD = new ActionSynchronizeBaseMD(this);
        getActionManager().registerAction("actionSynchronizeBaseMD", actionSynchronizeBaseMD);
         this.actionSynchronizeBaseMD.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSynchronizeFdcMD
        this.actionSynchronizeFdcMD = new ActionSynchronizeFdcMD(this);
        getActionManager().registerAction("actionSynchronizeFdcMD", actionSynchronizeFdcMD);
         this.actionSynchronizeFdcMD.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnSynchronizeMD = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSynchronizeEasMD = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSynchronizeBaseMD = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSynchronizeFdcMD = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSynchronizeMD.setName("btnSynchronizeMD");
        this.btnSynchronizeEasMD.setName("btnSynchronizeEasMD");
        this.btnSynchronizeBaseMD.setName("btnSynchronizeBaseMD");
        this.btnSynchronizeFdcMD.setName("btnSynchronizeFdcMD");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"fullName\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"alias\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fullBosTypeStr\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"bosTypeStr\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"dataTable.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"dataTable.name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"dataTable.isExist\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{fullName}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{alias}</t:Cell><t:Cell>$Resource{fullBosTypeStr}</t:Cell><t:Cell>$Resource{bosTypeStr}</t:Cell><t:Cell>$Resource{dataTable.id}</t:Cell><t:Cell>$Resource{dataTable.name}</t:Cell><t:Cell>$Resource{dataTable.isExist}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","fullName","name","alias","fullBosTypeStr","bosTypeStr","dataTable.id","dataTable.name","dataTable.isExist"});


		String kdtEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"property.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"property.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"property.alias\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"column.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column.sqlType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column.length\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column.scale\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column.precision\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column.isNullable\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column.defaultValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column.isMultilingual\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"column.isExist\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"linkProperty.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"linkProperty.fullName\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"linkProperty.name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"linkProperty.alias\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"linkProperty.bosTypeStr\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"property.relationshipUrlName\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{property.id}</t:Cell><t:Cell>$Resource{property.name}</t:Cell><t:Cell>$Resource{property.alias}</t:Cell><t:Cell>$Resource{column.id}</t:Cell><t:Cell>$Resource{column.name}</t:Cell><t:Cell>$Resource{column.sqlType}</t:Cell><t:Cell>$Resource{column.length}</t:Cell><t:Cell>$Resource{column.scale}</t:Cell><t:Cell>$Resource{column.precision}</t:Cell><t:Cell>$Resource{column.isNullable}</t:Cell><t:Cell>$Resource{column.defaultValue}</t:Cell><t:Cell>$Resource{column.isMultilingual}</t:Cell><t:Cell>$Resource{column.isExist}</t:Cell><t:Cell>$Resource{linkProperty.id}</t:Cell><t:Cell>$Resource{linkProperty.fullName}</t:Cell><t:Cell>$Resource{linkProperty.name}</t:Cell><t:Cell>$Resource{linkProperty.alias}</t:Cell><t:Cell>$Resource{linkProperty.bosTypeStr}</t:Cell><t:Cell>$Resource{property.relationshipUrlName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntries.setFormatXml(resHelper.translateString("kdtEntries",kdtEntriesStrXML));

        

        // btnSynchronizeMD
        this.btnSynchronizeMD.setAction((IItemAction)ActionProxyFactory.getProxy(actionSynchronizeMD, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSynchronizeMD.setText(resHelper.getString("btnSynchronizeMD.text"));		
        this.btnSynchronizeMD.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_synchronization"));
        // btnSynchronizeEasMD
        this.btnSynchronizeEasMD.setAction((IItemAction)ActionProxyFactory.getProxy(actionSynchronizeEasMD, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSynchronizeEasMD.setText(resHelper.getString("btnSynchronizeEasMD.text"));		
        this.btnSynchronizeEasMD.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_synchronization"));
        // btnSynchronizeBaseMD
        this.btnSynchronizeBaseMD.setAction((IItemAction)ActionProxyFactory.getProxy(actionSynchronizeBaseMD, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSynchronizeBaseMD.setText(resHelper.getString("btnSynchronizeBaseMD.text"));		
        this.btnSynchronizeBaseMD.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_synchronization"));
        // btnSynchronizeFdcMD
        this.btnSynchronizeFdcMD.setAction((IItemAction)ActionProxyFactory.getProxy(actionSynchronizeFdcMD, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSynchronizeFdcMD.setText(resHelper.getString("btnSynchronizeFdcMD.text"));		
        this.btnSynchronizeFdcMD.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_synchronization"));
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
        pnlMain.setBounds(new Rectangle(10, 10, 996, 580));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 996, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(kDContainer1, "left");
        pnlMain.add(kDSplitPane1, "right");
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(treeView, BorderLayout.CENTER);
        //treeView
        treeView.setTree(treeMain);
        //kDSplitPane1
        kDSplitPane1.add(kDContainer2, "top");
        kDSplitPane1.add(kDContainer3, "bottom");
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(tblMain, BorderLayout.CENTER);
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer3.getContentPane().add(kdtEntries, BorderLayout.CENTER);

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
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemMoveTree);
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
        menuBiz.add(menuItemConfig);
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
        
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnConfig);
        this.toolBar.add(btnSegment);
        this.toolBar.add(btnSynchronizeMD);
        this.toolBar.add(btnSynchronizeEasMD);
        this.toolBar.add(btnSynchronizeBaseMD);
        this.toolBar.add(btnSynchronizeFdcMD);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.scheme.app.FdcEntityObjectListUIHandler";
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
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("fullName"));
        sic.add(new SelectorItemInfo("alias"));
        sic.add(new SelectorItemInfo("bosTypeStr"));
        sic.add(new SelectorItemInfo("dataTable.name"));
        sic.add(new SelectorItemInfo("dataTable.id"));
        sic.add(new SelectorItemInfo("dataTable.isExist"));
        sic.add(new SelectorItemInfo("fullBosTypeStr"));
        return sic;
    }        
    	

    /**
     * output actionSynchronizeMD_actionPerformed method
     */
    public void actionSynchronizeMD_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSynchronizeEasMD_actionPerformed method
     */
    public void actionSynchronizeEasMD_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSynchronizeBaseMD_actionPerformed method
     */
    public void actionSynchronizeBaseMD_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSynchronizeFdcMD_actionPerformed method
     */
    public void actionSynchronizeFdcMD_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSynchronizeMD(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSynchronizeMD() {
    	return false;
    }
	public RequestContext prepareActionSynchronizeEasMD(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSynchronizeEasMD() {
    	return false;
    }
	public RequestContext prepareActionSynchronizeBaseMD(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSynchronizeBaseMD() {
    	return false;
    }
	public RequestContext prepareActionSynchronizeFdcMD(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSynchronizeFdcMD() {
    	return false;
    }

    /**
     * output ActionSynchronizeMD class
     */     
    protected class ActionSynchronizeMD extends ItemAction {     
    
        public ActionSynchronizeMD()
        {
            this(null);
        }

        public ActionSynchronizeMD(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSynchronizeMD.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSynchronizeMD.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSynchronizeMD.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFdcEntityObjectListUI.this, "ActionSynchronizeMD", "actionSynchronizeMD_actionPerformed", e);
        }
    }

    /**
     * output ActionSynchronizeEasMD class
     */     
    protected class ActionSynchronizeEasMD extends ItemAction {     
    
        public ActionSynchronizeEasMD()
        {
            this(null);
        }

        public ActionSynchronizeEasMD(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSynchronizeEasMD.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSynchronizeEasMD.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSynchronizeEasMD.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFdcEntityObjectListUI.this, "ActionSynchronizeEasMD", "actionSynchronizeEasMD_actionPerformed", e);
        }
    }

    /**
     * output ActionSynchronizeBaseMD class
     */     
    protected class ActionSynchronizeBaseMD extends ItemAction {     
    
        public ActionSynchronizeBaseMD()
        {
            this(null);
        }

        public ActionSynchronizeBaseMD(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSynchronizeBaseMD.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSynchronizeBaseMD.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSynchronizeBaseMD.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFdcEntityObjectListUI.this, "ActionSynchronizeBaseMD", "actionSynchronizeBaseMD_actionPerformed", e);
        }
    }

    /**
     * output ActionSynchronizeFdcMD class
     */     
    protected class ActionSynchronizeFdcMD extends ItemAction {     
    
        public ActionSynchronizeFdcMD()
        {
            this(null);
        }

        public ActionSynchronizeFdcMD(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSynchronizeFdcMD.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSynchronizeFdcMD.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSynchronizeFdcMD.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFdcEntityObjectListUI.this, "ActionSynchronizeFdcMD", "actionSynchronizeFdcMD_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.scheme.client", "FdcEntityObjectListUI");
    }




}