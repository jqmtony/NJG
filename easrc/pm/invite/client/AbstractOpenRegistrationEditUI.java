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
public abstract class AbstractOpenRegistrationEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractOpenRegistrationEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreportName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contopLocation;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contopDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contendDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreportNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contregName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcoefficient;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkcancel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtreportName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtopLocation;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkopDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkendDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtreportNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtregName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcoefficient;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDoCancel;
    protected com.kingdee.eas.port.pm.invite.OpenRegistrationInfo editData = null;
    protected ActionDoCancel actionDoCancel = null;
    /**
     * output class constructor
     */
    public AbstractOpenRegistrationEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractOpenRegistrationEditUI.class.getName());
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
        //actionDoCancel
        this.actionDoCancel = new ActionDoCancel(this);
        getActionManager().registerAction("actionDoCancel", actionDoCancel);
        this.actionDoCancel.setExtendProperty("canForewarn", "true");
        this.actionDoCancel.setExtendProperty("userDefined", "true");
        this.actionDoCancel.setExtendProperty("isObjectUpdateLock", "false");
         this.actionDoCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionDoCancel.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contreportName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contopLocation = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contopDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contendDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreportNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contregName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcoefficient = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkcancel = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtreportName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtopLocation = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkopDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkendDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtreportNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtregName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcoefficient = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnDoCancel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contCU.setName("contCU");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contBizStatus.setName("contBizStatus");
        this.kDPanel1.setName("kDPanel1");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.prmtCU.setName("prmtCU");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.comboBizStatus.setName("comboBizStatus");
        this.contreportName.setName("contreportName");
        this.contopLocation.setName("contopLocation");
        this.contopDate.setName("contopDate");
        this.contendDate.setName("contendDate");
        this.contNumber.setName("contNumber");
        this.contStatus.setName("contStatus");
        this.kDContainer1.setName("kDContainer1");
        this.contCreator.setName("contCreator");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.contCreateTime.setName("contCreateTime");
        this.contreportNumber.setName("contreportNumber");
        this.contregName.setName("contregName");
        this.contcoefficient.setName("contcoefficient");
        this.chkcancel.setName("chkcancel");
        this.prmtreportName.setName("prmtreportName");
        this.txtopLocation.setName("txtopLocation");
        this.pkopDate.setName("pkopDate");
        this.pkendDate.setName("pkendDate");
        this.txtNumber.setName("txtNumber");
        this.comboStatus.setName("comboStatus");
        this.kdtEntry.setName("kdtEntry");
        this.prmtCreator.setName("prmtCreator");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtreportNumber.setName("txtreportNumber");
        this.txtregName.setName("txtregName");
        this.txtcoefficient.setName("txtcoefficient");
        this.btnDoCancel.setName("btnDoCancel");
        // CoreUI		
        this.setPreferredSize(new Dimension(953,439));
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
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contBizStatus		
        this.contBizStatus.setBoundLabelText(resHelper.getString("contBizStatus.boundLabelText"));		
        this.contBizStatus.setBoundLabelLength(100);		
        this.contBizStatus.setBoundLabelUnderline(true);		
        this.contBizStatus.setEnabled(false);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
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
        // pkBizDate
        // txtDescription
        // comboBizStatus		
        this.comboBizStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBizActionEnum").toArray());		
        this.comboBizStatus.setEnabled(false);		
        this.comboBizStatus.setVisible(false);
        // contreportName		
        this.contreportName.setBoundLabelText(resHelper.getString("contreportName.boundLabelText"));		
        this.contreportName.setBoundLabelLength(100);		
        this.contreportName.setBoundLabelUnderline(true);		
        this.contreportName.setVisible(true);
        // contopLocation		
        this.contopLocation.setBoundLabelText(resHelper.getString("contopLocation.boundLabelText"));		
        this.contopLocation.setBoundLabelLength(100);		
        this.contopLocation.setBoundLabelUnderline(true);		
        this.contopLocation.setVisible(true);
        // contopDate		
        this.contopDate.setBoundLabelText(resHelper.getString("contopDate.boundLabelText"));		
        this.contopDate.setBoundLabelLength(100);		
        this.contopDate.setBoundLabelUnderline(true);		
        this.contopDate.setVisible(true);
        // contendDate		
        this.contendDate.setBoundLabelText(resHelper.getString("contendDate.boundLabelText"));		
        this.contendDate.setBoundLabelLength(100);		
        this.contendDate.setBoundLabelUnderline(true);		
        this.contendDate.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contreportNumber		
        this.contreportNumber.setBoundLabelText(resHelper.getString("contreportNumber.boundLabelText"));		
        this.contreportNumber.setBoundLabelLength(100);		
        this.contreportNumber.setBoundLabelUnderline(true);		
        this.contreportNumber.setVisible(true);
        // contregName		
        this.contregName.setBoundLabelText(resHelper.getString("contregName.boundLabelText"));		
        this.contregName.setBoundLabelLength(100);		
        this.contregName.setBoundLabelUnderline(true);		
        this.contregName.setVisible(true);
        // contcoefficient		
        this.contcoefficient.setBoundLabelText(resHelper.getString("contcoefficient.boundLabelText"));		
        this.contcoefficient.setBoundLabelLength(100);		
        this.contcoefficient.setBoundLabelUnderline(true);		
        this.contcoefficient.setVisible(false);
        // chkcancel		
        this.chkcancel.setText(resHelper.getString("chkcancel.text"));		
        this.chkcancel.setHorizontalAlignment(2);
        // prmtreportName		
        this.prmtreportName.setQueryInfo("com.kingdee.eas.port.pm.invite.app.InviteReportQuery");		
        this.prmtreportName.setEditable(true);		
        this.prmtreportName.setDisplayFormat("$projectNumber$");		
        this.prmtreportName.setEditFormat("$number$");		
        this.prmtreportName.setCommitFormat("$number$");		
        this.prmtreportName.setRequired(false);
        prmtreportName.addDataChangeListener(new DataChangeListener() {
		public void dataChanged(DataChangeEvent e) {
			try {
				prmtreportName_Changed();
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        this.prmtreportName.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtreportName_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtopLocation		
        this.txtopLocation.setHorizontalAlignment(2);		
        this.txtopLocation.setMaxLength(100);		
        this.txtopLocation.setRequired(false);
        // pkopDate		
        this.pkopDate.setRequired(false);
        // pkendDate		
        this.pkendDate.setRequired(false);
        // txtNumber
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"supplierName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contact\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"telephone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"payDeposit\" t:width=\"115\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isPresent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isQualified\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"deposit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"quotedPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"prjPeriod\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"quality\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"directions\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{supplierName}</t:Cell><t:Cell>$Resource{contact}</t:Cell><t:Cell>$Resource{telephone}</t:Cell><t:Cell>$Resource{payDeposit}</t:Cell><t:Cell>$Resource{isPresent}</t:Cell><t:Cell>$Resource{isQualified}</t:Cell><t:Cell>$Resource{deposit}</t:Cell><t:Cell>$Resource{quotedPrice}</t:Cell><t:Cell>$Resource{prjPeriod}</t:Cell><t:Cell>$Resource{quality}</t:Cell><t:Cell>$Resource{directions}</t:Cell><t:Cell>$Resource{comment}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

                this.kdtEntry.putBindContents("editData",new String[] {"seq","supplierName","contact","telephone","payDeposit","isPresent","isQualified","deposit","quotedPrice","prjPeriod","quality","directions","comment"});


        this.kdtEntry.checkParsed();
        final KDBizPromptBox kdtEntry_supplierName_PromptBox = new KDBizPromptBox();
        kdtEntry_supplierName_PromptBox.setQueryInfo("com.kingdee.eas.port.markesupplier.subill.app.MarketSupplierStockQuery");
        kdtEntry_supplierName_PromptBox.setVisible(true);
        kdtEntry_supplierName_PromptBox.setEditable(true);
        kdtEntry_supplierName_PromptBox.setDisplayFormat("$number$");
        kdtEntry_supplierName_PromptBox.setEditFormat("$number$");
        kdtEntry_supplierName_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_supplierName_CellEditor = new KDTDefaultCellEditor(kdtEntry_supplierName_PromptBox);
        this.kdtEntry.getColumn("supplierName").setEditor(kdtEntry_supplierName_CellEditor);
        ObjectValueRender kdtEntry_supplierName_OVR = new ObjectValueRender();
        kdtEntry_supplierName_OVR.setFormat(new BizDataFormat("$supplierName$"));
        this.kdtEntry.getColumn("supplierName").setRenderer(kdtEntry_supplierName_OVR);
        KDTextField kdtEntry_contact_TextField = new KDTextField();
        kdtEntry_contact_TextField.setName("kdtEntry_contact_TextField");
        kdtEntry_contact_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_contact_CellEditor = new KDTDefaultCellEditor(kdtEntry_contact_TextField);
        this.kdtEntry.getColumn("contact").setEditor(kdtEntry_contact_CellEditor);
        KDTextField kdtEntry_telephone_TextField = new KDTextField();
        kdtEntry_telephone_TextField.setName("kdtEntry_telephone_TextField");
        kdtEntry_telephone_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_telephone_CellEditor = new KDTDefaultCellEditor(kdtEntry_telephone_TextField);
        this.kdtEntry.getColumn("telephone").setEditor(kdtEntry_telephone_CellEditor);
        KDCheckBox kdtEntry_payDeposit_CheckBox = new KDCheckBox();
        kdtEntry_payDeposit_CheckBox.setName("kdtEntry_payDeposit_CheckBox");
        KDTDefaultCellEditor kdtEntry_payDeposit_CellEditor = new KDTDefaultCellEditor(kdtEntry_payDeposit_CheckBox);
        this.kdtEntry.getColumn("payDeposit").setEditor(kdtEntry_payDeposit_CellEditor);
        KDCheckBox kdtEntry_isPresent_CheckBox = new KDCheckBox();
        kdtEntry_isPresent_CheckBox.setName("kdtEntry_isPresent_CheckBox");
        KDTDefaultCellEditor kdtEntry_isPresent_CellEditor = new KDTDefaultCellEditor(kdtEntry_isPresent_CheckBox);
        this.kdtEntry.getColumn("isPresent").setEditor(kdtEntry_isPresent_CellEditor);
        KDCheckBox kdtEntry_isQualified_CheckBox = new KDCheckBox();
        kdtEntry_isQualified_CheckBox.setName("kdtEntry_isQualified_CheckBox");
        KDTDefaultCellEditor kdtEntry_isQualified_CellEditor = new KDTDefaultCellEditor(kdtEntry_isQualified_CheckBox);
        this.kdtEntry.getColumn("isQualified").setEditor(kdtEntry_isQualified_CellEditor);
        KDTextField kdtEntry_deposit_TextField = new KDTextField();
        kdtEntry_deposit_TextField.setName("kdtEntry_deposit_TextField");
        kdtEntry_deposit_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_deposit_CellEditor = new KDTDefaultCellEditor(kdtEntry_deposit_TextField);
        this.kdtEntry.getColumn("deposit").setEditor(kdtEntry_deposit_CellEditor);
        KDTextField kdtEntry_quotedPrice_TextField = new KDTextField();
        kdtEntry_quotedPrice_TextField.setName("kdtEntry_quotedPrice_TextField");
        kdtEntry_quotedPrice_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_quotedPrice_CellEditor = new KDTDefaultCellEditor(kdtEntry_quotedPrice_TextField);
        this.kdtEntry.getColumn("quotedPrice").setEditor(kdtEntry_quotedPrice_CellEditor);
        KDTextField kdtEntry_prjPeriod_TextField = new KDTextField();
        kdtEntry_prjPeriod_TextField.setName("kdtEntry_prjPeriod_TextField");
        kdtEntry_prjPeriod_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_prjPeriod_CellEditor = new KDTDefaultCellEditor(kdtEntry_prjPeriod_TextField);
        this.kdtEntry.getColumn("prjPeriod").setEditor(kdtEntry_prjPeriod_CellEditor);
        KDTextField kdtEntry_quality_TextField = new KDTextField();
        kdtEntry_quality_TextField.setName("kdtEntry_quality_TextField");
        kdtEntry_quality_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_quality_CellEditor = new KDTDefaultCellEditor(kdtEntry_quality_TextField);
        this.kdtEntry.getColumn("quality").setEditor(kdtEntry_quality_CellEditor);
        KDTextField kdtEntry_directions_TextField = new KDTextField();
        kdtEntry_directions_TextField.setName("kdtEntry_directions_TextField");
        kdtEntry_directions_TextField.setMaxLength(150);
        KDTDefaultCellEditor kdtEntry_directions_CellEditor = new KDTDefaultCellEditor(kdtEntry_directions_TextField);
        this.kdtEntry.getColumn("directions").setEditor(kdtEntry_directions_CellEditor);
        KDTextField kdtEntry_comment_TextField = new KDTextField();
        kdtEntry_comment_TextField.setName("kdtEntry_comment_TextField");
        kdtEntry_comment_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_comment_CellEditor = new KDTDefaultCellEditor(kdtEntry_comment_TextField);
        this.kdtEntry.getColumn("comment").setEditor(kdtEntry_comment_CellEditor);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setCommitFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");		
        this.prmtCreator.setDisplayFormat("$name$");
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setTimeEnabled(true);		
        this.pkCreateTime.setEnabled(false);
        // txtreportNumber		
        this.txtreportNumber.setHorizontalAlignment(2);		
        this.txtreportNumber.setMaxLength(80);		
        this.txtreportNumber.setRequired(false);
        // txtregName		
        this.txtregName.setHorizontalAlignment(2);		
        this.txtregName.setMaxLength(100);		
        this.txtregName.setRequired(false);
        // txtcoefficient		
        this.txtcoefficient.setHorizontalAlignment(2);		
        this.txtcoefficient.setMaxLength(100);		
        this.txtcoefficient.setRequired(false);
        // btnDoCancel
        this.btnDoCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionDoCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDoCancel.setText(resHelper.getString("btnDoCancel.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {comboStatus,comboBizStatus,pkAuditTime,txtNumber,pkBizDate,txtDescription,prmtAuditor,prmtCreator,pkCreateTime,prmtLastUpdateUser,pkLastUpdateTime,prmtCU,txtopLocation,pkopDate,prmtreportName,pkendDate,txtreportNumber,txtregName,txtcoefficient,chkcancel,kdtEntry}));
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
        this.setBounds(new Rectangle(10, 10, 953, 439));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 953, 439));
        contLastUpdateUser.setBounds(new Rectangle(316, 508, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(316, 508, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(316, 534, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(316, 534, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(30, 558, 270, 19));
        this.add(contCU, new KDLayout.Constraints(30, 558, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(33, 506, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(33, 506, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(33, 532, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(33, 532, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizStatus.setBounds(new Rectangle(315, 561, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(315, 561, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(2, 2, 953, 439));
        this.add(kDPanel1, new KDLayout.Constraints(2, 2, 953, 439, 0));
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contCU
        contCU.setBoundEditor(prmtCU);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contBizStatus
        contBizStatus.setBoundEditor(comboBizStatus);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(2, 2, 953, 439));        contreportName.setBounds(new Rectangle(338, 47, 270, 19));
        kDPanel1.add(contreportName, new KDLayout.Constraints(338, 47, 270, 19, 0));
        contopLocation.setBounds(new Rectangle(670, 22, 270, 19));
        kDPanel1.add(contopLocation, new KDLayout.Constraints(670, 22, 270, 19, 0));
        contopDate.setBounds(new Rectangle(13, 41, 270, 19));
        kDPanel1.add(contopDate, new KDLayout.Constraints(13, 41, 270, 19, 0));
        contendDate.setBounds(new Rectangle(589, 98, 270, 19));
        kDPanel1.add(contendDate, new KDLayout.Constraints(589, 98, 270, 19, 0));
        contNumber.setBounds(new Rectangle(13, 16, 270, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(13, 16, 270, 19, 0));
        contStatus.setBounds(new Rectangle(13, 67, 270, 19));
        kDPanel1.add(contStatus, new KDLayout.Constraints(13, 67, 270, 19, 0));
        kDContainer1.setBounds(new Rectangle(13, 89, 927, 286));
        kDPanel1.add(kDContainer1, new KDLayout.Constraints(13, 89, 927, 286, 0));
        contCreator.setBounds(new Rectangle(13, 380, 270, 19));
        kDPanel1.add(contCreator, new KDLayout.Constraints(13, 380, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(338, 380, 270, 19));
        kDPanel1.add(contAuditor, new KDLayout.Constraints(338, 380, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(338, 406, 270, 19));
        kDPanel1.add(contAuditTime, new KDLayout.Constraints(338, 406, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(13, 406, 270, 19));
        kDPanel1.add(contCreateTime, new KDLayout.Constraints(13, 406, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contreportNumber.setBounds(new Rectangle(670, 47, 270, 19));
        kDPanel1.add(contreportNumber, new KDLayout.Constraints(670, 47, 270, 19, 0));
        contregName.setBounds(new Rectangle(338, 22, 270, 19));
        kDPanel1.add(contregName, new KDLayout.Constraints(338, 22, 270, 19, 0));
        contcoefficient.setBounds(new Rectangle(348, 90, 270, 19));
        kDPanel1.add(contcoefficient, new KDLayout.Constraints(348, 90, 270, 19, 0));
        chkcancel.setBounds(new Rectangle(338, 67, 270, 19));
        kDPanel1.add(chkcancel, new KDLayout.Constraints(338, 67, 270, 19, 0));
        //contreportName
        contreportName.setBoundEditor(prmtreportName);
        //contopLocation
        contopLocation.setBoundEditor(txtopLocation);
        //contopDate
        contopDate.setBoundEditor(pkopDate);
        //contendDate
        contendDate.setBoundEditor(pkendDate);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry,new com.kingdee.eas.port.pm.invite.OpenRegistrationEntryInfo(),null,false);
        kDContainer1.getContentPane().add(kdtEntry_detailPanel, BorderLayout.CENTER);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contreportNumber
        contreportNumber.setBoundEditor(txtreportNumber);
        //contregName
        contregName.setBoundEditor(txtregName);
        //contcoefficient
        contcoefficient.setBoundEditor(txtcoefficient);

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
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnDoCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("cancel", boolean.class, this.chkcancel, "selected");
		dataBinder.registerBinding("reportName", com.kingdee.eas.port.pm.invite.InviteReportInfo.class, this.prmtreportName, "data");
		dataBinder.registerBinding("opLocation", String.class, this.txtopLocation, "text");
		dataBinder.registerBinding("opDate", java.util.Date.class, this.pkopDate, "value");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.pkendDate, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("Entry.seq", int.class, this.kdtEntry, "seq.text");
		dataBinder.registerBinding("Entry", com.kingdee.eas.port.pm.invite.OpenRegistrationEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("Entry.quality", String.class, this.kdtEntry, "quality.text");
		dataBinder.registerBinding("Entry.payDeposit", boolean.class, this.kdtEntry, "payDeposit.text");
		dataBinder.registerBinding("Entry.deposit", String.class, this.kdtEntry, "deposit.text");
		dataBinder.registerBinding("Entry.quotedPrice", String.class, this.kdtEntry, "quotedPrice.text");
		dataBinder.registerBinding("Entry.directions", String.class, this.kdtEntry, "directions.text");
		dataBinder.registerBinding("Entry.comment", String.class, this.kdtEntry, "comment.text");
		dataBinder.registerBinding("Entry.contact", String.class, this.kdtEntry, "contact.text");
		dataBinder.registerBinding("Entry.telephone", String.class, this.kdtEntry, "telephone.text");
		dataBinder.registerBinding("Entry.prjPeriod", String.class, this.kdtEntry, "prjPeriod.text");
		dataBinder.registerBinding("Entry.supplierName", java.lang.Object.class, this.kdtEntry, "supplierName.text");
		dataBinder.registerBinding("Entry.isPresent", boolean.class, this.kdtEntry, "isPresent.text");
		dataBinder.registerBinding("Entry.isQualified", boolean.class, this.kdtEntry, "isQualified.text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("reportNumber", String.class, this.txtreportNumber, "text");
		dataBinder.registerBinding("regName", String.class, this.txtregName, "text");
		dataBinder.registerBinding("coefficient", String.class, this.txtcoefficient, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invite.app.OpenRegistrationEditUIHandler";
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
        this.comboStatus.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.pm.invite.OpenRegistrationInfo)ov;
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
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cancel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reportName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("opLocation", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("opDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.quality", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.payDeposit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.deposit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.quotedPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.directions", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.comment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.contact", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.telephone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.prjPeriod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.supplierName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.isPresent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.isQualified", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reportNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("regName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("coefficient", ValidateHelper.ON_SAVE);    		
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
     * output prmtreportName_dataChanged method
     */
    protected void prmtreportName_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output prmtreportName_Changed() method
     */
    public void prmtreportName_Changed() throws Exception
    {
        System.out.println("prmtreportName_Changed() Function is executed!");
            txtreportNumber.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtreportName.getData(),"number")));


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
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("bizStatus"));
        sic.add(new SelectorItemInfo("cancel"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("reportName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("reportName.id"));
        	sic.add(new SelectorItemInfo("reportName.number"));
        	sic.add(new SelectorItemInfo("reportName.projectNumber"));
		}
        sic.add(new SelectorItemInfo("opLocation"));
        sic.add(new SelectorItemInfo("opDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("status"));
    	sic.add(new SelectorItemInfo("Entry.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("Entry.quality"));
    	sic.add(new SelectorItemInfo("Entry.payDeposit"));
    	sic.add(new SelectorItemInfo("Entry.deposit"));
    	sic.add(new SelectorItemInfo("Entry.quotedPrice"));
    	sic.add(new SelectorItemInfo("Entry.directions"));
    	sic.add(new SelectorItemInfo("Entry.comment"));
    	sic.add(new SelectorItemInfo("Entry.contact"));
    	sic.add(new SelectorItemInfo("Entry.telephone"));
    	sic.add(new SelectorItemInfo("Entry.prjPeriod"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.supplierName.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Entry.supplierName.id"));
			sic.add(new SelectorItemInfo("Entry.supplierName.supplierName"));
        	sic.add(new SelectorItemInfo("Entry.supplierName.number"));
		}
    	sic.add(new SelectorItemInfo("Entry.isPresent"));
    	sic.add(new SelectorItemInfo("Entry.isQualified"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
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
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("reportNumber"));
        sic.add(new SelectorItemInfo("regName"));
        sic.add(new SelectorItemInfo("coefficient"));
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
     * output actionDoCancel_actionPerformed method
     */
    public void actionDoCancel_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.pm.invite.OpenRegistrationFactory.getRemoteInstance().doCancel(editData);
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
	public RequestContext prepareActionDoCancel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDoCancel() {
    	return false;
    }

    /**
     * output ActionDoCancel class
     */     
    protected class ActionDoCancel extends ItemAction {     
    
        public ActionDoCancel()
        {
            this(null);
        }

        public ActionDoCancel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDoCancel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDoCancel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDoCancel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractOpenRegistrationEditUI.this, "ActionDoCancel", "actionDoCancel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.pm.invite.client", "OpenRegistrationEditUI");
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
        return com.kingdee.eas.port.pm.invite.client.OpenRegistrationEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invite.OpenRegistrationFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invite.OpenRegistrationInfo objectValue = new com.kingdee.eas.port.pm.invite.OpenRegistrationInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/invite/OpenRegistration";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.invite.app.OpenRegistrationQuery");
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
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}