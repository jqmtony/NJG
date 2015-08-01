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
public abstract class AbstractIncomeAccountTestUI extends CoreUIObject
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractIncomeAccountTestUI.class);
    protected ResourceBundleHelper resHelper = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtQueryIncomeAccountQuery;
    protected EntityViewInfo queryIncomeAccountQuery = null;
    protected IMetaDataPK queryIncomeAccountQueryPK;
    /**
     * output class constructor
     */
    public AbstractIncomeAccountTestUI() throws Exception
    {
        super();
        this.defaultObjectName = "queryIncomeAccountQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractIncomeAccountTestUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        queryIncomeAccountQueryPK = new MetaDataPK("com.kingdee.eas.fdc.basedata.app", "IncomeAccountQuery");
        this.toolBar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.menuBar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.kdtQueryIncomeAccountQuery = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.setName("IncomeAccountTestUI");
        this.toolBar.setName("IncomeAccountTestUI_toolbar");
        this.menuBar.setName("IncomeAccountTestUI_menubar");
        this.kdtQueryIncomeAccountQuery.setName("kdtQueryIncomeAccountQuery");
        // IncomeAccountTestUI
        // IncomeAccountTestUI_toolbar
        // IncomeAccountTestUI_menubar
        // kdtQueryIncomeAccountQuery		
        this.kdtQueryIncomeAccountQuery.setFormatXml(resHelper.getString("kdtQueryIncomeAccountQuery.formatXml"));
                this.kdtQueryIncomeAccountQuery.putBindContents("queryIncomeAccountQuery",new String[] {"id","number","longNumber","name","isEnabled","assigned","description","curProject.id","curProject.longNumber","parent.id","isLeaf","level","transLongNumber","fullOrgUnit.id","fullOrgUnit.longNumber","isSource","srcIncomeAccountId"});

        this.kdtQueryIncomeAccountQuery.addRequestRowSetListener(new RequestRowSetListener() {
            public void doRequestRowSet(RequestRowSetEvent e) {
                kdtQueryIncomeAccountQuery_doRequestRowSet(e);
            }
        });

		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(null);
        kdtQueryIncomeAccountQuery.setBounds(new Rectangle(10, 10, 996, 582));
        this.add(kdtQueryIncomeAccountQuery, null);

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
	    return "com.kingdee.eas.fdc.basedata.app.IncomeAccountTestUIHandler";
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
        this.queryIncomeAccountQuery = (EntityViewInfo)ov;
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
     * output kdtQueryIncomeAccountQuery_doRequestRowSet method
     */
    protected void kdtQueryIncomeAccountQuery_doRequestRowSet(RequestRowSetEvent e)
    {
        if (this.queryIncomeAccountQuery != null) {
            int start = ((Integer)e.getParam1()).intValue();
            int length = ((Integer)e.getParam2()).intValue() - start + 1;
            try {
                IQueryExecutor exec = this.getQueryExecutor(this.queryIncomeAccountQueryPK, this.queryIncomeAccountQuery);
                IRowSet rowSet = exec.executeQuery(start,length);
                e.setRowSet(rowSet);
                onGetRowSet(rowSet);
            } catch (Exception ee) {
                handUIException(ee);
            }
        }
    }


    /**
     * output getQueryExecutor method
     */
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo)
    {
        IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(queryPK);
        exec.setObjectView(viewInfo);
        return exec;
    }
    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("longNumber"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("assigned"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.longNumber"));
        sic.add(new SelectorItemInfo("parent.id"));
        sic.add(new SelectorItemInfo("isLeaf"));
        sic.add(new SelectorItemInfo("level"));
        sic.add(new SelectorItemInfo("transLongNumber"));
        sic.add(new SelectorItemInfo("fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
        sic.add(new SelectorItemInfo("isSource"));
        sic.add(new SelectorItemInfo("srcIncomeAccountId"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "IncomeAccountTestUI");
    }




}