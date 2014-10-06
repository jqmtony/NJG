/**
 * output package name
 */
package com.kingdee.eas.port.equipment.insurance.client;

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
public abstract class AbstractInsuranceCoverageListUI extends com.kingdee.eas.xr.client.XRBillBaseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInsuranceCoverageListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExcelBxmx;
    protected ActionExcelBxmx actionExcelBxmx = null;
    /**
     * output class constructor
     */
    public AbstractInsuranceCoverageListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInsuranceCoverageListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.port.equipment.insurance.app", "InsuranceCoverageQuery");
        //actionRemove
        String _tempStr = null;
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
        this.actionRemove.setBindWorkFlow(true);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionExcelBxmx
        this.actionExcelBxmx = new ActionExcelBxmx(this);
        getActionManager().registerAction("actionExcelBxmx", actionExcelBxmx);
        this.actionExcelBxmx.setExtendProperty("canForewarn", "true");
        this.actionExcelBxmx.setExtendProperty("userDefined", "true");
        this.actionExcelBxmx.setExtendProperty("isObjectUpdateLock", "false");
         this.actionExcelBxmx.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionExcelBxmx.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.btnExcelBxmx = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExcelBxmx.setName("btnExcelBxmx");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol21\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol29\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol30\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"coverNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"insurance.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"baodanNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"xianzhongTwo.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"CU.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"E1.seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"E1.useUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"E1.equNumber.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"E1.equType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"E1.equName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"E1.specModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"E1.factoryUseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"E1.makeUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"E1.tonnage\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"E1.originalValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"E1.presentValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"E1.insuranceAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"E1.remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"xianzhongID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"E1.premium\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"auditor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" /><t:Column t:key=\"contNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" /><t:Column t:key=\"effectDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" /><t:Column t:key=\"status\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" /><t:Column t:key=\"insuranceCompany.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" /><t:Column t:key=\"year\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" /><t:Column t:key=\"tbrmc.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" /><t:Column t:key=\"xzdm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{coverNumber}</t:Cell><t:Cell>$Resource{insurance.name}</t:Cell><t:Cell>$Resource{baodanNo}</t:Cell><t:Cell>$Resource{xianzhongTwo.name}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{CU.id}</t:Cell><t:Cell>$Resource{E1.seq}</t:Cell><t:Cell>$Resource{E1.useUnit.name}</t:Cell><t:Cell>$Resource{E1.equNumber.number}</t:Cell><t:Cell>$Resource{E1.equType}</t:Cell><t:Cell>$Resource{E1.equName}</t:Cell><t:Cell>$Resource{E1.specModel}</t:Cell><t:Cell>$Resource{E1.factoryUseDate}</t:Cell><t:Cell>$Resource{E1.makeUnit}</t:Cell><t:Cell>$Resource{E1.tonnage}</t:Cell><t:Cell>$Resource{E1.originalValue}</t:Cell><t:Cell>$Resource{E1.presentValue}</t:Cell><t:Cell>$Resource{E1.insuranceAmount}</t:Cell><t:Cell>$Resource{E1.remark}</t:Cell><t:Cell>$Resource{xianzhongID}</t:Cell><t:Cell>$Resource{E1.premium}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{auditor.name}</t:Cell><t:Cell>$Resource{auditTime}</t:Cell><t:Cell>$Resource{contNumber}</t:Cell><t:Cell>$Resource{effectDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{insuranceCompany.name}</t:Cell><t:Cell>$Resource{year}</t:Cell><t:Cell>$Resource{tbrmc.name}</t:Cell><t:Cell>$Resource{xzdm}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"number","coverNumber","insurance.name","baodanNo","xianzhongTwo.name","id","CU.id","E1.seq","E1.useUnit.name","E1.equNumber.number","E1.equType","E1.equName","E1.specModel","E1.factoryUseDate","E1.makeUnit","E1.tonnage","E1.originalValue","E1.presentValue","E1.insuranceAmount","E1.remark","xianzhongID","E1.premium","creator.name","createTime","lastUpdateUser.name","lastUpdateTime","auditor.name","auditTime","contNumber","effectDate","endDate","bizDate","status","insuranceCompany.name","year","tbrmc.name","xzdm"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);
        // btnExcelBxmx
        this.btnExcelBxmx.setAction((IItemAction)ActionProxyFactory.getProxy(actionExcelBxmx, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExcelBxmx.setText(resHelper.getString("btnExcelBxmx.text"));
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
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkFlow);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemCloudShare);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemSwitchView);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(menuItemQueryScheme);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnView);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnExcelBxmx);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.insurance.app.InsuranceCoverageListUIHandler";
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
	protected void Remove() throws Exception {
    	IObjectValue editData = getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
    	super.Remove();
    	recycleNumberByOrg(editData,"",editData.getString("number"));
    }
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
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

	public SelectorItemCollection getBOTPSelectors() {
			SelectorItemCollection sic = new SelectorItemCollection();
			return sic;
	}

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.name"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("CU.id"));
        sic.add(new SelectorItemInfo("year"));
        sic.add(new SelectorItemInfo("insurance.name"));
        sic.add(new SelectorItemInfo("insuranceCompany.name"));
        sic.add(new SelectorItemInfo("E1.seq"));
        sic.add(new SelectorItemInfo("E1.useUnit.name"));
        sic.add(new SelectorItemInfo("E1.equNumber.number"));
        sic.add(new SelectorItemInfo("E1.equType"));
        sic.add(new SelectorItemInfo("E1.equName"));
        sic.add(new SelectorItemInfo("E1.specModel"));
        sic.add(new SelectorItemInfo("E1.factoryUseDate"));
        sic.add(new SelectorItemInfo("E1.makeUnit"));
        sic.add(new SelectorItemInfo("E1.tonnage"));
        sic.add(new SelectorItemInfo("E1.originalValue"));
        sic.add(new SelectorItemInfo("E1.presentValue"));
        sic.add(new SelectorItemInfo("E1.insuranceAmount"));
        sic.add(new SelectorItemInfo("E1.remark"));
        sic.add(new SelectorItemInfo("xianzhongID"));
        sic.add(new SelectorItemInfo("coverNumber"));
        sic.add(new SelectorItemInfo("contNumber"));
        sic.add(new SelectorItemInfo("effectDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("E1.premium"));
        sic.add(new SelectorItemInfo("tbrmc.name"));
        sic.add(new SelectorItemInfo("xzdm"));
        sic.add(new SelectorItemInfo("baodanNo"));
        sic.add(new SelectorItemInfo("xianzhongTwo.name"));
        return sic;
    }            protected java.util.List getQuerySorterFields() 
    { 
        java.util.List sorterFieldList = new ArrayList(); 
        sorterFieldList.add("createTime"); 
        return sorterFieldList; 
    } 

    	

    /**
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionExcelBxmx_actionPerformed method
     */
    public void actionExcelBxmx_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo editData = (com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.insurance.InsuranceCoverageFactory.getRemoteInstance().excelBxmx(editData);
    }
	public RequestContext prepareActionRemove(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRemove(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemove() {
    	return false;
    }
	public RequestContext prepareActionExcelBxmx(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExcelBxmx() {
    	return false;
    }

    /**
     * output ActionExcelBxmx class
     */     
    protected class ActionExcelBxmx extends ItemAction {     
    
        public ActionExcelBxmx()
        {
            this(null);
        }

        public ActionExcelBxmx(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExcelBxmx.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelBxmx.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelBxmx.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInsuranceCoverageListUI.this, "ActionExcelBxmx", "actionExcelBxmx_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.insurance.client", "InsuranceCoverageListUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.port.equipment.insurance.client.InsuranceCoverageEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.insurance.InsuranceCoverageFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo objectValue = new com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo();		
        return objectValue;
    }

    /**
     * output getMergeColumnKeys method
     */
    public String[] getMergeColumnKeys()
    {
        return new String[] {"number","coverNumber","insurance.name","baodanNo","xianzhongTwo.name","id","CU.id","E1.useUnit.name","E1.equNumber.number","xianzhongID","creator.name","createTime","lastUpdateUser.name","lastUpdateTime","auditor.name","auditTime","contNumber","effectDate","endDate","bizDate","status","insuranceCompany.name","year","tbrmc.name","xzdm"};
    }



	protected String getTDFileName() {
    	return "/bim/port/equipment/insurance/InsuranceCoverage";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.insurance.app.InsuranceCoverageQuery");
	}

}