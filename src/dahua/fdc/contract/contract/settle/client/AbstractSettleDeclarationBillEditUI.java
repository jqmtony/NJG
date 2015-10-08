/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.settle.client;

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
public abstract class AbstractSettleDeclarationBillEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSettleDeclarationBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdecAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contversion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contapprovalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contactualConstruction;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsettleHead;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlinkTel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstartTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contendTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsubTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contunitPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstate;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbillState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontractNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisVersion;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtdecAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtversion;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtapprovalAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtactualConstruction;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsettleHead;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtlinkTel;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkstartTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkendTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pksubTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtunitPrice;
    protected com.kingdee.bos.ctrl.swing.KDComboBox state;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjszlcyqk;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjsydti;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjswcsjyq;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel5;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE1;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE1_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE2;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE2_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE3;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE3_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanejszlcyqk;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtjszlcyqk;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanejsydti;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtjsydti;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkjswcsjyq;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contgzjjmghfwms;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzlfhyj;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsptsyfw;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjgclfw;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contwycfts;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsdfjnqk;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzlaqgq;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkqizhu;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkfenshua;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkdimian;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanegzjjmghfwms;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtgzjjmghfwms;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanezlfhyj;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtzlfhyj;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanesptsyfw;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtsptsyfw;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanejgclfw;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtjgclfw;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanewycfts;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtwycfts;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanesdfjnqk;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtsdfjnqk;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanezlaqgq;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtzlaqgq;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox billState;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcontractNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnInTrial;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApproved;
    protected com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo editData = null;
    protected ActionInTrial actionInTrial = null;
    protected ActionApproved actionApproved = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    /**
     * output class constructor
     */
    public AbstractSettleDeclarationBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSettleDeclarationBillEditUI.class.getName());
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
        //actionInTrial
        this.actionInTrial = new ActionInTrial(this);
        getActionManager().registerAction("actionInTrial", actionInTrial);
         this.actionInTrial.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionApproved
        this.actionApproved = new ActionApproved(this);
        getActionManager().registerAction("actionApproved", actionApproved);
         this.actionApproved.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contdecAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contversion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contapprovalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contactualConstruction = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsettleHead = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlinkTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contstartTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contendTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsubTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contunitPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contstate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbillState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontractNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisVersion = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtdecAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtversion = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtapprovalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtactualConstruction = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtsettleHead = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtlinkTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkstartTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkendTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pksubTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtunitPrice = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.state = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contjszlcyqk = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjsydti = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjswcsjyq = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel5 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtE1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtE2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtE3 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.scrollPanejszlcyqk = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtjszlcyqk = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanejsydti = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtjsydti = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.pkjswcsjyq = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contgzjjmghfwms = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzlfhyj = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsptsyfw = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjgclfw = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contwycfts = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsdfjnqk = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzlaqgq = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkqizhu = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkfenshua = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkdimian = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPanegzjjmghfwms = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtgzjjmghfwms = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanezlfhyj = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtzlfhyj = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanesptsyfw = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtsptsyfw = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanejgclfw = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtjgclfw = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanewycfts = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtwycfts = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanesdfjnqk = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtsdfjnqk = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanezlaqgq = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtzlaqgq = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.billState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtcontractNumber = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnInTrial = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnApproved = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contdecAmount.setName("contdecAmount");
        this.contversion.setName("contversion");
        this.contapprovalAmount.setName("contapprovalAmount");
        this.contactualConstruction.setName("contactualConstruction");
        this.contsettleHead.setName("contsettleHead");
        this.contlinkTel.setName("contlinkTel");
        this.contstartTime.setName("contstartTime");
        this.contendTime.setName("contendTime");
        this.contsubTime.setName("contsubTime");
        this.contunitPrice.setName("contunitPrice");
        this.contstate.setName("contstate");
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel4.setName("kDPanel4");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contCreator.setName("contCreator");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contCreateTime.setName("contCreateTime");
        this.contDescription.setName("contDescription");
        this.contBizDate.setName("contBizDate");
        this.contbillState.setName("contbillState");
        this.contcontractNumber.setName("contcontractNumber");
        this.contAuditor.setName("contAuditor");
        this.contNumber.setName("contNumber");
        this.chkisVersion.setName("chkisVersion");
        this.txtdecAmount.setName("txtdecAmount");
        this.txtversion.setName("txtversion");
        this.txtapprovalAmount.setName("txtapprovalAmount");
        this.txtactualConstruction.setName("txtactualConstruction");
        this.txtsettleHead.setName("txtsettleHead");
        this.txtlinkTel.setName("txtlinkTel");
        this.pkstartTime.setName("pkstartTime");
        this.pkendTime.setName("pkendTime");
        this.pksubTime.setName("pksubTime");
        this.txtunitPrice.setName("txtunitPrice");
        this.state.setName("state");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contjszlcyqk.setName("contjszlcyqk");
        this.contjsydti.setName("contjsydti");
        this.contjswcsjyq.setName("contjswcsjyq");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel5.setName("kDPanel5");
        this.kdtE1.setName("kdtE1");
        this.kdtE2.setName("kdtE2");
        this.kdtE3.setName("kdtE3");
        this.scrollPanejszlcyqk.setName("scrollPanejszlcyqk");
        this.txtjszlcyqk.setName("txtjszlcyqk");
        this.scrollPanejsydti.setName("scrollPanejsydti");
        this.txtjsydti.setName("txtjsydti");
        this.pkjswcsjyq.setName("pkjswcsjyq");
        this.contgzjjmghfwms.setName("contgzjjmghfwms");
        this.contzlfhyj.setName("contzlfhyj");
        this.contsptsyfw.setName("contsptsyfw");
        this.contjgclfw.setName("contjgclfw");
        this.contwycfts.setName("contwycfts");
        this.contsdfjnqk.setName("contsdfjnqk");
        this.contzlaqgq.setName("contzlaqgq");
        this.chkqizhu.setName("chkqizhu");
        this.chkfenshua.setName("chkfenshua");
        this.chkdimian.setName("chkdimian");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.scrollPanegzjjmghfwms.setName("scrollPanegzjjmghfwms");
        this.txtgzjjmghfwms.setName("txtgzjjmghfwms");
        this.scrollPanezlfhyj.setName("scrollPanezlfhyj");
        this.txtzlfhyj.setName("txtzlfhyj");
        this.scrollPanesptsyfw.setName("scrollPanesptsyfw");
        this.txtsptsyfw.setName("txtsptsyfw");
        this.scrollPanejgclfw.setName("scrollPanejgclfw");
        this.txtjgclfw.setName("txtjgclfw");
        this.scrollPanewycfts.setName("scrollPanewycfts");
        this.txtwycfts.setName("txtwycfts");
        this.scrollPanesdfjnqk.setName("scrollPanesdfjnqk");
        this.txtsdfjnqk.setName("txtsdfjnqk");
        this.scrollPanezlaqgq.setName("scrollPanezlaqgq");
        this.txtzlaqgq.setName("txtzlaqgq");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.txtDescription.setName("txtDescription");
        this.pkBizDate.setName("pkBizDate");
        this.billState.setName("billState");
        this.prmtcontractNumber.setName("prmtcontractNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtNumber.setName("txtNumber");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnInTrial.setName("btnInTrial");
        this.btnApproved.setName("btnApproved");
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
        // contdecAmount		
        this.contdecAmount.setBoundLabelText(resHelper.getString("contdecAmount.boundLabelText"));		
        this.contdecAmount.setBoundLabelLength(70);		
        this.contdecAmount.setBoundLabelUnderline(true);		
        this.contdecAmount.setVisible(true);
        // contversion		
        this.contversion.setBoundLabelText(resHelper.getString("contversion.boundLabelText"));		
        this.contversion.setBoundLabelLength(70);		
        this.contversion.setBoundLabelUnderline(true);		
        this.contversion.setVisible(true);
        // contapprovalAmount		
        this.contapprovalAmount.setBoundLabelText(resHelper.getString("contapprovalAmount.boundLabelText"));		
        this.contapprovalAmount.setBoundLabelLength(70);		
        this.contapprovalAmount.setBoundLabelUnderline(true);		
        this.contapprovalAmount.setVisible(true);
        // contactualConstruction		
        this.contactualConstruction.setBoundLabelText(resHelper.getString("contactualConstruction.boundLabelText"));		
        this.contactualConstruction.setBoundLabelLength(70);		
        this.contactualConstruction.setBoundLabelUnderline(true);		
        this.contactualConstruction.setVisible(true);
        // contsettleHead		
        this.contsettleHead.setBoundLabelText(resHelper.getString("contsettleHead.boundLabelText"));		
        this.contsettleHead.setBoundLabelLength(70);		
        this.contsettleHead.setBoundLabelUnderline(true);		
        this.contsettleHead.setVisible(true);
        // contlinkTel		
        this.contlinkTel.setBoundLabelText(resHelper.getString("contlinkTel.boundLabelText"));		
        this.contlinkTel.setBoundLabelLength(70);		
        this.contlinkTel.setBoundLabelUnderline(true);		
        this.contlinkTel.setVisible(true);
        // contstartTime		
        this.contstartTime.setBoundLabelText(resHelper.getString("contstartTime.boundLabelText"));		
        this.contstartTime.setBoundLabelLength(70);		
        this.contstartTime.setBoundLabelUnderline(true);		
        this.contstartTime.setVisible(true);
        // contendTime		
        this.contendTime.setBoundLabelText(resHelper.getString("contendTime.boundLabelText"));		
        this.contendTime.setBoundLabelLength(70);		
        this.contendTime.setBoundLabelUnderline(true);		
        this.contendTime.setVisible(true);
        // contsubTime		
        this.contsubTime.setBoundLabelText(resHelper.getString("contsubTime.boundLabelText"));		
        this.contsubTime.setBoundLabelLength(70);		
        this.contsubTime.setBoundLabelUnderline(true);		
        this.contsubTime.setVisible(true);
        // contunitPrice		
        this.contunitPrice.setBoundLabelText(resHelper.getString("contunitPrice.boundLabelText"));		
        this.contunitPrice.setBoundLabelLength(70);		
        this.contunitPrice.setBoundLabelUnderline(true);		
        this.contunitPrice.setVisible(true);
        // contstate		
        this.contstate.setBoundLabelText(resHelper.getString("contstate.boundLabelText"));		
        this.contstate.setBoundLabelLength(70);		
        this.contstate.setBoundLabelUnderline(true);		
        this.contstate.setVisible(true);
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // kDPanel4		
        this.kDPanel4.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel4.border.title")));
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);		
        this.contLastUpdateUser.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);		
        this.contCreator.setVisible(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);		
        this.contLastUpdateTime.setVisible(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);		
        this.contCreateTime.setVisible(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setVisible(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(false);
        // contbillState		
        this.contbillState.setBoundLabelText(resHelper.getString("contbillState.boundLabelText"));		
        this.contbillState.setBoundLabelLength(70);		
        this.contbillState.setBoundLabelUnderline(true);		
        this.contbillState.setVisible(true);
        // contcontractNumber		
        this.contcontractNumber.setBoundLabelText(resHelper.getString("contcontractNumber.boundLabelText"));		
        this.contcontractNumber.setBoundLabelLength(70);		
        this.contcontractNumber.setBoundLabelUnderline(true);		
        this.contcontractNumber.setVisible(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(70);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(70);		
        this.contNumber.setBoundLabelUnderline(true);
        // chkisVersion		
        this.chkisVersion.setText(resHelper.getString("chkisVersion.text"));		
        this.chkisVersion.setVisible(true);		
        this.chkisVersion.setHorizontalAlignment(2);
        // txtdecAmount		
        this.txtdecAmount.setHorizontalAlignment(2);		
        this.txtdecAmount.setDataType(1);		
        this.txtdecAmount.setSupportedEmpty(true);		
        this.txtdecAmount.setMinimumValue( new java.math.BigDecimal("-999.9999999999"));		
        this.txtdecAmount.setMaximumValue( new java.math.BigDecimal("999.9999999999"));		
        this.txtdecAmount.setPrecision(10);		
        this.txtdecAmount.setRequired(false);
        // txtversion		
        this.txtversion.setHorizontalAlignment(2);		
        this.txtversion.setMaxLength(8);		
        this.txtversion.setRequired(false);
        // txtapprovalAmount		
        this.txtapprovalAmount.setHorizontalAlignment(2);		
        this.txtapprovalAmount.setDataType(1);		
        this.txtapprovalAmount.setSupportedEmpty(true);		
        this.txtapprovalAmount.setMinimumValue( new java.math.BigDecimal("-999.9999999999"));		
        this.txtapprovalAmount.setMaximumValue( new java.math.BigDecimal("999.9999999999"));		
        this.txtapprovalAmount.setPrecision(10);		
        this.txtapprovalAmount.setRequired(false);
        // txtactualConstruction		
        this.txtactualConstruction.setHorizontalAlignment(2);		
        this.txtactualConstruction.setMaxLength(100);		
        this.txtactualConstruction.setRequired(false);
        // txtsettleHead		
        this.txtsettleHead.setHorizontalAlignment(2);		
        this.txtsettleHead.setMaxLength(100);		
        this.txtsettleHead.setRequired(false);
        // txtlinkTel		
        this.txtlinkTel.setHorizontalAlignment(2);		
        this.txtlinkTel.setMaxLength(100);		
        this.txtlinkTel.setRequired(false);
        // pkstartTime		
        this.pkstartTime.setRequired(false);
        // pkendTime		
        this.pkendTime.setRequired(false);
        // pksubTime		
        this.pksubTime.setRequired(false);
        // txtunitPrice		
        this.txtunitPrice.setHorizontalAlignment(2);		
        this.txtunitPrice.setMaxLength(100);		
        this.txtunitPrice.setRequired(false);
        // state		
        this.state.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.settle.app.TrialStatusEnum").toArray());		
        this.state.setRequired(false);
        // kDTabbedPane1
        // contjszlcyqk		
        this.contjszlcyqk.setBoundLabelText(resHelper.getString("contjszlcyqk.boundLabelText"));		
        this.contjszlcyqk.setBoundLabelLength(20);		
        this.contjszlcyqk.setBoundLabelUnderline(true);		
        this.contjszlcyqk.setVisible(true);		
        this.contjszlcyqk.setBoundLabelAlignment(8);
        // contjsydti		
        this.contjsydti.setBoundLabelText(resHelper.getString("contjsydti.boundLabelText"));		
        this.contjsydti.setBoundLabelLength(20);		
        this.contjsydti.setBoundLabelUnderline(true);		
        this.contjsydti.setVisible(true);		
        this.contjsydti.setBoundLabelAlignment(8);
        // contjswcsjyq		
        this.contjswcsjyq.setBoundLabelText(resHelper.getString("contjswcsjyq.boundLabelText"));		
        this.contjswcsjyq.setBoundLabelLength(100);		
        this.contjswcsjyq.setBoundLabelUnderline(true);		
        this.contjswcsjyq.setVisible(true);
        // kDPanel1
        // kDPanel2
        // kDPanel5
        // kdtE1
		String kdtE1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"sbzl\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"xuanze\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yeshu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{sbzl}</t:Cell><t:Cell>$Resource{xuanze}</t:Cell><t:Cell>$Resource{yeshu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE1.setFormatXml(resHelper.translateString("kdtE1",kdtE1StrXML));

                this.kdtE1.putBindContents("editData",new String[] {"seq","sbzl","xuanze","yeshu"});


        this.kdtE1.checkParsed();
        KDTextField kdtE1_sbzl_TextField = new KDTextField();
        kdtE1_sbzl_TextField.setName("kdtE1_sbzl_TextField");
        kdtE1_sbzl_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_sbzl_CellEditor = new KDTDefaultCellEditor(kdtE1_sbzl_TextField);
        this.kdtE1.getColumn("sbzl").setEditor(kdtE1_sbzl_CellEditor);
        KDCheckBox kdtE1_xuanze_CheckBox = new KDCheckBox();
        kdtE1_xuanze_CheckBox.setName("kdtE1_xuanze_CheckBox");
        KDTDefaultCellEditor kdtE1_xuanze_CellEditor = new KDTDefaultCellEditor(kdtE1_xuanze_CheckBox);
        this.kdtE1.getColumn("xuanze").setEditor(kdtE1_xuanze_CellEditor);
        KDFormattedTextField kdtE1_yeshu_TextField = new KDFormattedTextField();
        kdtE1_yeshu_TextField.setName("kdtE1_yeshu_TextField");
        kdtE1_yeshu_TextField.setVisible(true);
        kdtE1_yeshu_TextField.setEditable(true);
        kdtE1_yeshu_TextField.setHorizontalAlignment(2);
        kdtE1_yeshu_TextField.setDataType(0);
        KDTDefaultCellEditor kdtE1_yeshu_CellEditor = new KDTDefaultCellEditor(kdtE1_yeshu_TextField);
        this.kdtE1.getColumn("yeshu").setEditor(kdtE1_yeshu_CellEditor);
        // kdtE2
		String kdtE2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"sbzl2\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"xuanze2\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yeshu2\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{sbzl2}</t:Cell><t:Cell>$Resource{xuanze2}</t:Cell><t:Cell>$Resource{yeshu2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE2.setFormatXml(resHelper.translateString("kdtE2",kdtE2StrXML));

                this.kdtE2.putBindContents("editData",new String[] {"seq","sbzl2","xuanze2","yeshu2"});


        this.kdtE2.checkParsed();
        KDCheckBox kdtE2_xuanze2_CheckBox = new KDCheckBox();
        kdtE2_xuanze2_CheckBox.setName("kdtE2_xuanze2_CheckBox");
        KDTDefaultCellEditor kdtE2_xuanze2_CellEditor = new KDTDefaultCellEditor(kdtE2_xuanze2_CheckBox);
        this.kdtE2.getColumn("xuanze2").setEditor(kdtE2_xuanze2_CellEditor);
        KDFormattedTextField kdtE2_yeshu2_TextField = new KDFormattedTextField();
        kdtE2_yeshu2_TextField.setName("kdtE2_yeshu2_TextField");
        kdtE2_yeshu2_TextField.setVisible(true);
        kdtE2_yeshu2_TextField.setEditable(true);
        kdtE2_yeshu2_TextField.setHorizontalAlignment(2);
        kdtE2_yeshu2_TextField.setDataType(0);
        KDTDefaultCellEditor kdtE2_yeshu2_CellEditor = new KDTDefaultCellEditor(kdtE2_yeshu2_TextField);
        this.kdtE2.getColumn("yeshu2").setEditor(kdtE2_yeshu2_CellEditor);
        // kdtE3
		String kdtE3StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"yujingrenyuan\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{yujingrenyuan}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE3.setFormatXml(resHelper.translateString("kdtE3",kdtE3StrXML));

                this.kdtE3.putBindContents("editData",new String[] {"seq","yujingrenyuan"});


        this.kdtE3.checkParsed();
        final KDBizPromptBox kdtE3_yujingrenyuan_PromptBox = new KDBizPromptBox();
        kdtE3_yujingrenyuan_PromptBox.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
        kdtE3_yujingrenyuan_PromptBox.setVisible(true);
        kdtE3_yujingrenyuan_PromptBox.setEditable(true);
        kdtE3_yujingrenyuan_PromptBox.setDisplayFormat("$number$");
        kdtE3_yujingrenyuan_PromptBox.setEditFormat("$number$");
        kdtE3_yujingrenyuan_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE3_yujingrenyuan_CellEditor = new KDTDefaultCellEditor(kdtE3_yujingrenyuan_PromptBox);
        this.kdtE3.getColumn("yujingrenyuan").setEditor(kdtE3_yujingrenyuan_CellEditor);
        ObjectValueRender kdtE3_yujingrenyuan_OVR = new ObjectValueRender();
        kdtE3_yujingrenyuan_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE3.getColumn("yujingrenyuan").setRenderer(kdtE3_yujingrenyuan_OVR);
        // scrollPanejszlcyqk
        // txtjszlcyqk		
        this.txtjszlcyqk.setVisible(true);		
        this.txtjszlcyqk.setRequired(false);		
        this.txtjszlcyqk.setMaxLength(255);
        // scrollPanejsydti
        // txtjsydti		
        this.txtjsydti.setVisible(true);		
        this.txtjsydti.setRequired(false);		
        this.txtjsydti.setMaxLength(255);
        // pkjswcsjyq		
        this.pkjswcsjyq.setRequired(false);
        // contgzjjmghfwms		
        this.contgzjjmghfwms.setBoundLabelText(resHelper.getString("contgzjjmghfwms.boundLabelText"));		
        this.contgzjjmghfwms.setBoundLabelLength(20);		
        this.contgzjjmghfwms.setBoundLabelUnderline(true);		
        this.contgzjjmghfwms.setVisible(true);		
        this.contgzjjmghfwms.setBoundLabelAlignment(8);
        // contzlfhyj		
        this.contzlfhyj.setBoundLabelText(resHelper.getString("contzlfhyj.boundLabelText"));		
        this.contzlfhyj.setBoundLabelLength(20);		
        this.contzlfhyj.setBoundLabelUnderline(true);		
        this.contzlfhyj.setVisible(true);		
        this.contzlfhyj.setBoundLabelAlignment(8);
        // contsptsyfw		
        this.contsptsyfw.setBoundLabelText(resHelper.getString("contsptsyfw.boundLabelText"));		
        this.contsptsyfw.setBoundLabelLength(20);		
        this.contsptsyfw.setBoundLabelUnderline(true);		
        this.contsptsyfw.setVisible(true);		
        this.contsptsyfw.setBoundLabelAlignment(8);
        // contjgclfw		
        this.contjgclfw.setBoundLabelText(resHelper.getString("contjgclfw.boundLabelText"));		
        this.contjgclfw.setBoundLabelLength(20);		
        this.contjgclfw.setBoundLabelUnderline(true);		
        this.contjgclfw.setVisible(true);		
        this.contjgclfw.setBoundLabelAlignment(8);
        // contwycfts		
        this.contwycfts.setBoundLabelText(resHelper.getString("contwycfts.boundLabelText"));		
        this.contwycfts.setBoundLabelLength(20);		
        this.contwycfts.setBoundLabelUnderline(true);		
        this.contwycfts.setVisible(true);		
        this.contwycfts.setBoundLabelAlignment(8);
        // contsdfjnqk		
        this.contsdfjnqk.setBoundLabelText(resHelper.getString("contsdfjnqk.boundLabelText"));		
        this.contsdfjnqk.setBoundLabelLength(20);		
        this.contsdfjnqk.setBoundLabelUnderline(true);		
        this.contsdfjnqk.setVisible(true);		
        this.contsdfjnqk.setBoundLabelAlignment(8);
        // contzlaqgq		
        this.contzlaqgq.setBoundLabelText(resHelper.getString("contzlaqgq.boundLabelText"));		
        this.contzlaqgq.setBoundLabelLength(20);		
        this.contzlaqgq.setBoundLabelUnderline(true);		
        this.contzlaqgq.setVisible(true);		
        this.contzlaqgq.setBoundLabelAlignment(8);
        // chkqizhu		
        this.chkqizhu.setText(resHelper.getString("chkqizhu.text"));		
        this.chkqizhu.setHorizontalAlignment(2);
        // chkfenshua		
        this.chkfenshua.setText(resHelper.getString("chkfenshua.text"));		
        this.chkfenshua.setHorizontalAlignment(2);
        // chkdimian		
        this.chkdimian.setText(resHelper.getString("chkdimian.text"));		
        this.chkdimian.setHorizontalAlignment(2);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));
        // scrollPanegzjjmghfwms
        // txtgzjjmghfwms		
        this.txtgzjjmghfwms.setRequired(false);		
        this.txtgzjjmghfwms.setMaxLength(255);
        // scrollPanezlfhyj
        // txtzlfhyj		
        this.txtzlfhyj.setRequired(false);		
        this.txtzlfhyj.setMaxLength(255);
        // scrollPanesptsyfw
        // txtsptsyfw		
        this.txtsptsyfw.setRequired(false);		
        this.txtsptsyfw.setMaxLength(255);
        // scrollPanejgclfw
        // txtjgclfw		
        this.txtjgclfw.setRequired(false);		
        this.txtjgclfw.setMaxLength(255);
        // scrollPanewycfts
        // txtwycfts		
        this.txtwycfts.setRequired(false);		
        this.txtwycfts.setMaxLength(255);
        // scrollPanesdfjnqk
        // txtsdfjnqk		
        this.txtsdfjnqk.setRequired(false);		
        this.txtsdfjnqk.setMaxLength(255);
        // scrollPanezlaqgq
        // txtzlaqgq		
        this.txtzlaqgq.setRequired(false);		
        this.txtzlaqgq.setMaxLength(255);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setTimeEnabled(true);		
        this.kDDateLastUpdateTime.setEnabled(false);
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setEnabled(false);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // pkBizDate		
        this.pkBizDate.setEnabled(true);
        // billState		
        this.billState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.settle.app.BillStateEnum").toArray());		
        this.billState.setRequired(false);
        // prmtcontractNumber		
        this.prmtcontractNumber.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillQuery");		
        this.prmtcontractNumber.setEditable(true);		
        this.prmtcontractNumber.setDisplayFormat("$codingNumber$");		
        this.prmtcontractNumber.setEditFormat("$number$");		
        this.prmtcontractNumber.setCommitFormat("$number$");		
        this.prmtcontractNumber.setRequired(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));
        // btnInTrial
        this.btnInTrial.setAction((IItemAction)ActionProxyFactory.getProxy(actionInTrial, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnInTrial.setText(resHelper.getString("btnInTrial.text"));
        // btnApproved
        this.btnApproved.setAction((IItemAction)ActionProxyFactory.getProxy(actionApproved, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnApproved.setText(resHelper.getString("btnApproved.text"));
        this.btnApproved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnApproved_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtdecAmount,txtapprovalAmount,txtversion,txtactualConstruction,txtsettleHead,txtlinkTel,pkstartTime,pkendTime,pksubTime,txtunitPrice,state,pkjswcsjyq,billState,txtgzjjmghfwms,txtzlfhyj,txtsptsyfw,txtjgclfw,txtwycfts,txtsdfjnqk,txtzlaqgq,chkqizhu,chkfenshua,chkdimian,prmtcontractNumber,kDDateLastUpdateTime,prmtLastUpdateUser,kDDateCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,kdtE3,kdtE2,kdtE1,txtjszlcyqk,txtjsydti,chkisVersion}));
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
        this.setBounds(new Rectangle(0, -15, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, -15, 1013, 629));
        contdecAmount.setBounds(new Rectangle(285, 3, 200, 19));
        this.add(contdecAmount, new KDLayout.Constraints(285, 3, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contversion.setBounds(new Rectangle(787, 26, 200, 19));
        this.add(contversion, new KDLayout.Constraints(787, 26, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contapprovalAmount.setBounds(new Rectangle(561, 3, 200, 19));
        this.add(contapprovalAmount, new KDLayout.Constraints(561, 3, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contactualConstruction.setBounds(new Rectangle(285, 30, 200, 19));
        this.add(contactualConstruction, new KDLayout.Constraints(285, 30, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsettleHead.setBounds(new Rectangle(561, 26, 200, 19));
        this.add(contsettleHead, new KDLayout.Constraints(561, 26, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contlinkTel.setBounds(new Rectangle(787, 50, 200, 19));
        this.add(contlinkTel, new KDLayout.Constraints(787, 50, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contstartTime.setBounds(new Rectangle(9, 49, 200, 19));
        this.add(contstartTime, new KDLayout.Constraints(9, 49, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contendTime.setBounds(new Rectangle(285, 52, 200, 19));
        this.add(contendTime, new KDLayout.Constraints(285, 52, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsubTime.setBounds(new Rectangle(561, 50, 200, 19));
        this.add(contsubTime, new KDLayout.Constraints(561, 50, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contunitPrice.setBounds(new Rectangle(285, 73, 200, 19));
        this.add(contunitPrice, new KDLayout.Constraints(285, 73, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contstate.setBounds(new Rectangle(561, 73, 200, 19));
        this.add(contstate, new KDLayout.Constraints(561, 73, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel3.setBounds(new Rectangle(5, 278, 980, 323));
        this.add(kDPanel3, new KDLayout.Constraints(5, 278, 980, 323, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel4.setBounds(new Rectangle(9, 99, 980, 167));
        this.add(kDPanel4, new KDLayout.Constraints(9, 99, 980, 167, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateUser.setBounds(new Rectangle(686, 606, 27, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(686, 606, 27, 19, 0));
        contCreator.setBounds(new Rectangle(741, 608, 47, 21));
        this.add(contCreator, new KDLayout.Constraints(741, 608, 47, 21, 0));
        contLastUpdateTime.setBounds(new Rectangle(790, 610, 34, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(790, 610, 34, 19, 0));
        contCreateTime.setBounds(new Rectangle(828, 609, 35, 19));
        this.add(contCreateTime, new KDLayout.Constraints(828, 609, 35, 19, 0));
        contDescription.setBounds(new Rectangle(870, 611, 43, 19));
        this.add(contDescription, new KDLayout.Constraints(870, 611, 43, 19, 0));
        contBizDate.setBounds(new Rectangle(915, 610, 55, 19));
        this.add(contBizDate, new KDLayout.Constraints(915, 610, 55, 19, 0));
        contbillState.setBounds(new Rectangle(787, 73, 200, 19));
        this.add(contbillState, new KDLayout.Constraints(787, 73, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contcontractNumber.setBounds(new Rectangle(9, 3, 200, 19));
        this.add(contcontractNumber, new KDLayout.Constraints(9, 3, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(9, 74, 200, 19));
        this.add(contAuditor, new KDLayout.Constraints(9, 74, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(9, 28, 200, 19));
        this.add(contNumber, new KDLayout.Constraints(9, 28, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkisVersion.setBounds(new Rectangle(787, 3, 200, 19));
        this.add(chkisVersion, new KDLayout.Constraints(787, 3, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contdecAmount
        contdecAmount.setBoundEditor(txtdecAmount);
        //contversion
        contversion.setBoundEditor(txtversion);
        //contapprovalAmount
        contapprovalAmount.setBoundEditor(txtapprovalAmount);
        //contactualConstruction
        contactualConstruction.setBoundEditor(txtactualConstruction);
        //contsettleHead
        contsettleHead.setBoundEditor(txtsettleHead);
        //contlinkTel
        contlinkTel.setBoundEditor(txtlinkTel);
        //contstartTime
        contstartTime.setBoundEditor(pkstartTime);
        //contendTime
        contendTime.setBoundEditor(pkendTime);
        //contsubTime
        contsubTime.setBoundEditor(pksubTime);
        //contunitPrice
        contunitPrice.setBoundEditor(txtunitPrice);
        //contstate
        contstate.setBoundEditor(state);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(5, 278, 980, 323));        kDTabbedPane1.setBounds(new Rectangle(12, 16, 693, 297));
        kDPanel3.add(kDTabbedPane1, new KDLayout.Constraints(12, 16, 693, 297, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contjszlcyqk.setBounds(new Rectangle(750, 33, 200, 60));
        kDPanel3.add(contjszlcyqk, new KDLayout.Constraints(750, 33, 200, 60, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contjsydti.setBounds(new Rectangle(750, 139, 200, 60));
        kDPanel3.add(contjsydti, new KDLayout.Constraints(750, 139, 200, 60, 0));
        contjswcsjyq.setBounds(new Rectangle(750, 246, 200, 19));
        kDPanel3.add(contjswcsjyq, new KDLayout.Constraints(750, 246, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane1.add(kDPanel5, resHelper.getString("kDPanel5.constraints"));
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kdtE1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE1,new com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE1Info(),null,false);
        kDPanel1.add(kdtE1_detailPanel, BorderLayout.CENTER);
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kdtE2_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE2,new com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE2Info(),null,false);
        kDPanel2.add(kdtE2_detailPanel, BorderLayout.CENTER);
        //kDPanel5
kDPanel5.setLayout(new BorderLayout(0, 0));        kdtE3_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE3,new com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE3Info(),null,false);
        kDPanel5.add(kdtE3_detailPanel, BorderLayout.CENTER);
        //contjszlcyqk
        contjszlcyqk.setBoundEditor(scrollPanejszlcyqk);
        //scrollPanejszlcyqk
        scrollPanejszlcyqk.getViewport().add(txtjszlcyqk, null);
        //contjsydti
        contjsydti.setBoundEditor(scrollPanejsydti);
        //scrollPanejsydti
        scrollPanejsydti.getViewport().add(txtjsydti, null);
        //contjswcsjyq
        contjswcsjyq.setBoundEditor(pkjswcsjyq);
        //kDPanel4
        kDPanel4.setLayout(new KDLayout());
        kDPanel4.putClientProperty("OriginalBounds", new Rectangle(9, 99, 980, 167));        contgzjjmghfwms.setBounds(new Rectangle(20, 17, 200, 60));
        kDPanel4.add(contgzjjmghfwms, new KDLayout.Constraints(20, 17, 200, 60, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contzlfhyj.setBounds(new Rectangle(263, 17, 200, 60));
        kDPanel4.add(contzlfhyj, new KDLayout.Constraints(263, 17, 200, 60, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsptsyfw.setBounds(new Rectangle(506, 17, 200, 60));
        kDPanel4.add(contsptsyfw, new KDLayout.Constraints(506, 17, 200, 60, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contjgclfw.setBounds(new Rectangle(749, 17, 200, 60));
        kDPanel4.add(contjgclfw, new KDLayout.Constraints(749, 17, 200, 60, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contwycfts.setBounds(new Rectangle(20, 86, 200, 60));
        kDPanel4.add(contwycfts, new KDLayout.Constraints(20, 86, 200, 60, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsdfjnqk.setBounds(new Rectangle(263, 86, 200, 60));
        kDPanel4.add(contsdfjnqk, new KDLayout.Constraints(263, 86, 200, 60, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contzlaqgq.setBounds(new Rectangle(506, 86, 200, 60));
        kDPanel4.add(contzlaqgq, new KDLayout.Constraints(506, 86, 200, 60, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkqizhu.setBounds(new Rectangle(749, 127, 60, 19));
        kDPanel4.add(chkqizhu, new KDLayout.Constraints(749, 127, 60, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkfenshua.setBounds(new Rectangle(819, 127, 60, 19));
        kDPanel4.add(chkfenshua, new KDLayout.Constraints(819, 127, 60, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkdimian.setBounds(new Rectangle(889, 127, 60, 19));
        kDPanel4.add(chkdimian, new KDLayout.Constraints(889, 127, 60, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(749, 86, 115, 19));
        kDPanel4.add(kDLabelContainer1, new KDLayout.Constraints(749, 86, 115, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contgzjjmghfwms
        contgzjjmghfwms.setBoundEditor(scrollPanegzjjmghfwms);
        //scrollPanegzjjmghfwms
        scrollPanegzjjmghfwms.getViewport().add(txtgzjjmghfwms, null);
        //contzlfhyj
        contzlfhyj.setBoundEditor(scrollPanezlfhyj);
        //scrollPanezlfhyj
        scrollPanezlfhyj.getViewport().add(txtzlfhyj, null);
        //contsptsyfw
        contsptsyfw.setBoundEditor(scrollPanesptsyfw);
        //scrollPanesptsyfw
        scrollPanesptsyfw.getViewport().add(txtsptsyfw, null);
        //contjgclfw
        contjgclfw.setBoundEditor(scrollPanejgclfw);
        //scrollPanejgclfw
        scrollPanejgclfw.getViewport().add(txtjgclfw, null);
        //contwycfts
        contwycfts.setBoundEditor(scrollPanewycfts);
        //scrollPanewycfts
        scrollPanewycfts.getViewport().add(txtwycfts, null);
        //contsdfjnqk
        contsdfjnqk.setBoundEditor(scrollPanesdfjnqk);
        //scrollPanesdfjnqk
        scrollPanesdfjnqk.getViewport().add(txtsdfjnqk, null);
        //contzlaqgq
        contzlaqgq.setBoundEditor(scrollPanezlaqgq);
        //scrollPanezlaqgq
        scrollPanezlaqgq.getViewport().add(txtzlaqgq, null);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDateLastUpdateTime);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contbillState
        contbillState.setBoundEditor(billState);
        //contcontractNumber
        contcontractNumber.setBoundEditor(prmtcontractNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contNumber
        contNumber.setBoundEditor(txtNumber);

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
        this.toolBar.add(btnInTrial);
        this.toolBar.add(btnApproved);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isVersion", boolean.class, this.chkisVersion, "selected");
		dataBinder.registerBinding("decAmount", java.math.BigDecimal.class, this.txtdecAmount, "value");
		dataBinder.registerBinding("version", String.class, this.txtversion, "text");
		dataBinder.registerBinding("approvalAmount", java.math.BigDecimal.class, this.txtapprovalAmount, "value");
		dataBinder.registerBinding("actualConstruction", String.class, this.txtactualConstruction, "text");
		dataBinder.registerBinding("settleHead", String.class, this.txtsettleHead, "text");
		dataBinder.registerBinding("linkTel", String.class, this.txtlinkTel, "text");
		dataBinder.registerBinding("startTime", java.util.Date.class, this.pkstartTime, "value");
		dataBinder.registerBinding("endTime", java.util.Date.class, this.pkendTime, "value");
		dataBinder.registerBinding("subTime", java.util.Date.class, this.pksubTime, "value");
		dataBinder.registerBinding("unitPrice", String.class, this.txtunitPrice, "text");
		dataBinder.registerBinding("state", com.kingdee.eas.basedata.assistant.AccountBillStateEnum.class, this.state, "selectedItem");
		dataBinder.registerBinding("E1.seq", int.class, this.kdtE1, "seq.text");
		dataBinder.registerBinding("E1", com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE1Info.class, this.kdtE1, "userObject");
		dataBinder.registerBinding("E1.sbzl", String.class, this.kdtE1, "sbzl.text");
		dataBinder.registerBinding("E1.yeshu", int.class, this.kdtE1, "yeshu.text");
		dataBinder.registerBinding("E1.xuanze", boolean.class, this.kdtE1, "xuanze.text");
		dataBinder.registerBinding("E2.seq", int.class, this.kdtE2, "seq.text");
		dataBinder.registerBinding("E2", com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE2Info.class, this.kdtE2, "userObject");
		dataBinder.registerBinding("E2.sbzl2", String.class, this.kdtE2, "sbzl2.text");
		dataBinder.registerBinding("E2.xuanze2", boolean.class, this.kdtE2, "xuanze2.text");
		dataBinder.registerBinding("E2.yeshu2", int.class, this.kdtE2, "yeshu2.text");
		dataBinder.registerBinding("E3.seq", int.class, this.kdtE3, "seq.text");
		dataBinder.registerBinding("E3", com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE3Info.class, this.kdtE3, "userObject");
		dataBinder.registerBinding("E3.yujingrenyuan", java.lang.Object.class, this.kdtE3, "yujingrenyuan.text");
		dataBinder.registerBinding("jszlcyqk", String.class, this.txtjszlcyqk, "text");
		dataBinder.registerBinding("jsydti", String.class, this.txtjsydti, "text");
		dataBinder.registerBinding("jswcsjyq", java.util.Date.class, this.pkjswcsjyq, "value");
		dataBinder.registerBinding("qizhu", boolean.class, this.chkqizhu, "selected");
		dataBinder.registerBinding("fenshua", boolean.class, this.chkfenshua, "selected");
		dataBinder.registerBinding("dimian", boolean.class, this.chkdimian, "selected");
		dataBinder.registerBinding("gzjjmghfwms", String.class, this.txtgzjjmghfwms, "text");
		dataBinder.registerBinding("zlfhyj", String.class, this.txtzlfhyj, "text");
		dataBinder.registerBinding("sptsyfw", String.class, this.txtsptsyfw, "text");
		dataBinder.registerBinding("jgclfw", String.class, this.txtjgclfw, "text");
		dataBinder.registerBinding("wycfts", String.class, this.txtwycfts, "text");
		dataBinder.registerBinding("sdfjnqk", String.class, this.txtsdfjnqk, "text");
		dataBinder.registerBinding("zlaqgq", String.class, this.txtzlaqgq, "text");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("billState", com.kingdee.eas.cp.odm.BillStatusEnum.class, this.billState, "selectedItem");
		dataBinder.registerBinding("contractNumber", com.kingdee.eas.fdc.contract.ContractBillInfo.class, this.prmtcontractNumber, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.settle.app.SettleDeclarationBillEditUIHandler";
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
        this.txtdecAmount.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo)ov;
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
		getValidateHelper().registerBindProperty("isVersion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("decAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("version", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("approvalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actualConstruction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settleHead", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkTel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("subTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unitPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.sbzl", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.yeshu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.xuanze", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.sbzl2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.xuanze2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.yeshu2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.yujingrenyuan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jszlcyqk", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jsydti", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jswcsjyq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fenshua", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dimian", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("gzjjmghfwms", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zlfhyj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sptsyfw", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jgclfw", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("wycfts", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sdfjnqk", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zlaqgq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    		
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
     * output btnApproved_actionPerformed method
     */
    protected void btnApproved_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("isVersion"));
        sic.add(new SelectorItemInfo("decAmount"));
        sic.add(new SelectorItemInfo("version"));
        sic.add(new SelectorItemInfo("approvalAmount"));
        sic.add(new SelectorItemInfo("actualConstruction"));
        sic.add(new SelectorItemInfo("settleHead"));
        sic.add(new SelectorItemInfo("linkTel"));
        sic.add(new SelectorItemInfo("startTime"));
        sic.add(new SelectorItemInfo("endTime"));
        sic.add(new SelectorItemInfo("subTime"));
        sic.add(new SelectorItemInfo("unitPrice"));
        sic.add(new SelectorItemInfo("state"));
    	sic.add(new SelectorItemInfo("E1.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E1.sbzl"));
    	sic.add(new SelectorItemInfo("E1.yeshu"));
    	sic.add(new SelectorItemInfo("E1.xuanze"));
    	sic.add(new SelectorItemInfo("E2.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E2.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E2.sbzl2"));
    	sic.add(new SelectorItemInfo("E2.xuanze2"));
    	sic.add(new SelectorItemInfo("E2.yeshu2"));
    	sic.add(new SelectorItemInfo("E3.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E3.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E3.yujingrenyuan.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E3.yujingrenyuan.id"));
			sic.add(new SelectorItemInfo("E3.yujingrenyuan.name"));
        	sic.add(new SelectorItemInfo("E3.yujingrenyuan.number"));
		}
        sic.add(new SelectorItemInfo("jszlcyqk"));
        sic.add(new SelectorItemInfo("jsydti"));
        sic.add(new SelectorItemInfo("jswcsjyq"));
        sic.add(new SelectorItemInfo("qizhu"));
        sic.add(new SelectorItemInfo("fenshua"));
        sic.add(new SelectorItemInfo("dimian"));
        sic.add(new SelectorItemInfo("gzjjmghfwms"));
        sic.add(new SelectorItemInfo("zlfhyj"));
        sic.add(new SelectorItemInfo("sptsyfw"));
        sic.add(new SelectorItemInfo("jgclfw"));
        sic.add(new SelectorItemInfo("wycfts"));
        sic.add(new SelectorItemInfo("sdfjnqk"));
        sic.add(new SelectorItemInfo("zlaqgq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
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
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("billState"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractNumber.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractNumber.id"));
        	sic.add(new SelectorItemInfo("contractNumber.number"));
        	sic.add(new SelectorItemInfo("contractNumber.name"));
        	sic.add(new SelectorItemInfo("contractNumber.codingNumber"));
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
        sic.add(new SelectorItemInfo("number"));
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
     * output actionInTrial_actionPerformed method
     */
    public void actionInTrial_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionApproved_actionPerformed method
     */
    public void actionApproved_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionInTrial(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInTrial() {
    	return false;
    }
	public RequestContext prepareActionApproved(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionApproved() {
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

    /**
     * output ActionInTrial class
     */     
    protected class ActionInTrial extends ItemAction {     
    
        public ActionInTrial()
        {
            this(null);
        }

        public ActionInTrial(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionInTrial.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInTrial.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInTrial.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSettleDeclarationBillEditUI.this, "ActionInTrial", "actionInTrial_actionPerformed", e);
        }
    }

    /**
     * output ActionApproved class
     */     
    protected class ActionApproved extends ItemAction {     
    
        public ActionApproved()
        {
            this(null);
        }

        public ActionApproved(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionApproved.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproved.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproved.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSettleDeclarationBillEditUI.this, "ActionApproved", "actionApproved_actionPerformed", e);
        }
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractSettleDeclarationBillEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractSettleDeclarationBillEditUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.settle.client", "SettleDeclarationBillEditUI");
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
        return com.kingdee.eas.fdc.contract.settle.client.SettleDeclarationBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo objectValue = new com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/contract/settle/SettleDeclarationBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.settle.app.SettleDeclarationBillQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtE1;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
				vo.put("decAmount",new java.math.BigDecimal(0));
vo.put("billState",new Integer(20));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}