/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.client;

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
public abstract class AbstractRichCustomWriteOffEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRichCustomWriteOffEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsales;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtFpEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtFpEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtDjEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtDjEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contkpCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsales;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtkpCustomer;
    protected com.kingdee.eas.custom.richinf.RichCustomWriteOffInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRichCustomWriteOffEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRichCustomWriteOffEditUI.class.getName());
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
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsales = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtFpEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtDjEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contkpCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsales = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtkpCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contsales.setName("contsales");
        this.kdtFpEntry.setName("kdtFpEntry");
        this.kdtDjEntry.setName("kdtDjEntry");
        this.contkpCustomer.setName("contkpCustomer");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.prmtsales.setName("prmtsales");
        this.prmtkpCustomer.setName("prmtkpCustomer");
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
        this.contBizDate.setVisible(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contsales		
        this.contsales.setBoundLabelText(resHelper.getString("contsales.boundLabelText"));		
        this.contsales.setBoundLabelLength(100);		
        this.contsales.setBoundLabelUnderline(true);		
        this.contsales.setVisible(true);
        // kdtFpEntry
		String kdtFpEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"fpNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"kpUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"kpCompany\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fpAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"yhxAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"whxAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{fpNo}</t:Cell><t:Cell>$Resource{kpUnit}</t:Cell><t:Cell>$Resource{kpCompany}</t:Cell><t:Cell>$Resource{fpAmount}</t:Cell><t:Cell>$Resource{yhxAmount}</t:Cell><t:Cell>$Resource{whxAmount}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtFpEntry.setFormatXml(resHelper.translateString("kdtFpEntry",kdtFpEntryStrXML));
        kdtFpEntry.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtFpEntry_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});


                this.kdtFpEntry.putBindContents("editData",new String[] {"seq","fpNo","kpUnit","kpCompany","fpAmount","yhxAmount","whxAmount","beizhu"});


        this.kdtFpEntry.checkParsed();
        final KDBizPromptBox kdtFpEntry_fpNo_PromptBox = new KDBizPromptBox();
        kdtFpEntry_fpNo_PromptBox.setQueryInfo("com.kingdee.eas.fi.ar.app.OtherBill");
        kdtFpEntry_fpNo_PromptBox.setVisible(true);
        kdtFpEntry_fpNo_PromptBox.setEditable(true);
        kdtFpEntry_fpNo_PromptBox.setDisplayFormat("$number$");
        kdtFpEntry_fpNo_PromptBox.setEditFormat("$number$");
        kdtFpEntry_fpNo_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtFpEntry_fpNo_CellEditor = new KDTDefaultCellEditor(kdtFpEntry_fpNo_PromptBox);
        this.kdtFpEntry.getColumn("fpNo").setEditor(kdtFpEntry_fpNo_CellEditor);
        ObjectValueRender kdtFpEntry_fpNo_OVR = new ObjectValueRender();
        kdtFpEntry_fpNo_OVR.setFormat(new BizDataFormat("$number$"));
        this.kdtFpEntry.getColumn("fpNo").setRenderer(kdtFpEntry_fpNo_OVR);
        final KDBizPromptBox kdtFpEntry_kpUnit_PromptBox = new KDBizPromptBox();
        kdtFpEntry_kpUnit_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.CustomerInfoQuery");
        kdtFpEntry_kpUnit_PromptBox.setVisible(true);
        kdtFpEntry_kpUnit_PromptBox.setEditable(true);
        kdtFpEntry_kpUnit_PromptBox.setDisplayFormat("$number$");
        kdtFpEntry_kpUnit_PromptBox.setEditFormat("$number$");
        kdtFpEntry_kpUnit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtFpEntry_kpUnit_CellEditor = new KDTDefaultCellEditor(kdtFpEntry_kpUnit_PromptBox);
        this.kdtFpEntry.getColumn("kpUnit").setEditor(kdtFpEntry_kpUnit_CellEditor);
        ObjectValueRender kdtFpEntry_kpUnit_OVR = new ObjectValueRender();
        kdtFpEntry_kpUnit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtFpEntry.getColumn("kpUnit").setRenderer(kdtFpEntry_kpUnit_OVR);
        final KDBizPromptBox kdtFpEntry_kpCompany_PromptBox = new KDBizPromptBox();
        kdtFpEntry_kpCompany_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery4AsstAcct");
        kdtFpEntry_kpCompany_PromptBox.setVisible(true);
        kdtFpEntry_kpCompany_PromptBox.setEditable(true);
        kdtFpEntry_kpCompany_PromptBox.setDisplayFormat("$number$");
        kdtFpEntry_kpCompany_PromptBox.setEditFormat("$number$");
        kdtFpEntry_kpCompany_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtFpEntry_kpCompany_CellEditor = new KDTDefaultCellEditor(kdtFpEntry_kpCompany_PromptBox);
        this.kdtFpEntry.getColumn("kpCompany").setEditor(kdtFpEntry_kpCompany_CellEditor);
        ObjectValueRender kdtFpEntry_kpCompany_OVR = new ObjectValueRender();
        kdtFpEntry_kpCompany_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtFpEntry.getColumn("kpCompany").setRenderer(kdtFpEntry_kpCompany_OVR);
        KDFormattedTextField kdtFpEntry_fpAmount_TextField = new KDFormattedTextField();
        kdtFpEntry_fpAmount_TextField.setName("kdtFpEntry_fpAmount_TextField");
        kdtFpEntry_fpAmount_TextField.setVisible(true);
        kdtFpEntry_fpAmount_TextField.setEditable(true);
        kdtFpEntry_fpAmount_TextField.setHorizontalAlignment(2);
        kdtFpEntry_fpAmount_TextField.setDataType(1);
        	kdtFpEntry_fpAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtFpEntry_fpAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtFpEntry_fpAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtFpEntry_fpAmount_CellEditor = new KDTDefaultCellEditor(kdtFpEntry_fpAmount_TextField);
        this.kdtFpEntry.getColumn("fpAmount").setEditor(kdtFpEntry_fpAmount_CellEditor);
        KDFormattedTextField kdtFpEntry_yhxAmount_TextField = new KDFormattedTextField();
        kdtFpEntry_yhxAmount_TextField.setName("kdtFpEntry_yhxAmount_TextField");
        kdtFpEntry_yhxAmount_TextField.setVisible(true);
        kdtFpEntry_yhxAmount_TextField.setEditable(true);
        kdtFpEntry_yhxAmount_TextField.setHorizontalAlignment(2);
        kdtFpEntry_yhxAmount_TextField.setDataType(1);
        	kdtFpEntry_yhxAmount_TextField.setMinimumValue(new java.math.BigDecimal("-3.4028234663852886E38"));
        	kdtFpEntry_yhxAmount_TextField.setMaximumValue(new java.math.BigDecimal("3.4028234663852886E38"));
        kdtFpEntry_yhxAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtFpEntry_yhxAmount_CellEditor = new KDTDefaultCellEditor(kdtFpEntry_yhxAmount_TextField);
        this.kdtFpEntry.getColumn("yhxAmount").setEditor(kdtFpEntry_yhxAmount_CellEditor);
        KDFormattedTextField kdtFpEntry_whxAmount_TextField = new KDFormattedTextField();
        kdtFpEntry_whxAmount_TextField.setName("kdtFpEntry_whxAmount_TextField");
        kdtFpEntry_whxAmount_TextField.setVisible(true);
        kdtFpEntry_whxAmount_TextField.setEditable(true);
        kdtFpEntry_whxAmount_TextField.setHorizontalAlignment(2);
        kdtFpEntry_whxAmount_TextField.setDataType(1);
        	kdtFpEntry_whxAmount_TextField.setMinimumValue(new java.math.BigDecimal("-3.4028234663852886E38"));
        	kdtFpEntry_whxAmount_TextField.setMaximumValue(new java.math.BigDecimal("3.4028234663852886E38"));
        kdtFpEntry_whxAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtFpEntry_whxAmount_CellEditor = new KDTDefaultCellEditor(kdtFpEntry_whxAmount_TextField);
        this.kdtFpEntry.getColumn("whxAmount").setEditor(kdtFpEntry_whxAmount_CellEditor);
        KDTextField kdtFpEntry_beizhu_TextField = new KDTextField();
        kdtFpEntry_beizhu_TextField.setName("kdtFpEntry_beizhu_TextField");
        kdtFpEntry_beizhu_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtFpEntry_beizhu_CellEditor = new KDTDefaultCellEditor(kdtFpEntry_beizhu_TextField);
        this.kdtFpEntry.getColumn("beizhu").setEditor(kdtFpEntry_beizhu_CellEditor);
        // kdtDjEntry
		String kdtDjEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"kpUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"djjg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"djNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"ldNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"jsAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{kpUnit}</t:Cell><t:Cell>$Resource{djjg}</t:Cell><t:Cell>$Resource{djNo}</t:Cell><t:Cell>$Resource{ldNo}</t:Cell><t:Cell>$Resource{jsAmount}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtDjEntry.setFormatXml(resHelper.translateString("kdtDjEntry",kdtDjEntryStrXML));
        kdtDjEntry.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtDjEntry_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});


                this.kdtDjEntry.putBindContents("editData",new String[] {"seq","kpUnit","djjg","djNo","ldNo","jsAmount","beizhu"});


        this.kdtDjEntry.checkParsed();
        final KDBizPromptBox kdtDjEntry_kpUnit_PromptBox = new KDBizPromptBox();
        kdtDjEntry_kpUnit_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.CustomerInfoQuery");
        kdtDjEntry_kpUnit_PromptBox.setVisible(true);
        kdtDjEntry_kpUnit_PromptBox.setEditable(true);
        kdtDjEntry_kpUnit_PromptBox.setDisplayFormat("$number$");
        kdtDjEntry_kpUnit_PromptBox.setEditFormat("$number$");
        kdtDjEntry_kpUnit_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtDjEntry_kpUnit_CellEditor = new KDTDefaultCellEditor(kdtDjEntry_kpUnit_PromptBox);
        this.kdtDjEntry.getColumn("kpUnit").setEditor(kdtDjEntry_kpUnit_CellEditor);
        ObjectValueRender kdtDjEntry_kpUnit_OVR = new ObjectValueRender();
        kdtDjEntry_kpUnit_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtDjEntry.getColumn("kpUnit").setRenderer(kdtDjEntry_kpUnit_OVR);
        final KDBizPromptBox kdtDjEntry_djjg_PromptBox = new KDBizPromptBox();
        kdtDjEntry_djjg_PromptBox.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery4AsstAcct");
        kdtDjEntry_djjg_PromptBox.setVisible(true);
        kdtDjEntry_djjg_PromptBox.setEditable(true);
        kdtDjEntry_djjg_PromptBox.setDisplayFormat("$number$");
        kdtDjEntry_djjg_PromptBox.setEditFormat("$number$");
        kdtDjEntry_djjg_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtDjEntry_djjg_CellEditor = new KDTDefaultCellEditor(kdtDjEntry_djjg_PromptBox);
        this.kdtDjEntry.getColumn("djjg").setEditor(kdtDjEntry_djjg_CellEditor);
        ObjectValueRender kdtDjEntry_djjg_OVR = new ObjectValueRender();
        kdtDjEntry_djjg_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtDjEntry.getColumn("djjg").setRenderer(kdtDjEntry_djjg_OVR);
        final KDBizPromptBox kdtDjEntry_djNo_PromptBox = new KDBizPromptBox();
        kdtDjEntry_djNo_PromptBox.setQueryInfo("com.kingdee.eas.custom.richinf.app.RichExamedQuery");
        kdtDjEntry_djNo_PromptBox.setVisible(true);
        kdtDjEntry_djNo_PromptBox.setEditable(true);
        kdtDjEntry_djNo_PromptBox.setDisplayFormat("$number$");
        kdtDjEntry_djNo_PromptBox.setEditFormat("$number$");
        kdtDjEntry_djNo_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtDjEntry_djNo_CellEditor = new KDTDefaultCellEditor(kdtDjEntry_djNo_PromptBox);
        this.kdtDjEntry.getColumn("djNo").setEditor(kdtDjEntry_djNo_CellEditor);
        ObjectValueRender kdtDjEntry_djNo_OVR = new ObjectValueRender();
        kdtDjEntry_djNo_OVR.setFormat(new BizDataFormat("$number$"));
        this.kdtDjEntry.getColumn("djNo").setRenderer(kdtDjEntry_djNo_OVR);
        KDTextField kdtDjEntry_ldNo_TextField = new KDTextField();
        kdtDjEntry_ldNo_TextField.setName("kdtDjEntry_ldNo_TextField");
        kdtDjEntry_ldNo_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtDjEntry_ldNo_CellEditor = new KDTDefaultCellEditor(kdtDjEntry_ldNo_TextField);
        this.kdtDjEntry.getColumn("ldNo").setEditor(kdtDjEntry_ldNo_CellEditor);
        KDTextField kdtDjEntry_jsAmount_TextField = new KDTextField();
        kdtDjEntry_jsAmount_TextField.setName("kdtDjEntry_jsAmount_TextField");
        kdtDjEntry_jsAmount_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtDjEntry_jsAmount_CellEditor = new KDTDefaultCellEditor(kdtDjEntry_jsAmount_TextField);
        this.kdtDjEntry.getColumn("jsAmount").setEditor(kdtDjEntry_jsAmount_CellEditor);
        KDTextField kdtDjEntry_beizhu_TextField = new KDTextField();
        kdtDjEntry_beizhu_TextField.setName("kdtDjEntry_beizhu_TextField");
        kdtDjEntry_beizhu_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtDjEntry_beizhu_CellEditor = new KDTDefaultCellEditor(kdtDjEntry_beizhu_TextField);
        this.kdtDjEntry.getColumn("beizhu").setEditor(kdtDjEntry_beizhu_CellEditor);
        // contkpCustomer		
        this.contkpCustomer.setBoundLabelText(resHelper.getString("contkpCustomer.boundLabelText"));		
        this.contkpCustomer.setBoundLabelLength(100);		
        this.contkpCustomer.setBoundLabelUnderline(true);		
        this.contkpCustomer.setVisible(true);
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
        this.pkBizDate.setEnabled(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // prmtsales		
        this.prmtsales.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtsales.setEditable(true);		
        this.prmtsales.setDisplayFormat("$name$");		
        this.prmtsales.setEditFormat("$number$");		
        this.prmtsales.setCommitFormat("$number$");		
        this.prmtsales.setRequired(false);
        // prmtkpCustomer		
        this.prmtkpCustomer.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.CustomerInfoQuery");		
        this.prmtkpCustomer.setVisible(true);		
        this.prmtkpCustomer.setEditable(true);		
        this.prmtkpCustomer.setDisplayFormat("$name$");		
        this.prmtkpCustomer.setEditFormat("$number$");		
        this.prmtkpCustomer.setCommitFormat("$number$");		
        this.prmtkpCustomer.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {kDDateLastUpdateTime,prmtLastUpdateUser,kDDateCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,prmtsales,kdtFpEntry}));
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
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(440, 524, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(730, 524, 270, 19));
        this.add(contCreateTime, null);
        contLastUpdateUser.setBounds(new Rectangle(440, 555, 270, 19));
        this.add(contLastUpdateUser, null);
        contLastUpdateTime.setBounds(new Rectangle(730, 555, 270, 19));
        this.add(contLastUpdateTime, null);
        contNumber.setBounds(new Rectangle(729, 12, 270, 19));
        this.add(contNumber, null);
        contBizDate.setBounds(new Rectangle(729, 36, 270, 19));
        this.add(contBizDate, null);
        contDescription.setBounds(new Rectangle(13, 36, 271, 19));
        this.add(contDescription, null);
        contAuditor.setBounds(new Rectangle(15, 528, 270, 19));
        this.add(contAuditor, null);
        contsales.setBounds(new Rectangle(13, 12, 270, 19));
        this.add(contsales, null);
        kdtFpEntry.setBounds(new Rectangle(10, 60, 992, 170));
        kdtFpEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtFpEntry,new com.kingdee.eas.custom.richinf.RichCustomWriteOffFpEntryInfo(),null,false);
        this.add(kdtFpEntry_detailPanel, null);
        kdtDjEntry.setBounds(new Rectangle(10, 234, 992, 287));
        kdtDjEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtDjEntry,new com.kingdee.eas.custom.richinf.RichCustomWriteOffDjEntryInfo(),null,false);
        this.add(kdtDjEntry_detailPanel, null);
        contkpCustomer.setBounds(new Rectangle(372, 12, 270, 19));
        this.add(contkpCustomer, null);
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
        //contsales
        contsales.setBoundEditor(prmtsales);
        //contkpCustomer
        contkpCustomer.setBoundEditor(prmtkpCustomer);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("FpEntry.seq", int.class, this.kdtFpEntry, "seq.text");
		dataBinder.registerBinding("FpEntry", com.kingdee.eas.custom.richinf.RichCustomWriteOffFpEntryInfo.class, this.kdtFpEntry, "userObject");
		dataBinder.registerBinding("FpEntry.fpNo", java.lang.Object.class, this.kdtFpEntry, "fpNo.text");
		dataBinder.registerBinding("FpEntry.kpUnit", java.lang.Object.class, this.kdtFpEntry, "kpUnit.text");
		dataBinder.registerBinding("FpEntry.kpCompany", java.lang.Object.class, this.kdtFpEntry, "kpCompany.text");
		dataBinder.registerBinding("FpEntry.fpAmount", java.math.BigDecimal.class, this.kdtFpEntry, "fpAmount.text");
		dataBinder.registerBinding("FpEntry.beizhu", String.class, this.kdtFpEntry, "beizhu.text");
		dataBinder.registerBinding("FpEntry.yhxAmount", java.math.BigDecimal.class, this.kdtFpEntry, "yhxAmount.text");
		dataBinder.registerBinding("FpEntry.whxAmount", java.math.BigDecimal.class, this.kdtFpEntry, "whxAmount.text");
		dataBinder.registerBinding("DjEntry.seq", int.class, this.kdtDjEntry, "seq.text");
		dataBinder.registerBinding("DjEntry", com.kingdee.eas.custom.richinf.RichCustomWriteOffDjEntryInfo.class, this.kdtDjEntry, "userObject");
		dataBinder.registerBinding("DjEntry.kpUnit", java.lang.Object.class, this.kdtDjEntry, "kpUnit.text");
		dataBinder.registerBinding("DjEntry.djjg", java.lang.Object.class, this.kdtDjEntry, "djjg.text");
		dataBinder.registerBinding("DjEntry.djNo", java.lang.Object.class, this.kdtDjEntry, "djNo.text");
		dataBinder.registerBinding("DjEntry.ldNo", String.class, this.kdtDjEntry, "ldNo.text");
		dataBinder.registerBinding("DjEntry.jsAmount", String.class, this.kdtDjEntry, "jsAmount.text");
		dataBinder.registerBinding("DjEntry.beizhu", String.class, this.kdtDjEntry, "beizhu.text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("sales", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtsales, "data");
		dataBinder.registerBinding("kpCustomer", com.kingdee.eas.basedata.master.cssp.CustomerInfo.class, this.prmtkpCustomer, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.custom.richinf.app.RichCustomWriteOffEditUIHandler";
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
        this.kDDateLastUpdateTime.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.custom.richinf.RichCustomWriteOffInfo)ov;
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
		getValidateHelper().registerBindProperty("FpEntry.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("FpEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("FpEntry.fpNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("FpEntry.kpUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("FpEntry.kpCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("FpEntry.fpAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("FpEntry.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("FpEntry.yhxAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("FpEntry.whxAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DjEntry.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DjEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DjEntry.kpUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DjEntry.djjg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DjEntry.djNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DjEntry.ldNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DjEntry.jsAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DjEntry.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sales", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("kpCustomer", ValidateHelper.ON_SAVE);    		
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
     * output kdtFpEntry_Changed(int rowIndex,int colIndex) method
     */
    public void kdtFpEntry_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("fpNo".equalsIgnoreCase(kdtFpEntry.getColumn(colIndex).getKey())) {
kdtFpEntry.getCell(rowIndex,"kpUnit").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtFpEntry.getCell(rowIndex,"fpNo").getValue(),"asstActName")));

}

    if ("fpNo".equalsIgnoreCase(kdtFpEntry.getColumn(colIndex).getKey())) {
kdtFpEntry.getCell(rowIndex,"yhxAmount").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getBigDecimal(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtFpEntry.getCell(rowIndex,"fpNo").getValue(),"verifyAmount")));

}

    if ("fpNo".equalsIgnoreCase(kdtFpEntry.getColumn(colIndex).getKey())) {
kdtFpEntry.getCell(rowIndex,"whxAmount").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getBigDecimal(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtFpEntry.getCell(rowIndex,"fpNo").getValue(),"unVerifyAmount")));

}


    }

    /**
     * output kdtDjEntry_Changed(int rowIndex,int colIndex) method
     */
    public void kdtDjEntry_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("djNo".equalsIgnoreCase(kdtDjEntry.getColumn(colIndex).getKey())) {
kdtDjEntry.getCell(rowIndex,"ldNo").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtDjEntry.getCell(rowIndex,"djNo").getValue(),"ldNumber")));

}

    if ("djNo".equalsIgnoreCase(kdtDjEntry.getColumn(colIndex).getKey())) {
kdtDjEntry.getCell(rowIndex,"jsAmount").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtDjEntry.getCell(rowIndex,"djNo").getValue(),"amount")));

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
    	sic.add(new SelectorItemInfo("FpEntry.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("FpEntry.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("FpEntry.fpNo.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("FpEntry.fpNo.id"));
			sic.add(new SelectorItemInfo("FpEntry.fpNo.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("FpEntry.kpUnit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("FpEntry.kpUnit.id"));
			sic.add(new SelectorItemInfo("FpEntry.kpUnit.name"));
        	sic.add(new SelectorItemInfo("FpEntry.kpUnit.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("FpEntry.kpCompany.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("FpEntry.kpCompany.id"));
			sic.add(new SelectorItemInfo("FpEntry.kpCompany.name"));
        	sic.add(new SelectorItemInfo("FpEntry.kpCompany.number"));
		}
    	sic.add(new SelectorItemInfo("FpEntry.fpAmount"));
    	sic.add(new SelectorItemInfo("FpEntry.beizhu"));
    	sic.add(new SelectorItemInfo("FpEntry.yhxAmount"));
    	sic.add(new SelectorItemInfo("FpEntry.whxAmount"));
    	sic.add(new SelectorItemInfo("DjEntry.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("DjEntry.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("DjEntry.kpUnit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("DjEntry.kpUnit.id"));
			sic.add(new SelectorItemInfo("DjEntry.kpUnit.name"));
        	sic.add(new SelectorItemInfo("DjEntry.kpUnit.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("DjEntry.djjg.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("DjEntry.djjg.id"));
			sic.add(new SelectorItemInfo("DjEntry.djjg.name"));
        	sic.add(new SelectorItemInfo("DjEntry.djjg.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("DjEntry.djNo.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("DjEntry.djNo.id"));
			sic.add(new SelectorItemInfo("DjEntry.djNo.number"));
		}
    	sic.add(new SelectorItemInfo("DjEntry.ldNo"));
    	sic.add(new SelectorItemInfo("DjEntry.jsAmount"));
    	sic.add(new SelectorItemInfo("DjEntry.beizhu"));
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("sales.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("sales.id"));
        	sic.add(new SelectorItemInfo("sales.number"));
        	sic.add(new SelectorItemInfo("sales.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("kpCustomer.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("kpCustomer.id"));
        	sic.add(new SelectorItemInfo("kpCustomer.number"));
        	sic.add(new SelectorItemInfo("kpCustomer.name"));
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

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.custom.richinf.client", "RichCustomWriteOffEditUI");
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
        return com.kingdee.eas.custom.richinf.client.RichCustomWriteOffEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.richinf.RichCustomWriteOffFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.custom.richinf.RichCustomWriteOffInfo objectValue = new com.kingdee.eas.custom.richinf.RichCustomWriteOffInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/custom/richinf/RichCustomWriteOff";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.custom.richinf.app.RichCustomWriteOffQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtFpEntry;
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