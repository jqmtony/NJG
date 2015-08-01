/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractChangeAuditAheadEditUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChangeAuditAheadEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAheadReason;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAheadReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contValidator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtValidator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConnectType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConnectType;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCan;
    protected com.kingdee.eas.fdc.contract.ChangeAuditBillInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractChangeAuditAheadEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChangeAuditAheadEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contAheadReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAheadReason = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contValidator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtValidator = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contConnectType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtConnectType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contAheadReason.setName("contAheadReason");
        this.txtAheadReason.setName("txtAheadReason");
        this.contValidator.setName("contValidator");
        this.txtValidator.setName("txtValidator");
        this.contConnectType.setName("contConnectType");
        this.txtConnectType.setName("txtConnectType");
        this.btnConfirm.setName("btnConfirm");
        this.btnCan.setName("btnCan");
        // CoreUI		
        this.menuBar.setVisible(false);
        // contAheadReason		
        this.contAheadReason.setBoundLabelText(resHelper.getString("contAheadReason.boundLabelText"));		
        this.contAheadReason.setBoundLabelLength(100);		
        this.contAheadReason.setBoundLabelUnderline(true);
        // txtAheadReason		
        this.txtAheadReason.setMaxLength(80);
        // contValidator		
        this.contValidator.setBoundLabelText(resHelper.getString("contValidator.boundLabelText"));		
        this.contValidator.setBoundLabelLength(100);		
        this.contValidator.setBoundLabelUnderline(true);
        // txtValidator		
        this.txtValidator.setMaxLength(80);
        // contConnectType		
        this.contConnectType.setBoundLabelText(resHelper.getString("contConnectType.boundLabelText"));		
        this.contConnectType.setBoundLabelLength(100);		
        this.contConnectType.setBoundLabelUnderline(true);
        // txtConnectType		
        this.txtConnectType.setMaxLength(80);
        // btnConfirm		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));
        this.btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnConfirm_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnCan		
        this.btnCan.setText(resHelper.getString("btnCan.text"));
        this.btnCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCan_actionPerformed(e);
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
        this.setBounds(new Rectangle(10, 10, 556, 114));
        this.setLayout(null);
        contAheadReason.setBounds(new Rectangle(10, 10, 536, 19));
        this.add(contAheadReason, null);
        contValidator.setBounds(new Rectangle(10, 58, 270, 19));
        this.add(contValidator, null);
        contConnectType.setBounds(new Rectangle(10, 34, 270, 19));
        this.add(contConnectType, null);
        btnConfirm.setBounds(new Rectangle(366, 84, 80, 19));
        this.add(btnConfirm, null);
        btnCan.setBounds(new Rectangle(466, 82, 80, 19));
        this.add(btnCan, null);
        //contAheadReason
        contAheadReason.setBoundEditor(txtAheadReason);
        //contValidator
        contValidator.setBoundEditor(txtValidator);
        //contConnectType
        contConnectType.setBoundEditor(txtConnectType);

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
	    return "com.kingdee.eas.fdc.contract.app.ChangeAuditAheadEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.ChangeAuditBillInfo)ov;
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
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnCan_actionPerformed method
     */
    protected void btnCan_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ChangeAuditAheadEditUI");
    }




}