/**
 * output package name
 */
package com.kingdee.eas.port.equipment.base.client;

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
public abstract class AbstractImportFaCardUI extends com.kingdee.eas.framework.client.BillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractImportFaCardUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchAddNew;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchSubmit;
    protected javax.swing.JToolBar.Separator separator1;
    protected javax.swing.JToolBar.Separator separator2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAuditing;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancelAuditing;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChange;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClear;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplit;
    protected javax.swing.JToolBar.Separator separator3;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChangeInfo;
    protected javax.swing.JToolBar.Separator separator5;
    protected javax.swing.JToolBar.Separator separator4;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTraceUpCard;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTraceDownCard;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBatchAddNew;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuImport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImpCard;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImpFacility;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImpCostCenter;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImpDept;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImpEva;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImpDef;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuExport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpCard;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpFacility;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpCostCenter;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpDept;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpEva;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpDef;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuExportSelect;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpCardSelect;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpFacilitySelect;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpCostCenterSelect;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpDeptSelect;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpEvaSelect;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpDefSelect;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPrintCard;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPrintCardPre;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuImpBarCodeModel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuPrintBarCode;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuGennerBarCode;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBatchSubmit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menChangeInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menAuditing;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menCancelAuditing;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menChange;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menClear;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShowQueryAnalysiser;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemTransToBook;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemTraceUpCard;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemTraceDownCard;
    protected ActionChange actionChange = null;
    protected ActionClear actionClear = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionChangeInfo actionChangeInfo = null;
    protected ActionFilter actionFilter = null;
    protected ActionSplit actionSplit = null;
    protected ActionImpCard actionImpCard = null;
    protected ActionExpCard actionExpCard = null;
    protected ActionShowQueryAnalysiser actionShowQueryAnalysiser = null;
    protected ActionPrintCard actionPrintCard = null;
    protected ActionPrintCardPreview actionPrintCardPreview = null;
    protected ActionBatchSubmit actionBatchSubmit = null;
    protected ActionBatchAddNew actionBatchAddNew = null;
    protected ActionTransToBook actionTransToBook = null;
    protected ActionTraceUpCard actionTraceUpCard = null;
    protected ActionTraceDownCard actionTraceDownCard = null;
    protected ActionImpBarCodeModel actionImpBarCodeModel = null;
    protected ActionPrintBarCode actionPrintBarCode = null;
    protected ActionGennerBarCode actionGennerBarCode = null;
    /**
     * output class constructor
     */
    public AbstractImportFaCardUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractImportFaCardUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fi.fa.manage", "FaCurCardQuery");
        //actionCalculator
        String _tempStr = null;
        actionCalculator.setEnabled(true);
        actionCalculator.setDaemonRun(false);

        actionCalculator.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift F12"));
        _tempStr = resHelper.getString("ActionCalculator.SHORT_DESCRIPTION");
        actionCalculator.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCalculator.LONG_DESCRIPTION");
        actionCalculator.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCalculator.NAME");
        actionCalculator.putValue(ItemAction.NAME, _tempStr);
         this.actionCalculator.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddNew
        actionAddNew.setEnabled(true);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
        actionAddNew.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
        actionAddNew.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.NAME");
        actionAddNew.putValue(ItemAction.NAME, _tempStr);
        this.actionAddNew.setExtendProperty("Mutex", "FI_FA_Mutex_Lock,0");
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
        this.actionEdit.setExtendProperty("Mutex", "FI_FA_Mutex_Lock,0");
        this.actionEdit.setExtendProperty("isObjectUpdateLock", "true");
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionRemove
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
        this.actionRemove.setExtendProperty("Mutex", "FI_FA_Mutex_Lock,0");
        this.actionRemove.setExtendProperty("isObjectUpdateLock", "true");
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionQuery
        actionQuery.setEnabled(true);
        actionQuery.setDaemonRun(false);

        actionQuery.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl F"));
        _tempStr = resHelper.getString("ActionQuery.SHORT_DESCRIPTION");
        actionQuery.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionQuery.LONG_DESCRIPTION");
        actionQuery.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionQuery.NAME");
        actionQuery.putValue(ItemAction.NAME, _tempStr);
         this.actionQuery.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionQuery.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionQuery.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionImportData
        actionImportData.setEnabled(true);
        actionImportData.setDaemonRun(false);

        actionImportData.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
        _tempStr = resHelper.getString("ActionImportData.SHORT_DESCRIPTION");
        actionImportData.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionImportData.LONG_DESCRIPTION");
        actionImportData.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionImportData.NAME");
        actionImportData.putValue(ItemAction.NAME, _tempStr);
         this.actionImportData.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionImportData.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionImportData.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionExportData
        actionExportData.setEnabled(true);
        actionExportData.setDaemonRun(false);

        actionExportData.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift E"));
        _tempStr = resHelper.getString("ActionExportData.SHORT_DESCRIPTION");
        actionExportData.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExportData.LONG_DESCRIPTION");
        actionExportData.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionExportData.NAME");
        actionExportData.putValue(ItemAction.NAME, _tempStr);
         this.actionExportData.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQueryScheme
        actionQueryScheme.setEnabled(true);
        actionQueryScheme.setDaemonRun(false);

        actionQueryScheme.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift F"));
        actionQueryScheme.putValue(ItemAction.MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
        _tempStr = resHelper.getString("ActionQueryScheme.SHORT_DESCRIPTION");
        actionQueryScheme.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionQueryScheme.LONG_DESCRIPTION");
        actionQueryScheme.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionQueryScheme.NAME");
        actionQueryScheme.putValue(ItemAction.NAME, _tempStr);
         this.actionQueryScheme.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionVoucher
        actionVoucher.setEnabled(true);
        actionVoucher.setDaemonRun(false);

        actionVoucher.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl K"));
        _tempStr = resHelper.getString("ActionVoucher.SHORT_DESCRIPTION");
        actionVoucher.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionVoucher.LONG_DESCRIPTION");
        actionVoucher.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionVoucher.NAME");
        actionVoucher.putValue(ItemAction.NAME, _tempStr);
        this.actionVoucher.setExtendProperty("Mutex", "FI_FA_Mutex_Lock,0");
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionVoucher.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionDelVoucher
        actionDelVoucher.setEnabled(true);
        actionDelVoucher.setDaemonRun(false);

        actionDelVoucher.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift K"));
        _tempStr = resHelper.getString("ActionDelVoucher.SHORT_DESCRIPTION");
        actionDelVoucher.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionDelVoucher.LONG_DESCRIPTION");
        actionDelVoucher.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionDelVoucher.NAME");
        actionDelVoucher.putValue(ItemAction.NAME, _tempStr);
        this.actionDelVoucher.setExtendProperty("Mutex", "FI_FA_Mutex_Lock,0");
         this.actionDelVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionDelVoucher.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionDelVoucher.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionChange
        this.actionChange = new ActionChange(this);
        getActionManager().registerAction("actionChange", actionChange);
        this.actionChange.setExtendProperty("Mutex", "FI_FA_Mutex_Lock,0");
         this.actionChange.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionChange.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionChange.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionClear
        this.actionClear = new ActionClear(this);
        getActionManager().registerAction("actionClear", actionClear);
        this.actionClear.setExtendProperty("Mutex", "FI_FA_Mutex_Lock,0");
         this.actionClear.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionClear.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionClear.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
        this.actionAudit.setBindWorkFlow(true);
        this.actionAudit.setExtendProperty("Mutex", "FI_FA_Mutex_Lock,0");
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
        this.actionUnAudit.setBindWorkFlow(true);
        this.actionUnAudit.setExtendProperty("Mutex", "FI_FA_Mutex_Lock,0");
        this.actionUnAudit.setExtendProperty("isObjectUpdateLock", "true");
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionChangeInfo
        this.actionChangeInfo = new ActionChangeInfo(this);
        getActionManager().registerAction("actionChangeInfo", actionChangeInfo);
         this.actionChangeInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionChangeInfo.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionChangeInfo.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionFilter
        this.actionFilter = new ActionFilter(this);
        getActionManager().registerAction("actionFilter", actionFilter);
         this.actionFilter.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionFilter.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionFilter.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSplit
        this.actionSplit = new ActionSplit(this);
        getActionManager().registerAction("actionSplit", actionSplit);
        this.actionSplit.setExtendProperty("Mutex", "FI_FA_Mutex_Lock,0");
         this.actionSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSplit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSplit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionImpCard
        this.actionImpCard = new ActionImpCard(this);
        getActionManager().registerAction("actionImpCard", actionImpCard);
        this.actionImpCard.setExtendProperty("Mutex", "FI_FA_Mutex_Lock,0");
         this.actionImpCard.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExpCard
        this.actionExpCard = new ActionExpCard(this);
        getActionManager().registerAction("actionExpCard", actionExpCard);
         this.actionExpCard.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowQueryAnalysiser
        this.actionShowQueryAnalysiser = new ActionShowQueryAnalysiser(this);
        getActionManager().registerAction("actionShowQueryAnalysiser", actionShowQueryAnalysiser);
         this.actionShowQueryAnalysiser.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrintCard
        this.actionPrintCard = new ActionPrintCard(this);
        getActionManager().registerAction("actionPrintCard", actionPrintCard);
         this.actionPrintCard.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrintCardPreview
        this.actionPrintCardPreview = new ActionPrintCardPreview(this);
        getActionManager().registerAction("actionPrintCardPreview", actionPrintCardPreview);
         this.actionPrintCardPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatchSubmit
        this.actionBatchSubmit = new ActionBatchSubmit(this);
        getActionManager().registerAction("actionBatchSubmit", actionBatchSubmit);
        this.actionBatchSubmit.setExtendProperty("Mutex", "FI_FA_Mutex_Lock,0");
         this.actionBatchSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionBatchSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
        //actionBatchAddNew
        this.actionBatchAddNew = new ActionBatchAddNew(this);
        getActionManager().registerAction("actionBatchAddNew", actionBatchAddNew);
         this.actionBatchAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTransToBook
        this.actionTransToBook = new ActionTransToBook(this);
        getActionManager().registerAction("actionTransToBook", actionTransToBook);
         this.actionTransToBook.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTraceUpCard
        this.actionTraceUpCard = new ActionTraceUpCard(this);
        getActionManager().registerAction("actionTraceUpCard", actionTraceUpCard);
         this.actionTraceUpCard.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTraceDownCard
        this.actionTraceDownCard = new ActionTraceDownCard(this);
        getActionManager().registerAction("actionTraceDownCard", actionTraceDownCard);
         this.actionTraceDownCard.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImpBarCodeModel
        this.actionImpBarCodeModel = new ActionImpBarCodeModel(this);
        getActionManager().registerAction("actionImpBarCodeModel", actionImpBarCodeModel);
         this.actionImpBarCodeModel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrintBarCode
        this.actionPrintBarCode = new ActionPrintBarCode(this);
        getActionManager().registerAction("actionPrintBarCode", actionPrintBarCode);
         this.actionPrintBarCode.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionGennerBarCode
        this.actionGennerBarCode = new ActionGennerBarCode(this);
        getActionManager().registerAction("actionGennerBarCode", actionGennerBarCode);
         this.actionGennerBarCode.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnBatchAddNew = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBatchSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator1 = new javax.swing.JToolBar.Separator();
        this.separator2 = new javax.swing.JToolBar.Separator();
        this.btnAuditing = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancelAuditing = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChange = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClear = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator3 = new javax.swing.JToolBar.Separator();
        this.btnChangeInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator5 = new javax.swing.JToolBar.Separator();
        this.separator4 = new javax.swing.JToolBar.Separator();
        this.btnTraceUpCard = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTraceDownCard = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemBatchAddNew = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuImport = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemImpCard = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImpFacility = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImpCostCenter = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImpDept = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImpEva = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImpDef = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuExport = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemExpCard = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpFacility = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpCostCenter = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpDept = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpEva = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpDef = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuExportSelect = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemExpCardSelect = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpFacilitySelect = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpCostCenterSelect = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpDeptSelect = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpEvaSelect = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpDefSelect = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemPrintCard = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPrintCardPre = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuImpBarCodeModel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuPrintBarCode = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuGennerBarCode = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBatchSubmit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menChangeInfo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menAuditing = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menCancelAuditing = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menChange = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menClear = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemShowQueryAnalysiser = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemTransToBook = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemTraceUpCard = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemTraceDownCard = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDContainer1.setName("kDContainer1");
        this.btnBatchAddNew.setName("btnBatchAddNew");
        this.btnBatchSubmit.setName("btnBatchSubmit");
        this.separator1.setName("separator1");
        this.separator2.setName("separator2");
        this.btnAuditing.setName("btnAuditing");
        this.btnCancelAuditing.setName("btnCancelAuditing");
        this.btnChange.setName("btnChange");
        this.btnClear.setName("btnClear");
        this.btnSplit.setName("btnSplit");
        this.separator3.setName("separator3");
        this.btnChangeInfo.setName("btnChangeInfo");
        this.separator5.setName("separator5");
        this.separator4.setName("separator4");
        this.btnTraceUpCard.setName("btnTraceUpCard");
        this.btnTraceDownCard.setName("btnTraceDownCard");
        this.menuItemBatchAddNew.setName("menuItemBatchAddNew");
        this.menuImport.setName("menuImport");
        this.menuItemImpCard.setName("menuItemImpCard");
        this.menuItemImpFacility.setName("menuItemImpFacility");
        this.menuItemImpCostCenter.setName("menuItemImpCostCenter");
        this.menuItemImpDept.setName("menuItemImpDept");
        this.menuItemImpEva.setName("menuItemImpEva");
        this.menuItemImpDef.setName("menuItemImpDef");
        this.menuExport.setName("menuExport");
        this.menuItemExpCard.setName("menuItemExpCard");
        this.menuItemExpFacility.setName("menuItemExpFacility");
        this.menuItemExpCostCenter.setName("menuItemExpCostCenter");
        this.menuItemExpDept.setName("menuItemExpDept");
        this.menuItemExpEva.setName("menuItemExpEva");
        this.menuItemExpDef.setName("menuItemExpDef");
        this.menuExportSelect.setName("menuExportSelect");
        this.menuItemExpCardSelect.setName("menuItemExpCardSelect");
        this.menuItemExpFacilitySelect.setName("menuItemExpFacilitySelect");
        this.menuItemExpCostCenterSelect.setName("menuItemExpCostCenterSelect");
        this.menuItemExpDeptSelect.setName("menuItemExpDeptSelect");
        this.menuItemExpEvaSelect.setName("menuItemExpEvaSelect");
        this.menuItemExpDefSelect.setName("menuItemExpDefSelect");
        this.kDSeparator9.setName("kDSeparator9");
        this.menuItemPrintCard.setName("menuItemPrintCard");
        this.menuItemPrintCardPre.setName("menuItemPrintCardPre");
        this.menuImpBarCodeModel.setName("menuImpBarCodeModel");
        this.menuPrintBarCode.setName("menuPrintBarCode");
        this.menuGennerBarCode.setName("menuGennerBarCode");
        this.menuItemBatchSubmit.setName("menuItemBatchSubmit");
        this.menChangeInfo.setName("menChangeInfo");
        this.menAuditing.setName("menAuditing");
        this.menCancelAuditing.setName("menCancelAuditing");
        this.kDSeparator8.setName("kDSeparator8");
        this.menChange.setName("menChange");
        this.menClear.setName("menClear");
        this.menSplit.setName("menSplit");
        this.menuItemShowQueryAnalysiser.setName("menuItemShowQueryAnalysiser");
        this.menuItemTransToBook.setName("menuItemTransToBook");
        this.menuItemTraceUpCard.setName("menuItemTraceUpCard");
        this.menuItemTraceDownCard.setName("menuItemTraceDownCard");
        // CoreUI		
        this.menuItemCalculator.setText(resHelper.getString("menuItemCalculator.text"));		
        this.menuItemCalculator.setMnemonic(67);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol20\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol24\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol25\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol26\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol27\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol28\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol29\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol31\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol32\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol33\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol34\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol35\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol36\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol37\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol38\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol39\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol40\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol41\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol42\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol44\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol45\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol46\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol47\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol53\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol55\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol56\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol57\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol58\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol59\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol60\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol61\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol62\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol63\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol64\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol65\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol66\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol67\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol68\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol69\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol70\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol71\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol72\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol73\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol74\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol75\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol76\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol77\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol78\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol80\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol81\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol82\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol83\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol84\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol85\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol86\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol87\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol88\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"assetCat.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"number\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"oldNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"groupNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"barCode\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"assetName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"specs\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"measureUnit.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"assetAmt\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"dept.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"useDepart\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"usePerson\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"keeper.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"storeCity.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"costCenter\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"accountDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"fiAccountDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"currency.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"originAmt\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"assetValue\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"inputTax\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"addonFare\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"treatmentIncome\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"false\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"addons\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"false\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"isEvaledBefore\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"initEvalValue\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"deprTermCount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"useTermCount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"useYears\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"evalLeftPeriod\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"deprTTerm\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /><t:Column t:key=\"monthDepreRate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" t:styleID=\"sCol32\" /><t:Column t:key=\"accuDepr\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" t:styleID=\"sCol33\" /><t:Column t:key=\"accuDeprTYear\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" t:styleID=\"sCol34\" /><t:Column t:key=\"accuDeprAll\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" t:styleID=\"sCol35\" /><t:Column t:key=\"neatRemValue\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" t:styleID=\"sCol36\" /><t:Column t:key=\"neatLeftRate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" t:styleID=\"sCol37\" /><t:Column t:key=\"decValue\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" t:styleID=\"sCol38\" /><t:Column t:key=\"neatValue\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" t:styleID=\"sCol39\" /><t:Column t:key=\"neatAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" t:styleID=\"sCol40\" /><t:Column t:key=\"originFlag\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"41\" t:styleID=\"sCol41\" /><t:Column t:key=\"isOveraged\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"42\" t:styleID=\"sCol42\" /><t:Column t:key=\"deprMethod.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"43\" /><t:Column t:key=\"hasDisabled\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"44\" t:styleID=\"sCol44\" /><t:Column t:key=\"accountAsset.dispName\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"45\" t:styleID=\"sCol45\" /><t:Column t:key=\"accountAccuDepr.dispName\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"46\" t:styleID=\"sCol46\" /><t:Column t:key=\"accountDevValue.dispName\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"47\" t:styleID=\"sCol47\" /><t:Column t:key=\"bizStatus\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"48\" /><t:Column t:key=\"leaseStatus\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"49\" /><t:Column t:key=\"checkedStatus\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"50\" /><t:Column t:key=\"deletedStatus\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"51\" /><t:Column t:key=\"effectedStatus\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"52\" /><t:Column t:key=\"blockedStatus\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"53\" t:styleID=\"sCol53\" /><t:Column t:key=\"fiVouchered\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"54\" /><t:Column t:key=\"hasEffected\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"56\" t:styleID=\"sCol55\" /><t:Column t:key=\"DeprPolicy.OldChange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"57\" t:styleID=\"sCol56\" /><t:Column t:key=\"DeprPolicy.TotalDeprChange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"58\" t:styleID=\"sCol57\" /><t:Column t:key=\"DeprPolicy.DevalueChange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"59\" t:styleID=\"sCol58\" /><t:Column t:key=\"DeprPolicy.LeaveValueChange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"60\" t:styleID=\"sCol59\" /><t:Column t:key=\"DeprPolicy.ChargeAccoutChange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"61\" t:styleID=\"sCol60\" /><t:Column t:key=\"DeprPolicy.UsedLifeChange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"62\" t:styleID=\"sCol61\" /><t:Column t:key=\"DeprPolicy.DeprMethodChange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"63\" t:styleID=\"sCol62\" /><t:Column t:key=\"DeprPolicy.EvaluateChange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"64\" t:styleID=\"sCol63\" /><t:Column t:key=\"DeprPolicy.CostCenterChange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"65\" t:styleID=\"sCol64\" /><t:Column t:key=\"DeprPolicy.UseStatusChange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"66\" t:styleID=\"sCol65\" /><t:Column t:key=\"DeprPolicy.TailDispose\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"67\" t:styleID=\"sCol66\" /><t:Column t:key=\"DeprPolicy.StartDeprTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"68\" t:styleID=\"sCol67\" /><t:Column t:key=\"DeprPolicy.DeprRule\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"69\" t:styleID=\"sCol68\" /><t:Column t:key=\"hasChanged\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"70\" t:styleID=\"sCol69\" /><t:Column t:key=\"hasCleared\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"71\" t:styleID=\"sCol70\" /><t:Column t:key=\"hasEvaled\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"72\" t:styleID=\"sCol71\" /><t:Column t:key=\"hasNew\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"73\" t:styleID=\"sCol72\" /><t:Column t:key=\"hasSplited\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"74\" t:styleID=\"sCol73\" /><t:Column t:key=\"currency.precision\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"75\" t:styleID=\"sCol74\" /><t:Column t:key=\"currency.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"76\" t:styleID=\"sCol75\" /><t:Column t:key=\"assetCat.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"77\" t:styleID=\"sCol76\" /><t:Column t:key=\"deprMethod.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"78\" t:styleID=\"sCol77\" /><t:Column t:key=\"company.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"79\" t:styleID=\"sCol78\" /><t:Column t:key=\"creator.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"80\" /><t:Column t:key=\"auditor.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"81\" t:styleID=\"sCol80\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"82\" t:styleID=\"sCol81\" /><t:Column t:key=\"assetCat.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"83\" t:styleID=\"sCol82\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"84\" t:styleID=\"sCol83\" /><t:Column t:key=\"assetCat.calcuByEvaluate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"85\" t:styleID=\"sCol84\" /><t:Column t:key=\"deprMethod.isWorkload\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"86\" t:styleID=\"sCol85\" /><t:Column t:key=\"wrtyPeriod\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"87\" t:styleID=\"sCol86\" /><t:Column t:key=\"wrtyNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"88\" t:styleID=\"sCol87\" /><t:Column t:key=\"company.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol88\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{assetCat.name}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{oldNumber}</t:Cell><t:Cell>$Resource{groupNumber}</t:Cell><t:Cell>$Resource{barCode}</t:Cell><t:Cell>$Resource{assetName}</t:Cell><t:Cell>$Resource{specs}</t:Cell><t:Cell>$Resource{measureUnit.name}</t:Cell><t:Cell>$Resource{assetAmt}</t:Cell><t:Cell>$Resource{dept.name}</t:Cell><t:Cell>$Resource{useDepart}</t:Cell><t:Cell>$Resource{usePerson}</t:Cell><t:Cell>$Resource{keeper.name}</t:Cell><t:Cell>$Resource{storeCity.name}</t:Cell><t:Cell>$Resource{costCenter}</t:Cell><t:Cell>$Resource{accountDate}</t:Cell><t:Cell>$Resource{fiAccountDate}</t:Cell><t:Cell>$Resource{currency.name}</t:Cell><t:Cell>$Resource{originAmt}</t:Cell><t:Cell>$Resource{assetValue}</t:Cell><t:Cell>$Resource{inputTax}</t:Cell><t:Cell>$Resource{addonFare}</t:Cell><t:Cell>$Resource{treatmentIncome}</t:Cell><t:Cell>$Resource{addons}</t:Cell><t:Cell>$Resource{isEvaledBefore}</t:Cell><t:Cell>$Resource{initEvalValue}</t:Cell><t:Cell>$Resource{deprTermCount}</t:Cell><t:Cell>$Resource{useTermCount}</t:Cell><t:Cell>$Resource{useYears}</t:Cell><t:Cell>$Resource{evalLeftPeriod}</t:Cell><t:Cell>$Resource{deprTTerm}</t:Cell><t:Cell>$Resource{monthDepreRate}</t:Cell><t:Cell>$Resource{accuDepr}</t:Cell><t:Cell>$Resource{accuDeprTYear}</t:Cell><t:Cell>$Resource{accuDeprAll}</t:Cell><t:Cell>$Resource{neatRemValue}</t:Cell><t:Cell>$Resource{neatLeftRate}</t:Cell><t:Cell>$Resource{decValue}</t:Cell><t:Cell>$Resource{neatValue}</t:Cell><t:Cell>$Resource{neatAmt}</t:Cell><t:Cell>$Resource{originFlag}</t:Cell><t:Cell>$Resource{isOveraged}</t:Cell><t:Cell>$Resource{deprMethod.name}</t:Cell><t:Cell>$Resource{hasDisabled}</t:Cell><t:Cell>$Resource{accountAsset.dispName}</t:Cell><t:Cell>$Resource{accountAccuDepr.dispName}</t:Cell><t:Cell>$Resource{accountDevValue.dispName}</t:Cell><t:Cell>$Resource{bizStatus}</t:Cell><t:Cell>$Resource{leaseStatus}</t:Cell><t:Cell>$Resource{checkedStatus}</t:Cell><t:Cell>$Resource{deletedStatus}</t:Cell><t:Cell>$Resource{effectedStatus}</t:Cell><t:Cell>$Resource{blockedStatus}</t:Cell><t:Cell>$Resource{fiVouchered}</t:Cell><t:Cell>$Resource{hasEffected}</t:Cell><t:Cell>$Resource{DeprPolicy.OldChange}</t:Cell><t:Cell>$Resource{DeprPolicy.TotalDeprChange}</t:Cell><t:Cell>$Resource{DeprPolicy.DevalueChange}</t:Cell><t:Cell>$Resource{DeprPolicy.LeaveValueChange}</t:Cell><t:Cell>$Resource{DeprPolicy.ChargeAccoutChange}</t:Cell><t:Cell>$Resource{DeprPolicy.UsedLifeChange}</t:Cell><t:Cell>$Resource{DeprPolicy.DeprMethodChange}</t:Cell><t:Cell>$Resource{DeprPolicy.EvaluateChange}</t:Cell><t:Cell>$Resource{DeprPolicy.CostCenterChange}</t:Cell><t:Cell>$Resource{DeprPolicy.UseStatusChange}</t:Cell><t:Cell>$Resource{DeprPolicy.TailDispose}</t:Cell><t:Cell>$Resource{DeprPolicy.StartDeprTime}</t:Cell><t:Cell>$Resource{DeprPolicy.DeprRule}</t:Cell><t:Cell>$Resource{hasChanged}</t:Cell><t:Cell>$Resource{hasCleared}</t:Cell><t:Cell>$Resource{hasEvaled}</t:Cell><t:Cell>$Resource{hasNew}</t:Cell><t:Cell>$Resource{hasSplited}</t:Cell><t:Cell>$Resource{currency.precision}</t:Cell><t:Cell>$Resource{currency.id}</t:Cell><t:Cell>$Resource{assetCat.number}</t:Cell><t:Cell>$Resource{deprMethod.number}</t:Cell><t:Cell>$Resource{company.id}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{auditor.name}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{assetCat.number}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{assetCat.calcuByEvaluate}</t:Cell><t:Cell>$Resource{deprMethod.isWorkload}</t:Cell><t:Cell>$Resource{wrtyPeriod}</t:Cell><t:Cell>$Resource{wrtyNumber}</t:Cell><t:Cell>$Resource{company.name}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","assetCat.name","number","oldNumber","groupNumber","barCode","assetName","specs","measureUnit.name","assetAmt","dept.name","useDepart","usePerson","keeper.name","storeCity.name","costCenter","accountDate","fiAccountDate","currency.name","originAmt","assetValue","inputTax","addonFare","treatmentIncome","addons","isEvaledBefore","initEvalValue","deprTermCount","useTermCount","useYears","evalLeftPeriod","deprTTerm","monthDepreRate","accuDepr","accuDeprTYear","accuDeprAll","neatRemValue","neatLeftRate","decValue","neatValue","neatAmt","originFlag","isOveraged","deprMethod.name","hasDisabled","accountAsset.dispName","accountAccuDepr.dispName","accountDevValue.dispName","bizStatus","leaseStatus","checkedStatus","deletedStatus","effectedStatus","blockedStatus","fiVouchered","hasEffected","DeprPolicy.OldChange","DeprPolicy.TotalDeprChange","DeprPolicy.DevalueChange","DeprPolicy.LeaveValueChange","DeprPolicy.ChargeAccoutChange","DeprPolicy.UsedLifeChange","DeprPolicy.DeprMethodChange","DeprPolicy.EvaluateChange","DeprPolicy.CostCenterChange","DeprPolicy.UseStatusChange","DeprPolicy.TailDispose","DeprPolicy.StartDeprTime","DeprPolicy.DeprRule","hasChanged","hasCleared","hasEvaled","hasNew","hasSplited","currency.precision","","assetCat.number","deprMethod.number","","creator.name","auditor.name","remark","","lastUpdateTime","assetCat.calcuByEvaluate","deprMethod.isWorkload","wrtyPeriod","wrtyNumber","company.name"});

		
        this.menuItemImportData.setVisible(false);		
        this.menuItemImportData.setEnabled(false);		
        this.menuItemPrint.setText(resHelper.getString("menuItemPrint.text"));		
        this.menuItemPrint.setToolTipText(resHelper.getString("menuItemPrint.toolTipText"));		
        this.menuItemPrintPreview.setText(resHelper.getString("menuItemPrintPreview.text"));		
        this.menuItemPrintPreview.setToolTipText(resHelper.getString("menuItemPrintPreview.toolTipText"));
        this.menuItemQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCreateTo.setVisible(false);		
        this.btnCopyTo.setVisible(false);		
        this.separatorFW3.setEnabled(false);		
        this.separatorFW3.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.menuItemCreateTo.setVisible(false);		
        this.menuItemCopyTo.setVisible(false);		
        this.kDSeparator5.setEnabled(false);		
        this.kDSeparator5.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);
        // kDContainer1
        // btnBatchAddNew
        this.btnBatchAddNew.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchAddNew.setText(resHelper.getString("btnBatchAddNew.text"));		
        this.btnBatchAddNew.setToolTipText(resHelper.getString("btnBatchAddNew.toolTipText"));
        // btnBatchSubmit
        this.btnBatchSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchSubmit.setText(resHelper.getString("btnBatchSubmit.text"));
        // separator1		
        this.separator1.setOrientation(1);		
        this.separator1.setEnabled(false);		
        this.separator1.setVisible(false);
        // separator2		
        this.separator2.setOrientation(1);
        // btnAuditing
        this.btnAuditing.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAuditing.setText(resHelper.getString("btnAuditing.text"));
        // btnCancelAuditing
        this.btnCancelAuditing.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancelAuditing.setText(resHelper.getString("btnCancelAuditing.text"));
        // btnChange
        this.btnChange.setAction((IItemAction)ActionProxyFactory.getProxy(actionChange, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChange.setText(resHelper.getString("btnChange.text"));
        // btnClear
        this.btnClear.setAction((IItemAction)ActionProxyFactory.getProxy(actionClear, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClear.setText(resHelper.getString("btnClear.text"));
        // btnSplit
        this.btnSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplit.setText(resHelper.getString("btnSplit.text"));
        // separator3		
        this.separator3.setOrientation(1);
        // btnChangeInfo
        this.btnChangeInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChangeInfo.setText(resHelper.getString("btnChangeInfo.text"));
        // separator5		
        this.separator5.setOrientation(1);
        // separator4		
        this.separator4.setOrientation(1);		
        this.separator4.setEnabled(false);		
        this.separator4.setVisible(false);
        // btnTraceUpCard
        this.btnTraceUpCard.setAction((IItemAction)ActionProxyFactory.getProxy(actionTraceUpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTraceUpCard.setText(resHelper.getString("btnTraceUpCard.text"));
        // btnTraceDownCard
        this.btnTraceDownCard.setAction((IItemAction)ActionProxyFactory.getProxy(actionTraceDownCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTraceDownCard.setText(resHelper.getString("btnTraceDownCard.text"));
        // menuItemBatchAddNew
        this.menuItemBatchAddNew.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBatchAddNew.setText(resHelper.getString("menuItemBatchAddNew.text"));		
        this.menuItemBatchAddNew.setToolTipText(resHelper.getString("menuItemBatchAddNew.toolTipText"));
        // menuImport		
        this.menuImport.setText(resHelper.getString("menuImport.text"));		
        this.menuImport.setToolTipText(resHelper.getString("menuImport.toolTipText"));		
        this.menuImport.setLabel(resHelper.getString("menuImport.label"));		
        this.menuImport.setMnemonic(73);
        // menuItemImpCard
        this.menuItemImpCard.setAction((IItemAction)ActionProxyFactory.getProxy(actionImpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImpCard.setText(resHelper.getString("menuItemImpCard.text"));		
        this.menuItemImpCard.setToolTipText(resHelper.getString("menuItemImpCard.toolTipText"));		
        this.menuItemImpCard.setActionCommand("cardimp");
        // menuItemImpFacility
        this.menuItemImpFacility.setAction((IItemAction)ActionProxyFactory.getProxy(actionImpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImpFacility.setText(resHelper.getString("menuItemImpFacility.text"));		
        this.menuItemImpFacility.setToolTipText(resHelper.getString("menuItemImpFacility.toolTipText"));		
        this.menuItemImpFacility.setActionCommand("facilityimp");
        // menuItemImpCostCenter
        this.menuItemImpCostCenter.setAction((IItemAction)ActionProxyFactory.getProxy(actionImpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImpCostCenter.setText(resHelper.getString("menuItemImpCostCenter.text"));		
        this.menuItemImpCostCenter.setToolTipText(resHelper.getString("menuItemImpCostCenter.toolTipText"));		
        this.menuItemImpCostCenter.setActionCommand("costcenterimp");
        // menuItemImpDept
        this.menuItemImpDept.setAction((IItemAction)ActionProxyFactory.getProxy(actionImpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImpDept.setText(resHelper.getString("menuItemImpDept.text"));		
        this.menuItemImpDept.setToolTipText(resHelper.getString("menuItemImpDept.toolTipText"));		
        this.menuItemImpDept.setActionCommand("deptimp");
        // menuItemImpEva
        this.menuItemImpEva.setAction((IItemAction)ActionProxyFactory.getProxy(actionImpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImpEva.setText(resHelper.getString("menuItemImpEva.text"));		
        this.menuItemImpEva.setToolTipText(resHelper.getString("menuItemImpEva.toolTipText"));		
        this.menuItemImpEva.setActionCommand("evaimp");
        // menuItemImpDef
        this.menuItemImpDef.setAction((IItemAction)ActionProxyFactory.getProxy(actionImpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImpDef.setText(resHelper.getString("menuItemImpDef.text"));		
        this.menuItemImpDef.setActionCommand("defproperty");		
        this.menuItemImpDef.setToolTipText(resHelper.getString("menuItemImpDef.toolTipText"));
        // menuExport		
        this.menuExport.setText(resHelper.getString("menuExport.text"));		
        this.menuExport.setLabel(resHelper.getString("menuExport.label"));		
        this.menuExport.setToolTipText(resHelper.getString("menuExport.toolTipText"));		
        this.menuExport.setMnemonic(69);
        // menuItemExpCard
        this.menuItemExpCard.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpCard.setText(resHelper.getString("menuItemExpCard.text"));		
        this.menuItemExpCard.setToolTipText(resHelper.getString("menuItemExpCard.toolTipText"));		
        this.menuItemExpCard.setActionCommand("cardexp");
        // menuItemExpFacility
        this.menuItemExpFacility.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpFacility.setText(resHelper.getString("menuItemExpFacility.text"));		
        this.menuItemExpFacility.setToolTipText(resHelper.getString("menuItemExpFacility.toolTipText"));		
        this.menuItemExpFacility.setActionCommand("facilityexp");
        // menuItemExpCostCenter
        this.menuItemExpCostCenter.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpCostCenter.setText(resHelper.getString("menuItemExpCostCenter.text"));		
        this.menuItemExpCostCenter.setToolTipText(resHelper.getString("menuItemExpCostCenter.toolTipText"));		
        this.menuItemExpCostCenter.setActionCommand("costcenterexp");
        // menuItemExpDept
        this.menuItemExpDept.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpDept.setText(resHelper.getString("menuItemExpDept.text"));		
        this.menuItemExpDept.setToolTipText(resHelper.getString("menuItemExpDept.toolTipText"));		
        this.menuItemExpDept.setActionCommand("deptexp");
        // menuItemExpEva
        this.menuItemExpEva.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpEva.setText(resHelper.getString("menuItemExpEva.text"));		
        this.menuItemExpEva.setToolTipText(resHelper.getString("menuItemExpEva.toolTipText"));		
        this.menuItemExpEva.setActionCommand("evaexp");
        // menuItemExpDef
        this.menuItemExpDef.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpDef.setText(resHelper.getString("menuItemExpDef.text"));		
        this.menuItemExpDef.setToolTipText(resHelper.getString("menuItemExpDef.toolTipText"));		
        this.menuItemExpDef.setActionCommand("defproperty");
        // menuExportSelect		
        this.menuExportSelect.setText(resHelper.getString("menuExportSelect.text"));		
        this.menuExportSelect.setLabel(resHelper.getString("menuExportSelect.label"));		
        this.menuExportSelect.setMnemonic(85);
        // menuItemExpCardSelect
        this.menuItemExpCardSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpCardSelect.setText(resHelper.getString("menuItemExpCardSelect.text"));		
        this.menuItemExpCardSelect.setActionCommand("cardexp_select");		
        this.menuItemExpCardSelect.setToolTipText(resHelper.getString("menuItemExpCardSelect.toolTipText"));
        // menuItemExpFacilitySelect
        this.menuItemExpFacilitySelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpFacilitySelect.setText(resHelper.getString("menuItemExpFacilitySelect.text"));		
        this.menuItemExpFacilitySelect.setToolTipText(resHelper.getString("menuItemExpFacilitySelect.toolTipText"));		
        this.menuItemExpFacilitySelect.setActionCommand("facilityexp_select");
        // menuItemExpCostCenterSelect
        this.menuItemExpCostCenterSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpCostCenterSelect.setText(resHelper.getString("menuItemExpCostCenterSelect.text"));		
        this.menuItemExpCostCenterSelect.setToolTipText(resHelper.getString("menuItemExpCostCenterSelect.toolTipText"));		
        this.menuItemExpCostCenterSelect.setActionCommand("costcenterexp_select");
        // menuItemExpDeptSelect
        this.menuItemExpDeptSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpDeptSelect.setText(resHelper.getString("menuItemExpDeptSelect.text"));		
        this.menuItemExpDeptSelect.setToolTipText(resHelper.getString("menuItemExpDeptSelect.toolTipText"));		
        this.menuItemExpDeptSelect.setActionCommand("deptexp_select");
        // menuItemExpEvaSelect
        this.menuItemExpEvaSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpEvaSelect.setText(resHelper.getString("menuItemExpEvaSelect.text"));		
        this.menuItemExpEvaSelect.setToolTipText(resHelper.getString("menuItemExpEvaSelect.toolTipText"));		
        this.menuItemExpEvaSelect.setActionCommand("evaexp_select");
        // menuItemExpDefSelect
        this.menuItemExpDefSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpDefSelect.setText(resHelper.getString("menuItemExpDefSelect.text"));		
        this.menuItemExpDefSelect.setActionCommand("defproperty");		
        this.menuItemExpDefSelect.setToolTipText(resHelper.getString("menuItemExpDefSelect.toolTipText"));
        // kDSeparator9
        // menuItemPrintCard
        this.menuItemPrintCard.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrintCard.setText(resHelper.getString("menuItemPrintCard.text"));		
        this.menuItemPrintCard.setToolTipText(resHelper.getString("menuItemPrintCard.toolTipText"));
        this.menuItemPrintCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    menuItemPrintCard_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // menuItemPrintCardPre
        this.menuItemPrintCardPre.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintCardPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrintCardPre.setText(resHelper.getString("menuItemPrintCardPre.text"));		
        this.menuItemPrintCardPre.setToolTipText(resHelper.getString("menuItemPrintCardPre.toolTipText"));
        this.menuItemPrintCardPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    menuItemPrintCardPre_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // menuImpBarCodeModel
        this.menuImpBarCodeModel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImpBarCodeModel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuImpBarCodeModel.setText(resHelper.getString("menuImpBarCodeModel.text"));
        // menuPrintBarCode
        this.menuPrintBarCode.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintBarCode, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuPrintBarCode.setText(resHelper.getString("menuPrintBarCode.text"));
        // menuGennerBarCode
        this.menuGennerBarCode.setAction((IItemAction)ActionProxyFactory.getProxy(actionGennerBarCode, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuGennerBarCode.setText(resHelper.getString("menuGennerBarCode.text"));
        // menuItemBatchSubmit
        this.menuItemBatchSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBatchSubmit.setText(resHelper.getString("menuItemBatchSubmit.text"));		
        this.menuItemBatchSubmit.setMnemonic(85);
        // menChangeInfo
        this.menChangeInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menChangeInfo.setText(resHelper.getString("menChangeInfo.text"));		
        this.menChangeInfo.setMnemonic(77);
        // menAuditing
        this.menAuditing.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menAuditing.setText(resHelper.getString("menAuditing.text"));		
        this.menAuditing.setMnemonic(80);
        // menCancelAuditing
        this.menCancelAuditing.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menCancelAuditing.setText(resHelper.getString("menCancelAuditing.text"));		
        this.menCancelAuditing.setMnemonic(85);
        // kDSeparator8
        // menChange
        this.menChange.setAction((IItemAction)ActionProxyFactory.getProxy(actionChange, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menChange.setText(resHelper.getString("menChange.text"));		
        this.menChange.setMnemonic(72);
        // menClear
        this.menClear.setAction((IItemAction)ActionProxyFactory.getProxy(actionClear, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menClear.setText(resHelper.getString("menClear.text"));		
        this.menClear.setMnemonic(76);
        // menSplit
        this.menSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menSplit.setText(resHelper.getString("menSplit.text"));		
        this.menSplit.setMnemonic(83);
        // menuItemShowQueryAnalysiser
        this.menuItemShowQueryAnalysiser.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowQueryAnalysiser, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemShowQueryAnalysiser.setText(resHelper.getString("menuItemShowQueryAnalysiser.text"));		
        this.menuItemShowQueryAnalysiser.setVisible(false);
        // menuItemTransToBook
        this.menuItemTransToBook.setAction((IItemAction)ActionProxyFactory.getProxy(actionTransToBook, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemTransToBook.setText(resHelper.getString("menuItemTransToBook.text"));
        // menuItemTraceUpCard
        this.menuItemTraceUpCard.setAction((IItemAction)ActionProxyFactory.getProxy(actionTraceUpCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemTraceUpCard.setText(resHelper.getString("menuItemTraceUpCard.text"));
        // menuItemTraceDownCard
        this.menuItemTraceDownCard.setAction((IItemAction)ActionProxyFactory.getProxy(actionTraceDownCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemTraceDownCard.setText(resHelper.getString("menuItemTraceDownCard.text"));
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
        this.setBounds(new Rectangle(10, 10, 900, 400));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 900, 400));
        kDContainer1.setBounds(new Rectangle(4, 4, 894, 389));
        this.add(kDContainer1, new KDLayout.Constraints(4, 4, 894, 389, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        this.menuBar.add(menuWorkFlow);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemBatchAddNew);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudShare);
        menuFile.add(separatorFile1);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuImport);
        menuFile.add(menuExport);
        menuFile.add(menuExportSelect);
        menuFile.add(kDSeparator1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator9);
        menuFile.add(menuItemPrintCard);
        menuFile.add(menuItemPrintCardPre);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuImpBarCodeModel);
        menuFile.add(menuPrintBarCode);
        menuFile.add(menuGennerBarCode);
        menuFile.add(menuItemExitCurrent);
        //menuImport
        menuImport.add(menuItemImpCard);
        menuImport.add(menuItemImpFacility);
        menuImport.add(menuItemImpCostCenter);
        menuImport.add(menuItemImpDept);
        menuImport.add(menuItemImpEva);
        menuImport.add(menuItemImpDef);
        //menuExport
        menuExport.add(menuItemExpCard);
        menuExport.add(menuItemExpFacility);
        menuExport.add(menuItemExpCostCenter);
        menuExport.add(menuItemExpDept);
        menuExport.add(menuItemExpEva);
        menuExport.add(menuItemExpDef);
        //menuExportSelect
        menuExportSelect.add(menuItemExpCardSelect);
        menuExportSelect.add(menuItemExpFacilitySelect);
        menuExportSelect.add(menuItemExpCostCenterSelect);
        menuExportSelect.add(menuItemExpDeptSelect);
        menuExportSelect.add(menuItemExpEvaSelect);
        menuExportSelect.add(menuItemExpDefSelect);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemBatchSubmit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(kDSeparator5);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator6);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(separatorView1);
        menuView.add(menuItemSwitchView);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator3);
        menuView.add(menChangeInfo);
        menuView.add(menuItemQueryScheme);
        //menuBiz
        menuBiz.add(menAuditing);
        menuBiz.add(menCancelAuditing);
        menuBiz.add(kDSeparator8);
        menuBiz.add(menChange);
        menuBiz.add(menClear);
        menuBiz.add(menSplit);
        menuBiz.add(kDSeparator7);
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemShowQueryAnalysiser);
        menuBiz.add(menuItemTransToBook);
        menuBiz.add(menuItemTraceUpCard);
        menuBiz.add(menuItemTraceDownCard);
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
        this.toolBar.add(btnBatchAddNew);
        this.toolBar.add(btnBatchSubmit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(separator1);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(separator2);
        this.toolBar.add(btnAuditing);
        this.toolBar.add(btnCancelAuditing);
        this.toolBar.add(btnChange);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnClear);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnSplit);
        this.toolBar.add(separator3);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnChangeInfo);
        this.toolBar.add(separator5);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnCancel);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(separator4);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnTraceUpCard);
        this.toolBar.add(btnTraceDownCard);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.base.app.ImportFaCardUIHandler";
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
    }

    /**
     * output menuItemPrintCard_actionPerformed method
     */
    protected void menuItemPrintCard_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output menuItemPrintCardPre_actionPerformed method
     */
    protected void menuItemPrintCardPre_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
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
        sic.add(new SelectorItemInfo("assetAmt"));
        sic.add(new SelectorItemInfo("accountDate"));
        sic.add(new SelectorItemInfo("specs"));
        sic.add(new SelectorItemInfo("pArea"));
        sic.add(new SelectorItemInfo("mfr"));
        sic.add(new SelectorItemInfo("levFrDate"));
        sic.add(new SelectorItemInfo("paperNo"));
        sic.add(new SelectorItemInfo("originType"));
        sic.add(new SelectorItemInfo("originUnit"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("exRate"));
        sic.add(new SelectorItemInfo("originAmt"));
        sic.add(new SelectorItemInfo("assetValue"));
        sic.add(new SelectorItemInfo("buyValue"));
        sic.add(new SelectorItemInfo("buyAccuDepr"));
        sic.add(new SelectorItemInfo("addons"));
        sic.add(new SelectorItemInfo("deliverDate"));
        sic.add(new SelectorItemInfo("startUseDate"));
        sic.add(new SelectorItemInfo("deprTermCount"));
        sic.add(new SelectorItemInfo("useTermCount"));
        sic.add(new SelectorItemInfo("useYears"));
        sic.add(new SelectorItemInfo("accuDepr"));
        sic.add(new SelectorItemInfo("accuDeprTYear"));
        sic.add(new SelectorItemInfo("neatRemValue"));
        sic.add(new SelectorItemInfo("decValue"));
        sic.add(new SelectorItemInfo("neatValue"));
        sic.add(new SelectorItemInfo("neatAmt"));
        sic.add(new SelectorItemInfo("accuDeprChg"));
        sic.add(new SelectorItemInfo("accuDeprAccuChg"));
        sic.add(new SelectorItemInfo("accuDeprAccuClean"));
        sic.add(new SelectorItemInfo("deprTTerm"));
        sic.add(new SelectorItemInfo("accuDeprChgTYear"));
        sic.add(new SelectorItemInfo("accuDeprAccuChgTYear"));
        sic.add(new SelectorItemInfo("decValueChg"));
        sic.add(new SelectorItemInfo("decValueAccuChg"));
        sic.add(new SelectorItemInfo("originFlag"));
        sic.add(new SelectorItemInfo("bizStatus"));
        sic.add(new SelectorItemInfo("checkedStatus"));
        sic.add(new SelectorItemInfo("deletedStatus"));
        sic.add(new SelectorItemInfo("effectedStatus"));
        sic.add(new SelectorItemInfo("blockedStatus"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("hasEffected"));
        sic.add(new SelectorItemInfo("sourceBillId"));
        sic.add(new SelectorItemInfo("sourceFunction"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("currency.name"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("measureUnit.name"));
        sic.add(new SelectorItemInfo("assetCat.name"));
        sic.add(new SelectorItemInfo("keeper.name"));
        sic.add(new SelectorItemInfo("measureUnitWL.name"));
        sic.add(new SelectorItemInfo("deprMethod.name"));
        sic.add(new SelectorItemInfo("DeprPolicy.OldChange"));
        sic.add(new SelectorItemInfo("DeprPolicy.TotalDeprChange"));
        sic.add(new SelectorItemInfo("DeprPolicy.DevalueChange"));
        sic.add(new SelectorItemInfo("DeprPolicy.LeaveValueChange"));
        sic.add(new SelectorItemInfo("DeprPolicy.ChargeAccoutChange"));
        sic.add(new SelectorItemInfo("DeprPolicy.UsedLifeChange"));
        sic.add(new SelectorItemInfo("DeprPolicy.DeprMethodChange"));
        sic.add(new SelectorItemInfo("DeprPolicy.EvaluateChange"));
        sic.add(new SelectorItemInfo("DeprPolicy.CostCenterChange"));
        sic.add(new SelectorItemInfo("DeprPolicy.UseStatusChange"));
        sic.add(new SelectorItemInfo("DeprPolicy.TailDispose"));
        sic.add(new SelectorItemInfo("DeprPolicy.StartDeprTime"));
        sic.add(new SelectorItemInfo("DeprPolicy.DeprRule"));
        sic.add(new SelectorItemInfo("hasChanged"));
        sic.add(new SelectorItemInfo("hasCleared"));
        sic.add(new SelectorItemInfo("hasEvaled"));
        sic.add(new SelectorItemInfo("hasNew"));
        sic.add(new SelectorItemInfo("hasSplited"));
        sic.add(new SelectorItemInfo("fiVouchered"));
        sic.add(new SelectorItemInfo("currency.precision"));
        sic.add(new SelectorItemInfo("assetCat.number"));
        sic.add(new SelectorItemInfo("deprMethod.number"));
        sic.add(new SelectorItemInfo("isEvaledBefore"));
        sic.add(new SelectorItemInfo("initEvalValue"));
        sic.add(new SelectorItemInfo("evalLeftPeriod"));
        sic.add(new SelectorItemInfo("accuDeprAll"));
        sic.add(new SelectorItemInfo("assetName"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("assetCat.calcuByEvaluate"));
        sic.add(new SelectorItemInfo("monthDepreRate"));
        sic.add(new SelectorItemInfo("isOveraged"));
        sic.add(new SelectorItemInfo("addonFare"));
        sic.add(new SelectorItemInfo("fiAccountDate"));
        sic.add(new SelectorItemInfo("accountAsset.dispName"));
        sic.add(new SelectorItemInfo("accountAccuDepr.dispName"));
        sic.add(new SelectorItemInfo("accountDevValue.dispName"));
        sic.add(new SelectorItemInfo("treatmentIncome"));
        sic.add(new SelectorItemInfo("neatLeftRate"));
        sic.add(new SelectorItemInfo("leaseStatus"));
        sic.add(new SelectorItemInfo("deprMethod.isWorkload"));
        sic.add(new SelectorItemInfo("dept.name"));
        sic.add(new SelectorItemInfo("storeCity.name"));
        sic.add(new SelectorItemInfo("useDepart"));
        sic.add(new SelectorItemInfo("costCenter"));
        sic.add(new SelectorItemInfo("groupNumber"));
        sic.add(new SelectorItemInfo("inputTax"));
        sic.add(new SelectorItemInfo("oldNumber"));
        sic.add(new SelectorItemInfo("wrtyPeriod"));
        sic.add(new SelectorItemInfo("wrtyNumber"));
        sic.add(new SelectorItemInfo("hasDisabled"));
        sic.add(new SelectorItemInfo("usePerson"));
        sic.add(new SelectorItemInfo("barCode"));
        sic.add(new SelectorItemInfo("company.name"));
        return sic;
    }        
    	

    /**
     * output actionCalculator_actionPerformed method
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
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
     * output actionQuery_actionPerformed method
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }
    	

    /**
     * output actionImportData_actionPerformed method
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }
    	

    /**
     * output actionExportData_actionPerformed method
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }
    	

    /**
     * output actionQueryScheme_actionPerformed method
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
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
     * output actionChange_actionPerformed method
     */
    public void actionChange_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClear_actionPerformed method
     */
    public void actionClear_actionPerformed(ActionEvent e) throws Exception
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
    	

    /**
     * output actionChangeInfo_actionPerformed method
     */
    public void actionChangeInfo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFilter_actionPerformed method
     */
    public void actionFilter_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplit_actionPerformed method
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImpCard_actionPerformed method
     */
    public void actionImpCard_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExpCard_actionPerformed method
     */
    public void actionExpCard_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowQueryAnalysiser_actionPerformed method
     */
    public void actionShowQueryAnalysiser_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrintCard_actionPerformed method
     */
    public void actionPrintCard_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrintCardPreview_actionPerformed method
     */
    public void actionPrintCardPreview_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatchSubmit_actionPerformed method
     */
    public void actionBatchSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatchAddNew_actionPerformed method
     */
    public void actionBatchAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTransToBook_actionPerformed method
     */
    public void actionTransToBook_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTraceUpCard_actionPerformed method
     */
    public void actionTraceUpCard_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTraceDownCard_actionPerformed method
     */
    public void actionTraceDownCard_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImpBarCodeModel_actionPerformed method
     */
    public void actionImpBarCodeModel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrintBarCode_actionPerformed method
     */
    public void actionPrintBarCode_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionGennerBarCode_actionPerformed method
     */
    public void actionGennerBarCode_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCalculator(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCalculator(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalculator() {
    	return false;
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
	public RequestContext prepareActionQuery(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionQuery(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuery() {
    	return false;
    }
	public RequestContext prepareActionImportData(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionImportData(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportData() {
    	return false;
    }
	public RequestContext prepareActionExportData(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionExportData(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportData() {
    	return false;
    }
	public RequestContext prepareActionQueryScheme(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionQueryScheme(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQueryScheme() {
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
	public RequestContext prepareActionChange(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChange() {
    	return false;
    }
	public RequestContext prepareActionClear(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClear() {
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
	public RequestContext prepareActionChangeInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChangeInfo() {
    	return false;
    }
	public RequestContext prepareActionFilter(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFilter() {
    	return false;
    }
	public RequestContext prepareActionSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSplit() {
    	return false;
    }
	public RequestContext prepareActionImpCard(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImpCard() {
    	return false;
    }
	public RequestContext prepareActionExpCard(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExpCard() {
    	return false;
    }
	public RequestContext prepareActionShowQueryAnalysiser(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowQueryAnalysiser() {
    	return false;
    }
	public RequestContext prepareActionPrintCard(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintCard() {
    	return false;
    }
	public RequestContext prepareActionPrintCardPreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintCardPreview() {
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
	public RequestContext prepareActionBatchAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchAddNew() {
    	return false;
    }
	public RequestContext prepareActionTransToBook(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTransToBook() {
    	return false;
    }
	public RequestContext prepareActionTraceUpCard(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTraceUpCard() {
    	return false;
    }
	public RequestContext prepareActionTraceDownCard(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTraceDownCard() {
    	return false;
    }
	public RequestContext prepareActionImpBarCodeModel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImpBarCodeModel() {
    	return false;
    }
	public RequestContext prepareActionPrintBarCode(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintBarCode() {
    	return false;
    }
	public RequestContext prepareActionGennerBarCode(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionGennerBarCode() {
    	return false;
    }

    /**
     * output ActionChange class
     */     
    protected class ActionChange extends ItemAction {     
    
        public ActionChange()
        {
            this(null);
        }

        public ActionChange(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl B"));
            _tempStr = resHelper.getString("ActionChange.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChange.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChange.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionChange", "actionChange_actionPerformed", e);
        }
    }

    /**
     * output ActionClear class
     */     
    protected class ActionClear extends ItemAction {     
    
        public ActionClear()
        {
            this(null);
        }

        public ActionClear(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl R"));
            _tempStr = resHelper.getString("ActionClear.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClear.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClear.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionClear", "actionClear_actionPerformed", e);
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
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl U"));
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
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
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
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
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
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionChangeInfo class
     */     
    protected class ActionChangeInfo extends ItemAction {     
    
        public ActionChangeInfo()
        {
            this(null);
        }

        public ActionChangeInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionChangeInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionChangeInfo", "actionChangeInfo_actionPerformed", e);
        }
    }

    /**
     * output ActionFilter class
     */     
    protected class ActionFilter extends ItemAction {     
    
        public ActionFilter()
        {
            this(null);
        }

        public ActionFilter(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl F"));
            _tempStr = resHelper.getString("ActionFilter.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFilter.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFilter.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionFilter", "actionFilter_actionPerformed", e);
        }
    }

    /**
     * output ActionSplit class
     */     
    protected class ActionSplit extends ItemAction {     
    
        public ActionSplit()
        {
            this(null);
        }

        public ActionSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl T"));
            _tempStr = resHelper.getString("ActionSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionSplit", "actionSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionImpCard class
     */     
    protected class ActionImpCard extends ItemAction {     
    
        public ActionImpCard()
        {
            this(null);
        }

        public ActionImpCard(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImpCard.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImpCard.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImpCard.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionImpCard", "actionImpCard_actionPerformed", e);
        }
    }

    /**
     * output ActionExpCard class
     */     
    protected class ActionExpCard extends ItemAction {     
    
        public ActionExpCard()
        {
            this(null);
        }

        public ActionExpCard(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExpCard.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExpCard.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExpCard.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionExpCard", "actionExpCard_actionPerformed", e);
        }
    }

    /**
     * output ActionShowQueryAnalysiser class
     */     
    protected class ActionShowQueryAnalysiser extends ItemAction {     
    
        public ActionShowQueryAnalysiser()
        {
            this(null);
        }

        public ActionShowQueryAnalysiser(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt F12"));
            _tempStr = resHelper.getString("ActionShowQueryAnalysiser.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowQueryAnalysiser.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowQueryAnalysiser.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionShowQueryAnalysiser", "actionShowQueryAnalysiser_actionPerformed", e);
        }
    }

    /**
     * output ActionPrintCard class
     */     
    protected class ActionPrintCard extends ItemAction {     
    
        public ActionPrintCard()
        {
            this(null);
        }

        public ActionPrintCard(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Z"));
            _tempStr = resHelper.getString("ActionPrintCard.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintCard.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintCard.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionPrintCard", "actionPrintCard_actionPerformed", e);
        }
    }

    /**
     * output ActionPrintCardPreview class
     */     
    protected class ActionPrintCardPreview extends ItemAction {     
    
        public ActionPrintCardPreview()
        {
            this(null);
        }

        public ActionPrintCardPreview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift Z"));
            _tempStr = resHelper.getString("ActionPrintCardPreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintCardPreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintCardPreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionPrintCardPreview", "actionPrintCardPreview_actionPerformed", e);
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
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
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
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionBatchSubmit", "actionBatchSubmit_actionPerformed", e);
        }
    }

    /**
     * output ActionBatchAddNew class
     */     
    protected class ActionBatchAddNew extends ItemAction {     
    
        public ActionBatchAddNew()
        {
            this(null);
        }

        public ActionBatchAddNew(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBatchAddNew.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchAddNew.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchAddNew.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionBatchAddNew", "actionBatchAddNew_actionPerformed", e);
        }
    }

    /**
     * output ActionTransToBook class
     */     
    protected class ActionTransToBook extends ItemAction {     
    
        public ActionTransToBook()
        {
            this(null);
        }

        public ActionTransToBook(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl J"));
            this.putValue(ItemAction.MNEMONIC_KEY, new Integer(KeyEvent.VK_J));
            _tempStr = resHelper.getString("ActionTransToBook.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTransToBook.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTransToBook.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionTransToBook", "actionTransToBook_actionPerformed", e);
        }
    }

    /**
     * output ActionTraceUpCard class
     */     
    protected class ActionTraceUpCard extends ItemAction {     
    
        public ActionTraceUpCard()
        {
            this(null);
        }

        public ActionTraceUpCard(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl G"));
            _tempStr = resHelper.getString("ActionTraceUpCard.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTraceUpCard.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTraceUpCard.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionTraceUpCard", "actionTraceUpCard_actionPerformed", e);
        }
    }

    /**
     * output ActionTraceDownCard class
     */     
    protected class ActionTraceDownCard extends ItemAction {     
    
        public ActionTraceDownCard()
        {
            this(null);
        }

        public ActionTraceDownCard(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Q"));
            _tempStr = resHelper.getString("ActionTraceDownCard.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTraceDownCard.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTraceDownCard.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionTraceDownCard", "actionTraceDownCard_actionPerformed", e);
        }
    }

    /**
     * output ActionImpBarCodeModel class
     */     
    protected class ActionImpBarCodeModel extends ItemAction {     
    
        public ActionImpBarCodeModel()
        {
            this(null);
        }

        public ActionImpBarCodeModel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImpBarCodeModel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImpBarCodeModel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImpBarCodeModel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionImpBarCodeModel", "actionImpBarCodeModel_actionPerformed", e);
        }
    }

    /**
     * output ActionPrintBarCode class
     */     
    protected class ActionPrintBarCode extends ItemAction {     
    
        public ActionPrintBarCode()
        {
            this(null);
        }

        public ActionPrintBarCode(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPrintBarCode.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintBarCode.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintBarCode.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionPrintBarCode", "actionPrintBarCode_actionPerformed", e);
        }
    }

    /**
     * output ActionGennerBarCode class
     */     
    protected class ActionGennerBarCode extends ItemAction {     
    
        public ActionGennerBarCode()
        {
            this(null);
        }

        public ActionGennerBarCode(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionGennerBarCode.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGennerBarCode.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGennerBarCode.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportFaCardUI.this, "ActionGennerBarCode", "actionGennerBarCode_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.base.client", "ImportFaCardUI");
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
        return com.kingdee.eas.fi.fa.manage.client.FaCurCardEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fi.fa.manage.FaCurCardFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fi.fa.manage.FaCurCardInfo objectValue = new com.kingdee.eas.fi.fa.manage.FaCurCardInfo();		
        return objectValue;
    }




}