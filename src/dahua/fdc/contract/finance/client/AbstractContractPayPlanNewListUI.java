/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractContractPayPlanNewListUI extends com.kingdee.eas.fdc.contract.client.ContractListBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractPayPlanNewListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer contBillList;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPayPlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditPayPlan;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemEditPayPlan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btn_Modify;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btn_Audit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btn_unAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btn_Submit;
    protected EntityViewInfo contractPayPlanNewQuery = null;
    protected IMetaDataPK contractPayPlanNewQueryPK;
    protected ActionEditPayPlan actionEditPayPlan = null;
    /**
     * output class constructor
     */
    public AbstractContractPayPlanNewListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractPayPlanNewListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractBillQuery");
        contractPayPlanNewQueryPK = new MetaDataPK("com.kingdee.eas.fdc.finance.app", "ContractPayPlanNewQuery");
        //actionEditPayPlan
        this.actionEditPayPlan = new ActionEditPayPlan(this);
        getActionManager().registerAction("actionEditPayPlan", actionEditPayPlan);
         this.actionEditPayPlan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contBillList = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblPayPlan = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnEditPayPlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemEditPayPlan = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btn_Modify = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btn_Audit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btn_unAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btn_Submit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contBillList.setName("contBillList");
        this.tblPayPlan.setName("tblPayPlan");
        this.btnEditPayPlan.setName("btnEditPayPlan");
        this.menuItemEditPayPlan.setName("menuItemEditPayPlan");
        this.btn_Modify.setName("btn_Modify");
        this.btn_Audit.setName("btn_Audit");
        this.btn_unAudit.setName("btn_unAudit");
        this.btn_Submit.setName("btn_Submit");
        // CoreUI		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","bookedDate","period.number","state","hasSettled","contractType.name","number","name","currency.name","originalAmount","amount","partB.name","contractSourceId.name","signDate","landDeveloper.name","partC.name","costProperty","contractPropert","entrys.id","currency.id","currency.precision"});

		
        this.kDSplitPane2.setDividerLocation(300);		
        this.kDSplitPane2.setOneTouchExpandable(true);		
        this.kDSplitPane2.setDividerSize(10);
        // contBillList		
        this.contBillList.setEnableActive(false);		
        this.contBillList.setTitle(resHelper.getString("contBillList.title"));		
        this.contBillList.setToolTipText(resHelper.getString("contBillList.toolTipText"));
        // tblPayPlan
        this.tblPayPlan.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblPayPlan_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblPayPlan.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblPayPlan_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnEditPayPlan
        this.btnEditPayPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditPayPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditPayPlan.setText(resHelper.getString("btnEditPayPlan.text"));		
        this.btnEditPayPlan.setToolTipText(resHelper.getString("btnEditPayPlan.toolTipText"));
        // menuItemEditPayPlan
        this.menuItemEditPayPlan.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditPayPlan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemEditPayPlan.setText(resHelper.getString("menuItemEditPayPlan.text"));		
        this.menuItemEditPayPlan.setMnemonic(82);		
        this.menuItemEditPayPlan.setToolTipText(resHelper.getString("menuItemEditPayPlan.toolTipText"));
        // btn_Modify		
        this.btn_Modify.setText(resHelper.getString("btn_Modify.text"));		
        this.btn_Modify.setToolTipText(resHelper.getString("btn_Modify.toolTipText"));		
        this.btn_Modify.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));		
        this.btn_Modify.setEnabled(false);
        this.btn_Modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btn_Modify_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btn_Audit		
        this.btn_Audit.setText(resHelper.getString("btn_Audit.text"));		
        this.btn_Audit.setToolTipText(resHelper.getString("btn_Audit.toolTipText"));		
        this.btn_Audit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));		
        this.btn_Audit.setEnabled(false);
        this.btn_Audit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btn_Audit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btn_unAudit		
        this.btn_unAudit.setText(resHelper.getString("btn_unAudit.text"));		
        this.btn_unAudit.setToolTipText(resHelper.getString("btn_unAudit.toolTipText"));		
        this.btn_unAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));		
        this.btn_unAudit.setEnabled(false);
        this.btn_unAudit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btn_unAudit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btn_Submit		
        this.btn_Submit.setText(resHelper.getString("btn_Submit.text"));		
        this.btn_Submit.setToolTipText(resHelper.getString("btn_Submit.toolTipText"));		
        this.btn_Submit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_submit"));
        this.btn_Submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btn_Submit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
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
        pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(0,0,1,1));        kDSplitPane1.setBounds(new Rectangle(-1, 3, 250, 606));
        pnlLeftTree.add(kDSplitPane1, new KDLayout.Constraints(-1, 3, 250, 606, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
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
        kDScrollPane2.getViewport().add(treeContractType, null);
        //pnlRight
        pnlRight.setLayout(new KDLayout());
        //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
        pnlRight.putClientProperty("OriginalBounds", new Rectangle(0,0,1,1));        kDSplitPane2.setBounds(new Rectangle(0, 1, 733, 608));
        pnlRight.add(kDSplitPane2, new KDLayout.Constraints(0, 1, 733, 608, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDSplitPane2
        kDSplitPane2.add(contContrList, "top");
        kDSplitPane2.add(contBillList, "bottom");
        //contContrList
contContrList.getContentPane().setLayout(new BorderLayout(0, 0));        contContrList.getContentPane().add(tblMain, BorderLayout.CENTER);
        //contBillList
contBillList.getContentPane().setLayout(new BorderLayout(0, 0));        contBillList.getContentPane().add(tblPayPlan, BorderLayout.CENTER);

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
        menuEdit.add(menuItemEditPayPlan);
        menuEdit.add(menuItemEdit);
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
        menuBiz.add(menuItemSetRespite);
        menuBiz.add(menuItemCancelRespite);
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
        this.toolBar.add(btnEditPayPlan);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btn_Submit);
        this.toolBar.add(btn_Audit);
        this.toolBar.add(btn_unAudit);
        this.toolBar.add(btn_Modify);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnSetRespite);
        this.toolBar.add(btnCancelRespite);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.ContractPayPlanNewListUIHandler";
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
     * output setDataObject method
     */
    public void setDataObject(String key, IObjectValue dataObject)
    {
        super.setDataObject(key, dataObject);
        if (key.equalsIgnoreCase("contractPayPlanNewQuery")) {
            this.contractPayPlanNewQuery = (EntityViewInfo)dataObject;
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
     * output tblPayPlan_tableClicked method
     */
    protected void tblPayPlan_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblPayPlan_tableSelectChanged method
     */
    protected void tblPayPlan_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output btn_Modify_actionPerformed method
     */
    protected void btn_Modify_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btn_Audit_actionPerformed method
     */
    protected void btn_Audit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btn_unAudit_actionPerformed method
     */
    protected void btn_unAudit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btn_Submit_actionPerformed method
     */
    protected void btn_Submit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("currency.name"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("currency.id"));
        sic.add(new SelectorItemInfo("currency.precision"));
        return sic;
    }        
    	

    /**
     * output actionEditPayPlan_actionPerformed method
     */
    public void actionEditPayPlan_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionEditPayPlan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditPayPlan() {
    	return false;
    }

    /**
     * output ActionEditPayPlan class
     */     
    protected class ActionEditPayPlan extends ItemAction {     
    
        public ActionEditPayPlan()
        {
            this(null);
        }

        public ActionEditPayPlan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl R"));
            _tempStr = resHelper.getString("ActionEditPayPlan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditPayPlan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditPayPlan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractPayPlanNewListUI.this, "ActionEditPayPlan", "actionEditPayPlan_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "ContractPayPlanNewListUI");
    }




}