/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.contractsplit.client;

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
public abstract class AbstractContractPCSplitBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractPCSplitBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnSplitAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSplitedAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractName;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreatetime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtUnSplitAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSplitedAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPCSelect;
    protected com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillInfo editData = null;
    protected ActionPCSelect actionPCSelect = null;
    /**
     * output class constructor
     */
    public AbstractContractPCSplitBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractPCSplitBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionPCSelect
        this.actionPCSelect = new ActionPCSelect(this);
        getActionManager().registerAction("actionPCSelect", actionPCSelect);
         this.actionPCSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contContractNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUnSplitAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSplitedAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreatetime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtContractNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtUnSplitAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSplitedAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnPCSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contContractNumber.setName("contContractNumber");
        this.contAmount.setName("contAmount");
        this.contUnSplitAmount.setName("contUnSplitAmount");
        this.contSplitedAmount.setName("contSplitedAmount");
        this.contContractName.setName("contContractName");
        this.kdtEntrys.setName("kdtEntrys");
        this.contCreator.setName("contCreator");
        this.contCreatetime.setName("contCreatetime");
        this.txtContractNumber.setName("txtContractNumber");
        this.txtAmount.setName("txtAmount");
        this.txtUnSplitAmount.setName("txtUnSplitAmount");
        this.txtSplitedAmount.setName("txtSplitedAmount");
        this.txtContractName.setName("txtContractName");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.btnPCSelect.setName("btnPCSelect");
        // CoreUI		
        this.kDSeparator1.setVisible(false);		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemAddNew.setEnabled(false);		
        this.menuItemSubmit.setEnabled(false);		
        this.menuItemSubmit.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuItemCopy.setEnabled(false);		
        this.menuView.setEnabled(false);		
        this.menuView.setVisible(false);		
        this.menuItemEdit.setVisible(false);		
        this.menuItemEdit.setEnabled(false);		
        this.menuSubmitOption.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCreateFrom.setEnabled(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemCopyFrom.setEnabled(false);		
        this.separator1.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.menuItemAddLine.setEnabled(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemInsertLine.setEnabled(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuWorkflow.setVisible(false);		
        this.menuWorkflow.setEnabled(false);
        // contContractNumber		
        this.contContractNumber.setBoundLabelText(resHelper.getString("contContractNumber.boundLabelText"));		
        this.contContractNumber.setBoundLabelLength(100);		
        this.contContractNumber.setBoundLabelUnderline(true);		
        this.contContractNumber.setBoundLabelAlignment(7);		
        this.contContractNumber.setVisible(true);
        // contAmount		
        this.contAmount.setBoundLabelText(resHelper.getString("contAmount.boundLabelText"));		
        this.contAmount.setBoundLabelLength(100);		
        this.contAmount.setBoundLabelUnderline(true);		
        this.contAmount.setVisible(true);		
        this.contAmount.setBoundLabelAlignment(7);
        // contUnSplitAmount		
        this.contUnSplitAmount.setBoundLabelText(resHelper.getString("contUnSplitAmount.boundLabelText"));		
        this.contUnSplitAmount.setBoundLabelLength(100);		
        this.contUnSplitAmount.setBoundLabelUnderline(true);		
        this.contUnSplitAmount.setVisible(true);		
        this.contUnSplitAmount.setBoundLabelAlignment(7);
        // contSplitedAmount		
        this.contSplitedAmount.setBoundLabelText(resHelper.getString("contSplitedAmount.boundLabelText"));		
        this.contSplitedAmount.setBoundLabelLength(100);		
        this.contSplitedAmount.setBoundLabelUnderline(true);		
        this.contSplitedAmount.setVisible(true);		
        this.contSplitedAmount.setBoundLabelAlignment(7);
        // contContractName		
        this.contContractName.setBoundLabelText(resHelper.getString("contContractName.boundLabelText"));		
        this.contContractName.setBoundLabelLength(100);		
        this.contContractName.setBoundLabelUnderline(true);		
        this.contContractName.setVisible(true);		
        this.contContractName.setBoundLabelAlignment(7);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"curProject\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"pcNumber\" t:width=\"220\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"pcName\" t:width=\"220\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"pcAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"scale\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{curProject}</t:Cell><t:Cell>$Resource{pcNumber}</t:Cell><t:Cell>$Resource{pcName}</t:Cell><t:Cell>$Resource{pcAmount}</t:Cell><t:Cell>$Resource{scale}</t:Cell><t:Cell>$Resource{amount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
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

                this.kdtEntrys.putBindContents("editData",new String[] {"programmingContract.programming.project.name","programmingContract.longNumber","programmingContract.name","programmingContract.amount","scale","amount"});


        this.kdtEntrys.checkParsed();
        KDTextField kdtEntrys_pcName_TextField = new KDTextField();
        kdtEntrys_pcName_TextField.setName("kdtEntrys_pcName_TextField");
        kdtEntrys_pcName_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrys_pcName_CellEditor = new KDTDefaultCellEditor(kdtEntrys_pcName_TextField);
        this.kdtEntrys.getColumn("pcName").setEditor(kdtEntrys_pcName_CellEditor);
        KDFormattedTextField kdtEntrys_pcAmount_TextField = new KDFormattedTextField();
        kdtEntrys_pcAmount_TextField.setName("kdtEntrys_pcAmount_TextField");
        kdtEntrys_pcAmount_TextField.setVisible(true);
        kdtEntrys_pcAmount_TextField.setEditable(true);
        kdtEntrys_pcAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_pcAmount_TextField.setDataType(1);
        	kdtEntrys_pcAmount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntrys_pcAmount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntrys_pcAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_pcAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_pcAmount_TextField);
        this.kdtEntrys.getColumn("pcAmount").setEditor(kdtEntrys_pcAmount_CellEditor);
        KDFormattedTextField kdtEntrys_scale_TextField = new KDFormattedTextField();
        kdtEntrys_scale_TextField.setName("kdtEntrys_scale_TextField");
        kdtEntrys_scale_TextField.setVisible(true);
        kdtEntrys_scale_TextField.setEditable(true);
        kdtEntrys_scale_TextField.setHorizontalAlignment(2);
        kdtEntrys_scale_TextField.setDataType(1);
        	kdtEntrys_scale_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntrys_scale_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntrys_scale_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_scale_CellEditor = new KDTDefaultCellEditor(kdtEntrys_scale_TextField);
        this.kdtEntrys.getColumn("scale").setEditor(kdtEntrys_scale_CellEditor);
        KDFormattedTextField kdtEntrys_amount_TextField = new KDFormattedTextField();
        kdtEntrys_amount_TextField.setName("kdtEntrys_amount_TextField");
        kdtEntrys_amount_TextField.setVisible(true);
        kdtEntrys_amount_TextField.setEditable(true);
        kdtEntrys_amount_TextField.setHorizontalAlignment(2);
        kdtEntrys_amount_TextField.setDataType(1);
        	kdtEntrys_amount_TextField.setMinimumValue(new java.math.BigDecimal("-999.9999999999"));
        	kdtEntrys_amount_TextField.setMaximumValue(new java.math.BigDecimal("999.9999999999"));
        kdtEntrys_amount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_amount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_amount_TextField);
        this.kdtEntrys.getColumn("amount").setEditor(kdtEntrys_amount_CellEditor);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setBoundLabelAlignment(7);
        // contCreatetime		
        this.contCreatetime.setBoundLabelText(resHelper.getString("contCreatetime.boundLabelText"));		
        this.contCreatetime.setBoundLabelLength(100);		
        this.contCreatetime.setBoundLabelUnderline(true);		
        this.contCreatetime.setBoundLabelAlignment(7);
        // txtContractNumber		
        this.txtContractNumber.setMaxLength(80);		
        this.txtContractNumber.setVisible(true);		
        this.txtContractNumber.setEnabled(false);		
        this.txtContractNumber.setHorizontalAlignment(2);		
        this.txtContractNumber.setRequired(false);
        // txtAmount		
        this.txtAmount.setVisible(true);		
        this.txtAmount.setHorizontalAlignment(2);		
        this.txtAmount.setDataType(1);		
        this.txtAmount.setSupportedEmpty(true);		
        this.txtAmount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtAmount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtAmount.setPrecision(2);		
        this.txtAmount.setRequired(true);		
        this.txtAmount.setEnabled(false);
        // txtUnSplitAmount		
        this.txtUnSplitAmount.setVisible(true);		
        this.txtUnSplitAmount.setHorizontalAlignment(2);		
        this.txtUnSplitAmount.setDataType(1);		
        this.txtUnSplitAmount.setSupportedEmpty(true);		
        this.txtUnSplitAmount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtUnSplitAmount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtUnSplitAmount.setPrecision(2);		
        this.txtUnSplitAmount.setRequired(true);		
        this.txtUnSplitAmount.setEnabled(false);
        // txtSplitedAmount		
        this.txtSplitedAmount.setVisible(true);		
        this.txtSplitedAmount.setHorizontalAlignment(2);		
        this.txtSplitedAmount.setDataType(1);		
        this.txtSplitedAmount.setSupportedEmpty(true);		
        this.txtSplitedAmount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
        this.txtSplitedAmount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
        this.txtSplitedAmount.setPrecision(2);		
        this.txtSplitedAmount.setRequired(true);		
        this.txtSplitedAmount.setEnabled(false);
        // txtContractName		
        this.txtContractName.setVisible(true);		
        this.txtContractName.setHorizontalAlignment(2);		
        this.txtContractName.setMaxLength(100);		
        this.txtContractName.setRequired(false);		
        this.txtContractName.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setVisible(true);		
        this.prmtCreator.setEditable(true);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);		
        this.pkCreateTime.setVisible(true);
        // btnPCSelect
        this.btnPCSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionPCSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPCSelect.setText(resHelper.getString("btnPCSelect.text"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 600));
        contContractNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contContractNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmount.setBounds(new Rectangle(10, 32, 270, 19));
        this.add(contAmount, new KDLayout.Constraints(10, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contUnSplitAmount.setBounds(new Rectangle(729, 32, 270, 19));
        this.add(contUnSplitAmount, new KDLayout.Constraints(729, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSplitedAmount.setBounds(new Rectangle(371, 32, 270, 19));
        this.add(contSplitedAmount, new KDLayout.Constraints(371, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractName.setBounds(new Rectangle(371, 10, 628, 19));
        this.add(contContractName, new KDLayout.Constraints(371, 10, 628, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntrys.setBounds(new Rectangle(10, 59, 989, 482));
        this.add(kdtEntrys, new KDLayout.Constraints(10, 59, 989, 482, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(10, 549, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 549, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreatetime.setBounds(new Rectangle(371, 549, 270, 19));
        this.add(contCreatetime, new KDLayout.Constraints(371, 549, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contContractNumber
        contContractNumber.setBoundEditor(txtContractNumber);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contUnSplitAmount
        contUnSplitAmount.setBoundEditor(txtUnSplitAmount);
        //contSplitedAmount
        contSplitedAmount.setBoundEditor(txtSplitedAmount);
        //contContractName
        contContractName.setBoundEditor(txtContractName);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreatetime
        contCreatetime.setBoundEditor(pkCreateTime);

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
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        menuEdit.add(menuItemEnterToNextRow);
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
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnPCSelect);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("entry.programmingContract.programming.project.name", String.class, this.kdtEntrys, "curProject.text");
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entry.programmingContract.longNumber", String.class, this.kdtEntrys, "pcNumber.text");
		dataBinder.registerBinding("entry.programmingContract.name", String.class, this.kdtEntrys, "pcName.text");
		dataBinder.registerBinding("entry.scale", java.math.BigDecimal.class, this.kdtEntrys, "scale.text");
		dataBinder.registerBinding("entry.amount", java.math.BigDecimal.class, this.kdtEntrys, "amount.text");
		dataBinder.registerBinding("entry.programmingContract.amount", java.math.BigDecimal.class, this.kdtEntrys, "pcAmount.text");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionUnAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAudit, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.contractsplit.app.ContractPCSplitBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillInfo)ov;
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("CostCenter");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
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
		getValidateHelper().registerBindProperty("entry.programmingContract.programming.project.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.programmingContract.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.programmingContract.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.scale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.programmingContract.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    		
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
		            this.actionUnAudit.setVisible(false);
		            this.actionUnAudit.setEnabled(false);
		            this.actionAudit.setVisible(false);
		            this.actionAudit.setEnabled(false);
        }
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
    	sic.add(new SelectorItemInfo("entry.programmingContract.programming.project.name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entry.programmingContract.longNumber"));
    	sic.add(new SelectorItemInfo("entry.programmingContract.name"));
    	sic.add(new SelectorItemInfo("entry.scale"));
    	sic.add(new SelectorItemInfo("entry.amount"));
    	sic.add(new SelectorItemInfo("entry.programmingContract.amount"));
        sic.add(new SelectorItemInfo("amount"));
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
        return sic;
    }        
    	

    /**
     * output actionPCSelect_actionPerformed method
     */
    public void actionPCSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionPCSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPCSelect() {
    	return false;
    }

    /**
     * output ActionPCSelect class
     */     
    protected class ActionPCSelect extends ItemAction {     
    
        public ActionPCSelect()
        {
            this(null);
        }

        public ActionPCSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPCSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPCSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPCSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractPCSplitBillEditUI.this, "ActionPCSelect", "actionPCSelect_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.contractsplit.client", "ContractPCSplitBillEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.contract.contractsplit.client.ContractPCSplitBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillInfo objectValue = new com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/contract/contractsplit/ContractPCSplitBill";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.contractsplit.app.ContractPCSplitBillQuery");
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
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}