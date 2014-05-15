/**
 * output package name
 */
package com.kingdee.eas.port.pm.contract.client;

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
public abstract class AbstractContractChangeSettleBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractChangeSettleBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOriginalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsFinish;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contResponsibleStyle;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReasonDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contColseDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeReson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReportAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAllowAmount;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractBillNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrigDeductAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOriginalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboResponsibleStyle;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLastAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtReasonDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtColseDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtChangeReson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractBill;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtReportAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAllowAmount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConBillNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOrigDeductAmount;
    protected com.kingdee.eas.port.pm.contract.ContractChangeSettleBillInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractContractChangeSettleBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractChangeSettleBillEditUI.class.getName());
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
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOriginalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsFinish = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contResponsibleStyle = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReasonDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contColseDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeReson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReportAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAllowAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contContractBillNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrigDeductAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOriginalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboResponsibleStyle = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtLastAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtReasonDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtColseDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtChangeReson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtSupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtContractBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtReportAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAllowAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtConBillNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOrigDeductAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contAuditor.setName("contAuditor");
        this.contOrgUnit.setName("contOrgUnit");
        this.contName.setName("contName");
        this.contOriginalAmount.setName("contOriginalAmount");
        this.contAmount.setName("contAmount");
        this.contAuditTime.setName("contAuditTime");
        this.chkIsFinish.setName("chkIsFinish");
        this.contResponsibleStyle.setName("contResponsibleStyle");
        this.contLastAmount.setName("contLastAmount");
        this.contReasonDescription.setName("contReasonDescription");
        this.contColseDescription.setName("contColseDescription");
        this.contChangeReson.setName("contChangeReson");
        this.contSupplier.setName("contSupplier");
        this.contContractBill.setName("contContractBill");
        this.contCurProject.setName("contCurProject");
        this.contReportAmount.setName("contReportAmount");
        this.contAllowAmount.setName("contAllowAmount");
        this.kDContainer1.setName("kDContainer1");
        this.contContractBillNumber.setName("contContractBillNumber");
        this.contOrigDeductAmount.setName("contOrigDeductAmount");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.txtName.setName("txtName");
        this.txtOriginalAmount.setName("txtOriginalAmount");
        this.txtAmount.setName("txtAmount");
        this.pkAuditTime.setName("pkAuditTime");
        this.comboResponsibleStyle.setName("comboResponsibleStyle");
        this.txtLastAmount.setName("txtLastAmount");
        this.txtReasonDescription.setName("txtReasonDescription");
        this.txtColseDescription.setName("txtColseDescription");
        this.txtChangeReson.setName("txtChangeReson");
        this.prmtSupplier.setName("prmtSupplier");
        this.prmtContractBill.setName("prmtContractBill");
        this.prmtCurProject.setName("prmtCurProject");
        this.txtReportAmount.setName("txtReportAmount");
        this.txtAllowAmount.setName("txtAllowAmount");
        this.kdtEntrys.setName("kdtEntrys");
        this.txtConBillNumber.setName("txtConBillNumber");
        this.txtOrigDeductAmount.setName("txtOrigDeductAmount");
        // CoreUI		
        this.setPreferredSize(new Dimension(920,560));		
        this.btnAddNew.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnUnAudit.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);		
        this.contOrgUnit.setEnabled(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contOriginalAmount		
        this.contOriginalAmount.setBoundLabelText(resHelper.getString("contOriginalAmount.boundLabelText"));		
        this.contOriginalAmount.setBoundLabelLength(100);		
        this.contOriginalAmount.setBoundLabelUnderline(true);		
        this.contOriginalAmount.setEnabled(false);
        // contAmount		
        this.contAmount.setBoundLabelText(resHelper.getString("contAmount.boundLabelText"));		
        this.contAmount.setBoundLabelLength(100);		
        this.contAmount.setBoundLabelUnderline(true);		
        this.contAmount.setEnabled(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);		
        this.contAuditTime.setEnabled(false);
        // chkIsFinish		
        this.chkIsFinish.setText(resHelper.getString("chkIsFinish.text"));
        // contResponsibleStyle		
        this.contResponsibleStyle.setBoundLabelText(resHelper.getString("contResponsibleStyle.boundLabelText"));		
        this.contResponsibleStyle.setBoundLabelLength(100);		
        this.contResponsibleStyle.setBoundLabelUnderline(true);
        // contLastAmount		
        this.contLastAmount.setBoundLabelText(resHelper.getString("contLastAmount.boundLabelText"));		
        this.contLastAmount.setBoundLabelLength(100);		
        this.contLastAmount.setBoundLabelUnderline(true);		
        this.contLastAmount.setEnabled(false);
        // contReasonDescription		
        this.contReasonDescription.setBoundLabelText(resHelper.getString("contReasonDescription.boundLabelText"));		
        this.contReasonDescription.setBoundLabelLength(100);		
        this.contReasonDescription.setBoundLabelUnderline(true);
        // contColseDescription		
        this.contColseDescription.setBoundLabelText(resHelper.getString("contColseDescription.boundLabelText"));		
        this.contColseDescription.setBoundLabelLength(100);		
        this.contColseDescription.setBoundLabelUnderline(true);
        // contChangeReson		
        this.contChangeReson.setBoundLabelText(resHelper.getString("contChangeReson.boundLabelText"));		
        this.contChangeReson.setBoundLabelLength(100);		
        this.contChangeReson.setBoundLabelUnderline(true);		
        this.contChangeReson.setEnabled(false);
        // contSupplier		
        this.contSupplier.setBoundLabelText(resHelper.getString("contSupplier.boundLabelText"));		
        this.contSupplier.setBoundLabelLength(100);		
        this.contSupplier.setBoundLabelUnderline(true);		
        this.contSupplier.setEnabled(false);
        // contContractBill		
        this.contContractBill.setBoundLabelText(resHelper.getString("contContractBill.boundLabelText"));		
        this.contContractBill.setBoundLabelLength(100);		
        this.contContractBill.setBoundLabelUnderline(true);		
        this.contContractBill.setEnabled(false);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);		
        this.contCurProject.setEnabled(false);
        // contReportAmount		
        this.contReportAmount.setBoundLabelText(resHelper.getString("contReportAmount.boundLabelText"));		
        this.contReportAmount.setBoundLabelLength(100);		
        this.contReportAmount.setBoundLabelUnderline(true);
        // contAllowAmount		
        this.contAllowAmount.setBoundLabelText(resHelper.getString("contAllowAmount.boundLabelText"));		
        this.contAllowAmount.setBoundLabelLength(100);		
        this.contAllowAmount.setBoundLabelUnderline(true);		
        this.contAllowAmount.setEnabled(false);
        // kDContainer1
        // contContractBillNumber		
        this.contContractBillNumber.setBoundLabelText(resHelper.getString("contContractBillNumber.boundLabelText"));		
        this.contContractBillNumber.setEnabled(false);		
        this.contContractBillNumber.setBoundLabelUnderline(true);		
        this.contContractBillNumber.setBoundLabelLength(100);
        // contOrigDeductAmount		
        this.contOrigDeductAmount.setBoundLabelText(resHelper.getString("contOrigDeductAmount.boundLabelText"));		
        this.contOrigDeductAmount.setBoundLabelLength(100);		
        this.contOrigDeductAmount.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // prmtOrgUnit		
        this.prmtOrgUnit.setEnabled(false);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // txtOriginalAmount		
        this.txtOriginalAmount.setEnabled(false);		
        this.txtOriginalAmount.setDataType(1);		
        this.txtOriginalAmount.setPrecision(2);
        // txtAmount		
        this.txtAmount.setEnabled(false);		
        this.txtAmount.setDataType(1);		
        this.txtAmount.setPrecision(2);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // comboResponsibleStyle		
        this.comboResponsibleStyle.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.ResponsibleStyleEnum").toArray());		
        this.comboResponsibleStyle.setRequired(true);
        // txtLastAmount		
        this.txtLastAmount.setDataType(1);		
        this.txtLastAmount.setEnabled(false);		
        this.txtLastAmount.setPrecision(2);
        // txtReasonDescription		
        this.txtReasonDescription.setRequired(true);		
        this.txtReasonDescription.setMaxLength(255);
        // txtColseDescription		
        this.txtColseDescription.setRequired(true);		
        this.txtColseDescription.setMaxLength(255);
        // txtChangeReson		
        this.txtChangeReson.setMaxLength(500);		
        this.txtChangeReson.setEnabled(false);
        // prmtSupplier		
        this.prmtSupplier.setEnabled(false);
        // prmtContractBill		
        this.prmtContractBill.setEnabled(false);		
        this.prmtContractBill.setDisplayFormat("$name$");		
        this.prmtContractBill.setEditFormat("$name$");		
        this.prmtContractBill.setCommitFormat("$name$");
        // prmtCurProject		
        this.prmtCurProject.setEnabled(false);
        // txtReportAmount		
        this.txtReportAmount.setDataType(1);		
        this.txtReportAmount.setPrecision(2);		
        this.txtReportAmount.setRequired(true);
        // txtAllowAmount		
        this.txtAllowAmount.setDataType(1);		
        this.txtAllowAmount.setPrecision(2);		
        this.txtAllowAmount.setEnabled(false);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"numer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"changeContent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"proNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"totalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{numer}</t:Cell><t:Cell>$Resource{changeContent}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{proNumber}</t:Cell><t:Cell>$Resource{totalAmount}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
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

                this.kdtEntrys.putBindContents("editData",new String[] {"numer","changeContent","unit","amount","proNumber","totalAmount","remark"});


        this.kdtEntrys.checkParsed();
        KDTextField kdtEntrys_numer_TextField = new KDTextField();
        kdtEntrys_numer_TextField.setName("kdtEntrys_numer_TextField");
        kdtEntrys_numer_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrys_numer_CellEditor = new KDTDefaultCellEditor(kdtEntrys_numer_TextField);
        this.kdtEntrys.getColumn("numer").setEditor(kdtEntrys_numer_CellEditor);
        KDTextField kdtEntrys_changeContent_TextField = new KDTextField();
        kdtEntrys_changeContent_TextField.setName("kdtEntrys_changeContent_TextField");
        kdtEntrys_changeContent_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrys_changeContent_CellEditor = new KDTDefaultCellEditor(kdtEntrys_changeContent_TextField);
        this.kdtEntrys.getColumn("changeContent").setEditor(kdtEntrys_changeContent_CellEditor);
        KDTextField kdtEntrys_unit_TextField = new KDTextField();
        kdtEntrys_unit_TextField.setName("kdtEntrys_unit_TextField");
        kdtEntrys_unit_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_unit_CellEditor = new KDTDefaultCellEditor(kdtEntrys_unit_TextField);
        this.kdtEntrys.getColumn("unit").setEditor(kdtEntrys_unit_CellEditor);
        KDFormattedTextField kdtEntrys_amount_TextField = new KDFormattedTextField();
        kdtEntrys_amount_TextField.setName("kdtEntrys_amount_TextField");
        kdtEntrys_amount_TextField.setVisible(true);
        kdtEntrys_amount_TextField.setEditable(true);
        kdtEntrys_amount_TextField.setHorizontalAlignment(2);
        kdtEntrys_amount_TextField.setDataType(1);
        	kdtEntrys_amount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntrys_amount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntrys_amount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_amount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_amount_TextField);
        this.kdtEntrys.getColumn("amount").setEditor(kdtEntrys_amount_CellEditor);
        KDFormattedTextField kdtEntrys_proNumber_TextField = new KDFormattedTextField();
        kdtEntrys_proNumber_TextField.setName("kdtEntrys_proNumber_TextField");
        kdtEntrys_proNumber_TextField.setVisible(true);
        kdtEntrys_proNumber_TextField.setEditable(true);
        kdtEntrys_proNumber_TextField.setHorizontalAlignment(2);
        kdtEntrys_proNumber_TextField.setDataType(1);
        	kdtEntrys_proNumber_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntrys_proNumber_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntrys_proNumber_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_proNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_proNumber_TextField);
        this.kdtEntrys.getColumn("proNumber").setEditor(kdtEntrys_proNumber_CellEditor);
        KDFormattedTextField kdtEntrys_totalAmount_TextField = new KDFormattedTextField();
        kdtEntrys_totalAmount_TextField.setName("kdtEntrys_totalAmount_TextField");
        kdtEntrys_totalAmount_TextField.setVisible(true);
        kdtEntrys_totalAmount_TextField.setEditable(true);
        kdtEntrys_totalAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_totalAmount_TextField.setDataType(1);
        	kdtEntrys_totalAmount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntrys_totalAmount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntrys_totalAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_totalAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_totalAmount_TextField);
        this.kdtEntrys.getColumn("totalAmount").setEditor(kdtEntrys_totalAmount_CellEditor);
        KDTextField kdtEntrys_remark_TextField = new KDTextField();
        kdtEntrys_remark_TextField.setName("kdtEntrys_remark_TextField");
        kdtEntrys_remark_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrys_remark_CellEditor = new KDTDefaultCellEditor(kdtEntrys_remark_TextField);
        this.kdtEntrys.getColumn("remark").setEditor(kdtEntrys_remark_CellEditor);
        // txtConBillNumber		
        this.txtConBillNumber.setEnabled(false);
        // txtOrigDeductAmount		
        this.txtOrigDeductAmount.setPrecision(2);		
        this.txtOrigDeductAmount.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 920, 461));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 920, 461));
        contCreator.setBounds(new Rectangle(10, 418, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 418, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(310, 418, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(310, 418, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(610, 394, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(610, 394, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgUnit.setBounds(new Rectangle(610, 34, 270, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(610, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contName.setBounds(new Rectangle(310, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(310, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contOriginalAmount.setBounds(new Rectangle(610, 58, 270, 19));
        this.add(contOriginalAmount, new KDLayout.Constraints(610, 58, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAmount.setBounds(new Rectangle(310, 58, 270, 19));
        this.add(contAmount, new KDLayout.Constraints(310, 58, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(610, 418, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(610, 418, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsFinish.setBounds(new Rectangle(310, 82, 140, 19));
        this.add(chkIsFinish, new KDLayout.Constraints(310, 82, 140, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contResponsibleStyle.setBounds(new Rectangle(10, 82, 270, 19));
        this.add(contResponsibleStyle, new KDLayout.Constraints(10, 82, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastAmount.setBounds(new Rectangle(10, 58, 270, 19));
        this.add(contLastAmount, new KDLayout.Constraints(10, 58, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contReasonDescription.setBounds(new Rectangle(610, 106, 270, 66));
        this.add(contReasonDescription, new KDLayout.Constraints(610, 106, 270, 66, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contColseDescription.setBounds(new Rectangle(9, 106, 571, 73));
        this.add(contColseDescription, new KDLayout.Constraints(9, 106, 571, 73, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contChangeReson.setBounds(new Rectangle(610, 82, 270, 19));
        this.add(contChangeReson, new KDLayout.Constraints(610, 82, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contSupplier.setBounds(new Rectangle(10, 202, 270, 19));
        this.add(contSupplier, new KDLayout.Constraints(10, 202, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contContractBill.setBounds(new Rectangle(10, 34, 270, 19));
        this.add(contContractBill, new KDLayout.Constraints(10, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurProject.setBounds(new Rectangle(610, 10, 270, 19));
        this.add(contCurProject, new KDLayout.Constraints(610, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contReportAmount.setBounds(new Rectangle(10, 394, 270, 19));
        this.add(contReportAmount, new KDLayout.Constraints(10, 394, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAllowAmount.setBounds(new Rectangle(310, 394, 270, 19));
        this.add(contAllowAmount, new KDLayout.Constraints(310, 394, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer1.setBounds(new Rectangle(10, 226, 890, 157));
        this.add(kDContainer1, new KDLayout.Constraints(10, 226, 890, 157, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contContractBillNumber.setBounds(new Rectangle(310, 34, 270, 19));
        this.add(contContractBillNumber, new KDLayout.Constraints(310, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrigDeductAmount.setBounds(new Rectangle(310, 202, 270, 19));
        this.add(contOrigDeductAmount, new KDLayout.Constraints(310, 202, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //contName
        contName.setBoundEditor(txtName);
        //contOriginalAmount
        contOriginalAmount.setBoundEditor(txtOriginalAmount);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contResponsibleStyle
        contResponsibleStyle.setBoundEditor(comboResponsibleStyle);
        //contLastAmount
        contLastAmount.setBoundEditor(txtLastAmount);
        //contReasonDescription
        contReasonDescription.setBoundEditor(txtReasonDescription);
        //contColseDescription
        contColseDescription.setBoundEditor(txtColseDescription);
        //contChangeReson
        contChangeReson.setBoundEditor(txtChangeReson);
        //contSupplier
        contSupplier.setBoundEditor(prmtSupplier);
        //contContractBill
        contContractBill.setBoundEditor(prmtContractBill);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurProject);
        //contReportAmount
        contReportAmount.setBoundEditor(txtReportAmount);
        //contAllowAmount
        contAllowAmount.setBoundEditor(txtAllowAmount);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kdtEntrys, BorderLayout.CENTER);
        //contContractBillNumber
        contContractBillNumber.setBoundEditor(txtConBillNumber);
        //contOrigDeductAmount
        contOrigDeductAmount.setBoundEditor(txtOrigDeductAmount);

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
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
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
		dataBinder.registerBinding("isFinish", boolean.class, this.chkIsFinish, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("originalAmount", java.math.BigDecimal.class, this.txtOriginalAmount, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("responsibleStyle", com.kingdee.eas.fdc.contract.ResponsibleStyleEnum.class, this.comboResponsibleStyle, "selectedItem");
		dataBinder.registerBinding("lastAmount", java.math.BigDecimal.class, this.txtLastAmount, "value");
		dataBinder.registerBinding("reasonDescription", String.class, this.txtReasonDescription, "text");
		dataBinder.registerBinding("colseDescription", String.class, this.txtColseDescription, "text");
		dataBinder.registerBinding("changeReson", String.class, this.txtChangeReson, "text");
		dataBinder.registerBinding("supplier", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtSupplier, "data");
		dataBinder.registerBinding("contractBill", com.kingdee.eas.fdc.contract.ContractBillInfo.class, this.prmtContractBill, "data");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtCurProject, "data");
		dataBinder.registerBinding("reportAmount", java.math.BigDecimal.class, this.txtReportAmount, "value");
		dataBinder.registerBinding("allowAmount", java.math.BigDecimal.class, this.txtAllowAmount, "value");
		dataBinder.registerBinding("entrys.numer", String.class, this.kdtEntrys, "numer.text");
		dataBinder.registerBinding("entrys.changeContent", String.class, this.kdtEntrys, "changeContent.text");
		dataBinder.registerBinding("entrys.amount", java.math.BigDecimal.class, this.kdtEntrys, "amount.text");
		dataBinder.registerBinding("entrys.proNumber", java.math.BigDecimal.class, this.kdtEntrys, "proNumber.text");
		dataBinder.registerBinding("entrys.unit", String.class, this.kdtEntrys, "unit.text");
		dataBinder.registerBinding("entrys.totalAmount", java.math.BigDecimal.class, this.kdtEntrys, "totalAmount.text");
		dataBinder.registerBinding("entrys.remark", String.class, this.kdtEntrys, "remark.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.port.pm.contract.ContractChangeSettleBillEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("contractBill.number", String.class, this.txtConBillNumber, "text");
		dataBinder.registerBinding("conChangeBill.amount", java.math.BigDecimal.class, this.txtOrigDeductAmount, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.contract.app.ContractChangeSettleBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.port.pm.contract.ContractChangeSettleBillInfo)ov;
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
		getValidateHelper().registerBindProperty("isFinish", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("responsibleStyle", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reasonDescription", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("colseDescription", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeReson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reportAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("allowAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.numer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.changeContent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.proNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.unit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.totalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conChangeBill.amount", ValidateHelper.ON_SAVE);    		
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
        //write your c ode here
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
        sic.add(new SelectorItemInfo("isFinish"));
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("orgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("orgUnit.id"));
        	sic.add(new SelectorItemInfo("orgUnit.number"));
        	sic.add(new SelectorItemInfo("orgUnit.name"));
		}
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("responsibleStyle"));
        sic.add(new SelectorItemInfo("lastAmount"));
        sic.add(new SelectorItemInfo("reasonDescription"));
        sic.add(new SelectorItemInfo("colseDescription"));
        sic.add(new SelectorItemInfo("changeReson"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("supplier.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("supplier.id"));
        	sic.add(new SelectorItemInfo("supplier.number"));
        	sic.add(new SelectorItemInfo("supplier.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractBill.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractBill.id"));
        	sic.add(new SelectorItemInfo("contractBill.number"));
        	sic.add(new SelectorItemInfo("contractBill.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("curProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("curProject.id"));
        	sic.add(new SelectorItemInfo("curProject.number"));
        	sic.add(new SelectorItemInfo("curProject.name"));
		}
        sic.add(new SelectorItemInfo("reportAmount"));
        sic.add(new SelectorItemInfo("allowAmount"));
    	sic.add(new SelectorItemInfo("entrys.numer"));
    	sic.add(new SelectorItemInfo("entrys.changeContent"));
    	sic.add(new SelectorItemInfo("entrys.amount"));
    	sic.add(new SelectorItemInfo("entrys.proNumber"));
    	sic.add(new SelectorItemInfo("entrys.unit"));
    	sic.add(new SelectorItemInfo("entrys.totalAmount"));
    	sic.add(new SelectorItemInfo("entrys.remark"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
        sic.add(new SelectorItemInfo("conChangeBill.amount"));
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

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.pm.contract.client", "ContractChangeSettleBillEditUI");
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
        return com.kingdee.eas.port.pm.contract.client.ContractChangeSettleBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.contract.ContractChangeSettleBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.contract.ContractChangeSettleBillInfo objectValue = new com.kingdee.eas.port.pm.contract.ContractChangeSettleBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/contract/ContractChangeSettleBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.contract.app.ContractChangeSettleBillQuery");
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
				vo.put("originalAmount",new java.math.BigDecimal(0));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}