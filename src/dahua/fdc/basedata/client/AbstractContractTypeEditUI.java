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
public abstract class AbstractContractTypeEditUI extends com.kingdee.eas.framework.client.TreeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractTypeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLongNumber;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsEnabled;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea bizDescription;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStampTaxRate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsRefProgram;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDIsWorkLoadConfirm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox bizName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLongNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizDutyOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField kdftxtPayScale;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStampTaxRate;
    protected com.kingdee.bos.ctrl.swing.KDTextField helperNumber;
    protected com.kingdee.eas.fdc.basedata.ContractTypeInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractContractTypeEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractTypeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionFirst
        String _tempStr = null;
        actionFirst.setEnabled(true);
        actionFirst.setDaemonRun(false);

        actionFirst.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl <"));
        _tempStr = resHelper.getString("ActionFirst.SHORT_DESCRIPTION");
        actionFirst.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionFirst.LONG_DESCRIPTION");
        actionFirst.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionFirst.NAME");
        actionFirst.putValue(ItemAction.NAME, _tempStr);
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLongNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsEnabled = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.bizDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.chkIsCost = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStampTaxRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsRefProgram = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDIsWorkLoadConfirm = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtLongNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.bizDutyOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdftxtPayScale = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStampTaxRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.helperNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contName.setName("contName");
        this.contLongNumber.setName("contLongNumber");
        this.chkIsEnabled.setName("chkIsEnabled");
        this.kDLabel1.setName("kDLabel1");
        this.bizDescription.setName("bizDescription");
        this.chkIsCost.setName("chkIsCost");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contStampTaxRate.setName("contStampTaxRate");
        this.chkIsRefProgram.setName("chkIsRefProgram");
        this.kDIsWorkLoadConfirm.setName("kDIsWorkLoadConfirm");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.bizName.setName("bizName");
        this.txtLongNumber.setName("txtLongNumber");
        this.bizDutyOrgUnit.setName("bizDutyOrgUnit");
        this.kdftxtPayScale.setName("kdftxtPayScale");
        this.txtStampTaxRate.setName("txtStampTaxRate");
        this.helperNumber.setName("helperNumber");
        // CoreUI		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.menuBiz.setVisible(false);		
        this.separatorFW1.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setVisible(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(80);		
        this.contName.setBoundLabelUnderline(true);
        // contLongNumber		
        this.contLongNumber.setBoundLabelText(resHelper.getString("contLongNumber.boundLabelText"));		
        this.contLongNumber.setBoundLabelLength(80);		
        this.contLongNumber.setBoundLabelUnderline(true);
        // chkIsEnabled		
        this.chkIsEnabled.setText(resHelper.getString("chkIsEnabled.text"));		
        this.chkIsEnabled.setVisible(false);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // bizDescription		
        this.bizDescription.setMaxLength(200);
        // chkIsCost		
        this.chkIsCost.setText(resHelper.getString("chkIsCost.text"));		
        this.chkIsCost.setToolTipText(resHelper.getString("chkIsCost.toolTipText"));
        this.chkIsCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsCost_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(80);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(80);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // contStampTaxRate		
        this.contStampTaxRate.setBoundLabelText(resHelper.getString("contStampTaxRate.boundLabelText"));		
        this.contStampTaxRate.setBoundLabelLength(80);		
        this.contStampTaxRate.setBoundLabelUnderline(true);
        // chkIsRefProgram		
        this.chkIsRefProgram.setText(resHelper.getString("chkIsRefProgram.text"));
        // kDIsWorkLoadConfirm		
        this.kDIsWorkLoadConfirm.setText(resHelper.getString("kDIsWorkLoadConfirm.text"));
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(80);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // bizName		
        this.bizName.setRequired(true);		
        this.bizName.setMaxLength(80);
        // txtLongNumber		
        this.txtLongNumber.setRequired(true);		
        this.txtLongNumber.setMaxLength(80);
        // bizDutyOrgUnit		
        this.bizDutyOrgUnit.setDisplayFormat("$name$");		
        this.bizDutyOrgUnit.setEditFormat("$number$");		
        this.bizDutyOrgUnit.setCommitFormat("$number$");		
        this.bizDutyOrgUnit.setEditable(true);
        this.bizDutyOrgUnit.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    bizPromptCompany_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdftxtPayScale		
        this.kdftxtPayScale.setDataType(1);		
        this.kdftxtPayScale.setPrecision(10);		
        this.kdftxtPayScale.setMultiplier(3);
        // txtStampTaxRate		
        this.txtStampTaxRate.setDataType(1);		
        this.txtStampTaxRate.setPrecision(2);
        // helperNumber		
        this.helperNumber.setMaxLength(80);
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
        this.setBounds(new Rectangle(10, 10, 300, 320));
        this.setLayout(null);
        contName.setBounds(new Rectangle(10, 35, 280, 19));
        this.add(contName, null);
        contLongNumber.setBounds(new Rectangle(10, 10, 280, 19));
        this.add(contLongNumber, null);
        chkIsEnabled.setBounds(new Rectangle(148, 288, 140, 19));
        this.add(chkIsEnabled, null);
        kDLabel1.setBounds(new Rectangle(8, 180, 61, 19));
        this.add(kDLabel1, null);
        bizDescription.setBounds(new Rectangle(8, 231, 284, 80));
        this.add(bizDescription, null);
        chkIsCost.setBounds(new Rectangle(88, 177, 110, 19));
        this.add(chkIsCost, null);
        kDLabelContainer1.setBounds(new Rectangle(10, 61, 280, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 87, 280, 19));
        this.add(kDLabelContainer2, null);
        contStampTaxRate.setBounds(new Rectangle(10, 114, 280, 19));
        this.add(contStampTaxRate, null);
        chkIsRefProgram.setBounds(new Rectangle(195, 178, 107, 19));
        this.add(chkIsRefProgram, null);
        kDIsWorkLoadConfirm.setBounds(new Rectangle(88, 203, 102, 19));
        this.add(kDIsWorkLoadConfirm, null);
        kDLabelContainer3.setBounds(new Rectangle(10, 142, 280, 19));
        this.add(kDLabelContainer3, null);
        //contName
        contName.setBoundEditor(bizName);
        //contLongNumber
        contLongNumber.setBoundEditor(txtLongNumber);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(bizDutyOrgUnit);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(kdftxtPayScale);
        //contStampTaxRate
        contStampTaxRate.setBoundEditor(txtStampTaxRate);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(helperNumber);

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
        this.menuBar.add(menuTool);
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
		dataBinder.registerBinding("isEnabled", boolean.class, this.chkIsEnabled, "selected");
		dataBinder.registerBinding("description", String.class, this.bizDescription, "_multiLangItem");
		dataBinder.registerBinding("isCost", boolean.class, this.chkIsCost, "selected");
		dataBinder.registerBinding("isRefProgram", boolean.class, this.chkIsRefProgram, "selected");
		dataBinder.registerBinding("isWorkLoadConfirm", boolean.class, this.kDIsWorkLoadConfirm, "selected");
		dataBinder.registerBinding("name", String.class, this.bizName, "_multiLangItem");
		dataBinder.registerBinding("longNumber", String.class, this.txtLongNumber, "text");
		dataBinder.registerBinding("dutyOrgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.bizDutyOrgUnit, "data");
		dataBinder.registerBinding("payScale", java.math.BigDecimal.class, this.kdftxtPayScale, "value");
		dataBinder.registerBinding("stampTaxRate", java.math.BigDecimal.class, this.txtStampTaxRate, "value");
		dataBinder.registerBinding("helperNumber", String.class, this.helperNumber, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.ContractTypeEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.basedata.ContractTypeInfo)ov;
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
		getValidateHelper().registerBindProperty("isEnabled", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isRefProgram", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isWorkLoadConfirm", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dutyOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payScale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("stampTaxRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("helperNumber", ValidateHelper.ON_SAVE);    		
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
     * output chkIsCost_actionPerformed method
     */
    protected void chkIsCost_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output bizPromptCompany_dataChanged method
     */
    protected void bizPromptCompany_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isCost"));
        sic.add(new SelectorItemInfo("isRefProgram"));
        sic.add(new SelectorItemInfo("isWorkLoadConfirm"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("longNumber"));
        sic.add(new SelectorItemInfo("dutyOrgUnit.*"));
        sic.add(new SelectorItemInfo("payScale"));
        sic.add(new SelectorItemInfo("stampTaxRate"));
        sic.add(new SelectorItemInfo("helperNumber"));
        return sic;
    }        
    	

    /**
     * output actionFirst_actionPerformed method
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }
	public RequestContext prepareActionFirst(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionFirst(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFirst() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "ContractTypeEditUI");
    }




}