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
public abstract class AbstractContractBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel mainPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel ecoItemPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlandDeveloper;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpartB;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpartC;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProj;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conContrarctRule;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conControlAmount;
    protected com.kingdee.bos.ctrl.swing.KDButton btnBuildPriceIndex;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractPropert;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsignDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRespDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsPartAMaterialCon;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttachmentNameList;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContrnt;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStampTaxAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStampTaxRate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkCostSplit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsPartAMaterialCon;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGrtRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcostProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractSource;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contExRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLocalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmtBig;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGrtAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchgPercForWarn;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayScale;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblOverRateContainer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFwContractTemp;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgAmtBig;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConSettleAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpayPercForWarn;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStrategyPact;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewInvite;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer tenderDiscusstion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conEntrustReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conChargeType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisContractor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractPrice;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkFiveClass;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDComboBox contractPropert;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pksignDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRespDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbookedDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox cbPeriod;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboModel;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboAttachmentNameList;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStampTaxAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStampTaxRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtGrtRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtamount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCurrency;
    protected com.kingdee.bos.ctrl.swing.KDComboBox costProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox contractSource;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtExRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLocalAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAmtBig;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtGrtAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtchgPercForWarn;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPayScale;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOverAmt;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrgAmtBig;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtConSettleAmout;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField ceremonyb;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField ceremonybb;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtpayPercForWarn;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtStrategyPact;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTenderDiscusstion;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtEntrustReason;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtModel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCharge;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcontractPrice;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMIndexType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtUnSplitAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSplitedAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtlandDeveloper;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcontractType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpartB;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpartC;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcontractName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProj;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlInviteInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlDetail;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlCost;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSplitEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCoopLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriceType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsSubMainContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conMainContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conEffectiveStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conEffectiveEndDate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInformation;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlowestPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlowerPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthigherPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmiddlePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthighestPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbasePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsecondPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contwinPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contwinUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfileNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contquantity;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtlowestPriceUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtlowerPriceUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtmiddlePriceUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmthigherPriceUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmthighestPriceUni;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblUnit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInviteExecudeInfo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCoopLevel;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPriceType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMainContract;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpEffectStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpEffectiveEndDate;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtInformation;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtlowestPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtlowerPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txthigherPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtmiddlePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txthighestPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtbasePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtsecondPrice;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinviteType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtwinPrice;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtwinUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfileNo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtquantity;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblDetail;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCost;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFwContract;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtControlAmount;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer contPayItem;
    protected com.kingdee.bos.ctrl.swing.KDContainer contBailItem;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblEconItem;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBailOriAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBailRate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBail;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBailOriAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBailRate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProgram;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelSplit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContent;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnContractPlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewCost;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewBgBalance;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewProgramContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewContent;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBgBalance;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewProg;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDelSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemContractPayPlan;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem enuItemViewCost;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemProgram;
    protected com.kingdee.eas.fdc.contract.ContractBillInfo editData = null;
    protected ActionSplit actionSplit = null;
    protected ActionViewContent actionViewContent = null;
    protected ActionContractPlan actionContractPlan = null;
    protected ActionDelSplit actionDelSplit = null;
    protected ActionViewCost actionViewCost = null;
    protected ActionViewBgBalance actionViewBgBalance = null;
    protected ActionViewAttachmentSelf actionViewAttachmentSelf = null;
    protected ActionViewContentSelf actionViewContentSelf = null;
    protected ActionProgram actionProgram = null;
    protected ActionViewProgramCon actionViewProgramCon = null;
    protected ActionAddTblBailLine actionAddTblBailLine = null;
    protected ActionRemoveTblBailLine actionRemoveTblBailLine = null;
    protected ActionViewInvite actionViewInvite = null;
    protected actionAcctSelect actionAcctSelect = null;
    protected actionSplitProj actionSplitProj = null;
    protected actionSplitBotUp actionSplitBotUp = null;
    protected actionSplitProd actionSplitProd = null;
    protected actionRemoveSplit actionRemoveSplit = null;
    protected ActionProgrAcctSelect actionProgrAcctSelect = null;
    protected ActionContractPriceSplit actionContractPriceSplit = null;
    protected ActionProfessionSplit actionProfessionSplit = null;
    /**
     * output class constructor
     */
    public AbstractContractBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractBillEditUI.class.getName());
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
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
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
        //actionSplit
        this.actionSplit = new ActionSplit(this);
        getActionManager().registerAction("actionSplit", actionSplit);
        this.actionSplit.setBindWorkFlow(true);
         this.actionSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContent
        this.actionViewContent = new ActionViewContent(this);
        getActionManager().registerAction("actionViewContent", actionViewContent);
         this.actionViewContent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionContractPlan
        this.actionContractPlan = new ActionContractPlan(this);
        getActionManager().registerAction("actionContractPlan", actionContractPlan);
         this.actionContractPlan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelSplit
        this.actionDelSplit = new ActionDelSplit(this);
        getActionManager().registerAction("actionDelSplit", actionDelSplit);
         this.actionDelSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewCost
        this.actionViewCost = new ActionViewCost(this);
        getActionManager().registerAction("actionViewCost", actionViewCost);
         this.actionViewCost.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBgBalance
        this.actionViewBgBalance = new ActionViewBgBalance(this);
        getActionManager().registerAction("actionViewBgBalance", actionViewBgBalance);
         this.actionViewBgBalance.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAttachmentSelf
        this.actionViewAttachmentSelf = new ActionViewAttachmentSelf(this);
        getActionManager().registerAction("actionViewAttachmentSelf", actionViewAttachmentSelf);
         this.actionViewAttachmentSelf.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContentSelf
        this.actionViewContentSelf = new ActionViewContentSelf(this);
        getActionManager().registerAction("actionViewContentSelf", actionViewContentSelf);
         this.actionViewContentSelf.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProgram
        this.actionProgram = new ActionProgram(this);
        getActionManager().registerAction("actionProgram", actionProgram);
         this.actionProgram.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewProgramCon
        this.actionViewProgramCon = new ActionViewProgramCon(this);
        getActionManager().registerAction("actionViewProgramCon", actionViewProgramCon);
         this.actionViewProgramCon.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddTblBailLine
        this.actionAddTblBailLine = new ActionAddTblBailLine(this);
        getActionManager().registerAction("actionAddTblBailLine", actionAddTblBailLine);
         this.actionAddTblBailLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveTblBailLine
        this.actionRemoveTblBailLine = new ActionRemoveTblBailLine(this);
        getActionManager().registerAction("actionRemoveTblBailLine", actionRemoveTblBailLine);
         this.actionRemoveTblBailLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewInvite
        this.actionViewInvite = new ActionViewInvite(this);
        getActionManager().registerAction("actionViewInvite", actionViewInvite);
         this.actionViewInvite.addService(new com.kingdee.eas.framework.client.service.PermissionService());
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
        //actionRemoveSplit
        this.actionRemoveSplit = new actionRemoveSplit(this);
        getActionManager().registerAction("actionRemoveSplit", actionRemoveSplit);
         this.actionRemoveSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProgrAcctSelect
        this.actionProgrAcctSelect = new ActionProgrAcctSelect(this);
        getActionManager().registerAction("actionProgrAcctSelect", actionProgrAcctSelect);
         this.actionProgrAcctSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionContractPriceSplit
        this.actionContractPriceSplit = new ActionContractPriceSplit(this);
        getActionManager().registerAction("actionContractPriceSplit", actionContractPriceSplit);
         this.actionContractPriceSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProfessionSplit
        this.actionProfessionSplit = new ActionProfessionSplit(this);
        getActionManager().registerAction("actionProfessionSplit", actionProfessionSplit);
         this.actionProfessionSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tabPanel = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.mainPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.ecoItemPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlandDeveloper = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpartB = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpartC = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProj = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.conContrarctRule = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conControlAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnBuildPriceIndex = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contcontractPropert = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsignDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRespDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRespPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIsPartAMaterialCon = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttachmentNameList = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewContrnt = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contStampTaxAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStampTaxRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkCostSplit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsPartAMaterialCon = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contGrtRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcostProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractSource = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contExRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLocalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmtBig = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGrtAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchgPercForWarn = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayScale = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblOverRateContainer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtFwContractTemp = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contOrgAmtBig = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConSettleAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpayPercForWarn = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStrategyPact = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnViewInvite = new com.kingdee.bos.ctrl.swing.KDButton();
        this.tenderDiscusstion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conEntrustReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conChargeType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisContractor = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contcontractPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkFiveClass = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contractPropert = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pksignDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtRespDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRespPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkbookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.cbPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtCreator = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboModel = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboAttachmentNameList = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtStampTaxAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStampTaxRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtGrtRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtamount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboCurrency = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.costProperty = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contractSource = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtExRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLocalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAmtBig = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtGrtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtchgPercForWarn = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPayScale = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOverAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOrgAmtBig = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtConSettleAmout = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.ceremonyb = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.ceremonybb = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtpayPercForWarn = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtInviteProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtStrategyPact = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtTenderDiscusstion = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtEntrustReason = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCharge = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcontractPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtMIndexType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtUnSplitAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSplitedAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtlandDeveloper = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcontractType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtpartB = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtpartC = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcontractName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProj = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlInviteInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlDetail = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlCost = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtSplitEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCoopLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPriceType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsSubMainContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.conMainContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conEffectiveStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conEffectiveEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.conInformation = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlowestPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlowerPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthigherPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmiddlePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthighestPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbasePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsecondPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contwinPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contwinUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfileNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contquantity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtlowestPriceUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtlowerPriceUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtmiddlePriceUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmthigherPriceUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmthighestPriceUni = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.lblPrice = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblUnit = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.btnInviteExecudeInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboCoopLevel = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboPriceType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtMainContract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdpEffectStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdpEffectiveEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtInformation = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtlowestPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtlowerPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txthigherPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtmiddlePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txthighestPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtbasePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtsecondPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtinviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtwinPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtwinUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtfileNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtquantity = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.tblDetail = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblCost = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtFwContract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtControlAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.contPayItem = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contBailItem = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblEconItem = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contBailOriAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBailRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tblBail = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtBailOriAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBailRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnProgram = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContent = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnContractPlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewCost = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewBgBalance = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewProgramContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewContent = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewBgBalance = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewProg = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDelSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemContractPayPlan = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.enuItemViewCost = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemProgram = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.tabPanel.setName("tabPanel");
        this.mainPanel.setName("mainPanel");
        this.ecoItemPanel.setName("ecoItemPanel");
        this.kDPanel1.setName("kDPanel1");
        this.contNumber.setName("contNumber");
        this.contlandDeveloper.setName("contlandDeveloper");
        this.contcontractType.setName("contcontractType");
        this.contpartB.setName("contpartB");
        this.contpartC.setName("contpartC");
        this.contcontractName.setName("contcontractName");
        this.contOrg.setName("contOrg");
        this.contProj.setName("contProj");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.conContrarctRule.setName("conContrarctRule");
        this.conControlAmount.setName("conControlAmount");
        this.btnBuildPriceIndex.setName("btnBuildPriceIndex");
        this.contcontractPropert.setName("contcontractPropert");
        this.contsignDate.setName("contsignDate");
        this.contRespDept.setName("contRespDept");
        this.contRespPerson.setName("contRespPerson");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contIsPartAMaterialCon.setName("contIsPartAMaterialCon");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contModel.setName("contModel");
        this.contAttachmentNameList.setName("contAttachmentNameList");
        this.btnViewContrnt.setName("btnViewContrnt");
        this.btnViewAttachment.setName("btnViewAttachment");
        this.contStampTaxAmt.setName("contStampTaxAmt");
        this.contStampTaxRate.setName("contStampTaxRate");
        this.chkCostSplit.setName("chkCostSplit");
        this.chkIsPartAMaterialCon.setName("chkIsPartAMaterialCon");
        this.contGrtRate.setName("contGrtRate");
        this.contamount.setName("contamount");
        this.contCurrency.setName("contCurrency");
        this.contcostProperty.setName("contcostProperty");
        this.contcontractSource.setName("contcontractSource");
        this.contExRate.setName("contExRate");
        this.contLocalAmount.setName("contLocalAmount");
        this.contAmtBig.setName("contAmtBig");
        this.contGrtAmount.setName("contGrtAmount");
        this.contchgPercForWarn.setName("contchgPercForWarn");
        this.contPayScale.setName("contPayScale");
        this.lblOverRateContainer.setName("lblOverRateContainer");
        this.prmtFwContractTemp.setName("prmtFwContractTemp");
        this.contOrgAmtBig.setName("contOrgAmtBig");
        this.contConSettleAmount.setName("contConSettleAmount");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.contpayPercForWarn.setName("contpayPercForWarn");
        this.contInviteProject.setName("contInviteProject");
        this.contStrategyPact.setName("contStrategyPact");
        this.btnViewInvite.setName("btnViewInvite");
        this.tenderDiscusstion.setName("tenderDiscusstion");
        this.conEntrustReason.setName("conEntrustReason");
        this.conModel.setName("conModel");
        this.conChargeType.setName("conChargeType");
        this.chkisContractor.setName("chkisContractor");
        this.contcontractPrice.setName("contcontractPrice");
        this.chkFiveClass.setName("chkFiveClass");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.contractPropert.setName("contractPropert");
        this.pksignDate.setName("pksignDate");
        this.prmtRespDept.setName("prmtRespDept");
        this.prmtRespPerson.setName("prmtRespPerson");
        this.pkbookedDate.setName("pkbookedDate");
        this.cbPeriod.setName("cbPeriod");
        this.txtCreator.setName("txtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.comboModel.setName("comboModel");
        this.comboAttachmentNameList.setName("comboAttachmentNameList");
        this.txtStampTaxAmt.setName("txtStampTaxAmt");
        this.txtStampTaxRate.setName("txtStampTaxRate");
        this.txtGrtRate.setName("txtGrtRate");
        this.txtamount.setName("txtamount");
        this.comboCurrency.setName("comboCurrency");
        this.costProperty.setName("costProperty");
        this.contractSource.setName("contractSource");
        this.txtExRate.setName("txtExRate");
        this.txtLocalAmount.setName("txtLocalAmount");
        this.txtAmtBig.setName("txtAmtBig");
        this.txtGrtAmount.setName("txtGrtAmount");
        this.txtchgPercForWarn.setName("txtchgPercForWarn");
        this.txtPayScale.setName("txtPayScale");
        this.txtOverAmt.setName("txtOverAmt");
        this.txtOrgAmtBig.setName("txtOrgAmtBig");
        this.txtConSettleAmout.setName("txtConSettleAmout");
        this.ceremonyb.setName("ceremonyb");
        this.ceremonybb.setName("ceremonybb");
        this.txtpayPercForWarn.setName("txtpayPercForWarn");
        this.prmtInviteProject.setName("prmtInviteProject");
        this.prmtStrategyPact.setName("prmtStrategyPact");
        this.prmtTenderDiscusstion.setName("prmtTenderDiscusstion");
        this.txtEntrustReason.setName("txtEntrustReason");
        this.prmtModel.setName("prmtModel");
        this.prmtCharge.setName("prmtCharge");
        this.txtcontractPrice.setName("txtcontractPrice");
        this.txtMIndexType.setName("txtMIndexType");
        this.txtUnSplitAmount.setName("txtUnSplitAmount");
        this.txtSplitedAmount.setName("txtSplitedAmount");
        this.txtNumber.setName("txtNumber");
        this.prmtlandDeveloper.setName("prmtlandDeveloper");
        this.prmtcontractType.setName("prmtcontractType");
        this.prmtpartB.setName("prmtpartB");
        this.prmtpartC.setName("prmtpartC");
        this.txtcontractName.setName("txtcontractName");
        this.txtOrg.setName("txtOrg");
        this.txtProj.setName("txtProj");
        this.kDPanel2.setName("kDPanel2");
        this.pnlInviteInfo.setName("pnlInviteInfo");
        this.pnlDetail.setName("pnlDetail");
        this.pnlCost.setName("pnlCost");
        this.kDContainer2.setName("kDContainer2");
        this.kdtSplitEntry.setName("kdtSplitEntry");
        this.contRemark.setName("contRemark");
        this.contCoopLevel.setName("contCoopLevel");
        this.contPriceType.setName("contPriceType");
        this.chkIsSubMainContract.setName("chkIsSubMainContract");
        this.conMainContract.setName("conMainContract");
        this.conEffectiveStartDate.setName("conEffectiveStartDate");
        this.conEffectiveEndDate.setName("conEffectiveEndDate");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.conInformation.setName("conInformation");
        this.contlowestPrice.setName("contlowestPrice");
        this.contlowerPrice.setName("contlowerPrice");
        this.conthigherPrice.setName("conthigherPrice");
        this.contmiddlePrice.setName("contmiddlePrice");
        this.conthighestPrice.setName("conthighestPrice");
        this.contbasePrice.setName("contbasePrice");
        this.contsecondPrice.setName("contsecondPrice");
        this.continviteType.setName("continviteType");
        this.contwinPrice.setName("contwinPrice");
        this.contwinUnit.setName("contwinUnit");
        this.contfileNo.setName("contfileNo");
        this.contquantity.setName("contquantity");
        this.prmtlowestPriceUnit.setName("prmtlowestPriceUnit");
        this.prmtlowerPriceUnit.setName("prmtlowerPriceUnit");
        this.prmtmiddlePriceUnit.setName("prmtmiddlePriceUnit");
        this.prmthigherPriceUnit.setName("prmthigherPriceUnit");
        this.prmthighestPriceUni.setName("prmthighestPriceUni");
        this.lblPrice.setName("lblPrice");
        this.lblUnit.setName("lblUnit");
        this.btnInviteExecudeInfo.setName("btnInviteExecudeInfo");
        this.txtRemark.setName("txtRemark");
        this.comboCoopLevel.setName("comboCoopLevel");
        this.comboPriceType.setName("comboPriceType");
        this.prmtMainContract.setName("prmtMainContract");
        this.kdpEffectStartDate.setName("kdpEffectStartDate");
        this.kdpEffectiveEndDate.setName("kdpEffectiveEndDate");
        this.txtInformation.setName("txtInformation");
        this.txtlowestPrice.setName("txtlowestPrice");
        this.txtlowerPrice.setName("txtlowerPrice");
        this.txthigherPrice.setName("txthigherPrice");
        this.txtmiddlePrice.setName("txtmiddlePrice");
        this.txthighestPrice.setName("txthighestPrice");
        this.txtbasePrice.setName("txtbasePrice");
        this.txtsecondPrice.setName("txtsecondPrice");
        this.prmtinviteType.setName("prmtinviteType");
        this.txtwinPrice.setName("txtwinPrice");
        this.prmtwinUnit.setName("prmtwinUnit");
        this.txtfileNo.setName("txtfileNo");
        this.txtquantity.setName("txtquantity");
        this.tblDetail.setName("tblDetail");
        this.tblCost.setName("tblCost");
        this.prmtFwContract.setName("prmtFwContract");
        this.txtControlAmount.setName("txtControlAmount");
        this.kDContainer1.setName("kDContainer1");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.contPayItem.setName("contPayItem");
        this.contBailItem.setName("contBailItem");
        this.tblEconItem.setName("tblEconItem");
        this.contBailOriAmount.setName("contBailOriAmount");
        this.contBailRate.setName("contBailRate");
        this.tblBail.setName("tblBail");
        this.txtBailOriAmount.setName("txtBailOriAmount");
        this.txtBailRate.setName("txtBailRate");
        this.btnProgram.setName("btnProgram");
        this.btnSplit.setName("btnSplit");
        this.btnDelSplit.setName("btnDelSplit");
        this.btnViewContent.setName("btnViewContent");
        this.btnContractPlan.setName("btnContractPlan");
        this.btnViewCost.setName("btnViewCost");
        this.btnViewBgBalance.setName("btnViewBgBalance");
        this.btnViewProgramContract.setName("btnViewProgramContract");
        this.menuItemViewContent.setName("menuItemViewContent");
        this.menuItemViewBgBalance.setName("menuItemViewBgBalance");
        this.menuItemViewProg.setName("menuItemViewProg");
        this.menuItemSplit.setName("menuItemSplit");
        this.menuItemDelSplit.setName("menuItemDelSplit");
        this.menuItemContractPayPlan.setName("menuItemContractPayPlan");
        this.enuItemViewCost.setName("enuItemViewCost");
        this.menuItemProgram.setName("menuItemProgram");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
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
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));		
        this.menuItemAudit.setMnemonic(65);		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setToolTipText(resHelper.getString("menuItemUnAudit.toolTipText"));		
        this.menuItemUnAudit.setMnemonic(85);
        // tabPanel
        // mainPanel
        // ecoItemPanel
        // kDPanel1
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelAlignment(7);		
        this.contNumber.setVisible(true);
        // contlandDeveloper		
        this.contlandDeveloper.setBoundLabelText(resHelper.getString("contlandDeveloper.boundLabelText"));		
        this.contlandDeveloper.setBoundLabelLength(100);		
        this.contlandDeveloper.setBoundLabelUnderline(true);		
        this.contlandDeveloper.setVisible(true);		
        this.contlandDeveloper.setBoundLabelAlignment(7);
        // contcontractType		
        this.contcontractType.setBoundLabelText(resHelper.getString("contcontractType.boundLabelText"));		
        this.contcontractType.setBoundLabelLength(100);		
        this.contcontractType.setBoundLabelUnderline(true);		
        this.contcontractType.setVisible(true);		
        this.contcontractType.setBoundLabelAlignment(7);
        // contpartB		
        this.contpartB.setBoundLabelText(resHelper.getString("contpartB.boundLabelText"));		
        this.contpartB.setBoundLabelLength(100);		
        this.contpartB.setBoundLabelUnderline(true);		
        this.contpartB.setVisible(true);		
        this.contpartB.setBoundLabelAlignment(7);
        // contpartC		
        this.contpartC.setBoundLabelText(resHelper.getString("contpartC.boundLabelText"));		
        this.contpartC.setBoundLabelLength(100);		
        this.contpartC.setBoundLabelUnderline(true);		
        this.contpartC.setVisible(true);		
        this.contpartC.setBoundLabelAlignment(7);
        // contcontractName		
        this.contcontractName.setBoundLabelText(resHelper.getString("contcontractName.boundLabelText"));		
        this.contcontractName.setBoundLabelLength(100);		
        this.contcontractName.setBoundLabelUnderline(true);		
        this.contcontractName.setVisible(true);		
        this.contcontractName.setBoundLabelAlignment(7);
        // contOrg		
        this.contOrg.setBoundLabelText(resHelper.getString("contOrg.boundLabelText"));		
        this.contOrg.setBoundLabelLength(100);		
        this.contOrg.setBoundLabelUnderline(true);		
        this.contOrg.setVisible(false);
        // contProj		
        this.contProj.setBoundLabelText(resHelper.getString("contProj.boundLabelText"));		
        this.contProj.setBoundLabelLength(100);		
        this.contProj.setBoundLabelUnderline(true);
        // kDTabbedPane1
        // conContrarctRule		
        this.conContrarctRule.setBoundLabelText(resHelper.getString("conContrarctRule.boundLabelText"));		
        this.conContrarctRule.setBoundLabelUnderline(true);		
        this.conContrarctRule.setBoundLabelLength(100);
        // conControlAmount		
        this.conControlAmount.setBoundLabelText(resHelper.getString("conControlAmount.boundLabelText"));		
        this.conControlAmount.setBoundLabelUnderline(true);		
        this.conControlAmount.setBoundLabelLength(100);		
        this.conControlAmount.setEnabled(false);
        // btnBuildPriceIndex		
        this.btnBuildPriceIndex.setText(resHelper.getString("btnBuildPriceIndex.text"));
        this.btnBuildPriceIndex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnBuildPriceIndex_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contcontractPropert		
        this.contcontractPropert.setBoundLabelText(resHelper.getString("contcontractPropert.boundLabelText"));		
        this.contcontractPropert.setBoundLabelLength(100);		
        this.contcontractPropert.setBoundLabelUnderline(true);		
        this.contcontractPropert.setVisible(true);		
        this.contcontractPropert.setBoundLabelAlignment(7);
        // contsignDate		
        this.contsignDate.setBoundLabelText(resHelper.getString("contsignDate.boundLabelText"));		
        this.contsignDate.setBoundLabelLength(100);		
        this.contsignDate.setBoundLabelUnderline(true);		
        this.contsignDate.setVisible(true);		
        this.contsignDate.setBoundLabelAlignment(7);
        // contRespDept		
        this.contRespDept.setBoundLabelText(resHelper.getString("contRespDept.boundLabelText"));		
        this.contRespDept.setBoundLabelLength(100);		
        this.contRespDept.setBoundLabelUnderline(true);
        // contRespPerson		
        this.contRespPerson.setBoundLabelText(resHelper.getString("contRespPerson.boundLabelText"));		
        this.contRespPerson.setBoundLabelLength(100);		
        this.contRespPerson.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setVisible(false);
        // contIsPartAMaterialCon		
        this.contIsPartAMaterialCon.setBoundLabelText(resHelper.getString("contIsPartAMaterialCon.boundLabelText"));		
        this.contIsPartAMaterialCon.setBoundLabelUnderline(true);		
        this.contIsPartAMaterialCon.setBoundLabelLength(100);		
        this.contIsPartAMaterialCon.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setBoundLabelAlignment(7);		
        this.contCreator.setVisible(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setBoundLabelAlignment(7);		
        this.contCreateTime.setVisible(true);
        // contModel		
        this.contModel.setBoundLabelLength(100);		
        this.contModel.setBoundLabelText(resHelper.getString("contModel.boundLabelText"));		
        this.contModel.setBoundLabelUnderline(true);
        // contAttachmentNameList		
        this.contAttachmentNameList.setBoundLabelText(resHelper.getString("contAttachmentNameList.boundLabelText"));		
        this.contAttachmentNameList.setBoundLabelLength(100);		
        this.contAttachmentNameList.setBoundLabelUnderline(true);
        // btnViewContrnt		
        this.btnViewContrnt.setText(resHelper.getString("btnViewContrnt.text"));
        this.btnViewContrnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnViewContrnt_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnViewAttachment
        this.btnViewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachmentSelf, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAttachment.setText(resHelper.getString("btnViewAttachment.text"));
        this.btnViewAttachment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnViewAttachment_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contStampTaxAmt		
        this.contStampTaxAmt.setBoundLabelText(resHelper.getString("contStampTaxAmt.boundLabelText"));		
        this.contStampTaxAmt.setBoundLabelLength(100);		
        this.contStampTaxAmt.setBoundLabelUnderline(true);
        // contStampTaxRate		
        this.contStampTaxRate.setBoundLabelText(resHelper.getString("contStampTaxRate.boundLabelText"));		
        this.contStampTaxRate.setBoundLabelLength(100);		
        this.contStampTaxRate.setBoundLabelUnderline(true);
        // chkCostSplit		
        this.chkCostSplit.setText(resHelper.getString("chkCostSplit.text"));		
        this.chkCostSplit.setToolTipText(resHelper.getString("chkCostSplit.toolTipText"));
        this.chkCostSplit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    chkCostSplit_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // chkIsPartAMaterialCon		
        this.chkIsPartAMaterialCon.setText(resHelper.getString("chkIsPartAMaterialCon.text"));
        this.chkIsPartAMaterialCon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    chkIsPartAMaterialCon_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // contGrtRate		
        this.contGrtRate.setBoundLabelText(resHelper.getString("contGrtRate.boundLabelText"));		
        this.contGrtRate.setBoundLabelLength(100);		
        this.contGrtRate.setBoundLabelUnderline(true);
        // contamount		
        this.contamount.setBoundLabelText(resHelper.getString("contamount.boundLabelText"));		
        this.contamount.setBoundLabelLength(100);		
        this.contamount.setBoundLabelUnderline(true);		
        this.contamount.setVisible(true);		
        this.contamount.setBoundLabelAlignment(7);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // contcostProperty		
        this.contcostProperty.setBoundLabelText(resHelper.getString("contcostProperty.boundLabelText"));		
        this.contcostProperty.setBoundLabelLength(100);		
        this.contcostProperty.setBoundLabelUnderline(true);		
        this.contcostProperty.setVisible(true);		
        this.contcostProperty.setBoundLabelAlignment(7);
        // contcontractSource		
        this.contcontractSource.setBoundLabelText(resHelper.getString("contcontractSource.boundLabelText"));		
        this.contcontractSource.setBoundLabelLength(100);		
        this.contcontractSource.setBoundLabelUnderline(true);		
        this.contcontractSource.setBoundLabelAlignment(7);
        // contExRate		
        this.contExRate.setBoundLabelText(resHelper.getString("contExRate.boundLabelText"));		
        this.contExRate.setBoundLabelLength(110);		
        this.contExRate.setBoundLabelUnderline(true);
        // contLocalAmount		
        this.contLocalAmount.setBoundLabelText(resHelper.getString("contLocalAmount.boundLabelText"));		
        this.contLocalAmount.setBoundLabelLength(110);		
        this.contLocalAmount.setBoundLabelUnderline(true);
        // contAmtBig		
        this.contAmtBig.setBoundLabelText(resHelper.getString("contAmtBig.boundLabelText"));		
        this.contAmtBig.setBoundLabelLength(110);		
        this.contAmtBig.setBoundLabelUnderline(true);		
        this.contAmtBig.setEnabled(false);
        // contGrtAmount		
        this.contGrtAmount.setBoundLabelText(resHelper.getString("contGrtAmount.boundLabelText"));		
        this.contGrtAmount.setBoundLabelLength(110);		
        this.contGrtAmount.setBoundLabelUnderline(true);
        // contchgPercForWarn		
        this.contchgPercForWarn.setBoundLabelText(resHelper.getString("contchgPercForWarn.boundLabelText"));		
        this.contchgPercForWarn.setBoundLabelLength(110);		
        this.contchgPercForWarn.setBoundLabelUnderline(true);		
        this.contchgPercForWarn.setVisible(true);		
        this.contchgPercForWarn.setBoundLabelAlignment(7);
        // contPayScale		
        this.contPayScale.setBoundLabelText(resHelper.getString("contPayScale.boundLabelText"));		
        this.contPayScale.setBoundLabelLength(110);		
        this.contPayScale.setBoundLabelUnderline(true);
        // lblOverRateContainer		
        this.lblOverRateContainer.setBoundLabelText(resHelper.getString("lblOverRateContainer.boundLabelText"));		
        this.lblOverRateContainer.setBoundLabelLength(110);		
        this.lblOverRateContainer.setBoundLabelUnderline(true);
        // prmtFwContractTemp		
        this.prmtFwContractTemp.setQueryInfo("com.kingdee.eas.fdc.contract.programming.app.ProgrammingContractF7Query");		
        this.prmtFwContractTemp.setCommitFormat("$number$");		
        this.prmtFwContractTemp.setDisplayFormat("$name$");		
        this.prmtFwContractTemp.setEditFormat("$number$");		
        this.prmtFwContractTemp.setVisible(false);
        // contOrgAmtBig		
        this.contOrgAmtBig.setBoundLabelText(resHelper.getString("contOrgAmtBig.boundLabelText"));		
        this.contOrgAmtBig.setBoundLabelLength(100);		
        this.contOrgAmtBig.setBoundLabelUnderline(true);		
        this.contOrgAmtBig.setVisible(false);
        // contConSettleAmount		
        this.contConSettleAmount.setBoundLabelText(resHelper.getString("contConSettleAmount.boundLabelText"));		
        this.contConSettleAmount.setBoundLabelLength(100);		
        this.contConSettleAmount.setBoundLabelUnderline(true);		
        this.contConSettleAmount.setEnabled(false);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(110);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setVisible(false);
        // contpayPercForWarn		
        this.contpayPercForWarn.setBoundLabelText(resHelper.getString("contpayPercForWarn.boundLabelText"));		
        this.contpayPercForWarn.setBoundLabelLength(110);		
        this.contpayPercForWarn.setBoundLabelUnderline(true);		
        this.contpayPercForWarn.setVisible(true);		
        this.contpayPercForWarn.setBoundLabelAlignment(7);
        // contInviteProject		
        this.contInviteProject.setBoundLabelText(resHelper.getString("contInviteProject.boundLabelText"));		
        this.contInviteProject.setBoundLabelLength(110);		
        this.contInviteProject.setBoundLabelUnderline(true);
        // contStrategyPact		
        this.contStrategyPact.setBoundLabelText(resHelper.getString("contStrategyPact.boundLabelText"));		
        this.contStrategyPact.setBoundLabelLength(110);		
        this.contStrategyPact.setBoundLabelUnderline(true);
        // btnViewInvite
        this.btnViewInvite.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewInvite, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewInvite.setText(resHelper.getString("btnViewInvite.text"));
        // tenderDiscusstion		
        this.tenderDiscusstion.setBoundLabelText(resHelper.getString("tenderDiscusstion.boundLabelText"));		
        this.tenderDiscusstion.setBoundLabelLength(110);		
        this.tenderDiscusstion.setBoundLabelUnderline(true);
        // conEntrustReason		
        this.conEntrustReason.setBoundLabelText(resHelper.getString("conEntrustReason.boundLabelText"));		
        this.conEntrustReason.setBoundLabelLength(110);		
        this.conEntrustReason.setBoundLabelUnderline(true);
        // conModel		
        this.conModel.setBoundLabelText(resHelper.getString("conModel.boundLabelText"));		
        this.conModel.setBoundLabelLength(100);		
        this.conModel.setBoundLabelUnderline(true);		
        this.conModel.setVisible(false);
        // conChargeType		
        this.conChargeType.setBoundLabelText(resHelper.getString("conChargeType.boundLabelText"));		
        this.conChargeType.setBoundLabelLength(100);		
        this.conChargeType.setBoundLabelUnderline(true);
        // chkisContractor		
        this.chkisContractor.setText(resHelper.getString("chkisContractor.text"));		
        this.chkisContractor.setVisible(true);		
        this.chkisContractor.setHorizontalAlignment(2);
        this.chkisContractor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkisContractor_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contcontractPrice		
        this.contcontractPrice.setBoundLabelText(resHelper.getString("contcontractPrice.boundLabelText"));		
        this.contcontractPrice.setBoundLabelLength(110);		
        this.contcontractPrice.setBoundLabelUnderline(true);		
        this.contcontractPrice.setVisible(true);
        // chkFiveClass		
        this.chkFiveClass.setText(resHelper.getString("chkFiveClass.text"));		
        this.chkFiveClass.setVisible(true);		
        this.chkFiveClass.setHorizontalAlignment(2);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(70);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(65);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // contractPropert		
        this.contractPropert.setVisible(true);		
        this.contractPropert.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.ContractPropertyEnum").toArray());		
        this.contractPropert.setEnabled(true);
        this.contractPropert.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    contractPropert_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pksignDate		
        this.pksignDate.setVisible(true);		
        this.pksignDate.setEnabled(true);
        // prmtRespDept		
        this.prmtRespDept.setDisplayFormat("$name$");		
        this.prmtRespDept.setEditFormat("$number");		
        this.prmtRespDept.setDefaultF7UIName("com.kingdee.eas.basedata.org.client.f7.AdminF7");		
        this.prmtRespDept.setCommitFormat("$number$");		
        this.prmtRespDept.setEditable(true);		
        this.prmtRespDept.setRequired(true);
        this.prmtRespDept.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRespDept_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtRespPerson		
        this.prmtRespPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtRespPerson.setDisplayFormat("$name$");		
        this.prmtRespPerson.setEditFormat("$number$");		
        this.prmtRespPerson.setCommitFormat("$number$");		
        this.prmtRespPerson.setRequired(true);		
        this.prmtRespPerson.setDefaultF7UIName("com.kingdee.eas.basedata.person.client.PersonF7UI");
        // pkbookedDate		
        this.pkbookedDate.setVisible(false);
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
        this.cbPeriod.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PeriodQuery");		
        this.cbPeriod.setEnabled(false);		
        this.cbPeriod.setVisible(false);
        // txtCreator		
        this.txtCreator.setEditable(false);
        this.txtCreator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtCreator_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setVisible(true);
        // comboModel
        this.comboModel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboModel_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboAttachmentNameList		
        this.comboAttachmentNameList.setEditable(true);
        // txtStampTaxAmt		
        this.txtStampTaxAmt.setDataType(1);		
        this.txtStampTaxAmt.setPrecision(2);		
        this.txtStampTaxAmt.setSupportedEmpty(true);
        // txtStampTaxRate		
        this.txtStampTaxRate.setDataType(1);		
        this.txtStampTaxRate.setPrecision(2);		
        this.txtStampTaxRate.setSupportedEmpty(true);
        this.txtStampTaxRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtStampTaxRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtGrtRate		
        this.txtGrtRate.setRequired(true);		
        this.txtGrtRate.setPrecision(2);		
        this.txtGrtRate.setDataType(5);
        this.txtGrtRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtGrtRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtamount		
        this.txtamount.setVisible(true);		
        this.txtamount.setDataType(1);		
        this.txtamount.setSupportedEmpty(true);		
        this.txtamount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtamount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtamount.setEnabled(true);		
        this.txtamount.setRequired(true);
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
            public void focusGained(java.awt.event.FocusEvent e) {
                try {
                    txtamount_focusGained(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // comboCurrency
        this.comboCurrency.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboCurrency_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // costProperty		
        this.costProperty.setVisible(true);		
        this.costProperty.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.CostPropertyEnum").toArray());		
        this.costProperty.setEnabled(true);		
        this.costProperty.setRequired(true);
        this.costProperty.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    costProperty_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contractSource		
        this.contractSource.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ContractSourceQuery");		
        this.contractSource.setCommitFormat("$number$");		
        this.contractSource.setDisplayFormat("$name$");		
        this.contractSource.setEditFormat("$number$");		
        this.contractSource.setRequired(true);
        this.contractSource.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    contractSource_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.contractSource.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    contractSource_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtExRate		
        this.txtExRate.setRequired(true);		
        this.txtExRate.setPrecision(10);		
        this.txtExRate.setDataType(1);
        this.txtExRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtExRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtLocalAmount		
        this.txtLocalAmount.setDataType(1);		
        this.txtLocalAmount.setRequired(true);		
        this.txtLocalAmount.setEditable(false);
        // txtAmtBig		
        this.txtAmtBig.setEnabled(false);
        // txtGrtAmount		
        this.txtGrtAmount.setDataType(1);		
        this.txtGrtAmount.setPrecision(2);
        // txtchgPercForWarn		
        this.txtchgPercForWarn.setVisible(true);		
        this.txtchgPercForWarn.setHorizontalAlignment(2);		
        this.txtchgPercForWarn.setDataType(1);		
        this.txtchgPercForWarn.setSupportedEmpty(true);		
        this.txtchgPercForWarn.setMinimumValue( new java.math.BigDecimal(0.0));		
        this.txtchgPercForWarn.setMaximumValue( new java.math.BigDecimal(100.0));		
        this.txtchgPercForWarn.setPrecision(2);		
        this.txtchgPercForWarn.setEnabled(true);		
        this.txtchgPercForWarn.setRequired(true);
        // txtPayScale		
        this.txtPayScale.setDataType(1);		
        this.txtPayScale.setPrecision(2);		
        this.txtPayScale.setSupportedEmpty(true);
        // txtOverAmt		
        this.txtOverAmt.setDataType(5);		
        this.txtOverAmt.setDataVerifierType(12);
        // txtOrgAmtBig		
        this.txtOrgAmtBig.setEnabled(false);
        // txtConSettleAmout		
        this.txtConSettleAmout.setEnabled(false);
        // ceremonyb		
        this.ceremonyb.setDataType(1);
        this.ceremonyb.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    ceremonyb_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.ceremonyb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                try {
                    ceremonyb_focusGained(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // ceremonybb		
        this.ceremonybb.setDataType(1);
        // txtpayPercForWarn		
        this.txtpayPercForWarn.setVisible(true);		
        this.txtpayPercForWarn.setHorizontalAlignment(2);		
        this.txtpayPercForWarn.setDataType(1);		
        this.txtpayPercForWarn.setSupportedEmpty(true);		
        this.txtpayPercForWarn.setMinimumValue( new java.math.BigDecimal(0.0));		
        this.txtpayPercForWarn.setMaximumValue( new java.math.BigDecimal(100.0));		
        this.txtpayPercForWarn.setPrecision(2);		
        this.txtpayPercForWarn.setEnabled(true);		
        this.txtpayPercForWarn.setRequired(true);
        // prmtInviteProject		
        this.prmtInviteProject.setEnabled(false);		
        this.prmtInviteProject.setDisplayFormat("$name$");		
        this.prmtInviteProject.setEditFormat("$number$");		
        this.prmtInviteProject.setCommitFormat("$number$");		
        this.prmtInviteProject.setQueryInfo("com.kingdee.eas.fdc.invite.app.InviteProjectQuery");
//        		prmtInviteProject.addSelectorListener(new SelectorListener() {
//			com.kingdee.eas.fdc.invite.client.InviteProjectListUI prmtInviteProject_F7ListUI = null;
//			public void willShow(SelectorEvent e) {
//				if (prmtInviteProject_F7ListUI == null) {
//					try {
//						prmtInviteProject_F7ListUI = new com.kingdee.eas.fdc.invite.client.InviteProjectListUI();
//					} catch (Exception e1) {
//						e1.printStackTrace();
//					}
//					HashMap ctx = new HashMap();
//					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmtInviteProject_F7ListUI));
//					prmtInviteProject_F7ListUI.setF7Use(true,ctx);
//					prmtInviteProject.setSelector(prmtInviteProject_F7ListUI);
//				}
//			}
//		});
					
        this.prmtInviteProject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    prmtInviteProject_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // prmtStrategyPact		
        this.prmtStrategyPact.setDisplayFormat("$name$");		
        this.prmtStrategyPact.setEditFormat("$number$");		
        this.prmtStrategyPact.setQueryInfo("com.kingdee.eas.fdc.invite.news.app.F7StrategyPactQuery");		
        this.prmtStrategyPact.setCommitFormat("$number$");
        this.prmtStrategyPact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    prmtStrategyPact_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // prmtTenderDiscusstion		
        this.prmtTenderDiscusstion.setEnabled(false);		
        this.prmtTenderDiscusstion.setQueryInfo("com.kingdee.eas.fdc.invite.news.app.TenderDiscusstionQuery");
        // txtEntrustReason
        // prmtModel		
        this.prmtModel.setCommitFormat("$name$");		
        this.prmtModel.setQueryInfo("com.kingdee.eas.fdc.basedata.app.AttachmentQuery");		
        this.prmtModel.setDisplayFormat("$attachID$ $name$");		
        this.prmtModel.setEditFormat("$attachID$");
        this.prmtModel.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtModel_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtModel.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtModel_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCharge		
        this.prmtCharge.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ContractChargeTypeQuery");		
        this.prmtCharge.setDisplayFormat("$name$");		
        this.prmtCharge.setEditFormat("$number$");		
        this.prmtCharge.setCommitFormat("$number$");
        // txtcontractPrice		
        this.txtcontractPrice.setVisible(true);		
        this.txtcontractPrice.setHorizontalAlignment(2);		
        this.txtcontractPrice.setDataType(1);		
        this.txtcontractPrice.setSupportedEmpty(true);		
        this.txtcontractPrice.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtcontractPrice.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtcontractPrice.setPrecision(2);		
        this.txtcontractPrice.setRequired(false);		
        this.txtcontractPrice.setEnabled(false);
        // txtMIndexType
        // txtUnSplitAmount
        this.txtUnSplitAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtUnSplitAmount_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.txtUnSplitAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtUnSplitAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtSplitedAmount
        this.txtSplitedAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtSplitedAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(true);
        // prmtlandDeveloper		
        this.prmtlandDeveloper.setQueryInfo("com.kingdee.eas.fdc.basedata.app.LandDeveloperQuery");		
        this.prmtlandDeveloper.setVisible(true);		
        this.prmtlandDeveloper.setEditable(true);		
        this.prmtlandDeveloper.setDisplayFormat("$name$");		
        this.prmtlandDeveloper.setEditFormat("$number$");		
        this.prmtlandDeveloper.setCommitFormat("$number$");		
        this.prmtlandDeveloper.setRequired(true);
        // prmtcontractType		
        this.prmtcontractType.setVisible(true);		
        this.prmtcontractType.setEditable(true);		
        this.prmtcontractType.setDisplayFormat("$name$");		
        this.prmtcontractType.setEditFormat("$number$");		
        this.prmtcontractType.setCommitFormat("$number$");		
        this.prmtcontractType.setRequired(true);		
        this.prmtcontractType.setDefaultF7UIName("com.kingdee.eas.fdc.basedata.client.ContractTypeF7UI");		
        this.prmtcontractType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ContractTypeQuery");
        this.prmtcontractType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtcontractType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtcontractType.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtcontractType_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtpartB		
        this.prmtpartB.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");		
        this.prmtpartB.setVisible(true);		
        this.prmtpartB.setEditable(true);		
        this.prmtpartB.setDisplayFormat("$name$");		
        this.prmtpartB.setEditFormat("$number$");		
        this.prmtpartB.setCommitFormat("$number$");		
        this.prmtpartB.setRequired(true);
        // prmtpartC		
        this.prmtpartC.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");		
        this.prmtpartC.setVisible(true);		
        this.prmtpartC.setEditable(true);		
        this.prmtpartC.setDisplayFormat("$name$");		
        this.prmtpartC.setEditFormat("$number$");		
        this.prmtpartC.setCommitFormat("$number$");
        // txtcontractName		
        this.txtcontractName.setVisible(true);		
        this.txtcontractName.setHorizontalAlignment(2);		
        this.txtcontractName.setMaxLength(100);		
        this.txtcontractName.setEnabled(true);		
        this.txtcontractName.setRequired(true);
        // txtOrg		
        this.txtOrg.setEditable(false);		
        this.txtOrg.setVisible(false);
        // txtProj		
        this.txtProj.setEditable(false);
        // kDPanel2
        // pnlInviteInfo
        // pnlDetail
        // pnlCost
        // kDContainer2		
        this.kDContainer2.setEnableActive(false);		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kdtSplitEntry
		String kdtSplitEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount.curProject.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"costAccount.curProject.name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"costAccount.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"costAccount.name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"workLoad\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"programming\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"splitScale\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"standard\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"product\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"costAccount.curProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"costAccount.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"splitType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"apportionType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"apportionValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"directAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"apportionValueTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"directAmountTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"otherRatioTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{costAccount.curProject.number}</t:Cell><t:Cell>$Resource{costAccount.curProject.name}</t:Cell><t:Cell>$Resource{costAccount.number}</t:Cell><t:Cell>$Resource{costAccount.name}</t:Cell><t:Cell>$Resource{workLoad}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{programming}</t:Cell><t:Cell>$Resource{splitScale}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{standard}</t:Cell><t:Cell>$Resource{product}</t:Cell><t:Cell>$Resource{costAccount.curProject.id}</t:Cell><t:Cell>$Resource{costAccount.id}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{splitType}</t:Cell><t:Cell>$Resource{apportionType.name}</t:Cell><t:Cell>$Resource{apportionValue}</t:Cell><t:Cell>$Resource{directAmount}</t:Cell><t:Cell>$Resource{apportionValueTotal}</t:Cell><t:Cell>$Resource{directAmountTotal}</t:Cell><t:Cell>$Resource{otherRatioTotal}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtSplitEntry.setFormatXml(resHelper.translateString("kdtSplitEntry",kdtSplitEntryStrXML));
        this.kdtSplitEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtSplitEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtSplitEntry.putBindContents("editData",new String[] {"id","","","","","workLoad","price","programmings","splitScale","amount","","product","costAccount.curProject.id","costAccount.id","level","splitType","apportionType.name","apportionValue","directAmount","apportionValueTotal","directAmountTotal","otherRatioTotal"});


        this.kdtSplitEntry.checkParsed();
        final KDBizPromptBox kdtSplitEntry_product_PromptBox = new KDBizPromptBox();
        kdtSplitEntry_product_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
        kdtSplitEntry_product_PromptBox.setVisible(true);
        kdtSplitEntry_product_PromptBox.setEditable(true);
        kdtSplitEntry_product_PromptBox.setDisplayFormat("$number$");
        kdtSplitEntry_product_PromptBox.setEditFormat("$number$");
        kdtSplitEntry_product_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtSplitEntry_product_CellEditor = new KDTDefaultCellEditor(kdtSplitEntry_product_PromptBox);
        this.kdtSplitEntry.getColumn("product").setEditor(kdtSplitEntry_product_CellEditor);
        ObjectValueRender kdtSplitEntry_product_OVR = new ObjectValueRender();
        kdtSplitEntry_product_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtSplitEntry.getColumn("product").setRenderer(kdtSplitEntry_product_OVR);
        KDComboBox kdtSplitEntry_splitType_ComboBox = new KDComboBox();
        kdtSplitEntry_splitType_ComboBox.setName("kdtSplitEntry_splitType_ComboBox");
        kdtSplitEntry_splitType_ComboBox.setVisible(true);
        kdtSplitEntry_splitType_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.CostSplitTypeEnum").toArray());
        KDTDefaultCellEditor kdtSplitEntry_splitType_CellEditor = new KDTDefaultCellEditor(kdtSplitEntry_splitType_ComboBox);
        this.kdtSplitEntry.getColumn("splitType").setEditor(kdtSplitEntry_splitType_CellEditor);
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelLength(100);		
        this.contRemark.setBoundLabelUnderline(true);		
        this.contRemark.setVisible(false);
        // contCoopLevel		
        this.contCoopLevel.setBoundLabelText(resHelper.getString("contCoopLevel.boundLabelText"));		
        this.contCoopLevel.setBoundLabelLength(100);		
        this.contCoopLevel.setBoundLabelUnderline(true);
        // contPriceType		
        this.contPriceType.setBoundLabelText(resHelper.getString("contPriceType.boundLabelText"));		
        this.contPriceType.setBoundLabelLength(100);		
        this.contPriceType.setBoundLabelUnderline(true);		
        this.contPriceType.setVisible(false);
        // chkIsSubMainContract		
        this.chkIsSubMainContract.setText(resHelper.getString("chkIsSubMainContract.text"));
        // conMainContract		
        this.conMainContract.setBoundLabelText(resHelper.getString("conMainContract.boundLabelText"));		
        this.conMainContract.setBoundLabelUnderline(true);		
        this.conMainContract.setBoundLabelLength(100);
        // conEffectiveStartDate		
        this.conEffectiveStartDate.setBoundLabelText(resHelper.getString("conEffectiveStartDate.boundLabelText"));		
        this.conEffectiveStartDate.setBoundLabelUnderline(true);		
        this.conEffectiveStartDate.setBoundLabelLength(100);
        // conEffectiveEndDate		
        this.conEffectiveEndDate.setBoundLabelText(resHelper.getString("conEffectiveEndDate.boundLabelText"));		
        this.conEffectiveEndDate.setBoundLabelLength(100);		
        this.conEffectiveEndDate.setBoundLabelUnderline(true);
        // kDScrollPane1
        // conInformation		
        this.conInformation.setBoundLabelText(resHelper.getString("conInformation.boundLabelText"));		
        this.conInformation.setBoundLabelUnderline(true);
        // contlowestPrice		
        this.contlowestPrice.setBoundLabelText(resHelper.getString("contlowestPrice.boundLabelText"));		
        this.contlowestPrice.setBoundLabelLength(100);		
        this.contlowestPrice.setBoundLabelUnderline(true);		
        this.contlowestPrice.setBoundLabelAlignment(7);
        // contlowerPrice		
        this.contlowerPrice.setBoundLabelText(resHelper.getString("contlowerPrice.boundLabelText"));		
        this.contlowerPrice.setBoundLabelLength(100);		
        this.contlowerPrice.setBoundLabelUnderline(true);		
        this.contlowerPrice.setBoundLabelAlignment(7);
        // conthigherPrice		
        this.conthigherPrice.setBoundLabelText(resHelper.getString("conthigherPrice.boundLabelText"));		
        this.conthigherPrice.setBoundLabelLength(100);		
        this.conthigherPrice.setBoundLabelUnderline(true);		
        this.conthigherPrice.setVisible(true);		
        this.conthigherPrice.setBoundLabelAlignment(7);
        // contmiddlePrice		
        this.contmiddlePrice.setBoundLabelText(resHelper.getString("contmiddlePrice.boundLabelText"));		
        this.contmiddlePrice.setBoundLabelLength(100);		
        this.contmiddlePrice.setBoundLabelUnderline(true);		
        this.contmiddlePrice.setBoundLabelAlignment(7);
        // conthighestPrice		
        this.conthighestPrice.setBoundLabelText(resHelper.getString("conthighestPrice.boundLabelText"));		
        this.conthighestPrice.setBoundLabelLength(100);		
        this.conthighestPrice.setBoundLabelUnderline(true);		
        this.conthighestPrice.setVisible(true);		
        this.conthighestPrice.setBoundLabelAlignment(7);
        // contbasePrice		
        this.contbasePrice.setBoundLabelText(resHelper.getString("contbasePrice.boundLabelText"));		
        this.contbasePrice.setBoundLabelLength(100);		
        this.contbasePrice.setBoundLabelUnderline(true);		
        this.contbasePrice.setVisible(true);		
        this.contbasePrice.setBoundLabelAlignment(7);
        // contsecondPrice		
        this.contsecondPrice.setBoundLabelText(resHelper.getString("contsecondPrice.boundLabelText"));		
        this.contsecondPrice.setBoundLabelLength(100);		
        this.contsecondPrice.setBoundLabelUnderline(true);		
        this.contsecondPrice.setVisible(true);		
        this.contsecondPrice.setBoundLabelAlignment(7);
        // continviteType		
        this.continviteType.setBoundLabelText(resHelper.getString("continviteType.boundLabelText"));		
        this.continviteType.setBoundLabelLength(100);		
        this.continviteType.setBoundLabelUnderline(true);		
        this.continviteType.setVisible(true);		
        this.continviteType.setBoundLabelAlignment(7);
        // contwinPrice		
        this.contwinPrice.setBoundLabelText(resHelper.getString("contwinPrice.boundLabelText"));		
        this.contwinPrice.setBoundLabelLength(100);		
        this.contwinPrice.setBoundLabelUnderline(true);		
        this.contwinPrice.setVisible(true);		
        this.contwinPrice.setBoundLabelAlignment(7);
        // contwinUnit		
        this.contwinUnit.setBoundLabelText(resHelper.getString("contwinUnit.boundLabelText"));		
        this.contwinUnit.setBoundLabelLength(100);		
        this.contwinUnit.setBoundLabelUnderline(true);		
        this.contwinUnit.setVisible(true);		
        this.contwinUnit.setBoundLabelAlignment(7);
        // contfileNo		
        this.contfileNo.setBoundLabelText(resHelper.getString("contfileNo.boundLabelText"));		
        this.contfileNo.setBoundLabelLength(100);		
        this.contfileNo.setBoundLabelUnderline(true);		
        this.contfileNo.setVisible(true);		
        this.contfileNo.setBoundLabelAlignment(7);
        // contquantity		
        this.contquantity.setBoundLabelText(resHelper.getString("contquantity.boundLabelText"));		
        this.contquantity.setBoundLabelLength(100);		
        this.contquantity.setBoundLabelUnderline(true);		
        this.contquantity.setVisible(true);		
        this.contquantity.setBoundLabelAlignment(7);
        // prmtlowestPriceUnit		
        this.prmtlowestPriceUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtlowestPriceUnit.setVisible(true);		
        this.prmtlowestPriceUnit.setEditable(true);		
        this.prmtlowestPriceUnit.setDisplayFormat("$number$  $name$");		
        this.prmtlowestPriceUnit.setEditFormat("$number$");		
        this.prmtlowestPriceUnit.setCommitFormat("$number$");
        // prmtlowerPriceUnit		
        this.prmtlowerPriceUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtlowerPriceUnit.setVisible(true);		
        this.prmtlowerPriceUnit.setEditable(true);		
        this.prmtlowerPriceUnit.setDisplayFormat("$number$ $name$");		
        this.prmtlowerPriceUnit.setEditFormat("$number$");		
        this.prmtlowerPriceUnit.setCommitFormat("$number$");
        // prmtmiddlePriceUnit		
        this.prmtmiddlePriceUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtmiddlePriceUnit.setVisible(true);		
        this.prmtmiddlePriceUnit.setEditable(true);		
        this.prmtmiddlePriceUnit.setDisplayFormat("$number$ $name$");		
        this.prmtmiddlePriceUnit.setEditFormat("$number$");		
        this.prmtmiddlePriceUnit.setCommitFormat("$number$");
        // prmthigherPriceUnit		
        this.prmthigherPriceUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmthigherPriceUnit.setVisible(true);		
        this.prmthigherPriceUnit.setEditable(true);		
        this.prmthigherPriceUnit.setDisplayFormat("$number$ $name$");		
        this.prmthigherPriceUnit.setEditFormat("$number$");		
        this.prmthigherPriceUnit.setCommitFormat("$number$");
        // prmthighestPriceUni		
        this.prmthighestPriceUni.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmthighestPriceUni.setVisible(true);		
        this.prmthighestPriceUni.setEditable(true);		
        this.prmthighestPriceUni.setDisplayFormat("$number$ $name$");		
        this.prmthighestPriceUni.setEditFormat("$number$");		
        this.prmthighestPriceUni.setCommitFormat("$number$");
        // lblPrice		
        this.lblPrice.setText(resHelper.getString("lblPrice.text"));
        // lblUnit		
        this.lblUnit.setText(resHelper.getString("lblUnit.text"));
        // btnInviteExecudeInfo		
        this.btnInviteExecudeInfo.setText(resHelper.getString("btnInviteExecudeInfo.text"));
        // txtRemark
        // comboCoopLevel		
        this.comboCoopLevel.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.CoopLevelEnum").toArray());
        // comboPriceType		
        this.comboPriceType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.PriceTypeEnum").toArray());
        // prmtMainContract		
        this.prmtMainContract.setDisplayFormat("$name$");		
        this.prmtMainContract.setEditFormat("$number$");		
        this.prmtMainContract.setCommitFormat("$number$");		
        this.prmtMainContract.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7MainContractBillQuery");
        this.prmtMainContract.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtMainContract_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdpEffectStartDate
        // kdpEffectiveEndDate
        // txtInformation
        // txtlowestPrice		
        this.txtlowestPrice.setVisible(true);		
        this.txtlowestPrice.setHorizontalAlignment(2);		
        this.txtlowestPrice.setDataType(1);		
        this.txtlowestPrice.setSupportedEmpty(true);		
        this.txtlowestPrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtlowestPrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtlowestPrice.setPrecision(2);		
        this.txtlowestPrice.setEnabled(true);
        // txtlowerPrice		
        this.txtlowerPrice.setVisible(true);		
        this.txtlowerPrice.setHorizontalAlignment(2);		
        this.txtlowerPrice.setDataType(1);		
        this.txtlowerPrice.setSupportedEmpty(true);		
        this.txtlowerPrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtlowerPrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtlowerPrice.setPrecision(2);		
        this.txtlowerPrice.setEnabled(true);
        // txthigherPrice		
        this.txthigherPrice.setVisible(true);		
        this.txthigherPrice.setHorizontalAlignment(2);		
        this.txthigherPrice.setDataType(1);		
        this.txthigherPrice.setSupportedEmpty(true);		
        this.txthigherPrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txthigherPrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txthigherPrice.setPrecision(2);		
        this.txthigherPrice.setEnabled(true);
        // txtmiddlePrice		
        this.txtmiddlePrice.setVisible(true);		
        this.txtmiddlePrice.setHorizontalAlignment(2);		
        this.txtmiddlePrice.setDataType(1);		
        this.txtmiddlePrice.setSupportedEmpty(true);		
        this.txtmiddlePrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtmiddlePrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtmiddlePrice.setPrecision(2);		
        this.txtmiddlePrice.setEnabled(true);
        // txthighestPrice		
        this.txthighestPrice.setVisible(true);		
        this.txthighestPrice.setHorizontalAlignment(2);		
        this.txthighestPrice.setDataType(1);		
        this.txthighestPrice.setSupportedEmpty(true);		
        this.txthighestPrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txthighestPrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txthighestPrice.setPrecision(2);		
        this.txthighestPrice.setEnabled(true);
        // txtbasePrice		
        this.txtbasePrice.setVisible(true);		
        this.txtbasePrice.setHorizontalAlignment(2);		
        this.txtbasePrice.setDataType(1);		
        this.txtbasePrice.setSupportedEmpty(true);		
        this.txtbasePrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtbasePrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtbasePrice.setPrecision(2);		
        this.txtbasePrice.setEnabled(true);
        // txtsecondPrice		
        this.txtsecondPrice.setVisible(true);		
        this.txtsecondPrice.setHorizontalAlignment(2);		
        this.txtsecondPrice.setDataType(1);		
        this.txtsecondPrice.setSupportedEmpty(true);		
        this.txtsecondPrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtsecondPrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtsecondPrice.setPrecision(2);		
        this.txtsecondPrice.setEnabled(true);
        // prmtinviteType		
        this.prmtinviteType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.InviteTypeQuery");		
        this.prmtinviteType.setVisible(true);		
        this.prmtinviteType.setEditable(true);		
        this.prmtinviteType.setDisplayFormat("$number$ $name$");		
        this.prmtinviteType.setEditFormat("$number$");		
        this.prmtinviteType.setCommitFormat("$number$");
        // txtwinPrice		
        this.txtwinPrice.setVisible(true);		
        this.txtwinPrice.setHorizontalAlignment(2);		
        this.txtwinPrice.setDataType(1);		
        this.txtwinPrice.setSupportedEmpty(true);		
        this.txtwinPrice.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtwinPrice.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtwinPrice.setPrecision(2);		
        this.txtwinPrice.setEnabled(true);
        // prmtwinUnit		
        this.prmtwinUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");		
        this.prmtwinUnit.setVisible(true);		
        this.prmtwinUnit.setEditable(true);		
        this.prmtwinUnit.setDisplayFormat("$number$ $name$");		
        this.prmtwinUnit.setEditFormat("$number$");		
        this.prmtwinUnit.setCommitFormat("$number$");
        // txtfileNo		
        this.txtfileNo.setVisible(true);		
        this.txtfileNo.setHorizontalAlignment(2);		
        this.txtfileNo.setMaxLength(100);		
        this.txtfileNo.setEnabled(true);
        // txtquantity		
        this.txtquantity.setVisible(true);		
        this.txtquantity.setHorizontalAlignment(2);		
        this.txtquantity.setDataType(1);		
        this.txtquantity.setSupportedEmpty(true);		
        this.txtquantity.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtquantity.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtquantity.setPrecision(4);		
        this.txtquantity.setEnabled(true);
        // tblDetail
		String tblDetailStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"detail\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"content\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"desc\" t:width=\"500\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"rowKey\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"dataType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"detailDef.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{detail}</t:Cell><t:Cell>$Resource{content}</t:Cell><t:Cell>$Resource{desc}</t:Cell><t:Cell>$Resource{rowKey}</t:Cell><t:Cell>$Resource{dataType}</t:Cell><t:Cell>$Resource{detailDef.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblDetail.setFormatXml(resHelper.translateString("tblDetail",tblDetailStrXML));
        this.tblDetail.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblDetail_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblDetail.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblDetail_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.tblDetail.checkParsed();
        // tblCost
		String tblCostStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"acctNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"acctName\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"aimCost\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"hasHappen\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"intendingHappen\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"dynamicCost\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"chayi\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{acctNumber}</t:Cell><t:Cell>$Resource{acctName}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{hasHappen}</t:Cell><t:Cell>$Resource{intendingHappen}</t:Cell><t:Cell>$Resource{dynamicCost}</t:Cell><t:Cell>$Resource{chayi}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblCost.setFormatXml(resHelper.translateString("tblCost",tblCostStrXML));
        this.tblCost.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblCost_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        this.tblCost.checkParsed();
        // prmtFwContract		
        this.prmtFwContract.setRequired(true);
        this.prmtFwContract.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtFwContract_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtControlAmount		
        this.txtControlAmount.setDataType(1);		
        this.txtControlAmount.setEditable(false);		
        this.txtControlAmount.setEnabled(false);
        // kDContainer1		
        this.kDContainer1.setEnableActive(false);
        // kDSplitPane1		
        this.kDSplitPane1.setOrientation(0);		
        this.kDSplitPane1.setResizeWeight(0.5);
        // contPayItem		
        this.contPayItem.setTitle(resHelper.getString("contPayItem.title"));
        // contBailItem		
        this.contBailItem.setTitle(resHelper.getString("contBailItem.title"));
        // tblEconItem
		String tblEconItemStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>###.00</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>###,##0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"payType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"payCondition\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"payRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"payAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"desc\" t:width=\"390\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{payType}</t:Cell><t:Cell>$Resource{payCondition}</t:Cell><t:Cell>$Resource{payRate}</t:Cell><t:Cell>$Resource{payAmount}</t:Cell><t:Cell>$Resource{desc}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblEconItem.setFormatXml(resHelper.translateString("tblEconItem",tblEconItemStrXML));
        this.tblEconItem.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblEconItem_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblEconItem.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblEconItem_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblEconItem.putBindContents("editData",new String[] {"payItemDate","paymentType","payCondition","prop","amount","desc"});


        this.tblEconItem.checkParsed();
        KDDatePicker tblEconItem_date_DatePicker = new KDDatePicker();
        tblEconItem_date_DatePicker.setName("tblEconItem_date_DatePicker");
        tblEconItem_date_DatePicker.setVisible(true);
        tblEconItem_date_DatePicker.setEditable(true);
        KDTDefaultCellEditor tblEconItem_date_CellEditor = new KDTDefaultCellEditor(tblEconItem_date_DatePicker);
        this.tblEconItem.getColumn("date").setEditor(tblEconItem_date_CellEditor);
        final KDBizPromptBox tblEconItem_payType_PromptBox = new KDBizPromptBox();
        tblEconItem_payType_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
        tblEconItem_payType_PromptBox.setVisible(true);
        tblEconItem_payType_PromptBox.setEditable(true);
        tblEconItem_payType_PromptBox.setDisplayFormat("$number$");
        tblEconItem_payType_PromptBox.setEditFormat("$number$");
        tblEconItem_payType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor tblEconItem_payType_CellEditor = new KDTDefaultCellEditor(tblEconItem_payType_PromptBox);
        this.tblEconItem.getColumn("payType").setEditor(tblEconItem_payType_CellEditor);
        ObjectValueRender tblEconItem_payType_OVR = new ObjectValueRender();
        tblEconItem_payType_OVR.setFormat(new BizDataFormat("$name$"));
        this.tblEconItem.getColumn("payType").setRenderer(tblEconItem_payType_OVR);
        KDTextField tblEconItem_payCondition_TextField = new KDTextField();
        tblEconItem_payCondition_TextField.setName("tblEconItem_payCondition_TextField");
        tblEconItem_payCondition_TextField.setMaxLength(200);
        KDTDefaultCellEditor tblEconItem_payCondition_CellEditor = new KDTDefaultCellEditor(tblEconItem_payCondition_TextField);
        this.tblEconItem.getColumn("payCondition").setEditor(tblEconItem_payCondition_CellEditor);
        KDFormattedTextField tblEconItem_payRate_TextField = new KDFormattedTextField();
        tblEconItem_payRate_TextField.setName("tblEconItem_payRate_TextField");
        tblEconItem_payRate_TextField.setVisible(true);
        tblEconItem_payRate_TextField.setEditable(true);
        tblEconItem_payRate_TextField.setHorizontalAlignment(2);
        tblEconItem_payRate_TextField.setDataType(1);
        	tblEconItem_payRate_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	tblEconItem_payRate_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        tblEconItem_payRate_TextField.setPrecision(10);
        KDTDefaultCellEditor tblEconItem_payRate_CellEditor = new KDTDefaultCellEditor(tblEconItem_payRate_TextField);
        this.tblEconItem.getColumn("payRate").setEditor(tblEconItem_payRate_CellEditor);
        KDFormattedTextField tblEconItem_payAmount_TextField = new KDFormattedTextField();
        tblEconItem_payAmount_TextField.setName("tblEconItem_payAmount_TextField");
        tblEconItem_payAmount_TextField.setVisible(true);
        tblEconItem_payAmount_TextField.setEditable(true);
        tblEconItem_payAmount_TextField.setHorizontalAlignment(2);
        tblEconItem_payAmount_TextField.setDataType(1);
        	tblEconItem_payAmount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	tblEconItem_payAmount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        tblEconItem_payAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor tblEconItem_payAmount_CellEditor = new KDTDefaultCellEditor(tblEconItem_payAmount_TextField);
        this.tblEconItem.getColumn("payAmount").setEditor(tblEconItem_payAmount_CellEditor);
        KDTextField tblEconItem_desc_TextField = new KDTextField();
        tblEconItem_desc_TextField.setName("tblEconItem_desc_TextField");
        tblEconItem_desc_TextField.setMaxLength(250);
        KDTDefaultCellEditor tblEconItem_desc_CellEditor = new KDTDefaultCellEditor(tblEconItem_desc_TextField);
        this.tblEconItem.getColumn("desc").setEditor(tblEconItem_desc_CellEditor);
        // contBailOriAmount		
        this.contBailOriAmount.setBoundLabelText(resHelper.getString("contBailOriAmount.boundLabelText"));		
        this.contBailOriAmount.setBoundLabelLength(140);		
        this.contBailOriAmount.setBoundLabelUnderline(true);
        // contBailRate		
        this.contBailRate.setBoundLabelText(resHelper.getString("contBailRate.boundLabelText"));		
        this.contBailRate.setBoundLabelLength(140);		
        this.contBailRate.setBoundLabelUnderline(true);
        // tblBail
		String tblBailStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>###.00</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###,##0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"bailDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bailCondition\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bailRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"bailAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"desc\" t:width=\"460\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{bailDate}</t:Cell><t:Cell>$Resource{bailCondition}</t:Cell><t:Cell>$Resource{bailRate}</t:Cell><t:Cell>$Resource{bailAmount}</t:Cell><t:Cell>$Resource{desc}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblBail.setFormatXml(resHelper.translateString("tblBail",tblBailStrXML));
        this.tblBail.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblBail_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblBail.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblBail_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblBail.putBindContents("editData",new String[] {"ail.entry.bailDate","ail.entry.bailConditon","ail.entry.prop","ail.entry.amount","ail.entry.desc"});


        this.tblBail.checkParsed();
        KDDatePicker tblBail_bailDate_DatePicker = new KDDatePicker();
        tblBail_bailDate_DatePicker.setName("tblBail_bailDate_DatePicker");
        tblBail_bailDate_DatePicker.setVisible(true);
        tblBail_bailDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor tblBail_bailDate_CellEditor = new KDTDefaultCellEditor(tblBail_bailDate_DatePicker);
        this.tblBail.getColumn("bailDate").setEditor(tblBail_bailDate_CellEditor);
        KDTextField tblBail_bailCondition_TextField = new KDTextField();
        tblBail_bailCondition_TextField.setName("tblBail_bailCondition_TextField");
        tblBail_bailCondition_TextField.setMaxLength(200);
        KDTDefaultCellEditor tblBail_bailCondition_CellEditor = new KDTDefaultCellEditor(tblBail_bailCondition_TextField);
        this.tblBail.getColumn("bailCondition").setEditor(tblBail_bailCondition_CellEditor);
        KDFormattedTextField tblBail_bailRate_TextField = new KDFormattedTextField();
        tblBail_bailRate_TextField.setName("tblBail_bailRate_TextField");
        tblBail_bailRate_TextField.setVisible(true);
        tblBail_bailRate_TextField.setEditable(true);
        tblBail_bailRate_TextField.setHorizontalAlignment(2);
        tblBail_bailRate_TextField.setDataType(1);
        	tblBail_bailRate_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	tblBail_bailRate_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        tblBail_bailRate_TextField.setPrecision(10);
        KDTDefaultCellEditor tblBail_bailRate_CellEditor = new KDTDefaultCellEditor(tblBail_bailRate_TextField);
        this.tblBail.getColumn("bailRate").setEditor(tblBail_bailRate_CellEditor);
        KDFormattedTextField tblBail_bailAmount_TextField = new KDFormattedTextField();
        tblBail_bailAmount_TextField.setName("tblBail_bailAmount_TextField");
        tblBail_bailAmount_TextField.setVisible(true);
        tblBail_bailAmount_TextField.setEditable(true);
        tblBail_bailAmount_TextField.setHorizontalAlignment(2);
        tblBail_bailAmount_TextField.setDataType(1);
        	tblBail_bailAmount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	tblBail_bailAmount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        tblBail_bailAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor tblBail_bailAmount_CellEditor = new KDTDefaultCellEditor(tblBail_bailAmount_TextField);
        this.tblBail.getColumn("bailAmount").setEditor(tblBail_bailAmount_CellEditor);
        KDTextField tblBail_desc_TextField = new KDTextField();
        tblBail_desc_TextField.setName("tblBail_desc_TextField");
        tblBail_desc_TextField.setMaxLength(250);
        KDTDefaultCellEditor tblBail_desc_CellEditor = new KDTDefaultCellEditor(tblBail_desc_TextField);
        this.tblBail.getColumn("desc").setEditor(tblBail_desc_CellEditor);
        // txtBailOriAmount		
        this.txtBailOriAmount.setDataType(1);
        this.txtBailOriAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtBailOriAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtBailRate		
        this.txtBailRate.setDataType(1);
        this.txtBailRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtBailRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnProgram
        this.btnProgram.setAction((IItemAction)ActionProxyFactory.getProxy(actionProgram, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProgram.setText(resHelper.getString("btnProgram.text"));		
        this.btnProgram.setToolTipText(resHelper.getString("btnProgram.toolTipText"));		
        this.btnProgram.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_associatecreate"));		
        this.btnProgram.setVisible(false);
        // btnSplit
        this.btnSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplit.setText(resHelper.getString("btnSplit.text"));		
        this.btnSplit.setToolTipText(resHelper.getString("btnSplit.toolTipText"));
        // btnDelSplit
        this.btnDelSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelSplit.setText(resHelper.getString("btnDelSplit.text"));		
        this.btnDelSplit.setToolTipText(resHelper.getString("btnDelSplit.toolTipText"));
        // btnViewContent
        this.btnViewContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContent.setText(resHelper.getString("btnViewContent.text"));		
        this.btnViewContent.setToolTipText(resHelper.getString("btnViewContent.toolTipText"));
        // btnContractPlan
        this.btnContractPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionContractPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnContractPlan.setText(resHelper.getString("btnContractPlan.text"));		
        this.btnContractPlan.setEnabled(false);		
        this.btnContractPlan.setVisible(false);
        // btnViewCost
        this.btnViewCost.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewCost, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewCost.setText(resHelper.getString("btnViewCost.text"));		
        this.btnViewCost.setToolTipText(resHelper.getString("btnViewCost.toolTipText"));
        // btnViewBgBalance
        this.btnViewBgBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBgBalance.setText(resHelper.getString("btnViewBgBalance.text"));		
        this.btnViewBgBalance.setToolTipText(resHelper.getString("btnViewBgBalance.toolTipText"));
        // btnViewProgramContract
        this.btnViewProgramContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewProgramCon, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewProgramContract.setText(resHelper.getString("btnViewProgramContract.text"));		
        this.btnViewProgramContract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showlist"));		
        this.btnViewProgramContract.setVisible(false);		
        this.btnViewProgramContract.setEnabled(false);
        // menuItemViewContent
        this.menuItemViewContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewContent.setText(resHelper.getString("menuItemViewContent.text"));		
        this.menuItemViewContent.setToolTipText(resHelper.getString("menuItemViewContent.toolTipText"));
        // menuItemViewBgBalance
        this.menuItemViewBgBalance.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBgBalance, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewBgBalance.setText(resHelper.getString("menuItemViewBgBalance.text"));		
        this.menuItemViewBgBalance.setVerticalTextPosition(3);		
        this.menuItemViewBgBalance.setToolTipText(resHelper.getString("menuItemViewBgBalance.toolTipText"));
        // menuItemViewProg
        this.menuItemViewProg.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewProgramCon, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewProg.setText(resHelper.getString("menuItemViewProg.text"));		
        this.menuItemViewProg.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showlist"));		
        this.menuItemViewProg.setEnabled(false);		
        this.menuItemViewProg.setVisible(false);
        // menuItemSplit
        this.menuItemSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplit.setText(resHelper.getString("menuItemSplit.text"));		
        this.menuItemSplit.setToolTipText(resHelper.getString("menuItemSplit.toolTipText"));		
        this.menuItemSplit.setMnemonic(76);
        // menuItemDelSplit
        this.menuItemDelSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDelSplit.setText(resHelper.getString("menuItemDelSplit.text"));		
        this.menuItemDelSplit.setMnemonic(69);
        // menuItemContractPayPlan
        this.menuItemContractPayPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionContractPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemContractPayPlan.setText(resHelper.getString("menuItemContractPayPlan.text"));		
        this.menuItemContractPayPlan.setMnemonic(80);		
        this.menuItemContractPayPlan.setEnabled(false);		
        this.menuItemContractPayPlan.setVisible(false);
        this.menuItemContractPayPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    menuItemContractPayPlan_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // enuItemViewCost
        this.enuItemViewCost.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewCost, new Class[] { IItemAction.class }, getServiceContext()));		
        this.enuItemViewCost.setText(resHelper.getString("enuItemViewCost.text"));		
        this.enuItemViewCost.setToolTipText(resHelper.getString("enuItemViewCost.toolTipText"));		
        this.enuItemViewCost.setMnemonic(79);
        // menuItemProgram
        this.menuItemProgram.setAction((IItemAction)ActionProxyFactory.getProxy(actionProgram, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemProgram.setText(resHelper.getString("menuItemProgram.text"));		
        this.menuItemProgram.setToolTipText(resHelper.getString("menuItemProgram.toolTipText"));		
        this.menuItemProgram.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_associatecreate"));		
        this.menuItemProgram.setVisible(false);		
        this.menuItemProgram.setEnabled(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtOrg,txtProj,prmtcontractType,txtNumber,txtcontractName,prmtlandDeveloper,prmtpartB,prmtpartC,contractPropert,pksignDate,comboCurrency,txtExRate,prmtRespDept,txtamount,txtLocalAmount,prmtRespPerson,txtGrtRate,txtGrtAmount,pkbookedDate,contractSource,txtpayPercForWarn,cbPeriod,costProperty,txtchgPercForWarn,txtCreator,chkCostSplit,chkIsPartAMaterialCon,kDDateCreateTime,txtPayScale,txtStampTaxAmt,txtStampTaxRate,comboAttachmentNameList,btnViewAttachment,btnViewContrnt,txtlowestPrice,txtlowerPrice,txtmiddlePrice,txthigherPrice,txthighestPrice,txtbasePrice,txtsecondPrice,prmtinviteType,txtwinPrice,prmtwinUnit,txtfileNo,txtquantity,lblPrice,lblUnit,prmtlowestPriceUnit,prmtlowerPriceUnit,prmtmiddlePriceUnit,prmthigherPriceUnit,prmthighestPriceUni,txtRemark,comboCoopLevel,comboPriceType,tblDetail,tblCost,prmtCharge,tblEconItem,txtBailOriAmount,txtBailRate,tblBail}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(0, 0, 1013, 730));
this.setLayout(new BorderLayout(0, 0));
        this.add(tabPanel, BorderLayout.CENTER);
        //tabPanel
        tabPanel.add(mainPanel, resHelper.getString("mainPanel.constraints"));
        tabPanel.add(ecoItemPanel, resHelper.getString("ecoItemPanel.constraints"));
        //mainPanel
        mainPanel.setLayout(new KDLayout());
        mainPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1012, 697));        kDPanel1.setBounds(new Rectangle(3, 122, 1007, 260));
        mainPanel.add(kDPanel1, new KDLayout.Constraints(3, 122, 1007, 260, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(532, 29, 470, 19));
        mainPanel.add(contNumber, new KDLayout.Constraints(532, 29, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contlandDeveloper.setBounds(new Rectangle(532, 52, 470, 19));
        mainPanel.add(contlandDeveloper, new KDLayout.Constraints(532, 52, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contcontractType.setBounds(new Rectangle(12, 6, 470, 19));
        mainPanel.add(contcontractType, new KDLayout.Constraints(12, 6, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpartB.setBounds(new Rectangle(12, 52, 470, 19));
        mainPanel.add(contpartB, new KDLayout.Constraints(12, 52, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpartC.setBounds(new Rectangle(532, 75, 470, 19));
        mainPanel.add(contpartC, new KDLayout.Constraints(532, 75, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contcontractName.setBounds(new Rectangle(12, 29, 470, 19));
        mainPanel.add(contcontractName, new KDLayout.Constraints(12, 29, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrg.setBounds(new Rectangle(12, 75, 470, 19));
        mainPanel.add(contOrg, new KDLayout.Constraints(12, 75, 470, 19, 0));
        contProj.setBounds(new Rectangle(532, 6, 470, 19));
        mainPanel.add(contProj, new KDLayout.Constraints(532, 6, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane1.setBounds(new Rectangle(3, 382, 997, 311));
        mainPanel.add(kDTabbedPane1, new KDLayout.Constraints(3, 382, 997, 311, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        conContrarctRule.setBounds(new Rectangle(12, 98, 470, 19));
        mainPanel.add(conContrarctRule, new KDLayout.Constraints(12, 98, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conControlAmount.setBounds(new Rectangle(532, 98, 342, 19));
        mainPanel.add(conControlAmount, new KDLayout.Constraints(532, 98, 342, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnBuildPriceIndex.setBounds(new Rectangle(882, 96, 120, 21));
        mainPanel.add(btnBuildPriceIndex, new KDLayout.Constraints(882, 96, 120, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(3, 122, 1007, 260));        contcontractPropert.setBounds(new Rectangle(9, 3, 270, 19));
        kDPanel1.add(contcontractPropert, new KDLayout.Constraints(9, 3, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsignDate.setBounds(new Rectangle(9, 26, 270, 19));
        kDPanel1.add(contsignDate, new KDLayout.Constraints(9, 26, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRespDept.setBounds(new Rectangle(9, 49, 270, 19));
        kDPanel1.add(contRespDept, new KDLayout.Constraints(9, 49, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRespPerson.setBounds(new Rectangle(9, 72, 270, 19));
        kDPanel1.add(contRespPerson, new KDLayout.Constraints(9, 72, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(8, 229, 270, 19));
        kDPanel1.add(kDLabelContainer1, new KDLayout.Constraints(8, 229, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsPartAMaterialCon.setBounds(new Rectangle(9, 234, 270, 19));
        kDPanel1.add(contIsPartAMaterialCon, new KDLayout.Constraints(9, 234, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(9, 96, 270, 19));
        kDPanel1.add(contCreator, new KDLayout.Constraints(9, 96, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(9, 119, 270, 19));
        kDPanel1.add(contCreateTime, new KDLayout.Constraints(9, 119, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contModel.setBounds(new Rectangle(9, 165, 270, 19));
        kDPanel1.add(contModel, new KDLayout.Constraints(9, 165, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAttachmentNameList.setBounds(new Rectangle(9, 188, 270, 19));
        kDPanel1.add(contAttachmentNameList, new KDLayout.Constraints(9, 188, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewContrnt.setBounds(new Rectangle(283, 163, 80, 21));
        kDPanel1.add(btnViewContrnt, new KDLayout.Constraints(283, 163, 80, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewAttachment.setBounds(new Rectangle(283, 186, 80, 21));
        kDPanel1.add(btnViewAttachment, new KDLayout.Constraints(283, 186, 80, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStampTaxAmt.setBounds(new Rectangle(367, 188, 270, 19));
        kDPanel1.add(contStampTaxAmt, new KDLayout.Constraints(367, 188, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStampTaxRate.setBounds(new Rectangle(9, 142, 270, 19));
        kDPanel1.add(contStampTaxRate, new KDLayout.Constraints(9, 142, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkCostSplit.setBounds(new Rectangle(368, 165, 118, 19));
        kDPanel1.add(chkCostSplit, new KDLayout.Constraints(368, 165, 118, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkIsPartAMaterialCon.setBounds(new Rectangle(510, 165, 140, 19));
        kDPanel1.add(chkIsPartAMaterialCon, new KDLayout.Constraints(510, 165, 140, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contGrtRate.setBounds(new Rectangle(366, 118, 270, 19));
        kDPanel1.add(contGrtRate, new KDLayout.Constraints(366, 118, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contamount.setBounds(new Rectangle(367, 72, 270, 19));
        kDPanel1.add(contamount, new KDLayout.Constraints(367, 72, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrency.setBounds(new Rectangle(367, 26, 270, 19));
        kDPanel1.add(contCurrency, new KDLayout.Constraints(367, 26, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcostProperty.setBounds(new Rectangle(366, 142, 270, 19));
        kDPanel1.add(contcostProperty, new KDLayout.Constraints(366, 142, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractSource.setBounds(new Rectangle(368, 210, 270, 19));
        kDPanel1.add(contcontractSource, new KDLayout.Constraints(368, 210, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contExRate.setBounds(new Rectangle(725, 3, 270, 19));
        kDPanel1.add(contExRate, new KDLayout.Constraints(725, 3, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLocalAmount.setBounds(new Rectangle(725, 49, 270, 19));
        kDPanel1.add(contLocalAmount, new KDLayout.Constraints(725, 49, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAmtBig.setBounds(new Rectangle(725, 72, 270, 19));
        kDPanel1.add(contAmtBig, new KDLayout.Constraints(725, 72, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contGrtAmount.setBounds(new Rectangle(725, 95, 270, 19));
        kDPanel1.add(contGrtAmount, new KDLayout.Constraints(725, 95, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contchgPercForWarn.setBounds(new Rectangle(725, 141, 270, 19));
        kDPanel1.add(contchgPercForWarn, new KDLayout.Constraints(725, 141, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPayScale.setBounds(new Rectangle(725, 210, 270, 19));
        kDPanel1.add(contPayScale, new KDLayout.Constraints(725, 210, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        lblOverRateContainer.setBounds(new Rectangle(725, 164, 270, 19));
        kDPanel1.add(lblOverRateContainer, new KDLayout.Constraints(725, 164, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        prmtFwContractTemp.setBounds(new Rectangle(666, 28, 30, 19));
        kDPanel1.add(prmtFwContractTemp, new KDLayout.Constraints(666, 28, 30, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE));
        contOrgAmtBig.setBounds(new Rectangle(884, 34, 270, 19));
        kDPanel1.add(contOrgAmtBig, new KDLayout.Constraints(884, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contConSettleAmount.setBounds(new Rectangle(655, 5, 50, 19));
        kDPanel1.add(contConSettleAmount, new KDLayout.Constraints(655, 5, 50, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(367, 49, 270, 19));
        kDPanel1.add(kDLabelContainer2, new KDLayout.Constraints(367, 49, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(919, 43, 270, 19));
        kDPanel1.add(kDLabelContainer3, new KDLayout.Constraints(919, 43, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contpayPercForWarn.setBounds(new Rectangle(725, 187, 270, 19));
        kDPanel1.add(contpayPercForWarn, new KDLayout.Constraints(725, 187, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInviteProject.setBounds(new Rectangle(725, 118, 270, 19));
        kDPanel1.add(contInviteProject, new KDLayout.Constraints(725, 118, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStrategyPact.setBounds(new Rectangle(725, 118, 270, 19));
        kDPanel1.add(contStrategyPact, new KDLayout.Constraints(725, 118, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnViewInvite.setBounds(new Rectangle(957, 116, 35, 21));
        kDPanel1.add(btnViewInvite, new KDLayout.Constraints(957, 116, 35, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        tenderDiscusstion.setBounds(new Rectangle(725, 118, 270, 19));
        kDPanel1.add(tenderDiscusstion, new KDLayout.Constraints(725, 118, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conEntrustReason.setBounds(new Rectangle(725, 118, 270, 19));
        kDPanel1.add(conEntrustReason, new KDLayout.Constraints(725, 118, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conModel.setBounds(new Rectangle(725, 233, 270, 19));
        kDPanel1.add(conModel, new KDLayout.Constraints(725, 233, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conChargeType.setBounds(new Rectangle(367, 233, 270, 19));
        kDPanel1.add(conChargeType, new KDLayout.Constraints(367, 233, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkisContractor.setBounds(new Rectangle(368, 95, 107, 19));
        kDPanel1.add(chkisContractor, new KDLayout.Constraints(368, 95, 107, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractPrice.setBounds(new Rectangle(725, 26, 270, 19));
        kDPanel1.add(contcontractPrice, new KDLayout.Constraints(725, 26, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkFiveClass.setBounds(new Rectangle(510, 95, 110, 19));
        kDPanel1.add(chkFiveClass, new KDLayout.Constraints(510, 95, 110, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(367, 3, 270, 19));
        kDPanel1.add(kDLabelContainer4, new KDLayout.Constraints(367, 3, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(559, 186, 172, 19));
        kDPanel1.add(kDLabelContainer5, new KDLayout.Constraints(559, 186, 172, 19, 0));
        kDLabelContainer6.setBounds(new Rectangle(576, 96, 185, 19));
        kDPanel1.add(kDLabelContainer6, new KDLayout.Constraints(576, 96, 185, 19, 0));
        //contcontractPropert
        contcontractPropert.setBoundEditor(contractPropert);
        //contsignDate
        contsignDate.setBoundEditor(pksignDate);
        //contRespDept
        contRespDept.setBoundEditor(prmtRespDept);
        //contRespPerson
        contRespPerson.setBoundEditor(prmtRespPerson);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(pkbookedDate);
        //contIsPartAMaterialCon
        contIsPartAMaterialCon.setBoundEditor(cbPeriod);
        //contCreator
        contCreator.setBoundEditor(txtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contModel
        contModel.setBoundEditor(comboModel);
        //contAttachmentNameList
        contAttachmentNameList.setBoundEditor(comboAttachmentNameList);
        //contStampTaxAmt
        contStampTaxAmt.setBoundEditor(txtStampTaxAmt);
        //contStampTaxRate
        contStampTaxRate.setBoundEditor(txtStampTaxRate);
        //contGrtRate
        contGrtRate.setBoundEditor(txtGrtRate);
        //contamount
        contamount.setBoundEditor(txtamount);
        //contCurrency
        contCurrency.setBoundEditor(comboCurrency);
        //contcostProperty
        contcostProperty.setBoundEditor(costProperty);
        //contcontractSource
        contcontractSource.setBoundEditor(contractSource);
        //contExRate
        contExRate.setBoundEditor(txtExRate);
        //contLocalAmount
        contLocalAmount.setBoundEditor(txtLocalAmount);
        //contAmtBig
        contAmtBig.setBoundEditor(txtAmtBig);
        //contGrtAmount
        contGrtAmount.setBoundEditor(txtGrtAmount);
        //contchgPercForWarn
        contchgPercForWarn.setBoundEditor(txtchgPercForWarn);
        //contPayScale
        contPayScale.setBoundEditor(txtPayScale);
        //lblOverRateContainer
        lblOverRateContainer.setBoundEditor(txtOverAmt);
        //contOrgAmtBig
        contOrgAmtBig.setBoundEditor(txtOrgAmtBig);
        //contConSettleAmount
        contConSettleAmount.setBoundEditor(txtConSettleAmout);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(ceremonyb);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(ceremonybb);
        //contpayPercForWarn
        contpayPercForWarn.setBoundEditor(txtpayPercForWarn);
        //contInviteProject
        contInviteProject.setBoundEditor(prmtInviteProject);
        //contStrategyPact
        contStrategyPact.setBoundEditor(prmtStrategyPact);
        //tenderDiscusstion
        tenderDiscusstion.setBoundEditor(prmtTenderDiscusstion);
        //conEntrustReason
        conEntrustReason.setBoundEditor(txtEntrustReason);
        //conModel
        conModel.setBoundEditor(prmtModel);
        //conChargeType
        conChargeType.setBoundEditor(prmtCharge);
        //contcontractPrice
        contcontractPrice.setBoundEditor(txtcontractPrice);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtMIndexType);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtUnSplitAmount);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtSplitedAmount);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contlandDeveloper
        contlandDeveloper.setBoundEditor(prmtlandDeveloper);
        //contcontractType
        contcontractType.setBoundEditor(prmtcontractType);
        //contpartB
        contpartB.setBoundEditor(prmtpartB);
        //contpartC
        contpartC.setBoundEditor(prmtpartC);
        //contcontractName
        contcontractName.setBoundEditor(txtcontractName);
        //contOrg
        contOrg.setBoundEditor(txtOrg);
        //contProj
        contProj.setBoundEditor(txtProj);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane1.add(pnlInviteInfo, resHelper.getString("pnlInviteInfo.constraints"));
        kDTabbedPane1.add(pnlDetail, resHelper.getString("pnlDetail.constraints"));
        kDTabbedPane1.add(pnlCost, resHelper.getString("pnlCost.constraints"));
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kDPanel2.add(kDContainer2, BorderLayout.CENTER);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(kdtSplitEntry, BorderLayout.CENTER);
        //pnlInviteInfo
        pnlInviteInfo.setLayout(null);        contRemark.setBounds(new Rectangle(8, 88, 270, 19));
        pnlInviteInfo.add(contRemark, null);
        contCoopLevel.setBounds(new Rectangle(31, 80, 270, 19));
        pnlInviteInfo.add(contCoopLevel, null);
        contPriceType.setBounds(new Rectangle(26, 162, 270, 19));
        pnlInviteInfo.add(contPriceType, null);
        chkIsSubMainContract.setBounds(new Rectangle(30, 25, 118, 19));
        pnlInviteInfo.add(chkIsSubMainContract, null);
        conMainContract.setBounds(new Rectangle(31, 51, 270, 19));
        pnlInviteInfo.add(conMainContract, null);
        conEffectiveStartDate.setBounds(new Rectangle(327, 25, 270, 19));
        pnlInviteInfo.add(conEffectiveStartDate, null);
        conEffectiveEndDate.setBounds(new Rectangle(701, 24, 270, 19));
        pnlInviteInfo.add(conEffectiveEndDate, null);
        kDScrollPane1.setBounds(new Rectangle(330, 75, 642, 58));
        pnlInviteInfo.add(kDScrollPane1, null);
        conInformation.setBounds(new Rectangle(326, 51, 270, 19));
        pnlInviteInfo.add(conInformation, null);
        contlowestPrice.setBounds(new Rectangle(8, 33, 270, 19));
        pnlInviteInfo.add(contlowestPrice, null);
        contlowerPrice.setBounds(new Rectangle(8, 60, 270, 19));
        pnlInviteInfo.add(contlowerPrice, null);
        conthigherPrice.setBounds(new Rectangle(8, 114, 270, 19));
        pnlInviteInfo.add(conthigherPrice, null);
        contmiddlePrice.setBounds(new Rectangle(8, 87, 270, 19));
        pnlInviteInfo.add(contmiddlePrice, null);
        conthighestPrice.setBounds(new Rectangle(8, 141, 270, 19));
        pnlInviteInfo.add(conthighestPrice, null);
        contbasePrice.setBounds(new Rectangle(8, 33, 270, 19));
        pnlInviteInfo.add(contbasePrice, null);
        contsecondPrice.setBounds(new Rectangle(8, 60, 270, 19));
        pnlInviteInfo.add(contsecondPrice, null);
        continviteType.setBounds(new Rectangle(636, 114, 346, 19));
        pnlInviteInfo.add(continviteType, null);
        contwinPrice.setBounds(new Rectangle(636, 33, 346, 19));
        pnlInviteInfo.add(contwinPrice, null);
        contwinUnit.setBounds(new Rectangle(636, 60, 346, 19));
        pnlInviteInfo.add(contwinUnit, null);
        contfileNo.setBounds(new Rectangle(636, 141, 346, 19));
        pnlInviteInfo.add(contfileNo, null);
        contquantity.setBounds(new Rectangle(636, 87, 346, 19));
        pnlInviteInfo.add(contquantity, null);
        prmtlowestPriceUnit.setBounds(new Rectangle(298, 33, 292, 19));
        pnlInviteInfo.add(prmtlowestPriceUnit, null);
        prmtlowerPriceUnit.setBounds(new Rectangle(298, 60, 292, 19));
        pnlInviteInfo.add(prmtlowerPriceUnit, null);
        prmtmiddlePriceUnit.setBounds(new Rectangle(298, 87, 292, 19));
        pnlInviteInfo.add(prmtmiddlePriceUnit, null);
        prmthigherPriceUnit.setBounds(new Rectangle(298, 114, 292, 19));
        pnlInviteInfo.add(prmthigherPriceUnit, null);
        prmthighestPriceUni.setBounds(new Rectangle(298, 141, 292, 19));
        pnlInviteInfo.add(prmthighestPriceUni, null);
        lblPrice.setBounds(new Rectangle(169, 8, 58, 19));
        pnlInviteInfo.add(lblPrice, null);
        lblUnit.setBounds(new Rectangle(431, 8, 65, 19));
        pnlInviteInfo.add(lblUnit, null);
        btnInviteExecudeInfo.setBounds(new Rectangle(868, 1, 112, 19));
        pnlInviteInfo.add(btnInviteExecudeInfo, null);
        //contRemark
        contRemark.setBoundEditor(txtRemark);
        //contCoopLevel
        contCoopLevel.setBoundEditor(comboCoopLevel);
        //contPriceType
        contPriceType.setBoundEditor(comboPriceType);
        //conMainContract
        conMainContract.setBoundEditor(prmtMainContract);
        //conEffectiveStartDate
        conEffectiveStartDate.setBoundEditor(kdpEffectStartDate);
        //conEffectiveEndDate
        conEffectiveEndDate.setBoundEditor(kdpEffectiveEndDate);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtInformation, null);
        //contlowestPrice
        contlowestPrice.setBoundEditor(txtlowestPrice);
        //contlowerPrice
        contlowerPrice.setBoundEditor(txtlowerPrice);
        //conthigherPrice
        conthigherPrice.setBoundEditor(txthigherPrice);
        //contmiddlePrice
        contmiddlePrice.setBoundEditor(txtmiddlePrice);
        //conthighestPrice
        conthighestPrice.setBoundEditor(txthighestPrice);
        //contbasePrice
        contbasePrice.setBoundEditor(txtbasePrice);
        //contsecondPrice
        contsecondPrice.setBoundEditor(txtsecondPrice);
        //continviteType
        continviteType.setBoundEditor(prmtinviteType);
        //contwinPrice
        contwinPrice.setBoundEditor(txtwinPrice);
        //contwinUnit
        contwinUnit.setBoundEditor(prmtwinUnit);
        //contfileNo
        contfileNo.setBoundEditor(txtfileNo);
        //contquantity
        contquantity.setBoundEditor(txtquantity);
        //pnlDetail
pnlDetail.setLayout(new BorderLayout(0, 0));        pnlDetail.add(tblDetail, BorderLayout.CENTER);
        //pnlCost
        pnlCost.setLayout(new KDLayout());
        pnlCost.putClientProperty("OriginalBounds", new Rectangle(0, 0, 996, 278));        tblCost.setBounds(new Rectangle(10, 10, 965, 194));
        pnlCost.add(tblCost, new KDLayout.Constraints(10, 10, 965, 194, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        //conContrarctRule
        conContrarctRule.setBoundEditor(prmtFwContract);
        //conControlAmount
        conControlAmount.setBoundEditor(txtControlAmount);
        //ecoItemPanel
ecoItemPanel.setLayout(new BorderLayout(0, 0));        ecoItemPanel.add(kDContainer1, BorderLayout.CENTER);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDSplitPane1, BorderLayout.CENTER);
        //kDSplitPane1
        kDSplitPane1.add(contPayItem, "top");
        kDSplitPane1.add(contBailItem, "bottom");
        //contPayItem
contPayItem.getContentPane().setLayout(new BorderLayout(0, 0));        contPayItem.getContentPane().add(tblEconItem, BorderLayout.CENTER);
        //contBailItem
        contBailItem.getContentPane().setLayout(new KDLayout());
        contBailItem.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 1011, 342));        contBailOriAmount.setBounds(new Rectangle(5, 8, 463, 19));
        contBailItem.getContentPane().add(contBailOriAmount, new KDLayout.Constraints(5, 8, 463, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBailRate.setBounds(new Rectangle(544, 8, 450, 19));
        contBailItem.getContentPane().add(contBailRate, new KDLayout.Constraints(544, 8, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        tblBail.setBounds(new Rectangle(3, 40, 995, 270));
        contBailItem.getContentPane().add(tblBail, new KDLayout.Constraints(3, 40, 995, 270, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contBailOriAmount
        contBailOriAmount.setBoundEditor(txtBailOriAmount);
        //contBailRate
        contBailRate.setBoundEditor(txtBailRate);

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
        menuFile.add(kDSeparator6);
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
        menuView.add(kDSeparator7);
        menuView.add(menuItemViewContent);
        menuView.add(menuItemLocate);
        menuView.add(menuItemViewBgBalance);
        menuView.add(menuItemViewProg);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemSplit);
        menuBiz.add(menuItemDelSplit);
        menuBiz.add(menuItemContractPayPlan);
        menuBiz.add(enuItemViewCost);
        menuBiz.add(menuItemProgram);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnProgram);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnSplit);
        this.toolBar.add(btnDelSplit);
        this.toolBar.add(btnViewContent);
        this.toolBar.add(btnContractPlan);
        this.toolBar.add(btnViewCost);
        this.toolBar.add(btnViewBgBalance);
        this.toolBar.add(btnViewProgramContract);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isCoseSplit", boolean.class, this.chkCostSplit, "selected");
		dataBinder.registerBinding("isPartAMaterialCon", boolean.class, this.chkIsPartAMaterialCon, "selected");
		dataBinder.registerBinding("isContractor", boolean.class, this.chkisContractor, "selected");
		dataBinder.registerBinding("isFiveClass", boolean.class, this.chkFiveClass, "selected");
		dataBinder.registerBinding("contractPropert", com.kingdee.eas.fdc.contract.ContractPropertyEnum.class, this.contractPropert, "selectedItem");
		dataBinder.registerBinding("signDate", java.util.Date.class, this.pksignDate, "value");
		dataBinder.registerBinding("respDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtRespDept, "data");
		dataBinder.registerBinding("respPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtRespPerson, "data");
		dataBinder.registerBinding("bookedDate", java.util.Date.class, this.pkbookedDate, "value");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.cbPeriod, "data");
		dataBinder.registerBinding("creator.name", String.class, this.txtCreator, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("stampTaxAmt", java.math.BigDecimal.class, this.txtStampTaxAmt, "value");
		dataBinder.registerBinding("stampTaxRate", java.math.BigDecimal.class, this.txtStampTaxRate, "value");
		dataBinder.registerBinding("grtRate", java.math.BigDecimal.class, this.txtGrtRate, "value");
		dataBinder.registerBinding("originalAmount", java.math.BigDecimal.class, this.txtamount, "value");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.comboCurrency, "selectedItem");
		dataBinder.registerBinding("costProperty", com.kingdee.eas.fdc.contract.CostPropertyEnum.class, this.costProperty, "selectedItem");
		dataBinder.registerBinding("contractSourceId", com.kingdee.eas.fdc.basedata.ContractSourceInfo.class, this.contractSource, "data");
		dataBinder.registerBinding("exRate", java.math.BigDecimal.class, this.txtExRate, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtLocalAmount, "value");
		dataBinder.registerBinding("grtAmount", java.math.BigDecimal.class, this.txtGrtAmount, "value");
		dataBinder.registerBinding("chgPercForWarn", java.math.BigDecimal.class, this.txtchgPercForWarn, "value");
		dataBinder.registerBinding("payScale", java.math.BigDecimal.class, this.txtPayScale, "value");
		dataBinder.registerBinding("overRate", double.class, this.txtOverAmt, "value");
		dataBinder.registerBinding("ceremonyb", java.math.BigDecimal.class, this.ceremonyb, "value");
		dataBinder.registerBinding("ceremonybb", java.math.BigDecimal.class, this.ceremonybb, "value");
		dataBinder.registerBinding("payPercForWarn", java.math.BigDecimal.class, this.txtpayPercForWarn, "value");
		dataBinder.registerBinding("inviteProject", com.kingdee.eas.fdc.invite.InviteProjectInfo.class, this.prmtInviteProject, "data");
		dataBinder.registerBinding("strategyPact", com.kingdee.eas.fdc.invite.news.StrategyPactInfo.class, this.prmtStrategyPact, "data");
		dataBinder.registerBinding("entrustReason", String.class, this.txtEntrustReason, "text");
		dataBinder.registerBinding("model", com.kingdee.eas.base.attachment.AttachmentInfo.class, this.prmtModel, "data");
		dataBinder.registerBinding("conChargeType", com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo.class, this.prmtCharge, "data");
		dataBinder.registerBinding("contractPrice", java.math.BigDecimal.class, this.txtcontractPrice, "value");
		dataBinder.registerBinding("mIndexType", String.class, this.txtMIndexType, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("landDeveloper", com.kingdee.eas.fdc.basedata.LandDeveloperInfo.class, this.prmtlandDeveloper, "data");
		dataBinder.registerBinding("contractType", com.kingdee.eas.fdc.basedata.ContractTypeInfo.class, this.prmtcontractType, "data");
		dataBinder.registerBinding("partB", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtpartB, "data");
		dataBinder.registerBinding("partC", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtpartC, "data");
		dataBinder.registerBinding("name", String.class, this.txtcontractName, "text");
		dataBinder.registerBinding("splitEntry.id", com.kingdee.bos.util.BOSUuid.class, this.kdtSplitEntry, "id.text");
		dataBinder.registerBinding("splitEntry", com.kingdee.eas.fdc.contract.ContractBillSplitEntryInfo.class, this.kdtSplitEntry, "userObject");
		dataBinder.registerBinding("splitEntry.amount", java.math.BigDecimal.class, this.kdtSplitEntry, "amount.text");
		dataBinder.registerBinding("splitEntry.product", com.kingdee.eas.fdc.basedata.ProductTypeInfo.class, this.kdtSplitEntry, "product.text");
		dataBinder.registerBinding("splitEntry.costAccount.curProject.id", com.kingdee.bos.util.BOSUuid.class, this.kdtSplitEntry, "costAccount.curProject.id.text");
		dataBinder.registerBinding("splitEntry.costAccount.id", com.kingdee.bos.util.BOSUuid.class, this.kdtSplitEntry, "costAccount.id.text");
		dataBinder.registerBinding("splitEntry.level", int.class, this.kdtSplitEntry, "level.text");
		dataBinder.registerBinding("splitEntry.apportionType.name", String.class, this.kdtSplitEntry, "apportionType.name.text");
		dataBinder.registerBinding("splitEntry.apportionValue", java.math.BigDecimal.class, this.kdtSplitEntry, "apportionValue.text");
		dataBinder.registerBinding("splitEntry.directAmount", java.math.BigDecimal.class, this.kdtSplitEntry, "directAmount.text");
		dataBinder.registerBinding("splitEntry.apportionValueTotal", java.math.BigDecimal.class, this.kdtSplitEntry, "apportionValueTotal.text");
		dataBinder.registerBinding("splitEntry.directAmountTotal", java.math.BigDecimal.class, this.kdtSplitEntry, "directAmountTotal.text");
		dataBinder.registerBinding("splitEntry.otherRatioTotal", java.math.BigDecimal.class, this.kdtSplitEntry, "otherRatioTotal.text");
		dataBinder.registerBinding("splitEntry.splitType", com.kingdee.eas.fdc.basedata.CostSplitTypeEnum.class, this.kdtSplitEntry, "splitType.text");
		dataBinder.registerBinding("splitEntry.workLoad", java.math.BigDecimal.class, this.kdtSplitEntry, "workLoad.text");
		dataBinder.registerBinding("splitEntry.price", java.math.BigDecimal.class, this.kdtSplitEntry, "price.text");
		dataBinder.registerBinding("splitEntry.splitScale", java.math.BigDecimal.class, this.kdtSplitEntry, "splitScale.text");
		dataBinder.registerBinding("splitEntry.programmings", com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo.class, this.kdtSplitEntry, "programming.text");
		dataBinder.registerBinding("isSubContract", boolean.class, this.chkIsSubMainContract, "selected");
		dataBinder.registerBinding("lowestPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtlowestPriceUnit, "data");
		dataBinder.registerBinding("lowerPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtlowerPriceUnit, "data");
		dataBinder.registerBinding("middlePriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtmiddlePriceUnit, "data");
		dataBinder.registerBinding("higherPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmthigherPriceUnit, "data");
		dataBinder.registerBinding("highestPriceUni", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmthighestPriceUni, "data");
		dataBinder.registerBinding("remark", String.class, this.txtRemark, "text");
		dataBinder.registerBinding("coopLevel", com.kingdee.eas.fdc.contract.CoopLevelEnum.class, this.comboCoopLevel, "selectedItem");
		dataBinder.registerBinding("priceType", com.kingdee.eas.fdc.contract.PriceTypeEnum.class, this.comboPriceType, "selectedItem");
		dataBinder.registerBinding("mainContract", com.kingdee.eas.fdc.contract.ContractBillCollection.class, this.prmtMainContract, "data");
		dataBinder.registerBinding("effectiveStartDate", java.util.Date.class, this.kdpEffectStartDate, "value");
		dataBinder.registerBinding("effectiveEndDate", java.util.Date.class, this.kdpEffectiveEndDate, "value");
		dataBinder.registerBinding("information", String.class, this.txtInformation, "text");
		dataBinder.registerBinding("lowestPrice", java.math.BigDecimal.class, this.txtlowestPrice, "value");
		dataBinder.registerBinding("lowerPrice", java.math.BigDecimal.class, this.txtlowerPrice, "value");
		dataBinder.registerBinding("higherPrice", java.math.BigDecimal.class, this.txthigherPrice, "value");
		dataBinder.registerBinding("middlePrice", java.math.BigDecimal.class, this.txtmiddlePrice, "value");
		dataBinder.registerBinding("highestPrice", java.math.BigDecimal.class, this.txthighestPrice, "value");
		dataBinder.registerBinding("basePrice", java.math.BigDecimal.class, this.txtbasePrice, "value");
		dataBinder.registerBinding("secondPrice", java.math.BigDecimal.class, this.txtsecondPrice, "value");
		dataBinder.registerBinding("inviteType", com.kingdee.eas.fdc.basedata.InviteTypeInfo.class, this.prmtinviteType, "data");
		dataBinder.registerBinding("winPrice", java.math.BigDecimal.class, this.txtwinPrice, "value");
		dataBinder.registerBinding("winUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtwinUnit, "data");
		dataBinder.registerBinding("fileNo", String.class, this.txtfileNo, "text");
		dataBinder.registerBinding("quantity", java.math.BigDecimal.class, this.txtquantity, "value");
		dataBinder.registerBinding("programmingContract", com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo.class, this.prmtFwContract, "data");
		dataBinder.registerBinding("programmingContract.controlBalance", java.math.BigDecimal.class, this.txtControlAmount, "value");
		dataBinder.registerBinding("payItems.payItemDate", java.util.Date.class, this.tblEconItem, "date.text");
		dataBinder.registerBinding("payItems", com.kingdee.eas.fdc.contract.ContractPayItemInfo.class, this.tblEconItem, "userObject");
		dataBinder.registerBinding("payItems.payCondition", String.class, this.tblEconItem, "payCondition.text");
		dataBinder.registerBinding("payItems.prop", java.math.BigDecimal.class, this.tblEconItem, "payRate.text");
		dataBinder.registerBinding("payItems.amount", java.math.BigDecimal.class, this.tblEconItem, "payAmount.text");
		dataBinder.registerBinding("payItems.desc", String.class, this.tblEconItem, "desc.text");
		dataBinder.registerBinding("payItems.paymentType", com.kingdee.eas.fdc.basedata.PaymentTypeInfo.class, this.tblEconItem, "payType.text");
		dataBinder.registerBinding("bail.entry.bailDate", java.util.Date.class, this.tblBail, "bailDate.text");
		dataBinder.registerBinding("bail.entry.bailConditon", String.class, this.tblBail, "bailCondition.text");
		dataBinder.registerBinding("bail.entry.prop", java.math.BigDecimal.class, this.tblBail, "bailRate.text");
		dataBinder.registerBinding("bail.entry.amount", java.math.BigDecimal.class, this.tblBail, "bailAmount.text");
		dataBinder.registerBinding("bail.entry.desc", String.class, this.tblBail, "desc.text");
		dataBinder.registerBinding("bail.entry", com.kingdee.eas.fdc.contract.ContractBailEntryInfo.class, this.tblBail, "userObject");
		dataBinder.registerBinding("bail.amount", java.math.BigDecimal.class, this.txtBailOriAmount, "value");
		dataBinder.registerBinding("bail.prop", java.math.BigDecimal.class, this.txtBailRate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionUnAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionSplit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionViewContent, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionContractPlan, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAttachment, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionWorkFlowG, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractBillEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.txtOrg.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.contract.ContractBillInfo)ov;
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
	 * ????????��??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("isCoseSplit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isPartAMaterialCon", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isContractor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isFiveClass", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractPropert", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("signDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("respDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("respPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("stampTaxAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("stampTaxRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("grtRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costProperty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractSourceId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("grtAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chgPercForWarn", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payScale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("overRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ceremonyb", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ceremonybb", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payPercForWarn", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("strategyPact", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrustReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("model", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conChargeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mIndexType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("landDeveloper", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("partB", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("partC", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.product", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.costAccount.curProject.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.costAccount.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.apportionType.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.apportionValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.directAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.apportionValueTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.directAmountTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.otherRatioTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.splitType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.workLoad", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.splitScale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("splitEntry.programmings", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isSubContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowestPriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowerPriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("middlePriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("higherPriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("highestPriceUni", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("coopLevel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mainContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("effectiveStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("effectiveEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("information", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowestPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowerPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("higherPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("middlePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("highestPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("basePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("secondPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("winPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("winUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fileNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("quantity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("programmingContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("programmingContract.controlBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.payItemDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.payCondition", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.prop", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.desc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.paymentType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.bailDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.bailConditon", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.prop", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.desc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.prop", ValidateHelper.ON_SAVE);    		
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
		            this.actionAudit.setVisible(false);
		            this.actionAudit.setEnabled(false);
		            this.actionUnAudit.setVisible(false);
		            this.actionUnAudit.setEnabled(false);
		            this.actionSplit.setVisible(false);
		            this.actionSplit.setEnabled(false);
		            this.actionViewContent.setVisible(false);
		            this.actionViewContent.setEnabled(false);
		            this.actionContractPlan.setVisible(false);
		            this.actionContractPlan.setEnabled(false);
		            this.actionAttachment.setVisible(false);
		            this.actionAttachment.setEnabled(false);
		            this.actionWorkFlowG.setVisible(false);
		            this.actionWorkFlowG.setEnabled(false);
        }
    }

    /**
     * output btnBuildPriceIndex_actionPerformed method
     */
    protected void btnBuildPriceIndex_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here  aad
    }

    /**
     * output btnViewContrnt_actionPerformed method
     */
    protected void btnViewContrnt_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnViewAttachment_actionPerformed method
     */
    protected void btnViewAttachment_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkCostSplit_mouseClicked method
     */
    protected void chkCostSplit_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output chkIsPartAMaterialCon_mouseClicked method
     */
    protected void chkIsPartAMaterialCon_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output chkisContractor_actionPerformed method
     */
    protected void chkisContractor_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output contractPropert_itemStateChanged method
     */
    protected void contractPropert_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtRespDept_dataChanged method
     */
    protected void prmtRespDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkbookedDate_dataChanged method
     */
    protected void pkbookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtCreator_actionPerformed method
     */
    protected void txtCreator_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboModel_itemStateChanged method
     */
    protected void comboModel_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output txtStampTaxRate_dataChanged method
     */
    protected void txtStampTaxRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtGrtRate_dataChanged method
     */
    protected void txtGrtRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtamount_dataChanged method
     */
    protected void txtamount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtamount_focusGained method
     */
    protected void txtamount_focusGained(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output comboCurrency_itemStateChanged method
     */
    protected void comboCurrency_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output costProperty_itemStateChanged method
     */
    protected void costProperty_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output contractSource_willShow method
     */
    protected void contractSource_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output contractSource_dataChanged method
     */
    protected void contractSource_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtExRate_dataChanged method
     */
    protected void txtExRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output ceremonyb_dataChanged method
     */
    protected void ceremonyb_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        System.out.println("sadfs");
    }

    /**
     * output ceremonyb_focusGained method
     */
    protected void ceremonyb_focusGained(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output prmtInviteProject_mouseClicked method
     */
    protected void prmtInviteProject_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output prmtStrategyPact_mouseClicked method
     */
    protected void prmtStrategyPact_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output prmtModel_dataChanged method
     */
    protected void prmtModel_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtModel_willShow method
     */
    protected void prmtModel_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output txtUnSplitAmount_actionPerformed method
     */
    protected void txtUnSplitAmount_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtUnSplitAmount_dataChanged method
     */
    protected void txtUnSplitAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output txtSplitedAmount_dataChanged method
     */
    protected void txtSplitedAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output prmtcontractType_dataChanged method
     */
    protected void prmtcontractType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtcontractType_willShow method
     */
    protected void prmtcontractType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output kdtSplitEntry_editStopped method
     */
    protected void kdtSplitEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output prmtMainContract_willShow method
     */
    protected void prmtMainContract_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output tblDetail_editStopping method
     */
    protected void tblDetail_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblDetail_tableClicked method
     */
    protected void tblDetail_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblCost_tableClicked method
     */
    protected void tblCost_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output prmtFwContract_dataChanged method
     */
    protected void prmtFwContract_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblEconItem_editStopped method
     */
    protected void tblEconItem_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblEconItem_tableClicked method
     */
    protected void tblEconItem_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblBail_editStopped method
     */
    protected void tblBail_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblBail_tableClicked method
     */
    protected void tblBail_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output txtBailOriAmount_dataChanged method
     */
    protected void txtBailOriAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtBailRate_dataChanged method
     */
    protected void txtBailRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output menuItemContractPayPlan_actionPerformed method
     */
    protected void menuItemContractPayPlan_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("isCoseSplit"));
        sic.add(new SelectorItemInfo("isPartAMaterialCon"));
        sic.add(new SelectorItemInfo("isContractor"));
        sic.add(new SelectorItemInfo("isFiveClass"));
        sic.add(new SelectorItemInfo("contractPropert"));
        sic.add(new SelectorItemInfo("signDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("respDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("respDept.id"));
        	sic.add(new SelectorItemInfo("respDept.number"));
        	sic.add(new SelectorItemInfo("respDept.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("respPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("respPerson.id"));
        	sic.add(new SelectorItemInfo("respPerson.number"));
        	sic.add(new SelectorItemInfo("respPerson.name"));
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
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("stampTaxAmt"));
        sic.add(new SelectorItemInfo("stampTaxRate"));
        sic.add(new SelectorItemInfo("grtRate"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("currency"));
        sic.add(new SelectorItemInfo("costProperty"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractSourceId.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractSourceId.id"));
        	sic.add(new SelectorItemInfo("contractSourceId.number"));
        	sic.add(new SelectorItemInfo("contractSourceId.name"));
		}
        sic.add(new SelectorItemInfo("exRate"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("grtAmount"));
        sic.add(new SelectorItemInfo("chgPercForWarn"));
        sic.add(new SelectorItemInfo("payScale"));
        sic.add(new SelectorItemInfo("overRate"));
        sic.add(new SelectorItemInfo("ceremonyb"));
        sic.add(new SelectorItemInfo("ceremonybb"));
        sic.add(new SelectorItemInfo("payPercForWarn"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("inviteProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("inviteProject.id"));
        	sic.add(new SelectorItemInfo("inviteProject.number"));
        	sic.add(new SelectorItemInfo("inviteProject.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("strategyPact.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("strategyPact.id"));
        	sic.add(new SelectorItemInfo("strategyPact.number"));
        	sic.add(new SelectorItemInfo("strategyPact.name"));
		}
        sic.add(new SelectorItemInfo("entrustReason"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("model.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("model.id"));
        	sic.add(new SelectorItemInfo("model.number"));
        	sic.add(new SelectorItemInfo("model.name"));
        	sic.add(new SelectorItemInfo("model.attachID"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("conChargeType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("conChargeType.id"));
        	sic.add(new SelectorItemInfo("conChargeType.number"));
        	sic.add(new SelectorItemInfo("conChargeType.name"));
		}
        sic.add(new SelectorItemInfo("contractPrice"));
        sic.add(new SelectorItemInfo("mIndexType"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("landDeveloper.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("landDeveloper.id"));
        	sic.add(new SelectorItemInfo("landDeveloper.number"));
        	sic.add(new SelectorItemInfo("landDeveloper.name"));
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("partB.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("partB.id"));
        	sic.add(new SelectorItemInfo("partB.number"));
        	sic.add(new SelectorItemInfo("partB.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("partC.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("partC.id"));
        	sic.add(new SelectorItemInfo("partC.number"));
        	sic.add(new SelectorItemInfo("partC.name"));
		}
        sic.add(new SelectorItemInfo("name"));
    	sic.add(new SelectorItemInfo("splitEntry.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("splitEntry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("splitEntry.amount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("splitEntry.product.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("splitEntry.product.id"));
			sic.add(new SelectorItemInfo("splitEntry.product.name"));
        	sic.add(new SelectorItemInfo("splitEntry.product.number"));
		}
    	sic.add(new SelectorItemInfo("splitEntry.costAccount.curProject.id"));
    	sic.add(new SelectorItemInfo("splitEntry.costAccount.id"));
    	sic.add(new SelectorItemInfo("splitEntry.level"));
    	sic.add(new SelectorItemInfo("splitEntry.apportionType.name"));
    	sic.add(new SelectorItemInfo("splitEntry.apportionValue"));
    	sic.add(new SelectorItemInfo("splitEntry.directAmount"));
    	sic.add(new SelectorItemInfo("splitEntry.apportionValueTotal"));
    	sic.add(new SelectorItemInfo("splitEntry.directAmountTotal"));
    	sic.add(new SelectorItemInfo("splitEntry.otherRatioTotal"));
    	sic.add(new SelectorItemInfo("splitEntry.splitType"));
    	sic.add(new SelectorItemInfo("splitEntry.workLoad"));
    	sic.add(new SelectorItemInfo("splitEntry.price"));
    	sic.add(new SelectorItemInfo("splitEntry.splitScale"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("splitEntry.programmings.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("splitEntry.programmings.id"));
			sic.add(new SelectorItemInfo("splitEntry.programmings.name"));
        	sic.add(new SelectorItemInfo("splitEntry.programmings.number"));
		}
        sic.add(new SelectorItemInfo("isSubContract"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lowestPriceUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lowestPriceUnit.id"));
        	sic.add(new SelectorItemInfo("lowestPriceUnit.number"));
        	sic.add(new SelectorItemInfo("lowestPriceUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lowerPriceUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lowerPriceUnit.id"));
        	sic.add(new SelectorItemInfo("lowerPriceUnit.number"));
        	sic.add(new SelectorItemInfo("lowerPriceUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("middlePriceUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("middlePriceUnit.id"));
        	sic.add(new SelectorItemInfo("middlePriceUnit.number"));
        	sic.add(new SelectorItemInfo("middlePriceUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("higherPriceUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("higherPriceUnit.id"));
        	sic.add(new SelectorItemInfo("higherPriceUnit.number"));
        	sic.add(new SelectorItemInfo("higherPriceUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("highestPriceUni.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("highestPriceUni.id"));
        	sic.add(new SelectorItemInfo("highestPriceUni.number"));
        	sic.add(new SelectorItemInfo("highestPriceUni.name"));
		}
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("coopLevel"));
        sic.add(new SelectorItemInfo("priceType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("mainContract.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("mainContract.id"));
        	sic.add(new SelectorItemInfo("mainContract.number"));
        	sic.add(new SelectorItemInfo("mainContract.name"));
		}
        sic.add(new SelectorItemInfo("effectiveStartDate"));
        sic.add(new SelectorItemInfo("effectiveEndDate"));
        sic.add(new SelectorItemInfo("information"));
        sic.add(new SelectorItemInfo("lowestPrice"));
        sic.add(new SelectorItemInfo("lowerPrice"));
        sic.add(new SelectorItemInfo("higherPrice"));
        sic.add(new SelectorItemInfo("middlePrice"));
        sic.add(new SelectorItemInfo("highestPrice"));
        sic.add(new SelectorItemInfo("basePrice"));
        sic.add(new SelectorItemInfo("secondPrice"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("inviteType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("inviteType.id"));
        	sic.add(new SelectorItemInfo("inviteType.number"));
        	sic.add(new SelectorItemInfo("inviteType.name"));
		}
        sic.add(new SelectorItemInfo("winPrice"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("winUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("winUnit.id"));
        	sic.add(new SelectorItemInfo("winUnit.number"));
        	sic.add(new SelectorItemInfo("winUnit.name"));
		}
        sic.add(new SelectorItemInfo("fileNo"));
        sic.add(new SelectorItemInfo("quantity"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("programmingContract.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("programmingContract.id"));
        	sic.add(new SelectorItemInfo("programmingContract.number"));
        	sic.add(new SelectorItemInfo("programmingContract.name"));
		}
        sic.add(new SelectorItemInfo("programmingContract.controlBalance"));
    	sic.add(new SelectorItemInfo("payItems.payItemDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payItems.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("payItems.payCondition"));
    	sic.add(new SelectorItemInfo("payItems.prop"));
    	sic.add(new SelectorItemInfo("payItems.amount"));
    	sic.add(new SelectorItemInfo("payItems.desc"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payItems.paymentType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("payItems.paymentType.id"));
			sic.add(new SelectorItemInfo("payItems.paymentType.name"));
        	sic.add(new SelectorItemInfo("payItems.paymentType.number"));
		}
    	sic.add(new SelectorItemInfo("bail.entry.bailDate"));
    	sic.add(new SelectorItemInfo("bail.entry.bailConditon"));
    	sic.add(new SelectorItemInfo("bail.entry.prop"));
    	sic.add(new SelectorItemInfo("bail.entry.amount"));
    	sic.add(new SelectorItemInfo("bail.entry.desc"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("bail.entry.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("bail.entry.id"));
		}
        sic.add(new SelectorItemInfo("bail.amount"));
        sic.add(new SelectorItemInfo("bail.prop"));
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
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
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
     * output actionSplit_actionPerformed method
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContent_actionPerformed method
     */
    public void actionViewContent_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionContractPlan_actionPerformed method
     */
    public void actionContractPlan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelSplit_actionPerformed method
     */
    public void actionDelSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewCost_actionPerformed method
     */
    public void actionViewCost_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewBgBalance_actionPerformed method
     */
    public void actionViewBgBalance_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewAttachmentSelf_actionPerformed method
     */
    public void actionViewAttachmentSelf_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContentSelf_actionPerformed method
     */
    public void actionViewContentSelf_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionProgram_actionPerformed method
     */
    public void actionProgram_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewProgramCon_actionPerformed method
     */
    public void actionViewProgramCon_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddTblBailLine_actionPerformed method
     */
    public void actionAddTblBailLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveTblBailLine_actionPerformed method
     */
    public void actionRemoveTblBailLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewInvite_actionPerformed method
     */
    public void actionViewInvite_actionPerformed(ActionEvent e) throws Exception
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
     * output actionRemoveSplit_actionPerformed method
     */
    public void actionRemoveSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionProgrAcctSelect_actionPerformed method
     */
    public void actionProgrAcctSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionContractPriceSplit_actionPerformed method
     */
    public void actionContractPriceSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionProfessionSplit_actionPerformed method
     */
    public void actionProfessionSplit_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionViewContent(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContent() {
    	return false;
    }
	public RequestContext prepareActionContractPlan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionContractPlan() {
    	return false;
    }
	public RequestContext prepareActionDelSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelSplit() {
    	return false;
    }
	public RequestContext prepareActionViewCost(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewCost() {
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
	public RequestContext prepareActionViewAttachmentSelf(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewAttachmentSelf() {
    	return false;
    }
	public RequestContext prepareActionViewContentSelf(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContentSelf() {
    	return false;
    }
	public RequestContext prepareActionProgram(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProgram() {
    	return false;
    }
	public RequestContext prepareActionViewProgramCon(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewProgramCon() {
    	return false;
    }
	public RequestContext prepareActionAddTblBailLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddTblBailLine() {
    	return false;
    }
	public RequestContext prepareActionRemoveTblBailLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveTblBailLine() {
    	return false;
    }
	public RequestContext prepareActionViewInvite(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewInvite() {
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
	public RequestContext prepareactionRemoveSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionRemoveSplit() {
    	return false;
    }
	public RequestContext prepareActionProgrAcctSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProgrAcctSelect() {
    	return false;
    }
	public RequestContext prepareActionContractPriceSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionContractPriceSplit() {
    	return false;
    }
	public RequestContext prepareActionProfessionSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProfessionSplit() {
    	return false;
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
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionSplit", "actionSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContent class
     */     
    protected class ActionViewContent extends ItemAction {     
    
        public ActionViewContent()
        {
            this(null);
        }

        public ActionViewContent(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewContent.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContent.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContent.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionViewContent", "actionViewContent_actionPerformed", e);
        }
    }

    /**
     * output ActionContractPlan class
     */     
    protected class ActionContractPlan extends ItemAction {     
    
        public ActionContractPlan()
        {
            this(null);
        }

        public ActionContractPlan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift P"));
            _tempStr = resHelper.getString("ActionContractPlan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractPlan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractPlan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionContractPlan", "actionContractPlan_actionPerformed", e);
        }
    }

    /**
     * output ActionDelSplit class
     */     
    protected class ActionDelSplit extends ItemAction {     
    
        public ActionDelSplit()
        {
            this(null);
        }

        public ActionDelSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionDelSplit", "actionDelSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionViewCost class
     */     
    protected class ActionViewCost extends ItemAction {     
    
        public ActionViewCost()
        {
            this(null);
        }

        public ActionViewCost(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewCost.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewCost.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewCost.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionViewCost", "actionViewCost_actionPerformed", e);
        }
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
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionViewBgBalance", "actionViewBgBalance_actionPerformed", e);
        }
    }

    /**
     * output ActionViewAttachmentSelf class
     */     
    protected class ActionViewAttachmentSelf extends ItemAction {     
    
        public ActionViewAttachmentSelf()
        {
            this(null);
        }

        public ActionViewAttachmentSelf(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewAttachmentSelf.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAttachmentSelf.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAttachmentSelf.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionViewAttachmentSelf", "actionViewAttachmentSelf_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContentSelf class
     */     
    protected class ActionViewContentSelf extends ItemAction {     
    
        public ActionViewContentSelf()
        {
            this(null);
        }

        public ActionViewContentSelf(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewContentSelf.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContentSelf.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContentSelf.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionViewContentSelf", "actionViewContentSelf_actionPerformed", e);
        }
    }

    /**
     * output ActionProgram class
     */     
    protected class ActionProgram extends ItemAction {     
    
        public ActionProgram()
        {
            this(null);
        }

        public ActionProgram(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionProgram.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgram.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgram.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionProgram", "actionProgram_actionPerformed", e);
        }
    }

    /**
     * output ActionViewProgramCon class
     */     
    protected class ActionViewProgramCon extends ItemAction {     
    
        public ActionViewProgramCon()
        {
            this(null);
        }

        public ActionViewProgramCon(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewProgramCon.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewProgramCon.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewProgramCon.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionViewProgramCon", "actionViewProgramCon_actionPerformed", e);
        }
    }

    /**
     * output ActionAddTblBailLine class
     */     
    protected class ActionAddTblBailLine extends ItemAction {     
    
        public ActionAddTblBailLine()
        {
            this(null);
        }

        public ActionAddTblBailLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddTblBailLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTblBailLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTblBailLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionAddTblBailLine", "actionAddTblBailLine_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveTblBailLine class
     */     
    protected class ActionRemoveTblBailLine extends ItemAction {     
    
        public ActionRemoveTblBailLine()
        {
            this(null);
        }

        public ActionRemoveTblBailLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRemoveTblBailLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveTblBailLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveTblBailLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionRemoveTblBailLine", "actionRemoveTblBailLine_actionPerformed", e);
        }
    }

    /**
     * output ActionViewInvite class
     */     
    protected class ActionViewInvite extends ItemAction {     
    
        public ActionViewInvite()
        {
            this(null);
        }

        public ActionViewInvite(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewInvite.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewInvite.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewInvite.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionViewInvite", "actionViewInvite_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "actionAcctSelect", "actionAcctSelect_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "actionSplitProj", "actionSplitProj_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "actionSplitBotUp", "actionSplitBotUp_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "actionSplitProd", "actionSplitProd_actionPerformed", e);
        }
    }

    /**
     * output actionRemoveSplit class
     */     
    protected class actionRemoveSplit extends ItemAction {     
    
        public actionRemoveSplit()
        {
            this(null);
        }

        public actionRemoveSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionRemoveSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionRemoveSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionRemoveSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "actionRemoveSplit", "actionRemoveSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionProgrAcctSelect class
     */     
    protected class ActionProgrAcctSelect extends ItemAction {     
    
        public ActionProgrAcctSelect()
        {
            this(null);
        }

        public ActionProgrAcctSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionProgrAcctSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgrAcctSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgrAcctSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionProgrAcctSelect", "actionProgrAcctSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionContractPriceSplit class
     */     
    protected class ActionContractPriceSplit extends ItemAction {     
    
        public ActionContractPriceSplit()
        {
            this(null);
        }

        public ActionContractPriceSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionContractPriceSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractPriceSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionContractPriceSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionContractPriceSplit", "actionContractPriceSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionProfessionSplit class
     */     
    protected class ActionProfessionSplit extends ItemAction {     
    
        public ActionProfessionSplit()
        {
            this(null);
        }

        public ActionProfessionSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionProfessionSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProfessionSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProfessionSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillEditUI.this, "ActionProfessionSplit", "actionProfessionSplit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractBillEditUI");
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
        return com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.ContractBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.ContractBillInfo objectValue = new com.kingdee.eas.fdc.contract.ContractBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/contract/ContractBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractBillQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtSplitEntry;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
				vo.put("stampTaxAmt",new java.math.BigDecimal(0));
		vo.put("stampTaxRate",new java.math.BigDecimal(0));
		vo.put("originalAmount",new java.math.BigDecimal(0));
		vo.put("ceremonyb",new java.math.BigDecimal(0.00));
		vo.put("controlBalance",new java.math.BigDecimal(0));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}