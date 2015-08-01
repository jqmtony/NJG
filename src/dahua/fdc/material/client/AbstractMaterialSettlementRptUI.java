/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

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
public abstract class AbstractMaterialSettlementRptUI extends com.kingdee.eas.fdc.basedata.client.FDCRptBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMaterialSettlementRptUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conPartB;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMaterial;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConfirm;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPartB;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContract;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMaterial;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEndDate;
    protected ActionDoQuery actionDoQuery = null;
    /**
     * output class constructor
     */
    public AbstractMaterialSettlementRptUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMaterialSettlementRptUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.material.app", "MaterialConfirmBillEntryQuery");
        //actionDoQuery
        this.actionDoQuery = new ActionDoQuery(this);
        getActionManager().registerAction("actionDoQuery", actionDoQuery);
         this.actionDoQuery.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.conPartB = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMaterial = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.prmtPartB = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtContract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMaterial = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel1.setName("kDPanel1");
        this.conPartB.setName("conPartB");
        this.contContract.setName("contContract");
        this.contMaterial.setName("contMaterial");
        this.contStartDate.setName("contStartDate");
        this.contEndDate.setName("contEndDate");
        this.btnConfirm.setName("btnConfirm");
        this.prmtPartB.setName("prmtPartB");
        this.prmtContract.setName("prmtContract");
        this.prmtMaterial.setName("prmtMaterial");
        this.pkStartDate.setName("pkStartDate");
        this.pkEndDate.setName("pkEndDate");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"material.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"material.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"material.model\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"baseUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"partB.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"section\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"quantity\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"avgPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"mainContractBill.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"mainContractBill.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"parent.supplyDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"materialContractBill.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"suppliername\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"pricePrecision\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"qtyPrecision\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{material.number}</t:Cell><t:Cell>$Resource{material.name}</t:Cell><t:Cell>$Resource{material.model}</t:Cell><t:Cell>$Resource{baseUnit.name}</t:Cell><t:Cell>$Resource{partB.name}</t:Cell><t:Cell>$Resource{section}</t:Cell><t:Cell>$Resource{quantity}</t:Cell><t:Cell>$Resource{avgPrice}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{mainContractBill.number}</t:Cell><t:Cell>$Resource{mainContractBill.name}</t:Cell><t:Cell>$Resource{parent.supplyDate}</t:Cell><t:Cell>$Resource{materialContractBill.name}</t:Cell><t:Cell>$Resource{suppliername}</t:Cell><t:Cell>$Resource{pricePrecision}</t:Cell><t:Cell>$Resource{qtyPrecision}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"material.number","material.name","material.model","baseUnit.name","partB.name","entrys.section","entrys.quantity","avgPrice","entrys.amount","mainContractBill.number","mainContractBill.name","parent.supplyDate","mainContractBill.number","suppliername","material.pricePrecision","baseUnit.qtyPrecision"});


        // kDPanel1
        // conPartB		
        this.conPartB.setBoundLabelText(resHelper.getString("conPartB.boundLabelText"));		
        this.conPartB.setBoundLabelUnderline(true);		
        this.conPartB.setBoundLabelLength(100);
        // contContract		
        this.contContract.setBoundLabelText(resHelper.getString("contContract.boundLabelText"));		
        this.contContract.setBoundLabelLength(100);		
        this.contContract.setBoundLabelUnderline(true);
        // contMaterial		
        this.contMaterial.setBoundLabelText(resHelper.getString("contMaterial.boundLabelText"));		
        this.contMaterial.setBoundLabelLength(100);		
        this.contMaterial.setBoundLabelUnderline(true);
        // contStartDate		
        this.contStartDate.setBoundLabelText(resHelper.getString("contStartDate.boundLabelText"));		
        this.contStartDate.setBoundLabelLength(100);
        // contEndDate		
        this.contEndDate.setBoundLabelText(resHelper.getString("contEndDate.boundLabelText"));		
        this.contEndDate.setBoundLabelLength(20);
        // btnConfirm
        this.btnConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionDoQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));
        // prmtPartB		
        this.prmtPartB.setCommitFormat("$number$");		
        this.prmtPartB.setDisplayFormat("$name$");		
        this.prmtPartB.setEditFormat("$number$");		
        this.prmtPartB.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard");
        // prmtContract		
        this.prmtContract.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillF7SimpleQuery");
        // prmtMaterial
        // pkStartDate
        // pkEndDate
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
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        pnlMain.setBounds(new Rectangle(8, 4, 996, 594));
        this.add(pnlMain, new KDLayout.Constraints(8, 4, 996, 594, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(kDPanel1, "right");
        //treeView
        treeView.setTree(treeMain);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 745, 593));        tblMain.setBounds(new Rectangle(5, 74, 750, 520));
        kDPanel1.add(tblMain, new KDLayout.Constraints(5, 74, 750, 520, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        conPartB.setBounds(new Rectangle(5, 6, 230, 19));
        kDPanel1.add(conPartB, new KDLayout.Constraints(5, 6, 230, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContract.setBounds(new Rectangle(260, 6, 230, 19));
        kDPanel1.add(contContract, new KDLayout.Constraints(260, 6, 230, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMaterial.setBounds(new Rectangle(516, 6, 230, 19));
        kDPanel1.add(contMaterial, new KDLayout.Constraints(516, 6, 230, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStartDate.setBounds(new Rectangle(5, 44, 230, 19));
        kDPanel1.add(contStartDate, new KDLayout.Constraints(5, 44, 230, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEndDate.setBounds(new Rectangle(242, 44, 131, 19));
        kDPanel1.add(contEndDate, new KDLayout.Constraints(242, 44, 131, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnConfirm.setBounds(new Rectangle(618, 42, 128, 19));
        kDPanel1.add(btnConfirm, new KDLayout.Constraints(618, 42, 128, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //conPartB
        conPartB.setBoundEditor(prmtPartB);
        //contContract
        contContract.setBoundEditor(prmtContract);
        //contMaterial
        contMaterial.setBoundEditor(prmtMaterial);
        //contStartDate
        contStartDate.setBoundEditor(pkStartDate);
        //contEndDate
        contEndDate.setBoundEditor(pkEndDate);

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
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemMoveTree);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
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
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.material.app.MaterialSettlementRptUIHandler";
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("mainContractBill.number"));
        sic.add(new SelectorItemInfo("mainContractBill.name"));
        sic.add(new SelectorItemInfo("partB.name"));
        sic.add(new SelectorItemInfo("material.number"));
        sic.add(new SelectorItemInfo("material.name"));
        sic.add(new SelectorItemInfo("material.model"));
        sic.add(new SelectorItemInfo("baseUnit.name"));
        sic.add(new SelectorItemInfo("avgPrice"));
        sic.add(new SelectorItemInfo("parent.supplyDate"));
        sic.add(new SelectorItemInfo("suppliername"));
        sic.add(new SelectorItemInfo("entrys.quantity"));
        sic.add(new SelectorItemInfo("entrys.amount"));
        sic.add(new SelectorItemInfo("material.pricePrecision"));
        sic.add(new SelectorItemInfo("baseUnit.qtyPrecision"));
        sic.add(new SelectorItemInfo("entrys.section"));
        return sic;
    }        
    	

    /**
     * output actionDoQuery_actionPerformed method
     */
    public void actionDoQuery_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionDoQuery(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDoQuery() {
    	return false;
    }

    /**
     * output ActionDoQuery class
     */     
    protected class ActionDoQuery extends ItemAction {     
    
        public ActionDoQuery()
        {
            this(null);
        }

        public ActionDoQuery(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDoQuery.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDoQuery.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDoQuery.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMaterialSettlementRptUI.this, "ActionDoQuery", "actionDoQuery_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.material.client", "MaterialSettlementRptUI");
    }




}