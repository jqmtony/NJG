/**
 * output package name
 */
package com.kingdee.eas.bpmdemo.client;

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
public abstract class AbstractChangeVisaAppEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChangeVisaAppEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane paneBIZLayerControl17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contconductDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurproject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contname;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchangereason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaudittype;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchangesubject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contconstrSite;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contperiod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjobtype;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUrgentDegree;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contconstrunit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contspecialtyType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contconductUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer reaDesc;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isImportChange;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contorgunit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contoffer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdesignUnit;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSendUnit;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtSendUnit_detailPanel = null;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDPanel billEntries;
    protected com.kingdee.bos.ctrl.swing.KDPanel assTab;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtconductDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcurproject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtname;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtchangereason;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtaudittype;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchangesubject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtconstrSite;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtperiod;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtjobtype;
    protected com.kingdee.bos.ctrl.swing.KDComboBox UrgentDegree;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtconstrunit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtspecialtyType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtconductUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtorgunit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtoffer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtdesignUnit;
    protected com.kingdee.eas.bpmdemo.ChangeVisaAppInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractChangeVisaAppEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChangeVisaAppEditUI.class.getName());
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
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.paneBIZLayerControl17 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contconductDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcurproject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contname = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchangereason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaudittype = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchangesubject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contconstrSite = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contperiod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjobtype = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUrgentDegree = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contconstrunit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contspecialtyType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contconductUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.reaDesc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isImportChange = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contorgunit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contoffer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdesignUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtSendUnit = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.billEntries = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.assTab = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.prmtconductDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcurproject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtname = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtchangereason = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtaudittype = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtchangesubject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtconstrSite = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtperiod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtjobtype = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.UrgentDegree = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtconstrunit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtspecialtyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtconductUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTextArea1 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtorgunit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtoffer = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtdesignUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contAuditor.setName("contAuditor");
        this.paneBIZLayerControl17.setName("paneBIZLayerControl17");
        this.contconductDept.setName("contconductDept");
        this.contcurproject.setName("contcurproject");
        this.contname.setName("contname");
        this.contchangereason.setName("contchangereason");
        this.contaudittype.setName("contaudittype");
        this.contchangesubject.setName("contchangesubject");
        this.contconstrSite.setName("contconstrSite");
        this.contperiod.setName("contperiod");
        this.contjobtype.setName("contjobtype");
        this.contUrgentDegree.setName("contUrgentDegree");
        this.contconstrunit.setName("contconstrunit");
        this.contspecialtyType.setName("contspecialtyType");
        this.contconductUnit.setName("contconductUnit");
        this.reaDesc.setName("reaDesc");
        this.isImportChange.setName("isImportChange");
        this.contorgunit.setName("contorgunit");
        this.contoffer.setName("contoffer");
        this.contdesignUnit.setName("contdesignUnit");
        this.kdtSendUnit.setName("kdtSendUnit");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.prmtAuditor.setName("prmtAuditor");
        this.billEntries.setName("billEntries");
        this.assTab.setName("assTab");
        this.kDContainer2.setName("kDContainer2");
        this.kdtEntrys.setName("kdtEntrys");
        this.kDContainer1.setName("kDContainer1");
        this.prmtconductDept.setName("prmtconductDept");
        this.prmtcurproject.setName("prmtcurproject");
        this.txtname.setName("txtname");
        this.prmtchangereason.setName("prmtchangereason");
        this.prmtaudittype.setName("prmtaudittype");
        this.txtchangesubject.setName("txtchangesubject");
        this.txtconstrSite.setName("txtconstrSite");
        this.prmtperiod.setName("prmtperiod");
        this.prmtjobtype.setName("prmtjobtype");
        this.UrgentDegree.setName("UrgentDegree");
        this.prmtconstrunit.setName("prmtconstrunit");
        this.prmtspecialtyType.setName("prmtspecialtyType");
        this.prmtconductUnit.setName("prmtconductUnit");
        this.kDTextArea1.setName("kDTextArea1");
        this.txtorgunit.setName("txtorgunit");
        this.txtoffer.setName("txtoffer");
        this.prmtdesignUnit.setName("prmtdesignUnit");
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
        this.contCreator.setBoundLabelAlignment(7);		
        this.contCreator.setVisible(true);		
        this.contCreator.setForeground(new java.awt.Color(0,0,0));
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);		
        this.contCreateTime.setBoundLabelAlignment(7);		
        this.contCreateTime.setVisible(true);		
        this.contCreateTime.setForeground(new java.awt.Color(0,0,0));
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);		
        this.contLastUpdateTime.setVisible(false);		
        this.contLastUpdateTime.setBoundLabelAlignment(7);		
        this.contLastUpdateTime.setForeground(new java.awt.Color(0,0,0));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelAlignment(7);		
        this.contNumber.setVisible(true);		
        this.contNumber.setForeground(new java.awt.Color(0,0,0));
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);		
        this.contBizDate.setForeground(new java.awt.Color(0,0,0));
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setBoundLabelAlignment(7);		
        this.contAuditor.setVisible(true);		
        this.contAuditor.setForeground(new java.awt.Color(0,0,0));
        // paneBIZLayerControl17		
        this.paneBIZLayerControl17.setVisible(true);
        // contconductDept		
        this.contconductDept.setBoundLabelText(resHelper.getString("contconductDept.boundLabelText"));		
        this.contconductDept.setBoundLabelLength(100);		
        this.contconductDept.setBoundLabelUnderline(true);		
        this.contconductDept.setVisible(true);
        // contcurproject		
        this.contcurproject.setBoundLabelText(resHelper.getString("contcurproject.boundLabelText"));		
        this.contcurproject.setBoundLabelLength(100);		
        this.contcurproject.setBoundLabelUnderline(true);		
        this.contcurproject.setVisible(true);
        // contname		
        this.contname.setBoundLabelText(resHelper.getString("contname.boundLabelText"));		
        this.contname.setBoundLabelLength(100);		
        this.contname.setBoundLabelUnderline(true);		
        this.contname.setVisible(true);
        // contchangereason		
        this.contchangereason.setBoundLabelText(resHelper.getString("contchangereason.boundLabelText"));		
        this.contchangereason.setBoundLabelLength(100);		
        this.contchangereason.setBoundLabelUnderline(true);		
        this.contchangereason.setVisible(true);
        // contaudittype		
        this.contaudittype.setBoundLabelText(resHelper.getString("contaudittype.boundLabelText"));		
        this.contaudittype.setBoundLabelLength(100);		
        this.contaudittype.setBoundLabelUnderline(true);		
        this.contaudittype.setVisible(true);
        // contchangesubject		
        this.contchangesubject.setBoundLabelText(resHelper.getString("contchangesubject.boundLabelText"));		
        this.contchangesubject.setBoundLabelLength(100);		
        this.contchangesubject.setBoundLabelUnderline(true);		
        this.contchangesubject.setVisible(true);
        // contconstrSite		
        this.contconstrSite.setBoundLabelText(resHelper.getString("contconstrSite.boundLabelText"));		
        this.contconstrSite.setBoundLabelLength(100);		
        this.contconstrSite.setBoundLabelUnderline(true);		
        this.contconstrSite.setVisible(true);
        // contperiod		
        this.contperiod.setBoundLabelText(resHelper.getString("contperiod.boundLabelText"));		
        this.contperiod.setBoundLabelLength(100);		
        this.contperiod.setBoundLabelUnderline(true);		
        this.contperiod.setVisible(true);
        // contjobtype		
        this.contjobtype.setBoundLabelText(resHelper.getString("contjobtype.boundLabelText"));		
        this.contjobtype.setBoundLabelLength(100);		
        this.contjobtype.setBoundLabelUnderline(true);		
        this.contjobtype.setVisible(true);
        // contUrgentDegree		
        this.contUrgentDegree.setBoundLabelText(resHelper.getString("contUrgentDegree.boundLabelText"));		
        this.contUrgentDegree.setBoundLabelLength(100);		
        this.contUrgentDegree.setBoundLabelUnderline(true);		
        this.contUrgentDegree.setVisible(true);
        // contconstrunit		
        this.contconstrunit.setBoundLabelText(resHelper.getString("contconstrunit.boundLabelText"));		
        this.contconstrunit.setBoundLabelLength(100);		
        this.contconstrunit.setBoundLabelUnderline(true);		
        this.contconstrunit.setVisible(true);
        // contspecialtyType		
        this.contspecialtyType.setBoundLabelText(resHelper.getString("contspecialtyType.boundLabelText"));		
        this.contspecialtyType.setBoundLabelLength(100);		
        this.contspecialtyType.setBoundLabelUnderline(true);		
        this.contspecialtyType.setVisible(true);
        // contconductUnit		
        this.contconductUnit.setBoundLabelText(resHelper.getString("contconductUnit.boundLabelText"));		
        this.contconductUnit.setBoundLabelLength(100);		
        this.contconductUnit.setBoundLabelUnderline(true);		
        this.contconductUnit.setVisible(true);
        // reaDesc		
        this.reaDesc.setBoundLabelText(resHelper.getString("reaDesc.boundLabelText"));		
        this.reaDesc.setBoundLabelLength(90);		
        this.reaDesc.setBoundLabelUnderline(true);
        // isImportChange		
        this.isImportChange.setText(resHelper.getString("isImportChange.text"));		
        this.isImportChange.setAlignmentY(10f);		
        this.isImportChange.setAlignmentX(20f);
        // contorgunit		
        this.contorgunit.setBoundLabelText(resHelper.getString("contorgunit.boundLabelText"));		
        this.contorgunit.setBoundLabelLength(100);		
        this.contorgunit.setBoundLabelUnderline(true);		
        this.contorgunit.setVisible(true);
        // contoffer		
        this.contoffer.setBoundLabelText(resHelper.getString("contoffer.boundLabelText"));		
        this.contoffer.setBoundLabelLength(100);		
        this.contoffer.setBoundLabelUnderline(true);		
        this.contoffer.setVisible(true);
        // contdesignUnit		
        this.contdesignUnit.setBoundLabelText(resHelper.getString("contdesignUnit.boundLabelText"));		
        this.contdesignUnit.setBoundLabelLength(100);		
        this.contdesignUnit.setBoundLabelUnderline(true);		
        this.contdesignUnit.setVisible(true);
        // kdtSendUnit
		String kdtSendUnitStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtSendUnit.setFormatXml(resHelper.translateString("kdtSendUnit",kdtSendUnitStrXML));

                this.kdtSendUnit.putBindContents("editData",new String[] {"seq"});


        this.kdtSendUnit.checkParsed();
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setVisible(true);		
        this.prmtCreator.setRequired(false);		
        this.prmtCreator.setForeground(new java.awt.Color(0,0,0));
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setEnabled(false);		
        this.kDDateCreateTime.setVisible(true);		
        this.kDDateCreateTime.setRequired(false);		
        this.kDDateCreateTime.setForeground(new java.awt.Color(0,0,0));
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setTimeEnabled(true);		
        this.kDDateLastUpdateTime.setEnabled(false);		
        this.kDDateLastUpdateTime.setVisible(false);		
        this.kDDateLastUpdateTime.setRequired(false);		
        this.kDDateLastUpdateTime.setForeground(new java.awt.Color(0,0,0));
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(false);		
        this.txtNumber.setForeground(new java.awt.Color(0,0,0));
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);		
        this.pkBizDate.setRequired(false);		
        this.pkBizDate.setForeground(new java.awt.Color(0,0,0));
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setVisible(true);		
        this.prmtAuditor.setRequired(false);		
        this.prmtAuditor.setForeground(new java.awt.Color(0,0,0));
        // billEntries		
        this.billEntries.setVisible(true);
        // assTab		
        this.assTab.setVisible(true);		
        this.assTab.setMinimumSize(new Dimension(50,10));
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));

                this.kdtEntrys.putBindContents("editData",new String[] {"number"});


        this.kdtEntrys.checkParsed();
        KDTextField kdtEntrys_number_TextField = new KDTextField();
        kdtEntrys_number_TextField.setName("kdtEntrys_number_TextField");
        kdtEntrys_number_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_number_CellEditor = new KDTDefaultCellEditor(kdtEntrys_number_TextField);
        this.kdtEntrys.getColumn("number").setEditor(kdtEntrys_number_CellEditor);
        // kDContainer1
        // prmtconductDept		
        this.prmtconductDept.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtconductDept.setVisible(true);		
        this.prmtconductDept.setEditable(true);		
        this.prmtconductDept.setDisplayFormat("$name$");		
        this.prmtconductDept.setEditFormat("$number$");		
        this.prmtconductDept.setCommitFormat("$number$");		
        this.prmtconductDept.setRequired(false);
        // prmtcurproject		
        this.prmtcurproject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");		
        this.prmtcurproject.setVisible(true);		
        this.prmtcurproject.setEditable(true);		
        this.prmtcurproject.setDisplayFormat("$name$");		
        this.prmtcurproject.setEditFormat("$number$");		
        this.prmtcurproject.setCommitFormat("$number$");		
        this.prmtcurproject.setRequired(false);
        // txtname		
        this.txtname.setVisible(true);		
        this.txtname.setHorizontalAlignment(2);		
        this.txtname.setMaxLength(100);		
        this.txtname.setRequired(false);
        // prmtchangereason		
        this.prmtchangereason.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ChangeTypeQuery");		
        this.prmtchangereason.setVisible(true);		
        this.prmtchangereason.setEditable(true);		
        this.prmtchangereason.setDisplayFormat("$isEnabled$");		
        this.prmtchangereason.setEditFormat("$number$");		
        this.prmtchangereason.setCommitFormat("$number$");		
        this.prmtchangereason.setRequired(false);
        // prmtaudittype		
        this.prmtaudittype.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ChangeTypeQuery");		
        this.prmtaudittype.setVisible(true);		
        this.prmtaudittype.setEditable(true);		
        this.prmtaudittype.setDisplayFormat("$name$");		
        this.prmtaudittype.setEditFormat("$number$");		
        this.prmtaudittype.setCommitFormat("$number$");		
        this.prmtaudittype.setRequired(false);
        // txtchangesubject		
        this.txtchangesubject.setVisible(true);		
        this.txtchangesubject.setHorizontalAlignment(2);		
        this.txtchangesubject.setMaxLength(100);		
        this.txtchangesubject.setRequired(false);
        // txtconstrSite		
        this.txtconstrSite.setVisible(true);		
        this.txtconstrSite.setHorizontalAlignment(2);		
        this.txtconstrSite.setMaxLength(100);		
        this.txtconstrSite.setRequired(false);
        // prmtperiod		
        this.prmtperiod.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PeriodQuery");		
        this.prmtperiod.setVisible(true);		
        this.prmtperiod.setEditable(true);		
        this.prmtperiod.setDisplayFormat("$periodNumber$");		
        this.prmtperiod.setEditFormat("$number$");		
        this.prmtperiod.setCommitFormat("$number$");		
        this.prmtperiod.setRequired(false);
        // prmtjobtype		
        this.prmtjobtype.setVisible(true);		
        this.prmtjobtype.setEditable(true);		
        this.prmtjobtype.setDisplayFormat("$name$");		
        this.prmtjobtype.setEditFormat("$number$");		
        this.prmtjobtype.setCommitFormat("$number$");		
        this.prmtjobtype.setRequired(false);
        // UrgentDegree		
        this.UrgentDegree.setVisible(true);		
        this.UrgentDegree.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum").toArray());		
        this.UrgentDegree.setRequired(false);
        // prmtconstrunit		
        this.prmtconstrunit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.PSupplierQuery");		
        this.prmtconstrunit.setVisible(true);		
        this.prmtconstrunit.setEditable(true);		
        this.prmtconstrunit.setDisplayFormat("$name$");		
        this.prmtconstrunit.setEditFormat("$number$");		
        this.prmtconstrunit.setCommitFormat("$number$");		
        this.prmtconstrunit.setRequired(false);
        // prmtspecialtyType		
        this.prmtspecialtyType.setQueryInfo("com.kingdee.eas.hr.base.app.SpecialtyTypeQuery");		
        this.prmtspecialtyType.setVisible(true);		
        this.prmtspecialtyType.setEditable(true);		
        this.prmtspecialtyType.setDisplayFormat("$name$");		
        this.prmtspecialtyType.setEditFormat("$number$");		
        this.prmtspecialtyType.setCommitFormat("$number$");		
        this.prmtspecialtyType.setRequired(false);
        // prmtconductUnit		
        this.prmtconductUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.PSupplierQuery");		
        this.prmtconductUnit.setVisible(true);		
        this.prmtconductUnit.setEditable(true);		
        this.prmtconductUnit.setDisplayFormat("$name$");		
        this.prmtconductUnit.setEditFormat("$number$");		
        this.prmtconductUnit.setCommitFormat("$number$");		
        this.prmtconductUnit.setRequired(false);
        // kDTextArea1
        // txtorgunit		
        this.txtorgunit.setVisible(true);		
        this.txtorgunit.setHorizontalAlignment(2);		
        this.txtorgunit.setMaxLength(100);		
        this.txtorgunit.setRequired(false);
        // txtoffer		
        this.txtoffer.setVisible(true);		
        this.txtoffer.setHorizontalAlignment(2);		
        this.txtoffer.setMaxLength(100);		
        this.txtoffer.setRequired(false);
        // prmtdesignUnit		
        this.prmtdesignUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.PSupplierQuery");		
        this.prmtdesignUnit.setVisible(true);		
        this.prmtdesignUnit.setEditable(true);		
        this.prmtdesignUnit.setDisplayFormat("$name$");		
        this.prmtdesignUnit.setEditFormat("$number$");		
        this.prmtdesignUnit.setCommitFormat("$number$");		
        this.prmtdesignUnit.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {kDDateLastUpdateTime,kDDateCreateTime,prmtCreator,prmtAuditor,pkBizDate,txtNumber,kdtEntrys,prmtconductDept,prmtcurproject,txtname,prmtchangereason,prmtaudittype,txtchangesubject,txtconstrSite,prmtperiod,prmtjobtype,UrgentDegree,prmtconstrunit,prmtspecialtyType,prmtconductUnit,txtorgunit,txtoffer,prmtdesignUnit}));
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
        contCreator.setBounds(new Rectangle(13, 562, 474, 19));
        this.add(contCreator, new KDLayout.Constraints(13, 562, 474, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(525, 562, 474, 19));
        this.add(contCreateTime, new KDLayout.Constraints(525, 562, 474, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateTime.setBounds(new Rectangle(525, 593, 474, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(525, 593, 474, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(6, 42, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(6, 42, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(6, 76, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(6, 76, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(13, 593, 474, 19));
        this.add(contAuditor, new KDLayout.Constraints(13, 593, 474, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        paneBIZLayerControl17.setBounds(new Rectangle(6, 295, 993, 253));
        this.add(paneBIZLayerControl17, new KDLayout.Constraints(6, 295, 993, 253, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contconductDept.setBounds(new Rectangle(6, 144, 270, 19));
        this.add(contconductDept, new KDLayout.Constraints(6, 144, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcurproject.setBounds(new Rectangle(525, 16, 474, 19));
        this.add(contcurproject, new KDLayout.Constraints(525, 16, 474, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contname.setBounds(new Rectangle(351, 42, 270, 19));
        this.add(contname, new KDLayout.Constraints(351, 42, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contchangereason.setBounds(new Rectangle(729, 42, 270, 19));
        this.add(contchangereason, new KDLayout.Constraints(729, 42, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contaudittype.setBounds(new Rectangle(351, 76, 270, 19));
        this.add(contaudittype, new KDLayout.Constraints(351, 76, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contchangesubject.setBounds(new Rectangle(729, 76, 270, 19));
        this.add(contchangesubject, new KDLayout.Constraints(729, 76, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contconstrSite.setBounds(new Rectangle(350, 226, 270, 19));
        this.add(contconstrSite, new KDLayout.Constraints(350, 226, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contperiod.setBounds(new Rectangle(6, 110, 270, 19));
        this.add(contperiod, new KDLayout.Constraints(6, 110, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contjobtype.setBounds(new Rectangle(351, 110, 270, 19));
        this.add(contjobtype, new KDLayout.Constraints(351, 110, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contUrgentDegree.setBounds(new Rectangle(729, 111, 270, 19));
        this.add(contUrgentDegree, new KDLayout.Constraints(729, 111, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contconstrunit.setBounds(new Rectangle(351, 181, 270, 19));
        this.add(contconstrunit, new KDLayout.Constraints(351, 181, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contspecialtyType.setBounds(new Rectangle(351, 144, 270, 19));
        this.add(contspecialtyType, new KDLayout.Constraints(351, 144, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contconductUnit.setBounds(new Rectangle(6, 181, 270, 19));
        this.add(contconductUnit, new KDLayout.Constraints(6, 181, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        reaDesc.setBounds(new Rectangle(729, 181, 270, 61));
        this.add(reaDesc, new KDLayout.Constraints(729, 181, 270, 61, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        isImportChange.setBounds(new Rectangle(350, 261, 166, 19));
        this.add(isImportChange, new KDLayout.Constraints(350, 261, 166, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contorgunit.setBounds(new Rectangle(6, 16, 474, 19));
        this.add(contorgunit, new KDLayout.Constraints(6, 16, 474, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contoffer.setBounds(new Rectangle(8, 218, 270, 19));
        this.add(contoffer, new KDLayout.Constraints(8, 218, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contdesignUnit.setBounds(new Rectangle(729, 144, 270, 19));
        this.add(contdesignUnit, new KDLayout.Constraints(729, 144, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtSendUnit.setBounds(new Rectangle(75, 135, 600, 170));
        kdtSendUnit_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtSendUnit,new com.kingdee.eas.bpmdemo.ChangeVisaAppSendUnitInfo(),null,false);
        this.add(kdtSendUnit_detailPanel, new KDLayout.Constraints(75, 135, 600, 170, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDateLastUpdateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //paneBIZLayerControl17
        paneBIZLayerControl17.add(billEntries, resHelper.getString("billEntries.constraints"));
        paneBIZLayerControl17.add(assTab, resHelper.getString("assTab.constraints"));
        //billEntries
billEntries.setLayout(new BorderLayout(0, 0));        billEntries.add(kDContainer2, BorderLayout.CENTER);
        //kDContainer2
        kDContainer2.getContentPane().setLayout(new KDLayout());
        kDContainer2.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 992, 220));        kdtEntrys.setBounds(new Rectangle(2, 12, 987, 200));
        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.bpmdemo.ChangeVisaAppEntryInfo(),null,false);
        kDContainer2.getContentPane().add(kdtEntrys_detailPanel, new KDLayout.Constraints(2, 12, 987, 200, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //assTab
assTab.setLayout(new BorderLayout(0, 0));        assTab.add(kDContainer1, BorderLayout.CENTER);
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 992, 220));        //contconductDept
        contconductDept.setBoundEditor(prmtconductDept);
        //contcurproject
        contcurproject.setBoundEditor(prmtcurproject);
        //contname
        contname.setBoundEditor(txtname);
        //contchangereason
        contchangereason.setBoundEditor(prmtchangereason);
        //contaudittype
        contaudittype.setBoundEditor(prmtaudittype);
        //contchangesubject
        contchangesubject.setBoundEditor(txtchangesubject);
        //contconstrSite
        contconstrSite.setBoundEditor(txtconstrSite);
        //contperiod
        contperiod.setBoundEditor(prmtperiod);
        //contjobtype
        contjobtype.setBoundEditor(prmtjobtype);
        //contUrgentDegree
        contUrgentDegree.setBoundEditor(UrgentDegree);
        //contconstrunit
        contconstrunit.setBoundEditor(prmtconstrunit);
        //contspecialtyType
        contspecialtyType.setBoundEditor(prmtspecialtyType);
        //contconductUnit
        contconductUnit.setBoundEditor(prmtconductUnit);
        //reaDesc
        reaDesc.setBoundEditor(kDTextArea1);
        //contorgunit
        contorgunit.setBoundEditor(txtorgunit);
        //contoffer
        contoffer.setBoundEditor(txtoffer);
        //contdesignUnit
        contdesignUnit.setBoundEditor(prmtdesignUnit);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("SendUnit.seq", int.class, this.kdtSendUnit, "seq.text");
		dataBinder.registerBinding("SendUnit", com.kingdee.eas.bpmdemo.ChangeVisaAppSendUnitInfo.class, this.kdtSendUnit, "userObject");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.bpmdemo.ChangeVisaAppEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.number", String.class, this.kdtEntrys, "number.text");
		dataBinder.registerBinding("conductDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtconductDept, "data");
		dataBinder.registerBinding("curproject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtcurproject, "data");
		dataBinder.registerBinding("name", String.class, this.txtname, "text");
		dataBinder.registerBinding("changereason", com.kingdee.eas.fdc.basedata.ChangeTypeInfo.class, this.prmtchangereason, "data");
		dataBinder.registerBinding("audittype", com.kingdee.eas.fdc.basedata.ChangeTypeInfo.class, this.prmtaudittype, "data");
		dataBinder.registerBinding("changesubject", String.class, this.txtchangesubject, "text");
		dataBinder.registerBinding("constrSite", String.class, this.txtconstrSite, "text");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.prmtperiod, "data");
		dataBinder.registerBinding("jobtype", com.kingdee.eas.basedata.org.JobTypeInfo.class, this.prmtjobtype, "data");
		dataBinder.registerBinding("UrgentDegree", com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum.class, this.UrgentDegree, "selectedItem");
		dataBinder.registerBinding("construnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtconstrunit, "data");
		dataBinder.registerBinding("specialtyType", com.kingdee.eas.hr.base.SpecialtyTypeInfo.class, this.prmtspecialtyType, "data");
		dataBinder.registerBinding("conductUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtconductUnit, "data");
		dataBinder.registerBinding("orgunit", String.class, this.txtorgunit, "text");
		dataBinder.registerBinding("offer", String.class, this.txtoffer, "text");
		dataBinder.registerBinding("designUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtdesignUnit, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.bpmdemo.app.ChangeVisaAppEditUIHandler";
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
        this.kDDateLastUpdateTime.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.bpmdemo.ChangeVisaAppInfo)ov;
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
		getValidateHelper().registerBindProperty("SendUnit.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SendUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conductDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curproject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changereason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("audittype", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changesubject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("constrSite", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jobtype", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("UrgentDegree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("construnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("specialtyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conductUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgunit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("offer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("designUnit", ValidateHelper.ON_SAVE);    		
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
    	sic.add(new SelectorItemInfo("SendUnit.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("SendUnit.*"));
		}
		else{
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
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("entrys.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("conductDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("conductDept.id"));
        	sic.add(new SelectorItemInfo("conductDept.number"));
        	sic.add(new SelectorItemInfo("conductDept.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("curproject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("curproject.id"));
        	sic.add(new SelectorItemInfo("curproject.number"));
        	sic.add(new SelectorItemInfo("curproject.name"));
		}
        sic.add(new SelectorItemInfo("name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("changereason.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("changereason.id"));
        	sic.add(new SelectorItemInfo("changereason.number"));
        	sic.add(new SelectorItemInfo("changereason.name"));
        	sic.add(new SelectorItemInfo("changereason.isEnabled"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("audittype.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("audittype.id"));
        	sic.add(new SelectorItemInfo("audittype.number"));
        	sic.add(new SelectorItemInfo("audittype.name"));
		}
        sic.add(new SelectorItemInfo("changesubject"));
        sic.add(new SelectorItemInfo("constrSite"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("period.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("period.id"));
        	sic.add(new SelectorItemInfo("period.number"));
        	sic.add(new SelectorItemInfo("period.periodNumber"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("jobtype.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("jobtype.id"));
        	sic.add(new SelectorItemInfo("jobtype.number"));
        	sic.add(new SelectorItemInfo("jobtype.name"));
		}
        sic.add(new SelectorItemInfo("UrgentDegree"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("construnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("construnit.id"));
        	sic.add(new SelectorItemInfo("construnit.number"));
        	sic.add(new SelectorItemInfo("construnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("specialtyType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("specialtyType.id"));
        	sic.add(new SelectorItemInfo("specialtyType.number"));
        	sic.add(new SelectorItemInfo("specialtyType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("conductUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("conductUnit.id"));
        	sic.add(new SelectorItemInfo("conductUnit.number"));
        	sic.add(new SelectorItemInfo("conductUnit.name"));
		}
        sic.add(new SelectorItemInfo("orgunit"));
        sic.add(new SelectorItemInfo("offer"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("designUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("designUnit.id"));
        	sic.add(new SelectorItemInfo("designUnit.number"));
        	sic.add(new SelectorItemInfo("designUnit.name"));
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

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.bpmdemo.client", "ChangeVisaAppEditUI");
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
        return com.kingdee.eas.bpmdemo.client.ChangeVisaAppEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.bpmdemo.ChangeVisaAppFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.bpmdemo.ChangeVisaAppInfo objectValue = new com.kingdee.eas.bpmdemo.ChangeVisaAppInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/bpmdemo/ChangeVisaApp";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.bpmdemo.app.ChangeVisaAppQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntrys;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("UrgentDegree","1normal");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}