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
public abstract class AbstractRichInvoiceRequestEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRichInvoiceRequestEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contldNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contkpUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsales;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbizState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbeizhu;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contkpCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contreqSumAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continvoicedAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contamount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdjAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkdjkp;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtldNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtkpUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsales;
    protected com.kingdee.bos.ctrl.swing.KDComboBox bizState;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanebeizhu;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtbeizhu;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtkpCompany;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtreqSumAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtinvoicedAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtamount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtdjAmount;
    protected com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRichInvoiceRequestEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRichInvoiceRequestEditUI.class.getName());
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
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contldNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contkpUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsales = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbizState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbeizhu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contkpCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contreqSumAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continvoicedAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contamount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdjAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkdjkp = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtldNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtkpUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsales = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.scrollPanebeizhu = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtbeizhu = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtkpCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkauditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtreqSumAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtinvoicedAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtamount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtdjAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.kdtEntrys.setName("kdtEntrys");
        this.contldNumber.setName("contldNumber");
        this.contkpUnit.setName("contkpUnit");
        this.contsales.setName("contsales");
        this.contbizState.setName("contbizState");
        this.contbeizhu.setName("contbeizhu");
        this.contkpCompany.setName("contkpCompany");
        this.contauditDate.setName("contauditDate");
        this.contreqSumAmount.setName("contreqSumAmount");
        this.continvoicedAmount.setName("continvoicedAmount");
        this.contamount.setName("contamount");
        this.contdjAmount.setName("contdjAmount");
        this.chkdjkp.setName("chkdjkp");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtldNumber.setName("txtldNumber");
        this.prmtkpUnit.setName("prmtkpUnit");
        this.prmtsales.setName("prmtsales");
        this.bizState.setName("bizState");
        this.scrollPanebeizhu.setName("scrollPanebeizhu");
        this.txtbeizhu.setName("txtbeizhu");
        this.prmtkpCompany.setName("prmtkpCompany");
        this.pkauditDate.setName("pkauditDate");
        this.txtreqSumAmount.setName("txtreqSumAmount");
        this.txtinvoicedAmount.setName("txtinvoicedAmount");
        this.txtamount.setName("txtamount");
        this.txtdjAmount.setName("txtdjAmount");
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
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"djd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"djjg\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"ysAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"beizhu\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{djd}</t:Cell><t:Cell>$Resource{djjg}</t:Cell><t:Cell>$Resource{ysAmount}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        kdtEntrys.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtEntrys_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});


                this.kdtEntrys.putBindContents("editData",new String[] {"id","djd","djjg","ysAmount","beizhu"});


        this.kdtEntrys.checkParsed();
        final KDBizPromptBox kdtEntrys_djd_PromptBox = new KDBizPromptBox();
        kdtEntrys_djd_PromptBox.setQueryInfo("com.kingdee.eas.custom.richinf.app.RichExamedQuery");
        kdtEntrys_djd_PromptBox.setVisible(true);
        kdtEntrys_djd_PromptBox.setEditable(true);
        kdtEntrys_djd_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_djd_PromptBox.setEditFormat("$number$");
        kdtEntrys_djd_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_djd_CellEditor = new KDTDefaultCellEditor(kdtEntrys_djd_PromptBox);
        this.kdtEntrys.getColumn("djd").setEditor(kdtEntrys_djd_CellEditor);
        ObjectValueRender kdtEntrys_djd_OVR = new ObjectValueRender();
        kdtEntrys_djd_OVR.setFormat(new BizDataFormat("$number$"));
        this.kdtEntrys.getColumn("djd").setRenderer(kdtEntrys_djd_OVR);
        KDTextField kdtEntrys_djjg_TextField = new KDTextField();
        kdtEntrys_djjg_TextField.setName("kdtEntrys_djjg_TextField");
        kdtEntrys_djjg_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntrys_djjg_CellEditor = new KDTDefaultCellEditor(kdtEntrys_djjg_TextField);
        this.kdtEntrys.getColumn("djjg").setEditor(kdtEntrys_djjg_CellEditor);
        KDFormattedTextField kdtEntrys_ysAmount_TextField = new KDFormattedTextField();
        kdtEntrys_ysAmount_TextField.setName("kdtEntrys_ysAmount_TextField");
        kdtEntrys_ysAmount_TextField.setVisible(true);
        kdtEntrys_ysAmount_TextField.setEditable(true);
        kdtEntrys_ysAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_ysAmount_TextField.setDataType(1);
        	kdtEntrys_ysAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntrys_ysAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntrys_ysAmount_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntrys_ysAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_ysAmount_TextField);
        this.kdtEntrys.getColumn("ysAmount").setEditor(kdtEntrys_ysAmount_CellEditor);
        KDTextArea kdtEntrys_beizhu_TextArea = new KDTextArea();
        kdtEntrys_beizhu_TextArea.setName("kdtEntrys_beizhu_TextArea");
        kdtEntrys_beizhu_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrys_beizhu_CellEditor = new KDTDefaultCellEditor(kdtEntrys_beizhu_TextArea);
        this.kdtEntrys.getColumn("beizhu").setEditor(kdtEntrys_beizhu_CellEditor);
        // contldNumber		
        this.contldNumber.setBoundLabelText(resHelper.getString("contldNumber.boundLabelText"));		
        this.contldNumber.setBoundLabelLength(100);		
        this.contldNumber.setBoundLabelUnderline(true);		
        this.contldNumber.setVisible(true);
        // contkpUnit		
        this.contkpUnit.setBoundLabelText(resHelper.getString("contkpUnit.boundLabelText"));		
        this.contkpUnit.setBoundLabelLength(100);		
        this.contkpUnit.setBoundLabelUnderline(true);		
        this.contkpUnit.setVisible(true);
        // contsales		
        this.contsales.setBoundLabelText(resHelper.getString("contsales.boundLabelText"));		
        this.contsales.setBoundLabelLength(100);		
        this.contsales.setBoundLabelUnderline(true);		
        this.contsales.setVisible(true);
        // contbizState		
        this.contbizState.setBoundLabelText(resHelper.getString("contbizState.boundLabelText"));		
        this.contbizState.setBoundLabelLength(100);		
        this.contbizState.setBoundLabelUnderline(true);		
        this.contbizState.setVisible(true);
        // contbeizhu		
        this.contbeizhu.setBoundLabelText(resHelper.getString("contbeizhu.boundLabelText"));		
        this.contbeizhu.setBoundLabelLength(18);		
        this.contbeizhu.setBoundLabelUnderline(true);		
        this.contbeizhu.setVisible(true);		
        this.contbeizhu.setBoundLabelAlignment(8);
        // contkpCompany		
        this.contkpCompany.setBoundLabelText(resHelper.getString("contkpCompany.boundLabelText"));		
        this.contkpCompany.setBoundLabelLength(100);		
        this.contkpCompany.setBoundLabelUnderline(true);		
        this.contkpCompany.setVisible(true);
        // contauditDate		
        this.contauditDate.setBoundLabelText(resHelper.getString("contauditDate.boundLabelText"));		
        this.contauditDate.setBoundLabelLength(100);		
        this.contauditDate.setBoundLabelUnderline(true);		
        this.contauditDate.setVisible(true);
        // contreqSumAmount		
        this.contreqSumAmount.setBoundLabelText(resHelper.getString("contreqSumAmount.boundLabelText"));		
        this.contreqSumAmount.setBoundLabelLength(100);		
        this.contreqSumAmount.setBoundLabelUnderline(true);		
        this.contreqSumAmount.setVisible(true);
        // continvoicedAmount		
        this.continvoicedAmount.setBoundLabelText(resHelper.getString("continvoicedAmount.boundLabelText"));		
        this.continvoicedAmount.setBoundLabelLength(100);		
        this.continvoicedAmount.setBoundLabelUnderline(true);		
        this.continvoicedAmount.setVisible(true);
        // contamount		
        this.contamount.setBoundLabelText(resHelper.getString("contamount.boundLabelText"));		
        this.contamount.setBoundLabelLength(100);		
        this.contamount.setBoundLabelUnderline(true);		
        this.contamount.setVisible(true);
        // contdjAmount		
        this.contdjAmount.setBoundLabelText(resHelper.getString("contdjAmount.boundLabelText"));		
        this.contdjAmount.setBoundLabelLength(100);		
        this.contdjAmount.setBoundLabelUnderline(true);		
        this.contdjAmount.setVisible(true);
        // chkdjkp		
        this.chkdjkp.setText(resHelper.getString("chkdjkp.text"));		
        this.chkdjkp.setVisible(true);		
        this.chkdjkp.setHorizontalAlignment(2);
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
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // txtldNumber		
        this.txtldNumber.setVisible(true);		
        this.txtldNumber.setHorizontalAlignment(2);		
        this.txtldNumber.setMaxLength(100);		
        this.txtldNumber.setRequired(false);
        // prmtkpUnit		
        this.prmtkpUnit.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.CustomerInfoQuery");		
        this.prmtkpUnit.setVisible(true);		
        this.prmtkpUnit.setEditable(true);		
        this.prmtkpUnit.setDisplayFormat("$name$");		
        this.prmtkpUnit.setEditFormat("$number$");		
        this.prmtkpUnit.setCommitFormat("$number$");		
        this.prmtkpUnit.setRequired(false);
        // prmtsales		
        this.prmtsales.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtsales.setVisible(true);		
        this.prmtsales.setEditable(true);		
        this.prmtsales.setDisplayFormat("$name$");		
        this.prmtsales.setEditFormat("$number$");		
        this.prmtsales.setCommitFormat("$number$");		
        this.prmtsales.setRequired(false);
        // bizState		
        this.bizState.setVisible(true);		
        this.bizState.addItems(EnumUtils.getEnumList("com.kingdee.eas.custom.richbase.BizStateEnum").toArray());		
        this.bizState.setRequired(false);
        // scrollPanebeizhu
        // txtbeizhu		
        this.txtbeizhu.setVisible(true);		
        this.txtbeizhu.setRequired(false);		
        this.txtbeizhu.setMaxLength(255);
        // prmtkpCompany		
        this.prmtkpCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.prmtkpCompany.setVisible(true);		
        this.prmtkpCompany.setEditable(true);		
        this.prmtkpCompany.setDisplayFormat("$name$");		
        this.prmtkpCompany.setEditFormat("$number$");		
        this.prmtkpCompany.setCommitFormat("$number$");		
        this.prmtkpCompany.setRequired(false);
        // pkauditDate		
        this.pkauditDate.setVisible(true);		
        this.pkauditDate.setRequired(false);
        // txtreqSumAmount		
        this.txtreqSumAmount.setVisible(true);		
        this.txtreqSumAmount.setHorizontalAlignment(2);		
        this.txtreqSumAmount.setDataType(1);		
        this.txtreqSumAmount.setSupportedEmpty(true);		
        this.txtreqSumAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtreqSumAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtreqSumAmount.setPrecision(2);		
        this.txtreqSumAmount.setRequired(false);
        // txtinvoicedAmount		
        this.txtinvoicedAmount.setVisible(true);		
        this.txtinvoicedAmount.setHorizontalAlignment(2);		
        this.txtinvoicedAmount.setDataType(1);		
        this.txtinvoicedAmount.setSupportedEmpty(true);		
        this.txtinvoicedAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtinvoicedAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtinvoicedAmount.setPrecision(2);		
        this.txtinvoicedAmount.setRequired(false);
        // txtamount		
        this.txtamount.setVisible(true);		
        this.txtamount.setHorizontalAlignment(2);		
        this.txtamount.setDataType(1);		
        this.txtamount.setSupportedEmpty(true);		
        this.txtamount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtamount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtamount.setPrecision(2);		
        this.txtamount.setRequired(false);
        // txtdjAmount		
        this.txtdjAmount.setVisible(true);		
        this.txtdjAmount.setHorizontalAlignment(2);		
        this.txtdjAmount.setDataType(1);		
        this.txtdjAmount.setSupportedEmpty(true);		
        this.txtdjAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtdjAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtdjAmount.setPrecision(2);		
        this.txtdjAmount.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {kDDateLastUpdateTime,prmtLastUpdateUser,kDDateCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,txtNumber,pkauditDate,prmtkpCompany,txtbeizhu,bizState,prmtsales,prmtkpUnit,txtldNumber,kdtEntrys}));
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
        this.setBounds(new Rectangle(0, 0, 1049, 629));
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(440, 524, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(730, 524, 270, 19));
        this.add(contCreateTime, null);
        contLastUpdateUser.setBounds(new Rectangle(440, 555, 270, 19));
        this.add(contLastUpdateUser, null);
        contLastUpdateTime.setBounds(new Rectangle(730, 555, 270, 19));
        this.add(contLastUpdateTime, null);
        contNumber.setBounds(new Rectangle(728, 17, 270, 19));
        this.add(contNumber, null);
        contBizDate.setBounds(new Rectangle(728, 41, 270, 19));
        this.add(contBizDate, null);
        contDescription.setBounds(new Rectangle(14, 579, 984, 19));
        this.add(contDescription, null);
        contAuditor.setBounds(new Rectangle(15, 528, 270, 19));
        this.add(contAuditor, null);
        kdtEntrys.setBounds(new Rectangle(13, 220, 991, 301));
        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.custom.richinf.RichInvoiceRequestEntryInfo(),null,false);
        this.add(kdtEntrys_detailPanel, null);
        contldNumber.setBounds(new Rectangle(15, 41, 270, 19));
        this.add(contldNumber, null);
        contkpUnit.setBounds(new Rectangle(371, 41, 270, 19));
        this.add(contkpUnit, null);
        contsales.setBounds(new Rectangle(15, 17, 270, 19));
        this.add(contsales, null);
        contbizState.setBounds(new Rectangle(728, 88, 270, 19));
        this.add(contbizState, null);
        contbeizhu.setBounds(new Rectangle(15, 107, 984, 109));
        this.add(contbeizhu, null);
        contkpCompany.setBounds(new Rectangle(371, 17, 270, 19));
        this.add(contkpCompany, null);
        contauditDate.setBounds(new Rectangle(15, 555, 270, 19));
        this.add(contauditDate, null);
        contreqSumAmount.setBounds(new Rectangle(371, 65, 270, 19));
        this.add(contreqSumAmount, null);
        continvoicedAmount.setBounds(new Rectangle(728, 65, 270, 19));
        this.add(continvoicedAmount, null);
        contamount.setBounds(new Rectangle(15, 65, 270, 19));
        this.add(contamount, null);
        contdjAmount.setBounds(new Rectangle(15, 89, 270, 19));
        this.add(contdjAmount, null);
        chkdjkp.setBounds(new Rectangle(372, 89, 270, 19));
        this.add(chkdjkp, null);
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
        //contldNumber
        contldNumber.setBoundEditor(txtldNumber);
        //contkpUnit
        contkpUnit.setBoundEditor(prmtkpUnit);
        //contsales
        contsales.setBoundEditor(prmtsales);
        //contbizState
        contbizState.setBoundEditor(bizState);
        //contbeizhu
        contbeizhu.setBoundEditor(scrollPanebeizhu);
        //scrollPanebeizhu
        scrollPanebeizhu.getViewport().add(txtbeizhu, null);
        //contkpCompany
        contkpCompany.setBoundEditor(prmtkpCompany);
        //contauditDate
        contauditDate.setBoundEditor(pkauditDate);
        //contreqSumAmount
        contreqSumAmount.setBoundEditor(txtreqSumAmount);
        //continvoicedAmount
        continvoicedAmount.setBoundEditor(txtinvoicedAmount);
        //contamount
        contamount.setBoundEditor(txtamount);
        //contdjAmount
        contdjAmount.setBoundEditor(txtdjAmount);

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
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.custom.richinf.RichInvoiceRequestEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.djd", java.lang.Object.class, this.kdtEntrys, "djd.text");
		dataBinder.registerBinding("entrys.djjg", String.class, this.kdtEntrys, "djjg.text");
		dataBinder.registerBinding("entrys.ysAmount", java.math.BigDecimal.class, this.kdtEntrys, "ysAmount.text");
		dataBinder.registerBinding("entrys.beizhu", String.class, this.kdtEntrys, "beizhu.text");
		dataBinder.registerBinding("djkp", boolean.class, this.chkdjkp, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("ldNumber", String.class, this.txtldNumber, "text");
		dataBinder.registerBinding("kpUnit", com.kingdee.eas.basedata.master.cssp.CustomerInfo.class, this.prmtkpUnit, "data");
		dataBinder.registerBinding("sales", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtsales, "data");
		dataBinder.registerBinding("bizState", com.kingdee.eas.custom.richbase.BizStateEnum.class, this.bizState, "selectedItem");
		dataBinder.registerBinding("beizhu", String.class, this.txtbeizhu, "text");
		dataBinder.registerBinding("kpCompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtkpCompany, "data");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.pkauditDate, "value");
		dataBinder.registerBinding("reqSumAmount", java.math.BigDecimal.class, this.txtreqSumAmount, "value");
		dataBinder.registerBinding("invoicedAmount", java.math.BigDecimal.class, this.txtinvoicedAmount, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtamount, "value");
		dataBinder.registerBinding("djAmount", java.math.BigDecimal.class, this.txtdjAmount, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.custom.richinf.app.RichInvoiceRequestEditUIHandler";
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
        this.editData = (com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo)ov;
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
		getValidateHelper().registerBindProperty("entrys.djd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.djjg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.ysAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djkp", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ldNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("kpUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sales", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("beizhu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("kpCompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reqSumAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invoicedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("djAmount", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntrys_Changed(int rowIndex,int colIndex) method
     */
    public void kdtEntrys_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("djd".equalsIgnoreCase(kdtEntrys.getColumn(colIndex).getKey())) {
kdtEntrys.getCell(rowIndex,"djjg").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntrys.getCell(rowIndex,"djd").getValue(),"djCompany.name")));

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
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.djd.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.djd.id"));
			sic.add(new SelectorItemInfo("entrys.djd.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.djjg"));
    	sic.add(new SelectorItemInfo("entrys.ysAmount"));
    	sic.add(new SelectorItemInfo("entrys.beizhu"));
        sic.add(new SelectorItemInfo("djkp"));
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
        sic.add(new SelectorItemInfo("ldNumber"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("kpUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("kpUnit.id"));
        	sic.add(new SelectorItemInfo("kpUnit.number"));
        	sic.add(new SelectorItemInfo("kpUnit.name"));
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
        sic.add(new SelectorItemInfo("bizState"));
        sic.add(new SelectorItemInfo("beizhu"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("kpCompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("kpCompany.id"));
        	sic.add(new SelectorItemInfo("kpCompany.number"));
        	sic.add(new SelectorItemInfo("kpCompany.name"));
		}
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("reqSumAmount"));
        sic.add(new SelectorItemInfo("invoicedAmount"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("djAmount"));
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
        return new MetaDataPK("com.kingdee.eas.custom.richinf.client", "RichInvoiceRequestEditUI");
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
        return com.kingdee.eas.custom.richinf.client.RichInvoiceRequestEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.richinf.RichInvoiceRequestFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo objectValue = new com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/custom/richinf/RichInvoiceRequest";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.custom.richinf.app.RichInvoiceRequestQuery");
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
		vo.put("bizState","30");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}