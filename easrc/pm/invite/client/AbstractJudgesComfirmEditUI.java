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
public abstract class AbstractJudgesComfirmEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractJudgesComfirmEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdepartment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contplanName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contplanNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contorgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprjName;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcomment;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanecomment;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtcomment;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtdepartment;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtplanName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtplanNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtorgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtinviteType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtprjName;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.eas.port.pm.invite.JudgesComfirmInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractJudgesComfirmEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractJudgesComfirmEditUI.class.getName());
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
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdepartment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contplanName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contplanNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contorgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprjName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contcomment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPanecomment = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtcomment = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtdepartment = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtplanName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtplanNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtorgUnit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtinviteType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtprjName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
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
        this.kDContainer1.setName("kDContainer1");
        this.contNumber.setName("contNumber");
        this.contStatus.setName("contStatus");
        this.contdepartment.setName("contdepartment");
        this.contCreateTime.setName("contCreateTime");
        this.contCreator.setName("contCreator");
        this.contAuditTime.setName("contAuditTime");
        this.contAuditor.setName("contAuditor");
        this.contplanName.setName("contplanName");
        this.contplanNumber.setName("contplanNumber");
        this.contorgUnit.setName("contorgUnit");
        this.continviteType.setName("continviteType");
        this.contprjName.setName("contprjName");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contcomment.setName("contcomment");
        this.scrollPanecomment.setName("scrollPanecomment");
        this.txtcomment.setName("txtcomment");
        this.txtNumber.setName("txtNumber");
        this.comboStatus.setName("comboStatus");
        this.prmtdepartment.setName("prmtdepartment");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtCreator.setName("prmtCreator");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtplanName.setName("prmtplanName");
        this.txtplanNumber.setName("txtplanNumber");
        this.txtorgUnit.setName("txtorgUnit");
        this.txtinviteType.setName("txtinviteType");
        this.txtprjName.setName("txtprjName");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.kDContainer2.setName("kDContainer2");
        this.kdtEntry.setName("kdtEntry");
        this.kDContainer3.setName("kDContainer3");
        // CoreUI		
        this.setPreferredSize(new Dimension(750,530));		
        this.setMinimumSize(new Dimension(750,530));
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setVisible(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setVisible(false);
        // contCU		
        this.contCU.setBoundLabelText(resHelper.getString("contCU.boundLabelText"));		
        this.contCU.setBoundLabelLength(100);		
        this.contCU.setBoundLabelUnderline(true);		
        this.contCU.setEnabled(false);		
        this.contCU.setVisible(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setVisible(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setVisible(false);
        // contBizStatus		
        this.contBizStatus.setBoundLabelText(resHelper.getString("contBizStatus.boundLabelText"));		
        this.contBizStatus.setBoundLabelLength(100);		
        this.contBizStatus.setBoundLabelUnderline(true);		
        this.contBizStatus.setEnabled(false);		
        this.contBizStatus.setVisible(false);
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
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);
        // contdepartment		
        this.contdepartment.setBoundLabelText(resHelper.getString("contdepartment.boundLabelText"));		
        this.contdepartment.setBoundLabelLength(100);		
        this.contdepartment.setBoundLabelUnderline(true);		
        this.contdepartment.setVisible(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contplanName		
        this.contplanName.setBoundLabelText(resHelper.getString("contplanName.boundLabelText"));		
        this.contplanName.setBoundLabelLength(100);		
        this.contplanName.setBoundLabelUnderline(true);		
        this.contplanName.setVisible(true);
        // contplanNumber		
        this.contplanNumber.setBoundLabelText(resHelper.getString("contplanNumber.boundLabelText"));		
        this.contplanNumber.setBoundLabelLength(100);		
        this.contplanNumber.setBoundLabelUnderline(true);		
        this.contplanNumber.setVisible(true);
        // contorgUnit		
        this.contorgUnit.setBoundLabelText(resHelper.getString("contorgUnit.boundLabelText"));		
        this.contorgUnit.setBoundLabelLength(100);		
        this.contorgUnit.setBoundLabelUnderline(true);		
        this.contorgUnit.setVisible(true);
        // continviteType		
        this.continviteType.setBoundLabelText(resHelper.getString("continviteType.boundLabelText"));		
        this.continviteType.setBoundLabelLength(100);		
        this.continviteType.setBoundLabelUnderline(true);		
        this.continviteType.setVisible(true);
        // contprjName		
        this.contprjName.setBoundLabelText(resHelper.getString("contprjName.boundLabelText"));		
        this.contprjName.setBoundLabelLength(100);		
        this.contprjName.setBoundLabelUnderline(true);		
        this.contprjName.setVisible(true);
        // kDTabbedPane1
        // contcomment		
        this.contcomment.setBoundLabelText(resHelper.getString("contcomment.boundLabelText"));		
        this.contcomment.setBoundLabelLength(0);		
        this.contcomment.setBoundLabelUnderline(true);		
        this.contcomment.setVisible(true);		
        this.contcomment.setBoundLabelAlignment(8);
        // scrollPanecomment
        // txtcomment		
        this.txtcomment.setVisible(true);		
        this.txtcomment.setRequired(false);		
        this.txtcomment.setMaxLength(255);
        // txtNumber
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);
        // prmtdepartment		
        this.prmtdepartment.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtdepartment.setVisible(true);		
        this.prmtdepartment.setEditable(true);		
        this.prmtdepartment.setDisplayFormat("$name$");		
        this.prmtdepartment.setEditFormat("$number$");		
        this.prmtdepartment.setCommitFormat("$number$");		
        this.prmtdepartment.setRequired(false);
        // pkCreateTime		
        this.pkCreateTime.setTimeEnabled(true);		
        this.pkCreateTime.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setCommitFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");		
        this.prmtCreator.setDisplayFormat("$name$");
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");
        // prmtplanName		
        this.prmtplanName.setQueryInfo("com.kingdee.eas.port.pm.invite.app.InviteReportQuery");		
        this.prmtplanName.setVisible(true);		
        this.prmtplanName.setEditable(true);		
        this.prmtplanName.setDisplayFormat("$reportName$");		
        this.prmtplanName.setEditFormat("$number$");		
        this.prmtplanName.setCommitFormat("$number$");		
        this.prmtplanName.setRequired(false);
        prmtplanName.addDataChangeListener(new DataChangeListener() {
		public void dataChanged(DataChangeEvent e) {
			try {
				prmtplanName_Changed();
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        this.prmtplanName.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtplanName_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtplanNumber		
        this.txtplanNumber.setVisible(true);		
        this.txtplanNumber.setHorizontalAlignment(2);		
        this.txtplanNumber.setMaxLength(80);		
        this.txtplanNumber.setRequired(false);
        // txtorgUnit		
        this.txtorgUnit.setVisible(true);		
        this.txtorgUnit.setHorizontalAlignment(2);		
        this.txtorgUnit.setMaxLength(80);		
        this.txtorgUnit.setRequired(false);
        // txtinviteType		
        this.txtinviteType.setVisible(true);		
        this.txtinviteType.setHorizontalAlignment(2);		
        this.txtinviteType.setMaxLength(80);		
        this.txtinviteType.setRequired(false);
        // txtprjName		
        this.txtprjName.setVisible(true);		
        this.txtprjName.setHorizontalAlignment(2);		
        this.txtprjName.setMaxLength(80);		
        this.txtprjName.setRequired(false);
        // kDPanel2
        // kDPanel3
        // kDContainer2		
        this.kDContainer2.setEnableActive(false);		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"judgeNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"judgesName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"juType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"orgUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"telephone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"juName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{judgeNumber}</t:Cell><t:Cell>$Resource{judgesName}</t:Cell><t:Cell>$Resource{juType}</t:Cell><t:Cell>$Resource{orgUnit}</t:Cell><t:Cell>$Resource{telephone}</t:Cell><t:Cell>$Resource{comment}</t:Cell><t:Cell>$Resource{juName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        kdtEntry.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtEntry_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});


                this.kdtEntry.putBindContents("editData",new String[] {"seq","judgeNumber","judgesName","juType","orgUnit","telephone","comment","juName"});


        this.kdtEntry.checkParsed();
        final KDBizPromptBox kdtEntry_judgeNumber_PromptBox = new KDBizPromptBox();
        kdtEntry_judgeNumber_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.base.app.JudgesQuery");
        kdtEntry_judgeNumber_PromptBox.setVisible(true);
        kdtEntry_judgeNumber_PromptBox.setEditable(true);
        kdtEntry_judgeNumber_PromptBox.setDisplayFormat("$number$");
        kdtEntry_judgeNumber_PromptBox.setEditFormat("$number$");
        kdtEntry_judgeNumber_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_judgeNumber_CellEditor = new KDTDefaultCellEditor(kdtEntry_judgeNumber_PromptBox);
        this.kdtEntry.getColumn("judgeNumber").setEditor(kdtEntry_judgeNumber_CellEditor);
        ObjectValueRender kdtEntry_judgeNumber_OVR = new ObjectValueRender();
        kdtEntry_judgeNumber_OVR.setFormat(new BizDataFormat("$number$"));
        this.kdtEntry.getColumn("judgeNumber").setRenderer(kdtEntry_judgeNumber_OVR);
        			kdtEntry_judgeNumber_PromptBox.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.pm.base.client.JudgesListUI kdtEntry_judgeNumber_PromptBox_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (kdtEntry_judgeNumber_PromptBox_F7ListUI == null) {
					try {
						kdtEntry_judgeNumber_PromptBox_F7ListUI = new com.kingdee.eas.port.pm.base.client.JudgesListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(kdtEntry_judgeNumber_PromptBox_F7ListUI));
					kdtEntry_judgeNumber_PromptBox_F7ListUI.setF7Use(true,ctx);
					kdtEntry_judgeNumber_PromptBox.setSelector(kdtEntry_judgeNumber_PromptBox_F7ListUI);
				}
			}
		});
					
        KDTextField kdtEntry_judgesName_TextField = new KDTextField();
        kdtEntry_judgesName_TextField.setName("kdtEntry_judgesName_TextField");
        kdtEntry_judgesName_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_judgesName_CellEditor = new KDTDefaultCellEditor(kdtEntry_judgesName_TextField);
        this.kdtEntry.getColumn("judgesName").setEditor(kdtEntry_judgesName_CellEditor);
        KDTextField kdtEntry_juType_TextField = new KDTextField();
        kdtEntry_juType_TextField.setName("kdtEntry_juType_TextField");
        kdtEntry_juType_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_juType_CellEditor = new KDTDefaultCellEditor(kdtEntry_juType_TextField);
        this.kdtEntry.getColumn("juType").setEditor(kdtEntry_juType_CellEditor);
        KDTextField kdtEntry_orgUnit_TextField = new KDTextField();
        kdtEntry_orgUnit_TextField.setName("kdtEntry_orgUnit_TextField");
        kdtEntry_orgUnit_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_orgUnit_CellEditor = new KDTDefaultCellEditor(kdtEntry_orgUnit_TextField);
        this.kdtEntry.getColumn("orgUnit").setEditor(kdtEntry_orgUnit_CellEditor);
        KDTextField kdtEntry_telephone_TextField = new KDTextField();
        kdtEntry_telephone_TextField.setName("kdtEntry_telephone_TextField");
        kdtEntry_telephone_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_telephone_CellEditor = new KDTDefaultCellEditor(kdtEntry_telephone_TextField);
        this.kdtEntry.getColumn("telephone").setEditor(kdtEntry_telephone_CellEditor);
        KDTextField kdtEntry_comment_TextField = new KDTextField();
        kdtEntry_comment_TextField.setName("kdtEntry_comment_TextField");
        kdtEntry_comment_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_comment_CellEditor = new KDTDefaultCellEditor(kdtEntry_comment_TextField);
        this.kdtEntry.getColumn("comment").setEditor(kdtEntry_comment_CellEditor);
        KDTextField kdtEntry_juName_TextField = new KDTextField();
        kdtEntry_juName_TextField.setName("kdtEntry_juName_TextField");
        kdtEntry_juName_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_juName_CellEditor = new KDTDefaultCellEditor(kdtEntry_juName_TextField);
        this.kdtEntry.getColumn("juName").setEditor(kdtEntry_juName_CellEditor);
        // kDContainer3		
        this.kDContainer3.setEnableActive(false);		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtdepartment,prmtplanName,txtplanNumber,txtorgUnit,txtinviteType,txtprjName}));
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
        this.setBounds(new Rectangle(10, 10, 750, 530));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 750, 530));
        contLastUpdateUser.setBounds(new Rectangle(709, 464, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(709, 464, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(709, 490, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(709, 490, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(719, 571, 270, 19));
        this.add(contCU, new KDLayout.Constraints(719, 571, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(719, 545, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(719, 545, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(716, 518, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(716, 518, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizStatus.setBounds(new Rectangle(720, 598, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(720, 598, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(4, 4, 743, 520));
        this.add(kDPanel1, new KDLayout.Constraints(4, 4, 743, 520, 0));
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
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(4, 4, 743, 520));        kDContainer1.setBounds(new Rectangle(16, 124, 710, 97));
        kDPanel1.add(kDContainer1, new KDLayout.Constraints(16, 124, 710, 97, 0));
        contNumber.setBounds(new Rectangle(16, 17, 270, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(16, 17, 270, 19, 0));
        contStatus.setBounds(new Rectangle(455, 17, 270, 19));
        kDPanel1.add(contStatus, new KDLayout.Constraints(455, 17, 270, 19, 0));
        contdepartment.setBounds(new Rectangle(455, 95, 270, 19));
        kDPanel1.add(contdepartment, new KDLayout.Constraints(455, 95, 270, 19, 0));
        contCreateTime.setBounds(new Rectangle(16, 485, 270, 19));
        kDPanel1.add(contCreateTime, new KDLayout.Constraints(16, 485, 270, 19, 0));
        contCreator.setBounds(new Rectangle(16, 459, 270, 19));
        kDPanel1.add(contCreator, new KDLayout.Constraints(16, 459, 270, 19, 0));
        contAuditTime.setBounds(new Rectangle(455, 485, 270, 19));
        kDPanel1.add(contAuditTime, new KDLayout.Constraints(455, 485, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(455, 459, 270, 19));
        kDPanel1.add(contAuditor, new KDLayout.Constraints(455, 459, 270, 19, 0));
        contplanName.setBounds(new Rectangle(16, 43, 270, 19));
        kDPanel1.add(contplanName, new KDLayout.Constraints(16, 43, 270, 19, 0));
        contplanNumber.setBounds(new Rectangle(455, 43, 270, 19));
        kDPanel1.add(contplanNumber, new KDLayout.Constraints(455, 43, 270, 19, 0));
        contorgUnit.setBounds(new Rectangle(455, 69, 270, 19));
        kDPanel1.add(contorgUnit, new KDLayout.Constraints(455, 69, 270, 19, 0));
        continviteType.setBounds(new Rectangle(16, 95, 270, 19));
        kDPanel1.add(continviteType, new KDLayout.Constraints(16, 95, 270, 19, 0));
        contprjName.setBounds(new Rectangle(16, 69, 270, 19));
        kDPanel1.add(contprjName, new KDLayout.Constraints(16, 69, 270, 19, 0));
        kDTabbedPane1.setBounds(new Rectangle(16, 223, 710, 228));
        kDPanel1.add(kDTabbedPane1, new KDLayout.Constraints(16, 223, 710, 228, 0));
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(contcomment, BorderLayout.CENTER);
        //contcomment
        contcomment.setBoundEditor(scrollPanecomment);
        //scrollPanecomment
        scrollPanecomment.getViewport().add(txtcomment, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //contdepartment
        contdepartment.setBoundEditor(prmtdepartment);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contplanName
        contplanName.setBoundEditor(prmtplanName);
        //contplanNumber
        contplanNumber.setBoundEditor(txtplanNumber);
        //contorgUnit
        contorgUnit.setBoundEditor(txtorgUnit);
        //continviteType
        continviteType.setBoundEditor(txtinviteType);
        //contprjName
        contprjName.setBoundEditor(txtprjName);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane1.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kDPanel2.add(kDContainer2, BorderLayout.CENTER);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry,new com.kingdee.eas.port.pm.invite.JudgesComfirmEntryInfo(),null,false);
        kDContainer2.getContentPane().add(kdtEntry_detailPanel, BorderLayout.CENTER);
        //kDPanel3
kDPanel3.setLayout(new BorderLayout(0, 0));        kDPanel3.add(kDContainer3, BorderLayout.CENTER);
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));
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
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("comment", String.class, this.txtcomment, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("department", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtdepartment, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("planName", com.kingdee.eas.port.pm.invite.InviteReportInfo.class, this.prmtplanName, "data");
		dataBinder.registerBinding("planNumber", String.class, this.txtplanNumber, "text");
		dataBinder.registerBinding("orgUnit", String.class, this.txtorgUnit, "text");
		dataBinder.registerBinding("inviteType", String.class, this.txtinviteType, "text");
		dataBinder.registerBinding("prjName", String.class, this.txtprjName, "text");
		dataBinder.registerBinding("Entry.seq", int.class, this.kdtEntry, "seq.text");
		dataBinder.registerBinding("Entry", com.kingdee.eas.port.pm.invite.JudgesComfirmEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("Entry.judgeNumber", java.lang.Object.class, this.kdtEntry, "judgeNumber.text");
		dataBinder.registerBinding("Entry.juName", String.class, this.kdtEntry, "juName.text");
		dataBinder.registerBinding("Entry.juType", String.class, this.kdtEntry, "juType.text");
		dataBinder.registerBinding("Entry.orgUnit", String.class, this.kdtEntry, "orgUnit.text");
		dataBinder.registerBinding("Entry.telephone", String.class, this.kdtEntry, "telephone.text");
		dataBinder.registerBinding("Entry.comment", String.class, this.kdtEntry, "comment.text");
		dataBinder.registerBinding("Entry.judgesName", String.class, this.kdtEntry, "judgesName.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invite.app.JudgesComfirmEditUIHandler";
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
        this.prmtdepartment.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.pm.invite.JudgesComfirmInfo)ov;
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
		getValidateHelper().registerBindProperty("comment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("department", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prjName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.judgeNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.juName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.juType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.telephone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.comment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.judgesName", ValidateHelper.ON_SAVE);    		
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
     * output prmtplanName_dataChanged method
     */
    protected void prmtplanName_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1
    }


    /**
     * output prmtplanName_Changed() method
     */
    public void prmtplanName_Changed() throws Exception
    {
        System.out.println("prmtplanName_Changed() Function is executed!");
            txtplanNumber.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtplanName.getData(),"number")));

    txtorgUnit.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtplanName.getData(),"useOrg.name")));

    txtinviteType.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtplanName.getData(),"inviteType.name")));

    txtprjName.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtplanName.getData(),"proName.name")));


    }

    /**
     * output kdtEntry_Changed(int rowIndex,int colIndex) method
     */
    public void kdtEntry_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("judgeNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"juName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"judgeNumber").getValue(),"juName.name")));

}

    if ("judgeNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"juType").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"judgeNumber").getValue(),"judgeType.name")));

}

    if ("judgeNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"orgUnit").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"judgeNumber").getValue(),"curDep.name")));

}

    if ("judgeNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"telephone").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"judgeNumber").getValue(),"mobile")));

}

    if ("judgeNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"judgesName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"judgeNumber").getValue(),"personName")));

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
        sic.add(new SelectorItemInfo("comment"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("status"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("department.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("department.id"));
        	sic.add(new SelectorItemInfo("department.number"));
        	sic.add(new SelectorItemInfo("department.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("auditTime"));
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
			sic.add(new SelectorItemInfo("planName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("planName.id"));
        	sic.add(new SelectorItemInfo("planName.number"));
        	sic.add(new SelectorItemInfo("planName.reportName"));
		}
        sic.add(new SelectorItemInfo("planNumber"));
        sic.add(new SelectorItemInfo("orgUnit"));
        sic.add(new SelectorItemInfo("inviteType"));
        sic.add(new SelectorItemInfo("prjName"));
    	sic.add(new SelectorItemInfo("Entry.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.judgeNumber.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Entry.judgeNumber.id"));
			sic.add(new SelectorItemInfo("Entry.judgeNumber.number"));
			sic.add(new SelectorItemInfo("Entry.judgeNumber.name"));
		}
    	sic.add(new SelectorItemInfo("Entry.juName"));
    	sic.add(new SelectorItemInfo("Entry.juType"));
    	sic.add(new SelectorItemInfo("Entry.orgUnit"));
    	sic.add(new SelectorItemInfo("Entry.telephone"));
    	sic.add(new SelectorItemInfo("Entry.comment"));
    	sic.add(new SelectorItemInfo("Entry.judgesName"));
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
        return new MetaDataPK("com.kingdee.eas.port.pm.invite.client", "JudgesComfirmEditUI");
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
        return com.kingdee.eas.port.pm.invite.client.JudgesComfirmEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invite.JudgesComfirmFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invite.JudgesComfirmInfo objectValue = new com.kingdee.eas.port.pm.invite.JudgesComfirmInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/invite/JudgesComfirm";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.invite.app.JudgesComfirmQuery");
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