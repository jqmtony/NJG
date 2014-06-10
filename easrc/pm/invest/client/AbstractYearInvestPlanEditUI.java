/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

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
public abstract class AbstractYearInvestPlanEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractYearInvestPlanEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contremark;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel5;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbuildType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contportProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfundSource;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contplanStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contplanEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrequestPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrequestOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaddInvestAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchancedAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0027;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contplanType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contobjectState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcostTemp;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continvestAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtbuildType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtprojectName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtportProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtfundSource;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkplanStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkplanEndDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrequestPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrequestOrg;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprojectType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtaddInvestAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtamount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtchancedAmount;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneBIMUDF0027;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtBIMUDF0027;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtaddress;
    protected com.kingdee.bos.ctrl.swing.KDComboBox planType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtyear;
    protected com.kingdee.bos.ctrl.swing.KDComboBox objectState;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcostTemp;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtinvestAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contanalyse;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneanalyse;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtanalyse;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contscheme;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanescheme;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtscheme;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE2;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE2_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE3;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE3_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtremark;
    protected com.kingdee.eas.port.pm.invest.YearInvestPlanInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractYearInvestPlanEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractYearInvestPlanEditUI.class.getName());
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
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contremark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel5 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbuildType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contportProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfundSource = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contplanStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contplanEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrequestPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrequestOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaddInvestAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchancedAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBIMUDF0027 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contplanType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contobjectState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contcostTemp = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continvestAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtbuildType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtprojectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtportProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtfundSource = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkplanStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkplanEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtrequestPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtrequestOrg = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtprojectType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtaddInvestAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtamount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtchancedAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.scrollPaneBIMUDF0027 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtBIMUDF0027 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtaddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.planType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtyear = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.objectState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtcostTemp = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtinvestAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contanalyse = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPaneanalyse = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtanalyse = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contscheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPanescheme = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtscheme = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kdtE2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtE3 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtremark = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contBizStatus.setName("contBizStatus");
        this.contAuditTime.setName("contAuditTime");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contremark.setName("contremark");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.comboBizStatus.setName("comboBizStatus");
        this.pkAuditTime.setName("pkAuditTime");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel5.setName("kDPanel5");
        this.kDPanel4.setName("kDPanel4");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contbuildType.setName("contbuildType");
        this.contprojectName.setName("contprojectName");
        this.contportProject.setName("contportProject");
        this.contfundSource.setName("contfundSource");
        this.contplanStartDate.setName("contplanStartDate");
        this.contplanEndDate.setName("contplanEndDate");
        this.contrequestPerson.setName("contrequestPerson");
        this.contrequestOrg.setName("contrequestOrg");
        this.contprojectType.setName("contprojectType");
        this.contaddInvestAmount.setName("contaddInvestAmount");
        this.contamount.setName("contamount");
        this.contchancedAmount.setName("contchancedAmount");
        this.contBIMUDF0027.setName("contBIMUDF0027");
        this.contaddress.setName("contaddress");
        this.contplanType.setName("contplanType");
        this.contyear.setName("contyear");
        this.contobjectState.setName("contobjectState");
        this.contCU.setName("contCU");
        this.kDContainer1.setName("kDContainer1");
        this.contcostTemp.setName("contcostTemp");
        this.continvestAmount.setName("continvestAmount");
        this.contStatus.setName("contStatus");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.prmtbuildType.setName("prmtbuildType");
        this.txtprojectName.setName("txtprojectName");
        this.prmtportProject.setName("prmtportProject");
        this.prmtfundSource.setName("prmtfundSource");
        this.pkplanStartDate.setName("pkplanStartDate");
        this.pkplanEndDate.setName("pkplanEndDate");
        this.prmtrequestPerson.setName("prmtrequestPerson");
        this.prmtrequestOrg.setName("prmtrequestOrg");
        this.prmtprojectType.setName("prmtprojectType");
        this.txtaddInvestAmount.setName("txtaddInvestAmount");
        this.txtamount.setName("txtamount");
        this.txtchancedAmount.setName("txtchancedAmount");
        this.scrollPaneBIMUDF0027.setName("scrollPaneBIMUDF0027");
        this.txtBIMUDF0027.setName("txtBIMUDF0027");
        this.txtaddress.setName("txtaddress");
        this.planType.setName("planType");
        this.txtyear.setName("txtyear");
        this.objectState.setName("objectState");
        this.prmtCU.setName("prmtCU");
        this.kdtEntry.setName("kdtEntry");
        this.prmtcostTemp.setName("prmtcostTemp");
        this.txtinvestAmount.setName("txtinvestAmount");
        this.comboStatus.setName("comboStatus");
        this.contanalyse.setName("contanalyse");
        this.scrollPaneanalyse.setName("scrollPaneanalyse");
        this.txtanalyse.setName("txtanalyse");
        this.contscheme.setName("contscheme");
        this.scrollPanescheme.setName("scrollPanescheme");
        this.txtscheme.setName("txtscheme");
        this.kdtE2.setName("kdtE2");
        this.kdtE3.setName("kdtE3");
        this.txtremark.setName("txtremark");
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
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contBizStatus		
        this.contBizStatus.setBoundLabelText(resHelper.getString("contBizStatus.boundLabelText"));		
        this.contBizStatus.setBoundLabelLength(100);		
        this.contBizStatus.setBoundLabelUnderline(true);		
        this.contBizStatus.setEnabled(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // kDTabbedPane1
        // contremark		
        this.contremark.setBoundLabelText(resHelper.getString("contremark.boundLabelText"));		
        this.contremark.setBoundLabelLength(100);		
        this.contremark.setBoundLabelUnderline(true);		
        this.contremark.setVisible(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setCommitFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setRequired(false);
        // pkCreateTime		
        this.pkCreateTime.setTimeEnabled(true);		
        this.pkCreateTime.setEnabled(false);		
        this.pkCreateTime.setRequired(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);		
        this.prmtLastUpdateUser.setDisplayFormat("$name$");		
        this.prmtLastUpdateUser.setEditFormat("$name$");		
        this.prmtLastUpdateUser.setCommitFormat("$name$");		
        this.prmtLastUpdateUser.setRequired(false);
        // pkLastUpdateTime		
        this.pkLastUpdateTime.setTimeEnabled(true);		
        this.pkLastUpdateTime.setEnabled(false);		
        this.pkLastUpdateTime.setRequired(false);
        // txtDescription
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");		
        this.prmtAuditor.setRequired(false);
        // comboBizStatus		
        this.comboBizStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBizActionEnum").toArray());		
        this.comboBizStatus.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);		
        this.pkAuditTime.setRequired(false);
        // kDPanel1
        // kDPanel2
        // kDPanel3
        // kDPanel5
        // kDPanel4
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contbuildType		
        this.contbuildType.setBoundLabelText(resHelper.getString("contbuildType.boundLabelText"));		
        this.contbuildType.setBoundLabelLength(100);		
        this.contbuildType.setBoundLabelUnderline(true);		
        this.contbuildType.setVisible(true);
        // contprojectName		
        this.contprojectName.setBoundLabelText(resHelper.getString("contprojectName.boundLabelText"));		
        this.contprojectName.setBoundLabelLength(100);		
        this.contprojectName.setBoundLabelUnderline(true);		
        this.contprojectName.setVisible(true);
        // contportProject		
        this.contportProject.setBoundLabelText(resHelper.getString("contportProject.boundLabelText"));		
        this.contportProject.setBoundLabelLength(100);		
        this.contportProject.setBoundLabelUnderline(true);		
        this.contportProject.setVisible(true);
        // contfundSource		
        this.contfundSource.setBoundLabelText(resHelper.getString("contfundSource.boundLabelText"));		
        this.contfundSource.setBoundLabelLength(100);		
        this.contfundSource.setBoundLabelUnderline(true);		
        this.contfundSource.setVisible(true);
        // contplanStartDate		
        this.contplanStartDate.setBoundLabelText(resHelper.getString("contplanStartDate.boundLabelText"));		
        this.contplanStartDate.setBoundLabelLength(100);		
        this.contplanStartDate.setBoundLabelUnderline(true);		
        this.contplanStartDate.setVisible(true);
        // contplanEndDate		
        this.contplanEndDate.setBoundLabelText(resHelper.getString("contplanEndDate.boundLabelText"));		
        this.contplanEndDate.setBoundLabelLength(100);		
        this.contplanEndDate.setBoundLabelUnderline(true);		
        this.contplanEndDate.setVisible(true);
        // contrequestPerson		
        this.contrequestPerson.setBoundLabelText(resHelper.getString("contrequestPerson.boundLabelText"));		
        this.contrequestPerson.setBoundLabelLength(100);		
        this.contrequestPerson.setBoundLabelUnderline(true);		
        this.contrequestPerson.setVisible(true);
        // contrequestOrg		
        this.contrequestOrg.setBoundLabelText(resHelper.getString("contrequestOrg.boundLabelText"));		
        this.contrequestOrg.setBoundLabelLength(100);		
        this.contrequestOrg.setBoundLabelUnderline(true);		
        this.contrequestOrg.setVisible(true);
        // contprojectType		
        this.contprojectType.setBoundLabelText(resHelper.getString("contprojectType.boundLabelText"));		
        this.contprojectType.setBoundLabelLength(100);		
        this.contprojectType.setBoundLabelUnderline(true);		
        this.contprojectType.setVisible(true);
        // contaddInvestAmount		
        this.contaddInvestAmount.setBoundLabelText(resHelper.getString("contaddInvestAmount.boundLabelText"));		
        this.contaddInvestAmount.setBoundLabelLength(140);		
        this.contaddInvestAmount.setBoundLabelUnderline(true);		
        this.contaddInvestAmount.setVisible(true);
        // contamount		
        this.contamount.setBoundLabelText(resHelper.getString("contamount.boundLabelText"));		
        this.contamount.setBoundLabelLength(100);		
        this.contamount.setBoundLabelUnderline(true);		
        this.contamount.setVisible(true);
        // contchancedAmount		
        this.contchancedAmount.setBoundLabelText(resHelper.getString("contchancedAmount.boundLabelText"));		
        this.contchancedAmount.setBoundLabelLength(100);		
        this.contchancedAmount.setBoundLabelUnderline(true);		
        this.contchancedAmount.setVisible(true);
        // contBIMUDF0027		
        this.contBIMUDF0027.setBoundLabelText(resHelper.getString("contBIMUDF0027.boundLabelText"));		
        this.contBIMUDF0027.setBoundLabelLength(16);		
        this.contBIMUDF0027.setBoundLabelUnderline(true);		
        this.contBIMUDF0027.setVisible(true);		
        this.contBIMUDF0027.setBoundLabelAlignment(8);
        // contaddress		
        this.contaddress.setBoundLabelText(resHelper.getString("contaddress.boundLabelText"));		
        this.contaddress.setBoundLabelLength(100);		
        this.contaddress.setBoundLabelUnderline(true);		
        this.contaddress.setVisible(true);
        // contplanType		
        this.contplanType.setBoundLabelText(resHelper.getString("contplanType.boundLabelText"));		
        this.contplanType.setBoundLabelLength(100);		
        this.contplanType.setBoundLabelUnderline(true);		
        this.contplanType.setVisible(true);
        // contyear		
        this.contyear.setBoundLabelText(resHelper.getString("contyear.boundLabelText"));		
        this.contyear.setBoundLabelLength(100);		
        this.contyear.setBoundLabelUnderline(true);		
        this.contyear.setVisible(true);
        // contobjectState		
        this.contobjectState.setBoundLabelText(resHelper.getString("contobjectState.boundLabelText"));		
        this.contobjectState.setBoundLabelLength(100);		
        this.contobjectState.setBoundLabelUnderline(true);		
        this.contobjectState.setVisible(true);
        // contCU		
        this.contCU.setBoundLabelText(resHelper.getString("contCU.boundLabelText"));		
        this.contCU.setBoundLabelLength(100);		
        this.contCU.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);
        // contcostTemp		
        this.contcostTemp.setBoundLabelText(resHelper.getString("contcostTemp.boundLabelText"));		
        this.contcostTemp.setBoundLabelLength(100);		
        this.contcostTemp.setBoundLabelUnderline(true);		
        this.contcostTemp.setVisible(true);
        // continvestAmount		
        this.continvestAmount.setBoundLabelText(resHelper.getString("continvestAmount.boundLabelText"));		
        this.continvestAmount.setBoundLabelLength(100);		
        this.continvestAmount.setBoundLabelUnderline(true);		
        this.continvestAmount.setVisible(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setEditable(false);
        // pkBizDate		
        this.pkBizDate.setRequired(false);
        // prmtbuildType		
        this.prmtbuildType.setQueryInfo("com.kingdee.eas.port.pm.base.app.BuildTypeQuery");		
        this.prmtbuildType.setEditable(true);		
        this.prmtbuildType.setDisplayFormat("$name$");		
        this.prmtbuildType.setEditFormat("$number$");		
        this.prmtbuildType.setCommitFormat("$number$");		
        this.prmtbuildType.setRequired(true);
        this.prmtbuildType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtbuildType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtprojectName		
        this.txtprojectName.setHorizontalAlignment(2);		
        this.txtprojectName.setMaxLength(100);		
        this.txtprojectName.setRequired(true);
        // prmtportProject		
        this.prmtportProject.setQueryInfo("com.kingdee.eas.basedata.assistant.app.ProjectQuery");		
        this.prmtportProject.setEditable(true);		
        this.prmtportProject.setDisplayFormat("$name$");		
        this.prmtportProject.setEditFormat("$number$");		
        this.prmtportProject.setCommitFormat("$number$");		
        this.prmtportProject.setRequired(false);
        this.prmtportProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtportProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtfundSource		
        this.prmtfundSource.setQueryInfo("com.kingdee.eas.port.pm.base.app.FundSourceQuery");		
        this.prmtfundSource.setEditable(true);		
        this.prmtfundSource.setDisplayFormat("$name$");		
        this.prmtfundSource.setEditFormat("$number$");		
        this.prmtfundSource.setCommitFormat("$number$");		
        this.prmtfundSource.setRequired(true);
        // pkplanStartDate		
        this.pkplanStartDate.setRequired(false);
        // pkplanEndDate		
        this.pkplanEndDate.setRequired(false);
        // prmtrequestPerson		
        this.prmtrequestPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtrequestPerson.setEditable(true);		
        this.prmtrequestPerson.setDisplayFormat("$name$");		
        this.prmtrequestPerson.setEditFormat("$number$");		
        this.prmtrequestPerson.setCommitFormat("$number$");		
        this.prmtrequestPerson.setRequired(true);
        prmtrequestPerson.addDataChangeListener(new DataChangeListener() {
		public void dataChanged(DataChangeEvent e) {
			try {
				prmtrequestPerson_Changed();
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        this.prmtrequestPerson.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtrequestPerson_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtrequestOrg		
        this.prmtrequestOrg.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtrequestOrg.setDisplayFormat("$name$");		
        this.prmtrequestOrg.setEditFormat("$number$");		
        this.prmtrequestOrg.setCommitFormat("$number$");		
        this.prmtrequestOrg.setRequired(true);
        // prmtprojectType		
        this.prmtprojectType.setQueryInfo("com.kingdee.eas.port.pm.base.app.ProjectTypeQuery");		
        this.prmtprojectType.setDisplayFormat("$name$");		
        this.prmtprojectType.setEditFormat("$number$");		
        this.prmtprojectType.setCommitFormat("$number$");		
        this.prmtprojectType.setRequired(true);
        // txtaddInvestAmount		
        this.txtaddInvestAmount.setHorizontalAlignment(2);		
        this.txtaddInvestAmount.setDataType(1);		
        this.txtaddInvestAmount.setSupportedEmpty(true);		
        this.txtaddInvestAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtaddInvestAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtaddInvestAmount.setPrecision(2);		
        this.txtaddInvestAmount.setRequired(false);		
        this.txtaddInvestAmount.setEditable(false);		
        this.txtaddInvestAmount.setEnabled(false);
        // txtamount		
        this.txtamount.setHorizontalAlignment(2);		
        this.txtamount.setDataType(1);		
        this.txtamount.setSupportedEmpty(true);		
        this.txtamount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtamount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtamount.setPrecision(2);		
        this.txtamount.setRequired(true);		
        this.txtamount.setEditable(false);
        // txtchancedAmount		
        this.txtchancedAmount.setHorizontalAlignment(2);		
        this.txtchancedAmount.setDataType(1);		
        this.txtchancedAmount.setSupportedEmpty(true);		
        this.txtchancedAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtchancedAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtchancedAmount.setPrecision(2);		
        this.txtchancedAmount.setRequired(false);
        // scrollPaneBIMUDF0027
        // txtBIMUDF0027		
        this.txtBIMUDF0027.setRequired(false);		
        this.txtBIMUDF0027.setMaxLength(1000);
        // txtaddress		
        this.txtaddress.setHorizontalAlignment(2);		
        this.txtaddress.setMaxLength(200);		
        this.txtaddress.setRequired(false);
        // planType		
        this.planType.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.pm.base.coms.PlanTypeEnum").toArray());		
        this.planType.setRequired(true);
        // txtyear		
        this.txtyear.setHorizontalAlignment(2);		
        this.txtyear.setDataType(0);		
        this.txtyear.setSupportedEmpty(true);		
        this.txtyear.setRequired(true);		
        this.txtyear.setEditable(false);
        // objectState		
        this.objectState.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.pm.invest.ObjectStateEnum").toArray());		
        this.objectState.setRequired(true);		
        this.objectState.setEnabled(false);
        this.objectState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    objectState_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCU		
        this.prmtCU.setEnabled(false);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costName\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"estimate\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"yearInvestBudget\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"planStartT\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"acceptTime\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"description\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{costType}</t:Cell><t:Cell>$Resource{costName}</t:Cell><t:Cell>$Resource{estimate}</t:Cell><t:Cell>$Resource{yearInvestBudget}</t:Cell><t:Cell>$Resource{planStartT}</t:Cell><t:Cell>$Resource{acceptTime}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntry.putBindContents("editData",new String[] {"seq","costType","costName","estimate","yearInvestBudget","planStartT","acceptTime","description"});


        this.kdtEntry.checkParsed();
        KDFormattedTextField kdtEntry_estimate_TextField = new KDFormattedTextField();
        kdtEntry_estimate_TextField.setName("kdtEntry_estimate_TextField");
        kdtEntry_estimate_TextField.setVisible(true);
        kdtEntry_estimate_TextField.setEditable(true);
        kdtEntry_estimate_TextField.setHorizontalAlignment(2);
        kdtEntry_estimate_TextField.setDataType(1);
        	kdtEntry_estimate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry_estimate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry_estimate_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_estimate_CellEditor = new KDTDefaultCellEditor(kdtEntry_estimate_TextField);
        this.kdtEntry.getColumn("estimate").setEditor(kdtEntry_estimate_CellEditor);
        KDFormattedTextField kdtEntry_yearInvestBudget_TextField = new KDFormattedTextField();
        kdtEntry_yearInvestBudget_TextField.setName("kdtEntry_yearInvestBudget_TextField");
        kdtEntry_yearInvestBudget_TextField.setVisible(true);
        kdtEntry_yearInvestBudget_TextField.setEditable(true);
        kdtEntry_yearInvestBudget_TextField.setHorizontalAlignment(2);
        kdtEntry_yearInvestBudget_TextField.setDataType(1);
        	kdtEntry_yearInvestBudget_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry_yearInvestBudget_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry_yearInvestBudget_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_yearInvestBudget_CellEditor = new KDTDefaultCellEditor(kdtEntry_yearInvestBudget_TextField);
        this.kdtEntry.getColumn("yearInvestBudget").setEditor(kdtEntry_yearInvestBudget_CellEditor);
        KDDatePicker kdtEntry_planStartT_DatePicker = new KDDatePicker();
        kdtEntry_planStartT_DatePicker.setName("kdtEntry_planStartT_DatePicker");
        kdtEntry_planStartT_DatePicker.setVisible(true);
        kdtEntry_planStartT_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntry_planStartT_CellEditor = new KDTDefaultCellEditor(kdtEntry_planStartT_DatePicker);
        this.kdtEntry.getColumn("planStartT").setEditor(kdtEntry_planStartT_CellEditor);
        KDDatePicker kdtEntry_acceptTime_DatePicker = new KDDatePicker();
        kdtEntry_acceptTime_DatePicker.setName("kdtEntry_acceptTime_DatePicker");
        kdtEntry_acceptTime_DatePicker.setVisible(true);
        kdtEntry_acceptTime_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntry_acceptTime_CellEditor = new KDTDefaultCellEditor(kdtEntry_acceptTime_DatePicker);
        this.kdtEntry.getColumn("acceptTime").setEditor(kdtEntry_acceptTime_CellEditor);
        // prmtcostTemp		
        this.prmtcostTemp.setQueryInfo("com.kingdee.eas.port.pm.invest.app.CostTempQuery");		
        this.prmtcostTemp.setEditable(true);		
        this.prmtcostTemp.setDisplayFormat("$tempName$");		
        this.prmtcostTemp.setEditFormat("$number$");		
        this.prmtcostTemp.setCommitFormat("$number$");		
        this.prmtcostTemp.setRequired(false);
        this.prmtcostTemp.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtcostTemp_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtinvestAmount		
        this.txtinvestAmount.setHorizontalAlignment(2);		
        this.txtinvestAmount.setDataType(1);		
        this.txtinvestAmount.setSupportedEmpty(true);		
        this.txtinvestAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtinvestAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtinvestAmount.setPrecision(2);		
        this.txtinvestAmount.setRequired(false);		
        this.txtinvestAmount.setEditable(false);
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);
        // contanalyse		
        this.contanalyse.setBoundLabelText(resHelper.getString("contanalyse.boundLabelText"));		
        this.contanalyse.setBoundLabelLength(100);		
        this.contanalyse.setBoundLabelUnderline(true);		
        this.contanalyse.setVisible(true);
        // scrollPaneanalyse
        // txtanalyse		
        this.txtanalyse.setRequired(false);		
        this.txtanalyse.setMaxLength(1000);
        // contscheme		
        this.contscheme.setBoundLabelText(resHelper.getString("contscheme.boundLabelText"));		
        this.contscheme.setBoundLabelLength(100);		
        this.contscheme.setBoundLabelUnderline(true);		
        this.contscheme.setVisible(true);
        // scrollPanescheme
        // txtscheme		
        this.txtscheme.setRequired(false);		
        this.txtscheme.setMaxLength(1000);
        // kdtE2
		String kdtE2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"apIndex\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"planComplete\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{apIndex}</t:Cell><t:Cell>$Resource{planComplete}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE2.setFormatXml(resHelper.translateString("kdtE2",kdtE2StrXML));

                this.kdtE2.putBindContents("editData",new String[] {"seq","apIndex","planComplete"});


        this.kdtE2.checkParsed();
        // kdtE3
		String kdtE3StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"reviewStage\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"accredConclusion\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"reviewTime\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{reviewStage}</t:Cell><t:Cell>$Resource{accredConclusion}</t:Cell><t:Cell>$Resource{reviewTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE3.setFormatXml(resHelper.translateString("kdtE3",kdtE3StrXML));

                this.kdtE3.putBindContents("editData",new String[] {"seq","reviewStage","accredConclusion","reviewTime"});


        this.kdtE3.checkParsed();
        KDComboBox kdtE3_reviewStage_ComboBox = new KDComboBox();
        kdtE3_reviewStage_ComboBox.setName("kdtE3_reviewStage_ComboBox");
        kdtE3_reviewStage_ComboBox.setVisible(true);
        kdtE3_reviewStage_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.pm.invest.AccredTypeEnum").toArray());
        KDTDefaultCellEditor kdtE3_reviewStage_CellEditor = new KDTDefaultCellEditor(kdtE3_reviewStage_ComboBox);
        this.kdtE3.getColumn("reviewStage").setEditor(kdtE3_reviewStage_CellEditor);
        KDTextField kdtE3_accredConclusion_TextField = new KDTextField();
        kdtE3_accredConclusion_TextField.setName("kdtE3_accredConclusion_TextField");
        kdtE3_accredConclusion_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtE3_accredConclusion_CellEditor = new KDTDefaultCellEditor(kdtE3_accredConclusion_TextField);
        this.kdtE3.getColumn("accredConclusion").setEditor(kdtE3_accredConclusion_CellEditor);
        KDDatePicker kdtE3_reviewTime_DatePicker = new KDDatePicker();
        kdtE3_reviewTime_DatePicker.setName("kdtE3_reviewTime_DatePicker");
        kdtE3_reviewTime_DatePicker.setVisible(true);
        kdtE3_reviewTime_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtE3_reviewTime_CellEditor = new KDTDefaultCellEditor(kdtE3_reviewTime_DatePicker);
        this.kdtE3.getColumn("reviewTime").setEditor(kdtE3_reviewTime_CellEditor);
        // txtremark		
        this.txtremark.setHorizontalAlignment(2);		
        this.txtremark.setMaxLength(100);		
        this.txtremark.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtyear,txtaddress,txtBIMUDF0027,txtchancedAmount,txtamount,txtaddInvestAmount,prmtprojectType,prmtrequestOrg,prmtrequestPerson,pkplanEndDate,pkplanStartDate,prmtfundSource,prmtportProject,prmtCU,pkLastUpdateTime,prmtLastUpdateUser,pkCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,pkAuditTime,comboBizStatus,comboStatus,txtprojectName,prmtbuildType,txtscheme,txtanalyse,planType,objectState,prmtcostTemp,txtinvestAmount,txtremark,kdtEntry,kdtE2,kdtE3}));
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
        this.setBounds(new Rectangle(-26, 10, 1041, 653));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(-26, 10, 1041, 653));
        contCreator.setBounds(new Rectangle(8, 584, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(8, 584, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(8, 612, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(8, 612, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(378, 584, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(378, 584, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(378, 612, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(378, 612, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(8, 627, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(8, 627, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(748, 584, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(748, 584, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(285, 631, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(285, 631, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(748, 612, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(748, 612, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane1.setBounds(new Rectangle(10, 10, 986, 571));
        this.add(kDTabbedPane1, new KDLayout.Constraints(10, 10, 986, 571, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contremark.setBounds(new Rectangle(571, 633, 270, 19));
        this.add(contremark, new KDLayout.Constraints(571, 633, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contBizStatus
        contBizStatus.setBoundEditor(comboBizStatus);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane1.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
        kDTabbedPane1.add(kDPanel5, resHelper.getString("kDPanel5.constraints"));
        kDTabbedPane1.add(kDPanel4, resHelper.getString("kDPanel4.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 985, 538));        contNumber.setBounds(new Rectangle(13, 31, 270, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(13, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(13, 135, 270, 19));
        kDPanel1.add(contBizDate, new KDLayout.Constraints(13, 135, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbuildType.setBounds(new Rectangle(696, 135, 270, 19));
        kDPanel1.add(contbuildType, new KDLayout.Constraints(696, 135, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contprojectName.setBounds(new Rectangle(13, 5, 611, 19));
        kDPanel1.add(contprojectName, new KDLayout.Constraints(13, 5, 611, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contportProject.setBounds(new Rectangle(696, 163, 270, 19));
        kDPanel1.add(contportProject, new KDLayout.Constraints(696, 163, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contfundSource.setBounds(new Rectangle(354, 83, 270, 19));
        kDPanel1.add(contfundSource, new KDLayout.Constraints(354, 83, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contplanStartDate.setBounds(new Rectangle(13, 163, 270, 19));
        kDPanel1.add(contplanStartDate, new KDLayout.Constraints(13, 163, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contplanEndDate.setBounds(new Rectangle(354, 163, 270, 19));
        kDPanel1.add(contplanEndDate, new KDLayout.Constraints(354, 163, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrequestPerson.setBounds(new Rectangle(13, 57, 270, 19));
        kDPanel1.add(contrequestPerson, new KDLayout.Constraints(13, 57, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrequestOrg.setBounds(new Rectangle(354, 57, 270, 19));
        kDPanel1.add(contrequestOrg, new KDLayout.Constraints(354, 57, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contprojectType.setBounds(new Rectangle(696, 83, 270, 19));
        kDPanel1.add(contprojectType, new KDLayout.Constraints(696, 83, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contaddInvestAmount.setBounds(new Rectangle(354, 109, 270, 19));
        kDPanel1.add(contaddInvestAmount, new KDLayout.Constraints(354, 109, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contamount.setBounds(new Rectangle(13, 109, 270, 19));
        kDPanel1.add(contamount, new KDLayout.Constraints(13, 109, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contchancedAmount.setBounds(new Rectangle(696, 109, 270, 19));
        kDPanel1.add(contchancedAmount, new KDLayout.Constraints(696, 109, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBIMUDF0027.setBounds(new Rectangle(14, 215, 957, 63));
        kDPanel1.add(contBIMUDF0027, new KDLayout.Constraints(14, 215, 957, 63, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contaddress.setBounds(new Rectangle(13, 191, 611, 19));
        kDPanel1.add(contaddress, new KDLayout.Constraints(13, 191, 611, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contplanType.setBounds(new Rectangle(354, 31, 270, 19));
        kDPanel1.add(contplanType, new KDLayout.Constraints(354, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contyear.setBounds(new Rectangle(354, 135, 270, 19));
        kDPanel1.add(contyear, new KDLayout.Constraints(354, 135, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contobjectState.setBounds(new Rectangle(696, 31, 270, 19));
        kDPanel1.add(contobjectState, new KDLayout.Constraints(696, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCU.setBounds(new Rectangle(696, 57, 270, 19));
        kDPanel1.add(contCU, new KDLayout.Constraints(696, 57, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer1.setBounds(new Rectangle(13, 304, 957, 223));
        kDPanel1.add(kDContainer1, new KDLayout.Constraints(13, 304, 957, 223, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contcostTemp.setBounds(new Rectangle(13, 282, 270, 19));
        kDPanel1.add(contcostTemp, new KDLayout.Constraints(13, 282, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continvestAmount.setBounds(new Rectangle(13, 83, 270, 19));
        kDPanel1.add(continvestAmount, new KDLayout.Constraints(13, 83, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStatus.setBounds(new Rectangle(696, 5, 270, 19));
        kDPanel1.add(contStatus, new KDLayout.Constraints(696, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contbuildType
        contbuildType.setBoundEditor(prmtbuildType);
        //contprojectName
        contprojectName.setBoundEditor(txtprojectName);
        //contportProject
        contportProject.setBoundEditor(prmtportProject);
        //contfundSource
        contfundSource.setBoundEditor(prmtfundSource);
        //contplanStartDate
        contplanStartDate.setBoundEditor(pkplanStartDate);
        //contplanEndDate
        contplanEndDate.setBoundEditor(pkplanEndDate);
        //contrequestPerson
        contrequestPerson.setBoundEditor(prmtrequestPerson);
        //contrequestOrg
        contrequestOrg.setBoundEditor(prmtrequestOrg);
        //contprojectType
        contprojectType.setBoundEditor(prmtprojectType);
        //contaddInvestAmount
        contaddInvestAmount.setBoundEditor(txtaddInvestAmount);
        //contamount
        contamount.setBoundEditor(txtamount);
        //contchancedAmount
        contchancedAmount.setBoundEditor(txtchancedAmount);
        //contBIMUDF0027
        contBIMUDF0027.setBoundEditor(scrollPaneBIMUDF0027);
        //scrollPaneBIMUDF0027
        scrollPaneBIMUDF0027.getViewport().add(txtBIMUDF0027, null);
        //contaddress
        contaddress.setBoundEditor(txtaddress);
        //contplanType
        contplanType.setBoundEditor(planType);
        //contyear
        contyear.setBoundEditor(txtyear);
        //contobjectState
        contobjectState.setBoundEditor(objectState);
        //contCU
        contCU.setBoundEditor(prmtCU);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry,new com.kingdee.eas.port.pm.invest.YearInvestPlanEntryInfo(),null,false);
        kDContainer1.getContentPane().add(kdtEntry_detailPanel, BorderLayout.CENTER);
        //contcostTemp
        contcostTemp.setBoundEditor(prmtcostTemp);
        //continvestAmount
        continvestAmount.setBoundEditor(txtinvestAmount);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //kDPanel2
        kDPanel2.setLayout(null);        contanalyse.setBounds(new Rectangle(4, 6, 962, 385));
        kDPanel2.add(contanalyse, null);
        //contanalyse
        contanalyse.setBoundEditor(scrollPaneanalyse);
        //scrollPaneanalyse
        scrollPaneanalyse.getViewport().add(txtanalyse, null);
        //kDPanel3
        kDPanel3.setLayout(null);        contscheme.setBounds(new Rectangle(4, 4, 962, 393));
        kDPanel3.add(contscheme, null);
        //contscheme
        contscheme.setBoundEditor(scrollPanescheme);
        //scrollPanescheme
        scrollPanescheme.getViewport().add(txtscheme, null);
        //kDPanel5
        kDPanel5.setLayout(new KDLayout());
        kDPanel5.putClientProperty("OriginalBounds", new Rectangle(0, 0, 985, 538));        kdtE2.setBounds(new Rectangle(1, 2, 600, 465));
        kdtE2_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE2,new com.kingdee.eas.port.pm.invest.YearInvestPlanE2Info(),null,false);
        kDPanel5.add(kdtE2_detailPanel, new KDLayout.Constraints(1, 2, 600, 465, 0));
        //kDPanel4
        kDPanel4.setLayout(new KDLayout());
        kDPanel4.putClientProperty("OriginalBounds", new Rectangle(0, 0, 985, 538));        kdtE3.setBounds(new Rectangle(1, 0, 526, 274));
        kdtE3_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE3,new com.kingdee.eas.port.pm.invest.YearInvestPlanE3Info(),null,false);
        kDPanel4.add(kdtE3_detailPanel, new KDLayout.Constraints(1, 0, 526, 274, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
		kdtE3_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("reviewStage","1");
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        //contremark
        contremark.setBoundEditor(txtremark);

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
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("buildType", com.kingdee.eas.port.pm.base.BuildTypeInfo.class, this.prmtbuildType, "data");
		dataBinder.registerBinding("projectName", String.class, this.txtprojectName, "text");
		dataBinder.registerBinding("portProject", com.kingdee.eas.port.pm.project.PortProjectInfo.class, this.prmtportProject, "data");
		dataBinder.registerBinding("fundSource", com.kingdee.eas.port.pm.base.FundSourceInfo.class, this.prmtfundSource, "data");
		dataBinder.registerBinding("planStartDate", java.util.Date.class, this.pkplanStartDate, "value");
		dataBinder.registerBinding("planEndDate", java.util.Date.class, this.pkplanEndDate, "value");
		dataBinder.registerBinding("requestPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtrequestPerson, "data");
		dataBinder.registerBinding("requestOrg", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtrequestOrg, "data");
		dataBinder.registerBinding("projectType", com.kingdee.eas.port.pm.base.ProjectTypeInfo.class, this.prmtprojectType, "data");
		dataBinder.registerBinding("addInvestAmount", java.math.BigDecimal.class, this.txtaddInvestAmount, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtamount, "value");
		dataBinder.registerBinding("chancedAmount", java.math.BigDecimal.class, this.txtchancedAmount, "value");
		dataBinder.registerBinding("BIMUDF0027", String.class, this.txtBIMUDF0027, "text");
		dataBinder.registerBinding("address", String.class, this.txtaddress, "text");
		dataBinder.registerBinding("planType", com.kingdee.eas.port.pm.base.coms.PlanTypeEnum.class, this.planType, "selectedItem");
		dataBinder.registerBinding("year", int.class, this.txtyear, "value");
		dataBinder.registerBinding("objectState", com.kingdee.eas.port.pm.invest.ObjectStateEnum.class, this.objectState, "selectedItem");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("Entry.seq", int.class, this.kdtEntry, "seq.text");
		dataBinder.registerBinding("Entry", com.kingdee.eas.port.pm.invest.YearInvestPlanEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("Entry.costName", String.class, this.kdtEntry, "costName.text");
		dataBinder.registerBinding("Entry.estimate", java.math.BigDecimal.class, this.kdtEntry, "estimate.text");
		dataBinder.registerBinding("Entry.yearInvestBudget", java.math.BigDecimal.class, this.kdtEntry, "yearInvestBudget.text");
		dataBinder.registerBinding("Entry.planStartT", java.util.Date.class, this.kdtEntry, "planStartT.text");
		dataBinder.registerBinding("Entry.acceptTime", java.util.Date.class, this.kdtEntry, "acceptTime.text");
		dataBinder.registerBinding("Entry.description", String.class, this.kdtEntry, "description.text");
		dataBinder.registerBinding("Entry.costType", java.lang.Object.class, this.kdtEntry, "costType.text");
		dataBinder.registerBinding("costTemp", com.kingdee.eas.port.pm.invest.CostTempInfo.class, this.prmtcostTemp, "data");
		dataBinder.registerBinding("investAmount", java.math.BigDecimal.class, this.txtinvestAmount, "value");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("analyse", String.class, this.txtanalyse, "text");
		dataBinder.registerBinding("scheme", String.class, this.txtscheme, "text");
		dataBinder.registerBinding("E2.seq", int.class, this.kdtE2, "seq.text");
		dataBinder.registerBinding("E2", com.kingdee.eas.port.pm.invest.YearInvestPlanE2Info.class, this.kdtE2, "userObject");
		dataBinder.registerBinding("E2.apIndex", String.class, this.kdtE2, "apIndex.text");
		dataBinder.registerBinding("E2.planComplete", String.class, this.kdtE2, "planComplete.text");
		dataBinder.registerBinding("E3.seq", int.class, this.kdtE3, "seq.text");
		dataBinder.registerBinding("E3", com.kingdee.eas.port.pm.invest.YearInvestPlanE3Info.class, this.kdtE3, "userObject");
		dataBinder.registerBinding("E3.reviewTime", java.util.Date.class, this.kdtE3, "reviewTime.text");
		dataBinder.registerBinding("E3.reviewStage", com.kingdee.util.enums.Enum.class, this.kdtE3, "reviewStage.text");
		dataBinder.registerBinding("E3.accredConclusion", String.class, this.kdtE3, "accredConclusion.text");
		dataBinder.registerBinding("remark", String.class, this.txtremark, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invest.app.YearInvestPlanEditUIHandler";
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
        this.txtyear.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.pm.invest.YearInvestPlanInfo)ov;
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("portProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fundSource", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("requestPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("requestOrg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("addInvestAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chancedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BIMUDF0027", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("year", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("objectState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.costName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.estimate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.yearInvestBudget", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.planStartT", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.acceptTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.costType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costTemp", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("investAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("analyse", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scheme", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.apIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.planComplete", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.reviewTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.reviewStage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.accredConclusion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    		
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
     * output prmtbuildType_dataChanged method
     */
    protected void prmtbuildType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtportProject_dataChanged method
     */
    protected void prmtportProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtrequestPerson_dataChanged method
     */
    protected void prmtrequestPerson_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output objectState_itemStateChanged method
     */
    protected void objectState_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output prmtcostTemp_dataChanged method
     */
    protected void prmtcostTemp_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output prmtrequestPerson_Changed() method
     */
    public void prmtrequestPerson_Changed() throws Exception
    {
        System.out.println("prmtrequestPerson_Changed() Function is executed!");
            

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
        sic.add(new SelectorItemInfo("bizStatus"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("buildType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("buildType.id"));
        	sic.add(new SelectorItemInfo("buildType.number"));
        	sic.add(new SelectorItemInfo("buildType.name"));
		}
        sic.add(new SelectorItemInfo("projectName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("portProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("portProject.id"));
        	sic.add(new SelectorItemInfo("portProject.number"));
        	sic.add(new SelectorItemInfo("portProject.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("fundSource.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("fundSource.id"));
        	sic.add(new SelectorItemInfo("fundSource.number"));
        	sic.add(new SelectorItemInfo("fundSource.name"));
		}
        sic.add(new SelectorItemInfo("planStartDate"));
        sic.add(new SelectorItemInfo("planEndDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("requestPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("requestPerson.id"));
        	sic.add(new SelectorItemInfo("requestPerson.number"));
        	sic.add(new SelectorItemInfo("requestPerson.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("requestOrg.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("requestOrg.id"));
        	sic.add(new SelectorItemInfo("requestOrg.number"));
        	sic.add(new SelectorItemInfo("requestOrg.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("projectType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("projectType.id"));
        	sic.add(new SelectorItemInfo("projectType.number"));
        	sic.add(new SelectorItemInfo("projectType.name"));
		}
        sic.add(new SelectorItemInfo("addInvestAmount"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("chancedAmount"));
        sic.add(new SelectorItemInfo("BIMUDF0027"));
        sic.add(new SelectorItemInfo("address"));
        sic.add(new SelectorItemInfo("planType"));
        sic.add(new SelectorItemInfo("year"));
        sic.add(new SelectorItemInfo("objectState"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("CU.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("CU.id"));
        	sic.add(new SelectorItemInfo("CU.number"));
        	sic.add(new SelectorItemInfo("CU.name"));
		}
    	sic.add(new SelectorItemInfo("Entry.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("Entry.costName"));
    	sic.add(new SelectorItemInfo("Entry.estimate"));
    	sic.add(new SelectorItemInfo("Entry.yearInvestBudget"));
    	sic.add(new SelectorItemInfo("Entry.planStartT"));
    	sic.add(new SelectorItemInfo("Entry.acceptTime"));
    	sic.add(new SelectorItemInfo("Entry.description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.costType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Entry.costType.id"));
			sic.add(new SelectorItemInfo("Entry.costType.name"));
        	sic.add(new SelectorItemInfo("Entry.costType.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costTemp.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("costTemp.id"));
        	sic.add(new SelectorItemInfo("costTemp.number"));
        	sic.add(new SelectorItemInfo("costTemp.tempName"));
		}
        sic.add(new SelectorItemInfo("investAmount"));
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("analyse"));
        sic.add(new SelectorItemInfo("scheme"));
    	sic.add(new SelectorItemInfo("E2.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E2.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E2.apIndex"));
    	sic.add(new SelectorItemInfo("E2.planComplete"));
    	sic.add(new SelectorItemInfo("E3.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E3.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E3.reviewTime"));
    	sic.add(new SelectorItemInfo("E3.reviewStage"));
    	sic.add(new SelectorItemInfo("E3.accredConclusion"));
        sic.add(new SelectorItemInfo("remark"));
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
        return new MetaDataPK("com.kingdee.eas.port.pm.invest.client", "YearInvestPlanEditUI");
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
        return com.kingdee.eas.port.pm.invest.client.YearInvestPlanEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invest.YearInvestPlanFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invest.YearInvestPlanInfo objectValue = new com.kingdee.eas.port.pm.invest.YearInvestPlanInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/invest/YearInvestPlan";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.invest.app.YearInvestPlanQuery");
	}
    
        
					protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtNumber.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"单据编号"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtbuildType.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"建设性质"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtprojectName.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"项目名称"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtfundSource.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"资金来源"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtrequestPerson.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"申报人"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtrequestOrg.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"申报部门"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtprojectType.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"项目类型"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtamount.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"计划投资金额"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(planType.getSelectedItem())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"计划类型"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtyear.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"投资年度"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(objectState.getSelectedItem())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"项目状态"});
		}
			super.beforeStoreFields(arg0);
		}

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntry;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("planType","10");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}