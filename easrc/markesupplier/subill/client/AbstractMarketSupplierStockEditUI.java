/**
 * output package name
 */
package com.kingdee.eas.port.markesupplier.subill.client;

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
public abstract class AbstractMarketSupplierStockEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketSupplierStockEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsysSupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLevel;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierFileType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVisibility;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuaLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierSplAreaEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplierBusinessMode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contServiceType;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsupplierName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurchaseOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEnterpriseMaster;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRegisterMoney;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBusinessNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaxRegisterNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizRegisterNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contboEnterpriseKind;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRecommended;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contserverfees;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEvatemp;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsupplierName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPurchaseOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAddress;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEnterpriseMaster;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRegisterMoney;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBusinessNum;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTaxRegisterNo;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBizRegisterNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtboEnterpriseKind;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRecommended;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtserverfees;
    protected com.kingdee.bos.ctrl.swing.KDComboBox State;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEvatemp;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsysSupplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLevel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplierFileType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtVisibility;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtQuaLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplierSplAreaEntry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplierBusinessMode;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtServiceType;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel6;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel7;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPeopleCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProvince;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCity;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkFax;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWebSite;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPostNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLinkMail;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer5;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer4;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPeopleCount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProvince;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCity;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkFax;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWebSite;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPostNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkMail;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntryAtt;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntryAtt_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmarketRemake;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanemarketRemake;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtmarketRemake;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntryPerson;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntryPerson_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer6;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE4;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE4_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPunish;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanePunish;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtPunish;
    protected com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractMarketSupplierStockEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketSupplierStockEditUI.class.getName());
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
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrint
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsysSupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contSupplierFileType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVisibility = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuaLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierSplAreaEntry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSupplierBusinessMode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contServiceType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane2 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsupplierName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPurchaseOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEnterpriseMaster = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRegisterMoney = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBusinessNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaxRegisterNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizRegisterNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contboEnterpriseKind = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRecommended = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contserverfees = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEvatemp = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtsupplierName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtPurchaseOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtInviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtEnterpriseMaster = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRegisterMoney = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBusinessNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTaxRegisterNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtBizRegisterNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtboEnterpriseKind = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRecommended = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtserverfees = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.State = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtEvatemp = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtsysSupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkauditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLevel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtSupplierFileType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtVisibility = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtQuaLevel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSupplierSplAreaEntry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSupplierBusinessMode = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtServiceType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel6 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel7 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel5 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contPeopleCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProvince = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkFax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contWebSite = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPostNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLinkMail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer5 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer4 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.txtPeopleCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtProvince = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCity = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkFax = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtWebSite = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPostNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkMail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdtEntryAtt = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contmarketRemake = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPanemarketRemake = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtmarketRemake = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kdtEntryPerson = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer6 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtE4 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contPunish = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPanePunish = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtPunish = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contDescription.setName("contDescription");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDContainer1.setName("kDContainer1");
        this.txtDescription.setName("txtDescription");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel4.setName("kDPanel4");
        this.kDPanel3.setName("kDPanel3");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contCreator.setName("contCreator");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contCreateTime.setName("contCreateTime");
        this.contsysSupplier.setName("contsysSupplier");
        this.contAuditor.setName("contAuditor");
        this.contauditDate.setName("contauditDate");
        this.contLevel.setName("contLevel");
        this.kDContainer3.setName("kDContainer3");
        this.contSupplierFileType.setName("contSupplierFileType");
        this.contVisibility.setName("contVisibility");
        this.contQuaLevel.setName("contQuaLevel");
        this.contSupplierSplAreaEntry.setName("contSupplierSplAreaEntry");
        this.contSupplierBusinessMode.setName("contSupplierBusinessMode");
        this.contServiceType.setName("contServiceType");
        this.kDTabbedPane2.setName("kDTabbedPane2");
        this.contNumber.setName("contNumber");
        this.contsupplierName.setName("contsupplierName");
        this.contPurchaseOrgUnit.setName("contPurchaseOrgUnit");
        this.contAddress.setName("contAddress");
        this.contInviteType.setName("contInviteType");
        this.contEnterpriseMaster.setName("contEnterpriseMaster");
        this.contRegisterMoney.setName("contRegisterMoney");
        this.contBusinessNum.setName("contBusinessNum");
        this.contTaxRegisterNo.setName("contTaxRegisterNo");
        this.contBizDate.setName("contBizDate");
        this.contBizRegisterNo.setName("contBizRegisterNo");
        this.contboEnterpriseKind.setName("contboEnterpriseKind");
        this.contRecommended.setName("contRecommended");
        this.contserverfees.setName("contserverfees");
        this.contState.setName("contState");
        this.contEvatemp.setName("contEvatemp");
        this.txtNumber.setName("txtNumber");
        this.txtsupplierName.setName("txtsupplierName");
        this.prmtPurchaseOrgUnit.setName("prmtPurchaseOrgUnit");
        this.txtAddress.setName("txtAddress");
        this.prmtInviteType.setName("prmtInviteType");
        this.txtEnterpriseMaster.setName("txtEnterpriseMaster");
        this.txtRegisterMoney.setName("txtRegisterMoney");
        this.txtBusinessNum.setName("txtBusinessNum");
        this.txtTaxRegisterNo.setName("txtTaxRegisterNo");
        this.pkBizDate.setName("pkBizDate");
        this.txtBizRegisterNo.setName("txtBizRegisterNo");
        this.txtboEnterpriseKind.setName("txtboEnterpriseKind");
        this.txtRecommended.setName("txtRecommended");
        this.txtserverfees.setName("txtserverfees");
        this.State.setName("State");
        this.prmtEvatemp.setName("prmtEvatemp");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtsysSupplier.setName("prmtsysSupplier");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkauditDate.setName("pkauditDate");
        this.prmtLevel.setName("prmtLevel");
        this.kdtEntrys.setName("kdtEntrys");
        this.prmtSupplierFileType.setName("prmtSupplierFileType");
        this.prmtVisibility.setName("prmtVisibility");
        this.prmtQuaLevel.setName("prmtQuaLevel");
        this.prmtSupplierSplAreaEntry.setName("prmtSupplierSplAreaEntry");
        this.prmtSupplierBusinessMode.setName("prmtSupplierBusinessMode");
        this.prmtServiceType.setName("prmtServiceType");
        this.kDPanel6.setName("kDPanel6");
        this.kDPanel7.setName("kDPanel7");
        this.kDPanel5.setName("kDPanel5");
        this.contPeopleCount.setName("contPeopleCount");
        this.contProvince.setName("contProvince");
        this.contCity.setName("contCity");
        this.contLinkFax.setName("contLinkFax");
        this.contLinkPhone.setName("contLinkPhone");
        this.contWebSite.setName("contWebSite");
        this.contPostNumber.setName("contPostNumber");
        this.contLinkMail.setName("contLinkMail");
        this.kDContainer5.setName("kDContainer5");
        this.kDContainer2.setName("kDContainer2");
        this.kDContainer4.setName("kDContainer4");
        this.txtPeopleCount.setName("txtPeopleCount");
        this.txtProvince.setName("txtProvince");
        this.txtCity.setName("txtCity");
        this.txtLinkFax.setName("txtLinkFax");
        this.txtLinkPhone.setName("txtLinkPhone");
        this.txtWebSite.setName("txtWebSite");
        this.txtPostNumber.setName("txtPostNumber");
        this.txtLinkMail.setName("txtLinkMail");
        this.kdtEntryAtt.setName("kdtEntryAtt");
        this.contmarketRemake.setName("contmarketRemake");
        this.scrollPanemarketRemake.setName("scrollPanemarketRemake");
        this.txtmarketRemake.setName("txtmarketRemake");
        this.kdtEntryPerson.setName("kdtEntryPerson");
        this.kDContainer6.setName("kDContainer6");
        this.kdtE4.setName("kdtE4");
        this.contPunish.setName("contPunish");
        this.scrollPanePunish.setName("scrollPanePunish");
        this.txtPunish.setName("txtPunish");
        // CoreUI		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnCreateTo.setVisible(true);		
        this.btnAddLine.setVisible(false);		
        this.btnCopyLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.separator1.setVisible(false);		
        this.menuItemCreateTo.setVisible(true);		
        this.separator3.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemCopyLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewDoProccess.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setVisible(false);
        // kDTabbedPane1
        // kDContainer1		
        this.kDContainer1.setEnableActive(false);		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setVisible(false);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // kDPanel1
        // kDPanel2
        // kDPanel4
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contsysSupplier		
        this.contsysSupplier.setBoundLabelText(resHelper.getString("contsysSupplier.boundLabelText"));		
        this.contsysSupplier.setBoundLabelLength(100);		
        this.contsysSupplier.setBoundLabelUnderline(true);		
        this.contsysSupplier.setVisible(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contauditDate		
        this.contauditDate.setBoundLabelText(resHelper.getString("contauditDate.boundLabelText"));		
        this.contauditDate.setBoundLabelLength(100);		
        this.contauditDate.setBoundLabelUnderline(true);		
        this.contauditDate.setVisible(true);
        // contLevel		
        this.contLevel.setBoundLabelText(resHelper.getString("contLevel.boundLabelText"));		
        this.contLevel.setBoundLabelLength(100);		
        this.contLevel.setBoundLabelUnderline(true);		
        this.contLevel.setVisible(true);
        // kDContainer3		
        this.kDContainer3.setEnableActive(false);		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));		
        this.kDContainer3.setVisible(false);
        // contSupplierFileType		
        this.contSupplierFileType.setBoundLabelText(resHelper.getString("contSupplierFileType.boundLabelText"));		
        this.contSupplierFileType.setBoundLabelLength(100);		
        this.contSupplierFileType.setBoundLabelUnderline(true);		
        this.contSupplierFileType.setVisible(false);
        // contVisibility		
        this.contVisibility.setBoundLabelText(resHelper.getString("contVisibility.boundLabelText"));		
        this.contVisibility.setBoundLabelLength(100);		
        this.contVisibility.setBoundLabelUnderline(true);		
        this.contVisibility.setVisible(false);
        // contQuaLevel		
        this.contQuaLevel.setBoundLabelText(resHelper.getString("contQuaLevel.boundLabelText"));		
        this.contQuaLevel.setBoundLabelLength(100);		
        this.contQuaLevel.setBoundLabelUnderline(true);		
        this.contQuaLevel.setVisible(false);
        // contSupplierSplAreaEntry		
        this.contSupplierSplAreaEntry.setBoundLabelText(resHelper.getString("contSupplierSplAreaEntry.boundLabelText"));		
        this.contSupplierSplAreaEntry.setBoundLabelLength(100);		
        this.contSupplierSplAreaEntry.setBoundLabelUnderline(true);		
        this.contSupplierSplAreaEntry.setVisible(false);
        // contSupplierBusinessMode		
        this.contSupplierBusinessMode.setBoundLabelText(resHelper.getString("contSupplierBusinessMode.boundLabelText"));		
        this.contSupplierBusinessMode.setBoundLabelLength(100);		
        this.contSupplierBusinessMode.setBoundLabelUnderline(true);		
        this.contSupplierBusinessMode.setVisible(false);
        // contServiceType		
        this.contServiceType.setBoundLabelText(resHelper.getString("contServiceType.boundLabelText"));		
        this.contServiceType.setBoundLabelLength(100);		
        this.contServiceType.setBoundLabelUnderline(true);		
        this.contServiceType.setVisible(false);
        // kDTabbedPane2
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contsupplierName		
        this.contsupplierName.setBoundLabelText(resHelper.getString("contsupplierName.boundLabelText"));		
        this.contsupplierName.setBoundLabelLength(100);		
        this.contsupplierName.setBoundLabelUnderline(true);		
        this.contsupplierName.setVisible(true);
        // contPurchaseOrgUnit		
        this.contPurchaseOrgUnit.setBoundLabelText(resHelper.getString("contPurchaseOrgUnit.boundLabelText"));		
        this.contPurchaseOrgUnit.setBoundLabelLength(100);		
        this.contPurchaseOrgUnit.setBoundLabelUnderline(true);		
        this.contPurchaseOrgUnit.setVisible(true);
        // contAddress		
        this.contAddress.setBoundLabelText(resHelper.getString("contAddress.boundLabelText"));		
        this.contAddress.setBoundLabelLength(100);		
        this.contAddress.setBoundLabelUnderline(true);		
        this.contAddress.setVisible(true);
        // contInviteType		
        this.contInviteType.setBoundLabelText(resHelper.getString("contInviteType.boundLabelText"));		
        this.contInviteType.setBoundLabelLength(100);		
        this.contInviteType.setBoundLabelUnderline(true);		
        this.contInviteType.setVisible(true);
        // contEnterpriseMaster		
        this.contEnterpriseMaster.setBoundLabelText(resHelper.getString("contEnterpriseMaster.boundLabelText"));		
        this.contEnterpriseMaster.setBoundLabelLength(100);		
        this.contEnterpriseMaster.setBoundLabelUnderline(true);		
        this.contEnterpriseMaster.setVisible(true);
        // contRegisterMoney		
        this.contRegisterMoney.setBoundLabelText(resHelper.getString("contRegisterMoney.boundLabelText"));		
        this.contRegisterMoney.setBoundLabelLength(100);		
        this.contRegisterMoney.setBoundLabelUnderline(true);		
        this.contRegisterMoney.setVisible(true);
        // contBusinessNum		
        this.contBusinessNum.setBoundLabelText(resHelper.getString("contBusinessNum.boundLabelText"));		
        this.contBusinessNum.setBoundLabelLength(100);		
        this.contBusinessNum.setBoundLabelUnderline(true);		
        this.contBusinessNum.setVisible(true);
        // contTaxRegisterNo		
        this.contTaxRegisterNo.setBoundLabelText(resHelper.getString("contTaxRegisterNo.boundLabelText"));		
        this.contTaxRegisterNo.setBoundLabelLength(100);		
        this.contTaxRegisterNo.setBoundLabelUnderline(true);		
        this.contTaxRegisterNo.setVisible(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);
        // contBizRegisterNo		
        this.contBizRegisterNo.setBoundLabelText(resHelper.getString("contBizRegisterNo.boundLabelText"));		
        this.contBizRegisterNo.setBoundLabelLength(100);		
        this.contBizRegisterNo.setBoundLabelUnderline(true);		
        this.contBizRegisterNo.setVisible(true);
        // contboEnterpriseKind		
        this.contboEnterpriseKind.setBoundLabelText(resHelper.getString("contboEnterpriseKind.boundLabelText"));		
        this.contboEnterpriseKind.setBoundLabelLength(100);		
        this.contboEnterpriseKind.setBoundLabelUnderline(true);		
        this.contboEnterpriseKind.setVisible(true);
        // contRecommended		
        this.contRecommended.setBoundLabelText(resHelper.getString("contRecommended.boundLabelText"));		
        this.contRecommended.setBoundLabelLength(100);		
        this.contRecommended.setBoundLabelUnderline(true);		
        this.contRecommended.setVisible(true);
        // contserverfees		
        this.contserverfees.setBoundLabelText(resHelper.getString("contserverfees.boundLabelText"));		
        this.contserverfees.setBoundLabelLength(100);		
        this.contserverfees.setBoundLabelUnderline(true);
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);		
        this.contState.setVisible(true);
        // contEvatemp		
        this.contEvatemp.setBoundLabelText(resHelper.getString("contEvatemp.boundLabelText"));		
        this.contEvatemp.setBoundLabelLength(100);		
        this.contEvatemp.setBoundLabelUnderline(true);		
        this.contEvatemp.setVisible(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // txtsupplierName		
        this.txtsupplierName.setVisible(true);		
        this.txtsupplierName.setHorizontalAlignment(2);		
        this.txtsupplierName.setMaxLength(100);		
        this.txtsupplierName.setRequired(false);
        // prmtPurchaseOrgUnit		
        this.prmtPurchaseOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.PurchaseItemQuery");		
        this.prmtPurchaseOrgUnit.setVisible(true);		
        this.prmtPurchaseOrgUnit.setEditable(true);		
        this.prmtPurchaseOrgUnit.setDisplayFormat("$name$");		
        this.prmtPurchaseOrgUnit.setEditFormat("$number$");		
        this.prmtPurchaseOrgUnit.setCommitFormat("$number$");		
        this.prmtPurchaseOrgUnit.setRequired(false);
        // txtAddress		
        this.txtAddress.setVisible(true);		
        this.txtAddress.setHorizontalAlignment(2);		
        this.txtAddress.setMaxLength(100);		
        this.txtAddress.setRequired(false);
        // prmtInviteType		
        this.prmtInviteType.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.supplierTypeInvoiceQuery");		
        this.prmtInviteType.setVisible(true);		
        this.prmtInviteType.setEditable(true);		
        this.prmtInviteType.setDisplayFormat("$name$");		
        this.prmtInviteType.setEditFormat("$number$");		
        this.prmtInviteType.setCommitFormat("$number$");		
        this.prmtInviteType.setRequired(false);
        // txtEnterpriseMaster		
        this.txtEnterpriseMaster.setVisible(true);		
        this.txtEnterpriseMaster.setHorizontalAlignment(2);		
        this.txtEnterpriseMaster.setMaxLength(100);		
        this.txtEnterpriseMaster.setRequired(false);
        // txtRegisterMoney		
        this.txtRegisterMoney.setVisible(true);		
        this.txtRegisterMoney.setHorizontalAlignment(2);		
        this.txtRegisterMoney.setDataType(1);		
        this.txtRegisterMoney.setSupportedEmpty(true);		
        this.txtRegisterMoney.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtRegisterMoney.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtRegisterMoney.setPrecision(2);		
        this.txtRegisterMoney.setRequired(false);
        // txtBusinessNum		
        this.txtBusinessNum.setVisible(true);		
        this.txtBusinessNum.setHorizontalAlignment(2);		
        this.txtBusinessNum.setMaxLength(100);		
        this.txtBusinessNum.setRequired(false);
        // txtTaxRegisterNo		
        this.txtTaxRegisterNo.setVisible(true);		
        this.txtTaxRegisterNo.setHorizontalAlignment(2);		
        this.txtTaxRegisterNo.setMaxLength(100);		
        this.txtTaxRegisterNo.setRequired(false);
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);
        // txtBizRegisterNo		
        this.txtBizRegisterNo.setVisible(true);		
        this.txtBizRegisterNo.setHorizontalAlignment(2);		
        this.txtBizRegisterNo.setMaxLength(100);		
        this.txtBizRegisterNo.setRequired(false);
        // txtboEnterpriseKind		
        this.txtboEnterpriseKind.setVisible(true);		
        this.txtboEnterpriseKind.setHorizontalAlignment(2);		
        this.txtboEnterpriseKind.setMaxLength(100);		
        this.txtboEnterpriseKind.setRequired(false);
        // txtRecommended		
        this.txtRecommended.setVisible(true);		
        this.txtRecommended.setHorizontalAlignment(2);		
        this.txtRecommended.setMaxLength(100);		
        this.txtRecommended.setRequired(false);
        // txtserverfees		
        this.txtserverfees.setVisible(true);		
        this.txtserverfees.setHorizontalAlignment(2);		
        this.txtserverfees.setDataType(1);		
        this.txtserverfees.setSupportedEmpty(true);		
        this.txtserverfees.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtserverfees.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtserverfees.setPrecision(2);		
        this.txtserverfees.setRequired(false);
        // State		
        this.State.setVisible(true);		
        this.State.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.markesupplier.subase.SupplierState").toArray());		
        this.State.setRequired(false);
        // prmtEvatemp		
        this.prmtEvatemp.setQueryInfo("com.kingdee.eas.port.pm.base.app.EvaluationTemplateQuery");		
        this.prmtEvatemp.setVisible(true);		
        this.prmtEvatemp.setEditable(true);		
        this.prmtEvatemp.setDisplayFormat("$templateName$");		
        this.prmtEvatemp.setEditFormat("$number$");		
        this.prmtEvatemp.setCommitFormat("$number$");		
        this.prmtEvatemp.setRequired(false);
        		prmtEvatemp.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.pm.base.client.EvaluationTemplateListUI prmtEvatemp_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (prmtEvatemp_F7ListUI == null) {
					try {
						prmtEvatemp_F7ListUI = new com.kingdee.eas.port.pm.base.client.EvaluationTemplateListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmtEvatemp_F7ListUI));
					prmtEvatemp_F7ListUI.setF7Use(true,ctx);
					prmtEvatemp.setSelector(prmtEvatemp_F7ListUI);
				}
			}
		});
					
        this.prmtEvatemp.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtEvatemp_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setTimeEnabled(true);		
        this.kDDateLastUpdateTime.setEnabled(false);
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setEnabled(false);
        // prmtsysSupplier		
        this.prmtsysSupplier.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.PSupplierQuery");		
        this.prmtsysSupplier.setVisible(true);		
        this.prmtsysSupplier.setEditable(true);		
        this.prmtsysSupplier.setDisplayFormat("$name$");		
        this.prmtsysSupplier.setEditFormat("$number$");		
        this.prmtsysSupplier.setCommitFormat("$number$");		
        this.prmtsysSupplier.setRequired(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // pkauditDate		
        this.pkauditDate.setVisible(true);		
        this.pkauditDate.setRequired(false);
        // prmtLevel		
        this.prmtLevel.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.MarketLevelSetUpQuery");		
        this.prmtLevel.setVisible(false);		
        this.prmtLevel.setEditable(true);		
        this.prmtLevel.setDisplayFormat("$name$");		
        this.prmtLevel.setEditFormat("$number$");		
        this.prmtLevel.setCommitFormat("$number$");		
        this.prmtLevel.setRequired(false);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"typeNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"typeName\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"peopleSum\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"remake\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{typeNumber}</t:Cell><t:Cell>$Resource{typeName}</t:Cell><t:Cell>$Resource{peopleSum}</t:Cell><t:Cell>$Resource{remake}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));

                this.kdtEntrys.putBindContents("editData",new String[] {"id","typeNumber","typeName","peopleSum","remake"});


        this.kdtEntrys.checkParsed();
        KDTextField kdtEntrys_typeNumber_TextField = new KDTextField();
        kdtEntrys_typeNumber_TextField.setName("kdtEntrys_typeNumber_TextField");
        kdtEntrys_typeNumber_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_typeNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_typeNumber_TextField);
        this.kdtEntrys.getColumn("typeNumber").setEditor(kdtEntrys_typeNumber_CellEditor);
        KDTextField kdtEntrys_typeName_TextField = new KDTextField();
        kdtEntrys_typeName_TextField.setName("kdtEntrys_typeName_TextField");
        kdtEntrys_typeName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_typeName_CellEditor = new KDTDefaultCellEditor(kdtEntrys_typeName_TextField);
        this.kdtEntrys.getColumn("typeName").setEditor(kdtEntrys_typeName_CellEditor);
        KDFormattedTextField kdtEntrys_peopleSum_TextField = new KDFormattedTextField();
        kdtEntrys_peopleSum_TextField.setName("kdtEntrys_peopleSum_TextField");
        kdtEntrys_peopleSum_TextField.setVisible(true);
        kdtEntrys_peopleSum_TextField.setEditable(true);
        kdtEntrys_peopleSum_TextField.setHorizontalAlignment(2);
        kdtEntrys_peopleSum_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntrys_peopleSum_CellEditor = new KDTDefaultCellEditor(kdtEntrys_peopleSum_TextField);
        this.kdtEntrys.getColumn("peopleSum").setEditor(kdtEntrys_peopleSum_CellEditor);
        KDTextArea kdtEntrys_remake_TextArea = new KDTextArea();
        kdtEntrys_remake_TextArea.setName("kdtEntrys_remake_TextArea");
        kdtEntrys_remake_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtEntrys_remake_CellEditor = new KDTDefaultCellEditor(kdtEntrys_remake_TextArea);
        this.kdtEntrys.getColumn("remake").setEditor(kdtEntrys_remake_CellEditor);
        // prmtSupplierFileType		
        this.prmtSupplierFileType.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.MarketSupplierFileTypQuery");		
        this.prmtSupplierFileType.setVisible(true);		
        this.prmtSupplierFileType.setEditable(true);		
        this.prmtSupplierFileType.setDisplayFormat("$name$");		
        this.prmtSupplierFileType.setEditFormat("$number$");		
        this.prmtSupplierFileType.setCommitFormat("$number$");		
        this.prmtSupplierFileType.setRequired(false);
        // prmtVisibility		
        this.prmtVisibility.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.VisibilityQuery");		
        this.prmtVisibility.setVisible(true);		
        this.prmtVisibility.setEditable(true);		
        this.prmtVisibility.setDisplayFormat("$name$");		
        this.prmtVisibility.setEditFormat("$number$");		
        this.prmtVisibility.setCommitFormat("$number$");		
        this.prmtVisibility.setRequired(false);
        // prmtQuaLevel		
        this.prmtQuaLevel.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.MarktQuaLevelQuery");		
        this.prmtQuaLevel.setVisible(true);		
        this.prmtQuaLevel.setEditable(true);		
        this.prmtQuaLevel.setDisplayFormat("$name$");		
        this.prmtQuaLevel.setEditFormat("$number$");		
        this.prmtQuaLevel.setCommitFormat("$number$");		
        this.prmtQuaLevel.setRequired(false);
        // prmtSupplierSplAreaEntry		
        this.prmtSupplierSplAreaEntry.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.MarketSplAreaQuery");		
        this.prmtSupplierSplAreaEntry.setVisible(true);		
        this.prmtSupplierSplAreaEntry.setEditable(true);		
        this.prmtSupplierSplAreaEntry.setDisplayFormat("$name$");		
        this.prmtSupplierSplAreaEntry.setEditFormat("$number$");		
        this.prmtSupplierSplAreaEntry.setCommitFormat("$number$");		
        this.prmtSupplierSplAreaEntry.setRequired(false);
        // prmtSupplierBusinessMode		
        this.prmtSupplierBusinessMode.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.MarketSupplierBusinessModeQuery");		
        this.prmtSupplierBusinessMode.setVisible(true);		
        this.prmtSupplierBusinessMode.setEditable(true);		
        this.prmtSupplierBusinessMode.setDisplayFormat("$name$");		
        this.prmtSupplierBusinessMode.setEditFormat("$number$");		
        this.prmtSupplierBusinessMode.setCommitFormat("$number$");		
        this.prmtSupplierBusinessMode.setRequired(false);
        // prmtServiceType		
        this.prmtServiceType.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.MarketSplServiceTypeQuery");		
        this.prmtServiceType.setVisible(true);		
        this.prmtServiceType.setEditable(true);		
        this.prmtServiceType.setDisplayFormat("$name$");		
        this.prmtServiceType.setEditFormat("$number$");		
        this.prmtServiceType.setCommitFormat("$number$");		
        this.prmtServiceType.setRequired(false);
        // kDPanel6		
        this.kDPanel6.setToolTipText(resHelper.getString("kDPanel6.toolTipText"));
        // kDPanel7
        // kDPanel5		
        this.kDPanel5.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel5.border.title")));
        // contPeopleCount		
        this.contPeopleCount.setBoundLabelText(resHelper.getString("contPeopleCount.boundLabelText"));		
        this.contPeopleCount.setBoundLabelLength(100);		
        this.contPeopleCount.setBoundLabelUnderline(true);		
        this.contPeopleCount.setVisible(true);
        // contProvince		
        this.contProvince.setBoundLabelText(resHelper.getString("contProvince.boundLabelText"));		
        this.contProvince.setBoundLabelLength(100);		
        this.contProvince.setBoundLabelUnderline(true);		
        this.contProvince.setVisible(true);
        // contCity		
        this.contCity.setBoundLabelText(resHelper.getString("contCity.boundLabelText"));		
        this.contCity.setBoundLabelLength(100);		
        this.contCity.setBoundLabelUnderline(true);		
        this.contCity.setVisible(true);
        // contLinkFax		
        this.contLinkFax.setBoundLabelText(resHelper.getString("contLinkFax.boundLabelText"));		
        this.contLinkFax.setBoundLabelLength(100);		
        this.contLinkFax.setBoundLabelUnderline(true);		
        this.contLinkFax.setVisible(true);
        // contLinkPhone		
        this.contLinkPhone.setBoundLabelText(resHelper.getString("contLinkPhone.boundLabelText"));		
        this.contLinkPhone.setBoundLabelLength(100);		
        this.contLinkPhone.setBoundLabelUnderline(true);		
        this.contLinkPhone.setVisible(true);
        // contWebSite		
        this.contWebSite.setBoundLabelText(resHelper.getString("contWebSite.boundLabelText"));		
        this.contWebSite.setBoundLabelLength(100);		
        this.contWebSite.setBoundLabelUnderline(true);		
        this.contWebSite.setVisible(true);
        // contPostNumber		
        this.contPostNumber.setBoundLabelText(resHelper.getString("contPostNumber.boundLabelText"));		
        this.contPostNumber.setBoundLabelLength(100);		
        this.contPostNumber.setBoundLabelUnderline(true);		
        this.contPostNumber.setVisible(true);
        // contLinkMail		
        this.contLinkMail.setBoundLabelText(resHelper.getString("contLinkMail.boundLabelText"));		
        this.contLinkMail.setBoundLabelLength(100);		
        this.contLinkMail.setBoundLabelUnderline(true);		
        this.contLinkMail.setVisible(true);
        // kDContainer5		
        this.kDContainer5.setEnableActive(false);		
        this.kDContainer5.setTitle(resHelper.getString("kDContainer5.title"));
        // kDContainer2		
        this.kDContainer2.setEnableActive(false);		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kDContainer4		
        this.kDContainer4.setEnableActive(false);		
        this.kDContainer4.setTitle(resHelper.getString("kDContainer4.title"));
        // txtPeopleCount		
        this.txtPeopleCount.setVisible(true);		
        this.txtPeopleCount.setHorizontalAlignment(2);		
        this.txtPeopleCount.setDataType(0);		
        this.txtPeopleCount.setSupportedEmpty(true);		
        this.txtPeopleCount.setRequired(false);
        // txtProvince		
        this.txtProvince.setVisible(true);		
        this.txtProvince.setHorizontalAlignment(2);		
        this.txtProvince.setMaxLength(100);		
        this.txtProvince.setRequired(false);
        // txtCity		
        this.txtCity.setVisible(true);		
        this.txtCity.setHorizontalAlignment(2);		
        this.txtCity.setMaxLength(100);		
        this.txtCity.setRequired(false);
        // txtLinkFax		
        this.txtLinkFax.setVisible(true);		
        this.txtLinkFax.setHorizontalAlignment(2);		
        this.txtLinkFax.setMaxLength(100);		
        this.txtLinkFax.setRequired(false);
        // txtLinkPhone		
        this.txtLinkPhone.setVisible(true);		
        this.txtLinkPhone.setHorizontalAlignment(2);		
        this.txtLinkPhone.setMaxLength(100);		
        this.txtLinkPhone.setRequired(false);
        // txtWebSite		
        this.txtWebSite.setVisible(true);		
        this.txtWebSite.setHorizontalAlignment(2);		
        this.txtWebSite.setMaxLength(100);		
        this.txtWebSite.setRequired(false);
        // txtPostNumber		
        this.txtPostNumber.setVisible(true);		
        this.txtPostNumber.setHorizontalAlignment(2);		
        this.txtPostNumber.setMaxLength(100);		
        this.txtPostNumber.setRequired(false);
        // txtLinkMail		
        this.txtLinkMail.setVisible(true);		
        this.txtLinkMail.setHorizontalAlignment(2);		
        this.txtLinkMail.setMaxLength(100);		
        this.txtLinkMail.setRequired(false);
        // kdtEntryAtt
		String kdtEntryAttStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"attNumber\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"attName\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"attlist\" t:width=\"350\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{attNumber}</t:Cell><t:Cell>$Resource{attName}</t:Cell><t:Cell>$Resource{attlist}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntryAtt.setFormatXml(resHelper.translateString("kdtEntryAtt",kdtEntryAttStrXML));

                this.kdtEntryAtt.putBindContents("editData",new String[] {"seq","attNumber","attName","attlist"});


        this.kdtEntryAtt.checkParsed();
        KDTextField kdtEntryAtt_attNumber_TextField = new KDTextField();
        kdtEntryAtt_attNumber_TextField.setName("kdtEntryAtt_attNumber_TextField");
        kdtEntryAtt_attNumber_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryAtt_attNumber_CellEditor = new KDTDefaultCellEditor(kdtEntryAtt_attNumber_TextField);
        this.kdtEntryAtt.getColumn("attNumber").setEditor(kdtEntryAtt_attNumber_CellEditor);
        KDTextField kdtEntryAtt_attName_TextField = new KDTextField();
        kdtEntryAtt_attName_TextField.setName("kdtEntryAtt_attName_TextField");
        kdtEntryAtt_attName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryAtt_attName_CellEditor = new KDTDefaultCellEditor(kdtEntryAtt_attName_TextField);
        this.kdtEntryAtt.getColumn("attName").setEditor(kdtEntryAtt_attName_CellEditor);
        KDTextField kdtEntryAtt_attlist_TextField = new KDTextField();
        kdtEntryAtt_attlist_TextField.setName("kdtEntryAtt_attlist_TextField");
        kdtEntryAtt_attlist_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryAtt_attlist_CellEditor = new KDTDefaultCellEditor(kdtEntryAtt_attlist_TextField);
        this.kdtEntryAtt.getColumn("attlist").setEditor(kdtEntryAtt_attlist_CellEditor);
        // contmarketRemake		
        this.contmarketRemake.setBoundLabelText(resHelper.getString("contmarketRemake.boundLabelText"));		
        this.contmarketRemake.setBoundLabelLength(0);		
        this.contmarketRemake.setBoundLabelUnderline(true);		
        this.contmarketRemake.setVisible(true);
        // scrollPanemarketRemake
        // txtmarketRemake		
        this.txtmarketRemake.setVisible(true);		
        this.txtmarketRemake.setRequired(false);		
        this.txtmarketRemake.setMaxLength(500);
        // kdtEntryPerson
		String kdtEntryPersonStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"personName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"position\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"workPhone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"personFax\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isDefault\" t:width=\"111\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"email\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contact\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{personName}</t:Cell><t:Cell>$Resource{position}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{workPhone}</t:Cell><t:Cell>$Resource{personFax}</t:Cell><t:Cell>$Resource{isDefault}</t:Cell><t:Cell>$Resource{email}</t:Cell><t:Cell>$Resource{contact}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntryPerson.setFormatXml(resHelper.translateString("kdtEntryPerson",kdtEntryPersonStrXML));

                this.kdtEntryPerson.putBindContents("editData",new String[] {"seq","personName","position","phone","workPhone","personFax","isDefault","email","contact"});


        this.kdtEntryPerson.checkParsed();
        KDTextField kdtEntryPerson_personName_TextField = new KDTextField();
        kdtEntryPerson_personName_TextField.setName("kdtEntryPerson_personName_TextField");
        kdtEntryPerson_personName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryPerson_personName_CellEditor = new KDTDefaultCellEditor(kdtEntryPerson_personName_TextField);
        this.kdtEntryPerson.getColumn("personName").setEditor(kdtEntryPerson_personName_CellEditor);
        KDTextField kdtEntryPerson_position_TextField = new KDTextField();
        kdtEntryPerson_position_TextField.setName("kdtEntryPerson_position_TextField");
        kdtEntryPerson_position_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryPerson_position_CellEditor = new KDTDefaultCellEditor(kdtEntryPerson_position_TextField);
        this.kdtEntryPerson.getColumn("position").setEditor(kdtEntryPerson_position_CellEditor);
        KDTextField kdtEntryPerson_phone_TextField = new KDTextField();
        kdtEntryPerson_phone_TextField.setName("kdtEntryPerson_phone_TextField");
        kdtEntryPerson_phone_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryPerson_phone_CellEditor = new KDTDefaultCellEditor(kdtEntryPerson_phone_TextField);
        this.kdtEntryPerson.getColumn("phone").setEditor(kdtEntryPerson_phone_CellEditor);
        KDTextField kdtEntryPerson_workPhone_TextField = new KDTextField();
        kdtEntryPerson_workPhone_TextField.setName("kdtEntryPerson_workPhone_TextField");
        kdtEntryPerson_workPhone_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryPerson_workPhone_CellEditor = new KDTDefaultCellEditor(kdtEntryPerson_workPhone_TextField);
        this.kdtEntryPerson.getColumn("workPhone").setEditor(kdtEntryPerson_workPhone_CellEditor);
        KDTextField kdtEntryPerson_personFax_TextField = new KDTextField();
        kdtEntryPerson_personFax_TextField.setName("kdtEntryPerson_personFax_TextField");
        kdtEntryPerson_personFax_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryPerson_personFax_CellEditor = new KDTDefaultCellEditor(kdtEntryPerson_personFax_TextField);
        this.kdtEntryPerson.getColumn("personFax").setEditor(kdtEntryPerson_personFax_CellEditor);
        KDCheckBox kdtEntryPerson_isDefault_CheckBox = new KDCheckBox();
        kdtEntryPerson_isDefault_CheckBox.setName("kdtEntryPerson_isDefault_CheckBox");
        KDTDefaultCellEditor kdtEntryPerson_isDefault_CellEditor = new KDTDefaultCellEditor(kdtEntryPerson_isDefault_CheckBox);
        this.kdtEntryPerson.getColumn("isDefault").setEditor(kdtEntryPerson_isDefault_CellEditor);
        KDTextField kdtEntryPerson_email_TextField = new KDTextField();
        kdtEntryPerson_email_TextField.setName("kdtEntryPerson_email_TextField");
        kdtEntryPerson_email_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryPerson_email_CellEditor = new KDTDefaultCellEditor(kdtEntryPerson_email_TextField);
        this.kdtEntryPerson.getColumn("email").setEditor(kdtEntryPerson_email_CellEditor);
        KDTextField kdtEntryPerson_contact_TextField = new KDTextField();
        kdtEntryPerson_contact_TextField.setName("kdtEntryPerson_contact_TextField");
        kdtEntryPerson_contact_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntryPerson_contact_CellEditor = new KDTDefaultCellEditor(kdtEntryPerson_contact_TextField);
        this.kdtEntryPerson.getColumn("contact").setEditor(kdtEntryPerson_contact_CellEditor);
        // kDContainer6		
        this.kDContainer6.setEnableActive(false);		
        this.kDContainer6.setTitle(resHelper.getString("kDContainer6.title"));
        // kdtE4
		String kdtE4StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"EvaluationIndex\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"Description\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isQualified\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{EvaluationIndex}</t:Cell><t:Cell>$Resource{Description}</t:Cell><t:Cell>$Resource{isQualified}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE4.setFormatXml(resHelper.translateString("kdtE4",kdtE4StrXML));

                this.kdtE4.putBindContents("editData",new String[] {"seq","EvaluationIndex","Description","isQualified"});


        this.kdtE4.checkParsed();
        KDTextField kdtE4_EvaluationIndex_TextField = new KDTextField();
        kdtE4_EvaluationIndex_TextField.setName("kdtE4_EvaluationIndex_TextField");
        kdtE4_EvaluationIndex_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE4_EvaluationIndex_CellEditor = new KDTDefaultCellEditor(kdtE4_EvaluationIndex_TextField);
        this.kdtE4.getColumn("EvaluationIndex").setEditor(kdtE4_EvaluationIndex_CellEditor);
        KDTextArea kdtE4_Description_TextArea = new KDTextArea();
        kdtE4_Description_TextArea.setName("kdtE4_Description_TextArea");
        kdtE4_Description_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtE4_Description_CellEditor = new KDTDefaultCellEditor(kdtE4_Description_TextArea);
        this.kdtE4.getColumn("Description").setEditor(kdtE4_Description_CellEditor);
        KDCheckBox kdtE4_isQualified_CheckBox = new KDCheckBox();
        kdtE4_isQualified_CheckBox.setName("kdtE4_isQualified_CheckBox");
        KDTDefaultCellEditor kdtE4_isQualified_CellEditor = new KDTDefaultCellEditor(kdtE4_isQualified_CheckBox);
        this.kdtE4.getColumn("isQualified").setEditor(kdtE4_isQualified_CellEditor);
        // contPunish		
        this.contPunish.setBoundLabelText(resHelper.getString("contPunish.boundLabelText"));		
        this.contPunish.setBoundLabelLength(0);		
        this.contPunish.setBoundLabelUnderline(true);		
        this.contPunish.setVisible(true);
        // scrollPanePunish
        // txtPunish		
        this.txtPunish.setVisible(true);		
        this.txtPunish.setRequired(false);		
        this.txtPunish.setMaxLength(500);
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
        this.setBounds(new Rectangle(0, 0, 1013, 768));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 768));
        contDescription.setBounds(new Rectangle(965, 214, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(965, 214, 270, 19, 0));
        kDTabbedPane1.setBounds(new Rectangle(4, 2, 1006, 764));
        this.add(kDTabbedPane1, new KDLayout.Constraints(4, 2, 1006, 764, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer1.setBounds(new Rectangle(6, 957, 134, 43));
        this.add(kDContainer1, new KDLayout.Constraints(6, 957, 134, 43, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(kDPanel4, BorderLayout.CENTER);
        //kDPanel4
        kDPanel4.setLayout(new KDLayout());
        kDPanel4.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1005, 731));        kDPanel3.setBounds(new Rectangle(3, 3, 991, 179));
        kDPanel4.add(kDPanel3, new KDLayout.Constraints(3, 3, 991, 179, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateUser.setBounds(new Rectangle(365, 542, 270, 19));
        kDPanel4.add(contLastUpdateUser, new KDLayout.Constraints(365, 542, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(365, 517, 270, 19));
        kDPanel4.add(contCreator, new KDLayout.Constraints(365, 517, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(724, 542, 270, 19));
        kDPanel4.add(contLastUpdateTime, new KDLayout.Constraints(724, 542, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(724, 517, 270, 19));
        kDPanel4.add(contCreateTime, new KDLayout.Constraints(724, 517, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contsysSupplier.setBounds(new Rectangle(366, 726, 270, 19));
        kDPanel4.add(contsysSupplier, new KDLayout.Constraints(366, 726, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(6, 517, 270, 19));
        kDPanel4.add(contAuditor, new KDLayout.Constraints(6, 517, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contauditDate.setBounds(new Rectangle(6, 542, 270, 19));
        kDPanel4.add(contauditDate, new KDLayout.Constraints(6, 542, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLevel.setBounds(new Rectangle(10, 725, 270, 19));
        kDPanel4.add(contLevel, new KDLayout.Constraints(10, 725, 270, 19, 0));
        kDContainer3.setBounds(new Rectangle(863, 683, 101, 36));
        kDPanel4.add(kDContainer3, new KDLayout.Constraints(863, 683, 101, 36, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupplierFileType.setBounds(new Rectangle(22, 700, 270, 19));
        kDPanel4.add(contSupplierFileType, new KDLayout.Constraints(22, 700, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contVisibility.setBounds(new Rectangle(303, 701, 270, 19));
        kDPanel4.add(contVisibility, new KDLayout.Constraints(303, 701, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contQuaLevel.setBounds(new Rectangle(586, 700, 270, 19));
        kDPanel4.add(contQuaLevel, new KDLayout.Constraints(586, 700, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupplierSplAreaEntry.setBounds(new Rectangle(304, 675, 270, 19));
        kDPanel4.add(contSupplierSplAreaEntry, new KDLayout.Constraints(304, 675, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSupplierBusinessMode.setBounds(new Rectangle(25, 675, 270, 19));
        kDPanel4.add(contSupplierBusinessMode, new KDLayout.Constraints(25, 675, 270, 19, 0));
        contServiceType.setBounds(new Rectangle(581, 676, 270, 19));
        kDPanel4.add(contServiceType, new KDLayout.Constraints(581, 676, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane2.setBounds(new Rectangle(3, 184, 991, 328));
        kDPanel4.add(kDTabbedPane2, new KDLayout.Constraints(3, 184, 991, 328, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(3, 3, 991, 179));        contNumber.setBounds(new Rectangle(14, 15, 270, 19));
        kDPanel3.add(contNumber, new KDLayout.Constraints(14, 15, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsupplierName.setBounds(new Rectangle(355, 15, 270, 19));
        kDPanel3.add(contsupplierName, new KDLayout.Constraints(355, 15, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPurchaseOrgUnit.setBounds(new Rectangle(704, 40, 270, 19));
        kDPanel3.add(contPurchaseOrgUnit, new KDLayout.Constraints(704, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAddress.setBounds(new Rectangle(355, 89, 270, 19));
        kDPanel3.add(contAddress, new KDLayout.Constraints(355, 89, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteType.setBounds(new Rectangle(704, 15, 270, 19));
        kDPanel3.add(contInviteType, new KDLayout.Constraints(704, 15, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contEnterpriseMaster.setBounds(new Rectangle(14, 40, 270, 19));
        kDPanel3.add(contEnterpriseMaster, new KDLayout.Constraints(14, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRegisterMoney.setBounds(new Rectangle(355, 40, 270, 19));
        kDPanel3.add(contRegisterMoney, new KDLayout.Constraints(355, 40, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBusinessNum.setBounds(new Rectangle(14, 65, 270, 19));
        kDPanel3.add(contBusinessNum, new KDLayout.Constraints(14, 65, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTaxRegisterNo.setBounds(new Rectangle(355, 65, 270, 19));
        kDPanel3.add(contTaxRegisterNo, new KDLayout.Constraints(355, 65, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(14, 90, 270, 19));
        kDPanel3.add(contBizDate, new KDLayout.Constraints(14, 90, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizRegisterNo.setBounds(new Rectangle(704, 65, 270, 19));
        kDPanel3.add(contBizRegisterNo, new KDLayout.Constraints(704, 65, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contboEnterpriseKind.setBounds(new Rectangle(355, 117, 270, 19));
        kDPanel3.add(contboEnterpriseKind, new KDLayout.Constraints(355, 117, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRecommended.setBounds(new Rectangle(14, 144, 611, 19));
        kDPanel3.add(contRecommended, new KDLayout.Constraints(14, 144, 611, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contserverfees.setBounds(new Rectangle(14, 117, 270, 19));
        kDPanel3.add(contserverfees, new KDLayout.Constraints(14, 117, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contState.setBounds(new Rectangle(704, 89, 270, 19));
        kDPanel3.add(contState, new KDLayout.Constraints(704, 89, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contEvatemp.setBounds(new Rectangle(704, 117, 270, 19));
        kDPanel3.add(contEvatemp, new KDLayout.Constraints(704, 117, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contsupplierName
        contsupplierName.setBoundEditor(txtsupplierName);
        //contPurchaseOrgUnit
        contPurchaseOrgUnit.setBoundEditor(prmtPurchaseOrgUnit);
        //contAddress
        contAddress.setBoundEditor(txtAddress);
        //contInviteType
        contInviteType.setBoundEditor(prmtInviteType);
        //contEnterpriseMaster
        contEnterpriseMaster.setBoundEditor(txtEnterpriseMaster);
        //contRegisterMoney
        contRegisterMoney.setBoundEditor(txtRegisterMoney);
        //contBusinessNum
        contBusinessNum.setBoundEditor(txtBusinessNum);
        //contTaxRegisterNo
        contTaxRegisterNo.setBoundEditor(txtTaxRegisterNo);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contBizRegisterNo
        contBizRegisterNo.setBoundEditor(txtBizRegisterNo);
        //contboEnterpriseKind
        contboEnterpriseKind.setBoundEditor(txtboEnterpriseKind);
        //contRecommended
        contRecommended.setBoundEditor(txtRecommended);
        //contserverfees
        contserverfees.setBoundEditor(txtserverfees);
        //contState
        contState.setBoundEditor(State);
        //contEvatemp
        contEvatemp.setBoundEditor(prmtEvatemp);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDateLastUpdateTime);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contsysSupplier
        contsysSupplier.setBoundEditor(prmtsysSupplier);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contauditDate
        contauditDate.setBoundEditor(pkauditDate);
        //contLevel
        contLevel.setBoundEditor(prmtLevel);
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryInfo(),null,false);
        kDContainer3.getContentPane().add(kdtEntrys_detailPanel, BorderLayout.NORTH);
        //contSupplierFileType
        contSupplierFileType.setBoundEditor(prmtSupplierFileType);
        //contVisibility
        contVisibility.setBoundEditor(prmtVisibility);
        //contQuaLevel
        contQuaLevel.setBoundEditor(prmtQuaLevel);
        //contSupplierSplAreaEntry
        contSupplierSplAreaEntry.setBoundEditor(prmtSupplierSplAreaEntry);
        //contSupplierBusinessMode
        contSupplierBusinessMode.setBoundEditor(prmtSupplierBusinessMode);
        //contServiceType
        contServiceType.setBoundEditor(prmtServiceType);
        //kDTabbedPane2
        kDTabbedPane2.add(kDPanel6, resHelper.getString("kDPanel6.constraints"));
        kDTabbedPane2.add(kDPanel7, resHelper.getString("kDPanel7.constraints"));
        //kDPanel6
        kDPanel6.setLayout(new KDLayout());
        kDPanel6.putClientProperty("OriginalBounds", new Rectangle(0, 0, 990, 295));        kDPanel5.setBounds(new Rectangle(0, 0, 985, 313));
        kDPanel6.add(kDPanel5, new KDLayout.Constraints(0, 0, 985, 313, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel5
        kDPanel5.setLayout(new KDLayout());
        kDPanel5.putClientProperty("OriginalBounds", new Rectangle(0, 0, 985, 313));        contPeopleCount.setBounds(new Rectangle(365, 61, 270, 19));
        kDPanel5.add(contPeopleCount, new KDLayout.Constraints(365, 61, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProvince.setBounds(new Rectangle(20, 13, 270, 19));
        kDPanel5.add(contProvince, new KDLayout.Constraints(20, 13, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCity.setBounds(new Rectangle(365, 15, 270, 19));
        kDPanel5.add(contCity, new KDLayout.Constraints(365, 15, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkFax.setBounds(new Rectangle(20, 37, 270, 19));
        kDPanel5.add(contLinkFax, new KDLayout.Constraints(20, 37, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkPhone.setBounds(new Rectangle(698, 13, 270, 19));
        kDPanel5.add(contLinkPhone, new KDLayout.Constraints(698, 13, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contWebSite.setBounds(new Rectangle(698, 37, 270, 19));
        kDPanel5.add(contWebSite, new KDLayout.Constraints(698, 37, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPostNumber.setBounds(new Rectangle(365, 38, 270, 19));
        kDPanel5.add(contPostNumber, new KDLayout.Constraints(365, 38, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLinkMail.setBounds(new Rectangle(20, 61, 270, 19));
        kDPanel5.add(contLinkMail, new KDLayout.Constraints(20, 61, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer5.setBounds(new Rectangle(482, 83, 488, 105));
        kDPanel5.add(kDContainer5, new KDLayout.Constraints(482, 83, 488, 105, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer2.setBounds(new Rectangle(15, 83, 457, 108));
        kDPanel5.add(kDContainer2, new KDLayout.Constraints(15, 83, 457, 108, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer4.setBounds(new Rectangle(14, 193, 958, 94));
        kDPanel5.add(kDContainer4, new KDLayout.Constraints(14, 193, 958, 94, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contPeopleCount
        contPeopleCount.setBoundEditor(txtPeopleCount);
        //contProvince
        contProvince.setBoundEditor(txtProvince);
        //contCity
        contCity.setBoundEditor(txtCity);
        //contLinkFax
        contLinkFax.setBoundEditor(txtLinkFax);
        //contLinkPhone
        contLinkPhone.setBoundEditor(txtLinkPhone);
        //contWebSite
        contWebSite.setBoundEditor(txtWebSite);
        //contPostNumber
        contPostNumber.setBoundEditor(txtPostNumber);
        //contLinkMail
        contLinkMail.setBoundEditor(txtLinkMail);
        //kDContainer5
kDContainer5.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntryAtt_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntryAtt,new com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryAttInfo(),null,false);
        kDContainer5.getContentPane().add(kdtEntryAtt_detailPanel, BorderLayout.CENTER);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(contmarketRemake, BorderLayout.CENTER);
        //contmarketRemake
        contmarketRemake.setBoundEditor(scrollPanemarketRemake);
        //scrollPanemarketRemake
        scrollPanemarketRemake.getViewport().add(txtmarketRemake, null);
        //kDContainer4
kDContainer4.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntryPerson_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntryPerson,new com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryPersonInfo(),null,false);
        kDContainer4.getContentPane().add(kdtEntryPerson_detailPanel, BorderLayout.CENTER);
        //kDPanel7
kDPanel7.setLayout(new BorderLayout(0, 0));        kDPanel7.add(kDContainer6, BorderLayout.CENTER);
        //kDContainer6
kDContainer6.getContentPane().setLayout(new BorderLayout(0, 0));        kdtE4_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE4,new com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockE4Info(),null,false);
        kDContainer6.getContentPane().add(kdtE4_detailPanel, BorderLayout.CENTER);
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1005, 731));        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(contPunish, BorderLayout.CENTER);
        //contPunish
        contPunish.setBoundEditor(scrollPanePunish);
        //scrollPanePunish
        scrollPanePunish.getViewport().add(txtPunish, null);

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
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
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
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
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
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("supplierName", String.class, this.txtsupplierName, "text");
		dataBinder.registerBinding("PurchaseOrgUnit", com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo.class, this.prmtPurchaseOrgUnit, "data");
		dataBinder.registerBinding("Address", String.class, this.txtAddress, "text");
		dataBinder.registerBinding("InviteType", com.kingdee.eas.port.markesupplier.subase.SupplierTypeInfo.class, this.prmtInviteType, "data");
		dataBinder.registerBinding("EnterpriseMaster", String.class, this.txtEnterpriseMaster, "text");
		dataBinder.registerBinding("RegisterMoney", java.math.BigDecimal.class, this.txtRegisterMoney, "value");
		dataBinder.registerBinding("BusinessNum", String.class, this.txtBusinessNum, "text");
		dataBinder.registerBinding("TaxRegisterNo", String.class, this.txtTaxRegisterNo, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("BizRegisterNo", String.class, this.txtBizRegisterNo, "text");
		dataBinder.registerBinding("boEnterpriseKind", String.class, this.txtboEnterpriseKind, "text");
		dataBinder.registerBinding("Recommended", String.class, this.txtRecommended, "text");
		dataBinder.registerBinding("serverfees", java.math.BigDecimal.class, this.txtserverfees, "value");
		dataBinder.registerBinding("State", com.kingdee.eas.port.markesupplier.subase.SupplierState.class, this.State, "selectedItem");
		dataBinder.registerBinding("Evatemp", com.kingdee.eas.port.pm.base.EvaluationTemplateInfo.class, this.prmtEvatemp, "data");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("sysSupplier", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtsysSupplier, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.pkauditDate, "value");
		dataBinder.registerBinding("Level", com.kingdee.eas.port.markesupplier.subase.MarketLevelSetUpInfo.class, this.prmtLevel, "data");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.typeNumber", String.class, this.kdtEntrys, "typeNumber.text");
		dataBinder.registerBinding("entrys.typeName", String.class, this.kdtEntrys, "typeName.text");
		dataBinder.registerBinding("entrys.peopleSum", int.class, this.kdtEntrys, "peopleSum.text");
		dataBinder.registerBinding("entrys.remake", String.class, this.kdtEntrys, "remake.text");
		dataBinder.registerBinding("SupplierFileType", com.kingdee.eas.port.markesupplier.subase.MarketSupplierFileTypInfo.class, this.prmtSupplierFileType, "data");
		dataBinder.registerBinding("Visibility", com.kingdee.eas.port.markesupplier.subase.VisibilityInfo.class, this.prmtVisibility, "data");
		dataBinder.registerBinding("QuaLevel", com.kingdee.eas.port.markesupplier.subase.MarktQuaLevelInfo.class, this.prmtQuaLevel, "data");
		dataBinder.registerBinding("SupplierSplAreaEntry", com.kingdee.eas.port.markesupplier.subase.MarketSplAreaInfo.class, this.prmtSupplierSplAreaEntry, "data");
		dataBinder.registerBinding("SupplierBusinessMode", com.kingdee.eas.port.markesupplier.subase.MarketSupplierBusinessModeInfo.class, this.prmtSupplierBusinessMode, "data");
		dataBinder.registerBinding("ServiceType", com.kingdee.eas.port.markesupplier.subase.MarketSplServiceTypeInfo.class, this.prmtServiceType, "data");
		dataBinder.registerBinding("PeopleCount", int.class, this.txtPeopleCount, "value");
		dataBinder.registerBinding("Province", String.class, this.txtProvince, "text");
		dataBinder.registerBinding("City", String.class, this.txtCity, "text");
		dataBinder.registerBinding("LinkFax", String.class, this.txtLinkFax, "text");
		dataBinder.registerBinding("LinkPhone", String.class, this.txtLinkPhone, "text");
		dataBinder.registerBinding("WebSite", String.class, this.txtWebSite, "text");
		dataBinder.registerBinding("PostNumber", String.class, this.txtPostNumber, "text");
		dataBinder.registerBinding("LinkMail", String.class, this.txtLinkMail, "text");
		dataBinder.registerBinding("EntryAtt.seq", int.class, this.kdtEntryAtt, "seq.text");
		dataBinder.registerBinding("EntryAtt", com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryAttInfo.class, this.kdtEntryAtt, "userObject");
		dataBinder.registerBinding("EntryAtt.attNumber", String.class, this.kdtEntryAtt, "attNumber.text");
		dataBinder.registerBinding("EntryAtt.attName", String.class, this.kdtEntryAtt, "attName.text");
		dataBinder.registerBinding("EntryAtt.attlist", String.class, this.kdtEntryAtt, "attlist.text");
		dataBinder.registerBinding("marketRemake", String.class, this.txtmarketRemake, "text");
		dataBinder.registerBinding("EntryPerson.seq", int.class, this.kdtEntryPerson, "seq.text");
		dataBinder.registerBinding("EntryPerson", com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryPersonInfo.class, this.kdtEntryPerson, "userObject");
		dataBinder.registerBinding("EntryPerson.personName", String.class, this.kdtEntryPerson, "personName.text");
		dataBinder.registerBinding("EntryPerson.position", String.class, this.kdtEntryPerson, "position.text");
		dataBinder.registerBinding("EntryPerson.phone", String.class, this.kdtEntryPerson, "phone.text");
		dataBinder.registerBinding("EntryPerson.workPhone", String.class, this.kdtEntryPerson, "workPhone.text");
		dataBinder.registerBinding("EntryPerson.personFax", String.class, this.kdtEntryPerson, "personFax.text");
		dataBinder.registerBinding("EntryPerson.isDefault", boolean.class, this.kdtEntryPerson, "isDefault.text");
		dataBinder.registerBinding("EntryPerson.email", String.class, this.kdtEntryPerson, "email.text");
		dataBinder.registerBinding("EntryPerson.contact", String.class, this.kdtEntryPerson, "contact.text");
		dataBinder.registerBinding("E4.seq", int.class, this.kdtE4, "seq.text");
		dataBinder.registerBinding("E4", com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockE4Info.class, this.kdtE4, "userObject");
		dataBinder.registerBinding("E4.EvaluationIndex", String.class, this.kdtE4, "EvaluationIndex.text");
		dataBinder.registerBinding("E4.Description", String.class, this.kdtE4, "Description.text");
		dataBinder.registerBinding("E4.isQualified", boolean.class, this.kdtE4, "isQualified.text");
		dataBinder.registerBinding("Punish", String.class, this.txtPunish, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.markesupplier.subill.app.MarketSupplierStockEditUIHandler";
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
        this.editData = (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("NONE");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplierName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PurchaseOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("InviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EnterpriseMaster", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("RegisterMoney", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BusinessNum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TaxRegisterNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BizRegisterNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("boEnterpriseKind", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Recommended", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("serverfees", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("State", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Evatemp", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sysSupplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.typeNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.typeName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.peopleSum", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.remake", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SupplierFileType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Visibility", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("QuaLevel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SupplierSplAreaEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SupplierBusinessMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ServiceType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PeopleCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Province", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("City", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LinkFax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LinkPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("WebSite", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("PostNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LinkMail", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryAtt.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryAtt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryAtt.attNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryAtt.attName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryAtt.attlist", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("marketRemake", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPerson.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPerson.personName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPerson.position", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPerson.phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPerson.workPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPerson.personFax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPerson.isDefault", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPerson.email", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryPerson.contact", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E4.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E4", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E4.EvaluationIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E4.Description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E4.isQualified", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Punish", ValidateHelper.ON_SAVE);    		
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
     * output prmtEvatemp_dataChanged method
     */
    protected void prmtEvatemp_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here1
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
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("supplierName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("PurchaseOrgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("PurchaseOrgUnit.id"));
        	sic.add(new SelectorItemInfo("PurchaseOrgUnit.number"));
        	sic.add(new SelectorItemInfo("PurchaseOrgUnit.name"));
		}
        sic.add(new SelectorItemInfo("Address"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("InviteType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("InviteType.id"));
        	sic.add(new SelectorItemInfo("InviteType.number"));
        	sic.add(new SelectorItemInfo("InviteType.name"));
		}
        sic.add(new SelectorItemInfo("EnterpriseMaster"));
        sic.add(new SelectorItemInfo("RegisterMoney"));
        sic.add(new SelectorItemInfo("BusinessNum"));
        sic.add(new SelectorItemInfo("TaxRegisterNo"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("BizRegisterNo"));
        sic.add(new SelectorItemInfo("boEnterpriseKind"));
        sic.add(new SelectorItemInfo("Recommended"));
        sic.add(new SelectorItemInfo("serverfees"));
        sic.add(new SelectorItemInfo("State"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Evatemp.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("Evatemp.id"));
        	sic.add(new SelectorItemInfo("Evatemp.number"));
        	sic.add(new SelectorItemInfo("Evatemp.name"));
        	sic.add(new SelectorItemInfo("Evatemp.templateName"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("sysSupplier.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("sysSupplier.id"));
        	sic.add(new SelectorItemInfo("sysSupplier.number"));
        	sic.add(new SelectorItemInfo("sysSupplier.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("auditDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Level.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("Level.id"));
        	sic.add(new SelectorItemInfo("Level.number"));
        	sic.add(new SelectorItemInfo("Level.name"));
		}
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entrys.typeNumber"));
    	sic.add(new SelectorItemInfo("entrys.typeName"));
    	sic.add(new SelectorItemInfo("entrys.peopleSum"));
    	sic.add(new SelectorItemInfo("entrys.remake"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("SupplierFileType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("SupplierFileType.id"));
        	sic.add(new SelectorItemInfo("SupplierFileType.number"));
        	sic.add(new SelectorItemInfo("SupplierFileType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Visibility.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("Visibility.id"));
        	sic.add(new SelectorItemInfo("Visibility.number"));
        	sic.add(new SelectorItemInfo("Visibility.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("QuaLevel.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("QuaLevel.id"));
        	sic.add(new SelectorItemInfo("QuaLevel.number"));
        	sic.add(new SelectorItemInfo("QuaLevel.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("SupplierSplAreaEntry.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("SupplierSplAreaEntry.id"));
        	sic.add(new SelectorItemInfo("SupplierSplAreaEntry.number"));
        	sic.add(new SelectorItemInfo("SupplierSplAreaEntry.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("SupplierBusinessMode.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("SupplierBusinessMode.id"));
        	sic.add(new SelectorItemInfo("SupplierBusinessMode.number"));
        	sic.add(new SelectorItemInfo("SupplierBusinessMode.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("ServiceType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("ServiceType.id"));
        	sic.add(new SelectorItemInfo("ServiceType.number"));
        	sic.add(new SelectorItemInfo("ServiceType.name"));
		}
        sic.add(new SelectorItemInfo("PeopleCount"));
        sic.add(new SelectorItemInfo("Province"));
        sic.add(new SelectorItemInfo("City"));
        sic.add(new SelectorItemInfo("LinkFax"));
        sic.add(new SelectorItemInfo("LinkPhone"));
        sic.add(new SelectorItemInfo("WebSite"));
        sic.add(new SelectorItemInfo("PostNumber"));
        sic.add(new SelectorItemInfo("LinkMail"));
    	sic.add(new SelectorItemInfo("EntryAtt.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EntryAtt.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("EntryAtt.attNumber"));
    	sic.add(new SelectorItemInfo("EntryAtt.attName"));
    	sic.add(new SelectorItemInfo("EntryAtt.attlist"));
        sic.add(new SelectorItemInfo("marketRemake"));
    	sic.add(new SelectorItemInfo("EntryPerson.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("EntryPerson.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("EntryPerson.personName"));
    	sic.add(new SelectorItemInfo("EntryPerson.position"));
    	sic.add(new SelectorItemInfo("EntryPerson.phone"));
    	sic.add(new SelectorItemInfo("EntryPerson.workPhone"));
    	sic.add(new SelectorItemInfo("EntryPerson.personFax"));
    	sic.add(new SelectorItemInfo("EntryPerson.isDefault"));
    	sic.add(new SelectorItemInfo("EntryPerson.email"));
    	sic.add(new SelectorItemInfo("EntryPerson.contact"));
    	sic.add(new SelectorItemInfo("E4.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E4.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E4.EvaluationIndex"));
    	sic.add(new SelectorItemInfo("E4.Description"));
    	sic.add(new SelectorItemInfo("E4.isQualified"));
        sic.add(new SelectorItemInfo("Punish"));
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
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
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
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.markesupplier.subill.client", "MarketSupplierStockEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.port.markesupplier.subill.client.MarketSupplierStockEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo objectValue = new com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/markesupplier/subill/MarketSupplierStock";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.markesupplier.subill.app.MarketSupplierStockQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntrys;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("State","1");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}