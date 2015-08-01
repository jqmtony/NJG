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
public abstract class AbstractMaterialConfirmBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMaterialConfirmBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMainContractName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplyAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDateSupplyAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConfirmAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDateConfirmAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPaidAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDatePaidAmt;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMaterialContractBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMainContractBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMaterialContractName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMaterialConPartB;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMainContractPartB;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMaterialConAmt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplyDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDesc;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMainContractName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSupplyAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtToDateSupplyAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtConfirmAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtToDateConfirmAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOrigialAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtToDatePaidAmt;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMaterialContractBill;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMainContractBill;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMaterialContractName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMaterialConPartB;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCurrency;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMainContractPartB;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMaterialConAmt;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkSupplyDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPaidAmt;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportMaterialEntry;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportMaterialOrder;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportMatEntry;
    protected com.kingdee.eas.fdc.material.MaterialConfirmBillInfo editData = null;
    protected ActionImportMaterialEntry actionImportMaterialEntry = null;
    protected ActionImportMaterialOrder actionImportMaterialOrder = null;
    /**
     * output class constructor
     */
    public AbstractMaterialConfirmBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMaterialConfirmBillEditUI.class.getName());
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
        actionUnAudit.setEnabled(false);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportMaterialEntry
        this.actionImportMaterialEntry = new ActionImportMaterialEntry(this);
        getActionManager().registerAction("actionImportMaterialEntry", actionImportMaterialEntry);
         this.actionImportMaterialEntry.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportMaterialOrder
        this.actionImportMaterialOrder = new ActionImportMaterialOrder(this);
        getActionManager().registerAction("actionImportMaterialOrder", actionImportMaterialOrder);
         this.actionImportMaterialOrder.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMainContractName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplyAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDateSupplyAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConfirmAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDateConfirmAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPaidAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDatePaidAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contMaterialContractBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMainContractBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMaterialContractName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMaterialConPartB = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMainContractPartB = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMaterialConAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplyDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDesc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtMainContractName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtSupplyAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtToDateSupplyAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtConfirmAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtToDateConfirmAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOrigialAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtToDatePaidAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtMaterialContractBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMainContractBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtMaterialContractName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtMaterialConPartB = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCurrency = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtMainContractPartB = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtMaterialConAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkSupplyDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtPaidAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnImportMaterialEntry = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportMaterialOrder = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemImportMatEntry = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contMainContractName.setName("contMainContractName");
        this.contAuditTime.setName("contAuditTime");
        this.contSupplyAmt.setName("contSupplyAmt");
        this.contToDateSupplyAmt.setName("contToDateSupplyAmt");
        this.contConfirmAmt.setName("contConfirmAmt");
        this.contToDateConfirmAmt.setName("contToDateConfirmAmt");
        this.contPaidAmt.setName("contPaidAmt");
        this.contToDatePaidAmt.setName("contToDatePaidAmt");
        this.kdtEntrys.setName("kdtEntrys");
        this.contMaterialContractBill.setName("contMaterialContractBill");
        this.contMainContractBill.setName("contMainContractBill");
        this.contMaterialContractName.setName("contMaterialContractName");
        this.contMaterialConPartB.setName("contMaterialConPartB");
        this.contCurrency.setName("contCurrency");
        this.contMainContractPartB.setName("contMainContractPartB");
        this.contMaterialConAmt.setName("contMaterialConAmt");
        this.contRate.setName("contRate");
        this.contSupplyDate.setName("contSupplyDate");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.txtDesc.setName("txtDesc");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtMainContractName.setName("txtMainContractName");
        this.pkAuditTime.setName("pkAuditTime");
        this.txtSupplyAmt.setName("txtSupplyAmt");
        this.txtToDateSupplyAmt.setName("txtToDateSupplyAmt");
        this.txtConfirmAmt.setName("txtConfirmAmt");
        this.txtToDateConfirmAmt.setName("txtToDateConfirmAmt");
        this.txtOrigialAmount.setName("txtOrigialAmount");
        this.txtToDatePaidAmt.setName("txtToDatePaidAmt");
        this.prmtMaterialContractBill.setName("prmtMaterialContractBill");
        this.prmtMainContractBill.setName("prmtMainContractBill");
        this.txtMaterialContractName.setName("txtMaterialContractName");
        this.txtMaterialConPartB.setName("txtMaterialConPartB");
        this.txtCurrency.setName("txtCurrency");
        this.txtMainContractPartB.setName("txtMainContractPartB");
        this.txtMaterialConAmt.setName("txtMaterialConAmt");
        this.txtRate.setName("txtRate");
        this.pkSupplyDate.setName("pkSupplyDate");
        this.txtPaidAmt.setName("txtPaidAmt");
        this.btnImportMaterialEntry.setName("btnImportMaterialEntry");
        this.btnImportMaterialOrder.setName("btnImportMaterialOrder");
        this.menuItemImportMatEntry.setName("menuItemImportMatEntry");
        // CoreUI		
        this.setMinimumSize(new Dimension(10,18));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contMainContractName		
        this.contMainContractName.setBoundLabelText(resHelper.getString("contMainContractName.boundLabelText"));		
        this.contMainContractName.setBoundLabelLength(100);		
        this.contMainContractName.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contSupplyAmt		
        this.contSupplyAmt.setBoundLabelText(resHelper.getString("contSupplyAmt.boundLabelText"));		
        this.contSupplyAmt.setBoundLabelLength(100);		
        this.contSupplyAmt.setBoundLabelUnderline(true);
        // contToDateSupplyAmt		
        this.contToDateSupplyAmt.setBoundLabelText(resHelper.getString("contToDateSupplyAmt.boundLabelText"));		
        this.contToDateSupplyAmt.setBoundLabelLength(100);		
        this.contToDateSupplyAmt.setBoundLabelUnderline(true);
        // contConfirmAmt		
        this.contConfirmAmt.setBoundLabelText(resHelper.getString("contConfirmAmt.boundLabelText"));		
        this.contConfirmAmt.setBoundLabelLength(100);		
        this.contConfirmAmt.setBoundLabelUnderline(true);
        // contToDateConfirmAmt		
        this.contToDateConfirmAmt.setBoundLabelText(resHelper.getString("contToDateConfirmAmt.boundLabelText"));		
        this.contToDateConfirmAmt.setBoundLabelLength(100);		
        this.contToDateConfirmAmt.setBoundLabelUnderline(true);
        // contPaidAmt		
        this.contPaidAmt.setBoundLabelText(resHelper.getString("contPaidAmt.boundLabelText"));		
        this.contPaidAmt.setBoundLabelLength(100);		
        this.contPaidAmt.setBoundLabelUnderline(true);
        // contToDatePaidAmt		
        this.contToDatePaidAmt.setBoundLabelText(resHelper.getString("contToDatePaidAmt.boundLabelText"));		
        this.contToDatePaidAmt.setBoundLabelLength(100);		
        this.contToDatePaidAmt.setBoundLabelUnderline(true);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>#,##0.0000;-#,##0.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:Protection locked=\"true\" /><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"materialNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"materialName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"model\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"originalPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"quantity\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"confirmOriginalPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"confirmPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"confirmOriAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"confirmAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"section\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"desc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"partAMaterialEntryId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"materialId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{materialNumber}</t:Cell><t:Cell>$Resource{materialName}</t:Cell><t:Cell>$Resource{model}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{originalPrice}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{quantity}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{confirmOriginalPrice}</t:Cell><t:Cell>$Resource{confirmPrice}</t:Cell><t:Cell>$Resource{confirmOriAmt}</t:Cell><t:Cell>$Resource{confirmAmount}</t:Cell><t:Cell>$Resource{section}</t:Cell><t:Cell>$Resource{desc}</t:Cell><t:Cell>$Resource{partAMaterialEntryId}</t:Cell><t:Cell>$Resource{materialId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"","material.number","material.name","material.model","material.baseUnit.name","partAMaterialEntry.originalPrice","partAMaterialEntry.price","quantity","partAMaterialEntry.amount","originalPrice","price","originalAmount","amount","section","desc","partAMaterialEntry.id","partAMaterialEntry.material.id"});


        // contMaterialContractBill		
        this.contMaterialContractBill.setBoundLabelText(resHelper.getString("contMaterialContractBill.boundLabelText"));		
        this.contMaterialContractBill.setBoundLabelLength(100);		
        this.contMaterialContractBill.setBoundLabelUnderline(true);
        // contMainContractBill		
        this.contMainContractBill.setBoundLabelText(resHelper.getString("contMainContractBill.boundLabelText"));		
        this.contMainContractBill.setBoundLabelLength(100);		
        this.contMainContractBill.setBoundLabelUnderline(true);
        // contMaterialContractName		
        this.contMaterialContractName.setBoundLabelText(resHelper.getString("contMaterialContractName.boundLabelText"));		
        this.contMaterialContractName.setBoundLabelLength(100);		
        this.contMaterialContractName.setBoundLabelUnderline(true);
        // contMaterialConPartB		
        this.contMaterialConPartB.setBoundLabelText(resHelper.getString("contMaterialConPartB.boundLabelText"));		
        this.contMaterialConPartB.setBoundLabelLength(100);		
        this.contMaterialConPartB.setBoundLabelUnderline(true);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // contMainContractPartB		
        this.contMainContractPartB.setBoundLabelText(resHelper.getString("contMainContractPartB.boundLabelText"));		
        this.contMainContractPartB.setBoundLabelLength(100);		
        this.contMainContractPartB.setBoundLabelUnderline(true);
        // contMaterialConAmt		
        this.contMaterialConAmt.setBoundLabelText(resHelper.getString("contMaterialConAmt.boundLabelText"));		
        this.contMaterialConAmt.setBoundLabelLength(100);		
        this.contMaterialConAmt.setBoundLabelUnderline(true);
        // contRate		
        this.contRate.setBoundLabelText(resHelper.getString("contRate.boundLabelText"));		
        this.contRate.setBoundLabelLength(100);		
        this.contRate.setBoundLabelUnderline(true);
        // contSupplyDate		
        this.contSupplyDate.setBoundLabelText(resHelper.getString("contSupplyDate.boundLabelText"));		
        this.contSupplyDate.setBoundLabelUnderline(true);		
        this.contSupplyDate.setBoundLabelLength(100);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setVisible(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setRequired(true);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);		
        this.pkCreateTime.setRequired(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // txtDesc
        // prmtAuditor		
        this.prmtAuditor.setRequired(true);		
        this.prmtAuditor.setEnabled(false);
        // txtMainContractName		
        this.txtMainContractName.setRequired(true);		
        this.txtMainContractName.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);		
        this.pkAuditTime.setRequired(true);
        // txtSupplyAmt		
        this.txtSupplyAmt.setDataType(1);		
        this.txtSupplyAmt.setEnabled(false);		
        this.txtSupplyAmt.setRequired(true);
        // txtToDateSupplyAmt		
        this.txtToDateSupplyAmt.setDataType(1);		
        this.txtToDateSupplyAmt.setEnabled(false);		
        this.txtToDateSupplyAmt.setRequired(true);
        // txtConfirmAmt		
        this.txtConfirmAmt.setDataType(1);		
        this.txtConfirmAmt.setRequired(true);		
        this.txtConfirmAmt.setEnabled(false);
        // txtToDateConfirmAmt		
        this.txtToDateConfirmAmt.setDataType(1);		
        this.txtToDateConfirmAmt.setEnabled(false);		
        this.txtToDateConfirmAmt.setRequired(true);
        // txtOrigialAmount		
        this.txtOrigialAmount.setDataType(1);		
        this.txtOrigialAmount.setEnabled(false);
        // txtToDatePaidAmt		
        this.txtToDatePaidAmt.setDataType(1);		
        this.txtToDatePaidAmt.setEnabled(false);
        // prmtMaterialContractBill		
        this.prmtMaterialContractBill.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBillQuery");		
        this.prmtMaterialContractBill.setRequired(true);		
        this.prmtMaterialContractBill.setDisplayFormat("$number$");		
        this.prmtMaterialContractBill.setEditFormat("$number$");		
        this.prmtMaterialContractBill.setCommitFormat("$number$");
        this.prmtMaterialContractBill.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtMaterialContractBill_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtMainContractBill		
        this.prmtMainContractBill.setDisplayFormat("$number$");		
        this.prmtMainContractBill.setEditFormat("$number$");		
        this.prmtMainContractBill.setRequired(true);		
        this.prmtMainContractBill.setEnabled(false);
        // txtMaterialContractName		
        this.txtMaterialContractName.setEnabled(false);		
        this.txtMaterialContractName.setRequired(true);
        // txtMaterialConPartB		
        this.txtMaterialConPartB.setRequired(true);		
        this.txtMaterialConPartB.setEnabled(false);
        // txtCurrency		
        this.txtCurrency.setEnabled(false);		
        this.txtCurrency.setRequired(true);
        // txtMainContractPartB		
        this.txtMainContractPartB.setEnabled(false);		
        this.txtMainContractPartB.setRequired(true);
        // txtMaterialConAmt		
        this.txtMaterialConAmt.setDataType(1);		
        this.txtMaterialConAmt.setEnabled(false);		
        this.txtMaterialConAmt.setRequired(true);		
        this.txtMaterialConAmt.setPrecision(2);
        // txtRate		
        this.txtRate.setDataType(1);		
        this.txtRate.setEnabled(false);		
        this.txtRate.setRequired(true);		
        this.txtRate.setPrecision(2);
        // pkSupplyDate
        // txtPaidAmt		
        this.txtPaidAmt.setDataType(1);
        // btnImportMaterialEntry
        this.btnImportMaterialEntry.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportMaterialEntry, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportMaterialEntry.setText(resHelper.getString("btnImportMaterialEntry.text"));		
        this.btnImportMaterialEntry.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addsinglefile"));
        // btnImportMaterialOrder
        this.btnImportMaterialOrder.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportMaterialOrder, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportMaterialOrder.setText(resHelper.getString("btnImportMaterialOrder.text"));		
        this.btnImportMaterialOrder.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addsinglefile"));
        // menuItemImportMatEntry
        this.menuItemImportMatEntry.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportMaterialEntry, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportMatEntry.setText(resHelper.getString("menuItemImportMatEntry.text"));		
        this.menuItemImportMatEntry.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addsinglefile"));
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
        contCreator.setBounds(new Rectangle(16, 570, 471, 19));
        this.add(contCreator, new KDLayout.Constraints(16, 570, 471, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(526, 570, 471, 19));
        this.add(contCreateTime, new KDLayout.Constraints(526, 570, 471, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(14, 19, 623, 19));
        this.add(contNumber, new KDLayout.Constraints(14, 19, 623, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(16, 178, 981, 54));
        this.add(contDescription, new KDLayout.Constraints(16, 178, 981, 54, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(16, 598, 471, 19));
        this.add(contAuditor, new KDLayout.Constraints(16, 598, 471, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMainContractName.setBounds(new Rectangle(368, 45, 270, 19));
        this.add(contMainContractName, new KDLayout.Constraints(368, 45, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(526, 598, 471, 19));
        this.add(contAuditTime, new KDLayout.Constraints(526, 598, 471, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSupplyAmt.setBounds(new Rectangle(16, 127, 270, 19));
        this.add(contSupplyAmt, new KDLayout.Constraints(16, 127, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contToDateSupplyAmt.setBounds(new Rectangle(16, 154, 270, 19));
        this.add(contToDateSupplyAmt, new KDLayout.Constraints(16, 154, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contConfirmAmt.setBounds(new Rectangle(368, 127, 270, 19));
        this.add(contConfirmAmt, new KDLayout.Constraints(368, 127, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contToDateConfirmAmt.setBounds(new Rectangle(368, 154, 270, 19));
        this.add(contToDateConfirmAmt, new KDLayout.Constraints(368, 154, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPaidAmt.setBounds(new Rectangle(723, 127, 270, 19));
        this.add(contPaidAmt, new KDLayout.Constraints(723, 127, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contToDatePaidAmt.setBounds(new Rectangle(723, 154, 270, 19));
        this.add(contToDatePaidAmt, new KDLayout.Constraints(723, 154, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntrys.setBounds(new Rectangle(16, 241, 981, 320));
        this.add(kdtEntrys, new KDLayout.Constraints(16, 241, 981, 320, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contMaterialContractBill.setBounds(new Rectangle(16, 73, 270, 19));
        this.add(contMaterialContractBill, new KDLayout.Constraints(16, 73, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMainContractBill.setBounds(new Rectangle(16, 46, 270, 19));
        this.add(contMainContractBill, new KDLayout.Constraints(16, 46, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMaterialContractName.setBounds(new Rectangle(368, 73, 270, 19));
        this.add(contMaterialContractName, new KDLayout.Constraints(368, 73, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMaterialConPartB.setBounds(new Rectangle(723, 73, 270, 19));
        this.add(contMaterialConPartB, new KDLayout.Constraints(723, 73, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurrency.setBounds(new Rectangle(368, 100, 270, 19));
        this.add(contCurrency, new KDLayout.Constraints(368, 100, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMainContractPartB.setBounds(new Rectangle(723, 45, 270, 19));
        this.add(contMainContractPartB, new KDLayout.Constraints(723, 45, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contMaterialConAmt.setBounds(new Rectangle(16, 100, 270, 19));
        this.add(contMaterialConAmt, new KDLayout.Constraints(16, 100, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRate.setBounds(new Rectangle(723, 100, 270, 19));
        this.add(contRate, new KDLayout.Constraints(723, 100, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSupplyDate.setBounds(new Rectangle(723, 20, 270, 19));
        this.add(contSupplyDate, new KDLayout.Constraints(723, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(675, 115, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(675, 115, 270, 19, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(txtDesc);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contMainContractName
        contMainContractName.setBoundEditor(txtMainContractName);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contSupplyAmt
        contSupplyAmt.setBoundEditor(txtSupplyAmt);
        //contToDateSupplyAmt
        contToDateSupplyAmt.setBoundEditor(txtToDateSupplyAmt);
        //contConfirmAmt
        contConfirmAmt.setBoundEditor(txtConfirmAmt);
        //contToDateConfirmAmt
        contToDateConfirmAmt.setBoundEditor(txtToDateConfirmAmt);
        //contPaidAmt
        contPaidAmt.setBoundEditor(txtOrigialAmount);
        //contToDatePaidAmt
        contToDatePaidAmt.setBoundEditor(txtToDatePaidAmt);
        //contMaterialContractBill
        contMaterialContractBill.setBoundEditor(prmtMaterialContractBill);
        //contMainContractBill
        contMainContractBill.setBoundEditor(prmtMainContractBill);
        //contMaterialContractName
        contMaterialContractName.setBoundEditor(txtMaterialContractName);
        //contMaterialConPartB
        contMaterialConPartB.setBoundEditor(txtMaterialConPartB);
        //contCurrency
        contCurrency.setBoundEditor(txtCurrency);
        //contMainContractPartB
        contMainContractPartB.setBoundEditor(txtMainContractPartB);
        //contMaterialConAmt
        contMaterialConAmt.setBoundEditor(txtMaterialConAmt);
        //contRate
        contRate.setBoundEditor(txtRate);
        //contSupplyDate
        contSupplyDate.setBoundEditor(pkSupplyDate);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtPaidAmt);

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
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemImportMatEntry);
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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnImportMaterialEntry);
        this.toolBar.add(btnImportMaterialOrder);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("entrys.desc", String.class, this.kdtEntrys, "desc.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.material.MaterialConfirmBillEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.material.number", String.class, this.kdtEntrys, "materialNumber.text");
		dataBinder.registerBinding("entrys.material.model", String.class, this.kdtEntrys, "model.text");
		dataBinder.registerBinding("entrys.material.baseUnit.name", String.class, this.kdtEntrys, "unit.text");
		dataBinder.registerBinding("entrys.quantity", int.class, this.kdtEntrys, "quantity.text");
		dataBinder.registerBinding("entrys.partAMaterialEntry.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "partAMaterialEntryId.text");
		dataBinder.registerBinding("entrys.partAMaterialEntry.originalPrice", java.math.BigDecimal.class, this.kdtEntrys, "originalPrice.text");
		dataBinder.registerBinding("entrys.partAMaterialEntry.price", java.math.BigDecimal.class, this.kdtEntrys, "price.text");
		dataBinder.registerBinding("entrys.originalPrice", java.math.BigDecimal.class, this.kdtEntrys, "confirmOriginalPrice.text");
		dataBinder.registerBinding("entrys.price", java.math.BigDecimal.class, this.kdtEntrys, "confirmPrice.text");
		dataBinder.registerBinding("entrys.amount", java.math.BigDecimal.class, this.kdtEntrys, "confirmAmount.text");
		dataBinder.registerBinding("entrys.material.name", String.class, this.kdtEntrys, "materialName.text");
		dataBinder.registerBinding("entrys.partAMaterialEntry.amount", java.math.BigDecimal.class, this.kdtEntrys, "amount.text");
		dataBinder.registerBinding("entrys.partAMaterialEntry.material.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "materialId.text");
		dataBinder.registerBinding("entrys.originalAmount", java.math.BigDecimal.class, this.kdtEntrys, "confirmOriAmt.text");
		dataBinder.registerBinding("entrys.section", com.kingdee.eas.fdc.invite.SectionEnum.class, this.kdtEntrys, "section.text");
		dataBinder.registerBinding("creator.name", String.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDesc, "text");
		dataBinder.registerBinding("auditor.name", String.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("mainContractBill.name", String.class, this.txtMainContractName, "text");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("supplyAmt", java.math.BigDecimal.class, this.txtSupplyAmt, "value");
		dataBinder.registerBinding("toDateSupplyAmt", java.math.BigDecimal.class, this.txtToDateSupplyAmt, "value");
		dataBinder.registerBinding("confirmAmt", java.math.BigDecimal.class, this.txtConfirmAmt, "value");
		dataBinder.registerBinding("toDateConfirmAmt", java.math.BigDecimal.class, this.txtToDateConfirmAmt, "value");
		dataBinder.registerBinding("originalAmount", java.math.BigDecimal.class, this.txtOrigialAmount, "value");
		dataBinder.registerBinding("toDatePaidAmt", java.math.BigDecimal.class, this.txtToDatePaidAmt, "value");
		dataBinder.registerBinding("materialContractBill", com.kingdee.eas.fdc.contract.ContractBillInfo.class, this.prmtMaterialContractBill, "data");
		dataBinder.registerBinding("mainContractBill", com.kingdee.eas.fdc.contract.ContractBillInfo.class, this.prmtMainContractBill, "data");
		dataBinder.registerBinding("materialContractBill.name", String.class, this.txtMaterialContractName, "text");
		dataBinder.registerBinding("materialContractBill.partB.name", String.class, this.txtMaterialConPartB, "text");
		dataBinder.registerBinding("materialContractBill.currency.name", String.class, this.txtCurrency, "text");
		dataBinder.registerBinding("mainContractBill.partB.name", String.class, this.txtMainContractPartB, "text");
		dataBinder.registerBinding("materialContractBill.amount", java.math.BigDecimal.class, this.txtMaterialConAmt, "value");
		dataBinder.registerBinding("materialContractBill.exRate", java.math.BigDecimal.class, this.txtRate, "value");
		dataBinder.registerBinding("supplyDate", java.util.Date.class, this.pkSupplyDate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.material.app.MaterialConfirmBillEditUIHandler";
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
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.material.MaterialConfirmBillInfo)ov;
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
		getValidateHelper().registerBindProperty("entrys.desc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.material.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.material.model", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.material.baseUnit.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.quantity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.partAMaterialEntry.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.partAMaterialEntry.originalPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.partAMaterialEntry.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.originalPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.material.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.partAMaterialEntry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.partAMaterialEntry.material.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.section", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mainContractBill.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplyAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("toDateSupplyAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("confirmAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("toDateConfirmAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("toDatePaidAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("materialContractBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mainContractBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("materialContractBill.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("materialContractBill.partB.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("materialContractBill.currency.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mainContractBill.partB.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("materialContractBill.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("materialContractBill.exRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplyDate", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntrys_editStopping method
     */
    protected void kdtEntrys_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output prmtMaterialContractBill_dataChanged method
     */
    protected void prmtMaterialContractBill_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("entrys.desc"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
    sic.add(new SelectorItemInfo("entrys.material.number"));
    sic.add(new SelectorItemInfo("entrys.material.model"));
    sic.add(new SelectorItemInfo("entrys.material.baseUnit.name"));
    sic.add(new SelectorItemInfo("entrys.quantity"));
    sic.add(new SelectorItemInfo("entrys.partAMaterialEntry.id"));
    sic.add(new SelectorItemInfo("entrys.partAMaterialEntry.originalPrice"));
    sic.add(new SelectorItemInfo("entrys.partAMaterialEntry.price"));
    sic.add(new SelectorItemInfo("entrys.originalPrice"));
    sic.add(new SelectorItemInfo("entrys.price"));
    sic.add(new SelectorItemInfo("entrys.amount"));
    sic.add(new SelectorItemInfo("entrys.material.name"));
    sic.add(new SelectorItemInfo("entrys.partAMaterialEntry.amount"));
    sic.add(new SelectorItemInfo("entrys.partAMaterialEntry.material.id"));
    sic.add(new SelectorItemInfo("entrys.originalAmount"));
    sic.add(new SelectorItemInfo("entrys.section"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("mainContractBill.name"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("supplyAmt"));
        sic.add(new SelectorItemInfo("toDateSupplyAmt"));
        sic.add(new SelectorItemInfo("confirmAmt"));
        sic.add(new SelectorItemInfo("toDateConfirmAmt"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("toDatePaidAmt"));
        sic.add(new SelectorItemInfo("materialContractBill.*"));
        sic.add(new SelectorItemInfo("mainContractBill.*"));
        sic.add(new SelectorItemInfo("materialContractBill.name"));
        sic.add(new SelectorItemInfo("materialContractBill.partB.name"));
        sic.add(new SelectorItemInfo("materialContractBill.currency.name"));
        sic.add(new SelectorItemInfo("mainContractBill.partB.name"));
        sic.add(new SelectorItemInfo("materialContractBill.amount"));
        sic.add(new SelectorItemInfo("materialContractBill.exRate"));
        sic.add(new SelectorItemInfo("supplyDate"));
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
    	

    /**
     * output actionImportMaterialEntry_actionPerformed method
     */
    public void actionImportMaterialEntry_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportMaterialOrder_actionPerformed method
     */
    public void actionImportMaterialOrder_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionImportMaterialEntry(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportMaterialEntry() {
    	return false;
    }
	public RequestContext prepareActionImportMaterialOrder(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportMaterialOrder() {
    	return false;
    }

    /**
     * output ActionImportMaterialEntry class
     */     
    protected class ActionImportMaterialEntry extends ItemAction {     
    
        public ActionImportMaterialEntry()
        {
            this(null);
        }

        public ActionImportMaterialEntry(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportMaterialEntry.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportMaterialEntry.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportMaterialEntry.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMaterialConfirmBillEditUI.this, "ActionImportMaterialEntry", "actionImportMaterialEntry_actionPerformed", e);
        }
    }

    /**
     * output ActionImportMaterialOrder class
     */     
    protected class ActionImportMaterialOrder extends ItemAction {     
    
        public ActionImportMaterialOrder()
        {
            this(null);
        }

        public ActionImportMaterialOrder(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImportMaterialOrder.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportMaterialOrder.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportMaterialOrder.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMaterialConfirmBillEditUI.this, "ActionImportMaterialOrder", "actionImportMaterialOrder_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.material.client", "MaterialConfirmBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}