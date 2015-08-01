/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.scheme.client;

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
public abstract class AbstractFdcIsqlUI extends com.kingdee.eas.fm.common.client.FMIsqlUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFdcIsqlUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShowDirectByUUID;
    protected ActionShowDirectByUUID actionShowDirectByUUID = null;
    /**
     * output class constructor
     */
    public AbstractFdcIsqlUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFdcIsqlUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionShowDirectByUUID
        this.actionShowDirectByUUID = new ActionShowDirectByUUID(this);
        getActionManager().registerAction("actionShowDirectByUUID", actionShowDirectByUUID);
         this.actionShowDirectByUUID.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnShowDirectByUUID = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnShowDirectByUUID.setName("btnShowDirectByUUID");
        // CoreUI
        // btnShowDirectByUUID
        this.btnShowDirectByUUID.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowDirectByUUID, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnShowDirectByUUID.setText(resHelper.getString("btnShowDirectByUUID.text"));		
        this.btnShowDirectByUUID.setToolTipText(resHelper.getString("btnShowDirectByUUID.toolTipText"));		
        this.btnShowDirectByUUID.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_linkviewbill"));
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
        this.setBounds(new Rectangle(10, 10, 700, 400));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 700, 400));
        splitpMain.setBounds(new Rectangle(10, 10, 681, 380));
        this.add(splitpMain, new KDLayout.Constraints(10, 10, 681, 380, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //splitpMain
        splitpMain.add(tabMain, "bottom");
        splitpMain.add(scrpScript, "top");
        //tabMain
        tabMain.add(scrpMsg, resHelper.getString("scrpMsg.constraints"));
        tabMain.add(scrpRowsets, resHelper.getString("scrpRowsets.constraints"));
        tabMain.add(scrpAbout, resHelper.getString("scrpAbout.constraints"));
        //scrpMsg
        scrpMsg.getViewport().add(txtMsg, null);
        //scrpAbout
        scrpAbout.getViewport().add(txtAbout, null);
        //scrpScript
        scrpScript.getViewport().add(txtScript, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExitCurrent);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        menuTool.add(menuItemExecute);
        menuTool.add(menuItemTransSql);
        menuTool.add(menuItemRunScriptClient);
        menuTool.add(kDMenuItemClientGC);
        menuTool.add(kDMenuItemServerGC);
        menuTool.add(kDMenuItemExcuteDialect);
        menuTool.add(kDMenuItemExcutePlan);
        menuTool.add(kDMenuItemFillTableName);
        menuTool.add(searches);
        menuTool.add(kDMenuItemRebuilderRef);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnExecute);
        
        this.toolBar.add(btnExecuteWithDialect);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnExecutePlan);
        this.toolBar.add(btnTransSql);
        this.toolBar.add(btnRunScriptServer);
        this.toolBar.add(btnRunScriptClient);
        this.toolBar.add(btnShowTables);
        this.toolBar.add(btnShowTableDef);
        this.toolBar.add(btnEntitySearch);
        this.toolBar.add(btnShowById);
        this.toolBar.add(kDWorkButton1);
        this.toolBar.add(btnShowTableByUuid);
        this.toolBar.add(btnShowDirectByUUID);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.scheme.app.FdcIsqlUIHandler";
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
     * output actionShowDirectByUUID_actionPerformed method
     */
    public void actionShowDirectByUUID_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionShowDirectByUUID(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowDirectByUUID() {
    	return false;
    }

    /**
     * output ActionShowDirectByUUID class
     */     
    protected class ActionShowDirectByUUID extends ItemAction {     
    
        public ActionShowDirectByUUID()
        {
            this(null);
        }

        public ActionShowDirectByUUID(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionShowDirectByUUID.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowDirectByUUID.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowDirectByUUID.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFdcIsqlUI.this, "ActionShowDirectByUUID", "actionShowDirectByUUID_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.scheme.client", "FdcIsqlUI");
    }




}