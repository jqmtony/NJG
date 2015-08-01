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
public abstract class AbstractFDCBIRptBaseMainUI extends com.kingdee.eas.framework.bireport.client.BireportBaseMainUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCBIRptBaseMainUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnJoinQuery;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuItemJoinQuery;
    protected ActionJoinQuery actionJoinQuery = null;
    /**
     * output class constructor
     */
    public AbstractFDCBIRptBaseMainUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCBIRptBaseMainUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionShowCustomStyle
        String _tempStr = null;
        actionShowCustomStyle.setEnabled(false);
        actionShowCustomStyle.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionShowCustomStyle.SHORT_DESCRIPTION");
        actionShowCustomStyle.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionShowCustomStyle.LONG_DESCRIPTION");
        actionShowCustomStyle.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionShowCustomStyle.NAME");
        actionShowCustomStyle.putValue(ItemAction.NAME, _tempStr);
         this.actionShowCustomStyle.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionJoinQuery
        this.actionJoinQuery = new ActionJoinQuery(this);
        getActionManager().registerAction("actionJoinQuery", actionJoinQuery);
         this.actionJoinQuery.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnJoinQuery = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDMenuItemJoinQuery = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnJoinQuery.setName("btnJoinQuery");
        this.kDMenuItemJoinQuery.setName("kDMenuItemJoinQuery");
        // CoreUI		
        this.btnShowChart.setVisible(false);		
        this.separator2.setVisible(false);
        // btnJoinQuery
        this.btnJoinQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionJoinQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnJoinQuery.setText(resHelper.getString("btnJoinQuery.text"));
        // kDMenuItemJoinQuery
        this.kDMenuItemJoinQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionJoinQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuItemJoinQuery.setText(resHelper.getString("kDMenuItemJoinQuery.text"));
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

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuView);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuView
        menuView.add(menuItemDisplayConfig);
        menuView.add(menuSwapAxes);
        menuView.add(kDSeparator2);
        menuView.add(menuItemFilt);
        menuView.add(menuShowSlice);
        menuView.add(menuItemRefresh);
        menuView.add(kDMenuItemJoinQuery);
        menuView.add(menuShowChart);
        menuView.add(menuChartAnalysis);
        menuView.add(menuCustomChart);
        menuView.add(menuShowSortRank);
        menuView.add(menuSizerData);
        menuView.add(menuShowCustomStyle);
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
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnFilt);
        this.toolBar.add(separator2);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separator1);
        this.toolBar.add(btnDisplayConfig);
        this.toolBar.add(btnSwapAxes);
        this.toolBar.add(btnShowSlice);
        this.toolBar.add(btnJoinQuery);
        this.toolBar.add(btnShowChart);
        this.toolBar.add(btnChartAnalysis);
        this.toolBar.add(btnCustomChart);
        this.toolBar.add(btnShowSortRank);
        this.toolBar.add(btnSizerData);
        this.toolBar.add(btnShowCustomStyle);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.FDCBIRptBaseMainUIHandler";
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
     * output actionShowCustomStyle_actionPerformed method
     */
    public void actionShowCustomStyle_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionShowCustomStyle_actionPerformed(e);
    }
    	

    /**
     * output actionJoinQuery_actionPerformed method
     */
    public void actionJoinQuery_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionJoinQuery class
     */
    protected class ActionJoinQuery extends ItemAction
    {
        public ActionJoinQuery()
        {
            this(null);
        }

        public ActionJoinQuery(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionJoinQuery.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionJoinQuery.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionJoinQuery.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCBIRptBaseMainUI.this, "ActionJoinQuery", "actionJoinQuery_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "FDCBIRptBaseMainUI");
    }




}