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
public abstract class AbstractProjectIndexDataFilterUI extends com.kingdee.eas.fdc.basedata.client.FDCBIRptBaseFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectIndexDataFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrjoect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTargetType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCompanySelect;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkWholeProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProductType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTargetType;
    protected com.kingdee.bos.ctrl.swing.KDButton btnTargetTypeSelect;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtTips;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStage;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStage;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conProjectType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProjectType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    /**
     * output class constructor
     */
    public AbstractProjectIndexDataFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectIndexDataFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPrjoect = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTargetType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnCompanySelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.chkWholeProject = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTargetType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnTargetTypeSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.txtTips = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contStage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboStage = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.conProjectType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProjectType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCompany.setName("contCompany");
        this.contPrjoect.setName("contPrjoect");
        this.contTargetType.setName("contTargetType");
        this.contDateFrom.setName("contDateFrom");
        this.txtCompany.setName("txtCompany");
        this.btnCompanySelect.setName("btnCompanySelect");
        this.chkWholeProject.setName("chkWholeProject");
        this.prmtProductType.setName("prmtProductType");
        this.txtTargetType.setName("txtTargetType");
        this.btnTargetTypeSelect.setName("btnTargetTypeSelect");
        this.txtTips.setName("txtTips");
        this.contStage.setName("contStage");
        this.comboStage.setName("comboStage");
        this.conProjectType.setName("conProjectType");
        this.prmtProjectType.setName("prmtProjectType");
        this.prmtProject.setName("prmtProject");
        // CustomerQueryPanel
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);
        // contPrjoect		
        this.contPrjoect.setBoundLabelText(resHelper.getString("contPrjoect.boundLabelText"));		
        this.contPrjoect.setBoundLabelLength(100);		
        this.contPrjoect.setBoundLabelUnderline(true);
        // contTargetType		
        this.contTargetType.setBoundLabelText(resHelper.getString("contTargetType.boundLabelText"));		
        this.contTargetType.setBoundLabelLength(100);		
        this.contTargetType.setBoundLabelUnderline(true);
        // contDateFrom		
        this.contDateFrom.setBoundLabelText(resHelper.getString("contDateFrom.boundLabelText"));		
        this.contDateFrom.setBoundLabelLength(100);		
        this.contDateFrom.setBoundLabelUnderline(true);
        // txtCompany		
        this.txtCompany.setEnabled(false);
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
        // chkWholeProject		
        this.chkWholeProject.setText(resHelper.getString("chkWholeProject.text"));
        this.chkWholeProject.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkWholeProject_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtProductType		
        this.prmtProductType.setDisplayFormat("$name$");		
        this.prmtProductType.setEditFormat("$number$");		
        this.prmtProductType.setCommitFormat("$number$");		
        this.prmtProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");
        // txtTargetType
        // btnTargetTypeSelect		
        this.btnTargetTypeSelect.setText(resHelper.getString("btnTargetTypeSelect.text"));
        this.btnTargetTypeSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnTargetTypeSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtTips		
        this.txtTips.setText(resHelper.getString("txtTips.text"));		
        this.txtTips.setLineWrap(true);		
        this.txtTips.setEditable(false);		
        this.txtTips.setEnabled(false);
        // contStage		
        this.contStage.setBoundLabelText(resHelper.getString("contStage.boundLabelText"));		
        this.contStage.setBoundLabelLength(100);		
        this.contStage.setBoundLabelUnderline(true);
        // comboStage		
        this.comboStage.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.ProjectStageEnum").toArray());
        // conProjectType		
        this.conProjectType.setBoundLabelText(resHelper.getString("conProjectType.boundLabelText"));		
        this.conProjectType.setBoundLabelUnderline(true);		
        this.conProjectType.setBoundLabelLength(100);
        // prmtProjectType		
        this.prmtProjectType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectTypeQuery");		
        this.prmtProjectType.setEnabledMultiSelection(true);
        this.prmtProjectType.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtProjectType_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtProjectType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtProjectType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtProject.setRequired(true);
        this.prmtProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtProject.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtProject_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtCompany,btnCompanySelect,prmtProjectType,prmtProject,txtTargetType,btnTargetTypeSelect,prmtProductType,chkWholeProject,comboStage}));
        this.setFocusCycleRoot(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 400, 250));
        this.setLayout(null);
        contCompany.setBounds(new Rectangle(10, 10, 270, 21));
        this.add(contCompany, null);
        contPrjoect.setBounds(new Rectangle(10, 65, 342, 19));
        this.add(contPrjoect, null);
        contTargetType.setBounds(new Rectangle(10, 95, 270, 19));
        this.add(contTargetType, null);
        contDateFrom.setBounds(new Rectangle(10, 121, 270, 19));
        this.add(contDateFrom, null);
        btnCompanySelect.setBounds(new Rectangle(283, 10, 69, 21));
        this.add(btnCompanySelect, null);
        chkWholeProject.setBounds(new Rectangle(283, 121, 99, 19));
        this.add(chkWholeProject, null);
        btnTargetTypeSelect.setBounds(new Rectangle(283, 95, 69, 19));
        this.add(btnTargetTypeSelect, null);
        txtTips.setBounds(new Rectangle(10, 179, 346, 44));
        this.add(txtTips, null);
        contStage.setBounds(new Rectangle(10, 147, 270, 19));
        this.add(contStage, null);
        conProjectType.setBounds(new Rectangle(10, 37, 342, 19));
        this.add(conProjectType, null);
        //contCompany
        contCompany.setBoundEditor(txtCompany);
        //contPrjoect
        contPrjoect.setBoundEditor(prmtProject);
        //contTargetType
        contTargetType.setBoundEditor(txtTargetType);
        //contDateFrom
        contDateFrom.setBoundEditor(prmtProductType);
        //contStage
        contStage.setBoundEditor(comboStage);
        //conProjectType
        conProjectType.setBoundEditor(prmtProjectType);

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
	    return "com.kingdee.eas.fdc.basedata.app.ProjectIndexDataFilterUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.txtCompany.requestFocusInWindow();
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
     * output btnCompanySelect_actionPerformed method
     */
    protected void btnCompanySelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output chkWholeProject_stateChanged method
     */
    protected void chkWholeProject_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output btnTargetTypeSelect_actionPerformed method
     */
    protected void btnTargetTypeSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtProjectType_willShow method
     */
    protected void prmtProjectType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtProjectType_dataChanged method
     */
    protected void prmtProjectType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtProject_dataChanged method
     */
    protected void prmtProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtProject_willShow method
     */
    protected void prmtProject_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "ProjectIndexDataFilterUI");
    }




}