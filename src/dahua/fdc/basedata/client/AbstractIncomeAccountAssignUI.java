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
public abstract class AbstractIncomeAccountAssignUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractIncomeAccountAssignUI.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCuForAssigned;
    protected com.kingdee.bos.ctrl.swing.KDContainer lblCuForAssigned;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAssign;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAllSelect;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAllDisselect;
    protected ActionAssign actionAssign = null;
    protected ActionAssigned actionAssigned = null;
    protected ActionNotAssigned actionNotAssigned = null;
    protected ActionAllSelect actionAllSelect = null;
    protected ActionAllDisselect actionAllDisselect = null;
    protected ActionSelectUpper actionSelectUpper = null;
    protected ActionDisSelectUpper actionDisSelectUpper = null;
    /**
     * output class constructor
     */
    public AbstractIncomeAccountAssignUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractIncomeAccountAssignUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAssign
        this.actionAssign = new ActionAssign(this);
        getActionManager().registerAction("actionAssign", actionAssign);
        //actionAssigned
        this.actionAssigned = new ActionAssigned(this);
        getActionManager().registerAction("actionAssigned", actionAssigned);
         this.actionAssigned.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAssigned.addService(new com.kingdee.eas.framework.client.service.WorkFlowService());
        //actionNotAssigned
        this.actionNotAssigned = new ActionNotAssigned(this);
        getActionManager().registerAction("actionNotAssigned", actionNotAssigned);
         this.actionNotAssigned.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionNotAssigned.addService(new com.kingdee.eas.framework.client.service.WorkFlowService());
        //actionAllSelect
        this.actionAllSelect = new ActionAllSelect(this);
        getActionManager().registerAction("actionAllSelect", actionAllSelect);
         this.actionAllSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAllSelect.addService(new com.kingdee.eas.framework.client.service.WorkFlowService());
        //actionAllDisselect
        this.actionAllDisselect = new ActionAllDisselect(this);
        getActionManager().registerAction("actionAllDisselect", actionAllDisselect);
         this.actionAllDisselect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAllDisselect.addService(new com.kingdee.eas.framework.client.service.WorkFlowService());
        //actionSelectUpper
        this.actionSelectUpper = new ActionSelectUpper(this);
        getActionManager().registerAction("actionSelectUpper", actionSelectUpper);
         this.actionSelectUpper.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisSelectUpper
        this.actionDisSelectUpper = new ActionDisSelectUpper(this);
        getActionManager().registerAction("actionDisSelectUpper", actionDisSelectUpper);
         this.actionDisSelectUpper.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tblCuForAssigned = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.lblCuForAssigned = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.btnAllSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAllDisselect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblCuForAssigned.setName("tblCuForAssigned");
        this.lblCuForAssigned.setName("lblCuForAssigned");
        this.btnAssign.setName("btnAssign");
        this.btnAllSelect.setName("btnAllSelect");
        this.btnAllDisselect.setName("btnAllDisselect");
        // CoreUI
        // tblCuForAssigned		
        this.tblCuForAssigned.setFormatXml(resHelper.getString("tblCuForAssigned.formatXml"));
        this.tblCuForAssigned.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblCuForAssigned_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // lblCuForAssigned		
        this.lblCuForAssigned.setTitle(resHelper.getString("lblCuForAssigned.title"));		
        this.lblCuForAssigned.setTitleStyle(2);		
        this.lblCuForAssigned.setEnableActive(false);
        // btnAssign
        this.btnAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAssign.setText(resHelper.getString("btnAssign.text"));		
        this.btnAssign.setToolTipText(resHelper.getString("btnAssign.toolTipText"));
        // kDButtonGroup1
        // btnAllSelect
        this.btnAllSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAllSelect.setText(resHelper.getString("btnAllSelect.text"));		
        this.btnAllSelect.setToolTipText(resHelper.getString("btnAllSelect.toolTipText"));
        // btnAllDisselect
        this.btnAllDisselect.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllDisselect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAllDisselect.setText(resHelper.getString("btnAllDisselect.text"));		
        this.btnAllDisselect.setToolTipText(resHelper.getString("btnAllDisselect.toolTipText"));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 650, 480));
        this.setLayout(null);
        tblCuForAssigned.setBounds(new Rectangle(10, 31, 627, 438));
        this.add(tblCuForAssigned, null);
        lblCuForAssigned.setBounds(new Rectangle(11, 11, 144, 100));
        this.add(lblCuForAssigned, null);
        btnAllSelect.setBounds(new Rectangle(589, 9, 22, 19));
        this.add(btnAllSelect, null);
        btnAllDisselect.setBounds(new Rectangle(615, 9, 22, 19));
        this.add(btnAllDisselect, null);
        lblCuForAssigned.getContentPane().setLayout(null);
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
        this.toolBar.add(btnAssign);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.IncomeAccountAssignUIHandler";
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
     * output tblCuForAssigned_editValueChanged method
     */
    protected void tblCuForAssigned_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    	

    /**
     * output actionAssign_actionPerformed method
     */
    public void actionAssign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAssigned_actionPerformed method
     */
    public void actionAssigned_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNotAssigned_actionPerformed method
     */
    public void actionNotAssigned_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAllSelect_actionPerformed method
     */
    public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAllDisselect_actionPerformed method
     */
    public void actionAllDisselect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSelectUpper_actionPerformed method
     */
    public void actionSelectUpper_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisSelectUpper_actionPerformed method
     */
    public void actionDisSelectUpper_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionAssign class
     */     
    protected class ActionAssign extends ItemAction {     
    
        public ActionAssign()
        {
            this(null);
        }

        public ActionAssign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAssign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountAssignUI.this, "ActionAssign", "actionAssign_actionPerformed", e);
        }
    }

    /**
     * output ActionAssigned class
     */     
    protected class ActionAssigned extends ItemAction {     
    
        public ActionAssigned()
        {
            this(null);
        }

        public ActionAssigned(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAssigned.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssigned.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssigned.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountAssignUI.this, "ActionAssigned", "actionAssigned_actionPerformed", e);
        }
    }

    /**
     * output ActionNotAssigned class
     */     
    protected class ActionNotAssigned extends ItemAction {     
    
        public ActionNotAssigned()
        {
            this(null);
        }

        public ActionNotAssigned(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionNotAssigned.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNotAssigned.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNotAssigned.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountAssignUI.this, "ActionNotAssigned", "actionNotAssigned_actionPerformed", e);
        }
    }

    /**
     * output ActionAllSelect class
     */     
    protected class ActionAllSelect extends ItemAction {     
    
        public ActionAllSelect()
        {
            this(null);
        }

        public ActionAllSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAllSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountAssignUI.this, "ActionAllSelect", "actionAllSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionAllDisselect class
     */     
    protected class ActionAllDisselect extends ItemAction {     
    
        public ActionAllDisselect()
        {
            this(null);
        }

        public ActionAllDisselect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAllDisselect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllDisselect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllDisselect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountAssignUI.this, "ActionAllDisselect", "actionAllDisselect_actionPerformed", e);
        }
    }

    /**
     * output ActionSelectUpper class
     */     
    protected class ActionSelectUpper extends ItemAction {     
    
        public ActionSelectUpper()
        {
            this(null);
        }

        public ActionSelectUpper(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSelectUpper.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectUpper.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectUpper.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountAssignUI.this, "ActionSelectUpper", "actionSelectUpper_actionPerformed", e);
        }
    }

    /**
     * output ActionDisSelectUpper class
     */     
    protected class ActionDisSelectUpper extends ItemAction {     
    
        public ActionDisSelectUpper()
        {
            this(null);
        }

        public ActionDisSelectUpper(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisSelectUpper.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisSelectUpper.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisSelectUpper.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractIncomeAccountAssignUI.this, "ActionDisSelectUpper", "actionDisSelectUpper_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "IncomeAccountAssignUI");
    }




}