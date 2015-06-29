/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.client;

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
public abstract class AbstractRichExamedManagerUI extends com.kingdee.eas.framework.client.CoreBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRichExamedManagerUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel tbl;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblInvoiceRequest;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblOtherBill;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCompanyoff;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCustomerOff;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddNewInvoite;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton butnInvoiteCreatToBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCompanyOff;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCustomerOff;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSyncustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewLog;
    protected EntityViewInfo queryF7RichInvoiceRequestQuery = null;
    protected IMetaDataPK queryF7RichInvoiceRequestQueryPK;
    protected EntityViewInfo queryF7RichCompayWriteOffQuery = null;
    protected IMetaDataPK queryF7RichCompayWriteOffQueryPK;
    protected EntityViewInfo queryF7RichCustomWriteOffQuery = null;
    protected IMetaDataPK queryF7RichCustomWriteOffQueryPK;
    protected ActionTDPrint actionTDPrint = null;
    protected ActionTDPrintPreview actionTDPrintPreview = null;
    protected actionAddNewInviton actionAddNewInviton = null;
    protected actionInvoiteCreatToBill actionInvoiteCreatToBill = null;
    protected actionCompnyOff actionCompnyOff = null;
    protected actionCustomerOff actionCustomerOff = null;
    protected actionSyncustomer actionSyncustomer = null;
    protected actionViewLog actionViewLog = null;
    public final static String STATUS_VIEW = "VIEW";
    /**
     * output class constructor
     */
    public AbstractRichExamedManagerUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRichExamedManagerUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.custom.richinf.app", "F7RichExamedQuery");
        queryF7RichInvoiceRequestQueryPK = new MetaDataPK("com.kingdee.eas.custom.richinf.query", "F7RichInvoiceRequestQuery");
        queryF7RichCompayWriteOffQueryPK = new MetaDataPK("com.kingdee.eas.custom.richinf.query", "F7RichCompayWriteOffQuery");
        queryF7RichCustomWriteOffQueryPK = new MetaDataPK("com.kingdee.eas.custom.richinf.query", "F7RichCustomWriteOffQuery");
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
        //actionTDPrint
        this.actionTDPrint = new ActionTDPrint(this);
        getActionManager().registerAction("actionTDPrint", actionTDPrint);
         this.actionTDPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTDPrintPreview
        this.actionTDPrintPreview = new ActionTDPrintPreview(this);
        getActionManager().registerAction("actionTDPrintPreview", actionTDPrintPreview);
         this.actionTDPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddNewInviton
        this.actionAddNewInviton = new actionAddNewInviton(this);
        getActionManager().registerAction("actionAddNewInviton", actionAddNewInviton);
         this.actionAddNewInviton.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInvoiteCreatToBill
        this.actionInvoiteCreatToBill = new actionInvoiteCreatToBill(this);
        getActionManager().registerAction("actionInvoiteCreatToBill", actionInvoiteCreatToBill);
         this.actionInvoiteCreatToBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCompnyOff
        this.actionCompnyOff = new actionCompnyOff(this);
        getActionManager().registerAction("actionCompnyOff", actionCompnyOff);
         this.actionCompnyOff.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCustomerOff
        this.actionCustomerOff = new actionCustomerOff(this);
        getActionManager().registerAction("actionCustomerOff", actionCustomerOff);
         this.actionCustomerOff.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSyncustomer
        this.actionSyncustomer = new actionSyncustomer(this);
        getActionManager().registerAction("actionSyncustomer", actionSyncustomer);
         this.actionSyncustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewLog
        this.actionViewLog = new actionViewLog(this);
        getActionManager().registerAction("actionViewLog", actionViewLog);
         this.actionViewLog.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tbl = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblInvoiceRequest = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblOtherBill = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblCompanyoff = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblCustomerOff = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddNewInvoite = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.butnInvoiteCreatToBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCompanyOff = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCustomerOff = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSyncustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewLog = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel3.setName("kDLabel3");
        this.kDLabel4.setName("kDLabel4");
        this.kDContainer1.setName("kDContainer1");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.tbl.setName("tbl");
        this.tblInvoiceRequest.setName("tblInvoiceRequest");
        this.tblOtherBill.setName("tblOtherBill");
        this.tblCompanyoff.setName("tblCompanyoff");
        this.tblCustomerOff.setName("tblCustomerOff");
        this.btnAddNewInvoite.setName("btnAddNewInvoite");
        this.butnInvoiteCreatToBill.setName("butnInvoiteCreatToBill");
        this.btnCompanyOff.setName("btnCompanyOff");
        this.btnCustomerOff.setName("btnCustomerOff");
        this.btnSyncustomer.setName("btnSyncustomer");
        this.btnViewLog.setName("btnViewLog");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"ywNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"ldNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"sales.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"yhxAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"nbyhxAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"dj\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"hc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"kpUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"kpCompany.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"djCompany.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"zjjg.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"qyUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"djUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"fkUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"fpNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"tjType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"djDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{ywNumber}</t:Cell><t:Cell>$Resource{ldNumber}</t:Cell><t:Cell>$Resource{sales.name}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{yhxAmount}</t:Cell><t:Cell>$Resource{nbyhxAmount}</t:Cell><t:Cell>$Resource{dj}</t:Cell><t:Cell>$Resource{hc}</t:Cell><t:Cell>$Resource{kpUnit.name}</t:Cell><t:Cell>$Resource{kpCompany.name}</t:Cell><t:Cell>$Resource{djCompany.name}</t:Cell><t:Cell>$Resource{zjjg.name}</t:Cell><t:Cell>$Resource{qyUnit.name}</t:Cell><t:Cell>$Resource{djUnit.name}</t:Cell><t:Cell>$Resource{fkUnit.name}</t:Cell><t:Cell>$Resource{fpNumber}</t:Cell><t:Cell>$Resource{tjType.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{djDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","","ldNumber","sales.name","amount","yhxAmount","nbyhxAmount","dj","hc","kpUnit.name","kpCompany.name","djCompany.name","zjjg.name","qyUnit.name","djUnit.name","fkUnit.name","fpNumber","tjType.name","createTime","bizDate","djDate"});

		
        this.separatorFW2.setVisible(true);
        // kDSplitPane1		
        this.kDSplitPane1.setOrientation(0);		
        this.kDSplitPane1.setDividerLocation(280);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));		
        this.kDLabel1.setBackground(new java.awt.Color(0,255,128));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);
        // kDTabbedPane1
        // kDPanel1
        // kDPanel2
        // kDPanel3
        // tbl
        // tblInvoiceRequest
		String tblInvoiceRequestStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"ldNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"kpUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sales.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"kpCompany.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"reqSumAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"invoicedAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"djAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"djkp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"billState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"Fivouchered\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"bizState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{ldNumber}</t:Cell><t:Cell>$Resource{kpUnit.name}</t:Cell><t:Cell>$Resource{sales.name}</t:Cell><t:Cell>$Resource{kpCompany.name}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{reqSumAmount}</t:Cell><t:Cell>$Resource{invoicedAmount}</t:Cell><t:Cell>$Resource{djAmount}</t:Cell><t:Cell>$Resource{djkp}</t:Cell><t:Cell>$Resource{billState}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{Fivouchered}</t:Cell><t:Cell>$Resource{bizState}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblInvoiceRequest.setFormatXml(resHelper.translateString("tblInvoiceRequest",tblInvoiceRequestStrXML));
        this.tblInvoiceRequest.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblInvoiceRequest_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
                this.tblInvoiceRequest.putBindContents("queryF7RichInvoiceRequestQuery",new String[] {"id","number","bizDate","ldNumber","kpUnit.name","sales.name","kpCompany.name","amount","reqSumAmount","invoicedAmount","djAmount","djkp","billState","auditDate","createTime","lastUpdateTime","creator.name","lastUpdateUser.name","Fivouchered","bizState","beizhu"});

        this.tblInvoiceRequest.addRequestRowSetListener(new RequestRowSetListener() {
            public void doRequestRowSet(RequestRowSetEvent e) {
                tblInvoiceRequest_doRequestRowSet(e);
            }
        });

        // tblOtherBill
		String tblOtherBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol20\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol24\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol25\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol26\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol27\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol28\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol29\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol34\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol35\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol36\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol41\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol44\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol45\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol47\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol48\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol49\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol50\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol51\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol52\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol53\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol54\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol55\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol56\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol57\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol58\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol59\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol60\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol61\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol62\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol63\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol64\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol65\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol66\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol68\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol69\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol70\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol73\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol75\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol76\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol82\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol83\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol84\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol85\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol87\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol88\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol89\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol90\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol91\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol101\"><c:NumberFormat>%r{yyyy-M-d}t</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"company.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"company.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"billStatus\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"billDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"bizDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"billType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"bizType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"sourceBillType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"asstActType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"asstActNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"asstActName\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"currency\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"exchangeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"adminOrg\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"costCenter\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"saleOrg\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"saleGroup\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"person\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"paymentType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"payCondition\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"cashDiscount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"abstractName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"totalRecPayAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"totalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"totalTaxAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"totalVerifyAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"totalBadAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"totalUnVerifyAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"entry.seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"coreBillType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" /><t:Column t:key=\"coreBillNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" /><t:Column t:key=\"coreBillEntrySeq\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" /><t:Column t:key=\"project\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" t:styleID=\"sCol34\" /><t:Column t:key=\"trackNumberzc\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" t:styleID=\"sCol35\" /><t:Column t:key=\"lot\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" t:styleID=\"sCol36\" /><t:Column t:key=\"contractNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" /><t:Column t:key=\"contractEntrySeq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" /><t:Column t:key=\"material.number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" /><t:Column t:key=\"material.name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" /><t:Column t:key=\"pricePrecision\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"41\" t:styleID=\"sCol41\" /><t:Column t:key=\"specification\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"42\" /><t:Column t:key=\"assistProperty\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"43\" /><t:Column t:key=\"expenseItem.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"44\" t:styleID=\"sCol44\" /><t:Column t:key=\"expenseItem.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"45\" t:styleID=\"sCol45\" /><t:Column t:key=\"measureUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"46\" /><t:Column t:key=\"qty\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"47\" t:styleID=\"sCol47\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"48\" t:styleID=\"sCol48\" /><t:Column t:key=\"taxRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"49\" t:styleID=\"sCol49\" /><t:Column t:key=\"taxPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"50\" t:styleID=\"sCol50\" /><t:Column t:key=\"discountType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"51\" t:styleID=\"sCol51\" /><t:Column t:key=\"discountRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"52\" t:styleID=\"sCol52\" /><t:Column t:key=\"realPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"53\" t:styleID=\"sCol53\" /><t:Column t:key=\"actualPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"54\" t:styleID=\"sCol54\" /><t:Column t:key=\"recieveAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"55\" t:styleID=\"sCol55\" /><t:Column t:key=\"amount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"56\" t:styleID=\"sCol56\" /><t:Column t:key=\"taxAmount\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"57\" t:styleID=\"sCol57\" /><t:Column t:key=\"discountAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"58\" t:styleID=\"sCol58\" /><t:Column t:key=\"lockVerifyAmt\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"59\" t:styleID=\"sCol59\" /><t:Column t:key=\"lockUnVerifyAmt\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"60\" t:styleID=\"sCol60\" /><t:Column t:key=\"verifyAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"61\" t:styleID=\"sCol61\" /><t:Column t:key=\"badAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"62\" t:styleID=\"sCol62\" /><t:Column t:key=\"unVerifyAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"63\" t:styleID=\"sCol63\" /><t:Column t:key=\"preparedBadAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"64\" t:styleID=\"sCol64\" /><t:Column t:key=\"preparedBadAmountLoc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"65\" t:styleID=\"sCol65\" /><t:Column t:key=\"account\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"66\" t:styleID=\"sCol66\" /><t:Column t:key=\"oppAccount.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"67\" /><t:Column t:key=\"remark\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"68\" t:styleID=\"sCol68\" /><t:Column t:key=\"isNeedVoucher\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"69\" t:styleID=\"sCol69\" /><t:Column t:key=\"fivouchered\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"70\" t:styleID=\"sCol70\" /><t:Column t:key=\"voucherType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"71\" /><t:Column t:key=\"voucherNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"72\" /><t:Column t:key=\"voucherID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"73\" t:styleID=\"sCol73\" /><t:Column t:key=\"invoiceNumber\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"74\" /><t:Column t:key=\"invoiceAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"75\" t:styleID=\"sCol75\" /><t:Column t:key=\"isInitBill\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"76\" t:styleID=\"sCol76\" /><t:Column t:key=\"isImportBill\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"77\" /><t:Column t:key=\"isTransBill\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"78\" /><t:Column t:key=\"isAllowanceBill\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"79\" /><t:Column t:key=\"isReversed\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"80\" /><t:Column t:key=\"isReverseBill\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"81\" /><t:Column t:key=\"isBizBill\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"82\" t:styleID=\"sCol82\" /><t:Column t:key=\"entry.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"83\" t:styleID=\"sCol83\" /><t:Column t:key=\"currency.precision\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"84\" t:styleID=\"sCol84\" /><t:Column t:key=\"lastExchangeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"85\" t:styleID=\"sCol85\" /><t:Column t:key=\"isFullWriteOff\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"86\" /><t:Column t:key=\"localWrittenOffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"87\" t:styleID=\"sCol87\" /><t:Column t:key=\"writtenOffBaseQty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"88\" t:styleID=\"sCol88\" /><t:Column t:key=\"localUnwriteOffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"89\" t:styleID=\"sCol89\" /><t:Column t:key=\"unwriteOffBaseQty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"90\" t:styleID=\"sCol90\" /><t:Column t:key=\"sendOrgUnit.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"91\" t:styleID=\"sCol91\" /><t:Column t:key=\"ordCustNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"92\" /><t:Column t:key=\"ordCustName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"93\" /><t:Column t:key=\"serCustNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"94\" /><t:Column t:key=\"serCustName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"95\" /><t:Column t:key=\"recAsstActNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"96\" /><t:Column t:key=\"recAsstActName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"97\" /><t:Column t:key=\"creator\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"98\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"99\" /><t:Column t:key=\"updator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"100\" /><t:Column t:key=\"updateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"101\" t:styleID=\"sCol101\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"102\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"103\" /><t:Column t:key=\"accountant\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"104\" /><t:Column t:key=\"SYNbillType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"105\" /><t:Column t:key=\"SYNbillNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"106\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{company.name}</t:Cell><t:Cell>$Resource{company.id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{billStatus}</t:Cell><t:Cell>$Resource{billDate}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{billType}</t:Cell><t:Cell>$Resource{bizType}</t:Cell><t:Cell>$Resource{sourceBillType}</t:Cell><t:Cell>$Resource{asstActType}</t:Cell><t:Cell>$Resource{asstActNumber}</t:Cell><t:Cell>$Resource{asstActName}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{exchangeRate}</t:Cell><t:Cell>$Resource{adminOrg}</t:Cell><t:Cell>$Resource{costCenter}</t:Cell><t:Cell>$Resource{saleOrg}</t:Cell><t:Cell>$Resource{saleGroup}</t:Cell><t:Cell>$Resource{person}</t:Cell><t:Cell>$Resource{paymentType}</t:Cell><t:Cell>$Resource{payCondition}</t:Cell><t:Cell>$Resource{cashDiscount}</t:Cell><t:Cell>$Resource{abstractName}</t:Cell><t:Cell>$Resource{totalRecPayAmount}</t:Cell><t:Cell>$Resource{totalAmount}</t:Cell><t:Cell>$Resource{totalTaxAmount}</t:Cell><t:Cell>$Resource{totalVerifyAmount}</t:Cell><t:Cell>$Resource{totalBadAmount}</t:Cell><t:Cell>$Resource{totalUnVerifyAmount}</t:Cell><t:Cell>$Resource{entry.seq}</t:Cell><t:Cell>$Resource{coreBillType}</t:Cell><t:Cell>$Resource{coreBillNumber}</t:Cell><t:Cell>$Resource{coreBillEntrySeq}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{trackNumberzc}</t:Cell><t:Cell>$Resource{lot}</t:Cell><t:Cell>$Resource{contractNum}</t:Cell><t:Cell>$Resource{contractEntrySeq}</t:Cell><t:Cell>$Resource{material.number}</t:Cell><t:Cell>$Resource{material.name}</t:Cell><t:Cell>$Resource{pricePrecision}</t:Cell><t:Cell>$Resource{specification}</t:Cell><t:Cell>$Resource{assistProperty}</t:Cell><t:Cell>$Resource{expenseItem.number}</t:Cell><t:Cell>$Resource{expenseItem.name}</t:Cell><t:Cell>$Resource{measureUnit}</t:Cell><t:Cell>$Resource{qty}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{taxRate}</t:Cell><t:Cell>$Resource{taxPrice}</t:Cell><t:Cell>$Resource{discountType}</t:Cell><t:Cell>$Resource{discountRate}</t:Cell><t:Cell>$Resource{realPrice}</t:Cell><t:Cell>$Resource{actualPrice}</t:Cell><t:Cell>$Resource{recieveAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{taxAmount}</t:Cell><t:Cell>$Resource{discountAmount}</t:Cell><t:Cell>$Resource{lockVerifyAmt}</t:Cell><t:Cell>$Resource{lockUnVerifyAmt}</t:Cell><t:Cell>$Resource{verifyAmount}</t:Cell><t:Cell>$Resource{badAmount}</t:Cell><t:Cell>$Resource{unVerifyAmount}</t:Cell><t:Cell>$Resource{preparedBadAmount}</t:Cell><t:Cell>$Resource{preparedBadAmountLoc}</t:Cell><t:Cell>$Resource{account}</t:Cell><t:Cell>$Resource{oppAccount.name}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{isNeedVoucher}</t:Cell><t:Cell>$Resource{fivouchered}</t:Cell><t:Cell>$Resource{voucherType}</t:Cell><t:Cell>$Resource{voucherNumber}</t:Cell><t:Cell>$Resource{voucherID}</t:Cell><t:Cell>$Resource{invoiceNumber}</t:Cell><t:Cell>$Resource{invoiceAmt}</t:Cell><t:Cell>$Resource{isInitBill}</t:Cell><t:Cell>$Resource{isImportBill}</t:Cell><t:Cell>$Resource{isTransBill}</t:Cell><t:Cell>$Resource{isAllowanceBill}</t:Cell><t:Cell>$Resource{isReversed}</t:Cell><t:Cell>$Resource{isReverseBill}</t:Cell><t:Cell>$Resource{isBizBill}</t:Cell><t:Cell>$Resource{entry.id}</t:Cell><t:Cell>$Resource{currency.precision}</t:Cell><t:Cell>$Resource{lastExchangeRate}</t:Cell><t:Cell>$Resource{isFullWriteOff}</t:Cell><t:Cell>$Resource{localWrittenOffAmount}</t:Cell><t:Cell>$Resource{writtenOffBaseQty}</t:Cell><t:Cell>$Resource{localUnwriteOffAmount}</t:Cell><t:Cell>$Resource{unwriteOffBaseQty}</t:Cell><t:Cell>$Resource{sendOrgUnit.name}</t:Cell><t:Cell>$Resource{ordCustNumber}</t:Cell><t:Cell>$Resource{ordCustName}</t:Cell><t:Cell>$Resource{serCustNumber}</t:Cell><t:Cell>$Resource{serCustName}</t:Cell><t:Cell>$Resource{recAsstActNumber}</t:Cell><t:Cell>$Resource{recAsstActName}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{updator}</t:Cell><t:Cell>$Resource{updateTime}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell><t:Cell>$Resource{accountant}</t:Cell><t:Cell>$Resource{SYNbillType}</t:Cell><t:Cell>$Resource{SYNbillNumber}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblOtherBill.setFormatXml(resHelper.translateString("tblOtherBill",tblOtherBillStrXML));
        this.tblOtherBill.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblOtherBill_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // tblCompanyoff
		String tblCompanyoffStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"sales.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"kpCustomer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"Fivouchered\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{sales.name}</t:Cell><t:Cell>$Resource{kpCustomer.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{Fivouchered}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblCompanyoff.setFormatXml(resHelper.translateString("tblCompanyoff",tblCompanyoffStrXML));
        this.tblCompanyoff.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblCompanyoff_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
                this.tblCompanyoff.putBindContents("queryF7RichCompayWriteOffQuery",new String[] {"id","number","bizDate","sales.name","kpCustomer.name","creator.name","createTime","lastUpdateTime","lastUpdateUser.name","Fivouchered"});

        this.tblCompanyoff.addRequestRowSetListener(new RequestRowSetListener() {
            public void doRequestRowSet(RequestRowSetEvent e) {
                tblCompanyoff_doRequestRowSet(e);
            }
        });

        // tblCustomerOff
		String tblCustomerOffStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"sales.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"kpCustomer.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"Fivouchered\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{sales.name}</t:Cell><t:Cell>$Resource{kpCustomer.name}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{Fivouchered}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblCustomerOff.setFormatXml(resHelper.translateString("tblCustomerOff",tblCustomerOffStrXML));
        this.tblCustomerOff.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblCustomerOff_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
                this.tblCustomerOff.putBindContents("queryF7RichCustomWriteOffQuery",new String[] {"id","number","bizDate","sales.name","kpCustomer.name","creator.name","createTime","lastUpdateTime","lastUpdateUser.name","Fivouchered"});

        this.tblCustomerOff.addRequestRowSetListener(new RequestRowSetListener() {
            public void doRequestRowSet(RequestRowSetEvent e) {
                tblCustomerOff_doRequestRowSet(e);
            }
        });

        // btnAddNewInvoite
        this.btnAddNewInvoite.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNewInviton, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddNewInvoite.setText(resHelper.getString("btnAddNewInvoite.text"));		
        this.btnAddNewInvoite.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcollection"));
        // butnInvoiteCreatToBill
        this.butnInvoiteCreatToBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionInvoiteCreatToBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.butnInvoiteCreatToBill.setText(resHelper.getString("butnInvoiteCreatToBill.text"));		
        this.butnInvoiteCreatToBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_autocreate"));
        // btnCompanyOff
        this.btnCompanyOff.setAction((IItemAction)ActionProxyFactory.getProxy(actionCompnyOff, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCompanyOff.setText(resHelper.getString("btnCompanyOff.text"));		
        this.btnCompanyOff.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_adduction"));
        // btnCustomerOff
        this.btnCustomerOff.setAction((IItemAction)ActionProxyFactory.getProxy(actionCustomerOff, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCustomerOff.setText(resHelper.getString("btnCustomerOff.text"));		
        this.btnCustomerOff.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_adduction"));
        // btnSyncustomer
        this.btnSyncustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionSyncustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSyncustomer.setText(resHelper.getString("btnSyncustomer.text"));
        // btnViewLog
        this.btnViewLog.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewLog, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewLog.setText(resHelper.getString("btnViewLog.text"));
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
        kDSplitPane1.setBounds(new Rectangle(4, 23, 1005, 603));
        this.add(kDSplitPane1, new KDLayout.Constraints(4, 23, 1005, 603, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabel1.setBounds(new Rectangle(7, 4, 80, 19));
        this.add(kDLabel1, new KDLayout.Constraints(7, 4, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel2.setBounds(new Rectangle(153, 4, 80, 19));
        this.add(kDLabel2, new KDLayout.Constraints(153, 4, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel3.setBounds(new Rectangle(299, 4, 80, 19));
        this.add(kDLabel3, new KDLayout.Constraints(299, 4, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel4.setBounds(new Rectangle(446, 4, 80, 19));
        this.add(kDLabel4, new KDLayout.Constraints(446, 4, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDSplitPane1
        kDSplitPane1.add(kDContainer1, "top");
        kDSplitPane1.add(kDTabbedPane1, "bottom");
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblMain, BorderLayout.CENTER);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane1.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
        kDTabbedPane1.add(tbl, resHelper.getString("tbl.constraints"));
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(tblInvoiceRequest, BorderLayout.CENTER);
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kDPanel2.add(tblOtherBill, BorderLayout.CENTER);
        //kDPanel3
kDPanel3.setLayout(new BorderLayout(0, 0));        kDPanel3.add(tblCompanyoff, BorderLayout.CENTER);
        //tbl
tbl.setLayout(new BorderLayout(0, 0));        tbl.add(tblCustomerOff, BorderLayout.CENTER);

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
        this.toolBar.add(btnAddNewInvoite);
        this.toolBar.add(butnInvoiteCreatToBill);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCompanyOff);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnCustomerOff);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnSyncustomer);
        this.toolBar.add(btnViewLog);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.custom.richinf.app.RichExamedManagerUIHandler";
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
        if (key.equalsIgnoreCase("queryF7RichInvoiceRequestQuery")) {
            this.queryF7RichInvoiceRequestQuery = (EntityViewInfo)dataObject;
		}
        else if (key.equalsIgnoreCase("queryF7RichCompayWriteOffQuery")) {
            this.queryF7RichCompayWriteOffQuery = (EntityViewInfo)dataObject;
		}
        else if (key.equalsIgnoreCase("queryF7RichCustomWriteOffQuery")) {
            this.queryF7RichCustomWriteOffQuery = (EntityViewInfo)dataObject;
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
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output tblInvoiceRequest_tableClicked method
     */
    protected void tblInvoiceRequest_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output tblInvoiceRequest_doRequestRowSet method
     */
    protected void tblInvoiceRequest_doRequestRowSet(RequestRowSetEvent e)
    {
        if (this.queryF7RichInvoiceRequestQuery != null) {
            int start = ((Integer)e.getParam1()).intValue();
            int length = ((Integer)e.getParam2()).intValue() - start + 1;
            try {
                IQueryExecutor exec = this.getQueryExecutor(this.queryF7RichInvoiceRequestQueryPK, this.queryF7RichInvoiceRequestQuery);
                IRowSet rowSet = exec.executeQuery(start,length);
                e.setRowSet(rowSet);
                onGetRowSet(rowSet);
            } catch (Exception ee) {
                handUIException(ee);
            }
        }
    }

    /**
     * output tblOtherBill_tableClicked method
     */
    protected void tblOtherBill_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output tblCompanyoff_tableClicked method
     */
    protected void tblCompanyoff_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output tblCompanyoff_doRequestRowSet method
     */
    protected void tblCompanyoff_doRequestRowSet(RequestRowSetEvent e)
    {
        if (this.queryF7RichCompayWriteOffQuery != null) {
            int start = ((Integer)e.getParam1()).intValue();
            int length = ((Integer)e.getParam2()).intValue() - start + 1;
            try {
                IQueryExecutor exec = this.getQueryExecutor(this.queryF7RichCompayWriteOffQueryPK, this.queryF7RichCompayWriteOffQuery);
                IRowSet rowSet = exec.executeQuery(start,length);
                e.setRowSet(rowSet);
                onGetRowSet(rowSet);
            } catch (Exception ee) {
                handUIException(ee);
            }
        }
    }

    /**
     * output tblCustomerOff_tableClicked method
     */
    protected void tblCustomerOff_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output tblCustomerOff_doRequestRowSet method
     */
    protected void tblCustomerOff_doRequestRowSet(RequestRowSetEvent e)
    {
        if (this.queryF7RichCustomWriteOffQuery != null) {
            int start = ((Integer)e.getParam1()).intValue();
            int length = ((Integer)e.getParam2()).intValue() - start + 1;
            try {
                IQueryExecutor exec = this.getQueryExecutor(this.queryF7RichCustomWriteOffQueryPK, this.queryF7RichCustomWriteOffQuery);
                IRowSet rowSet = exec.executeQuery(start,length);
                e.setRowSet(rowSet);
                onGetRowSet(rowSet);
            } catch (Exception ee) {
                handUIException(ee);
            }
        }
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
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("djDate"));
        sic.add(new SelectorItemInfo("yhxAmount"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("dj"));
        sic.add(new SelectorItemInfo("hc"));
        sic.add(new SelectorItemInfo("zjjg.name"));
        sic.add(new SelectorItemInfo("ldNumber"));
        sic.add(new SelectorItemInfo("qyUnit.name"));
        sic.add(new SelectorItemInfo("djUnit.name"));
        sic.add(new SelectorItemInfo("kpUnit.name"));
        sic.add(new SelectorItemInfo("fkUnit.name"));
        sic.add(new SelectorItemInfo("fpNumber"));
        sic.add(new SelectorItemInfo("sales.name"));
        sic.add(new SelectorItemInfo("tjType.name"));
        sic.add(new SelectorItemInfo("kpCompany.name"));
        sic.add(new SelectorItemInfo("djCompany.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("nbyhxAmount"));
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
     * output actionTDPrint_actionPerformed method
     */
    public void actionTDPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTDPrintPreview_actionPerformed method
     */
    public void actionTDPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddNewInviton_actionPerformed method
     */
    public void actionAddNewInviton_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInvoiteCreatToBill_actionPerformed method
     */
    public void actionInvoiteCreatToBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCompnyOff_actionPerformed method
     */
    public void actionCompnyOff_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCustomerOff_actionPerformed method
     */
    public void actionCustomerOff_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSyncustomer_actionPerformed method
     */
    public void actionSyncustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewLog_actionPerformed method
     */
    public void actionViewLog_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionTDPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTDPrint() {
    	return false;
    }
	public RequestContext prepareActionTDPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTDPrintPreview() {
    	return false;
    }
	public RequestContext prepareactionAddNewInviton(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAddNewInviton() {
    	return false;
    }
	public RequestContext prepareactionInvoiteCreatToBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionInvoiteCreatToBill() {
    	return false;
    }
	public RequestContext prepareactionCompnyOff(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionCompnyOff() {
    	return false;
    }
	public RequestContext prepareactionCustomerOff(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionCustomerOff() {
    	return false;
    }
	public RequestContext prepareactionSyncustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionSyncustomer() {
    	return false;
    }
	public RequestContext prepareactionViewLog(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionViewLog() {
    	return false;
    }

    /**
     * output ActionTDPrint class
     */     
    protected class ActionTDPrint extends ItemAction {     
    
        public ActionTDPrint()
        {
            this(null);
        }

        public ActionTDPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionTDPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTDPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTDPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRichExamedManagerUI.this, "ActionTDPrint", "actionTDPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionTDPrintPreview class
     */     
    protected class ActionTDPrintPreview extends ItemAction {     
    
        public ActionTDPrintPreview()
        {
            this(null);
        }

        public ActionTDPrintPreview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionTDPrintPreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTDPrintPreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTDPrintPreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRichExamedManagerUI.this, "ActionTDPrintPreview", "actionTDPrintPreview_actionPerformed", e);
        }
    }

    /**
     * output actionAddNewInviton class
     */     
    protected class actionAddNewInviton extends ItemAction {     
    
        public actionAddNewInviton()
        {
            this(null);
        }

        public actionAddNewInviton(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionAddNewInviton.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddNewInviton.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddNewInviton.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRichExamedManagerUI.this, "actionAddNewInviton", "actionAddNewInviton_actionPerformed", e);
        }
    }

    /**
     * output actionInvoiteCreatToBill class
     */     
    protected class actionInvoiteCreatToBill extends ItemAction {     
    
        public actionInvoiteCreatToBill()
        {
            this(null);
        }

        public actionInvoiteCreatToBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionInvoiteCreatToBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionInvoiteCreatToBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionInvoiteCreatToBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRichExamedManagerUI.this, "actionInvoiteCreatToBill", "actionInvoiteCreatToBill_actionPerformed", e);
        }
    }

    /**
     * output actionCompnyOff class
     */     
    protected class actionCompnyOff extends ItemAction {     
    
        public actionCompnyOff()
        {
            this(null);
        }

        public actionCompnyOff(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionCompnyOff.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionCompnyOff.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionCompnyOff.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRichExamedManagerUI.this, "actionCompnyOff", "actionCompnyOff_actionPerformed", e);
        }
    }

    /**
     * output actionCustomerOff class
     */     
    protected class actionCustomerOff extends ItemAction {     
    
        public actionCustomerOff()
        {
            this(null);
        }

        public actionCustomerOff(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionCustomerOff.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionCustomerOff.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionCustomerOff.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRichExamedManagerUI.this, "actionCustomerOff", "actionCustomerOff_actionPerformed", e);
        }
    }

    /**
     * output actionSyncustomer class
     */     
    protected class actionSyncustomer extends ItemAction {     
    
        public actionSyncustomer()
        {
            this(null);
        }

        public actionSyncustomer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionSyncustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSyncustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSyncustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRichExamedManagerUI.this, "actionSyncustomer", "actionSyncustomer_actionPerformed", e);
        }
    }

    /**
     * output actionViewLog class
     */     
    protected class actionViewLog extends ItemAction {     
    
        public actionViewLog()
        {
            this(null);
        }

        public actionViewLog(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionViewLog.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionViewLog.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionViewLog.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRichExamedManagerUI.this, "actionViewLog", "actionViewLog_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.custom.richinf.client", "RichExamedManagerUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.custom.richinf.client.RichExamedEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.richinf.RichExamedFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.custom.richinf.RichExamedInfo objectValue = new com.kingdee.eas.custom.richinf.RichExamedInfo();		
        return objectValue;
    }




}