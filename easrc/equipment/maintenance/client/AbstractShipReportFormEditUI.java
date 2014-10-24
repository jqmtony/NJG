/**
 * output package name
 */
package com.kingdee.eas.port.equipment.maintenance.client;

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
public abstract class AbstractShipReportFormEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractShipReportFormEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshipName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contperformance;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE1;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE1_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcourse;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttowing;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmeasuringDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contexProblems;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsuspendRepair;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrepairType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshipzhang;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlunjizhang;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmonth;
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
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtshipName;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneperformance;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtperformance;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzuozs;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyouzs;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzuozy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyouzy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzuowd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyouwd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzuorh;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyourh;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzuozs;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyouzs;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzuozy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyouzy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzuowd;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyouwd;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtzuorh;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyourh;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcourse;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttowing;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkmeasuringDate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneexProblems;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtexProblems;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtsuspendRepair;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtrepairType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtshipzhang;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtlunjizhang;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtmonth;
    protected com.kingdee.eas.port.equipment.maintenance.ShipReportFormInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractShipReportFormEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractShipReportFormEditUI.class.getName());
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
        this.contshipName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contperformance = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtE1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contcourse = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttowing = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmeasuringDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contexProblems = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsuspendRepair = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrepairType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contshipzhang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlunjizhang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.prmtshipName = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.scrollPaneperformance = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtperformance = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contzuozs = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyouzs = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzuozy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyouzy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzuowd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyouwd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzuorh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyourh = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtzuozs = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyouzs = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzuozy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyouzy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzuowd = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyouwd = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtzuorh = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyourh = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtcourse = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttowing = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkmeasuringDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.scrollPaneexProblems = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtexProblems = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtsuspendRepair = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtrepairType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtshipzhang = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtlunjizhang = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtmonth = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
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
        this.contshipName.setName("contshipName");
        this.contperformance.setName("contperformance");
        this.kDPanel2.setName("kDPanel2");
        this.kdtE1.setName("kdtE1");
        this.contcourse.setName("contcourse");
        this.conttowing.setName("conttowing");
        this.contmeasuringDate.setName("contmeasuringDate");
        this.contexProblems.setName("contexProblems");
        this.contsuspendRepair.setName("contsuspendRepair");
        this.contrepairType.setName("contrepairType");
        this.contshipzhang.setName("contshipzhang");
        this.contlunjizhang.setName("contlunjizhang");
        this.contmonth.setName("contmonth");
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
        this.prmtshipName.setName("prmtshipName");
        this.scrollPaneperformance.setName("scrollPaneperformance");
        this.txtperformance.setName("txtperformance");
        this.contzuozs.setName("contzuozs");
        this.contyouzs.setName("contyouzs");
        this.contzuozy.setName("contzuozy");
        this.contyouzy.setName("contyouzy");
        this.contzuowd.setName("contzuowd");
        this.contyouwd.setName("contyouwd");
        this.contzuorh.setName("contzuorh");
        this.contyourh.setName("contyourh");
        this.txtzuozs.setName("txtzuozs");
        this.txtyouzs.setName("txtyouzs");
        this.txtzuozy.setName("txtzuozy");
        this.txtyouzy.setName("txtyouzy");
        this.txtzuowd.setName("txtzuowd");
        this.txtyouwd.setName("txtyouwd");
        this.txtzuorh.setName("txtzuorh");
        this.txtyourh.setName("txtyourh");
        this.txtcourse.setName("txtcourse");
        this.txttowing.setName("txttowing");
        this.pkmeasuringDate.setName("pkmeasuringDate");
        this.scrollPaneexProblems.setName("scrollPaneexProblems");
        this.txtexProblems.setName("txtexProblems");
        this.txtsuspendRepair.setName("txtsuspendRepair");
        this.txtrepairType.setName("txtrepairType");
        this.prmtshipzhang.setName("prmtshipzhang");
        this.prmtlunjizhang.setName("prmtlunjizhang");
        this.prmtmonth.setName("prmtmonth");
        // CoreUI
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
        // contshipName		
        this.contshipName.setBoundLabelText(resHelper.getString("contshipName.boundLabelText"));		
        this.contshipName.setBoundLabelLength(100);		
        this.contshipName.setBoundLabelUnderline(true);		
        this.contshipName.setVisible(true);
        // contperformance		
        this.contperformance.setBoundLabelText(resHelper.getString("contperformance.boundLabelText"));		
        this.contperformance.setBoundLabelLength(100);		
        this.contperformance.setBoundLabelUnderline(true);		
        this.contperformance.setVisible(true);
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // kdtE1
		String kdtE1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"zhujigangone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"zhujigangtwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"one\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"two\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"three\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"four\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"five\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"six\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"seven\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"eight\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"nine\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{zhujigangone}</t:Cell><t:Cell>$Resource{zhujigangtwo}</t:Cell><t:Cell>$Resource{one}</t:Cell><t:Cell>$Resource{two}</t:Cell><t:Cell>$Resource{three}</t:Cell><t:Cell>$Resource{four}</t:Cell><t:Cell>$Resource{five}</t:Cell><t:Cell>$Resource{six}</t:Cell><t:Cell>$Resource{seven}</t:Cell><t:Cell>$Resource{eight}</t:Cell><t:Cell>$Resource{nine}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"0\" t:right=\"2\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE1.setFormatXml(resHelper.translateString("kdtE1",kdtE1StrXML));

                this.kdtE1.putBindContents("editData",new String[] {"seq","zhujigangone","zhujigangtwo","one","two","three","four","five","six","seven","eight","nine"});


        this.kdtE1.checkParsed();
        KDFormattedTextField kdtE1_seq_TextField = new KDFormattedTextField();
        kdtE1_seq_TextField.setName("kdtE1_seq_TextField");
        kdtE1_seq_TextField.setVisible(true);
        kdtE1_seq_TextField.setEditable(true);
        kdtE1_seq_TextField.setHorizontalAlignment(2);
        kdtE1_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtE1_seq_CellEditor = new KDTDefaultCellEditor(kdtE1_seq_TextField);
        this.kdtE1.getColumn("seq").setEditor(kdtE1_seq_CellEditor);
        KDTextField kdtE1_zhujigangone_TextField = new KDTextField();
        kdtE1_zhujigangone_TextField.setName("kdtE1_zhujigangone_TextField");
        kdtE1_zhujigangone_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_zhujigangone_CellEditor = new KDTDefaultCellEditor(kdtE1_zhujigangone_TextField);
        this.kdtE1.getColumn("zhujigangone").setEditor(kdtE1_zhujigangone_CellEditor);
        KDTextField kdtE1_zhujigangtwo_TextField = new KDTextField();
        kdtE1_zhujigangtwo_TextField.setName("kdtE1_zhujigangtwo_TextField");
        kdtE1_zhujigangtwo_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_zhujigangtwo_CellEditor = new KDTDefaultCellEditor(kdtE1_zhujigangtwo_TextField);
        this.kdtE1.getColumn("zhujigangtwo").setEditor(kdtE1_zhujigangtwo_CellEditor);
        KDTextField kdtE1_one_TextField = new KDTextField();
        kdtE1_one_TextField.setName("kdtE1_one_TextField");
        kdtE1_one_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_one_CellEditor = new KDTDefaultCellEditor(kdtE1_one_TextField);
        this.kdtE1.getColumn("one").setEditor(kdtE1_one_CellEditor);
        KDTextField kdtE1_two_TextField = new KDTextField();
        kdtE1_two_TextField.setName("kdtE1_two_TextField");
        kdtE1_two_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_two_CellEditor = new KDTDefaultCellEditor(kdtE1_two_TextField);
        this.kdtE1.getColumn("two").setEditor(kdtE1_two_CellEditor);
        KDTextField kdtE1_three_TextField = new KDTextField();
        kdtE1_three_TextField.setName("kdtE1_three_TextField");
        kdtE1_three_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_three_CellEditor = new KDTDefaultCellEditor(kdtE1_three_TextField);
        this.kdtE1.getColumn("three").setEditor(kdtE1_three_CellEditor);
        KDTextField kdtE1_four_TextField = new KDTextField();
        kdtE1_four_TextField.setName("kdtE1_four_TextField");
        kdtE1_four_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_four_CellEditor = new KDTDefaultCellEditor(kdtE1_four_TextField);
        this.kdtE1.getColumn("four").setEditor(kdtE1_four_CellEditor);
        KDTextField kdtE1_five_TextField = new KDTextField();
        kdtE1_five_TextField.setName("kdtE1_five_TextField");
        kdtE1_five_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_five_CellEditor = new KDTDefaultCellEditor(kdtE1_five_TextField);
        this.kdtE1.getColumn("five").setEditor(kdtE1_five_CellEditor);
        KDTextField kdtE1_six_TextField = new KDTextField();
        kdtE1_six_TextField.setName("kdtE1_six_TextField");
        kdtE1_six_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_six_CellEditor = new KDTDefaultCellEditor(kdtE1_six_TextField);
        this.kdtE1.getColumn("six").setEditor(kdtE1_six_CellEditor);
        KDTextField kdtE1_seven_TextField = new KDTextField();
        kdtE1_seven_TextField.setName("kdtE1_seven_TextField");
        kdtE1_seven_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_seven_CellEditor = new KDTDefaultCellEditor(kdtE1_seven_TextField);
        this.kdtE1.getColumn("seven").setEditor(kdtE1_seven_CellEditor);
        KDTextField kdtE1_eight_TextField = new KDTextField();
        kdtE1_eight_TextField.setName("kdtE1_eight_TextField");
        kdtE1_eight_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_eight_CellEditor = new KDTDefaultCellEditor(kdtE1_eight_TextField);
        this.kdtE1.getColumn("eight").setEditor(kdtE1_eight_CellEditor);
        KDTextField kdtE1_nine_TextField = new KDTextField();
        kdtE1_nine_TextField.setName("kdtE1_nine_TextField");
        kdtE1_nine_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_nine_CellEditor = new KDTDefaultCellEditor(kdtE1_nine_TextField);
        this.kdtE1.getColumn("nine").setEditor(kdtE1_nine_CellEditor);
        // contcourse		
        this.contcourse.setBoundLabelText(resHelper.getString("contcourse.boundLabelText"));		
        this.contcourse.setBoundLabelLength(100);		
        this.contcourse.setBoundLabelUnderline(true);		
        this.contcourse.setVisible(true);
        // conttowing		
        this.conttowing.setBoundLabelText(resHelper.getString("conttowing.boundLabelText"));		
        this.conttowing.setBoundLabelLength(100);		
        this.conttowing.setBoundLabelUnderline(true);		
        this.conttowing.setVisible(true);
        // contmeasuringDate		
        this.contmeasuringDate.setBoundLabelText(resHelper.getString("contmeasuringDate.boundLabelText"));		
        this.contmeasuringDate.setBoundLabelLength(100);		
        this.contmeasuringDate.setBoundLabelUnderline(true);		
        this.contmeasuringDate.setVisible(true);
        // contexProblems		
        this.contexProblems.setBoundLabelText(resHelper.getString("contexProblems.boundLabelText"));		
        this.contexProblems.setBoundLabelLength(100);		
        this.contexProblems.setBoundLabelUnderline(true);		
        this.contexProblems.setVisible(true);
        // contsuspendRepair		
        this.contsuspendRepair.setBoundLabelText(resHelper.getString("contsuspendRepair.boundLabelText"));		
        this.contsuspendRepair.setBoundLabelLength(100);		
        this.contsuspendRepair.setBoundLabelUnderline(true);		
        this.contsuspendRepair.setVisible(true);
        // contrepairType		
        this.contrepairType.setBoundLabelText(resHelper.getString("contrepairType.boundLabelText"));		
        this.contrepairType.setBoundLabelLength(100);		
        this.contrepairType.setBoundLabelUnderline(true);		
        this.contrepairType.setVisible(true);
        // contshipzhang		
        this.contshipzhang.setBoundLabelText(resHelper.getString("contshipzhang.boundLabelText"));		
        this.contshipzhang.setBoundLabelLength(100);		
        this.contshipzhang.setBoundLabelUnderline(true);		
        this.contshipzhang.setVisible(true);
        // contlunjizhang		
        this.contlunjizhang.setBoundLabelText(resHelper.getString("contlunjizhang.boundLabelText"));		
        this.contlunjizhang.setBoundLabelLength(100);		
        this.contlunjizhang.setBoundLabelUnderline(true);		
        this.contlunjizhang.setVisible(true);
        // contmonth		
        this.contmonth.setBoundLabelText(resHelper.getString("contmonth.boundLabelText"));		
        this.contmonth.setBoundLabelLength(100);		
        this.contmonth.setBoundLabelUnderline(true);		
        this.contmonth.setVisible(true);
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
        // prmtshipName		
        this.prmtshipName.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");		
        this.prmtshipName.setVisible(true);		
        this.prmtshipName.setEditable(true);		
        this.prmtshipName.setDisplayFormat("$name$");		
        this.prmtshipName.setEditFormat("$number$");		
        this.prmtshipName.setCommitFormat("$number$");		
        this.prmtshipName.setRequired(false);
        // scrollPaneperformance
        // txtperformance		
        this.txtperformance.setVisible(true);		
        this.txtperformance.setRequired(false);		
        this.txtperformance.setMaxLength(5000);
        // contzuozs		
        this.contzuozs.setBoundLabelText(resHelper.getString("contzuozs.boundLabelText"));		
        this.contzuozs.setBoundLabelLength(85);		
        this.contzuozs.setBoundLabelUnderline(true);		
        this.contzuozs.setVisible(true);
        // contyouzs		
        this.contyouzs.setBoundLabelText(resHelper.getString("contyouzs.boundLabelText"));		
        this.contyouzs.setBoundLabelLength(85);		
        this.contyouzs.setBoundLabelUnderline(true);		
        this.contyouzs.setVisible(true);
        // contzuozy		
        this.contzuozy.setBoundLabelText(resHelper.getString("contzuozy.boundLabelText"));		
        this.contzuozy.setBoundLabelLength(110);		
        this.contzuozy.setBoundLabelUnderline(true);		
        this.contzuozy.setVisible(true);
        // contyouzy		
        this.contyouzy.setBoundLabelText(resHelper.getString("contyouzy.boundLabelText"));		
        this.contyouzy.setBoundLabelLength(110);		
        this.contyouzy.setBoundLabelUnderline(true);		
        this.contyouzy.setVisible(true);
        // contzuowd		
        this.contzuowd.setBoundLabelText(resHelper.getString("contzuowd.boundLabelText"));		
        this.contzuowd.setBoundLabelLength(100);		
        this.contzuowd.setBoundLabelUnderline(true);		
        this.contzuowd.setVisible(true);
        // contyouwd		
        this.contyouwd.setBoundLabelText(resHelper.getString("contyouwd.boundLabelText"));		
        this.contyouwd.setBoundLabelLength(100);		
        this.contyouwd.setBoundLabelUnderline(true);		
        this.contyouwd.setVisible(true);
        // contzuorh		
        this.contzuorh.setBoundLabelText(resHelper.getString("contzuorh.boundLabelText"));		
        this.contzuorh.setBoundLabelLength(100);		
        this.contzuorh.setBoundLabelUnderline(true);		
        this.contzuorh.setVisible(true);
        // contyourh		
        this.contyourh.setBoundLabelText(resHelper.getString("contyourh.boundLabelText"));		
        this.contyourh.setBoundLabelLength(100);		
        this.contyourh.setBoundLabelUnderline(true);		
        this.contyourh.setVisible(true);
        // txtzuozs		
        this.txtzuozs.setVisible(true);		
        this.txtzuozs.setHorizontalAlignment(2);		
        this.txtzuozs.setMaxLength(100);		
        this.txtzuozs.setRequired(false);
        // txtyouzs		
        this.txtyouzs.setVisible(true);		
        this.txtyouzs.setHorizontalAlignment(2);		
        this.txtyouzs.setMaxLength(100);		
        this.txtyouzs.setRequired(false);
        // txtzuozy		
        this.txtzuozy.setVisible(true);		
        this.txtzuozy.setHorizontalAlignment(2);		
        this.txtzuozy.setMaxLength(100);		
        this.txtzuozy.setRequired(false);
        // txtyouzy		
        this.txtyouzy.setVisible(true);		
        this.txtyouzy.setHorizontalAlignment(2);		
        this.txtyouzy.setMaxLength(100);		
        this.txtyouzy.setRequired(false);
        // txtzuowd		
        this.txtzuowd.setVisible(true);		
        this.txtzuowd.setHorizontalAlignment(2);		
        this.txtzuowd.setMaxLength(100);		
        this.txtzuowd.setRequired(false);
        // txtyouwd		
        this.txtyouwd.setVisible(true);		
        this.txtyouwd.setHorizontalAlignment(2);		
        this.txtyouwd.setMaxLength(100);		
        this.txtyouwd.setRequired(false);
        // txtzuorh		
        this.txtzuorh.setVisible(true);		
        this.txtzuorh.setHorizontalAlignment(2);		
        this.txtzuorh.setMaxLength(100);		
        this.txtzuorh.setRequired(false);
        // txtyourh		
        this.txtyourh.setVisible(true);		
        this.txtyourh.setHorizontalAlignment(2);		
        this.txtyourh.setMaxLength(100);		
        this.txtyourh.setRequired(false);
        // txtcourse		
        this.txtcourse.setVisible(true);		
        this.txtcourse.setHorizontalAlignment(2);		
        this.txtcourse.setMaxLength(100);		
        this.txtcourse.setRequired(false);
        // txttowing		
        this.txttowing.setVisible(true);		
        this.txttowing.setHorizontalAlignment(2);		
        this.txttowing.setMaxLength(100);		
        this.txttowing.setRequired(false);
        // pkmeasuringDate		
        this.pkmeasuringDate.setVisible(true);		
        this.pkmeasuringDate.setRequired(false);
        // scrollPaneexProblems
        // txtexProblems		
        this.txtexProblems.setVisible(true);		
        this.txtexProblems.setRequired(false);		
        this.txtexProblems.setMaxLength(5000);
        // txtsuspendRepair		
        this.txtsuspendRepair.setVisible(true);		
        this.txtsuspendRepair.setHorizontalAlignment(2);		
        this.txtsuspendRepair.setMaxLength(100);		
        this.txtsuspendRepair.setRequired(false);
        // txtrepairType		
        this.txtrepairType.setVisible(true);		
        this.txtrepairType.setHorizontalAlignment(2);		
        this.txtrepairType.setMaxLength(100);		
        this.txtrepairType.setRequired(false);
        // prmtshipzhang		
        this.prmtshipzhang.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtshipzhang.setVisible(true);		
        this.prmtshipzhang.setEditable(true);		
        this.prmtshipzhang.setDisplayFormat("$name$");		
        this.prmtshipzhang.setEditFormat("$number$");		
        this.prmtshipzhang.setCommitFormat("$number$");		
        this.prmtshipzhang.setRequired(false);
        // prmtlunjizhang		
        this.prmtlunjizhang.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtlunjizhang.setVisible(true);		
        this.prmtlunjizhang.setEditable(true);		
        this.prmtlunjizhang.setDisplayFormat("$name$");		
        this.prmtlunjizhang.setEditFormat("$number$");		
        this.prmtlunjizhang.setCommitFormat("$number$");		
        this.prmtlunjizhang.setRequired(false);
        // prmtmonth		
        this.prmtmonth.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PeriodQuery");		
        this.prmtmonth.setVisible(true);		
        this.prmtmonth.setEditable(true);		
        this.prmtmonth.setDisplayFormat("$number$");		
        this.prmtmonth.setEditFormat("$number$");		
        this.prmtmonth.setCommitFormat("$number$");		
        this.prmtmonth.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtshipName,txtperformance,txtcourse,txttowing,pkmeasuringDate,txtexProblems,txtzuozs,txtyouzs,txtzuozy,txtyouzy,txtzuowd,txtyouwd,txtzuorh,txtyourh,txtsuspendRepair,txtrepairType,prmtshipzhang,prmtlunjizhang,prmtmonth}));
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
        contCreator.setBounds(new Rectangle(35, 539, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(35, 539, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(35, 575, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(35, 575, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(364, 539, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(364, 539, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(364, 575, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(364, 575, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(357, 11, 270, 19));
        this.add(contCU, new KDLayout.Constraints(357, 11, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(33, 11, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(33, 11, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(693, 36, 270, 20));
        this.add(contBizDate, new KDLayout.Constraints(693, 36, 270, 20, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(1002, 115, 110, 8));
        this.add(contDescription, new KDLayout.Constraints(1002, 115, 110, 8, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(693, 539, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(693, 539, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(693, 11, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(693, 11, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(708, 611, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(708, 611, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(693, 575, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(693, 575, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contshipName.setBounds(new Rectangle(33, 36, 270, 19));
        this.add(contshipName, new KDLayout.Constraints(33, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contperformance.setBounds(new Rectangle(33, 61, 930, 55));
        this.add(contperformance, new KDLayout.Constraints(33, 61, 930, 55, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel2.setBounds(new Rectangle(33, 260, 930, 86));
        this.add(kDPanel2, new KDLayout.Constraints(33, 260, 930, 86, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtE1.setBounds(new Rectangle(33, 124, 930, 130));
        kdtE1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE1,new com.kingdee.eas.port.equipment.maintenance.ShipReportFormE1Info(),null,false);
        this.add(kdtE1_detailPanel, new KDLayout.Constraints(33, 124, 930, 130, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contcourse.setBounds(new Rectangle(33, 356, 270, 19));
        this.add(contcourse, new KDLayout.Constraints(33, 356, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttowing.setBounds(new Rectangle(363, 356, 270, 19));
        this.add(conttowing, new KDLayout.Constraints(363, 356, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contmeasuringDate.setBounds(new Rectangle(693, 356, 270, 19));
        this.add(contmeasuringDate, new KDLayout.Constraints(693, 356, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contexProblems.setBounds(new Rectangle(33, 383, 930, 80));
        this.add(contexProblems, new KDLayout.Constraints(33, 383, 930, 80, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsuspendRepair.setBounds(new Rectangle(33, 471, 270, 19));
        this.add(contsuspendRepair, new KDLayout.Constraints(33, 471, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contrepairType.setBounds(new Rectangle(693, 471, 270, 19));
        this.add(contrepairType, new KDLayout.Constraints(693, 471, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contshipzhang.setBounds(new Rectangle(35, 504, 270, 19));
        this.add(contshipzhang, new KDLayout.Constraints(35, 504, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contlunjizhang.setBounds(new Rectangle(693, 504, 270, 19));
        this.add(contlunjizhang, new KDLayout.Constraints(693, 504, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contmonth.setBounds(new Rectangle(357, 36, 270, 19));
        this.add(contmonth, new KDLayout.Constraints(357, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
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
        //contshipName
        contshipName.setBoundEditor(prmtshipName);
        //contperformance
        contperformance.setBoundEditor(scrollPaneperformance);
        //scrollPaneperformance
        scrollPaneperformance.getViewport().add(txtperformance, null);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(33, 260, 930, 86));        contzuozs.setBounds(new Rectangle(15, 20, 182, 19));
        kDPanel2.add(contzuozs, new KDLayout.Constraints(15, 20, 182, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contyouzs.setBounds(new Rectangle(15, 48, 182, 19));
        kDPanel2.add(contyouzs, new KDLayout.Constraints(15, 48, 182, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contzuozy.setBounds(new Rectangle(251, 20, 182, 19));
        kDPanel2.add(contzuozy, new KDLayout.Constraints(251, 20, 182, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contyouzy.setBounds(new Rectangle(251, 48, 182, 19));
        kDPanel2.add(contyouzy, new KDLayout.Constraints(251, 48, 182, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contzuowd.setBounds(new Rectangle(487, 20, 182, 19));
        kDPanel2.add(contzuowd, new KDLayout.Constraints(487, 20, 182, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contyouwd.setBounds(new Rectangle(487, 48, 182, 19));
        kDPanel2.add(contyouwd, new KDLayout.Constraints(487, 48, 182, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contzuorh.setBounds(new Rectangle(723, 20, 182, 19));
        kDPanel2.add(contzuorh, new KDLayout.Constraints(723, 20, 182, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contyourh.setBounds(new Rectangle(723, 48, 182, 19));
        kDPanel2.add(contyourh, new KDLayout.Constraints(723, 48, 182, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contzuozs
        contzuozs.setBoundEditor(txtzuozs);
        //contyouzs
        contyouzs.setBoundEditor(txtyouzs);
        //contzuozy
        contzuozy.setBoundEditor(txtzuozy);
        //contyouzy
        contyouzy.setBoundEditor(txtyouzy);
        //contzuowd
        contzuowd.setBoundEditor(txtzuowd);
        //contyouwd
        contyouwd.setBoundEditor(txtyouwd);
        //contzuorh
        contzuorh.setBoundEditor(txtzuorh);
        //contyourh
        contyourh.setBoundEditor(txtyourh);
        //contcourse
        contcourse.setBoundEditor(txtcourse);
        //conttowing
        conttowing.setBoundEditor(txttowing);
        //contmeasuringDate
        contmeasuringDate.setBoundEditor(pkmeasuringDate);
        //contexProblems
        contexProblems.setBoundEditor(scrollPaneexProblems);
        //scrollPaneexProblems
        scrollPaneexProblems.getViewport().add(txtexProblems, null);
        //contsuspendRepair
        contsuspendRepair.setBoundEditor(txtsuspendRepair);
        //contrepairType
        contrepairType.setBoundEditor(txtrepairType);
        //contshipzhang
        contshipzhang.setBoundEditor(prmtshipzhang);
        //contlunjizhang
        contlunjizhang.setBoundEditor(prmtlunjizhang);
        //contmonth
        contmonth.setBoundEditor(prmtmonth);

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
		dataBinder.registerBinding("E1.seq", int.class, this.kdtE1, "seq.text");
		dataBinder.registerBinding("E1", com.kingdee.eas.port.equipment.maintenance.ShipReportFormE1Info.class, this.kdtE1, "userObject");
		dataBinder.registerBinding("E1.zhujigangone", String.class, this.kdtE1, "zhujigangone.text");
		dataBinder.registerBinding("E1.zhujigangtwo", String.class, this.kdtE1, "zhujigangtwo.text");
		dataBinder.registerBinding("E1.one", String.class, this.kdtE1, "one.text");
		dataBinder.registerBinding("E1.two", String.class, this.kdtE1, "two.text");
		dataBinder.registerBinding("E1.three", String.class, this.kdtE1, "three.text");
		dataBinder.registerBinding("E1.four", String.class, this.kdtE1, "four.text");
		dataBinder.registerBinding("E1.five", String.class, this.kdtE1, "five.text");
		dataBinder.registerBinding("E1.six", String.class, this.kdtE1, "six.text");
		dataBinder.registerBinding("E1.seven", String.class, this.kdtE1, "seven.text");
		dataBinder.registerBinding("E1.eight", String.class, this.kdtE1, "eight.text");
		dataBinder.registerBinding("E1.nine", String.class, this.kdtE1, "nine.text");
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
		dataBinder.registerBinding("shipName", com.kingdee.eas.port.equipment.record.EquIdInfo.class, this.prmtshipName, "data");
		dataBinder.registerBinding("performance", String.class, this.txtperformance, "text");
		dataBinder.registerBinding("zuozs", String.class, this.txtzuozs, "text");
		dataBinder.registerBinding("youzs", String.class, this.txtyouzs, "text");
		dataBinder.registerBinding("zuozy", String.class, this.txtzuozy, "text");
		dataBinder.registerBinding("youzy", String.class, this.txtyouzy, "text");
		dataBinder.registerBinding("zuowd", String.class, this.txtzuowd, "text");
		dataBinder.registerBinding("youwd", String.class, this.txtyouwd, "text");
		dataBinder.registerBinding("zuorh", String.class, this.txtzuorh, "text");
		dataBinder.registerBinding("yourh", String.class, this.txtyourh, "text");
		dataBinder.registerBinding("course", String.class, this.txtcourse, "text");
		dataBinder.registerBinding("towing", String.class, this.txttowing, "text");
		dataBinder.registerBinding("measuringDate", java.util.Date.class, this.pkmeasuringDate, "value");
		dataBinder.registerBinding("exProblems", String.class, this.txtexProblems, "text");
		dataBinder.registerBinding("suspendRepair", String.class, this.txtsuspendRepair, "text");
		dataBinder.registerBinding("repairType", String.class, this.txtrepairType, "text");
		dataBinder.registerBinding("shipzhang", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtshipzhang, "data");
		dataBinder.registerBinding("lunjizhang", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtlunjizhang, "data");
		dataBinder.registerBinding("month", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.prmtmonth, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.maintenance.app.ShipReportFormEditUIHandler";
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
        this.prmtshipName.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.maintenance.ShipReportFormInfo)ov;
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
		getValidateHelper().registerBindProperty("E1.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.zhujigangone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.zhujigangtwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.one", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.two", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.three", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.four", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.five", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.six", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.seven", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.eight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.nine", ValidateHelper.ON_SAVE);    
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
		getValidateHelper().registerBindProperty("shipName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("performance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zuozs", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("youzs", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zuozy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("youzy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zuowd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("youwd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zuorh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yourh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("course", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("towing", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("measuringDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exProblems", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("suspendRepair", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("repairType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shipzhang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lunjizhang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("month", ValidateHelper.ON_SAVE);    		
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
    	sic.add(new SelectorItemInfo("E1.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E1.zhujigangone"));
    	sic.add(new SelectorItemInfo("E1.zhujigangtwo"));
    	sic.add(new SelectorItemInfo("E1.one"));
    	sic.add(new SelectorItemInfo("E1.two"));
    	sic.add(new SelectorItemInfo("E1.three"));
    	sic.add(new SelectorItemInfo("E1.four"));
    	sic.add(new SelectorItemInfo("E1.five"));
    	sic.add(new SelectorItemInfo("E1.six"));
    	sic.add(new SelectorItemInfo("E1.seven"));
    	sic.add(new SelectorItemInfo("E1.eight"));
    	sic.add(new SelectorItemInfo("E1.nine"));
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("shipName.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("shipName.id"));
        	sic.add(new SelectorItemInfo("shipName.number"));
        	sic.add(new SelectorItemInfo("shipName.name"));
		}
        sic.add(new SelectorItemInfo("performance"));
        sic.add(new SelectorItemInfo("zuozs"));
        sic.add(new SelectorItemInfo("youzs"));
        sic.add(new SelectorItemInfo("zuozy"));
        sic.add(new SelectorItemInfo("youzy"));
        sic.add(new SelectorItemInfo("zuowd"));
        sic.add(new SelectorItemInfo("youwd"));
        sic.add(new SelectorItemInfo("zuorh"));
        sic.add(new SelectorItemInfo("yourh"));
        sic.add(new SelectorItemInfo("course"));
        sic.add(new SelectorItemInfo("towing"));
        sic.add(new SelectorItemInfo("measuringDate"));
        sic.add(new SelectorItemInfo("exProblems"));
        sic.add(new SelectorItemInfo("suspendRepair"));
        sic.add(new SelectorItemInfo("repairType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("shipzhang.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("shipzhang.id"));
        	sic.add(new SelectorItemInfo("shipzhang.number"));
        	sic.add(new SelectorItemInfo("shipzhang.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lunjizhang.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lunjizhang.id"));
        	sic.add(new SelectorItemInfo("lunjizhang.number"));
        	sic.add(new SelectorItemInfo("lunjizhang.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("month.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("month.id"));
        	sic.add(new SelectorItemInfo("month.number"));
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

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.maintenance.client", "ShipReportFormEditUI");
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
        return com.kingdee.eas.port.equipment.maintenance.client.ShipReportFormEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.maintenance.ShipReportFormFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.maintenance.ShipReportFormInfo objectValue = new com.kingdee.eas.port.equipment.maintenance.ShipReportFormInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/maintenance/ShipReportForm";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.maintenance.app.ShipReportFormQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtE1;
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