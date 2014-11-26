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
public abstract class AbstractAccActOnLoadBgFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAccActOnLoadBgFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostedDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostedCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBgItem;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostedDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostedCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBgItem;
    /**
     * output class constructor
     */
    public AbstractAccActOnLoadBgFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAccActOnLoadBgFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCostedDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCostedCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBgItem = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCostedDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCostedCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBgItem = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCostedDept.setName("contCostedDept");
        this.contCostedCompany.setName("contCostedCompany");
        this.contBgItem.setName("contBgItem");
        this.prmtCostedDept.setName("prmtCostedDept");
        this.prmtCostedCompany.setName("prmtCostedCompany");
        this.prmtBgItem.setName("prmtBgItem");
        // CustomerQueryPanel
        // contCostedDept		
        this.contCostedDept.setBoundLabelText(resHelper.getString("contCostedDept.boundLabelText"));		
        this.contCostedDept.setBoundLabelLength(100);		
        this.contCostedDept.setBoundLabelUnderline(true);
        // contCostedCompany		
        this.contCostedCompany.setBoundLabelText(resHelper.getString("contCostedCompany.boundLabelText"));		
        this.contCostedCompany.setBoundLabelLength(100);		
        this.contCostedCompany.setBoundLabelUnderline(true);
        // contBgItem		
        this.contBgItem.setBoundLabelText(resHelper.getString("contBgItem.boundLabelText"));		
        this.contBgItem.setBoundLabelLength(100);		
        this.contBgItem.setBoundLabelUnderline(true);
        // prmtCostedDept		
        this.prmtCostedDept.setRequired(true);
        this.prmtCostedDept.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCostedDept_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCostedCompany		
        this.prmtCostedCompany.setRequired(true);
        this.prmtCostedCompany.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCostedCompany_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtBgItem		
        this.prmtBgItem.setRequired(true);
        this.prmtBgItem.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBgItem_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
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
        this.setBounds(new Rectangle(10, 10, 600, 150));
        this.setLayout(null);
        contCostedDept.setBounds(new Rectangle(31, 61, 270, 19));
        this.add(contCostedDept, null);
        contCostedCompany.setBounds(new Rectangle(31, 27, 270, 19));
        this.add(contCostedCompany, null);
        contBgItem.setBounds(new Rectangle(31, 98, 540, 19));
        this.add(contBgItem, null);
        //contCostedDept
        contCostedDept.setBoundEditor(prmtCostedDept);
        //contCostedCompany
        contCostedCompany.setBoundEditor(prmtCostedCompany);
        //contBgItem
        contBgItem.setBoundEditor(prmtBgItem);

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
	    return "com.kingdee.eas.fdc.contract.app.AccActOnLoadBgFilterUIHandler";
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
     * output prmtCostedDept_dataChanged method
     */
    protected void prmtCostedDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCostedCompany_dataChanged method
     */
    protected void prmtCostedCompany_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtBgItem_dataChanged method
     */
    protected void prmtBgItem_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "AccActOnLoadBgFilterUI");
    }




}