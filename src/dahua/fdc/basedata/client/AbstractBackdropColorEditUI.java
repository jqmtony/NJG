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
public abstract class AbstractBackdropColorEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBackdropColorEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSimpleName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contColorID;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtColorID;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contColor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtColor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrder;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrder;
    protected com.kingdee.eas.fdc.basedata.BackdropColorInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractBackdropColorEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBackdropColorEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contSimpleName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contColorID = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtColorID = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contColor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtColor = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contOrder = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtOrder = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCU.setName("contCU");
        this.prmtCU.setName("prmtCU");
        this.contName.setName("contName");
        this.txtName.setName("txtName");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contDescription.setName("contDescription");
        this.txtDescription.setName("txtDescription");
        this.contSimpleName.setName("contSimpleName");
        this.txtSimpleName.setName("txtSimpleName");
        this.contColorID.setName("contColorID");
        this.txtColorID.setName("txtColorID");
        this.contColor.setName("contColor");
        this.txtColor.setName("txtColor");
        this.contOrder.setName("contOrder");
        this.txtOrder.setName("txtOrder");
        // CoreUI
        // contCU		
        this.contCU.setBoundLabelText(resHelper.getString("contCU.boundLabelText"));		
        this.contCU.setBoundLabelLength(100);		
        this.contCU.setBoundLabelUnderline(true);
        // prmtCU
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // txtName
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // txtDescription
        // contSimpleName		
        this.contSimpleName.setBoundLabelText(resHelper.getString("contSimpleName.boundLabelText"));		
        this.contSimpleName.setBoundLabelLength(100);		
        this.contSimpleName.setBoundLabelUnderline(true);
        // txtSimpleName
        // contColorID		
        this.contColorID.setBoundLabelText(resHelper.getString("contColorID.boundLabelText"));		
        this.contColorID.setBoundLabelLength(100);		
        this.contColorID.setBoundLabelUnderline(true);
        // txtColorID		
        this.txtColorID.setMaxLength(44);
        // contColor		
        this.contColor.setBoundLabelText(resHelper.getString("contColor.boundLabelText"));		
        this.contColor.setBoundLabelLength(100);		
        this.contColor.setBoundLabelUnderline(true);
        // txtColor		
        this.txtColor.setMaxLength(20);
        // contOrder		
        this.contOrder.setBoundLabelText(resHelper.getString("contOrder.boundLabelText"));		
        this.contOrder.setBoundLabelLength(100);		
        this.contOrder.setBoundLabelUnderline(true);
        // txtOrder		
        this.txtOrder.setMaxLength(5);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(null);
        contCU.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contCU, null);
        contName.setBounds(new Rectangle(300, 10, 270, 19));
        this.add(contName, null);
        contNumber.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contNumber, null);
        contDescription.setBounds(new Rectangle(300, 40, 270, 19));
        this.add(contDescription, null);
        contSimpleName.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(contSimpleName, null);
        contColorID.setBounds(new Rectangle(300, 70, 270, 19));
        this.add(contColorID, null);
        contColor.setBounds(new Rectangle(10, 100, 270, 19));
        this.add(contColor, null);
        contOrder.setBounds(new Rectangle(300, 100, 270, 19));
        this.add(contOrder, null);
        //contCU
        contCU.setBoundEditor(prmtCU);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contSimpleName
        contSimpleName.setBoundEditor(txtSimpleName);
        //contColorID
        contColorID.setBoundEditor(txtColorID);
        //contColor
        contColor.setBoundEditor(txtColor);
        //contOrder
        contOrder.setBoundEditor(txtOrder);

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
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("colorID", com.kingdee.bos.util.BOSUuid.class, this.txtColorID, "text");
		dataBinder.registerBinding("color", String.class, this.txtColor, "text");
		dataBinder.registerBinding("order", String.class, this.txtOrder, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.BackdropColorEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.basedata.BackdropColorInfo)ov;
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
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("colorID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("color", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("order", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("CU.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("colorID"));
        sic.add(new SelectorItemInfo("color"));
        sic.add(new SelectorItemInfo("order"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "BackdropColorEditUI");
    }




}