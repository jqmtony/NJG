/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

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
public abstract class AbstractMaterialEnterSumFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMaterialEnterSumFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCostCenterSelect;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCurProjectSelect;
    protected com.kingdee.bos.ctrl.swing.KDButton btnMaterialSelect;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplier;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker enterTimeFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker enterTimeTo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCostCenter;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCurProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMaterial;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkBoxAllPlan;
    /**
     * output class constructor
     */
    public AbstractMaterialEnterSumFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMaterialEnterSumFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnCostCenterSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCurProjectSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnMaterialSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.prmtSupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.enterTimeFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.enterTimeTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtCostCenter = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCurProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtMaterial = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.chkBoxAllPlan = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.btnCostCenterSelect.setName("btnCostCenterSelect");
        this.btnCurProjectSelect.setName("btnCurProjectSelect");
        this.btnMaterialSelect.setName("btnMaterialSelect");
        this.prmtSupplier.setName("prmtSupplier");
        this.enterTimeFrom.setName("enterTimeFrom");
        this.enterTimeTo.setName("enterTimeTo");
        this.txtCostCenter.setName("txtCostCenter");
        this.txtCurProject.setName("txtCurProject");
        this.txtMaterial.setName("txtMaterial");
        this.chkBoxAllPlan.setName("chkBoxAllPlan");
        // CustomerQueryPanel
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelLength(100);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelLength(100);
        // btnCostCenterSelect		
        this.btnCostCenterSelect.setText(resHelper.getString("btnCostCenterSelect.text"));
        this.btnCostCenterSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCostCenterSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnCurProjectSelect		
        this.btnCurProjectSelect.setText(resHelper.getString("btnCurProjectSelect.text"));
        this.btnCurProjectSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCurProjectSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnMaterialSelect		
        this.btnMaterialSelect.setText(resHelper.getString("btnMaterialSelect.text"));
        this.btnMaterialSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnMaterialSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // prmtSupplier		
        this.prmtSupplier.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierDefaultQuery");
        // enterTimeFrom
        // enterTimeTo
        // txtCostCenter
        // txtCurProject
        // txtMaterial
        // chkBoxAllPlan		
        this.chkBoxAllPlan.setText(resHelper.getString("chkBoxAllPlan.text"));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 400, 280));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(25, 17, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(25, 47, 270, 19));
        this.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(30, 2310, 270, 19));
        this.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(25, 77, 270, 19));
        this.add(kDLabelContainer4, null);
        kDLabelContainer5.setBounds(new Rectangle(25, 107, 270, 19));
        this.add(kDLabelContainer5, null);
        kDLabelContainer6.setBounds(new Rectangle(25, 137, 270, 19));
        this.add(kDLabelContainer6, null);
        btnCostCenterSelect.setBounds(new Rectangle(313, 17, 60, 21));
        this.add(btnCostCenterSelect, null);
        btnCurProjectSelect.setBounds(new Rectangle(313, 47, 60, 21));
        this.add(btnCurProjectSelect, null);
        btnMaterialSelect.setBounds(new Rectangle(318, 2310, 60, 21));
        this.add(btnMaterialSelect, null);
        chkBoxAllPlan.setBounds(new Rectangle(25, 170, 134, 19));
        this.add(chkBoxAllPlan, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtCostCenter);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtCurProject);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtMaterial);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(prmtSupplier);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(enterTimeFrom);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(enterTimeTo);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.material.app.MaterialEnterSumFilterUIHandler";
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
     * output btnCostCenterSelect_actionPerformed method
     */
    protected void btnCostCenterSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnCurProjectSelect_actionPerformed method
     */
    protected void btnCurProjectSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnMaterialSelect_actionPerformed method
     */
    protected void btnMaterialSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.material.client", "MaterialEnterSumFilterUI");
    }




}