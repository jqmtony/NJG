/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

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
public abstract class AbstractAimCostZkyF7UI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAimCostZkyF7UI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSubmit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRecense;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewWorkFlow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFirstVersion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPreVersion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnNextVersion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLastVersion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAmountUnit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRevert;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExpression;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnVersionInfo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApportion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCloseInit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSubmit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRecense;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAddRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRevert;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemVersionInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpression;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemFirstVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPreVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemNextVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemLastVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemApportion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCloseInit;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuWorkFlow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemWorkFlowG;
    protected ActionSubmit actionSubmit = null;
    protected ActionAddRow actionAddRow = null;
    protected ActionDeleteRow actionDeleteRow = null;
    protected ActionPreVersion actionPreVersion = null;
    protected ActionNextVersion actionNextVersion = null;
    protected ActionRecense actionRecense = null;
    protected ActionExpression actionExpression = null;
    protected ActionFirstVersion actionFirstVersion = null;
    protected ActionLastVersion actionLastVersion = null;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionVersionInfo actionVersionInfo = null;
    protected ActionApportion actionApportion = null;
    protected ActionRevert actionRevert = null;
    protected ActionAmountUnit actionAmountUnit = null;
    protected ActionCloseInit actionCloseInit = null;
    protected ActionWorkFlowG actionWorkFlowG = null;
    /**
     * output class constructor
     */
    public AbstractAimCostZkyF7UI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAimCostZkyF7UI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.aimcost.app", "AIMCostZkyF7Query");
        //actionMoveTree
        String _tempStr = null;
        actionMoveTree.setEnabled(true);
        actionMoveTree.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionMoveTree.SHORT_DESCRIPTION");
        actionMoveTree.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionMoveTree.LONG_DESCRIPTION");
        actionMoveTree.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionMoveTree.NAME");
        actionMoveTree.putValue(ItemAction.NAME, _tempStr);
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmit
        this.actionSubmit = new ActionSubmit(this);
        getActionManager().registerAction("actionSubmit", actionSubmit);
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddRow
        this.actionAddRow = new ActionAddRow(this);
        getActionManager().registerAction("actionAddRow", actionAddRow);
         this.actionAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteRow
        this.actionDeleteRow = new ActionDeleteRow(this);
        getActionManager().registerAction("actionDeleteRow", actionDeleteRow);
         this.actionDeleteRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPreVersion
        this.actionPreVersion = new ActionPreVersion(this);
        getActionManager().registerAction("actionPreVersion", actionPreVersion);
         this.actionPreVersion.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNextVersion
        this.actionNextVersion = new ActionNextVersion(this);
        getActionManager().registerAction("actionNextVersion", actionNextVersion);
         this.actionNextVersion.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRecense
        this.actionRecense = new ActionRecense(this);
        getActionManager().registerAction("actionRecense", actionRecense);
        this.actionRecense.setBindWorkFlow(true);
         this.actionRecense.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExpression
        this.actionExpression = new ActionExpression(this);
        getActionManager().registerAction("actionExpression", actionExpression);
         this.actionExpression.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFirstVersion
        this.actionFirstVersion = new ActionFirstVersion(this);
        getActionManager().registerAction("actionFirstVersion", actionFirstVersion);
         this.actionFirstVersion.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionLastVersion
        this.actionLastVersion = new ActionLastVersion(this);
        getActionManager().registerAction("actionLastVersion", actionLastVersion);
         this.actionLastVersion.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionVersionInfo
        this.actionVersionInfo = new ActionVersionInfo(this);
        getActionManager().registerAction("actionVersionInfo", actionVersionInfo);
         this.actionVersionInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionApportion
        this.actionApportion = new ActionApportion(this);
        getActionManager().registerAction("actionApportion", actionApportion);
         this.actionApportion.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRevert
        this.actionRevert = new ActionRevert(this);
        getActionManager().registerAction("actionRevert", actionRevert);
         this.actionRevert.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAmountUnit
        this.actionAmountUnit = new ActionAmountUnit(this);
        getActionManager().registerAction("actionAmountUnit", actionAmountUnit);
         this.actionAmountUnit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCloseInit
        this.actionCloseInit = new ActionCloseInit(this);
        getActionManager().registerAction("actionCloseInit", actionCloseInit);
         this.actionCloseInit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionWorkFlowG
        this.actionWorkFlowG = new ActionWorkFlowG(this);
        getActionManager().registerAction("actionWorkFlowG", actionWorkFlowG);
         this.actionWorkFlowG.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRecense = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewWorkFlow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFirstVersion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPreVersion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnNextVersion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnLastVersion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAmountUnit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRevert = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExpression = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnVersionInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnApportion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCloseInit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSubmit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRecense = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAddRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDeleteRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRevert = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemVersionInfo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpression = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemFirstVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPreVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemNextVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemLastVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemApportion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCloseInit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuWorkFlow = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemWorkFlowG = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnSubmit.setName("btnSubmit");
        this.btnRecense.setName("btnRecense");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnViewWorkFlow.setName("btnViewWorkFlow");
        this.btnAddRow.setName("btnAddRow");
        this.btnDeleteRow.setName("btnDeleteRow");
        this.btnFirstVersion.setName("btnFirstVersion");
        this.btnPreVersion.setName("btnPreVersion");
        this.btnNextVersion.setName("btnNextVersion");
        this.btnLastVersion.setName("btnLastVersion");
        this.btnAmountUnit.setName("btnAmountUnit");
        this.btnRevert.setName("btnRevert");
        this.btnExpression.setName("btnExpression");
        this.btnVersionInfo.setName("btnVersionInfo");
        this.btnApportion.setName("btnApportion");
        this.btnCloseInit.setName("btnCloseInit");
        this.menuItemSubmit.setName("menuItemSubmit");
        this.menuItemRecense.setName("menuItemRecense");
        this.menuItemAddRow.setName("menuItemAddRow");
        this.menuItemDeleteRow.setName("menuItemDeleteRow");
        this.menuItemRevert.setName("menuItemRevert");
        this.menuItemVersionInfo.setName("menuItemVersionInfo");
        this.menuItemExpression.setName("menuItemExpression");
        this.menuItemFirstVersion.setName("menuItemFirstVersion");
        this.menuItemPreVersion.setName("menuItemPreVersion");
        this.menuItemNextVersion.setName("menuItemNextVersion");
        this.menuItemLastVersion.setName("menuItemLastVersion");
        this.menuItemAudit.setName("menuItemAudit");
        this.menuItemUnAudit.setName("menuItemUnAudit");
        this.menuItemApportion.setName("menuItemApportion");
        this.menuItemCloseInit.setName("menuItemCloseInit");
        this.menuWorkFlow.setName("menuWorkFlow");
        this.menuItemWorkFlowG.setName("menuItemWorkFlowG");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"measureStage.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"versionNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"versionName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"isLastVersion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"sortVersionNumber\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"id\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"orgOrProId\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{measureStage.name}</t:Cell><t:Cell>$Resource{versionNumber}</t:Cell><t:Cell>$Resource{versionName}</t:Cell><t:Cell>$Resource{isLastVersion}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{sortVersionNumber}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{orgOrProId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"measureStage.name","versionNumber","versionName","isLastVersion","creator.name","description","sortVersionNumber","id","orgOrProId"});

		
        this.pnlMain.setDividerLocation(180);		
        this.pnlMain.setOneTouchExpandable(true);
        // btnSubmit
        this.btnSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));
        // btnRecense
        this.btnRecense.setAction((IItemAction)ActionProxyFactory.getProxy(actionRecense, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRecense.setText(resHelper.getString("btnRecense.text"));		
        this.btnRecense.setToolTipText(resHelper.getString("btnRecense.toolTipText"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setToolTipText(resHelper.getString("btnUnAudit.toolTipText"));
        // btnViewWorkFlow
        this.btnViewWorkFlow.setAction((IItemAction)ActionProxyFactory.getProxy(actionWorkFlowG, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewWorkFlow.setText(resHelper.getString("btnViewWorkFlow.text"));
        // btnAddRow
        this.btnAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRow.setText(resHelper.getString("btnAddRow.text"));		
        this.btnAddRow.setToolTipText(resHelper.getString("btnAddRow.toolTipText"));
        // btnDeleteRow
        this.btnDeleteRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeleteRow.setText(resHelper.getString("btnDeleteRow.text"));		
        this.btnDeleteRow.setToolTipText(resHelper.getString("btnDeleteRow.toolTipText"));
        // btnFirstVersion
        this.btnFirstVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionFirstVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFirstVersion.setText(resHelper.getString("btnFirstVersion.text"));		
        this.btnFirstVersion.setToolTipText(resHelper.getString("btnFirstVersion.toolTipText"));
        // btnPreVersion
        this.btnPreVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionPreVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPreVersion.setText(resHelper.getString("btnPreVersion.text"));		
        this.btnPreVersion.setToolTipText(resHelper.getString("btnPreVersion.toolTipText"));
        // btnNextVersion
        this.btnNextVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionNextVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnNextVersion.setText(resHelper.getString("btnNextVersion.text"));		
        this.btnNextVersion.setToolTipText(resHelper.getString("btnNextVersion.toolTipText"));
        // btnLastVersion
        this.btnLastVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionLastVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLastVersion.setText(resHelper.getString("btnLastVersion.text"));		
        this.btnLastVersion.setToolTipText(resHelper.getString("btnLastVersion.toolTipText"));
        // btnAmountUnit		
        this.btnAmountUnit.setText(resHelper.getString("btnAmountUnit.text"));
        // btnRevert
        this.btnRevert.setAction((IItemAction)ActionProxyFactory.getProxy(actionRevert, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRevert.setText(resHelper.getString("btnRevert.text"));		
        this.btnRevert.setToolTipText(resHelper.getString("btnRevert.toolTipText"));
        // btnExpression
        this.btnExpression.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpression, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExpression.setText(resHelper.getString("btnExpression.text"));		
        this.btnExpression.setToolTipText(resHelper.getString("btnExpression.toolTipText"));
        // btnVersionInfo
        this.btnVersionInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionVersionInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnVersionInfo.setText(resHelper.getString("btnVersionInfo.text"));		
        this.btnVersionInfo.setToolTipText(resHelper.getString("btnVersionInfo.toolTipText"));
        // btnApportion
        this.btnApportion.setAction((IItemAction)ActionProxyFactory.getProxy(actionApportion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnApportion.setText(resHelper.getString("btnApportion.text"));		
        this.btnApportion.setToolTipText(resHelper.getString("btnApportion.toolTipText"));
        // btnCloseInit
        this.btnCloseInit.setAction((IItemAction)ActionProxyFactory.getProxy(actionCloseInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCloseInit.setText(resHelper.getString("btnCloseInit.text"));		
        this.btnCloseInit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_closeinitialize"));
        // menuItemSubmit
        this.menuItemSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemSubmit.setToolTipText(resHelper.getString("menuItemSubmit.toolTipText"));
        // menuItemRecense
        this.menuItemRecense.setAction((IItemAction)ActionProxyFactory.getProxy(actionRecense, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRecense.setText(resHelper.getString("menuItemRecense.text"));		
        this.menuItemRecense.setToolTipText(resHelper.getString("menuItemRecense.toolTipText"));
        // menuItemAddRow
        this.menuItemAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAddRow.setText(resHelper.getString("menuItemAddRow.text"));
        // menuItemDeleteRow
        this.menuItemDeleteRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDeleteRow.setText(resHelper.getString("menuItemDeleteRow.text"));
        // menuItemRevert
        this.menuItemRevert.setAction((IItemAction)ActionProxyFactory.getProxy(actionRevert, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRevert.setText(resHelper.getString("menuItemRevert.text"));		
        this.menuItemRevert.setToolTipText(resHelper.getString("menuItemRevert.toolTipText"));
        // menuItemVersionInfo
        this.menuItemVersionInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionVersionInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemVersionInfo.setText(resHelper.getString("menuItemVersionInfo.text"));		
        this.menuItemVersionInfo.setToolTipText(resHelper.getString("menuItemVersionInfo.toolTipText"));
        // menuItemExpression
        this.menuItemExpression.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpression, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpression.setText(resHelper.getString("menuItemExpression.text"));		
        this.menuItemExpression.setToolTipText(resHelper.getString("menuItemExpression.toolTipText"));
        // menuItemFirstVersion
        this.menuItemFirstVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionFirstVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemFirstVersion.setText(resHelper.getString("menuItemFirstVersion.text"));
        // menuItemPreVersion
        this.menuItemPreVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionPreVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPreVersion.setText(resHelper.getString("menuItemPreVersion.text"));
        // menuItemNextVersion
        this.menuItemNextVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionNextVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemNextVersion.setText(resHelper.getString("menuItemNextVersion.text"));
        // menuItemLastVersion
        this.menuItemLastVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionLastVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemLastVersion.setText(resHelper.getString("menuItemLastVersion.text"));
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));
        // menuItemUnAudit
        this.menuItemUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));
        // menuItemApportion
        this.menuItemApportion.setAction((IItemAction)ActionProxyFactory.getProxy(actionApportion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemApportion.setText(resHelper.getString("menuItemApportion.text"));
        // menuItemCloseInit
        this.menuItemCloseInit.setAction((IItemAction)ActionProxyFactory.getProxy(actionCloseInit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCloseInit.setText(resHelper.getString("menuItemCloseInit.text"));		
        this.menuItemCloseInit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_closeinitialize"));
        // menuWorkFlow		
        this.menuWorkFlow.setText(resHelper.getString("menuWorkFlow.text"));		
        this.menuWorkFlow.setMnemonic(87);
        // menuItemWorkFlowG
        this.menuItemWorkFlowG.setAction((IItemAction)ActionProxyFactory.getProxy(actionWorkFlowG, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemWorkFlowG.setText(resHelper.getString("menuItemWorkFlowG.text"));		
        this.menuItemWorkFlowG.setMnemonic(71);
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
        this.setBounds(new Rectangle(10, 10, 1000, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1000, 600));
        pnlMain.setBounds(new Rectangle(10, 10, 980, 580));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 980, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(tblMain, "right");
        pnlMain.add(treeView, "left");
        //treeView
        treeView.setTree(treeMain);

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
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemRecense);
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
        menuEdit.add(menuItemAddRow);
        menuEdit.add(menuItemDeleteRow);
        menuEdit.add(menuItemRevert);
        menuEdit.add(menuItemVersionInfo);
        menuEdit.add(menuItemExpression);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemFirstVersion);
        menuView.add(menuItemPreVersion);
        menuView.add(menuItemNextVersion);
        menuView.add(menuItemLastVersion);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemApportion);
        menuBiz.add(menuItemCloseInit);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuWorkFlow
        menuWorkFlow.add(menuItemWorkFlowG);
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
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnRecense);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnViewWorkFlow);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAddRow);
        this.toolBar.add(btnDeleteRow);
        this.toolBar.add(btnFirstVersion);
        this.toolBar.add(btnPreVersion);
        this.toolBar.add(btnNextVersion);
        this.toolBar.add(btnLastVersion);
        this.toolBar.add(btnAmountUnit);
        this.toolBar.add(btnRevert);
        this.toolBar.add(btnExpression);
        this.toolBar.add(btnVersionInfo);
        this.toolBar.add(btnApportion);
        this.toolBar.add(btnCloseInit);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnAttachment);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.aimcost.app.AimCostZkyF7UIHandler";
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
     * output tblMain_editStopping method
     */
    protected void tblMain_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblMain_editStopped method
     */
    protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("versionNumber"));
        sic.add(new SelectorItemInfo("versionName"));
        sic.add(new SelectorItemInfo("isLastVersion"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("sortVersionNumber"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("orgOrProId"));
        sic.add(new SelectorItemInfo("measureStage.name"));
        return sic;
    }        
    	

    /**
     * output actionMoveTree_actionPerformed method
     */
    public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMoveTree_actionPerformed(e);
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        //write your code here
    }
    	

    /**
     * output actionAddRow_actionPerformed method
     */
    public void actionAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteRow_actionPerformed method
     */
    public void actionDeleteRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPreVersion_actionPerformed method
     */
    public void actionPreVersion_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNextVersion_actionPerformed method
     */
    public void actionNextVersion_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRecense_actionPerformed method
     */
    public void actionRecense_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExpression_actionPerformed method
     */
    public void actionExpression_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFirstVersion_actionPerformed method
     */
    public void actionFirstVersion_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionLastVersion_actionPerformed method
     */
    public void actionLastVersion_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionVersionInfo_actionPerformed method
     */
    public void actionVersionInfo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionApportion_actionPerformed method
     */
    public void actionApportion_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRevert_actionPerformed method
     */
    public void actionRevert_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAmountUnit_actionPerformed method
     */
    public void actionAmountUnit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCloseInit_actionPerformed method
     */
    public void actionCloseInit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionWorkFlowG_actionPerformed method
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionMoveTree(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionMoveTree(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMoveTree() {
    	return false;
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionAddRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddRow() {
    	return false;
    }
	public RequestContext prepareActionDeleteRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeleteRow() {
    	return false;
    }
	public RequestContext prepareActionPreVersion(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPreVersion() {
    	return false;
    }
	public RequestContext prepareActionNextVersion(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNextVersion() {
    	return false;
    }
	public RequestContext prepareActionRecense(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRecense() {
    	return false;
    }
	public RequestContext prepareActionExpression(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExpression() {
    	return false;
    }
	public RequestContext prepareActionFirstVersion(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFirstVersion() {
    	return false;
    }
	public RequestContext prepareActionLastVersion(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLastVersion() {
    	return false;
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionVersionInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionVersionInfo() {
    	return false;
    }
	public RequestContext prepareActionApportion(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionApportion() {
    	return false;
    }
	public RequestContext prepareActionRevert(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRevert() {
    	return false;
    }
	public RequestContext prepareActionAmountUnit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAmountUnit() {
    	return false;
    }
	public RequestContext prepareActionCloseInit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCloseInit() {
    	return false;
    }
	public RequestContext prepareActionWorkFlowG(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionWorkFlowG() {
    	return false;
    }

    /**
     * output ActionSubmit class
     */     
    protected class ActionSubmit extends ItemAction {     
    
        public ActionSubmit()
        {
            this(null);
        }

        public ActionSubmit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift S"));
            _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionSubmit", "actionSubmit_actionPerformed", e);
        }
    }

    /**
     * output ActionAddRow class
     */     
    protected class ActionAddRow extends ItemAction {     
    
        public ActionAddRow()
        {
            this(null);
        }

        public ActionAddRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift N"));
            _tempStr = resHelper.getString("ActionAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionAddRow", "actionAddRow_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteRow class
     */     
    protected class ActionDeleteRow extends ItemAction {     
    
        public ActionDeleteRow()
        {
            this(null);
        }

        public ActionDeleteRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift D"));
            _tempStr = resHelper.getString("ActionDeleteRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionDeleteRow", "actionDeleteRow_actionPerformed", e);
        }
    }

    /**
     * output ActionPreVersion class
     */     
    protected class ActionPreVersion extends ItemAction {     
    
        public ActionPreVersion()
        {
            this(null);
        }

        public ActionPreVersion(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl <"));
            _tempStr = resHelper.getString("ActionPreVersion.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPreVersion.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPreVersion.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionPreVersion", "actionPreVersion_actionPerformed", e);
        }
    }

    /**
     * output ActionNextVersion class
     */     
    protected class ActionNextVersion extends ItemAction {     
    
        public ActionNextVersion()
        {
            this(null);
        }

        public ActionNextVersion(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl >"));
            _tempStr = resHelper.getString("ActionNextVersion.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNextVersion.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNextVersion.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionNextVersion", "actionNextVersion_actionPerformed", e);
        }
    }

    /**
     * output ActionRecense class
     */     
    protected class ActionRecense extends ItemAction {     
    
        public ActionRecense()
        {
            this(null);
        }

        public ActionRecense(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift R"));
            _tempStr = resHelper.getString("ActionRecense.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRecense.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRecense.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionRecense", "actionRecense_actionPerformed", e);
        }
    }

    /**
     * output ActionExpression class
     */     
    protected class ActionExpression extends ItemAction {     
    
        public ActionExpression()
        {
            this(null);
        }

        public ActionExpression(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl K"));
            _tempStr = resHelper.getString("ActionExpression.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExpression.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExpression.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionExpression", "actionExpression_actionPerformed", e);
        }
    }

    /**
     * output ActionFirstVersion class
     */     
    protected class ActionFirstVersion extends ItemAction {     
    
        public ActionFirstVersion()
        {
            this(null);
        }

        public ActionFirstVersion(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift <"));
            _tempStr = resHelper.getString("ActionFirstVersion.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFirstVersion.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFirstVersion.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionFirstVersion", "actionFirstVersion_actionPerformed", e);
        }
    }

    /**
     * output ActionLastVersion class
     */     
    protected class ActionLastVersion extends ItemAction {     
    
        public ActionLastVersion()
        {
            this(null);
        }

        public ActionLastVersion(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift >"));
            _tempStr = resHelper.getString("ActionLastVersion.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLastVersion.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLastVersion.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionLastVersion", "actionLastVersion_actionPerformed", e);
        }
    }

    /**
     * output ActionAudit class
     */     
    protected class ActionAudit extends ItemAction {     
    
        public ActionAudit()
        {
            this(null);
        }

        public ActionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift T"));
            _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAudit class
     */     
    protected class ActionUnAudit extends ItemAction {     
    
        public ActionUnAudit()
        {
            this(null);
        }

        public ActionUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
            _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionVersionInfo class
     */     
    protected class ActionVersionInfo extends ItemAction {     
    
        public ActionVersionInfo()
        {
            this(null);
        }

        public ActionVersionInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift I"));
            _tempStr = resHelper.getString("ActionVersionInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVersionInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionVersionInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionVersionInfo", "actionVersionInfo_actionPerformed", e);
        }
    }

    /**
     * output ActionApportion class
     */     
    protected class ActionApportion extends ItemAction {     
    
        public ActionApportion()
        {
            this(null);
        }

        public ActionApportion(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionApportion.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApportion.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApportion.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionApportion", "actionApportion_actionPerformed", e);
        }
    }

    /**
     * output ActionRevert class
     */     
    protected class ActionRevert extends ItemAction {     
    
        public ActionRevert()
        {
            this(null);
        }

        public ActionRevert(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift V"));
            _tempStr = resHelper.getString("ActionRevert.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRevert.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRevert.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionRevert", "actionRevert_actionPerformed", e);
        }
    }

    /**
     * output ActionAmountUnit class
     */     
    protected class ActionAmountUnit extends ItemAction {     
    
        public ActionAmountUnit()
        {
            this(null);
        }

        public ActionAmountUnit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAmountUnit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAmountUnit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAmountUnit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionAmountUnit", "actionAmountUnit_actionPerformed", e);
        }
    }

    /**
     * output ActionCloseInit class
     */     
    protected class ActionCloseInit extends ItemAction {     
    
        public ActionCloseInit()
        {
            this(null);
        }

        public ActionCloseInit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_closeinitialize"));
            _tempStr = resHelper.getString("ActionCloseInit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCloseInit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCloseInit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionCloseInit", "actionCloseInit_actionPerformed", e);
        }
    }

    /**
     * output ActionWorkFlowG class
     */     
    protected class ActionWorkFlowG extends ItemAction {     
    
        public ActionWorkFlowG()
        {
            this(null);
        }

        public ActionWorkFlowG(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionWorkFlowG.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWorkFlowG.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWorkFlowG.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAimCostZkyF7UI.this, "ActionWorkFlowG", "actionWorkFlowG_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "AimCostZkyF7UI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}