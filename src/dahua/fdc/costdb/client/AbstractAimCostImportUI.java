/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

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
public abstract class AbstractAimCostImportUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAimCostImportUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSubmit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPreVersion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnNextVersion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRecense;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExpression;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlAimCost;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSumArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSumArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCellArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFirstVersion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLastVersion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersionNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersionName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersionNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersionName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnVersionInfo;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRevert;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApportion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemNextVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemLastVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAddRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRevert;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemFirstVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPreVersion;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemApportion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAmountUnit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemVersionInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpression;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSubmit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRecense;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportData;
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
    /**
     * output class constructor
     */
    public AbstractAimCostImportUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAimCostImportUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
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
        this.btnAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPreVersion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnNextVersion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRecense = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExpression = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.pnlAimCost = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.pnlDescription = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contSumArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSumArea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCellArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSellArea = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCreator = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAuditor = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contAuditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCreateTime = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAuditDate = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnFirstVersion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnLastVersion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contVersionNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVersionName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtVersionNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtVersionName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnVersionInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRevert = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnApportion = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemNextVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemLastVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAddRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDeleteRow = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRevert = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemFirstVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPreVersion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemApportion = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnAmountUnit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemVersionInfo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExpression = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSubmit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRecense = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnImportData = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddRow.setName("btnAddRow");
        this.btnDeleteRow.setName("btnDeleteRow");
        this.btnSubmit.setName("btnSubmit");
        this.btnPreVersion.setName("btnPreVersion");
        this.btnNextVersion.setName("btnNextVersion");
        this.btnRecense.setName("btnRecense");
        this.btnExpression.setName("btnExpression");
        this.pnlAimCost.setName("pnlAimCost");
        this.pnlDescription.setName("pnlDescription");
        this.contSumArea.setName("contSumArea");
        this.txtSumArea.setName("txtSumArea");
        this.contCellArea.setName("contCellArea");
        this.txtSellArea.setName("txtSellArea");
        this.contCreator.setName("contCreator");
        this.txtCreator.setName("txtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contAuditor.setName("contAuditor");
        this.txtAuditor.setName("txtAuditor");
        this.contAuditDate.setName("contAuditDate");
        this.txtCreateTime.setName("txtCreateTime");
        this.txtAuditDate.setName("txtAuditDate");
        this.btnFirstVersion.setName("btnFirstVersion");
        this.btnLastVersion.setName("btnLastVersion");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.contVersionNumber.setName("contVersionNumber");
        this.contVersionName.setName("contVersionName");
        this.txtVersionNumber.setName("txtVersionNumber");
        this.txtVersionName.setName("txtVersionName");
        this.btnVersionInfo.setName("btnVersionInfo");
        this.btnRevert.setName("btnRevert");
        this.btnApportion.setName("btnApportion");
        this.menuItemNextVersion.setName("menuItemNextVersion");
        this.menuItemLastVersion.setName("menuItemLastVersion");
        this.menuItemAddRow.setName("menuItemAddRow");
        this.menuItemDeleteRow.setName("menuItemDeleteRow");
        this.menuItemRevert.setName("menuItemRevert");
        this.menuItemFirstVersion.setName("menuItemFirstVersion");
        this.menuItemPreVersion.setName("menuItemPreVersion");
        this.menuItemAudit.setName("menuItemAudit");
        this.menuItemUnAudit.setName("menuItemUnAudit");
        this.menuItemApportion.setName("menuItemApportion");
        this.btnAmountUnit.setName("btnAmountUnit");
        this.menuItemVersionInfo.setName("menuItemVersionInfo");
        this.menuItemExpression.setName("menuItemExpression");
        this.menuItemSubmit.setName("menuItemSubmit");
        this.menuItemRecense.setName("menuItemRecense");
        this.btnImportData.setName("btnImportData");
        // CoreUI		
        this.kDSeparator1.setVisible(false);		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
        this.tblMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"","","","","","","","","","","","","",""});

		
        this.btnAddNew.setVisible(false);		
        this.btnView.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnQuery.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemImportData.setVisible(true);		
        this.menuEdit.setVisible(false);		
        this.menuView.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.separatorFW2.setVisible(true);		
        this.pnlMain.setDividerLocation(180);		
        this.pnlMain.setOneTouchExpandable(true);
        // btnAddRow
        this.btnAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRow.setText(resHelper.getString("btnAddRow.text"));		
        this.btnAddRow.setToolTipText(resHelper.getString("btnAddRow.toolTipText"));		
        this.btnAddRow.setVisible(false);
        // btnDeleteRow
        this.btnDeleteRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeleteRow.setText(resHelper.getString("btnDeleteRow.text"));		
        this.btnDeleteRow.setToolTipText(resHelper.getString("btnDeleteRow.toolTipText"));		
        this.btnDeleteRow.setVisible(false);
        // btnSubmit
        this.btnSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));		
        this.btnSubmit.setVisible(false);		
        this.btnSubmit.setEnabled(false);
        // btnPreVersion
        this.btnPreVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionPreVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPreVersion.setText(resHelper.getString("btnPreVersion.text"));		
        this.btnPreVersion.setToolTipText(resHelper.getString("btnPreVersion.toolTipText"));		
        this.btnPreVersion.setVisible(false);
        // btnNextVersion
        this.btnNextVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionNextVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnNextVersion.setText(resHelper.getString("btnNextVersion.text"));		
        this.btnNextVersion.setToolTipText(resHelper.getString("btnNextVersion.toolTipText"));		
        this.btnNextVersion.setVisible(false);
        // btnRecense
        this.btnRecense.setAction((IItemAction)ActionProxyFactory.getProxy(actionRecense, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRecense.setText(resHelper.getString("btnRecense.text"));		
        this.btnRecense.setToolTipText(resHelper.getString("btnRecense.toolTipText"));		
        this.btnRecense.setVisible(false);		
        this.btnRecense.setEnabled(false);
        // btnExpression
        this.btnExpression.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpression, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExpression.setText(resHelper.getString("btnExpression.text"));		
        this.btnExpression.setToolTipText(resHelper.getString("btnExpression.toolTipText"));		
        this.btnExpression.setVisible(false);
        // pnlAimCost		
        this.pnlAimCost.setOrientation(0);		
        this.pnlAimCost.setDividerSize(8);		
        this.pnlAimCost.setDividerLocation(500);
        // pnlDescription
        // contSumArea		
        this.contSumArea.setBoundLabelText(resHelper.getString("contSumArea.boundLabelText"));		
        this.contSumArea.setBoundLabelLength(80);		
        this.contSumArea.setBoundLabelUnderline(true);
        // txtSumArea		
        this.txtSumArea.setEnabled(false);
        // contCellArea		
        this.contCellArea.setBoundLabelText(resHelper.getString("contCellArea.boundLabelText"));		
        this.contCellArea.setBoundLabelLength(80);		
        this.contCellArea.setBoundLabelUnderline(true);
        // txtSellArea		
        this.txtSellArea.setEnabled(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(80);		
        this.contCreator.setBoundLabelUnderline(true);
        // txtCreator		
        this.txtCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(80);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(80);		
        this.contAuditor.setBoundLabelUnderline(true);
        // txtAuditor		
        this.txtAuditor.setEnabled(false);
        // contAuditDate		
        this.contAuditDate.setBoundLabelText(resHelper.getString("contAuditDate.boundLabelText"));		
        this.contAuditDate.setBoundLabelLength(80);		
        this.contAuditDate.setBoundLabelUnderline(true);
        // txtCreateTime		
        this.txtCreateTime.setEnabled(false);
        // txtAuditDate		
        this.txtAuditDate.setEnabled(false);
        // btnFirstVersion
        this.btnFirstVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionFirstVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFirstVersion.setText(resHelper.getString("btnFirstVersion.text"));		
        this.btnFirstVersion.setToolTipText(resHelper.getString("btnFirstVersion.toolTipText"));		
        this.btnFirstVersion.setVisible(false);
        // btnLastVersion
        this.btnLastVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionLastVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLastVersion.setText(resHelper.getString("btnLastVersion.text"));		
        this.btnLastVersion.setToolTipText(resHelper.getString("btnLastVersion.toolTipText"));		
        this.btnLastVersion.setVisible(false);
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));		
        this.btnAudit.setVisible(false);		
        this.btnAudit.setEnabled(false);
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setToolTipText(resHelper.getString("btnUnAudit.toolTipText"));		
        this.btnUnAudit.setVisible(false);		
        this.btnUnAudit.setEnabled(false);
        // contVersionNumber		
        this.contVersionNumber.setBoundLabelText(resHelper.getString("contVersionNumber.boundLabelText"));		
        this.contVersionNumber.setBoundLabelLength(80);		
        this.contVersionNumber.setBoundLabelUnderline(true);
        // contVersionName		
        this.contVersionName.setBoundLabelText(resHelper.getString("contVersionName.boundLabelText"));		
        this.contVersionName.setBoundLabelLength(80);		
        this.contVersionName.setBoundLabelUnderline(true);
        // txtVersionNumber		
        this.txtVersionNumber.setEnabled(false);
        // txtVersionName		
        this.txtVersionName.setMaxLength(80);
        // btnVersionInfo
        this.btnVersionInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionVersionInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnVersionInfo.setText(resHelper.getString("btnVersionInfo.text"));		
        this.btnVersionInfo.setToolTipText(resHelper.getString("btnVersionInfo.toolTipText"));		
        this.btnVersionInfo.setVisible(false);
        // btnRevert
        this.btnRevert.setAction((IItemAction)ActionProxyFactory.getProxy(actionRevert, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRevert.setText(resHelper.getString("btnRevert.text"));		
        this.btnRevert.setToolTipText(resHelper.getString("btnRevert.toolTipText"));		
        this.btnRevert.setVisible(false);
        // btnApportion
        this.btnApportion.setAction((IItemAction)ActionProxyFactory.getProxy(actionApportion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnApportion.setText(resHelper.getString("btnApportion.text"));		
        this.btnApportion.setToolTipText(resHelper.getString("btnApportion.toolTipText"));		
        this.btnApportion.setVisible(false);
        // menuItemNextVersion
        this.menuItemNextVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionNextVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemNextVersion.setText(resHelper.getString("menuItemNextVersion.text"));
        // menuItemLastVersion
        this.menuItemLastVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionLastVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemLastVersion.setText(resHelper.getString("menuItemLastVersion.text"));
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
        // menuItemFirstVersion
        this.menuItemFirstVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionFirstVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemFirstVersion.setText(resHelper.getString("menuItemFirstVersion.text"));
        // menuItemPreVersion
        this.menuItemPreVersion.setAction((IItemAction)ActionProxyFactory.getProxy(actionPreVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPreVersion.setText(resHelper.getString("menuItemPreVersion.text"));
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setVisible(false);
        // menuItemUnAudit
        this.menuItemUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setVisible(false);
        // menuItemApportion
        this.menuItemApportion.setAction((IItemAction)ActionProxyFactory.getProxy(actionApportion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemApportion.setText(resHelper.getString("menuItemApportion.text"));		
        this.menuItemApportion.setVisible(false);
        // btnAmountUnit		
        this.btnAmountUnit.setText(resHelper.getString("btnAmountUnit.text"));		
        this.btnAmountUnit.setVisible(false);
        // menuItemVersionInfo
        this.menuItemVersionInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionVersionInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemVersionInfo.setText(resHelper.getString("menuItemVersionInfo.text"));		
        this.menuItemVersionInfo.setToolTipText(resHelper.getString("menuItemVersionInfo.toolTipText"));
        // menuItemExpression
        this.menuItemExpression.setAction((IItemAction)ActionProxyFactory.getProxy(actionExpression, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpression.setText(resHelper.getString("menuItemExpression.text"));		
        this.menuItemExpression.setToolTipText(resHelper.getString("menuItemExpression.toolTipText"));
        // menuItemSubmit
        this.menuItemSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemSubmit.setToolTipText(resHelper.getString("menuItemSubmit.toolTipText"));		
        this.menuItemSubmit.setVisible(false);
        // menuItemRecense
        this.menuItemRecense.setAction((IItemAction)ActionProxyFactory.getProxy(actionRecense, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRecense.setText(resHelper.getString("menuItemRecense.text"));		
        this.menuItemRecense.setToolTipText(resHelper.getString("menuItemRecense.toolTipText"));		
        this.menuItemRecense.setVisible(false);
        // btnImportData
        this.btnImportData.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportData, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportData.setText(resHelper.getString("btnImportData.text"));		
        this.btnImportData.setToolTipText(resHelper.getString("btnImportData.toolTipText"));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        pnlMain.setBounds(new Rectangle(10, 10, 996, 580));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 996, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(pnlAimCost, "right");
        //treeView
        treeView.setTree(treeMain);
        //pnlAimCost
        pnlAimCost.add(tblMain, "top");
        pnlAimCost.add(pnlDescription, "bottom");
        //pnlDescription
        pnlDescription.setLayout(new KDLayout());
        //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
        pnlDescription.putClientProperty("OriginalBounds", new Rectangle(0,0,1,1));        contSumArea.setBounds(new Rectangle(11, 51, 169, 19));
        pnlDescription.add(contSumArea, new KDLayout.Constraints(11, 51, 169, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contCellArea.setBounds(new Rectangle(191, 51, 164, 19));
        pnlDescription.add(contCellArea, new KDLayout.Constraints(191, 51, 164, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contCreator.setBounds(new Rectangle(368, 14, 184, 19));
        pnlDescription.add(contCreator, new KDLayout.Constraints(368, 14, 184, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contCreateTime.setBounds(new Rectangle(561, 10, 184, 19));
        pnlDescription.add(contCreateTime, new KDLayout.Constraints(561, 10, 184, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contAuditor.setBounds(new Rectangle(368, 51, 184, 19));
        pnlDescription.add(contAuditor, new KDLayout.Constraints(368, 51, 184, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contAuditDate.setBounds(new Rectangle(561, 51, 184, 19));
        pnlDescription.add(contAuditDate, new KDLayout.Constraints(561, 51, 184, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contVersionNumber.setBounds(new Rectangle(11, 14, 169, 19));
        pnlDescription.add(contVersionNumber, new KDLayout.Constraints(11, 14, 169, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contVersionName.setBounds(new Rectangle(191, 14, 164, 19));
        pnlDescription.add(contVersionName, new KDLayout.Constraints(191, 14, 164, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        //contSumArea
        contSumArea.setBoundEditor(txtSumArea);
        //contCellArea
        contCellArea.setBoundEditor(txtSellArea);
        //contCreator
        contCreator.setBoundEditor(txtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(txtCreateTime);
        //contAuditor
        contAuditor.setBoundEditor(txtAuditor);
        //contAuditDate
        contAuditDate.setBoundEditor(txtAuditDate);
        //contVersionNumber
        contVersionNumber.setBoundEditor(txtVersionNumber);
        //contVersionName
        contVersionName.setBoundEditor(txtVersionName);

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
        this.toolBar.add(btnImportData);
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
        this.toolBar.add(btnRefresh);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnFirstVersion);
        this.toolBar.add(btnPreVersion);
        this.toolBar.add(btnNextVersion);
        this.toolBar.add(btnLastVersion);
        this.toolBar.add(btnAmountUnit);
        this.toolBar.add(btnAddRow);
        this.toolBar.add(btnDeleteRow);
        this.toolBar.add(btnRevert);
        this.toolBar.add(btnExpression);
        this.toolBar.add(btnVersionInfo);
        this.toolBar.add(btnApportion);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.costdb.app.AimCostImportUIHandler";
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
	 * ????????У??
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
        //write your code here
    }

    /**
     * output tblMain_editStopped method
     */
    protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
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
     * output ActionSubmit class
     */
    protected class ActionSubmit extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionSubmit", "actionSubmit_actionPerformed", e);
        }
    }

    /**
     * output ActionAddRow class
     */
    protected class ActionAddRow extends ItemAction
    {
        public ActionAddRow()
        {
            this(null);
        }

        public ActionAddRow(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift A"));
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionAddRow", "actionAddRow_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteRow class
     */
    protected class ActionDeleteRow extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionDeleteRow", "actionDeleteRow_actionPerformed", e);
        }
    }

    /**
     * output ActionPreVersion class
     */
    protected class ActionPreVersion extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionPreVersion", "actionPreVersion_actionPerformed", e);
        }
    }

    /**
     * output ActionNextVersion class
     */
    protected class ActionNextVersion extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionNextVersion", "actionNextVersion_actionPerformed", e);
        }
    }

    /**
     * output ActionRecense class
     */
    protected class ActionRecense extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionRecense", "actionRecense_actionPerformed", e);
        }
    }

    /**
     * output ActionExpression class
     */
    protected class ActionExpression extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionExpression", "actionExpression_actionPerformed", e);
        }
    }

    /**
     * output ActionFirstVersion class
     */
    protected class ActionFirstVersion extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionFirstVersion", "actionFirstVersion_actionPerformed", e);
        }
    }

    /**
     * output ActionLastVersion class
     */
    protected class ActionLastVersion extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionLastVersion", "actionLastVersion_actionPerformed", e);
        }
    }

    /**
     * output ActionAudit class
     */
    protected class ActionAudit extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAudit class
     */
    protected class ActionUnAudit extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionVersionInfo class
     */
    protected class ActionVersionInfo extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionVersionInfo", "actionVersionInfo_actionPerformed", e);
        }
    }

    /**
     * output ActionApportion class
     */
    protected class ActionApportion extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionApportion", "actionApportion_actionPerformed", e);
        }
    }

    /**
     * output ActionRevert class
     */
    protected class ActionRevert extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionRevert", "actionRevert_actionPerformed", e);
        }
    }

    /**
     * output ActionAmountUnit class
     */
    protected class ActionAmountUnit extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractAimCostImportUI.this, "ActionAmountUnit", "actionAmountUnit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.costdb.client", "AimCostImportUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}