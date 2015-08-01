/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractPaymentLayoutEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPaymentLayoutEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0001;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPlanName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0002;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPlanAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0003;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCashRate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkProgressPayout;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0005;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneBIMUDF0005;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane paneBIZLayerControl21;
    protected com.kingdee.bos.ctrl.swing.KDPanel BIMUDF0006;
    protected com.kingdee.bos.ctrl.swing.KDPanel BIMUDF0007;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPlanMingxi;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtPlanMingxi_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPlam;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtPlam_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDButton btnFenqi;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPayByStages;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtPayByStages_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDTextField ContractPlanNo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnloadLinkAssign;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLoadLink;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kdDeleteRow;
    protected com.kingdee.eas.fdc.contract.PaymentLayoutInfo editData = null;
    protected importReWu importReWu = null;
    protected addNew addNew = null;
    protected DeletePlanMingxi DeletePlanMingxi = null;
    /**
     * output class constructor
     */
    public AbstractPaymentLayoutEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPaymentLayoutEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl s"));
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
        //actionAddNew
        actionAddNew.setEnabled(true);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl n"));
        _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
        actionAddNew.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
        actionAddNew.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.NAME");
        actionAddNew.putValue(ItemAction.NAME, _tempStr);
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //importReWu
        this.importReWu = new importReWu(this);
        getActionManager().registerAction("importReWu", importReWu);
         this.importReWu.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //addNew
        this.addNew = new addNew(this);
        getActionManager().registerAction("addNew", addNew);
         this.addNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //DeletePlanMingxi
        this.DeletePlanMingxi = new DeletePlanMingxi(this);
        getActionManager().registerAction("DeletePlanMingxi", DeletePlanMingxi);
         this.DeletePlanMingxi.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contBIMUDF0001 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPlanName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBIMUDF0002 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPlanAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contBIMUDF0003 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCashRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.chkProgressPayout = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contBIMUDF0005 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPaneBIMUDF0005 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.paneBIZLayerControl21 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.BIMUDF0006 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.BIMUDF0007 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtPlanMingxi = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtPlam = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnFenqi = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kdtPayByStages = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ContractPlanNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnloadLinkAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnLoadLink = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kdDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtNumber.setName("txtNumber");
        this.contBizDate.setName("contBizDate");
        this.pkBizDate.setName("pkBizDate");
        this.contBIMUDF0001.setName("contBIMUDF0001");
        this.txtPlanName.setName("txtPlanName");
        this.contBIMUDF0002.setName("contBIMUDF0002");
        this.txtPlanAmount.setName("txtPlanAmount");
        this.contBIMUDF0003.setName("contBIMUDF0003");
        this.txtCashRate.setName("txtCashRate");
        this.chkProgressPayout.setName("chkProgressPayout");
        this.contBIMUDF0005.setName("contBIMUDF0005");
        this.scrollPaneBIMUDF0005.setName("scrollPaneBIMUDF0005");
        this.txtRemark.setName("txtRemark");
        this.paneBIZLayerControl21.setName("paneBIZLayerControl21");
        this.BIMUDF0006.setName("BIMUDF0006");
        this.BIMUDF0007.setName("BIMUDF0007");
        this.kdtPlanMingxi.setName("kdtPlanMingxi");
        this.kdtPlam.setName("kdtPlam");
        this.btnFenqi.setName("btnFenqi");
        this.kdtPayByStages.setName("kdtPayByStages");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.ContractPlanNo.setName("ContractPlanNo");
        this.btnAddRow.setName("btnAddRow");
        this.btnRemoveRow.setName("btnRemoveRow");
        this.btnloadLinkAssign.setName("btnloadLinkAssign");
        this.btnLoadLink.setName("btnLoadLink");
        this.kdAddRow.setName("kdAddRow");
        this.kdDeleteRow.setName("kdDeleteRow");
        // CoreUI		
        this.menuBar.setVisible(false);		
        this.menuFile.setVisible(false);		
        this.menuHelp.setVisible(false);		
        this.menuTool.setVisible(false);		
        this.btnAddNew.setVisible(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuEdit.setVisible(false);		
        this.menuView.setVisible(false);		
        this.menuBiz.setVisible(false);		
        this.separatorFW1.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuWorkflow.setVisible(false);		
        this.separatorFW8.setVisible(false);		
        this.separatorFW9.setVisible(false);		
        this.separatorFW7.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);		
        this.kDLabelContainer1.setForeground(new java.awt.Color(0,0,0));
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setForeground(new java.awt.Color(0,0,0));		
        this.txtNumber.setRequired(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);		
        this.contBizDate.setForeground(new java.awt.Color(0,0,0));
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);		
        this.pkBizDate.setForeground(new java.awt.Color(0,0,0));		
        this.pkBizDate.setRequired(false);
        // contBIMUDF0001		
        this.contBIMUDF0001.setBoundLabelText(resHelper.getString("contBIMUDF0001.boundLabelText"));		
        this.contBIMUDF0001.setBoundLabelLength(100);		
        this.contBIMUDF0001.setBoundLabelUnderline(true);		
        this.contBIMUDF0001.setVisible(true);		
        this.contBIMUDF0001.setBoundLabelAlignment(7);		
        this.contBIMUDF0001.setForeground(new java.awt.Color(0,0,0));
        // txtPlanName		
        this.txtPlanName.setVisible(true);		
        this.txtPlanName.setHorizontalAlignment(2);		
        this.txtPlanName.setMaxLength(50);		
        this.txtPlanName.setRequired(false);		
        this.txtPlanName.setEnabled(true);		
        this.txtPlanName.setForeground(new java.awt.Color(0,0,0));
        // contBIMUDF0002		
        this.contBIMUDF0002.setBoundLabelText(resHelper.getString("contBIMUDF0002.boundLabelText"));		
        this.contBIMUDF0002.setBoundLabelLength(100);		
        this.contBIMUDF0002.setBoundLabelUnderline(true);		
        this.contBIMUDF0002.setVisible(true);		
        this.contBIMUDF0002.setBoundLabelAlignment(7);		
        this.contBIMUDF0002.setForeground(new java.awt.Color(0,0,0));
        // txtPlanAmount		
        this.txtPlanAmount.setVisible(true);		
        this.txtPlanAmount.setHorizontalAlignment(2);		
        this.txtPlanAmount.setDataType(1);		
        this.txtPlanAmount.setSupportedEmpty(true);		
        this.txtPlanAmount.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtPlanAmount.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtPlanAmount.setPrecision(2);		
        this.txtPlanAmount.setRequired(false);		
        this.txtPlanAmount.setEnabled(false);		
        this.txtPlanAmount.setForeground(new java.awt.Color(0,0,0));
        // contBIMUDF0003		
        this.contBIMUDF0003.setBoundLabelText(resHelper.getString("contBIMUDF0003.boundLabelText"));		
        this.contBIMUDF0003.setBoundLabelLength(100);		
        this.contBIMUDF0003.setBoundLabelUnderline(true);		
        this.contBIMUDF0003.setVisible(true);		
        this.contBIMUDF0003.setBoundLabelAlignment(7);		
        this.contBIMUDF0003.setForeground(new java.awt.Color(0,0,0));
        // txtCashRate		
        this.txtCashRate.setVisible(true);		
        this.txtCashRate.setHorizontalAlignment(2);		
        this.txtCashRate.setDataType(1);		
        this.txtCashRate.setSupportedEmpty(true);		
        this.txtCashRate.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtCashRate.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtCashRate.setPrecision(10);		
        this.txtCashRate.setRequired(false);		
        this.txtCashRate.setEnabled(true);		
        this.txtCashRate.setForeground(new java.awt.Color(0,0,0));
        // chkProgressPayout		
        this.chkProgressPayout.setText(resHelper.getString("chkProgressPayout.text"));		
        this.chkProgressPayout.setVisible(true);		
        this.chkProgressPayout.setHorizontalAlignment(2);		
        this.chkProgressPayout.setEnabled(true);		
        this.chkProgressPayout.setForeground(new java.awt.Color(0,0,0));
        this.chkProgressPayout.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    chkProgressPayout_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contBIMUDF0005		
        this.contBIMUDF0005.setBoundLabelText(resHelper.getString("contBIMUDF0005.boundLabelText"));		
        this.contBIMUDF0005.setBoundLabelLength(100);		
        this.contBIMUDF0005.setBoundLabelUnderline(true);		
        this.contBIMUDF0005.setVisible(true);		
        this.contBIMUDF0005.setBoundLabelAlignment(7);		
        this.contBIMUDF0005.setForeground(new java.awt.Color(0,0,0));
        // scrollPaneBIMUDF0005
        // txtRemark		
        this.txtRemark.setVisible(true);		
        this.txtRemark.setRequired(false);		
        this.txtRemark.setMaxLength(255);		
        this.txtRemark.setEnabled(true);		
        this.txtRemark.setForeground(new java.awt.Color(0,0,0));
        // paneBIZLayerControl21		
        this.paneBIZLayerControl21.setVisible(true);
        this.paneBIZLayerControl21.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    paneBIZLayerControl21_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // BIMUDF0006		
        this.BIMUDF0006.setVisible(true);
        // BIMUDF0007		
        this.BIMUDF0007.setVisible(true);
        // kdtPlanMingxi
		String kdtPlanMingxiStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-M-d</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-M-d</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>0</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-M-d</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>0.00</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>###,###.00</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"shixiang\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"shixiangmx\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"planStratDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"planEndDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"dayNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"planDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"fukuanBili\" t:width=\"110\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"yingfuMoney\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"remark\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"isEdit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{shixiang}</t:Cell><t:Cell>$Resource{shixiangmx}</t:Cell><t:Cell>$Resource{planStratDate}</t:Cell><t:Cell>$Resource{planEndDate}</t:Cell><t:Cell>$Resource{dayNumber}</t:Cell><t:Cell>$Resource{planDate}</t:Cell><t:Cell>$Resource{fukuanBili}</t:Cell><t:Cell>$Resource{yingfuMoney}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{isEdit}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"0\" t:right=\"1\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtPlanMingxi.setFormatXml(resHelper.translateString("kdtPlanMingxi",kdtPlanMingxiStrXML));
        this.kdtPlanMingxi.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtPlanMingxi_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtPlanMingxi.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtPlanMingxi_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtPlanMingxi.putBindContents("editData",new String[] {"shixiang","shixiangmx","planStratDate","planEndDate","dayNumber","planDate","fukuanBili","yingfuMoney","remark","isEdit"});


        // kdtPlam
		String kdtPlamStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>0.00</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>###,###.00</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###,###.00</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>###,###.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"planPayMonth\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"planPayBili\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"planPaymoney\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"leiJiMoney\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"leiJiBili\" t:width=\"110\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"remark\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{planPayMonth}</t:Cell><t:Cell>$Resource{planPayBili}</t:Cell><t:Cell>$Resource{planPaymoney}</t:Cell><t:Cell>$Resource{leiJiMoney}</t:Cell><t:Cell>$Resource{leiJiBili}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtPlam.setFormatXml(resHelper.translateString("kdtPlam",kdtPlamStrXML));

                this.kdtPlam.putBindContents("editData",new String[] {"planPayMonth","planPayBili","planPaymoney","leiJiMoney","leiJiBili","remark"});


        // btnFenqi		
        this.btnFenqi.setText(resHelper.getString("btnFenqi.text"));		
        this.btnFenqi.setVisible(true);		
        this.btnFenqi.setHorizontalAlignment(2);		
        this.btnFenqi.setEnabled(true);		
        this.btnFenqi.setForeground(new java.awt.Color(0,0,0));
        this.btnFenqi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFenqi_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFenqi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    btnFenqi_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kdtPayByStages
		String kdtPayByStagesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>yyyy-M-d</c:NumberFormat></c:Style><c:Style id=\"sCol1\"><c:NumberFormat>yyyy-M-d</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>###,###.00</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###,###.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"month\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"payDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"payBili\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"payMoney\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"remark\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{month}</t:Cell><t:Cell>$Resource{payDate}</t:Cell><t:Cell>$Resource{payBili}</t:Cell><t:Cell>$Resource{payMoney}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtPayByStages.setFormatXml(resHelper.translateString("kdtPayByStages",kdtPayByStagesStrXML));
        this.kdtPayByStages.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtPayByStages_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtPayByStages.putBindContents("editData",new String[] {"month","payDate","payBili","payMoney","remark"});


        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setVisible(false);
        // ContractPlanNo		
        this.ContractPlanNo.setVisible(false);
        // btnAddRow		
        this.btnAddRow.setText(resHelper.getString("btnAddRow.text"));		
        this.btnAddRow.setOpaque(true);		
        this.btnAddRow.setDisplayedMnemonicIndex(1);		
        this.btnAddRow.setSelected(true);		
        this.btnAddRow.setRolloverEnabled(true);
        this.btnAddRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRemoveRow		
        this.btnRemoveRow.setText(resHelper.getString("btnRemoveRow.text"));
        this.btnRemoveRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRemoveRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnloadLinkAssign		
        this.btnloadLinkAssign.setText(resHelper.getString("btnloadLinkAssign.text"));
        this.btnloadLinkAssign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnloadLinkAssign_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnLoadLink		
        this.btnLoadLink.setText(resHelper.getString("btnLoadLink.text"));		
        this.btnLoadLink.setToolTipText(resHelper.getString("btnLoadLink.toolTipText"));		
        this.btnLoadLink.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showlist"));
        this.btnLoadLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnLoadLink_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kdAddRow		
        this.kdAddRow.setText(resHelper.getString("kdAddRow.text"));		
        this.kdAddRow.setToolTipText(resHelper.getString("kdAddRow.toolTipText"));		
        this.kdAddRow.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        this.kdAddRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kdAddRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kdDeleteRow		
        this.kdDeleteRow.setText(resHelper.getString("kdDeleteRow.text"));		
        this.kdDeleteRow.setToolTipText(resHelper.getString("kdDeleteRow.toolTipText"));		
        this.kdDeleteRow.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        this.kdDeleteRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kdDeleteRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtNumber,txtPlanName,pkBizDate,txtPlanAmount,txtCashRate,chkProgressPayout,txtRemark,kdtPlanMingxi,kdtPlam,btnFenqi,kdtPayByStages}));
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
        this.setBounds(new Rectangle(0, 0, 1013, 600));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(40, 19, 270, 19));
        this.add(kDLabelContainer1, null);
        contBizDate.setBounds(new Rectangle(664, 19, 270, 19));
        this.add(contBizDate, null);
        contBIMUDF0001.setBounds(new Rectangle(347, 19, 270, 19));
        this.add(contBIMUDF0001, null);
        contBIMUDF0002.setBounds(new Rectangle(40, 47, 270, 19));
        this.add(contBIMUDF0002, null);
        contBIMUDF0003.setBounds(new Rectangle(347, 47, 270, 19));
        this.add(contBIMUDF0003, null);
        chkProgressPayout.setBounds(new Rectangle(664, 47, 270, 19));
        this.add(chkProgressPayout, null);
        contBIMUDF0005.setBounds(new Rectangle(40, 75, 893, 36));
        this.add(contBIMUDF0005, null);
        paneBIZLayerControl21.setBounds(new Rectangle(40, 121, 970, 369));
        this.add(paneBIZLayerControl21, null);
        btnFenqi.setBounds(new Rectangle(40, 501, 120, 20));
        this.add(btnFenqi, null);
        kdtPayByStages.setBounds(new Rectangle(40, 525, 970, 258));
        kdtPayByStages_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtPayByStages,new com.kingdee.eas.fdc.contract.PaymentLayoutPayByStageInfo(),null,false);
        this.add(kdtPayByStages_detailPanel, null);
        kDLabelContainer2.setBounds(new Rectangle(810, 44, 246, 21));
        this.add(kDLabelContainer2, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contBIMUDF0001
        contBIMUDF0001.setBoundEditor(txtPlanName);
        //contBIMUDF0002
        contBIMUDF0002.setBoundEditor(txtPlanAmount);
        //contBIMUDF0003
        contBIMUDF0003.setBoundEditor(txtCashRate);
        //contBIMUDF0005
        contBIMUDF0005.setBoundEditor(scrollPaneBIMUDF0005);
        //scrollPaneBIMUDF0005
        scrollPaneBIMUDF0005.getViewport().add(txtRemark, null);
        //paneBIZLayerControl21
        paneBIZLayerControl21.add(BIMUDF0006, resHelper.getString("BIMUDF0006.constraints"));
        paneBIZLayerControl21.add(BIMUDF0007, resHelper.getString("BIMUDF0007.constraints"));
        //BIMUDF0006
        BIMUDF0006.setLayout(null);        kdtPlanMingxi.setBounds(new Rectangle(0, -1, 963, 340));
        kdtPlanMingxi_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtPlanMingxi,new com.kingdee.eas.fdc.contract.PaymentLayoutPlanMingxiInfo(),null,false);
        BIMUDF0006.add(kdtPlanMingxi_detailPanel, null);
        btnAddRow.setBounds(new Rectangle(804, 2, 70, 19));
        BIMUDF0006.add(btnAddRow, null);
        btnRemoveRow.setBounds(new Rectangle(881, 2, 74, 19));
        BIMUDF0006.add(btnRemoveRow, null);
        btnloadLinkAssign.setBounds(new Rectangle(691, 2, 107, 19));
        BIMUDF0006.add(btnloadLinkAssign, null);
        //BIMUDF0007
        BIMUDF0007.setLayout(null);        kdtPlam.setBounds(new Rectangle(1, 0, 956, 339));
        kdtPlam_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtPlam,new com.kingdee.eas.fdc.contract.PaymentLayoutPlamInfo(),null,false);
        BIMUDF0007.add(kdtPlam_detailPanel, null);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(ContractPlanNo);

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
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnLoadLink);
        this.toolBar.add(kdAddRow);
        this.toolBar.add(kdDeleteRow);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("PlanName", String.class, this.txtPlanName, "text");
		dataBinder.registerBinding("PlanAmount", java.math.BigDecimal.class, this.txtPlanAmount, "value");
		dataBinder.registerBinding("CashRate", java.math.BigDecimal.class, this.txtCashRate, "value");
		dataBinder.registerBinding("ProgressPayout", boolean.class, this.chkProgressPayout, "selected");
		dataBinder.registerBinding("Remark", String.class, this.txtRemark, "text");
		dataBinder.registerBinding("PlanMingxi.shixiang", String.class, this.kdtPlanMingxi, "shixiang.text");
		dataBinder.registerBinding("PlanMingxi", com.kingdee.eas.fdc.contract.PaymentLayoutPlanMingxiInfo.class, this.kdtPlanMingxi, "userObject");
		dataBinder.registerBinding("PlanMingxi.planStratDate", java.util.Date.class, this.kdtPlanMingxi, "planStratDate.text");
		dataBinder.registerBinding("PlanMingxi.planEndDate", java.util.Date.class, this.kdtPlanMingxi, "planEndDate.text");
		dataBinder.registerBinding("PlanMingxi.planDate", java.util.Date.class, this.kdtPlanMingxi, "planDate.text");
		dataBinder.registerBinding("PlanMingxi.dayNumber", java.math.BigDecimal.class, this.kdtPlanMingxi, "dayNumber.text");
		dataBinder.registerBinding("PlanMingxi.fukuanBili", java.math.BigDecimal.class, this.kdtPlanMingxi, "fukuanBili.text");
		dataBinder.registerBinding("PlanMingxi.yingfuMoney", java.math.BigDecimal.class, this.kdtPlanMingxi, "yingfuMoney.text");
		dataBinder.registerBinding("PlanMingxi.remark", String.class, this.kdtPlanMingxi, "remark.text");
		dataBinder.registerBinding("PlanMingxi.shixiangmx", String.class, this.kdtPlanMingxi, "shixiangmx.text");
		dataBinder.registerBinding("PlanMingxi.isEdit", char.class, this.kdtPlanMingxi, "isEdit.text");
		dataBinder.registerBinding("Plam.planPayMonth", java.util.Date.class, this.kdtPlam, "planPayMonth.text");
		dataBinder.registerBinding("Plam", com.kingdee.eas.fdc.contract.PaymentLayoutPlamInfo.class, this.kdtPlam, "userObject");
		dataBinder.registerBinding("Plam.planPayBili", java.math.BigDecimal.class, this.kdtPlam, "planPayBili.text");
		dataBinder.registerBinding("Plam.planPaymoney", java.math.BigDecimal.class, this.kdtPlam, "planPaymoney.text");
		dataBinder.registerBinding("Plam.leiJiMoney", java.math.BigDecimal.class, this.kdtPlam, "leiJiMoney.text");
		dataBinder.registerBinding("Plam.leiJiBili", java.math.BigDecimal.class, this.kdtPlam, "leiJiBili.text");
		dataBinder.registerBinding("Plam.remark", String.class, this.kdtPlam, "remark.text");
		dataBinder.registerBinding("PayByStages", com.kingdee.eas.fdc.contract.PaymentLayoutPayByStageInfo.class, this.kdtPayByStages, "userObject");
		dataBinder.registerBinding("PayByStages.payDate", java.util.Date.class, this.kdtPayByStages, "payDate.text");
		dataBinder.registerBinding("PayByStages.month", java.util.Date.class, this.kdtPayByStages, "month.text");
		dataBinder.registerBinding("PayByStages.payBili", java.math.BigDecimal.class, this.kdtPayByStages, "payBili.text");
		dataBinder.registerBinding("PayByStages.payMoney", java.math.BigDecimal.class, this.kdtPayByStages, "payMoney.text");
		dataBinder.registerBinding("PayByStages.remark", String.class, this.kdtPayByStages, "remark.text");
		dataBinder.registerBinding("ContractPlanNo", String.class, this.ContractPlanNo, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.PaymentLayoutEditUIHandler";
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
        this.txtNumber.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.contract.PaymentLayoutInfo)ov;
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
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
		getValidateHelper().registerBindProperty("PlanName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CashRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ProgressPayout", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.shixiang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.planStratDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.planEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.planDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.dayNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.fukuanBili", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.yingfuMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.shixiangmx", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PlanMingxi.isEdit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.planPayMonth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.planPayBili", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.planPaymoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.leiJiMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.leiJiBili", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Plam.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayByStages", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayByStages.payDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayByStages.month", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayByStages.payBili", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayByStages.payMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PayByStages.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ContractPlanNo", ValidateHelper.ON_SAVE);    		
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
     * output chkProgressPayout_itemStateChanged method
     */
    protected void chkProgressPayout_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output paneBIZLayerControl21_stateChanged method
     */
    protected void paneBIZLayerControl21_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtPlanMingxi_editStopped method
     */
    protected void kdtPlanMingxi_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtPlanMingxi_tableClicked method
     */
    protected void kdtPlanMingxi_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnFenqi_mouseClicked method
     */
    protected void btnFenqi_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFenqi_actionPerformed method
     */
    protected void btnFenqi_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output kdtPayByStages_editStopped method
     */
    protected void kdtPayByStages_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnAddRow_actionPerformed method
     */
    protected void btnAddRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnRemoveRow_actionPerformed method
     */
    protected void btnRemoveRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnloadLinkAssign_actionPerformed method
     */
    protected void btnloadLinkAssign_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnLoadLink_actionPerformed method
     */
    protected void btnLoadLink_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdAddRow_actionPerformed method
     */
    protected void kdAddRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdDeleteRow_actionPerformed method
     */
    protected void kdDeleteRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("PlanName"));
        sic.add(new SelectorItemInfo("PlanAmount"));
        sic.add(new SelectorItemInfo("CashRate"));
        sic.add(new SelectorItemInfo("ProgressPayout"));
        sic.add(new SelectorItemInfo("Remark"));
    sic.add(new SelectorItemInfo("PlanMingxi.shixiang"));
        sic.add(new SelectorItemInfo("PlanMingxi.*"));
