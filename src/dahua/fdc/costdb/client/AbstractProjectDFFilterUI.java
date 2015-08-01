/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

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
public abstract class AbstractProjectDFFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectDFFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrjoect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateFrom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProductType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProjectType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDFType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboDFType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkWholeProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labelStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer labelEndDate;
    /**
     * output class constructor
     */
    public AbstractProjectDFFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectDFFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contProjectType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPrjoect = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProjectType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contDFType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboDFType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.chkWholeProject = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.labelStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.labelEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectType.setName("contProjectType");
        this.contPrjoect.setName("contPrjoect");
        this.contDateFrom.setName("contDateFrom");
        this.prmtProductType.setName("prmtProductType");
        this.prmtProjectType.setName("prmtProjectType");
        this.contDFType.setName("contDFType");
        this.comboDFType.setName("comboDFType");
        this.prmtProject.setName("prmtProject");
        this.chkWholeProject.setName("chkWholeProject");
        this.kDStartDate.setName("kDStartDate");
        this.kDEndDate.setName("kDEndDate");
        this.labelStartDate.setName("labelStartDate");
        this.labelEndDate.setName("labelEndDate");
        // CustomerQueryPanel
        // contProjectType		
        this.contProjectType.setBoundLabelText(resHelper.getString("contProjectType.boundLabelText"));		
        this.contProjectType.setBoundLabelLength(100);		
        this.contProjectType.setBoundLabelUnderline(true);
        // contPrjoect		
        this.contPrjoect.setBoundLabelText(resHelper.getString("contPrjoect.boundLabelText"));		
        this.contPrjoect.setBoundLabelLength(100);		
        this.contPrjoect.setBoundLabelUnderline(true);
        // contDateFrom		
        this.contDateFrom.setBoundLabelText(resHelper.getString("contDateFrom.boundLabelText"));		
        this.contDateFrom.setBoundLabelLength(100);		
        this.contDateFrom.setBoundLabelUnderline(true);
        // prmtProductType		
        this.prmtProductType.setDisplayFormat("$name$");		
        this.prmtProductType.setEditFormat("$number$");		
        this.prmtProductType.setCommitFormat("$number$");		
        this.prmtProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");		
        this.prmtProductType.setEnabledMultiSelection(true);
        // prmtProjectType		
        this.prmtProjectType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectTypeQuery");		
        this.prmtProjectType.setDisplayFormat("$name$");		
        this.prmtProjectType.setEditFormat("$number$");		
        this.prmtProjectType.setCommitFormat("$number$");		
        this.prmtProjectType.setRequired(true);
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
        // contDFType		
        this.contDFType.setBoundLabelText(resHelper.getString("contDFType.boundLabelText"));		
        this.contDFType.setBoundLabelLength(100);		
        this.contDFType.setBoundLabelUnderline(true);
        // comboDFType		
        this.comboDFType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.DanFangTypeEnum").toArray());
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtProject.setCommitFormat("$name$");
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
        // kDStartDate
        // kDEndDate
        // labelStartDate		
        this.labelStartDate.setBoundLabelText(resHelper.getString("labelStartDate.boundLabelText"));		
        this.labelStartDate.setBoundLabelUnderline(true);
        // labelEndDate		
        this.labelEndDate.setBoundLabelText(resHelper.getString("labelEndDate.boundLabelText"));		
        this.labelEndDate.setBoundLabelUnderline(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 400, 200));
        this.setLayout(null);
        contProjectType.setBounds(new Rectangle(10, 5, 379, 20));
        this.add(contProjectType, null);
        contPrjoect.setBounds(new Rectangle(10, 32, 379, 20));
        this.add(contPrjoect, null);
        contDateFrom.setBounds(new Rectangle(10, 57, 289, 20));
        this.add(contDateFrom, null);
        contDFType.setBounds(new Rectangle(10, 83, 379, 20));
        this.add(contDFType, null);
        chkWholeProject.setBounds(new Rectangle(315, 57, 82, 19));
        this.add(chkWholeProject, null);
        kDStartDate.setBounds(new Rectangle(110, 112, 190, 20));
        this.add(kDStartDate, null);
        kDEndDate.setBounds(new Rectangle(111, 143, 190, 20));
        this.add(kDEndDate, null);
        labelStartDate.setBounds(new Rectangle(10, 112, 101, 20));
        this.add(labelStartDate, null);
        labelEndDate.setBounds(new Rectangle(10, 143, 101, 20));
        this.add(labelEndDate, null);
        //contProjectType
        contProjectType.setBoundEditor(prmtProjectType);
        //contPrjoect
        contPrjoect.setBoundEditor(prmtProject);
        //contDateFrom
        contDateFrom.setBoundEditor(prmtProductType);
        //contDFType
        contDFType.setBoundEditor(comboDFType);

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
	    return "com.kingdee.eas.fdc.costdb.app.ProjectDFFilterUIHandler";
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
     * output prmtProjectType_dataChanged method
     */
    protected void prmtProjectType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtProject_willShow method
     */
    protected void prmtProject_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output chkWholeProject_stateChanged method
     */
    protected void chkWholeProject_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.costdb.client", "ProjectDFFilterUI");
    }




}