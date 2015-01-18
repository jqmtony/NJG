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
public abstract class AbstractProjectStartRequestEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectStartRequestEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contperson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdeparment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyear;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkwuxu;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contscheme;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanescheme;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtscheme;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE2;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE2_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0022;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdec;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneBIMUDF0022;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtBIMUDF0022;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanedec;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtdec;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtperson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtdeparment;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprojectName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprojectType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtamount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtyear;
    protected com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractProjectStartRequestEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectStartRequestEditUI.class.getName());
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
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contperson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdeparment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkwuxu = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contscheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanescheme = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtscheme = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtE2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contBIMUDF0022 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdec = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPaneBIMUDF0022 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtBIMUDF0022 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanedec = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtdec = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtperson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtdeparment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtprojectName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtprojectType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtamount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtyear = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contBizStatus.setName("contBizStatus");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contStatus.setName("contStatus");
        this.contperson.setName("contperson");
        this.contdeparment.setName("contdeparment");
        this.contCU.setName("contCU");
        this.contprojectName.setName("contprojectName");
        this.contprojectType.setName("contprojectType");
        this.contamount.setName("contamount");
        this.contyear.setName("contyear");
        this.chkwuxu.setName("chkwuxu");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel4.setName("kDPanel4");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.contDescription.setName("contDescription");
        this.contscheme.setName("contscheme");
        this.txtDescription.setName("txtDescription");
        this.scrollPanescheme.setName("scrollPanescheme");
        this.txtscheme.setName("txtscheme");
        this.kdtEntry.setName("kdtEntry");
        this.kdtE2.setName("kdtE2");
        this.contBIMUDF0022.setName("contBIMUDF0022");
        this.contdec.setName("contdec");
        this.scrollPaneBIMUDF0022.setName("scrollPaneBIMUDF0022");
        this.txtBIMUDF0022.setName("txtBIMUDF0022");
        this.scrollPanedec.setName("scrollPanedec");
        this.txtdec.setName("txtdec");
        this.comboBizStatus.setName("comboBizStatus");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.comboStatus.setName("comboStatus");
        this.prmtperson.setName("prmtperson");
        this.prmtdeparment.setName("prmtdeparment");
        this.prmtCU.setName("prmtCU");
        this.prmtprojectName.setName("prmtprojectName");
        this.prmtprojectType.setName("prmtprojectType");
        this.txtamount.setName("txtamount");
        this.prmtyear.setName("prmtyear");
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
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // kDTabbedPane1
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
        // contBizStatus		
        this.contBizStatus.setBoundLabelText(resHelper.getString("contBizStatus.boundLabelText"));		
        this.contBizStatus.setBoundLabelLength(100);		
        this.contBizStatus.setBoundLabelUnderline(true);		
        this.contBizStatus.setEnabled(false);		
        this.contBizStatus.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);
        // contperson		
        this.contperson.setBoundLabelText(resHelper.getString("contperson.boundLabelText"));		
        this.contperson.setBoundLabelLength(100);		
        this.contperson.setBoundLabelUnderline(true);		
        this.contperson.setVisible(true);
        // contdeparment		
        this.contdeparment.setBoundLabelText(resHelper.getString("contdeparment.boundLabelText"));		
        this.contdeparment.setBoundLabelLength(100);		
        this.contdeparment.setBoundLabelUnderline(true);		
        this.contdeparment.setVisible(true);
        // contCU		
        this.contCU.setBoundLabelText(resHelper.getString("contCU.boundLabelText"));		
        this.contCU.setBoundLabelLength(100);		
        this.contCU.setBoundLabelUnderline(true);		
        this.contCU.setEnabled(false);
        // contprojectName		
        this.contprojectName.setBoundLabelText(resHelper.getString("contprojectName.boundLabelText"));		
        this.contprojectName.setBoundLabelLength(100);		
        this.contprojectName.setBoundLabelUnderline(true);		
        this.contprojectName.setVisible(true);
        // contprojectType		
        this.contprojectType.setBoundLabelText(resHelper.getString("contprojectType.boundLabelText"));		
        this.contprojectType.setBoundLabelLength(100);		
        this.contprojectType.setBoundLabelUnderline(true);		
        this.contprojectType.setVisible(true);
        // contamount		
        this.contamount.setBoundLabelText(resHelper.getString("contamount.boundLabelText"));		
        this.contamount.setBoundLabelLength(100);		
        this.contamount.setBoundLabelUnderline(true);		
        this.contamount.setVisible(true);
        // contyear		
        this.contyear.setBoundLabelText(resHelper.getString("contyear.boundLabelText"));		
        this.contyear.setBoundLabelLength(100);		
        this.contyear.setBoundLabelUnderline(true);		
        this.contyear.setVisible(true);
        // chkwuxu		
        this.chkwuxu.setText(resHelper.getString("chkwuxu.text"));		
        this.chkwuxu.setVisible(true);		
        this.chkwuxu.setHorizontalAlignment(2);
        this.chkwuxu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkwuxu_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
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
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // kDPanel2
        // kDPanel3		
        this.kDPanel3.setEnabled(false);
        // kDPanel1
        // kDPanel4
        // kDScrollPane1
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contscheme		
        this.contscheme.setBoundLabelText(resHelper.getString("contscheme.boundLabelText"));		
        this.contscheme.setBoundLabelLength(100);		
        this.contscheme.setBoundLabelUnderline(true);		
        this.contscheme.setVisible(true);
        // txtDescription
        // scrollPanescheme
        // txtscheme		
        this.txtscheme.setVisible(true);		
        this.txtscheme.setRequired(false);		
        this.txtscheme.setMaxLength(1000);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"inviteName\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"planDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"linkPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"beizhu\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{inviteName}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{planDate}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{linkPerson}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

                this.kdtEntry.putBindContents("editData",new String[] {"seq","inviteName","type","planDate","amount","linkPerson","beizhu"});


        this.kdtEntry.checkParsed();
        KDFormattedTextField kdtEntry_seq_TextField = new KDFormattedTextField();
        kdtEntry_seq_TextField.setName("kdtEntry_seq_TextField");
        kdtEntry_seq_TextField.setVisible(true);
        kdtEntry_seq_TextField.setEditable(true);
        kdtEntry_seq_TextField.setHorizontalAlignment(2);
        kdtEntry_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntry_seq_CellEditor = new KDTDefaultCellEditor(kdtEntry_seq_TextField);
        this.kdtEntry.getColumn("seq").setEditor(kdtEntry_seq_CellEditor);
        KDTextArea kdtEntry_inviteName_TextArea = new KDTextArea();
        kdtEntry_inviteName_TextArea.setName("kdtEntry_inviteName_TextArea");
        kdtEntry_inviteName_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtEntry_inviteName_CellEditor = new KDTDefaultCellEditor(kdtEntry_inviteName_TextArea);
        this.kdtEntry.getColumn("inviteName").setEditor(kdtEntry_inviteName_CellEditor);
        final KDBizPromptBox kdtEntry_type_PromptBox = new KDBizPromptBox();
        kdtEntry_type_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.base.app.InviteTypeQuery");
        kdtEntry_type_PromptBox.setVisible(true);
        kdtEntry_type_PromptBox.setEditable(true);
        kdtEntry_type_PromptBox.setDisplayFormat("$number$");
        kdtEntry_type_PromptBox.setEditFormat("$number$");
        kdtEntry_type_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_type_CellEditor = new KDTDefaultCellEditor(kdtEntry_type_PromptBox);
        this.kdtEntry.getColumn("type").setEditor(kdtEntry_type_CellEditor);
        ObjectValueRender kdtEntry_type_OVR = new ObjectValueRender();
        kdtEntry_type_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("type").setRenderer(kdtEntry_type_OVR);
        KDDatePicker kdtEntry_planDate_DatePicker = new KDDatePicker();
        kdtEntry_planDate_DatePicker.setName("kdtEntry_planDate_DatePicker");
        kdtEntry_planDate_DatePicker.setVisible(true);
        kdtEntry_planDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntry_planDate_CellEditor = new KDTDefaultCellEditor(kdtEntry_planDate_DatePicker);
        this.kdtEntry.getColumn("planDate").setEditor(kdtEntry_planDate_CellEditor);
        KDFormattedTextField kdtEntry_amount_TextField = new KDFormattedTextField();
        kdtEntry_amount_TextField.setName("kdtEntry_amount_TextField");
        kdtEntry_amount_TextField.setVisible(true);
        kdtEntry_amount_TextField.setEditable(true);
        kdtEntry_amount_TextField.setHorizontalAlignment(2);
        kdtEntry_amount_TextField.setDataType(1);
        	kdtEntry_amount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry_amount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry_amount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_amount_CellEditor = new KDTDefaultCellEditor(kdtEntry_amount_TextField);
        this.kdtEntry.getColumn("amount").setEditor(kdtEntry_amount_CellEditor);
        KDTextField kdtEntry_linkPerson_TextField = new KDTextField();
        kdtEntry_linkPerson_TextField.setName("kdtEntry_linkPerson_TextField");
        kdtEntry_linkPerson_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_linkPerson_CellEditor = new KDTDefaultCellEditor(kdtEntry_linkPerson_TextField);
        this.kdtEntry.getColumn("linkPerson").setEditor(kdtEntry_linkPerson_CellEditor);
        KDTextArea kdtEntry_beizhu_TextArea = new KDTextArea();
        kdtEntry_beizhu_TextArea.setName("kdtEntry_beizhu_TextArea");
        kdtEntry_beizhu_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtEntry_beizhu_CellEditor = new KDTDefaultCellEditor(kdtEntry_beizhu_TextArea);
        this.kdtEntry.getColumn("beizhu").setEditor(kdtEntry_beizhu_CellEditor);
        // kdtE2
		String kdtE2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"evaluationIndex\" t:width=\"260\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"value\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"beizhu\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{evaluationIndex}</t:Cell><t:Cell>$Resource{value}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE2.setFormatXml(resHelper.translateString("kdtE2",kdtE2StrXML));

                this.kdtE2.putBindContents("editData",new String[] {"seq","evaluationIndex","value","beizhu"});


        this.kdtE2.checkParsed();
        KDFormattedTextField kdtE2_seq_TextField = new KDFormattedTextField();
        kdtE2_seq_TextField.setName("kdtE2_seq_TextField");
        kdtE2_seq_TextField.setVisible(true);
        kdtE2_seq_TextField.setEditable(true);
        kdtE2_seq_TextField.setHorizontalAlignment(2);
        kdtE2_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtE2_seq_CellEditor = new KDTDefaultCellEditor(kdtE2_seq_TextField);
        this.kdtE2.getColumn("seq").setEditor(kdtE2_seq_CellEditor);
        KDTextField kdtE2_evaluationIndex_TextField = new KDTextField();
        kdtE2_evaluationIndex_TextField.setName("kdtE2_evaluationIndex_TextField");
        kdtE2_evaluationIndex_TextField.setMaxLength(200);
        KDTDefaultCellEditor kdtE2_evaluationIndex_CellEditor = new KDTDefaultCellEditor(kdtE2_evaluationIndex_TextField);
        this.kdtE2.getColumn("evaluationIndex").setEditor(kdtE2_evaluationIndex_CellEditor);
        KDTextField kdtE2_value_TextField = new KDTextField();
        kdtE2_value_TextField.setName("kdtE2_value_TextField");
        kdtE2_value_TextField.setMaxLength(200);
        KDTDefaultCellEditor kdtE2_value_CellEditor = new KDTDefaultCellEditor(kdtE2_value_TextField);
        this.kdtE2.getColumn("value").setEditor(kdtE2_value_CellEditor);
        KDTextArea kdtE2_beizhu_TextArea = new KDTextArea();
        kdtE2_beizhu_TextArea.setName("kdtE2_beizhu_TextArea");
        kdtE2_beizhu_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtE2_beizhu_CellEditor = new KDTDefaultCellEditor(kdtE2_beizhu_TextArea);
        this.kdtE2.getColumn("beizhu").setEditor(kdtE2_beizhu_CellEditor);
        // contBIMUDF0022		
        this.contBIMUDF0022.setBoundLabelText(resHelper.getString("contBIMUDF0022.boundLabelText"));		
        this.contBIMUDF0022.setBoundLabelLength(100);		
        this.contBIMUDF0022.setBoundLabelUnderline(true);		
        this.contBIMUDF0022.setVisible(true);		
        this.contBIMUDF0022.setBoundLabelAlignment(1);
        // contdec		
        this.contdec.setBoundLabelText(resHelper.getString("contdec.boundLabelText"));		
        this.contdec.setBoundLabelLength(16);		
        this.contdec.setBoundLabelUnderline(true);		
        this.contdec.setVisible(true);		
        this.contdec.setBoundLabelAlignment(8);
        // scrollPaneBIMUDF0022
        // txtBIMUDF0022		
        this.txtBIMUDF0022.setVisible(true);		
        this.txtBIMUDF0022.setRequired(false);		
        this.txtBIMUDF0022.setMaxLength(255);
        // scrollPanedec
        // txtdec		
        this.txtdec.setVisible(true);		
        this.txtdec.setRequired(false);		
        this.txtdec.setMaxLength(1000);
        // comboBizStatus		
        this.comboBizStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBizActionEnum").toArray());		
        this.comboBizStatus.setEnabled(false);
        // txtNumber
        // pkBizDate
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);
        // prmtperson		
        this.prmtperson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtperson.setVisible(true);		
        this.prmtperson.setEditable(true);		
        this.prmtperson.setDisplayFormat("$name$");		
        this.prmtperson.setEditFormat("$number$");		
        this.prmtperson.setCommitFormat("$number$");		
        this.prmtperson.setRequired(false);
        this.prmtperson.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtperson_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtdeparment		
        this.prmtdeparment.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtdeparment.setVisible(true);		
        this.prmtdeparment.setEditable(true);		
        this.prmtdeparment.setDisplayFormat("$name$");		
        this.prmtdeparment.setEditFormat("$number$");		
        this.prmtdeparment.setCommitFormat("$number$");		
        this.prmtdeparment.setRequired(false);		
        this.prmtdeparment.setEnabled(false);
        // prmtCU		
        this.prmtCU.setEnabled(false);
        // prmtprojectName		
        this.prmtprojectName.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7ProjectQuery");		
        this.prmtprojectName.setVisible(true);		
        this.prmtprojectName.setEditable(true);		
        this.prmtprojectName.setDisplayFormat("$name$");		
        this.prmtprojectName.setEditFormat("$number$");		
        this.prmtprojectName.setCommitFormat("$number$");		
        this.prmtprojectName.setRequired(true);
        this.prmtprojectName.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtprojectName_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtprojectType		
        this.prmtprojectType.setQueryInfo("com.kingdee.eas.port.pm.base.app.ProjectTypeQuery");		
        this.prmtprojectType.setVisible(true);		
        this.prmtprojectType.setEditable(true);		
        this.prmtprojectType.setDisplayFormat("$name$");		
        this.prmtprojectType.setEditFormat("$number$");		
        this.prmtprojectType.setCommitFormat("$number$");		
        this.prmtprojectType.setRequired(false);		
        this.prmtprojectType.setEnabled(false);
        // txtamount		
        this.txtamount.setVisible(true);		
        this.txtamount.setHorizontalAlignment(2);		
        this.txtamount.setDataType(1);		
        this.txtamount.setSupportedEmpty(true);		
        this.txtamount.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtamount.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtamount.setPrecision(2);		
        this.txtamount.setRequired(false);		
        this.txtamount.setEnabled(false);
        // prmtyear		
        this.prmtyear.setQueryInfo("com.kingdee.eas.port.pm.base.app.InvestYearQuery");		
        this.prmtyear.setVisible(true);		
        this.prmtyear.setEditable(true);		
        this.prmtyear.setDisplayFormat("$name$");		
        this.prmtyear.setEditFormat("$number$");		
        this.prmtyear.setCommitFormat("$number$");		
        this.prmtyear.setRequired(false);		
        this.prmtyear.setEnabled(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtperson,prmtdeparment,txtamount,prmtyear,prmtprojectType,kdtE2,kdtEntry,prmtCU,pkLastUpdateTime,prmtLastUpdateUser,pkCreateTime,prmtCreator,prmtAuditor,pkBizDate,txtNumber,pkAuditTime,comboBizStatus,comboStatus,prmtprojectName,txtscheme,chkwuxu}));
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
        contCreator.setBounds(new Rectangle(19, 564, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(19, 564, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(19, 590, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(19, 590, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(369, 564, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(369, 564, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(369, 590, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(369, 590, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(722, 564, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(722, 564, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(722, 590, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(722, 590, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane1.setBounds(new Rectangle(18, 124, 975, 428));
        this.add(kDTabbedPane1, new KDLayout.Constraints(18, 124, 975, 428, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(17, 610, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(17, 610, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(17, 12, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(17, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(359, 12, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(359, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStatus.setBounds(new Rectangle(701, 12, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(701, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contperson.setBounds(new Rectangle(17, 40, 270, 19));
        this.add(contperson, new KDLayout.Constraints(17, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contdeparment.setBounds(new Rectangle(359, 40, 270, 19));
        this.add(contdeparment, new KDLayout.Constraints(359, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(702, 40, 270, 19));
        this.add(contCU, new KDLayout.Constraints(702, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contprojectName.setBounds(new Rectangle(17, 69, 270, 19));
        this.add(contprojectName, new KDLayout.Constraints(17, 69, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contprojectType.setBounds(new Rectangle(360, 69, 270, 19));
        this.add(contprojectType, new KDLayout.Constraints(360, 69, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contamount.setBounds(new Rectangle(17, 99, 270, 19));
        this.add(contamount, new KDLayout.Constraints(17, 99, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contyear.setBounds(new Rectangle(703, 69, 270, 19));
        this.add(contyear, new KDLayout.Constraints(703, 69, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkwuxu.setBounds(new Rectangle(360, 99, 270, 19));
        this.add(chkwuxu, new KDLayout.Constraints(360, 99, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane1.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel4, resHelper.getString("kDPanel4.constraints"));
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0, 0, 974, 395));        kDScrollPane1.setBounds(new Rectangle(4, 4, 962, 384));
        kDPanel2.add(kDScrollPane1, new KDLayout.Constraints(4, 4, 962, 384, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(0, 0, 974, 395));        contDescription.setBounds(new Rectangle(10, 10, 952, 137));
        kDPanel3.add(contDescription, new KDLayout.Constraints(10, 10, 952, 137, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contscheme.setBounds(new Rectangle(9, 152, 952, 237));
        kDPanel3.add(contscheme, new KDLayout.Constraints(9, 152, 952, 237, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contscheme
        contscheme.setBoundEditor(scrollPanescheme);
        //scrollPanescheme
        scrollPanescheme.getViewport().add(txtscheme, null);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 974, 395));        kdtEntry.setBounds(new Rectangle(3, 2, 961, 387));
        kdtEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry,new com.kingdee.eas.port.pm.invest.ProjectStartRequestEntryInfo(),null,false);
        kDPanel1.add(kdtEntry_detailPanel, new KDLayout.Constraints(3, 2, 961, 387, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel4
        kDPanel4.setLayout(new KDLayout());
        kDPanel4.putClientProperty("OriginalBounds", new Rectangle(0, 0, 974, 395));        kdtE2.setBounds(new Rectangle(2, 3, 963, 227));
        kdtE2_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE2,new com.kingdee.eas.port.pm.invest.ProjectStartRequestE2Info(),null,false);
        kDPanel4.add(kdtE2_detailPanel, new KDLayout.Constraints(2, 3, 963, 227, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBIMUDF0022.setBounds(new Rectangle(85, 286, 270, 19));
        kDPanel4.add(contBIMUDF0022, new KDLayout.Constraints(85, 286, 270, 19, 0));
        contdec.setBounds(new Rectangle(5, 237, 961, 153));
        kDPanel4.add(contdec, new KDLayout.Constraints(5, 237, 961, 153, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contBIMUDF0022
        contBIMUDF0022.setBoundEditor(scrollPaneBIMUDF0022);
        //scrollPaneBIMUDF0022
        scrollPaneBIMUDF0022.getViewport().add(txtBIMUDF0022, null);
        //contdec
        contdec.setBoundEditor(scrollPanedec);
        //scrollPanedec
        scrollPanedec.getViewport().add(txtdec, null);
        //contBizStatus
        contBizStatus.setBoundEditor(comboBizStatus);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //contperson
        contperson.setBoundEditor(prmtperson);
        //contdeparment
        contdeparment.setBoundEditor(prmtdeparment);
        //contCU
        contCU.setBoundEditor(prmtCU);
        //contprojectName
        contprojectName.setBoundEditor(prmtprojectName);
        //contprojectType
        contprojectType.setBoundEditor(prmtprojectType);
        //contamount
        contamount.setBoundEditor(txtamount);
        //contyear
        contyear.setBoundEditor(prmtyear);

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
		dataBinder.registerBinding("wuxu", boolean.class, this.chkwuxu, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("scheme", String.class, this.txtscheme, "text");
		dataBinder.registerBinding("Entry.seq", int.class, this.kdtEntry, "seq.text");
		dataBinder.registerBinding("Entry", com.kingdee.eas.port.pm.invest.ProjectStartRequestEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("Entry.inviteName", String.class, this.kdtEntry, "inviteName.text");
		dataBinder.registerBinding("Entry.type", java.lang.Object.class, this.kdtEntry, "type.text");
		dataBinder.registerBinding("Entry.planDate", java.util.Date.class, this.kdtEntry, "planDate.text");
		dataBinder.registerBinding("Entry.amount", java.math.BigDecimal.class, this.kdtEntry, "amount.text");
		dataBinder.registerBinding("Entry.linkPerson", String.class, this.kdtEntry, "linkPerson.text");
		dataBinder.registerBinding("Entry.beizhu", String.class, this.kdtEntry, "beizhu.text");
		dataBinder.registerBinding("E2.seq", int.class, this.kdtE2, "seq.text");
		dataBinder.registerBinding("E2", com.kingdee.eas.port.pm.invest.ProjectStartRequestE2Info.class, this.kdtE2, "userObject");
		dataBinder.registerBinding("E2.evaluationIndex", String.class, this.kdtE2, "evaluationIndex.text");
		dataBinder.registerBinding("E2.value", String.class, this.kdtE2, "value.text");
		dataBinder.registerBinding("E2.beizhu", String.class, this.kdtE2, "beizhu.text");
		dataBinder.registerBinding("BIMUDF0022", String.class, this.txtBIMUDF0022, "text");
		dataBinder.registerBinding("dec", String.class, this.txtdec, "text");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("person", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtperson, "data");
		dataBinder.registerBinding("deparment", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtdeparment, "data");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("projectName", com.kingdee.eas.basedata.assistant.ProjectInfo.class, this.prmtprojectName, "data");
		dataBinder.registerBinding("projectType", com.kingdee.eas.port.pm.base.ProjectTypeInfo.class, this.prmtprojectType, "data");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtamount, "value");
		dataBinder.registerBinding("year", com.kingdee.eas.port.pm.base.InvestYearInfo.class, this.prmtyear, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invest.app.ProjectStartRequestEditUIHandler";
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
        this.prmtperson.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo)ov;
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
		getValidateHelper().registerBindProperty("wuxu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scheme", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.inviteName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.type", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.planDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.linkPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.evaluationIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.value", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BIMUDF0022", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dec", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("person", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("deparment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("year", ValidateHelper.ON_SAVE);    		
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
     * output chkwuxu_actionPerformed method
     */
    protected void chkwuxu_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtperson_dataChanged method
     */
    protected void prmtperson_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtprojectName_dataChanged method
     */
    protected void prmtprojectName_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here00
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
        sic.add(new SelectorItemInfo("wuxu"));
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
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("scheme"));
    	sic.add(new SelectorItemInfo("Entry.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("Entry.inviteName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.type.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Entry.type.id"));
			sic.add(new SelectorItemInfo("Entry.type.name"));
        	sic.add(new SelectorItemInfo("Entry.type.number"));
		}
    	sic.add(new SelectorItemInfo("Entry.planDate"));
    	sic.add(new SelectorItemInfo("Entry.amount"));
    	sic.add(new SelectorItemInfo("Entry.linkPerson"));
    	sic.add(new SelectorItemInfo("Entry.beizhu"));
    	sic.add(new SelectorItemInfo("E2.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E2.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E2.evaluationIndex"));
    	sic.add(new SelectorItemInfo("E2.value"));
    	sic.add(new SelectorItemInfo("E2.beizhu"));
        sic.add(new SelectorItemInfo("BIMUDF0022"));
        sic.add(new SelectorItemInfo("dec"));
        sic.add(new SelectorItemInfo("bizStatus"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("status"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("person.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("person.id"));
        	sic.add(new SelectorItemInfo("person.number"));
        	sic.add(new SelectorItemInfo("person.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("deparment.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("deparment.id"));
        	sic.add(new SelectorItemInfo("deparment.number"));
        	sic.add(new SelectorItemInfo("deparment.name"));
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("projectName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("projectName.id"));
        	sic.add(new SelectorItemInfo("projectName.number"));
        	sic.add(new SelectorItemInfo("projectName.name"));
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
        sic.add(new SelectorItemInfo("amount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("year.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("year.id"));
        	sic.add(new SelectorItemInfo("year.number"));
        	sic.add(new SelectorItemInfo("year.name"));
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
        return new MetaDataPK("com.kingdee.eas.port.pm.invest.client", "ProjectStartRequestEditUI");
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
        return com.kingdee.eas.port.pm.invest.client.ProjectStartRequestEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invest.ProjectStartRequestFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo objectValue = new com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/invest/ProjectStartRequest";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.invest.app.ProjectStartRequestQuery");
	}
    
        
					protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtprojectName.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"ÏîÄ¿Ãû³Æ"});
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
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}