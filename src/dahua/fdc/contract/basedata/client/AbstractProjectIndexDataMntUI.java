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
public abstract class AbstractProjectIndexDataMntUI extends com.kingdee.eas.fdc.basedata.client.FDCMntUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectIndexDataMntUI.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblEntries;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlSplit;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlLeftTree;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRight;
    protected com.kingdee.bos.ctrl.swing.KDContainer contContrList;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboProductType;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVerNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVerNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVerName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVerName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeProject;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTree treeTargetType;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDContainer contContrType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjStage;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboProjStage;
    /**
     * output class constructor
     */
    public AbstractProjectIndexDataMntUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectIndexDataMntUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionCalculator
        String _tempStr = null;
        actionCalculator.setEnabled(false);
        actionCalculator.setDaemonRun(false);

        actionCalculator.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F12"));
        _tempStr = resHelper.getString("ActionCalculator.SHORT_DESCRIPTION");
        actionCalculator.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCalculator.LONG_DESCRIPTION");
        actionCalculator.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCalculator.NAME");
        actionCalculator.putValue(ItemAction.NAME, _tempStr);
         this.actionCalculator.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSave
        actionSave.setEnabled(true);
        actionSave.setDaemonRun(false);

        actionSave.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
        actionSave.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
        actionSave.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.NAME");
        actionSave.putValue(ItemAction.NAME, _tempStr);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRefresh
        actionRefresh.setEnabled(true);
        actionRefresh.setDaemonRun(false);

        actionRefresh.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F5"));
        _tempStr = resHelper.getString("ActionRefresh.SHORT_DESCRIPTION");
        actionRefresh.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRefresh.LONG_DESCRIPTION");
        actionRefresh.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRefresh.NAME");
        actionRefresh.putValue(ItemAction.NAME, _tempStr);
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        actionAudit.setEnabled(true);
        actionAudit.setDaemonRun(false);

        actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl U"));
        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionAudit.setBindWorkFlow(true);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        actionUnAudit.setEnabled(true);
        actionUnAudit.setDaemonRun(false);

        actionUnAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tblEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.pnlSplit = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.pnlLeftTree = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlRight = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contContrList = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboProductType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contVerNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtVerNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contVerName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtVerName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCreator = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCreateTime = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAuditor = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contAuditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAuditDate = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.treeProject = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.treeTargetType = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contContrType = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contProjStage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboProjStage = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblEntries.setName("tblEntries");
        this.pnlSplit.setName("pnlSplit");
        this.pnlLeftTree.setName("pnlLeftTree");
        this.pnlRight.setName("pnlRight");
        this.contContrList.setName("contContrList");
        this.contProductType.setName("contProductType");
        this.comboProductType.setName("comboProductType");
        this.kDPanel1.setName("kDPanel1");
        this.contVerNo.setName("contVerNo");
        this.txtVerNo.setName("txtVerNo");
        this.contVerName.setName("contVerName");
        this.txtVerName.setName("txtVerName");
        this.contCreator.setName("contCreator");
        this.txtCreator.setName("txtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.txtCreateTime.setName("txtCreateTime");
        this.contAuditor.setName("contAuditor");
        this.txtAuditor.setName("txtAuditor");
        this.contAuditDate.setName("contAuditDate");
        this.txtAuditDate.setName("txtAuditDate");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.treeProject.setName("treeProject");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.treeTargetType.setName("treeTargetType");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.contProject.setName("contProject");
        this.contContrType.setName("contContrType");
        this.contProjStage.setName("contProjStage");
        this.comboProjStage.setName("comboProjStage");
        // CoreUI		
        this.menuTool.setVisible(false);		
        this.menuItemSave.setText(resHelper.getString("menuItemSave.text"));		
        this.menuItemSave.setToolTipText(resHelper.getString("menuItemSave.toolTipText"));		
        this.menuItemRefresh.setText(resHelper.getString("menuItemRefresh.text"));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));
        // tblEntries		
        this.tblEntries.setFormatXml(resHelper.getString("tblEntries.formatXml"));
        this.tblEntries.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblEntries_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // pnlSplit		
        this.pnlSplit.setOneTouchExpandable(true);		
        this.pnlSplit.setDividerLocation(250);
        // pnlLeftTree
        // pnlRight
        // contContrList		
        this.contContrList.setEnableActive(false);		
        this.contContrList.setTitle(resHelper.getString("contContrList.title"));
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);
        // comboProductType		
        this.comboProductType.setAutoscrolls(true);		
        this.comboProductType.setMaximumRowCount(6);
        this.comboProductType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboProductType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDPanel1
        // contVerNo		
        this.contVerNo.setBoundLabelText(resHelper.getString("contVerNo.boundLabelText"));		
        this.contVerNo.setBoundLabelLength(80);		
        this.contVerNo.setBoundLabelUnderline(true);
        // txtVerNo		
        this.txtVerNo.setEditable(false);
        // contVerName		
        this.contVerName.setBoundLabelText(resHelper.getString("contVerName.boundLabelText"));		
        this.contVerName.setBoundLabelLength(80);		
        this.contVerName.setBoundLabelUnderline(true);
        // txtVerName		
        this.txtVerName.setEditable(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(80);		
        this.contCreator.setBoundLabelUnderline(true);
        // txtCreator		
        this.txtCreator.setEditable(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(80);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // txtCreateTime		
        this.txtCreateTime.setEditable(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(80);		
        this.contAuditor.setBoundLabelUnderline(true);
        // txtAuditor		
        this.txtAuditor.setEditable(false);
        // contAuditDate		
        this.contAuditDate.setBoundLabelText(resHelper.getString("contAuditDate.boundLabelText"));		
        this.contAuditDate.setBoundLabelLength(80);		
        this.contAuditDate.setBoundLabelUnderline(true);
        // txtAuditDate		
        this.txtAuditDate.setEditable(false);
        // kDScrollPane1
        // treeProject		
        this.treeProject.setAutoscrolls(true);
        this.treeProject.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeProject_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDScrollPane2
        // treeTargetType		
        this.treeTargetType.setAutoscrolls(true);
        this.treeTargetType.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeTargetType_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDSplitPane1		
        this.kDSplitPane1.setOrientation(0);		
        this.kDSplitPane1.setDividerLocation(360);		
        this.kDSplitPane1.setOneTouchExpandable(true);
        // contProject		
        this.contProject.setTitle(resHelper.getString("contProject.title"));		
        this.contProject.setEnableActive(false);
        // contContrType		
        this.contContrType.setTitle(resHelper.getString("contContrType.title"));		
        this.contContrType.setEnableActive(false);
        // contProjStage		
        this.contProjStage.setBoundLabelText(resHelper.getString("contProjStage.boundLabelText"));		
        this.contProjStage.setBoundLabelLength(100);		
        this.contProjStage.setBoundLabelUnderline(true);
        // comboProjStage		
        this.comboProjStage.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.ProjectStageEnum").toArray());		
        this.comboProjStage.setAutoscrolls(true);		
        this.comboProjStage.setMaximumRowCount(6);
        this.comboProjStage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboProjStage_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
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
        pnlSplit.setBounds(new Rectangle(10, 10, 993, 609));
        this.add(pnlSplit, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlSplit
        pnlSplit.add(pnlLeftTree, "left");
        pnlSplit.add(pnlRight, "right");
        //pnlLeftTree
        pnlLeftTree.setLayout(new KDLayout());
        //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
        pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(0,0,1,1));        kDSplitPane1.setBounds(new Rectangle(0, 0, 250, 616));
        pnlLeftTree.add(kDSplitPane1, new KDLayout.Constraints(0, 0, 250, 616, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDSplitPane1
        kDSplitPane1.add(contProject, "top");
        kDSplitPane1.add(contContrType, "bottom");
        //contProject
contProject.getContentPane().setLayout(new BorderLayout(0, 0));        contProject.getContentPane().add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeProject, null);
        //contContrType
contContrType.getContentPane().setLayout(new BorderLayout(0, 0));        contContrType.getContentPane().add(kDScrollPane2, BorderLayout.CENTER);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(treeTargetType, null);
        //pnlRight
        pnlRight.setLayout(new KDLayout());
        //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
        pnlRight.putClientProperty("OriginalBounds", new Rectangle(0,0,1,1));        contContrList.setBounds(new Rectangle(0, 37, 733, 502));
        pnlRight.add(contContrList, new KDLayout.Constraints(0, 37, 733, 502, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contProductType.setBounds(new Rectangle(353, 8, 379, 19));
        pnlRight.add(contProductType, new KDLayout.Constraints(353, 8, 379, 19, 0));
        kDPanel1.setBounds(new Rectangle(1, 545, 732, 63));
        pnlRight.add(kDPanel1, new KDLayout.Constraints(1, 545, 732, 63, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        contProjStage.setBounds(new Rectangle(4, 8, 270, 19));
        pnlRight.add(contProjStage, new KDLayout.Constraints(4, 8, 270, 19, 0));
        //contContrList
contContrList.getContentPane().setLayout(new BorderLayout(0, 0));        contContrList.getContentPane().add(tblEntries, BorderLayout.CENTER);
        //contProductType
        contProductType.setBoundEditor(comboProductType);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(1, 545, 732, 63));        contVerNo.setBounds(new Rectangle(12, 10, 215, 19));
        kDPanel1.add(contVerNo, new KDLayout.Constraints(12, 10, 215, 19, 0));
        contVerName.setBounds(new Rectangle(12, 34, 215, 19));
        kDPanel1.add(contVerName, new KDLayout.Constraints(12, 34, 215, 19, 0));
        contCreator.setBounds(new Rectangle(257, 10, 215, 19));
        kDPanel1.add(contCreator, new KDLayout.Constraints(257, 10, 215, 19, 0));
        contCreateTime.setBounds(new Rectangle(257, 34, 215, 19));
        kDPanel1.add(contCreateTime, new KDLayout.Constraints(257, 34, 215, 19, 0));
        contAuditor.setBounds(new Rectangle(506, 10, 215, 19));
        kDPanel1.add(contAuditor, new KDLayout.Constraints(506, 10, 215, 19, 0));
        contAuditDate.setBounds(new Rectangle(507, 34, 215, 19));
        kDPanel1.add(contAuditDate, new KDLayout.Constraints(507, 34, 215, 19, 0));
        //contVerNo
        contVerNo.setBoundEditor(txtVerNo);
        //contVerName
        contVerName.setBoundEditor(txtVerName);
        //contCreator
        contCreator.setBoundEditor(txtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(txtCreateTime);
        //contAuditor
        contAuditor.setBoundEditor(txtAuditor);
        //contAuditDate
        contAuditDate.setBoundEditor(txtAuditDate);
        //contProjStage
        contProjStage.setBoundEditor(comboProjStage);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemRefresh);
        menuFile.add(menuItemAudit);
        menuFile.add(menuItemUnAudit);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnPageSetup);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.ProjectIndexDataMntUIHandler";
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
     * output tblEntries_editStopping method
     */
    protected void tblEntries_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output comboProductType_itemStateChanged method
     */
    protected void comboProductType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output treeProject_valueChanged method
     */
    protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output treeTargetType_valueChanged method
     */
    protected void treeTargetType_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output comboProjStage_itemStateChanged method
     */
    protected void comboProjStage_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    	

    /**
     * output actionCalculator_actionPerformed method
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    	

    /**
     * output actionRefresh_actionPerformed method
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "ProjectIndexDataMntUI");
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
        return com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.ContractBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.ContractBillInfo objectValue = new com.kingdee.eas.fdc.contract.ContractBillInfo();		
        return objectValue;
    }




}