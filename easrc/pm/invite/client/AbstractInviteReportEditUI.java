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
public abstract class AbstractInviteReportEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInviteReportEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdevOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contproSite;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer4;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continviteBudget;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaudDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contapplicant;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpaperFee;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbidBond;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjudgeSolution;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contevaTemplate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contvalidTemplate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continviteDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsubDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contopenDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0004;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreportName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contproName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contuseOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDButton kDButton1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQualificationRequemt;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continvitePlan;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtinviteBudget;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkaudDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtapplicant;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpaperFee;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbidBond;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinviteType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox judgeSolution;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtevaTemplate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtvalidTemplate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkinviteDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pksubDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkopenDate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneBIMUDF0004;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtBIMUDF0004;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtreportName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtproName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtuseOrg;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtQualificationRequemt;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer5;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry4;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry4_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry2;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry2_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry3;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry3_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry5;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry5_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel5;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrmhigh;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrmlow;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbusinessScore;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttechScore;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreduHigh;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreduLow;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcoefficient;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtrmhigh;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtrmlow;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbusinessScore;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttechScore;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtreduHigh;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtreduLow;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcoefficient;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel8;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel7;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer7;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE7;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE7_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer6;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE6;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE6_detailPanel = null;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinvitePlan;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtprojectNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtdevOrg;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtproSite;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry1;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry1_detailPanel = null;
    protected com.kingdee.eas.port.pm.invite.InviteReportInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractInviteReportEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInviteReportEditUI.class.getName());
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
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contprojectNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdevOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contproSite = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer4 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDPanel6 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.continviteBudget = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaudDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contapplicant = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpaperFee = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbidBond = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjudgeSolution = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contevaTemplate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contvalidTemplate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continviteDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsubDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contopenDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBIMUDF0004 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreportName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contproName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contuseOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDButton1 = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contQualificationRequemt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane2 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.continvitePlan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtinviteBudget = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkaudDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtapplicant = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtpaperFee = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbidBond = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtinviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.judgeSolution = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtevaTemplate = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtvalidTemplate = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkinviteDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pksubDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkopenDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.scrollPaneBIMUDF0004 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtBIMUDF0004 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtreportName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtproName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtuseOrg = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtQualificationRequemt = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDContainer5 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntry4 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntry2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntry3 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtEntry5 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDPanel5 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDTabbedPane3 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contrmhigh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrmlow = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbusinessScore = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttechScore = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreduHigh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreduLow = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcoefficient = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtrmhigh = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtrmlow = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbusinessScore = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttechScore = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtreduHigh = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtreduLow = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcoefficient = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDPanel8 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel7 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDContainer7 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtE7 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer6 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtE6 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtinvitePlan = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtprojectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtdevOrg = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtproSite = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdtEntry1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contCU.setName("contCU");
        this.contBizStatus.setName("contBizStatus");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contprojectNumber.setName("contprojectNumber");
        this.contdevOrg.setName("contdevOrg");
        this.contproSite.setName("contproSite");
        this.kDContainer4.setName("kDContainer4");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtCU.setName("prmtCU");
        this.comboBizStatus.setName("comboBizStatus");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.kDPanel6.setName("kDPanel6");
        this.continviteBudget.setName("continviteBudget");
        this.contaudDate.setName("contaudDate");
        this.contapplicant.setName("contapplicant");
        this.contpaperFee.setName("contpaperFee");
        this.contbidBond.setName("contbidBond");
        this.continviteType.setName("continviteType");
        this.contjudgeSolution.setName("contjudgeSolution");
        this.contevaTemplate.setName("contevaTemplate");
        this.contvalidTemplate.setName("contvalidTemplate");
        this.continviteDate.setName("continviteDate");
        this.contsubDate.setName("contsubDate");
        this.contopenDate.setName("contopenDate");
        this.contBIMUDF0004.setName("contBIMUDF0004");
        this.contNumber.setName("contNumber");
        this.contreportName.setName("contreportName");
        this.contproName.setName("contproName");
        this.contuseOrg.setName("contuseOrg");
        this.contStatus.setName("contStatus");
        this.kDButton1.setName("kDButton1");
        this.contQualificationRequemt.setName("contQualificationRequemt");
        this.kDTabbedPane2.setName("kDTabbedPane2");
        this.continvitePlan.setName("continvitePlan");
        this.txtinviteBudget.setName("txtinviteBudget");
        this.pkaudDate.setName("pkaudDate");
        this.prmtapplicant.setName("prmtapplicant");
        this.txtpaperFee.setName("txtpaperFee");
        this.txtbidBond.setName("txtbidBond");
        this.prmtinviteType.setName("prmtinviteType");
        this.judgeSolution.setName("judgeSolution");
        this.prmtevaTemplate.setName("prmtevaTemplate");
        this.prmtvalidTemplate.setName("prmtvalidTemplate");
        this.pkinviteDate.setName("pkinviteDate");
        this.pksubDate.setName("pksubDate");
        this.pkopenDate.setName("pkopenDate");
        this.scrollPaneBIMUDF0004.setName("scrollPaneBIMUDF0004");
        this.txtBIMUDF0004.setName("txtBIMUDF0004");
        this.txtNumber.setName("txtNumber");
        this.txtreportName.setName("txtreportName");
        this.prmtproName.setName("prmtproName");
        this.prmtuseOrg.setName("prmtuseOrg");
        this.comboStatus.setName("comboStatus");
        this.txtQualificationRequemt.setName("txtQualificationRequemt");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel4.setName("kDPanel4");
        this.kDContainer5.setName("kDContainer5");
        this.kdtEntry4.setName("kdtEntry4");
        this.kDContainer1.setName("kDContainer1");
        this.kdtEntry2.setName("kdtEntry2");
        this.kDContainer3.setName("kDContainer3");
        this.kDContainer2.setName("kDContainer2");
        this.kdtEntry3.setName("kdtEntry3");
        this.kdtEntry5.setName("kdtEntry5");
        this.kDPanel5.setName("kDPanel5");
        this.kDTabbedPane3.setName("kDTabbedPane3");
        this.contrmhigh.setName("contrmhigh");
        this.contrmlow.setName("contrmlow");
        this.contbusinessScore.setName("contbusinessScore");
        this.conttechScore.setName("conttechScore");
        this.contreduHigh.setName("contreduHigh");
        this.contreduLow.setName("contreduLow");
        this.contcoefficient.setName("contcoefficient");
        this.txtrmhigh.setName("txtrmhigh");
        this.txtrmlow.setName("txtrmlow");
        this.txtbusinessScore.setName("txtbusinessScore");
        this.txttechScore.setName("txttechScore");
        this.txtreduHigh.setName("txtreduHigh");
        this.txtreduLow.setName("txtreduLow");
        this.txtcoefficient.setName("txtcoefficient");
        this.kDPanel8.setName("kDPanel8");
        this.kDPanel7.setName("kDPanel7");
        this.kDContainer7.setName("kDContainer7");
        this.kdtE7.setName("kdtE7");
        this.kDContainer6.setName("kDContainer6");
        this.kdtE6.setName("kdtE6");
        this.prmtinvitePlan.setName("prmtinvitePlan");
        this.txtprojectNumber.setName("txtprojectNumber");
        this.prmtdevOrg.setName("prmtdevOrg");
        this.txtproSite.setName("txtproSite");
        this.kdtEntry1.setName("kdtEntry1");
        // CoreUI		
        this.setPreferredSize(new Dimension(1010,629));		
        this.setBorder(null);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contCU		
        this.contCU.setBoundLabelText(resHelper.getString("contCU.boundLabelText"));		
        this.contCU.setBoundLabelLength(100);		
        this.contCU.setBoundLabelUnderline(true);		
        this.contCU.setEnabled(false);
        // contBizStatus		
        this.contBizStatus.setBoundLabelText(resHelper.getString("contBizStatus.boundLabelText"));		
        this.contBizStatus.setBoundLabelLength(100);		
        this.contBizStatus.setBoundLabelUnderline(true);		
        this.contBizStatus.setEnabled(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // kDTabbedPane1
        // contprojectNumber		
        this.contprojectNumber.setBoundLabelText(resHelper.getString("contprojectNumber.boundLabelText"));		
        this.contprojectNumber.setBoundLabelLength(100);		
        this.contprojectNumber.setBoundLabelUnderline(true);		
        this.contprojectNumber.setVisible(true);
        // contdevOrg		
        this.contdevOrg.setBoundLabelText(resHelper.getString("contdevOrg.boundLabelText"));		
        this.contdevOrg.setBoundLabelLength(100);		
        this.contdevOrg.setBoundLabelUnderline(true);		
        this.contdevOrg.setVisible(true);
        // contproSite		
        this.contproSite.setBoundLabelText(resHelper.getString("contproSite.boundLabelText"));		
        this.contproSite.setBoundLabelLength(100);		
        this.contproSite.setBoundLabelUnderline(true);		
        this.contproSite.setVisible(true);
        // kDContainer4		
        this.kDContainer4.setTitle(resHelper.getString("kDContainer4.title"));		
        this.kDContainer4.setEnableActive(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);		
        this.prmtLastUpdateUser.setDisplayFormat("$name$");		
        this.prmtLastUpdateUser.setEditFormat("$name$");		
        this.prmtLastUpdateUser.setCommitFormat("$name$");
        // pkLastUpdateTime		
        this.pkLastUpdateTime.setTimeEnabled(true);		
        this.pkLastUpdateTime.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setCommitFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");		
        this.prmtCreator.setDisplayFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setTimeEnabled(true);		
        this.pkCreateTime.setEnabled(false);
        // prmtCU		
        this.prmtCU.setEnabled(false);
        // comboBizStatus		
        this.comboBizStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBizActionEnum").toArray());		
        this.comboBizStatus.setEnabled(false);
        // pkBizDate
        // txtDescription
        // kDPanel6
        // continviteBudget		
        this.continviteBudget.setBoundLabelText(resHelper.getString("continviteBudget.boundLabelText"));		
        this.continviteBudget.setBoundLabelLength(100);		
        this.continviteBudget.setBoundLabelUnderline(true);		
        this.continviteBudget.setVisible(true);		
        this.continviteBudget.setForeground(new java.awt.Color(255,0,0));
        // contaudDate		
        this.contaudDate.setBoundLabelText(resHelper.getString("contaudDate.boundLabelText"));		
        this.contaudDate.setBoundLabelLength(100);		
        this.contaudDate.setBoundLabelUnderline(true);		
        this.contaudDate.setVisible(true);
        // contapplicant		
        this.contapplicant.setBoundLabelText(resHelper.getString("contapplicant.boundLabelText"));		
        this.contapplicant.setBoundLabelLength(100);		
        this.contapplicant.setBoundLabelUnderline(true);		
        this.contapplicant.setVisible(true);
        // contpaperFee		
        this.contpaperFee.setBoundLabelText(resHelper.getString("contpaperFee.boundLabelText"));		
        this.contpaperFee.setBoundLabelLength(100);		
        this.contpaperFee.setBoundLabelUnderline(true);		
        this.contpaperFee.setVisible(false);
        // contbidBond		
        this.contbidBond.setBoundLabelText(resHelper.getString("contbidBond.boundLabelText"));		
        this.contbidBond.setBoundLabelLength(100);		
        this.contbidBond.setBoundLabelUnderline(true);		
        this.contbidBond.setVisible(true);
        // continviteType		
        this.continviteType.setBoundLabelText(resHelper.getString("continviteType.boundLabelText"));		
        this.continviteType.setBoundLabelLength(100);		
        this.continviteType.setBoundLabelUnderline(true);		
        this.continviteType.setVisible(true);
        // contjudgeSolution		
        this.contjudgeSolution.setBoundLabelText(resHelper.getString("contjudgeSolution.boundLabelText"));		
        this.contjudgeSolution.setBoundLabelLength(100);		
        this.contjudgeSolution.setBoundLabelUnderline(true);		
        this.contjudgeSolution.setVisible(true);
        // contevaTemplate		
        this.contevaTemplate.setBoundLabelText(resHelper.getString("contevaTemplate.boundLabelText"));		
        this.contevaTemplate.setBoundLabelLength(100);		
        this.contevaTemplate.setBoundLabelUnderline(true);		
        this.contevaTemplate.setVisible(true);
        // contvalidTemplate		
        this.contvalidTemplate.setBoundLabelText(resHelper.getString("contvalidTemplate.boundLabelText"));		
        this.contvalidTemplate.setBoundLabelLength(100);		
        this.contvalidTemplate.setBoundLabelUnderline(true);		
        this.contvalidTemplate.setVisible(true);
        // continviteDate		
        this.continviteDate.setBoundLabelText(resHelper.getString("continviteDate.boundLabelText"));		
        this.continviteDate.setBoundLabelLength(100);		
        this.continviteDate.setBoundLabelUnderline(true);		
        this.continviteDate.setVisible(true);
        // contsubDate		
        this.contsubDate.setBoundLabelText(resHelper.getString("contsubDate.boundLabelText"));		
        this.contsubDate.setBoundLabelLength(100);		
        this.contsubDate.setBoundLabelUnderline(true);		
        this.contsubDate.setVisible(false);
        // contopenDate		
        this.contopenDate.setBoundLabelText(resHelper.getString("contopenDate.boundLabelText"));		
        this.contopenDate.setBoundLabelLength(100);		
        this.contopenDate.setBoundLabelUnderline(true);		
        this.contopenDate.setVisible(true);
        // contBIMUDF0004		
        this.contBIMUDF0004.setBoundLabelText(resHelper.getString("contBIMUDF0004.boundLabelText"));		
        this.contBIMUDF0004.setBoundLabelLength(16);		
        this.contBIMUDF0004.setBoundLabelUnderline(true);		
        this.contBIMUDF0004.setVisible(true);		
        this.contBIMUDF0004.setBoundLabelAlignment(8);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contreportName		
        this.contreportName.setBoundLabelText(resHelper.getString("contreportName.boundLabelText"));		
        this.contreportName.setBoundLabelLength(100);		
        this.contreportName.setBoundLabelUnderline(true);		
        this.contreportName.setVisible(true);
        // contproName		
        this.contproName.setBoundLabelText(resHelper.getString("contproName.boundLabelText"));		
        this.contproName.setBoundLabelLength(100);		
        this.contproName.setBoundLabelUnderline(true);		
        this.contproName.setVisible(true);
        // contuseOrg		
        this.contuseOrg.setBoundLabelText(resHelper.getString("contuseOrg.boundLabelText"));		
        this.contuseOrg.setBoundLabelLength(100);		
        this.contuseOrg.setBoundLabelUnderline(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);
        // kDButton1		
        this.kDButton1.setText(resHelper.getString("kDButton1.text"));
        // contQualificationRequemt		
        this.contQualificationRequemt.setBoundLabelText(resHelper.getString("contQualificationRequemt.boundLabelText"));		
        this.contQualificationRequemt.setBoundLabelLength(100);		
        this.contQualificationRequemt.setBoundLabelUnderline(true);		
        this.contQualificationRequemt.setVisible(true);
        // kDTabbedPane2
        // continvitePlan		
        this.continvitePlan.setBoundLabelText(resHelper.getString("continvitePlan.boundLabelText"));		
        this.continvitePlan.setBoundLabelLength(100);		
        this.continvitePlan.setBoundLabelUnderline(true);		
        this.continvitePlan.setVisible(true);
        // txtinviteBudget		
        this.txtinviteBudget.setHorizontalAlignment(2);		
        this.txtinviteBudget.setMaxLength(100);		
        this.txtinviteBudget.setRequired(false);		
        this.txtinviteBudget.setEnabled(false);
        // pkaudDate		
        this.pkaudDate.setRequired(false);
        // prmtapplicant		
        this.prmtapplicant.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtapplicant.setEditable(true);		
        this.prmtapplicant.setDisplayFormat("$name$");		
        this.prmtapplicant.setEditFormat("$number$");		
        this.prmtapplicant.setCommitFormat("$number$");		
        this.prmtapplicant.setRequired(false);
        // txtpaperFee		
        this.txtpaperFee.setHorizontalAlignment(2);		
        this.txtpaperFee.setMaxLength(100);		
        this.txtpaperFee.setRequired(false);
        // txtbidBond		
        this.txtbidBond.setHorizontalAlignment(2);		
        this.txtbidBond.setMaxLength(100);		
        this.txtbidBond.setRequired(false);
        // prmtinviteType		
        this.prmtinviteType.setQueryInfo("com.kingdee.eas.port.pm.base.app.InviteTypeQuery");		
        this.prmtinviteType.setEditable(true);		
        this.prmtinviteType.setDisplayFormat("$name$");		
        this.prmtinviteType.setEditFormat("$number$");		
        this.prmtinviteType.setCommitFormat("$number$");		
        this.prmtinviteType.setRequired(false);
        this.prmtinviteType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtinviteType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // judgeSolution		
        this.judgeSolution.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.pm.invite.judgeSolution").toArray());		
        this.judgeSolution.setRequired(false);
        this.judgeSolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    judgeSolution_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // prmtevaTemplate		
        this.prmtevaTemplate.setQueryInfo("com.kingdee.eas.port.pm.base.app.EvaluationTemplateQuery");		
        this.prmtevaTemplate.setEditable(true);		
        this.prmtevaTemplate.setDisplayFormat("$templateName$");		
        this.prmtevaTemplate.setEditFormat("$number$");		
        this.prmtevaTemplate.setCommitFormat("$number$");		
        this.prmtevaTemplate.setRequired(false);
        		prmtevaTemplate.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.pm.base.client.EvaluationTemplateListUI prmtevaTemplate_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (prmtevaTemplate_F7ListUI == null) {
					try {
						prmtevaTemplate_F7ListUI = new com.kingdee.eas.port.pm.base.client.EvaluationTemplateListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmtevaTemplate_F7ListUI));
					prmtevaTemplate_F7ListUI.setF7Use(true,ctx);
					prmtevaTemplate.setSelector(prmtevaTemplate_F7ListUI);
				}
			}
		});
					
        this.prmtevaTemplate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtevaTemplate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtvalidTemplate		
        this.prmtvalidTemplate.setQueryInfo("com.kingdee.eas.port.pm.base.app.EvaluationTemplateQuery");		
        this.prmtvalidTemplate.setEditable(true);		
        this.prmtvalidTemplate.setDisplayFormat("$templateName$");		
        this.prmtvalidTemplate.setEditFormat("$number$");		
        this.prmtvalidTemplate.setCommitFormat("$number$");		
        this.prmtvalidTemplate.setRequired(false);
        		prmtvalidTemplate.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.pm.base.client.EvaluationTemplateListUI prmtvalidTemplate_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (prmtvalidTemplate_F7ListUI == null) {
					try {
						prmtvalidTemplate_F7ListUI = new com.kingdee.eas.port.pm.base.client.EvaluationTemplateListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmtvalidTemplate_F7ListUI));
					prmtvalidTemplate_F7ListUI.setF7Use(true,ctx);
					prmtvalidTemplate.setSelector(prmtvalidTemplate_F7ListUI);
				}
			}
		});
					
        this.prmtvalidTemplate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtvalidTemplate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkinviteDate		
        this.pkinviteDate.setRequired(false);
        // pksubDate		
        this.pksubDate.setRequired(false);
        // pkopenDate		
        this.pkopenDate.setRequired(false);
        // scrollPaneBIMUDF0004
        // txtBIMUDF0004		
        this.txtBIMUDF0004.setRequired(false);		
        this.txtBIMUDF0004.setMaxLength(255);
        // txtNumber
        // txtreportName		
        this.txtreportName.setHorizontalAlignment(2);		
        this.txtreportName.setMaxLength(100);		
        this.txtreportName.setRequired(false);
        // prmtproName		
        this.prmtproName.setQueryInfo("com.kingdee.eas.basedata.assistant.app.ProjectQuery");		
        this.prmtproName.setEditable(true);		
        this.prmtproName.setDisplayFormat("$name$");		
        this.prmtproName.setEditFormat("$number$");		
        this.prmtproName.setCommitFormat("$number$");		
        this.prmtproName.setRequired(false);
        prmtproName.addDataChangeListener(new DataChangeListener() {
		public void dataChanged(DataChangeEvent e) {
			try {
				prmtproName_Changed();
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        // prmtuseOrg		
        this.prmtuseOrg.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtuseOrg.setEditable(true);		
        this.prmtuseOrg.setDisplayFormat("$name$");		
        this.prmtuseOrg.setEditFormat("$number$");		
        this.prmtuseOrg.setCommitFormat("$number$");		
        this.prmtuseOrg.setRequired(false);
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);
        // txtQualificationRequemt		
        this.txtQualificationRequemt.setHorizontalAlignment(2);		
        this.txtQualificationRequemt.setMaxLength(100);		
        this.txtQualificationRequemt.setRequired(false);
        // kDPanel1
        // kDPanel2		
        this.kDPanel2.setToolTipText(resHelper.getString("kDPanel2.toolTipText"));
        // kDPanel3
        // kDPanel4
        // kDContainer5
        // kdtEntry4
		String kdtEntry4StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"budgetNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"budgetName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"budgetAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"balance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"content\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{budgetNumber}</t:Cell><t:Cell>$Resource{budgetName}</t:Cell><t:Cell>$Resource{budgetAmount}</t:Cell><t:Cell>$Resource{balance}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{content}</t:Cell><t:Cell>$Resource{lastAmount}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry4.setFormatXml(resHelper.translateString("kdtEntry4",kdtEntry4StrXML));
        kdtEntry4.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtEntry4_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});


                this.kdtEntry4.putBindContents("editData",new String[] {"seq","budgetNumber","budgetName","budgetAmount","balance","amount","content","lastAmount","beizhu"});


        this.kdtEntry4.checkParsed();
        KDTextField kdtEntry4_budgetName_TextField = new KDTextField();
        kdtEntry4_budgetName_TextField.setName("kdtEntry4_budgetName_TextField");
        kdtEntry4_budgetName_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry4_budgetName_CellEditor = new KDTDefaultCellEditor(kdtEntry4_budgetName_TextField);
        this.kdtEntry4.getColumn("budgetName").setEditor(kdtEntry4_budgetName_CellEditor);
        KDFormattedTextField kdtEntry4_budgetAmount_TextField = new KDFormattedTextField();
        kdtEntry4_budgetAmount_TextField.setName("kdtEntry4_budgetAmount_TextField");
        kdtEntry4_budgetAmount_TextField.setVisible(true);
        kdtEntry4_budgetAmount_TextField.setEditable(true);
        kdtEntry4_budgetAmount_TextField.setHorizontalAlignment(2);
        kdtEntry4_budgetAmount_TextField.setDataType(1);
        	kdtEntry4_budgetAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry4_budgetAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry4_budgetAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry4_budgetAmount_CellEditor = new KDTDefaultCellEditor(kdtEntry4_budgetAmount_TextField);
        this.kdtEntry4.getColumn("budgetAmount").setEditor(kdtEntry4_budgetAmount_CellEditor);
        KDFormattedTextField kdtEntry4_balance_TextField = new KDFormattedTextField();
        kdtEntry4_balance_TextField.setName("kdtEntry4_balance_TextField");
        kdtEntry4_balance_TextField.setVisible(true);
        kdtEntry4_balance_TextField.setEditable(true);
        kdtEntry4_balance_TextField.setHorizontalAlignment(2);
        kdtEntry4_balance_TextField.setDataType(1);
        	kdtEntry4_balance_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry4_balance_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry4_balance_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry4_balance_CellEditor = new KDTDefaultCellEditor(kdtEntry4_balance_TextField);
        this.kdtEntry4.getColumn("balance").setEditor(kdtEntry4_balance_CellEditor);
        KDFormattedTextField kdtEntry4_amount_TextField = new KDFormattedTextField();
        kdtEntry4_amount_TextField.setName("kdtEntry4_amount_TextField");
        kdtEntry4_amount_TextField.setVisible(true);
        kdtEntry4_amount_TextField.setEditable(true);
        kdtEntry4_amount_TextField.setHorizontalAlignment(2);
        kdtEntry4_amount_TextField.setDataType(1);
        	kdtEntry4_amount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry4_amount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry4_amount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry4_amount_CellEditor = new KDTDefaultCellEditor(kdtEntry4_amount_TextField);
        this.kdtEntry4.getColumn("amount").setEditor(kdtEntry4_amount_CellEditor);
        KDTextArea kdtEntry4_content_TextArea = new KDTextArea();
        kdtEntry4_content_TextArea.setName("kdtEntry4_content_TextArea");
        kdtEntry4_content_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtEntry4_content_CellEditor = new KDTDefaultCellEditor(kdtEntry4_content_TextArea);
        this.kdtEntry4.getColumn("content").setEditor(kdtEntry4_content_CellEditor);
        KDFormattedTextField kdtEntry4_lastAmount_TextField = new KDFormattedTextField();
        kdtEntry4_lastAmount_TextField.setName("kdtEntry4_lastAmount_TextField");
        kdtEntry4_lastAmount_TextField.setVisible(true);
        kdtEntry4_lastAmount_TextField.setEditable(true);
        kdtEntry4_lastAmount_TextField.setHorizontalAlignment(2);
        kdtEntry4_lastAmount_TextField.setDataType(1);
        	kdtEntry4_lastAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry4_lastAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry4_lastAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry4_lastAmount_CellEditor = new KDTDefaultCellEditor(kdtEntry4_lastAmount_TextField);
        this.kdtEntry4.getColumn("lastAmount").setEditor(kdtEntry4_lastAmount_CellEditor);
        KDTextArea kdtEntry4_beizhu_TextArea = new KDTextArea();
        kdtEntry4_beizhu_TextArea.setName("kdtEntry4_beizhu_TextArea");
        kdtEntry4_beizhu_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtEntry4_beizhu_CellEditor = new KDTDefaultCellEditor(kdtEntry4_beizhu_TextArea);
        this.kdtEntry4.getColumn("beizhu").setEditor(kdtEntry4_beizhu_CellEditor);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);
        // kdtEntry2
		String kdtEntry2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"evaEnterprise\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"comment\" t:width=\"500\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{evaEnterprise}</t:Cell><t:Cell>$Resource{comment}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry2.setFormatXml(resHelper.translateString("kdtEntry2",kdtEntry2StrXML));
        this.kdtEntry2.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEntry2_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

                this.kdtEntry2.putBindContents("editData",new String[] {"seq","evaEnterprise","comment"});


        this.kdtEntry2.checkParsed();
        final KDBizPromptBox kdtEntry2_evaEnterprise_PromptBox = new KDBizPromptBox();
        kdtEntry2_evaEnterprise_PromptBox.setQueryInfo("com.kingdee.eas.port.markesupplier.subill.app.MarketSupplierStockQuery");
        kdtEntry2_evaEnterprise_PromptBox.setVisible(true);
        kdtEntry2_evaEnterprise_PromptBox.setEditable(true);
        kdtEntry2_evaEnterprise_PromptBox.setDisplayFormat("$number$");
        kdtEntry2_evaEnterprise_PromptBox.setEditFormat("$number$");
        kdtEntry2_evaEnterprise_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry2_evaEnterprise_CellEditor = new KDTDefaultCellEditor(kdtEntry2_evaEnterprise_PromptBox);
        this.kdtEntry2.getColumn("evaEnterprise").setEditor(kdtEntry2_evaEnterprise_CellEditor);
        ObjectValueRender kdtEntry2_evaEnterprise_OVR = new ObjectValueRender();
        kdtEntry2_evaEnterprise_OVR.setFormat(new BizDataFormat("$supplierName$"));
        this.kdtEntry2.getColumn("evaEnterprise").setRenderer(kdtEntry2_evaEnterprise_OVR);
        KDTextField kdtEntry2_comment_TextField = new KDTextField();
        kdtEntry2_comment_TextField.setName("kdtEntry2_comment_TextField");
        kdtEntry2_comment_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry2_comment_CellEditor = new KDTDefaultCellEditor(kdtEntry2_comment_TextField);
        this.kdtEntry2.getColumn("comment").setEditor(kdtEntry2_comment_CellEditor);
        // kDContainer3		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));		
        this.kDContainer3.setEnableActive(false);
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));		
        this.kDContainer2.setEnableActive(false);
        // kdtEntry3
		String kdtEntry3StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"invitePerson\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"department\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"isLeader\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"comment\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{invitePerson}</t:Cell><t:Cell>$Resource{department}</t:Cell><t:Cell>$Resource{isLeader}</t:Cell><t:Cell>$Resource{comment}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry3.setFormatXml(resHelper.translateString("kdtEntry3",kdtEntry3StrXML));
        kdtEntry3.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtEntry3_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});


                this.kdtEntry3.putBindContents("editData",new String[] {"seq","invitePerson","department","isLeader","comment"});


        this.kdtEntry3.checkParsed();
        final KDBizPromptBox kdtEntry3_invitePerson_PromptBox = new KDBizPromptBox();
        kdtEntry3_invitePerson_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.base.app.JudgesQuery");
        kdtEntry3_invitePerson_PromptBox.setVisible(true);
        kdtEntry3_invitePerson_PromptBox.setEditable(true);
        kdtEntry3_invitePerson_PromptBox.setDisplayFormat("$number$");
        kdtEntry3_invitePerson_PromptBox.setEditFormat("$number$");
        kdtEntry3_invitePerson_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry3_invitePerson_CellEditor = new KDTDefaultCellEditor(kdtEntry3_invitePerson_PromptBox);
        this.kdtEntry3.getColumn("invitePerson").setEditor(kdtEntry3_invitePerson_CellEditor);
        ObjectValueRender kdtEntry3_invitePerson_OVR = new ObjectValueRender();
