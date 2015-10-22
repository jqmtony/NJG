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
public abstract class AbstractWinInviteReportEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractWinInviteReportEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continviteReport;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continviteDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaddress;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contevaSolution;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contevaDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continvitePrjName;
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
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinviteReport;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanecontent;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtcontent;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinviteType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkinviteDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtaddress;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtUnit;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtUnit_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbudgetAmount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtBudgetEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtBudgetEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continvitedAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contwinInviteUnit;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtbudgetAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtinvitedAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtwinInviteUnit;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtJudges;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtJudges_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDComboBox evaSolution;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkevaDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtinvitePrjName;
    protected com.kingdee.eas.port.pm.invite.WinInviteReportInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractWinInviteReportEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractWinInviteReportEditUI.class.getName());
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
        //actionAudit
        actionAudit.setEnabled(true);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionAudit.setBindWorkFlow(true);
        this.actionAudit.setExtendProperty("userDefined", "true");
        this.actionAudit.setExtendProperty("isObjectUpdateLock", "false");
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.WorkFlowService());
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
        this.continviteReport = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcontent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continviteDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contevaSolution = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contevaDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continvitePrjName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.prmtinviteReport = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.scrollPanecontent = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtcontent = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtinviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkinviteDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtaddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtUnit = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contbudgetAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtBudgetEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.continvitedAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contwinInviteUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtbudgetAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtinvitedAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtwinInviteUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtJudges = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.evaSolution = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkevaDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtinvitePrjName = new com.kingdee.bos.ctrl.swing.KDTextField();
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
        this.continviteReport.setName("continviteReport");
        this.contcontent.setName("contcontent");
        this.continviteType.setName("continviteType");
        this.continviteDate.setName("continviteDate");
        this.contaddress.setName("contaddress");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contevaSolution.setName("contevaSolution");
        this.contevaDate.setName("contevaDate");
        this.continvitePrjName.setName("continvitePrjName");
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
        this.prmtinviteReport.setName("prmtinviteReport");
        this.scrollPanecontent.setName("scrollPanecontent");
        this.txtcontent.setName("txtcontent");
        this.prmtinviteType.setName("prmtinviteType");
        this.pkinviteDate.setName("pkinviteDate");
        this.txtaddress.setName("txtaddress");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kdtUnit.setName("kdtUnit");
        this.contbudgetAmount.setName("contbudgetAmount");
        this.kdtBudgetEntry.setName("kdtBudgetEntry");
        this.continvitedAmount.setName("continvitedAmount");
        this.contwinInviteUnit.setName("contwinInviteUnit");
        this.txtbudgetAmount.setName("txtbudgetAmount");
        this.txtinvitedAmount.setName("txtinvitedAmount");
        this.prmtwinInviteUnit.setName("prmtwinInviteUnit");
        this.kdtJudges.setName("kdtJudges");
        this.evaSolution.setName("evaSolution");
        this.pkevaDate.setName("pkevaDate");
        this.txtinvitePrjName.setName("txtinvitePrjName");
        // CoreUI		
        this.btnCreateTo.setVisible(true);
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
        // continviteReport		
        this.continviteReport.setBoundLabelText(resHelper.getString("continviteReport.boundLabelText"));		
        this.continviteReport.setBoundLabelLength(100);		
        this.continviteReport.setBoundLabelUnderline(true);		
        this.continviteReport.setVisible(true);
        // contcontent		
        this.contcontent.setBoundLabelText(resHelper.getString("contcontent.boundLabelText"));		
        this.contcontent.setBoundLabelLength(100);		
        this.contcontent.setBoundLabelUnderline(true);		
        this.contcontent.setVisible(true);
        // continviteType		
        this.continviteType.setBoundLabelText(resHelper.getString("continviteType.boundLabelText"));		
        this.continviteType.setBoundLabelLength(100);		
        this.continviteType.setBoundLabelUnderline(true);		
        this.continviteType.setVisible(true);
        // continviteDate		
        this.continviteDate.setBoundLabelText(resHelper.getString("continviteDate.boundLabelText"));		
        this.continviteDate.setBoundLabelLength(100);		
        this.continviteDate.setBoundLabelUnderline(true);		
        this.continviteDate.setVisible(true);
        // contaddress		
        this.contaddress.setBoundLabelText(resHelper.getString("contaddress.boundLabelText"));		
        this.contaddress.setBoundLabelLength(100);		
        this.contaddress.setBoundLabelUnderline(true);		
        this.contaddress.setVisible(true);
        // kDTabbedPane1
        // contevaSolution		
        this.contevaSolution.setBoundLabelText(resHelper.getString("contevaSolution.boundLabelText"));		
        this.contevaSolution.setBoundLabelLength(100);		
        this.contevaSolution.setBoundLabelUnderline(true);		
        this.contevaSolution.setVisible(true);
        // contevaDate		
        this.contevaDate.setBoundLabelText(resHelper.getString("contevaDate.boundLabelText"));		
        this.contevaDate.setBoundLabelLength(100);		
        this.contevaDate.setBoundLabelUnderline(true);		
        this.contevaDate.setVisible(true);
        // continvitePrjName		
        this.continvitePrjName.setBoundLabelText(resHelper.getString("continvitePrjName.boundLabelText"));		
        this.continvitePrjName.setBoundLabelLength(100);		
        this.continvitePrjName.setBoundLabelUnderline(true);		
        this.continvitePrjName.setVisible(true);
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
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // prmtinviteReport		
        this.prmtinviteReport.setQueryInfo("com.kingdee.eas.port.pm.invite.app.InviteReportQuery");		
        this.prmtinviteReport.setEditable(true);		
        this.prmtinviteReport.setDisplayFormat("$number$");		
        this.prmtinviteReport.setEditFormat("$number$");		
        this.prmtinviteReport.setCommitFormat("$number$");		
        this.prmtinviteReport.setRequired(false);
        prmtinviteReport.addDataChangeListener(new DataChangeListener() {
		public void dataChanged(DataChangeEvent e) {
			try {
				prmtinviteReport_Changed();
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        this.prmtinviteReport.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtinviteReport_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // scrollPanecontent
        // txtcontent		
        this.txtcontent.setRequired(false);		
        this.txtcontent.setMaxLength(1000);
        // prmtinviteType		
        this.prmtinviteType.setQueryInfo("com.kingdee.eas.port.pm.base.app.InviteTypeQuery");		
        this.prmtinviteType.setEditable(true);		
        this.prmtinviteType.setDisplayFormat("$name$");		
        this.prmtinviteType.setEditFormat("$number$");		
        this.prmtinviteType.setCommitFormat("$number$");		
        this.prmtinviteType.setRequired(false);
        // pkinviteDate		
        this.pkinviteDate.setRequired(false);
        // txtaddress		
        this.txtaddress.setHorizontalAlignment(2);		
        this.txtaddress.setMaxLength(100);		
        this.txtaddress.setRequired(false);
        // kDPanel1
        // kDPanel2
        // kdtUnit
		String kdtUnitStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"unitName\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"quality\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"inviteAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"ranking\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"win\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{unitName}</t:Cell><t:Cell>$Resource{quality}</t:Cell><t:Cell>$Resource{inviteAmount}</t:Cell><t:Cell>$Resource{ranking}</t:Cell><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{win}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtUnit.setFormatXml(resHelper.translateString("kdtUnit",kdtUnitStrXML));
        this.kdtUnit.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtUnit_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtUnit.putBindContents("editData",new String[] {"unitName","quality","inviteAmount","ranking","seq","win","beizhu"});


        this.kdtUnit.checkParsed();
        final KDBizPromptBox kdtUnit_unitName_PromptBox = new KDBizPromptBox();
        kdtUnit_unitName_PromptBox.setQueryInfo("com.kingdee.eas.port.markesupplier.subill.app.MarketSupplierStockQuery");
        kdtUnit_unitName_PromptBox.setVisible(true);
        kdtUnit_unitName_PromptBox.setEditable(true);
        kdtUnit_unitName_PromptBox.setDisplayFormat("$number$");
        kdtUnit_unitName_PromptBox.setEditFormat("$number$");
        kdtUnit_unitName_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtUnit_unitName_CellEditor = new KDTDefaultCellEditor(kdtUnit_unitName_PromptBox);
        this.kdtUnit.getColumn("unitName").setEditor(kdtUnit_unitName_CellEditor);
        ObjectValueRender kdtUnit_unitName_OVR = new ObjectValueRender();
        kdtUnit_unitName_OVR.setFormat(new BizDataFormat("$supplierName$"));
        this.kdtUnit.getColumn("unitName").setRenderer(kdtUnit_unitName_OVR);
        KDTextField kdtUnit_quality_TextField = new KDTextField();
        kdtUnit_quality_TextField.setName("kdtUnit_quality_TextField");
        kdtUnit_quality_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtUnit_quality_CellEditor = new KDTDefaultCellEditor(kdtUnit_quality_TextField);
        this.kdtUnit.getColumn("quality").setEditor(kdtUnit_quality_CellEditor);
        KDFormattedTextField kdtUnit_inviteAmount_TextField = new KDFormattedTextField();
        kdtUnit_inviteAmount_TextField.setName("kdtUnit_inviteAmount_TextField");
        kdtUnit_inviteAmount_TextField.setVisible(true);
        kdtUnit_inviteAmount_TextField.setEditable(true);
        kdtUnit_inviteAmount_TextField.setHorizontalAlignment(2);
        kdtUnit_inviteAmount_TextField.setDataType(1);
        	kdtUnit_inviteAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtUnit_inviteAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtUnit_inviteAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtUnit_inviteAmount_CellEditor = new KDTDefaultCellEditor(kdtUnit_inviteAmount_TextField);
        this.kdtUnit.getColumn("inviteAmount").setEditor(kdtUnit_inviteAmount_CellEditor);
        KDTextField kdtUnit_ranking_TextField = new KDTextField();
        kdtUnit_ranking_TextField.setName("kdtUnit_ranking_TextField");
        kdtUnit_ranking_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtUnit_ranking_CellEditor = new KDTDefaultCellEditor(kdtUnit_ranking_TextField);
        this.kdtUnit.getColumn("ranking").setEditor(kdtUnit_ranking_CellEditor);
        KDCheckBox kdtUnit_win_CheckBox = new KDCheckBox();
        kdtUnit_win_CheckBox.setName("kdtUnit_win_CheckBox");
        KDTDefaultCellEditor kdtUnit_win_CellEditor = new KDTDefaultCellEditor(kdtUnit_win_CheckBox);
        this.kdtUnit.getColumn("win").setEditor(kdtUnit_win_CellEditor);
        KDTextArea kdtUnit_beizhu_TextArea = new KDTextArea();
        kdtUnit_beizhu_TextArea.setName("kdtUnit_beizhu_TextArea");
        kdtUnit_beizhu_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtUnit_beizhu_CellEditor = new KDTDefaultCellEditor(kdtUnit_beizhu_TextArea);
        this.kdtUnit.getColumn("beizhu").setEditor(kdtUnit_beizhu_CellEditor);
        // contbudgetAmount		
        this.contbudgetAmount.setBoundLabelText(resHelper.getString("contbudgetAmount.boundLabelText"));		
        this.contbudgetAmount.setBoundLabelLength(100);		
        this.contbudgetAmount.setBoundLabelUnderline(true);		
        this.contbudgetAmount.setVisible(true);
        // kdtBudgetEntry
		String kdtBudgetEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"Project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"budgetNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"budgetName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"budgetAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"balance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"sectBudget\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"lastAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"diffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{Project}</t:Cell><t:Cell>$Resource{budgetNumber}</t:Cell><t:Cell>$Resource{budgetName}</t:Cell><t:Cell>$Resource{budgetAmount}</t:Cell><t:Cell>$Resource{balance}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{sectBudget}</t:Cell><t:Cell>$Resource{lastAmount}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell><t:Cell>$Resource{diffAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtBudgetEntry.setFormatXml(resHelper.translateString("kdtBudgetEntry",kdtBudgetEntryStrXML));
        kdtBudgetEntry.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtBudgetEntry_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});


                this.kdtBudgetEntry.putBindContents("editData",new String[] {"seq","Project","budgetNumber","budgetName","budgetAmount","balance","amount","sectBudget","lastAmount","beizhu","diffAmount"});


        this.kdtBudgetEntry.checkParsed();
        final KDBizPromptBox kdtBudgetEntry_Project_PromptBox = new KDBizPromptBox();
        kdtBudgetEntry_Project_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CustomerProjectQuery");
        kdtBudgetEntry_Project_PromptBox.setVisible(true);
        kdtBudgetEntry_Project_PromptBox.setEditable(true);
        kdtBudgetEntry_Project_PromptBox.setDisplayFormat("$number$");
        kdtBudgetEntry_Project_PromptBox.setEditFormat("$number$");
        kdtBudgetEntry_Project_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtBudgetEntry_Project_CellEditor = new KDTDefaultCellEditor(kdtBudgetEntry_Project_PromptBox);
        this.kdtBudgetEntry.getColumn("Project").setEditor(kdtBudgetEntry_Project_CellEditor);
        ObjectValueRender kdtBudgetEntry_Project_OVR = new ObjectValueRender();
        kdtBudgetEntry_Project_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtBudgetEntry.getColumn("Project").setRenderer(kdtBudgetEntry_Project_OVR);
        KDTextField kdtBudgetEntry_budgetName_TextField = new KDTextField();
        kdtBudgetEntry_budgetName_TextField.setName("kdtBudgetEntry_budgetName_TextField");
        kdtBudgetEntry_budgetName_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtBudgetEntry_budgetName_CellEditor = new KDTDefaultCellEditor(kdtBudgetEntry_budgetName_TextField);
        this.kdtBudgetEntry.getColumn("budgetName").setEditor(kdtBudgetEntry_budgetName_CellEditor);
        KDFormattedTextField kdtBudgetEntry_budgetAmount_TextField = new KDFormattedTextField();
        kdtBudgetEntry_budgetAmount_TextField.setName("kdtBudgetEntry_budgetAmount_TextField");
        kdtBudgetEntry_budgetAmount_TextField.setVisible(true);
        kdtBudgetEntry_budgetAmount_TextField.setEditable(true);
        kdtBudgetEntry_budgetAmount_TextField.setHorizontalAlignment(2);
        kdtBudgetEntry_budgetAmount_TextField.setDataType(1);
        	kdtBudgetEntry_budgetAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtBudgetEntry_budgetAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtBudgetEntry_budgetAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtBudgetEntry_budgetAmount_CellEditor = new KDTDefaultCellEditor(kdtBudgetEntry_budgetAmount_TextField);
        this.kdtBudgetEntry.getColumn("budgetAmount").setEditor(kdtBudgetEntry_budgetAmount_CellEditor);
        KDFormattedTextField kdtBudgetEntry_balance_TextField = new KDFormattedTextField();
        kdtBudgetEntry_balance_TextField.setName("kdtBudgetEntry_balance_TextField");
        kdtBudgetEntry_balance_TextField.setVisible(true);
        kdtBudgetEntry_balance_TextField.setEditable(true);
        kdtBudgetEntry_balance_TextField.setHorizontalAlignment(2);
        kdtBudgetEntry_balance_TextField.setDataType(1);
        	kdtBudgetEntry_balance_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtBudgetEntry_balance_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtBudgetEntry_balance_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtBudgetEntry_balance_CellEditor = new KDTDefaultCellEditor(kdtBudgetEntry_balance_TextField);
        this.kdtBudgetEntry.getColumn("balance").setEditor(kdtBudgetEntry_balance_CellEditor);
        KDFormattedTextField kdtBudgetEntry_amount_TextField = new KDFormattedTextField();
        kdtBudgetEntry_amount_TextField.setName("kdtBudgetEntry_amount_TextField");
        kdtBudgetEntry_amount_TextField.setVisible(true);
        kdtBudgetEntry_amount_TextField.setEditable(true);
        kdtBudgetEntry_amount_TextField.setHorizontalAlignment(2);
        kdtBudgetEntry_amount_TextField.setDataType(1);
        	kdtBudgetEntry_amount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtBudgetEntry_amount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtBudgetEntry_amount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtBudgetEntry_amount_CellEditor = new KDTDefaultCellEditor(kdtBudgetEntry_amount_TextField);
        this.kdtBudgetEntry.getColumn("amount").setEditor(kdtBudgetEntry_amount_CellEditor);
        KDFormattedTextField kdtBudgetEntry_sectBudget_TextField = new KDFormattedTextField();
        kdtBudgetEntry_sectBudget_TextField.setName("kdtBudgetEntry_sectBudget_TextField");
        kdtBudgetEntry_sectBudget_TextField.setVisible(true);
        kdtBudgetEntry_sectBudget_TextField.setEditable(true);
        kdtBudgetEntry_sectBudget_TextField.setHorizontalAlignment(2);
        kdtBudgetEntry_sectBudget_TextField.setDataType(1);
        	kdtBudgetEntry_sectBudget_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtBudgetEntry_sectBudget_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtBudgetEntry_sectBudget_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtBudgetEntry_sectBudget_CellEditor = new KDTDefaultCellEditor(kdtBudgetEntry_sectBudget_TextField);
        this.kdtBudgetEntry.getColumn("sectBudget").setEditor(kdtBudgetEntry_sectBudget_CellEditor);
        KDFormattedTextField kdtBudgetEntry_lastAmount_TextField = new KDFormattedTextField();
        kdtBudgetEntry_lastAmount_TextField.setName("kdtBudgetEntry_lastAmount_TextField");
        kdtBudgetEntry_lastAmount_TextField.setVisible(true);
        kdtBudgetEntry_lastAmount_TextField.setEditable(true);
        kdtBudgetEntry_lastAmount_TextField.setHorizontalAlignment(2);
        kdtBudgetEntry_lastAmount_TextField.setDataType(1);
        	kdtBudgetEntry_lastAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtBudgetEntry_lastAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtBudgetEntry_lastAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtBudgetEntry_lastAmount_CellEditor = new KDTDefaultCellEditor(kdtBudgetEntry_lastAmount_TextField);
        this.kdtBudgetEntry.getColumn("lastAmount").setEditor(kdtBudgetEntry_lastAmount_CellEditor);
        KDTextArea kdtBudgetEntry_beizhu_TextArea = new KDTextArea();
        kdtBudgetEntry_beizhu_TextArea.setName("kdtBudgetEntry_beizhu_TextArea");
        kdtBudgetEntry_beizhu_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtBudgetEntry_beizhu_CellEditor = new KDTDefaultCellEditor(kdtBudgetEntry_beizhu_TextArea);
        this.kdtBudgetEntry.getColumn("beizhu").setEditor(kdtBudgetEntry_beizhu_CellEditor);
        KDFormattedTextField kdtBudgetEntry_diffAmount_TextField = new KDFormattedTextField();
        kdtBudgetEntry_diffAmount_TextField.setName("kdtBudgetEntry_diffAmount_TextField");
        kdtBudgetEntry_diffAmount_TextField.setVisible(true);
        kdtBudgetEntry_diffAmount_TextField.setEditable(true);
        kdtBudgetEntry_diffAmount_TextField.setHorizontalAlignment(2);
        kdtBudgetEntry_diffAmount_TextField.setDataType(1);
        	kdtBudgetEntry_diffAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtBudgetEntry_diffAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtBudgetEntry_diffAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtBudgetEntry_diffAmount_CellEditor = new KDTDefaultCellEditor(kdtBudgetEntry_diffAmount_TextField);
        this.kdtBudgetEntry.getColumn("diffAmount").setEditor(kdtBudgetEntry_diffAmount_CellEditor);
        // continvitedAmount		
        this.continvitedAmount.setBoundLabelText(resHelper.getString("continvitedAmount.boundLabelText"));		
        this.continvitedAmount.setBoundLabelLength(100);		
        this.continvitedAmount.setBoundLabelUnderline(true);		
        this.continvitedAmount.setVisible(true);
        // contwinInviteUnit		
        this.contwinInviteUnit.setBoundLabelText(resHelper.getString("contwinInviteUnit.boundLabelText"));		
        this.contwinInviteUnit.setBoundLabelLength(100);		
        this.contwinInviteUnit.setBoundLabelUnderline(true);		
        this.contwinInviteUnit.setVisible(true);
        // txtbudgetAmount		
        this.txtbudgetAmount.setHorizontalAlignment(2);		
        this.txtbudgetAmount.setDataType(1);		
        this.txtbudgetAmount.setSupportedEmpty(true);		
        this.txtbudgetAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtbudgetAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtbudgetAmount.setPrecision(2);		
        this.txtbudgetAmount.setRequired(false);		
        this.txtbudgetAmount.setEnabled(false);
        // txtinvitedAmount		
        this.txtinvitedAmount.setHorizontalAlignment(2);		
        this.txtinvitedAmount.setDataType(1);		
        this.txtinvitedAmount.setSupportedEmpty(true);		
        this.txtinvitedAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtinvitedAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtinvitedAmount.setPrecision(2);		
        this.txtinvitedAmount.setRequired(false);
        // prmtwinInviteUnit		
        this.prmtwinInviteUnit.setQueryInfo("com.kingdee.eas.port.markesupplier.subill.app.MarketSupplierStockQuery");		
        this.prmtwinInviteUnit.setEditable(true);		
        this.prmtwinInviteUnit.setDisplayFormat("$supplierName$");		
        this.prmtwinInviteUnit.setEditFormat("$number$");		
        this.prmtwinInviteUnit.setCommitFormat("$number$");		
        this.prmtwinInviteUnit.setRequired(false);
        // kdtJudges
		String kdtJudgesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"judgesName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"unitName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"org\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{judgesName}</t:Cell><t:Cell>$Resource{unitName}</t:Cell><t:Cell>$Resource{org}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtJudges.setFormatXml(resHelper.translateString("kdtJudges",kdtJudgesStrXML));

                this.kdtJudges.putBindContents("editData",new String[] {"seq","judgesName","unitName","org"});


        this.kdtJudges.checkParsed();
        final KDBizPromptBox kdtJudges_judgesName_PromptBox = new KDBizPromptBox();
        kdtJudges_judgesName_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.base.app.JudgesQuery");
        kdtJudges_judgesName_PromptBox.setVisible(true);
        kdtJudges_judgesName_PromptBox.setEditable(true);
        kdtJudges_judgesName_PromptBox.setDisplayFormat("$number$");
        kdtJudges_judgesName_PromptBox.setEditFormat("$number$");
        kdtJudges_judgesName_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtJudges_judgesName_CellEditor = new KDTDefaultCellEditor(kdtJudges_judgesName_PromptBox);
        this.kdtJudges.getColumn("judgesName").setEditor(kdtJudges_judgesName_CellEditor);
        ObjectValueRender kdtJudges_judgesName_OVR = new ObjectValueRender();
        kdtJudges_judgesName_OVR.setFormat(new BizDataFormat("$personName$"));
        this.kdtJudges.getColumn("judgesName").setRenderer(kdtJudges_judgesName_OVR);
        			kdtJudges_judgesName_PromptBox.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.pm.base.client.JudgesListUI kdtJudges_judgesName_PromptBox_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (kdtJudges_judgesName_PromptBox_F7ListUI == null) {
					try {
						kdtJudges_judgesName_PromptBox_F7ListUI = new com.kingdee.eas.port.pm.base.client.JudgesListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(kdtJudges_judgesName_PromptBox_F7ListUI));
					kdtJudges_judgesName_PromptBox_F7ListUI.setF7Use(true,ctx);
					kdtJudges_judgesName_PromptBox.setSelector(kdtJudges_judgesName_PromptBox_F7ListUI);
				}
			}
		});
					
        KDTextField kdtJudges_unitName_TextField = new KDTextField();
        kdtJudges_unitName_TextField.setName("kdtJudges_unitName_TextField");
        kdtJudges_unitName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtJudges_unitName_CellEditor = new KDTDefaultCellEditor(kdtJudges_unitName_TextField);
        this.kdtJudges.getColumn("unitName").setEditor(kdtJudges_unitName_CellEditor);
        final KDBizPromptBox kdtJudges_org_PromptBox = new KDBizPromptBox();
        kdtJudges_org_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");
        kdtJudges_org_PromptBox.setVisible(true);
        kdtJudges_org_PromptBox.setEditable(true);
        kdtJudges_org_PromptBox.setDisplayFormat("$number$");
        kdtJudges_org_PromptBox.setEditFormat("$number$");
        kdtJudges_org_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtJudges_org_CellEditor = new KDTDefaultCellEditor(kdtJudges_org_PromptBox);
        this.kdtJudges.getColumn("org").setEditor(kdtJudges_org_CellEditor);
        ObjectValueRender kdtJudges_org_OVR = new ObjectValueRender();
        kdtJudges_org_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtJudges.getColumn("org").setRenderer(kdtJudges_org_OVR);
        // evaSolution		
        this.evaSolution.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.pm.invite.judgeSolution").toArray());		
        this.evaSolution.setRequired(false);
        // pkevaDate		
        this.pkevaDate.setRequired(false);
        // txtinvitePrjName		
        this.txtinvitePrjName.setHorizontalAlignment(2);		
        this.txtinvitePrjName.setMaxLength(80);		
        this.txtinvitePrjName.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtinvitedAmount,prmtwinInviteUnit,prmtinviteReport,txtcontent,prmtinviteType,pkinviteDate,txtaddress,txtbudgetAmount,comboStatus,comboBizStatus,pkAuditTime,txtNumber,pkBizDate,txtDescription,prmtAuditor,prmtCreator,pkCreateTime,prmtLastUpdateUser,pkLastUpdateTime,prmtCU,evaSolution,pkevaDate,txtinvitePrjName,kdtUnit,kdtJudges,kdtBudgetEntry}));
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        contCreator.setBounds(new Rectangle(35, 536, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(35, 536, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(35, 562, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(35, 562, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(371, 536, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(371, 536, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(371, 562, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(371, 562, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(35, 589, 270, 19));
        this.add(contCU, new KDLayout.Constraints(35, 589, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(33, 11, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(33, 11, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(789, 583, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(789, 583, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(558, 597, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(558, 597, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(708, 536, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(708, 536, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(706, 62, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(706, 62, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(305, 590, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(305, 590, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(708, 562, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(708, 562, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        continviteReport.setBounds(new Rectangle(369, 11, 270, 19));
        this.add(continviteReport, new KDLayout.Constraints(369, 11, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontent.setBounds(new Rectangle(33, 85, 944, 60));
        this.add(contcontent, new KDLayout.Constraints(33, 85, 944, 60, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        continviteType.setBounds(new Rectangle(33, 35, 270, 19));
        this.add(continviteType, new KDLayout.Constraints(33, 35, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continviteDate.setBounds(new Rectangle(33, 62, 270, 19));
        this.add(continviteDate, new KDLayout.Constraints(33, 62, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contaddress.setBounds(new Rectangle(706, 35, 270, 19));
        this.add(contaddress, new KDLayout.Constraints(706, 35, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane1.setBounds(new Rectangle(31, 147, 971, 384));
        this.add(kDTabbedPane1, new KDLayout.Constraints(31, 147, 971, 384, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contevaSolution.setBounds(new Rectangle(369, 35, 270, 19));
        this.add(contevaSolution, new KDLayout.Constraints(369, 35, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contevaDate.setBounds(new Rectangle(369, 62, 270, 19));
        this.add(contevaDate, new KDLayout.Constraints(369, 62, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continvitePrjName.setBounds(new Rectangle(706, 11, 270, 19));
        this.add(continvitePrjName, new KDLayout.Constraints(706, 11, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
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
        //continviteReport
        continviteReport.setBoundEditor(prmtinviteReport);
        //contcontent
        contcontent.setBoundEditor(scrollPanecontent);
        //scrollPanecontent
        scrollPanecontent.getViewport().add(txtcontent, null);
        //continviteType
        continviteType.setBoundEditor(prmtinviteType);
        //continviteDate
        continviteDate.setBoundEditor(pkinviteDate);
        //contaddress
        contaddress.setBoundEditor(txtaddress);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 970, 351));        kdtUnit.setBounds(new Rectangle(4, 31, 942, 129));
        kdtUnit_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtUnit,new com.kingdee.eas.port.pm.invite.WinInviteReportUnitInfo(),null,false);
        kDPanel1.add(kdtUnit_detailPanel, new KDLayout.Constraints(4, 31, 942, 129, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contbudgetAmount.setBounds(new Rectangle(6, 5, 270, 19));
        kDPanel1.add(contbudgetAmount, new KDLayout.Constraints(6, 5, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtBudgetEntry.setBounds(new Rectangle(4, 167, 912, 170));
        kdtBudgetEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtBudgetEntry,new com.kingdee.eas.port.pm.invite.WinInviteReportBudgetEntryInfo(),null,false);
        kDPanel1.add(kdtBudgetEntry_detailPanel, new KDLayout.Constraints(4, 167, 912, 170, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        continvitedAmount.setBounds(new Rectangle(341, 5, 270, 19));
        kDPanel1.add(continvitedAmount, new KDLayout.Constraints(341, 5, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contwinInviteUnit.setBounds(new Rectangle(670, 5, 270, 19));
        kDPanel1.add(contwinInviteUnit, new KDLayout.Constraints(670, 5, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contbudgetAmount
        contbudgetAmount.setBoundEditor(txtbudgetAmount);
        //continvitedAmount
        continvitedAmount.setBoundEditor(txtinvitedAmount);
        //contwinInviteUnit
        contwinInviteUnit.setBoundEditor(prmtwinInviteUnit);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0, 0, 970, 351));        kdtJudges.setBounds(new Rectangle(1, 3, 899, 297));
        kdtJudges_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtJudges,new com.kingdee.eas.port.pm.invite.WinInviteReportJudgeInfo(),null,false);
        kDPanel2.add(kdtJudges_detailPanel, new KDLayout.Constraints(1, 3, 899, 297, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contevaSolution
        contevaSolution.setBoundEditor(evaSolution);
        //contevaDate
        contevaDate.setBoundEditor(pkevaDate);
        //continvitePrjName
        continvitePrjName.setBoundEditor(txtinvitePrjName);

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
		dataBinder.registerBinding("inviteReport", com.kingdee.eas.port.pm.invite.InviteReportInfo.class, this.prmtinviteReport, "data");
		dataBinder.registerBinding("content", String.class, this.txtcontent, "text");
		dataBinder.registerBinding("inviteType", com.kingdee.eas.port.pm.base.InviteTypeInfo.class, this.prmtinviteType, "data");
		dataBinder.registerBinding("inviteDate", java.util.Date.class, this.pkinviteDate, "value");
		dataBinder.registerBinding("address", String.class, this.txtaddress, "text");
		dataBinder.registerBinding("Unit.seq", int.class, this.kdtUnit, "seq.text");
		dataBinder.registerBinding("Unit", com.kingdee.eas.port.pm.invite.WinInviteReportUnitInfo.class, this.kdtUnit, "userObject");
		dataBinder.registerBinding("Unit.quality", String.class, this.kdtUnit, "quality.text");
		dataBinder.registerBinding("Unit.inviteAmount", java.math.BigDecimal.class, this.kdtUnit, "inviteAmount.text");
		dataBinder.registerBinding("Unit.win", boolean.class, this.kdtUnit, "win.text");
		dataBinder.registerBinding("Unit.beizhu", String.class, this.kdtUnit, "beizhu.text");
		dataBinder.registerBinding("Unit.unitName", java.lang.Object.class, this.kdtUnit, "unitName.text");
		dataBinder.registerBinding("Unit.ranking", String.class, this.kdtUnit, "ranking.text");
		dataBinder.registerBinding("BudgetEntry.seq", int.class, this.kdtBudgetEntry, "seq.text");
		dataBinder.registerBinding("BudgetEntry", com.kingdee.eas.port.pm.invite.WinInviteReportBudgetEntryInfo.class, this.kdtBudgetEntry, "userObject");
		dataBinder.registerBinding("BudgetEntry.budgetNumber", java.lang.Object.class, this.kdtBudgetEntry, "budgetNumber.text");
		dataBinder.registerBinding("BudgetEntry.budgetName", String.class, this.kdtBudgetEntry, "budgetName.text");
		dataBinder.registerBinding("BudgetEntry.budgetAmount", java.math.BigDecimal.class, this.kdtBudgetEntry, "budgetAmount.text");
		dataBinder.registerBinding("BudgetEntry.balance", java.math.BigDecimal.class, this.kdtBudgetEntry, "balance.text");
		dataBinder.registerBinding("BudgetEntry.amount", java.math.BigDecimal.class, this.kdtBudgetEntry, "amount.text");
		dataBinder.registerBinding("BudgetEntry.lastAmount", java.math.BigDecimal.class, this.kdtBudgetEntry, "lastAmount.text");
		dataBinder.registerBinding("BudgetEntry.beizhu", String.class, this.kdtBudgetEntry, "beizhu.text");
		dataBinder.registerBinding("BudgetEntry.diffAmount", java.math.BigDecimal.class, this.kdtBudgetEntry, "diffAmount.text");
		dataBinder.registerBinding("BudgetEntry.sectBudget", java.math.BigDecimal.class, this.kdtBudgetEntry, "sectBudget.text");
		dataBinder.registerBinding("BudgetEntry.Project", java.lang.Object.class, this.kdtBudgetEntry, "Project.text");
		dataBinder.registerBinding("budgetAmount", java.math.BigDecimal.class, this.txtbudgetAmount, "value");
		dataBinder.registerBinding("invitedAmount", java.math.BigDecimal.class, this.txtinvitedAmount, "value");
		dataBinder.registerBinding("winInviteUnit", com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo.class, this.prmtwinInviteUnit, "data");
		dataBinder.registerBinding("Judges.seq", int.class, this.kdtJudges, "seq.text");
		dataBinder.registerBinding("Judges", com.kingdee.eas.port.pm.invite.WinInviteReportJudgeInfo.class, this.kdtJudges, "userObject");
		dataBinder.registerBinding("Judges.org", java.lang.Object.class, this.kdtJudges, "org.text");
		dataBinder.registerBinding("Judges.judgesName", java.lang.Object.class, this.kdtJudges, "judgesName.text");
		dataBinder.registerBinding("Judges.unitName", String.class, this.kdtJudges, "unitName.text");
		dataBinder.registerBinding("evaSolution", com.kingdee.eas.port.pm.invite.judgeSolution.class, this.evaSolution, "selectedItem");
		dataBinder.registerBinding("evaDate", java.util.Date.class, this.pkevaDate, "value");
		dataBinder.registerBinding("invitePrjName", String.class, this.txtinvitePrjName, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invite.app.WinInviteReportEditUIHandler";
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
        this.txtinvitedAmount.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.pm.invite.WinInviteReportInfo)ov;
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
		getValidateHelper().registerBindProperty("inviteReport", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("content", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Unit.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Unit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Unit.quality", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Unit.inviteAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Unit.win", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Unit.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Unit.unitName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Unit.ranking", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BudgetEntry.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BudgetEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BudgetEntry.budgetNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BudgetEntry.budgetName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BudgetEntry.budgetAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BudgetEntry.balance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BudgetEntry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BudgetEntry.lastAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BudgetEntry.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BudgetEntry.diffAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BudgetEntry.sectBudget", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BudgetEntry.Project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("budgetAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invitedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("winInviteUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Judges.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Judges", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Judges.org", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Judges.judgesName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Judges.unitName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("evaSolution", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("evaDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invitePrjName", ValidateHelper.ON_SAVE);    		
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
     * output prmtinviteReport_dataChanged method
     */
    protected void prmtinviteReport_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtUnit_editValueChanged method
     */
    protected void kdtUnit_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }


    /**
     * output prmtinviteReport_Changed() method
     */
    public void prmtinviteReport_Changed() throws Exception
    {
        System.out.println("prmtinviteReport_Changed() Function is executed!");
            evaSolution.setSelectedItem(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtinviteReport.getData(),"judgeSolution"));

    txtinvitePrjName.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtinviteReport.getData(),"proName.name")));


    }

    /**
     * output kdtBudgetEntry_Changed(int rowIndex,int colIndex) method
     */
    public void kdtBudgetEntry_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("budgetNumber".equalsIgnoreCase(kdtBudgetEntry.getColumn(colIndex).getKey())) {
kdtBudgetEntry.getCell(rowIndex,"budgetName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtBudgetEntry.getCell(rowIndex,"budgetNumber").getValue(),"feeName")));

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
			sic.add(new SelectorItemInfo("inviteReport.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("inviteReport.id"));
        	sic.add(new SelectorItemInfo("inviteReport.number"));
		}
        sic.add(new SelectorItemInfo("content"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("inviteType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("inviteType.id"));
        	sic.add(new SelectorItemInfo("inviteType.number"));
        	sic.add(new SelectorItemInfo("inviteType.name"));
		}
        sic.add(new SelectorItemInfo("inviteDate"));
        sic.add(new SelectorItemInfo("address"));
    	sic.add(new SelectorItemInfo("Unit.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Unit.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("Unit.quality"));
    	sic.add(new SelectorItemInfo("Unit.inviteAmount"));
    	sic.add(new SelectorItemInfo("Unit.win"));
    	sic.add(new SelectorItemInfo("Unit.beizhu"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Unit.unitName.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Unit.unitName.id"));
			sic.add(new SelectorItemInfo("Unit.unitName.supplierName"));
        	sic.add(new SelectorItemInfo("Unit.unitName.number"));
		}
    	sic.add(new SelectorItemInfo("Unit.ranking"));
    	sic.add(new SelectorItemInfo("BudgetEntry.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("BudgetEntry.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("BudgetEntry.budgetNumber.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("BudgetEntry.budgetNumber.id"));
			sic.add(new SelectorItemInfo("BudgetEntry.budgetNumber.feeNumber"));
        	sic.add(new SelectorItemInfo("BudgetEntry.budgetNumber.number"));
		}
    	sic.add(new SelectorItemInfo("BudgetEntry.budgetName"));
    	sic.add(new SelectorItemInfo("BudgetEntry.budgetAmount"));
    	sic.add(new SelectorItemInfo("BudgetEntry.balance"));
    	sic.add(new SelectorItemInfo("BudgetEntry.amount"));
    	sic.add(new SelectorItemInfo("BudgetEntry.lastAmount"));
    	sic.add(new SelectorItemInfo("BudgetEntry.beizhu"));
    	sic.add(new SelectorItemInfo("BudgetEntry.diffAmount"));
    	sic.add(new SelectorItemInfo("BudgetEntry.sectBudget"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("BudgetEntry.Project.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("BudgetEntry.Project.id"));
			sic.add(new SelectorItemInfo("BudgetEntry.Project.name"));
        	sic.add(new SelectorItemInfo("BudgetEntry.Project.number"));
		}
        sic.add(new SelectorItemInfo("budgetAmount"));
        sic.add(new SelectorItemInfo("invitedAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("winInviteUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("winInviteUnit.id"));
        	sic.add(new SelectorItemInfo("winInviteUnit.number"));
        	sic.add(new SelectorItemInfo("winInviteUnit.supplierName"));
		}
    	sic.add(new SelectorItemInfo("Judges.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Judges.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Judges.org.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Judges.org.id"));
			sic.add(new SelectorItemInfo("Judges.org.name"));
        	sic.add(new SelectorItemInfo("Judges.org.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Judges.judgesName.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Judges.judgesName.id"));
			sic.add(new SelectorItemInfo("Judges.judgesName.personName"));
			sic.add(new SelectorItemInfo("Judges.judgesName.name"));
        	sic.add(new SelectorItemInfo("Judges.judgesName.number"));
		}
    	sic.add(new SelectorItemInfo("Judges.unitName"));
        sic.add(new SelectorItemInfo("evaSolution"));
        sic.add(new SelectorItemInfo("evaDate"));
        sic.add(new SelectorItemInfo("invitePrjName"));
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
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
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
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
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
        return new MetaDataPK("com.kingdee.eas.port.pm.invite.client", "WinInviteReportEditUI");
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
        return com.kingdee.eas.port.pm.invite.client.WinInviteReportEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invite.WinInviteReportFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invite.WinInviteReportInfo objectValue = new com.kingdee.eas.port.pm.invite.WinInviteReportInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/invite/WinInviteReport";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.invite.app.WinInviteReportQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtUnit;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("evaSolution","1");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}