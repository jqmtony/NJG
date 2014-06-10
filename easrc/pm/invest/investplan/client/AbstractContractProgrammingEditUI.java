/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.investplan.client;

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
public abstract class AbstractContractProgrammingEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractProgrammingEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdCheckBoxIsImagePay;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker programmingDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtProgrammingMoney;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractContractProgrammingEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractProgrammingEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdCheckBoxIsImagePay = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.programmingDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtProgrammingMoney = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDContainer1.setName("kDContainer1");
        this.kdCheckBoxIsImagePay.setName("kdCheckBoxIsImagePay");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.prmtCurProject.setName("prmtCurProject");
        this.programmingDate.setName("programmingDate");
        this.txtProgrammingMoney.setName("txtProgrammingMoney");
        this.txtDescription.setName("txtDescription");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kdtEntry.setName("kdtEntry");
        // CoreUI		
        this.btnPageSetup.setEnabled(false);		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnFirst.setEnabled(false);		
        this.btnPre.setVisible(false);		
        this.btnPre.setEnabled(false);		
        this.btnNext.setVisible(false);		
        this.btnNext.setEnabled(false);		
        this.btnLast.setVisible(false);		
        this.btnLast.setEnabled(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrint.setEnabled(false);		
        this.btnPrintPreview.setVisible(false);		
        this.btnPrintPreview.setEnabled(false);		
        this.menuItemSave.setToolTipText(resHelper.getString("menuItemSave.toolTipText"));		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemSubmit.setToolTipText(resHelper.getString("menuItemSubmit.toolTipText"));		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.menuView.setEnabled(false);		
        this.menuItemRemove.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.menuSubmitOption.setEnabled(false);		
        this.menuBiz.setVisible(false);		
        this.menuBiz.setEnabled(false);		
        this.separatorFW1.setVisible(false);		
        this.separatorFW1.setEnabled(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW2.setEnabled(false);		
        this.separatorFW3.setVisible(false);		
        this.separatorFW3.setEnabled(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnAuditResult.setEnabled(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setEnabled(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnMultiapprove.setEnabled(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnNextPerson.setEnabled(false);		
        this.btnVoucher.setEnabled(false);		
        this.btnDelVoucher.setEnabled(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.btnWorkFlowG.setEnabled(false);		
        this.menuWorkflow.setVisible(false);		
        this.menuWorkflow.setEnabled(false);		
        this.separatorFW8.setVisible(false);		
        this.separatorFW8.setEnabled(false);		
        this.separatorFW9.setVisible(false);		
        this.separatorFW9.setEnabled(false);		
        this.separatorFW7.setVisible(false);		
        this.separatorFW7.setEnabled(false);		
        this.btnCreateTo.setEnabled(false);		
        this.btnAudit.setVisible(false);		
        this.btnAudit.setEnabled(false);		
        this.btnUnAudit.setVisible(false);		
        this.btnUnAudit.setEnabled(false);		
        this.btnCalculator.setVisible(false);		
        this.btnCalculator.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(100);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kdCheckBoxIsImagePay		
        this.kdCheckBoxIsImagePay.setText(resHelper.getString("kdCheckBoxIsImagePay.text"));
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // prmtCurProject		
        this.prmtCurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtCurProject.setEnabled(false);
        // programmingDate
        // txtProgrammingMoney		
        this.txtProgrammingMoney.setDataType(1);		
        this.txtProgrammingMoney.setEnabled(false);
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // kDScrollPane1
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costAccountName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aimcost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"programmingMoney\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"prjLongNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"prjDisplayName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{costAccountName}</t:Cell><t:Cell>$Resource{aimcost}</t:Cell><t:Cell>$Resource{programmingMoney}</t:Cell><t:Cell>$Resource{prjLongNumber}</t:Cell><t:Cell>$Resource{prjDisplayName}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntry.putBindContents("editData",new String[] {"costAccount","costAccountName","aimCostMoney","programmingMoney","prjLongNumber","prjDisplayName","id"});


        this.kdtEntry.checkParsed();
        final KDBizPromptBox kdtEntry_costAccount_PromptBox = new KDBizPromptBox();
        kdtEntry_costAccount_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
        kdtEntry_costAccount_PromptBox.setVisible(true);
        kdtEntry_costAccount_PromptBox.setEditable(true);
        kdtEntry_costAccount_PromptBox.setDisplayFormat("$number$");
        kdtEntry_costAccount_PromptBox.setEditFormat("$number$");
        kdtEntry_costAccount_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_costAccount_CellEditor = new KDTDefaultCellEditor(kdtEntry_costAccount_PromptBox);
        this.kdtEntry.getColumn("costAccount").setEditor(kdtEntry_costAccount_CellEditor);
        ObjectValueRender kdtEntry_costAccount_OVR = new ObjectValueRender();
        kdtEntry_costAccount_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("costAccount").setRenderer(kdtEntry_costAccount_OVR);
        KDTextField kdtEntry_costAccountName_TextField = new KDTextField();
        kdtEntry_costAccountName_TextField.setName("kdtEntry_costAccountName_TextField");
        kdtEntry_costAccountName_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_costAccountName_CellEditor = new KDTDefaultCellEditor(kdtEntry_costAccountName_TextField);
        this.kdtEntry.getColumn("costAccountName").setEditor(kdtEntry_costAccountName_CellEditor);
        KDFormattedTextField kdtEntry_aimcost_TextField = new KDFormattedTextField();
        kdtEntry_aimcost_TextField.setName("kdtEntry_aimcost_TextField");
        kdtEntry_aimcost_TextField.setVisible(true);
        kdtEntry_aimcost_TextField.setEditable(true);
        kdtEntry_aimcost_TextField.setHorizontalAlignment(2);
        kdtEntry_aimcost_TextField.setDataType(1);
        	kdtEntry_aimcost_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntry_aimcost_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntry_aimcost_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_aimcost_CellEditor = new KDTDefaultCellEditor(kdtEntry_aimcost_TextField);
        this.kdtEntry.getColumn("aimcost").setEditor(kdtEntry_aimcost_CellEditor);
        KDFormattedTextField kdtEntry_programmingMoney_TextField = new KDFormattedTextField();
        kdtEntry_programmingMoney_TextField.setName("kdtEntry_programmingMoney_TextField");
        kdtEntry_programmingMoney_TextField.setVisible(true);
        kdtEntry_programmingMoney_TextField.setEditable(true);
        kdtEntry_programmingMoney_TextField.setHorizontalAlignment(2);
        kdtEntry_programmingMoney_TextField.setDataType(1);
        	kdtEntry_programmingMoney_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntry_programmingMoney_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntry_programmingMoney_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_programmingMoney_CellEditor = new KDTDefaultCellEditor(kdtEntry_programmingMoney_TextField);
        this.kdtEntry.getColumn("programmingMoney").setEditor(kdtEntry_programmingMoney_CellEditor);
        KDTextField kdtEntry_prjLongNumber_TextField = new KDTextField();
        kdtEntry_prjLongNumber_TextField.setName("kdtEntry_prjLongNumber_TextField");
        kdtEntry_prjLongNumber_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_prjLongNumber_CellEditor = new KDTDefaultCellEditor(kdtEntry_prjLongNumber_TextField);
        this.kdtEntry.getColumn("prjLongNumber").setEditor(kdtEntry_prjLongNumber_CellEditor);
        KDTextField kdtEntry_prjDisplayName_TextField = new KDTextField();
        kdtEntry_prjDisplayName_TextField.setName("kdtEntry_prjDisplayName_TextField");
        kdtEntry_prjDisplayName_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_prjDisplayName_CellEditor = new KDTDefaultCellEditor(kdtEntry_prjDisplayName_TextField);
        this.kdtEntry.getColumn("prjDisplayName").setEditor(kdtEntry_prjDisplayName_CellEditor);
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
        contNumber.setBounds(new Rectangle(371, 12, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(371, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(732, 12, 270, 19));
        this.add(contName, new KDLayout.Constraints(732, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(12, 12, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(12, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(12, 47, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(12, 47, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(370, 47, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(370, 47, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(12, 80, 988, 136));
        this.add(kDLabelContainer5, new KDLayout.Constraints(12, 80, 988, 136, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(12, 230, 990, 384));
        this.add(kDContainer1, new KDLayout.Constraints(12, 230, 990, 384, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdCheckBoxIsImagePay.setBounds(new Rectangle(732, 47, 269, 19));
        this.add(kdCheckBoxIsImagePay, new KDLayout.Constraints(732, 47, 269, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtCurProject);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(programmingDate);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtProgrammingMoney);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtDescription);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(kdtEntry, null);

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
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
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
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isImagePay", boolean.class, this.kdCheckBoxIsImagePay, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtCurProject, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.programmingDate, "value");
		dataBinder.registerBinding("programmingMoney", java.math.BigDecimal.class, this.txtProgrammingMoney, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("entrys.costAccount", com.kingdee.eas.fdc.basedata.CostAccountInfo.class, this.kdtEntry, "costAccount.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entrys.aimCostMoney", java.math.BigDecimal.class, this.kdtEntry, "aimcost.text");
		dataBinder.registerBinding("entrys.programmingMoney", java.math.BigDecimal.class, this.kdtEntry, "programmingMoney.text");
		dataBinder.registerBinding("entrys.costAccountName", String.class, this.kdtEntry, "costAccountName.text");
		dataBinder.registerBinding("entrys.prjLongNumber", String.class, this.kdtEntry, "prjLongNumber.text");
		dataBinder.registerBinding("entrys.prjDisplayName", String.class, this.kdtEntry, "prjDisplayName.text");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntry, "id.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invest.investplan.app.ContractProgrammingEditUIHandler";
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
        this.editData = (com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"CostCenter",editData.getString("number"));
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
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("CostCenter");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("CostCenter");
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
		getValidateHelper().registerBindProperty("isImagePay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("programmingMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.costAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.aimCostMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.programmingMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.costAccountName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.prjLongNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.prjDisplayName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("isImagePay"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("curProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("curProject.id"));
        	sic.add(new SelectorItemInfo("curProject.number"));
        	sic.add(new SelectorItemInfo("curProject.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("programmingMoney"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.costAccount.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.costAccount.id"));
			sic.add(new SelectorItemInfo("entrys.costAccount.name"));
        	sic.add(new SelectorItemInfo("entrys.costAccount.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entrys.aimCostMoney"));
    	sic.add(new SelectorItemInfo("entrys.programmingMoney"));
    	sic.add(new SelectorItemInfo("entrys.costAccountName"));
    	sic.add(new SelectorItemInfo("entrys.prjLongNumber"));
    	sic.add(new SelectorItemInfo("entrys.prjDisplayName"));
    	sic.add(new SelectorItemInfo("entrys.id"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.pm.invest.investplan.client", "ContractProgrammingEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.port.pm.invest.investplan.client.ContractProgrammingEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingInfo objectValue = new com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/pm/invest/investplan/ContractProgramming";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.pm.invest.investplan.app.ContractProgrammingQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntry;
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