//        kdtEntry3_invitePerson_OVR.setFormat(new BizDataFormat("$personName$"));
        this.kdtEntry3.getColumn("invitePerson").setRenderer(kdtEntry3_invitePerson_OVR);
        			kdtEntry3_invitePerson_PromptBox.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.pm.base.client.JudgesListUI kdtEntry3_invitePerson_PromptBox_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (kdtEntry3_invitePerson_PromptBox_F7ListUI == null) {
					try {
						kdtEntry3_invitePerson_PromptBox_F7ListUI = new com.kingdee.eas.port.pm.base.client.JudgesListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(kdtEntry3_invitePerson_PromptBox_F7ListUI));
					kdtEntry3_invitePerson_PromptBox_F7ListUI.setF7Use(true,ctx);
					kdtEntry3_invitePerson_PromptBox.setSelector(kdtEntry3_invitePerson_PromptBox_F7ListUI);
				}
			}
		});
					
        KDTextField kdtEntry3_department_TextField = new KDTextField();
        kdtEntry3_department_TextField.setName("kdtEntry3_department_TextField");
        kdtEntry3_department_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry3_department_CellEditor = new KDTDefaultCellEditor(kdtEntry3_department_TextField);
        this.kdtEntry3.getColumn("department").setEditor(kdtEntry3_department_CellEditor);
        KDCheckBox kdtEntry3_isLeader_CheckBox = new KDCheckBox();
        kdtEntry3_isLeader_CheckBox.setName("kdtEntry3_isLeader_CheckBox");
        KDTDefaultCellEditor kdtEntry3_isLeader_CellEditor = new KDTDefaultCellEditor(kdtEntry3_isLeader_CheckBox);
        this.kdtEntry3.getColumn("isLeader").setEditor(kdtEntry3_isLeader_CellEditor);
        KDTextField kdtEntry3_comment_TextField = new KDTextField();
        kdtEntry3_comment_TextField.setName("kdtEntry3_comment_TextField");
        kdtEntry3_comment_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry3_comment_CellEditor = new KDTDefaultCellEditor(kdtEntry3_comment_TextField);
        this.kdtEntry3.getColumn("comment").setEditor(kdtEntry3_comment_CellEditor);
        // kdtEntry5
		String kdtEntry5StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"judgeType\" t:width=\"166\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"determineThenWay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{judgeType}</t:Cell><t:Cell>$Resource{determineThenWay}</t:Cell><t:Cell>$Resource{amount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry5.setFormatXml(resHelper.translateString("kdtEntry5",kdtEntry5StrXML));

                this.kdtEntry5.putBindContents("editData",new String[] {"seq","judgeType","determineThenWay","amount"});


        this.kdtEntry5.checkParsed();
        KDComboBox kdtEntry5_determineThenWay_ComboBox = new KDComboBox();
        kdtEntry5_determineThenWay_ComboBox.setName("kdtEntry5_determineThenWay_ComboBox");
        kdtEntry5_determineThenWay_ComboBox.setVisible(true);
        kdtEntry5_determineThenWay_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.pm.invite.determineThenWay").toArray());
        KDTDefaultCellEditor kdtEntry5_determineThenWay_CellEditor = new KDTDefaultCellEditor(kdtEntry5_determineThenWay_ComboBox);
        this.kdtEntry5.getColumn("determineThenWay").setEditor(kdtEntry5_determineThenWay_CellEditor);
        KDTextField kdtEntry5_amount_TextField = new KDTextField();
        kdtEntry5_amount_TextField.setName("kdtEntry5_amount_TextField");
        kdtEntry5_amount_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry5_amount_CellEditor = new KDTDefaultCellEditor(kdtEntry5_amount_TextField);
        this.kdtEntry5.getColumn("amount").setEditor(kdtEntry5_amount_CellEditor);
        // kDPanel5		
        this.kDPanel5.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel5.border.title")));
        // kDTabbedPane3
        // contrmhigh		
        this.contrmhigh.setBoundLabelText(resHelper.getString("contrmhigh.boundLabelText"));		
        this.contrmhigh.setBoundLabelLength(100);		
        this.contrmhigh.setBoundLabelUnderline(true);		
        this.contrmhigh.setVisible(true);
        // contrmlow		
        this.contrmlow.setBoundLabelText(resHelper.getString("contrmlow.boundLabelText"));		
        this.contrmlow.setBoundLabelLength(100);		
        this.contrmlow.setBoundLabelUnderline(true);		
        this.contrmlow.setVisible(true);
        // contbusinessScore		
        this.contbusinessScore.setBoundLabelText(resHelper.getString("contbusinessScore.boundLabelText"));		
        this.contbusinessScore.setBoundLabelLength(100);		
        this.contbusinessScore.setBoundLabelUnderline(true);		
        this.contbusinessScore.setVisible(true);
        // conttechScore		
        this.conttechScore.setBoundLabelText(resHelper.getString("conttechScore.boundLabelText"));		
        this.conttechScore.setBoundLabelLength(100);		
        this.conttechScore.setBoundLabelUnderline(true);		
        this.conttechScore.setVisible(true);
        // contreduHigh		
        this.contreduHigh.setBoundLabelText(resHelper.getString("contreduHigh.boundLabelText"));		
        this.contreduHigh.setBoundLabelLength(100);		
        this.contreduHigh.setBoundLabelUnderline(true);		
        this.contreduHigh.setVisible(true);
        // contreduLow		
        this.contreduLow.setBoundLabelText(resHelper.getString("contreduLow.boundLabelText"));		
        this.contreduLow.setBoundLabelLength(100);		
        this.contreduLow.setBoundLabelUnderline(true);		
        this.contreduLow.setVisible(true);
        // contcoefficient		
        this.contcoefficient.setBoundLabelText(resHelper.getString("contcoefficient.boundLabelText"));		
        this.contcoefficient.setBoundLabelLength(100);		
        this.contcoefficient.setBoundLabelUnderline(true);		
        this.contcoefficient.setVisible(true);
        // txtrmhigh		
        this.txtrmhigh.setHorizontalAlignment(2);		
        this.txtrmhigh.setMaxLength(100);		
        this.txtrmhigh.setRequired(false);
        // txtrmlow		
        this.txtrmlow.setHorizontalAlignment(2);		
        this.txtrmlow.setMaxLength(100);		
        this.txtrmlow.setRequired(false);
        // txtbusinessScore		
        this.txtbusinessScore.setHorizontalAlignment(2);		
        this.txtbusinessScore.setMaxLength(100);		
        this.txtbusinessScore.setRequired(false);
        // txttechScore		
        this.txttechScore.setHorizontalAlignment(2);		
        this.txttechScore.setMaxLength(100);		
        this.txttechScore.setRequired(false);
        // txtreduHigh		
        this.txtreduHigh.setHorizontalAlignment(2);		
        this.txtreduHigh.setMaxLength(100);		
        this.txtreduHigh.setRequired(false);
        // txtreduLow		
        this.txtreduLow.setHorizontalAlignment(2);		
        this.txtreduLow.setMaxLength(100);		
        this.txtreduLow.setRequired(false);
        // txtcoefficient		
        this.txtcoefficient.setHorizontalAlignment(2);		
        this.txtcoefficient.setDataType(1);		
        this.txtcoefficient.setSupportedEmpty(true);		
        this.txtcoefficient.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtcoefficient.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtcoefficient.setPrecision(2);		
        this.txtcoefficient.setRequired(false);
        // kDPanel8
        // kDPanel7
        // kDContainer7		
        this.kDContainer7.setEnableActive(false);		
        this.kDContainer7.setTitle(resHelper.getString("kDContainer7.title"));
        // kdtE7
		String kdtE7StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"EvaluationName\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"EvaluationType\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"weight\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"remake\" t:width=\"450\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{EvaluationName}</t:Cell><t:Cell>$Resource{EvaluationType}</t:Cell><t:Cell>$Resource{weight}</t:Cell><t:Cell>$Resource{remake}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE7.setFormatXml(resHelper.translateString("kdtE7",kdtE7StrXML));
        this.kdtE7.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtE7_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtE7.putBindContents("editData",new String[] {"seq","EvaluationName","EvaluationType","weight","remake"});


        this.kdtE7.checkParsed();
        KDTextField kdtE7_EvaluationName_TextField = new KDTextField();
        kdtE7_EvaluationName_TextField.setName("kdtE7_EvaluationName_TextField");
        kdtE7_EvaluationName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE7_EvaluationName_CellEditor = new KDTDefaultCellEditor(kdtE7_EvaluationName_TextField);
        this.kdtE7.getColumn("EvaluationName").setEditor(kdtE7_EvaluationName_CellEditor);
        KDTextField kdtE7_EvaluationType_TextField = new KDTextField();
        kdtE7_EvaluationType_TextField.setName("kdtE7_EvaluationType_TextField");
        kdtE7_EvaluationType_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE7_EvaluationType_CellEditor = new KDTDefaultCellEditor(kdtE7_EvaluationType_TextField);
        this.kdtE7.getColumn("EvaluationType").setEditor(kdtE7_EvaluationType_CellEditor);
        KDFormattedTextField kdtE7_weight_TextField = new KDFormattedTextField();
        kdtE7_weight_TextField.setName("kdtE7_weight_TextField");
        kdtE7_weight_TextField.setVisible(true);
        kdtE7_weight_TextField.setEditable(true);
        kdtE7_weight_TextField.setHorizontalAlignment(2);
        kdtE7_weight_TextField.setDataType(1);
        	kdtE7_weight_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtE7_weight_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtE7_weight_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtE7_weight_CellEditor = new KDTDefaultCellEditor(kdtE7_weight_TextField);
        this.kdtE7.getColumn("weight").setEditor(kdtE7_weight_CellEditor);
        KDTextField kdtE7_remake_TextField = new KDTextField();
        kdtE7_remake_TextField.setName("kdtE7_remake_TextField");
        kdtE7_remake_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE7_remake_CellEditor = new KDTDefaultCellEditor(kdtE7_remake_TextField);
        this.kdtE7.getColumn("remake").setEditor(kdtE7_remake_CellEditor);
        // kDContainer6		
        this.kDContainer6.setEnableActive(false);		
        this.kDContainer6.setTitle(resHelper.getString("kDContainer6.title"));
        // kdtE6
		String kdtE6StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"EvaluationName\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"evaluationNameTex\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"EvaluationType\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"weight\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"remake\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{EvaluationName}</t:Cell><t:Cell>$Resource{evaluationNameTex}</t:Cell><t:Cell>$Resource{EvaluationType}</t:Cell><t:Cell>$Resource{weight}</t:Cell><t:Cell>$Resource{remake}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE6.setFormatXml(resHelper.translateString("kdtE6",kdtE6StrXML));
        kdtE6.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtE6_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});


                this.kdtE6.putBindContents("editData",new String[] {"seq","EvaluationName","evaluationNameTex","EvaluationType","weight","remake"});


        this.kdtE6.checkParsed();
        final KDBizPromptBox kdtE6_EvaluationName_PromptBox = new KDBizPromptBox();
        kdtE6_EvaluationName_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.base.app.EvaluationIndicatorsQuery");
        kdtE6_EvaluationName_PromptBox.setVisible(true);
        kdtE6_EvaluationName_PromptBox.setEditable(true);
        kdtE6_EvaluationName_PromptBox.setDisplayFormat("$number$");
        kdtE6_EvaluationName_PromptBox.setEditFormat("$number$");
        kdtE6_EvaluationName_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE6_EvaluationName_CellEditor = new KDTDefaultCellEditor(kdtE6_EvaluationName_PromptBox);
        this.kdtE6.getColumn("EvaluationName").setEditor(kdtE6_EvaluationName_CellEditor);
        ObjectValueRender kdtE6_EvaluationName_OVR = new ObjectValueRender();
        kdtE6_EvaluationName_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE6.getColumn("EvaluationName").setRenderer(kdtE6_EvaluationName_OVR);
        			kdtE6_EvaluationName_PromptBox.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.pm.base.client.EvaluationIndicatorsListUI kdtE6_EvaluationName_PromptBox_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (kdtE6_EvaluationName_PromptBox_F7ListUI == null) {
					try {
						kdtE6_EvaluationName_PromptBox_F7ListUI = new com.kingdee.eas.port.pm.base.client.EvaluationIndicatorsListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(kdtE6_EvaluationName_PromptBox_F7ListUI));
					kdtE6_EvaluationName_PromptBox_F7ListUI.setF7Use(true,ctx);
					kdtE6_EvaluationName_PromptBox.setSelector(kdtE6_EvaluationName_PromptBox_F7ListUI);
				}
			}
		});
					
        KDTextField kdtE6_evaluationNameTex_TextField = new KDTextField();
        kdtE6_evaluationNameTex_TextField.setName("kdtE6_evaluationNameTex_TextField");
        kdtE6_evaluationNameTex_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE6_evaluationNameTex_CellEditor = new KDTDefaultCellEditor(kdtE6_evaluationNameTex_TextField);
        this.kdtE6.getColumn("evaluationNameTex").setEditor(kdtE6_evaluationNameTex_CellEditor);
        KDTextField kdtE6_EvaluationType_TextField = new KDTextField();
        kdtE6_EvaluationType_TextField.setName("kdtE6_EvaluationType_TextField");
        kdtE6_EvaluationType_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE6_EvaluationType_CellEditor = new KDTDefaultCellEditor(kdtE6_EvaluationType_TextField);
        this.kdtE6.getColumn("EvaluationType").setEditor(kdtE6_EvaluationType_CellEditor);
        KDFormattedTextField kdtE6_weight_TextField = new KDFormattedTextField();
        kdtE6_weight_TextField.setName("kdtE6_weight_TextField");
        kdtE6_weight_TextField.setVisible(true);
        kdtE6_weight_TextField.setEditable(true);
        kdtE6_weight_TextField.setHorizontalAlignment(2);
        kdtE6_weight_TextField.setDataType(1);
        	kdtE6_weight_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtE6_weight_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtE6_weight_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtE6_weight_CellEditor = new KDTDefaultCellEditor(kdtE6_weight_TextField);
        this.kdtE6.getColumn("weight").setEditor(kdtE6_weight_CellEditor);
        KDTextField kdtE6_remake_TextField = new KDTextField();
        kdtE6_remake_TextField.setName("kdtE6_remake_TextField");
        kdtE6_remake_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE6_remake_CellEditor = new KDTDefaultCellEditor(kdtE6_remake_TextField);
        this.kdtE6.getColumn("remake").setEditor(kdtE6_remake_CellEditor);
        // prmtinvitePlan		
        this.prmtinvitePlan.setQueryInfo("com.kingdee.eas.port.pm.invite.app.InvitePlanQuery");		
        this.prmtinvitePlan.setEditable(true);		
        this.prmtinvitePlan.setDisplayFormat("$planName$");		
        this.prmtinvitePlan.setEditFormat("$number$");		
        this.prmtinvitePlan.setCommitFormat("$number$");		
        this.prmtinvitePlan.setRequired(false);
        this.prmtinvitePlan.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtinvitePlan_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtprojectNumber		
        this.txtprojectNumber.setHorizontalAlignment(2);		
        this.txtprojectNumber.setMaxLength(80);		
        this.txtprojectNumber.setRequired(false);
        // prmtdevOrg		
        this.prmtdevOrg.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtdevOrg.setEditable(true);		
        this.prmtdevOrg.setDisplayFormat("$name$");		
        this.prmtdevOrg.setEditFormat("$number$");		
        this.prmtdevOrg.setCommitFormat("$number$");		
        this.prmtdevOrg.setRequired(false);
        // txtproSite		
        this.txtproSite.setHorizontalAlignment(2);		
        this.txtproSite.setMaxLength(100);		
        this.txtproSite.setRequired(false);
        // kdtEntry1
		String kdtEntry1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry1.setFormatXml(resHelper.translateString("kdtEntry1",kdtEntry1StrXML));

                this.kdtEntry1.putBindContents("editData",new String[] {"seq"});


        this.kdtEntry1.checkParsed();
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtQualificationRequemt,txtBIMUDF0004,comboStatus,comboBizStatus,pkAuditTime,txtNumber,pkBizDate,txtDescription,prmtAuditor,prmtCreator,pkCreateTime,prmtLastUpdateUser,pkLastUpdateTime,prmtCU,prmtdevOrg,prmtuseOrg,txtinviteBudget,prmtinviteType,pkinviteDate,pkopenDate,pksubDate,prmtapplicant,pkaudDate,txtpaperFee,txtbidBond,judgeSolution,txtproSite,prmtevaTemplate,txtreportName,prmtvalidTemplate,txtrmhigh,txtrmlow,txtbusinessScore,txttechScore,txtreduHigh,txtreduLow,prmtproName,txtprojectNumber,txtcoefficient,prmtinvitePlan,kdtEntry3,kdtEntry2,kdtEntry1,kdtEntry5,kdtEntry4,kdtE6,kdtE7}));
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
        this.setBounds(new Rectangle(10, 10, 1026, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1026, 629));
        contAuditor.setBounds(new Rectangle(752, 583, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(752, 583, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(752, 606, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(752, 606, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateUser.setBounds(new Rectangle(380, 583, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(380, 583, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(380, 606, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(380, 606, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(9, 583, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(9, 583, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(9, 606, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(9, 606, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(20, 633, 270, 19));
        this.add(contCU, new KDLayout.Constraints(20, 633, 270, 19, 0));
        contBizStatus.setBounds(new Rectangle(20, 640, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(20, 640, 270, 19, 0));
        contBizDate.setBounds(new Rectangle(23, 668, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(23, 668, 270, 19, 0));
        contDescription.setBounds(new Rectangle(23, 693, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(23, 693, 270, 19, 0));
        kDTabbedPane1.setBounds(new Rectangle(3, 4, 1019, 574));
        this.add(kDTabbedPane1, new KDLayout.Constraints(3, 4, 1019, 574, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contprojectNumber.setBounds(new Rectangle(1056, 470, 270, 19));
        this.add(contprojectNumber, new KDLayout.Constraints(1056, 470, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contdevOrg.setBounds(new Rectangle(1056, 486, 270, 19));
        this.add(contdevOrg, new KDLayout.Constraints(1056, 486, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contproSite.setBounds(new Rectangle(1056, 497, 270, 19));
        this.add(contproSite, new KDLayout.Constraints(1056, 497, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer4.setBounds(new Rectangle(1057, 398, 88, 73));
        this.add(kDContainer4, new KDLayout.Constraints(1057, 398, 88, 73, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contCU
        contCU.setBoundEditor(prmtCU);
        //contBizStatus
        contBizStatus.setBoundEditor(comboBizStatus);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel6, resHelper.getString("kDPanel6.constraints"));
        //kDPanel6
        kDPanel6.setLayout(new KDLayout());
        kDPanel6.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1018, 541));        continviteBudget.setBounds(new Rectangle(8, 31, 270, 19));
        kDPanel6.add(continviteBudget, new KDLayout.Constraints(8, 31, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contaudDate.setBounds(new Rectangle(371, 97, 270, 19));
        kDPanel6.add(contaudDate, new KDLayout.Constraints(371, 97, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contapplicant.setBounds(new Rectangle(8, 75, 270, 19));
        kDPanel6.add(contapplicant, new KDLayout.Constraints(8, 75, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpaperFee.setBounds(new Rectangle(651, 137, 270, 19));
        kDPanel6.add(contpaperFee, new KDLayout.Constraints(651, 137, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contbidBond.setBounds(new Rectangle(734, 97, 270, 19));
        kDPanel6.add(contbidBond, new KDLayout.Constraints(734, 97, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continviteType.setBounds(new Rectangle(734, 53, 270, 19));
        kDPanel6.add(continviteType, new KDLayout.Constraints(734, 53, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contjudgeSolution.setBounds(new Rectangle(734, 31, 270, 19));
        kDPanel6.add(contjudgeSolution, new KDLayout.Constraints(734, 31, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contevaTemplate.setBounds(new Rectangle(371, 53, 270, 19));
        kDPanel6.add(contevaTemplate, new KDLayout.Constraints(371, 53, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contvalidTemplate.setBounds(new Rectangle(8, 53, 270, 19));
        kDPanel6.add(contvalidTemplate, new KDLayout.Constraints(8, 53, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continviteDate.setBounds(new Rectangle(371, 75, 270, 19));
        kDPanel6.add(continviteDate, new KDLayout.Constraints(371, 75, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsubDate.setBounds(new Rectangle(368, 126, 270, 19));
        kDPanel6.add(contsubDate, new KDLayout.Constraints(368, 126, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contopenDate.setBounds(new Rectangle(8, 97, 270, 19));
        kDPanel6.add(contopenDate, new KDLayout.Constraints(8, 97, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBIMUDF0004.setBounds(new Rectangle(8, 143, 997, 91));
        kDPanel6.add(contBIMUDF0004, new KDLayout.Constraints(8, 143, 997, 91, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(8, 9, 270, 19));
        kDPanel6.add(contNumber, new KDLayout.Constraints(8, 9, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contreportName.setBounds(new Rectangle(371, 9, 270, 19));
        kDPanel6.add(contreportName, new KDLayout.Constraints(371, 9, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contproName.setBounds(new Rectangle(734, 9, 270, 19));
        kDPanel6.add(contproName, new KDLayout.Constraints(734, 9, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contuseOrg.setBounds(new Rectangle(734, 75, 270, 19));
        kDPanel6.add(contuseOrg, new KDLayout.Constraints(734, 75, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStatus.setBounds(new Rectangle(734, 121, 270, 19));
        kDPanel6.add(contStatus, new KDLayout.Constraints(734, 121, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDButton1.setBounds(new Rectangle(645, 7, 84, 21));
        kDPanel6.add(kDButton1, new KDLayout.Constraints(645, 7, 84, 21, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contQualificationRequemt.setBounds(new Rectangle(8, 121, 633, 19));
        kDPanel6.add(contQualificationRequemt, new KDLayout.Constraints(8, 121, 633, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane2.setBounds(new Rectangle(8, 236, 997, 301));
        kDPanel6.add(kDTabbedPane2, new KDLayout.Constraints(8, 236, 997, 301, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        continvitePlan.setBounds(new Rectangle(371, 31, 270, 19));
        kDPanel6.add(continvitePlan, new KDLayout.Constraints(371, 31, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //continviteBudget
        continviteBudget.setBoundEditor(txtinviteBudget);
        //contaudDate
        contaudDate.setBoundEditor(pkaudDate);
        //contapplicant
        contapplicant.setBoundEditor(prmtapplicant);
        //contpaperFee
        contpaperFee.setBoundEditor(txtpaperFee);
        //contbidBond
        contbidBond.setBoundEditor(txtbidBond);
        //continviteType
        continviteType.setBoundEditor(prmtinviteType);
        //contjudgeSolution
        contjudgeSolution.setBoundEditor(judgeSolution);
        //contevaTemplate
        contevaTemplate.setBoundEditor(prmtevaTemplate);
        //contvalidTemplate
        contvalidTemplate.setBoundEditor(prmtvalidTemplate);
        //continviteDate
        continviteDate.setBoundEditor(pkinviteDate);
        //contsubDate
        contsubDate.setBoundEditor(pksubDate);
        //contopenDate
        contopenDate.setBoundEditor(pkopenDate);
        //contBIMUDF0004
        contBIMUDF0004.setBoundEditor(scrollPaneBIMUDF0004);
        //scrollPaneBIMUDF0004
        scrollPaneBIMUDF0004.getViewport().add(txtBIMUDF0004, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contreportName
        contreportName.setBoundEditor(txtreportName);
        //contproName
        contproName.setBoundEditor(prmtproName);
        //contuseOrg
        contuseOrg.setBoundEditor(prmtuseOrg);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //contQualificationRequemt
        contQualificationRequemt.setBoundEditor(txtQualificationRequemt);
        //kDTabbedPane2
        kDTabbedPane2.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane2.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane2.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
        kDTabbedPane2.add(kDPanel4, resHelper.getString("kDPanel4.constraints"));
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(kDContainer5, BorderLayout.CENTER);
        //kDContainer5
kDContainer5.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntry4_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry4,new com.kingdee.eas.port.pm.invite.InviteReportEntry4Info(),null,false);
        kDContainer5.getContentPane().add(kdtEntry4_detailPanel, BorderLayout.CENTER);
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kDPanel2.add(kDContainer1, BorderLayout.CENTER);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntry2_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry2,new com.kingdee.eas.port.pm.invite.InviteReportEntry2Info(),null,false);
        kDContainer1.getContentPane().add(kdtEntry2_detailPanel, BorderLayout.CENTER);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(0, 0, 996, 268));        kDContainer3.setBounds(new Rectangle(2, 2, 985, 140));
        kDPanel3.add(kDContainer3, new KDLayout.Constraints(2, 2, 985, 140, KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer2.setBounds(new Rectangle(2, 144, 985, 121));
        kDPanel3.add(kDContainer2, new KDLayout.Constraints(2, 144, 985, 121, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntry3_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry3,new com.kingdee.eas.port.pm.invite.InviteReportEntry3Info(),null,false);
        kDContainer3.getContentPane().add(kdtEntry3_detailPanel, BorderLayout.CENTER);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntry5_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry5,new com.kingdee.eas.port.pm.invite.InviteReportEntry5Info(),null,false);
        kDContainer2.getContentPane().add(kdtEntry5_detailPanel, BorderLayout.CENTER);
		kdtEntry5_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("determineThenWay","extracting");
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        //kDPanel4
        kDPanel4.setLayout(new KDLayout());
        kDPanel4.putClientProperty("OriginalBounds", new Rectangle(0, 0, 996, 268));        kDPanel5.setBounds(new Rectangle(3, 1, 986, 72));
        kDPanel4.add(kDPanel5, new KDLayout.Constraints(3, 1, 986, 72, KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane3.setBounds(new Rectangle(3, 74, 986, 193));
        kDPanel4.add(kDTabbedPane3, new KDLayout.Constraints(3, 74, 986, 193, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel5
        kDPanel5.setLayout(new KDLayout());
        kDPanel5.putClientProperty("OriginalBounds", new Rectangle(3, 1, 986, 72));        contrmhigh.setBounds(new Rectangle(24, 15, 270, 19));
        kDPanel5.add(contrmhigh, new KDLayout.Constraints(24, 15, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrmlow.setBounds(new Rectangle(360, 15, 270, 19));
        kDPanel5.add(contrmlow, new KDLayout.Constraints(360, 15, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbusinessScore.setBounds(new Rectangle(360, 38, 185, 19));
        kDPanel5.add(contbusinessScore, new KDLayout.Constraints(360, 38, 185, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttechScore.setBounds(new Rectangle(570, 38, 185, 19));
        kDPanel5.add(conttechScore, new KDLayout.Constraints(570, 38, 185, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contreduHigh.setBounds(new Rectangle(696, 15, 270, 19));
        kDPanel5.add(contreduHigh, new KDLayout.Constraints(696, 15, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contreduLow.setBounds(new Rectangle(24, 38, 270, 19));
        kDPanel5.add(contreduLow, new KDLayout.Constraints(24, 38, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcoefficient.setBounds(new Rectangle(781, 38, 185, 19));
        kDPanel5.add(contcoefficient, new KDLayout.Constraints(781, 38, 185, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contrmhigh
        contrmhigh.setBoundEditor(txtrmhigh);
        //contrmlow
        contrmlow.setBoundEditor(txtrmlow);
        //contbusinessScore
        contbusinessScore.setBoundEditor(txtbusinessScore);
        //conttechScore
        conttechScore.setBoundEditor(txttechScore);
        //contreduHigh
        contreduHigh.setBoundEditor(txtreduHigh);
        //contreduLow
        contreduLow.setBoundEditor(txtreduLow);
        //contcoefficient
        contcoefficient.setBoundEditor(txtcoefficient);
        //kDTabbedPane3
        kDTabbedPane3.add(kDPanel8, resHelper.getString("kDPanel8.constraints"));
        kDTabbedPane3.add(kDPanel7, resHelper.getString("kDPanel7.constraints"));
        //kDPanel8
kDPanel8.setLayout(new BorderLayout(0, 0));        kDPanel8.add(kDContainer7, BorderLayout.CENTER);
        //kDContainer7
kDContainer7.getContentPane().setLayout(new BorderLayout(0, 0));        kdtE7_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE7,new com.kingdee.eas.port.pm.invite.InviteReportE7Info(),null,false);
        kDContainer7.getContentPane().add(kdtE7_detailPanel, BorderLayout.CENTER);
        //kDPanel7
kDPanel7.setLayout(new BorderLayout(0, 0));        kDPanel7.add(kDContainer6, BorderLayout.CENTER);
        //kDContainer6
kDContainer6.getContentPane().setLayout(new BorderLayout(0, 0));        kdtE6_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE6,new com.kingdee.eas.port.pm.invite.InviteReportE6Info(),null,false);
        kDContainer6.getContentPane().add(kdtE6_detailPanel, BorderLayout.CENTER);
        //continvitePlan
        continvitePlan.setBoundEditor(prmtinvitePlan);
        //contprojectNumber
        contprojectNumber.setBoundEditor(txtprojectNumber);
        //contdevOrg
        contdevOrg.setBoundEditor(prmtdevOrg);
        //contproSite
        contproSite.setBoundEditor(txtproSite);
        //kDContainer4
kDContainer4.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntry1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry1,new com.kingdee.eas.port.pm.invite.InviteReportEntry1Info(),null,false);
        kDContainer4.getContentPane().add(kdtEntry1_detailPanel, BorderLayout.CENTER);

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
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("inviteBudget", String.class, this.txtinviteBudget, "text");
		dataBinder.registerBinding("audDate", java.util.Date.class, this.pkaudDate, "value");
		dataBinder.registerBinding("applicant", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtapplicant, "data");
		dataBinder.registerBinding("paperFee", String.class, this.txtpaperFee, "text");
		dataBinder.registerBinding("bidBond", String.class, this.txtbidBond, "text");
		dataBinder.registerBinding("inviteType", com.kingdee.eas.port.pm.base.InviteTypeInfo.class, this.prmtinviteType, "data");
		dataBinder.registerBinding("judgeSolution", com.kingdee.eas.port.pm.invite.judgeSolution.class, this.judgeSolution, "selectedItem");
		dataBinder.registerBinding("evaTemplate", com.kingdee.eas.port.pm.base.EvaluationTemplateInfo.class, this.prmtevaTemplate, "data");
		dataBinder.registerBinding("validTemplate", com.kingdee.eas.port.pm.base.EvaluationTemplateInfo.class, this.prmtvalidTemplate, "data");
		dataBinder.registerBinding("inviteDate", java.util.Date.class, this.pkinviteDate, "value");
		dataBinder.registerBinding("subDate", java.util.Date.class, this.pksubDate, "value");
		dataBinder.registerBinding("openDate", java.util.Date.class, this.pkopenDate, "value");
		dataBinder.registerBinding("BIMUDF0004", String.class, this.txtBIMUDF0004, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("reportName", String.class, this.txtreportName, "text");
		dataBinder.registerBinding("proName", com.kingdee.eas.port.pm.base.EvaluationIndicatorsInfo.class, this.prmtproName, "data");
		dataBinder.registerBinding("useOrg", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtuseOrg, "data");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("QualificationRequemt", String.class, this.txtQualificationRequemt, "text");
		dataBinder.registerBinding("Entry4.seq", int.class, this.kdtEntry4, "seq.text");
		dataBinder.registerBinding("Entry4", com.kingdee.eas.port.pm.invite.InviteReportEntry4Info.class, this.kdtEntry4, "userObject");
		dataBinder.registerBinding("Entry4.budgetAmount", java.math.BigDecimal.class, this.kdtEntry4, "budgetAmount.text");
		dataBinder.registerBinding("Entry4.balance", java.math.BigDecimal.class, this.kdtEntry4, "balance.text");
		dataBinder.registerBinding("Entry4.content", String.class, this.kdtEntry4, "content.text");
		dataBinder.registerBinding("Entry4.lastAmount", java.math.BigDecimal.class, this.kdtEntry4, "lastAmount.text");
		dataBinder.registerBinding("Entry4.beizhu", String.class, this.kdtEntry4, "beizhu.text");
		dataBinder.registerBinding("Entry4.amount", java.math.BigDecimal.class, this.kdtEntry4, "amount.text");
		dataBinder.registerBinding("Entry4.budgetNumber", java.lang.Object.class, this.kdtEntry4, "budgetNumber.text");
		dataBinder.registerBinding("Entry4.budgetName", String.class, this.kdtEntry4, "budgetName.text");
		dataBinder.registerBinding("Entry2.seq", int.class, this.kdtEntry2, "seq.text");
		dataBinder.registerBinding("Entry2", com.kingdee.eas.port.pm.invite.InviteReportEntry2Info.class, this.kdtEntry2, "userObject");
		dataBinder.registerBinding("Entry2.comment", String.class, this.kdtEntry2, "comment.text");
		dataBinder.registerBinding("Entry2.evaEnterprise", java.lang.Object.class, this.kdtEntry2, "evaEnterprise.text");
		dataBinder.registerBinding("Entry3.seq", int.class, this.kdtEntry3, "seq.text");
		dataBinder.registerBinding("Entry3", com.kingdee.eas.port.pm.invite.InviteReportEntry3Info.class, this.kdtEntry3, "userObject");
		dataBinder.registerBinding("Entry3.isLeader", boolean.class, this.kdtEntry3, "isLeader.text");
		dataBinder.registerBinding("Entry3.comment", String.class, this.kdtEntry3, "comment.text");
		dataBinder.registerBinding("Entry3.invitePerson", java.lang.Object.class, this.kdtEntry3, "invitePerson.text");
		dataBinder.registerBinding("Entry3.department", String.class, this.kdtEntry3, "department.text");
		dataBinder.registerBinding("Entry5.seq", int.class, this.kdtEntry5, "seq.text");
		dataBinder.registerBinding("Entry5", com.kingdee.eas.port.pm.invite.InviteReportEntry5Info.class, this.kdtEntry5, "userObject");
		dataBinder.registerBinding("Entry5.amount", String.class, this.kdtEntry5, "amount.text");
		dataBinder.registerBinding("Entry5.judgeType", java.lang.Object.class, this.kdtEntry5, "judgeType.text");
		dataBinder.registerBinding("Entry5.determineThenWay", com.kingdee.util.enums.Enum.class, this.kdtEntry5, "determineThenWay.text");
		dataBinder.registerBinding("rmhigh", String.class, this.txtrmhigh, "text");
		dataBinder.registerBinding("rmlow", String.class, this.txtrmlow, "text");
		dataBinder.registerBinding("businessScore", String.class, this.txtbusinessScore, "text");
		dataBinder.registerBinding("techScore", String.class, this.txttechScore, "text");
		dataBinder.registerBinding("reduHigh", String.class, this.txtreduHigh, "text");
		dataBinder.registerBinding("reduLow", String.class, this.txtreduLow, "text");
		dataBinder.registerBinding("coefficient", java.math.BigDecimal.class, this.txtcoefficient, "value");
		dataBinder.registerBinding("E7.seq", int.class, this.kdtE7, "seq.text");
		dataBinder.registerBinding("E7", com.kingdee.eas.port.pm.invite.InviteReportE7Info.class, this.kdtE7, "userObject");
		dataBinder.registerBinding("E7.EvaluationName", String.class, this.kdtE7, "EvaluationName.text");
		dataBinder.registerBinding("E7.EvaluationType", String.class, this.kdtE7, "EvaluationType.text");
		dataBinder.registerBinding("E7.weight", java.math.BigDecimal.class, this.kdtE7, "weight.text");
		dataBinder.registerBinding("E7.remake", String.class, this.kdtE7, "remake.text");
		dataBinder.registerBinding("E6.seq", int.class, this.kdtE6, "seq.text");
		dataBinder.registerBinding("E6", com.kingdee.eas.port.pm.invite.InviteReportE6Info.class, this.kdtE6, "userObject");
		dataBinder.registerBinding("E6.EvaluationName", java.lang.Object.class, this.kdtE6, "EvaluationName.text");
		dataBinder.registerBinding("E6.EvaluationType", String.class, this.kdtE6, "EvaluationType.text");
		dataBinder.registerBinding("E6.weight", java.math.BigDecimal.class, this.kdtE6, "weight.text");
		dataBinder.registerBinding("E6.remake", String.class, this.kdtE6, "remake.text");
		dataBinder.registerBinding("E6.evaluationNameTex", String.class, this.kdtE6, "evaluationNameTex.text");
		dataBinder.registerBinding("invitePlan", com.kingdee.eas.port.pm.invite.InvitePlanInfo.class, this.prmtinvitePlan, "data");
		dataBinder.registerBinding("projectNumber", String.class, this.txtprojectNumber, "text");
		dataBinder.registerBinding("devOrg", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtdevOrg, "data");
		dataBinder.registerBinding("proSite", String.class, this.txtproSite, "text");
		dataBinder.registerBinding("Entry1.seq", int.class, this.kdtEntry1, "seq.text");
		dataBinder.registerBinding("Entry1", com.kingdee.eas.port.pm.invite.InviteReportEntry1Info.class, this.kdtEntry1, "userObject");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invite.app.InviteReportEditUIHandler";
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
        this.txtQualificationRequemt.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.pm.invite.InviteReportInfo)ov;
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
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteBudget", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("audDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("applicant", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paperFee", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bidBond", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("judgeSolution", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("evaTemplate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("validTemplate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("subDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("openDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BIMUDF0004", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reportName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("proName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("useOrg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("QualificationRequemt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry4.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry4", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry4.budgetAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry4.balance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry4.content", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry4.lastAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry4.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry4.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry4.budgetNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry4.budgetName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry2.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry2.comment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry2.evaEnterprise", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry3.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry3", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry3.isLeader", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry3.comment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry3.invitePerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry3.department", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry5.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry5", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry5.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry5.judgeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry5.determineThenWay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rmhigh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("rmlow", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("businessScore", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("techScore", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reduHigh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reduLow", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("coefficient", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E7.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E7", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E7.EvaluationName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E7.EvaluationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E7.weight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E7.remake", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E6.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E6", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E6.EvaluationName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E6.EvaluationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E6.weight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E6.remake", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E6.evaluationNameTex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invitePlan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("devOrg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("proSite", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry1.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry1", ValidateHelper.ON_SAVE);    		
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
     * output prmtinviteType_dataChanged method
     */
    protected void prmtinviteType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output judgeSolution_actionPerformed method
     */
    protected void judgeSolution_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output prmtevaTemplate_dataChanged method
     */
    protected void prmtevaTemplate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output prmtvalidTemplate_dataChanged method
     */
    protected void prmtvalidTemplate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output kdtEntry2_tableClicked method
     */
    protected void kdtEntry2_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here11
    }

    /**
     * output kdtE7_editStopped method
     */
    protected void kdtE7_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here1
    }

    /**
     * output prmtinvitePlan_dataChanged method
     */
    protected void prmtinvitePlan_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1
    }


    /**
     * output prmtproName_Changed() method
     */
    public void prmtproName_Changed() throws Exception
    {
        System.out.println("prmtproName_Changed() Function is executed!");
            txtprojectNumber.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtproName.getData(),"number")));


    }

    /**
     * output kdtEntry4_Changed(int rowIndex,int colIndex) method
     */
    public void kdtEntry4_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("budgetNumber".equalsIgnoreCase(kdtEntry4.getColumn(colIndex).getKey())) {
kdtEntry4.getCell(rowIndex,"budgetName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry4.getCell(rowIndex,"budgetNumber").getValue(),"feeName")));

}


    }

    /**
     * output kdtEntry3_Changed(int rowIndex,int colIndex) method
     */
    public void kdtEntry3_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("invitePerson".equalsIgnoreCase(kdtEntry3.getColumn(colIndex).getKey())) {
kdtEntry3.getCell(rowIndex,"department").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry3.getCell(rowIndex,"invitePerson").getValue(),"curDep.name")));

}


    }

    /**
     * output kdtE6_Changed(int rowIndex,int colIndex) method
     */
    public void kdtE6_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("EvaluationName".equalsIgnoreCase(kdtE6.getColumn(colIndex).getKey())) {
kdtE6.getCell(rowIndex,"EvaluationType").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE6.getCell(rowIndex,"EvaluationName").getValue(),"evalType")));

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
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("auditTime"));
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
			sic.add(new SelectorItemInfo("CU.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("CU.id"));
        	sic.add(new SelectorItemInfo("CU.number"));
        	sic.add(new SelectorItemInfo("CU.name"));
		}
        sic.add(new SelectorItemInfo("bizStatus"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("inviteBudget"));
        sic.add(new SelectorItemInfo("audDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("applicant.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("applicant.id"));
        	sic.add(new SelectorItemInfo("applicant.number"));
        	sic.add(new SelectorItemInfo("applicant.name"));
		}
        sic.add(new SelectorItemInfo("paperFee"));
        sic.add(new SelectorItemInfo("bidBond"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("inviteType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("inviteType.id"));
        	sic.add(new SelectorItemInfo("inviteType.number"));
        	sic.add(new SelectorItemInfo("inviteType.name"));
		}
        sic.add(new SelectorItemInfo("judgeSolution"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("evaTemplate.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("evaTemplate.id"));
        	sic.add(new SelectorItemInfo("evaTemplate.number"));
        	sic.add(new SelectorItemInfo("evaTemplate.name"));
        	sic.add(new SelectorItemInfo("evaTemplate.templateName"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("validTemplate.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("validTemplate.id"));
        	sic.add(new SelectorItemInfo("validTemplate.number"));
        	sic.add(new SelectorItemInfo("validTemplate.name"));
        	sic.add(new SelectorItemInfo("validTemplate.templateName"));
		}
        sic.add(new SelectorItemInfo("inviteDate"));
        sic.add(new SelectorItemInfo("subDate"));
        sic.add(new SelectorItemInfo("openDate"));
        sic.add(new SelectorItemInfo("BIMUDF0004"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("reportName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("proName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("proName.id"));
        	sic.add(new SelectorItemInfo("proName.number"));
        	sic.add(new SelectorItemInfo("proName.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("useOrg.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("useOrg.id"));
        	sic.add(new SelectorItemInfo("useOrg.number"));
        	sic.add(new SelectorItemInfo("useOrg.name"));
		}
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("QualificationRequemt"));
    	sic.add(new SelectorItemInfo("Entry4.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry4.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("Entry4.budgetAmount"));
    	sic.add(new SelectorItemInfo("Entry4.balance"));
    	sic.add(new SelectorItemInfo("Entry4.content"));
    	sic.add(new SelectorItemInfo("Entry4.lastAmount"));
    	sic.add(new SelectorItemInfo("Entry4.beizhu"));
    	sic.add(new SelectorItemInfo("Entry4.amount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry4.budgetNumber.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Entry4.budgetNumber.id"));
			sic.add(new SelectorItemInfo("Entry4.budgetNumber.feeNumber"));
        	sic.add(new SelectorItemInfo("Entry4.budgetNumber.number"));
		}
    	sic.add(new SelectorItemInfo("Entry4.budgetName"));
    	sic.add(new SelectorItemInfo("Entry2.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry2.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("Entry2.comment"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry2.evaEnterprise.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Entry2.evaEnterprise.id"));
			sic.add(new SelectorItemInfo("Entry2.evaEnterprise.supplierName"));
        	sic.add(new SelectorItemInfo("Entry2.evaEnterprise.number"));
		}
    	sic.add(new SelectorItemInfo("Entry3.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry3.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("Entry3.isLeader"));
    	sic.add(new SelectorItemInfo("Entry3.comment"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry3.invitePerson.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Entry3.invitePerson.id"));
			sic.add(new SelectorItemInfo("Entry3.invitePerson.personName"));
			sic.add(new SelectorItemInfo("Entry3.invitePerson.name"));
        	sic.add(new SelectorItemInfo("Entry3.invitePerson.number"));
		}
    	sic.add(new SelectorItemInfo("Entry3.department"));
    	sic.add(new SelectorItemInfo("Entry5.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry5.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("Entry5.amount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry5.judgeType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Entry5.judgeType.id"));
			sic.add(new SelectorItemInfo("Entry5.judgeType.name"));
        	sic.add(new SelectorItemInfo("Entry5.judgeType.number"));
		}
    	sic.add(new SelectorItemInfo("Entry5.determineThenWay"));
        sic.add(new SelectorItemInfo("rmhigh"));
        sic.add(new SelectorItemInfo("rmlow"));
        sic.add(new SelectorItemInfo("businessScore"));
        sic.add(new SelectorItemInfo("techScore"));
        sic.add(new SelectorItemInfo("reduHigh"));
        sic.add(new SelectorItemInfo("reduLow"));
        sic.add(new SelectorItemInfo("coefficient"));
    	sic.add(new SelectorItemInfo("E7.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E7.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E7.EvaluationName"));
    	sic.add(new SelectorItemInfo("E7.EvaluationType"));
    	sic.add(new SelectorItemInfo("E7.weight"));
    	sic.add(new SelectorItemInfo("E7.remake"));
    	sic.add(new SelectorItemInfo("E6.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E6.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E6.EvaluationName.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E6.EvaluationName.id"));
			sic.add(new SelectorItemInfo("E6.EvaluationName.name"));
        	sic.add(new SelectorItemInfo("E6.EvaluationName.number"));
		}
    	sic.add(new SelectorItemInfo("E6.EvaluationType"));
    	sic.add(new SelectorItemInfo("E6.weight"));
    	sic.add(new SelectorItemInfo("E6.remake"));
    	sic.add(new SelectorItemInfo("E6.evaluationNameTex"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("invitePlan.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("invitePlan.id"));
        	sic.add(new SelectorItemInfo("invitePlan.number"));
        	sic.add(new SelectorItemInfo("invitePlan.planName"));
		}
        sic.add(new SelectorItemInfo("projectNumber"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("devOrg.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("devOrg.id"));
        	sic.add(new SelectorItemInfo("devOrg.number"));
        	sic.add(new SelectorItemInfo("devOrg.name"));
		}
        sic.add(new SelectorItemInfo("proSite"));
    	sic.add(new SelectorItemInfo("Entry1.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry1.*"));
		}
		else{
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
        return new MetaDataPK("com.kingdee.eas.port.pm.invite.client", "InviteReportEditUI");
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
        return com.kingdee.eas.port.pm.invite.client.InviteReportEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invite.InviteReportFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invite.InviteReportInfo objectValue = new com.kingdee.eas.port.pm.invite.InviteReportInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/invite/InviteReport";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.invite.app.InviteReportQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntry4;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("judgeSolution","1");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}