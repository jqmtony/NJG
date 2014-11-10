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
public abstract class AbstractPressurePipingEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPressurePipingEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmedium;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpipelineNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contloadPoint;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdesignUnits;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continstalliUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer continsterDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstartDate;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contguandaocaizhi;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conthankoushuliang;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbiaoshi;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contguandaojibie;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpushefangshi;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfangfufangshi;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbaowenjuere;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpingdingUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhucedaima;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contnextDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjianyanbaogao;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmedium;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtpipelineNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtloadPoint;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdesignUnits;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtinstalliUnit;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkinsterDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkstartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contgongcheng;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbihou;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contguandaoLenth;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtgongcheng;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtbihou;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtguandaoLenth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdesign;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshejiwendu;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtdesign;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtshejiwendu;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contworkYali;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contworkWendu;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtworkYali;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtworkWendu;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtguandaocaizhi;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txthankoushuliang;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbiaoshi;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtguandaojibie;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpushefangshi;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfangfufangshi;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtbaowenjuere;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpingdingUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzhucedaima;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pknextDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjianyanbaogao;
    protected com.kingdee.eas.port.equipment.record.PressurePipingInfo editData = null;
    protected ActionExcel actionExcel = null;
    /**
     * output class constructor
     */
    public AbstractPressurePipingEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPressurePipingEditUI.class.getName());
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
        //actionExcel
        this.actionExcel = new ActionExcel(this);
        getActionManager().registerAction("actionExcel", actionExcel);
        this.actionExcel.setExtendProperty("canForewarn", "true");
        this.actionExcel.setExtendProperty("userDefined", "true");
        this.actionExcel.setExtendProperty("isObjectUpdateLock", "false");
         this.actionExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionExcel.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmedium = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpipelineNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contloadPoint = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdesignUnits = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continstalliUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.continsterDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contstartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contguandaocaizhi = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conthankoushuliang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbiaoshi = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contguandaojibie = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpushefangshi = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contfangfufangshi = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbaowenjuere = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpingdingUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzhucedaima = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contnextDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjianyanbaogao = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtmedium = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtpipelineNo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtloadPoint = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtdesignUnits = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtinstalliUnit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkinsterDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkstartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contgongcheng = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbihou = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contguandaoLenth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtgongcheng = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtbihou = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtguandaoLenth = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contdesign = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contshejiwendu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtdesign = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtshejiwendu = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contworkYali = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contworkWendu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtworkYali = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtworkWendu = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtguandaocaizhi = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txthankoushuliang = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtbiaoshi = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtguandaojibie = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtpushefangshi = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtfangfufangshi = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtbaowenjuere = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtpingdingUnit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzhucedaima = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pknextDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtjianyanbaogao = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contCU.setName("contCU");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contStatus.setName("contStatus");
        this.contBizStatus.setName("contBizStatus");
        this.contAuditTime.setName("contAuditTime");
        this.contmedium.setName("contmedium");
        this.contpipelineNo.setName("contpipelineNo");
        this.contloadPoint.setName("contloadPoint");
        this.contdesignUnits.setName("contdesignUnits");
        this.continstalliUnit.setName("continstalliUnit");
        this.continsterDate.setName("continsterDate");
        this.contstartDate.setName("contstartDate");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.contguandaocaizhi.setName("contguandaocaizhi");
        this.conthankoushuliang.setName("conthankoushuliang");
        this.contbiaoshi.setName("contbiaoshi");
        this.contguandaojibie.setName("contguandaojibie");
        this.contpushefangshi.setName("contpushefangshi");
        this.contfangfufangshi.setName("contfangfufangshi");
        this.contbaowenjuere.setName("contbaowenjuere");
        this.contpingdingUnit.setName("contpingdingUnit");
        this.contzhucedaima.setName("contzhucedaima");
        this.contnextDate.setName("contnextDate");
        this.contjianyanbaogao.setName("contjianyanbaogao");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.prmtCU.setName("prmtCU");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.comboStatus.setName("comboStatus");
        this.comboBizStatus.setName("comboBizStatus");
        this.pkAuditTime.setName("pkAuditTime");
        this.txtmedium.setName("txtmedium");
        this.txtpipelineNo.setName("txtpipelineNo");
        this.txtloadPoint.setName("txtloadPoint");
        this.txtdesignUnits.setName("txtdesignUnits");
        this.txtinstalliUnit.setName("txtinstalliUnit");
        this.pkinsterDate.setName("pkinsterDate");
        this.pkstartDate.setName("pkstartDate");
        this.contgongcheng.setName("contgongcheng");
        this.contbihou.setName("contbihou");
        this.contguandaoLenth.setName("contguandaoLenth");
        this.txtgongcheng.setName("txtgongcheng");
        this.txtbihou.setName("txtbihou");
        this.txtguandaoLenth.setName("txtguandaoLenth");
        this.contdesign.setName("contdesign");
        this.contshejiwendu.setName("contshejiwendu");
        this.txtdesign.setName("txtdesign");
        this.txtshejiwendu.setName("txtshejiwendu");
        this.contworkYali.setName("contworkYali");
        this.contworkWendu.setName("contworkWendu");
        this.txtworkYali.setName("txtworkYali");
        this.txtworkWendu.setName("txtworkWendu");
        this.txtguandaocaizhi.setName("txtguandaocaizhi");
        this.txthankoushuliang.setName("txthankoushuliang");
        this.txtbiaoshi.setName("txtbiaoshi");
        this.txtguandaojibie.setName("txtguandaojibie");
        this.txtpushefangshi.setName("txtpushefangshi");
        this.txtfangfufangshi.setName("txtfangfufangshi");
        this.txtbaowenjuere.setName("txtbaowenjuere");
        this.txtpingdingUnit.setName("txtpingdingUnit");
        this.txtzhucedaima.setName("txtzhucedaima");
        this.pknextDate.setName("pknextDate");
        this.txtjianyanbaogao.setName("txtjianyanbaogao");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,390));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
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
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);
        // contBizStatus		
        this.contBizStatus.setBoundLabelText(resHelper.getString("contBizStatus.boundLabelText"));		
        this.contBizStatus.setBoundLabelLength(100);		
        this.contBizStatus.setBoundLabelUnderline(true);		
        this.contBizStatus.setEnabled(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contmedium		
        this.contmedium.setBoundLabelText(resHelper.getString("contmedium.boundLabelText"));		
        this.contmedium.setBoundLabelLength(100);		
        this.contmedium.setBoundLabelUnderline(true);		
        this.contmedium.setVisible(true);
        // contpipelineNo		
        this.contpipelineNo.setBoundLabelText(resHelper.getString("contpipelineNo.boundLabelText"));		
        this.contpipelineNo.setBoundLabelLength(100);		
        this.contpipelineNo.setBoundLabelUnderline(true);		
        this.contpipelineNo.setVisible(true);
        // contloadPoint		
        this.contloadPoint.setBoundLabelText(resHelper.getString("contloadPoint.boundLabelText"));		
        this.contloadPoint.setBoundLabelLength(100);		
        this.contloadPoint.setBoundLabelUnderline(true);		
        this.contloadPoint.setVisible(true);
        // contdesignUnits		
        this.contdesignUnits.setBoundLabelText(resHelper.getString("contdesignUnits.boundLabelText"));		
        this.contdesignUnits.setBoundLabelLength(100);		
        this.contdesignUnits.setBoundLabelUnderline(true);		
        this.contdesignUnits.setVisible(true);
        // continstalliUnit		
        this.continstalliUnit.setBoundLabelText(resHelper.getString("continstalliUnit.boundLabelText"));		
        this.continstalliUnit.setBoundLabelLength(100);		
        this.continstalliUnit.setBoundLabelUnderline(true);		
        this.continstalliUnit.setVisible(true);
        // continsterDate		
        this.continsterDate.setBoundLabelText(resHelper.getString("continsterDate.boundLabelText"));		
        this.continsterDate.setBoundLabelLength(100);		
        this.continsterDate.setBoundLabelUnderline(true);		
        this.continsterDate.setVisible(true);
        // contstartDate		
        this.contstartDate.setBoundLabelText(resHelper.getString("contstartDate.boundLabelText"));		
        this.contstartDate.setBoundLabelLength(100);		
        this.contstartDate.setBoundLabelUnderline(true);		
        this.contstartDate.setVisible(true);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // contguandaocaizhi		
        this.contguandaocaizhi.setBoundLabelText(resHelper.getString("contguandaocaizhi.boundLabelText"));		
        this.contguandaocaizhi.setBoundLabelLength(100);		
        this.contguandaocaizhi.setBoundLabelUnderline(true);		
        this.contguandaocaizhi.setVisible(true);
        // conthankoushuliang		
        this.conthankoushuliang.setBoundLabelText(resHelper.getString("conthankoushuliang.boundLabelText"));		
        this.conthankoushuliang.setBoundLabelLength(100);		
        this.conthankoushuliang.setBoundLabelUnderline(true);		
        this.conthankoushuliang.setVisible(true);
        // contbiaoshi		
        this.contbiaoshi.setBoundLabelText(resHelper.getString("contbiaoshi.boundLabelText"));		
        this.contbiaoshi.setBoundLabelLength(100);		
        this.contbiaoshi.setBoundLabelUnderline(true);		
        this.contbiaoshi.setVisible(true);
        // contguandaojibie		
        this.contguandaojibie.setBoundLabelText(resHelper.getString("contguandaojibie.boundLabelText"));		
        this.contguandaojibie.setBoundLabelLength(100);		
        this.contguandaojibie.setBoundLabelUnderline(true);		
        this.contguandaojibie.setVisible(true);
        // contpushefangshi		
        this.contpushefangshi.setBoundLabelText(resHelper.getString("contpushefangshi.boundLabelText"));		
        this.contpushefangshi.setBoundLabelLength(100);		
        this.contpushefangshi.setBoundLabelUnderline(true);		
        this.contpushefangshi.setVisible(true);
        // contfangfufangshi		
        this.contfangfufangshi.setBoundLabelText(resHelper.getString("contfangfufangshi.boundLabelText"));		
        this.contfangfufangshi.setBoundLabelLength(100);		
        this.contfangfufangshi.setBoundLabelUnderline(true);		
        this.contfangfufangshi.setVisible(true);
        // contbaowenjuere		
        this.contbaowenjuere.setBoundLabelText(resHelper.getString("contbaowenjuere.boundLabelText"));		
        this.contbaowenjuere.setBoundLabelLength(100);		
        this.contbaowenjuere.setBoundLabelUnderline(true);		
        this.contbaowenjuere.setVisible(true);
        // contpingdingUnit		
        this.contpingdingUnit.setBoundLabelText(resHelper.getString("contpingdingUnit.boundLabelText"));		
        this.contpingdingUnit.setBoundLabelLength(114);		
        this.contpingdingUnit.setBoundLabelUnderline(true);		
        this.contpingdingUnit.setVisible(true);
        // contzhucedaima		
        this.contzhucedaima.setBoundLabelText(resHelper.getString("contzhucedaima.boundLabelText"));		
        this.contzhucedaima.setBoundLabelLength(100);		
        this.contzhucedaima.setBoundLabelUnderline(true);		
        this.contzhucedaima.setVisible(true);
        // contnextDate		
        this.contnextDate.setBoundLabelText(resHelper.getString("contnextDate.boundLabelText"));		
        this.contnextDate.setBoundLabelLength(100);		
        this.contnextDate.setBoundLabelUnderline(true);		
        this.contnextDate.setVisible(true);
        // contjianyanbaogao		
        this.contjianyanbaogao.setBoundLabelText(resHelper.getString("contjianyanbaogao.boundLabelText"));		
        this.contjianyanbaogao.setBoundLabelLength(100);		
        this.contjianyanbaogao.setBoundLabelUnderline(true);		
        this.contjianyanbaogao.setVisible(true);
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
        // txtNumber
        // pkBizDate
        // txtDescription
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
        // txtmedium		
        this.txtmedium.setVisible(true);		
        this.txtmedium.setHorizontalAlignment(2);		
        this.txtmedium.setMaxLength(100);		
        this.txtmedium.setRequired(false);
        // txtpipelineNo		
        this.txtpipelineNo.setVisible(true);		
        this.txtpipelineNo.setHorizontalAlignment(2);		
        this.txtpipelineNo.setDataType(1);		
        this.txtpipelineNo.setSupportedEmpty(true);		
        this.txtpipelineNo.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtpipelineNo.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtpipelineNo.setPrecision(4);		
        this.txtpipelineNo.setRequired(false);
        // txtloadPoint		
        this.txtloadPoint.setVisible(true);		
        this.txtloadPoint.setHorizontalAlignment(2);		
        this.txtloadPoint.setMaxLength(100);		
        this.txtloadPoint.setRequired(false);
        // txtdesignUnits		
        this.txtdesignUnits.setVisible(true);		
        this.txtdesignUnits.setHorizontalAlignment(2);		
        this.txtdesignUnits.setMaxLength(100);		
        this.txtdesignUnits.setRequired(false);
        // txtinstalliUnit		
        this.txtinstalliUnit.setVisible(true);		
        this.txtinstalliUnit.setHorizontalAlignment(2);		
        this.txtinstalliUnit.setMaxLength(100);		
        this.txtinstalliUnit.setRequired(false);
        // pkinsterDate		
        this.pkinsterDate.setVisible(true);		
        this.pkinsterDate.setRequired(false);
        // pkstartDate		
        this.pkstartDate.setVisible(true);		
        this.pkstartDate.setRequired(false);
        // contgongcheng		
        this.contgongcheng.setBoundLabelText(resHelper.getString("contgongcheng.boundLabelText"));		
        this.contgongcheng.setBoundLabelLength(100);		
        this.contgongcheng.setBoundLabelUnderline(true);		
        this.contgongcheng.setVisible(true);
        // contbihou		
        this.contbihou.setBoundLabelText(resHelper.getString("contbihou.boundLabelText"));		
        this.contbihou.setBoundLabelLength(100);		
        this.contbihou.setBoundLabelUnderline(true);		
        this.contbihou.setVisible(true);
        // contguandaoLenth		
        this.contguandaoLenth.setBoundLabelText(resHelper.getString("contguandaoLenth.boundLabelText"));		
        this.contguandaoLenth.setBoundLabelLength(100);		
        this.contguandaoLenth.setBoundLabelUnderline(true);		
        this.contguandaoLenth.setVisible(true);
        // txtgongcheng		
        this.txtgongcheng.setVisible(true);		
        this.txtgongcheng.setHorizontalAlignment(2);		
        this.txtgongcheng.setDataType(1);		
        this.txtgongcheng.setSupportedEmpty(true);		
        this.txtgongcheng.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtgongcheng.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtgongcheng.setPrecision(4);		
        this.txtgongcheng.setRequired(false);
        // txtbihou		
        this.txtbihou.setVisible(true);		
        this.txtbihou.setHorizontalAlignment(2);		
        this.txtbihou.setDataType(1);		
        this.txtbihou.setSupportedEmpty(true);		
        this.txtbihou.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtbihou.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtbihou.setPrecision(4);		
        this.txtbihou.setRequired(false);
        // txtguandaoLenth		
        this.txtguandaoLenth.setVisible(true);		
        this.txtguandaoLenth.setHorizontalAlignment(2);		
        this.txtguandaoLenth.setDataType(1);		
        this.txtguandaoLenth.setSupportedEmpty(true);		
        this.txtguandaoLenth.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtguandaoLenth.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtguandaoLenth.setPrecision(4);		
        this.txtguandaoLenth.setRequired(false);
        // contdesign		
        this.contdesign.setBoundLabelText(resHelper.getString("contdesign.boundLabelText"));		
        this.contdesign.setBoundLabelLength(100);		
        this.contdesign.setBoundLabelUnderline(true);		
        this.contdesign.setVisible(true);
        // contshejiwendu		
        this.contshejiwendu.setBoundLabelText(resHelper.getString("contshejiwendu.boundLabelText"));		
        this.contshejiwendu.setBoundLabelLength(100);		
        this.contshejiwendu.setBoundLabelUnderline(true);		
        this.contshejiwendu.setVisible(true);
        // txtdesign		
        this.txtdesign.setVisible(true);		
        this.txtdesign.setHorizontalAlignment(2);		
        this.txtdesign.setDataType(1);		
        this.txtdesign.setSupportedEmpty(true);		
        this.txtdesign.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtdesign.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtdesign.setPrecision(2);		
        this.txtdesign.setRequired(false);
        // txtshejiwendu		
        this.txtshejiwendu.setVisible(true);		
        this.txtshejiwendu.setHorizontalAlignment(2);		
        this.txtshejiwendu.setDataType(1);		
        this.txtshejiwendu.setSupportedEmpty(true);		
        this.txtshejiwendu.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtshejiwendu.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtshejiwendu.setPrecision(2);		
        this.txtshejiwendu.setRequired(false);
        // contworkYali		
        this.contworkYali.setBoundLabelText(resHelper.getString("contworkYali.boundLabelText"));		
        this.contworkYali.setBoundLabelLength(100);		
        this.contworkYali.setBoundLabelUnderline(true);		
        this.contworkYali.setVisible(true);
        // contworkWendu		
        this.contworkWendu.setBoundLabelText(resHelper.getString("contworkWendu.boundLabelText"));		
        this.contworkWendu.setBoundLabelLength(100);		
        this.contworkWendu.setBoundLabelUnderline(true);		
        this.contworkWendu.setVisible(true);
        // txtworkYali		
        this.txtworkYali.setVisible(true);		
        this.txtworkYali.setHorizontalAlignment(2);		
        this.txtworkYali.setDataType(1);		
        this.txtworkYali.setSupportedEmpty(true);		
        this.txtworkYali.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtworkYali.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtworkYali.setPrecision(2);		
        this.txtworkYali.setRequired(false);
        // txtworkWendu		
        this.txtworkWendu.setVisible(true);		
        this.txtworkWendu.setHorizontalAlignment(2);		
        this.txtworkWendu.setDataType(1);		
        this.txtworkWendu.setSupportedEmpty(true);		
        this.txtworkWendu.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtworkWendu.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtworkWendu.setPrecision(2);		
        this.txtworkWendu.setRequired(false);
        // txtguandaocaizhi		
        this.txtguandaocaizhi.setVisible(true);		
        this.txtguandaocaizhi.setHorizontalAlignment(2);		
        this.txtguandaocaizhi.setMaxLength(100);		
        this.txtguandaocaizhi.setRequired(false);
        // txthankoushuliang		
        this.txthankoushuliang.setVisible(true);		
        this.txthankoushuliang.setHorizontalAlignment(2);		
        this.txthankoushuliang.setDataType(1);		
        this.txthankoushuliang.setSupportedEmpty(true);		
        this.txthankoushuliang.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txthankoushuliang.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txthankoushuliang.setPrecision(4);		
        this.txthankoushuliang.setRequired(false);
        // txtbiaoshi		
        this.txtbiaoshi.setVisible(true);		
        this.txtbiaoshi.setHorizontalAlignment(2);		
        this.txtbiaoshi.setMaxLength(100);		
        this.txtbiaoshi.setRequired(false);
        // txtguandaojibie		
        this.txtguandaojibie.setVisible(true);		
        this.txtguandaojibie.setHorizontalAlignment(2);		
        this.txtguandaojibie.setMaxLength(100);		
        this.txtguandaojibie.setRequired(false);
        // txtpushefangshi		
        this.txtpushefangshi.setVisible(true);		
        this.txtpushefangshi.setHorizontalAlignment(2);		
        this.txtpushefangshi.setMaxLength(100);		
        this.txtpushefangshi.setRequired(false);
        // txtfangfufangshi		
        this.txtfangfufangshi.setVisible(true);		
        this.txtfangfufangshi.setHorizontalAlignment(2);		
        this.txtfangfufangshi.setMaxLength(100);		
        this.txtfangfufangshi.setRequired(false);
        // txtbaowenjuere		
        this.txtbaowenjuere.setVisible(true);		
        this.txtbaowenjuere.setHorizontalAlignment(2);		
        this.txtbaowenjuere.setMaxLength(100);		
        this.txtbaowenjuere.setRequired(false);
        // txtpingdingUnit		
        this.txtpingdingUnit.setVisible(true);		
        this.txtpingdingUnit.setHorizontalAlignment(2);		
        this.txtpingdingUnit.setMaxLength(100);		
        this.txtpingdingUnit.setRequired(false);
        // txtzhucedaima		
        this.txtzhucedaima.setVisible(true);		
        this.txtzhucedaima.setHorizontalAlignment(2);		
        this.txtzhucedaima.setMaxLength(100);		
        this.txtzhucedaima.setRequired(false);
        // pknextDate		
        this.pknextDate.setVisible(true);		
        this.pknextDate.setRequired(false);
        // txtjianyanbaogao		
        this.txtjianyanbaogao.setVisible(true);		
        this.txtjianyanbaogao.setHorizontalAlignment(2);		
        this.txtjianyanbaogao.setMaxLength(100);		
        this.txtjianyanbaogao.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtmedium,txtpipelineNo,txtloadPoint,txtdesignUnits,txtinstalliUnit,pkinsterDate,pkstartDate,txtgongcheng,txtbihou,txtguandaoLenth,txtdesign,txtshejiwendu,txtworkYali,txtworkWendu,txtguandaocaizhi,txthankoushuliang,txtbiaoshi,txtguandaojibie,txtpushefangshi,txtfangfufangshi,txtbaowenjuere,txtpingdingUnit,txtzhucedaima,pknextDate,txtjianyanbaogao}));
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
        this.setBounds(new Rectangle(10, 10, 1013, 390));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 390));
        contCreator.setBounds(new Rectangle(22, 332, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(22, 332, 270, 19, 0));
        contCreateTime.setBounds(new Rectangle(22, 355, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(22, 355, 270, 19, 0));
        contLastUpdateUser.setBounds(new Rectangle(370, 332, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(370, 332, 270, 19, 0));
        contLastUpdateTime.setBounds(new Rectangle(370, 355, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(370, 355, 270, 19, 0));
        contCU.setBounds(new Rectangle(721, 11, 270, 19));
        this.add(contCU, new KDLayout.Constraints(721, 11, 270, 19, 0));
        contNumber.setBounds(new Rectangle(22, 11, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(22, 11, 270, 19, 0));
        contBizDate.setBounds(new Rectangle(1029, 99, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(1029, 99, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(1018, 127, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(1018, 127, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(721, 332, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(721, 332, 270, 19, 0));
        contStatus.setBounds(new Rectangle(721, 308, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(721, 308, 270, 19, 0));
        contBizStatus.setBounds(new Rectangle(712, 571, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(712, 571, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(721, 355, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(721, 355, 270, 19, 0));
        contmedium.setBounds(new Rectangle(372, 11, 270, 19));
        this.add(contmedium, new KDLayout.Constraints(372, 11, 270, 19, 0));
        contpipelineNo.setBounds(new Rectangle(22, 33, 270, 19));
        this.add(contpipelineNo, new KDLayout.Constraints(22, 33, 270, 19, 0));
        contloadPoint.setBounds(new Rectangle(372, 33, 270, 19));
        this.add(contloadPoint, new KDLayout.Constraints(372, 33, 270, 19, 0));
        contdesignUnits.setBounds(new Rectangle(721, 33, 270, 19));
        this.add(contdesignUnits, new KDLayout.Constraints(721, 33, 270, 19, 0));
        continstalliUnit.setBounds(new Rectangle(22, 55, 270, 19));
        this.add(continstalliUnit, new KDLayout.Constraints(22, 55, 270, 19, 0));
        continsterDate.setBounds(new Rectangle(370, 55, 270, 19));
        this.add(continsterDate, new KDLayout.Constraints(370, 55, 270, 19, 0));
        contstartDate.setBounds(new Rectangle(721, 55, 270, 19));
        this.add(contstartDate, new KDLayout.Constraints(721, 55, 270, 19, 0));
        kDPanel1.setBounds(new Rectangle(8, 78, 998, 50));
        this.add(kDPanel1, new KDLayout.Constraints(8, 78, 998, 50, 0));
        kDPanel2.setBounds(new Rectangle(8, 132, 998, 50));
        this.add(kDPanel2, new KDLayout.Constraints(8, 132, 998, 50, 0));
        kDPanel3.setBounds(new Rectangle(8, 185, 998, 50));
        this.add(kDPanel3, new KDLayout.Constraints(8, 185, 998, 50, 0));
        contguandaocaizhi.setBounds(new Rectangle(22, 240, 270, 19));
        this.add(contguandaocaizhi, new KDLayout.Constraints(22, 240, 270, 19, 0));
        conthankoushuliang.setBounds(new Rectangle(370, 240, 270, 19));
        this.add(conthankoushuliang, new KDLayout.Constraints(370, 240, 270, 19, 0));
        contbiaoshi.setBounds(new Rectangle(721, 262, 270, 19));
        this.add(contbiaoshi, new KDLayout.Constraints(721, 262, 270, 19, 0));
        contguandaojibie.setBounds(new Rectangle(721, 240, 270, 19));
        this.add(contguandaojibie, new KDLayout.Constraints(721, 240, 270, 19, 0));
        contpushefangshi.setBounds(new Rectangle(22, 262, 270, 19));
        this.add(contpushefangshi, new KDLayout.Constraints(22, 262, 270, 19, 0));
        contfangfufangshi.setBounds(new Rectangle(370, 262, 270, 19));
        this.add(contfangfufangshi, new KDLayout.Constraints(370, 262, 270, 19, 0));
        contbaowenjuere.setBounds(new Rectangle(22, 285, 270, 19));
        this.add(contbaowenjuere, new KDLayout.Constraints(22, 285, 270, 19, 0));
        contpingdingUnit.setBounds(new Rectangle(370, 285, 270, 19));
        this.add(contpingdingUnit, new KDLayout.Constraints(370, 285, 270, 19, 0));
        contzhucedaima.setBounds(new Rectangle(721, 285, 270, 19));
        this.add(contzhucedaima, new KDLayout.Constraints(721, 285, 270, 19, 0));
        contnextDate.setBounds(new Rectangle(22, 308, 270, 19));
        this.add(contnextDate, new KDLayout.Constraints(22, 308, 270, 19, 0));
        contjianyanbaogao.setBounds(new Rectangle(370, 308, 270, 19));
        this.add(contjianyanbaogao, new KDLayout.Constraints(370, 308, 270, 19, 0));
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
        //contNumber
        contNumber.setBoundEditor(txtNumber);
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
        //contmedium
        contmedium.setBoundEditor(txtmedium);
        //contpipelineNo
        contpipelineNo.setBoundEditor(txtpipelineNo);
        //contloadPoint
        contloadPoint.setBoundEditor(txtloadPoint);
        //contdesignUnits
        contdesignUnits.setBoundEditor(txtdesignUnits);
        //continstalliUnit
        continstalliUnit.setBoundEditor(txtinstalliUnit);
        //continsterDate
        continsterDate.setBoundEditor(pkinsterDate);
        //contstartDate
        contstartDate.setBoundEditor(pkstartDate);
        //kDPanel1
        kDPanel1.setLayout(null);        contgongcheng.setBounds(new Rectangle(14, 15, 270, 19));
        kDPanel1.add(contgongcheng, null);
        contbihou.setBounds(new Rectangle(363, 15, 270, 19));
        kDPanel1.add(contbihou, null);
        contguandaoLenth.setBounds(new Rectangle(713, 15, 270, 19));
        kDPanel1.add(contguandaoLenth, null);
        //contgongcheng
        contgongcheng.setBoundEditor(txtgongcheng);
        //contbihou
        contbihou.setBoundEditor(txtbihou);
        //contguandaoLenth
        contguandaoLenth.setBoundEditor(txtguandaoLenth);
        //kDPanel2
        kDPanel2.setLayout(null);        contdesign.setBounds(new Rectangle(14, 16, 270, 19));
        kDPanel2.add(contdesign, null);
        contshejiwendu.setBounds(new Rectangle(714, 16, 270, 19));
        kDPanel2.add(contshejiwendu, null);
        //contdesign
        contdesign.setBoundEditor(txtdesign);
        //contshejiwendu
        contshejiwendu.setBoundEditor(txtshejiwendu);
        //kDPanel3
        kDPanel3.setLayout(null);        contworkYali.setBounds(new Rectangle(13, 14, 270, 19));
        kDPanel3.add(contworkYali, null);
        contworkWendu.setBounds(new Rectangle(714, 14, 270, 19));
        kDPanel3.add(contworkWendu, null);
        //contworkYali
        contworkYali.setBoundEditor(txtworkYali);
        //contworkWendu
        contworkWendu.setBoundEditor(txtworkWendu);
        //contguandaocaizhi
        contguandaocaizhi.setBoundEditor(txtguandaocaizhi);
        //conthankoushuliang
        conthankoushuliang.setBoundEditor(txthankoushuliang);
        //contbiaoshi
        contbiaoshi.setBoundEditor(txtbiaoshi);
        //contguandaojibie
        contguandaojibie.setBoundEditor(txtguandaojibie);
        //contpushefangshi
        contpushefangshi.setBoundEditor(txtpushefangshi);
        //contfangfufangshi
        contfangfufangshi.setBoundEditor(txtfangfufangshi);
        //contbaowenjuere
        contbaowenjuere.setBoundEditor(txtbaowenjuere);
        //contpingdingUnit
        contpingdingUnit.setBoundEditor(txtpingdingUnit);
        //contzhucedaima
        contzhucedaima.setBoundEditor(txtzhucedaima);
        //contnextDate
        contnextDate.setBoundEditor(pknextDate);
        //contjianyanbaogao
        contjianyanbaogao.setBoundEditor(txtjianyanbaogao);

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
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("medium", String.class, this.txtmedium, "text");
		dataBinder.registerBinding("pipelineNo", java.math.BigDecimal.class, this.txtpipelineNo, "value");
		dataBinder.registerBinding("loadPoint", String.class, this.txtloadPoint, "text");
		dataBinder.registerBinding("designUnits", String.class, this.txtdesignUnits, "text");
		dataBinder.registerBinding("installiUnit", String.class, this.txtinstalliUnit, "text");
		dataBinder.registerBinding("insterDate", java.util.Date.class, this.pkinsterDate, "value");
		dataBinder.registerBinding("startDate", java.util.Date.class, this.pkstartDate, "value");
		dataBinder.registerBinding("gongcheng", java.math.BigDecimal.class, this.txtgongcheng, "value");
		dataBinder.registerBinding("bihou", java.math.BigDecimal.class, this.txtbihou, "value");
		dataBinder.registerBinding("guandaoLenth", java.math.BigDecimal.class, this.txtguandaoLenth, "value");
		dataBinder.registerBinding("design", java.math.BigDecimal.class, this.txtdesign, "value");
		dataBinder.registerBinding("shejiwendu", java.math.BigDecimal.class, this.txtshejiwendu, "value");
		dataBinder.registerBinding("workYali", java.math.BigDecimal.class, this.txtworkYali, "value");
		dataBinder.registerBinding("workWendu", java.math.BigDecimal.class, this.txtworkWendu, "value");
		dataBinder.registerBinding("guandaocaizhi", String.class, this.txtguandaocaizhi, "text");
		dataBinder.registerBinding("hankoushuliang", java.math.BigDecimal.class, this.txthankoushuliang, "value");
		dataBinder.registerBinding("biaoshi", String.class, this.txtbiaoshi, "text");
		dataBinder.registerBinding("guandaojibie", String.class, this.txtguandaojibie, "text");
		dataBinder.registerBinding("pushefangshi", String.class, this.txtpushefangshi, "text");
		dataBinder.registerBinding("fangfufangshi", String.class, this.txtfangfufangshi, "text");
		dataBinder.registerBinding("baowenjuere", String.class, this.txtbaowenjuere, "text");
		dataBinder.registerBinding("pingdingUnit", String.class, this.txtpingdingUnit, "text");
		dataBinder.registerBinding("zhucedaima", String.class, this.txtzhucedaima, "text");
		dataBinder.registerBinding("nextDate", java.util.Date.class, this.pknextDate, "value");
		dataBinder.registerBinding("jianyanbaogao", String.class, this.txtjianyanbaogao, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.record.app.PressurePipingEditUIHandler";
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
        this.txtmedium.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.record.PressurePipingInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("medium", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pipelineNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("loadPoint", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("designUnits", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("installiUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("insterDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("gongcheng", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bihou", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("guandaoLenth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("design", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shejiwendu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("workYali", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("workWendu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("guandaocaizhi", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hankoushuliang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("biaoshi", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("guandaojibie", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pushefangshi", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fangfufangshi", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("baowenjuere", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pingdingUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhucedaima", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("nextDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jianyanbaogao", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("number"));
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
        sic.add(new SelectorItemInfo("medium"));
        sic.add(new SelectorItemInfo("pipelineNo"));
        sic.add(new SelectorItemInfo("loadPoint"));
        sic.add(new SelectorItemInfo("designUnits"));
        sic.add(new SelectorItemInfo("installiUnit"));
        sic.add(new SelectorItemInfo("insterDate"));
        sic.add(new SelectorItemInfo("startDate"));
        sic.add(new SelectorItemInfo("gongcheng"));
        sic.add(new SelectorItemInfo("bihou"));
        sic.add(new SelectorItemInfo("guandaoLenth"));
        sic.add(new SelectorItemInfo("design"));
        sic.add(new SelectorItemInfo("shejiwendu"));
        sic.add(new SelectorItemInfo("workYali"));
        sic.add(new SelectorItemInfo("workWendu"));
        sic.add(new SelectorItemInfo("guandaocaizhi"));
        sic.add(new SelectorItemInfo("hankoushuliang"));
        sic.add(new SelectorItemInfo("biaoshi"));
        sic.add(new SelectorItemInfo("guandaojibie"));
        sic.add(new SelectorItemInfo("pushefangshi"));
        sic.add(new SelectorItemInfo("fangfufangshi"));
        sic.add(new SelectorItemInfo("baowenjuere"));
        sic.add(new SelectorItemInfo("pingdingUnit"));
        sic.add(new SelectorItemInfo("zhucedaima"));
        sic.add(new SelectorItemInfo("nextDate"));
        sic.add(new SelectorItemInfo("jianyanbaogao"));
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
     * output actionExcel_actionPerformed method
     */
    public void actionExcel_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.port.equipment.record.PressurePipingFactory.getRemoteInstance().excel(editData);
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
            innerActionPerformed("eas", AbstractPressurePipingEditUI.this, "ActionExcel", "actionExcel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.record.client", "PressurePipingEditUI");
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
        return com.kingdee.eas.port.equipment.record.client.PressurePipingEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.record.PressurePipingFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.record.PressurePipingInfo objectValue = new com.kingdee.eas.port.equipment.record.PressurePipingInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/record/PressurePiping";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.record.app.PressurePipingQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {        
        return null;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}