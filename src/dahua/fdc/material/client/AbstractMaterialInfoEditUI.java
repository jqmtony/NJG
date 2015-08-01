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
public abstract class AbstractMaterialInfoEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMaterialInfoEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contValidDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuoteTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkValidDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplier;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkQuoteTime;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtPrice;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractBill;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboState;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer unit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer model;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMaterial;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox KDUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox KDModel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMaterial;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox txtNumber;
    protected com.kingdee.eas.fdc.material.MaterialInfoInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractMaterialInfoEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMaterialInfoEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contValidDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuoteTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkValidDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtSupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkQuoteTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtPrice = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.prmtContractBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.unit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.model = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMaterial = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.KDUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.KDModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMaterial = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.contValidDate.setName("contValidDate");
        this.contSupplier.setName("contSupplier");
        this.contQuoteTime.setName("contQuoteTime");
        this.contPrice.setName("contPrice");
        this.contContractBill.setName("contContractBill");
        this.contProject.setName("contProject");
        this.contState.setName("contState");
        this.pkValidDate.setName("pkValidDate");
        this.prmtSupplier.setName("prmtSupplier");
        this.pkQuoteTime.setName("pkQuoteTime");
        this.txtPrice.setName("txtPrice");
        this.prmtContractBill.setName("prmtContractBill");
        this.prmtProject.setName("prmtProject");
        this.comboState.setName("comboState");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDPanel1.setName("kDPanel1");
        this.unit.setName("unit");
        this.model.setName("model");
        this.contMaterial.setName("contMaterial");
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.KDUnit.setName("KDUnit");
        this.KDModel.setName("KDModel");
        this.prmtMaterial.setName("prmtMaterial");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        // CoreUI		
        this.setMaximumSize(new Dimension(300,300));		
        this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),1));		
        this.menuTool.setVisible(false);		
        this.MenuService.setVisible(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemSubmit.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.btnAttachment.setEnabled(false);		
        this.btnAttachment.setFocusPainted(false);		
        this.btnAttachment.setFocusable(false);		
        this.btnAttachment.setContentAreaFilled(false);		
        this.btnAttachment.setBorderPainted(false);		
        this.menuSubmitOption.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.menuBiz.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuWorkflow.setVisible(false);		
        this.menuItemSendMail.setVisible(false);		
        this.btnAudit.setVisible(false);		
        this.btnUnAudit.setVisible(false);		
        this.btnCalculator.setVisible(false);
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("kDPanel2.border.title")));
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("kDPanel3.border.title")));
        // contValidDate		
        this.contValidDate.setBoundLabelText(resHelper.getString("contValidDate.boundLabelText"));		
        this.contValidDate.setBoundLabelLength(100);		
        this.contValidDate.setBoundLabelUnderline(true);
        // contSupplier		
        this.contSupplier.setBoundLabelText(resHelper.getString("contSupplier.boundLabelText"));		
        this.contSupplier.setBoundLabelLength(100);		
        this.contSupplier.setBoundLabelUnderline(true);
        // contQuoteTime		
        this.contQuoteTime.setBoundLabelText(resHelper.getString("contQuoteTime.boundLabelText"));		
        this.contQuoteTime.setBoundLabelLength(100);		
        this.contQuoteTime.setBoundLabelUnderline(true);
        // contPrice		
        this.contPrice.setBoundLabelText(resHelper.getString("contPrice.boundLabelText"));		
        this.contPrice.setBoundLabelLength(100);		
        this.contPrice.setBoundLabelUnderline(true);
        // contContractBill		
        this.contContractBill.setBoundLabelText(resHelper.getString("contContractBill.boundLabelText"));		
        this.contContractBill.setBoundLabelLength(100);		
        this.contContractBill.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);
        // pkValidDate		
        this.pkValidDate.setEditable(false);
        // prmtSupplier		
        this.prmtSupplier.setRequired(true);		
        this.prmtSupplier.setRequestFocusEnabled(false);
        // pkQuoteTime		
        this.pkQuoteTime.setRequired(true);		
        this.pkQuoteTime.setEditable(false);
        // txtPrice		
        this.txtPrice.setDataType(6);		
        this.txtPrice.setNegatived(false);
        // prmtContractBill
        // prmtProject
        // comboState		
        this.comboState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.material.MaterialStateEnum").toArray());		
        this.comboState.setSelectedIndex(1);
        // kDScrollPane1		
        this.kDScrollPane1.setAutoscrolls(true);
        // kDPanel1		
        this.kDPanel1.setBorder(null);		
        this.kDPanel1.setMaximumSize(new Dimension(567,600));		
        this.kDPanel1.setMinimumSize(new Dimension(567,600));		
        this.kDPanel1.setAutoscrolls(true);		
        this.kDPanel1.setPreferredSize(new Dimension(567,600));
        // unit		
        this.unit.setBoundLabelText(resHelper.getString("unit.boundLabelText"));		
        this.unit.setBoundLabelLength(100);		
        this.unit.setBoundLabelUnderline(true);
        // model		
        this.model.setBoundLabelText(resHelper.getString("model.boundLabelText"));		
        this.model.setBoundLabelLength(100);		
        this.model.setBoundLabelUnderline(true);
        // contMaterial		
        this.contMaterial.setBoundLabelText(resHelper.getString("contMaterial.boundLabelText"));		
        this.contMaterial.setBoundLabelLength(100);		
        this.contMaterial.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // KDUnit		
        this.KDUnit.setEnabled(false);
        // KDModel		
        this.KDModel.setEnabled(false);
        // prmtMaterial		
        this.prmtMaterial.setEnabled(false);
        // txtName		
        this.txtName.setEnabled(false);
        // txtNumber		
        this.txtNumber.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 580, 435));
        this.setLayout(null);
        kDPanel2.setBounds(new Rectangle(8, 223, 561, 201));
        this.add(kDPanel2, null);
        kDPanel3.setBounds(new Rectangle(8, 12, 561, 196));
        this.add(kDPanel3, null);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(8, 223, 561, 201));        contValidDate.setBounds(new Rectangle(18, 151, 240, 20));
        kDPanel2.add(contValidDate, new KDLayout.Constraints(18, 151, 240, 20, 0));
        contSupplier.setBounds(new Rectangle(18, 72, 240, 20));
        kDPanel2.add(contSupplier, new KDLayout.Constraints(18, 72, 240, 20, 0));
        contQuoteTime.setBounds(new Rectangle(18, 115, 240, 20));
        kDPanel2.add(contQuoteTime, new KDLayout.Constraints(18, 115, 240, 20, 0));
        contPrice.setBounds(new Rectangle(274, 72, 240, 20));
        kDPanel2.add(contPrice, new KDLayout.Constraints(274, 72, 240, 20, 0));
        contContractBill.setBounds(new Rectangle(274, 31, 240, 20));
        kDPanel2.add(contContractBill, new KDLayout.Constraints(274, 31, 240, 20, 0));
        contProject.setBounds(new Rectangle(18, 30, 240, 20));
        kDPanel2.add(contProject, new KDLayout.Constraints(18, 30, 240, 20, 0));
        contState.setBounds(new Rectangle(274, 115, 240, 20));
        kDPanel2.add(contState, new KDLayout.Constraints(274, 115, 240, 20, 0));
        //contValidDate
        contValidDate.setBoundEditor(pkValidDate);
        //contSupplier
        contSupplier.setBoundEditor(prmtSupplier);
        //contQuoteTime
        contQuoteTime.setBoundEditor(pkQuoteTime);
        //contPrice
        contPrice.setBoundEditor(txtPrice);
        //contContractBill
        contContractBill.setBoundEditor(prmtContractBill);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contState
        contState.setBoundEditor(comboState);
        //kDPanel3
        kDPanel3.setLayout(null);        kDScrollPane1.setBounds(new Rectangle(10, 17, 540, 165));
        kDPanel3.add(kDScrollPane1, null);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(kDPanel1, null);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(6, 6, 567, 195));        unit.setBounds(new Rectangle(7, 50, 240, 20));
        kDPanel1.add(unit, new KDLayout.Constraints(7, 50, 240, 20, 0));
        model.setBounds(new Rectangle(262, 51, 240, 20));
        kDPanel1.add(model, new KDLayout.Constraints(262, 51, 240, 20, 0));
        contMaterial.setBounds(new Rectangle(7, 14, 240, 20));
        kDPanel1.add(contMaterial, new KDLayout.Constraints(7, 14, 240, 20, 0));
        contName.setBounds(new Rectangle(7, 84, 240, 20));
        kDPanel1.add(contName, new KDLayout.Constraints(7, 84, 240, 20, 0));
        contNumber.setBounds(new Rectangle(262, 15, 240, 19));
        kDPanel1.add(contNumber, new KDLayout.Constraints(262, 15, 240, 19, 0));
        //unit
        unit.setBoundEditor(KDUnit);
        //model
        model.setBoundEditor(KDModel);
        //contMaterial
        contMaterial.setBoundEditor(prmtMaterial);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);

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
		dataBinder.registerBinding("validDate", java.sql.Timestamp.class, this.pkValidDate, "value");
		dataBinder.registerBinding("supplier", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtSupplier, "data");
		dataBinder.registerBinding("quoteTime", java.sql.Timestamp.class, this.pkQuoteTime, "value");
		dataBinder.registerBinding("contractBill", com.kingdee.eas.fdc.contract.ContractBillInfo.class, this.prmtContractBill, "data");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("mState", com.kingdee.eas.fdc.material.MaterialStateEnum.class, this.comboState, "selectedItem");
		dataBinder.registerBinding("material.materialGroup.displayName", String.class, this.prmtMaterial, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.material.app.MaterialInfoEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.material.MaterialInfoInfo)ov;
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
		getValidateHelper().registerBindProperty("validDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("quoteTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("material.materialGroup.displayName", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("validDate"));
        sic.add(new SelectorItemInfo("supplier.*"));
        sic.add(new SelectorItemInfo("quoteTime"));
        sic.add(new SelectorItemInfo("contractBill.*"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("mState"));
        sic.add(new SelectorItemInfo("material.materialGroup.displayName"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.material.client", "MaterialInfoEditUI");
    }




}