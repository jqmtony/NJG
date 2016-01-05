/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractPcontractTrackBillEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPcontractTrackBillEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcurProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contversion;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisNew;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttrackBillStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditTime;
    protected com.kingdee.bos.ctrl.swing.KDButton btnGrabData;
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
    protected com.kingdee.bos.ctrl.swing.KDComboBox trackBillStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditTime;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFix;
    protected com.kingdee.eas.fdc.contract.PcontractTrackBillInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnaudit actionUnaudit = null;
    protected ActionFix actionFix = null;
    /**
     * output class constructor
     */
    public AbstractPcontractTrackBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPcontractTrackBillEditUI.class.getName());
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
        //actionUnaudit
        this.actionUnaudit = new ActionUnaudit(this);
        getActionManager().registerAction("actionUnaudit", actionUnaudit);
        this.actionUnaudit.setExtendProperty("canForewarn", "true");
        this.actionUnaudit.setExtendProperty("userDefined", "true");
        this.actionUnaudit.setExtendProperty("isObjectUpdateLock", "false");
         this.actionUnaudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionUnaudit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionFix
        this.actionFix = new ActionFix(this);
        getActionManager().registerAction("actionFix", actionFix);
        this.actionFix.setExtendProperty("canForewarn", "true");
        this.actionFix.setExtendProperty("userDefined", "true");
        this.actionFix.setExtendProperty("isObjectUpdateLock", "false");
         this.actionFix.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionFix.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contcurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contversion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisNew = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.conttrackBillStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnGrabData = new com.kingdee.bos.ctrl.swing.KDButton();
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
        this.trackBillStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkauditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFix = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.kdtEntrys.setName("kdtEntrys");
        this.contcurProject.setName("contcurProject");
        this.contversion.setName("contversion");
        this.chkisNew.setName("chkisNew");
        this.conttrackBillStatus.setName("conttrackBillStatus");
        this.contauditTime.setName("contauditTime");
        this.btnGrabData.setName("btnGrabData");
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
        this.trackBillStatus.setName("trackBillStatus");
        this.pkauditTime.setName("pkauditTime");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnFix.setName("btnFix");
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
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setVisible(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol19\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol21\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol22\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol23\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol25\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol26\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol27\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol29\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"pcid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"headNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"hyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"planAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"changeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"contralAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"sgtDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"sgtRealDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"sgtOverdue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sgtPlanDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"csDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"csRealDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"csOverdue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"csPlanDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol18\" /><t:Column t:key=\"startRealDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol19\" /><t:Column t:key=\"startOverdue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"startPlanDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol21\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol22\" /><t:Column t:key=\"endRealDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol23\" /><t:Column t:key=\"endOverdue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"endPlanDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol25\" /><t:Column t:key=\"csendDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol26\" /><t:Column t:key=\"csendRealDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol27\" /><t:Column t:key=\"csendOverdue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"csendPlanDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol29\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{pcid}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{headNumber}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{hyType}</t:Cell><t:Cell>$Resource{planAmount}</t:Cell><t:Cell>$Resource{changeRate}</t:Cell><t:Cell>$Resource{contralAmount}</t:Cell><t:Cell>$Resource{sgtDate}</t:Cell><t:Cell>$Resource{sgtRealDate}</t:Cell><t:Cell>$Resource{sgtOverdue}</t:Cell><t:Cell>$Resource{sgtPlanDate}</t:Cell><t:Cell>$Resource{csDate}</t:Cell><t:Cell>$Resource{csRealDate}</t:Cell><t:Cell>$Resource{csOverdue}</t:Cell><t:Cell>$Resource{csPlanDate}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{startRealDate}</t:Cell><t:Cell>$Resource{startOverdue}</t:Cell><t:Cell>$Resource{startPlanDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{endRealDate}</t:Cell><t:Cell>$Resource{endOverdue}</t:Cell><t:Cell>$Resource{endPlanDate}</t:Cell><t:Cell>$Resource{csendDate}</t:Cell><t:Cell>$Resource{csendRealDate}</t:Cell><t:Cell>$Resource{csendOverdue}</t:Cell><t:Cell>$Resource{csendPlanDate}</t:Cell></t:Row><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{pcid_Row2}</t:Cell><t:Cell>$Resource{level_Row2}</t:Cell><t:Cell>$Resource{longNumber_Row2}</t:Cell><t:Cell>$Resource{headNumber_Row2}</t:Cell><t:Cell>$Resource{name_Row2}</t:Cell><t:Cell>$Resource{hyType_Row2}</t:Cell><t:Cell>$Resource{planAmount_Row2}</t:Cell><t:Cell>$Resource{changeRate_Row2}</t:Cell><t:Cell>$Resource{contralAmount_Row2}</t:Cell><t:Cell>$Resource{sgtDate_Row2}</t:Cell><t:Cell>$Resource{sgtRealDate_Row2}</t:Cell><t:Cell>$Resource{sgtOverdue_Row2}</t:Cell><t:Cell>$Resource{sgtPlanDate_Row2}</t:Cell><t:Cell>$Resource{csDate_Row2}</t:Cell><t:Cell>$Resource{csRealDate_Row2}</t:Cell><t:Cell>$Resource{csOverdue_Row2}</t:Cell><t:Cell>$Resource{csPlanDate_Row2}</t:Cell><t:Cell>$Resource{startDate_Row2}</t:Cell><t:Cell>$Resource{startRealDate_Row2}</t:Cell><t:Cell>$Resource{startOverdue_Row2}</t:Cell><t:Cell>$Resource{startPlanDate_Row2}</t:Cell><t:Cell>$Resource{endDate_Row2}</t:Cell><t:Cell>$Resource{endRealDate_Row2}</t:Cell><t:Cell>$Resource{endOverdue_Row2}</t:Cell><t:Cell>$Resource{endPlanDate_Row2}</t:Cell><t:Cell>$Resource{csendDate_Row2}</t:Cell><t:Cell>$Resource{csendRealDate_Row2}</t:Cell><t:Cell>$Resource{csendOverdue_Row2}</t:Cell><t:Cell>$Resource{csendPlanDate_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"1\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"1\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"1\" t:right=\"7\" /><t:Block t:top=\"0\" t:left=\"8\" t:bottom=\"1\" t:right=\"8\" /><t:Block t:top=\"0\" t:left=\"9\" t:bottom=\"1\" t:right=\"9\" /><t:Block t:top=\"0\" t:left=\"10\" t:bottom=\"0\" t:right=\"13\" /><t:Block t:top=\"0\" t:left=\"14\" t:bottom=\"0\" t:right=\"17\" /><t:Block t:top=\"0\" t:left=\"18\" t:bottom=\"0\" t:right=\"25\" /><t:Block t:top=\"0\" t:left=\"26\" t:bottom=\"0\" t:right=\"29\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));

                this.kdtEntrys.putBindContents("editData",new String[] {"id","pcid","level","longNumber","headNumber","name","hyType","planAmount","changeRate","contralAmount","sgtDate","sgtRealDate","sgtOverdue","sgtPlanDate","csDate","csRealDate","csOverdue","csPlanDate","startDate","startRealDate","startOverdue","startPlanDate","endDate","endRealDate","endOverdue","endPlanDate","csendDate","csendRealDate","csendOverdue","csendPlanDate"});


        this.kdtEntrys.checkParsed();
        KDTextField kdtEntrys_pcid_TextField = new KDTextField();
        kdtEntrys_pcid_TextField.setName("kdtEntrys_pcid_TextField");
        kdtEntrys_pcid_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_pcid_CellEditor = new KDTDefaultCellEditor(kdtEntrys_pcid_TextField);
        this.kdtEntrys.getColumn("pcid").setEditor(kdtEntrys_pcid_CellEditor);
        KDFormattedTextField kdtEntrys_level_TextField = new KDFormattedTextField();
        kdtEntrys_level_TextField.setName("kdtEntrys_level_TextField");
        kdtEntrys_level_TextField.setVisible(true);
        kdtEntrys_level_TextField.setEditable(true);
        kdtEntrys_level_TextField.setHorizontalAlignment(2);
        kdtEntrys_level_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntrys_level_CellEditor = new KDTDefaultCellEditor(kdtEntrys_level_TextField);
        this.kdtEntrys.getColumn("level").setEditor(kdtEntrys_level_CellEditor);
        KDTextField kdtEntrys_longNumber_TextField = new KDTextField();
        kdtEntrys_longNumber_TextField.setName("kdtEntrys_longNumber_TextField");
        kdtEntrys_longNumber_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_longNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_longNumber_TextField);
        this.kdtEntrys.getColumn("longNumber").setEditor(kdtEntrys_longNumber_CellEditor);
        KDTextField kdtEntrys_headNumber_TextField = new KDTextField();
        kdtEntrys_headNumber_TextField.setName("kdtEntrys_headNumber_TextField");
        kdtEntrys_headNumber_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_headNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_headNumber_TextField);
        this.kdtEntrys.getColumn("headNumber").setEditor(kdtEntrys_headNumber_CellEditor);
        KDTextField kdtEntrys_name_TextField = new KDTextField();
        kdtEntrys_name_TextField.setName("kdtEntrys_name_TextField");
        kdtEntrys_name_TextField.setMaxLength(200);
        KDTDefaultCellEditor kdtEntrys_name_CellEditor = new KDTDefaultCellEditor(kdtEntrys_name_TextField);
        this.kdtEntrys.getColumn("name").setEditor(kdtEntrys_name_CellEditor);
        KDTextField kdtEntrys_hyType_TextField = new KDTextField();
        kdtEntrys_hyType_TextField.setName("kdtEntrys_hyType_TextField");
        kdtEntrys_hyType_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_hyType_CellEditor = new KDTDefaultCellEditor(kdtEntrys_hyType_TextField);
        this.kdtEntrys.getColumn("hyType").setEditor(kdtEntrys_hyType_CellEditor);
        KDFormattedTextField kdtEntrys_planAmount_TextField = new KDFormattedTextField();
        kdtEntrys_planAmount_TextField.setName("kdtEntrys_planAmount_TextField");
        kdtEntrys_planAmount_TextField.setVisible(true);
        kdtEntrys_planAmount_TextField.setEditable(true);
        kdtEntrys_planAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_planAmount_TextField.setDataType(1);
        	kdtEntrys_planAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntrys_planAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntrys_planAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntrys_planAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_planAmount_TextField);
        this.kdtEntrys.getColumn("planAmount").setEditor(kdtEntrys_planAmount_CellEditor);
        KDFormattedTextField kdtEntrys_changeRate_TextField = new KDFormattedTextField();
        kdtEntrys_changeRate_TextField.setName("kdtEntrys_changeRate_TextField");
        kdtEntrys_changeRate_TextField.setVisible(true);
        kdtEntrys_changeRate_TextField.setEditable(true);
        kdtEntrys_changeRate_TextField.setHorizontalAlignment(2);
        kdtEntrys_changeRate_TextField.setDataType(1);
        	kdtEntrys_changeRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntrys_changeRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntrys_changeRate_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntrys_changeRate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_changeRate_TextField);
        this.kdtEntrys.getColumn("changeRate").setEditor(kdtEntrys_changeRate_CellEditor);
        KDFormattedTextField kdtEntrys_contralAmount_TextField = new KDFormattedTextField();
        kdtEntrys_contralAmount_TextField.setName("kdtEntrys_contralAmount_TextField");
        kdtEntrys_contralAmount_TextField.setVisible(true);
        kdtEntrys_contralAmount_TextField.setEditable(true);
        kdtEntrys_contralAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_contralAmount_TextField.setDataType(1);
        	kdtEntrys_contralAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntrys_contralAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntrys_contralAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntrys_contralAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_contralAmount_TextField);
        this.kdtEntrys.getColumn("contralAmount").setEditor(kdtEntrys_contralAmount_CellEditor);
        KDDatePicker kdtEntrys_sgtDate_DatePicker = new KDDatePicker();
        kdtEntrys_sgtDate_DatePicker.setName("kdtEntrys_sgtDate_DatePicker");
        kdtEntrys_sgtDate_DatePicker.setVisible(true);
        kdtEntrys_sgtDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_sgtDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_sgtDate_DatePicker);
        this.kdtEntrys.getColumn("sgtDate").setEditor(kdtEntrys_sgtDate_CellEditor);
        KDDatePicker kdtEntrys_sgtRealDate_DatePicker = new KDDatePicker();
        kdtEntrys_sgtRealDate_DatePicker.setName("kdtEntrys_sgtRealDate_DatePicker");
        kdtEntrys_sgtRealDate_DatePicker.setVisible(true);
        kdtEntrys_sgtRealDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_sgtRealDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_sgtRealDate_DatePicker);
        this.kdtEntrys.getColumn("sgtRealDate").setEditor(kdtEntrys_sgtRealDate_CellEditor);
        KDCheckBox kdtEntrys_sgtOverdue_CheckBox = new KDCheckBox();
        kdtEntrys_sgtOverdue_CheckBox.setName("kdtEntrys_sgtOverdue_CheckBox");
        KDTDefaultCellEditor kdtEntrys_sgtOverdue_CellEditor = new KDTDefaultCellEditor(kdtEntrys_sgtOverdue_CheckBox);
        this.kdtEntrys.getColumn("sgtOverdue").setEditor(kdtEntrys_sgtOverdue_CellEditor);
        KDDatePicker kdtEntrys_sgtPlanDate_DatePicker = new KDDatePicker();
        kdtEntrys_sgtPlanDate_DatePicker.setName("kdtEntrys_sgtPlanDate_DatePicker");
        kdtEntrys_sgtPlanDate_DatePicker.setVisible(true);
        kdtEntrys_sgtPlanDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_sgtPlanDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_sgtPlanDate_DatePicker);
        this.kdtEntrys.getColumn("sgtPlanDate").setEditor(kdtEntrys_sgtPlanDate_CellEditor);
        KDDatePicker kdtEntrys_csDate_DatePicker = new KDDatePicker();
        kdtEntrys_csDate_DatePicker.setName("kdtEntrys_csDate_DatePicker");
        kdtEntrys_csDate_DatePicker.setVisible(true);
        kdtEntrys_csDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_csDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_csDate_DatePicker);
        this.kdtEntrys.getColumn("csDate").setEditor(kdtEntrys_csDate_CellEditor);
        KDDatePicker kdtEntrys_csRealDate_DatePicker = new KDDatePicker();
        kdtEntrys_csRealDate_DatePicker.setName("kdtEntrys_csRealDate_DatePicker");
        kdtEntrys_csRealDate_DatePicker.setVisible(true);
        kdtEntrys_csRealDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_csRealDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_csRealDate_DatePicker);
        this.kdtEntrys.getColumn("csRealDate").setEditor(kdtEntrys_csRealDate_CellEditor);
        KDCheckBox kdtEntrys_csOverdue_CheckBox = new KDCheckBox();
        kdtEntrys_csOverdue_CheckBox.setName("kdtEntrys_csOverdue_CheckBox");
        KDTDefaultCellEditor kdtEntrys_csOverdue_CellEditor = new KDTDefaultCellEditor(kdtEntrys_csOverdue_CheckBox);
        this.kdtEntrys.getColumn("csOverdue").setEditor(kdtEntrys_csOverdue_CellEditor);
        KDDatePicker kdtEntrys_csPlanDate_DatePicker = new KDDatePicker();
        kdtEntrys_csPlanDate_DatePicker.setName("kdtEntrys_csPlanDate_DatePicker");
        kdtEntrys_csPlanDate_DatePicker.setVisible(true);
        kdtEntrys_csPlanDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_csPlanDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_csPlanDate_DatePicker);
        this.kdtEntrys.getColumn("csPlanDate").setEditor(kdtEntrys_csPlanDate_CellEditor);
        KDDatePicker kdtEntrys_startDate_DatePicker = new KDDatePicker();
        kdtEntrys_startDate_DatePicker.setName("kdtEntrys_startDate_DatePicker");
        kdtEntrys_startDate_DatePicker.setVisible(true);
        kdtEntrys_startDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_startDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_startDate_DatePicker);
        this.kdtEntrys.getColumn("startDate").setEditor(kdtEntrys_startDate_CellEditor);
        KDDatePicker kdtEntrys_startRealDate_DatePicker = new KDDatePicker();
        kdtEntrys_startRealDate_DatePicker.setName("kdtEntrys_startRealDate_DatePicker");
        kdtEntrys_startRealDate_DatePicker.setVisible(true);
        kdtEntrys_startRealDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_startRealDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_startRealDate_DatePicker);
        this.kdtEntrys.getColumn("startRealDate").setEditor(kdtEntrys_startRealDate_CellEditor);
        KDCheckBox kdtEntrys_startOverdue_CheckBox = new KDCheckBox();
        kdtEntrys_startOverdue_CheckBox.setName("kdtEntrys_startOverdue_CheckBox");
        KDTDefaultCellEditor kdtEntrys_startOverdue_CellEditor = new KDTDefaultCellEditor(kdtEntrys_startOverdue_CheckBox);
        this.kdtEntrys.getColumn("startOverdue").setEditor(kdtEntrys_startOverdue_CellEditor);
        KDDatePicker kdtEntrys_startPlanDate_DatePicker = new KDDatePicker();
        kdtEntrys_startPlanDate_DatePicker.setName("kdtEntrys_startPlanDate_DatePicker");
        kdtEntrys_startPlanDate_DatePicker.setVisible(true);
        kdtEntrys_startPlanDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_startPlanDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_startPlanDate_DatePicker);
        this.kdtEntrys.getColumn("startPlanDate").setEditor(kdtEntrys_startPlanDate_CellEditor);
        KDDatePicker kdtEntrys_endDate_DatePicker = new KDDatePicker();
        kdtEntrys_endDate_DatePicker.setName("kdtEntrys_endDate_DatePicker");
        kdtEntrys_endDate_DatePicker.setVisible(true);
        kdtEntrys_endDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_endDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_endDate_DatePicker);
        this.kdtEntrys.getColumn("endDate").setEditor(kdtEntrys_endDate_CellEditor);
        KDDatePicker kdtEntrys_endRealDate_DatePicker = new KDDatePicker();
        kdtEntrys_endRealDate_DatePicker.setName("kdtEntrys_endRealDate_DatePicker");
        kdtEntrys_endRealDate_DatePicker.setVisible(true);
        kdtEntrys_endRealDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_endRealDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_endRealDate_DatePicker);
        this.kdtEntrys.getColumn("endRealDate").setEditor(kdtEntrys_endRealDate_CellEditor);
        KDCheckBox kdtEntrys_endOverdue_CheckBox = new KDCheckBox();
        kdtEntrys_endOverdue_CheckBox.setName("kdtEntrys_endOverdue_CheckBox");
        KDTDefaultCellEditor kdtEntrys_endOverdue_CellEditor = new KDTDefaultCellEditor(kdtEntrys_endOverdue_CheckBox);
        this.kdtEntrys.getColumn("endOverdue").setEditor(kdtEntrys_endOverdue_CellEditor);
        KDDatePicker kdtEntrys_endPlanDate_DatePicker = new KDDatePicker();
        kdtEntrys_endPlanDate_DatePicker.setName("kdtEntrys_endPlanDate_DatePicker");
        kdtEntrys_endPlanDate_DatePicker.setVisible(true);
        kdtEntrys_endPlanDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_endPlanDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_endPlanDate_DatePicker);
        this.kdtEntrys.getColumn("endPlanDate").setEditor(kdtEntrys_endPlanDate_CellEditor);
        KDDatePicker kdtEntrys_csendDate_DatePicker = new KDDatePicker();
        kdtEntrys_csendDate_DatePicker.setName("kdtEntrys_csendDate_DatePicker");
        kdtEntrys_csendDate_DatePicker.setVisible(true);
        kdtEntrys_csendDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_csendDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_csendDate_DatePicker);
        this.kdtEntrys.getColumn("csendDate").setEditor(kdtEntrys_csendDate_CellEditor);
        KDDatePicker kdtEntrys_csendRealDate_DatePicker = new KDDatePicker();
        kdtEntrys_csendRealDate_DatePicker.setName("kdtEntrys_csendRealDate_DatePicker");
        kdtEntrys_csendRealDate_DatePicker.setVisible(true);
        kdtEntrys_csendRealDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_csendRealDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_csendRealDate_DatePicker);
        this.kdtEntrys.getColumn("csendRealDate").setEditor(kdtEntrys_csendRealDate_CellEditor);
        KDCheckBox kdtEntrys_csendOverdue_CheckBox = new KDCheckBox();
        kdtEntrys_csendOverdue_CheckBox.setName("kdtEntrys_csendOverdue_CheckBox");
        KDTDefaultCellEditor kdtEntrys_csendOverdue_CellEditor = new KDTDefaultCellEditor(kdtEntrys_csendOverdue_CheckBox);
        this.kdtEntrys.getColumn("csendOverdue").setEditor(kdtEntrys_csendOverdue_CellEditor);
        KDDatePicker kdtEntrys_csendPlanDate_DatePicker = new KDDatePicker();
        kdtEntrys_csendPlanDate_DatePicker.setName("kdtEntrys_csendPlanDate_DatePicker");
        kdtEntrys_csendPlanDate_DatePicker.setVisible(true);
        kdtEntrys_csendPlanDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_csendPlanDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_csendPlanDate_DatePicker);
        this.kdtEntrys.getColumn("csendPlanDate").setEditor(kdtEntrys_csendPlanDate_CellEditor);
        // contcurProject		
        this.contcurProject.setBoundLabelText(resHelper.getString("contcurProject.boundLabelText"));		
        this.contcurProject.setBoundLabelLength(100);		
        this.contcurProject.setBoundLabelUnderline(true);		
        this.contcurProject.setVisible(true);
        // contversion		
        this.contversion.setBoundLabelText(resHelper.getString("contversion.boundLabelText"));		
        this.contversion.setBoundLabelLength(100);		
        this.contversion.setBoundLabelUnderline(true);		
        this.contversion.setVisible(true);
        // chkisNew		
        this.chkisNew.setText(resHelper.getString("chkisNew.text"));		
        this.chkisNew.setVisible(true);		
        this.chkisNew.setHorizontalAlignment(2);		
        this.chkisNew.setEnabled(false);
        // conttrackBillStatus		
        this.conttrackBillStatus.setBoundLabelText(resHelper.getString("conttrackBillStatus.boundLabelText"));		
        this.conttrackBillStatus.setBoundLabelLength(100);		
        this.conttrackBillStatus.setBoundLabelUnderline(true);		
        this.conttrackBillStatus.setVisible(true);
        // contauditTime		
        this.contauditTime.setBoundLabelText(resHelper.getString("contauditTime.boundLabelText"));		
        this.contauditTime.setBoundLabelLength(100);		
        this.contauditTime.setBoundLabelUnderline(true);		
        this.contauditTime.setVisible(true);		
        this.contauditTime.setEnabled(false);
        // btnGrabData		
        this.btnGrabData.setText(resHelper.getString("btnGrabData.text"));
        this.btnGrabData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnGrabData_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
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
        this.prmtcurProject.setEnabled(false);
        // txtversion		
        this.txtversion.setVisible(true);		
        this.txtversion.setHorizontalAlignment(2);		
        this.txtversion.setDataType(0);		
        this.txtversion.setSupportedEmpty(true);		
        this.txtversion.setRequired(false);		
        this.txtversion.setEnabled(false);
        // trackBillStatus		
        this.trackBillStatus.setVisible(true);		
        this.trackBillStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.trackBillStatus.setRequired(false);		
        this.trackBillStatus.setEnabled(false);
        // pkauditTime		
        this.pkauditTime.setVisible(true);		
        this.pkauditTime.setRequired(false);		
        this.pkauditTime.setEnabled(false);
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnaudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnFix
        this.btnFix.setAction((IItemAction)ActionProxyFactory.getProxy(actionFix, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFix.setText(resHelper.getString("btnFix.text"));		
        this.btnFix.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_emend"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtcurProject,txtversion,chkisNew,trackBillStatus,pkauditTime}));
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
        contCreator.setBounds(new Rectangle(372, 578, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(372, 578, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(372, 602, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(372, 602, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(736, 577, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(736, 577, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateTime.setBounds(new Rectangle(736, 602, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(736, 602, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(9, 9, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(9, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(372, 554, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(372, 554, 270, 19, 0));
        contDescription.setBounds(new Rectangle(737, 552, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(737, 552, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(9, 578, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(9, 578, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtEntrys.setBounds(new Rectangle(9, 60, 997, 469));
        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.fdc.contract.PcontractTrackBillEntryInfo(),null,false);
        this.add(kdtEntrys_detailPanel, new KDLayout.Constraints(9, 60, 997, 469, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcurProject.setBounds(new Rectangle(9, 34, 270, 19));
        this.add(contcurProject, new KDLayout.Constraints(9, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contversion.setBounds(new Rectangle(372, 9, 270, 19));
        this.add(contversion, new KDLayout.Constraints(372, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkisNew.setBounds(new Rectangle(372, 34, 270, 19));
        this.add(chkisNew, new KDLayout.Constraints(372, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttrackBillStatus.setBounds(new Rectangle(735, 9, 270, 19));
        this.add(conttrackBillStatus, new KDLayout.Constraints(735, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contauditTime.setBounds(new Rectangle(9, 602, 270, 19));
        this.add(contauditTime, new KDLayout.Constraints(9, 602, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnGrabData.setBounds(new Rectangle(735, 34, 91, 21));
        this.add(btnGrabData, new KDLayout.Constraints(735, 34, 91, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
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
        //conttrackBillStatus
        conttrackBillStatus.setBoundEditor(trackBillStatus);
        //contauditTime
        contauditTime.setBoundEditor(pkauditTime);

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
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnFix);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.contract.PcontractTrackBillEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.pcid", String.class, this.kdtEntrys, "pcid.text");
		dataBinder.registerBinding("entrys.level", int.class, this.kdtEntrys, "level.text");
		dataBinder.registerBinding("entrys.longNumber", String.class, this.kdtEntrys, "longNumber.text");
		dataBinder.registerBinding("entrys.headNumber", String.class, this.kdtEntrys, "headNumber.text");
		dataBinder.registerBinding("entrys.name", String.class, this.kdtEntrys, "name.text");
		dataBinder.registerBinding("entrys.hyType", String.class, this.kdtEntrys, "hyType.text");
		dataBinder.registerBinding("entrys.planAmount", java.math.BigDecimal.class, this.kdtEntrys, "planAmount.text");
		dataBinder.registerBinding("entrys.changeRate", java.math.BigDecimal.class, this.kdtEntrys, "changeRate.text");
		dataBinder.registerBinding("entrys.contralAmount", java.math.BigDecimal.class, this.kdtEntrys, "contralAmount.text");
		dataBinder.registerBinding("entrys.sgtDate", java.util.Date.class, this.kdtEntrys, "sgtDate.text");
		dataBinder.registerBinding("entrys.sgtRealDate", java.util.Date.class, this.kdtEntrys, "sgtRealDate.text");
		dataBinder.registerBinding("entrys.sgtOverdue", boolean.class, this.kdtEntrys, "sgtOverdue.text");
		dataBinder.registerBinding("entrys.sgtPlanDate", java.util.Date.class, this.kdtEntrys, "sgtPlanDate.text");
		dataBinder.registerBinding("entrys.csDate", java.util.Date.class, this.kdtEntrys, "csDate.text");
		dataBinder.registerBinding("entrys.csRealDate", java.util.Date.class, this.kdtEntrys, "csRealDate.text");
		dataBinder.registerBinding("entrys.csOverdue", boolean.class, this.kdtEntrys, "csOverdue.text");
		dataBinder.registerBinding("entrys.csPlanDate", java.util.Date.class, this.kdtEntrys, "csPlanDate.text");
		dataBinder.registerBinding("entrys.startDate", java.util.Date.class, this.kdtEntrys, "startDate.text");
		dataBinder.registerBinding("entrys.startRealDate", java.util.Date.class, this.kdtEntrys, "startRealDate.text");
		dataBinder.registerBinding("entrys.startOverdue", boolean.class, this.kdtEntrys, "startOverdue.text");
		dataBinder.registerBinding("entrys.startPlanDate", java.util.Date.class, this.kdtEntrys, "startPlanDate.text");
		dataBinder.registerBinding("entrys.endDate", java.util.Date.class, this.kdtEntrys, "endDate.text");
		dataBinder.registerBinding("entrys.endRealDate", java.util.Date.class, this.kdtEntrys, "endRealDate.text");
		dataBinder.registerBinding("entrys.endOverdue", boolean.class, this.kdtEntrys, "endOverdue.text");
		dataBinder.registerBinding("entrys.endPlanDate", java.util.Date.class, this.kdtEntrys, "endPlanDate.text");
		dataBinder.registerBinding("entrys.csendDate", java.util.Date.class, this.kdtEntrys, "csendDate.text");
		dataBinder.registerBinding("entrys.csendRealDate", java.util.Date.class, this.kdtEntrys, "csendRealDate.text");
		dataBinder.registerBinding("entrys.csendOverdue", boolean.class, this.kdtEntrys, "csendOverdue.text");
		dataBinder.registerBinding("entrys.csendPlanDate", java.util.Date.class, this.kdtEntrys, "csendPlanDate.text");
		dataBinder.registerBinding("isNew", boolean.class, this.chkisNew, "selected");
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
		dataBinder.registerBinding("trackBillStatus", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.trackBillStatus, "selectedItem");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkauditTime, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.PcontractTrackBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.PcontractTrackBillInfo)ov;
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
		getValidateHelper().registerBindProperty("entrys.pcid", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.headNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.hyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.planAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.changeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.contralAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.sgtDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.sgtRealDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.sgtOverdue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.sgtPlanDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.csDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.csRealDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.csOverdue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.csPlanDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.startRealDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.startOverdue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.startPlanDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.endRealDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.endOverdue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.endPlanDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.csendDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.csendRealDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.csendOverdue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.csendPlanDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isNew", ValidateHelper.ON_SAVE);    
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
		getValidateHelper().registerBindProperty("trackBillStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    		
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
     * output btnGrabData_actionPerformed method
     */
    protected void btnGrabData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code hereaa
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
			sic.add(new SelectorItemInfo("entrys.name"));
		}
    	sic.add(new SelectorItemInfo("entrys.pcid"));
    	sic.add(new SelectorItemInfo("entrys.level"));
    	sic.add(new SelectorItemInfo("entrys.longNumber"));
    	sic.add(new SelectorItemInfo("entrys.headNumber"));
    	sic.add(new SelectorItemInfo("entrys.hyType"));
    	sic.add(new SelectorItemInfo("entrys.planAmount"));
    	sic.add(new SelectorItemInfo("entrys.changeRate"));
    	sic.add(new SelectorItemInfo("entrys.contralAmount"));
    	sic.add(new SelectorItemInfo("entrys.sgtDate"));
    	sic.add(new SelectorItemInfo("entrys.sgtRealDate"));
    	sic.add(new SelectorItemInfo("entrys.sgtOverdue"));
    	sic.add(new SelectorItemInfo("entrys.sgtPlanDate"));
    	sic.add(new SelectorItemInfo("entrys.csDate"));
    	sic.add(new SelectorItemInfo("entrys.csRealDate"));
    	sic.add(new SelectorItemInfo("entrys.csOverdue"));
    	sic.add(new SelectorItemInfo("entrys.csPlanDate"));
    	sic.add(new SelectorItemInfo("entrys.startDate"));
    	sic.add(new SelectorItemInfo("entrys.startRealDate"));
    	sic.add(new SelectorItemInfo("entrys.startOverdue"));
    	sic.add(new SelectorItemInfo("entrys.startPlanDate"));
    	sic.add(new SelectorItemInfo("entrys.endDate"));
    	sic.add(new SelectorItemInfo("entrys.endRealDate"));
    	sic.add(new SelectorItemInfo("entrys.endOverdue"));
    	sic.add(new SelectorItemInfo("entrys.endPlanDate"));
    	sic.add(new SelectorItemInfo("entrys.csendDate"));
    	sic.add(new SelectorItemInfo("entrys.csendRealDate"));
    	sic.add(new SelectorItemInfo("entrys.csendOverdue"));
    	sic.add(new SelectorItemInfo("entrys.csendPlanDate"));
        sic.add(new SelectorItemInfo("isNew"));
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
        sic.add(new SelectorItemInfo("trackBillStatus"));
        sic.add(new SelectorItemInfo("auditTime"));
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
        com.kingdee.eas.fdc.contract.PcontractTrackBillFactory.getRemoteInstance().audit(editData);
    }
    	

    /**
     * output actionUnaudit_actionPerformed method
     */
    public void actionUnaudit_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.contract.PcontractTrackBillFactory.getRemoteInstance().unaudit(editData);
    }
    	

    /**
     * output actionFix_actionPerformed method
     */
    public void actionFix_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.contract.PcontractTrackBillFactory.getRemoteInstance().fix(editData);
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
	public RequestContext prepareActionUnaudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnaudit() {
    	return false;
    }
	public RequestContext prepareActionFix(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFix() {
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
            innerActionPerformed("eas", AbstractPcontractTrackBillEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnaudit class
     */     
    protected class ActionUnaudit extends ItemAction {     
    
        public ActionUnaudit()
        {
            this(null);
        }

        public ActionUnaudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnaudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnaudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnaudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPcontractTrackBillEditUI.this, "ActionUnaudit", "actionUnaudit_actionPerformed", e);
        }
    }

    /**
     * output ActionFix class
     */     
    protected class ActionFix extends ItemAction {     
    
        public ActionFix()
        {
            this(null);
        }

        public ActionFix(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionFix.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFix.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFix.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPcontractTrackBillEditUI.this, "ActionFix", "actionFix_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "PcontractTrackBillEditUI");
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
        return com.kingdee.eas.fdc.contract.client.PcontractTrackBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.PcontractTrackBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.PcontractTrackBillInfo objectValue = new com.kingdee.eas.fdc.contract.PcontractTrackBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/contract/PcontractTrackBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.PcontractTrackBillQuery");
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
		vo.put("trackBillStatus","1SAVED");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}