/**
 * output package name
 */
package com.kingdee.eas.port.pm.invite.client;

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
public abstract class AbstractInvitePlanEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInvitePlanEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contendDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contplanName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdepartment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contresponse;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contproject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continDate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkShortlistedInv;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBusinessFinish;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contopeningBidTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSendOutTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contscalingTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkstartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkendDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtplanName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtdepartment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtresponse;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0009;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneBIMUDF0009;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtBIMUDF0009;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtproject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinviteType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkinDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBusinessFinish;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkopeningBidTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkSendOutTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkscalingTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.eas.port.pm.invite.InvitePlanInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractInvitePlanEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInvitePlanEditUI.class.getName());
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
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
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
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contstartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contendDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contplanName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdepartment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contresponse = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contproject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkShortlistedInv = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contBusinessFinish = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contopeningBidTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSendOutTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contscalingTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkstartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkendDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtplanName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtdepartment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtresponse = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBIMUDF0009 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPaneBIMUDF0009 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtBIMUDF0009 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtproject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtinviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkinDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkBusinessFinish = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkopeningBidTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkSendOutTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkscalingTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contCU.setName("contCU");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contBizStatus.setName("contBizStatus");
        this.kDPanel1.setName("kDPanel1");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.prmtCU.setName("prmtCU");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.comboBizStatus.setName("comboBizStatus");
        this.contNumber.setName("contNumber");
        this.contStatus.setName("contStatus");
        this.contstartDate.setName("contstartDate");
        this.contendDate.setName("contendDate");
        this.contplanName.setName("contplanName");
        this.contdepartment.setName("contdepartment");
        this.contresponse.setName("contresponse");
        this.kDContainer1.setName("kDContainer1");
        this.contproject.setName("contproject");
        this.continviteType.setName("continviteType");
        this.continDate.setName("continDate");
        this.chkShortlistedInv.setName("chkShortlistedInv");
        this.contBusinessFinish.setName("contBusinessFinish");
        this.contopeningBidTime.setName("contopeningBidTime");
        this.contSendOutTime.setName("contSendOutTime");
        this.contscalingTime.setName("contscalingTime");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contAuditTime.setName("contAuditTime");
        this.contAuditor.setName("contAuditor");
        this.kDSeparator8.setName("kDSeparator8");
        this.kDSeparator9.setName("kDSeparator9");
        this.txtNumber.setName("txtNumber");
        this.comboStatus.setName("comboStatus");
        this.pkstartDate.setName("pkstartDate");
        this.pkendDate.setName("pkendDate");
        this.txtplanName.setName("txtplanName");
        this.prmtdepartment.setName("prmtdepartment");
        this.prmtresponse.setName("prmtresponse");
        this.contBIMUDF0009.setName("contBIMUDF0009");
        this.scrollPaneBIMUDF0009.setName("scrollPaneBIMUDF0009");
        this.txtBIMUDF0009.setName("txtBIMUDF0009");
        this.prmtproject.setName("prmtproject");
        this.prmtinviteType.setName("prmtinviteType");
        this.pkinDate.setName("pkinDate");
        this.pkBusinessFinish.setName("pkBusinessFinish");
        this.pkopeningBidTime.setName("pkopeningBidTime");
        this.pkSendOutTime.setName("pkSendOutTime");
        this.pkscalingTime.setName("pkscalingTime");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtAuditor.setName("prmtAuditor");
        // CoreUI		
        this.setPreferredSize(new Dimension(902,455));
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);
        // contCU		
        this.contCU.setBoundLabelText(resHelper.getString("contCU.boundLabelText"));		
        this.contCU.setBoundLabelLength(100);		
        this.contCU.setBoundLabelUnderline(true);		
        this.contCU.setEnabled(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contBizStatus		
        this.contBizStatus.setBoundLabelText(resHelper.getString("contBizStatus.boundLabelText"));		
        this.contBizStatus.setBoundLabelLength(100);		
        this.contBizStatus.setBoundLabelUnderline(true);		
        this.contBizStatus.setEnabled(false);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);		
        this.prmtLastUpdateUser.setDisplayFormat("$name$");		
        this.prmtLastUpdateUser.setEditFormat("$name$");		
        this.prmtLastUpdateUser.setCommitFormat("$name$");
        // pkLastUpdateTime		
        this.pkLastUpdateTime.setTimeEnabled(true);		
        this.pkLastUpdateTime.setEnabled(false);
        // prmtCU		
        this.prmtCU.setEnabled(false);
        // pkBizDate
        // txtDescription
        // comboBizStatus		
        this.comboBizStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBizActionEnum").toArray());		
        this.comboBizStatus.setEnabled(false);		
        this.comboBizStatus.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);
        // contstartDate		
        this.contstartDate.setBoundLabelText(resHelper.getString("contstartDate.boundLabelText"));		
        this.contstartDate.setBoundLabelLength(100);		
        this.contstartDate.setBoundLabelUnderline(true);		
        this.contstartDate.setVisible(true);
        // contendDate		
        this.contendDate.setBoundLabelText(resHelper.getString("contendDate.boundLabelText"));		
        this.contendDate.setBoundLabelLength(100);		
        this.contendDate.setBoundLabelUnderline(true);		
        this.contendDate.setVisible(true);
        // contplanName		
        this.contplanName.setBoundLabelText(resHelper.getString("contplanName.boundLabelText"));		
        this.contplanName.setBoundLabelLength(100);		
        this.contplanName.setBoundLabelUnderline(true);		
        this.contplanName.setVisible(true);
        // contdepartment		
        this.contdepartment.setBoundLabelText(resHelper.getString("contdepartment.boundLabelText"));		
        this.contdepartment.setBoundLabelLength(100);		
        this.contdepartment.setBoundLabelUnderline(true);		
        this.contdepartment.setVisible(true);
        // contresponse		
        this.contresponse.setBoundLabelText(resHelper.getString("contresponse.boundLabelText"));		
        this.contresponse.setBoundLabelLength(100);		
        this.contresponse.setBoundLabelUnderline(true);		
        this.contresponse.setVisible(true);
        // kDContainer1		
        this.kDContainer1.setEnableActive(false);		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // contproject		
        this.contproject.setBoundLabelText(resHelper.getString("contproject.boundLabelText"));		
        this.contproject.setBoundLabelLength(100);		
        this.contproject.setBoundLabelUnderline(true);		
        this.contproject.setVisible(true);
        // continviteType		
        this.continviteType.setBoundLabelText(resHelper.getString("continviteType.boundLabelText"));		
        this.continviteType.setBoundLabelLength(100);		
        this.continviteType.setBoundLabelUnderline(true);		
        this.continviteType.setVisible(true);
        // continDate		
        this.continDate.setBoundLabelText(resHelper.getString("continDate.boundLabelText"));		
        this.continDate.setBoundLabelLength(100);		
        this.continDate.setBoundLabelUnderline(true);		
        this.continDate.setVisible(true);
        // chkShortlistedInv		
        this.chkShortlistedInv.setText(resHelper.getString("chkShortlistedInv.text"));		
        this.chkShortlistedInv.setVisible(true);		
        this.chkShortlistedInv.setHorizontalAlignment(2);
        // contBusinessFinish		
        this.contBusinessFinish.setBoundLabelText(resHelper.getString("contBusinessFinish.boundLabelText"));		
        this.contBusinessFinish.setBoundLabelLength(100);		
        this.contBusinessFinish.setBoundLabelUnderline(true);		
        this.contBusinessFinish.setVisible(true);
        // contopeningBidTime		
        this.contopeningBidTime.setBoundLabelText(resHelper.getString("contopeningBidTime.boundLabelText"));		
        this.contopeningBidTime.setBoundLabelLength(100);		
        this.contopeningBidTime.setBoundLabelUnderline(true);		
        this.contopeningBidTime.setVisible(true);
        // contSendOutTime		
        this.contSendOutTime.setBoundLabelText(resHelper.getString("contSendOutTime.boundLabelText"));		
        this.contSendOutTime.setBoundLabelLength(100);		
        this.contSendOutTime.setBoundLabelUnderline(true);		
        this.contSendOutTime.setVisible(true);
        // contscalingTime		
        this.contscalingTime.setBoundLabelText(resHelper.getString("contscalingTime.boundLabelText"));		
        this.contscalingTime.setBoundLabelLength(100);		
        this.contscalingTime.setBoundLabelUnderline(true);		
        this.contscalingTime.setVisible(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // kDSeparator8
        // kDSeparator9
        // txtNumber
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);
        // pkstartDate		
        this.pkstartDate.setVisible(true);		
        this.pkstartDate.setRequired(false);
        // pkendDate		
        this.pkendDate.setVisible(true);		
        this.pkendDate.setRequired(false);
        // txtplanName		
        this.txtplanName.setVisible(true);		
        this.txtplanName.setHorizontalAlignment(2);		
        this.txtplanName.setMaxLength(100);		
        this.txtplanName.setRequired(false);
        // prmtdepartment		
        this.prmtdepartment.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtdepartment.setVisible(true);		
        this.prmtdepartment.setEditable(true);		
        this.prmtdepartment.setDisplayFormat("$name$");		
        this.prmtdepartment.setEditFormat("$number$");		
        this.prmtdepartment.setCommitFormat("$number$");		
        this.prmtdepartment.setRequired(false);
        // prmtresponse		
        this.prmtresponse.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtresponse.setVisible(true);		
        this.prmtresponse.setEditable(true);		
        this.prmtresponse.setDisplayFormat("$name$");		
        this.prmtresponse.setEditFormat("$number$");		
        this.prmtresponse.setCommitFormat("$number$");		
        this.prmtresponse.setRequired(false);
        this.prmtresponse.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtresponse_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contBIMUDF0009		
        this.contBIMUDF0009.setBoundLabelText(resHelper.getString("contBIMUDF0009.boundLabelText"));		
        this.contBIMUDF0009.setBoundLabelLength(0);		
        this.contBIMUDF0009.setBoundLabelUnderline(true);		
        this.contBIMUDF0009.setVisible(true);		
        this.contBIMUDF0009.setBoundLabelAlignment(8);
        // scrollPaneBIMUDF0009
        // txtBIMUDF0009		
        this.txtBIMUDF0009.setVisible(true);		
        this.txtBIMUDF0009.setRequired(false);		
        this.txtBIMUDF0009.setMaxLength(255);
        // prmtproject		
        this.prmtproject.setQueryInfo("com.kingdee.eas.basedata.assistant.app.ProjectQuery");		
        this.prmtproject.setVisible(true);		
        this.prmtproject.setEditable(true);		
        this.prmtproject.setDisplayFormat("$name$");		
        this.prmtproject.setEditFormat("$number$");		
        this.prmtproject.setCommitFormat("$number$");		
        this.prmtproject.setRequired(false);
        // prmtinviteType		
        this.prmtinviteType.setQueryInfo("com.kingdee.eas.port.pm.base.app.InviteTypeQuery");		
        this.prmtinviteType.setVisible(true);		
        this.prmtinviteType.setEditable(true);		
        this.prmtinviteType.setDisplayFormat("$name$");		
        this.prmtinviteType.setEditFormat("$number$");		
        this.prmtinviteType.setCommitFormat("$number$");		
        this.prmtinviteType.setRequired(false);
        // pkinDate		
        this.pkinDate.setVisible(true);		
        this.pkinDate.setRequired(false);
        // pkBusinessFinish		
        this.pkBusinessFinish.setVisible(true);		
        this.pkBusinessFinish.setRequired(false);
        // pkopeningBidTime		
        this.pkopeningBidTime.setVisible(true);		
        this.pkopeningBidTime.setRequired(false);
        // pkSendOutTime		
        this.pkSendOutTime.setVisible(true);		
        this.pkSendOutTime.setRequired(false);
        // pkscalingTime		
        this.pkscalingTime.setVisible(true);		
        this.pkscalingTime.setRequired(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setCommitFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");		
        this.prmtCreator.setDisplayFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setTimeEnabled(true);		
        this.pkCreateTime.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtplanName,pkstartDate,pkendDate,pkinDate,prmtresponse,prmtdepartment,prmtproject}));
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
        this.setBounds(new Rectangle(10, 10, 902, 455));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 902, 455));
        contLastUpdateUser.setBounds(new Rectangle(388, 578, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(388, 578, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(722, 579, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(722, 579, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCU.setBounds(new Rectangle(303, 549, 270, 19));
        this.add(contCU, new KDLayout.Constraints(303, 549, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(145, 575, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(145, 575, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(292, 525, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(292, 525, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizStatus.setBounds(new Rectangle(292, 604, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(292, 604, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(2, 2, 896, 446));
        this.add(kDPanel1, new KDLayout.Constraints(2, 2, 896, 446, 0));
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contCU
        contCU.setBoundEditor(prmtCU);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contBizStatus
        contBizStatus.setBoundEditor(comboBizStatus);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(2, 2, 896, 446));        contNumber.setBounds(new Rectangle(17, 17, 270, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(17, 17, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStatus.setBounds(new Rectangle(608, 43, 270, 19));
        kDPanel1.add(contStatus, new KDLayout.Constraints(608, 43, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contstartDate.setBounds(new Rectangle(17, 103, 270, 19));
        kDPanel1.add(contstartDate, new KDLayout.Constraints(17, 103, 270, 19, 0));
        contendDate.setBounds(new Rectangle(312, 103, 270, 19));
        kDPanel1.add(contendDate, new KDLayout.Constraints(312, 103, 270, 19, 0));
        contplanName.setBounds(new Rectangle(17, 43, 270, 19));
        kDPanel1.add(contplanName, new KDLayout.Constraints(17, 43, 270, 19, 0));
        contdepartment.setBounds(new Rectangle(17, 69, 270, 19));
        kDPanel1.add(contdepartment, new KDLayout.Constraints(17, 69, 270, 19, 0));
        contresponse.setBounds(new Rectangle(312, 43, 270, 19));
        kDPanel1.add(contresponse, new KDLayout.Constraints(312, 43, 270, 19, 0));
        kDContainer1.setBounds(new Rectangle(17, 196, 862, 176));
        kDPanel1.add(kDContainer1, new KDLayout.Constraints(17, 196, 862, 176, 0));
        contproject.setBounds(new Rectangle(608, 17, 270, 19));
        kDPanel1.add(contproject, new KDLayout.Constraints(608, 17, 270, 19, 0));
        continviteType.setBounds(new Rectangle(312, 17, 270, 19));
        kDPanel1.add(continviteType, new KDLayout.Constraints(312, 17, 270, 19, 0));
        continDate.setBounds(new Rectangle(17, 156, 270, 19));
        kDPanel1.add(continDate, new KDLayout.Constraints(17, 156, 270, 19, 0));
        chkShortlistedInv.setBounds(new Rectangle(312, 69, 270, 19));
        kDPanel1.add(chkShortlistedInv, new KDLayout.Constraints(312, 69, 270, 19, 0));
        contBusinessFinish.setBounds(new Rectangle(608, 130, 270, 19));
        kDPanel1.add(contBusinessFinish, new KDLayout.Constraints(608, 130, 270, 19, 0));
        contopeningBidTime.setBounds(new Rectangle(17, 130, 270, 19));
        kDPanel1.add(contopeningBidTime, new KDLayout.Constraints(17, 130, 270, 19, 0));
        contSendOutTime.setBounds(new Rectangle(312, 130, 270, 19));
        kDPanel1.add(contSendOutTime, new KDLayout.Constraints(312, 130, 270, 19, 0));
        contscalingTime.setBounds(new Rectangle(608, 103, 270, 19));
        kDPanel1.add(contscalingTime, new KDLayout.Constraints(608, 103, 270, 19, 0));
        contCreator.setBounds(new Rectangle(17, 381, 270, 19));
        kDPanel1.add(contCreator, new KDLayout.Constraints(17, 381, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(17, 409, 270, 19));
        kDPanel1.add(contCreateTime, new KDLayout.Constraints(17, 409, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(312, 409, 270, 19));
        kDPanel1.add(contAuditTime, new KDLayout.Constraints(312, 409, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(312, 381, 270, 19));
        kDPanel1.add(contAuditor, new KDLayout.Constraints(312, 381, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator8.setBounds(new Rectangle(17, 96, 861, 10));
        kDPanel1.add(kDSeparator8, new KDLayout.Constraints(17, 96, 861, 10, 0));
        kDSeparator9.setBounds(new Rectangle(17, 182, 863, 10));
        kDPanel1.add(kDSeparator9, new KDLayout.Constraints(17, 182, 863, 10, 0));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //contstartDate
        contstartDate.setBoundEditor(pkstartDate);
        //contendDate
        contendDate.setBoundEditor(pkendDate);
        //contplanName
        contplanName.setBoundEditor(txtplanName);
        //contdepartment
        contdepartment.setBoundEditor(prmtdepartment);
        //contresponse
        contresponse.setBoundEditor(prmtresponse);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(contBIMUDF0009, BorderLayout.CENTER);
        //contBIMUDF0009
        contBIMUDF0009.setBoundEditor(scrollPaneBIMUDF0009);
        //scrollPaneBIMUDF0009
        scrollPaneBIMUDF0009.getViewport().add(txtBIMUDF0009, null);
        //contproject
        contproject.setBoundEditor(prmtproject);
        //continviteType
        continviteType.setBoundEditor(prmtinviteType);
        //continDate
        continDate.setBoundEditor(pkinDate);
        //contBusinessFinish
        contBusinessFinish.setBoundEditor(pkBusinessFinish);
        //contopeningBidTime
        contopeningBidTime.setBoundEditor(pkopeningBidTime);
        //contSendOutTime
        contSendOutTime.setBoundEditor(pkSendOutTime);
        //contscalingTime
        contscalingTime.setBoundEditor(pkscalingTime);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);

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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
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
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
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
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("ShortlistedInv", boolean.class, this.chkShortlistedInv, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("startDate", java.util.Date.class, this.pkstartDate, "value");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.pkendDate, "value");
		dataBinder.registerBinding("planName", String.class, this.txtplanName, "text");
		dataBinder.registerBinding("department", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtdepartment, "data");
		dataBinder.registerBinding("response", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtresponse, "data");
		dataBinder.registerBinding("BIMUDF0009", String.class, this.txtBIMUDF0009, "text");
		dataBinder.registerBinding("project", com.kingdee.eas.port.pm.base.EvaluationIndicatorsInfo.class, this.prmtproject, "data");
		dataBinder.registerBinding("inviteType", com.kingdee.eas.port.pm.base.InviteTypeInfo.class, this.prmtinviteType, "data");
		dataBinder.registerBinding("inDate", java.util.Date.class, this.pkinDate, "value");
		dataBinder.registerBinding("BusinessFinish", java.util.Date.class, this.pkBusinessFinish, "value");
		dataBinder.registerBinding("openingBidTime", java.util.Date.class, this.pkopeningBidTime, "value");
		dataBinder.registerBinding("SendOutTime", java.util.Date.class, this.pkSendOutTime, "value");
		dataBinder.registerBinding("scalingTime", java.util.Date.class, this.pkscalingTime, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invite.app.InvitePlanEditUIHandler";
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
        this.txtplanName.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.pm.invite.InvitePlanInfo)ov;
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
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ShortlistedInv", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("department", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("response", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BIMUDF0009", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BusinessFinish", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("openingBidTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SendOutTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scalingTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    		
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
     * output prmtresponse_dataChanged method
     */
    protected void prmtresponse_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
			sic.add(new SelectorItemInfo("CU.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("CU.id"));
        	sic.add(new SelectorItemInfo("CU.number"));
        	sic.add(new SelectorItemInfo("CU.name"));
		}
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("bizStatus"));
        sic.add(new SelectorItemInfo("ShortlistedInv"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("startDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("planName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("department.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("department.id"));
        	sic.add(new SelectorItemInfo("department.number"));
        	sic.add(new SelectorItemInfo("department.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("response.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("response.id"));
        	sic.add(new SelectorItemInfo("response.number"));
        	sic.add(new SelectorItemInfo("response.name"));
		}
        sic.add(new SelectorItemInfo("BIMUDF0009"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("project.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("project.id"));
        	sic.add(new SelectorItemInfo("project.number"));
        	sic.add(new SelectorItemInfo("project.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("inviteType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("inviteType.id"));
        	sic.add(new SelectorItemInfo("inviteType.number"));
        	sic.add(new SelectorItemInfo("inviteType.name"));
		}
        sic.add(new SelectorItemInfo("inDate"));
        sic.add(new SelectorItemInfo("BusinessFinish"));
        sic.add(new SelectorItemInfo("openingBidTime"));
        sic.add(new SelectorItemInfo("SendOutTime"));
        sic.add(new SelectorItemInfo("scalingTime"));
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
        sic.add(new SelectorItemInfo("auditTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
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
        return new MetaDataPK("com.kingdee.eas.port.pm.invite.client", "InvitePlanEditUI");
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
        return com.kingdee.eas.port.pm.invite.client.InvitePlanEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invite.InvitePlanFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invite.InvitePlanInfo objectValue = new com.kingdee.eas.port.pm.invite.InvitePlanInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/invite/InvitePlan";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.invite.app.InvitePlanQuery");
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
				try {vo.put("BusinessFinish",java.text.DateFormat.getDateInstance().parse("²¼¶û"));} catch (Exception pkBusinessFinish_exception) {}
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}