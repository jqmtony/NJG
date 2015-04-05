/**
 * output package name
 */
package com.kingdee.eas.port.markesupplier.subill.client;

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
public abstract class AbstractMarketSupplierReviewGatherEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketSupplierReviewGatherEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditDate;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contorgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsavePerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contremake;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTemplate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEvaluationType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkJob;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPartProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSplArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsPass;
    protected com.kingdee.bos.ctrl.swing.KDComboBox State;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneremake;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtremake;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEntry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTemplate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEvaluationType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkJob;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPartProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSplArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSupplierName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplier;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtamount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox IsPass;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntryPs;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntryPs_detailPanel = null;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtorgUnit;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanesavePerson;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtsavePerson;
    protected com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo editData = null;
    protected ActionUnAudit actionUnAudit = null;
    /**
     * output class constructor
     */
    public AbstractMarketSupplierReviewGatherEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketSupplierReviewGatherEditUI.class.getName());
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
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
        this.actionUnAudit.setExtendProperty("canForewarn", "true");
        this.actionUnAudit.setExtendProperty("userDefined", "true");
        this.actionUnAudit.setExtendProperty("isObjectUpdateLock", "false");
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contorgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsavePerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkauditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contremake = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEntry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTemplate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEvaluationType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkJob = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPartProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSplArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIsPass = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.State = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.scrollPaneremake = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtremake = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtEntry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtTemplate = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtEvaluationType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtLinkJob = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkPerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPartProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSplArea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInviteType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSupplierName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtSupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtamount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.IsPass = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtEntryPs = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtorgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.scrollPanesavePerson = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtsavePerson = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contauditDate.setName("contauditDate");
        this.kDPanel1.setName("kDPanel1");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.contorgUnit.setName("contorgUnit");
        this.contsavePerson.setName("contsavePerson");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkauditDate.setName("pkauditDate");
        this.contState.setName("contState");
        this.contremake.setName("contremake");
        this.contEntry.setName("contEntry");
        this.contTemplate.setName("contTemplate");
        this.contEvaluationType.setName("contEvaluationType");
        this.contLinkJob.setName("contLinkJob");
        this.contLinkPhone.setName("contLinkPhone");
        this.contLinkPerson.setName("contLinkPerson");
        this.contPartProject.setName("contPartProject");
        this.contSplArea.setName("contSplArea");
        this.contInviteType.setName("contInviteType");
        this.contSupplierName.setName("contSupplierName");
        this.contSupplier.setName("contSupplier");
        this.contBizDate.setName("contBizDate");
        this.contNumber.setName("contNumber");
        this.contamount.setName("contamount");
        this.contIsPass.setName("contIsPass");
        this.State.setName("State");
        this.scrollPaneremake.setName("scrollPaneremake");
        this.txtremake.setName("txtremake");
        this.prmtEntry.setName("prmtEntry");
        this.prmtTemplate.setName("prmtTemplate");
        this.prmtEvaluationType.setName("prmtEvaluationType");
        this.txtLinkJob.setName("txtLinkJob");
        this.txtLinkPhone.setName("txtLinkPhone");
        this.txtLinkPerson.setName("txtLinkPerson");
        this.txtPartProject.setName("txtPartProject");
        this.txtSplArea.setName("txtSplArea");
        this.txtInviteType.setName("txtInviteType");
        this.txtSupplierName.setName("txtSupplierName");
        this.prmtSupplier.setName("prmtSupplier");
        this.pkBizDate.setName("pkBizDate");
        this.txtNumber.setName("txtNumber");
        this.txtamount.setName("txtamount");
        this.IsPass.setName("IsPass");
        this.kdtEntrys.setName("kdtEntrys");
        this.kdtEntryPs.setName("kdtEntryPs");
        this.prmtorgUnit.setName("prmtorgUnit");
        this.scrollPanesavePerson.setName("scrollPanesavePerson");
        this.txtsavePerson.setName("txtsavePerson");
        // CoreUI		
        this.btnAddLine.setVisible(false);		
        this.btnCopyLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.separator1.setVisible(false);		
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
        this.btnCreateTo.setVisible(true);		
        this.menuItemCreateTo.setVisible(true);		
        this.menuItemCopyLine.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setVisible(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contauditDate		
        this.contauditDate.setBoundLabelText(resHelper.getString("contauditDate.boundLabelText"));		
        this.contauditDate.setBoundLabelLength(100);		
        this.contauditDate.setBoundLabelUnderline(true);		
        this.contauditDate.setVisible(true);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // kDContainer1		
        this.kDContainer1.setEnableActive(false);		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));		
        this.kDContainer2.setEnableActive(false);
        // contorgUnit		
        this.contorgUnit.setBoundLabelText(resHelper.getString("contorgUnit.boundLabelText"));		
        this.contorgUnit.setBoundLabelLength(100);		
        this.contorgUnit.setBoundLabelUnderline(true);		
        this.contorgUnit.setVisible(true);
        // contsavePerson		
        this.contsavePerson.setBoundLabelText(resHelper.getString("contsavePerson.boundLabelText"));		
        this.contsavePerson.setBoundLabelLength(100);		
        this.contsavePerson.setBoundLabelUnderline(true);		
        this.contsavePerson.setVisible(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setTimeEnabled(true);		
        this.kDDateLastUpdateTime.setEnabled(false);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // pkauditDate		
        this.pkauditDate.setRequired(false);		
        this.pkauditDate.setEnabled(false);
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);		
        this.contState.setVisible(true);
        // contremake		
        this.contremake.setBoundLabelText(resHelper.getString("contremake.boundLabelText"));		
        this.contremake.setBoundLabelLength(16);		
        this.contremake.setBoundLabelUnderline(true);		
        this.contremake.setVisible(true);		
        this.contremake.setBoundLabelAlignment(8);
        // contEntry		
        this.contEntry.setBoundLabelText(resHelper.getString("contEntry.boundLabelText"));		
        this.contEntry.setBoundLabelLength(100);		
        this.contEntry.setBoundLabelUnderline(true);		
        this.contEntry.setVisible(true);
        // contTemplate		
        this.contTemplate.setBoundLabelText(resHelper.getString("contTemplate.boundLabelText"));		
        this.contTemplate.setBoundLabelLength(100);		
        this.contTemplate.setBoundLabelUnderline(true);		
        this.contTemplate.setVisible(true);
        // contEvaluationType		
        this.contEvaluationType.setBoundLabelText(resHelper.getString("contEvaluationType.boundLabelText"));		
        this.contEvaluationType.setBoundLabelLength(100);		
        this.contEvaluationType.setBoundLabelUnderline(true);		
        this.contEvaluationType.setVisible(true);
        // contLinkJob		
        this.contLinkJob.setBoundLabelText(resHelper.getString("contLinkJob.boundLabelText"));		
        this.contLinkJob.setBoundLabelLength(100);		
        this.contLinkJob.setBoundLabelUnderline(true);		
        this.contLinkJob.setVisible(true);
        // contLinkPhone		
        this.contLinkPhone.setBoundLabelText(resHelper.getString("contLinkPhone.boundLabelText"));		
        this.contLinkPhone.setBoundLabelLength(100);		
        this.contLinkPhone.setBoundLabelUnderline(true);		
        this.contLinkPhone.setVisible(true);
        // contLinkPerson		
        this.contLinkPerson.setBoundLabelText(resHelper.getString("contLinkPerson.boundLabelText"));		
        this.contLinkPerson.setBoundLabelLength(100);		
        this.contLinkPerson.setBoundLabelUnderline(true);		
        this.contLinkPerson.setVisible(true);
        // contPartProject		
        this.contPartProject.setBoundLabelText(resHelper.getString("contPartProject.boundLabelText"));		
        this.contPartProject.setBoundLabelLength(100);		
        this.contPartProject.setBoundLabelUnderline(true);		
        this.contPartProject.setVisible(true);
        // contSplArea		
        this.contSplArea.setBoundLabelText(resHelper.getString("contSplArea.boundLabelText"));		
        this.contSplArea.setBoundLabelLength(100);		
        this.contSplArea.setBoundLabelUnderline(true);		
        this.contSplArea.setVisible(true);
        // contInviteType		
        this.contInviteType.setBoundLabelText(resHelper.getString("contInviteType.boundLabelText"));		
        this.contInviteType.setBoundLabelLength(100);		
        this.contInviteType.setBoundLabelUnderline(true);		
        this.contInviteType.setVisible(true);
        // contSupplierName		
        this.contSupplierName.setBoundLabelText(resHelper.getString("contSupplierName.boundLabelText"));		
        this.contSupplierName.setBoundLabelLength(100);		
        this.contSupplierName.setBoundLabelUnderline(true);		
        this.contSupplierName.setVisible(true);
        // contSupplier		
        this.contSupplier.setBoundLabelText(resHelper.getString("contSupplier.boundLabelText"));		
        this.contSupplier.setBoundLabelLength(100);		
        this.contSupplier.setBoundLabelUnderline(true);		
        this.contSupplier.setVisible(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contamount		
        this.contamount.setBoundLabelText(resHelper.getString("contamount.boundLabelText"));		
        this.contamount.setBoundLabelLength(100);		
        this.contamount.setBoundLabelUnderline(true);		
        this.contamount.setVisible(true);
        // contIsPass		
        this.contIsPass.setBoundLabelText(resHelper.getString("contIsPass.boundLabelText"));		
        this.contIsPass.setBoundLabelLength(100);		
        this.contIsPass.setBoundLabelUnderline(true);		
        this.contIsPass.setVisible(true);
        // State		
        this.State.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.markesupplier.subase.SupplierState").toArray());		
        this.State.setRequired(false);		
        this.State.setEnabled(false);		
        this.State.setVisible(false);
        // scrollPaneremake
        // txtremake		
        this.txtremake.setRequired(false);		
        this.txtremake.setMaxLength(1000);
        // prmtEntry		
        this.prmtEntry.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtEntry.setEditable(true);		
        this.prmtEntry.setDisplayFormat("$name$");		
        this.prmtEntry.setEditFormat("$number$");		
        this.prmtEntry.setCommitFormat("$number$");		
        this.prmtEntry.setRequired(false);
        this.prmtEntry.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtEntry_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtTemplate		
        this.prmtTemplate.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.MarketSupplierAppraiseTemplateQuery");		
        this.prmtTemplate.setEditable(true);		
        this.prmtTemplate.setDisplayFormat("$name$");		
        this.prmtTemplate.setEditFormat("$number$");		
        this.prmtTemplate.setCommitFormat("$number$");		
        this.prmtTemplate.setRequired(false);
        		prmtTemplate.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.markesupplier.subase.client.MarketSupplierAppraiseTemplateListUI prmtTemplate_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (prmtTemplate_F7ListUI == null) {
					try {
						prmtTemplate_F7ListUI = new com.kingdee.eas.port.markesupplier.subase.client.MarketSupplierAppraiseTemplateListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmtTemplate_F7ListUI));
					prmtTemplate_F7ListUI.setF7Use(true,ctx);
					prmtTemplate.setSelector(prmtTemplate_F7ListUI);
				}
			}
		});
					
        this.prmtTemplate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtTemplate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtEvaluationType		
        this.prmtEvaluationType.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.MarketAccreditationTypeQuery");		
        this.prmtEvaluationType.setEditable(true);		
        this.prmtEvaluationType.setDisplayFormat("$name$");		
        this.prmtEvaluationType.setEditFormat("$number$");		
        this.prmtEvaluationType.setCommitFormat("$number$");		
        this.prmtEvaluationType.setRequired(false);
        this.prmtEvaluationType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtEvaluationType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtLinkJob		
        this.txtLinkJob.setHorizontalAlignment(2);		
        this.txtLinkJob.setMaxLength(100);		
        this.txtLinkJob.setRequired(false);
        // txtLinkPhone		
        this.txtLinkPhone.setHorizontalAlignment(2);		
        this.txtLinkPhone.setMaxLength(100);		
        this.txtLinkPhone.setRequired(false);		
        this.txtLinkPhone.setEnabled(false);
        // txtLinkPerson		
        this.txtLinkPerson.setHorizontalAlignment(2);		
        this.txtLinkPerson.setMaxLength(100);		
        this.txtLinkPerson.setRequired(false);		
        this.txtLinkPerson.setEnabled(false);
        // txtPartProject		
        this.txtPartProject.setHorizontalAlignment(2);		
        this.txtPartProject.setMaxLength(100);		
        this.txtPartProject.setRequired(false);
        // txtSplArea		
        this.txtSplArea.setHorizontalAlignment(2);		
        this.txtSplArea.setMaxLength(100);		
        this.txtSplArea.setRequired(false);		
        this.txtSplArea.setEnabled(false);
        // txtInviteType		
        this.txtInviteType.setHorizontalAlignment(2);		
        this.txtInviteType.setMaxLength(100);		
        this.txtInviteType.setRequired(false);		
        this.txtInviteType.setEnabled(false);
        // txtSupplierName		
        this.txtSupplierName.setHorizontalAlignment(2);		
        this.txtSupplierName.setMaxLength(80);		
        this.txtSupplierName.setRequired(false);		
        this.txtSupplierName.setEnabled(false);
        // prmtSupplier		
        this.prmtSupplier.setQueryInfo("com.kingdee.eas.port.markesupplier.subill.app.MarketSupplierStockQuery");		
        this.prmtSupplier.setEditable(true);		
        this.prmtSupplier.setDisplayFormat("$number$");		
        this.prmtSupplier.setEditFormat("$number$");		
        this.prmtSupplier.setCommitFormat("$number$");		
        this.prmtSupplier.setRequired(false);
        prmtSupplier.addDataChangeListener(new DataChangeListener() {
		public void dataChanged(DataChangeEvent e) {
			try {
				prmtSupplier_Changed();
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        this.prmtSupplier.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSupplier_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkBizDate		
        this.pkBizDate.setEnabled(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // txtamount		
        this.txtamount.setHorizontalAlignment(2);		
        this.txtamount.setDataType(1);		
        this.txtamount.setSupportedEmpty(true);		
        this.txtamount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtamount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtamount.setPrecision(2);		
        this.txtamount.setRequired(false);		
        this.txtamount.setEnabled(false);
        // IsPass		
        this.IsPass.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.markesupplier.subase.IsGradeEnum").toArray());		
        this.IsPass.setRequired(true);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"isHasContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"conName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"manager\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"managerPhone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"Quantity\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"uniy\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"ValueForMoney\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{isHasContract}</t:Cell><t:Cell>$Resource{contract}</t:Cell><t:Cell>$Resource{conName}</t:Cell><t:Cell>$Resource{contractAmount}</t:Cell><t:Cell>$Resource{manager}</t:Cell><t:Cell>$Resource{managerPhone}</t:Cell><t:Cell>$Resource{contractPrice}</t:Cell><t:Cell>$Resource{Quantity}</t:Cell><t:Cell>$Resource{uniy}</t:Cell><t:Cell>$Resource{ValueForMoney}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","isHasContract","contract","conName","contractAmount","manager","managerPhone","contractPrice","Quantity","uniy","ValueForMoney"});


        this.kdtEntrys.checkParsed();
        KDCheckBox kdtEntrys_isHasContract_CheckBox = new KDCheckBox();
        kdtEntrys_isHasContract_CheckBox.setName("kdtEntrys_isHasContract_CheckBox");
        KDTDefaultCellEditor kdtEntrys_isHasContract_CellEditor = new KDTDefaultCellEditor(kdtEntrys_isHasContract_CheckBox);
        this.kdtEntrys.getColumn("isHasContract").setEditor(kdtEntrys_isHasContract_CellEditor);
        KDTextField kdtEntrys_contract_TextField = new KDTextField();
        kdtEntrys_contract_TextField.setName("kdtEntrys_contract_TextField");
        kdtEntrys_contract_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_contract_CellEditor = new KDTDefaultCellEditor(kdtEntrys_contract_TextField);
        this.kdtEntrys.getColumn("contract").setEditor(kdtEntrys_contract_CellEditor);
        final KDBizPromptBox kdtEntrys_conName_PromptBox = new KDBizPromptBox();
        kdtEntrys_conName_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.contract.app.ContractBillQuery");
        kdtEntrys_conName_PromptBox.setVisible(true);
        kdtEntrys_conName_PromptBox.setEditable(true);
        kdtEntrys_conName_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_conName_PromptBox.setEditFormat("$number$");
        kdtEntrys_conName_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_conName_CellEditor = new KDTDefaultCellEditor(kdtEntrys_conName_PromptBox);
        this.kdtEntrys.getColumn("conName").setEditor(kdtEntrys_conName_CellEditor);
        ObjectValueRender kdtEntrys_conName_OVR = new ObjectValueRender();
        kdtEntrys_conName_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("conName").setRenderer(kdtEntrys_conName_OVR);
        KDFormattedTextField kdtEntrys_contractAmount_TextField = new KDFormattedTextField();
        kdtEntrys_contractAmount_TextField.setName("kdtEntrys_contractAmount_TextField");
        kdtEntrys_contractAmount_TextField.setVisible(true);
        kdtEntrys_contractAmount_TextField.setEditable(true);
        kdtEntrys_contractAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_contractAmount_TextField.setDataType(1);
        	kdtEntrys_contractAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_contractAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_contractAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_contractAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_contractAmount_TextField);
        this.kdtEntrys.getColumn("contractAmount").setEditor(kdtEntrys_contractAmount_CellEditor);
        KDTextField kdtEntrys_manager_TextField = new KDTextField();
        kdtEntrys_manager_TextField.setName("kdtEntrys_manager_TextField");
        kdtEntrys_manager_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_manager_CellEditor = new KDTDefaultCellEditor(kdtEntrys_manager_TextField);
        this.kdtEntrys.getColumn("manager").setEditor(kdtEntrys_manager_CellEditor);
        KDTextField kdtEntrys_managerPhone_TextField = new KDTextField();
        kdtEntrys_managerPhone_TextField.setName("kdtEntrys_managerPhone_TextField");
        kdtEntrys_managerPhone_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_managerPhone_CellEditor = new KDTDefaultCellEditor(kdtEntrys_managerPhone_TextField);
        this.kdtEntrys.getColumn("managerPhone").setEditor(kdtEntrys_managerPhone_CellEditor);
        KDFormattedTextField kdtEntrys_contractPrice_TextField = new KDFormattedTextField();
        kdtEntrys_contractPrice_TextField.setName("kdtEntrys_contractPrice_TextField");
        kdtEntrys_contractPrice_TextField.setVisible(true);
        kdtEntrys_contractPrice_TextField.setEditable(true);
        kdtEntrys_contractPrice_TextField.setHorizontalAlignment(2);
        kdtEntrys_contractPrice_TextField.setDataType(1);
        	kdtEntrys_contractPrice_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_contractPrice_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_contractPrice_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_contractPrice_CellEditor = new KDTDefaultCellEditor(kdtEntrys_contractPrice_TextField);
        this.kdtEntrys.getColumn("contractPrice").setEditor(kdtEntrys_contractPrice_CellEditor);
        KDFormattedTextField kdtEntrys_Quantity_TextField = new KDFormattedTextField();
        kdtEntrys_Quantity_TextField.setName("kdtEntrys_Quantity_TextField");
        kdtEntrys_Quantity_TextField.setVisible(true);
        kdtEntrys_Quantity_TextField.setEditable(true);
        kdtEntrys_Quantity_TextField.setHorizontalAlignment(2);
        kdtEntrys_Quantity_TextField.setDataType(1);
        	kdtEntrys_Quantity_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_Quantity_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_Quantity_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_Quantity_CellEditor = new KDTDefaultCellEditor(kdtEntrys_Quantity_TextField);
        this.kdtEntrys.getColumn("Quantity").setEditor(kdtEntrys_Quantity_CellEditor);
        final KDBizPromptBox kdtEntrys_uniy_PromptBox = new KDBizPromptBox();
        kdtEntrys_uniy_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
        kdtEntrys_uniy_PromptBox.setVisible(true);
        kdtEntrys_uniy_PromptBox.setEditable(true);
        kdtEntrys_uniy_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_uniy_PromptBox.setEditFormat("$number$");
        kdtEntrys_uniy_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_uniy_CellEditor = new KDTDefaultCellEditor(kdtEntrys_uniy_PromptBox);
        this.kdtEntrys.getColumn("uniy").setEditor(kdtEntrys_uniy_CellEditor);
        ObjectValueRender kdtEntrys_uniy_OVR = new ObjectValueRender();
        kdtEntrys_uniy_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("uniy").setRenderer(kdtEntrys_uniy_OVR);
        KDTextField kdtEntrys_ValueForMoney_TextField = new KDTextField();
        kdtEntrys_ValueForMoney_TextField.setName("kdtEntrys_ValueForMoney_TextField");
        kdtEntrys_ValueForMoney_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_ValueForMoney_CellEditor = new KDTDefaultCellEditor(kdtEntrys_ValueForMoney_TextField);
        this.kdtEntrys.getColumn("ValueForMoney").setEditor(kdtEntrys_ValueForMoney_CellEditor);
        // kdtEntryPs
		String kdtEntryPsStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"guideName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"guideType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fullNum\" t:width=\"280\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"weight\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"remark\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isPass\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"score\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{guideName}</t:Cell><t:Cell>$Resource{guideType}</t:Cell><t:Cell>$Resource{fullNum}</t:Cell><t:Cell>$Resource{weight}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{isPass}</t:Cell><t:Cell>$Resource{score}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntryPs.setFormatXml(resHelper.translateString("kdtEntryPs",kdtEntryPsStrXML));
        this.kdtEntryPs.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntryPs_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntryPs.putBindContents("editData",new String[] {"seq","guideName","guideType","fullNum","weight","remark","isPass","score"});


        this.kdtEntryPs.checkParsed();
        final KDBizPromptBox kdtEntryPs_guideName_PromptBox = new KDBizPromptBox();
        kdtEntryPs_guideName_PromptBox.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.MarketSplAuditIndexQuery");
        kdtEntryPs_guideName_PromptBox.setVisible(true);
        kdtEntryPs_guideName_PromptBox.setEditable(true);
        kdtEntryPs_guideName_PromptBox.setDisplayFormat("$number$");
        kdtEntryPs_guideName_PromptBox.setEditFormat("$number$");
        kdtEntryPs_guideName_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntryPs_guideName_CellEditor = new KDTDefaultCellEditor(kdtEntryPs_guideName_PromptBox);
        this.kdtEntryPs.getColumn("guideName").setEditor(kdtEntryPs_guideName_CellEditor);
        ObjectValueRender kdtEntryPs_guideName_OVR = new ObjectValueRender();
        kdtEntryPs_guideName_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntryPs.getColumn("guideName").setRenderer(kdtEntryPs_guideName_OVR);
        			kdtEntryPs_guideName_PromptBox.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.markesupplier.subase.client.MarketSplAuditIndexListUI kdtEntryPs_guideName_PromptBox_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (kdtEntryPs_guideName_PromptBox_F7ListUI == null) {
					try {
						kdtEntryPs_guideName_PromptBox_F7ListUI = new com.kingdee.eas.port.markesupplier.subase.client.MarketSplAuditIndexListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(kdtEntryPs_guideName_PromptBox_F7ListUI));
					kdtEntryPs_guideName_PromptBox_F7ListUI.setF7Use(true,ctx);
					kdtEntryPs_guideName_PromptBox.setSelector(kdtEntryPs_guideName_PromptBox_F7ListUI);
				}
			}
		});
					
        KDTextField kdtEntryPs_guideType_TextField = new KDTextField();
        kdtEntryPs_guideType_TextField.setName("kdtEntryPs_guideType_TextField");
        kdtEntryPs_guideType_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryPs_guideType_CellEditor = new KDTDefaultCellEditor(kdtEntryPs_guideType_TextField);
        this.kdtEntryPs.getColumn("guideType").setEditor(kdtEntryPs_guideType_CellEditor);
        KDTextArea kdtEntryPs_fullNum_TextArea = new KDTextArea();
        kdtEntryPs_fullNum_TextArea.setName("kdtEntryPs_fullNum_TextArea");
        kdtEntryPs_fullNum_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtEntryPs_fullNum_CellEditor = new KDTDefaultCellEditor(kdtEntryPs_fullNum_TextArea);
        this.kdtEntryPs.getColumn("fullNum").setEditor(kdtEntryPs_fullNum_CellEditor);
        KDFormattedTextField kdtEntryPs_weight_TextField = new KDFormattedTextField();
        kdtEntryPs_weight_TextField.setName("kdtEntryPs_weight_TextField");
        kdtEntryPs_weight_TextField.setVisible(true);
        kdtEntryPs_weight_TextField.setEditable(true);
        kdtEntryPs_weight_TextField.setHorizontalAlignment(2);
        kdtEntryPs_weight_TextField.setDataType(1);
        	kdtEntryPs_weight_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntryPs_weight_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntryPs_weight_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntryPs_weight_CellEditor = new KDTDefaultCellEditor(kdtEntryPs_weight_TextField);
        this.kdtEntryPs.getColumn("weight").setEditor(kdtEntryPs_weight_CellEditor);
        KDTextArea kdtEntryPs_remark_TextArea = new KDTextArea();
        kdtEntryPs_remark_TextArea.setName("kdtEntryPs_remark_TextArea");
        kdtEntryPs_remark_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtEntryPs_remark_CellEditor = new KDTDefaultCellEditor(kdtEntryPs_remark_TextArea);
        this.kdtEntryPs.getColumn("remark").setEditor(kdtEntryPs_remark_CellEditor);
        KDComboBox kdtEntryPs_isPass_ComboBox = new KDComboBox();
        kdtEntryPs_isPass_ComboBox.setName("kdtEntryPs_isPass_ComboBox");
        kdtEntryPs_isPass_ComboBox.setVisible(true);
        kdtEntryPs_isPass_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.markesupplier.subase.IsGradeEnum").toArray());
        KDTDefaultCellEditor kdtEntryPs_isPass_CellEditor = new KDTDefaultCellEditor(kdtEntryPs_isPass_ComboBox);
        this.kdtEntryPs.getColumn("isPass").setEditor(kdtEntryPs_isPass_CellEditor);
        KDFormattedTextField kdtEntryPs_score_TextField = new KDFormattedTextField();
        kdtEntryPs_score_TextField.setName("kdtEntryPs_score_TextField");
        kdtEntryPs_score_TextField.setVisible(true);
        kdtEntryPs_score_TextField.setEditable(true);
        kdtEntryPs_score_TextField.setHorizontalAlignment(2);
        kdtEntryPs_score_TextField.setDataType(1);
        	kdtEntryPs_score_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntryPs_score_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntryPs_score_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntryPs_score_CellEditor = new KDTDefaultCellEditor(kdtEntryPs_score_TextField);
        this.kdtEntryPs.getColumn("score").setEditor(kdtEntryPs_score_CellEditor);
        // prmtorgUnit		
        this.prmtorgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.PurchaseItemQuery");		
        this.prmtorgUnit.setVisible(false);		
        this.prmtorgUnit.setEditable(true);		
        this.prmtorgUnit.setDisplayFormat("$name$");		
        this.prmtorgUnit.setEditFormat("$number$");		
        this.prmtorgUnit.setCommitFormat("$number$");		
        this.prmtorgUnit.setRequired(false);
        // scrollPanesavePerson
        // txtsavePerson		
        this.txtsavePerson.setVisible(false);		
        this.txtsavePerson.setRequired(false);		
        this.txtsavePerson.setMaxLength(2000);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtamount,IsPass,txtsavePerson,State,prmtorgUnit,txtremake,prmtEntry,prmtTemplate,prmtEvaluationType,txtLinkJob,txtLinkPhone,txtLinkPerson,txtPartProject,txtSplArea,txtInviteType,txtSupplierName,prmtSupplier,kDDateLastUpdateTime,prmtLastUpdateUser,kDDateCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,pkauditDate,kdtEntrys,kdtEntryPs}));
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
        this.setBounds(new Rectangle(0, 0, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 629));
        contCreator.setBounds(new Rectangle(367, 577, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(367, 577, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(725, 577, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(725, 577, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateUser.setBounds(new Rectangle(367, 601, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(367, 601, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(725, 601, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(725, 601, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(715, 598, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(715, 598, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(10, 577, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(10, 577, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contauditDate.setBounds(new Rectangle(10, 601, 270, 19));
        this.add(contauditDate, new KDLayout.Constraints(10, 601, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel1.setBounds(new Rectangle(3, 3, 1008, 229));
        this.add(kDPanel1, new KDLayout.Constraints(3, 3, 1008, 229, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer1.setBounds(new Rectangle(6, 234, 1001, 125));
        this.add(kDContainer1, new KDLayout.Constraints(6, 234, 1001, 125, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer2.setBounds(new Rectangle(6, 361, 1001, 213));
        this.add(kDContainer2, new KDLayout.Constraints(6, 361, 1001, 213, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contorgUnit.setBounds(new Rectangle(651, 585, 270, 19));
        this.add(contorgUnit, new KDLayout.Constraints(651, 585, 270, 19, 0));
        contsavePerson.setBounds(new Rectangle(293, 584, 270, 19));
        this.add(contsavePerson, new KDLayout.Constraints(293, 584, 270, 19, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDateLastUpdateTime);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contauditDate
        contauditDate.setBoundEditor(pkauditDate);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(3, 3, 1008, 229));        contState.setBounds(new Rectangle(652, 12, 270, 19));
        kDPanel1.add(contState, new KDLayout.Constraints(652, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contremake.setBounds(new Rectangle(17, 128, 974, 86));
        kDPanel1.add(contremake, new KDLayout.Constraints(17, 128, 974, 86, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contEntry.setBounds(new Rectangle(17, 108, 270, 19));
        kDPanel1.add(contEntry, new KDLayout.Constraints(17, 108, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTemplate.setBounds(new Rectangle(368, 85, 270, 19));
        kDPanel1.add(contTemplate, new KDLayout.Constraints(368, 85, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEvaluationType.setBounds(new Rectangle(17, 85, 270, 19));
        kDPanel1.add(contEvaluationType, new KDLayout.Constraints(17, 85, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkJob.setBounds(new Rectangle(720, 62, 270, 19));
        kDPanel1.add(contLinkJob, new KDLayout.Constraints(720, 62, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLinkPhone.setBounds(new Rectangle(368, 62, 270, 19));
        kDPanel1.add(contLinkPhone, new KDLayout.Constraints(368, 62, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkPerson.setBounds(new Rectangle(17, 62, 270, 19));
        kDPanel1.add(contLinkPerson, new KDLayout.Constraints(17, 62, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPartProject.setBounds(new Rectangle(720, 39, 270, 19));
        kDPanel1.add(contPartProject, new KDLayout.Constraints(720, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSplArea.setBounds(new Rectangle(368, 39, 270, 19));
        kDPanel1.add(contSplArea, new KDLayout.Constraints(368, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteType.setBounds(new Rectangle(17, 39, 270, 19));
        kDPanel1.add(contInviteType, new KDLayout.Constraints(17, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupplierName.setBounds(new Rectangle(720, 16, 270, 19));
        kDPanel1.add(contSupplierName, new KDLayout.Constraints(720, 16, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSupplier.setBounds(new Rectangle(368, 16, 270, 19));
        kDPanel1.add(contSupplier, new KDLayout.Constraints(368, 16, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(720, 85, 270, 19));
        kDPanel1.add(contBizDate, new KDLayout.Constraints(720, 85, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(17, 16, 270, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(17, 16, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contamount.setBounds(new Rectangle(368, 108, 270, 19));
        kDPanel1.add(contamount, new KDLayout.Constraints(368, 108, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsPass.setBounds(new Rectangle(720, 108, 270, 19));
        kDPanel1.add(contIsPass, new KDLayout.Constraints(720, 108, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contState
        contState.setBoundEditor(State);
        //contremake
        contremake.setBoundEditor(scrollPaneremake);
        //scrollPaneremake
        scrollPaneremake.getViewport().add(txtremake, null);
        //contEntry
        contEntry.setBoundEditor(prmtEntry);
        //contTemplate
        contTemplate.setBoundEditor(prmtTemplate);
        //contEvaluationType
        contEvaluationType.setBoundEditor(prmtEvaluationType);
        //contLinkJob
        contLinkJob.setBoundEditor(txtLinkJob);
        //contLinkPhone
        contLinkPhone.setBoundEditor(txtLinkPhone);
        //contLinkPerson
        contLinkPerson.setBoundEditor(txtLinkPerson);
        //contPartProject
        contPartProject.setBoundEditor(txtPartProject);
        //contSplArea
        contSplArea.setBoundEditor(txtSplArea);
        //contInviteType
        contInviteType.setBoundEditor(txtInviteType);
        //contSupplierName
        contSupplierName.setBoundEditor(txtSupplierName);
        //contSupplier
        contSupplier.setBoundEditor(prmtSupplier);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contamount
        contamount.setBoundEditor(txtamount);
        //contIsPass
        contIsPass.setBoundEditor(IsPass);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryInfo(),null,false);
        kDContainer1.getContentPane().add(kdtEntrys_detailPanel, BorderLayout.CENTER);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntryPs_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntryPs,new com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryPInfo(),null,false);
        kDContainer2.getContentPane().add(kdtEntryPs_detailPanel, BorderLayout.CENTER);
		kdtEntryPs_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("isPass","1");
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        //contorgUnit
        contorgUnit.setBoundEditor(prmtorgUnit);
        //contsavePerson
        contsavePerson.setBoundEditor(scrollPanesavePerson);
        //scrollPanesavePerson
        scrollPanesavePerson.getViewport().add(txtsavePerson, null);

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
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
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
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.pkauditDate, "value");
		dataBinder.registerBinding("State", com.kingdee.eas.port.markesupplier.subase.SupplierState.class, this.State, "selectedItem");
		dataBinder.registerBinding("remake", String.class, this.txtremake, "text");
		dataBinder.registerBinding("Entry", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtEntry, "data");
		dataBinder.registerBinding("Template", com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo.class, this.prmtTemplate, "data");
		dataBinder.registerBinding("EvaluationType", com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo.class, this.prmtEvaluationType, "data");
		dataBinder.registerBinding("LinkJob", String.class, this.txtLinkJob, "text");
		dataBinder.registerBinding("LinkPhone", String.class, this.txtLinkPhone, "text");
		dataBinder.registerBinding("LinkPerson", String.class, this.txtLinkPerson, "text");
		dataBinder.registerBinding("PartProject", String.class, this.txtPartProject, "text");
		dataBinder.registerBinding("SplArea", String.class, this.txtSplArea, "text");
		dataBinder.registerBinding("InviteType", String.class, this.txtInviteType, "text");
		dataBinder.registerBinding("SupplierName", String.class, this.txtSupplierName, "text");
		dataBinder.registerBinding("Supplier", com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo.class, this.prmtSupplier, "data");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtamount, "value");
		dataBinder.registerBinding("IsPass", com.kingdee.eas.port.markesupplier.subase.IsGradeEnum.class, this.IsPass, "selectedItem");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.isHasContract", boolean.class, this.kdtEntrys, "isHasContract.text");
		dataBinder.registerBinding("entrys.contractAmount", java.math.BigDecimal.class, this.kdtEntrys, "contractAmount.text");
		dataBinder.registerBinding("entrys.manager", String.class, this.kdtEntrys, "manager.text");
		dataBinder.registerBinding("entrys.managerPhone", String.class, this.kdtEntrys, "managerPhone.text");
		dataBinder.registerBinding("entrys.contractPrice", java.math.BigDecimal.class, this.kdtEntrys, "contractPrice.text");
		dataBinder.registerBinding("entrys.Quantity", java.math.BigDecimal.class, this.kdtEntrys, "Quantity.text");
		dataBinder.registerBinding("entrys.uniy", java.lang.Object.class, this.kdtEntrys, "uniy.text");
		dataBinder.registerBinding("entrys.ValueForMoney", String.class, this.kdtEntrys, "ValueForMoney.text");
		dataBinder.registerBinding("entrys.contract", String.class, this.kdtEntrys, "contract.text");
		dataBinder.registerBinding("entrys.conName", java.lang.Object.class, this.kdtEntrys, "conName.text");
		dataBinder.registerBinding("EntryPs.seq", int.class, this.kdtEntryPs, "seq.text");
		dataBinder.registerBinding("EntryPs", com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherEntryPInfo.class, this.kdtEntryPs, "userObject");
		dataBinder.registerBinding("EntryPs.guideType", String.class, this.kdtEntryPs, "guideType.text");
		dataBinder.registerBinding("EntryPs.fullNum", String.class, this.kdtEntryPs, "fullNum.text");
		dataBinder.registerBinding("EntryPs.weight", java.math.BigDecimal.class, this.kdtEntryPs, "weight.text");
		dataBinder.registerBinding("EntryPs.remark", String.class, this.kdtEntryPs, "remark.text");
		dataBinder.registerBinding("EntryPs.isPass", com.kingdee.util.enums.Enum.class, this.kdtEntryPs, "isPass.text");
		dataBinder.registerBinding("EntryPs.score", java.math.BigDecimal.class, this.kdtEntryPs, "score.text");
		dataBinder.registerBinding("EntryPs.guideName", java.lang.Object.class, this.kdtEntryPs, "guideName.text");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo.class, this.prmtorgUnit, "data");
		dataBinder.registerBinding("savePerson", String.class, this.txtsavePerson, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.markesupplier.subill.app.MarketSupplierReviewGatherEditUIHandler";
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
        this.txtamount.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
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

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("NONE");
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("State", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remake", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Template", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EvaluationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LinkJob", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LinkPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LinkPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PartProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SplArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("InviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SupplierName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("IsPass", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.isHasContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.contractAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.manager", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.managerPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.contractPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Quantity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.uniy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.ValueForMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.contract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.conName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPs.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPs", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPs.guideType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPs.fullNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPs.weight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPs.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPs.isPass", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPs.score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPs.guideName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("savePerson", ValidateHelper.ON_SAVE);    		
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
     * output prmtEntry_dataChanged method
     */
    protected void prmtEntry_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code hereeeeqq
    }

    /**
     * output prmtTemplate_dataChanged method
     */
    protected void prmtTemplate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code heresssee
    }

    /**
     * output prmtEvaluationType_dataChanged method
     */
    protected void prmtEvaluationType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code heresssf
    }

    /**
     * output prmtSupplier_dataChanged method
     */
    protected void prmtSupplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code hereffe
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code hereefq
    }

    /**
     * output kdtEntryPs_editStopped method
     */
    protected void kdtEntryPs_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code hereeeqqy
    }


    /**
     * output prmtSupplier_Changed() method
     */
    public void prmtSupplier_Changed() throws Exception
    {
        System.out.println("prmtSupplier_Changed() Function is executed!");
            txtSupplierName.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtSupplier.getData(),"supplierName")));


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
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("State"));
        sic.add(new SelectorItemInfo("remake"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("Entry.id"));
        	sic.add(new SelectorItemInfo("Entry.number"));
        	sic.add(new SelectorItemInfo("Entry.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Template.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("Template.id"));
        	sic.add(new SelectorItemInfo("Template.number"));
        	sic.add(new SelectorItemInfo("Template.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EvaluationType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("EvaluationType.id"));
        	sic.add(new SelectorItemInfo("EvaluationType.number"));
        	sic.add(new SelectorItemInfo("EvaluationType.name"));
		}
        sic.add(new SelectorItemInfo("LinkJob"));
        sic.add(new SelectorItemInfo("LinkPhone"));
        sic.add(new SelectorItemInfo("LinkPerson"));
        sic.add(new SelectorItemInfo("PartProject"));
        sic.add(new SelectorItemInfo("SplArea"));
        sic.add(new SelectorItemInfo("InviteType"));
        sic.add(new SelectorItemInfo("SupplierName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Supplier.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("Supplier.id"));
        	sic.add(new SelectorItemInfo("Supplier.number"));
		}
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("IsPass"));
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entrys.isHasContract"));
    	sic.add(new SelectorItemInfo("entrys.contractAmount"));
    	sic.add(new SelectorItemInfo("entrys.manager"));
    	sic.add(new SelectorItemInfo("entrys.managerPhone"));
    	sic.add(new SelectorItemInfo("entrys.contractPrice"));
    	sic.add(new SelectorItemInfo("entrys.Quantity"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.uniy.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.uniy.id"));
			sic.add(new SelectorItemInfo("entrys.uniy.name"));
        	sic.add(new SelectorItemInfo("entrys.uniy.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.ValueForMoney"));
    	sic.add(new SelectorItemInfo("entrys.contract"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.conName.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.conName.id"));
			sic.add(new SelectorItemInfo("entrys.conName.name"));
        	sic.add(new SelectorItemInfo("entrys.conName.number"));
		}
    	sic.add(new SelectorItemInfo("EntryPs.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EntryPs.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("EntryPs.guideType"));
    	sic.add(new SelectorItemInfo("EntryPs.fullNum"));
    	sic.add(new SelectorItemInfo("EntryPs.weight"));
    	sic.add(new SelectorItemInfo("EntryPs.remark"));
    	sic.add(new SelectorItemInfo("EntryPs.isPass"));
    	sic.add(new SelectorItemInfo("EntryPs.score"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EntryPs.guideName.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("EntryPs.guideName.id"));
			sic.add(new SelectorItemInfo("EntryPs.guideName.name"));
        	sic.add(new SelectorItemInfo("EntryPs.guideName.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("orgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("orgUnit.id"));
        	sic.add(new SelectorItemInfo("orgUnit.number"));
        	sic.add(new SelectorItemInfo("orgUnit.name"));
		}
        sic.add(new SelectorItemInfo("savePerson"));
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
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherFactory.getRemoteInstance().unAudit(editData);
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
            innerActionPerformed("eas", AbstractMarketSupplierReviewGatherEditUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.markesupplier.subill.client", "MarketSupplierReviewGatherEditUI");
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
        return com.kingdee.eas.port.markesupplier.subill.client.MarketSupplierReviewGatherEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo objectValue = new com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/markesupplier/subill/MarketSupplierReviewGather";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.markesupplier.subill.app.MarketSupplierReviewGatherQuery");
	}
    
        
					protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(IsPass.getSelectedItem())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"ÊÇ·ñºÏ¸ñ"});
		}
			super.beforeStoreFields(arg0);
		}

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntrys;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("State","1");
vo.put("IsPass","1");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}