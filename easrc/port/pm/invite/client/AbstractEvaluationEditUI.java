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
public abstract class AbstractEvaluationEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEvaluationEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDToolBar kDToolBar1;
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continviteReport;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprjName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contevaSolution;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contevaDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntryValid;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntryValid_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntryUnit;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntryUnit_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntryScore;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntryScore_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntryTotal;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntryTotal_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbasePrice;
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
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinviteReport;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtprjName;
    protected com.kingdee.bos.ctrl.swing.KDComboBox evaSolution;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkevaDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable3;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtbasePrice;
    protected com.kingdee.eas.port.pm.invite.EvaluationInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractEvaluationEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEvaluationEditUI.class.getName());
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
        this.kDToolBar1 = new com.kingdee.bos.ctrl.swing.KDToolBar();
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
        this.continviteReport = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprjName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contevaSolution = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contevaDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntryValid = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntryUnit = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntryScore = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntryTotal = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contbasePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.prmtinviteReport = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtprjName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.evaSolution = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkevaDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTable2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTable3 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtbasePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDToolBar1.setName("kDToolBar1");
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
        this.continviteReport.setName("continviteReport");
        this.contprjName.setName("contprjName");
        this.contevaSolution.setName("contevaSolution");
        this.contevaDate.setName("contevaDate");
        this.kdtEntryValid.setName("kdtEntryValid");
        this.kDContainer1.setName("kDContainer1");
        this.kdtEntryUnit.setName("kdtEntryUnit");
        this.kDContainer2.setName("kDContainer2");
        this.kdtEntryScore.setName("kdtEntryScore");
        this.kDContainer3.setName("kDContainer3");
        this.kdtEntryTotal.setName("kdtEntryTotal");
        this.contbasePrice.setName("contbasePrice");
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
        this.prmtinviteReport.setName("prmtinviteReport");
        this.txtprjName.setName("txtprjName");
        this.evaSolution.setName("evaSolution");
        this.pkevaDate.setName("pkevaDate");
        this.kDTable1.setName("kDTable1");
        this.kDTable2.setName("kDTable2");
        this.kDTable3.setName("kDTable3");
        this.txtbasePrice.setName("txtbasePrice");
        // CoreUI
        // kDToolBar1
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
        // continviteReport		
        this.continviteReport.setBoundLabelText(resHelper.getString("continviteReport.boundLabelText"));		
        this.continviteReport.setBoundLabelLength(100);		
        this.continviteReport.setBoundLabelUnderline(true);		
        this.continviteReport.setVisible(true);
        // contprjName		
        this.contprjName.setBoundLabelText(resHelper.getString("contprjName.boundLabelText"));		
        this.contprjName.setBoundLabelLength(100);		
        this.contprjName.setBoundLabelUnderline(true);		
        this.contprjName.setVisible(true);
        // contevaSolution		
        this.contevaSolution.setBoundLabelText(resHelper.getString("contevaSolution.boundLabelText"));		
        this.contevaSolution.setBoundLabelLength(100);		
        this.contevaSolution.setBoundLabelUnderline(true);		
        this.contevaSolution.setVisible(true);
        // contevaDate		
        this.contevaDate.setBoundLabelText(resHelper.getString("contevaDate.boundLabelText"));		
        this.contevaDate.setBoundLabelLength(100);		
        this.contevaDate.setBoundLabelUnderline(true);		
        this.contevaDate.setVisible(true);
        // kdtEntryValid
		String kdtEntryValidStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"judges\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"indicators\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"valid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{judges}</t:Cell><t:Cell>$Resource{indicators}</t:Cell><t:Cell>$Resource{valid}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntryValid.setFormatXml(resHelper.translateString("kdtEntryValid",kdtEntryValidStrXML));

                this.kdtEntryValid.putBindContents("editData",new String[] {"seq","judges","indicators","valid"});


        this.kdtEntryValid.checkParsed();
        KDTextField kdtEntryValid_judges_TextField = new KDTextField();
        kdtEntryValid_judges_TextField.setName("kdtEntryValid_judges_TextField");
        kdtEntryValid_judges_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryValid_judges_CellEditor = new KDTDefaultCellEditor(kdtEntryValid_judges_TextField);
        this.kdtEntryValid.getColumn("judges").setEditor(kdtEntryValid_judges_CellEditor);
        KDTextField kdtEntryValid_indicators_TextField = new KDTextField();
        kdtEntryValid_indicators_TextField.setName("kdtEntryValid_indicators_TextField");
        kdtEntryValid_indicators_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryValid_indicators_CellEditor = new KDTDefaultCellEditor(kdtEntryValid_indicators_TextField);
        this.kdtEntryValid.getColumn("indicators").setEditor(kdtEntryValid_indicators_CellEditor);
        KDTextField kdtEntryValid_valid_TextField = new KDTextField();
        kdtEntryValid_valid_TextField.setName("kdtEntryValid_valid_TextField");
        kdtEntryValid_valid_TextField.setMaxLength(200);
        KDTDefaultCellEditor kdtEntryValid_valid_CellEditor = new KDTDefaultCellEditor(kdtEntryValid_valid_TextField);
        this.kdtEntryValid.getColumn("valid").setEditor(kdtEntryValid_valid_CellEditor);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kdtEntryUnit
		String kdtEntryUnitStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"enterprise\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{enterprise}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntryUnit.setFormatXml(resHelper.translateString("kdtEntryUnit",kdtEntryUnitStrXML));

                this.kdtEntryUnit.putBindContents("editData",new String[] {"seq","enterprise"});


        this.kdtEntryUnit.checkParsed();
        KDTextField kdtEntryUnit_enterprise_TextField = new KDTextField();
        kdtEntryUnit_enterprise_TextField.setName("kdtEntryUnit_enterprise_TextField");
        kdtEntryUnit_enterprise_TextField.setMaxLength(200);
        KDTDefaultCellEditor kdtEntryUnit_enterprise_CellEditor = new KDTDefaultCellEditor(kdtEntryUnit_enterprise_TextField);
        this.kdtEntryUnit.getColumn("enterprise").setEditor(kdtEntryUnit_enterprise_CellEditor);
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kdtEntryScore
		String kdtEntryScoreStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"judges\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"indicators\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fullScore\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"score\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{judges}</t:Cell><t:Cell>$Resource{indicators}</t:Cell><t:Cell>$Resource{fullScore}</t:Cell><t:Cell>$Resource{score}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntryScore.setFormatXml(resHelper.translateString("kdtEntryScore",kdtEntryScoreStrXML));

                this.kdtEntryScore.putBindContents("editData",new String[] {"seq","judges","indicators","fullScore","score"});


        this.kdtEntryScore.checkParsed();
        KDTextField kdtEntryScore_judges_TextField = new KDTextField();
        kdtEntryScore_judges_TextField.setName("kdtEntryScore_judges_TextField");
        kdtEntryScore_judges_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryScore_judges_CellEditor = new KDTDefaultCellEditor(kdtEntryScore_judges_TextField);
        this.kdtEntryScore.getColumn("judges").setEditor(kdtEntryScore_judges_CellEditor);
        KDTextField kdtEntryScore_indicators_TextField = new KDTextField();
        kdtEntryScore_indicators_TextField.setName("kdtEntryScore_indicators_TextField");
        kdtEntryScore_indicators_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryScore_indicators_CellEditor = new KDTDefaultCellEditor(kdtEntryScore_indicators_TextField);
        this.kdtEntryScore.getColumn("indicators").setEditor(kdtEntryScore_indicators_CellEditor);
        KDTextField kdtEntryScore_fullScore_TextField = new KDTextField();
        kdtEntryScore_fullScore_TextField.setName("kdtEntryScore_fullScore_TextField");
        kdtEntryScore_fullScore_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryScore_fullScore_CellEditor = new KDTDefaultCellEditor(kdtEntryScore_fullScore_TextField);
        this.kdtEntryScore.getColumn("fullScore").setEditor(kdtEntryScore_fullScore_CellEditor);
        KDTextField kdtEntryScore_score_TextField = new KDTextField();
        kdtEntryScore_score_TextField.setName("kdtEntryScore_score_TextField");
        kdtEntryScore_score_TextField.setMaxLength(200);
        KDTDefaultCellEditor kdtEntryScore_score_CellEditor = new KDTDefaultCellEditor(kdtEntryScore_score_TextField);
        this.kdtEntryScore.getColumn("score").setEditor(kdtEntryScore_score_CellEditor);
        // kDContainer3		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));
        // kdtEntryTotal
		String kdtEntryTotalStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"indicators\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"result\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{indicators}</t:Cell><t:Cell>$Resource{result}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntryTotal.setFormatXml(resHelper.translateString("kdtEntryTotal",kdtEntryTotalStrXML));

                this.kdtEntryTotal.putBindContents("editData",new String[] {"seq","indicators","result"});


        this.kdtEntryTotal.checkParsed();
        KDTextField kdtEntryTotal_indicators_TextField = new KDTextField();
        kdtEntryTotal_indicators_TextField.setName("kdtEntryTotal_indicators_TextField");
        kdtEntryTotal_indicators_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryTotal_indicators_CellEditor = new KDTDefaultCellEditor(kdtEntryTotal_indicators_TextField);
        this.kdtEntryTotal.getColumn("indicators").setEditor(kdtEntryTotal_indicators_CellEditor);
        KDTextField kdtEntryTotal_result_TextField = new KDTextField();
        kdtEntryTotal_result_TextField.setName("kdtEntryTotal_result_TextField");
        kdtEntryTotal_result_TextField.setMaxLength(200);
        KDTDefaultCellEditor kdtEntryTotal_result_CellEditor = new KDTDefaultCellEditor(kdtEntryTotal_result_TextField);
        this.kdtEntryTotal.getColumn("result").setEditor(kdtEntryTotal_result_CellEditor);
        // contbasePrice		
        this.contbasePrice.setBoundLabelText(resHelper.getString("contbasePrice.boundLabelText"));		
        this.contbasePrice.setBoundLabelLength(100);		
        this.contbasePrice.setBoundLabelUnderline(true);		
        this.contbasePrice.setVisible(true);
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
        // prmtinviteReport		
        this.prmtinviteReport.setQueryInfo("com.kingdee.eas.port.pm.invite.app.InviteReportQuery");		
        this.prmtinviteReport.setEditable(true);		
        this.prmtinviteReport.setDisplayFormat("$reportName$");		
        this.prmtinviteReport.setEditFormat("$number$");		
        this.prmtinviteReport.setCommitFormat("$number$");		
        this.prmtinviteReport.setRequired(false);
        prmtinviteReport.addDataChangeListener(new DataChangeListener() {
		public void dataChanged(DataChangeEvent e) {
			try {
				prmtinviteReport_Changed();
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        this.prmtinviteReport.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtinviteReport_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtprjName		
        this.txtprjName.setHorizontalAlignment(2);		
        this.txtprjName.setMaxLength(80);		
        this.txtprjName.setRequired(false);
        // evaSolution		
        this.evaSolution.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.pm.invite.judgeSolution").toArray());		
        this.evaSolution.setRequired(false);
        this.evaSolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    evaSolution_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // pkevaDate		
        this.pkevaDate.setRequired(false);
        // kDTable1
        this.kDTable1.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kDTable1_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        this.kDTable1.checkParsed();
        // kDTable2

        

        this.kDTable2.checkParsed();
        // kDTable3

        

        this.kDTable3.checkParsed();
        // txtbasePrice		
        this.txtbasePrice.setVisible(true);		
        this.txtbasePrice.setHorizontalAlignment(2);		
        this.txtbasePrice.setDataType(1);		
        this.txtbasePrice.setSupportedEmpty(true);		
        this.txtbasePrice.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtbasePrice.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtbasePrice.setPrecision(10);		
        this.txtbasePrice.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {comboStatus,comboBizStatus,pkAuditTime,txtNumber,pkBizDate,txtDescription,prmtAuditor,prmtCreator,pkCreateTime,prmtLastUpdateUser,pkLastUpdateTime,prmtCU,prmtinviteReport,txtprjName,evaSolution,pkevaDate,kdtEntryValid,kdtEntryScore,kdtEntryUnit,txtbasePrice}));
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
		list.add(this.kDToolBar1);
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
        contCreator.setBounds(new Rectangle(35, 579, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(35, 579, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(35, 605, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(35, 605, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(372, 581, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(372, 581, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(372, 607, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(372, 607, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(303, 557, 270, 19));
        this.add(contCU, new KDLayout.Constraints(303, 557, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(34, 13, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(34, 13, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(12, 560, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(12, 560, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(754, 75, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(754, 75, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(708, 564, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(708, 564, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(32, 61, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(32, 61, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizStatus.setBounds(new Rectangle(708, 611, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(708, 611, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(708, 590, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(708, 590, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        continviteReport.setBounds(new Rectangle(328, 13, 270, 19));
        this.add(continviteReport, new KDLayout.Constraints(328, 13, 270, 19, 0));
        contprjName.setBounds(new Rectangle(649, 10, 270, 19));
        this.add(contprjName, new KDLayout.Constraints(649, 10, 270, 19, 0));
        contevaSolution.setBounds(new Rectangle(32, 37, 270, 19));
        this.add(contevaSolution, new KDLayout.Constraints(32, 37, 270, 19, 0));
        contevaDate.setBounds(new Rectangle(327, 37, 270, 19));
        this.add(contevaDate, new KDLayout.Constraints(327, 37, 270, 19, 0));
        kdtEntryValid.setBounds(new Rectangle(765, 215, 252, 113));
        kdtEntryValid_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntryValid,new com.kingdee.eas.port.pm.invite.EvaluationEntryValidInfo(),null,false);
        this.add(kdtEntryValid_detailPanel, new KDLayout.Constraints(765, 215, 252, 113, 0));
        kDContainer1.setBounds(new Rectangle(32, 81, 704, 181));
        this.add(kDContainer1, new KDLayout.Constraints(32, 81, 704, 181, 0));
        kdtEntryUnit.setBounds(new Rectangle(765, 119, 259, 94));
        kdtEntryUnit_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntryUnit,new com.kingdee.eas.port.pm.invite.EvaluationEntryUnitInfo(),null,false);
        this.add(kdtEntryUnit_detailPanel, new KDLayout.Constraints(765, 119, 259, 94, 0));
        kDContainer2.setBounds(new Rectangle(32, 269, 703, 158));
        this.add(kDContainer2, new KDLayout.Constraints(32, 269, 703, 158, 0));
        kdtEntryScore.setBounds(new Rectangle(765, 334, 265, 91));
        kdtEntryScore_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntryScore,new com.kingdee.eas.port.pm.invite.EvaluationEntryScoreInfo(),null,false);
        this.add(kdtEntryScore_detailPanel, new KDLayout.Constraints(765, 334, 265, 91, 0));
        kDContainer3.setBounds(new Rectangle(32, 438, 702, 106));
        this.add(kDContainer3, new KDLayout.Constraints(32, 438, 702, 106, 0));
        kdtEntryTotal.setBounds(new Rectangle(765, 432, 250, 113));
        kdtEntryTotal_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntryTotal,new com.kingdee.eas.port.pm.invite.EvaluationEntryTotalInfo(),null,false);
        this.add(kdtEntryTotal_detailPanel, new KDLayout.Constraints(765, 432, 250, 113, 0));
        contbasePrice.setBounds(new Rectangle(649, 36, 270, 19));
        this.add(contbasePrice, new KDLayout.Constraints(649, 36, 270, 19, 0));
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
        //continviteReport
        continviteReport.setBoundEditor(prmtinviteReport);
        //contprjName
        contprjName.setBoundEditor(txtprjName);
        //contevaSolution
        contevaSolution.setBoundEditor(evaSolution);
        //contevaDate
        contevaDate.setBoundEditor(pkevaDate);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDTable1, BorderLayout.CENTER);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(kDTable2, BorderLayout.CENTER);
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer3.getContentPane().add(kDTable3, BorderLayout.CENTER);
        //contbasePrice
        contbasePrice.setBoundEditor(txtbasePrice);

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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnCloud);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);



    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("EntryValid.seq", int.class, this.kdtEntryValid, "seq.text");
		dataBinder.registerBinding("EntryValid", com.kingdee.eas.port.pm.invite.EvaluationEntryValidInfo.class, this.kdtEntryValid, "userObject");
		dataBinder.registerBinding("EntryValid.indicators", String.class, this.kdtEntryValid, "indicators.text");
		dataBinder.registerBinding("EntryValid.valid", String.class, this.kdtEntryValid, "valid.text");
		dataBinder.registerBinding("EntryValid.judges", String.class, this.kdtEntryValid, "judges.text");
		dataBinder.registerBinding("EntryUnit.seq", int.class, this.kdtEntryUnit, "seq.text");
		dataBinder.registerBinding("EntryUnit", com.kingdee.eas.port.pm.invite.EvaluationEntryUnitInfo.class, this.kdtEntryUnit, "userObject");
		dataBinder.registerBinding("EntryUnit.enterprise", String.class, this.kdtEntryUnit, "enterprise.text");
		dataBinder.registerBinding("EntryScore.seq", int.class, this.kdtEntryScore, "seq.text");
		dataBinder.registerBinding("EntryScore", com.kingdee.eas.port.pm.invite.EvaluationEntryScoreInfo.class, this.kdtEntryScore, "userObject");
		dataBinder.registerBinding("EntryScore.judges", String.class, this.kdtEntryScore, "judges.text");
		dataBinder.registerBinding("EntryScore.indicators", String.class, this.kdtEntryScore, "indicators.text");
		dataBinder.registerBinding("EntryScore.fullScore", String.class, this.kdtEntryScore, "fullScore.text");
		dataBinder.registerBinding("EntryScore.score", String.class, this.kdtEntryScore, "score.text");
		dataBinder.registerBinding("EntryTotal.seq", int.class, this.kdtEntryTotal, "seq.text");
		dataBinder.registerBinding("EntryTotal", com.kingdee.eas.port.pm.invite.EvaluationEntryTotalInfo.class, this.kdtEntryTotal, "userObject");
		dataBinder.registerBinding("EntryTotal.indicators", String.class, this.kdtEntryTotal, "indicators.text");
		dataBinder.registerBinding("EntryTotal.result", String.class, this.kdtEntryTotal, "result.text");
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
		dataBinder.registerBinding("inviteReport", com.kingdee.eas.port.pm.invite.InviteReportInfo.class, this.prmtinviteReport, "data");
		dataBinder.registerBinding("prjName", String.class, this.txtprjName, "text");
		dataBinder.registerBinding("evaSolution", com.kingdee.eas.port.pm.invite.judgeSolution.class, this.evaSolution, "selectedItem");
		dataBinder.registerBinding("evaDate", java.util.Date.class, this.pkevaDate, "value");
		dataBinder.registerBinding("basePrice", java.math.BigDecimal.class, this.txtbasePrice, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invite.app.EvaluationEditUIHandler";
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
        this.editData = (com.kingdee.eas.port.pm.invite.EvaluationInfo)ov;
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
		getValidateHelper().registerBindProperty("EntryValid.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryValid", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryValid.indicators", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryValid.valid", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryValid.judges", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryUnit.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryUnit.enterprise", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryScore.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryScore", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryScore.judges", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryScore.indicators", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryScore.fullScore", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryScore.score", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryTotal.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryTotal.indicators", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryTotal.result", ValidateHelper.ON_SAVE);    
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
		getValidateHelper().registerBindProperty("inviteReport", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prjName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("evaSolution", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("evaDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("basePrice", ValidateHelper.ON_SAVE);    		
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
     * output prmtinviteReport_dataChanged method
     */
    protected void prmtinviteReport_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output evaSolution_actionPerformed method
     */
    protected void evaSolution_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDTable1_editStopped method
     */
    protected void kDTable1_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }


    /**
     * output prmtinviteReport_Changed() method
     */
    public void prmtinviteReport_Changed() throws Exception
    {
        System.out.println("prmtinviteReport_Changed() Function is executed!");
            txtprjName.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtinviteReport.getData(),"proName.name")));

    evaSolution.setSelectedItem(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtinviteReport.getData(),"judgeSolution"));


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
    	sic.add(new SelectorItemInfo("EntryValid.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EntryValid.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("EntryValid.indicators"));
    	sic.add(new SelectorItemInfo("EntryValid.valid"));
    	sic.add(new SelectorItemInfo("EntryValid.judges"));
    	sic.add(new SelectorItemInfo("EntryUnit.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EntryUnit.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("EntryUnit.enterprise"));
    	sic.add(new SelectorItemInfo("EntryScore.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EntryScore.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("EntryScore.judges"));
    	sic.add(new SelectorItemInfo("EntryScore.indicators"));
    	sic.add(new SelectorItemInfo("EntryScore.fullScore"));
    	sic.add(new SelectorItemInfo("EntryScore.score"));
    	sic.add(new SelectorItemInfo("EntryTotal.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EntryTotal.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("EntryTotal.indicators"));
    	sic.add(new SelectorItemInfo("EntryTotal.result"));
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
			sic.add(new SelectorItemInfo("inviteReport.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("inviteReport.id"));
        	sic.add(new SelectorItemInfo("inviteReport.number"));
        	sic.add(new SelectorItemInfo("inviteReport.reportName"));
		}
        sic.add(new SelectorItemInfo("prjName"));
        sic.add(new SelectorItemInfo("evaSolution"));
        sic.add(new SelectorItemInfo("evaDate"));
        sic.add(new SelectorItemInfo("basePrice"));
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
        return new MetaDataPK("com.kingdee.eas.port.pm.invite.client", "EvaluationEditUI");
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
        return com.kingdee.eas.port.pm.invite.client.EvaluationEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invite.EvaluationFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invite.EvaluationInfo objectValue = new com.kingdee.eas.port.pm.invite.EvaluationInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/invite/Evaluation";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.invite.app.EvaluationQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntryValid;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("evaSolution","1");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}