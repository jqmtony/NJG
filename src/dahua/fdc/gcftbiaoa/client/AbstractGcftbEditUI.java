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
public abstract class AbstractGcftbEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractGcftbEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contgsmc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbbh;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstatus;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisLast;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contengineeringProject;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtgsmc;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbbh;
    protected com.kingdee.bos.ctrl.swing.KDComboBox status;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtengineeringProject;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtDetail;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtDetail_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnModify;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton aduitAction;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton unAduit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewHistoryList;
    protected com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnaudit actionUnaudit = null;
    protected actionHistoryVersion ActionHistoryVersion = null;
    protected actionModify ActionModify = null;
    /**
     * output class constructor
     */
    public AbstractGcftbEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractGcftbEditUI.class.getName());
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
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
        this.actionAudit.setExtendProperty("canForewarn", "true");
        this.actionAudit.setExtendProperty("userDefined", "true");
        this.actionAudit.setExtendProperty("isObjectUpdateLock", "false");
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionUnaudit
        this.actionUnaudit = new ActionUnaudit(this);
        getActionManager().registerAction("actionUnaudit", actionUnaudit);
        this.actionUnaudit.setExtendProperty("canForewarn", "true");
        this.actionUnaudit.setExtendProperty("userDefined", "true");
        this.actionUnaudit.setExtendProperty("isObjectUpdateLock", "false");
         this.actionUnaudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionUnaudit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //ActionHistoryVersion
        this.ActionHistoryVersion = new actionHistoryVersion(this);
        getActionManager().registerAction("ActionHistoryVersion", ActionHistoryVersion);
         this.ActionHistoryVersion.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //ActionModify
        this.ActionModify = new actionModify(this);
        getActionManager().registerAction("ActionModify", ActionModify);
         this.ActionModify.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contgsmc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbbh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contstatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisLast = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contengineeringProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtgsmc = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbbh = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.status = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtengineeringProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtDetail = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnModify = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.aduitAction = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.unAduit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewHistoryList = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contgsmc.setName("contgsmc");
        this.contbbh.setName("contbbh");
        this.contstatus.setName("contstatus");
        this.chkisLast.setName("chkisLast");
        this.contengineeringProject.setName("contengineeringProject");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtgsmc.setName("txtgsmc");
        this.txtbbh.setName("txtbbh");
        this.status.setName("status");
        this.prmtengineeringProject.setName("prmtengineeringProject");
        this.kdtEntrys.setName("kdtEntrys");
        this.kdtDetail.setName("kdtDetail");
        this.btnModify.setName("btnModify");
        this.aduitAction.setName("aduitAction");
        this.unAduit.setName("unAduit");
        this.btnViewHistoryList.setName("btnViewHistoryList");
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
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);
        // contgsmc		
        this.contgsmc.setBoundLabelText(resHelper.getString("contgsmc.boundLabelText"));		
        this.contgsmc.setBoundLabelLength(100);		
        this.contgsmc.setBoundLabelUnderline(true);		
        this.contgsmc.setVisible(true);
        // contbbh		
        this.contbbh.setBoundLabelText(resHelper.getString("contbbh.boundLabelText"));		
        this.contbbh.setBoundLabelLength(100);		
        this.contbbh.setBoundLabelUnderline(true);		
        this.contbbh.setVisible(true);
        // contstatus		
        this.contstatus.setBoundLabelText(resHelper.getString("contstatus.boundLabelText"));		
        this.contstatus.setBoundLabelLength(100);		
        this.contstatus.setBoundLabelUnderline(true);		
        this.contstatus.setVisible(true);
        // chkisLast		
        this.chkisLast.setText(resHelper.getString("chkisLast.text"));		
        this.chkisLast.setHorizontalAlignment(2);		
        this.chkisLast.setVisible(false);
        // contengineeringProject		
        this.contengineeringProject.setBoundLabelText(resHelper.getString("contengineeringProject.boundLabelText"));		
        this.contengineeringProject.setBoundLabelLength(100);		
        this.contengineeringProject.setBoundLabelUnderline(true);		
        this.contengineeringProject.setVisible(true);
        // kDContainer1		
        this.kDContainer1.setEnableActive(false);		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDContainer2		
        this.kDContainer2.setEnableActive(false);		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // pkBizDate		
        this.pkBizDate.setEnabled(true);		
        this.pkBizDate.setRequired(true);
        // txtgsmc		
        this.txtgsmc.setHorizontalAlignment(2);		
        this.txtgsmc.setMaxLength(100);		
        this.txtgsmc.setRequired(true);
        // txtbbh		
        this.txtbbh.setHorizontalAlignment(2);		
        this.txtbbh.setMaxLength(100);		
        this.txtbbh.setRequired(true);
        // status		
        this.status.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.status.setRequired(false);
        // prmtengineeringProject		
        this.prmtengineeringProject.setQueryInfo("com.kingdee.eas.fdc.gcftbiaoa.app.TreeNodeQuery");		
        this.prmtengineeringProject.setEditable(true);		
        this.prmtengineeringProject.setDisplayFormat("$name$");		
        this.prmtengineeringProject.setEditFormat("$number$");		
        this.prmtengineeringProject.setCommitFormat("$number$");		
        this.prmtengineeringProject.setRequired(false);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"engineeringProject\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"allshare\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"facilityName\" t:width=\"130\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"proptreyRight\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"constructionArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"startTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"actualStartTine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"completionTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"actualCompeltionTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"totalCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"costHasOccurred\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"allocationIndex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"totalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"share\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"sharePrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{engineeringProject}</t:Cell><t:Cell>$Resource{allshare}</t:Cell><t:Cell>$Resource{facilityName}</t:Cell><t:Cell>$Resource{proptreyRight}</t:Cell><t:Cell>$Resource{constructionArea}</t:Cell><t:Cell>$Resource{startTime}</t:Cell><t:Cell>$Resource{actualStartTine}</t:Cell><t:Cell>$Resource{completionTime}</t:Cell><t:Cell>$Resource{actualCompeltionTime}</t:Cell><t:Cell>$Resource{totalCost}</t:Cell><t:Cell>$Resource{costHasOccurred}</t:Cell><t:Cell>$Resource{allocationIndex}</t:Cell><t:Cell>$Resource{totalAmount}</t:Cell><t:Cell>$Resource{share}</t:Cell><t:Cell>$Resource{sharePrice}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

                this.kdtEntrys.putBindContents("editData",new String[] {"id","engineeringProject","allshare","facilityName","proptreyRight","constructionArea","startTime","actualStartTine","completionTime","actualCompeltionTime","totalCost","costHasOccurred","allocationIndex","totalAmount","share","sharePrice"});


        this.kdtEntrys.checkParsed();
        final KDBizPromptBox kdtEntrys_engineeringProject_PromptBox = new KDBizPromptBox();
        kdtEntrys_engineeringProject_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectForAssActQuery");
        kdtEntrys_engineeringProject_PromptBox.setVisible(true);
        kdtEntrys_engineeringProject_PromptBox.setEditable(true);
        kdtEntrys_engineeringProject_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_engineeringProject_PromptBox.setEditFormat("$number$");
        kdtEntrys_engineeringProject_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_engineeringProject_CellEditor = new KDTDefaultCellEditor(kdtEntrys_engineeringProject_PromptBox);
        this.kdtEntrys.getColumn("engineeringProject").setEditor(kdtEntrys_engineeringProject_CellEditor);
        ObjectValueRender kdtEntrys_engineeringProject_OVR = new ObjectValueRender();
        kdtEntrys_engineeringProject_OVR.setFormat(new BizDataFormat("$isEnabled$"));
        this.kdtEntrys.getColumn("engineeringProject").setRenderer(kdtEntrys_engineeringProject_OVR);
        KDCheckBox kdtEntrys_allshare_CheckBox = new KDCheckBox();
        kdtEntrys_allshare_CheckBox.setName("kdtEntrys_allshare_CheckBox");
        KDTDefaultCellEditor kdtEntrys_allshare_CellEditor = new KDTDefaultCellEditor(kdtEntrys_allshare_CheckBox);
        this.kdtEntrys.getColumn("allshare").setEditor(kdtEntrys_allshare_CellEditor);
        final KDBizPromptBox kdtEntrys_facilityName_PromptBox = new KDBizPromptBox();
        kdtEntrys_facilityName_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
        kdtEntrys_facilityName_PromptBox.setVisible(true);
        kdtEntrys_facilityName_PromptBox.setEditable(true);
        kdtEntrys_facilityName_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_facilityName_PromptBox.setEditFormat("$number$");
        kdtEntrys_facilityName_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_facilityName_CellEditor = new KDTDefaultCellEditor(kdtEntrys_facilityName_PromptBox);
        this.kdtEntrys.getColumn("facilityName").setEditor(kdtEntrys_facilityName_CellEditor);
        ObjectValueRender kdtEntrys_facilityName_OVR = new ObjectValueRender();
        kdtEntrys_facilityName_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("facilityName").setRenderer(kdtEntrys_facilityName_OVR);
        final KDBizPromptBox kdtEntrys_proptreyRight_PromptBox = new KDBizPromptBox();
        kdtEntrys_proptreyRight_PromptBox.setQueryInfo("com.kingdee.eas.fdc.aimcost.costkf.app.CqgsBaseQuery");
        kdtEntrys_proptreyRight_PromptBox.setVisible(true);
        kdtEntrys_proptreyRight_PromptBox.setEditable(true);
        kdtEntrys_proptreyRight_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_proptreyRight_PromptBox.setEditFormat("$number$");
        kdtEntrys_proptreyRight_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_proptreyRight_CellEditor = new KDTDefaultCellEditor(kdtEntrys_proptreyRight_PromptBox);
        this.kdtEntrys.getColumn("proptreyRight").setEditor(kdtEntrys_proptreyRight_CellEditor);
        ObjectValueRender kdtEntrys_proptreyRight_OVR = new ObjectValueRender();
        kdtEntrys_proptreyRight_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("proptreyRight").setRenderer(kdtEntrys_proptreyRight_OVR);
        KDFormattedTextField kdtEntrys_constructionArea_TextField = new KDFormattedTextField();
        kdtEntrys_constructionArea_TextField.setName("kdtEntrys_constructionArea_TextField");
        kdtEntrys_constructionArea_TextField.setVisible(true);
        kdtEntrys_constructionArea_TextField.setEditable(true);
        kdtEntrys_constructionArea_TextField.setHorizontalAlignment(2);
        kdtEntrys_constructionArea_TextField.setDataType(1);
        	kdtEntrys_constructionArea_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_constructionArea_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_constructionArea_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_constructionArea_CellEditor = new KDTDefaultCellEditor(kdtEntrys_constructionArea_TextField);
        this.kdtEntrys.getColumn("constructionArea").setEditor(kdtEntrys_constructionArea_CellEditor);
        KDDatePicker kdtEntrys_startTime_DatePicker = new KDDatePicker();
        kdtEntrys_startTime_DatePicker.setName("kdtEntrys_startTime_DatePicker");
        kdtEntrys_startTime_DatePicker.setVisible(true);
        kdtEntrys_startTime_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_startTime_CellEditor = new KDTDefaultCellEditor(kdtEntrys_startTime_DatePicker);
        this.kdtEntrys.getColumn("startTime").setEditor(kdtEntrys_startTime_CellEditor);
        KDDatePicker kdtEntrys_actualStartTine_DatePicker = new KDDatePicker();
        kdtEntrys_actualStartTine_DatePicker.setName("kdtEntrys_actualStartTine_DatePicker");
        kdtEntrys_actualStartTine_DatePicker.setVisible(true);
        kdtEntrys_actualStartTine_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_actualStartTine_CellEditor = new KDTDefaultCellEditor(kdtEntrys_actualStartTine_DatePicker);
        this.kdtEntrys.getColumn("actualStartTine").setEditor(kdtEntrys_actualStartTine_CellEditor);
        KDDatePicker kdtEntrys_completionTime_DatePicker = new KDDatePicker();
        kdtEntrys_completionTime_DatePicker.setName("kdtEntrys_completionTime_DatePicker");
        kdtEntrys_completionTime_DatePicker.setVisible(true);
        kdtEntrys_completionTime_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_completionTime_CellEditor = new KDTDefaultCellEditor(kdtEntrys_completionTime_DatePicker);
        this.kdtEntrys.getColumn("completionTime").setEditor(kdtEntrys_completionTime_CellEditor);
        KDDatePicker kdtEntrys_actualCompeltionTime_DatePicker = new KDDatePicker();
        kdtEntrys_actualCompeltionTime_DatePicker.setName("kdtEntrys_actualCompeltionTime_DatePicker");
        kdtEntrys_actualCompeltionTime_DatePicker.setVisible(true);
        kdtEntrys_actualCompeltionTime_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_actualCompeltionTime_CellEditor = new KDTDefaultCellEditor(kdtEntrys_actualCompeltionTime_DatePicker);
        this.kdtEntrys.getColumn("actualCompeltionTime").setEditor(kdtEntrys_actualCompeltionTime_CellEditor);
        KDFormattedTextField kdtEntrys_totalCost_TextField = new KDFormattedTextField();
        kdtEntrys_totalCost_TextField.setName("kdtEntrys_totalCost_TextField");
        kdtEntrys_totalCost_TextField.setVisible(true);
        kdtEntrys_totalCost_TextField.setEditable(true);
        kdtEntrys_totalCost_TextField.setHorizontalAlignment(2);
        kdtEntrys_totalCost_TextField.setDataType(1);
        	kdtEntrys_totalCost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_totalCost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_totalCost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_totalCost_CellEditor = new KDTDefaultCellEditor(kdtEntrys_totalCost_TextField);
        this.kdtEntrys.getColumn("totalCost").setEditor(kdtEntrys_totalCost_CellEditor);
        KDFormattedTextField kdtEntrys_costHasOccurred_TextField = new KDFormattedTextField();
        kdtEntrys_costHasOccurred_TextField.setName("kdtEntrys_costHasOccurred_TextField");
        kdtEntrys_costHasOccurred_TextField.setVisible(true);
        kdtEntrys_costHasOccurred_TextField.setEditable(true);
        kdtEntrys_costHasOccurred_TextField.setHorizontalAlignment(2);
        kdtEntrys_costHasOccurred_TextField.setDataType(1);
        	kdtEntrys_costHasOccurred_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_costHasOccurred_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_costHasOccurred_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_costHasOccurred_CellEditor = new KDTDefaultCellEditor(kdtEntrys_costHasOccurred_TextField);
        this.kdtEntrys.getColumn("costHasOccurred").setEditor(kdtEntrys_costHasOccurred_CellEditor);
        KDComboBox kdtEntrys_allocationIndex_ComboBox = new KDComboBox();
        kdtEntrys_allocationIndex_ComboBox.setName("kdtEntrys_allocationIndex_ComboBox");
        kdtEntrys_allocationIndex_ComboBox.setVisible(true);
        kdtEntrys_allocationIndex_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.gcftbiaoa.AllocationIndex").toArray());
        KDTDefaultCellEditor kdtEntrys_allocationIndex_CellEditor = new KDTDefaultCellEditor(kdtEntrys_allocationIndex_ComboBox);
        this.kdtEntrys.getColumn("allocationIndex").setEditor(kdtEntrys_allocationIndex_CellEditor);
        KDFormattedTextField kdtEntrys_totalAmount_TextField = new KDFormattedTextField();
        kdtEntrys_totalAmount_TextField.setName("kdtEntrys_totalAmount_TextField");
        kdtEntrys_totalAmount_TextField.setVisible(true);
        kdtEntrys_totalAmount_TextField.setEditable(true);
        kdtEntrys_totalAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_totalAmount_TextField.setDataType(1);
        	kdtEntrys_totalAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_totalAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_totalAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_totalAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_totalAmount_TextField);
        this.kdtEntrys.getColumn("totalAmount").setEditor(kdtEntrys_totalAmount_CellEditor);
        KDFormattedTextField kdtEntrys_share_TextField = new KDFormattedTextField();
        kdtEntrys_share_TextField.setName("kdtEntrys_share_TextField");
        kdtEntrys_share_TextField.setVisible(true);
        kdtEntrys_share_TextField.setEditable(true);
        kdtEntrys_share_TextField.setHorizontalAlignment(2);
        kdtEntrys_share_TextField.setDataType(1);
        	kdtEntrys_share_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_share_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_share_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_share_CellEditor = new KDTDefaultCellEditor(kdtEntrys_share_TextField);
        this.kdtEntrys.getColumn("share").setEditor(kdtEntrys_share_CellEditor);
        KDFormattedTextField kdtEntrys_sharePrice_TextField = new KDFormattedTextField();
        kdtEntrys_sharePrice_TextField.setName("kdtEntrys_sharePrice_TextField");
        kdtEntrys_sharePrice_TextField.setVisible(true);
        kdtEntrys_sharePrice_TextField.setEditable(true);
        kdtEntrys_sharePrice_TextField.setHorizontalAlignment(2);
        kdtEntrys_sharePrice_TextField.setDataType(1);
        	kdtEntrys_sharePrice_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_sharePrice_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_sharePrice_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_sharePrice_CellEditor = new KDTDefaultCellEditor(kdtEntrys_sharePrice_TextField);
        this.kdtEntrys.getColumn("sharePrice").setEditor(kdtEntrys_sharePrice_CellEditor);
        // kdtDetail
		String kdtDetailStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"benefitProject\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"allocationBase\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"shareAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{benefitProject}</t:Cell><t:Cell>$Resource{allocationBase}</t:Cell><t:Cell>$Resource{shareAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtDetail.setFormatXml(resHelper.translateString("kdtDetail",kdtDetailStrXML));
        this.kdtDetail.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtDetail_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtDetail.putBindContents("editData",new String[] {"Detail.seq","Detail.benefitProject","Detail.allocationBase","Detail.shareAmount"});


        this.kdtDetail.checkParsed();
        final KDBizPromptBox kdtDetail_benefitProject_PromptBox = new KDBizPromptBox();
        kdtDetail_benefitProject_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectForAssActQuery");
        kdtDetail_benefitProject_PromptBox.setVisible(true);
        kdtDetail_benefitProject_PromptBox.setEditable(true);
        kdtDetail_benefitProject_PromptBox.setDisplayFormat("$number$");
        kdtDetail_benefitProject_PromptBox.setEditFormat("$number$");
        kdtDetail_benefitProject_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtDetail_benefitProject_CellEditor = new KDTDefaultCellEditor(kdtDetail_benefitProject_PromptBox);
        this.kdtDetail.getColumn("benefitProject").setEditor(kdtDetail_benefitProject_CellEditor);
        ObjectValueRender kdtDetail_benefitProject_OVR = new ObjectValueRender();
        kdtDetail_benefitProject_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtDetail.getColumn("benefitProject").setRenderer(kdtDetail_benefitProject_OVR);
        KDFormattedTextField kdtDetail_allocationBase_TextField = new KDFormattedTextField();
        kdtDetail_allocationBase_TextField.setName("kdtDetail_allocationBase_TextField");
        kdtDetail_allocationBase_TextField.setVisible(true);
        kdtDetail_allocationBase_TextField.setEditable(true);
        kdtDetail_allocationBase_TextField.setHorizontalAlignment(2);
        kdtDetail_allocationBase_TextField.setDataType(1);
        	kdtDetail_allocationBase_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtDetail_allocationBase_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtDetail_allocationBase_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtDetail_allocationBase_CellEditor = new KDTDefaultCellEditor(kdtDetail_allocationBase_TextField);
        this.kdtDetail.getColumn("allocationBase").setEditor(kdtDetail_allocationBase_CellEditor);
        KDFormattedTextField kdtDetail_shareAmount_TextField = new KDFormattedTextField();
        kdtDetail_shareAmount_TextField.setName("kdtDetail_shareAmount_TextField");
        kdtDetail_shareAmount_TextField.setVisible(true);
        kdtDetail_shareAmount_TextField.setEditable(true);
        kdtDetail_shareAmount_TextField.setHorizontalAlignment(2);
        kdtDetail_shareAmount_TextField.setDataType(1);
        	kdtDetail_shareAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtDetail_shareAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtDetail_shareAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtDetail_shareAmount_CellEditor = new KDTDefaultCellEditor(kdtDetail_shareAmount_TextField);
        this.kdtDetail.getColumn("shareAmount").setEditor(kdtDetail_shareAmount_CellEditor);
        // btnModify
        this.btnModify.setAction((IItemAction)ActionProxyFactory.getProxy(ActionModify, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnModify.setText(resHelper.getString("btnModify.text"));		
        this.btnModify.setToolTipText(resHelper.getString("btnModify.toolTipText"));		
        this.btnModify.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_modifyattribute"));
        // aduitAction
        this.aduitAction.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.aduitAction.setText(resHelper.getString("aduitAction.text"));		
        this.aduitAction.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // unAduit
        this.unAduit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnaudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.unAduit.setText(resHelper.getString("unAduit.text"));		
        this.unAduit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnViewHistoryList
        this.btnViewHistoryList.setAction((IItemAction)ActionProxyFactory.getProxy(ActionHistoryVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewHistoryList.setText(resHelper.getString("btnViewHistoryList.text"));
        this.btnViewHistoryList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnViewHistoryList_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {kdtDetail,txtgsmc,txtbbh,status,prmtengineeringProject,pkBizDate,txtNumber,chkisLast,kdtEntrys}));
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
        this.setBounds(new Rectangle(2, -4, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(2, -4, 1013, 629));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(372, 8, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(372, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contgsmc.setBounds(new Rectangle(10, 34, 270, 19));
        this.add(contgsmc, new KDLayout.Constraints(10, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contbbh.setBounds(new Rectangle(735, 33, 270, 19));
        this.add(contbbh, new KDLayout.Constraints(735, 33, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contstatus.setBounds(new Rectangle(735, 9, 270, 19));
        this.add(contstatus, new KDLayout.Constraints(735, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkisLast.setBounds(new Rectangle(392, 56, 270, 19));
        this.add(chkisLast, new KDLayout.Constraints(392, 56, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contengineeringProject.setBounds(new Rectangle(372, 32, 270, 19));
        this.add(contengineeringProject, new KDLayout.Constraints(372, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(10, 58, 995, 183));
        this.add(kDContainer1, new KDLayout.Constraints(10, 58, 995, 183, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer2.setBounds(new Rectangle(10, 245, 995, 377));
        this.add(kDContainer2, new KDLayout.Constraints(10, 245, 995, 377, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contgsmc
        contgsmc.setBoundEditor(txtgsmc);
        //contbbh
        contbbh.setBoundEditor(txtbbh);
        //contstatus
        contstatus.setBoundEditor(status);
        //contengineeringProject
        contengineeringProject.setBoundEditor(prmtengineeringProject);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryInfo(),null,false);
        kDContainer1.getContentPane().add(kdtEntrys_detailPanel, BorderLayout.CENTER);
		kdtEntrys_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("allocationIndex","1");
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kdtDetail_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtDetail,new com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryDetailInfo(),null,false);
        kDContainer2.getContentPane().add(kdtDetail_detailPanel, BorderLayout.CENTER);

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
        this.toolBar.add(btnModify);
        this.toolBar.add(aduitAction);
        this.toolBar.add(unAduit);
        this.toolBar.add(btnViewHistoryList);
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
		dataBinder.registerBinding("isLast", boolean.class, this.chkisLast, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("gsmc", String.class, this.txtgsmc, "text");
		dataBinder.registerBinding("bbh", String.class, this.txtbbh, "text");
		dataBinder.registerBinding("status", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.status, "selectedItem");
		dataBinder.registerBinding("engineeringProject", com.kingdee.eas.fdc.gcftbiaoa.TreeNodeInfo.class, this.prmtengineeringProject, "data");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.startTime", java.util.Date.class, this.kdtEntrys, "startTime.text");
		dataBinder.registerBinding("entrys.facilityName", java.lang.Object.class, this.kdtEntrys, "facilityName.text");
		dataBinder.registerBinding("entrys.proptreyRight", java.lang.Object.class, this.kdtEntrys, "proptreyRight.text");
		dataBinder.registerBinding("entrys.constructionArea", java.math.BigDecimal.class, this.kdtEntrys, "constructionArea.text");
		dataBinder.registerBinding("entrys.actualStartTine", java.util.Date.class, this.kdtEntrys, "actualStartTine.text");
		dataBinder.registerBinding("entrys.completionTime", java.util.Date.class, this.kdtEntrys, "completionTime.text");
		dataBinder.registerBinding("entrys.totalCost", java.math.BigDecimal.class, this.kdtEntrys, "totalCost.text");
		dataBinder.registerBinding("entrys.costHasOccurred", java.math.BigDecimal.class, this.kdtEntrys, "costHasOccurred.text");
		dataBinder.registerBinding("entrys.allocationIndex", com.kingdee.util.enums.Enum.class, this.kdtEntrys, "allocationIndex.text");
		dataBinder.registerBinding("entrys.totalAmount", java.math.BigDecimal.class, this.kdtEntrys, "totalAmount.text");
		dataBinder.registerBinding("entrys.share", java.math.BigDecimal.class, this.kdtEntrys, "share.text");
		dataBinder.registerBinding("entrys.sharePrice", java.math.BigDecimal.class, this.kdtEntrys, "sharePrice.text");
		dataBinder.registerBinding("entrys.engineeringProject", java.lang.Object.class, this.kdtEntrys, "engineeringProject.text");
		dataBinder.registerBinding("entrys.actualCompeltionTime", java.util.Date.class, this.kdtEntrys, "actualCompeltionTime.text");
		dataBinder.registerBinding("entrys.allshare", boolean.class, this.kdtEntrys, "allshare.text");
		dataBinder.registerBinding("entrys.Detail.seq", int.class, this.kdtDetail, "seq.text");
		dataBinder.registerBinding("entrys.Detail", com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryDetailInfo.class, this.kdtDetail, "userObject");
		dataBinder.registerBinding("entrys.Detail.benefitProject", java.lang.Object.class, this.kdtDetail, "benefitProject.text");
		dataBinder.registerBinding("entrys.Detail.allocationBase", java.math.BigDecimal.class, this.kdtDetail, "allocationBase.text");
		dataBinder.registerBinding("entrys.Detail.shareAmount", java.math.BigDecimal.class, this.kdtDetail, "shareAmount.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.gcftbiaoa.app.GcftbEditUIHandler";
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
        this.kdtDetail.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo)ov;
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("isLast", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("gsmc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bbh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("engineeringProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.startTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.facilityName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.proptreyRight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.constructionArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.actualStartTine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.completionTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.totalCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.costHasOccurred", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.allocationIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.totalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.share", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.sharePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.engineeringProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.actualCompeltionTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.allshare", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Detail.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Detail", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Detail.benefitProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Detail.allocationBase", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.Detail.shareAmount", ValidateHelper.ON_SAVE);    		
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
        //write your code hereaa
    }

    /**
     * output kdtDetail_editStopped method
     */
    protected void kdtDetail_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnViewHistoryList_actionPerformed method
     */
    protected void btnViewHistoryList_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("isLast"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("gsmc"));
        sic.add(new SelectorItemInfo("bbh"));
        sic.add(new SelectorItemInfo("status"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("engineeringProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("engineeringProject.id"));
        	sic.add(new SelectorItemInfo("engineeringProject.number"));
        	sic.add(new SelectorItemInfo("engineeringProject.name"));
		}
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entrys.startTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.facilityName.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.facilityName.id"));
			sic.add(new SelectorItemInfo("entrys.facilityName.name"));
        	sic.add(new SelectorItemInfo("entrys.facilityName.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.proptreyRight.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.proptreyRight.id"));
			sic.add(new SelectorItemInfo("entrys.proptreyRight.name"));
        	sic.add(new SelectorItemInfo("entrys.proptreyRight.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.constructionArea"));
    	sic.add(new SelectorItemInfo("entrys.actualStartTine"));
    	sic.add(new SelectorItemInfo("entrys.completionTime"));
    	sic.add(new SelectorItemInfo("entrys.totalCost"));
    	sic.add(new SelectorItemInfo("entrys.costHasOccurred"));
    	sic.add(new SelectorItemInfo("entrys.allocationIndex"));
    	sic.add(new SelectorItemInfo("entrys.totalAmount"));
    	sic.add(new SelectorItemInfo("entrys.share"));
    	sic.add(new SelectorItemInfo("entrys.sharePrice"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.engineeringProject.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.engineeringProject.id"));
			sic.add(new SelectorItemInfo("entrys.engineeringProject.isEnabled"));
			sic.add(new SelectorItemInfo("entrys.engineeringProject.name"));
        	sic.add(new SelectorItemInfo("entrys.engineeringProject.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.actualCompeltionTime"));
    	sic.add(new SelectorItemInfo("entrys.allshare"));
    	sic.add(new SelectorItemInfo("entrys.Detail.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.Detail.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.Detail.id"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.Detail.benefitProject.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.Detail.benefitProject.id"));
			sic.add(new SelectorItemInfo("entrys.Detail.benefitProject.name"));
        	sic.add(new SelectorItemInfo("entrys.Detail.benefitProject.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.Detail.allocationBase"));
    	sic.add(new SelectorItemInfo("entrys.Detail.shareAmount"));
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
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.gcftbiaoa.GcftbFactory.getRemoteInstance().audit(editData);
    }
    	

    /**
     * output actionUnaudit_actionPerformed method
     */
    public void actionUnaudit_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.gcftbiaoa.GcftbFactory.getRemoteInstance().unaudit(editData);
    }
    	

    /**
     * output actionHistoryVersion_actionPerformed method
     */
    public void actionHistoryVersion_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionModify_actionPerformed method
     */
    public void actionModify_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnaudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnaudit() {
    	return false;
    }
	public RequestContext prepareactionHistoryVersion(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionHistoryVersion() {
    	return false;
    }
	public RequestContext prepareactionModify(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionModify() {
    	return false;
    }

    /**
     * output ActionAudit class
     */     
    protected class ActionAudit extends ItemAction {     
    
        public ActionAudit()
        {
            this(null);
        }

        public ActionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractGcftbEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnaudit class
     */     
    protected class ActionUnaudit extends ItemAction {     
    
        public ActionUnaudit()
        {
            this(null);
        }

        public ActionUnaudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnaudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnaudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnaudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractGcftbEditUI.this, "ActionUnaudit", "actionUnaudit_actionPerformed", e);
        }
    }

    /**
     * output actionHistoryVersion class
     */     
    protected class actionHistoryVersion extends ItemAction {     
    
        public actionHistoryVersion()
        {
            this(null);
        }

        public actionHistoryVersion(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionHistoryVersion.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionHistoryVersion.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionHistoryVersion.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractGcftbEditUI.this, "actionHistoryVersion", "actionHistoryVersion_actionPerformed", e);
        }
    }

    /**
     * output actionModify class
     */     
    protected class actionModify extends ItemAction {     
    
        public actionModify()
        {
            this(null);
        }

        public actionModify(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionModify.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionModify.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionModify.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractGcftbEditUI.this, "actionModify", "actionModify_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.gcftbiaoa.client", "GcftbEditUI");
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
        return com.kingdee.eas.fdc.gcftbiaoa.client.GcftbEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.gcftbiaoa.GcftbFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo objectValue = new com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/gcftbiaoa/Gcftb";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.gcftbiaoa.app.GcftbQuery");
	}
    
        
					protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtNumber.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"单据编号"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(pkBizDate.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"业务日期"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtgsmc.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"公司名称"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtbbh.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"版本号"});
		}
			super.beforeStoreFields(arg0);
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
		vo.put("status","1SAVED");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}