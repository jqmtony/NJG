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
public abstract class AbstractSettlementCostSplitListUI extends com.kingdee.eas.fdc.basedata.client.FDCSplitBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSettlementCostSplitListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewInvalid;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewInvalid;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClearSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemClearSplit;
    protected ActionViewInvalid actionViewInvalid = null;
    protected ActionClearSplit actionClearSplit = null;
    /**
     * output class constructor
     */
    public AbstractSettlementCostSplitListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSettlementCostSplitListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "SettlementCostSplitQuery");
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
        //actionAudit
        actionAudit.setEnabled(true);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionAudit.setBindWorkFlow(true);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProjectAttachment
        actionProjectAttachment.setEnabled(true);
        actionProjectAttachment.setDaemonRun(false);

        actionProjectAttachment.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl W"));
        _tempStr = resHelper.getString("ActionProjectAttachment.SHORT_DESCRIPTION");
        actionProjectAttachment.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionProjectAttachment.LONG_DESCRIPTION");
        actionProjectAttachment.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionProjectAttachment.NAME");
        actionProjectAttachment.putValue(ItemAction.NAME, _tempStr);
         this.actionProjectAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContent
        actionViewContent.setEnabled(true);
        actionViewContent.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionViewContent.SHORT_DESCRIPTION");
        actionViewContent.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionViewContent.LONG_DESCRIPTION");
        actionViewContent.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionViewContent.NAME");
        actionViewContent.putValue(ItemAction.NAME, _tempStr);
         this.actionViewContent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddContent
        actionAddContent.setEnabled(true);
        actionAddContent.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAddContent.SHORT_DESCRIPTION");
        actionAddContent.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddContent.LONG_DESCRIPTION");
        actionAddContent.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddContent.NAME");
        actionAddContent.putValue(ItemAction.NAME, _tempStr);
         this.actionAddContent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewInvalid
        this.actionViewInvalid = new ActionViewInvalid(this);
        getActionManager().registerAction("actionViewInvalid", actionViewInvalid);
         this.actionViewInvalid.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClearSplit
        this.actionClearSplit = new ActionClearSplit(this);
        getActionManager().registerAction("actionClearSplit", actionClearSplit);
         this.actionClearSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnViewInvalid = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewInvalid = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnClearSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemClearSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnViewInvalid.setName("btnViewInvalid");
        this.menuItemViewInvalid.setName("menuItemViewInvalid");
        this.btnClearSplit.setName("btnClearSplit");
        this.menuItemClearSplit.setName("menuItemClearSplit");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol5\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /><c:NumberFormat>true</c:NumberFormat></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol24\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol26\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"isCostSplit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costSplit.splitState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractBill.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"curSettlePrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"totalSettlePrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"settlePrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"compensationAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"finalAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"qualityGuarante\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"unitPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"buildArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"getFeeCriteria\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"isFinalSettle\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"SettlementSplit.createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"auditor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"SettlementSplit.auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"infoPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"costSplit.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"contractId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"costSplit.state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"curProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{isCostSplit}</t:Cell><t:Cell>$Resource{costSplit.splitState}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{contractBill.number}</t:Cell><t:Cell>$Resource{curSettlePrice}</t:Cell><t:Cell>$Resource{totalSettlePrice}</t:Cell><t:Cell>$Resource{settlePrice}</t:Cell><t:Cell>$Resource{compensationAmt}</t:Cell><t:Cell>$Resource{finalAmt}</t:Cell><t:Cell>$Resource{qualityGuarante}</t:Cell><t:Cell>$Resource{unitPrice}</t:Cell><t:Cell>$Resource{buildArea}</t:Cell><t:Cell>$Resource{getFeeCriteria}</t:Cell><t:Cell>$Resource{isFinalSettle}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{SettlementSplit.createTime}</t:Cell><t:Cell>$Resource{auditor.name}</t:Cell><t:Cell>$Resource{SettlementSplit.auditTime}</t:Cell><t:Cell>$Resource{infoPrice}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{costSplit.id}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{contractId}</t:Cell><t:Cell>$Resource{costSplit.state}</t:Cell><t:Cell>$Resource{curProject.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"contractBill.isCoseSplit","costSplit.splitState","number","name","contractBill.number","curSettlePrice","totalSettlePrice","settlePrice","compensationAmt","finalAmt","qualityGuarante","unitPrice","buildArea","getFeeCriteria","isFinalSettle","creator.name","SettlementSplit.createTime","auditor.name","SettlementSplit.auditTime","infoPrice","description","state","costSplit.id","id","contractId","costSplit.state","curProject.id"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setText(resHelper.getString("btnEdit.text"));		
        this.btnEdit.setToolTipText(resHelper.getString("btnEdit.toolTipText"));		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemEdit.setText(resHelper.getString("menuItemEdit.text"));		
        this.menuItemEdit.setToolTipText(resHelper.getString("menuItemEdit.toolTipText"));		
        this.separatorFile1.setVisible(false);		
        this.menuWorkFlow.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);
        this.btnProjectAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionProjectAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProjectAttachment.setText(resHelper.getString("btnProjectAttachment.text"));		
        this.btnProjectAttachment.setToolTipText(resHelper.getString("btnProjectAttachment.toolTipText"));		
        this.btnProjectAttachment.setVisible(false);
        this.btnViewContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContent.setText(resHelper.getString("btnViewContent.text"));		
        this.btnViewContent.setToolTipText(resHelper.getString("btnViewContent.toolTipText"));		
        this.btnViewContent.setVisible(false);
        this.btnAddContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddContent.setText(resHelper.getString("btnAddContent.text"));		
        this.btnAddContent.setToolTipText(resHelper.getString("btnAddContent.toolTipText"));		
        this.btnAddContent.setVisible(false);
        // btnViewInvalid
        this.btnViewInvalid.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewInvalid, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewInvalid.setText(resHelper.getString("btnViewInvalid.text"));		
        this.btnViewInvalid.setToolTipText(resHelper.getString("btnViewInvalid.toolTipText"));
        // menuItemViewInvalid
        this.menuItemViewInvalid.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewInvalid, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewInvalid.setText(resHelper.getString("menuItemViewInvalid.text"));		
        this.menuItemViewInvalid.setToolTipText(resHelper.getString("menuItemViewInvalid.toolTipText"));		
        this.menuItemViewInvalid.setMnemonic(73);
        // btnClearSplit
        this.btnClearSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClearSplit.setText(resHelper.getString("btnClearSplit.text"));		
        this.btnClearSplit.setToolTipText(resHelper.getString("btnClearSplit.toolTipText"));
        // menuItemClearSplit
        this.menuItemClearSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionClearSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemClearSplit.setText(resHelper.getString("menuItemClearSplit.text"));		
        this.menuItemClearSplit.setMnemonic(76);		
        this.menuItemClearSplit.setToolTipText(resHelper.getString("menuItemClearSplit.toolTipText"));
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
        kDSplitPane1.setBounds(new Rectangle(9, 9, 994, 607));
        this.add(kDSplitPane1, new KDLayout.Constraints(9, 9, 994, 607, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDSplitPane1
        kDSplitPane1.add(treeProject, "left");
        kDSplitPane1.add(mainPanel, "right");
        //mainPanel
mainPanel.setLayout(new BorderLayout(0, 0));        mainPanel.add(tblMain, BorderLayout.CENTER);
        mainPanel.add(colorPanel, BorderLayout.SOUTH);
        colorPanel.setLayout(null);
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
        this.menuBar.add(menuWorkFlow);
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
        menuEdit.add(menuItemCostSplit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemClearSplit);
        menuBiz.add(menuItemViewInvalid);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnCostSplit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnQuery);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnProjectAttachment);
        this.toolBar.add(btnViewContent);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnAddContent);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnClearSplit);
        this.toolBar.add(btnViewInvalid);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.SettlementCostSplitListUIHandler";
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
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("CostCenter");
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
	 * ????????��??
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
			sic.add(new SelectorItemInfo("*"));
			sic.add(new SelectorItemInfo("creator.*"));
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
			sic.add(new SelectorItemInfo("CU.*"));
			sic.add(new SelectorItemInfo("handler.*"));
			sic.add(new SelectorItemInfo("auditor.*"));
			sic.add(new SelectorItemInfo("orgUnit.*"));
			sic.add(new SelectorItemInfo("period.*"));
			sic.add(new SelectorItemInfo("contractBill.*"));
			sic.add(new SelectorItemInfo("curProject.*"));
			sic.add(new SelectorItemInfo("entrys.*"));
			sic.add(new SelectorItemInfo("entrys.costAccount.*"));
			sic.add(new SelectorItemInfo("entrys.product.*"));
			sic.add(new SelectorItemInfo("entrys.apportionType.*"));
			sic.add(new SelectorItemInfo("entrys.accountView.*"));
			sic.add(new SelectorItemInfo("settlementBill.*"));
			return sic;
		}

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("costSplit.id"));
        sic.add(new SelectorItemInfo("costSplit.splitState"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("contractBill.number"));
        sic.add(new SelectorItemInfo("settlePrice"));
        sic.add(new SelectorItemInfo("qualityGuarante"));
        sic.add(new SelectorItemInfo("unitPrice"));
        sic.add(new SelectorItemInfo("buildArea"));
        sic.add(new SelectorItemInfo("getFeeCriteria"));
        sic.add(new SelectorItemInfo("isFinalSettle"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("SettlementSplit.createTime"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("SettlementSplit.auditTime"));
        sic.add(new SelectorItemInfo("infoPrice"));
        sic.add(new SelectorItemInfo("contractId"));
        sic.add(new SelectorItemInfo("costSplit.state"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("compensationAmt"));
        sic.add(new SelectorItemInfo("finalAmt"));
        sic.add(new SelectorItemInfo("contractBill.isCoseSplit"));
        sic.add(new SelectorItemInfo("curSettlePrice"));
        sic.add(new SelectorItemInfo("totalSettlePrice"));
        return sic;
    }        
    { 
        java.util.List sorterFieldList = new ArrayList(); 
        return sorterFieldList; 
    } 

    { 
        java.util.List pkList = new ArrayList(); 
        pkList.add("id"); 
        return pkList;
    }
    	

    /**
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }
    	

    /**
     * output actionProjectAttachment_actionPerformed method
     */
    public void actionProjectAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProjectAttachment_actionPerformed(e);
    }
    	

    /**
     * output actionViewContent_actionPerformed method
     */
    public void actionViewContent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewContent_actionPerformed(e);
    }
    	

    /**
     * output actionAddContent_actionPerformed method
     */
    public void actionAddContent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddContent_actionPerformed(e);
    }
    	

    /**
     * output actionViewInvalid_actionPerformed method
     */
    public void actionViewInvalid_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClearSplit_actionPerformed method
     */
    public void actionClearSplit_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionProjectAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionProjectAttachment(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProjectAttachment() {
    	return false;
    }
	public RequestContext prepareActionViewContent(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionViewContent(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContent() {
    	return false;
    }
	public RequestContext prepareActionAddContent(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAddContent(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddContent() {
    	return false;
    }
	public RequestContext prepareActionViewInvalid(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewInvalid() {
    	return false;
    }
	public RequestContext prepareActionClearSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClearSplit() {
    	return false;
    }

    /**
     * output ActionViewInvalid class
     */     
    protected class ActionViewInvalid extends ItemAction {     
    
        public ActionViewInvalid()
        {
            this(null);
        }

        public ActionViewInvalid(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_lookup"));
            _tempStr = resHelper.getString("ActionViewInvalid.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewInvalid.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewInvalid.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSettlementCostSplitListUI.this, "ActionViewInvalid", "actionViewInvalid_actionPerformed", e);
        }
    }

    /**
     * output ActionClearSplit class
     */     
    protected class ActionClearSplit extends ItemAction {     
    
        public ActionClearSplit()
        {
            this(null);
        }

        public ActionClearSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_blankout"));
            _tempStr = resHelper.getString("ActionClearSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClearSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSettlementCostSplitListUI.this, "ActionClearSplit", "actionClearSplit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "SettlementCostSplitListUI");
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
        return com.kingdee.eas.fdc.contract.client.SettlementCostSplitEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.SettlementCostSplitFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.SettlementCostSplitInfo objectValue = new com.kingdee.eas.fdc.contract.SettlementCostSplitInfo();		
        return objectValue;
    }

    /**
     * output getMergeColumnKeys method
     */
    public String[] getMergeColumnKeys()
    {
        return new String[] {"isCostSplit","number","name","contractBill.number","curSettlePrice","totalSettlePrice","settlePrice","qualityGuarante","unitPrice","buildArea","getFeeCriteria","isFinalSettle","infoPrice","description","state","id","contractId","curProject.id"};
    }



	protected String getTDFileName() {
    	return "/bim/fdc/contract/SettlementCostSplit";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.SettlementCostSplitQuery");
	}

}