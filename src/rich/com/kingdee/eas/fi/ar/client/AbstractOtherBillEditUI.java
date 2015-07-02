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
public abstract class AbstractOtherBillEditUI extends com.kingdee.eas.fi.ar.client.ArApBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractOtherBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsaleOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttotalAmounts;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contabstractName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBillType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer18;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer19;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAccountCussentId;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer20;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer21;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer22;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer23;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer24;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer25;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkBoxIsTransBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer26;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleGroup;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastExhangeRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer27;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer28;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbxIsRed;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsInTax;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsPriceWithoutTax;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer29;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsAllowanceBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer30;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsBizBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUpdateDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer costCenter;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCompanyOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurrency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSaleOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAbstractName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdComBillDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox kdComBillType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSettleType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtExchangeRate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox kdComAsstActType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAccountCussent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtUnVerifyAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtVerifyAmt;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdAuditDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAccountant;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBillStatus;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalAmtLocal;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPlan;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox kdComSourceBillType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCashDiscount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdComBizDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtVerifyAmtLocal;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtUnVerifyAmtLocal;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBizType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSaleGroup;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLastExhangeRate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPaymentType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPerson;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalEntryAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalEntryTaxAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalQty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayCondition;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkUpdateDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostCenterUnit;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField kDFormattedTextField1;
    protected com.kingdee.bos.ctrl.swing.KDTextField kDTextField1;
    protected javax.swing.JToolBar.Separator separator6;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemainAmountQuery;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFetchPricePolicy;
    protected javax.swing.JToolBar.Separator separator8;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditVouchers;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnScmVerifyQuery;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemScmVerifyQuery;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRemainAmountQuery;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemFetchPricePolicy;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemEditVouchers;
    protected com.kingdee.eas.fi.ar.OtherBillInfo editData = null;
    protected ActionFetchPricePolicy actionFetchPricePolicy = null;
    protected ActionRemainAmountQuery actionRemainAmountQuery = null;
    protected ActionEditVouchers actionEditVouchers = null;
    protected ActionViewWriteOffRecord actionViewWriteOffRecord = null;
    /**
     * output class constructor
     */
    public AbstractOtherBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractOtherBillEditUI.class.getName());
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
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmit
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl s"));
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
        //actionCopy
        actionCopy.setEnabled(true);
        actionCopy.setDaemonRun(false);

        actionCopy.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift C"));
        _tempStr = resHelper.getString("ActionCopy.SHORT_DESCRIPTION");
        actionCopy.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCopy.LONG_DESCRIPTION");
        actionCopy.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCopy.NAME");
        actionCopy.putValue(ItemAction.NAME, _tempStr);
         this.actionCopy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionCopy.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionCopy.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAddNew
        actionAddNew.setEnabled(true);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl n"));
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
        //actionRemove
        actionRemove.setEnabled(false);
        actionRemove.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
        this.actionRemove.setBindWorkFlow(true);
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
        //actionFetchPricePolicy
        this.actionFetchPricePolicy = new ActionFetchPricePolicy(this);
        getActionManager().registerAction("actionFetchPricePolicy", actionFetchPricePolicy);
         this.actionFetchPricePolicy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemainAmountQuery
        this.actionRemainAmountQuery = new ActionRemainAmountQuery(this);
        getActionManager().registerAction("actionRemainAmountQuery", actionRemainAmountQuery);
         this.actionRemainAmountQuery.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditVouchers
        this.actionEditVouchers = new ActionEditVouchers(this);
        getActionManager().registerAction("actionEditVouchers", actionEditVouchers);
         this.actionEditVouchers.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionEditVouchers.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionEditVouchers.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionViewWriteOffRecord
        this.actionViewWriteOffRecord = new ActionViewWriteOffRecord(this);
        getActionManager().registerAction("actionViewWriteOffRecord", actionViewWriteOffRecord);
         this.actionViewWriteOffRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsaleOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttotalAmounts = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contabstractName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer17 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer18 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer19 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAccountCussentId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDLabelContainer20 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer21 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer22 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer23 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer24 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer25 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.checkBoxIsTransBill = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer26 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSaleGroup = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastExhangeRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer27 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer28 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbxIsRed = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsInTax = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsPriceWithoutTax = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer29 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsAllowanceBill = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer30 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsBizBill = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUpdateDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.costCenter = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.dateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bizPromptAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCompanyOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSaleOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAbstractName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdComBillDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdComBillType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtSettleType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtExchangeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdComAsstActType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtAccountCussent = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtUnVerifyAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtVerifyAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdAuditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAccountant = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboBillStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtTotalAmtLocal = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtPlan = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtAdminOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdComSourceBillType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtCashDiscount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdComBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtVerifyAmtLocal = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtUnVerifyAmtLocal = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtBizType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSaleGroup = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtLastExhangeRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtPaymentType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTotalEntryAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalEntryTaxAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalQty = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtPayCondition = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkUpdateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCostCenterUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDFormattedTextField1 = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDTextField1 = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.separator6 = new javax.swing.JToolBar.Separator();
        this.btnRemainAmountQuery = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFetchPricePolicy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator8 = new javax.swing.JToolBar.Separator();
        this.btnEditVouchers = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnScmVerifyQuery = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemScmVerifyQuery = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRemainAmountQuery = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemFetchPricePolicy = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemEditVouchers = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contcurrency.setName("contcurrency");
        this.contsaleOrg.setName("contsaleOrg");
        this.conttotalAmounts.setName("conttotalAmounts");
        this.contabstractName.setName("contabstractName");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.contBillType.setName("contBillType");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.kDLabelContainer17.setName("kDLabelContainer17");
        this.kDLabelContainer18.setName("kDLabelContainer18");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer19.setName("kDLabelContainer19");
        this.txtAccountCussentId.setName("txtAccountCussentId");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDLabelContainer20.setName("kDLabelContainer20");
        this.kDLabelContainer21.setName("kDLabelContainer21");
        this.kDLabelContainer22.setName("kDLabelContainer22");
        this.kDLabelContainer23.setName("kDLabelContainer23");
        this.kDLabelContainer24.setName("kDLabelContainer24");
        this.kDLabelContainer25.setName("kDLabelContainer25");
        this.checkBoxIsTransBill.setName("checkBoxIsTransBill");
        this.kDLabelContainer26.setName("kDLabelContainer26");
        this.contSaleGroup.setName("contSaleGroup");
        this.contLastExhangeRate.setName("contLastExhangeRate");
        this.kDLabelContainer27.setName("kDLabelContainer27");
        this.contPerson.setName("contPerson");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer28.setName("kDLabelContainer28");
        this.cbxIsRed.setName("cbxIsRed");
        this.cbIsInTax.setName("cbIsInTax");
        this.cbIsPriceWithoutTax.setName("cbIsPriceWithoutTax");
        this.kDLabelContainer29.setName("kDLabelContainer29");
        this.cbIsAllowanceBill.setName("cbIsAllowanceBill");
        this.kDLabelContainer30.setName("kDLabelContainer30");
        this.cbIsBizBill.setName("cbIsBizBill");
        this.contUpdateUser.setName("contUpdateUser");
        this.contUpdateDate.setName("contUpdateDate");
        this.costCenter.setName("costCenter");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.txtNumber.setName("txtNumber");
        this.dateCreateTime.setName("dateCreateTime");
        this.bizPromptAuditor.setName("bizPromptAuditor");
        this.bizPromptCreator.setName("bizPromptCreator");
        this.prmtCompanyOrgUnit.setName("prmtCompanyOrgUnit");
        this.prmtCurrency.setName("prmtCurrency");
        this.prmtSaleOrgUnit.setName("prmtSaleOrgUnit");
        this.txtTotalAmount.setName("txtTotalAmount");
        this.txtAbstractName.setName("txtAbstractName");
        this.kdComBillDate.setName("kdComBillDate");
        this.kdComBillType.setName("kdComBillType");
        this.prmtSettleType.setName("prmtSettleType");
        this.txtExchangeRate.setName("txtExchangeRate");
        this.kdComAsstActType.setName("kdComAsstActType");
        this.prmtAccountCussent.setName("prmtAccountCussent");
        this.txtUnVerifyAmt.setName("txtUnVerifyAmt");
        this.txtVerifyAmt.setName("txtVerifyAmt");
        this.kdAuditDate.setName("kdAuditDate");
        this.prmtAccountant.setName("prmtAccountant");
        this.comboBillStatus.setName("comboBillStatus");
        this.txtTotalAmtLocal.setName("txtTotalAmtLocal");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kdtEntry.setName("kdtEntry");
        this.kdtPlan.setName("kdtPlan");
        this.prmtAdminOrgUnit.setName("prmtAdminOrgUnit");
        this.kdComSourceBillType.setName("kdComSourceBillType");
        this.prmtCashDiscount.setName("prmtCashDiscount");
        this.kdComBizDate.setName("kdComBizDate");
        this.txtVerifyAmtLocal.setName("txtVerifyAmtLocal");
        this.txtUnVerifyAmtLocal.setName("txtUnVerifyAmtLocal");
        this.prmtBizType.setName("prmtBizType");
        this.prmtSaleGroup.setName("prmtSaleGroup");
        this.txtLastExhangeRate.setName("txtLastExhangeRate");
        this.prmtPaymentType.setName("prmtPaymentType");
        this.prmtPerson.setName("prmtPerson");
        this.txtTotalEntryAmount.setName("txtTotalEntryAmount");
        this.txtTotalEntryTaxAmount.setName("txtTotalEntryTaxAmount");
        this.txtTotalQty.setName("txtTotalQty");
        this.prmtPayCondition.setName("prmtPayCondition");
        this.prmtUpdateUser.setName("prmtUpdateUser");
        this.pkUpdateDate.setName("pkUpdateDate");
        this.prmtCostCenterUnit.setName("prmtCostCenterUnit");
        this.kDFormattedTextField1.setName("kDFormattedTextField1");
        this.kDTextField1.setName("kDTextField1");
        this.separator6.setName("separator6");
        this.btnRemainAmountQuery.setName("btnRemainAmountQuery");
        this.btnFetchPricePolicy.setName("btnFetchPricePolicy");
        this.separator8.setName("separator8");
        this.btnEditVouchers.setName("btnEditVouchers");
        this.btnScmVerifyQuery.setName("btnScmVerifyQuery");
        this.menuItemScmVerifyQuery.setName("menuItemScmVerifyQuery");
        this.menuItemRemainAmountQuery.setName("menuItemRemainAmountQuery");
        this.menuItemFetchPricePolicy.setName("menuItemFetchPricePolicy");
        this.menuItemEditVouchers.setName("menuItemEditVouchers");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.setAutoscrolls(true);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.kDSeparator2.setVisible(true);		
        this.separatorFile1.setEnabled(false);		
        this.separatorFile1.setVisible(false);		
        this.menuItemCancelCancel.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW2.setEnabled(false);		
        this.btnAuditResult.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.btnVoucher.setVisible(true);		
        this.btnDelVoucher.setVisible(true);		
        this.MenuItemVoucher.setVisible(true);		
        this.menuItemDelVoucher.setVisible(true);		
        this.separatorFW8.setEnabled(false);		
        this.separatorFW8.setVisible(false);		
        this.separatorFW7.setEnabled(false);		
        this.separatorFW7.setVisible(false);		
        this.menuItemAudit.setToolTipText(resHelper.getString("menuItemAudit.toolTipText"));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setVisible(true);
        // contcurrency		
        this.contcurrency.setBoundLabelText(resHelper.getString("contcurrency.boundLabelText"));		
        this.contcurrency.setBoundLabelLength(100);		
        this.contcurrency.setBoundLabelUnderline(true);		
        this.contcurrency.setVisible(true);
        // contsaleOrg		
        this.contsaleOrg.setBoundLabelText(resHelper.getString("contsaleOrg.boundLabelText"));		
        this.contsaleOrg.setBoundLabelLength(100);		
        this.contsaleOrg.setBoundLabelUnderline(true);		
        this.contsaleOrg.setVisible(true);
        // conttotalAmounts		
        this.conttotalAmounts.setBoundLabelText(resHelper.getString("conttotalAmounts.boundLabelText"));		
        this.conttotalAmounts.setBoundLabelLength(100);		
        this.conttotalAmounts.setBoundLabelUnderline(true);		
        this.conttotalAmounts.setVisible(true);
        // contabstractName		
        this.contabstractName.setBoundLabelText(resHelper.getString("contabstractName.boundLabelText"));		
        this.contabstractName.setBoundLabelLength(100);		
        this.contabstractName.setBoundLabelUnderline(true);		
        this.contabstractName.setVisible(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // contBillType		
        this.contBillType.setBoundLabelText(resHelper.getString("contBillType.boundLabelText"));		
        this.contBillType.setBoundLabelLength(100);		
        this.contBillType.setBoundLabelUnderline(true);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(100);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(100);		
        this.kDLabelContainer15.setBoundLabelUnderline(true);
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(100);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);		
        this.kDLabelContainer16.setVisible(false);
        // kDLabelContainer17		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelLength(100);		
        this.kDLabelContainer17.setBoundLabelUnderline(true);
        // kDLabelContainer18		
        this.kDLabelContainer18.setBoundLabelText(resHelper.getString("kDLabelContainer18.boundLabelText"));		
        this.kDLabelContainer18.setBoundLabelLength(100);		
        this.kDLabelContainer18.setBoundLabelUnderline(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // kDLabelContainer19		
        this.kDLabelContainer19.setBoundLabelText(resHelper.getString("kDLabelContainer19.boundLabelText"));		
        this.kDLabelContainer19.setBoundLabelLength(100);		
        this.kDLabelContainer19.setBoundLabelUnderline(true);		
        this.kDLabelContainer19.setVisible(false);
        // txtAccountCussentId		
        this.txtAccountCussentId.setText(resHelper.getString("txtAccountCussentId.text"));		
        this.txtAccountCussentId.setVisible(false);
        // kDTabbedPane1		
        this.kDTabbedPane1.setAutoscrolls(true);
        this.kDTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDTabbedPane1_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabelContainer20		
        this.kDLabelContainer20.setBoundLabelText(resHelper.getString("kDLabelContainer20.boundLabelText"));		
        this.kDLabelContainer20.setBoundLabelLength(100);		
        this.kDLabelContainer20.setBoundLabelUnderline(true);
        // kDLabelContainer21		
        this.kDLabelContainer21.setBoundLabelText(resHelper.getString("kDLabelContainer21.boundLabelText"));		
        this.kDLabelContainer21.setBoundLabelLength(100);		
        this.kDLabelContainer21.setBoundLabelUnderline(true);
        // kDLabelContainer22		
        this.kDLabelContainer22.setBoundLabelText(resHelper.getString("kDLabelContainer22.boundLabelText"));		
        this.kDLabelContainer22.setBoundLabelLength(100);		
        this.kDLabelContainer22.setBoundLabelUnderline(true);
        // kDLabelContainer23		
        this.kDLabelContainer23.setBoundLabelText(resHelper.getString("kDLabelContainer23.boundLabelText"));		
        this.kDLabelContainer23.setBoundLabelLength(100);		
        this.kDLabelContainer23.setBoundLabelUnderline(true);
        // kDLabelContainer24		
        this.kDLabelContainer24.setBoundLabelText(resHelper.getString("kDLabelContainer24.boundLabelText"));		
        this.kDLabelContainer24.setBoundLabelLength(100);		
        this.kDLabelContainer24.setBoundLabelUnderline(true);		
        this.kDLabelContainer24.setVisible(false);
        // kDLabelContainer25		
        this.kDLabelContainer25.setBoundLabelText(resHelper.getString("kDLabelContainer25.boundLabelText"));		
        this.kDLabelContainer25.setBoundLabelLength(100);		
        this.kDLabelContainer25.setBoundLabelUnderline(true);		
        this.kDLabelContainer25.setVisible(false);
        // checkBoxIsTransBill		
        this.checkBoxIsTransBill.setText(resHelper.getString("checkBoxIsTransBill.text"));		
        this.checkBoxIsTransBill.setEnabled(false);
        // kDLabelContainer26		
        this.kDLabelContainer26.setBoundLabelText(resHelper.getString("kDLabelContainer26.boundLabelText"));		
        this.kDLabelContainer26.setBoundLabelUnderline(true);		
        this.kDLabelContainer26.setBoundLabelLength(100);
        // contSaleGroup		
        this.contSaleGroup.setBoundLabelText(resHelper.getString("contSaleGroup.boundLabelText"));		
        this.contSaleGroup.setBoundLabelLength(100);		
        this.contSaleGroup.setBoundLabelUnderline(true);
        // contLastExhangeRate		
        this.contLastExhangeRate.setBoundLabelText(resHelper.getString("contLastExhangeRate.boundLabelText"));		
        this.contLastExhangeRate.setBoundLabelLength(100);		
        this.contLastExhangeRate.setBoundLabelUnderline(true);		
        this.contLastExhangeRate.setVisible(false);
        // kDLabelContainer27		
        this.kDLabelContainer27.setBoundLabelText(resHelper.getString("kDLabelContainer27.boundLabelText"));		
        this.kDLabelContainer27.setBoundLabelLength(100);		
        this.kDLabelContainer27.setBoundLabelUnderline(true);
        // contPerson		
        this.contPerson.setBoundLabelText(resHelper.getString("contPerson.boundLabelText"));		
        this.contPerson.setBoundLabelLength(100);		
        this.contPerson.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelUnderline(true);		
        this.kDLabelContainer9.setBoundLabelLength(100);
        // kDLabelContainer28		
        this.kDLabelContainer28.setBoundLabelText(resHelper.getString("kDLabelContainer28.boundLabelText"));		
        this.kDLabelContainer28.setBoundLabelUnderline(true);		
        this.kDLabelContainer28.setBoundLabelLength(100);
        // cbxIsRed		
        this.cbxIsRed.setText(resHelper.getString("cbxIsRed.text"));		
        this.cbxIsRed.setVisible(false);
        this.cbxIsRed.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbxIsRed_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.cbxIsRed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    cbxIsRed_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.cbxIsRed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cbxIsRed_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // cbIsInTax		
        this.cbIsInTax.setText(resHelper.getString("cbIsInTax.text"));
        this.cbIsInTax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cbIsInTax_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // cbIsPriceWithoutTax		
        this.cbIsPriceWithoutTax.setText(resHelper.getString("cbIsPriceWithoutTax.text"));
        this.cbIsPriceWithoutTax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cbIsPriceWithoutTax_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer29		
        this.kDLabelContainer29.setBoundLabelText(resHelper.getString("kDLabelContainer29.boundLabelText"));		
        this.kDLabelContainer29.setBoundLabelLength(30);		
        this.kDLabelContainer29.setBoundLabelUnderline(true);		
        this.kDLabelContainer29.setVisible(false);
        // cbIsAllowanceBill		
        this.cbIsAllowanceBill.setText(resHelper.getString("cbIsAllowanceBill.text"));		
        this.cbIsAllowanceBill.setEnabled(false);
        // kDLabelContainer30		
        this.kDLabelContainer30.setBoundLabelText(resHelper.getString("kDLabelContainer30.boundLabelText"));		
        this.kDLabelContainer30.setBoundLabelLength(100);		
        this.kDLabelContainer30.setBoundLabelUnderline(true);
        // cbIsBizBill		
        this.cbIsBizBill.setText(resHelper.getString("cbIsBizBill.text"));
        // contUpdateUser		
        this.contUpdateUser.setBoundLabelText(resHelper.getString("contUpdateUser.boundLabelText"));		
        this.contUpdateUser.setBoundLabelLength(100);		
        this.contUpdateUser.setBoundLabelUnderline(true);		
        this.contUpdateUser.setEnabled(false);
        // contUpdateDate		
        this.contUpdateDate.setBoundLabelText(resHelper.getString("contUpdateDate.boundLabelText"));		
        this.contUpdateDate.setBoundLabelLength(100);		
        this.contUpdateDate.setBoundLabelUnderline(true);
        // costCenter		
        this.costCenter.setBoundLabelText(resHelper.getString("costCenter.boundLabelText"));		
        this.costCenter.setBoundLabelLength(100);		
        this.costCenter.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelUnderline(true);		
        this.kDLabelContainer8.setBoundLabelLength(100);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // dateCreateTime		
        this.dateCreateTime.setEditable(false);		
        this.dateCreateTime.setEnabled(false);
        // bizPromptAuditor		
        this.bizPromptAuditor.setCommitFormat("$number$");		
        this.bizPromptAuditor.setEditFormat("$number$");		
        this.bizPromptAuditor.setDisplayFormat("$name$");		
        this.bizPromptAuditor.setEditable(true);		
        this.bizPromptAuditor.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.bizPromptAuditor.setReadOnly(true);		
        this.bizPromptAuditor.setEnabled(false);
        // bizPromptCreator		
        this.bizPromptCreator.setDisplayFormat("$name$");		
        this.bizPromptCreator.setEditFormat("$number$");		
        this.bizPromptCreator.setCommitFormat("$number$");		
        this.bizPromptCreator.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.bizPromptCreator.setEditable(true);		
        this.bizPromptCreator.setReadOnly(true);		
        this.bizPromptCreator.setEnabled(false);
        // prmtCompanyOrgUnit		
        this.prmtCompanyOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.prmtCompanyOrgUnit.setEditable(true);		
        this.prmtCompanyOrgUnit.setCommitFormat("$number$");		
        this.prmtCompanyOrgUnit.setEnabled(false);
        this.prmtCompanyOrgUnit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    prmtCompanyOrgUnit_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // prmtCurrency		
        this.prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.prmtCurrency.setVisible(true);		
        this.prmtCurrency.setEditable(true);		
        this.prmtCurrency.setDisplayFormat("$name$");		
        this.prmtCurrency.setEditFormat("$number$");		
        this.prmtCurrency.setCommitFormat("$number$");		
        this.prmtCurrency.setRequired(true);
        // prmtSaleOrgUnit		
        this.prmtSaleOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.SaleOrgUnitQuery");		
        this.prmtSaleOrgUnit.setVisible(true);		
        this.prmtSaleOrgUnit.setEditable(true);		
        this.prmtSaleOrgUnit.setDisplayFormat("$name$");		
        this.prmtSaleOrgUnit.setEditFormat("$number$");		
        this.prmtSaleOrgUnit.setCommitFormat("$number$");		
        this.prmtSaleOrgUnit.setRequired(true);
        // txtTotalAmount		
        this.txtTotalAmount.setDataType(1);		
        this.txtTotalAmount.setEditable(false);
        // txtAbstractName		
        this.txtAbstractName.setVisible(true);		
        this.txtAbstractName.setHorizontalAlignment(2);		
        this.txtAbstractName.setMaxLength(80);		
        this.txtAbstractName.setEnabled(true);
        // kdComBillDate
        // kdComBillType		
        this.kdComBillType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fi.ar.OtherBillTypeEnum").toArray());		
        this.kdComBillType.setEditable(true);		
        this.kdComBillType.setRequired(true);		
        this.kdComBillType.setSelectedIndex(2);
        // prmtSettleType		
        this.prmtSettleType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");		
        this.prmtSettleType.setEditable(true);		
        this.prmtSettleType.setDisplayFormat("$name$");		
        this.prmtSettleType.setEditFormat("$number$");		
        this.prmtSettleType.setCommitFormat("$number$");
        // txtExchangeRate		
        this.txtExchangeRate.setText(resHelper.getString("txtExchangeRate.text"));		
        this.txtExchangeRate.setDataType(1);		
        this.txtExchangeRate.setRequired(true);
        // kdComAsstActType		
        this.kdComAsstActType.setRequired(true);
        this.kdComAsstActType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    kdComAsstActType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtAccountCussent		
        this.prmtAccountCussent.setEditable(true);		
        this.prmtAccountCussent.setCommitFormat("$number$");		
        this.prmtAccountCussent.setEditFormat("$number$");		
        this.prmtAccountCussent.setDisplayFormat("$name$");		
        this.prmtAccountCussent.setRequired(true);
        // txtUnVerifyAmt		
        this.txtUnVerifyAmt.setText(resHelper.getString("txtUnVerifyAmt.text"));		
        this.txtUnVerifyAmt.setDataType(1);		
        this.txtUnVerifyAmt.setEditable(false);
        // txtVerifyAmt		
        this.txtVerifyAmt.setText(resHelper.getString("txtVerifyAmt.text"));		
        this.txtVerifyAmt.setDataType(1);		
        this.txtVerifyAmt.setEditable(false);
        // kdAuditDate		
        this.kdAuditDate.setEditable(false);		
        this.kdAuditDate.setEnabled(false);
        // prmtAccountant		
        this.prmtAccountant.setCommitFormat("$number$");		
        this.prmtAccountant.setEditFormat("$number$");		
        this.prmtAccountant.setDisplayFormat("$name$");		
        this.prmtAccountant.setReadOnly(true);		
        this.prmtAccountant.setEditable(true);		
        this.prmtAccountant.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtAccountant.setEnabled(false);
        // comboBillStatus		
        this.comboBillStatus.setEnabled(false);		
        this.comboBillStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.fi.ar.BillStatusEnum").toArray());
        // txtTotalAmtLocal		
        this.txtTotalAmtLocal.setDataType(1);		
        this.txtTotalAmtLocal.setEditable(false);
        // kDPanel1		
        this.kDPanel1.setPreferredSize(new Dimension(1000,300));
        // kDPanel2		
        this.kDPanel2.setPreferredSize(new Dimension(1000,300));
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol19\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol20\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol21\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol22\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol23\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol24\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol27\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol28\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol29\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol30\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol31\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol32\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol33\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol34\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol35\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol36\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol37\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol38\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol39\"><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol41\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol42\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol43\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol47\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol48\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol49\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol50\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol52\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol53\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol54\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol55\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.00}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol56\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"rowType\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"expenseItemNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"expenseItemName\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"materialNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"materialName\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"specification\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"assistProperty\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"isPresent\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"measureUnit\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"quantity\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"assistUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"assistQty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"taxRate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"price\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"taxPrice\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"discountType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"discountRate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"discountAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"discountAmountLocal\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol18\" /><t:Column t:key=\"realPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol19\" /><t:Column t:key=\"actualPrice\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol20\" /><t:Column t:key=\"amount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol21\" /><t:Column t:key=\"amountLocal\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol22\" /><t:Column t:key=\"taxAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol23\" /><t:Column t:key=\"taxAmountLocal\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol24\" /><t:Column t:key=\"oppAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"account\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"recPayAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol27\" /><t:Column t:key=\"recPayAmountLocal\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol28\" /><t:Column t:key=\"verifyAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol29\" /><t:Column t:key=\"verifyAmountLocal\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol30\" /><t:Column t:key=\"badAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol31\" /><t:Column t:key=\"badAmountLocal\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol32\" /><t:Column t:key=\"unVerifyAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol33\" /><t:Column t:key=\"unVerifyAmountLocal\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol34\" /><t:Column t:key=\"preparedBadAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" t:styleID=\"sCol35\" /><t:Column t:key=\"preparedBadAmountLocal\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" t:styleID=\"sCol36\" /><t:Column t:key=\"lockVerifyAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" t:styleID=\"sCol37\" /><t:Column t:key=\"lockUnVerifyAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" t:styleID=\"sCol38\" /><t:Column t:key=\"invoicedAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" t:styleID=\"sCol39\" /><t:Column t:key=\"remark\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" /><t:Column t:key=\"coreBillType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"41\" t:styleID=\"sCol41\" /><t:Column t:key=\"coreBillNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"42\" t:styleID=\"sCol42\" /><t:Column t:key=\"coreBillEntrySeq\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"43\" t:styleID=\"sCol43\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"44\" /><t:Column t:key=\"trackNumberzc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"45\" /><t:Column t:key=\"lot\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"46\" /><t:Column t:key=\"contractNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"47\" t:styleID=\"sCol47\" /><t:Column t:key=\"contractEntrySeq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"48\" t:styleID=\"sCol48\" /><t:Column t:key=\"baseUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"49\" t:styleID=\"sCol49\" /><t:Column t:key=\"baseQty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"50\" t:styleID=\"sCol50\" /><t:Column t:key=\"isFullWriteOff\" t:width=\"75\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"51\" /><t:Column t:key=\"wittenOffBaseQty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"52\" t:styleID=\"sCol52\" /><t:Column t:key=\"localWrittenOffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"53\" t:styleID=\"sCol53\" /><t:Column t:key=\"unwriteOffBaseQty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"54\" t:styleID=\"sCol54\" /><t:Column t:key=\"localUnwriteOffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"55\" t:styleID=\"sCol55\" /><t:Column t:key=\"entryId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"56\" t:styleID=\"sCol56\" /><t:Column t:key=\"recSendOrgUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"57\" /><t:Column t:key=\"orderCustomer\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"58\" /><t:Column t:key=\"serviceCustomer\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"59\" /><t:Column t:key=\"recAsstActType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"60\" /><t:Column t:key=\"receivablesCustomer\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"61\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{rowType}</t:Cell><t:Cell>$Resource{expenseItemNumber}</t:Cell><t:Cell>$Resource{expenseItemName}</t:Cell><t:Cell>$Resource{materialNumber}</t:Cell><t:Cell>$Resource{materialName}</t:Cell><t:Cell>$Resource{specification}</t:Cell><t:Cell>$Resource{assistProperty}</t:Cell><t:Cell>$Resource{isPresent}</t:Cell><t:Cell>$Resource{measureUnit}</t:Cell><t:Cell>$Resource{quantity}</t:Cell><t:Cell>$Resource{assistUnit}</t:Cell><t:Cell>$Resource{assistQty}</t:Cell><t:Cell>$Resource{taxRate}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{taxPrice}</t:Cell><t:Cell>$Resource{discountType}</t:Cell><t:Cell>$Resource{discountRate}</t:Cell><t:Cell>$Resource{discountAmount}</t:Cell><t:Cell>$Resource{discountAmountLocal}</t:Cell><t:Cell>$Resource{realPrice}</t:Cell><t:Cell>$Resource{actualPrice}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{amountLocal}</t:Cell><t:Cell>$Resource{taxAmount}</t:Cell><t:Cell>$Resource{taxAmountLocal}</t:Cell><t:Cell>$Resource{oppAccount}</t:Cell><t:Cell>$Resource{account}</t:Cell><t:Cell>$Resource{recPayAmount}</t:Cell><t:Cell>$Resource{recPayAmountLocal}</t:Cell><t:Cell>$Resource{verifyAmount}</t:Cell><t:Cell>$Resource{verifyAmountLocal}</t:Cell><t:Cell>$Resource{badAmount}</t:Cell><t:Cell>$Resource{badAmountLocal}</t:Cell><t:Cell>$Resource{unVerifyAmount}</t:Cell><t:Cell>$Resource{unVerifyAmountLocal}</t:Cell><t:Cell>$Resource{preparedBadAmount}</t:Cell><t:Cell>$Resource{preparedBadAmountLocal}</t:Cell><t:Cell>$Resource{lockVerifyAmt}</t:Cell><t:Cell>$Resource{lockUnVerifyAmt}</t:Cell><t:Cell>$Resource{invoicedAmt}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{coreBillType}</t:Cell><t:Cell>$Resource{coreBillNumber}</t:Cell><t:Cell>$Resource{coreBillEntrySeq}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{trackNumberzc}</t:Cell><t:Cell>$Resource{lot}</t:Cell><t:Cell>$Resource{contractNum}</t:Cell><t:Cell>$Resource{contractEntrySeq}</t:Cell><t:Cell>$Resource{baseUnit}</t:Cell><t:Cell>$Resource{baseQty}</t:Cell><t:Cell>$Resource{isFullWriteOff}</t:Cell><t:Cell>$Resource{wittenOffBaseQty}</t:Cell><t:Cell>$Resource{localWrittenOffAmount}</t:Cell><t:Cell>$Resource{unwriteOffBaseQty}</t:Cell><t:Cell>$Resource{localUnwriteOffAmount}</t:Cell><t:Cell>$Resource{entryId}</t:Cell><t:Cell>$Resource{recSendOrgUnit}</t:Cell><t:Cell>$Resource{orderCustomer}</t:Cell><t:Cell>$Resource{serviceCustomer}</t:Cell><t:Cell>$Resource{recAsstActType}</t:Cell><t:Cell>$Resource{receivablesCustomer}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

                this.kdtEntry.putBindContents("editData",new String[] {"rowType","expenseItem","expenseItem.name","material","materialName","material.model","assistProperty","isPresent","measureUnit","quantity","assistUnit","assistQty","taxRate","price","taxPrice","discountType","discountRate","discountAmount","discountAmountLocal","realPrice","actualPrice","amount","amountLocal","taxAmount","taxAmountLocal","oppAccount","account","recievePayAmount","recievePayAmountLocal","verifyAmount","verifyAmountLocal","badAmount","badAmountLocal","unVerifyAmount","unVerifyAmountLocal","preparedBadAmount","preparedBadAmountLocal","lockVerifyAmt","lockUnVerifyAmt","invoicedAmt","remark","coreBillType","coreBillNumber","coreBillEntrySeq","project","trackNumberzc","lot","contractNum","contractEntrySeq","baseUnit","baseQty","isFullWriteOff","wittenOffBaseQty","localWrittenOffAmount","unwriteOffBaseQty","localUnwriteOffAmount","id","recSendOrgUnit","orderCustomer","serviceCustomer","recAsstActType",""});


        this.kdtEntry.checkParsed();
        final KDBizPromptBox kdtEntry_rowType_PromptBox = new KDBizPromptBox();
        kdtEntry_rowType_PromptBox.setQueryInfo("com.kingdee.eas.basedata.scm.common.app.RowTypeQuery");
        kdtEntry_rowType_PromptBox.setVisible(true);
        kdtEntry_rowType_PromptBox.setEditable(true);
        kdtEntry_rowType_PromptBox.setDisplayFormat("$number$");
        kdtEntry_rowType_PromptBox.setEditFormat("$number$");
        kdtEntry_rowType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_rowType_CellEditor = new KDTDefaultCellEditor(kdtEntry_rowType_PromptBox);
        this.kdtEntry.getColumn("rowType").setEditor(kdtEntry_rowType_CellEditor);
        ObjectValueRender kdtEntry_rowType_OVR = new ObjectValueRender();
        kdtEntry_rowType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("rowType").setRenderer(kdtEntry_rowType_OVR);
        final KDBizPromptBox kdtEntry_expenseItemNumber_PromptBox = new KDBizPromptBox();
        kdtEntry_expenseItemNumber_PromptBox.setQueryInfo("com.kingdee.eas.basedata.scm.common.app.ExpenseItemQuery");
        kdtEntry_expenseItemNumber_PromptBox.setVisible(true);
        kdtEntry_expenseItemNumber_PromptBox.setEditable(true);
        kdtEntry_expenseItemNumber_PromptBox.setDisplayFormat("$number$");
        kdtEntry_expenseItemNumber_PromptBox.setEditFormat("$number$");
        kdtEntry_expenseItemNumber_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_expenseItemNumber_CellEditor = new KDTDefaultCellEditor(kdtEntry_expenseItemNumber_PromptBox);
        this.kdtEntry.getColumn("expenseItemNumber").setEditor(kdtEntry_expenseItemNumber_CellEditor);
        ObjectValueRender kdtEntry_expenseItemNumber_OVR = new ObjectValueRender();
        kdtEntry_expenseItemNumber_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("expenseItemNumber").setRenderer(kdtEntry_expenseItemNumber_OVR);
        final KDBizPromptBox kdtEntry_materialNumber_PromptBox = new KDBizPromptBox();
        kdtEntry_materialNumber_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialBaseInfoQuery");
        kdtEntry_materialNumber_PromptBox.setVisible(true);
        kdtEntry_materialNumber_PromptBox.setEditable(true);
        kdtEntry_materialNumber_PromptBox.setDisplayFormat("$number$");
        kdtEntry_materialNumber_PromptBox.setEditFormat("$number$");
        kdtEntry_materialNumber_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_materialNumber_CellEditor = new KDTDefaultCellEditor(kdtEntry_materialNumber_PromptBox);
        this.kdtEntry.getColumn("materialNumber").setEditor(kdtEntry_materialNumber_CellEditor);
        ObjectValueRender kdtEntry_materialNumber_OVR = new ObjectValueRender();
        kdtEntry_materialNumber_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("materialNumber").setRenderer(kdtEntry_materialNumber_OVR);
        KDTextField kdtEntry_specification_TextField = new KDTextField();
        kdtEntry_specification_TextField.setName("kdtEntry_specification_TextField");
        kdtEntry_specification_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntry_specification_CellEditor = new KDTDefaultCellEditor(kdtEntry_specification_TextField);
        this.kdtEntry.getColumn("specification").setEditor(kdtEntry_specification_CellEditor);
        final KDBizPromptBox kdtEntry_assistProperty_PromptBox = new KDBizPromptBox();
        kdtEntry_assistProperty_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7AsstAttrValueQuery");
        kdtEntry_assistProperty_PromptBox.setVisible(true);
        kdtEntry_assistProperty_PromptBox.setEditable(true);
        kdtEntry_assistProperty_PromptBox.setDisplayFormat("$number$");
        kdtEntry_assistProperty_PromptBox.setEditFormat("$number$");
        kdtEntry_assistProperty_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_assistProperty_CellEditor = new KDTDefaultCellEditor(kdtEntry_assistProperty_PromptBox);
        this.kdtEntry.getColumn("assistProperty").setEditor(kdtEntry_assistProperty_CellEditor);
        ObjectValueRender kdtEntry_assistProperty_OVR = new ObjectValueRender();
        kdtEntry_assistProperty_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("assistProperty").setRenderer(kdtEntry_assistProperty_OVR);
        KDCheckBox kdtEntry_isPresent_CheckBox = new KDCheckBox();
        kdtEntry_isPresent_CheckBox.setName("kdtEntry_isPresent_CheckBox");
        KDTDefaultCellEditor kdtEntry_isPresent_CellEditor = new KDTDefaultCellEditor(kdtEntry_isPresent_CheckBox);
        this.kdtEntry.getColumn("isPresent").setEditor(kdtEntry_isPresent_CellEditor);
        final KDBizPromptBox kdtEntry_measureUnit_PromptBox = new KDBizPromptBox();
        kdtEntry_measureUnit_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
        kdtEntry_measureUnit_PromptBox.setVisible(true);
        kdtEntry_measureUnit_PromptBox.setEditable(true);
        kdtEntry_measureUnit_PromptBox.setDisplayFormat("$number$");
        kdtEntry_measureUnit_PromptBox.setEditFormat("$number$");
        kdtEntry_measureUnit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_measureUnit_CellEditor = new KDTDefaultCellEditor(kdtEntry_measureUnit_PromptBox);
        this.kdtEntry.getColumn("measureUnit").setEditor(kdtEntry_measureUnit_CellEditor);
        ObjectValueRender kdtEntry_measureUnit_OVR = new ObjectValueRender();
        kdtEntry_measureUnit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("measureUnit").setRenderer(kdtEntry_measureUnit_OVR);
        final KDBizPromptBox kdtEntry_assistUnit_PromptBox = new KDBizPromptBox();
        kdtEntry_assistUnit_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
        kdtEntry_assistUnit_PromptBox.setVisible(true);
        kdtEntry_assistUnit_PromptBox.setEditable(true);
        kdtEntry_assistUnit_PromptBox.setDisplayFormat("$number$");
        kdtEntry_assistUnit_PromptBox.setEditFormat("$number$");
        kdtEntry_assistUnit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_assistUnit_CellEditor = new KDTDefaultCellEditor(kdtEntry_assistUnit_PromptBox);
        this.kdtEntry.getColumn("assistUnit").setEditor(kdtEntry_assistUnit_CellEditor);
        ObjectValueRender kdtEntry_assistUnit_OVR = new ObjectValueRender();
        kdtEntry_assistUnit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("assistUnit").setRenderer(kdtEntry_assistUnit_OVR);
        KDComboBox kdtEntry_discountType_ComboBox = new KDComboBox();
        kdtEntry_discountType_ComboBox.setName("kdtEntry_discountType_ComboBox");
        kdtEntry_discountType_ComboBox.setVisible(true);
        kdtEntry_discountType_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.basedata.scm.common.DiscountModeEnum").toArray());
        KDTDefaultCellEditor kdtEntry_discountType_CellEditor = new KDTDefaultCellEditor(kdtEntry_discountType_ComboBox);
        this.kdtEntry.getColumn("discountType").setEditor(kdtEntry_discountType_CellEditor);
        final KDBizPromptBox kdtEntry_oppAccount_PromptBox = new KDBizPromptBox();
        kdtEntry_oppAccount_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
        kdtEntry_oppAccount_PromptBox.setVisible(true);
        kdtEntry_oppAccount_PromptBox.setEditable(true);
        kdtEntry_oppAccount_PromptBox.setDisplayFormat("$number$");
        kdtEntry_oppAccount_PromptBox.setEditFormat("$number$");
        kdtEntry_oppAccount_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_oppAccount_CellEditor = new KDTDefaultCellEditor(kdtEntry_oppAccount_PromptBox);
        this.kdtEntry.getColumn("oppAccount").setEditor(kdtEntry_oppAccount_CellEditor);
        ObjectValueRender kdtEntry_oppAccount_OVR = new ObjectValueRender();
        kdtEntry_oppAccount_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("oppAccount").setRenderer(kdtEntry_oppAccount_OVR);
        final KDBizPromptBox kdtEntry_account_PromptBox = new KDBizPromptBox();
        kdtEntry_account_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
        kdtEntry_account_PromptBox.setVisible(true);
        kdtEntry_account_PromptBox.setEditable(true);
        kdtEntry_account_PromptBox.setDisplayFormat("$number$");
        kdtEntry_account_PromptBox.setEditFormat("$number$");
        kdtEntry_account_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_account_CellEditor = new KDTDefaultCellEditor(kdtEntry_account_PromptBox);
        this.kdtEntry.getColumn("account").setEditor(kdtEntry_account_CellEditor);
        ObjectValueRender kdtEntry_account_OVR = new ObjectValueRender();
        kdtEntry_account_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("account").setRenderer(kdtEntry_account_OVR);
        KDFormattedTextField kdtEntry_badAmount_TextField = new KDFormattedTextField();
        kdtEntry_badAmount_TextField.setName("kdtEntry_badAmount_TextField");
        kdtEntry_badAmount_TextField.setVisible(true);
        kdtEntry_badAmount_TextField.setEditable(true);
        kdtEntry_badAmount_TextField.setHorizontalAlignment(2);
        kdtEntry_badAmount_TextField.setDataType(1);
        	kdtEntry_badAmount_TextField.setMinimumValue(new java.math.BigDecimal("-9.999999999999E8"));
        	kdtEntry_badAmount_TextField.setMaximumValue(new java.math.BigDecimal("9.999999999999E8"));
        kdtEntry_badAmount_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntry_badAmount_CellEditor = new KDTDefaultCellEditor(kdtEntry_badAmount_TextField);
        this.kdtEntry.getColumn("badAmount").setEditor(kdtEntry_badAmount_CellEditor);
        KDFormattedTextField kdtEntry_badAmountLocal_TextField = new KDFormattedTextField();
        kdtEntry_badAmountLocal_TextField.setName("kdtEntry_badAmountLocal_TextField");
        kdtEntry_badAmountLocal_TextField.setVisible(true);
        kdtEntry_badAmountLocal_TextField.setEditable(true);
        kdtEntry_badAmountLocal_TextField.setHorizontalAlignment(2);
        kdtEntry_badAmountLocal_TextField.setDataType(1);
        	kdtEntry_badAmountLocal_TextField.setMinimumValue(new java.math.BigDecimal("-9.999999999999E8"));
        	kdtEntry_badAmountLocal_TextField.setMaximumValue(new java.math.BigDecimal("9.999999999999E8"));
        kdtEntry_badAmountLocal_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntry_badAmountLocal_CellEditor = new KDTDefaultCellEditor(kdtEntry_badAmountLocal_TextField);
        this.kdtEntry.getColumn("badAmountLocal").setEditor(kdtEntry_badAmountLocal_CellEditor);
        KDFormattedTextField kdtEntry_preparedBadAmount_TextField = new KDFormattedTextField();
        kdtEntry_preparedBadAmount_TextField.setName("kdtEntry_preparedBadAmount_TextField");
        kdtEntry_preparedBadAmount_TextField.setVisible(true);
        kdtEntry_preparedBadAmount_TextField.setEditable(true);
        kdtEntry_preparedBadAmount_TextField.setHorizontalAlignment(2);
        kdtEntry_preparedBadAmount_TextField.setDataType(1);
        kdtEntry_preparedBadAmount_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntry_preparedBadAmount_CellEditor = new KDTDefaultCellEditor(kdtEntry_preparedBadAmount_TextField);
        this.kdtEntry.getColumn("preparedBadAmount").setEditor(kdtEntry_preparedBadAmount_CellEditor);
        KDFormattedTextField kdtEntry_preparedBadAmountLocal_TextField = new KDFormattedTextField();
        kdtEntry_preparedBadAmountLocal_TextField.setName("kdtEntry_preparedBadAmountLocal_TextField");
        kdtEntry_preparedBadAmountLocal_TextField.setVisible(true);
        kdtEntry_preparedBadAmountLocal_TextField.setEditable(true);
        kdtEntry_preparedBadAmountLocal_TextField.setHorizontalAlignment(2);
        kdtEntry_preparedBadAmountLocal_TextField.setDataType(1);
        kdtEntry_preparedBadAmountLocal_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntry_preparedBadAmountLocal_CellEditor = new KDTDefaultCellEditor(kdtEntry_preparedBadAmountLocal_TextField);
        this.kdtEntry.getColumn("preparedBadAmountLocal").setEditor(kdtEntry_preparedBadAmountLocal_CellEditor);
        final KDBizPromptBox kdtEntry_coreBillType_PromptBox = new KDBizPromptBox();
        kdtEntry_coreBillType_PromptBox.setQueryInfo("com.kingdee.eas.basedata.scm.common.app.F7BillTypeQuery");
        kdtEntry_coreBillType_PromptBox.setVisible(true);
        kdtEntry_coreBillType_PromptBox.setEditable(true);
        kdtEntry_coreBillType_PromptBox.setDisplayFormat("$number$");
        kdtEntry_coreBillType_PromptBox.setEditFormat("$number$");
        kdtEntry_coreBillType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_coreBillType_CellEditor = new KDTDefaultCellEditor(kdtEntry_coreBillType_PromptBox);
        this.kdtEntry.getColumn("coreBillType").setEditor(kdtEntry_coreBillType_CellEditor);
        ObjectValueRender kdtEntry_coreBillType_OVR = new ObjectValueRender();
        kdtEntry_coreBillType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("coreBillType").setRenderer(kdtEntry_coreBillType_OVR);
        final KDBizPromptBox kdtEntry_project_PromptBox = new KDBizPromptBox();
        kdtEntry_project_PromptBox.setQueryInfo("com.kingdee.eas.mm.project.app.F7ProjectQuery");
        kdtEntry_project_PromptBox.setVisible(true);
        kdtEntry_project_PromptBox.setEditable(true);
        kdtEntry_project_PromptBox.setDisplayFormat("$number$");
        kdtEntry_project_PromptBox.setEditFormat("$number$");
        kdtEntry_project_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_project_CellEditor = new KDTDefaultCellEditor(kdtEntry_project_PromptBox);
        this.kdtEntry.getColumn("project").setEditor(kdtEntry_project_CellEditor);
        ObjectValueRender kdtEntry_project_OVR = new ObjectValueRender();
        kdtEntry_project_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("project").setRenderer(kdtEntry_project_OVR);
        final KDBizPromptBox kdtEntry_trackNumberzc_PromptBox = new KDBizPromptBox();
        kdtEntry_trackNumberzc_PromptBox.setQueryInfo("com.kingdee.eas.mm.basedata.app.TrackNumberQuery");
        kdtEntry_trackNumberzc_PromptBox.setVisible(true);
        kdtEntry_trackNumberzc_PromptBox.setEditable(true);
        kdtEntry_trackNumberzc_PromptBox.setDisplayFormat("$number$");
        kdtEntry_trackNumberzc_PromptBox.setEditFormat("$number$");
        kdtEntry_trackNumberzc_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_trackNumberzc_CellEditor = new KDTDefaultCellEditor(kdtEntry_trackNumberzc_PromptBox);
        this.kdtEntry.getColumn("trackNumberzc").setEditor(kdtEntry_trackNumberzc_CellEditor);
        ObjectValueRender kdtEntry_trackNumberzc_OVR = new ObjectValueRender();
        kdtEntry_trackNumberzc_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("trackNumberzc").setRenderer(kdtEntry_trackNumberzc_OVR);
        final KDBizPromptBox kdtEntry_baseUnit_PromptBox = new KDBizPromptBox();
        kdtEntry_baseUnit_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
        kdtEntry_baseUnit_PromptBox.setVisible(true);
        kdtEntry_baseUnit_PromptBox.setEditable(true);
        kdtEntry_baseUnit_PromptBox.setDisplayFormat("$number$");
        kdtEntry_baseUnit_PromptBox.setEditFormat("$number$");
        kdtEntry_baseUnit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_baseUnit_CellEditor = new KDTDefaultCellEditor(kdtEntry_baseUnit_PromptBox);
        this.kdtEntry.getColumn("baseUnit").setEditor(kdtEntry_baseUnit_CellEditor);
        ObjectValueRender kdtEntry_baseUnit_OVR = new ObjectValueRender();
        kdtEntry_baseUnit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("baseUnit").setRenderer(kdtEntry_baseUnit_OVR);
        KDCheckBox kdtEntry_isFullWriteOff_CheckBox = new KDCheckBox();
        kdtEntry_isFullWriteOff_CheckBox.setName("kdtEntry_isFullWriteOff_CheckBox");
        KDTDefaultCellEditor kdtEntry_isFullWriteOff_CellEditor = new KDTDefaultCellEditor(kdtEntry_isFullWriteOff_CheckBox);
        this.kdtEntry.getColumn("isFullWriteOff").setEditor(kdtEntry_isFullWriteOff_CellEditor);
        final KDBizPromptBox kdtEntry_recSendOrgUnit_PromptBox = new KDBizPromptBox();
        kdtEntry_recSendOrgUnit_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.StorageItemQuery");
        kdtEntry_recSendOrgUnit_PromptBox.setVisible(true);
        kdtEntry_recSendOrgUnit_PromptBox.setEditable(true);
        kdtEntry_recSendOrgUnit_PromptBox.setDisplayFormat("$number$");
        kdtEntry_recSendOrgUnit_PromptBox.setEditFormat("$number$");
        kdtEntry_recSendOrgUnit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_recSendOrgUnit_CellEditor = new KDTDefaultCellEditor(kdtEntry_recSendOrgUnit_PromptBox);
        this.kdtEntry.getColumn("recSendOrgUnit").setEditor(kdtEntry_recSendOrgUnit_CellEditor);
        ObjectValueRender kdtEntry_recSendOrgUnit_OVR = new ObjectValueRender();
        kdtEntry_recSendOrgUnit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("recSendOrgUnit").setRenderer(kdtEntry_recSendOrgUnit_OVR);
        final KDBizPromptBox kdtEntry_orderCustomer_PromptBox = new KDBizPromptBox();
        kdtEntry_orderCustomer_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.CustomerInfoQuery");
        kdtEntry_orderCustomer_PromptBox.setVisible(true);
        kdtEntry_orderCustomer_PromptBox.setEditable(true);
        kdtEntry_orderCustomer_PromptBox.setDisplayFormat("$number$");
        kdtEntry_orderCustomer_PromptBox.setEditFormat("$number$");
        kdtEntry_orderCustomer_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_orderCustomer_CellEditor = new KDTDefaultCellEditor(kdtEntry_orderCustomer_PromptBox);
        this.kdtEntry.getColumn("orderCustomer").setEditor(kdtEntry_orderCustomer_CellEditor);
        ObjectValueRender kdtEntry_orderCustomer_OVR = new ObjectValueRender();
        kdtEntry_orderCustomer_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("orderCustomer").setRenderer(kdtEntry_orderCustomer_OVR);
        final KDBizPromptBox kdtEntry_serviceCustomer_PromptBox = new KDBizPromptBox();
        kdtEntry_serviceCustomer_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.CustomerInfoQuery");
        kdtEntry_serviceCustomer_PromptBox.setVisible(true);
        kdtEntry_serviceCustomer_PromptBox.setEditable(true);
        kdtEntry_serviceCustomer_PromptBox.setDisplayFormat("$number$");
        kdtEntry_serviceCustomer_PromptBox.setEditFormat("$number$");
        kdtEntry_serviceCustomer_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_serviceCustomer_CellEditor = new KDTDefaultCellEditor(kdtEntry_serviceCustomer_PromptBox);
        this.kdtEntry.getColumn("serviceCustomer").setEditor(kdtEntry_serviceCustomer_CellEditor);
        ObjectValueRender kdtEntry_serviceCustomer_OVR = new ObjectValueRender();
        kdtEntry_serviceCustomer_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("serviceCustomer").setRenderer(kdtEntry_serviceCustomer_OVR);
        final KDBizPromptBox kdtEntry_recAsstActType_PromptBox = new KDBizPromptBox();
        kdtEntry_recAsstActType_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.auxacct.app.F7AsstActTypeQuery");
        kdtEntry_recAsstActType_PromptBox.setVisible(true);
        kdtEntry_recAsstActType_PromptBox.setEditable(true);
        kdtEntry_recAsstActType_PromptBox.setDisplayFormat("$number$");
        kdtEntry_recAsstActType_PromptBox.setEditFormat("$number$");
        kdtEntry_recAsstActType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_recAsstActType_CellEditor = new KDTDefaultCellEditor(kdtEntry_recAsstActType_PromptBox);
        this.kdtEntry.getColumn("recAsstActType").setEditor(kdtEntry_recAsstActType_CellEditor);
        ObjectValueRender kdtEntry_recAsstActType_OVR = new ObjectValueRender();
        kdtEntry_recAsstActType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("recAsstActType").setRenderer(kdtEntry_recAsstActType_OVR);
        // kdtPlan
		String kdtPlanStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:NumberFormat>%r{yyyy-MM-dd}t</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>%r{0.0000000000}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>%r{0.0000000000}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /><c:NumberFormat>%r{0.0000000000}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>%r{0.0000000000}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /><c:NumberFormat>%r{0.0000000000}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /><c:NumberFormat>%r{0.0000000000}f</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"recPayDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"recPayAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"recPayAmountLoc\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"verifyAmt\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"verifyAmtLoc\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"unVerifyAmt\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"unVerifyAmtLoc\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"remark\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{recPayDate}</t:Cell><t:Cell>$Resource{recPayAmount}</t:Cell><t:Cell>$Resource{recPayAmountLoc}</t:Cell><t:Cell>$Resource{verifyAmt}</t:Cell><t:Cell>$Resource{verifyAmtLoc}</t:Cell><t:Cell>$Resource{unVerifyAmt}</t:Cell><t:Cell>$Resource{unVerifyAmtLoc}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtPlan.setFormatXml(resHelper.translateString("kdtPlan",kdtPlanStrXML));
        this.kdtPlan.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtPlan_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtPlan.putBindContents("editData",new String[] {"id","recievePayDate","recievePayAmount","recievePayAmountLocal","verifyAmount","verifyAmountLocal","","","remark"});


        this.kdtPlan.checkParsed();
        // prmtAdminOrgUnit		
        this.prmtAdminOrgUnit.setDisplayFormat("$name$");		
        this.prmtAdminOrgUnit.setEditFormat("$number$");		
        this.prmtAdminOrgUnit.setCommitFormat("$number$");		
        this.prmtAdminOrgUnit.setEditable(true);		
        this.prmtAdminOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");
        // kdComSourceBillType		
        this.kdComSourceBillType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fi.ap.VerificateBillTypeEnum").toArray());		
        this.kdComSourceBillType.setReadOnly(true);		
        this.kdComSourceBillType.setEnabled(false);
        // prmtCashDiscount		
        this.prmtCashDiscount.setQueryInfo("com.kingdee.eas.fi.arap.app.ArApCashDiscountQuery");		
        this.prmtCashDiscount.setDisplayFormat("$description$");		
        this.prmtCashDiscount.setEditFormat("$number$");		
        this.prmtCashDiscount.setCommitFormat("$number$");
        // kdComBizDate
        // txtVerifyAmtLocal		
        this.txtVerifyAmtLocal.setDataType(1);		
        this.txtVerifyAmtLocal.setEditable(false);		
        this.txtVerifyAmtLocal.setPrecision(10);
        // txtUnVerifyAmtLocal		
        this.txtUnVerifyAmtLocal.setDataType(1);		
        this.txtUnVerifyAmtLocal.setEditable(false);		
        this.txtUnVerifyAmtLocal.setPrecision(10);
        // prmtBizType		
        this.prmtBizType.setQueryInfo("com.kingdee.eas.basedata.scm.common.app.F7BizTypeHasBillTypeQuery");		
        this.prmtBizType.setDisplayFormat("$name$");		
        this.prmtBizType.setEditFormat("$number$");		
        this.prmtBizType.setCommitFormat("$number$");		
        this.prmtBizType.setRequired(true);		
        this.prmtBizType.setEditable(true);
        // prmtSaleGroup		
        this.prmtSaleGroup.setQueryInfo("com.kingdee.eas.basedata.scm.sd.sale.app.F7SaleGroupQuery");		
        this.prmtSaleGroup.setDisplayFormat("$name$");		
        this.prmtSaleGroup.setEditFormat("$number$");		
        this.prmtSaleGroup.setCommitFormat("$number$");		
        this.prmtSaleGroup.setEditable(true);
        // txtLastExhangeRate		
        this.txtLastExhangeRate.setDataType(1);		
        this.txtLastExhangeRate.setEditable(false);
        // prmtPaymentType		
        this.prmtPaymentType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PaymentTypeQuery");		
        this.prmtPaymentType.setDisplayFormat("$name$");		
        this.prmtPaymentType.setEditFormat("$number$");		
        this.prmtPaymentType.setCommitFormat("$number$");		
        this.prmtPaymentType.setEditable(true);		
        this.prmtPaymentType.setRequired(true);
        // prmtPerson		
        this.prmtPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtPerson.setEditable(true);
        // txtTotalEntryAmount		
        this.txtTotalEntryAmount.setDataType(1);		
        this.txtTotalEntryAmount.setEditable(false);
        // txtTotalEntryTaxAmount		
        this.txtTotalEntryTaxAmount.setDataType(1);		
        this.txtTotalEntryTaxAmount.setEditable(false);
        // txtTotalQty		
        this.txtTotalQty.setDataType(1);		
        this.txtTotalQty.setEditable(false);
        // prmtPayCondition		
        this.prmtPayCondition.setDisplayFormat("$name$");		
        this.prmtPayCondition.setEditFormat("$number$");		
        this.prmtPayCondition.setCommitFormat("$number$");		
        this.prmtPayCondition.setToolTipText(resHelper.getString("prmtPayCondition.toolTipText"));		
        this.prmtPayCondition.setDefaultF7UIName("收款条件");		
        this.prmtPayCondition.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7ReceiveConditionQuery");		
        this.prmtPayCondition.setEditable(true);
        // prmtUpdateUser		
        this.prmtUpdateUser.setCommitFormat("$number$");		
        this.prmtUpdateUser.setEditFormat("$number$");		
        this.prmtUpdateUser.setDisplayFormat("$name$");		
        this.prmtUpdateUser.setEditable(true);		
        this.prmtUpdateUser.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtUpdateUser.setReadOnly(true);		
        this.prmtUpdateUser.setEnabled(false);
        // pkUpdateDate		
        this.pkUpdateDate.setEditable(false);		
        this.pkUpdateDate.setEnabled(false);
        // prmtCostCenterUnit		
        this.prmtCostCenterUnit.setDisplayFormat("$name$");		
        this.prmtCostCenterUnit.setEditFormat("$number$");		
        this.prmtCostCenterUnit.setCommitFormat("$number$");		
        this.prmtCostCenterUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterItemQuery");		
        this.prmtCostCenterUnit.setEditable(true);
        // kDFormattedTextField1		
        this.kDFormattedTextField1.setEnabled(false);
        // kDTextField1		
        this.kDTextField1.setEnabled(false);
        // separator6		
        this.separator6.setOrientation(1);
        // btnRemainAmountQuery
        this.btnRemainAmountQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemainAmountQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemainAmountQuery.setText(resHelper.getString("btnRemainAmountQuery.text"));		
        this.btnRemainAmountQuery.setToolTipText(resHelper.getString("btnRemainAmountQuery.toolTipText"));
        // btnFetchPricePolicy
        this.btnFetchPricePolicy.setAction((IItemAction)ActionProxyFactory.getProxy(actionFetchPricePolicy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFetchPricePolicy.setText(resHelper.getString("btnFetchPricePolicy.text"));		
        this.btnFetchPricePolicy.setToolTipText(resHelper.getString("btnFetchPricePolicy.toolTipText"));
        // separator8		
        this.separator8.setOrientation(1);		
        this.separator8.setEnabled(false);		
        this.separator8.setVisible(false);
        // btnEditVouchers
        this.btnEditVouchers.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditVouchers, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditVouchers.setText(resHelper.getString("btnEditVouchers.text"));		
        this.btnEditVouchers.setToolTipText(resHelper.getString("btnEditVouchers.toolTipText"));
        // btnScmVerifyQuery
        this.btnScmVerifyQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewWriteOffRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnScmVerifyQuery.setText(resHelper.getString("btnScmVerifyQuery.text"));
        // menuItemScmVerifyQuery
        this.menuItemScmVerifyQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewWriteOffRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemScmVerifyQuery.setText(resHelper.getString("menuItemScmVerifyQuery.text"));
        // menuItemRemainAmountQuery
        this.menuItemRemainAmountQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemainAmountQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRemainAmountQuery.setText(resHelper.getString("menuItemRemainAmountQuery.text"));
        // menuItemFetchPricePolicy
        this.menuItemFetchPricePolicy.setAction((IItemAction)ActionProxyFactory.getProxy(actionFetchPricePolicy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemFetchPricePolicy.setText(resHelper.getString("menuItemFetchPricePolicy.text"));
        // menuItemEditVouchers
        this.menuItemEditVouchers.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditVouchers, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemEditVouchers.setText(resHelper.getString("menuItemEditVouchers.text"));		
        this.menuItemEditVouchers.setToolTipText(resHelper.getString("menuItemEditVouchers.toolTipText"));
        this.menuItemEditVouchers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    menuItemEditVouchers_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
		//Register control's property binding
		registerBindings();
		registerUIState();
		setUseAgent(true);


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
        kDLabelContainer1.setBounds(new Rectangle(374, 10, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(374, 10, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(10, 582, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(10, 582, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(733, 559, 270, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(733, 559, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer7.setBounds(new Rectangle(10, 559, 270, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(10, 559, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(12, 10, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(12, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcurrency.setBounds(new Rectangle(12, 124, 270, 19));
        this.add(contcurrency, new KDLayout.Constraints(12, 124, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsaleOrg.setBounds(new Rectangle(12, 101, 270, 19));
        this.add(contsaleOrg, new KDLayout.Constraints(12, 101, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttotalAmounts.setBounds(new Rectangle(12, 170, 270, 19));
        this.add(conttotalAmounts, new KDLayout.Constraints(12, 170, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contabstractName.setBounds(new Rectangle(374, 193, 270, 19));
        this.add(contabstractName, new KDLayout.Constraints(374, 193, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(728, 10, 270, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(728, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBillType.setBounds(new Rectangle(12, 32, 270, 19));
        this.add(contBillType, new KDLayout.Constraints(12, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer10.setBounds(new Rectangle(374, 147, 270, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(374, 147, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer12.setBounds(new Rectangle(374, 124, 270, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(374, 124, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer13.setBounds(new Rectangle(12, 78, 270, 19));
        this.add(kDLabelContainer13, new KDLayout.Constraints(12, 78, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer14.setBounds(new Rectangle(374, 78, 270, 19));
        this.add(kDLabelContainer14, new KDLayout.Constraints(374, 78, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer15.setBounds(new Rectangle(12, 193, 270, 19));
        this.add(kDLabelContainer15, new KDLayout.Constraints(12, 193, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer16.setBounds(new Rectangle(653, 64, 67, 19));
        this.add(kDLabelContainer16, new KDLayout.Constraints(653, 64, 67, 19, 0));
        kDLabelContainer17.setBounds(new Rectangle(733, 582, 270, 19));
        this.add(kDLabelContainer17, new KDLayout.Constraints(733, 582, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer18.setBounds(new Rectangle(10, 605, 270, 19));
        this.add(kDLabelContainer18, new KDLayout.Constraints(10, 605, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer11.setBounds(new Rectangle(728, 32, 270, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(728, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer19.setBounds(new Rectangle(651, 91, 74, 19));
        this.add(kDLabelContainer19, new KDLayout.Constraints(651, 91, 74, 19, 0));
        txtAccountCussentId.setBounds(new Rectangle(657, 11, 46, 19));
        this.add(txtAccountCussentId, new KDLayout.Constraints(657, 11, 46, 19, 0));
        kDTabbedPane1.setBounds(new Rectangle(10, 244, 993, 312));
        this.add(kDTabbedPane1, new KDLayout.Constraints(10, 244, 993, 312, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer20.setBounds(new Rectangle(728, 78, 270, 19));
        this.add(kDLabelContainer20, new KDLayout.Constraints(728, 78, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer21.setBounds(new Rectangle(374, 32, 270, 19));
        this.add(kDLabelContainer21, new KDLayout.Constraints(374, 32, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer22.setBounds(new Rectangle(12, 147, 270, 19));
        this.add(kDLabelContainer22, new KDLayout.Constraints(12, 147, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer23.setBounds(new Rectangle(728, 124, 270, 19));
        this.add(kDLabelContainer23, new KDLayout.Constraints(728, 124, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer24.setBounds(new Rectangle(651, 118, 68, 19));
        this.add(kDLabelContainer24, new KDLayout.Constraints(651, 118, 68, 19, 0));
        kDLabelContainer25.setBounds(new Rectangle(649, 38, 74, 19));
        this.add(kDLabelContainer25, new KDLayout.Constraints(649, 38, 74, 19, 0));
        checkBoxIsTransBill.setBounds(new Rectangle(825, 149, 106, 19));
        this.add(checkBoxIsTransBill, new KDLayout.Constraints(825, 149, 106, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer26.setBounds(new Rectangle(12, 55, 270, 19));
        this.add(kDLabelContainer26, new KDLayout.Constraints(12, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSaleGroup.setBounds(new Rectangle(374, 101, 270, 19));
        this.add(contSaleGroup, new KDLayout.Constraints(374, 101, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastExhangeRate.setBounds(new Rectangle(652, 181, 72, 19));
        this.add(contLastExhangeRate, new KDLayout.Constraints(652, 181, 72, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer27.setBounds(new Rectangle(374, 55, 270, 19));
        this.add(kDLabelContainer27, new KDLayout.Constraints(374, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPerson.setBounds(new Rectangle(728, 101, 270, 19));
        this.add(contPerson, new KDLayout.Constraints(728, 101, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer9.setBounds(new Rectangle(374, 170, 270, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(374, 170, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer28.setBounds(new Rectangle(728, 170, 270, 19));
        this.add(kDLabelContainer28, new KDLayout.Constraints(728, 170, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        cbxIsRed.setBounds(new Rectangle(652, 145, 70, 19));
        this.add(cbxIsRed, new KDLayout.Constraints(652, 145, 70, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        cbIsInTax.setBounds(new Rectangle(825, 193, 64, 19));
        this.add(cbIsInTax, new KDLayout.Constraints(825, 193, 64, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        cbIsPriceWithoutTax.setBounds(new Rectangle(728, 193, 71, 19));
        this.add(cbIsPriceWithoutTax, new KDLayout.Constraints(728, 193, 71, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer29.setBounds(new Rectangle(295, 163, 69, 19));
        this.add(kDLabelContainer29, new KDLayout.Constraints(295, 163, 69, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cbIsAllowanceBill.setBounds(new Rectangle(728, 149, 156, 19));
        this.add(cbIsAllowanceBill, new KDLayout.Constraints(728, 149, 156, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer30.setBounds(new Rectangle(728, 55, 270, 19));
        this.add(kDLabelContainer30, new KDLayout.Constraints(728, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        cbIsBizBill.setBounds(new Rectangle(909, 193, 89, 19));
        this.add(cbIsBizBill, new KDLayout.Constraints(909, 193, 89, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contUpdateUser.setBounds(new Rectangle(371, 559, 270, 19));
        this.add(contUpdateUser, new KDLayout.Constraints(371, 559, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contUpdateDate.setBounds(new Rectangle(371, 582, 270, 19));
        this.add(contUpdateDate, new KDLayout.Constraints(371, 582, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        costCenter.setBounds(new Rectangle(12, 216, 270, 19));
        this.add(costCenter, new KDLayout.Constraints(12, 216, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(728, 216, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(728, 216, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer8.setBounds(new Rectangle(374, 216, 270, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(374, 216, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(dateCreateTime);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(bizPromptAuditor);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(bizPromptCreator);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prmtCompanyOrgUnit);
        //contcurrency
        contcurrency.setBoundEditor(prmtCurrency);
        //contsaleOrg
        contsaleOrg.setBoundEditor(prmtSaleOrgUnit);
        //conttotalAmounts
        conttotalAmounts.setBoundEditor(txtTotalAmount);
        //contabstractName
        contabstractName.setBoundEditor(txtAbstractName);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(kdComBillDate);
        //contBillType
        contBillType.setBoundEditor(kdComBillType);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(prmtSettleType);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtExchangeRate);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(kdComAsstActType);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(prmtAccountCussent);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(txtUnVerifyAmt);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(txtVerifyAmt);
        //kDLabelContainer17
        kDLabelContainer17.setBoundEditor(kdAuditDate);
        //kDLabelContainer18
        kDLabelContainer18.setBoundEditor(prmtAccountant);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(comboBillStatus);
        //kDLabelContainer19
        kDLabelContainer19.setBoundEditor(txtTotalAmtLocal);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 992, 279));        kdtEntry.setBounds(new Rectangle(0, 0, 1000, 300));
        kDPanel1.add(kdtEntry, new KDLayout.Constraints(0, 0, 1000, 300, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0, 0, 992, 279));        kdtPlan.setBounds(new Rectangle(0, 0, 1000, 300));
        kDPanel2.add(kdtPlan, new KDLayout.Constraints(0, 0, 1000, 300, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDLabelContainer20
        kDLabelContainer20.setBoundEditor(prmtAdminOrgUnit);
        //kDLabelContainer21
        kDLabelContainer21.setBoundEditor(kdComSourceBillType);
        //kDLabelContainer22
        kDLabelContainer22.setBoundEditor(prmtCashDiscount);
        //kDLabelContainer23
        kDLabelContainer23.setBoundEditor(kdComBizDate);
        //kDLabelContainer24
        kDLabelContainer24.setBoundEditor(txtVerifyAmtLocal);
        //kDLabelContainer25
        kDLabelContainer25.setBoundEditor(txtUnVerifyAmtLocal);
        //kDLabelContainer26
        kDLabelContainer26.setBoundEditor(prmtBizType);
        //contSaleGroup
        contSaleGroup.setBoundEditor(prmtSaleGroup);
        //contLastExhangeRate
        contLastExhangeRate.setBoundEditor(txtLastExhangeRate);
        //kDLabelContainer27
        kDLabelContainer27.setBoundEditor(prmtPaymentType);
        //contPerson
        contPerson.setBoundEditor(prmtPerson);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtTotalEntryAmount);
        //kDLabelContainer28
        kDLabelContainer28.setBoundEditor(txtTotalEntryTaxAmount);
        //kDLabelContainer29
        kDLabelContainer29.setBoundEditor(txtTotalQty);
        //kDLabelContainer30
        kDLabelContainer30.setBoundEditor(prmtPayCondition);
        //contUpdateUser
        contUpdateUser.setBoundEditor(prmtUpdateUser);
        //contUpdateDate
        contUpdateDate.setBoundEditor(pkUpdateDate);
        //costCenter
        costCenter.setBoundEditor(prmtCostCenterUnit);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(kDFormattedTextField1);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(kDTextField1);

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
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        menuFile.add(menuItemSendMail);
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
        menuEdit.add(separator2);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(menuTranslateOption);
        //menuTranslateOption
        menuTranslateOption.add(quantityToAssistQty);
        menuTranslateOption.add(assistQtyToQuantity);
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
        menuView.add(kDSeparator6);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        menuView.add(menuItemScmVerifyQuery);
        menuView.add(menuItemRemainAmountQuery);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemQuickInput);
        menuBiz.add(menuItemFetchPricePolicy);
        menuBiz.add(menuItemESD);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemAntiAudit);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemEditVouchers);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemBuildDebitAdjust);
        menuBiz.add(menuItemVerification);
        menuBiz.add(menuItemReversedBizBill);
        menuBiz.add(menuItemUnReversedBizBill);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        menuTool.add(menuItemFx);
        menuTool.add(menuItemCancelFx);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemWorkFlowList);
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
        menuHelp.add(menuItemAbout);
        menuHelp.add(kDSeparatorProduct);

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
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnAntiAudit);
        this.toolBar.add(btnVerification);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
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
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnBuildDebitAdjust);
        this.toolBar.add(btnReversedBizBill);
        this.toolBar.add(btnUnReversedBizBill);
        this.toolBar.add(separator6);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnFx);
        this.toolBar.add(btnCancelFx);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnRemainAmountQuery);
        this.toolBar.add(btnQuickInput);
        this.toolBar.add(btnFetchPricePolicy);
        this.toolBar.add(btnESD);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separator8);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnEditVouchers);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnScmVerifyQuery);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("asstActID", String.class, this.txtAccountCussentId, "text");
		dataBinder.registerBinding("isTransBill", boolean.class, this.checkBoxIsTransBill, "selected");
		dataBinder.registerBinding("redBlueType", boolean.class, this.cbxIsRed, "selected");
		dataBinder.registerBinding("isInTax", boolean.class, this.cbIsInTax, "selected");
		dataBinder.registerBinding("isPriceWithoutTax", boolean.class, this.cbIsPriceWithoutTax, "selected");
		dataBinder.registerBinding("isAllowanceBill", boolean.class, this.cbIsAllowanceBill, "selected");
		dataBinder.registerBinding("isBizBill", boolean.class, this.cbIsBizBill, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.dateCreateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptAuditor, "data");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptCreator, "data");
		dataBinder.registerBinding("company", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtCompanyOrgUnit, "data");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtCurrency, "data");
		dataBinder.registerBinding("saleOrg", com.kingdee.eas.basedata.org.SaleOrgUnitInfo.class, this.prmtSaleOrgUnit, "data");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtTotalAmount, "value");
		dataBinder.registerBinding("abstractName", String.class, this.txtAbstractName, "text");
		dataBinder.registerBinding("billDate", java.util.Date.class, this.kdComBillDate, "value");
		dataBinder.registerBinding("billType", com.kingdee.eas.fi.ar.OtherBillTypeEnum.class, this.kdComBillType, "selectedItem");
		dataBinder.registerBinding("settleType", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.prmtSettleType, "data");
		dataBinder.registerBinding("exchangeRate", java.math.BigDecimal.class, this.txtExchangeRate, "value");
		dataBinder.registerBinding("asstActType", com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo.class, this.kdComAsstActType, "selectedItem");
		dataBinder.registerBinding("unVerifyAmount", java.math.BigDecimal.class, this.txtUnVerifyAmt, "value");
		dataBinder.registerBinding("verifyAmount", java.math.BigDecimal.class, this.txtVerifyAmt, "value");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.kdAuditDate, "value");
		dataBinder.registerBinding("accountant", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAccountant, "data");
		dataBinder.registerBinding("billStatus", com.kingdee.eas.fi.ar.BillStatusEnum.class, this.comboBillStatus, "selectedItem");
		dataBinder.registerBinding("amountLocal", java.math.BigDecimal.class, this.txtTotalAmtLocal, "value");
		dataBinder.registerBinding("entry.badAmount", java.math.BigDecimal.class, this.kdtEntry, "badAmount.text");
		dataBinder.registerBinding("entry.unVerifyAmount", java.math.BigDecimal.class, this.kdtEntry, "unVerifyAmount.text");
		dataBinder.registerBinding("entry.remark", String.class, this.kdtEntry, "remark.text");
		dataBinder.registerBinding("entry.verifyAmountLocal", java.math.BigDecimal.class, this.kdtEntry, "verifyAmountLocal.text");
		dataBinder.registerBinding("entry.badAmountLocal", java.math.BigDecimal.class, this.kdtEntry, "badAmountLocal.text");
		dataBinder.registerBinding("entry.unVerifyAmountLocal", java.math.BigDecimal.class, this.kdtEntry, "unVerifyAmountLocal.text");
		dataBinder.registerBinding("entry", com.kingdee.eas.fi.ar.OtherBillentryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entry.material", com.kingdee.eas.basedata.master.material.MaterialInfo.class, this.kdtEntry, "materialNumber.text");
		dataBinder.registerBinding("entry.expenseItem", com.kingdee.eas.basedata.scm.common.ExpenseItemInfo.class, this.kdtEntry, "expenseItemNumber.text");
		dataBinder.registerBinding("entry.quantity", java.math.BigDecimal.class, this.kdtEntry, "quantity.text");
		dataBinder.registerBinding("entry.price", java.math.BigDecimal.class, this.kdtEntry, "price.text");
		dataBinder.registerBinding("entry.taxPrice", java.math.BigDecimal.class, this.kdtEntry, "taxPrice.text");
		dataBinder.registerBinding("entry.taxRate", java.math.BigDecimal.class, this.kdtEntry, "taxRate.text");
		dataBinder.registerBinding("entry.taxAmount", java.math.BigDecimal.class, this.kdtEntry, "taxAmount.text");
		dataBinder.registerBinding("entry.measureUnit", com.kingdee.eas.basedata.assistant.MeasureUnitInfo.class, this.kdtEntry, "measureUnit.text");
		dataBinder.registerBinding("entry.discountRate", java.math.BigDecimal.class, this.kdtEntry, "discountRate.text");
		dataBinder.registerBinding("entry.actualPrice", java.math.BigDecimal.class, this.kdtEntry, "actualPrice.text");
		dataBinder.registerBinding("entry.amount", java.math.BigDecimal.class, this.kdtEntry, "amount.text");
		dataBinder.registerBinding("entry.amountLocal", java.math.BigDecimal.class, this.kdtEntry, "amountLocal.text");
		dataBinder.registerBinding("entry.taxAmountLocal", java.math.BigDecimal.class, this.kdtEntry, "taxAmountLocal.text");
		dataBinder.registerBinding("entry.materialName", String.class, this.kdtEntry, "materialName.text");
		dataBinder.registerBinding("entry.material.model", String.class, this.kdtEntry, "specification.text");
		dataBinder.registerBinding("entry.expenseItem.name", String.class, this.kdtEntry, "expenseItemName.text");
		dataBinder.registerBinding("entry.verifyAmount", java.math.BigDecimal.class, this.kdtEntry, "verifyAmount.text");
		dataBinder.registerBinding("entry.assistProperty", com.kingdee.eas.basedata.master.material.AsstAttrValueInfo.class, this.kdtEntry, "assistProperty.text");
		dataBinder.registerBinding("entry.account", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.kdtEntry, "account.text");
		dataBinder.registerBinding("entry.coreBillNumber", String.class, this.kdtEntry, "coreBillNumber.text");
		dataBinder.registerBinding("entry.coreBillEntrySeq", int.class, this.kdtEntry, "coreBillEntrySeq.text");
		dataBinder.registerBinding("entry.coreBillType", com.kingdee.eas.basedata.scm.common.BillTypeInfo.class, this.kdtEntry, "coreBillType.text");
		dataBinder.registerBinding("entry.discountAmount", java.math.BigDecimal.class, this.kdtEntry, "discountAmount.text");
		dataBinder.registerBinding("entry.discountAmountLocal", java.math.BigDecimal.class, this.kdtEntry, "discountAmountLocal.text");
		dataBinder.registerBinding("entry.preparedBadAmount", java.math.BigDecimal.class, this.kdtEntry, "preparedBadAmount.text");
		dataBinder.registerBinding("entry.preparedBadAmountLocal", java.math.BigDecimal.class, this.kdtEntry, "preparedBadAmountLocal.text");
		dataBinder.registerBinding("entry.lockVerifyAmt", java.math.BigDecimal.class, this.kdtEntry, "lockVerifyAmt.text");
		dataBinder.registerBinding("entry.lockUnVerifyAmt", java.math.BigDecimal.class, this.kdtEntry, "lockUnVerifyAmt.text");
		dataBinder.registerBinding("entry.assistUnit", com.kingdee.eas.basedata.assistant.MeasureUnitInfo.class, this.kdtEntry, "assistUnit.text");
		dataBinder.registerBinding("entry.oppAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.kdtEntry, "oppAccount.text");
		dataBinder.registerBinding("entry.assistQty", java.math.BigDecimal.class, this.kdtEntry, "assistQty.text");
		dataBinder.registerBinding("entry.discountType", com.kingdee.eas.basedata.scm.common.DiscountModeEnum.class, this.kdtEntry, "discountType.text");
		dataBinder.registerBinding("entry.baseUnit", com.kingdee.eas.basedata.assistant.MeasureUnitInfo.class, this.kdtEntry, "baseUnit.text");
		dataBinder.registerBinding("entry.baseQty", java.math.BigDecimal.class, this.kdtEntry, "baseQty.text");
		dataBinder.registerBinding("entry.wittenOffBaseQty", java.math.BigDecimal.class, this.kdtEntry, "wittenOffBaseQty.text");
		dataBinder.registerBinding("entry.localWrittenOffAmount", java.math.BigDecimal.class, this.kdtEntry, "localWrittenOffAmount.text");
		dataBinder.registerBinding("entry.unwriteOffBaseQty", java.math.BigDecimal.class, this.kdtEntry, "unwriteOffBaseQty.text");
		dataBinder.registerBinding("entry.localUnwriteOffAmount", java.math.BigDecimal.class, this.kdtEntry, "localUnwriteOffAmount.text");
		dataBinder.registerBinding("entry.realPrice", java.math.BigDecimal.class, this.kdtEntry, "realPrice.text");
		dataBinder.registerBinding("entry.invoicedAmt", java.math.BigDecimal.class, this.kdtEntry, "invoicedAmt.text");
		dataBinder.registerBinding("entry.recievePayAmount", java.math.BigDecimal.class, this.kdtEntry, "recPayAmount.text");
		dataBinder.registerBinding("entry.recievePayAmountLocal", java.math.BigDecimal.class, this.kdtEntry, "recPayAmountLocal.text");
		dataBinder.registerBinding("entry.isPresent", boolean.class, this.kdtEntry, "isPresent.text");
		dataBinder.registerBinding("entry.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntry, "entryId.text");
		dataBinder.registerBinding("entry.rowType", com.kingdee.eas.basedata.scm.common.RowTypeInfo.class, this.kdtEntry, "rowType.text");
		dataBinder.registerBinding("entry.orderCustomer", com.kingdee.eas.basedata.master.cssp.CustomerInfo.class, this.kdtEntry, "orderCustomer.text");
		dataBinder.registerBinding("entry.serviceCustomer", com.kingdee.eas.basedata.master.cssp.CustomerInfo.class, this.kdtEntry, "serviceCustomer.text");
		dataBinder.registerBinding("entry.recAsstActType", com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo.class, this.kdtEntry, "recAsstActType.text");
		dataBinder.registerBinding("entry.contractNum", String.class, this.kdtEntry, "contractNum.text");
		dataBinder.registerBinding("entry.contractEntrySeq", String.class, this.kdtEntry, "contractEntrySeq.text");
		dataBinder.registerBinding("entry.recSendOrgUnit", com.kingdee.eas.basedata.org.StorageOrgUnitInfo.class, this.kdtEntry, "recSendOrgUnit.text");
		dataBinder.registerBinding("entry.project", com.kingdee.eas.mm.project.ProjectInfo.class, this.kdtEntry, "project.text");
		dataBinder.registerBinding("entry.trackNumberzc", com.kingdee.eas.mm.basedata.TrackNumberInfo.class, this.kdtEntry, "trackNumberzc.text");
		dataBinder.registerBinding("entry.lot", String.class, this.kdtEntry, "lot.text");
		dataBinder.registerBinding("entry.isFullWriteOff", boolean.class, this.kdtEntry, "isFullWriteOff.text");
		dataBinder.registerBinding("recievePlan.id", com.kingdee.bos.util.BOSUuid.class, this.kdtPlan, "id.text");
		dataBinder.registerBinding("recievePlan.remark", String.class, this.kdtPlan, "remark.text");
		dataBinder.registerBinding("recievePlan", com.kingdee.eas.fi.ar.OtherBillPlanInfo.class, this.kdtPlan, "userObject");
		dataBinder.registerBinding("recievePlan.verifyAmount", java.math.BigDecimal.class, this.kdtPlan, "verifyAmt.text");
		dataBinder.registerBinding("recievePlan.verifyAmountLocal", java.math.BigDecimal.class, this.kdtPlan, "verifyAmtLoc.text");
		dataBinder.registerBinding("recievePlan.recievePayDate", java.util.Date.class, this.kdtPlan, "recPayDate.text");
		dataBinder.registerBinding("recievePlan.recievePayAmount", java.math.BigDecimal.class, this.kdtPlan, "recPayAmount.text");
		dataBinder.registerBinding("recievePlan.recievePayAmountLocal", java.math.BigDecimal.class, this.kdtPlan, "recPayAmountLoc.text");
		dataBinder.registerBinding("adminOrgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtAdminOrgUnit, "data");
		dataBinder.registerBinding("sourceBillType", com.kingdee.eas.fi.ap.VerificateBillTypeEnum.class, this.kdComSourceBillType, "selectedItem");
		dataBinder.registerBinding("cashDiscount", com.kingdee.eas.basedata.assistant.CashDiscountInfo.class, this.prmtCashDiscount, "data");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.kdComBizDate, "value");
		dataBinder.registerBinding("verifyAmountLocal", java.math.BigDecimal.class, this.txtVerifyAmtLocal, "value");
		dataBinder.registerBinding("unVerifyAmountLocal", java.math.BigDecimal.class, this.txtUnVerifyAmtLocal, "value");
		dataBinder.registerBinding("bizType", com.kingdee.eas.basedata.scm.common.BizTypeInfo.class, this.prmtBizType, "data");
		dataBinder.registerBinding("saleGroup", com.kingdee.eas.basedata.scm.sd.sale.SaleGroupInfo.class, this.prmtSaleGroup, "data");
		dataBinder.registerBinding("lastExhangeRate", java.math.BigDecimal.class, this.txtLastExhangeRate, "value");
		dataBinder.registerBinding("paymentType", com.kingdee.eas.basedata.assistant.PaymentTypeInfo.class, this.prmtPaymentType, "data");
		dataBinder.registerBinding("person", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtPerson, "data");
		dataBinder.registerBinding("totalAmount", java.math.BigDecimal.class, this.txtTotalEntryAmount, "value");
		dataBinder.registerBinding("totalTax", java.math.BigDecimal.class, this.txtTotalEntryTaxAmount, "value");
		dataBinder.registerBinding("payCondition", com.kingdee.eas.basedata.assistant.PayConditionInfo.class, this.prmtPayCondition, "data");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkUpdateDate, "value");
		dataBinder.registerBinding("costCenter", com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo.class, this.prmtCostCenterUnit, "data");
		dataBinder.registerBinding("yhxAmount", java.math.BigDecimal.class, this.kDFormattedTextField1, "value");
		dataBinder.registerBinding("ldNo", String.class, this.kDTextField1, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fi.ar.app.OtherBillEditUIHandler";
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




	
	protected boolean isAgentAddNew() {
		return "ADDNEW".equals(getOprtState());
	}
	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;
	        if(isUseAgent()) {
			  	if (isAgentAddNew()) {
					if (! (dataObject instanceof com.kingdee.bos.framework.agent.IObjectValueAgent)) {
						ov = com.kingdee.eas.fi.ar.OtherBillAgent.copyOvAsNewAgent(dataObject);
					} else if (((com.kingdee.bos.framework.agent.IObjectValueAgent)dataObject).getAgentState() != com.kingdee.bos.framework.agent.AgentState.NEW) {
						((com.kingdee.eas.fi.ar.OtherBillAgent)ov).recursiveSetAgentState(com.kingdee.bos.framework.agent.AgentState.NEW);
			  		}
			  	} else {
		        	if (! (dataObject instanceof com.kingdee.bos.framework.agent.IObjectValueAgent)) {
						ov = com.kingdee.eas.fi.ar.OtherBillAgent.copyOvToAgent(dataObject);
		        	}
	        	}
	        }        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fi.ar.OtherBillInfo)ov;
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("asstActID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isTransBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("redBlueType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isInTax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isPriceWithoutTax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isAllowanceBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isBizBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleOrg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("abstractName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settleType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("asstActType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unVerifyAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("verifyAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accountant", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amountLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.badAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.unVerifyAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.verifyAmountLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.badAmountLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.unVerifyAmountLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.material", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.expenseItem", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.quantity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.taxPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.taxRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.taxAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.measureUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.discountRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.actualPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.amountLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.taxAmountLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.materialName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.material.model", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.expenseItem.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.verifyAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.assistProperty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.account", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.coreBillNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.coreBillEntrySeq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.coreBillType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.discountAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.discountAmountLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.preparedBadAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.preparedBadAmountLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.lockVerifyAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.lockUnVerifyAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.assistUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.oppAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.assistQty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.discountType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.baseUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.baseQty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.wittenOffBaseQty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.localWrittenOffAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.unwriteOffBaseQty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.localUnwriteOffAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.realPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.invoicedAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.recievePayAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.recievePayAmountLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.isPresent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.rowType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.orderCustomer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.serviceCustomer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.recAsstActType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.contractNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.contractEntrySeq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.recSendOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.trackNumberzc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.lot", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.isFullWriteOff", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recievePlan.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recievePlan.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recievePlan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recievePlan.verifyAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recievePlan.verifyAmountLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recievePlan.recievePayDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recievePlan.recievePayAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recievePlan.recievePayAmountLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sourceBillType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cashDiscount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("verifyAmountLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unVerifyAmountLocal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleGroup", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastExhangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paymentType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("person", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalTax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payCondition", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costCenter", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yhxAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ldNo", ValidateHelper.ON_SAVE);    		
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
        }
    }

    /**
     * output kDTabbedPane1_stateChanged method
     */
    protected void kDTabbedPane1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output cbxIsRed_itemStateChanged method
     */
    protected void cbxIsRed_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output cbxIsRed_stateChanged method
     */
    protected void cbxIsRed_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output cbxIsRed_actionPerformed method
     */
    protected void cbxIsRed_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output cbIsInTax_actionPerformed method
     */
    protected void cbIsInTax_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output cbIsPriceWithoutTax_actionPerformed method
     */
    protected void cbIsPriceWithoutTax_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtCompanyOrgUnit_focusLost method
     */
    protected void prmtCompanyOrgUnit_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output kdComAsstActType_itemStateChanged method
     */
    protected void kdComAsstActType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output kdtPlan_editStopped method
     */
    protected void kdtPlan_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output menuItemEditVouchers_actionPerformed method
     */
    protected void menuItemEditVouchers_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("asstActID"));
        sic.add(new SelectorItemInfo("isTransBill"));
        sic.add(new SelectorItemInfo("redBlueType"));
        sic.add(new SelectorItemInfo("isInTax"));
        sic.add(new SelectorItemInfo("isPriceWithoutTax"));
        sic.add(new SelectorItemInfo("isAllowanceBill"));
        sic.add(new SelectorItemInfo("isBizBill"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
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
			sic.add(new SelectorItemInfo("company.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("company.id"));
        	sic.add(new SelectorItemInfo("company.number"));
        	sic.add(new SelectorItemInfo("company.name"));
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("saleOrg.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("saleOrg.id"));
        	sic.add(new SelectorItemInfo("saleOrg.number"));
        	sic.add(new SelectorItemInfo("saleOrg.name"));
		}
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("abstractName"));
        sic.add(new SelectorItemInfo("billDate"));
        sic.add(new SelectorItemInfo("billType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("settleType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("settleType.id"));
        	sic.add(new SelectorItemInfo("settleType.number"));
        	sic.add(new SelectorItemInfo("settleType.name"));
		}
        sic.add(new SelectorItemInfo("exchangeRate"));
        sic.add(new SelectorItemInfo("asstActType"));
        sic.add(new SelectorItemInfo("unVerifyAmount"));
        sic.add(new SelectorItemInfo("verifyAmount"));
        sic.add(new SelectorItemInfo("auditDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("accountant.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("accountant.id"));
        	sic.add(new SelectorItemInfo("accountant.number"));
        	sic.add(new SelectorItemInfo("accountant.name"));
		}
        sic.add(new SelectorItemInfo("billStatus"));
        sic.add(new SelectorItemInfo("amountLocal"));
    	sic.add(new SelectorItemInfo("entry.badAmount"));
    	sic.add(new SelectorItemInfo("entry.unVerifyAmount"));
    	sic.add(new SelectorItemInfo("entry.remark"));
    	sic.add(new SelectorItemInfo("entry.verifyAmountLocal"));
    	sic.add(new SelectorItemInfo("entry.badAmountLocal"));
    	sic.add(new SelectorItemInfo("entry.unVerifyAmountLocal"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.material.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.material.id"));
			sic.add(new SelectorItemInfo("entry.material.name"));
        	sic.add(new SelectorItemInfo("entry.material.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.expenseItem.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.expenseItem.id"));
			sic.add(new SelectorItemInfo("entry.expenseItem.name"));
        	sic.add(new SelectorItemInfo("entry.expenseItem.number"));
		}
    	sic.add(new SelectorItemInfo("entry.quantity"));
    	sic.add(new SelectorItemInfo("entry.price"));
    	sic.add(new SelectorItemInfo("entry.taxPrice"));
    	sic.add(new SelectorItemInfo("entry.taxRate"));
    	sic.add(new SelectorItemInfo("entry.taxAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.measureUnit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.measureUnit.id"));
			sic.add(new SelectorItemInfo("entry.measureUnit.name"));
        	sic.add(new SelectorItemInfo("entry.measureUnit.number"));
		}
    	sic.add(new SelectorItemInfo("entry.discountRate"));
    	sic.add(new SelectorItemInfo("entry.actualPrice"));
    	sic.add(new SelectorItemInfo("entry.amount"));
    	sic.add(new SelectorItemInfo("entry.amountLocal"));
    	sic.add(new SelectorItemInfo("entry.taxAmountLocal"));
    	sic.add(new SelectorItemInfo("entry.materialName"));
    	sic.add(new SelectorItemInfo("entry.material.model"));
    	sic.add(new SelectorItemInfo("entry.verifyAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.assistProperty.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.assistProperty.id"));
			sic.add(new SelectorItemInfo("entry.assistProperty.name"));
        	sic.add(new SelectorItemInfo("entry.assistProperty.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.account.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.account.id"));
			sic.add(new SelectorItemInfo("entry.account.name"));
        	sic.add(new SelectorItemInfo("entry.account.number"));
		}
    	sic.add(new SelectorItemInfo("entry.coreBillNumber"));
    	sic.add(new SelectorItemInfo("entry.coreBillEntrySeq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.coreBillType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.coreBillType.id"));
			sic.add(new SelectorItemInfo("entry.coreBillType.name"));
        	sic.add(new SelectorItemInfo("entry.coreBillType.number"));
		}
    	sic.add(new SelectorItemInfo("entry.discountAmount"));
    	sic.add(new SelectorItemInfo("entry.discountAmountLocal"));
    	sic.add(new SelectorItemInfo("entry.preparedBadAmount"));
    	sic.add(new SelectorItemInfo("entry.preparedBadAmountLocal"));
    	sic.add(new SelectorItemInfo("entry.lockVerifyAmt"));
    	sic.add(new SelectorItemInfo("entry.lockUnVerifyAmt"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.assistUnit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.assistUnit.id"));
			sic.add(new SelectorItemInfo("entry.assistUnit.name"));
        	sic.add(new SelectorItemInfo("entry.assistUnit.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.oppAccount.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.oppAccount.id"));
			sic.add(new SelectorItemInfo("entry.oppAccount.name"));
        	sic.add(new SelectorItemInfo("entry.oppAccount.number"));
		}
    	sic.add(new SelectorItemInfo("entry.assistQty"));
    	sic.add(new SelectorItemInfo("entry.discountType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.baseUnit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.baseUnit.id"));
			sic.add(new SelectorItemInfo("entry.baseUnit.name"));
        	sic.add(new SelectorItemInfo("entry.baseUnit.number"));
		}
    	sic.add(new SelectorItemInfo("entry.baseQty"));
    	sic.add(new SelectorItemInfo("entry.wittenOffBaseQty"));
    	sic.add(new SelectorItemInfo("entry.localWrittenOffAmount"));
    	sic.add(new SelectorItemInfo("entry.unwriteOffBaseQty"));
    	sic.add(new SelectorItemInfo("entry.localUnwriteOffAmount"));
    	sic.add(new SelectorItemInfo("entry.realPrice"));
    	sic.add(new SelectorItemInfo("entry.invoicedAmt"));
    	sic.add(new SelectorItemInfo("entry.recievePayAmount"));
    	sic.add(new SelectorItemInfo("entry.recievePayAmountLocal"));
    	sic.add(new SelectorItemInfo("entry.isPresent"));
    	sic.add(new SelectorItemInfo("entry.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.rowType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.rowType.id"));
			sic.add(new SelectorItemInfo("entry.rowType.name"));
        	sic.add(new SelectorItemInfo("entry.rowType.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.orderCustomer.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.orderCustomer.id"));
			sic.add(new SelectorItemInfo("entry.orderCustomer.name"));
        	sic.add(new SelectorItemInfo("entry.orderCustomer.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.serviceCustomer.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.serviceCustomer.id"));
			sic.add(new SelectorItemInfo("entry.serviceCustomer.name"));
        	sic.add(new SelectorItemInfo("entry.serviceCustomer.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.recAsstActType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.recAsstActType.id"));
			sic.add(new SelectorItemInfo("entry.recAsstActType.name"));
        	sic.add(new SelectorItemInfo("entry.recAsstActType.number"));
		}
    	sic.add(new SelectorItemInfo("entry.contractNum"));
    	sic.add(new SelectorItemInfo("entry.contractEntrySeq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.recSendOrgUnit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.recSendOrgUnit.id"));
			sic.add(new SelectorItemInfo("entry.recSendOrgUnit.name"));
        	sic.add(new SelectorItemInfo("entry.recSendOrgUnit.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.project.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.project.id"));
			sic.add(new SelectorItemInfo("entry.project.name"));
        	sic.add(new SelectorItemInfo("entry.project.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.trackNumberzc.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.trackNumberzc.id"));
			sic.add(new SelectorItemInfo("entry.trackNumberzc.name"));
        	sic.add(new SelectorItemInfo("entry.trackNumberzc.number"));
		}
    	sic.add(new SelectorItemInfo("entry.lot"));
    	sic.add(new SelectorItemInfo("entry.isFullWriteOff"));
    	sic.add(new SelectorItemInfo("recievePlan.id"));
    	sic.add(new SelectorItemInfo("recievePlan.remark"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("recievePlan.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("recievePlan.verifyAmount"));
    	sic.add(new SelectorItemInfo("recievePlan.verifyAmountLocal"));
    	sic.add(new SelectorItemInfo("recievePlan.recievePayDate"));
    	sic.add(new SelectorItemInfo("recievePlan.recievePayAmount"));
    	sic.add(new SelectorItemInfo("recievePlan.recievePayAmountLocal"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("adminOrgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("adminOrgUnit.id"));
        	sic.add(new SelectorItemInfo("adminOrgUnit.number"));
        	sic.add(new SelectorItemInfo("adminOrgUnit.name"));
		}
        sic.add(new SelectorItemInfo("sourceBillType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("cashDiscount.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("cashDiscount.id"));
        	sic.add(new SelectorItemInfo("cashDiscount.number"));
        	sic.add(new SelectorItemInfo("cashDiscount.name"));
        	sic.add(new SelectorItemInfo("cashDiscount.description"));
		}
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("verifyAmountLocal"));
        sic.add(new SelectorItemInfo("unVerifyAmountLocal"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("bizType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("bizType.id"));
        	sic.add(new SelectorItemInfo("bizType.number"));
        	sic.add(new SelectorItemInfo("bizType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("saleGroup.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("saleGroup.id"));
        	sic.add(new SelectorItemInfo("saleGroup.number"));
        	sic.add(new SelectorItemInfo("saleGroup.name"));
		}
        sic.add(new SelectorItemInfo("lastExhangeRate"));
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
			sic.add(new SelectorItemInfo("person.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("person.id"));
        	sic.add(new SelectorItemInfo("person.number"));
        	sic.add(new SelectorItemInfo("person.name"));
		}
        sic.add(new SelectorItemInfo("totalAmount"));
        sic.add(new SelectorItemInfo("totalTax"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payCondition.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("payCondition.id"));
        	sic.add(new SelectorItemInfo("payCondition.number"));
        	sic.add(new SelectorItemInfo("payCondition.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costCenter.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("costCenter.id"));
        	sic.add(new SelectorItemInfo("costCenter.number"));
        	sic.add(new SelectorItemInfo("costCenter.name"));
		}
        sic.add(new SelectorItemInfo("yhxAmount"));
        sic.add(new SelectorItemInfo("ldNo"));
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
     * output actionCopy_actionPerformed method
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
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
     * output actionFetchPricePolicy_actionPerformed method
     */
    public void actionFetchPricePolicy_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemainAmountQuery_actionPerformed method
     */
    public void actionRemainAmountQuery_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditVouchers_actionPerformed method
     */
    public void actionEditVouchers_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewWriteOffRecord_actionPerformed method
     */
    public void actionViewWriteOffRecord_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionCopy(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCopy(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCopy() {
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
	public RequestContext prepareActionFetchPricePolicy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFetchPricePolicy() {
    	return false;
    }
	public RequestContext prepareActionRemainAmountQuery(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemainAmountQuery() {
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

    /**
     * output ActionFetchPricePolicy class
     */     
    protected class ActionFetchPricePolicy extends ItemAction {     
    
        public ActionFetchPricePolicy()
        {
            this(null);
        }

        public ActionFetchPricePolicy(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionFetchPricePolicy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFetchPricePolicy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFetchPricePolicy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillEditUI.this, "ActionFetchPricePolicy", "actionFetchPricePolicy_actionPerformed", e);
        }
    }

    /**
     * output ActionRemainAmountQuery class
     */     
    protected class ActionRemainAmountQuery extends ItemAction {     
    
        public ActionRemainAmountQuery()
        {
            this(null);
        }

        public ActionRemainAmountQuery(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl F9"));
            _tempStr = resHelper.getString("ActionRemainAmountQuery.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemainAmountQuery.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemainAmountQuery.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOtherBillEditUI.this, "ActionRemainAmountQuery", "actionRemainAmountQuery_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractOtherBillEditUI.this, "ActionEditVouchers", "actionEditVouchers_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractOtherBillEditUI.this, "ActionViewWriteOffRecord", "actionViewWriteOffRecord_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fi.ar.client", "OtherBillEditUI");
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
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntry;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
				vo.setString("asstActID","accountCussentId");
		vo.put("amount",new java.math.BigDecimal(0));
		vo.put("exchangeRate",new java.math.BigDecimal(0));
		vo.put("unVerifyAmount",new java.math.BigDecimal(0));
		vo.put("verifyAmount",new java.math.BigDecimal(0));
		vo.put("amountLocal",new java.math.BigDecimal(0));
		vo.put("verifyAmountLocal",new java.math.BigDecimal(0));
		vo.put("unVerifyAmountLocal",new java.math.BigDecimal(0));
		vo.put("lastExhangeRate",new java.math.BigDecimal(0));
		vo.put("totalAmount",new java.math.BigDecimal(0));
		vo.put("totalTax",new java.math.BigDecimal(0));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}