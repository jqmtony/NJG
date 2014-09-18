/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.client;

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
public abstract class AbstractAnnualYearDetailListUI extends com.kingdee.eas.xr.client.XRBillBaseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAnnualYearDetailListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnIssued;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnIssued;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEntry;
    protected ActionEntry actionEntry = null;
    protected ActionConfirmation actionConfirmation = null;
    protected ActionUnConfirmation actionUnConfirmation = null;
    protected ActionConfirm actionConfirm = null;
    protected ActionUnConfirm actionUnConfirm = null;
    protected ActionXiada actionXiada = null;
    protected ActionFanxiada actionFanxiada = null;
    /**
     * output class constructor
     */
    public AbstractAnnualYearDetailListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAnnualYearDetailListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.port.equipment.special.app", "AnnualYearDetailQuery");
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
        //actionEntry
        this.actionEntry = new ActionEntry(this);
        getActionManager().registerAction("actionEntry", actionEntry);
        this.actionEntry.setExtendProperty("canForewarn", "true");
        this.actionEntry.setExtendProperty("userDefined", "true");
        this.actionEntry.setExtendProperty("isObjectUpdateLock", "false");
         this.actionEntry.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionEntry.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionConfirmation
        this.actionConfirmation = new ActionConfirmation(this);
        getActionManager().registerAction("actionConfirmation", actionConfirmation);
        this.actionConfirmation.setExtendProperty("canForewarn", "true");
        this.actionConfirmation.setExtendProperty("userDefined", "true");
        this.actionConfirmation.setExtendProperty("isObjectUpdateLock", "false");
         this.actionConfirmation.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionConfirmation.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionUnConfirmation
        this.actionUnConfirmation = new ActionUnConfirmation(this);
        getActionManager().registerAction("actionUnConfirmation", actionUnConfirmation);
        this.actionUnConfirmation.setExtendProperty("canForewarn", "true");
        this.actionUnConfirmation.setExtendProperty("userDefined", "true");
        this.actionUnConfirmation.setExtendProperty("isObjectUpdateLock", "false");
         this.actionUnConfirmation.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionUnConfirmation.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionConfirm
        this.actionConfirm = new ActionConfirm(this);
        getActionManager().registerAction("actionConfirm", actionConfirm);
        this.actionConfirm.setExtendProperty("canForewarn", "true");
        this.actionConfirm.setExtendProperty("userDefined", "true");
        this.actionConfirm.setExtendProperty("isObjectUpdateLock", "false");
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionUnConfirm
        this.actionUnConfirm = new ActionUnConfirm(this);
        getActionManager().registerAction("actionUnConfirm", actionUnConfirm);
        this.actionUnConfirm.setExtendProperty("canForewarn", "true");
        this.actionUnConfirm.setExtendProperty("userDefined", "true");
        this.actionUnConfirm.setExtendProperty("isObjectUpdateLock", "false");
         this.actionUnConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionUnConfirm.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionXiada
        this.actionXiada = new ActionXiada(this);
        getActionManager().registerAction("actionXiada", actionXiada);
        this.actionXiada.setExtendProperty("canForewarn", "true");
        this.actionXiada.setExtendProperty("userDefined", "true");
        this.actionXiada.setExtendProperty("isObjectUpdateLock", "false");
         this.actionXiada.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionXiada.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionFanxiada
        this.actionFanxiada = new ActionFanxiada(this);
        getActionManager().registerAction("actionFanxiada", actionFanxiada);
        this.actionFanxiada.setExtendProperty("canForewarn", "true");
        this.actionFanxiada.setExtendProperty("userDefined", "true");
        this.actionFanxiada.setExtendProperty("isObjectUpdateLock", "false");
         this.actionFanxiada.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionFanxiada.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.btnIssued = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnIssued = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEntry = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnIssued.setName("btnIssued");
        this.btnUnIssued.setName("btnUnIssued");
        this.btnEntry.setName("btnEntry");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol24\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol25\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol33\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"auditor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"status\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"CU.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"Entry.seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"Entry.zdaNumber.tzdaNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.equipmentName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.code\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.useUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.planDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"Entry.endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"Entry.state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.address\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.companyNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.NO\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.engineNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.carNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.weight\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol24\" /><t:Column t:key=\"Entry.useDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol25\" /><t:Column t:key=\"Entry.createUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.checkType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.check\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.result\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"useDpatmen.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isConfirmation\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Entry.sjjcrq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol33\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{auditor.name}</t:Cell><t:Cell>$Resource{auditTime}</t:Cell><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{CU.id}</t:Cell><t:Cell>$Resource{Entry.seq}</t:Cell><t:Cell>$Resource{Entry.zdaNumber.tzdaNumber}</t:Cell><t:Cell>$Resource{Entry.equipmentName}</t:Cell><t:Cell>$Resource{Entry.code}</t:Cell><t:Cell>$Resource{Entry.useUnit}</t:Cell><t:Cell>$Resource{Entry.planDate}</t:Cell><t:Cell>$Resource{Entry.endDate}</t:Cell><t:Cell>$Resource{Entry.state}</t:Cell><t:Cell>$Resource{Entry.address}</t:Cell><t:Cell>$Resource{Entry.companyNumber}</t:Cell><t:Cell>$Resource{Entry.NO}</t:Cell><t:Cell>$Resource{Entry.engineNumber}</t:Cell><t:Cell>$Resource{Entry.carNumber}</t:Cell><t:Cell>$Resource{Entry.weight}</t:Cell><t:Cell>$Resource{Entry.useDate}</t:Cell><t:Cell>$Resource{Entry.createUnit}</t:Cell><t:Cell>$Resource{Entry.checkType}</t:Cell><t:Cell>$Resource{Entry.check}</t:Cell><t:Cell>$Resource{Entry.result}</t:Cell><t:Cell>$Resource{Entry.beizhu}</t:Cell><t:Cell>$Resource{useDpatmen.name}</t:Cell><t:Cell>$Resource{isConfirmation}</t:Cell><t:Cell>$Resource{Entry.sjjcrq}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"number","bizDate","creator.name","createTime","lastUpdateUser.name","lastUpdateTime","auditor.name","auditTime","status","id","CU.id","Entry.seq","Entry.zdaNumber.tzdaNumber","Entry.equipmentName","Entry.code","Entry.useUnit","Entry.planDate","Entry.endDate","Entry.state","Entry.address","Entry.companyNumber","Entry.NO","Entry.engineNumber","Entry.carNumber","Entry.weight","Entry.useDate","Entry.createUnit","Entry.checkType","Entry.check","Entry.result","Entry.beizhu","useDpatmen.name","isConfirmation","Entry.sjjcrq"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);
        // btnIssued		
        this.btnIssued.setText(resHelper.getString("btnIssued.text"));
        this.btnIssued.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnIssued_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnUnIssued		
        this.btnUnIssued.setText(resHelper.getString("btnUnIssued.text"));
        this.btnUnIssued.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnUnIssued_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnEntry
        this.btnEntry.setAction((IItemAction)ActionProxyFactory.getProxy(actionEntry, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEntry.setText(resHelper.getString("btnEntry.text"));
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
        this.toolBar.add(btnIssued);
        this.toolBar.add(btnUnIssued);
        this.toolBar.add(btnEntry);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
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
	    return "com.kingdee.eas.port.equipment.special.app.AnnualYearDetailListUIHandler";
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

    /**
     * output btnIssued_actionPerformed method
     */
    protected void btnIssued_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnUnIssued_actionPerformed method
     */
    protected void btnUnIssued_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
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
        sic.add(new SelectorItemInfo("Entry.seq"));
        sic.add(new SelectorItemInfo("Entry.zdaNumber.tzdaNumber"));
        sic.add(new SelectorItemInfo("Entry.equipmentName"));
        sic.add(new SelectorItemInfo("Entry.code"));
        sic.add(new SelectorItemInfo("Entry.useUnit"));
        sic.add(new SelectorItemInfo("Entry.planDate"));
        sic.add(new SelectorItemInfo("Entry.endDate"));
        sic.add(new SelectorItemInfo("Entry.state"));
        sic.add(new SelectorItemInfo("Entry.address"));
        sic.add(new SelectorItemInfo("Entry.companyNumber"));
        sic.add(new SelectorItemInfo("Entry.NO"));
        sic.add(new SelectorItemInfo("Entry.engineNumber"));
        sic.add(new SelectorItemInfo("Entry.carNumber"));
        sic.add(new SelectorItemInfo("Entry.weight"));
        sic.add(new SelectorItemInfo("Entry.useDate"));
        sic.add(new SelectorItemInfo("Entry.createUnit"));
        sic.add(new SelectorItemInfo("Entry.checkType"));
        sic.add(new SelectorItemInfo("Entry.beizhu"));
        sic.add(new SelectorItemInfo("Entry.check"));
        sic.add(new SelectorItemInfo("Entry.result"));
        sic.add(new SelectorItemInfo("useDpatmen.name"));
        sic.add(new SelectorItemInfo("isConfirmation"));
        sic.add(new SelectorItemInfo("Entry.sjjcrq"));
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
     * output actionEntry_actionPerformed method
     */
    public void actionEntry_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo editData = (com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance().entry(editData);
    }
    	

    /**
     * output actionConfirmation_actionPerformed method
     */
    public void actionConfirmation_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo editData = (com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance().confirmation(editData);
    }
    	

    /**
     * output actionUnConfirmation_actionPerformed method
     */
    public void actionUnConfirmation_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo editData = (com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance().unConfirmation(editData);
    }
    	

    /**
     * output actionConfirm_actionPerformed method
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo editData = (com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance().confirm(editData);
    }
    	

    /**
     * output actionUnConfirm_actionPerformed method
     */
    public void actionUnConfirm_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo editData = (com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance().unConfirm(editData);
    }
    	

    /**
     * output actionXiada_actionPerformed method
     */
    public void actionXiada_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo editData = (com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance().xiada(editData);
    }
    	

    /**
     * output actionFanxiada_actionPerformed method
     */
    public void actionFanxiada_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo editData = (com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance().fanxiada(editData);
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
	public RequestContext prepareActionEntry(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEntry() {
    	return false;
    }
	public RequestContext prepareActionConfirmation(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConfirmation() {
    	return false;
    }
	public RequestContext prepareActionUnConfirmation(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnConfirmation() {
    	return false;
    }
	public RequestContext prepareActionConfirm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConfirm() {
    	return false;
    }
	public RequestContext prepareActionUnConfirm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnConfirm() {
    	return false;
    }
	public RequestContext prepareActionXiada(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionXiada() {
    	return false;
    }
	public RequestContext prepareActionFanxiada(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFanxiada() {
    	return false;
    }

    /**
     * output ActionEntry class
     */     
    protected class ActionEntry extends ItemAction {     
    
        public ActionEntry()
        {
            this(null);
        }

        public ActionEntry(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEntry.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEntry.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEntry.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAnnualYearDetailListUI.this, "ActionEntry", "actionEntry_actionPerformed", e);
        }
    }

    /**
     * output ActionConfirmation class
     */     
    protected class ActionConfirmation extends ItemAction {     
    
        public ActionConfirmation()
        {
            this(null);
        }

        public ActionConfirmation(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionConfirmation.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirmation.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirmation.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAnnualYearDetailListUI.this, "ActionConfirmation", "actionConfirmation_actionPerformed", e);
        }
    }

    /**
     * output ActionUnConfirmation class
     */     
    protected class ActionUnConfirmation extends ItemAction {     
    
        public ActionUnConfirmation()
        {
            this(null);
        }

        public ActionUnConfirmation(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnConfirmation.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnConfirmation.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnConfirmation.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAnnualYearDetailListUI.this, "ActionUnConfirmation", "actionUnConfirmation_actionPerformed", e);
        }
    }

    /**
     * output ActionConfirm class
     */     
    protected class ActionConfirm extends ItemAction {     
    
        public ActionConfirm()
        {
            this(null);
        }

        public ActionConfirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionConfirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAnnualYearDetailListUI.this, "ActionConfirm", "actionConfirm_actionPerformed", e);
        }
    }

    /**
     * output ActionUnConfirm class
     */     
    protected class ActionUnConfirm extends ItemAction {     
    
        public ActionUnConfirm()
        {
            this(null);
        }

        public ActionUnConfirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnConfirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnConfirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnConfirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAnnualYearDetailListUI.this, "ActionUnConfirm", "actionUnConfirm_actionPerformed", e);
        }
    }

    /**
     * output ActionXiada class
     */     
    protected class ActionXiada extends ItemAction {     
    
        public ActionXiada()
        {
            this(null);
        }

        public ActionXiada(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionXiada.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionXiada.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionXiada.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAnnualYearDetailListUI.this, "ActionXiada", "actionXiada_actionPerformed", e);
        }
    }

    /**
     * output ActionFanxiada class
     */     
    protected class ActionFanxiada extends ItemAction {     
    
        public ActionFanxiada()
        {
            this(null);
        }

        public ActionFanxiada(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionFanxiada.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFanxiada.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFanxiada.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAnnualYearDetailListUI.this, "ActionFanxiada", "actionFanxiada_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.special.client", "AnnualYearDetailListUI");
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
        return com.kingdee.eas.port.equipment.special.client.AnnualYearDetailEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo objectValue = new com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo();		
        return objectValue;
    }

    /**
     * output getMergeColumnKeys method
     */
    public String[] getMergeColumnKeys()
    {
        return new String[] {"number","bizDate","creator.name","createTime","lastUpdateUser.name","lastUpdateTime","auditor.name","auditTime","status","id","CU.id","Entry.zdaNumber.tzdaNumber","useDpatmen.name","isConfirmation"};
    }



	protected String getTDFileName() {
    	return "/bim/port/equipment/special/AnnualYearDetail";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.special.app.AnnualYearDetailQuery");
	}

}