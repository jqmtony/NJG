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
public abstract class AbstractProjectEstimateEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectEstimateEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttempName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contestimateAmount;
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
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmttempName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprojectName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtestimateAmount;
    protected com.kingdee.eas.port.pm.invest.ProjectEstimateInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractProjectEstimateEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectEstimateEditUI.class.getName());
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
        this.conttempName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contestimateAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.prmttempName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtprojectName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtestimateAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
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
        this.conttempName.setName("conttempName");
        this.contprojectName.setName("contprojectName");
        this.contestimateAmount.setName("contestimateAmount");
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
        this.prmttempName.setName("prmttempName");
        this.prmtprojectName.setName("prmtprojectName");
        this.txtestimateAmount.setName("txtestimateAmount");
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
        // kdtE1
		String kdtE1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"costName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"total\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"constructionCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"EPCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"installCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"otherCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"volume\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"index\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"proportion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{costType}</t:Cell><t:Cell>$Resource{costName}</t:Cell><t:Cell>$Resource{total}</t:Cell><t:Cell>$Resource{constructionCost}</t:Cell><t:Cell>$Resource{EPCost}</t:Cell><t:Cell>$Resource{installCost}</t:Cell><t:Cell>$Resource{otherCost}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{volume}</t:Cell><t:Cell>$Resource{index}</t:Cell><t:Cell>$Resource{proportion}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE1.setFormatXml(resHelper.translateString("kdtE1",kdtE1StrXML));
        this.kdtE1.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtE1_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtE1.putBindContents("editData",new String[] {"seq","costType","costName","total","constructionCost","EPCost","installCost","otherCost","unit","volume","index","proportion"});


        this.kdtE1.checkParsed();
        KDFormattedTextField kdtE1_seq_TextField = new KDFormattedTextField();
        kdtE1_seq_TextField.setName("kdtE1_seq_TextField");
        kdtE1_seq_TextField.setVisible(true);
        kdtE1_seq_TextField.setEditable(true);
        kdtE1_seq_TextField.setHorizontalAlignment(2);
        kdtE1_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtE1_seq_CellEditor = new KDTDefaultCellEditor(kdtE1_seq_TextField);
        this.kdtE1.getColumn("seq").setEditor(kdtE1_seq_CellEditor);
        final KDBizPromptBox kdtE1_costName_PromptBox = new KDBizPromptBox();
        kdtE1_costName_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.base.app.CostTypeQuery");
        kdtE1_costName_PromptBox.setVisible(true);
        kdtE1_costName_PromptBox.setEditable(true);
        kdtE1_costName_PromptBox.setDisplayFormat("$number$");
        kdtE1_costName_PromptBox.setEditFormat("$number$");
        kdtE1_costName_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_costName_CellEditor = new KDTDefaultCellEditor(kdtE1_costName_PromptBox);
        this.kdtE1.getColumn("costName").setEditor(kdtE1_costName_CellEditor);
        ObjectValueRender kdtE1_costName_OVR = new ObjectValueRender();
        kdtE1_costName_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE1.getColumn("costName").setRenderer(kdtE1_costName_OVR);
        			kdtE1_costName_PromptBox.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.pm.base.client.CostTypeListUI kdtE1_costName_PromptBox_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (kdtE1_costName_PromptBox_F7ListUI == null) {
					try {
						kdtE1_costName_PromptBox_F7ListUI = new com.kingdee.eas.port.pm.base.client.CostTypeListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(kdtE1_costName_PromptBox_F7ListUI));
					kdtE1_costName_PromptBox_F7ListUI.setF7Use(true,ctx);
					kdtE1_costName_PromptBox.setSelector(kdtE1_costName_PromptBox_F7ListUI);
				}
			}
		});
					
        KDFormattedTextField kdtE1_total_TextField = new KDFormattedTextField();
        kdtE1_total_TextField.setName("kdtE1_total_TextField");
        kdtE1_total_TextField.setVisible(true);
        kdtE1_total_TextField.setEditable(true);
        kdtE1_total_TextField.setHorizontalAlignment(2);
        kdtE1_total_TextField.setDataType(1);
        	kdtE1_total_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtE1_total_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtE1_total_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtE1_total_CellEditor = new KDTDefaultCellEditor(kdtE1_total_TextField);
        this.kdtE1.getColumn("total").setEditor(kdtE1_total_CellEditor);
        KDFormattedTextField kdtE1_constructionCost_TextField = new KDFormattedTextField();
        kdtE1_constructionCost_TextField.setName("kdtE1_constructionCost_TextField");
        kdtE1_constructionCost_TextField.setVisible(true);
        kdtE1_constructionCost_TextField.setEditable(true);
        kdtE1_constructionCost_TextField.setHorizontalAlignment(2);
        kdtE1_constructionCost_TextField.setDataType(1);
        	kdtE1_constructionCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_constructionCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_constructionCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_constructionCost_CellEditor = new KDTDefaultCellEditor(kdtE1_constructionCost_TextField);
        this.kdtE1.getColumn("constructionCost").setEditor(kdtE1_constructionCost_CellEditor);
        KDFormattedTextField kdtE1_EPCost_TextField = new KDFormattedTextField();
        kdtE1_EPCost_TextField.setName("kdtE1_EPCost_TextField");
        kdtE1_EPCost_TextField.setVisible(true);
        kdtE1_EPCost_TextField.setEditable(true);
        kdtE1_EPCost_TextField.setHorizontalAlignment(2);
        kdtE1_EPCost_TextField.setDataType(1);
        	kdtE1_EPCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_EPCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_EPCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_EPCost_CellEditor = new KDTDefaultCellEditor(kdtE1_EPCost_TextField);
        this.kdtE1.getColumn("EPCost").setEditor(kdtE1_EPCost_CellEditor);
        KDFormattedTextField kdtE1_installCost_TextField = new KDFormattedTextField();
        kdtE1_installCost_TextField.setName("kdtE1_installCost_TextField");
        kdtE1_installCost_TextField.setVisible(true);
        kdtE1_installCost_TextField.setEditable(true);
        kdtE1_installCost_TextField.setHorizontalAlignment(2);
        kdtE1_installCost_TextField.setDataType(1);
        	kdtE1_installCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_installCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_installCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_installCost_CellEditor = new KDTDefaultCellEditor(kdtE1_installCost_TextField);
        this.kdtE1.getColumn("installCost").setEditor(kdtE1_installCost_CellEditor);
        KDFormattedTextField kdtE1_otherCost_TextField = new KDFormattedTextField();
        kdtE1_otherCost_TextField.setName("kdtE1_otherCost_TextField");
        kdtE1_otherCost_TextField.setVisible(true);
        kdtE1_otherCost_TextField.setEditable(true);
        kdtE1_otherCost_TextField.setHorizontalAlignment(2);
        kdtE1_otherCost_TextField.setDataType(1);
        	kdtE1_otherCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_otherCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_otherCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_otherCost_CellEditor = new KDTDefaultCellEditor(kdtE1_otherCost_TextField);
        this.kdtE1.getColumn("otherCost").setEditor(kdtE1_otherCost_CellEditor);
        final KDBizPromptBox kdtE1_unit_PromptBox = new KDBizPromptBox();
        kdtE1_unit_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
        kdtE1_unit_PromptBox.setVisible(true);
        kdtE1_unit_PromptBox.setEditable(true);
        kdtE1_unit_PromptBox.setDisplayFormat("$number$");
        kdtE1_unit_PromptBox.setEditFormat("$number$");
        kdtE1_unit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_unit_CellEditor = new KDTDefaultCellEditor(kdtE1_unit_PromptBox);
        this.kdtE1.getColumn("unit").setEditor(kdtE1_unit_CellEditor);
        ObjectValueRender kdtE1_unit_OVR = new ObjectValueRender();
        kdtE1_unit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE1.getColumn("unit").setRenderer(kdtE1_unit_OVR);
        KDFormattedTextField kdtE1_volume_TextField = new KDFormattedTextField();
        kdtE1_volume_TextField.setName("kdtE1_volume_TextField");
        kdtE1_volume_TextField.setVisible(true);
        kdtE1_volume_TextField.setEditable(true);
        kdtE1_volume_TextField.setHorizontalAlignment(2);
        kdtE1_volume_TextField.setDataType(1);
        	kdtE1_volume_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_volume_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_volume_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_volume_CellEditor = new KDTDefaultCellEditor(kdtE1_volume_TextField);
        this.kdtE1.getColumn("volume").setEditor(kdtE1_volume_CellEditor);
        KDFormattedTextField kdtE1_index_TextField = new KDFormattedTextField();
        kdtE1_index_TextField.setName("kdtE1_index_TextField");
        kdtE1_index_TextField.setVisible(true);
        kdtE1_index_TextField.setEditable(true);
        kdtE1_index_TextField.setHorizontalAlignment(2);
        kdtE1_index_TextField.setDataType(1);
        	kdtE1_index_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtE1_index_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtE1_index_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtE1_index_CellEditor = new KDTDefaultCellEditor(kdtE1_index_TextField);
        this.kdtE1.getColumn("index").setEditor(kdtE1_index_CellEditor);
        KDFormattedTextField kdtE1_proportion_TextField = new KDFormattedTextField();
        kdtE1_proportion_TextField.setName("kdtE1_proportion_TextField");
        kdtE1_proportion_TextField.setVisible(true);
        kdtE1_proportion_TextField.setEditable(true);
        kdtE1_proportion_TextField.setHorizontalAlignment(2);
        kdtE1_proportion_TextField.setDataType(1);
        	kdtE1_proportion_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtE1_proportion_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtE1_proportion_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtE1_proportion_CellEditor = new KDTDefaultCellEditor(kdtE1_proportion_TextField);
        this.kdtE1.getColumn("proportion").setEditor(kdtE1_proportion_CellEditor);
        // conttempName		
        this.conttempName.setBoundLabelText(resHelper.getString("conttempName.boundLabelText"));		
        this.conttempName.setBoundLabelLength(100);		
        this.conttempName.setBoundLabelUnderline(true);		
        this.conttempName.setVisible(true);
        // contprojectName		
        this.contprojectName.setBoundLabelText(resHelper.getString("contprojectName.boundLabelText"));		
        this.contprojectName.setBoundLabelLength(100);		
        this.contprojectName.setBoundLabelUnderline(true);		
        this.contprojectName.setVisible(true);
        // contestimateAmount		
        this.contestimateAmount.setBoundLabelText(resHelper.getString("contestimateAmount.boundLabelText"));		
        this.contestimateAmount.setBoundLabelLength(100);		
        this.contestimateAmount.setBoundLabelUnderline(true);		
        this.contestimateAmount.setVisible(true);
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
        this.pkBizDate.setEditable(false);		
        this.pkBizDate.setEnabled(false);
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
        // prmttempName		
        this.prmttempName.setQueryInfo("com.kingdee.eas.port.pm.invest.app.CostTempQuery");		
        this.prmttempName.setEditable(true);		
        this.prmttempName.setDisplayFormat("$tempName$");		
        this.prmttempName.setEditFormat("$number$");		
        this.prmttempName.setCommitFormat("$number$");		
        this.prmttempName.setRequired(false);
        this.prmttempName.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmttempName_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtprojectName		
        this.prmtprojectName.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7ProjectQuery");		
        this.prmtprojectName.setEditable(true);		
        this.prmtprojectName.setDisplayFormat("$name$");		
        this.prmtprojectName.setEditFormat("$number$");		
        this.prmtprojectName.setCommitFormat("$number$");		
        this.prmtprojectName.setRequired(true);
        // txtestimateAmount		
        this.txtestimateAmount.setHorizontalAlignment(2);		
        this.txtestimateAmount.setDataType(1);		
        this.txtestimateAmount.setSupportedEmpty(true);		
        this.txtestimateAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtestimateAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtestimateAmount.setPrecision(2);		
        this.txtestimateAmount.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtCU,pkLastUpdateTime,prmtLastUpdateUser,pkCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,pkAuditTime,comboBizStatus,comboStatus,prmttempName,prmtprojectName,txtestimateAmount,kdtE1}));
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
        this.setBounds(new Rectangle(10, 10, 1013, 557));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 557));
        contCreator.setBounds(new Rectangle(10, 466, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 466, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(10, 490, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(10, 490, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(341, 466, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(341, 466, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(341, 490, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(341, 490, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(672, 10, 270, 19));
        this.add(contCU, new KDLayout.Constraints(672, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(672, 37, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(672, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(10, 514, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(10, 514, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(672, 466, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(672, 466, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(341, 10, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(341, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizStatus.setBounds(new Rectangle(341, 514, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(341, 514, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(672, 490, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(672, 490, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtE1.setBounds(new Rectangle(10, 102, 939, 341));
        kdtE1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE1,new com.kingdee.eas.port.pm.invest.ProjectEstimateE1Info(),null,false);
        this.add(kdtE1_detailPanel, new KDLayout.Constraints(10, 102, 939, 341, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        conttempName.setBounds(new Rectangle(10, 37, 270, 19));
        this.add(conttempName, new KDLayout.Constraints(10, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contprojectName.setBounds(new Rectangle(341, 37, 270, 19));
        this.add(contprojectName, new KDLayout.Constraints(341, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contestimateAmount.setBounds(new Rectangle(10, 65, 270, 19));
        this.add(contestimateAmount, new KDLayout.Constraints(10, 65, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
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
        //conttempName
        conttempName.setBoundEditor(prmttempName);
        //contprojectName
        contprojectName.setBoundEditor(prmtprojectName);
        //contestimateAmount
        contestimateAmount.setBoundEditor(txtestimateAmount);

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
		dataBinder.registerBinding("E1", com.kingdee.eas.port.pm.invest.ProjectEstimateE1Info.class, this.kdtE1, "userObject");
		dataBinder.registerBinding("E1.constructionCost", java.math.BigDecimal.class, this.kdtE1, "constructionCost.text");
		dataBinder.registerBinding("E1.EPCost", java.math.BigDecimal.class, this.kdtE1, "EPCost.text");
		dataBinder.registerBinding("E1.installCost", java.math.BigDecimal.class, this.kdtE1, "installCost.text");
		dataBinder.registerBinding("E1.otherCost", java.math.BigDecimal.class, this.kdtE1, "otherCost.text");
		dataBinder.registerBinding("E1.unit", java.lang.Object.class, this.kdtE1, "unit.text");
		dataBinder.registerBinding("E1.volume", java.math.BigDecimal.class, this.kdtE1, "volume.text");
		dataBinder.registerBinding("E1.index", java.math.BigDecimal.class, this.kdtE1, "index.text");
		dataBinder.registerBinding("E1.proportion", java.math.BigDecimal.class, this.kdtE1, "proportion.text");
		dataBinder.registerBinding("E1.total", java.math.BigDecimal.class, this.kdtE1, "total.text");
		dataBinder.registerBinding("E1.costType", com.kingdee.eas.port.pm.base.CostTypeInfo.class, this.kdtE1, "costType.text");
		dataBinder.registerBinding("E1.costName", java.lang.Object.class, this.kdtE1, "costName.text");
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
		dataBinder.registerBinding("tempName", com.kingdee.eas.port.pm.invest.CostTempInfo.class, this.prmttempName, "data");
		dataBinder.registerBinding("projectName", com.kingdee.eas.basedata.assistant.ProjectInfo.class, this.prmtprojectName, "data");
		dataBinder.registerBinding("estimateAmount", java.math.BigDecimal.class, this.txtestimateAmount, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invest.app.ProjectEstimateEditUIHandler";
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
        this.editData = (com.kingdee.eas.port.pm.invest.ProjectEstimateInfo)ov;
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
		getValidateHelper().registerBindProperty("E1.constructionCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.EPCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.installCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.otherCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.unit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.volume", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.index", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.proportion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.total", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.costType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.costName", ValidateHelper.ON_SAVE);    
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
		getValidateHelper().registerBindProperty("tempName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("estimateAmount", ValidateHelper.ON_SAVE);    		
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
     * output kdtE1_editStopped method
     */
    protected void kdtE1_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here000
    }

    /**
     * output prmttempName_dataChanged method
     */
    protected void prmttempName_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
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
    	sic.add(new SelectorItemInfo("E1.constructionCost"));
    	sic.add(new SelectorItemInfo("E1.EPCost"));
    	sic.add(new SelectorItemInfo("E1.installCost"));
    	sic.add(new SelectorItemInfo("E1.otherCost"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.unit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.unit.id"));
			sic.add(new SelectorItemInfo("E1.unit.name"));
        	sic.add(new SelectorItemInfo("E1.unit.number"));
		}
    	sic.add(new SelectorItemInfo("E1.volume"));
    	sic.add(new SelectorItemInfo("E1.index"));
    	sic.add(new SelectorItemInfo("E1.proportion"));
    	sic.add(new SelectorItemInfo("E1.total"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.costType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.costType.id"));
			sic.add(new SelectorItemInfo("E1.costType.name"));
        	sic.add(new SelectorItemInfo("E1.costType.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.costName.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("E1.costName.id"));
			sic.add(new SelectorItemInfo("E1.costName.name"));
        	sic.add(new SelectorItemInfo("E1.costName.number"));
		}
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
			sic.add(new SelectorItemInfo("tempName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("tempName.id"));
        	sic.add(new SelectorItemInfo("tempName.number"));
        	sic.add(new SelectorItemInfo("tempName.tempName"));
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
        sic.add(new SelectorItemInfo("estimateAmount"));
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
        return new MetaDataPK("com.kingdee.eas.port.pm.invest.client", "ProjectEstimateEditUI");
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
        return com.kingdee.eas.port.pm.invest.client.ProjectEstimateEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invest.ProjectEstimateFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invest.ProjectEstimateInfo objectValue = new com.kingdee.eas.port.pm.invest.ProjectEstimateInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/invest/ProjectEstimate";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.invest.app.ProjectEstimateQuery");
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