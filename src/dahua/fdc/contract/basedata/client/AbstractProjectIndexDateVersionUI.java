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
public abstract class AbstractProjectIndexDateVersionUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectIndexDateVersionUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVerDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkVerDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVerNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVerName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVerRemark;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVerNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVerRemark;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDComboBox txtVerName;
    /**
     * output class constructor
     */
    public AbstractProjectIndexDateVersionUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectIndexDateVersionUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contVerDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkVerDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contVerNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVerName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVerRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtVerNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtVerRemark = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.txtVerName = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contVerDate.setName("contVerDate");
        this.pkVerDate.setName("pkVerDate");
        this.contVerNo.setName("contVerNo");
        this.contVerName.setName("contVerName");
        this.contVerRemark.setName("contVerRemark");
        this.txtVerNo.setName("txtVerNo");
        this.txtVerRemark.setName("txtVerRemark");
        this.kDSeparator2.setName("kDSeparator2");
        this.btnConfirm.setName("btnConfirm");
        this.btnCancel.setName("btnCancel");
        this.txtVerName.setName("txtVerName");
        // CoreUI
        // contVerDate		
        this.contVerDate.setBoundLabelText(resHelper.getString("contVerDate.boundLabelText"));		
        this.contVerDate.setBoundLabelLength(100);		
        this.contVerDate.setBoundLabelUnderline(true);
        // pkVerDate		
        this.pkVerDate.setEditable(false);		
        this.pkVerDate.setEnabled(false);
        // contVerNo		
        this.contVerNo.setBoundLabelText(resHelper.getString("contVerNo.boundLabelText"));		
        this.contVerNo.setBoundLabelLength(100);		
        this.contVerNo.setBoundLabelUnderline(true);
        // contVerName		
        this.contVerName.setBoundLabelText(resHelper.getString("contVerName.boundLabelText"));		
        this.contVerName.setBoundLabelLength(100);		
        this.contVerName.setBoundLabelUnderline(true);
        // contVerRemark		
        this.contVerRemark.setBoundLabelText(resHelper.getString("contVerRemark.boundLabelText"));		
        this.contVerRemark.setBoundLabelLength(100);		
        this.contVerRemark.setBoundLabelUnderline(true);
        // txtVerNo		
        this.txtVerNo.setEditable(false);
        // txtVerRemark
        // kDSeparator2
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
        // btnCancel		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        this.btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCancel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtVerName		
        this.txtVerName.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.ProjectIndexVerTypeEnum").toArray());
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 293, 170));
        this.setLayout(null);
        contVerDate.setBounds(new Rectangle(11, 13, 270, 19));
        this.add(contVerDate, null);
        contVerNo.setBounds(new Rectangle(11, 41, 270, 19));
        this.add(contVerNo, null);
        contVerName.setBounds(new Rectangle(10, 67, 270, 19));
        this.add(contVerName, null);
        contVerRemark.setBounds(new Rectangle(10, 93, 270, 19));
        this.add(contVerRemark, null);
        kDSeparator2.setBounds(new Rectangle(8, 125, 278, 10));
        this.add(kDSeparator2, null);
        btnConfirm.setBounds(new Rectangle(63, 137, 73, 21));
        this.add(btnConfirm, null);
        btnCancel.setBounds(new Rectangle(155, 137, 73, 21));
        this.add(btnCancel, null);
        //contVerDate
        contVerDate.setBoundEditor(pkVerDate);
        //contVerNo
        contVerNo.setBoundEditor(txtVerNo);
        //contVerName
        contVerName.setBoundEditor(txtVerName);
        //contVerRemark
        contVerRemark.setBoundEditor(txtVerRemark);

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
	    return "com.kingdee.eas.fdc.basedata.app.ProjectIndexDateVersionUIHandler";
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
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnCancel_actionPerformed method
     */
    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "ProjectIndexDateVersionUI");
    }




}