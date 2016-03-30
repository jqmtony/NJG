/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.client;

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
public abstract class AbstractProjectDynamicCostEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectDynamicCostEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurProject;
    protected com.kingdee.bos.ctrl.swing.KDButton btnLoadData;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contversion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditTime;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfirstLevelPos;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsecondLevelPos;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contthirdLevelPos;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntryPosition;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntryPosition_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisLatest;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcurProject;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtversion;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spYear;
    protected com.kingdee.bos.ctrl.swing.KDComboBox state;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditTime;
    protected com.kingdee.bos.ctrl.swing.KDPanel contractPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel accountPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel totalPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel sixMonthPanel;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrysContract;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrysContract_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel5;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel6;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel7;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable3;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable4;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable5;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable6;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrysAccount;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrysAccount_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEentryTotal;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEentryTotal_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer4;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrySixMonth;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrySixMonth_detailPanel = null;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtfirstLevelPos;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsecondLevelPos;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtthirdLevelPos;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAduit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRevise;
    protected com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionRevise actionRevise = null;
    /**
     * output class constructor
     */
    public AbstractProjectDynamicCostEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectDynamicCostEditUI.class.getName());
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
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
        this.actionAudit.setExtendProperty("canForewarn", "true");
        this.actionAudit.setExtendProperty("userDefined", "true");
        this.actionAudit.setExtendProperty("isObjectUpdateLock", "false");
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
        this.actionUnAudit.setExtendProperty("canForewarn", "true");
        this.actionUnAudit.setExtendProperty("userDefined", "true");
        this.actionUnAudit.setExtendProperty("isObjectUpdateLock", "false");
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionRevise
        this.actionRevise = new ActionRevise(this);
        getActionManager().registerAction("actionRevise", actionRevise);
         this.actionRevise.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnLoadData = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contversion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contstate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contfirstLevelPos = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsecondLevelPos = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contthirdLevelPos = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntryPosition = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.chkisLatest = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtversion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.spYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.state = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkauditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contractPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.accountPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.totalPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.sixMonthPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntrysContract = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTabbedPane2 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel5 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel6 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel7 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTable2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTable3 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTable4 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTable5 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTable6 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntrysAccount = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEentryTotal = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer4 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntrySixMonth = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtfirstLevelPos = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsecondLevelPos = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtthirdLevelPos = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAduit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRevise = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contcurProject.setName("contcurProject");
        this.btnLoadData.setName("btnLoadData");
        this.contversion.setName("contversion");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.spMonth.setName("spMonth");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.contstate.setName("contstate");
        this.contauditTime.setName("contauditTime");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contfirstLevelPos.setName("contfirstLevelPos");
        this.contsecondLevelPos.setName("contsecondLevelPos");
        this.contthirdLevelPos.setName("contthirdLevelPos");
        this.kdtEntryPosition.setName("kdtEntryPosition");
        this.chkisLatest.setName("chkisLatest");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtcurProject.setName("prmtcurProject");
        this.txtversion.setName("txtversion");
        this.spYear.setName("spYear");
        this.state.setName("state");
        this.pkauditTime.setName("pkauditTime");
        this.contractPanel.setName("contractPanel");
        this.accountPanel.setName("accountPanel");
        this.totalPanel.setName("totalPanel");
        this.sixMonthPanel.setName("sixMonthPanel");
        this.kDContainer2.setName("kDContainer2");
        this.kdtEntrysContract.setName("kdtEntrysContract");
        this.kDTabbedPane2.setName("kDTabbedPane2");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel4.setName("kDPanel4");
        this.kDPanel5.setName("kDPanel5");
        this.kDPanel6.setName("kDPanel6");
        this.kDPanel7.setName("kDPanel7");
        this.kDTable1.setName("kDTable1");
        this.kDTable2.setName("kDTable2");
        this.kDTable3.setName("kDTable3");
        this.kDTable4.setName("kDTable4");
        this.kDTable5.setName("kDTable5");
        this.kDTable6.setName("kDTable6");
        this.kDContainer1.setName("kDContainer1");
        this.kdtEntrysAccount.setName("kdtEntrysAccount");
        this.kDContainer3.setName("kDContainer3");
        this.kdtEentryTotal.setName("kdtEentryTotal");
        this.kDContainer4.setName("kDContainer4");
        this.kdtEntrySixMonth.setName("kdtEntrySixMonth");
        this.prmtfirstLevelPos.setName("prmtfirstLevelPos");
        this.prmtsecondLevelPos.setName("prmtsecondLevelPos");
        this.prmtthirdLevelPos.setName("prmtthirdLevelPos");
        this.btnAudit.setName("btnAudit");
        this.btnUnAduit.setName("btnUnAduit");
        this.btnRevise.setName("btnRevise");
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
        this.contLastUpdateUser.setVisible(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);		
        this.contLastUpdateTime.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contcurProject		
        this.contcurProject.setBoundLabelText(resHelper.getString("contcurProject.boundLabelText"));		
        this.contcurProject.setBoundLabelLength(100);		
        this.contcurProject.setBoundLabelUnderline(true);		
        this.contcurProject.setVisible(true);
        // btnLoadData		
        this.btnLoadData.setText(resHelper.getString("btnLoadData.text"));
        this.btnLoadData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnLoadData_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contversion		
        this.contversion.setBoundLabelText(resHelper.getString("contversion.boundLabelText"));		
        this.contversion.setBoundLabelLength(100);		
        this.contversion.setBoundLabelUnderline(true);		
        this.contversion.setVisible(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // spMonth
        this.spMonth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spMonth_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // contstate		
        this.contstate.setBoundLabelText(resHelper.getString("contstate.boundLabelText"));		
        this.contstate.setBoundLabelLength(100);		
        this.contstate.setBoundLabelUnderline(true);		
        this.contstate.setVisible(true);
        // contauditTime		
        this.contauditTime.setBoundLabelText(resHelper.getString("contauditTime.boundLabelText"));		
        this.contauditTime.setBoundLabelLength(100);		
        this.contauditTime.setBoundLabelUnderline(true);		
        this.contauditTime.setVisible(true);
        // kDTabbedPane1
        // contfirstLevelPos		
        this.contfirstLevelPos.setBoundLabelText(resHelper.getString("contfirstLevelPos.boundLabelText"));		
        this.contfirstLevelPos.setBoundLabelLength(100);		
        this.contfirstLevelPos.setBoundLabelUnderline(true);		
        this.contfirstLevelPos.setVisible(true);
        // contsecondLevelPos		
        this.contsecondLevelPos.setBoundLabelText(resHelper.getString("contsecondLevelPos.boundLabelText"));		
        this.contsecondLevelPos.setBoundLabelLength(100);		
        this.contsecondLevelPos.setBoundLabelUnderline(true);		
        this.contsecondLevelPos.setVisible(true);
        // contthirdLevelPos		
        this.contthirdLevelPos.setBoundLabelText(resHelper.getString("contthirdLevelPos.boundLabelText"));		
        this.contthirdLevelPos.setBoundLabelLength(100);		
        this.contthirdLevelPos.setBoundLabelUnderline(true);		
        this.contthirdLevelPos.setVisible(true);
        // kdtEntryPosition
		String kdtEntryPositionStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol1\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"position\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{position}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntryPosition.setFormatXml(resHelper.translateString("kdtEntryPosition",kdtEntryPositionStrXML));

                this.kdtEntryPosition.putBindContents("editData",new String[] {"seq","level","position"});


        this.kdtEntryPosition.checkParsed();
        KDFormattedTextField kdtEntryPosition_level_TextField = new KDFormattedTextField();
        kdtEntryPosition_level_TextField.setName("kdtEntryPosition_level_TextField");
        kdtEntryPosition_level_TextField.setVisible(true);
        kdtEntryPosition_level_TextField.setEditable(true);
        kdtEntryPosition_level_TextField.setHorizontalAlignment(2);
        kdtEntryPosition_level_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntryPosition_level_CellEditor = new KDTDefaultCellEditor(kdtEntryPosition_level_TextField);
        this.kdtEntryPosition.getColumn("level").setEditor(kdtEntryPosition_level_CellEditor);
        final KDBizPromptBox kdtEntryPosition_position_PromptBox = new KDBizPromptBox();
        kdtEntryPosition_position_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.PositionQuery");
        kdtEntryPosition_position_PromptBox.setVisible(true);
        kdtEntryPosition_position_PromptBox.setEditable(true);
        kdtEntryPosition_position_PromptBox.setDisplayFormat("$number$");
        kdtEntryPosition_position_PromptBox.setEditFormat("$number$");
        kdtEntryPosition_position_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntryPosition_position_CellEditor = new KDTDefaultCellEditor(kdtEntryPosition_position_PromptBox);
        this.kdtEntryPosition.getColumn("position").setEditor(kdtEntryPosition_position_CellEditor);
        ObjectValueRender kdtEntryPosition_position_OVR = new ObjectValueRender();
        kdtEntryPosition_position_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntryPosition.getColumn("position").setRenderer(kdtEntryPosition_position_OVR);
        // chkisLatest		
        this.chkisLatest.setText(resHelper.getString("chkisLatest.text"));		
        this.chkisLatest.setHorizontalAlignment(2);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setTimeEnabled(true);		
        this.kDDateLastUpdateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // pkBizDate		
        this.pkBizDate.setEnabled(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // prmtcurProject		
        this.prmtcurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");		
        this.prmtcurProject.setEditable(true);		
        this.prmtcurProject.setDisplayFormat("$name$");		
        this.prmtcurProject.setEditFormat("$number$");		
        this.prmtcurProject.setCommitFormat("$number$");		
        this.prmtcurProject.setRequired(false);
        // txtversion		
        this.txtversion.setHorizontalAlignment(2);		
        this.txtversion.setDataType(0);		
        this.txtversion.setSupportedEmpty(true);		
        this.txtversion.setRequired(false);
        // spYear
        this.spYear.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spYear_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // state		
        this.state.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.state.setRequired(false);
        // pkauditTime		
        this.pkauditTime.setRequired(false);
        // contractPanel
        // accountPanel
        // totalPanel
        // sixMonthPanel
        // kDContainer2
        // kdtEntrysContract
		String kdtEntrysContractStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol19\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol20\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"programmingName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"Contract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"ContractBillId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"Amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"ContractAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"designChangeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"siteCertificatAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"settlementAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"notextContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"sumB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"onWayCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"curMonthEstimateChange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"EstimateChangeBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"curMonthOtherAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"otherAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"unSignContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"sumC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"dynamicCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"diffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"diffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"Comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"curMonthOtherId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"ForecastChangeVisID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"programmingId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{programmingName}</t:Cell><t:Cell>$Resource{Contract}</t:Cell><t:Cell>$Resource{ContractBillId}</t:Cell><t:Cell>$Resource{Amount}</t:Cell><t:Cell>$Resource{ContractAmount}</t:Cell><t:Cell>$Resource{designChangeAmount}</t:Cell><t:Cell>$Resource{siteCertificatAmount}</t:Cell><t:Cell>$Resource{settlementAmount}</t:Cell><t:Cell>$Resource{notextContract}</t:Cell><t:Cell>$Resource{sumB}</t:Cell><t:Cell>$Resource{onWayCost}</t:Cell><t:Cell>$Resource{curMonthEstimateChange}</t:Cell><t:Cell>$Resource{EstimateChangeBalance}</t:Cell><t:Cell>$Resource{curMonthOtherAmount}</t:Cell><t:Cell>$Resource{otherAmount}</t:Cell><t:Cell>$Resource{unSignContract}</t:Cell><t:Cell>$Resource{sumC}</t:Cell><t:Cell>$Resource{dynamicCost}</t:Cell><t:Cell>$Resource{diffAmount}</t:Cell><t:Cell>$Resource{diffRate}</t:Cell><t:Cell>$Resource{Comment}</t:Cell><t:Cell>$Resource{curMonthOtherId}</t:Cell><t:Cell>$Resource{ForecastChangeVisID}</t:Cell><t:Cell>$Resource{programmingId}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{programmingName_Row2}</t:Cell><t:Cell>$Resource{Contract_Row2}</t:Cell><t:Cell>$Resource{ContractBillId_Row2}</t:Cell><t:Cell>$Resource{Amount_Row2}</t:Cell><t:Cell>$Resource{ContractAmount_Row2}</t:Cell><t:Cell>$Resource{designChangeAmount_Row2}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row2}</t:Cell><t:Cell>$Resource{settlementAmount_Row2}</t:Cell><t:Cell>$Resource{notextContract_Row2}</t:Cell><t:Cell>$Resource{sumB_Row2}</t:Cell><t:Cell>$Resource{onWayCost_Row2}</t:Cell><t:Cell>$Resource{curMonthEstimateChange_Row2}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row2}</t:Cell><t:Cell>$Resource{curMonthOtherAmount_Row2}</t:Cell><t:Cell>$Resource{otherAmount_Row2}</t:Cell><t:Cell>$Resource{unSignContract_Row2}</t:Cell><t:Cell>$Resource{sumC_Row2}</t:Cell><t:Cell>$Resource{dynamicCost_Row2}</t:Cell><t:Cell>$Resource{diffAmount_Row2}</t:Cell><t:Cell>$Resource{diffRate_Row2}</t:Cell><t:Cell>$Resource{Comment_Row2}</t:Cell><t:Cell>$Resource{curMonthOtherId_Row2}</t:Cell><t:Cell>$Resource{ForecastChangeVisID_Row2}</t:Cell><t:Cell>$Resource{programmingId_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row3}</t:Cell><t:Cell>$Resource{programmingName_Row3}</t:Cell><t:Cell>$Resource{Contract_Row3}</t:Cell><t:Cell>$Resource{ContractBillId_Row3}</t:Cell><t:Cell>$Resource{Amount_Row3}</t:Cell><t:Cell>$Resource{ContractAmount_Row3}</t:Cell><t:Cell>$Resource{designChangeAmount_Row3}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row3}</t:Cell><t:Cell>$Resource{settlementAmount_Row3}</t:Cell><t:Cell>$Resource{notextContract_Row3}</t:Cell><t:Cell>$Resource{sumB_Row3}</t:Cell><t:Cell>$Resource{onWayCost_Row3}</t:Cell><t:Cell>$Resource{curMonthEstimateChange_Row3}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row3}</t:Cell><t:Cell>$Resource{curMonthOtherAmount_Row3}</t:Cell><t:Cell>$Resource{otherAmount_Row3}</t:Cell><t:Cell>$Resource{unSignContract_Row3}</t:Cell><t:Cell>$Resource{sumC_Row3}</t:Cell><t:Cell>$Resource{dynamicCost_Row3}</t:Cell><t:Cell>$Resource{diffAmount_Row3}</t:Cell><t:Cell>$Resource{diffRate_Row3}</t:Cell><t:Cell>$Resource{Comment_Row3}</t:Cell><t:Cell>$Resource{curMonthOtherId_Row3}</t:Cell><t:Cell>$Resource{ForecastChangeVisID_Row3}</t:Cell><t:Cell>$Resource{programmingId_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"2\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"2\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"0\" t:right=\"17\" /><t:Block t:top=\"1\" t:left=\"5\" t:bottom=\"1\" t:right=\"10\" /><t:Block t:top=\"1\" t:left=\"11\" t:bottom=\"1\" t:right=\"17\" /><t:Block t:top=\"0\" t:left=\"18\" t:bottom=\"2\" t:right=\"18\" /><t:Block t:top=\"0\" t:left=\"19\" t:bottom=\"2\" t:right=\"19\" /><t:Block t:top=\"0\" t:left=\"20\" t:bottom=\"2\" t:right=\"20\" /><t:Block t:top=\"0\" t:left=\"21\" t:bottom=\"2\" t:right=\"21\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrysContract.setFormatXml(resHelper.translateString("kdtEntrysContract",kdtEntrysContractStrXML));
        this.kdtEntrysContract.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEntrysContract_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

                this.kdtEntrysContract.putBindContents("editData",new String[] {"id","programmingName","Contract","ContractBillId","Amount","ContractAmount","designChangeAmount","siteCertificatAmount","settlementAmount","notextContract","sumB","onWayCost","curMonthEstimateChange","EstimateChangeBalance","curMonthOtherAmount","otherAmount","unSignContract","sumC","dynamicCost","diffAmount","diffRate","Comment","curMonthOtherId","ForecastChangeVisID","programmingId"});


        this.kdtEntrysContract.checkParsed();
        KDTextArea kdtEntrysContract_programmingName_TextArea = new KDTextArea();
        kdtEntrysContract_programmingName_TextArea.setName("kdtEntrysContract_programmingName_TextArea");
        kdtEntrysContract_programmingName_TextArea.setMaxLength(500);
        KDTDefaultCellEditor kdtEntrysContract_programmingName_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_programmingName_TextArea);
        this.kdtEntrysContract.getColumn("programmingName").setEditor(kdtEntrysContract_programmingName_CellEditor);
        KDTextField kdtEntrysContract_Contract_TextField = new KDTextField();
        kdtEntrysContract_Contract_TextField.setName("kdtEntrysContract_Contract_TextField");
        kdtEntrysContract_Contract_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrysContract_Contract_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_Contract_TextField);
        this.kdtEntrysContract.getColumn("Contract").setEditor(kdtEntrysContract_Contract_CellEditor);
        KDTextField kdtEntrysContract_ContractBillId_TextField = new KDTextField();
        kdtEntrysContract_ContractBillId_TextField.setName("kdtEntrysContract_ContractBillId_TextField");
        kdtEntrysContract_ContractBillId_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrysContract_ContractBillId_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_ContractBillId_TextField);
        this.kdtEntrysContract.getColumn("ContractBillId").setEditor(kdtEntrysContract_ContractBillId_CellEditor);
        KDFormattedTextField kdtEntrysContract_Amount_TextField = new KDFormattedTextField();
        kdtEntrysContract_Amount_TextField.setName("kdtEntrysContract_Amount_TextField");
        kdtEntrysContract_Amount_TextField.setVisible(true);
        kdtEntrysContract_Amount_TextField.setEditable(true);
        kdtEntrysContract_Amount_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_Amount_TextField.setDataType(1);
        	kdtEntrysContract_Amount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_Amount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_Amount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_Amount_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_Amount_TextField);
        this.kdtEntrysContract.getColumn("Amount").setEditor(kdtEntrysContract_Amount_CellEditor);
        KDFormattedTextField kdtEntrysContract_ContractAmount_TextField = new KDFormattedTextField();
        kdtEntrysContract_ContractAmount_TextField.setName("kdtEntrysContract_ContractAmount_TextField");
        kdtEntrysContract_ContractAmount_TextField.setVisible(true);
        kdtEntrysContract_ContractAmount_TextField.setEditable(true);
        kdtEntrysContract_ContractAmount_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_ContractAmount_TextField.setDataType(1);
        	kdtEntrysContract_ContractAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_ContractAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_ContractAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_ContractAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_ContractAmount_TextField);
        this.kdtEntrysContract.getColumn("ContractAmount").setEditor(kdtEntrysContract_ContractAmount_CellEditor);
        KDTextField kdtEntrysContract_designChangeAmount_TextField = new KDTextField();
        kdtEntrysContract_designChangeAmount_TextField.setName("kdtEntrysContract_designChangeAmount_TextField");
        kdtEntrysContract_designChangeAmount_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrysContract_designChangeAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_designChangeAmount_TextField);
        this.kdtEntrysContract.getColumn("designChangeAmount").setEditor(kdtEntrysContract_designChangeAmount_CellEditor);
        KDFormattedTextField kdtEntrysContract_siteCertificatAmount_TextField = new KDFormattedTextField();
        kdtEntrysContract_siteCertificatAmount_TextField.setName("kdtEntrysContract_siteCertificatAmount_TextField");
        kdtEntrysContract_siteCertificatAmount_TextField.setVisible(true);
        kdtEntrysContract_siteCertificatAmount_TextField.setEditable(true);
        kdtEntrysContract_siteCertificatAmount_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_siteCertificatAmount_TextField.setDataType(1);
        	kdtEntrysContract_siteCertificatAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_siteCertificatAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_siteCertificatAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_siteCertificatAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_siteCertificatAmount_TextField);
        this.kdtEntrysContract.getColumn("siteCertificatAmount").setEditor(kdtEntrysContract_siteCertificatAmount_CellEditor);
        KDFormattedTextField kdtEntrysContract_settlementAmount_TextField = new KDFormattedTextField();
        kdtEntrysContract_settlementAmount_TextField.setName("kdtEntrysContract_settlementAmount_TextField");
        kdtEntrysContract_settlementAmount_TextField.setVisible(true);
        kdtEntrysContract_settlementAmount_TextField.setEditable(true);
        kdtEntrysContract_settlementAmount_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_settlementAmount_TextField.setDataType(1);
        	kdtEntrysContract_settlementAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_settlementAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_settlementAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_settlementAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_settlementAmount_TextField);
        this.kdtEntrysContract.getColumn("settlementAmount").setEditor(kdtEntrysContract_settlementAmount_CellEditor);
        KDFormattedTextField kdtEntrysContract_notextContract_TextField = new KDFormattedTextField();
        kdtEntrysContract_notextContract_TextField.setName("kdtEntrysContract_notextContract_TextField");
        kdtEntrysContract_notextContract_TextField.setVisible(true);
        kdtEntrysContract_notextContract_TextField.setEditable(true);
        kdtEntrysContract_notextContract_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_notextContract_TextField.setDataType(1);
        kdtEntrysContract_notextContract_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_notextContract_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_notextContract_TextField);
        this.kdtEntrysContract.getColumn("notextContract").setEditor(kdtEntrysContract_notextContract_CellEditor);
        KDFormattedTextField kdtEntrysContract_sumB_TextField = new KDFormattedTextField();
        kdtEntrysContract_sumB_TextField.setName("kdtEntrysContract_sumB_TextField");
        kdtEntrysContract_sumB_TextField.setVisible(true);
        kdtEntrysContract_sumB_TextField.setEditable(true);
        kdtEntrysContract_sumB_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_sumB_TextField.setDataType(1);
        	kdtEntrysContract_sumB_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_sumB_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_sumB_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_sumB_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_sumB_TextField);
        this.kdtEntrysContract.getColumn("sumB").setEditor(kdtEntrysContract_sumB_CellEditor);
        KDFormattedTextField kdtEntrysContract_onWayCost_TextField = new KDFormattedTextField();
        kdtEntrysContract_onWayCost_TextField.setName("kdtEntrysContract_onWayCost_TextField");
        kdtEntrysContract_onWayCost_TextField.setVisible(true);
        kdtEntrysContract_onWayCost_TextField.setEditable(true);
        kdtEntrysContract_onWayCost_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_onWayCost_TextField.setDataType(1);
        	kdtEntrysContract_onWayCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_onWayCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_onWayCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_onWayCost_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_onWayCost_TextField);
        this.kdtEntrysContract.getColumn("onWayCost").setEditor(kdtEntrysContract_onWayCost_CellEditor);
        KDFormattedTextField kdtEntrysContract_curMonthEstimateChange_TextField = new KDFormattedTextField();
        kdtEntrysContract_curMonthEstimateChange_TextField.setName("kdtEntrysContract_curMonthEstimateChange_TextField");
        kdtEntrysContract_curMonthEstimateChange_TextField.setVisible(true);
        kdtEntrysContract_curMonthEstimateChange_TextField.setEditable(true);
        kdtEntrysContract_curMonthEstimateChange_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_curMonthEstimateChange_TextField.setDataType(1);
        	kdtEntrysContract_curMonthEstimateChange_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_curMonthEstimateChange_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_curMonthEstimateChange_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_curMonthEstimateChange_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_curMonthEstimateChange_TextField);
        this.kdtEntrysContract.getColumn("curMonthEstimateChange").setEditor(kdtEntrysContract_curMonthEstimateChange_CellEditor);
        KDFormattedTextField kdtEntrysContract_EstimateChangeBalance_TextField = new KDFormattedTextField();
        kdtEntrysContract_EstimateChangeBalance_TextField.setName("kdtEntrysContract_EstimateChangeBalance_TextField");
        kdtEntrysContract_EstimateChangeBalance_TextField.setVisible(true);
        kdtEntrysContract_EstimateChangeBalance_TextField.setEditable(true);
        kdtEntrysContract_EstimateChangeBalance_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_EstimateChangeBalance_TextField.setDataType(1);
        	kdtEntrysContract_EstimateChangeBalance_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_EstimateChangeBalance_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_EstimateChangeBalance_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_EstimateChangeBalance_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_EstimateChangeBalance_TextField);
        this.kdtEntrysContract.getColumn("EstimateChangeBalance").setEditor(kdtEntrysContract_EstimateChangeBalance_CellEditor);
        KDFormattedTextField kdtEntrysContract_curMonthOtherAmount_TextField = new KDFormattedTextField();
        kdtEntrysContract_curMonthOtherAmount_TextField.setName("kdtEntrysContract_curMonthOtherAmount_TextField");
        kdtEntrysContract_curMonthOtherAmount_TextField.setVisible(true);
        kdtEntrysContract_curMonthOtherAmount_TextField.setEditable(true);
        kdtEntrysContract_curMonthOtherAmount_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_curMonthOtherAmount_TextField.setDataType(1);
        	kdtEntrysContract_curMonthOtherAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_curMonthOtherAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_curMonthOtherAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_curMonthOtherAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_curMonthOtherAmount_TextField);
        this.kdtEntrysContract.getColumn("curMonthOtherAmount").setEditor(kdtEntrysContract_curMonthOtherAmount_CellEditor);
        KDFormattedTextField kdtEntrysContract_otherAmount_TextField = new KDFormattedTextField();
        kdtEntrysContract_otherAmount_TextField.setName("kdtEntrysContract_otherAmount_TextField");
        kdtEntrysContract_otherAmount_TextField.setVisible(true);
        kdtEntrysContract_otherAmount_TextField.setEditable(true);
        kdtEntrysContract_otherAmount_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_otherAmount_TextField.setDataType(1);
        	kdtEntrysContract_otherAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_otherAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_otherAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_otherAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_otherAmount_TextField);
        this.kdtEntrysContract.getColumn("otherAmount").setEditor(kdtEntrysContract_otherAmount_CellEditor);
        KDFormattedTextField kdtEntrysContract_unSignContract_TextField = new KDFormattedTextField();
        kdtEntrysContract_unSignContract_TextField.setName("kdtEntrysContract_unSignContract_TextField");
        kdtEntrysContract_unSignContract_TextField.setVisible(true);
        kdtEntrysContract_unSignContract_TextField.setEditable(true);
        kdtEntrysContract_unSignContract_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_unSignContract_TextField.setDataType(1);
        	kdtEntrysContract_unSignContract_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_unSignContract_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_unSignContract_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_unSignContract_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_unSignContract_TextField);
        this.kdtEntrysContract.getColumn("unSignContract").setEditor(kdtEntrysContract_unSignContract_CellEditor);
        KDFormattedTextField kdtEntrysContract_sumC_TextField = new KDFormattedTextField();
        kdtEntrysContract_sumC_TextField.setName("kdtEntrysContract_sumC_TextField");
        kdtEntrysContract_sumC_TextField.setVisible(true);
        kdtEntrysContract_sumC_TextField.setEditable(true);
        kdtEntrysContract_sumC_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_sumC_TextField.setDataType(1);
        	kdtEntrysContract_sumC_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_sumC_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_sumC_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_sumC_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_sumC_TextField);
        this.kdtEntrysContract.getColumn("sumC").setEditor(kdtEntrysContract_sumC_CellEditor);
        KDFormattedTextField kdtEntrysContract_dynamicCost_TextField = new KDFormattedTextField();
        kdtEntrysContract_dynamicCost_TextField.setName("kdtEntrysContract_dynamicCost_TextField");
        kdtEntrysContract_dynamicCost_TextField.setVisible(true);
        kdtEntrysContract_dynamicCost_TextField.setEditable(true);
        kdtEntrysContract_dynamicCost_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_dynamicCost_TextField.setDataType(1);
        	kdtEntrysContract_dynamicCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_dynamicCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_dynamicCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_dynamicCost_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_dynamicCost_TextField);
        this.kdtEntrysContract.getColumn("dynamicCost").setEditor(kdtEntrysContract_dynamicCost_CellEditor);
        KDFormattedTextField kdtEntrysContract_diffAmount_TextField = new KDFormattedTextField();
        kdtEntrysContract_diffAmount_TextField.setName("kdtEntrysContract_diffAmount_TextField");
        kdtEntrysContract_diffAmount_TextField.setVisible(true);
        kdtEntrysContract_diffAmount_TextField.setEditable(true);
        kdtEntrysContract_diffAmount_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_diffAmount_TextField.setDataType(1);
        	kdtEntrysContract_diffAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_diffAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_diffAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_diffAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_diffAmount_TextField);
        this.kdtEntrysContract.getColumn("diffAmount").setEditor(kdtEntrysContract_diffAmount_CellEditor);
        KDFormattedTextField kdtEntrysContract_diffRate_TextField = new KDFormattedTextField();
        kdtEntrysContract_diffRate_TextField.setName("kdtEntrysContract_diffRate_TextField");
        kdtEntrysContract_diffRate_TextField.setVisible(true);
        kdtEntrysContract_diffRate_TextField.setEditable(true);
        kdtEntrysContract_diffRate_TextField.setHorizontalAlignment(2);
        kdtEntrysContract_diffRate_TextField.setDataType(1);
        	kdtEntrysContract_diffRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysContract_diffRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysContract_diffRate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysContract_diffRate_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_diffRate_TextField);
        this.kdtEntrysContract.getColumn("diffRate").setEditor(kdtEntrysContract_diffRate_CellEditor);
        KDTextArea kdtEntrysContract_Comment_TextArea = new KDTextArea();
        kdtEntrysContract_Comment_TextArea.setName("kdtEntrysContract_Comment_TextArea");
        kdtEntrysContract_Comment_TextArea.setMaxLength(500);
        KDTDefaultCellEditor kdtEntrysContract_Comment_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_Comment_TextArea);
        this.kdtEntrysContract.getColumn("Comment").setEditor(kdtEntrysContract_Comment_CellEditor);
        KDTextField kdtEntrysContract_curMonthOtherId_TextField = new KDTextField();
        kdtEntrysContract_curMonthOtherId_TextField.setName("kdtEntrysContract_curMonthOtherId_TextField");
        kdtEntrysContract_curMonthOtherId_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrysContract_curMonthOtherId_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_curMonthOtherId_TextField);
        this.kdtEntrysContract.getColumn("curMonthOtherId").setEditor(kdtEntrysContract_curMonthOtherId_CellEditor);
        KDTextField kdtEntrysContract_ForecastChangeVisID_TextField = new KDTextField();
        kdtEntrysContract_ForecastChangeVisID_TextField.setName("kdtEntrysContract_ForecastChangeVisID_TextField");
        kdtEntrysContract_ForecastChangeVisID_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrysContract_ForecastChangeVisID_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_ForecastChangeVisID_TextField);
        this.kdtEntrysContract.getColumn("ForecastChangeVisID").setEditor(kdtEntrysContract_ForecastChangeVisID_CellEditor);
        KDTextField kdtEntrysContract_programmingId_TextField = new KDTextField();
        kdtEntrysContract_programmingId_TextField.setName("kdtEntrysContract_programmingId_TextField");
        kdtEntrysContract_programmingId_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrysContract_programmingId_CellEditor = new KDTDefaultCellEditor(kdtEntrysContract_programmingId_TextField);
        this.kdtEntrysContract.getColumn("programmingId").setEditor(kdtEntrysContract_programmingId_CellEditor);
        // kDTabbedPane2
        // kDPanel1
        // kDPanel2
        // kDPanel3
        // kDPanel4
        // kDPanel5
        // kDPanel6
        // kDPanel7
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costAccountNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"Contract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"designChangeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"siteCertificatAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"settlementAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"withouttxt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"sumB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"onWayCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"EstimateChangeBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"otherAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"unSignContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"sumC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"sumBC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"diffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"diffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"Comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{costAccountNumber}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{Contract}</t:Cell><t:Cell>$Resource{designChangeAmount}</t:Cell><t:Cell>$Resource{siteCertificatAmount}</t:Cell><t:Cell>$Resource{settlementAmount}</t:Cell><t:Cell>$Resource{withouttxt}</t:Cell><t:Cell>$Resource{sumB}</t:Cell><t:Cell>$Resource{onWayCost}</t:Cell><t:Cell>$Resource{EstimateChangeBalance}</t:Cell><t:Cell>$Resource{otherAmount}</t:Cell><t:Cell>$Resource{unSignContract}</t:Cell><t:Cell>$Resource{sumC}</t:Cell><t:Cell>$Resource{sumBC}</t:Cell><t:Cell>$Resource{diffAmount}</t:Cell><t:Cell>$Resource{diffRate}</t:Cell><t:Cell>$Resource{Comment}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row2}</t:Cell><t:Cell>$Resource{costAccount_Row2}</t:Cell><t:Cell>$Resource{costAccountNumber_Row2}</t:Cell><t:Cell>$Resource{aimCost_Row2}</t:Cell><t:Cell>$Resource{Contract_Row2}</t:Cell><t:Cell>$Resource{designChangeAmount_Row2}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row2}</t:Cell><t:Cell>$Resource{settlementAmount_Row2}</t:Cell><t:Cell>$Resource{withouttxt_Row2}</t:Cell><t:Cell>$Resource{sumB_Row2}</t:Cell><t:Cell>$Resource{onWayCost_Row2}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row2}</t:Cell><t:Cell>$Resource{otherAmount_Row2}</t:Cell><t:Cell>$Resource{unSignContract_Row2}</t:Cell><t:Cell>$Resource{sumC_Row2}</t:Cell><t:Cell>$Resource{sumBC_Row2}</t:Cell><t:Cell>$Resource{diffAmount_Row2}</t:Cell><t:Cell>$Resource{diffRate_Row2}</t:Cell><t:Cell>$Resource{Comment_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row3}</t:Cell><t:Cell>$Resource{costAccount_Row3}</t:Cell><t:Cell>$Resource{costAccountNumber_Row3}</t:Cell><t:Cell>$Resource{aimCost_Row3}</t:Cell><t:Cell>$Resource{Contract_Row3}</t:Cell><t:Cell>$Resource{designChangeAmount_Row3}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row3}</t:Cell><t:Cell>$Resource{settlementAmount_Row3}</t:Cell><t:Cell>$Resource{withouttxt_Row3}</t:Cell><t:Cell>$Resource{sumB_Row3}</t:Cell><t:Cell>$Resource{onWayCost_Row3}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row3}</t:Cell><t:Cell>$Resource{otherAmount_Row3}</t:Cell><t:Cell>$Resource{unSignContract_Row3}</t:Cell><t:Cell>$Resource{sumC_Row3}</t:Cell><t:Cell>$Resource{sumBC_Row3}</t:Cell><t:Cell>$Resource{diffAmount_Row3}</t:Cell><t:Cell>$Resource{diffRate_Row3}</t:Cell><t:Cell>$Resource{Comment_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"2\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"0\" t:right=\"14\" /><t:Block t:top=\"1\" t:left=\"4\" t:bottom=\"1\" t:right=\"9\" /><t:Block t:top=\"1\" t:left=\"10\" t:bottom=\"1\" t:right=\"14\" /><t:Block t:top=\"0\" t:left=\"15\" t:bottom=\"2\" t:right=\"15\" /><t:Block t:top=\"0\" t:left=\"16\" t:bottom=\"2\" t:right=\"16\" /><t:Block t:top=\"0\" t:left=\"17\" t:bottom=\"2\" t:right=\"17\" /><t:Block t:top=\"0\" t:left=\"18\" t:bottom=\"2\" t:right=\"18\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));

        

        this.kDTable1.checkParsed();
        // kDTable2
		String kDTable2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costAccountNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"Contract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"designChangeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"siteCertificatAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"settlementAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"withouttxt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"sumB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"onWayCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"EstimateChangeBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"otherAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"unSignContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"sumC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"sumBC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"diffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"diffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"Comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{costAccountNumber}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{Contract}</t:Cell><t:Cell>$Resource{designChangeAmount}</t:Cell><t:Cell>$Resource{siteCertificatAmount}</t:Cell><t:Cell>$Resource{settlementAmount}</t:Cell><t:Cell>$Resource{withouttxt}</t:Cell><t:Cell>$Resource{sumB}</t:Cell><t:Cell>$Resource{onWayCost}</t:Cell><t:Cell>$Resource{EstimateChangeBalance}</t:Cell><t:Cell>$Resource{otherAmount}</t:Cell><t:Cell>$Resource{unSignContract}</t:Cell><t:Cell>$Resource{sumC}</t:Cell><t:Cell>$Resource{sumBC}</t:Cell><t:Cell>$Resource{diffAmount}</t:Cell><t:Cell>$Resource{diffRate}</t:Cell><t:Cell>$Resource{Comment}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row2}</t:Cell><t:Cell>$Resource{costAccount_Row2}</t:Cell><t:Cell>$Resource{costAccountNumber_Row2}</t:Cell><t:Cell>$Resource{aimCost_Row2}</t:Cell><t:Cell>$Resource{Contract_Row2}</t:Cell><t:Cell>$Resource{designChangeAmount_Row2}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row2}</t:Cell><t:Cell>$Resource{settlementAmount_Row2}</t:Cell><t:Cell>$Resource{withouttxt_Row2}</t:Cell><t:Cell>$Resource{sumB_Row2}</t:Cell><t:Cell>$Resource{onWayCost_Row2}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row2}</t:Cell><t:Cell>$Resource{otherAmount_Row2}</t:Cell><t:Cell>$Resource{unSignContract_Row2}</t:Cell><t:Cell>$Resource{sumC_Row2}</t:Cell><t:Cell>$Resource{sumBC_Row2}</t:Cell><t:Cell>$Resource{diffAmount_Row2}</t:Cell><t:Cell>$Resource{diffRate_Row2}</t:Cell><t:Cell>$Resource{Comment_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row3}</t:Cell><t:Cell>$Resource{costAccount_Row3}</t:Cell><t:Cell>$Resource{costAccountNumber_Row3}</t:Cell><t:Cell>$Resource{aimCost_Row3}</t:Cell><t:Cell>$Resource{Contract_Row3}</t:Cell><t:Cell>$Resource{designChangeAmount_Row3}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row3}</t:Cell><t:Cell>$Resource{settlementAmount_Row3}</t:Cell><t:Cell>$Resource{withouttxt_Row3}</t:Cell><t:Cell>$Resource{sumB_Row3}</t:Cell><t:Cell>$Resource{onWayCost_Row3}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row3}</t:Cell><t:Cell>$Resource{otherAmount_Row3}</t:Cell><t:Cell>$Resource{unSignContract_Row3}</t:Cell><t:Cell>$Resource{sumC_Row3}</t:Cell><t:Cell>$Resource{sumBC_Row3}</t:Cell><t:Cell>$Resource{diffAmount_Row3}</t:Cell><t:Cell>$Resource{diffRate_Row3}</t:Cell><t:Cell>$Resource{Comment_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"2\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"0\" t:right=\"14\" /><t:Block t:top=\"1\" t:left=\"4\" t:bottom=\"1\" t:right=\"9\" /><t:Block t:top=\"1\" t:left=\"10\" t:bottom=\"1\" t:right=\"14\" /><t:Block t:top=\"0\" t:left=\"15\" t:bottom=\"2\" t:right=\"15\" /><t:Block t:top=\"0\" t:left=\"16\" t:bottom=\"2\" t:right=\"16\" /><t:Block t:top=\"0\" t:left=\"17\" t:bottom=\"2\" t:right=\"17\" /><t:Block t:top=\"0\" t:left=\"18\" t:bottom=\"2\" t:right=\"18\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable2.setFormatXml(resHelper.translateString("kDTable2",kDTable2StrXML));

        

        this.kDTable2.checkParsed();
        // kDTable3
		String kDTable3StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costAccountNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"Contract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"designChangeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"siteCertificatAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"settlementAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"withouttxt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"sumB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"onWayCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"EstimateChangeBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"otherAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"unSignContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"sumC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"sumBC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"diffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"diffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"Comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{costAccountNumber}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{Contract}</t:Cell><t:Cell>$Resource{designChangeAmount}</t:Cell><t:Cell>$Resource{siteCertificatAmount}</t:Cell><t:Cell>$Resource{settlementAmount}</t:Cell><t:Cell>$Resource{withouttxt}</t:Cell><t:Cell>$Resource{sumB}</t:Cell><t:Cell>$Resource{onWayCost}</t:Cell><t:Cell>$Resource{EstimateChangeBalance}</t:Cell><t:Cell>$Resource{otherAmount}</t:Cell><t:Cell>$Resource{unSignContract}</t:Cell><t:Cell>$Resource{sumC}</t:Cell><t:Cell>$Resource{sumBC}</t:Cell><t:Cell>$Resource{diffAmount}</t:Cell><t:Cell>$Resource{diffRate}</t:Cell><t:Cell>$Resource{Comment}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row2}</t:Cell><t:Cell>$Resource{costAccount_Row2}</t:Cell><t:Cell>$Resource{costAccountNumber_Row2}</t:Cell><t:Cell>$Resource{aimCost_Row2}</t:Cell><t:Cell>$Resource{Contract_Row2}</t:Cell><t:Cell>$Resource{designChangeAmount_Row2}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row2}</t:Cell><t:Cell>$Resource{settlementAmount_Row2}</t:Cell><t:Cell>$Resource{withouttxt_Row2}</t:Cell><t:Cell>$Resource{sumB_Row2}</t:Cell><t:Cell>$Resource{onWayCost_Row2}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row2}</t:Cell><t:Cell>$Resource{otherAmount_Row2}</t:Cell><t:Cell>$Resource{unSignContract_Row2}</t:Cell><t:Cell>$Resource{sumC_Row2}</t:Cell><t:Cell>$Resource{sumBC_Row2}</t:Cell><t:Cell>$Resource{diffAmount_Row2}</t:Cell><t:Cell>$Resource{diffRate_Row2}</t:Cell><t:Cell>$Resource{Comment_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row3}</t:Cell><t:Cell>$Resource{costAccount_Row3}</t:Cell><t:Cell>$Resource{costAccountNumber_Row3}</t:Cell><t:Cell>$Resource{aimCost_Row3}</t:Cell><t:Cell>$Resource{Contract_Row3}</t:Cell><t:Cell>$Resource{designChangeAmount_Row3}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row3}</t:Cell><t:Cell>$Resource{settlementAmount_Row3}</t:Cell><t:Cell>$Resource{withouttxt_Row3}</t:Cell><t:Cell>$Resource{sumB_Row3}</t:Cell><t:Cell>$Resource{onWayCost_Row3}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row3}</t:Cell><t:Cell>$Resource{otherAmount_Row3}</t:Cell><t:Cell>$Resource{unSignContract_Row3}</t:Cell><t:Cell>$Resource{sumC_Row3}</t:Cell><t:Cell>$Resource{sumBC_Row3}</t:Cell><t:Cell>$Resource{diffAmount_Row3}</t:Cell><t:Cell>$Resource{diffRate_Row3}</t:Cell><t:Cell>$Resource{Comment_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"2\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"0\" t:right=\"14\" /><t:Block t:top=\"1\" t:left=\"4\" t:bottom=\"1\" t:right=\"9\" /><t:Block t:top=\"1\" t:left=\"10\" t:bottom=\"1\" t:right=\"14\" /><t:Block t:top=\"0\" t:left=\"15\" t:bottom=\"2\" t:right=\"15\" /><t:Block t:top=\"0\" t:left=\"16\" t:bottom=\"2\" t:right=\"16\" /><t:Block t:top=\"0\" t:left=\"17\" t:bottom=\"2\" t:right=\"17\" /><t:Block t:top=\"0\" t:left=\"18\" t:bottom=\"2\" t:right=\"18\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable3.setFormatXml(resHelper.translateString("kDTable3",kDTable3StrXML));

        

        this.kDTable3.checkParsed();
        // kDTable4
		String kDTable4StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costAccountNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"Contract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"designChangeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"siteCertificatAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"settlementAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"withouttxt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"sumB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"onWayCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"EstimateChangeBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"otherAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"unSignContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"sumC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"sumBC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"diffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"diffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"Comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{costAccountNumber}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{Contract}</t:Cell><t:Cell>$Resource{designChangeAmount}</t:Cell><t:Cell>$Resource{siteCertificatAmount}</t:Cell><t:Cell>$Resource{settlementAmount}</t:Cell><t:Cell>$Resource{withouttxt}</t:Cell><t:Cell>$Resource{sumB}</t:Cell><t:Cell>$Resource{onWayCost}</t:Cell><t:Cell>$Resource{EstimateChangeBalance}</t:Cell><t:Cell>$Resource{otherAmount}</t:Cell><t:Cell>$Resource{unSignContract}</t:Cell><t:Cell>$Resource{sumC}</t:Cell><t:Cell>$Resource{sumBC}</t:Cell><t:Cell>$Resource{diffAmount}</t:Cell><t:Cell>$Resource{diffRate}</t:Cell><t:Cell>$Resource{Comment}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row2}</t:Cell><t:Cell>$Resource{costAccount_Row2}</t:Cell><t:Cell>$Resource{costAccountNumber_Row2}</t:Cell><t:Cell>$Resource{aimCost_Row2}</t:Cell><t:Cell>$Resource{Contract_Row2}</t:Cell><t:Cell>$Resource{designChangeAmount_Row2}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row2}</t:Cell><t:Cell>$Resource{settlementAmount_Row2}</t:Cell><t:Cell>$Resource{withouttxt_Row2}</t:Cell><t:Cell>$Resource{sumB_Row2}</t:Cell><t:Cell>$Resource{onWayCost_Row2}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row2}</t:Cell><t:Cell>$Resource{otherAmount_Row2}</t:Cell><t:Cell>$Resource{unSignContract_Row2}</t:Cell><t:Cell>$Resource{sumC_Row2}</t:Cell><t:Cell>$Resource{sumBC_Row2}</t:Cell><t:Cell>$Resource{diffAmount_Row2}</t:Cell><t:Cell>$Resource{diffRate_Row2}</t:Cell><t:Cell>$Resource{Comment_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row3}</t:Cell><t:Cell>$Resource{costAccount_Row3}</t:Cell><t:Cell>$Resource{costAccountNumber_Row3}</t:Cell><t:Cell>$Resource{aimCost_Row3}</t:Cell><t:Cell>$Resource{Contract_Row3}</t:Cell><t:Cell>$Resource{designChangeAmount_Row3}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row3}</t:Cell><t:Cell>$Resource{settlementAmount_Row3}</t:Cell><t:Cell>$Resource{withouttxt_Row3}</t:Cell><t:Cell>$Resource{sumB_Row3}</t:Cell><t:Cell>$Resource{onWayCost_Row3}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row3}</t:Cell><t:Cell>$Resource{otherAmount_Row3}</t:Cell><t:Cell>$Resource{unSignContract_Row3}</t:Cell><t:Cell>$Resource{sumC_Row3}</t:Cell><t:Cell>$Resource{sumBC_Row3}</t:Cell><t:Cell>$Resource{diffAmount_Row3}</t:Cell><t:Cell>$Resource{diffRate_Row3}</t:Cell><t:Cell>$Resource{Comment_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"2\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"0\" t:right=\"14\" /><t:Block t:top=\"1\" t:left=\"4\" t:bottom=\"1\" t:right=\"9\" /><t:Block t:top=\"1\" t:left=\"10\" t:bottom=\"1\" t:right=\"14\" /><t:Block t:top=\"0\" t:left=\"15\" t:bottom=\"2\" t:right=\"15\" /><t:Block t:top=\"0\" t:left=\"16\" t:bottom=\"2\" t:right=\"16\" /><t:Block t:top=\"0\" t:left=\"17\" t:bottom=\"2\" t:right=\"17\" /><t:Block t:top=\"0\" t:left=\"18\" t:bottom=\"2\" t:right=\"18\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable4.setFormatXml(resHelper.translateString("kDTable4",kDTable4StrXML));

        

        this.kDTable4.checkParsed();
        // kDTable5
		String kDTable5StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costAccountNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"Contract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"designChangeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"siteCertificatAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"settlementAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"withouttxt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"sumB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"onWayCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"EstimateChangeBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"otherAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"unSignContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"sumC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"sumBC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"diffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"diffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"Comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{costAccountNumber}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{Contract}</t:Cell><t:Cell>$Resource{designChangeAmount}</t:Cell><t:Cell>$Resource{siteCertificatAmount}</t:Cell><t:Cell>$Resource{settlementAmount}</t:Cell><t:Cell>$Resource{withouttxt}</t:Cell><t:Cell>$Resource{sumB}</t:Cell><t:Cell>$Resource{onWayCost}</t:Cell><t:Cell>$Resource{EstimateChangeBalance}</t:Cell><t:Cell>$Resource{otherAmount}</t:Cell><t:Cell>$Resource{unSignContract}</t:Cell><t:Cell>$Resource{sumC}</t:Cell><t:Cell>$Resource{sumBC}</t:Cell><t:Cell>$Resource{diffAmount}</t:Cell><t:Cell>$Resource{diffRate}</t:Cell><t:Cell>$Resource{Comment}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row2}</t:Cell><t:Cell>$Resource{costAccount_Row2}</t:Cell><t:Cell>$Resource{costAccountNumber_Row2}</t:Cell><t:Cell>$Resource{aimCost_Row2}</t:Cell><t:Cell>$Resource{Contract_Row2}</t:Cell><t:Cell>$Resource{designChangeAmount_Row2}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row2}</t:Cell><t:Cell>$Resource{settlementAmount_Row2}</t:Cell><t:Cell>$Resource{withouttxt_Row2}</t:Cell><t:Cell>$Resource{sumB_Row2}</t:Cell><t:Cell>$Resource{onWayCost_Row2}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row2}</t:Cell><t:Cell>$Resource{otherAmount_Row2}</t:Cell><t:Cell>$Resource{unSignContract_Row2}</t:Cell><t:Cell>$Resource{sumC_Row2}</t:Cell><t:Cell>$Resource{sumBC_Row2}</t:Cell><t:Cell>$Resource{diffAmount_Row2}</t:Cell><t:Cell>$Resource{diffRate_Row2}</t:Cell><t:Cell>$Resource{Comment_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row3}</t:Cell><t:Cell>$Resource{costAccount_Row3}</t:Cell><t:Cell>$Resource{costAccountNumber_Row3}</t:Cell><t:Cell>$Resource{aimCost_Row3}</t:Cell><t:Cell>$Resource{Contract_Row3}</t:Cell><t:Cell>$Resource{designChangeAmount_Row3}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row3}</t:Cell><t:Cell>$Resource{settlementAmount_Row3}</t:Cell><t:Cell>$Resource{withouttxt_Row3}</t:Cell><t:Cell>$Resource{sumB_Row3}</t:Cell><t:Cell>$Resource{onWayCost_Row3}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row3}</t:Cell><t:Cell>$Resource{otherAmount_Row3}</t:Cell><t:Cell>$Resource{unSignContract_Row3}</t:Cell><t:Cell>$Resource{sumC_Row3}</t:Cell><t:Cell>$Resource{sumBC_Row3}</t:Cell><t:Cell>$Resource{diffAmount_Row3}</t:Cell><t:Cell>$Resource{diffRate_Row3}</t:Cell><t:Cell>$Resource{Comment_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"2\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"0\" t:right=\"14\" /><t:Block t:top=\"1\" t:left=\"4\" t:bottom=\"1\" t:right=\"9\" /><t:Block t:top=\"1\" t:left=\"10\" t:bottom=\"1\" t:right=\"14\" /><t:Block t:top=\"0\" t:left=\"15\" t:bottom=\"2\" t:right=\"15\" /><t:Block t:top=\"0\" t:left=\"16\" t:bottom=\"2\" t:right=\"16\" /><t:Block t:top=\"0\" t:left=\"17\" t:bottom=\"2\" t:right=\"17\" /><t:Block t:top=\"0\" t:left=\"18\" t:bottom=\"2\" t:right=\"18\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable5.setFormatXml(resHelper.translateString("kDTable5",kDTable5StrXML));

        

        this.kDTable5.checkParsed();
        // kDTable6
		String kDTable6StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costAccountNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"Contract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"designChangeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"siteCertificatAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"settlementAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"withouttxt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"sumB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"onWayCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"EstimateChangeBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"otherAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"unSignContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"sumC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"sumBC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"diffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"diffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"Comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{costAccountNumber}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{Contract}</t:Cell><t:Cell>$Resource{designChangeAmount}</t:Cell><t:Cell>$Resource{siteCertificatAmount}</t:Cell><t:Cell>$Resource{settlementAmount}</t:Cell><t:Cell>$Resource{withouttxt}</t:Cell><t:Cell>$Resource{sumB}</t:Cell><t:Cell>$Resource{onWayCost}</t:Cell><t:Cell>$Resource{EstimateChangeBalance}</t:Cell><t:Cell>$Resource{otherAmount}</t:Cell><t:Cell>$Resource{unSignContract}</t:Cell><t:Cell>$Resource{sumC}</t:Cell><t:Cell>$Resource{sumBC}</t:Cell><t:Cell>$Resource{diffAmount}</t:Cell><t:Cell>$Resource{diffRate}</t:Cell><t:Cell>$Resource{Comment}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row2}</t:Cell><t:Cell>$Resource{costAccount_Row2}</t:Cell><t:Cell>$Resource{costAccountNumber_Row2}</t:Cell><t:Cell>$Resource{aimCost_Row2}</t:Cell><t:Cell>$Resource{Contract_Row2}</t:Cell><t:Cell>$Resource{designChangeAmount_Row2}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row2}</t:Cell><t:Cell>$Resource{settlementAmount_Row2}</t:Cell><t:Cell>$Resource{withouttxt_Row2}</t:Cell><t:Cell>$Resource{sumB_Row2}</t:Cell><t:Cell>$Resource{onWayCost_Row2}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row2}</t:Cell><t:Cell>$Resource{otherAmount_Row2}</t:Cell><t:Cell>$Resource{unSignContract_Row2}</t:Cell><t:Cell>$Resource{sumC_Row2}</t:Cell><t:Cell>$Resource{sumBC_Row2}</t:Cell><t:Cell>$Resource{diffAmount_Row2}</t:Cell><t:Cell>$Resource{diffRate_Row2}</t:Cell><t:Cell>$Resource{Comment_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row3}</t:Cell><t:Cell>$Resource{costAccount_Row3}</t:Cell><t:Cell>$Resource{costAccountNumber_Row3}</t:Cell><t:Cell>$Resource{aimCost_Row3}</t:Cell><t:Cell>$Resource{Contract_Row3}</t:Cell><t:Cell>$Resource{designChangeAmount_Row3}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row3}</t:Cell><t:Cell>$Resource{settlementAmount_Row3}</t:Cell><t:Cell>$Resource{withouttxt_Row3}</t:Cell><t:Cell>$Resource{sumB_Row3}</t:Cell><t:Cell>$Resource{onWayCost_Row3}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row3}</t:Cell><t:Cell>$Resource{otherAmount_Row3}</t:Cell><t:Cell>$Resource{unSignContract_Row3}</t:Cell><t:Cell>$Resource{sumC_Row3}</t:Cell><t:Cell>$Resource{sumBC_Row3}</t:Cell><t:Cell>$Resource{diffAmount_Row3}</t:Cell><t:Cell>$Resource{diffRate_Row3}</t:Cell><t:Cell>$Resource{Comment_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"2\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"0\" t:right=\"14\" /><t:Block t:top=\"1\" t:left=\"4\" t:bottom=\"1\" t:right=\"9\" /><t:Block t:top=\"1\" t:left=\"10\" t:bottom=\"1\" t:right=\"14\" /><t:Block t:top=\"0\" t:left=\"15\" t:bottom=\"2\" t:right=\"15\" /><t:Block t:top=\"0\" t:left=\"16\" t:bottom=\"2\" t:right=\"16\" /><t:Block t:top=\"0\" t:left=\"17\" t:bottom=\"2\" t:right=\"17\" /><t:Block t:top=\"0\" t:left=\"18\" t:bottom=\"2\" t:right=\"18\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable6.setFormatXml(resHelper.translateString("kDTable6",kDTable6StrXML));

        

        this.kDTable6.checkParsed();
        // kDContainer1
        // kdtEntrysAccount
		String kdtEntrysAccountStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol20\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol21\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costAccountNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"Contract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"designChangeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"siteCertificatAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"settlementAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"withouttxt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"sumB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"onWayCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"EstimateChangeBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"otherAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"unSignContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"sumC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"sumBC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"diffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"diffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"Comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"accountIndex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol20\" /><t:Column t:key=\"alertIndex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol21\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{costAccountNumber}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{Contract}</t:Cell><t:Cell>$Resource{designChangeAmount}</t:Cell><t:Cell>$Resource{siteCertificatAmount}</t:Cell><t:Cell>$Resource{settlementAmount}</t:Cell><t:Cell>$Resource{withouttxt}</t:Cell><t:Cell>$Resource{sumB}</t:Cell><t:Cell>$Resource{onWayCost}</t:Cell><t:Cell>$Resource{EstimateChangeBalance}</t:Cell><t:Cell>$Resource{otherAmount}</t:Cell><t:Cell>$Resource{unSignContract}</t:Cell><t:Cell>$Resource{sumC}</t:Cell><t:Cell>$Resource{sumBC}</t:Cell><t:Cell>$Resource{diffAmount}</t:Cell><t:Cell>$Resource{diffRate}</t:Cell><t:Cell>$Resource{Comment}</t:Cell><t:Cell>$Resource{accountIndex}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{alertIndex}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row2}</t:Cell><t:Cell>$Resource{costAccount_Row2}</t:Cell><t:Cell>$Resource{costAccountNumber_Row2}</t:Cell><t:Cell>$Resource{aimCost_Row2}</t:Cell><t:Cell>$Resource{Contract_Row2}</t:Cell><t:Cell>$Resource{designChangeAmount_Row2}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row2}</t:Cell><t:Cell>$Resource{settlementAmount_Row2}</t:Cell><t:Cell>$Resource{withouttxt_Row2}</t:Cell><t:Cell>$Resource{sumB_Row2}</t:Cell><t:Cell>$Resource{onWayCost_Row2}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row2}</t:Cell><t:Cell>$Resource{otherAmount_Row2}</t:Cell><t:Cell>$Resource{unSignContract_Row2}</t:Cell><t:Cell>$Resource{sumC_Row2}</t:Cell><t:Cell>$Resource{sumBC_Row2}</t:Cell><t:Cell>$Resource{diffAmount_Row2}</t:Cell><t:Cell>$Resource{diffRate_Row2}</t:Cell><t:Cell>$Resource{Comment_Row2}</t:Cell><t:Cell>$Resource{accountIndex_Row2}</t:Cell><t:Cell>$Resource{level_Row2}</t:Cell><t:Cell>$Resource{alertIndex_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row3}</t:Cell><t:Cell>$Resource{costAccount_Row3}</t:Cell><t:Cell>$Resource{costAccountNumber_Row3}</t:Cell><t:Cell>$Resource{aimCost_Row3}</t:Cell><t:Cell>$Resource{Contract_Row3}</t:Cell><t:Cell>$Resource{designChangeAmount_Row3}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row3}</t:Cell><t:Cell>$Resource{settlementAmount_Row3}</t:Cell><t:Cell>$Resource{withouttxt_Row3}</t:Cell><t:Cell>$Resource{sumB_Row3}</t:Cell><t:Cell>$Resource{onWayCost_Row3}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row3}</t:Cell><t:Cell>$Resource{otherAmount_Row3}</t:Cell><t:Cell>$Resource{unSignContract_Row3}</t:Cell><t:Cell>$Resource{sumC_Row3}</t:Cell><t:Cell>$Resource{sumBC_Row3}</t:Cell><t:Cell>$Resource{diffAmount_Row3}</t:Cell><t:Cell>$Resource{diffRate_Row3}</t:Cell><t:Cell>$Resource{Comment_Row3}</t:Cell><t:Cell>$Resource{accountIndex_Row3}</t:Cell><t:Cell>$Resource{level_Row3}</t:Cell><t:Cell>$Resource{alertIndex_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"2\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"0\" t:right=\"14\" /><t:Block t:top=\"1\" t:left=\"4\" t:bottom=\"1\" t:right=\"9\" /><t:Block t:top=\"1\" t:left=\"10\" t:bottom=\"1\" t:right=\"14\" /><t:Block t:top=\"0\" t:left=\"15\" t:bottom=\"2\" t:right=\"15\" /><t:Block t:top=\"0\" t:left=\"16\" t:bottom=\"2\" t:right=\"16\" /><t:Block t:top=\"0\" t:left=\"17\" t:bottom=\"2\" t:right=\"17\" /><t:Block t:top=\"0\" t:left=\"18\" t:bottom=\"2\" t:right=\"18\" /><t:Block t:top=\"0\" t:left=\"21\" t:bottom=\"2\" t:right=\"21\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrysAccount.setFormatXml(resHelper.translateString("kdtEntrysAccount",kdtEntrysAccountStrXML));

                this.kdtEntrysAccount.putBindContents("editData",new String[] {"seq","costAccount","costAccountNumber","aimCost","Contract","designChangeAmount","siteCertificatAmount","settlementAmount","withouttxt","sumB","onWayCost","EstimateChangeBalance","otherAmount","unSignContract","sumC","sumBC","diffAmount","diffRate","Comment","accountIndex","level","alertIndex"});


        this.kdtEntrysAccount.checkParsed();
        KDTextField kdtEntrysAccount_costAccount_TextField = new KDTextField();
        kdtEntrysAccount_costAccount_TextField.setName("kdtEntrysAccount_costAccount_TextField");
        kdtEntrysAccount_costAccount_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrysAccount_costAccount_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_costAccount_TextField);
        this.kdtEntrysAccount.getColumn("costAccount").setEditor(kdtEntrysAccount_costAccount_CellEditor);
        KDTextField kdtEntrysAccount_costAccountNumber_TextField = new KDTextField();
        kdtEntrysAccount_costAccountNumber_TextField.setName("kdtEntrysAccount_costAccountNumber_TextField");
        kdtEntrysAccount_costAccountNumber_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrysAccount_costAccountNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_costAccountNumber_TextField);
        this.kdtEntrysAccount.getColumn("costAccountNumber").setEditor(kdtEntrysAccount_costAccountNumber_CellEditor);
        KDFormattedTextField kdtEntrysAccount_aimCost_TextField = new KDFormattedTextField();
        kdtEntrysAccount_aimCost_TextField.setName("kdtEntrysAccount_aimCost_TextField");
        kdtEntrysAccount_aimCost_TextField.setVisible(true);
        kdtEntrysAccount_aimCost_TextField.setEditable(true);
        kdtEntrysAccount_aimCost_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_aimCost_TextField.setDataType(1);
        	kdtEntrysAccount_aimCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_aimCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_aimCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_aimCost_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_aimCost_TextField);
        this.kdtEntrysAccount.getColumn("aimCost").setEditor(kdtEntrysAccount_aimCost_CellEditor);
        KDFormattedTextField kdtEntrysAccount_Contract_TextField = new KDFormattedTextField();
        kdtEntrysAccount_Contract_TextField.setName("kdtEntrysAccount_Contract_TextField");
        kdtEntrysAccount_Contract_TextField.setVisible(true);
        kdtEntrysAccount_Contract_TextField.setEditable(true);
        kdtEntrysAccount_Contract_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_Contract_TextField.setDataType(1);
        	kdtEntrysAccount_Contract_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_Contract_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_Contract_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_Contract_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_Contract_TextField);
        this.kdtEntrysAccount.getColumn("Contract").setEditor(kdtEntrysAccount_Contract_CellEditor);
        KDFormattedTextField kdtEntrysAccount_designChangeAmount_TextField = new KDFormattedTextField();
        kdtEntrysAccount_designChangeAmount_TextField.setName("kdtEntrysAccount_designChangeAmount_TextField");
        kdtEntrysAccount_designChangeAmount_TextField.setVisible(true);
        kdtEntrysAccount_designChangeAmount_TextField.setEditable(true);
        kdtEntrysAccount_designChangeAmount_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_designChangeAmount_TextField.setDataType(1);
        	kdtEntrysAccount_designChangeAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_designChangeAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_designChangeAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_designChangeAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_designChangeAmount_TextField);
        this.kdtEntrysAccount.getColumn("designChangeAmount").setEditor(kdtEntrysAccount_designChangeAmount_CellEditor);
        KDFormattedTextField kdtEntrysAccount_siteCertificatAmount_TextField = new KDFormattedTextField();
        kdtEntrysAccount_siteCertificatAmount_TextField.setName("kdtEntrysAccount_siteCertificatAmount_TextField");
        kdtEntrysAccount_siteCertificatAmount_TextField.setVisible(true);
        kdtEntrysAccount_siteCertificatAmount_TextField.setEditable(true);
        kdtEntrysAccount_siteCertificatAmount_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_siteCertificatAmount_TextField.setDataType(1);
        	kdtEntrysAccount_siteCertificatAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_siteCertificatAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_siteCertificatAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_siteCertificatAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_siteCertificatAmount_TextField);
        this.kdtEntrysAccount.getColumn("siteCertificatAmount").setEditor(kdtEntrysAccount_siteCertificatAmount_CellEditor);
        KDFormattedTextField kdtEntrysAccount_settlementAmount_TextField = new KDFormattedTextField();
        kdtEntrysAccount_settlementAmount_TextField.setName("kdtEntrysAccount_settlementAmount_TextField");
        kdtEntrysAccount_settlementAmount_TextField.setVisible(true);
        kdtEntrysAccount_settlementAmount_TextField.setEditable(true);
        kdtEntrysAccount_settlementAmount_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_settlementAmount_TextField.setDataType(1);
        	kdtEntrysAccount_settlementAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_settlementAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_settlementAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_settlementAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_settlementAmount_TextField);
        this.kdtEntrysAccount.getColumn("settlementAmount").setEditor(kdtEntrysAccount_settlementAmount_CellEditor);
        KDFormattedTextField kdtEntrysAccount_withouttxt_TextField = new KDFormattedTextField();
        kdtEntrysAccount_withouttxt_TextField.setName("kdtEntrysAccount_withouttxt_TextField");
        kdtEntrysAccount_withouttxt_TextField.setVisible(true);
        kdtEntrysAccount_withouttxt_TextField.setEditable(true);
        kdtEntrysAccount_withouttxt_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_withouttxt_TextField.setDataType(1);
        	kdtEntrysAccount_withouttxt_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_withouttxt_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_withouttxt_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_withouttxt_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_withouttxt_TextField);
        this.kdtEntrysAccount.getColumn("withouttxt").setEditor(kdtEntrysAccount_withouttxt_CellEditor);
        KDFormattedTextField kdtEntrysAccount_sumB_TextField = new KDFormattedTextField();
        kdtEntrysAccount_sumB_TextField.setName("kdtEntrysAccount_sumB_TextField");
        kdtEntrysAccount_sumB_TextField.setVisible(true);
        kdtEntrysAccount_sumB_TextField.setEditable(true);
        kdtEntrysAccount_sumB_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_sumB_TextField.setDataType(1);
        	kdtEntrysAccount_sumB_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_sumB_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_sumB_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_sumB_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_sumB_TextField);
        this.kdtEntrysAccount.getColumn("sumB").setEditor(kdtEntrysAccount_sumB_CellEditor);
        KDFormattedTextField kdtEntrysAccount_onWayCost_TextField = new KDFormattedTextField();
        kdtEntrysAccount_onWayCost_TextField.setName("kdtEntrysAccount_onWayCost_TextField");
        kdtEntrysAccount_onWayCost_TextField.setVisible(true);
        kdtEntrysAccount_onWayCost_TextField.setEditable(true);
        kdtEntrysAccount_onWayCost_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_onWayCost_TextField.setDataType(1);
        	kdtEntrysAccount_onWayCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_onWayCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_onWayCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_onWayCost_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_onWayCost_TextField);
        this.kdtEntrysAccount.getColumn("onWayCost").setEditor(kdtEntrysAccount_onWayCost_CellEditor);
        KDFormattedTextField kdtEntrysAccount_EstimateChangeBalance_TextField = new KDFormattedTextField();
        kdtEntrysAccount_EstimateChangeBalance_TextField.setName("kdtEntrysAccount_EstimateChangeBalance_TextField");
        kdtEntrysAccount_EstimateChangeBalance_TextField.setVisible(true);
        kdtEntrysAccount_EstimateChangeBalance_TextField.setEditable(true);
        kdtEntrysAccount_EstimateChangeBalance_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_EstimateChangeBalance_TextField.setDataType(1);
        	kdtEntrysAccount_EstimateChangeBalance_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_EstimateChangeBalance_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_EstimateChangeBalance_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_EstimateChangeBalance_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_EstimateChangeBalance_TextField);
        this.kdtEntrysAccount.getColumn("EstimateChangeBalance").setEditor(kdtEntrysAccount_EstimateChangeBalance_CellEditor);
        KDFormattedTextField kdtEntrysAccount_otherAmount_TextField = new KDFormattedTextField();
        kdtEntrysAccount_otherAmount_TextField.setName("kdtEntrysAccount_otherAmount_TextField");
        kdtEntrysAccount_otherAmount_TextField.setVisible(true);
        kdtEntrysAccount_otherAmount_TextField.setEditable(true);
        kdtEntrysAccount_otherAmount_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_otherAmount_TextField.setDataType(1);
        	kdtEntrysAccount_otherAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_otherAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_otherAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_otherAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_otherAmount_TextField);
        this.kdtEntrysAccount.getColumn("otherAmount").setEditor(kdtEntrysAccount_otherAmount_CellEditor);
        KDFormattedTextField kdtEntrysAccount_unSignContract_TextField = new KDFormattedTextField();
        kdtEntrysAccount_unSignContract_TextField.setName("kdtEntrysAccount_unSignContract_TextField");
        kdtEntrysAccount_unSignContract_TextField.setVisible(true);
        kdtEntrysAccount_unSignContract_TextField.setEditable(true);
        kdtEntrysAccount_unSignContract_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_unSignContract_TextField.setDataType(1);
        	kdtEntrysAccount_unSignContract_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_unSignContract_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_unSignContract_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_unSignContract_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_unSignContract_TextField);
        this.kdtEntrysAccount.getColumn("unSignContract").setEditor(kdtEntrysAccount_unSignContract_CellEditor);
        KDFormattedTextField kdtEntrysAccount_sumC_TextField = new KDFormattedTextField();
        kdtEntrysAccount_sumC_TextField.setName("kdtEntrysAccount_sumC_TextField");
        kdtEntrysAccount_sumC_TextField.setVisible(true);
        kdtEntrysAccount_sumC_TextField.setEditable(true);
        kdtEntrysAccount_sumC_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_sumC_TextField.setDataType(1);
        	kdtEntrysAccount_sumC_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_sumC_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_sumC_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_sumC_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_sumC_TextField);
        this.kdtEntrysAccount.getColumn("sumC").setEditor(kdtEntrysAccount_sumC_CellEditor);
        KDFormattedTextField kdtEntrysAccount_sumBC_TextField = new KDFormattedTextField();
        kdtEntrysAccount_sumBC_TextField.setName("kdtEntrysAccount_sumBC_TextField");
        kdtEntrysAccount_sumBC_TextField.setVisible(true);
        kdtEntrysAccount_sumBC_TextField.setEditable(true);
        kdtEntrysAccount_sumBC_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_sumBC_TextField.setDataType(1);
        	kdtEntrysAccount_sumBC_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_sumBC_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_sumBC_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_sumBC_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_sumBC_TextField);
        this.kdtEntrysAccount.getColumn("sumBC").setEditor(kdtEntrysAccount_sumBC_CellEditor);
        KDFormattedTextField kdtEntrysAccount_diffAmount_TextField = new KDFormattedTextField();
        kdtEntrysAccount_diffAmount_TextField.setName("kdtEntrysAccount_diffAmount_TextField");
        kdtEntrysAccount_diffAmount_TextField.setVisible(true);
        kdtEntrysAccount_diffAmount_TextField.setEditable(true);
        kdtEntrysAccount_diffAmount_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_diffAmount_TextField.setDataType(1);
        	kdtEntrysAccount_diffAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_diffAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_diffAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_diffAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_diffAmount_TextField);
        this.kdtEntrysAccount.getColumn("diffAmount").setEditor(kdtEntrysAccount_diffAmount_CellEditor);
        KDFormattedTextField kdtEntrysAccount_diffRate_TextField = new KDFormattedTextField();
        kdtEntrysAccount_diffRate_TextField.setName("kdtEntrysAccount_diffRate_TextField");
        kdtEntrysAccount_diffRate_TextField.setVisible(true);
        kdtEntrysAccount_diffRate_TextField.setEditable(true);
        kdtEntrysAccount_diffRate_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_diffRate_TextField.setDataType(1);
        	kdtEntrysAccount_diffRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrysAccount_diffRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrysAccount_diffRate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrysAccount_diffRate_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_diffRate_TextField);
        this.kdtEntrysAccount.getColumn("diffRate").setEditor(kdtEntrysAccount_diffRate_CellEditor);
        KDTextArea kdtEntrysAccount_Comment_TextArea = new KDTextArea();
        kdtEntrysAccount_Comment_TextArea.setName("kdtEntrysAccount_Comment_TextArea");
        kdtEntrysAccount_Comment_TextArea.setMaxLength(500);
        KDTDefaultCellEditor kdtEntrysAccount_Comment_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_Comment_TextArea);
        this.kdtEntrysAccount.getColumn("Comment").setEditor(kdtEntrysAccount_Comment_CellEditor);
        KDComboBox kdtEntrysAccount_accountIndex_ComboBox = new KDComboBox();
        kdtEntrysAccount_accountIndex_ComboBox.setName("kdtEntrysAccount_accountIndex_ComboBox");
        kdtEntrysAccount_accountIndex_ComboBox.setVisible(true);
        kdtEntrysAccount_accountIndex_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.AccountIndexType").toArray());
        KDTDefaultCellEditor kdtEntrysAccount_accountIndex_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_accountIndex_ComboBox);
        this.kdtEntrysAccount.getColumn("accountIndex").setEditor(kdtEntrysAccount_accountIndex_CellEditor);
        KDFormattedTextField kdtEntrysAccount_level_TextField = new KDFormattedTextField();
        kdtEntrysAccount_level_TextField.setName("kdtEntrysAccount_level_TextField");
        kdtEntrysAccount_level_TextField.setVisible(true);
        kdtEntrysAccount_level_TextField.setEditable(true);
        kdtEntrysAccount_level_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_level_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntrysAccount_level_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_level_TextField);
        this.kdtEntrysAccount.getColumn("level").setEditor(kdtEntrysAccount_level_CellEditor);
        KDFormattedTextField kdtEntrysAccount_alertIndex_TextField = new KDFormattedTextField();
        kdtEntrysAccount_alertIndex_TextField.setName("kdtEntrysAccount_alertIndex_TextField");
        kdtEntrysAccount_alertIndex_TextField.setVisible(true);
        kdtEntrysAccount_alertIndex_TextField.setEditable(true);
        kdtEntrysAccount_alertIndex_TextField.setHorizontalAlignment(2);
        kdtEntrysAccount_alertIndex_TextField.setDataType(1);
        	kdtEntrysAccount_alertIndex_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrysAccount_alertIndex_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrysAccount_alertIndex_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrysAccount_alertIndex_CellEditor = new KDTDefaultCellEditor(kdtEntrysAccount_alertIndex_TextField);
        this.kdtEntrysAccount.getColumn("alertIndex").setEditor(kdtEntrysAccount_alertIndex_CellEditor);
        // kDContainer3
        // kdtEentryTotal
		String kdtEentryTotalStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol19\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costAccountNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"firstVersionAimcost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"deletaAimcost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"Contract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"designChangeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"siteCertificatAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"settlementAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"withouttxt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"sumB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"onWayCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"EstimateChangeBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"otherAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"unSignContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"sumC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"sumBC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"diffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol18\" /><t:Column t:key=\"diffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol19\" /><t:Column t:key=\"Comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header0\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{costAccountNumber}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{firstVersionAimcost}</t:Cell><t:Cell>$Resource{deletaAimcost}</t:Cell><t:Cell>$Resource{Contract}</t:Cell><t:Cell>$Resource{designChangeAmount}</t:Cell><t:Cell>$Resource{siteCertificatAmount}</t:Cell><t:Cell>$Resource{settlementAmount}</t:Cell><t:Cell>$Resource{withouttxt}</t:Cell><t:Cell>$Resource{sumB}</t:Cell><t:Cell>$Resource{onWayCost}</t:Cell><t:Cell>$Resource{EstimateChangeBalance}</t:Cell><t:Cell>$Resource{otherAmount}</t:Cell><t:Cell>$Resource{unSignContract}</t:Cell><t:Cell>$Resource{sumC}</t:Cell><t:Cell>$Resource{sumBC}</t:Cell><t:Cell>$Resource{diffAmount}</t:Cell><t:Cell>$Resource{diffRate}</t:Cell><t:Cell>$Resource{Comment}</t:Cell></t:Row><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row2}</t:Cell><t:Cell>$Resource{costAccount_Row2}</t:Cell><t:Cell>$Resource{costAccountNumber_Row2}</t:Cell><t:Cell>$Resource{aimCost_Row2}</t:Cell><t:Cell>$Resource{firstVersionAimcost_Row2}</t:Cell><t:Cell>$Resource{deletaAimcost_Row2}</t:Cell><t:Cell>$Resource{Contract_Row2}</t:Cell><t:Cell>$Resource{designChangeAmount_Row2}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row2}</t:Cell><t:Cell>$Resource{settlementAmount_Row2}</t:Cell><t:Cell>$Resource{withouttxt_Row2}</t:Cell><t:Cell>$Resource{sumB_Row2}</t:Cell><t:Cell>$Resource{onWayCost_Row2}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row2}</t:Cell><t:Cell>$Resource{otherAmount_Row2}</t:Cell><t:Cell>$Resource{unSignContract_Row2}</t:Cell><t:Cell>$Resource{sumC_Row2}</t:Cell><t:Cell>$Resource{sumBC_Row2}</t:Cell><t:Cell>$Resource{diffAmount_Row2}</t:Cell><t:Cell>$Resource{diffRate_Row2}</t:Cell><t:Cell>$Resource{Comment_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row3}</t:Cell><t:Cell>$Resource{costAccount_Row3}</t:Cell><t:Cell>$Resource{costAccountNumber_Row3}</t:Cell><t:Cell>$Resource{aimCost_Row3}</t:Cell><t:Cell>$Resource{firstVersionAimcost_Row3}</t:Cell><t:Cell>$Resource{deletaAimcost_Row3}</t:Cell><t:Cell>$Resource{Contract_Row3}</t:Cell><t:Cell>$Resource{designChangeAmount_Row3}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row3}</t:Cell><t:Cell>$Resource{settlementAmount_Row3}</t:Cell><t:Cell>$Resource{withouttxt_Row3}</t:Cell><t:Cell>$Resource{sumB_Row3}</t:Cell><t:Cell>$Resource{onWayCost_Row3}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row3}</t:Cell><t:Cell>$Resource{otherAmount_Row3}</t:Cell><t:Cell>$Resource{unSignContract_Row3}</t:Cell><t:Cell>$Resource{sumC_Row3}</t:Cell><t:Cell>$Resource{sumBC_Row3}</t:Cell><t:Cell>$Resource{diffAmount_Row3}</t:Cell><t:Cell>$Resource{diffRate_Row3}</t:Cell><t:Cell>$Resource{Comment_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"0\" t:right=\"16\" /><t:Block t:top=\"1\" t:left=\"6\" t:bottom=\"1\" t:right=\"11\" /><t:Block t:top=\"1\" t:left=\"12\" t:bottom=\"1\" t:right=\"16\" /><t:Block t:top=\"0\" t:left=\"17\" t:bottom=\"2\" t:right=\"17\" /><t:Block t:top=\"0\" t:left=\"18\" t:bottom=\"2\" t:right=\"18\" /><t:Block t:top=\"0\" t:left=\"19\" t:bottom=\"2\" t:right=\"19\" /><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"0\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"20\" t:bottom=\"2\" t:right=\"20\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEentryTotal.setFormatXml(resHelper.translateString("kdtEentryTotal",kdtEentryTotalStrXML));

                this.kdtEentryTotal.putBindContents("editData",new String[] {"seq","costAccount","costAccountNumber","aimCost","firstVersionAimcost","deletaAimcost","Contract","designChangeAmount","siteCertificatAmount","settlementAmount","withouttxt","sumB","onWayCost","EstimateChangeBalance","otherAmount","unSignContract","sumC","sumBC","diffAmount","diffRate","Comment"});


        this.kdtEentryTotal.checkParsed();
        KDTextField kdtEentryTotal_costAccount_TextField = new KDTextField();
        kdtEentryTotal_costAccount_TextField.setName("kdtEentryTotal_costAccount_TextField");
        kdtEentryTotal_costAccount_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEentryTotal_costAccount_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_costAccount_TextField);
        this.kdtEentryTotal.getColumn("costAccount").setEditor(kdtEentryTotal_costAccount_CellEditor);
        KDTextField kdtEentryTotal_costAccountNumber_TextField = new KDTextField();
        kdtEentryTotal_costAccountNumber_TextField.setName("kdtEentryTotal_costAccountNumber_TextField");
        kdtEentryTotal_costAccountNumber_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEentryTotal_costAccountNumber_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_costAccountNumber_TextField);
        this.kdtEentryTotal.getColumn("costAccountNumber").setEditor(kdtEentryTotal_costAccountNumber_CellEditor);
        KDFormattedTextField kdtEentryTotal_aimCost_TextField = new KDFormattedTextField();
        kdtEentryTotal_aimCost_TextField.setName("kdtEentryTotal_aimCost_TextField");
        kdtEentryTotal_aimCost_TextField.setVisible(true);
        kdtEentryTotal_aimCost_TextField.setEditable(true);
        kdtEentryTotal_aimCost_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_aimCost_TextField.setDataType(1);
        	kdtEentryTotal_aimCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_aimCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_aimCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_aimCost_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_aimCost_TextField);
        this.kdtEentryTotal.getColumn("aimCost").setEditor(kdtEentryTotal_aimCost_CellEditor);
        KDFormattedTextField kdtEentryTotal_firstVersionAimcost_TextField = new KDFormattedTextField();
        kdtEentryTotal_firstVersionAimcost_TextField.setName("kdtEentryTotal_firstVersionAimcost_TextField");
        kdtEentryTotal_firstVersionAimcost_TextField.setVisible(true);
        kdtEentryTotal_firstVersionAimcost_TextField.setEditable(true);
        kdtEentryTotal_firstVersionAimcost_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_firstVersionAimcost_TextField.setDataType(1);
        	kdtEentryTotal_firstVersionAimcost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_firstVersionAimcost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_firstVersionAimcost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_firstVersionAimcost_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_firstVersionAimcost_TextField);
        this.kdtEentryTotal.getColumn("firstVersionAimcost").setEditor(kdtEentryTotal_firstVersionAimcost_CellEditor);
        KDFormattedTextField kdtEentryTotal_deletaAimcost_TextField = new KDFormattedTextField();
        kdtEentryTotal_deletaAimcost_TextField.setName("kdtEentryTotal_deletaAimcost_TextField");
        kdtEentryTotal_deletaAimcost_TextField.setVisible(true);
        kdtEentryTotal_deletaAimcost_TextField.setEditable(true);
        kdtEentryTotal_deletaAimcost_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_deletaAimcost_TextField.setDataType(1);
        	kdtEentryTotal_deletaAimcost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_deletaAimcost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_deletaAimcost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_deletaAimcost_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_deletaAimcost_TextField);
        this.kdtEentryTotal.getColumn("deletaAimcost").setEditor(kdtEentryTotal_deletaAimcost_CellEditor);
        KDFormattedTextField kdtEentryTotal_Contract_TextField = new KDFormattedTextField();
        kdtEentryTotal_Contract_TextField.setName("kdtEentryTotal_Contract_TextField");
        kdtEentryTotal_Contract_TextField.setVisible(true);
        kdtEentryTotal_Contract_TextField.setEditable(true);
        kdtEentryTotal_Contract_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_Contract_TextField.setDataType(1);
        	kdtEentryTotal_Contract_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_Contract_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_Contract_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_Contract_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_Contract_TextField);
        this.kdtEentryTotal.getColumn("Contract").setEditor(kdtEentryTotal_Contract_CellEditor);
        KDFormattedTextField kdtEentryTotal_designChangeAmount_TextField = new KDFormattedTextField();
        kdtEentryTotal_designChangeAmount_TextField.setName("kdtEentryTotal_designChangeAmount_TextField");
        kdtEentryTotal_designChangeAmount_TextField.setVisible(true);
        kdtEentryTotal_designChangeAmount_TextField.setEditable(true);
        kdtEentryTotal_designChangeAmount_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_designChangeAmount_TextField.setDataType(1);
        	kdtEentryTotal_designChangeAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_designChangeAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_designChangeAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_designChangeAmount_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_designChangeAmount_TextField);
        this.kdtEentryTotal.getColumn("designChangeAmount").setEditor(kdtEentryTotal_designChangeAmount_CellEditor);
        KDFormattedTextField kdtEentryTotal_siteCertificatAmount_TextField = new KDFormattedTextField();
        kdtEentryTotal_siteCertificatAmount_TextField.setName("kdtEentryTotal_siteCertificatAmount_TextField");
        kdtEentryTotal_siteCertificatAmount_TextField.setVisible(true);
        kdtEentryTotal_siteCertificatAmount_TextField.setEditable(true);
        kdtEentryTotal_siteCertificatAmount_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_siteCertificatAmount_TextField.setDataType(1);
        	kdtEentryTotal_siteCertificatAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_siteCertificatAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_siteCertificatAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_siteCertificatAmount_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_siteCertificatAmount_TextField);
        this.kdtEentryTotal.getColumn("siteCertificatAmount").setEditor(kdtEentryTotal_siteCertificatAmount_CellEditor);
        KDFormattedTextField kdtEentryTotal_settlementAmount_TextField = new KDFormattedTextField();
        kdtEentryTotal_settlementAmount_TextField.setName("kdtEentryTotal_settlementAmount_TextField");
        kdtEentryTotal_settlementAmount_TextField.setVisible(true);
        kdtEentryTotal_settlementAmount_TextField.setEditable(true);
        kdtEentryTotal_settlementAmount_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_settlementAmount_TextField.setDataType(1);
        	kdtEentryTotal_settlementAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_settlementAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_settlementAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_settlementAmount_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_settlementAmount_TextField);
        this.kdtEentryTotal.getColumn("settlementAmount").setEditor(kdtEentryTotal_settlementAmount_CellEditor);
        KDFormattedTextField kdtEentryTotal_withouttxt_TextField = new KDFormattedTextField();
        kdtEentryTotal_withouttxt_TextField.setName("kdtEentryTotal_withouttxt_TextField");
        kdtEentryTotal_withouttxt_TextField.setVisible(true);
        kdtEentryTotal_withouttxt_TextField.setEditable(true);
        kdtEentryTotal_withouttxt_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_withouttxt_TextField.setDataType(1);
        	kdtEentryTotal_withouttxt_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_withouttxt_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_withouttxt_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_withouttxt_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_withouttxt_TextField);
        this.kdtEentryTotal.getColumn("withouttxt").setEditor(kdtEentryTotal_withouttxt_CellEditor);
        KDFormattedTextField kdtEentryTotal_sumB_TextField = new KDFormattedTextField();
        kdtEentryTotal_sumB_TextField.setName("kdtEentryTotal_sumB_TextField");
        kdtEentryTotal_sumB_TextField.setVisible(true);
        kdtEentryTotal_sumB_TextField.setEditable(true);
        kdtEentryTotal_sumB_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_sumB_TextField.setDataType(1);
        	kdtEentryTotal_sumB_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_sumB_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_sumB_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_sumB_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_sumB_TextField);
        this.kdtEentryTotal.getColumn("sumB").setEditor(kdtEentryTotal_sumB_CellEditor);
        KDFormattedTextField kdtEentryTotal_onWayCost_TextField = new KDFormattedTextField();
        kdtEentryTotal_onWayCost_TextField.setName("kdtEentryTotal_onWayCost_TextField");
        kdtEentryTotal_onWayCost_TextField.setVisible(true);
        kdtEentryTotal_onWayCost_TextField.setEditable(true);
        kdtEentryTotal_onWayCost_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_onWayCost_TextField.setDataType(1);
        	kdtEentryTotal_onWayCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_onWayCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_onWayCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_onWayCost_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_onWayCost_TextField);
        this.kdtEentryTotal.getColumn("onWayCost").setEditor(kdtEentryTotal_onWayCost_CellEditor);
        KDFormattedTextField kdtEentryTotal_EstimateChangeBalance_TextField = new KDFormattedTextField();
        kdtEentryTotal_EstimateChangeBalance_TextField.setName("kdtEentryTotal_EstimateChangeBalance_TextField");
        kdtEentryTotal_EstimateChangeBalance_TextField.setVisible(true);
        kdtEentryTotal_EstimateChangeBalance_TextField.setEditable(true);
        kdtEentryTotal_EstimateChangeBalance_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_EstimateChangeBalance_TextField.setDataType(1);
        	kdtEentryTotal_EstimateChangeBalance_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_EstimateChangeBalance_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_EstimateChangeBalance_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_EstimateChangeBalance_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_EstimateChangeBalance_TextField);
        this.kdtEentryTotal.getColumn("EstimateChangeBalance").setEditor(kdtEentryTotal_EstimateChangeBalance_CellEditor);
        KDFormattedTextField kdtEentryTotal_otherAmount_TextField = new KDFormattedTextField();
        kdtEentryTotal_otherAmount_TextField.setName("kdtEentryTotal_otherAmount_TextField");
        kdtEentryTotal_otherAmount_TextField.setVisible(true);
        kdtEentryTotal_otherAmount_TextField.setEditable(true);
        kdtEentryTotal_otherAmount_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_otherAmount_TextField.setDataType(1);
        	kdtEentryTotal_otherAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_otherAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_otherAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_otherAmount_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_otherAmount_TextField);
        this.kdtEentryTotal.getColumn("otherAmount").setEditor(kdtEentryTotal_otherAmount_CellEditor);
        KDFormattedTextField kdtEentryTotal_unSignContract_TextField = new KDFormattedTextField();
        kdtEentryTotal_unSignContract_TextField.setName("kdtEentryTotal_unSignContract_TextField");
        kdtEentryTotal_unSignContract_TextField.setVisible(true);
        kdtEentryTotal_unSignContract_TextField.setEditable(true);
        kdtEentryTotal_unSignContract_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_unSignContract_TextField.setDataType(1);
        	kdtEentryTotal_unSignContract_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_unSignContract_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_unSignContract_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_unSignContract_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_unSignContract_TextField);
        this.kdtEentryTotal.getColumn("unSignContract").setEditor(kdtEentryTotal_unSignContract_CellEditor);
        KDFormattedTextField kdtEentryTotal_sumC_TextField = new KDFormattedTextField();
        kdtEentryTotal_sumC_TextField.setName("kdtEentryTotal_sumC_TextField");
        kdtEentryTotal_sumC_TextField.setVisible(true);
        kdtEentryTotal_sumC_TextField.setEditable(true);
        kdtEentryTotal_sumC_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_sumC_TextField.setDataType(1);
        	kdtEentryTotal_sumC_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_sumC_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_sumC_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_sumC_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_sumC_TextField);
        this.kdtEentryTotal.getColumn("sumC").setEditor(kdtEentryTotal_sumC_CellEditor);
        KDFormattedTextField kdtEentryTotal_sumBC_TextField = new KDFormattedTextField();
        kdtEentryTotal_sumBC_TextField.setName("kdtEentryTotal_sumBC_TextField");
        kdtEentryTotal_sumBC_TextField.setVisible(true);
        kdtEentryTotal_sumBC_TextField.setEditable(true);
        kdtEentryTotal_sumBC_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_sumBC_TextField.setDataType(1);
        	kdtEentryTotal_sumBC_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_sumBC_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_sumBC_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_sumBC_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_sumBC_TextField);
        this.kdtEentryTotal.getColumn("sumBC").setEditor(kdtEentryTotal_sumBC_CellEditor);
        KDFormattedTextField kdtEentryTotal_diffAmount_TextField = new KDFormattedTextField();
        kdtEentryTotal_diffAmount_TextField.setName("kdtEentryTotal_diffAmount_TextField");
        kdtEentryTotal_diffAmount_TextField.setVisible(true);
        kdtEentryTotal_diffAmount_TextField.setEditable(true);
        kdtEentryTotal_diffAmount_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_diffAmount_TextField.setDataType(1);
        	kdtEentryTotal_diffAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_diffAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_diffAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_diffAmount_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_diffAmount_TextField);
        this.kdtEentryTotal.getColumn("diffAmount").setEditor(kdtEentryTotal_diffAmount_CellEditor);
        KDFormattedTextField kdtEentryTotal_diffRate_TextField = new KDFormattedTextField();
        kdtEentryTotal_diffRate_TextField.setName("kdtEentryTotal_diffRate_TextField");
        kdtEentryTotal_diffRate_TextField.setVisible(true);
        kdtEentryTotal_diffRate_TextField.setEditable(true);
        kdtEentryTotal_diffRate_TextField.setHorizontalAlignment(2);
        kdtEentryTotal_diffRate_TextField.setDataType(1);
        	kdtEentryTotal_diffRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEentryTotal_diffRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEentryTotal_diffRate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEentryTotal_diffRate_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_diffRate_TextField);
        this.kdtEentryTotal.getColumn("diffRate").setEditor(kdtEentryTotal_diffRate_CellEditor);
        KDTextArea kdtEentryTotal_Comment_TextArea = new KDTextArea();
        kdtEentryTotal_Comment_TextArea.setName("kdtEentryTotal_Comment_TextArea");
        kdtEentryTotal_Comment_TextArea.setMaxLength(500);
        KDTDefaultCellEditor kdtEentryTotal_Comment_CellEditor = new KDTDefaultCellEditor(kdtEentryTotal_Comment_TextArea);
        this.kdtEentryTotal.getColumn("Comment").setEditor(kdtEentryTotal_Comment_CellEditor);
        // kDContainer4
        // kdtEntrySixMonth
		String kdtEntrySixMonthStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol19\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol20\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol21\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol22\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol23\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol24\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol25\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol26\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costAccountNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"firstAimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"firstDynaCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"firstDiffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"firstDiffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"secondAimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"secondDynaCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"secondDiffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"secondDiffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"thirdAimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"thirdDynaCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"thirdDiffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"thirdDiffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"fourthAimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"fourthDynaCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"fourthDiffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"fourthDiffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol18\" /><t:Column t:key=\"fifthAimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol19\" /><t:Column t:key=\"fifthDynaCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol20\" /><t:Column t:key=\"fifthDiffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol21\" /><t:Column t:key=\"fifthDiffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol22\" /><t:Column t:key=\"sixthAimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol23\" /><t:Column t:key=\"sixthDynaCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol24\" /><t:Column t:key=\"sixthDiffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol25\" /><t:Column t:key=\"sixthDiffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol26\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header0\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{costAccountNumber}</t:Cell><t:Cell>$Resource{firstAimCost}</t:Cell><t:Cell>$Resource{firstDynaCost}</t:Cell><t:Cell>$Resource{firstDiffAmount}</t:Cell><t:Cell>$Resource{firstDiffRate}</t:Cell><t:Cell>$Resource{secondAimCost}</t:Cell><t:Cell>$Resource{secondDynaCost}</t:Cell><t:Cell>$Resource{secondDiffAmount}</t:Cell><t:Cell>$Resource{secondDiffRate}</t:Cell><t:Cell>$Resource{thirdAimCost}</t:Cell><t:Cell>$Resource{thirdDynaCost}</t:Cell><t:Cell>$Resource{thirdDiffAmount}</t:Cell><t:Cell>$Resource{thirdDiffRate}</t:Cell><t:Cell>$Resource{fourthAimCost}</t:Cell><t:Cell>$Resource{fourthDynaCost}</t:Cell><t:Cell>$Resource{fourthDiffAmount}</t:Cell><t:Cell>$Resource{fourthDiffRate}</t:Cell><t:Cell>$Resource{fifthAimCost}</t:Cell><t:Cell>$Resource{fifthDynaCost}</t:Cell><t:Cell>$Resource{fifthDiffAmount}</t:Cell><t:Cell>$Resource{fifthDiffRate}</t:Cell><t:Cell>$Resource{sixthAimCost}</t:Cell><t:Cell>$Resource{sixthDynaCost}</t:Cell><t:Cell>$Resource{sixthDiffAmount}</t:Cell><t:Cell>$Resource{sixthDiffRate}</t:Cell></t:Row><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row2}</t:Cell><t:Cell>$Resource{costAccount_Row2}</t:Cell><t:Cell>$Resource{costAccountNumber_Row2}</t:Cell><t:Cell>$Resource{firstAimCost_Row2}</t:Cell><t:Cell>$Resource{firstDynaCost_Row2}</t:Cell><t:Cell>$Resource{firstDiffAmount_Row2}</t:Cell><t:Cell>$Resource{firstDiffRate_Row2}</t:Cell><t:Cell>$Resource{secondAimCost_Row2}</t:Cell><t:Cell>$Resource{secondDynaCost_Row2}</t:Cell><t:Cell>$Resource{secondDiffAmount_Row2}</t:Cell><t:Cell>$Resource{secondDiffRate_Row2}</t:Cell><t:Cell>$Resource{thirdAimCost_Row2}</t:Cell><t:Cell>$Resource{thirdDynaCost_Row2}</t:Cell><t:Cell>$Resource{thirdDiffAmount_Row2}</t:Cell><t:Cell>$Resource{thirdDiffRate_Row2}</t:Cell><t:Cell>$Resource{fourthAimCost_Row2}</t:Cell><t:Cell>$Resource{fourthDynaCost_Row2}</t:Cell><t:Cell>$Resource{fourthDiffAmount_Row2}</t:Cell><t:Cell>$Resource{fourthDiffRate_Row2}</t:Cell><t:Cell>$Resource{fifthAimCost_Row2}</t:Cell><t:Cell>$Resource{fifthDynaCost_Row2}</t:Cell><t:Cell>$Resource{fifthDiffAmount_Row2}</t:Cell><t:Cell>$Resource{fifthDiffRate_Row2}</t:Cell><t:Cell>$Resource{sixthAimCost_Row2}</t:Cell><t:Cell>$Resource{sixthDynaCost_Row2}</t:Cell><t:Cell>$Resource{sixthDiffAmount_Row2}</t:Cell><t:Cell>$Resource{sixthDiffRate_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"0\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"0\" t:right=\"10\" /><t:Block t:top=\"0\" t:left=\"11\" t:bottom=\"0\" t:right=\"14\" /><t:Block t:top=\"0\" t:left=\"15\" t:bottom=\"0\" t:right=\"18\" /><t:Block t:top=\"0\" t:left=\"19\" t:bottom=\"0\" t:right=\"22\" /><t:Block t:top=\"0\" t:left=\"23\" t:bottom=\"0\" t:right=\"26\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrySixMonth.setFormatXml(resHelper.translateString("kdtEntrySixMonth",kdtEntrySixMonthStrXML));

                this.kdtEntrySixMonth.putBindContents("editData",new String[] {"seq","costAccount","costAccountNumber","firstAimCost","firstDynaCost","firstDiffAmount","firstDiffRate","secondAimCost","secondDynaCost","secondDiffAmount","secondDiffRate","thirdAimCost","thirdDynaCost","thirdDiffAmount","thirdDiffRate","fourthAimCost","fourthDynaCost","fourthDiffAmount","fourthDiffRate","fifthAimCost","fifthDynaCost","fifthDiffAmount","fifthDiffRate","sixthAimCost","sixthDynaCost","sixthDiffAmount","sixthDiffRate"});


        this.kdtEntrySixMonth.checkParsed();
        KDTextField kdtEntrySixMonth_costAccount_TextField = new KDTextField();
        kdtEntrySixMonth_costAccount_TextField.setName("kdtEntrySixMonth_costAccount_TextField");
        kdtEntrySixMonth_costAccount_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrySixMonth_costAccount_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_costAccount_TextField);
        this.kdtEntrySixMonth.getColumn("costAccount").setEditor(kdtEntrySixMonth_costAccount_CellEditor);
        KDTextField kdtEntrySixMonth_costAccountNumber_TextField = new KDTextField();
        kdtEntrySixMonth_costAccountNumber_TextField.setName("kdtEntrySixMonth_costAccountNumber_TextField");
        kdtEntrySixMonth_costAccountNumber_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrySixMonth_costAccountNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_costAccountNumber_TextField);
        this.kdtEntrySixMonth.getColumn("costAccountNumber").setEditor(kdtEntrySixMonth_costAccountNumber_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_firstAimCost_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_firstAimCost_TextField.setName("kdtEntrySixMonth_firstAimCost_TextField");
        kdtEntrySixMonth_firstAimCost_TextField.setVisible(true);
        kdtEntrySixMonth_firstAimCost_TextField.setEditable(true);
        kdtEntrySixMonth_firstAimCost_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_firstAimCost_TextField.setDataType(1);
        	kdtEntrySixMonth_firstAimCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_firstAimCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_firstAimCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_firstAimCost_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_firstAimCost_TextField);
        this.kdtEntrySixMonth.getColumn("firstAimCost").setEditor(kdtEntrySixMonth_firstAimCost_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_firstDynaCost_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_firstDynaCost_TextField.setName("kdtEntrySixMonth_firstDynaCost_TextField");
        kdtEntrySixMonth_firstDynaCost_TextField.setVisible(true);
        kdtEntrySixMonth_firstDynaCost_TextField.setEditable(true);
        kdtEntrySixMonth_firstDynaCost_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_firstDynaCost_TextField.setDataType(1);
        	kdtEntrySixMonth_firstDynaCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_firstDynaCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_firstDynaCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_firstDynaCost_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_firstDynaCost_TextField);
        this.kdtEntrySixMonth.getColumn("firstDynaCost").setEditor(kdtEntrySixMonth_firstDynaCost_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_firstDiffAmount_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_firstDiffAmount_TextField.setName("kdtEntrySixMonth_firstDiffAmount_TextField");
        kdtEntrySixMonth_firstDiffAmount_TextField.setVisible(true);
        kdtEntrySixMonth_firstDiffAmount_TextField.setEditable(true);
        kdtEntrySixMonth_firstDiffAmount_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_firstDiffAmount_TextField.setDataType(1);
        	kdtEntrySixMonth_firstDiffAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_firstDiffAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_firstDiffAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_firstDiffAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_firstDiffAmount_TextField);
        this.kdtEntrySixMonth.getColumn("firstDiffAmount").setEditor(kdtEntrySixMonth_firstDiffAmount_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_firstDiffRate_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_firstDiffRate_TextField.setName("kdtEntrySixMonth_firstDiffRate_TextField");
        kdtEntrySixMonth_firstDiffRate_TextField.setVisible(true);
        kdtEntrySixMonth_firstDiffRate_TextField.setEditable(true);
        kdtEntrySixMonth_firstDiffRate_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_firstDiffRate_TextField.setDataType(1);
        	kdtEntrySixMonth_firstDiffRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_firstDiffRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_firstDiffRate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_firstDiffRate_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_firstDiffRate_TextField);
        this.kdtEntrySixMonth.getColumn("firstDiffRate").setEditor(kdtEntrySixMonth_firstDiffRate_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_secondAimCost_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_secondAimCost_TextField.setName("kdtEntrySixMonth_secondAimCost_TextField");
        kdtEntrySixMonth_secondAimCost_TextField.setVisible(true);
        kdtEntrySixMonth_secondAimCost_TextField.setEditable(true);
        kdtEntrySixMonth_secondAimCost_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_secondAimCost_TextField.setDataType(1);
        	kdtEntrySixMonth_secondAimCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_secondAimCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_secondAimCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_secondAimCost_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_secondAimCost_TextField);
        this.kdtEntrySixMonth.getColumn("secondAimCost").setEditor(kdtEntrySixMonth_secondAimCost_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_secondDynaCost_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_secondDynaCost_TextField.setName("kdtEntrySixMonth_secondDynaCost_TextField");
        kdtEntrySixMonth_secondDynaCost_TextField.setVisible(true);
        kdtEntrySixMonth_secondDynaCost_TextField.setEditable(true);
        kdtEntrySixMonth_secondDynaCost_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_secondDynaCost_TextField.setDataType(1);
        	kdtEntrySixMonth_secondDynaCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_secondDynaCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_secondDynaCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_secondDynaCost_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_secondDynaCost_TextField);
        this.kdtEntrySixMonth.getColumn("secondDynaCost").setEditor(kdtEntrySixMonth_secondDynaCost_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_secondDiffAmount_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_secondDiffAmount_TextField.setName("kdtEntrySixMonth_secondDiffAmount_TextField");
        kdtEntrySixMonth_secondDiffAmount_TextField.setVisible(true);
        kdtEntrySixMonth_secondDiffAmount_TextField.setEditable(true);
        kdtEntrySixMonth_secondDiffAmount_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_secondDiffAmount_TextField.setDataType(1);
        	kdtEntrySixMonth_secondDiffAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_secondDiffAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_secondDiffAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_secondDiffAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_secondDiffAmount_TextField);
        this.kdtEntrySixMonth.getColumn("secondDiffAmount").setEditor(kdtEntrySixMonth_secondDiffAmount_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_secondDiffRate_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_secondDiffRate_TextField.setName("kdtEntrySixMonth_secondDiffRate_TextField");
        kdtEntrySixMonth_secondDiffRate_TextField.setVisible(true);
        kdtEntrySixMonth_secondDiffRate_TextField.setEditable(true);
        kdtEntrySixMonth_secondDiffRate_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_secondDiffRate_TextField.setDataType(1);
        	kdtEntrySixMonth_secondDiffRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_secondDiffRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_secondDiffRate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_secondDiffRate_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_secondDiffRate_TextField);
        this.kdtEntrySixMonth.getColumn("secondDiffRate").setEditor(kdtEntrySixMonth_secondDiffRate_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_thirdAimCost_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_thirdAimCost_TextField.setName("kdtEntrySixMonth_thirdAimCost_TextField");
        kdtEntrySixMonth_thirdAimCost_TextField.setVisible(true);
        kdtEntrySixMonth_thirdAimCost_TextField.setEditable(true);
        kdtEntrySixMonth_thirdAimCost_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_thirdAimCost_TextField.setDataType(1);
        	kdtEntrySixMonth_thirdAimCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_thirdAimCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_thirdAimCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_thirdAimCost_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_thirdAimCost_TextField);
        this.kdtEntrySixMonth.getColumn("thirdAimCost").setEditor(kdtEntrySixMonth_thirdAimCost_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_thirdDynaCost_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_thirdDynaCost_TextField.setName("kdtEntrySixMonth_thirdDynaCost_TextField");
        kdtEntrySixMonth_thirdDynaCost_TextField.setVisible(true);
        kdtEntrySixMonth_thirdDynaCost_TextField.setEditable(true);
        kdtEntrySixMonth_thirdDynaCost_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_thirdDynaCost_TextField.setDataType(1);
        	kdtEntrySixMonth_thirdDynaCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_thirdDynaCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_thirdDynaCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_thirdDynaCost_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_thirdDynaCost_TextField);
        this.kdtEntrySixMonth.getColumn("thirdDynaCost").setEditor(kdtEntrySixMonth_thirdDynaCost_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_thirdDiffAmount_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_thirdDiffAmount_TextField.setName("kdtEntrySixMonth_thirdDiffAmount_TextField");
        kdtEntrySixMonth_thirdDiffAmount_TextField.setVisible(true);
        kdtEntrySixMonth_thirdDiffAmount_TextField.setEditable(true);
        kdtEntrySixMonth_thirdDiffAmount_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_thirdDiffAmount_TextField.setDataType(1);
        	kdtEntrySixMonth_thirdDiffAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_thirdDiffAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_thirdDiffAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_thirdDiffAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_thirdDiffAmount_TextField);
        this.kdtEntrySixMonth.getColumn("thirdDiffAmount").setEditor(kdtEntrySixMonth_thirdDiffAmount_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_thirdDiffRate_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_thirdDiffRate_TextField.setName("kdtEntrySixMonth_thirdDiffRate_TextField");
        kdtEntrySixMonth_thirdDiffRate_TextField.setVisible(true);
        kdtEntrySixMonth_thirdDiffRate_TextField.setEditable(true);
        kdtEntrySixMonth_thirdDiffRate_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_thirdDiffRate_TextField.setDataType(1);
        	kdtEntrySixMonth_thirdDiffRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_thirdDiffRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_thirdDiffRate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_thirdDiffRate_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_thirdDiffRate_TextField);
        this.kdtEntrySixMonth.getColumn("thirdDiffRate").setEditor(kdtEntrySixMonth_thirdDiffRate_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_fourthAimCost_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_fourthAimCost_TextField.setName("kdtEntrySixMonth_fourthAimCost_TextField");
        kdtEntrySixMonth_fourthAimCost_TextField.setVisible(true);
        kdtEntrySixMonth_fourthAimCost_TextField.setEditable(true);
        kdtEntrySixMonth_fourthAimCost_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_fourthAimCost_TextField.setDataType(1);
        	kdtEntrySixMonth_fourthAimCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_fourthAimCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_fourthAimCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_fourthAimCost_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_fourthAimCost_TextField);
        this.kdtEntrySixMonth.getColumn("fourthAimCost").setEditor(kdtEntrySixMonth_fourthAimCost_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_fourthDynaCost_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_fourthDynaCost_TextField.setName("kdtEntrySixMonth_fourthDynaCost_TextField");
        kdtEntrySixMonth_fourthDynaCost_TextField.setVisible(true);
        kdtEntrySixMonth_fourthDynaCost_TextField.setEditable(true);
        kdtEntrySixMonth_fourthDynaCost_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_fourthDynaCost_TextField.setDataType(1);
        	kdtEntrySixMonth_fourthDynaCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_fourthDynaCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_fourthDynaCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_fourthDynaCost_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_fourthDynaCost_TextField);
        this.kdtEntrySixMonth.getColumn("fourthDynaCost").setEditor(kdtEntrySixMonth_fourthDynaCost_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_fourthDiffAmount_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_fourthDiffAmount_TextField.setName("kdtEntrySixMonth_fourthDiffAmount_TextField");
        kdtEntrySixMonth_fourthDiffAmount_TextField.setVisible(true);
        kdtEntrySixMonth_fourthDiffAmount_TextField.setEditable(true);
        kdtEntrySixMonth_fourthDiffAmount_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_fourthDiffAmount_TextField.setDataType(1);
        	kdtEntrySixMonth_fourthDiffAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_fourthDiffAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_fourthDiffAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_fourthDiffAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_fourthDiffAmount_TextField);
        this.kdtEntrySixMonth.getColumn("fourthDiffAmount").setEditor(kdtEntrySixMonth_fourthDiffAmount_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_fourthDiffRate_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_fourthDiffRate_TextField.setName("kdtEntrySixMonth_fourthDiffRate_TextField");
        kdtEntrySixMonth_fourthDiffRate_TextField.setVisible(true);
        kdtEntrySixMonth_fourthDiffRate_TextField.setEditable(true);
        kdtEntrySixMonth_fourthDiffRate_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_fourthDiffRate_TextField.setDataType(1);
        	kdtEntrySixMonth_fourthDiffRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_fourthDiffRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_fourthDiffRate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_fourthDiffRate_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_fourthDiffRate_TextField);
        this.kdtEntrySixMonth.getColumn("fourthDiffRate").setEditor(kdtEntrySixMonth_fourthDiffRate_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_fifthAimCost_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_fifthAimCost_TextField.setName("kdtEntrySixMonth_fifthAimCost_TextField");
        kdtEntrySixMonth_fifthAimCost_TextField.setVisible(true);
        kdtEntrySixMonth_fifthAimCost_TextField.setEditable(true);
        kdtEntrySixMonth_fifthAimCost_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_fifthAimCost_TextField.setDataType(1);
        	kdtEntrySixMonth_fifthAimCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_fifthAimCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_fifthAimCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_fifthAimCost_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_fifthAimCost_TextField);
        this.kdtEntrySixMonth.getColumn("fifthAimCost").setEditor(kdtEntrySixMonth_fifthAimCost_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_fifthDynaCost_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_fifthDynaCost_TextField.setName("kdtEntrySixMonth_fifthDynaCost_TextField");
        kdtEntrySixMonth_fifthDynaCost_TextField.setVisible(true);
        kdtEntrySixMonth_fifthDynaCost_TextField.setEditable(true);
        kdtEntrySixMonth_fifthDynaCost_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_fifthDynaCost_TextField.setDataType(1);
        	kdtEntrySixMonth_fifthDynaCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_fifthDynaCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_fifthDynaCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_fifthDynaCost_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_fifthDynaCost_TextField);
        this.kdtEntrySixMonth.getColumn("fifthDynaCost").setEditor(kdtEntrySixMonth_fifthDynaCost_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_fifthDiffAmount_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_fifthDiffAmount_TextField.setName("kdtEntrySixMonth_fifthDiffAmount_TextField");
        kdtEntrySixMonth_fifthDiffAmount_TextField.setVisible(true);
        kdtEntrySixMonth_fifthDiffAmount_TextField.setEditable(true);
        kdtEntrySixMonth_fifthDiffAmount_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_fifthDiffAmount_TextField.setDataType(1);
        	kdtEntrySixMonth_fifthDiffAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_fifthDiffAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_fifthDiffAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_fifthDiffAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_fifthDiffAmount_TextField);
        this.kdtEntrySixMonth.getColumn("fifthDiffAmount").setEditor(kdtEntrySixMonth_fifthDiffAmount_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_fifthDiffRate_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_fifthDiffRate_TextField.setName("kdtEntrySixMonth_fifthDiffRate_TextField");
        kdtEntrySixMonth_fifthDiffRate_TextField.setVisible(true);
        kdtEntrySixMonth_fifthDiffRate_TextField.setEditable(true);
        kdtEntrySixMonth_fifthDiffRate_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_fifthDiffRate_TextField.setDataType(1);
        	kdtEntrySixMonth_fifthDiffRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_fifthDiffRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_fifthDiffRate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_fifthDiffRate_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_fifthDiffRate_TextField);
        this.kdtEntrySixMonth.getColumn("fifthDiffRate").setEditor(kdtEntrySixMonth_fifthDiffRate_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_sixthAimCost_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_sixthAimCost_TextField.setName("kdtEntrySixMonth_sixthAimCost_TextField");
        kdtEntrySixMonth_sixthAimCost_TextField.setVisible(true);
        kdtEntrySixMonth_sixthAimCost_TextField.setEditable(true);
        kdtEntrySixMonth_sixthAimCost_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_sixthAimCost_TextField.setDataType(1);
        	kdtEntrySixMonth_sixthAimCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_sixthAimCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_sixthAimCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_sixthAimCost_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_sixthAimCost_TextField);
        this.kdtEntrySixMonth.getColumn("sixthAimCost").setEditor(kdtEntrySixMonth_sixthAimCost_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_sixthDynaCost_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_sixthDynaCost_TextField.setName("kdtEntrySixMonth_sixthDynaCost_TextField");
        kdtEntrySixMonth_sixthDynaCost_TextField.setVisible(true);
        kdtEntrySixMonth_sixthDynaCost_TextField.setEditable(true);
        kdtEntrySixMonth_sixthDynaCost_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_sixthDynaCost_TextField.setDataType(1);
        	kdtEntrySixMonth_sixthDynaCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_sixthDynaCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_sixthDynaCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_sixthDynaCost_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_sixthDynaCost_TextField);
        this.kdtEntrySixMonth.getColumn("sixthDynaCost").setEditor(kdtEntrySixMonth_sixthDynaCost_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_sixthDiffAmount_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_sixthDiffAmount_TextField.setName("kdtEntrySixMonth_sixthDiffAmount_TextField");
        kdtEntrySixMonth_sixthDiffAmount_TextField.setVisible(true);
        kdtEntrySixMonth_sixthDiffAmount_TextField.setEditable(true);
        kdtEntrySixMonth_sixthDiffAmount_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_sixthDiffAmount_TextField.setDataType(1);
        	kdtEntrySixMonth_sixthDiffAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_sixthDiffAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_sixthDiffAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_sixthDiffAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_sixthDiffAmount_TextField);
        this.kdtEntrySixMonth.getColumn("sixthDiffAmount").setEditor(kdtEntrySixMonth_sixthDiffAmount_CellEditor);
        KDFormattedTextField kdtEntrySixMonth_sixthDiffRate_TextField = new KDFormattedTextField();
        kdtEntrySixMonth_sixthDiffRate_TextField.setName("kdtEntrySixMonth_sixthDiffRate_TextField");
        kdtEntrySixMonth_sixthDiffRate_TextField.setVisible(true);
        kdtEntrySixMonth_sixthDiffRate_TextField.setEditable(true);
        kdtEntrySixMonth_sixthDiffRate_TextField.setHorizontalAlignment(2);
        kdtEntrySixMonth_sixthDiffRate_TextField.setDataType(1);
        	kdtEntrySixMonth_sixthDiffRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrySixMonth_sixthDiffRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrySixMonth_sixthDiffRate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrySixMonth_sixthDiffRate_CellEditor = new KDTDefaultCellEditor(kdtEntrySixMonth_sixthDiffRate_TextField);
        this.kdtEntrySixMonth.getColumn("sixthDiffRate").setEditor(kdtEntrySixMonth_sixthDiffRate_CellEditor);
        // prmtfirstLevelPos		
        this.prmtfirstLevelPos.setQueryInfo("com.kingdee.eas.basedata.org.app.PositionQuery");		
        this.prmtfirstLevelPos.setEditable(true);		
        this.prmtfirstLevelPos.setDisplayFormat("$name$");		
        this.prmtfirstLevelPos.setEditFormat("$number$");		
        this.prmtfirstLevelPos.setCommitFormat("$number$");		
        this.prmtfirstLevelPos.setRequired(false);		
        this.prmtfirstLevelPos.setEnabledMultiSelection(true);
        this.prmtfirstLevelPos.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtfirstLevelPos_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtsecondLevelPos		
        this.prmtsecondLevelPos.setQueryInfo("com.kingdee.eas.basedata.org.app.PositionQuery");		
        this.prmtsecondLevelPos.setEditable(true);		
        this.prmtsecondLevelPos.setDisplayFormat("$name$");		
        this.prmtsecondLevelPos.setEditFormat("$number$");		
        this.prmtsecondLevelPos.setCommitFormat("$number$");		
        this.prmtsecondLevelPos.setRequired(false);		
        this.prmtsecondLevelPos.setEnabledMultiSelection(true);
        this.prmtsecondLevelPos.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtsecondLevelPos_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtthirdLevelPos		
        this.prmtthirdLevelPos.setQueryInfo("com.kingdee.eas.basedata.org.app.PositionQuery");		
        this.prmtthirdLevelPos.setEditable(true);		
        this.prmtthirdLevelPos.setDisplayFormat("$name$");		
        this.prmtthirdLevelPos.setEditFormat("$number$");		
        this.prmtthirdLevelPos.setCommitFormat("$number$");		
        this.prmtthirdLevelPos.setRequired(false);		
        this.prmtthirdLevelPos.setEnabledMultiSelection(true);
        this.prmtthirdLevelPos.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtthirdLevelPos_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));
        // btnUnAduit
        this.btnUnAduit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAduit.setText(resHelper.getString("btnUnAduit.text"));
        // btnRevise
        this.btnRevise.setAction((IItemAction)ActionProxyFactory.getProxy(actionRevise, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRevise.setText(resHelper.getString("btnRevise.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {spYear,spMonth,txtNumber,pkBizDate,txtDescription,prmtAuditor,prmtCreator,kDDateCreateTime,prmtLastUpdateUser,kDDateLastUpdateTime,prmtcurProject,txtversion,state,pkauditTime,prmtfirstLevelPos,prmtsecondLevelPos,prmtthirdLevelPos,chkisLatest,kdtEntrySixMonth,kdtEentryTotal,kdtEntryPosition,kdtEntrysAccount,kdtEntrysContract}));
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
        this.setBounds(new Rectangle(0, 0, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 629));
        contCreator.setBounds(new Rectangle(440, 547, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(440, 547, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(440, 574, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(440, 574, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(734, 601, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(734, 601, 270, 19, 0));
        contLastUpdateTime.setBounds(new Rectangle(730, 574, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(730, 574, 270, 19, 0));
        contNumber.setBounds(new Rectangle(16, 7, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(16, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(722, 7, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(722, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(14, 601, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(14, 601, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(16, 547, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(16, 547, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcurProject.setBounds(new Rectangle(369, 7, 270, 19));
        this.add(contcurProject, new KDLayout.Constraints(369, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnLoadData.setBounds(new Rectangle(722, 32, 113, 21));
        this.add(btnLoadData, new KDLayout.Constraints(722, 32, 113, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contversion.setBounds(new Rectangle(369, 32, 270, 19));
        this.add(contversion, new KDLayout.Constraints(369, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(16, 33, 163, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(16, 33, 163, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        spMonth.setBounds(new Rectangle(204, 33, 59, 19));
        this.add(spMonth, new KDLayout.Constraints(204, 33, 59, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel1.setBounds(new Rectangle(184, 33, 15, 19));
        this.add(kDLabel1, new KDLayout.Constraints(184, 33, 15, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel2.setBounds(new Rectangle(270, 33, 16, 19));
        this.add(kDLabel2, new KDLayout.Constraints(270, 33, 16, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contstate.setBounds(new Rectangle(439, 601, 270, 19));
        this.add(contstate, new KDLayout.Constraints(439, 601, 270, 19, 0));
        contauditTime.setBounds(new Rectangle(16, 574, 270, 19));
        this.add(contauditTime, new KDLayout.Constraints(16, 574, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane1.setBounds(new Rectangle(17, 83, 975, 460));
        this.add(kDTabbedPane1, new KDLayout.Constraints(17, 83, 975, 460, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contfirstLevelPos.setBounds(new Rectangle(16, 58, 270, 19));
        this.add(contfirstLevelPos, new KDLayout.Constraints(16, 58, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsecondLevelPos.setBounds(new Rectangle(369, 58, 270, 19));
        this.add(contsecondLevelPos, new KDLayout.Constraints(369, 58, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contthirdLevelPos.setBounds(new Rectangle(722, 58, 270, 19));
        this.add(contthirdLevelPos, new KDLayout.Constraints(722, 58, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntryPosition.setBounds(new Rectangle(724, 549, 600, 170));
        kdtEntryPosition_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntryPosition,new com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryPositionInfo(),null,false);
        this.add(kdtEntryPosition_detailPanel, new KDLayout.Constraints(724, 549, 600, 170, 0));
        chkisLatest.setBounds(new Rectangle(285, 591, 270, 19));
        this.add(chkisLatest, new KDLayout.Constraints(285, 591, 270, 19, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDateLastUpdateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contcurProject
        contcurProject.setBoundEditor(prmtcurProject);
        //contversion
        contversion.setBoundEditor(txtversion);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(spYear);
        //contstate
        contstate.setBoundEditor(state);
        //contauditTime
        contauditTime.setBoundEditor(pkauditTime);
        //kDTabbedPane1
        kDTabbedPane1.add(contractPanel, resHelper.getString("contractPanel.constraints"));
        kDTabbedPane1.add(accountPanel, resHelper.getString("accountPanel.constraints"));
        kDTabbedPane1.add(totalPanel, resHelper.getString("totalPanel.constraints"));
        kDTabbedPane1.add(sixMonthPanel, resHelper.getString("sixMonthPanel.constraints"));
        //contractPanel
        contractPanel.setLayout(new KDLayout());
        contractPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 974, 427));        kDContainer2.setBounds(new Rectangle(0, 0, 968, 425));
        contractPanel.add(kDContainer2, new KDLayout.Constraints(0, 0, 968, 425, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntrysContract_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrysContract,new com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryInfo(),null,false);
        kDContainer2.getContentPane().add(kdtEntrysContract_detailPanel, BorderLayout.CENTER);
        //accountPanel
        accountPanel.setLayout(new KDLayout());
        accountPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 974, 427));        kDTabbedPane2.setBounds(new Rectangle(2, 2, 966, 424));
        accountPanel.add(kDTabbedPane2, new KDLayout.Constraints(2, 2, 966, 424, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDTabbedPane2
        kDTabbedPane2.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane2.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane2.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
        kDTabbedPane2.add(kDPanel4, resHelper.getString("kDPanel4.constraints"));
        kDTabbedPane2.add(kDPanel5, resHelper.getString("kDPanel5.constraints"));
        kDTabbedPane2.add(kDPanel6, resHelper.getString("kDPanel6.constraints"));
        kDTabbedPane2.add(kDPanel7, resHelper.getString("kDPanel7.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 965, 391));        kDTable1.setBounds(new Rectangle(1, 1, 962, 356));
        kDPanel1.add(kDTable1, new KDLayout.Constraints(1, 1, 962, 356, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0, 0, 965, 391));        kDTable2.setBounds(new Rectangle(1, 1, 962, 356));
        kDPanel2.add(kDTable2, new KDLayout.Constraints(1, 1, 962, 356, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(0, 0, 965, 391));        kDTable3.setBounds(new Rectangle(1, 1, 962, 356));
        kDPanel3.add(kDTable3, new KDLayout.Constraints(1, 1, 962, 356, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel4
        kDPanel4.setLayout(new KDLayout());
        kDPanel4.putClientProperty("OriginalBounds", new Rectangle(0, 0, 965, 391));        kDTable4.setBounds(new Rectangle(1, 1, 962, 356));
        kDPanel4.add(kDTable4, new KDLayout.Constraints(1, 1, 962, 356, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel5
        kDPanel5.setLayout(new KDLayout());
        kDPanel5.putClientProperty("OriginalBounds", new Rectangle(0, 0, 965, 391));        kDTable5.setBounds(new Rectangle(1, 1, 962, 356));
        kDPanel5.add(kDTable5, new KDLayout.Constraints(1, 1, 962, 356, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel6
        kDPanel6.setLayout(new KDLayout());
        kDPanel6.putClientProperty("OriginalBounds", new Rectangle(0, 0, 965, 391));        kDTable6.setBounds(new Rectangle(1, 1, 962, 356));
        kDPanel6.add(kDTable6, new KDLayout.Constraints(1, 1, 962, 356, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel7
        kDPanel7.setLayout(new KDLayout());
        kDPanel7.putClientProperty("OriginalBounds", new Rectangle(0, 0, 965, 391));        kDContainer1.setBounds(new Rectangle(0, 0, 961, 392));
        kDPanel7.add(kDContainer1, new KDLayout.Constraints(0, 0, 961, 392, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntrysAccount_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrysAccount,new com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrysAccountInfo(),null,false);
        kDContainer1.getContentPane().add(kdtEntrysAccount_detailPanel, BorderLayout.CENTER);
		kdtEntrysAccount_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("accountIndex","LAND");
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        //totalPanel
        totalPanel.setLayout(new KDLayout());
        totalPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 974, 427));        kDContainer3.setBounds(new Rectangle(0, 1, 967, 424));
        totalPanel.add(kDContainer3, new KDLayout.Constraints(0, 1, 967, 424, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEentryTotal_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEentryTotal,new com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEentryTotalInfo(),null,false);
        kDContainer3.getContentPane().add(kdtEentryTotal_detailPanel, BorderLayout.CENTER);
        //sixMonthPanel
        sixMonthPanel.setLayout(new KDLayout());
        sixMonthPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 974, 427));        kDContainer4.setBounds(new Rectangle(0, 0, 968, 426));
        sixMonthPanel.add(kDContainer4, new KDLayout.Constraints(0, 0, 968, 426, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer4
kDContainer4.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntrySixMonth_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrySixMonth,new com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrySixMonthInfo(),null,false);
        kDContainer4.getContentPane().add(kdtEntrySixMonth_detailPanel, BorderLayout.CENTER);
        //contfirstLevelPos
        contfirstLevelPos.setBoundEditor(prmtfirstLevelPos);
        //contsecondLevelPos
        contsecondLevelPos.setBoundEditor(prmtsecondLevelPos);
        //contthirdLevelPos
        contthirdLevelPos.setBoundEditor(prmtthirdLevelPos);

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
        this.toolBar.add(btnUnAduit);
        this.toolBar.add(btnRevise);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("month", int.class, this.spMonth, "value");
		dataBinder.registerBinding("EntryPosition.seq", int.class, this.kdtEntryPosition, "seq.text");
		dataBinder.registerBinding("EntryPosition", com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryPositionInfo.class, this.kdtEntryPosition, "userObject");
		dataBinder.registerBinding("EntryPosition.level", int.class, this.kdtEntryPosition, "level.text");
		dataBinder.registerBinding("EntryPosition.position", java.lang.Object.class, this.kdtEntryPosition, "position.text");
		dataBinder.registerBinding("isLatest", boolean.class, this.chkisLatest, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtcurProject, "data");
		dataBinder.registerBinding("version", int.class, this.txtversion, "value");
		dataBinder.registerBinding("year", int.class, this.spYear, "value");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.state, "selectedItem");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkauditTime, "value");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrysContract, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryInfo.class, this.kdtEntrysContract, "userObject");
		dataBinder.registerBinding("entrys.Contract", String.class, this.kdtEntrysContract, "Contract.text");
		dataBinder.registerBinding("entrys.ContractBillId", String.class, this.kdtEntrysContract, "ContractBillId.text");
		dataBinder.registerBinding("entrys.Amount", java.math.BigDecimal.class, this.kdtEntrysContract, "Amount.text");
		dataBinder.registerBinding("entrys.ContractAmount", java.math.BigDecimal.class, this.kdtEntrysContract, "ContractAmount.text");
		dataBinder.registerBinding("entrys.designChangeAmount", String.class, this.kdtEntrysContract, "designChangeAmount.text");
		dataBinder.registerBinding("entrys.siteCertificatAmount", java.math.BigDecimal.class, this.kdtEntrysContract, "siteCertificatAmount.text");
		dataBinder.registerBinding("entrys.settlementAmount", java.math.BigDecimal.class, this.kdtEntrysContract, "settlementAmount.text");
		dataBinder.registerBinding("entrys.sumB", java.math.BigDecimal.class, this.kdtEntrysContract, "sumB.text");
		dataBinder.registerBinding("entrys.onWayCost", java.math.BigDecimal.class, this.kdtEntrysContract, "onWayCost.text");
		dataBinder.registerBinding("entrys.curMonthEstimateChange", java.math.BigDecimal.class, this.kdtEntrysContract, "curMonthEstimateChange.text");
		dataBinder.registerBinding("entrys.EstimateChangeBalance", java.math.BigDecimal.class, this.kdtEntrysContract, "EstimateChangeBalance.text");
		dataBinder.registerBinding("entrys.curMonthOtherAmount", java.math.BigDecimal.class, this.kdtEntrysContract, "curMonthOtherAmount.text");
		dataBinder.registerBinding("entrys.otherAmount", java.math.BigDecimal.class, this.kdtEntrysContract, "otherAmount.text");
		dataBinder.registerBinding("entrys.unSignContract", java.math.BigDecimal.class, this.kdtEntrysContract, "unSignContract.text");
		dataBinder.registerBinding("entrys.sumC", java.math.BigDecimal.class, this.kdtEntrysContract, "sumC.text");
		dataBinder.registerBinding("entrys.dynamicCost", java.math.BigDecimal.class, this.kdtEntrysContract, "dynamicCost.text");
		dataBinder.registerBinding("entrys.diffAmount", java.math.BigDecimal.class, this.kdtEntrysContract, "diffAmount.text");
		dataBinder.registerBinding("entrys.diffRate", java.math.BigDecimal.class, this.kdtEntrysContract, "diffRate.text");
		dataBinder.registerBinding("entrys.Comment", String.class, this.kdtEntrysContract, "Comment.text");
		dataBinder.registerBinding("entrys.curMonthOtherId", String.class, this.kdtEntrysContract, "curMonthOtherId.text");
		dataBinder.registerBinding("entrys.ForecastChangeVisID", String.class, this.kdtEntrysContract, "ForecastChangeVisID.text");
		dataBinder.registerBinding("entrys.programmingId", String.class, this.kdtEntrysContract, "programmingId.text");
		dataBinder.registerBinding("entrys.programmingName", String.class, this.kdtEntrysContract, "programmingName.text");
		dataBinder.registerBinding("entrys.notextContract", java.math.BigDecimal.class, this.kdtEntrysContract, "notextContract.text");
		dataBinder.registerBinding("EntrysAccount.seq", int.class, this.kdtEntrysAccount, "seq.text");
		dataBinder.registerBinding("EntrysAccount", com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrysAccountInfo.class, this.kdtEntrysAccount, "userObject");
		dataBinder.registerBinding("EntrysAccount.costAccount", String.class, this.kdtEntrysAccount, "costAccount.text");
		dataBinder.registerBinding("EntrysAccount.costAccountNumber", String.class, this.kdtEntrysAccount, "costAccountNumber.text");
		dataBinder.registerBinding("EntrysAccount.aimCost", java.math.BigDecimal.class, this.kdtEntrysAccount, "aimCost.text");
		dataBinder.registerBinding("EntrysAccount.Contract", java.math.BigDecimal.class, this.kdtEntrysAccount, "Contract.text");
		dataBinder.registerBinding("EntrysAccount.designChangeAmount", java.math.BigDecimal.class, this.kdtEntrysAccount, "designChangeAmount.text");
		dataBinder.registerBinding("EntrysAccount.siteCertificatAmount", java.math.BigDecimal.class, this.kdtEntrysAccount, "siteCertificatAmount.text");
		dataBinder.registerBinding("EntrysAccount.settlementAmount", java.math.BigDecimal.class, this.kdtEntrysAccount, "settlementAmount.text");
		dataBinder.registerBinding("EntrysAccount.withouttxt", java.math.BigDecimal.class, this.kdtEntrysAccount, "withouttxt.text");
		dataBinder.registerBinding("EntrysAccount.sumB", java.math.BigDecimal.class, this.kdtEntrysAccount, "sumB.text");
		dataBinder.registerBinding("EntrysAccount.onWayCost", java.math.BigDecimal.class, this.kdtEntrysAccount, "onWayCost.text");
		dataBinder.registerBinding("EntrysAccount.EstimateChangeBalance", java.math.BigDecimal.class, this.kdtEntrysAccount, "EstimateChangeBalance.text");
		dataBinder.registerBinding("EntrysAccount.otherAmount", java.math.BigDecimal.class, this.kdtEntrysAccount, "otherAmount.text");
		dataBinder.registerBinding("EntrysAccount.unSignContract", java.math.BigDecimal.class, this.kdtEntrysAccount, "unSignContract.text");
		dataBinder.registerBinding("EntrysAccount.sumC", java.math.BigDecimal.class, this.kdtEntrysAccount, "sumC.text");
		dataBinder.registerBinding("EntrysAccount.sumBC", java.math.BigDecimal.class, this.kdtEntrysAccount, "sumBC.text");
		dataBinder.registerBinding("EntrysAccount.diffAmount", java.math.BigDecimal.class, this.kdtEntrysAccount, "diffAmount.text");
		dataBinder.registerBinding("EntrysAccount.diffRate", java.math.BigDecimal.class, this.kdtEntrysAccount, "diffRate.text");
		dataBinder.registerBinding("EntrysAccount.Comment", String.class, this.kdtEntrysAccount, "Comment.text");
		dataBinder.registerBinding("EntrysAccount.accountIndex", com.kingdee.util.enums.Enum.class, this.kdtEntrysAccount, "accountIndex.text");
		dataBinder.registerBinding("EntrysAccount.level", int.class, this.kdtEntrysAccount, "level.text");
		dataBinder.registerBinding("EntrysAccount.alertIndex", java.math.BigDecimal.class, this.kdtEntrysAccount, "alertIndex.text");
		dataBinder.registerBinding("EentryTotal.seq", int.class, this.kdtEentryTotal, "seq.text");
		dataBinder.registerBinding("EentryTotal", com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEentryTotalInfo.class, this.kdtEentryTotal, "userObject");
		dataBinder.registerBinding("EentryTotal.costAccount", String.class, this.kdtEentryTotal, "costAccount.text");
		dataBinder.registerBinding("EentryTotal.costAccountNumber", String.class, this.kdtEentryTotal, "costAccountNumber.text");
		dataBinder.registerBinding("EentryTotal.aimCost", java.math.BigDecimal.class, this.kdtEentryTotal, "aimCost.text");
		dataBinder.registerBinding("EentryTotal.firstVersionAimcost", java.math.BigDecimal.class, this.kdtEentryTotal, "firstVersionAimcost.text");
		dataBinder.registerBinding("EentryTotal.deletaAimcost", java.math.BigDecimal.class, this.kdtEentryTotal, "deletaAimcost.text");
		dataBinder.registerBinding("EentryTotal.Contract", java.math.BigDecimal.class, this.kdtEentryTotal, "Contract.text");
		dataBinder.registerBinding("EentryTotal.designChangeAmount", java.math.BigDecimal.class, this.kdtEentryTotal, "designChangeAmount.text");
		dataBinder.registerBinding("EentryTotal.siteCertificatAmount", java.math.BigDecimal.class, this.kdtEentryTotal, "siteCertificatAmount.text");
		dataBinder.registerBinding("EentryTotal.settlementAmount", java.math.BigDecimal.class, this.kdtEentryTotal, "settlementAmount.text");
		dataBinder.registerBinding("EentryTotal.withouttxt", java.math.BigDecimal.class, this.kdtEentryTotal, "withouttxt.text");
		dataBinder.registerBinding("EentryTotal.sumB", java.math.BigDecimal.class, this.kdtEentryTotal, "sumB.text");
		dataBinder.registerBinding("EentryTotal.onWayCost", java.math.BigDecimal.class, this.kdtEentryTotal, "onWayCost.text");
		dataBinder.registerBinding("EentryTotal.EstimateChangeBalance", java.math.BigDecimal.class, this.kdtEentryTotal, "EstimateChangeBalance.text");
		dataBinder.registerBinding("EentryTotal.otherAmount", java.math.BigDecimal.class, this.kdtEentryTotal, "otherAmount.text");
		dataBinder.registerBinding("EentryTotal.unSignContract", java.math.BigDecimal.class, this.kdtEentryTotal, "unSignContract.text");
		dataBinder.registerBinding("EentryTotal.sumC", java.math.BigDecimal.class, this.kdtEentryTotal, "sumC.text");
		dataBinder.registerBinding("EentryTotal.sumBC", java.math.BigDecimal.class, this.kdtEentryTotal, "sumBC.text");
		dataBinder.registerBinding("EentryTotal.diffAmount", java.math.BigDecimal.class, this.kdtEentryTotal, "diffAmount.text");
		dataBinder.registerBinding("EentryTotal.diffRate", java.math.BigDecimal.class, this.kdtEentryTotal, "diffRate.text");
		dataBinder.registerBinding("EentryTotal.Comment", String.class, this.kdtEentryTotal, "Comment.text");
		dataBinder.registerBinding("EntrySixMonth.seq", int.class, this.kdtEntrySixMonth, "seq.text");
		dataBinder.registerBinding("EntrySixMonth", com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrySixMonthInfo.class, this.kdtEntrySixMonth, "userObject");
		dataBinder.registerBinding("EntrySixMonth.firstAimCost", java.math.BigDecimal.class, this.kdtEntrySixMonth, "firstAimCost.text");
		dataBinder.registerBinding("EntrySixMonth.firstDynaCost", java.math.BigDecimal.class, this.kdtEntrySixMonth, "firstDynaCost.text");
		dataBinder.registerBinding("EntrySixMonth.firstDiffAmount", java.math.BigDecimal.class, this.kdtEntrySixMonth, "firstDiffAmount.text");
		dataBinder.registerBinding("EntrySixMonth.firstDiffRate", java.math.BigDecimal.class, this.kdtEntrySixMonth, "firstDiffRate.text");
		dataBinder.registerBinding("EntrySixMonth.secondAimCost", java.math.BigDecimal.class, this.kdtEntrySixMonth, "secondAimCost.text");
		dataBinder.registerBinding("EntrySixMonth.secondDynaCost", java.math.BigDecimal.class, this.kdtEntrySixMonth, "secondDynaCost.text");
		dataBinder.registerBinding("EntrySixMonth.secondDiffAmount", java.math.BigDecimal.class, this.kdtEntrySixMonth, "secondDiffAmount.text");
		dataBinder.registerBinding("EntrySixMonth.secondDiffRate", java.math.BigDecimal.class, this.kdtEntrySixMonth, "secondDiffRate.text");
		dataBinder.registerBinding("EntrySixMonth.thirdAimCost", java.math.BigDecimal.class, this.kdtEntrySixMonth, "thirdAimCost.text");
		dataBinder.registerBinding("EntrySixMonth.thirdDynaCost", java.math.BigDecimal.class, this.kdtEntrySixMonth, "thirdDynaCost.text");
		dataBinder.registerBinding("EntrySixMonth.thirdDiffAmount", java.math.BigDecimal.class, this.kdtEntrySixMonth, "thirdDiffAmount.text");
		dataBinder.registerBinding("EntrySixMonth.thirdDiffRate", java.math.BigDecimal.class, this.kdtEntrySixMonth, "thirdDiffRate.text");
		dataBinder.registerBinding("EntrySixMonth.fourthAimCost", java.math.BigDecimal.class, this.kdtEntrySixMonth, "fourthAimCost.text");
		dataBinder.registerBinding("EntrySixMonth.fourthDynaCost", java.math.BigDecimal.class, this.kdtEntrySixMonth, "fourthDynaCost.text");
		dataBinder.registerBinding("EntrySixMonth.fourthDiffAmount", java.math.BigDecimal.class, this.kdtEntrySixMonth, "fourthDiffAmount.text");
		dataBinder.registerBinding("EntrySixMonth.fourthDiffRate", java.math.BigDecimal.class, this.kdtEntrySixMonth, "fourthDiffRate.text");
		dataBinder.registerBinding("EntrySixMonth.fifthAimCost", java.math.BigDecimal.class, this.kdtEntrySixMonth, "fifthAimCost.text");
		dataBinder.registerBinding("EntrySixMonth.fifthDynaCost", java.math.BigDecimal.class, this.kdtEntrySixMonth, "fifthDynaCost.text");
		dataBinder.registerBinding("EntrySixMonth.fifthDiffAmount", java.math.BigDecimal.class, this.kdtEntrySixMonth, "fifthDiffAmount.text");
		dataBinder.registerBinding("EntrySixMonth.fifthDiffRate", java.math.BigDecimal.class, this.kdtEntrySixMonth, "fifthDiffRate.text");
		dataBinder.registerBinding("EntrySixMonth.sixthAimCost", java.math.BigDecimal.class, this.kdtEntrySixMonth, "sixthAimCost.text");
		dataBinder.registerBinding("EntrySixMonth.sixthDynaCost", java.math.BigDecimal.class, this.kdtEntrySixMonth, "sixthDynaCost.text");
		dataBinder.registerBinding("EntrySixMonth.sixthDiffAmount", java.math.BigDecimal.class, this.kdtEntrySixMonth, "sixthDiffAmount.text");
		dataBinder.registerBinding("EntrySixMonth.sixthDiffRate", java.math.BigDecimal.class, this.kdtEntrySixMonth, "sixthDiffRate.text");
		dataBinder.registerBinding("EntrySixMonth.costAccount", String.class, this.kdtEntrySixMonth, "costAccount.text");
		dataBinder.registerBinding("EntrySixMonth.costAccountNumber", String.class, this.kdtEntrySixMonth, "costAccountNumber.text");
		dataBinder.registerBinding("firstLevelPos", com.kingdee.eas.basedata.org.PositionInfo.class, this.prmtfirstLevelPos, "data");
		dataBinder.registerBinding("secondLevelPos", com.kingdee.eas.basedata.org.PositionInfo.class, this.prmtsecondLevelPos, "data");
		dataBinder.registerBinding("thirdLevelPos", com.kingdee.eas.basedata.org.PositionInfo.class, this.prmtthirdLevelPos, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app.ProjectDynamicCostEditUIHandler";
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
        this.spYear.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo)ov;
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
		getValidateHelper().registerBindProperty("month", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPosition.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPosition", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPosition.level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPosition.position", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isLatest", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("version", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("year", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Contract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.ContractBillId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.ContractAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.designChangeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.siteCertificatAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.settlementAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.sumB", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.onWayCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.curMonthEstimateChange", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.EstimateChangeBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.curMonthOtherAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.otherAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.unSignContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.sumC", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.dynamicCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.diffAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.diffRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Comment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.curMonthOtherId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.ForecastChangeVisID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.programmingId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.programmingName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.notextContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.costAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.costAccountNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.aimCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.Contract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.designChangeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.siteCertificatAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.settlementAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.withouttxt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.sumB", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.onWayCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.EstimateChangeBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.otherAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.unSignContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.sumC", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.sumBC", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.diffAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.diffRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.Comment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.accountIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrysAccount.alertIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.costAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.costAccountNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.aimCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.firstVersionAimcost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.deletaAimcost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.Contract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.designChangeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.siteCertificatAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.settlementAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.withouttxt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.sumB", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.onWayCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.EstimateChangeBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.otherAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.unSignContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.sumC", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.sumBC", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.diffAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.diffRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EentryTotal.Comment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.firstAimCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.firstDynaCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.firstDiffAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.firstDiffRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.secondAimCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.secondDynaCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.secondDiffAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.secondDiffRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.thirdAimCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.thirdDynaCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.thirdDiffAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.thirdDiffRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.fourthAimCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.fourthDynaCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.fourthDiffAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.fourthDiffRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.fifthAimCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.fifthDynaCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.fifthDiffAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.fifthDiffRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.sixthAimCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.sixthDynaCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.sixthDiffAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.sixthDiffRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.costAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntrySixMonth.costAccountNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("firstLevelPos", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("secondLevelPos", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("thirdLevelPos", ValidateHelper.ON_SAVE);    		
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
     * output btnLoadData_actionPerformed method
     */
    protected void btnLoadData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output spMonth_stateChanged method
     */
    protected void spMonth_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output spYear_stateChanged method
     */
    protected void spYear_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrysContract_tableClicked method
     */
    protected void kdtEntrysContract_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output prmtfirstLevelPos_dataChanged method
     */
    protected void prmtfirstLevelPos_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtsecondLevelPos_dataChanged method
     */
    protected void prmtsecondLevelPos_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtthirdLevelPos_dataChanged method
     */
    protected void prmtthirdLevelPos_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("month"));
    	sic.add(new SelectorItemInfo("EntryPosition.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EntryPosition.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("EntryPosition.level"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EntryPosition.position.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("EntryPosition.position.id"));
			sic.add(new SelectorItemInfo("EntryPosition.position.name"));
        	sic.add(new SelectorItemInfo("EntryPosition.position.number"));
		}
        sic.add(new SelectorItemInfo("isLatest"));
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("curProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("curProject.id"));
        	sic.add(new SelectorItemInfo("curProject.number"));
        	sic.add(new SelectorItemInfo("curProject.name"));
		}
        sic.add(new SelectorItemInfo("version"));
        sic.add(new SelectorItemInfo("year"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("auditTime"));
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entrys.Contract"));
    	sic.add(new SelectorItemInfo("entrys.ContractBillId"));
    	sic.add(new SelectorItemInfo("entrys.Amount"));
    	sic.add(new SelectorItemInfo("entrys.ContractAmount"));
    	sic.add(new SelectorItemInfo("entrys.designChangeAmount"));
    	sic.add(new SelectorItemInfo("entrys.siteCertificatAmount"));
    	sic.add(new SelectorItemInfo("entrys.settlementAmount"));
    	sic.add(new SelectorItemInfo("entrys.sumB"));
    	sic.add(new SelectorItemInfo("entrys.onWayCost"));
    	sic.add(new SelectorItemInfo("entrys.curMonthEstimateChange"));
    	sic.add(new SelectorItemInfo("entrys.EstimateChangeBalance"));
    	sic.add(new SelectorItemInfo("entrys.curMonthOtherAmount"));
    	sic.add(new SelectorItemInfo("entrys.otherAmount"));
    	sic.add(new SelectorItemInfo("entrys.unSignContract"));
    	sic.add(new SelectorItemInfo("entrys.sumC"));
    	sic.add(new SelectorItemInfo("entrys.dynamicCost"));
    	sic.add(new SelectorItemInfo("entrys.diffAmount"));
    	sic.add(new SelectorItemInfo("entrys.diffRate"));
    	sic.add(new SelectorItemInfo("entrys.Comment"));
    	sic.add(new SelectorItemInfo("entrys.curMonthOtherId"));
    	sic.add(new SelectorItemInfo("entrys.ForecastChangeVisID"));
    	sic.add(new SelectorItemInfo("entrys.programmingId"));
    	sic.add(new SelectorItemInfo("entrys.programmingName"));
    	sic.add(new SelectorItemInfo("entrys.notextContract"));
    	sic.add(new SelectorItemInfo("EntrysAccount.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EntrysAccount.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("EntrysAccount.costAccount"));
    	sic.add(new SelectorItemInfo("EntrysAccount.costAccountNumber"));
    	sic.add(new SelectorItemInfo("EntrysAccount.aimCost"));
    	sic.add(new SelectorItemInfo("EntrysAccount.Contract"));
    	sic.add(new SelectorItemInfo("EntrysAccount.designChangeAmount"));
    	sic.add(new SelectorItemInfo("EntrysAccount.siteCertificatAmount"));
    	sic.add(new SelectorItemInfo("EntrysAccount.settlementAmount"));
    	sic.add(new SelectorItemInfo("EntrysAccount.withouttxt"));
    	sic.add(new SelectorItemInfo("EntrysAccount.sumB"));
    	sic.add(new SelectorItemInfo("EntrysAccount.onWayCost"));
    	sic.add(new SelectorItemInfo("EntrysAccount.EstimateChangeBalance"));
    	sic.add(new SelectorItemInfo("EntrysAccount.otherAmount"));
    	sic.add(new SelectorItemInfo("EntrysAccount.unSignContract"));
    	sic.add(new SelectorItemInfo("EntrysAccount.sumC"));
    	sic.add(new SelectorItemInfo("EntrysAccount.sumBC"));
    	sic.add(new SelectorItemInfo("EntrysAccount.diffAmount"));
    	sic.add(new SelectorItemInfo("EntrysAccount.diffRate"));
    	sic.add(new SelectorItemInfo("EntrysAccount.Comment"));
    	sic.add(new SelectorItemInfo("EntrysAccount.accountIndex"));
    	sic.add(new SelectorItemInfo("EntrysAccount.level"));
    	sic.add(new SelectorItemInfo("EntrysAccount.alertIndex"));
    	sic.add(new SelectorItemInfo("EentryTotal.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EentryTotal.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("EentryTotal.costAccount"));
    	sic.add(new SelectorItemInfo("EentryTotal.costAccountNumber"));
    	sic.add(new SelectorItemInfo("EentryTotal.aimCost"));
    	sic.add(new SelectorItemInfo("EentryTotal.firstVersionAimcost"));
    	sic.add(new SelectorItemInfo("EentryTotal.deletaAimcost"));
    	sic.add(new SelectorItemInfo("EentryTotal.Contract"));
    	sic.add(new SelectorItemInfo("EentryTotal.designChangeAmount"));
    	sic.add(new SelectorItemInfo("EentryTotal.siteCertificatAmount"));
    	sic.add(new SelectorItemInfo("EentryTotal.settlementAmount"));
    	sic.add(new SelectorItemInfo("EentryTotal.withouttxt"));
    	sic.add(new SelectorItemInfo("EentryTotal.sumB"));
    	sic.add(new SelectorItemInfo("EentryTotal.onWayCost"));
    	sic.add(new SelectorItemInfo("EentryTotal.EstimateChangeBalance"));
    	sic.add(new SelectorItemInfo("EentryTotal.otherAmount"));
    	sic.add(new SelectorItemInfo("EentryTotal.unSignContract"));
    	sic.add(new SelectorItemInfo("EentryTotal.sumC"));
    	sic.add(new SelectorItemInfo("EentryTotal.sumBC"));
    	sic.add(new SelectorItemInfo("EentryTotal.diffAmount"));
    	sic.add(new SelectorItemInfo("EentryTotal.diffRate"));
    	sic.add(new SelectorItemInfo("EentryTotal.Comment"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EntrySixMonth.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("EntrySixMonth.firstAimCost"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.firstDynaCost"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.firstDiffAmount"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.firstDiffRate"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.secondAimCost"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.secondDynaCost"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.secondDiffAmount"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.secondDiffRate"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.thirdAimCost"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.thirdDynaCost"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.thirdDiffAmount"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.thirdDiffRate"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.fourthAimCost"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.fourthDynaCost"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.fourthDiffAmount"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.fourthDiffRate"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.fifthAimCost"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.fifthDynaCost"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.fifthDiffAmount"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.fifthDiffRate"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.sixthAimCost"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.sixthDynaCost"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.sixthDiffAmount"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.sixthDiffRate"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.costAccount"));
    	sic.add(new SelectorItemInfo("EntrySixMonth.costAccountNumber"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("firstLevelPos.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("firstLevelPos.id"));
        	sic.add(new SelectorItemInfo("firstLevelPos.number"));
        	sic.add(new SelectorItemInfo("firstLevelPos.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("secondLevelPos.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("secondLevelPos.id"));
        	sic.add(new SelectorItemInfo("secondLevelPos.number"));
        	sic.add(new SelectorItemInfo("secondLevelPos.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("thirdLevelPos.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("thirdLevelPos.id"));
        	sic.add(new SelectorItemInfo("thirdLevelPos.number"));
        	sic.add(new SelectorItemInfo("thirdLevelPos.name"));
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
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostFactory.getRemoteInstance().audit(editData);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostFactory.getRemoteInstance().unAudit(editData);
    }
    	

    /**
     * output actionRevise_actionPerformed method
     */
    public void actionRevise_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionRevise(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRevise() {
    	return false;
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
            innerActionPerformed("eas", AbstractProjectDynamicCostEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractProjectDynamicCostEditUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionRevise class
     */     
    protected class ActionRevise extends ItemAction {     
    
        public ActionRevise()
        {
            this(null);
        }

        public ActionRevise(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRevise.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRevise.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRevise.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectDynamicCostEditUI.this, "ActionRevise", "actionRevise_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.client", "ProjectDynamicCostEditUI");
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
        return com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.client.ProjectDynamicCostEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo objectValue = new com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/aimcost/prjdynamiccostbill/ProjectDynamicCost";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app.ProjectDynamicCostQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntrysContract;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("state","1SAVED");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}