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
public abstract class AbstractPayPlanTemplateUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPayPlanTemplateUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer contProgramming;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnBySchedule;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton btnByMonth;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblBySchedule;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblByMonth;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton bntSubmit;
    protected com.kingdee.eas.fdc.finance.PayPlanTemplateInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractPayPlanTemplateUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPayPlanTemplateUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAttachment
        actionAttachment.setEnabled(false);
        actionAttachment.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAttachment.SHORT_DESCRIPTION");
        actionAttachment.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.LONG_DESCRIPTION");
        actionAttachment.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAttachment.NAME");
        actionAttachment.putValue(ItemAction.NAME, _tempStr);
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contProgramming = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnBySchedule = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnByMonth = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.tblBySchedule = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblByMonth = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.bntSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contProgramming.setName("contProgramming");
        this.btnBySchedule.setName("btnBySchedule");
        this.btnByMonth.setName("btnByMonth");
        this.tblBySchedule.setName("tblBySchedule");
        this.tblByMonth.setName("tblByMonth");
        this.bntSubmit.setName("bntSubmit");
        // CoreUI
        this.btnAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.btnAttachment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_affixmanage"));
        // contProgramming		
        this.contProgramming.setTitle(resHelper.getString("contProgramming.title"));
        // btnBySchedule		
        this.btnBySchedule.setText(resHelper.getString("btnBySchedule.text"));		
        this.btnBySchedule.setVisible(false);
        this.btnBySchedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnBySchedule_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnByMonth		
        this.btnByMonth.setText(resHelper.getString("btnByMonth.text"));		
        this.btnByMonth.setVisible(false);
        this.btnByMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnByMonth_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.btnBySchedule);
        this.kDButtonGroup1.add(this.btnByMonth);
        // tblBySchedule
		String tblByScheduleStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"paymentType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"calType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"payScale\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"templateTask\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" /><t:Column t:key=\"calStandard\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /><t:Column t:key=\"delayDay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" t:styleID=\"sCol7\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" /><t:Column t:key=\"writeOffType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" /><t:Column t:key=\"description\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{paymentType}</t:Cell><t:Cell>$Resource{calType}</t:Cell><t:Cell>$Resource{payScale}</t:Cell><t:Cell>$Resource{templateTask}</t:Cell><t:Cell>$Resource{calStandard}</t:Cell><t:Cell>$Resource{delayDay}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{writeOffType}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblBySchedule.setFormatXml(resHelper.translateString("tblBySchedule",tblByScheduleStrXML));
        this.tblBySchedule.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblBySchedule_editStarting(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblBySchedule_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblBySchedule.putBindContents("editData",new String[] {"id","seq","paymentType","calType","payScale","templateTask","calStandard","delayDay","costAccount","writeOffType","description"});


        // tblByMonth
		String tblByMonthStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"paymentItem\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"usage\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"costAccount\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{paymentItem}</t:Cell><t:Cell>$Resource{usage}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblByMonth.setFormatXml(resHelper.translateString("tblByMonth",tblByMonthStrXML));

        

        // bntSubmit
        this.bntSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.bntSubmit.setText(resHelper.getString("bntSubmit.text"));		
        this.bntSubmit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
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
        this.setBounds(new Rectangle(10, 10, 680, 545));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 680, 545));
        contProgramming.setBounds(new Rectangle(1, 33, 679, 511));
        this.add(contProgramming, new KDLayout.Constraints(1, 33, 679, 511, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnBySchedule.setBounds(new Rectangle(44, 11, 140, 19));
        this.add(btnBySchedule, new KDLayout.Constraints(44, 11, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnByMonth.setBounds(new Rectangle(410, 11, 140, 19));
        this.add(btnByMonth, new KDLayout.Constraints(410, 11, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contProgramming
        contProgramming.getContentPane().setLayout(null);        tblBySchedule.setBounds(new Rectangle(1, 2, 322, 489));
        contProgramming.getContentPane().add(tblBySchedule, null);
        tblByMonth.setBounds(new Rectangle(326, 2, 344, 490));
        contProgramming.getContentPane().add(tblByMonth, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(bntSubmit);
        
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnReset);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("BySchedule.id", com.kingdee.bos.util.BOSUuid.class, this.tblBySchedule, "id.text");
		dataBinder.registerBinding("BySchedule.seq", int.class, this.tblBySchedule, "seq.text");
		dataBinder.registerBinding("BySchedule", com.kingdee.eas.fdc.finance.PayPlanTemplateByScheduleInfo.class, this.tblBySchedule, "userObject");
		dataBinder.registerBinding("BySchedule.calType", com.kingdee.eas.fdc.finance.CalTypeEnum.class, this.tblBySchedule, "calType.text");
		dataBinder.registerBinding("BySchedule.calStandard", com.kingdee.eas.fdc.finance.CalStandardEnum.class, this.tblBySchedule, "calStandard.text");
		dataBinder.registerBinding("BySchedule.delayDay", int.class, this.tblBySchedule, "delayDay.text");
		dataBinder.registerBinding("BySchedule.writeOffType", com.kingdee.eas.fdc.finance.PrepayWriteOffEnum.class, this.tblBySchedule, "writeOffType.text");
		dataBinder.registerBinding("BySchedule.costAccount", com.kingdee.eas.fdc.basedata.CostAccountInfo.class, this.tblBySchedule, "costAccount.text");
		dataBinder.registerBinding("BySchedule.paymentType", com.kingdee.eas.basedata.assistant.PaymentTypeInfo.class, this.tblBySchedule, "paymentType.text");
		dataBinder.registerBinding("BySchedule.payScale", java.math.BigDecimal.class, this.tblBySchedule, "payScale.text");
		dataBinder.registerBinding("BySchedule.templateTask", com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo.class, this.tblBySchedule, "templateTask.text");
		dataBinder.registerBinding("BySchedule.description", String.class, this.tblBySchedule, "description.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.PayPlanTemplateUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.finance.PayPlanTemplateInfo)ov;
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
		getValidateHelper().registerBindProperty("BySchedule.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BySchedule.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BySchedule", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BySchedule.calType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BySchedule.calStandard", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BySchedule.delayDay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BySchedule.writeOffType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BySchedule.costAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BySchedule.paymentType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BySchedule.payScale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BySchedule.templateTask", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BySchedule.description", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output btnBySchedule_actionPerformed method
     */
    protected void btnBySchedule_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnByMonth_actionPerformed method
     */
    protected void btnByMonth_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblBySchedule_editStarting method
     */
    protected void tblBySchedule_editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblBySchedule_editStopped method
     */
    protected void tblBySchedule_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
    	sic.add(new SelectorItemInfo("BySchedule.id"));
    	sic.add(new SelectorItemInfo("BySchedule.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("BySchedule.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("BySchedule.calType"));
    	sic.add(new SelectorItemInfo("BySchedule.calStandard"));
    	sic.add(new SelectorItemInfo("BySchedule.delayDay"));
    	sic.add(new SelectorItemInfo("BySchedule.writeOffType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("BySchedule.costAccount.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("BySchedule.costAccount.id"));
			sic.add(new SelectorItemInfo("BySchedule.costAccount.name"));
        	sic.add(new SelectorItemInfo("BySchedule.costAccount.number"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("BySchedule.paymentType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("BySchedule.paymentType.id"));
			sic.add(new SelectorItemInfo("BySchedule.paymentType.name"));
        	sic.add(new SelectorItemInfo("BySchedule.paymentType.number"));
		}
    	sic.add(new SelectorItemInfo("BySchedule.payScale"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("BySchedule.templateTask.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("BySchedule.templateTask.id"));
			sic.add(new SelectorItemInfo("BySchedule.templateTask.name"));
        	sic.add(new SelectorItemInfo("BySchedule.templateTask.number"));
		}
    	sic.add(new SelectorItemInfo("BySchedule.description"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAttachment(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachment() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "PayPlanTemplateUI");
    }




}