/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

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
public abstract class AbstractMarketInfoEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketInfoEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPosition;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPosition;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAttchment;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView;
    protected com.kingdee.bos.ctrl.swing.KDTree treeAttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveAttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContent;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsGroupFile;
    protected com.kingdee.eas.fdc.costdb.MarketInfoInfo editData = null;
    protected ActionUploadAttachment actionUploadAttachment = null;
    protected ActionRemoveAttachment actionRemoveAttachment = null;
    protected ActionViewContent actionViewContent = null;
    /**
     * output class constructor
     */
    public AbstractMarketInfoEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketInfoEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionUploadAttachment
        this.actionUploadAttachment = new ActionUploadAttachment(this);
        getActionManager().registerAction("actionUploadAttachment", actionUploadAttachment);
         this.actionUploadAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveAttachment
        this.actionRemoveAttachment = new ActionRemoveAttachment(this);
        getActionManager().registerAction("actionRemoveAttachment", actionRemoveAttachment);
         this.actionRemoveAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContent
        this.actionViewContent = new ActionViewContent(this);
        getActionManager().registerAction("actionViewContent", actionViewContent);
         this.actionViewContent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contPosition = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtPosition = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtOrgUnit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnAttchment = new com.kingdee.bos.ctrl.swing.KDButton();
        this.pkLastUpdateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDTreeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeAttachment = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnRemoveAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContent = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.chkIsGroupFile = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contDescription.setName("contDescription");
        this.contName.setName("contName");
        this.txtName.setName("txtName");
        this.contPerson.setName("contPerson");
        this.prmtPerson.setName("prmtPerson");
        this.contPosition.setName("contPosition");
        this.prmtPosition.setName("prmtPosition");
        this.contType.setName("contType");
        this.prmtType.setName("prmtType");
        this.contOrgUnit.setName("contOrgUnit");
        this.txtDescription.setName("txtDescription");
        this.txtOrgUnit.setName("txtOrgUnit");
        this.btnAttchment.setName("btnAttchment");
        this.pkLastUpdateDate.setName("pkLastUpdateDate");
        this.pkCreateTime.setName("pkCreateTime");
        this.kDTreeView.setName("kDTreeView");
        this.treeAttachment.setName("treeAttachment");
        this.btnRemoveAttachment.setName("btnRemoveAttachment");
        this.btnViewContent.setName("btnViewContent");
        this.chkIsGroupFile.setName("chkIsGroupFile");
        // CoreUI
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");		
        this.prmtCreator.setCommitFormat("$name$");
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // contPerson		
        this.contPerson.setBoundLabelText(resHelper.getString("contPerson.boundLabelText"));		
        this.contPerson.setBoundLabelLength(100);		
        this.contPerson.setBoundLabelUnderline(true);
        // prmtPerson		
        this.prmtPerson.setCommitFormat("$name$");		
        this.prmtPerson.setDisplayFormat("$name$");		
        this.prmtPerson.setEditFormat("$name$");		
        this.prmtPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
        // contPosition		
        this.contPosition.setBoundLabelText(resHelper.getString("contPosition.boundLabelText"));		
        this.contPosition.setBoundLabelLength(100);		
        this.contPosition.setBoundLabelUnderline(true);
        // prmtPosition		
        this.prmtPosition.setCommitFormat("$name$");		
        this.prmtPosition.setDisplayFormat("$name$");		
        this.prmtPosition.setEditFormat("$name$");		
        this.prmtPosition.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");
        // contType		
        this.contType.setBoundLabelText(resHelper.getString("contType.boundLabelText"));		
        this.contType.setBoundLabelLength(100);		
        this.contType.setBoundLabelUnderline(true);
        // prmtType		
        this.prmtType.setRequired(true);		
        this.prmtType.setDisplayFormat("$name$");		
        this.prmtType.setEditFormat("$number$");		
        this.prmtType.setCommitFormat("$number$");		
        this.prmtType.setQueryInfo("com.kingdee.eas.fdc.costdb.app.MarketInfoTypeQuery");
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);		
        this.contOrgUnit.setEnabled(false);
        // txtDescription
        // txtOrgUnit
        // btnAttchment
        this.btnAttchment.setAction((IItemAction)ActionProxyFactory.getProxy(actionUploadAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttchment.setText(resHelper.getString("btnAttchment.text"));
        // pkLastUpdateDate		
        this.pkLastUpdateDate.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // kDTreeView		
        this.kDTreeView.setShowButton(false);
        // treeAttachment
        this.treeAttachment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    treeAttach_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.treeAttachment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                try {
                    treeAttach_keyPressed(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void keyReleased(java.awt.event.KeyEvent e) {
                try {
                    treeAttach_keyReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnRemoveAttachment
        this.btnRemoveAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemoveAttachment.setText(resHelper.getString("btnRemoveAttachment.text"));
        // btnViewContent
        this.btnViewContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContent.setText(resHelper.getString("btnViewContent.text"));		
        this.btnViewContent.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_open"));		
        this.btnViewContent.setToolTipText(resHelper.getString("btnViewContent.toolTipText"));
        // chkIsGroupFile		
        this.chkIsGroupFile.setText(resHelper.getString("chkIsGroupFile.text"));
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
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        contCreator.setBounds(new Rectangle(20, 524, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(20, 524, 270, 19, 0));
        contCreateTime.setBounds(new Rectangle(20, 560, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(20, 560, 270, 19, 0));
        contLastUpdateTime.setBounds(new Rectangle(20, 106, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(20, 106, 270, 19, 0));
        contNumber.setBounds(new Rectangle(20, 52, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(20, 52, 270, 19, 0));
        contDescription.setBounds(new Rectangle(20, 133, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(20, 133, 270, 19, 0));
        contName.setBounds(new Rectangle(460, 25, 270, 19));
        this.add(contName, new KDLayout.Constraints(460, 25, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contPerson.setBounds(new Rectangle(20, 79, 270, 19));
        this.add(contPerson, new KDLayout.Constraints(20, 79, 270, 19, 0));
        contPosition.setBounds(new Rectangle(460, 79, 270, 19));
        this.add(contPosition, new KDLayout.Constraints(460, 79, 270, 19, 0));
        contType.setBounds(new Rectangle(460, 52, 270, 19));
        this.add(contType, new KDLayout.Constraints(460, 52, 270, 19, 0));
        contOrgUnit.setBounds(new Rectangle(20, 25, 270, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(20, 25, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        txtDescription.setBounds(new Rectangle(20, 160, 712, 140));
        this.add(txtDescription, new KDLayout.Constraints(20, 160, 712, 140, 0));
        btnAttchment.setBounds(new Rectangle(20, 309, 100, 21));
        this.add(btnAttchment, new KDLayout.Constraints(20, 309, 100, 21, 0));
        kDTreeView.setBounds(new Rectangle(137, 309, 170, 93));
        this.add(kDTreeView, new KDLayout.Constraints(137, 309, 170, 93, 0));
        chkIsGroupFile.setBounds(new Rectangle(460, 106, 140, 19));
        this.add(chkIsGroupFile, new KDLayout.Constraints(460, 106, 140, 19, 0));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateDate);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contPerson
        contPerson.setBoundEditor(prmtPerson);
        //contPosition
        contPosition.setBoundEditor(prmtPosition);
        //contType
        contType.setBoundEditor(prmtType);
        //contOrgUnit
        contOrgUnit.setBoundEditor(txtOrgUnit);
        //kDTreeView
        kDTreeView.setTree(treeAttachment);

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
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(btnRemoveAttachment);
        this.toolBar.add(btnViewContent);

    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("personName", String.class, this.prmtPerson, "data");
		dataBinder.registerBinding("personOrg", String.class, this.prmtPosition, "data");
		dataBinder.registerBinding("type", com.kingdee.eas.fdc.costdb.MarketInfoTypeInfo.class, this.prmtType, "data");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("org.displayName", String.class, this.txtOrgUnit, "text");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateDate, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("isGroupFile", boolean.class, this.chkIsGroupFile, "selected");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.costdb.app.MarketInfoEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.costdb.MarketInfoInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("personName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("personOrg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("type", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("org.displayName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isGroupFile", ValidateHelper.ON_SAVE);    		
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
     * output treeAttach_keyPressed method
     */
    protected void treeAttach_keyPressed(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output treeAttach_mouseClicked method
     */
    protected void treeAttach_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output treeAttach_keyReleased method
     */
    protected void treeAttach_keyReleased(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("personName.*"));
        sic.add(new SelectorItemInfo("personOrg.*"));
        sic.add(new SelectorItemInfo("type.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("org.displayName"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("isGroupFile"));
        return sic;
    }        
    	

    /**
     * output actionUploadAttachment_actionPerformed method
     */
    public void actionUploadAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveAttachment_actionPerformed method
     */
    public void actionRemoveAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContent_actionPerformed method
     */
    public void actionViewContent_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionUploadAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUploadAttachment() {
    	return false;
    }
	public RequestContext prepareActionRemoveAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveAttachment() {
    	return false;
    }
	public RequestContext prepareActionViewContent(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContent() {
    	return false;
    }

    /**
     * output ActionUploadAttachment class
     */
    protected class ActionUploadAttachment extends ItemAction
    {
        public ActionUploadAttachment()
        {
            this(null);
        }

        public ActionUploadAttachment(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUploadAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUploadAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUploadAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketInfoEditUI.this, "ActionUploadAttachment", "actionUploadAttachment_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveAttachment class
     */
    protected class ActionRemoveAttachment extends ItemAction
    {
        public ActionRemoveAttachment()
        {
            this(null);
        }

        public ActionRemoveAttachment(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRemoveAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketInfoEditUI.this, "ActionRemoveAttachment", "actionRemoveAttachment_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContent class
     */
    protected class ActionViewContent extends ItemAction
    {
        public ActionViewContent()
        {
            this(null);
        }

        public ActionViewContent(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewContent.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContent.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContent.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketInfoEditUI.this, "ActionViewContent", "actionViewContent_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.costdb.client", "MarketInfoEditUI");
    }




}