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
public abstract class AbstractContractMoveEditUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractMoveEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRespDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRespDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRespPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoveDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkMoveDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldRespDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOldRespDept;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOldRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCanc;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.eas.fdc.contract.ContractMoveHistoryInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractContractMoveEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractMoveEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contRespDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtRespDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRespPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtRespPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contMoveDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkMoveDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contOldRespDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldRespPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOldRespDept = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOldRespPerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCanc = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.contRespDept.setName("contRespDept");
        this.prmtRespDept.setName("prmtRespDept");
        this.contRespPerson.setName("contRespPerson");
        this.prmtRespPerson.setName("prmtRespPerson");
        this.contMoveDate.setName("contMoveDate");
        this.pkMoveDate.setName("pkMoveDate");
        this.contOldRespDept.setName("contOldRespDept");
        this.contOldRespPerson.setName("contOldRespPerson");
        this.contRemark.setName("contRemark");
        this.txtRemark.setName("txtRemark");
        this.txtOldRespDept.setName("txtOldRespDept");
        this.txtOldRespPerson.setName("txtOldRespPerson");
        this.btnConfirm.setName("btnConfirm");
        this.btnCanc.setName("btnCanc");
        this.kDSeparator5.setName("kDSeparator5");
        // CoreUI
        // contRespDept		
        this.contRespDept.setBoundLabelText(resHelper.getString("contRespDept.boundLabelText"));		
        this.contRespDept.setBoundLabelLength(100);		
        this.contRespDept.setBoundLabelUnderline(true);
        // prmtRespDept		
        this.prmtRespDept.setDisplayFormat("$name$");		
        this.prmtRespDept.setEditFormat("$number$");		
        this.prmtRespDept.setCommitFormat("$number$");		
        this.prmtRespDept.setRequired(true);		
        this.prmtRespDept.setDefaultF7UIName("com.kingdee.eas.basedata.org.client.f7.AdminF7");
        // contRespPerson		
        this.contRespPerson.setBoundLabelText(resHelper.getString("contRespPerson.boundLabelText"));		
        this.contRespPerson.setBoundLabelLength(100);		
        this.contRespPerson.setBoundLabelUnderline(true);
        // prmtRespPerson		
        this.prmtRespPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtRespPerson.setDisplayFormat("$name$");		
        this.prmtRespPerson.setEditFormat("$number$");		
        this.prmtRespPerson.setCommitFormat("$number$");		
        this.prmtRespPerson.setRequired(true);
        // contMoveDate		
        this.contMoveDate.setBoundLabelText(resHelper.getString("contMoveDate.boundLabelText"));		
        this.contMoveDate.setBoundLabelLength(100);		
        this.contMoveDate.setBoundLabelUnderline(true);
        // pkMoveDate
        // contOldRespDept		
        this.contOldRespDept.setBoundLabelText(resHelper.getString("contOldRespDept.boundLabelText"));		
        this.contOldRespDept.setBoundLabelLength(100);		
        this.contOldRespDept.setBoundLabelUnderline(true);
        // contOldRespPerson		
        this.contOldRespPerson.setBoundLabelText(resHelper.getString("contOldRespPerson.boundLabelText"));		
        this.contOldRespPerson.setBoundLabelLength(100);		
        this.contOldRespPerson.setBoundLabelUnderline(true);
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelLength(100);		
        this.contRemark.setBoundLabelUnderline(true);
        // txtRemark		
        this.txtRemark.setMaxLength(200);
        // txtOldRespDept
        // txtOldRespPerson
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
        // btnCanc		
        this.btnCanc.setText(resHelper.getString("btnCanc.text"));
        this.btnCanc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCanc_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDSeparator5
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
        this.setBounds(new Rectangle(10, 10, 600, 170));
        this.setLayout(null);
        contRespDept.setBounds(new Rectangle(10, 39, 270, 19));
        this.add(contRespDept, null);
        contRespPerson.setBounds(new Rectangle(316, 39, 270, 19));
        this.add(contRespPerson, null);
        contMoveDate.setBounds(new Rectangle(10, 69, 270, 19));
        this.add(contMoveDate, null);
        contOldRespDept.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contOldRespDept, null);
        contOldRespPerson.setBounds(new Rectangle(316, 10, 270, 19));
        this.add(contOldRespPerson, null);
        contRemark.setBounds(new Rectangle(10, 99, 579, 19));
        this.add(contRemark, null);
        btnConfirm.setBounds(new Rectangle(421, 138, 73, 21));
        this.add(btnConfirm, null);
        btnCanc.setBounds(new Rectangle(516, 138, 73, 21));
        this.add(btnCanc, null);
        kDSeparator5.setBounds(new Rectangle(10, 128, 583, 10));
        this.add(kDSeparator5, null);
        //contRespDept
        contRespDept.setBoundEditor(prmtRespDept);
        //contRespPerson
        contRespPerson.setBoundEditor(prmtRespPerson);
        //contMoveDate
        contMoveDate.setBoundEditor(pkMoveDate);
        //contOldRespDept
        contOldRespDept.setBoundEditor(txtOldRespDept);
        //contOldRespPerson
        contOldRespPerson.setBoundEditor(txtOldRespPerson);
        //contRemark
        contRemark.setBoundEditor(txtRemark);

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
		dataBinder.registerBinding("respDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtRespDept, "data");
		dataBinder.registerBinding("respPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtRespPerson, "data");
		dataBinder.registerBinding("moveDate", java.util.Date.class, this.pkMoveDate, "value");
		dataBinder.registerBinding("remark", String.class, this.txtRemark, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractMoveEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.ContractMoveHistoryInfo)ov;
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
		getValidateHelper().registerBindProperty("respDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("respPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("moveDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    		
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
     * output btnCanc_actionPerformed method
     */
    protected void btnCanc_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("respDept.*"));
        sic.add(new SelectorItemInfo("respPerson.*"));
        sic.add(new SelectorItemInfo("moveDate"));
        sic.add(new SelectorItemInfo("remark"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractMoveEditUI");
    }




}