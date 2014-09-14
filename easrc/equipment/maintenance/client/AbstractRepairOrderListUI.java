/**
 * output package name
 */
package com.kingdee.eas.port.equipment.maintenance.client;

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
public abstract class AbstractRepairOrderListUI extends com.kingdee.eas.xr.client.XRBillBaseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRepairOrderListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnToVoid;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnToViod;
    protected ActionToVoid actionToVoid = null;
    protected ActionUnToVoid actionUnToVoid = null;
    /**
     * output class constructor
     */
    public AbstractRepairOrderListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRepairOrderListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.port.equipment.maintenance.app", "RepairOrderQuery");
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
        //actionToVoid
        this.actionToVoid = new ActionToVoid(this);
        getActionManager().registerAction("actionToVoid", actionToVoid);
        this.actionToVoid.setExtendProperty("canForewarn", "true");
        this.actionToVoid.setExtendProperty("userDefined", "true");
        this.actionToVoid.setExtendProperty("isObjectUpdateLock", "false");
         this.actionToVoid.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionToVoid.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionUnToVoid
        this.actionUnToVoid = new ActionUnToVoid(this);
        getActionManager().registerAction("actionUnToVoid", actionUnToVoid);
        this.actionUnToVoid.setExtendProperty("canForewarn", "true");
        this.actionUnToVoid.setExtendProperty("userDefined", "true");
        this.actionUnToVoid.setExtendProperty("isObjectUpdateLock", "false");
         this.actionUnToVoid.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionUnToVoid.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.btnToVoid = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnToViod = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnToVoid.setName("btnToVoid");
        this.btnUnToViod.setName("btnUnToViod");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol23\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol27\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol29\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol31\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol32\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol35\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol36\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol39\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol45\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol48\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"status\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"CU.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"equName.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"equModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"equAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"repairDepart.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"repairPerson.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"repairTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"repairContent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"assignee.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"acceptTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"maintenanceType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"maintenanceProgram\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"auditor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"BIMUDF0021\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"repairDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"acceptSituation\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"deliveryPerson.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"recipient.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"transferTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"slDepart.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" /><t:Column t:key=\"selfStudy\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"outsourcing\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"selfAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /><t:Column t:key=\"outAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" t:styleID=\"sCol32\" /><t:Column t:key=\"sfjhn\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" /><t:Column t:key=\"baoxiuren.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" /><t:Column t:key=\"baoxiuTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" t:styleID=\"sCol35\" /><t:Column t:key=\"E1.seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" t:styleID=\"sCol36\" /><t:Column t:key=\"E1.repairContent0\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" /><t:Column t:key=\"E1.replaceSparePart.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" /><t:Column t:key=\"E1.workTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" t:styleID=\"sCol39\" /><t:Column t:key=\"E1.repairPerson.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" /><t:Column t:key=\"E1.remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"41\" /><t:Column t:key=\"E1.FaLocation.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"42\" /><t:Column t:key=\"E1.wxfa\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"43\" /><t:Column t:key=\"E1.sfww\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"44\" /><t:Column t:key=\"E1.xlAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"45\" t:styleID=\"sCol45\" /><t:Column t:key=\"E1.guzhangqingkuang\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"E1.model\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"E1.shuliang\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol48\" /><t:Column t:key=\"E1.yujingzhouqi\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{CU.id}</t:Cell><t:Cell>$Resource{equName.name}</t:Cell><t:Cell>$Resource{equModel}</t:Cell><t:Cell>$Resource{equAddress}</t:Cell><t:Cell>$Resource{repairDepart.name}</t:Cell><t:Cell>$Resource{repairPerson.name}</t:Cell><t:Cell>$Resource{repairTime}</t:Cell><t:Cell>$Resource{repairContent}</t:Cell><t:Cell>$Resource{assignee.name}</t:Cell><t:Cell>$Resource{acceptTime}</t:Cell><t:Cell>$Resource{maintenanceType.name}</t:Cell><t:Cell>$Resource{maintenanceProgram}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{auditor.name}</t:Cell><t:Cell>$Resource{auditTime}</t:Cell><t:Cell>$Resource{BIMUDF0021}</t:Cell><t:Cell>$Resource{repairDate}</t:Cell><t:Cell>$Resource{acceptSituation}</t:Cell><t:Cell>$Resource{deliveryPerson.name}</t:Cell><t:Cell>$Resource{recipient.name}</t:Cell><t:Cell>$Resource{transferTime}</t:Cell><t:Cell>$Resource{slDepart.name}</t:Cell><t:Cell>$Resource{selfStudy}</t:Cell><t:Cell>$Resource{outsourcing}</t:Cell><t:Cell>$Resource{selfAmount}</t:Cell><t:Cell>$Resource{outAmount}</t:Cell><t:Cell>$Resource{sfjhn}</t:Cell><t:Cell>$Resource{baoxiuren.name}</t:Cell><t:Cell>$Resource{baoxiuTime}</t:Cell><t:Cell>$Resource{E1.seq}</t:Cell><t:Cell>$Resource{E1.repairContent0}</t:Cell><t:Cell>$Resource{E1.replaceSparePart.name}</t:Cell><t:Cell>$Resource{E1.workTime}</t:Cell><t:Cell>$Resource{E1.repairPerson.name}</t:Cell><t:Cell>$Resource{E1.remark}</t:Cell><t:Cell>$Resource{E1.FaLocation.name}</t:Cell><t:Cell>$Resource{E1.wxfa}</t:Cell><t:Cell>$Resource{E1.sfww}</t:Cell><t:Cell>$Resource{E1.xlAmount}</t:Cell><t:Cell>$Resource{E1.guzhangqingkuang}</t:Cell><t:Cell>$Resource{E1.model}</t:Cell><t:Cell>$Resource{E1.shuliang}</t:Cell><t:Cell>$Resource{E1.yujingzhouqi}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"number","bizDate","status","id","CU.id","equName.name","equModel","equAddress","repairDepart.name","repairPerson.name","repairTime","repairContent","assignee.name","acceptTime","maintenanceType.name","maintenanceProgram","creator.name","createTime","lastUpdateUser.name","lastUpdateTime","auditor.name","auditTime","BIMUDF0021","repairDate","acceptSituation","deliveryPerson.name","recipient.name","transferTime","slDepart.name","selfStudy","outsourcing","selfAmount","outAmount","sfjhn","baoxiuren.name","baoxiuTime","E1.seq","E1.repairContent0","E1.replaceSparePart.name","E1.workTime","E1.repairPerson.name","E1.remark","E1.FaLocation.name","E1.wxfa","E1.sfww","E1.xlAmount","E1.guzhangqingkuang","E1.model","E1.shuliang","E1.yujingzhouqi"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);
        // btnToVoid
        this.btnToVoid.setAction((IItemAction)ActionProxyFactory.getProxy(actionToVoid, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnToVoid.setText(resHelper.getString("btnToVoid.text"));
        // btnUnToViod
        this.btnUnToViod.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnToVoid, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnToViod.setText(resHelper.getString("btnUnToViod.text"));
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
        this.toolBar.add(btnToVoid);
        this.toolBar.add(btnUnToViod);
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
	    return "com.kingdee.eas.port.equipment.maintenance.app.RepairOrderListUIHandler";
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
        sic.add(new SelectorItemInfo("equName.name"));
        sic.add(new SelectorItemInfo("equModel"));
        sic.add(new SelectorItemInfo("equAddress"));
        sic.add(new SelectorItemInfo("repairDepart.name"));
        sic.add(new SelectorItemInfo("repairPerson.name"));
        sic.add(new SelectorItemInfo("repairTime"));
        sic.add(new SelectorItemInfo("repairContent"));
        sic.add(new SelectorItemInfo("assignee.name"));
        sic.add(new SelectorItemInfo("acceptTime"));
        sic.add(new SelectorItemInfo("maintenanceType.name"));
        sic.add(new SelectorItemInfo("maintenanceProgram"));
        sic.add(new SelectorItemInfo("E1.seq"));
        sic.add(new SelectorItemInfo("E1.repairContent0"));
        sic.add(new SelectorItemInfo("E1.replaceSparePart.name"));
        sic.add(new SelectorItemInfo("E1.workTime"));
        sic.add(new SelectorItemInfo("E1.repairPerson.name"));
        sic.add(new SelectorItemInfo("E1.remark"));
        sic.add(new SelectorItemInfo("BIMUDF0021"));
        sic.add(new SelectorItemInfo("repairDate"));
        sic.add(new SelectorItemInfo("acceptSituation"));
        sic.add(new SelectorItemInfo("deliveryPerson.name"));
        sic.add(new SelectorItemInfo("recipient.name"));
        sic.add(new SelectorItemInfo("transferTime"));
        sic.add(new SelectorItemInfo("slDepart.name"));
        sic.add(new SelectorItemInfo("selfStudy"));
        sic.add(new SelectorItemInfo("outsourcing"));
        sic.add(new SelectorItemInfo("selfAmount"));
        sic.add(new SelectorItemInfo("outAmount"));
        sic.add(new SelectorItemInfo("E1.FaLocation.name"));
        sic.add(new SelectorItemInfo("E1.wxfa"));
        sic.add(new SelectorItemInfo("sfjhn"));
        sic.add(new SelectorItemInfo("E1.sfww"));
        sic.add(new SelectorItemInfo("E1.xlAmount"));
        sic.add(new SelectorItemInfo("baoxiuren.name"));
        sic.add(new SelectorItemInfo("baoxiuTime"));
        sic.add(new SelectorItemInfo("E1.guzhangqingkuang"));
        sic.add(new SelectorItemInfo("E1.model"));
        sic.add(new SelectorItemInfo("E1.shuliang"));
        sic.add(new SelectorItemInfo("E1.yujingzhouqi"));
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
     * output actionToVoid_actionPerformed method
     */
    public void actionToVoid_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo editData = (com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.maintenance.RepairOrderFactory.getRemoteInstance().toVoid(editData);
    }
    	

    /**
     * output actionUnToVoid_actionPerformed method
     */
    public void actionUnToVoid_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo editData = (com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.maintenance.RepairOrderFactory.getRemoteInstance().unToVoid(editData);
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
	public RequestContext prepareActionToVoid(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionToVoid() {
    	return false;
    }
	public RequestContext prepareActionUnToVoid(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnToVoid() {
    	return false;
    }

    /**
     * output ActionToVoid class
     */     
    protected class ActionToVoid extends ItemAction {     
    
        public ActionToVoid()
        {
            this(null);
        }

        public ActionToVoid(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionToVoid.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToVoid.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionToVoid.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRepairOrderListUI.this, "ActionToVoid", "actionToVoid_actionPerformed", e);
        }
    }

    /**
     * output ActionUnToVoid class
     */     
    protected class ActionUnToVoid extends ItemAction {     
    
        public ActionUnToVoid()
        {
            this(null);
        }

        public ActionUnToVoid(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnToVoid.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnToVoid.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnToVoid.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRepairOrderListUI.this, "ActionUnToVoid", "actionUnToVoid_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.maintenance.client", "RepairOrderListUI");
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
        return com.kingdee.eas.port.equipment.maintenance.client.RepairOrderEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.maintenance.RepairOrderFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo objectValue = new com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo();		
        return objectValue;
    }

    /**
     * output getMergeColumnKeys method
     */
    public String[] getMergeColumnKeys()
    {
        return new String[] {"number","bizDate","status","id","CU.id","equName.name","equModel","equAddress","repairDepart.name","repairPerson.name","repairTime","repairContent","assignee.name","acceptTime","maintenanceType.name","maintenanceProgram","creator.name","createTime","lastUpdateUser.name","lastUpdateTime","auditor.name","auditTime","BIMUDF0021","repairDate","acceptSituation","deliveryPerson.name","recipient.name","transferTime","slDepart.name","selfStudy","outsourcing","selfAmount","outAmount","sfjhn","baoxiuren.name","baoxiuTime","E1.replaceSparePart.name","E1.repairPerson.name","E1.FaLocation.name"};
    }



	protected String getTDFileName() {
    	return "/bim/port/equipment/maintenance/RepairOrder";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.maintenance.app.RepairOrderQuery");
	}

}