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
public abstract class AbstractEumUseRecordEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEumUseRecordEditUI.class);
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
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEqmUse;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEqmUse_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstaPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUseOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreportTime;
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
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtstaPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtUseOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtreportTime;
    protected com.kingdee.eas.port.equipment.operate.EumUseRecordInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractEumUseRecordEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEumUseRecordEditUI.class.getName());
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
        this.kdtEqmUse = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contstaPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUseOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreportTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.prmtstaPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtUseOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtreportTime = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
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
        this.kdtEqmUse.setName("kdtEqmUse");
        this.contstaPerson.setName("contstaPerson");
        this.contUseOrgUnit.setName("contUseOrgUnit");
        this.contreportTime.setName("contreportTime");
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
        this.prmtstaPerson.setName("prmtstaPerson");
        this.prmtUseOrgUnit.setName("prmtUseOrgUnit");
        this.prmtreportTime.setName("prmtreportTime");
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
        this.contCU.setVisible(false);
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
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);		
        this.contStatus.setVisible(false);
        // contBizStatus		
        this.contBizStatus.setBoundLabelText(resHelper.getString("contBizStatus.boundLabelText"));		
        this.contBizStatus.setBoundLabelLength(100);		
        this.contBizStatus.setBoundLabelUnderline(true);		
        this.contBizStatus.setEnabled(false);		
        this.contBizStatus.setVisible(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // kdtEqmUse
		String kdtEqmUseStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"eqmName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"eqmType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"eqmCategory\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"modelType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"powerCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"powerUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"czCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"czUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"powerUnitCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"puUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"CostType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"eqmTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"EventTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"usageRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"UseTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"faultRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{eqmName}</t:Cell><t:Cell>$Resource{eqmType}</t:Cell><t:Cell>$Resource{eqmCategory}</t:Cell><t:Cell>$Resource{modelType}</t:Cell><t:Cell>$Resource{powerCost}</t:Cell><t:Cell>$Resource{powerUnit}</t:Cell><t:Cell>$Resource{czCost}</t:Cell><t:Cell>$Resource{czUnit}</t:Cell><t:Cell>$Resource{powerUnitCost}</t:Cell><t:Cell>$Resource{puUnit}</t:Cell><t:Cell>$Resource{CostType}</t:Cell><t:Cell>$Resource{eqmTime}</t:Cell><t:Cell>$Resource{EventTime}</t:Cell><t:Cell>$Resource{usageRate}</t:Cell><t:Cell>$Resource{UseTime}</t:Cell><t:Cell>$Resource{faultRate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEqmUse.setFormatXml(resHelper.translateString("kdtEqmUse",kdtEqmUseStrXML));

                this.kdtEqmUse.putBindContents("editData",new String[] {"seq","eqmName","eqmType","eqmCategory","modelType","powerCost","powerUnit","czCost","czUnit","powerUnitCost","puUnit","CostType","eqmTime","EventTime","usageRate","UseTime","faultRate"});


        this.kdtEqmUse.checkParsed();
        KDTextField kdtEqmUse_eqmName_TextField = new KDTextField();
        kdtEqmUse_eqmName_TextField.setName("kdtEqmUse_eqmName_TextField");
        kdtEqmUse_eqmName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEqmUse_eqmName_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_eqmName_TextField);
        this.kdtEqmUse.getColumn("eqmName").setEditor(kdtEqmUse_eqmName_CellEditor);
        KDTextField kdtEqmUse_eqmType_TextField = new KDTextField();
        kdtEqmUse_eqmType_TextField.setName("kdtEqmUse_eqmType_TextField");
        kdtEqmUse_eqmType_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEqmUse_eqmType_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_eqmType_TextField);
        this.kdtEqmUse.getColumn("eqmType").setEditor(kdtEqmUse_eqmType_CellEditor);
        KDTextField kdtEqmUse_eqmCategory_TextField = new KDTextField();
        kdtEqmUse_eqmCategory_TextField.setName("kdtEqmUse_eqmCategory_TextField");
        kdtEqmUse_eqmCategory_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEqmUse_eqmCategory_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_eqmCategory_TextField);
        this.kdtEqmUse.getColumn("eqmCategory").setEditor(kdtEqmUse_eqmCategory_CellEditor);
        KDTextField kdtEqmUse_modelType_TextField = new KDTextField();
        kdtEqmUse_modelType_TextField.setName("kdtEqmUse_modelType_TextField");
        kdtEqmUse_modelType_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEqmUse_modelType_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_modelType_TextField);
        this.kdtEqmUse.getColumn("modelType").setEditor(kdtEqmUse_modelType_CellEditor);
        KDFormattedTextField kdtEqmUse_powerCost_TextField = new KDFormattedTextField();
        kdtEqmUse_powerCost_TextField.setName("kdtEqmUse_powerCost_TextField");
        kdtEqmUse_powerCost_TextField.setVisible(true);
        kdtEqmUse_powerCost_TextField.setEditable(true);
        kdtEqmUse_powerCost_TextField.setHorizontalAlignment(2);
        kdtEqmUse_powerCost_TextField.setDataType(1);
        	kdtEqmUse_powerCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEqmUse_powerCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEqmUse_powerCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEqmUse_powerCost_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_powerCost_TextField);
        this.kdtEqmUse.getColumn("powerCost").setEditor(kdtEqmUse_powerCost_CellEditor);
        final KDBizPromptBox kdtEqmUse_powerUnit_PromptBox = new KDBizPromptBox();
        kdtEqmUse_powerUnit_PromptBox.setQueryInfo("com.kingdee.eas.port.equipment.base.app.PowerUnitQuery");
        kdtEqmUse_powerUnit_PromptBox.setVisible(true);
        kdtEqmUse_powerUnit_PromptBox.setEditable(true);
        kdtEqmUse_powerUnit_PromptBox.setDisplayFormat("$number$");
        kdtEqmUse_powerUnit_PromptBox.setEditFormat("$number$");
        kdtEqmUse_powerUnit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEqmUse_powerUnit_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_powerUnit_PromptBox);
        this.kdtEqmUse.getColumn("powerUnit").setEditor(kdtEqmUse_powerUnit_CellEditor);
        ObjectValueRender kdtEqmUse_powerUnit_OVR = new ObjectValueRender();
        kdtEqmUse_powerUnit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEqmUse.getColumn("powerUnit").setRenderer(kdtEqmUse_powerUnit_OVR);
        KDFormattedTextField kdtEqmUse_czCost_TextField = new KDFormattedTextField();
        kdtEqmUse_czCost_TextField.setName("kdtEqmUse_czCost_TextField");
        kdtEqmUse_czCost_TextField.setVisible(true);
        kdtEqmUse_czCost_TextField.setEditable(true);
        kdtEqmUse_czCost_TextField.setHorizontalAlignment(2);
        kdtEqmUse_czCost_TextField.setDataType(1);
        	kdtEqmUse_czCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEqmUse_czCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEqmUse_czCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEqmUse_czCost_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_czCost_TextField);
        this.kdtEqmUse.getColumn("czCost").setEditor(kdtEqmUse_czCost_CellEditor);
        final KDBizPromptBox kdtEqmUse_czUnit_PromptBox = new KDBizPromptBox();
        kdtEqmUse_czUnit_PromptBox.setQueryInfo("com.kingdee.eas.port.equipment.base.app.CzUnitQuery");
        kdtEqmUse_czUnit_PromptBox.setVisible(true);
        kdtEqmUse_czUnit_PromptBox.setEditable(true);
        kdtEqmUse_czUnit_PromptBox.setDisplayFormat("$number$");
        kdtEqmUse_czUnit_PromptBox.setEditFormat("$number$");
        kdtEqmUse_czUnit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEqmUse_czUnit_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_czUnit_PromptBox);
        this.kdtEqmUse.getColumn("czUnit").setEditor(kdtEqmUse_czUnit_CellEditor);
        ObjectValueRender kdtEqmUse_czUnit_OVR = new ObjectValueRender();
        kdtEqmUse_czUnit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEqmUse.getColumn("czUnit").setRenderer(kdtEqmUse_czUnit_OVR);
        KDFormattedTextField kdtEqmUse_powerUnitCost_TextField = new KDFormattedTextField();
        kdtEqmUse_powerUnitCost_TextField.setName("kdtEqmUse_powerUnitCost_TextField");
        kdtEqmUse_powerUnitCost_TextField.setVisible(true);
        kdtEqmUse_powerUnitCost_TextField.setEditable(true);
        kdtEqmUse_powerUnitCost_TextField.setHorizontalAlignment(2);
        kdtEqmUse_powerUnitCost_TextField.setDataType(1);
        	kdtEqmUse_powerUnitCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEqmUse_powerUnitCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEqmUse_powerUnitCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEqmUse_powerUnitCost_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_powerUnitCost_TextField);
        this.kdtEqmUse.getColumn("powerUnitCost").setEditor(kdtEqmUse_powerUnitCost_CellEditor);
        final KDBizPromptBox kdtEqmUse_puUnit_PromptBox = new KDBizPromptBox();
        kdtEqmUse_puUnit_PromptBox.setQueryInfo("com.kingdee.eas.port.equipment.base.app.PuUnitQuery");
        kdtEqmUse_puUnit_PromptBox.setVisible(true);
        kdtEqmUse_puUnit_PromptBox.setEditable(true);
        kdtEqmUse_puUnit_PromptBox.setDisplayFormat("$number$");
        kdtEqmUse_puUnit_PromptBox.setEditFormat("$number$");
        kdtEqmUse_puUnit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEqmUse_puUnit_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_puUnit_PromptBox);
        this.kdtEqmUse.getColumn("puUnit").setEditor(kdtEqmUse_puUnit_CellEditor);
        ObjectValueRender kdtEqmUse_puUnit_OVR = new ObjectValueRender();
        kdtEqmUse_puUnit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEqmUse.getColumn("puUnit").setRenderer(kdtEqmUse_puUnit_OVR);
        KDComboBox kdtEqmUse_CostType_ComboBox = new KDComboBox();
        kdtEqmUse_CostType_ComboBox.setName("kdtEqmUse_CostType_ComboBox");
        kdtEqmUse_CostType_ComboBox.setVisible(true);
        kdtEqmUse_CostType_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.equipment.base.enumbase.CostType").toArray());
        KDTDefaultCellEditor kdtEqmUse_CostType_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_CostType_ComboBox);
        this.kdtEqmUse.getColumn("CostType").setEditor(kdtEqmUse_CostType_CellEditor);
        KDFormattedTextField kdtEqmUse_eqmTime_TextField = new KDFormattedTextField();
        kdtEqmUse_eqmTime_TextField.setName("kdtEqmUse_eqmTime_TextField");
        kdtEqmUse_eqmTime_TextField.setVisible(true);
        kdtEqmUse_eqmTime_TextField.setEditable(true);
        kdtEqmUse_eqmTime_TextField.setHorizontalAlignment(2);
        kdtEqmUse_eqmTime_TextField.setDataType(1);
        	kdtEqmUse_eqmTime_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEqmUse_eqmTime_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEqmUse_eqmTime_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEqmUse_eqmTime_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_eqmTime_TextField);
        this.kdtEqmUse.getColumn("eqmTime").setEditor(kdtEqmUse_eqmTime_CellEditor);
        KDFormattedTextField kdtEqmUse_EventTime_TextField = new KDFormattedTextField();
        kdtEqmUse_EventTime_TextField.setName("kdtEqmUse_EventTime_TextField");
        kdtEqmUse_EventTime_TextField.setVisible(true);
        kdtEqmUse_EventTime_TextField.setEditable(true);
        kdtEqmUse_EventTime_TextField.setHorizontalAlignment(2);
        kdtEqmUse_EventTime_TextField.setDataType(1);
        	kdtEqmUse_EventTime_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEqmUse_EventTime_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEqmUse_EventTime_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEqmUse_EventTime_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_EventTime_TextField);
        this.kdtEqmUse.getColumn("EventTime").setEditor(kdtEqmUse_EventTime_CellEditor);
        KDFormattedTextField kdtEqmUse_usageRate_TextField = new KDFormattedTextField();
        kdtEqmUse_usageRate_TextField.setName("kdtEqmUse_usageRate_TextField");
        kdtEqmUse_usageRate_TextField.setVisible(true);
        kdtEqmUse_usageRate_TextField.setEditable(true);
        kdtEqmUse_usageRate_TextField.setHorizontalAlignment(2);
        kdtEqmUse_usageRate_TextField.setDataType(1);
        	kdtEqmUse_usageRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEqmUse_usageRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEqmUse_usageRate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEqmUse_usageRate_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_usageRate_TextField);
        this.kdtEqmUse.getColumn("usageRate").setEditor(kdtEqmUse_usageRate_CellEditor);
        KDFormattedTextField kdtEqmUse_UseTime_TextField = new KDFormattedTextField();
        kdtEqmUse_UseTime_TextField.setName("kdtEqmUse_UseTime_TextField");
        kdtEqmUse_UseTime_TextField.setVisible(true);
        kdtEqmUse_UseTime_TextField.setEditable(true);
        kdtEqmUse_UseTime_TextField.setHorizontalAlignment(2);
        kdtEqmUse_UseTime_TextField.setDataType(1);
        	kdtEqmUse_UseTime_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEqmUse_UseTime_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEqmUse_UseTime_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEqmUse_UseTime_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_UseTime_TextField);
        this.kdtEqmUse.getColumn("UseTime").setEditor(kdtEqmUse_UseTime_CellEditor);
        KDFormattedTextField kdtEqmUse_faultRate_TextField = new KDFormattedTextField();
        kdtEqmUse_faultRate_TextField.setName("kdtEqmUse_faultRate_TextField");
        kdtEqmUse_faultRate_TextField.setVisible(true);
        kdtEqmUse_faultRate_TextField.setEditable(true);
        kdtEqmUse_faultRate_TextField.setHorizontalAlignment(2);
        kdtEqmUse_faultRate_TextField.setDataType(1);
        	kdtEqmUse_faultRate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEqmUse_faultRate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEqmUse_faultRate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEqmUse_faultRate_CellEditor = new KDTDefaultCellEditor(kdtEqmUse_faultRate_TextField);
        this.kdtEqmUse.getColumn("faultRate").setEditor(kdtEqmUse_faultRate_CellEditor);
        // contstaPerson		
        this.contstaPerson.setBoundLabelText(resHelper.getString("contstaPerson.boundLabelText"));		
        this.contstaPerson.setBoundLabelLength(100);		
        this.contstaPerson.setBoundLabelUnderline(true);		
        this.contstaPerson.setVisible(true);
        // contUseOrgUnit		
        this.contUseOrgUnit.setBoundLabelText(resHelper.getString("contUseOrgUnit.boundLabelText"));		
        this.contUseOrgUnit.setBoundLabelLength(100);		
        this.contUseOrgUnit.setBoundLabelUnderline(true);		
        this.contUseOrgUnit.setVisible(true);
        // contreportTime		
        this.contreportTime.setBoundLabelText(resHelper.getString("contreportTime.boundLabelText"));		
        this.contreportTime.setBoundLabelLength(100);		
        this.contreportTime.setBoundLabelUnderline(true);		
        this.contreportTime.setVisible(true);
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
        this.prmtCU.setVisible(false);
        // txtNumber		
        this.txtNumber.setVisible(false);		
        this.txtNumber.setEnabled(false);
        // pkBizDate
        // txtDescription		
        this.txtDescription.setVisible(false);		
        this.txtDescription.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);		
        this.comboStatus.setVisible(false);
        // comboBizStatus		
        this.comboBizStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBizActionEnum").toArray());		
        this.comboBizStatus.setEnabled(false);		
        this.comboBizStatus.setVisible(false);
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // prmtstaPerson		
        this.prmtstaPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtstaPerson.setEditable(true);		
        this.prmtstaPerson.setDisplayFormat("$name$");		
        this.prmtstaPerson.setEditFormat("$number$");		
        this.prmtstaPerson.setCommitFormat("$number$");		
        this.prmtstaPerson.setRequired(false);
        // prmtUseOrgUnit		
        this.prmtUseOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtUseOrgUnit.setEditable(true);		
        this.prmtUseOrgUnit.setDisplayFormat("$name$");		
        this.prmtUseOrgUnit.setEditFormat("$number$");		
        this.prmtUseOrgUnit.setCommitFormat("$number$");		
        this.prmtUseOrgUnit.setRequired(false);
        // prmtreportTime		
        this.prmtreportTime.setQueryInfo("com.kingdee.eas.port.equipment.base.app.QuarterTimeQuery");		
        this.prmtreportTime.setEditable(true);		
        this.prmtreportTime.setDisplayFormat("$name$");		
        this.prmtreportTime.setEditFormat("$number$");		
        this.prmtreportTime.setCommitFormat("$number$");		
        this.prmtreportTime.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtCU,pkLastUpdateTime,prmtLastUpdateUser,pkCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,pkAuditTime,comboBizStatus,comboStatus,kdtEqmUse,prmtstaPerson,prmtUseOrgUnit,prmtreportTime}));
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
        this.setBounds(new Rectangle(10, 10, 1013, 541));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 541));
        contCreator.setBounds(new Rectangle(36, 461, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(36, 461, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(36, 487, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(36, 487, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(372, 461, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(372, 461, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(372, 487, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(372, 487, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(719, 596, 270, 19));
        this.add(contCU, new KDLayout.Constraints(719, 596, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(38, 592, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(38, 592, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(371, 13, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(371, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(373, 617, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(373, 617, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(709, 461, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(709, 461, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(35, 612, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(35, 612, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizStatus.setBounds(new Rectangle(708, 611, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(708, 611, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(709, 487, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(709, 487, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEqmUse.setBounds(new Rectangle(33, 71, 950, 377));
        kdtEqmUse_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEqmUse,new com.kingdee.eas.port.equipment.operate.EumUseRecordEqmUseInfo(),null,false);
        this.add(kdtEqmUse_detailPanel, new KDLayout.Constraints(33, 71, 950, 377, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
		kdtEqmUse_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("CostType","1");
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        contstaPerson.setBounds(new Rectangle(702, 13, 270, 19));
        this.add(contstaPerson, new KDLayout.Constraints(702, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contUseOrgUnit.setBounds(new Rectangle(40, 13, 270, 19));
        this.add(contUseOrgUnit, new KDLayout.Constraints(40, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contreportTime.setBounds(new Rectangle(39, 42, 270, 19));
        this.add(contreportTime, new KDLayout.Constraints(39, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
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
        //contstaPerson
        contstaPerson.setBoundEditor(prmtstaPerson);
        //contUseOrgUnit
        contUseOrgUnit.setBoundEditor(prmtUseOrgUnit);
        //contreportTime
        contreportTime.setBoundEditor(prmtreportTime);

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
		dataBinder.registerBinding("EqmUse.seq", int.class, this.kdtEqmUse, "seq.text");
		dataBinder.registerBinding("EqmUse", com.kingdee.eas.port.equipment.operate.EumUseRecordEqmUseInfo.class, this.kdtEqmUse, "userObject");
		dataBinder.registerBinding("EqmUse.eqmName", String.class, this.kdtEqmUse, "eqmName.text");
		dataBinder.registerBinding("EqmUse.modelType", String.class, this.kdtEqmUse, "modelType.text");
		dataBinder.registerBinding("EqmUse.powerCost", java.math.BigDecimal.class, this.kdtEqmUse, "powerCost.text");
		dataBinder.registerBinding("EqmUse.czCost", java.math.BigDecimal.class, this.kdtEqmUse, "czCost.text");
		dataBinder.registerBinding("EqmUse.powerUnitCost", java.math.BigDecimal.class, this.kdtEqmUse, "powerUnitCost.text");
		dataBinder.registerBinding("EqmUse.eqmTime", java.math.BigDecimal.class, this.kdtEqmUse, "eqmTime.text");
		dataBinder.registerBinding("EqmUse.EventTime", java.math.BigDecimal.class, this.kdtEqmUse, "EventTime.text");
		dataBinder.registerBinding("EqmUse.usageRate", java.math.BigDecimal.class, this.kdtEqmUse, "usageRate.text");
		dataBinder.registerBinding("EqmUse.UseTime", java.math.BigDecimal.class, this.kdtEqmUse, "UseTime.text");
		dataBinder.registerBinding("EqmUse.faultRate", java.math.BigDecimal.class, this.kdtEqmUse, "faultRate.text");
		dataBinder.registerBinding("EqmUse.eqmCategory", String.class, this.kdtEqmUse, "eqmCategory.text");
		dataBinder.registerBinding("EqmUse.powerUnit", java.lang.Object.class, this.kdtEqmUse, "powerUnit.text");
		dataBinder.registerBinding("EqmUse.czUnit", java.lang.Object.class, this.kdtEqmUse, "czUnit.text");
		dataBinder.registerBinding("EqmUse.puUnit", java.lang.Object.class, this.kdtEqmUse, "puUnit.text");
		dataBinder.registerBinding("EqmUse.CostType", com.kingdee.util.enums.Enum.class, this.kdtEqmUse, "CostType.text");
		dataBinder.registerBinding("EqmUse.eqmType", String.class, this.kdtEqmUse, "eqmType.text");
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
		dataBinder.registerBinding("staPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtstaPerson, "data");
		dataBinder.registerBinding("UseOrgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtUseOrgUnit, "data");
		dataBinder.registerBinding("reportTime", com.kingdee.eas.port.equipment.base.QuarterTimeInfo.class, this.prmtreportTime, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.operate.app.EumUseRecordEditUIHandler";
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
        this.prmtCU.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.operate.EumUseRecordInfo)ov;
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
		getValidateHelper().registerBindProperty("EqmUse.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.eqmName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.modelType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.powerCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.czCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.powerUnitCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.eqmTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.EventTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.usageRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.UseTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.faultRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.eqmCategory", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.powerUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.czUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.puUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.CostType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmUse.eqmType", ValidateHelper.ON_SAVE);    
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
		getValidateHelper().registerBindProperty("staPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("UseOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reportTime", ValidateHelper.ON_SAVE);    		
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
    	sic.add(new SelectorItemInfo("EqmUse.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EqmUse.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("EqmUse.eqmName"));
    	sic.add(new SelectorItemInfo("EqmUse.modelType"));
    	sic.add(new SelectorItemInfo("EqmUse.powerCost"));
    	sic.add(new SelectorItemInfo("EqmUse.czCost"));
    	sic.add(new SelectorItemInfo("EqmUse.powerUnitCost"));
    	sic.add(new SelectorItemInfo("EqmUse.eqmTime"));
    	sic.add(new SelectorItemInfo("EqmUse.EventTime"));
    	sic.add(new SelectorItemInfo("EqmUse.usageRate"));
    	sic.add(new SelectorItemInfo("EqmUse.UseTime"));
    	sic.add(new SelectorItemInfo("EqmUse.faultRate"));
    	sic.add(new SelectorItemInfo("EqmUse.eqmCategory"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EqmUse.powerUnit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("EqmUse.powerUnit.id"));
			sic.add(new SelectorItemInfo("EqmUse.powerUnit.name"));
        	sic.add(new SelectorItemInfo("EqmUse.powerUnit.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EqmUse.czUnit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("EqmUse.czUnit.id"));
			sic.add(new SelectorItemInfo("EqmUse.czUnit.name"));
        	sic.add(new SelectorItemInfo("EqmUse.czUnit.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EqmUse.puUnit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("EqmUse.puUnit.id"));
			sic.add(new SelectorItemInfo("EqmUse.puUnit.name"));
        	sic.add(new SelectorItemInfo("EqmUse.puUnit.number"));
		}
    	sic.add(new SelectorItemInfo("EqmUse.CostType"));
    	sic.add(new SelectorItemInfo("EqmUse.eqmType"));
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
			sic.add(new SelectorItemInfo("staPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("staPerson.id"));
        	sic.add(new SelectorItemInfo("staPerson.number"));
        	sic.add(new SelectorItemInfo("staPerson.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("UseOrgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("UseOrgUnit.id"));
        	sic.add(new SelectorItemInfo("UseOrgUnit.number"));
        	sic.add(new SelectorItemInfo("UseOrgUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("reportTime.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("reportTime.id"));
        	sic.add(new SelectorItemInfo("reportTime.number"));
        	sic.add(new SelectorItemInfo("reportTime.name"));
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
        return new MetaDataPK("com.kingdee.eas.port.equipment.operate.client", "EumUseRecordEditUI");
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
        return com.kingdee.eas.port.equipment.operate.client.EumUseRecordEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.operate.EumUseRecordFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.operate.EumUseRecordInfo objectValue = new com.kingdee.eas.port.equipment.operate.EumUseRecordInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/operate/EumUseRecord";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.operate.app.EumUseRecordQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEqmUse;
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