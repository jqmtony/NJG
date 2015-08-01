/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractContractBillStoreUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractBillStoreUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOldNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNewNumber;
    protected com.kingdee.bos.ctrl.swing.KDButton kdbSave;
    protected com.kingdee.bos.ctrl.swing.KDButton kdbCancel;
    protected ActionDo actionDo = null;
    protected ActionRe actionRe = null;
    /**
     * output class constructor
     */
    public AbstractContractBillStoreUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractBillStoreUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionDo
        this.actionDo = new ActionDo(this);
        getActionManager().registerAction("actionDo", actionDo);
         this.actionDo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRe
        this.actionRe = new ActionRe(this);
        getActionManager().registerAction("actionRe", actionRe);
         this.actionRe.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtOldNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNewNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdbSave = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kdbCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtOldNumber.setName("txtOldNumber");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.txtNewNumber.setName("txtNewNumber");
        this.kdbSave.setName("kdbSave");
        this.kdbCancel.setName("kdbCancel");
        // CoreUI
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);
        // txtOldNumber		
        this.txtOldNumber.setMaxLength(80);		
        this.txtOldNumber.setVisible(true);		
        this.txtOldNumber.setEnabled(true);		
        this.txtOldNumber.setHorizontalAlignment(2);		
        this.txtOldNumber.setRequired(true);		
        this.txtOldNumber.setText(resHelper.getString("txtOldNumber.text"));		
        this.txtOldNumber.setEditable(false);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelAlignment(7);		
        this.kDLabelContainer2.setVisible(true);
        // txtNewNumber		
        this.txtNewNumber.setMaxLength(80);		
        this.txtNewNumber.setVisible(true);		
        this.txtNewNumber.setEnabled(true);		
        this.txtNewNumber.setHorizontalAlignment(2);		
        this.txtNewNumber.setRequired(true);		
        this.txtNewNumber.setText(resHelper.getString("txtNewNumber.text"));
        // kdbSave
        this.kdbSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionDo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kdbSave.setText(resHelper.getString("kdbSave.text"));		
        this.kdbSave.setToolTipText(resHelper.getString("kdbSave.toolTipText"));
        // kdbCancel
        this.kdbCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionRe, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kdbCancel.setText(resHelper.getString("kdbCancel.text"));		
        this.kdbCancel.setToolTipText(resHelper.getString("kdbCancel.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 496, 99));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(10, 10, 470, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 40, 470, 19));
        this.add(kDLabelContainer2, null);
        kdbSave.setBounds(new Rectangle(317, 69, 73, 21));
        this.add(kdbSave, null);
        kdbCancel.setBounds(new Rectangle(403, 69, 73, 21));
        this.add(kdbCancel, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtOldNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtNewNumber);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractBillStoreUIHandler";
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
     * output actionDo_actionPerformed method
     */
    public void actionDo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRe_actionPerformed method
     */
    public void actionRe_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionDo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDo() {
    	return false;
    }
	public RequestContext prepareActionRe(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRe() {
    	return false;
    }

    /**
     * output ActionDo class
     */     
    protected class ActionDo extends ItemAction {     
    
        public ActionDo()
        {
            this(null);
        }

        public ActionDo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillStoreUI.this, "ActionDo", "actionDo_actionPerformed", e);
        }
    }

    /**
     * output ActionRe class
     */     
    protected class ActionRe extends ItemAction {     
    
        public ActionRe()
        {
            this(null);
        }

        public ActionRe(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRe.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRe.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRe.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillStoreUI.this, "ActionRe", "actionRe_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractBillStoreUI");
    }




}