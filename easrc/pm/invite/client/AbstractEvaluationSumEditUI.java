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
public abstract class AbstractEvaluationSumEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEvaluationSumEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continviteName;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry_detailPanel = null;
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
    protected com.kingdee.bos.ctrl.swing.KDTextField txtinviteName;
    protected com.kingdee.eas.port.pm.invite.EvaluationSumInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractEvaluationSumEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEvaluationSumEditUI.class.getName());
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
        this.continviteReport = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continviteName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
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
        this.txtinviteName = new com.kingdee.bos.ctrl.swing.KDTextField();
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
        this.continviteName.setName("continviteName");
        this.kdtEntry.setName("kdtEntry");
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
        this.txtinviteName.setName("txtinviteName");
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
        // continviteReport		
        this.continviteReport.setBoundLabelText(resHelper.getString("continviteReport.boundLabelText"));		
        this.continviteReport.setBoundLabelLength(100);		
        this.continviteReport.setBoundLabelUnderline(true);		
        this.continviteReport.setVisible(true);
        // continviteName		
        this.continviteName.setBoundLabelText(resHelper.getString("continviteName.boundLabelText"));		
        this.continviteName.setBoundLabelLength(100);		
        this.continviteName.setBoundLabelUnderline(true);		
        this.continviteName.setVisible(true);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"unitName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"quality\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"projectLimit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"quote\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"avgAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"benchmarkAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"difference\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"diffRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"deduct\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"businessSorce\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"technologyScore\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"totalScore\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"index\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{unitName}</t:Cell><t:Cell>$Resource{quality}</t:Cell><t:Cell>$Resource{projectLimit}</t:Cell><t:Cell>$Resource{quote}</t:Cell><t:Cell>$Resource{avgAmount}</t:Cell><t:Cell>$Resource{benchmarkAmount}</t:Cell><t:Cell>$Resource{difference}</t:Cell><t:Cell>$Resource{diffRate}</t:Cell><t:Cell>$Resource{deduct}</t:Cell><t:Cell>$Resource{businessSorce}</t:Cell><t:Cell>$Resource{technologyScore}</t:Cell><t:Cell>$Resource{totalScore}</t:Cell><t:Cell>$Resource{index}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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


                this.kdtEntry.putBindContents("editData",new String[] {"seq","unitName","quality","projectLimit","quote","avgAmount","benchmarkAmount","difference","diffRate","deduct","businessSorce","technologyScore","totalScore","index","beizhu"});


        this.kdtEntry.checkParsed();
        final KDBizPromptBox kdtEntry_unitName_PromptBox = new KDBizPromptBox();
        kdtEntry_unitName_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierDefaultQuery");
        kdtEntry_unitName_PromptBox.setVisible(true);
        kdtEntry_unitName_PromptBox.setEditable(true);
        kdtEntry_unitName_PromptBox.setDisplayFormat("$number$");
        kdtEntry_unitName_PromptBox.setEditFormat("$number$");
        kdtEntry_unitName_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_unitName_CellEditor = new KDTDefaultCellEditor(kdtEntry_unitName_PromptBox);
        this.kdtEntry.getColumn("unitName").setEditor(kdtEntry_unitName_CellEditor);
        ObjectValueRender kdtEntry_unitName_OVR = new ObjectValueRender();
        kdtEntry_unitName_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("unitName").setRenderer(kdtEntry_unitName_OVR);
        KDTextField kdtEntry_quality_TextField = new KDTextField();
        kdtEntry_quality_TextField.setName("kdtEntry_quality_TextField");
        kdtEntry_quality_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_quality_CellEditor = new KDTDefaultCellEditor(kdtEntry_quality_TextField);
        this.kdtEntry.getColumn("quality").setEditor(kdtEntry_quality_CellEditor);
        KDFormattedTextField kdtEntry_projectLimit_TextField = new KDFormattedTextField();
        kdtEntry_projectLimit_TextField.setName("kdtEntry_projectLimit_TextField");
        kdtEntry_projectLimit_TextField.setVisible(true);
        kdtEntry_projectLimit_TextField.setEditable(true);
        kdtEntry_projectLimit_TextField.setHorizontalAlignment(2);
        kdtEntry_projectLimit_TextField.setDataType(1);
        	kdtEntry_projectLimit_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E27"));
        	kdtEntry_projectLimit_TextField.setMaximumValue(new java.math.BigDecimal("1.0E27"));
        kdtEntry_projectLimit_TextField.setPrecision(1);
        KDTDefaultCellEditor kdtEntry_projectLimit_CellEditor = new KDTDefaultCellEditor(kdtEntry_projectLimit_TextField);
        this.kdtEntry.getColumn("projectLimit").setEditor(kdtEntry_projectLimit_CellEditor);
        KDFormattedTextField kdtEntry_quote_TextField = new KDFormattedTextField();
        kdtEntry_quote_TextField.setName("kdtEntry_quote_TextField");
        kdtEntry_quote_TextField.setVisible(true);
        kdtEntry_quote_TextField.setEditable(true);
        kdtEntry_quote_TextField.setHorizontalAlignment(2);
        kdtEntry_quote_TextField.setDataType(1);
        	kdtEntry_quote_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry_quote_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry_quote_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_quote_CellEditor = new KDTDefaultCellEditor(kdtEntry_quote_TextField);
        this.kdtEntry.getColumn("quote").setEditor(kdtEntry_quote_CellEditor);
        KDFormattedTextField kdtEntry_avgAmount_TextField = new KDFormattedTextField();
        kdtEntry_avgAmount_TextField.setName("kdtEntry_avgAmount_TextField");
        kdtEntry_avgAmount_TextField.setVisible(true);
        kdtEntry_avgAmount_TextField.setEditable(true);
        kdtEntry_avgAmount_TextField.setHorizontalAlignment(2);
        kdtEntry_avgAmount_TextField.setDataType(1);
        	kdtEntry_avgAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry_avgAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry_avgAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_avgAmount_CellEditor = new KDTDefaultCellEditor(kdtEntry_avgAmount_TextField);
        this.kdtEntry.getColumn("avgAmount").setEditor(kdtEntry_avgAmount_CellEditor);
        KDFormattedTextField kdtEntry_benchmarkAmount_TextField = new KDFormattedTextField();
        kdtEntry_benchmarkAmount_TextField.setName("kdtEntry_benchmarkAmount_TextField");
        kdtEntry_benchmarkAmount_TextField.setVisible(true);
        kdtEntry_benchmarkAmount_TextField.setEditable(true);
        kdtEntry_benchmarkAmount_TextField.setHorizontalAlignment(2);
        kdtEntry_benchmarkAmount_TextField.setDataType(1);
        	kdtEntry_benchmarkAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry_benchmarkAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry_benchmarkAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_benchmarkAmount_CellEditor = new KDTDefaultCellEditor(kdtEntry_benchmarkAmount_TextField);
        this.kdtEntry.getColumn("benchmarkAmount").setEditor(kdtEntry_benchmarkAmount_CellEditor);
        KDTextField kdtEntry_difference_TextField = new KDTextField();
        kdtEntry_difference_TextField.setName("kdtEntry_difference_TextField");
        kdtEntry_difference_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_difference_CellEditor = new KDTDefaultCellEditor(kdtEntry_difference_TextField);
        this.kdtEntry.getColumn("difference").setEditor(kdtEntry_difference_CellEditor);
        KDFormattedTextField kdtEntry_diffRate_TextField = new KDFormattedTextField();
        kdtEntry_diffRate_TextField.setName("kdtEntry_diffRate_TextField");
        kdtEntry_diffRate_TextField.setVisible(true);
        kdtEntry_diffRate_TextField.setEditable(true);
        kdtEntry_diffRate_TextField.setHorizontalAlignment(2);
        kdtEntry_diffRate_TextField.setDataType(1);
        	kdtEntry_diffRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry_diffRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry_diffRate_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_diffRate_CellEditor = new KDTDefaultCellEditor(kdtEntry_diffRate_TextField);
        this.kdtEntry.getColumn("diffRate").setEditor(kdtEntry_diffRate_CellEditor);
        KDFormattedTextField kdtEntry_deduct_TextField = new KDFormattedTextField();
        kdtEntry_deduct_TextField.setName("kdtEntry_deduct_TextField");
        kdtEntry_deduct_TextField.setVisible(true);
        kdtEntry_deduct_TextField.setEditable(true);
        kdtEntry_deduct_TextField.setHorizontalAlignment(2);
        kdtEntry_deduct_TextField.setDataType(1);
        	kdtEntry_deduct_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry_deduct_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry_deduct_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_deduct_CellEditor = new KDTDefaultCellEditor(kdtEntry_deduct_TextField);
        this.kdtEntry.getColumn("deduct").setEditor(kdtEntry_deduct_CellEditor);
        KDFormattedTextField kdtEntry_businessSorce_TextField = new KDFormattedTextField();
        kdtEntry_businessSorce_TextField.setName("kdtEntry_businessSorce_TextField");
        kdtEntry_businessSorce_TextField.setVisible(true);
        kdtEntry_businessSorce_TextField.setEditable(true);
        kdtEntry_businessSorce_TextField.setHorizontalAlignment(2);
        kdtEntry_businessSorce_TextField.setDataType(1);
        	kdtEntry_businessSorce_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry_businessSorce_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry_businessSorce_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_businessSorce_CellEditor = new KDTDefaultCellEditor(kdtEntry_businessSorce_TextField);
        this.kdtEntry.getColumn("businessSorce").setEditor(kdtEntry_businessSorce_CellEditor);
        KDFormattedTextField kdtEntry_technologyScore_TextField = new KDFormattedTextField();
        kdtEntry_technologyScore_TextField.setName("kdtEntry_technologyScore_TextField");
        kdtEntry_technologyScore_TextField.setVisible(true);
        kdtEntry_technologyScore_TextField.setEditable(true);
        kdtEntry_technologyScore_TextField.setHorizontalAlignment(2);
        kdtEntry_technologyScore_TextField.setDataType(1);
        	kdtEntry_technologyScore_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry_technologyScore_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry_technologyScore_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_technologyScore_CellEditor = new KDTDefaultCellEditor(kdtEntry_technologyScore_TextField);
        this.kdtEntry.getColumn("technologyScore").setEditor(kdtEntry_technologyScore_CellEditor);
        KDFormattedTextField kdtEntry_totalScore_TextField = new KDFormattedTextField();
        kdtEntry_totalScore_TextField.setName("kdtEntry_totalScore_TextField");
        kdtEntry_totalScore_TextField.setVisible(true);
        kdtEntry_totalScore_TextField.setEditable(true);
        kdtEntry_totalScore_TextField.setHorizontalAlignment(2);
        kdtEntry_totalScore_TextField.setDataType(1);
        	kdtEntry_totalScore_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry_totalScore_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry_totalScore_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_totalScore_CellEditor = new KDTDefaultCellEditor(kdtEntry_totalScore_TextField);
        this.kdtEntry.getColumn("totalScore").setEditor(kdtEntry_totalScore_CellEditor);
        KDFormattedTextField kdtEntry_index_TextField = new KDFormattedTextField();
        kdtEntry_index_TextField.setName("kdtEntry_index_TextField");
        kdtEntry_index_TextField.setVisible(true);
        kdtEntry_index_TextField.setEditable(true);
        kdtEntry_index_TextField.setHorizontalAlignment(2);
        kdtEntry_index_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntry_index_CellEditor = new KDTDefaultCellEditor(kdtEntry_index_TextField);
        this.kdtEntry.getColumn("index").setEditor(kdtEntry_index_CellEditor);
        KDTextArea kdtEntry_beizhu_TextArea = new KDTextArea();
        kdtEntry_beizhu_TextArea.setName("kdtEntry_beizhu_TextArea");
        kdtEntry_beizhu_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtEntry_beizhu_CellEditor = new KDTDefaultCellEditor(kdtEntry_beizhu_TextArea);
        this.kdtEntry.getColumn("beizhu").setEditor(kdtEntry_beizhu_CellEditor);
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
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // prmtinviteReport		
        this.prmtinviteReport.setQueryInfo("com.kingdee.eas.port.pm.invite.app.InviteReportQuery");		
        this.prmtinviteReport.setEditable(true);		
        this.prmtinviteReport.setDisplayFormat("$number$");		
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

        // txtinviteName		
        this.txtinviteName.setHorizontalAlignment(2);		
        this.txtinviteName.setMaxLength(80);		
        this.txtinviteName.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtinviteName,prmtinviteReport,prmtCU,pkLastUpdateTime,prmtLastUpdateUser,kdtEntry,pkCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,pkAuditTime,comboBizStatus,comboStatus}));
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
        contCreator.setBounds(new Rectangle(35, 564, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(35, 564, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(35, 590, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(35, 590, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(371, 564, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(371, 564, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(371, 590, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(371, 590, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(706, 11, 270, 19));
        this.add(contCU, new KDLayout.Constraints(706, 11, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(33, 11, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(33, 11, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(369, 11, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(369, 11, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(33, 35, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(33, 35, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(708, 564, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(708, 564, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(369, 35, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(369, 35, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizStatus.setBounds(new Rectangle(706, 35, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(706, 35, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(708, 590, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(708, 590, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        continviteReport.setBounds(new Rectangle(33, 61, 270, 19));
        this.add(continviteReport, new KDLayout.Constraints(33, 61, 270, 19, 0));
        continviteName.setBounds(new Rectangle(369, 61, 270, 19));
        this.add(continviteName, new KDLayout.Constraints(369, 61, 270, 19, 0));
        kdtEntry.setBounds(new Rectangle(18, 87, 984, 471));
        kdtEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry,new com.kingdee.eas.port.pm.invite.EvaluationSumEntryInfo(),null,false);
        this.add(kdtEntry_detailPanel, new KDLayout.Constraints(18, 87, 984, 471, 0));
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
        //continviteName
        continviteName.setBoundEditor(txtinviteName);

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
		dataBinder.registerBinding("Entry.seq", int.class, this.kdtEntry, "seq.text");
		dataBinder.registerBinding("Entry", com.kingdee.eas.port.pm.invite.EvaluationSumEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("Entry.unitName", java.lang.Object.class, this.kdtEntry, "unitName.text");
		dataBinder.registerBinding("Entry.quality", String.class, this.kdtEntry, "quality.text");
		dataBinder.registerBinding("Entry.projectLimit", java.math.BigDecimal.class, this.kdtEntry, "projectLimit.text");
		dataBinder.registerBinding("Entry.quote", java.math.BigDecimal.class, this.kdtEntry, "quote.text");
		dataBinder.registerBinding("Entry.avgAmount", java.math.BigDecimal.class, this.kdtEntry, "avgAmount.text");
		dataBinder.registerBinding("Entry.benchmarkAmount", java.math.BigDecimal.class, this.kdtEntry, "benchmarkAmount.text");
		dataBinder.registerBinding("Entry.difference", String.class, this.kdtEntry, "difference.text");
		dataBinder.registerBinding("Entry.diffRate", java.math.BigDecimal.class, this.kdtEntry, "diffRate.text");
		dataBinder.registerBinding("Entry.deduct", java.math.BigDecimal.class, this.kdtEntry, "deduct.text");
		dataBinder.registerBinding("Entry.businessSorce", java.math.BigDecimal.class, this.kdtEntry, "businessSorce.text");
		dataBinder.registerBinding("Entry.technologyScore", java.math.BigDecimal.class, this.kdtEntry, "technologyScore.text");
		dataBinder.registerBinding("Entry.totalScore", java.math.BigDecimal.class, this.kdtEntry, "totalScore.text");
		dataBinder.registerBinding("Entry.beizhu", String.class, this.kdtEntry, "beizhu.text");
		dataBinder.registerBinding("Entry.index", int.class, this.kdtEntry, "index.text");
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
		dataBinder.registerBinding("inviteName", String.class, this.txtinviteName, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invite.app.EvaluationSumEditUIHandler";
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
        this.txtinviteName.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.pm.invite.EvaluationSumInfo)ov;
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
		getValidateHelper().registerBindProperty("Entry.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.unitName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.quality", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.projectLimit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.quote", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.avgAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.benchmarkAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.difference", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.diffRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.deduct", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.businessSorce", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.technologyScore", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.totalScore", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.index", ValidateHelper.ON_SAVE);    
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
		getValidateHelper().registerBindProperty("inviteName", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntry_Changed(int rowIndex,int colIndex) method
     */
    public void kdtEntry_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("unitName".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"quality").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"unitName").getValue(),"barCode")));

}


    }

    /**
     * output prmtinviteReport_Changed() method
     */
    public void prmtinviteReport_Changed() throws Exception
    {
        System.out.println("prmtinviteReport_Changed() Function is executed!");
            txtinviteName.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtinviteReport.getData(),"description")));


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
    	sic.add(new SelectorItemInfo("Entry.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.unitName.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Entry.unitName.id"));
			sic.add(new SelectorItemInfo("Entry.unitName.name"));
        	sic.add(new SelectorItemInfo("Entry.unitName.number"));
		}
    	sic.add(new SelectorItemInfo("Entry.quality"));
    	sic.add(new SelectorItemInfo("Entry.projectLimit"));
    	sic.add(new SelectorItemInfo("Entry.quote"));
    	sic.add(new SelectorItemInfo("Entry.avgAmount"));
    	sic.add(new SelectorItemInfo("Entry.benchmarkAmount"));
    	sic.add(new SelectorItemInfo("Entry.difference"));
    	sic.add(new SelectorItemInfo("Entry.diffRate"));
    	sic.add(new SelectorItemInfo("Entry.deduct"));
    	sic.add(new SelectorItemInfo("Entry.businessSorce"));
    	sic.add(new SelectorItemInfo("Entry.technologyScore"));
    	sic.add(new SelectorItemInfo("Entry.totalScore"));
    	sic.add(new SelectorItemInfo("Entry.beizhu"));
    	sic.add(new SelectorItemInfo("Entry.index"));
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
		}
        sic.add(new SelectorItemInfo("inviteName"));
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
        return new MetaDataPK("com.kingdee.eas.port.pm.invite.client", "EvaluationSumEditUI");
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
        return com.kingdee.eas.port.pm.invite.client.EvaluationSumEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invite.EvaluationSumFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invite.EvaluationSumInfo objectValue = new com.kingdee.eas.port.pm.invite.EvaluationSumInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/invite/EvaluationSum";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.invite.app.EvaluationSumQuery");
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