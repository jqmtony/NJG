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
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtfirstLevelPos;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsecondLevelPos;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtthirdLevelPos;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAduit;
    protected com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
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
        this.prmtfirstLevelPos = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsecondLevelPos = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtthirdLevelPos = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAduit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
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
        this.prmtfirstLevelPos.setName("prmtfirstLevelPos");
        this.prmtsecondLevelPos.setName("prmtsecondLevelPos");
        this.prmtthirdLevelPos.setName("prmtthirdLevelPos");
        this.btnAudit.setName("btnAudit");
        this.btnUnAduit.setName("btnUnAduit");
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
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // prmtcurProject		
        this.prmtcurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");		
        this.prmtcurProject.setVisible(true);		
        this.prmtcurProject.setEditable(true);		
        this.prmtcurProject.setDisplayFormat("$name$");		
        this.prmtcurProject.setEditFormat("$number$");		
        this.prmtcurProject.setCommitFormat("$number$");		
        this.prmtcurProject.setRequired(false);
        // txtversion		
        this.txtversion.setVisible(true);		
        this.txtversion.setHorizontalAlignment(2);		
        this.txtversion.setDataType(0);		
        this.txtversion.setSupportedEmpty(true);		
        this.txtversion.setRequired(false);
        // spYear
        // state		
        this.state.setVisible(true);		
        this.state.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.state.setRequired(false);
        // pkauditTime		
        this.pkauditTime.setVisible(true);		
        this.pkauditTime.setRequired(false);
        // contractPanel
        // accountPanel
        // kDContainer2
        // kdtEntrysContract
		String kdtEntrysContractStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol19\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"programmingName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"Contract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"ContractBillId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"Amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"ContractAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"designChangeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"siteCertificatAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"settlementAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"sumB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"onWayCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"curMonthEstimateChange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"EstimateChangeBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"curMonthOtherAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"otherAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"unSignContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"sumC\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"dynamicCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"diffAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"diffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"Comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"curMonthOtherId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"ForecastChangeVisID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"programmingId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{programmingName}</t:Cell><t:Cell>$Resource{Contract}</t:Cell><t:Cell>$Resource{ContractBillId}</t:Cell><t:Cell>$Resource{Amount}</t:Cell><t:Cell>$Resource{ContractAmount}</t:Cell><t:Cell>$Resource{designChangeAmount}</t:Cell><t:Cell>$Resource{siteCertificatAmount}</t:Cell><t:Cell>$Resource{settlementAmount}</t:Cell><t:Cell>$Resource{sumB}</t:Cell><t:Cell>$Resource{onWayCost}</t:Cell><t:Cell>$Resource{curMonthEstimateChange}</t:Cell><t:Cell>$Resource{EstimateChangeBalance}</t:Cell><t:Cell>$Resource{curMonthOtherAmount}</t:Cell><t:Cell>$Resource{otherAmount}</t:Cell><t:Cell>$Resource{unSignContract}</t:Cell><t:Cell>$Resource{sumC}</t:Cell><t:Cell>$Resource{dynamicCost}</t:Cell><t:Cell>$Resource{diffAmount}</t:Cell><t:Cell>$Resource{diffRate}</t:Cell><t:Cell>$Resource{Comment}</t:Cell><t:Cell>$Resource{curMonthOtherId}</t:Cell><t:Cell>$Resource{ForecastChangeVisID}</t:Cell><t:Cell>$Resource{programmingId}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{programmingName_Row2}</t:Cell><t:Cell>$Resource{Contract_Row2}</t:Cell><t:Cell>$Resource{ContractBillId_Row2}</t:Cell><t:Cell>$Resource{Amount_Row2}</t:Cell><t:Cell>$Resource{ContractAmount_Row2}</t:Cell><t:Cell>$Resource{designChangeAmount_Row2}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row2}</t:Cell><t:Cell>$Resource{settlementAmount_Row2}</t:Cell><t:Cell>$Resource{sumB_Row2}</t:Cell><t:Cell>$Resource{onWayCost_Row2}</t:Cell><t:Cell>$Resource{curMonthEstimateChange_Row2}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row2}</t:Cell><t:Cell>$Resource{curMonthOtherAmount_Row2}</t:Cell><t:Cell>$Resource{otherAmount_Row2}</t:Cell><t:Cell>$Resource{unSignContract_Row2}</t:Cell><t:Cell>$Resource{sumC_Row2}</t:Cell><t:Cell>$Resource{dynamicCost_Row2}</t:Cell><t:Cell>$Resource{diffAmount_Row2}</t:Cell><t:Cell>$Resource{diffRate_Row2}</t:Cell><t:Cell>$Resource{Comment_Row2}</t:Cell><t:Cell>$Resource{curMonthOtherId_Row2}</t:Cell><t:Cell>$Resource{ForecastChangeVisID_Row2}</t:Cell><t:Cell>$Resource{programmingId_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row3}</t:Cell><t:Cell>$Resource{programmingName_Row3}</t:Cell><t:Cell>$Resource{Contract_Row3}</t:Cell><t:Cell>$Resource{ContractBillId_Row3}</t:Cell><t:Cell>$Resource{Amount_Row3}</t:Cell><t:Cell>$Resource{ContractAmount_Row3}</t:Cell><t:Cell>$Resource{designChangeAmount_Row3}</t:Cell><t:Cell>$Resource{siteCertificatAmount_Row3}</t:Cell><t:Cell>$Resource{settlementAmount_Row3}</t:Cell><t:Cell>$Resource{sumB_Row3}</t:Cell><t:Cell>$Resource{onWayCost_Row3}</t:Cell><t:Cell>$Resource{curMonthEstimateChange_Row3}</t:Cell><t:Cell>$Resource{EstimateChangeBalance_Row3}</t:Cell><t:Cell>$Resource{curMonthOtherAmount_Row3}</t:Cell><t:Cell>$Resource{otherAmount_Row3}</t:Cell><t:Cell>$Resource{unSignContract_Row3}</t:Cell><t:Cell>$Resource{sumC_Row3}</t:Cell><t:Cell>$Resource{dynamicCost_Row3}</t:Cell><t:Cell>$Resource{diffAmount_Row3}</t:Cell><t:Cell>$Resource{diffRate_Row3}</t:Cell><t:Cell>$Resource{Comment_Row3}</t:Cell><t:Cell>$Resource{curMonthOtherId_Row3}</t:Cell><t:Cell>$Resource{ForecastChangeVisID_Row3}</t:Cell><t:Cell>$Resource{programmingId_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"2\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"2\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"0\" t:right=\"16\" /><t:Block t:top=\"1\" t:left=\"5\" t:bottom=\"1\" t:right=\"9\" /><t:Block t:top=\"1\" t:left=\"10\" t:bottom=\"1\" t:right=\"16\" /><t:Block t:top=\"0\" t:left=\"17\" t:bottom=\"2\" t:right=\"17\" /><t:Block t:top=\"0\" t:left=\"18\" t:bottom=\"2\" t:right=\"18\" /><t:Block t:top=\"0\" t:left=\"19\" t:bottom=\"2\" t:right=\"19\" /><t:Block t:top=\"0\" t:left=\"20\" t:bottom=\"2\" t:right=\"20\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

                this.kdtEntrysContract.putBindContents("editData",new String[] {"id","programmingName","Contract","ContractBillId","Amount","ContractAmount","designChangeAmount","siteCertificatAmount","settlementAmount","sumB","onWayCost","curMonthEstimateChange","EstimateChangeBalance","curMonthOtherAmount","otherAmount","unSignContract","sumC","dynamicCost","diffAmount","diffRate","Comment","curMonthOtherId","ForecastChangeVisID","programmingId"});


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
        // prmtfirstLevelPos		
        this.prmtfirstLevelPos.setQueryInfo("com.kingdee.eas.basedata.org.app.PositionQuery");		
        this.prmtfirstLevelPos.setVisible(true);		
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
        this.prmtsecondLevelPos.setVisible(true);		
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
        this.prmtthirdLevelPos.setVisible(true);		
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
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtcurProject,txtversion,state,pkauditTime,prmtfirstLevelPos,prmtsecondLevelPos,prmtthirdLevelPos}));
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("month", int.class, this.spMonth, "value");
		dataBinder.registerBinding("EntryPosition.seq", int.class, this.kdtEntryPosition, "seq.text");
		dataBinder.registerBinding("EntryPosition", com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntryPositionInfo.class, this.kdtEntryPosition, "userObject");
		dataBinder.registerBinding("EntryPosition.level", int.class, this.kdtEntryPosition, "level.text");
		dataBinder.registerBinding("EntryPosition.position", java.lang.Object.class, this.kdtEntryPosition, "position.text");
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
        this.prmtcurProject.requestFocusInWindow();
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