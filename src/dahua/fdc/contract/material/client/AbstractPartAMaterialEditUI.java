/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

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
public abstract class AbstractPartAMaterialEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPartAMaterialEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contexRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractNumber;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalAmt;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCurrency;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField numberTxtRate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalAmt;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportMaterial;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportFromTemplate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportMat;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportFromTemplate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportMaterial;
    protected com.kingdee.eas.fdc.material.PartAMaterialInfo editData = null;
    protected ActionImportMaterial actionImportMaterial = null;
    protected ActionImportFromTemplate actionImportFromTemplate = null;
    /**
     * output class constructor
     */
    public AbstractPartAMaterialEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPartAMaterialEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionImportMaterial
        this.actionImportMaterial = new ActionImportMaterial(this);
        getActionManager().registerAction("actionImportMaterial", actionImportMaterial);
         this.actionImportMaterial.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportFromTemplate
        this.actionImportFromTemplate = new ActionImportFromTemplate(this);
        getActionManager().registerAction("actionImportFromTemplate", actionImportFromTemplate);
         this.actionImportFromTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contexRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contTotalAmt = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtContractName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCurrency = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.numberTxtRate = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtContractNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTotalAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnImportMaterial = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportFromTemplate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemImportMat = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImportFromTemplate = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImportMaterial = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contNumber.setName("contNumber");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.contContractName.setName("contContractName");
        this.contCurrency.setName("contCurrency");
        this.contexRate.setName("contexRate");
        this.contCreateTime.setName("contCreateTime");
        this.contContractNumber.setName("contContractNumber");
        this.kdtEntrys.setName("kdtEntrys");
        this.contTotalAmt.setName("contTotalAmt");
        this.prmtCreator.setName("prmtCreator");
        this.txtNumber.setName("txtNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.txtContractName.setName("txtContractName");
        this.txtCurrency.setName("txtCurrency");
        this.numberTxtRate.setName("numberTxtRate");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtContractNumber.setName("txtContractNumber");
        this.txtTotalAmt.setName("txtTotalAmt");
        this.btnImportMaterial.setName("btnImportMaterial");
        this.btnImportFromTemplate.setName("btnImportFromTemplate");
        this.menuItemImportMat.setName("menuItemImportMat");
        this.menuItemImportFromTemplate.setName("menuItemImportFromTemplate");
        this.menuItemImportMaterial.setName("menuItemImportMaterial");
        // CoreUI		
        this.setEnabled(false);		
        this.menuItemFirst.setVisible(false);		
        this.menuItemPre.setVisible(false);		
        this.menuItemNext.setVisible(false);		
        this.menuItemLast.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuItemMultiapprove.setVisible(false);		
        this.menuItemNextPerson.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.MenuItemWFG.setVisible(false);		
        this.menuWorkflow.setVisible(false);		
        this.kDMenuItemSendMessage.setVisible(false);		
        this.menuItemWorkFlowList.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(80);		
        this.contCreator.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(80);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(80);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contContractName		
        this.contContractName.setBoundLabelText(resHelper.getString("contContractName.boundLabelText"));		
        this.contContractName.setBoundLabelUnderline(true);		
        this.contContractName.setBoundLabelLength(100);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelUnderline(true);		
        this.contCurrency.setBoundLabelLength(100);
        // contexRate		
        this.contexRate.setBoundLabelText(resHelper.getString("contexRate.boundLabelText"));		
        this.contexRate.setBoundLabelUnderline(true);		
        this.contexRate.setBoundLabelLength(100);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setBoundLabelLength(80);
        // contContractNumber		
        this.contContractNumber.setBoundLabelText(resHelper.getString("contContractNumber.boundLabelText"));		
        this.contContractNumber.setBoundLabelLength(100);		
        this.contContractNumber.setBoundLabelUnderline(true);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"mainContractNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"mainContractName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"materialNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" /><t:Column t:key=\"materialName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"model\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"originalPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"quantity\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"arriveDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"materialId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"contractId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{mainContractNumber}</t:Cell><t:Cell>$Resource{mainContractName}</t:Cell><t:Cell>$Resource{materialNumber}</t:Cell><t:Cell>$Resource{materialName}</t:Cell><t:Cell>$Resource{model}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{originalPrice}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{quantity}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{arriveDate}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{materialId}</t:Cell><t:Cell>$Resource{contractId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","mainContractBill","mainContractBill.name","material","material.name","material.model","material.baseUnit.name","originalPrice","price","quantity","amount","arriveDate","description","material.id","mainContractBill.id"});


        // contTotalAmt		
        this.contTotalAmt.setBoundLabelText(resHelper.getString("contTotalAmt.boundLabelText"));		
        this.contTotalAmt.setBoundLabelUnderline(true);		
        this.contTotalAmt.setBoundLabelLength(100);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // txtContractName		
        this.txtContractName.setRequired(true);		
        this.txtContractName.setEnabled(false);
        // txtCurrency		
        this.txtCurrency.setRequired(true);		
        this.txtCurrency.setEnabled(false);
        // numberTxtRate		
        this.numberTxtRate.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // txtContractNumber		
        this.txtContractNumber.setRequired(true);		
        this.txtContractNumber.setEnabled(false);
        // txtTotalAmt		
        this.txtTotalAmt.setEnabled(false);
        // btnImportMaterial
        this.btnImportMaterial.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportMaterial, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportMaterial.setText(resHelper.getString("btnImportMaterial.text"));		
        this.btnImportMaterial.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));
        // btnImportFromTemplate
        this.btnImportFromTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportFromTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportFromTemplate.setText(resHelper.getString("btnImportFromTemplate.text"));		
        this.btnImportFromTemplate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));		
        this.btnImportFromTemplate.setToolTipText(resHelper.getString("btnImportFromTemplate.toolTipText"));
        // menuItemImportMat
        this.menuItemImportMat.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportMaterial, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportMat.setText(resHelper.getString("menuItemImportMat.text"));		
        this.menuItemImportMat.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));		
        this.menuItemImportMat.setToolTipText(resHelper.getString("menuItemImportMat.toolTipText"));
        // menuItemImportFromTemplate
        this.menuItemImportFromTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportFromTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportFromTemplate.setText(resHelper.getString("menuItemImportFromTemplate.text"));		
        this.menuItemImportFromTemplate.setToolTipText(resHelper.getString("menuItemImportFromTemplate.toolTipText"));
        // menuItemImportMaterial
        this.menuItemImportMaterial.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportMaterial, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportMaterial.setText(resHelper.getString("menuItemImportMaterial.text"));		
        this.menuItemImportMaterial.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        contCreator.setBounds(new Rectangle(9, 601, 451, 19));
        this.add(contCreator, new KDLayout.Constraints(9, 601, 451, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(9, 10, 483, 19));
        this.add(contNumber, new KDLayout.Constraints(9, 10, 483, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(81, 435, 482, 19));
        this.add(contAuditor, new KDLayout.Constraints(81, 435, 482, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(199, 487, 480, 19));
        this.add(contAuditTime, new KDLayout.Constraints(199, 487, 480, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contContractName.setBounds(new Rectangle(9, 36, 483, 19));
        this.add(contContractName, new KDLayout.Constraints(9, 36, 483, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrency.setBounds(new Rectangle(513, 34, 489, 19));
        this.add(contCurrency, new KDLayout.Constraints(513, 34, 489, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contexRate.setBounds(new Rectangle(9, 63, 483, 19));
        this.add(contexRate, new KDLayout.Constraints(9, 63, 483, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(551, 601, 451, 19));
        this.add(contCreateTime, new KDLayout.Constraints(551, 601, 451, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contContractNumber.setBounds(new Rectangle(513, 10, 489, 19));
        this.add(contContractNumber, new KDLayout.Constraints(513, 10, 489, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntrys.setBounds(new Rectangle(9, 88, 993, 506));
        this.add(kdtEntrys, new KDLayout.Constraints(9, 88, 993, 506, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTotalAmt.setBounds(new Rectangle(513, 60, 489, 19));
        this.add(contTotalAmt, new KDLayout.Constraints(513, 60, 489, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contContractName
        contContractName.setBoundEditor(txtContractName);
        //contCurrency
        contCurrency.setBoundEditor(txtCurrency);
        //contexRate
        contexRate.setBoundEditor(numberTxtRate);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contContractNumber
        contContractNumber.setBoundEditor(txtContractNumber);
        //contTotalAmt
        contTotalAmt.setBoundEditor(txtTotalAmt);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(menuItemImportMat);
        menuEdit.add(menuItemImportFromTemplate);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemImportMaterial);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnImportMaterial);
        this.toolBar.add(btnImportFromTemplate);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys.material.name", String.class, this.kdtEntrys, "materialName.text");
		dataBinder.registerBinding("entrys.material.model", String.class, this.kdtEntrys, "model.text");
		dataBinder.registerBinding("entrys.originalPrice", java.math.BigDecimal.class, this.kdtEntrys, "originalPrice.text");
		dataBinder.registerBinding("entrys.price", java.math.BigDecimal.class, this.kdtEntrys, "price.text");
		dataBinder.registerBinding("entrys.amount", java.math.BigDecimal.class, this.kdtEntrys, "amount.text");
		dataBinder.registerBinding("entrys.arriveDate", java.util.Date.class, this.kdtEntrys, "arriveDate.text");
		dataBinder.registerBinding("entrys.mainContractBill.name", String.class, this.kdtEntrys, "mainContractName.text");
		dataBinder.registerBinding("entrys.mainContractBill", com.kingdee.eas.fdc.contract.ContractBillInfo.class, this.kdtEntrys, "mainContractNumber.text");
		dataBinder.registerBinding("entrys.material", com.kingdee.eas.basedata.master.material.MaterialInfo.class, this.kdtEntrys, "materialNumber.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.material.PartAMaterialEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.material.baseUnit.name", String.class, this.kdtEntrys, "unit.text");
		dataBinder.registerBinding("entrys.mainContractBill.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "contractId.text");
		dataBinder.registerBinding("entrys.material.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "materialId.text");
		dataBinder.registerBinding("entrys.description", String.class, this.kdtEntrys, "description.text");
		dataBinder.registerBinding("entrys.quantity", java.math.BigDecimal.class, this.kdtEntrys, "quantity.text");
		dataBinder.registerBinding("creator.name", String.class, this.prmtCreator, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor.name", String.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("contractBill.name", String.class, this.txtContractName, "text");
		dataBinder.registerBinding("contractBill.currency.name", String.class, this.txtCurrency, "text");
		dataBinder.registerBinding("contractBill.exRate", java.math.BigDecimal.class, this.numberTxtRate, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("contractBill.number", String.class, this.txtContractNumber, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.material.app.PartAMaterialEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.material.PartAMaterialInfo)ov;
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
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.material.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.material.model", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.originalPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.arriveDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.mainContractBill.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.mainContractBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.material", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.material.baseUnit.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.mainContractBill.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.material.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.quantity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.currency.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.exRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.number", ValidateHelper.ON_SAVE);    		
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("entrys.id"));
    sic.add(new SelectorItemInfo("entrys.material.name"));
    sic.add(new SelectorItemInfo("entrys.material.model"));
    sic.add(new SelectorItemInfo("entrys.originalPrice"));
    sic.add(new SelectorItemInfo("entrys.price"));
    sic.add(new SelectorItemInfo("entrys.amount"));
    sic.add(new SelectorItemInfo("entrys.arriveDate"));
    sic.add(new SelectorItemInfo("entrys.mainContractBill.name"));
        sic.add(new SelectorItemInfo("entrys.mainContractBill.*"));
//        sic.add(new SelectorItemInfo("entrys.mainContractBill.number"));
        sic.add(new SelectorItemInfo("entrys.material.*"));
//        sic.add(new SelectorItemInfo("entrys.material.number"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
    sic.add(new SelectorItemInfo("entrys.material.baseUnit.name"));
    sic.add(new SelectorItemInfo("entrys.mainContractBill.id"));
    sic.add(new SelectorItemInfo("entrys.material.id"));
    sic.add(new SelectorItemInfo("entrys.description"));
    sic.add(new SelectorItemInfo("entrys.quantity"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("contractBill.name"));
        sic.add(new SelectorItemInfo("contractBill.currency.name"));
        sic.add(new SelectorItemInfo("contractBill.exRate"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("contractBill.number"));
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
     * output actionImportMaterial_actionPerformed method
     */
    public void actionImportMaterial_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportFromTemplate_actionPerformed method
     */
    public void actionImportFromTemplate_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionImportMaterial(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportMaterial() {
    	return false;
    }
	public RequestContext prepareActionImportFromTemplate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportFromTemplate() {
    	return false;
    }

    /**
     * output ActionImportMaterial class
     */     
    protected class ActionImportMaterial extends ItemAction {     
    
        public ActionImportMaterial()
        {
            this(null);
        }

        public ActionImportMaterial(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImportMaterial.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportMaterial.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportMaterial.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPartAMaterialEditUI.this, "ActionImportMaterial", "actionImportMaterial_actionPerformed", e);
        }
    }

    /**
     * output ActionImportFromTemplate class
     */     
    protected class ActionImportFromTemplate extends ItemAction {     
    
        public ActionImportFromTemplate()
        {
            this(null);
        }

        public ActionImportFromTemplate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImportFromTemplate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportFromTemplate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportFromTemplate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPartAMaterialEditUI.this, "ActionImportFromTemplate", "actionImportFromTemplate_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.material.client", "PartAMaterialEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}