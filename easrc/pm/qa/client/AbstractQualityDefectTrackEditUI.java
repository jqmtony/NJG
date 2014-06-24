/**
 * output package name
 */
package com.kingdee.eas.port.pm.qa.client;

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
public abstract class AbstractQualityDefectTrackEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractQualityDefectTrackEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpersonMake;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpersonImplement;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrespondDepart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contimpactNote;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contquestionDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreasonAnalysis;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsolution;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conteconomicLoss;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE1;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE1_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contquestionType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contquestionName;
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
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprojectName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpersonMake;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpersonImplement;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtrespondDepart;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneimpactNote;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtimpactNote;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanequestionDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtquestionDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanereasonAnalysis;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtreasonAnalysis;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanesolution;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtsolution;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txteconomicLoss;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtquestionType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtquestionName;
    protected com.kingdee.eas.port.pm.qa.QualityDefectTrackInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractQualityDefectTrackEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractQualityDefectTrackEditUI.class.getName());
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
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpersonMake = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpersonImplement = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrespondDepart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contimpactNote = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contquestionDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreasonAnalysis = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsolution = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conteconomicLoss = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtE1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDCheckBox1 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contquestionType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contquestionName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.prmtprojectName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtpersonMake = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtpersonImplement = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtrespondDepart = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.scrollPaneimpactNote = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtimpactNote = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanequestionDescription = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtquestionDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanereasonAnalysis = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtreasonAnalysis = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanesolution = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtsolution = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txteconomicLoss = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtquestionType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtquestionName = new com.kingdee.bos.ctrl.swing.KDTextField();
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
        this.contprojectName.setName("contprojectName");
        this.contpersonMake.setName("contpersonMake");
        this.contpersonImplement.setName("contpersonImplement");
        this.contrespondDepart.setName("contrespondDepart");
        this.contimpactNote.setName("contimpactNote");
        this.contquestionDescription.setName("contquestionDescription");
        this.contreasonAnalysis.setName("contreasonAnalysis");
        this.contsolution.setName("contsolution");
        this.conteconomicLoss.setName("conteconomicLoss");
        this.kdtE1.setName("kdtE1");
        this.kDCheckBox1.setName("kDCheckBox1");
        this.contquestionType.setName("contquestionType");
        this.contquestionName.setName("contquestionName");
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
        this.prmtprojectName.setName("prmtprojectName");
        this.prmtpersonMake.setName("prmtpersonMake");
        this.prmtpersonImplement.setName("prmtpersonImplement");
        this.prmtrespondDepart.setName("prmtrespondDepart");
        this.scrollPaneimpactNote.setName("scrollPaneimpactNote");
        this.txtimpactNote.setName("txtimpactNote");
        this.scrollPanequestionDescription.setName("scrollPanequestionDescription");
        this.txtquestionDescription.setName("txtquestionDescription");
        this.scrollPanereasonAnalysis.setName("scrollPanereasonAnalysis");
        this.txtreasonAnalysis.setName("txtreasonAnalysis");
        this.scrollPanesolution.setName("scrollPanesolution");
        this.txtsolution.setName("txtsolution");
        this.txteconomicLoss.setName("txteconomicLoss");
        this.prmtquestionType.setName("prmtquestionType");
        this.txtquestionName.setName("txtquestionName");
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
        // contprojectName		
        this.contprojectName.setBoundLabelText(resHelper.getString("contprojectName.boundLabelText"));		
        this.contprojectName.setBoundLabelLength(100);		
        this.contprojectName.setBoundLabelUnderline(true);		
        this.contprojectName.setVisible(true);
        // contpersonMake		
        this.contpersonMake.setBoundLabelText(resHelper.getString("contpersonMake.boundLabelText"));		
        this.contpersonMake.setBoundLabelLength(100);		
        this.contpersonMake.setBoundLabelUnderline(true);		
        this.contpersonMake.setVisible(true);
        // contpersonImplement		
        this.contpersonImplement.setBoundLabelText(resHelper.getString("contpersonImplement.boundLabelText"));		
        this.contpersonImplement.setBoundLabelLength(100);		
        this.contpersonImplement.setBoundLabelUnderline(true);		
        this.contpersonImplement.setVisible(true);
        // contrespondDepart		
        this.contrespondDepart.setBoundLabelText(resHelper.getString("contrespondDepart.boundLabelText"));		
        this.contrespondDepart.setBoundLabelLength(100);		
        this.contrespondDepart.setBoundLabelUnderline(true);		
        this.contrespondDepart.setVisible(true);
        // contimpactNote		
        this.contimpactNote.setBoundLabelText(resHelper.getString("contimpactNote.boundLabelText"));		
        this.contimpactNote.setBoundLabelLength(16);		
        this.contimpactNote.setBoundLabelUnderline(true);		
        this.contimpactNote.setVisible(true);		
        this.contimpactNote.setBoundLabelAlignment(8);
        // contquestionDescription		
        this.contquestionDescription.setBoundLabelText(resHelper.getString("contquestionDescription.boundLabelText"));		
        this.contquestionDescription.setBoundLabelLength(16);		
        this.contquestionDescription.setBoundLabelUnderline(true);		
        this.contquestionDescription.setVisible(true);		
        this.contquestionDescription.setBoundLabelAlignment(8);
        // contreasonAnalysis		
        this.contreasonAnalysis.setBoundLabelText(resHelper.getString("contreasonAnalysis.boundLabelText"));		
        this.contreasonAnalysis.setBoundLabelLength(16);		
        this.contreasonAnalysis.setBoundLabelUnderline(true);		
        this.contreasonAnalysis.setVisible(true);		
        this.contreasonAnalysis.setBoundLabelAlignment(8);
        // contsolution		
        this.contsolution.setBoundLabelText(resHelper.getString("contsolution.boundLabelText"));		
        this.contsolution.setBoundLabelLength(16);		
        this.contsolution.setBoundLabelUnderline(true);		
        this.contsolution.setVisible(true);		
        this.contsolution.setBoundLabelAlignment(8);
        // conteconomicLoss		
        this.conteconomicLoss.setBoundLabelText(resHelper.getString("conteconomicLoss.boundLabelText"));		
        this.conteconomicLoss.setBoundLabelLength(100);		
        this.conteconomicLoss.setBoundLabelUnderline(true);		
        this.conteconomicLoss.setVisible(true);
        // kdtE1
		String kdtE1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"mainRespond\" t:width=\"220\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"supplier\" t:width=\"220\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{mainRespond}</t:Cell><t:Cell>$Resource{supplier}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE1.setFormatXml(resHelper.translateString("kdtE1",kdtE1StrXML));

                this.kdtE1.putBindContents("editData",new String[] {"seq","mainRespond","supplier","description"});


        this.kdtE1.checkParsed();
        KDTextField kdtE1_mainRespond_TextField = new KDTextField();
        kdtE1_mainRespond_TextField.setName("kdtE1_mainRespond_TextField");
        kdtE1_mainRespond_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_mainRespond_CellEditor = new KDTDefaultCellEditor(kdtE1_mainRespond_TextField);
        this.kdtE1.getColumn("mainRespond").setEditor(kdtE1_mainRespond_CellEditor);
        final KDBizPromptBox kdtE1_supplier_PromptBox = new KDBizPromptBox();
        kdtE1_supplier_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierDefaultQuery");
        kdtE1_supplier_PromptBox.setVisible(true);
        kdtE1_supplier_PromptBox.setEditable(true);
        kdtE1_supplier_PromptBox.setDisplayFormat("$number$");
        kdtE1_supplier_PromptBox.setEditFormat("$number$");
        kdtE1_supplier_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_supplier_CellEditor = new KDTDefaultCellEditor(kdtE1_supplier_PromptBox);
        this.kdtE1.getColumn("supplier").setEditor(kdtE1_supplier_CellEditor);
        ObjectValueRender kdtE1_supplier_OVR = new ObjectValueRender();
        kdtE1_supplier_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE1.getColumn("supplier").setRenderer(kdtE1_supplier_OVR);
        KDTextField kdtE1_description_TextField = new KDTextField();
        kdtE1_description_TextField.setName("kdtE1_description_TextField");
        kdtE1_description_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtE1_description_CellEditor = new KDTDefaultCellEditor(kdtE1_description_TextField);
        this.kdtE1.getColumn("description").setEditor(kdtE1_description_CellEditor);
        // kDCheckBox1		
        this.kDCheckBox1.setText(resHelper.getString("kDCheckBox1.text"));
        // contquestionType		
        this.contquestionType.setBoundLabelText(resHelper.getString("contquestionType.boundLabelText"));		
        this.contquestionType.setBoundLabelLength(100);		
        this.contquestionType.setBoundLabelUnderline(true);		
        this.contquestionType.setVisible(true);
        // contquestionName		
        this.contquestionName.setBoundLabelText(resHelper.getString("contquestionName.boundLabelText"));		
        this.contquestionName.setBoundLabelLength(100);		
        this.contquestionName.setBoundLabelUnderline(true);		
        this.contquestionName.setVisible(true);
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
        this.comboBizStatus.setVisible(false);
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // prmtprojectName		
        this.prmtprojectName.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7ProjectQuery");		
        this.prmtprojectName.setVisible(true);		
        this.prmtprojectName.setEditable(true);		
        this.prmtprojectName.setDisplayFormat("$name$");		
        this.prmtprojectName.setEditFormat("$number$");		
        this.prmtprojectName.setCommitFormat("$number$");		
        this.prmtprojectName.setRequired(false);
        // prmtpersonMake		
        this.prmtpersonMake.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtpersonMake.setVisible(true);		
        this.prmtpersonMake.setEditable(true);		
        this.prmtpersonMake.setDisplayFormat("$name$");		
        this.prmtpersonMake.setEditFormat("$number$");		
        this.prmtpersonMake.setCommitFormat("$number$");		
        this.prmtpersonMake.setRequired(false);
        // prmtpersonImplement		
        this.prmtpersonImplement.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtpersonImplement.setVisible(true);		
        this.prmtpersonImplement.setEditable(true);		
        this.prmtpersonImplement.setDisplayFormat("$name$");		
        this.prmtpersonImplement.setEditFormat("$number$");		
        this.prmtpersonImplement.setCommitFormat("$number$");		
        this.prmtpersonImplement.setRequired(false);
        // prmtrespondDepart		
        this.prmtrespondDepart.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtrespondDepart.setVisible(true);		
        this.prmtrespondDepart.setEditable(true);		
        this.prmtrespondDepart.setDisplayFormat("$name$");		
        this.prmtrespondDepart.setEditFormat("$number$");		
        this.prmtrespondDepart.setCommitFormat("$number$");		
        this.prmtrespondDepart.setRequired(false);
        // scrollPaneimpactNote
        // txtimpactNote		
        this.txtimpactNote.setVisible(true);		
        this.txtimpactNote.setRequired(false);		
        this.txtimpactNote.setMaxLength(500);
        // scrollPanequestionDescription
        // txtquestionDescription		
        this.txtquestionDescription.setVisible(true);		
        this.txtquestionDescription.setRequired(false);		
        this.txtquestionDescription.setMaxLength(500);
        // scrollPanereasonAnalysis
        // txtreasonAnalysis		
        this.txtreasonAnalysis.setVisible(true);		
        this.txtreasonAnalysis.setRequired(false);		
        this.txtreasonAnalysis.setMaxLength(500);
        // scrollPanesolution
        // txtsolution		
        this.txtsolution.setVisible(true);		
        this.txtsolution.setRequired(false);		
        this.txtsolution.setMaxLength(500);
        // txteconomicLoss		
        this.txteconomicLoss.setVisible(true);		
        this.txteconomicLoss.setHorizontalAlignment(2);		
        this.txteconomicLoss.setDataType(1);		
        this.txteconomicLoss.setSupportedEmpty(true);		
        this.txteconomicLoss.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txteconomicLoss.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txteconomicLoss.setPrecision(2);		
        this.txteconomicLoss.setRequired(false);
        // prmtquestionType		
        this.prmtquestionType.setQueryInfo("com.kingdee.eas.port.pm.base.app.QuestionTypeQuery");		
        this.prmtquestionType.setVisible(true);		
        this.prmtquestionType.setEditable(true);		
        this.prmtquestionType.setDisplayFormat("$name$");		
        this.prmtquestionType.setEditFormat("$number$");		
        this.prmtquestionType.setCommitFormat("$number$");		
        this.prmtquestionType.setRequired(false);
        // txtquestionName		
        this.txtquestionName.setVisible(true);		
        this.txtquestionName.setHorizontalAlignment(2);		
        this.txtquestionName.setMaxLength(100);		
        this.txtquestionName.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtprojectName,prmtpersonMake,prmtpersonImplement,prmtrespondDepart,txtquestionDescription,txtimpactNote,txtreasonAnalysis,txtsolution,txteconomicLoss,prmtquestionType,txtquestionName}));
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
        contCreator.setBounds(new Rectangle(10, 569, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 569, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(10, 593, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(10, 593, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(370, 569, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(370, 569, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(370, 593, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(370, 593, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(370, 8, 270, 19));
        this.add(contCU, new KDLayout.Constraints(370, 8, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(10, 8, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 8, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(370, 77, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(370, 77, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(13, 619, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(13, 619, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(731, 569, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(731, 569, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(731, 8, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(731, 8, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(345, 620, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(345, 620, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(731, 593, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(731, 593, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contprojectName.setBounds(new Rectangle(10, 31, 270, 19));
        this.add(contprojectName, new KDLayout.Constraints(10, 31, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpersonMake.setBounds(new Rectangle(10, 54, 270, 19));
        this.add(contpersonMake, new KDLayout.Constraints(10, 54, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contpersonImplement.setBounds(new Rectangle(370, 54, 270, 19));
        this.add(contpersonImplement, new KDLayout.Constraints(370, 54, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrespondDepart.setBounds(new Rectangle(731, 54, 270, 19));
        this.add(contrespondDepart, new KDLayout.Constraints(731, 54, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contimpactNote.setBounds(new Rectangle(513, 104, 488, 105));
        this.add(contimpactNote, new KDLayout.Constraints(513, 104, 488, 105, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contquestionDescription.setBounds(new Rectangle(10, 104, 488, 105));
        this.add(contquestionDescription, new KDLayout.Constraints(10, 104, 488, 105, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contreasonAnalysis.setBounds(new Rectangle(10, 219, 488, 121));
        this.add(contreasonAnalysis, new KDLayout.Constraints(10, 219, 488, 121, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsolution.setBounds(new Rectangle(513, 219, 488, 121));
        this.add(contsolution, new KDLayout.Constraints(513, 219, 488, 121, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conteconomicLoss.setBounds(new Rectangle(10, 348, 270, 19));
        this.add(conteconomicLoss, new KDLayout.Constraints(10, 348, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtE1.setBounds(new Rectangle(7, 374, 991, 187));
        kdtE1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE1,new com.kingdee.eas.port.pm.qa.QualityDefectTrackE1Info(),null,false);
        this.add(kdtE1_detailPanel, new KDLayout.Constraints(7, 374, 991, 187, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDCheckBox1.setBounds(new Rectangle(324, 348, 140, 19));
        this.add(kDCheckBox1, new KDLayout.Constraints(324, 348, 140, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contquestionType.setBounds(new Rectangle(731, 31, 270, 19));
        this.add(contquestionType, new KDLayout.Constraints(731, 31, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contquestionName.setBounds(new Rectangle(370, 31, 270, 19));
        this.add(contquestionName, new KDLayout.Constraints(370, 31, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
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
        //contprojectName
        contprojectName.setBoundEditor(prmtprojectName);
        //contpersonMake
        contpersonMake.setBoundEditor(prmtpersonMake);
        //contpersonImplement
        contpersonImplement.setBoundEditor(prmtpersonImplement);
        //contrespondDepart
        contrespondDepart.setBoundEditor(prmtrespondDepart);
        //contimpactNote
        contimpactNote.setBoundEditor(scrollPaneimpactNote);
        //scrollPaneimpactNote
        scrollPaneimpactNote.getViewport().add(txtimpactNote, null);
        //contquestionDescription
        contquestionDescription.setBoundEditor(scrollPanequestionDescription);
        //scrollPanequestionDescription
        scrollPanequestionDescription.getViewport().add(txtquestionDescription, null);
        //contreasonAnalysis
        contreasonAnalysis.setBoundEditor(scrollPanereasonAnalysis);
        //scrollPanereasonAnalysis
        scrollPanereasonAnalysis.getViewport().add(txtreasonAnalysis, null);
        //contsolution
        contsolution.setBoundEditor(scrollPanesolution);
        //scrollPanesolution
        scrollPanesolution.getViewport().add(txtsolution, null);
        //conteconomicLoss
        conteconomicLoss.setBoundEditor(txteconomicLoss);
        //contquestionType
        contquestionType.setBoundEditor(prmtquestionType);
        //contquestionName
        contquestionName.setBoundEditor(txtquestionName);

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
		dataBinder.registerBinding("E1.seq", int.class, this.kdtE1, "seq.text");
		dataBinder.registerBinding("E1", com.kingdee.eas.port.pm.qa.QualityDefectTrackE1Info.class, this.kdtE1, "userObject");
		dataBinder.registerBinding("E1.mainRespond", String.class, this.kdtE1, "mainRespond.text");
		dataBinder.registerBinding("E1.supplier", java.lang.Object.class, this.kdtE1, "supplier.text");
		dataBinder.registerBinding("E1.description", String.class, this.kdtE1, "description.text");
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
		dataBinder.registerBinding("projectName", com.kingdee.eas.basedata.assistant.ProjectInfo.class, this.prmtprojectName, "data");
		dataBinder.registerBinding("personMake", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtpersonMake, "data");
		dataBinder.registerBinding("personImplement", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtpersonImplement, "data");
		dataBinder.registerBinding("respondDepart", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtrespondDepart, "data");
		dataBinder.registerBinding("impactNote", String.class, this.txtimpactNote, "text");
		dataBinder.registerBinding("questionDescription", String.class, this.txtquestionDescription, "text");
		dataBinder.registerBinding("reasonAnalysis", String.class, this.txtreasonAnalysis, "text");
		dataBinder.registerBinding("solution", String.class, this.txtsolution, "text");
		dataBinder.registerBinding("economicLoss", java.math.BigDecimal.class, this.txteconomicLoss, "value");
		dataBinder.registerBinding("questionType", com.kingdee.eas.port.pm.base.QuestionTypeInfo.class, this.prmtquestionType, "data");
		dataBinder.registerBinding("questionName", String.class, this.txtquestionName, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.qa.app.QualityDefectTrackEditUIHandler";
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
        this.prmtprojectName.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.pm.qa.QualityDefectTrackInfo)ov;
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
		getValidateHelper().registerBindProperty("E1.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.mainRespond", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.description", ValidateHelper.ON_SAVE);    
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
		getValidateHelper().registerBindProperty("projectName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("personMake", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("personImplement", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("respondDepart", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("impactNote", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("questionDescription", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reasonAnalysis", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("solution", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("economicLoss", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("questionType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("questionName", ValidateHelper.ON_SAVE);    		
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
    	sic.add(new SelectorItemInfo("E1.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E1.mainRespond"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.supplier.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.supplier.id"));
			sic.add(new SelectorItemInfo("E1.supplier.name"));
        	sic.add(new SelectorItemInfo("E1.supplier.number"));
		}
    	sic.add(new SelectorItemInfo("E1.description"));
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
			sic.add(new SelectorItemInfo("projectName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("projectName.id"));
        	sic.add(new SelectorItemInfo("projectName.number"));
        	sic.add(new SelectorItemInfo("projectName.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("personMake.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("personMake.id"));
        	sic.add(new SelectorItemInfo("personMake.number"));
        	sic.add(new SelectorItemInfo("personMake.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("personImplement.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("personImplement.id"));
        	sic.add(new SelectorItemInfo("personImplement.number"));
        	sic.add(new SelectorItemInfo("personImplement.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("respondDepart.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("respondDepart.id"));
        	sic.add(new SelectorItemInfo("respondDepart.number"));
        	sic.add(new SelectorItemInfo("respondDepart.name"));
		}
        sic.add(new SelectorItemInfo("impactNote"));
        sic.add(new SelectorItemInfo("questionDescription"));
        sic.add(new SelectorItemInfo("reasonAnalysis"));
        sic.add(new SelectorItemInfo("solution"));
        sic.add(new SelectorItemInfo("economicLoss"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("questionType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("questionType.id"));
        	sic.add(new SelectorItemInfo("questionType.number"));
        	sic.add(new SelectorItemInfo("questionType.name"));
		}
        sic.add(new SelectorItemInfo("questionName"));
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
        return new MetaDataPK("com.kingdee.eas.port.pm.qa.client", "QualityDefectTrackEditUI");
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
        return com.kingdee.eas.port.pm.qa.client.QualityDefectTrackEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.qa.QualityDefectTrackFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.qa.QualityDefectTrackInfo objectValue = new com.kingdee.eas.port.pm.qa.QualityDefectTrackInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/qa/QualityDefectTrack";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.qa.app.QualityDefectTrackQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtE1;
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