/**
 * output package name
 */
package com.kingdee.eas.port.equipment.operate.client;

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
public abstract class AbstractEqmAccidentEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEqmAccidentEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conteqmName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conteqmNumner;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conteqmType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conteqmModelType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conteqmAdress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contuseingDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaccidentType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continputPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contssOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaccidentNature;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthappenDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contendDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contloss;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstopTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaccidentDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaccidentManagement;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaccidentCause;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contResultCode;
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
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmteqmName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txteqmNumner;
    protected com.kingdee.bos.ctrl.swing.KDTextField txteqmType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txteqmModelType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txteqmAdress;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtuseingDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtaccidentType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinputPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtssOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtaccidentNature;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkhappenDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkendDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtloss;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtstopTime;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneaccidentDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtaccidentDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneaccidentManagement;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtaccidentManagement;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneaccidentCause;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtaccidentCause;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneResultCode;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtResultCode;
    protected com.kingdee.eas.port.equipment.operate.EqmAccidentInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractEqmAccidentEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEqmAccidentEditUI.class.getName());
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
        this.conteqmName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conteqmNumner = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conteqmType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conteqmModelType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conteqmAdress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contuseingDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaccidentType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continputPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contssOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaccidentNature = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthappenDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contendDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contloss = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contstopTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaccidentDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaccidentManagement = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaccidentCause = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contResultCode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.prmteqmName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txteqmNumner = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txteqmType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txteqmModelType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txteqmAdress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtuseingDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtaccidentType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtinputPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtssOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtaccidentNature = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkhappenDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkendDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtloss = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtstopTime = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.scrollPaneaccidentDescription = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtaccidentDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPaneaccidentManagement = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtaccidentManagement = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPaneaccidentCause = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtaccidentCause = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPaneResultCode = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtResultCode = new com.kingdee.bos.ctrl.swing.KDTextArea();
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
        this.conteqmName.setName("conteqmName");
        this.conteqmNumner.setName("conteqmNumner");
        this.conteqmType.setName("conteqmType");
        this.conteqmModelType.setName("conteqmModelType");
        this.conteqmAdress.setName("conteqmAdress");
        this.contuseingDept.setName("contuseingDept");
        this.contaccidentType.setName("contaccidentType");
        this.continputPerson.setName("continputPerson");
        this.contssOrgUnit.setName("contssOrgUnit");
        this.contaccidentNature.setName("contaccidentNature");
        this.conthappenDate.setName("conthappenDate");
        this.contendDate.setName("contendDate");
        this.contloss.setName("contloss");
        this.contstopTime.setName("contstopTime");
        this.contaccidentDescription.setName("contaccidentDescription");
        this.contaccidentManagement.setName("contaccidentManagement");
        this.contaccidentCause.setName("contaccidentCause");
        this.contResultCode.setName("contResultCode");
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
        this.prmteqmName.setName("prmteqmName");
        this.txteqmNumner.setName("txteqmNumner");
        this.txteqmType.setName("txteqmType");
        this.txteqmModelType.setName("txteqmModelType");
        this.txteqmAdress.setName("txteqmAdress");
        this.prmtuseingDept.setName("prmtuseingDept");
        this.prmtaccidentType.setName("prmtaccidentType");
        this.prmtinputPerson.setName("prmtinputPerson");
        this.prmtssOrgUnit.setName("prmtssOrgUnit");
        this.prmtaccidentNature.setName("prmtaccidentNature");
        this.pkhappenDate.setName("pkhappenDate");
        this.pkendDate.setName("pkendDate");
        this.txtloss.setName("txtloss");
        this.txtstopTime.setName("txtstopTime");
        this.scrollPaneaccidentDescription.setName("scrollPaneaccidentDescription");
        this.txtaccidentDescription.setName("txtaccidentDescription");
        this.scrollPaneaccidentManagement.setName("scrollPaneaccidentManagement");
        this.txtaccidentManagement.setName("txtaccidentManagement");
        this.scrollPaneaccidentCause.setName("scrollPaneaccidentCause");
        this.txtaccidentCause.setName("txtaccidentCause");
        this.scrollPaneResultCode.setName("scrollPaneResultCode");
        this.txtResultCode.setName("txtResultCode");
        // CoreUI
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
        this.contAuditor.setEnabled(false);
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
        this.contAuditTime.setEnabled(false);
        // conteqmName		
        this.conteqmName.setBoundLabelText(resHelper.getString("conteqmName.boundLabelText"));		
        this.conteqmName.setBoundLabelLength(100);		
        this.conteqmName.setBoundLabelUnderline(true);		
        this.conteqmName.setVisible(true);
        // conteqmNumner		
        this.conteqmNumner.setBoundLabelText(resHelper.getString("conteqmNumner.boundLabelText"));		
        this.conteqmNumner.setBoundLabelLength(100);		
        this.conteqmNumner.setBoundLabelUnderline(true);		
        this.conteqmNumner.setVisible(true);
        // conteqmType		
        this.conteqmType.setBoundLabelText(resHelper.getString("conteqmType.boundLabelText"));		
        this.conteqmType.setBoundLabelLength(100);		
        this.conteqmType.setBoundLabelUnderline(true);		
        this.conteqmType.setVisible(true);
        // conteqmModelType		
        this.conteqmModelType.setBoundLabelText(resHelper.getString("conteqmModelType.boundLabelText"));		
        this.conteqmModelType.setBoundLabelLength(100);		
        this.conteqmModelType.setBoundLabelUnderline(true);		
        this.conteqmModelType.setVisible(true);
        // conteqmAdress		
        this.conteqmAdress.setBoundLabelText(resHelper.getString("conteqmAdress.boundLabelText"));		
        this.conteqmAdress.setBoundLabelLength(100);		
        this.conteqmAdress.setBoundLabelUnderline(true);		
        this.conteqmAdress.setVisible(true);
        // contuseingDept		
        this.contuseingDept.setBoundLabelText(resHelper.getString("contuseingDept.boundLabelText"));		
        this.contuseingDept.setBoundLabelLength(100);		
        this.contuseingDept.setBoundLabelUnderline(true);		
        this.contuseingDept.setVisible(true);
        // contaccidentType		
        this.contaccidentType.setBoundLabelText(resHelper.getString("contaccidentType.boundLabelText"));		
        this.contaccidentType.setBoundLabelLength(100);		
        this.contaccidentType.setBoundLabelUnderline(true);		
        this.contaccidentType.setVisible(true);
        // continputPerson		
        this.continputPerson.setBoundLabelText(resHelper.getString("continputPerson.boundLabelText"));		
        this.continputPerson.setBoundLabelLength(100);		
        this.continputPerson.setBoundLabelUnderline(true);		
        this.continputPerson.setVisible(true);
        // contssOrgUnit		
        this.contssOrgUnit.setBoundLabelText(resHelper.getString("contssOrgUnit.boundLabelText"));		
        this.contssOrgUnit.setBoundLabelLength(100);		
        this.contssOrgUnit.setBoundLabelUnderline(true);		
        this.contssOrgUnit.setVisible(true);
        // contaccidentNature		
        this.contaccidentNature.setBoundLabelText(resHelper.getString("contaccidentNature.boundLabelText"));		
        this.contaccidentNature.setBoundLabelLength(100);		
        this.contaccidentNature.setBoundLabelUnderline(true);		
        this.contaccidentNature.setVisible(true);
        // conthappenDate		
        this.conthappenDate.setBoundLabelText(resHelper.getString("conthappenDate.boundLabelText"));		
        this.conthappenDate.setBoundLabelLength(100);		
        this.conthappenDate.setBoundLabelUnderline(true);		
        this.conthappenDate.setVisible(true);
        // contendDate		
        this.contendDate.setBoundLabelText(resHelper.getString("contendDate.boundLabelText"));		
        this.contendDate.setBoundLabelLength(100);		
        this.contendDate.setBoundLabelUnderline(true);		
        this.contendDate.setVisible(true);
        // contloss		
        this.contloss.setBoundLabelText(resHelper.getString("contloss.boundLabelText"));		
        this.contloss.setBoundLabelLength(100);		
        this.contloss.setBoundLabelUnderline(true);		
        this.contloss.setVisible(true);
        // contstopTime		
        this.contstopTime.setBoundLabelText(resHelper.getString("contstopTime.boundLabelText"));		
        this.contstopTime.setBoundLabelLength(120);		
        this.contstopTime.setBoundLabelUnderline(true);		
        this.contstopTime.setVisible(true);
        // contaccidentDescription		
        this.contaccidentDescription.setBoundLabelText(resHelper.getString("contaccidentDescription.boundLabelText"));		
        this.contaccidentDescription.setBoundLabelLength(100);		
        this.contaccidentDescription.setBoundLabelUnderline(true);		
        this.contaccidentDescription.setVisible(true);
        // contaccidentManagement		
        this.contaccidentManagement.setBoundLabelText(resHelper.getString("contaccidentManagement.boundLabelText"));		
        this.contaccidentManagement.setBoundLabelLength(100);		
        this.contaccidentManagement.setBoundLabelUnderline(true);		
        this.contaccidentManagement.setVisible(true);
        // contaccidentCause		
        this.contaccidentCause.setBoundLabelText(resHelper.getString("contaccidentCause.boundLabelText"));		
        this.contaccidentCause.setBoundLabelLength(100);		
        this.contaccidentCause.setBoundLabelUnderline(true);		
        this.contaccidentCause.setVisible(true);
        // contResultCode		
        this.contResultCode.setBoundLabelText(resHelper.getString("contResultCode.boundLabelText"));		
        this.contResultCode.setBoundLabelLength(100);		
        this.contResultCode.setBoundLabelUnderline(true);		
        this.contResultCode.setVisible(true);
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
        // prmteqmName		
        this.prmteqmName.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");		
        this.prmteqmName.setVisible(true);		
        this.prmteqmName.setEditable(true);		
        this.prmteqmName.setDisplayFormat("$name$");		
        this.prmteqmName.setEditFormat("$number$");		
        this.prmteqmName.setCommitFormat("$number$");		
        this.prmteqmName.setRequired(false);
        prmteqmName.addDataChangeListener(new DataChangeListener() {
		public void dataChanged(DataChangeEvent e) {
			try {
				prmteqmName_Changed();
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        // txteqmNumner		
        this.txteqmNumner.setVisible(true);		
        this.txteqmNumner.setHorizontalAlignment(2);		
        this.txteqmNumner.setMaxLength(80);		
        this.txteqmNumner.setRequired(false);
        // txteqmType		
        this.txteqmType.setVisible(true);		
        this.txteqmType.setHorizontalAlignment(2);		
        this.txteqmType.setMaxLength(80);		
        this.txteqmType.setRequired(false);
        // txteqmModelType		
        this.txteqmModelType.setVisible(true);		
        this.txteqmModelType.setHorizontalAlignment(2);		
        this.txteqmModelType.setMaxLength(80);		
        this.txteqmModelType.setRequired(false);
        // txteqmAdress		
        this.txteqmAdress.setVisible(true);		
        this.txteqmAdress.setHorizontalAlignment(2);		
        this.txteqmAdress.setMaxLength(80);		
        this.txteqmAdress.setRequired(false);
        // prmtuseingDept		
        this.prmtuseingDept.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtuseingDept.setVisible(true);		
        this.prmtuseingDept.setEditable(true);		
        this.prmtuseingDept.setDisplayFormat("$name$");		
        this.prmtuseingDept.setEditFormat("$number$");		
        this.prmtuseingDept.setCommitFormat("$number$");		
        this.prmtuseingDept.setRequired(false);
        // prmtaccidentType		
        this.prmtaccidentType.setQueryInfo("com.kingdee.eas.port.equipment.base.app.AccidentTypeQuery");		
        this.prmtaccidentType.setVisible(true);		
        this.prmtaccidentType.setEditable(true);		
        this.prmtaccidentType.setDisplayFormat("$name$");		
        this.prmtaccidentType.setEditFormat("$number$");		
        this.prmtaccidentType.setCommitFormat("$number$");		
        this.prmtaccidentType.setRequired(false);
        // prmtinputPerson		
        this.prmtinputPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtinputPerson.setVisible(true);		
        this.prmtinputPerson.setEditable(true);		
        this.prmtinputPerson.setDisplayFormat("$name$");		
        this.prmtinputPerson.setEditFormat("$number$");		
        this.prmtinputPerson.setCommitFormat("$number$");		
        this.prmtinputPerson.setRequired(false);
        // prmtssOrgUnit		
        this.prmtssOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtssOrgUnit.setVisible(true);		
        this.prmtssOrgUnit.setEditable(true);		
        this.prmtssOrgUnit.setDisplayFormat("$name$");		
        this.prmtssOrgUnit.setEditFormat("$number$");		
        this.prmtssOrgUnit.setCommitFormat("$number$");		
        this.prmtssOrgUnit.setRequired(false);
        // prmtaccidentNature		
        this.prmtaccidentNature.setQueryInfo("com.kingdee.eas.port.equipment.base.app.AccidentNatureQuery");		
        this.prmtaccidentNature.setVisible(true);		
        this.prmtaccidentNature.setEditable(true);		
        this.prmtaccidentNature.setDisplayFormat("$name$");		
        this.prmtaccidentNature.setEditFormat("$number$");		
        this.prmtaccidentNature.setCommitFormat("$number$");		
        this.prmtaccidentNature.setRequired(false);
        // pkhappenDate		
        this.pkhappenDate.setVisible(true);		
        this.pkhappenDate.setRequired(false);
        // pkendDate		
        this.pkendDate.setVisible(true);		
        this.pkendDate.setRequired(false);
        // txtloss		
        this.txtloss.setVisible(true);		
        this.txtloss.setHorizontalAlignment(2);		
        this.txtloss.setMaxLength(100);		
        this.txtloss.setRequired(false);
        // txtstopTime		
        this.txtstopTime.setVisible(true);		
        this.txtstopTime.setHorizontalAlignment(2);		
        this.txtstopTime.setDataType(1);		
        this.txtstopTime.setSupportedEmpty(true);		
        this.txtstopTime.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtstopTime.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtstopTime.setPrecision(10);		
        this.txtstopTime.setRequired(false);
        // scrollPaneaccidentDescription
        // txtaccidentDescription		
        this.txtaccidentDescription.setVisible(true);		
        this.txtaccidentDescription.setRequired(false);		
        this.txtaccidentDescription.setMaxLength(255);
        // scrollPaneaccidentManagement
        // txtaccidentManagement		
        this.txtaccidentManagement.setVisible(true);		
        this.txtaccidentManagement.setRequired(false);		
        this.txtaccidentManagement.setMaxLength(255);
        // scrollPaneaccidentCause
        // txtaccidentCause		
        this.txtaccidentCause.setVisible(true);		
        this.txtaccidentCause.setRequired(false);		
        this.txtaccidentCause.setMaxLength(255);
        // scrollPaneResultCode
        // txtResultCode		
        this.txtResultCode.setVisible(true);		
        this.txtResultCode.setRequired(false);		
        this.txtResultCode.setMaxLength(255);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmteqmName,txteqmNumner,txteqmType,txteqmModelType,txteqmAdress,prmtuseingDept,prmtaccidentType,prmtinputPerson,prmtssOrgUnit,prmtaccidentNature,pkhappenDate,pkendDate,txtloss,txtstopTime,txtaccidentDescription,txtaccidentManagement,txtaccidentCause,txtResultCode}));
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
        this.setBounds(new Rectangle(10, 10, 1013, 477));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 477));
        contCreator.setBounds(new Rectangle(672, 397, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(672, 397, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(672, 424, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(672, 424, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateUser.setBounds(new Rectangle(10, 397, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(10, 397, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(10, 424, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(10, 424, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(341, 682, 270, 19));
        this.add(contCU, new KDLayout.Constraints(341, 682, 270, 19, 0));
        contNumber.setBounds(new Rectangle(672, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(672, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(672, 34, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(672, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(10, 682, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(10, 682, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(341, 397, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(341, 397, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStatus.setBounds(new Rectangle(672, 58, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(672, 58, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(644, 684, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(644, 684, 270, 19, 0));
        contAuditTime.setBounds(new Rectangle(341, 424, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(341, 424, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conteqmName.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(conteqmName, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conteqmNumner.setBounds(new Rectangle(341, 10, 270, 19));
        this.add(conteqmNumner, new KDLayout.Constraints(341, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conteqmType.setBounds(new Rectangle(10, 34, 270, 19));
        this.add(conteqmType, new KDLayout.Constraints(10, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conteqmModelType.setBounds(new Rectangle(341, 34, 270, 19));
        this.add(conteqmModelType, new KDLayout.Constraints(341, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conteqmAdress.setBounds(new Rectangle(10, 58, 270, 19));
        this.add(conteqmAdress, new KDLayout.Constraints(10, 58, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contuseingDept.setBounds(new Rectangle(341, 58, 270, 19));
        this.add(contuseingDept, new KDLayout.Constraints(341, 58, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contaccidentType.setBounds(new Rectangle(10, 82, 270, 19));
        this.add(contaccidentType, new KDLayout.Constraints(10, 82, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continputPerson.setBounds(new Rectangle(341, 82, 270, 19));
        this.add(continputPerson, new KDLayout.Constraints(341, 82, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contssOrgUnit.setBounds(new Rectangle(672, 82, 270, 19));
        this.add(contssOrgUnit, new KDLayout.Constraints(672, 82, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contaccidentNature.setBounds(new Rectangle(10, 106, 270, 19));
        this.add(contaccidentNature, new KDLayout.Constraints(10, 106, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conthappenDate.setBounds(new Rectangle(341, 106, 270, 19));
        this.add(conthappenDate, new KDLayout.Constraints(341, 106, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contendDate.setBounds(new Rectangle(672, 106, 270, 19));
        this.add(contendDate, new KDLayout.Constraints(672, 106, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contloss.setBounds(new Rectangle(10, 130, 270, 19));
        this.add(contloss, new KDLayout.Constraints(10, 130, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contstopTime.setBounds(new Rectangle(341, 130, 270, 19));
        this.add(contstopTime, new KDLayout.Constraints(341, 130, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contaccidentDescription.setBounds(new Rectangle(10, 160, 412, 92));
        this.add(contaccidentDescription, new KDLayout.Constraints(10, 160, 412, 92, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contaccidentManagement.setBounds(new Rectangle(485, 269, 457, 92));
        this.add(contaccidentManagement, new KDLayout.Constraints(485, 269, 457, 92, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contaccidentCause.setBounds(new Rectangle(10, 269, 412, 92));
        this.add(contaccidentCause, new KDLayout.Constraints(10, 269, 412, 92, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contResultCode.setBounds(new Rectangle(485, 160, 457, 92));
        this.add(contResultCode, new KDLayout.Constraints(485, 160, 457, 92, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
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
        //conteqmName
        conteqmName.setBoundEditor(prmteqmName);
        //conteqmNumner
        conteqmNumner.setBoundEditor(txteqmNumner);
        //conteqmType
        conteqmType.setBoundEditor(txteqmType);
        //conteqmModelType
        conteqmModelType.setBoundEditor(txteqmModelType);
        //conteqmAdress
        conteqmAdress.setBoundEditor(txteqmAdress);
        //contuseingDept
        contuseingDept.setBoundEditor(prmtuseingDept);
        //contaccidentType
        contaccidentType.setBoundEditor(prmtaccidentType);
        //continputPerson
        continputPerson.setBoundEditor(prmtinputPerson);
        //contssOrgUnit
        contssOrgUnit.setBoundEditor(prmtssOrgUnit);
        //contaccidentNature
        contaccidentNature.setBoundEditor(prmtaccidentNature);
        //conthappenDate
        conthappenDate.setBoundEditor(pkhappenDate);
        //contendDate
        contendDate.setBoundEditor(pkendDate);
        //contloss
        contloss.setBoundEditor(txtloss);
        //contstopTime
        contstopTime.setBoundEditor(txtstopTime);
        //contaccidentDescription
        contaccidentDescription.setBoundEditor(scrollPaneaccidentDescription);
        //scrollPaneaccidentDescription
        scrollPaneaccidentDescription.getViewport().add(txtaccidentDescription, null);
        //contaccidentManagement
        contaccidentManagement.setBoundEditor(scrollPaneaccidentManagement);
        //scrollPaneaccidentManagement
        scrollPaneaccidentManagement.getViewport().add(txtaccidentManagement, null);
        //contaccidentCause
        contaccidentCause.setBoundEditor(scrollPaneaccidentCause);
        //scrollPaneaccidentCause
        scrollPaneaccidentCause.getViewport().add(txtaccidentCause, null);
        //contResultCode
        contResultCode.setBoundEditor(scrollPaneResultCode);
        //scrollPaneResultCode
        scrollPaneResultCode.getViewport().add(txtResultCode, null);

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
		dataBinder.registerBinding("eqmName", com.kingdee.eas.port.equipment.record.EquIdInfo.class, this.prmteqmName, "data");
		dataBinder.registerBinding("eqmNumner", String.class, this.txteqmNumner, "text");
		dataBinder.registerBinding("eqmType", String.class, this.txteqmType, "text");
		dataBinder.registerBinding("eqmModelType", String.class, this.txteqmModelType, "text");
		dataBinder.registerBinding("eqmAdress", String.class, this.txteqmAdress, "text");
		dataBinder.registerBinding("useingDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtuseingDept, "data");
		dataBinder.registerBinding("accidentType", com.kingdee.eas.port.equipment.base.AccidentTypeInfo.class, this.prmtaccidentType, "data");
		dataBinder.registerBinding("inputPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtinputPerson, "data");
		dataBinder.registerBinding("ssOrgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtssOrgUnit, "data");
		dataBinder.registerBinding("accidentNature", com.kingdee.eas.port.equipment.base.AccidentNatureInfo.class, this.prmtaccidentNature, "data");
		dataBinder.registerBinding("happenDate", java.util.Date.class, this.pkhappenDate, "value");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.pkendDate, "value");
		dataBinder.registerBinding("loss", String.class, this.txtloss, "text");
		dataBinder.registerBinding("stopTime", java.math.BigDecimal.class, this.txtstopTime, "value");
		dataBinder.registerBinding("accidentDescription", String.class, this.txtaccidentDescription, "text");
		dataBinder.registerBinding("accidentManagement", String.class, this.txtaccidentManagement, "text");
		dataBinder.registerBinding("accidentCause", String.class, this.txtaccidentCause, "text");
		dataBinder.registerBinding("ResultCode", String.class, this.txtResultCode, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.operate.app.EqmAccidentEditUIHandler";
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
        this.prmteqmName.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.operate.EqmAccidentInfo)ov;
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
		getValidateHelper().registerBindProperty("eqmName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("eqmNumner", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("eqmType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("eqmModelType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("eqmAdress", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("useingDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accidentType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inputPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ssOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accidentNature", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("happenDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("loss", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("stopTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accidentDescription", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accidentManagement", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accidentCause", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ResultCode", ValidateHelper.ON_SAVE);    		
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
     * output prmteqmName_Changed() method
     */
    public void prmteqmName_Changed() throws Exception
    {
        System.out.println("prmteqmName_Changed() Function is executed!");
            txteqmNumner.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmteqmName.getData(),"number")));

    txteqmType.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmteqmName.getData(),"eqmType.name")));

    txteqmModelType.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmteqmName.getData(),"model")));

    txteqmAdress.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmteqmName.getData(),"address.name")));


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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("eqmName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("eqmName.id"));
        	sic.add(new SelectorItemInfo("eqmName.number"));
        	sic.add(new SelectorItemInfo("eqmName.name"));
		}
        sic.add(new SelectorItemInfo("eqmNumner"));
        sic.add(new SelectorItemInfo("eqmType"));
        sic.add(new SelectorItemInfo("eqmModelType"));
        sic.add(new SelectorItemInfo("eqmAdress"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("useingDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("useingDept.id"));
        	sic.add(new SelectorItemInfo("useingDept.number"));
        	sic.add(new SelectorItemInfo("useingDept.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("accidentType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("accidentType.id"));
        	sic.add(new SelectorItemInfo("accidentType.number"));
        	sic.add(new SelectorItemInfo("accidentType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("inputPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("inputPerson.id"));
        	sic.add(new SelectorItemInfo("inputPerson.number"));
        	sic.add(new SelectorItemInfo("inputPerson.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("ssOrgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("ssOrgUnit.id"));
        	sic.add(new SelectorItemInfo("ssOrgUnit.number"));
        	sic.add(new SelectorItemInfo("ssOrgUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("accidentNature.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("accidentNature.id"));
        	sic.add(new SelectorItemInfo("accidentNature.number"));
        	sic.add(new SelectorItemInfo("accidentNature.name"));
		}
        sic.add(new SelectorItemInfo("happenDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("loss"));
        sic.add(new SelectorItemInfo("stopTime"));
        sic.add(new SelectorItemInfo("accidentDescription"));
        sic.add(new SelectorItemInfo("accidentManagement"));
        sic.add(new SelectorItemInfo("accidentCause"));
        sic.add(new SelectorItemInfo("ResultCode"));
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
        return new MetaDataPK("com.kingdee.eas.port.equipment.operate.client", "EqmAccidentEditUI");
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
        return com.kingdee.eas.port.equipment.operate.client.EqmAccidentEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.operate.EqmAccidentFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.operate.EqmAccidentInfo objectValue = new com.kingdee.eas.port.equipment.operate.EqmAccidentInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/operate/EqmAccident";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.operate.app.EqmAccidentQuery");
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
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}