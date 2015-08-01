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
public abstract class AbstractTemplateMeasureCostF7UI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTemplateMeasureCostF7UI.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDButton btnOk;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabPane;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlMeasure;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlDB;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuery;
    protected javax.swing.JToolBar.Separator separator1;
    protected ActionQuery actionQuery = null;
    /**
     * output class constructor
     */
    public AbstractTemplateMeasureCostF7UI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTemplateMeasureCostF7UI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionQuery
        this.actionQuery = new ActionQuery(this);
        getActionManager().registerAction("actionQuery", actionQuery);
         this.actionQuery.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnOk = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.tabPane = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.pnlMeasure = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlDB = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.btnQuery = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator1 = new javax.swing.JToolBar.Separator();
        this.tblMain.setName("tblMain");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.treeMain.setName("treeMain");
        this.kDSeparator3.setName("kDSeparator3");
        this.btnOk.setName("btnOk");
        this.btnNo.setName("btnNo");
        this.tabPane.setName("tabPane");
        this.pnlMeasure.setName("pnlMeasure");
        this.pnlDB.setName("pnlDB");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.btnQuery.setName("btnQuery");
        this.separator1.setName("separator1");
        // CoreUI		
        this.setPreferredSize(new Dimension(780,629));		
        this.setMinimumSize(new Dimension(780,629));		
        this.setMaximumSize(new Dimension(780,629));
        // tblMain		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
        this.tblMain.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblMain_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDScrollPane1
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
        // kDSeparator3
        // btnOk		
        this.btnOk.setText(resHelper.getString("btnOk.text"));
        this.btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnOk_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnNo		
        this.btnNo.setText(resHelper.getString("btnNo.text"));
        this.btnNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNo_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tabPane
        this.tabPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    tabPane_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pnlMeasure
        // pnlDB
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(200);
        // btnQuery
        this.btnQuery.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuery, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuery.setText(resHelper.getString("btnQuery.text"));		
        this.btnQuery.setToolTipText(resHelper.getString("btnQuery.toolTipText"));
        // separator1
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 800, 629));
        this.setLayout(null);
        kDSeparator3.setBounds(new Rectangle(10, 588, 773, 10));
        this.add(kDSeparator3, null);
        btnOk.setBounds(new Rectangle(618, 595, 73, 21));
        this.add(btnOk, null);
        btnNo.setBounds(new Rectangle(694, 595, 73, 21));
        this.add(btnNo, null);
        tabPane.setBounds(new Rectangle(10, 10, 783, 569));
        this.add(tabPane, null);
        //tabPane
        tabPane.add(pnlMeasure, resHelper.getString("pnlMeasure.constraints"));
        tabPane.add(pnlDB, resHelper.getString("pnlDB.constraints"));
        //pnlMeasure
pnlMeasure.setLayout(new BorderLayout(0, 0));        pnlMeasure.add(kDSplitPane1, BorderLayout.CENTER);
        //kDSplitPane1
        kDSplitPane1.add(tblMain, "right");
        kDSplitPane1.add(kDScrollPane1, "left");
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeMain, null);
        pnlDB.setLayout(new KDLayout());
        //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
        pnlDB.putClientProperty("OriginalBounds", new Rectangle(0,0,1,1));
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
        this.toolBar.add(separator1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnQuery);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.aimcost.app.TemplateMeasureCostF7UIHandler";
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
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output btnOk_actionPerformed method
     */
    protected void btnOk_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnNo_actionPerformed method
     */
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tabPane_stateChanged method
     */
    protected void tabPane_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    	

    /**
     * output actionQuery_actionPerformed method
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionQuery class
     */
    protected class ActionQuery extends ItemAction
    {
        public ActionQuery()
        {
            this(null);
        }

        public ActionQuery(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionQuery.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuery.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuery.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTemplateMeasureCostF7UI.this, "ActionQuery", "actionQuery_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "TemplateMeasureCostF7UI");
    }




}