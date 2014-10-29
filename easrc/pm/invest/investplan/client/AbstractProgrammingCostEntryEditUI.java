/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.investplan.client;

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
public abstract class AbstractProgrammingCostEntryEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProgrammingCostEntryEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contId;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCostAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGoalCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAssigned;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAssigning;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractAssign;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvestYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProportion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFeeNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFeeName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvitReportedAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvitedAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractedAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRequestPayAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayedAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalanceAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtId;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostAccount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtGoalCost;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAssigned;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAssigning;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractAssign;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInvestYear;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtProportion;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtYear;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFeeNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFeeName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtInvitReportedAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtInvitedAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractedAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRequestPayAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPayedAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBalanceAmount;
    protected com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractProgrammingCostEntryEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProgrammingCostEntryEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contId = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtContract = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contCostAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGoalCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAssigned = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAssigning = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractAssign = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvestYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProportion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFeeNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFeeName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvitReportedAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvitedAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractedAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRequestPayAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPayedAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBalanceAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCostAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtGoalCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAssigned = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAssigning = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractAssign = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtInvestYear = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProportion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtYear = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtFeeNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtFeeName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInvitReportedAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtInvitedAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractedAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRequestPayAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPayedAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBalanceAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contId.setName("contId");
        this.kdtContract.setName("kdtContract");
        this.contCostAccount.setName("contCostAccount");
        this.contGoalCost.setName("contGoalCost");
        this.contAssigned.setName("contAssigned");
        this.contAssigning.setName("contAssigning");
        this.contContractAssign.setName("contContractAssign");
        this.contDescription.setName("contDescription");
        this.contInvestYear.setName("contInvestYear");
        this.contProject.setName("contProject");
        this.contNumber.setName("contNumber");
        this.contProportion.setName("contProportion");
        this.contYear.setName("contYear");
        this.contFeeNumber.setName("contFeeNumber");
        this.contFeeName.setName("contFeeName");
        this.contInvitReportedAmount.setName("contInvitReportedAmount");
        this.contInvitedAmount.setName("contInvitedAmount");
        this.contContractedAmount.setName("contContractedAmount");
        this.contRequestPayAmount.setName("contRequestPayAmount");
        this.contPayedAmount.setName("contPayedAmount");
        this.contBalanceAmount.setName("contBalanceAmount");
        this.txtId.setName("txtId");
        this.prmtCostAccount.setName("prmtCostAccount");
        this.txtGoalCost.setName("txtGoalCost");
        this.txtAssigned.setName("txtAssigned");
        this.txtAssigning.setName("txtAssigning");
        this.txtContractAssign.setName("txtContractAssign");
        this.txtDescription.setName("txtDescription");
        this.prmtInvestYear.setName("prmtInvestYear");
        this.txtProject.setName("txtProject");
        this.txtNumber.setName("txtNumber");
        this.txtProportion.setName("txtProportion");
        this.txtYear.setName("txtYear");
        this.txtFeeNumber.setName("txtFeeNumber");
        this.txtFeeName.setName("txtFeeName");
        this.txtInvitReportedAmount.setName("txtInvitReportedAmount");
        this.txtInvitedAmount.setName("txtInvitedAmount");
        this.txtContractedAmount.setName("txtContractedAmount");
        this.txtRequestPayAmount.setName("txtRequestPayAmount");
        this.txtPayedAmount.setName("txtPayedAmount");
        this.txtBalanceAmount.setName("txtBalanceAmount");
        // CoreUI		
        this.btnPageSetup.setVisible(false);		
        this.btnCloud.setVisible(false);		
        this.kDSeparatorCloud.setVisible(false);		
        this.menuItemPageSetup.setVisible(false);		
        this.menuItemCloudFeed.setVisible(false);		
        this.menuItemCloudScreen.setEnabled(false);		
        this.menuItemCloudScreen.setVisible(false);		
        this.menuItemCloudShare.setVisible(false);		
        this.kdSeparatorFWFile1.setVisible(false);		
        this.kDSeparator2.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.kDSeparator4.setVisible(false);		
        this.kDSeparator4.setEnabled(false);		
        this.rMenuItemSubmit.setVisible(false);		
        this.rMenuItemSubmit.setEnabled(false);		
        this.rMenuItemSubmitAndAddNew.setVisible(false);		
        this.rMenuItemSubmitAndAddNew.setEnabled(false);		
        this.rMenuItemSubmitAndPrint.setVisible(false);		
        this.rMenuItemSubmitAndPrint.setEnabled(false);		
        this.btnReset.setEnabled(false);		
        this.btnReset.setVisible(false);		
        this.menuItemReset.setEnabled(false);		
        this.menuItemReset.setVisible(false);
        // contId		
        this.contId.setBoundLabelText(resHelper.getString("contId.boundLabelText"));		
        this.contId.setBoundLabelLength(100);		
        this.contId.setBoundLabelUnderline(true);
        // kdtContract
		String kdtContractStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"goalCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"assigned\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"assigning\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractAssign\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"proportion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"year\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"feeNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"feeName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"invitReportedAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"invitedAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractedAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"requestPayAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"payedAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"balanceAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{goalCost}</t:Cell><t:Cell>$Resource{assigned}</t:Cell><t:Cell>$Resource{assigning}</t:Cell><t:Cell>$Resource{contractAssign}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{proportion}</t:Cell><t:Cell>$Resource{year}</t:Cell><t:Cell>$Resource{feeNumber}</t:Cell><t:Cell>$Resource{feeName}</t:Cell><t:Cell>$Resource{invitReportedAmount}</t:Cell><t:Cell>$Resource{invitedAmount}</t:Cell><t:Cell>$Resource{contractedAmount}</t:Cell><t:Cell>$Resource{requestPayAmount}</t:Cell><t:Cell>$Resource{payedAmount}</t:Cell><t:Cell>$Resource{balanceAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtContract.setFormatXml(resHelper.translateString("kdtContract",kdtContractStrXML));

                this.kdtContract.putBindContents("editData",new String[] {"goalCost","assigned","assigning","contractAssign","description","project","number","proportion","year","feeNumber","feeName","invitReportedAmount","invitedAmount","contractedAmount","requestPayAmount","payedAmount","balanceAmount"});


        // contCostAccount		
        this.contCostAccount.setBoundLabelText(resHelper.getString("contCostAccount.boundLabelText"));		
        this.contCostAccount.setBoundLabelLength(100);		
        this.contCostAccount.setBoundLabelUnderline(true);
        // contGoalCost		
        this.contGoalCost.setBoundLabelText(resHelper.getString("contGoalCost.boundLabelText"));		
        this.contGoalCost.setBoundLabelLength(100);		
        this.contGoalCost.setBoundLabelUnderline(true);
        // contAssigned		
        this.contAssigned.setBoundLabelText(resHelper.getString("contAssigned.boundLabelText"));		
        this.contAssigned.setBoundLabelLength(100);		
        this.contAssigned.setBoundLabelUnderline(true);
        // contAssigning		
        this.contAssigning.setBoundLabelText(resHelper.getString("contAssigning.boundLabelText"));		
        this.contAssigning.setBoundLabelLength(100);		
        this.contAssigning.setBoundLabelUnderline(true);
        // contContractAssign		
        this.contContractAssign.setBoundLabelText(resHelper.getString("contContractAssign.boundLabelText"));		
        this.contContractAssign.setBoundLabelLength(100);		
        this.contContractAssign.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contInvestYear		
        this.contInvestYear.setBoundLabelText(resHelper.getString("contInvestYear.boundLabelText"));		
        this.contInvestYear.setBoundLabelLength(100);		
        this.contInvestYear.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contProportion		
        this.contProportion.setBoundLabelText(resHelper.getString("contProportion.boundLabelText"));		
        this.contProportion.setBoundLabelLength(100);		
        this.contProportion.setBoundLabelUnderline(true);
        // contYear		
        this.contYear.setBoundLabelText(resHelper.getString("contYear.boundLabelText"));		
        this.contYear.setBoundLabelLength(100);		
        this.contYear.setBoundLabelUnderline(true);
        // contFeeNumber		
        this.contFeeNumber.setBoundLabelText(resHelper.getString("contFeeNumber.boundLabelText"));		
        this.contFeeNumber.setBoundLabelLength(100);		
        this.contFeeNumber.setBoundLabelUnderline(true);
        // contFeeName		
        this.contFeeName.setBoundLabelText(resHelper.getString("contFeeName.boundLabelText"));		
        this.contFeeName.setBoundLabelLength(100);		
        this.contFeeName.setBoundLabelUnderline(true);
        // contInvitReportedAmount		
        this.contInvitReportedAmount.setBoundLabelText(resHelper.getString("contInvitReportedAmount.boundLabelText"));		
        this.contInvitReportedAmount.setBoundLabelLength(100);		
        this.contInvitReportedAmount.setBoundLabelUnderline(true);
        // contInvitedAmount		
        this.contInvitedAmount.setBoundLabelText(resHelper.getString("contInvitedAmount.boundLabelText"));		
        this.contInvitedAmount.setBoundLabelLength(100);		
        this.contInvitedAmount.setBoundLabelUnderline(true);
        // contContractedAmount		
        this.contContractedAmount.setBoundLabelText(resHelper.getString("contContractedAmount.boundLabelText"));		
        this.contContractedAmount.setBoundLabelLength(100);		
        this.contContractedAmount.setBoundLabelUnderline(true);
        // contRequestPayAmount		
        this.contRequestPayAmount.setBoundLabelText(resHelper.getString("contRequestPayAmount.boundLabelText"));		
        this.contRequestPayAmount.setBoundLabelLength(100);		
        this.contRequestPayAmount.setBoundLabelUnderline(true);
        // contPayedAmount		
        this.contPayedAmount.setBoundLabelText(resHelper.getString("contPayedAmount.boundLabelText"));		
        this.contPayedAmount.setBoundLabelLength(100);		
        this.contPayedAmount.setBoundLabelUnderline(true);
        // contBalanceAmount		
        this.contBalanceAmount.setBoundLabelText(resHelper.getString("contBalanceAmount.boundLabelText"));		
        this.contBalanceAmount.setBoundLabelLength(100);		
        this.contBalanceAmount.setBoundLabelUnderline(true);
        // txtId		
        this.txtId.setMaxLength(44);
        // prmtCostAccount
        // txtGoalCost
        // txtAssigned
        // txtAssigning
        // txtContractAssign
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // prmtInvestYear
        // txtProject		
        this.txtProject.setMaxLength(255);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // txtProportion
        // txtYear		
        this.txtYear.setMaxLength(40);
        // txtFeeNumber		
        this.txtFeeNumber.setMaxLength(100);
        // txtFeeName		
        this.txtFeeName.setMaxLength(400);
        // txtInvitReportedAmount
        // txtInvitedAmount
        // txtContractedAmount
        // txtRequestPayAmount
        // txtPayedAmount
        // txtBalanceAmount
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
        this.setLayout(null);
        contId.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contId, null);
        kdtContract.setBounds(new Rectangle(14, 373, 560, 100));
        this.add(kdtContract, null);
        contCostAccount.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(contCostAccount, null);
        contGoalCost.setBounds(new Rectangle(300, 70, 270, 19));
        this.add(contGoalCost, null);
        contAssigned.setBounds(new Rectangle(10, 100, 270, 19));
        this.add(contAssigned, null);
        contAssigning.setBounds(new Rectangle(300, 100, 270, 19));
        this.add(contAssigning, null);
        contContractAssign.setBounds(new Rectangle(10, 130, 270, 19));
        this.add(contContractAssign, null);
        contDescription.setBounds(new Rectangle(300, 130, 270, 19));
        this.add(contDescription, null);
        contInvestYear.setBounds(new Rectangle(10, 160, 270, 19));
        this.add(contInvestYear, null);
        contProject.setBounds(new Rectangle(300, 160, 270, 19));
        this.add(contProject, null);
        contNumber.setBounds(new Rectangle(10, 190, 270, 19));
        this.add(contNumber, null);
        contProportion.setBounds(new Rectangle(300, 190, 270, 19));
        this.add(contProportion, null);
        contYear.setBounds(new Rectangle(10, 220, 270, 19));
        this.add(contYear, null);
        contFeeNumber.setBounds(new Rectangle(300, 220, 270, 19));
        this.add(contFeeNumber, null);
        contFeeName.setBounds(new Rectangle(10, 250, 270, 19));
        this.add(contFeeName, null);
        contInvitReportedAmount.setBounds(new Rectangle(300, 250, 270, 19));
        this.add(contInvitReportedAmount, null);
        contInvitedAmount.setBounds(new Rectangle(10, 280, 270, 19));
        this.add(contInvitedAmount, null);
        contContractedAmount.setBounds(new Rectangle(300, 280, 270, 19));
        this.add(contContractedAmount, null);
        contRequestPayAmount.setBounds(new Rectangle(10, 310, 270, 19));
        this.add(contRequestPayAmount, null);
        contPayedAmount.setBounds(new Rectangle(300, 310, 270, 19));
        this.add(contPayedAmount, null);
        contBalanceAmount.setBounds(new Rectangle(10, 340, 270, 19));
        this.add(contBalanceAmount, null);
        //contId
        contId.setBoundEditor(txtId);
        //contCostAccount
        contCostAccount.setBoundEditor(prmtCostAccount);
        //contGoalCost
        contGoalCost.setBoundEditor(txtGoalCost);
        //contAssigned
        contAssigned.setBoundEditor(txtAssigned);
        //contAssigning
        contAssigning.setBoundEditor(txtAssigning);
        //contContractAssign
        contContractAssign.setBoundEditor(txtContractAssign);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contInvestYear
        contInvestYear.setBoundEditor(prmtInvestYear);
        //contProject
        contProject.setBoundEditor(txtProject);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contProportion
        contProportion.setBoundEditor(txtProportion);
        //contYear
        contYear.setBoundEditor(txtYear);
        //contFeeNumber
        contFeeNumber.setBoundEditor(txtFeeNumber);
        //contFeeName
        contFeeName.setBoundEditor(txtFeeName);
        //contInvitReportedAmount
        contInvitReportedAmount.setBoundEditor(txtInvitReportedAmount);
        //contInvitedAmount
        contInvitedAmount.setBoundEditor(txtInvitedAmount);
        //contContractedAmount
        contContractedAmount.setBoundEditor(txtContractedAmount);
        //contRequestPayAmount
        contRequestPayAmount.setBoundEditor(txtRequestPayAmount);
        //contPayedAmount
        contPayedAmount.setBoundEditor(txtPayedAmount);
        //contBalanceAmount
        contBalanceAmount.setBoundEditor(txtBalanceAmount);

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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("contract.goalCost", java.math.BigDecimal.class, this.kdtContract, "goalCost.text");
		dataBinder.registerBinding("contract.assigned", java.math.BigDecimal.class, this.kdtContract, "assigned.text");
		dataBinder.registerBinding("contract.assigning", java.math.BigDecimal.class, this.kdtContract, "assigning.text");
		dataBinder.registerBinding("contract.contractAssign", java.math.BigDecimal.class, this.kdtContract, "contractAssign.text");
		dataBinder.registerBinding("contract.description", String.class, this.kdtContract, "description.text");
		dataBinder.registerBinding("contract.project", String.class, this.kdtContract, "project.text");
		dataBinder.registerBinding("contract.number", String.class, this.kdtContract, "number.text");
		dataBinder.registerBinding("contract.proportion", java.math.BigDecimal.class, this.kdtContract, "proportion.text");
		dataBinder.registerBinding("contract.year", String.class, this.kdtContract, "year.text");
		dataBinder.registerBinding("contract.feeNumber", String.class, this.kdtContract, "feeNumber.text");
		dataBinder.registerBinding("contract.feeName", String.class, this.kdtContract, "feeName.text");
		dataBinder.registerBinding("contract.invitReportedAmount", java.math.BigDecimal.class, this.kdtContract, "invitReportedAmount.text");
		dataBinder.registerBinding("contract.invitedAmount", java.math.BigDecimal.class, this.kdtContract, "invitedAmount.text");
		dataBinder.registerBinding("contract.contractedAmount", java.math.BigDecimal.class, this.kdtContract, "contractedAmount.text");
		dataBinder.registerBinding("contract.requestPayAmount", java.math.BigDecimal.class, this.kdtContract, "requestPayAmount.text");
		dataBinder.registerBinding("contract.payedAmount", java.math.BigDecimal.class, this.kdtContract, "payedAmount.text");
		dataBinder.registerBinding("contract.balanceAmount", java.math.BigDecimal.class, this.kdtContract, "balanceAmount.text");
		dataBinder.registerBinding("contract", com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo.class, this.kdtContract, "userObject");
		dataBinder.registerBinding("id", com.kingdee.bos.util.BOSUuid.class, this.txtId, "text");
		dataBinder.registerBinding("costAccount", com.kingdee.eas.fdc.basedata.CostAccountInfo.class, this.prmtCostAccount, "data");
		dataBinder.registerBinding("goalCost", java.math.BigDecimal.class, this.txtGoalCost, "value");
		dataBinder.registerBinding("assigned", java.math.BigDecimal.class, this.txtAssigned, "value");
		dataBinder.registerBinding("assigning", java.math.BigDecimal.class, this.txtAssigning, "value");
		dataBinder.registerBinding("contractAssign", java.math.BigDecimal.class, this.txtContractAssign, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("investYear", com.kingdee.eas.port.pm.base.InvestYearInfo.class, this.prmtInvestYear, "data");
		dataBinder.registerBinding("project", String.class, this.txtProject, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("proportion", java.math.BigDecimal.class, this.txtProportion, "value");
		dataBinder.registerBinding("year", String.class, this.txtYear, "text");
		dataBinder.registerBinding("feeNumber", String.class, this.txtFeeNumber, "text");
		dataBinder.registerBinding("feeName", String.class, this.txtFeeName, "text");
		dataBinder.registerBinding("invitReportedAmount", java.math.BigDecimal.class, this.txtInvitReportedAmount, "value");
		dataBinder.registerBinding("invitedAmount", java.math.BigDecimal.class, this.txtInvitedAmount, "value");
		dataBinder.registerBinding("contractedAmount", java.math.BigDecimal.class, this.txtContractedAmount, "value");
		dataBinder.registerBinding("requestPayAmount", java.math.BigDecimal.class, this.txtRequestPayAmount, "value");
		dataBinder.registerBinding("payedAmount", java.math.BigDecimal.class, this.txtPayedAmount, "value");
		dataBinder.registerBinding("balanceAmount", java.math.BigDecimal.class, this.txtBalanceAmount, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.pm.invest.investplan.app.ProgrammingCostEntryEditUIHandler";
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
        this.editData = (com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo)ov;
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
		getValidateHelper().registerBindProperty("contract.goalCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.assigned", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.assigning", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.contractAssign", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.proportion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.year", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.feeNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.feeName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.invitReportedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.invitedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.contractedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.requestPayAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.payedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract.balanceAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("goalCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("assigned", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("assigning", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractAssign", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("investYear", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("proportion", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("year", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("feeNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("feeName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invitReportedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("invitedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("requestPayAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payedAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("balanceAmount", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
    	sic.add(new SelectorItemInfo("contract.goalCost"));
    	sic.add(new SelectorItemInfo("contract.assigned"));
    	sic.add(new SelectorItemInfo("contract.assigning"));
    	sic.add(new SelectorItemInfo("contract.contractAssign"));
    	sic.add(new SelectorItemInfo("contract.description"));
    	sic.add(new SelectorItemInfo("contract.project"));
    	sic.add(new SelectorItemInfo("contract.number"));
    	sic.add(new SelectorItemInfo("contract.proportion"));
    	sic.add(new SelectorItemInfo("contract.year"));
    	sic.add(new SelectorItemInfo("contract.feeNumber"));
    	sic.add(new SelectorItemInfo("contract.feeName"));
    	sic.add(new SelectorItemInfo("contract.invitReportedAmount"));
    	sic.add(new SelectorItemInfo("contract.invitedAmount"));
    	sic.add(new SelectorItemInfo("contract.contractedAmount"));
    	sic.add(new SelectorItemInfo("contract.requestPayAmount"));
    	sic.add(new SelectorItemInfo("contract.payedAmount"));
    	sic.add(new SelectorItemInfo("contract.balanceAmount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contract.*"));
		}
		else{
		}
        sic.add(new SelectorItemInfo("id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costAccount.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("costAccount.id"));
        	sic.add(new SelectorItemInfo("costAccount.number"));
        	sic.add(new SelectorItemInfo("costAccount.name"));
		}
        sic.add(new SelectorItemInfo("goalCost"));
        sic.add(new SelectorItemInfo("assigned"));
        sic.add(new SelectorItemInfo("assigning"));
        sic.add(new SelectorItemInfo("contractAssign"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("investYear.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("investYear.id"));
        	sic.add(new SelectorItemInfo("investYear.number"));
        	sic.add(new SelectorItemInfo("investYear.name"));
		}
        sic.add(new SelectorItemInfo("project"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("proportion"));
        sic.add(new SelectorItemInfo("year"));
        sic.add(new SelectorItemInfo("feeNumber"));
        sic.add(new SelectorItemInfo("feeName"));
        sic.add(new SelectorItemInfo("invitReportedAmount"));
        sic.add(new SelectorItemInfo("invitedAmount"));
        sic.add(new SelectorItemInfo("contractedAmount"));
        sic.add(new SelectorItemInfo("requestPayAmount"));
        sic.add(new SelectorItemInfo("payedAmount"));
        sic.add(new SelectorItemInfo("balanceAmount"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.pm.invest.investplan.client", "ProgrammingCostEntryEditUI");
    }




}