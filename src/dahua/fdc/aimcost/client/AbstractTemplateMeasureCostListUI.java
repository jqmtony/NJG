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
public abstract class AbstractTemplateMeasureCostListUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTemplateMeasureCostListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDButton btnOk;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel researchPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel aimPanel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable aimTable;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable researchTable;
    /**
     * output class constructor
     */
    public AbstractTemplateMeasureCostListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTemplateMeasureCostListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnOk = new com.kingdee.bos.ctrl.swing.KDButton();
        this.tabPanel = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.researchPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.aimPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.aimTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.researchTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.treeMain.setName("treeMain");
        this.kDSeparator3.setName("kDSeparator3");
        this.btnNo.setName("btnNo");
        this.btnOk.setName("btnOk");
        this.tabPanel.setName("tabPanel");
        this.researchPanel.setName("researchPanel");
        this.aimPanel.setName("aimPanel");
        this.aimTable.setName("aimTable");
        this.researchTable.setName("researchTable");
        // CoreUI
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(200);
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
        // tabPanel
        // researchPanel
        // aimPanel
        // aimTable		
        this.aimTable.setFormatXml(resHelper.getString("aimTable.formatXml"));
        this.aimTable.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    aimTable_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // researchTable		
        this.researchTable.setFormatXml(resHelper.getString("researchTable.formatXml"));
        this.researchTable.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    researchTable_tableClicked(e);
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
        kDSplitPane1.setBounds(new Rectangle(10, 10, 982, 546));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 982, 546, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator3.setBounds(new Rectangle(10, 574, 977, 10));
        this.add(kDSeparator3, new KDLayout.Constraints(10, 574, 977, 10, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnNo.setBounds(new Rectangle(876, 589, 73, 21));
        this.add(btnNo, new KDLayout.Constraints(876, 589, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        btnOk.setBounds(new Rectangle(800, 589, 73, 21));
        this.add(btnOk, new KDLayout.Constraints(800, 589, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(kDScrollPane1, "left");
        kDSplitPane1.add(tabPanel, "right");
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeMain, null);
        //tabPanel
        tabPanel.add(researchPanel, resHelper.getString("researchPanel.constraints"));
        tabPanel.add(aimPanel, resHelper.getString("aimPanel.constraints"));
        //researchPanel
researchPanel.setLayout(new BorderLayout(0, 0));        researchPanel.add(researchTable, BorderLayout.CENTER);
        //aimPanel
aimPanel.setLayout(new BorderLayout(0, 0));        aimPanel.add(aimTable, BorderLayout.CENTER);

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
        this.toolBar.add(btnPageSetup);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.aimcost.app.TemplateMeasureCostListUIHandler";
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
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output btnNo_actionPerformed method
     */
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnOk_actionPerformed method
     */
    protected void btnOk_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output aimTable_tableClicked method
     */
    protected void aimTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output researchTable_tableClicked method
     */
    protected void researchTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "TemplateMeasureCostListUI");
    }




}