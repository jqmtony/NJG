/**
 * output package name
 */
package com.kingdee.eas.port.equipment.maintenance.client;

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
public abstract class AbstractRepairOrderEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRepairOrderEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contequName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contequModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contequAddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrepairDepart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrepairPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrepairTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrepairContent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contassignee;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contacceptTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmaintenanceType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmaintenanceProgram;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE1;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE1_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0021;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrepairDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contacceptSituation;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdeliveryPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrecipient;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttransferTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contslDepart;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkselfStudy;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkoutsourcing;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contselfAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contoutAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chksfjhn;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbaoxiuren;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbaoxiuTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtequName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtequModel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtequAddress;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrepairDepart;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrepairPerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkrepairTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtrepairContent;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtassignee;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkacceptTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtmaintenanceType;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanemaintenanceProgram;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtmaintenanceProgram;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneBIMUDF0021;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtBIMUDF0021;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkrepairDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtacceptSituation;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtdeliveryPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrecipient;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pktransferTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtslDepart;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtselfAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtoutAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtbaoxiuren;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbaoxiuTime;
    protected com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo editData = null;
    protected ActionToVoid actionToVoid = null;
    protected ActionUnToVoid actionUnToVoid = null;
    /**
     * output class constructor
     */
    public AbstractRepairOrderEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRepairOrderEditUI.class.getName());
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
        //actionToVoid
        this.actionToVoid = new ActionToVoid(this);
        getActionManager().registerAction("actionToVoid", actionToVoid);
        this.actionToVoid.setExtendProperty("canForewarn", "true");
        this.actionToVoid.setExtendProperty("userDefined", "true");
        this.actionToVoid.setExtendProperty("isObjectUpdateLock", "false");
         this.actionToVoid.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionToVoid.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionUnToVoid
        this.actionUnToVoid = new ActionUnToVoid(this);
        getActionManager().registerAction("actionUnToVoid", actionUnToVoid);
        this.actionUnToVoid.setExtendProperty("canForewarn", "true");
        this.actionUnToVoid.setExtendProperty("userDefined", "true");
        this.actionUnToVoid.setExtendProperty("isObjectUpdateLock", "false");
         this.actionUnToVoid.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionUnToVoid.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contequName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contequModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contequAddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrepairDepart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrepairPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrepairTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrepairContent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contassignee = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contacceptTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmaintenanceType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmaintenanceProgram = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtE1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contBIMUDF0021 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrepairDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contacceptSituation = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdeliveryPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrecipient = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttransferTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contslDepart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkselfStudy = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkoutsourcing = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contselfAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contoutAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chksfjhn = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contbaoxiuren = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbaoxiuTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtequName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtequModel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtequAddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtrepairDepart = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtrepairPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkrepairTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtrepairContent = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtassignee = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkacceptTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtmaintenanceType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.scrollPanemaintenanceProgram = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtmaintenanceProgram = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPaneBIMUDF0021 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtBIMUDF0021 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.pkrepairDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtacceptSituation = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtdeliveryPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtrecipient = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pktransferTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtslDepart = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtselfAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtoutAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtbaoxiuren = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkbaoxiuTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contCU.setName("contCU");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contStatus.setName("contStatus");
        this.contBizStatus.setName("contBizStatus");
        this.contAuditTime.setName("contAuditTime");
        this.contequName.setName("contequName");
        this.contequModel.setName("contequModel");
        this.contequAddress.setName("contequAddress");
        this.contrepairDepart.setName("contrepairDepart");
        this.contrepairPerson.setName("contrepairPerson");
        this.contrepairTime.setName("contrepairTime");
        this.contrepairContent.setName("contrepairContent");
        this.contassignee.setName("contassignee");
        this.contacceptTime.setName("contacceptTime");
        this.contmaintenanceType.setName("contmaintenanceType");
        this.contmaintenanceProgram.setName("contmaintenanceProgram");
        this.kdtE1.setName("kdtE1");
        this.contBIMUDF0021.setName("contBIMUDF0021");
        this.contrepairDate.setName("contrepairDate");
        this.contacceptSituation.setName("contacceptSituation");
        this.contdeliveryPerson.setName("contdeliveryPerson");
        this.contrecipient.setName("contrecipient");
        this.conttransferTime.setName("conttransferTime");
        this.contslDepart.setName("contslDepart");
        this.chkselfStudy.setName("chkselfStudy");
        this.chkoutsourcing.setName("chkoutsourcing");
        this.contselfAmount.setName("contselfAmount");
        this.contoutAmount.setName("contoutAmount");
        this.chksfjhn.setName("chksfjhn");
        this.contbaoxiuren.setName("contbaoxiuren");
        this.contbaoxiuTime.setName("contbaoxiuTime");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.prmtCU.setName("prmtCU");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.comboStatus.setName("comboStatus");
        this.comboBizStatus.setName("comboBizStatus");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtequName.setName("prmtequName");
        this.txtequModel.setName("txtequModel");
        this.txtequAddress.setName("txtequAddress");
        this.prmtrepairDepart.setName("prmtrepairDepart");
        this.prmtrepairPerson.setName("prmtrepairPerson");
        this.pkrepairTime.setName("pkrepairTime");
        this.txtrepairContent.setName("txtrepairContent");
        this.prmtassignee.setName("prmtassignee");
        this.pkacceptTime.setName("pkacceptTime");
        this.prmtmaintenanceType.setName("prmtmaintenanceType");
        this.scrollPanemaintenanceProgram.setName("scrollPanemaintenanceProgram");
        this.txtmaintenanceProgram.setName("txtmaintenanceProgram");
        this.scrollPaneBIMUDF0021.setName("scrollPaneBIMUDF0021");
        this.txtBIMUDF0021.setName("txtBIMUDF0021");
        this.pkrepairDate.setName("pkrepairDate");
        this.txtacceptSituation.setName("txtacceptSituation");
        this.prmtdeliveryPerson.setName("prmtdeliveryPerson");
        this.prmtrecipient.setName("prmtrecipient");
        this.pktransferTime.setName("pktransferTime");
        this.prmtslDepart.setName("prmtslDepart");
        this.txtselfAmount.setName("txtselfAmount");
        this.txtoutAmount.setName("txtoutAmount");
        this.prmtbaoxiuren.setName("prmtbaoxiuren");
        this.pkbaoxiuTime.setName("pkbaoxiuTime");
        // CoreUI
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
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
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
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
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);
        // contBizStatus		
        this.contBizStatus.setBoundLabelText(resHelper.getString("contBizStatus.boundLabelText"));		
        this.contBizStatus.setBoundLabelLength(100);		
        this.contBizStatus.setBoundLabelUnderline(true);		
        this.contBizStatus.setEnabled(false);		
        this.contBizStatus.setVisible(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contequName		
        this.contequName.setBoundLabelText(resHelper.getString("contequName.boundLabelText"));		
        this.contequName.setBoundLabelLength(100);		
        this.contequName.setBoundLabelUnderline(true);		
        this.contequName.setVisible(true);
        // contequModel		
        this.contequModel.setBoundLabelText(resHelper.getString("contequModel.boundLabelText"));		
        this.contequModel.setBoundLabelLength(100);		
        this.contequModel.setBoundLabelUnderline(true);		
        this.contequModel.setVisible(true);
        // contequAddress		
        this.contequAddress.setBoundLabelText(resHelper.getString("contequAddress.boundLabelText"));		
        this.contequAddress.setBoundLabelLength(100);		
        this.contequAddress.setBoundLabelUnderline(true);		
        this.contequAddress.setVisible(true);
        // contrepairDepart		
        this.contrepairDepart.setBoundLabelText(resHelper.getString("contrepairDepart.boundLabelText"));		
        this.contrepairDepart.setBoundLabelLength(100);		
        this.contrepairDepart.setBoundLabelUnderline(true);		
        this.contrepairDepart.setVisible(true);
        // contrepairPerson		
        this.contrepairPerson.setBoundLabelText(resHelper.getString("contrepairPerson.boundLabelText"));		
        this.contrepairPerson.setBoundLabelLength(100);		
        this.contrepairPerson.setBoundLabelUnderline(true);		
        this.contrepairPerson.setVisible(true);
        // contrepairTime		
        this.contrepairTime.setBoundLabelText(resHelper.getString("contrepairTime.boundLabelText"));		
        this.contrepairTime.setBoundLabelLength(100);		
        this.contrepairTime.setBoundLabelUnderline(true);		
        this.contrepairTime.setVisible(true);
        // contrepairContent		
        this.contrepairContent.setBoundLabelText(resHelper.getString("contrepairContent.boundLabelText"));		
        this.contrepairContent.setBoundLabelLength(100);		
        this.contrepairContent.setBoundLabelUnderline(true);		
        this.contrepairContent.setVisible(true);
        // contassignee		
        this.contassignee.setBoundLabelText(resHelper.getString("contassignee.boundLabelText"));		
        this.contassignee.setBoundLabelLength(100);		
        this.contassignee.setBoundLabelUnderline(true);		
        this.contassignee.setVisible(true);
        // contacceptTime		
        this.contacceptTime.setBoundLabelText(resHelper.getString("contacceptTime.boundLabelText"));		
        this.contacceptTime.setBoundLabelLength(100);		
        this.contacceptTime.setBoundLabelUnderline(true);		
        this.contacceptTime.setVisible(true);
        // contmaintenanceType		
        this.contmaintenanceType.setBoundLabelText(resHelper.getString("contmaintenanceType.boundLabelText"));		
        this.contmaintenanceType.setBoundLabelLength(100);		
        this.contmaintenanceType.setBoundLabelUnderline(true);		
        this.contmaintenanceType.setVisible(true);
        // contmaintenanceProgram		
        this.contmaintenanceProgram.setBoundLabelText(resHelper.getString("contmaintenanceProgram.boundLabelText"));		
        this.contmaintenanceProgram.setBoundLabelLength(100);		
        this.contmaintenanceProgram.setBoundLabelUnderline(true);		
        this.contmaintenanceProgram.setVisible(true);
        // kdtE1
		String kdtE1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"FaLocation\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"guzhangqingkuang\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"repairContent\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"wxfa\" t:width=\"220\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"replaceSparePart\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"model\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"shuliang\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"jlUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"workTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"repairPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sfww\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"xlAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"yujingzhouqi\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{FaLocation}</t:Cell><t:Cell>$Resource{guzhangqingkuang}</t:Cell><t:Cell>$Resource{repairContent}</t:Cell><t:Cell>$Resource{wxfa}</t:Cell><t:Cell>$Resource{replaceSparePart}</t:Cell><t:Cell>$Resource{model}</t:Cell><t:Cell>$Resource{shuliang}</t:Cell><t:Cell>$Resource{jlUnit}</t:Cell><t:Cell>$Resource{workTime}</t:Cell><t:Cell>$Resource{repairPerson}</t:Cell><t:Cell>$Resource{sfww}</t:Cell><t:Cell>$Resource{xlAmount}</t:Cell><t:Cell>$Resource{yujingzhouqi}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE1.setFormatXml(resHelper.translateString("kdtE1",kdtE1StrXML));
        kdtE1.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtE1_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});


                this.kdtE1.putBindContents("editData",new String[] {"seq","FaLocation","guzhangqingkuang","repairContent","wxfa","replaceSparePart","model","shuliang","jlUnit","workTime","repairPerson","sfww","xlAmount","yujingzhouqi","remark"});


        this.kdtE1.checkParsed();
        KDFormattedTextField kdtE1_seq_TextField = new KDFormattedTextField();
        kdtE1_seq_TextField.setName("kdtE1_seq_TextField");
        kdtE1_seq_TextField.setVisible(true);
        kdtE1_seq_TextField.setEditable(true);
        kdtE1_seq_TextField.setHorizontalAlignment(2);
        kdtE1_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtE1_seq_CellEditor = new KDTDefaultCellEditor(kdtE1_seq_TextField);
        this.kdtE1.getColumn("seq").setEditor(kdtE1_seq_CellEditor);
        final KDBizPromptBox kdtE1_FaLocation_PromptBox = new KDBizPromptBox();
        kdtE1_FaLocation_PromptBox.setQueryInfo("com.kingdee.eas.port.equipment.base.app.FaLocationQuery");
        kdtE1_FaLocation_PromptBox.setVisible(true);
        kdtE1_FaLocation_PromptBox.setEditable(true);
        kdtE1_FaLocation_PromptBox.setDisplayFormat("$number$");
        kdtE1_FaLocation_PromptBox.setEditFormat("$number$");
        kdtE1_FaLocation_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_FaLocation_CellEditor = new KDTDefaultCellEditor(kdtE1_FaLocation_PromptBox);
        this.kdtE1.getColumn("FaLocation").setEditor(kdtE1_FaLocation_CellEditor);
        ObjectValueRender kdtE1_FaLocation_OVR = new ObjectValueRender();
        kdtE1_FaLocation_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE1.getColumn("FaLocation").setRenderer(kdtE1_FaLocation_OVR);
        KDTextArea kdtE1_guzhangqingkuang_TextArea = new KDTextArea();
        kdtE1_guzhangqingkuang_TextArea.setName("kdtE1_guzhangqingkuang_TextArea");
        kdtE1_guzhangqingkuang_TextArea.setMaxLength(2000);
        KDTDefaultCellEditor kdtE1_guzhangqingkuang_CellEditor = new KDTDefaultCellEditor(kdtE1_guzhangqingkuang_TextArea);
        this.kdtE1.getColumn("guzhangqingkuang").setEditor(kdtE1_guzhangqingkuang_CellEditor);
        KDTextField kdtE1_repairContent_TextField = new KDTextField();
        kdtE1_repairContent_TextField.setName("kdtE1_repairContent_TextField");
        kdtE1_repairContent_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_repairContent_CellEditor = new KDTDefaultCellEditor(kdtE1_repairContent_TextField);
        this.kdtE1.getColumn("repairContent").setEditor(kdtE1_repairContent_CellEditor);
        KDTextArea kdtE1_wxfa_TextArea = new KDTextArea();
        kdtE1_wxfa_TextArea.setName("kdtE1_wxfa_TextArea");
        kdtE1_wxfa_TextArea.setMaxLength(2000);
        KDTDefaultCellEditor kdtE1_wxfa_CellEditor = new KDTDefaultCellEditor(kdtE1_wxfa_TextArea);
        this.kdtE1.getColumn("wxfa").setEditor(kdtE1_wxfa_CellEditor);
        final KDBizPromptBox kdtE1_replaceSparePart_PromptBox = new KDBizPromptBox();
        kdtE1_replaceSparePart_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialBaseInfoQuery");
        kdtE1_replaceSparePart_PromptBox.setVisible(true);
        kdtE1_replaceSparePart_PromptBox.setEditable(true);
        kdtE1_replaceSparePart_PromptBox.setDisplayFormat("$number$");
        kdtE1_replaceSparePart_PromptBox.setEditFormat("$number$");
        kdtE1_replaceSparePart_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_replaceSparePart_CellEditor = new KDTDefaultCellEditor(kdtE1_replaceSparePart_PromptBox);
        this.kdtE1.getColumn("replaceSparePart").setEditor(kdtE1_replaceSparePart_CellEditor);
        ObjectValueRender kdtE1_replaceSparePart_OVR = new ObjectValueRender();
        kdtE1_replaceSparePart_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE1.getColumn("replaceSparePart").setRenderer(kdtE1_replaceSparePart_OVR);
        KDTextField kdtE1_model_TextField = new KDTextField();
        kdtE1_model_TextField.setName("kdtE1_model_TextField");
        kdtE1_model_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE1_model_CellEditor = new KDTDefaultCellEditor(kdtE1_model_TextField);
        this.kdtE1.getColumn("model").setEditor(kdtE1_model_CellEditor);
        KDFormattedTextField kdtE1_shuliang_TextField = new KDFormattedTextField();
        kdtE1_shuliang_TextField.setName("kdtE1_shuliang_TextField");
        kdtE1_shuliang_TextField.setVisible(true);
        kdtE1_shuliang_TextField.setEditable(true);
        kdtE1_shuliang_TextField.setHorizontalAlignment(2);
        kdtE1_shuliang_TextField.setDataType(1);
        	kdtE1_shuliang_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_shuliang_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_shuliang_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_shuliang_CellEditor = new KDTDefaultCellEditor(kdtE1_shuliang_TextField);
        this.kdtE1.getColumn("shuliang").setEditor(kdtE1_shuliang_CellEditor);
        KDTextField kdtE1_jlUnit_TextField = new KDTextField();
        kdtE1_jlUnit_TextField.setName("kdtE1_jlUnit_TextField");
        kdtE1_jlUnit_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE1_jlUnit_CellEditor = new KDTDefaultCellEditor(kdtE1_jlUnit_TextField);
        this.kdtE1.getColumn("jlUnit").setEditor(kdtE1_jlUnit_CellEditor);
        KDFormattedTextField kdtE1_workTime_TextField = new KDFormattedTextField();
        kdtE1_workTime_TextField.setName("kdtE1_workTime_TextField");
        kdtE1_workTime_TextField.setVisible(true);
        kdtE1_workTime_TextField.setEditable(true);
        kdtE1_workTime_TextField.setHorizontalAlignment(2);
        kdtE1_workTime_TextField.setDataType(1);
        	kdtE1_workTime_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E27"));
        	kdtE1_workTime_TextField.setMaximumValue(new java.math.BigDecimal("1.0E27"));
        kdtE1_workTime_TextField.setPrecision(1);
        KDTDefaultCellEditor kdtE1_workTime_CellEditor = new KDTDefaultCellEditor(kdtE1_workTime_TextField);
        this.kdtE1.getColumn("workTime").setEditor(kdtE1_workTime_CellEditor);
        final KDBizPromptBox kdtE1_repairPerson_PromptBox = new KDBizPromptBox();
        kdtE1_repairPerson_PromptBox.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
        kdtE1_repairPerson_PromptBox.setVisible(true);
        kdtE1_repairPerson_PromptBox.setEditable(true);
        kdtE1_repairPerson_PromptBox.setDisplayFormat("$number$");
        kdtE1_repairPerson_PromptBox.setEditFormat("$number$");
        kdtE1_repairPerson_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_repairPerson_CellEditor = new KDTDefaultCellEditor(kdtE1_repairPerson_PromptBox);
        this.kdtE1.getColumn("repairPerson").setEditor(kdtE1_repairPerson_CellEditor);
        ObjectValueRender kdtE1_repairPerson_OVR = new ObjectValueRender();
        kdtE1_repairPerson_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE1.getColumn("repairPerson").setRenderer(kdtE1_repairPerson_OVR);
        KDCheckBox kdtE1_sfww_CheckBox = new KDCheckBox();
        kdtE1_sfww_CheckBox.setName("kdtE1_sfww_CheckBox");
        KDTDefaultCellEditor kdtE1_sfww_CellEditor = new KDTDefaultCellEditor(kdtE1_sfww_CheckBox);
        this.kdtE1.getColumn("sfww").setEditor(kdtE1_sfww_CellEditor);
        KDFormattedTextField kdtE1_xlAmount_TextField = new KDFormattedTextField();
        kdtE1_xlAmount_TextField.setName("kdtE1_xlAmount_TextField");
        kdtE1_xlAmount_TextField.setVisible(true);
        kdtE1_xlAmount_TextField.setEditable(true);
        kdtE1_xlAmount_TextField.setHorizontalAlignment(2);
        kdtE1_xlAmount_TextField.setDataType(1);
        	kdtE1_xlAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_xlAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_xlAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_xlAmount_CellEditor = new KDTDefaultCellEditor(kdtE1_xlAmount_TextField);
        this.kdtE1.getColumn("xlAmount").setEditor(kdtE1_xlAmount_CellEditor);
        KDTextField kdtE1_yujingzhouqi_TextField = new KDTextField();
        kdtE1_yujingzhouqi_TextField.setName("kdtE1_yujingzhouqi_TextField");
        kdtE1_yujingzhouqi_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_yujingzhouqi_CellEditor = new KDTDefaultCellEditor(kdtE1_yujingzhouqi_TextField);
        this.kdtE1.getColumn("yujingzhouqi").setEditor(kdtE1_yujingzhouqi_CellEditor);
        KDTextField kdtE1_remark_TextField = new KDTextField();
        kdtE1_remark_TextField.setName("kdtE1_remark_TextField");
        kdtE1_remark_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_remark_CellEditor = new KDTDefaultCellEditor(kdtE1_remark_TextField);
        this.kdtE1.getColumn("remark").setEditor(kdtE1_remark_CellEditor);
        // contBIMUDF0021		
        this.contBIMUDF0021.setBoundLabelText(resHelper.getString("contBIMUDF0021.boundLabelText"));		
        this.contBIMUDF0021.setBoundLabelLength(100);		
        this.contBIMUDF0021.setBoundLabelUnderline(true);		
        this.contBIMUDF0021.setVisible(true);
        // contrepairDate		
        this.contrepairDate.setBoundLabelText(resHelper.getString("contrepairDate.boundLabelText"));		
        this.contrepairDate.setBoundLabelLength(100);		
        this.contrepairDate.setBoundLabelUnderline(true);		
        this.contrepairDate.setVisible(true);
        // contacceptSituation		
        this.contacceptSituation.setBoundLabelText(resHelper.getString("contacceptSituation.boundLabelText"));		
        this.contacceptSituation.setBoundLabelLength(100);		
        this.contacceptSituation.setBoundLabelUnderline(true);		
        this.contacceptSituation.setVisible(true);
        // contdeliveryPerson		
        this.contdeliveryPerson.setBoundLabelText(resHelper.getString("contdeliveryPerson.boundLabelText"));		
        this.contdeliveryPerson.setBoundLabelLength(100);		
        this.contdeliveryPerson.setBoundLabelUnderline(true);		
        this.contdeliveryPerson.setVisible(true);
        // contrecipient		
        this.contrecipient.setBoundLabelText(resHelper.getString("contrecipient.boundLabelText"));		
        this.contrecipient.setBoundLabelLength(100);		
        this.contrecipient.setBoundLabelUnderline(true);		
        this.contrecipient.setVisible(true);
        // conttransferTime		
        this.conttransferTime.setBoundLabelText(resHelper.getString("conttransferTime.boundLabelText"));		
        this.conttransferTime.setBoundLabelLength(100);		
        this.conttransferTime.setBoundLabelUnderline(true);		
        this.conttransferTime.setVisible(true);
        // contslDepart		
        this.contslDepart.setBoundLabelText(resHelper.getString("contslDepart.boundLabelText"));		
        this.contslDepart.setBoundLabelLength(100);		
        this.contslDepart.setBoundLabelUnderline(true);		
        this.contslDepart.setVisible(true);
        // chkselfStudy		
        this.chkselfStudy.setText(resHelper.getString("chkselfStudy.text"));		
        this.chkselfStudy.setHorizontalAlignment(2);
        // chkoutsourcing		
        this.chkoutsourcing.setText(resHelper.getString("chkoutsourcing.text"));		
        this.chkoutsourcing.setHorizontalAlignment(2);
        // contselfAmount		
        this.contselfAmount.setBoundLabelText(resHelper.getString("contselfAmount.boundLabelText"));		
        this.contselfAmount.setBoundLabelLength(100);		
        this.contselfAmount.setBoundLabelUnderline(true);		
        this.contselfAmount.setVisible(true);
        // contoutAmount		
        this.contoutAmount.setBoundLabelText(resHelper.getString("contoutAmount.boundLabelText"));		
        this.contoutAmount.setBoundLabelLength(100);		
        this.contoutAmount.setBoundLabelUnderline(true);		
        this.contoutAmount.setVisible(true);
        // chksfjhn		
        this.chksfjhn.setText(resHelper.getString("chksfjhn.text"));		
        this.chksfjhn.setHorizontalAlignment(2);
        // contbaoxiuren		
        this.contbaoxiuren.setBoundLabelText(resHelper.getString("contbaoxiuren.boundLabelText"));		
        this.contbaoxiuren.setBoundLabelLength(100);		
        this.contbaoxiuren.setBoundLabelUnderline(true);		
        this.contbaoxiuren.setVisible(true);
        // contbaoxiuTime		
        this.contbaoxiuTime.setBoundLabelText(resHelper.getString("contbaoxiuTime.boundLabelText"));		
        this.contbaoxiuTime.setBoundLabelLength(100);		
        this.contbaoxiuTime.setBoundLabelUnderline(true);		
        this.contbaoxiuTime.setVisible(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setCommitFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");		
        this.prmtCreator.setDisplayFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setTimeEnabled(true);		
        this.pkCreateTime.setEnabled(false);
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
        // txtNumber
        // pkBizDate
        // txtDescription
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);
        // comboBizStatus		
        this.comboBizStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBizActionEnum").toArray());		
        this.comboBizStatus.setEnabled(false);		
        this.comboBizStatus.setVisible(false);
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // prmtequName		
        this.prmtequName.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");		
        this.prmtequName.setEditable(true);		
        this.prmtequName.setDisplayFormat("$name$");		
        this.prmtequName.setEditFormat("$number$");		
        this.prmtequName.setCommitFormat("$number$");		
        this.prmtequName.setRequired(false);
        prmtequName.addDataChangeListener(new DataChangeListener() {
		public void dataChanged(DataChangeEvent e) {
			try {
				prmtequName_Changed();
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        // txtequModel		
        this.txtequModel.setHorizontalAlignment(2);		
        this.txtequModel.setMaxLength(80);		
        this.txtequModel.setRequired(false);
        // txtequAddress		
        this.txtequAddress.setHorizontalAlignment(2);		
        this.txtequAddress.setMaxLength(80);		
        this.txtequAddress.setRequired(false);
        // prmtrepairDepart		
        this.prmtrepairDepart.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtrepairDepart.setEditable(true);		
        this.prmtrepairDepart.setDisplayFormat("$name$");		
        this.prmtrepairDepart.setEditFormat("$number$");		
        this.prmtrepairDepart.setCommitFormat("$number$");		
        this.prmtrepairDepart.setRequired(false);
        // prmtrepairPerson		
        this.prmtrepairPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtrepairPerson.setEditable(true);		
        this.prmtrepairPerson.setDisplayFormat("$name$");		
        this.prmtrepairPerson.setEditFormat("$number$");		
        this.prmtrepairPerson.setCommitFormat("$number$");		
        this.prmtrepairPerson.setRequired(false);
        this.prmtrepairPerson.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtrepairPerson_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkrepairTime		
        this.pkrepairTime.setRequired(false);
        // txtrepairContent		
        this.txtrepairContent.setHorizontalAlignment(2);		
        this.txtrepairContent.setMaxLength(255);		
        this.txtrepairContent.setRequired(false);
        // prmtassignee		
        this.prmtassignee.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtassignee.setEditable(true);		
        this.prmtassignee.setDisplayFormat("$name$");		
        this.prmtassignee.setEditFormat("$number$");		
        this.prmtassignee.setCommitFormat("$number$");		
        this.prmtassignee.setRequired(false);
        this.prmtassignee.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtassignee_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkacceptTime		
        this.pkacceptTime.setRequired(false);
        // prmtmaintenanceType		
        this.prmtmaintenanceType.setQueryInfo("com.kingdee.eas.port.equipment.base.app.MaintenanceTypeQuery");		
        this.prmtmaintenanceType.setEditable(true);		
        this.prmtmaintenanceType.setDisplayFormat("$name$");		
        this.prmtmaintenanceType.setEditFormat("$number$");		
        this.prmtmaintenanceType.setCommitFormat("$number$");		
        this.prmtmaintenanceType.setRequired(false);
        // scrollPanemaintenanceProgram
        // txtmaintenanceProgram		
        this.txtmaintenanceProgram.setRequired(false);		
        this.txtmaintenanceProgram.setMaxLength(255);
        // scrollPaneBIMUDF0021
        // txtBIMUDF0021		
        this.txtBIMUDF0021.setRequired(false);		
        this.txtBIMUDF0021.setMaxLength(255);
        // pkrepairDate		
        this.pkrepairDate.setRequired(false);
        // txtacceptSituation		
        this.txtacceptSituation.setHorizontalAlignment(2);		
        this.txtacceptSituation.setMaxLength(100);		
        this.txtacceptSituation.setRequired(false);
        // prmtdeliveryPerson		
        this.prmtdeliveryPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtdeliveryPerson.setEditable(true);		
        this.prmtdeliveryPerson.setDisplayFormat("$name$");		
        this.prmtdeliveryPerson.setEditFormat("$number$");		
        this.prmtdeliveryPerson.setCommitFormat("$number$");		
        this.prmtdeliveryPerson.setRequired(false);
        // prmtrecipient		
        this.prmtrecipient.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtrecipient.setEditable(true);		
        this.prmtrecipient.setDisplayFormat("$name$");		
        this.prmtrecipient.setEditFormat("$number$");		
        this.prmtrecipient.setCommitFormat("$number$");		
        this.prmtrecipient.setRequired(false);
        // pktransferTime		
        this.pktransferTime.setRequired(false);
        // prmtslDepart		
        this.prmtslDepart.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtslDepart.setEditable(true);		
        this.prmtslDepart.setDisplayFormat("$name$");		
        this.prmtslDepart.setEditFormat("$number$");		
        this.prmtslDepart.setCommitFormat("$number$");		
        this.prmtslDepart.setRequired(false);
        // txtselfAmount		
        this.txtselfAmount.setHorizontalAlignment(2);		
        this.txtselfAmount.setDataType(1);		
        this.txtselfAmount.setSupportedEmpty(true);		
        this.txtselfAmount.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtselfAmount.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtselfAmount.setPrecision(2);		
        this.txtselfAmount.setRequired(false);
        // txtoutAmount		
        this.txtoutAmount.setHorizontalAlignment(2);		
        this.txtoutAmount.setDataType(1);		
        this.txtoutAmount.setSupportedEmpty(true);		
        this.txtoutAmount.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtoutAmount.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtoutAmount.setPrecision(2);		
        this.txtoutAmount.setRequired(false);
        // prmtbaoxiuren		
        this.prmtbaoxiuren.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtbaoxiuren.setEditable(true);		
        this.prmtbaoxiuren.setDisplayFormat("$name$");		
        this.prmtbaoxiuren.setEditFormat("$number$");		
        this.prmtbaoxiuren.setCommitFormat("$number$");		
        this.prmtbaoxiuren.setRequired(false);
        // pkbaoxiuTime		
        this.pkbaoxiuTime.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtBIMUDF0021,prmtequName,txtequModel,txtequAddress,prmtrepairDepart,prmtrepairPerson,pkrepairTime,txtrepairContent,prmtassignee,pkacceptTime,prmtmaintenanceType,txtmaintenanceProgram,prmtCU,pkLastUpdateTime,prmtLastUpdateUser,pkCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,pkAuditTime,comboBizStatus,comboStatus,pkrepairDate,txtacceptSituation,prmtdeliveryPerson,prmtrecipient,pktransferTime,prmtslDepart,chkselfStudy,chkoutsourcing,txtselfAmount,txtoutAmount,chksfjhn,prmtbaoxiuren,pkbaoxiuTime,kdtE1}));
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
        this.setBounds(new Rectangle(10, 10, 1013, 581));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 581));
        contCreator.setBounds(new Rectangle(672, 466, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(672, 466, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(672, 490, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(672, 490, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateUser.setBounds(new Rectangle(10, 490, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(10, 490, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(10, 514, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(10, 514, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contCU, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(672, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(672, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(341, 10, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(341, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(10, 538, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(10, 538, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(341, 490, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(341, 490, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStatus.setBounds(new Rectangle(672, 142, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(672, 142, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(672, 514, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(672, 514, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(341, 514, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(341, 514, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contequName.setBounds(new Rectangle(10, 36, 270, 19));
        this.add(contequName, new KDLayout.Constraints(10, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contequModel.setBounds(new Rectangle(341, 34, 270, 19));
        this.add(contequModel, new KDLayout.Constraints(341, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contequAddress.setBounds(new Rectangle(672, 35, 270, 19));
        this.add(contequAddress, new KDLayout.Constraints(672, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contrepairDepart.setBounds(new Rectangle(341, 62, 270, 19));
        this.add(contrepairDepart, new KDLayout.Constraints(341, 62, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrepairPerson.setBounds(new Rectangle(10, 62, 270, 19));
        this.add(contrepairPerson, new KDLayout.Constraints(10, 62, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrepairTime.setBounds(new Rectangle(672, 62, 270, 19));
        this.add(contrepairTime, new KDLayout.Constraints(672, 62, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contrepairContent.setBounds(new Rectangle(10, 114, 270, 19));
        this.add(contrepairContent, new KDLayout.Constraints(10, 114, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contassignee.setBounds(new Rectangle(10, 88, 270, 19));
        this.add(contassignee, new KDLayout.Constraints(10, 88, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contacceptTime.setBounds(new Rectangle(672, 88, 270, 19));
        this.add(contacceptTime, new KDLayout.Constraints(672, 88, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contmaintenanceType.setBounds(new Rectangle(341, 142, 270, 19));
        this.add(contmaintenanceType, new KDLayout.Constraints(341, 142, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contmaintenanceProgram.setBounds(new Rectangle(965, 227, 601, 66));
        this.add(contmaintenanceProgram, new KDLayout.Constraints(965, 227, 601, 66, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtE1.setBounds(new Rectangle(10, 173, 922, 207));
        kdtE1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE1,new com.kingdee.eas.port.equipment.maintenance.RepairOrderE1Info(),null,false);
        this.add(kdtE1_detailPanel, new KDLayout.Constraints(10, 173, 922, 207, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBIMUDF0021.setBounds(new Rectangle(10, 394, 922, 37));
        this.add(contBIMUDF0021, new KDLayout.Constraints(10, 394, 922, 37, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrepairDate.setBounds(new Rectangle(341, 442, 270, 19));
        this.add(contrepairDate, new KDLayout.Constraints(341, 442, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contacceptSituation.setBounds(new Rectangle(10, 442, 270, 19));
        this.add(contacceptSituation, new KDLayout.Constraints(10, 442, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contdeliveryPerson.setBounds(new Rectangle(672, 442, 270, 19));
        this.add(contdeliveryPerson, new KDLayout.Constraints(672, 442, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contrecipient.setBounds(new Rectangle(10, 466, 270, 19));
        this.add(contrecipient, new KDLayout.Constraints(10, 466, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttransferTime.setBounds(new Rectangle(341, 466, 270, 19));
        this.add(conttransferTime, new KDLayout.Constraints(341, 466, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contslDepart.setBounds(new Rectangle(341, 88, 270, 19));
        this.add(contslDepart, new KDLayout.Constraints(341, 88, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkselfStudy.setBounds(new Rectangle(955, 152, 93, 19));
        this.add(chkselfStudy, new KDLayout.Constraints(955, 152, 93, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkoutsourcing.setBounds(new Rectangle(961, 128, 101, 19));
        this.add(chkoutsourcing, new KDLayout.Constraints(961, 128, 101, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contselfAmount.setBounds(new Rectangle(971, 177, 270, 19));
        this.add(contselfAmount, new KDLayout.Constraints(971, 177, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contoutAmount.setBounds(new Rectangle(976, 101, 270, 19));
        this.add(contoutAmount, new KDLayout.Constraints(976, 101, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chksfjhn.setBounds(new Rectangle(10, 142, 270, 19));
        this.add(chksfjhn, new KDLayout.Constraints(10, 142, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbaoxiuren.setBounds(new Rectangle(341, 114, 270, 19));
        this.add(contbaoxiuren, new KDLayout.Constraints(341, 114, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbaoxiuTime.setBounds(new Rectangle(672, 114, 270, 19));
        this.add(contbaoxiuTime, new KDLayout.Constraints(672, 114, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
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
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //contBizStatus
        contBizStatus.setBoundEditor(comboBizStatus);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contequName
        contequName.setBoundEditor(prmtequName);
        //contequModel
        contequModel.setBoundEditor(txtequModel);
        //contequAddress
        contequAddress.setBoundEditor(txtequAddress);
        //contrepairDepart
        contrepairDepart.setBoundEditor(prmtrepairDepart);
        //contrepairPerson
        contrepairPerson.setBoundEditor(prmtrepairPerson);
        //contrepairTime
        contrepairTime.setBoundEditor(pkrepairTime);
        //contrepairContent
        contrepairContent.setBoundEditor(txtrepairContent);
        //contassignee
        contassignee.setBoundEditor(prmtassignee);
        //contacceptTime
        contacceptTime.setBoundEditor(pkacceptTime);
        //contmaintenanceType
        contmaintenanceType.setBoundEditor(prmtmaintenanceType);
        //contmaintenanceProgram
        contmaintenanceProgram.setBoundEditor(scrollPanemaintenanceProgram);
        //scrollPanemaintenanceProgram
        scrollPanemaintenanceProgram.getViewport().add(txtmaintenanceProgram, null);
        //contBIMUDF0021
        contBIMUDF0021.setBoundEditor(scrollPaneBIMUDF0021);
        //scrollPaneBIMUDF0021
        scrollPaneBIMUDF0021.getViewport().add(txtBIMUDF0021, null);
        //contrepairDate
        contrepairDate.setBoundEditor(pkrepairDate);
        //contacceptSituation
        contacceptSituation.setBoundEditor(txtacceptSituation);
        //contdeliveryPerson
        contdeliveryPerson.setBoundEditor(prmtdeliveryPerson);
        //contrecipient
        contrecipient.setBoundEditor(prmtrecipient);
        //conttransferTime
        conttransferTime.setBoundEditor(pktransferTime);
        //contslDepart
        contslDepart.setBoundEditor(prmtslDepart);
        //contselfAmount
        contselfAmount.setBoundEditor(txtselfAmount);
        //contoutAmount
        contoutAmount.setBoundEditor(txtoutAmount);
        //contbaoxiuren
        contbaoxiuren.setBoundEditor(prmtbaoxiuren);
        //contbaoxiuTime
        contbaoxiuTime.setBoundEditor(pkbaoxiuTime);

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
		dataBinder.registerBinding("E1.seq", int.class, this.kdtE1, "seq.text");
		dataBinder.registerBinding("E1", com.kingdee.eas.port.equipment.maintenance.RepairOrderE1Info.class, this.kdtE1, "userObject");
		dataBinder.registerBinding("E1.repairContent", String.class, this.kdtE1, "repairContent.text");
		dataBinder.registerBinding("E1.replaceSparePart", java.lang.Object.class, this.kdtE1, "replaceSparePart.text");
		dataBinder.registerBinding("E1.workTime", java.math.BigDecimal.class, this.kdtE1, "workTime.text");
		dataBinder.registerBinding("E1.repairPerson", java.lang.Object.class, this.kdtE1, "repairPerson.text");
		dataBinder.registerBinding("E1.remark", String.class, this.kdtE1, "remark.text");
		dataBinder.registerBinding("E1.FaLocation", java.lang.Object.class, this.kdtE1, "FaLocation.text");
		dataBinder.registerBinding("E1.wxfa", String.class, this.kdtE1, "wxfa.text");
		dataBinder.registerBinding("E1.sfww", boolean.class, this.kdtE1, "sfww.text");
		dataBinder.registerBinding("E1.xlAmount", java.math.BigDecimal.class, this.kdtE1, "xlAmount.text");
		dataBinder.registerBinding("E1.guzhangqingkuang", String.class, this.kdtE1, "guzhangqingkuang.text");
		dataBinder.registerBinding("E1.model", String.class, this.kdtE1, "model.text");
		dataBinder.registerBinding("E1.shuliang", java.math.BigDecimal.class, this.kdtE1, "shuliang.text");
		dataBinder.registerBinding("E1.yujingzhouqi", String.class, this.kdtE1, "yujingzhouqi.text");
		dataBinder.registerBinding("E1.jlUnit", String.class, this.kdtE1, "jlUnit.text");
		dataBinder.registerBinding("selfStudy", boolean.class, this.chkselfStudy, "selected");
		dataBinder.registerBinding("outsourcing", boolean.class, this.chkoutsourcing, "selected");
		dataBinder.registerBinding("sfjhn", boolean.class, this.chksfjhn, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("equName", com.kingdee.eas.port.equipment.record.EquIdInfo.class, this.prmtequName, "data");
		dataBinder.registerBinding("equModel", String.class, this.txtequModel, "text");
		dataBinder.registerBinding("equAddress", String.class, this.txtequAddress, "text");
		dataBinder.registerBinding("repairDepart", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtrepairDepart, "data");
		dataBinder.registerBinding("repairPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtrepairPerson, "data");
		dataBinder.registerBinding("repairTime", java.util.Date.class, this.pkrepairTime, "value");
		dataBinder.registerBinding("repairContent", String.class, this.txtrepairContent, "text");
		dataBinder.registerBinding("assignee", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtassignee, "data");
		dataBinder.registerBinding("acceptTime", java.util.Date.class, this.pkacceptTime, "value");
		dataBinder.registerBinding("maintenanceType", com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo.class, this.prmtmaintenanceType, "data");
		dataBinder.registerBinding("maintenanceProgram", String.class, this.txtmaintenanceProgram, "text");
		dataBinder.registerBinding("BIMUDF0021", String.class, this.txtBIMUDF0021, "text");
		dataBinder.registerBinding("repairDate", java.util.Date.class, this.pkrepairDate, "value");
		dataBinder.registerBinding("acceptSituation", String.class, this.txtacceptSituation, "text");
		dataBinder.registerBinding("deliveryPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtdeliveryPerson, "data");
		dataBinder.registerBinding("recipient", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtrecipient, "data");
		dataBinder.registerBinding("transferTime", java.util.Date.class, this.pktransferTime, "value");
		dataBinder.registerBinding("slDepart", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtslDepart, "data");
		dataBinder.registerBinding("selfAmount", java.math.BigDecimal.class, this.txtselfAmount, "value");
		dataBinder.registerBinding("outAmount", java.math.BigDecimal.class, this.txtoutAmount, "value");
		dataBinder.registerBinding("baoxiuren", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtbaoxiuren, "data");
		dataBinder.registerBinding("baoxiuTime", java.util.Date.class, this.pkbaoxiuTime, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.maintenance.app.RepairOrderEditUIHandler";
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
        this.txtBIMUDF0021.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo)ov;
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
		getValidateHelper().registerBindProperty("E1.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.repairContent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.replaceSparePart", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.workTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.repairPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.FaLocation", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.wxfa", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.sfww", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.xlAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.guzhangqingkuang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.model", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.shuliang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.yujingzhouqi", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.jlUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("selfStudy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("outsourcing", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sfjhn", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("equName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("equModel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("equAddress", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("repairDepart", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("repairPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("repairTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("repairContent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("assignee", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("acceptTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("maintenanceType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("maintenanceProgram", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BIMUDF0021", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("repairDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("acceptSituation", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("deliveryPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recipient", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("transferTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("slDepart", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("selfAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("outAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("baoxiuren", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("baoxiuTime", ValidateHelper.ON_SAVE);    		
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
     * output prmtrepairPerson_dataChanged method
     */
    protected void prmtrepairPerson_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtassignee_dataChanged method
     */
    protected void prmtassignee_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output kdtE1_Changed(int rowIndex,int colIndex) method
     */
    public void kdtE1_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("replaceSparePart".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"model").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"replaceSparePart").getValue(),"model")));

}

    if ("replaceSparePart".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"jlUnit").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"replaceSparePart").getValue(),"baseUnit.name")));

}


    }

    /**
     * output prmtequName_Changed() method
     */
    public void prmtequName_Changed() throws Exception
    {
        System.out.println("prmtequName_Changed() Function is executed!");
            txtequModel.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtequName.getData(),"model")));

    txtequAddress.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtequName.getData(),"address.name")));


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
    	sic.add(new SelectorItemInfo("E1.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E1.repairContent"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.replaceSparePart.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.replaceSparePart.id"));
			sic.add(new SelectorItemInfo("E1.replaceSparePart.name"));
        	sic.add(new SelectorItemInfo("E1.replaceSparePart.number"));
		}
    	sic.add(new SelectorItemInfo("E1.workTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.repairPerson.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.repairPerson.id"));
			sic.add(new SelectorItemInfo("E1.repairPerson.name"));
        	sic.add(new SelectorItemInfo("E1.repairPerson.number"));
		}
    	sic.add(new SelectorItemInfo("E1.remark"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.FaLocation.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.FaLocation.id"));
			sic.add(new SelectorItemInfo("E1.FaLocation.name"));
        	sic.add(new SelectorItemInfo("E1.FaLocation.number"));
		}
    	sic.add(new SelectorItemInfo("E1.wxfa"));
    	sic.add(new SelectorItemInfo("E1.sfww"));
    	sic.add(new SelectorItemInfo("E1.xlAmount"));
    	sic.add(new SelectorItemInfo("E1.guzhangqingkuang"));
    	sic.add(new SelectorItemInfo("E1.model"));
    	sic.add(new SelectorItemInfo("E1.shuliang"));
    	sic.add(new SelectorItemInfo("E1.yujingzhouqi"));
    	sic.add(new SelectorItemInfo("E1.jlUnit"));
        sic.add(new SelectorItemInfo("selfStudy"));
        sic.add(new SelectorItemInfo("outsourcing"));
        sic.add(new SelectorItemInfo("sfjhn"));
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("CU.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("CU.id"));
        	sic.add(new SelectorItemInfo("CU.number"));
        	sic.add(new SelectorItemInfo("CU.name"));
		}
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
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("bizStatus"));
        sic.add(new SelectorItemInfo("auditTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("equName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("equName.id"));
        	sic.add(new SelectorItemInfo("equName.number"));
        	sic.add(new SelectorItemInfo("equName.name"));
		}
        sic.add(new SelectorItemInfo("equModel"));
        sic.add(new SelectorItemInfo("equAddress"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("repairDepart.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("repairDepart.id"));
        	sic.add(new SelectorItemInfo("repairDepart.number"));
        	sic.add(new SelectorItemInfo("repairDepart.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("repairPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("repairPerson.id"));
        	sic.add(new SelectorItemInfo("repairPerson.number"));
        	sic.add(new SelectorItemInfo("repairPerson.name"));
		}
        sic.add(new SelectorItemInfo("repairTime"));
        sic.add(new SelectorItemInfo("repairContent"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("assignee.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("assignee.id"));
        	sic.add(new SelectorItemInfo("assignee.number"));
        	sic.add(new SelectorItemInfo("assignee.name"));
		}
        sic.add(new SelectorItemInfo("acceptTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("maintenanceType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("maintenanceType.id"));
        	sic.add(new SelectorItemInfo("maintenanceType.number"));
        	sic.add(new SelectorItemInfo("maintenanceType.name"));
		}
        sic.add(new SelectorItemInfo("maintenanceProgram"));
        sic.add(new SelectorItemInfo("BIMUDF0021"));
        sic.add(new SelectorItemInfo("repairDate"));
        sic.add(new SelectorItemInfo("acceptSituation"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("deliveryPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("deliveryPerson.id"));
        	sic.add(new SelectorItemInfo("deliveryPerson.number"));
        	sic.add(new SelectorItemInfo("deliveryPerson.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("recipient.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("recipient.id"));
        	sic.add(new SelectorItemInfo("recipient.number"));
        	sic.add(new SelectorItemInfo("recipient.name"));
		}
        sic.add(new SelectorItemInfo("transferTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("slDepart.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("slDepart.id"));
        	sic.add(new SelectorItemInfo("slDepart.number"));
        	sic.add(new SelectorItemInfo("slDepart.name"));
		}
        sic.add(new SelectorItemInfo("selfAmount"));
        sic.add(new SelectorItemInfo("outAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("baoxiuren.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("baoxiuren.id"));
        	sic.add(new SelectorItemInfo("baoxiuren.number"));
        	sic.add(new SelectorItemInfo("baoxiuren.name"));
		}
        sic.add(new SelectorItemInfo("baoxiuTime"));
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
    	

    /**
     * output actionToVoid_actionPerformed method
     */
    public void actionToVoid_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.maintenance.RepairOrderFactory.getRemoteInstance().toVoid(editData);
    }
    	

    /**
     * output actionUnToVoid_actionPerformed method
     */
    public void actionUnToVoid_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.maintenance.RepairOrderFactory.getRemoteInstance().unToVoid(editData);
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
	public RequestContext prepareActionToVoid(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionToVoid() {
    	return false;
    }
	public RequestContext prepareActionUnToVoid(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnToVoid() {
    	return false;
    }

    /**
     * output ActionToVoid class
     */     
    protected class ActionToVoid extends ItemAction {     
    
        public ActionToVoid()
        {
            this(null);
        }

        public ActionToVoid(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionToVoid.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToVoid.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToVoid.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRepairOrderEditUI.this, "ActionToVoid", "actionToVoid_actionPerformed", e);
        }
    }

    /**
     * output ActionUnToVoid class
     */     
    protected class ActionUnToVoid extends ItemAction {     
    
        public ActionUnToVoid()
        {
            this(null);
        }

        public ActionUnToVoid(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnToVoid.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnToVoid.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnToVoid.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRepairOrderEditUI.this, "ActionUnToVoid", "actionUnToVoid_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.maintenance.client", "RepairOrderEditUI");
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
        return com.kingdee.eas.port.equipment.maintenance.client.RepairOrderEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.maintenance.RepairOrderFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo objectValue = new com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/maintenance/RepairOrder";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.maintenance.app.RepairOrderQuery");
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
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}