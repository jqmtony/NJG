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
public abstract class AbstractDeductBillFullFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDeductBillFullFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrjoect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateTo;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlSettleState;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateTo;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioSave;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioSubmit;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioAuditing;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioAudited;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioStateAll;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup2;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCompanySelect;
    protected com.kingdee.bos.ctrl.swing.KDButton btnProjectSelect;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateFrom;
    /**
     * output class constructor
     */
    public AbstractDeductBillFullFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDeductBillFullFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPrjoect = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pnlSettleState = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.radioSave = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioSubmit = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioAuditing = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioAudited = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioStateAll = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup2 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.btnCompanySelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnProjectSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.pkDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contCompany.setName("contCompany");
        this.contPrjoect.setName("contPrjoect");
        this.contDateFrom.setName("contDateFrom");
        this.contDateTo.setName("contDateTo");
        this.pnlSettleState.setName("pnlSettleState");
        this.txtCompany.setName("txtCompany");
        this.txtProject.setName("txtProject");
        this.pkDateTo.setName("pkDateTo");
        this.radioSave.setName("radioSave");
        this.radioSubmit.setName("radioSubmit");
        this.radioAuditing.setName("radioAuditing");
        this.radioAudited.setName("radioAudited");
        this.radioStateAll.setName("radioStateAll");
        this.btnCompanySelect.setName("btnCompanySelect");
        this.btnProjectSelect.setName("btnProjectSelect");
        this.pkDateFrom.setName("pkDateFrom");
        // CustomerQueryPanel
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);
        // contPrjoect		
        this.contPrjoect.setBoundLabelText(resHelper.getString("contPrjoect.boundLabelText"));		
        this.contPrjoect.setBoundLabelLength(100);		
        this.contPrjoect.setBoundLabelUnderline(true);
        // contDateFrom		
        this.contDateFrom.setBoundLabelText(resHelper.getString("contDateFrom.boundLabelText"));		
        this.contDateFrom.setBoundLabelLength(100);		
        this.contDateFrom.setBoundLabelUnderline(true);
        // contDateTo		
        this.contDateTo.setBoundLabelText(resHelper.getString("contDateTo.boundLabelText"));		
        this.contDateTo.setBoundLabelLength(100);		
        this.contDateTo.setBoundLabelUnderline(true);
        // pnlSettleState		
        this.pnlSettleState.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlSettleState.border.title")));
        // txtCompany		
        this.txtCompany.setEnabled(false);
        // txtProject		
        this.txtProject.setEnabled(false);
        // pkDateTo
        // radioSave		
        this.radioSave.setText(resHelper.getString("radioSave.text"));
        // radioSubmit		
        this.radioSubmit.setText(resHelper.getString("radioSubmit.text"));
        // radioAuditing		
        this.radioAuditing.setText(resHelper.getString("radioAuditing.text"));
        // radioAudited		
        this.radioAudited.setText(resHelper.getString("radioAudited.text"));
        // radioStateAll		
        this.radioStateAll.setText(resHelper.getString("radioStateAll.text"));		
        this.radioStateAll.setSelected(true);
        // kDButtonGroup2
        this.kDButtonGroup2.add(this.radioSave);
        this.kDButtonGroup2.add(this.radioSubmit);
        this.kDButtonGroup2.add(this.radioAuditing);
        this.kDButtonGroup2.add(this.radioAudited);
        this.kDButtonGroup2.add(this.radioStateAll);
        // btnCompanySelect		
        this.btnCompanySelect.setText(resHelper.getString("btnCompanySelect.text"));
        this.btnCompanySelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCompanySelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnProjectSelect		
        this.btnProjectSelect.setText(resHelper.getString("btnProjectSelect.text"));
        this.btnProjectSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnProjectSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // pkDateFrom
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
        this.setBounds(new Rectangle(10, 10, 400, 400));
        this.setLayout(null);
        contCompany.setBounds(new Rectangle(10, 10, 270, 21));
        this.add(contCompany, null);
        contPrjoect.setBounds(new Rectangle(10, 33, 270, 19));
        this.add(contPrjoect, null);
        contDateFrom.setBounds(new Rectangle(10, 58, 270, 19));
        this.add(contDateFrom, null);
        contDateTo.setBounds(new Rectangle(10, 84, 270, 19));
        this.add(contDateTo, null);
        pnlSettleState.setBounds(new Rectangle(10, 114, 111, 146));
        this.add(pnlSettleState, null);
        btnCompanySelect.setBounds(new Rectangle(287, 10, 69, 21));
        this.add(btnCompanySelect, null);
        btnProjectSelect.setBounds(new Rectangle(288, 34, 69, 19));
        this.add(btnProjectSelect, null);
        //contCompany
        contCompany.setBoundEditor(txtCompany);
        //contPrjoect
        contPrjoect.setBoundEditor(txtProject);
        //contDateFrom
        contDateFrom.setBoundEditor(pkDateFrom);
        //contDateTo
        contDateTo.setBoundEditor(pkDateTo);
        //pnlSettleState
        pnlSettleState.setLayout(null);        radioSave.setBounds(new Rectangle(24, 18, 64, 19));
        pnlSettleState.add(radioSave, null);
        radioSubmit.setBounds(new Rectangle(24, 42, 64, 19));
        pnlSettleState.add(radioSubmit, null);
        radioAuditing.setBounds(new Rectangle(24, 65, 64, 19));
        pnlSettleState.add(radioAuditing, null);
        radioAudited.setBounds(new Rectangle(24, 90, 64, 19));
        pnlSettleState.add(radioAudited, null);
        radioStateAll.setBounds(new Rectangle(24, 115, 64, 19));
        pnlSettleState.add(radioStateAll, null);

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
	    return "com.kingdee.eas.fdc.contract.app.DeductBillFullFilterUIHandler";
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
	 * ????????��??
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
     * output btnCompanySelect_actionPerformed method
     */
    protected void btnCompanySelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnProjectSelect_actionPerformed method
     */
    protected void btnProjectSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "DeductBillFullFilterUI");
    }




}