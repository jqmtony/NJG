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
public abstract class AbstractViewContractUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractViewContractUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer txtNameCon;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer txtAmountCon;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer txtBalanceCon;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer txtExRateCon;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer txtChangeCon;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer desCon;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBalance;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtExRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtChange;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExit;
    protected ActionExit ationExit = null;
    /**
     * output class constructor
     */
    public AbstractViewContractUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractViewContractUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //ationExit
        this.ationExit = new ActionExit(this);
        getActionManager().registerAction("ationExit", ationExit);
         this.ationExit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.txtNameCon = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAmountCon = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBalanceCon = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtExRateCon = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtChangeCon = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.desCon = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBalance = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtExRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtChange = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnExit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtNameCon.setName("txtNameCon");
        this.txtAmountCon.setName("txtAmountCon");
        this.txtBalanceCon.setName("txtBalanceCon");
        this.txtExRateCon.setName("txtExRateCon");
        this.txtChangeCon.setName("txtChangeCon");
        this.desCon.setName("desCon");
        this.txtName.setName("txtName");
        this.txtAmount.setName("txtAmount");
        this.txtBalance.setName("txtBalance");
        this.txtExRate.setName("txtExRate");
        this.txtChange.setName("txtChange");
        this.btnExit.setName("btnExit");
        // CoreUI		
        this.btnPageSetup.setVisible(true);
        // txtNameCon		
        this.txtNameCon.setBoundLabelText(resHelper.getString("txtNameCon.boundLabelText"));		
        this.txtNameCon.setBoundLabelUnderline(true);
        // txtAmountCon		
        this.txtAmountCon.setBoundLabelText(resHelper.getString("txtAmountCon.boundLabelText"));		
        this.txtAmountCon.setBoundLabelUnderline(true);
        // txtBalanceCon		
        this.txtBalanceCon.setBoundLabelText(resHelper.getString("txtBalanceCon.boundLabelText"));		
        this.txtBalanceCon.setBoundLabelUnderline(true);
        // txtExRateCon		
        this.txtExRateCon.setBoundLabelText(resHelper.getString("txtExRateCon.boundLabelText"));		
        this.txtExRateCon.setBoundLabelUnderline(true);
        // txtChangeCon		
        this.txtChangeCon.setBoundLabelText(resHelper.getString("txtChangeCon.boundLabelText"));		
        this.txtChangeCon.setBoundLabelUnderline(true);
        // desCon		
        this.desCon.setBoundLabelText(resHelper.getString("desCon.boundLabelText"));		
        this.desCon.setBoundLabelUnderline(true);		
        this.desCon.setEnabled(false);
        // txtName		
        this.txtName.setEnabled(false);
        // txtAmount		
        this.txtAmount.setEnabled(false);		
        this.txtAmount.setPrecision(2);
        // txtBalance		
        this.txtBalance.setEnabled(false);		
        this.txtBalance.setPrecision(2);
        // txtExRate		
        this.txtExRate.setEnabled(false);		
        this.txtExRate.setPrecision(2);
        // txtChange		
        this.txtChange.setEnabled(false);		
        this.txtChange.setPrecision(2);
        // btnExit
        this.btnExit.setAction((IItemAction)ActionProxyFactory.getProxy(ationExit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExit.setMaximumSize(new Dimension(1,18));		
        this.btnExit.setMinimumSize(new Dimension(1,18));		
        this.btnExit.setPreferredSize(new Dimension(1,18));
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
        this.setBounds(new Rectangle(10, 10, 640, 135));
        this.setLayout(null);
        txtNameCon.setBounds(new Rectangle(10, 10, 620, 19));
        this.add(txtNameCon, null);
        txtAmountCon.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(txtAmountCon, null);
        txtBalanceCon.setBounds(new Rectangle(360, 40, 270, 19));
        this.add(txtBalanceCon, null);
        txtExRateCon.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(txtExRateCon, null);
        txtChangeCon.setBounds(new Rectangle(360, 70, 270, 19));
        this.add(txtChangeCon, null);
        desCon.setBounds(new Rectangle(10, 100, 270, 19));
        this.add(desCon, null);
        //txtNameCon
        txtNameCon.setBoundEditor(txtName);
        //txtAmountCon
        txtAmountCon.setBoundEditor(txtAmount);
        //txtBalanceCon
        txtBalanceCon.setBoundEditor(txtBalance);
        //txtExRateCon
        txtExRateCon.setBoundEditor(txtExRate);
        //txtChangeCon
        txtChangeCon.setBoundEditor(txtChange);

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
        this.toolBar.add(btnExit);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ViewContractUIHandler";
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
     * output actionExit_actionPerformed method
     */
    public void actionExit_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionExit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExit() {
    	return false;
    }

    /**
     * output ActionExit class
     */     
    protected class ActionExit extends ItemAction {     
    
        public ActionExit()
        {
            this(null);
        }

        public ActionExit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractViewContractUI.this, "ActionExit", "actionExit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ViewContractUI");
    }




}