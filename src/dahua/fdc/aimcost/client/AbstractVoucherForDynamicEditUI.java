/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

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
public abstract class AbstractVoucherForDynamicEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractVoucherForDynamicEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contId;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtId;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTimePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTimePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHandler;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHandler;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkHasEffected;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSourceBillId;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSourceBillId;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSourceFunction;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSourceFunction;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkFiVouchered;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBeAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBeAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProduct;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProduct;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdjustEntry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdjustEntry;
    protected com.kingdee.eas.fdc.aimcost.VoucherForDynamicInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractVoucherForDynamicEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractVoucherForDynamicEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionWorkflowList
        String _tempStr = null;
        actionWorkflowList.setEnabled(false);
        actionWorkflowList.setDaemonRun(false);

        _tempStr = resHelper.getString("actionWorkflowList.SHORT_DESCRIPTION");
        actionWorkflowList.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionWorkflowList.LONG_DESCRIPTION");
        actionWorkflowList.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionWorkflowList.NAME");
        actionWorkflowList.putValue(ItemAction.NAME, _tempStr);
         this.actionWorkflowList.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        actionAudit.setEnabled(false);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contId = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDTimePicker();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDTimePicker();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contHandler = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtHandler = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.chkHasEffected = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSourceBillId = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSourceBillId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contSourceFunction = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSourceFunction = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.chkFiVouchered = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contBeAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtBeAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contProduct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProduct = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contAdjustEntry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtAdjustEntry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contId.setName("contId");
        this.txtId.setName("txtId");
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.pkCreateTime.setName("pkCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.contCU.setName("contCU");
        this.prmtCU.setName("prmtCU");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contBizDate.setName("contBizDate");
        this.pkBizDate.setName("pkBizDate");
        this.contHandler.setName("contHandler");
        this.prmtHandler.setName("prmtHandler");
        this.contDescription.setName("contDescription");
        this.txtDescription.setName("txtDescription");
        this.chkHasEffected.setName("chkHasEffected");
        this.contAuditor.setName("contAuditor");
        this.prmtAuditor.setName("prmtAuditor");
        this.contSourceBillId.setName("contSourceBillId");
        this.txtSourceBillId.setName("txtSourceBillId");
        this.contSourceFunction.setName("contSourceFunction");
        this.txtSourceFunction.setName("txtSourceFunction");
        this.contName.setName("contName");
        this.txtName.setName("txtName");
        this.contAmount.setName("contAmount");
        this.txtAmount.setName("txtAmount");
        this.contState.setName("contState");
        this.comboState.setName("comboState");
        this.contOrgUnit.setName("contOrgUnit");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.contAuditTime.setName("contAuditTime");
        this.pkAuditTime.setName("pkAuditTime");
        this.chkFiVouchered.setName("chkFiVouchered");
        this.contBeAccount.setName("contBeAccount");
        this.prmtBeAccount.setName("prmtBeAccount");
        this.contCurProject.setName("contCurProject");
        this.prmtCurProject.setName("prmtCurProject");
        this.contProduct.setName("contProduct");
        this.prmtProduct.setName("prmtProduct");
        this.contAdjustEntry.setName("contAdjustEntry");
        this.prmtAdjustEntry.setName("prmtAdjustEntry");
        // CoreUI
        // contId		
        this.contId.setBoundLabelText(resHelper.getString("contId.boundLabelText"));		
        this.contId.setBoundLabelLength(100);		
        this.contId.setBoundLabelUnderline(true);
        // txtId
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // prmtCreator
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // pkCreateTime
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);
        // prmtLastUpdateUser
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);
        // pkLastUpdateTime
        // contCU		
        this.contCU.setBoundLabelText(resHelper.getString("contCU.boundLabelText"));		
        this.contCU.setBoundLabelLength(100);		
        this.contCU.setBoundLabelUnderline(true);
        // prmtCU
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // pkBizDate
        // contHandler		
        this.contHandler.setBoundLabelText(resHelper.getString("contHandler.boundLabelText"));		
        this.contHandler.setBoundLabelLength(100);		
        this.contHandler.setBoundLabelUnderline(true);
        // prmtHandler
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // txtDescription
        // chkHasEffected		
        this.chkHasEffected.setText(resHelper.getString("chkHasEffected.text"));
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // prmtAuditor
        // contSourceBillId		
        this.contSourceBillId.setBoundLabelText(resHelper.getString("contSourceBillId.boundLabelText"));		
        this.contSourceBillId.setBoundLabelLength(100);		
        this.contSourceBillId.setBoundLabelUnderline(true);
        // txtSourceBillId
        // contSourceFunction		
        this.contSourceFunction.setBoundLabelText(resHelper.getString("contSourceFunction.boundLabelText"));		
        this.contSourceFunction.setBoundLabelLength(100);		
        this.contSourceFunction.setBoundLabelUnderline(true);
        // txtSourceFunction
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // txtName
        // contAmount		
        this.contAmount.setBoundLabelText(resHelper.getString("contAmount.boundLabelText"));		
        this.contAmount.setBoundLabelLength(100);		
        this.contAmount.setBoundLabelUnderline(true);
        // txtAmount
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);
        // comboState		
        this.comboState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);
        // prmtOrgUnit
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // pkAuditTime
        // chkFiVouchered		
        this.chkFiVouchered.setText(resHelper.getString("chkFiVouchered.text"));
        // contBeAccount		
        this.contBeAccount.setBoundLabelText(resHelper.getString("contBeAccount.boundLabelText"));		
        this.contBeAccount.setBoundLabelLength(100);		
        this.contBeAccount.setBoundLabelUnderline(true);
        // prmtBeAccount
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);
        // prmtCurProject
        // contProduct		
        this.contProduct.setBoundLabelText(resHelper.getString("contProduct.boundLabelText"));		
        this.contProduct.setBoundLabelLength(100);		
        this.contProduct.setBoundLabelUnderline(true);
        // prmtProduct
        // contAdjustEntry		
        this.contAdjustEntry.setBoundLabelText(resHelper.getString("contAdjustEntry.boundLabelText"));		
        this.contAdjustEntry.setBoundLabelLength(100);		
        this.contAdjustEntry.setBoundLabelUnderline(true);
        // prmtAdjustEntry
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(null);
        contId.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contId, null);
        contCreator.setBounds(new Rectangle(300, 10, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contCreateTime, null);
        contLastUpdateUser.setBounds(new Rectangle(300, 40, 270, 19));
        this.add(contLastUpdateUser, null);
        contLastUpdateTime.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(contLastUpdateTime, null);
        contCU.setBounds(new Rectangle(300, 70, 270, 19));
        this.add(contCU, null);
        contNumber.setBounds(new Rectangle(10, 100, 270, 19));
        this.add(contNumber, null);
        contBizDate.setBounds(new Rectangle(300, 100, 270, 19));
        this.add(contBizDate, null);
        contHandler.setBounds(new Rectangle(10, 130, 270, 19));
        this.add(contHandler, null);
        contDescription.setBounds(new Rectangle(300, 130, 270, 19));
        this.add(contDescription, null);
        chkHasEffected.setBounds(new Rectangle(10, 160, 140, 19));
        this.add(chkHasEffected, null);
        contAuditor.setBounds(new Rectangle(300, 160, 270, 19));
        this.add(contAuditor, null);
        contSourceBillId.setBounds(new Rectangle(10, 190, 270, 19));
        this.add(contSourceBillId, null);
        contSourceFunction.setBounds(new Rectangle(300, 190, 270, 19));
        this.add(contSourceFunction, null);
        contName.setBounds(new Rectangle(10, 220, 270, 19));
        this.add(contName, null);
        contAmount.setBounds(new Rectangle(300, 220, 270, 19));
        this.add(contAmount, null);
        contState.setBounds(new Rectangle(10, 250, 270, 19));
        this.add(contState, null);
        contOrgUnit.setBounds(new Rectangle(300, 250, 270, 19));
        this.add(contOrgUnit, null);
        contAuditTime.setBounds(new Rectangle(10, 280, 270, 19));
        this.add(contAuditTime, null);
        chkFiVouchered.setBounds(new Rectangle(300, 280, 140, 19));
        this.add(chkFiVouchered, null);
        contBeAccount.setBounds(new Rectangle(10, 310, 270, 19));
        this.add(contBeAccount, null);
        contCurProject.setBounds(new Rectangle(300, 310, 270, 19));
        this.add(contCurProject, null);
        contProduct.setBounds(new Rectangle(10, 340, 270, 19));
        this.add(contProduct, null);
        contAdjustEntry.setBounds(new Rectangle(300, 340, 270, 19));
        this.add(contAdjustEntry, null);
        //contId
        contId.setBoundEditor(txtId);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contCU
        contCU.setBoundEditor(prmtCU);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contHandler
        contHandler.setBoundEditor(prmtHandler);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contSourceBillId
        contSourceBillId.setBoundEditor(txtSourceBillId);
        //contSourceFunction
        contSourceFunction.setBoundEditor(txtSourceFunction);
        //contName
        contName.setBoundEditor(txtName);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contState
        contState.setBoundEditor(comboState);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contBeAccount
        contBeAccount.setBoundEditor(prmtBeAccount);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurProject);
        //contProduct
        contProduct.setBoundEditor(prmtProduct);
        //contAdjustEntry
        contAdjustEntry.setBoundEditor(prmtAdjustEntry);

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
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnEdit);
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
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
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

    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("id", com.kingdee.bos.util.BOSUuid.class, this.txtId, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("handler", com.kingdee.eas.base.permission.UserInfo.class, this.prmtHandler, "data");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("hasEffected", boolean.class, this.chkHasEffected, "selected");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("sourceBillId", String.class, this.txtSourceBillId, "text");
		dataBinder.registerBinding("sourceFunction", String.class, this.txtSourceFunction, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.comboState, "selectedItem");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("fiVouchered", boolean.class, this.chkFiVouchered, "selected");
		dataBinder.registerBinding("beAccount", com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo.class, this.prmtBeAccount, "data");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtCurProject, "data");
		dataBinder.registerBinding("product", com.kingdee.eas.fdc.basedata.ProductTypeInfo.class, this.prmtProduct, "data");
		dataBinder.registerBinding("adjustEntry", com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo.class, this.prmtAdjustEntry, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.aimcost.app.VoucherForDynamicEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.aimcost.VoucherForDynamicInfo)ov;
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
	 * ????????��??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("handler", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hasEffected", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sourceBillId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sourceFunction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fiVouchered", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("beAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("product", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustEntry", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("CU.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("handler.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("hasEffected"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("sourceBillId"));
        sic.add(new SelectorItemInfo("sourceFunction"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("fiVouchered"));
        sic.add(new SelectorItemInfo("beAccount.*"));
        sic.add(new SelectorItemInfo("curProject.*"));
        sic.add(new SelectorItemInfo("product.*"));
        sic.add(new SelectorItemInfo("adjustEntry.*"));
        return sic;
    }        
    	

    /**
     * output actionWorkflowList_actionPerformed method
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "VoucherForDynamicEditUI");
    }




}