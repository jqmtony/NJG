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
public abstract class AbstractFDCContractParamUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCContractParamUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup3;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rdAim;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rdDymic;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rdMsg;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rdControl;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rdNone;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rdContractOverRate;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rdCostantsRate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtConstantsRate;
    /**
     * output class constructor
     */
    public AbstractFDCContractParamUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCContractParamUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDButtonGroup2 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDButtonGroup3 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.rdAim = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rdDymic = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.rdMsg = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rdControl = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rdNone = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rdContractOverRate = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rdCostantsRate = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.txtConstantsRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.kDLabel1.setName("kDLabel1");
        this.rdAim.setName("rdAim");
        this.rdDymic.setName("rdDymic");
        this.kDLabel2.setName("kDLabel2");
        this.rdMsg.setName("rdMsg");
        this.rdControl.setName("rdControl");
        this.rdNone.setName("rdNone");
        this.rdContractOverRate.setName("rdContractOverRate");
        this.rdCostantsRate.setName("rdCostantsRate");
        this.txtConstantsRate.setName("txtConstantsRate");
        // CoreUI
        // kDTabbedPane1
        // kDPanel1		
        this.kDPanel1.setBorder(null);
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.rdAim);
        this.kDButtonGroup1.add(this.rdDymic);
        // kDButtonGroup2
        this.kDButtonGroup2.add(this.rdMsg);
        this.kDButtonGroup2.add(this.rdControl);
        this.kDButtonGroup2.add(this.rdNone);
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // kDButtonGroup3
        this.kDButtonGroup3.add(this.rdContractOverRate);
        this.kDButtonGroup3.add(this.rdCostantsRate);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // rdAim		
        this.rdAim.setText(resHelper.getString("rdAim.text"));		
        this.rdAim.setToolTipText(resHelper.getString("rdAim.toolTipText"));		
        this.rdAim.setSelected(true);
        // rdDymic		
        this.rdDymic.setText(resHelper.getString("rdDymic.text"));		
        this.rdDymic.setToolTipText(resHelper.getString("rdDymic.toolTipText"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // rdMsg		
        this.rdMsg.setText(resHelper.getString("rdMsg.text"));
        // rdControl		
        this.rdControl.setText(resHelper.getString("rdControl.text"));
        // rdNone		
        this.rdNone.setText(resHelper.getString("rdNone.text"));		
        this.rdNone.setSelected(true);
        // rdContractOverRate		
        this.rdContractOverRate.setText(resHelper.getString("rdContractOverRate.text"));		
        this.rdContractOverRate.setSelected(true);
        // rdCostantsRate		
        this.rdCostantsRate.setText(resHelper.getString("rdCostantsRate.text"));
        // txtConstantsRate		
        this.txtConstantsRate.setDataType(5);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(null);
        kDTabbedPane1.setBounds(new Rectangle(12, 12, 926, 581));
        this.add(kDTabbedPane1, null);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        //kDPanel1
        kDPanel1.setLayout(null);        kDPanel2.setBounds(new Rectangle(15, 17, 526, 92));
        kDPanel1.add(kDPanel2, null);
        kDPanel3.setBounds(new Rectangle(15, 145, 526, 92));
        kDPanel1.add(kDPanel3, null);
        //kDPanel2
        kDPanel2.setLayout(null);        kDLabel1.setBounds(new Rectangle(22, 22, 199, 19));
        kDPanel2.add(kDLabel1, null);
        rdAim.setBounds(new Rectangle(228, 22, 87, 19));
        kDPanel2.add(rdAim, null);
        rdDymic.setBounds(new Rectangle(330, 22, 81, 19));
        kDPanel2.add(rdDymic, null);
        kDLabel2.setBounds(new Rectangle(22, 50, 100, 19));
        kDPanel2.add(kDLabel2, null);
        rdMsg.setBounds(new Rectangle(142, 50, 68, 19));
        kDPanel2.add(rdMsg, null);
        rdControl.setBounds(new Rectangle(228, 50, 93, 19));
        kDPanel2.add(rdControl, null);
        rdNone.setBounds(new Rectangle(330, 50, 102, 19));
        kDPanel2.add(rdNone, null);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(15, 145, 526, 92));        rdContractOverRate.setBounds(new Rectangle(39, 22, 319, 19));
        kDPanel3.add(rdContractOverRate, new KDLayout.Constraints(39, 22, 319, 19, 0));
        rdCostantsRate.setBounds(new Rectangle(39, 54, 163, 19));
        kDPanel3.add(rdCostantsRate, new KDLayout.Constraints(39, 54, 163, 19, 0));
        txtConstantsRate.setBounds(new Rectangle(204, 53, 88, 19));
        kDPanel3.add(txtConstantsRate, new KDLayout.Constraints(204, 53, 88, 19, 0));

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
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.FDCContractParamUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "FDCContractParamUI");
    }




}