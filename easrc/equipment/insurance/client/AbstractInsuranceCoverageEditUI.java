/**
 * output package name
 */
package com.kingdee.eas.port.equipment.insurance.client;

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
public abstract class AbstractInsuranceCoverageEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInsuranceCoverageEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continsurance;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continsuranceCompany;
    protected com.kingdee.bos.ctrl.swing.KDButton kDInsuranceDetail;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDButton btnExportExcel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportExcel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExcel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contxianzhongID;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcoverNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conteffectDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contendDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttbrmc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contxzdm;
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
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinsurance;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinsuranceCompany;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE1;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE1_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanexianzhongID;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtxianzhongID;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcoverNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcontNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkeffectDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkendDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyear;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmttbrmc;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtxzdm;
    protected com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo editData = null;
    protected ActionExcelBxmx actionExcelBxmx = null;
    /**
     * output class constructor
     */
    public AbstractInsuranceCoverageEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInsuranceCoverageEditUI.class.getName());
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
        //actionExcelBxmx
        this.actionExcelBxmx = new ActionExcelBxmx(this);
        getActionManager().registerAction("actionExcelBxmx", actionExcelBxmx);
        this.actionExcelBxmx.setExtendProperty("canForewarn", "true");
        this.actionExcelBxmx.setExtendProperty("userDefined", "true");
        this.actionExcelBxmx.setExtendProperty("isObjectUpdateLock", "false");
         this.actionExcelBxmx.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionExcelBxmx.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
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
        this.continsurance = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continsuranceCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDInsuranceDetail = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnExportExcel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnImportExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contxianzhongID = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcoverNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conteffectDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contendDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttbrmc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contxzdm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.prmtinsurance = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtinsuranceCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtE1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.scrollPanexianzhongID = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtxianzhongID = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtcoverNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcontNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkeffectDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkendDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtyear = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmttbrmc = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtxzdm = new com.kingdee.bos.ctrl.swing.KDTextField();
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
        this.continsurance.setName("continsurance");
        this.continsuranceCompany.setName("continsuranceCompany");
        this.kDInsuranceDetail.setName("kDInsuranceDetail");
        this.kDContainer1.setName("kDContainer1");
        this.btnExportExcel.setName("btnExportExcel");
        this.btnImportExcel.setName("btnImportExcel");
        this.btnExcel.setName("btnExcel");
        this.contxianzhongID.setName("contxianzhongID");
        this.contcoverNumber.setName("contcoverNumber");
        this.contcontNumber.setName("contcontNumber");
        this.conteffectDate.setName("conteffectDate");
        this.contendDate.setName("contendDate");
        this.contyear.setName("contyear");
        this.conttbrmc.setName("conttbrmc");
        this.contxzdm.setName("contxzdm");
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
        this.prmtinsurance.setName("prmtinsurance");
        this.prmtinsuranceCompany.setName("prmtinsuranceCompany");
        this.kdtE1.setName("kdtE1");
        this.scrollPanexianzhongID.setName("scrollPanexianzhongID");
        this.txtxianzhongID.setName("txtxianzhongID");
        this.txtcoverNumber.setName("txtcoverNumber");
        this.txtcontNumber.setName("txtcontNumber");
        this.pkeffectDate.setName("pkeffectDate");
        this.pkendDate.setName("pkendDate");
        this.txtyear.setName("txtyear");
        this.prmttbrmc.setName("prmttbrmc");
        this.txtxzdm.setName("txtxzdm");
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
        // continsurance		
        this.continsurance.setBoundLabelText(resHelper.getString("continsurance.boundLabelText"));		
        this.continsurance.setBoundLabelLength(100);		
        this.continsurance.setBoundLabelUnderline(true);		
        this.continsurance.setVisible(true);
        // continsuranceCompany		
        this.continsuranceCompany.setBoundLabelText(resHelper.getString("continsuranceCompany.boundLabelText"));		
        this.continsuranceCompany.setBoundLabelLength(100);		
        this.continsuranceCompany.setBoundLabelUnderline(true);		
        this.continsuranceCompany.setVisible(true);
        // kDInsuranceDetail		
        this.kDInsuranceDetail.setText(resHelper.getString("kDInsuranceDetail.text"));
        // kDContainer1		
        this.kDContainer1.setEnableActive(false);
        // btnExportExcel		
        this.btnExportExcel.setText(resHelper.getString("btnExportExcel.text"));
        this.btnExportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnExportExcel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnImportExcel		
        this.btnImportExcel.setText(resHelper.getString("btnImportExcel.text"));
        this.btnImportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnImportExcel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnExcel		
        this.btnExcel.setText(resHelper.getString("btnExcel.text"));
        this.btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnExcel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contxianzhongID		
        this.contxianzhongID.setBoundLabelText(resHelper.getString("contxianzhongID.boundLabelText"));		
        this.contxianzhongID.setBoundLabelLength(100);		
        this.contxianzhongID.setBoundLabelUnderline(true);		
        this.contxianzhongID.setVisible(true);
        // contcoverNumber		
        this.contcoverNumber.setBoundLabelText(resHelper.getString("contcoverNumber.boundLabelText"));		
        this.contcoverNumber.setBoundLabelLength(100);		
        this.contcoverNumber.setBoundLabelUnderline(true);		
        this.contcoverNumber.setVisible(true);
        // contcontNumber		
        this.contcontNumber.setBoundLabelText(resHelper.getString("contcontNumber.boundLabelText"));		
        this.contcontNumber.setBoundLabelLength(100);		
        this.contcontNumber.setBoundLabelUnderline(true);		
        this.contcontNumber.setVisible(true);
        // conteffectDate		
        this.conteffectDate.setBoundLabelText(resHelper.getString("conteffectDate.boundLabelText"));		
        this.conteffectDate.setBoundLabelLength(100);		
        this.conteffectDate.setBoundLabelUnderline(true);		
        this.conteffectDate.setVisible(true);
        // contendDate		
        this.contendDate.setBoundLabelText(resHelper.getString("contendDate.boundLabelText"));		
        this.contendDate.setBoundLabelLength(100);		
        this.contendDate.setBoundLabelUnderline(true);		
        this.contendDate.setVisible(true);
        // contyear		
        this.contyear.setBoundLabelText(resHelper.getString("contyear.boundLabelText"));		
        this.contyear.setBoundLabelLength(100);		
        this.contyear.setBoundLabelUnderline(true);		
        this.contyear.setVisible(true);
        // conttbrmc		
        this.conttbrmc.setBoundLabelText(resHelper.getString("conttbrmc.boundLabelText"));		
        this.conttbrmc.setBoundLabelLength(100);		
        this.conttbrmc.setBoundLabelUnderline(true);		
        this.conttbrmc.setVisible(true);
        // contxzdm		
        this.contxzdm.setBoundLabelText(resHelper.getString("contxzdm.boundLabelText"));		
        this.contxzdm.setBoundLabelLength(100);		
        this.contxzdm.setBoundLabelUnderline(true);		
        this.contxzdm.setVisible(true);
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
        // prmtinsurance		
        this.prmtinsurance.setQueryInfo("com.kingdee.eas.port.equipment.base.app.InsuranceQuery");		
        this.prmtinsurance.setEditable(true);		
        this.prmtinsurance.setDisplayFormat("$name$");		
        this.prmtinsurance.setEditFormat("$number$");		
        this.prmtinsurance.setCommitFormat("$number$");		
        this.prmtinsurance.setRequired(false);
        // prmtinsuranceCompany		
        this.prmtinsuranceCompany.setQueryInfo("com.kingdee.eas.port.equipment.base.app.InsuranceCompanyQuery");		
        this.prmtinsuranceCompany.setEditable(true);		
        this.prmtinsuranceCompany.setDisplayFormat("$name$");		
        this.prmtinsuranceCompany.setEditFormat("$number$");		
        this.prmtinsuranceCompany.setCommitFormat("$number$");		
        this.prmtinsuranceCompany.setRequired(false);
        // kdtE1
		String kdtE1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"useUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"equNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"equType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"equName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"specModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"factoryUseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"makeUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"tonnage\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"originalValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"presentValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"insuranceAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"premium\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{useUnit}</t:Cell><t:Cell>$Resource{equNumber}</t:Cell><t:Cell>$Resource{equType}</t:Cell><t:Cell>$Resource{equName}</t:Cell><t:Cell>$Resource{specModel}</t:Cell><t:Cell>$Resource{factoryUseDate}</t:Cell><t:Cell>$Resource{makeUnit}</t:Cell><t:Cell>$Resource{tonnage}</t:Cell><t:Cell>$Resource{originalValue}</t:Cell><t:Cell>$Resource{presentValue}</t:Cell><t:Cell>$Resource{insuranceAmount}</t:Cell><t:Cell>$Resource{premium}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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


                this.kdtE1.putBindContents("editData",new String[] {"seq","useUnit","equNumber","equType","equName","specModel","factoryUseDate","makeUnit","tonnage","originalValue","presentValue","insuranceAmount","premium","remark"});


        this.kdtE1.checkParsed();
        KDFormattedTextField kdtE1_seq_TextField = new KDFormattedTextField();
        kdtE1_seq_TextField.setName("kdtE1_seq_TextField");
        kdtE1_seq_TextField.setVisible(true);
        kdtE1_seq_TextField.setEditable(true);
        kdtE1_seq_TextField.setHorizontalAlignment(2);
        kdtE1_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtE1_seq_CellEditor = new KDTDefaultCellEditor(kdtE1_seq_TextField);
        this.kdtE1.getColumn("seq").setEditor(kdtE1_seq_CellEditor);
        final KDBizPromptBox kdtE1_useUnit_PromptBox = new KDBizPromptBox();
        kdtE1_useUnit_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");
        kdtE1_useUnit_PromptBox.setVisible(true);
        kdtE1_useUnit_PromptBox.setEditable(true);
        kdtE1_useUnit_PromptBox.setDisplayFormat("$number$");
        kdtE1_useUnit_PromptBox.setEditFormat("$number$");
        kdtE1_useUnit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_useUnit_CellEditor = new KDTDefaultCellEditor(kdtE1_useUnit_PromptBox);
        this.kdtE1.getColumn("useUnit").setEditor(kdtE1_useUnit_CellEditor);
        ObjectValueRender kdtE1_useUnit_OVR = new ObjectValueRender();
        kdtE1_useUnit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE1.getColumn("useUnit").setRenderer(kdtE1_useUnit_OVR);
        final KDBizPromptBox kdtE1_equNumber_PromptBox = new KDBizPromptBox();
        kdtE1_equNumber_PromptBox.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");
        kdtE1_equNumber_PromptBox.setVisible(true);
        kdtE1_equNumber_PromptBox.setEditable(true);
        kdtE1_equNumber_PromptBox.setDisplayFormat("$number$");
        kdtE1_equNumber_PromptBox.setEditFormat("$number$");
        kdtE1_equNumber_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_equNumber_CellEditor = new KDTDefaultCellEditor(kdtE1_equNumber_PromptBox);
        this.kdtE1.getColumn("equNumber").setEditor(kdtE1_equNumber_CellEditor);
        ObjectValueRender kdtE1_equNumber_OVR = new ObjectValueRender();
        kdtE1_equNumber_OVR.setFormat(new BizDataFormat("$number$"));
        this.kdtE1.getColumn("equNumber").setRenderer(kdtE1_equNumber_OVR);
        KDTextField kdtE1_equType_TextField = new KDTextField();
        kdtE1_equType_TextField.setName("kdtE1_equType_TextField");
        kdtE1_equType_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE1_equType_CellEditor = new KDTDefaultCellEditor(kdtE1_equType_TextField);
        this.kdtE1.getColumn("equType").setEditor(kdtE1_equType_CellEditor);
        KDTextField kdtE1_equName_TextField = new KDTextField();
        kdtE1_equName_TextField.setName("kdtE1_equName_TextField");
        kdtE1_equName_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE1_equName_CellEditor = new KDTDefaultCellEditor(kdtE1_equName_TextField);
        this.kdtE1.getColumn("equName").setEditor(kdtE1_equName_CellEditor);
        KDTextField kdtE1_specModel_TextField = new KDTextField();
        kdtE1_specModel_TextField.setName("kdtE1_specModel_TextField");
        kdtE1_specModel_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE1_specModel_CellEditor = new KDTDefaultCellEditor(kdtE1_specModel_TextField);
        this.kdtE1.getColumn("specModel").setEditor(kdtE1_specModel_CellEditor);
        KDDatePicker kdtE1_factoryUseDate_DatePicker = new KDDatePicker();
        kdtE1_factoryUseDate_DatePicker.setName("kdtE1_factoryUseDate_DatePicker");
        kdtE1_factoryUseDate_DatePicker.setVisible(true);
        kdtE1_factoryUseDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtE1_factoryUseDate_CellEditor = new KDTDefaultCellEditor(kdtE1_factoryUseDate_DatePicker);
        this.kdtE1.getColumn("factoryUseDate").setEditor(kdtE1_factoryUseDate_CellEditor);
        KDTextField kdtE1_makeUnit_TextField = new KDTextField();
        kdtE1_makeUnit_TextField.setName("kdtE1_makeUnit_TextField");
        kdtE1_makeUnit_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE1_makeUnit_CellEditor = new KDTDefaultCellEditor(kdtE1_makeUnit_TextField);
        this.kdtE1.getColumn("makeUnit").setEditor(kdtE1_makeUnit_CellEditor);
        KDTextField kdtE1_tonnage_TextField = new KDTextField();
        kdtE1_tonnage_TextField.setName("kdtE1_tonnage_TextField");
        kdtE1_tonnage_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE1_tonnage_CellEditor = new KDTDefaultCellEditor(kdtE1_tonnage_TextField);
        this.kdtE1.getColumn("tonnage").setEditor(kdtE1_tonnage_CellEditor);
        KDFormattedTextField kdtE1_originalValue_TextField = new KDFormattedTextField();
        kdtE1_originalValue_TextField.setName("kdtE1_originalValue_TextField");
        kdtE1_originalValue_TextField.setVisible(true);
        kdtE1_originalValue_TextField.setEditable(true);
        kdtE1_originalValue_TextField.setHorizontalAlignment(2);
        kdtE1_originalValue_TextField.setDataType(1);
        	kdtE1_originalValue_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtE1_originalValue_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtE1_originalValue_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtE1_originalValue_CellEditor = new KDTDefaultCellEditor(kdtE1_originalValue_TextField);
        this.kdtE1.getColumn("originalValue").setEditor(kdtE1_originalValue_CellEditor);
        KDFormattedTextField kdtE1_presentValue_TextField = new KDFormattedTextField();
        kdtE1_presentValue_TextField.setName("kdtE1_presentValue_TextField");
        kdtE1_presentValue_TextField.setVisible(true);
        kdtE1_presentValue_TextField.setEditable(true);
        kdtE1_presentValue_TextField.setHorizontalAlignment(2);
        kdtE1_presentValue_TextField.setDataType(1);
        	kdtE1_presentValue_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtE1_presentValue_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtE1_presentValue_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtE1_presentValue_CellEditor = new KDTDefaultCellEditor(kdtE1_presentValue_TextField);
        this.kdtE1.getColumn("presentValue").setEditor(kdtE1_presentValue_CellEditor);
        KDFormattedTextField kdtE1_insuranceAmount_TextField = new KDFormattedTextField();
        kdtE1_insuranceAmount_TextField.setName("kdtE1_insuranceAmount_TextField");
        kdtE1_insuranceAmount_TextField.setVisible(true);
        kdtE1_insuranceAmount_TextField.setEditable(true);
        kdtE1_insuranceAmount_TextField.setHorizontalAlignment(2);
        kdtE1_insuranceAmount_TextField.setDataType(1);
        	kdtE1_insuranceAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtE1_insuranceAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtE1_insuranceAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtE1_insuranceAmount_CellEditor = new KDTDefaultCellEditor(kdtE1_insuranceAmount_TextField);
        this.kdtE1.getColumn("insuranceAmount").setEditor(kdtE1_insuranceAmount_CellEditor);
        KDFormattedTextField kdtE1_premium_TextField = new KDFormattedTextField();
        kdtE1_premium_TextField.setName("kdtE1_premium_TextField");
        kdtE1_premium_TextField.setVisible(true);
        kdtE1_premium_TextField.setEditable(true);
        kdtE1_premium_TextField.setHorizontalAlignment(2);
        kdtE1_premium_TextField.setDataType(1);
        	kdtE1_premium_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtE1_premium_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtE1_premium_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtE1_premium_CellEditor = new KDTDefaultCellEditor(kdtE1_premium_TextField);
        this.kdtE1.getColumn("premium").setEditor(kdtE1_premium_CellEditor);
        KDTextField kdtE1_remark_TextField = new KDTextField();
        kdtE1_remark_TextField.setName("kdtE1_remark_TextField");
        kdtE1_remark_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_remark_CellEditor = new KDTDefaultCellEditor(kdtE1_remark_TextField);
        this.kdtE1.getColumn("remark").setEditor(kdtE1_remark_CellEditor);
        // scrollPanexianzhongID
        // txtxianzhongID		
        this.txtxianzhongID.setRequired(false);		
        this.txtxianzhongID.setMaxLength(500);
        // txtcoverNumber		
        this.txtcoverNumber.setHorizontalAlignment(2);		
        this.txtcoverNumber.setMaxLength(100);		
        this.txtcoverNumber.setRequired(false);
        // txtcontNumber		
        this.txtcontNumber.setHorizontalAlignment(2);		
        this.txtcontNumber.setMaxLength(100);		
        this.txtcontNumber.setRequired(false);
        // pkeffectDate		
        this.pkeffectDate.setRequired(false);
        // pkendDate		
        this.pkendDate.setRequired(false);
        // txtyear		
        this.txtyear.setVisible(true);		
        this.txtyear.setHorizontalAlignment(2);		
        this.txtyear.setMaxLength(100);		
        this.txtyear.setRequired(false);
        // prmttbrmc		
        this.prmttbrmc.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.prmttbrmc.setVisible(true);		
        this.prmttbrmc.setEditable(true);		
        this.prmttbrmc.setDisplayFormat("$name$");		
        this.prmttbrmc.setEditFormat("$number$");		
        this.prmttbrmc.setCommitFormat("$number$");		
        this.prmttbrmc.setRequired(false);
        // txtxzdm		
        this.txtxzdm.setVisible(true);		
        this.txtxzdm.setHorizontalAlignment(2);		
        this.txtxzdm.setMaxLength(100);		
        this.txtxzdm.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtxianzhongID,prmtinsurance,prmtinsuranceCompany,prmtCU,pkLastUpdateTime,prmtLastUpdateUser,pkCreateTime,kdtE1,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,pkAuditTime,comboBizStatus,comboStatus,txtcoverNumber,txtcontNumber,pkeffectDate,pkendDate,txtyear,prmttbrmc,txtxzdm}));
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
        this.setBounds(new Rectangle(10, 10, 1013, 509));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 509));
        contCreator.setBounds(new Rectangle(10, 418, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 418, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(10, 442, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(10, 442, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(371, 418, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(371, 418, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(371, 442, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(371, 442, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(736, 10, 270, 19));
        this.add(contCU, new KDLayout.Constraints(736, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(373, 76, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(373, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(373, 10, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(373, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(10, 466, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(10, 466, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(732, 418, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(732, 418, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(736, 32, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(736, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(371, 466, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(371, 466, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(732, 442, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(732, 442, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        continsurance.setBounds(new Rectangle(373, 32, 270, 19));
        this.add(continsurance, new KDLayout.Constraints(373, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continsuranceCompany.setBounds(new Rectangle(10, 76, 270, 19));
        this.add(continsuranceCompany, new KDLayout.Constraints(10, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDInsuranceDetail.setBounds(new Rectangle(663, 477, 197, 21));
        this.add(kDInsuranceDetail, new KDLayout.Constraints(663, 477, 197, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(11, 122, 992, 289));
        this.add(kDContainer1, new KDLayout.Constraints(11, 122, 992, 289, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnExportExcel.setBounds(new Rectangle(887, 474, 101, 21));
        this.add(btnExportExcel, new KDLayout.Constraints(887, 474, 101, 21, 0));
        btnImportExcel.setBounds(new Rectangle(672, 446, 22, 19));
        this.add(btnImportExcel, new KDLayout.Constraints(672, 446, 22, 19, 0));
        btnExcel.setBounds(new Rectangle(682, 425, 22, 19));
        this.add(btnExcel, new KDLayout.Constraints(682, 425, 22, 19, 0));
        contxianzhongID.setBounds(new Rectangle(286, 482, 270, 19));
        this.add(contxianzhongID, new KDLayout.Constraints(286, 482, 270, 19, 0));
        contcoverNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contcoverNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontNumber.setBounds(new Rectangle(10, 54, 270, 19));
        this.add(contcontNumber, new KDLayout.Constraints(10, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conteffectDate.setBounds(new Rectangle(373, 54, 270, 19));
        this.add(conteffectDate, new KDLayout.Constraints(373, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contendDate.setBounds(new Rectangle(736, 54, 270, 19));
        this.add(contendDate, new KDLayout.Constraints(736, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contyear.setBounds(new Rectangle(10, 32, 270, 19));
        this.add(contyear, new KDLayout.Constraints(10, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttbrmc.setBounds(new Rectangle(736, 76, 270, 19));
        this.add(conttbrmc, new KDLayout.Constraints(736, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contxzdm.setBounds(new Rectangle(10, 99, 270, 19));
        this.add(contxzdm, new KDLayout.Constraints(10, 99, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
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
        //continsurance
        continsurance.setBoundEditor(prmtinsurance);
        //continsuranceCompany
        continsuranceCompany.setBoundEditor(prmtinsuranceCompany);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kdtE1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE1,new com.kingdee.eas.port.equipment.insurance.InsuranceCoverageE1Info(),null,false);
        kDContainer1.getContentPane().add(kdtE1_detailPanel, BorderLayout.CENTER);
        //contxianzhongID
        contxianzhongID.setBoundEditor(scrollPanexianzhongID);
        //scrollPanexianzhongID
        scrollPanexianzhongID.getViewport().add(txtxianzhongID, null);
        //contcoverNumber
        contcoverNumber.setBoundEditor(txtcoverNumber);
        //contcontNumber
        contcontNumber.setBoundEditor(txtcontNumber);
        //conteffectDate
        conteffectDate.setBoundEditor(pkeffectDate);
        //contendDate
        contendDate.setBoundEditor(pkendDate);
        //contyear
        contyear.setBoundEditor(txtyear);
        //conttbrmc
        conttbrmc.setBoundEditor(prmttbrmc);
        //contxzdm
        contxzdm.setBoundEditor(txtxzdm);

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
		dataBinder.registerBinding("insurance", com.kingdee.eas.port.equipment.base.InsuranceInfo.class, this.prmtinsurance, "data");
		dataBinder.registerBinding("insuranceCompany", com.kingdee.eas.port.equipment.base.InsuranceCompanyInfo.class, this.prmtinsuranceCompany, "data");
		dataBinder.registerBinding("E1.seq", int.class, this.kdtE1, "seq.text");
		dataBinder.registerBinding("E1", com.kingdee.eas.port.equipment.insurance.InsuranceCoverageE1Info.class, this.kdtE1, "userObject");
		dataBinder.registerBinding("E1.useUnit", java.lang.Object.class, this.kdtE1, "useUnit.text");
		dataBinder.registerBinding("E1.equNumber", java.lang.Object.class, this.kdtE1, "equNumber.text");
		dataBinder.registerBinding("E1.equType", String.class, this.kdtE1, "equType.text");
		dataBinder.registerBinding("E1.equName", String.class, this.kdtE1, "equName.text");
		dataBinder.registerBinding("E1.specModel", String.class, this.kdtE1, "specModel.text");
		dataBinder.registerBinding("E1.factoryUseDate", java.util.Date.class, this.kdtE1, "factoryUseDate.text");
		dataBinder.registerBinding("E1.makeUnit", String.class, this.kdtE1, "makeUnit.text");
		dataBinder.registerBinding("E1.tonnage", String.class, this.kdtE1, "tonnage.text");
		dataBinder.registerBinding("E1.originalValue", java.math.BigDecimal.class, this.kdtE1, "originalValue.text");
		dataBinder.registerBinding("E1.presentValue", java.math.BigDecimal.class, this.kdtE1, "presentValue.text");
		dataBinder.registerBinding("E1.insuranceAmount", java.math.BigDecimal.class, this.kdtE1, "insuranceAmount.text");
		dataBinder.registerBinding("E1.remark", String.class, this.kdtE1, "remark.text");
		dataBinder.registerBinding("E1.premium", java.math.BigDecimal.class, this.kdtE1, "premium.text");
		dataBinder.registerBinding("xianzhongID", String.class, this.txtxianzhongID, "text");
		dataBinder.registerBinding("coverNumber", String.class, this.txtcoverNumber, "text");
		dataBinder.registerBinding("contNumber", String.class, this.txtcontNumber, "text");
		dataBinder.registerBinding("effectDate", java.util.Date.class, this.pkeffectDate, "value");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.pkendDate, "value");
		dataBinder.registerBinding("year", String.class, this.txtyear, "text");
		dataBinder.registerBinding("tbrmc", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmttbrmc, "data");
		dataBinder.registerBinding("xzdm", String.class, this.txtxzdm, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.insurance.app.InsuranceCoverageEditUIHandler";
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
        this.txtxianzhongID.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo)ov;
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
		getValidateHelper().registerBindProperty("insurance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("insuranceCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.useUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.equNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.equType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.equName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.specModel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.factoryUseDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.makeUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.tonnage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.originalValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.presentValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.insuranceAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.premium", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("xianzhongID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("coverNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("effectDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("year", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tbrmc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("xzdm", ValidateHelper.ON_SAVE);    		
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
     * output btnExportExcel_actionPerformed method
     */
    protected void btnExportExcel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code hereq
    }

    /**
     * output btnImportExcel_actionPerformed method
     */
    protected void btnImportExcel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here111
    }

    /**
     * output btnExcel_actionPerformed method
     */
    protected void btnExcel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here12
    }


    /**
     * output kdtE1_Changed(int rowIndex,int colIndex) method
     */
    public void kdtE1_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("equNumber".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"equName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"equNumber").getValue(),"name")));

}

    if ("equNumber".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"specModel").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"equNumber").getValue(),"model")));

}

    if ("equNumber".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"factoryUseDate").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getDateValue(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"equNumber").getValue(),"qyDate")));

}

    if ("equNumber".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"makeUnit").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"equNumber").getValue(),"mader")));

}

    if ("equNumber".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"tonnage").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"equNumber").getValue(),"weight")));

}

    if ("equNumber".equalsIgnoreCase(kdtE1.getColumn(colIndex).getKey())) {
kdtE1.getCell(rowIndex,"equType").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE1.getCell(rowIndex,"equNumber").getValue(),"eqmType.name")));

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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("insurance.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("insurance.id"));
        	sic.add(new SelectorItemInfo("insurance.number"));
        	sic.add(new SelectorItemInfo("insurance.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("insuranceCompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("insuranceCompany.id"));
        	sic.add(new SelectorItemInfo("insuranceCompany.number"));
        	sic.add(new SelectorItemInfo("insuranceCompany.name"));
		}
    	sic.add(new SelectorItemInfo("E1.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.useUnit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.useUnit.id"));
			sic.add(new SelectorItemInfo("E1.useUnit.name"));
        	sic.add(new SelectorItemInfo("E1.useUnit.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.equNumber.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.equNumber.id"));
			sic.add(new SelectorItemInfo("E1.equNumber.number"));
			sic.add(new SelectorItemInfo("E1.equNumber.name"));
		}
    	sic.add(new SelectorItemInfo("E1.equType"));
    	sic.add(new SelectorItemInfo("E1.equName"));
    	sic.add(new SelectorItemInfo("E1.specModel"));
    	sic.add(new SelectorItemInfo("E1.factoryUseDate"));
    	sic.add(new SelectorItemInfo("E1.makeUnit"));
    	sic.add(new SelectorItemInfo("E1.tonnage"));
    	sic.add(new SelectorItemInfo("E1.originalValue"));
    	sic.add(new SelectorItemInfo("E1.presentValue"));
    	sic.add(new SelectorItemInfo("E1.insuranceAmount"));
    	sic.add(new SelectorItemInfo("E1.remark"));
    	sic.add(new SelectorItemInfo("E1.premium"));
        sic.add(new SelectorItemInfo("xianzhongID"));
        sic.add(new SelectorItemInfo("coverNumber"));
        sic.add(new SelectorItemInfo("contNumber"));
        sic.add(new SelectorItemInfo("effectDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("year"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("tbrmc.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("tbrmc.id"));
        	sic.add(new SelectorItemInfo("tbrmc.number"));
        	sic.add(new SelectorItemInfo("tbrmc.name"));
		}
        sic.add(new SelectorItemInfo("xzdm"));
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
     * output actionExcelBxmx_actionPerformed method
     */
    public void actionExcelBxmx_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.insurance.InsuranceCoverageFactory.getRemoteInstance().excelBxmx(editData);
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
	public RequestContext prepareActionExcelBxmx(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExcelBxmx() {
    	return false;
    }

    /**
     * output ActionExcelBxmx class
     */     
    protected class ActionExcelBxmx extends ItemAction {     
    
        public ActionExcelBxmx()
        {
            this(null);
        }

        public ActionExcelBxmx(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExcelBxmx.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelBxmx.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelBxmx.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInsuranceCoverageEditUI.this, "ActionExcelBxmx", "actionExcelBxmx_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.insurance.client", "InsuranceCoverageEditUI");
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
        return com.kingdee.eas.port.equipment.insurance.client.InsuranceCoverageEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.insurance.InsuranceCoverageFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo objectValue = new com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/insurance/InsuranceCoverage";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.insurance.app.InsuranceCoverageQuery");
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