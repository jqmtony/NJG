/**
 * output package name
 */
package com.kingdee.eas.fi.ar.client;

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
public abstract class AbstractOtherBillListUI extends com.kingdee.eas.fi.arap.client.ArApBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractOtherBillListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchSubmit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCreditorTrans;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReverse;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditVouchers;
    protected javax.swing.JToolBar.Separator separator5;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnScmVerifyQuery;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBlankOut;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemMultiPrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemMultiPrintPreview;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuImport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuImportHead;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuImportEntry;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuImportPlan;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBatchSubmit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemScmVerifyQuery;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuSetDataFormat;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChangeDataFormat;
    protected com.kingdee.bos.ctrl.swing.KDCheckBoxMenuItem menuItemSetZeroNotDisplay;
    protected com.kingdee.bos.ctrl.swing.KDCheckBoxMenuItem boxIsShowSumRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemEditVouchers;
    protected javax.swing.JPopupMenu.Separator separator6;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCreditorTrans;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReverse;
    protected ActionCreditorTrans actionCreditorTrans = null;
    protected ActionReverse actionReverse = null;
    protected ActionImportHead actionImportHead = null;
    protected ActionImportEntry actionImportEntry = null;
    protected ActionImportPlan actionImportPlan = null;
    protected ActionViewWriteOffRecord actionViewWriteOffRecord = null;
    protected ActionBlankOut actionBlankOut = null;
    protected ActionEditVouchers actionEditVouchers = null;
    protected ActionChangeDataFormat actionChangeDataFormat = null;
    protected ActionSetZeroNotDisplay actionSetZeroNotDisplay = null;
    protected ActionMultiPrint actionMultiPrint = null;
    protected ActionMultiPrintPreview actionMultiPrintPreview = null;
    protected ActionBatchSubmit actionBatchSubmit = null;
    protected ActionIsShowSumRow actionIsShowSumRow = null;
    public final static String STATUS_VIEW = "VIEW";
    public final static String STATUS_INITBILL = "INITBILL";
    public final static String STATUS_INITBILLLISTUI = "INITBILLLISTUI";
    /**
     * output class constructor
     */
    public AbstractOtherBillListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractOtherBillListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fi.ar.app", "OtherBill");
        //actionAddNew
        String _tempStr = null;
        actionAddNew.setEnabled(true);
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
        actionEdit.setEnabled(true);
        actionEdit.setDaemonRun(false);

        actionEdit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        _tempStr = resHelper.getString("ActionEdit.SHORT_DESCRIPTION");
        actionEdit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.LONG_DESCRIPTION");
        actionEdit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionEdit.NAME");
        actionEdit.putValue(ItemAction.NAME, _tempStr);
        this.actionEdit.setExtendProperty("isObjectUpdateLock", "true");
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionRemove
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl d"));
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
        //actionCopyTo
        actionCopyTo.setEnabled(true);
        actionCopyTo.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionCopyTo.SHORT_DESCRIPTION");
        actionCopyTo.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCopyTo.LONG_DESCRIPTION");
        actionCopyTo.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCopyTo.NAME");
        actionCopyTo.putValue(ItemAction.NAME, _tempStr);
         this.actionCopyTo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionCopyTo.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionCopyTo.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionVoucher
        actionVoucher.setEnabled(true);
        actionVoucher.setDaemonRun(false);

        actionVoucher.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt V"));
        _tempStr = resHelper.getString("ActionVoucher.SHORT_DESCRIPTION");
        actionVoucher.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionVoucher.LONG_DESCRIPTION");
        actionVoucher.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionVoucher.NAME");
        actionVoucher.putValue(ItemAction.NAME, _tempStr);
        this.actionVoucher.setBindWorkFlow(true);
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionDelVoucher
        actionDelVoucher.setEnabled(true);
        actionDelVoucher.setDaemonRun(false);

        actionDelVoucher.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt K"));
        _tempStr = resHelper.getString("ActionDelVoucher.SHORT_DESCRIPTION");
        actionDelVoucher.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionDelVoucher.LONG_DESCRIPTION");
        actionDelVoucher.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionDelVoucher.NAME");
        actionDelVoucher.putValue(ItemAction.NAME, _tempStr);
         this.actionDelVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionDelVoucher.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionDelVoucher.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionCreditorTrans
        this.actionCreditorTrans = new ActionCreditorTrans(this);
        getActionManager().registerAction("actionCreditorTrans", actionCreditorTrans);
         this.actionCreditorTrans.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionCreditorTrans.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
        //actionReverse
        this.actionReverse = new ActionReverse(this);
        getActionManager().registerAction("actionReverse", actionReverse);
         this.actionReverse.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionReverse.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
        //actionImportHead
        this.actionImportHead = new ActionImportHead(this);
        getActionManager().registerAction("actionImportHead", actionImportHead);
         this.actionImportHead.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportEntry
        this.actionImportEntry = new ActionImportEntry(this);
        getActionManager().registerAction("actionImportEntry", actionImportEntry);
         this.actionImportEntry.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportPlan
        this.actionImportPlan = new ActionImportPlan(this);
        getActionManager().registerAction("actionImportPlan", actionImportPlan);
         this.actionImportPlan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewWriteOffRecord
        this.actionViewWriteOffRecord = new ActionViewWriteOffRecord(this);
        getActionManager().registerAction("actionViewWriteOffRecord", actionViewWriteOffRecord);
         this.actionViewWriteOffRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBlankOut
        this.actionBlankOut = new ActionBlankOut(this);
        getActionManager().registerAction("actionBlankOut", actionBlankOut);
         this.actionBlankOut.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditVouchers
        this.actionEditVouchers = new ActionEditVouchers(this);
        getActionManager().registerAction("actionEditVouchers", actionEditVouchers);
         this.actionEditVouchers.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChangeDataFormat
        this.actionChangeDataFormat = new ActionChangeDataFormat(this);
        getActionManager().registerAction("actionChangeDataFormat", actionChangeDataFormat);
         this.actionChangeDataFormat.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSetZeroNotDisplay
        this.actionSetZeroNotDisplay = new ActionSetZeroNotDisplay(this);
        getActionManager().registerAction("actionSetZeroNotDisplay", actionSetZeroNotDisplay);
         this.actionSetZeroNotDisplay.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMultiPrint
        this.actionMultiPrint = new ActionMultiPrint(this);
        getActionManager().registerAction("actionMultiPrint", actionMultiPrint);
         this.actionMultiPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMultiPrintPreview
        this.actionMultiPrintPreview = new ActionMultiPrintPreview(this);
        getActionManager().registerAction("actionMultiPrintPreview", actionMultiPrintPreview);
         this.actionMultiPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatchSubmit
        this.actionBatchSubmit = new ActionBatchSubmit(this);
        getActionManager().registerAction("actionBatchSubmit", actionBatchSubmit);
         this.actionBatchSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionIsShowSumRow
        this.actionIsShowSumRow = new ActionIsShowSumRow(this);
        getActionManager().registerAction("actionIsShowSumRow", actionIsShowSumRow);
         this.actionIsShowSumRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnBatchSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCreditorTrans = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReverse = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEditVouchers = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator5 = new javax.swing.JToolBar.Separator();
        this.btnScmVerifyQuery = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBlankOut = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemMultiPrint = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemMultiPrintPreview = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuImport = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuImportHead = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuImportEntry = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuImportPlan = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBatchSubmit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemScmVerifyQuery = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuSetDataFormat = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemChangeDataFormat = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSetZeroNotDisplay = new com.kingdee.bos.ctrl.swing.KDCheckBoxMenuItem();
        this.boxIsShowSumRow = new com.kingdee.bos.ctrl.swing.KDCheckBoxMenuItem();
        this.menuItemEditVouchers = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.separator6 = new javax.swing.JPopupMenu.Separator();
        this.menuItemCreditorTrans = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemReverse = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnBatchSubmit.setName("btnBatchSubmit");
        this.btnCreditorTrans.setName("btnCreditorTrans");
        this.btnReverse.setName("btnReverse");
        this.btnEditVouchers.setName("btnEditVouchers");
        this.separator5.setName("separator5");
        this.btnScmVerifyQuery.setName("btnScmVerifyQuery");
        this.btnBlankOut.setName("btnBlankOut");
        this.menuItemMultiPrint.setName("menuItemMultiPrint");
        this.menuItemMultiPrintPreview.setName("menuItemMultiPrintPreview");
        this.menuImport.setName("menuImport");
        this.menuImportHead.setName("menuImportHead");
        this.menuImportEntry.setName("menuImportEntry");
        this.menuImportPlan.setName("menuImportPlan");
        this.menuItemBatchSubmit.setName("menuItemBatchSubmit");
        this.menuItemScmVerifyQuery.setName("menuItemScmVerifyQuery");
        this.menuSetDataFormat.setName("menuSetDataFormat");
        this.menuItemChangeDataFormat.setName("menuItemChangeDataFormat");
        this.menuItemSetZeroNotDisplay.setName("menuItemSetZeroNotDisplay");
        this.boxIsShowSumRow.setName("boxIsShowSumRow");
        this.menuItemEditVouchers.setName("menuItemEditVouchers");
        this.separator6.setName("separator6");
        this.menuItemCreditorTrans.setName("menuItemCreditorTrans");
        this.menuItemReverse.setName("menuItemReverse");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,639));		
        this.setMinimumSize(new Dimension(1013,629));
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol20\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol24\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol25\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol26\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol27\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol28\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol29\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol34\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol35\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol36\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol41\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol44\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol45\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol47\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol48\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol49\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol50\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol51\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol52\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol53\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol54\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol55\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol56\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol57\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol58\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol59\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol60\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol61\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol62\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol63\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol64\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol65\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol66\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol68\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol69\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol70\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol73\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol75\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol76\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol82\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol83\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol84\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol85\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol87\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol88\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol89\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol90\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol91\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol101\"><c:NumberFormat>%r{yyyy-M-d}t</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"company.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"company.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"billStatus\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"billDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"bizDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"billType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"bizType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"sourceBillType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"asstActType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"asstActNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"asstActName\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"currency\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"exchangeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"adminOrg\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"costCenter\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"saleOrg\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"saleGroup\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"person\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"paymentType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"payCondition\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"cashDiscount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"abstractName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"totalRecPayAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"totalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"totalTaxAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"totalVerifyAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"totalBadAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"totalUnVerifyAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"entry.seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"coreBillType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" /><t:Column t:key=\"coreBillNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" /><t:Column t:key=\"coreBillEntrySeq\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" /><t:Column t:key=\"project\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" t:styleID=\"sCol34\" /><t:Column t:key=\"trackNumberzc\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" t:styleID=\"sCol35\" /><t:Column t:key=\"lot\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" t:styleID=\"sCol36\" /><t:Column t:key=\"contractNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" /><t:Column t:key=\"contractEntrySeq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" /><t:Column t:key=\"material.number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" /><t:Column t:key=\"material.name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" /><t:Column t:key=\"pricePrecision\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"41\" t:styleID=\"sCol41\" /><t:Column t:key=\"specification\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"42\" /><t:Column t:key=\"assistProperty\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"43\" /><t:Column t:key=\"expenseItem.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"44\" t:styleID=\"sCol44\" /><t:Column t:key=\"expenseItem.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"45\" t:styleID=\"sCol45\" /><t:Column t:key=\"measureUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"46\" /><t:Column t:key=\"qty\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"47\" t:styleID=\"sCol47\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"48\" t:styleID=\"sCol48\" /><t:Column t:key=\"taxRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"49\" t:styleID=\"sCol49\" /><t:Column t:key=\"taxPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"50\" t:styleID=\"sCol50\" /><t:Column t:key=\"discountType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"51\" t:styleID=\"sCol51\" /><t:Column t:key=\"discountRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"52\" t:styleID=\"sCol52\" /><t:Column t:key=\"realPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"53\" t:styleID=\"sCol53\" /><t:Column t:key=\"actualPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"54\" t:styleID=\"sCol54\" /><t:Column t:key=\"recieveAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"55\" t:styleID=\"sCol55\" /><t:Column t:key=\"amount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"56\" t:styleID=\"sCol56\" /><t:Column t:key=\"taxAmount\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"57\" t:styleID=\"sCol57\" /><t:Column t:key=\"discountAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"58\" t:styleID=\"sCol58\" /><t:Column t:key=\"lockVerifyAmt\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"59\" t:styleID=\"sCol59\" /><t:Column t:key=\"lockUnVerifyAmt\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"60\" t:styleID=\"sCol60\" /><t:Column t:key=\"verifyAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"61\" t:styleID=\"sCol61\" /><t:Column t:key=\"badAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"62\" t:styleID=\"sCol62\" /><t:Column t:key=\"unVerifyAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"63\" t:styleID=\"sCol63\" /><t:Column t:key=\"preparedBadAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"64\" t:styleID=\"sCol64\" /><t:Column t:key=\"preparedBadAmountLoc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"65\" t:styleID=\"sCol65\" /><t:Column t:key=\"account\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"66\" t:styleID=\"sCol66\" /><t:Column t:key=\"oppAccount.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"67\" /><t:Column t:key=\"remark\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"68\" t:styleID=\"sCol68\" /><t:Column t:key=\"isNeedVoucher\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"69\" t:styleID=\"sCol69\" /><t:Column t:key=\"fivouchered\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"70\" t:styleID=\"sCol70\" /><t:Column t:key=\"voucherType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"71\" /><t:Column t:key=\"voucherNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"72\" /><t:Column t:key=\"voucherID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"73\" t:styleID=\"sCol73\" /><t:Column t:key=\"invoiceNumber\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"74\" /><t:Column t:key=\"invoiceAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"75\" t:styleID=\"sCol75\" /><t:Column t:key=\"isInitBill\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"76\" t:styleID=\"sCol76\" /><t:Column t:key=\"isImportBill\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"77\" /><t:Column t:key=\"isTransBill\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"78\" /><t:Column t:key=\"isAllowanceBill\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"79\" /><t:Column t:key=\"isReversed\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"80\" /><t:Column t:key=\"isReverseBill\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"81\" /><t:Column t:key=\"isBizBill\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"82\" t:styleID=\"sCol82\" /><t:Column t:key=\"entry.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"83\" t:styleID=\"sCol83\" /><t:Column t:key=\"currency.precision\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"84\" t:styleID=\"sCol84\" /><t:Column t:key=\"lastExchangeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"85\" t:styleID=\"sCol85\" /><t:Column t:key=\"isFullWriteOff\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"86\" /><t:Column t:key=\"localWrittenOffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"87\" t:styleID=\"sCol87\" /><t:Column t:key=\"writtenOffBaseQty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"88\" t:styleID=\"sCol88\" /><t:Column t:key=\"localUnwriteOffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"89\" t:styleID=\"sCol89\" /><t:Column t:key=\"unwriteOffBaseQty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"90\" t:styleID=\"sCol90\" /><t:Column t:key=\"sendOrgUnit.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"91\" t:styleID=\"sCol91\" /><t:Column t:key=\"ordCustNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"92\" /><t:Column t:key=\"ordCustName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"93\" /><t:Column t:key=\"serCustNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"94\" /><t:Column t:key=\"serCustName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"95\" /><t:Column t:key=\"recAsstActNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"96\" /><t:Column t:key=\"recAsstActName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"97\" /><t:Column t:key=\"creator\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"98\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"99\" /><t:Column t:key=\"updator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"100\" /><t:Column t:key=\"updateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"101\" t:styleID=\"sCol101\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"102\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"103\" /><t:Column t:key=\"accountant\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"104\" /><t:Column t:key=\"SYNbillType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"105\" /><t:Column t:key=\"SYNbillNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"106\" /><t:Column t:key=\"ldNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yhxAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{company.name}</t:Cell><t:Cell>$Resource{company.id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{billStatus}</t:Cell><t:Cell>$Resource{billDate}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{billType}</t:Cell><t:Cell>$Resource{bizType}</t:Cell><t:Cell>$Resource{sourceBillType}</t:Cell><t:Cell>$Resource{asstActType}</t:Cell><t:Cell>$Resource{asstActNumber}</t:Cell><t:Cell>$Resource{asstActName}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{exchangeRate}</t:Cell><t:Cell>$Resource{adminOrg}</t:Cell><t:Cell>$Resource{costCenter}</t:Cell><t:Cell>$Resource{saleOrg}</t:Cell><t:Cell>$Resource{saleGroup}</t:Cell><t:Cell>$Resource{person}</t:Cell><t:Cell>$Resource{paymentType}</t:Cell><t:Cell>$Resource{payCondition}</t:Cell><t:Cell>$Resource{cashDiscount}</t:Cell><t:Cell>$Resource{abstractName}</t:Cell><t:Cell>$Resource{totalRecPayAmount}</t:Cell><t:Cell>$Resource{totalAmount}</t:Cell><t:Cell>$Resource{totalTaxAmount}</t:Cell><t:Cell>$Resource{totalVerifyAmount}</t:Cell><t:Cell>$Resource{totalBadAmount}</t:Cell><t:Cell>$Resource{totalUnVerifyAmount}</t:Cell><t:Cell>$Resource{entry.seq}</t:Cell><t:Cell>$Resource{coreBillType}</t:Cell><t:Cell>$Resource{coreBillNumber}</t:Cell><t:Cell>$Resource{coreBillEntrySeq}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{trackNumberzc}</t:Cell><t:Cell>$Resource{lot}</t:Cell><t:Cell>$Resource{contractNum}</t:Cell><t:Cell>$Resource{contractEntrySeq}</t:Cell><t:Cell>$Resource{material.number}</t:Cell><t:Cell>$Resource{material.name}</t:Cell><t:Cell>$Resource{pricePrecision}</t:Cell><t:Cell>$Resource{specification}</t:Cell><t:Cell>$Resource{assistProperty}</t:Cell><t:Cell>$Resource{expenseItem.number}</t:Cell><t:Cell>$Resource{expenseItem.name}</t:Cell><t:Cell>$Resource{measureUnit}</t:Cell><t:Cell>$Resource{qty}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{taxRate}</t:Cell><t:Cell>$Resource{taxPrice}</t:Cell><t:Cell>$Resource{discountType}</t:Cell><t:Cell>$Resource{discountRate}</t:Cell><t:Cell>$Resource{realPrice}</t:Cell><t:Cell>$Resource{actualPrice}</t:Cell><t:Cell>$Resource{recieveAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{taxAmount}</t:Cell><t:Cell>$Resource{discountAmount}</t:Cell><t:Cell>$Resource{lockVerifyAmt}</t:Cell><t:Cell>$Resource{lockUnVerifyAmt}</t:Cell><t:Cell>$Resource{verifyAmount}</t:Cell><t:Cell>$Resource{badAmount}</t:Cell><t:Cell>$Resource{unVerifyAmount}</t:Cell><t:Cell>$Resource{preparedBadAmount}</t:Cell><t:Cell>$Resource{preparedBadAmountLoc}</t:Cell><t:Cell>$Resource{account}</t:Cell><t:Cell>$Resource{oppAccount.name}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{isNeedVoucher}</t:Cell><t:Cell>$Resource{fivouchered}</t:Cell><t:Cell>$Resource{voucherType}</t:Cell><t:Cell>$Resource{voucherNumber}</t:Cell><t:Cell>$Resource{voucherID}</t:Cell><t:Cell>$Resource{invoiceNumber}</t:Cell><t:Cell>$Resource{invoiceAmt}</t:Cell><t:Cell>$Resource{isInitBill}</t:Cell><t:Cell>$Resource{isImportBill}</t:Cell><t:Cell>$Resource{isTransBill}</t:Cell><t:Cell>$Resource{isAllowanceBill}</t:Cell><t:Cell>$Resource{isReversed}</t:Cell><t:Cell>$Resource{isReverseBill}</t:Cell><t:Cell>$Resource{isBizBill}</t:Cell><t:Cell>$Resource{entry.id}</t:Cell><t:Cell>$Resource{currency.precision}</t:Cell><t:Cell>$Resource{lastExchangeRate}</t:Cell><t:Cell>$Resource{isFullWriteOff}</t:Cell><t:Cell>$Resource{localWrittenOffAmount}</t:Cell><t:Cell>$Resource{writtenOffBaseQty}</t:Cell><t:Cell>$Resource{localUnwriteOffAmount}</t:Cell><t:Cell>$Resource{unwriteOffBaseQty}</t:Cell><t:Cell>$Resource{sendOrgUnit.name}</t:Cell><t:Cell>$Resource{ordCustNumber}</t:Cell><t:Cell>$Resource{ordCustName}</t:Cell><t:Cell>$Resource{serCustNumber}</t:Cell><t:Cell>$Resource{serCustName}</t:Cell><t:Cell>$Resource{recAsstActNumber}</t:Cell><t:Cell>$Resource{recAsstActName}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{updator}</t:Cell><t:Cell>$Resource{updateTime}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell><t:Cell>$Resource{accountant}</t:Cell><t:Cell>$Resource{SYNbillType}</t:Cell><t:Cell>$Resource{SYNbillNumber}</t:Cell><t:Cell>$Resource{ldNo}</t:Cell><t:Cell>$Resource{yhxAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","company.name","company.id","number","billStatus","billDate","bizDate","billType","bizType.name","sourceBillType","asstActType.name","asstActNumber","asstActName","currency.name","exchangeRate","adminOrgUnit.name","costCenter.name","saleOrg.name","saleGroup.name","person.name","paymentType.name","payCondition.name","cashDiscount.description","abstractName","amount","totalAmount","totalTax","verifyAmount","totalBadAmount","unVerifyAmount","entry.seq","coreBillType.name","entry.coreBillNumber","entry.coreBillEntrySeq","project.number","trackNumberzc.number","entry.lot","entry.contractNum","entry.contractEntrySeq","material.number","material.name","material.pricePrecision","material.model","assistProperty.name","expenseItem.number","expenseItem.name","measureUnit.name","entry.quantity","entry.price","entry.taxRate","entry.taxPrice","entry.discountType","entry.discountRate","entry.realPrice","entry.actualPrice","entry.recievePayAmount","entry.amount","entry.taxAmount","entry.discountAmount","entry.lockVerifyAmt","entry.lockUnVerifyAmt","entry.verifyAmount","entry.badAmount","entry.unVerifyAmount","entry.preparedBadAmount","entry.preparedBadAmountLocal","account.name","oppAccount.name","entry.remark","isNeedVoucher","fiVouchered","voucherType.name","voucherNumber","voucher.id","entry.invoiceNumber","entry.invoicedAmt","isInitializeBill","isImportBill","isTransBill","isAllowanceBill","isReversed","isReverseBill","isBizBill","entry.id","currency.precision","lastExhangeRate","entry.isFullWriteOff","entry.localWrittenOffAmount","entry.wittenOffBaseQty","entry.localUnwriteOffAmount","entry.unwriteOffBaseQty","recSendOrgUnit.name","entry.ordCustNumber","entry.ordCustName","entry.serCustNumber","entry.serCustName","entry.recAsstActNumber","entry.recAsstActName","creator.name","createTime","lastUpdateUser.name","lastUpdateTime","auditor.name","auditDate","accountant.name","SYNbillType","SYNbillNumber","ldNo","yhxAmount"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);		
        this.menuItemImportData.setVisible(true);		
        this.separatorFW3.setVisible(false);		
        this.btnAuditResult.setEnabled(false);		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setMnemonic(80);		
        this.menuItemAntiAudit.setText(resHelper.getString("menuItemAntiAudit.text"));		
        this.menuItemAntiAudit.setMnemonic(85);		
        this.menuItemPointSubject.setText(resHelper.getString("menuItemPointSubject.text"));		
        this.menuItemPointSubject.setToolTipText(resHelper.getString("menuItemPointSubject.toolTipText"));		
        this.menuItemPointVoucher.setText(resHelper.getString("menuItemPointVoucher.text"));		
        this.menuItemPointVoucher.setToolTipText(resHelper.getString("menuItemPointVoucher.toolTipText"));		
        this.menuItemSplit.setText(resHelper.getString("menuItemSplit.text"));
        // btnBatchSubmit
        this.btnBatchSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchSubmit.setText(resHelper.getString("btnBatchSubmit.text"));		
        this.btnBatchSubmit.setToolTipText(resHelper.getString("btnBatchSubmit.toolTipText"));
        // btnCreditorTrans
        this.btnCreditorTrans.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreditorTrans, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCreditorTrans.setText(resHelper.getString("btnCreditorTrans.text"));		
        this.btnCreditorTrans.setToolTipText(resHelper.getString("btnCreditorTrans.toolTipText"));
        // btnReverse
        this.btnReverse.setAction((IItemAction)ActionProxyFactory.getProxy(actionReverse, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReverse.setText(resHelper.getString("btnReverse.text"));		
        this.btnReverse.setToolTipText(resHelper.getString("btnReverse.toolTipText"));
        // btnEditVouchers
        this.btnEditVouchers.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditVouchers, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditVouchers.setText(resHelper.getString("btnEditVouchers.text"));		
        this.btnEditVouchers.setToolTipText(resHelper.getString("btnEditVouchers.toolTipText"));
        // separator5		
        this.separator5.setOrientation(1);
        // btnScmVerifyQuery
        this.btnScmVerifyQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewWriteOffRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnScmVerifyQuery.setText(resHelper.getString("btnScmVerifyQuery.text"));
        // btnBlankOut
        this.btnBlankOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionBlankOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBlankOut.setText(resHelper.getString("btnBlankOut.text"));		
        this.btnBlankOut.setVisible(false);
        // menuItemMultiPrint
        this.menuItemMultiPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionMultiPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemMultiPrint.setText(resHelper.getString("menuItemMultiPrint.text"));
        // menuItemMultiPrintPreview
        this.menuItemMultiPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionMultiPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemMultiPrintPreview.setText(resHelper.getString("menuItemMultiPrintPreview.text"));
        // menuImport		
        this.menuImport.setText(resHelper.getString("menuImport.text"));		
        this.menuImport.setVisible(false);
        // menuImportHead
        this.menuImportHead.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportHead, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuImportHead.setText(resHelper.getString("menuImportHead.text"));
        // menuImportEntry
        this.menuImportEntry.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportEntry, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuImportEntry.setText(resHelper.getString("menuImportEntry.text"));
        // menuImportPlan
        this.menuImportPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuImportPlan.setText(resHelper.getString("menuImportPlan.text"));
        // menuItemBatchSubmit
        this.menuItemBatchSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBatchSubmit.setText(resHelper.getString("menuItemBatchSubmit.text"));		
        this.menuItemBatchSubmit.setToolTipText(resHelper.getString("menuItemBatchSubmit.toolTipText"));
        // menuItemScmVerifyQuery
        this.menuItemScmVerifyQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewWriteOffRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemScmVerifyQuery.setText(resHelper.getString("menuItemScmVerifyQuery.text"));		
        this.menuItemScmVerifyQuery.setToolTipText(resHelper.getString("menuItemScmVerifyQuery.toolTipText"));
        // menuSetDataFormat		
        this.menuSetDataFormat.setText(resHelper.getString("menuSetDataFormat.text"));
        // menuItemChangeDataFormat
        this.menuItemChangeDataFormat.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeDataFormat, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemChangeDataFormat.setText(resHelper.getString("menuItemChangeDataFormat.text"));
        // menuItemSetZeroNotDisplay
        this.menuItemSetZeroNotDisplay.setAction((IItemAction)ActionProxyFactory.getProxy(actionSetZeroNotDisplay, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSetZeroNotDisplay.setText(resHelper.getString("menuItemSetZeroNotDisplay.text"));
        // boxIsShowSumRow
        this.boxIsShowSumRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionIsShowSumRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.boxIsShowSumRow.setText(resHelper.getString("boxIsShowSumRow.text"));		
        this.boxIsShowSumRow.setEnabled(false);		
        this.boxIsShowSumRow.setVisible(false);
        // menuItemEditVouchers
        this.menuItemEditVouchers.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditVouchers, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemEditVouchers.setText(resHelper.getString("menuItemEditVouchers.text"));		
        this.menuItemEditVouchers.setMnemonic(77);
        // separator6
        // menuItemCreditorTrans
        this.menuItemCreditorTrans.setAction((IItemAction)ActionProxyFactory.getProxy(actionCreditorTrans, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCreditorTrans.setText(resHelper.getString("menuItemCreditorTrans.text"));		
        this.menuItemCreditorTrans.setMnemonic(82);
        // menuItemReverse
        this.menuItemReverse.setAction((IItemAction)ActionProxyFactory.getProxy(actionReverse, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemReverse.setText(resHelper.getString("menuItemReverse.text"));		
        this.menuItemReverse.setMnemonic(87);
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
        tblMain.setBounds(new Rectangle(10, 10, 996, 610));
        this.add(tblMain, new KDLayout.Constraints(10, 10, 996, 610, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

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
        menuFile.add(menuItemCloudScreen);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemExportData);
        menuFile.add(MenuItemAttachment);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(menuItemMultiPrint);
        menuFile.add(menuItemMultiPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        menuFile.add(menuImport);
        //menuImport
        menuImport.add(menuImportHead);
        menuImport.add(menuImportEntry);
        menuImport.add(menuImportPlan);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(menuItemBatchSubmit);
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
        menuView.add(separatorView1);
        menuView.add(menuItemSwitchView);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(menuItemVerificate);
        menuView.add(menuItemQueryScheme);
        menuView.add(kDSeparator6);
        menuView.add(menuItemScmVerifyQuery);
        menuView.add(menuSetDataFormat);
        //menuSetDataFormat
        menuSetDataFormat.add(menuItemChangeDataFormat);
        menuSetDataFormat.add(menuItemSetZeroNotDisplay);
        menuSetDataFormat.add(boxIsShowSumRow);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemAntiAudit);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemEditVouchers);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemPointVoucher);
        menuBiz.add(menuItemCancelPointVoucher);
        menuBiz.add(menuItemPointSubject);
        menuBiz.add(menuItemCancelPointSubject);
        menuBiz.add(separator6);
        menuBiz.add(menuItemCreditorTrans);
        menuBiz.add(menuItemAllowance);
        menuBiz.add(menuItemReverse);
        menuBiz.add(menuItemSplit);
        menuBiz.add(menuItemBuildDebitAdjust);
        menuBiz.add(menuItemReversedBizBill);
        menuBiz.add(menuItemUnReversedBizBill);
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
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnBatchSubmit);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnAntiAudit);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnVerificate);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreditorTrans);
        this.toolBar.add(btnAllowance);
        this.toolBar.add(btnReverse);
        this.toolBar.add(btnSplit);
        this.toolBar.add(btnBuildDebitAdjust);
        this.toolBar.add(btnReversedBizBill);
        this.toolBar.add(btnUnReversedBizBill);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnEditVouchers);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(separator5);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnScmVerifyQuery);
        this.toolBar.add(btnBlankOut);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_VIEW, this.btnEdit, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_VIEW, this.btnAddNew, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_VIEW, this.btnRemove, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_INITBILL, this.btnEdit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_INITBILL, this.btnRemove, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fi.ar.app.OtherBillListUIHandler";
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
	protected void Remove() throws Exception {
    	IObjectValue editData = getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
    	super.Remove();
    	recycleNumberByOrg(editData,"",editData.getString("number"));
    }
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Company");
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
        if (STATUS_VIEW.equals(this.oprtState)) {
		            this.btnEdit.setEnabled(true);
		            this.btnAddNew.setEnabled(true);
		            this.btnRemove.setEnabled(true);
        } else if (STATUS_INITBILL.equals(this.oprtState)) {
		            this.btnEdit.setVisible(true);
		            this.btnEdit.setEnabled(false);
		            this.btnRemove.setVisible(true);
		            this.btnRemove.setEnabled(false);
        } else if (STATUS_INITBILLLISTUI.equals(this.oprtState)) {
		            this.btnVoucher.setVisible(false);
		            this.btnDelVoucher.setVisible(false);
		            this.btnCreateTo.setVisible(false);
		            this.btnTraceDown.setVisible(false);
		            this.btnWFViewdoProccess.setVisible(false);
		            this.btnCreditorTrans.setVisible(false);
		            this.btnReverse.setVisible(false);
		            this.btnTraceUp.setVisible(false);
		            this.btnAuditResult.setVisible(false);
        }
    }

	public SelectorItemCollection getBOTPSelectors() {
			SelectorItemCollection sic = new SelectorItemCollection();
			return sic;
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("company.name"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("currency.name"));
        sic.add(new SelectorItemInfo("saleOrg.name"));
        sic.add(new SelectorItemInfo("abstractName"));
        sic.add(new SelectorItemInfo("asstActType.name"));
        sic.add(new SelectorItemInfo("billStatus"));
        sic.add(new SelectorItemInfo("asstActName"));
        sic.add(new SelectorItemInfo("fiVouchered"));
        sic.add(new SelectorItemInfo("entry.recievePayAmount"));
        sic.add(new SelectorItemInfo("isReverseBill"));
        sic.add(new SelectorItemInfo("isReversed"));
        sic.add(new SelectorItemInfo("material.number"));
        sic.add(new SelectorItemInfo("expenseItem.number"));
        sic.add(new SelectorItemInfo("material.name"));
        sic.add(new SelectorItemInfo("expenseItem.name"));
        sic.add(new SelectorItemInfo("totalAmount"));
        sic.add(new SelectorItemInfo("verifyAmount"));
        sic.add(new SelectorItemInfo("totalBadAmount"));
        sic.add(new SelectorItemInfo("unVerifyAmount"));
        sic.add(new SelectorItemInfo("entry.verifyAmount"));
        sic.add(new SelectorItemInfo("entry.badAmount"));
        sic.add(new SelectorItemInfo("entry.unVerifyAmount"));
        sic.add(new SelectorItemInfo("adminOrgUnit.name"));
        sic.add(new SelectorItemInfo("entry.id"));
        sic.add(new SelectorItemInfo("billDate"));
        sic.add(new SelectorItemInfo("currency.precision"));
        sic.add(new SelectorItemInfo("material.model"));
        sic.add(new SelectorItemInfo("isImportBill"));
        sic.add(new SelectorItemInfo("entry.coreBillNumber"));
        sic.add(new SelectorItemInfo("entry.coreBillEntrySeq"));
        sic.add(new SelectorItemInfo("coreBillType.name"));
        sic.add(new SelectorItemInfo("voucherNumber"));
        sic.add(new SelectorItemInfo("entry.preparedBadAmount"));
        sic.add(new SelectorItemInfo("entry.lockVerifyAmt"));
        sic.add(new SelectorItemInfo("entry.lockUnVerifyAmt"));
        sic.add(new SelectorItemInfo("person.name"));
        sic.add(new SelectorItemInfo("voucherType.name"));
        sic.add(new SelectorItemInfo("lastExhangeRate"));
        sic.add(new SelectorItemInfo("sourceBillType"));
        sic.add(new SelectorItemInfo("voucher.id"));
        sic.add(new SelectorItemInfo("entry.localWrittenOffAmount"));
        sic.add(new SelectorItemInfo("entry.wittenOffBaseQty"));
        sic.add(new SelectorItemInfo("entry.localUnwriteOffAmount"));
        sic.add(new SelectorItemInfo("entry.unwriteOffBaseQty"));
        sic.add(new SelectorItemInfo("entry.preparedBadAmountLocal"));
        sic.add(new SelectorItemInfo("entry.taxAmount"));
        sic.add(new SelectorItemInfo("entry.invoiceNumber"));
        sic.add(new SelectorItemInfo("totalTax"));
        sic.add(new SelectorItemInfo("asstActNumber"));
        sic.add(new SelectorItemInfo("saleGroup.name"));
        sic.add(new SelectorItemInfo("paymentType.name"));
        sic.add(new SelectorItemInfo("cashDiscount.description"));
        sic.add(new SelectorItemInfo("assistProperty.name"));
        sic.add(new SelectorItemInfo("measureUnit.name"));
        sic.add(new SelectorItemInfo("entry.quantity"));
        sic.add(new SelectorItemInfo("entry.price"));
        sic.add(new SelectorItemInfo("entry.taxRate"));
        sic.add(new SelectorItemInfo("entry.taxPrice"));
        sic.add(new SelectorItemInfo("entry.discountType"));
        sic.add(new SelectorItemInfo("entry.discountRate"));
        sic.add(new SelectorItemInfo("entry.realPrice"));
        sic.add(new SelectorItemInfo("entry.actualPrice"));
        sic.add(new SelectorItemInfo("entry.amount"));
        sic.add(new SelectorItemInfo("entry.discountAmount"));
        sic.add(new SelectorItemInfo("entry.remark"));
        sic.add(new SelectorItemInfo("entry.invoicedAmt"));
        sic.add(new SelectorItemInfo("isInitializeBill"));
        sic.add(new SelectorItemInfo("isTransBill"));
        sic.add(new SelectorItemInfo("redBlueType"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("accountant.name"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("billType"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("material.pricePrecision"));
        sic.add(new SelectorItemInfo("exchangeRate"));
        sic.add(new SelectorItemInfo("isNeedVoucher"));
        sic.add(new SelectorItemInfo("bizType.name"));
        sic.add(new SelectorItemInfo("oppAccount.name"));
        sic.add(new SelectorItemInfo("account.name"));
        sic.add(new SelectorItemInfo("isAllowanceBill"));
        sic.add(new SelectorItemInfo("payCondition.name"));
        sic.add(new SelectorItemInfo("entry.contractNum"));
        sic.add(new SelectorItemInfo("entry.contractEntrySeq"));
        sic.add(new SelectorItemInfo("entry.ordCustNumber"));
        sic.add(new SelectorItemInfo("entry.ordCustName"));
        sic.add(new SelectorItemInfo("entry.serCustNumber"));
        sic.add(new SelectorItemInfo("entry.serCustName"));
        sic.add(new SelectorItemInfo("entry.recAsstActNumber"));
        sic.add(new SelectorItemInfo("entry.recAsstActName"));
        sic.add(new SelectorItemInfo("recSendOrgUnit.name"));
        sic.add(new SelectorItemInfo("recSendOrgUnit.id"));
        sic.add(new SelectorItemInfo("project.number"));
        sic.add(new SelectorItemInfo("trackNumberzc.number"));
        sic.add(new SelectorItemInfo("isBizBill"));
        sic.add(new SelectorItemInfo("lastUpdateUser.name"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("company.id"));
        sic.add(new SelectorItemInfo("entry.seq"));
        sic.add(new SelectorItemInfo("SYNbillType"));
        sic.add(new SelectorItemInfo("SYNbillNumber"));
        sic.add(new SelectorItemInfo("entry.lot"));
        sic.add(new SelectorItemInfo("costCenter.name"));
        sic.add(new SelectorItemInfo("entry.isFullWriteOff"));
        sic.add(new SelectorItemInfo("ldNo"));
        sic.add(new SelectorItemInfo("yhxAmount"));
        return sic;
    }            protected java.util.List getQuerySorterFields() 
    { 
        java.util.List sorterFieldList = new ArrayList(); 
        sorterFieldList.add("number"); 
        sorterFieldList.add("entry.seq"); 
        sorterFieldList.add("entry.coreBillEntrySeq"); 
        return sorterFieldList; 
    } 
    protected java.util.List getQueryPKFields() 
    { 
        java.util.List pkList = new ArrayList(); 
        pkList.add("id"); 
        pkList.add("entry.id"); 
        return pkList;
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
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionCopyTo_actionPerformed method
     */
    public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyTo_actionPerformed(e);
    }
    	

    /**
     * output actionVoucher_actionPerformed method
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }
    	

    /**
     * output actionDelVoucher_actionPerformed method
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }
    	

    /**
     * output actionCreditorTrans_actionPerformed method
     */
    public void actionCreditorTrans_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReverse_actionPerformed method
     */
    public void actionReverse_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportHead_actionPerformed method
     */
    public void actionImportHead_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportEntry_actionPerformed method
     */
    public void actionImportEntry_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportPlan_actionPerformed method
     */
    public void actionImportPlan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewWriteOffRecord_actionPerformed method
     */
    public void actionViewWriteOffRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBlankOut_actionPerformed method
     */
    public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditVouchers_actionPerformed method
     */
    public void actionEditVouchers_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChangeDataFormat_actionPerformed method
     */
    public void actionChangeDataFormat_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSetZeroNotDisplay_actionPerformed method
     */
    public void actionSetZeroNotDisplay_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMultiPrint_actionPerformed method
     */
    public void actionMultiPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMultiPrintPreview_actionPerformed method
     */
    public void actionMultiPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatchSubmit_actionPerformed method
     */
    public void actionBatchSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionIsShowSumRow_actionPerformed method
     */
    public void actionIsShowSumRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAddNew(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNew() {
    	return false;
    }
	public RequestContext prepareActionEdit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionEdit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEdit() {
    	return false;
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
	public RequestContext prepareActionCopyTo(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCopyTo(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCopyTo() {
    	return false;
    }
	public RequestContext prepareActionVoucher(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionVoucher(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionVoucher() {
    	return false;
    }
	public RequestContext prepareActionDelVoucher(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionDelVoucher(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelVoucher() {
    	return false;
    }
	public RequestContext prepareActionCreditorTrans(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCreditorTrans() {
    	return false;
    }
	public RequestContext prepareActionReverse(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReverse() {
    	return false;
    }
	public RequestContext prepareActionImportHead(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportHead() {
    	return false;
    }
	public RequestContext prepareActionImportEntry(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportEntry() {
    	return false;
    }
	public RequestContext prepareActionImportPlan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportPlan() {
    	return false;
    }
	public RequestContext prepareActionViewWriteOffRecord(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewWriteOffRecord() {
    	return false;
    }
	public RequestContext prepareActionBlankOut(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBlankOut() {
    	return false;
    }
	public RequestContext prepareActionEditVouchers(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditVouchers() {
    	return false;
    }
	public RequestContext prepareActionChangeDataFormat(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChangeDataFormat() {
    	return false;
    }
	public RequestContext prepareActionSetZeroNotDisplay(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSetZeroNotDisplay() {
    	return false;
    }
	public RequestContext prepareActionMultiPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMultiPrint() {
    	return false;
    }
	public RequestContext prepareActionMultiPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMultiPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionBatchSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchSubmit() {
    	return false;
    }
	public RequestContext prepareActionIsShowSumRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionIsShowSumRow() {
    	return false;
    }

    /**
     * output ActionCreditorTrans class
     */     
    protected class ActionCreditorTrans extends ItemAction {     
    
        public ActionCreditorTrans()
        {
            this(null);
        }

        public ActionCreditorTrans(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt shift T"));
            _tempStr = resHelper.getString("ActionCreditorTrans.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreditorTrans.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCreditorTrans.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionCreditorTrans", "actionCreditorTrans_actionPerformed", e);
        }
    }

    /**
     * output ActionReverse class
     */     
    protected class ActionReverse extends ItemAction {     
    
        public ActionReverse()
        {
            this(null);
        }

        public ActionReverse(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt shift R"));
            _tempStr = resHelper.getString("ActionReverse.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReverse.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReverse.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionReverse", "actionReverse_actionPerformed", e);
        }
    }

    /**
     * output ActionImportHead class
     */     
    protected class ActionImportHead extends ItemAction {     
    
        public ActionImportHead()
        {
            this(null);
        }

        public ActionImportHead(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportHead.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportHead.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportHead.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionImportHead", "actionImportHead_actionPerformed", e);
        }
    }

    /**
     * output ActionImportEntry class
     */     
    protected class ActionImportEntry extends ItemAction {     
    
        public ActionImportEntry()
        {
            this(null);
        }

        public ActionImportEntry(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportEntry.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportEntry.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportEntry.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionImportEntry", "actionImportEntry_actionPerformed", e);
        }
    }

    /**
     * output ActionImportPlan class
     */     
    protected class ActionImportPlan extends ItemAction {     
    
        public ActionImportPlan()
        {
            this(null);
        }

        public ActionImportPlan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportPlan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportPlan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportPlan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionImportPlan", "actionImportPlan_actionPerformed", e);
        }
    }

    /**
     * output ActionViewWriteOffRecord class
     */     
    protected class ActionViewWriteOffRecord extends ItemAction {     
    
        public ActionViewWriteOffRecord()
        {
            this(null);
        }

        public ActionViewWriteOffRecord(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewWriteOffRecord.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewWriteOffRecord.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewWriteOffRecord.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionViewWriteOffRecord", "actionViewWriteOffRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionBlankOut class
     */     
    protected class ActionBlankOut extends ItemAction {     
    
        public ActionBlankOut()
        {
            this(null);
        }

        public ActionBlankOut(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBlankOut.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBlankOut.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBlankOut.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionBlankOut", "actionBlankOut_actionPerformed", e);
        }
    }

    /**
     * output ActionEditVouchers class
     */     
    protected class ActionEditVouchers extends ItemAction {     
    
        public ActionEditVouchers()
        {
            this(null);
        }

        public ActionEditVouchers(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift O"));
            _tempStr = resHelper.getString("ActionEditVouchers.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditVouchers.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditVouchers.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionEditVouchers", "actionEditVouchers_actionPerformed", e);
        }
    }

    /**
     * output ActionChangeDataFormat class
     */     
    protected class ActionChangeDataFormat extends ItemAction {     
    
        public ActionChangeDataFormat()
        {
            this(null);
        }

        public ActionChangeDataFormat(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionChangeDataFormat.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeDataFormat.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeDataFormat.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionChangeDataFormat", "actionChangeDataFormat_actionPerformed", e);
        }
    }

    /**
     * output ActionSetZeroNotDisplay class
     */     
    protected class ActionSetZeroNotDisplay extends ItemAction {     
    
        public ActionSetZeroNotDisplay()
        {
            this(null);
        }

        public ActionSetZeroNotDisplay(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSetZeroNotDisplay.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetZeroNotDisplay.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetZeroNotDisplay.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionSetZeroNotDisplay", "actionSetZeroNotDisplay_actionPerformed", e);
        }
    }

    /**
     * output ActionMultiPrint class
     */     
    protected class ActionMultiPrint extends ItemAction {     
    
        public ActionMultiPrint()
        {
            this(null);
        }

        public ActionMultiPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMultiPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMultiPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMultiPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionMultiPrint", "actionMultiPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionMultiPrintPreview class
     */     
    protected class ActionMultiPrintPreview extends ItemAction {     
    
        public ActionMultiPrintPreview()
        {
            this(null);
        }

        public ActionMultiPrintPreview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMultiPrintPreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMultiPrintPreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMultiPrintPreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionMultiPrintPreview", "actionMultiPrintPreview_actionPerformed", e);
        }
    }

    /**
     * output ActionBatchSubmit class
     */     
    protected class ActionBatchSubmit extends ItemAction {     
    
        public ActionBatchSubmit()
        {
            this(null);
        }

        public ActionBatchSubmit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBatchSubmit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchSubmit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchSubmit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionBatchSubmit", "actionBatchSubmit_actionPerformed", e);
        }
    }

    /**
     * output ActionIsShowSumRow class
     */     
    protected class ActionIsShowSumRow extends ItemAction {     
    
        public ActionIsShowSumRow()
        {
            this(null);
        }

        public ActionIsShowSumRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionIsShowSumRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIsShowSumRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIsShowSumRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillListUI.this, "ActionIsShowSumRow", "actionIsShowSumRow_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fi.ar.client", "OtherBillListUI");
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
        return com.kingdee.eas.fi.ar.client.OtherBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fi.ar.OtherBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fi.ar.OtherBillInfo objectValue = new com.kingdee.eas.fi.ar.OtherBillInfo();		
        return objectValue;
    }


        
				protected boolean isFootVisible() {
			return true;
		}


}