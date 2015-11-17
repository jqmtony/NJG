/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

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
public abstract class AbstractProgrammingUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProgrammingUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlHide;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeMainView;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labelState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labelDisplayVersion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDContainer conProgramming;
    protected com.kingdee.bos.ctrl.swing.KDContainer pnlCostAccount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntries;
    protected com.kingdee.bos.ctrl.swing.KDLabel labelExplain;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtCostAccount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtState;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDisplayVersion;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtYjDays;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCostVersionInfo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtYjDesign;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtYjCost;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtYjProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtYjMaterial;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersionGroup;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAimCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblCostVersion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersion;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker1;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersionGroup;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAllAimCost;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAimCost;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSaleArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtAimCoustVersion;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtVersion;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnModify;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRefresh;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportProject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewHistoryList;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRefresh;
    protected javax.swing.JPopupMenu.Separator separator4;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExport;
    protected com.kingdee.eas.fdc.contract.programming.ProgrammingInfo editData = null;
    protected ActionImport actionImport = null;
    protected ActionExportPro actionExportPro = null;
    protected ActionRefresh actionRefresh = null;
    protected ActionModify actionModify = null;
    protected ActionHistoryVersion actionHistoryVersion = null;
    protected ActioinImportProject actioinImportProject = null;
    /**
     * output class constructor
     */
    public AbstractProgrammingUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractProgrammingUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionImport
        this.actionImport = new ActionImport(this);
        getActionManager().registerAction("actionImport", actionImport);
         this.actionImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportPro
        this.actionExportPro = new ActionExportPro(this);
        getActionManager().registerAction("actionExportPro", actionExportPro);
         this.actionExportPro.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRefresh
        this.actionRefresh = new ActionRefresh(this);
        getActionManager().registerAction("actionRefresh", actionRefresh);
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionModify
        this.actionModify = new ActionModify(this);
        getActionManager().registerAction("actionModify", actionModify);
         this.actionModify.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionHistoryVersion
        this.actionHistoryVersion = new ActionHistoryVersion(this);
        getActionManager().registerAction("actionHistoryVersion", actionHistoryVersion);
         this.actionHistoryVersion.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actioinImportProject
        this.actioinImportProject = new ActioinImportProject(this);
        getActionManager().registerAction("actioinImportProject", actioinImportProject);
         this.actioinImportProject.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.pnlHide = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.treeMainView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labelState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labelDisplayVersion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conProgramming = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.pnlCostAccount = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.labelExplain = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kdtCostAccount = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtState = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDisplayVersion = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtYjDays = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCostVersionInfo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtYjDesign = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtYjCost = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtYjProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtYjMaterial = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVersionGroup = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAimCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblCostVersion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVersion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDDatePicker2 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDDatePicker1 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtBuildArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtVersionGroup = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAllAimCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtAimCost = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSaleArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAimCoustVersion = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtVersion = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.btnModify = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRefresh = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportProject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewHistoryList = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemRefresh = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.separator4 = new javax.swing.JPopupMenu.Separator();
        this.menuItemImport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.pnlHide.setName("pnlHide");
        this.treeMainView.setName("treeMainView");
        this.kDPanel1.setName("kDPanel1");
        this.treeMain.setName("treeMain");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contProject.setName("contProject");
        this.labelState.setName("labelState");
        this.labelDisplayVersion.setName("labelDisplayVersion");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.conProgramming.setName("conProgramming");
        this.pnlCostAccount.setName("pnlCostAccount");
        this.kdtEntries.setName("kdtEntries");
        this.labelExplain.setName("labelExplain");
        this.kdtCostAccount.setName("kdtCostAccount");
        this.txtProjectName.setName("txtProjectName");
        this.txtState.setName("txtState");
        this.txtDisplayVersion.setName("txtDisplayVersion");
        this.txtYjDays.setName("txtYjDays");
        this.txtCostVersionInfo.setName("txtCostVersionInfo");
        this.prmtYjDesign.setName("prmtYjDesign");
        this.prmtYjCost.setName("prmtYjCost");
        this.prmtYjProject.setName("prmtYjProject");
        this.prmtYjMaterial.setName("prmtYjMaterial");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contNumber.setName("contNumber");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.contVersionGroup.setName("contVersionGroup");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.contAimCost.setName("contAimCost");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.contName.setName("contName");
        this.lblCostVersion.setName("lblCostVersion");
        this.contVersion.setName("contVersion");
        this.kDDatePicker2.setName("kDDatePicker2");
        this.txtNumber.setName("txtNumber");
        this.kDDatePicker1.setName("kDDatePicker1");
        this.txtBuildArea.setName("txtBuildArea");
        this.txtVersionGroup.setName("txtVersionGroup");
        this.txtAllAimCost.setName("txtAllAimCost");
        this.prmtAimCost.setName("prmtAimCost");
        this.txtSaleArea.setName("txtSaleArea");
        this.txtName.setName("txtName");
        this.txtAimCoustVersion.setName("txtAimCoustVersion");
        this.txtVersion.setName("txtVersion");
        this.btnModify.setName("btnModify");
        this.btnRefresh.setName("btnRefresh");
        this.btnImport.setName("btnImport");
        this.btnImportProject.setName("btnImportProject");
        this.btnExport.setName("btnExport");
        this.btnViewHistoryList.setName("btnViewHistoryList");
        this.menuItemRefresh.setName("menuItemRefresh");
        this.separator4.setName("separator4");
        this.menuItemImport.setName("menuItemImport");
        this.menuItemExport.setName("menuItemExport");
        // CoreUI		
        this.btnAddNew.setVisible(false);		
        this.btnAddNew.setEnabled(false);		
        this.btnEdit.setText(resHelper.getString("btnEdit.text"));		
        this.btnEdit.setToolTipText(resHelper.getString("btnEdit.toolTipText"));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));		
        this.btnCopy.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.btnRemove.setEnabled(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemAddNew.setEnabled(false);		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));		
        this.menuItemSubmit.setToolTipText(resHelper.getString("menuItemSubmit.toolTipText"));		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrint.setEnabled(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuItemCopy.setEnabled(false);		
        this.menuItemFirst.setVisible(false);		
        this.menuItemFirst.setEnabled(false);		
        this.menuItemPre.setVisible(false);		
        this.menuItemNext.setVisible(false);		
        this.menuItemLast.setVisible(false);		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.btnAttachment.setToolTipText(resHelper.getString("btnAttachment.toolTipText"));		
        this.menuItemRemove.setVisible(false);		
        this.menuItemRemove.setEnabled(false);		
        this.MenuItemAttachment.setText(resHelper.getString("MenuItemAttachment.text"));		
        this.MenuItemAttachment.setToolTipText(resHelper.getString("MenuItemAttachment.toolTipText"));		
        this.btnAddLine.setVisible(false);		
        this.btnAddLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnRemoveLine.setEnabled(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnAuditResult.setEnabled(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCreateFrom.setEnabled(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemCopyFrom.setEnabled(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnMultiapprove.setEnabled(false);		
        this.menuTable1.setVisible(false);		
        this.menuTable1.setEnabled(false);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(250);		
        this.kDSplitPane1.setOneTouchExpandable(true);
        // pnlHide
        // treeMainView		
        this.treeMainView.setShowButton(false);		
        this.treeMainView.setTitle(resHelper.getString("treeMainView.title"));
        // kDPanel1
        // treeMain
        this.treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDTabbedPane1
        this.kDTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDTabbedPane1_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // labelState		
        this.labelState.setBoundLabelText(resHelper.getString("labelState.boundLabelText"));		
        this.labelState.setBoundLabelLength(100);		
        this.labelState.setBoundLabelUnderline(true);
        // labelDisplayVersion		
        this.labelDisplayVersion.setBoundLabelText(resHelper.getString("labelDisplayVersion.boundLabelText"));		
        this.labelDisplayVersion.setBoundLabelLength(100);		
        this.labelDisplayVersion.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(100);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);
        // conProgramming		
        this.conProgramming.setTitle(resHelper.getString("conProgramming.title"));
        // pnlCostAccount		
        this.pnlCostAccount.setTitle(resHelper.getString("pnlCostAccount.title"));
        // kdtEntries
		String kdtEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection locked=\"true\" hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:Protection locked=\"true\" hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:Protection locked=\"true\" hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol17\"><c:Protection locked=\"true\" hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:Protection locked=\"true\" hidden=\"true\" /><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol19\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol24\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol25\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol26\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol27\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol28\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol30\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol31\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol32\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol33\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol42\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"iscse\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"name\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"2\" /><t:Column t:key=\"hyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"workContent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"supMaterial\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"inviteWay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"inviteOrg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"amount\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"reservedChangeRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"controlAmount\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"occurAmount\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"isCiting\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"signUpAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"changeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"settleAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"balance\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"controlBalance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"attachment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"buildPerSquare\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"soldPerSquare\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"citeVersion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"headNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"longName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"sortNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"isHasPlan\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" /><t:Column t:key=\"estimateAwardStartDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"estimateAwardEndDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /><t:Column t:key=\"inviteMode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" t:styleID=\"sCol32\" /><t:Column t:key=\"jobType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" t:styleID=\"sCol33\" /><t:Column t:key=\"contractRange\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" /><t:Column t:key=\"priceWay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" /><t:Column t:key=\"sendPage\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" /><t:Column t:key=\"sgDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" /><t:Column t:key=\"csDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" /><t:Column t:key=\"csendDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"41\" /><t:Column t:key=\"groupTempId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol42\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{iscse}</t:Cell><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{hyType}</t:Cell><t:Cell>$Resource{workContent}</t:Cell><t:Cell>$Resource{supMaterial}</t:Cell><t:Cell>$Resource{inviteWay}</t:Cell><t:Cell>$Resource{inviteOrg}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{reservedChangeRate}</t:Cell><t:Cell>$Resource{controlAmount}</t:Cell><t:Cell>$Resource{occurAmount}</t:Cell><t:Cell>$Resource{isCiting}</t:Cell><t:Cell>$Resource{signUpAmount}</t:Cell><t:Cell>$Resource{changeAmount}</t:Cell><t:Cell>$Resource{settleAmount}</t:Cell><t:Cell>$Resource{balance}</t:Cell><t:Cell>$Resource{controlBalance}</t:Cell><t:Cell>$Resource{attachment}</t:Cell><t:Cell>$Resource{buildPerSquare}</t:Cell><t:Cell>$Resource{soldPerSquare}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{citeVersion}</t:Cell><t:Cell>$Resource{headNumber}</t:Cell><t:Cell>$Resource{longName}</t:Cell><t:Cell>$Resource{sortNumber}</t:Cell><t:Cell>$Resource{isHasPlan}</t:Cell><t:Cell>$Resource{estimateAwardStartDate}</t:Cell><t:Cell>$Resource{estimateAwardEndDate}</t:Cell><t:Cell>$Resource{inviteMode}</t:Cell><t:Cell>$Resource{jobType}</t:Cell><t:Cell>$Resource{contractRange}</t:Cell><t:Cell>$Resource{priceWay}</t:Cell><t:Cell>$Resource{sendPage}</t:Cell><t:Cell>$Resource{sgDate}</t:Cell><t:Cell>$Resource{csDate}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{csendDate}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{groupTempId}</t:Cell><t:Cell /><t:Cell>$Resource{iscse_Row2}</t:Cell><t:Cell>$Resource{longNumber_Row2}</t:Cell><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell>$Resource{name_Row2}</t:Cell><t:Cell>$Resource{hyType_Row2}</t:Cell><t:Cell>$Resource{workContent_Row2}</t:Cell><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell /><t:Cell>$Resource{supMaterial_Row2}</t:Cell><t:Cell>$Resource{inviteWay_Row2}</t:Cell><t:Cell>$Resource{inviteOrg_Row2}</t:Cell><t:Cell>$Resource{amount_Row2}</t:Cell><t:Cell>$Resource{reservedChangeRate_Row2}</t:Cell><t:Cell>$Resource{controlAmount_Row2}</t:Cell><t:Cell>$Resource{occurAmount_Row2}</t:Cell><t:Cell>$Resource{isCiting_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"8\" t:bottom=\"1\" t:right=\"8\" /><t:Block t:top=\"0\" t:left=\"9\" t:bottom=\"1\" t:right=\"9\" /><t:Block t:top=\"0\" t:left=\"10\" t:bottom=\"1\" t:right=\"10\" /><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"0\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"34\" t:bottom=\"1\" t:right=\"34\" /><t:Block t:top=\"0\" t:left=\"35\" t:bottom=\"1\" t:right=\"35\" /><t:Block t:top=\"0\" t:left=\"36\" t:bottom=\"1\" t:right=\"36\" /><t:Block t:top=\"0\" t:left=\"39\" t:bottom=\"0\" t:right=\"40\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntries.setFormatXml(resHelper.translateString("kdtEntries",kdtEntriesStrXML));
        this.kdtEntries.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    kdtEntries_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEntries.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEntries_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEntries.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntries_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntries_editStarted(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntries_editStarting(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntries.putBindContents("editData",new String[] {"iscse","longNumber","name","hyType","workContent","supMaterial","inviteWay","inviteOrg","amount","reservedChangeRate","controlAmount","","isCiting","signUpAmount","changeAmount","settleAmount","balance","controlBalance","attachment","buildPerSquare","soldPerSquare","description","id","number","level","citeVersion","parent.longNumber","displayName","sortNumber","isHasPlan","estimateAwardStartDate","estimateAwardEndDate","inviteMode","jobType","contractRange","priceWay","sendPage","sgtDate","contSignDate","startDate","endDate","csendDate","simpleName"});


        // labelExplain		
        this.labelExplain.setText(resHelper.getString("labelExplain.text"));
        // kdtCostAccount
		String kdtCostAccountStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>#,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"costNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"costName\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"aimCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"assigned\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"assigning\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"proName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"headNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"costAccount.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"costAccount.parent.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{costNumber}</t:Cell><t:Cell>$Resource{costName}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{assigned}</t:Cell><t:Cell>$Resource{assigning}</t:Cell><t:Cell>$Resource{proName}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{headNumber}</t:Cell><t:Cell>$Resource{costAccount.id}</t:Cell><t:Cell>$Resource{costAccount.parent.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtCostAccount.setFormatXml(resHelper.translateString("kdtCostAccount",kdtCostAccountStrXML));
        this.kdtCostAccount.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtCostAccount_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // txtProjectName		
        this.txtProjectName.setEnabled(false);
        // txtState		
        this.txtState.setEnabled(false);
        // txtDisplayVersion		
        this.txtDisplayVersion.setEnabled(false);
        // txtYjDays
        // txtCostVersionInfo		
        this.txtCostVersionInfo.setEnabled(false);
        // prmtYjDesign		
        this.prmtYjDesign.setDisplayFormat("$name$");		
        this.prmtYjDesign.setEditFormat("$number$");		
        this.prmtYjDesign.setCommitFormat("$number$");		
        this.prmtYjDesign.setQueryInfo("com.kingdee.eas.basedata.org.app.PositionQuery");
        // prmtYjCost		
        this.prmtYjCost.setCommitFormat("$number$");		
        this.prmtYjCost.setEditFormat("$number$");		
        this.prmtYjCost.setDisplayFormat("$name$");		
        this.prmtYjCost.setQueryInfo("com.kingdee.eas.basedata.org.app.PositionQuery");
        // prmtYjProject		
        this.prmtYjProject.setQueryInfo("com.kingdee.eas.basedata.org.app.PositionQuery");		
        this.prmtYjProject.setCommitFormat("$number$");		
        this.prmtYjProject.setDisplayFormat("$name$");		
        this.prmtYjProject.setEditFormat("$number$");
        // prmtYjMaterial		
        this.prmtYjMaterial.setDisplayFormat("$name$");		
        this.prmtYjMaterial.setEditFormat("$number$");		
        this.prmtYjMaterial.setQueryInfo("com.kingdee.eas.basedata.org.app.PositionQuery");		
        this.prmtYjMaterial.setCommitFormat("$number$");
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setEnabled(false);		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setEnabled(false);		
        this.contNumber.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setEnabled(false);		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setVisible(false);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setVisible(false);
        // contVersionGroup		
        this.contVersionGroup.setBoundLabelText(resHelper.getString("contVersionGroup.boundLabelText"));		
        this.contVersionGroup.setBoundLabelLength(100);		
        this.contVersionGroup.setBoundLabelUnderline(true);		
        this.contVersionGroup.setVisible(false);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setVisible(false);
        // contAimCost		
        this.contAimCost.setBoundLabelText(resHelper.getString("contAimCost.boundLabelText"));		
        this.contAimCost.setBoundLabelLength(100);		
        this.contAimCost.setBoundLabelUnderline(true);		
        this.contAimCost.setVisible(false);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setVisible(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);		
        this.contName.setVisible(false);
        // lblCostVersion		
        this.lblCostVersion.setBoundLabelText(resHelper.getString("lblCostVersion.boundLabelText"));		
        this.lblCostVersion.setBoundLabelLength(100);		
        this.lblCostVersion.setBoundLabelUnderline(true);		
        this.lblCostVersion.setVisible(false);
        // contVersion		
        this.contVersion.setBoundLabelText(resHelper.getString("contVersion.boundLabelText"));		
        this.contVersion.setBoundLabelLength(100);		
        this.contVersion.setBoundLabelUnderline(true);		
        this.contVersion.setVisible(false);
        // kDDatePicker2		
        this.kDDatePicker2.setVisible(false);		
        this.kDDatePicker2.setEnabled(false);
        // txtNumber
        // kDDatePicker1		
        this.kDDatePicker1.setVisible(false);		
        this.kDDatePicker1.setEnabled(false);
        // txtBuildArea
        // txtVersionGroup
        // txtAllAimCost
        // prmtAimCost		
        this.prmtAimCost.setQueryInfo("com.kingdee.eas.fdc.contract.programming.app.AIMCostF7Query");		
        this.prmtAimCost.setDisplayFormat("$versionName$");		
        this.prmtAimCost.setEditFormat("$versionNumber$");		
        this.prmtAimCost.setCommitFormat("$versionNumber$");
        // txtSaleArea
        // txtName		
        this.txtName.setRequired(true);
        // txtAimCoustVersion		
        this.txtAimCoustVersion.setDataType(6);		
        this.txtAimCoustVersion.setEnabled(false);		
        this.txtAimCoustVersion.setPrecision(1);
        // txtVersion		
        this.txtVersion.setDataType(6);		
        this.txtVersion.setPrecision(1);		
        this.txtVersion.setEnabled(false);
        // btnModify
        this.btnModify.setAction((IItemAction)ActionProxyFactory.getProxy(actionModify, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnModify.setText(resHelper.getString("btnModify.text"));		
        this.btnModify.setToolTipText(resHelper.getString("btnModify.toolTipText"));		
        this.btnModify.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_duizsetting"));
        this.btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnModify_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRefresh
        this.btnRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefresh.setText(resHelper.getString("btnRefresh.text"));		
        this.btnRefresh.setToolTipText(resHelper.getString("btnRefresh.toolTipText"));
        // btnImport
        this.btnImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImport.setText(resHelper.getString("btnImport.text"));		
        this.btnImport.setToolTipText(resHelper.getString("btnImport.toolTipText"));
        // btnImportProject
        this.btnImportProject.setAction((IItemAction)ActionProxyFactory.getProxy(actioinImportProject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportProject.setText(resHelper.getString("btnImportProject.text"));		
        this.btnImportProject.setToolTipText(resHelper.getString("btnImportProject.toolTipText"));
        // btnExport
        this.btnExport.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportPro, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExport.setText(resHelper.getString("btnExport.text"));		
        this.btnExport.setToolTipText(resHelper.getString("btnExport.toolTipText"));
        // btnViewHistoryList
        this.btnViewHistoryList.setAction((IItemAction)ActionProxyFactory.getProxy(actionHistoryVersion, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewHistoryList.setText(resHelper.getString("btnViewHistoryList.text"));		
        this.btnViewHistoryList.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_historyedition"));		
        this.btnViewHistoryList.setToolTipText(resHelper.getString("btnViewHistoryList.toolTipText"));
        // menuItemRefresh
        this.menuItemRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRefresh.setText(resHelper.getString("menuItemRefresh.text"));		
        this.menuItemRefresh.setToolTipText(resHelper.getString("menuItemRefresh.toolTipText"));
        // separator4
        // menuItemImport
        this.menuItemImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImport.setText(resHelper.getString("menuItemImport.text"));		
        this.menuItemImport.setToolTipText(resHelper.getString("menuItemImport.toolTipText"));
        // menuItemExport
        this.menuItemExport.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportPro, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExport.setText(resHelper.getString("menuItemExport.text"));		
        this.menuItemExport.setToolTipText(resHelper.getString("menuItemExport.toolTipText"));
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
        kDSplitPane1.setBounds(new Rectangle(5, 6, 1003, 619));
        this.add(kDSplitPane1, new KDLayout.Constraints(5, 6, 1003, 619, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlHide.setBounds(new Rectangle(60, 777, 306, 369));
        this.add(pnlHide, new KDLayout.Constraints(60, 777, 306, 369, 0));
        //kDSplitPane1
        kDSplitPane1.add(treeMainView, "left");
        kDSplitPane1.add(kDPanel1, "right");
        //treeMainView
        treeMainView.setTree(treeMain);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 742, 618));        kDTabbedPane1.setBounds(new Rectangle(4, 70, 735, 545));
        kDPanel1.add(kDTabbedPane1, new KDLayout.Constraints(4, 70, 735, 545, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contProject.setBounds(new Rectangle(6, 2, 220, 19));
        kDPanel1.add(contProject, new KDLayout.Constraints(6, 2, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labelState.setBounds(new Rectangle(518, 2, 220, 19));
        kDPanel1.add(labelState, new KDLayout.Constraints(518, 2, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        labelDisplayVersion.setBounds(new Rectangle(262, 3, 220, 19));
        kDPanel1.add(labelDisplayVersion, new KDLayout.Constraints(262, 3, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(262, 25, 220, 19));
        kDPanel1.add(kDLabelContainer6, new KDLayout.Constraints(262, 25, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer8.setBounds(new Rectangle(6, 25, 220, 19));
        kDPanel1.add(kDLabelContainer8, new KDLayout.Constraints(6, 25, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer11.setBounds(new Rectangle(518, 24, 220, 19));
        kDPanel1.add(kDLabelContainer11, new KDLayout.Constraints(518, 24, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer12.setBounds(new Rectangle(6, 47, 220, 19));
        kDPanel1.add(kDLabelContainer12, new KDLayout.Constraints(6, 47, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer13.setBounds(new Rectangle(262, 47, 220, 19));
        kDPanel1.add(kDLabelContainer13, new KDLayout.Constraints(262, 47, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer14.setBounds(new Rectangle(518, 47, 220, 19));
        kDPanel1.add(kDLabelContainer14, new KDLayout.Constraints(518, 47, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDTabbedPane1
        kDTabbedPane1.add(conProgramming, resHelper.getString("conProgramming.constraints"));
        kDTabbedPane1.add(pnlCostAccount, resHelper.getString("pnlCostAccount.constraints"));
        //conProgramming
conProgramming.getContentPane().setLayout(new BorderLayout(0, 0));        conProgramming.getContentPane().add(kdtEntries, BorderLayout.CENTER);
        conProgramming.getContentPane().add(labelExplain, BorderLayout.SOUTH);
        //pnlCostAccount
pnlCostAccount.getContentPane().setLayout(new BorderLayout(0, 0));        pnlCostAccount.getContentPane().add(kdtCostAccount, BorderLayout.CENTER);
        //contProject
        contProject.setBoundEditor(txtProjectName);
        //labelState
        labelState.setBoundEditor(txtState);
        //labelDisplayVersion
        labelDisplayVersion.setBoundEditor(txtDisplayVersion);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtYjDays);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtCostVersionInfo);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(prmtYjDesign);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(prmtYjCost);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(prmtYjProject);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(prmtYjMaterial);
        //pnlHide
        pnlHide.setLayout(null);        kDLabelContainer2.setBounds(new Rectangle(14, 7, 270, 19));
        pnlHide.add(kDLabelContainer2, null);
        contNumber.setBounds(new Rectangle(14, 36, 270, 19));
        pnlHide.add(contNumber, null);
        kDLabelContainer1.setBounds(new Rectangle(14, 65, 270, 19));
        pnlHide.add(kDLabelContainer1, null);
        kDLabelContainer3.setBounds(new Rectangle(14, 94, 270, 19));
        pnlHide.add(kDLabelContainer3, null);
        contVersionGroup.setBounds(new Rectangle(14, 123, 270, 19));
        pnlHide.add(contVersionGroup, null);
        kDLabelContainer5.setBounds(new Rectangle(14, 152, 270, 19));
        pnlHide.add(kDLabelContainer5, null);
        contAimCost.setBounds(new Rectangle(14, 181, 270, 19));
        pnlHide.add(contAimCost, null);
        kDLabelContainer4.setBounds(new Rectangle(14, 210, 270, 19));
        pnlHide.add(kDLabelContainer4, null);
        contName.setBounds(new Rectangle(14, 239, 270, 19));
        pnlHide.add(contName, null);
        lblCostVersion.setBounds(new Rectangle(14, 273, 270, 19));
        pnlHide.add(lblCostVersion, null);
        contVersion.setBounds(new Rectangle(15, 304, 220, 20));
        pnlHide.add(contVersion, null);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(kDDatePicker2);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(kDDatePicker1);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtBuildArea);
        //contVersionGroup
        contVersionGroup.setBoundEditor(txtVersionGroup);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtAllAimCost);
        //contAimCost
        contAimCost.setBoundEditor(prmtAimCost);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtSaleArea);
        //contName
        contName.setBoundEditor(txtName);
        //lblCostVersion
        lblCostVersion.setBoundEditor(txtAimCoustVersion);
        //contVersion
        contVersion.setBoundEditor(txtVersion);

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
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemRefresh);
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(separator4);
        menuBiz.add(menuItemImport);
        menuBiz.add(menuItemExport);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnModify);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnImport);
        this.toolBar.add(btnImportProject);
        this.toolBar.add(btnExport);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnViewHistoryList);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnReset);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("entries.longNumber", String.class, this.kdtEntries, "longNumber.text");
		dataBinder.registerBinding("entries.name", String.class, this.kdtEntries, "name.text");
		dataBinder.registerBinding("entries", com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo.class, this.kdtEntries, "userObject");
		dataBinder.registerBinding("entries.amount", java.math.BigDecimal.class, this.kdtEntries, "amount.text");
		dataBinder.registerBinding("entries.controlAmount", java.math.BigDecimal.class, this.kdtEntries, "controlAmount.text");
		dataBinder.registerBinding("entries.balance", java.math.BigDecimal.class, this.kdtEntries, "balance.text");
		dataBinder.registerBinding("entries.controlBalance", java.math.BigDecimal.class, this.kdtEntries, "controlBalance.text");
		dataBinder.registerBinding("entries.signUpAmount", java.math.BigDecimal.class, this.kdtEntries, "signUpAmount.text");
		dataBinder.registerBinding("entries.changeAmount", java.math.BigDecimal.class, this.kdtEntries, "changeAmount.text");
		dataBinder.registerBinding("entries.settleAmount", java.math.BigDecimal.class, this.kdtEntries, "settleAmount.text");
		dataBinder.registerBinding("entries.citeVersion", int.class, this.kdtEntries, "citeVersion.text");
		dataBinder.registerBinding("entries.isCiting", boolean.class, this.kdtEntries, "isCiting.text");
		dataBinder.registerBinding("entries.attachment", String.class, this.kdtEntries, "attachment.text");
		dataBinder.registerBinding("entries.description", String.class, this.kdtEntries, "remark.text");
		dataBinder.registerBinding("entries.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntries, "id.text");
		dataBinder.registerBinding("entries.number", String.class, this.kdtEntries, "number.text");
		dataBinder.registerBinding("entries.level", int.class, this.kdtEntries, "level.text");
		dataBinder.registerBinding("entries.parent.longNumber", String.class, this.kdtEntries, "headNumber.text");
		dataBinder.registerBinding("entries.displayName", String.class, this.kdtEntries, "longName.text");
		dataBinder.registerBinding("entries.sortNumber", int.class, this.kdtEntries, "sortNumber.text");
		dataBinder.registerBinding("entries.workContent", String.class, this.kdtEntries, "workContent.text");
		dataBinder.registerBinding("entries.supMaterial", String.class, this.kdtEntries, "supMaterial.text");
		dataBinder.registerBinding("entries.inviteWay", com.kingdee.eas.fdc.invite.InviteFormEnum.class, this.kdtEntries, "inviteWay.text");
		dataBinder.registerBinding("entries.buildPerSquare", java.math.BigDecimal.class, this.kdtEntries, "buildPerSquare.text");
		dataBinder.registerBinding("entries.soldPerSquare", java.math.BigDecimal.class, this.kdtEntries, "soldPerSquare.text");
		dataBinder.registerBinding("entries.inviteOrg", com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo.class, this.kdtEntries, "inviteOrg.text");
		dataBinder.registerBinding("entries.isHasPlan", boolean.class, this.kdtEntries, "isHasPlan.text");
		dataBinder.registerBinding("entries.reservedChangeRate", java.math.BigDecimal.class, this.kdtEntries, "reservedChangeRate.text");
		dataBinder.registerBinding("entries.estimateAwardStartDate", java.util.Date.class, this.kdtEntries, "estimateAwardStartDate.text");
		dataBinder.registerBinding("entries.estimateAwardEndDate", java.util.Date.class, this.kdtEntries, "estimateAwardEndDate.text");
		dataBinder.registerBinding("entries.inviteMode", com.kingdee.eas.fdc.invite.InviteModeInfo.class, this.kdtEntries, "inviteMode.text");
		dataBinder.registerBinding("entries.jobType", com.kingdee.eas.fdc.basedata.JobTypeInfo.class, this.kdtEntries, "jobType.text");
		dataBinder.registerBinding("entries.contractRange", String.class, this.kdtEntries, "contractRange.text");
		dataBinder.registerBinding("entries.priceWay", com.kingdee.eas.fdc.contract.programming.PriceWay.class, this.kdtEntries, "priceWay.text");
		dataBinder.registerBinding("entries.sendPage", com.kingdee.eas.fdc.contract.programming.SendContWay.class, this.kdtEntries, "sendPage.text");
		dataBinder.registerBinding("entries.sgtDate", java.util.Date.class, this.kdtEntries, "sgDate.text");
		dataBinder.registerBinding("entries.contSignDate", java.util.Date.class, this.kdtEntries, "csDate.text");
		dataBinder.registerBinding("entries.startDate", java.util.Date.class, this.kdtEntries, "startDate.text");
		dataBinder.registerBinding("entries.endDate", java.util.Date.class, this.kdtEntries, "endDate.text");
		dataBinder.registerBinding("entries.csendDate", java.util.Date.class, this.kdtEntries, "csendDate.text");
		dataBinder.registerBinding("entries.hyType", com.kingdee.eas.fdc.contract.programming.PcTypeInfo.class, this.kdtEntries, "hyType.text");
		dataBinder.registerBinding("entries.iscse", boolean.class, this.kdtEntries, "iscse.text");
		dataBinder.registerBinding("entries.simpleName", String.class, this.kdtEntries, "groupTempId.text");
		dataBinder.registerBinding("project.name", String.class, this.txtProjectName, "text");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.txtState, "text");
		dataBinder.registerBinding("yjDays", int.class, this.txtYjDays, "value");
		dataBinder.registerBinding("yjDesign", com.kingdee.eas.basedata.org.PositionInfo.class, this.prmtYjDesign, "data");
		dataBinder.registerBinding("yjCost", com.kingdee.eas.basedata.org.PositionInfo.class, this.prmtYjCost, "data");
		dataBinder.registerBinding("yjProject", com.kingdee.eas.basedata.org.PositionInfo.class, this.prmtYjProject, "data");
		dataBinder.registerBinding("yjMaterial", com.kingdee.eas.basedata.org.PositionInfo.class, this.prmtYjMaterial, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDatePicker2, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDatePicker1, "value");
		dataBinder.registerBinding("versionGroup", String.class, this.txtVersionGroup, "text");
		dataBinder.registerBinding("aimCost", com.kingdee.eas.fdc.aimcost.AimCostInfo.class, this.prmtAimCost, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("version", java.math.BigDecimal.class, this.txtVersion, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.programming.app.ProgrammingUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.programming.ProgrammingInfo)ov;
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
		getValidateHelper().registerBindProperty("entries.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.controlAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.balance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.controlBalance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.signUpAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.changeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.settleAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.citeVersion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.isCiting", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.attachment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.parent.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.displayName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.sortNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.workContent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.supMaterial", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.inviteWay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.buildPerSquare", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.soldPerSquare", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.inviteOrg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.isHasPlan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.reservedChangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.estimateAwardStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.estimateAwardEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.inviteMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.jobType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.contractRange", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.priceWay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.sendPage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.sgtDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.contSignDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.csendDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.hyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.iscse", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yjDays", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yjDesign", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yjCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yjProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yjMaterial", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("versionGroup", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aimCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("version", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output kDTabbedPane1_stateChanged method
     */
    protected void kDTabbedPane1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntries_activeCellChanged method
     */
    protected void kdtEntries_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output kdtEntries_tableClicked method
     */
    protected void kdtEntries_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtEntries_editStopped method
     */
    protected void kdtEntries_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntries_editStarted method
     */
    protected void kdtEntries_editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntries_editStarting method
     */
    protected void kdtEntries_editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtCostAccount_tableClicked method
     */
    protected void kdtCostAccount_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnModify_actionPerformed method
     */
    protected void btnModify_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
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
    	sic.add(new SelectorItemInfo("entries.longNumber"));
    	sic.add(new SelectorItemInfo("entries.name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("entries.number"));
		}
    	sic.add(new SelectorItemInfo("entries.amount"));
    	sic.add(new SelectorItemInfo("entries.controlAmount"));
    	sic.add(new SelectorItemInfo("entries.balance"));
    	sic.add(new SelectorItemInfo("entries.controlBalance"));
    	sic.add(new SelectorItemInfo("entries.signUpAmount"));
    	sic.add(new SelectorItemInfo("entries.changeAmount"));
    	sic.add(new SelectorItemInfo("entries.settleAmount"));
    	sic.add(new SelectorItemInfo("entries.citeVersion"));
    	sic.add(new SelectorItemInfo("entries.isCiting"));
    	sic.add(new SelectorItemInfo("entries.attachment"));
    	sic.add(new SelectorItemInfo("entries.description"));
    	sic.add(new SelectorItemInfo("entries.id"));
    	sic.add(new SelectorItemInfo("entries.level"));
    	sic.add(new SelectorItemInfo("entries.parent.longNumber"));
    	sic.add(new SelectorItemInfo("entries.displayName"));
    	sic.add(new SelectorItemInfo("entries.sortNumber"));
    	sic.add(new SelectorItemInfo("entries.workContent"));
    	sic.add(new SelectorItemInfo("entries.supMaterial"));
    	sic.add(new SelectorItemInfo("entries.inviteWay"));
    	sic.add(new SelectorItemInfo("entries.buildPerSquare"));
    	sic.add(new SelectorItemInfo("entries.soldPerSquare"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.inviteOrg.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.inviteOrg.id"));
			sic.add(new SelectorItemInfo("entries.inviteOrg.name"));
        	sic.add(new SelectorItemInfo("entries.inviteOrg.number"));
		}
    	sic.add(new SelectorItemInfo("entries.isHasPlan"));
    	sic.add(new SelectorItemInfo("entries.reservedChangeRate"));
    	sic.add(new SelectorItemInfo("entries.estimateAwardStartDate"));
    	sic.add(new SelectorItemInfo("entries.estimateAwardEndDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.inviteMode.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.inviteMode.id"));
			sic.add(new SelectorItemInfo("entries.inviteMode.name"));
        	sic.add(new SelectorItemInfo("entries.inviteMode.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.jobType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.jobType.id"));
			sic.add(new SelectorItemInfo("entries.jobType.name"));
        	sic.add(new SelectorItemInfo("entries.jobType.number"));
		}
    	sic.add(new SelectorItemInfo("entries.contractRange"));
    	sic.add(new SelectorItemInfo("entries.priceWay"));
    	sic.add(new SelectorItemInfo("entries.sendPage"));
    	sic.add(new SelectorItemInfo("entries.sgtDate"));
    	sic.add(new SelectorItemInfo("entries.contSignDate"));
    	sic.add(new SelectorItemInfo("entries.startDate"));
    	sic.add(new SelectorItemInfo("entries.endDate"));
    	sic.add(new SelectorItemInfo("entries.csendDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.hyType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.hyType.id"));
			sic.add(new SelectorItemInfo("entries.hyType.hyType"));
        	sic.add(new SelectorItemInfo("entries.hyType.number"));
		}
    	sic.add(new SelectorItemInfo("entries.iscse"));
    	sic.add(new SelectorItemInfo("entries.simpleName"));
        sic.add(new SelectorItemInfo("project.name"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("yjDays"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("yjDesign.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("yjDesign.id"));
        	sic.add(new SelectorItemInfo("yjDesign.number"));
        	sic.add(new SelectorItemInfo("yjDesign.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("yjCost.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("yjCost.id"));
        	sic.add(new SelectorItemInfo("yjCost.number"));
        	sic.add(new SelectorItemInfo("yjCost.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("yjProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("yjProject.id"));
        	sic.add(new SelectorItemInfo("yjProject.number"));
        	sic.add(new SelectorItemInfo("yjProject.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("yjMaterial.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("yjMaterial.id"));
        	sic.add(new SelectorItemInfo("yjMaterial.number"));
        	sic.add(new SelectorItemInfo("yjMaterial.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("versionGroup"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("aimCost.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("aimCost.id"));
        	sic.add(new SelectorItemInfo("aimCost.number"));
        	sic.add(new SelectorItemInfo("aimCost.versionName"));
        	sic.add(new SelectorItemInfo("aimCost.versionNumber"));
		}
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("version"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionImport_actionPerformed method
     */
    public void actionImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportPro_actionPerformed method
     */
    public void actionExportPro_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRefresh_actionPerformed method
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionModify_actionPerformed method
     */
    public void actionModify_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionHistoryVersion_actionPerformed method
     */
    public void actionHistoryVersion_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actioinImportProject_actionPerformed method
     */
    public void actioinImportProject_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionImport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImport() {
    	return false;
    }
	public RequestContext prepareActionExportPro(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportPro() {
    	return false;
    }
	public RequestContext prepareActionRefresh(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefresh() {
    	return false;
    }
	public RequestContext prepareActionModify(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionModify() {
    	return false;
    }
	public RequestContext prepareActionHistoryVersion(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHistoryVersion() {
    	return false;
    }
	public RequestContext prepareActioinImportProject(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActioinImportProject() {
    	return false;
    }

    /**
     * output ActionImport class
     */     
    protected class ActionImport extends ItemAction {     
    
        public ActionImport()
        {
            this(null);
        }

        public ActionImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingUI.this, "ActionImport", "actionImport_actionPerformed", e);
        }
    }

    /**
     * output ActionExportPro class
     */     
    protected class ActionExportPro extends ItemAction {     
    
        public ActionExportPro()
        {
            this(null);
        }

        public ActionExportPro(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExportPro.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportPro.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportPro.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingUI.this, "ActionExportPro", "actionExportPro_actionPerformed", e);
        }
    }

    /**
     * output ActionRefresh class
     */     
    protected class ActionRefresh extends ItemAction {     
    
        public ActionRefresh()
        {
            this(null);
        }

        public ActionRefresh(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRefresh.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingUI.this, "ActionRefresh", "actionRefresh_actionPerformed", e);
        }
    }

    /**
     * output ActionModify class
     */     
    protected class ActionModify extends ItemAction {     
    
        public ActionModify()
        {
            this(null);
        }

        public ActionModify(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionModify.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModify.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionModify.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingUI.this, "ActionModify", "actionModify_actionPerformed", e);
        }
    }

    /**
     * output ActionHistoryVersion class
     */     
    protected class ActionHistoryVersion extends ItemAction {     
    
        public ActionHistoryVersion()
        {
            this(null);
        }

        public ActionHistoryVersion(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionHistoryVersion.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHistoryVersion.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHistoryVersion.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingUI.this, "ActionHistoryVersion", "actionHistoryVersion_actionPerformed", e);
        }
    }

    /**
     * output ActioinImportProject class
     */     
    protected class ActioinImportProject extends ItemAction {     
    
        public ActioinImportProject()
        {
            this(null);
        }

        public ActioinImportProject(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActioinImportProject.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActioinImportProject.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActioinImportProject.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingUI.this, "ActioinImportProject", "actioinImportProject_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.programming.client", "ProgrammingUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}