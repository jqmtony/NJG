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
public abstract class AbstractCostAccountNewAssignUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCostAccountNewAssignUI.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCompanyForAssigned;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbAssigned;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbNotAssigned;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAllSelect;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAllDisSelect;
    protected com.kingdee.bos.ctrl.swing.KDContainer lblCuForAssigned;
    protected com.kingdee.bos.ctrl.swing.KDContainer lblAccountForAssigned;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelectAllOU;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDisselectAllOU;
    protected com.kingdee.bos.ctrl.swing.KDButton btnQueryOrg;
    protected com.kingdee.bos.ctrl.swing.KDButton btnQueryCostAccount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAccountForAssigned;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbxCompanyOrgQueryField;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbxCostAccountQueryField;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompanyOrgNumAndName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCostAccountNumAndName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton selectall;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton modfiys;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAssign;
    protected ActionAssign actionAssign = null;
    protected ActionSetAcctCtrlForRefer actionSetAcctCtrlForRefer = null;
    protected ActionAssigned actionAssigned = null;
    protected ActionNotAssigned actionNotAssigned = null;
    protected ActionAllSelect actionAllSelect = null;
    protected ActionAllDisselect actionAllDisselect = null;
    protected ActionSelectUpper actionSelectUpper = null;
    protected ActionDisSelectUpper actionDisSelectUpper = null;
    protected ActionSelectAllOU actionSelectAllOU = null;
    protected ActionDisselectAllOU actionDisselectAllOU = null;
    protected ActionAssignToLowerCU actionAssignToLowerCU = null;
    protected ActionAssignToCompany actionAssignToCompany = null;
    protected ActionDisplayAll actionDisplayAll = null;
    protected ActionSelectAllCA actionSelectAllCA = null;
    protected ActionDisSelectAllCA actionDisSelectAllCA = null;
    /**
     * output class constructor
     */
    public AbstractCostAccountNewAssignUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCostAccountNewAssignUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAssign
        this.actionAssign = new ActionAssign(this);
        getActionManager().registerAction("actionAssign", actionAssign);
        //actionSetAcctCtrlForRefer
        this.actionSetAcctCtrlForRefer = new ActionSetAcctCtrlForRefer(this);
        getActionManager().registerAction("actionSetAcctCtrlForRefer", actionSetAcctCtrlForRefer);
        //actionAssigned
        this.actionAssigned = new ActionAssigned(this);
        getActionManager().registerAction("actionAssigned", actionAssigned);
         this.actionAssigned.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAssigned.addService(new com.kingdee.eas.framework.client.service.WorkFlowService());
        //actionNotAssigned
        this.actionNotAssigned = new ActionNotAssigned(this);
        getActionManager().registerAction("actionNotAssigned", actionNotAssigned);
         this.actionNotAssigned.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionNotAssigned.addService(new com.kingdee.eas.framework.client.service.WorkFlowService());
        //actionAllSelect
        this.actionAllSelect = new ActionAllSelect(this);
        getActionManager().registerAction("actionAllSelect", actionAllSelect);
         this.actionAllSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAllSelect.addService(new com.kingdee.eas.framework.client.service.WorkFlowService());
        //actionAllDisselect
        this.actionAllDisselect = new ActionAllDisselect(this);
        getActionManager().registerAction("actionAllDisselect", actionAllDisselect);
         this.actionAllDisselect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAllDisselect.addService(new com.kingdee.eas.framework.client.service.WorkFlowService());
        //actionSelectUpper
        this.actionSelectUpper = new ActionSelectUpper(this);
        getActionManager().registerAction("actionSelectUpper", actionSelectUpper);
         this.actionSelectUpper.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisSelectUpper
        this.actionDisSelectUpper = new ActionDisSelectUpper(this);
        getActionManager().registerAction("actionDisSelectUpper", actionDisSelectUpper);
         this.actionDisSelectUpper.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSelectAllOU
        this.actionSelectAllOU = new ActionSelectAllOU(this);
        getActionManager().registerAction("actionSelectAllOU", actionSelectAllOU);
         this.actionSelectAllOU.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisselectAllOU
        this.actionDisselectAllOU = new ActionDisselectAllOU(this);
        getActionManager().registerAction("actionDisselectAllOU", actionDisselectAllOU);
         this.actionDisselectAllOU.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAssignToLowerCU
        this.actionAssignToLowerCU = new ActionAssignToLowerCU(this);
        getActionManager().registerAction("actionAssignToLowerCU", actionAssignToLowerCU);
         this.actionAssignToLowerCU.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAssignToCompany
        this.actionAssignToCompany = new ActionAssignToCompany(this);
        getActionManager().registerAction("actionAssignToCompany", actionAssignToCompany);
         this.actionAssignToCompany.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisplayAll
        this.actionDisplayAll = new ActionDisplayAll(this);
        getActionManager().registerAction("actionDisplayAll", actionDisplayAll);
         this.actionDisplayAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSelectAllCA
        this.actionSelectAllCA = new ActionSelectAllCA(this);
        getActionManager().registerAction("actionSelectAllCA", actionSelectAllCA);
         this.actionSelectAllCA.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDisSelectAllCA
        this.actionDisSelectAllCA = new ActionDisSelectAllCA(this);
        getActionManager().registerAction("actionDisSelectAllCA", actionDisSelectAllCA);
         this.actionDisSelectAllCA.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tblCompanyForAssigned = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.rbAssigned = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbNotAssigned = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnAllSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAllDisSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.lblCuForAssigned = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.lblAccountForAssigned = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnSelectAllOU = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDisselectAllOU = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnQueryOrg = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnQueryCostAccount = new com.kingdee.bos.ctrl.swing.KDButton();
        this.tblAccountForAssigned = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.cbxCompanyOrgQueryField = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbxCostAccountQueryField = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtCompanyOrgNumAndName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCostAccountNumAndName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.selectall = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.modfiys = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblCompanyForAssigned.setName("tblCompanyForAssigned");
        this.rbAssigned.setName("rbAssigned");
        this.rbNotAssigned.setName("rbNotAssigned");
        this.btnAllSelect.setName("btnAllSelect");
        this.btnAllDisSelect.setName("btnAllDisSelect");
        this.lblCuForAssigned.setName("lblCuForAssigned");
        this.lblAccountForAssigned.setName("lblAccountForAssigned");
        this.btnSelectAllOU.setName("btnSelectAllOU");
        this.btnDisselectAllOU.setName("btnDisselectAllOU");
        this.btnQueryOrg.setName("btnQueryOrg");
        this.btnQueryCostAccount.setName("btnQueryCostAccount");
        this.tblAccountForAssigned.setName("tblAccountForAssigned");
        this.cbxCompanyOrgQueryField.setName("cbxCompanyOrgQueryField");
        this.cbxCostAccountQueryField.setName("cbxCostAccountQueryField");
        this.txtCompanyOrgNumAndName.setName("txtCompanyOrgNumAndName");
        this.txtCostAccountNumAndName.setName("txtCostAccountNumAndName");
        this.selectall.setName("selectall");
        this.modfiys.setName("modfiys");
        this.btnAssign.setName("btnAssign");
        // CoreUI
        // tblCompanyForAssigned		
        this.tblCompanyForAssigned.setFormatXml(resHelper.getString("tblCompanyForAssigned.formatXml"));
        this.tblCompanyForAssigned.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblCompanyForAssigned_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // rbAssigned
        this.rbAssigned.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssigned, new Class[] { IItemAction.class }, getServiceContext()));		
        this.rbAssigned.setText(resHelper.getString("rbAssigned.text"));
        this.rbAssigned.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    rbAssigned_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // rbNotAssigned
        this.rbNotAssigned.setAction((IItemAction)ActionProxyFactory.getProxy(actionNotAssigned, new Class[] { IItemAction.class }, getServiceContext()));		
        this.rbNotAssigned.setText(resHelper.getString("rbNotAssigned.text"));		
        this.rbNotAssigned.setSelected(true);
        this.rbNotAssigned.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    rbNotAssigned_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnAllSelect
        this.btnAllSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAllSelect.setText(resHelper.getString("btnAllSelect.text"));
        // btnAllDisSelect
        this.btnAllDisSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllDisselect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAllDisSelect.setText(resHelper.getString("btnAllDisSelect.text"));
        // lblCuForAssigned		
        this.lblCuForAssigned.setTitle(resHelper.getString("lblCuForAssigned.title"));		
        this.lblCuForAssigned.setTitleStyle(2);		
        this.lblCuForAssigned.setEnableActive(false);
        // lblAccountForAssigned		
        this.lblAccountForAssigned.setTitle(resHelper.getString("lblAccountForAssigned.title"));		
        this.lblAccountForAssigned.setTitleStyle(2);		
        this.lblAccountForAssigned.setEnableActive(false);
        // btnSelectAllOU
        this.btnSelectAllOU.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelectAllOU, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSelectAllOU.setText(resHelper.getString("btnSelectAllOU.text"));
        // btnDisselectAllOU
        this.btnDisselectAllOU.setAction((IItemAction)ActionProxyFactory.getProxy(actionDisselectAllOU, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDisselectAllOU.setText(resHelper.getString("btnDisselectAllOU.text"));
        // btnQueryOrg		
        this.btnQueryOrg.setText(resHelper.getString("btnQueryOrg.text"));
        this.btnQueryOrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnQueryOrg_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnQueryOrg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    btnQueryOrg_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnQueryCostAccount		
        this.btnQueryCostAccount.setText(resHelper.getString("btnQueryCostAccount.text"));
        this.btnQueryCostAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnQueryCostAccount_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblAccountForAssigned		
        this.tblAccountForAssigned.setFormatXml(resHelper.getString("tblAccountForAssigned.formatXml"));
        this.tblAccountForAssigned.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblAccountForAssigned_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // cbxCompanyOrgQueryField		
        this.cbxCompanyOrgQueryField.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.QueryFieldConditionEnum").toArray());
        // cbxCostAccountQueryField		
        this.cbxCostAccountQueryField.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.QueryFieldConditionEnum").toArray());
        // txtCompanyOrgNumAndName
        // txtCostAccountNumAndName
        // selectall		
        this.selectall.setText(resHelper.getString("selectall.text"));		
        this.selectall.setToolTipText(resHelper.getString("selectall.toolTipText"));		
        this.selectall.setVisible(false);
        // modfiys		
        this.modfiys.setText(resHelper.getString("modfiys.text"));		
        this.modfiys.setToolTipText(resHelper.getString("modfiys.toolTipText"));		
        this.modfiys.setVisible(false);
        // btnAssign
        this.btnAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAssign.setText(resHelper.getString("btnAssign.text"));		
        this.btnAssign.setToolTipText(resHelper.getString("btnAssign.toolTipText"));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 700, 520));
        this.setLayout(null);
        tblCompanyForAssigned.setBounds(new Rectangle(6, 32, 686, 151));
        this.add(tblCompanyForAssigned, null);
        rbAssigned.setBounds(new Rectangle(114, 191, 64, 19));
        this.add(rbAssigned, null);
        rbNotAssigned.setBounds(new Rectangle(184, 190, 63, 19));
        this.add(rbNotAssigned, null);
        btnAllSelect.setBounds(new Rectangle(546, 190, 64, 19));
        this.add(btnAllSelect, null);
        btnAllDisSelect.setBounds(new Rectangle(616, 190, 68, 19));
        this.add(btnAllDisSelect, null);
        lblCuForAssigned.setBounds(new Rectangle(7, 11, 199, 100));
        this.add(lblCuForAssigned, null);
        lblAccountForAssigned.setBounds(new Rectangle(4, 187, 115, 21));
        this.add(lblAccountForAssigned, null);
        btnSelectAllOU.setBounds(new Rectangle(535, 8, 67, 19));
        this.add(btnSelectAllOU, null);
        btnDisselectAllOU.setBounds(new Rectangle(608, 8, 66, 19));
        this.add(btnDisselectAllOU, null);
        btnQueryOrg.setBounds(new Rectangle(461, 7, 62, 21));
        this.add(btnQueryOrg, null);
        btnQueryCostAccount.setBounds(new Rectangle(472, 189, 62, 21));
        this.add(btnQueryCostAccount, null);
        tblAccountForAssigned.setBounds(new Rectangle(7, 222, 684, 292));
        this.add(tblAccountForAssigned, null);
        cbxCompanyOrgQueryField.setBounds(new Rectangle(208, 10, 81, 19));
        this.add(cbxCompanyOrgQueryField, null);
        cbxCostAccountQueryField.setBounds(new Rectangle(249, 191, 82, 18));
        this.add(cbxCostAccountQueryField, null);
        txtCompanyOrgNumAndName.setBounds(new Rectangle(291, 10, 146, 19));
        this.add(txtCompanyOrgNumAndName, null);
        txtCostAccountNumAndName.setBounds(new Rectangle(334, 191, 119, 18));
        this.add(txtCostAccountNumAndName, null);
        lblCuForAssigned.getContentPane().setLayout(null);        lblAccountForAssigned.getContentPane().setLayout(null);
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
        this.toolBar.add(selectall);
        this.toolBar.add(modfiys);
        this.toolBar.add(btnAssign);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.CostAccountNewAssignUIHandler";
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
     * output tblCompanyForAssigned_tableSelectChanged method
     */
    protected void tblCompanyForAssigned_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output rbAssigned_stateChanged method
     */
    protected void rbAssigned_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output rbNotAssigned_stateChanged method
     */
    protected void rbNotAssigned_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output btnQueryOrg_actionPerformed method
     */
    protected void btnQueryOrg_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnQueryOrg_mouseClicked method
     */
    protected void btnQueryOrg_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnQueryCostAccount_actionPerformed method
     */
    protected void btnQueryCostAccount_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblAccountForAssigned_editValueChanged method
     */
    protected void tblAccountForAssigned_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    	

    /**
     * output actionAssign_actionPerformed method
     */
    public void actionAssign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSetAcctCtrlForRefer_actionPerformed method
     */
    public void actionSetAcctCtrlForRefer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAssigned_actionPerformed method
     */
    public void actionAssigned_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNotAssigned_actionPerformed method
     */
    public void actionNotAssigned_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAllSelect_actionPerformed method
     */
    public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAllDisselect_actionPerformed method
     */
    public void actionAllDisselect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSelectUpper_actionPerformed method
     */
    public void actionSelectUpper_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisSelectUpper_actionPerformed method
     */
    public void actionDisSelectUpper_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSelectAllOU_actionPerformed method
     */
    public void actionSelectAllOU_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisselectAllOU_actionPerformed method
     */
    public void actionDisselectAllOU_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAssignToLowerCU_actionPerformed method
     */
    public void actionAssignToLowerCU_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAssignToCompany_actionPerformed method
     */
    public void actionAssignToCompany_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisplayAll_actionPerformed method
     */
    public void actionDisplayAll_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSelectAllCA_actionPerformed method
     */
    public void actionSelectAllCA_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDisSelectAllCA_actionPerformed method
     */
    public void actionDisSelectAllCA_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionAssign class
     */     
    protected class ActionAssign extends ItemAction {     
    
        public ActionAssign()
        {
            this(null);
        }

        public ActionAssign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAssign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionAssign", "actionAssign_actionPerformed", e);
        }
    }

    /**
     * output ActionSetAcctCtrlForRefer class
     */     
    protected class ActionSetAcctCtrlForRefer extends ItemAction {     
    
        public ActionSetAcctCtrlForRefer()
        {
            this(null);
        }

        public ActionSetAcctCtrlForRefer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSetAcctCtrlForRefer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetAcctCtrlForRefer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSetAcctCtrlForRefer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionSetAcctCtrlForRefer", "actionSetAcctCtrlForRefer_actionPerformed", e);
        }
    }

    /**
     * output ActionAssigned class
     */     
    protected class ActionAssigned extends ItemAction {     
    
        public ActionAssigned()
        {
            this(null);
        }

        public ActionAssigned(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAssigned.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssigned.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssigned.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionAssigned", "actionAssigned_actionPerformed", e);
        }
    }

    /**
     * output ActionNotAssigned class
     */     
    protected class ActionNotAssigned extends ItemAction {     
    
        public ActionNotAssigned()
        {
            this(null);
        }

        public ActionNotAssigned(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionNotAssigned.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNotAssigned.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNotAssigned.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionNotAssigned", "actionNotAssigned_actionPerformed", e);
        }
    }

    /**
     * output ActionAllSelect class
     */     
    protected class ActionAllSelect extends ItemAction {     
    
        public ActionAllSelect()
        {
            this(null);
        }

        public ActionAllSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAllSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionAllSelect", "actionAllSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionAllDisselect class
     */     
    protected class ActionAllDisselect extends ItemAction {     
    
        public ActionAllDisselect()
        {
            this(null);
        }

        public ActionAllDisselect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAllDisselect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllDisselect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllDisselect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionAllDisselect", "actionAllDisselect_actionPerformed", e);
        }
    }

    /**
     * output ActionSelectUpper class
     */     
    protected class ActionSelectUpper extends ItemAction {     
    
        public ActionSelectUpper()
        {
            this(null);
        }

        public ActionSelectUpper(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSelectUpper.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectUpper.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectUpper.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionSelectUpper", "actionSelectUpper_actionPerformed", e);
        }
    }

    /**
     * output ActionDisSelectUpper class
     */     
    protected class ActionDisSelectUpper extends ItemAction {     
    
        public ActionDisSelectUpper()
        {
            this(null);
        }

        public ActionDisSelectUpper(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisSelectUpper.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisSelectUpper.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisSelectUpper.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionDisSelectUpper", "actionDisSelectUpper_actionPerformed", e);
        }
    }

    /**
     * output ActionSelectAllOU class
     */     
    protected class ActionSelectAllOU extends ItemAction {     
    
        public ActionSelectAllOU()
        {
            this(null);
        }

        public ActionSelectAllOU(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSelectAllOU.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectAllOU.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectAllOU.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionSelectAllOU", "actionSelectAllOU_actionPerformed", e);
        }
    }

    /**
     * output ActionDisselectAllOU class
     */     
    protected class ActionDisselectAllOU extends ItemAction {     
    
        public ActionDisselectAllOU()
        {
            this(null);
        }

        public ActionDisselectAllOU(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisselectAllOU.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisselectAllOU.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisselectAllOU.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionDisselectAllOU", "actionDisselectAllOU_actionPerformed", e);
        }
    }

    /**
     * output ActionAssignToLowerCU class
     */     
    protected class ActionAssignToLowerCU extends ItemAction {     
    
        public ActionAssignToLowerCU()
        {
            this(null);
        }

        public ActionAssignToLowerCU(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAssignToLowerCU.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssignToLowerCU.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssignToLowerCU.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionAssignToLowerCU", "actionAssignToLowerCU_actionPerformed", e);
        }
    }

    /**
     * output ActionAssignToCompany class
     */     
    protected class ActionAssignToCompany extends ItemAction {     
    
        public ActionAssignToCompany()
        {
            this(null);
        }

        public ActionAssignToCompany(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAssignToCompany.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssignToCompany.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAssignToCompany.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionAssignToCompany", "actionAssignToCompany_actionPerformed", e);
        }
    }

    /**
     * output ActionDisplayAll class
     */     
    protected class ActionDisplayAll extends ItemAction {     
    
        public ActionDisplayAll()
        {
            this(null);
        }

        public ActionDisplayAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisplayAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisplayAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionDisplayAll", "actionDisplayAll_actionPerformed", e);
        }
    }

    /**
     * output ActionSelectAllCA class
     */     
    protected class ActionSelectAllCA extends ItemAction {     
    
        public ActionSelectAllCA()
        {
            this(null);
        }

        public ActionSelectAllCA(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSelectAllCA.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectAllCA.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectAllCA.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionSelectAllCA", "actionSelectAllCA_actionPerformed", e);
        }
    }

    /**
     * output ActionDisSelectAllCA class
     */     
    protected class ActionDisSelectAllCA extends ItemAction {     
    
        public ActionDisSelectAllCA()
        {
            this(null);
        }

        public ActionDisSelectAllCA(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDisSelectAllCA.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisSelectAllCA.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDisSelectAllCA.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractCostAccountNewAssignUI.this, "ActionDisSelectAllCA", "actionDisSelectAllCA_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "CostAccountNewAssignUI");
    }




}