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
public abstract class AbstractComproductionEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractComproductionEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreportingUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contnote;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreportMonth;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtreportingUnit;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanenote;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtnote;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDComboBox state;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtreportMonth;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.eas.port.equipment.operate.ComproductionInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    /**
     * output class constructor
     */
    public AbstractComproductionEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractComproductionEditUI.class.getName());
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
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contreportingUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contnote = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contstate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreportMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtreportingUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.scrollPanenote = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtnote = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.state = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtreportMonth = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.kdtEntrys.setName("kdtEntrys");
        this.contreportingUnit.setName("contreportingUnit");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel3.setName("kDLabel3");
        this.kDLabel4.setName("kDLabel4");
        this.contnote.setName("contnote");
        this.contAuditTime.setName("contAuditTime");
        this.contstate.setName("contstate");
        this.contreportMonth.setName("contreportMonth");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtreportingUnit.setName("prmtreportingUnit");
        this.scrollPanenote.setName("scrollPanenote");
        this.txtnote.setName("txtnote");
        this.pkAuditTime.setName("pkAuditTime");
        this.state.setName("state");
        this.prmtreportMonth.setName("prmtreportMonth");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
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
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setEnabled(false);		
        this.contNumber.setVisible(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);
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
        this.contAuditor.setEnabled(false);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"project1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"stagePerformance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"proEnergy\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"fzproEnergy\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"lifeEnergy\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"otherEnergy\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"samePerformance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"increaseDecrease\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"increaseRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"periodCon\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"samePeriod\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"increaseRate1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"excessSection\" t:width=\"140\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{project1}</t:Cell><t:Cell>$Resource{stagePerformance}</t:Cell><t:Cell>$Resource{proEnergy}</t:Cell><t:Cell>$Resource{fzproEnergy}</t:Cell><t:Cell>$Resource{lifeEnergy}</t:Cell><t:Cell>$Resource{otherEnergy}</t:Cell><t:Cell>$Resource{samePerformance}</t:Cell><t:Cell>$Resource{increaseDecrease}</t:Cell><t:Cell>$Resource{increaseRate}</t:Cell><t:Cell>$Resource{periodCon}</t:Cell><t:Cell>$Resource{samePeriod}</t:Cell><t:Cell>$Resource{increaseRate1}</t:Cell><t:Cell>$Resource{excessSection}</t:Cell></t:Row><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{project_Row2}</t:Cell><t:Cell>$Resource{project1_Row2}</t:Cell><t:Cell>$Resource{stagePerformance_Row2}</t:Cell><t:Cell>$Resource{proEnergy_Row2}</t:Cell><t:Cell>$Resource{fzproEnergy_Row2}</t:Cell><t:Cell>$Resource{lifeEnergy_Row2}</t:Cell><t:Cell>$Resource{otherEnergy_Row2}</t:Cell><t:Cell>$Resource{samePerformance_Row2}</t:Cell><t:Cell>$Resource{increaseDecrease_Row2}</t:Cell><t:Cell>$Resource{increaseRate_Row2}</t:Cell><t:Cell>$Resource{periodCon_Row2}</t:Cell><t:Cell>$Resource{samePeriod_Row2}</t:Cell><t:Cell>$Resource{increaseRate1_Row2}</t:Cell><t:Cell>$Resource{excessSection_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"0\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"0\" t:right=\"7\" /><t:Block t:top=\"0\" t:left=\"8\" t:bottom=\"0\" t:right=\"10\" /><t:Block t:top=\"0\" t:left=\"11\" t:bottom=\"0\" t:right=\"13\" /><t:Block t:top=\"0\" t:left=\"14\" t:bottom=\"1\" t:right=\"14\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","project","project1","stagePerformance","proEnergy","fzproEnergy","lifeEnergy","otherEnergy","samePerformance","increaseDecrease","increaseRate","periodCon","samePeriod","increaseRate1","excessSection"});


        this.kdtEntrys.checkParsed();
        KDTextField kdtEntrys_project_TextField = new KDTextField();
        kdtEntrys_project_TextField.setName("kdtEntrys_project_TextField");
        kdtEntrys_project_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_project_CellEditor = new KDTDefaultCellEditor(kdtEntrys_project_TextField);
        this.kdtEntrys.getColumn("project").setEditor(kdtEntrys_project_CellEditor);
        KDTextField kdtEntrys_project1_TextField = new KDTextField();
        kdtEntrys_project1_TextField.setName("kdtEntrys_project1_TextField");
        kdtEntrys_project1_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_project1_CellEditor = new KDTDefaultCellEditor(kdtEntrys_project1_TextField);
        this.kdtEntrys.getColumn("project1").setEditor(kdtEntrys_project1_CellEditor);
        KDFormattedTextField kdtEntrys_stagePerformance_TextField = new KDFormattedTextField();
        kdtEntrys_stagePerformance_TextField.setName("kdtEntrys_stagePerformance_TextField");
        kdtEntrys_stagePerformance_TextField.setVisible(true);
        kdtEntrys_stagePerformance_TextField.setEditable(true);
        kdtEntrys_stagePerformance_TextField.setHorizontalAlignment(2);
        kdtEntrys_stagePerformance_TextField.setDataType(1);
        	kdtEntrys_stagePerformance_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_stagePerformance_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_stagePerformance_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_stagePerformance_CellEditor = new KDTDefaultCellEditor(kdtEntrys_stagePerformance_TextField);
        this.kdtEntrys.getColumn("stagePerformance").setEditor(kdtEntrys_stagePerformance_CellEditor);
        KDFormattedTextField kdtEntrys_proEnergy_TextField = new KDFormattedTextField();
        kdtEntrys_proEnergy_TextField.setName("kdtEntrys_proEnergy_TextField");
        kdtEntrys_proEnergy_TextField.setVisible(true);
        kdtEntrys_proEnergy_TextField.setEditable(true);
        kdtEntrys_proEnergy_TextField.setHorizontalAlignment(2);
        kdtEntrys_proEnergy_TextField.setDataType(1);
        	kdtEntrys_proEnergy_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_proEnergy_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_proEnergy_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_proEnergy_CellEditor = new KDTDefaultCellEditor(kdtEntrys_proEnergy_TextField);
        this.kdtEntrys.getColumn("proEnergy").setEditor(kdtEntrys_proEnergy_CellEditor);
        KDFormattedTextField kdtEntrys_fzproEnergy_TextField = new KDFormattedTextField();
        kdtEntrys_fzproEnergy_TextField.setName("kdtEntrys_fzproEnergy_TextField");
        kdtEntrys_fzproEnergy_TextField.setVisible(true);
        kdtEntrys_fzproEnergy_TextField.setEditable(true);
        kdtEntrys_fzproEnergy_TextField.setHorizontalAlignment(2);
        kdtEntrys_fzproEnergy_TextField.setDataType(1);
        	kdtEntrys_fzproEnergy_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_fzproEnergy_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_fzproEnergy_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_fzproEnergy_CellEditor = new KDTDefaultCellEditor(kdtEntrys_fzproEnergy_TextField);
        this.kdtEntrys.getColumn("fzproEnergy").setEditor(kdtEntrys_fzproEnergy_CellEditor);
        KDFormattedTextField kdtEntrys_lifeEnergy_TextField = new KDFormattedTextField();
        kdtEntrys_lifeEnergy_TextField.setName("kdtEntrys_lifeEnergy_TextField");
        kdtEntrys_lifeEnergy_TextField.setVisible(true);
        kdtEntrys_lifeEnergy_TextField.setEditable(true);
        kdtEntrys_lifeEnergy_TextField.setHorizontalAlignment(2);
        kdtEntrys_lifeEnergy_TextField.setDataType(1);
        	kdtEntrys_lifeEnergy_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_lifeEnergy_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_lifeEnergy_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_lifeEnergy_CellEditor = new KDTDefaultCellEditor(kdtEntrys_lifeEnergy_TextField);
        this.kdtEntrys.getColumn("lifeEnergy").setEditor(kdtEntrys_lifeEnergy_CellEditor);
        KDFormattedTextField kdtEntrys_otherEnergy_TextField = new KDFormattedTextField();
        kdtEntrys_otherEnergy_TextField.setName("kdtEntrys_otherEnergy_TextField");
        kdtEntrys_otherEnergy_TextField.setVisible(true);
        kdtEntrys_otherEnergy_TextField.setEditable(true);
        kdtEntrys_otherEnergy_TextField.setHorizontalAlignment(2);
        kdtEntrys_otherEnergy_TextField.setDataType(1);
        	kdtEntrys_otherEnergy_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_otherEnergy_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_otherEnergy_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_otherEnergy_CellEditor = new KDTDefaultCellEditor(kdtEntrys_otherEnergy_TextField);
        this.kdtEntrys.getColumn("otherEnergy").setEditor(kdtEntrys_otherEnergy_CellEditor);
        KDFormattedTextField kdtEntrys_samePerformance_TextField = new KDFormattedTextField();
        kdtEntrys_samePerformance_TextField.setName("kdtEntrys_samePerformance_TextField");
        kdtEntrys_samePerformance_TextField.setVisible(true);
        kdtEntrys_samePerformance_TextField.setEditable(true);
        kdtEntrys_samePerformance_TextField.setHorizontalAlignment(2);
        kdtEntrys_samePerformance_TextField.setDataType(1);
        	kdtEntrys_samePerformance_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_samePerformance_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_samePerformance_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_samePerformance_CellEditor = new KDTDefaultCellEditor(kdtEntrys_samePerformance_TextField);
        this.kdtEntrys.getColumn("samePerformance").setEditor(kdtEntrys_samePerformance_CellEditor);
        KDFormattedTextField kdtEntrys_increaseDecrease_TextField = new KDFormattedTextField();
        kdtEntrys_increaseDecrease_TextField.setName("kdtEntrys_increaseDecrease_TextField");
        kdtEntrys_increaseDecrease_TextField.setVisible(true);
        kdtEntrys_increaseDecrease_TextField.setEditable(true);
        kdtEntrys_increaseDecrease_TextField.setHorizontalAlignment(2);
        kdtEntrys_increaseDecrease_TextField.setDataType(1);
        	kdtEntrys_increaseDecrease_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_increaseDecrease_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_increaseDecrease_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_increaseDecrease_CellEditor = new KDTDefaultCellEditor(kdtEntrys_increaseDecrease_TextField);
        this.kdtEntrys.getColumn("increaseDecrease").setEditor(kdtEntrys_increaseDecrease_CellEditor);
        KDFormattedTextField kdtEntrys_increaseRate_TextField = new KDFormattedTextField();
        kdtEntrys_increaseRate_TextField.setName("kdtEntrys_increaseRate_TextField");
        kdtEntrys_increaseRate_TextField.setVisible(true);
        kdtEntrys_increaseRate_TextField.setEditable(true);
        kdtEntrys_increaseRate_TextField.setHorizontalAlignment(2);
        kdtEntrys_increaseRate_TextField.setDataType(1);
        	kdtEntrys_increaseRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntrys_increaseRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntrys_increaseRate_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntrys_increaseRate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_increaseRate_TextField);
        this.kdtEntrys.getColumn("increaseRate").setEditor(kdtEntrys_increaseRate_CellEditor);
        KDFormattedTextField kdtEntrys_periodCon_TextField = new KDFormattedTextField();
        kdtEntrys_periodCon_TextField.setName("kdtEntrys_periodCon_TextField");
        kdtEntrys_periodCon_TextField.setVisible(true);
        kdtEntrys_periodCon_TextField.setEditable(true);
        kdtEntrys_periodCon_TextField.setHorizontalAlignment(2);
        kdtEntrys_periodCon_TextField.setDataType(1);
        	kdtEntrys_periodCon_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_periodCon_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_periodCon_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_periodCon_CellEditor = new KDTDefaultCellEditor(kdtEntrys_periodCon_TextField);
        this.kdtEntrys.getColumn("periodCon").setEditor(kdtEntrys_periodCon_CellEditor);
        KDFormattedTextField kdtEntrys_samePeriod_TextField = new KDFormattedTextField();
        kdtEntrys_samePeriod_TextField.setName("kdtEntrys_samePeriod_TextField");
        kdtEntrys_samePeriod_TextField.setVisible(true);
        kdtEntrys_samePeriod_TextField.setEditable(true);
        kdtEntrys_samePeriod_TextField.setHorizontalAlignment(2);
        kdtEntrys_samePeriod_TextField.setDataType(1);
        	kdtEntrys_samePeriod_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_samePeriod_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_samePeriod_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_samePeriod_CellEditor = new KDTDefaultCellEditor(kdtEntrys_samePeriod_TextField);
        this.kdtEntrys.getColumn("samePeriod").setEditor(kdtEntrys_samePeriod_CellEditor);
        KDFormattedTextField kdtEntrys_increaseRate1_TextField = new KDFormattedTextField();
        kdtEntrys_increaseRate1_TextField.setName("kdtEntrys_increaseRate1_TextField");
        kdtEntrys_increaseRate1_TextField.setVisible(true);
        kdtEntrys_increaseRate1_TextField.setEditable(true);
        kdtEntrys_increaseRate1_TextField.setHorizontalAlignment(2);
        kdtEntrys_increaseRate1_TextField.setDataType(1);
        	kdtEntrys_increaseRate1_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntrys_increaseRate1_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntrys_increaseRate1_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntrys_increaseRate1_CellEditor = new KDTDefaultCellEditor(kdtEntrys_increaseRate1_TextField);
        this.kdtEntrys.getColumn("increaseRate1").setEditor(kdtEntrys_increaseRate1_CellEditor);
        KDFormattedTextField kdtEntrys_excessSection_TextField = new KDFormattedTextField();
        kdtEntrys_excessSection_TextField.setName("kdtEntrys_excessSection_TextField");
        kdtEntrys_excessSection_TextField.setVisible(true);
        kdtEntrys_excessSection_TextField.setEditable(true);
        kdtEntrys_excessSection_TextField.setHorizontalAlignment(2);
        kdtEntrys_excessSection_TextField.setDataType(1);
        	kdtEntrys_excessSection_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_excessSection_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_excessSection_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_excessSection_CellEditor = new KDTDefaultCellEditor(kdtEntrys_excessSection_TextField);
        this.kdtEntrys.getColumn("excessSection").setEditor(kdtEntrys_excessSection_CellEditor);
        // contreportingUnit		
        this.contreportingUnit.setBoundLabelText(resHelper.getString("contreportingUnit.boundLabelText"));		
        this.contreportingUnit.setBoundLabelLength(100);		
        this.contreportingUnit.setBoundLabelUnderline(true);		
        this.contreportingUnit.setVisible(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));		
        this.kDLabel1.setFont(resHelper.getFont("kDLabel1.font"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));		
        this.kDLabel2.setFont(resHelper.getFont("kDLabel2.font"));
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // contnote		
        this.contnote.setBoundLabelText(resHelper.getString("contnote.boundLabelText"));		
        this.contnote.setBoundLabelLength(228);		
        this.contnote.setBoundLabelUnderline(true);		
        this.contnote.setVisible(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);		
        this.contAuditTime.setVisible(true);		
        this.contAuditTime.setEnabled(false);
        // contstate		
        this.contstate.setBoundLabelText(resHelper.getString("contstate.boundLabelText"));		
        this.contstate.setBoundLabelLength(100);		
        this.contstate.setBoundLabelUnderline(true);		
        this.contstate.setVisible(true);		
        this.contstate.setEnabled(false);
        // contreportMonth		
        this.contreportMonth.setBoundLabelText(resHelper.getString("contreportMonth.boundLabelText"));		
        this.contreportMonth.setBoundLabelLength(100);		
        this.contreportMonth.setBoundLabelUnderline(true);		
        this.contreportMonth.setVisible(true);
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
        this.pkBizDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkBizDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // prmtreportingUnit		
        this.prmtreportingUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtreportingUnit.setVisible(true);		
        this.prmtreportingUnit.setEditable(true);		
        this.prmtreportingUnit.setDisplayFormat("$name$");		
        this.prmtreportingUnit.setEditFormat("$number$");		
        this.prmtreportingUnit.setCommitFormat("$number$");		
        this.prmtreportingUnit.setRequired(false);
        this.prmtreportingUnit.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtreportingUnit_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // scrollPanenote
        // txtnote		
        this.txtnote.setVisible(true);		
        this.txtnote.setRequired(false);		
        this.txtnote.setMaxLength(1000);
        // pkAuditTime		
        this.pkAuditTime.setVisible(true);		
        this.pkAuditTime.setRequired(false);
        // state		
        this.state.setVisible(true);		
        this.state.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.state.setRequired(false);
        // prmtreportMonth		
        this.prmtreportMonth.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PeriodQuery");		
        this.prmtreportMonth.setVisible(true);		
        this.prmtreportMonth.setEditable(true);		
        this.prmtreportMonth.setDisplayFormat("$number$");		
        this.prmtreportMonth.setEditFormat("$number$");		
        this.prmtreportMonth.setCommitFormat("$number$");		
        this.prmtreportMonth.setRequired(false);
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtreportingUnit,txtnote,pkAuditTime,state,prmtreportMonth}));
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
        contCreator.setBounds(new Rectangle(12, 561, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(12, 561, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(12, 588, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(12, 588, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(373, 561, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(373, 561, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(373, 588, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(373, 588, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(800, 668, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(800, 668, 270, 19, 0));
        contBizDate.setBounds(new Rectangle(370, 42, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(370, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(781, 647, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(781, 647, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(735, 561, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(735, 561, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntrys.setBounds(new Rectangle(12, 93, 991, 351));
        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.port.equipment.operate.ComproductionEntryInfo(),null,false);
        this.add(kdtEntrys_detailPanel, new KDLayout.Constraints(12, 93, 991, 351, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contreportingUnit.setBounds(new Rectangle(12, 42, 270, 19));
        this.add(contreportingUnit, new KDLayout.Constraints(12, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel1.setBounds(new Rectangle(355, 1, 468, 37));
        this.add(kDLabel1, new KDLayout.Constraints(355, 1, 468, 37, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel2.setBounds(new Rectangle(12, 452, 61, 19));
        this.add(kDLabel2, new KDLayout.Constraints(12, 452, 61, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel3.setBounds(new Rectangle(77, 452, 913, 19));
        this.add(kDLabel3, new KDLayout.Constraints(77, 452, 913, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabel4.setBounds(new Rectangle(77, 468, 509, 19));
        this.add(kDLabel4, new KDLayout.Constraints(77, 468, 509, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contnote.setBounds(new Rectangle(12, 494, 989, 54));
        this.add(contnote, new KDLayout.Constraints(12, 494, 989, 54, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(735, 588, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(735, 588, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contstate.setBounds(new Rectangle(729, 42, 270, 19));
        this.add(contstate, new KDLayout.Constraints(729, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contreportMonth.setBounds(new Rectangle(12, 69, 270, 19));
        this.add(contreportMonth, new KDLayout.Constraints(12, 69, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
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
        //contreportingUnit
        contreportingUnit.setBoundEditor(prmtreportingUnit);
        //contnote
        contnote.setBoundEditor(scrollPanenote);
        //scrollPanenote
        scrollPanenote.getViewport().add(txtnote, null);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contstate
        contstate.setBoundEditor(state);
        //contreportMonth
        contreportMonth.setBoundEditor(prmtreportMonth);

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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
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
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.port.equipment.operate.ComproductionEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.project", String.class, this.kdtEntrys, "project.text");
		dataBinder.registerBinding("entrys.project1", String.class, this.kdtEntrys, "project1.text");
		dataBinder.registerBinding("entrys.stagePerformance", java.math.BigDecimal.class, this.kdtEntrys, "stagePerformance.text");
		dataBinder.registerBinding("entrys.proEnergy", java.math.BigDecimal.class, this.kdtEntrys, "proEnergy.text");
		dataBinder.registerBinding("entrys.fzproEnergy", java.math.BigDecimal.class, this.kdtEntrys, "fzproEnergy.text");
		dataBinder.registerBinding("entrys.lifeEnergy", java.math.BigDecimal.class, this.kdtEntrys, "lifeEnergy.text");
		dataBinder.registerBinding("entrys.otherEnergy", java.math.BigDecimal.class, this.kdtEntrys, "otherEnergy.text");
		dataBinder.registerBinding("entrys.samePerformance", java.math.BigDecimal.class, this.kdtEntrys, "samePerformance.text");
		dataBinder.registerBinding("entrys.increaseDecrease", java.math.BigDecimal.class, this.kdtEntrys, "increaseDecrease.text");
		dataBinder.registerBinding("entrys.increaseRate", java.math.BigDecimal.class, this.kdtEntrys, "increaseRate.text");
		dataBinder.registerBinding("entrys.periodCon", java.math.BigDecimal.class, this.kdtEntrys, "periodCon.text");
		dataBinder.registerBinding("entrys.samePeriod", java.math.BigDecimal.class, this.kdtEntrys, "samePeriod.text");
		dataBinder.registerBinding("entrys.increaseRate1", java.math.BigDecimal.class, this.kdtEntrys, "increaseRate1.text");
		dataBinder.registerBinding("entrys.excessSection", java.math.BigDecimal.class, this.kdtEntrys, "excessSection.text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("reportingUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtreportingUnit, "data");
		dataBinder.registerBinding("note", String.class, this.txtnote, "text");
		dataBinder.registerBinding("AuditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("state", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.state, "selectedItem");
		dataBinder.registerBinding("reportMonth", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.prmtreportMonth, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.operate.app.ComproductionEditUIHandler";
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
        this.prmtreportingUnit.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.operate.ComproductionInfo)ov;
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
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.project1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.stagePerformance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.proEnergy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.fzproEnergy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.lifeEnergy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.otherEnergy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.samePerformance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.increaseDecrease", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.increaseRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.periodCon", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.samePeriod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.increaseRate1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.excessSection", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reportingUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("note", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AuditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reportMonth", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output pkBizDate_dataChanged method
     */
    protected void pkBizDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here112
    }

    /**
     * output prmtreportingUnit_dataChanged method
     */
    protected void prmtreportingUnit_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here111
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
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entrys.project"));
    	sic.add(new SelectorItemInfo("entrys.project1"));
    	sic.add(new SelectorItemInfo("entrys.stagePerformance"));
    	sic.add(new SelectorItemInfo("entrys.proEnergy"));
    	sic.add(new SelectorItemInfo("entrys.fzproEnergy"));
    	sic.add(new SelectorItemInfo("entrys.lifeEnergy"));
    	sic.add(new SelectorItemInfo("entrys.otherEnergy"));
    	sic.add(new SelectorItemInfo("entrys.samePerformance"));
    	sic.add(new SelectorItemInfo("entrys.increaseDecrease"));
    	sic.add(new SelectorItemInfo("entrys.increaseRate"));
    	sic.add(new SelectorItemInfo("entrys.periodCon"));
    	sic.add(new SelectorItemInfo("entrys.samePeriod"));
    	sic.add(new SelectorItemInfo("entrys.increaseRate1"));
    	sic.add(new SelectorItemInfo("entrys.excessSection"));
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
			sic.add(new SelectorItemInfo("reportingUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("reportingUnit.id"));
        	sic.add(new SelectorItemInfo("reportingUnit.number"));
        	sic.add(new SelectorItemInfo("reportingUnit.name"));
		}
        sic.add(new SelectorItemInfo("note"));
        sic.add(new SelectorItemInfo("AuditTime"));
        sic.add(new SelectorItemInfo("state"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("reportMonth.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("reportMonth.id"));
        	sic.add(new SelectorItemInfo("reportMonth.number"));
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
        com.kingdee.eas.port.equipment.operate.ComproductionFactory.getRemoteInstance().actionAudit(editData);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.operate.ComproductionFactory.getRemoteInstance().actionUnAudit(editData);
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
            innerActionPerformed("eas", AbstractComproductionEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractComproductionEditUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.operate.client", "ComproductionEditUI");
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
        return com.kingdee.eas.port.equipment.operate.client.ComproductionEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.operate.ComproductionFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.operate.ComproductionInfo objectValue = new com.kingdee.eas.port.equipment.operate.ComproductionInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/operate/Comproduction";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.operate.app.ComproductionQuery");
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
		vo.put("state",new Integer(-3));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}