//        sic.add(new SelectorItemInfo("PlanMingxi.number"));
    sic.add(new SelectorItemInfo("PlanMingxi.planStratDate"));
    sic.add(new SelectorItemInfo("PlanMingxi.planEndDate"));
    sic.add(new SelectorItemInfo("PlanMingxi.planDate"));
    sic.add(new SelectorItemInfo("PlanMingxi.dayNumber"));
    sic.add(new SelectorItemInfo("PlanMingxi.fukuanBili"));
    sic.add(new SelectorItemInfo("PlanMingxi.yingfuMoney"));
    sic.add(new SelectorItemInfo("PlanMingxi.remark"));
        sic.add(new SelectorItemInfo("PlanMingxi.shixiangmx.*"));
//        sic.add(new SelectorItemInfo("PlanMingxi.shixiangmx.number"));
    sic.add(new SelectorItemInfo("PlanMingxi.isEdit"));
    sic.add(new SelectorItemInfo("Plam.planPayMonth"));
        sic.add(new SelectorItemInfo("Plam.*"));
//        sic.add(new SelectorItemInfo("Plam.number"));
    sic.add(new SelectorItemInfo("Plam.planPayBili"));
    sic.add(new SelectorItemInfo("Plam.planPaymoney"));
    sic.add(new SelectorItemInfo("Plam.leiJiMoney"));
    sic.add(new SelectorItemInfo("Plam.leiJiBili"));
    sic.add(new SelectorItemInfo("Plam.remark"));
        sic.add(new SelectorItemInfo("PayByStages.*"));
