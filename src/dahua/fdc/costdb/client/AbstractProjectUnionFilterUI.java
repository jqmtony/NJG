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
public abstract class AbstractProjectUnionFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectUnionFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrjoect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizProjectType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDUnionStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelEndDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDUnionEndDate;
    /**
     * output class constructor
     */
    public AbstractProjectUnionFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectUnionFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contPrjoect = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizProjectType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDUnionStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDUnionEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contPrjoect.setName("contPrjoect");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.bizProjectType.setName("bizProjectType");
        this.prmtProject.setName("prmtProject");
        this.kDLabelStartDate.setName("kDLabelStartDate");
        this.kDUnionStartDate.setName("kDUnionStartDate");
        this.kDLabelEndDate.setName("kDLabelEndDate");
        this.kDUnionEndDate.setName("kDUnionEndDate");
        // CustomerQueryPanel		
        this.setToolTipText(resHelper.getString("this.toolTipText"));
        // contPrjoect		
        this.contPrjoect.setBoundLabelText(resHelper.getString("contPrjoect.boundLabelText"));		
        this.contPrjoect.setBoundLabelLength(100);		
        this.contPrjoect.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(100);
        // bizProjectType		
        this.bizProjectType.setCommitFormat("$number$");		
        this.bizProjectType.setEditFormat("$number$");		
        this.bizProjectType.setDisplayFormat("$name$");		
        this.bizProjectType.setEditable(true);
        this.bizProjectType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    bizProjectType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.bizProjectType.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    pbAccount_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // prmtProject		
        this.prmtProject.setRequired(true);		
        this.prmtProject.setCommitFormat("$name$");		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
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
        // kDLabelStartDate		
        this.kDLabelStartDate.setBoundLabelText(resHelper.getString("kDLabelStartDate.boundLabelText"));		
        this.kDLabelStartDate.setBoundLabelUnderline(true);		
        this.kDLabelStartDate.setBoundLabelLength(100);
        // kDUnionStartDate
        // kDLabelEndDate		
        this.kDLabelEndDate.setBoundLabelText(resHelper.getString("kDLabelEndDate.boundLabelText"));		
        this.kDLabelEndDate.setBoundLabelUnderline(true);		
        this.kDLabelEndDate.setBoundLabelLength(100);
        // kDUnionEndDate
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 556, 359));
        this.setLayout(null);
        contPrjoect.setBounds(new Rectangle(10, 40, 357, 19));
        this.add(contPrjoect, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 10, 357, 19));
        this.add(kDLabelContainer2, null);
        kDLabelStartDate.setBounds(new Rectangle(10, 70, 357, 19));
        this.add(kDLabelStartDate, null);
        kDLabelEndDate.setBounds(new Rectangle(10, 100, 357, 19));
        this.add(kDLabelEndDate, null);
        //contPrjoect
        contPrjoect.setBoundEditor(prmtProject);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(bizProjectType);
        //kDLabelStartDate
        kDLabelStartDate.setBoundEditor(kDUnionStartDate);
        //kDLabelEndDate
        kDLabelEndDate.setBoundEditor(kDUnionEndDate);

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
	    return "com.kingdee.eas.fdc.costdb.app.ProjectUnionFilterUIHandler";
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
     * output pbAccount_focusLost method
     */
    protected void pbAccount_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output bizProjectType_dataChanged method
     */
    protected void bizProjectType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
        return new MetaDataPK("com.kingdee.eas.fdc.costdb.client", "ProjectUnionFilterUI");
    }




}