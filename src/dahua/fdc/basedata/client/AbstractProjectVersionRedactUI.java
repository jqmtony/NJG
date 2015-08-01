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
public abstract class AbstractProjectVersionRedactUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectVersionRedactUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtParentNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLongNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox bizName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizLandDeveloper;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCostEntries;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDSpinner txtSortNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdOnlyApplyObjCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox bizVersionName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFirst;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrev;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnNext;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLast;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersionNumber;
    protected com.kingdee.eas.fdc.basedata.HisProjectInfo hisProject = null;
    protected ActionSave actionSave = null;
    protected ActionFirst actionFirst = null;
    protected ActionNext actionNext = null;
    protected ActionLast actionLast = null;
    protected ActionPrev actionPrev = null;
    /**
     * output class constructor
     */
    public AbstractProjectVersionRedactUI() throws Exception
    {
        super();
        this.defaultObjectName = "hisProject";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractProjectVersionRedactUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFirst
        this.actionFirst = new ActionFirst(this);
        getActionManager().registerAction("actionFirst", actionFirst);
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNext
        this.actionNext = new ActionNext(this);
        getActionManager().registerAction("actionNext", actionNext);
         this.actionNext.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionLast
        this.actionLast = new ActionLast(this);
        getActionManager().registerAction("actionLast", actionLast);
         this.actionLast.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrev
        this.actionPrev = new ActionPrev(this);
        getActionManager().registerAction("actionPrev", actionPrev);
         this.actionPrev.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtParentNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtLongNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizLandDeveloper = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.tblCostEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSortNo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.kdOnlyApplyObjCost = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizVersionName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.btnFirst = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPrev = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnNext = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnLast = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtVersionNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtParentNumber.setName("txtParentNumber");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.txtNumber.setName("txtNumber");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.txtLongNumber.setName("txtLongNumber");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.bizName.setName("bizName");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.bizLandDeveloper.setName("bizLandDeveloper");
        this.kDLabel1.setName("kDLabel1");
        this.tblCostEntries.setName("tblCostEntries");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.txtSortNo.setName("txtSortNo");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.pkStartDate.setName("pkStartDate");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.txtDescription.setName("txtDescription");
        this.kdOnlyApplyObjCost.setName("kdOnlyApplyObjCost");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.bizVersionName.setName("bizVersionName");
        this.btnFirst.setName("btnFirst");
        this.btnPrev.setName("btnPrev");
        this.btnNext.setName("btnNext");
        this.btnLast.setName("btnLast");
        this.btnSave.setName("btnSave");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.txtVersionNumber.setName("txtVersionNumber");
        // CoreUI
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // txtParentNumber		
        this.txtParentNumber.setText(resHelper.getString("txtParentNumber.text"));		
        this.txtParentNumber.setEnabled(false);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setText(resHelper.getString("txtNumber.text"));		
        this.txtNumber.setEnabled(false);		
        this.txtNumber.setMaxLength(80);
        this.txtNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtNumber_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // txtLongNumber		
        this.txtLongNumber.setText(resHelper.getString("txtLongNumber.text"));		
        this.txtLongNumber.setEnabled(false);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // bizName		
        this.bizName.setRequired(true);		
        this.bizName.setEnabled(false);		
        this.bizName.setMaxLength(80);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // bizLandDeveloper		
        this.bizLandDeveloper.setQueryInfo("com.kingdee.eas.fdc.basedata.app.LandDeveloperQuery");		
        this.bizLandDeveloper.setEditable(true);		
        this.bizLandDeveloper.setCommitFormat("$number$");		
        this.bizLandDeveloper.setDisplayFormat("$number$ $name$");		
        this.bizLandDeveloper.setEditFormat("$number$");		
        this.bizLandDeveloper.setRequired(true);		
        this.bizLandDeveloper.setEnabled(false);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // tblCostEntries		
        this.tblCostEntries.setFormatXml(resHelper.getString("tblCostEntries.formatXml"));

        

        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // txtSortNo		
        this.txtSortNo.setRequired(true);		
        this.txtSortNo.setEnabled(false);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // pkStartDate		
        this.pkStartDate.setRequired(true);		
        this.pkStartDate.setEnabled(false);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // txtDescription		
        this.txtDescription.setMaxLength(200);
        // kdOnlyApplyObjCost		
        this.kdOnlyApplyObjCost.setText(resHelper.getString("kdOnlyApplyObjCost.text"));
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // bizVersionName		
        this.bizVersionName.setRequired(true);
        // btnFirst
        this.btnFirst.setAction((IItemAction)ActionProxyFactory.getProxy(actionFirst, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFirst.setText(resHelper.getString("btnFirst.text"));		
        this.btnFirst.setToolTipText(resHelper.getString("btnFirst.toolTipText"));
        // btnPrev
        this.btnPrev.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrev, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrev.setText(resHelper.getString("btnPrev.text"));		
        this.btnPrev.setToolTipText(resHelper.getString("btnPrev.toolTipText"));
        // btnNext
        this.btnNext.setAction((IItemAction)ActionProxyFactory.getProxy(actionNext, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnNext.setText(resHelper.getString("btnNext.text"));		
        this.btnNext.setToolTipText(resHelper.getString("btnNext.toolTipText"));
        // btnLast
        this.btnLast.setAction((IItemAction)ActionProxyFactory.getProxy(actionLast, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLast.setText(resHelper.getString("btnLast.text"));		
        this.btnLast.setToolTipText(resHelper.getString("btnLast.toolTipText"));
        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // txtVersionNumber		
        this.txtVersionNumber.setText(resHelper.getString("txtVersionNumber.text"));		
        this.txtVersionNumber.setEnabled(false);		
        this.txtVersionNumber.setRequired(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 586, 433));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(300, 40, 270, 19));
        this.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(10, 70, 560, 19));
        this.add(kDLabelContainer4, null);
        kDLabelContainer5.setBounds(new Rectangle(10, 100, 560, 19));
        this.add(kDLabelContainer5, null);
        kDLabel1.setBounds(new Rectangle(10, 130, 100, 19));
        this.add(kDLabel1, null);
        tblCostEntries.setBounds(new Rectangle(111, 160, 459, 148));
        this.add(tblCostEntries, null);
        kDLabelContainer6.setBounds(new Rectangle(10, 330, 270, 19));
        this.add(kDLabelContainer6, null);
        kDLabelContainer7.setBounds(new Rectangle(300, 330, 270, 19));
        this.add(kDLabelContainer7, null);
        kDLabelContainer8.setBounds(new Rectangle(300, 360, 270, 19));
        this.add(kDLabelContainer8, null);
        kdOnlyApplyObjCost.setBounds(new Rectangle(423, 390, 140, 19));
        this.add(kdOnlyApplyObjCost, null);
        kDLabelContainer9.setBounds(new Rectangle(10, 390, 270, 19));
        this.add(kDLabelContainer9, null);
        kDLabelContainer10.setBounds(new Rectangle(10, 360, 270, 19));
        this.add(kDLabelContainer10, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtParentNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtLongNumber);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(bizName);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(bizLandDeveloper);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtSortNo);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(pkStartDate);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtDescription);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(bizVersionName);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(txtVersionNumber);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPrev);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.ProjectVersionRedactUIHandler";
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
        this.hisProject = (com.kingdee.eas.fdc.basedata.HisProjectInfo)ov;
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
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output txtNumber_focusLost method
     */
    protected void txtNumber_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFirst_actionPerformed method
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNext_actionPerformed method
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionLast_actionPerformed method
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrev_actionPerformed method
     */
    public void actionPrev_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionSave class
     */
    protected class ActionSave extends ItemAction
    {
        public ActionSave()
        {
            this(null);
        }

        public ActionSave(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectVersionRedactUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output ActionFirst class
     */
    protected class ActionFirst extends ItemAction
    {
        public ActionFirst()
        {
            this(null);
        }

        public ActionFirst(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionFirst.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFirst.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFirst.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectVersionRedactUI.this, "ActionFirst", "actionFirst_actionPerformed", e);
        }
    }

    /**
     * output ActionNext class
     */
    protected class ActionNext extends ItemAction
    {
        public ActionNext()
        {
            this(null);
        }

        public ActionNext(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionNext.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNext.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNext.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectVersionRedactUI.this, "ActionNext", "actionNext_actionPerformed", e);
        }
    }

    /**
     * output ActionLast class
     */
    protected class ActionLast extends ItemAction
    {
        public ActionLast()
        {
            this(null);
        }

        public ActionLast(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionLast.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLast.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLast.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectVersionRedactUI.this, "ActionLast", "actionLast_actionPerformed", e);
        }
    }

    /**
     * output ActionPrev class
     */
    protected class ActionPrev extends ItemAction
    {
        public ActionPrev()
        {
            this(null);
        }

        public ActionPrev(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPrev.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrev.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrev.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectVersionRedactUI.this, "ActionPrev", "actionPrev_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "ProjectVersionRedactUI");
    }




}