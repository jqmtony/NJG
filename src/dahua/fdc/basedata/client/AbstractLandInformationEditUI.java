/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

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
public abstract class AbstractLandInformationEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractLandInformationEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCity;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contObtainDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contObtainType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalLandPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlotRatio;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnitPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker createDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCity;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCompany;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkObtainDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboObtainType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalLandPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPlotRatio;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtUnitPrice;
    protected com.kingdee.eas.fdc.basedata.LandInfomationInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractLandInformationEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractLandInformationEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionCancel
        String _tempStr = null;
        actionCancel.setEnabled(false);
        actionCancel.setDaemonRun(false);

        actionCancel.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl -"));
        _tempStr = resHelper.getString("ActionCancel.SHORT_DESCRIPTION");
        actionCancel.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancel.LONG_DESCRIPTION");
        actionCancel.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancel.NAME");
        actionCancel.putValue(ItemAction.NAME, _tempStr);
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionCancelCancel
        actionCancelCancel.setEnabled(false);
        actionCancelCancel.setDaemonRun(false);

        actionCancelCancel.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl +"));
        _tempStr = resHelper.getString("ActionCancelCancel.SHORT_DESCRIPTION");
        actionCancelCancel.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancelCancel.LONG_DESCRIPTION");
        actionCancelCancel.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancelCancel.NAME");
        actionCancelCancel.putValue(ItemAction.NAME, _tempStr);
         this.actionCancelCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionCancelCancel.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionCancelCancel.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contObtainDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contObtainType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalLandPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlotRatio = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUnitPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labelDescription = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.createDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCity = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkObtainDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboObtainType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtTotalLandPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPlotRatio = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtUnitPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contCity.setName("contCity");
        this.contCompany.setName("contCompany");
        this.contObtainDate.setName("contObtainDate");
        this.contObtainType.setName("contObtainType");
        this.contTotalLandPrice.setName("contTotalLandPrice");
        this.contPlotRatio.setName("contPlotRatio");
        this.contUnitPrice.setName("contUnitPrice");
        this.labelDescription.setName("labelDescription");
        this.txtDescription.setName("txtDescription");
        this.prmtCreator.setName("prmtCreator");
        this.createDate.setName("createDate");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.prmtCity.setName("prmtCity");
        this.prmtCompany.setName("prmtCompany");
        this.pkObtainDate.setName("pkObtainDate");
        this.comboObtainType.setName("comboObtainType");
        this.txtTotalLandPrice.setName("txtTotalLandPrice");
        this.txtPlotRatio.setName("txtPlotRatio");
        this.txtUnitPrice.setName("txtUnitPrice");
        // CoreUI		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancelCancel.setEnabled(false);		
        this.btnCancel.setVisible(false);		
        this.btnCancel.setEnabled(false);		
        this.btnPrint.setEnabled(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setEnabled(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemSave.setText(resHelper.getString("menuItemSave.text"));		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemCancelCancel.setEnabled(false);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setEnabled(false);		
        this.menuItemCancel.setVisible(false);
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
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contCity		
        this.contCity.setBoundLabelText(resHelper.getString("contCity.boundLabelText"));		
        this.contCity.setBoundLabelLength(100);		
        this.contCity.setBoundLabelUnderline(true);
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);		
        this.contCompany.setEnabled(false);
        // contObtainDate		
        this.contObtainDate.setBoundLabelText(resHelper.getString("contObtainDate.boundLabelText"));		
        this.contObtainDate.setBoundLabelLength(100);		
        this.contObtainDate.setBoundLabelUnderline(true);
        // contObtainType		
        this.contObtainType.setBoundLabelText(resHelper.getString("contObtainType.boundLabelText"));		
        this.contObtainType.setBoundLabelLength(100);		
        this.contObtainType.setBoundLabelUnderline(true);
        // contTotalLandPrice		
        this.contTotalLandPrice.setBoundLabelText(resHelper.getString("contTotalLandPrice.boundLabelText"));		
        this.contTotalLandPrice.setBoundLabelLength(100);		
        this.contTotalLandPrice.setBoundLabelUnderline(true);
        // contPlotRatio		
        this.contPlotRatio.setBoundLabelText(resHelper.getString("contPlotRatio.boundLabelText"));		
        this.contPlotRatio.setBoundLabelLength(100);		
        this.contPlotRatio.setBoundLabelUnderline(true);
        // contUnitPrice		
        this.contUnitPrice.setBoundLabelText(resHelper.getString("contUnitPrice.boundLabelText"));		
        this.contUnitPrice.setBoundLabelLength(140);		
        this.contUnitPrice.setBoundLabelUnderline(true);
        // labelDescription		
        this.labelDescription.setText(resHelper.getString("labelDescription.text"));
        // txtDescription
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // createDate		
        this.createDate.setEnabled(false);
        // txtNumber
        // txtName		
        this.txtName.setMaxLength(50);
        // prmtCity		
        this.prmtCity.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CityQuery");
        // prmtCompany		
        this.prmtCompany.setEnabled(false);
        // pkObtainDate
        // comboObtainType		
        this.comboObtainType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.ObtainTypeEnum").toArray());
        // txtTotalLandPrice		
        this.txtTotalLandPrice.setDataType(1);		
        this.txtTotalLandPrice.setPrecision(2);
        // txtPlotRatio		
        this.txtPlotRatio.setDataType(5);		
        this.txtPlotRatio.setPrecision(2);
        // txtUnitPrice		
        this.txtUnitPrice.setDataType(1);		
        this.txtUnitPrice.setPrecision(2);
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
        contCreator.setBounds(new Rectangle(10, 588, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 588, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(720, 588, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(720, 588, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(365, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(365, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCity.setBounds(new Rectangle(720, 10, 270, 19));
        this.add(contCity, new KDLayout.Constraints(720, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCompany.setBounds(new Rectangle(10, 41, 270, 19));
        this.add(contCompany, new KDLayout.Constraints(10, 41, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contObtainDate.setBounds(new Rectangle(365, 41, 270, 19));
        this.add(contObtainDate, new KDLayout.Constraints(365, 41, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contObtainType.setBounds(new Rectangle(720, 41, 270, 19));
        this.add(contObtainType, new KDLayout.Constraints(720, 41, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTotalLandPrice.setBounds(new Rectangle(365, 73, 270, 19));
        this.add(contTotalLandPrice, new KDLayout.Constraints(365, 73, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlotRatio.setBounds(new Rectangle(10, 73, 270, 19));
        this.add(contPlotRatio, new KDLayout.Constraints(10, 73, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contUnitPrice.setBounds(new Rectangle(720, 73, 270, 19));
        this.add(contUnitPrice, new KDLayout.Constraints(720, 73, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        labelDescription.setBounds(new Rectangle(10, 105, 100, 19));
        this.add(labelDescription, new KDLayout.Constraints(10, 105, 100, 19, KDLayout.Constraints.ANCHOR_LEFT));
        txtDescription.setBounds(new Rectangle(10, 133, 980, 442));
        this.add(txtDescription, new KDLayout.Constraints(10, 133, 980, 442, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(createDate);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contCity
        contCity.setBoundEditor(prmtCity);
        //contCompany
        contCompany.setBoundEditor(prmtCompany);
        //contObtainDate
        contObtainDate.setBoundEditor(pkObtainDate);
        //contObtainType
        contObtainType.setBoundEditor(comboObtainType);
        //contTotalLandPrice
        contTotalLandPrice.setBoundEditor(txtTotalLandPrice);
        //contPlotRatio
        contPlotRatio.setBoundEditor(txtPlotRatio);
        //contUnitPrice
        contUnitPrice.setBoundEditor(txtUnitPrice);

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
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.createDate, "userObject");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("city", com.kingdee.eas.basedata.assistant.CityInfo.class, this.prmtCity, "data");
		dataBinder.registerBinding("company", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtCompany, "data");
		dataBinder.registerBinding("obtainDate", java.util.Date.class, this.pkObtainDate, "value");
		dataBinder.registerBinding("obtainType", com.kingdee.eas.fdc.basedata.ObtainTypeEnum.class, this.comboObtainType, "selectedItem");
		dataBinder.registerBinding("totalLandPrice", java.math.BigDecimal.class, this.txtTotalLandPrice, "value");
		dataBinder.registerBinding("plotRatio", double.class, this.txtPlotRatio, "value");
		dataBinder.registerBinding("unitPrice", java.math.BigDecimal.class, this.txtUnitPrice, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.LandInformationEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.basedata.LandInfomationInfo)ov;
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
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("city", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("obtainDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("obtainType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalLandPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("plotRatio", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unitPrice", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("description"));
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("city.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("city.id"));
        	sic.add(new SelectorItemInfo("city.number"));
        	sic.add(new SelectorItemInfo("city.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("company.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("company.id"));
        	sic.add(new SelectorItemInfo("company.number"));
        	sic.add(new SelectorItemInfo("company.name"));
		}
        sic.add(new SelectorItemInfo("obtainDate"));
        sic.add(new SelectorItemInfo("obtainType"));
        sic.add(new SelectorItemInfo("totalLandPrice"));
        sic.add(new SelectorItemInfo("plotRatio"));
        sic.add(new SelectorItemInfo("unitPrice"));
        return sic;
    }        
    	

    /**
     * output actionCancel_actionPerformed method
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }
    	

    /**
     * output actionCancelCancel_actionPerformed method
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }
	public RequestContext prepareActionCancel(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCancel(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancel() {
    	return false;
    }
	public RequestContext prepareActionCancelCancel(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCancelCancel(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelCancel() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "LandInformationEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.basedata.client.LandInformationEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.basedata.LandInfomationFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.basedata.LandInfomationInfo objectValue = new com.kingdee.eas.fdc.basedata.LandInfomationInfo();
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
				vo.put("totalLandPrice",new java.math.BigDecimal(0));
		vo.put("plotRatio",new java.math.BigDecimal(0));
		vo.put("unitPrice",new java.math.BigDecimal(0));
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}