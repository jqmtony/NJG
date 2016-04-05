/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa.client;

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
public abstract class AbstractIndoorengEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractIndoorengEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersion;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chklasted;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalesArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalesAreaIndex;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSoft;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHard;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDComboBox State;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProjectName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtVersion;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSalesArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSalesAreaIndex;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSoft;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtHard;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditTime;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.eas.fdc.gcftbiaoa.IndoorengInfo editData = null;
    protected actionAduit ActionAduit = null;
    protected actionUnAduit ActionUnAduit = null;
    /**
     * output class constructor
     */
    public AbstractIndoorengEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractIndoorengEditUI.class.getName());
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
        //ActionAduit
        this.ActionAduit = new actionAduit(this);
        getActionManager().registerAction("ActionAduit", ActionAduit);
         this.ActionAduit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //ActionUnAduit
        this.ActionUnAduit = new actionUnAduit(this);
        getActionManager().registerAction("ActionUnAduit", ActionUnAduit);
         this.ActionUnAduit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVersion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chklasted = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSalesArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSalesAreaIndex = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSoft = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHard = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.State = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtProjectName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtVersion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSalesArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSalesAreaIndex = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSoft = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtHard = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkauditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.kdtEntrys.setName("kdtEntrys");
        this.contState.setName("contState");
        this.contProjectName.setName("contProjectName");
        this.contVersion.setName("contVersion");
        this.chklasted.setName("chklasted");
        this.contRoom.setName("contRoom");
        this.contSalesArea.setName("contSalesArea");
        this.contSalesAreaIndex.setName("contSalesAreaIndex");
        this.contSoft.setName("contSoft");
        this.contHard.setName("contHard");
        this.contauditTime.setName("contauditTime");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.State.setName("State");
        this.prmtProjectName.setName("prmtProjectName");
        this.txtVersion.setName("txtVersion");
        this.prmtRoom.setName("prmtRoom");
        this.txtSalesArea.setName("txtSalesArea");
        this.txtSalesAreaIndex.setName("txtSalesAreaIndex");
        this.txtSoft.setName("txtSoft");
        this.txtHard.setName("txtHard");
        this.pkauditTime.setName("pkauditTime");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        // CoreUI		
        this.btnAddLine.setVisible(false);		
        this.btnCopyLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
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
        this.menuItemCopyLine.setVisible(false);
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
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);		
        this.contLastUpdateUser.setVisible(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);		
        this.contLastUpdateTime.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(false);		
        this.contBizDate.setEnabled(false);
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
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"key\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"title\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"DecorateArea\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"DecorateAreaIndex\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"Sumproportion\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"ComePrice\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"Areaproportion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"danwei\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"other\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell /><t:Cell /><t:Cell>$Resource{key}</t:Cell><t:Cell>$Resource{title}</t:Cell><t:Cell>$Resource{Price}</t:Cell><t:Cell>$Resource{DecorateArea}</t:Cell><t:Cell>$Resource{DecorateAreaIndex}</t:Cell><t:Cell>$Resource{Sumproportion}</t:Cell><t:Cell>$Resource{ComePrice}</t:Cell><t:Cell>$Resource{Areaproportion}</t:Cell></t:Row><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{danwei}</t:Cell><t:Cell /><t:Cell>$Resource{other}</t:Cell><t:Cell>$Resource{id_Row2}</t:Cell><t:Cell>$Resource{key_Row2}</t:Cell><t:Cell>$Resource{title_Row2}</t:Cell><t:Cell>$Resource{Price_Row2}</t:Cell><t:Cell>$Resource{DecorateArea_Row2}</t:Cell><t:Cell>$Resource{DecorateAreaIndex_Row2}</t:Cell><t:Cell>$Resource{Sumproportion_Row2}</t:Cell><t:Cell>$Resource{ComePrice_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"0\" t:right=\"2\" /><t:Block t:top=\"1\" t:left=\"1\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"1\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"1\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"1\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"1\" t:right=\"7\" /><t:Block t:top=\"0\" t:left=\"8\" t:bottom=\"0\" t:right=\"9\" /><t:Block t:top=\"0\" t:left=\"10\" t:bottom=\"1\" t:right=\"10\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","key","title","Price","DecorateArea","DecorateAreaIndex","Sumproportion","ComePrice","Areaproportion","danwei","other"});


        this.kdtEntrys.checkParsed();
        KDTextField kdtEntrys_key_TextField = new KDTextField();
        kdtEntrys_key_TextField.setName("kdtEntrys_key_TextField");
        kdtEntrys_key_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_key_CellEditor = new KDTDefaultCellEditor(kdtEntrys_key_TextField);
        this.kdtEntrys.getColumn("key").setEditor(kdtEntrys_key_CellEditor);
        KDTextField kdtEntrys_title_TextField = new KDTextField();
        kdtEntrys_title_TextField.setName("kdtEntrys_title_TextField");
        kdtEntrys_title_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_title_CellEditor = new KDTDefaultCellEditor(kdtEntrys_title_TextField);
        this.kdtEntrys.getColumn("title").setEditor(kdtEntrys_title_CellEditor);
        KDFormattedTextField kdtEntrys_Price_TextField = new KDFormattedTextField();
        kdtEntrys_Price_TextField.setName("kdtEntrys_Price_TextField");
        kdtEntrys_Price_TextField.setVisible(true);
        kdtEntrys_Price_TextField.setEditable(true);
        kdtEntrys_Price_TextField.setHorizontalAlignment(2);
        kdtEntrys_Price_TextField.setDataType(1);
        	kdtEntrys_Price_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_Price_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_Price_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_Price_CellEditor = new KDTDefaultCellEditor(kdtEntrys_Price_TextField);
        this.kdtEntrys.getColumn("Price").setEditor(kdtEntrys_Price_CellEditor);
        KDFormattedTextField kdtEntrys_DecorateArea_TextField = new KDFormattedTextField();
        kdtEntrys_DecorateArea_TextField.setName("kdtEntrys_DecorateArea_TextField");
        kdtEntrys_DecorateArea_TextField.setVisible(true);
        kdtEntrys_DecorateArea_TextField.setEditable(true);
        kdtEntrys_DecorateArea_TextField.setHorizontalAlignment(2);
        kdtEntrys_DecorateArea_TextField.setDataType(1);
        	kdtEntrys_DecorateArea_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_DecorateArea_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_DecorateArea_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_DecorateArea_CellEditor = new KDTDefaultCellEditor(kdtEntrys_DecorateArea_TextField);
        this.kdtEntrys.getColumn("DecorateArea").setEditor(kdtEntrys_DecorateArea_CellEditor);
        KDFormattedTextField kdtEntrys_DecorateAreaIndex_TextField = new KDFormattedTextField();
        kdtEntrys_DecorateAreaIndex_TextField.setName("kdtEntrys_DecorateAreaIndex_TextField");
        kdtEntrys_DecorateAreaIndex_TextField.setVisible(true);
        kdtEntrys_DecorateAreaIndex_TextField.setEditable(true);
        kdtEntrys_DecorateAreaIndex_TextField.setHorizontalAlignment(2);
        kdtEntrys_DecorateAreaIndex_TextField.setDataType(1);
        	kdtEntrys_DecorateAreaIndex_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_DecorateAreaIndex_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_DecorateAreaIndex_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_DecorateAreaIndex_CellEditor = new KDTDefaultCellEditor(kdtEntrys_DecorateAreaIndex_TextField);
        this.kdtEntrys.getColumn("DecorateAreaIndex").setEditor(kdtEntrys_DecorateAreaIndex_CellEditor);
        KDFormattedTextField kdtEntrys_Sumproportion_TextField = new KDFormattedTextField();
        kdtEntrys_Sumproportion_TextField.setName("kdtEntrys_Sumproportion_TextField");
        kdtEntrys_Sumproportion_TextField.setVisible(true);
        kdtEntrys_Sumproportion_TextField.setEditable(true);
        kdtEntrys_Sumproportion_TextField.setHorizontalAlignment(2);
        kdtEntrys_Sumproportion_TextField.setDataType(1);
        	kdtEntrys_Sumproportion_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_Sumproportion_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_Sumproportion_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_Sumproportion_CellEditor = new KDTDefaultCellEditor(kdtEntrys_Sumproportion_TextField);
        this.kdtEntrys.getColumn("Sumproportion").setEditor(kdtEntrys_Sumproportion_CellEditor);
        KDFormattedTextField kdtEntrys_ComePrice_TextField = new KDFormattedTextField();
        kdtEntrys_ComePrice_TextField.setName("kdtEntrys_ComePrice_TextField");
        kdtEntrys_ComePrice_TextField.setVisible(true);
        kdtEntrys_ComePrice_TextField.setEditable(true);
        kdtEntrys_ComePrice_TextField.setHorizontalAlignment(2);
        kdtEntrys_ComePrice_TextField.setDataType(1);
        	kdtEntrys_ComePrice_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_ComePrice_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_ComePrice_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_ComePrice_CellEditor = new KDTDefaultCellEditor(kdtEntrys_ComePrice_TextField);
        this.kdtEntrys.getColumn("ComePrice").setEditor(kdtEntrys_ComePrice_CellEditor);
        KDFormattedTextField kdtEntrys_Areaproportion_TextField = new KDFormattedTextField();
        kdtEntrys_Areaproportion_TextField.setName("kdtEntrys_Areaproportion_TextField");
        kdtEntrys_Areaproportion_TextField.setVisible(true);
        kdtEntrys_Areaproportion_TextField.setEditable(true);
        kdtEntrys_Areaproportion_TextField.setHorizontalAlignment(2);
        kdtEntrys_Areaproportion_TextField.setDataType(1);
        	kdtEntrys_Areaproportion_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E24"));
        	kdtEntrys_Areaproportion_TextField.setMaximumValue(new java.math.BigDecimal("1.0E24"));
        kdtEntrys_Areaproportion_TextField.setPrecision(4);
        KDTDefaultCellEditor kdtEntrys_Areaproportion_CellEditor = new KDTDefaultCellEditor(kdtEntrys_Areaproportion_TextField);
        this.kdtEntrys.getColumn("Areaproportion").setEditor(kdtEntrys_Areaproportion_CellEditor);
        KDTextField kdtEntrys_danwei_TextField = new KDTextField();
        kdtEntrys_danwei_TextField.setName("kdtEntrys_danwei_TextField");
        kdtEntrys_danwei_TextField.setMaxLength(20);
        KDTDefaultCellEditor kdtEntrys_danwei_CellEditor = new KDTDefaultCellEditor(kdtEntrys_danwei_TextField);
        this.kdtEntrys.getColumn("danwei").setEditor(kdtEntrys_danwei_CellEditor);
        KDTextField kdtEntrys_other_TextField = new KDTextField();
        kdtEntrys_other_TextField.setName("kdtEntrys_other_TextField");
        kdtEntrys_other_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_other_CellEditor = new KDTDefaultCellEditor(kdtEntrys_other_TextField);
        this.kdtEntrys.getColumn("other").setEditor(kdtEntrys_other_CellEditor);
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);		
        this.contState.setVisible(true);		
        this.contState.setEnabled(false);
        // contProjectName		
        this.contProjectName.setBoundLabelText(resHelper.getString("contProjectName.boundLabelText"));		
        this.contProjectName.setBoundLabelLength(100);		
        this.contProjectName.setBoundLabelUnderline(true);		
        this.contProjectName.setVisible(true);		
        this.contProjectName.setEnabled(false);
        // contVersion		
        this.contVersion.setBoundLabelText(resHelper.getString("contVersion.boundLabelText"));		
        this.contVersion.setBoundLabelLength(100);		
        this.contVersion.setBoundLabelUnderline(true);		
        this.contVersion.setVisible(true);		
        this.contVersion.setEnabled(false);
        // chklasted		
        this.chklasted.setText(resHelper.getString("chklasted.text"));		
        this.chklasted.setVisible(false);		
        this.chklasted.setHorizontalAlignment(2);		
        this.chklasted.setEnabled(false);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);		
        this.contRoom.setVisible(true);
        // contSalesArea		
        this.contSalesArea.setBoundLabelText(resHelper.getString("contSalesArea.boundLabelText"));		
        this.contSalesArea.setBoundLabelLength(100);		
        this.contSalesArea.setBoundLabelUnderline(true);		
        this.contSalesArea.setVisible(true);
        // contSalesAreaIndex		
        this.contSalesAreaIndex.setBoundLabelText(resHelper.getString("contSalesAreaIndex.boundLabelText"));		
        this.contSalesAreaIndex.setBoundLabelLength(140);		
        this.contSalesAreaIndex.setBoundLabelUnderline(true);		
        this.contSalesAreaIndex.setVisible(true);		
        this.contSalesAreaIndex.setEnabled(false);
        // contSoft		
        this.contSoft.setBoundLabelText(resHelper.getString("contSoft.boundLabelText"));		
        this.contSoft.setBoundLabelLength(100);		
        this.contSoft.setBoundLabelUnderline(true);		
        this.contSoft.setVisible(true);		
        this.contSoft.setEnabled(false);
        // contHard		
        this.contHard.setBoundLabelText(resHelper.getString("contHard.boundLabelText"));		
        this.contHard.setBoundLabelLength(100);		
        this.contHard.setBoundLabelUnderline(true);		
        this.contHard.setVisible(true);		
        this.contHard.setEnabled(false);
        // contauditTime		
        this.contauditTime.setBoundLabelText(resHelper.getString("contauditTime.boundLabelText"));		
        this.contauditTime.setBoundLabelLength(100);		
        this.contauditTime.setBoundLabelUnderline(true);		
        this.contauditTime.setVisible(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setTimeEnabled(true);		
        this.kDDateLastUpdateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // pkBizDate		
        this.pkBizDate.setVisible(false);		
        this.pkBizDate.setEnabled(false);
        // txtDescription		
        this.txtDescription.setMaxLength(80);		
        this.txtDescription.setVisible(false);		
        this.txtDescription.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // State		
        this.State.setVisible(true);		
        this.State.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.State.setRequired(false);
        // prmtProjectName		
        this.prmtProjectName.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");		
        this.prmtProjectName.setVisible(true);		
        this.prmtProjectName.setEditable(true);		
        this.prmtProjectName.setDisplayFormat("$name$");		
        this.prmtProjectName.setEditFormat("$number$");		
        this.prmtProjectName.setCommitFormat("$number$");		
        this.prmtProjectName.setRequired(false);		
        this.prmtProjectName.setEnabled(false);
        // txtVersion		
        this.txtVersion.setVisible(true);		
        this.txtVersion.setHorizontalAlignment(2);		
        this.txtVersion.setDataType(0);		
        this.txtVersion.setSupportedEmpty(true);		
        this.txtVersion.setRequired(false);		
        this.txtVersion.setEnabled(false);
        // prmtRoom		
        this.prmtRoom.setQueryInfo("com.kingdee.eas.fdc.gcftbiaoa.app.HousingQuery");		
        this.prmtRoom.setVisible(true);		
        this.prmtRoom.setEditable(true);		
        this.prmtRoom.setDisplayFormat("$name$");		
        this.prmtRoom.setEditFormat("$number$");		
        this.prmtRoom.setCommitFormat("$number$");		
        this.prmtRoom.setRequired(false);
        this.prmtRoom.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRoom_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtSalesArea		
        this.txtSalesArea.setVisible(true);		
        this.txtSalesArea.setHorizontalAlignment(2);		
        this.txtSalesArea.setDataType(1);		
        this.txtSalesArea.setSupportedEmpty(true);		
        this.txtSalesArea.setMinimumValue( new java.math.BigDecimal("-1.0E24"));		
        this.txtSalesArea.setMaximumValue( new java.math.BigDecimal("1.0E24"));		
        this.txtSalesArea.setPrecision(4);		
        this.txtSalesArea.setRequired(false);
        // txtSalesAreaIndex		
        this.txtSalesAreaIndex.setVisible(true);		
        this.txtSalesAreaIndex.setHorizontalAlignment(2);		
        this.txtSalesAreaIndex.setDataType(1);		
        this.txtSalesAreaIndex.setSupportedEmpty(true);		
        this.txtSalesAreaIndex.setMinimumValue( new java.math.BigDecimal("-1.0E24"));		
        this.txtSalesAreaIndex.setMaximumValue( new java.math.BigDecimal("1.0E24"));		
        this.txtSalesAreaIndex.setPrecision(4);		
        this.txtSalesAreaIndex.setRequired(false);		
        this.txtSalesAreaIndex.setEnabled(false);
        // txtSoft		
        this.txtSoft.setVisible(true);		
        this.txtSoft.setHorizontalAlignment(2);		
        this.txtSoft.setDataType(1);		
        this.txtSoft.setSupportedEmpty(true);		
        this.txtSoft.setMinimumValue( new java.math.BigDecimal("-1.0E24"));		
        this.txtSoft.setMaximumValue( new java.math.BigDecimal("1.0E24"));		
        this.txtSoft.setPrecision(4);		
        this.txtSoft.setRequired(false);		
        this.txtSoft.setEnabled(false);
        // txtHard		
        this.txtHard.setVisible(true);		
        this.txtHard.setHorizontalAlignment(2);		
        this.txtHard.setDataType(1);		
        this.txtHard.setSupportedEmpty(true);		
        this.txtHard.setMinimumValue( new java.math.BigDecimal("-1.0E24"));		
        this.txtHard.setMaximumValue( new java.math.BigDecimal("1.0E24"));		
        this.txtHard.setPrecision(4);		
        this.txtHard.setRequired(false);		
        this.txtHard.setEnabled(false);
        // pkauditTime		
        this.pkauditTime.setVisible(true);		
        this.pkauditTime.setRequired(false);
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(ActionAduit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(ActionUnAduit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {State,prmtProjectName,txtVersion,prmtRoom,txtSalesArea,txtSoft,txtHard,txtSalesAreaIndex,chklasted,kDDateLastUpdateTime,prmtLastUpdateUser,kDDateCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,kdtEntrys,pkauditTime}));
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
        this.setBounds(new Rectangle(0, 0, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 629));
        contCreator.setBounds(new Rectangle(735, 528, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(735, 528, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(735, 553, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(735, 553, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateUser.setBounds(new Rectangle(383, 528, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(383, 528, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(383, 553, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(383, 553, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(20, 4, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(20, 4, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(642, 40, 54, 19));
        this.add(contBizDate, new KDLayout.Constraints(642, 40, 54, 19, 0));
        contDescription.setBounds(new Rectangle(627, 5, 42, 19));
        this.add(contDescription, new KDLayout.Constraints(627, 5, 42, 19, 0));
        contAuditor.setBounds(new Rectangle(15, 528, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(15, 528, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtEntrys.setBounds(new Rectangle(12, 99, 991, 413));
        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.fdc.gcftbiaoa.IndoorengEntryInfo(),null,false);
        this.add(kdtEntrys_detailPanel, new KDLayout.Constraints(12, 99, 991, 413, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contState.setBounds(new Rectangle(359, 4, 270, 19));
        this.add(contState, new KDLayout.Constraints(359, 4, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProjectName.setBounds(new Rectangle(720, 4, 270, 19));
        this.add(contProjectName, new KDLayout.Constraints(720, 4, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contVersion.setBounds(new Rectangle(720, 38, 270, 19));
        this.add(contVersion, new KDLayout.Constraints(720, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chklasted.setBounds(new Rectangle(621, 28, 77, 19));
        this.add(chklasted, new KDLayout.Constraints(621, 28, 77, 19, 0));
        contRoom.setBounds(new Rectangle(360, 38, 270, 19));
        this.add(contRoom, new KDLayout.Constraints(360, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSalesArea.setBounds(new Rectangle(21, 38, 270, 19));
        this.add(contSalesArea, new KDLayout.Constraints(21, 38, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSalesAreaIndex.setBounds(new Rectangle(19, 75, 270, 19));
        this.add(contSalesAreaIndex, new KDLayout.Constraints(19, 75, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSoft.setBounds(new Rectangle(724, 75, 270, 19));
        this.add(contSoft, new KDLayout.Constraints(724, 75, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contHard.setBounds(new Rectangle(359, 75, 270, 19));
        this.add(contHard, new KDLayout.Constraints(359, 75, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contauditTime.setBounds(new Rectangle(15, 553, 270, 19));
        this.add(contauditTime, new KDLayout.Constraints(15, 553, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDateLastUpdateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contState
        contState.setBoundEditor(State);
        //contProjectName
        contProjectName.setBoundEditor(prmtProjectName);
        //contVersion
        contVersion.setBoundEditor(txtVersion);
        //contRoom
        contRoom.setBoundEditor(prmtRoom);
        //contSalesArea
        contSalesArea.setBoundEditor(txtSalesArea);
        //contSalesAreaIndex
        contSalesAreaIndex.setBoundEditor(txtSalesAreaIndex);
        //contSoft
        contSoft.setBoundEditor(txtSoft);
        //contHard
        contHard.setBoundEditor(txtHard);
        //contauditTime
        contauditTime.setBoundEditor(pkauditTime);

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
        this.toolBar.add(btnNumberSign);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.gcftbiaoa.IndoorengEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.key", String.class, this.kdtEntrys, "key.text");
		dataBinder.registerBinding("entrys.title", String.class, this.kdtEntrys, "title.text");
		dataBinder.registerBinding("entrys.DecorateArea", java.math.BigDecimal.class, this.kdtEntrys, "DecorateArea.text");
		dataBinder.registerBinding("entrys.DecorateAreaIndex", java.math.BigDecimal.class, this.kdtEntrys, "DecorateAreaIndex.text");
		dataBinder.registerBinding("entrys.Price", java.math.BigDecimal.class, this.kdtEntrys, "Price.text");
		dataBinder.registerBinding("entrys.Sumproportion", java.math.BigDecimal.class, this.kdtEntrys, "Sumproportion.text");
		dataBinder.registerBinding("entrys.ComePrice", java.math.BigDecimal.class, this.kdtEntrys, "ComePrice.text");
		dataBinder.registerBinding("entrys.Areaproportion", java.math.BigDecimal.class, this.kdtEntrys, "Areaproportion.text");
		dataBinder.registerBinding("entrys.danwei", String.class, this.kdtEntrys, "danwei.text");
		dataBinder.registerBinding("entrys.other", String.class, this.kdtEntrys, "other.text");
		dataBinder.registerBinding("lasted", boolean.class, this.chklasted, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("State", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.State, "selectedItem");
		dataBinder.registerBinding("ProjectName", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtProjectName, "data");
		dataBinder.registerBinding("Version", int.class, this.txtVersion, "value");
		dataBinder.registerBinding("Room", com.kingdee.eas.fdc.gcftbiaoa.HousingInfo.class, this.prmtRoom, "data");
		dataBinder.registerBinding("SalesArea", java.math.BigDecimal.class, this.txtSalesArea, "value");
		dataBinder.registerBinding("SalesAreaIndex", java.math.BigDecimal.class, this.txtSalesAreaIndex, "value");
		dataBinder.registerBinding("Soft", java.math.BigDecimal.class, this.txtSoft, "value");
		dataBinder.registerBinding("Hard", java.math.BigDecimal.class, this.txtHard, "value");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkauditTime, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.gcftbiaoa.app.IndoorengEditUIHandler";
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
        this.State.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.gcftbiaoa.IndoorengInfo)ov;
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
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.key", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.title", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.DecorateArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.DecorateAreaIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Sumproportion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.ComePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Areaproportion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.danwei", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.other", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lasted", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("State", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ProjectName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Version", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SalesArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SalesAreaIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Soft", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Hard", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    		
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
     * output prmtRoom_dataChanged method
     */
    protected void prmtRoom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entrys.key"));
    	sic.add(new SelectorItemInfo("entrys.title"));
    	sic.add(new SelectorItemInfo("entrys.DecorateArea"));
    	sic.add(new SelectorItemInfo("entrys.DecorateAreaIndex"));
    	sic.add(new SelectorItemInfo("entrys.Price"));
    	sic.add(new SelectorItemInfo("entrys.Sumproportion"));
    	sic.add(new SelectorItemInfo("entrys.ComePrice"));
    	sic.add(new SelectorItemInfo("entrys.Areaproportion"));
    	sic.add(new SelectorItemInfo("entrys.danwei"));
    	sic.add(new SelectorItemInfo("entrys.other"));
        sic.add(new SelectorItemInfo("lasted"));
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
        sic.add(new SelectorItemInfo("State"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("ProjectName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("ProjectName.id"));
        	sic.add(new SelectorItemInfo("ProjectName.number"));
        	sic.add(new SelectorItemInfo("ProjectName.name"));
		}
        sic.add(new SelectorItemInfo("Version"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Room.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("Room.id"));
        	sic.add(new SelectorItemInfo("Room.number"));
        	sic.add(new SelectorItemInfo("Room.name"));
		}
        sic.add(new SelectorItemInfo("SalesArea"));
        sic.add(new SelectorItemInfo("SalesAreaIndex"));
        sic.add(new SelectorItemInfo("Soft"));
        sic.add(new SelectorItemInfo("Hard"));
        sic.add(new SelectorItemInfo("auditTime"));
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
     * output actionAduit_actionPerformed method
     */
    public void actionAduit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAduit_actionPerformed method
     */
    public void actionUnAduit_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareactionAduit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAduit() {
    	return false;
    }
	public RequestContext prepareactionUnAduit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionUnAduit() {
    	return false;
    }

    /**
     * output actionAduit class
     */     
    protected class actionAduit extends ItemAction {     
    
        public actionAduit()
        {
            this(null);
        }

        public actionAduit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionAduit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAduit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAduit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIndoorengEditUI.this, "actionAduit", "actionAduit_actionPerformed", e);
        }
    }

    /**
     * output actionUnAduit class
     */     
    protected class actionUnAduit extends ItemAction {     
    
        public actionUnAduit()
        {
            this(null);
        }

        public actionUnAduit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionUnAduit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionUnAduit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionUnAduit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIndoorengEditUI.this, "actionUnAduit", "actionUnAduit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.gcftbiaoa.client", "IndoorengEditUI");
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
        return com.kingdee.eas.fdc.gcftbiaoa.client.IndoorengEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.gcftbiaoa.IndoorengFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.gcftbiaoa.IndoorengInfo objectValue = new com.kingdee.eas.fdc.gcftbiaoa.IndoorengInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/gcftbiaoa/Indooreng";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.gcftbiaoa.app.IndoorengQuery");
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
		vo.put("State","1SAVED");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}