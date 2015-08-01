/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractPayReqAcctPayUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPayReqAcctPayUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPeriod;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable table;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDButton btnOk;
    /**
     * output class constructor
     */
    public AbstractPayReqAcctPayUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPayReqAcctPayUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.table = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnOk = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contPeriod.setName("contPeriod");
        this.conProject.setName("conProject");
        this.prmtPeriod.setName("prmtPeriod");
        this.prmtProject.setName("prmtProject");
        this.kDContainer1.setName("kDContainer1");
        this.table.setName("table");
        this.btnNo.setName("btnNo");
        this.btnOk.setName("btnOk");
        // CoreUI
        // contPeriod		
        this.contPeriod.setBoundLabelText(resHelper.getString("contPeriod.boundLabelText"));		
        this.contPeriod.setBoundLabelLength(100);		
        this.contPeriod.setBoundLabelUnderline(true);
        // conProject		
        this.conProject.setBoundLabelText(resHelper.getString("conProject.boundLabelText"));		
        this.conProject.setBoundLabelLength(100);		
        this.conProject.setBoundLabelUnderline(true);		
        this.conProject.setEnabled(false);
        // prmtPeriod		
        this.prmtPeriod.setQueryInfo("com.kingdee.eas.fdc.finance.app.F7FDCPeriodQuery");
        this.prmtPeriod.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtPeriod_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtProject		
        this.prmtProject.setDisplayFormat("$name$");		
        this.prmtProject.setEditFormat("$number$");		
        this.prmtProject.setCommitFormat("$number$");		
        this.prmtProject.setEnabled(false);
        // kDContainer1
        // table		
        this.table.setFormatXml(resHelper.getString("table.formatXml"));
        this.table.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    table_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnNo		
        this.btnNo.setText(resHelper.getString("btnNo.text"));
        this.btnNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNo_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnOk		
        this.btnOk.setText(resHelper.getString("btnOk.text"));
        this.btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnOk_actionPerformed(e);
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
        this.setBounds(new Rectangle(10, 10, 800, 600));
        this.setLayout(null);
        contPeriod.setBounds(new Rectangle(337, 10, 270, 19));
        this.add(contPeriod, null);
        conProject.setBounds(new Rectangle(10, 9, 270, 19));
        this.add(conProject, null);
        kDContainer1.setBounds(new Rectangle(10, 39, 775, 518));
        this.add(kDContainer1, null);
        btnNo.setBounds(new Rectangle(706, 566, 73, 21));
        this.add(btnNo, null);
        btnOk.setBounds(new Rectangle(623, 566, 73, 21));
        this.add(btnOk, null);
        //contPeriod
        contPeriod.setBoundEditor(prmtPeriod);
        //conProject
        conProject.setBoundEditor(prmtProject);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(table, BorderLayout.CENTER);

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
	    return "com.kingdee.eas.fdc.finance.app.PayReqAcctPayUIHandler";
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
     * output prmtPeriod_dataChanged method
     */
    protected void prmtPeriod_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output table_tableClicked method
     */
    protected void table_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnNo_actionPerformed method
     */
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnOk_actionPerformed method
     */
    protected void btnOk_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "PayReqAcctPayUI");
    }




}