/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa.client;

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
public abstract class AbstractSwbEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSwbEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectName;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersion;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chklasted;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSumArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostThan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAreaThan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlandscape;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSumAreaIndax;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGreenH;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contother;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcompany;
    protected com.kingdee.bos.ctrl.swing.KDComboBox State;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProjectName;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtVersion;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSumArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCostThan;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAreaThan;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtlandscape;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSumAreaIndax;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtGreenH;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditTime;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtother;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.eas.fdc.gcftbiaoa.SwbInfo editData = null;
    protected ActionAduit actionAduit = null;
    protected ActionUnAudit actionUnAudit = null;
    /**
     * output class constructor
     */
    public AbstractSwbEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSwbEditUI.class.getName());
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
        //actionAduit
        this.actionAduit = new ActionAduit(this);
        getActionManager().registerAction("actionAduit", actionAduit);
        this.actionAduit.setExtendProperty("canForewarn", "true");
        this.actionAduit.setExtendProperty("userDefined", "true");
        this.actionAduit.setExtendProperty("isObjectUpdateLock", "false");
         this.actionAduit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAduit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
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
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contVersion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chklasted = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contSumArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCostThan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAreaThan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlandscape = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSumAreaIndax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGreenH = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contother = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.State = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtProjectName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtVersion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSumArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCostThan = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAreaThan = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtlandscape = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSumAreaIndax = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtGreenH = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkauditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtother = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contcompany.setName("contcompany");
        this.contState.setName("contState");
        this.contProjectName.setName("contProjectName");
        this.kDContainer1.setName("kDContainer1");
        this.contVersion.setName("contVersion");
        this.chklasted.setName("chklasted");
        this.contSumArea.setName("contSumArea");
        this.contCostThan.setName("contCostThan");
        this.contAreaThan.setName("contAreaThan");
        this.contlandscape.setName("contlandscape");
        this.contSumAreaIndax.setName("contSumAreaIndax");
        this.contGreenH.setName("contGreenH");
        this.contauditTime.setName("contauditTime");
        this.contother.setName("contother");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtcompany.setName("txtcompany");
        this.State.setName("State");
        this.prmtProjectName.setName("prmtProjectName");
        this.kdtEntrys.setName("kdtEntrys");
        this.txtVersion.setName("txtVersion");
        this.txtSumArea.setName("txtSumArea");
        this.txtCostThan.setName("txtCostThan");
        this.txtAreaThan.setName("txtAreaThan");
        this.txtlandscape.setName("txtlandscape");
        this.txtSumAreaIndax.setName("txtSumAreaIndax");
        this.txtGreenH.setName("txtGreenH");
        this.pkauditTime.setName("pkauditTime");
        this.txtother.setName("txtother");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
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
        this.contLastUpdateUser.setVisible(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);		
        this.contLastUpdateTime.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(false);		
        this.contBizDate.setEnabled(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setEnabled(false);		
        this.contDescription.setVisible(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contcompany		
        this.contcompany.setBoundLabelText(resHelper.getString("contcompany.boundLabelText"));		
        this.contcompany.setBoundLabelLength(100);		
        this.contcompany.setBoundLabelUnderline(true);		
        this.contcompany.setVisible(true);		
        this.contcompany.setEnabled(false);
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);		
        this.contState.setVisible(true);
        // contProjectName		
        this.contProjectName.setBoundLabelText(resHelper.getString("contProjectName.boundLabelText"));		
        this.contProjectName.setBoundLabelLength(100);		
        this.contProjectName.setBoundLabelUnderline(true);		
        this.contProjectName.setVisible(true);		
        this.contProjectName.setEnabled(false);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);
        // contVersion		
        this.contVersion.setBoundLabelText(resHelper.getString("contVersion.boundLabelText"));		
        this.contVersion.setBoundLabelLength(100);		
        this.contVersion.setBoundLabelUnderline(true);		
        this.contVersion.setVisible(true);
        // chklasted		
        this.chklasted.setText(resHelper.getString("chklasted.text"));		
        this.chklasted.setHorizontalAlignment(2);		
        this.chklasted.setRequired(true);		
        this.chklasted.setEnabled(false);		
        this.chklasted.setVisible(false);
        // contSumArea		
        this.contSumArea.setBoundLabelText(resHelper.getString("contSumArea.boundLabelText"));		
        this.contSumArea.setBoundLabelLength(120);		
        this.contSumArea.setBoundLabelUnderline(true);		
        this.contSumArea.setVisible(true);
        // contCostThan		
        this.contCostThan.setBoundLabelText(resHelper.getString("contCostThan.boundLabelText"));		
        this.contCostThan.setBoundLabelLength(100);		
        this.contCostThan.setBoundLabelUnderline(true);		
        this.contCostThan.setVisible(true);
        // contAreaThan		
        this.contAreaThan.setBoundLabelText(resHelper.getString("contAreaThan.boundLabelText"));		
        this.contAreaThan.setBoundLabelLength(100);		
        this.contAreaThan.setBoundLabelUnderline(true);		
        this.contAreaThan.setVisible(true);
        // contlandscape		
        this.contlandscape.setBoundLabelText(resHelper.getString("contlandscape.boundLabelText"));		
        this.contlandscape.setBoundLabelLength(100);		
        this.contlandscape.setBoundLabelUnderline(true);		
        this.contlandscape.setVisible(true);		
        this.contlandscape.setEnabled(false);
        // contSumAreaIndax		
        this.contSumAreaIndax.setBoundLabelText(resHelper.getString("contSumAreaIndax.boundLabelText"));		
        this.contSumAreaIndax.setBoundLabelLength(180);		
        this.contSumAreaIndax.setBoundLabelUnderline(true);		
        this.contSumAreaIndax.setVisible(true);
        // contGreenH		
        this.contGreenH.setBoundLabelText(resHelper.getString("contGreenH.boundLabelText"));		
        this.contGreenH.setBoundLabelLength(100);		
        this.contGreenH.setBoundLabelUnderline(true);		
        this.contGreenH.setVisible(true);		
        this.contGreenH.setEnabled(false);
        // contauditTime		
        this.contauditTime.setBoundLabelText(resHelper.getString("contauditTime.boundLabelText"));		
        this.contauditTime.setBoundLabelLength(100);		
        this.contauditTime.setBoundLabelUnderline(true);		
        this.contauditTime.setVisible(true);
        // contother		
        this.contother.setBoundLabelText(resHelper.getString("contother.boundLabelText"));		
        this.contother.setBoundLabelLength(100);		
        this.contother.setBoundLabelUnderline(true);		
        this.contother.setVisible(true);		
        this.contother.setEnabled(false);
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
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // pkBizDate		
        this.pkBizDate.setVisible(false);		
        this.pkBizDate.setEnabled(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);		
        this.txtDescription.setVisible(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // txtcompany		
        this.txtcompany.setHorizontalAlignment(2);		
        this.txtcompany.setMaxLength(100);		
        this.txtcompany.setRequired(false);		
        this.txtcompany.setEnabled(false);
        // State		
        this.State.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.State.setRequired(false);
        // prmtProjectName		
        this.prmtProjectName.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");		
        this.prmtProjectName.setEditable(true);		
        this.prmtProjectName.setDisplayFormat("$name$");		
        this.prmtProjectName.setEditFormat("$number$");		
        this.prmtProjectName.setCommitFormat("$number$");		
        this.prmtProjectName.setRequired(false);		
        this.prmtProjectName.setEnabled(false);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"key\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol1\" /><t:Column t:key=\"title\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"Price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol3\" /><t:Column t:key=\"GreenArea\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol4\" /><t:Column t:key=\"GreenAreaIndex\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol5\" /><t:Column t:key=\"Areaproportion\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"Sumproportion\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"ComePrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol8\" /><t:Column t:key=\"danwei\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell /><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{key}</t:Cell><t:Cell>$Resource{title}</t:Cell><t:Cell>$Resource{Price}</t:Cell><t:Cell>$Resource{GreenArea}</t:Cell><t:Cell>$Resource{GreenAreaIndex}</t:Cell><t:Cell>$Resource{Areaproportion}</t:Cell><t:Cell>$Resource{Sumproportion}</t:Cell><t:Cell>$Resource{ComePrice}</t:Cell></t:Row><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{danwei}</t:Cell><t:Cell /><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{key_Row2}</t:Cell><t:Cell>$Resource{title_Row2}</t:Cell><t:Cell>$Resource{Price_Row2}</t:Cell><t:Cell>$Resource{GreenArea_Row2}</t:Cell><t:Cell>$Resource{GreenAreaIndex_Row2}</t:Cell><t:Cell>$Resource{Areaproportion_Row2}</t:Cell><t:Cell>$Resource{Sumproportion_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"1\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"1\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"1\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"1\" t:right=\"7\" /><t:Block t:top=\"0\" t:left=\"8\" t:bottom=\"0\" t:right=\"9\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

                this.kdtEntrys.putBindContents("editData",new String[] {"id","key","title","Price","GreenArea","GreenAreaIndex","Areaproportion","Sumproportion","ComePrice","danwei"});


        this.kdtEntrys.checkParsed();
        KDTextField kdtEntrys_key_TextField = new KDTextField();
        kdtEntrys_key_TextField.setName("kdtEntrys_key_TextField");
        kdtEntrys_key_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_key_CellEditor = new KDTDefaultCellEditor(kdtEntrys_key_TextField);
        this.kdtEntrys.getColumn("key").setEditor(kdtEntrys_key_CellEditor);
        KDTextField kdtEntrys_title_TextField = new KDTextField();
        kdtEntrys_title_TextField.setName("kdtEntrys_title_TextField");
        kdtEntrys_title_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_title_CellEditor = new KDTDefaultCellEditor(kdtEntrys_title_TextField);
        this.kdtEntrys.getColumn("title").setEditor(kdtEntrys_title_CellEditor);
        KDFormattedTextField kdtEntrys_Price_TextField = new KDFormattedTextField();
        kdtEntrys_Price_TextField.setName("kdtEntrys_Price_TextField");
        kdtEntrys_Price_TextField.setVisible(true);
        kdtEntrys_Price_TextField.setEditable(true);
        kdtEntrys_Price_TextField.setHorizontalAlignment(2);
        kdtEntrys_Price_TextField.setDataType(1);
        	kdtEntrys_Price_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_Price_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_Price_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_Price_CellEditor = new KDTDefaultCellEditor(kdtEntrys_Price_TextField);
        this.kdtEntrys.getColumn("Price").setEditor(kdtEntrys_Price_CellEditor);
        KDFormattedTextField kdtEntrys_GreenArea_TextField = new KDFormattedTextField();
        kdtEntrys_GreenArea_TextField.setName("kdtEntrys_GreenArea_TextField");
        kdtEntrys_GreenArea_TextField.setVisible(true);
        kdtEntrys_GreenArea_TextField.setEditable(true);
        kdtEntrys_GreenArea_TextField.setHorizontalAlignment(2);
        kdtEntrys_GreenArea_TextField.setDataType(1);
        	kdtEntrys_GreenArea_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_GreenArea_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_GreenArea_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_GreenArea_CellEditor = new KDTDefaultCellEditor(kdtEntrys_GreenArea_TextField);
        this.kdtEntrys.getColumn("GreenArea").setEditor(kdtEntrys_GreenArea_CellEditor);
        KDFormattedTextField kdtEntrys_GreenAreaIndex_TextField = new KDFormattedTextField();
        kdtEntrys_GreenAreaIndex_TextField.setName("kdtEntrys_GreenAreaIndex_TextField");
        kdtEntrys_GreenAreaIndex_TextField.setVisible(true);
        kdtEntrys_GreenAreaIndex_TextField.setEditable(true);
        kdtEntrys_GreenAreaIndex_TextField.setHorizontalAlignment(2);
        kdtEntrys_GreenAreaIndex_TextField.setDataType(1);
        	kdtEntrys_GreenAreaIndex_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_GreenAreaIndex_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_GreenAreaIndex_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_GreenAreaIndex_CellEditor = new KDTDefaultCellEditor(kdtEntrys_GreenAreaIndex_TextField);
        this.kdtEntrys.getColumn("GreenAreaIndex").setEditor(kdtEntrys_GreenAreaIndex_CellEditor);
        KDFormattedTextField kdtEntrys_Areaproportion_TextField = new KDFormattedTextField();
        kdtEntrys_Areaproportion_TextField.setName("kdtEntrys_Areaproportion_TextField");
        kdtEntrys_Areaproportion_TextField.setVisible(true);
        kdtEntrys_Areaproportion_TextField.setEditable(true);
        kdtEntrys_Areaproportion_TextField.setHorizontalAlignment(2);
        kdtEntrys_Areaproportion_TextField.setDataType(1);
        	kdtEntrys_Areaproportion_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_Areaproportion_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_Areaproportion_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_Areaproportion_CellEditor = new KDTDefaultCellEditor(kdtEntrys_Areaproportion_TextField);
        this.kdtEntrys.getColumn("Areaproportion").setEditor(kdtEntrys_Areaproportion_CellEditor);
        KDFormattedTextField kdtEntrys_Sumproportion_TextField = new KDFormattedTextField();
        kdtEntrys_Sumproportion_TextField.setName("kdtEntrys_Sumproportion_TextField");
        kdtEntrys_Sumproportion_TextField.setVisible(true);
        kdtEntrys_Sumproportion_TextField.setEditable(true);
        kdtEntrys_Sumproportion_TextField.setHorizontalAlignment(2);
        kdtEntrys_Sumproportion_TextField.setDataType(1);
        	kdtEntrys_Sumproportion_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_Sumproportion_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_Sumproportion_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_Sumproportion_CellEditor = new KDTDefaultCellEditor(kdtEntrys_Sumproportion_TextField);
        this.kdtEntrys.getColumn("Sumproportion").setEditor(kdtEntrys_Sumproportion_CellEditor);
        KDFormattedTextField kdtEntrys_ComePrice_TextField = new KDFormattedTextField();
        kdtEntrys_ComePrice_TextField.setName("kdtEntrys_ComePrice_TextField");
        kdtEntrys_ComePrice_TextField.setVisible(true);
        kdtEntrys_ComePrice_TextField.setEditable(true);
        kdtEntrys_ComePrice_TextField.setHorizontalAlignment(2);
        kdtEntrys_ComePrice_TextField.setDataType(1);
        	kdtEntrys_ComePrice_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_ComePrice_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_ComePrice_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_ComePrice_CellEditor = new KDTDefaultCellEditor(kdtEntrys_ComePrice_TextField);
        this.kdtEntrys.getColumn("ComePrice").setEditor(kdtEntrys_ComePrice_CellEditor);
        KDFormattedTextField kdtEntrys_danwei_TextField = new KDFormattedTextField();
        kdtEntrys_danwei_TextField.setName("kdtEntrys_danwei_TextField");
        kdtEntrys_danwei_TextField.setVisible(true);
        kdtEntrys_danwei_TextField.setEditable(true);
        kdtEntrys_danwei_TextField.setHorizontalAlignment(2);
        kdtEntrys_danwei_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntrys_danwei_CellEditor = new KDTDefaultCellEditor(kdtEntrys_danwei_TextField);
        this.kdtEntrys.getColumn("danwei").setEditor(kdtEntrys_danwei_CellEditor);
        // txtVersion		
        this.txtVersion.setHorizontalAlignment(2);		
        this.txtVersion.setDataType(0);		
        this.txtVersion.setSupportedEmpty(true);		
        this.txtVersion.setRequired(false);
        // txtSumArea		
        this.txtSumArea.setHorizontalAlignment(2);		
        this.txtSumArea.setDataType(1);		
        this.txtSumArea.setSupportedEmpty(true);		
        this.txtSumArea.setMinimumValue( new java.math.BigDecimal("-1.0E24"));		
        this.txtSumArea.setMaximumValue( new java.math.BigDecimal("1.0E24"));		
        this.txtSumArea.setPrecision(4);		
        this.txtSumArea.setRequired(false);
        // txtCostThan		
        this.txtCostThan.setHorizontalAlignment(2);		
        this.txtCostThan.setDataType(1);		
        this.txtCostThan.setSupportedEmpty(true);		
        this.txtCostThan.setMinimumValue( new java.math.BigDecimal("-1.0E24"));		
        this.txtCostThan.setMaximumValue( new java.math.BigDecimal("1.0E24"));		
        this.txtCostThan.setPrecision(4);		
        this.txtCostThan.setRequired(false);
        // txtAreaThan		
        this.txtAreaThan.setHorizontalAlignment(2);		
        this.txtAreaThan.setDataType(1);		
        this.txtAreaThan.setSupportedEmpty(true);		
        this.txtAreaThan.setMinimumValue( new java.math.BigDecimal("-1.0E24"));		
        this.txtAreaThan.setMaximumValue( new java.math.BigDecimal("1.0E24"));		
        this.txtAreaThan.setPrecision(4);		
        this.txtAreaThan.setRequired(false);
        // txtlandscape		
        this.txtlandscape.setHorizontalAlignment(2);		
        this.txtlandscape.setDataType(1);		
        this.txtlandscape.setSupportedEmpty(true);		
        this.txtlandscape.setMinimumValue( new java.math.BigDecimal("-1.0E24"));		
        this.txtlandscape.setMaximumValue( new java.math.BigDecimal("1.0E24"));		
        this.txtlandscape.setPrecision(4);		
        this.txtlandscape.setRequired(false);
        // txtSumAreaIndax		
        this.txtSumAreaIndax.setHorizontalAlignment(2);		
        this.txtSumAreaIndax.setDataType(1);		
        this.txtSumAreaIndax.setSupportedEmpty(true);		
        this.txtSumAreaIndax.setMinimumValue( new java.math.BigDecimal("-1.0E24"));		
        this.txtSumAreaIndax.setMaximumValue( new java.math.BigDecimal("1.0E24"));		
        this.txtSumAreaIndax.setPrecision(4);		
        this.txtSumAreaIndax.setRequired(false);
        // txtGreenH		
        this.txtGreenH.setHorizontalAlignment(2);		
        this.txtGreenH.setDataType(1);		
        this.txtGreenH.setSupportedEmpty(true);		
        this.txtGreenH.setMinimumValue( new java.math.BigDecimal("-1.0E24"));		
        this.txtGreenH.setMaximumValue( new java.math.BigDecimal("1.0E24"));		
        this.txtGreenH.setPrecision(4);		
        this.txtGreenH.setRequired(false);
        // pkauditTime		
        this.pkauditTime.setRequired(false);
        // txtother		
        this.txtother.setHorizontalAlignment(2);		
        this.txtother.setDataType(1);		
        this.txtother.setSupportedEmpty(true);		
        this.txtother.setMinimumValue( new java.math.BigDecimal("-1.0E24"));		
        this.txtother.setMaximumValue( new java.math.BigDecimal("1.0E24"));		
        this.txtother.setPrecision(4);		
        this.txtother.setRequired(false);
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAduit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {State,chklasted,txtcompany,kDDateLastUpdateTime,prmtLastUpdateUser,kDDateCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,prmtProjectName,txtVersion,txtSumArea,txtCostThan,txtAreaThan,txtlandscape,txtSumAreaIndax,txtGreenH,txtother,pkauditTime,kdtEntrys}));
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
        contCreator.setBounds(new Rectangle(728, 568, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(728, 568, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(728, 599, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(728, 599, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateUser.setBounds(new Rectangle(379, 568, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(379, 568, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(379, 599, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(379, 599, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(16, 14, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(16, 14, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(305, 56, 45, 19));
        this.add(contBizDate, new KDLayout.Constraints(305, 56, 45, 19, 0));
        contDescription.setBounds(new Rectangle(326, 93, 8, 19));
        this.add(contDescription, new KDLayout.Constraints(326, 93, 8, 19, 0));
        contAuditor.setBounds(new Rectangle(12, 568, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(12, 568, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcompany.setBounds(new Rectangle(381, 14, 270, 19));
        this.add(contcompany, new KDLayout.Constraints(381, 14, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contState.setBounds(new Rectangle(747, 70, 270, 19));
        this.add(contState, new KDLayout.Constraints(747, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contProjectName.setBounds(new Rectangle(747, 14, 270, 19));
        this.add(contProjectName, new KDLayout.Constraints(747, 14, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer1.setBounds(new Rectangle(19, 126, 975, 426));
        this.add(kDContainer1, new KDLayout.Constraints(19, 126, 975, 426, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contVersion.setBounds(new Rectangle(381, 70, 270, 19));
        this.add(contVersion, new KDLayout.Constraints(381, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chklasted.setBounds(new Rectangle(669, 99, 78, 19));
        this.add(chklasted, new KDLayout.Constraints(669, 99, 78, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSumArea.setBounds(new Rectangle(16, 42, 270, 19));
        this.add(contSumArea, new KDLayout.Constraints(16, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCostThan.setBounds(new Rectangle(381, 42, 270, 19));
        this.add(contCostThan, new KDLayout.Constraints(381, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAreaThan.setBounds(new Rectangle(747, 42, 270, 19));
        this.add(contAreaThan, new KDLayout.Constraints(747, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contlandscape.setBounds(new Rectangle(16, 102, 270, 19));
        this.add(contlandscape, new KDLayout.Constraints(16, 102, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSumAreaIndax.setBounds(new Rectangle(16, 70, 270, 19));
        this.add(contSumAreaIndax, new KDLayout.Constraints(16, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contGreenH.setBounds(new Rectangle(381, 102, 270, 19));
        this.add(contGreenH, new KDLayout.Constraints(381, 102, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contauditTime.setBounds(new Rectangle(12, 599, 270, 19));
        this.add(contauditTime, new KDLayout.Constraints(12, 599, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contother.setBounds(new Rectangle(749, 102, 268, 19));
        this.add(contother, new KDLayout.Constraints(749, 102, 268, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDateLastUpdateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contcompany
        contcompany.setBoundEditor(txtcompany);
        //contState
        contState.setBoundEditor(State);
        //contProjectName
        contProjectName.setBoundEditor(prmtProjectName);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.fdc.gcftbiaoa.SwbEntryInfo(),null,false);
        kDContainer1.getContentPane().add(kdtEntrys_detailPanel, BorderLayout.CENTER);
        //contVersion
        contVersion.setBoundEditor(txtVersion);
        //contSumArea
        contSumArea.setBoundEditor(txtSumArea);
        //contCostThan
        contCostThan.setBoundEditor(txtCostThan);
        //contAreaThan
        contAreaThan.setBoundEditor(txtAreaThan);
        //contlandscape
        contlandscape.setBoundEditor(txtlandscape);
        //contSumAreaIndax
        contSumAreaIndax.setBoundEditor(txtSumAreaIndax);
        //contGreenH
        contGreenH.setBoundEditor(txtGreenH);
        //contauditTime
        contauditTime.setBoundEditor(pkauditTime);
        //contother
        contother.setBoundEditor(txtother);

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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("lasted", boolean.class, this.chklasted, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("company", String.class, this.txtcompany, "text");
		dataBinder.registerBinding("State", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.State, "selectedItem");
		dataBinder.registerBinding("ProjectName", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtProjectName, "data");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.gcftbiaoa.SwbEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.title", String.class, this.kdtEntrys, "title.text");
		dataBinder.registerBinding("entrys.Price", java.math.BigDecimal.class, this.kdtEntrys, "Price.text");
		dataBinder.registerBinding("entrys.GreenArea", java.math.BigDecimal.class, this.kdtEntrys, "GreenArea.text");
		dataBinder.registerBinding("entrys.GreenAreaIndex", java.math.BigDecimal.class, this.kdtEntrys, "GreenAreaIndex.text");
		dataBinder.registerBinding("entrys.Sumproportion", java.math.BigDecimal.class, this.kdtEntrys, "Sumproportion.text");
		dataBinder.registerBinding("entrys.Areaproportion", java.math.BigDecimal.class, this.kdtEntrys, "Areaproportion.text");
		dataBinder.registerBinding("entrys.ComePrice", java.math.BigDecimal.class, this.kdtEntrys, "ComePrice.text");
		dataBinder.registerBinding("entrys.danwei", int.class, this.kdtEntrys, "danwei.text");
		dataBinder.registerBinding("entrys.key", String.class, this.kdtEntrys, "key.text");
		dataBinder.registerBinding("Version", int.class, this.txtVersion, "value");
		dataBinder.registerBinding("SumArea", java.math.BigDecimal.class, this.txtSumArea, "value");
		dataBinder.registerBinding("CostThan", java.math.BigDecimal.class, this.txtCostThan, "value");
		dataBinder.registerBinding("AreaThan", java.math.BigDecimal.class, this.txtAreaThan, "value");
		dataBinder.registerBinding("landscape", java.math.BigDecimal.class, this.txtlandscape, "value");
		dataBinder.registerBinding("SumAreaIndax", java.math.BigDecimal.class, this.txtSumAreaIndax, "value");
		dataBinder.registerBinding("GreenH", java.math.BigDecimal.class, this.txtGreenH, "value");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkauditTime, "value");
		dataBinder.registerBinding("other", java.math.BigDecimal.class, this.txtother, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.gcftbiaoa.app.SwbEditUIHandler";
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
        this.State.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.gcftbiaoa.SwbInfo)ov;
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
		getValidateHelper().registerBindProperty("lasted", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("State", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ProjectName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.title", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.GreenArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.GreenAreaIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Sumproportion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Areaproportion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.ComePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.danwei", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.key", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Version", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SumArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CostThan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AreaThan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("landscape", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SumAreaIndax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("GreenH", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("other", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("lasted"));
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
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
        sic.add(new SelectorItemInfo("company"));
        sic.add(new SelectorItemInfo("State"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("ProjectName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("ProjectName.id"));
        	sic.add(new SelectorItemInfo("ProjectName.number"));
        	sic.add(new SelectorItemInfo("ProjectName.name"));
		}
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entrys.title"));
    	sic.add(new SelectorItemInfo("entrys.Price"));
    	sic.add(new SelectorItemInfo("entrys.GreenArea"));
    	sic.add(new SelectorItemInfo("entrys.GreenAreaIndex"));
    	sic.add(new SelectorItemInfo("entrys.Sumproportion"));
    	sic.add(new SelectorItemInfo("entrys.Areaproportion"));
    	sic.add(new SelectorItemInfo("entrys.ComePrice"));
    	sic.add(new SelectorItemInfo("entrys.danwei"));
    	sic.add(new SelectorItemInfo("entrys.key"));
        sic.add(new SelectorItemInfo("Version"));
        sic.add(new SelectorItemInfo("SumArea"));
        sic.add(new SelectorItemInfo("CostThan"));
        sic.add(new SelectorItemInfo("AreaThan"));
        sic.add(new SelectorItemInfo("landscape"));
        sic.add(new SelectorItemInfo("SumAreaIndax"));
        sic.add(new SelectorItemInfo("GreenH"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("other"));
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
     * output actionAduit_actionPerformed method
     */
    public void actionAduit_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.gcftbiaoa.SwbFactory.getRemoteInstance().aduit(editData);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.gcftbiaoa.SwbFactory.getRemoteInstance().unAudit(editData);
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
	public RequestContext prepareActionAduit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAduit() {
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
     * output ActionAduit class
     */     
    protected class ActionAduit extends ItemAction {     
    
        public ActionAduit()
        {
            this(null);
        }

        public ActionAduit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAduit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAduit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAduit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSwbEditUI.this, "ActionAduit", "actionAduit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractSwbEditUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.gcftbiaoa.client", "SwbEditUI");
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
        return com.kingdee.eas.fdc.gcftbiaoa.client.SwbEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.gcftbiaoa.SwbFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.gcftbiaoa.SwbInfo objectValue = new com.kingdee.eas.fdc.gcftbiaoa.SwbInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/gcftbiaoa/Swb";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.gcftbiaoa.app.SwbQuery");
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
		vo.put("State","1SAVED");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}