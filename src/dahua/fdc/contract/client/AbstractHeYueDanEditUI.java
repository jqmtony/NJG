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
public abstract class AbstractHeYueDanEditUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractHeYueDanEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDTextField conBalance;
    protected com.kingdee.bos.ctrl.swing.KDTextField conControlAmt;
    protected com.kingdee.bos.ctrl.swing.KDTextField conControlBalance;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtConContrarct;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conContrarctName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conGuiHua;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conKongZhi;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conKongZhiYu;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnOK;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCal;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected ActionOK actionOK = null;
    protected ActionCal actionCal = null;
    /**
     * output class constructor
     */
    public AbstractHeYueDanEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractHeYueDanEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionOK
        this.actionOK = new ActionOK(this);
        getActionManager().registerAction("actionOK", actionOK);
         this.actionOK.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCal
        this.actionCal = new ActionCal(this);
        getActionManager().registerAction("actionCal", actionCal);
         this.actionCal.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.conBalance = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.conControlAmt = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.conControlBalance = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtConContrarct = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.conContrarctName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conGuiHua = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conKongZhi = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conKongZhiYu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnOK = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCal = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDPanel1.setName("kDPanel1");
        this.conBalance.setName("conBalance");
        this.conControlAmt.setName("conControlAmt");
        this.conControlBalance.setName("conControlBalance");
        this.prmtConContrarct.setName("prmtConContrarct");
        this.conContrarctName.setName("conContrarctName");
        this.conGuiHua.setName("conGuiHua");
        this.conKongZhi.setName("conKongZhi");
        this.conKongZhiYu.setName("conKongZhiYu");
        this.btnOK.setName("btnOK");
        this.btnCal.setName("btnCal");
        // CoreUI
        // kDPanel1
        // conBalance		
        this.conBalance.setEnabled(false);
        // conControlAmt		
        this.conControlAmt.setEnabled(false);
        // conControlBalance		
        this.conControlBalance.setEnabled(false);
        // prmtConContrarct
        this.prmtConContrarct.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtConContrarct_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtConContrarct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                try {
                    prmtConContrarct_mousePressed(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // conContrarctName		
        this.conContrarctName.setBoundLabelText(resHelper.getString("conContrarctName.boundLabelText"));		
        this.conContrarctName.setToolTipText(resHelper.getString("conContrarctName.toolTipText"));		
        this.conContrarctName.setBoundLabelLength(100);
        // conGuiHua		
        this.conGuiHua.setBoundLabelText(resHelper.getString("conGuiHua.boundLabelText"));		
        this.conGuiHua.setToolTipText(resHelper.getString("conGuiHua.toolTipText"));		
        this.conGuiHua.setBoundLabelLength(100);
        // conKongZhi		
        this.conKongZhi.setBoundLabelText(resHelper.getString("conKongZhi.boundLabelText"));		
        this.conKongZhi.setBoundLabelLength(100);		
        this.conKongZhi.setToolTipText(resHelper.getString("conKongZhi.toolTipText"));
        // conKongZhiYu		
        this.conKongZhiYu.setBoundLabelText(resHelper.getString("conKongZhiYu.boundLabelText"));		
        this.conKongZhiYu.setToolTipText(resHelper.getString("conKongZhiYu.toolTipText"));		
        this.conKongZhiYu.setBoundLabelLength(100);
        // btnOK
        this.btnOK.setAction((IItemAction)ActionProxyFactory.getProxy(actionOK, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOK.setText(resHelper.getString("btnOK.text"));		
        this.btnOK.setToolTipText(resHelper.getString("btnOK.toolTipText"));
        // btnCal
        this.btnCal.setAction((IItemAction)ActionProxyFactory.getProxy(actionCal, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCal.setText(resHelper.getString("btnCal.text"));		
        this.btnCal.setToolTipText(resHelper.getString("btnCal.toolTipText"));
        this.btnCal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCal_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
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
        this.setBounds(new Rectangle(10, 10, 800, 130));
        this.setLayout(null);
        kDPanel1.setBounds(new Rectangle(-3, 0, 805, 130));
        this.add(kDPanel1, null);
        //kDPanel1
        kDPanel1.setLayout(null);        conContrarctName.setBounds(new Rectangle(14, 23, 359, 19));
        kDPanel1.add(conContrarctName, null);
        conGuiHua.setBounds(new Rectangle(414, 25, 361, 19));
        kDPanel1.add(conGuiHua, null);
        conKongZhi.setBounds(new Rectangle(14, 51, 360, 19));
        kDPanel1.add(conKongZhi, null);
        conKongZhiYu.setBounds(new Rectangle(414, 53, 363, 19));
        kDPanel1.add(conKongZhiYu, null);
        btnOK.setBounds(new Rectangle(516, 95, 86, 19));
        kDPanel1.add(btnOK, null);
        btnCal.setBounds(new Rectangle(642, 95, 83, 19));
        kDPanel1.add(btnCal, null);
        //conContrarctName
        conContrarctName.setBoundEditor(prmtConContrarct);
        //conGuiHua
        conGuiHua.setBoundEditor(conBalance);
        //conKongZhi
        conKongZhi.setBoundEditor(conControlAmt);
        //conKongZhiYu
        conKongZhiYu.setBoundEditor(conControlBalance);

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
	    return "com.kingdee.eas.fdc.contract.app.HeYueDanEditUIHandler";
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
        this.editData = (com.kingdee.eas.framework.CoreBaseInfo)ov;
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
     * output prmtConContrarct_dataChanged method
     */
    protected void prmtConContrarct_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtConContrarct_mousePressed method
     */
    protected void prmtConContrarct_mousePressed(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnCal_actionPerformed method
     */
    protected void btnCal_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionOK_actionPerformed method
     */
    public void actionOK_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCal_actionPerformed method
     */
    public void actionCal_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionOK class
     */     
    protected class ActionOK extends ItemAction {     
    
        public ActionOK()
        {
            this(null);
        }

        public ActionOK(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionOK.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOK.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOK.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractHeYueDanEditUI.this, "ActionOK", "actionOK_actionPerformed", e);
        }
    }

    /**
     * output ActionCal class
     */     
    protected class ActionCal extends ItemAction {     
    
        public ActionCal()
        {
            this(null);
        }

        public ActionCal(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCal.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCal.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCal.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractHeYueDanEditUI.this, "ActionCal", "actionCal_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "HeYueDanEditUI");
    }




}