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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcostTemp;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel6;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel5;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contplanStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contplanEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0027;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaddress;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcompanyProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contproject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkplanStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkplanEndDate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneBIMUDF0027;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtBIMUDF0027;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtaddress;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcompanyProperty;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtproject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contanalyse;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneanalyse;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtanalyse;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contscheme;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanescheme;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtscheme;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE2;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE2_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdesc;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanedesc;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtdesc;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE3;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtremark;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcostTemp;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contportProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continvestAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrequestPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contplanType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrequestOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfundSource;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaddInvestAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchancedAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbuildType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbalance;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contobjectState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contseq;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtyear;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtportProject;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtamount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtinvestAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrequestPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox planType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrequestOrg;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtfundSource;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtaddInvestAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtchancedAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtbuildType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtbalance;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprojectType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDComboBox objectState;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtseq;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtprojectName;
    protected com.kingdee.eas.port.pm.invest.YearInvestPlanInfo editData = null;
    protected EntityViewInfo queryYearPlanAuditInfoQuery = null;
    protected IMetaDataPK queryYearPlanAuditInfoQueryPK;
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
        queryYearPlanAuditInfoQueryPK = new MetaDataPK("com.kingdee.eas.port.pm.invest.app", "YearPlanAuditInfoQuery");
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
        this.contcostTemp = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel6 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel5 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contplanStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contplanEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBIMUDF0027 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contcompanyProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contproject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkplanStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkplanEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.scrollPaneBIMUDF0027 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtBIMUDF0027 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtaddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtcompanyProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtproject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contanalyse = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPaneanalyse = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtanalyse = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contscheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPanescheme = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtscheme = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kdtE2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contdesc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPanedesc = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtdesc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kdtE3 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtremark = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtcostTemp = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contyear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contportProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continvestAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrequestPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contplanType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrequestOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfundSource = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaddInvestAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchancedAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbuildType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbalance = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contobjectState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contseq = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtyear = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtportProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtamount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtinvestAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtrequestPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.planType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtrequestOrg = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtfundSource = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtaddInvestAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtchancedAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtbuildType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtbalance = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtprojectType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.objectState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtseq = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtprojectName = new com.kingdee.bos.ctrl.swing.KDTextField();
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
        this.contcostTemp.setName("contcostTemp");
        this.kDPanel6.setName("kDPanel6");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.comboBizStatus.setName("comboBizStatus");
        this.pkAuditTime.setName("pkAuditTime");
        this.kDPanel1.setName("kDPanel1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel5.setName("kDPanel5");
        this.kDPanel4.setName("kDPanel4");
        this.contplanStartDate.setName("contplanStartDate");
        this.contplanEndDate.setName("contplanEndDate");
        this.contBIMUDF0027.setName("contBIMUDF0027");
        this.contaddress.setName("contaddress");
        this.kDContainer1.setName("kDContainer1");
        this.contcompanyProperty.setName("contcompanyProperty");
        this.contStatus.setName("contStatus");
        this.contBizDate.setName("contBizDate");
        this.contproject.setName("contproject");
        this.pkplanStartDate.setName("pkplanStartDate");
        this.pkplanEndDate.setName("pkplanEndDate");
        this.scrollPaneBIMUDF0027.setName("scrollPaneBIMUDF0027");
        this.txtBIMUDF0027.setName("txtBIMUDF0027");
        this.txtaddress.setName("txtaddress");
        this.kdtEntry.setName("kdtEntry");
        this.prmtcompanyProperty.setName("prmtcompanyProperty");
        this.comboStatus.setName("comboStatus");
        this.pkBizDate.setName("pkBizDate");
        this.prmtproject.setName("prmtproject");
        this.contanalyse.setName("contanalyse");
        this.scrollPaneanalyse.setName("scrollPaneanalyse");
        this.txtanalyse.setName("txtanalyse");
        this.contscheme.setName("contscheme");
        this.scrollPanescheme.setName("scrollPanescheme");
        this.txtscheme.setName("txtscheme");
        this.kdtE2.setName("kdtE2");
        this.contdesc.setName("contdesc");
        this.scrollPanedesc.setName("scrollPanedesc");
        this.txtdesc.setName("txtdesc");
        this.kdtE3.setName("kdtE3");
        this.txtremark.setName("txtremark");
        this.prmtcostTemp.setName("prmtcostTemp");
        this.contyear.setName("contyear");
        this.contportProject.setName("contportProject");
        this.contamount.setName("contamount");
        this.continvestAmount.setName("continvestAmount");
        this.contrequestPerson.setName("contrequestPerson");
        this.contNumber.setName("contNumber");
        this.contplanType.setName("contplanType");
        this.contrequestOrg.setName("contrequestOrg");
        this.contfundSource.setName("contfundSource");
        this.contaddInvestAmount.setName("contaddInvestAmount");
        this.contchancedAmount.setName("contchancedAmount");
        this.contbuildType.setName("contbuildType");
        this.contbalance.setName("contbalance");
        this.contprojectType.setName("contprojectType");
        this.contCU.setName("contCU");
        this.contobjectState.setName("contobjectState");
        this.contseq.setName("contseq");
        this.contprojectName.setName("contprojectName");
        this.prmtyear.setName("prmtyear");
        this.prmtportProject.setName("prmtportProject");
        this.txtamount.setName("txtamount");
        this.txtinvestAmount.setName("txtinvestAmount");
        this.prmtrequestPerson.setName("prmtrequestPerson");
        this.txtNumber.setName("txtNumber");
        this.planType.setName("planType");
        this.prmtrequestOrg.setName("prmtrequestOrg");
        this.prmtfundSource.setName("prmtfundSource");
        this.txtaddInvestAmount.setName("txtaddInvestAmount");
        this.txtchancedAmount.setName("txtchancedAmount");
        this.prmtbuildType.setName("prmtbuildType");
        this.txtbalance.setName("txtbalance");
        this.prmtprojectType.setName("prmtprojectType");
        this.prmtCU.setName("prmtCU");
        this.objectState.setName("objectState");
        this.txtseq.setName("txtseq");
        this.txtprojectName.setName("txtprojectName");
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
        this.kDTabbedPane1.setToolTipText(resHelper.getString("kDTabbedPane1.toolTipText"));
        this.kDTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDTabbedPane1_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contremark		
        this.contremark.setBoundLabelText(resHelper.getString("contremark.boundLabelText"));		
        this.contremark.setBoundLabelLength(100);		
        this.contremark.setBoundLabelUnderline(true);		
        this.contremark.setVisible(false);
        // contcostTemp		
        this.contcostTemp.setBoundLabelText(resHelper.getString("contcostTemp.boundLabelText"));		
        this.contcostTemp.setBoundLabelLength(100);		
        this.contcostTemp.setBoundLabelUnderline(true);		
        this.contcostTemp.setVisible(true);
        // kDPanel6		
        this.kDPanel6.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),1));
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
        // kDScrollPane1
        // kDPanel2
        // kDPanel3
        // kDPanel5
        // kDPanel4
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
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);
        // contcompanyProperty		
        this.contcompanyProperty.setBoundLabelText(resHelper.getString("contcompanyProperty.boundLabelText"));		
        this.contcompanyProperty.setBoundLabelLength(100);		
        this.contcompanyProperty.setBoundLabelUnderline(true);		
        this.contcompanyProperty.setVisible(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contproject		
        this.contproject.setBoundLabelText(resHelper.getString("contproject.boundLabelText"));		
        this.contproject.setBoundLabelLength(100);		
        this.contproject.setBoundLabelUnderline(true);		
        this.contproject.setVisible(true);
        // pkplanStartDate		
        this.pkplanStartDate.setRequired(false);
        // pkplanEndDate		
        this.pkplanEndDate.setRequired(false);
        // scrollPaneBIMUDF0027
        // txtBIMUDF0027		
        this.txtBIMUDF0027.setRequired(false);		
        this.txtBIMUDF0027.setMaxLength(1000);
        // txtaddress		
        this.txtaddress.setHorizontalAlignment(2);		
        this.txtaddress.setMaxLength(200);		
        this.txtaddress.setRequired(false);
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
        // prmtcompanyProperty		
        this.prmtcompanyProperty.setQueryInfo("com.kingdee.eas.port.pm.base.app.CompanyPropertyQuery");		
        this.prmtcompanyProperty.setVisible(true);		
        this.prmtcompanyProperty.setEditable(true);		
        this.prmtcompanyProperty.setDisplayFormat("$name$");		
        this.prmtcompanyProperty.setEditFormat("$number$");		
        this.prmtcompanyProperty.setCommitFormat("$number$");		
        this.prmtcompanyProperty.setRequired(false);
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        // prmtproject		
        this.prmtproject.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7ProjectQuery");		
        this.prmtproject.setVisible(true);		
        this.prmtproject.setEditable(true);		
        this.prmtproject.setDisplayFormat("$name$- $number$");		
        this.prmtproject.setEditFormat("$number$");		
        this.prmtproject.setCommitFormat("$number$");		
        this.prmtproject.setRequired(false);
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
        // contdesc		
        this.contdesc.setBoundLabelText(resHelper.getString("contdesc.boundLabelText"));		
        this.contdesc.setBoundLabelLength(100);		
        this.contdesc.setBoundLabelUnderline(true);		
        this.contdesc.setVisible(true);
        // scrollPanedesc
        // txtdesc		
        this.txtdesc.setVisible(true);		
        this.txtdesc.setRequired(false);		
        this.txtdesc.setMaxLength(500);
        // kdtE3
		String kdtE3StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"accredType\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"accredDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"auditTime\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"E1.accredResu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"E1.projectConclude\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"accredPerson.name\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"accredDpart.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"E2.aduitTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"E2.accreConclu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"E2.opinion\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"E2.remark\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"projectName.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"projectName.projectName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{accredType}</t:Cell><t:Cell>$Resource{accredDate}</t:Cell><t:Cell>$Resource{auditTime}</t:Cell><t:Cell>$Resource{E1.accredResu}</t:Cell><t:Cell>$Resource{E1.projectConclude}</t:Cell><t:Cell>$Resource{accredPerson.name}</t:Cell><t:Cell>$Resource{accredDpart.name}</t:Cell><t:Cell>$Resource{E2.aduitTime}</t:Cell><t:Cell>$Resource{E2.accreConclu}</t:Cell><t:Cell>$Resource{E2.opinion}</t:Cell><t:Cell>$Resource{E2.remark}</t:Cell><t:Cell>$Resource{projectName.id}</t:Cell><t:Cell>$Resource{projectName.projectName}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE3.setFormatXml(resHelper.translateString("kdtE3",kdtE3StrXML));
                this.kdtE3.putBindContents("queryYearPlanAuditInfoQuery",new String[] {"accredType","accredDate","auditTime","E1.accredResu","E1.projectConclude","accredPerson.name","accredDpart.name","E2.aduitTime","E2.accreConclu","E2.opinion","E2.remark","projectName.id","projectName.projectName","id"});

        this.kdtE3.addRequestRowSetListener(new RequestRowSetListener() {
            public void doRequestRowSet(RequestRowSetEvent e) {
                kdtE3_doRequestRowSet(e);
            }
        });

        this.kdtE3.checkParsed();
        // txtremark		
        this.txtremark.setHorizontalAlignment(2);		
        this.txtremark.setMaxLength(100);		
        this.txtremark.setRequired(false);
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
        // contyear		
        this.contyear.setBoundLabelText(resHelper.getString("contyear.boundLabelText"));		
        this.contyear.setBoundLabelLength(100);		
        this.contyear.setBoundLabelUnderline(true);		
        this.contyear.setVisible(true);		
        this.contyear.setVerifyInputWhenFocusTarget(false);
        // contportProject		
        this.contportProject.setBoundLabelText(resHelper.getString("contportProject.boundLabelText"));		
        this.contportProject.setBoundLabelLength(100);		
        this.contportProject.setBoundLabelUnderline(true);		
        this.contportProject.setVisible(true);
        // contamount		
        this.contamount.setBoundLabelText(resHelper.getString("contamount.boundLabelText"));		
        this.contamount.setBoundLabelLength(100);		
        this.contamount.setBoundLabelUnderline(true);		
        this.contamount.setVisible(true);
        // continvestAmount		
        this.continvestAmount.setBoundLabelText(resHelper.getString("continvestAmount.boundLabelText"));		
        this.continvestAmount.setBoundLabelLength(100);		
        this.continvestAmount.setBoundLabelUnderline(true);		
        this.continvestAmount.setVisible(true);
        // contrequestPerson		
        this.contrequestPerson.setBoundLabelText(resHelper.getString("contrequestPerson.boundLabelText"));		
        this.contrequestPerson.setBoundLabelLength(100);		
        this.contrequestPerson.setBoundLabelUnderline(true);		
        this.contrequestPerson.setVisible(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contplanType		
        this.contplanType.setBoundLabelText(resHelper.getString("contplanType.boundLabelText"));		
        this.contplanType.setBoundLabelLength(100);		
        this.contplanType.setBoundLabelUnderline(true);		
        this.contplanType.setVisible(true);
        // contrequestOrg		
        this.contrequestOrg.setBoundLabelText(resHelper.getString("contrequestOrg.boundLabelText"));		
        this.contrequestOrg.setBoundLabelLength(100);		
        this.contrequestOrg.setBoundLabelUnderline(true);		
        this.contrequestOrg.setVisible(true);
        // contfundSource		
        this.contfundSource.setBoundLabelText(resHelper.getString("contfundSource.boundLabelText"));		
        this.contfundSource.setBoundLabelLength(100);		
        this.contfundSource.setBoundLabelUnderline(true);		
        this.contfundSource.setVisible(true);
        // contaddInvestAmount		
        this.contaddInvestAmount.setBoundLabelText(resHelper.getString("contaddInvestAmount.boundLabelText"));		
        this.contaddInvestAmount.setBoundLabelLength(140);		
        this.contaddInvestAmount.setBoundLabelUnderline(true);		
        this.contaddInvestAmount.setVisible(true);
        // contchancedAmount		
        this.contchancedAmount.setBoundLabelText(resHelper.getString("contchancedAmount.boundLabelText"));		
        this.contchancedAmount.setBoundLabelLength(100);		
        this.contchancedAmount.setBoundLabelUnderline(true);		
        this.contchancedAmount.setEnabled(false);
        // contbuildType		
        this.contbuildType.setBoundLabelText(resHelper.getString("contbuildType.boundLabelText"));		
        this.contbuildType.setBoundLabelLength(100);		
        this.contbuildType.setBoundLabelUnderline(true);		
        this.contbuildType.setVisible(true);
        // contbalance		
        this.contbalance.setBoundLabelText(resHelper.getString("contbalance.boundLabelText"));		
        this.contbalance.setBoundLabelLength(100);		
        this.contbalance.setBoundLabelUnderline(true);		
        this.contbalance.setVisible(true);
        // contprojectType		
        this.contprojectType.setBoundLabelText(resHelper.getString("contprojectType.boundLabelText"));		
        this.contprojectType.setBoundLabelLength(100);		
        this.contprojectType.setBoundLabelUnderline(true);		
        this.contprojectType.setVisible(true);
        // contCU		
        this.contCU.setBoundLabelText(resHelper.getString("contCU.boundLabelText"));		
        this.contCU.setBoundLabelLength(100);		
        this.contCU.setBoundLabelUnderline(true);
        // contobjectState		
        this.contobjectState.setBoundLabelText(resHelper.getString("contobjectState.boundLabelText"));		
        this.contobjectState.setBoundLabelLength(100);		
        this.contobjectState.setBoundLabelUnderline(true);		
        this.contobjectState.setVisible(true);
        // contseq		
        this.contseq.setBoundLabelText(resHelper.getString("contseq.boundLabelText"));		
        this.contseq.setBoundLabelLength(30);		
        this.contseq.setBoundLabelUnderline(true);		
        this.contseq.setVisible(true);		
        this.contseq.setForeground(new java.awt.Color(255,0,0));
        // contprojectName		
        this.contprojectName.setBoundLabelText(resHelper.getString("contprojectName.boundLabelText"));		
        this.contprojectName.setBoundLabelLength(100);		
        this.contprojectName.setBoundLabelUnderline(true);		
        this.contprojectName.setVisible(true);
        // prmtyear		
        this.prmtyear.setQueryInfo("com.kingdee.eas.port.pm.base.app.InvestYearQuery");		
        this.prmtyear.setVisible(true);		
        this.prmtyear.setEditable(true);		
        this.prmtyear.setDisplayFormat("$name$");		
        this.prmtyear.setEditFormat("$number$");		
        this.prmtyear.setCommitFormat("$number$");		
        this.prmtyear.setRequired(true);
        this.prmtyear.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtyear_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtportProject		
        this.prmtportProject.setQueryInfo("com.kingdee.eas.basedata.assistant.app.ProjectQuery");		
        this.prmtportProject.setEditable(true);		
        this.prmtportProject.setDisplayFormat("$name$");		
        this.prmtportProject.setEditFormat("$number$");		
        this.prmtportProject.setCommitFormat("$number$");		
        this.prmtportProject.setRequired(true);
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
        // txtamount		
        this.txtamount.setHorizontalAlignment(2);		
        this.txtamount.setDataType(1);		
        this.txtamount.setSupportedEmpty(true);		
        this.txtamount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtamount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtamount.setPrecision(2);		
        this.txtamount.setRequired(false);
        // txtinvestAmount		
        this.txtinvestAmount.setHorizontalAlignment(2);		
        this.txtinvestAmount.setDataType(1);		
        this.txtinvestAmount.setSupportedEmpty(true);		
        this.txtinvestAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtinvestAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtinvestAmount.setPrecision(2);		
        this.txtinvestAmount.setRequired(false);
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
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setEditable(false);
        // planType		
        this.planType.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.pm.base.coms.PlanTypeEnum").toArray());		
        this.planType.setRequired(true);
        this.planType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    planType_itemStateChanged(e);
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
        // prmtfundSource		
        this.prmtfundSource.setQueryInfo("com.kingdee.eas.port.pm.base.app.FundSourceQuery");		
        this.prmtfundSource.setEditable(true);		
        this.prmtfundSource.setDisplayFormat("$name$");		
        this.prmtfundSource.setEditFormat("$number$");		
        this.prmtfundSource.setCommitFormat("$number$");		
        this.prmtfundSource.setRequired(true);
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
        // txtchancedAmount		
        this.txtchancedAmount.setHorizontalAlignment(2);		
        this.txtchancedAmount.setDataType(1);		
        this.txtchancedAmount.setSupportedEmpty(true);		
        this.txtchancedAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtchancedAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtchancedAmount.setPrecision(2);		
        this.txtchancedAmount.setRequired(false);		
        this.txtchancedAmount.setEnabled(false);
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
        // txtbalance		
        this.txtbalance.setVisible(true);		
        this.txtbalance.setHorizontalAlignment(2);		
        this.txtbalance.setDataType(1);		
        this.txtbalance.setSupportedEmpty(true);		
        this.txtbalance.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtbalance.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtbalance.setPrecision(2);		
        this.txtbalance.setRequired(false);
        // prmtprojectType		
        this.prmtprojectType.setQueryInfo("com.kingdee.eas.port.pm.base.app.ProjectTypeQuery");		
        this.prmtprojectType.setDisplayFormat("$name$");		
        this.prmtprojectType.setEditFormat("$number$");		
        this.prmtprojectType.setCommitFormat("$number$");		
        this.prmtprojectType.setRequired(true);
        this.prmtprojectType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtprojectType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCU		
        this.prmtCU.setQueryInfo("com.kingdee.eas.basedata.framework.app.CtrlUnitQuery");		
        this.prmtCU.setRequired(true);
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
        // txtseq		
        this.txtseq.setVisible(true);		
        this.txtseq.setHorizontalAlignment(2);		
        this.txtseq.setDataType(1);		
        this.txtseq.setSupportedEmpty(true);		
        this.txtseq.setMinimumValue( new java.math.BigDecimal("-1.0E27"));		
        this.txtseq.setMaximumValue( new java.math.BigDecimal("1.0E27"));		
        this.txtseq.setPrecision(1);		
        this.txtseq.setRequired(false);		
        this.txtseq.setForeground(new java.awt.Color(255,0,0));
        // txtprojectName		
        this.txtprojectName.setHorizontalAlignment(2);		
        this.txtprojectName.setMaxLength(100);		
        this.txtprojectName.setRequired(true);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtaddress,txtBIMUDF0027,txtchancedAmount,txtamount,txtaddInvestAmount,prmtprojectType,prmtrequestOrg,prmtrequestPerson,pkplanEndDate,pkplanStartDate,prmtfundSource,prmtportProject,prmtCU,pkLastUpdateTime,prmtLastUpdateUser,pkCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,pkAuditTime,comboBizStatus,comboStatus,txtprojectName,prmtbuildType,txtscheme,txtanalyse,planType,objectState,prmtcostTemp,txtinvestAmount,txtremark,kdtEntry,kdtE2,kdtE3,prmtyear,txtbalance}));
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
        contCreator.setBounds(new Rectangle(8, 593, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(8, 593, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(8, 621, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(8, 621, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(378, 593, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(378, 593, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(378, 621, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(378, 621, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(8, 627, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(8, 627, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(748, 593, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(748, 593, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(285, 631, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(285, 631, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(748, 621, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(748, 621, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane1.setBounds(new Rectangle(10, 190, 1009, 400));
        this.add(kDTabbedPane1, new KDLayout.Constraints(10, 190, 1009, 400, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contremark.setBounds(new Rectangle(571, 633, 270, 19));
        this.add(contremark, new KDLayout.Constraints(571, 633, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contcostTemp.setBounds(new Rectangle(863, 632, 68, 19));
        this.add(contcostTemp, new KDLayout.Constraints(863, 632, 68, 19, 0));
        kDPanel6.setBounds(new Rectangle(14, 3, 1004, 179));
        this.add(kDPanel6, new KDLayout.Constraints(14, 3, 1004, 179, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        kDTabbedPane1.add(kDScrollPane1, resHelper.getString("kDScrollPane1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane1.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
        kDTabbedPane1.add(kDPanel5, resHelper.getString("kDPanel5.constraints"));
        kDTabbedPane1.add(kDPanel4, resHelper.getString("kDPanel4.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1008, 367));        contplanStartDate.setBounds(new Rectangle(5, 10, 270, 19));
        kDPanel1.add(contplanStartDate, new KDLayout.Constraints(5, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contplanEndDate.setBounds(new Rectangle(346, 10, 270, 19));
        kDPanel1.add(contplanEndDate, new KDLayout.Constraints(346, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBIMUDF0027.setBounds(new Rectangle(5, 88, 957, 272));
        kDPanel1.add(contBIMUDF0027, new KDLayout.Constraints(5, 88, 957, 272, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contaddress.setBounds(new Rectangle(5, 38, 270, 19));
        kDPanel1.add(contaddress, new KDLayout.Constraints(5, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(4, 526, 957, 9));
        kDPanel1.add(kDContainer1, new KDLayout.Constraints(4, 526, 957, 9, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contcompanyProperty.setBounds(new Rectangle(346, 38, 270, 19));
        kDPanel1.add(contcompanyProperty, new KDLayout.Constraints(346, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStatus.setBounds(new Rectangle(695, 38, 270, 19));
        kDPanel1.add(contStatus, new KDLayout.Constraints(695, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(695, 10, 270, 19));
        kDPanel1.add(contBizDate, new KDLayout.Constraints(695, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contproject.setBounds(new Rectangle(5, 63, 270, 19));
        kDPanel1.add(contproject, new KDLayout.Constraints(5, 63, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contplanStartDate
        contplanStartDate.setBoundEditor(pkplanStartDate);
        //contplanEndDate
        contplanEndDate.setBoundEditor(pkplanEndDate);
        //contBIMUDF0027
        contBIMUDF0027.setBoundEditor(scrollPaneBIMUDF0027);
        //scrollPaneBIMUDF0027
        scrollPaneBIMUDF0027.getViewport().add(txtBIMUDF0027, null);
        //contaddress
        contaddress.setBoundEditor(txtaddress);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry,new com.kingdee.eas.port.pm.invest.YearInvestPlanEntryInfo(),null,false);
        kDContainer1.getContentPane().add(kdtEntry_detailPanel, BorderLayout.CENTER);
        //contcompanyProperty
        contcompanyProperty.setBoundEditor(prmtcompanyProperty);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contproject
        contproject.setBoundEditor(prmtproject);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1008, 367));        contanalyse.setBounds(new Rectangle(4, 6, 962, 354));
        kDPanel2.add(contanalyse, new KDLayout.Constraints(4, 6, 962, 354, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contanalyse
        contanalyse.setBoundEditor(scrollPaneanalyse);
        //scrollPaneanalyse
        scrollPaneanalyse.getViewport().add(txtanalyse, null);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1008, 367));        contscheme.setBounds(new Rectangle(4, 4, 962, 357));
        kDPanel3.add(contscheme, new KDLayout.Constraints(4, 4, 962, 357, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contscheme
        contscheme.setBoundEditor(scrollPanescheme);
        //scrollPanescheme
        scrollPanescheme.getViewport().add(txtscheme, null);
        //kDPanel5
        kDPanel5.setLayout(new KDLayout());
        kDPanel5.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1008, 367));        kdtE2.setBounds(new Rectangle(3, 128, 971, 231));
        kdtE2_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE2,new com.kingdee.eas.port.pm.invest.YearInvestPlanE2Info(),null,false);
        kDPanel5.add(kdtE2_detailPanel, new KDLayout.Constraints(3, 128, 971, 231, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contdesc.setBounds(new Rectangle(5, 6, 970, 119));
        kDPanel5.add(contdesc, new KDLayout.Constraints(5, 6, 970, 119, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contdesc
        contdesc.setBoundEditor(scrollPanedesc);
        //scrollPanedesc
        scrollPanedesc.getViewport().add(txtdesc, null);
        //kDPanel4
        kDPanel4.setLayout(new KDLayout());
        kDPanel4.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1008, 367));        kdtE3.setBounds(new Rectangle(1, -1, 996, 360));
        kDPanel4.add(kdtE3, new KDLayout.Constraints(1, -1, 996, 360, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contremark
        contremark.setBoundEditor(txtremark);
        //contcostTemp
        contcostTemp.setBoundEditor(prmtcostTemp);
        //kDPanel6
        kDPanel6.setLayout(new KDLayout());
        kDPanel6.putClientProperty("OriginalBounds", new Rectangle(14, 3, 1004, 179));        contyear.setBounds(new Rectangle(701, 112, 270, 19));
        kDPanel6.add(contyear, new KDLayout.Constraints(701, 112, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contportProject.setBounds(new Rectangle(701, 138, 270, 19));
        kDPanel6.add(contportProject, new KDLayout.Constraints(701, 138, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contamount.setBounds(new Rectangle(18, 112, 270, 19));
        kDPanel6.add(contamount, new KDLayout.Constraints(18, 112, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continvestAmount.setBounds(new Rectangle(18, 86, 270, 19));
        kDPanel6.add(continvestAmount, new KDLayout.Constraints(18, 86, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrequestPerson.setBounds(new Rectangle(18, 60, 270, 19));
        kDPanel6.add(contrequestPerson, new KDLayout.Constraints(18, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(18, 34, 270, 19));
        kDPanel6.add(contNumber, new KDLayout.Constraints(18, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contplanType.setBounds(new Rectangle(359, 34, 270, 19));
        kDPanel6.add(contplanType, new KDLayout.Constraints(359, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrequestOrg.setBounds(new Rectangle(359, 60, 270, 19));
        kDPanel6.add(contrequestOrg, new KDLayout.Constraints(359, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contfundSource.setBounds(new Rectangle(359, 86, 270, 19));
        kDPanel6.add(contfundSource, new KDLayout.Constraints(359, 86, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contaddInvestAmount.setBounds(new Rectangle(359, 112, 270, 19));
        kDPanel6.add(contaddInvestAmount, new KDLayout.Constraints(359, 112, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contchancedAmount.setBounds(new Rectangle(18, 138, 270, 19));
        kDPanel6.add(contchancedAmount, new KDLayout.Constraints(18, 138, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbuildType.setBounds(new Rectangle(701, 86, 270, 19));
        kDPanel6.add(contbuildType, new KDLayout.Constraints(701, 86, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contbalance.setBounds(new Rectangle(359, 138, 270, 19));
        kDPanel6.add(contbalance, new KDLayout.Constraints(359, 138, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contprojectType.setBounds(new Rectangle(701, 60, 270, 19));
        kDPanel6.add(contprojectType, new KDLayout.Constraints(701, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCU.setBounds(new Rectangle(701, 34, 270, 19));
        kDPanel6.add(contCU, new KDLayout.Constraints(701, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contobjectState.setBounds(new Rectangle(701, 8, 270, 19));
        kDPanel6.add(contobjectState, new KDLayout.Constraints(701, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contseq.setBounds(new Rectangle(550, 8, 79, 19));
        kDPanel6.add(contseq, new KDLayout.Constraints(550, 8, 79, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contprojectName.setBounds(new Rectangle(18, 8, 523, 19));
        kDPanel6.add(contprojectName, new KDLayout.Constraints(18, 8, 523, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contyear
        contyear.setBoundEditor(prmtyear);
        //contportProject
        contportProject.setBoundEditor(prmtportProject);
        //contamount
        contamount.setBoundEditor(txtamount);
        //continvestAmount
        continvestAmount.setBoundEditor(txtinvestAmount);
        //contrequestPerson
        contrequestPerson.setBoundEditor(prmtrequestPerson);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contplanType
        contplanType.setBoundEditor(planType);
        //contrequestOrg
        contrequestOrg.setBoundEditor(prmtrequestOrg);
        //contfundSource
        contfundSource.setBoundEditor(prmtfundSource);
        //contaddInvestAmount
        contaddInvestAmount.setBoundEditor(txtaddInvestAmount);
        //contchancedAmount
        contchancedAmount.setBoundEditor(txtchancedAmount);
        //contbuildType
        contbuildType.setBoundEditor(prmtbuildType);
        //contbalance
        contbalance.setBoundEditor(txtbalance);
        //contprojectType
        contprojectType.setBoundEditor(prmtprojectType);
        //contCU
        contCU.setBoundEditor(prmtCU);
        //contobjectState
        contobjectState.setBoundEditor(objectState);
        //contseq
        contseq.setBoundEditor(txtseq);
        //contprojectName
        contprojectName.setBoundEditor(txtprojectName);

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
		dataBinder.registerBinding("planStartDate", java.util.Date.class, this.pkplanStartDate, "value");
		dataBinder.registerBinding("planEndDate", java.util.Date.class, this.pkplanEndDate, "value");
		dataBinder.registerBinding("BIMUDF0027", String.class, this.txtBIMUDF0027, "text");
		dataBinder.registerBinding("address", String.class, this.txtaddress, "text");
		dataBinder.registerBinding("Entry.seq", int.class, this.kdtEntry, "seq.text");
		dataBinder.registerBinding("Entry", com.kingdee.eas.port.pm.invest.YearInvestPlanEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("Entry.costName", String.class, this.kdtEntry, "costName.text");
		dataBinder.registerBinding("Entry.estimate", java.math.BigDecimal.class, this.kdtEntry, "estimate.text");
		dataBinder.registerBinding("Entry.yearInvestBudget", java.math.BigDecimal.class, this.kdtEntry, "yearInvestBudget.text");
		dataBinder.registerBinding("Entry.planStartT", java.util.Date.class, this.kdtEntry, "planStartT.text");
		dataBinder.registerBinding("Entry.acceptTime", java.util.Date.class, this.kdtEntry, "acceptTime.text");
		dataBinder.registerBinding("Entry.description", String.class, this.kdtEntry, "description.text");
		dataBinder.registerBinding("Entry.costType", java.lang.Object.class, this.kdtEntry, "costType.text");
		dataBinder.registerBinding("companyProperty", com.kingdee.eas.port.pm.base.CompanyPropertyInfo.class, this.prmtcompanyProperty, "data");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("project", com.kingdee.eas.basedata.assistant.ProjectInfo.class, this.prmtproject, "data");
		dataBinder.registerBinding("analyse", String.class, this.txtanalyse, "text");
		dataBinder.registerBinding("scheme", String.class, this.txtscheme, "text");
		dataBinder.registerBinding("E2.seq", int.class, this.kdtE2, "seq.text");
		dataBinder.registerBinding("E2", com.kingdee.eas.port.pm.invest.YearInvestPlanE2Info.class, this.kdtE2, "userObject");
		dataBinder.registerBinding("E2.apIndex", String.class, this.kdtE2, "apIndex.text");
		dataBinder.registerBinding("E2.planComplete", String.class, this.kdtE2, "planComplete.text");
		dataBinder.registerBinding("desc", String.class, this.txtdesc, "text");
		dataBinder.registerBinding("remark", String.class, this.txtremark, "text");
		dataBinder.registerBinding("costTemp", com.kingdee.eas.port.pm.invest.CostTempInfo.class, this.prmtcostTemp, "data");
		dataBinder.registerBinding("year", com.kingdee.eas.port.pm.base.InvestYearInfo.class, this.prmtyear, "data");
		dataBinder.registerBinding("portProject", com.kingdee.eas.port.pm.project.PortProjectInfo.class, this.prmtportProject, "data");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtamount, "value");
		dataBinder.registerBinding("investAmount", java.math.BigDecimal.class, this.txtinvestAmount, "value");
		dataBinder.registerBinding("requestPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtrequestPerson, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("planType", com.kingdee.eas.port.pm.base.coms.PlanTypeEnum.class, this.planType, "selectedItem");
		dataBinder.registerBinding("requestOrg", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtrequestOrg, "data");
		dataBinder.registerBinding("fundSource", com.kingdee.eas.port.pm.base.FundSourceInfo.class, this.prmtfundSource, "data");
		dataBinder.registerBinding("addInvestAmount", java.math.BigDecimal.class, this.txtaddInvestAmount, "value");
		dataBinder.registerBinding("chancedAmount", java.math.BigDecimal.class, this.txtchancedAmount, "value");
		dataBinder.registerBinding("buildType", com.kingdee.eas.port.pm.base.BuildTypeInfo.class, this.prmtbuildType, "data");
		dataBinder.registerBinding("balance", java.math.BigDecimal.class, this.txtbalance, "value");
		dataBinder.registerBinding("projectType", com.kingdee.eas.port.pm.base.ProjectTypeInfo.class, this.prmtprojectType, "data");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("objectState", com.kingdee.eas.port.pm.invest.ObjectStateEnum.class, this.objectState, "selectedItem");
		dataBinder.registerBinding("seq", java.math.BigDecimal.class, this.txtseq, "value");
		dataBinder.registerBinding("projectName", String.class, this.txtprojectName, "text");		
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
        this.txtaddress.requestFocusInWindow();
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

    /**
     * output setDataObject method
     */
    public void setDataObject(String key, IObjectValue dataObject)
    {
        super.setDataObject(key, dataObject);
        if (key.equalsIgnoreCase("queryYearPlanAuditInfoQuery")) {
            this.queryYearPlanAuditInfoQuery = (EntityViewInfo)dataObject;
		}
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
		getValidateHelper().registerBindProperty("planStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BIMUDF0027", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.costName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.estimate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.yearInvestBudget", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.planStartT", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.acceptTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.costType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("companyProperty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("analyse", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scheme", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.apIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.planComplete", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("desc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costTemp", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("year", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("portProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("investAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("requestPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("requestOrg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fundSource", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("addInvestAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chancedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("balance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("objectState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectName", ValidateHelper.ON_SAVE);    		
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
     * output kDTabbedPane1_stateChanged method
     */
    protected void kDTabbedPane1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtE3_doRequestRowSet method
     */
    protected void kdtE3_doRequestRowSet(RequestRowSetEvent e)
    {
        if (this.queryYearPlanAuditInfoQuery != null) {
            int start = ((Integer)e.getParam1()).intValue();
            int length = ((Integer)e.getParam2()).intValue() - start + 1;
            try {
                IQueryExecutor exec = this.getQueryExecutor(this.queryYearPlanAuditInfoQueryPK, this.queryYearPlanAuditInfoQuery);
                IRowSet rowSet = exec.executeQuery(start,length);
                e.setRowSet(rowSet);
                onGetRowSet(rowSet);
            } catch (Exception ee) {
                handUIException(ee);
            }
        }
    }

    /**
     * output prmtcostTemp_dataChanged method
     */
    protected void prmtcostTemp_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtyear_dataChanged method
     */
    protected void prmtyear_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code hereoo
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
     * output planType_itemStateChanged method
     */
    protected void planType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtbuildType_dataChanged method
     */
    protected void prmtbuildType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtprojectType_dataChanged method
     */
    protected void prmtprojectType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output objectState_itemStateChanged method
     */
    protected void objectState_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
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
     * output getQueryExecutor method
     */
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo)
    {
        IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(queryPK);
        exec.setObjectView(viewInfo);
        return exec;
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
        sic.add(new SelectorItemInfo("planStartDate"));
        sic.add(new SelectorItemInfo("planEndDate"));
        sic.add(new SelectorItemInfo("BIMUDF0027"));
        sic.add(new SelectorItemInfo("address"));
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
			sic.add(new SelectorItemInfo("companyProperty.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("companyProperty.id"));
        	sic.add(new SelectorItemInfo("companyProperty.number"));
        	sic.add(new SelectorItemInfo("companyProperty.name"));
		}
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("bizDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("project.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("project.id"));
        	sic.add(new SelectorItemInfo("project.number"));
        	sic.add(new SelectorItemInfo("project.name"));
		}
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
        sic.add(new SelectorItemInfo("desc"));
        sic.add(new SelectorItemInfo("remark"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costTemp.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("costTemp.id"));
        	sic.add(new SelectorItemInfo("costTemp.number"));
        	sic.add(new SelectorItemInfo("costTemp.tempName"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("year.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("year.id"));
        	sic.add(new SelectorItemInfo("year.number"));
        	sic.add(new SelectorItemInfo("year.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("portProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("portProject.id"));
        	sic.add(new SelectorItemInfo("portProject.number"));
        	sic.add(new SelectorItemInfo("portProject.name"));
		}
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("investAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("requestPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("requestPerson.id"));
        	sic.add(new SelectorItemInfo("requestPerson.number"));
        	sic.add(new SelectorItemInfo("requestPerson.name"));
		}
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("planType"));
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
			sic.add(new SelectorItemInfo("fundSource.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("fundSource.id"));
        	sic.add(new SelectorItemInfo("fundSource.number"));
        	sic.add(new SelectorItemInfo("fundSource.name"));
		}
        sic.add(new SelectorItemInfo("addInvestAmount"));
        sic.add(new SelectorItemInfo("chancedAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("buildType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("buildType.id"));
        	sic.add(new SelectorItemInfo("buildType.number"));
        	sic.add(new SelectorItemInfo("buildType.name"));
		}
        sic.add(new SelectorItemInfo("balance"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("projectType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("projectType.id"));
        	sic.add(new SelectorItemInfo("projectType.number"));
        	sic.add(new SelectorItemInfo("projectType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("CU.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("CU.id"));
        	sic.add(new SelectorItemInfo("CU.number"));
        	sic.add(new SelectorItemInfo("CU.name"));
		}
        sic.add(new SelectorItemInfo("objectState"));
        sic.add(new SelectorItemInfo("seq"));
        sic.add(new SelectorItemInfo("projectName"));
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
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtrequestPerson.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"申报人"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtNumber.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"单据编号"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(planType.getSelectedItem())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"计划类型"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtrequestOrg.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"申报部门"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtfundSource.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"资金来源"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtbuildType.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"建设性质"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtprojectType.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"项目类型"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(objectState.getSelectedItem())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"项目状态"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtprojectName.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"项目名称"});
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