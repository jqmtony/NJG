/**
 * output package name
 */
package com.kingdee.eas.port.equipment.record.client;

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
public abstract class AbstractMeasurEquipEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMeasurEquipEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contname;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmodel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmeasurRange;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaccuracy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmanufacturer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfactoryNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfactoryDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contuseDepart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contusePerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contverificatCycle;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcheckDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contabc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contvalidityPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmanageState;
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
    protected com.kingdee.bos.ctrl.swing.KDTextField txtname;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmodel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmeasurRange;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtaccuracy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmanufacturer;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfactoryNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkfactoryDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtuseDepart;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtusePerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkstartDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtverificatCycle;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkcheckDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtabc;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkvalidityPeriod;
    protected com.kingdee.bos.ctrl.swing.KDComboBox manageState;
    protected com.kingdee.eas.port.equipment.record.MeasurEquipInfo editData = null;
    protected ActionExcel actionExcel = null;
    /**
     * output class constructor
     */
    public AbstractMeasurEquipEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMeasurEquipEditUI.class.getName());
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
        //actionExcel
        this.actionExcel = new ActionExcel(this);
        getActionManager().registerAction("actionExcel", actionExcel);
        this.actionExcel.setExtendProperty("canForewarn", "true");
        this.actionExcel.setExtendProperty("userDefined", "true");
        this.actionExcel.setExtendProperty("isObjectUpdateLock", "false");
         this.actionExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionExcel.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
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
        this.contname = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmodel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmeasurRange = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaccuracy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmanufacturer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfactoryNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfactoryDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contuseDepart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contusePerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contstartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contverificatCycle = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcheckDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contabc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contvalidityPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmanageState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.txtname = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtmodel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtmeasurRange = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtaccuracy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtmanufacturer = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtfactoryNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkfactoryDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtuseDepart = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtusePerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkstartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtverificatCycle = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkcheckDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtabc = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkvalidityPeriod = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.manageState = new com.kingdee.bos.ctrl.swing.KDComboBox();
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
        this.contname.setName("contname");
        this.contmodel.setName("contmodel");
        this.contmeasurRange.setName("contmeasurRange");
        this.contaccuracy.setName("contaccuracy");
        this.contmanufacturer.setName("contmanufacturer");
        this.contfactoryNumber.setName("contfactoryNumber");
        this.contfactoryDate.setName("contfactoryDate");
        this.contuseDepart.setName("contuseDepart");
        this.contusePerson.setName("contusePerson");
        this.contstartDate.setName("contstartDate");
        this.contverificatCycle.setName("contverificatCycle");
        this.contcheckDate.setName("contcheckDate");
        this.contabc.setName("contabc");
        this.contvalidityPeriod.setName("contvalidityPeriod");
        this.contmanageState.setName("contmanageState");
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
        this.txtname.setName("txtname");
        this.txtmodel.setName("txtmodel");
        this.txtmeasurRange.setName("txtmeasurRange");
        this.txtaccuracy.setName("txtaccuracy");
        this.txtmanufacturer.setName("txtmanufacturer");
        this.txtfactoryNumber.setName("txtfactoryNumber");
        this.pkfactoryDate.setName("pkfactoryDate");
        this.prmtuseDepart.setName("prmtuseDepart");
        this.prmtusePerson.setName("prmtusePerson");
        this.pkstartDate.setName("pkstartDate");
        this.txtverificatCycle.setName("txtverificatCycle");
        this.pkcheckDate.setName("pkcheckDate");
        this.txtabc.setName("txtabc");
        this.pkvalidityPeriod.setName("pkvalidityPeriod");
        this.manageState.setName("manageState");
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
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contname		
        this.contname.setBoundLabelText(resHelper.getString("contname.boundLabelText"));		
        this.contname.setBoundLabelLength(100);		
        this.contname.setBoundLabelUnderline(true);		
        this.contname.setVisible(true);
        // contmodel		
        this.contmodel.setBoundLabelText(resHelper.getString("contmodel.boundLabelText"));		
        this.contmodel.setBoundLabelLength(100);		
        this.contmodel.setBoundLabelUnderline(true);		
        this.contmodel.setVisible(true);
        // contmeasurRange		
        this.contmeasurRange.setBoundLabelText(resHelper.getString("contmeasurRange.boundLabelText"));		
        this.contmeasurRange.setBoundLabelLength(100);		
        this.contmeasurRange.setBoundLabelUnderline(true);		
        this.contmeasurRange.setVisible(true);
        // contaccuracy		
        this.contaccuracy.setBoundLabelText(resHelper.getString("contaccuracy.boundLabelText"));		
        this.contaccuracy.setBoundLabelLength(100);		
        this.contaccuracy.setBoundLabelUnderline(true);		
        this.contaccuracy.setVisible(true);
        // contmanufacturer		
        this.contmanufacturer.setBoundLabelText(resHelper.getString("contmanufacturer.boundLabelText"));		
        this.contmanufacturer.setBoundLabelLength(100);		
        this.contmanufacturer.setBoundLabelUnderline(true);		
        this.contmanufacturer.setVisible(true);
        // contfactoryNumber		
        this.contfactoryNumber.setBoundLabelText(resHelper.getString("contfactoryNumber.boundLabelText"));		
        this.contfactoryNumber.setBoundLabelLength(100);		
        this.contfactoryNumber.setBoundLabelUnderline(true);		
        this.contfactoryNumber.setVisible(true);
        // contfactoryDate		
        this.contfactoryDate.setBoundLabelText(resHelper.getString("contfactoryDate.boundLabelText"));		
        this.contfactoryDate.setBoundLabelLength(100);		
        this.contfactoryDate.setBoundLabelUnderline(true);		
        this.contfactoryDate.setVisible(true);
        // contuseDepart		
        this.contuseDepart.setBoundLabelText(resHelper.getString("contuseDepart.boundLabelText"));		
        this.contuseDepart.setBoundLabelLength(100);		
        this.contuseDepart.setBoundLabelUnderline(true);		
        this.contuseDepart.setVisible(true);
        // contusePerson		
        this.contusePerson.setBoundLabelText(resHelper.getString("contusePerson.boundLabelText"));		
        this.contusePerson.setBoundLabelLength(100);		
        this.contusePerson.setBoundLabelUnderline(true);		
        this.contusePerson.setVisible(true);
        // contstartDate		
        this.contstartDate.setBoundLabelText(resHelper.getString("contstartDate.boundLabelText"));		
        this.contstartDate.setBoundLabelLength(100);		
        this.contstartDate.setBoundLabelUnderline(true);		
        this.contstartDate.setVisible(true);
        // contverificatCycle		
        this.contverificatCycle.setBoundLabelText(resHelper.getString("contverificatCycle.boundLabelText"));		
        this.contverificatCycle.setBoundLabelLength(100);		
        this.contverificatCycle.setBoundLabelUnderline(true);		
        this.contverificatCycle.setVisible(true);
        // contcheckDate		
        this.contcheckDate.setBoundLabelText(resHelper.getString("contcheckDate.boundLabelText"));		
        this.contcheckDate.setBoundLabelLength(100);		
        this.contcheckDate.setBoundLabelUnderline(true);		
        this.contcheckDate.setVisible(true);
        // contabc		
        this.contabc.setBoundLabelText(resHelper.getString("contabc.boundLabelText"));		
        this.contabc.setBoundLabelLength(100);		
        this.contabc.setBoundLabelUnderline(true);		
        this.contabc.setVisible(true);
        // contvalidityPeriod		
        this.contvalidityPeriod.setBoundLabelText(resHelper.getString("contvalidityPeriod.boundLabelText"));		
        this.contvalidityPeriod.setBoundLabelLength(100);		
        this.contvalidityPeriod.setBoundLabelUnderline(true);		
        this.contvalidityPeriod.setVisible(true);
        // contmanageState		
        this.contmanageState.setBoundLabelText(resHelper.getString("contmanageState.boundLabelText"));		
        this.contmanageState.setBoundLabelLength(100);		
        this.contmanageState.setBoundLabelUnderline(true);		
        this.contmanageState.setVisible(true);
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
        // txtname		
        this.txtname.setVisible(true);		
        this.txtname.setHorizontalAlignment(2);		
        this.txtname.setMaxLength(100);		
        this.txtname.setRequired(false);
        // txtmodel		
        this.txtmodel.setVisible(true);		
        this.txtmodel.setHorizontalAlignment(2);		
        this.txtmodel.setMaxLength(100);		
        this.txtmodel.setRequired(false);
        // txtmeasurRange		
        this.txtmeasurRange.setVisible(true);		
        this.txtmeasurRange.setHorizontalAlignment(2);		
        this.txtmeasurRange.setMaxLength(100);		
        this.txtmeasurRange.setRequired(false);
        // txtaccuracy		
        this.txtaccuracy.setVisible(true);		
        this.txtaccuracy.setHorizontalAlignment(2);		
        this.txtaccuracy.setMaxLength(100);		
        this.txtaccuracy.setRequired(false);
        // txtmanufacturer		
        this.txtmanufacturer.setVisible(true);		
        this.txtmanufacturer.setHorizontalAlignment(2);		
        this.txtmanufacturer.setMaxLength(100);		
        this.txtmanufacturer.setRequired(false);
        // txtfactoryNumber		
        this.txtfactoryNumber.setVisible(true);		
        this.txtfactoryNumber.setHorizontalAlignment(2);		
        this.txtfactoryNumber.setMaxLength(100);		
        this.txtfactoryNumber.setRequired(false);
        // pkfactoryDate		
        this.pkfactoryDate.setVisible(true);		
        this.pkfactoryDate.setRequired(false);
        // prmtuseDepart		
        this.prmtuseDepart.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtuseDepart.setVisible(true);		
        this.prmtuseDepart.setEditable(true);		
        this.prmtuseDepart.setDisplayFormat("$name$");		
        this.prmtuseDepart.setEditFormat("$number$");		
        this.prmtuseDepart.setCommitFormat("$number$");		
        this.prmtuseDepart.setRequired(false);
        // prmtusePerson		
        this.prmtusePerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtusePerson.setVisible(true);		
        this.prmtusePerson.setEditable(true);		
        this.prmtusePerson.setDisplayFormat("$name$");		
        this.prmtusePerson.setEditFormat("$number$");		
        this.prmtusePerson.setCommitFormat("$number$");		
        this.prmtusePerson.setRequired(false);
        // pkstartDate		
        this.pkstartDate.setVisible(true);		
        this.pkstartDate.setRequired(false);
        // txtverificatCycle		
        this.txtverificatCycle.setVisible(true);		
        this.txtverificatCycle.setHorizontalAlignment(2);		
        this.txtverificatCycle.setDataType(0);		
        this.txtverificatCycle.setSupportedEmpty(true);		
        this.txtverificatCycle.setRequired(false);
        // pkcheckDate		
        this.pkcheckDate.setVisible(true);		
        this.pkcheckDate.setRequired(false);
        // txtabc		
        this.txtabc.setVisible(true);		
        this.txtabc.setHorizontalAlignment(2);		
        this.txtabc.setMaxLength(100);		
        this.txtabc.setRequired(false);
        // pkvalidityPeriod		
        this.pkvalidityPeriod.setVisible(true);		
        this.pkvalidityPeriod.setRequired(false);
        // manageState		
        this.manageState.setVisible(true);		
        this.manageState.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.equipment.base.enumbase.sbStatusType").toArray());		
        this.manageState.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtname,txtmodel,txtmeasurRange,txtaccuracy,txtmanufacturer,txtfactoryNumber,pkfactoryDate,prmtuseDepart,prmtusePerson,pkstartDate,txtverificatCycle,pkcheckDate,txtabc,pkvalidityPeriod,manageState}));
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
        this.setBounds(new Rectangle(10, 10, 1013, 274));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 274));
        contCreator.setBounds(new Rectangle(8, 209, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(8, 209, 270, 19, 0));
        contCreateTime.setBounds(new Rectangle(8, 245, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(8, 245, 270, 19, 0));
        contLastUpdateUser.setBounds(new Rectangle(371, 209, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(371, 209, 270, 19, 0));
        contLastUpdateTime.setBounds(new Rectangle(371, 245, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(371, 245, 270, 19, 0));
        contCU.setBounds(new Rectangle(734, 11, 270, 19));
        this.add(contCU, new KDLayout.Constraints(734, 11, 270, 19, 0));
        contNumber.setBounds(new Rectangle(8, 11, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(8, 11, 270, 19, 0));
        contBizDate.setBounds(new Rectangle(962, 342, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(962, 342, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(962, 318, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(962, 318, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(735, 209, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(735, 209, 270, 19, 0));
        contStatus.setBounds(new Rectangle(735, 176, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(735, 176, 270, 19, 0));
        contBizStatus.setBounds(new Rectangle(954, 300, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(954, 300, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(735, 245, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(735, 245, 270, 19, 0));
        contname.setBounds(new Rectangle(371, 11, 270, 19));
        this.add(contname, new KDLayout.Constraints(371, 11, 270, 19, 0));
        contmodel.setBounds(new Rectangle(8, 44, 270, 19));
        this.add(contmodel, new KDLayout.Constraints(8, 44, 270, 19, 0));
        contmeasurRange.setBounds(new Rectangle(371, 44, 270, 19));
        this.add(contmeasurRange, new KDLayout.Constraints(371, 44, 270, 19, 0));
        contaccuracy.setBounds(new Rectangle(734, 44, 270, 19));
        this.add(contaccuracy, new KDLayout.Constraints(734, 44, 270, 19, 0));
        contmanufacturer.setBounds(new Rectangle(8, 77, 270, 19));
        this.add(contmanufacturer, new KDLayout.Constraints(8, 77, 270, 19, 0));
        contfactoryNumber.setBounds(new Rectangle(371, 77, 270, 19));
        this.add(contfactoryNumber, new KDLayout.Constraints(371, 77, 270, 19, 0));
        contfactoryDate.setBounds(new Rectangle(734, 77, 270, 19));
        this.add(contfactoryDate, new KDLayout.Constraints(734, 77, 270, 19, 0));
        contuseDepart.setBounds(new Rectangle(371, 110, 270, 19));
        this.add(contuseDepart, new KDLayout.Constraints(371, 110, 270, 19, 0));
        contusePerson.setBounds(new Rectangle(734, 110, 270, 19));
        this.add(contusePerson, new KDLayout.Constraints(734, 110, 270, 19, 0));
        contstartDate.setBounds(new Rectangle(8, 143, 270, 19));
        this.add(contstartDate, new KDLayout.Constraints(8, 143, 270, 19, 0));
        contverificatCycle.setBounds(new Rectangle(371, 143, 270, 19));
        this.add(contverificatCycle, new KDLayout.Constraints(371, 143, 270, 19, 0));
        contcheckDate.setBounds(new Rectangle(734, 143, 270, 19));
        this.add(contcheckDate, new KDLayout.Constraints(734, 143, 270, 19, 0));
        contabc.setBounds(new Rectangle(371, 176, 270, 19));
        this.add(contabc, new KDLayout.Constraints(371, 176, 270, 19, 0));
        contvalidityPeriod.setBounds(new Rectangle(8, 176, 270, 19));
        this.add(contvalidityPeriod, new KDLayout.Constraints(8, 176, 270, 19, 0));
        contmanageState.setBounds(new Rectangle(8, 110, 270, 19));
        this.add(contmanageState, new KDLayout.Constraints(8, 110, 270, 19, 0));
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
        //contname
        contname.setBoundEditor(txtname);
        //contmodel
        contmodel.setBoundEditor(txtmodel);
        //contmeasurRange
        contmeasurRange.setBoundEditor(txtmeasurRange);
        //contaccuracy
        contaccuracy.setBoundEditor(txtaccuracy);
        //contmanufacturer
        contmanufacturer.setBoundEditor(txtmanufacturer);
        //contfactoryNumber
        contfactoryNumber.setBoundEditor(txtfactoryNumber);
        //contfactoryDate
        contfactoryDate.setBoundEditor(pkfactoryDate);
        //contuseDepart
        contuseDepart.setBoundEditor(prmtuseDepart);
        //contusePerson
        contusePerson.setBoundEditor(prmtusePerson);
        //contstartDate
        contstartDate.setBoundEditor(pkstartDate);
        //contverificatCycle
        contverificatCycle.setBoundEditor(txtverificatCycle);
        //contcheckDate
        contcheckDate.setBoundEditor(pkcheckDate);
        //contabc
        contabc.setBoundEditor(txtabc);
        //contvalidityPeriod
        contvalidityPeriod.setBoundEditor(pkvalidityPeriod);
        //contmanageState
        contmanageState.setBoundEditor(manageState);

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
		dataBinder.registerBinding("name", String.class, this.txtname, "text");
		dataBinder.registerBinding("model", String.class, this.txtmodel, "text");
		dataBinder.registerBinding("measurRange", String.class, this.txtmeasurRange, "text");
		dataBinder.registerBinding("accuracy", String.class, this.txtaccuracy, "text");
		dataBinder.registerBinding("manufacturer", String.class, this.txtmanufacturer, "text");
		dataBinder.registerBinding("factoryNumber", String.class, this.txtfactoryNumber, "text");
		dataBinder.registerBinding("factoryDate", java.util.Date.class, this.pkfactoryDate, "value");
		dataBinder.registerBinding("useDepart", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtuseDepart, "data");
		dataBinder.registerBinding("usePerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtusePerson, "data");
		dataBinder.registerBinding("startDate", java.util.Date.class, this.pkstartDate, "value");
		dataBinder.registerBinding("verificatCycle", int.class, this.txtverificatCycle, "value");
		dataBinder.registerBinding("checkDate", java.util.Date.class, this.pkcheckDate, "value");
		dataBinder.registerBinding("abc", String.class, this.txtabc, "text");
		dataBinder.registerBinding("validityPeriod", java.util.Date.class, this.pkvalidityPeriod, "value");
		dataBinder.registerBinding("manageState", com.kingdee.eas.port.equipment.base.enumbase.sbStatusType.class, this.manageState, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.record.app.MeasurEquipEditUIHandler";
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
        this.txtname.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.record.MeasurEquipInfo)ov;
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
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("model", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("measurRange", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accuracy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("manufacturer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("factoryNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("factoryDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("useDepart", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("usePerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("verificatCycle", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("checkDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("abc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("validityPeriod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("manageState", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("model"));
        sic.add(new SelectorItemInfo("measurRange"));
        sic.add(new SelectorItemInfo("accuracy"));
        sic.add(new SelectorItemInfo("manufacturer"));
        sic.add(new SelectorItemInfo("factoryNumber"));
        sic.add(new SelectorItemInfo("factoryDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("useDepart.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("useDepart.id"));
        	sic.add(new SelectorItemInfo("useDepart.number"));
        	sic.add(new SelectorItemInfo("useDepart.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("usePerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("usePerson.id"));
        	sic.add(new SelectorItemInfo("usePerson.number"));
        	sic.add(new SelectorItemInfo("usePerson.name"));
		}
        sic.add(new SelectorItemInfo("startDate"));
        sic.add(new SelectorItemInfo("verificatCycle"));
        sic.add(new SelectorItemInfo("checkDate"));
        sic.add(new SelectorItemInfo("abc"));
        sic.add(new SelectorItemInfo("validityPeriod"));
        sic.add(new SelectorItemInfo("manageState"));
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
     * output actionExcel_actionPerformed method
     */
    public void actionExcel_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.record.MeasurEquipFactory.getRemoteInstance().excel(editData);
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
	public RequestContext prepareActionExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExcel() {
    	return false;
    }

    /**
     * output ActionExcel class
     */     
    protected class ActionExcel extends ItemAction {     
    
        public ActionExcel()
        {
            this(null);
        }

        public ActionExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMeasurEquipEditUI.this, "ActionExcel", "actionExcel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.record.client", "MeasurEquipEditUI");
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
        return com.kingdee.eas.port.equipment.record.client.MeasurEquipEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.record.MeasurEquipFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.record.MeasurEquipInfo objectValue = new com.kingdee.eas.port.equipment.record.MeasurEquipInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/record/MeasurEquip";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.record.app.MeasurEquipQuery");
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
		vo.put("manageState","1");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}