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
public abstract class AbstractContractWithoutTextEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractWithoutTextEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbillName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreceiveUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbankAcct;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsettlementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkNeedPaid;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAllInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanProject;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewBudget;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer18;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostedCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostedDept;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcurProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbillName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtamount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pksignDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtreceiveUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox txtBankAcct;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsettlementType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtuseDepartment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrealSupplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcurrency;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPaymentProportion;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPaymentRequestBillNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtexchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcapitalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBcAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtattachment;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrg;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbookedDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox cbPeriod;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcompletePrjAmt;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInvoiceNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkInvoiceDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtInvoiceAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAllInvoiceAmt;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPlanProject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox kdDepPlanState;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDContainer contBgEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkUrgency;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkCostsplit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conConCharge;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblAttachmentContainer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtBgEntry;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtMoneyDesc;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtNoPaidReason;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtConCharge;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAccount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbAttachment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostedCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostedDept;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewBgBalance;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBgBalance;
    protected com.kingdee.eas.fdc.contract.ContractWithoutTextInfo editData = null;
    protected ActionViewBgBalance actionViewBgBalance = null;
    protected ActionViewAttachment actionViewAttachment = null;
    protected actionViewBudget actionViewBudget = null;
    /**
     * output class constructor
     */
    public AbstractContractWithoutTextEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractWithoutTextEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionPrint
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionDelVoucher
        actionDelVoucher.setEnabled(true);
        actionDelVoucher.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionDelVoucher.SHORT_DESCRIPTION");
        actionDelVoucher.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionDelVoucher.LONG_DESCRIPTION");
        actionDelVoucher.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionDelVoucher.NAME");
        actionDelVoucher.putValue(ItemAction.NAME, _tempStr);
        this.actionDelVoucher.setBindWorkFlow(true);
         this.actionDelVoucher.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        actionAudit.setEnabled(true);
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
        actionUnAudit.setEnabled(true);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBgBalance
        this.actionViewBgBalance = new ActionViewBgBalance(this);
        getActionManager().registerAction("actionViewBgBalance", actionViewBgBalance);
         this.actionViewBgBalance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAttachment
        this.actionViewAttachment = new ActionViewAttachment(this);
        getActionManager().registerAction("actionViewAttachment", actionViewAttachment);
         this.actionViewAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBudget
        this.actionViewBudget = new actionViewBudget(this);
        getActionManager().registerAction("actionViewBudget", actionViewBudget);
         this.actionViewBudget.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbillName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreceiveUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbankAcct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsettlementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkNeedPaid = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer17 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAllInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewBudget = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDLabelContainer18 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane3 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contCostedCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCostedDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtcurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtbillName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtamount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pksignDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtreceiveUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBankAcct = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsettlementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPayment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtuseDepartment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtrealSupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPaymentProportion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPaymentRequestBillNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtexchangeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtcapitalAmount = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBcAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtattachment = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkbookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcompletePrjAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtContractType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtInvoiceNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkInvoiceDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAllInvoiceAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtPlanProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdDepPlanState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contBgEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkUrgency = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkCostsplit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conConCharge = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewAttachment = new com.kingdee.bos.ctrl.swing.KDButton();
        this.lblAttachmentContainer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtBgEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtMoneyDesc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtNoPaidReason = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtConCharge = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cmbAttachment = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkauditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCostedCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCostedDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnViewBgBalance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewBgBalance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contNumber.setName("contNumber");
        this.contcurProject.setName("contcurProject");
        this.contbillName.setName("contbillName");
        this.contamount.setName("contamount");
        this.contpayDate.setName("contpayDate");
        this.contreceiveUnit.setName("contreceiveUnit");
        this.contbank.setName("contbank");
        this.contbankAcct.setName("contbankAcct");
        this.contsettlementType.setName("contsettlementType");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.contOrg.setName("contOrg");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.chkNeedPaid.setName("chkNeedPaid");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer17.setName("kDLabelContainer17");
        this.contInvoiceNumber.setName("contInvoiceNumber");
        this.contInvoiceDate.setName("contInvoiceDate");
        this.contInvoiceAmt.setName("contInvoiceAmt");
        this.contAllInvoiceAmt.setName("contAllInvoiceAmt");
        this.contPlanProject.setName("contPlanProject");
        this.btnViewBudget.setName("btnViewBudget");
        this.kDLabelContainer18.setName("kDLabelContainer18");
        this.kDScrollPane3.setName("kDScrollPane3");
        this.contCostedCompany.setName("contCostedCompany");
        this.contCostedDept.setName("contCostedDept");
        this.txtNumber.setName("txtNumber");
        this.prmtcurProject.setName("prmtcurProject");
        this.txtbillName.setName("txtbillName");
        this.txtamount.setName("txtamount");
        this.pksignDate.setName("pksignDate");
        this.prmtreceiveUnit.setName("prmtreceiveUnit");
        this.txtBank.setName("txtBank");
        this.txtBankAcct.setName("txtBankAcct");
        this.prmtsettlementType.setName("prmtsettlementType");
        this.prmtPayment.setName("prmtPayment");
        this.prmtuseDepartment.setName("prmtuseDepartment");
        this.prmtrealSupplier.setName("prmtrealSupplier");
        this.prmtcurrency.setName("prmtcurrency");
        this.txtPaymentProportion.setName("txtPaymentProportion");
        this.txtPaymentRequestBillNumber.setName("txtPaymentRequestBillNumber");
        this.txtexchangeRate.setName("txtexchangeRate");
        this.txtcapitalAmount.setName("txtcapitalAmount");
        this.txtBcAmount.setName("txtBcAmount");
        this.txtattachment.setName("txtattachment");
        this.txtOrg.setName("txtOrg");
        this.pkbookedDate.setName("pkbookedDate");
        this.cbPeriod.setName("cbPeriod");
        this.txtcompletePrjAmt.setName("txtcompletePrjAmt");
        this.prmtContractType.setName("prmtContractType");
        this.txtInvoiceNumber.setName("txtInvoiceNumber");
        this.pkInvoiceDate.setName("pkInvoiceDate");
        this.txtInvoiceAmt.setName("txtInvoiceAmt");
        this.txtAllInvoiceAmt.setName("txtAllInvoiceAmt");
        this.prmtPlanProject.setName("prmtPlanProject");
        this.kdDepPlanState.setName("kdDepPlanState");
        this.kDPanel1.setName("kDPanel1");
        this.contBgEntry.setName("contBgEntry");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.chkUrgency.setName("chkUrgency");
        this.chkCostsplit.setName("chkCostsplit");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.conConCharge.setName("conConCharge");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.btnViewAttachment.setName("btnViewAttachment");
        this.lblAttachmentContainer.setName("lblAttachmentContainer");
        this.contCreator.setName("contCreator");
        this.contAuditor.setName("contAuditor");
        this.contauditTime.setName("contauditTime");
        this.contCreateTime.setName("contCreateTime");
        this.kdtBgEntry.setName("kdtBgEntry");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtMoneyDesc.setName("txtMoneyDesc");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtNoPaidReason.setName("txtNoPaidReason");
        this.prmtConCharge.setName("prmtConCharge");
        this.prmtAccount.setName("prmtAccount");
        this.cmbAttachment.setName("cmbAttachment");
        this.prmtCreator.setName("prmtCreator");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkauditTime.setName("pkauditTime");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtCostedCompany.setName("prmtCostedCompany");
        this.prmtCostedDept.setName("prmtCostedDept");
        this.btnViewBgBalance.setName("btnViewBgBalance");
        this.menuItemViewBgBalance.setName("menuItemViewBgBalance");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemSubmit.setToolTipText(resHelper.getString("menuItemSubmit.toolTipText"));		
        this.menuSubmitOption.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnCopyFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.separator1.setVisible(false);		
        this.separator2.setVisible(false);		
        this.separator3.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewDoProccess.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setToolTipText(resHelper.getString("btnUnAudit.toolTipText"));
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));
        this.menuItemUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setToolTipText(resHelper.getString("menuItemUnAudit.toolTipText"));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelAlignment(7);		
        this.contNumber.setVisible(true);
        // contcurProject		
        this.contcurProject.setBoundLabelText(resHelper.getString("contcurProject.boundLabelText"));		
        this.contcurProject.setBoundLabelLength(100);		
        this.contcurProject.setBoundLabelUnderline(true);		
        this.contcurProject.setVisible(true);		
        this.contcurProject.setBoundLabelAlignment(7);
        // contbillName		
        this.contbillName.setBoundLabelText(resHelper.getString("contbillName.boundLabelText"));		
        this.contbillName.setBoundLabelLength(100);		
        this.contbillName.setBoundLabelUnderline(true);		
        this.contbillName.setVisible(true);		
        this.contbillName.setBoundLabelAlignment(7);
        // contamount		
        this.contamount.setBoundLabelText(resHelper.getString("contamount.boundLabelText"));		
        this.contamount.setBoundLabelLength(100);		
        this.contamount.setBoundLabelUnderline(true);		
        this.contamount.setVisible(true);		
        this.contamount.setBoundLabelAlignment(7);
        // contpayDate		
        this.contpayDate.setBoundLabelText(resHelper.getString("contpayDate.boundLabelText"));		
        this.contpayDate.setBoundLabelLength(100);		
        this.contpayDate.setBoundLabelUnderline(true);		
        this.contpayDate.setVisible(true);		
        this.contpayDate.setBoundLabelAlignment(7);
        // contreceiveUnit		
        this.contreceiveUnit.setBoundLabelText(resHelper.getString("contreceiveUnit.boundLabelText"));		
        this.contreceiveUnit.setBoundLabelLength(100);		
        this.contreceiveUnit.setBoundLabelUnderline(true);		
        this.contreceiveUnit.setVisible(true);		
        this.contreceiveUnit.setBoundLabelAlignment(7);
        // contbank		
        this.contbank.setBoundLabelText(resHelper.getString("contbank.boundLabelText"));		
        this.contbank.setBoundLabelLength(100);		
        this.contbank.setBoundLabelUnderline(true);		
        this.contbank.setVisible(true);
        // contbankAcct		
        this.contbankAcct.setBoundLabelText(resHelper.getString("contbankAcct.boundLabelText"));		
        this.contbankAcct.setBoundLabelLength(100);		
        this.contbankAcct.setBoundLabelUnderline(true);		
        this.contbankAcct.setVisible(true);
        // contsettlementType		
        this.contsettlementType.setBoundLabelText(resHelper.getString("contsettlementType.boundLabelText"));		
        this.contsettlementType.setBoundLabelLength(100);		
        this.contsettlementType.setBoundLabelUnderline(true);		
        this.contsettlementType.setVisible(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setVisible(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setVisible(true);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setVisible(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setVisible(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);		
        this.kDLabelContainer9.setBoundLabelAlignment(7);		
        this.kDLabelContainer9.setVisible(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setVisible(true);		
        this.kDLabelContainer3.setBoundLabelAlignment(7);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);		
        this.kDLabelContainer10.setBoundLabelAlignment(7);		
        this.kDLabelContainer10.setVisible(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);		
        this.kDLabelContainer11.setVisible(true);		
        this.kDLabelContainer11.setBoundLabelAlignment(7);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);		
        this.kDLabelContainer12.setVisible(true);		
        this.kDLabelContainer12.setBoundLabelAlignment(7);
        // contOrg		
        this.contOrg.setBoundLabelText(resHelper.getString("contOrg.boundLabelText"));		
        this.contOrg.setBoundLabelLength(100);		
        this.contOrg.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // chkNeedPaid		
        this.chkNeedPaid.setText(resHelper.getString("chkNeedPaid.text"));
        this.chkNeedPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkNeedPaid_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelUnderline(true);		
        this.kDLabelContainer8.setBoundLabelLength(100);
        // kDLabelContainer17		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelLength(100);		
        this.kDLabelContainer17.setBoundLabelUnderline(true);
        // contInvoiceNumber		
        this.contInvoiceNumber.setBoundLabelText(resHelper.getString("contInvoiceNumber.boundLabelText"));		
        this.contInvoiceNumber.setBoundLabelLength(100);		
        this.contInvoiceNumber.setBoundLabelUnderline(true);
        // contInvoiceDate		
        this.contInvoiceDate.setBoundLabelText(resHelper.getString("contInvoiceDate.boundLabelText"));		
        this.contInvoiceDate.setBoundLabelLength(100);		
        this.contInvoiceDate.setBoundLabelUnderline(true);
        // contInvoiceAmt		
        this.contInvoiceAmt.setBoundLabelText(resHelper.getString("contInvoiceAmt.boundLabelText"));		
        this.contInvoiceAmt.setBoundLabelLength(100);		
        this.contInvoiceAmt.setBoundLabelUnderline(true);
        // contAllInvoiceAmt		
        this.contAllInvoiceAmt.setBoundLabelText(resHelper.getString("contAllInvoiceAmt.boundLabelText"));		
        this.contAllInvoiceAmt.setBoundLabelLength(100);		
        this.contAllInvoiceAmt.setBoundLabelUnderline(true);
        // contPlanProject		
        this.contPlanProject.setBoundLabelText(resHelper.getString("contPlanProject.boundLabelText"));		
        this.contPlanProject.setBoundLabelUnderline(true);		
        this.contPlanProject.setBoundLabelLength(100);
        // btnViewBudget
        this.btnViewBudget.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBudget, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBudget.setText(resHelper.getString("btnViewBudget.text"));
        // kDLabelContainer18		
        this.kDLabelContainer18.setBoundLabelText(resHelper.getString("kDLabelContainer18.boundLabelText"));		
        this.kDLabelContainer18.setBoundLabelLength(100);		
        this.kDLabelContainer18.setBoundLabelUnderline(true);		
        this.kDLabelContainer18.setEnabled(false);
        // kDScrollPane3
        // contCostedCompany		
        this.contCostedCompany.setBoundLabelText(resHelper.getString("contCostedCompany.boundLabelText"));		
        this.contCostedCompany.setBoundLabelLength(100);		
        this.contCostedCompany.setBoundLabelUnderline(true);
        // contCostedDept		
        this.contCostedDept.setBoundLabelText(resHelper.getString("contCostedDept.boundLabelText"));		
        this.contCostedDept.setBoundLabelLength(100);		
        this.contCostedDept.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(true);
        this.txtNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtNumber_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // prmtcurProject		
        this.prmtcurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtcurProject.setVisible(true);		
        this.prmtcurProject.setEditable(true);		
        this.prmtcurProject.setDisplayFormat("$displayname$");		
        this.prmtcurProject.setEditFormat("$number$");		
        this.prmtcurProject.setCommitFormat("$number$");		
        this.prmtcurProject.setRequired(true);
        this.prmtcurProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtcurProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtbillName		
        this.txtbillName.setVisible(true);		
        this.txtbillName.setHorizontalAlignment(2);		
        this.txtbillName.setMaxLength(100);		
        this.txtbillName.setRequired(true);		
        this.txtbillName.setEnabled(true);
        // txtamount		
        this.txtamount.setVisible(true);		
        this.txtamount.setHorizontalAlignment(2);		
        this.txtamount.setDataType(1);		
        this.txtamount.setSupportedEmpty(true);		
        this.txtamount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtamount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtamount.setPrecision(2);		
        this.txtamount.setRequired(true);		
        this.txtamount.setEnabled(true);
        this.txtamount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtamount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtamount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtamount_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // pksignDate		
        this.pksignDate.setVisible(true);		
        this.pksignDate.setEnabled(true);		
        this.pksignDate.setRequired(true);
        // prmtreceiveUnit		
        this.prmtreceiveUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtreceiveUnit.setVisible(true);		
        this.prmtreceiveUnit.setEditable(true);		
        this.prmtreceiveUnit.setDisplayFormat("$number$ $name$");		
        this.prmtreceiveUnit.setEditFormat("$number$");		
        this.prmtreceiveUnit.setCommitFormat("$number$");		
        this.prmtreceiveUnit.setRequired(true);
        this.prmtreceiveUnit.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtreceiveUnit_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtBank		
        this.txtBank.setRequired(true);
        // txtBankAcct		
        this.txtBankAcct.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7SupplierCompanyBankQuery");		
        this.txtBankAcct.setDisplayFormat("$bankAccount");		
        this.txtBankAcct.setEditFormat("$bankAccount");		
        this.txtBankAcct.setCommitFormat("$bankAccount");
        // prmtsettlementType		
        this.prmtsettlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7SettlementTypeQuery");		
        this.prmtsettlementType.setVisible(true);		
        this.prmtsettlementType.setEditable(true);		
        this.prmtsettlementType.setDisplayFormat("$number$ $name$");		
        this.prmtsettlementType.setEditFormat("$number$");		
        this.prmtsettlementType.setCommitFormat("$number$");
        this.prmtsettlementType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtsettlementType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtPayment		
        this.prmtPayment.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");		
        this.prmtPayment.setVisible(true);		
        this.prmtPayment.setEditable(true);		
        this.prmtPayment.setDisplayFormat("$number$ $name$");		
        this.prmtPayment.setEditFormat("$number$");		
        this.prmtPayment.setCommitFormat("$number$");		
        this.prmtPayment.setRequired(true);
        // prmtuseDepartment		
        this.prmtuseDepartment.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");		
        this.prmtuseDepartment.setVisible(true);		
        this.prmtuseDepartment.setEditable(true);		
        this.prmtuseDepartment.setDisplayFormat("$number$ $name$");		
        this.prmtuseDepartment.setEditFormat("$number$");		
        this.prmtuseDepartment.setCommitFormat("$number$");		
        this.prmtuseDepartment.setRequired(true);
        // prmtrealSupplier		
        this.prmtrealSupplier.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtrealSupplier.setVisible(true);		
        this.prmtrealSupplier.setEditable(true);		
        this.prmtrealSupplier.setDisplayFormat("$number$ $name$");		
        this.prmtrealSupplier.setEditFormat("$number$");		
        this.prmtrealSupplier.setCommitFormat("$number$");		
        this.prmtrealSupplier.setRequired(true);
        this.prmtrealSupplier.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtrealSupplier_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtcurrency		
        this.prmtcurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.prmtcurrency.setVisible(true);		
        this.prmtcurrency.setEditable(true);		
        this.prmtcurrency.setDisplayFormat("$number$ $name$");		
        this.prmtcurrency.setEditFormat("$number$");		
        this.prmtcurrency.setCommitFormat("$number$");		
        this.prmtcurrency.setRequired(true);
        this.prmtcurrency.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtcurrency_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPaymentProportion		
        this.txtPaymentProportion.setDataType(1);		
        this.txtPaymentProportion.setEnabled(false);
        this.txtPaymentProportion.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtpaymentProportion_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPaymentRequestBillNumber		
        this.txtPaymentRequestBillNumber.setMaxLength(80);		
        this.txtPaymentRequestBillNumber.setVisible(true);		
        this.txtPaymentRequestBillNumber.setEnabled(true);		
        this.txtPaymentRequestBillNumber.setHorizontalAlignment(2);		
        this.txtPaymentRequestBillNumber.setRequired(true);		
        this.txtPaymentRequestBillNumber.setText(resHelper.getString("txtPaymentRequestBillNumber.text"));
        // txtexchangeRate		
        this.txtexchangeRate.setDataType(1);		
        this.txtexchangeRate.setRequired(true);		
        this.txtexchangeRate.setPrecision(3);
        this.txtexchangeRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtexchangeRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtexchangeRate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtexchangeRate_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtcapitalAmount		
        this.txtcapitalAmount.setMaxLength(80);		
        this.txtcapitalAmount.setVisible(true);		
        this.txtcapitalAmount.setEnabled(true);		
        this.txtcapitalAmount.setHorizontalAlignment(2);		
        this.txtcapitalAmount.setRequired(false);		
        this.txtcapitalAmount.setText(resHelper.getString("txtcapitalAmount.text"));		
        this.txtcapitalAmount.setEditable(false);
        // txtBcAmount		
        this.txtBcAmount.setDataType(1);		
        this.txtBcAmount.setRequired(true);		
        this.txtBcAmount.setPrecision(2);		
        this.txtBcAmount.setEnabled(false);
        // txtattachment		
        this.txtattachment.setVisible(true);		
        this.txtattachment.setDataType(0);		
        this.txtattachment.setSupportedEmpty(true);		
        this.txtattachment.setRequired(false);		
        this.txtattachment.setEnabled(true);		
        this.txtattachment.setPrecision(0);
        // txtOrg		
        this.txtOrg.setEditable(false);
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
        // txtcompletePrjAmt		
        this.txtcompletePrjAmt.setDataType(1);		
        this.txtcompletePrjAmt.setEnabled(false);
        this.txtcompletePrjAmt.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtcompletePrjAmt_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtContractType		
        this.prmtContractType.setCommitFormat("$number$");		
        this.prmtContractType.setDisplayFormat("$name$");		
        this.prmtContractType.setEditFormat("$number$");		
        this.prmtContractType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ContractTypeQuery");		
        this.prmtContractType.setRequired(true);		
        this.prmtContractType.setDefaultF7UIName("com.kingdee.eas.fdc.basedata.client.ContractTypeF7UI");
        this.prmtContractType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtContractType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtInvoiceNumber		
        this.txtInvoiceNumber.setRequired(true);
        // pkInvoiceDate
        // txtInvoiceAmt		
        this.txtInvoiceAmt.setSupportedEmpty(true);		
        this.txtInvoiceAmt.setEnabled(false);
        // txtAllInvoiceAmt		
        this.txtAllInvoiceAmt.setSupportedEmpty(true);		
        this.txtAllInvoiceAmt.setEnabled(false);		
        this.txtAllInvoiceAmt.setDataType(1);
        // prmtPlanProject
        this.prmtPlanProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtPlanProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdDepPlanState		
        this.kdDepPlanState.setEnabled(false);
        // kDPanel1		
        this.kDPanel1.setPreferredSize(new Dimension(1013,500));
        // contBgEntry
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(100);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);
        // chkUrgency		
        this.chkUrgency.setText(resHelper.getString("chkUrgency.text"));
        // chkCostsplit		
        this.chkCostsplit.setText(resHelper.getString("chkCostsplit.text"));
        this.chkCostsplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkCostsplit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(100);		
        this.kDLabelContainer15.setBoundLabelUnderline(true);		
        this.kDLabelContainer15.setEnabled(false);
        // conConCharge		
        this.conConCharge.setBoundLabelText(resHelper.getString("conConCharge.boundLabelText"));		
        this.conConCharge.setBoundLabelLength(100);		
        this.conConCharge.setBoundLabelUnderline(true);
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(100);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);
        // btnViewAttachment
        this.btnViewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAttachment.setText(resHelper.getString("btnViewAttachment.text"));
        // lblAttachmentContainer		
        this.lblAttachmentContainer.setBoundLabelText(resHelper.getString("lblAttachmentContainer.boundLabelText"));		
        this.lblAttachmentContainer.setBoundLabelLength(100);		
        this.lblAttachmentContainer.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contauditTime		
        this.contauditTime.setBoundLabelText(resHelper.getString("contauditTime.boundLabelText"));		
        this.contauditTime.setBoundLabelLength(100);		
        this.contauditTime.setBoundLabelUnderline(true);		
        this.contauditTime.setVisible(true);		
        this.contauditTime.setBoundLabelAlignment(7);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // kdtBgEntry
		String kdtBgEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"bgItem\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"rate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"requestAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"bgBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{bgItem}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{rate}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{requestAmount}</t:Cell><t:Cell>$Resource{bgBalance}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtBgEntry.setFormatXml(resHelper.translateString("kdtBgEntry",kdtBgEntryStrXML));
        this.kdtBgEntry.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    kdtBgEntry_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtBgEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtBgEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.kdtBgEntry.checkParsed();
        // kDScrollPane1		
        this.kDScrollPane1.setAutoscrolls(true);
        // txtMoneyDesc		
        this.txtMoneyDesc.setToolTipText(resHelper.getString("txtMoneyDesc.toolTipText"));		
        this.txtMoneyDesc.setText(resHelper.getString("txtMoneyDesc.text"));		
        this.txtMoneyDesc.setMaxLength(1000);		
        this.txtMoneyDesc.setWrapStyleWord(true);		
        this.txtMoneyDesc.setLineWrap(true);
        // kDScrollPane2
        // txtNoPaidReason		
        this.txtNoPaidReason.setEnabled(false);
        // prmtConCharge		
        this.prmtConCharge.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ContractChargeTypeQuery");		
        this.prmtConCharge.setDisplayFormat("$name$");		
        this.prmtConCharge.setEditFormat("$number$");		
        this.prmtConCharge.setCommitFormat("$number$");
        // prmtAccount		
        this.prmtAccount.setEnabled(false);		
        this.prmtAccount.setDisplayFormat("$name$");		
        this.prmtAccount.setEditFormat("$number$");		
        this.prmtAccount.setCommitFormat("$number$");		
        this.prmtAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
        this.prmtAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtAccount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtAccount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cmbAttachment
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$number$");		
        this.prmtAuditor.setCommitFormat("$number$");
        // pkauditTime		
        this.pkauditTime.setVisible(true);		
        this.pkauditTime.setEnabled(false);
        this.pkauditTime.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkauditTime_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDDateCreateTime		
        this.kDDateCreateTime.setEnabled(false);
        // prmtCostedCompany
        // prmtCostedDept
        // btnViewBgBalance
        this.btnViewBgBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBgBalance.setText(resHelper.getString("btnViewBgBalance.text"));
        // menuItemViewBgBalance
        this.menuItemViewBgBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewBgBalance.setText(resHelper.getString("menuItemViewBgBalance.text"));		
        this.menuItemViewBgBalance.setToolTipText(resHelper.getString("menuItemViewBgBalance.toolTipText"));
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
        this.setBounds(new Rectangle(0, 0, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 629));
        kDSeparator5.setBounds(new Rectangle(9, 53, 991, 10));
        this.add(kDSeparator5, new KDLayout.Constraints(9, 53, 991, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(9, 30, 469, 19));
        this.add(contNumber, new KDLayout.Constraints(9, 30, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcurProject.setBounds(new Rectangle(531, 8, 469, 19));
        this.add(contcurProject, new KDLayout.Constraints(531, 8, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contbillName.setBounds(new Rectangle(531, 30, 469, 19));
        this.add(contbillName, new KDLayout.Constraints(531, 30, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contamount.setBounds(new Rectangle(531, 169, 469, 19));
        this.add(contamount, new KDLayout.Constraints(531, 169, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contpayDate.setBounds(new Rectangle(531, 103, 469, 19));
        this.add(contpayDate, new KDLayout.Constraints(531, 103, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contreceiveUnit.setBounds(new Rectangle(9, 147, 469, 19));
        this.add(contreceiveUnit, new KDLayout.Constraints(9, 147, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbank.setBounds(new Rectangle(9, 257, 469, 19));
        this.add(contbank, new KDLayout.Constraints(9, 257, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbankAcct.setBounds(new Rectangle(9, 279, 469, 19));
        this.add(contbankAcct, new KDLayout.Constraints(9, 279, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsettlementType.setBounds(new Rectangle(9, 235, 469, 19));
        this.add(contsettlementType, new KDLayout.Constraints(9, 235, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(9, 81, 469, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(9, 81, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(531, 81, 469, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(531, 81, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer7.setBounds(new Rectangle(9, 169, 469, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(9, 169, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(531, 125, 469, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(531, 125, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(9, 301, 469, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(9, 301, 469, 19, 0));
        kDLabelContainer9.setBounds(new Rectangle(8, 125, 469, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(8, 125, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(531, 147, 469, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(531, 147, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer10.setBounds(new Rectangle(531, 258, 469, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(531, 258, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer11.setBounds(new Rectangle(531, 235, 469, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(531, 235, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer12.setBounds(new Rectangle(531, 301, 469, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(531, 301, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrg.setBounds(new Rectangle(9, 8, 469, 19));
        this.add(contOrg, new KDLayout.Constraints(9, 8, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(9, 59, 469, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(9, 59, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer13.setBounds(new Rectangle(531, 59, 469, 19));
        this.add(kDLabelContainer13, new KDLayout.Constraints(531, 59, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkNeedPaid.setBounds(new Rectangle(902, 323, 100, 19));
        this.add(chkNeedPaid, new KDLayout.Constraints(902, 323, 100, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer8.setBounds(new Rectangle(531, 279, 469, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(531, 279, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer17.setBounds(new Rectangle(9, 103, 469, 19));
        this.add(kDLabelContainer17, new KDLayout.Constraints(9, 103, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoiceNumber.setBounds(new Rectangle(9, 191, 469, 19));
        this.add(contInvoiceNumber, new KDLayout.Constraints(9, 191, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoiceDate.setBounds(new Rectangle(9, 213, 469, 19));
        this.add(contInvoiceDate, new KDLayout.Constraints(9, 213, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInvoiceAmt.setBounds(new Rectangle(531, 191, 469, 19));
        this.add(contInvoiceAmt, new KDLayout.Constraints(531, 191, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAllInvoiceAmt.setBounds(new Rectangle(531, 213, 469, 19));
        this.add(contAllInvoiceAmt, new KDLayout.Constraints(531, 213, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPlanProject.setBounds(new Rectangle(11, 323, 270, 19));
        this.add(contPlanProject, new KDLayout.Constraints(11, 323, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewBudget.setBounds(new Rectangle(384, 323, 93, 21));
        this.add(btnViewBudget, new KDLayout.Constraints(384, 323, 93, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer18.setBounds(new Rectangle(531, 323, 270, 19));
        this.add(kDLabelContainer18, new KDLayout.Constraints(531, 323, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane3.setBounds(new Rectangle(4, 367, 1005, 378));
        this.add(kDScrollPane3, new KDLayout.Constraints(4, 367, 1005, 378, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCostedCompany.setBounds(new Rectangle(11, 346, 270, 19));
        this.add(contCostedCompany, new KDLayout.Constraints(11, 346, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostedDept.setBounds(new Rectangle(531, 346, 270, 19));
        this.add(contCostedDept, new KDLayout.Constraints(531, 346, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contcurProject
        contcurProject.setBoundEditor(prmtcurProject);
        //contbillName
        contbillName.setBoundEditor(txtbillName);
        //contamount
        contamount.setBoundEditor(txtamount);
        //contpayDate
        contpayDate.setBoundEditor(pksignDate);
        //contreceiveUnit
        contreceiveUnit.setBoundEditor(prmtreceiveUnit);
        //contbank
        contbank.setBoundEditor(txtBank);
        //contbankAcct
        contbankAcct.setBoundEditor(txtBankAcct);
        //contsettlementType
        contsettlementType.setBoundEditor(prmtsettlementType);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(prmtPayment);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(prmtuseDepartment);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(prmtrealSupplier);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prmtcurrency);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtPaymentProportion);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtPaymentRequestBillNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtexchangeRate);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(txtcapitalAmount);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(txtBcAmount);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtattachment);
        //contOrg
        contOrg.setBoundEditor(txtOrg);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(pkbookedDate);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(cbPeriod);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtcompletePrjAmt);
        //kDLabelContainer17
        kDLabelContainer17.setBoundEditor(prmtContractType);
        //contInvoiceNumber
        contInvoiceNumber.setBoundEditor(txtInvoiceNumber);
        //contInvoiceDate
        contInvoiceDate.setBoundEditor(pkInvoiceDate);
        //contInvoiceAmt
        contInvoiceAmt.setBoundEditor(txtInvoiceAmt);
        //contAllInvoiceAmt
        contAllInvoiceAmt.setBoundEditor(txtAllInvoiceAmt);
        //contPlanProject
        contPlanProject.setBoundEditor(prmtPlanProject);
        //kDLabelContainer18
        kDLabelContainer18.setBoundEditor(kdDepPlanState);
        //kDScrollPane3
        kDScrollPane3.getViewport().add(kDPanel1, null);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 500));        kDSeparator6.setBounds(new Rectangle(7, 331, 991, 8));
        kDPanel1.add(kDSeparator6, new KDLayout.Constraints(7, 331, 991, 8, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBgEntry.setBounds(new Rectangle(7, 6, 989, 213));
        kDPanel1.add(contBgEntry, new KDLayout.Constraints(7, 6, 989, 213, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer14.setBounds(new Rectangle(7, 226, 469, 50));
        kDPanel1.add(kDLabelContainer14, new KDLayout.Constraints(7, 226, 469, 50, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkUrgency.setBounds(new Rectangle(7, 279, 58, 19));
        kDPanel1.add(chkUrgency, new KDLayout.Constraints(7, 279, 58, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkCostsplit.setBounds(new Rectangle(372, 279, 121, 19));
        kDPanel1.add(chkCostsplit, new KDLayout.Constraints(372, 279, 121, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer15.setBounds(new Rectangle(525, 226, 469, 50));
        kDPanel1.add(kDLabelContainer15, new KDLayout.Constraints(525, 226, 469, 50, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conConCharge.setBounds(new Rectangle(525, 279, 469, 19));
        kDPanel1.add(conConCharge, new KDLayout.Constraints(525, 279, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer16.setBounds(new Rectangle(525, 301, 469, 19));
        kDPanel1.add(kDLabelContainer16, new KDLayout.Constraints(525, 301, 469, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        btnViewAttachment.setBounds(new Rectangle(425, 301, 86, 21));
        kDPanel1.add(btnViewAttachment, new KDLayout.Constraints(425, 301, 86, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblAttachmentContainer.setBounds(new Rectangle(7, 301, 378, 19));
        kDPanel1.add(lblAttachmentContainer, new KDLayout.Constraints(7, 301, 378, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(7, 339, 469, 19));
        kDPanel1.add(contCreator, new KDLayout.Constraints(7, 339, 469, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(7, 361, 469, 19));
        kDPanel1.add(contAuditor, new KDLayout.Constraints(7, 361, 469, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contauditTime.setBounds(new Rectangle(525, 361, 469, 19));
        kDPanel1.add(contauditTime, new KDLayout.Constraints(525, 361, 469, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(525, 339, 469, 19));
        kDPanel1.add(contCreateTime, new KDLayout.Constraints(525, 339, 469, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contBgEntry
contBgEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contBgEntry.getContentPane().add(kdtBgEntry, BorderLayout.CENTER);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtMoneyDesc, null);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtNoPaidReason, null);
        //conConCharge
        conConCharge.setBoundEditor(prmtConCharge);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(prmtAccount);
        //lblAttachmentContainer
        lblAttachmentContainer.setBoundEditor(cmbAttachment);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contauditTime
        contauditTime.setBoundEditor(pkauditTime);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contCostedCompany
        contCostedCompany.setBoundEditor(prmtCostedCompany);
        //contCostedDept
        contCostedDept.setBoundEditor(prmtCostedDept);

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
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        menuEdit.add(menuItemEnterToNextRow);
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
        menuView.add(kDSeparator7);
        menuView.add(menuItemViewBgBalance);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
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
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyLine);
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
        this.toolBar.add(btnViewBgBalance);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isNeedPaid", boolean.class, this.chkNeedPaid, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtcurProject, "data");
		dataBinder.registerBinding("name", String.class, this.txtbillName, "text");
		dataBinder.registerBinding("originalAmount", java.math.BigDecimal.class, this.txtamount, "value");
		dataBinder.registerBinding("signDate", java.util.Date.class, this.pksignDate, "value");
		dataBinder.registerBinding("receiveUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtreceiveUnit, "data");
		dataBinder.registerBinding("bank", String.class, this.txtBank, "text");
		dataBinder.registerBinding("settlementType", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.prmtsettlementType, "data");
		dataBinder.registerBinding("paymentType", com.kingdee.eas.fdc.basedata.PaymentTypeInfo.class, this.prmtPayment, "data");
		dataBinder.registerBinding("useDepartment", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtuseDepartment, "data");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtcurrency, "data");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtBcAmount, "value");
		dataBinder.registerBinding("bookedDate", java.util.Date.class, this.pkbookedDate, "value");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.cbPeriod, "data");
		dataBinder.registerBinding("contractType", com.kingdee.eas.fdc.basedata.ContractTypeInfo.class, this.prmtContractType, "data");
		dataBinder.registerBinding("invoiceNumber", String.class, this.txtInvoiceNumber, "text");
		dataBinder.registerBinding("invoiceDate", java.util.Date.class, this.pkInvoiceDate, "value");
		dataBinder.registerBinding("invoiceAmt", java.math.BigDecimal.class, this.txtInvoiceAmt, "value");
		dataBinder.registerBinding("allInvoiceAmt", double.class, this.txtAllInvoiceAmt, "value");
		dataBinder.registerBinding("fdcDepConPlan", com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo.class, this.prmtPlanProject, "data");
		dataBinder.registerBinding("depPlanState", com.kingdee.eas.fdc.contract.DepPlanStateEnum.class, this.kdDepPlanState, "selectedItem");
		dataBinder.registerBinding("isCostSplit", boolean.class, this.chkCostsplit, "selected");
		dataBinder.registerBinding("noPaidReason", String.class, this.txtNoPaidReason, "text");
		dataBinder.registerBinding("conChargeType", com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo.class, this.prmtConCharge, "data");
		dataBinder.registerBinding("account", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtAccount, "data");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkauditTime, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("costedCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtCostedCompany, "data");
		dataBinder.registerBinding("costedDept", com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo.class, this.prmtCostedDept, "data");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionEdit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionUnAudit, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractWithoutTextEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"CostCenter",editData.getString("number"));
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
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("CostCenter");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("CostCenter");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("isNeedPaid", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("signDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("receiveUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paymentType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("useDepartment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoiceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("allInvoiceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fdcDepConPlan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("depPlanState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isCostSplit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("noPaidReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conChargeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("account", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costedDept", ValidateHelper.ON_SAVE);    		
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		            this.actionEdit.setEnabled(false);
		            this.actionAudit.setVisible(false);
		            this.actionAudit.setEnabled(false);
		            this.actionUnAudit.setVisible(false);
		            this.actionUnAudit.setEnabled(false);
        }
    }

    /**
     * output chkNeedPaid_actionPerformed method
     */
    protected void chkNeedPaid_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtNumber_focusLost method
     */
    protected void txtNumber_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output prmtcurProject_dataChanged method
     */
    protected void prmtcurProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtamount_dataChanged method
     */
    protected void txtamount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtamount_focusLost method
     */
    protected void txtamount_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output prmtreceiveUnit_dataChanged method
     */
    protected void prmtreceiveUnit_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtsettlementType_dataChanged method
     */
    protected void prmtsettlementType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtrealSupplier_dataChanged method
     */
    protected void prmtrealSupplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtcurrency_dataChanged method
     */
    protected void prmtcurrency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtpaymentProportion_dataChanged method
     */
    protected void txtpaymentProportion_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtexchangeRate_focusLost method
     */
    protected void txtexchangeRate_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtexchangeRate_dataChanged method
     */
    protected void txtexchangeRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkbookedDate_dataChanged method
     */
    protected void pkbookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtcompletePrjAmt_dataChanged method
     */
    protected void txtcompletePrjAmt_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtContractType_dataChanged method
     */
    protected void prmtContractType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtPlanProject_dataChanged method
     */
    protected void prmtPlanProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output chkCostsplit_actionPerformed method
     */
    protected void chkCostsplit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdtBgEntry_editStopped method
     */
    protected void kdtBgEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtBgEntry_tableSelectChanged method
     */
    protected void kdtBgEntry_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output prmtAccount_willCommit method
     */
    protected void prmtAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtAccount_willShow method
     */
    protected void prmtAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtAccount_dataChanged method
     */
    protected void prmtAccount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkauditTime_dataChanged method
     */
    protected void pkauditTime_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("isNeedPaid"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("curProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("curProject.id"));
        	sic.add(new SelectorItemInfo("curProject.number"));
        	sic.add(new SelectorItemInfo("curProject.name"));
        	sic.add(new SelectorItemInfo("curProject.displayname"));
		}
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("signDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("receiveUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("receiveUnit.id"));
        	sic.add(new SelectorItemInfo("receiveUnit.number"));
        	sic.add(new SelectorItemInfo("receiveUnit.name"));
		}
        sic.add(new SelectorItemInfo("bank"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("settlementType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("settlementType.id"));
        	sic.add(new SelectorItemInfo("settlementType.number"));
        	sic.add(new SelectorItemInfo("settlementType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("paymentType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("paymentType.id"));
        	sic.add(new SelectorItemInfo("paymentType.number"));
        	sic.add(new SelectorItemInfo("paymentType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("useDepartment.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("useDepartment.id"));
        	sic.add(new SelectorItemInfo("useDepartment.number"));
        	sic.add(new SelectorItemInfo("useDepartment.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("currency.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("currency.id"));
        	sic.add(new SelectorItemInfo("currency.number"));
        	sic.add(new SelectorItemInfo("currency.name"));
		}
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("bookedDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("period.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("period.id"));
        	sic.add(new SelectorItemInfo("period.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractType.id"));
        	sic.add(new SelectorItemInfo("contractType.number"));
        	sic.add(new SelectorItemInfo("contractType.name"));
		}
        sic.add(new SelectorItemInfo("invoiceNumber"));
        sic.add(new SelectorItemInfo("invoiceDate"));
        sic.add(new SelectorItemInfo("invoiceAmt"));
        sic.add(new SelectorItemInfo("allInvoiceAmt"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("fdcDepConPlan.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("fdcDepConPlan.id"));
		}
        sic.add(new SelectorItemInfo("depPlanState"));
        sic.add(new SelectorItemInfo("isCostSplit"));
        sic.add(new SelectorItemInfo("noPaidReason"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("conChargeType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("conChargeType.id"));
        	sic.add(new SelectorItemInfo("conChargeType.number"));
        	sic.add(new SelectorItemInfo("conChargeType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("account.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("account.id"));
        	sic.add(new SelectorItemInfo("account.number"));
        	sic.add(new SelectorItemInfo("account.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costedCompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("costedCompany.id"));
        	sic.add(new SelectorItemInfo("costedCompany.number"));
        	sic.add(new SelectorItemInfo("costedCompany.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costedDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("costedDept.id"));
        	sic.add(new SelectorItemInfo("costedDept.number"));
        	sic.add(new SelectorItemInfo("costedDept.name"));
		}
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }
    	

    /**
     * output actionDelVoucher_actionPerformed method
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
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
     * output actionViewBgBalance_actionPerformed method
     */
    public void actionViewBgBalance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewAttachment_actionPerformed method
     */
    public void actionViewAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewBudget_actionPerformed method
     */
    public void actionViewBudget_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
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
	public RequestContext prepareActionViewBgBalance(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewBgBalance() {
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
	public RequestContext prepareactionViewBudget(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionViewBudget() {
    	return false;
    }

    /**
     * output ActionViewBgBalance class
     */     
    protected class ActionViewBgBalance extends ItemAction {     
    
        public ActionViewBgBalance()
        {
            this(null);
        }

        public ActionViewBgBalance(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewBgBalance.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBgBalance.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBgBalance.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionViewBgBalance", "actionViewBgBalance_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "ActionViewAttachment", "actionViewAttachment_actionPerformed", e);
        }
    }

    /**
     * output actionViewBudget class
     */     
    protected class actionViewBudget extends ItemAction {     
    
        public actionViewBudget()
        {
            this(null);
        }

        public actionViewBudget(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionViewBudget.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionViewBudget.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionViewBudget.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractWithoutTextEditUI.this, "actionViewBudget", "actionViewBudget_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractWithoutTextEditUI");
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
        return com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.ContractWithoutTextFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.ContractWithoutTextInfo objectValue = new com.kingdee.eas.fdc.contract.ContractWithoutTextInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/contract/ContractWithoutText";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractWithoutTextQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtBgEntry;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
				vo.put("originalAmount",new java.math.BigDecimal(0));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}