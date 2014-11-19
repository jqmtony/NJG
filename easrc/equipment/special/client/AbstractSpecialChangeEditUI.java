/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.client;

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
public abstract class AbstractSpecialChangeEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSpecialChangeEditUI.class);
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
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcxdw;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsl;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcontent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBIMUDF0050;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjbr;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjbrq;
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
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contoldUseUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contnewUseUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contoldUnitCode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contnewUnitCode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHander;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtoldUseUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtnewUseUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtoldUnitCode;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtnewUnitCode;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHander;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttel;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkwr;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkylrq;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkylgd;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkdt;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkqzj;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkcnqc;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkylss;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkty;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkzx;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkgh;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkqy;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkgz;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtcxdw;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtsl;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanecontent;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtcontent;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneBIMUDF0050;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtBIMUDF0050;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtjbr;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkjbrq;
    protected com.kingdee.eas.port.equipment.special.SpecialChangeInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractSpecialChangeEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSpecialChangeEditUI.class.getName());
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
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contcxdw = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsl = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contcontent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBIMUDF0050 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjbr = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjbrq = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
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
        this.contoldUseUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contnewUseUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contoldUnitCode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contnewUnitCode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHander = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtoldUseUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtnewUseUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtoldUnitCode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtnewUnitCode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtHander = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txttel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.chkwr = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkylrq = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkylgd = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkdt = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkqzj = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkcnqc = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkylss = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkty = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkzx = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkgh = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkqy = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkgz = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtcxdw = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtsl = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.scrollPanecontent = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtcontent = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPaneBIMUDF0050 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtBIMUDF0050 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtjbr = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkjbrq = new com.kingdee.bos.ctrl.swing.KDDatePicker();
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
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.contcxdw.setName("contcxdw");
        this.contsl.setName("contsl");
        this.kdtEntry.setName("kdtEntry");
        this.contcontent.setName("contcontent");
        this.contBIMUDF0050.setName("contBIMUDF0050");
        this.contjbr.setName("contjbr");
        this.contjbrq.setName("contjbrq");
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
        this.contoldUseUnit.setName("contoldUseUnit");
        this.contnewUseUnit.setName("contnewUseUnit");
        this.contoldUnitCode.setName("contoldUnitCode");
        this.contnewUnitCode.setName("contnewUnitCode");
        this.contHander.setName("contHander");
        this.conttel.setName("conttel");
        this.prmtoldUseUnit.setName("prmtoldUseUnit");
        this.prmtnewUseUnit.setName("prmtnewUseUnit");
        this.txtoldUnitCode.setName("txtoldUnitCode");
        this.txtnewUnitCode.setName("txtnewUnitCode");
        this.prmtHander.setName("prmtHander");
        this.txttel.setName("txttel");
        this.chkwr.setName("chkwr");
        this.chkylrq.setName("chkylrq");
        this.chkylgd.setName("chkylgd");
        this.chkdt.setName("chkdt");
        this.chkqzj.setName("chkqzj");
        this.chkcnqc.setName("chkcnqc");
        this.chkylss.setName("chkylss");
        this.chkty.setName("chkty");
        this.chkzx.setName("chkzx");
        this.chkgh.setName("chkgh");
        this.chkqy.setName("chkqy");
        this.chkgz.setName("chkgz");
        this.txtcxdw.setName("txtcxdw");
        this.txtsl.setName("txtsl");
        this.scrollPanecontent.setName("scrollPanecontent");
        this.txtcontent.setName("txtcontent");
        this.scrollPaneBIMUDF0050.setName("scrollPaneBIMUDF0050");
        this.txtBIMUDF0050.setName("txtBIMUDF0050");
        this.txtjbr.setName("txtjbr");
        this.pkjbrq.setName("pkjbrq");
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
        this.contBizStatus.setVisible(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("kDPanel1.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("kDPanel2.border.title")));
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("kDPanel3.border.title")));
        // contcxdw		
        this.contcxdw.setBoundLabelText(resHelper.getString("contcxdw.boundLabelText"));		
        this.contcxdw.setBoundLabelLength(100);		
        this.contcxdw.setBoundLabelUnderline(true);		
        this.contcxdw.setVisible(true);
        // contsl		
        this.contsl.setBoundLabelText(resHelper.getString("contsl.boundLabelText"));		
        this.contsl.setBoundLabelLength(100);		
        this.contsl.setBoundLabelUnderline(true);		
        this.contsl.setVisible(true);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"zdaNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"equipmentName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"productNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"oldNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"beizhu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{zdaNumber}</t:Cell><t:Cell>$Resource{equipmentName}</t:Cell><t:Cell>$Resource{productNumber}</t:Cell><t:Cell>$Resource{oldNumber}</t:Cell><t:Cell>$Resource{beizhu}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        kdtEntry.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtEntry_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});


                this.kdtEntry.putBindContents("editData",new String[] {"seq","zdaNumber","equipmentName","productNumber","oldNumber","beizhu"});


        this.kdtEntry.checkParsed();
        final KDBizPromptBox kdtEntry_zdaNumber_PromptBox = new KDBizPromptBox();
        kdtEntry_zdaNumber_PromptBox.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");
        kdtEntry_zdaNumber_PromptBox.setVisible(true);
        kdtEntry_zdaNumber_PromptBox.setEditable(true);
        kdtEntry_zdaNumber_PromptBox.setDisplayFormat("$number$");
        kdtEntry_zdaNumber_PromptBox.setEditFormat("$number$");
        kdtEntry_zdaNumber_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_zdaNumber_CellEditor = new KDTDefaultCellEditor(kdtEntry_zdaNumber_PromptBox);
        this.kdtEntry.getColumn("zdaNumber").setEditor(kdtEntry_zdaNumber_CellEditor);
        ObjectValueRender kdtEntry_zdaNumber_OVR = new ObjectValueRender();
        kdtEntry_zdaNumber_OVR.setFormat(new BizDataFormat("$tzdaNumber$"));
        this.kdtEntry.getColumn("zdaNumber").setRenderer(kdtEntry_zdaNumber_OVR);
        KDTextField kdtEntry_equipmentName_TextField = new KDTextField();
        kdtEntry_equipmentName_TextField.setName("kdtEntry_equipmentName_TextField");
        kdtEntry_equipmentName_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_equipmentName_CellEditor = new KDTDefaultCellEditor(kdtEntry_equipmentName_TextField);
        this.kdtEntry.getColumn("equipmentName").setEditor(kdtEntry_equipmentName_CellEditor);
        KDTextField kdtEntry_productNumber_TextField = new KDTextField();
        kdtEntry_productNumber_TextField.setName("kdtEntry_productNumber_TextField");
        kdtEntry_productNumber_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_productNumber_CellEditor = new KDTDefaultCellEditor(kdtEntry_productNumber_TextField);
        this.kdtEntry.getColumn("productNumber").setEditor(kdtEntry_productNumber_CellEditor);
        KDTextField kdtEntry_oldNumber_TextField = new KDTextField();
        kdtEntry_oldNumber_TextField.setName("kdtEntry_oldNumber_TextField");
        kdtEntry_oldNumber_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_oldNumber_CellEditor = new KDTDefaultCellEditor(kdtEntry_oldNumber_TextField);
        this.kdtEntry.getColumn("oldNumber").setEditor(kdtEntry_oldNumber_CellEditor);
        KDTextField kdtEntry_beizhu_TextField = new KDTextField();
        kdtEntry_beizhu_TextField.setName("kdtEntry_beizhu_TextField");
        kdtEntry_beizhu_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_beizhu_CellEditor = new KDTDefaultCellEditor(kdtEntry_beizhu_TextField);
        this.kdtEntry.getColumn("beizhu").setEditor(kdtEntry_beizhu_CellEditor);
        // contcontent		
        this.contcontent.setBoundLabelText(resHelper.getString("contcontent.boundLabelText"));		
        this.contcontent.setBoundLabelLength(100);		
        this.contcontent.setBoundLabelUnderline(true);		
        this.contcontent.setVisible(true);
        // contBIMUDF0050		
        this.contBIMUDF0050.setBoundLabelText(resHelper.getString("contBIMUDF0050.boundLabelText"));		
        this.contBIMUDF0050.setBoundLabelLength(100);		
        this.contBIMUDF0050.setBoundLabelUnderline(true);		
        this.contBIMUDF0050.setVisible(true);
        // contjbr		
        this.contjbr.setBoundLabelText(resHelper.getString("contjbr.boundLabelText"));		
        this.contjbr.setBoundLabelLength(100);		
        this.contjbr.setBoundLabelUnderline(true);		
        this.contjbr.setVisible(true);
        // contjbrq		
        this.contjbrq.setBoundLabelText(resHelper.getString("contjbrq.boundLabelText"));		
        this.contjbrq.setBoundLabelLength(100);		
        this.contjbrq.setBoundLabelUnderline(true);		
        this.contjbrq.setVisible(true);
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
        // contoldUseUnit		
        this.contoldUseUnit.setBoundLabelText(resHelper.getString("contoldUseUnit.boundLabelText"));		
        this.contoldUseUnit.setBoundLabelLength(100);		
        this.contoldUseUnit.setBoundLabelUnderline(true);		
        this.contoldUseUnit.setVisible(true);
        // contnewUseUnit		
        this.contnewUseUnit.setBoundLabelText(resHelper.getString("contnewUseUnit.boundLabelText"));		
        this.contnewUseUnit.setBoundLabelLength(100);		
        this.contnewUseUnit.setBoundLabelUnderline(true);		
        this.contnewUseUnit.setVisible(true);
        // contoldUnitCode		
        this.contoldUnitCode.setBoundLabelText(resHelper.getString("contoldUnitCode.boundLabelText"));		
        this.contoldUnitCode.setBoundLabelLength(120);		
        this.contoldUnitCode.setBoundLabelUnderline(true);		
        this.contoldUnitCode.setVisible(true);
        // contnewUnitCode		
        this.contnewUnitCode.setBoundLabelText(resHelper.getString("contnewUnitCode.boundLabelText"));		
        this.contnewUnitCode.setBoundLabelLength(120);		
        this.contnewUnitCode.setBoundLabelUnderline(true);		
        this.contnewUnitCode.setVisible(true);
        // contHander		
        this.contHander.setBoundLabelText(resHelper.getString("contHander.boundLabelText"));		
        this.contHander.setBoundLabelLength(100);		
        this.contHander.setBoundLabelUnderline(true);		
        this.contHander.setVisible(true);
        // conttel		
        this.conttel.setBoundLabelText(resHelper.getString("conttel.boundLabelText"));		
        this.conttel.setBoundLabelLength(100);		
        this.conttel.setBoundLabelUnderline(true);		
        this.conttel.setVisible(true);
        // prmtoldUseUnit		
        this.prmtoldUseUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtoldUseUnit.setVisible(true);		
        this.prmtoldUseUnit.setEditable(true);		
        this.prmtoldUseUnit.setDisplayFormat("$name$");		
        this.prmtoldUseUnit.setEditFormat("$number$");		
        this.prmtoldUseUnit.setCommitFormat("$number$");		
        this.prmtoldUseUnit.setRequired(false);
        // prmtnewUseUnit		
        this.prmtnewUseUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtnewUseUnit.setVisible(true);		
        this.prmtnewUseUnit.setEditable(true);		
        this.prmtnewUseUnit.setDisplayFormat("$name$");		
        this.prmtnewUseUnit.setEditFormat("$number$");		
        this.prmtnewUseUnit.setCommitFormat("$number$");		
        this.prmtnewUseUnit.setRequired(false);
        // txtoldUnitCode		
        this.txtoldUnitCode.setVisible(true);		
        this.txtoldUnitCode.setHorizontalAlignment(2);		
        this.txtoldUnitCode.setMaxLength(100);		
        this.txtoldUnitCode.setRequired(false);
        // txtnewUnitCode		
        this.txtnewUnitCode.setVisible(true);		
        this.txtnewUnitCode.setHorizontalAlignment(2);		
        this.txtnewUnitCode.setMaxLength(100);		
        this.txtnewUnitCode.setRequired(false);
        // prmtHander		
        this.prmtHander.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtHander.setVisible(true);		
        this.prmtHander.setEditable(true);		
        this.prmtHander.setDisplayFormat("$name$");		
        this.prmtHander.setEditFormat("$number$");		
        this.prmtHander.setCommitFormat("$number$");		
        this.prmtHander.setRequired(false);
        // txttel		
        this.txttel.setVisible(true);		
        this.txttel.setHorizontalAlignment(2);		
        this.txttel.setMaxLength(100);		
        this.txttel.setRequired(false);
        // chkwr		
        this.chkwr.setText(resHelper.getString("chkwr.text"));		
        this.chkwr.setVisible(true);		
        this.chkwr.setHorizontalAlignment(2);
        // chkylrq		
        this.chkylrq.setText(resHelper.getString("chkylrq.text"));		
        this.chkylrq.setVisible(true);		
        this.chkylrq.setHorizontalAlignment(2);
        // chkylgd		
        this.chkylgd.setText(resHelper.getString("chkylgd.text"));		
        this.chkylgd.setVisible(true);		
        this.chkylgd.setHorizontalAlignment(2);
        // chkdt		
        this.chkdt.setText(resHelper.getString("chkdt.text"));		
        this.chkdt.setVisible(true);		
        this.chkdt.setHorizontalAlignment(2);
        // chkqzj		
        this.chkqzj.setText(resHelper.getString("chkqzj.text"));		
        this.chkqzj.setVisible(true);		
        this.chkqzj.setHorizontalAlignment(2);
        // chkcnqc		
        this.chkcnqc.setText(resHelper.getString("chkcnqc.text"));		
        this.chkcnqc.setVisible(true);		
        this.chkcnqc.setHorizontalAlignment(2);
        // chkylss		
        this.chkylss.setText(resHelper.getString("chkylss.text"));		
        this.chkylss.setVisible(true);		
        this.chkylss.setHorizontalAlignment(2);
        // chkty		
        this.chkty.setText(resHelper.getString("chkty.text"));		
        this.chkty.setVisible(true);		
        this.chkty.setHorizontalAlignment(2);
        // chkzx		
        this.chkzx.setText(resHelper.getString("chkzx.text"));		
        this.chkzx.setVisible(true);		
        this.chkzx.setHorizontalAlignment(2);
        // chkgh		
        this.chkgh.setText(resHelper.getString("chkgh.text"));		
        this.chkgh.setVisible(true);		
        this.chkgh.setHorizontalAlignment(2);
        // chkqy		
        this.chkqy.setText(resHelper.getString("chkqy.text"));		
        this.chkqy.setVisible(true);		
        this.chkqy.setHorizontalAlignment(2);
        // chkgz		
        this.chkgz.setText(resHelper.getString("chkgz.text"));		
        this.chkgz.setVisible(true);		
        this.chkgz.setHorizontalAlignment(2);
        // txtcxdw		
        this.txtcxdw.setVisible(true);		
        this.txtcxdw.setHorizontalAlignment(2);		
        this.txtcxdw.setMaxLength(100);		
        this.txtcxdw.setRequired(false);
        // txtsl		
        this.txtsl.setVisible(true);		
        this.txtsl.setHorizontalAlignment(2);		
        this.txtsl.setDataType(1);		
        this.txtsl.setSupportedEmpty(true);		
        this.txtsl.setMinimumValue( new java.math.BigDecimal("-1.0E27"));		
        this.txtsl.setMaximumValue( new java.math.BigDecimal("1.0E27"));		
        this.txtsl.setPrecision(1);		
        this.txtsl.setRequired(false);
        // scrollPanecontent
        // txtcontent		
        this.txtcontent.setVisible(true);		
        this.txtcontent.setRequired(false);		
        this.txtcontent.setMaxLength(1000);
        // scrollPaneBIMUDF0050
        // txtBIMUDF0050		
        this.txtBIMUDF0050.setVisible(true);		
        this.txtBIMUDF0050.setRequired(false);		
        this.txtBIMUDF0050.setMaxLength(1000);
        // txtjbr		
        this.txtjbr.setVisible(true);		
        this.txtjbr.setHorizontalAlignment(2);		
        this.txtjbr.setMaxLength(100);		
        this.txtjbr.setRequired(false);
        // pkjbrq		
        this.pkjbrq.setVisible(true);		
        this.pkjbrq.setRequired(false);
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
        contCreator.setBounds(new Rectangle(21, 564, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(21, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(21, 590, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(21, 590, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(371, 564, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(371, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(371, 590, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(371, 590, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCU.setBounds(new Rectangle(706, 11, 270, 19));
        this.add(contCU, new KDLayout.Constraints(706, 11, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(33, 11, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(33, 11, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(369, 11, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(369, 11, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(34, 35, 607, 19));
        this.add(contDescription, new KDLayout.Constraints(34, 35, 607, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(708, 564, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(708, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStatus.setBounds(new Rectangle(706, 35, 270, 19));
        this.add(contStatus, new KDLayout.Constraints(706, 35, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizStatus.setBounds(new Rectangle(708, 611, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(708, 611, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(708, 590, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(708, 590, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(17, 59, 988, 76));
        this.add(kDPanel1, new KDLayout.Constraints(17, 59, 988, 76, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel2.setBounds(new Rectangle(17, 139, 987, 45));
        this.add(kDPanel2, new KDLayout.Constraints(17, 139, 987, 45, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel3.setBounds(new Rectangle(17, 187, 987, 49));
        this.add(kDPanel3, new KDLayout.Constraints(17, 187, 987, 49, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contcxdw.setBounds(new Rectangle(33, 243, 270, 19));
        this.add(contcxdw, new KDLayout.Constraints(33, 243, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsl.setBounds(new Rectangle(369, 243, 270, 19));
        this.add(contsl, new KDLayout.Constraints(369, 243, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtEntry.setBounds(new Rectangle(21, 268, 981, 152));
        kdtEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry,new com.kingdee.eas.port.equipment.special.SpecialChangeEntryInfo(),null,false);
        this.add(kdtEntry_detailPanel, new KDLayout.Constraints(21, 268, 981, 152, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contcontent.setBounds(new Rectangle(22, 426, 981, 50));
        this.add(contcontent, new KDLayout.Constraints(22, 426, 981, 50, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBIMUDF0050.setBounds(new Rectangle(21, 513, 980, 39));
        this.add(contBIMUDF0050, new KDLayout.Constraints(21, 513, 980, 39, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contjbr.setBounds(new Rectangle(21, 489, 270, 19));
        this.add(contjbr, new KDLayout.Constraints(21, 489, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contjbrq.setBounds(new Rectangle(371, 489, 270, 19));
        this.add(contjbrq, new KDLayout.Constraints(371, 489, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
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
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(17, 59, 988, 76));        contoldUseUnit.setBounds(new Rectangle(14, 17, 270, 19));
        kDPanel1.add(contoldUseUnit, new KDLayout.Constraints(14, 17, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contnewUseUnit.setBounds(new Rectangle(14, 42, 270, 19));
        kDPanel1.add(contnewUseUnit, new KDLayout.Constraints(14, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contoldUnitCode.setBounds(new Rectangle(350, 17, 270, 19));
        kDPanel1.add(contoldUnitCode, new KDLayout.Constraints(350, 17, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contnewUnitCode.setBounds(new Rectangle(350, 42, 270, 19));
        kDPanel1.add(contnewUnitCode, new KDLayout.Constraints(350, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHander.setBounds(new Rectangle(688, 17, 270, 19));
        kDPanel1.add(contHander, new KDLayout.Constraints(688, 17, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conttel.setBounds(new Rectangle(688, 42, 270, 19));
        kDPanel1.add(conttel, new KDLayout.Constraints(688, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contoldUseUnit
        contoldUseUnit.setBoundEditor(prmtoldUseUnit);
        //contnewUseUnit
        contnewUseUnit.setBoundEditor(prmtnewUseUnit);
        //contoldUnitCode
        contoldUnitCode.setBoundEditor(txtoldUnitCode);
        //contnewUnitCode
        contnewUnitCode.setBoundEditor(txtnewUnitCode);
        //contHander
        contHander.setBoundEditor(prmtHander);
        //conttel
        conttel.setBoundEditor(txttel);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(17, 139, 987, 45));        chkwr.setBounds(new Rectangle(14, 14, 83, 19));
        kDPanel2.add(chkwr, new KDLayout.Constraints(14, 14, 83, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkylrq.setBounds(new Rectangle(98, 14, 98, 19));
        kDPanel2.add(chkylrq, new KDLayout.Constraints(98, 14, 98, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkylgd.setBounds(new Rectangle(221, 14, 104, 19));
        kDPanel2.add(chkylgd, new KDLayout.Constraints(221, 14, 104, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkdt.setBounds(new Rectangle(337, 13, 84, 19));
        kDPanel2.add(chkdt, new KDLayout.Constraints(337, 13, 84, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkqzj.setBounds(new Rectangle(441, 12, 88, 19));
        kDPanel2.add(chkqzj, new KDLayout.Constraints(441, 12, 88, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkcnqc.setBounds(new Rectangle(535, 12, 124, 19));
        kDPanel2.add(chkcnqc, new KDLayout.Constraints(535, 12, 124, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkylss.setBounds(new Rectangle(659, 12, 120, 19));
        kDPanel2.add(chkylss, new KDLayout.Constraints(659, 12, 120, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(17, 187, 987, 49));        chkty.setBounds(new Rectangle(17, 14, 80, 19));
        kDPanel3.add(chkty, new KDLayout.Constraints(17, 14, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkzx.setBounds(new Rectangle(98, 14, 71, 19));
        kDPanel3.add(chkzx, new KDLayout.Constraints(98, 14, 71, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkgh.setBounds(new Rectangle(222, 15, 72, 19));
        kDPanel3.add(chkgh, new KDLayout.Constraints(222, 15, 72, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkqy.setBounds(new Rectangle(338, 15, 69, 19));
        kDPanel3.add(chkqy, new KDLayout.Constraints(338, 15, 69, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkgz.setBounds(new Rectangle(442, 14, 79, 19));
        kDPanel3.add(chkgz, new KDLayout.Constraints(442, 14, 79, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contcxdw
        contcxdw.setBoundEditor(txtcxdw);
        //contsl
        contsl.setBoundEditor(txtsl);
        //contcontent
        contcontent.setBoundEditor(scrollPanecontent);
        //scrollPanecontent
        scrollPanecontent.getViewport().add(txtcontent, null);
        //contBIMUDF0050
        contBIMUDF0050.setBoundEditor(scrollPaneBIMUDF0050);
        //scrollPaneBIMUDF0050
        scrollPaneBIMUDF0050.getViewport().add(txtBIMUDF0050, null);
        //contjbr
        contjbr.setBoundEditor(txtjbr);
        //contjbrq
        contjbrq.setBoundEditor(pkjbrq);

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
		dataBinder.registerBinding("Entry.seq", int.class, this.kdtEntry, "seq.text");
		dataBinder.registerBinding("Entry", com.kingdee.eas.port.equipment.special.SpecialChangeEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("Entry.zdaNumber", java.lang.Object.class, this.kdtEntry, "zdaNumber.text");
		dataBinder.registerBinding("Entry.equipmentName", String.class, this.kdtEntry, "equipmentName.text");
		dataBinder.registerBinding("Entry.productNumber", String.class, this.kdtEntry, "productNumber.text");
		dataBinder.registerBinding("Entry.oldNumber", String.class, this.kdtEntry, "oldNumber.text");
		dataBinder.registerBinding("Entry.beizhu", String.class, this.kdtEntry, "beizhu.text");
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
		dataBinder.registerBinding("oldUseUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtoldUseUnit, "data");
		dataBinder.registerBinding("newUseUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtnewUseUnit, "data");
		dataBinder.registerBinding("oldUnitCode", String.class, this.txtoldUnitCode, "text");
		dataBinder.registerBinding("newUnitCode", String.class, this.txtnewUnitCode, "text");
		dataBinder.registerBinding("Hander", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtHander, "data");
		dataBinder.registerBinding("tel", String.class, this.txttel, "text");
		dataBinder.registerBinding("wr", boolean.class, this.chkwr, "selected");
		dataBinder.registerBinding("ylrq", boolean.class, this.chkylrq, "selected");
		dataBinder.registerBinding("ylgd", boolean.class, this.chkylgd, "selected");
		dataBinder.registerBinding("dt", boolean.class, this.chkdt, "selected");
		dataBinder.registerBinding("qzj", boolean.class, this.chkqzj, "selected");
		dataBinder.registerBinding("cnqc", boolean.class, this.chkcnqc, "selected");
		dataBinder.registerBinding("ylss", boolean.class, this.chkylss, "selected");
		dataBinder.registerBinding("ty", boolean.class, this.chkty, "selected");
		dataBinder.registerBinding("zx", boolean.class, this.chkzx, "selected");
		dataBinder.registerBinding("gh", boolean.class, this.chkgh, "selected");
		dataBinder.registerBinding("qy", boolean.class, this.chkqy, "selected");
		dataBinder.registerBinding("gz", boolean.class, this.chkgz, "selected");
		dataBinder.registerBinding("cxdw", String.class, this.txtcxdw, "text");
		dataBinder.registerBinding("sl", java.math.BigDecimal.class, this.txtsl, "value");
		dataBinder.registerBinding("content", String.class, this.txtcontent, "text");
		dataBinder.registerBinding("BIMUDF0050", String.class, this.txtBIMUDF0050, "text");
		dataBinder.registerBinding("jbr", String.class, this.txtjbr, "text");
		dataBinder.registerBinding("jbrq", java.util.Date.class, this.pkjbrq, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.special.app.SpecialChangeEditUIHandler";
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
        this.editData = (com.kingdee.eas.port.equipment.special.SpecialChangeInfo)ov;
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
		getValidateHelper().registerBindProperty("Entry.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.zdaNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.equipmentName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.productNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.oldNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.beizhu", ValidateHelper.ON_SAVE);    
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
		getValidateHelper().registerBindProperty("oldUseUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newUseUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldUnitCode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newUnitCode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Hander", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("wr", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ylrq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ylgd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qzj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cnqc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ylss", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zx", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("gh", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("gz", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cxdw", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sl", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("content", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("BIMUDF0050", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jbr", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jbrq", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntry_Changed(int rowIndex,int colIndex) method
     */
    public void kdtEntry_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"equipmentName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"name")));

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
    	sic.add(new SelectorItemInfo("Entry.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.zdaNumber.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Entry.zdaNumber.id"));
			sic.add(new SelectorItemInfo("Entry.zdaNumber.tzdaNumber"));
			sic.add(new SelectorItemInfo("Entry.zdaNumber.name"));
        	sic.add(new SelectorItemInfo("Entry.zdaNumber.number"));
		}
    	sic.add(new SelectorItemInfo("Entry.equipmentName"));
    	sic.add(new SelectorItemInfo("Entry.productNumber"));
    	sic.add(new SelectorItemInfo("Entry.oldNumber"));
    	sic.add(new SelectorItemInfo("Entry.beizhu"));
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
			sic.add(new SelectorItemInfo("oldUseUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("oldUseUnit.id"));
        	sic.add(new SelectorItemInfo("oldUseUnit.number"));
        	sic.add(new SelectorItemInfo("oldUseUnit.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("newUseUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("newUseUnit.id"));
        	sic.add(new SelectorItemInfo("newUseUnit.number"));
        	sic.add(new SelectorItemInfo("newUseUnit.name"));
		}
        sic.add(new SelectorItemInfo("oldUnitCode"));
        sic.add(new SelectorItemInfo("newUnitCode"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Hander.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("Hander.id"));
        	sic.add(new SelectorItemInfo("Hander.number"));
        	sic.add(new SelectorItemInfo("Hander.name"));
		}
        sic.add(new SelectorItemInfo("tel"));
        sic.add(new SelectorItemInfo("wr"));
        sic.add(new SelectorItemInfo("ylrq"));
        sic.add(new SelectorItemInfo("ylgd"));
        sic.add(new SelectorItemInfo("dt"));
        sic.add(new SelectorItemInfo("qzj"));
        sic.add(new SelectorItemInfo("cnqc"));
        sic.add(new SelectorItemInfo("ylss"));
        sic.add(new SelectorItemInfo("ty"));
        sic.add(new SelectorItemInfo("zx"));
        sic.add(new SelectorItemInfo("gh"));
        sic.add(new SelectorItemInfo("qy"));
        sic.add(new SelectorItemInfo("gz"));
        sic.add(new SelectorItemInfo("cxdw"));
        sic.add(new SelectorItemInfo("sl"));
        sic.add(new SelectorItemInfo("content"));
        sic.add(new SelectorItemInfo("BIMUDF0050"));
        sic.add(new SelectorItemInfo("jbr"));
        sic.add(new SelectorItemInfo("jbrq"));
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
        return new MetaDataPK("com.kingdee.eas.port.equipment.special.client", "SpecialChangeEditUI");
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
        return com.kingdee.eas.port.equipment.special.client.SpecialChangeEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.special.SpecialChangeFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.special.SpecialChangeInfo objectValue = new com.kingdee.eas.port.equipment.special.SpecialChangeInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/special/SpecialChange";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.special.app.SpecialChangeQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntry;
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