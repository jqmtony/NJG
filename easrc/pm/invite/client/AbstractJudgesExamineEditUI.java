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
public abstract class AbstractJudgesExamineEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractJudgesExamineEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcheckPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprjName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contevaDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjudgeName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreportName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continvitePerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprjOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contppr;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntryIndicators;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntryIndicators_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcheckPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtprjName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkevaDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtjudgeName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtreportName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinvitePerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtprjOrg;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneppr;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtppr;
    protected com.kingdee.eas.port.pm.invite.JudgesExamineInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractJudgesExamineEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractJudgesExamineEditUI.class.getName());
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
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contcheckPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprjName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contevaDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjudgeName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreportName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continvitePerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprjOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contppr = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdtEntryIndicators = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtcheckPerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtprjName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkevaDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtjudgeName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtreportName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtinvitePerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtprjOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.scrollPaneppr = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtppr = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contCU.setName("contCU");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contBizStatus.setName("contBizStatus");
        this.kDPanel1.setName("kDPanel1");
        this.prmtCU.setName("prmtCU");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.comboBizStatus.setName("comboBizStatus");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.kDContainer1.setName("kDContainer1");
        this.contcheckPerson.setName("contcheckPerson");
        this.contprjName.setName("contprjName");
        this.contNumber.setName("contNumber");
        this.contevaDate.setName("contevaDate");
        this.contjudgeName.setName("contjudgeName");
        this.contreportName.setName("contreportName");
        this.continvitePerson.setName("continvitePerson");
        this.contprjOrg.setName("contprjOrg");
        this.contStatus.setName("contStatus");
        this.contppr.setName("contppr");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.kdtEntryIndicators.setName("kdtEntryIndicators");
        this.txtcheckPerson.setName("txtcheckPerson");
        this.txtprjName.setName("txtprjName");
        this.txtNumber.setName("txtNumber");
        this.pkevaDate.setName("pkevaDate");
        this.prmtjudgeName.setName("prmtjudgeName");
        this.prmtreportName.setName("prmtreportName");
        this.prmtinvitePerson.setName("prmtinvitePerson");
        this.txtprjOrg.setName("txtprjOrg");
        this.comboStatus.setName("comboStatus");
        this.scrollPaneppr.setName("scrollPaneppr");
        this.txtppr.setName("txtppr");
        // CoreUI		
        this.setPreferredSize(new Dimension(938,476));
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
        // prmtCU		
        this.prmtCU.setEnabled(false);
        // pkBizDate
        // txtDescription
        // comboBizStatus		
        this.comboBizStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBizActionEnum").toArray());		
        this.comboBizStatus.setEnabled(false);		
        this.comboBizStatus.setVisible(false);
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
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // contcheckPerson		
        this.contcheckPerson.setBoundLabelText(resHelper.getString("contcheckPerson.boundLabelText"));		
        this.contcheckPerson.setBoundLabelLength(100);		
        this.contcheckPerson.setBoundLabelUnderline(true);		
        this.contcheckPerson.setVisible(true);
        // contprjName		
        this.contprjName.setBoundLabelText(resHelper.getString("contprjName.boundLabelText"));		
        this.contprjName.setBoundLabelLength(100);		
        this.contprjName.setBoundLabelUnderline(true);		
        this.contprjName.setVisible(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contevaDate		
        this.contevaDate.setBoundLabelText(resHelper.getString("contevaDate.boundLabelText"));		
        this.contevaDate.setBoundLabelLength(100);		
        this.contevaDate.setBoundLabelUnderline(true);		
        this.contevaDate.setVisible(true);
        // contjudgeName		
        this.contjudgeName.setBoundLabelText(resHelper.getString("contjudgeName.boundLabelText"));		
        this.contjudgeName.setBoundLabelLength(100);		
        this.contjudgeName.setBoundLabelUnderline(true);		
        this.contjudgeName.setVisible(false);
        // contreportName		
        this.contreportName.setBoundLabelText(resHelper.getString("contreportName.boundLabelText"));		
        this.contreportName.setBoundLabelLength(100);		
        this.contreportName.setBoundLabelUnderline(true);		
        this.contreportName.setVisible(true);
        // continvitePerson		
        this.continvitePerson.setBoundLabelText(resHelper.getString("continvitePerson.boundLabelText"));		
        this.continvitePerson.setBoundLabelLength(100);		
        this.continvitePerson.setBoundLabelUnderline(true);		
        this.continvitePerson.setVisible(true);
        // contprjOrg		
        this.contprjOrg.setBoundLabelText(resHelper.getString("contprjOrg.boundLabelText"));		
        this.contprjOrg.setBoundLabelLength(100);		
        this.contprjOrg.setBoundLabelUnderline(true);		
        this.contprjOrg.setVisible(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);
        // contppr		
        this.contppr.setBoundLabelText(resHelper.getString("contppr.boundLabelText"));		
        this.contppr.setBoundLabelLength(16);		
        this.contppr.setBoundLabelUnderline(true);		
        this.contppr.setVisible(true);		
        this.contppr.setBoundLabelAlignment(8);
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
        // kdtEntryIndicators
		String kdtEntryIndicatorsStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"examCategory\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"examIndicators\" t:width=\"550\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"record\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{examCategory}</t:Cell><t:Cell>$Resource{examIndicators}</t:Cell><t:Cell>$Resource{record}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntryIndicators.setFormatXml(resHelper.translateString("kdtEntryIndicators",kdtEntryIndicatorsStrXML));

                this.kdtEntryIndicators.putBindContents("editData",new String[] {"seq","examCategory","examIndicators","record"});


        this.kdtEntryIndicators.checkParsed();
        KDTextField kdtEntryIndicators_examCategory_TextField = new KDTextField();
        kdtEntryIndicators_examCategory_TextField.setName("kdtEntryIndicators_examCategory_TextField");
        kdtEntryIndicators_examCategory_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryIndicators_examCategory_CellEditor = new KDTDefaultCellEditor(kdtEntryIndicators_examCategory_TextField);
        this.kdtEntryIndicators.getColumn("examCategory").setEditor(kdtEntryIndicators_examCategory_CellEditor);
        KDTextField kdtEntryIndicators_examIndicators_TextField = new KDTextField();
        kdtEntryIndicators_examIndicators_TextField.setName("kdtEntryIndicators_examIndicators_TextField");
        kdtEntryIndicators_examIndicators_TextField.setMaxLength(200);
        KDTDefaultCellEditor kdtEntryIndicators_examIndicators_CellEditor = new KDTDefaultCellEditor(kdtEntryIndicators_examIndicators_TextField);
        this.kdtEntryIndicators.getColumn("examIndicators").setEditor(kdtEntryIndicators_examIndicators_CellEditor);
        KDComboBox kdtEntryIndicators_record_ComboBox = new KDComboBox();
        kdtEntryIndicators_record_ComboBox.setName("kdtEntryIndicators_record_ComboBox");
        kdtEntryIndicators_record_ComboBox.setVisible(true);
        kdtEntryIndicators_record_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.pm.invite.examRecord").toArray());
        KDTDefaultCellEditor kdtEntryIndicators_record_CellEditor = new KDTDefaultCellEditor(kdtEntryIndicators_record_ComboBox);
        this.kdtEntryIndicators.getColumn("record").setEditor(kdtEntryIndicators_record_CellEditor);
        // txtcheckPerson		
        this.txtcheckPerson.setVisible(true);		
        this.txtcheckPerson.setHorizontalAlignment(2);		
        this.txtcheckPerson.setMaxLength(100);		
        this.txtcheckPerson.setRequired(false);
        // txtprjName		
        this.txtprjName.setVisible(true);		
        this.txtprjName.setHorizontalAlignment(2);		
        this.txtprjName.setMaxLength(80);		
        this.txtprjName.setRequired(false);
        // txtNumber
        // pkevaDate		
        this.pkevaDate.setVisible(true);		
        this.pkevaDate.setRequired(false);
        // prmtjudgeName		
        this.prmtjudgeName.setQueryInfo("com.kingdee.eas.port.pm.base.app.JudgesQuery");		
        this.prmtjudgeName.setVisible(true);		
        this.prmtjudgeName.setEditable(true);		
        this.prmtjudgeName.setDisplayFormat("$personName$");		
        this.prmtjudgeName.setEditFormat("$number$");		
        this.prmtjudgeName.setCommitFormat("$number$");		
        this.prmtjudgeName.setRequired(false);
        		prmtjudgeName.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.pm.base.client.JudgesListUI prmtjudgeName_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (prmtjudgeName_F7ListUI == null) {
					try {
						prmtjudgeName_F7ListUI = new com.kingdee.eas.port.pm.base.client.JudgesListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmtjudgeName_F7ListUI));
					prmtjudgeName_F7ListUI.setF7Use(true,ctx);
					prmtjudgeName.setSelector(prmtjudgeName_F7ListUI);
				}
			}
		});
					
        this.prmtjudgeName.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtjudgeName_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtreportName		
        this.prmtreportName.setQueryInfo("com.kingdee.eas.port.pm.invite.app.InviteReportQuery");		
        this.prmtreportName.setVisible(true);		
        this.prmtreportName.setEditable(true);		
        this.prmtreportName.setDisplayFormat("$reportName$");		
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
        // prmtinvitePerson		
        this.prmtinvitePerson.setQueryInfo("com.kingdee.eas.port.pm.base.app.JudgesQuery");		
        this.prmtinvitePerson.setVisible(true);		
        this.prmtinvitePerson.setEditable(true);		
        this.prmtinvitePerson.setDisplayFormat("$personName$");		
        this.prmtinvitePerson.setEditFormat("$number$");		
        this.prmtinvitePerson.setCommitFormat("$number$");		
        this.prmtinvitePerson.setRequired(false);
        		prmtinvitePerson.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.pm.base.client.JudgesListUI prmtinvitePerson_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (prmtinvitePerson_F7ListUI == null) {
					try {
						prmtinvitePerson_F7ListUI = new com.kingdee.eas.port.pm.base.client.JudgesListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmtinvitePerson_F7ListUI));
					prmtinvitePerson_F7ListUI.setF7Use(true,ctx);
					prmtinvitePerson.setSelector(prmtinvitePerson_F7ListUI);
				}
			}
		});
					
        // txtprjOrg		
        this.txtprjOrg.setVisible(true);		
        this.txtprjOrg.setHorizontalAlignment(2);		
        this.txtprjOrg.setMaxLength(80);		
        this.txtprjOrg.setRequired(false);
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);
        // scrollPaneppr
        // txtppr		
        this.txtppr.setVisible(true);		
        this.txtppr.setRequired(false);		
        this.txtppr.setMaxLength(255);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtreportName,txtprjOrg,txtprjName,prmtjudgeName,txtcheckPerson,pkevaDate,prmtinvitePerson}));
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
        this.setBounds(new Rectangle(10, 10, 938, 476));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 938, 476));
        contCU.setBounds(new Rectangle(622, 494, 270, 19));
        this.add(contCU, new KDLayout.Constraints(622, 494, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(22, 491, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(22, 491, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(324, 492, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(324, 492, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizStatus.setBounds(new Rectangle(628, 516, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(628, 516, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(3, 3, 938, 475));
        this.add(kDPanel1, new KDLayout.Constraints(3, 3, 938, 475, 0));
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
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(3, 3, 938, 475));        contCreator.setBounds(new Rectangle(13, 410, 270, 19));
        kDPanel1.add(contCreator, new KDLayout.Constraints(13, 410, 270, 19, 0));
        contCreateTime.setBounds(new Rectangle(13, 439, 270, 19));
        kDPanel1.add(contCreateTime, new KDLayout.Constraints(13, 439, 270, 19, 0));
        contLastUpdateUser.setBounds(new Rectangle(332, 410, 270, 19));
        kDPanel1.add(contLastUpdateUser, new KDLayout.Constraints(332, 410, 270, 19, 0));
        contLastUpdateTime.setBounds(new Rectangle(332, 439, 270, 19));
        kDPanel1.add(contLastUpdateTime, new KDLayout.Constraints(332, 439, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(652, 410, 270, 19));
        kDPanel1.add(contAuditor, new KDLayout.Constraints(652, 410, 270, 19, 0));
        contAuditTime.setBounds(new Rectangle(652, 439, 270, 19));
        kDPanel1.add(contAuditTime, new KDLayout.Constraints(652, 439, 270, 19, 0));
        kDContainer1.setBounds(new Rectangle(13, 136, 909, 269));
        kDPanel1.add(kDContainer1, new KDLayout.Constraints(13, 136, 909, 269, 0));
        contcheckPerson.setBounds(new Rectangle(14, 82, 270, 19));
        kDPanel1.add(contcheckPerson, new KDLayout.Constraints(14, 82, 270, 19, 0));
        contprjName.setBounds(new Rectangle(14, 52, 270, 19));
        kDPanel1.add(contprjName, new KDLayout.Constraints(14, 52, 270, 19, 0));
        contNumber.setBounds(new Rectangle(14, 22, 270, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(14, 22, 270, 19, 0));
        contevaDate.setBounds(new Rectangle(333, 45, 270, 19));
        kDPanel1.add(contevaDate, new KDLayout.Constraints(333, 45, 270, 19, 0));
        contjudgeName.setBounds(new Rectangle(596, 15, 270, 19));
        kDPanel1.add(contjudgeName, new KDLayout.Constraints(596, 15, 270, 19, 0));
        contreportName.setBounds(new Rectangle(333, 22, 270, 19));
        kDPanel1.add(contreportName, new KDLayout.Constraints(333, 22, 270, 19, 0));
        continvitePerson.setBounds(new Rectangle(14, 114, 270, 19));
        kDPanel1.add(continvitePerson, new KDLayout.Constraints(14, 114, 270, 19, 0));
        contprjOrg.setBounds(new Rectangle(652, 22, 270, 19));
        kDPanel1.add(contprjOrg, new KDLayout.Constraints(652, 22, 270, 19, 0));
        contStatus.setBounds(new Rectangle(652, 45, 270, 19));
        kDPanel1.add(contStatus, new KDLayout.Constraints(652, 45, 270, 19, 0));
        contppr.setBounds(new Rectangle(333, 65, 590, 68));
        kDPanel1.add(contppr, new KDLayout.Constraints(333, 65, 590, 68, 0));
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
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntryIndicators_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntryIndicators,new com.kingdee.eas.port.pm.invite.JudgesExamineEntryIndicatorInfo(),null,false);
        kDContainer1.getContentPane().add(kdtEntryIndicators_detailPanel, BorderLayout.CENTER);
		kdtEntryIndicators_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("record","1");
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        //contcheckPerson
        contcheckPerson.setBoundEditor(txtcheckPerson);
        //contprjName
        contprjName.setBoundEditor(txtprjName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contevaDate
        contevaDate.setBoundEditor(pkevaDate);
        //contjudgeName
        contjudgeName.setBoundEditor(prmtjudgeName);
        //contreportName
        contreportName.setBoundEditor(prmtreportName);
        //continvitePerson
        continvitePerson.setBoundEditor(prmtinvitePerson);
        //contprjOrg
        contprjOrg.setBoundEditor(txtprjOrg);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //contppr
        contppr.setBoundEditor(scrollPaneppr);
        //scrollPaneppr
        scrollPaneppr.getViewport().add(txtppr, null);

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
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("EntryIndicators.seq", int.class, this.kdtEntryIndicators, "seq.text");
		dataBinder.registerBinding("EntryIndicators", com.kingdee.eas.port.pm.invite.JudgesExamineEntryIndicatorInfo.class, this.kdtEntryIndicators, "userObject");
		dataBinder.registerBinding("EntryIndicators.examCategory", String.class, this.kdtEntryIndicators, "examCategory.text");
		dataBinder.registerBinding("EntryIndicators.examIndicators", String.class, this.kdtEntryIndicators, "examIndicators.text");
		dataBinder.registerBinding("EntryIndicators.record", com.kingdee.util.enums.Enum.class, this.kdtEntryIndicators, "record.text");
		dataBinder.registerBinding("checkPerson", String.class, this.txtcheckPerson, "text");
		dataBinder.registerBinding("prjName", String.class, this.txtprjName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("evaDate", java.util.Date.class, this.pkevaDate, "value");
		dataBinder.registerBinding("judgeName", com.kingdee.eas.port.pm.base.JudgesInfo.class, this.prmtjudgeName, "data");
		dataBinder.registerBinding("reportName", com.kingdee.eas.port.pm.invite.InviteReportInfo.class, this.prmtreportName, "data");
		dataBinder.registerBinding("invitePerson", com.kingdee.eas.port.pm.base.JudgesInfo.class, this.prmtinvitePerson, "data");
		dataBinder.registerBinding("prjOrg", String.class, this.txtprjOrg, "text");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("ppr", String.class, this.txtppr, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invite.app.JudgesExamineEditUIHandler";
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
        this.prmtreportName.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.pm.invite.JudgesExamineInfo)ov;
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
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryIndicators.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryIndicators", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryIndicators.examCategory", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryIndicators.examIndicators", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryIndicators.record", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("checkPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prjName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("evaDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("judgeName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reportName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invitePerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prjOrg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ppr", ValidateHelper.ON_SAVE);    		
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
     * output prmtjudgeName_dataChanged method
     */
    protected void prmtjudgeName_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
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
            txtprjOrg.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtreportName.getData(),"useOrg.name")));

    txtprjName.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtreportName.getData(),"proName.name")));


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
    	sic.add(new SelectorItemInfo("EntryIndicators.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EntryIndicators.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("EntryIndicators.examCategory"));
    	sic.add(new SelectorItemInfo("EntryIndicators.examIndicators"));
    	sic.add(new SelectorItemInfo("EntryIndicators.record"));
        sic.add(new SelectorItemInfo("checkPerson"));
        sic.add(new SelectorItemInfo("prjName"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("evaDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("judgeName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("judgeName.id"));
        	sic.add(new SelectorItemInfo("judgeName.number"));
        	sic.add(new SelectorItemInfo("judgeName.name"));
        	sic.add(new SelectorItemInfo("judgeName.personName"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("reportName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("reportName.id"));
        	sic.add(new SelectorItemInfo("reportName.number"));
        	sic.add(new SelectorItemInfo("reportName.reportName"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("invitePerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("invitePerson.id"));
        	sic.add(new SelectorItemInfo("invitePerson.number"));
        	sic.add(new SelectorItemInfo("invitePerson.name"));
        	sic.add(new SelectorItemInfo("invitePerson.personName"));
		}
        sic.add(new SelectorItemInfo("prjOrg"));
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("ppr"));
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
        return new MetaDataPK("com.kingdee.eas.port.pm.invite.client", "JudgesExamineEditUI");
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
        return com.kingdee.eas.port.pm.invite.client.JudgesExamineEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invite.JudgesExamineFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invite.JudgesExamineInfo objectValue = new com.kingdee.eas.port.pm.invite.JudgesExamineInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/invite/JudgesExamine";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.invite.app.JudgesExamineQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntryIndicators;
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