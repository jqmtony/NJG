/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

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
public abstract class AbstractMaterialInfoUI extends com.kingdee.eas.fdc.basedata.client.FDCBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMaterialInfoUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDTree kDTree1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable MaterialInfo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton showDotsMap;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton showLineMap;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton KDBtnExcelImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton KDExportExcel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton KDBtnMaterialImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton audit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton unAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem ExcelImportItem;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem ExportExcelItem;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem MaterialImportItem;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator10;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem showDotsMapItem;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem showLineMapItem;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator11;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem auditItem;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem unAuditItem;
    protected EntityViewInfo materialInfoQuery = null;
    protected IMetaDataPK materialInfoQueryPK;
    protected actionShowDotsMap actionShowDotsMap = null;
    protected actionShowLineMap actionShowLineMap = null;
    protected actionImportDataFormMaterial actionImportDataFormMaterial = null;
    protected actionExportExcel actionExportExcel = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    /**
     * output class constructor
     */
    public AbstractMaterialInfoUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMaterialInfoUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.material.app", "MaterialQuery");
        materialInfoQueryPK = new MetaDataPK("com.kingdee.eas.fdc.material.app", "MaterialInfoQuery");
        //actionShowDotsMap
        this.actionShowDotsMap = new actionShowDotsMap(this);
        getActionManager().registerAction("actionShowDotsMap", actionShowDotsMap);
         this.actionShowDotsMap.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowLineMap
        this.actionShowLineMap = new actionShowLineMap(this);
        getActionManager().registerAction("actionShowLineMap", actionShowLineMap);
         this.actionShowLineMap.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportDataFormMaterial
        this.actionImportDataFormMaterial = new actionImportDataFormMaterial(this);
        getActionManager().registerAction("actionImportDataFormMaterial", actionImportDataFormMaterial);
         this.actionImportDataFormMaterial.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportExcel
        this.actionExportExcel = new actionExportExcel(this);
        getActionManager().registerAction("actionExportExcel", actionExportExcel);
         this.actionExportExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDTreeView1 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDTree1 = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.MaterialInfo = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.showDotsMap = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.showLineMap = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.KDBtnExcelImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.KDExportExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.KDBtnMaterialImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.audit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.unAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.ExcelImportItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.ExportExcelItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.MaterialImportItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator10 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.showDotsMapItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.showLineMapItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator11 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.auditItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.unAuditItem = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDTreeView1.setName("kDTreeView1");
        this.kDPanel1.setName("kDPanel1");
        this.kDTree1.setName("kDTree1");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.MaterialInfo.setName("MaterialInfo");
        this.showDotsMap.setName("showDotsMap");
        this.showLineMap.setName("showLineMap");
        this.KDBtnExcelImport.setName("KDBtnExcelImport");
        this.KDExportExcel.setName("KDExportExcel");
        this.KDBtnMaterialImport.setName("KDBtnMaterialImport");
        this.audit.setName("audit");
        this.unAudit.setName("unAudit");
        this.ExcelImportItem.setName("ExcelImportItem");
        this.ExportExcelItem.setName("ExportExcelItem");
        this.MaterialImportItem.setName("MaterialImportItem");
        this.kDSeparator10.setName("kDSeparator10");
        this.kDSeparator9.setName("kDSeparator9");
        this.showDotsMapItem.setName("showDotsMapItem");
        this.showLineMapItem.setName("showLineMapItem");
        this.kDSeparator11.setName("kDSeparator11");
        this.kDSeparator8.setName("kDSeparator8");
        this.auditItem.setName("auditItem");
        this.unAuditItem.setName("unAuditItem");
        // CoreUI		
        this.menuTool.setVisible(false);		
        this.MenuService.setVisible(false);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>0.00</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"model\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"baseUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"winPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"quoteTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"supplier\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{model}</t:Cell><t:Cell>$Resource{baseUnit.name}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{winPrice}</t:Cell><t:Cell>$Resource{quoteTime}</t:Cell><t:Cell>$Resource{supplier}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"0\" t:right=\"0\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"","","","","","","",""});

		
        this.btnLocate.setVisible(false);		
        this.btnQuery.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemImportData.setVisible(true);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuItemLocate.setVisible(false);		
        this.menuItemQuery.setVisible(false);		
        this.menuItemRefresh.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.menuItemExportData.setVisible(true);		
        this.MenuItemAttachment.setVisible(false);		
        this.btnQueryScheme.setVisible(false);		
        this.menuItemQueryScheme.setVisible(false);		
        this.btnVoucher.setVisible(false);		
        this.btnDelVoucher.setVisible(false);		
        this.btnCreateTo.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.kDSeparator4.setVisible(true);		
        this.menuItemCreateTo.setVisible(false);		
        this.menuItemCopyTo.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuWorkFlow.setVisible(false);		
        this.menuItemVoucher.setVisible(false);		
        this.menuItemDelVoucher.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(200);
        // kDTreeView1		
        this.kDTreeView1.setShowButton(false);		
        this.kDTreeView1.setShowControlPanel(false);
        // kDPanel1
        // kDTree1
        // kDContainer1		
        this.kDContainer1.setAutoscrolls(true);		
        this.kDContainer1.setEnableActive(false);
        // kDContainer2		
        this.kDContainer2.setAutoscrolls(true);		
        this.kDContainer2.setEnableActive(false);
        // MaterialInfo
		String MaterialInfoStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /><c:NumberFormat>0.00</c:NumberFormat></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"quoteTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"supplier\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"contractBill\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"validDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"sstate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{quoteTime}</t:Cell><t:Cell>$Resource{supplier}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{contractBill}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{validDate}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{sstate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.MaterialInfo.setFormatXml(resHelper.translateString("MaterialInfo",MaterialInfoStrXML));
                this.MaterialInfo.putBindContents("materialInfoQuery",new String[] {"price","quoteTime","supplier.name","project.name","contractBill.name","mState","id","","","state"});

        this.MaterialInfo.addRequestRowSetListener(new RequestRowSetListener() {
            public void doRequestRowSet(RequestRowSetEvent e) {
                MaterialInfo_doRequestRowSet(e);
            }
        });

        // showDotsMap
        this.showDotsMap.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowDotsMap, new Class[] { IItemAction.class }, getServiceContext()));		
        this.showDotsMap.setText(resHelper.getString("showDotsMap.text"));		
        this.showDotsMap.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_chart"));
        // showLineMap
        this.showLineMap.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowLineMap, new Class[] { IItemAction.class }, getServiceContext()));		
        this.showLineMap.setText(resHelper.getString("showLineMap.text"));		
        this.showLineMap.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_chart"));
        // KDBtnExcelImport
        this.KDBtnExcelImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportData, new Class[] { IItemAction.class }, getServiceContext()));		
        this.KDBtnExcelImport.setText(resHelper.getString("KDBtnExcelImport.text"));		
        this.KDBtnExcelImport.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
        // KDExportExcel
        this.KDExportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.KDExportExcel.setText(resHelper.getString("KDExportExcel.text"));		
        this.KDExportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_dcdwj"));
        // KDBtnMaterialImport
        this.KDBtnMaterialImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportDataFormMaterial, new Class[] { IItemAction.class }, getServiceContext()));		
        this.KDBtnMaterialImport.setText(resHelper.getString("KDBtnMaterialImport.text"));		
        this.KDBtnMaterialImport.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importfromzz"));
        // audit
        this.audit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.audit.setText(resHelper.getString("audit.text"));
        this.audit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    audit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // unAudit
        this.unAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.unAudit.setText(resHelper.getString("unAudit.text"));
        // ExcelImportItem
        this.ExcelImportItem.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportData, new Class[] { IItemAction.class }, getServiceContext()));		
        this.ExcelImportItem.setText(resHelper.getString("ExcelImportItem.text"));		
        this.ExcelImportItem.setToolTipText(resHelper.getString("ExcelImportItem.toolTipText"));
        // ExportExcelItem
        this.ExportExcelItem.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.ExportExcelItem.setText(resHelper.getString("ExportExcelItem.text"));		
        this.ExportExcelItem.setToolTipText(resHelper.getString("ExportExcelItem.toolTipText"));
        // MaterialImportItem
        this.MaterialImportItem.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportDataFormMaterial, new Class[] { IItemAction.class }, getServiceContext()));		
        this.MaterialImportItem.setText(resHelper.getString("MaterialImportItem.text"));		
        this.MaterialImportItem.setToolTipText(resHelper.getString("MaterialImportItem.toolTipText"));
        // kDSeparator10
        // kDSeparator9
        // showDotsMapItem
        this.showDotsMapItem.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowDotsMap, new Class[] { IItemAction.class }, getServiceContext()));		
        this.showDotsMapItem.setText(resHelper.getString("showDotsMapItem.text"));
        this.showDotsMapItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    showDotsMapItem_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // showLineMapItem
        this.showLineMapItem.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowLineMap, new Class[] { IItemAction.class }, getServiceContext()));		
        this.showLineMapItem.setText(resHelper.getString("showLineMapItem.text"));
        // kDSeparator11
        // kDSeparator8
        // auditItem
        this.auditItem.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.auditItem.setText(resHelper.getString("auditItem.text"));
        // unAuditItem
        this.unAuditItem.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.unAuditItem.setText(resHelper.getString("unAuditItem.text"));
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
        kDSplitPane1.setBounds(new Rectangle(10, 10, 993, 617));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 993, 617, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(kDTreeView1, "left");
        kDSplitPane1.add(kDPanel1, "right");
        //kDTreeView1
        kDTreeView1.setTree(kDTree1);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 782, 616));        kDContainer1.setBounds(new Rectangle(0, 0, 782, 286));
        kDPanel1.add(kDContainer1, new KDLayout.Constraints(0, 0, 782, 286, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer2.setBounds(new Rectangle(0, 298, 781, 290));
        kDPanel1.add(kDContainer2, new KDLayout.Constraints(0, 298, 781, 290, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblMain, BorderLayout.CENTER);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(MaterialInfo, BorderLayout.CENTER);

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
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(ExcelImportItem);
        menuFile.add(ExportExcelItem);
        menuFile.add(MaterialImportItem);
        menuFile.add(kDSeparator10);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(kDSeparator9);
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
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        menuView.add(showDotsMapItem);
        menuView.add(showLineMapItem);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(kDSeparator11);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(kDSeparator8);
        menuBiz.add(auditItem);
        menuBiz.add(unAuditItem);
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
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(showDotsMap);
        this.toolBar.add(showLineMap);
        this.toolBar.add(KDBtnExcelImport);
        this.toolBar.add(KDExportExcel);
        this.toolBar.add(KDBtnMaterialImport);
        this.toolBar.add(audit);
        this.toolBar.add(unAudit);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.material.app.MaterialInfoUIHandler";
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
        if (key.equalsIgnoreCase("materialInfoQuery")) {
            this.materialInfoQuery = (EntityViewInfo)dataObject;
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
     * output MaterialInfo_doRequestRowSet method
     */
    protected void MaterialInfo_doRequestRowSet(RequestRowSetEvent e)
    {
        if (this.materialInfoQuery != null) {
            int start = ((Integer)e.getParam1()).intValue();
            int length = ((Integer)e.getParam2()).intValue() - start + 1;
            try {
                IQueryExecutor exec = this.getQueryExecutor(this.materialInfoQueryPK, this.materialInfoQuery);
                IRowSet rowSet = exec.executeQuery(start,length);
                e.setRowSet(rowSet);
                onGetRowSet(rowSet);
            } catch (Exception ee) {
                handUIException(ee);
            }
        }
    }

    /**
     * output audit_actionPerformed method
     */
    protected void audit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output showDotsMapItem_actionPerformed method
     */
    protected void showDotsMapItem_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getQueryExecutor method
     */
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo)
    {
        IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(queryPK);
        exec.setObjectView(viewInfo);
        return exec;
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
     * output actionShowDotsMap_actionPerformed method
     */
    public void actionShowDotsMap_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowLineMap_actionPerformed method
     */
    public void actionShowLineMap_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportDataFormMaterial_actionPerformed method
     */
    public void actionImportDataFormMaterial_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportExcel_actionPerformed method
     */
    public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareactionShowDotsMap(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionShowDotsMap() {
    	return false;
    }
	public RequestContext prepareactionShowLineMap(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionShowLineMap() {
    	return false;
    }
	public RequestContext prepareactionImportDataFormMaterial(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionImportDataFormMaterial() {
    	return false;
    }
	public RequestContext prepareactionExportExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionExportExcel() {
    	return false;
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }

    /**
     * output actionShowDotsMap class
     */     
    protected class actionShowDotsMap extends ItemAction {     
    
        public actionShowDotsMap()
        {
            this(null);
        }

        public actionShowDotsMap(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionShowDotsMap.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionShowDotsMap.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionShowDotsMap.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMaterialInfoUI.this, "actionShowDotsMap", "actionShowDotsMap_actionPerformed", e);
        }
    }

    /**
     * output actionShowLineMap class
     */     
    protected class actionShowLineMap extends ItemAction {     
    
        public actionShowLineMap()
        {
            this(null);
        }

        public actionShowLineMap(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionShowLineMap.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionShowLineMap.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionShowLineMap.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMaterialInfoUI.this, "actionShowLineMap", "actionShowLineMap_actionPerformed", e);
        }
    }

    /**
     * output actionImportDataFormMaterial class
     */     
    protected class actionImportDataFormMaterial extends ItemAction {     
    
        public actionImportDataFormMaterial()
        {
            this(null);
        }

        public actionImportDataFormMaterial(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionImportDataFormMaterial.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImportDataFormMaterial.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImportDataFormMaterial.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMaterialInfoUI.this, "actionImportDataFormMaterial", "actionImportDataFormMaterial_actionPerformed", e);
        }
    }

    /**
     * output actionExportExcel class
     */     
    protected class actionExportExcel extends ItemAction {     
    
        public actionExportExcel()
        {
            this(null);
        }

        public actionExportExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionExportExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionExportExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionExportExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMaterialInfoUI.this, "actionExportExcel", "actionExportExcel_actionPerformed", e);
        }
    }

    /**
     * output ActionAudit class
     */     
    protected class ActionAudit extends ItemAction {     
    
        public ActionAudit()
        {
            this(null);
        }

        public ActionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMaterialInfoUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAudit class
     */     
    protected class ActionUnAudit extends ItemAction {     
    
        public ActionUnAudit()
        {
            this(null);
        }

        public ActionUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMaterialInfoUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.material.client", "MaterialInfoUI");
    }




}