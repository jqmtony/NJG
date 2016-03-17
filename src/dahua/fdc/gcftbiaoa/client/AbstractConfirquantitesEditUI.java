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
public abstract class AbstractConfirquantitesEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractConfirquantitesEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contversion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contremake;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstatus;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisLast;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojrct;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractorUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contendTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contengineeringAudit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcostAudit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectFirstAudit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contworking;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractorPerosn;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contengineeringPerosn;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcostPerosn;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfirstPerosn;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtversion;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneremake;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtremake;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtChangeAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBanane;
    protected com.kingdee.bos.ctrl.swing.KDComboBox status;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcontractNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprojrct;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcontractorUnit;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkendTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtengineeringAudit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcostAudit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtprojectFirstAudit;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtworking;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcontractorPerosn;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtengineeringPerosn;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcostPerosn;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfirstPerosn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBananZreo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    protected actionAcctSelect actionAcctSelect = null;
    protected actionSplitProj actionSplitProj = null;
    protected actionSplitBotUp actionSplitBotUp = null;
    protected actionSplitProd actionSplitProd = null;
    protected actionBananZreo actionBananZreo = null;
    /**
     * output class constructor
     */
    public AbstractConfirquantitesEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractConfirquantitesEditUI.class.getName());
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
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
        this.actionUnAudit.setExtendProperty("canForewarn", "true");
        this.actionUnAudit.setExtendProperty("userDefined", "true");
        this.actionUnAudit.setExtendProperty("isObjectUpdateLock", "false");
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
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
        //actionBananZreo
        this.actionBananZreo = new actionBananZreo(this);
        getActionManager().registerAction("actionBananZreo", actionBananZreo);
         this.actionBananZreo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contversion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contremake = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contstatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisLast = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contcontractNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojrct = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractorUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contendTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contengineeringAudit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcostAudit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectFirstAudit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contworking = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractorPerosn = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contengineeringPerosn = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcostPerosn = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfirstPerosn = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtversion = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.scrollPaneremake = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtremake = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.pkauditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtChangeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBanane = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.status = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtcontractNumber = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtprojrct = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcontractorUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkendTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtengineeringAudit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcostAudit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtprojectFirstAudit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtworking = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtcontractorPerosn = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtengineeringPerosn = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcostPerosn = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtfirstPerosn = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnBananZreo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contAuditor.setName("contAuditor");
        this.contversion.setName("contversion");
        this.contremake.setName("contremake");
        this.contauditDate.setName("contauditDate");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contstatus.setName("contstatus");
        this.chkisLast.setName("chkisLast");
        this.contcontractNumber.setName("contcontractNumber");
        this.contprojrct.setName("contprojrct");
        this.contcontractorUnit.setName("contcontractorUnit");
        this.contendTime.setName("contendTime");
        this.contengineeringAudit.setName("contengineeringAudit");
        this.contcostAudit.setName("contcostAudit");
        this.contprojectFirstAudit.setName("contprojectFirstAudit");
        this.contworking.setName("contworking");
        this.contcontractorPerosn.setName("contcontractorPerosn");
        this.contengineeringPerosn.setName("contengineeringPerosn");
        this.contcostPerosn.setName("contcostPerosn");
        this.contfirstPerosn.setName("contfirstPerosn");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtversion.setName("txtversion");
        this.scrollPaneremake.setName("scrollPaneremake");
        this.txtremake.setName("txtremake");
        this.pkauditDate.setName("pkauditDate");
        this.txtChangeAmount.setName("txtChangeAmount");
        this.txtBanane.setName("txtBanane");
        this.status.setName("status");
        this.prmtcontractNumber.setName("prmtcontractNumber");
        this.prmtprojrct.setName("prmtprojrct");
        this.prmtcontractorUnit.setName("prmtcontractorUnit");
        this.pkendTime.setName("pkendTime");
        this.txtengineeringAudit.setName("txtengineeringAudit");
        this.txtcostAudit.setName("txtcostAudit");
        this.txtprojectFirstAudit.setName("txtprojectFirstAudit");
        this.txtworking.setName("txtworking");
        this.txtcontractorPerosn.setName("txtcontractorPerosn");
        this.txtengineeringPerosn.setName("txtengineeringPerosn");
        this.txtcostPerosn.setName("txtcostPerosn");
        this.txtfirstPerosn.setName("txtfirstPerosn");
        this.btnBananZreo.setName("btnBananZreo");
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
        this.contCreator.setVisible(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);		
        this.contCreateTime.setVisible(false);
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
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setVisible(false);
        // contversion		
        this.contversion.setBoundLabelText(resHelper.getString("contversion.boundLabelText"));		
        this.contversion.setBoundLabelLength(100);		
        this.contversion.setBoundLabelUnderline(true);		
        this.contversion.setVisible(false);
        // contremake		
        this.contremake.setBoundLabelText(resHelper.getString("contremake.boundLabelText"));		
        this.contremake.setBoundLabelLength(16);		
        this.contremake.setVisible(true);		
        this.contremake.setBoundLabelAlignment(8);
        // contauditDate		
        this.contauditDate.setBoundLabelText(resHelper.getString("contauditDate.boundLabelText"));		
        this.contauditDate.setBoundLabelLength(100);		
        this.contauditDate.setBoundLabelUnderline(true);		
        this.contauditDate.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setVisible(false);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setVisible(false);
        // contstatus		
        this.contstatus.setBoundLabelText(resHelper.getString("contstatus.boundLabelText"));		
        this.contstatus.setBoundLabelLength(100);		
        this.contstatus.setBoundLabelUnderline(true);		
        this.contstatus.setVisible(true);		
        this.contstatus.setEnabled(false);
        // chkisLast		
        this.chkisLast.setText(resHelper.getString("chkisLast.text"));		
        this.chkisLast.setVisible(false);		
        this.chkisLast.setHorizontalAlignment(2);
        // contcontractNumber		
        this.contcontractNumber.setBoundLabelText(resHelper.getString("contcontractNumber.boundLabelText"));		
        this.contcontractNumber.setBoundLabelLength(100);		
        this.contcontractNumber.setBoundLabelUnderline(true);		
        this.contcontractNumber.setVisible(true);		
        this.contcontractNumber.setEnabled(false);
        // contprojrct		
        this.contprojrct.setBoundLabelText(resHelper.getString("contprojrct.boundLabelText"));		
        this.contprojrct.setBoundLabelLength(100);		
        this.contprojrct.setBoundLabelUnderline(true);		
        this.contprojrct.setVisible(true);		
        this.contprojrct.setEnabled(false);
        // contcontractorUnit		
        this.contcontractorUnit.setBoundLabelText(resHelper.getString("contcontractorUnit.boundLabelText"));		
        this.contcontractorUnit.setBoundLabelLength(100);		
        this.contcontractorUnit.setBoundLabelUnderline(true);		
        this.contcontractorUnit.setVisible(true);
        // contendTime		
        this.contendTime.setBoundLabelText(resHelper.getString("contendTime.boundLabelText"));		
        this.contendTime.setBoundLabelLength(100);		
        this.contendTime.setBoundLabelUnderline(true);		
        this.contendTime.setVisible(true);
        // contengineeringAudit		
        this.contengineeringAudit.setBoundLabelText(resHelper.getString("contengineeringAudit.boundLabelText"));		
        this.contengineeringAudit.setBoundLabelLength(16);		
        this.contengineeringAudit.setVisible(true);		
        this.contengineeringAudit.setBoundLabelAlignment(8);
        // contcostAudit		
        this.contcostAudit.setBoundLabelText(resHelper.getString("contcostAudit.boundLabelText"));		
        this.contcostAudit.setBoundLabelLength(16);		
        this.contcostAudit.setVisible(true);		
        this.contcostAudit.setBoundLabelAlignment(8);
        // contprojectFirstAudit		
        this.contprojectFirstAudit.setBoundLabelText(resHelper.getString("contprojectFirstAudit.boundLabelText"));		
        this.contprojectFirstAudit.setBoundLabelLength(16);		
        this.contprojectFirstAudit.setBoundLabelUnderline(true);		
        this.contprojectFirstAudit.setVisible(true);		
        this.contprojectFirstAudit.setBoundLabelAlignment(8);
        // contworking		
        this.contworking.setBoundLabelText(resHelper.getString("contworking.boundLabelText"));		
        this.contworking.setBoundLabelLength(16);		
        this.contworking.setVisible(true);		
        this.contworking.setBoundLabelAlignment(8);
        // contcontractorPerosn		
        this.contcontractorPerosn.setBoundLabelText(resHelper.getString("contcontractorPerosn.boundLabelText"));		
        this.contcontractorPerosn.setBoundLabelLength(100);		
        this.contcontractorPerosn.setBoundLabelUnderline(true);		
        this.contcontractorPerosn.setVisible(true);
        // contengineeringPerosn		
        this.contengineeringPerosn.setBoundLabelText(resHelper.getString("contengineeringPerosn.boundLabelText"));		
        this.contengineeringPerosn.setBoundLabelLength(100);		
        this.contengineeringPerosn.setBoundLabelUnderline(true);		
        this.contengineeringPerosn.setVisible(true);		
        this.contengineeringPerosn.setEnabled(false);
        // contcostPerosn		
        this.contcostPerosn.setBoundLabelText(resHelper.getString("contcostPerosn.boundLabelText"));		
        this.contcostPerosn.setBoundLabelLength(100);		
        this.contcostPerosn.setBoundLabelUnderline(true);		
        this.contcostPerosn.setVisible(true);		
        this.contcostPerosn.setEnabled(false);
        // contfirstPerosn		
        this.contfirstPerosn.setBoundLabelText(resHelper.getString("contfirstPerosn.boundLabelText"));		
        this.contfirstPerosn.setBoundLabelLength(100);		
        this.contfirstPerosn.setBoundLabelUnderline(true);		
        this.contfirstPerosn.setVisible(true);		
        this.contfirstPerosn.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setEnabled(false);
        this.kDDateCreateTime.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    kDDateCreateTime_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // pkBizDate		
        this.pkBizDate.setVisible(false);		
        this.pkBizDate.setEnabled(true);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // txtversion		
        this.txtversion.setVisible(true);		
        this.txtversion.setHorizontalAlignment(2);		
        this.txtversion.setMaxLength(100);		
        this.txtversion.setRequired(false);		
        this.txtversion.setEnabled(false);
        // scrollPaneremake
        // txtremake		
        this.txtremake.setVisible(true);		
        this.txtremake.setRequired(false);		
        this.txtremake.setMaxLength(500);
        // pkauditDate		
        this.pkauditDate.setVisible(true);		
        this.pkauditDate.setRequired(false);		
        this.pkauditDate.setEnabled(false);
        // txtChangeAmount		
        this.txtChangeAmount.setEnabled(false);		
        this.txtChangeAmount.setVisible(false);
        // txtBanane		
        this.txtBanane.setEnabled(false);		
        this.txtBanane.setVisible(false);
        // status		
        this.status.setVisible(true);		
        this.status.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.status.setRequired(false);		
        this.status.setEnabled(false);
        // prmtcontractNumber		
        this.prmtcontractNumber.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillQuery");		
        this.prmtcontractNumber.setVisible(true);		
        this.prmtcontractNumber.setEditable(true);		
        this.prmtcontractNumber.setDisplayFormat("$number$- $name$");		
        this.prmtcontractNumber.setEditFormat("$number$");		
        this.prmtcontractNumber.setCommitFormat("$number$");		
        this.prmtcontractNumber.setRequired(false);
        // prmtprojrct		
        this.prmtprojrct.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");		
        this.prmtprojrct.setVisible(true);		
        this.prmtprojrct.setEditable(true);		
        this.prmtprojrct.setDisplayFormat("$name$");		
        this.prmtprojrct.setEditFormat("$number$");		
        this.prmtprojrct.setCommitFormat("$number$");		
        this.prmtprojrct.setRequired(false);
        // prmtcontractorUnit		
        this.prmtcontractorUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.PSupplierQuery");		
        this.prmtcontractorUnit.setVisible(true);		
        this.prmtcontractorUnit.setEditable(true);		
        this.prmtcontractorUnit.setDisplayFormat("$name$");		
        this.prmtcontractorUnit.setEditFormat("$number$");		
        this.prmtcontractorUnit.setCommitFormat("$number$");		
        this.prmtcontractorUnit.setRequired(false);		
        this.prmtcontractorUnit.setEnabled(false);
        // pkendTime		
        this.pkendTime.setVisible(true);		
        this.pkendTime.setRequired(false);
        // txtengineeringAudit		
        this.txtengineeringAudit.setVisible(true);		
        this.txtengineeringAudit.setHorizontalAlignment(2);		
        this.txtengineeringAudit.setMaxLength(255);		
        this.txtengineeringAudit.setRequired(false);		
        this.txtengineeringAudit.setEnabled(false);
        // txtcostAudit		
        this.txtcostAudit.setVisible(true);		
        this.txtcostAudit.setHorizontalAlignment(2);		
        this.txtcostAudit.setMaxLength(255);		
        this.txtcostAudit.setRequired(false);		
        this.txtcostAudit.setEnabled(false);
        // txtprojectFirstAudit		
        this.txtprojectFirstAudit.setVisible(true);		
        this.txtprojectFirstAudit.setHorizontalAlignment(2);		
        this.txtprojectFirstAudit.setMaxLength(255);		
        this.txtprojectFirstAudit.setRequired(false);		
        this.txtprojectFirstAudit.setEnabled(false);
        // txtworking		
        this.txtworking.setMaxLength(500);
        // txtcontractorPerosn		
        this.txtcontractorPerosn.setVisible(true);		
        this.txtcontractorPerosn.setHorizontalAlignment(2);		
        this.txtcontractorPerosn.setMaxLength(20);		
        this.txtcontractorPerosn.setRequired(false);
        this.txtcontractorPerosn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtcontractorPerosn_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtengineeringPerosn		
        this.txtengineeringPerosn.setVisible(true);		
        this.txtengineeringPerosn.setHorizontalAlignment(2);		
        this.txtengineeringPerosn.setMaxLength(20);		
        this.txtengineeringPerosn.setRequired(false);		
        this.txtengineeringPerosn.setEnabled(false);
        // txtcostPerosn		
        this.txtcostPerosn.setVisible(true);		
        this.txtcostPerosn.setHorizontalAlignment(2);		
        this.txtcostPerosn.setMaxLength(20);		
        this.txtcostPerosn.setRequired(false);		
        this.txtcostPerosn.setEnabled(false);
        // txtfirstPerosn		
        this.txtfirstPerosn.setVisible(true);		
        this.txtfirstPerosn.setHorizontalAlignment(2);		
        this.txtfirstPerosn.setMaxLength(20);		
        this.txtfirstPerosn.setRequired(false);		
        this.txtfirstPerosn.setEnabled(false);
        this.txtfirstPerosn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtfirstPerosn_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnBananZreo
        this.btnBananZreo.setAction((IItemAction)ActionProxyFactory.getProxy(actionBananZreo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBananZreo.setText(resHelper.getString("btnBananZreo.text"));		
        this.btnBananZreo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_amountgeneralledger"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtprojrct,prmtcontractorUnit,pkendTime,txtcontractorPerosn}));
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
        contCreator.setBounds(new Rectangle(374, 500, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(374, 500, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(374, 524, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(374, 524, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(7, 6, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(7, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(675, 7, 28, 19));
        this.add(contBizDate, new KDLayout.Constraints(675, 7, 28, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(9, 500, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(9, 500, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contversion.setBounds(new Rectangle(662, 43, 63, 19));
        this.add(contversion, new KDLayout.Constraints(662, 43, 63, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contremake.setBounds(new Rectangle(7, 56, 1001, 76));
        this.add(contremake, new KDLayout.Constraints(7, 56, 1001, 76, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contauditDate.setBounds(new Rectangle(9, 524, 270, 19));
        this.add(contauditDate, new KDLayout.Constraints(9, 524, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(295, 39, 30, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(295, 39, 30, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(582, 231, 43, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(582, 231, 43, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contstatus.setBounds(new Rectangle(738, 31, 270, 19));
        this.add(contstatus, new KDLayout.Constraints(738, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkisLast.setBounds(new Rectangle(292, 8, 77, 19));
        this.add(chkisLast, new KDLayout.Constraints(292, 8, 77, 19, 0));
        contcontractNumber.setBounds(new Rectangle(7, 31, 270, 19));
        this.add(contcontractNumber, new KDLayout.Constraints(7, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contprojrct.setBounds(new Rectangle(372, 6, 270, 19));
        this.add(contprojrct, new KDLayout.Constraints(372, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractorUnit.setBounds(new Rectangle(738, 6, 270, 19));
        this.add(contcontractorUnit, new KDLayout.Constraints(738, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contendTime.setBounds(new Rectangle(372, 31, 270, 19));
        this.add(contendTime, new KDLayout.Constraints(372, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contengineeringAudit.setBounds(new Rectangle(7, 225, 637, 87));
        this.add(contengineeringAudit, new KDLayout.Constraints(7, 225, 637, 87, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcostAudit.setBounds(new Rectangle(7, 316, 637, 87));
        this.add(contcostAudit, new KDLayout.Constraints(7, 316, 637, 87, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contprojectFirstAudit.setBounds(new Rectangle(7, 407, 637, 87));
        this.add(contprojectFirstAudit, new KDLayout.Constraints(7, 407, 637, 87, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contworking.setBounds(new Rectangle(7, 135, 637, 87));
        this.add(contworking, new KDLayout.Constraints(7, 135, 637, 87, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractorPerosn.setBounds(new Rectangle(738, 203, 270, 19));
        this.add(contcontractorPerosn, new KDLayout.Constraints(738, 203, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contengineeringPerosn.setBounds(new Rectangle(738, 293, 270, 19));
        this.add(contengineeringPerosn, new KDLayout.Constraints(738, 293, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contcostPerosn.setBounds(new Rectangle(738, 384, 270, 19));
        this.add(contcostPerosn, new KDLayout.Constraints(738, 384, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contfirstPerosn.setBounds(new Rectangle(738, 475, 270, 19));
        this.add(contfirstPerosn, new KDLayout.Constraints(738, 475, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contversion
        contversion.setBoundEditor(txtversion);
        //contremake
        contremake.setBoundEditor(scrollPaneremake);
        //scrollPaneremake
        scrollPaneremake.getViewport().add(txtremake, null);
        //contauditDate
        contauditDate.setBoundEditor(pkauditDate);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtChangeAmount);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtBanane);
        //contstatus
        contstatus.setBoundEditor(status);
        //contcontractNumber
        contcontractNumber.setBoundEditor(prmtcontractNumber);
        //contprojrct
        contprojrct.setBoundEditor(prmtprojrct);
        //contcontractorUnit
        contcontractorUnit.setBoundEditor(prmtcontractorUnit);
        //contendTime
        contendTime.setBoundEditor(pkendTime);
        //contengineeringAudit
        contengineeringAudit.setBoundEditor(txtengineeringAudit);
        //contcostAudit
        contcostAudit.setBoundEditor(txtcostAudit);
        //contprojectFirstAudit
        contprojectFirstAudit.setBoundEditor(txtprojectFirstAudit);
        //contworking
        contworking.setBoundEditor(txtworking);
        //contcontractorPerosn
        contcontractorPerosn.setBoundEditor(txtcontractorPerosn);
        //contengineeringPerosn
        contengineeringPerosn.setBoundEditor(txtengineeringPerosn);
        //contcostPerosn
        contcostPerosn.setBoundEditor(txtcostPerosn);
        //contfirstPerosn
        contfirstPerosn.setBoundEditor(txtfirstPerosn);

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
        this.toolBar.add(btnBananZreo);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
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
		dataBinder.registerBinding("isLast", boolean.class, this.chkisLast, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("version", String.class, this.txtversion, "text");
		dataBinder.registerBinding("remake", String.class, this.txtremake, "text");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.pkauditDate, "value");
		dataBinder.registerBinding("status", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.status, "selectedItem");
		dataBinder.registerBinding("contractNumber", com.kingdee.eas.fdc.contract.ContractBillInfo.class, this.prmtcontractNumber, "data");
		dataBinder.registerBinding("projrct", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtprojrct, "data");
		dataBinder.registerBinding("contractorUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtcontractorUnit, "data");
		dataBinder.registerBinding("endTime", java.util.Date.class, this.pkendTime, "value");
		dataBinder.registerBinding("engineeringAudit", String.class, this.txtengineeringAudit, "text");
		dataBinder.registerBinding("costAudit", String.class, this.txtcostAudit, "text");
		dataBinder.registerBinding("projectFirstAudit", String.class, this.txtprojectFirstAudit, "text");
		dataBinder.registerBinding("working", String.class, this.txtworking, "text");
		dataBinder.registerBinding("contractorPerosn", String.class, this.txtcontractorPerosn, "text");
		dataBinder.registerBinding("engineeringPerosn", String.class, this.txtengineeringPerosn, "text");
		dataBinder.registerBinding("costPerosn", String.class, this.txtcostPerosn, "text");
		dataBinder.registerBinding("firstPerosn", String.class, this.txtfirstPerosn, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.gcftbiaoa.app.ConfirquantitesEditUIHandler";
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
        this.prmtprojrct.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesInfo)ov;
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
		getValidateHelper().registerBindProperty("isLast", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("version", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remake", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projrct", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractorUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("engineeringAudit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costAudit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectFirstAudit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("working", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractorPerosn", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("engineeringPerosn", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costPerosn", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("firstPerosn", ValidateHelper.ON_SAVE);    		
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
     * output kDDateCreateTime_dataChanged method
     */
    protected void kDDateCreateTime_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtcontractorPerosn_actionPerformed method
     */
    protected void txtcontractorPerosn_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtfirstPerosn_actionPerformed method
     */
    protected void txtfirstPerosn_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("isLast"));
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
        sic.add(new SelectorItemInfo("bizDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("version"));
        sic.add(new SelectorItemInfo("remake"));
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("status"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractNumber.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractNumber.id"));
        	sic.add(new SelectorItemInfo("contractNumber.number"));
        	sic.add(new SelectorItemInfo("contractNumber.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("projrct.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("projrct.id"));
        	sic.add(new SelectorItemInfo("projrct.number"));
        	sic.add(new SelectorItemInfo("projrct.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractorUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractorUnit.id"));
        	sic.add(new SelectorItemInfo("contractorUnit.number"));
        	sic.add(new SelectorItemInfo("contractorUnit.name"));
		}
        sic.add(new SelectorItemInfo("endTime"));
        sic.add(new SelectorItemInfo("engineeringAudit"));
        sic.add(new SelectorItemInfo("costAudit"));
        sic.add(new SelectorItemInfo("projectFirstAudit"));
        sic.add(new SelectorItemInfo("working"));
        sic.add(new SelectorItemInfo("contractorPerosn"));
        sic.add(new SelectorItemInfo("engineeringPerosn"));
        sic.add(new SelectorItemInfo("costPerosn"));
        sic.add(new SelectorItemInfo("firstPerosn"));
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
        com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesFactory.getRemoteInstance().actionAudit(editData);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesFactory.getRemoteInstance().actionUnAudit(editData);
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
     * output actionBananZreo_actionPerformed method
     */
    public void actionBananZreo_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareactionBananZreo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionBananZreo() {
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
            innerActionPerformed("eas", AbstractConfirquantitesEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractConfirquantitesEditUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractConfirquantitesEditUI.this, "actionAcctSelect", "actionAcctSelect_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractConfirquantitesEditUI.this, "actionSplitProj", "actionSplitProj_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractConfirquantitesEditUI.this, "actionSplitBotUp", "actionSplitBotUp_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractConfirquantitesEditUI.this, "actionSplitProd", "actionSplitProd_actionPerformed", e);
        }
    }

    /**
     * output actionBananZreo class
     */     
    protected class actionBananZreo extends ItemAction {     
    
        public actionBananZreo()
        {
            this(null);
        }

        public actionBananZreo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionBananZreo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionBananZreo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionBananZreo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractConfirquantitesEditUI.this, "actionBananZreo", "actionBananZreo_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.gcftbiaoa.client", "ConfirquantitesEditUI");
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
        return com.kingdee.eas.fdc.gcftbiaoa.client.ConfirquantitesEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesInfo objectValue = new com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/gcftbiaoa/Confirquantites";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.gcftbiaoa.app.ConfirquantitesQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {        
        return null;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("status","1SAVED");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}