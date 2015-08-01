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
public abstract class AbstractProjectIndexDataVerMntUI extends com.kingdee.eas.fdc.basedata.client.ProjectIndexDataMntUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectIndexDataVerMntUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFirst;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPre;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnNext;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLast;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuView;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemFirst;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPre;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemNext;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemLast;
    protected ActionFirst actionFirst = null;
    protected ActionNext actionNext = null;
    protected ActionPre actionPre = null;
    protected ActionLast actionLast = null;
    /**
     * output class constructor
     */
    public AbstractProjectIndexDataVerMntUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectIndexDataVerMntUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionFirst
        this.actionFirst = new ActionFirst(this);
        getActionManager().registerAction("actionFirst", actionFirst);
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNext
        this.actionNext = new ActionNext(this);
        getActionManager().registerAction("actionNext", actionNext);
         this.actionNext.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPre
        this.actionPre = new ActionPre(this);
        getActionManager().registerAction("actionPre", actionPre);
         this.actionPre.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionLast
        this.actionLast = new ActionLast(this);
        getActionManager().registerAction("actionLast", actionLast);
         this.actionLast.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnFirst = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPre = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnNext = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnLast = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuView = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemFirst = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPre = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemNext = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemLast = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnFirst.setName("btnFirst");
        this.btnPre.setName("btnPre");
        this.btnNext.setName("btnNext");
        this.btnLast.setName("btnLast");
        this.menuView.setName("menuView");
        this.menuItemFirst.setName("menuItemFirst");
        this.menuItemPre.setName("menuItemPre");
        this.menuItemNext.setName("menuItemNext");
        this.menuItemLast.setName("menuItemLast");
        // CoreUI		
        this.menuTool.setVisible(false);		
        this.menuItemSave.setText(resHelper.getString("menuItemSave.text"));		
        this.menuItemRefresh.setText(resHelper.getString("menuItemRefresh.text"));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.tblEntries.setFormatXml(resHelper.getString("tblEntries.formatXml"));
        this.tblEntries.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });

        

        // btnFirst
        this.btnFirst.setAction((IItemAction)ActionProxyFactory.getProxy(actionFirst, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnFirst.setText(resHelper.getString("btnFirst.text"));		
        this.btnFirst.setToolTipText(resHelper.getString("btnFirst.toolTipText"));
        // btnPre
        this.btnPre.setAction((IItemAction)ActionProxyFactory.getProxy(actionPre, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPre.setText(resHelper.getString("btnPre.text"));		
        this.btnPre.setToolTipText(resHelper.getString("btnPre.toolTipText"));
        // btnNext
        this.btnNext.setAction((IItemAction)ActionProxyFactory.getProxy(actionNext, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnNext.setText(resHelper.getString("btnNext.text"));		
        this.btnNext.setToolTipText(resHelper.getString("btnNext.toolTipText"));
        // btnLast
        this.btnLast.setAction((IItemAction)ActionProxyFactory.getProxy(actionLast, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLast.setText(resHelper.getString("btnLast.text"));		
        this.btnLast.setToolTipText(resHelper.getString("btnLast.toolTipText"));
        // menuView		
        this.menuView.setText(resHelper.getString("menuView.text"));		
        this.menuView.setToolTipText(resHelper.getString("menuView.toolTipText"));
        // menuItemFirst
        this.menuItemFirst.setAction((IItemAction)ActionProxyFactory.getProxy(actionFirst, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemFirst.setText(resHelper.getString("menuItemFirst.text"));		
        this.menuItemFirst.setToolTipText(resHelper.getString("menuItemFirst.toolTipText"));
        // menuItemPre
        this.menuItemPre.setAction((IItemAction)ActionProxyFactory.getProxy(actionPre, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPre.setText(resHelper.getString("menuItemPre.text"));		
        this.menuItemPre.setToolTipText(resHelper.getString("menuItemPre.toolTipText"));
        // menuItemNext
        this.menuItemNext.setAction((IItemAction)ActionProxyFactory.getProxy(actionNext, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemNext.setText(resHelper.getString("menuItemNext.text"));		
        this.menuItemNext.setToolTipText(resHelper.getString("menuItemNext.toolTipText"));
        // menuItemLast
        this.menuItemLast.setAction((IItemAction)ActionProxyFactory.getProxy(actionLast, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemLast.setText(resHelper.getString("menuItemLast.text"));		
        this.menuItemLast.setToolTipText(resHelper.getString("menuItemLast.toolTipText"));
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
        this.menuBar.add(menuView);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemRefresh);
        menuFile.add(menuItemAudit);
        menuFile.add(menuItemUnAudit);
        menuFile.add(menuItemSave);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
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
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.ProjectIndexDataVerMntUIHandler";
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
     * output actionFirst_actionPerformed method
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNext_actionPerformed method
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPre_actionPerformed method
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionLast_actionPerformed method
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionFirst class
     */
    protected class ActionFirst extends ItemAction
    {
        public ActionFirst()
        {
            this(null);
        }

        public ActionFirst(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionFirst.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFirst.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFirst.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectIndexDataVerMntUI.this, "ActionFirst", "actionFirst_actionPerformed", e);
        }
    }

    /**
     * output ActionNext class
     */
    protected class ActionNext extends ItemAction
    {
        public ActionNext()
        {
            this(null);
        }

        public ActionNext(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionNext.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNext.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNext.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectIndexDataVerMntUI.this, "ActionNext", "actionNext_actionPerformed", e);
        }
    }

    /**
     * output ActionPre class
     */
    protected class ActionPre extends ItemAction
    {
        public ActionPre()
        {
            this(null);
        }

        public ActionPre(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPre.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPre.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPre.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectIndexDataVerMntUI.this, "ActionPre", "actionPre_actionPerformed", e);
        }
    }

    /**
     * output ActionLast class
     */
    protected class ActionLast extends ItemAction
    {
        public ActionLast()
        {
            this(null);
        }

        public ActionLast(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionLast.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLast.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLast.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectIndexDataVerMntUI.this, "ActionLast", "actionLast_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "ProjectIndexDataVerMntUI");
    }




}