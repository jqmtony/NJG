/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractDesignChangeAuditEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDesignChangeAuditEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeState;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tbpChangAudit;
    protected com.kingdee.bos.ctrl.swing.KDContainer contAheadDisPatch;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConductDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReaDesc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbillType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contputForwardTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalCost;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSpecialtyType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contJobType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsImportChange;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreworkVisa;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractAmPro;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttotalChangeAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdesignChangeAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchangeEstimate;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCostIndex;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboChangeState;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlContent;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlSupp;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnEntrys;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnSuppEntrys;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSuppEntry;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAheadReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConnectType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contValidator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGraphCount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbxNoUse;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDutyAmout;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNoUse;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmoutA;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReason;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAheadReason;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConnectType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtValidator;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboGraphCount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDutyAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNoUse;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmountA;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bmptResaon;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrg;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtConductDept;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbookedDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox cbPeriod;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea txtReaDesc;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSpecialtyType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox billType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkputForwardTime;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conAttenTwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblAttachmentContainer;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDButton viewAttenTwo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConstrSite;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOffer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSpecialtyType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeSubject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUrgentDegree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConstrUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConductUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDesignUnit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbAttenTwo;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbAttachment;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConstrSite;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboOffer;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtChangeSubject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboUrgentDegree;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtConstrUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtConductUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDesignUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSpecialtyType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtJobType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtreworkVisa;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcontractAmPro;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txttotalChangeAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtdesignChangeAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox txtchangeEstimate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContract;
    protected javax.swing.JToolBar.Separator separator4;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAttenTwo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRegister;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisPatch;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRegister;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDispatch;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuViewContract;
    protected com.kingdee.eas.fdc.contract.ChangeAuditBillInfo editData = null;
    protected ActionAddSupp actionAddSupp = null;
    protected ActionDelSupp actionDelSupp = null;
    protected ActionRegister actionRegister = null;
    protected ActionDisPatch actionDisPatch = null;
    protected ActionAheadDisPatch actionAheadDisPatch = null;
    protected ActionViewAttachment actionViewAttachment = null;
    protected ActionViewContract actionViewContract = null;
    protected ActionAttenTwo actionAttenTwo = null;
    protected ActionViewTwo actionViewTwo = null;
    protected actionAcctSelect actionAcctSelect = null;
    protected actionSplitProj actionSplitProj = null;
    protected actionSplitBotUp actionSplitBotUp = null;
    protected actionSplitProd actionSplitProd = null;
    protected actionImpContrSplit actionImpContrSplit = null;
    protected actionAddSplit actionAddSplit = null;
    protected actionRemoveSplitEntry actionRemoveSplitEntry = null;
    /**
     * output class constructor
     */
    public AbstractDesignChangeAuditEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractDesignChangeAuditEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        String _tempStr = null;
        actionSave.setEnabled(true);
        actionSave.setDaemonRun(false);

        actionSave.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl S"));
        _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
        actionSave.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
        actionSave.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.NAME");
        actionSave.putValue(ItemAction.NAME, _tempStr);
        this.actionSave.setBindWorkFlow(true);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmit
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionAttachment
        actionAttachment.setEnabled(true);
        actionAttachment.setDaemonRun(false);

        actionAttachment.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift H"));
        _tempStr = resHelper.getString("ActionAttachment.SHORT_DESCRIPTION");
        actionAttachment.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.LONG_DESCRIPTION");
        actionAttachment.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.NAME");
        actionAttachment.putValue(ItemAction.NAME, _tempStr);
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        actionAudit.setEnabled(false);
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
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddSupp
        this.actionAddSupp = new ActionAddSupp(this);
        getActionManager().registerAction("actionAddSupp", actionAddSupp);
         this.actionAddSupp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelSupp
        this.actionDelSupp = new ActionDelSupp(this);
        getActionManager().registerAction("actionDelSupp", actionDelSupp);
         this.actionDelSupp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRegister
        this.actionRegister = new ActionRegister(this);
        getActionManager().registerAction("actionRegister", actionRegister);
        this.actionRegister.setBindWorkFlow(true);
         this.actionRegister.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisPatch
        this.actionDisPatch = new ActionDisPatch(this);
        getActionManager().registerAction("actionDisPatch", actionDisPatch);
         this.actionDisPatch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAheadDisPatch
        this.actionAheadDisPatch = new ActionAheadDisPatch(this);
        getActionManager().registerAction("actionAheadDisPatch", actionAheadDisPatch);
         this.actionAheadDisPatch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAttachment
        this.actionViewAttachment = new ActionViewAttachment(this);
        getActionManager().registerAction("actionViewAttachment", actionViewAttachment);
         this.actionViewAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContract
        this.actionViewContract = new ActionViewContract(this);
        getActionManager().registerAction("actionViewContract", actionViewContract);
         this.actionViewContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAttenTwo
        this.actionAttenTwo = new ActionAttenTwo(this);
        getActionManager().registerAction("actionAttenTwo", actionAttenTwo);
         this.actionAttenTwo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewTwo
        this.actionViewTwo = new ActionViewTwo(this);
        getActionManager().registerAction("actionViewTwo", actionViewTwo);
         this.actionViewTwo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAcctSelect
        this.actionAcctSelect = new actionAcctSelect(this);
        getActionManager().registerAction("actionAcctSelect", actionAcctSelect);
         this.actionAcctSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplitProj
        this.actionSplitProj = new actionSplitProj(this);
        getActionManager().registerAction("actionSplitProj", actionSplitProj);
         this.actionSplitProj.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplitBotUp
        this.actionSplitBotUp = new actionSplitBotUp(this);
        getActionManager().registerAction("actionSplitBotUp", actionSplitBotUp);
         this.actionSplitBotUp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplitProd
        this.actionSplitProd = new actionSplitProd(this);
        getActionManager().registerAction("actionSplitProd", actionSplitProd);
         this.actionSplitProd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImpContrSplit
        this.actionImpContrSplit = new actionImpContrSplit(this);
        getActionManager().registerAction("actionImpContrSplit", actionImpContrSplit);
         this.actionImpContrSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddSplit
        this.actionAddSplit = new actionAddSplit(this);
        getActionManager().registerAction("actionAddSplit", actionAddSplit);
         this.actionAddSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveSplitEntry
        this.actionRemoveSplitEntry = new actionRemoveSplitEntry(this);
        getActionManager().registerAction("actionRemoveSplitEntry", actionRemoveSplitEntry);
         this.actionRemoveSplitEntry.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tbpChangAudit = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contAheadDisPatch = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConductDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReaDesc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contputForwardTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contSpecialtyType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contJobType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsImportChange = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contreworkVisa = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractAmPro = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttotalChangeAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdesignChangeAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchangeEstimate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnCostIndex = new com.kingdee.bos.ctrl.swing.KDButton();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAuditType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtChangeReason = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboChangeState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pnlContent = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlSupp = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.ctnEntrys = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.ctnSuppEntrys = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtSuppEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contAheadReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConnectType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contValidator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGraphCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbxNoUse = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contDutyAmout = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNoUse = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmoutA = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAheadReason = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtConnectType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtValidator = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboGraphCount = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtDutyAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNoUse = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAmountA = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.bmptResaon = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtConductDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkbookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtReaDesc = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.txtSpecialtyType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.billType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkputForwardTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtTotalCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.conAttenTwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblAttachmentContainer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewAttachment = new com.kingdee.bos.ctrl.swing.KDButton();
        this.viewAttenTwo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contConstrSite = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOffer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtSpecialtyType = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contChangeSubject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUrgentDegree = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConstrUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConductUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDesignUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cmbAttenTwo = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cmbAttachment = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtConstrSite = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboOffer = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtChangeSubject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboUrgentDegree = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtConstrUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtConductUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDesignUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSpecialtyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtJobType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtreworkVisa = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtcontractAmPro = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txttotalChangeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtdesignChangeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtchangeEstimate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnViewContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator4 = new javax.swing.JToolBar.Separator();
        this.btnAttenTwo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRegister = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDisPatch = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemRegister = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDispatch = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuViewContract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contAuditor.setName("contAuditor");
        this.contName.setName("contName");
        this.contAuditTime.setName("contAuditTime");
        this.contCurProject.setName("contCurProject");
        this.contAuditType.setName("contAuditType");
        this.contChangeReason.setName("contChangeReason");
        this.contChangeState.setName("contChangeState");
        this.tbpChangAudit.setName("tbpChangAudit");
        this.contAheadDisPatch.setName("contAheadDisPatch");
        this.contOrg.setName("contOrg");
        this.contConductDept.setName("contConductDept");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contReaDesc.setName("contReaDesc");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.contbillType.setName("contbillType");
        this.contputForwardTime.setName("contputForwardTime");
        this.contTotalCost.setName("contTotalCost");
        this.kDPanel1.setName("kDPanel1");
        this.contSpecialtyType.setName("contSpecialtyType");
        this.contJobType.setName("contJobType");
        this.chkIsImportChange.setName("chkIsImportChange");
        this.contreworkVisa.setName("contreworkVisa");
        this.contcontractAmPro.setName("contcontractAmPro");
        this.conttotalChangeAmount.setName("conttotalChangeAmount");
        this.contdesignChangeAmount.setName("contdesignChangeAmount");
        this.contchangeEstimate.setName("contchangeEstimate");
        this.btnCostIndex.setName("btnCostIndex");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtName.setName("txtName");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtCurProject.setName("prmtCurProject");
        this.prmtAuditType.setName("prmtAuditType");
        this.prmtChangeReason.setName("prmtChangeReason");
        this.comboChangeState.setName("comboChangeState");
        this.pnlContent.setName("pnlContent");
        this.pnlSupp.setName("pnlSupp");
        this.kDPanel2.setName("kDPanel2");
        this.ctnEntrys.setName("ctnEntrys");
        this.kdtEntrys.setName("kdtEntrys");
        this.ctnSuppEntrys.setName("ctnSuppEntrys");
        this.kdtSuppEntry.setName("kdtSuppEntry");
        this.kDContainer1.setName("kDContainer1");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDPanel3.setName("kDPanel3");
        this.contAheadReason.setName("contAheadReason");
        this.contConnectType.setName("contConnectType");
        this.contValidator.setName("contValidator");
        this.contGraphCount.setName("contGraphCount");
        this.cbxNoUse.setName("cbxNoUse");
        this.contDutyAmout.setName("contDutyAmout");
        this.contNoUse.setName("contNoUse");
        this.contAmoutA.setName("contAmoutA");
        this.contReason.setName("contReason");
        this.txtAheadReason.setName("txtAheadReason");
        this.txtConnectType.setName("txtConnectType");
        this.txtValidator.setName("txtValidator");
        this.comboGraphCount.setName("comboGraphCount");
        this.txtDutyAmount.setName("txtDutyAmount");
        this.txtNoUse.setName("txtNoUse");
        this.txtAmountA.setName("txtAmountA");
        this.bmptResaon.setName("bmptResaon");
        this.txtOrg.setName("txtOrg");
        this.prmtConductDept.setName("prmtConductDept");
        this.pkbookedDate.setName("pkbookedDate");
        this.cbPeriod.setName("cbPeriod");
        this.txtReaDesc.setName("txtReaDesc");
        this.txtSpecialtyType.setName("txtSpecialtyType");
        this.billType.setName("billType");
        this.pkputForwardTime.setName("pkputForwardTime");
        this.txtTotalCost.setName("txtTotalCost");
        this.conAttenTwo.setName("conAttenTwo");
        this.lblAttachmentContainer.setName("lblAttachmentContainer");
        this.btnViewAttachment.setName("btnViewAttachment");
        this.viewAttenTwo.setName("viewAttenTwo");
        this.contConstrSite.setName("contConstrSite");
        this.contOffer.setName("contOffer");
        this.kdtSpecialtyType.setName("kdtSpecialtyType");
        this.contChangeSubject.setName("contChangeSubject");
        this.contUrgentDegree.setName("contUrgentDegree");
        this.contConstrUnit.setName("contConstrUnit");
        this.contConductUnit.setName("contConductUnit");
        this.contDesignUnit.setName("contDesignUnit");
        this.cmbAttenTwo.setName("cmbAttenTwo");
        this.cmbAttachment.setName("cmbAttachment");
        this.txtConstrSite.setName("txtConstrSite");
        this.comboOffer.setName("comboOffer");
        this.txtChangeSubject.setName("txtChangeSubject");
        this.comboUrgentDegree.setName("comboUrgentDegree");
        this.prmtConstrUnit.setName("prmtConstrUnit");
        this.prmtConductUnit.setName("prmtConductUnit");
        this.prmtDesignUnit.setName("prmtDesignUnit");
        this.prmtSpecialtyType.setName("prmtSpecialtyType");
        this.prmtJobType.setName("prmtJobType");
        this.txtreworkVisa.setName("txtreworkVisa");
        this.txtcontractAmPro.setName("txtcontractAmPro");
        this.txttotalChangeAmount.setName("txttotalChangeAmount");
        this.txtdesignChangeAmount.setName("txtdesignChangeAmount");
        this.txtchangeEstimate.setName("txtchangeEstimate");
        this.btnViewContract.setName("btnViewContract");
        this.separator4.setName("separator4");
        this.btnAttenTwo.setName("btnAttenTwo");
        this.btnRegister.setName("btnRegister");
        this.btnDisPatch.setName("btnDisPatch");
        this.menuItemRegister.setName("menuItemRegister");
        this.menuItemDispatch.setName("menuItemDispatch");
        this.menuViewContract.setName("menuViewContract");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.btnAttachment.setToolTipText(resHelper.getString("btnAttachment.toolTipText"));		
        this.chkMenuItemSubmitAndPrint.setVisible(false);		
        this.chkMenuItemSubmitAndPrint.setEnabled(false);		
        this.MenuItemAttachment.setText(resHelper.getString("MenuItemAttachment.text"));		
        this.MenuItemAttachment.setToolTipText(resHelper.getString("MenuItemAttachment.toolTipText"));		
        this.btnAddLine.setEnabled(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setEnabled(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceUp.setEnabled(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);
        // contAuditType		
        this.contAuditType.setBoundLabelText(resHelper.getString("contAuditType.boundLabelText"));		
        this.contAuditType.setBoundLabelLength(100);		
        this.contAuditType.setBoundLabelUnderline(true);		
        this.contAuditType.setVisible(false);
        // contChangeReason		
        this.contChangeReason.setBoundLabelText(resHelper.getString("contChangeReason.boundLabelText"));		
        this.contChangeReason.setBoundLabelLength(100);		
        this.contChangeReason.setBoundLabelUnderline(true);		
        this.contChangeReason.setVisible(false);
        // contChangeState		
        this.contChangeState.setBoundLabelText(resHelper.getString("contChangeState.boundLabelText"));		
        this.contChangeState.setBoundLabelLength(100);		
        this.contChangeState.setBoundLabelUnderline(true);
        // tbpChangAudit
        // contAheadDisPatch		
        this.contAheadDisPatch.setTitle(resHelper.getString("contAheadDisPatch.title"));		
        this.contAheadDisPatch.setEnableActive(false);		
        this.contAheadDisPatch.setVisible(false);
        // contOrg		
        this.contOrg.setBoundLabelText(resHelper.getString("contOrg.boundLabelText"));		
        this.contOrg.setBoundLabelLength(100);		
        this.contOrg.setBoundLabelUnderline(true);		
        this.contOrg.setVisible(false);
        // contConductDept		
        this.contConductDept.setBoundLabelText(resHelper.getString("contConductDept.boundLabelText"));		
        this.contConductDept.setBoundLabelLength(100);		
        this.contConductDept.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setVisible(false);
        // contReaDesc		
        this.contReaDesc.setBoundLabelText(resHelper.getString("contReaDesc.boundLabelText"));		
        this.contReaDesc.setBoundLabelLength(100);		
        this.contReaDesc.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setEnabled(false);		
        this.kDLabelContainer3.setVisible(false);
        // contbillType		
        this.contbillType.setBoundLabelText(resHelper.getString("contbillType.boundLabelText"));		
        this.contbillType.setBoundLabelLength(100);		
        this.contbillType.setBoundLabelUnderline(true);		
        this.contbillType.setVisible(false);
        // contputForwardTime		
        this.contputForwardTime.setBoundLabelText(resHelper.getString("contputForwardTime.boundLabelText"));		
        this.contputForwardTime.setBoundLabelLength(100);		
        this.contputForwardTime.setBoundLabelUnderline(true);		
        this.contputForwardTime.setVisible(true);
        // contTotalCost		
        this.contTotalCost.setBoundLabelText(resHelper.getString("contTotalCost.boundLabelText"));		
        this.contTotalCost.setBoundLabelLength(100);		
        this.contTotalCost.setBoundLabelUnderline(true);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));		
        this.kDPanel1.setVisible(false);
        // contSpecialtyType		
        this.contSpecialtyType.setBoundLabelText(resHelper.getString("contSpecialtyType.boundLabelText"));		
        this.contSpecialtyType.setBoundLabelLength(100);		
        this.contSpecialtyType.setBoundLabelUnderline(true);		
        this.contSpecialtyType.setVisible(false);
        // contJobType		
        this.contJobType.setBoundLabelText(resHelper.getString("contJobType.boundLabelText"));		
        this.contJobType.setBoundLabelLength(100);		
        this.contJobType.setBoundLabelUnderline(true);		
        this.contJobType.setVisible(false);
        // chkIsImportChange		
        this.chkIsImportChange.setText(resHelper.getString("chkIsImportChange.text"));
        // contreworkVisa		
        this.contreworkVisa.setBoundLabelText(resHelper.getString("contreworkVisa.boundLabelText"));		
        this.contreworkVisa.setBoundLabelLength(100);		
        this.contreworkVisa.setBoundLabelUnderline(true);
        // contcontractAmPro		
        this.contcontractAmPro.setBoundLabelText(resHelper.getString("contcontractAmPro.boundLabelText"));		
        this.contcontractAmPro.setBoundLabelLength(100);		
        this.contcontractAmPro.setBoundLabelUnderline(true);
        // conttotalChangeAmount		
        this.conttotalChangeAmount.setBoundLabelText(resHelper.getString("conttotalChangeAmount.boundLabelText"));		
        this.conttotalChangeAmount.setBoundLabelUnderline(true);		
        this.conttotalChangeAmount.setBoundLabelLength(100);
        // contdesignChangeAmount		
        this.contdesignChangeAmount.setBoundLabelText(resHelper.getString("contdesignChangeAmount.boundLabelText"));		
        this.contdesignChangeAmount.setBoundLabelUnderline(true);		
        this.contdesignChangeAmount.setBoundLabelLength(100);
        // contchangeEstimate		
        this.contchangeEstimate.setBoundLabelText(resHelper.getString("contchangeEstimate.boundLabelText"));		
        this.contchangeEstimate.setBoundLabelLength(100);		
        this.contchangeEstimate.setBoundLabelUnderline(true);
        // btnCostIndex		
        this.btnCostIndex.setText(resHelper.getString("btnCostIndex.text"));
        this.btnCostIndex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCostIndex_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$number$");		
        this.prmtAuditor.setCommitFormat("$name$");
        // txtName		
        this.txtName.setRequired(true);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // prmtCurProject		
        this.prmtCurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtCurProject.setCommitFormat("$name$");		
        this.prmtCurProject.setDisplayFormat("$name$");		
        this.prmtCurProject.setEditFormat("$name$");		
        this.prmtCurProject.setEnabled(false);
        // prmtAuditType		
        this.prmtAuditType.setDisplayFormat("$name$");		
        this.prmtAuditType.setEditFormat("$number$");		
        this.prmtAuditType.setCommitFormat("$number$");		
        this.prmtAuditType.setRequired(true);		
        this.prmtAuditType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ChangeTypeQuery");		
        this.prmtAuditType.setEditable(true);		
        this.prmtAuditType.setEnabled(false);
        this.prmtAuditType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtAuditType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtChangeReason		
        this.prmtChangeReason.setEditable(true);		
        this.prmtChangeReason.setDisplayFormat("$name$");		
        this.prmtChangeReason.setEditFormat("$number$");		
        this.prmtChangeReason.setCommitFormat("$number$");		
        this.prmtChangeReason.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ChangeReasonQuery");		
        this.prmtChangeReason.setRequired(true);
        // comboChangeState		
        this.comboChangeState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.ChangeBillStateEnum").toArray());		
        this.comboChangeState.setEnabled(false);
        // pnlContent		
        this.pnlContent.setAutoscrolls(true);
        // pnlSupp		
        this.pnlSupp.setAutoscrolls(true);
        // kDPanel2
        // ctnEntrys		
        this.ctnEntrys.setTitle(resHelper.getString("ctnEntrys.title"));		
        this.ctnEntrys.setAutoscrolls(true);		
        this.ctnEntrys.setEnableActive(false);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"changeContent\" t:width=\"750\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"isBack\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"seq\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{changeContent}</t:Cell><t:Cell>$Resource{isBack}</t:Cell><t:Cell>$Resource{seq}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEntrys_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","number","changeContent","isBack","seq"});


        // ctnSuppEntrys		
        this.ctnSuppEntrys.setTitle(resHelper.getString("ctnSuppEntrys.title"));		
        this.ctnSuppEntrys.setEnableActive(false);
        // kdtSuppEntry
		String kdtSuppEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"supp\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"thing\" t:width=\"140\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"name\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"content\" t:width=\"500\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{supp}</t:Cell><t:Cell>$Resource{thing}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{content}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtSuppEntry.setFormatXml(resHelper.translateString("kdtSuppEntry",kdtSuppEntryStrXML));
        this.kdtSuppEntry.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtSuppEntry_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtSuppEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtSuppEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtSuppEntry_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kDContainer1		
        this.kDContainer1.setEnableActive(false);		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDTabbedPane1
        // kDPanel3
        // contAheadReason		
        this.contAheadReason.setBoundLabelText(resHelper.getString("contAheadReason.boundLabelText"));		
        this.contAheadReason.setBoundLabelLength(100);		
        this.contAheadReason.setBoundLabelUnderline(true);
        // contConnectType		
        this.contConnectType.setBoundLabelText(resHelper.getString("contConnectType.boundLabelText"));		
        this.contConnectType.setBoundLabelLength(100);		
        this.contConnectType.setBoundLabelUnderline(true);
        // contValidator		
        this.contValidator.setBoundLabelText(resHelper.getString("contValidator.boundLabelText"));		
        this.contValidator.setBoundLabelLength(100);		
        this.contValidator.setBoundLabelUnderline(true);
        // contGraphCount		
        this.contGraphCount.setBoundLabelText(resHelper.getString("contGraphCount.boundLabelText"));		
        this.contGraphCount.setBoundLabelLength(100);		
        this.contGraphCount.setBoundLabelUnderline(true);		
        this.contGraphCount.setPreferredSize(new Dimension(270,19));		
        this.contGraphCount.setVisible(false);
        // cbxNoUse		
        this.cbxNoUse.setText(resHelper.getString("cbxNoUse.text"));		
        this.cbxNoUse.setVisible(false);
        this.cbxNoUse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cbxNoUse_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contDutyAmout		
        this.contDutyAmout.setBoundLabelText(resHelper.getString("contDutyAmout.boundLabelText"));		
        this.contDutyAmout.setBoundLabelLength(100);		
        this.contDutyAmout.setBoundLabelUnderline(true);		
        this.contDutyAmout.setEnabled(false);		
        this.contDutyAmout.setVisible(false);
        // contNoUse		
        this.contNoUse.setBoundLabelText(resHelper.getString("contNoUse.boundLabelText"));		
        this.contNoUse.setBoundLabelLength(100);		
        this.contNoUse.setBoundLabelUnderline(true);		
        this.contNoUse.setVisible(false);
        // contAmoutA		
        this.contAmoutA.setBoundLabelText(resHelper.getString("contAmoutA.boundLabelText"));		
        this.contAmoutA.setBoundLabelLength(100);		
        this.contAmoutA.setBoundLabelUnderline(true);		
        this.contAmoutA.setVisible(false);
        // contReason		
        this.contReason.setBoundLabelText(resHelper.getString("contReason.boundLabelText"));		
        this.contReason.setBoundLabelLength(100);		
        this.contReason.setBoundLabelUnderline(true);		
        this.contReason.setVisible(false);
        // txtAheadReason		
        this.txtAheadReason.setEnabled(false);
        // txtConnectType		
        this.txtConnectType.setEnabled(false);
        // txtValidator		
        this.txtValidator.setEnabled(false);
        // comboGraphCount		
        this.comboGraphCount.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.GraphCountEnum").toArray());		
        this.comboGraphCount.setRequired(true);
        // txtDutyAmount		
        this.txtDutyAmount.setDataType(1);		
        this.txtDutyAmount.setPrecision(2);		
        this.txtDutyAmount.setEnabled(false);
        // txtNoUse		
        this.txtNoUse.setDataType(1);		
        this.txtNoUse.setPrecision(2);
        // txtAmountA		
        this.txtAmountA.setDataType(1);		
        this.txtAmountA.setPrecision(2);
        // bmptResaon		
        this.bmptResaon.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7InvalidCostReasonQuery");		
        this.bmptResaon.setCommitFormat("$number$");		
        this.bmptResaon.setEditFormat("$number$");		
        this.bmptResaon.setDisplayFormat("$name$");
        // txtOrg		
        this.txtOrg.setMaxLength(80);		
        this.txtOrg.setRequired(true);		
        this.txtOrg.setEditable(false);
        // prmtConductDept		
        this.prmtConductDept.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.prmtConductDept.setCommitFormat("$number$");		
        this.prmtConductDept.setDisplayFormat("$name$");		
        this.prmtConductDept.setEditFormat("$number$");		
        this.prmtConductDept.setRequired(true);		
        this.prmtConductDept.setEditable(true);
        // pkbookedDate
        this.pkbookedDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkbookedDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cbPeriod		
        this.cbPeriod.setEnabled(false);
        // txtReaDesc
        // txtSpecialtyType		
        this.txtSpecialtyType.setMaxLength(80);		
        this.txtSpecialtyType.setVisible(false);
        // billType		
        this.billType.setVisible(true);		
        this.billType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.ChangeAuditBillType").toArray());		
        this.billType.setRequired(false);		
        this.billType.setEnabled(false);
        // pkputForwardTime		
        this.pkputForwardTime.setVisible(true);		
        this.pkputForwardTime.setRequired(false);
        // txtTotalCost		
        this.txtTotalCost.setDataType(1);		
        this.txtTotalCost.setPrecision(2);		
        this.txtTotalCost.setEnabled(false);
        // conAttenTwo		
        this.conAttenTwo.setBoundLabelText(resHelper.getString("conAttenTwo.boundLabelText"));		
        this.conAttenTwo.setBoundLabelLength(100);
        // lblAttachmentContainer		
        this.lblAttachmentContainer.setBoundLabelText(resHelper.getString("lblAttachmentContainer.boundLabelText"));		
        this.lblAttachmentContainer.setBoundLabelLength(100);		
        this.lblAttachmentContainer.setBoundLabelUnderline(true);
        // btnViewAttachment
        this.btnViewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAttachment.setText(resHelper.getString("btnViewAttachment.text"));
        // viewAttenTwo
        this.viewAttenTwo.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewTwo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.viewAttenTwo.setText(resHelper.getString("viewAttenTwo.text"));
        // contConstrSite		
        this.contConstrSite.setBoundLabelText(resHelper.getString("contConstrSite.boundLabelText"));		
        this.contConstrSite.setBoundLabelLength(100);		
        this.contConstrSite.setBoundLabelUnderline(true);
        // contOffer		
        this.contOffer.setBoundLabelText(resHelper.getString("contOffer.boundLabelText"));		
        this.contOffer.setBoundLabelLength(100);		
        this.contOffer.setBoundLabelUnderline(true);
        // kdtSpecialtyType
		String kdtSpecialtyTypeStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"specialtyTypeID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{specialtyTypeID}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtSpecialtyType.setFormatXml(resHelper.translateString("kdtSpecialtyType",kdtSpecialtyTypeStrXML));		
        this.kdtSpecialtyType.setVisible(false);

                this.kdtSpecialtyType.putBindContents("editData",new String[] {"id","specialtyType"});


        // contChangeSubject		
        this.contChangeSubject.setBoundLabelText(resHelper.getString("contChangeSubject.boundLabelText"));		
        this.contChangeSubject.setBoundLabelLength(100);		
        this.contChangeSubject.setBoundLabelUnderline(true);
        // contUrgentDegree		
        this.contUrgentDegree.setBoundLabelText(resHelper.getString("contUrgentDegree.boundLabelText"));		
        this.contUrgentDegree.setBoundLabelLength(100);		
        this.contUrgentDegree.setBoundLabelUnderline(true);
        // contConstrUnit		
        this.contConstrUnit.setBoundLabelText(resHelper.getString("contConstrUnit.boundLabelText"));		
        this.contConstrUnit.setBoundLabelLength(100);		
        this.contConstrUnit.setBoundLabelUnderline(true);
        // contConductUnit		
        this.contConductUnit.setBoundLabelText(resHelper.getString("contConductUnit.boundLabelText"));		
        this.contConductUnit.setBoundLabelLength(100);		
        this.contConductUnit.setBoundLabelUnderline(true);
        // contDesignUnit		
        this.contDesignUnit.setBoundLabelText(resHelper.getString("contDesignUnit.boundLabelText"));		
        this.contDesignUnit.setBoundLabelLength(100);		
        this.contDesignUnit.setBoundLabelUnderline(true);
        // cmbAttenTwo
        // cmbAttachment
        // txtConstrSite
        // comboOffer		
        this.comboOffer.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.OfferEnum").toArray());
        this.comboOffer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboOffer_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtChangeSubject		
        this.txtChangeSubject.setMaxLength(80);
        // comboUrgentDegree		
        this.comboUrgentDegree.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum").toArray());		
        this.comboUrgentDegree.setRequired(true);
        // prmtConstrUnit		
        this.prmtConstrUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");		
        this.prmtConstrUnit.setCommitFormat("$number$");		
        this.prmtConstrUnit.setEditFormat("$number$");		
        this.prmtConstrUnit.setDisplayFormat("$number$ $name$");
        // prmtConductUnit		
        this.prmtConductUnit.setDisplayFormat("$number$ $name$");		
        this.prmtConductUnit.setEditFormat("$number$");		
        this.prmtConductUnit.setCommitFormat("$number$");		
        this.prmtConductUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");
        // prmtDesignUnit		
        this.prmtDesignUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");		
        this.prmtDesignUnit.setCommitFormat("$number$");		
        this.prmtDesignUnit.setEditFormat("$number$");		
        this.prmtDesignUnit.setDisplayFormat("$number$ $name$");
        // prmtSpecialtyType		
        this.prmtSpecialtyType.setDisplayFormat("$name$");		
        this.prmtSpecialtyType.setEditFormat("$number$");		
        this.prmtSpecialtyType.setRequired(true);		
        this.prmtSpecialtyType.setCommitFormat("$number$");		
        this.prmtSpecialtyType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7SpecialtyTypeQuery");		
        this.prmtSpecialtyType.setEditable(true);		
        this.prmtSpecialtyType.setEnabledMultiSelection(true);
        this.prmtSpecialtyType.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtSpecialtyType_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtSpecialtyType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSpecialtyType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtJobType		
        this.prmtJobType.setDisplayFormat("$name$");		
        this.prmtJobType.setEditFormat("$number$");		
        this.prmtJobType.setRequired(true);		
        this.prmtJobType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7JobTypeQuery");		
        this.prmtJobType.setEditable(true);		
        this.prmtJobType.setCommitFormat("$number$");
        // txtreworkVisa		
        this.txtreworkVisa.setPrecision(2);		
        this.txtreworkVisa.setDataType(1);
        this.txtreworkVisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtreworkVisa_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtcontractAmPro		
        this.txtcontractAmPro.setDataType(1);		
        this.txtcontractAmPro.setPrecision(2);		
        this.txtcontractAmPro.setEnabled(false);
        // txttotalChangeAmount		
        this.txttotalChangeAmount.setDataType(1);		
        this.txttotalChangeAmount.setPrecision(2);		
        this.txttotalChangeAmount.setEnabled(false);
        // txtdesignChangeAmount		
        this.txtdesignChangeAmount.setPrecision(2);		
        this.txtdesignChangeAmount.setDataType(1);		
        this.txtdesignChangeAmount.setEnabled(false);
        // txtchangeEstimate
        // btnViewContract
        this.btnViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContract.setText(resHelper.getString("btnViewContract.text"));		
        this.btnViewContract.setToolTipText(resHelper.getString("btnViewContract.toolTipText"));
        // separator4
        // btnAttenTwo
        this.btnAttenTwo.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttenTwo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttenTwo.setText(resHelper.getString("btnAttenTwo.text"));
        // btnRegister
        this.btnRegister.setAction((IItemAction)ActionProxyFactory.getProxy(actionRegister, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRegister.setText(resHelper.getString("btnRegister.text"));		
        this.btnRegister.setToolTipText(resHelper.getString("btnRegister.toolTipText"));
        // btnDisPatch
        this.btnDisPatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisPatch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisPatch.setText(resHelper.getString("btnDisPatch.text"));
        // menuItemRegister
        this.menuItemRegister.setAction((IItemAction)ActionProxyFactory.getProxy(actionRegister, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRegister.setText(resHelper.getString("menuItemRegister.text"));
        // menuItemDispatch
        this.menuItemDispatch.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisPatch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDispatch.setText(resHelper.getString("menuItemDispatch.text"));
        // menuViewContract
        this.menuViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuViewContract.setText(resHelper.getString("menuViewContract.text"));		
        this.menuViewContract.setToolTipText(resHelper.getString("menuViewContract.toolTipText"));
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
        kDSeparator6.setBounds(new Rectangle(8, 531, 990, 5));
        this.add(kDSeparator6, new KDLayout.Constraints(8, 531, 990, 5, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator7.setBounds(new Rectangle(8, 183, 990, 8));
        this.add(kDSeparator7, new KDLayout.Constraints(8, 183, 990, 8, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(9, 539, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(9, 539, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(9, 563, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(9, 563, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(12, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(12, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(377, 536, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(377, 536, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(377, 36, 270, 19));
        this.add(contName, new KDLayout.Constraints(377, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(377, 563, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(377, 563, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurProject.setBounds(new Rectangle(730, 12, 270, 19));
        this.add(contCurProject, new KDLayout.Constraints(730, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditType.setBounds(new Rectangle(716, 157, 270, 19));
        this.add(contAuditType, new KDLayout.Constraints(716, 157, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChangeReason.setBounds(new Rectangle(12, 208, 270, 19));
        this.add(contChangeReason, new KDLayout.Constraints(12, 208, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChangeState.setBounds(new Rectangle(730, 36, 270, 19));
        this.add(contChangeState, new KDLayout.Constraints(730, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        tbpChangAudit.setBounds(new Rectangle(9, 196, 991, 332));
        this.add(tbpChangAudit, new KDLayout.Constraints(9, 196, 991, 332, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAheadDisPatch.setBounds(new Rectangle(623, 588, 991, 27));
        this.add(contAheadDisPatch, new KDLayout.Constraints(623, 588, 991, 27, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrg.setBounds(new Rectangle(712, 166, 270, 19));
        this.add(contOrg, new KDLayout.Constraints(712, 166, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contConductDept.setBounds(new Rectangle(12, 60, 270, 19));
        this.add(contConductDept, new KDLayout.Constraints(12, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(12, 36, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(12, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(721, 124, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(721, 124, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contReaDesc.setBounds(new Rectangle(12, 133, 635, 43));
        this.add(contReaDesc, new KDLayout.Constraints(12, 133, 635, 43, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(659, 211, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(659, 211, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbillType.setBounds(new Rectangle(718, 141, 270, 19));
        this.add(contbillType, new KDLayout.Constraints(718, 141, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contputForwardTime.setBounds(new Rectangle(377, 60, 270, 19));
        this.add(contputForwardTime, new KDLayout.Constraints(377, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTotalCost.setBounds(new Rectangle(12, 84, 270, 19));
        this.add(contTotalCost, new KDLayout.Constraints(12, 84, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel1.setBounds(new Rectangle(937, 234, 67, 241));
        this.add(kDPanel1, new KDLayout.Constraints(937, 234, 67, 241, 0));
        contSpecialtyType.setBounds(new Rectangle(377, 208, 270, 19));
        this.add(contSpecialtyType, new KDLayout.Constraints(377, 208, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contJobType.setBounds(new Rectangle(730, 208, 270, 19));
        this.add(contJobType, new KDLayout.Constraints(730, 208, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsImportChange.setBounds(new Rectangle(730, 108, 146, 19));
        this.add(chkIsImportChange, new KDLayout.Constraints(730, 108, 146, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contreworkVisa.setBounds(new Rectangle(377, 84, 270, 19));
        this.add(contreworkVisa, new KDLayout.Constraints(377, 84, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractAmPro.setBounds(new Rectangle(377, 108, 270, 19));
        this.add(contcontractAmPro, new KDLayout.Constraints(377, 108, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttotalChangeAmount.setBounds(new Rectangle(730, 84, 270, 19));
        this.add(conttotalChangeAmount, new KDLayout.Constraints(730, 84, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contdesignChangeAmount.setBounds(new Rectangle(12, 108, 270, 19));
        this.add(contdesignChangeAmount, new KDLayout.Constraints(12, 108, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contchangeEstimate.setBounds(new Rectangle(730, 60, 270, 19));
        this.add(contchangeEstimate, new KDLayout.Constraints(730, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnCostIndex.setBounds(new Rectangle(547, 10, 100, 21));
        this.add(btnCostIndex, new KDLayout.Constraints(547, 10, 100, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contName
        contName.setBoundEditor(txtName);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurProject);
        //contAuditType
        contAuditType.setBoundEditor(prmtAuditType);
        //contChangeReason
        contChangeReason.setBoundEditor(prmtChangeReason);
        //contChangeState
        contChangeState.setBoundEditor(comboChangeState);
        //tbpChangAudit
        tbpChangAudit.add(pnlContent, resHelper.getString("pnlContent.constraints"));
        tbpChangAudit.add(pnlSupp, resHelper.getString("pnlSupp.constraints"));
        tbpChangAudit.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //pnlContent
pnlContent.setLayout(new BorderLayout(0, 0));        pnlContent.add(ctnEntrys, BorderLayout.CENTER);
        //ctnEntrys
ctnEntrys.getContentPane().setLayout(new BorderLayout(0, 0));        ctnEntrys.getContentPane().add(kdtEntrys, BorderLayout.CENTER);
        //pnlSupp
pnlSupp.setLayout(new BorderLayout(0, 0));        pnlSupp.add(ctnSuppEntrys, BorderLayout.CENTER);
        //ctnSuppEntrys
ctnSuppEntrys.getContentPane().setLayout(new BorderLayout(0, 0));        ctnSuppEntrys.getContentPane().add(kdtSuppEntry, BorderLayout.CENTER);
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kDPanel2.add(kDContainer1, BorderLayout.CENTER);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDTabbedPane1, BorderLayout.CENTER);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
kDPanel3.setLayout(new BorderLayout(0, 0));        //contAheadDisPatch
        contAheadDisPatch.getContentPane().setLayout(new KDLayout());
        contAheadDisPatch.getContentPane().putClientProperty("OriginalBounds", new Rectangle(623, 588, 991, 27));        contAheadReason.setBounds(new Rectangle(740, 1, 400, 19));
        contAheadDisPatch.getContentPane().add(contAheadReason, new KDLayout.Constraints(740, 1, 400, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contConnectType.setBounds(new Rectangle(290, 2, 270, 19));
        contAheadDisPatch.getContentPane().add(contConnectType, new KDLayout.Constraints(290, 2, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contValidator.setBounds(new Rectangle(0, 2, 270, 19));
        contAheadDisPatch.getContentPane().add(contValidator, new KDLayout.Constraints(0, 2, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contGraphCount.setBounds(new Rectangle(7, 23, 270, 19));
        contAheadDisPatch.getContentPane().add(contGraphCount, new KDLayout.Constraints(7, 23, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        cbxNoUse.setBounds(new Rectangle(254, 21, 270, 19));
        contAheadDisPatch.getContentPane().add(cbxNoUse, new KDLayout.Constraints(254, 21, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDutyAmout.setBounds(new Rectangle(388, 15, 270, 19));
        contAheadDisPatch.getContentPane().add(contDutyAmout, new KDLayout.Constraints(388, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNoUse.setBounds(new Rectangle(632, 19, 270, 19));
        contAheadDisPatch.getContentPane().add(contNoUse, new KDLayout.Constraints(632, 19, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmoutA.setBounds(new Rectangle(891, 13, 270, 19));
        contAheadDisPatch.getContentPane().add(contAmoutA, new KDLayout.Constraints(891, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contReason.setBounds(new Rectangle(615, 2, 270, 19));
        contAheadDisPatch.getContentPane().add(contReason, new KDLayout.Constraints(615, 2, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contAheadReason
        contAheadReason.setBoundEditor(txtAheadReason);
        //contConnectType
        contConnectType.setBoundEditor(txtConnectType);
        //contValidator
        contValidator.setBoundEditor(txtValidator);
        //contGraphCount
        contGraphCount.setBoundEditor(comboGraphCount);
        //contDutyAmout
        contDutyAmout.setBoundEditor(txtDutyAmount);
        //contNoUse
        contNoUse.setBoundEditor(txtNoUse);
        //contAmoutA
        contAmoutA.setBoundEditor(txtAmountA);
        //contReason
        contReason.setBoundEditor(bmptResaon);
        //contOrg
        contOrg.setBoundEditor(txtOrg);
        //contConductDept
        contConductDept.setBoundEditor(prmtConductDept);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(pkbookedDate);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(cbPeriod);
        //contReaDesc
        contReaDesc.setBoundEditor(txtReaDesc);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtSpecialtyType);
        //contbillType
        contbillType.setBoundEditor(billType);
        //contputForwardTime
        contputForwardTime.setBoundEditor(pkputForwardTime);
        //contTotalCost
        contTotalCost.setBoundEditor(txtTotalCost);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(937, 234, 67, 241));        conAttenTwo.setBounds(new Rectangle(12, 35, 379, 19));
        kDPanel1.add(conAttenTwo, new KDLayout.Constraints(12, 35, 379, 19, 0));
        lblAttachmentContainer.setBounds(new Rectangle(12, 10, 379, 19));
        kDPanel1.add(lblAttachmentContainer, new KDLayout.Constraints(12, 10, 379, 19, 0));
        btnViewAttachment.setBounds(new Rectangle(400, 14, 101, 21));
        kDPanel1.add(btnViewAttachment, new KDLayout.Constraints(400, 14, 101, 21, 0));
        viewAttenTwo.setBounds(new Rectangle(400, 39, 101, 21));
        kDPanel1.add(viewAttenTwo, new KDLayout.Constraints(400, 39, 101, 21, 0));
        contConstrSite.setBounds(new Rectangle(499, 11, 270, 19));
        kDPanel1.add(contConstrSite, new KDLayout.Constraints(499, 11, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOffer.setBounds(new Rectangle(506, 33, 270, 19));
        kDPanel1.add(contOffer, new KDLayout.Constraints(506, 33, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtSpecialtyType.setBounds(new Rectangle(72, -8, 68, 140));
        kDPanel1.add(kdtSpecialtyType, new KDLayout.Constraints(72, -8, 68, 140, 0));
        contChangeSubject.setBounds(new Rectangle(154, 56, 270, 19));
        kDPanel1.add(contChangeSubject, new KDLayout.Constraints(154, 56, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contUrgentDegree.setBounds(new Rectangle(429, 52, 270, 19));
        kDPanel1.add(contUrgentDegree, new KDLayout.Constraints(429, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contConstrUnit.setBounds(new Rectangle(435, 69, 270, 19));
        kDPanel1.add(contConstrUnit, new KDLayout.Constraints(435, 69, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contConductUnit.setBounds(new Rectangle(154, 68, 270, 19));
        kDPanel1.add(contConductUnit, new KDLayout.Constraints(154, 68, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDesignUnit.setBounds(new Rectangle(709, 70, 270, 19));
        kDPanel1.add(contDesignUnit, new KDLayout.Constraints(709, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //conAttenTwo
        conAttenTwo.setBoundEditor(cmbAttenTwo);
        //lblAttachmentContainer
        lblAttachmentContainer.setBoundEditor(cmbAttachment);
        //contConstrSite
        contConstrSite.setBoundEditor(txtConstrSite);
        //contOffer
        contOffer.setBoundEditor(comboOffer);
        //contChangeSubject
        contChangeSubject.setBoundEditor(txtChangeSubject);
        //contUrgentDegree
        contUrgentDegree.setBoundEditor(comboUrgentDegree);
        //contConstrUnit
        contConstrUnit.setBoundEditor(prmtConstrUnit);
        //contConductUnit
        contConductUnit.setBoundEditor(prmtConductUnit);
        //contDesignUnit
        contDesignUnit.setBoundEditor(prmtDesignUnit);
        //contSpecialtyType
        contSpecialtyType.setBoundEditor(prmtSpecialtyType);
        //contJobType
        contJobType.setBoundEditor(prmtJobType);
        //contreworkVisa
        contreworkVisa.setBoundEditor(txtreworkVisa);
        //contcontractAmPro
        contcontractAmPro.setBoundEditor(txtcontractAmPro);
        //conttotalChangeAmount
        conttotalChangeAmount.setBoundEditor(txttotalChangeAmount);
        //contdesignChangeAmount
        contdesignChangeAmount.setBoundEditor(txtdesignChangeAmount);
        //contchangeEstimate
        contchangeEstimate.setBoundEditor(txtchangeEstimate);

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
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemRegister);
        menuBiz.add(menuItemDispatch);
        menuBiz.add(menuViewContract);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnViewContract);
        this.toolBar.add(separator4);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnAttenTwo);
        this.toolBar.add(btnRegister);
        this.toolBar.add(btnDisPatch);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isImportChange", boolean.class, this.chkIsImportChange, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtCurProject, "data");
		dataBinder.registerBinding("auditType", com.kingdee.eas.fdc.basedata.ChangeTypeInfo.class, this.prmtAuditType, "data");
		dataBinder.registerBinding("changeReason", com.kingdee.eas.fdc.basedata.ChangeReasonInfo.class, this.prmtChangeReason, "data");
		dataBinder.registerBinding("changeState", com.kingdee.eas.fdc.contract.ChangeBillStateEnum.class, this.comboChangeState, "selectedItem");
		dataBinder.registerBinding("entrys.changeContent", String.class, this.kdtEntrys, "changeContent.text");
		dataBinder.registerBinding("entrys.isBack", boolean.class, this.kdtEntrys, "isBack.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.number", String.class, this.kdtEntrys, "number.text");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys.seq", int.class, this.kdtEntrys, "seq.text");
		dataBinder.registerBinding("isNoUse", boolean.class, this.cbxNoUse, "selected");
		dataBinder.registerBinding("aheadReason", String.class, this.txtAheadReason, "text");
		dataBinder.registerBinding("connectType", String.class, this.txtConnectType, "text");
		dataBinder.registerBinding("validator", String.class, this.txtValidator, "text");
		dataBinder.registerBinding("graphCount", com.kingdee.eas.fdc.contract.GraphCountEnum.class, this.comboGraphCount, "selectedItem");
		dataBinder.registerBinding("amountDutySupp", java.math.BigDecimal.class, this.txtDutyAmount, "value");
		dataBinder.registerBinding("costNouse", java.math.BigDecimal.class, this.txtNoUse, "value");
		dataBinder.registerBinding("amountA", java.math.BigDecimal.class, this.txtAmountA, "value");
		dataBinder.registerBinding("invalidCostReason", com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo.class, this.bmptResaon, "data");
		dataBinder.registerBinding("conductDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtConductDept, "data");
		dataBinder.registerBinding("bookedDate", java.util.Date.class, this.pkbookedDate, "value");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.cbPeriod, "data");
		dataBinder.registerBinding("reaDesc", String.class, this.txtReaDesc, "_multiLangItem");
		dataBinder.registerBinding("specialName", String.class, this.txtSpecialtyType, "text");
		dataBinder.registerBinding("billType", com.kingdee.eas.fdc.contract.ChangeAuditBillType.class, this.billType, "selectedItem");
		dataBinder.registerBinding("putForwardTime", java.util.Date.class, this.pkputForwardTime, "value");
		dataBinder.registerBinding("totalCost", java.math.BigDecimal.class, this.txtTotalCost, "value");
		dataBinder.registerBinding("specialtyTypeEntry.specialtyType", com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo.class, this.kdtSpecialtyType, "specialtyTypeID.text");
		dataBinder.registerBinding("specialtyTypeEntry", com.kingdee.eas.fdc.contract.SpecialtyTypeEntryInfo.class, this.kdtSpecialtyType, "userObject");
		dataBinder.registerBinding("specialtyTypeEntry.id", com.kingdee.bos.util.BOSUuid.class, this.kdtSpecialtyType, "id.text");
		dataBinder.registerBinding("constrSite", String.class, this.txtConstrSite, "text");
		dataBinder.registerBinding("offer", com.kingdee.eas.fdc.contract.OfferEnum.class, this.comboOffer, "selectedItem");
		dataBinder.registerBinding("changeSubject", String.class, this.txtChangeSubject, "text");
		dataBinder.registerBinding("urgentDegree", com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum.class, this.comboUrgentDegree, "selectedItem");
		dataBinder.registerBinding("constrUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtConstrUnit, "data");
		dataBinder.registerBinding("conductUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtConductUnit, "data");
		dataBinder.registerBinding("designUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtDesignUnit, "data");
		dataBinder.registerBinding("specialtyType", com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo.class, this.prmtSpecialtyType, "data");
		dataBinder.registerBinding("jobType", com.kingdee.eas.fdc.basedata.JobTypeInfo.class, this.prmtJobType, "data");
		dataBinder.registerBinding("reworkVisa", java.math.BigDecimal.class, this.txtreworkVisa, "value");
		dataBinder.registerBinding("contractAmPro", java.math.BigDecimal.class, this.txtcontractAmPro, "value");
		dataBinder.registerBinding("totalChangeAmount", java.math.BigDecimal.class, this.txttotalChangeAmount, "value");
		dataBinder.registerBinding("designChangeAmount", java.math.BigDecimal.class, this.txtdesignChangeAmount, "value");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionAddLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionRemoveLine, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionAddSupp, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.actionDelSupp, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionUnAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAddSupp, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionDelSupp, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.DesignChangeAuditEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.ChangeAuditBillInfo)ov;
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
		getValidateHelper().registerBindProperty("isImportChange", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.changeContent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.isBack", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isNoUse", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aheadReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("connectType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("validator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("graphCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountDutySupp", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costNouse", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountA", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invalidCostReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conductDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reaDesc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("specialName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("putForwardTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("specialtyTypeEntry.specialtyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("specialtyTypeEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("specialtyTypeEntry.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("constrSite", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("offer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeSubject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("urgentDegree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("constrUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conductUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("designUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("specialtyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jobType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reworkVisa", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractAmPro", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalChangeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("designChangeAmount", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.actionAddLine.setEnabled(false);
		            this.actionRemoveLine.setEnabled(false);
		            this.actionAddSupp.setEnabled(false);
		            this.actionDelSupp.setEnabled(false);
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		            this.actionAudit.setEnabled(false);
		            this.actionUnAudit.setEnabled(false);
		            this.actionAddSupp.setEnabled(false);
		            this.actionDelSupp.setEnabled(false);
        }
    }

    /**
     * output btnCostIndex_actionPerformed method
     */
    protected void btnCostIndex_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code hereaa
    }

    /**
     * output prmtAuditType_dataChanged method
     */
    protected void prmtAuditType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_tableClicked method
     */
    protected void kdtEntrys_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtSuppEntry_editStopped method
     */
    protected void kdtSuppEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtSuppEntry_editStopping method
     */
    protected void kdtSuppEntry_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtSuppEntry_tableClicked method
     */
    protected void kdtSuppEntry_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output cbxNoUse_actionPerformed method
     */
    protected void cbxNoUse_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output pkbookedDate_dataChanged method
     */
    protected void pkbookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboOffer_itemStateChanged method
     */
    protected void comboOffer_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtSpecialtyType_willShow method
     */
    protected void prmtSpecialtyType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtSpecialtyType_dataChanged method
     */
    protected void prmtSpecialtyType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtreworkVisa_actionPerformed method
     */
    protected void txtreworkVisa_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here1
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
        sic.add(new SelectorItemInfo("isImportChange"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("auditTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("curProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("curProject.id"));
        	sic.add(new SelectorItemInfo("curProject.number"));
        	sic.add(new SelectorItemInfo("curProject.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditType.id"));
        	sic.add(new SelectorItemInfo("auditType.number"));
        	sic.add(new SelectorItemInfo("auditType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("changeReason.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("changeReason.id"));
        	sic.add(new SelectorItemInfo("changeReason.number"));
        	sic.add(new SelectorItemInfo("changeReason.name"));
		}
        sic.add(new SelectorItemInfo("changeState"));
    	sic.add(new SelectorItemInfo("entrys.changeContent"));
    	sic.add(new SelectorItemInfo("entrys.isBack"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("entrys.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.id"));
    	sic.add(new SelectorItemInfo("entrys.seq"));
        sic.add(new SelectorItemInfo("isNoUse"));
        sic.add(new SelectorItemInfo("aheadReason"));
        sic.add(new SelectorItemInfo("connectType"));
        sic.add(new SelectorItemInfo("validator"));
        sic.add(new SelectorItemInfo("graphCount"));
        sic.add(new SelectorItemInfo("amountDutySupp"));
        sic.add(new SelectorItemInfo("costNouse"));
        sic.add(new SelectorItemInfo("amountA"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("invalidCostReason.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("invalidCostReason.id"));
        	sic.add(new SelectorItemInfo("invalidCostReason.number"));
        	sic.add(new SelectorItemInfo("invalidCostReason.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("conductDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("conductDept.id"));
        	sic.add(new SelectorItemInfo("conductDept.number"));
        	sic.add(new SelectorItemInfo("conductDept.name"));
		}
        sic.add(new SelectorItemInfo("bookedDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("period.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("period.id"));
        	sic.add(new SelectorItemInfo("period.number"));
		}
        sic.add(new SelectorItemInfo("reaDesc"));
        sic.add(new SelectorItemInfo("specialName"));
        sic.add(new SelectorItemInfo("billType"));
        sic.add(new SelectorItemInfo("putForwardTime"));
        sic.add(new SelectorItemInfo("totalCost"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("specialtyTypeEntry.specialtyType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("specialtyTypeEntry.specialtyType.id"));
			sic.add(new SelectorItemInfo("specialtyTypeEntry.specialtyType.name"));
        	sic.add(new SelectorItemInfo("specialtyTypeEntry.specialtyType.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("specialtyTypeEntry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("specialtyTypeEntry.id"));
        sic.add(new SelectorItemInfo("constrSite"));
        sic.add(new SelectorItemInfo("offer"));
        sic.add(new SelectorItemInfo("changeSubject"));
        sic.add(new SelectorItemInfo("urgentDegree"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("constrUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("constrUnit.id"));
        	sic.add(new SelectorItemInfo("constrUnit.number"));
        	sic.add(new SelectorItemInfo("constrUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("conductUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("conductUnit.id"));
        	sic.add(new SelectorItemInfo("conductUnit.number"));
        	sic.add(new SelectorItemInfo("conductUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("designUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("designUnit.id"));
        	sic.add(new SelectorItemInfo("designUnit.number"));
        	sic.add(new SelectorItemInfo("designUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("specialtyType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("specialtyType.id"));
        	sic.add(new SelectorItemInfo("specialtyType.number"));
        	sic.add(new SelectorItemInfo("specialtyType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("jobType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("jobType.id"));
        	sic.add(new SelectorItemInfo("jobType.number"));
        	sic.add(new SelectorItemInfo("jobType.name"));
		}
        sic.add(new SelectorItemInfo("reworkVisa"));
        sic.add(new SelectorItemInfo("contractAmPro"));
        sic.add(new SelectorItemInfo("totalChangeAmount"));
        sic.add(new SelectorItemInfo("designChangeAmount"));
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
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
     * output actionAddSupp_actionPerformed method
     */
    public void actionAddSupp_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelSupp_actionPerformed method
     */
    public void actionDelSupp_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRegister_actionPerformed method
     */
    public void actionRegister_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisPatch_actionPerformed method
     */
    public void actionDisPatch_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAheadDisPatch_actionPerformed method
     */
    public void actionAheadDisPatch_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewAttachment_actionPerformed method
     */
    public void actionViewAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContract_actionPerformed method
     */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAttenTwo_actionPerformed method
     */
    public void actionAttenTwo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewTwo_actionPerformed method
     */
    public void actionViewTwo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAcctSelect_actionPerformed method
     */
    public void actionAcctSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplitProj_actionPerformed method
     */
    public void actionSplitProj_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplitBotUp_actionPerformed method
     */
    public void actionSplitBotUp_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplitProd_actionPerformed method
     */
    public void actionSplitProd_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImpContrSplit_actionPerformed method
     */
    public void actionImpContrSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddSplit_actionPerformed method
     */
    public void actionAddSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveSplitEntry_actionPerformed method
     */
    public void actionRemoveSplitEntry_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSave(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAttachment(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachment() {
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
	public RequestContext prepareActionAddSupp(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddSupp() {
    	return false;
    }
	public RequestContext prepareActionDelSupp(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelSupp() {
    	return false;
    }
	public RequestContext prepareActionRegister(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRegister() {
    	return false;
    }
	public RequestContext prepareActionDisPatch(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDisPatch() {
    	return false;
    }
	public RequestContext prepareActionAheadDisPatch(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAheadDisPatch() {
    	return false;
    }
	public RequestContext prepareActionViewAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewAttachment() {
    	return false;
    }
	public RequestContext prepareActionViewContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContract() {
    	return false;
    }
	public RequestContext prepareActionAttenTwo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttenTwo() {
    	return false;
    }
	public RequestContext prepareActionViewTwo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewTwo() {
    	return false;
    }
	public RequestContext prepareactionAcctSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAcctSelect() {
    	return false;
    }
	public RequestContext prepareactionSplitProj(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionSplitProj() {
    	return false;
    }
	public RequestContext prepareactionSplitBotUp(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionSplitBotUp() {
    	return false;
    }
	public RequestContext prepareactionSplitProd(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionSplitProd() {
    	return false;
    }
	public RequestContext prepareactionImpContrSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionImpContrSplit() {
    	return false;
    }
	public RequestContext prepareactionAddSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAddSplit() {
    	return false;
    }
	public RequestContext prepareactionRemoveSplitEntry(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionRemoveSplitEntry() {
    	return false;
    }

    /**
     * output ActionAddSupp class
     */     
    protected class ActionAddSupp extends ItemAction {     
    
        public ActionAddSupp()
        {
            this(null);
        }

        public ActionAddSupp(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddSupp.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddSupp.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddSupp.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "ActionAddSupp", "actionAddSupp_actionPerformed", e);
        }
    }

    /**
     * output ActionDelSupp class
     */     
    protected class ActionDelSupp extends ItemAction {     
    
        public ActionDelSupp()
        {
            this(null);
        }

        public ActionDelSupp(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelSupp.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelSupp.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelSupp.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "ActionDelSupp", "actionDelSupp_actionPerformed", e);
        }
    }

    /**
     * output ActionRegister class
     */     
    protected class ActionRegister extends ItemAction {     
    
        public ActionRegister()
        {
            this(null);
        }

        public ActionRegister(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRegister.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRegister.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRegister.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "ActionRegister", "actionRegister_actionPerformed", e);
        }
    }

    /**
     * output ActionDisPatch class
     */     
    protected class ActionDisPatch extends ItemAction {     
    
        public ActionDisPatch()
        {
            this(null);
        }

        public ActionDisPatch(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDisPatch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisPatch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisPatch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "ActionDisPatch", "actionDisPatch_actionPerformed", e);
        }
    }

    /**
     * output ActionAheadDisPatch class
     */     
    protected class ActionAheadDisPatch extends ItemAction {     
    
        public ActionAheadDisPatch()
        {
            this(null);
        }

        public ActionAheadDisPatch(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAheadDisPatch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAheadDisPatch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAheadDisPatch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "ActionAheadDisPatch", "actionAheadDisPatch_actionPerformed", e);
        }
    }

    /**
     * output ActionViewAttachment class
     */     
    protected class ActionViewAttachment extends ItemAction {     
    
        public ActionViewAttachment()
        {
            this(null);
        }

        public ActionViewAttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "ActionViewAttachment", "actionViewAttachment_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContract class
     */     
    protected class ActionViewContract extends ItemAction {     
    
        public ActionViewContract()
        {
            this(null);
        }

        public ActionViewContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "ActionViewContract", "actionViewContract_actionPerformed", e);
        }
    }

    /**
     * output ActionAttenTwo class
     */     
    protected class ActionAttenTwo extends ItemAction {     
    
        public ActionAttenTwo()
        {
            this(null);
        }

        public ActionAttenTwo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAttenTwo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttenTwo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttenTwo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "ActionAttenTwo", "actionAttenTwo_actionPerformed", e);
        }
    }

    /**
     * output ActionViewTwo class
     */     
    protected class ActionViewTwo extends ItemAction {     
    
        public ActionViewTwo()
        {
            this(null);
        }

        public ActionViewTwo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewTwo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewTwo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewTwo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "ActionViewTwo", "actionViewTwo_actionPerformed", e);
        }
    }

    /**
     * output actionAcctSelect class
     */     
    protected class actionAcctSelect extends ItemAction {     
    
        public actionAcctSelect()
        {
            this(null);
        }

        public actionAcctSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionAcctSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAcctSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAcctSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "actionAcctSelect", "actionAcctSelect_actionPerformed", e);
        }
    }

    /**
     * output actionSplitProj class
     */     
    protected class actionSplitProj extends ItemAction {     
    
        public actionSplitProj()
        {
            this(null);
        }

        public actionSplitProj(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionSplitProj.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSplitProj.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSplitProj.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "actionSplitProj", "actionSplitProj_actionPerformed", e);
        }
    }

    /**
     * output actionSplitBotUp class
     */     
    protected class actionSplitBotUp extends ItemAction {     
    
        public actionSplitBotUp()
        {
            this(null);
        }

        public actionSplitBotUp(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionSplitBotUp.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSplitBotUp.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSplitBotUp.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "actionSplitBotUp", "actionSplitBotUp_actionPerformed", e);
        }
    }

    /**
     * output actionSplitProd class
     */     
    protected class actionSplitProd extends ItemAction {     
    
        public actionSplitProd()
        {
            this(null);
        }

        public actionSplitProd(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionSplitProd.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSplitProd.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSplitProd.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "actionSplitProd", "actionSplitProd_actionPerformed", e);
        }
    }

    /**
     * output actionImpContrSplit class
     */     
    protected class actionImpContrSplit extends ItemAction {     
    
        public actionImpContrSplit()
        {
            this(null);
        }

        public actionImpContrSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionImpContrSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImpContrSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImpContrSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "actionImpContrSplit", "actionImpContrSplit_actionPerformed", e);
        }
    }

    /**
     * output actionAddSplit class
     */     
    protected class actionAddSplit extends ItemAction {     
    
        public actionAddSplit()
        {
            this(null);
        }

        public actionAddSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionAddSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "actionAddSplit", "actionAddSplit_actionPerformed", e);
        }
    }

    /**
     * output actionRemoveSplitEntry class
     */     
    protected class actionRemoveSplitEntry extends ItemAction {     
    
        public actionRemoveSplitEntry()
        {
            this(null);
        }

        public actionRemoveSplitEntry(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionRemoveSplitEntry.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionRemoveSplitEntry.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionRemoveSplitEntry.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDesignChangeAuditEditUI.this, "actionRemoveSplitEntry", "actionRemoveSplitEntry_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "DesignChangeAuditEditUI");
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
        return com.kingdee.eas.fdc.contract.client.ChangeAuditEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.ChangeAuditBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.ChangeAuditBillInfo objectValue = new com.kingdee.eas.fdc.contract.ChangeAuditBillInfo();		
        return objectValue;
    }




}