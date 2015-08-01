/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractDynaticFundPlanUI extends com.kingdee.eas.fdc.schedule.framework.client.ScheduleRateAchievedBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDynaticFundPlanUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer curProCon;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblStartMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblEndMonth;
    protected com.kingdee.bos.ctrl.swing.KDButton btnQueryData;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbStartMonth;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbEndYear;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbEndMonth;
    protected ActionQueryData actionQueryData = null;
    /**
     * output class constructor
     */
    public AbstractDynaticFundPlanUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDynaticFundPlanUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionQueryData
        this.actionQueryData = new ActionQueryData(this);
        getActionManager().registerAction("actionQueryData", actionQueryData);
         this.actionQueryData.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.curProCon = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contStartMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEndYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEndMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblStartMonth = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblEndMonth = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.btnQueryData = new com.kingdee.bos.ctrl.swing.KDButton();
        this.cbStartMonth = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbEndYear = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbEndMonth = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.curProCon.setName("curProCon");
        this.contStartMonth.setName("contStartMonth");
        this.contEndYear.setName("contEndYear");
        this.contEndMonth.setName("contEndMonth");
        this.lblStartMonth.setName("lblStartMonth");
        this.lblEndMonth.setName("lblEndMonth");
        this.btnQueryData.setName("btnQueryData");
        this.cbStartMonth.setName("cbStartMonth");
        this.cbEndYear.setName("cbEndYear");
        this.cbEndMonth.setName("cbEndMonth");
        // CoreUI		
        this.contYear.setBoundLabelText(resHelper.getString("contYear.boundLabelText"));		
        this.contYear.setBoundLabelLength(-1);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"fundType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{fundType}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));

        

        // curProCon		
        this.curProCon.setTitle(resHelper.getString("curProCon.title"));
        // contStartMonth		
        this.contStartMonth.setBoundLabelText(resHelper.getString("contStartMonth.boundLabelText"));
        // contEndYear		
        this.contEndYear.setBoundLabelText(resHelper.getString("contEndYear.boundLabelText"));
        // contEndMonth		
        this.contEndMonth.setBoundLabelText(resHelper.getString("contEndMonth.boundLabelText"));
        // lblStartMonth		
        this.lblStartMonth.setText(resHelper.getString("lblStartMonth.text"));
        // lblEndMonth		
        this.lblEndMonth.setText(resHelper.getString("lblEndMonth.text"));
        // btnQueryData
        this.btnQueryData.setAction((IItemAction)ActionProxyFactory.getProxy(actionQueryData, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQueryData.setText(resHelper.getString("btnQueryData.text"));
        // cbStartMonth
        // cbEndYear
        // cbEndMonth
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        sptLeft.setBounds(new Rectangle(10, 10, 993, 609));
        this.add(sptLeft, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //sptLeft
        sptLeft.add(sptTop, "right");
        sptLeft.add(curProCon, "left");
        //sptTop
        sptTop.add(ctChart1, "top");
        sptTop.add(tblMain, "bottom");
        //ctChart1
        ctChart1.setLayout(new KDLayout());
        ctChart1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 731, 499));        contYear.setBounds(new Rectangle(10, 10, 140, 19));
        ctChart1.add(contYear, new KDLayout.Constraints(10, 10, 140, 19, 0));
        pnlChart.setBounds(new Rectangle(10, 40, 713, 431));
        ctChart1.add(pnlChart, new KDLayout.Constraints(10, 40, 713, 431, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contStartMonth.setBounds(new Rectangle(160, 10, 60, 19));
        ctChart1.add(contStartMonth, new KDLayout.Constraints(160, 10, 60, 19, 0));
        contEndYear.setBounds(new Rectangle(320, 10, 140, 19));
        ctChart1.add(contEndYear, new KDLayout.Constraints(320, 10, 140, 19, 0));
        contEndMonth.setBounds(new Rectangle(470, 10, 60, 19));
        ctChart1.add(contEndMonth, new KDLayout.Constraints(470, 10, 60, 19, 0));
        lblStartMonth.setBounds(new Rectangle(230, 12, 20, 16));
        ctChart1.add(lblStartMonth, new KDLayout.Constraints(230, 12, 20, 16, 0));
        lblEndMonth.setBounds(new Rectangle(540, 12, 20, 19));
        ctChart1.add(lblEndMonth, new KDLayout.Constraints(540, 12, 20, 19, 0));
        btnQueryData.setBounds(new Rectangle(600, 10, 60, 19));
        ctChart1.add(btnQueryData, new KDLayout.Constraints(600, 10, 60, 19, 0));
        //contYear
        contYear.setBoundEditor(cbYear);
pnlChart.setLayout(new BorderLayout(0, 0));        //contStartMonth
        contStartMonth.setBoundEditor(cbStartMonth);
        //contEndYear
        contEndYear.setBoundEditor(cbEndYear);
        //contEndMonth
        contEndMonth.setBoundEditor(cbEndMonth);
        //curProCon
curProCon.getContentPane().setLayout(new BorderLayout(0, 0));        curProCon.getContentPane().add(treeView, BorderLayout.CENTER);
        //treeView
        treeView.setTree(treeMain);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(meueView);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemExitCurrent);
        //meueView
        meueView.add(menuItemPrint);
        meueView.add(menuItemPrintPre);
        meueView.add(kDSeparator2);
        meueView.add(menuItemExpIMG);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnExportIMG);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(kDSeparatorCloud);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.DynaticFundPlanUIHandler";
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
     * output actionQueryData_actionPerformed method
     */
    public void actionQueryData_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionQueryData(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQueryData() {
    	return false;
    }

    /**
     * output ActionQueryData class
     */     
    protected class ActionQueryData extends ItemAction {     
    
        public ActionQueryData()
        {
            this(null);
        }

        public ActionQueryData(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQueryData.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQueryData.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQueryData.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractDynaticFundPlanUI.this, "ActionQueryData", "actionQueryData_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "DynaticFundPlanUI");
    }




}