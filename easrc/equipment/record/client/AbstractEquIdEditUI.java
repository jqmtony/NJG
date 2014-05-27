/**
 * output package name
 */
package com.kingdee.eas.port.equipment.record.client;

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
public abstract class AbstractEquIdEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEquIdEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup2;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup3;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel6;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel7;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel8;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel10;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel11;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer consbDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conname;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conssOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conjhOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conwxOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conmodel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer consize;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conweight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conwxDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conqyDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conserialNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer consbStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conunit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkspecial;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contype;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continnerNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contnowStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzzsShortName;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisMainEqm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEqmCategory;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtparent;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtname;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtssOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtjhOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtwxOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmodel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsize;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtweight;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtwxDept;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkqyDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtserialNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combosbStatus;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtunit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmttype;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtinnerNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combonowStatus;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzzsShortName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEqmCategory;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conparent;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkdependable;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conaddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conlocation;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conusingDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conresPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtparent;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtaddress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtlocation;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtusingDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtresPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conmader;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conmadedCountry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conmadeDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer consupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conreachedDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conccNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer coninstaller;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer condebuger;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer concheckDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer condeadline;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer consourceUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmader;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtmadedCountry;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkmadeDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsupplier;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkreachedDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtccNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinstaller;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtdebuger;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkcheckDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkdeadline;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsourceUnit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkportTest;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkcityTest;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttestDay;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttzdaNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttzsbStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contspecialType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcityPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contportPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contengineNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcarNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contratedWeight;
    protected com.kingdee.bos.ctrl.swing.KDTimePicker testDay;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttzdaNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox tzsbStatus;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtspecialType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcityPeriod;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtportPeriod;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcode;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtengineNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcarNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtratedWeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contasset;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contassetStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contassetValue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continstallCost;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtasset;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtassetStatus;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtassetValue;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtinstallCost;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtTechnologyPar;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtTechnologyPar_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSpareInfo;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtSpareInfo_detailPanel = null;
    protected com.kingdee.eas.port.equipment.record.EquIdInfo editData = null;
    protected ActionInUse actionInUse = null;
    protected ActionOutUse actionOutUse = null;
    /**
     * output class constructor
     */
    public AbstractEquIdEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEquIdEditUI.class.getName());
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
        //actionUnAudit
        actionUnAudit.setEnabled(true);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInUse
        this.actionInUse = new ActionInUse(this);
        getActionManager().registerAction("actionInUse", actionInUse);
        this.actionInUse.setExtendProperty("canForewarn", "true");
        this.actionInUse.setExtendProperty("userDefined", "true");
        this.actionInUse.setExtendProperty("isObjectUpdateLock", "false");
         this.actionInUse.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionInUse.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionOutUse
        this.actionOutUse = new ActionOutUse(this);
        getActionManager().registerAction("actionOutUse", actionOutUse);
        this.actionOutUse.setExtendProperty("canForewarn", "true");
        this.actionOutUse.setExtendProperty("userDefined", "true");
        this.actionOutUse.setExtendProperty("isObjectUpdateLock", "false");
         this.actionOutUse.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionOutUse.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDButtonGroup2 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDButtonGroup3 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel6 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel7 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel8 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel10 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel11 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.consbDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conname = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conssOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conjhOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conwxOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conmodel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.consize = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conweight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conwxDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conqyDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conserialNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.consbStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conunit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkspecial = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contype = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continnerNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contnowStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzzsShortName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisMainEqm = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEqmCategory = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtparent = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtname = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtssOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtjhOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtwxOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtmodel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtsize = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtweight = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtwxDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkqyDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtserialNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.combosbStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtunit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmttype = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtinnerNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.combonowStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtzzsShortName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEqmCategory = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.conparent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkdependable = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.conaddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conlocation = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conusingDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conresPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtparent = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtaddress = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtlocation = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtusingDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtresPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.conmader = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conmadedCountry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conmadeDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.consupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conreachedDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conccNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.coninstaller = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.condebuger = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.concheckDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.condeadline = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.consourceUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtmader = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtmadedCountry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkmadeDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtsupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkreachedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtccNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtinstaller = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtdebuger = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkcheckDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkdeadline = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtsourceUnit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.chkportTest = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkcityTest = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.conttestDay = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttzdaNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttzsbStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contspecialType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcityPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contportPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contengineNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcarNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contratedWeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.testDay = new com.kingdee.bos.ctrl.swing.KDTimePicker();
        this.txttzdaNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tzsbStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtspecialType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcityPeriod = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtportPeriod = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtcode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtengineNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcarNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtratedWeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contasset = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contassetStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contassetValue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continstallCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtasset = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtassetStatus = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtassetValue = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtinstallCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdtTechnologyPar = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtSpareInfo = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contCU.setName("contCU");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contStatus.setName("contStatus");
        this.contBizStatus.setName("contBizStatus");
        this.contAuditTime.setName("contAuditTime");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.prmtCU.setName("prmtCU");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.comboStatus.setName("comboStatus");
        this.comboBizStatus.setName("comboBizStatus");
        this.pkAuditTime.setName("pkAuditTime");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel6.setName("kDPanel6");
        this.kDPanel7.setName("kDPanel7");
        this.kDPanel8.setName("kDPanel8");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel10.setName("kDPanel10");
        this.kDPanel11.setName("kDPanel11");
        this.kDPanel3.setName("kDPanel3");
        this.consbDescription.setName("consbDescription");
        this.conname.setName("conname");
        this.conssOrgUnit.setName("conssOrgUnit");
        this.conjhOrgUnit.setName("conjhOrgUnit");
        this.conwxOrgUnit.setName("conwxOrgUnit");
        this.conmodel.setName("conmodel");
        this.consize.setName("consize");
        this.conweight.setName("conweight");
        this.conwxDept.setName("conwxDept");
        this.conqyDate.setName("conqyDate");
        this.conserialNumber.setName("conserialNumber");
        this.consbStatus.setName("consbStatus");
        this.conunit.setName("conunit");
        this.chkspecial.setName("chkspecial");
        this.contype.setName("contype");
        this.continnerNumber.setName("continnerNumber");
        this.contnowStatus.setName("contnowStatus");
        this.contzzsShortName.setName("contzzsShortName");
        this.chkisMainEqm.setName("chkisMainEqm");
        this.contNumber.setName("contNumber");
        this.contEqmCategory.setName("contEqmCategory");
        this.txtparent.setName("txtparent");
        this.txtname.setName("txtname");
        this.prmtssOrgUnit.setName("prmtssOrgUnit");
        this.prmtjhOrgUnit.setName("prmtjhOrgUnit");
        this.prmtwxOrgUnit.setName("prmtwxOrgUnit");
        this.txtmodel.setName("txtmodel");
        this.txtsize.setName("txtsize");
        this.txtweight.setName("txtweight");
        this.prmtwxDept.setName("prmtwxDept");
        this.pkqyDate.setName("pkqyDate");
        this.txtserialNumber.setName("txtserialNumber");
        this.combosbStatus.setName("combosbStatus");
        this.prmtunit.setName("prmtunit");
        this.prmttype.setName("prmttype");
        this.txtinnerNumber.setName("txtinnerNumber");
        this.combonowStatus.setName("combonowStatus");
        this.txtzzsShortName.setName("txtzzsShortName");
        this.txtNumber.setName("txtNumber");
        this.txtEqmCategory.setName("txtEqmCategory");
        this.conparent.setName("conparent");
        this.chkdependable.setName("chkdependable");
        this.conaddress.setName("conaddress");
        this.conlocation.setName("conlocation");
        this.conusingDept.setName("conusingDept");
        this.conresPerson.setName("conresPerson");
        this.prmtparent.setName("prmtparent");
        this.prmtaddress.setName("prmtaddress");
        this.txtlocation.setName("txtlocation");
        this.prmtusingDept.setName("prmtusingDept");
        this.prmtresPerson.setName("prmtresPerson");
        this.conmader.setName("conmader");
        this.conmadedCountry.setName("conmadedCountry");
        this.conmadeDate.setName("conmadeDate");
        this.consupplier.setName("consupplier");
        this.conreachedDate.setName("conreachedDate");
        this.conccNumber.setName("conccNumber");
        this.coninstaller.setName("coninstaller");
        this.condebuger.setName("condebuger");
        this.concheckDate.setName("concheckDate");
        this.condeadline.setName("condeadline");
        this.consourceUnit.setName("consourceUnit");
        this.txtmader.setName("txtmader");
        this.prmtmadedCountry.setName("prmtmadedCountry");
        this.pkmadeDate.setName("pkmadeDate");
        this.prmtsupplier.setName("prmtsupplier");
        this.pkreachedDate.setName("pkreachedDate");
        this.txtccNumber.setName("txtccNumber");
        this.prmtinstaller.setName("prmtinstaller");
        this.prmtdebuger.setName("prmtdebuger");
        this.pkcheckDate.setName("pkcheckDate");
        this.pkdeadline.setName("pkdeadline");
        this.txtsourceUnit.setName("txtsourceUnit");
        this.chkportTest.setName("chkportTest");
        this.chkcityTest.setName("chkcityTest");
        this.conttestDay.setName("conttestDay");
        this.conttzdaNumber.setName("conttzdaNumber");
        this.conttzsbStatus.setName("conttzsbStatus");
        this.contspecialType.setName("contspecialType");
        this.contcityPeriod.setName("contcityPeriod");
        this.contportPeriod.setName("contportPeriod");
        this.contcode.setName("contcode");
        this.contengineNumber.setName("contengineNumber");
        this.contcarNumber.setName("contcarNumber");
        this.contratedWeight.setName("contratedWeight");
        this.testDay.setName("testDay");
        this.txttzdaNumber.setName("txttzdaNumber");
        this.tzsbStatus.setName("tzsbStatus");
        this.prmtspecialType.setName("prmtspecialType");
        this.txtcityPeriod.setName("txtcityPeriod");
        this.txtportPeriod.setName("txtportPeriod");
        this.txtcode.setName("txtcode");
        this.txtengineNumber.setName("txtengineNumber");
        this.txtcarNumber.setName("txtcarNumber");
        this.txtratedWeight.setName("txtratedWeight");
        this.contasset.setName("contasset");
        this.contassetStatus.setName("contassetStatus");
        this.contassetValue.setName("contassetValue");
        this.continstallCost.setName("continstallCost");
        this.prmtasset.setName("prmtasset");
        this.prmtassetStatus.setName("prmtassetStatus");
        this.txtassetValue.setName("txtassetValue");
        this.txtinstallCost.setName("txtinstallCost");
        this.kdtTechnologyPar.setName("kdtTechnologyPar");
        this.kdtSpareInfo.setName("kdtSpareInfo");
        // CoreUI
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);
        // contCU		
        this.contCU.setBoundLabelText(resHelper.getString("contCU.boundLabelText"));		
        this.contCU.setBoundLabelLength(100);		
        this.contCU.setBoundLabelUnderline(true);		
        this.contCU.setEnabled(false);		
        this.contCU.setVisible(false);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setVisible(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setVisible(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);		
        this.contStatus.setVisible(false);
        // contBizStatus		
        this.contBizStatus.setBoundLabelText(resHelper.getString("contBizStatus.boundLabelText"));		
        this.contBizStatus.setBoundLabelLength(100);		
        this.contBizStatus.setBoundLabelUnderline(true);		
        this.contBizStatus.setEnabled(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // kDButtonGroup1
        // kDButtonGroup2
        // kDButtonGroup3
        // kDTabbedPane1
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setCommitFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");		
        this.prmtCreator.setDisplayFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setTimeEnabled(true);		
        this.pkCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);		
        this.prmtLastUpdateUser.setDisplayFormat("$name$");		
        this.prmtLastUpdateUser.setEditFormat("$name$");		
        this.prmtLastUpdateUser.setCommitFormat("$name$");
        // pkLastUpdateTime		
        this.pkLastUpdateTime.setTimeEnabled(true);		
        this.pkLastUpdateTime.setEnabled(false);
        // prmtCU		
        this.prmtCU.setEnabled(false);		
        this.prmtCU.setVisible(false);
        // pkBizDate
        // txtDescription		
        this.txtDescription.setEnabled(false);		
        this.txtDescription.setVisible(false);		
        this.txtDescription.setOpaque(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);
        // comboBizStatus		
        this.comboBizStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBizActionEnum").toArray());		
        this.comboBizStatus.setEnabled(false);		
        this.comboBizStatus.setVisible(false);
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // kDPanel1
        // kDPanel6
        // kDPanel7
        // kDPanel8		
        this.kDPanel8.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel8.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // kDPanel10		
        this.kDPanel10.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel10.border.title")));
        // kDPanel11		
        this.kDPanel11.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel11.border.title")));
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // consbDescription		
        this.consbDescription.setBoundLabelText(resHelper.getString("consbDescription.boundLabelText"));		
        this.consbDescription.setBoundLabelLength(100);		
        this.consbDescription.setBoundLabelUnderline(true);		
        this.consbDescription.setVisible(true);
        // conname		
        this.conname.setBoundLabelText(resHelper.getString("conname.boundLabelText"));		
        this.conname.setBoundLabelLength(100);		
        this.conname.setBoundLabelUnderline(true);		
        this.conname.setVisible(true);
        // conssOrgUnit		
        this.conssOrgUnit.setBoundLabelText(resHelper.getString("conssOrgUnit.boundLabelText"));		
        this.conssOrgUnit.setBoundLabelLength(100);		
        this.conssOrgUnit.setBoundLabelUnderline(true);		
        this.conssOrgUnit.setVisible(true);
        // conjhOrgUnit		
        this.conjhOrgUnit.setBoundLabelText(resHelper.getString("conjhOrgUnit.boundLabelText"));		
        this.conjhOrgUnit.setBoundLabelLength(100);		
        this.conjhOrgUnit.setBoundLabelUnderline(true);		
        this.conjhOrgUnit.setVisible(true);
        // conwxOrgUnit		
        this.conwxOrgUnit.setBoundLabelText(resHelper.getString("conwxOrgUnit.boundLabelText"));		
        this.conwxOrgUnit.setBoundLabelLength(100);		
        this.conwxOrgUnit.setBoundLabelUnderline(true);		
        this.conwxOrgUnit.setVisible(true);
        // conmodel		
        this.conmodel.setBoundLabelText(resHelper.getString("conmodel.boundLabelText"));		
        this.conmodel.setBoundLabelLength(100);		
        this.conmodel.setBoundLabelUnderline(true);		
        this.conmodel.setVisible(true);
        // consize		
        this.consize.setBoundLabelText(resHelper.getString("consize.boundLabelText"));		
        this.consize.setBoundLabelLength(100);		
        this.consize.setBoundLabelUnderline(true);		
        this.consize.setVisible(true);
        // conweight		
        this.conweight.setBoundLabelText(resHelper.getString("conweight.boundLabelText"));		
        this.conweight.setBoundLabelLength(100);		
        this.conweight.setBoundLabelUnderline(true);		
        this.conweight.setVisible(true);
        // conwxDept		
        this.conwxDept.setBoundLabelText(resHelper.getString("conwxDept.boundLabelText"));		
        this.conwxDept.setBoundLabelLength(100);		
        this.conwxDept.setBoundLabelUnderline(true);		
        this.conwxDept.setVisible(true);
        // conqyDate		
        this.conqyDate.setBoundLabelText(resHelper.getString("conqyDate.boundLabelText"));		
        this.conqyDate.setBoundLabelLength(100);		
        this.conqyDate.setBoundLabelUnderline(true);		
        this.conqyDate.setVisible(true);
        // conserialNumber		
        this.conserialNumber.setBoundLabelText(resHelper.getString("conserialNumber.boundLabelText"));		
        this.conserialNumber.setBoundLabelLength(100);		
        this.conserialNumber.setBoundLabelUnderline(true);		
        this.conserialNumber.setVisible(true);
        // consbStatus		
        this.consbStatus.setBoundLabelText(resHelper.getString("consbStatus.boundLabelText"));		
        this.consbStatus.setBoundLabelLength(100);		
        this.consbStatus.setBoundLabelUnderline(true);		
        this.consbStatus.setVisible(true);
        // conunit		
        this.conunit.setBoundLabelText(resHelper.getString("conunit.boundLabelText"));		
        this.conunit.setBoundLabelLength(100);		
        this.conunit.setBoundLabelUnderline(true);		
        this.conunit.setVisible(true);
        // chkspecial		
        this.chkspecial.setText(resHelper.getString("chkspecial.text"));		
        this.chkspecial.setVisible(true);		
        this.chkspecial.setHorizontalAlignment(2);
        this.chkspecial.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkspecial_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contype		
        this.contype.setBoundLabelText(resHelper.getString("contype.boundLabelText"));		
        this.contype.setBoundLabelLength(100);		
        this.contype.setBoundLabelUnderline(true);		
        this.contype.setVisible(true);
        // continnerNumber		
        this.continnerNumber.setBoundLabelText(resHelper.getString("continnerNumber.boundLabelText"));		
        this.continnerNumber.setBoundLabelLength(100);		
        this.continnerNumber.setBoundLabelUnderline(true);		
        this.continnerNumber.setVisible(true);
        // contnowStatus		
        this.contnowStatus.setBoundLabelText(resHelper.getString("contnowStatus.boundLabelText"));		
        this.contnowStatus.setBoundLabelLength(100);		
        this.contnowStatus.setBoundLabelUnderline(true);		
        this.contnowStatus.setVisible(true);
        // contzzsShortName		
        this.contzzsShortName.setBoundLabelText(resHelper.getString("contzzsShortName.boundLabelText"));		
        this.contzzsShortName.setBoundLabelLength(100);		
        this.contzzsShortName.setBoundLabelUnderline(true);		
        this.contzzsShortName.setVisible(true);
        // chkisMainEqm		
        this.chkisMainEqm.setText(resHelper.getString("chkisMainEqm.text"));		
        this.chkisMainEqm.setVisible(true);		
        this.chkisMainEqm.setHorizontalAlignment(2);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contEqmCategory		
        this.contEqmCategory.setBoundLabelText(resHelper.getString("contEqmCategory.boundLabelText"));		
        this.contEqmCategory.setBoundLabelLength(100);		
        this.contEqmCategory.setBoundLabelUnderline(true);		
        this.contEqmCategory.setVisible(true);
        // txtparent		
        this.txtparent.setVisible(true);		
        this.txtparent.setRequired(false);		
        this.txtparent.setMaxLength(255);
        // txtname		
        this.txtname.setVisible(true);		
        this.txtname.setHorizontalAlignment(2);		
        this.txtname.setMaxLength(100);		
        this.txtname.setRequired(true);
        // prmtssOrgUnit		
        this.prmtssOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtssOrgUnit.setVisible(true);		
        this.prmtssOrgUnit.setEditable(true);		
        this.prmtssOrgUnit.setDisplayFormat("$name$");		
        this.prmtssOrgUnit.setEditFormat("$number$");		
        this.prmtssOrgUnit.setCommitFormat("$number$");		
        this.prmtssOrgUnit.setRequired(true);
        // prmtjhOrgUnit		
        this.prmtjhOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtjhOrgUnit.setVisible(true);		
        this.prmtjhOrgUnit.setEditable(true);		
        this.prmtjhOrgUnit.setDisplayFormat("$name$");		
        this.prmtjhOrgUnit.setEditFormat("$number$");		
        this.prmtjhOrgUnit.setCommitFormat("$number$");		
        this.prmtjhOrgUnit.setRequired(true);
        // prmtwxOrgUnit		
        this.prmtwxOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtwxOrgUnit.setVisible(true);		
        this.prmtwxOrgUnit.setEditable(true);		
        this.prmtwxOrgUnit.setDisplayFormat("$name$");		
        this.prmtwxOrgUnit.setEditFormat("$number$");		
        this.prmtwxOrgUnit.setCommitFormat("$number$");		
        this.prmtwxOrgUnit.setRequired(true);
        // txtmodel		
        this.txtmodel.setVisible(true);		
        this.txtmodel.setHorizontalAlignment(2);		
        this.txtmodel.setMaxLength(100);		
        this.txtmodel.setRequired(false);
        // txtsize		
        this.txtsize.setVisible(true);		
        this.txtsize.setHorizontalAlignment(2);		
        this.txtsize.setMaxLength(100);		
        this.txtsize.setRequired(false);
        // txtweight		
        this.txtweight.setVisible(true);		
        this.txtweight.setHorizontalAlignment(2);		
        this.txtweight.setMaxLength(100);		
        this.txtweight.setRequired(false);
        // prmtwxDept		
        this.prmtwxDept.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtwxDept.setVisible(true);		
        this.prmtwxDept.setEditable(true);		
        this.prmtwxDept.setDisplayFormat("$name$");		
        this.prmtwxDept.setEditFormat("$number$");		
        this.prmtwxDept.setCommitFormat("$number$");		
        this.prmtwxDept.setRequired(false);
        // pkqyDate		
        this.pkqyDate.setVisible(true);		
        this.pkqyDate.setRequired(true);
        // txtserialNumber		
        this.txtserialNumber.setVisible(true);		
        this.txtserialNumber.setHorizontalAlignment(2);		
        this.txtserialNumber.setMaxLength(100);		
        this.txtserialNumber.setRequired(false);
        // combosbStatus		
        this.combosbStatus.setVisible(true);		
        this.combosbStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.equipment.base.enumbase.sbStatusType").toArray());		
        this.combosbStatus.setRequired(true);
        // prmtunit		
        this.prmtunit.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");		
        this.prmtunit.setVisible(true);		
        this.prmtunit.setEditable(true);		
        this.prmtunit.setDisplayFormat("$name$");		
        this.prmtunit.setEditFormat("$number$");		
        this.prmtunit.setCommitFormat("$number$");		
        this.prmtunit.setRequired(false);
        // prmttype		
        this.prmttype.setQueryInfo("com.kingdee.eas.fi.fa.basedata.FaCatQuery");		
        this.prmttype.setVisible(true);		
        this.prmttype.setEditable(true);		
        this.prmttype.setDisplayFormat("$name$");		
        this.prmttype.setEditFormat("$number$");		
        this.prmttype.setCommitFormat("$number$");		
        this.prmttype.setRequired(true);
        prmttype.addDataChangeListener(new DataChangeListener() {
		public void dataChanged(DataChangeEvent e) {
			try {
				prmttype_Changed();
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        this.prmttype.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmttype_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtinnerNumber		
        this.txtinnerNumber.setVisible(true);		
        this.txtinnerNumber.setHorizontalAlignment(2);		
        this.txtinnerNumber.setMaxLength(100);		
        this.txtinnerNumber.setRequired(false);
        // combonowStatus		
        this.combonowStatus.setVisible(true);		
        this.combonowStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.equipment.base.enumbase.nowStatusType").toArray());		
        this.combonowStatus.setRequired(false);
        // txtzzsShortName		
        this.txtzzsShortName.setVisible(true);		
        this.txtzzsShortName.setHorizontalAlignment(2);		
        this.txtzzsShortName.setMaxLength(100);		
        this.txtzzsShortName.setRequired(false);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // txtEqmCategory		
        this.txtEqmCategory.setVisible(true);		
        this.txtEqmCategory.setHorizontalAlignment(2);		
        this.txtEqmCategory.setMaxLength(80);		
        this.txtEqmCategory.setRequired(false);		
        this.txtEqmCategory.setEnabled(false);
        // conparent		
        this.conparent.setBoundLabelText(resHelper.getString("conparent.boundLabelText"));		
        this.conparent.setBoundLabelLength(100);		
        this.conparent.setBoundLabelUnderline(true);		
        this.conparent.setVisible(true);
        // chkdependable		
        this.chkdependable.setText(resHelper.getString("chkdependable.text"));		
        this.chkdependable.setVisible(true);		
        this.chkdependable.setHorizontalAlignment(2);
        // conaddress		
        this.conaddress.setBoundLabelText(resHelper.getString("conaddress.boundLabelText"));		
        this.conaddress.setBoundLabelLength(100);		
        this.conaddress.setBoundLabelUnderline(true);		
        this.conaddress.setVisible(true);
        // conlocation		
        this.conlocation.setBoundLabelText(resHelper.getString("conlocation.boundLabelText"));		
        this.conlocation.setBoundLabelLength(100);		
        this.conlocation.setBoundLabelUnderline(true);		
        this.conlocation.setVisible(true);
        // conusingDept		
        this.conusingDept.setBoundLabelText(resHelper.getString("conusingDept.boundLabelText"));		
        this.conusingDept.setBoundLabelLength(100);		
        this.conusingDept.setBoundLabelUnderline(true);		
        this.conusingDept.setVisible(true);
        // conresPerson		
        this.conresPerson.setBoundLabelText(resHelper.getString("conresPerson.boundLabelText"));		
        this.conresPerson.setBoundLabelLength(100);		
        this.conresPerson.setBoundLabelUnderline(true);		
        this.conresPerson.setVisible(true);
        // prmtparent		
        this.prmtparent.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");		
        this.prmtparent.setVisible(true);		
        this.prmtparent.setEditable(true);		
        this.prmtparent.setDisplayFormat("$name$");		
        this.prmtparent.setEditFormat("$number$");		
        this.prmtparent.setCommitFormat("$number$");		
        this.prmtparent.setRequired(false);
        // prmtaddress		
        this.prmtaddress.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7AddressQuery");		
        this.prmtaddress.setVisible(true);		
        this.prmtaddress.setEditable(true);		
        this.prmtaddress.setDisplayFormat("$detailAddress$");		
        this.prmtaddress.setEditFormat("$number$");		
        this.prmtaddress.setCommitFormat("$number$");		
        this.prmtaddress.setRequired(false);
        // txtlocation		
        this.txtlocation.setVisible(true);		
        this.txtlocation.setHorizontalAlignment(2);		
        this.txtlocation.setMaxLength(100);		
        this.txtlocation.setRequired(false);
        // prmtusingDept		
        this.prmtusingDept.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtusingDept.setVisible(true);		
        this.prmtusingDept.setEditable(true);		
        this.prmtusingDept.setDisplayFormat("$name$");		
        this.prmtusingDept.setEditFormat("$number$");		
        this.prmtusingDept.setCommitFormat("$number$");		
        this.prmtusingDept.setRequired(false);
        // prmtresPerson		
        this.prmtresPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtresPerson.setVisible(true);		
        this.prmtresPerson.setEditable(true);		
        this.prmtresPerson.setDisplayFormat("$name$");		
        this.prmtresPerson.setEditFormat("$number$");		
        this.prmtresPerson.setCommitFormat("$number$");		
        this.prmtresPerson.setRequired(false);
        // conmader		
        this.conmader.setBoundLabelText(resHelper.getString("conmader.boundLabelText"));		
        this.conmader.setBoundLabelLength(100);		
        this.conmader.setBoundLabelUnderline(true);		
        this.conmader.setVisible(true);
        // conmadedCountry		
        this.conmadedCountry.setBoundLabelText(resHelper.getString("conmadedCountry.boundLabelText"));		
        this.conmadedCountry.setBoundLabelLength(100);		
        this.conmadedCountry.setBoundLabelUnderline(true);		
        this.conmadedCountry.setVisible(true);
        // conmadeDate		
        this.conmadeDate.setBoundLabelText(resHelper.getString("conmadeDate.boundLabelText"));		
        this.conmadeDate.setBoundLabelLength(100);		
        this.conmadeDate.setBoundLabelUnderline(true);		
        this.conmadeDate.setVisible(true);
        // consupplier		
        this.consupplier.setBoundLabelText(resHelper.getString("consupplier.boundLabelText"));		
        this.consupplier.setBoundLabelLength(100);		
        this.consupplier.setBoundLabelUnderline(true);		
        this.consupplier.setVisible(true);
        // conreachedDate		
        this.conreachedDate.setBoundLabelText(resHelper.getString("conreachedDate.boundLabelText"));		
        this.conreachedDate.setBoundLabelLength(100);		
        this.conreachedDate.setBoundLabelUnderline(true);		
        this.conreachedDate.setVisible(true);
        // conccNumber		
        this.conccNumber.setBoundLabelText(resHelper.getString("conccNumber.boundLabelText"));		
        this.conccNumber.setBoundLabelLength(100);		
        this.conccNumber.setBoundLabelUnderline(true);		
        this.conccNumber.setVisible(true);
        // coninstaller		
        this.coninstaller.setBoundLabelText(resHelper.getString("coninstaller.boundLabelText"));		
        this.coninstaller.setBoundLabelLength(100);		
        this.coninstaller.setBoundLabelUnderline(true);		
        this.coninstaller.setVisible(true);
        // condebuger		
        this.condebuger.setBoundLabelText(resHelper.getString("condebuger.boundLabelText"));		
        this.condebuger.setBoundLabelLength(100);		
        this.condebuger.setBoundLabelUnderline(true);		
        this.condebuger.setVisible(true);
        // concheckDate		
        this.concheckDate.setBoundLabelText(resHelper.getString("concheckDate.boundLabelText"));		
        this.concheckDate.setBoundLabelLength(100);		
        this.concheckDate.setBoundLabelUnderline(true);		
        this.concheckDate.setVisible(true);
        // condeadline		
        this.condeadline.setBoundLabelText(resHelper.getString("condeadline.boundLabelText"));		
        this.condeadline.setBoundLabelLength(100);		
        this.condeadline.setBoundLabelUnderline(true);		
        this.condeadline.setVisible(true);
        // consourceUnit		
        this.consourceUnit.setBoundLabelText(resHelper.getString("consourceUnit.boundLabelText"));		
        this.consourceUnit.setBoundLabelLength(100);		
        this.consourceUnit.setBoundLabelUnderline(true);		
        this.consourceUnit.setVisible(true);
        // txtmader		
        this.txtmader.setVisible(true);		
        this.txtmader.setHorizontalAlignment(2);		
        this.txtmader.setMaxLength(100);		
        this.txtmader.setRequired(false);
        // prmtmadedCountry		
        this.prmtmadedCountry.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CountryQuery");		
        this.prmtmadedCountry.setVisible(true);		
        this.prmtmadedCountry.setEditable(true);		
        this.prmtmadedCountry.setDisplayFormat("$name$");		
        this.prmtmadedCountry.setEditFormat("$number$");		
        this.prmtmadedCountry.setCommitFormat("$number$");		
        this.prmtmadedCountry.setRequired(false);
        // pkmadeDate		
        this.pkmadeDate.setVisible(true);		
        this.pkmadeDate.setRequired(false);
        // prmtsupplier		
        this.prmtsupplier.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.PSupplierQuery");		
        this.prmtsupplier.setVisible(true);		
        this.prmtsupplier.setEditable(true);		
        this.prmtsupplier.setDisplayFormat("$name$");		
        this.prmtsupplier.setEditFormat("$number$");		
        this.prmtsupplier.setCommitFormat("$number$");		
        this.prmtsupplier.setRequired(false);
        // pkreachedDate		
        this.pkreachedDate.setVisible(true);		
        this.pkreachedDate.setRequired(false);
        // txtccNumber		
        this.txtccNumber.setVisible(true);		
        this.txtccNumber.setHorizontalAlignment(2);		
        this.txtccNumber.setMaxLength(100);		
        this.txtccNumber.setRequired(false);
        // prmtinstaller		
        this.prmtinstaller.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.PSupplierQuery");		
        this.prmtinstaller.setVisible(true);		
        this.prmtinstaller.setEditable(true);		
        this.prmtinstaller.setDisplayFormat("$name$");		
        this.prmtinstaller.setEditFormat("$number$");		
        this.prmtinstaller.setCommitFormat("$number$");		
        this.prmtinstaller.setRequired(false);
        // prmtdebuger		
        this.prmtdebuger.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.PSupplierQuery");		
        this.prmtdebuger.setVisible(true);		
        this.prmtdebuger.setEditable(true);		
        this.prmtdebuger.setDisplayFormat("$name$");		
        this.prmtdebuger.setEditFormat("$number$");		
        this.prmtdebuger.setCommitFormat("$number$");		
        this.prmtdebuger.setRequired(false);
        // pkcheckDate		
        this.pkcheckDate.setVisible(true);		
        this.pkcheckDate.setRequired(false);
        // pkdeadline		
        this.pkdeadline.setVisible(true);		
        this.pkdeadline.setRequired(false);
        // txtsourceUnit		
        this.txtsourceUnit.setVisible(true);		
        this.txtsourceUnit.setHorizontalAlignment(2);		
        this.txtsourceUnit.setMaxLength(100);		
        this.txtsourceUnit.setRequired(false);
        // chkportTest		
        this.chkportTest.setText(resHelper.getString("chkportTest.text"));		
        this.chkportTest.setVisible(true);		
        this.chkportTest.setHorizontalAlignment(2);
        // chkcityTest		
        this.chkcityTest.setText(resHelper.getString("chkcityTest.text"));		
        this.chkcityTest.setVisible(true);		
        this.chkcityTest.setHorizontalAlignment(2);
        // conttestDay		
        this.conttestDay.setBoundLabelText(resHelper.getString("conttestDay.boundLabelText"));		
        this.conttestDay.setBoundLabelLength(100);		
        this.conttestDay.setBoundLabelUnderline(true);		
        this.conttestDay.setVisible(true);
        // conttzdaNumber		
        this.conttzdaNumber.setBoundLabelText(resHelper.getString("conttzdaNumber.boundLabelText"));		
        this.conttzdaNumber.setBoundLabelLength(100);		
        this.conttzdaNumber.setBoundLabelUnderline(true);		
        this.conttzdaNumber.setVisible(true);
        // conttzsbStatus		
        this.conttzsbStatus.setBoundLabelText(resHelper.getString("conttzsbStatus.boundLabelText"));		
        this.conttzsbStatus.setBoundLabelLength(100);		
        this.conttzsbStatus.setBoundLabelUnderline(true);		
        this.conttzsbStatus.setVisible(true);
        // contspecialType		
        this.contspecialType.setBoundLabelText(resHelper.getString("contspecialType.boundLabelText"));		
        this.contspecialType.setBoundLabelLength(100);		
        this.contspecialType.setBoundLabelUnderline(true);		
        this.contspecialType.setVisible(true);
        // contcityPeriod		
        this.contcityPeriod.setBoundLabelText(resHelper.getString("contcityPeriod.boundLabelText"));		
        this.contcityPeriod.setBoundLabelLength(60);		
        this.contcityPeriod.setBoundLabelUnderline(true);		
        this.contcityPeriod.setVisible(true);
        // contportPeriod		
        this.contportPeriod.setBoundLabelText(resHelper.getString("contportPeriod.boundLabelText"));		
        this.contportPeriod.setBoundLabelLength(60);		
        this.contportPeriod.setBoundLabelUnderline(true);		
        this.contportPeriod.setVisible(true);
        // contcode		
        this.contcode.setBoundLabelText(resHelper.getString("contcode.boundLabelText"));		
        this.contcode.setBoundLabelLength(100);		
        this.contcode.setBoundLabelUnderline(true);		
        this.contcode.setVisible(true);
        // contengineNumber		
        this.contengineNumber.setBoundLabelText(resHelper.getString("contengineNumber.boundLabelText"));		
        this.contengineNumber.setBoundLabelLength(100);		
        this.contengineNumber.setBoundLabelUnderline(true);		
        this.contengineNumber.setVisible(true);
        // contcarNumber		
        this.contcarNumber.setBoundLabelText(resHelper.getString("contcarNumber.boundLabelText"));		
        this.contcarNumber.setBoundLabelLength(100);		
        this.contcarNumber.setBoundLabelUnderline(true);		
        this.contcarNumber.setVisible(true);
        // contratedWeight		
        this.contratedWeight.setBoundLabelText(resHelper.getString("contratedWeight.boundLabelText"));		
        this.contratedWeight.setBoundLabelLength(100);		
        this.contratedWeight.setBoundLabelUnderline(true);		
        this.contratedWeight.setVisible(true);
        // testDay		
        this.testDay.setVisible(true);
        // txttzdaNumber		
        this.txttzdaNumber.setVisible(true);		
        this.txttzdaNumber.setHorizontalAlignment(2);		
        this.txttzdaNumber.setMaxLength(100);		
        this.txttzdaNumber.setRequired(false);
        // tzsbStatus		
        this.tzsbStatus.setVisible(true);		
        this.tzsbStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.equipment.base.enumbase.sbStatusType").toArray());		
        this.tzsbStatus.setRequired(false);
        // prmtspecialType		
        this.prmtspecialType.setQueryInfo("com.kingdee.eas.port.equipment.base.app.SpecialTypeQuery");		
        this.prmtspecialType.setVisible(true);		
        this.prmtspecialType.setEditable(true);		
        this.prmtspecialType.setDisplayFormat("$name$");		
        this.prmtspecialType.setEditFormat("$number$");		
        this.prmtspecialType.setCommitFormat("$number$");		
        this.prmtspecialType.setRequired(false);
        // txtcityPeriod		
        this.txtcityPeriod.setVisible(true);		
        this.txtcityPeriod.setHorizontalAlignment(2);		
        this.txtcityPeriod.setDataType(1);		
        this.txtcityPeriod.setSupportedEmpty(true);		
        this.txtcityPeriod.setMinimumValue( new java.math.BigDecimal("-1.0E27"));		
        this.txtcityPeriod.setMaximumValue( new java.math.BigDecimal("1.0E27"));		
        this.txtcityPeriod.setPrecision(1);		
        this.txtcityPeriod.setRequired(false);
        // txtportPeriod		
        this.txtportPeriod.setVisible(true);		
        this.txtportPeriod.setHorizontalAlignment(2);		
        this.txtportPeriod.setDataType(1);		
        this.txtportPeriod.setSupportedEmpty(true);		
        this.txtportPeriod.setMinimumValue( new java.math.BigDecimal("-1.0E27"));		
        this.txtportPeriod.setMaximumValue( new java.math.BigDecimal("1.0E27"));		
        this.txtportPeriod.setPrecision(1);		
        this.txtportPeriod.setRequired(false);
        // txtcode		
        this.txtcode.setVisible(true);		
        this.txtcode.setHorizontalAlignment(2);		
        this.txtcode.setMaxLength(100);		
        this.txtcode.setRequired(false);
        // txtengineNumber		
        this.txtengineNumber.setVisible(true);		
        this.txtengineNumber.setHorizontalAlignment(2);		
        this.txtengineNumber.setMaxLength(100);		
        this.txtengineNumber.setRequired(false);
        // txtcarNumber		
        this.txtcarNumber.setVisible(true);		
        this.txtcarNumber.setHorizontalAlignment(2);		
        this.txtcarNumber.setMaxLength(100);		
        this.txtcarNumber.setRequired(false);
        // txtratedWeight		
        this.txtratedWeight.setVisible(true);		
        this.txtratedWeight.setHorizontalAlignment(2);		
        this.txtratedWeight.setDataType(1);		
        this.txtratedWeight.setSupportedEmpty(true);		
        this.txtratedWeight.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtratedWeight.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtratedWeight.setPrecision(2);		
        this.txtratedWeight.setRequired(false);
        // contasset		
        this.contasset.setBoundLabelText(resHelper.getString("contasset.boundLabelText"));		
        this.contasset.setBoundLabelLength(100);		
        this.contasset.setBoundLabelUnderline(true);		
        this.contasset.setVisible(true);
        // contassetStatus		
        this.contassetStatus.setBoundLabelText(resHelper.getString("contassetStatus.boundLabelText"));		
        this.contassetStatus.setBoundLabelLength(100);		
        this.contassetStatus.setBoundLabelUnderline(true);		
        this.contassetStatus.setVisible(true);
        // contassetValue		
        this.contassetValue.setBoundLabelText(resHelper.getString("contassetValue.boundLabelText"));		
        this.contassetValue.setBoundLabelLength(100);		
        this.contassetValue.setBoundLabelUnderline(true);		
        this.contassetValue.setVisible(true);
        // continstallCost		
        this.continstallCost.setBoundLabelText(resHelper.getString("continstallCost.boundLabelText"));		
        this.continstallCost.setBoundLabelLength(100);		
        this.continstallCost.setBoundLabelUnderline(true);		
        this.continstallCost.setVisible(false);
        // prmtasset		
        this.prmtasset.setQueryInfo("com.kingdee.eas.fi.fa.manage.FaCurCardQuery");		
        this.prmtasset.setVisible(true);		
        this.prmtasset.setEditable(true);		
        this.prmtasset.setDisplayFormat("$assetName$");		
        this.prmtasset.setEditFormat("$number$");		
        this.prmtasset.setCommitFormat("$number$");		
        this.prmtasset.setRequired(false);
        this.prmtasset.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtasset_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtassetStatus		
        this.prmtassetStatus.setQueryInfo("com.kingdee.eas.fi.fa.basedata.app.FaLeaseTypeQuery");		
        this.prmtassetStatus.setVisible(true);		
        this.prmtassetStatus.setEditable(true);		
        this.prmtassetStatus.setDisplayFormat("$name$");		
        this.prmtassetStatus.setEditFormat("$number$");		
        this.prmtassetStatus.setCommitFormat("$number$");		
        this.prmtassetStatus.setRequired(false);		
        this.prmtassetStatus.setEnabled(false);
        // txtassetValue		
        this.txtassetValue.setVisible(true);		
        this.txtassetValue.setHorizontalAlignment(2);		
        this.txtassetValue.setDataType(1);		
        this.txtassetValue.setSupportedEmpty(true);		
        this.txtassetValue.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtassetValue.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtassetValue.setPrecision(2);		
        this.txtassetValue.setRequired(false);		
        this.txtassetValue.setEnabled(false);
        // txtinstallCost		
        this.txtinstallCost.setVisible(true);		
        this.txtinstallCost.setHorizontalAlignment(2);		
        this.txtinstallCost.setDataType(1);		
        this.txtinstallCost.setSupportedEmpty(true);		
        this.txtinstallCost.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtinstallCost.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtinstallCost.setPrecision(2);		
        this.txtinstallCost.setRequired(false);
        // kdtTechnologyPar
		String kdtTechnologyParStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"parName\" t:width=\"222\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"parValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"parInfo\" t:width=\"333\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{parName}</t:Cell><t:Cell>$Resource{parValue}</t:Cell><t:Cell>$Resource{parInfo}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtTechnologyPar.setFormatXml(resHelper.translateString("kdtTechnologyPar",kdtTechnologyParStrXML));

                this.kdtTechnologyPar.putBindContents("editData",new String[] {"seq","parName","parValue","parInfo"});


        this.kdtTechnologyPar.checkParsed();
        KDTextField kdtTechnologyPar_parName_TextField = new KDTextField();
        kdtTechnologyPar_parName_TextField.setName("kdtTechnologyPar_parName_TextField");
        kdtTechnologyPar_parName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtTechnologyPar_parName_CellEditor = new KDTDefaultCellEditor(kdtTechnologyPar_parName_TextField);
        this.kdtTechnologyPar.getColumn("parName").setEditor(kdtTechnologyPar_parName_CellEditor);
        KDTextField kdtTechnologyPar_parValue_TextField = new KDTextField();
        kdtTechnologyPar_parValue_TextField.setName("kdtTechnologyPar_parValue_TextField");
        kdtTechnologyPar_parValue_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtTechnologyPar_parValue_CellEditor = new KDTDefaultCellEditor(kdtTechnologyPar_parValue_TextField);
        this.kdtTechnologyPar.getColumn("parValue").setEditor(kdtTechnologyPar_parValue_CellEditor);
        KDTextField kdtTechnologyPar_parInfo_TextField = new KDTextField();
        kdtTechnologyPar_parInfo_TextField.setName("kdtTechnologyPar_parInfo_TextField");
        kdtTechnologyPar_parInfo_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtTechnologyPar_parInfo_CellEditor = new KDTDefaultCellEditor(kdtTechnologyPar_parInfo_TextField);
        this.kdtTechnologyPar.getColumn("parInfo").setEditor(kdtTechnologyPar_parInfo_CellEditor);
        // kdtSpareInfo
		String kdtSpareInfoStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"materialName\" t:width=\"333\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"speModel\" t:width=\"222\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{materialName}</t:Cell><t:Cell>$Resource{speModel}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtSpareInfo.setFormatXml(resHelper.translateString("kdtSpareInfo",kdtSpareInfoStrXML));

                this.kdtSpareInfo.putBindContents("editData",new String[] {"seq","materialName","speModel"});


        this.kdtSpareInfo.checkParsed();
        KDTextField kdtSpareInfo_materialName_TextField = new KDTextField();
        kdtSpareInfo_materialName_TextField.setName("kdtSpareInfo_materialName_TextField");
        kdtSpareInfo_materialName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtSpareInfo_materialName_CellEditor = new KDTDefaultCellEditor(kdtSpareInfo_materialName_TextField);
        this.kdtSpareInfo.getColumn("materialName").setEditor(kdtSpareInfo_materialName_CellEditor);
        KDTextField kdtSpareInfo_speModel_TextField = new KDTextField();
        kdtSpareInfo_speModel_TextField.setName("kdtSpareInfo_speModel_TextField");
        kdtSpareInfo_speModel_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtSpareInfo_speModel_CellEditor = new KDTDefaultCellEditor(kdtSpareInfo_speModel_TextField);
        this.kdtSpareInfo.getColumn("speModel").setEditor(kdtSpareInfo_speModel_CellEditor);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtCU,pkLastUpdateTime,prmtLastUpdateUser,pkCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,pkAuditTime,comboBizStatus,comboStatus,kdtTechnologyPar,combosbStatus,txtinnerNumber,combonowStatus,txtzzsShortName,chkcityTest,chkportTest,testDay,txttzdaNumber,tzsbStatus,prmtasset,prmtassetStatus,txtassetValue,txtinstallCost,chkisMainEqm,txtEqmCategory}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(10, 10, 1014, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1014, 629));
        contCreator.setBounds(new Rectangle(25, 601, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(25, 601, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(25, 577, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(25, 577, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(368, 601, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(368, 601, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(368, 577, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(368, 577, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(48, 841, 53, 25));
        this.add(contCU, new KDLayout.Constraints(48, 841, 53, 25, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(378, 878, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(378, 878, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(47, 878, 50, 23));
        this.add(contDescription, new KDLayout.Constraints(47, 878, 50, 23, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(713, 601, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(713, 601, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(378, 830, 51, 19));
        this.add(contStatus, new KDLayout.Constraints(378, 830, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(709, 830, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(709, 830, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(713, 577, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(713, 577, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane1.setBounds(new Rectangle(8, 4, 993, 569));
        this.add(kDTabbedPane1, new KDLayout.Constraints(8, 4, 993, 569, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contCU
        contCU.setBoundEditor(prmtCU);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //contBizStatus
        contBizStatus.setBoundEditor(comboBizStatus);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel6, resHelper.getString("kDPanel6.constraints"));
        kDTabbedPane1.add(kDPanel7, resHelper.getString("kDPanel7.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 992, 536));        kDPanel8.setBounds(new Rectangle(2, 3, 983, 177));
        kDPanel1.add(kDPanel8, new KDLayout.Constraints(2, 3, 983, 177, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel2.setBounds(new Rectangle(2, 184, 983, 72));
        kDPanel1.add(kDPanel2, new KDLayout.Constraints(2, 184, 983, 72, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel10.setBounds(new Rectangle(2, 260, 983, 116));
        kDPanel1.add(kDPanel10, new KDLayout.Constraints(2, 260, 983, 116, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel11.setBounds(new Rectangle(2, 380, 983, 93));
        kDPanel1.add(kDPanel11, new KDLayout.Constraints(2, 380, 983, 93, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel3.setBounds(new Rectangle(2, 477, 983, 55));
        kDPanel1.add(kDPanel3, new KDLayout.Constraints(2, 477, 983, 55, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanel8
        kDPanel8.setLayout(new KDLayout());
        kDPanel8.putClientProperty("OriginalBounds", new Rectangle(2, 3, 983, 177));        consbDescription.setBounds(new Rectangle(355, 139, 613, 19));
        kDPanel8.add(consbDescription, new KDLayout.Constraints(355, 139, 613, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conname.setBounds(new Rectangle(355, 13, 270, 19));
        kDPanel8.add(conname, new KDLayout.Constraints(355, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conssOrgUnit.setBounds(new Rectangle(699, 55, 270, 19));
        kDPanel8.add(conssOrgUnit, new KDLayout.Constraints(699, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conjhOrgUnit.setBounds(new Rectangle(11, 34, 270, 19));
        kDPanel8.add(conjhOrgUnit, new KDLayout.Constraints(11, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conwxOrgUnit.setBounds(new Rectangle(355, 34, 270, 19));
        kDPanel8.add(conwxOrgUnit, new KDLayout.Constraints(355, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conmodel.setBounds(new Rectangle(11, 76, 270, 19));
        kDPanel8.add(conmodel, new KDLayout.Constraints(11, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        consize.setBounds(new Rectangle(355, 76, 270, 19));
        kDPanel8.add(consize, new KDLayout.Constraints(355, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conweight.setBounds(new Rectangle(11, 118, 270, 19));
        kDPanel8.add(conweight, new KDLayout.Constraints(11, 118, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conwxDept.setBounds(new Rectangle(699, 34, 270, 19));
        kDPanel8.add(conwxDept, new KDLayout.Constraints(699, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conqyDate.setBounds(new Rectangle(11, 97, 270, 19));
        kDPanel8.add(conqyDate, new KDLayout.Constraints(11, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conserialNumber.setBounds(new Rectangle(699, 76, 270, 19));
        kDPanel8.add(conserialNumber, new KDLayout.Constraints(699, 76, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        consbStatus.setBounds(new Rectangle(355, 97, 270, 19));
        kDPanel8.add(consbStatus, new KDLayout.Constraints(355, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conunit.setBounds(new Rectangle(355, 118, 270, 19));
        kDPanel8.add(conunit, new KDLayout.Constraints(355, 118, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkspecial.setBounds(new Rectangle(699, 118, 90, 19));
        kDPanel8.add(chkspecial, new KDLayout.Constraints(699, 118, 90, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contype.setBounds(new Rectangle(11, 55, 270, 19));
        kDPanel8.add(contype, new KDLayout.Constraints(11, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continnerNumber.setBounds(new Rectangle(699, 13, 270, 19));
        kDPanel8.add(continnerNumber, new KDLayout.Constraints(699, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contnowStatus.setBounds(new Rectangle(699, 97, 270, 19));
        kDPanel8.add(contnowStatus, new KDLayout.Constraints(699, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contzzsShortName.setBounds(new Rectangle(11, 139, 270, 19));
        kDPanel8.add(contzzsShortName, new KDLayout.Constraints(11, 139, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkisMainEqm.setBounds(new Rectangle(792, 118, 104, 19));
        kDPanel8.add(chkisMainEqm, new KDLayout.Constraints(792, 118, 104, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(11, 13, 270, 19));
        kDPanel8.add(contNumber, new KDLayout.Constraints(11, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEqmCategory.setBounds(new Rectangle(355, 55, 270, 19));
        kDPanel8.add(contEqmCategory, new KDLayout.Constraints(355, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //consbDescription
        consbDescription.setBoundEditor(txtparent);
        //conname
        conname.setBoundEditor(txtname);
        //conssOrgUnit
        conssOrgUnit.setBoundEditor(prmtssOrgUnit);
        //conjhOrgUnit
        conjhOrgUnit.setBoundEditor(prmtjhOrgUnit);
        //conwxOrgUnit
        conwxOrgUnit.setBoundEditor(prmtwxOrgUnit);
        //conmodel
        conmodel.setBoundEditor(txtmodel);
        //consize
        consize.setBoundEditor(txtsize);
        //conweight
        conweight.setBoundEditor(txtweight);
        //conwxDept
        conwxDept.setBoundEditor(prmtwxDept);
        //conqyDate
        conqyDate.setBoundEditor(pkqyDate);
        //conserialNumber
        conserialNumber.setBoundEditor(txtserialNumber);
        //consbStatus
        consbStatus.setBoundEditor(combosbStatus);
        //conunit
        conunit.setBoundEditor(prmtunit);
        //contype
        contype.setBoundEditor(prmttype);
        //continnerNumber
        continnerNumber.setBoundEditor(txtinnerNumber);
        //contnowStatus
        contnowStatus.setBoundEditor(combonowStatus);
        //contzzsShortName
        contzzsShortName.setBoundEditor(txtzzsShortName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contEqmCategory
        contEqmCategory.setBoundEditor(txtEqmCategory);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(2, 184, 983, 72));        conparent.setBounds(new Rectangle(11, 14, 270, 19));
        kDPanel2.add(conparent, new KDLayout.Constraints(11, 14, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkdependable.setBounds(new Rectangle(357, 14, 82, 19));
        kDPanel2.add(chkdependable, new KDLayout.Constraints(357, 14, 82, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conaddress.setBounds(new Rectangle(699, 14, 270, 19));
        kDPanel2.add(conaddress, new KDLayout.Constraints(699, 14, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conlocation.setBounds(new Rectangle(11, 35, 270, 19));
        kDPanel2.add(conlocation, new KDLayout.Constraints(11, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conusingDept.setBounds(new Rectangle(355, 35, 270, 19));
        kDPanel2.add(conusingDept, new KDLayout.Constraints(355, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conresPerson.setBounds(new Rectangle(699, 35, 270, 19));
        kDPanel2.add(conresPerson, new KDLayout.Constraints(699, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //conparent
        conparent.setBoundEditor(prmtparent);
        //conaddress
        conaddress.setBoundEditor(prmtaddress);
        //conlocation
        conlocation.setBoundEditor(txtlocation);
        //conusingDept
        conusingDept.setBoundEditor(prmtusingDept);
        //conresPerson
        conresPerson.setBoundEditor(prmtresPerson);
        //kDPanel10
        kDPanel10.setLayout(new KDLayout());
        kDPanel10.putClientProperty("OriginalBounds", new Rectangle(2, 260, 983, 116));        conmader.setBounds(new Rectangle(11, 15, 270, 19));
        kDPanel10.add(conmader, new KDLayout.Constraints(11, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conmadedCountry.setBounds(new Rectangle(355, 15, 270, 19));
        kDPanel10.add(conmadedCountry, new KDLayout.Constraints(355, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conmadeDate.setBounds(new Rectangle(699, 15, 270, 19));
        kDPanel10.add(conmadeDate, new KDLayout.Constraints(699, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        consupplier.setBounds(new Rectangle(11, 36, 270, 19));
        kDPanel10.add(consupplier, new KDLayout.Constraints(11, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conreachedDate.setBounds(new Rectangle(355, 36, 270, 19));
        kDPanel10.add(conreachedDate, new KDLayout.Constraints(355, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conccNumber.setBounds(new Rectangle(699, 36, 270, 19));
        kDPanel10.add(conccNumber, new KDLayout.Constraints(699, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        coninstaller.setBounds(new Rectangle(11, 57, 270, 19));
        kDPanel10.add(coninstaller, new KDLayout.Constraints(11, 57, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        condebuger.setBounds(new Rectangle(355, 57, 270, 19));
        kDPanel10.add(condebuger, new KDLayout.Constraints(355, 57, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        concheckDate.setBounds(new Rectangle(699, 57, 270, 19));
        kDPanel10.add(concheckDate, new KDLayout.Constraints(699, 57, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        condeadline.setBounds(new Rectangle(11, 78, 270, 19));
        kDPanel10.add(condeadline, new KDLayout.Constraints(11, 78, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        consourceUnit.setBounds(new Rectangle(355, 78, 270, 19));
        kDPanel10.add(consourceUnit, new KDLayout.Constraints(355, 78, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //conmader
        conmader.setBoundEditor(txtmader);
        //conmadedCountry
        conmadedCountry.setBoundEditor(prmtmadedCountry);
        //conmadeDate
        conmadeDate.setBoundEditor(pkmadeDate);
        //consupplier
        consupplier.setBoundEditor(prmtsupplier);
        //conreachedDate
        conreachedDate.setBoundEditor(pkreachedDate);
        //conccNumber
        conccNumber.setBoundEditor(txtccNumber);
        //coninstaller
        coninstaller.setBoundEditor(prmtinstaller);
        //condebuger
        condebuger.setBoundEditor(prmtdebuger);
        //concheckDate
        concheckDate.setBoundEditor(pkcheckDate);
        //condeadline
        condeadline.setBoundEditor(pkdeadline);
        //consourceUnit
        consourceUnit.setBoundEditor(txtsourceUnit);
        //kDPanel11
        kDPanel11.setLayout(new KDLayout());
        kDPanel11.putClientProperty("OriginalBounds", new Rectangle(2, 380, 983, 93));        chkportTest.setBounds(new Rectangle(286, 34, 65, 19));
        kDPanel11.add(chkportTest, new KDLayout.Constraints(286, 34, 65, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkcityTest.setBounds(new Rectangle(286, 14, 67, 19));
        kDPanel11.add(chkcityTest, new KDLayout.Constraints(286, 14, 67, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttestDay.setBounds(new Rectangle(699, 13, 270, 19));
        kDPanel11.add(conttestDay, new KDLayout.Constraints(699, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conttzdaNumber.setBounds(new Rectangle(11, 13, 270, 19));
        kDPanel11.add(conttzdaNumber, new KDLayout.Constraints(11, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttzsbStatus.setBounds(new Rectangle(699, 34, 270, 19));
        kDPanel11.add(conttzsbStatus, new KDLayout.Constraints(699, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contspecialType.setBounds(new Rectangle(355, 34, 270, 19));
        kDPanel11.add(contspecialType, new KDLayout.Constraints(355, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcityPeriod.setBounds(new Rectangle(11, 34, 130, 19));
        kDPanel11.add(contcityPeriod, new KDLayout.Constraints(11, 34, 130, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contportPeriod.setBounds(new Rectangle(151, 34, 130, 19));
        kDPanel11.add(contportPeriod, new KDLayout.Constraints(151, 34, 130, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcode.setBounds(new Rectangle(355, 13, 270, 19));
        kDPanel11.add(contcode, new KDLayout.Constraints(355, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contengineNumber.setBounds(new Rectangle(355, 55, 270, 19));
        kDPanel11.add(contengineNumber, new KDLayout.Constraints(355, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcarNumber.setBounds(new Rectangle(699, 55, 270, 19));
        kDPanel11.add(contcarNumber, new KDLayout.Constraints(699, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contratedWeight.setBounds(new Rectangle(11, 55, 270, 19));
        kDPanel11.add(contratedWeight, new KDLayout.Constraints(11, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //conttestDay
        conttestDay.setBoundEditor(testDay);
        //conttzdaNumber
        conttzdaNumber.setBoundEditor(txttzdaNumber);
        //conttzsbStatus
        conttzsbStatus.setBoundEditor(tzsbStatus);
        //contspecialType
        contspecialType.setBoundEditor(prmtspecialType);
        //contcityPeriod
        contcityPeriod.setBoundEditor(txtcityPeriod);
        //contportPeriod
        contportPeriod.setBoundEditor(txtportPeriod);
        //contcode
        contcode.setBoundEditor(txtcode);
        //contengineNumber
        contengineNumber.setBoundEditor(txtengineNumber);
        //contcarNumber
        contcarNumber.setBoundEditor(txtcarNumber);
        //contratedWeight
        contratedWeight.setBoundEditor(txtratedWeight);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(2, 477, 983, 55));        contasset.setBounds(new Rectangle(11, 15, 270, 19));
        kDPanel3.add(contasset, new KDLayout.Constraints(11, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contassetStatus.setBounds(new Rectangle(699, 17, 270, 19));
        kDPanel3.add(contassetStatus, new KDLayout.Constraints(699, 17, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contassetValue.setBounds(new Rectangle(355, 17, 270, 19));
        kDPanel3.add(contassetValue, new KDLayout.Constraints(355, 17, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continstallCost.setBounds(new Rectangle(523, 41, 89, 19));
        kDPanel3.add(continstallCost, new KDLayout.Constraints(523, 41, 89, 19, 0));
        //contasset
        contasset.setBoundEditor(prmtasset);
        //contassetStatus
        contassetStatus.setBoundEditor(prmtassetStatus);
        //contassetValue
        contassetValue.setBoundEditor(txtassetValue);
        //continstallCost
        continstallCost.setBoundEditor(txtinstallCost);
        //kDPanel6
kDPanel6.setLayout(new BorderLayout(0, 0));        kdtTechnologyPar_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtTechnologyPar,new com.kingdee.eas.port.equipment.record.EquIdTechnologyParInfo(),null,false);
        kDPanel6.add(kdtTechnologyPar_detailPanel, BorderLayout.CENTER);
        //kDPanel7
kDPanel7.setLayout(new BorderLayout(0, 0));        kdtSpareInfo_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtSpareInfo,new com.kingdee.eas.port.equipment.record.EquIdSpareInfoInfo(),null,false);
        kDPanel7.add(kdtSpareInfo_detailPanel, BorderLayout.CENTER);

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
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
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
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
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
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("special", boolean.class, this.chkspecial, "selected");
		dataBinder.registerBinding("isMainEqm", boolean.class, this.chkisMainEqm, "selected");
		dataBinder.registerBinding("parent", com.kingdee.eas.port.equipment.record.EquIdInfo.class, this.txtparent, "text");
		dataBinder.registerBinding("name", String.class, this.txtname, "text");
		dataBinder.registerBinding("ssOrgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtssOrgUnit, "data");
		dataBinder.registerBinding("jhOrgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtjhOrgUnit, "data");
		dataBinder.registerBinding("wxOrgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtwxOrgUnit, "data");
		dataBinder.registerBinding("model", String.class, this.txtmodel, "text");
		dataBinder.registerBinding("size", String.class, this.txtsize, "text");
		dataBinder.registerBinding("weight", String.class, this.txtweight, "text");
		dataBinder.registerBinding("wxDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtwxDept, "data");
		dataBinder.registerBinding("qyDate", java.util.Date.class, this.pkqyDate, "value");
		dataBinder.registerBinding("serialNumber", String.class, this.txtserialNumber, "text");
		dataBinder.registerBinding("sbStatus", com.kingdee.eas.port.equipment.base.enumbase.sbStatusType.class, this.combosbStatus, "selectedItem");
		dataBinder.registerBinding("unit", com.kingdee.eas.basedata.assistant.MeasureUnitInfo.class, this.prmtunit, "data");
		dataBinder.registerBinding("eqmType", com.kingdee.eas.fi.fa.basedata.FaCatInfo.class, this.prmttype, "data");
		dataBinder.registerBinding("innerNumber", String.class, this.txtinnerNumber, "text");
		dataBinder.registerBinding("nowStatus", com.kingdee.eas.port.equipment.base.enumbase.nowStatusType.class, this.combonowStatus, "selectedItem");
		dataBinder.registerBinding("zzsShortName", String.class, this.txtzzsShortName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("EqmCategory", String.class, this.txtEqmCategory, "text");
		dataBinder.registerBinding("dependable", boolean.class, this.chkdependable, "selected");
		dataBinder.registerBinding("parent", com.kingdee.eas.port.equipment.record.EquIdInfo.class, this.prmtparent, "data");
		dataBinder.registerBinding("address", com.kingdee.eas.basedata.assistant.AddressInfo.class, this.prmtaddress, "data");
		dataBinder.registerBinding("location", String.class, this.txtlocation, "text");
		dataBinder.registerBinding("usingDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtusingDept, "data");
		dataBinder.registerBinding("resPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtresPerson, "data");
		dataBinder.registerBinding("mader", String.class, this.txtmader, "text");
		dataBinder.registerBinding("madedCountry", com.kingdee.eas.basedata.assistant.CountryInfo.class, this.prmtmadedCountry, "data");
		dataBinder.registerBinding("madeDate", java.util.Date.class, this.pkmadeDate, "value");
		dataBinder.registerBinding("supplier", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtsupplier, "data");
		dataBinder.registerBinding("reachedDate", java.util.Date.class, this.pkreachedDate, "value");
		dataBinder.registerBinding("ccNumber", String.class, this.txtccNumber, "text");
		dataBinder.registerBinding("installer", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtinstaller, "data");
		dataBinder.registerBinding("debuger", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtdebuger, "data");
		dataBinder.registerBinding("checkDate", java.util.Date.class, this.pkcheckDate, "value");
		dataBinder.registerBinding("deadline", java.util.Date.class, this.pkdeadline, "value");
		dataBinder.registerBinding("sourceUnit", String.class, this.txtsourceUnit, "text");
		dataBinder.registerBinding("portTest", boolean.class, this.chkportTest, "selected");
		dataBinder.registerBinding("cityTest", boolean.class, this.chkcityTest, "selected");
		dataBinder.registerBinding("testDay", java.sql.Time.class, this.testDay, "value");
		dataBinder.registerBinding("tzdaNumber", String.class, this.txttzdaNumber, "text");
		dataBinder.registerBinding("tzsbStatus", com.kingdee.eas.port.equipment.base.enumbase.sbStatusType.class, this.tzsbStatus, "selectedItem");
		dataBinder.registerBinding("specialType", com.kingdee.eas.port.equipment.base.SpecialTypeInfo.class, this.prmtspecialType, "data");
		dataBinder.registerBinding("cityPeriod", java.math.BigDecimal.class, this.txtcityPeriod, "value");
		dataBinder.registerBinding("portPeriod", java.math.BigDecimal.class, this.txtportPeriod, "value");
		dataBinder.registerBinding("code", String.class, this.txtcode, "text");
		dataBinder.registerBinding("engineNumber", String.class, this.txtengineNumber, "text");
		dataBinder.registerBinding("carNumber", String.class, this.txtcarNumber, "text");
		dataBinder.registerBinding("ratedWeight", java.math.BigDecimal.class, this.txtratedWeight, "value");
		dataBinder.registerBinding("asset", com.kingdee.eas.fi.fa.manage.FaCurCardInfo.class, this.prmtasset, "data");
		dataBinder.registerBinding("assetStatus", com.kingdee.eas.fi.fa.basedata.FaUseStatusInfo.class, this.prmtassetStatus, "data");
		dataBinder.registerBinding("assetValue", java.math.BigDecimal.class, this.txtassetValue, "value");
		dataBinder.registerBinding("installCost", java.math.BigDecimal.class, this.txtinstallCost, "value");
		dataBinder.registerBinding("TechnologyPar.seq", int.class, this.kdtTechnologyPar, "seq.text");
		dataBinder.registerBinding("TechnologyPar", com.kingdee.eas.port.equipment.record.EquIdTechnologyParInfo.class, this.kdtTechnologyPar, "userObject");
		dataBinder.registerBinding("TechnologyPar.parName", String.class, this.kdtTechnologyPar, "parName.text");
		dataBinder.registerBinding("TechnologyPar.parValue", String.class, this.kdtTechnologyPar, "parValue.text");
		dataBinder.registerBinding("TechnologyPar.parInfo", String.class, this.kdtTechnologyPar, "parInfo.text");
		dataBinder.registerBinding("SpareInfo.seq", int.class, this.kdtSpareInfo, "seq.text");
		dataBinder.registerBinding("SpareInfo", com.kingdee.eas.port.equipment.record.EquIdSpareInfoInfo.class, this.kdtSpareInfo, "userObject");
		dataBinder.registerBinding("SpareInfo.materialName", String.class, this.kdtSpareInfo, "materialName.text");
		dataBinder.registerBinding("SpareInfo.speModel", String.class, this.kdtSpareInfo, "speModel.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.record.app.EquIdEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.prmtCU.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.record.EquIdInfo)ov;
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
	 * ????????��??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("special", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isMainEqm", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ssOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jhOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("wxOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("model", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("size", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("weight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("wxDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qyDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("serialNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sbStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("eqmType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("innerNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("nowStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zzsShortName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmCategory", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dependable", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("location", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("usingDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("resPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mader", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("madedCountry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("madeDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reachedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ccNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("installer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("debuger", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("checkDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("deadline", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sourceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("portTest", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cityTest", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("testDay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tzdaNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tzsbStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("specialType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cityPeriod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("portPeriod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("code", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("engineNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("carNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ratedWeight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("asset", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("assetStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("assetValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("installCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TechnologyPar.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TechnologyPar", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TechnologyPar.parName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TechnologyPar.parValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TechnologyPar.parInfo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SpareInfo.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SpareInfo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SpareInfo.materialName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SpareInfo.speModel", ValidateHelper.ON_SAVE);    		
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
     * output chkspecial_stateChanged method
     */
    protected void chkspecial_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output prmttype_dataChanged method
     */
    protected void prmttype_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtasset_dataChanged method
     */
    protected void prmtasset_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output prmttype_Changed() method
     */
    public void prmttype_Changed() throws Exception
    {
        System.out.println("prmttype_Changed() Function is executed!");
            txtEqmCategory.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmttype.getData(),"treeid.name")));


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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("CU.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("CU.id"));
        	sic.add(new SelectorItemInfo("CU.number"));
        	sic.add(new SelectorItemInfo("CU.name"));
		}
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("bizStatus"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("special"));
        sic.add(new SelectorItemInfo("isMainEqm"));
        sic.add(new SelectorItemInfo("parent"));
        sic.add(new SelectorItemInfo("name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("ssOrgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("ssOrgUnit.id"));
        	sic.add(new SelectorItemInfo("ssOrgUnit.number"));
        	sic.add(new SelectorItemInfo("ssOrgUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("jhOrgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("jhOrgUnit.id"));
        	sic.add(new SelectorItemInfo("jhOrgUnit.number"));
        	sic.add(new SelectorItemInfo("jhOrgUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("wxOrgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("wxOrgUnit.id"));
        	sic.add(new SelectorItemInfo("wxOrgUnit.number"));
        	sic.add(new SelectorItemInfo("wxOrgUnit.name"));
		}
        sic.add(new SelectorItemInfo("model"));
        sic.add(new SelectorItemInfo("size"));
        sic.add(new SelectorItemInfo("weight"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("wxDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("wxDept.id"));
        	sic.add(new SelectorItemInfo("wxDept.number"));
        	sic.add(new SelectorItemInfo("wxDept.name"));
		}
        sic.add(new SelectorItemInfo("qyDate"));
        sic.add(new SelectorItemInfo("serialNumber"));
        sic.add(new SelectorItemInfo("sbStatus"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("unit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("unit.id"));
        	sic.add(new SelectorItemInfo("unit.number"));
        	sic.add(new SelectorItemInfo("unit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("eqmType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("eqmType.id"));
        	sic.add(new SelectorItemInfo("eqmType.number"));
        	sic.add(new SelectorItemInfo("eqmType.name"));
		}
        sic.add(new SelectorItemInfo("innerNumber"));
        sic.add(new SelectorItemInfo("nowStatus"));
        sic.add(new SelectorItemInfo("zzsShortName"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("EqmCategory"));
        sic.add(new SelectorItemInfo("dependable"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("parent.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("parent.id"));
        	sic.add(new SelectorItemInfo("parent.number"));
        	sic.add(new SelectorItemInfo("parent.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("address.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("address.id"));
        	sic.add(new SelectorItemInfo("address.number"));
        	sic.add(new SelectorItemInfo("address.name"));
        	sic.add(new SelectorItemInfo("address.detailAddress"));
		}
        sic.add(new SelectorItemInfo("location"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("usingDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("usingDept.id"));
        	sic.add(new SelectorItemInfo("usingDept.number"));
        	sic.add(new SelectorItemInfo("usingDept.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("resPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("resPerson.id"));
        	sic.add(new SelectorItemInfo("resPerson.number"));
        	sic.add(new SelectorItemInfo("resPerson.name"));
		}
        sic.add(new SelectorItemInfo("mader"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("madedCountry.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("madedCountry.id"));
        	sic.add(new SelectorItemInfo("madedCountry.number"));
        	sic.add(new SelectorItemInfo("madedCountry.name"));
		}
        sic.add(new SelectorItemInfo("madeDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("supplier.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("supplier.id"));
        	sic.add(new SelectorItemInfo("supplier.number"));
        	sic.add(new SelectorItemInfo("supplier.name"));
		}
        sic.add(new SelectorItemInfo("reachedDate"));
        sic.add(new SelectorItemInfo("ccNumber"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("installer.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("installer.id"));
        	sic.add(new SelectorItemInfo("installer.number"));
        	sic.add(new SelectorItemInfo("installer.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("debuger.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("debuger.id"));
        	sic.add(new SelectorItemInfo("debuger.number"));
        	sic.add(new SelectorItemInfo("debuger.name"));
		}
        sic.add(new SelectorItemInfo("checkDate"));
        sic.add(new SelectorItemInfo("deadline"));
        sic.add(new SelectorItemInfo("sourceUnit"));
        sic.add(new SelectorItemInfo("portTest"));
        sic.add(new SelectorItemInfo("cityTest"));
        sic.add(new SelectorItemInfo("testDay"));
        sic.add(new SelectorItemInfo("tzdaNumber"));
        sic.add(new SelectorItemInfo("tzsbStatus"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("specialType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("specialType.id"));
        	sic.add(new SelectorItemInfo("specialType.number"));
        	sic.add(new SelectorItemInfo("specialType.name"));
		}
        sic.add(new SelectorItemInfo("cityPeriod"));
        sic.add(new SelectorItemInfo("portPeriod"));
        sic.add(new SelectorItemInfo("code"));
        sic.add(new SelectorItemInfo("engineNumber"));
        sic.add(new SelectorItemInfo("carNumber"));
        sic.add(new SelectorItemInfo("ratedWeight"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("asset.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("asset.id"));
        	sic.add(new SelectorItemInfo("asset.number"));
        	sic.add(new SelectorItemInfo("asset.assetName"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("assetStatus.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("assetStatus.id"));
        	sic.add(new SelectorItemInfo("assetStatus.number"));
        	sic.add(new SelectorItemInfo("assetStatus.name"));
		}
        sic.add(new SelectorItemInfo("assetValue"));
        sic.add(new SelectorItemInfo("installCost"));
    	sic.add(new SelectorItemInfo("TechnologyPar.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("TechnologyPar.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("TechnologyPar.parName"));
    	sic.add(new SelectorItemInfo("TechnologyPar.parValue"));
    	sic.add(new SelectorItemInfo("TechnologyPar.parInfo"));
    	sic.add(new SelectorItemInfo("SpareInfo.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("SpareInfo.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("SpareInfo.materialName"));
    	sic.add(new SelectorItemInfo("SpareInfo.speModel"));
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
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
    	

    /**
     * output actionInUse_actionPerformed method
     */
    public void actionInUse_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().inUse(editData);
    }
    	

    /**
     * output actionOutUse_actionPerformed method
     */
    public void actionOutUse_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().outUse(editData);
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
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionUnAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionInUse(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInUse() {
    	return false;
    }
	public RequestContext prepareActionOutUse(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOutUse() {
    	return false;
    }

    /**
     * output ActionInUse class
     */     
    protected class ActionInUse extends ItemAction {     
    
        public ActionInUse()
        {
            this(null);
        }

        public ActionInUse(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInUse.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInUse.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInUse.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdEditUI.this, "ActionInUse", "actionInUse_actionPerformed", e);
        }
    }

    /**
     * output ActionOutUse class
     */     
    protected class ActionOutUse extends ItemAction {     
    
        public ActionOutUse()
        {
            this(null);
        }

        public ActionOutUse(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionOutUse.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOutUse.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOutUse.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdEditUI.this, "ActionOutUse", "actionOutUse_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.record.client", "EquIdEditUI");
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
        return com.kingdee.eas.port.equipment.record.client.EquIdEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.record.EquIdInfo objectValue = new com.kingdee.eas.port.equipment.record.EquIdInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/record/EquId";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.record.app.EquIdQuery");
	}
    
        
					protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(combosbStatus.getSelectedItem())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"�豸״̬"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmttype.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"�豸����"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtNumber.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"���ݱ��"});
		}
			super.beforeStoreFields(arg0);
		}

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtTechnologyPar;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("sbStatus","1");
vo.put("nowStatus","1");
vo.put("tzsbStatus","1");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}