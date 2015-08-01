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
public abstract class AbstractPartAMaterialDefaultFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPartAMaterialDefaultFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conMaterial;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conPlanCondition;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conEnterNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conNotEnterNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isIncludeLike;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conContract;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPlanNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEnterNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNotEnterNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMaterialNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtModel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtUnit;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combEquals;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combE1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comE2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMaterialName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContract;
    /**
     * output class constructor
     */
    public AbstractPartAMaterialDefaultFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPartAMaterialDefaultFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.conMaterial = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conPlanCondition = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conEnterNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conNotEnterNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isIncludeLike = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.conContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPlanNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEnterNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNotEnterNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtMaterialNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtModel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtUnit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.combEquals = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.combE1 = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comE2 = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtMaterialName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtContract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.conMaterial.setName("conMaterial");
        this.conModel.setName("conModel");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.conPlanCondition.setName("conPlanCondition");
        this.conEnterNumber.setName("conEnterNumber");
        this.conNotEnterNumber.setName("conNotEnterNumber");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.isIncludeLike.setName("isIncludeLike");
        this.conContract.setName("conContract");
        this.txtPlanNumber.setName("txtPlanNumber");
        this.txtEnterNumber.setName("txtEnterNumber");
        this.txtNotEnterNumber.setName("txtNotEnterNumber");
        this.txtMaterialNumber.setName("txtMaterialNumber");
        this.txtModel.setName("txtModel");
        this.txtUnit.setName("txtUnit");
        this.combEquals.setName("combEquals");
        this.combE1.setName("combE1");
        this.comE2.setName("comE2");
        this.txtMaterialName.setName("txtMaterialName");
        this.prmtContract.setName("prmtContract");
        // CustomerQueryPanel
        // conMaterial		
        this.conMaterial.setBoundLabelText(resHelper.getString("conMaterial.boundLabelText"));		
        this.conMaterial.setBoundLabelLength(100);		
        this.conMaterial.setBoundLabelUnderline(true);
        // conModel		
        this.conModel.setBoundLabelText(resHelper.getString("conModel.boundLabelText"));		
        this.conModel.setBoundLabelUnderline(true);		
        this.conModel.setBoundLabelLength(100);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(100);
        // conPlanCondition		
        this.conPlanCondition.setBoundLabelText(resHelper.getString("conPlanCondition.boundLabelText"));		
        this.conPlanCondition.setBoundLabelLength(100);		
        this.conPlanCondition.setBoundLabelUnderline(true);
        // conEnterNumber		
        this.conEnterNumber.setBoundLabelText(resHelper.getString("conEnterNumber.boundLabelText"));		
        this.conEnterNumber.setBoundLabelLength(100);		
        this.conEnterNumber.setBoundLabelUnderline(true);
        // conNotEnterNumber		
        this.conNotEnterNumber.setBoundLabelText(resHelper.getString("conNotEnterNumber.boundLabelText"));		
        this.conNotEnterNumber.setBoundLabelLength(100);		
        this.conNotEnterNumber.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelUnderline(true);		
        this.kDLabelContainer8.setBoundLabelLength(100);
        // isIncludeLike		
        this.isIncludeLike.setText(resHelper.getString("isIncludeLike.text"));
        // conContract		
        this.conContract.setBoundLabelText(resHelper.getString("conContract.boundLabelText"));		
        this.conContract.setBoundLabelUnderline(true);		
        this.conContract.setBoundLabelLength(100);
        // txtPlanNumber
        // txtEnterNumber
        // txtNotEnterNumber
        // txtMaterialNumber
        // txtModel
        // txtUnit
        // combEquals		
        this.combEquals.addItems(EnumUtils.getEnumList("com.kingdee.eas.base.forewarn.CompareType").toArray());
        // combE1		
        this.combE1.addItems(EnumUtils.getEnumList("com.kingdee.eas.base.forewarn.CompareType").toArray());
        // comE2		
        this.comE2.addItems(EnumUtils.getEnumList("com.kingdee.eas.base.forewarn.CompareType").toArray());
        // txtMaterialName
        // prmtContract		
        this.prmtContract.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillF7SimpleQuery");		
        this.prmtContract.setDisplayFormat("$name$");		
        this.prmtContract.setEditFormat("$number$");		
        this.prmtContract.setCommitFormat("$number$");
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 400, 300));
        this.setLayout(null);
        conMaterial.setBounds(new Rectangle(18, 42, 270, 19));
        this.add(conMaterial, null);
        conModel.setBounds(new Rectangle(18, 90, 270, 19));
        this.add(conModel, null);
        kDLabelContainer3.setBounds(new Rectangle(18, 116, 270, 19));
        this.add(kDLabelContainer3, null);
        conPlanCondition.setBounds(new Rectangle(17, 150, 161, 19));
        this.add(conPlanCondition, null);
        conEnterNumber.setBounds(new Rectangle(17, 177, 160, 19));
        this.add(conEnterNumber, null);
        conNotEnterNumber.setBounds(new Rectangle(17, 202, 160, 19));
        this.add(conNotEnterNumber, null);
        kDLabelContainer8.setBounds(new Rectangle(18, 66, 270, 19));
        this.add(kDLabelContainer8, null);
        isIncludeLike.setBounds(new Rectangle(139, 237, 140, 19));
        this.add(isIncludeLike, null);
        conContract.setBounds(new Rectangle(17, 16, 270, 19));
        this.add(conContract, null);
        txtPlanNumber.setBounds(new Rectangle(201, 150, 97, 19));
        this.add(txtPlanNumber, null);
        txtEnterNumber.setBounds(new Rectangle(201, 177, 97, 19));
        this.add(txtEnterNumber, null);
        txtNotEnterNumber.setBounds(new Rectangle(201, 202, 97, 19));
        this.add(txtNotEnterNumber, null);
        //conMaterial
        conMaterial.setBoundEditor(txtMaterialNumber);
        //conModel
        conModel.setBoundEditor(txtModel);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtUnit);
        //conPlanCondition
        conPlanCondition.setBoundEditor(combEquals);
        //conEnterNumber
        conEnterNumber.setBoundEditor(combE1);
        //conNotEnterNumber
        conNotEnterNumber.setBoundEditor(comE2);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtMaterialName);
        //conContract
        conContract.setBoundEditor(prmtContract);

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
	    return "com.kingdee.eas.fdc.material.app.PartAMaterialDefaultFilterUIHandler";
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.material.client", "PartAMaterialDefaultFilterUI");
    }




}