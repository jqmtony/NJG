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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdaytow;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdayone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttestDay;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkdependable;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conresPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contportPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continstallCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conjhOrgUnit;
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
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel8;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel10;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel11;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEqmCategory;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer consbDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conname;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conssOrgUnit;
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
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisMainEqm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conparent;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisbaoxian;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisccCheck;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conusingDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conlocation;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conaddress;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtsbDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtname;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtssOrgUnit;
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
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtparent;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtusingDept;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtlocation;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtaddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conmader;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conmadedCountry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conmadeDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer consupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conreachedDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conccNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer coninstaller;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer concheckDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer consourceUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdeadline;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdebuger;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmader;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtmadedCountry;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkmadeDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsupplier;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkreachedDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtccNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtinstaller;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkcheckDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsourceUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdeadline;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdebuger;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkportTest;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkcityTest;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttzdaNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttzsbStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contspecialType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcityPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contengineNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcarNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contratedWeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttextDate1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzzsShortName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttelePhoneNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contactrueTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contresponsible;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continStreet;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttextType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contequTypeone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcpgjzs;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcpsyqh;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcpsbh;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkchuanCheck;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttzdaNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox tzsbStatus;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtspecialType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtcityPeriod;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcode;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtengineNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcarNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtratedWeight;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pktextDate1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzzsShortName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttelePhoneNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkactrueTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtresponsible;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtinStreet;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmttextType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtequTypeone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcpgjzs;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcpsyqh;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcpsbh;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contasset;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contassetStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contassetValue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contnowAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contoldYear;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtasset;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtassetStatus;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtassetValue;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtnowAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtoldYear;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEqmCategory;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtTechnologyPar;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtTechnologyPar_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSpareInfo;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtSpareInfo_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE3;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE3_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkdaytow;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkdayone;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker testDay;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtresPerson;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtportPeriod;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtinstallCost;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtjhOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRegistChange;
    protected com.kingdee.eas.port.equipment.record.EquIdInfo editData = null;
    protected ActionInUse actionInUse = null;
    protected ActionOutUse actionOutUse = null;
    protected ActionRegistChange actionRegistChange = null;
    protected ActionExcel actionExcel = null;
    protected ActionExcelFoced actionExcelFoced = null;
    protected ActionExcelEqu actionExcelEqu = null;
    protected ActionZhuyao actionZhuyao = null;
    protected ActionBeijian actionBeijian = null;
    protected ActionXiangxi actionXiangxi = null;
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
        //actionRegistChange
        this.actionRegistChange = new ActionRegistChange(this);
        getActionManager().registerAction("actionRegistChange", actionRegistChange);
        this.actionRegistChange.setExtendProperty("canForewarn", "true");
        this.actionRegistChange.setExtendProperty("userDefined", "true");
        this.actionRegistChange.setExtendProperty("isObjectUpdateLock", "false");
         this.actionRegistChange.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRegistChange.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionExcel
        this.actionExcel = new ActionExcel(this);
        getActionManager().registerAction("actionExcel", actionExcel);
        this.actionExcel.setExtendProperty("canForewarn", "true");
        this.actionExcel.setExtendProperty("userDefined", "true");
        this.actionExcel.setExtendProperty("isObjectUpdateLock", "false");
         this.actionExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionExcel.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionExcelFoced
        this.actionExcelFoced = new ActionExcelFoced(this);
        getActionManager().registerAction("actionExcelFoced", actionExcelFoced);
        this.actionExcelFoced.setExtendProperty("canForewarn", "true");
        this.actionExcelFoced.setExtendProperty("userDefined", "true");
        this.actionExcelFoced.setExtendProperty("isObjectUpdateLock", "false");
         this.actionExcelFoced.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionExcelFoced.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionExcelEqu
        this.actionExcelEqu = new ActionExcelEqu(this);
        getActionManager().registerAction("actionExcelEqu", actionExcelEqu);
        this.actionExcelEqu.setExtendProperty("canForewarn", "true");
        this.actionExcelEqu.setExtendProperty("userDefined", "true");
        this.actionExcelEqu.setExtendProperty("isObjectUpdateLock", "false");
         this.actionExcelEqu.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionExcelEqu.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionZhuyao
        this.actionZhuyao = new ActionZhuyao(this);
        getActionManager().registerAction("actionZhuyao", actionZhuyao);
        this.actionZhuyao.setExtendProperty("canForewarn", "true");
        this.actionZhuyao.setExtendProperty("userDefined", "true");
        this.actionZhuyao.setExtendProperty("isObjectUpdateLock", "false");
         this.actionZhuyao.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionZhuyao.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionBeijian
        this.actionBeijian = new ActionBeijian(this);
        getActionManager().registerAction("actionBeijian", actionBeijian);
        this.actionBeijian.setExtendProperty("canForewarn", "true");
        this.actionBeijian.setExtendProperty("userDefined", "true");
        this.actionBeijian.setExtendProperty("isObjectUpdateLock", "false");
         this.actionBeijian.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionBeijian.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionXiangxi
        this.actionXiangxi = new ActionXiangxi(this);
        getActionManager().registerAction("actionXiangxi", actionXiangxi);
        this.actionXiangxi.setExtendProperty("canForewarn", "true");
        this.actionXiangxi.setExtendProperty("userDefined", "true");
        this.actionXiangxi.setExtendProperty("isObjectUpdateLock", "false");
         this.actionXiangxi.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionXiangxi.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
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
        this.contdaytow = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdayone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttestDay = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkdependable = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.conresPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contportPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continstallCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conjhOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel8 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel10 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel11 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contEqmCategory = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.consbDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conname = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conssOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.chkisMainEqm = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conparent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisbaoxian = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkisccCheck = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.conusingDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conlocation = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conaddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtsbDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtname = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtssOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
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
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtparent = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtusingDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtlocation = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtaddress = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.conmader = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conmadedCountry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conmadeDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.consupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conreachedDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conccNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.coninstaller = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.concheckDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.consourceUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdeadline = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdebuger = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtmader = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtmadedCountry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkmadeDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtsupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkreachedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtccNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtinstaller = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkcheckDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtsourceUnit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdeadline = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdebuger = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.chkportTest = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkcityTest = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.conttzdaNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttzsbStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contspecialType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcityPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contengineNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcarNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contratedWeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttextDate1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzzsShortName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttelePhoneNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contactrueTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contresponsible = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continStreet = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttextType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contequTypeone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcpgjzs = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcpsyqh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcpsbh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkchuanCheck = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txttzdaNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tzsbStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtspecialType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcityPeriod = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtcode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtengineNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcarNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtratedWeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pktextDate1 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtzzsShortName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttelePhoneNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkactrueTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtresponsible = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtinStreet = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmttextType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtequTypeone = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtcpgjzs = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcpsyqh = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcpsbh = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contasset = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contassetStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contassetValue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contnowAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contoldYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtasset = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtassetStatus = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtassetValue = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtnowAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtoldYear = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtEqmCategory = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdtTechnologyPar = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtSpareInfo = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtE3 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.pkdaytow = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkdayone = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.testDay = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtresPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtportPeriod = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtinstallCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtjhOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnRegistChange = new com.kingdee.bos.ctrl.swing.KDWorkButton();
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
        this.contdaytow.setName("contdaytow");
        this.contdayone.setName("contdayone");
        this.conttestDay.setName("conttestDay");
        this.chkdependable.setName("chkdependable");
        this.conresPerson.setName("conresPerson");
        this.contportPeriod.setName("contportPeriod");
        this.continstallCost.setName("continstallCost");
        this.conjhOrgUnit.setName("conjhOrgUnit");
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
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel8.setName("kDPanel8");
        this.kDPanel10.setName("kDPanel10");
        this.kDPanel11.setName("kDPanel11");
        this.kDPanel3.setName("kDPanel3");
        this.contEqmCategory.setName("contEqmCategory");
        this.consbDescription.setName("consbDescription");
        this.conname.setName("conname");
        this.conssOrgUnit.setName("conssOrgUnit");
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
        this.chkisMainEqm.setName("chkisMainEqm");
        this.contNumber.setName("contNumber");
        this.conparent.setName("conparent");
        this.chkisbaoxian.setName("chkisbaoxian");
        this.chkisccCheck.setName("chkisccCheck");
        this.conusingDept.setName("conusingDept");
        this.conlocation.setName("conlocation");
        this.conaddress.setName("conaddress");
        this.txtsbDescription.setName("txtsbDescription");
        this.txtname.setName("txtname");
        this.prmtssOrgUnit.setName("prmtssOrgUnit");
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
        this.txtNumber.setName("txtNumber");
        this.prmtparent.setName("prmtparent");
        this.prmtusingDept.setName("prmtusingDept");
        this.txtlocation.setName("txtlocation");
        this.prmtaddress.setName("prmtaddress");
        this.conmader.setName("conmader");
        this.conmadedCountry.setName("conmadedCountry");
        this.conmadeDate.setName("conmadeDate");
        this.consupplier.setName("consupplier");
        this.conreachedDate.setName("conreachedDate");
        this.conccNumber.setName("conccNumber");
        this.coninstaller.setName("coninstaller");
        this.concheckDate.setName("concheckDate");
        this.consourceUnit.setName("consourceUnit");
        this.contdeadline.setName("contdeadline");
        this.contdebuger.setName("contdebuger");
        this.txtmader.setName("txtmader");
        this.prmtmadedCountry.setName("prmtmadedCountry");
        this.pkmadeDate.setName("pkmadeDate");
        this.prmtsupplier.setName("prmtsupplier");
        this.pkreachedDate.setName("pkreachedDate");
        this.txtccNumber.setName("txtccNumber");
        this.prmtinstaller.setName("prmtinstaller");
        this.pkcheckDate.setName("pkcheckDate");
        this.txtsourceUnit.setName("txtsourceUnit");
        this.txtdeadline.setName("txtdeadline");
        this.txtdebuger.setName("txtdebuger");
        this.chkportTest.setName("chkportTest");
        this.chkcityTest.setName("chkcityTest");
        this.conttzdaNumber.setName("conttzdaNumber");
        this.conttzsbStatus.setName("conttzsbStatus");
        this.contspecialType.setName("contspecialType");
        this.contcityPeriod.setName("contcityPeriod");
        this.contcode.setName("contcode");
        this.contengineNumber.setName("contengineNumber");
        this.contcarNumber.setName("contcarNumber");
        this.contratedWeight.setName("contratedWeight");
        this.conttextDate1.setName("conttextDate1");
        this.contzzsShortName.setName("contzzsShortName");
        this.conttelePhoneNumber.setName("conttelePhoneNumber");
        this.contactrueTime.setName("contactrueTime");
        this.contresponsible.setName("contresponsible");
        this.continStreet.setName("continStreet");
        this.conttextType.setName("conttextType");
        this.contequTypeone.setName("contequTypeone");
        this.contcpgjzs.setName("contcpgjzs");
        this.contcpsyqh.setName("contcpsyqh");
        this.contcpsbh.setName("contcpsbh");
        this.chkchuanCheck.setName("chkchuanCheck");
        this.txttzdaNumber.setName("txttzdaNumber");
        this.tzsbStatus.setName("tzsbStatus");
        this.prmtspecialType.setName("prmtspecialType");
        this.txtcityPeriod.setName("txtcityPeriod");
        this.txtcode.setName("txtcode");
        this.txtengineNumber.setName("txtengineNumber");
        this.txtcarNumber.setName("txtcarNumber");
        this.txtratedWeight.setName("txtratedWeight");
        this.pktextDate1.setName("pktextDate1");
        this.txtzzsShortName.setName("txtzzsShortName");
        this.txttelePhoneNumber.setName("txttelePhoneNumber");
        this.pkactrueTime.setName("pkactrueTime");
        this.txtresponsible.setName("txtresponsible");
        this.txtinStreet.setName("txtinStreet");
        this.prmttextType.setName("prmttextType");
        this.prmtequTypeone.setName("prmtequTypeone");
        this.txtcpgjzs.setName("txtcpgjzs");
        this.txtcpsyqh.setName("txtcpsyqh");
        this.txtcpsbh.setName("txtcpsbh");
        this.contasset.setName("contasset");
        this.contassetStatus.setName("contassetStatus");
        this.contassetValue.setName("contassetValue");
        this.contnowAmount.setName("contnowAmount");
        this.contoldYear.setName("contoldYear");
        this.prmtasset.setName("prmtasset");
        this.prmtassetStatus.setName("prmtassetStatus");
        this.txtassetValue.setName("txtassetValue");
        this.txtnowAmount.setName("txtnowAmount");
        this.txtoldYear.setName("txtoldYear");
        this.txtEqmCategory.setName("txtEqmCategory");
        this.kdtTechnologyPar.setName("kdtTechnologyPar");
        this.kdtSpareInfo.setName("kdtSpareInfo");
        this.kdtE3.setName("kdtE3");
        this.pkdaytow.setName("pkdaytow");
        this.pkdayone.setName("pkdayone");
        this.testDay.setName("testDay");
        this.prmtresPerson.setName("prmtresPerson");
        this.txtportPeriod.setName("txtportPeriod");
        this.txtinstallCost.setName("txtinstallCost");
        this.prmtjhOrgUnit.setName("prmtjhOrgUnit");
        this.btnRegistChange.setName("btnRegistChange");
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
        // contdaytow		
        this.contdaytow.setBoundLabelText(resHelper.getString("contdaytow.boundLabelText"));		
        this.contdaytow.setBoundLabelLength(100);		
        this.contdaytow.setBoundLabelUnderline(true);		
        this.contdaytow.setEnabled(false);		
        this.contdaytow.setVisible(false);
        // contdayone		
        this.contdayone.setBoundLabelText(resHelper.getString("contdayone.boundLabelText"));		
        this.contdayone.setBoundLabelLength(100);		
        this.contdayone.setBoundLabelUnderline(true);		
        this.contdayone.setEnabled(false);		
        this.contdayone.setVisible(false);
        // conttestDay		
        this.conttestDay.setBoundLabelText(resHelper.getString("conttestDay.boundLabelText"));		
        this.conttestDay.setBoundLabelLength(100);		
        this.conttestDay.setBoundLabelUnderline(true);		
        this.conttestDay.setVisible(false);		
        this.conttestDay.setEnabled(false);
        // chkdependable		
        this.chkdependable.setText(resHelper.getString("chkdependable.text"));		
        this.chkdependable.setVisible(false);		
        this.chkdependable.setHorizontalAlignment(2);		
        this.chkdependable.setEnabled(false);
        // conresPerson		
        this.conresPerson.setBoundLabelText(resHelper.getString("conresPerson.boundLabelText"));		
        this.conresPerson.setBoundLabelLength(100);		
        this.conresPerson.setBoundLabelUnderline(true);		
        this.conresPerson.setVisible(false);		
        this.conresPerson.setEnabled(false);
        // contportPeriod		
        this.contportPeriod.setBoundLabelText(resHelper.getString("contportPeriod.boundLabelText"));		
        this.contportPeriod.setBoundLabelLength(60);		
        this.contportPeriod.setBoundLabelUnderline(true);		
        this.contportPeriod.setVisible(false);		
        this.contportPeriod.setEnabled(false);
        // continstallCost		
        this.continstallCost.setBoundLabelText(resHelper.getString("continstallCost.boundLabelText"));		
        this.continstallCost.setBoundLabelLength(100);		
        this.continstallCost.setBoundLabelUnderline(true);		
        this.continstallCost.setVisible(false);
        // conjhOrgUnit		
        this.conjhOrgUnit.setBoundLabelText(resHelper.getString("conjhOrgUnit.boundLabelText"));		
        this.conjhOrgUnit.setBoundLabelLength(100);		
        this.conjhOrgUnit.setBoundLabelUnderline(true);		
        this.conjhOrgUnit.setVisible(true);
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
        // kDPanel2
        // kDPanel8		
        this.kDPanel8.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel8.border.title")));
        // kDPanel10		
        this.kDPanel10.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel10.border.title")));
        // kDPanel11		
        this.kDPanel11.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel11.border.title")));
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // contEqmCategory		
        this.contEqmCategory.setBoundLabelText(resHelper.getString("contEqmCategory.boundLabelText"));		
        this.contEqmCategory.setBoundLabelLength(100);		
        this.contEqmCategory.setBoundLabelUnderline(true);		
        this.contEqmCategory.setVisible(false);		
        this.contEqmCategory.setEnabled(false);
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
        // chkisMainEqm		
        this.chkisMainEqm.setText(resHelper.getString("chkisMainEqm.text"));		
        this.chkisMainEqm.setVisible(true);		
        this.chkisMainEqm.setHorizontalAlignment(2);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // conparent		
        this.conparent.setBoundLabelText(resHelper.getString("conparent.boundLabelText"));		
        this.conparent.setBoundLabelLength(100);		
        this.conparent.setBoundLabelUnderline(true);		
        this.conparent.setVisible(true);
        // chkisbaoxian		
        this.chkisbaoxian.setText(resHelper.getString("chkisbaoxian.text"));		
        this.chkisbaoxian.setVisible(true);		
        this.chkisbaoxian.setHorizontalAlignment(2);
        // chkisccCheck		
        this.chkisccCheck.setText(resHelper.getString("chkisccCheck.text"));		
        this.chkisccCheck.setVisible(true);		
        this.chkisccCheck.setHorizontalAlignment(2);
        // conusingDept		
        this.conusingDept.setBoundLabelText(resHelper.getString("conusingDept.boundLabelText"));		
        this.conusingDept.setBoundLabelLength(100);		
        this.conusingDept.setBoundLabelUnderline(true);		
        this.conusingDept.setVisible(true);
        // conlocation		
        this.conlocation.setBoundLabelText(resHelper.getString("conlocation.boundLabelText"));		
        this.conlocation.setBoundLabelLength(100);		
        this.conlocation.setBoundLabelUnderline(true);		
        this.conlocation.setVisible(true);
        // conaddress		
        this.conaddress.setBoundLabelText(resHelper.getString("conaddress.boundLabelText"));		
        this.conaddress.setBoundLabelLength(100);		
        this.conaddress.setBoundLabelUnderline(true);		
        this.conaddress.setVisible(true);
        // txtsbDescription		
        this.txtsbDescription.setVisible(true);		
        this.txtsbDescription.setRequired(false);		
        this.txtsbDescription.setMaxLength(255);
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
        this.prmtssOrgUnit.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtssOrgUnit_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
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
        // txtNumber		
        this.txtNumber.setRequired(true);
        // prmtparent		
        this.prmtparent.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");		
        this.prmtparent.setVisible(true);		
        this.prmtparent.setEditable(true);		
        this.prmtparent.setDisplayFormat("$name$");		
        this.prmtparent.setEditFormat("$number$");		
        this.prmtparent.setCommitFormat("$number$");		
        this.prmtparent.setRequired(false);
        // prmtusingDept		
        this.prmtusingDept.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtusingDept.setVisible(true);		
        this.prmtusingDept.setEditable(true);		
        this.prmtusingDept.setDisplayFormat("$name$");		
        this.prmtusingDept.setEditFormat("$number$");		
        this.prmtusingDept.setCommitFormat("$number$");		
        this.prmtusingDept.setRequired(false);
        // txtlocation		
        this.txtlocation.setVisible(true);		
        this.txtlocation.setHorizontalAlignment(2);		
        this.txtlocation.setMaxLength(100);		
        this.txtlocation.setRequired(false);
        // prmtaddress		
        this.prmtaddress.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7AddressQuery");		
        this.prmtaddress.setVisible(true);		
        this.prmtaddress.setEditable(true);		
        this.prmtaddress.setDisplayFormat("$detailAddress$");		
        this.prmtaddress.setEditFormat("$number$");		
        this.prmtaddress.setCommitFormat("$number$");		
        this.prmtaddress.setRequired(false);
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
        // concheckDate		
        this.concheckDate.setBoundLabelText(resHelper.getString("concheckDate.boundLabelText"));		
        this.concheckDate.setBoundLabelLength(100);		
        this.concheckDate.setBoundLabelUnderline(true);		
        this.concheckDate.setVisible(true);
        // consourceUnit		
        this.consourceUnit.setBoundLabelText(resHelper.getString("consourceUnit.boundLabelText"));		
        this.consourceUnit.setBoundLabelLength(100);		
        this.consourceUnit.setBoundLabelUnderline(true);		
        this.consourceUnit.setVisible(true);
        // contdeadline		
        this.contdeadline.setBoundLabelText(resHelper.getString("contdeadline.boundLabelText"));		
        this.contdeadline.setBoundLabelLength(100);		
        this.contdeadline.setBoundLabelUnderline(true);		
        this.contdeadline.setVisible(true);
        // contdebuger		
        this.contdebuger.setBoundLabelText(resHelper.getString("contdebuger.boundLabelText"));		
        this.contdebuger.setBoundLabelLength(100);		
        this.contdebuger.setBoundLabelUnderline(true);		
        this.contdebuger.setVisible(true);
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
        // pkcheckDate		
        this.pkcheckDate.setVisible(true);		
        this.pkcheckDate.setRequired(false);
        // txtsourceUnit		
        this.txtsourceUnit.setVisible(true);		
        this.txtsourceUnit.setHorizontalAlignment(2);		
        this.txtsourceUnit.setMaxLength(100);		
        this.txtsourceUnit.setRequired(false);
        // txtdeadline		
        this.txtdeadline.setVisible(true);		
        this.txtdeadline.setHorizontalAlignment(2);		
        this.txtdeadline.setMaxLength(100);		
        this.txtdeadline.setRequired(false);
        // txtdebuger		
        this.txtdebuger.setVisible(true);		
        this.txtdebuger.setHorizontalAlignment(2);		
        this.txtdebuger.setMaxLength(255);		
        this.txtdebuger.setRequired(false);
        // chkportTest		
        this.chkportTest.setText(resHelper.getString("chkportTest.text"));		
        this.chkportTest.setVisible(true);		
        this.chkportTest.setHorizontalAlignment(2);
        // chkcityTest		
        this.chkcityTest.setText(resHelper.getString("chkcityTest.text"));		
        this.chkcityTest.setVisible(true);		
        this.chkcityTest.setHorizontalAlignment(2);
        // conttzdaNumber		
        this.conttzdaNumber.setBoundLabelText(resHelper.getString("conttzdaNumber.boundLabelText"));		
        this.conttzdaNumber.setBoundLabelLength(80);		
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
        this.contcityPeriod.setBoundLabelLength(30);		
        this.contcityPeriod.setBoundLabelUnderline(true);		
        this.contcityPeriod.setVisible(true);
        // contcode		
        this.contcode.setBoundLabelText(resHelper.getString("contcode.boundLabelText"));		
        this.contcode.setBoundLabelLength(70);		
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
        // conttextDate1		
        this.conttextDate1.setBoundLabelText(resHelper.getString("conttextDate1.boundLabelText"));		
        this.conttextDate1.setBoundLabelLength(75);		
        this.conttextDate1.setBoundLabelUnderline(true);		
        this.conttextDate1.setVisible(true);
        // contzzsShortName		
        this.contzzsShortName.setBoundLabelText(resHelper.getString("contzzsShortName.boundLabelText"));		
        this.contzzsShortName.setBoundLabelLength(80);		
        this.contzzsShortName.setBoundLabelUnderline(true);		
        this.contzzsShortName.setVisible(true);
        // conttelePhoneNumber		
        this.conttelePhoneNumber.setBoundLabelText(resHelper.getString("conttelePhoneNumber.boundLabelText"));		
        this.conttelePhoneNumber.setBoundLabelLength(70);		
        this.conttelePhoneNumber.setBoundLabelUnderline(true);		
        this.conttelePhoneNumber.setVisible(true);
        // contactrueTime		
        this.contactrueTime.setBoundLabelText(resHelper.getString("contactrueTime.boundLabelText"));		
        this.contactrueTime.setBoundLabelLength(75);		
        this.contactrueTime.setBoundLabelUnderline(true);		
        this.contactrueTime.setVisible(true);
        // contresponsible		
        this.contresponsible.setBoundLabelText(resHelper.getString("contresponsible.boundLabelText"));		
        this.contresponsible.setBoundLabelLength(80);		
        this.contresponsible.setBoundLabelUnderline(true);		
        this.contresponsible.setVisible(true);
        // continStreet		
        this.continStreet.setBoundLabelText(resHelper.getString("continStreet.boundLabelText"));		
        this.continStreet.setBoundLabelLength(105);		
        this.continStreet.setBoundLabelUnderline(true);		
        this.continStreet.setVisible(true);
        // conttextType		
        this.conttextType.setBoundLabelText(resHelper.getString("conttextType.boundLabelText"));		
        this.conttextType.setBoundLabelLength(80);		
        this.conttextType.setBoundLabelUnderline(true);		
        this.conttextType.setVisible(true);
        // contequTypeone		
        this.contequTypeone.setBoundLabelText(resHelper.getString("contequTypeone.boundLabelText"));		
        this.contequTypeone.setBoundLabelLength(100);		
        this.contequTypeone.setBoundLabelUnderline(true);		
        this.contequTypeone.setVisible(true);
        // contcpgjzs		
        this.contcpgjzs.setBoundLabelText(resHelper.getString("contcpgjzs.boundLabelText"));		
        this.contcpgjzs.setBoundLabelLength(100);		
        this.contcpgjzs.setBoundLabelUnderline(true);		
        this.contcpgjzs.setVisible(true);
        // contcpsyqh		
        this.contcpsyqh.setBoundLabelText(resHelper.getString("contcpsyqh.boundLabelText"));		
        this.contcpsyqh.setBoundLabelLength(100);		
        this.contcpsyqh.setBoundLabelUnderline(true);		
        this.contcpsyqh.setVisible(true);
        // contcpsbh		
        this.contcpsbh.setBoundLabelText(resHelper.getString("contcpsbh.boundLabelText"));		
        this.contcpsbh.setBoundLabelLength(100);		
        this.contcpsbh.setBoundLabelUnderline(true);		
        this.contcpsbh.setVisible(true);
        // chkchuanCheck		
        this.chkchuanCheck.setText(resHelper.getString("chkchuanCheck.text"));		
        this.chkchuanCheck.setVisible(true);		
        this.chkchuanCheck.setHorizontalAlignment(2);
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
        this.txtcityPeriod.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtcityPeriod_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
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
        // pktextDate1		
        this.pktextDate1.setVisible(true);		
        this.pktextDate1.setRequired(false);
        // txtzzsShortName		
        this.txtzzsShortName.setVisible(true);		
        this.txtzzsShortName.setHorizontalAlignment(2);		
        this.txtzzsShortName.setMaxLength(100);		
        this.txtzzsShortName.setRequired(false);
        // txttelePhoneNumber		
        this.txttelePhoneNumber.setVisible(true);		
        this.txttelePhoneNumber.setHorizontalAlignment(2);		
        this.txttelePhoneNumber.setMaxLength(100);		
        this.txttelePhoneNumber.setRequired(false);
        // pkactrueTime		
        this.pkactrueTime.setVisible(true);		
        this.pkactrueTime.setRequired(false);
        // txtresponsible		
        this.txtresponsible.setVisible(true);		
        this.txtresponsible.setHorizontalAlignment(2);		
        this.txtresponsible.setMaxLength(100);		
        this.txtresponsible.setRequired(false);
        // txtinStreet		
        this.txtinStreet.setVisible(true);		
        this.txtinStreet.setHorizontalAlignment(2);		
        this.txtinStreet.setMaxLength(100);		
        this.txtinStreet.setRequired(false);
        // prmttextType		
        this.prmttextType.setQueryInfo("com.kingdee.eas.port.equipment.base.app.TestTypeQuery");		
        this.prmttextType.setVisible(true);		
        this.prmttextType.setEditable(true);		
        this.prmttextType.setDisplayFormat("$name$");		
        this.prmttextType.setEditFormat("$number$");		
        this.prmttextType.setCommitFormat("$number$");		
        this.prmttextType.setRequired(false);
        // prmtequTypeone		
        this.prmtequTypeone.setQueryInfo("com.kingdee.eas.port.equipment.base.app.EquTypeQuery");		
        this.prmtequTypeone.setVisible(true);		
        this.prmtequTypeone.setEditable(true);		
        this.prmtequTypeone.setDisplayFormat("$name$");		
        this.prmtequTypeone.setEditFormat("$number$");		
        this.prmtequTypeone.setCommitFormat("$number$");		
        this.prmtequTypeone.setRequired(false);
        // txtcpgjzs		
        this.txtcpgjzs.setVisible(true);		
        this.txtcpgjzs.setHorizontalAlignment(2);		
        this.txtcpgjzs.setMaxLength(100);		
        this.txtcpgjzs.setRequired(false);
        // txtcpsyqh		
        this.txtcpsyqh.setVisible(true);		
        this.txtcpsyqh.setHorizontalAlignment(2);		
        this.txtcpsyqh.setMaxLength(100);		
        this.txtcpsyqh.setRequired(false);
        // txtcpsbh		
        this.txtcpsbh.setVisible(true);		
        this.txtcpsbh.setHorizontalAlignment(2);		
        this.txtcpsbh.setMaxLength(100);		
        this.txtcpsbh.setRequired(false);
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
        // contnowAmount		
        this.contnowAmount.setBoundLabelText(resHelper.getString("contnowAmount.boundLabelText"));		
        this.contnowAmount.setBoundLabelLength(100);		
        this.contnowAmount.setBoundLabelUnderline(true);		
        this.contnowAmount.setVisible(true);
        // contoldYear		
        this.contoldYear.setBoundLabelText(resHelper.getString("contoldYear.boundLabelText"));		
        this.contoldYear.setBoundLabelLength(100);		
        this.contoldYear.setBoundLabelUnderline(true);		
        this.contoldYear.setVisible(true);
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
        // txtnowAmount		
        this.txtnowAmount.setVisible(true);		
        this.txtnowAmount.setHorizontalAlignment(2);		
        this.txtnowAmount.setDataType(1);		
        this.txtnowAmount.setSupportedEmpty(true);		
        this.txtnowAmount.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtnowAmount.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtnowAmount.setPrecision(2);		
        this.txtnowAmount.setRequired(false);
        // txtoldYear		
        this.txtoldYear.setVisible(true);		
        this.txtoldYear.setHorizontalAlignment(2);		
        this.txtoldYear.setDataType(1);		
        this.txtoldYear.setSupportedEmpty(true);		
        this.txtoldYear.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtoldYear.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtoldYear.setPrecision(4);		
        this.txtoldYear.setRequired(false);
        // txtEqmCategory		
        this.txtEqmCategory.setVisible(true);		
        this.txtEqmCategory.setHorizontalAlignment(2);		
        this.txtEqmCategory.setMaxLength(80);		
        this.txtEqmCategory.setRequired(false);		
        this.txtEqmCategory.setEnabled(false);
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
		String kdtSpareInfoStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"materialName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"speModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shuliangone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"useyong\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fachangjia\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"noteone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"attachone\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{materialName}</t:Cell><t:Cell>$Resource{speModel}</t:Cell><t:Cell>$Resource{shuliangone}</t:Cell><t:Cell>$Resource{useyong}</t:Cell><t:Cell>$Resource{fachangjia}</t:Cell><t:Cell>$Resource{noteone}</t:Cell><t:Cell>$Resource{attachone}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtSpareInfo.setFormatXml(resHelper.translateString("kdtSpareInfo",kdtSpareInfoStrXML));

                this.kdtSpareInfo.putBindContents("editData",new String[] {"seq","materialName","speModel","shuliangone","useyong","fachangjia","noteone","attachone"});


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
        KDFormattedTextField kdtSpareInfo_shuliangone_TextField = new KDFormattedTextField();
        kdtSpareInfo_shuliangone_TextField.setName("kdtSpareInfo_shuliangone_TextField");
        kdtSpareInfo_shuliangone_TextField.setVisible(true);
        kdtSpareInfo_shuliangone_TextField.setEditable(true);
        kdtSpareInfo_shuliangone_TextField.setHorizontalAlignment(2);
        kdtSpareInfo_shuliangone_TextField.setDataType(0);
        KDTDefaultCellEditor kdtSpareInfo_shuliangone_CellEditor = new KDTDefaultCellEditor(kdtSpareInfo_shuliangone_TextField);
        this.kdtSpareInfo.getColumn("shuliangone").setEditor(kdtSpareInfo_shuliangone_CellEditor);
        KDTextField kdtSpareInfo_useyong_TextField = new KDTextField();
        kdtSpareInfo_useyong_TextField.setName("kdtSpareInfo_useyong_TextField");
        kdtSpareInfo_useyong_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtSpareInfo_useyong_CellEditor = new KDTDefaultCellEditor(kdtSpareInfo_useyong_TextField);
        this.kdtSpareInfo.getColumn("useyong").setEditor(kdtSpareInfo_useyong_CellEditor);
        KDTextField kdtSpareInfo_fachangjia_TextField = new KDTextField();
        kdtSpareInfo_fachangjia_TextField.setName("kdtSpareInfo_fachangjia_TextField");
        kdtSpareInfo_fachangjia_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtSpareInfo_fachangjia_CellEditor = new KDTDefaultCellEditor(kdtSpareInfo_fachangjia_TextField);
        this.kdtSpareInfo.getColumn("fachangjia").setEditor(kdtSpareInfo_fachangjia_CellEditor);
        KDTextArea kdtSpareInfo_noteone_TextArea = new KDTextArea();
        kdtSpareInfo_noteone_TextArea.setName("kdtSpareInfo_noteone_TextArea");
        kdtSpareInfo_noteone_TextArea.setMaxLength(500);
        KDTDefaultCellEditor kdtSpareInfo_noteone_CellEditor = new KDTDefaultCellEditor(kdtSpareInfo_noteone_TextArea);
        this.kdtSpareInfo.getColumn("noteone").setEditor(kdtSpareInfo_noteone_CellEditor);
        KDTextField kdtSpareInfo_attachone_TextField = new KDTextField();
        kdtSpareInfo_attachone_TextField.setName("kdtSpareInfo_attachone_TextField");
        kdtSpareInfo_attachone_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtSpareInfo_attachone_CellEditor = new KDTDefaultCellEditor(kdtSpareInfo_attachone_TextField);
        this.kdtSpareInfo.getColumn("attachone").setEditor(kdtSpareInfo_attachone_CellEditor);
        // kdtE3
		String kdtE3StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"csmingcheng\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"csmodel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shuliang\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"power\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"speed\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"chuandong\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"zidong\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"madeFac\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"noteoo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{csmingcheng}</t:Cell><t:Cell>$Resource{csmodel}</t:Cell><t:Cell>$Resource{shuliang}</t:Cell><t:Cell>$Resource{power}</t:Cell><t:Cell>$Resource{speed}</t:Cell><t:Cell>$Resource{chuandong}</t:Cell><t:Cell>$Resource{zidong}</t:Cell><t:Cell>$Resource{madeFac}</t:Cell><t:Cell>$Resource{noteoo}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE3.setFormatXml(resHelper.translateString("kdtE3",kdtE3StrXML));

                this.kdtE3.putBindContents("editData",new String[] {"seq","csmingcheng","csmodel","shuliang","power","speed","chuandong","zidong","madeFac","noteoo"});


        this.kdtE3.checkParsed();
        KDTextField kdtE3_csmingcheng_TextField = new KDTextField();
        kdtE3_csmingcheng_TextField.setName("kdtE3_csmingcheng_TextField");
        kdtE3_csmingcheng_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE3_csmingcheng_CellEditor = new KDTDefaultCellEditor(kdtE3_csmingcheng_TextField);
        this.kdtE3.getColumn("csmingcheng").setEditor(kdtE3_csmingcheng_CellEditor);
        KDTextField kdtE3_csmodel_TextField = new KDTextField();
        kdtE3_csmodel_TextField.setName("kdtE3_csmodel_TextField");
        kdtE3_csmodel_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE3_csmodel_CellEditor = new KDTDefaultCellEditor(kdtE3_csmodel_TextField);
        this.kdtE3.getColumn("csmodel").setEditor(kdtE3_csmodel_CellEditor);
        KDFormattedTextField kdtE3_shuliang_TextField = new KDFormattedTextField();
        kdtE3_shuliang_TextField.setName("kdtE3_shuliang_TextField");
        kdtE3_shuliang_TextField.setVisible(true);
        kdtE3_shuliang_TextField.setEditable(true);
        kdtE3_shuliang_TextField.setHorizontalAlignment(2);
        kdtE3_shuliang_TextField.setDataType(0);
        KDTDefaultCellEditor kdtE3_shuliang_CellEditor = new KDTDefaultCellEditor(kdtE3_shuliang_TextField);
        this.kdtE3.getColumn("shuliang").setEditor(kdtE3_shuliang_CellEditor);
        KDTextField kdtE3_power_TextField = new KDTextField();
        kdtE3_power_TextField.setName("kdtE3_power_TextField");
        kdtE3_power_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE3_power_CellEditor = new KDTDefaultCellEditor(kdtE3_power_TextField);
        this.kdtE3.getColumn("power").setEditor(kdtE3_power_CellEditor);
        KDTextField kdtE3_speed_TextField = new KDTextField();
        kdtE3_speed_TextField.setName("kdtE3_speed_TextField");
        kdtE3_speed_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE3_speed_CellEditor = new KDTDefaultCellEditor(kdtE3_speed_TextField);
        this.kdtE3.getColumn("speed").setEditor(kdtE3_speed_CellEditor);
        KDTextField kdtE3_chuandong_TextField = new KDTextField();
        kdtE3_chuandong_TextField.setName("kdtE3_chuandong_TextField");
        kdtE3_chuandong_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE3_chuandong_CellEditor = new KDTDefaultCellEditor(kdtE3_chuandong_TextField);
        this.kdtE3.getColumn("chuandong").setEditor(kdtE3_chuandong_CellEditor);
        KDTextField kdtE3_zidong_TextField = new KDTextField();
        kdtE3_zidong_TextField.setName("kdtE3_zidong_TextField");
        kdtE3_zidong_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE3_zidong_CellEditor = new KDTDefaultCellEditor(kdtE3_zidong_TextField);
        this.kdtE3.getColumn("zidong").setEditor(kdtE3_zidong_CellEditor);
        KDTextField kdtE3_madeFac_TextField = new KDTextField();
        kdtE3_madeFac_TextField.setName("kdtE3_madeFac_TextField");
        kdtE3_madeFac_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE3_madeFac_CellEditor = new KDTDefaultCellEditor(kdtE3_madeFac_TextField);
        this.kdtE3.getColumn("madeFac").setEditor(kdtE3_madeFac_CellEditor);
        KDTextArea kdtE3_noteoo_TextArea = new KDTextArea();
        kdtE3_noteoo_TextArea.setName("kdtE3_noteoo_TextArea");
        kdtE3_noteoo_TextArea.setMaxLength(500);
        KDTDefaultCellEditor kdtE3_noteoo_CellEditor = new KDTDefaultCellEditor(kdtE3_noteoo_TextArea);
        this.kdtE3.getColumn("noteoo").setEditor(kdtE3_noteoo_CellEditor);
        // pkdaytow		
        this.pkdaytow.setRequired(false);		
        this.pkdaytow.setEnabled(false);
        // pkdayone		
        this.pkdayone.setRequired(false);		
        this.pkdayone.setEnabled(false);
        // testDay
        // prmtresPerson		
        this.prmtresPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtresPerson.setVisible(true);		
        this.prmtresPerson.setEditable(true);		
        this.prmtresPerson.setDisplayFormat("$name$");		
        this.prmtresPerson.setEditFormat("$number$");		
        this.prmtresPerson.setCommitFormat("$number$");		
        this.prmtresPerson.setRequired(false);
        // txtportPeriod		
        this.txtportPeriod.setVisible(true);		
        this.txtportPeriod.setHorizontalAlignment(2);		
        this.txtportPeriod.setDataType(1);		
        this.txtportPeriod.setSupportedEmpty(true);		
        this.txtportPeriod.setMinimumValue( new java.math.BigDecimal("-1.0E27"));		
        this.txtportPeriod.setMaximumValue( new java.math.BigDecimal("1.0E27"));		
        this.txtportPeriod.setPrecision(1);		
        this.txtportPeriod.setRequired(false);
        this.txtportPeriod.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtportPeriod_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtinstallCost		
        this.txtinstallCost.setVisible(true);		
        this.txtinstallCost.setHorizontalAlignment(2);		
        this.txtinstallCost.setDataType(1);		
        this.txtinstallCost.setSupportedEmpty(true);		
        this.txtinstallCost.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtinstallCost.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtinstallCost.setPrecision(2);		
        this.txtinstallCost.setRequired(false);
        // prmtjhOrgUnit		
        this.prmtjhOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtjhOrgUnit.setVisible(true);		
        this.prmtjhOrgUnit.setEditable(true);		
        this.prmtjhOrgUnit.setDisplayFormat("$name$");		
        this.prmtjhOrgUnit.setEditFormat("$number$");		
        this.prmtjhOrgUnit.setCommitFormat("$number$");		
        this.prmtjhOrgUnit.setRequired(true);
        // btnRegistChange
        this.btnRegistChange.setAction((IItemAction)ActionProxyFactory.getProxy(actionRegistChange, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRegistChange.setText(resHelper.getString("btnRegistChange.text"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtCU,pkLastUpdateTime,prmtLastUpdateUser,pkCreateTime,prmtCreator,prmtAuditor,txtDescription,pkBizDate,pkAuditTime,comboBizStatus,comboStatus,kdtTechnologyPar,combosbStatus,txtinnerNumber,combonowStatus,txtzzsShortName,chkcityTest,chkportTest,txttzdaNumber,tzsbStatus,prmtasset,prmtassetStatus,txtassetValue,txtinstallCost,chkisMainEqm,txtEqmCategory,pktextDate1,pkdayone,pkdaytow,txttelePhoneNumber,pkactrueTime,txtresponsible,txtinStreet,prmttextType,prmtequTypeone,chkisbaoxian,txtnowAmount,txtoldYear,txtdeadline,txtdebuger,chkisccCheck,txtcpgjzs,txtcpsyqh,txtcpsbh,chkchuanCheck}));
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        contCreator.setBounds(new Rectangle(25, 596, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(25, 596, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(25, 572, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(25, 572, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(368, 596, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(368, 596, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(368, 572, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(368, 572, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(48, 841, 53, 25));
        this.add(contCU, new KDLayout.Constraints(48, 841, 53, 25, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(378, 878, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(378, 878, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(47, 878, 50, 23));
        this.add(contDescription, new KDLayout.Constraints(47, 878, 50, 23, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(713, 596, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(713, 596, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(378, 830, 51, 19));
        this.add(contStatus, new KDLayout.Constraints(378, 830, 51, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(709, 830, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(709, 830, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(713, 572, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(713, 572, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane1.setBounds(new Rectangle(8, 4, 993, 561));
        this.add(kDTabbedPane1, new KDLayout.Constraints(8, 4, 993, 561, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contdaytow.setBounds(new Rectangle(964, 610, 270, 19));
        this.add(contdaytow, new KDLayout.Constraints(964, 610, 270, 19, 0));
        contdayone.setBounds(new Rectangle(988, 602, 270, 19));
        this.add(contdayone, new KDLayout.Constraints(988, 602, 270, 19, 0));
        conttestDay.setBounds(new Rectangle(987, 531, 270, 19));
        this.add(conttestDay, new KDLayout.Constraints(987, 531, 270, 19, 0));
        chkdependable.setBounds(new Rectangle(932, 613, 82, 19));
        this.add(chkdependable, new KDLayout.Constraints(932, 613, 82, 19, 0));
        conresPerson.setBounds(new Rectangle(995, 586, 270, 19));
        this.add(conresPerson, new KDLayout.Constraints(995, 586, 270, 19, 0));
        contportPeriod.setBounds(new Rectangle(976, 594, 130, 19));
        this.add(contportPeriod, new KDLayout.Constraints(976, 594, 130, 19, 0));
        continstallCost.setBounds(new Rectangle(972, 522, 89, 19));
        this.add(continstallCost, new KDLayout.Constraints(972, 522, 89, 19, 0));
        conjhOrgUnit.setBounds(new Rectangle(939, 165, 270, 19));
        this.add(conjhOrgUnit, new KDLayout.Constraints(939, 165, 270, 19, 0));
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
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 992, 528));        kDPanel8.setBounds(new Rectangle(2, 3, 983, 166));
        kDPanel1.add(kDPanel8, new KDLayout.Constraints(2, 3, 983, 166, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel10.setBounds(new Rectangle(2, 176, 983, 111));
        kDPanel1.add(kDPanel10, new KDLayout.Constraints(2, 176, 983, 111, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel11.setBounds(new Rectangle(2, 292, 983, 155));
        kDPanel1.add(kDPanel11, new KDLayout.Constraints(2, 292, 983, 155, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel3.setBounds(new Rectangle(2, 451, 983, 75));
        kDPanel1.add(kDPanel3, new KDLayout.Constraints(2, 451, 983, 75, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contEqmCategory.setBounds(new Rectangle(966, 319, 270, 19));
        kDPanel1.add(contEqmCategory, new KDLayout.Constraints(966, 319, 270, 19, 0));
        //kDPanel8
        kDPanel8.setLayout(new KDLayout());
        kDPanel8.putClientProperty("OriginalBounds", new Rectangle(2, 3, 983, 166));        consbDescription.setBounds(new Rectangle(12, 132, 614, 19));
        kDPanel8.add(consbDescription, new KDLayout.Constraints(12, 132, 614, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conname.setBounds(new Rectangle(355, 12, 270, 19));
        kDPanel8.add(conname, new KDLayout.Constraints(355, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conssOrgUnit.setBounds(new Rectangle(699, 52, 270, 19));
        kDPanel8.add(conssOrgUnit, new KDLayout.Constraints(699, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conwxOrgUnit.setBounds(new Rectangle(931, 151, 270, 19));
        kDPanel8.add(conwxOrgUnit, new KDLayout.Constraints(931, 151, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conmodel.setBounds(new Rectangle(355, 32, 269, 19));
        kDPanel8.add(conmodel, new KDLayout.Constraints(355, 32, 269, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        consize.setBounds(new Rectangle(945, 116, 270, 19));
        kDPanel8.add(consize, new KDLayout.Constraints(945, 116, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conweight.setBounds(new Rectangle(11, 52, 270, 19));
        kDPanel8.add(conweight, new KDLayout.Constraints(11, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conwxDept.setBounds(new Rectangle(699, 32, 270, 19));
        kDPanel8.add(conwxDept, new KDLayout.Constraints(699, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conqyDate.setBounds(new Rectangle(11, 72, 270, 19));
        kDPanel8.add(conqyDate, new KDLayout.Constraints(11, 72, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conserialNumber.setBounds(new Rectangle(11, 92, 270, 19));
        kDPanel8.add(conserialNumber, new KDLayout.Constraints(11, 92, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        consbStatus.setBounds(new Rectangle(355, 52, 270, 19));
        kDPanel8.add(consbStatus, new KDLayout.Constraints(355, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conunit.setBounds(new Rectangle(355, 72, 270, 19));
        kDPanel8.add(conunit, new KDLayout.Constraints(355, 72, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkspecial.setBounds(new Rectangle(699, 112, 90, 19));
        kDPanel8.add(chkspecial, new KDLayout.Constraints(699, 112, 90, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contype.setBounds(new Rectangle(11, 32, 270, 19));
        kDPanel8.add(contype, new KDLayout.Constraints(11, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continnerNumber.setBounds(new Rectangle(699, 12, 270, 19));
        kDPanel8.add(continnerNumber, new KDLayout.Constraints(699, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contnowStatus.setBounds(new Rectangle(699, 92, 270, 19));
        kDPanel8.add(contnowStatus, new KDLayout.Constraints(699, 92, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkisMainEqm.setBounds(new Rectangle(792, 112, 104, 19));
        kDPanel8.add(chkisMainEqm, new KDLayout.Constraints(792, 112, 104, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(11, 12, 270, 19));
        kDPanel8.add(contNumber, new KDLayout.Constraints(11, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conparent.setBounds(new Rectangle(355, 92, 270, 19));
        kDPanel8.add(conparent, new KDLayout.Constraints(355, 92, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkisbaoxian.setBounds(new Rectangle(699, 132, 117, 19));
        kDPanel8.add(chkisbaoxian, new KDLayout.Constraints(699, 132, 117, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkisccCheck.setBounds(new Rectangle(828, 132, 127, 19));
        kDPanel8.add(chkisccCheck, new KDLayout.Constraints(828, 132, 127, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conusingDept.setBounds(new Rectangle(11, 112, 269, 19));
        kDPanel8.add(conusingDept, new KDLayout.Constraints(11, 112, 269, 19, 0));
        conlocation.setBounds(new Rectangle(355, 112, 270, 19));
        kDPanel8.add(conlocation, new KDLayout.Constraints(355, 112, 270, 19, 0));
        conaddress.setBounds(new Rectangle(699, 72, 270, 19));
        kDPanel8.add(conaddress, new KDLayout.Constraints(699, 72, 270, 19, 0));
        //consbDescription
        consbDescription.setBoundEditor(txtsbDescription);
        //conname
        conname.setBoundEditor(txtname);
        //conssOrgUnit
        conssOrgUnit.setBoundEditor(prmtssOrgUnit);
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
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //conparent
        conparent.setBoundEditor(prmtparent);
        //conusingDept
        conusingDept.setBoundEditor(prmtusingDept);
        //conlocation
        conlocation.setBoundEditor(txtlocation);
        //conaddress
        conaddress.setBoundEditor(prmtaddress);
        //kDPanel10
        kDPanel10.setLayout(new KDLayout());
        kDPanel10.putClientProperty("OriginalBounds", new Rectangle(2, 176, 983, 111));        conmader.setBounds(new Rectangle(11, 13, 270, 19));
        kDPanel10.add(conmader, new KDLayout.Constraints(11, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conmadedCountry.setBounds(new Rectangle(355, 13, 270, 19));
        kDPanel10.add(conmadedCountry, new KDLayout.Constraints(355, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conmadeDate.setBounds(new Rectangle(699, 13, 270, 19));
        kDPanel10.add(conmadeDate, new KDLayout.Constraints(699, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        consupplier.setBounds(new Rectangle(11, 33, 270, 19));
        kDPanel10.add(consupplier, new KDLayout.Constraints(11, 33, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conreachedDate.setBounds(new Rectangle(355, 33, 270, 19));
        kDPanel10.add(conreachedDate, new KDLayout.Constraints(355, 33, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conccNumber.setBounds(new Rectangle(699, 33, 270, 19));
        kDPanel10.add(conccNumber, new KDLayout.Constraints(699, 33, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        coninstaller.setBounds(new Rectangle(11, 53, 270, 19));
        kDPanel10.add(coninstaller, new KDLayout.Constraints(11, 53, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        concheckDate.setBounds(new Rectangle(699, 53, 270, 19));
        kDPanel10.add(concheckDate, new KDLayout.Constraints(699, 53, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        consourceUnit.setBounds(new Rectangle(355, 53, 270, 19));
        kDPanel10.add(consourceUnit, new KDLayout.Constraints(355, 53, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contdeadline.setBounds(new Rectangle(11, 73, 270, 19));
        kDPanel10.add(contdeadline, new KDLayout.Constraints(11, 73, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contdebuger.setBounds(new Rectangle(355, 73, 614, 19));
        kDPanel10.add(contdebuger, new KDLayout.Constraints(355, 73, 614, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
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
        //concheckDate
        concheckDate.setBoundEditor(pkcheckDate);
        //consourceUnit
        consourceUnit.setBoundEditor(txtsourceUnit);
        //contdeadline
        contdeadline.setBoundEditor(txtdeadline);
        //contdebuger
        contdebuger.setBoundEditor(txtdebuger);
        //kDPanel11
        kDPanel11.setLayout(new KDLayout());
        kDPanel11.putClientProperty("OriginalBounds", new Rectangle(2, 292, 983, 155));        chkportTest.setBounds(new Rectangle(286, 39, 65, 19));
        kDPanel11.add(chkportTest, new KDLayout.Constraints(286, 39, 65, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkcityTest.setBounds(new Rectangle(286, 19, 67, 19));
        kDPanel11.add(chkcityTest, new KDLayout.Constraints(286, 19, 67, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttzdaNumber.setBounds(new Rectangle(11, 17, 269, 19));
        kDPanel11.add(conttzdaNumber, new KDLayout.Constraints(11, 17, 269, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttzsbStatus.setBounds(new Rectangle(700, 77, 268, 19));
        kDPanel11.add(conttzsbStatus, new KDLayout.Constraints(700, 77, 268, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contspecialType.setBounds(new Rectangle(355, 37, 270, 19));
        kDPanel11.add(contspecialType, new KDLayout.Constraints(355, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcityPeriod.setBounds(new Rectangle(11, 37, 73, 19));
        kDPanel11.add(contcityPeriod, new KDLayout.Constraints(11, 37, 73, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcode.setBounds(new Rectangle(355, 17, 269, 19));
        kDPanel11.add(contcode, new KDLayout.Constraints(355, 17, 269, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contengineNumber.setBounds(new Rectangle(700, 57, 267, 19));
        kDPanel11.add(contengineNumber, new KDLayout.Constraints(700, 57, 267, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contcarNumber.setBounds(new Rectangle(700, 37, 268, 19));
        kDPanel11.add(contcarNumber, new KDLayout.Constraints(700, 37, 268, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contratedWeight.setBounds(new Rectangle(700, 17, 268, 19));
        kDPanel11.add(contratedWeight, new KDLayout.Constraints(700, 17, 268, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conttextDate1.setBounds(new Rectangle(95, 37, 185, 19));
        kDPanel11.add(conttextDate1, new KDLayout.Constraints(95, 37, 185, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contzzsShortName.setBounds(new Rectangle(11, 57, 268, 19));
        kDPanel11.add(contzzsShortName, new KDLayout.Constraints(11, 57, 268, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttelePhoneNumber.setBounds(new Rectangle(355, 57, 270, 19));
        kDPanel11.add(conttelePhoneNumber, new KDLayout.Constraints(355, 57, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contactrueTime.setBounds(new Rectangle(355, 77, 271, 19));
        kDPanel11.add(contactrueTime, new KDLayout.Constraints(355, 77, 271, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contresponsible.setBounds(new Rectangle(11, 97, 270, 19));
        kDPanel11.add(contresponsible, new KDLayout.Constraints(11, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        continStreet.setBounds(new Rectangle(700, 97, 268, 19));
        kDPanel11.add(continStreet, new KDLayout.Constraints(700, 97, 268, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conttextType.setBounds(new Rectangle(11, 77, 270, 19));
        kDPanel11.add(conttextType, new KDLayout.Constraints(11, 77, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contequTypeone.setBounds(new Rectangle(355, 97, 270, 19));
        kDPanel11.add(contequTypeone, new KDLayout.Constraints(355, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcpgjzs.setBounds(new Rectangle(11, 117, 270, 19));
        kDPanel11.add(contcpgjzs, new KDLayout.Constraints(11, 117, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcpsyqh.setBounds(new Rectangle(355, 117, 270, 19));
        kDPanel11.add(contcpsyqh, new KDLayout.Constraints(355, 117, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcpsbh.setBounds(new Rectangle(700, 117, 270, 19));
        kDPanel11.add(contcpsbh, new KDLayout.Constraints(700, 117, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkchuanCheck.setBounds(new Rectangle(286, 117, 59, 19));
        kDPanel11.add(chkchuanCheck, new KDLayout.Constraints(286, 117, 59, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //conttzdaNumber
        conttzdaNumber.setBoundEditor(txttzdaNumber);
        //conttzsbStatus
        conttzsbStatus.setBoundEditor(tzsbStatus);
        //contspecialType
        contspecialType.setBoundEditor(prmtspecialType);
        //contcityPeriod
        contcityPeriod.setBoundEditor(txtcityPeriod);
        //contcode
        contcode.setBoundEditor(txtcode);
        //contengineNumber
        contengineNumber.setBoundEditor(txtengineNumber);
        //contcarNumber
        contcarNumber.setBoundEditor(txtcarNumber);
        //contratedWeight
        contratedWeight.setBoundEditor(txtratedWeight);
        //conttextDate1
        conttextDate1.setBoundEditor(pktextDate1);
        //contzzsShortName
        contzzsShortName.setBoundEditor(txtzzsShortName);
        //conttelePhoneNumber
        conttelePhoneNumber.setBoundEditor(txttelePhoneNumber);
        //contactrueTime
        contactrueTime.setBoundEditor(pkactrueTime);
        //contresponsible
        contresponsible.setBoundEditor(txtresponsible);
        //continStreet
        continStreet.setBoundEditor(txtinStreet);
        //conttextType
        conttextType.setBoundEditor(prmttextType);
        //contequTypeone
        contequTypeone.setBoundEditor(prmtequTypeone);
        //contcpgjzs
        contcpgjzs.setBoundEditor(txtcpgjzs);
        //contcpsyqh
        contcpsyqh.setBoundEditor(txtcpsyqh);
        //contcpsbh
        contcpsbh.setBoundEditor(txtcpsbh);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(2, 451, 983, 75));        contasset.setBounds(new Rectangle(11, 15, 270, 19));
        kDPanel3.add(contasset, new KDLayout.Constraints(11, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contassetStatus.setBounds(new Rectangle(699, 15, 270, 19));
        kDPanel3.add(contassetStatus, new KDLayout.Constraints(699, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contassetValue.setBounds(new Rectangle(355, 15, 270, 19));
        kDPanel3.add(contassetValue, new KDLayout.Constraints(355, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contnowAmount.setBounds(new Rectangle(11, 39, 270, 19));
        kDPanel3.add(contnowAmount, new KDLayout.Constraints(11, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contoldYear.setBounds(new Rectangle(355, 39, 270, 19));
        kDPanel3.add(contoldYear, new KDLayout.Constraints(355, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contasset
        contasset.setBoundEditor(prmtasset);
        //contassetStatus
        contassetStatus.setBoundEditor(prmtassetStatus);
        //contassetValue
        contassetValue.setBoundEditor(txtassetValue);
        //contnowAmount
        contnowAmount.setBoundEditor(txtnowAmount);
        //contoldYear
        contoldYear.setBoundEditor(txtoldYear);
        //contEqmCategory
        contEqmCategory.setBoundEditor(txtEqmCategory);
        //kDPanel6
kDPanel6.setLayout(new BorderLayout(0, 0));        kdtTechnologyPar_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtTechnologyPar,new com.kingdee.eas.port.equipment.record.EquIdTechnologyParInfo(),null,false);
        kDPanel6.add(kdtTechnologyPar_detailPanel, BorderLayout.CENTER);
        //kDPanel7
kDPanel7.setLayout(new BorderLayout(0, 0));        kdtSpareInfo_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtSpareInfo,new com.kingdee.eas.port.equipment.record.EquIdSpareInfoInfo(),null,false);
        kDPanel7.add(kdtSpareInfo_detailPanel, BorderLayout.CENTER);
        //kDPanel2
        kDPanel2.setLayout(null);        kdtE3.setBounds(new Rectangle(-1, 0, 988, 527));
        kdtE3_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE3,new com.kingdee.eas.port.equipment.record.EquIdE3Info(),null,false);
        kDPanel2.add(kdtE3_detailPanel, null);
        //contdaytow
        contdaytow.setBoundEditor(pkdaytow);
        //contdayone
        contdayone.setBoundEditor(pkdayone);
        //conttestDay
        conttestDay.setBoundEditor(testDay);
        //conresPerson
        conresPerson.setBoundEditor(prmtresPerson);
        //contportPeriod
        contportPeriod.setBoundEditor(txtportPeriod);
        //continstallCost
        continstallCost.setBoundEditor(txtinstallCost);
        //conjhOrgUnit
        conjhOrgUnit.setBoundEditor(prmtjhOrgUnit);

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
        this.toolBar.add(btnRegistChange);
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
		dataBinder.registerBinding("dependable", boolean.class, this.chkdependable, "selected");
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
		dataBinder.registerBinding("isbaoxian", boolean.class, this.chkisbaoxian, "selected");
		dataBinder.registerBinding("isccCheck", boolean.class, this.chkisccCheck, "selected");
		dataBinder.registerBinding("sbDescription", String.class, this.txtsbDescription, "text");
		dataBinder.registerBinding("name", String.class, this.txtname, "text");
		dataBinder.registerBinding("ssOrgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtssOrgUnit, "data");
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
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("parent", com.kingdee.eas.port.equipment.record.EquIdInfo.class, this.prmtparent, "data");
		dataBinder.registerBinding("usingDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtusingDept, "data");
		dataBinder.registerBinding("location", String.class, this.txtlocation, "text");
		dataBinder.registerBinding("address", com.kingdee.eas.basedata.assistant.AddressInfo.class, this.prmtaddress, "data");
		dataBinder.registerBinding("mader", String.class, this.txtmader, "text");
		dataBinder.registerBinding("madedCountry", com.kingdee.eas.basedata.assistant.CountryInfo.class, this.prmtmadedCountry, "data");
		dataBinder.registerBinding("madeDate", java.util.Date.class, this.pkmadeDate, "value");
		dataBinder.registerBinding("supplier", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtsupplier, "data");
		dataBinder.registerBinding("reachedDate", java.util.Date.class, this.pkreachedDate, "value");
		dataBinder.registerBinding("ccNumber", String.class, this.txtccNumber, "text");
		dataBinder.registerBinding("installer", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtinstaller, "data");
		dataBinder.registerBinding("checkDate", java.util.Date.class, this.pkcheckDate, "value");
		dataBinder.registerBinding("sourceUnit", String.class, this.txtsourceUnit, "text");
		dataBinder.registerBinding("deadline", String.class, this.txtdeadline, "text");
		dataBinder.registerBinding("debuger", String.class, this.txtdebuger, "text");
		dataBinder.registerBinding("portTest", boolean.class, this.chkportTest, "selected");
		dataBinder.registerBinding("cityTest", boolean.class, this.chkcityTest, "selected");
		dataBinder.registerBinding("chuanCheck", boolean.class, this.chkchuanCheck, "selected");
		dataBinder.registerBinding("tzdaNumber", String.class, this.txttzdaNumber, "text");
		dataBinder.registerBinding("tzsbStatus", com.kingdee.eas.port.equipment.base.enumbase.sbStatusType.class, this.tzsbStatus, "selectedItem");
		dataBinder.registerBinding("specialType", com.kingdee.eas.port.equipment.base.SpecialTypeInfo.class, this.prmtspecialType, "data");
		dataBinder.registerBinding("cityPeriod", java.math.BigDecimal.class, this.txtcityPeriod, "value");
		dataBinder.registerBinding("code", String.class, this.txtcode, "text");
		dataBinder.registerBinding("engineNumber", String.class, this.txtengineNumber, "text");
		dataBinder.registerBinding("carNumber", String.class, this.txtcarNumber, "text");
		dataBinder.registerBinding("ratedWeight", java.math.BigDecimal.class, this.txtratedWeight, "value");
		dataBinder.registerBinding("textDate1", java.util.Date.class, this.pktextDate1, "value");
		dataBinder.registerBinding("zzsShortName", String.class, this.txtzzsShortName, "text");
		dataBinder.registerBinding("telePhoneNumber", String.class, this.txttelePhoneNumber, "text");
		dataBinder.registerBinding("actrueTime", java.util.Date.class, this.pkactrueTime, "value");
		dataBinder.registerBinding("responsible", String.class, this.txtresponsible, "text");
		dataBinder.registerBinding("inStreet", String.class, this.txtinStreet, "text");
		dataBinder.registerBinding("textType", com.kingdee.eas.port.equipment.base.TestTypeInfo.class, this.prmttextType, "data");
		dataBinder.registerBinding("equTypeone", com.kingdee.eas.port.equipment.base.EquTypeInfo.class, this.prmtequTypeone, "data");
		dataBinder.registerBinding("cpgjzs", String.class, this.txtcpgjzs, "text");
		dataBinder.registerBinding("cpsyqh", String.class, this.txtcpsyqh, "text");
		dataBinder.registerBinding("cpsbh", String.class, this.txtcpsbh, "text");
		dataBinder.registerBinding("asset", com.kingdee.eas.fi.fa.manage.FaCurCardInfo.class, this.prmtasset, "data");
		dataBinder.registerBinding("assetStatus", com.kingdee.eas.fi.fa.basedata.FaUseStatusInfo.class, this.prmtassetStatus, "data");
		dataBinder.registerBinding("assetValue", java.math.BigDecimal.class, this.txtassetValue, "value");
		dataBinder.registerBinding("nowAmount", java.math.BigDecimal.class, this.txtnowAmount, "value");
		dataBinder.registerBinding("oldYear", java.math.BigDecimal.class, this.txtoldYear, "value");
		dataBinder.registerBinding("EqmCategory", String.class, this.txtEqmCategory, "text");
		dataBinder.registerBinding("TechnologyPar.seq", int.class, this.kdtTechnologyPar, "seq.text");
		dataBinder.registerBinding("TechnologyPar", com.kingdee.eas.port.equipment.record.EquIdTechnologyParInfo.class, this.kdtTechnologyPar, "userObject");
		dataBinder.registerBinding("TechnologyPar.parName", String.class, this.kdtTechnologyPar, "parName.text");
		dataBinder.registerBinding("TechnologyPar.parValue", String.class, this.kdtTechnologyPar, "parValue.text");
		dataBinder.registerBinding("TechnologyPar.parInfo", String.class, this.kdtTechnologyPar, "parInfo.text");
		dataBinder.registerBinding("SpareInfo.seq", int.class, this.kdtSpareInfo, "seq.text");
		dataBinder.registerBinding("SpareInfo", com.kingdee.eas.port.equipment.record.EquIdSpareInfoInfo.class, this.kdtSpareInfo, "userObject");
		dataBinder.registerBinding("SpareInfo.materialName", String.class, this.kdtSpareInfo, "materialName.text");
		dataBinder.registerBinding("SpareInfo.speModel", String.class, this.kdtSpareInfo, "speModel.text");
		dataBinder.registerBinding("SpareInfo.shuliangone", int.class, this.kdtSpareInfo, "shuliangone.text");
		dataBinder.registerBinding("SpareInfo.useyong", String.class, this.kdtSpareInfo, "useyong.text");
		dataBinder.registerBinding("SpareInfo.fachangjia", String.class, this.kdtSpareInfo, "fachangjia.text");
		dataBinder.registerBinding("SpareInfo.noteone", String.class, this.kdtSpareInfo, "noteone.text");
		dataBinder.registerBinding("SpareInfo.attachone", String.class, this.kdtSpareInfo, "attachone.text");
		dataBinder.registerBinding("E3.seq", int.class, this.kdtE3, "seq.text");
		dataBinder.registerBinding("E3", com.kingdee.eas.port.equipment.record.EquIdE3Info.class, this.kdtE3, "userObject");
		dataBinder.registerBinding("E3.csmingcheng", String.class, this.kdtE3, "csmingcheng.text");
		dataBinder.registerBinding("E3.csmodel", String.class, this.kdtE3, "csmodel.text");
		dataBinder.registerBinding("E3.shuliang", int.class, this.kdtE3, "shuliang.text");
		dataBinder.registerBinding("E3.power", String.class, this.kdtE3, "power.text");
		dataBinder.registerBinding("E3.speed", String.class, this.kdtE3, "speed.text");
		dataBinder.registerBinding("E3.chuandong", String.class, this.kdtE3, "chuandong.text");
		dataBinder.registerBinding("E3.zidong", String.class, this.kdtE3, "zidong.text");
		dataBinder.registerBinding("E3.madeFac", String.class, this.kdtE3, "madeFac.text");
		dataBinder.registerBinding("E3.noteoo", String.class, this.kdtE3, "noteoo.text");
		dataBinder.registerBinding("daytow", java.util.Date.class, this.pkdaytow, "value");
		dataBinder.registerBinding("dayone", java.util.Date.class, this.pkdayone, "value");
		dataBinder.registerBinding("testDay", java.util.Date.class, this.testDay, "value");
		dataBinder.registerBinding("resPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtresPerson, "data");
		dataBinder.registerBinding("portPeriod", java.math.BigDecimal.class, this.txtportPeriod, "value");
		dataBinder.registerBinding("installCost", java.math.BigDecimal.class, this.txtinstallCost, "value");
		dataBinder.registerBinding("jhOrgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtjhOrgUnit, "data");		
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("dependable", ValidateHelper.ON_SAVE);    
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
		getValidateHelper().registerBindProperty("isbaoxian", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isccCheck", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sbDescription", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ssOrgUnit", ValidateHelper.ON_SAVE);    
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("usingDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("location", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mader", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("madedCountry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("madeDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("reachedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ccNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("installer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("checkDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sourceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("deadline", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("debuger", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("portTest", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cityTest", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chuanCheck", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tzdaNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tzsbStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("specialType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cityPeriod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("code", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("engineNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("carNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ratedWeight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("textDate1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zzsShortName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("telePhoneNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actrueTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("responsible", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inStreet", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("textType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("equTypeone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cpgjzs", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cpsyqh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cpsbh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("asset", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("assetStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("assetValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("nowAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldYear", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EqmCategory", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TechnologyPar.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TechnologyPar", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TechnologyPar.parName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TechnologyPar.parValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TechnologyPar.parInfo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SpareInfo.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SpareInfo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SpareInfo.materialName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SpareInfo.speModel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SpareInfo.shuliangone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SpareInfo.useyong", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SpareInfo.fachangjia", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SpareInfo.noteone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("SpareInfo.attachone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.csmingcheng", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.csmodel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.shuliang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.power", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.speed", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.chuandong", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.zidong", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.madeFac", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.noteoo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("daytow", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dayone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("testDay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("resPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("portPeriod", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("installCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jhOrgUnit", ValidateHelper.ON_SAVE);    		
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
     * output prmtssOrgUnit_dataChanged method
     */
    protected void prmtssOrgUnit_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmttype_dataChanged method
     */
    protected void prmttype_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtcityPeriod_dataChanged method
     */
    protected void txtcityPeriod_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtasset_dataChanged method
     */
    protected void prmtasset_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtportPeriod_dataChanged method
     */
    protected void txtportPeriod_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("dependable"));
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
        sic.add(new SelectorItemInfo("isbaoxian"));
        sic.add(new SelectorItemInfo("isccCheck"));
        sic.add(new SelectorItemInfo("sbDescription"));
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
        sic.add(new SelectorItemInfo("number"));
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
			sic.add(new SelectorItemInfo("usingDept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("usingDept.id"));
        	sic.add(new SelectorItemInfo("usingDept.number"));
        	sic.add(new SelectorItemInfo("usingDept.name"));
		}
        sic.add(new SelectorItemInfo("location"));
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
        sic.add(new SelectorItemInfo("checkDate"));
        sic.add(new SelectorItemInfo("sourceUnit"));
        sic.add(new SelectorItemInfo("deadline"));
        sic.add(new SelectorItemInfo("debuger"));
        sic.add(new SelectorItemInfo("portTest"));
        sic.add(new SelectorItemInfo("cityTest"));
        sic.add(new SelectorItemInfo("chuanCheck"));
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
        sic.add(new SelectorItemInfo("code"));
        sic.add(new SelectorItemInfo("engineNumber"));
        sic.add(new SelectorItemInfo("carNumber"));
        sic.add(new SelectorItemInfo("ratedWeight"));
        sic.add(new SelectorItemInfo("textDate1"));
        sic.add(new SelectorItemInfo("zzsShortName"));
        sic.add(new SelectorItemInfo("telePhoneNumber"));
        sic.add(new SelectorItemInfo("actrueTime"));
        sic.add(new SelectorItemInfo("responsible"));
        sic.add(new SelectorItemInfo("inStreet"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("textType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("textType.id"));
        	sic.add(new SelectorItemInfo("textType.number"));
        	sic.add(new SelectorItemInfo("textType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("equTypeone.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("equTypeone.id"));
        	sic.add(new SelectorItemInfo("equTypeone.number"));
        	sic.add(new SelectorItemInfo("equTypeone.name"));
		}
        sic.add(new SelectorItemInfo("cpgjzs"));
        sic.add(new SelectorItemInfo("cpsyqh"));
        sic.add(new SelectorItemInfo("cpsbh"));
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
        sic.add(new SelectorItemInfo("nowAmount"));
        sic.add(new SelectorItemInfo("oldYear"));
        sic.add(new SelectorItemInfo("EqmCategory"));
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
    	sic.add(new SelectorItemInfo("SpareInfo.shuliangone"));
    	sic.add(new SelectorItemInfo("SpareInfo.useyong"));
    	sic.add(new SelectorItemInfo("SpareInfo.fachangjia"));
    	sic.add(new SelectorItemInfo("SpareInfo.noteone"));
    	sic.add(new SelectorItemInfo("SpareInfo.attachone"));
    	sic.add(new SelectorItemInfo("E3.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E3.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E3.csmingcheng"));
    	sic.add(new SelectorItemInfo("E3.csmodel"));
    	sic.add(new SelectorItemInfo("E3.shuliang"));
    	sic.add(new SelectorItemInfo("E3.power"));
    	sic.add(new SelectorItemInfo("E3.speed"));
    	sic.add(new SelectorItemInfo("E3.chuandong"));
    	sic.add(new SelectorItemInfo("E3.zidong"));
    	sic.add(new SelectorItemInfo("E3.madeFac"));
    	sic.add(new SelectorItemInfo("E3.noteoo"));
        sic.add(new SelectorItemInfo("daytow"));
        sic.add(new SelectorItemInfo("dayone"));
        sic.add(new SelectorItemInfo("testDay"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("resPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("resPerson.id"));
        	sic.add(new SelectorItemInfo("resPerson.number"));
        	sic.add(new SelectorItemInfo("resPerson.name"));
		}
        sic.add(new SelectorItemInfo("portPeriod"));
        sic.add(new SelectorItemInfo("installCost"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("jhOrgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("jhOrgUnit.id"));
        	sic.add(new SelectorItemInfo("jhOrgUnit.number"));
        	sic.add(new SelectorItemInfo("jhOrgUnit.name"));
		}
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
    	

    /**
     * output actionRegistChange_actionPerformed method
     */
    public void actionRegistChange_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().actionRegistChange(editData);
    }
    	

    /**
     * output actionExcel_actionPerformed method
     */
    public void actionExcel_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().excel(editData);
    }
    	

    /**
     * output actionExcelFoced_actionPerformed method
     */
    public void actionExcelFoced_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().excelFoced(editData);
    }
    	

    /**
     * output actionExcelEqu_actionPerformed method
     */
    public void actionExcelEqu_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().excelEqu(editData);
    }
    	

    /**
     * output actionZhuyao_actionPerformed method
     */
    public void actionZhuyao_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().zhuyao(editData);
    }
    	

    /**
     * output actionBeijian_actionPerformed method
     */
    public void actionBeijian_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().beijian(editData);
    }
    	

    /**
     * output actionXiangxi_actionPerformed method
     */
    public void actionXiangxi_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().xiangxi(editData);
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
	public RequestContext prepareActionRegistChange(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRegistChange() {
    	return false;
    }
	public RequestContext prepareActionExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExcel() {
    	return false;
    }
	public RequestContext prepareActionExcelFoced(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExcelFoced() {
    	return false;
    }
	public RequestContext prepareActionExcelEqu(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExcelEqu() {
    	return false;
    }
	public RequestContext prepareActionZhuyao(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionZhuyao() {
    	return false;
    }
	public RequestContext prepareActionBeijian(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBeijian() {
    	return false;
    }
	public RequestContext prepareActionXiangxi(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionXiangxi() {
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
     * output ActionRegistChange class
     */     
    protected class ActionRegistChange extends ItemAction {     
    
        public ActionRegistChange()
        {
            this(null);
        }

        public ActionRegistChange(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRegistChange.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRegistChange.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRegistChange.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdEditUI.this, "ActionRegistChange", "actionRegistChange_actionPerformed", e);
        }
    }

    /**
     * output ActionExcel class
     */     
    protected class ActionExcel extends ItemAction {     
    
        public ActionExcel()
        {
            this(null);
        }

        public ActionExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdEditUI.this, "ActionExcel", "actionExcel_actionPerformed", e);
        }
    }

    /**
     * output ActionExcelFoced class
     */     
    protected class ActionExcelFoced extends ItemAction {     
    
        public ActionExcelFoced()
        {
            this(null);
        }

        public ActionExcelFoced(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExcelFoced.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelFoced.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelFoced.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdEditUI.this, "ActionExcelFoced", "actionExcelFoced_actionPerformed", e);
        }
    }

    /**
     * output ActionExcelEqu class
     */     
    protected class ActionExcelEqu extends ItemAction {     
    
        public ActionExcelEqu()
        {
            this(null);
        }

        public ActionExcelEqu(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExcelEqu.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelEqu.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelEqu.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdEditUI.this, "ActionExcelEqu", "actionExcelEqu_actionPerformed", e);
        }
    }

    /**
     * output ActionZhuyao class
     */     
    protected class ActionZhuyao extends ItemAction {     
    
        public ActionZhuyao()
        {
            this(null);
        }

        public ActionZhuyao(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionZhuyao.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZhuyao.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZhuyao.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdEditUI.this, "ActionZhuyao", "actionZhuyao_actionPerformed", e);
        }
    }

    /**
     * output ActionBeijian class
     */     
    protected class ActionBeijian extends ItemAction {     
    
        public ActionBeijian()
        {
            this(null);
        }

        public ActionBeijian(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBeijian.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBeijian.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBeijian.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdEditUI.this, "ActionBeijian", "actionBeijian_actionPerformed", e);
        }
    }

    /**
     * output ActionXiangxi class
     */     
    protected class ActionXiangxi extends ItemAction {     
    
        public ActionXiangxi()
        {
            this(null);
        }

        public ActionXiangxi(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionXiangxi.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionXiangxi.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionXiangxi.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdEditUI.this, "ActionXiangxi", "actionXiangxi_actionPerformed", e);
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
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"设备状态"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmttype.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"设备类型"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtNumber.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"单据编号"});
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