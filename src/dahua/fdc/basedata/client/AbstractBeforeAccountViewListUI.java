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
public abstract class AbstractBeforeAccountViewListUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBeforeAccountViewListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImpTemplate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImpTemplate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuImpTemplate;
    protected ActionImpTemplate actionImpTemplate = null;
    /**
     * output class constructor
     */
    public AbstractBeforeAccountViewListUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractBeforeAccountViewListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.basedata.app", "BeforeAccountViewQuery");
        //actionImpTemplate
        this.actionImpTemplate = new ActionImpTemplate(this);
        getActionManager().registerAction("actionImpTemplate", actionImpTemplate);
         this.actionImpTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnImpTemplate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemImpTemplate = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuImpTemplate = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnImpTemplate.setName("btnImpTemplate");
        this.menuItemImpTemplate.setName("menuItemImpTemplate");
        this.menuImpTemplate.setName("menuImpTemplate");
        // CoreUI		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","company.displayName","company.name","otherProAccount.longName","beforeOtherAccount.longName","otherSettAccount.longName","settAccount.longName","beforeSettAccount.longName","proAccount.longName","tempAccount.longName","intendAccount.longName","beforeDevAccount.longName","productAccount.longName","adminFees.longName","marketFees.longName"});

		
        this.btnLocate.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);
        // btnImpTemplate
        this.btnImpTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionImpTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImpTemplate.setText(resHelper.getString("btnImpTemplate.text"));		
        this.btnImpTemplate.setToolTipText(resHelper.getString("btnImpTemplate.toolTipText"));		
        this.btnImpTemplate.setMnemonic(73);
        // menuItemImpTemplate
        this.menuItemImpTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionImpTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImpTemplate.setText(resHelper.getString("menuItemImpTemplate.text"));		
        this.menuItemImpTemplate.setToolTipText(resHelper.getString("menuItemImpTemplate.toolTipText"));
        // menuImpTemplate
        this.menuImpTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionImpTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuImpTemplate.setText(resHelper.getString("menuImpTemplate.text"));		
        this.menuImpTemplate.setMnemonic(77);
        this.menuImpTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    menuImpTemplate_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
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
        tblMain.setBounds(new Rectangle(10, 10, 996, 580));
        this.add(tblMain, new KDLayout.Constraints(10, 10, 996, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuImpTemplate);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemImpTemplate);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
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
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnImpTemplate);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.BeforeAccountViewListUIHandler";
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
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output menuImpTemplate_actionPerformed method
     */
    protected void menuImpTemplate_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("company.displayName"));
        sic.add(new SelectorItemInfo("company.name"));
        sic.add(new SelectorItemInfo("otherProAccount.longName"));
        sic.add(new SelectorItemInfo("beforeOtherAccount.longName"));
        sic.add(new SelectorItemInfo("otherIntendAccount.longName"));
        sic.add(new SelectorItemInfo("otherSettAccount.longName"));
        sic.add(new SelectorItemInfo("settAccount.longName"));
        sic.add(new SelectorItemInfo("beforeSettAccount.longName"));
        sic.add(new SelectorItemInfo("proAccount.longName"));
        sic.add(new SelectorItemInfo("intendAccount.longName"));
        sic.add(new SelectorItemInfo("beforeDevAccount.longName"));
        sic.add(new SelectorItemInfo("productAccount.longName"));
        sic.add(new SelectorItemInfo("adminFees.longName"));
        sic.add(new SelectorItemInfo("marketFees.longName"));
        sic.add(new SelectorItemInfo("tempAccount.longName"));
        return sic;
    }        
    	

    /**
     * output actionImpTemplate_actionPerformed method
     */
    public void actionImpTemplate_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionImpTemplate class
     */     
    protected class ActionImpTemplate extends ItemAction {     
    
        public ActionImpTemplate()
        {
            this(null);
        }

        public ActionImpTemplate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importcyclostyle"));
            _tempStr = resHelper.getString("ActionImpTemplate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImpTemplate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImpTemplate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBeforeAccountViewListUI.this, "ActionImpTemplate", "actionImpTemplate_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "BeforeAccountViewListUI");
    }




}