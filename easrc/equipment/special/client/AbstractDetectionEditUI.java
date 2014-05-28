/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.client;

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
public abstract class AbstractDetectionEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDetectionEditUI.class);
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
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE1;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE1_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contactualDate;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
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
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkactualDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE2;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE2_detailPanel = null;
    protected com.kingdee.eas.port.equipment.special.DetectionInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractDetectionEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDetectionEditUI.class.getName());
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
        this.kdtE1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contactualDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
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
        this.pkactualDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdtE2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
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
        this.kdtE1.setName("kdtE1");
        this.contactualDate.setName("contactualDate");
        this.kDContainer1.setName("kDContainer1");
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
        this.pkactualDate.setName("pkactualDate");
        this.kdtE2.setName("kdtE2");
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
        this.contBizStatus.setVisible(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // kdtE1
		String kdtE1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"deviceType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"deviceType1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"planNumber1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"actualNumber1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"qualifiedNumber1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"qualifiedRate1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"planNumber2\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"actualNumber2\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"qualifiedNumber2\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"qualifiedRate2\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{deviceType}</t:Cell><t:Cell>$Resource{deviceType1}</t:Cell><t:Cell>$Resource{planNumber1}</t:Cell><t:Cell>$Resource{actualNumber1}</t:Cell><t:Cell>$Resource{qualifiedNumber1}</t:Cell><t:Cell>$Resource{qualifiedRate1}</t:Cell><t:Cell>$Resource{planNumber2}</t:Cell><t:Cell>$Resource{actualNumber2}</t:Cell><t:Cell>$Resource{qualifiedNumber2}</t:Cell><t:Cell>$Resource{qualifiedRate2}</t:Cell></t:Row><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row2}</t:Cell><t:Cell>$Resource{deviceType_Row2}</t:Cell><t:Cell>$Resource{deviceType1_Row2}</t:Cell><t:Cell>$Resource{planNumber1_Row2}</t:Cell><t:Cell>$Resource{actualNumber1_Row2}</t:Cell><t:Cell>$Resource{qualifiedNumber1_Row2}</t:Cell><t:Cell>$Resource{qualifiedRate1_Row2}</t:Cell><t:Cell>$Resource{planNumber2_Row2}</t:Cell><t:Cell>$Resource{actualNumber2_Row2}</t:Cell><t:Cell>$Resource{qualifiedNumber2_Row2}</t:Cell><t:Cell>$Resource{qualifiedRate2_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"0\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"0\" t:right=\"10\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE1.setFormatXml(resHelper.translateString("kdtE1",kdtE1StrXML));

                this.kdtE1.putBindContents("editData",new String[] {"seq","deviceType","deviceType1","planNumber1","actualNumber1","qualifiedNumber1","qualifiedRate1","planNumber2","actualNumber2","qualifiedNumber2","qualifiedRate2"});


        this.kdtE1.checkParsed();
        final KDBizPromptBox kdtE1_deviceType_PromptBox = new KDBizPromptBox();
        kdtE1_deviceType_PromptBox.setQueryInfo("com.kingdee.eas.port.equipment.base.app.EqmTypeQuery");
        kdtE1_deviceType_PromptBox.setVisible(true);
        kdtE1_deviceType_PromptBox.setEditable(true);
        kdtE1_deviceType_PromptBox.setDisplayFormat("$number$");
        kdtE1_deviceType_PromptBox.setEditFormat("$number$");
        kdtE1_deviceType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_deviceType_CellEditor = new KDTDefaultCellEditor(kdtE1_deviceType_PromptBox);
        this.kdtE1.getColumn("deviceType").setEditor(kdtE1_deviceType_CellEditor);
        ObjectValueRender kdtE1_deviceType_OVR = new ObjectValueRender();
        kdtE1_deviceType_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE1.getColumn("deviceType").setRenderer(kdtE1_deviceType_OVR);
        			kdtE1_deviceType_PromptBox.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.equipment.base.client.EqmTypeListUI kdtE1_deviceType_PromptBox_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (kdtE1_deviceType_PromptBox_F7ListUI == null) {
					try {
						kdtE1_deviceType_PromptBox_F7ListUI = new com.kingdee.eas.port.equipment.base.client.EqmTypeListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(kdtE1_deviceType_PromptBox_F7ListUI));
					kdtE1_deviceType_PromptBox_F7ListUI.setF7Use(true,ctx);
					kdtE1_deviceType_PromptBox.setSelector(kdtE1_deviceType_PromptBox_F7ListUI);
				}
			}
		});
					
        KDTextField kdtE1_deviceType1_TextField = new KDTextField();
        kdtE1_deviceType1_TextField.setName("kdtE1_deviceType1_TextField");
        kdtE1_deviceType1_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_deviceType1_CellEditor = new KDTDefaultCellEditor(kdtE1_deviceType1_TextField);
        this.kdtE1.getColumn("deviceType1").setEditor(kdtE1_deviceType1_CellEditor);
        KDFormattedTextField kdtE1_planNumber1_TextField = new KDFormattedTextField();
        kdtE1_planNumber1_TextField.setName("kdtE1_planNumber1_TextField");
        kdtE1_planNumber1_TextField.setVisible(true);
        kdtE1_planNumber1_TextField.setEditable(true);
        kdtE1_planNumber1_TextField.setHorizontalAlignment(2);
        kdtE1_planNumber1_TextField.setDataType(1);
        	kdtE1_planNumber1_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_planNumber1_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_planNumber1_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_planNumber1_CellEditor = new KDTDefaultCellEditor(kdtE1_planNumber1_TextField);
        this.kdtE1.getColumn("planNumber1").setEditor(kdtE1_planNumber1_CellEditor);
        KDFormattedTextField kdtE1_actualNumber1_TextField = new KDFormattedTextField();
        kdtE1_actualNumber1_TextField.setName("kdtE1_actualNumber1_TextField");
        kdtE1_actualNumber1_TextField.setVisible(true);
        kdtE1_actualNumber1_TextField.setEditable(true);
        kdtE1_actualNumber1_TextField.setHorizontalAlignment(2);
        kdtE1_actualNumber1_TextField.setDataType(1);
        	kdtE1_actualNumber1_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_actualNumber1_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_actualNumber1_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_actualNumber1_CellEditor = new KDTDefaultCellEditor(kdtE1_actualNumber1_TextField);
        this.kdtE1.getColumn("actualNumber1").setEditor(kdtE1_actualNumber1_CellEditor);
        KDFormattedTextField kdtE1_qualifiedNumber1_TextField = new KDFormattedTextField();
        kdtE1_qualifiedNumber1_TextField.setName("kdtE1_qualifiedNumber1_TextField");
        kdtE1_qualifiedNumber1_TextField.setVisible(true);
        kdtE1_qualifiedNumber1_TextField.setEditable(true);
        kdtE1_qualifiedNumber1_TextField.setHorizontalAlignment(2);
        kdtE1_qualifiedNumber1_TextField.setDataType(1);
        	kdtE1_qualifiedNumber1_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_qualifiedNumber1_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_qualifiedNumber1_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_qualifiedNumber1_CellEditor = new KDTDefaultCellEditor(kdtE1_qualifiedNumber1_TextField);
        this.kdtE1.getColumn("qualifiedNumber1").setEditor(kdtE1_qualifiedNumber1_CellEditor);
        KDFormattedTextField kdtE1_qualifiedRate1_TextField = new KDFormattedTextField();
        kdtE1_qualifiedRate1_TextField.setName("kdtE1_qualifiedRate1_TextField");
        kdtE1_qualifiedRate1_TextField.setVisible(true);
        kdtE1_qualifiedRate1_TextField.setEditable(true);
        kdtE1_qualifiedRate1_TextField.setHorizontalAlignment(2);
        kdtE1_qualifiedRate1_TextField.setDataType(1);
        	kdtE1_qualifiedRate1_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_qualifiedRate1_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_qualifiedRate1_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_qualifiedRate1_CellEditor = new KDTDefaultCellEditor(kdtE1_qualifiedRate1_TextField);
        this.kdtE1.getColumn("qualifiedRate1").setEditor(kdtE1_qualifiedRate1_CellEditor);
        KDFormattedTextField kdtE1_planNumber2_TextField = new KDFormattedTextField();
        kdtE1_planNumber2_TextField.setName("kdtE1_planNumber2_TextField");
        kdtE1_planNumber2_TextField.setVisible(true);
        kdtE1_planNumber2_TextField.setEditable(true);
        kdtE1_planNumber2_TextField.setHorizontalAlignment(2);
        kdtE1_planNumber2_TextField.setDataType(1);
        	kdtE1_planNumber2_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_planNumber2_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_planNumber2_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_planNumber2_CellEditor = new KDTDefaultCellEditor(kdtE1_planNumber2_TextField);
        this.kdtE1.getColumn("planNumber2").setEditor(kdtE1_planNumber2_CellEditor);
        KDFormattedTextField kdtE1_actualNumber2_TextField = new KDFormattedTextField();
        kdtE1_actualNumber2_TextField.setName("kdtE1_actualNumber2_TextField");
        kdtE1_actualNumber2_TextField.setVisible(true);
        kdtE1_actualNumber2_TextField.setEditable(true);
        kdtE1_actualNumber2_TextField.setHorizontalAlignment(2);
        kdtE1_actualNumber2_TextField.setDataType(1);
        	kdtE1_actualNumber2_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_actualNumber2_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_actualNumber2_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_actualNumber2_CellEditor = new KDTDefaultCellEditor(kdtE1_actualNumber2_TextField);
        this.kdtE1.getColumn("actualNumber2").setEditor(kdtE1_actualNumber2_CellEditor);
        KDFormattedTextField kdtE1_qualifiedNumber2_TextField = new KDFormattedTextField();
        kdtE1_qualifiedNumber2_TextField.setName("kdtE1_qualifiedNumber2_TextField");
        kdtE1_qualifiedNumber2_TextField.setVisible(true);
        kdtE1_qualifiedNumber2_TextField.setEditable(true);
        kdtE1_qualifiedNumber2_TextField.setHorizontalAlignment(2);
        kdtE1_qualifiedNumber2_TextField.setDataType(1);
        	kdtE1_qualifiedNumber2_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_qualifiedNumber2_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_qualifiedNumber2_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_qualifiedNumber2_CellEditor = new KDTDefaultCellEditor(kdtE1_qualifiedNumber2_TextField);
        this.kdtE1.getColumn("qualifiedNumber2").setEditor(kdtE1_qualifiedNumber2_CellEditor);
        KDFormattedTextField kdtE1_qualifiedRate2_TextField = new KDFormattedTextField();
        kdtE1_qualifiedRate2_TextField.setName("kdtE1_qualifiedRate2_TextField");
        kdtE1_qualifiedRate2_TextField.setVisible(true);
        kdtE1_qualifiedRate2_TextField.setEditable(true);
        kdtE1_qualifiedRate2_TextField.setHorizontalAlignment(2);
        kdtE1_qualifiedRate2_TextField.setDataType(1);
        	kdtE1_qualifiedRate2_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_qualifiedRate2_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_qualifiedRate2_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_qualifiedRate2_CellEditor = new KDTDefaultCellEditor(kdtE1_qualifiedRate2_TextField);
        this.kdtE1.getColumn("qualifiedRate2").setEditor(kdtE1_qualifiedRate2_CellEditor);
        // contactualDate		
        this.contactualDate.setBoundLabelText(resHelper.getString("contactualDate.boundLabelText"));		
        this.contactualDate.setBoundLabelLength(100);		
        this.contactualDate.setBoundLabelUnderline(true);		
        this.contactualDate.setVisible(true);
        // kDContainer1
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
        // pkactualDate		
        this.pkactualDate.setRequired(false);
        // kdtE2
		String kdtE2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"zdaNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"deviceName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"useUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"planDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"userNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"model\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"commDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"madeUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"testCategory\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"check\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"testResults\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"note\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{zdaNumber}</t:Cell><t:Cell>$Resource{deviceName}</t:Cell><t:Cell>$Resource{useUnit}</t:Cell><t:Cell>$Resource{planDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{userNumber}</t:Cell><t:Cell>$Resource{model}</t:Cell><t:Cell>$Resource{commDate}</t:Cell><t:Cell>$Resource{madeUnit}</t:Cell><t:Cell>$Resource{testCategory}</t:Cell><t:Cell>$Resource{check}</t:Cell><t:Cell>$Resource{testResults}</t:Cell><t:Cell>$Resource{note}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE2.setFormatXml(resHelper.translateString("kdtE2",kdtE2StrXML));
        kdtE2.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtE2_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});


                this.kdtE2.putBindContents("editData",new String[] {"seq","zdaNumber","deviceName","useUnit","planDate","endDate","userNumber","model","commDate","madeUnit","testCategory","check","testResults","note"});


        this.kdtE2.checkParsed();
        final KDBizPromptBox kdtE2_zdaNumber_PromptBox = new KDBizPromptBox();
        kdtE2_zdaNumber_PromptBox.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");
        kdtE2_zdaNumber_PromptBox.setVisible(true);
        kdtE2_zdaNumber_PromptBox.setEditable(true);
        kdtE2_zdaNumber_PromptBox.setDisplayFormat("$number$");
        kdtE2_zdaNumber_PromptBox.setEditFormat("$number$");
        kdtE2_zdaNumber_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE2_zdaNumber_CellEditor = new KDTDefaultCellEditor(kdtE2_zdaNumber_PromptBox);
        this.kdtE2.getColumn("zdaNumber").setEditor(kdtE2_zdaNumber_CellEditor);
        ObjectValueRender kdtE2_zdaNumber_OVR = new ObjectValueRender();
        kdtE2_zdaNumber_OVR.setFormat(new BizDataFormat("$tzdaNumber$"));
        this.kdtE2.getColumn("zdaNumber").setRenderer(kdtE2_zdaNumber_OVR);
        KDTextField kdtE2_deviceName_TextField = new KDTextField();
        kdtE2_deviceName_TextField.setName("kdtE2_deviceName_TextField");
        kdtE2_deviceName_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE2_deviceName_CellEditor = new KDTDefaultCellEditor(kdtE2_deviceName_TextField);
        this.kdtE2.getColumn("deviceName").setEditor(kdtE2_deviceName_CellEditor);
        KDTextField kdtE2_useUnit_TextField = new KDTextField();
        kdtE2_useUnit_TextField.setName("kdtE2_useUnit_TextField");
        kdtE2_useUnit_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE2_useUnit_CellEditor = new KDTDefaultCellEditor(kdtE2_useUnit_TextField);
        this.kdtE2.getColumn("useUnit").setEditor(kdtE2_useUnit_CellEditor);
        KDDatePicker kdtE2_planDate_DatePicker = new KDDatePicker();
        kdtE2_planDate_DatePicker.setName("kdtE2_planDate_DatePicker");
        kdtE2_planDate_DatePicker.setVisible(true);
        kdtE2_planDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtE2_planDate_CellEditor = new KDTDefaultCellEditor(kdtE2_planDate_DatePicker);
        this.kdtE2.getColumn("planDate").setEditor(kdtE2_planDate_CellEditor);
        KDDatePicker kdtE2_endDate_DatePicker = new KDDatePicker();
        kdtE2_endDate_DatePicker.setName("kdtE2_endDate_DatePicker");
        kdtE2_endDate_DatePicker.setVisible(true);
        kdtE2_endDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtE2_endDate_CellEditor = new KDTDefaultCellEditor(kdtE2_endDate_DatePicker);
        this.kdtE2.getColumn("endDate").setEditor(kdtE2_endDate_CellEditor);
        KDTextField kdtE2_userNumber_TextField = new KDTextField();
        kdtE2_userNumber_TextField.setName("kdtE2_userNumber_TextField");
        kdtE2_userNumber_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE2_userNumber_CellEditor = new KDTDefaultCellEditor(kdtE2_userNumber_TextField);
        this.kdtE2.getColumn("userNumber").setEditor(kdtE2_userNumber_CellEditor);
        KDTextField kdtE2_model_TextField = new KDTextField();
        kdtE2_model_TextField.setName("kdtE2_model_TextField");
        kdtE2_model_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE2_model_CellEditor = new KDTDefaultCellEditor(kdtE2_model_TextField);
        this.kdtE2.getColumn("model").setEditor(kdtE2_model_CellEditor);
        KDDatePicker kdtE2_commDate_DatePicker = new KDDatePicker();
        kdtE2_commDate_DatePicker.setName("kdtE2_commDate_DatePicker");
        kdtE2_commDate_DatePicker.setVisible(true);
        kdtE2_commDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtE2_commDate_CellEditor = new KDTDefaultCellEditor(kdtE2_commDate_DatePicker);
        this.kdtE2.getColumn("commDate").setEditor(kdtE2_commDate_CellEditor);
        KDTextField kdtE2_madeUnit_TextField = new KDTextField();
        kdtE2_madeUnit_TextField.setName("kdtE2_madeUnit_TextField");
        kdtE2_madeUnit_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE2_madeUnit_CellEditor = new KDTDefaultCellEditor(kdtE2_madeUnit_TextField);
        this.kdtE2.getColumn("madeUnit").setEditor(kdtE2_madeUnit_CellEditor);
        KDComboBox kdtE2_testCategory_ComboBox = new KDComboBox();
        kdtE2_testCategory_ComboBox.setName("kdtE2_testCategory_ComboBox");
        kdtE2_testCategory_ComboBox.setVisible(true);
        kdtE2_testCategory_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.equipment.base.enumbase.CheckType").toArray());
        KDTDefaultCellEditor kdtE2_testCategory_CellEditor = new KDTDefaultCellEditor(kdtE2_testCategory_ComboBox);
        this.kdtE2.getColumn("testCategory").setEditor(kdtE2_testCategory_CellEditor);
        KDCheckBox kdtE2_check_CheckBox = new KDCheckBox();
        kdtE2_check_CheckBox.setName("kdtE2_check_CheckBox");
        KDTDefaultCellEditor kdtE2_check_CellEditor = new KDTDefaultCellEditor(kdtE2_check_CheckBox);
        this.kdtE2.getColumn("check").setEditor(kdtE2_check_CellEditor);
        KDComboBox kdtE2_testResults_ComboBox = new KDComboBox();
        kdtE2_testResults_ComboBox.setName("kdtE2_testResults_ComboBox");
        kdtE2_testResults_ComboBox.setVisible(true);
        kdtE2_testResults_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.equipment.base.enumbase.CheckResult").toArray());
        KDTDefaultCellEditor kdtE2_testResults_CellEditor = new KDTDefaultCellEditor(kdtE2_testResults_ComboBox);
        this.kdtE2.getColumn("testResults").setEditor(kdtE2_testResults_CellEditor);
        KDTextField kdtE2_note_TextField = new KDTextField();
        kdtE2_note_TextField.setName("kdtE2_note_TextField");
        kdtE2_note_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtE2_note_CellEditor = new KDTDefaultCellEditor(kdtE2_note_TextField);
        this.kdtE2.getColumn("note").setEditor(kdtE2_note_CellEditor);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {pkactualDate,prmtCU,pkLastUpdateTime,prmtLastUpdateUser,pkCreateTime,kdtE2,prmtCreator,prmtAuditor,kdtE1,txtDescription,pkBizDate,txtNumber,pkAuditTime,comboBizStatus,comboStatus}));
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
        this.add(contCreator, new KDLayout.Constraints(35, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(35, 590, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(35, 590, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(371, 564, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(371, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(371, 590, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(371, 590, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(706, 11, 270, 19));
        this.add(contCU, new KDLayout.Constraints(706, 11, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(33, 11, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(33, 11, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(369, 11, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(369, 11, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(33, 35, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(33, 35, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(708, 564, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(708, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(706, 35, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(706, 35, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizStatus.setBounds(new Rectangle(708, 611, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(708, 611, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(708, 590, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(708, 590, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtE1.setBounds(new Rectangle(33, 65, 941, 178));
        kdtE1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE1,new com.kingdee.eas.port.equipment.special.DetectionE1Info(),null,false);
        this.add(kdtE1_detailPanel, new KDLayout.Constraints(33, 65, 941, 178, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contactualDate.setBounds(new Rectangle(369, 35, 270, 19));
        this.add(contactualDate, new KDLayout.Constraints(369, 35, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(33, 253, 944, 305));
        this.add(kDContainer1, new KDLayout.Constraints(33, 253, 944, 305, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        //contactualDate
        contactualDate.setBoundEditor(pkactualDate);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kdtE2_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE2,new com.kingdee.eas.port.equipment.special.DetectionE2Info(),null,false);
        kDContainer1.getContentPane().add(kdtE2_detailPanel, BorderLayout.CENTER);
		kdtE2_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("testCategory","10");
vo.put("testResults","10");
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});

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
		dataBinder.registerBinding("E1", com.kingdee.eas.port.equipment.special.DetectionE1Info.class, this.kdtE1, "userObject");
		dataBinder.registerBinding("E1.deviceType", java.lang.Object.class, this.kdtE1, "deviceType.text");
		dataBinder.registerBinding("E1.planNumber1", java.math.BigDecimal.class, this.kdtE1, "planNumber1.text");
		dataBinder.registerBinding("E1.actualNumber1", java.math.BigDecimal.class, this.kdtE1, "actualNumber1.text");
		dataBinder.registerBinding("E1.qualifiedNumber1", java.math.BigDecimal.class, this.kdtE1, "qualifiedNumber1.text");
		dataBinder.registerBinding("E1.qualifiedRate1", java.math.BigDecimal.class, this.kdtE1, "qualifiedRate1.text");
		dataBinder.registerBinding("E1.planNumber2", java.math.BigDecimal.class, this.kdtE1, "planNumber2.text");
		dataBinder.registerBinding("E1.actualNumber2", java.math.BigDecimal.class, this.kdtE1, "actualNumber2.text");
		dataBinder.registerBinding("E1.qualifiedNumber2", java.math.BigDecimal.class, this.kdtE1, "qualifiedNumber2.text");
		dataBinder.registerBinding("E1.qualifiedRate2", java.math.BigDecimal.class, this.kdtE1, "qualifiedRate2.text");
		dataBinder.registerBinding("E1.deviceType1", String.class, this.kdtE1, "deviceType1.text");
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
		dataBinder.registerBinding("actualDate", java.util.Date.class, this.pkactualDate, "value");
		dataBinder.registerBinding("E2.seq", int.class, this.kdtE2, "seq.text");
		dataBinder.registerBinding("E2", com.kingdee.eas.port.equipment.special.DetectionE2Info.class, this.kdtE2, "userObject");
		dataBinder.registerBinding("E2.zdaNumber", java.lang.Object.class, this.kdtE2, "zdaNumber.text");
		dataBinder.registerBinding("E2.deviceName", String.class, this.kdtE2, "deviceName.text");
		dataBinder.registerBinding("E2.useUnit", String.class, this.kdtE2, "useUnit.text");
		dataBinder.registerBinding("E2.planDate", java.util.Date.class, this.kdtE2, "planDate.text");
		dataBinder.registerBinding("E2.endDate", java.util.Date.class, this.kdtE2, "endDate.text");
		dataBinder.registerBinding("E2.userNumber", String.class, this.kdtE2, "userNumber.text");
		dataBinder.registerBinding("E2.model", String.class, this.kdtE2, "model.text");
		dataBinder.registerBinding("E2.commDate", java.util.Date.class, this.kdtE2, "commDate.text");
		dataBinder.registerBinding("E2.madeUnit", String.class, this.kdtE2, "madeUnit.text");
		dataBinder.registerBinding("E2.testCategory", com.kingdee.util.enums.Enum.class, this.kdtE2, "testCategory.text");
		dataBinder.registerBinding("E2.check", boolean.class, this.kdtE2, "check.text");
		dataBinder.registerBinding("E2.testResults", com.kingdee.util.enums.Enum.class, this.kdtE2, "testResults.text");
		dataBinder.registerBinding("E2.note", String.class, this.kdtE2, "note.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.special.app.DetectionEditUIHandler";
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
        this.pkactualDate.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.special.DetectionInfo)ov;
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
		getValidateHelper().registerBindProperty("E1.deviceType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.planNumber1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.actualNumber1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.qualifiedNumber1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.qualifiedRate1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.planNumber2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.actualNumber2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.qualifiedNumber2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.qualifiedRate2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.deviceType1", ValidateHelper.ON_SAVE);    
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
		getValidateHelper().registerBindProperty("actualDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.zdaNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.deviceName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.useUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.planDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.userNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.model", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.commDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.madeUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.testCategory", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.check", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.testResults", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.note", ValidateHelper.ON_SAVE);    		
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
     * output kdtE2_Changed(int rowIndex,int colIndex) method
     */
    public void kdtE2_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("zdaNumber".equalsIgnoreCase(kdtE2.getColumn(colIndex).getKey())) {
kdtE2.getCell(rowIndex,"deviceName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE2.getCell(rowIndex,"zdaNumber").getValue(),"name")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtE2.getColumn(colIndex).getKey())) {
kdtE2.getCell(rowIndex,"useUnit").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE2.getCell(rowIndex,"zdaNumber").getValue(),"usingDept.name")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtE2.getColumn(colIndex).getKey())) {
kdtE2.getCell(rowIndex,"userNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE2.getCell(rowIndex,"zdaNumber").getValue(),"innerNumber")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtE2.getColumn(colIndex).getKey())) {
kdtE2.getCell(rowIndex,"model").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE2.getCell(rowIndex,"zdaNumber").getValue(),"model")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtE2.getColumn(colIndex).getKey())) {
kdtE2.getCell(rowIndex,"commDate").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getDateValue(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE2.getCell(rowIndex,"zdaNumber").getValue(),"qyDate")));

}

    if ("zdaNumber".equalsIgnoreCase(kdtE2.getColumn(colIndex).getKey())) {
kdtE2.getCell(rowIndex,"madeUnit").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtE2.getCell(rowIndex,"zdaNumber").getValue(),"mader")));

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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.deviceType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.deviceType.id"));
			sic.add(new SelectorItemInfo("E1.deviceType.name"));
        	sic.add(new SelectorItemInfo("E1.deviceType.number"));
		}
    	sic.add(new SelectorItemInfo("E1.planNumber1"));
    	sic.add(new SelectorItemInfo("E1.actualNumber1"));
    	sic.add(new SelectorItemInfo("E1.qualifiedNumber1"));
    	sic.add(new SelectorItemInfo("E1.qualifiedRate1"));
    	sic.add(new SelectorItemInfo("E1.planNumber2"));
    	sic.add(new SelectorItemInfo("E1.actualNumber2"));
    	sic.add(new SelectorItemInfo("E1.qualifiedNumber2"));
    	sic.add(new SelectorItemInfo("E1.qualifiedRate2"));
    	sic.add(new SelectorItemInfo("E1.deviceType1"));
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
        sic.add(new SelectorItemInfo("actualDate"));
    	sic.add(new SelectorItemInfo("E2.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E2.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E2.zdaNumber.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E2.zdaNumber.id"));
			sic.add(new SelectorItemInfo("E2.zdaNumber.tzdaNumber"));
			sic.add(new SelectorItemInfo("E2.zdaNumber.name"));
        	sic.add(new SelectorItemInfo("E2.zdaNumber.number"));
		}
    	sic.add(new SelectorItemInfo("E2.deviceName"));
    	sic.add(new SelectorItemInfo("E2.useUnit"));
    	sic.add(new SelectorItemInfo("E2.planDate"));
    	sic.add(new SelectorItemInfo("E2.endDate"));
    	sic.add(new SelectorItemInfo("E2.userNumber"));
    	sic.add(new SelectorItemInfo("E2.model"));
    	sic.add(new SelectorItemInfo("E2.commDate"));
    	sic.add(new SelectorItemInfo("E2.madeUnit"));
    	sic.add(new SelectorItemInfo("E2.testCategory"));
    	sic.add(new SelectorItemInfo("E2.check"));
    	sic.add(new SelectorItemInfo("E2.testResults"));
    	sic.add(new SelectorItemInfo("E2.note"));
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
        return new MetaDataPK("com.kingdee.eas.port.equipment.special.client", "DetectionEditUI");
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
        return com.kingdee.eas.port.equipment.special.client.DetectionEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.special.DetectionFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.special.DetectionInfo objectValue = new com.kingdee.eas.port.equipment.special.DetectionInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/special/Detection";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.special.app.DetectionQuery");
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