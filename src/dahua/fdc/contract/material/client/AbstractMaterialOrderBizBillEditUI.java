/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

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
public abstract class AbstractMaterialOrderBizBillEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMaterialOrderBizBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane paneBIZLayerControl17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsuppliers;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbillState;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDPanel baseTab;
    protected com.kingdee.bos.ctrl.swing.KDPanel assTab;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtAssEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtAssEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsuppliers;
    protected com.kingdee.bos.ctrl.swing.KDComboBox billState;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton importMes;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrice;
    protected com.kingdee.eas.fdc.material.MaterialOrderBizBillInfo editData = null;
    protected actionImportMes actionImportMes = null;
    protected actionAudit actionAudit = null;
    protected actionUnAudit actionUnAudit = null;
    /**
     * output class constructor
     */
    public AbstractMaterialOrderBizBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMaterialOrderBizBillEditUI.class.getName());
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
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrint
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionImportMes
        this.actionImportMes = new actionImportMes(this);
        getActionManager().registerAction("actionImportMes", actionImportMes);
         this.actionImportMes.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new actionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new actionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.paneBIZLayerControl17 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contsuppliers = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbillState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.baseTab = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.assTab = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtAssEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtsuppliers = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.billState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.importMes = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPrice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.paneBIZLayerControl17.setName("paneBIZLayerControl17");
        this.contsuppliers.setName("contsuppliers");
        this.contbillState.setName("contbillState");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.baseTab.setName("baseTab");
        this.assTab.setName("assTab");
        this.kdtEntrys.setName("kdtEntrys");
        this.kdtAssEntrys.setName("kdtAssEntrys");
        this.prmtsuppliers.setName("prmtsuppliers");
        this.billState.setName("billState");
        this.importMes.setName("importMes");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnPrice.setName("btnPrice");
        // CoreUI		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.separator1.setVisible(false);		
        this.separator3.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewDoProccess.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);		
        this.btnCreateTo.setVisible(true);		
        this.menuItemCreateTo.setVisible(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelAlignment(7);		
        this.contNumber.setVisible(true);		
        this.contNumber.setForeground(new java.awt.Color(0,0,0));
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);		
        this.contBizDate.setForeground(new java.awt.Color(0,0,0));
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setBoundLabelAlignment(7);		
        this.contDescription.setVisible(true);		
        this.contDescription.setForeground(new java.awt.Color(0,0,0));
        // paneBIZLayerControl17		
        this.paneBIZLayerControl17.setVisible(true);
        this.paneBIZLayerControl17.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    paneBIZLayerControl17_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.paneBIZLayerControl17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    paneBIZLayerControl17_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // contsuppliers		
        this.contsuppliers.setBoundLabelText(resHelper.getString("contsuppliers.boundLabelText"));		
        this.contsuppliers.setBoundLabelLength(100);		
        this.contsuppliers.setBoundLabelUnderline(true);		
        this.contsuppliers.setVisible(true);
        // contbillState		
        this.contbillState.setBoundLabelText(resHelper.getString("contbillState.boundLabelText"));		
        this.contbillState.setBoundLabelLength(100);		
        this.contbillState.setBoundLabelUnderline(true);		
        this.contbillState.setVisible(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setForeground(new java.awt.Color(0,0,0));
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);		
        this.pkBizDate.setRequired(true);		
        this.pkBizDate.setForeground(new java.awt.Color(0,0,0));
        // txtDescription		
        this.txtDescription.setMaxLength(80);		
        this.txtDescription.setVisible(true);		
        this.txtDescription.setEnabled(true);		
        this.txtDescription.setHorizontalAlignment(2);		
        this.txtDescription.setRequired(false);		
        this.txtDescription.setForeground(new java.awt.Color(0,0,0));
        // baseTab		
        this.baseTab.setVisible(true);
        // assTab		
        this.assTab.setVisible(true);
        // kdtEntrys		
        this.kdtEntrys.setFormatXml(resHelper.getString("kdtEntrys.formatXml"));
        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","materialNumber","materialName","materialModel","unit","quantity","price","currency","exRate","amount","originalAmount","arriveDate","curProject","ariverAddr","dept","planUser","contractUnit","contractBill","materialContract"});


        this.kdtEntrys.checkParsed();
        final KDBizPromptBox kdtEntrys_materialNumber_PromptBox = new KDBizPromptBox();
        kdtEntrys_materialNumber_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialBaseInfoQuery");
        kdtEntrys_materialNumber_PromptBox.setVisible(true);
        kdtEntrys_materialNumber_PromptBox.setEditable(true);
        kdtEntrys_materialNumber_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_materialNumber_PromptBox.setEditFormat("$number$");
        kdtEntrys_materialNumber_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_materialNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_materialNumber_PromptBox);
        this.kdtEntrys.getColumn("materialNumber").setEditor(kdtEntrys_materialNumber_CellEditor);
        ObjectValueRender kdtEntrys_materialNumber_OVR = new ObjectValueRender();
        kdtEntrys_materialNumber_OVR.setFormat(new BizDataFormat("$number$"));
        this.kdtEntrys.getColumn("materialNumber").setRenderer(kdtEntrys_materialNumber_OVR);
        KDTextField kdtEntrys_materialName_TextField = new KDTextField();
        kdtEntrys_materialName_TextField.setName("kdtEntrys_materialName_TextField");
        kdtEntrys_materialName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_materialName_CellEditor = new KDTDefaultCellEditor(kdtEntrys_materialName_TextField);
        this.kdtEntrys.getColumn("materialName").setEditor(kdtEntrys_materialName_CellEditor);
        KDTextField kdtEntrys_materialModel_TextField = new KDTextField();
        kdtEntrys_materialModel_TextField.setName("kdtEntrys_materialModel_TextField");
        kdtEntrys_materialModel_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_materialModel_CellEditor = new KDTDefaultCellEditor(kdtEntrys_materialModel_TextField);
        this.kdtEntrys.getColumn("materialModel").setEditor(kdtEntrys_materialModel_CellEditor);
        final KDBizPromptBox kdtEntrys_unit_PromptBox = new KDBizPromptBox();
        kdtEntrys_unit_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
        kdtEntrys_unit_PromptBox.setVisible(true);
        kdtEntrys_unit_PromptBox.setEditable(true);
        kdtEntrys_unit_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_unit_PromptBox.setEditFormat("$number$");
        kdtEntrys_unit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_unit_CellEditor = new KDTDefaultCellEditor(kdtEntrys_unit_PromptBox);
        this.kdtEntrys.getColumn("unit").setEditor(kdtEntrys_unit_CellEditor);
        ObjectValueRender kdtEntrys_unit_OVR = new ObjectValueRender();
        kdtEntrys_unit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("unit").setRenderer(kdtEntrys_unit_OVR);
        KDFormattedTextField kdtEntrys_quantity_TextField = new KDFormattedTextField();
        kdtEntrys_quantity_TextField.setName("kdtEntrys_quantity_TextField");
        kdtEntrys_quantity_TextField.setVisible(true);
        kdtEntrys_quantity_TextField.setEditable(true);
        kdtEntrys_quantity_TextField.setHorizontalAlignment(2);
        kdtEntrys_quantity_TextField.setDataType(1);
        	kdtEntrys_quantity_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_quantity_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_quantity_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_quantity_CellEditor = new KDTDefaultCellEditor(kdtEntrys_quantity_TextField);
        this.kdtEntrys.getColumn("quantity").setEditor(kdtEntrys_quantity_CellEditor);
        KDFormattedTextField kdtEntrys_price_TextField = new KDFormattedTextField();
        kdtEntrys_price_TextField.setName("kdtEntrys_price_TextField");
        kdtEntrys_price_TextField.setVisible(true);
        kdtEntrys_price_TextField.setEditable(true);
        kdtEntrys_price_TextField.setHorizontalAlignment(2);
        kdtEntrys_price_TextField.setDataType(1);
        	kdtEntrys_price_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_price_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_price_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_price_CellEditor = new KDTDefaultCellEditor(kdtEntrys_price_TextField);
        this.kdtEntrys.getColumn("price").setEditor(kdtEntrys_price_CellEditor);
        final KDBizPromptBox kdtEntrys_currency_PromptBox = new KDBizPromptBox();
        kdtEntrys_currency_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");
        kdtEntrys_currency_PromptBox.setVisible(true);
        kdtEntrys_currency_PromptBox.setEditable(true);
        kdtEntrys_currency_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_currency_PromptBox.setEditFormat("$number$");
        kdtEntrys_currency_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_currency_CellEditor = new KDTDefaultCellEditor(kdtEntrys_currency_PromptBox);
        this.kdtEntrys.getColumn("currency").setEditor(kdtEntrys_currency_CellEditor);
        ObjectValueRender kdtEntrys_currency_OVR = new ObjectValueRender();
        kdtEntrys_currency_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("currency").setRenderer(kdtEntrys_currency_OVR);
        KDFormattedTextField kdtEntrys_rate_TextField = new KDFormattedTextField();
        kdtEntrys_rate_TextField.setName("kdtEntrys_rate_TextField");
        kdtEntrys_rate_TextField.setVisible(true);
        kdtEntrys_rate_TextField.setEditable(true);
        kdtEntrys_rate_TextField.setHorizontalAlignment(2);
        kdtEntrys_rate_TextField.setDataType(1);
        	kdtEntrys_rate_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_rate_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_rate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_rate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_rate_TextField);
        this.kdtEntrys.getColumn("rate").setEditor(kdtEntrys_rate_CellEditor);
        KDFormattedTextField kdtEntrys_amount_TextField = new KDFormattedTextField();
        kdtEntrys_amount_TextField.setName("kdtEntrys_amount_TextField");
        kdtEntrys_amount_TextField.setVisible(true);
        kdtEntrys_amount_TextField.setEditable(true);
        kdtEntrys_amount_TextField.setHorizontalAlignment(2);
        kdtEntrys_amount_TextField.setDataType(1);
        	kdtEntrys_amount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_amount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_amount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_amount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_amount_TextField);
        this.kdtEntrys.getColumn("amount").setEditor(kdtEntrys_amount_CellEditor);
        KDFormattedTextField kdtEntrys_originalAmount_TextField = new KDFormattedTextField();
        kdtEntrys_originalAmount_TextField.setName("kdtEntrys_originalAmount_TextField");
        kdtEntrys_originalAmount_TextField.setVisible(true);
        kdtEntrys_originalAmount_TextField.setEditable(true);
        kdtEntrys_originalAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_originalAmount_TextField.setDataType(1);
        	kdtEntrys_originalAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_originalAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_originalAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_originalAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_originalAmount_TextField);
        this.kdtEntrys.getColumn("originalAmount").setEditor(kdtEntrys_originalAmount_CellEditor);
        KDDatePicker kdtEntrys_arriveDate_DatePicker = new KDDatePicker();
        kdtEntrys_arriveDate_DatePicker.setName("kdtEntrys_arriveDate_DatePicker");
        kdtEntrys_arriveDate_DatePicker.setVisible(true);
        kdtEntrys_arriveDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_arriveDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_arriveDate_DatePicker);
        this.kdtEntrys.getColumn("arriveDate").setEditor(kdtEntrys_arriveDate_CellEditor);
        final KDBizPromptBox kdtEntrys_curProject_PromptBox = new KDBizPromptBox();
        kdtEntrys_curProject_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectForAssActQuery");
        kdtEntrys_curProject_PromptBox.setVisible(true);
        kdtEntrys_curProject_PromptBox.setEditable(true);
        kdtEntrys_curProject_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_curProject_PromptBox.setEditFormat("$number$");
        kdtEntrys_curProject_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_curProject_CellEditor = new KDTDefaultCellEditor(kdtEntrys_curProject_PromptBox);
        this.kdtEntrys.getColumn("curProject").setEditor(kdtEntrys_curProject_CellEditor);
        ObjectValueRender kdtEntrys_curProject_OVR = new ObjectValueRender();
        kdtEntrys_curProject_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("curProject").setRenderer(kdtEntrys_curProject_OVR);
        KDTextField kdtEntrys_ariverAddr_TextField = new KDTextField();
        kdtEntrys_ariverAddr_TextField.setName("kdtEntrys_ariverAddr_TextField");
        kdtEntrys_ariverAddr_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_ariverAddr_CellEditor = new KDTDefaultCellEditor(kdtEntrys_ariverAddr_TextField);
        this.kdtEntrys.getColumn("ariverAddr").setEditor(kdtEntrys_ariverAddr_CellEditor);
        final KDBizPromptBox kdtEntrys_dept_PromptBox = new KDBizPromptBox();
        kdtEntrys_dept_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");
        kdtEntrys_dept_PromptBox.setVisible(true);
        kdtEntrys_dept_PromptBox.setEditable(true);
        kdtEntrys_dept_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_dept_PromptBox.setEditFormat("$number$");
        kdtEntrys_dept_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_dept_CellEditor = new KDTDefaultCellEditor(kdtEntrys_dept_PromptBox);
        this.kdtEntrys.getColumn("dept").setEditor(kdtEntrys_dept_CellEditor);
        ObjectValueRender kdtEntrys_dept_OVR = new ObjectValueRender();
        kdtEntrys_dept_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("dept").setRenderer(kdtEntrys_dept_OVR);
        final KDBizPromptBox kdtEntrys_planUser_PromptBox = new KDBizPromptBox();
        kdtEntrys_planUser_PromptBox.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
        kdtEntrys_planUser_PromptBox.setVisible(true);
        kdtEntrys_planUser_PromptBox.setEditable(true);
        kdtEntrys_planUser_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_planUser_PromptBox.setEditFormat("$number$");
        kdtEntrys_planUser_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_planUser_CellEditor = new KDTDefaultCellEditor(kdtEntrys_planUser_PromptBox);
        this.kdtEntrys.getColumn("planUser").setEditor(kdtEntrys_planUser_CellEditor);
        ObjectValueRender kdtEntrys_planUser_OVR = new ObjectValueRender();
        kdtEntrys_planUser_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("planUser").setRenderer(kdtEntrys_planUser_OVR);
        final KDBizPromptBox kdtEntrys_contractUnit_PromptBox = new KDBizPromptBox();
        kdtEntrys_contractUnit_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierDefaultQuery");
        kdtEntrys_contractUnit_PromptBox.setVisible(true);
        kdtEntrys_contractUnit_PromptBox.setEditable(true);
        kdtEntrys_contractUnit_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_contractUnit_PromptBox.setEditFormat("$number$");
        kdtEntrys_contractUnit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_contractUnit_CellEditor = new KDTDefaultCellEditor(kdtEntrys_contractUnit_PromptBox);
        this.kdtEntrys.getColumn("contractUnit").setEditor(kdtEntrys_contractUnit_CellEditor);
        ObjectValueRender kdtEntrys_contractUnit_OVR = new ObjectValueRender();
        kdtEntrys_contractUnit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("contractUnit").setRenderer(kdtEntrys_contractUnit_OVR);
        final KDBizPromptBox kdtEntrys_contractBill_PromptBox = new KDBizPromptBox();
        kdtEntrys_contractBill_PromptBox.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillQuery");
        kdtEntrys_contractBill_PromptBox.setVisible(true);
        kdtEntrys_contractBill_PromptBox.setEditable(true);
        kdtEntrys_contractBill_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_contractBill_PromptBox.setEditFormat("$number$");
        kdtEntrys_contractBill_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_contractBill_CellEditor = new KDTDefaultCellEditor(kdtEntrys_contractBill_PromptBox);
        this.kdtEntrys.getColumn("contractBill").setEditor(kdtEntrys_contractBill_CellEditor);
        ObjectValueRender kdtEntrys_contractBill_OVR = new ObjectValueRender();
        kdtEntrys_contractBill_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("contractBill").setRenderer(kdtEntrys_contractBill_OVR);
        final KDBizPromptBox kdtEntrys_materialContract_PromptBox = new KDBizPromptBox();
        kdtEntrys_materialContract_PromptBox.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillQuery");
        kdtEntrys_materialContract_PromptBox.setVisible(true);
        kdtEntrys_materialContract_PromptBox.setEditable(true);
        kdtEntrys_materialContract_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_materialContract_PromptBox.setEditFormat("$number$");
        kdtEntrys_materialContract_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_materialContract_CellEditor = new KDTDefaultCellEditor(kdtEntrys_materialContract_PromptBox);
        this.kdtEntrys.getColumn("materialContract").setEditor(kdtEntrys_materialContract_CellEditor);
        ObjectValueRender kdtEntrys_materialContract_OVR = new ObjectValueRender();
        kdtEntrys_materialContract_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("materialContract").setRenderer(kdtEntrys_materialContract_OVR);
        // kdtAssEntrys		
        this.kdtAssEntrys.setFormatXml(resHelper.getString("kdtAssEntrys.formatXml"));
        this.kdtAssEntrys.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    kdtAssEntrys_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtAssEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtAssEntrys_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtAssEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtAssEntrys.putBindContents("editData",new String[] {"id","materialNumber","materialName","materialModel","unit","quantity","price","currency","exRate","amount","originalAmount"});


        this.kdtAssEntrys.checkParsed();
        final KDBizPromptBox kdtAssEntrys_materialNumber_PromptBox = new KDBizPromptBox();
        kdtAssEntrys_materialNumber_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialBaseInfoQuery");
        kdtAssEntrys_materialNumber_PromptBox.setVisible(true);
        kdtAssEntrys_materialNumber_PromptBox.setEditable(true);
        kdtAssEntrys_materialNumber_PromptBox.setDisplayFormat("$number$");
        kdtAssEntrys_materialNumber_PromptBox.setEditFormat("$number$");
        kdtAssEntrys_materialNumber_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtAssEntrys_materialNumber_CellEditor = new KDTDefaultCellEditor(kdtAssEntrys_materialNumber_PromptBox);
        this.kdtAssEntrys.getColumn("materialNumber").setEditor(kdtAssEntrys_materialNumber_CellEditor);
        ObjectValueRender kdtAssEntrys_materialNumber_OVR = new ObjectValueRender();
        kdtAssEntrys_materialNumber_OVR.setFormat(new BizDataFormat("$number$"));
        this.kdtAssEntrys.getColumn("materialNumber").setRenderer(kdtAssEntrys_materialNumber_OVR);
        KDTextField kdtAssEntrys_materialName_TextField = new KDTextField();
        kdtAssEntrys_materialName_TextField.setName("kdtAssEntrys_materialName_TextField");
        kdtAssEntrys_materialName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtAssEntrys_materialName_CellEditor = new KDTDefaultCellEditor(kdtAssEntrys_materialName_TextField);
        this.kdtAssEntrys.getColumn("materialName").setEditor(kdtAssEntrys_materialName_CellEditor);
        KDTextField kdtAssEntrys_materialModel_TextField = new KDTextField();
        kdtAssEntrys_materialModel_TextField.setName("kdtAssEntrys_materialModel_TextField");
        kdtAssEntrys_materialModel_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtAssEntrys_materialModel_CellEditor = new KDTDefaultCellEditor(kdtAssEntrys_materialModel_TextField);
        this.kdtAssEntrys.getColumn("materialModel").setEditor(kdtAssEntrys_materialModel_CellEditor);
        final KDBizPromptBox kdtAssEntrys_unit_PromptBox = new KDBizPromptBox();
        kdtAssEntrys_unit_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
        kdtAssEntrys_unit_PromptBox.setVisible(true);
        kdtAssEntrys_unit_PromptBox.setEditable(true);
        kdtAssEntrys_unit_PromptBox.setDisplayFormat("$number$");
        kdtAssEntrys_unit_PromptBox.setEditFormat("$number$");
        kdtAssEntrys_unit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtAssEntrys_unit_CellEditor = new KDTDefaultCellEditor(kdtAssEntrys_unit_PromptBox);
        this.kdtAssEntrys.getColumn("unit").setEditor(kdtAssEntrys_unit_CellEditor);
        ObjectValueRender kdtAssEntrys_unit_OVR = new ObjectValueRender();
        kdtAssEntrys_unit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtAssEntrys.getColumn("unit").setRenderer(kdtAssEntrys_unit_OVR);
        KDFormattedTextField kdtAssEntrys_quantity_TextField = new KDFormattedTextField();
        kdtAssEntrys_quantity_TextField.setName("kdtAssEntrys_quantity_TextField");
        kdtAssEntrys_quantity_TextField.setVisible(true);
        kdtAssEntrys_quantity_TextField.setEditable(true);
        kdtAssEntrys_quantity_TextField.setHorizontalAlignment(2);
        kdtAssEntrys_quantity_TextField.setDataType(1);
        	kdtAssEntrys_quantity_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtAssEntrys_quantity_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtAssEntrys_quantity_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtAssEntrys_quantity_CellEditor = new KDTDefaultCellEditor(kdtAssEntrys_quantity_TextField);
        this.kdtAssEntrys.getColumn("quantity").setEditor(kdtAssEntrys_quantity_CellEditor);
        KDFormattedTextField kdtAssEntrys_price_TextField = new KDFormattedTextField();
        kdtAssEntrys_price_TextField.setName("kdtAssEntrys_price_TextField");
        kdtAssEntrys_price_TextField.setVisible(true);
        kdtAssEntrys_price_TextField.setEditable(true);
        kdtAssEntrys_price_TextField.setHorizontalAlignment(2);
        kdtAssEntrys_price_TextField.setDataType(1);
        	kdtAssEntrys_price_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtAssEntrys_price_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtAssEntrys_price_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtAssEntrys_price_CellEditor = new KDTDefaultCellEditor(kdtAssEntrys_price_TextField);
        this.kdtAssEntrys.getColumn("price").setEditor(kdtAssEntrys_price_CellEditor);
        final KDBizPromptBox kdtAssEntrys_currency_PromptBox = new KDBizPromptBox();
        kdtAssEntrys_currency_PromptBox.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");
        kdtAssEntrys_currency_PromptBox.setVisible(true);
        kdtAssEntrys_currency_PromptBox.setEditable(true);
        kdtAssEntrys_currency_PromptBox.setDisplayFormat("$number$");
        kdtAssEntrys_currency_PromptBox.setEditFormat("$number$");
        kdtAssEntrys_currency_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtAssEntrys_currency_CellEditor = new KDTDefaultCellEditor(kdtAssEntrys_currency_PromptBox);
        this.kdtAssEntrys.getColumn("currency").setEditor(kdtAssEntrys_currency_CellEditor);
        ObjectValueRender kdtAssEntrys_currency_OVR = new ObjectValueRender();
        kdtAssEntrys_currency_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtAssEntrys.getColumn("currency").setRenderer(kdtAssEntrys_currency_OVR);
        KDFormattedTextField kdtAssEntrys_rate_TextField = new KDFormattedTextField();
        kdtAssEntrys_rate_TextField.setName("kdtAssEntrys_rate_TextField");
        kdtAssEntrys_rate_TextField.setVisible(true);
        kdtAssEntrys_rate_TextField.setEditable(true);
        kdtAssEntrys_rate_TextField.setHorizontalAlignment(2);
        kdtAssEntrys_rate_TextField.setDataType(1);
        kdtAssEntrys_rate_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtAssEntrys_rate_CellEditor = new KDTDefaultCellEditor(kdtAssEntrys_rate_TextField);
        this.kdtAssEntrys.getColumn("rate").setEditor(kdtAssEntrys_rate_CellEditor);
        KDFormattedTextField kdtAssEntrys_amount_TextField = new KDFormattedTextField();
        kdtAssEntrys_amount_TextField.setName("kdtAssEntrys_amount_TextField");
        kdtAssEntrys_amount_TextField.setVisible(true);
        kdtAssEntrys_amount_TextField.setEditable(true);
        kdtAssEntrys_amount_TextField.setHorizontalAlignment(2);
        kdtAssEntrys_amount_TextField.setDataType(1);
        	kdtAssEntrys_amount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtAssEntrys_amount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtAssEntrys_amount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtAssEntrys_amount_CellEditor = new KDTDefaultCellEditor(kdtAssEntrys_amount_TextField);
        this.kdtAssEntrys.getColumn("amount").setEditor(kdtAssEntrys_amount_CellEditor);
        KDFormattedTextField kdtAssEntrys_originalAmount_TextField = new KDFormattedTextField();
        kdtAssEntrys_originalAmount_TextField.setName("kdtAssEntrys_originalAmount_TextField");
        kdtAssEntrys_originalAmount_TextField.setVisible(true);
        kdtAssEntrys_originalAmount_TextField.setEditable(true);
        kdtAssEntrys_originalAmount_TextField.setHorizontalAlignment(2);
        kdtAssEntrys_originalAmount_TextField.setDataType(1);
        	kdtAssEntrys_originalAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtAssEntrys_originalAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtAssEntrys_originalAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtAssEntrys_originalAmount_CellEditor = new KDTDefaultCellEditor(kdtAssEntrys_originalAmount_TextField);
        this.kdtAssEntrys.getColumn("originalAmount").setEditor(kdtAssEntrys_originalAmount_CellEditor);
        // prmtsuppliers		
        this.prmtsuppliers.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");		
        this.prmtsuppliers.setVisible(true);		
        this.prmtsuppliers.setEditable(true);		
        this.prmtsuppliers.setDisplayFormat("$name$");		
        this.prmtsuppliers.setEditFormat("$number$");		
        this.prmtsuppliers.setCommitFormat("$number$");		
        this.prmtsuppliers.setRequired(true);
        // billState		
        this.billState.setVisible(true);		
        this.billState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.material.MaterialBillStateEnum").toArray());		
        this.billState.setRequired(false);
        // importMes
        this.importMes.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportMes, new Class[] { IItemAction.class }, getServiceContext()));		
        this.importMes.setText(resHelper.getString("importMes.text"));		
        this.importMes.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addsinglefile"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));
        this.btnAudit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAudit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));
        this.btnUnAudit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnUnAudit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnPrice		
        this.btnPrice.setText(resHelper.getString("btnPrice.text"));
        this.btnPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnPrice_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {kdtAssEntrys,txtDescription,pkBizDate,txtNumber,kdtEntrys,prmtsuppliers,billState}));
        this.setFocusCycleRoot(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(0, 0, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 629));
        contNumber.setBounds(new Rectangle(18, 22, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(18, 22, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(350, 22, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(350, 22, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(350, 57, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(350, 57, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        paneBIZLayerControl17.setBounds(new Rectangle(6, 95, 993, 527));
        this.add(paneBIZLayerControl17, new KDLayout.Constraints(6, 95, 993, 527, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contsuppliers.setBounds(new Rectangle(682, 22, 270, 19));
        this.add(contsuppliers, new KDLayout.Constraints(682, 22, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbillState.setBounds(new Rectangle(18, 57, 270, 19));
        this.add(contbillState, new KDLayout.Constraints(18, 57, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //paneBIZLayerControl17
        paneBIZLayerControl17.add(baseTab, resHelper.getString("baseTab.constraints"));
        paneBIZLayerControl17.add(assTab, resHelper.getString("assTab.constraints"));
        //baseTab
baseTab.setLayout(new BorderLayout(0, 0));        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryInfo(),null,false);
        baseTab.add(kdtEntrys_detailPanel, BorderLayout.CENTER);
        //assTab
assTab.setLayout(new BorderLayout(0, 0));        kdtAssEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtAssEntrys,new com.kingdee.eas.fdc.material.MaterialOrderBizBillAssEntryInfo(),null,false);
        assTab.add(kdtAssEntrys_detailPanel, BorderLayout.CENTER);
        //contsuppliers
        contsuppliers.setBoundEditor(prmtsuppliers);
        //contbillState
        contbillState.setBoundEditor(billState);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnPrice);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
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
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
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
        this.toolBar.add(importMes);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);

    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.materialNumber", java.lang.Object.class, this.kdtEntrys, "materialNumber.text");
		dataBinder.registerBinding("entrys.materialName", String.class, this.kdtEntrys, "materialName.text");
		dataBinder.registerBinding("entrys.materialModel", String.class, this.kdtEntrys, "materialModel.text");
		dataBinder.registerBinding("entrys.unit", java.lang.Object.class, this.kdtEntrys, "unit.text");
		dataBinder.registerBinding("entrys.quantity", java.math.BigDecimal.class, this.kdtEntrys, "quantity.text");
		dataBinder.registerBinding("entrys.price", java.math.BigDecimal.class, this.kdtEntrys, "price.text");
		dataBinder.registerBinding("entrys.currency", java.lang.Object.class, this.kdtEntrys, "currency.text");
		dataBinder.registerBinding("entrys.amount", java.math.BigDecimal.class, this.kdtEntrys, "amount.text");
		dataBinder.registerBinding("entrys.originalAmount", java.math.BigDecimal.class, this.kdtEntrys, "originalAmount.text");
		dataBinder.registerBinding("entrys.arriveDate", java.util.Date.class, this.kdtEntrys, "arriveDate.text");
		dataBinder.registerBinding("entrys.curProject", java.lang.Object.class, this.kdtEntrys, "curProject.text");
		dataBinder.registerBinding("entrys.ariverAddr", String.class, this.kdtEntrys, "ariverAddr.text");
		dataBinder.registerBinding("entrys.dept", java.lang.Object.class, this.kdtEntrys, "dept.text");
		dataBinder.registerBinding("entrys.planUser", java.lang.Object.class, this.kdtEntrys, "planUser.text");
		dataBinder.registerBinding("entrys.contractUnit", java.lang.Object.class, this.kdtEntrys, "contractUnit.text");
		dataBinder.registerBinding("entrys.contractBill", java.lang.Object.class, this.kdtEntrys, "contractBill.text");
		dataBinder.registerBinding("entrys.materialContract", java.lang.Object.class, this.kdtEntrys, "materialContract.text");
		dataBinder.registerBinding("entrys.exRate", java.math.BigDecimal.class, this.kdtEntrys, "rate.text");
		dataBinder.registerBinding("AssEntrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtAssEntrys, "id.text");
		dataBinder.registerBinding("AssEntrys", com.kingdee.eas.fdc.material.MaterialOrderBizBillAssEntryInfo.class, this.kdtAssEntrys, "userObject");
		dataBinder.registerBinding("AssEntrys.materialNumber", java.lang.Object.class, this.kdtAssEntrys, "materialNumber.text");
		dataBinder.registerBinding("AssEntrys.materialName", String.class, this.kdtAssEntrys, "materialName.text");
		dataBinder.registerBinding("AssEntrys.materialModel", String.class, this.kdtAssEntrys, "materialModel.text");
		dataBinder.registerBinding("AssEntrys.unit", java.lang.Object.class, this.kdtAssEntrys, "unit.text");
		dataBinder.registerBinding("AssEntrys.quantity", java.math.BigDecimal.class, this.kdtAssEntrys, "quantity.text");
		dataBinder.registerBinding("AssEntrys.price", java.math.BigDecimal.class, this.kdtAssEntrys, "price.text");
		dataBinder.registerBinding("AssEntrys.currency", java.lang.Object.class, this.kdtAssEntrys, "currency.text");
		dataBinder.registerBinding("AssEntrys.amount", java.math.BigDecimal.class, this.kdtAssEntrys, "amount.text");
		dataBinder.registerBinding("AssEntrys.originalAmount", java.math.BigDecimal.class, this.kdtAssEntrys, "originalAmount.text");
		dataBinder.registerBinding("AssEntrys.exRate", java.math.BigDecimal.class, this.kdtAssEntrys, "rate.text");
		dataBinder.registerBinding("suppliers", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtsuppliers, "data");
		dataBinder.registerBinding("billState", com.kingdee.eas.fdc.material.MaterialBillStateEnum.class, this.billState, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.material.app.MaterialOrderBizBillEditUIHandler";
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
        this.kdtAssEntrys.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.material.MaterialOrderBizBillInfo)ov;
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
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.materialNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.materialName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.materialModel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.unit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.quantity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.arriveDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.ariverAddr", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.dept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.planUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.contractUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.contractBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.materialContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.exRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.materialNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.materialName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.materialModel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.unit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.quantity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AssEntrys.exRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("suppliers", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("billState", ValidateHelper.ON_SAVE);    		
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
     * output paneBIZLayerControl17_stateChanged method
     */
    protected void paneBIZLayerControl17_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output paneBIZLayerControl17_mouseClicked method
     */
    protected void paneBIZLayerControl17_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_editStopping method
     */
    protected void kdtEntrys_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_editValueChanged method
     */
    protected void kdtEntrys_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtAssEntrys_editStopping method
     */
    protected void kdtAssEntrys_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtAssEntrys_editStopped method
     */
    protected void kdtAssEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtAssEntrys_activeCellChanged method
     */
    protected void kdtAssEntrys_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output btnAudit_actionPerformed method
     */
    protected void btnAudit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnUnAudit_actionPerformed method
     */
    protected void btnUnAudit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnPrice_actionPerformed method
     */
    protected void btnPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
    sic.add(new SelectorItemInfo("entrys.id"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
        sic.add(new SelectorItemInfo("entrys.materialNumber.*"));
//        sic.add(new SelectorItemInfo("entrys.materialNumber.number"));
    sic.add(new SelectorItemInfo("entrys.materialName"));
    sic.add(new SelectorItemInfo("entrys.materialModel"));
        sic.add(new SelectorItemInfo("entrys.unit.*"));
//        sic.add(new SelectorItemInfo("entrys.unit.number"));
    sic.add(new SelectorItemInfo("entrys.quantity"));
    sic.add(new SelectorItemInfo("entrys.price"));
        sic.add(new SelectorItemInfo("entrys.currency.*"));
//        sic.add(new SelectorItemInfo("entrys.currency.number"));
    sic.add(new SelectorItemInfo("entrys.amount"));
    sic.add(new SelectorItemInfo("entrys.originalAmount"));
    sic.add(new SelectorItemInfo("entrys.arriveDate"));
        sic.add(new SelectorItemInfo("entrys.curProject.*"));
//        sic.add(new SelectorItemInfo("entrys.curProject.number"));
    sic.add(new SelectorItemInfo("entrys.ariverAddr"));
        sic.add(new SelectorItemInfo("entrys.dept.*"));
//        sic.add(new SelectorItemInfo("entrys.dept.number"));
        sic.add(new SelectorItemInfo("entrys.planUser.*"));
//        sic.add(new SelectorItemInfo("entrys.planUser.number"));
        sic.add(new SelectorItemInfo("entrys.contractUnit.*"));
//        sic.add(new SelectorItemInfo("entrys.contractUnit.number"));
        sic.add(new SelectorItemInfo("entrys.contractBill.*"));
//        sic.add(new SelectorItemInfo("entrys.contractBill.number"));
        sic.add(new SelectorItemInfo("entrys.materialContract.*"));
//        sic.add(new SelectorItemInfo("entrys.materialContract.number"));
    sic.add(new SelectorItemInfo("entrys.exRate"));
    sic.add(new SelectorItemInfo("AssEntrys.id"));
        sic.add(new SelectorItemInfo("AssEntrys.*"));
//        sic.add(new SelectorItemInfo("AssEntrys.number"));
        sic.add(new SelectorItemInfo("AssEntrys.materialNumber.*"));
//        sic.add(new SelectorItemInfo("AssEntrys.materialNumber.number"));
    sic.add(new SelectorItemInfo("AssEntrys.materialName"));
    sic.add(new SelectorItemInfo("AssEntrys.materialModel"));
        sic.add(new SelectorItemInfo("AssEntrys.unit.*"));
//        sic.add(new SelectorItemInfo("AssEntrys.unit.number"));
    sic.add(new SelectorItemInfo("AssEntrys.quantity"));
    sic.add(new SelectorItemInfo("AssEntrys.price"));
        sic.add(new SelectorItemInfo("AssEntrys.currency.*"));
//        sic.add(new SelectorItemInfo("AssEntrys.currency.number"));
    sic.add(new SelectorItemInfo("AssEntrys.amount"));
    sic.add(new SelectorItemInfo("AssEntrys.originalAmount"));
    sic.add(new SelectorItemInfo("AssEntrys.exRate"));
        sic.add(new SelectorItemInfo("suppliers.*"));
        sic.add(new SelectorItemInfo("billState"));
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
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionImportMes_actionPerformed method
     */
    public void actionImportMes_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output actionImportMes class
     */     
    protected class actionImportMes extends ItemAction {     
    
        public actionImportMes()
        {
            this(null);
        }

        public actionImportMes(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionImportMes.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImportMes.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImportMes.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMaterialOrderBizBillEditUI.this, "actionImportMes", "actionImportMes_actionPerformed", e);
        }
    }

    /**
     * output actionAudit class
     */     
    protected class actionAudit extends ItemAction {     
    
        public actionAudit()
        {
            this(null);
        }

        public actionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMaterialOrderBizBillEditUI.this, "actionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output actionUnAudit class
     */     
    protected class actionUnAudit extends ItemAction {     
    
        public actionUnAudit()
        {
            this(null);
        }

        public actionUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMaterialOrderBizBillEditUI.this, "actionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.material.client", "MaterialOrderBizBillEditUI");
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
        return com.kingdee.eas.fdc.material.client.MaterialOrderBizBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.material.MaterialOrderBizBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.material.MaterialOrderBizBillInfo objectValue = new com.kingdee.eas.fdc.material.MaterialOrderBizBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/material/MaterialOrderBizBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.material.app.MaterialOrderBizBillQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntrys;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("billState","1");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}