//        sic.add(new SelectorItemInfo("PayByStages.number"));
    sic.add(new SelectorItemInfo("PayByStages.payDate"));
    sic.add(new SelectorItemInfo("PayByStages.month"));
    sic.add(new SelectorItemInfo("PayByStages.payBili"));
    sic.add(new SelectorItemInfo("PayByStages.payMoney"));
    sic.add(new SelectorItemInfo("PayByStages.remark"));
        sic.add(new SelectorItemInfo("ContractPlanNo"));
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
        super.actionPrint_actionPerformed(e);
         //write your code here
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
         //write your code here
    }
    	

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    	

    /**
     * output importReWu_actionPerformed method
     */
    public void importReWu_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output addNew_actionPerformed method
     */
    public void addNew_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output deletePlanMingxi_actionPerformed method
     */
    public void deletePlanMingxi_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAddNew(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNew() {
    	return false;
    }
	public RequestContext prepareimportReWu(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareimportReWu() {
    	return false;
    }
	public RequestContext prepareaddNew(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareaddNew() {
    	return false;
    }
	public RequestContext prepareDeletePlanMingxi(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareDeletePlanMingxi() {
    	return false;
    }

    /**
     * output importReWu class
     */     
    protected class importReWu extends ItemAction {     
    
        public importReWu()
        {
            this(null);
        }

        public importReWu(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("importReWu.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("importReWu.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("importReWu.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentLayoutEditUI.this, "importReWu", "importReWu_actionPerformed", e);
        }
    }

    /**
     * output addNew class
     */     
    protected class addNew extends ItemAction {     
    
        public addNew()
        {
            this(null);
        }

        public addNew(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("addNew.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("addNew.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("addNew.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentLayoutEditUI.this, "addNew", "addNew_actionPerformed", e);
        }
    }

    /**
     * output DeletePlanMingxi class
     */     
    protected class DeletePlanMingxi extends ItemAction {     
    
        public DeletePlanMingxi()
        {
            this(null);
        }

        public DeletePlanMingxi(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("DeletePlanMingxi.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("DeletePlanMingxi.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("DeletePlanMingxi.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPaymentLayoutEditUI.this, "DeletePlanMingxi", "deletePlanMingxi_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "PaymentLayoutEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}