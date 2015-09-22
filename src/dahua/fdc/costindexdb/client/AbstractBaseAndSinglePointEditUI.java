/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.client;

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
public abstract class AbstractBaseAndSinglePointEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBaseAndSinglePointEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectId;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contversion;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisLatest;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpointBillStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbeizhu;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaudiTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtprojectName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtprojectId;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtversion;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdcBase;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdcSingle;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEcost;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEcost_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDComboBox pointBillStatus;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanebeizhu;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtbeizhu;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkaudiTime;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUNAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFix;
    protected com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAdudit actionUnAdudit = null;
    protected ActionRefix actionRefix = null;
    /**
     * output class constructor
     */
    public AbstractBaseAndSinglePointEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBaseAndSinglePointEditUI.class.getName());
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
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
        this.actionAudit.setExtendProperty("canForewarn", "true");
        this.actionAudit.setExtendProperty("userDefined", "true");
        this.actionAudit.setExtendProperty("isObjectUpdateLock", "false");
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionUnAdudit
        this.actionUnAdudit = new ActionUnAdudit(this);
        getActionManager().registerAction("actionUnAdudit", actionUnAdudit);
        this.actionUnAdudit.setExtendProperty("canForewarn", "true");
        this.actionUnAdudit.setExtendProperty("userDefined", "true");
        this.actionUnAdudit.setExtendProperty("isObjectUpdateLock", "false");
         this.actionUnAdudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionUnAdudit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionRefix
        this.actionRefix = new ActionRefix(this);
        getActionManager().registerAction("actionRefix", actionRefix);
        this.actionRefix.setExtendProperty("canForewarn", "true");
        this.actionRefix.setExtendProperty("userDefined", "true");
        this.actionRefix.setExtendProperty("isObjectUpdateLock", "false");
         this.actionRefix.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRefix.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectId = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contversion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisLatest = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contpointBillStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbeizhu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaudiTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtprojectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtprojectId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtversion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdcBase = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdcSingle = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtEcost = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.pointBillStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.scrollPanebeizhu = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtbeizhu = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.pkaudiTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUNAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFix = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contprojectName.setName("contprojectName");
        this.contprojectId.setName("contprojectId");
        this.contversion.setName("contversion");
        this.chkisLatest.setName("chkisLatest");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contpointBillStatus.setName("contpointBillStatus");
        this.contbeizhu.setName("contbeizhu");
        this.contaudiTime.setName("contaudiTime");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtprojectName.setName("txtprojectName");
        this.txtprojectId.setName("txtprojectId");
        this.txtversion.setName("txtversion");
        this.kdcBase.setName("kdcBase");
        this.kdcSingle.setName("kdcSingle");
        this.kdtEntrys.setName("kdtEntrys");
        this.kdtEcost.setName("kdtEcost");
        this.pointBillStatus.setName("pointBillStatus");
        this.scrollPanebeizhu.setName("scrollPanebeizhu");
        this.txtbeizhu.setName("txtbeizhu");
        this.pkaudiTime.setName("pkaudiTime");
        this.btnAudit.setName("btnAudit");
        this.btnUNAudit.setName("btnUNAudit");
        this.btnFix.setName("btnFix");
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
        this.contBizDate.setVisible(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setVisible(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contprojectName		
        this.contprojectName.setBoundLabelText(resHelper.getString("contprojectName.boundLabelText"));		
        this.contprojectName.setBoundLabelLength(100);		
        this.contprojectName.setBoundLabelUnderline(true);		
        this.contprojectName.setVisible(true);
        // contprojectId		
        this.contprojectId.setBoundLabelText(resHelper.getString("contprojectId.boundLabelText"));		
        this.contprojectId.setBoundLabelLength(100);		
        this.contprojectId.setBoundLabelUnderline(true);		
        this.contprojectId.setVisible(false);
        // contversion		
        this.contversion.setBoundLabelText(resHelper.getString("contversion.boundLabelText"));		
        this.contversion.setBoundLabelLength(100);		
        this.contversion.setBoundLabelUnderline(true);		
        this.contversion.setVisible(true);
        // chkisLatest		
        this.chkisLatest.setText(resHelper.getString("chkisLatest.text"));		
        this.chkisLatest.setHorizontalAlignment(2);		
        this.chkisLatest.setVisible(false);
        // kDTabbedPane1
        // contpointBillStatus		
        this.contpointBillStatus.setBoundLabelText(resHelper.getString("contpointBillStatus.boundLabelText"));		
        this.contpointBillStatus.setBoundLabelLength(100);		
        this.contpointBillStatus.setBoundLabelUnderline(true);		
        this.contpointBillStatus.setVisible(true);		
        this.contpointBillStatus.setEnabled(false);
        // contbeizhu		
        this.contbeizhu.setBoundLabelText(resHelper.getString("contbeizhu.boundLabelText"));		
        this.contbeizhu.setBoundLabelLength(100);		
        this.contbeizhu.setBoundLabelUnderline(true);		
        this.contbeizhu.setVisible(true);
        // contaudiTime		
        this.contaudiTime.setBoundLabelText(resHelper.getString("contaudiTime.boundLabelText"));		
        this.contaudiTime.setBoundLabelLength(100);		
        this.contaudiTime.setBoundLabelUnderline(true);		
        this.contaudiTime.setVisible(true);
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
        this.pkBizDate.setEnabled(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // txtprojectName		
        this.txtprojectName.setHorizontalAlignment(2);		
        this.txtprojectName.setMaxLength(200);		
        this.txtprojectName.setRequired(false);		
        this.txtprojectName.setEnabled(false);
        // txtprojectId		
        this.txtprojectId.setHorizontalAlignment(2);		
        this.txtprojectId.setMaxLength(50);		
        this.txtprojectId.setRequired(false);
        // txtversion		
        this.txtversion.setHorizontalAlignment(2);		
        this.txtversion.setDataType(0);		
        this.txtversion.setSupportedEmpty(true);		
        this.txtversion.setRequired(false);		
        this.txtversion.setEnabled(false);
        // kdcBase		
        this.kdcBase.setTitle(resHelper.getString("kdcBase.title"));
        // kdcSingle		
        this.kdcSingle.setTitle(resHelper.getString("kdcSingle.title"));
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"pointName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"pointValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"baseUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isCombo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"productType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{pointName}</t:Cell><t:Cell>$Resource{pointValue}</t:Cell><t:Cell>$Resource{baseUnit}</t:Cell><t:Cell>$Resource{isCombo}</t:Cell><t:Cell>$Resource{productType}</t:Cell><t:Cell>$Resource{buildNo}</t:Cell><t:Cell>$Resource{isModel}</t:Cell><t:Cell>$Resource{buildValue}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));

                this.kdtEntrys.putBindContents("editData",new String[] {"id","pointName","pointValue","baseUnit","isCombo","productType","buildNo","isModel","buildValue","beizhu"});


        this.kdtEntrys.checkParsed();
        KDTextField kdtEntrys_pointName_TextField = new KDTextField();
        kdtEntrys_pointName_TextField.setName("kdtEntrys_pointName_TextField");
        kdtEntrys_pointName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_pointName_CellEditor = new KDTDefaultCellEditor(kdtEntrys_pointName_TextField);
        this.kdtEntrys.getColumn("pointName").setEditor(kdtEntrys_pointName_CellEditor);
        KDFormattedTextField kdtEntrys_pointValue_TextField = new KDFormattedTextField();
        kdtEntrys_pointValue_TextField.setName("kdtEntrys_pointValue_TextField");
        kdtEntrys_pointValue_TextField.setVisible(true);
        kdtEntrys_pointValue_TextField.setEditable(true);
        kdtEntrys_pointValue_TextField.setHorizontalAlignment(2);
        kdtEntrys_pointValue_TextField.setDataType(1);
        	kdtEntrys_pointValue_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntrys_pointValue_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntrys_pointValue_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntrys_pointValue_CellEditor = new KDTDefaultCellEditor(kdtEntrys_pointValue_TextField);
        this.kdtEntrys.getColumn("pointValue").setEditor(kdtEntrys_pointValue_CellEditor);
        final KDBizPromptBox kdtEntrys_baseUnit_PromptBox = new KDBizPromptBox();
        kdtEntrys_baseUnit_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
        kdtEntrys_baseUnit_PromptBox.setVisible(true);
        kdtEntrys_baseUnit_PromptBox.setEditable(true);
        kdtEntrys_baseUnit_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_baseUnit_PromptBox.setEditFormat("$number$");
        kdtEntrys_baseUnit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_baseUnit_CellEditor = new KDTDefaultCellEditor(kdtEntrys_baseUnit_PromptBox);
        this.kdtEntrys.getColumn("baseUnit").setEditor(kdtEntrys_baseUnit_CellEditor);
        ObjectValueRender kdtEntrys_baseUnit_OVR = new ObjectValueRender();
        kdtEntrys_baseUnit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("baseUnit").setRenderer(kdtEntrys_baseUnit_OVR);
        KDCheckBox kdtEntrys_isCombo_CheckBox = new KDCheckBox();
        kdtEntrys_isCombo_CheckBox.setName("kdtEntrys_isCombo_CheckBox");
        KDTDefaultCellEditor kdtEntrys_isCombo_CellEditor = new KDTDefaultCellEditor(kdtEntrys_isCombo_CheckBox);
        this.kdtEntrys.getColumn("isCombo").setEditor(kdtEntrys_isCombo_CellEditor);
        final KDBizPromptBox kdtEntrys_productType_PromptBox = new KDBizPromptBox();
        kdtEntrys_productType_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
        kdtEntrys_productType_PromptBox.setVisible(true);
        kdtEntrys_productType_PromptBox.setEditable(true);
        kdtEntrys_productType_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_productType_PromptBox.setEditFormat("$number$");
        kdtEntrys_productType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_productType_CellEditor = new KDTDefaultCellEditor(kdtEntrys_productType_PromptBox);
        this.kdtEntrys.getColumn("productType").setEditor(kdtEntrys_productType_CellEditor);
        ObjectValueRender kdtEntrys_productType_OVR = new ObjectValueRender();
        kdtEntrys_productType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("productType").setRenderer(kdtEntrys_productType_OVR);
        final KDBizPromptBox kdtEntrys_buildNo_PromptBox = new KDBizPromptBox();
        kdtEntrys_buildNo_PromptBox.setQueryInfo("com.kingdee.eas.fdc.costindexdb.database.app.BuildNumberQuery");
        kdtEntrys_buildNo_PromptBox.setVisible(true);
        kdtEntrys_buildNo_PromptBox.setEditable(true);
        kdtEntrys_buildNo_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_buildNo_PromptBox.setEditFormat("$number$");
        kdtEntrys_buildNo_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_buildNo_CellEditor = new KDTDefaultCellEditor(kdtEntrys_buildNo_PromptBox);
        this.kdtEntrys.getColumn("buildNo").setEditor(kdtEntrys_buildNo_CellEditor);
        ObjectValueRender kdtEntrys_buildNo_OVR = new ObjectValueRender();
        kdtEntrys_buildNo_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("buildNo").setRenderer(kdtEntrys_buildNo_OVR);
        KDCheckBox kdtEntrys_isModel_CheckBox = new KDCheckBox();
        kdtEntrys_isModel_CheckBox.setName("kdtEntrys_isModel_CheckBox");
        KDTDefaultCellEditor kdtEntrys_isModel_CellEditor = new KDTDefaultCellEditor(kdtEntrys_isModel_CheckBox);
        this.kdtEntrys.getColumn("isModel").setEditor(kdtEntrys_isModel_CellEditor);
        KDFormattedTextField kdtEntrys_buildValue_TextField = new KDFormattedTextField();
        kdtEntrys_buildValue_TextField.setName("kdtEntrys_buildValue_TextField");
        kdtEntrys_buildValue_TextField.setVisible(true);
        kdtEntrys_buildValue_TextField.setEditable(true);
        kdtEntrys_buildValue_TextField.setHorizontalAlignment(2);
        kdtEntrys_buildValue_TextField.setDataType(1);
        	kdtEntrys_buildValue_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntrys_buildValue_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntrys_buildValue_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntrys_buildValue_CellEditor = new KDTDefaultCellEditor(kdtEntrys_buildValue_TextField);
        this.kdtEntrys.getColumn("buildValue").setEditor(kdtEntrys_buildValue_CellEditor);
        KDTextArea kdtEntrys_beizhu_TextArea = new KDTextArea();
        kdtEntrys_beizhu_TextArea.setName("kdtEntrys_beizhu_TextArea");
        kdtEntrys_beizhu_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrys_beizhu_CellEditor = new KDTDefaultCellEditor(kdtEntrys_beizhu_TextArea);
        this.kdtEntrys.getColumn("beizhu").setEditor(kdtEntrys_beizhu_CellEditor);
        // kdtEcost
		String kdtEcostStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"pointName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"pointValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"baseUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isCombo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"productType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{pointName}</t:Cell><t:Cell>$Resource{pointValue}</t:Cell><t:Cell>$Resource{baseUnit}</t:Cell><t:Cell>$Resource{isCombo}</t:Cell><t:Cell>$Resource{productType}</t:Cell><t:Cell>$Resource{buildNo}</t:Cell><t:Cell>$Resource{isModel}</t:Cell><t:Cell>$Resource{buildValue}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEcost.setFormatXml(resHelper.translateString("kdtEcost",kdtEcostStrXML));

                this.kdtEcost.putBindContents("editData",new String[] {"seq","costAccount","pointName","pointValue","baseUnit","isCombo","productType","buildNo","isModel","buildValue","beizhu"});


        this.kdtEcost.checkParsed();
        final KDBizPromptBox kdtEcost_costAccount_PromptBox = new KDBizPromptBox();
        kdtEcost_costAccount_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
        kdtEcost_costAccount_PromptBox.setVisible(true);
        kdtEcost_costAccount_PromptBox.setEditable(true);
        kdtEcost_costAccount_PromptBox.setDisplayFormat("$number$");
        kdtEcost_costAccount_PromptBox.setEditFormat("$number$");
        kdtEcost_costAccount_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEcost_costAccount_CellEditor = new KDTDefaultCellEditor(kdtEcost_costAccount_PromptBox);
        this.kdtEcost.getColumn("costAccount").setEditor(kdtEcost_costAccount_CellEditor);
        ObjectValueRender kdtEcost_costAccount_OVR = new ObjectValueRender();
        kdtEcost_costAccount_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEcost.getColumn("costAccount").setRenderer(kdtEcost_costAccount_OVR);
        KDTextField kdtEcost_pointName_TextField = new KDTextField();
        kdtEcost_pointName_TextField.setName("kdtEcost_pointName_TextField");
        kdtEcost_pointName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEcost_pointName_CellEditor = new KDTDefaultCellEditor(kdtEcost_pointName_TextField);
        this.kdtEcost.getColumn("pointName").setEditor(kdtEcost_pointName_CellEditor);
        KDFormattedTextField kdtEcost_pointValue_TextField = new KDFormattedTextField();
        kdtEcost_pointValue_TextField.setName("kdtEcost_pointValue_TextField");
        kdtEcost_pointValue_TextField.setVisible(true);
        kdtEcost_pointValue_TextField.setEditable(true);
        kdtEcost_pointValue_TextField.setHorizontalAlignment(2);
        kdtEcost_pointValue_TextField.setDataType(1);
        	kdtEcost_pointValue_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEcost_pointValue_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEcost_pointValue_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEcost_pointValue_CellEditor = new KDTDefaultCellEditor(kdtEcost_pointValue_TextField);
        this.kdtEcost.getColumn("pointValue").setEditor(kdtEcost_pointValue_CellEditor);
        final KDBizPromptBox kdtEcost_baseUnit_PromptBox = new KDBizPromptBox();
        kdtEcost_baseUnit_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
        kdtEcost_baseUnit_PromptBox.setVisible(true);
        kdtEcost_baseUnit_PromptBox.setEditable(true);
        kdtEcost_baseUnit_PromptBox.setDisplayFormat("$number$");
        kdtEcost_baseUnit_PromptBox.setEditFormat("$number$");
        kdtEcost_baseUnit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEcost_baseUnit_CellEditor = new KDTDefaultCellEditor(kdtEcost_baseUnit_PromptBox);
        this.kdtEcost.getColumn("baseUnit").setEditor(kdtEcost_baseUnit_CellEditor);
        ObjectValueRender kdtEcost_baseUnit_OVR = new ObjectValueRender();
        kdtEcost_baseUnit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEcost.getColumn("baseUnit").setRenderer(kdtEcost_baseUnit_OVR);
        KDCheckBox kdtEcost_isCombo_CheckBox = new KDCheckBox();
        kdtEcost_isCombo_CheckBox.setName("kdtEcost_isCombo_CheckBox");
        KDTDefaultCellEditor kdtEcost_isCombo_CellEditor = new KDTDefaultCellEditor(kdtEcost_isCombo_CheckBox);
        this.kdtEcost.getColumn("isCombo").setEditor(kdtEcost_isCombo_CellEditor);
        final KDBizPromptBox kdtEcost_productType_PromptBox = new KDBizPromptBox();
        kdtEcost_productType_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
        kdtEcost_productType_PromptBox.setVisible(true);
        kdtEcost_productType_PromptBox.setEditable(true);
        kdtEcost_productType_PromptBox.setDisplayFormat("$number$");
        kdtEcost_productType_PromptBox.setEditFormat("$number$");
        kdtEcost_productType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEcost_productType_CellEditor = new KDTDefaultCellEditor(kdtEcost_productType_PromptBox);
        this.kdtEcost.getColumn("productType").setEditor(kdtEcost_productType_CellEditor);
        ObjectValueRender kdtEcost_productType_OVR = new ObjectValueRender();
        kdtEcost_productType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEcost.getColumn("productType").setRenderer(kdtEcost_productType_OVR);
        final KDBizPromptBox kdtEcost_buildNo_PromptBox = new KDBizPromptBox();
        kdtEcost_buildNo_PromptBox.setQueryInfo("com.kingdee.eas.fdc.costindexdb.database.app.BuildNumberQuery");
        kdtEcost_buildNo_PromptBox.setVisible(true);
        kdtEcost_buildNo_PromptBox.setEditable(true);
        kdtEcost_buildNo_PromptBox.setDisplayFormat("$number$");
        kdtEcost_buildNo_PromptBox.setEditFormat("$number$");
        kdtEcost_buildNo_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEcost_buildNo_CellEditor = new KDTDefaultCellEditor(kdtEcost_buildNo_PromptBox);
        this.kdtEcost.getColumn("buildNo").setEditor(kdtEcost_buildNo_CellEditor);
        ObjectValueRender kdtEcost_buildNo_OVR = new ObjectValueRender();
        kdtEcost_buildNo_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEcost.getColumn("buildNo").setRenderer(kdtEcost_buildNo_OVR);
        KDCheckBox kdtEcost_isModel_CheckBox = new KDCheckBox();
        kdtEcost_isModel_CheckBox.setName("kdtEcost_isModel_CheckBox");
        KDTDefaultCellEditor kdtEcost_isModel_CellEditor = new KDTDefaultCellEditor(kdtEcost_isModel_CheckBox);
        this.kdtEcost.getColumn("isModel").setEditor(kdtEcost_isModel_CellEditor);
        KDFormattedTextField kdtEcost_buildValue_TextField = new KDFormattedTextField();
        kdtEcost_buildValue_TextField.setName("kdtEcost_buildValue_TextField");
        kdtEcost_buildValue_TextField.setVisible(true);
        kdtEcost_buildValue_TextField.setEditable(true);
        kdtEcost_buildValue_TextField.setHorizontalAlignment(2);
        kdtEcost_buildValue_TextField.setDataType(1);
        	kdtEcost_buildValue_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEcost_buildValue_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEcost_buildValue_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEcost_buildValue_CellEditor = new KDTDefaultCellEditor(kdtEcost_buildValue_TextField);
        this.kdtEcost.getColumn("buildValue").setEditor(kdtEcost_buildValue_CellEditor);
        KDTextArea kdtEcost_beizhu_TextArea = new KDTextArea();
        kdtEcost_beizhu_TextArea.setName("kdtEcost_beizhu_TextArea");
        kdtEcost_beizhu_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtEcost_beizhu_CellEditor = new KDTDefaultCellEditor(kdtEcost_beizhu_TextArea);
        this.kdtEcost.getColumn("beizhu").setEditor(kdtEcost_beizhu_CellEditor);
        // pointBillStatus		
        this.pointBillStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.pointBillStatus.setRequired(false);		
        this.pointBillStatus.setEnabled(false);
        // scrollPanebeizhu
        // txtbeizhu		
        this.txtbeizhu.setRequired(false);		
        this.txtbeizhu.setMaxLength(255);
        // pkaudiTime		
        this.pkaudiTime.setRequired(false);		
        this.pkaudiTime.setEnabled(false);
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btnUNAudit
        this.btnUNAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAdudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUNAudit.setText(resHelper.getString("btnUNAudit.text"));		
        this.btnUNAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnFix
        this.btnFix.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefix, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFix.setText(resHelper.getString("btnFix.text"));		
        this.btnFix.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_emend"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtNumber,pkBizDate,txtDescription,prmtAuditor,prmtCreator,kDDateCreateTime,prmtLastUpdateUser,kDDateLastUpdateTime,txtprojectName,txtprojectId,txtversion,chkisLatest,pointBillStatus,txtbeizhu,pkaudiTime,kdtEcost,kdtEntrys}));
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
        contCreator.setBounds(new Rectangle(374, 568, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(374, 568, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(373, 594, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(373, 594, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(734, 568, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(734, 568, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateTime.setBounds(new Rectangle(734, 594, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(734, 594, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(11, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(11, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(373, 10, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(373, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(758, 38, 142, 19));
        this.add(contDescription, new KDLayout.Constraints(758, 38, 142, 19, 0));
        contAuditor.setBounds(new Rectangle(13, 568, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(13, 568, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contprojectName.setBounds(new Rectangle(11, 36, 270, 19));
        this.add(contprojectName, new KDLayout.Constraints(11, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contprojectId.setBounds(new Rectangle(891, 39, 117, 19));
        this.add(contprojectId, new KDLayout.Constraints(891, 39, 117, 19, 0));
        contversion.setBounds(new Rectangle(373, 36, 270, 19));
        this.add(contversion, new KDLayout.Constraints(373, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkisLatest.setBounds(new Rectangle(700, 42, 86, 19));
        this.add(chkisLatest, new KDLayout.Constraints(700, 42, 86, 19, 0));
        kDTabbedPane1.setBounds(new Rectangle(11, 118, 992, 437));
        this.add(kDTabbedPane1, new KDLayout.Constraints(11, 118, 992, 437, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contpointBillStatus.setBounds(new Rectangle(735, 10, 270, 19));
        this.add(contpointBillStatus, new KDLayout.Constraints(735, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contbeizhu.setBounds(new Rectangle(11, 64, 993, 42));
        this.add(contbeizhu, new KDLayout.Constraints(11, 64, 993, 42, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contaudiTime.setBounds(new Rectangle(13, 594, 270, 19));
        this.add(contaudiTime, new KDLayout.Constraints(13, 594, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
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
        //contprojectName
        contprojectName.setBoundEditor(txtprojectName);
        //contprojectId
        contprojectId.setBoundEditor(txtprojectId);
        //contversion
        contversion.setBoundEditor(txtversion);
        //kDTabbedPane1
        kDTabbedPane1.add(kdcBase, resHelper.getString("kdcBase.constraints"));
        kDTabbedPane1.add(kdcSingle, resHelper.getString("kdcSingle.constraints"));
        //kdcBase
kdcBase.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEntryInfo(),null,false);
        kdcBase.getContentPane().add(kdtEntrys_detailPanel, BorderLayout.CENTER);
        //kdcSingle
kdcSingle.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEcost_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEcost,new com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEcostInfo(),null,false);
        kdcSingle.getContentPane().add(kdtEcost_detailPanel, BorderLayout.CENTER);
        //contpointBillStatus
        contpointBillStatus.setBoundEditor(pointBillStatus);
        //contbeizhu
        contbeizhu.setBoundEditor(scrollPanebeizhu);
        //scrollPanebeizhu
        scrollPanebeizhu.getViewport().add(txtbeizhu, null);
        //contaudiTime
        contaudiTime.setBoundEditor(pkaudiTime);

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
        this.toolBar.add(btnUNAudit);
        this.toolBar.add(btnFix);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isLatest", boolean.class, this.chkisLatest, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("projectName", String.class, this.txtprojectName, "text");
		dataBinder.registerBinding("projectId", String.class, this.txtprojectId, "text");
		dataBinder.registerBinding("version", int.class, this.txtversion, "value");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.pointName", String.class, this.kdtEntrys, "pointName.text");
		dataBinder.registerBinding("entrys.pointValue", java.math.BigDecimal.class, this.kdtEntrys, "pointValue.text");
		dataBinder.registerBinding("entrys.baseUnit", java.lang.Object.class, this.kdtEntrys, "baseUnit.text");
		dataBinder.registerBinding("entrys.isCombo", boolean.class, this.kdtEntrys, "isCombo.text");
		dataBinder.registerBinding("entrys.productType", java.lang.Object.class, this.kdtEntrys, "productType.text");
		dataBinder.registerBinding("entrys.isModel", boolean.class, this.kdtEntrys, "isModel.text");
		dataBinder.registerBinding("entrys.buildValue", java.math.BigDecimal.class, this.kdtEntrys, "buildValue.text");
		dataBinder.registerBinding("entrys.beizhu", String.class, this.kdtEntrys, "beizhu.text");
		dataBinder.registerBinding("entrys.buildNo", java.lang.Object.class, this.kdtEntrys, "buildNo.text");
		dataBinder.registerBinding("Ecost.seq", int.class, this.kdtEcost, "seq.text");
		dataBinder.registerBinding("Ecost", com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEcostInfo.class, this.kdtEcost, "userObject");
		dataBinder.registerBinding("Ecost.costAccount", java.lang.Object.class, this.kdtEcost, "costAccount.text");
		dataBinder.registerBinding("Ecost.pointName", String.class, this.kdtEcost, "pointName.text");
		dataBinder.registerBinding("Ecost.pointValue", java.math.BigDecimal.class, this.kdtEcost, "pointValue.text");
		dataBinder.registerBinding("Ecost.baseUnit", java.lang.Object.class, this.kdtEcost, "baseUnit.text");
		dataBinder.registerBinding("Ecost.isCombo", boolean.class, this.kdtEcost, "isCombo.text");
		dataBinder.registerBinding("Ecost.productType", java.lang.Object.class, this.kdtEcost, "productType.text");
		dataBinder.registerBinding("Ecost.isModel", boolean.class, this.kdtEcost, "isModel.text");
		dataBinder.registerBinding("Ecost.buildValue", java.math.BigDecimal.class, this.kdtEcost, "buildValue.text");
		dataBinder.registerBinding("Ecost.beizhu", String.class, this.kdtEcost, "beizhu.text");
		dataBinder.registerBinding("Ecost.buildNo", java.lang.Object.class, this.kdtEcost, "buildNo.text");
		dataBinder.registerBinding("pointBillStatus", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.pointBillStatus, "selectedItem");
		dataBinder.registerBinding("beizhu", String.class, this.txtbeizhu, "text");
		dataBinder.registerBinding("audiTime", java.util.Date.class, this.pkaudiTime, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.costindexdb.app.BaseAndSinglePointEditUIHandler";
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
        this.txtNumber.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo)ov;
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
		getValidateHelper().registerBindProperty("isLatest", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("version", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.pointName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.pointValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.baseUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.isCombo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.productType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.isModel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.buildValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.buildNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ecost.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ecost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ecost.costAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ecost.pointName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ecost.pointValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ecost.baseUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ecost.isCombo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ecost.productType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ecost.isModel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ecost.buildValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ecost.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Ecost.buildNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pointBillStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("audiTime", ValidateHelper.ON_SAVE);    		
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
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("isLatest"));
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
        sic.add(new SelectorItemInfo("projectName"));
        sic.add(new SelectorItemInfo("projectId"));
        sic.add(new SelectorItemInfo("version"));
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entrys.pointName"));
    	sic.add(new SelectorItemInfo("entrys.pointValue"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.baseUnit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.baseUnit.id"));
			sic.add(new SelectorItemInfo("entrys.baseUnit.name"));
        	sic.add(new SelectorItemInfo("entrys.baseUnit.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.isCombo"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.productType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.productType.id"));
			sic.add(new SelectorItemInfo("entrys.productType.name"));
        	sic.add(new SelectorItemInfo("entrys.productType.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.isModel"));
    	sic.add(new SelectorItemInfo("entrys.buildValue"));
    	sic.add(new SelectorItemInfo("entrys.beizhu"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.buildNo.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.buildNo.id"));
			sic.add(new SelectorItemInfo("entrys.buildNo.name"));
        	sic.add(new SelectorItemInfo("entrys.buildNo.number"));
		}
    	sic.add(new SelectorItemInfo("Ecost.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Ecost.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Ecost.costAccount.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Ecost.costAccount.id"));
			sic.add(new SelectorItemInfo("Ecost.costAccount.name"));
        	sic.add(new SelectorItemInfo("Ecost.costAccount.number"));
		}
    	sic.add(new SelectorItemInfo("Ecost.pointName"));
    	sic.add(new SelectorItemInfo("Ecost.pointValue"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Ecost.baseUnit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Ecost.baseUnit.id"));
			sic.add(new SelectorItemInfo("Ecost.baseUnit.name"));
        	sic.add(new SelectorItemInfo("Ecost.baseUnit.number"));
		}
    	sic.add(new SelectorItemInfo("Ecost.isCombo"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Ecost.productType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Ecost.productType.id"));
			sic.add(new SelectorItemInfo("Ecost.productType.name"));
        	sic.add(new SelectorItemInfo("Ecost.productType.number"));
		}
    	sic.add(new SelectorItemInfo("Ecost.isModel"));
    	sic.add(new SelectorItemInfo("Ecost.buildValue"));
    	sic.add(new SelectorItemInfo("Ecost.beizhu"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Ecost.buildNo.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Ecost.buildNo.id"));
			sic.add(new SelectorItemInfo("Ecost.buildNo.name"));
        	sic.add(new SelectorItemInfo("Ecost.buildNo.number"));
		}
        sic.add(new SelectorItemInfo("pointBillStatus"));
        sic.add(new SelectorItemInfo("beizhu"));
        sic.add(new SelectorItemInfo("audiTime"));
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
        com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointFactory.getRemoteInstance().audit(editData);
    }
    	

    /**
     * output actionUnAdudit_actionPerformed method
     */
    public void actionUnAdudit_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointFactory.getRemoteInstance().unAdudit(editData);
    }
    	

    /**
     * output actionRefix_actionPerformed method
     */
    public void actionRefix_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointFactory.getRemoteInstance().refix(editData);
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
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAdudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAdudit() {
    	return false;
    }
	public RequestContext prepareActionRefix(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefix() {
    	return false;
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
            innerActionPerformed("eas", AbstractBaseAndSinglePointEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAdudit class
     */     
    protected class ActionUnAdudit extends ItemAction {     
    
        public ActionUnAdudit()
        {
            this(null);
        }

        public ActionUnAdudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnAdudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAdudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAdudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseAndSinglePointEditUI.this, "ActionUnAdudit", "actionUnAdudit_actionPerformed", e);
        }
    }

    /**
     * output ActionRefix class
     */     
    protected class ActionRefix extends ItemAction {     
    
        public ActionRefix()
        {
            this(null);
        }

        public ActionRefix(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRefix.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefix.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefix.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBaseAndSinglePointEditUI.this, "ActionRefix", "actionRefix_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.costindexdb.client", "BaseAndSinglePointEditUI");
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
        return com.kingdee.eas.fdc.costindexdb.client.BaseAndSinglePointEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo objectValue = new com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/costindexdb/BaseAndSinglePoint";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.costindexdb.app.BaseAndSinglePointQuery");
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
		vo.put("pointBillStatus","1SAVED");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}