/**
 * output package name
 */
package com.kingdee.eas.port.pm.project.client;

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
public abstract class AbstractPortProjectEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPortProjectEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprojectType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contindex;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contestimatesAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbudgetAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprojectStatus;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprojectType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtindex;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continviteDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbeginDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfinishDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contendDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkstartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkinviteDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbeginDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkfinishDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkendDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtestimatesAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtbudgetAmount;
    protected com.kingdee.eas.port.pm.project.PortProjectInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractPortProjectEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPortProjectEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprojectType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contindex = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contestimatesAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbudgetAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.prmtprojectStatus = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtprojectType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtindex = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contstartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continviteDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbeginDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfinishDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contendDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkstartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkinviteDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkbeginDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkfinishDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkendDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtestimatesAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtbudgetAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.contprojectStatus.setName("contprojectStatus");
        this.contprojectType.setName("contprojectType");
        this.contindex.setName("contindex");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contestimatesAmount.setName("contestimatesAmount");
        this.contbudgetAmount.setName("contbudgetAmount");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.txtSimpleName.setName("txtSimpleName");
        this.txtDescription.setName("txtDescription");
        this.prmtprojectStatus.setName("prmtprojectStatus");
        this.prmtprojectType.setName("prmtprojectType");
        this.txtindex.setName("txtindex");
        this.kDPanel1.setName("kDPanel1");
        this.contstartDate.setName("contstartDate");
        this.continviteDate.setName("continviteDate");
        this.contbeginDate.setName("contbeginDate");
        this.contfinishDate.setName("contfinishDate");
        this.contendDate.setName("contendDate");
        this.pkstartDate.setName("pkstartDate");
        this.pkinviteDate.setName("pkinviteDate");
        this.pkbeginDate.setName("pkbeginDate");
        this.pkfinishDate.setName("pkfinishDate");
        this.pkendDate.setName("pkendDate");
        this.txtestimatesAmount.setName("txtestimatesAmount");
        this.txtbudgetAmount.setName("txtbudgetAmount");
        // CoreUI		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
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
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);		
        this.kDLabelContainer4.setVisible(true);
        // contprojectStatus		
        this.contprojectStatus.setBoundLabelText(resHelper.getString("contprojectStatus.boundLabelText"));		
        this.contprojectStatus.setBoundLabelLength(100);		
        this.contprojectStatus.setBoundLabelUnderline(true);		
        this.contprojectStatus.setVisible(true);
        // contprojectType		
        this.contprojectType.setBoundLabelText(resHelper.getString("contprojectType.boundLabelText"));		
        this.contprojectType.setBoundLabelLength(100);		
        this.contprojectType.setBoundLabelUnderline(true);		
        this.contprojectType.setVisible(true);
        // contindex		
        this.contindex.setBoundLabelText(resHelper.getString("contindex.boundLabelText"));		
        this.contindex.setBoundLabelLength(100);		
        this.contindex.setBoundLabelUnderline(true);		
        this.contindex.setVisible(true);
        // kDTabbedPane1
        // contestimatesAmount		
        this.contestimatesAmount.setBoundLabelText(resHelper.getString("contestimatesAmount.boundLabelText"));		
        this.contestimatesAmount.setBoundLabelLength(100);		
        this.contestimatesAmount.setBoundLabelUnderline(true);		
        this.contestimatesAmount.setVisible(true);
        // contbudgetAmount		
        this.contbudgetAmount.setBoundLabelText(resHelper.getString("contbudgetAmount.boundLabelText"));		
        this.contbudgetAmount.setBoundLabelLength(100);		
        this.contbudgetAmount.setBoundLabelUnderline(true);		
        this.contbudgetAmount.setVisible(true);
        // txtName
        // txtNumber
        // txtSimpleName
        // txtDescription
        // prmtprojectStatus		
        this.prmtprojectStatus.setQueryInfo("com.kingdee.eas.port.pm.base.app.ProjectStatusQuery");		
        this.prmtprojectStatus.setVisible(true);		
        this.prmtprojectStatus.setEditable(true);		
        this.prmtprojectStatus.setDisplayFormat("$name$");		
        this.prmtprojectStatus.setEditFormat("$number$");		
        this.prmtprojectStatus.setCommitFormat("$number$");		
        this.prmtprojectStatus.setRequired(false);
        // prmtprojectType		
        this.prmtprojectType.setQueryInfo("com.kingdee.eas.port.pm.base.app.ProjectTypeQuery");		
        this.prmtprojectType.setVisible(true);		
        this.prmtprojectType.setEditable(true);		
        this.prmtprojectType.setDisplayFormat("$name$");		
        this.prmtprojectType.setEditFormat("$number$");		
        this.prmtprojectType.setCommitFormat("$number$");		
        this.prmtprojectType.setRequired(false);
        // txtindex		
        this.txtindex.setVisible(true);		
        this.txtindex.setHorizontalAlignment(2);		
        this.txtindex.setDataType(0);		
        this.txtindex.setSupportedEmpty(true);		
        this.txtindex.setRequired(false);
        // kDPanel1
        // contstartDate		
        this.contstartDate.setBoundLabelText(resHelper.getString("contstartDate.boundLabelText"));		
        this.contstartDate.setBoundLabelLength(100);		
        this.contstartDate.setBoundLabelUnderline(true);		
        this.contstartDate.setVisible(true);
        // continviteDate		
        this.continviteDate.setBoundLabelText(resHelper.getString("continviteDate.boundLabelText"));		
        this.continviteDate.setBoundLabelLength(100);		
        this.continviteDate.setBoundLabelUnderline(true);		
        this.continviteDate.setVisible(true);
        // contbeginDate		
        this.contbeginDate.setBoundLabelText(resHelper.getString("contbeginDate.boundLabelText"));		
        this.contbeginDate.setBoundLabelLength(100);		
        this.contbeginDate.setBoundLabelUnderline(true);		
        this.contbeginDate.setVisible(true);
        // contfinishDate		
        this.contfinishDate.setBoundLabelText(resHelper.getString("contfinishDate.boundLabelText"));		
        this.contfinishDate.setBoundLabelLength(100);		
        this.contfinishDate.setBoundLabelUnderline(true);		
        this.contfinishDate.setVisible(true);
        // contendDate		
        this.contendDate.setBoundLabelText(resHelper.getString("contendDate.boundLabelText"));		
        this.contendDate.setBoundLabelLength(100);		
        this.contendDate.setBoundLabelUnderline(true);		
        this.contendDate.setVisible(true);
        // pkstartDate		
        this.pkstartDate.setVisible(true);		
        this.pkstartDate.setRequired(false);
        // pkinviteDate		
        this.pkinviteDate.setVisible(true);		
        this.pkinviteDate.setRequired(false);
        // pkbeginDate		
        this.pkbeginDate.setVisible(true);		
        this.pkbeginDate.setRequired(false);
        // pkfinishDate		
        this.pkfinishDate.setVisible(true);		
        this.pkfinishDate.setRequired(false);
        // pkendDate		
        this.pkendDate.setVisible(true);		
        this.pkendDate.setRequired(false);
        // txtestimatesAmount		
        this.txtestimatesAmount.setVisible(true);		
        this.txtestimatesAmount.setHorizontalAlignment(2);		
        this.txtestimatesAmount.setDataType(1);		
        this.txtestimatesAmount.setSupportedEmpty(true);		
        this.txtestimatesAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtestimatesAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtestimatesAmount.setPrecision(2);		
        this.txtestimatesAmount.setRequired(false);
        // txtbudgetAmount		
        this.txtbudgetAmount.setVisible(true);		
        this.txtbudgetAmount.setHorizontalAlignment(2);		
        this.txtbudgetAmount.setDataType(1);		
        this.txtbudgetAmount.setSupportedEmpty(true);		
        this.txtbudgetAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtbudgetAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtbudgetAmount.setPrecision(2);		
        this.txtbudgetAmount.setRequired(false);
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
        kDLabelContainer1.setBounds(new Rectangle(52, 55, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(52, 27, 270, 19));
        this.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(386, 55, 270, 19));
        this.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(50, 347, 733, 19));
        this.add(kDLabelContainer4, null);
        contprojectStatus.setBounds(new Rectangle(386, 27, 270, 19));
        this.add(contprojectStatus, null);
        contprojectType.setBounds(new Rectangle(385, 83, 270, 19));
        this.add(contprojectType, null);
        contindex.setBounds(new Rectangle(386, 110, 270, 19));
        this.add(contindex, null);
        kDTabbedPane1.setBounds(new Rectangle(50, 136, 732, 175));
        this.add(kDTabbedPane1, null);
        contestimatesAmount.setBounds(new Rectangle(52, 83, 270, 19));
        this.add(contestimatesAmount, null);
        contbudgetAmount.setBounds(new Rectangle(52, 110, 270, 19));
        this.add(contbudgetAmount, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtName);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtSimpleName);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtDescription);
        //contprojectStatus
        contprojectStatus.setBoundEditor(prmtprojectStatus);
        //contprojectType
        contprojectType.setBoundEditor(prmtprojectType);
        //contindex
        contindex.setBoundEditor(txtindex);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        //kDPanel1
        kDPanel1.setLayout(null);        contstartDate.setBounds(new Rectangle(5, 6, 270, 19));
        kDPanel1.add(contstartDate, null);
        continviteDate.setBounds(new Rectangle(5, 34, 270, 19));
        kDPanel1.add(continviteDate, null);
        contbeginDate.setBounds(new Rectangle(6, 63, 270, 19));
        kDPanel1.add(contbeginDate, null);
        contfinishDate.setBounds(new Rectangle(6, 92, 270, 19));
        kDPanel1.add(contfinishDate, null);
        contendDate.setBounds(new Rectangle(6, 121, 270, 19));
        kDPanel1.add(contendDate, null);
        //contstartDate
        contstartDate.setBoundEditor(pkstartDate);
        //continviteDate
        continviteDate.setBoundEditor(pkinviteDate);
        //contbeginDate
        contbeginDate.setBoundEditor(pkbeginDate);
        //contfinishDate
        contfinishDate.setBoundEditor(pkfinishDate);
        //contendDate
        contendDate.setBoundEditor(pkendDate);
        //contestimatesAmount
        contestimatesAmount.setBoundEditor(txtestimatesAmount);
        //contbudgetAmount
        contbudgetAmount.setBoundEditor(txtbudgetAmount);

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
        this.menuBar.add(menuTool);
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
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "_multiLangItem");
		dataBinder.registerBinding("projectStatus", com.kingdee.eas.port.pm.base.ProjectStatusInfo.class, this.prmtprojectStatus, "data");
		dataBinder.registerBinding("projectType", com.kingdee.eas.port.pm.base.ProjectTypeInfo.class, this.prmtprojectType, "data");
		dataBinder.registerBinding("index", int.class, this.txtindex, "value");
		dataBinder.registerBinding("startDate", java.util.Date.class, this.pkstartDate, "value");
		dataBinder.registerBinding("inviteDate", java.util.Date.class, this.pkinviteDate, "value");
		dataBinder.registerBinding("beginDate", java.util.Date.class, this.pkbeginDate, "value");
		dataBinder.registerBinding("finishDate", java.util.Date.class, this.pkfinishDate, "value");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.pkendDate, "value");
		dataBinder.registerBinding("estimatesAmount", java.math.BigDecimal.class, this.txtestimatesAmount, "value");
		dataBinder.registerBinding("budgetAmount", java.math.BigDecimal.class, this.txtbudgetAmount, "value");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtDescription, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtSimpleName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtDescription, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtSimpleName, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtDescription, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtSimpleName, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.project.app.PortProjectEditUIHandler";
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
        this.editData = (com.kingdee.eas.port.pm.project.PortProjectInfo)ov;
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
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("projectType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("index", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("beginDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("finishDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("estimatesAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("budgetAmount", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtDescription.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtDescription.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(false);
		            this.txtDescription.setEnabled(false);
		            this.txtNumber.setEnabled(false);
		            this.txtSimpleName.setEnabled(false);
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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("projectStatus.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("projectStatus.id"));
        	sic.add(new SelectorItemInfo("projectStatus.number"));
        	sic.add(new SelectorItemInfo("projectStatus.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("projectType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("projectType.id"));
        	sic.add(new SelectorItemInfo("projectType.number"));
        	sic.add(new SelectorItemInfo("projectType.name"));
		}
        sic.add(new SelectorItemInfo("index"));
        sic.add(new SelectorItemInfo("startDate"));
        sic.add(new SelectorItemInfo("inviteDate"));
        sic.add(new SelectorItemInfo("beginDate"));
        sic.add(new SelectorItemInfo("finishDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("estimatesAmount"));
        sic.add(new SelectorItemInfo("budgetAmount"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.pm.project.client", "PortProjectEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.port.pm.project.client.PortProjectEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.project.PortProjectFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.project.PortProjectInfo objectValue = new com.kingdee.eas.port.pm.project.PortProjectInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {        
        return null;
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